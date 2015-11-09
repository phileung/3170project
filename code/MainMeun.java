import java.io.*;

public class MainMeun
{
	public static void main(String[] args)
	{
		
		boolean choiceFlag = true;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while(choiceFlag)
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
			
				AdminMeun AM = new AdminMeun();
				AM.printMeun();
				choiceFlag = false;
			}
			else if(input.equals("2"))
			{
				SaleMeun SM = new SaleMeun();
				SM.printMeun();
				choiceFlag = false;
			}
			else if(input.equals("3"))
			{			
				ManaMeun MM = new ManaMeun();
				MM.printMeun();
				choiceFlag = false;
			}
			else if(input.equals("4"))
			{	
				choiceFlag = false;
				System.out.print("ByeBye");
				System.exit(0);
			}
			else
			{
				System.out.println("Unexcepted input, please enter 1, 2, 3 or 4");
			}
			
		}
	}
	
	
	private static void printlog()
	{
	System.out.println("Welcome to sales system!");
	System.out.println();
	System.out.println("-----Main meun-----");
	System.out.println("What kinds of operation would you like to perform?");
	System.out.println("1. Operations for administrator");
	System.out.println("2. Operations for salesperson");
	System.out.println("3. Operations for manager");
	System.out.println("4. Exit this program");
	System.out.print("Enter your Choice: ");
	}	
	
	
}