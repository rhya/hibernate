package com.nf.emp_ms.entity;


import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dept")
@NamedQueries(
        {
                @NamedQuery(name = "dept_aaa", query = "from Department where deptno = :dn"),
                @NamedQuery(name = "dept_bbb", query = "from Department where deptno > :dn")
        }
)
public class Department {

    @Id
    @GeneratedValue(generator = "aaa")
    @SequenceGenerator(name = "aaa", sequenceName = "seq_dept", allocationSize = 1)
    private long deptno;

    @Column(name = "dname")
    private String name;

    @Column(name = "loc")
    private String location;

    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL)
    //@Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 5)
    private List<Employee> employees = new ArrayList<>();

    public Department() {
    }

    public Department(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public long getDeptno() {
        return deptno;
    }

    public void setDeptno(long deptno) {
        this.deptno = deptno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Department{" +
                "deptno=" + deptno +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
