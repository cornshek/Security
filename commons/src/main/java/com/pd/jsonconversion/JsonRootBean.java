package com.pd.jsonconversion;

import java.util.List;

public class JsonRootBean {

    private Message message;
    private List<Cardsinfo> cardsinfo;

    public void setMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setCardsinfo(List<Cardsinfo> cardsinfo) {
        this.cardsinfo = cardsinfo;
    }

    public List<Cardsinfo> getCardsinfo() {
        return cardsinfo;
    }

    @Override
    public String toString() {
        return "JsonRootBean{" +
                "message=" + message +
                ", cardsinfo=" + cardsinfo +
                '}';
    }
}
