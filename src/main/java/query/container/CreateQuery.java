package query.container;

import java.util.List;

/**
 * Contains the information on select query
 */
public class CreateQuery {
    private List columns;           //Columns name

    private List columnsDataType;   //Columns datatype like int, varchar etc

    private List columnsNotNullStatus; //Add true (Boolean) at the position, if specific column is not null

    private String tableName;       //name of the table

    private String database;        //name of the schema

    private String primaryKey;      //L.H.S of the where clause


    private String foreignKey;      //Foreign key column name

    private String foreignKeyRefTable; //Reference table of the foreign key

    private String foreignKeyRefCol; //Reference column of the table for the foreign key

    public CreateQuery() {
    }

    public CreateQuery(List columns, List columnsDataType, List columnsNotNullStatus, String tableName, String database, String primaryKey, String foreignKey, String foreignKeyRefTable, String foreignKeyRefCol) {
        this.columns = columns;
        this.columnsDataType = columnsDataType;
        this.columnsNotNullStatus = columnsNotNullStatus;
        this.tableName = tableName;
        this.database = database;
        this.primaryKey = primaryKey;
        this.foreignKey = foreignKey;
        this.foreignKeyRefTable = foreignKeyRefTable;
        this.foreignKeyRefCol = foreignKeyRefCol;
    }

    public List getColumns() {
        return columns;
    }

    public void setColumns(List columns) {
        this.columns = columns;
    }

    public List getColumnsDataType() {
        return columnsDataType;
    }

    public void setColumnsDataType(List columnsDataType) {
        this.columnsDataType = columnsDataType;
    }

    public List getColumnsNotNullStatus() {
        return columnsNotNullStatus;
    }

    public void setColumnsNotNullStatus(List columnsNotNullStatus) {
        this.columnsNotNullStatus = columnsNotNullStatus;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    public String getForeignKeyRefTable() {
        return foreignKeyRefTable;
    }

    public void setForeignKeyRefTable(String foreignKeyRefTable) {
        this.foreignKeyRefTable = foreignKeyRefTable;
    }

    public String getForeignKeyRefCol() {
        return foreignKeyRefCol;
    }

    public void setForeignKeyRefCol(String foreignKeyRefCol) {
        this.foreignKeyRefCol = foreignKeyRefCol;
    }
}
