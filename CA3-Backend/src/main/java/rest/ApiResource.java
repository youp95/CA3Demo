package rest;

import DTO.PersonDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import errorhandling.NotFoundException;
import facades.ApiFacade;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 */
@Path("swapi")
public class ApiResource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final ApiFacade AF = ApiFacade.getApiFacade();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @Context
    private UriInfo context;
    
    @Path ("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user","admin"})
    public String getPersons() throws NotFoundException {
        try {
            List<PersonDTO> persons = AF.getAll();
            return GSON.toJson(persons);
        } catch (InterruptedException | ExecutionException ex) {

            throw new NotFoundException(ex.getMessage());
        }
    }
}