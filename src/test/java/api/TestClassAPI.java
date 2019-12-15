package api;

import api.body.json.add.*;
import api.body.json.edit.EditIncomeData;
import api.body.json.edit.EditItemData;
import api.body.json.edit.EditOutgoeData;
import api.data.ApiData;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import javax.ws.rs.*;

public class TestClassAPI {

    private ApiData apiData;
    private RegistrationData regData;
    private LoginJsonData loginJsonData;
    private IncomeData incomeData;
    private OutgoeData outgoeData;
    private ItemsData itemsData;
    private EditItemData editItemData;
    private EditIncomeData editIncomeData;
    private EditOutgoeData editOutgoeData;

    @BeforeClass
    public void init()
    {
        apiData = new ApiData();
        regData = new RegistrationData();
        loginJsonData = new LoginJsonData();
        incomeData = new IncomeData();
        outgoeData = new OutgoeData();
        itemsData = new ItemsData();
        editItemData = new EditItemData();
        editIncomeData = new EditIncomeData();
        editOutgoeData = new EditOutgoeData();
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
    @Test(groups = "Account",priority = 1)
    public void loginAdminSuccess() {
        Assert.assertEquals(new APIMethods().post(apiData.DataHeaders("/accounts/jsonLogin"),loginJsonData.loginAdmin(),
                200,true).getStatus(),200);
    }

    @POST
    @Path("/accounts/jsonLogin")
    @Test(groups = "Account",priority = 1)
    public void loginUserSuccess() {
        Assert.assertEquals(new APIMethods().post(apiData.DataHeaders("/accounts/jsonLogin"),loginJsonData.loginUser(),
                200,true).getStatus(),200);
    }

    @POST
    @Path("/accounts/jsonLogin")
    @Test(groups = "Account",priority = 1)
    public void loginIncorrectPass() {
        Assert.assertEquals(new APIMethods().post(apiData.DataHeaders("/accounts/jsonLogin"),loginJsonData.loginIncorrectPass(),
                401,true).getStatus(), 401);
    }

    @GET
    @Path("/accounts")
    @Test(groups = "Account",priority = 2)
    public void gettingAllUsersSuccess() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersToken("/accounts","Admin"),
                200,true).getStatus(), 200);
    }

    @GET
    @Path("/accounts")
    @Test(groups = "Account", priority = 2)
    public void gettingAllUsersWithUserAccount() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersToken("/accounts","User"),
                403,true).getStatus(), 403);
    }

    @GET
    @Path("/accounts")
    @Test(groups = "Account",priority = 2)
    public void gettingAllUsersWithInvalidToken() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersToken("/accounts","Invalid"),
                401,true).getStatus(), 401);
    }

    @GET
    @Path("/accounts")
    @Test(groups = "Account",priority = 2)
    public void gettingNonExistentUser() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersToken("/accounts/5000000","Admin"),
                404,true).getStatus(), 404);
    }

    @GET
    @Path("/accounts")
    @Test(groups = "Account",priority = 3)
    public void gettingCurrenciesList() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeaders("/currencies"),
                200,true).getStatus(), 200);
    }

    @POST
    @Path("/incomes")
    @Test(groups = "Income",priority = 3)
    public void savingIncome() {
        Assert.assertEquals(new APIMethods().post(apiData.DataHeadersToken("/incomes","User"),
                incomeData.correctIncome(),200,true).getStatus(),200);
    }

    @POST
    @Path("/incomes")
    @Test(groups = "Income",priority = 3)
    public void savingIncomeWithBadRequest() {
        Assert.assertEquals(new APIMethods().post(apiData.DataHeadersToken("/incomes","User"),
                incomeData.incorrectIncomeMoneyString(),400,true).getStatus(), 400);
    }

    @POST
    @Path("/incomes")
    @Test(groups = "Income",priority = 3)
    public void savingIncomeWithInvalidToken() {
        Assert.assertEquals(new APIMethods().post(apiData.DataHeadersToken("/incomes","Invalid"),
                incomeData.correctIncome(),401,true).getStatus(), 401);
    }

    @DELETE
    @Path("/incomes")
    @Test(groups = "Income",priority = 6)
    public void deletingIncome() {
        Assert.assertEquals(new APIMethods().delete(apiData.DataHeadersTokenId("/incomes","User",1),
                200,true).getStatus(), 200);
    }

    @DELETE
    @Path("/incomes")
    @Test(groups = "Income",priority = 6)
    public void deletingIncomeWithNoId() {
        Assert.assertEquals(new APIMethods().delete(apiData.DataHeadersToken("/incomes","Admin"),
                400,true).getStatus(), 400);
    }

    @DELETE
    @Path("/incomes")
    @Test(groups = "Income",priority = 6)
    public void deletingIncomeWithInExistentId() {
        Assert.assertEquals(new APIMethods().delete(apiData.DataHeadersTokenId("/incomes","Admin",232332),
                404,true).getStatus(), 404);
    }

    @DELETE
    @Path("/incomes")
    @Test(groups = "Income",priority = 6)
    public void deletingIncomeWithInvalidToken() {
        Assert.assertEquals(new APIMethods().delete(apiData.DataHeadersTokenId("/incomes","Invalid",1),
                401,true).getStatus(), 401);
    }

    @PUT
    @Path("/incomes")
    @Test(groups = "Income",priority = 4)
    public void editIncome() {
        Assert.assertEquals(new APIMethods().put(apiData.DataHeadersToken("/incomes","User"),
                editIncomeData.correctIncome(),200,true).getStatus(),200);
    }

    @PUT
    @Path("/incomes")
    @Test(groups = "Income",priority = 4)
    public void editIncomeWithBadRequest() {
        Assert.assertEquals(new APIMethods().put(apiData.DataHeadersToken("/incomes","User"),
                editIncomeData.incorrectIncomeMoneyString(),400,true).getStatus(), 400);
    }

    @PUT
    @Path("/incomes")
    @Test(groups = "Income",priority = 4)
    public void editInExistentIncome() {
        Assert.assertEquals(new APIMethods().put(apiData.DataHeadersToken("/incomes","User"),
                editIncomeData.inExistentIncome(),404,true).getStatus(), 404);
    }

    @PUT
    @Path("/incomes")
    @Test(groups = "Income",priority = 4)
    public void editIncomeWithInvalidToken() {
        Assert.assertEquals(new APIMethods().put(apiData.DataHeadersToken("/incomes","Invalid"),
                editIncomeData.correctIncome(),401,true).getStatus(), 401);
    }

    @GET
    @Path("/incomes")
    @Test(groups = "Income",priority = 5)
    public void getAllIncomesUser() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersToken("/incomes","User"),
                200,true).getStatus(),200);
    }

    @GET
    @Path("/incomes")
    @Test(groups = "Income",priority = 5)
    public void getIncomeByIdUser() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersTokenId("/incomes","User",1),
                200,true).getStatus(), 200);
    }

    @GET
    @Path("/incomes")
    @Test(groups = "Income",priority = 5)
    public void getIncomeByInExistentIdUser() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersTokenId("/incomes","User",4332)
                ,404,true).getStatus(), 404);
    }

    @GET
    @Path("/incomes")
    @Test(groups = "Income",priority = 5)
    public void getIncomesWithInvalidToken() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersToken("/incomes","Invalid"),
                401,true).getStatus(), 401);
    }

    @POST
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 3)
    public void savingOutgoe() {
        Assert.assertEquals(new APIMethods().post(apiData.DataHeadersToken("/outgoes","User"),
                outgoeData.correctOutgoe(),200,true).getStatus(),200);
    }

   @POST
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 3)
    public void savingOutgoeWithBadRequest() {
        Assert.assertEquals(new APIMethods().post(apiData.DataHeadersToken("/outgoes","User"),
                outgoeData.incorrectMoneyStringOutgoe(),400,true).getStatus(), 400);
    }

    @POST
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 3)
    public void savingOutgoeWithInvalidToken() {
        Assert.assertEquals(new APIMethods().post(apiData.DataHeadersToken("/outgoes","Invalid"),
                outgoeData.correctOutgoe(),401,true).getStatus(), 401);
    }

    @DELETE
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 6)
    public void deletingOutgoe() {
        Assert.assertEquals(new APIMethods().delete(apiData.DataHeadersTokenId("/outgoes","User",1),
                200,true).getStatus(), 200);
    }

    @DELETE
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 6)
    public void deletingOutgoeWithNoId() {
        Assert.assertEquals(new APIMethods().delete(apiData.DataHeadersToken("/outgoes","Admin"),
                400,true).getStatus(), 400);
    }

    @DELETE
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 6)
    public void deletingOutgoeWithInExistentId() {
        Assert.assertEquals(new APIMethods().delete(apiData.DataHeadersTokenId("/outgoes","Admin",3213123),
                404,true).getStatus(), 404);
    }

    @DELETE
    @Path("/incomes")
    @Test(groups = "Outgoes",priority = 6)
    public void deletingOutgoeWithInvalidToken() {
        Assert.assertEquals(new APIMethods().delete(apiData.DataHeadersTokenId("/outgoes","Invalid",3),
                401,true).getStatus(), 401);
    }

    @PUT
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 4)
    public void editOutgoe() {
        Assert.assertEquals(new APIMethods().put(apiData.DataHeadersToken("/outgoes","User"),
                editOutgoeData.correctOutgoe(),200,true).getStatus(),200);
    }

    @PUT
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 4)
    public void editOutgoesWithBadRequest() {
        Assert.assertEquals(new APIMethods().put(apiData.DataHeadersToken("/outgoes","Admin"),
                editOutgoeData.incorrectMoneyStringOutgoe(),400,true).getStatus(), 400);
    }

    @PUT
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 4)
    public void editOutgoeWithInvalidToken() {
        Assert.assertEquals(new APIMethods().put(apiData.DataHeadersToken("/outgoes","Invalid"),
                editOutgoeData.correctOutgoe(),401,true).getStatus(), 401);
    }

    @PUT
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 4)
    public void editInExistentOutgoe() {
        Assert.assertEquals(new APIMethods().put(apiData.DataHeadersToken("/outgoes","User"),
                editOutgoeData.inExistentOutgoe(),404,true).getStatus(), 404);
    }

    @GET
    @Path("/outgoes")
    @Test(groups = "Income",priority = 5)
    public void getOutgoesUser() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersToken("/outgoes","User"),
                200,true).getStatus(),200);
    }

    @GET
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 5)
    public void getOutgoeByIdUser() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersTokenId("/outgoes","User",1),
                200,true).getStatus(), 200);
    }

    @GET
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 5)
    public void getOutgoesByInExistentIdUser() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersTokenId("/outgoes","User",1212),
                404,true).getStatus(), 404);
    }

    @GET
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 5)
    public void getOutgoesWithInvalidToken() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersToken("/outgoes","Invalid"),
                401,true).getStatus(), 401);
    }


    @GET
    @Path("/items")
    @Test(groups = "Item",priority = 2)
    public void getItemsList() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersToken("/items","User"),
                200,true).getStatus(), 200);
    }

    @GET
    @Path("/items")
    @Test(groups = "Item",priority = 5)
    public void getItems() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersToken("/items","User"),
                200,true).getStatus(), 200);
    }

    @GET
    @Path("/items")
    @Test(groups = "Item",priority = 5)
    public void getItemsById() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersTokenId("/items","User",1),
                200,true).getStatus(), 200);
    }

    @GET
    @Path("/items")
    @Test(groups = "Item",priority = 5)
    public void getItemsByInExistentId() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersTokenId("/items","User",134),
                404,true).getStatus(), 404);
    }

    @GET
    @Path("/items")
    @Test(groups = "Item",priority = 5)
    public void getItemsWithInvalidToken() {
        Assert.assertEquals(new APIMethods().get(apiData.DataHeadersTokenId("/items","Invalid",1),
                401,true).getStatus(), 401);
    }

    @POST
    @Path("/items")
    @Test(groups = "Item",priority = 3)
    public void saveItem() {
        Assert.assertEquals(new APIMethods().post(apiData.DataHeadersToken("/items","User"),itemsData.correctItems(),
                200,true).getStatus(), 200);
    }

    @POST
    @Path("/items")
    @Test(groups = "Item",priority = 3)
    public void saveBlankItem() {
        Assert.assertEquals(new APIMethods().post(apiData.DataHeadersToken("/items","User"),itemsData.incorrectItems(),
                400,true).getStatus(), 400);
    }

    @POST
    @Path("/items")
    @Test(groups = "Item",priority = 3)
    public void saveSQLRequestInItem() {
        Assert.assertEquals(new APIMethods().post(apiData.DataHeadersToken("/items","User"),
                itemsData.incorrectItemsSQLRequest(),400,true).getStatus(), 400);
    }

    @POST
    @Path("/items")
    @Test(groups = "Item",priority = 3)
    public void saveItemWithIncorrectToken() {
        Assert.assertEquals(new APIMethods().post(apiData.DataHeadersToken("/items","Invalid"),
                itemsData.correctItems(),401,true).getStatus(), 401);
    }

    @PUT
    @Path("/items")
    @Test(groups = "Item",priority = 4)
    public void editItem() {
        Assert.assertEquals(new APIMethods().put(apiData.DataHeadersToken("/items","Users"),
                editItemData.correctItems(),200,true).getStatus(), 200);
    }

    @PUT
    @Path("/items")
    @Test(groups = "Item",priority = 4)
    public void editInExistentItem() {
        Assert.assertEquals(new APIMethods().put(apiData.DataHeadersToken("/items","Users"),
                editItemData.inExistentItem(),404,true).getStatus(), 404);
    }

    @PUT
    @Path("/items")
    @Test(groups = "Item",priority = 4)
    public void editItemsWithInvalidToken() {
        Assert.assertEquals(new APIMethods().put(apiData.DataHeadersToken("/items","Invalid"),
                editItemData.correctItems(),401,true).getStatus(), 401);
    }

    @DELETE
    @Path("/items")
    @Test(groups = "Item",priority = 6)
    public void deleteItem() {
        Assert.assertEquals(new APIMethods().delete(apiData.DataHeadersTokenId("/items","User",3),
                200,true).getStatus(), 200);
    }

    @DELETE
    @Path("/items")
    @Test(groups = "Item",priority = 6)
    public void deleteInExistentItem() {
        Assert.assertEquals(new APIMethods().delete(apiData.DataHeadersTokenId("/items","User",3312),
                404,true).getStatus(), 404);
    }

    @DELETE
    @Path("/items")
    @Test(groups = "Item",priority = 6)
    public void deleteItemWithIncorrectToken() {
        Assert.assertEquals(new APIMethods().delete(apiData.DataHeadersTokenId("/items","Invalid",2),
                401,true).getStatus(), 401);
    }
}
