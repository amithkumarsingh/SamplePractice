package com.vam.whitecoats.core.realm;

import androidx.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by swathim on 5/21/2015.
 */
public class RealmPrintPub extends RealmObject {


    @Required
    private String id;

    private int printId;
    private String print_name;


    public int getPrintId() {
        return printId;
    }

    public void setPrintId(int printId) {
        this.printId = printId;
    }

    public String getPrint_name() {
        return print_name;
    }

    public void setPrint_name(String print_name) {
        this.print_name = print_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
