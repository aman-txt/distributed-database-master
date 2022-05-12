package query.manager;

import localmetadata.LocalMetdataHandler;
import query.container.CreateQuery;
import query.container.CreateSchema;
import query.response.Response;
import query.response.ResponseType;
import utils.UtilsConstant;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class SchemaHandler {

    public static Response executeSchemaCreateQuery(CreateSchema createSchema)
    {
        return LocalMetdataHandler.createSchemaMetadata(createSchema,UtilsConstant.DATABASE_ROOT_FOLDER);
    }

    public static Response checkSchemaQuery(CreateSchema createSchema)
    {
        return LocalMetdataHandler.checkSchemaQuery(createSchema,UtilsConstant.DATABASE_ROOT_FOLDER);
    }
}
