
package com.cs.nks.easycouriers.model;

public class Subject {

    private String p_id;
    private String paper_name;
    private String paper_code;
    private String material;

    public Subject(String p_id, String paper_name,String paper_code,String material) {
        super();
        this.p_id = p_id;
        this.paper_name = paper_name;
        this.paper_code = paper_code;
        this.material = material;
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

    public String getpaper_code() {
        return paper_code;
    }
    public String getmaterial() {
        return material;
    }
}
