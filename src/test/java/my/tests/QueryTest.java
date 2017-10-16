package my.tests;

import com.nf.emp_ms.entity.Employee;
import my.BaseTest;
import org.hibernate.query.Query;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class QueryTest extends BaseTest{

    @Test
    public void testBasicResult () {

        //sql
        //hibernate sql:  hql
        String hql = "from Employee where id = 7521L";
        Query query = session.createQuery(hql);

        //第一种得到结果的方法
        List<Employee> employees = query.list();
        System.out.println(employees);
        System.out.println();
        //第二种得到结果的方法，这是上面方法的一种快捷方法
        //这种方法是确定只有一条数据的时候用
        Employee employee = (Employee) query.uniqueResult();
        //第三种得到结果的方法  迭代
        Iterator iterate = query.iterate();
        while (iterate.hasNext()){
            System.out.println(iterate.next());
        }

    }

    @Test
    public void testChainSyntax(){
        //可以采取链式语法
        System.out.println(session.createQuery("from Employee ").list().size());
    }

    @Test
    public void testWhere1(){
        //条件中的name / salary 应该是 Employee类的属性
        String hql = " from Employee where name like 'M%' and salary >= 1300";
        Iterator iterate = session.createQuery(hql).iterate();
        while(iterate.hasNext()){
            System.out.println(iterate.next());
        }
    }

    @Test
    public void testWhere2(){
        //可以占位符
        String hql = "from Employee where name like ? and salary >= ?";
        Iterator iterate = session.createQuery(hql)
                .setString(0, "J%")
                .setParameter(1, 2000F)
                .iterate();
        while(iterate.hasNext()){
            System.out.println(iterate.next()+"-----------------");
        }
    }

    @Test
    public void testWhere3(){
        //可以使用占位符，命名占位符
        String hql = "from Employee where name like :name and salary >= :sal";
        List<Employee> employees = session.createQuery(hql)
                .setParameter("sal", 1000F)
                .setParameter("name", "M%")
                .list();
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    @Test
    public void testSelect1(){
        //默认情况下，如果不写select相当于下面语句
        //会将结果封装到Employee的类中
        String hql = "select e from Employee e where name = 'KING'";
        System.out.println(session.createQuery(hql).uniqueResult());
    }

    @Test
    public void testSelect2(){
        //得到一个单独的值
        String hql = "select name from Employee where id = :id";
        String name = (String) session.createQuery(hql).setParameter("id", 7521L).uniqueResult();
        System.out.println(name);
    }

    @Test
    public void testSelect3Single(){
        //得到一个单独的值，封装到list中
        String hql = "select name from Employee ";
        List<String> names = session.createQuery(hql).list();
        for (String name : names) {
            System.out.println(name);
        }
    }

    @Test
    public void testSelect4Array(){
        /////如果查询多个条件，得到的是一个Object[]数组
        String hql = "select name,salary from Employee where id = 7521";
        Object[] os = (Object[]) session.createQuery(hql).uniqueResult();
        System.out.println(os[0]+"/////////"+os[1]);
    }

    @Test
    public void testSelect5(){
        // 聚合函数，返回Object[]数组
        String hql = "select max(salary),avg (salary),sum(salary) from Employee ";
        Object[] os = (Object[]) session.createQuery(hql).uniqueResult();
        System.out.printf("所有人的最大工资 %.2f,平均工资是 %.2f,工资总和是： %.2f\n",os[0],os[1],os[2]);

    }

    @Test
    public void testSelect6Group(){
        //聚合函数 group by ,返回 Object[]数组
        String hql = "select e.department.deptno,max(salary),avg(salary),min(salary) from Employee e group by e.department order by e.department.deptno";
        List<Object[]> employees = session.createQuery(hql).list();
        for (Object[] os : employees) {
            System.out.printf("组%d 所有人的最大工资%.2f,平均工资是 %.2f,平均总和是：%.2f\n",
                    os[0],os[1],os[2],os[3]);
        }

    }

    @Test
    public void testSelect7List(){
        //将返回结果封装到list中
        String hql = "select new list(name,salary) from Employee where id = 7521";
        List e  = (List) session.createQuery(hql).uniqueResult();
        System.out.println(e.size());
        System.out.println(e.get(0));
        System.out.println(e.get(1));

    }

    @Test
    public void testSelect8Map(){
        // 将返回结果封装到 MAP 中
        String hql = "select new map(name as 名字, salary+nvl(commission,0) as 工资) from Employee where id=7369";
        Map e = (Map) session.createQuery(hql).uniqueResult();

        System.out.println(e);
        System.out.println(e.get("名字")+" 的工资是： "+e.get("工资"));

    }

    @Test
    public void testSelect9(){
        //将结果集封装到一个对象中
        String hql = "select name,salary from Employee where id = 7521";
        Object[] os = (Object[]) session.createQuery(hql).uniqueResult();
        Boy boy = new Boy((String) os[0], (Float) os[1]);
        System.out.println(boy);

        ////   可以简写成下面
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        String hql2 = "select new my.tests.Boy(name,salary) from Employee where id = 7521";
        Boy boy2 = session.createQuery(hql2, Boy.class).uniqueResult();
        System.out.println(boy2);

    }
}
