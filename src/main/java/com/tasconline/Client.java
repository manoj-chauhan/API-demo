package com.tasconline;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;


public class Client {

    private static String url = Constants.ENV_BASE_URL_PROFILE + "/profile/profileType/individual/search";



    public static final JSONObject get(String email) throws IOException {
        System.out.println("-------------------------------------------------------------------");
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String payload = "[{\"key\":\"primaryEmail\",\"matchType\":\"EXACT\",\"value\":\"" + email + "\"}]";

        JSONObject responseJson = null;

        try {
            HttpPost httpPost = new HttpPost(url);

            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Authorization", Constants.ACCESS_TOKEN);

            System.out.println("Payload : " + payload);
            StringEntity entity = new StringEntity(payload);
            httpPost.setEntity(entity);

            System.out.println("Fetching Individual Detail: " + httpPost.getRequestLine());

            // Create a custom response handler
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
            responseJson = (JSONObject) new JSONArray(responseBody).get(0);
        } finally {
            httpclient.close();
        }
        System.out.println("-------------------------------------------------------------------");
        return responseJson;
    }

}
