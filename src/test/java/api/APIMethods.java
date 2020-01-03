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

    public Invocation.Builder buildRequest(JSONObjectAPI headers)
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
        Response res = buildRequest(headers).get();
        res.bufferEntity();
            if(needLogs) {
                logs(headers, null, res);
            }
        return res;
    }

    public Response post(JSONObjectAPI headers, JSONObject body)
    {

        Response res = buildRequest(headers).post(Entity.json(body.toString()));
        res.bufferEntity();
        if(needLogs) {
            logs(headers, body, res);
        }
        return res;
    }

    public Response delete(JSONObjectAPI headers)
    {
            Response response= buildRequest(headers)
                    .delete();
        response.bufferEntity();

        if(needLogs) {
            logs(headers, null, response);
        }
        return response;
    }

    public Response put(JSONObjectAPI headers,JSONObject body)
    {

        Response res = buildRequest(headers).put(Entity.json(body.toString()));
        res.bufferEntity();

        if(needLogs) {
            logs(headers, body, res);
        }
        return res;
    }

    public Response postNL(JSONObjectAPI headers, JSONObject body)
    {

        Response res = buildRequest(headers).post(Entity.json(body.toString()));
        res.bufferEntity();
        return res;
    }

    public Response getNL(JSONObjectAPI headers)
    {
        Response res = buildRequest(headers).get();
        res.bufferEntity();
        return res;
    }
}
