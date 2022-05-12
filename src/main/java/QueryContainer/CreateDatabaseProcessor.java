package QueryContainer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import parser.RegexConstant;

public class CreateDatabaseProcessor {

	
	private String dbName;

	public void parseCreDataQuery(String creDataQuery) {

		final Pattern pattern = Pattern.compile(RegexConstant.CREATE_DATA_REGEX, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(creDataQuery);

		while (matcher.find()) {
			this.dbName = matcher.group(4);
		}

	}

	public String getDbName() {
		return dbName;
	}

	@Override
	public String toString() {
		return "CreateDatabaseProcessor [dbName=" + dbName + "]";
	}
	
	
}
