package api.process;

import api.data.JSONObjectAPI;
import io.qameta.allure.Step;
import org.json.JSONObject;

import javax.ws.rs.core.*;


public class APIOperations {

    @Step("Logging operation")
    public static void logs(JSONObjectAPI headers, JSONObject body, Response response) {

        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("Date: " + response.getDate());

        System.out.println("URI: " + createSubString(response.toString(), " uri=", ", status"));
        System.out.println("HTTP Method: " + createSubString(response.toString(), "method=", ","));

        System.out.println("Request headers: " + headers);
        if (body != null) {
            System.out.println("Request body: " + body.toString());
        } else {
            System.out.println("Request body: ");
        }

        System.out.println();
        System.out.println("Response HTTP Code: " + response.getStatus());
        if (response.getHeaders() != null) {
            System.out.println("Response headers: " + response.getHeaders().toString());
        } else {
            System.out.println("Response headers: ");
        }

        System.out.println("Response body: " + response.readEntity(String.class));

    }

    /*
    public static boolean keyValidation(Response response, List<String> keyList) {
        boolean result = true;
        StringBuilder resultsOfCheck = new StringBuilder("Response body checking:");
            JSONObject body=new JSONObject(response.readEntity(String.class)).getJSONObject("data");
            for (String key : keyList) {
                if (!body.has(key)) {
                   resultsOfCheck.append("\n").append(key).append(" is not present in the body");
                result = false;
                }
            }
            if(!resultsOfCheck.toString().equals("Response body checking:"))
                System.out.println(resultsOfCheck);

        return result;
    }*/

    public static String createSubString(String fullString, String start, String end) {
        return fullString.substring(fullString.indexOf(start) + start.length(),
                fullString.indexOf(end));
    }

}
