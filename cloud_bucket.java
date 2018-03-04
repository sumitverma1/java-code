package cloud.access;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.Channels;
import java.util.ArrayList;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.datastore.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
public class App 
{
    public static void main( String[] args )
    {
		Storage storage;
		try {
			storage = StorageOptions.newBuilder()
					.setProjectId("onyx-cumulus-196507")
					.setCredentials(GoogleCredentials.fromStream(new FileInputStream("C:/Users/admin/Desktop/My Project 8750-509218735fdd.json")))
					.build()
					.getService();
			
			
			
		com.google.cloud.storage.Blob blob = storage.get("https://console.cloud.google.com/storage/browser/onyx-cumulus-196507/satish999","/satish999/airport.csv");
        ReadChannel readChannel = blob.reader();
        FileOutputStream fileOuputStream = new FileOutputStream("outputFileName");
        fileOuputStream.getChannel().transferFrom(readChannel, 0, Long.MAX_VALUE);
        fileOuputStream.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} }
}
