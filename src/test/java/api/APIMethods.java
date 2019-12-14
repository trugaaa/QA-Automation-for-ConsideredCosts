package api;

import api.data.JSONObjectAPI;
import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.*;
import java.io.FileInputStream;
import java.util.Properties;
import static api.process.APIOperations.*;

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

    public Invocation.Builder getResponse(JSONObjectAPI headers)
    {
        Client client= ClientBuilder.newClient();
        if(headers.has("headers"))
        {
            return client
                    .target(rootURL).path(headers.getEndpoint())
                    .request(headers.getRequest())
                    .headers(headers.getHeadersMultiMap())
                    .accept(headers.getAccept());
        }
        else
            return client
                    .target(rootURL).path(headers.getEndpoint())
                    .request(headers.getRequest())
                    .accept(headers.getAccept());
    }

    public Response get(JSONObjectAPI headers,int expectedHttpCode, boolean needLogs)
    {
        Response res = getResponse(headers).get();
            requestLogs(rootURL, headers,null,res,new Throwable()
                    .getStackTrace()[0]
                    .getMethodName(),expectedHttpCode,needLogs);
        return res;
    }



    public Response post(JSONObjectAPI headers,JSONObject data , int expectedHttpCode, boolean needLogs)
    {

        Response res = getResponse(headers).post(Entity.json(data.toString()));

            requestLogs(rootURL, headers,null,res,new Throwable()
                    .getStackTrace()[0]
                    .getMethodName(),expectedHttpCode,needLogs);
        return res;
    }


    public Response delete(JSONObjectAPI headers, int expectedHttpCode, boolean needLogs)
    {
            Response response= getResponse(headers)
                    .delete();
        requestLogs(rootURL,headers,null,response,new Throwable()
                .getStackTrace()[0]
                .getMethodName(),expectedHttpCode,needLogs);


        return response;
    }

}
