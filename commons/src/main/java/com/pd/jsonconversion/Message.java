package com.pd.jsonconversion;

public class Message {

    private Integer status;
    private String value;

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "status=" + status +
                ", value='" + value + '\'' +
                '}';
    }
}
