package com.first.dlam.bean;

import com.first.dlam.base.views.TimeBarView;

import java.io.Serializable;
import java.util.ArrayList;

public class QueueBean implements Serializable {

    private ArrayList<TimeBarView.HoBarEntity> TileBarEntities;
    private String title;
    private String content;
    private String codeName;
    private String info;

    public QueueBean(ArrayList<TimeBarView.HoBarEntity> TileBarEntities,String title,String content,String info,String codeName){
       this.TileBarEntities =TileBarEntities;
       this.title=title;
       this.content=content;
       this.codeName=codeName;
       this.info=info;
    }

    public ArrayList<TimeBarView.HoBarEntity> getTileBarEntities() {
        return TileBarEntities;
    }

    public void setTileBarEntities(ArrayList<TimeBarView.HoBarEntity> tileBarEntities) {
        TileBarEntities = tileBarEntities;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
