package reverseEngineering;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import QueryContainer.UseDatabaseQueryProc;
import parser.QueryParserExecutor;
import parser.exception.InvalidQueryException;
import query.container.CreateSchema;
import query.manager.SchemaHandler;
import query.response.Response;
import query.response.ResponseType;

public class ErdExecutor {

	public static String dbName = "";

	private QueryParserExecutor queryParserExecutor;

	private UseDatabaseQueryProc useDatabaseQueryProc;

	public ErdExecutor() {
		this.queryParserExecutor = new QueryParserExecutor();
	}

	String takeInput() {
		Scanner sc = new Scanner(System.in);

		System.out.println("Please select database:");
		String query = sc.nextLine();
		return query;
	}

	public void doReverseEngineering() throws IOException {

		String query = takeInput();

		if (query == null) {
			System.out.println("Database is not selected");
		} else {
			try {
				this.queryParserExecutor.processQuery(query);
				this.useDatabaseQueryProc=this.queryParserExecutor.getUseDatabaseQueryProc();
				CreateSchema createSchema = new CreateSchema(useDatabaseQueryProc.getDbName());
				Response response = SchemaHandler.checkSchemaQuery(createSchema);
				if (response.getResponseType().toString().equals(ResponseType.SUCCESS.toString())) {
					this.dbName = useDatabaseQueryProc.getDbName();
				}
				printResponse(response.getResponseType().toString(), response.getDescription());
				String erd=printErd();
				System.out.println(erd);
			} catch (InvalidQueryException e) {
				System.out.println(e.getErrorMsg());
			}
		}
	}

	private String printErd() throws IOException {
		ReverseEngineering reverseEngineering = new ReverseEngineering();
		String[] rankOrder = reverseEngineering.getRankOrder(ErdExecutor.dbName);

		DrawERD drawERD = new DrawERD();
		HashMap<String, HashMap<String, String[]>> dependencyGraph = reverseEngineering.getDependencyGraph();
		String erd = drawERD.draw(rankOrder, reverseEngineering.getTableMetadata(this.dbName, "metadata_"), dependencyGraph);
		return erd;
	}

	private void printResponse(String status, String desc) {
		System.out.println(status + ":" + desc);
	}
}
