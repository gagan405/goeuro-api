package processor;

import client.GoEuroClient;
import client.internal.GoEuroClientException;
import client.internal.GoEuroClientImpl;
import csvwriter.CsvWriter;
import entities.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by gbm on 25/08/15.
 */
public class RequestProcessor {
    private final Logger logger = LoggerFactory.getLogger(RequestProcessor.class);
    private GoEuroClient client;
    private CsvWriter csvWriter;

    public RequestProcessor(){
        this.client = new GoEuroClientImpl();
        this.csvWriter = new CsvWriter(getProcessors(), getHeaders());
    }

    public static void main(String[] args){
        if(args.length == 0){
            System.err.println("No input found. This needs a city name to continue.");
            System.exit(-1);
        }
        if(args.length > 1){
            System.out.println("More than one input entered. Ignoring input strings after the first one.");
        }
        RequestProcessor processor = new RequestProcessor();
        processor.process(args[0]);
    }

    public void process(String args){
        try {
            //Get data from the api
            List<City> cities = client.doGet(args);
            if(cities.size() == 0){
                logger.info("Empty list received from end point.");
            }
            //write to csv file
            csvWriter.write(cities, new FileWriter(args + ".csv"));
            logger.info("Created CSV file : " + args + ".csv");
        }catch (GoEuroClientException e){
            logger.error(e.getMessage());
            System.err.println("Error handling request. Check logs for more details");
        } catch (IOException e) {
            logger.error(e.getMessage());
            System.err.println("Error writing to file. Check logs for details");
        }
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

    private String[] getHeaders(){
        return new String[]{"_id", "name", "type", "latitude", "longitude"};
    }
}
