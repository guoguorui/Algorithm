public class Queue {
}

class ArrayQueue{
    private Node[] array;
    private int currentCapacity=16;
    private int headIndex;
    private int tailIndex;

    ArrayQueue(){
        array=new Node[currentCapacity];
    }

    ArrayQueue(int initCapacity){
        currentCapacity=initCapacity;
        array=new Node[currentCapacity];
    }

    void enqueue(Node newTail){
        int nextIndex;
        if(array[tailIndex]==null)
            nextIndex=0;
        else
            nextIndex=tailIndex+1;
        if(nextIndex==currentCapacity)
            nextIndex=0;
        if(array[nextIndex]!=null){
            Node[] newArray;
            int newCapacity=currentCapacity*2;
            newArray=new Node[newCapacity];
            int i,j=0;
            for(i=headIndex;i<currentCapacity;i++) {
                newArray[j++]=array[i];
            }
            if(nextIndex!=0){
                for(i=0;i<=tailIndex;i++){
                    newArray[j++]=array[i];
                }
            }
            array=newArray;
            currentCapacity=newCapacity;
            nextIndex=j;
            headIndex=0;
        }
        array[nextIndex]=newTail;
        tailIndex=nextIndex;
    }

    Node dequeue(){
        if(array[headIndex]==null)
            return null;
        return array[headIndex++];
    }

}

class ListQueue{
    private Node head;
    private Node tail;

    ListQueue(){
        head=tail;
    }

    void enqueue(Node newTail){
        if(head==null && tail==null){
            head=tail=newTail;
            return;
        }
        tail.next=newTail;
        tail=newTail;
    }

    Node dequeue(){
        if(head==null)
            return null;
        Node oldHead=head;
        head=head.next;
        return oldHead;
    }
}
