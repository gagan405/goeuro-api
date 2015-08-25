package client.internal;

import client.GoEuroClient;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import entities.City;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by gbm on 24/08/15.
 */

public class GoEuroClientImpl implements GoEuroClient {
    private static final String url = "http://api.goeuro.com/api/v2/position/suggest/en/";
    private final Logger logger = LoggerFactory.getLogger(GoEuroClientImpl.class);
    private final Client client;

    public GoEuroClientImpl(){
        ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JacksonJaxbJsonProvider.class);
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        this.client = Client.create(config);
    }

    public List<City> doGet(String city) throws GoEuroClientException {
        checkRequest(city);
        logger.info("Hitting API endpoint : " + url + getEncodedRequest(city));
        ClientResponse response = client.resource(url + getEncodedRequest(city))
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);

        checkResponse(response);
        return response.getEntity(new GenericType<List<City>>(){});
    }

    private void checkRequest(String city){
        if(city == null || city.isEmpty()){
            throw new GoEuroClientException("Invalid request. City name cannot be empty.");
        }
    }

    private String getEncodedRequest(String request){
        try {
            return URLEncoder.encode(request, "UTF-8");
        }catch (UnsupportedEncodingException e){
            throw new GoEuroClientException("Invalid request. City name cannot be encoded in UTF-8.");
        }
    }

    private void checkResponse(ClientResponse response) {
        if (response.getStatus() != 200) {
            throw new GoEuroClientException("Failed to get object. Error code : " + response.getStatus());
        }
    }
}
