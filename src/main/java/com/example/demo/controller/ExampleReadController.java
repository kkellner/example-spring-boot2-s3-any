package com.example.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;

/**
 * Example application to demonstrate reading from S3 bucket The following
 * endpoints are available:
 * 
 * <pre>
 * /exampleRead - Read from a file located in a S3 bucket (bucketname and filename defined in applicaiton.yml)
 * </pre>
 * 
 * @author kkellner
 */
@RestController
public class ExampleReadController {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ExampleReadController.class);

	@Autowired
	AmazonS3 s3client;

	@Value("${example.s3.bucketname}")
	String bucketName;

	@Value("${example.s3.examplefilename}")
	String examplefilename;

	@RequestMapping("/exampleRead")
	public void exampleRead(HttpServletResponse response) throws IOException {

		long startTime = System.currentTimeMillis();

		readFromS3(response);
		long endTime = System.currentTimeMillis();

		String msg = "Text read from S3 file.  Duration (milliseconds):" + (endTime - startTime);
		log.info(msg);

	}

	private void readFromS3(HttpServletResponse response) throws IOException {

		S3Object s3Object = s3client.getObject(bucketName, examplefilename);
		try (InputStream inputStream = s3Object.getObjectContent()) {
			OutputStream outputStream = response.getOutputStream();
			IOUtils.copy(inputStream, outputStream);
		}
		s3Object.close();
	}
}
