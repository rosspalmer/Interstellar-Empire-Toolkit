import pandas as pd

REGEX_SEARCHES = {
    'class_matches': '^([OABFGKM])',
    'type_matches': '^.*([VI])+',
    'number_matches': '^[OABFGKM]([0-9])'
}

USED_SEARCHES = ['class', 'type']

def run():

    raw_df = load_csv_data('rawStars.csv')
    raw_df = determine_matches(raw_df)

    df = apply_regex(raw_df)

    df.to_csv('stars.csv')

def load_csv_data(filepath):

    df = pd.read_csv(filepath)
    df.columns = map(str.lower, df.columns)

    return df

def determine_matches(df):

    df.loc[pd.isnull(df['spectrum']), 'spectrum'] = ''
    df['spectrum'] = df['spectrum'].str.upper()

    for category, regex in REGEX_SEARCHES.items():
        regex_for_matching = regex.replace('(', '')
        regex_for_matching = regex_for_matching.replace(')', '')
        df[category] = df['spectrum'].str.match(regex_for_matching)

    return df

def apply_regex(df):

    filter_columns = list(map(lambda x: "%s_matches" % x, USED_SEARCHES))
    df['complete'] = True

    print(filter_columns)

    for column_name in filter_columns:
        df['complete'] *= df[column_name]

    df = df[df['complete']]

    for column_name in USED_SEARCHES:
        regex_string = REGEX_SEARCHES.get("%s_matches" % column_name)
        df[column_name] = df['spectrum'].str.extract(regex_string)

    return df

run()
