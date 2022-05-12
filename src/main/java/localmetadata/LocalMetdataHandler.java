package localmetadata;


import DiskHandler.DistributedManager;
import query.container.CreateQuery;
import query.response.Response;
import query.response.ResponseType;
import utils.UtilsConstant;
import query.container.*;
import query.container.CreateQuery;
import query.container.CreateSchema;
import query.container.InsertQuery;
import query.container.SelectQuery;
import query.response.Response;
import query.response.ResponseType;
import utils.UtilsCondition;
import utils.UtilsConstant;
import utils.UtilsFileHandler;
import utils.UtilsMetadata;

import javax.swing.text.Utilities;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LocalMetdataHandler {

    public static Response createTableMetadata(CreateQuery createQuery, String path) throws IOException {

        String filePath = path + UtilsConstant.PREFIX_LOCAL_METADATA + createQuery.getTableName() + ".txt";
        String content = "";

        for (int i = 0; i < createQuery.getColumns().size(); i++) {
            String line = createQuery.getColumns().get(i) + UtilsConstant.SEPERATOR +
                    createQuery.getColumnsDataType().get(i) + UtilsConstant.SEPERATOR +
                    createQuery.getColumnsNotNullStatus().get(i);

            line += UtilsConstant.SEPERATOR;

            if (createQuery.getColumns().get(i).equals(createQuery.getPrimaryKey())) {
                line += "PK";
            }

            line += UtilsConstant.SEPERATOR;

            if (createQuery.getColumns().get(i).equals(createQuery.getForeignKey())) {
                if (UtilsMetadata.fkRefExists(path + UtilsConstant.PREFIX_LOCAL_METADATA + createQuery.getForeignKeyRefTable() + ".txt", createQuery.getForeignKeyRefCol())) {
                    line += "FK" +
                            UtilsConstant.SEPERATOR + createQuery.getForeignKeyRefTable() +
                            UtilsConstant.SEPERATOR + createQuery.getForeignKeyRefCol();
                } else {
                    File file = new File(filePath);
                    file.delete();
                    return new Response(ResponseType.FAILED, "Reference for foreign key " + createQuery.getForeignKeyRefTable() + "." + createQuery.getForeignKeyRefCol() + " doesn't exists");
                }
            } else {
                line += UtilsConstant.SEPERATOR +
                        UtilsConstant.SEPERATOR;
            }

            content += line + "\n";
        }
        DistributedManager.writeFile(createQuery.getDatabase(),filePath,UtilsConstant.PREFIX_LOCAL_METADATA + createQuery.getTableName() + ".txt",content);

        return new Response(ResponseType.SUCCESS, "Query OK, 0 rows affected");
    }


    public static Response insertRows(InsertQuery insertQuery, String path) {

        List<String> contentFile = new ArrayList<>();
        String filePath = path + UtilsConstant.PREFIX_TABLE + insertQuery.getTableName() + ".txt";
        String filePathMeta = path + UtilsConstant.PREFIX_LOCAL_METADATA + insertQuery.getTableName() + ".txt";
//        Writer fileOutput = null;
        try {
            //fileOutput = new BufferedWriter(new FileWriter(filePath, true));
            contentFile = DistributedManager.readFile(insertQuery.getDatabase(),filePath,UtilsConstant.PREFIX_TABLE + insertQuery.getTableName() + ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String primaryKey = UtilsMetadata.getPrimarykey(filePathMeta,insertQuery.getDatabase(),UtilsConstant.PREFIX_LOCAL_METADATA + insertQuery.getTableName() + ".txt");

        if (!primaryKey.isEmpty()) {
            String primaryKeyVal = "";
            for (int i = 0; i < insertQuery.getColumns().size(); i++) {
                if (primaryKey.equals(insertQuery.getColumns().get(i))) {
                    primaryKeyVal = (String) insertQuery.getValues().get(i);
                }
            }

            if (UtilsMetadata.primaryKeyViolation(filePathMeta, filePath, primaryKeyVal,insertQuery.getDatabase(),UtilsConstant.PREFIX_LOCAL_METADATA + insertQuery.getTableName() + ".txt"))
                return new Response(ResponseType.FAILED, "Row with same primary key value already exists");
        }

        String foreignKey = UtilsMetadata.getForeignkey(filePathMeta, insertQuery.getDatabase(),UtilsConstant.PREFIX_LOCAL_METADATA + insertQuery.getTableName() + ".txt");
        String foreignKeyRef = UtilsMetadata.getForeignKeyReference(filePathMeta, insertQuery.getDatabase(),UtilsConstant.PREFIX_LOCAL_METADATA + insertQuery.getTableName() + ".txt");
        if (!foreignKeyRef.isEmpty())       //foreign key exists if true
        {
            String array[] = foreignKeyRef.split("[.]");
            String refTable = array[0];
            String refCol = array[1];

            String refMetaPath = path + UtilsConstant.PREFIX_LOCAL_METADATA + refTable + ".txt";
            String refTablePath = path + UtilsConstant.PREFIX_TABLE + refTable + ".txt";

            int index = UtilsMetadata.getIndexOfColumnInTable(refMetaPath, refCol,insertQuery.getDatabase(), UtilsConstant.PREFIX_LOCAL_METADATA + refTable + ".txt");

            String foreignKeyVal = "";
            for (int i = 0; i < insertQuery.getColumns().size(); i++) {
                if (foreignKey.equals(insertQuery.getColumns().get(i))) {
                    foreignKeyVal = (String) insertQuery.getValues().get(i);
                }
            }

            if (UtilsMetadata.foreignKeyViolation(refTablePath, foreignKeyVal, index,insertQuery.getDatabase(),UtilsConstant.PREFIX_TABLE + refTable + ".txt" ))
                return new Response(ResponseType.FAILED, "Cannot add or update a child row: a foreign key constraint fails");
        }

        List orderCol = new ArrayList<>();

        try {
            List<String> lines = DistributedManager.readFile(insertQuery.getDatabase(),filePathMeta,UtilsConstant.PREFIX_LOCAL_METADATA + insertQuery.getTableName() + ".txt");
            for(String line : lines) {
                String array[] = line.split("[|]");
                orderCol.add(array[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String newLine = "";
        for (int i = 0; i < orderCol.size(); i++) {
            String col = (String) orderCol.get(i);

            String value = searchInInsert(col, insertQuery.getColumns(), insertQuery.getValues());

            newLine += value + UtilsConstant.SEPERATOR;
        }

        newLine = newLine.substring(0, newLine.length() - 1);


        contentFile.add(newLine);
        String finalResult = "";
        for(String line : contentFile)
        {
            finalResult += line +"\n";
        }
        finalResult = finalResult.substring(0,finalResult.length()-1);
//            fileOutput.close();
        try {
            DistributedManager.writeFile(insertQuery.getDatabase(),filePath,UtilsConstant.PREFIX_TABLE + insertQuery.getTableName() + ".txt",finalResult);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return new Response(ResponseType.SUCCESS, "Inserted 1 row");
    }

    private static String searchInInsert(String input, List<String> cols, List<String> values) {
        for (int i = 0; i < cols.size(); i++) {
            if (cols.get(i).equals(input)) {
                return values.get(i);
            }
        }

        return "";
    }

    public static Response createSchemaMetadata(CreateSchema schemaHandler, String path) {
        //new File(path + "/" + schemaHandler.getDatabase()).mkdirs();
        try {
            DistributedManager.createFolder(UtilsConstant.DATABASE_ROOT_FOLDER.substring(2,UtilsConstant.DATABASE_ROOT_FOLDER.length()) + "/" + schemaHandler.getDatabase());
            Thread.sleep(2000);
            DistributedManager.createEmptyFile(UtilsConstant.DATABASE_ROOT_FOLDER.substring(2,UtilsConstant.DATABASE_ROOT_FOLDER.length()) + "/" + schemaHandler.getDatabase() + "/" + UtilsConstant.GM_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Response(ResponseType.SUCCESS, "Database created");
    }


    public static Response checkSchemaQuery(CreateSchema schemaHandler, String path) {
        File f = new File(path + "/" + schemaHandler.getDatabase());
        if (f.exists() && f.isDirectory()) {
            return new Response(ResponseType.SUCCESS, "Database exists");
        }

        return new Response(ResponseType.FAILED, "Database doesn't exist");
    }

    public static Response executeSelectQuery(SelectQuery selectQuery, String path) {

        String fileTablePath = path + UtilsConstant.PREFIX_TABLE + selectQuery.getTableName() + ".txt";
        String fileMetaPath = path + UtilsConstant.PREFIX_LOCAL_METADATA + selectQuery.getTableName() + ".txt";

        List<Integer> indexOfColumns = new ArrayList<>();

        String result = "";

        int indexOfLHS = -1;

        if(!selectQuery.getColumnInWhere().isEmpty())
            indexOfLHS = UtilsMetadata.getIndexOfColumnInTable(fileMetaPath , selectQuery.getColumnInWhere(), selectQuery.getDatabase(),UtilsConstant.PREFIX_LOCAL_METADATA + selectQuery.getTableName() + ".txt");

        for(Object col : selectQuery.getColumns())
        {
            result += (String) col + UtilsConstant.SEPERATOR;
            indexOfColumns.add(UtilsMetadata.getIndexOfColumnInTable(fileMetaPath , (String) col,selectQuery.getDatabase(), UtilsConstant.PREFIX_LOCAL_METADATA + selectQuery.getTableName() + ".txt"));
        }

        result = result.substring(0,result.length()-1);
        result += "\n";

        try {
//            Scanner in = new Scanner(new FileReader(fileTablePath));
            List<String> lines = DistributedManager.readFile(selectQuery.getDatabase(),fileTablePath, UtilsConstant.PREFIX_TABLE + selectQuery.getTableName() + ".txt");
            for(String line : lines) {
                String elements[] = line.split("[|]");

                for(int i = 0;i < indexOfColumns.size() ; i++)
                {
                    if(indexOfLHS == -1 || UtilsCondition.evaluateCondition(selectQuery.getWhereCond(), elements[indexOfLHS] , selectQuery.getFactor()))
                        result +=  elements[indexOfColumns.get(i)] + UtilsConstant.SEPERATOR;
                }

                result = result.substring(0,result.length()-1);
                result += "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return new Response(ResponseType.SUCCESS,result);
    }

    public static Response executeDeleteQuery(DeleteQuery deleteQuery, String path) {

        String fileTablePath = path + UtilsConstant.PREFIX_TABLE + deleteQuery.getTableName() + ".txt";
        String fileMetaPath = path + UtilsConstant.PREFIX_LOCAL_METADATA + deleteQuery.getTableName() + ".txt";

        List<Integer> indexOfColumns = new ArrayList<>();

        int indexOfLHS = -1;

        String result = "";

        if(!deleteQuery.getColumnInWhere().isEmpty())
            indexOfLHS = UtilsMetadata.getIndexOfColumnInTable(fileMetaPath , deleteQuery.getColumnInWhere(), deleteQuery.getDatabase(), UtilsConstant.PREFIX_LOCAL_METADATA + deleteQuery.getTableName() + ".txt");


        try {
            List<String> lines = DistributedManager.readFile(deleteQuery.getDatabase(),fileTablePath,UtilsConstant.PREFIX_LOCAL_METADATA + deleteQuery.getTableName() + ".txt");
            for(String line : lines) {
                String elements[] = line.split("[|]");

                if(indexOfLHS != -1 &&
                        !UtilsCondition.evaluateCondition(deleteQuery.getWhereCond(), elements[indexOfLHS] , deleteQuery.getFactor()))
                {
                    result += line;
                    result += "\n";
                }
            }
            if(!result.isEmpty())
                result = result.substring(0,result.length()-1);

            DistributedManager.writeFile(deleteQuery.getDatabase(),fileTablePath,UtilsConstant.PREFIX_TABLE + deleteQuery.getTableName() + ".txt",result);
            //UtilsFileHandler.writeToFile(fileTablePath, result);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            new Response(ResponseType.INTERNAL_ERROR, "System error");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            new Response(ResponseType.INTERNAL_ERROR, "System error");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Response(ResponseType.SUCCESS,"Record(s) deleted");
    }

}
