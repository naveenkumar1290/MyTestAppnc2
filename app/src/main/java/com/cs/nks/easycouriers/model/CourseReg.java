
package com.cs.nks.easycouriers.model;

import java.util.HashMap;
import java.util.Map;

public class CourseReg {

    private String cId;
    private String course;



    public CourseReg(String cId, String course) {
        super();
        this.cId = cId;
        this.course = course;
    }

    public String getCId() {
        return cId;
    }

    public void setCId(String cId) {
        this.cId = cId;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String toString() {
        return course;
    }

}
