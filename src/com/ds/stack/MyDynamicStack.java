package com.ds.stack;

public class MyDynamicStack extends MyStackUsingArray {
    // We can use LinkedList or ArrayList

    public MyDynamicStack() throws Exception {
        this(DEFAULT_CAPACITY);
    }

    public MyDynamicStack(int capacity) throws Exception {
        super(capacity);
    }

    public void push(int element) throws Exception {
        if (size() == data.length) {
            int[] arr = new int[2 * this.data.length];
            if (this.size() >= 0) System.arraycopy(this.data, 0, arr, 0, this.size());
            this.data = arr;
        }
        super.push(element);
    }

    public int pop() throws Exception {
        return super.pop();
    }

    public int peek() throws Exception {
        return super.peek();
    }


    public void display() {
        super.display();
    }

    public boolean isEmpty() {
        return super.isEmpty();
    }

}
