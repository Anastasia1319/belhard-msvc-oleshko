package com.belhard.resourceservice.client;

import java.util.List;

public interface StorageS3Client {

    void upload(byte[] data, String key);

    byte[] download(String key);

    List<byte[]> downloadAll();

    void delete(String key);
}
