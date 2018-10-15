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


public class Individual {

    private static String url = Constants.ENV_BASE_URL_PROFILE + "/profile/profileType/individual/search";



    public static final JSONObject getIndividualInfo(String email) throws IOException {
        System.out.println("########################## Individual Info ##############################");
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String payload = "[{\"key\":\"primaryEmail\",\"matchType\":\"EXACT\",\"value\":\"" + email + "\"}]";

        JSONObject responseJson = null;

        try {
            HttpPost httpPost = new HttpPost(url);
            System.out.println("Url :: " + url);

            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Authorization", Constants.ACCESS_TOKEN);

            System.out.println("Payload :: " + payload);
            StringEntity entity = new StringEntity(payload);
            httpPost.setEntity(entity);

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

            responseJson = (JSONObject) new JSONArray(responseBody).get(0);
            System.out.println("Individual ::");
            Utility.logJson(responseJson);
        } finally {
            httpclient.close();
        }
        return responseJson;
    }

}
