import java.io.*;
import java.sql.*;
public class AdminMenu
{
	public static void printMenu()
	{
	try
	{	
		System.out.print("Driver loading...");
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
	}
	catch(Exception x)
	{	
		System.out.println("Failed");
		System.out.println("Unable to load the driver class!");
		System.out.println("Return to the Main Menu");
		return;	
	}
	System.out.println("Done");
	Connection conn = null;
	try
	{
	System.out.print("Connecting to database...");
	conn = DriverManager.getConnection(
	"jdbc:oracle:thin:@db12.cse.cuhk.edu.hk:1521:db12",
	"d019",
	"xysqchsz");
	}
	catch(Exception e)
	{
	System.out.println("Failed");
	System.out.println(e.getMessage());
	System.out.println("Unable to connect to the database!");
	System.out.println("Return to the Main Menu");
	return;
	}
	System.out.println("Done");
	boolean opFlag = true;
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	while(opFlag)
		{
			//Print out welcome log
			printlog();
			String input = "";
			//Read input
			try
			{
				input = in.readLine();
			}
			catch(IOException e)
			{
				
			}
			
			if(input.equals("1"))
			{
				//create
			System.out.println("create table");
			}
			else if(input.equals("2"))
			{
				//delete
				System.out.println("delete table");

			}
			else if(input.equals("3"))
			{		
				//load
				System.out.println("load data");

			}
			else if(input.equals("4"))
			{	
				//show numer
				System.out.println("show number");
			}
			else if(input.equals("5"))
			{
			//go back to mainMenu
				try{
				conn.close();
				}
				catch(Exception e)
				{
				System.out.println("unable to close database");
				}
				opFlag = false;
			}
			else
			{
				System.out.println("Unexcepted input, please enter 1, 2, 3, 4 or 5");
			}
			
		}
	}
	private static void printlog()
	{
	//System.out.println("Welcome to admin Menu!");
	System.out.println();
	System.out.println("-----Operations for administrator Menu-----");
	System.out.println("What kinds of operation would you like to perform?");
	System.out.println("1. Create all tables");
	System.out.println("2. Delete all tables");
	System.out.println("3. Load from datafile");
	System.out.println("4. Show number of records in each table");
	System.out.println("5. Return to the main menu");
	System.out.print("Enter your Choice: ");
	}	
	
}