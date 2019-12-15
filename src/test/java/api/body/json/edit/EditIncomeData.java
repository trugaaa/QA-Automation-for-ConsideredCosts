package api.body.json.edit;

import org.json.JSONObject;

public class EditIncomeData {

    /**
     * DATA FOR 200 status
     **/
    public JSONObject correctIncome()
    {
        JSONObject body = new JSONObject();
        body.put("id",1);
        body.put("workType","Salary"); //Salary/Business/PartWork
        body.put("money",2000);
        body.put("incomeType","Family");//Family/Private
        body.put("description","Get a salary");
        body.put("date","2019-12-15");
        body.put("currencyId",1);
        return body;
    }

    /**
     * DATA FOR 400 status
     **/
    public JSONObject incorrectIncomeMoneyString()
    {
        JSONObject body = new JSONObject();
        body.put("id",1);
        body.put("workType","Salary"); //Salary/Business/PartWork
        body.put("money","saas");
        body.put("incomeType","Family");//Family/Private
        body.put("description","Get a salary");
        body.put("date","2019-12-15");
        body.put("currencyId",1);
        return body;
    }

    /**
     * DATA FOR 404 status
     **/
    public JSONObject inExistentIncome()
    {
        JSONObject body = new JSONObject();
        body.put("id",321);
        body.put("workType","Salary"); //Salary/Business/PartWork
        body.put("money",1000);
        body.put("incomeType","Family");//Family/Private
        body.put("description","Get a salary");
        body.put("date","2019-12-15");
        body.put("currencyId",1);
        return body;
    }
}
