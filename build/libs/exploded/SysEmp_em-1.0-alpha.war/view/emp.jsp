<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>员工页面</title>
</head>
<body>
<div>
    <header>
        <h2>员工信息</h2>
    </header>
    <section>
        <div>员工编号： ${employee.empno}</div>
        <div>员工姓名： ${employee.name}</div>
        <div>员工工资： ${employee.salary}</div>
        <div>员工经理： <a href="/emp?empno=${employee.manager.empno}">${employee.manager.name}</a></div>
        <div>员工部门： <a href="/dept?deptno=${employee.department.deptno}">${employee.department.name}</a></div>
    </section>
</div>
</body>
</html>
