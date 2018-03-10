public class Stack {
}

class ArrayStack{

    private Node[] array;
    private int currentIndex;
    private int currentCapacity=16;

    ArrayStack(){
        array=new Node[currentCapacity];
    }

    ArrayStack(int capacity){
        currentCapacity=capacity;
        array=new Node[currentCapacity];
    }

    void push(Node newHead) throws Exception{
        if(currentIndex==currentCapacity){
            Node[] newArray;
            currentCapacity*=2;
            if(currentCapacity>Integer.MAX_VALUE)
                throw new Exception("Capacity is too large");
            newArray=new Node[currentCapacity];
            for(int i=0;i<currentIndex;i++){
                newArray[i]=array[i];
            }
            array=newArray;
        }
        array[currentIndex++]=newHead;
        System.out.println("push node "+newHead.val);
    }

    Node pop(){
        if(currentIndex==0)
            return null;
        Node popedNode=array[--currentIndex];
        System.out.println("pop node "+popedNode.val);
        return popedNode;
    }

    Node top(){
        if(currentIndex==0)
            return null;
        return array[currentIndex-1];
    }

}

class LinkedStack {
    private Node head;

    void push(Node newHead){
        newHead.next=head;
        head=newHead;
        //System.out.println("push "+newHead.val);
    }

    Node pop(){
        if(head==null)
            return null;
        Node oldNode=head;
        head = head.next;
        //System.out.println("pop "+oldNode.val);
        return oldNode;
    }

    Node top(){
        return head;
    }
}


