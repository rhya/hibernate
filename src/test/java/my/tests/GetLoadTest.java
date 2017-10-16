package my.tests;

import com.nf.emp_ms.entity.Department;
import com.nf.emp_ms.entity.Employee;
import my.BaseTest;
import org.hibernate.query.Query;
import org.junit.Test;

import java.util.List;

public class GetLoadTest extends BaseTest {

    @Test
    public void getTest () {
        Employee employee = (Employee) session.get("com.nf.emp_ms.entity.Employee", 7521L);
        System.out.println(employee);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Test
    public void loadTest () {
        Employee employee = (Employee) session.load(Employee.class, 7521L);
        System.out.println(employee);
    }

    ////// 分页
    @Test
    public void pagination(){
        List<Employee> employees = session.createQuery("from Employee ").setFirstResult(0).setMaxResults(5).list();
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    ///  计算总条数
    @Test
    public void selectCount(){
        Long count = (Long) session.createQuery("select count(*) from Employee ").uniqueResult();
        System.out.println(count);
    }

    @Test
    public void testJoinQueryPre(){
        //默认情况下，Entity属性，采取的是EAGER + JOIN 的策略
        Employee employee = session.get(Employee.class, 7521L);
        System.out.println(employee.getDepartment().getLocation());

    }

    @Test
    public void testJoinQuery1(){
        //默认情况下，Join策略对Query查询是无效的
        // 默认 Query 查询会采取Select的策略
        String hql = "from Employee ";
        Employee employee = session.createQuery(hql, Employee.class).list().get(0);
        System.out.println(employee.getName() + "`````" + employee.getDepartment().getLocation());

    }

    @Test
    public void testJoinQuery2(){
        //1 、 隐式的 Join 调用
        String hql = "from Employee e where e.department.location = 'NEW YORK'";
        Object employee = session.createQuery(hql).list().get(0);
        System.out.println(employee);
    }

    @Test
    public void testJoinQuery3(){
        // 2、显示的 Join 调用
        // 默认的情况下，返回一个数组，数组里面， 包含着连接的各个实体类对象
        // [employee,department]
        String hql = "select e.name, d.location from Employee e left join e.department d where d.location = 'NEW YORK'";
        Object[] o = (Object[]) session.createQuery(hql).list().get(0);
        System.out.println(o.length);
        System.out.println(">>>> 元素分别为:");
        System.out.println(o[0]+ "``````````" + o[1]);
    }

    @Test
    public void testJoinQuery4Fetch(){
        // 2、 显示的 Join 调用
        // 通过 fetch 关键词，设置， 返回的结果是 Employee 对象
        String hql = "from Employee e left join fetch e.department where e.empno = 7521";
        Employee employee = session.createQuery(hql, Employee.class).list().get(0);
        System.out.println(employee);

        System.out.println("----------------------------");
        System.out.println(session.get(Employee.class,7521L));
    }

    @Test
    public void testDeleteInitData(){
        Department department = new Department();
        department.setName("保安");
        department.setLocation("校门口");

        Employee employee1 = new Employee();
        employee1.setName("小狗");
        employee1.setDepartment(department);

        Employee employee2 = new Employee();
        employee2.setName("小猫");
        employee2.setDepartment(department);

        Employee employee3 = new Employee();
        employee3.setName("小猪");
        employee3.setDepartment(department);

        session.save(department);
        session.save(employee1);
        session.save(employee2);
        session.save(employee3);
    }

    @Test
    public void testDelete2Query(){
        //在quert中进行删除
        int count = session.createQuery("delete Employee where empno =8013").executeUpdate();
        System.out.println(count);
    }

    @Test
    public void testDelete3(){
        //通过 delete 方法删除
        //关联删除
        Employee employee = session.load(Employee.class, 8014L);
        session.delete(employee);
    }

    @Test
    public void testDelete4(){
        /*Employee employee = new Employee();
        employee.setEmpno(8019L);*/
        Employee employee = session.get(Employee.class, 8025L);
        session.delete(employee);
    }

    @Test
    public void testDeleteCascade(){
        Department department = new Department();
        department.setDeptno(124L);
        //Department department = session.get(Department.class, 123L);
        session.delete(department);
    }

    @Test
    public void testSaveCascade(){
        Department department = new Department();
        department.setName("svt");

        Employee employee = new Employee();
        employee.setName("tvs");
        employee.setDepartment(department);

        session.persist(employee);
    }

    @Test
    public void testUpdate2Query(){
        int count = session.createQuery("update Employee set salary = :sal where id = :id")
                .setParameter("sal", 2555f)
                .setParameter("id", 8031L)
                .executeUpdate();
        if(count == 1){
            System.out.println("修改成功");
        }else {
            System.out.println("修改失败");
        }



    }



}
