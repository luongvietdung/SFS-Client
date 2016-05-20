package com.appsfs.sfs.Objects;

/**
 * Created by longdv on 5/20/16.
 */
public class Orders {
    private String codeOrder;
    private String phoneCustomer;
    private String phoneShipper;
    private String codeCheckOrder;
    private String date;
    private int status;

    public Orders() {
        super();
    }

    public String getCodeOrder() {
        return codeOrder;
    }

    public void setCodeOrder(String codeOrder) {
        this.codeOrder = codeOrder;
    }

    public String getPhoneCustomer() {
        return phoneCustomer;
    }

    public void setPhoneCustomer(String phoneCustomer) {
        this.phoneCustomer = phoneCustomer;
    }

    public String getPhoneShipper() {
        return phoneShipper;
    }

    public void setPhoneShipper(String phoneShipper) {
        this.phoneShipper = phoneShipper;
    }

    public String getCodeCheckOrder() {
        return codeCheckOrder;
    }

    public void setCodeCheckOrder(String codeCheckOrder) {
        this.codeCheckOrder = codeCheckOrder;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
