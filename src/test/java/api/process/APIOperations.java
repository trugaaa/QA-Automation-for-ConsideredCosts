package api.process;

import api.data.JSONObjectAPI;
import org.json.JSONObject;

import javax.ws.rs.core.Response;

public class APIOperations {

    public static void requestLogs(String rootURL, JSONObjectAPI headers, JSONObject body, Response response, String method, int expectedHttpCode, boolean needLogs) {
        if (needLogs)
        {
            System.out.println("------------------------------------------------------------------------------------------");
            System.out.println("URL: " + rootURL + headers.getEndpoint());
            System.out.println("HTTP Method: " + method.toUpperCase());
            System.out.println("Request headers: "+headers);
            if (body != null) {
                System.out.println("Request body: " + body.toString());
            }else { System.out.println("Request body: ");}

            System.out.println("Expected HTTP Code: " + expectedHttpCode);

            System.out.println();
            System.out.println("Response HTTP Code: " + response.getStatus());
            if (response.getHeaders() != null) {
                System.out.println("Response headers: " + response.getHeaders().toString());
            }else { System.out.println("Response headers: ");}

             System.out.println("Response body: " + response.readEntity(String.class));
        }
    }

}
