package domain;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class CircularLinkedListTest {

    @Test
    void test() {
        CircularLinkedList list = new CircularLinkedList();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2005,4,30);
        list.add(new Employee(111111111,"Campos", "David", "Informatica", calendar.getTime()));
        calendar.set(2002,5,10);
        list.add(new Employee(222222222,"Jimenez", "Ana", "Administración", calendar.getTime()));
        calendar.set(2000,8,15);
        list.add(new Employee(333333333,"Tilin", "Jorge", "Ingles", calendar.getTime()));


        System.out.println(list);
        try {
            System.out.println(showAgeList(list,"Empleados con rango de edad entre 18 y 25",18, 25));
            System.out.println(showAgeList(list,"",26, 40));
            System.out.println(showAgeList(list,"",41, 55));
            System.out.println(showAgeList(list,"",56, 100));
            System.out.println(getTitleList(list,"Ingles"));
        } catch (ListException e) {
            throw new RuntimeException(e);
        }

    }

    private String showAgeList(CircularLinkedList list,String msg, int low, int high) throws ListException {
        String result=msg+"\n";


        for (int i = 1; i <= list.size() ; i++) {
            Employee e = (Employee)  list.getNode(i).data;
            int age = e.getAge();
            if(age>=low&&age<=high) result+= e+"\n";

        }
        return result;
    }

    private CircularLinkedList getTitleList(CircularLinkedList list, String title) throws ListException {

        CircularLinkedList l = new CircularLinkedList();

        for (int i = 1; i <= list.size(); i++) {
            Employee e = (Employee)  list.getNode(i).data;
            String t = e.getTittle();
            if(util.Utility.compare(t,title)==0){
                l.add(e);
            }

        }

        return l;

    }
}