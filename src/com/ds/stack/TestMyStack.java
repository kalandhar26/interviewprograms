package com.ds.stack;

public class TestMyStack {
    public static void main(String[] args) throws Exception {
        MyStackUsingArray stack = new MyStackUsingArray();

        System.out.println(stack.size());
        System.out.println(stack.isEmpty());
        stack.push(1);
        stack.push(4);
        stack.push(7);
        stack.push(9);
        stack.push(8);
        stack.display();
        System.out.println(stack.size());
        stack.pop();
        stack.pop();
        stack.display();
        System.out.println(stack.peek());
        System.out.println(stack.isEmpty());

        MyDynamicStack stack1 = new MyDynamicStack();

        System.out.println(stack1.size());
        System.out.println(stack1.isEmpty());
        stack1.push(1);
        stack1.push(4);
        stack1.push(7);
        stack1.push(9);
        stack1.push(8);
        stack1.display();
        System.out.println(stack1.size());
        stack1.pop();
        stack1.pop();
        stack1.display();
        System.out.println(stack1.peek());
        System.out.println(stack1.isEmpty());

        DynamicStackWithArrayList stack2 = new DynamicStackWithArrayList();

        System.out.println(stack2.size());
        System.out.println(stack2.isEmpty());
        stack2.push(1);
        stack2.push(4);
        stack2.push(7);
        stack2.push(9);
        stack2.push(8);
        stack2.display();
        System.out.println(stack2.size());
        stack2.pop();
        stack2.pop();
        stack2.display();
        System.out.println(stack2.peek());
        System.out.println(stack2.isEmpty());

        DynamicStackWithLinkedList stack3 = new DynamicStackWithLinkedList();

        System.out.println(stack3.size());
        System.out.println(stack3.isEmpty());
        stack3.push(1);
        stack3.push(4);
        stack3.push(7);
        stack3.push(9);
        stack3.push(8);
        stack3.display();
        System.out.println(stack3.size());
        stack3.pop();
        stack3.pop();
        stack3.display();
        System.out.println(stack3.peek());
        System.out.println(stack3.isEmpty());

        DynamicStackWithMyLinkedList stack4 = new DynamicStackWithMyLinkedList();
        System.out.println(stack4.size());
        System.out.println(stack4.isEmpty());
        stack4.push(1);
        stack4.push(4);
        stack4.push(7);
        stack4.push(9);
        stack4.push(8);
        stack4.display();
        System.out.println(stack4.size());
        stack4.pop();
        stack4.pop();
        stack4.display();
        System.out.println(stack4.peek());
        System.out.println(stack4.isEmpty());
    }
}
