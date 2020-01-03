package api;

import api.data.JSONObjectAPI;
import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.*;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import static api.process.APIOperations.*;

public class APIMethods {

    private  String rootURL;
    private  Boolean needLogs;


    public APIMethods()
    {   FileInputStream fis;
        Properties property = new Properties();
        try {
            fis = new FileInputStream("src/test/java/api/resources/configAPI.properties");
            property.load(fis);
            rootURL = property.getProperty("ROOT_URL");
            needLogs = Boolean.parseBoolean(property.getProperty("NEED_LOGS"));
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

    public Response get(JSONObjectAPI headers)
    {
        Response res = getResponse(headers).get();
        res.bufferEntity();
            if(needLogs) {
                requestLogs(headers, null, res);
            }
        return res;
    }

    public Response post(JSONObjectAPI headers, JSONObject body)
    {

        Response res = getResponse(headers).post(Entity.json(body.toString()));
        res.bufferEntity();
        if(needLogs) {
            requestLogs(headers, body, res);
        }
        return res;
    }

    public Response delete(JSONObjectAPI headers)
    {
            Response response= getResponse(headers)
                    .delete();
        response.bufferEntity();

        if(needLogs) {
            requestLogs(headers, null, response);
        }
        return response;
    }

    public Response put(JSONObjectAPI headers,JSONObject body)
    {

        Response res = getResponse(headers).put(Entity.json(body.toString()));
        res.bufferEntity();

        if(needLogs) {
            requestLogs(headers, body, res);
        }
        return res;
    }

}
