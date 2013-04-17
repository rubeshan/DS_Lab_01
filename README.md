DS_Lab_01
=========

Distributed System's first lab session


Lab#1 – Client / Server Communications

Objective
In this lab you will modify examples of client server applications that are both single and multi-threaded. The first modifications are to support user input and output to acquire the date and time of a server. 
Finally these applications are modified to query the Mindstorms NXT BRIC. In Lab 0, the preparation lab, you made certain that you could communicate with the Mindstorms NXT BRIC. 
A. The Client / Server Communication Model
The client / server communication model is the most prevalent distributed communication model. It is service-oriented, and employs a request-response protocol. A server process, running on a server host, provides access to a service. A client process, running on a client host, accesses the service via the server process. The interaction of the process proceeds according to a protocol.
A protocol/service session
In the context of the client-server model, we will use the term session to refer to the interaction between the server and one client. The service managed by a server may be accessed by multiple clients who desire the service, sometimes concurrently. Each client, when serviced by the server, engages in a separate session with the server, during which it conducts a dialog with the server until the client has obtained the service it required.
A protocol is needed to specify the rules that must be observed by the client and the server while conducting a service. Such rules include specifications on matters such as
(i)  how the service is to be located,
(ii)	the sequence of inter-process communication, and,
(iii)	the representation and interpretation of data exchanged with each IPC. On the Internet, such protocols are specified in the RFCs.
Software Engineering for a Network Service
Client / server applications follow a simple hierarchical design abstraction that includes presentation, application, and service layers as shown below. 

Presentation Layer: Encapsulates the client-side presentation logic; that is, it provides the interface for a user of the client process. The code in this class is concerned with obtaining input / output from the user.

Application Layer: Encapsulates the client-side application logic. The details of using datagram sockets are hidden from this module. This module manages the Socket connections and performs the IPC for sending a request and receiving a response that are particular to the application protocol. It defines methods like sendHttpRequest (for say an HTTP message), done, etc. This module does not need to deal with the byte array for carrying the payload data.

Service Logic: Provides the details of the IPC service. This class tends to extend an existing Socket class and defines the sendMessage and receiveMessage methods to handle IPC communications. It also maintains a communication buffer. 

It is good software engineering design to follow this abstraction when designing a client / server application.
B. The Java stream socket API (Single Thread Example)
Java supports both connection and connectionless socket communications. Connectionless socket communications is done using the DatagramPacket over a DatagramSocket. Connection oriented communications is done utilizing the ServerSocket stream along with DatagramSocket. In this lab we will focus on stream oriented connection.
 
In Java, the stream-mode socket API is provided with two classes: 
	ServerSocket: for accepting connections and
	Socket: for data exchange.
Pictorially this is shown in the figure below.


Key methods in the ServerSocket class

 
Key methods in the Socket class

Server Side Code
The following are the basic Java lines of code to support connection oriented communications from the server’s perspective

   	ServerSocket connectionSocket = new ServerSocket(portNo);
        // wait to accept a connecion request, at which
        //  time a data socket is created                  
        Socket dataSocket = connectionSocket.accept();

It is then possible to create read or write streams for the data socket in the following manner. Note that the input stream uses a BufferedReader and the output a PrintWriter for character input and output.
            
	InputStream inStream = dataSocket.getInputStream();
      	input = new BufferedReader(new InputStreamReader(inStream));
      	OutputStream outStream = dataSocket.getOutputStream();
	output = new PrintWriter(new OutputStreamWriter(outStream));
      
Note: Any output to the socket needs to be flushed.

        output.flush();

At the end of communications the socket and connection needs to be closed.

        dataSocket.close( );
        connectionSocket.close( );
Client Side Code
Client side coding is much simpler in that is only necessary to request for a connection by simply utilizing the Socket constructor.

	Socket mySocket = new Socket(acceptorHost, acceptorPort);

Similar to the server all input and output is performed using the data stream objects.

An example of a Daytime application is included that services client requests for the time on a server. The following Java files should be available with the lab.

	DaytimeServer.java (Server Application logic)
	DaytimeClient.java (Client Presentation logic)
	DaytimeClientHelper.java (Client Application logic)
	MyStreamSocket.java (Server and Client Service logic)

