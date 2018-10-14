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

import java.io.IOException;


public class IndividualBenefitPlans {

    private static final String urlTemplate = Constants.ENV_BASE_URL_ACCOUNT + "/profile/%s/client/%s/benefitAccounts/balances?orderBy=planName&orderDirection=ASC";

    public static final JSONArray getIndividualEnrolledBenefitPlans(String individualId, String clientId) throws IOException {
        System.out.println("-------------------------------------------------------------------");
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String urlString = String.format(urlTemplate, individualId, clientId);
        System.out.println("Url : " + urlString);

        JSONArray responseJson = null;

        try {
            HttpGet httpPost = new HttpGet(urlString);

            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Authorization", Constants.ACCESS_TOKEN);

            System.out.println("Fetching Employer Detail: " + httpPost.getRequestLine());

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
