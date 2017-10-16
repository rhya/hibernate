package my.tests;

import com.nf.emp_ms.entity.Department;
import com.nf.emp_ms.entity.Employee;
import com.nf.emp_ms.util.SessionUtil;
import my.BaseTest;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;
import org.junit.Test;

import java.util.List;

/**
 * Created by admin on 2017/10/13.
 */
public class NativeSQLTest extends BaseTest{

    @Test
    public void testFirst(){

        String sql = "select * from emp where sal>2000";
        List<Employee> employees = session.createNativeQuery(sql)
               .addEntity(Employee.class)
                .list();
        for (Employee employee : employees) {
            System.out.println(employee.getName());
        }

    }

    @Test
    public void testFirst2(){
        String sql = "select empno,ename,sal from emp e where sal > 1000 and comm is not null";
        List<Object[]> list = session.createNativeQuery(sql).list();

        for (Object[] o : list) {
            System.out.printf("编号：%s,名字：%s,工资：%s\n",
                    o[0],o[1],o[2]);
        }

    }

    @Test
    public void testFirst3(){
        //返回 Object[] 将多个实体类对象封装到 Object[] 内返回
        String sql = "select e.*,d.* from emp e,dept d where e.deptno = d.deptno and sal >=3000";

        List<Object[]> o = session.createNativeQuery(sql)
                .addEntity(Employee.class)
                .addEntity(Department.class)
                .list();

        for (Object[] list : o) {
            Employee e = (Employee) list[0];
            Department d = (Department) list[1];

            System.out.printf("%s 在%s 工作\n",e.getName(),d.getLocation());
        }

    }

    @Test
    public void testParameter(){
        String sql = "select e.* from emp e where sal > :s";
        List<Employee> employees = session.createNativeQuery(sql)
                .setParameter("s", 2000)
                .addEntity("e", Employee.class)
                .list();
        for (Employee employee : employees) {
            System.out.println(employee.getName());
        }
    }

    @Test
    public void testScalar(){
        // addScalar
        // 1. 指定返回的字段，比如，下面例子， Object[]中只包含 ename,sal 虽然查询的是
        // 2. 指定类型，提高查询效率
        String sql = "select * from emp where deptno > :d";
        List<Object[]> list = session.createNativeQuery(sql)
                .setParameter("d", 30)
                .addScalar("ename", StandardBasicTypes.STRING)
                .addScalar("sal", StandardBasicTypes.FLOAT)
                .list();

        for (Object[] o : list) {
            System.out.println(o);
            System.out.println("------------");
            System.out.println(o[0]);
            System.out.println(o[1]);
            System.out.println("------------");
        }
    }


}
