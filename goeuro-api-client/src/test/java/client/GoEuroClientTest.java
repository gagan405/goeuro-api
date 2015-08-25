package client;

import client.internal.GoEuroClientImpl;
import client.internal.GoEuroClientException;

import entities.City;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

/**
 * Created by gbm on 24/08/15.
 */

public class GoEuroClientTest {

    private GoEuroClient clientUnderTest;
    @Before
    public void setUp(){
        this.clientUnderTest = new GoEuroClientImpl();
    }

    @Test
    public void testShouldGetValidDataForValidURL(){
        List<City> cities = clientUnderTest.doGet("Berlin");

      //Berlin should not return an empty array
        Assert.assertFalse(cities.size() == 0);

        //Actually this is kind of testing what the API is returning us. Which we don't need.
        /*
        Java 8 needed for this.
        boolean isDifferent = cities.stream()
                .filter(object -> !object.getName().toLowerCase().contains("berlin"))
                .findAny().isPresent();
        Assert.assertFalse(isDifferent);
        */
    }

    @Test(expected = GoEuroClientException.class)
    public void testShouldRaiseExceptionForEmptyCity(){
        clientUnderTest.doGet("");
    }
}
