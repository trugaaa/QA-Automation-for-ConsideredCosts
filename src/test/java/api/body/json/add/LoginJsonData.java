package api.body.json.add;

import org.json.JSONObject;

public class LoginJsonData {

    /**
     * DATA FOR 200 status
     **/
    public JSONObject loginAdmin()
    {
        JSONObject reg = new JSONObject();
        reg.put("userName","Admin");
        reg.put("password","Admin");
        return reg;
    }

    /**
     * DATA FOR 200 status
     **/
    public JSONObject loginUser()
    {
        JSONObject reg = new JSONObject();
        reg.put("userName","az09a.A-Z_K");
        reg.put("password","9421ZZ1249zz");
        return reg;
    }

    /**
     * DATA FOR 401 status
     **/
    public JSONObject loginIncorrectPass()
    {
        JSONObject reg = new JSONObject();
        reg.put("userName","az09a.A-Z_K");
        reg.put("password","9421zz1249zz");
        return reg;
    }

}
