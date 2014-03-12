package com.jesson.sexybelle.model;

/**
 * Created by zhangdi on 14-3-11.
 */
public class Series {

    private int type;

    private String title;

    public Series() {

    }

    public Series(int type, String title) {
        this.type = type;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Series{" +
                "type=" + type +
                ", title='" + title + '\'' +
                '}';
    }

}
