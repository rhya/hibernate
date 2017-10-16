package com.nf.emp_ms.web;

import com.nf.emp_ms.dao.EmpDAO;
import com.nf.emp_ms.entity.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by admin on 2017/10/11.
 */
@WebServlet("/ran")
public class RanServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String names = request.getParameter("name");
        String minSal = request.getParameter("minSal");
        String maxSal = request.getParameter("maxSal");

        String hql = "from Employee where name like '%"+names+"%' and salary >= "+minSal+" and salary <= "+maxSal+"";


        List<Employee> ran = new EmpDAO().getRan(hql);
        System.out.println(ran.size());
        request.getSession().setAttribute("emps",ran);
        request.getRequestDispatcher("/view/all.jsp");



    }

}
