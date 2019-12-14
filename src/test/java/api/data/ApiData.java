package api.data;

import api.APIMethods;
import api.body.json.LoginJsonData;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;


public class ApiData {

    public JSONObjectAPI DataHeaders(String endpoint) {
        JSONObjectAPI apiHeaders = new JSONObjectAPI();
        apiHeaders.putAccept(MediaType.APPLICATION_JSON);
        apiHeaders.putRequest(MediaType.APPLICATION_JSON);
        apiHeaders.putEndpoint(endpoint);
        return apiHeaders;
    }

    public JSONObjectAPI DataHeadersToken(String endpoint, String tokenType) {
        if (tokenType == "Admin") {
            JSONObjectAPI apiHeaders = new JSONObjectAPI();
            apiHeaders.putAccept(MediaType.APPLICATION_JSON);
            apiHeaders.putRequest(MediaType.APPLICATION_JSON);
            apiHeaders.putHeaders(getValidAdminTokenHeader());
            apiHeaders.putEndpoint(endpoint);
            return apiHeaders;
        } else if (tokenType == "User") {
            JSONObjectAPI apiHeaders = new JSONObjectAPI();
            apiHeaders.putAccept(MediaType.APPLICATION_JSON);
            apiHeaders.putRequest(MediaType.APPLICATION_JSON);
            apiHeaders.putHeaders(getValidUserTokenHeader());
            apiHeaders.putEndpoint(endpoint);
            return apiHeaders;
        } else if (tokenType == "Invalid") {
            JSONObjectAPI apiHeaders = new JSONObjectAPI();
            apiHeaders.putAccept(MediaType.APPLICATION_JSON);
            apiHeaders.putRequest(MediaType.APPLICATION_JSON);
            apiHeaders.putHeaders(getInvalidTokenHeader());
            apiHeaders.putEndpoint(endpoint);
            return apiHeaders;
        } else  {
            // (tokenType == "Expired")
            JSONObjectAPI apiHeaders = new JSONObjectAPI();
            apiHeaders.putAccept(MediaType.APPLICATION_JSON);
            apiHeaders.putRequest(MediaType.APPLICATION_JSON);
            apiHeaders.putHeaders(getExpiredAdminTokenHeader());
            apiHeaders.putEndpoint(endpoint);
            return apiHeaders;
        }

    }




    public HashMap<String,Object> getValidAdminTokenHeader()
    {
        JSONObjectAPI headers=DataHeaders("/accounts/jsonLogin");
        JSONObject data=new LoginJsonData().loginAdmin();
        JSONObject object=new JSONObject(new APIMethods().post(headers,data,200,false).readEntity(String.class)).getJSONObject("data");
        HashMap<String,Object> myHeaders=new HashMap<>();
        myHeaders.put("Authorization","Bearer "+object.getString("access_token"));
        return myHeaders;
    }

    public HashMap<String,Object> getValidUserTokenHeader()
    {
        JSONObjectAPI headers=DataHeaders("/accounts/jsonLogin");
        JSONObject data=new LoginJsonData().loginUser();
        JSONObject object=new JSONObject(new APIMethods().post(headers,data,200,false).readEntity(String.class)).getJSONObject("data");
        HashMap<String,Object> myHeaders=new HashMap<>();
        myHeaders.put("Authorization","Bearer "+object.getString("access_token"));
        return myHeaders;
    }

    public HashMap<String,Object> getInvalidTokenHeader()
    {
        HashMap<String,Object> myHeaders=new HashMap<>();
        String invalidToken = "";
        FileInputStream fis;
        Properties property = new Properties();
        try {
            fis = new FileInputStream("src/test/java/api/resources/configAPI.properties");
            property.load(fis);
            invalidToken = property.getProperty("INVALID_AUTH_TOKEN");
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

        myHeaders.put("Authorization","Bearer "+ invalidToken);
        return myHeaders;
    }
    public HashMap<String,Object> getExpiredAdminTokenHeader()
    {
        JSONObjectAPI headers=DataHeaders("/accounts/jsonLogin");
        JSONObject data=new LoginJsonData().loginAdmin();
        JSONObject object=new JSONObject(new APIMethods().post(headers,data,200,false).readEntity(String.class)).getJSONObject("data");
        HashMap<String,Object> myHeaders=new HashMap<>();
        myHeaders.put("Authorization","Bearer "+object.getString("access_token"));
        new JSONObject(new APIMethods().post(headers,data,200,false));
        return myHeaders;
    }
}
