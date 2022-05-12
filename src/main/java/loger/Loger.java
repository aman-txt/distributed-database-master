package loger;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import DiskHandler.DistributedManager;
import utils.UtilsConstant;
import utils.UtilsMetadata;

public class Loger {
    String logPath = UtilsConstant.LOG_ROOT_FOLDER;

    public void wirteLogs(LogsParameters params) throws IOException, ParseException {
        writeEventLog(params);
        writeDatabaseLog(params);
        writeQueryLog(params);
    }

    public void writeEventLog(LogsParameters params) throws IOException, ParseException {
        // ////FileWriter //fileWriter;
        JSONParser parser = new JSONParser();
        try {
            List<String> log=DistributedManager.readFile(params.database, logPath+"/eventLogs.json", "eventLogs.json");
            Object obj = parser.parse(log.get(0));
            JSONObject logsJsonObject = (JSONObject) obj;
            JSONArray eventLogArray = (JSONArray) logsJsonObject.get("events");
            JSONObject eventLog = new JSONObject();
            eventLog.put("timeStamp",params.timeStamp);
            eventLog.put("event", params.event);
            eventLog.put("onDatabase", params.database);
            eventLog.put("onTable", params.table);
            eventLog.put("condition", params.condition);
            eventLog.put("numberOfRowsChanges", params.numberOfRowsChanges);
            eventLog.put("columns", params.columns);
            eventLog.put("values", params.values);
            eventLog.put("crashReport", params.crashReport);
            eventLogArray.add(eventLog);
            DistributedManager.writeFile("", logPath + "/eventLogs.json", "eventLogs.json",
                    logsJsonObject.toJSONString());
            // ////fileWriter = new //FileWriter(logPath + "/eventLogs.json");
            // //fileWriter.append(logsJsonObject.toJSONString());
        } catch (Exception e) {
            ////fileWriter = new //FileWriter(logPath + "/eventLogs.json");
            String jsonString = "{\"events\":[]}";
            JSONObject logsJsonObject = (JSONObject) parser.parse(jsonString);
            DistributedManager.writeFile("", logPath + "/eventLogs.json", "eventLogs.json",
                    logsJsonObject.toJSONString());
            //fileWriter.append(logsJsonObject.toJSONString());
            JSONArray eventLogArray = (JSONArray) logsJsonObject.get("events");
            JSONObject eventLog = new JSONObject();
            eventLog.put("timeStamp", params.timeStamp);
            eventLog.put("event", params.event);
            eventLog.put("onDatabase", params.database);
            eventLog.put("onTable", params.table);
            eventLog.put("condition", params.condition);
            eventLog.put("numberOfRowsChanges", params.numberOfRowsChanges);
            eventLog.put("columns", params.columns);
            eventLog.put("values", params.values);
            eventLog.put("crashReport", params.crashReport);
            eventLogArray.add(eventLog);
            DistributedManager.writeFile("", logPath + "/eventLogs.json", "eventLogs.json",
                    logsJsonObject.toJSONString());
            // ////fileWriter = new //FileWriter(logPath + "/eventLogs.json");
            // //fileWriter.append(logsJsonObject.toJSONString());
        }
        // //fileWriter.close();
    }

