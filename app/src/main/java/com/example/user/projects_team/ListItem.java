package com.example.user.projects_team;

/**
 * Created by USER on 2016-10-08.
 */

public class ListItem  {
    private String name;
    private String title;
    private String number;

    public String getNames() { return name; }

    public String getTitles() { return title; }

    public String getNumber() {
        return number;
    }

    public void setName(String newname){
        name = newname;
    }

    public void setTitle(String newtitle){
        title = newtitle;
    }

    public void setNumber(String newnumber){
        number = newnumber;
    }
}
