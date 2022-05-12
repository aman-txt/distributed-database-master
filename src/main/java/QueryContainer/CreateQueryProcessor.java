package QueryContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import parser.QueryParser;
import parser.RegexConstant;

public class CreateQueryProcessor {

	private List<String> columns; // Columns to query

	private String tableName; // name of the table

	private String database; // name of the schema

	private List<String> datatype;
	
	private String primaryKey;
	
	private String refTable;
	
	private String foreginKey;
	
	private String refId;
	
	

	public CreateQueryProcessor() {
		this.columns = new ArrayList<String>();
		this.datatype = new ArrayList<String>();

	}

	public void parseCreateQuery(String createQuery) {

		parser1(RegexConstant.GET_COLUMNS_REGEX, createQuery);
		parser2(RegexConstant.CREATE_TABLE_REGEX, createQuery);

	}

	private void parser1(String regx, String query) {

		System.out.println(regx);
		final Pattern pattern = Pattern.compile(regx, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(query);

		while (matcher.find()) {
			// System.out.println("Full match: " + matcher.group(0));
			String temp = matcher.group(1).substring(1,matcher.group(1).length());

			String metadata[] = temp.split(",");

			for (String data : metadata) {
				data=" "+data;
				String columnData[] = data.trim().split("\\s+");


				this.columns.add(columnData[0]);
				this.datatype.add(columnData[1]);
				System.out.println(data);

				if (columnData.length > 2) {
					if(data.toLowerCase().contains("primary")) {
						this.primaryKey=columnData[0];
					}
					
					if(data.toLowerCase().contains("foreign")) {
						
						String refData=columnData[5];
						this.foreginKey=columnData[0];
						this.refTable=refData.substring(0, refData.indexOf("("));
						this.refId=refData.substring(refData.indexOf("(")+1, refData.indexOf(")"));
						
					}
					
				}

			}

			/*
			 * for (int i = 1; i <= matcher.groupCount(); i++) { System.out.println("Group "
			 * + i + ": " + matcher.group(i)); }
			 */
		}

	}

	private void parser2(String regx, String query) {

		System.out.println(regx);
		final Pattern pattern = Pattern.compile(regx, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(query);

		while (matcher.find()) {

			if (matcher.group(4).contains(".")) {
				database = matcher.group(4).substring(0, matcher.group(4).length());
			} else {
				tableName = matcher.group(4);
			}

		}

	}

	public List<String> getColumns() {
		return columns;
	}

	public String getTableName() {
		return tableName;
	}


	public String getDatabase() {
		return database;
	}


	public List<String> getDatatype() {
		return datatype;
	}


	public String getPrimaryKey() {
		return primaryKey;
	}

	

	public String getRefTable() {
		return refTable;
	}



	public String getRefId() {
		return refId;
	}

	
	public String getForeginKey() {
		return foreginKey;
	}

	@Override
	public String toString() {
		return "CreateQueryProcessor [columns=" + columns + ", tableName=" + tableName + ", database=" + database
				+ ", datatype=" + datatype + ", primaryKey=" + primaryKey + ", refTable=" + refTable + ", foreginKey="
				+ foreginKey + ", refId=" + refId + "]";
	}

	
	
}
