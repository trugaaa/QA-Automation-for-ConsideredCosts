package api;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import java.io.FileInputStream;
import java.util.Properties;

public class APIMethods {
    private static  String rootURL;
    private static String validAuthToken;
    private static final String INVALID_AUTH_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9u" +
            "YW1lIjoiQWRtaW4iLCJuYmYiOjE1NzQ4ODE3NTMsImV4cCI6MTU3NDg4MjM1MywiaXNzIjoiQ0NUZWFtIiwiYXVkIjoiQ0NVc2VycyJ9.yz1mP4VMxBzN516mvmH-T0F9VshYQmVfbnmzi6CkOw";

    //TODO CREate normal work
    /*

    public static ClientResponse settingThingsUp(){
        webResource=client.resource(APIMethods.getURLWithEndpoint("/login"));
        ClientResponse response = webResource.accept("application/json").post(ClientResponse.class);
        //assertEquals(response.getStatus(),400);
    }*/
    public APIMethods()
    {
        FileInputStream fis;
        Properties property = new Properties();
        Client client;
        WebResource webResource;



        try {
            fis = new FileInputStream("src/test/api/resources/configAPI.properties");
            property.load(fis);

            rootURL = property.getProperty("rootURL");
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public static String getValidAuthToken() {
        return validAuthToken;
    }

    public static void setValidAuthToken(String validAuthToken) {
        APIMethods.validAuthToken = validAuthToken;
    }

    public static String getInvalidAuthToken() {
        return INVALID_AUTH_TOKEN;
    }

    public static String getURLWithEndpoint(String endpoint)
    {
        return rootURL + endpoint;
    }
}
