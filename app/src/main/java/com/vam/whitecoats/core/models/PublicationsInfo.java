package com.vam.whitecoats.core.models;

import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by swathim on 5/21/2015.
 */
public class PublicationsInfo implements Serializable{

    String title;
    String authors;
    String journal;
    String web_page;
    boolean isFirstItem=false;
    public boolean isFirstItem() {
        return isFirstItem;
    }

    public void setFirstItem(boolean firstItem) {
        isFirstItem = firstItem;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String type;

    int pub_id;

    public int getPub_id() {
        return pub_id;
    }

    public void setPub_id(int pub_id) {
        this.pub_id = pub_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getWeb_page() {
        return web_page;
    }

    public void setWeb_page(String web_page) {
        this.web_page = web_page;
    }





}
