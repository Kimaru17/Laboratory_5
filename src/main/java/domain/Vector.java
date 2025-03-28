package domain;

public class Vector implements VectorInterface {
    private int n; //tamaño máximo del vector
    private int[] data; //array de elementos tipo enteros
    private int counter; //cantidad de elementos agregados

    //Constructor
    public Vector(int n){
        this.n = n;
        this.data = new int[n];
        this.counter = 0; //significa q tenemos 0 elementos agregados
    }

    public int[] getData() {
        return data;
    }

    @Override
    public int size() {
        return counter;
    }

    @Override
    public void clear() {
        this.data = new int[n];
        this.counter = 0; //significa q tenemos 0 elementos agregados
    }

    @Override
    public boolean isEmpty() {
        return counter == 0;
    }

    @Override
    public boolean contains(Object element) {
        return false;
    }

    @Override
    public void add(Object element) {
        if(counter<this.data.length)
            this.data[counter++] = (int)element;
    }

    @Override
    public void add(int index, Object element) {
        if(index<data.length) {
            data[index] = (int) element;
            if(counter<=index)
                counter = index+1; //contador queda en la sgte pos a agregar
        }
    }

    @Override
    public boolean remove(Object element) {
        return false;
    }

    @Override
    public Object remove(int index) {
        return null;
    }

    @Override
    public void sort() {
        bubbleSort(this.data, counter);
    }

    @Override
    public int indexOf(Object element) {
        return 0;
    }

    @Override
    public Object get(int index) {
        return null;
    }

    @Override
    public void fill() {
        util.Utility.fill(this.data);
        this.counter = this.n;
    }

    // An optimized version of Bubble Sort
    static void bubbleSort(int arr[], int n){
        int i, j, temp;
        boolean swapped;
        for (i = 0; i < n - 1; i++) {
            swapped = false;
            for (j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {

                    // Swap arr[j] and arr[j+1]
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }

            // If no two elements were
            // swapped by inner loop, then break
            if (swapped == false)
                break;
        }
    }
}
