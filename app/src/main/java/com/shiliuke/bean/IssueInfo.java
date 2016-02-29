package com.shiliuke.bean;

import java.io.Serializable;

/**
 * Created by wupeitao on 15/11/23.
 */
public class IssueInfo implements Serializable {
    private String subject_activity_content;
    private String activity_address_content;
    private String activity_time_content;
    private String assemble_time;
    private String assemble_address_content;
    private String means_of_transportation_content;
    private String contact_man_content;
    private String contact_phone_content;
    private String max_person_content;
    private String cost_issue_content;
    private String method_of_payment_content;


    public String getAssemble_time() {
        return assemble_time;
    }

    public void setAssemble_time(String assemble_time) {
        this.assemble_time = assemble_time;
    }

    public String getSubject_activity_content() {
        return subject_activity_content;
    }

    public void setSubject_activity_content(String subject_activity_content) {
        this.subject_activity_content = subject_activity_content;
    }

    public String getActivity_address_content() {
        return activity_address_content;
    }

    public void setActivity_address_content(String activity_address_content) {
        this.activity_address_content = activity_address_content;
    }

    public String getActivity_time_content() {
        return activity_time_content;
    }

    public void setActivity_time_content(String activity_time_content) {
        this.activity_time_content = activity_time_content;
    }

    public String getAssemble_address_content() {
        return assemble_address_content;
    }

    public void setAssemble_address_content(String assemble_address_content) {
        this.assemble_address_content = assemble_address_content;
    }

    public String getMeans_of_transportation_content() {
        return means_of_transportation_content;
    }

    public void setMeans_of_transportation_content(String means_of_transportation_content) {
        this.means_of_transportation_content = means_of_transportation_content;
    }

    public String getContact_man_content() {
        return contact_man_content;
    }

    public void setContact_man_content(String contact_man_content) {
        this.contact_man_content = contact_man_content;
    }

    public String getContact_phone_content() {
        return contact_phone_content;
    }

    public void setContact_phone_content(String contact_phone_content) {
        this.contact_phone_content = contact_phone_content;
    }

    public String getMax_person_content() {
        return max_person_content;
    }

    public void setMax_person_content(String max_person_content) {
        this.max_person_content = max_person_content;
    }

    public String getCost_issue_content() {
        return cost_issue_content;
    }

    public void setCost_issue_content(String cost_issue_content) {
        this.cost_issue_content = cost_issue_content;
    }

    public String getMethod_of_payment_content() {
        return method_of_payment_content;
    }

    public void setMethod_of_payment_content(String method_of_payment_content) {
        this.method_of_payment_content = method_of_payment_content;
    }

}
