package tests;

import common.DatabaseConnection;
import common.RestAssuredSetup;
import io.restassured.response.Response;

import jakarta.persistence.NoResultException;
import model.Account;
import model.AccountEntity;
import net.javacrumbs.jsonunit.core.Option;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static common.MethodUtils.createAccount;
import static data.JsonData.ACCOUNT_EMPTY_OR_NULL_RESPONSE;
import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;
import static net.javacrumbs.jsonunit.JsonMatchers.jsonPartEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ResisterTests {
    private static SessionFactory sessionFactory = DatabaseConnection.getSessionFactory();
    private static List<AccountEntity> accountToDelete = new ArrayList<>();

    public static void deleteAccount(AccountEntity account){
        sessionFactory.inTransaction(session -> {
            try{
                session.createNativeQuery(String.format("DELETE FROM accounts_roles WHERE account_id=%s", account.getId())).executeUpdate();
            }catch (NoResultException e){
                fail(e.getMessage() + "\n Fail to delete account in accounts_roles table");
            }try{
                session.createQuery("delete from AccountEntity where username = :username")
                        .setParameter("username", account.getUsername()).executeUpdate();
            }catch (NoResultException e){
                fail(e.getMessage() + "\n Fail to delete account in accounts table");
            }
        });
    }


    @BeforeAll
    public static void setUp(){
        RestAssuredSetup.setup();
    }

    @AfterAll
    public static void cleanUp(){
        for(AccountEntity account : accountToDelete){
            deleteAccount(account);
        }
    }

    //Verify Success Response - Valid data - TC_001
    @Test
    public void TC_001(){
        String random = String.valueOf(System.currentTimeMillis());
        Account account = new Account("tester", "test"+ random, String.format("test%s@gmail.com", random), "test");
        Response response= createAccount(account);
        sessionFactory.inTransaction(session -> {
            try{
                AccountEntity accountEntity = getAccountEntityWithUsername(session, "test"+ random);
                accountToDelete.add(accountEntity);
            }catch (NoResultException e){
                System.out.println("Response maybe alright but failed to delete the account");
                //we don't want this testcase FAILED because of delete function, because we're verifying the response
            }
        });
        assertEquals(response.asString().trim(), "User registered successfully!.");
        assertEquals(response.getStatusCode(), 201);
    }

    //Verify new Account exists in database - TC_002
    @Test
    public void TC_002(){
        String random = String.valueOf(System.currentTimeMillis());
        Account account = new Account("tester", "test"+ random, String.format("test%s@gmail.com", random), "test");
        Response response= createAccount(account);
        assertEquals(response.asString().trim(), "User registered successfully!.");

        sessionFactory.inTransaction(session -> {
            try{
                AccountEntity accountEntity = getAccountEntityWithUsername(session, "test"+ random);
                accountToDelete.add(accountEntity);
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

        sessionFactory.inTransaction(session -> {
            try{
                AccountEntity accountEntity = getAccountEntityWithUsername(session, "test"+ random);
                accountToDelete.add(accountEntity);

                Long roleId = (Long) session.createNativeQuery(String.format("SELECT role_id FROM accounts_roles where account_id=%s", accountEntity.getId())).getSingleResult();
                assertEquals(roleId, 2);
            }catch (NoResultException e){
                fail(e.getMessage());
            }
        });
    }

    //Verify failed response when email format is invalid - TC_004
    @ParameterizedTest
    @ValueSource(strings = {"test1395876", "test@gmail", "@domain.com", "test@domain.-com"})
    public void TC_004(String email){
        String random = String.valueOf(System.currentTimeMillis());
        Account account = new Account("tester", "test"+ random, email, "test");
        Response response= createAccount(account);

        sessionFactory.inTransaction(session -> {
            try{
                AccountEntity accountEntity = getAccountEntityWithUsername(session, "test"+ random);
                accountToDelete.add(accountEntity);
                fail("Account still created in database regard the correct response");
            }catch (NoResultException e){}
        });

        assertThat(response.getStatusCode(), equalTo(400));
        assertThat(response.asString(), jsonPartEquals("email", "must be a well-formed email address"));
    }

    //Verify failed response when null or empty on email/username - TC_005
    @ParameterizedTest
    @MethodSource("emptyNullRegister")
    public void TC_005(String name, String username, String email, String password){
        String random = String.valueOf(System.currentTimeMillis());
        Account account = new Account(name, username, email, password);
        Response response= createAccount(account);

        sessionFactory.inTransaction(session -> {
            try{
                AccountEntity accountEntity = getAccountEntityWithUsername(session, "test"+ random);
                accountToDelete.add(accountEntity);
                fail("Account still created in database regard the correct response");
            }catch (NoResultException e){}
        });

        assertThat(response.getStatusCode(), equalTo(400));
        assertThat(response.asString(), jsonEquals(ACCOUNT_EMPTY_OR_NULL_RESPONSE).when(Option.IGNORING_ARRAY_ORDER));
    }

    private static Stream<Arguments> emptyNullRegister(){
        return Stream.of(
                Arguments.arguments(null, null, null, null),
                Arguments.arguments("", "", "", "")
        );
    }

    private static AccountEntity getAccountEntityWithUsername(Session session, String username) {
        AccountEntity accountEntity = session.createSelectionQuery("from AccountEntity where username=:username", AccountEntity.class)
                .setParameter("username", username)
                .getSingleResult();
        return accountEntity;
    }
}
