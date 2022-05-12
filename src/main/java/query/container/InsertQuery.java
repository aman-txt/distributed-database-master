package query.container;

import java.util.List;

/**
 * Contains the information on select query
 */
public class InsertQuery {
    private List columns;           //Keep it empty for all the columns

    private String tableName;       //name of the table

    private String database;        //name of the schema

    private List values;            //values of the insert

    public InsertQuery(List columns, String tableName, String database, List values) {
        this.columns = columns;
        this.tableName = tableName;
        this.database = database;
        this.values = values;
    }

    public List getColumns() {
        return columns;
    }

    public void setColumns(List columns) {
        this.columns = columns;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public List getValues() {
        return values;
    }

    public void setValues(List values) {
        this.values = values;
    }
}
