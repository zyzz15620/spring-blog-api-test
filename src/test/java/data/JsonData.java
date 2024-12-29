package data;

public class JsonData {
    public static final String ACCOUNT_EMPTY_OR_NULL_RESPONSE = """
            {
                "password": "must not be empty",
                "name": "Name should not be null or empty",
                "email": "Email should not be null or empty",
                "username": "Username should not be null or empty"
            }""";
}
