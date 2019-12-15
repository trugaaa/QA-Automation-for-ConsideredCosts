package api;

import api.body.json.IncomeData;
import api.data.ApiData;
import api.body.json.LoginJsonData;
import api.body.json.RegistrationData;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import javax.ws.rs.*;

public class TestClassAPI {

    private ApiData apiData;
    private RegistrationData regData;
    private LoginJsonData loginJsonData;
    private IncomeData incomeData;

    @BeforeMethod
    public void init()
    {
        apiData = new ApiData();
        regData = new RegistrationData();
        loginJsonData = new LoginJsonData();
        incomeData = new IncomeData();

    }

    @DELETE
    @Path("/Test/ClearDb")
    @Test(groups = "TEST")
    public void deleteDBBeforeTests() {
        Assert.assertEquals(new APIMethods().delete(apiData.DataHeaders("/Test/ClearDb"),
                200,true).getStatus(),200);
    }

    @POST
    @Path("/accounts/registration")
    @Test(groups = "Account")
    public void registrationSuccess() {
        Assert.assertEquals(new APIMethods().post(apiData.DataHeaders("/accounts/registration"),regData.dataForReg(),
                200,true).getStatus(),200);
    }

    @POST
    @Path("/accounts/jsonLogin")
    @Test(groups = "Account")
    public void loginAdminSuccess() {
        Assert.assertEquals(new APIMethods().post(apiData.DataHeaders("/accounts/jsonLogin"),loginJsonData.loginAdmin(),
                200,true).getStatus(),200);
    }

    @POST
    @Path("/accounts/jsonLogin")
    @Test(groups = "Account", dependsOnMethods = "registrationSuccess")
    public void loginUserSuccess() {
        Assert.assertEquals(new APIMethods().post(apiData.DataHeaders("/accounts/jsonLogin"),loginJsonData.loginUser(),
                200,true).getStatus(),200);
    }

    @POST
    @Path("/accounts/jsonLogin")
    @Test(groups = "Account")
    public void loginIncorrectPass() {
        Assert.assertEquals(new APIMethods().post(apiData.DataHeaders("/accounts/jsonLogin"),loginJsonData.loginIncorrectPass(),
                401,true).getStatus(), 401);
    }

    @GET
    @Path("/accounts")
    @Test(groups = "Account")
    public void gettingAllUsersSuccess() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersToken("/accounts","Admin"),
                200,true).getStatus(), 200);
    }

    @GET
    @Path("/accounts")
    @Test(groups = "Account")
    public void gettingAllUsersWithUserAccount() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersToken("/accounts","User"),
                403,true).getStatus(), 403);
    }

    @GET
    @Path("/accounts")
    @Test(groups = "Account")
    public void gettingAllUsersWithInvalidToken() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersToken("/accounts","Invalid"),
                401,true).getStatus(), 401);
    }

    @GET
    @Path("/accounts")
    @Test(groups = "Account")
    public void gettingNonExistentUser() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersToken("/accounts/5000000","Admin"),
                404,true).getStatus(), 404);
    }

    @GET
    @Path("/accounts")
    @Test(groups = "Account")
    public void gettingCurrenciesList() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersToken("/currencies","Admin"),
                200,true).getStatus(), 200);
    }

    @POST
    @Path("/incomes")
    @Test(groups = "Income")
    public void savingIncome() {
        Assert.assertEquals(new APIMethods().post(apiData.DataHeadersToken("/incomes","User"),
                incomeData.correctIncome(),200,true).getStatus(),200);
    }

    @POST
    @Path("/incomes")
    @Test(groups = "Income")
    public void savingIncomeWithBadRequest() {
        Assert.assertEquals(new APIMethods().post(apiData.DataHeadersToken("/incomes","Admin"),
                incomeData.incorrectIncomeMoneyString(),400,true).getStatus(), 400);
    }

    @POST
    @Path("/incomes")
    @Test(groups = "Income")
    public void savingIncomeWithInvalidToken() {
        Assert.assertEquals(new APIMethods().post(apiData.DataHeadersToken("/incomes","Invalid"),
                incomeData.correctIncome(),401,true).getStatus(), 401);
    }

    @DELETE
    @Path("/incomes")
    @Test(groups = "Income",dependsOnMethods = "savingIncome")
    public void deletingIncome() {
        Assert.assertEquals(new APIMethods().delete(apiData.DataHeadersToken("/incomes/1","User"),
                200,true).getStatus(), 200);
    }

    @DELETE
    @Path("/incomes")
    @Test(groups = "Income")
    public void deletingIncomeWithNoId() {
        Assert.assertEquals(new APIMethods().delete(apiData.DataHeadersToken("/incomes","User"),
                400,true).getStatus(), 400);
    }

    @DELETE
    @Path("/incomes")
    @Test(groups = "Income")
    public void deletingIncomeWithInvalidToken() {
        Assert.assertEquals(new APIMethods().delete(apiData.DataHeadersToken("/incomes/1","Invalid"),
                401,true).getStatus(), 401);
    }
}
