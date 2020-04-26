package api;

import api.body.json.add.*;
import api.body.json.edit.EditIncomeData;
import api.body.json.edit.EditItemData;
import api.body.json.edit.EditOutgoeData;
import api.data.ApiData;
import api.data.Endpoints;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

import static org.testng.Assert.assertEquals;

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
    private Endpoints endpoints;


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
        endpoints = new Endpoints();

       new APIMethods().delete(apiData.DataHeaders(endpoints.clearDb));
       new APIMethods().getNL(apiData.DataHeadersToken(endpoints.items,"User"));
       new APIMethods().getNL(apiData.DataHeaders(endpoints.currencies));
    }

    @Test(groups = "Account")
    public void registrationSuccess() {
        Response res = new APIMethods().post(apiData.DataHeaders(endpoints.registration),regData.dataForReg());
        assertEquals(res.getStatus(),200);
    }

    @Test(groups = "Account")
    public void registrationWithIncorrectPin() {
        Response res = new APIMethods().post(apiData.DataHeaders(endpoints.registration),regData.dataForRegIncorrectPin());
        assertEquals(res.getStatus(),400);
    }

    @Test(groups = "Account")
    public void registrationWithIncorrectEmail() {
        Response res = new APIMethods().post(apiData.DataHeaders(endpoints.registration),regData.dataForRegInUniqueEmail());
        assertEquals(res.getStatus(),400);
    }

    @Test(groups = "Account")
    public void registrationWithInUniqueUsername() {
        Response res = new APIMethods().post(apiData.DataHeaders(endpoints.registration),regData.dataForRegInUniqueUsername());
        assertEquals(res.getStatus(),400);
    }

    @Test(groups = "Account")
    public void registrationWithInUniqueCaseSensitiveEmail() {
        Response res = new APIMethods().post(apiData.DataHeaders(endpoints.registration),regData.dataForRegIncorrectEmailDublicateCaseInsensitive());
        assertEquals(res.getStatus(),400);
    }

    @Test(groups = "Account",priority = 1)
    public void loginAdminSuccess() {
        Response res = new APIMethods().post(apiData.DataHeaders(endpoints.login),loginJsonData.loginAdmin());
        assertEquals(res.getStatus(),200);
    }

    @Test(groups = "Account",priority = 1)
    public void loginUserSuccess() {
        Response res = new APIMethods().post(apiData.DataHeaders(endpoints.login),loginJsonData.loginUser());
        assertEquals(res.getStatus(),200);
    }


    @Test(groups = "Account",priority = 1)
    public void loginIncorrectPass() {
        Response res = new APIMethods().post(
                apiData.DataHeaders(endpoints.login),loginJsonData.loginIncorrectPass());
        assertEquals(res.getStatus(),401);
    }


    @Test(groups = "Account",priority = 2)
    public void gettingAllUsers() {
        Response res = new APIMethods().get(apiData.DataHeadersToken(endpoints.accounts,"Admin"));
        assertEquals(res.getStatus(),200);
    }

    @Test(groups = "Account", priority = 2)
    public void gettingAllUsersWithUserAccount() {
        Response res = new APIMethods().get(apiData.DataHeadersToken(endpoints.accounts,"User"));
        assertEquals(res.getStatus(),403);
    }

    @Test(groups = "Account",priority = 2)
    public void gettingAllUsersWithInvalidToken() {
        Response res = new APIMethods().get(apiData.DataHeadersToken(endpoints.accounts,"Invalid"));
        assertEquals(res.getStatus(),401);
    }

    @Test(groups = "Account",priority = 2)
    public void gettingNonExistentUser() {
        Response res = new APIMethods().get(apiData.DataHeadersTokenId(endpoints.accounts,"Admin", 34444));
        assertEquals(res.getStatus(),404);
    }

    @Test(groups = "Account",priority = 3)
    public void gettingCurrenciesList() {
        Response res = new APIMethods().get(apiData.DataHeaders(endpoints.currencies));
        assertEquals(res.getStatus(),200);
    }

    @Test(groups = "Income",priority = 3)
    public void savingIncome() {
        Response res = new APIMethods().post(apiData.DataHeadersToken(endpoints.incomes,"User"),
                incomeData.correctIncome());
        assertEquals(res.getStatus(),200);
    }

    @Test(groups = "Income",priority = 3)
    public void savingIncomeWithBadRequest() {
        Response res = new APIMethods().post(apiData.DataHeadersToken(endpoints.incomes,"User"),
                incomeData.incorrectIncomeMoneyString());
        assertEquals(res.getStatus(),400);
    }

    @Test(groups = "Income",priority = 3)
    public void savingIncomeWithInvalidToken() {
        Response res = new APIMethods().post(apiData.DataHeadersToken(endpoints.incomes,"Invalid"),
                incomeData.correctIncome());
        assertEquals(res.getStatus(),401);
    }

    @Test(groups = "Income",priority = 6)
    public void deletingIncome() {
        Response res = new APIMethods().delete(apiData.DataHeadersTokenId(endpoints.incomes,"User",1));
        assertEquals(res.getStatus(),200);
    }

    @Test(groups = "Income",priority = 6)
    public void deletingIncomeWithNoId() {
        Response res = new APIMethods().delete(apiData.DataHeadersToken(endpoints.incomes,"Admin"));
        assertEquals(res.getStatus(),400);
    }

    @Test(groups = "Income",priority = 6)
    public void deletingIncomeWithInExistentId() {
        Response res = new APIMethods().delete(apiData.DataHeadersTokenId(endpoints.incomes,"Admin",232332));
        assertEquals(res.getStatus(),404);
    }

    @Test(groups = "Income",priority = 6)
    public void deletingIncomeWithInvalidToken() {
        Response res = new APIMethods().delete(apiData.DataHeadersTokenId(endpoints.incomes,"Invalid",1));
        assertEquals(res.getStatus(),401);
    }

    @Test(groups = "Income",priority = 4)
    public void editIncome() {
        Response res = new APIMethods().put(apiData.DataHeadersToken(endpoints.incomes,"User"),
                editIncomeData.correctIncome());
        assertEquals(res.getStatus(),200);
    }

    @Test(groups = "Income",priority = 4)
    public void editIncomeWithBadRequest() {
        Response res = new APIMethods().put(apiData.DataHeadersToken(endpoints.incomes,"User"),
                editIncomeData.incorrectIncomeMoneyString());
        assertEquals(res.getStatus(),400);
    }

    @Test(groups = "Income",priority = 4)
    public void editInExistentIncome() {
        Response res = new APIMethods().put(apiData.DataHeadersToken(endpoints.incomes,"Admin"),
                editIncomeData.inExistentIncome());
        assertEquals(res.getStatus(),400);
    }

    @Test(groups = "Income",priority = 4)
    public void editIncomeWithInvalidToken() {
        Response res = new APIMethods().put(apiData.DataHeadersToken(endpoints.incomes,"Invalid"),
                editIncomeData.correctIncome());
        assertEquals(res.getStatus(),401);
    }

    @Test(groups = "Income",priority = 5)
    public void getAllIncomesUser() {
        Response res = new APIMethods().get(apiData.DataHeadersToken(endpoints.incomes,"User"));
        assertEquals(res.getStatus(),200);
    }

    @Test(groups = "Income",priority = 5)
    public void getIncomeByIdUser() {
        Response res = new APIMethods().get(apiData.DataHeadersTokenId(endpoints.incomes,"User",1));
        assertEquals(res.getStatus(),200);
    }

    @Test(groups = "Income",priority = 5)
    public void getIncomeByInExistentIdUser() {
        Response res = new APIMethods().get(apiData.DataHeadersTokenId(endpoints.incomes,"User",4332));
        assertEquals(res.getStatus(),404);
    }

    @Test(groups = "Income",priority = 5)
    public void getIncomesWithInvalidToken() {
        Response res = new APIMethods().get(apiData.DataHeadersToken(endpoints.incomes,"Invalid"));
        assertEquals(res.getStatus(),401);
    }

    @Test(groups = "Outgoes",priority = 3)
    public void savingOutgoe() {
        Response res = new APIMethods().post(apiData.DataHeadersToken(endpoints.outgoes,"User"),
                outgoeData.correctOutgoe());
        assertEquals(res.getStatus(),200);
    }

    @Test(groups = "Outgoes",priority = 3)
    public void savingOutgoeWithBadRequest() {
       Response res = new APIMethods().post(apiData.DataHeadersToken(endpoints.outgoes,"User"),
                outgoeData.incorrectMoneyStringOutgoe());
       assertEquals(res.getStatus(),400);
    }

    @Test(groups = "Outgoes",priority = 3)
    public void savingOutgoWithInvalidToken() {
        Response res = new APIMethods().post(apiData.DataHeadersToken(endpoints.outgoes,"Invalid"),
                outgoeData.correctOutgoe());
        assertEquals(res.getStatus(),401);
    }

    @Test(groups = "Outgoes",priority = 6)
    public void deletingOutgo() {
        Response res = new APIMethods().delete(apiData.DataHeadersTokenId(endpoints.outgoes,"User",1));
        assertEquals(res.getStatus(),200);
    }

    @Test(groups = "Outgoes",priority = 6)
    public void deletingOutgoWithNoId() {
        Response res = new APIMethods().delete(apiData.DataHeadersToken(endpoints.outgoes,"Admin"));
        assertEquals(res.getStatus(),400);
    }

    @Test(groups = "Outgoes",priority = 6)
    public void deletingOutgoWithInExistentId() {
        Response res = new APIMethods().delete(apiData.DataHeadersTokenId(endpoints.outgoes,"Admin",3213123));
        assertEquals(res.getStatus(),404);
    }

    @Test(groups = "Outgoes",priority = 6)
    public void deletingOutgoWithInvalidToken() {
        Response res = new APIMethods().delete(apiData.DataHeadersTokenId(endpoints.outgoes,"Invalid",3));
        assertEquals(res.getStatus(),401);
    }

    @Test(groups = "Outgoes",priority = 4)
    public void editOutgo() {
        Response res = new APIMethods().put(apiData.DataHeadersToken(endpoints.outgoes,"User"),
                editOutgoeData.correctOutgoe());
        assertEquals(res.getStatus(),200);
    }

    @Test(groups = "Outgoes",priority = 4)
    public void editOutgoesWithBadRequest() {
        Response res = new APIMethods().put(apiData.DataHeadersToken(endpoints.outgoes,"Admin"),
                editOutgoeData.incorrectMoneyStringOutgoe());
        assertEquals(res.getStatus(),400);
    }

    @Test(groups = "Outgoes",priority = 4)
    public void editOutgoeWithInvalidToken() {
        Response res = new APIMethods().put(apiData.DataHeadersToken(endpoints.outgoes,"Invalid"),
                editOutgoeData.correctOutgoe());
        assertEquals(res.getStatus(),401);
    }

    @Test(groups = "Outgoes",priority = 4)
    public void editInExistentOutgoe() {
        Response res = new APIMethods().put(apiData.DataHeadersToken(endpoints.outgoes,"User"),
                editOutgoeData.inExistentOutgoe());
        assertEquals(res.getStatus(), 404);
    }

    @Test(groups = "Income",priority = 5)
    public void getOutgoesUser() {
        Response res = new APIMethods().get(apiData.DataHeadersToken(endpoints.outgoes,"User"));
        assertEquals(res.getStatus(),200);
    }

    @Test(groups = "Outgoes",priority = 5)
    public void getOutgoByIdUser() {
        Response res = new APIMethods().get(apiData.DataHeadersTokenId(endpoints.outgoes,"User",1));
        assertEquals(res.getStatus(),200);
    }

    @Test(groups = "Outgoes",priority = 5)
    public void getOutgoesByInExistentIdUser() {
        Response res = new APIMethods().get(apiData.DataHeadersTokenId(endpoints.outgoes,"User",1212));
        assertEquals(res.getStatus(),404);
    }

    @Test(groups = "Outgoes",priority = 5)
    public void getOutgoesWithInvalidToken() {
        Response res = new APIMethods().get(apiData.DataHeadersToken(endpoints.outgoes,"Invalid"));
        assertEquals(res.getStatus(),401);
    }

    @Test(groups = "Item",priority = 5)
    public void getItems() {
        Response res = new APIMethods().get(apiData.DataHeadersToken(endpoints.items,"User"));
        assertEquals(res.getStatus(),200);
    }

    @Test(groups = "Item",priority = 5)
    public void getItemsById() {
        Response res = new APIMethods().get(apiData.DataHeadersTokenId(endpoints.items,"User",1));
        assertEquals(res.getStatus(),200);
    }

    @Test(groups = "Item",priority = 5)
    public void getItemsByInExistentId() {
        Response res = new APIMethods().get(apiData.DataHeadersTokenId(endpoints.items,"User",134));
        assertEquals(res.getStatus(),404);
    }

    @Test(groups = "Item",priority = 5)
    public void getItemsWithInvalidToken() {
        Response res = new APIMethods().get(apiData.DataHeadersTokenId(endpoints.items,"Invalid",1));
        assertEquals(res.getStatus(),401);
    }

    @Test(groups = "Item",priority = 3)
    public void saveItem() {
        Response res = new APIMethods().post(apiData.DataHeadersToken(endpoints.items,"User"),itemsData.correctItems());
        assertEquals(res.getStatus(),200);
    }

    @Test(groups = "Item",priority = 3)
    public void saveDublicateItem() {
        Response res = new APIMethods().post(apiData.DataHeadersToken(endpoints.items,"User"),itemsData.correctItems());
        assertEquals(res.getStatus(),400);
    }

    @Test(groups = "Item",priority = 3)
    public void saveBlankItem() {
        Response res = new APIMethods().post(apiData.DataHeadersToken(endpoints.items,"User"),itemsData.incorrectItems());
        assertEquals(res.getStatus(),400);
    }

    @Test(groups = "Item",priority = 3)
    public void saveItemWithoutBody() {
        Response res = new APIMethods().post(apiData.DataHeadersToken(endpoints.items,"User"),itemsData.blankItems());
        assertEquals(res.getStatus(),400);
    }

    @Test(groups = "Item",priority = 3)
    public void saveSQLRequestInItem() {
        Response res = new APIMethods().post(apiData.DataHeadersToken(endpoints.items,"User"),
                itemsData.incorrectItemsSQLRequest());
        assertEquals(res.getStatus(),400);
    }

    @Test(groups = "Item",priority = 3)
    public void saveItemWithIncorrectToken() {
        Response res = new APIMethods().post(apiData.DataHeadersToken(endpoints.items,"Invalid"),
                itemsData.correctItems());
        assertEquals(res.getStatus(),401);
    }

    @Test(groups = "Item",priority = 4)
    public void editItem() {
        Response res = new APIMethods().put(apiData.DataHeadersToken(endpoints.items,"Users"),
                editItemData.correctItems());
        assertEquals(res.getStatus(),200);
    }

    @Test(groups = "Item",priority = 4)
    public void editInExistentItem() {
        Response res = new APIMethods().put(apiData.DataHeadersToken(endpoints.items,"Users"),
                editItemData.inExistentItem());
        assertEquals(res.getStatus(),404);
    }

    @Test(groups = "Item",priority = 4)
    public void editItemsWithInvalidToken() {
        Response res = new APIMethods().put(apiData.DataHeadersToken(endpoints.items,"Invalid"),
                editItemData.correctItems());
        assertEquals(res.getStatus(),401);
    }

    @Test(groups = "Item",priority = 6)
    public void deleteItem() {
        Response res = new APIMethods().delete(apiData.DataHeadersTokenId(endpoints.items,"User",3));
        assertEquals(res.getStatus(),200);
    }

    @Test(groups = "Item",priority = 6)
    public void deleteInExistentItem() {
        Response res = new APIMethods().delete(apiData.DataHeadersTokenId(endpoints.items,"User",3312));
        assertEquals(res.getStatus(),404);
    }

    @Test(groups = "Item",priority = 6)
    public void deleteItemWithIncorrectToken() {
        Response res = new APIMethods().delete(apiData.DataHeadersTokenId(endpoints.items,"Invalid",2));
        assertEquals(res.getStatus(),401);
    }

    @Test(groups = "Screen",priority = 5)
    public void getPointsToIncomesAndOutgoes() {
        Response res = new APIMethods().get(apiData.DataHeadersPoints(endpoints.points,"User",
                "incomesAndOutgoes",""));
        assertEquals(res.getStatus(),200);
    }

    @Test(groups = "Screen",priority = 5)
    public void getPointsToIncomesLastMonth() {
        Response res = new APIMethods().get(apiData.DataHeadersPoints(endpoints.points,"User",
                "incomes","lastMonth"));
        assertEquals(res.getStatus(),200);
    }

    @Test(groups = "Screen",priority = 5)
    public void getPointsToItemsLastHalfYear() {
        Response res = new APIMethods().get(apiData.DataHeadersPoints(endpoints.points,"User",
                "items","lastHalfYear"));
        assertEquals(res.getStatus(),200);
    }

    @Test(groups = "Screen",priority = 5)
    public void getPointsToOutgoesLastThreeMonths() {
        Response res = new APIMethods().get(apiData.DataHeadersPoints(endpoints.points,"User",
                "outgoes","lastThreeMonth"));
        assertEquals(res.getStatus(),200);
    }

    @Test(groups = "Screen",priority = 5)
    public void getPointsToOutgoesBadPeriod() {
        Response res = new APIMethods().get(apiData.DataHeadersPoints(endpoints.points,"User",
                "outgoes","last"));
        assertEquals(res.getStatus(),400);
    }

    @Test(groups = "Screen",priority = 5)
    public void getPointsBadType() {
        Response res = new APIMethods().get(apiData.DataHeadersPoints(endpoints.points,"User",
                "outgoaes",""));
        assertEquals(res.getStatus(),400);
    }

    @Test(groups = "Screen",priority = 5)
    public void getPointsToIncomesAndOutgoesInvalidToken() {
        Response res = new APIMethods().get(apiData.DataHeadersPoints(endpoints.points, "Invalid",
                "incomesAndOutgoes", ""));
        assertEquals(res.getStatus(),401);
    }
}
