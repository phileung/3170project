//java -classpath ./ojdbc6.jar:./ 

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class ManaMenu {
	public static void printMenu() {
		Connection conn = null;
		try {	
			System.out.print("Driver loading...");
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception x) {	
			System.out.println("Failed");
			System.out.println("Unable to load the driver class!");
			return;	
		}
		try {
			conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@db12.cse.cuhk.edu.hk:1521:db12",
				"d019",
				"xysqchsz");
		} catch (Exception e) {
			System.out.println("Failed!");
			System.out.println(e.getMessage());
			System.out.println("Unable to connect to the database! Check your network!");
			System.exit(0);
		}		
		try {
			printlog();
			String input = "";
			int choice = 0;
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			Statement stmt;
			ResultSet rs;
			while (choice < 1 || choice > 4) {
				try {
					input = in.readLine();
					choice = Integer.parseInt(input);
					if (choice < 1 || choice > 4)
						throw new NumberFormatException();
				} catch (NumberFormatException e) {
					System.out.println("Choice should be 0-4.");
				} catch (IOException e) {}
			}
			switch (choice) {
			case 1:
				int sID = 0;
				String startDate = "", endDate = "";
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				while (sID < 1) {
					try {
						System.out.println("Enter The Salesperson ID: ");
						input = in.readLine();
						sID = Integer.parseInt(input);
						if (sID < 1)
							throw new NumberFormatException();
					} catch (NumberFormatException e) {
						System.out.println("sID should be > 0.");
					} catch (IOException e) {}
				}
				try {
					System.out.print("Type in the starting date [dd/mm/yyyy]: ");
					startDate = in.readLine();
					
				} catch (IOException e) {}
				try {
					System.out.print("Type in the ending date [dd/mm/yyyy]: ");
					endDate = in.readLine();
				} catch (IOException e) {}
				System.out.println("Transaction Record:\n" + 
					"| ID | Part ID | Part Name | Manufacturer | Price | Date |");
				PreparedStatement ps = conn.prepareStatement(
					"SELECT t.tID, p.pID, p.pName, m.mName, p.pPrice, t.tDate " +
					"FROM transaction t, part p, manufacturer m, salesperson s " +
					"WHERE t.sID = ? AND to_date(?,'dd/mm/yyyy') <= t.tDate " +
						"AND t.tDate <= to_date(?,'dd/mm/yyyy') " +
						"AND t.pID = p.pID AND p.mID = m.mID AND t.sID = s.sID " +
						"ORDER BY t.tDate DESC");
				ps.setInt(1, sID);
				ps.setString(2, startDate);
				ps.setString(3, endDate);
				rs = ps.executeQuery();
				while (rs.next()) {
					System.out.println("| " + rs.getInt(1) + " | " +
						rs.getInt(2) + " | " + rs.getString(3) + " | " +
						rs.getString(4) + " | " + rs.getInt(5) + " | " +
						sdf.format(new Date(rs.getDate(6).getTime())) + " |");
				}
				System.out.println("End of Query\n");
				break;
			case 2:
				System.out.println("| Manufacturer ID | Manufacturer Name " +
				"| Total Sales Value |");
				stmt = conn.createStatement();
				rs = stmt.executeQuery(
					"SELECT m.mID, m.mName, SUM(p.pPrice) AS totalSales " +
					"FROM manufacturer m, transaction t, part p " +
					"WHERE m.mID = p.mID AND p.pID = t.pID " +
					"GROUP BY m.mID, m.mName " +
					"ORDER BY totalSales DESC");
				while (rs.next()) {
					System.out.println("| " + rs.getInt(1) + " | " +
						rs.getString(2) + " | " + rs.getInt(3) + " |");
				}
				System.out.println("End of Query\n");
				break;
			case 3:
				int numOfParts = 0;
				while (numOfParts < 1) {
					try {
						System.out.print("Type in the number of parts: ");
						input = in.readLine();
						numOfParts = Integer.parseInt(input);
						if (numOfParts < 1)
							throw new NumberFormatException();
					} catch (NumberFormatException e) {
						System.out.println("Number of parts should be > 0.");
					} catch (IOException e) {}
				}
				stmt = conn.createStatement();
				rs = stmt.executeQuery(
					"SELECT p.pID, p.pName, COUNT(*) AS transCount " +
					"FROM transaction t, part p " +
					"WHERE p.pID = t.pID " +
					"GROUP BY p.pID, p.pName " +
					"ORDER BY transCount DESC");
				for (; numOfParts > 0 && rs.next(); numOfParts--) {
					System.out.println("| " + rs.getInt(1) + " | " +
						rs.getString(2) + " | " + rs.getInt(3) + " |");
				}
				System.out.println("End of Query\n");
				break;
			case 4:
			
			}
		} catch (SQLException e) {
			System.out.println("SQLException");
			System.out.println(e);
		}
	}
	
	private static void printlog() {
		System.out.println("\n-----Operations for manager Menu-----");
		System.out.println("What kinds of operation would you like to perform?");
		System.out.println("1. Show the sales record of a sales person within a period");
		System.out.println("2. Show the total sales value of each manufacturer");
		System.out.println("3. Show the N mowt popular part");
		System.out.println("4. Return to the main Menu");
		System.out.print("Enter your Choice: ");
	}	
}
