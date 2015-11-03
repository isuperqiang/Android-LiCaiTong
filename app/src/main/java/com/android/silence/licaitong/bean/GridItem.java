package com.android.silence.licaitong.bean;

/**
 * Created by Silence on 2015/10/26 0026.
 * GridView列表元素
 */
public class GridItem {
    private String title;
    private int imageId;

    public GridItem() {
    }

    public GridItem(String title, int imageId) {
        this.title = title;
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
