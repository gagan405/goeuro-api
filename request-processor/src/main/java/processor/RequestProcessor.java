package processor;

import client.GoEuroClient;
import client.internal.GoEuroClientException;
import entities.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import writer.Writer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by gbm on 25/08/15.
 */
public class RequestProcessor implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(RequestProcessor.class);
    private GoEuroClient client;
    private Writer writer;
    private String query;

    //Request processor takes the clinet and writer and just calls the proper methods
    //Keeping them as interfaces will make it extensible if we want to have XML Writer or a
    //client that reads from file

    //We can also use dependency injection
    public RequestProcessor(GoEuroClient client, Writer writer){
        this.client = client;
        this.writer = writer;
    }

    public RequestProcessor withRequest(String query){
        this.query = query;
        return this;
    }

    public void run(){
        try {
            //Get data from the api
            List<City> cities = client.doGet(query);
            if(cities.size() == 0){
                logger.info("Empty list received from end point.");
            }
            //write to csv file
            //This overwrites if file already exists.
            writer.write(cities, new FileWriter(query + ".csv"));
            logger.info("Created CSV file : " + query + ".csv");
        }catch (GoEuroClientException e){
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
