package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {

	@Bean
	public AWSCredentialsProvider credentialsProvider(
			@Value("${example.s3.credentials.accessKey}") String accessKey, 
			@Value("${example.s3.credentials.secretKey}") String secretKey) {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
	    return new AWSStaticCredentialsProvider(awsCreds);
	}
	
	
	@Bean
	public AmazonS3 s3Client(AWSCredentialsProvider credentialsProvider, 
			@Value("${example.s3.region}") String region,
			@Value("${example.s3.endpoint}") String endpoint) {
		
		AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard();
		builder.setEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region));
		builder.withCredentials(credentialsProvider);
		AmazonS3 s3Client = builder.build();
		return s3Client;
	}
	
}
