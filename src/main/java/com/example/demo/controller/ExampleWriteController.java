package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

/**
 * Example application to demonstrate writing to S3 buckets The following
 * endpoints are available:
 * 
 * <pre>
 *   /exampleCreateBucket - Create an example bucket (bucketname defined in applicaiton.yml) 
 *   /exampleWrite - Write a file to bucket (bucketname and filename defined in applicaiton.yml)
 * </pre>
 * 
 * @author kkellner
 *
 */
@RestController
public class ExampleWriteController {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ExampleWriteController.class);

	@Autowired
	AmazonS3 s3Client;

	@Value("${example.s3.bucketname}")
	String bucketName;

	@Value("${example.s3.examplefilename}")
	String examplefilename;

	@RequestMapping("/exampleCreateBucket")
	public String exampleCreateBucket() {

		long startTime = System.currentTimeMillis();
		createBucket(bucketName);
		long endTime = System.currentTimeMillis();

		String msg = "S3 Bucket Created.  Duration (milliseconds):" + (endTime - startTime);
		log.info(msg);
		return msg;
	}

	@RequestMapping("/exampleWrite")
	public String exampleWrite() {

		long startTime = System.currentTimeMillis();

		Calendar cal = Calendar.getInstance();
		Date currentTime = cal.getTime();

		writeToS3("Text from example S3 program.  Time:" + currentTime);
		long endTime = System.currentTimeMillis();

		String msg = "Text written to S3 file.  Duration (milliseconds):" + (endTime - startTime);
		log.info(msg);
		return msg;
	}

	private void createBucket(String createBucketName) {
		s3Client.createBucket(createBucketName);
	}

	private void writeToS3(String textToWrite) {

		String filename = this.examplefilename;

		// create meta-data for your folder and set content-length to 0
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(textToWrite.length());

		// create stream over string content
		InputStream stream = new ByteArrayInputStream(textToWrite.getBytes(StandardCharsets.UTF_8));

		// create a PutObjectRequest passing the filename and stream
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filename, stream, metadata);
		putObjectRequest = putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);

		// send request to S3 to create folder
		s3Client.putObject(putObjectRequest);

	}

}
