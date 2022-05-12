package QueryContainer;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import parser.RegexConstant;
import query.container.WhereCond;

public class SelectQueryProcessor {

	private List columns; // Keep it empty for all the columns

	private String tableName; // name of the table

	private String database; // name of the schema

	private String columnInWhere = ""; // L.H.S of the where clause

	private WhereCond whereCond; // Condition of the where clause

	private String factor = ""; // R.H.S in where clause

	private boolean allColumn = false;
	

	public void parseSelectQuery(String insertQuery) {

		if (insertQuery.contains("*") && insertQuery.toLowerCase().contains("where")) {
			queryWithStarAndWhere(insertQuery);
		} else if (!insertQuery.contains("*") && insertQuery.toLowerCase().contains("where")) {
			queryWithOnlyWhere(insertQuery);
		} else if (insertQuery.contains("*") && !insertQuery.toLowerCase().contains("where")) {
			queryWithOnlyStar(insertQuery);
		} else if (!insertQuery.contains("*") && !insertQuery.toLowerCase().contains("where")) {
			queryWithoutStarAndWhere(insertQuery);
		}

	}

	private void queryWithOnlyStar(String query) {
		final Matcher matcher = pattern(query);
		while (matcher.find()) {
			this.allColumn=true;
			this.tableName = matcher.group(7);

		}
	}

	private void queryWithoutStarAndWhere(String query) {
		final Matcher matcher = pattern(query);
		while (matcher.find()) {
			this.tableName = matcher.group(7);
			String[] columns = matcher.group(3).split(",");
			this.columns = Arrays.asList(columns);
		}
	}

	private void queryWithOnlyWhere(String query) {

		final Matcher matcher = pattern(query);
		while (matcher.find()) {
			this.tableName = matcher.group(7);
			String[] columns = matcher.group(3).split(",");
			this.columns = Arrays.asList(columns);

			getOpeTokens(matcher.group(9));

		}

	}

	private void queryWithStarAndWhere(String query) {

		final Matcher matcher = pattern(query);

		while (matcher.find()) {

			this.allColumn = true;
			this.tableName = matcher.group(7);

			getOpeTokens(matcher.group(9));

		}

	}

	private Matcher pattern(String query) {
		final Pattern pattern = Pattern.compile(RegexConstant.SELECT_REGEX,
				Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(query);
		return matcher;
	}
	
	private void getOpeTokens(String stat) {

		String values[] = null;
		if (stat.contains(">=")) {
			values = stat.split(">=");
			this.whereCond = WhereCond.GREATER_THAN_EQUALS;
		} else if (stat.contains("<=")) {
			values = stat.split("<=");
			this.whereCond = WhereCond.LESS_THAN_EQUALS;
		} else if (stat.contains("=")) {

			values = stat.split("=");
			this.whereCond = WhereCond.EQUALS;
		} else if (stat.contains(">")) {
			values = stat.split(">");
			this.whereCond = WhereCond.GREATER_THAN;
		} else if (stat.contains("<")) {
			values = stat.split("<");
			this.whereCond = WhereCond.LESS_THAN;
		}

		this.columnInWhere = values[0].trim();
		this.factor = values[1].trim();

	}

	public List getColumns() {
		return columns;
	}

	public String getTableName() {
		return tableName;
	}

	public String getDatabase() {
		return database;
	}

	public String getColumnInWhere() {
		return columnInWhere;
	}

	public WhereCond getWhereCond() {
		return whereCond;
	}

	public String getFactor() {
		return factor;
	}

	public boolean isAllColumn() {
		return allColumn;
	}

	@Override
	public String toString() {
		return "SelectQueryProcessor [columns=" + columns + ", tableName=" + tableName + ", database=" + database
				+ ", columnInWhere=" + columnInWhere + ", whereCond=" + whereCond + ", factor=" + factor
				+ ", allColumn=" + allColumn + "]";
	}

}
