package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import parser.exception.InvalidQueryException;

public class QueryParser {

	private String errorMessage;

	public boolean parseQuery(String query) {

		boolean isQueryValid = false;

		if (query == null || query.trim().equals("")) {
			this.errorMessage = "Invalid sql query";
		}

		if (query.toLowerCase().contains("create")) {

			isQueryValid = parse(query);

			if (!isQueryValid) {
				this.errorMessage = "Invalid create query syntax";
			}

		}

		if (query.toLowerCase().contains("insert")) {

			boolean queryType1 = isQueryValid(query, RegexConstant.INSERT_WITHOUT_COLUMN);
			boolean queryType2 = isQueryValid(query, RegexConstant.INSERT_WITH_COLUMN);

			if (queryType1 | queryType2) {
				isQueryValid = true;
			}

			if (!isQueryValid) {
				this.errorMessage = "Invalid insert query syntax";
			}

		}

		if (query.toLowerCase().contains("delete")) {

			isQueryValid = isQueryValid(query, RegexConstant.DELETE_REGEX);

			if (!isQueryValid) {
				this.errorMessage = "Invalid delete query syntax";
			}

		}
		
		
		if (query.toLowerCase().contains("select")) {

			isQueryValid = isQueryValid(query, RegexConstant.SELECT_REGEX);

			if (!isQueryValid) {
				this.errorMessage = "Invalid select  query syntax";
			}

		}

		if (query.toLowerCase().contains("update")) {

			isQueryValid = isQueryValid(query, RegexConstant.UPDATE_REGEX);

			if (!isQueryValid) {
				this.errorMessage = "Invalid update  query syntax";
			}

		}
		if (query.toLowerCase().contains("use")) {

			isQueryValid = isQueryValid(query, RegexConstant.USE_REGEX);

			if (!isQueryValid) {
				this.errorMessage = "Invalid use database  query syntax";
			}

		}

		if (query.toLowerCase().contains("create") && isCreDbQuery(query)) {

			isQueryValid = true;
		}



		return isQueryValid;
	}

	private boolean parse(String query) {
		boolean isQueryValid = false;

		final Pattern pattern = Pattern.compile(RegexConstant.CREATE_TABLE_REGEX,
				Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(query);

		while (matcher.find()) {
			isQueryValid = true;
		}

		return isQueryValid;
	}

	public boolean isQueryValid(String query, String regex) {
		boolean isQueryValid = false;

		Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);

		String quotedString = Pattern.quote(query);

		Matcher matcher = pattern.matcher(quotedString);

		while (matcher.find()) {
			isQueryValid = true;
		}
		return isQueryValid;
	}

	private boolean isCreDbQuery(String query) {
		boolean isCreDbQuery=false;

		final Pattern pattern = Pattern.compile(RegexConstant.CREATE_DATA_REGEX, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(query);

		if(matcher.find()) {
			isCreDbQuery=true;
		}

		if(!isCreDbQuery) {
			this.errorMessage="Invalid create database  query syntax";
		}

		return isCreDbQuery;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
