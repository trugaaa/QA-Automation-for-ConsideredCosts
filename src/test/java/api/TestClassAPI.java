package api;

import api.data.LoginJsonData;
import api.data.RegistrationData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

public class TestClassAPI {


    private RegistrationData regData;
    private LoginJsonData loginJsonData;

    @BeforeMethod
    public void init()
    {
        regData = new RegistrationData();
        loginJsonData = new LoginJsonData();
    }




    @DELETE
    @Path("/Test/deleteDB")
    @Test(groups = "TEST")
    public void deleteDBBeforeTests() {
        Assert.assertEquals(new APIMethods().delete("/Test/ClearDb",200).getStatus(),200);
    }

    @POST
    @Path("/accounts/registration")
    @Test(groups = "Account")
    public void registrationSuccess() {
        Assert.assertEquals(new APIMethods().post("/accounts/registration",regData.dataForReg(),200).getStatus(),200);
    }

    @POST
    @Path("/accounts/jsonLogin")
    @Test(groups = "Account")
    public void loginAdminSuccess() {
        Assert.assertEquals(new APIMethods().post("/accounts/jsonLogin",loginJsonData.loginAdmin(),200).getStatus(),200);
    }

    @POST
    @Path("/accounts/jsonLogin")
    @Test(groups = "Account", dependsOnMethods = "registrationSuccess")
    public void loginUserSuccess() {
        Assert.assertEquals(new APIMethods().post("/accounts/jsonLogin",loginJsonData.loginUser(),200).getStatus(),200);
    }

    @POST
    @Path("/accounts/jsonLogin")
    @Test(groups = "Account")
    public void loginIncorrectPass() {
        Assert.assertEquals(new APIMethods().post("/accounts/jsonLogin",loginJsonData.loginIncorrectPass(),401).getStatus(), 401);
    }
}
