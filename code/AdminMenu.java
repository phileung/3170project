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
			System.out.print("Processing...");
			// category
			execute("CREATE TABLE category " +
					"(cID INTEGER not NULL, " +
					"cName VARCHAR(20), " +
					"PRIMARY KEY (cID))");
			// 	manufacturer			   
			execute("CREATE TABLE manufacturer " +
					"(mID INTEGER not NULL, " +
					"mName VARCHAR(20), " +
					"mAddress VARCHAR(50), " +
					"mPhoneNumber INTEGER, " +
					"mWarrantyPeriod INTEGER, " +
					"PRIMARY KEY (mID))");
			// part
			execute("CREATE TABLE part " +
					"(pID INTEGER not NULL, " +
					"pName VARCHAR(20), " +
					"pPrice INTEGER, " +
					"mID INTEGER, " +
					"cID INTEGER, " +
					"pAvailableQuantity INTEGER, " +
					"PRIMARY KEY (pID), " +
					"FOREIGN KEY (mID) REFERENCES manufacturer (mID), " +
					"FOREIGN KEY (cID) REFERENCES category (cID))");
			// salesperson
			execute("CREATE TABLE salesperson " +
					"(sID INTEGER not NULL, " +
					"sName VARCHAR(20), " +
					"sAddress VARCHAR(50), " +
					"sPhoneNumber INTEGER, " +
					"PRIMARY KEY (sID))");
			//transaction		
			execute("CREATE TABLE transaction " +
					"(tID INTEGER not NULL, " +
					"pID INTEGER, " +
					"sID INTEGER, " +
					"tDate DATE, " +
					"PRIMARY KEY (tID), " +
					"FOREIGN KEY (pID) REFERENCES part (pID), " +
					"FOREIGN KEY (sID) REFERENCES salesperson (sID))");
			System.out.print("Done! ");
			System.out.println("Database is initialized!");
			}
			else if(input.equals("2"))
			{
				//delete
				System.out.print("Processing...");
				execute("DROP TABLE transaction cascade constraints PURGE");
				execute("DROP TABLE salesperson cascade constraints PURGE");
				execute("DROP TABLE part cascade constraints PURGE");
				execute("DROP TABLE manufacturer cascade constraints PURGE");
				execute("DROP TABLE category cascade constraints PURGE");
			System.out.print("Done! ");
			System.out.println("Database is removed!");				

			}
			else if(input.equals("3"))
			{		
				//load
				System.out.print("Type in the Source Data Folder Path: ");
				BufferedReader in_for_path = new BufferedReader(new InputStreamReader(System.in));
				String inPath = null;
			try
			{
				inPath = in_for_path.readLine();
			}
			catch(IOException e)
			{
				
			}				
				loadData(inPath);
				
			}
			else if(input.equals("4"))
			{	
				//show numer
				System.out.println("show number");
			}
			else if(input.equals("5"))
			{
			//go back to mainMenu
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
	//run the sql
	private static void execute(String sql)
	{
	try
	{
	//System.out.print("Connecting to database...");
	Connection conn = DriverManager.getConnection(
	"jdbc:oracle:thin:@db12.cse.cuhk.edu.hk:1521:db12",
	"d019",
	"xysqchsz");
	//System.out.println("Done");
	Statement stmt = conn.createStatement();
	stmt.executeUpdate(sql);
	stmt.close();
	conn.close();
	}
	catch(Exception e)
	{
	System.out.println("Failed!");
	System.out.println(e.getMessage());
	System.out.println("Unable to connect to the database! Check your network!");
	System.exit(0);;
	}
	}
	
	//loaddata
	private static void loadData(String path)
	{
	try{
	BufferedReader inFile = new BufferedReader(new FileReader(new File(path+"/part.txt")));
	System.out.println("./"+path+"/part.txt");
	String d1 = null;
	//line by line
	while ((d1 = inFile.readLine()) != null)
	{
	//System.out.println(d1);
	}
	}
	catch(Exception e){
	System.out.println(e.getMessage());
	}
	}
}