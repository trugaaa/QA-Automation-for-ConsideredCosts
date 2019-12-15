package api.data;

import api.APIMethods;
import api.body.json.add.LoginJsonData;
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
        HashMap<String,Object> headersMap = new HashMap<>();
        JSONObjectAPI apiHeaders = new JSONObjectAPI();
        apiHeaders.putAccept(MediaType.APPLICATION_JSON);
        apiHeaders.putRequest(MediaType.APPLICATION_JSON);
        apiHeaders.putEndpoint(endpoint);

        if (tokenType == "Admin") {
            headersMap.put("Authorization", getValidAdminTokenHeader());
        } else if (tokenType == "User") {
            headersMap.put("Authorization", getValidUserTokenHeader());
        } else if (tokenType == "Invalid") {
            headersMap.put("Authorization", getInvalidTokenHeader());
        } else {
            headersMap.put("Authorization", getExpiredAdminTokenHeader());
        }
        apiHeaders.putHeaders(headersMap);
        return apiHeaders;
    }

    public JSONObjectAPI DataHeadersTokenId(String endpoint, String tokenType,Object id) {
        HashMap<String,Object> headersMap = new HashMap<>();
        headersMap.put("id",id);
        JSONObjectAPI apiHeaders = new JSONObjectAPI();
        apiHeaders.putAccept(MediaType.APPLICATION_JSON);
        apiHeaders.putRequest(MediaType.APPLICATION_JSON);
        apiHeaders.putEndpoint(endpoint);

        if (tokenType == "Admin") {
            headersMap.put("Authorization", getValidAdminTokenHeader());
        } else if (tokenType == "User") {
            headersMap.put("Authorization", getValidUserTokenHeader());
        } else if (tokenType == "Invalid") {
            headersMap.put("Authorization", getInvalidTokenHeader());
        } else {
            headersMap.put("Authorization", getExpiredAdminTokenHeader());
        }
        apiHeaders.putHeaders(headersMap);
        return apiHeaders;
    }


    public String getValidAdminTokenHeader()
    {
        JSONObjectAPI headers=DataHeaders("/accounts/jsonLogin");
        JSONObject data=new LoginJsonData().loginAdmin();
        HashMap<String, Object> myHeaders = new HashMap<>();
        String ret = "";
        try {
            JSONObject object = new JSONObject(new APIMethods().post(headers, data, 200, false).readEntity(String.class)).getJSONObject("data");
            ret = object.getString("access_token");
        }catch(Exception ignored){
        }
        return "Bearer "+ ret;
    }

    public String getValidUserTokenHeader()
    {
        JSONObjectAPI headers=DataHeaders("/accounts/jsonLogin");
        JSONObject data=new LoginJsonData().loginUser();
        String ret = "";
        try {
            JSONObject object = new JSONObject(new APIMethods().post(headers, data, 200, false).readEntity(String.class)).getJSONObject("data");
            ret = object.getString("access_token");
        }catch(Exception ignored){
        }
        return "Bearer "+ ret;
    }

    public String getInvalidTokenHeader()
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
        return invalidToken;
    }
    public String getExpiredAdminTokenHeader()
    {
        JSONObjectAPI headers=DataHeaders("/accounts/jsonLogin");
        JSONObject data=new LoginJsonData().loginAdmin();
        String ret = "";
        try {
            JSONObject object = new JSONObject(new APIMethods().post(headers, data,
                    200, false).readEntity(String.class)).getJSONObject("data");
            new JSONObject(new APIMethods().post(headers, data,200, false));
            ret = object.getString("access_token");
        }catch(Exception ignored){
        }
        return "Bearer "+ ret;
    }
}
