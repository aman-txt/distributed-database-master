package loger;

public class LogsParameters {
    String event;
    String excutionTime;
    String queryString;
    String user;
    String timeStamp;
    String type;
    String condition;
    String columns;
    String values;
    String database;
    String table;
    int numberOfRowsChanges;
    int numberOfTables;
    String[]tableNames;
    int[] numberOfRows;
    Boolean isSuccessful;
    String crashReport;


    public LogsParameters(String event, String excutionTime, String queryString, String user, String timeStamp, String type, String condition, String columns, String values, String database, String table, int numberOfRowsChanges, int numberOfTables, String[] tableNames, int[] numberOfRows, Boolean isSuccessful,String crashReport) {
        this.event = event;
        this.excutionTime = excutionTime;
        this.queryString = queryString;
        this.user = user;
        this.timeStamp = timeStamp;
        this.type = type;
        this.condition = condition;
        this.columns = columns;
        this.values = values;
        this.database = database;
        this.table = table;
        this.numberOfRowsChanges = numberOfRowsChanges;
        this.numberOfTables = numberOfTables;
        this.tableNames = tableNames;
        this.numberOfRows = numberOfRows;
        this.isSuccessful = isSuccessful;
        this.crashReport = crashReport;
    }
    
}
