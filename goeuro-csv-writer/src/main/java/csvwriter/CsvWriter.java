package csvwriter;

import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Created by gbm on 25/08/15.
 */
public class CsvWriter {
    private CellProcessor[] processors;
    private String[] headers;

    public CsvWriter(CellProcessor[] processors, String[] headers){
        this.processors = processors;
        this.headers = headers;
    }
    //Same instance can be re used to write into different write pads ..
    //like file writer or string writer
    //Alternately, writer can be made a part of constructor
    public void write(List<?> cities, Writer writer) throws IOException {
        ICsvBeanWriter beanWriter = null;
        try {
            beanWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE);
            beanWriter.writeHeader(this.headers);
            // write the beans
            for(Object city : cities) {
                beanWriter.write(city, headers, processors);
            }
        }
        finally {
            if( beanWriter != null ) {
                beanWriter.close();
            }
        }
    }
}
