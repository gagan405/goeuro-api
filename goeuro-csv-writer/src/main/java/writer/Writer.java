package writer;

import entities.City;

import java.io.IOException;
import java.util.List;

/**
 * Created by gbm on 26/08/15.
 */
public interface Writer {
    void write(List<?> objects, java.io.Writer writer ) throws IOException;
}
