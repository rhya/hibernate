package my.tests;

import com.nf.emp_ms.entity.Company;
import com.nf.emp_ms.entity.Employee;
import my.BaseTest;
import org.hibernate.LockMode;
import org.junit.Test;

import javax.persistence.LockModeType;
import java.util.List;

/**
 * Created by admin on 2017/10/16.
 */
public class LockTest extends BaseTest{

    @Test
    public void testPessimisticLock1(){
        Employee tvs = session.load(Employee.class, 8031L, LockMode.UPGRADE_NOWAIT);
        tvs.setSalary(9999F);
        session.save(tvs);
    }

    @Test
    public void testPessimisticLock2(){
        Employee tvs = session.load(Employee.class, 8031L);
        // .. 若干事务

        session.lock(tvs,LockMode.UPGRADE_NOWAIT);
        tvs.setSalary(8888F);
        session.save(tvs);
    }

    @Test
    public void testPessimisticLock3(){
        List<Employee> employees = session.createQuery("from Employee where id = 8031", Employee.class)
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .list();
        System.out.println(employees.size());
    }

    @Test
    public void testOptimisticLock1(){
        session.save(new Company("华为", "深圳"));
        session.save(new Company("格力", "珠海"));
        session.save(new Company("金山", "珠海"));
        session.save(new Company("魅族", "珠海"));
        session.save(new Company("用友", "珠海"));
    }

    @Test
    public void testOptimisticLock2(){
        //悲观锁实例
        Company hw = session.load(Company.class, 1L, LockMode.UPGRADE_NOWAIT);
        hw.setAddress("广东省东莞市");
        session.save(hw);
    }

    @Test
    public void testOptimisticLoc2(){
        //
        Company hw = session.load(Company.class, 1L);
        hw.setAddress("广东省东莞市");
        session.save(hw);
    }

}
