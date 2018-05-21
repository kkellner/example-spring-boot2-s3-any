# Example read/write to S3

Example of how to read / write to any S3 compatible storage system.


# How to run

This example can be run against any S3 compatible storage system.  AWS S3 works fine but if you do not have
convenient access to an S3 storage system for local devleopment / testing, you can use Minio.


# Minio
[minio](https://github.com/minio/minio) running on MacOS was used to test this code.

You can run the following to startup minio on your local system and start sharing `~/minio_share` directory. 
```
mkdir ~/minio_share
minio server ~/minio_share
```
Note the output of the above command as it will be needed to update the `application.yml` file.


# Testing app

Before starting the application, update the `application.yml` file with appropriate values.  The only values that need to be updated to match your environment are the accessKey and secretKey. 

If you need an S3 storage system, you can use minio on your local system.  When you start minio it will provide accessKey, secretKey as well as the URL to access the S3 storage.  Update `application.yml` with these values.

Start the app.

## Endpoints

To create a bucket for testing, 
http://localhost:8080/exampleCreateBucket

Write a test text file to the bucket
http://localhost:8080/exampleWrite

Read the ext file from the bucket
http://localhost:8080/exampleRead

