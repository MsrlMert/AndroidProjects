package com.mertmsrl.navdeneme;

import java.util.ArrayList;
import java.util.HashMap;

public class NavigationItemClass {

    private String name;
    private long iconId;

    public NavigationItemClass() {
    }

    public NavigationItemClass(String name, long iconId) {
        this.name = name;
        this.iconId = iconId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getIconId() {
        return iconId;
    }

    public void setIconId(long iconId) {
        this.iconId = iconId;
    }

}
