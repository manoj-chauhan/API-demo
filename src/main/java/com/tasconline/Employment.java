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


public class Employment {

    private static String urlTemplate = Constants.ENV_BASE_URL_CONFIG + "/profile/%s/configuration/employmentInfo/search";



    public static final JSONObject getEmployerInfo(String individualId) throws IOException {
        System.out.println("########################## Individual Employment Info ##############################");
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String urlString = String.format(urlTemplate, individualId);
        System.out.println("Url :: " + urlString);

        String payload = "[\n" +
                "\t{\"key\":\"individualId\",\"value\":\"" + individualId + "\",\"matchType\":\"EXACT\"},\n" +
                "\t{\"key\":\"currentState\",\"value\":\"Active\",\"matchType\":\"EXACT\"}\n" +
                "]";
        System.out.println("Payload :: " + payload);

        JSONObject responseJson = null;

        try {
            HttpPost httpPost = new HttpPost(urlString);

            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Authorization", Constants.ACCESS_TOKEN);


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
            System.out.println("Employment ::");
            Utility.logJson(responseJson);
        } finally {
            httpclient.close();
        }

        return responseJson;
    }

}