1.	Compile and execute the Daytime examples.
2.	 With the knowledge and Daytime examples above modify the DaytimeServer and DaytimeClient application so that the client interacts with the user through a simple command window prompt and the server responds to commands that the user inputs based on the requirements listed below:
a.	When the user inputs the letter “d” the server will return the date and time.
b.	When the user inputs a “.” this signifies that the end of the session and the client shuts down.
Make certain to utilize the abstraction layer design for your application. Submit a UML diagram of your design as well as code and execution examples.
Presentation to the TA:
Show the TA the running program.
C. The Java stream socket API (Multi-Threaded Example)
In the previous exercise you should have created a single threaded server application Single thread communications is not scalable because only one connection can be supported at a time. Multi-threaded communications are commonly used to support multiple server connections as well as to support I/O from other sources like that encountered from a (Chat programs have this characteristic, where a user can enter input into a window pane as well as receive input from a remote client).

In Java this can be easily done by instantiating the class that will manage the connection in a new thread for each requested connection as shown in the example below. The lines where a new thread is created are in bold.

ServerSocket myConnectionSocket = 
      new ServerSocket(serverPort);  
         while (true) {  // forever loop
             MyStreamSocket myDataSocket = new   
                 MyStreamSocket
                      (myConnectionSocket.accept( ));
            Thread theThread = 
                new Thread(new  
                             ServerThread(myDataSocket));
            theThread.start();
} //end while forever

In this example the ServerThread class is the class defined to service the connection. This ServerThread class must be defined as Runnable and has in it the application code to service the incoming requests.

class ServerThread implements Runnable {
   static final String endMessage = ".";
   MyStreamSocket myDataSocket;
   EchoServerThread(MyStreamSocket myDataSocket) {
      this.myDataSocket = myDataSocket;
   }
   public void run( ) {
      boolean done = false; String message;
     try {  // put in here the logic for each client session
         ………
        }// end try
        catch (Exception ex) {
       }
   } //end run
} //end class

1.	Modify the DaytimeServer application you developed above that responds to user commands so that it can manage multiple concurrent connections using multi-threading.
2.	Make certain that you try multiple connections to the server and submit the code and test results.
Presentation to the TA:
Show the TA the running program and multiple connection examples.
D. Connecting to an actual device
In this section of the lab you will modify the multi-threaded client server example you developed in section D to open a connection with the NXT BRIC and allow the user to query the sensor and run the motor.
To add the NXT communications in principle is rather straight forward.
1.	Make a copy of the multi threaded DaytimeServer and DaytimeServerThread programs. Name them something like NXTServer and NXTServerThread.
2.	To the code in the NXTServer you will have to initiate a connection to the NXT BRIC before executing any other code. This is because you want to make certain that the connection to the NXT has been established before proceeding. Below is an example of this type of code:

		NXTServerComm myNXTComm = new NXTServerComm();
		if (myNXTComm.open()) {
			System.out.println("Connection established with NXT.");

3.	In this example there is a custom Class called NXTServerComm that you need to define that contains methods and code to communicate with the NXT similar to that presented in Lab 0. The open() method simply establishes a connection to the NXT and returns true if the connection has been established or false if a failure occurred. Define some other methods like getLightSensor() and startMotor() and stopMotor() that simply call the corresponding NXT methods that were presented in Lab 0.
4.	To service the client’s connection you have to modify the ServerThread example, call it NXTServerThread, so that it will services the client’s request and communicate that request to the NXT BRIC. As an example, this thread will service commands sent by the client like “l” for read light sensor, “f” for forward, “s” for stop, and “.” For disconnecting. Write this class.
5.	The client class should not change from that used in section C because the user is the one that will input the commands through a UI. Therefore they can enter the new commands as single characters. Since the client also prints out the results returned from the server it will print out the light sensor reading.

Presentation to the TA:
Show the TA the running program and cases where the light sensor is read and the motor is turned off and on.
D. Food for Thought
The client / server example that you created in section D that communicates with the NXT BRIC is a good example of a server that services a single resource. It did not make sense to try to open multiple connections with the NXT BRIC since there is only one resource to manage. These types of client / server applications require that state full servers, i.e.  servers that keep information on the resource’s state. Explain the difference between state full and state less servers and list the type of state information you might  keep for the NXT BRIC.
