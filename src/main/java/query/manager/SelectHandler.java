package query.manager;

import localmetadata.LocalMetdataHandler;
import query.container.CreateQuery;
import query.container.SelectQuery;
import query.response.Response;
import query.response.ResponseType;
import utils.UtilsConstant;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class SelectHandler {

    public static Response executeSelectQuery(SelectQuery selectQuery)
    {

        return LocalMetdataHandler.executeSelectQuery(selectQuery,UtilsConstant.DATABASE_ROOT_FOLDER + "/" + selectQuery.getDatabase() + "/");

//        return new Response(ResponseType.INTERNAL_ERROR, "System error.");
    }
}
