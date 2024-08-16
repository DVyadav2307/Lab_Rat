package gdrive;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;

public class App {
    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);


    public static GoogleCredentials getCredentials() throws IOException, GeneralSecurityException{


        
        GoogleCredentials credentials = ServiceAccountCredentials.fromStream(
            new FileInputStream(App.class.getResource("credentials.json").getPath().replaceAll("%20", " ")))
            .createScoped(SCOPES);
            return credentials;
        }
        
        
        
        
        public static void main(String... args) {
            try {
                

            NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            Drive driveService = new Drive.Builder(httpTransport, JSON_FACTORY, new HttpCredentialsAdapter(getCredentials()))
                                .setApplicationName(APPLICATION_NAME)
                                .build()
            ;





            // // upload files todrive
            // File fileMetaData = new File();
            // fileMetaData.setName("it_creds");
            // fileMetaData.setParents(Collections.singletonList("1rorsy8vsoGC36wpHYBNvQWjRc1sMJRKk"));

            // java.io.File filePath = new java.io.File(App.class.getResource("credentials.json")
            //                                                 .getPath()
            //                                                 .replaceAll("%20", " "))
            // ;

            // FileContent content = new FileContent("text/json", filePath);
            
            // File upFile = driveService.files().create(fileMetaData, content)
            //             .setFields("id")
            //             .execute();
            
            // System.out.println(upFile.getId());
            



            // // didn't work for service accounts
            // Permission filePermission = new Permission()
            // .setType("user")
            // .setRole("owner")
            // .setEmailAddress("divyanshuy858@gmail.com");

            // driveService.permissions().create(upFile.getId(), filePermission)
            // .setTransferOwnership(true)
            // .setSendNotificationEmail(true)
            // .execute();

                
            // // delete files from id
            // driveService.files().delete("19REGd9PyUn-YmnfGSoQwPJZ37vayY9tQ").execute();
        
            
            // //download in browser via link
            // File downFile = driveService.files().get("1xoPzmu9mGB2sKDgNG8Y1M0BLnBraxXAd")
            //             .setFields("id, name, webContentLink")
            //             .execute();
            // System.out.println("downFile.getWebContentLink() = " + downFile.getWebContentLink());





            // List files
            FileList result = driveService.files().list()
                    .setPageSize(10)
                    .setFields("nextPageToken, files(id, name)")
                    .execute();
            List<File> files = result.getFiles();
            if (files == null || files.isEmpty()) {
                System.out.println("No files found.");
            } else {
                System.out.println("Files:");
                for (File file : files) {
                    System.out.printf("%s (%s)\n", file.getName(), file.getId());
                }
            }



            
        } catch (IOException | GeneralSecurityException  e) {
            e.printStackTrace();
        }
    }
}
