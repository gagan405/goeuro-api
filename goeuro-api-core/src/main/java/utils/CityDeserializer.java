package utils;

import entities.City;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class CityDeserializer extends JsonDeserializer<City> {

    @Override
    public City deserialize(JsonParser parser, DeserializationContext context)
            throws IOException {
        JsonNode json = parser.getCodec().readTree(parser);
        long id = json.get("_id").getLongValue();
        String name =  json.get("name").getTextValue();
        String type = json.get("type").getTextValue();
        Double latitude = json.get("geo_position").get("latitude").getValueAsDouble();
        Double longitude = json.get("geo_position").get("longitude").getValueAsDouble();
        return new City(id, name, type, latitude, longitude);
    }

}