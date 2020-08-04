package com.pd.jsonconversion;

import java.util.List;

public class Cardsinfo {

    private String type;
    private List<Items> items;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public List<Items> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Cardsinfo{" +
                "type='" + type + '\'' +
                ", items=" + items +
                '}';
    }
}
