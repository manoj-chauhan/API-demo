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


public class EnrollBPRequestType {

        private static final String urlTemplate = Constants.ENV_BASE_URL_ACCOUNT_CONFIG + "/profile/%s/configuration/benefitPlan/%s/requestType/%s";

        public static final JSONObject getEnrollBPRequestType(String individualId, String planId) throws IOException {
            System.out.println("-------------------------------------------------------------------");
            CloseableHttpClient httpclient = HttpClients.createDefault();

            String urlString = String.format(urlTemplate, individualId, planId, planId);
            System.out.println("Url : " + urlString);

            JSONObject responseJson = null;

            try {
                HttpGet httpGet = new HttpGet(urlString);

                httpGet.setHeader("Content-Type", "application/json");
                httpGet.setHeader("Accept", "application/json");
                httpGet.setHeader("Authorization", Constants.ACCESS_TOKEN);

                System.out.println("Fetching ENROLL BP REQUEST TYPE: " + httpGet.getRequestLine());

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
                String responseBody = httpclient.execute(httpGet, responseHandler);
                responseJson = new JSONObject(responseBody);
                System.out.println( responseJson.toString(4));
            } finally {
                httpclient.close();
            }
            System.out.println("-------------------------------------------------------------------");
            return responseJson;
        }

}
