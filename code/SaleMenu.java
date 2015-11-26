import java.io.*;
import java.sql.*;
import java.util.Calendar;
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
				sell();
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
				String searchBy = "WHERE pName = '" + keyWord +"'";
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
				String searchBy = "WHERE mName = '" + keyWord +"'";
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
		private static void sell()
		{
				System.out.print("Enter The Part ID: ");
				BufferedReader in_partID = new BufferedReader(new InputStreamReader(System.in));
				String pid = null;
				try
				{
					pid = in_partID.readLine();
				}
				catch(IOException e)
				{
					System.out.println(e.getMessage());
				}
				System.out.print("Enter the Salesperson ID: ");
				BufferedReader in_saleID = new BufferedReader(new InputStreamReader(System.in));
				String sid = null;
				try
				{
					sid = in_saleID.readLine();
				}
				catch(IOException e)
				{
					System.out.println(e.getMessage());
				}
				try
				{
					Connection conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@db12.cse.cuhk.edu.hk:1521:db12",
					"d019",
					"xysqchsz");
					//check quantity of part
					String sql = "SELECT * " + 
								 "FROM part"+
								 " WHERE pID = "+ pid;
					//System.out.println(sql);			 
					PreparedStatement pstmt = conn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();
					int NumOFPart = 0;
					String NameOFPart = null;
					if(rs.next()){
						NumOFPart = rs.getInt("pAvailableQuantity");
						NameOFPart = rs.getString("pName");
							if (NumOFPart<=0){
								System.out.println("Failed! The quantity of the part with part ID: " +" is 0");
								return;
							}
					}
					else
					{
						System.out.println("Failed! The part with part ID: " + pid+" does not exist");
						return;
					}
					pstmt.close();
					// check if sale exist
							sql = "SELECT * " + 
								 "FROM salesperson "+
								 "WHERE sID = " + sid;
					PreparedStatement pstmt2 = conn.prepareStatement(sql);
					rs = pstmt2.executeQuery();					
					if(!rs.next()){
						System.out.println("Failed! The salesperson with salesperson ID: " +sid+" does not exist");
						return;
					}
					pstmt2.close();
					NumOFPart = NumOFPart - 1;
					//System.out.println("checked every thing");
					
					//at this step, it is sure that part quantity > 0 and sales exist
							sql = "UPDATE part " + 
								 "SET pAvailableQuantity = " + NumOFPart+
								 " WHERE pID = " + pid;				
					Statement stmt = conn.createStatement();
					stmt.executeUpdate(sql);
					stmt.close();
					Calendar Date = Calendar.getInstance();
					Date.add(Calendar.MONTH,1);
					String sellDate = Date.get(Calendar.DAY_OF_MONTH)+"/"+
									  Date.get(Calendar.MONTH)+"/"+
									  Date.get(Calendar.YEAR);
					//find what transaction id should use
							sql = "SELECT * " + 
								 "FROM transaction"+
								 " ORDER BY tID DESC";
					PreparedStatement pstmt3 = conn.prepareStatement(sql);
					rs = pstmt3.executeQuery();
					int tranID = 1;
					
					if(rs.next())
					{
						tranID = rs.getInt("tID") + 1;
					}
					else
					{
						tranID = 1;
					}
					pstmt.close();
					//System.out.println("ready for insert");
					PreparedStatement pstmt4 = conn.prepareStatement(
					"INSERT INTO transaction VALUES(?,?,?,to_date(?,'dd/mm/yyyy'))");
					pstmt4.setString(1, String.valueOf(tranID));
					pstmt4.setString(2, pid);
					pstmt4.setString(3, sid);
					pstmt4.setString(4, sellDate);	
					pstmt4.executeUpdate();
					pstmt4.close();
					conn.close();
					System.out.println("Product: "+NameOFPart+" (id: "+pid+") Remaining Quantity: "+NumOFPart);
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
				}
				
		}

	}