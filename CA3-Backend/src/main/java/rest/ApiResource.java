package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Output;
import facades.ApiFacade;
import java.util.List;
import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("api")
public class ApiResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/startcode",
                "sj1234",
                "sj1234",
                EMF_Creator.Strategy.CREATE);
    private static final ApiFacade FACADE =  ApiFacade.getApiFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    @Path("server")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Output> getDataFromServers() {
        String[] resources = {"planets/", "people/", "starships/", "films/", "species/"};
        return FACADE.fetchFromServers(resources);
    }

 
}
