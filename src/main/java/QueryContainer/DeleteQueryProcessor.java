package QueryContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import parser.RegexConstant;

public class DeleteQueryProcessor {
	private String column = ""; // Columns to query

	private String tableName; // name of the table

	private String value = ""; // Columns to query

	private boolean isContainWhere = false;
	private WhereCond whereCond;

	public String getColumns() {
		return column;
	}

	public String getTableName() {
		return tableName;
	}
	public String getValue() {
		return value;
	}
	public Boolean getIsContainWhere() {
		return isContainWhere;
	}
	public WhereCond getOperator() {
		return whereCond;
	}

	private void getOpeTokens(String stat) {

		String values[] = null;
		if (stat.contains("=")) {

			values = stat.split("=");
			this.whereCond = WhereCond.EQUALS;
		} else if (stat.contains(">")) {
			values = stat.split(">");
			this.whereCond = WhereCond.GREATER_THAN;
		} else if (stat.contains("<")) {
			values = stat.split("<");
			this.whereCond = WhereCond.LESS_THAN;
		}
		this.column = values[0].trim();
		this.value = values[1].trim();
	}

	public void parseDeleteQuery(String insertQuery) {

		if (insertQuery.toLowerCase().contains("where")) {
			parserWithWhereCondition(RegexConstant.DELETE_REGEX, insertQuery);
		} else {
			parserWithOutCondition(RegexConstant.DELETE_REGEX, insertQuery);
		}

	}

	private void parserWithOutCondition(String regx, String insertQuery) {

		final Pattern pattern = Pattern.compile(regx, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(insertQuery);

		while (matcher.find()) {
			this.tableName = matcher.group(2);
		}

	}

	private void parserWithWhereCondition(String regx, String insertQuery) {

		final Pattern pattern = Pattern.compile(regx, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(insertQuery);

		while (matcher.find()) {
			this.tableName = matcher.group(2);
			this.isContainWhere = true;
			getOpeTokens(matcher.group(5));
		}

	}
/*
	private void getConToken(Matcher matcher) {

		String stat = matcher.group(5);
		String values[] = null;
		if (stat.contains("=")) {

			values = stat.split("=");
			this.operator = WhereCond.EQUALS.toString();
		} else if (stat.contains(">")) {
			values = stat.split(">");
			this.operator = WhereCond.GREATER_THAN.toString();
		} else if (stat.contains("<")) {
			values = stat.split("<");
			this.operator = WhereCond.LESS_THAN.toString();
		}
		this.column = values[0].trim();
		this.value = values[1].trim();

	}*/

	@Override
	public String toString() {
		return "DeleteQueryProcessor [column=" + column + ", tableName=" + tableName + ", value=" + value
				+ ", isContainWhere=" + isContainWhere + ", operator=" + getOperator() + "]";
	}

}
