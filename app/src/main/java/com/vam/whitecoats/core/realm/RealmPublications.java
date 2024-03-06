package com.vam.whitecoats.core.realm;

import androidx.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by lokeshl on 7/9/2015.
 */
public class RealmPublications extends RealmObject{
    @Required
    private Integer pub_id;
    private String title;
    private String authors;
    private String journal;
    private String web_page;
    private String type;


    public Integer getPub_id() {
        return pub_id;
    }

    public void setPub_id(Integer pub_id) {
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
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
