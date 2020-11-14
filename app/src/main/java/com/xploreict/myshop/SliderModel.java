package com.xploreict.myshop;

public class SliderModel {
    private int banner;
    private String backgroundcolor;

    public SliderModel(int banner, String backgroundcolor) {
        this.banner = banner;
        this.backgroundcolor = backgroundcolor;
    }

    public int getBanner() {
        return banner;
    }

    public void setBanner(int banner) {
        this.banner = banner;
    }

    public String getBackgroundcolor() {
        return backgroundcolor;
    }

    public void setBackgroundcolor(String backgroundcolor) {
        this.backgroundcolor = backgroundcolor;
    }
}
