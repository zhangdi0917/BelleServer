package com.jesson.sexybelle.model;

/**
 * Created by zhangdi on 14-3-6.
 */
public class Belle {

    private long id;

    private long time;

    private int type;

    private String url;

    @Override
    public String toString() {
        return "Belle{" +
                "id=" + id +
                ", time=" + time +
                ", type=" + type +
                ", url='" + url + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
