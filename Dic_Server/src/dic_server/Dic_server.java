//John Dan
//694911
package dic_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

public class Dic_server {
	public static void main(String[] args) {
			//Date
	
		
			//Create a server socket listening on port 4444
			@SuppressWarnings("resource")
			ServerSocket listeningSocket = null;

	        // Object that will store the parsed command line arguments.
	        serCommand cmmdarg = new serCommand();

	        // Parser provided by args4j.
	        CmdLineParser parser = new CmdLineParser(cmmdarg);


			int i = 0; //counter to keep track of the number of clients
			
			try {
				parser.parseArgument(args);
				listeningSocket = new ServerSocket(cmmdarg.getPort());
				
			
			//Listen for incoming connections for ever 
				while (true) 	
					{
					System.out.println("Server listening on port "+cmmdarg.getPort()+" for a connection");
					//Accept an incoming client connection request 
					Socket clientSocket = listeningSocket.accept(); //This method will block until a connection request is received
					Multithread cliThread = new Multithread(clientSocket,i++,cmmdarg.getFile());
					new Thread(cliThread).start();
					//Get the input/output streams for reading/writing data from/to the socket
			
					}
			}
			//  Failure model
			catch(CmdLineException e) {
				System.out.println("Parsing errors.");
	            System.exit(0);
			}catch (SocketException ex)
    		{
    			ex.printStackTrace();
    		}
			catch (IOException e) {
	            System.out.println("Error setting up socket.");
			}catch(Exception e){
	    			System.out.println("Some error has come up");
	    			e.printStackTrace();
			}finally{
	    			if(listeningSocket != null)
	    			{
	    				try
	    				{
	    					listeningSocket.close();
	    				}
	    				catch (IOException e) 
	    				{
	    					 System.out.println("Error closing up socket.");
	    					e.printStackTrace();
	    				}
	    			}
	    		}

	}
}

