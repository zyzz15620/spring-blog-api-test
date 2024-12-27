package tests;

import common.DatabaseConnection;
import common.RestAssuredSetup;
import io.restassured.response.Response;

import jakarta.persistence.NoResultException;
import model.Account;
import model.AccountEntity;
import model.AccountRoleRelationship;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static common.MethodUtils.createAccount;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ResisterTests {
    private static SessionFactory sessionFactory = DatabaseConnection.getSessionFactory();
    private static List<String> accountToDelete = new ArrayList<>();

    public static void deleteAccount(String username){
        ///Not Possible to delete directly by query because the account's FOREIGN key is used in accounts_roles table
        ///So it is best to remove by API
    }


    @BeforeAll
    public static void setUp(){
        RestAssuredSetup.setup();
    }

    @AfterAll
    public static void cleanUp(){
        for(String username : accountToDelete){
            deleteAccount(username);
        }
    }

    //Verify Success Response - Valid data - TC_001
    @Test
    public void TC_001(){
        String random = String.valueOf(System.currentTimeMillis());
        Account account = new Account("tester", "test"+ random, String.format("test%s@gmail.com", random), "test");
        Response response= createAccount(account);
        assertEquals(response.asString().trim(), "User registered successfully!.");
        accountToDelete.add("test"+ random);
    }

    //Verify new Account exists in database - TC_002
    @Test
    public void TC_002(){
        String random = String.valueOf(System.currentTimeMillis());
        Account account = new Account("tester", "test"+ random, String.format("test%s@gmail.com", random), "test");
        Response response= createAccount(account);
        assertEquals(response.asString().trim(), "User registered successfully!.");
        accountToDelete.add("test"+ random);

        sessionFactory.inTransaction(session -> {
            try{
                session.createSelectionQuery("from AccountEntity where username=:username", AccountEntity.class)
                        .setParameter("username", "test"+random)
                        .getSingleResult();
            }catch (NoResultException e){
                fail(e.getMessage());
            }
            });
    }

    //Verify new Account has role ROLE_USER - TC_003
    @Test
    public void TC_003(){
        String random = String.valueOf(System.currentTimeMillis());
        Account account = new Account("tester", "test"+ random, String.format("test%s@gmail.com", random), "test");
        Response response= createAccount(account);
        assertEquals(response.asString().trim(), "User registered successfully!.");
        accountToDelete.add("test"+ random);

        sessionFactory.inTransaction(session -> {
            try{
                AccountEntity accountEntity = session.createSelectionQuery("from AccountEntity where username=:username", AccountEntity.class)
                        .setParameter("username", "test"+random)
                        .getSingleResult();
                AccountRoleRelationship accountRoleRelationship = session.createSelectionQuery("from AccountRoleRelationship where accountId=:accountId", AccountRoleRelationship.class)
                        .setParameter("accountId", accountEntity.getId())
                        .getSingleResult();
                assertEquals(accountRoleRelationship.getRoleId(), 1);
            }catch (NoResultException e){
                fail(e.getMessage());
            }
        });
    }

}
