package client;


import client.internal.GoEuroClientException;
import entities.City;

import java.util.List;

/**
 * Created by gbm on 24/08/15.
 */
public interface GoEuroClient {
    List<City> doGet(String name) throws GoEuroClientException;
}
