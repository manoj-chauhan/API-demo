package com.tasconline;

import com.google.gson.JsonArray;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;


public class BPPayrollNumber {

        private static final String urlTemplate = Constants.ENV_BASE_URL_ACCOUNT + "/profile/%s/benefitAccount/%2A/electionSchedule";
    private static final String payloadTemplate = "{\"planId\":\"%s\"," +
            "\"payrollScheduleId\":\"%s\",\"electionAmount\":%s,\"clientId\":\"%s\"}";

        public static final JSONArray getBPPayrollNumber(String individualId, String planId, String payrollScheduleId, String electionAmount, String clientId) throws IOException {
            System.out.println("-------------------------------------------------------------------");
            CloseableHttpClient httpclient = HttpClients.createDefault();

            String urlString = String.format(urlTemplate, individualId);
            System.out.println("Url : " + urlString);

            String payload = String.format(payloadTemplate, planId, payrollScheduleId, electionAmount, clientId);
            System.out.println("Payload : " + payload);

            JSONArray responseJson = null;

            try {
                HttpPost httpPost = new HttpPost(urlString);

                httpPost.setHeader("Content-Type", "application/json");
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Authorization", Constants.ACCESS_TOKEN);

                System.out.println("Fetching BP PAYROLL NUMBER: " + httpPost.getRequestLine());

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
                System.out.println( responseJson.toString(4));
            } finally {
                httpclient.close();
            }
            System.out.println("-------------------------------------------------------------------");
            return responseJson;
        }

}
