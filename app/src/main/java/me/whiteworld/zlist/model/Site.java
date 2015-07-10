package me.whiteworld.zlist.model;


import java.util.List;

/**
 * Created by whiteworld on 2015/3/26.
 */
public class Site {
    private String name;
    private List<Info> info;

    public Site() {
    }

    public Site(String name, List<Info> infoList) {
        this.name = name;
        this.info = infoList;
    }

    public List<Info> getInfo() {
        return info;
    }

    public void setInfo(List<Info> info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
