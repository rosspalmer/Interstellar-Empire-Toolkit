package ross.palmer.interstellar.utilities;

import java.util.HashMap;
import java.util.Map;

public class IdGenerator {

    private static Map<String, Long> idMap = new HashMap<>();

    public static long getNextId(String idType) {

        long id = 0;

        if (idMap.containsKey(idType)) {
            id = idMap.get(idType);
        }

        id++;
        idMap.put(idType, id);

        return id;

    }

}
