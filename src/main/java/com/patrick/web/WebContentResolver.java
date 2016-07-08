package com.patrick.web;

import com.patrick.exception.RequestFailedException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.*;

public class WebContentResolver implements ContentResolver {

    private final HttpClient httpClient;

    public WebContentResolver(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public String getUrlAsString(String link) throws RequestFailedException {
        try {
            HttpResponse response = httpClient.execute(new HttpGet(link));
            if (response.getStatusLine().getStatusCode() == 200) {
                return writeStreamToString(response.getEntity().getContent());
            }
            throw new RequestFailedException("Response was not a 200");
        } catch(IOException e) {
            throw new RequestFailedException(e);
        }
    }

    private String writeStreamToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;

        while ((length = inputStream.read(buffer)) != -1) {
            out.write(buffer, 0, length);
        }
        return out.toString("UTF-8");
    }
}
