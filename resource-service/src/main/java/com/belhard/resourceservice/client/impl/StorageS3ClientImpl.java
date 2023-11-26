package com.belhard.resourceservice.client.impl;

import com.belhard.resourceservice.client.StorageS3Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class StorageS3ClientImpl implements StorageS3Client {

    @Value("${clients.s3.bucket_name}")
    private String bucketName;

    private final S3Client s3Client;

    @Override
    public void upload(byte[] data, String key) {
        findBucket(bucketName);
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3Client.putObject(request, RequestBody.fromBytes(data));
        log.info("Upload to S3: {}", key);
    }

    @Override
    public byte[] download(String key) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        try {
            byte[] data = s3Client.getObject(request).readAllBytes();
            log.info("Data was downloaded from S3");
            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<byte[]> downloadAll() {
        findBucket(bucketName);
        ListObjectsRequest request = ListObjectsRequest.builder()
                .bucket(bucketName)
                .build();
        List<S3Object> s3Objects = s3Client.listObjects(request).contents();
        log.info("Received list of S3Object");
        List<byte[]> resources = new ArrayList<>();
        for (S3Object s3Object : s3Objects) {
            resources.add(s3Object.toString().getBytes());
        }
        log.info("Created list of byte[] from list of S3Object");
        return resources;
    }

    @Override
    public void delete(String key) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3Client.deleteObject(request);
        log.info("Data from S3 was deleted successful");
    }

    private void findBucket(String bucketName) {
        HeadBucketRequest request = HeadBucketRequest.builder()
                .bucket(bucketName)
                .build();
        try {
            s3Client.headBucket(request);
            log.info("The bucketName exists");
        } catch (NoSuchBucketException e) {
            log.info("Bucket doesn't exist");
            createBucket(bucketName);
        }
    }

    private void createBucket(String bucketName) {
        CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
                .bucket(bucketName)
                .build();

        s3Client.createBucket(bucketRequest);
        S3Waiter s3Waiter = s3Client.waiter();
        HeadBucketRequest headBucketRequest = HeadBucketRequest.builder()
                .bucket(bucketName)
                .build();

        WaiterResponse<HeadBucketResponse> waiterResponse = s3Waiter.waitUntilBucketExists(headBucketRequest);
        waiterResponse.matched()
                .response()
                .map(Object::toString)
                .ifPresent(log::info);
    }
}
