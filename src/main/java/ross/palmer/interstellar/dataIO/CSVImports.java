package ross.palmer.interstellar.dataIO;

import jdk.internal.util.xml.impl.ReaderUTF8;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import ross.palmer.interstellar.simulator.galaxy.Galaxy;
import ross.palmer.interstellar.simulator.galaxy.StellarSystem;

import java.io.*;

public class CSVImports {

    public static void stars(Galaxy galaxy) throws IOException {

        System.out.println("=== Loading Star Data ===");

        InputStream stream = new FileInputStream("within300pc.csv");
        Reader reader = new ReaderUTF8(stream);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());

        csvParser.getRecords().forEach(csvRecord -> {
            StellarSystem stellarSystem = new StellarSystem(csvRecord);
            galaxy.addStellarSystem(stellarSystem);
        });

        System.out.println("=== Stellar Systems Generated ===");

    }

}
