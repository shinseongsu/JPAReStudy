package com.spring.jpa.vo;

import java.sql.Timestamp;

public class EmployeeVO {
    private Long id;                // 직원 아이디
    private String name;            // 직원 이름
    private Timestamp startDate;    // 입사일
    private String title;           // 직급
    private String deptName;        // 부서 이름
    private Double salary;          // 급여

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDeptName() {
        return deptName;
    }

    public Double getSalary() {
        return salary;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "EmployeeVO[" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", title='" + title + '\'' +
                ", deptName='" + deptName + '\'' +
                ", salary=" + salary +
                ']';
    }
}
