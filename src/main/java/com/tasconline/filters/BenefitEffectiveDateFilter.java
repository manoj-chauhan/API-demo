package com.tasconline.filters;

import com.tasconline.DateUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static com.tasconline.filters.BenefitEffectiveDateFilter.EligibilityEffectiveDateType.*;

/**
 * Created by kiwitech on 15/10/18.
 */
public class BenefitEffectiveDateFilter {

    String dateFormat = "yyyy-MM-dd";//2018-12-25

    public JSONArray filterByBenefitEffectiveDate(JSONArray plans, String hireDateStr, int waitingPeriod, String effDateType) throws ParseException{
        EligibilityEffectiveDateType effectiveDateType = getEffectiveDateType(effDateType);
        Date hireDate = DateUtil.convertStringToDate(hireDateStr, dateFormat);

        JSONArray arr = new JSONArray();
        int l = plans.length();

        for(int i = 0; i < l; i++) {
            JSONObject plan = (JSONObject) plans.get(i);
            Date planStartDate = DateUtil.convertStringToDate(plan.optString("planStartDate"), dateFormat);
            Date planEndDate = DateUtil.convertStringToDate(plan.optString("planEndDate"), dateFormat);
//            Date benefitEffectiveDate = DateUtil.convertStringToDate(plan.optString("benefitEffectiveDate"), dateFormat);

            Date benefitEffectiveDate = calculateBenefitEffectiveDate(calculateEligibilityEffectiveDate(hireDate, waitingPeriod, effectiveDateType), planStartDate);

            if(benefitEffectiveDate.equals(planStartDate) || benefitEffectiveDate.equals(planEndDate) || (benefitEffectiveDate.after(planStartDate) && benefitEffectiveDate.before(planEndDate))) {
                arr.put(plan);
            }
        }

        return arr;
    }

    private EligibilityEffectiveDateType getEffectiveDateType(String effDateType) {
        switch(effDateType) {
            case "SameDay":
                return SameDay;
            case "FirstDay":
                return FirstDay;
            case "FirstOfMonth":
                return FirstOfMonth;
            default:
                return HireDay;
        }
    }

    private Date calculateBenefitEffectiveDate(Date eligibilityEffectiveDate, Date planStartDate) {
        if(eligibilityEffectiveDate.after(new Date())) {
            //return max date
            if(eligibilityEffectiveDate.compareTo(planStartDate) >0)
                return eligibilityEffectiveDate;
            else
                return planStartDate;
        } else {
            return planStartDate;
        }
    }

    private Date calculateEligibilityEffectiveDate(Date hireDate, int waitingPeriod, EligibilityEffectiveDateType eligibilityEffectiveType) throws ParseException{

        Calendar endWaitCal = Calendar.getInstance();
        endWaitCal.setTime(hireDate);
        endWaitCal.add(Calendar.DATE, waitingPeriod);
        Date endWaitDay = endWaitCal.getTime();

        Calendar c = Calendar.getInstance();
        c.setTime(endWaitDay);

        switch(eligibilityEffectiveType) {
            case SameDay:
                return endWaitDay;
            case FirstDay:
                c.add(Calendar.DATE, 1);
                return c.getTime();
            case FirstOfMonth:
                c.add(Calendar.MONTH, 1);
                return c.getTime();
            default:
                return hireDate;
        }

    }



    enum EligibilityEffectiveDateType {
        SameDay,
        FirstDay,
        FirstOfMonth,
        HireDay
    }

}
