package analytics;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.UtilsConstant;

public class Analyzor {
    String logPath = UtilsConstant.LOG_ROOT_FOLDER;
    String path = UtilsConstant.ANALYTIC_ROOT__FOLDER;

    public Set<String> getDatabases() throws FileNotFoundException, IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(logPath + "/databaseLogs.json"));
        JSONObject logsJsonObject = (JSONObject) obj;
        JSONArray databaseLogArray = (JSONArray) logsJsonObject.get("databases");
        Set<String> databaseSet = new HashSet<String>();
        for (Object ob : databaseLogArray) {
            JSONObject jsOb = (JSONObject)ob;
            databaseSet.add((String)jsOb.get("databaseName"));
        }
        return databaseSet;
    }
    public Set<String> getTables() throws FileNotFoundException, IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(logPath + "/queryLogs.json"));
        JSONObject logsJsonObject = (JSONObject) obj;
        JSONArray queryLogArray = (JSONArray) logsJsonObject.get("querys");
        Set<String> querySet = new HashSet<String>();
        for (Object ob : queryLogArray) {
            JSONObject jsOb = (JSONObject) ob;
            querySet.add((String) jsOb.get("table"));
        }
        return querySet;
    }

    public void reportDatasbe(String user, String vm) throws IOException, ParseException {
        Set<String> databases = getDatabases();
        FileWriter fileWriter = new FileWriter(path+"/analytics_Database.txt");
        for (String database : databases) {
            int numberOfQuerys = countNumberOfQuery(user, database);
            String result = "user " + user + " submitted " + numberOfQuerys + " queries for " + database + " running on "
                    + "Virtual Machine" + vm;
            fileWriter.append(result+"\n");
            System.out.println(result);
        }
        fileWriter.close();
    }

    public void reporTabel(String sqlType) throws FileNotFoundException, IOException, ParseException {
        Set<String> tables = getTables();
        FileWriter fileWriter = new FileWriter(path+"/analytics_Tables.txt");
        for (String table : tables) {
            int numberOfSuccessfulQuery = countNumberOfSuccessfulQuery(table, sqlType);
            String result = "Total " + numberOfSuccessfulQuery + " " + sqlType + " operations are performed on "
                    + table;
            fileWriter.append(result+"\n");
            System.out.println(result);
        }
        fileWriter.close();

    }

    private int countNumberOfSuccessfulQuery(String table, String sqlType)
            throws FileNotFoundException, IOException, ParseException {
        int count = 0;
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(logPath + "/queryLogs.json"));
        JSONObject logsJsonObject = (JSONObject) obj;
        JSONArray queryArray = (JSONArray) logsJsonObject.get("querys");
        for (Object object : queryArray) {
            JSONObject ob = (JSONObject) object;
            boolean matchTable = ob.get("table").equals(table);
            boolean isSuccessful = ob.get("isSuccessful").toString().equals("true");
            boolean checkType = ob.get("type").equals(sqlType);
            if (matchTable && isSuccessful && checkType) {
                count++;
            }
        }
        return count;
    }

    public int countNumberOfQuery(String user, String database) throws IOException, ParseException {
        int count = 0;
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(logPath + "/queryLogs.json"));
        JSONObject logsJsonObject = (JSONObject) obj;
        JSONArray queryArray = (JSONArray) logsJsonObject.get("querys");
        for (Object object : queryArray) {
            JSONObject ob = (JSONObject) object;
            if (ob.get("user").equals(user) && ob.get("database").equals(database)) {
                count++;
            }
        }
        return count;
    }

}
