package my.tests;

/**
 * Created by admin on 2017/10/10.
 */
public class Boy {

    private String name;
    private float salary;

    public Boy() {
    }

    public Boy(String name, float salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }
}
