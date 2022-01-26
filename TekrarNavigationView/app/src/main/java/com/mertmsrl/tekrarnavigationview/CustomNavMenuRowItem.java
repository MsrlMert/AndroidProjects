package com.mertmsrl.tekrarnavigationview;

public class CustomNavMenuRowItem {

    private String iconName;
    private long iconId;

    public CustomNavMenuRowItem() {
    }

    public CustomNavMenuRowItem(String iconName, long iconId) {
        this.iconName = iconName;
        this.iconId = iconId;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public long getIconId() {
        return iconId;
    }

    public void setIconId(long iconId) {
        this.iconId = iconId;
    }
}