    public void writeDatabaseLog(LogsParameters params) throws IOException, ParseException {
        // ////FileWriter //fileWriter;
        JSONParser parser = new JSONParser();
        try {
            List<String> log = DistributedManager.readFile(params.database, logPath + "/databaseLogs.json",
                    "databaseLogs.json");
            Object obj = parser.parse(log.get(0));
            JSONObject logsJsonObject = (JSONObject) obj;
            JSONArray databaseLogArray = (JSONArray) logsJsonObject.get("databases");
            JSONObject databaseObject = new JSONObject();
            databaseObject.put("timeStamp", params.timeStamp);
            databaseObject.put("databaseName", params.database);
            databaseObject.put("numberOfTables", params.numberOfTables);
            JSONArray tableObjects = new JSONArray();
            for (int i = 0; i < params.numberOfTables;i++) {
                JSONObject table = new JSONObject();
                table.put("tableName", params.tableNames[i]);
                table.put("numberOfRows", params.numberOfRows[i]);
                tableObjects.add(table);
            }
            databaseObject.put("tables",tableObjects);
            databaseLogArray.add(databaseObject);
            DistributedManager.writeFile("", logPath + "/databaseLogs.json", "databaseLogs.json",
                    logsJsonObject.toJSONString());
            ////fileWriter = new //FileWriter(logPath + "/databaseLogs.json");
            //fileWriter.append(logsJsonObject.toJSONString());
        } catch (Exception e) {
            ////fileWriter = new //FileWriter(logPath + "/databaseLogs.json");
            String jsonString = "{\"databases\":[]}";
            JSONObject logsJsonObject = (JSONObject) parser.parse(jsonString);
            DistributedManager.writeFile("", logPath + "/databaseLogs.json", "databaseLogs.json",
                    logsJsonObject.toJSONString());
            //fileWriter.append(logsJsonObject.toJSONString());
            
           
             JSONArray databaseLogArray = (JSONArray) logsJsonObject.get("databases");
            JSONObject databaseObject = new JSONObject();
            databaseObject.put("timeStamp", params.timeStamp);
            databaseObject.put("databaseName", params.database);
            databaseObject.put("numberOfTables", params.numberOfTables);
            JSONArray tableObjects = new JSONArray();
            for (int i = 0; i < params.numberOfTables;i++) {
                JSONObject table = new JSONObject();
                table.put("tableName", params.tableNames[i]);
                table.put("numberOfRows", params.numberOfRows[i]);
                tableObjects.add(table);
            }
            databaseObject.put("tables",tableObjects);
            databaseLogArray.add(databaseObject);
            DistributedManager.writeFile("", logPath + "/databaseLogs.json", "databaseLogs.json",
                    logsJsonObject.toJSONString());
            ////fileWriter = new //FileWriter(logPath + "/databaseLogs.json");
            // //fileWriter.append(logsJsonObject.toJSONString());
            
            
        }
        //fileWriter.close();
       

    }

    public void writeQueryLog(LogsParameters params) throws IOException, ParseException {
        // ////FileWriter //fileWriter;
        JSONParser parser = new JSONParser();
        try {
            List<String> log = DistributedManager.readFile(params.database, logPath + "/queryLogs.json",
                    "queryLogs.json");
            Object obj = parser.parse(log.get(0));
            JSONObject logsJsonObject = (JSONObject) obj;
            JSONArray queryLogArray = (JSONArray) logsJsonObject.get("querys");
            JSONObject queryLog = new JSONObject();
            queryLog.put("user", params.user);
            queryLog.put("database", params.database);
            queryLog.put("table", params.table);
            queryLog.put("query", params.queryString);
            queryLog.put("type", params.type);
            queryLog.put("excutionTime", params.excutionTime);
            queryLog.put("timeStamp", params.timeStamp);
            queryLog.put("codition", params.condition);
            queryLog.put("columns", params.columns);
            queryLog.put("values", params.values);
            queryLog.put("isSuccessful", params.isSuccessful);
            queryLogArray.add(queryLog);
            // ////fileWriter = new //FileWriter(logPath + "/queryLogs.json");
            DistributedManager.writeFile("", logPath+"/queryLogs.json", "queryLogs.json", logsJsonObject.toJSONString());
            // //fileWriter.append(logsJsonObject.toJSONString());
        } catch (Exception e) {
            // ////fileWriter = new //FileWriter(logPath + "/queryLogs.json");
            String jsonString = "{\"querys\":[]}";
            JSONObject logsJsonObject = (JSONObject) parser.parse(jsonString);
            DistributedManager.writeFile("", logPath + "/queryLogs.json", "queryLogs.json",
                    logsJsonObject.toJSONString());
            // //fileWriter.append(logsJsonObject.toJSONString());
            JSONArray queryLogArray = (JSONArray) logsJsonObject.get("querys");
            JSONObject queryLog = new JSONObject();
            queryLog.put("user", params.user);
            queryLog.put("database", params.database);
            queryLog.put("table", params.table);
            queryLog.put("query", params.queryString);
            queryLog.put("type", params.type);
            queryLog.put("excutionTime", params.excutionTime);
            queryLog.put("timeStamp", params.timeStamp);
            queryLog.put("condition", params.condition);
            queryLog.put("columns", params.columns);
            queryLog.put("values", params.values);
            queryLog.put("isSuccessful", params.isSuccessful);
            queryLogArray.add(queryLog);
            DistributedManager.writeFile("", logPath + "/queryLogs.json", "queryLogs.json",
                    logsJsonObject.toJSONString());
            // ////fileWriter = new //FileWriter(logPath + "/queryLogs.json");
            // //fileWriter.append(logsJsonObject.toJSONString());
        }
        // //fileWriter.close();

    }

}