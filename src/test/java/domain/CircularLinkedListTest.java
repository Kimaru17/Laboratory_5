package domain;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class CircularLinkedListTest {

    @Test
    void test() {
        CircularLinkedList list = new CircularLinkedList();
        Calendar calendar = Calendar.getInstance();
        //profesiones
        String[] professions = {
                "Informática", "Administración", "Inglés", "Turismo", "Agronomía",
                "Diseño Publicitario", "Diseño Web", "Asesor", "Doctor", "Abogado"
        };
        //apellidos
        String[] lastName = {
                "Mora", "Chavez", "Lopez", "Maduro", "Sanchez",
                "Montenegro", "Calvo", "Garcia", "Herrera", "Nuñez"
        };
        //nombres
        String[] firstName = {
                "Gilberth", "Evans", "Eduardo", "Varela", "Sebastian",
                "Daniel", "Roberto", "Adrian", "Alex", "Felipe"
        };
        //Años para las edades
        int[] year = {2004, 2000, 1999, 1989, 1972, 1979, 1936, 1945, 1993, 1969};

        // crear y agregar empleados
        for (int i = 0; i < professions.length; i++) {
            int birthYear = year[i];
            calendar.set(birthYear, Calendar.JANUARY, 1+i); // Fecha fija
            list.add(new Employee(100000000 + i, lastName[i], firstName[i], professions[i], calendar.getTime()));
        }


        System.out.println(list);
        System.out.println("\n");
        try {
            System.out.println(showAgeList(list,"Empleados con rango de edad entre 18 y 25",18, 25));
            System.out.println(showAgeList(list,"Empleados con rango de edad entre 26 y 40",26, 40));
            System.out.println(showAgeList(list,"Empleados con rango de edad entre 41 y 55",41, 55));
            System.out.println(showAgeList(list,"Empleados con mas de 55 años de edad",56, 100));
            /*
            System.out.println("Empleados con el titulo de Ingles");
            System.out.println(getTitleList(list,"Ingles"));
            System.out.println("Empleados con el titulo de Informatica");
            System.out.println(getTitleList(list,"Informatica"));
            System.out.println("Empleados con el titulo de Administracion");
            System.out.println(getTitleList(list,"Administración"));
             */
            System.out.println(showTitleList(list, "Empleados con título Informática", "Informática"));
            System.out.println(showTitleList(list, "Empleados con título Administración", "Administración"));
            System.out.println(showTitleList(list, "Empleados con título Inglés", "Inglés"));
            System.out.println(showTitleList(list, "Empleados con título Turismo", "Turismo"));
            System.out.println(showTitleList(list, "Empleados con título Agronomía", "Agronomía"));
            System.out.println(showTitleList(list, "Empleados con título Diseño Publicitario", "Diseño Publicitario"));
            System.out.println(showTitleList(list, "Empleados con título Diseño Web", "Diseño Web"));
            System.out.println(showTitleList(list, "Empleados con título Asesor", "Asesor"));
            System.out.println(showTitleList(list, "Empleados con título Doctor", "Doctor"));
            System.out.println(showTitleList(list, "Empleados con título Abogado", "Abogado"));

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
    private String showTitleList(CircularLinkedList list, String msg, String title) throws ListException {
        String result = msg + "\n";
        for (int i = 1; i <= list.size(); i++) {
            Employee e = (Employee) list.getNode(i).data;
            if (util.Utility.compare(e.getTittle(), title) == 0) {
                result += e + "\n";
            }
        }
        return result;
    }
    /*
    private CircularLinkedList getTitleList(CircularLinkedList list, String title) throws ListException {
        CircularLinkedList l = new CircularLinkedList();
        for (int i = 1; i <= list.size(); i++) {
            Employee e = (Employee)  list.getNode(i).data;
            String t = e.getTitle();
            if(util.Utility.compare(t,title)==0){
                l.add(e);
            }
        }
        return l;
    }
     */
}