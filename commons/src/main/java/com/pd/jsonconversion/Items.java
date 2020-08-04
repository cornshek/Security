package com.pd.jsonconversion;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Items {

    private String nID;
    private String index;
    private String desc;
    private String content;

    public void setNID(String nID) {
        this.nID = nID;
    }

    public String getNID() {
        return nID;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Items{" +
                "nID='" + nID + '\'' +
                ", index='" + index + '\'' +
                ", desc='" + desc + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

}
