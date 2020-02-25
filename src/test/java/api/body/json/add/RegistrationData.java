package api.body.json.add;


import org.json.JSONObject;

public class RegistrationData {

    /**
     * DATA FOR 200 status
     **/
    public JSONObject dataForReg()
{
    JSONObject reg = new JSONObject();
    reg.put("userName","az09a.A-Z_K");
    reg.put("password","9421ZZ1249zz");
    reg.put("firstName","Andrey");
    reg.put("lastName","Kolesnyk");
    reg.put("email","trugaaa.andrey@gmail.com");
    return reg;
}

    /**
     * DATA FOR 400 status
     **/
    public JSONObject dataForRegIncorrectPin()
    {
        JSONObject reg = new JSONObject();
        reg.put("userName","az09a.A-Z_aK");
        reg.put("password","9421zz1249zz");
        reg.put("firstName","Andrey");
        reg.put("lastName","Kolesnyk");
        reg.put("email","trugaaa.andarey@gmail.com");
        return reg;
    }

    /**
     * DATA FOR 400 status
     **/
    public JSONObject dataForRegInUniqueEmail()
    {
        JSONObject reg = new JSONObject();
        reg.put("userName","trugaaa1");
        reg.put("password","9421ZZ1249zz");
        reg.put("firstName","Andrey");
        reg.put("lastName","Kolesnyk");
        reg.put("email","trugaaa.andrey@gmail.com");
        return reg;
    }

    /**
     * DATA FOR 400 status
     **/
    public JSONObject dataForRegInUniqueUsername()
    {
        JSONObject reg = new JSONObject();
        reg.put("userName","az09a.A-Z_K");
        reg.put("password","9421ZZ1249zz");
        reg.put("firstName","Andrey");
        reg.put("lastName","Kolesnyk");
        reg.put("email","trugaaaaa.andrey@gmail.com");
        return reg;
    }

    /**
     * DATA FOR 400 status
     **/
    public JSONObject dataForRegIncorrectEmailDublicateCaseInsensitive()
    {
        JSONObject reg = new JSONObject();
        reg.put("userName","azaa09a.A-Z_K");
        reg.put("password","9421ZZ1249zz");
        reg.put("firstName","Andrey");
        reg.put("lastName","Kolesnyk");
        reg.put("email","TRUGAAA.andrey@gmail.com");
        return reg;
    }
}
