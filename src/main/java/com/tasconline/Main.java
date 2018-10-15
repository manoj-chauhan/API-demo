package com.tasconline;

import com.google.gson.annotations.JsonAdapter;
import org.json.JSONArray;
import org.json.JSONObject;


public class Main {

    private static String individualEmail = "manojtest@test.com";

    public final static void main(String[] args) throws Exception {

        JSONObject individual = Individual.getIndividualInfo(individualEmail);
        String individualId = (String) individual.get("id");

        JSONObject employer = Employment.getEmployerInfo(individualId);
        String clientId = (String) employer.get("parentId");
        String hireDate = (String) employer.get("hireDate");

        JSONArray enrolledPlans = IndividualBenefitPlans.getIndividualEnrolledBenefitPlans(individualId, clientId);

        JSONArray plans = BenefitPlans.getClientBenefitPlans(clientId);
        String planId = (String) ((JSONObject) plans.get(0)).get("id");

        String payrollScheduleId = "";
//        for (int i = 0 ; i < plans.length(); i++) {
//            JSONObject jsonObject = (JSONObject) plans.get(i);
//            String planIdTemp = (String) jsonObject.get("id");
//            JSONArray funding = Funding.getFundingSources(clientId, planId);
//            payrollScheduleId = (String)((JSONObject)funding.get(0)).get("id");
//            System.out.println("Funding...."+ funding.get(0).toString());
//        }

        JSONObject enrollRequestType = EnrollBPRequestType.getEnrollBPRequestType(individualId, planId);

        /*PAYROLL SCHEDULE IDS FOR BP*/
        JSONArray payrollScheduleBP = PayrollScheduleBP.getPayrollSchedule(individualId, planId);

        payrollScheduleId = (String) ((JSONObject) payrollScheduleBP.get(0)).get("id");

        /*BP PAYROLL NUMBER*/
//        JSONArray payrollNumber = BPPayrollNumber.getBPPayrollNumber(individualId, planId, payrollScheduleId, "0", clientId);

//        String commandName = "StartToEnrolled";
        String activeToActiveCommandName = "ActiveToActive";
        String benefitAccountId = "26a59487-2111-4fe4-82b9-fcf14ffa22b8";//Utility.getUUId();
        String payloadEnrollBP = EnrollBP.getEnrollBPPayload( (JSONObject)plans.get(1),individualEmail, activeToActiveCommandName, benefitAccountId, individualId,clientId, hireDate, payrollScheduleId);
        JSONArray successEnrollingBP = EnrollBP.enrollIndividualBP(payloadEnrollBP, individualId, benefitAccountId, activeToActiveCommandName);

    }

}
