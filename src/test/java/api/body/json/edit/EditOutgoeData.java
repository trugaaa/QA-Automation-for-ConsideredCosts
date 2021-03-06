package api.body.json.edit;

import org.json.JSONObject;

public class EditOutgoeData {

    /**
     * DATA FOR 200 status
     **/
    public JSONObject correctOutgoe()
    {
        JSONObject body = new JSONObject();
        body.put("id",1);
        body.put("money",2222);
        body.put("type","Family");//Family/Private
        body.put("description","Get a salary");
        body.put("itemId",1);
        body.put("date","2019-12-15");
        body.put("currencyId",1);
        return body;
    }

    /**
     * DATA FOR 400 status
     **/
    public JSONObject incorrectMoneyStringOutgoe()
    {
        JSONObject body = new JSONObject();
        body.put("id",1);
        body.put("money","sdadaad");
        body.put("type","Family");//Family/Private
        body.put("description","Get a salary");
        body.put("itemId",1);
        body.put("date","2019-12-15");
        body.put("currencyId",1);
        return body;
    }

    /**
     * DATA FOR 404 status
     **/
    public JSONObject inExistentOutgoe()
    {
        JSONObject body = new JSONObject();
        body.put("id",1211);
        body.put("money",2121);
        body.put("type","Family");//Family/Private
        body.put("description","Get a salary");
        body.put("itemId",1);
        body.put("date","2019-12-15");
        body.put("currencyId",1);
        return body;
    }
}
