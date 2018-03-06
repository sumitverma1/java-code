package cloud.access;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import com.google.api.client.http.FileContent;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.Bigquery.Jobs.Insert;
import com.google.api.services.bigquery.model.Job;
import com.google.api.services.bigquery.model.JobConfiguration;
import com.google.api.services.bigquery.model.JobConfigurationLoad;
import com.google.api.services.bigquery.model.JobReference;
import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableReference;
import com.google.api.services.bigquery.model.TableSchema;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.Dataset;
import com.google.cloud.bigquery.DatasetInfo;
import com.google.cloud.bigquery.FormatOptions;
import com.google.cloud.bigquery.JobStatistics.LoadStatistics;
import com.google.cloud.bigquery.TableDataWriteChannel;
import com.google.cloud.bigquery.TableId;
import com.google.cloud.bigquery.WriteChannelConfiguration;

public class bq {

	BigQuery bigquery = BigQueryOptions
			.getDefaultInstance()
			.toBuilder()
			.setProjectId("")
			
			.build()
			.getService();
	
	
	
	String datasetName = "";
	 String tableName = "";
	 Path csvPath = FileSystems.getDefault().getPath(".", "C:/Users/admin/Desktop/output.txt");
	
	public long writeFileToTable(String datasetName, String tableName, Path csvPath)
		      throws IOException, InterruptedException, TimeoutException {
		    // [START writeFileToTable]
		
		    TableId tableId = TableId.of(datasetName, tableName);
		    WriteChannelConfiguration writeChannelConfiguration =
		        WriteChannelConfiguration.newBuilder(tableId)
		            .setFormatOptions(FormatOptions.csv())
		            .build();
		    TableDataWriteChannel writer = bigquery.writer(writeChannelConfiguration);
		    // Write data to writer
		    try (OutputStream stream = Channels.newOutputStream(writer)) {
		      Files.copy(csvPath, stream);
		    }
		    // Get load job
		    com.google.cloud.bigquery.Job job = writer.getJob();
		    job = job.waitFor();
		    LoadStatistics stats = job.getStatistics();
		    return stats.getOutputRows();
		    // [END writeFileToTable]
		  }}
