package api.body.json.add;

import org.json.JSONObject;

public class ItemsData {
    public JSONObject correctItems()
    {
        JSONObject body = new JSONObject();
        body.put("type","Car");
        return body;
    }

    public JSONObject incorrectItems()
    {
        JSONObject body = new JSONObject();
        body.put("type","");
        return body;
    }

    public JSONObject blankItems()
    {
        JSONObject body = new JSONObject();
        return body;
    }

    public JSONObject incorrectItemsSQLRequest()
    {
        JSONObject body = new JSONObject();
        body.put("type","Car, select * from Items");
        return body;
    }
}
