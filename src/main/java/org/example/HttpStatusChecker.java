package org.example;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class HttpStatusChecker {
    public String getStatusImage(int code) throws Exception  {
        URI uri = new URI("https", "http.cat", "/" + code + ".jpg", null);
        URL url = uri.toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode();
        if (responseCode == 404) {
            throw new Exception("HTTP cat image " + code + " is not found");
        }

        return url.toString();
    }
}
