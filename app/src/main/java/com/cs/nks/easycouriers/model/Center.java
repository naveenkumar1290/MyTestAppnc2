
package com.cs.nks.easycouriers.model;


public class Center {

    private String centreName;
    private String centreAddress;
    private String headName;
    private String adminEmail;
    private String mobile;


    /**
     * 
     * @param centreAddress
     * @param centreName
     * @param adminEmail
     * @param mobile
     * @param headName
     */
    public Center(String centreName, String centreAddress, String headName, String adminEmail, String mobile) {
        super();
        this.centreName = centreName;
        this.centreAddress = centreAddress;
        this.headName = headName;
        this.adminEmail = adminEmail;
        this.mobile = mobile;
    }

    public String getCentreName() {
        return centreName;
    }

    public void setCentreName(String centreName) {
        this.centreName = centreName;
    }

    public String getCentreAddress() {
        return centreAddress;
    }

    public void setCentreAddress(String centreAddress) {
        this.centreAddress = centreAddress;
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
