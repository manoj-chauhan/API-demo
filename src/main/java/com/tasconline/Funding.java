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

import java.io.IOException;


public class Funding {

    private static final String urlTemplate = Constants.ENV_BASE_URL_ACCOUNT_CONFIG + "/profile/%s/configuration/benefitPlan/%s/fundingSourceAndSchedule/search";
    private static final String payloadTemplate = "[\n" +
            "    {\"key\":\"parentId\",\"value\":\"%s\",\"matchType\":\"EXACT\",\"chainType\":\"AND\"},\n" +
            "    {\"key\":\"parentType\",\"value\":\"BENEFIT_PLAN\",\"matchType\":\"EXACT\",\"chainType\":\"AND\"}\n" +
            "]";


    public static final JSONArray getFundingSources(String clientId, String planId) throws IOException {
        System.out.println("-------------------------------------------------------------------");
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String urlString = String.format(urlTemplate, clientId, planId);
        System.out.println("Url : " + urlString);

        String payload = String.format(payloadTemplate, planId);
        System.out.println("Payload : " + payload);

        JSONArray responseJson = null;

        try {
            HttpPost httpPost = new HttpPost(urlString);

            StringEntity entity = new StringEntity(payload);
            httpPost.setEntity(entity);


            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Authorization", Constants.ACCESS_TOKEN);

            System.out.println("Fetching Funding Source Detail: " + httpPost.getRequestLine());

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
            responseJson = new JSONArray(responseBody);
        } finally {
            httpclient.close();
        }
        System.out.println("-------------------------------------------------------------------");
        return responseJson;
    }

}
