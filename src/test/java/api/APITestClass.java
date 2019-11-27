package api;

import com.sun.jersey.api.client.ClientResponse;
import org.testng.annotations.Test;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class APITestClass
{
    @POST @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Test(groups ="Account")
    public void loginSuccessful()
    {

    }
}
