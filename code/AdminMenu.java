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
			opFlag = false;
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
			opFlag = false;
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
				
				System.out.print("Processing...");
				loadData(inPath);
				opFlag = false;
			}
			else if(input.equals("4"))
			{	
				//show numer
				//System.out.println("show number");
				countTable();
				opFlag = false;
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
	System.out.print("Enter Your Choice: ");
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
	//connect first
	Connection conn = DriverManager.getConnection(
	"jdbc:oracle:thin:@db12.cse.cuhk.edu.hk:1521:db12",
	"d019",
	"xysqchsz");
	BufferedReader inFile1 = new BufferedReader(new FileReader(new File(path+"/category.txt")));
	BufferedReader inFile2 = new BufferedReader(new FileReader(new File(path+"/manufacturer.txt")));
	BufferedReader inFile3 = new BufferedReader(new FileReader(new File(path+"/part.txt")));
	BufferedReader inFile4 = new BufferedReader(new FileReader(new File(path+"/salesperson.txt")));
	BufferedReader inFile5 = new BufferedReader(new FileReader(new File(path+"/transaction.txt")));
	String indata = null;
	//line by line category
	while ((indata = inFile1.readLine()) != null)
	{
	String[] tokens = null;
	tokens = indata.split("\t");
	//System.out.print(tokens[0]+" "); testing haha
	//System.out.println(tokens[1]);
	PreparedStatement pstmt = conn.prepareStatement(
	"INSERT INTO category VALUES(?,?)");
	pstmt.setString(1, tokens[0]);
	pstmt.setString(2, tokens[1]);
	pstmt.executeUpdate();	
	}
	// manufacturer
	while ((indata = inFile2.readLine()) != null)
	{
	String[] tokens = null;
	tokens = indata.split("\t");
	//System.out.print(tokens[0]+" "); testing haha
	//System.out.println(tokens[1]);
	PreparedStatement pstmt = conn.prepareStatement(
	"INSERT INTO manufacturer VALUES(?,?,?,?,?)");
	pstmt.setString(1, tokens[0]);
	pstmt.setString(2, tokens[1]);
	pstmt.setString(3, tokens[2]);
	pstmt.setString(4, tokens[3]);
	pstmt.setString(5, tokens[4]);	
	pstmt.executeUpdate();	
	}
	// part
	while ((indata = inFile3.readLine()) != null)
	{
	String[] tokens = null;
	tokens = indata.split("\t");
	//System.out.print(tokens[0]+" "); testing haha
	//System.out.println(tokens[1]);
	PreparedStatement pstmt = conn.prepareStatement(
	"INSERT INTO part VALUES(?,?,?,?,?,?)");
	pstmt.setString(1, tokens[0]);
	pstmt.setString(2, tokens[1]);
	pstmt.setString(3, tokens[2]);
	pstmt.setString(4, tokens[3]);
	pstmt.setString(5, tokens[4]);
	pstmt.setString(6, tokens[5]);	
	pstmt.executeUpdate();	
	}
	// salesperson
	while ((indata = inFile4.readLine()) != null)
	{
	String[] tokens = null;
	tokens = indata.split("\t");
	//System.out.print(tokens[0]+" "); testing haha
	//System.out.println(tokens[1]);
	PreparedStatement pstmt = conn.prepareStatement(
	"INSERT INTO salesperson VALUES(?,?,?,?)");
	pstmt.setString(1, tokens[0]);
	pstmt.setString(2, tokens[1]);
	pstmt.setString(3, tokens[2]);
	pstmt.setString(4, tokens[3]);	
	pstmt.executeUpdate();	
	}		
	// transaction
	while ((indata = inFile5.readLine()) != null)
	{
	String[] tokens = null;
	tokens = indata.split("\t");
	//System.out.print(tokens[0]+" "); testing haha
	//System.out.println(tokens[1]);
	PreparedStatement pstmt = conn.prepareStatement(
	"INSERT INTO transaction VALUES(?,?,?,to_date(?,'dd/mm/yyyy'))");
	pstmt.setString(1, tokens[0]);
	pstmt.setString(2, tokens[1]);
	pstmt.setString(3, tokens[2]);
	pstmt.setString(4, tokens[3]);	
	pstmt.executeUpdate();	
	}
	conn.close();
	System.out.print("Done! ");
	System.out.println("Data is inputted to the database!");		
	
	}
	catch(Exception e){
	System.out.println("Failed! ");
	System.out.println(e.getMessage());
	System.out.println("Please check the input file");
	
	}
	}
	
	//count the table
	private static void countTable()
	{
	try
	{
	//connect first
	Connection conn = DriverManager.getConnection(
	"jdbc:oracle:thin:@db12.cse.cuhk.edu.hk:1521:db12",
	"d019",
	"xysqchsz");
	Statement stmt = conn.createStatement();
	Statement stmt3 = conn.createStatement();
	//find the table list
	ResultSet dbList = stmt.executeQuery("SELECT * FROM cat");
	ResultSet dbCheck = stmt3.executeQuery("SELECT * FROM cat");
	boolean tableFlag = false;
	if(dbCheck.next())
	{
	System.out.println("Number of records in each table:");
	while(dbList.next())
	{
	Statement stmt2 = conn.createStatement();
	String tableName = dbList.getString("TABLE_NAME");
	ResultSet tableCount = stmt2.executeQuery("SELECT count(*) AS COUNT FROM "+tableName);
	//while(tableCount.next()){
	tableCount.next();
	System.out.println(tableName+": "+tableCount.getInt("COUNT"));
	//}
	stmt2.close();
	}
	}
	else
	{
	System.out.println("There is no table");
	}
	stmt.close();
	conn.close();
	}
	catch(Exception e)
	{
	System.out.println(e.getMessage());
	}
	}
	
}
