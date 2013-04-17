import java.io.*;
import javax.swing.JOptionPane;

/**
 * This module contains the presentaton logic of a DaytimeClient.
 * @author M. L. Liu
 */

public class DaytimeClient {
	static String Welcome = "Welcome to the Daytime client.\nd = TimeStamp . = Terminate";
	static String TimeStamp = "Here is the timestamp received from the server";
	static String Port = "What is the port number of the server host?";
	
   public static void main(String[] args) {
    /*  InputStreamReader is = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(is);
      		
      try {
         System.out.println(Welcome);
         String hostName = br.readLine();
         if (hostName.length() == 0) // if user did not enter a name
            hostName = "localhost";  //   use the default host name
         System.out.println(Port);
         String portNum = br.readLine();
         if (portNum.length() == 0)
            portNum = "13";          // default port number
         System.out.println(TimeStamp + DaytimeClientHelper.getTimestamp(hostName, portNum));
      } // end try  
      catch (Exception ex) {
         ex.printStackTrace( );
      } // end catch*/

      String input = getInput(Welcome);
      int c=0;
      while (input.equalsIgnoreCase("d") || !input.equalsIgnoreCase(".")){    	  
    	  if(input.equalsIgnoreCase("d")){    		 
    		  try {
				System.out.println(TimeStamp + DaytimeClientHelper.getTimestamp("localhost", "5050"));
			} catch (Exception e) {
				e.printStackTrace();
			}
    		  
    	  }else{
    		  input = getInput("Incorrect input was entered. Please re-enter the input.");
    		  
    	  }
    	input = getInput(Welcome);
    	c++;
      }
      System.out.println("The system was terminated");
      System.exit(1000);
   } //end main
   
   
   public static String getInput(String a){
	   String input = JOptionPane.showInputDialog(a);
	   return input;
   }
   
   
} // end class     
