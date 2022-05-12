package QueryContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import parser.RegexConstant;

public class InsertQueryProcessor {

	private List<String> columns; // Columns to query

	private String tableName; // name of the table

	private List<String> values; // Columns to query

	private boolean isWithoutColumn = false;

	public InsertQueryProcessor() {
		this.columns = new ArrayList<String>();
		this.values = new ArrayList<String>();

	}

	public void parseInsertQuery(String insertQuery) {

		if (isQueryWithColumn(insertQuery)) {
			parserWithColumn(RegexConstant.INSERT_WITH_COLUMN, insertQuery);
		} else {
			parserWithoutColumn(RegexConstant.INSERT_WITHOUT_COLUMN, insertQuery);
		}

	}

	private void parserWithoutColumn(String regx, String insertQuery) {

		final Pattern pattern = Pattern.compile(regx, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(insertQuery);

		while (matcher.find()) {

			this.tableName = matcher.group(3);
			String[] sValues = matcher.group(5).split(",");
			this.values = Arrays.asList(sValues);

			this.isWithoutColumn = true;
		}

	}

	private void parserWithColumn(String regx, String insertQuery) {
		final Pattern pattern = Pattern.compile(regx, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(insertQuery);

		while (matcher.find()) {

			this.tableName = matcher.group(3);
			String[] columns = matcher.group(4).split(",");

			this.columns = Arrays.asList(columns);

			String[] sValues = matcher.group(6).split(",");
			this.values = Arrays.asList(sValues);

			this.isWithoutColumn = true;
		}

	}

	private boolean isQueryWithColumn(String query) {

		boolean colPresent = false;

		final Pattern pattern = Pattern.compile(RegexConstant.CEHCK_COLUMN_REGEX,
				Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(query);

		while (matcher.find()) {
			colPresent = true;
		}

		return colPresent;
	}

	@Override
	public String toString() {
		return "InsertQueryProcessor [columns=" + columns + ", tableName=" + tableName + ", values=" + values
				+ ", isWithoutColumn=" + isWithoutColumn + "]";
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public boolean isWithoutColumn() {
		return isWithoutColumn;
	}

	public void setWithoutColumn(boolean isWithoutColumn) {
		this.isWithoutColumn = isWithoutColumn;
	}

}
