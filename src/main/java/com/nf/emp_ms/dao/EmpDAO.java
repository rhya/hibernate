package com.nf.emp_ms.dao;

import com.nf.emp_ms.entity.Employee;
import com.nf.emp_ms.util.SessionUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import java.util.List;

public class EmpDAO {

    // 通过 id 获取
    public Employee getById(long empno) {
        Session session = SessionUtil.getSession();
        Employee employee = session.get(Employee.class,empno);
        session.close();

        return employee;
    }

    // 获取所有员工
    public List<Employee> getAll() {
        Session session = SessionUtil.getSession();
        List<Employee> employees = session.createQuery("from Employee ").list();
        session.close();
        return employees;
    }
    /// 根据用户传入的参数渲染数据
    public List<Employee> getRan(String hql){
        Session session = SessionUtil.getSession();
        List<Employee> list = session.createQuery(hql, Employee.class).list();
        session.close();
        return list;
    }
    //判断传入的参数是否为空
    public boolean conditionPass(String input){
        return input!=null && !input.isEmpty();
    }

    // 根据用户传入的参数
    public List<Employee> queryConditions(String name, String minSal, String maxSal) {
        Session session = SessionUtil.getSession();
        String hql = "from Employee where 1=1";

        if(conditionPass(name)){
            hql += "and name like '"+name.toUpperCase()+"%'";
        }
        if(conditionPass(minSal)){
            hql += "and salary >="+minSal;
        }
        if(conditionPass(maxSal)){
            hql += "and salary <="+maxSal;
        }

        Query<Employee> query = session.createQuery(hql, Employee.class);

        List<Employee> employees = query.list();
        session.close();
        return employees;
    }

    public List<Employee> queryCriteria(String name, String minSal, String maxSal) {

        Session session = SessionUtil.getSession();
        Criteria criteria = session.createCriteria(Employee.class);
        if (conditionPass(name)){
            criteria.add(Restrictions.like("name",name+"%"));
        }
        if(conditionPass(minSal)){
            criteria.add(Restrictions.ge("salary",Float.parseFloat(minSal)));
        }
        if(conditionPass(maxSal)){
            criteria.add(Restrictions.le("salary",Float.parseFloat(maxSal)));
        }
        List<Employee> employees = criteria.list();
        session.close();
        return employees;
    }
}
