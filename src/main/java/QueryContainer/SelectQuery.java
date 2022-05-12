package QueryContainer;

import java.util.List;

enum WhereCond
{
    EQUALS,
    GREATER_THAN,
    LESS_THAN,
    IN,
    NOT_IN,
    LIKE
}

/**
 * Contains the information on select query
 */
public class SelectQuery {
    private List columns;       //Columns to query

    private String tableName;   //name of the table

    private String database;    //name of the schema

    private String columnInWhere;


}
