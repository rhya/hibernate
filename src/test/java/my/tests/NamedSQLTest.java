package my.tests;

import com.nf.emp_ms.dao.EmpDAO;
import com.nf.emp_ms.entity.Employee;
import my.BaseTest;
import org.junit.Test;

import java.util.List;

/**
 * Created by admin on 2017/10/13.
 * 为我们的SQL/HQL语句，起一个响当当的名字
 * 一切为了简单，总之为了复用
 */
public class NamedSQLTest extends BaseTest{

    @Test
    public void testBasicResult(){
        List<Employee> employees = session.createNamedQuery("gongzidayu")
                .setParameter("sal", 4000F)
                .list();
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    @Test
    public void testNamedNativeQuery(){

        List<Object[]> gongzidayunative = session.createNamedQuery("gongzidayunative")
                .setParameter("sal", 2000F)
                .list();

        for (Object[] o : gongzidayunative) {
            System.out.println(o[0]+"的工资是："+o[1]);
        }
    }

    @Test
    public void testQueries1(){
        List list = session.createNamedQuery("dept_aaa")
                .setParameter("dn", 20L).list();
        for (Object o : list) {
            System.out.println(o);
        }

    }
    @Test
    public void testQueries2(){
        List list = session.createNamedQuery("dept_bbb")
                .setParameter("dn", 20L)
                .list();
        for (Object o : list) {
            System.out.println(o);
        }
    }
}
