package parser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import parser.exception.InvalidQueryException;
import reverseEngineering.DrawERD;
import reverseEngineering.ErdExecutor;
import reverseEngineering.ReverseEngineering;

public class Console {

	boolean picker(int no) throws IOException {
		if (no == 1) {
			WriteQueries wq = new WriteQueries();

			boolean chk = wq.manager();
			if (!chk) {
				System.out.println("Something went wrong");
			}
			return true;
		}
		if (no == 2) {

			return true;
		}
		if (no == 3) {

			return true;
		}
		if (no == 4) {

			return true;
		}
		if (no == 5) {
			ErdExecutor erdExecutor = new ErdExecutor();

			erdExecutor.doReverseEngineering();

			return true;
		}
		return false;
	}

	void userInput() throws IOException {

		Scanner sc = new Scanner(System.in);
		System.out.println();

		System.out.println("======MENU======");
		System.out.println();

		System.out.println("1. Write Queries");
		System.out.println("2. Export");
		System.out.println("3. Data Model");
		System.out.println("4. Analysis");
		System.out.println("5. Reverse Engineering");
		int no = sc.nextInt();
		Boolean success = picker(no);
		// if(!success){
		// System.out.println("Invalid selection, select again");
		userInput();
		// }

	}

	void auth(Boolean passed) throws IOException {

		LoginSignup ls = new LoginSignup();
		if (passed) {
			Console con = new Console();
			Boolean flag = true;
			while (flag) {
				Scanner sc = new Scanner(System.in);
				/*
				 * String quit="quit"; System.out.println("press 'q' to exit");
				 * 
				 * if(quit.equalsIgnoreCase(sc.next())){ flag=false; }s
				 */
				con.userInput();

				sc.close();

			}
			System.out.println("Quitted!!");
		} else {
			System.out.println("Try Again!!");
			passed = ls.runHere();
			auth(passed);
		}
	}

	public static void main(String[] string) throws IOException {
		LoginSignup ls = new LoginSignup();
		Boolean passed = ls.runHere();
		Console con = new Console();
		con.auth(passed);

	}
}
