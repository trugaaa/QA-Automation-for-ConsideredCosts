package api.process;

        import api.data.JSONObjectAPI;
        import org.json.JSONObject;

        import javax.ws.rs.core.Response;
        import java.util.List;

public class APIOperations {

    public static void requestLogs(JSONObjectAPI headers, JSONObject body, Response response) {
            String method = "";
            String URI = "";

            System.out.println("------------------------------------------------------------------------------------------");
            System.out.println("URI: "+URI);
            System.out.println("HTTP Method: " + method);
            System.out.println("Request headers: "+headers);
            if (body != null) {
                System.out.println("Request body: " + body.toString());
            }else { System.out.println("Request body: ");}

            System.out.println();
            System.out.println("Response HTTP Code: " + response.getStatus());
            if (response.getHeaders() != null) {
                System.out.println("Response headers: " + response.getHeaders().toString());
            }else { System.out.println("Response headers: ");}

            System.out.println("Response body: " + response.readEntity(String.class));

    }

    public static boolean keyValidation(Response response, List<String> keyList) {
        boolean result = true;
        String resultsOfCheck = "Response body checking:";
            JSONObject body=new JSONObject(response.readEntity(String.class)).getJSONObject("data");
            for (String key : keyList) {
                if (!body.has(key)) {
                   resultsOfCheck+="\n" + key +" is not present in the body";
                result = false;
                }
            }
            if(resultsOfCheck!="Response body checking:")
                System.out.println(resultsOfCheck);

        return result;
    }
}
