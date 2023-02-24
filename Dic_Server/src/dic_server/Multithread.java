//John Dan
//694911
package dic_server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Multithread implements Runnable{
	
	protected Socket clientSocket = null;
	protected int id = 0;
	protected File dictionary ;
	
	public Multithread(Socket clientSocket,int id, File dictionary) {
		this.clientSocket = clientSocket;
		this.id=id;
		this.dictionary = dictionary;
	}
	


	public void run() {
		//Date
		LocalDateTime myDate = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = myDate.format(myFormatObj); 
		
		System.out.println(formattedDate+": AcceptedClient:ID-"+id + ":Address -"
			+ clientSocket.getInetAddress().getHostAddress());
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
			BufferedReader in2 = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));
			//Read the message from the client and reply
			
			String clientMsg;
			String cmsg;
			String cmmd;
			String word = null;
			String[] cmmdarry;
			while((clientMsg = in.readLine()) != null) 
			{
				System.out.println("Message from client " + id + ": " + clientMsg);
				
				//Splitting the client message into operation and word
				cmsg= clientMsg;
				cmmdarry = cmsg.split(" ");
				cmmd= cmmdarry[0];
				word = cmmdarry[1];
				boolean temp;
				String def="";
				
				// For add operation
				if(cmmdarry.length>2) {
					for(int i=2;i<cmmdarry.length;i++) {
						def=def+" "+ cmmdarry[i];
					}
				}
				
				String dicdata= readFile(dictionary);
				HashMap<String,String> dicMap=parsetoHash(dicdata);
				switch(cmmd) {
				case "add":
					temp=dicMap.containsKey(word.toLowerCase());
					if(temp){
						out.write("Duplicate exists\n");
						out.flush();
					}else {
						add(word,def,out,dicMap);
						updatefile(dicMap,dictionary);
					}
					break;
				case "search":
					query(word.toLowerCase(),out,dicMap);
					break;
				case "delete":
					temp=dicMap.containsKey(word.toLowerCase());
					if(temp){
						delete(word.toLowerCase(),out,dicMap);
						updatefile(dicMap,dictionary);
					}else {
						out.write("Not found" + "\n");
						out.flush();
					}
					break;
				default:
					out.write("Command not valid" + "\n");
					out.flush();
				}
			}
		}
		catch(SocketException e)
		{
			System.out.println("Error in connecting with socket");
		}
		catch(Exception e)
		{
			System.out.println("Some error has come up");
			e.printStackTrace();
			
		}finally {
			if(clientSocket != null)
			{
				try
				{
					clientSocket.close();
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		
			
	}
	//Methods
	
	private void query(String word,BufferedWriter output,HashMap<String,String> dicmap) throws IOException {
		String temp = dicmap.get(word);
		if(temp!=null) {
			output.write(temp+"\n");
			output.flush();
			}else {
			output.write("The word doesn't exist\n");
			output.flush();
		}
	}
	
	private synchronized void add(String word, String def,BufferedWriter output, HashMap<String,String> dicmap) throws IOException {
		dicmap.put(word, def);
		output.write("Dic Updated\n");
		output.flush();
	}
	
	private synchronized void delete(String word,BufferedWriter output, HashMap<String,String> dicmap) throws IOException {
		dicmap.remove(word);
		output.write("Dic Updated\n");
		output.flush();
	}
	
	private void updatefile( HashMap<String,String> dicmap, File filename) throws FileNotFoundException,IOException{
		FileWriter outputStream = new FileWriter(filename);
		BufferedWriter buffWrit= new BufferedWriter(outputStream);
		String temp="";
		for(Map.Entry m:dicmap.entrySet()) {
			temp=temp+m.getKey()+";"+ m.getValue()+";";
		}
		buffWrit.write(temp);
		buffWrit.close();
		System.out.println("Actual file updated \n");
	}
	
	private String readFile(File filename) throws FileNotFoundException,IOException{
		FileReader fileReader = new FileReader(filename);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String dicdata= bufferedReader.readLine();
		bufferedReader.close();
		return dicdata;
	}
	
	private HashMap<String,String> parsetoHash(String dicdata){
		String[] dicArray=dicdata.split(";");
		int len=dicArray.length;
		HashMap<String, String> dicmap= new HashMap<String, String>();
		//First half of the if statement is redundant but is left since it does no harm
		if (len%2!=0) {
			System.out.println("not even");
		}else {
			for(int i=0; i<len; i=i+2) {
				dicmap.put(dicArray[i].toLowerCase(), dicArray[i+1].toLowerCase());
			}
		}
		return dicmap;
	}
}
