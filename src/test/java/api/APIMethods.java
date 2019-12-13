package api;

import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.io.FileInputStream;
import java.util.Properties;

public class APIMethods {

    private  String rootURL;

    public APIMethods()
    {   FileInputStream fis;
        Properties property = new Properties();
        try {
            fis = new FileInputStream("src/test/java/api/resources/configAPI.properties");
            property.load(fis);

            rootURL = property.getProperty("ROOT_URL");
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    public Response post(String endpoint, JSONObject body,int expectedHttpCode)
    {
        Client client= ClientBuilder.newClient();
        Response res = client.target(rootURL).path(endpoint).request().post(Entity.json(body.toString()));
        requestLogs(endpoint,body,res,new Throwable()
                .getStackTrace()[0]
                .getMethodName(),expectedHttpCode);

        return res;
    }

    public Response delete(String endpoint,int expectedHttpCode)
    {
        Client client= ClientBuilder.newClient();
        Response res = client.target(rootURL).path(endpoint).request().delete();
        requestLogs(endpoint,null,res,new Throwable()
                .getStackTrace()[0]
                .getMethodName(),expectedHttpCode);
        return res;

    }
    public void requestLogs(String endpoint, JSONObject body, Response response, String method, int expectedHttpCode)
    {
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("URL: "+rootURL+endpoint);
        System.out.println("HTTP Method: "+ method.toUpperCase());
        if(body!=null){
        System.out.println("Request body: "+body.toString());
    }
        System.out.println("Expected HTTP Code: " + expectedHttpCode);

        System.out.println();
        System.out.println("Actual HTTP Code: "+ response.getStatus());
        if(response.getHeaders()!=null)
        {
            System.out.println(response.getHeaders().toString());
        }
        if(body!=null) {
            System.out.println("Response body: " + response.readEntity(String.class));
        }

        System.out.println();
    }
}
