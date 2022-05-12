package parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import QueryContainer.*;
import parser.exception.InvalidQueryException;
import query.container.CreateQuery;

public class QueryParserExecutor {

	private QueryParser queryParser;

	private CreateQueryProcessor createQueryProcessor;

	private InsertQueryProcessor insertQueryProcessor;

	private DeleteQueryProcessor deleteQueryProcessor;
	
	private SelectQueryProcessor selectQueryProcessor;
	
	private UpdateQueryProcessor updateQueryProcessor;

	private UseDatabaseQueryProc useDatabaseQueryProc;

	private CreateDatabaseProcessor createDatabaseProc;

	private CreateQuery createQuery;


	public QueryParserExecutor() {
		this.queryParser = new QueryParser();

	}

	public boolean processQuery(String query) throws InvalidQueryException {

		boolean isQueryProcessed = false;

		isQueryProcessed = this.queryParser.parseQuery(query);

		if (!isQueryProcessed) {
			throw new InvalidQueryException(this.queryParser.getErrorMessage());
		}
		if (isCreDbQuery(query)) {
			this.createDatabaseProc = new CreateDatabaseProcessor();
			createDatabaseProc.parseCreDataQuery(query);
			System.out.println(createDatabaseProc.toString());
		}

		if (query.toLowerCase().contains("create")) {
			this.createQueryProcessor = new CreateQueryProcessor();
			createQueryProcessor.parseCreateQuery(query);
			// create object of createQuery using createQueryProcessor
			// this.createQuery=new CreateQuery(createQueryProcessor.getColumns(),);
			System.out.println(createQueryProcessor.toString());
		}

		if (query.toLowerCase().contains("insert")) {
			this.insertQueryProcessor = new InsertQueryProcessor();
			insertQueryProcessor.parseInsertQuery(query);
			System.out.println(insertQueryProcessor.toString());
		}

		if (query.toLowerCase().contains("delete")) {
			this.deleteQueryProcessor = new DeleteQueryProcessor();
			deleteQueryProcessor.parseDeleteQuery(query);
			System.out.println(deleteQueryProcessor.toString());
		}
		
		
		if (query.toLowerCase().contains("select")) {
			this.selectQueryProcessor = new SelectQueryProcessor();
			selectQueryProcessor.parseSelectQuery(query);
			System.out.println(selectQueryProcessor.toString());
		}

		if (query.toLowerCase().contains("use")) {
			this.useDatabaseQueryProc = new UseDatabaseQueryProc();
			useDatabaseQueryProc.parseUseQUery(query);
			System.out.println(useDatabaseQueryProc.toString());
		}


		
		
		if (query.toLowerCase().contains("update")) {
			this.updateQueryProcessor = new UpdateQueryProcessor();
			updateQueryProcessor.parseUpdateQuery(query);
			System.out.println(updateQueryProcessor.toString());
		}
		 

		return isQueryProcessed;
	}

	public boolean isCreDbQuery(String query) {
		boolean isCreDbQuery=false;

		final Pattern pattern = Pattern.compile(RegexConstant.CREATE_DATA_REGEX, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(query);

		if(matcher.find()) {
			isCreDbQuery=true;
		}

		return isCreDbQuery;
	}

	public CreateQueryProcessor getCreateQueryProcessor() {
		return createQueryProcessor;
	}

	public InsertQueryProcessor getInsertQueryProcessor() {
		return insertQueryProcessor;
	}

	public DeleteQueryProcessor getDeleteQueryProcessor() {
		return deleteQueryProcessor;
	}

	public SelectQueryProcessor getSelectQueryProcessor() {
		return selectQueryProcessor;
	}

	public UseDatabaseQueryProc getUseDatabaseQueryProc() {
		return useDatabaseQueryProc;
	}

	public CreateDatabaseProcessor getCreateDatabaseProc() {
		return createDatabaseProc;
	}

}
