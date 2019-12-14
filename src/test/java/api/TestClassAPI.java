package api;

import api.data.ApiData;
import api.body.json.LoginJsonData;
import api.body.json.RegistrationData;
import api.process.APIOperations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.*;

public class TestClassAPI {

    ApiData apiData;
    private RegistrationData regData;
    private LoginJsonData loginJsonData;

    @BeforeClass
    public void initOnce()
    {
    apiData = new ApiData();
    }

    @BeforeMethod
    public void init()
    {
        regData = new RegistrationData();
        loginJsonData = new LoginJsonData();
    }

    @DELETE
    @Path("/Test/ClearDb")
    @Test(groups = "TEST")
    public void deleteDBBeforeTests() {
        Assert.assertEquals(new APIMethods().delete(apiData.DataHeaders("/Test/ClearDb"),200,true).getStatus(),200);
    }


    @POST
    @Path("/accounts/registration")
    @Test(groups = "Account")
    public void registrationSuccess() {
        Assert.assertEquals(new APIMethods().post(apiData.DataHeaders("/accounts/registration"),regData.dataForReg(),200,true).getStatus(),200);
    }

    @POST
    @Path("/accounts/jsonLogin")
    @Test(groups = "Account")
    public void loginAdminSuccess() {
        Assert.assertEquals(new APIMethods().post(apiData.DataHeaders("/accounts/jsonLogin"),loginJsonData.loginAdmin(),200,true).getStatus(),200);




    }

    @POST
    @Path("/accounts/jsonLogin")
    @Test(groups = "Account", dependsOnMethods = "registrationSuccess")
    public void loginUserSuccess() {
        Assert.assertEquals(new APIMethods().post(apiData.DataHeaders("/accounts/jsonLogin"),loginJsonData.loginUser(),200,true).getStatus(),200);
    }

    @POST
    @Path("/accounts/jsonLogin")
    @Test(groups = "Account")
    public void loginIncorrectPass() {
        Assert.assertEquals(new APIMethods().post(apiData.DataHeaders("/accounts/jsonLogin"),loginJsonData.loginIncorrectPass(),401,true).getStatus(), 401);
    }

    @GET
    @Path("/accounts")
    @Test(groups = "Account")
    public void gettingAllUsersSuccess() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersToken("/accounts","Admin"),200,true).getStatus(), 200);
    }

    @GET
    @Path("/accounts")
    @Test(groups = "Account")
    public void gettingAllUsersWithUserAccount() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersToken("/accounts","User"),403,true).getStatus(), 403);
    }

    @GET
    @Path("/accounts")
    @Test(groups = "Account")
    public void gettingAllUsersWithInvalidToken() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersToken("/accounts","Invalid"),401,true).getStatus(), 401);
    }

    @GET
    @Path("/accounts")
    @Test(groups = "Account")
    public void gettingNonExistentUser() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersToken("/accounts/5000000","Admin"),404,true).getStatus(), 404);
    }

}
