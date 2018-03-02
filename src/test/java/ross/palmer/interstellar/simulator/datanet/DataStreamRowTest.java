package ross.palmer.interstellar.simulator.datanet;

import org.junit.Before;
import org.junit.Test;
import ross.palmer.interstellar.simulator.engine.Simulator;

import static org.junit.Assert.*;

public class DataStreamRowTest {

    private DataStreamRow dataRow;

    @Before
    public void setup() {
        dataRow = new DataStreamRow();
    }

    @Test
    public void timestamp() {

        Simulator.resetTime();
        DataStreamRow newRow = new DataStreamRow();
        assertEquals(0, newRow.getTimeStamp(), 0.000001);

        Simulator.setCurrentTime(302.4);
        newRow = new DataStreamRow();
        assertEquals(302.4, newRow.getTimeStamp(), 0.000001);

        Simulator.setCurrentTime(560);
        newRow = new DataStreamRow();
        assertEquals(560, newRow.getTimeStamp(), 0.000001);

    }

    @Test
    public void valueBoolean() {

        dataRow.setValue("boolean", true);
        boolean value = dataRow.getValueBoolean("boolean");
        assertTrue(value);

        dataRow.setValue("boolean", false);
        value = dataRow.getValueBoolean("boolean");
        assertFalse(value);

    }

    @Test
    public void valueDouble() {

        dataRow.setValue("double", 304.2);
        double value = dataRow.getValueDouble("double");
        assertEquals(304.2, value, 0.00001);

        dataRow.setValue("double", 24.5);
        value = dataRow.getValueDouble("double");
        assertEquals(24.5, value, 0.00001);

    }

    @Test
    public void valueEnum() {

        dataRow.setValue("enum", TestEnum.WHY);
        Enum value = dataRow.getValueEnum("enum");
        assertEquals(TestEnum.WHY, value);

        dataRow.setValue("enum", TestEnum.BYE);
        value = dataRow.getValueEnum("enum");
        assertEquals(TestEnum.BYE, value);

    }

    @Test
    public void valueLong() {

        dataRow.setValue("long", 205934);
        long value = dataRow.getValueLong("long");
        assertEquals(205934, value);

        dataRow.setValue("long", 79);
        value = dataRow.getValueLong("long");
        assertEquals(79, value);

    }

    @Test
    public void valueString() {

        dataRow.setValue("String", "Gingah");
        String value = dataRow.getValueString("String");
        assertTrue(value.equals("Gingah"));

        dataRow.setValue("String", "F Testing");
        value = dataRow.getValueString("String");
        assertTrue(value.equals("F Testing"));

    }

    private enum TestEnum {HI, BYE, WHY}

}