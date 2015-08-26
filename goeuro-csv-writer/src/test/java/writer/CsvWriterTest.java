package writer;

import entities.City;
import org.junit.Assert;
import org.junit.Test;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gbm on 25/08/15.
 */
public class CsvWriterTest {

    @Test
    public void testShouldCorrectlyWriteToCsv() throws Exception {
        String[] expectedHeaders =  new String[]{"_id", "name", "type", "latitude", "longitude"};
        CsvWriter writer = new CsvWriter(getProcessors(),expectedHeaders);
        City city = new City(1L, "Barcelona", "city", 2.71828, 3.141592);
        List<City> cities = new ArrayList<>();
        cities.add(city);
        StringWriter stringWriter = new StringWriter();

        writer.write(cities, stringWriter);
        String result = stringWriter.toString();

        String[] actualHeaders = result.split("\r\n")[0].split(",");
        Assert.assertArrayEquals(expectedHeaders, actualHeaders);

        String[] data = result.split("\r\n")[1].split(",");
        Assert.assertEquals((long)city.get_id(), (long)Long.valueOf(data[0]));
        Assert.assertEquals(city.getName(), data[1]);
        Assert.assertEquals(city.getType(), data[2]);
        Assert.assertEquals(String.valueOf(city.getLatitude()), data[3]);
        Assert.assertEquals(String.valueOf(city.getLongitude()), data[4]);
    }

    private CellProcessor[] getProcessors() {

        final CellProcessor[] processors = new CellProcessor[] {
                new UniqueHashCode(), // ID (must be unique)
                new NotNull(), // Name
                new NotNull(), // Type
                new NotNull(), // Latitude
                new NotNull() // Longitude
        };
        return processors;
    }
}
