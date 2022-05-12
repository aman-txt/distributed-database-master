package query.manager;

import localmetadata.LocalMetdataHandler;
import query.container.CreateQuery;
import query.container.InsertQuery;
import query.response.Response;
import query.response.ResponseType;
import utils.UtilsConstant;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class InsertHandler {

    public static Response executeInsertQuery(InsertQuery insertQuery)
    {

        return LocalMetdataHandler.insertRows(insertQuery,UtilsConstant.DATABASE_ROOT_FOLDER + "/" + insertQuery.getDatabase() + "/");

    }
}
