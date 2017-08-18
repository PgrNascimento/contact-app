package br.com.campuscode.contactapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alan_mimi on 14/08/17.
 */

public class Contact {

    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("phone")
    private String phone;

    public Contact(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public String getId() {
        return id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
