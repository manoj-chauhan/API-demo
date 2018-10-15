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


public class BenefitPlans {

    private static final String urlTemplate = Constants.ENV_BASE_URL_ACCOUNT_CONFIG + "/profile/%s/configuration/benefitPlan/search?take=100&skip=0&orderBy=name&orderDirection=ASC";
    private static final String payloadTemplate = "[\n" +
            "\t{\"key\":\"parentId\",\"value\":\"%s\",\"matchType\":\"EXACT\",\"chainType\":\"AND\"},\n" +
            "\t{\"key\":\"parentType\",\"value\":\"CLIENT\",\"matchType\":\"EXACT\",\"chainType\":\"AND\"},\n" +
            "\t{\"key\":\"currentState\", \"value\": \"Setup|Active|AuditAdjustment|Initiating|Initiated|TakeoverHold|GracePeriod|RunOut|Reconciliation\", \"matchType\": \"IN\" }\n" +
            "]";

    public static final JSONArray getClientBenefitPlans(String clientId) throws IOException {
        System.out.println("########################## Client's Benefit Plans ##############################");
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String urlString = String.format(urlTemplate, clientId);
        System.out.println("Url :: " + urlString);

        String payload = String.format(payloadTemplate, clientId);
        System.out.println("Payload :: " + payload);

        JSONArray responseJson = null;

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
            responseJson = new JSONArray(responseBody);

            System.out.println("Plans ::");
            Utility.logJson(responseJson);

        } finally {
            httpclient.close();
        }
        return responseJson;
    }

}
