public class DataStructuresAndAlgorithm {

    int maxSubsequenceSum1(int[] src){
        int max=0;
        for(int i=0;i<src.length;i++){
            int tempMax=0;
            for(int j=i;j<src.length;j++){
                tempMax+=src[j];
                if(tempMax>max)
                    max=tempMax;
            }

        }
        return max;
    }

    int maxSubsequenceSum2(int[] src,int left,int right){
        if(left >= right)
            return src[right];
        int mid=(left+right)/2;
        int onlyLeft=maxSubsequenceSum2(src,left,mid);
        int onlyRight=maxSubsequenceSum2(src,mid+1,right);
        int partOfLeft=0,tempPartOfLeft=0,partOfRight=0,tempPartOfRigt=0;
        for(int i=mid;i>=left;i--){
            tempPartOfLeft+=src[i];
            if(tempPartOfLeft>partOfLeft)
                partOfLeft=tempPartOfLeft;
        }
        for(int j=mid+1;j<=right;j++){
            tempPartOfRigt+=src[j];
            if(tempPartOfRigt>partOfRight)
                partOfRight=tempPartOfRigt;
        }
        return maxInThree(onlyLeft,onlyRight,partOfLeft+partOfRight);
    }

    int maxSubsequenceSum3(int[] src){
        int max=0,tempMax=0;
        for(int i=0;i<src.length;i++){
            tempMax+=src[i];
            if(tempMax>max)
                max=tempMax;
            else if(tempMax<0)
                tempMax=0;
        }
        return max;
    }

    int maxInThree(int a, int b, int c){
        int max=0;
        if(a>b)
            max=a;
        else
            max=b;
        if(max<c)
            max=c;
        return max;
    }

    long pow(long x,int n){
        if(n==0)
            return 1;
        if(n==1)
            return x;
        if(n%2==0)
            return pow(x*x,n/2);
        else
            return pow(x*x,n/2)*x;
    }

    //LinkedList
    Node Find(Node head, int value){
        Node tempNode=head;
        while(tempNode!=null){
            if((Integer)tempNode.val==value)
                return tempNode;
            tempNode=tempNode.next;
        }
        return null;
    }

    Node Delete(Node head, int value){
        Node tempNode=head,previousNode=head;
        while(tempNode!=null){
            if((Integer)tempNode.val==value){
                if(previousNode.next==null)
                    head=null;
                previousNode.next=tempNode.next;
                break;
            }
            previousNode=tempNode;
            tempNode=tempNode.next;
        }
        return head;
    }

    Node Insert(Node head, Node previous, int value){
        Node newNode=new Node(value);
        if(previous!=null){
            newNode.next=previous.next;
            previous.next=newNode;
        }
        else{
            head=newNode;
        }
        return head;
    }

    //LinkedStack
    Node CreateStack(int value){
        Node head=new Node(value);
        return head;
    }

    Node Push(Node head, int value){
        Node newNode=new Node(value);
        newNode.next=head;
        return newNode;
    }

    //不适合用参数传递，因为返回值只能返回一个值,因此要适应面向对象的设计
    Node Pop(Node head){
        Node popeNode=head;
        head=head.next;
        return head;
    }

    int reversePolish(String expression){
        ArrayStack arrayStack=new ArrayStack();
        int result=0;
        for(int i=0;i<expression.length();i++){
            String c=expression.substring(i,i+1);
            try{
                Integer integer=Integer.parseInt(c);
                arrayStack.push(new Node(integer));
            }
            catch (Exception e) {
                try {
                    int temp=0;
                    if (c.equals("+")) {
                        Node one = arrayStack.pop();
                        Node two = arrayStack.pop();
                        temp= (Integer)one.val + (Integer)two.val;
                    } else if (c.equals("-")) {
                        Node one = arrayStack.pop();
                        Node two = arrayStack.pop();
                        temp= (Integer)one.val - (Integer)two.val;
                    } else if (c.equals("*")) {
                        Node one = arrayStack.pop();
                        Node two = arrayStack.pop();
                        temp= (Integer)one.val * (Integer)two.val;
                    } else if (c.equals("/")) {
                        Node one = arrayStack.pop();
                        Node two = arrayStack.pop();
                        temp= (Integer)one.val / (Integer)two.val;
                    }
                    arrayStack.push(new Node(temp));
                    result=temp;
                }
                catch (Exception ea){
                    ea.printStackTrace();
                }
            }
        }
        return result;
    }

    String postfixToInfix(String src){
        LinkedStack linkedStack=new LinkedStack();
        StringBuilder stringBuilder=new StringBuilder();
        Node tempNode=null;
        for(int i=0;i<src.length();i++){
            String c=src.substring(i,i+1);
            try{
            Integer integer=Integer.parseInt(c);
            stringBuilder.append(integer);
            }catch (Exception e){
                if(c.equals("+") || c.equals("-")){
                    while((tempNode=linkedStack.top())!=null){
                        String tempString=(String)tempNode.val;
                        if(tempString.equals("*") || tempString.equals("/") || tempString.equals("+") || tempString.equals("-")){
                            stringBuilder.append(tempString);
                            linkedStack.pop();
                        }else
                            break;
                    }
                    if(c.equals("+"))
                        linkedStack.push(new Node("+"));
                    else
                        linkedStack.push(new Node("-"));
                }
                else if(c.equals("*") || c.equals("/")){
                    while((tempNode=linkedStack.top())!=null){
                        String tempString=(String)tempNode.val;
                        if(tempString.equals("*") || tempString.equals("/")){
                            stringBuilder.append(tempString);
                            linkedStack.pop();
                        }else
                            break;
                    }
                    if(c.equals("*"))
                        linkedStack.push(new Node("*"));
                    else
                        linkedStack.push(new Node("/"));
                }
                else if(c.equals("(")){
                    linkedStack.push(new Node("("));
                }
                else if(c.equals(")")){
                    while((tempNode=linkedStack.pop())!=null){
                        String tempString=(String)tempNode.val;
                        if(tempString.equals("(")){
                            break;
                        }
                        stringBuilder.append(tempString);
                    }
                }
            }
        }
        while((tempNode=linkedStack.pop())!=null){
            stringBuilder.append(tempNode.val);
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args){
        DataStructuresAndAlgorithm d=new DataStructuresAndAlgorithm();
        //System.out.println(d.reversePolish(d.postfixToInfix("1+2*3")));
        ArrayQueue arrayQueue=new ArrayQueue(2);
        arrayQueue.enqueue(new Node(5));
        arrayQueue.enqueue(new Node(4));
        arrayQueue.enqueue(new Node(3));
        System.out.println(arrayQueue.dequeue().val);
        System.out.println(arrayQueue.dequeue().val);
        System.out.println(arrayQueue.dequeue().val);
    }

}


class Node<T>{
    Node next;
    T val;
    Node(T val){
        this.val=val;
    }

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
            newArray=null;
        }
        array[currentIndex++]=newHead;
        //System.out.println("push node "+newHead.val);
    }

    Node pop(){
        if(currentIndex==0)
            return null;
        Node popedNode=array[--currentIndex];
        //System.out.println("pop node "+popedNode.val);
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
            newArray=null;
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