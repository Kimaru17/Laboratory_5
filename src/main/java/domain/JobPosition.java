package domain;

public class JobPosition {
    private int id;
    private String description;
    private double hourlyWage;
    private static int autoId; //para el autogenerado
    private int totalHours;
    private double salary;

    //Constructor 1
    public JobPosition(int id, String description, double hourlyWage) {
        this.id = id;
        this.description = description;
        this.hourlyWage = hourlyWage;
    }

    //Constructor 2
    public JobPosition(String description, double hourlyWage) {
        this.id = ++autoId;
        this.description = description;
        this.hourlyWage = hourlyWage;
    }



    public double getSalary(double n) {
        return n * hourlyWage;
    }
    public double getSalary(){
        return getSalary(this.totalHours);
    }

    //Constructor 3
    public JobPosition(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public static int getAutoId() {
        return autoId;
    }

    public static void setAutoId(int autoId) {
        JobPosition.autoId = autoId;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    @Override
    public String toString() {
        return "(ID)"+id+"/(Job Position)"+description+ "/(Hourly Wage)"+hourlyWage;
    }
}
