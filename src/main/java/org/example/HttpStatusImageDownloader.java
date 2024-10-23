package org.example;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HttpStatusImageDownloader {
    private final HttpStatusChecker statusChecker = new HttpStatusChecker();

    public void downloadStatusImage(int code) throws Exception {
        String imgUrl = statusChecker.getStatusImage(code);
        URL url = new URL(imgUrl);

        try (InputStream inputStream = getInputStreamFromUrl(url)) {
            Path imagePath = Paths.get(code + ".jpg");
            Files.copy(inputStream, imagePath);  // Завантажуємо файл

            System.out.println("Image saved at: " + imagePath.toAbsolutePath());
        } catch (Exception e) {
            throw new Exception(imgUrl + " not found or failed to download", e);
        }
    }

    private InputStream getInputStreamFromUrl(URL url) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int status = connection.getResponseCode();
        if (status == 404) {
            throw new Exception("Image not found for URL: " + url);
        }

        return connection.getInputStream();
    }
}
