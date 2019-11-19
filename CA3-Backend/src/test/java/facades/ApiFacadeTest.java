package facades;

import DTO.PersonDTO;
import utils.EMF_Creator;
import entities.RenameMe;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.Settings;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;


//@Disabled
public class ApiFacadeTest {

   
    private static ApiFacade facade;
    private static EntityManagerFactory emf;
   

    public ApiFacadeTest() {
    }

    //@BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/startcode_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        
        facade = ApiFacade.getApiFacade();
    }

    /*   **** HINT **** 
        A better way to handle configuration values, compared to the UNUSED example above, is to store those values
        ONE COMMON place accessible from anywhere.
        The file config.properties and the corresponding helper class utils.Settings is added just to do that. 
        See below for how to use these files. This is our RECOMENDED strategy
     */
    @BeforeAll
    public static void setUpClassV2() {
       emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST,Strategy.DROP_AND_CREATE);
       facade = ApiFacade.getApiFacade();
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
       
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    /**
    Testing getApiFacade() from class ApiFacade
    */
    
    @Test
    public void testGetApiFacade() {
        System.out.println("getApiFacade");
        ApiFacade expResult = ApiFacade.getApiFacade();
        ApiFacade result = ApiFacade.getApiFacade();
        assertEquals(expResult, result);
    }
    
    /**
    * Testing getAll() from class ApiFacade
    * @throws java.lang.Exception
    */
    @Test
   public void testGetAll() throws Exception {
       System.out.println("getAll");
       List<PersonDTO> result = facade.getAll();
       assertTrue(result.size() > 0);
       
       assertTrue(result.get(1)!=null);
       
}
}