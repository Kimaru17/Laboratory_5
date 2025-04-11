package domain;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class CircularDoublyLinkedListTest {

    @Test
    void test() {

        CircularDoublyLinkedList a = new CircularDoublyLinkedList();
        CircularDoublyLinkedList b = new CircularDoublyLinkedList();
        CircularDoublyLinkedList c = new CircularDoublyLinkedList();
        CircularDoublyLinkedList d = new CircularDoublyLinkedList();

        CircularLinkedList list =  new CircularLinkedList();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2005,4,30);
        list.add(new Employee(111111111,"Campos", "David", "Informatica", calendar.getTime()));
        calendar.set(2002,5,10);
        list.add(new Employee(222222222,"Jimenez", "Ana", "Administración", calendar.getTime()));
        calendar.set(2000,8,15);
        list.add(new Employee(333333333,"Ramirez", "Jorge", "Ingles", calendar.getTime()));
        calendar.set(1997,4,30);
        list.add(new Employee(111111111,"Gutierrez", "Juan", "Doctor", calendar.getTime()));
        calendar.set(1995,5,10);
        list.add(new Employee(222222222,"Narvaez", "Pedro", "Abogado", calendar.getTime()));
        calendar.set(1991,8,15);
        list.add(new Employee(333333333,"Aguilar", "Pelon", "Ingles", calendar.getTime()));
        calendar.set(1988,4,30);
        list.add(new Employee(111111111,"Varela", "Tilin", "Informatica", calendar.getTime()));
        calendar.set(1978,5,10);
        list.add(new Employee(222222222,"McNullty", "Pepito", "Administración", calendar.getTime()));
        calendar.set(1976,8,15);
        list.add(new Employee(333333333,"Sech", "Fulanito", "Ingles", calendar.getTime()));

        try {
            a=getAgesList(list,18,25);
            b=getAgesList(list,26,40);
            c=getAgesList(list,41,55);
            d=getAgesList(list,56,100);

            System.out.println("Edades entre 18 y 25\n"+a);
            System.out.println("Edades entre 26 y 40\n"+b);
            System.out.println("Edades entre 41 y 55\n"+c);
            System.out.println("Edades mayores a 55\n"+d);
        } catch (ListException e) {
            throw new RuntimeException(e);
        }
    }


    private CircularDoublyLinkedList getAgesList(CircularLinkedList list, int low, int high) throws ListException {

        CircularDoublyLinkedList result = new CircularDoublyLinkedList();
        for (int i = 1; i <= list.size() ; i++) {
            Employee e = (Employee)  list.getNode(i).data;
            int age = e.getAge();
            if(age>=low&&age<=high) result.add(e);


        }
        return result;
    }
}