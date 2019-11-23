package vitalik;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


public class journal_API {
 private final static String JSON = MediaType.APPLICATION_JSON.toString();

    String requestBody;
    @BeforeClass
    public void setUp()
    {
         requestBody =  "{\n" +
                "  \"username\": \"mind0wner\",\n" +
                "  \"password\": \"123\"\n" +
                "}";
    }

    @POST
    @Path("/login")

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Test
    public void loginSuccess()
    {
        Response response = RestAssured.given().contentType(ContentType.JSON).body(requestBody).post(Vet_API_methods.targetURL("/login"));
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertTrue(Vet_API_methods.isBodyHasKey(response,"data.token"));

        try{
        Vet_API_methods.setAuthToken(Vet_API_methods.takeKeyValue(response,"data.token"));
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage().toString());
        }
    }

    @GET
    @Path("/schedule")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Test(dependsOnMethods = "loginSuccess")
    public void getSchedule()
    {
        Response response = RestAssured.given().contentType(ContentType.JSON).header("Authorization",Vet_API_methods.getAuthToken())
                .get(Vet_API_methods.targetURL("/schedule"));

        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertTrue(Vet_API_methods.isBodyHasKey(response,"data"));

    }
    @GET
    @Path("/schedule/time")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Test(dependsOnMethods = "loginSuccess")
    public void getTimeSchedule()
    {
        Response response = RestAssured.given().contentType(ContentType.JSON).header("Authorization",Vet_API_methods.getAuthToken())
                .get(Vet_API_methods.targetURL("/schedule/time"));
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertTrue(Vet_API_methods.isBodyHasKey(response,"data"));

    }
    @GET
    @Path("/schedule/time")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Test
    public void getTimeScheduleInvalidToken()
    {
        Response response = RestAssured.given().contentType(ContentType.JSON).header("Authorization",Vet_API_methods.getInvalidAuthToken())
                .get(Vet_API_methods.targetURL("/schedule/time"));
        Assert.assertEquals(response.getStatusCode(),403);
        Assert.assertTrue(Vet_API_methods.isBodyNotHasKey(response,"data"));
    }

    @GET
    @Path("/users")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Test(dependsOnMethods = "loginSuccess")
    public void getAllAvailableUsers()
    {
        Response response = RestAssured.given().contentType(ContentType.JSON).header("Authorization",Vet_API_methods.getAuthToken())
                .get(Vet_API_methods.targetURL("/users"));
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertTrue(Vet_API_methods.isBodyHasKey(response,"data"));
    }

    @GET
    @Path("/users")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Test
    public void getAllAvailableUsersWithInvalidToken()
    {
        Response response = RestAssured.given().contentType(ContentType.JSON).header("Authorization",Vet_API_methods.getInvalidAuthToken())
                .get(Vet_API_methods.targetURL("/users"));
        Assert.assertEquals(response.getStatusCode(),403);
        Assert.assertTrue(Vet_API_methods.isBodyNotHasKey(response,"data"));
    }
}
