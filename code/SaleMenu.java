import java.io.*;
import java.sql.*;
public class SaleMenu
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
				search();
			}
			else if(input.equals("2"))
			{

			}

			else if(input.equals("3"))
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
		System.out.println("-----Operations for salesperson Menu-----");
		System.out.println("What kinds of operation would you like to perform?");
		System.out.println("1. Search for parts");
		System.out.println("2. Sell a part");
		System.out.println("3. Return to the main menu");
		System.out.print("Enter Your Choice: ");
	}

	//search the parts
	private static void search()
	{
		boolean SearchFlag = true;
		BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
		while(SearchFlag)
		{
			//Print out welcome log
			System.out.println("Choose the Search criterion:");
			System.out.println("1. Part Name");
			System.out.println("2. Manufacturer Name");
			System.out.print("Choose the search criterion: ");
			String sinput = "";
			//Read input
			try
			{
				sinput = sin.readLine();
			}
			catch(IOException e)
			{

			}

			if(sinput.equals("1"))
			{
				System.out.print("Type in the Search Keyword: ");
				BufferedReader in_for_key = new BufferedReader(new InputStreamReader(System.in));
				String keyWord = null;
				try
				{
					keyWord = in_for_key.readLine();
				}
				catch(IOException e)
				{
				}
				String searchBy = "WHERE pName LIKE '" + keyWord +"'";
				executeSearch(searchBy);
				SearchFlag = false;
			}
			else if (sinput.equals("2"))
			{
				System.out.print("Type in the Search Keyword: ");
				BufferedReader in_for_key = new BufferedReader(new InputStreamReader(System.in));
				String keyWord = null;
				try
				{
					keyWord = in_for_key.readLine();
				}
				catch(IOException e)
				{
				}
				String searchBy = "WHERE mName LIKE '" + keyWord +"'";
				executeSearch(searchBy);
				SearchFlag = false;
			}
			else
			{
				System.out.println("Unexcepted input, please enter 1 or 2");
			}


		}
		}


		//run the sql
		private static void executeSearch(String searchBy)
		{
			//ordering, set default to prevent error
			String order = "ASC";
			boolean orderFlag = true;
			BufferedReader oin = new BufferedReader(new InputStreamReader(System.in));
			while(orderFlag)
			{
				//Print out welcome log
				System.out.println("Choose ordering: ");
				System.out.println("1. By price, ascending order");
				System.out.println("2. By price, descending order");
				System.out.print("Choose the search criterion: ");
				String oinput = "";
				//Read input
				try
				{
					oinput = oin.readLine();
				}
				catch(IOException e)
				{

				}

				if(oinput.equals("1"))
				{
					order = "ASC";
					orderFlag = false;
				}
				else if (oinput.equals("2"))
				{
					order = "DESC";
					orderFlag = false;
				}
				else
				{
					System.out.println("Unexcepted input, please enter 1 or 2");
				}

			}
			try
			{
				//System.out.print("Connecting to database...");
				Connection conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@db12.cse.cuhk.edu.hk:1521:db12",
				"d019",
				"xysqchsz");
				//System.out.println("Done");
				//Statement stmt = conn.createStatement();
				//String sql = "CREATE VIEW temp As" +
				//String sql	= "SELECT pID, pName, mName, cName, pAvailableQuantity, mWarrantyPeriod, pPrice" +
				String sql = "SELECT * " + 
				             "FROM Part NATURAL JOIN Manufacturer NATURAL JOIN Category "+
				             searchBy + 
				             " ORDER BY pPrice " + order;
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				System.out.println("| ID | Name | Manufacturer | Category | Quantity | Warranty | Price |");
				//stmt.executeUpdate(sql);
				//stmt.executeUpdate("SELECT temp" + "ORDER BY pPrice " + order);
				while(rs.next()){

					System.out.println(String.format("| %d | %s | %s | %s | %d | %d | %d |",
										rs.getInt("pID"),
										rs.getString("pName"),
										rs.getString("mName"),
										rs.getString("cName"),
										rs.getInt("pAvailableQuantity"),
										rs.getInt("mWarrantyPeriod"),
										rs.getInt("pPrice")));
				
				}
								   
				pstmt.close();
				conn.close();
			}
			catch(Exception e)
			{
				System.out.println("Failed!");
				System.out.println(e.getMessage());
				System.out.println("Unable to search parts! Make sure you have create the database!");
				System.exit(0);;
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