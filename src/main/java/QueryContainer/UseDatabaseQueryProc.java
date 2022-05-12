package QueryContainer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import parser.RegexConstant;

public class UseDatabaseQueryProc {

	private String dbName;

	public void parseUseQUery(String useQuery) {

		final Pattern pattern = Pattern.compile(RegexConstant.USE_REGEX, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(useQuery);

		while (matcher.find()) {
			this.dbName = matcher.group(3);
		}

	}

	public String getDbName() {
		return dbName;
	}

	@Override
	public String toString() {
		return "UseDatabaseQueryProc [dbName=" + dbName + "]";
	}

	

	

	
}
