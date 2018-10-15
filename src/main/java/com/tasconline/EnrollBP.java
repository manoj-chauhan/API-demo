package com.tasconline;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import java.io.IOException;

public class EnrollBP {

    private static final String urlTemplate = Constants.ENV_BASE_URL_ACCOUNT + "/profile/%s/benefitAccount/%s/command/%s";


    public static final JSONArray enrollIndividualBP(String payloadTemplate, String profileId, String benefitAccountId, String commandName) throws IOException {
        System.out.println("-------------------------------------------------------------------");
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String urlString = String.format(urlTemplate, profileId, benefitAccountId, commandName);
        System.out.println("Url : " + urlString);

        System.out.println("Payload : " + payloadTemplate);

        JSONArray responseJson = null;

        try {
            HttpPut httpPut = new HttpPut(urlString);

            httpPut.setHeader("Content-Type", "application/json");
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Authorization", Constants.ACCESS_TOKEN);


            StringEntity entity = new StringEntity(payloadTemplate);
            httpPut.setEntity(entity);

            System.out.println("Fetching Employer Detail: " + httpPut.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        System.out.println("EnrollBP Statue" + status);
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + response);
                    }
                }

            };
            String responseBody = httpclient.execute(httpPut, responseHandler);
            System.out.println(responseBody);
//            responseJson = new JSONArray(responseBody);
        } finally {
            httpclient.close();
        }
        System.out.println("-------------------------------------------------------------------");
        return responseJson;
    }
}
