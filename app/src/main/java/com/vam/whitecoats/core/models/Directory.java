package com.vam.whitecoats.core.models;

/**
 * Created by satyasarathim on 20-06-2016.
 */
public class Directory {
    int departmentId;
    int deptMembersCount;
    String departmentName;

    public int getDeptMembersCount() {
        return deptMembersCount;
    }

    public void setDeptMembersCount(int deptMembersCount) {
        this.deptMembersCount = deptMembersCount;
    }



    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
