package query.manager;

import DiskHandler.DistributedManager;
import localmetadata.LocalMetdataHandler;
import utils.UtilsConstant;
import query.container.CreateQuery;
import query.response.Response;
import query.response.ResponseType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateHandler {

    public static Response executeCreateQuery(CreateQuery createQuery) throws IOException
    {
        decideTableInstance(createQuery);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            return LocalMetdataHandler.createTableMetadata(createQuery,UtilsConstant.DATABASE_ROOT_FOLDER + "/" + createQuery.getDatabase() + "/");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new Response(ResponseType.INTERNAL_ERROR, "System error.");
    }


    public static void decideTableInstance(CreateQuery createQuery) throws IOException
    {
        String gm = "";
        List<String> gm_metadata = DistributedManager.readFile(createQuery.getDatabase(),UtilsConstant.DATABASE_ROOT_FOLDER+"/"+createQuery.getDatabase()+"/"+UtilsConstant.GM_FILE_NAME,UtilsConstant.GM_FILE_NAME);
        for(String s : gm_metadata)
        {
            gm += s + "\n";
        }
        String result = tableDistribution(gm, createQuery.getTableName());
        DistributedManager.writeFile(createQuery.getDatabase(), UtilsConstant.DATABASE_ROOT_FOLDER+"/"+createQuery.getDatabase()+"/"+UtilsConstant.GM_FILE_NAME,UtilsConstant.GM_FILE_NAME, result);
    }

    public static String tableDistribution(String metadata, String tableName) {
        String pattern1 = "(table_[a-zA-Z._]*\\|1)";
        String pattern2 = "(table_[a-zA-Z._]*\\|2)";
        Matcher matcher1 = Pattern.compile(pattern1).matcher(metadata);
        Matcher matcher2 = Pattern.compile(pattern2).matcher(metadata);
        int tablesInInstance1 = 0;
        int tablesInInstance2 = 0;
        while (matcher1.find()) {
            tablesInInstance1++;
        }
        while (matcher2.find()) {
            tablesInInstance2++;
        }
        if (tablesInInstance1 == tablesInInstance2)

            return metadata  + "metadata_" + tableName + ".txt|" + 1 + "\n" + "table_" + tableName + ".txt|" + 1;
        if (tablesInInstance1 < tablesInInstance2)
            return metadata + "metadata_" + tableName + ".txt|" + 1 + "\n" + "table_" + tableName + ".txt|" + 1;

        return metadata  + "metadata_" + tableName + ".txt|" + 2 + "\n" + "table_" + tableName + ".txt|" + 2;
    }
}
