package dump;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import reverseEngineering.ReverseEngineering;
import utils.UtilsConstant;
import DiskHandler.DistributedManager;
import utils.UtilsMetadata;

public class DumpGenerator {

    String databasePath = UtilsConstant.DATABASE_ROOT_FOLDER;
    String prefixMetadata = UtilsConstant.PREFIX_LOCAL_METADATA;
    String prefixTable = UtilsConstant.PREFIX_TABLE;
    String dumpPath = UtilsConstant.DUMP_ROOT_FOLDER;
    ReverseEngineering reverseEngineering = new ReverseEngineering();
    String[] sortedTables;

    public void createBothDump(String databaseName) throws IOException {
        String out = "";
        out = out + createStructureDump(databaseName);
        out = out + createDataDump(databaseName);
        DistributedManager.writeFile(databaseName, dumpPath + "/dump.sql", "dump.sql", out);
    }

    public String createDataDump(String databaseName) throws IOException {

        HashMap<String, List<String>> tables = UtilsMetadata.fetchDBData(databaseName, prefixTable);
        HashMap<String, List<String>> localMetaData = UtilsMetadata.fetchDBData(databaseName, prefixMetadata);
        // sortedTables = reverseEngineering.getRankOrder(databaseName);
        String out = "";
        for (String tableName : sortedTables) {
            out = out + "INSERT INTO " + tableName + " ( ";
            List<String> records = localMetaData.get(tableName);
            List<String> valueRecords = tables.get(tableName);

            for (int i = 0; i < records.size() - 1; i++) {
                String[] cols = records.get(i).split("\\|");
                out = out + " " + cols[0] + ", ";
            }

            out = out + records.get(records.size() - 1).split("\\|")[0] + " )";
            out = out + " VALUES ( ";

            for (int i = 0; i < valueRecords.size() - 1; i++) {
                String[] cols = valueRecords.get(i).split("\\|");
                for (int j = 0; j < cols.length - 1; j++) {
                    out = out + " " + cols[j] + ", ";
                }
                out = out + " " + cols[cols.length - 1];

            }
            out = out + " )\n";

        }
        return out;

    }

    public String createStructureDump(String databaseName) throws IOException {
        HashMap<String, List<String>> localMetaData = UtilsMetadata.fetchDBData(databaseName, prefixMetadata);
        sortedTables = reverseEngineering.getRankOrder(databaseName);
        String out = "CREATE DATABASE " + databaseName + ";\n";
        out = out + "USE " + databaseName+";\n";
        for (String tableName : sortedTables) {
            out = out + "CREATE TABLE " + tableName + " ( ";
            List<String> attributes = localMetaData.get(tableName);
            out = out + createScriput(attributes);
        }
        return out;

    }

    public String createScriput(List<String> attributes) throws IOException {
        String primaryKey = "";
        String forigenkey = "";
        String out = "";
        for (String attribute : attributes) {
            String[] columns = attribute.split("\\|", -1);
            for (int i = 0; i < columns.length; i++) {
                out = out + " " + columns[0] + " " + columns[1] + " ";
                if (columns[2].equals("true")) {
                    out = out + "NOT NULL, ";
                }
                if (!columns[3].equals("")) {
                    primaryKey = "PRIMARY KEY (" + columns[0] + ")";
                }
                if (!columns[4].equals("")) {
                    forigenkey = "FOREIGN KEY (" + columns[6] + ") REFERENCES " + columns[5] + "(" + columns[6] + ")";
                }
            }

        }

        if (!primaryKey.equals("")) {
            out = out + ", " + primaryKey;
        }
        if (!forigenkey.equals("")) {
            out = out + ", " + forigenkey;
        }
        out = out + " );\n";

        return out;

    }

}