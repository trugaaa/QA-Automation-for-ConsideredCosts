package api.body.json;

import org.json.JSONObject;

public class IncomeData {

    public JSONObject correctIncome()
    {
        JSONObject body = new JSONObject();
        body.put("workType","Salary"); //Salary/Business/PartWork
        body.put("money",1000);
        body.put("incomeType","Family");//Family/Private
        body.put("description","Get a salary");
        body.put("date","2019-12-15");
        body.put("currencyId",1);
        return body;
    }
    public JSONObject incorrectIncomeMoneyString()
    {
        JSONObject body = new JSONObject();
        body.put("workType","Salary"); //Salary/Business/PartWork
        body.put("money","saas");
        body.put("incomeType","Family");//Family/Private
        body.put("description","Get a salary");
        body.put("date","2019-12-15");
        body.put("currencyId",1);
        return body;
    }

}
