package domain;

import java.util.Date;

public class Employee {
    public int id;
    public String lastName;
    public String firstName;
    public String tittle;
    private Date birthday;

    public Employee(int id, String lastName, String firstName, String tittle, Date birthday) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.tittle = tittle;
        this.birthday = birthday;
    }

    public Employee(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getAge(Date birthDate) {
        long ageInMillis = new Date().getTime() - birthDate.getTime();
        long millisInYear = 1000L * 60 * 60 * 24 * 365;
        return (int)(ageInMillis / millisInYear);
    }

    public int getAge(){
        return getAge(birthday);
    }

    @Override
    public String toString() {
        return  "(ID)"+id +"/(Name)"+lastName+", "+firstName
                + "/(Birthday)"+util.Utility.dateFormat(birthday)+ "/(Title)"+tittle
                +"/(Age)"+ getAge(birthday);
    }
}
