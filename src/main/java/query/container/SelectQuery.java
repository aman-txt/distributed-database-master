package query.container;

import java.util.List;

/**
 * Contains the information on select query
 */
public class SelectQuery {


    private Boolean allColumnsSelected; //True if all columns were mentioned in select query

    private List columns;           //Keep it empty for all the columns

    private String tableName;       //name of the table

    private String database;        //name of the schema

    private String columnInWhere;   //L.H.S of the where clause

    private WhereCond whereCond;    //Condition of the where clause

    private String factor;          //R.H.S in where clause
  

    public SelectQuery() {
    }

    public SelectQuery(List columns, String tableName, String database, String columnInWhere, WhereCond whereCond, String factor, Boolean allColumnsSelected) {
        this.columns = columns;
        this.tableName = tableName;
        this.database = database;
        this.columnInWhere = columnInWhere;
        this.whereCond = whereCond;
        this.factor = factor;
        this.allColumnsSelected = allColumnsSelected;

    }


    //------ Getter and setter ----

    public List getColumns() {
        return columns;
    }

    public void setColumns(List columns) {
        this.columns = columns;
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

    public String getColumnInWhere() {
        return columnInWhere;
    }

    public void setColumnInWhere(String columnInWhere) {
        this.columnInWhere = columnInWhere;
    }

    public WhereCond getWhereCond() {
        return whereCond;
    }

    public void setWhereCond(WhereCond whereCond) {
        this.whereCond = whereCond;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public Boolean getAllColumnsSelected() {
        return allColumnsSelected;
    }

    public void setAllColumnsSelected(Boolean allColumnsSelected) {
        this.allColumnsSelected = allColumnsSelected;
    }

}
