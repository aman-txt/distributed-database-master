package query.container;

/**
 * Contains the information on select query
 */
public class CreateSchema {
    private String database;        //name of the schema

    public CreateSchema() {
    }

    public CreateSchema(String database) {
        this.database = database;
    }

    public String getDatabase() {
        return database;
    }
}