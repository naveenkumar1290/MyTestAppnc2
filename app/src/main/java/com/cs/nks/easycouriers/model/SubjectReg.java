
package com.cs.nks.easycouriers.model;

public class SubjectReg {

    private String p_id;
    private String paper_name;



    public SubjectReg(String p_id, String paper_name) {
        super();
        this.p_id = p_id;
        this.paper_name = paper_name;
    }

    public String getp_id() {
        return p_id;
    }

    public void setp_id(String p_id) {
        this.p_id = p_id;
    }

    public String getpaper_name() {
        return paper_name;
    }

    public void setpaper_name(String paper_name) {
        this.paper_name = paper_name;
    }



}
