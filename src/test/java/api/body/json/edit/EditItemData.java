package api.body.json.edit;

import org.json.JSONObject;

public class EditItemData {

    /**
     * DATA FOR 200 status
     **/
    public JSONObject correctItems()
    {
        JSONObject body = new JSONObject();
        body.put("id","3");
        body.put("type","Car Details");
        return body;
    }

    /**
     * DATA FOR 404 status
     **/
    public JSONObject inExistentItem()
    {
        JSONObject body = new JSONObject();
        body.put("id","333");
        body.put("type","adadds");
        return body;
    }
}
