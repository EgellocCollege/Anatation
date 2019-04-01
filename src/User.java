
@TAG(Key="student")
public class User {

    private String name;

    @TAG(Key="name")
    public void setName(String name) {
        this.name = name;
    }
    @TAG(Key="sex")
    public void setSex(String sex) {
        this.sex = sex;
    }

    public User(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    public User() {
    }

    private String sex;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
