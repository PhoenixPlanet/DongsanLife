package com.example.user.projects_team;

public class UserData {
    private String userName;
    private String classNum;
    private String gradeNum;

    public UserData() {}

    public UserData(String userName, String classNum, String gradeNum) {
        this.userName = userName;
        this.classNum = classNum;
        this.gradeNum = gradeNum;
    }

    public String getUserName(){
        return userName;
    }

    public String getClassNum(){
        return classNum;
    }

    public String getGradeNum(){
        return gradeNum;
    }
}
