package query.manager;

import localmetadata.LocalMetdataHandler;
import query.container.DeleteQuery;
import query.container.SelectQuery;
import query.response.Response;
import utils.UtilsConstant;

public class DeleteHandler {

    public static Response executeDeleteQuery(DeleteQuery deleteQuery)
    {
        return LocalMetdataHandler.executeDeleteQuery(deleteQuery,UtilsConstant.DATABASE_ROOT_FOLDER + "/" + deleteQuery.getDatabase() + "/");
    }
}
