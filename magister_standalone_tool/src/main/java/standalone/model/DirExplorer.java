package standalone.model;

import java.io.File;

public class DirExplorer {
	
    public interface FileHandler {
        void handle(int level, String path, File file);
    }

    public interface Filter {
        boolean interested(int level, String path, File file);
    }

    private FileHandler fileHandler;
    private Filter filter;

    public DirExplorer(Filter filter, FileHandler fileHandler) {
        this.filter = filter;
        this.fileHandler = fileHandler;
    }

    public void explore(File root) {
        explore(0, "", root);
    }

    private void explore(int level, String path, File file) {
    
    	if (file.isDirectory()) {
            
    		for (File child : file.listFiles()) {
    			
    			//decomment
    			try {
    				explore(level + 1, path + "/" + child.getName(), child);
    			} catch (Exception e) {}
    			
            }
        
    	} else {
        
    		if (filter.interested(level, path, file)) {
                fileHandler.handle(level, path, file);
            }
        }
    }

}