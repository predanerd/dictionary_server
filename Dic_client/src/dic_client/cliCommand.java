//John Dan
//694911
package dic_client;

import org.kohsuke.args4j.Option;

public class cliCommand {
	@Option(required = true, name = "-s", usage="Server hostname")
	private String host;
	
	@Option(required = true, name = "-p", usage="Port number")
	private int port;
	
	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
		
	}
}
