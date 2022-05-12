package query.manager;


import query.container.CreateQuery;

import java.io.IOException;

import query.container.*;
import query.container.CreateSchema;
import query.container.InsertQuery;
import query.container.SqlType;
import query.response.Response;
import query.response.ResponseType;

/**
 *  Handles different queries from user
 *  Use this class to pass tokenised query
 */
public class QueryHandler
{


    public static Response executeQuery(Object query, SqlType sqlType) throws IOException   //we will need to instantiate the
    {
        if(sqlType.equals(SqlType.CREATE))      //Handle create query
        {
            return CreateHandler.executeCreateQuery((CreateQuery) query);
        }
        else if(sqlType.equals(SqlType.INSERT))
        {
            return InsertHandler.executeInsertQuery((InsertQuery) query);
        }
        else if(sqlType.equals(SqlType.CREATE_SCHEMA))
        {
            return SchemaHandler.executeSchemaCreateQuery((CreateSchema) query);
        }
        else if(sqlType.equals(SqlType.CHECK_SCHEMA))
        {
            return SchemaHandler.checkSchemaQuery((CreateSchema) query);
        }
        else if(sqlType.equals(SqlType.SELECT))
        {
            return SelectHandler.executeSelectQuery((SelectQuery) query);
        }
        else if(sqlType.equals(SqlType.DELETE))
        {
            return DeleteHandler.executeDeleteQuery((DeleteQuery) query);
        }

        return new Response(ResponseType.INTERNAL_ERROR, "System error.");
    }
}
