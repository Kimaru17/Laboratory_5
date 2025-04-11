package util;

import domain.*;
import domain.Employee;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Utility {
    private static final Random random;
    private static CircularLinkedList employeeList;
    private static CircularDoublyLinkedList staffList;
    private static CircularDoublyLinkedList jobPositionList;
    private static CircularLinkedList assignTypeList;
    private static CircularLinkedList supervisorList;
    private static int staffId = 1 ;
    private static final String[] firstNames = {"Ana", "Carlos", "Lucía", "Pedro", "María", "Julio", "Antonio", "Pedro", "Felipe"};
    private static final String[] lastNames = {"García", "López", "Martínez", "Rodríguez", "Pérez", "Aguilar", "Cerdas", "Varela"};

    //constructor estatico, inicializador estatico
    static {
        // semilla para el random
        long seed = System.currentTimeMillis();
        random = new Random(seed);
        employeeList = new CircularLinkedList();
        staffList = new CircularDoublyLinkedList();
        assignTypeList = new CircularLinkedList();
        supervisorList = new CircularLinkedList();
        jobPositionList = new CircularDoublyLinkedList();
        generateRandomSupervisors();
        assignTypeList.add("Promocion");
        assignTypeList.add("Transferencia");
        assignTypeList.add("Nuevo ingreso");
        assignTypeList.add("Temporal");
        assignTypeList.add("Otro");
    }

    private static void generateRandomSupervisors() {
        for (int i = 0; i < 5; i++) {
            String firstName = firstNames[random.nextInt(firstNames.length)];
            String lastName = lastNames[random.nextInt(lastNames.length)];
            supervisorList.add(lastName + ", " + firstName);

    }}

    public static CircularDoublyLinkedList getJobPositionList() {return jobPositionList;}

    public static void setJobPositionList(CircularDoublyLinkedList jobPositionList) {Utility.jobPositionList = jobPositionList;}

    public static CircularLinkedList getSupervisorList() {return supervisorList;}

    public static int getStaffId() {return staffId;}
    public static void setStaffId(int id) {staffId = id;}

    public static CircularLinkedList getAssignTypeList() {return assignTypeList;}

    public static void setAssignTypeList(CircularLinkedList assignList) {Utility.assignTypeList = assignList;}

    public static CircularLinkedList getEmployeeList() {
        return employeeList;
    }

    public static void setEmployeeList(CircularLinkedList employeeList) {
        Utility.employeeList = employeeList;
    }

    public static CircularDoublyLinkedList getStaffList() {return staffList;}

    public static void setStaffList(CircularDoublyLinkedList staffList) {Utility.staffList = staffList;}

    public static int random(int bound){
        //return (int)Math.floor(Math.random()*bound); //forma 1
        return 1+random.nextInt(bound);
    }

    public static void fill(int[] a) {
        for (int i = 0; i < a.length; i++) {
            a[i] = random(99);
        }
    }

    public static String format(long n) {
        return new DecimalFormat("###,###,###.##").format(n);
    }

    public static int min(int x, int y) {
        return x<y ? x : y;
    }

    public static int max(int x, int y) {
        return x>y ? x : y;
    }

    public static String show(int[] a) {
        String result="";
        for(int item : a){
            if(item == 0) break; //si es cero es xq no hay mas elementos
            result+=item + " ";
        }
        return result;
    }

    public static int compare(Object a, Object b) {
        switch(instanceOf(a, b)){
            case "Integer":
                Integer int1 = (Integer)a; Integer int2 = (Integer)b;
                return int1 < int2 ? -1 : int1 > int2 ? 1 : 0;

            case "String":
                String str1 = (String)a; String str2 = (String)b;
                return str1.compareTo(str2) < 0 ? -1 : str1.compareTo(str2) > 0 ? 1 : 0;

            case "Character":
                Character ch1 = (Character) a; Character ch2 = (Character) b;
                return ch1.compareTo(ch2) < 0 ? -1 : ch1.compareTo(ch2) > 0 ? 1 : 0;

            case "Employee":
                Employee emp1 = (Employee) a; Employee emp2 = (Employee) b;
                return emp1.getId() < emp2.getId() ? -1
                        :  emp1.getId() > emp2.getId() ? 1 : 0;

        }
        return 2; //Unknown
    }
    public static boolean isInteger(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String instanceOf(Object a, Object b){
        if(a instanceof Integer && b instanceof Integer) return "Integer";
        if(a instanceof String && b instanceof String) return "String";
        if(a instanceof Character && b instanceof Character) return "Character";
        if(a instanceof Employee && b instanceof Employee) return "Employee";
        return "Unknown";
    }
    public static int getAge(Date date) {
        if (date == null) return 0;

        Calendar birthCal = Calendar.getInstance();
        birthCal.setTime(date);

        Calendar todayCal = Calendar.getInstance();

        int age = todayCal.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);

        // Verificar si el cumpleaños ya pasó este año
        if (todayCal.get(Calendar.DAY_OF_YEAR) < birthCal.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }

    public static String dateFormat(Date value){
        return new SimpleDateFormat("dd/MM/yyyy").format(value);
    }


}
