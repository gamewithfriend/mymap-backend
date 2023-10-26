package com.sillimfive.mymap.common;


import org.json.simple.JSONObject;

public class JSONBuilder {


    private JSONObject json;

    public JSONBuilder() {
        this.json = new JSONObject();
    }

    public static JSONBuilder create() {
        return new JSONBuilder();
    }

    public JSONBuilder success(boolean successful) {
        json.put("", successful);
        return this;
    }

    public JSONBuilder data(Object data) {
        json.put("data", data);
        return this;
    }

    public JSONBuilder put(String key, Object object) {
        json.put(key, object);
        return this;
    }

    public JSONObject build() {
        return json;
    }
}
