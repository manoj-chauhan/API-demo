package com.tasconline;

import java.io.IOException;

import com.tasconline.filters.BenefitEffectiveDateFilter;
import com.tasconline.filters.EligibilityClassFilter;
import com.tasconline.filters.EnrolledPlanFilter;
import com.tasconline.filters.FilterByEnrollmentMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


public class Main {

    private static String individualEmail = "manojtest@mailinator.com"; //"manj.muthukumaresan@gmail.com";

    public final static void main(String[] args) throws Exception {

        JSONObject individual = Individual.getIndividualInfo(individualEmail);
        String individualId = (String) individual.get("id");

        JSONObject employer = Employment.getEmployerInfo(individualId);
        String clientId = (String) employer.get("parentId");
        String hireDate = (String) employer.get("hireDate");
        int eligibilityClassId = employer.optInt("eligibilityClassId", -1);

        JSONObject clientDetail = Client.getClientDetail(clientId);
        String effectiveDateType = ((JSONObject)clientDetail.optJSONArray("eligibilityClasses").get(0)).optString("eligibilityEffectiveDate");
        System.out.println("------effectiveDateType----" + effectiveDateType);

        int waitingPeriod = ((JSONObject)clientDetail.optJSONArray("eligibilityClasses").get(0)).optInt("waitingPeriod");
        System.out.println("------waitingPeriod----" + waitingPeriod);

        JSONArray enrolledPlans = IndividualBenefitPlans.getIndividualEnrolledBenefitPlans(individualId, clientId);
        System.out.println("-------enrolledPlans " + enrolledPlans.length());

        JSONArray plans = BenefitPlans.getClientBenefitPlans(clientId);
//
        JSONArray filteredPlans = new BenefitEffectiveDateFilter().filterByBenefitEffectiveDate(plans, hireDate, waitingPeriod, effectiveDateType);
        System.out.println("-------filterdPlans " + filteredPlans.length());

        JSONArray filteredPlansByMethods = new FilterByEnrollmentMethod().filterByEnrollmentMethod(plans);
        System.out.println("-------filteredPlansByMethods " + filteredPlansByMethods.length()); //already done

        JSONArray notEnrolledPlans = new EnrolledPlanFilter().filterOutAlreadyEnrolledPlans(enrolledPlans, plans);
        System.out.println("-------notEnrolledPlans " + notEnrolledPlans.length()); //already done


        for (int i = 0 ; i < notEnrolledPlans.length(); i++) {
            JSONObject jsonObject = (JSONObject) notEnrolledPlans.get(i);
            String planId = (String) jsonObject.get("id");
//            JSONArray fundingSources = Funding.getFundingSources(clientId, planId);
//            System.out.println("-------fundingSources " + fundingSources);
        }

        JSONArray elibileFilteredPlans = new EligibilityClassFilter().filterByEligibilityClassId(notEnrolledPlans, eligibilityClassId);
        System.out.println("-------elibileFilteredPlans " + elibileFilteredPlans.length());

    }


}
