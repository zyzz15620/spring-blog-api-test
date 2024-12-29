package data;

public class SchemaData {
    public static final String DUPLICATE_EMAIL_OR_USERNAME_RESPONSE_SCHEMA = """
            {
              "$schema": "http://json-schema.org/draft-07/schema#",
              "title": "Generated schema for Root",
              "type": "object",
              "properties": {
                "timestamp": {
                  "type": "string"
                },
                "message": {
                  "type": "string"
                },
                "details": {
                  "type": "string"
                }
              },
              "required": [
                "timestamp",
                "message",
                "details"
              ]
            }""";
}
