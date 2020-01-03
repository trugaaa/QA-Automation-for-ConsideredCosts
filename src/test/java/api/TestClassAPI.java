package api;

import api.body.json.add.*;
import api.body.json.edit.EditIncomeData;
import api.body.json.edit.EditItemData;
import api.body.json.edit.EditOutgoeData;
import api.body.json.expected.RegistrationResponse;
import api.data.ApiData;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static api.process.APIOperations.keyValidation;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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

    private RegistrationResponse registrationResponse;

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

        registrationResponse = new RegistrationResponse();

        new APIMethods().delete(apiData.DataHeaders("/Test/ClearDb"));
    }

    @POST
    @Path("/accounts/registration")
    @Test(groups = "Account")
    public void registrationSuccess() {
        Response res = new APIMethods().post(apiData.DataHeaders("/accounts/registration"),regData.dataForReg());
        assertEquals(res.getStatus(),200);
        assertTrue(keyValidation(res,registrationResponse.registrationSuccessExpected()));
    }
/*
    @POST
    @Path("/accounts/jsonLogin")
    @Test(groups = "Account",priority = 1)
    public void loginAdminSuccess() {
        assertEquals(new APIMethods().post(apiData.DataHeaders("/accounts/jsonLogin"),loginJsonData.loginAdmin(),
                200).getStatus(),200);
    }

    @POST
    @Path("/accounts/jsonLogin")
    @Test(groups = "Account",priority = 1)
    public void loginUserSuccess() {
        assertEquals(new APIMethods().post(apiData.DataHeaders("/accounts/jsonLogin"),loginJsonData.loginUser(),
                200,true).getStatus(),200);
    }

    @POST
    @Path("/accounts/jsonLogin")
    @Test(groups = "Account",priority = 1)
    public void loginIncorrectPass() {
        assertEquals(new APIMethods().post(apiData.DataHeaders("/accounts/jsonLogin"),loginJsonData.loginIncorrectPass(),
                401,true).getStatus(), 401);
    }

    @GET
    @Path("/accounts")
    @Test(groups = "Account",priority = 2)
    public void gettingAllUsersSuccess() {
        assertEquals(new APIMethods().get(apiData.DataHeadersToken("/accounts","Admin"),
                200,true).getStatus(), 200);
    }

    @GET
    @Path("/accounts")
    @Test(groups = "Account", priority = 2)
    public void gettingAllUsersWithUserAccount() {
        assertEquals(new APIMethods().get(apiData.DataHeadersToken("/accounts","User"),
                403,true).getStatus(), 403);
    }

    @GET
    @Path("/accounts")
    @Test(groups = "Account",priority = 2)
    public void gettingAllUsersWithInvalidToken() {
        assertEquals(new APIMethods().get(apiData.DataHeadersToken("/accounts","Invalid"),
                401,true).getStatus(), 401);
    }

    @GET
    @Path("/accounts")
    @Test(groups = "Account",priority = 2)
    public void gettingNonExistentUser() {
        assertEquals(new APIMethods().get(apiData.DataHeadersToken("/accounts/5000000","Admin"),
                404,true).getStatus(), 404);
    }

    @GET
    @Path("/accounts")
    @Test(groups = "Account",priority = 3)
    public void gettingCurrenciesList() {
        assertEquals(new APIMethods().get(apiData.DataHeaders("/currencies"),
                200,true).getStatus(), 200);
    }

    @POST
    @Path("/incomes")
    @Test(groups = "Income",priority = 3)
    public void savingIncome() {
        assertEquals(new APIMethods().post(apiData.DataHeadersToken("/incomes","User"),
                incomeData.correctIncome(),200,true).getStatus(),200);
    }

    @POST
    @Path("/incomes")
    @Test(groups = "Income",priority = 3)
    public void savingIncomeWithBadRequest() {
        assertEquals(new APIMethods().post(apiData.DataHeadersToken("/incomes","User"),
                incomeData.incorrectIncomeMoneyString(),400,true).getStatus(), 400);
    }

    @POST
    @Path("/incomes")
    @Test(groups = "Income",priority = 3)
    public void savingIncomeWithInvalidToken() {
        assertEquals(new APIMethods().post(apiData.DataHeadersToken("/incomes","Invalid"),
                incomeData.correctIncome(),401,true).getStatus(), 401);
    }

    @DELETE
    @Path("/incomes")
    @Test(groups = "Income",priority = 6)
    public void deletingIncome() {
        assertEquals(new APIMethods().delete(apiData.DataHeadersTokenId("/incomes","User",1),
                200,true).getStatus(), 200);
    }

    @DELETE
    @Path("/incomes")
    @Test(groups = "Income",priority = 6)
    public void deletingIncomeWithNoId() {
        assertEquals(new APIMethods().delete(apiData.DataHeadersToken("/incomes","Admin"),
                400,true).getStatus(), 400);
    }

    @DELETE
    @Path("/incomes")
    @Test(groups = "Income",priority = 6)
    public void deletingIncomeWithInExistentId() {
        assertEquals(new APIMethods().delete(apiData.DataHeadersTokenId("/incomes","Admin",232332),
                400,true).getStatus(), 400);
    }

    @DELETE
    @Path("/incomes")
    @Test(groups = "Income",priority = 6)
    public void deletingIncomeWithInvalidToken() {
        assertEquals(new APIMethods().delete(apiData.DataHeadersTokenId("/incomes","Invalid",1),
                401,true).getStatus(), 401);
    }

    @PUT
    @Path("/incomes")
    @Test(groups = "Income",priority = 4)
    public void editIncome() {
        assertEquals(new APIMethods().put(apiData.DataHeadersToken("/incomes","User"),
                editIncomeData.correctIncome(),200,true).getStatus(),200);
    }

    @PUT
    @Path("/incomes")
    @Test(groups = "Income",priority = 4)
    public void editIncomeWithBadRequest() {
        assertEquals(new APIMethods().put(apiData.DataHeadersToken("/incomes","User"),
                editIncomeData.incorrectIncomeMoneyString(),400,true).getStatus(), 400);
    }

    @PUT
    @Path("/incomes")
    @Test(groups = "Income",priority = 4)
    public void editInExistentIncome() {
        assertEquals(new APIMethods().put(apiData.DataHeadersToken("/incomes","User"),
                editIncomeData.inExistentIncome(),400,true).getStatus(), 400);
    }

    @PUT
    @Path("/incomes")
    @Test(groups = "Income",priority = 4)
    public void editIncomeWithInvalidToken() {
        assertEquals(new APIMethods().put(apiData.DataHeadersToken("/incomes","Invalid"),
                editIncomeData.correctIncome(),401,true).getStatus(), 401);
    }

    @GET
    @Path("/incomes")
    @Test(groups = "Income",priority = 5)
    public void getAllIncomesUser() {
        assertEquals(new APIMethods().get(apiData.DataHeadersToken("/incomes","User"),
                200,true).getStatus(),200);
    }

    @GET
    @Path("/incomes")
    @Test(groups = "Income",priority = 5)
    public void getIncomeByIdUser() {
        assertEquals(new APIMethods().get(apiData.DataHeadersTokenId("/incomes","User",1),
                200,true).getStatus(), 200);
    }

    @GET
    @Path("/incomes")
    @Test(groups = "Income",priority = 5)
    public void getIncomeByInExistentIdUser() {
        assertEquals(new APIMethods().get(apiData.DataHeadersTokenId("/incomes","User",4332)
                ,404,true).getStatus(), 404);
    }

    @GET
    @Path("/incomes")
    @Test(groups = "Income",priority = 5)
    public void getIncomesWithInvalidToken() {
        assertEquals(new APIMethods().get(apiData.DataHeadersToken("/incomes","Invalid"),
                401,true).getStatus(), 401);
    }

    @POST
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 3)
    public void savingOutgoe() {
        assertEquals(new APIMethods().post(apiData.DataHeadersToken("/outgoes","User"),
                outgoeData.correctOutgoe(),200,true).getStatus(),200);
    }

   @POST
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 3)
    public void savingOutgoeWithBadRequest() {
        assertEquals(new APIMethods().post(apiData.DataHeadersToken("/outgoes","User"),
                outgoeData.incorrectMoneyStringOutgoe(),400,true).getStatus(), 400);
    }

    @POST
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 3)
    public void savingOutgoeWithInvalidToken() {
        assertEquals(new APIMethods().post(apiData.DataHeadersToken("/outgoes","Invalid"),
                outgoeData.correctOutgoe(),401,true).getStatus(), 401);
    }

    @DELETE
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 6)
    public void deletingOutgoe() {
        assertEquals(new APIMethods().delete(apiData.DataHeadersTokenId("/outgoes","User",1),
                200,true).getStatus(), 200);
    }

    @DELETE
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 6)
    public void deletingOutgoeWithNoId() {
        assertEquals(new APIMethods().delete(apiData.DataHeadersToken("/outgoes","Admin"),
                400,true).getStatus(), 400);
    }

    @DELETE
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 6)
    public void deletingOutgoeWithInExistentId() {
        assertEquals(new APIMethods().delete(apiData.DataHeadersTokenId("/outgoes","Admin",3213123),
                400,true).getStatus(), 400);
    }

    @DELETE
    @Path("/incomes")
    @Test(groups = "Outgoes",priority = 6)
    public void deletingOutgoeWithInvalidToken() {
        assertEquals(new APIMethods().delete(apiData.DataHeadersTokenId("/outgoes","Invalid",3),
                401,true).getStatus(), 401);
    }

    @PUT
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 4)
    public void editOutgoe() {
        assertEquals(new APIMethods().put(apiData.DataHeadersToken("/outgoes","User"),
                editOutgoeData.correctOutgoe(),200,true).getStatus(),200);
    }

    @PUT
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 4)
    public void editOutgoesWithBadRequest() {
        assertEquals(new APIMethods().put(apiData.DataHeadersToken("/outgoes","Admin"),
                editOutgoeData.incorrectMoneyStringOutgoe(),400,true).getStatus(), 400);
    }

    @PUT
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 4)
    public void editOutgoeWithInvalidToken() {
        assertEquals(new APIMethods().put(apiData.DataHeadersToken("/outgoes","Invalid"),
                editOutgoeData.correctOutgoe(),401,true).getStatus(), 401);
    }

    @PUT
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 4)
    public void editInExistentOutgoe() {
        assertEquals(new APIMethods().put(apiData.DataHeadersToken("/outgoes","User"),
                editOutgoeData.inExistentOutgoe(),400,true).getStatus(), 400);
    }

    @GET
    @Path("/outgoes")
    @Test(groups = "Income",priority = 5)
    public void getOutgoesUser() {
        assertEquals(new APIMethods().get(apiData.DataHeadersToken("/outgoes","User"),
                200,true).getStatus(),200);
    }

    @GET
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 5)
    public void getOutgoeByIdUser() {
        assertEquals(new APIMethods().get(apiData.DataHeadersTokenId("/outgoes","User",1),
                200,true).getStatus(), 200);
    }

    @GET
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 5)
    public void getOutgoesByInExistentIdUser() {
        assertEquals(new APIMethods().get(apiData.DataHeadersTokenId("/outgoes","User",1212),
                404,true).getStatus(), 404);
    }

    @GET
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 5)
    public void getOutgoesWithInvalidToken() {
        assertEquals(new APIMethods().get(apiData.DataHeadersToken("/outgoes","Invalid"),
                401,true).getStatus(), 401);
    }


    @GET
    @Path("/items")
    @Test(groups = "Item",priority = 2)
    public void getItemsList() {
        assertEquals(new APIMethods().get(apiData.DataHeadersToken("/items","User"),
                200,true).getStatus(), 200);
    }

    @GET
    @Path("/items")
    @Test(groups = "Item",priority = 5)
    public void getItems() {
        assertEquals(new APIMethods().get(apiData.DataHeadersToken("/items","User"),
                200,true).getStatus(), 200);
    }

    @GET
    @Path("/items")
    @Test(groups = "Item",priority = 5)
    public void getItemsById() {
        assertEquals(new APIMethods().get(apiData.DataHeadersTokenId("/items","User",1),
                200,true).getStatus(), 200);
    }

    @GET
    @Path("/items")
    @Test(groups = "Item",priority = 5)
    public void getItemsByInExistentId() {
        assertEquals(new APIMethods().get(apiData.DataHeadersTokenId("/items","User",134),
                404,true).getStatus(), 404);
    }

    @GET
    @Path("/items")
    @Test(groups = "Item",priority = 5)
    public void getItemsWithInvalidToken() {
        assertEquals(new APIMethods().get(apiData.DataHeadersTokenId("/items","Invalid",1),
                401,true).getStatus(), 401);
    }

    @POST
    @Path("/items")
    @Test(groups = "Item",priority = 3)
    public void saveItem() {
        assertEquals(new APIMethods().post(apiData.DataHeadersToken("/items","User"),itemsData.correctItems(),
                200,true).getStatus(), 200);
    }

    @POST
    @Path("/items")
    @Test(groups = "Item",priority = 3)
    public void saveBlankItem() {
        assertEquals(new APIMethods().post(apiData.DataHeadersToken("/items","User"),itemsData.incorrectItems(),
                400,true).getStatus(), 400);
    }

    @POST
    @Path("/items")
    @Test(groups = "Item",priority = 3)
    public void saveSQLRequestInItem() {
        assertEquals(new APIMethods().post(apiData.DataHeadersToken("/items","User"),
                itemsData.incorrectItemsSQLRequest(),400,true).getStatus(), 400);
    }

    @POST
    @Path("/items")
    @Test(groups = "Item",priority = 3)
    public void saveItemWithIncorrectToken() {
        assertEquals(new APIMethods().post(apiData.DataHeadersToken("/items","Invalid"),
                itemsData.correctItems(),401,true).getStatus(), 401);
    }

    @PUT
    @Path("/items")
    @Test(groups = "Item",priority = 4)
    public void editItem() {
        assertEquals(new APIMethods().put(apiData.DataHeadersToken("/items","Users"),
                editItemData.correctItems(),200,true).getStatus(), 200);
    }

    @PUT
    @Path("/items")
    @Test(groups = "Item",priority = 4)
    public void editInExistentItem() {
        assertEquals(new APIMethods().put(apiData.DataHeadersToken("/items","Users"),
                editItemData.inExistentItem(),400,true).getStatus(), 400);
    }

    @PUT
    @Path("/items")
    @Test(groups = "Item",priority = 4)
    public void editItemsWithInvalidToken() {
        assertEquals(new APIMethods().put(apiData.DataHeadersToken("/items","Invalid"),
                editItemData.correctItems(),401,true).getStatus(), 401);
    }

    @DELETE
    @Path("/items")
    @Test(groups = "Item",priority = 6)
    public void deleteItem() {
        assertEquals(new APIMethods().delete(apiData.DataHeadersTokenId("/items","User",3),
                200,true).getStatus(), 200);
    }

    @DELETE
    @Path("/items")
    @Test(groups = "Item",priority = 6)
    public void deleteInExistentItem() {
        assertEquals(new APIMethods().delete(apiData.DataHeadersTokenId("/items","User",3312),
                400,true).getStatus(), 400);
    }

    @DELETE
    @Path("/items")
    @Test(groups = "Item",priority = 6)
    public void deleteItemWithIncorrectToken() {
        assertEquals(new APIMethods().delete(apiData.DataHeadersTokenId("/items","Invalid",2),
                401,true).getStatus(), 401);
    }

    @GET
    @Path("/points")
    @Test(groups = "Screen",priority = 5)
    public void getPointsToIncomesAndOutgoes() {
        assertEquals(new APIMethods().get(apiData.DataHeadersPoints("/points","User",
                "incomesAndOutgoes",""),200,true).getStatus(), 200);
    }

    @GET
    @Path("/points")
    @Test(groups = "Screen",priority = 5)
    public void getPointsToIncomesLastMonth() {
        assertEquals(new APIMethods().get(apiData.DataHeadersPoints("/points","User",
                "incomes","lastMonth"),200,true).getStatus(), 200);
    }

    @GET
    @Path("/points")
    @Test(groups = "Screen",priority = 5)
    public void getPointsToItemsLastHalfYear() {
        assertEquals(new APIMethods().get(apiData.DataHeadersPoints("/points","User",
                "items","lastHalfYear"),200,true).getStatus(), 200);
    }

    @GET
    @Path("/points")
    @Test(groups = "Screen",priority = 5)
    public void getPointsToOutgoesLastThreeMonths() {
        assertEquals(new APIMethods().get(apiData.DataHeadersPoints("/points","User",
                "outgoes","lastThreeMonth"),200,true).getStatus(), 200);
    }

    @GET
    @Path("/points")
    @Test(groups = "Screen",priority = 5)
    public void getPointsToOutgoesBadPeriod() {
        assertEquals(new APIMethods().get(apiData.DataHeadersPoints("/points","User",
                "outgoes","last"),400,true).getStatus(), 400);
    }

    @GET
    @Path("/points")
    @Test(groups = "Screen",priority = 5)
    public void getPointsBadType() {
        assertEquals(new APIMethods().get(apiData.DataHeadersPoints("/points","User",
                "outgoaes",""),400,true).getStatus(), 400);
    }

    @GET
    @Path("/points")
    @Test(groups = "Screen",priority = 5)
    public void getPointsToIncomesAndOutgoesInvalidToken() {
        assertEquals(new APIMethods().get(apiData.DataHeadersPoints("/points","Invalid",
                "incomesAndOutgoes",""),401,true).getStatus(), 401);
    }
*/
}
