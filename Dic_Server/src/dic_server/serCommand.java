//John Dan
//694911
package dic_server;

import org.kohsuke.args4j.Option;
import java.io.File;

public class serCommand {
    
    @Option(required = true, name = "-p", usage = "Port number")
    private int port;

    @Option(required = true, name = "-f", usage = "File name")
    private File filename;
    
    
    public int getPort() {
        return port;
    }
    
    public File getFile() {
    	return filename;
    }
}
