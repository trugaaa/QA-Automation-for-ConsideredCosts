package api.data;

import api.APIMethods;
import api.body.json.add.LoginJsonData;
import io.qameta.allure.Step;
import org.json.JSONObject;
import javax.ws.rs.core.MediaType;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;


public class ApiData {

    @Step("Headers building")
    public JSONObjectAPI DataHeaders(String endpoint) {
        JSONObjectAPI apiHeaders = new JSONObjectAPI();
        apiHeaders.putAccept(MediaType.APPLICATION_JSON);
        apiHeaders.putRequest(MediaType.APPLICATION_JSON);
        apiHeaders.putEndpoint(endpoint);
        return apiHeaders;
    }

    public JSONObjectAPI DataHeadersToken(String endpoint, String tokenType) {
        HashMap<String,Object> headersMap = new HashMap<>();
        JSONObjectAPI apiHeaders = new ApiData().DataHeaders(endpoint);
        headersMap.put("Authorization", new ApiData().settingToken(tokenType));
        apiHeaders.putHeaders(headersMap);
        return apiHeaders;
    }

    public JSONObjectAPI DataHeadersTokenId(String endpoint, String tokenType,Object id) {
        HashMap<String,Object> headersMap = new HashMap<>();
        JSONObjectAPI apiHeaders = new ApiData().DataHeaders(endpoint);
        headersMap.put("id",id);
        headersMap.put("Authorization", new ApiData().settingToken(tokenType));
        apiHeaders.putHeaders(headersMap);
        return apiHeaders;
    }

    public JSONObjectAPI DataHeadersPoints(String endpoint, String tokenType,Object type,Object period) {
        HashMap<String,Object> headersMap = new HashMap<>();
        headersMap.put("type",type);
        headersMap.put("period",period);
        JSONObjectAPI apiHeaders = new ApiData().DataHeaders(endpoint);
        headersMap.put("Authorization", new ApiData().settingToken(tokenType));
        apiHeaders.putHeaders(headersMap);
        return apiHeaders;
    }

    public String settingToken(String tokenType)
    {
        switch (tokenType) {
            case "Admin":
                return getValidAdminTokenHeader();
            case "User":
                return getValidUserTokenHeader();
            case "Invalid":
                return getInvalidTokenHeader();
            default:
                return getExpiredAdminTokenHeader();
        }
    }
    @Step("Admin token getting")
    public String getValidAdminTokenHeader()
    {
        JSONObjectAPI headers=DataHeaders("/accounts/jsonLogin");
        JSONObject data=new LoginJsonData().loginAdmin();
        String ret = "";
        try {
            JSONObject object = new JSONObject(new APIMethods().postNL(headers, data).readEntity(String.class)).getJSONObject("data");
            ret = object.getString("access_token");
        }catch(Exception ignored){
        }
        return "Bearer "+ ret;
    }

    @Step("User token getting")
    public String getValidUserTokenHeader()
    {
        JSONObjectAPI headers=DataHeaders("/accounts/jsonLogin");
        JSONObject data=new LoginJsonData().loginUser();
        String ret = "";
        try {
            JSONObject object = new JSONObject(new APIMethods().postNL(headers, data).readEntity(String.class)).getJSONObject("data");
            ret = object.getString("access_token");
        }catch(Exception ignored){
        }
        return "Bearer "+ ret;
    }


    @Step("Invalid token getting")
    public String getInvalidTokenHeader()
    {
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


    @Step("Expired token getting")
    public String getExpiredAdminTokenHeader()
    {
        JSONObjectAPI headers=DataHeaders("/accounts/jsonLogin");
        JSONObject data=new LoginJsonData().loginAdmin();
        String ret = "";
        try {
            JSONObject object = new JSONObject(new APIMethods().postNL(headers, data).readEntity(String.class)).getJSONObject("data");
            new JSONObject(new APIMethods().postNL(headers, data));
            ret = object.getString("access_token");
        }catch(Exception ignored){
        }
        return "Bearer "+ ret;
    }
}
