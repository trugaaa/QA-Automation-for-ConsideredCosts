package api;

import api.body.json.add.*;
import api.body.json.edit.EditIncomeData;
import api.body.json.edit.EditItemData;
import api.body.json.edit.EditOutgoeData;
import api.body.json.expected.RegistrationResponse;
import api.data.ApiData;
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
    }

    @DELETE
    @Path("/Test/ClearDb")
    @Test(groups = "Test")
    public void deleteDB() {
        Response res = new APIMethods().delete(apiData.DataHeaders("/Test/ClearDb"));
        assertEquals(res.getStatus(),200);
       new APIMethods().getNL(apiData.DataHeadersToken("/items","User"));
       new APIMethods().getNL(apiData.DataHeaders("/currencies"));
    }

    @POST
    @Path("/accounts/registration")
    @Test(groups = "Account")
    public void registrationSuccess() {
        Response res = new APIMethods().post(apiData.DataHeaders("/accounts/registration"),regData.dataForReg());
        assertEquals(res.getStatus(),200);
        assertTrue(keyValidation(res,registrationResponse.registrationSuccessExpected()));
    }

    @POST
    @Path("/accounts/jsonLogin")
    @Test(groups = "Account",priority = 1)
    public void loginAdminSuccess() {
        Response res = new APIMethods().post(apiData.DataHeaders("/accounts/jsonLogin"),loginJsonData.loginAdmin());
        assertEquals(res.getStatus(),200);
    }

    @POST
    @Path("/accounts/jsonLogin")
    @Test(groups = "Account",priority = 1)
    public void loginUserSuccess() {
        Response res = new APIMethods().post(apiData.DataHeaders("/accounts/jsonLogin"),loginJsonData.loginUser());
        assertEquals(res.getStatus(),200);
    }

    @POST
    @Path("/accounts/jsonLogin")
    @Test(groups = "Account",priority = 1)
    public void loginIncorrectPass() {
        Response res = new APIMethods().post(
                apiData.DataHeaders("/accounts/jsonLogin"),loginJsonData.loginIncorrectPass());
        assertEquals(res.getStatus(),401);
    }

    @GET
    @Path("/accounts")
    @Test(groups = "Account",priority = 2)
    public void gettingAllUsers() {
        Response res = new APIMethods().get(apiData.DataHeadersToken("/accounts","Admin"));
        assertEquals(res.getStatus(),200);
    }

    @GET
    @Path("/accounts")
    @Test(groups = "Account", priority = 2)
    public void gettingAllUsersWithUserAccount() {
        Response res = new APIMethods().get(apiData.DataHeadersToken("/accounts","User"));
        assertEquals(res.getStatus(),403);
    }

    @GET
    @Path("/accounts")
    @Test(groups = "Account",priority = 2)
    public void gettingAllUsersWithInvalidToken() {
        Response res = new APIMethods().get(apiData.DataHeadersToken("/accounts","Invalid"));
        assertEquals(res.getStatus(),401);
    }

    @GET
    @Path("/accounts")
    @Test(groups = "Account",priority = 2)
    public void gettingNonExistentUser() {
        Response res = new APIMethods().get(apiData.DataHeadersTokenId("/accounts","Admin", 34444));
        assertEquals(res.getStatus(),404);
    }

    @GET
    @Path("/accounts")
    @Test(groups = "Account",priority = 3)
    public void gettingCurrenciesList() {
        Response res = new APIMethods().get(apiData.DataHeaders("/currencies"));
        assertEquals(res.getStatus(),200);
    }

    @POST
    @Path("/incomes")
    @Test(groups = "Income",priority = 3)
    public void savingIncome() {
        Response res = new APIMethods().post(apiData.DataHeadersToken("/incomes","User"),
                incomeData.correctIncome());
        assertEquals(res.getStatus(),200);
    }

    @POST
    @Path("/incomes")
    @Test(groups = "Income",priority = 3)
    public void savingIncomeWithBadRequest() {
        Response res = new APIMethods().post(apiData.DataHeadersToken("/incomes","User"),
                incomeData.incorrectIncomeMoneyString());
        assertEquals(res.getStatus(),400);
    }

    @POST
    @Path("/incomes")
    @Test(groups = "Income",priority = 3)
    public void savingIncomeWithInvalidToken() {
        Response res = new APIMethods().post(apiData.DataHeadersToken("/incomes","Invalid"),
                incomeData.correctIncome());
        assertEquals(res.getStatus(),401);
    }

    @DELETE
    @Path("/incomes")
    @Test(groups = "Income",priority = 6)
    public void deletingIncome() {
        Response res = new APIMethods().delete(apiData.DataHeadersTokenId("/incomes","User",1));
        assertEquals(res.getStatus(),200);
    }

    @DELETE
    @Path("/incomes")
    @Test(groups = "Income",priority = 6)
    public void deletingIncomeWithNoId() {
        Response res = new APIMethods().delete(apiData.DataHeadersToken("/incomes","Admin"));
        assertEquals(res.getStatus(),400);
    }

    @DELETE
    @Path("/incomes")
    @Test(groups = "Income",priority = 6)
    public void deletingIncomeWithInExistentId() {
        Response res = new APIMethods().delete(apiData.DataHeadersTokenId("/incomes","Admin",232332));
        assertEquals(res.getStatus(),404);
    }

    @DELETE
    @Path("/incomes")
    @Test(groups = "Income",priority = 6)
    public void deletingIncomeWithInvalidToken() {
        Response res = new APIMethods().delete(apiData.DataHeadersTokenId("/incomes","Invalid",1));
        assertEquals(res.getStatus(),401);
    }

    @PUT
    @Path("/incomes")
    @Test(groups = "Income",priority = 4)
    public void editIncome() {
        Response res = new APIMethods().put(apiData.DataHeadersToken("/incomes","User"),
                editIncomeData.correctIncome());
        assertEquals(res.getStatus(),200);
    }

    @PUT
    @Path("/incomes")
    @Test(groups = "Income",priority = 4)
    public void editIncomeWithBadRequest() {
        Response res = new APIMethods().put(apiData.DataHeadersToken("/incomes","User"),
                editIncomeData.incorrectIncomeMoneyString());
        assertEquals(res.getStatus(),400);
    }

    @PUT
    @Path("/incomes")
    @Test(groups = "Income",priority = 4)
    public void editInExistentIncome() {
        Response res = new APIMethods().put(apiData.DataHeadersToken("/incomes","Admin"),
                editIncomeData.inExistentIncome());
        assertEquals(res.getStatus(),400);
    }

    @PUT
    @Path("/incomes")
    @Test(groups = "Income",priority = 4)
    public void editIncomeWithInvalidToken() {
        Response res = new APIMethods().put(apiData.DataHeadersToken("/incomes","Invalid"),
                editIncomeData.correctIncome());
        assertEquals(res.getStatus(),401);
    }

    @GET
    @Path("/incomes")
    @Test(groups = "Income",priority = 5)
    public void getAllIncomesUser() {
        Response res = new APIMethods().get(apiData.DataHeadersToken("/incomes","User"));
        assertEquals(res.getStatus(),200);
    }

    @GET
    @Path("/incomes")
    @Test(groups = "Income",priority = 5)
    public void getIncomeByIdUser() {
        Response res = new APIMethods().get(apiData.DataHeadersTokenId("/incomes","User",1));
        assertEquals(res.getStatus(),200);
    }

    @GET
    @Path("/incomes")
    @Test(groups = "Income",priority = 5)
    public void getIncomeByInExistentIdUser() {
        Response res = new APIMethods().get(apiData.DataHeadersTokenId("/incomes","User",4332));
        assertEquals(res.getStatus(),404);
    }

    @GET
    @Path("/incomes")
    @Test(groups = "Income",priority = 5)
    public void getIncomesWithInvalidToken() {
        Response res = new APIMethods().get(apiData.DataHeadersToken("/incomes","Invalid"));
        assertEquals(res.getStatus(),401);
    }

    @POST
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 3)
    public void savingOutgoe() {
        Response res = new APIMethods().post(apiData.DataHeadersToken("/outgoes","User"),
                outgoeData.correctOutgoe());
        assertEquals(res.getStatus(),200);
    }

   @POST
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 3)
    public void savingOutgoeWithBadRequest() {
       Response res = new APIMethods().post(apiData.DataHeadersToken("/outgoes","User"),
                outgoeData.incorrectMoneyStringOutgoe());
       assertEquals(res.getStatus(),400);
    }

    @POST
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 3)
    public void savingOutgoeWithInvalidToken() {
        Response res = new APIMethods().post(apiData.DataHeadersToken("/outgoes","Invalid"),
                outgoeData.correctOutgoe());
        assertEquals(res.getStatus(),401);
    }

    @DELETE
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 6)
    public void deletingOutgoe() {
        Response res = new APIMethods().delete(apiData.DataHeadersTokenId("/outgoes","User",1));
        assertEquals(res.getStatus(),200);
    }

    @DELETE
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 6)
    public void deletingOutgoeWithNoId() {
        Response res = new APIMethods().delete(apiData.DataHeadersToken("/outgoes","Admin"));
        assertEquals(res.getStatus(),400);
    }

    @DELETE
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 6)
    public void deletingOutgoeWithInExistentId() {
        Response res = new APIMethods().delete(apiData.DataHeadersTokenId("/outgoes","Admin",3213123));
        assertEquals(res.getStatus(),404);
    }

    @DELETE
    @Path("/incomes")
    @Test(groups = "Outgoes",priority = 6)
    public void deletingOutgoeWithInvalidToken() {
        Response res = new APIMethods().delete(apiData.DataHeadersTokenId("/outgoes","Invalid",3));
        assertEquals(res.getStatus(),401);
    }

    @PUT
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 4)
    public void editOutgoe() {
        Response res = new APIMethods().put(apiData.DataHeadersToken("/outgoes","User"),
                editOutgoeData.correctOutgoe());
        assertEquals(res.getStatus(),200);
    }

    @PUT
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 4)
    public void editOutgoesWithBadRequest() {
        Response res = new APIMethods().put(apiData.DataHeadersToken("/outgoes","Admin"),
                editOutgoeData.incorrectMoneyStringOutgoe());
        assertEquals(res.getStatus(),400);
    }

    @PUT
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 4)
    public void editOutgoeWithInvalidToken() {
        Response res = new APIMethods().put(apiData.DataHeadersToken("/outgoes","Invalid"),
                editOutgoeData.correctOutgoe());
        assertEquals(res.getStatus(),401);
    }

    @PUT
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 4)
    public void editInExistentOutgoe() {
        Response res = new APIMethods().put(apiData.DataHeadersToken("/outgoes","User"),
                editOutgoeData.inExistentOutgoe());
        assertEquals(res.getStatus(), 404);
    }

    @GET
    @Path("/outgoes")
    @Test(groups = "Income",priority = 5)
    public void getOutgoesUser() {
        Response res = new APIMethods().get(apiData.DataHeadersToken("/outgoes","User"));
        assertEquals(res.getStatus(),200);
    }

    @GET
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 5)
    public void getOutgoeByIdUser() {
        Response res = new APIMethods().get(apiData.DataHeadersTokenId("/outgoes","User",1));
        assertEquals(res.getStatus(),200);
    }

    @GET
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 5)
    public void getOutgoesByInExistentIdUser() {
        Response res = new APIMethods().get(apiData.DataHeadersTokenId("/outgoes","User",1212));
        assertEquals(res.getStatus(),404);
    }

    @GET
    @Path("/outgoes")
    @Test(groups = "Outgoes",priority = 5)
    public void getOutgoesWithInvalidToken() {
        Response res = new APIMethods().get(apiData.DataHeadersToken("/outgoes","Invalid"));
        assertEquals(res.getStatus(),401);
    }

    @GET
    @Path("/items")
    @Test(groups = "Item",priority = 5)
    public void getItems() {
        Response res = new APIMethods().get(apiData.DataHeadersToken("/items","User"));
        assertEquals(res.getStatus(),200);
    }

    @GET
    @Path("/items")
    @Test(groups = "Item",priority = 5)
    public void getItemsById() {
        Response res = new APIMethods().get(apiData.DataHeadersTokenId("/items","User",1));
        assertEquals(res.getStatus(),200);
    }

    @GET
    @Path("/items")
    @Test(groups = "Item",priority = 5)
    public void getItemsByInExistentId() {
        Response res = new APIMethods().get(apiData.DataHeadersTokenId("/items","User",134));
        assertEquals(res.getStatus(),404);
    }

    @GET
    @Path("/items")
    @Test(groups = "Item",priority = 5)
    public void getItemsWithInvalidToken() {
        Response res = new APIMethods().get(apiData.DataHeadersTokenId("/items","Invalid",1));
        assertEquals(res.getStatus(),401);
    }

    @POST
    @Path("/items")
    @Test(groups = "Item",priority = 3)
    public void saveItem() {
        Response res = new APIMethods().post(apiData.DataHeadersToken("/items","User"),itemsData.correctItems());
        assertEquals(res.getStatus(),200);
    }

    @POST
    @Path("/items")
    @Test(groups = "Item",priority = 3)
    public void saveBlankItem() {
        Response res = new APIMethods().post(apiData.DataHeadersToken("/items","User"),itemsData.incorrectItems());
        assertEquals(res.getStatus(),400);
    }

    @POST
    @Path("/items")
    @Test(groups = "Item",priority = 3)
    public void saveItemWithoutBody() {
        Response res = new APIMethods().post(apiData.DataHeadersToken("/items","User"),itemsData.blankItems());
        assertEquals(res.getStatus(),400);
    }

    @POST
    @Path("/items")
    @Test(groups = "Item",priority = 3)
    public void saveSQLRequestInItem() {
        Response res = new APIMethods().post(apiData.DataHeadersToken("/items","User"),
                itemsData.incorrectItemsSQLRequest());
        assertEquals(res.getStatus(),400);
    }

    @POST
    @Path("/items")
    @Test(groups = "Item",priority = 3)
    public void saveItemWithIncorrectToken() {
        Response res = new APIMethods().post(apiData.DataHeadersToken("/items","Invalid"),
                itemsData.correctItems());
        assertEquals(res.getStatus(),401);
    }

    @PUT
    @Path("/items")
    @Test(groups = "Item",priority = 4)
    public void editItem() {
        Response res = new APIMethods().put(apiData.DataHeadersToken("/items","Users"),
                editItemData.correctItems());
        assertEquals(res.getStatus(),200);
    }

    @PUT
    @Path("/items")
    @Test(groups = "Item",priority = 4)
    public void editInExistentItem() {
        Response res = new APIMethods().put(apiData.DataHeadersToken("/items","Users"),
                editItemData.inExistentItem());
        assertEquals(res.getStatus(),404);
    }

    @PUT
    @Path("/items")
    @Test(groups = "Item",priority = 4)
    public void editItemsWithInvalidToken() {
        Response res = new APIMethods().put(apiData.DataHeadersToken("/items","Invalid"),
                editItemData.correctItems());
        assertEquals(res.getStatus(),401);
    }

    @DELETE
    @Path("/items")
    @Test(groups = "Item",priority = 6)
    public void deleteItem() {
        Response res = new APIMethods().delete(apiData.DataHeadersTokenId("/items","User",3));
        assertEquals(res.getStatus(),200);
    }

    @DELETE
    @Path("/items")
    @Test(groups = "Item",priority = 6)
    public void deleteInExistentItem() {
        Response res = new APIMethods().delete(apiData.DataHeadersTokenId("/items","User",3312));
        assertEquals(res.getStatus(),404);
    }

    @DELETE
    @Path("/items")
    @Test(groups = "Item",priority = 6)
    public void deleteItemWithIncorrectToken() {
        Response res = new APIMethods().delete(apiData.DataHeadersTokenId("/items","Invalid",2));
        assertEquals(res.getStatus(),401);
    }

    @GET
    @Path("/points")
    @Test(groups = "Screen",priority = 5)
    public void getPointsToIncomesAndOutgoes() {
        Response res = new APIMethods().get(apiData.DataHeadersPoints("/points","User",
                "incomesAndOutgoes",""));
        assertEquals(res.getStatus(),200);
    }

    @GET
    @Path("/points")
    @Test(groups = "Screen",priority = 5)
    public void getPointsToIncomesLastMonth() {
        Response res = new APIMethods().get(apiData.DataHeadersPoints("/points","User",
                "incomes","lastMonth"));
        assertEquals(res.getStatus(),200);
    }

    @GET
    @Path("/points")
    @Test(groups = "Screen",priority = 5)
    public void getPointsToItemsLastHalfYear() {
        Response res = new APIMethods().get(apiData.DataHeadersPoints("/points","User",
                "items","lastHalfYear"));
        assertEquals(res.getStatus(),200);
    }

    @GET
    @Path("/points")
    @Test(groups = "Screen",priority = 5)
    public void getPointsToOutgoesLastThreeMonths() {
        Response res = new APIMethods().get(apiData.DataHeadersPoints("/points","User",
                "outgoes","lastThreeMonth"));
        assertEquals(res.getStatus(),200);
    }

    @GET
    @Path("/points")
    @Test(groups = "Screen",priority = 5)
    public void getPointsToOutgoesBadPeriod() {
        Response res = new APIMethods().get(apiData.DataHeadersPoints("/points","User",
                "outgoes","last"));
        assertEquals(res.getStatus(),400);
    }

    @GET
    @Path("/points")
    @Test(groups = "Screen",priority = 5)
    public void getPointsBadType() {
        Response res = new APIMethods().get(apiData.DataHeadersPoints("/points","User",
                "outgoaes",""));
        assertEquals(res.getStatus(),400);
    }

    @GET
    @Path("/points")
    @Test(groups = "Screen",priority = 5)
    public void getPointsToIncomesAndOutgoesInvalidToken() {
        Response res = new APIMethods().get(apiData.DataHeadersPoints("/points", "Invalid",
                "incomesAndOutgoes", ""));
        assertEquals(res.getStatus(),401);
    }
}
