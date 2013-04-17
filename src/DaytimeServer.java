import java.io.*;
import java.net.*;
import java.util.Date;   // for obtaining a timestamp

/**
 * This module contains the application logic of a Daytime server
 *   which uses connection-oriented datagram socket for IPC.
 * A command-line argument is required to specify the server port.
 * @author M. L. Liu
 */
public class DaytimeServer {
	
   public static void main(String[] args) {
	   
      int serverPort = 5050;    // default port
      if (args.length == 1 )
         serverPort = Integer.parseInt(args[0]);       
      try {
         // instantiates a stream socket for accepting
         //   connections
   	   ServerSocket myConnectionSocket = new ServerSocket(serverPort); 
         System.out.println("Daytime server ready.");
        
         while (true){  // forever loop
            // wait to accept a connection
/**/        System.out.println("Waiting for a connection.");
            MyStreamSocket myDataSocket = new MyStreamSocket(myConnectionSocket.accept( ));
      //System.out.println("the meshshhsdshdh "+  myDataSocket.receiveMessage( ) );
            
            Thread theThread = new Thread(new ServerThread(myDataSocket));
            theThread.start();
            
            // Note: there is no need to read a request - the
            // request is implicit.            
            
           
/**/        System.out.println("A client has made connection.");
            Date timestamp = new Date ();
/**/        System.out.println("timestamp sent: "+ timestamp.toString());
            // Now send the reply to the requester
            myDataSocket.sendMessage(timestamp.toString( ));
            myDataSocket.close( );
            
    

		   } //end while
       } // end try
       catch (Exception ex) {
          ex.printStackTrace( );
       } // end catch
   } //end main
} // end class




class ServerThread implements Runnable {
	private static int ThreadNUm=0;
	   static final String endMessage = ".";
	   MyStreamSocket myDataSocket;

	   ServerThread(MyStreamSocket myDataSocket) {
	      this.myDataSocket = myDataSocket;
	   }
	 
	   public void run( ) {
		   
		   System.out.println("yeayyyyy... the thread is working");
		   System.out.println(ThreadNUm++);
	      boolean done = false;
	      String message;
	      try {
	         while (!done) {
	             message = myDataSocket.receiveMessage( );
	/**/         System.out.println("message received: "+ message);
	             if ((message.trim()).equals (endMessage)){
	                //Session over; close the data socket.
	/**/            System.out.println("Session over.");
	                myDataSocket.close( );
	                done = true;
	             } //end if
	             else {
	                // Now send the echo to the requestor
	                myDataSocket.sendMessage(message);
	             } //end else
	          } //end while !done
	        }// end try
	        catch (Exception ex) {
	           System.out.println("Exception caught in thread: " + ex);
	        } // end catch
	   } //end run
	} //end class 


