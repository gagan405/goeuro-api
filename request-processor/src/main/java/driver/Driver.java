package driver;

import client.GoEuroClient;
import client.internal.GoEuroClientImpl;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import processor.RequestProcessor;
import writer.CsvWriter;
import writer.Writer;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by gbm on 26/08/15.
 */
public class Driver {
    //Right now we need only 1 thread
    private final ExecutorService exec = Executors.newSingleThreadExecutor();

    //Construct the dependencies
    private final GoEuroClient client = new GoEuroClientImpl();
    private final Writer writer = new CsvWriter(getProcessors(), getHeaders());


    public static void main(String[] args){
        //Basic sanity check
        if(args.length == 0){
            System.err.println("No input found. This needs a city name to continue.");
            System.exit(-1);
        }
        if(args.length > 1){
            System.out.println("More than one input entered. Ignoring input strings after the first one.");
        }
        //Going ahead with processing
        new Driver().doProcess(args[0]);
    }

    private void doProcess(String input) {
        //construct processor
        RequestProcessor processor = new RequestProcessor(client, writer).withRequest(input);

        //Run it in a thread
        exec.execute(processor);
        exec.shutdown();
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
