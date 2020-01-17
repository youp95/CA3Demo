/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Role;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import utils.EMF_Creator;

/**
 *
 * @author Younes
 */
@Disabled

public class UserFacadeTest {
    
     private static EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.DROP_AND_CREATE);
     private static UserFacade facade = UserFacade.getUserFacade(emf);
             
    private static User user2 = new User("user", "test");
    private static User user_admin2 = new User("user_admin", "test");
    private static User admin2 = new User("admin", "test");
    private static User both2 = new User("user_admin", "test");
    private static Role userRole2 = new Role("user");
    private static Role adminRole2 = new Role("admin");
    
    public UserFacadeTest() {
    }
    
     @BeforeAll
    public static void setUpClass() {
    
      }
    
    
   @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
//        EMF_Creator.endREST_TestWithDB();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    
    
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //Delete existing users and roles to get a "fresh" database
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            User user = new User("user", "test");
            user.addRole(userRole);
            User admin = new User("admin", "test");
            admin.addRole(adminRole);
            User both = new User("user_admin", "test");
            both.addRole(userRole);
            both.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
            System.out.println("Saved test data to database");
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
     @AfterEach
    public void tearDown() {
        
        // Remove any data after each test was run
    }

    /**
     * Testing getUserFacade() from UserFacade
     */
    @Test
    public void testGetUserFacade(){
        System.out.println("getUserFacade");
        UserFacade expResult = facade.getUserFacade(emf);
        UserFacade result = UserFacade.getUserFacade(emf);
        assertEquals(expResult, result);
        
        
    }
    
    /**
     * Testing getVerifiedUser() from UserFacade
     */
    @Test
    public void testGetVerifiedUser() throws Exception {
        System.out.println("getVerifiedUser");
        String username = "user";
        String password = "test";
        
        String expResult = user2.getUserName();
        String result = null;
        try {
            if (facade != null) {
                result = facade.getVerifiedUser(username, password).getUserName();
            }
        } catch (Exception ex) {
            System.out.println("Exception caught: " + ex);
        }
        assertEquals(expResult, result);
    }

    

    
}
