import java.util.Iterator;

class MyDynamicArray<T> implements Iterable<T>
{
    private int size;
    private int capacity;
    private T [] arr;

    public int getSize()
    {
        return this.size;
    }
    public int getCapacity()
    {
        return this.capacity;
    }
    public void enlarge() {
        if(this.capacity == 0) {
            this.capacity = 1;
        }
        this.capacity *= 2;
        T [] arr1 = (T[]) new Object[this.capacity];
        for(int i = 0; i < this.size; ++i) {
            arr1[i] = this.arr[i];
        }
        this.arr = arr1;

    }
    public void pushBack(T val) {
        if(this.size == this.capacity) {
            enlarge();
        }
        this.arr[this.size] = val;
        ++this.size;
    }
    public T popBack()
    {
        if(this.isEmpty())
        {
            throw new RuntimeException("Invalid");
        }
        T temp = this.arr[this.size - 1];
        //this.arr[this.size - 1] = null;    // null for garbage collection
        --this.size;
        return temp;
    }
    public void insert(T val, int index) {
        if(index < 0 || index > this.size)
        {
            throw new RuntimeException("Invalid index");
        }
        if(this.size == this.capacity) {
            enlarge();
        }
        if(index == this.size)
        {
            this.pushBack(val);
            return;
        }
        T [] arr1 = (T[]) new Object[this.capacity];
        int j = 0;
        for(int i = 0; i < index; ++i)
        {
            arr1[j] = this.arr[i];
            ++j;
        }
        arr1[j] = val;
        ++j;
        for(int i = index; i < this.size; ++i)
        {
            arr1[j] = this.arr[i];
            ++j;
        }

        this.arr = arr1;
        this.size = this.size + 1;
    }

    public T delete(int index)
    {
        if(index < 0 || index >= this.size)
        {
            throw new RuntimeException("Invalid index");
        }
        if(this.isEmpty())
        {
            throw new RuntimeException("Invalid");
        }
        if(index == this.size - 1)
        {
            this.popBack();
        }
        T [] arr1 = (T[]) new Object[this.capacity];
        int j = 0;
        for(int i = 0; i < this.size; ++i)
        {
            if(i == index)
            {
                continue;
            }
            arr1[j] = this.arr[i];
            ++j;
        }
        T temp = arr[index];
        this.arr = arr1;
        --this.size;
        return temp;
    }
    public int search(T val)
    {
        for(int i = 0; i < this.size; ++i)
        {
            if(this.arr[i].equals(val))
            {
                return  i;
            }
        }
        return -1; //There is not val
    }

    public void shrink()
    {
        if(this.size < this.capacity)
        {
            this.capacity = (int)(this.size + (this.size * 0.25));
            T [] arr1 = (T[]) new Object[this.capacity];
            for(int i = 0; i < this.size; ++i)
            {
                arr1[i] = arr[i];
            }
            this.arr = arr1;
        }

    }

    public void resize(int count)
    {
        if(count > this.size || count < 0)
        {
            throw new RuntimeException("Invalid count");
        }
        this.size = count;
        T [] arr1 = (T[]) new Object[this.capacity];
        for(int i = 0; i < this.size; ++i)
        {
            arr1[i] = this.arr[i];
        }
        this.arr = arr1;
    }
    public void resize(int count, T val)
    {
        if(count < 0)
        {
            throw new RuntimeException("Invalid count");
        }
        if(count < this.size)
        {
            this.resize(count);
        }
        else if(count > this.size)
        {
            T [] arr1 = (T[]) new Object[this.capacity];
            int i = 0;
            for(; i < this.size; ++i)
            {
                arr1[i] = arr[i];
            }
            for(; i < count; ++i)
            {
                arr1[i] = val;
            }
            this.arr = arr1;
        }

    }


    private class ITR implements Iterator<T>
    {
        private int currentIndex = 0;
        @Override
        public boolean hasNext()
        {
            return this.currentIndex < size;
        }

        @Override
        public T next()
        {
            if(!hasNext())
            {
                throw new RuntimeException("Invalid");
            }
            T temp = arr[currentIndex];
            ++currentIndex;
            return temp;
        }

    }
    public Iterator<T> iterator()
    {
        return new ITR();
    }

    //    Modifications
    public void pushBack(T [] array)
    {
        for(int i = 0; i < array.length; ++i)
        {
            this.pushBack(array[i]);
        }
    }

    public void pushBack(T val, int count)
    {
        for(int i = 0; i < count; ++i)
        {
            this.pushBack(val);
        }
    }
    public void insert(T [] array, int index)
    {
        int temp = index;
        for(int i = 0; i < array.length; ++i)
        {
            this.insert(array[i], temp);
            ++temp;
        }
    }

    public void delete(int index, int count)
    {
        if (index < 0 || index >= size) {
            throw new RuntimeException("Invalid index");
        }

        if (count < 0) {
            throw new RuntimeException("Invalid count");
        }
        else if(count > 0)
        {
            int newSize = this.size - count;
            for (int i = index; i < newSize; i++)
            {
                this.arr[i] = this.arr[i + count];
            }
            this.size = newSize;
        }

    }
    public T get(int index)
    {
        if(index < 0 || index >= this.getSize())
        {
            throw new RuntimeException("Index is not valid");
        }
        return this.arr[index];
    }
    public boolean isEmpty()
    {
        return this.size == 0;
    }
    @Override
    public String toString()
    {
        StringBuilder sBuild = new StringBuilder();
        for(int i = 0; i < this.size; ++i)
        {
            sBuild.append(this.arr[i] + " ");
        }
        return sBuild.toString();
    }
}


class Stack<T>
{
    private MyDynamicArray<T> stack;
    public Stack()
    {
        this.stack = new MyDynamicArray<>();
    }
    public void push(T val)
    {
        stack.pushBack(val);
    }
    public T pop()
    {
        return this.stack.popBack();
    }
    public T top()
    {
        return this.stack.get(this.stack.getSize() - 1);
    }
    public boolean isEmpty()
    {
        return this.stack.isEmpty();
    }
    public int size()
    {
        return this.stack.getSize();
    }
    @Override
    public String toString()
    {
        return this.stack.toString();
    }
}

class Queue<T>
{
    private MyDynamicArray<T> queue;
    public Queue()
    {
        this.queue = new MyDynamicArray<>();
    }
    public void push(T val)
    {
        this.queue.pushBack(val);
    }
    public T pop()
    {
        return this.queue.delete(0);
    }
    public T top()
    {
        return this.queue.get(0);
    }
    public boolean isEmpty()
    {
        return this.queue.isEmpty();
    }
    public int size()
    {
        return this.queue.getSize();
    }
    @Override
    public String toString()
    {
        return this.queue.toString();
    }
}

public class Main
{
    public static void main(String[] args)
    {

        MyDynamicArray<Integer> dynamicArray = new MyDynamicArray<>();
        dynamicArray.pushBack(1);
        dynamicArray.pushBack(2);
        dynamicArray.pushBack(3);
        System.out.println(dynamicArray);

        Stack<String> stack = new Stack<>();
        stack.push("A");
        stack.push("B");
        stack.push("C");
        System.out.println(stack.size());
        System.out.println(stack.top());
        System.out.println(stack.pop());
        System.out.println(stack);

        Queue<Double> queue = new Queue<>();
        queue.push(2.5);
        queue.push(4.0);
        queue.push(6.3);
        System.out.println(queue.size());
        System.out.println(queue.top());
        System.out.println(queue.pop());
        System.out.println(queue);
    }
}