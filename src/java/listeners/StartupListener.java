package listeners;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author wifil
 */
public class StartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //Get current context
        ServletContext context = sce.getServletContext();
        try {
            
            loadMap(context);
            loadGeneralPermission(context);
            loadNotCheckSession(context);
            loadDefaultUserPermissions(context);
            loadDefaultAdminPermissions(context);
        } catch (FileNotFoundException e) {
            context.log("StartupListener FileNotFound: " + e.getMessage());
        } catch (IOException e) {
            context.log("StartupListener IOException: " + e.getMessage());
        }
    }

    private final String ROAD_MAP_PATH = "/WEB-INF/roadMap.properties";

    private void loadMap(ServletContext context) 
            throws FileNotFoundException, IOException{
        //1. Get full path to roadMap file
        String fullPath = context.getRealPath("/") + ROAD_MAP_PATH;

        Map<String, String> roadMap = null;
        FileReader reader = null;
        BufferedReader buffer = null;
        try {
            //2. Read file-content to buffer
            reader = new FileReader(fullPath);
            buffer = new BufferedReader(reader);
            
            //3. Add each record to roadmap
            while (buffer.ready()) {
                String record = buffer.readLine().trim();
                String[] tokens = record.split("=");
                String name = tokens[0];
                String value = tokens[1];
                
                if (roadMap == null) {
                    roadMap = new HashMap<>();
                }
                roadMap.put(name, value);
            }
            //4. Store in context scope
            context.setAttribute("ROAD_MAP", roadMap);
        } finally {
            //5. Close objects
            try {
                if (reader != null) reader.close();
                if (buffer != null) buffer.close();
            } catch (IOException e) {
                context.log("StartupListener IOException: " + e.getMessage());
            }
        }
    }    
    
    private final String GENERAL_PERMISSIONS_PATH = "/WEB-INF/generalPermissions.properties";
    
    private void loadGeneralPermission (ServletContext context) 
            throws IOException, FileNotFoundException{
        //1. Get full path to file
        String fullPath = context.getRealPath("/") + GENERAL_PERMISSIONS_PATH;
        
        List<String> generalPermissions = null;
        FileReader reader = null;
        BufferedReader buffer = null;
        
        try {
            //2. Read file-content to buffer
            reader = new FileReader(fullPath);
            buffer = new BufferedReader(reader);
            
            //3. Add records to list
            while(buffer.ready()) {
                String record = buffer.readLine().trim();
                
                if(generalPermissions == null) {
                    generalPermissions = new ArrayList<>();
                }
                generalPermissions.add(record);
            }
            
            //4. Store in context scope
            context.setAttribute("GENERAL_PERMISSIONS", generalPermissions);
        } finally {
            //5. Close objects
            if(reader != null) reader.close();
            if(buffer != null) buffer.close();
        }
    }
    
    private final String NOT_CHECK_SESSION_PATH = "/WEB-INF/notCheckSession.properties";
    
    private void loadNotCheckSession (ServletContext context) 
            throws IOException, FileNotFoundException{
        //1. Get full path to file
        String fullPath = context.getRealPath("/") + NOT_CHECK_SESSION_PATH;
        
        List<String> notCheckSession = null;
        FileReader reader = null;
        BufferedReader buffer = null;
        
        try {
            //2. Read file-content to buffer
            reader = new FileReader(fullPath);
            buffer = new BufferedReader(reader);
            
            //3. Add records to list
            while(buffer.ready()) {
                String record = buffer.readLine().trim();
                
                if(notCheckSession == null) {
                    notCheckSession = new ArrayList<>();
                }
                notCheckSession.add(record);
            }
            
            //4. Store in context scope
            context.setAttribute("NOT_CHECK_SESSION", notCheckSession);
        } finally {
            //5. Close objects
            if(reader != null) reader.close();
            if(buffer != null) buffer.close();
        }
    }
    
    private final String DEFAULT_USER_PERMISSIONS_PATH = "/WEB-INF/defaultUserPermissions.properties";
    
    private void loadDefaultUserPermissions (ServletContext context) 
            throws IOException, FileNotFoundException{
        //1. Get full path to file
        String fullPath = context.getRealPath("/") + DEFAULT_USER_PERMISSIONS_PATH;
        
        List<String> defaultUserPermissions = null;
        FileReader reader = null;
        BufferedReader buffer = null;
        try {
            //2. Read file-content to buffer
            reader = new FileReader(fullPath);
            buffer = new BufferedReader(reader);
            
            //3. Add records to list
            while(buffer.ready()) {
                String record = buffer.readLine().trim();
                
                if(defaultUserPermissions == null) {
                    defaultUserPermissions = new ArrayList<>();
                }
                defaultUserPermissions.add(record);
            }
            
            //4. Store in context scope
            context.setAttribute("DEFAULT_USER_PERMISSIONS", defaultUserPermissions);
        } finally {
            //5. Close objects
            if(reader != null) reader.close();
            if(buffer != null) buffer.close();
        }
    }
    
    private final String DEFAULT_ADMIN_PERMISSIONS_PATH = "/WEB-INF/defaultAdminPermissions.properties";
    
    private void loadDefaultAdminPermissions (ServletContext context) 
            throws IOException, FileNotFoundException{
        //1. Get full path to file
        String fullPath = context.getRealPath("/") + DEFAULT_ADMIN_PERMISSIONS_PATH;
        
        List<String> defaultAdminPermissions = null;
        FileReader reader = null;
        BufferedReader buffer = null;
        
        try {
            //2. Read file-content to buffer
            reader = new FileReader(fullPath);
            buffer = new BufferedReader(reader);
            
            //3. Add records to list
            while(buffer.ready()) {
                String record = buffer.readLine().trim();
                
                if(defaultAdminPermissions == null) {
                    defaultAdminPermissions = new ArrayList<>();
                }
                defaultAdminPermissions.add(record);
            }
            
            //4. Store in context scope
            context.setAttribute("DEFAULT_ADMIN_PERMISSIONS", defaultAdminPermissions);
        } finally {
            //5. Close objects
            if(reader != null) reader.close();
            if(buffer != null) buffer.close();
        }
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
