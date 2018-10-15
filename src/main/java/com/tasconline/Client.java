package com.tasconline;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;


public class Client {

    private static String urlTemplate = Constants.ENV_BASE_URL_PROFILE + "/profile/%s/profileType/client";



    public static final JSONObject getClientDetail(String clientId) throws IOException {
        System.out.println("-----------------------------------------Client detail on the basis of clientId----------------------------------------------------");
        CloseableHttpClient httpclient = HttpClients.createDefault();

        JSONObject responseJson = null;

        String url = String.format(urlTemplate, clientId);

        try {
            HttpGet httpPost = new HttpGet(url);

            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Authorization", Constants.ACCESS_TOKEN);

            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            String responseBody = httpclient.execute(httpPost, responseHandler);
            System.out.println(responseBody);
            responseJson = new JSONObject(responseBody);
        } finally {
            httpclient.close();
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        return responseJson;
    }

}
