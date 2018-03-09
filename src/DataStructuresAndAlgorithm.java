public class DataStructuresAndAlgorithm {

    private int maxKeyNum =3;

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

    /*Node findOnLinkedList(Node head, int value){
        Node tempNode=head;
        while(tempNode!=null){
            if((Integer)tempNode.val==value)
                return tempNode;
            tempNode=tempNode.next;
        }
        return null;
    }

    Node deleteOnLinkedList(Node head, int value){
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

    Node insertOnLinkedList(Node head, Node previous, int value){
        Node newNode=new Node<>(value);
        if(previous!=null){
            newNode.next=previous.next;
            previous.next=newNode;
        }
        else{
            head=newNode;
        }
        return head;
    }*/



    int reversePolish(String expression){
        ArrayStack arrayStack=new ArrayStack();
        int result=0;
        for(int i=0;i<expression.length();i++){
            String c=expression.substring(i,i+1);
            try{
                Integer integer=Integer.parseInt(c);
                arrayStack.push(new Node<>(integer));
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
                    arrayStack.push(new Node<>(temp));
                    result=temp;
                }
                catch (Exception ea){
                    ea.printStackTrace();
                }
            }
        }
        return result;
    }

    String infixToPostfix(String src){
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
                        linkedStack.push(new Node<>("+"));
                    else
                        linkedStack.push(new Node<>("-"));
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
                        linkedStack.push(new Node<>("*"));
                    else
                        linkedStack.push(new Node<>("/"));
                }
                else if(c.equals("(")){
                    linkedStack.push(new Node<>("("));
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


    //类似于逆波兰
    TreeNode postfixToInfixTree(String src){
        ArrayStack arrayStack=new ArrayStack();
        for(int i=0;i<src.length();i++){
            String c=src.substring(i,i+1);
            try {
                Integer integer=Integer.parseInt(c);
                arrayStack.push(new TreeNode<>(c));
            } catch (Exception e) {
                TreeNode root=new TreeNode<>(c);
                root.right=(TreeNode) arrayStack.pop();
                root.left=(TreeNode) arrayStack.pop();
                try {
                    arrayStack.push(root);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
        return (TreeNode)arrayStack.pop();
    }

    StringBuilder traverseInfixTree(TreeNode root,StringBuilder appendedTo){
        if(root==null)
            return appendedTo;
        traverseInfixTree(root.left,appendedTo);
        appendedTo.append(root.val);
        traverseInfixTree(root.right,appendedTo);
        return appendedTo;
    }

    TreeNode findOnSearchTree(TreeNode root,int value){
        TreeNode currentNode=root;
        while(currentNode!=null){
            if((int) currentNode.val > value)
                currentNode=currentNode.left;
            else if((int) currentNode.val < value)
                currentNode=currentNode.right;
            else
                return currentNode;
        }
        return null;
    }

    TreeNode insertOnSearchTree(TreeNode root, int value){
        TreeNode currentNode=root;
        if(currentNode==null)
            root=new TreeNode<>(value);
        else{
            //只有修改left或right才会生效
            while(currentNode!=null) {
                if ((int) currentNode.val > value) {
                    if (currentNode.left == null)
                        currentNode.left = new TreeNode<>(value);
                    else
                        currentNode=currentNode.left;
                } else if ((int) currentNode.val < value) {
                    if (currentNode.right == null)
                        currentNode.right = new TreeNode<>(value);
                    else
                        currentNode=currentNode.right;
                }else if((int) currentNode.val == value)
                    break;
            }
        }
        return root;
    }

    TreeNode deleteOnSearchTree(TreeNode root,int value){
        TreeNode currentNode=root;
        TreeNode findNode=null;
        TreeNode parentNode=null;
        boolean isLeft=false;
        while(currentNode!=null){
            if((int) currentNode.val > value){
                parentNode=currentNode;
                isLeft=true;
                currentNode=currentNode.left;
            }
            else if((int) currentNode.val < value){
                parentNode=currentNode;
                isLeft=false;
                currentNode=currentNode.right;
            }
            else{
                findNode=currentNode;
                break;
            }
        }
        if(findNode!=null){
            if(findNode.left==null && findNode.right==null){
                if(isLeft)
                    parentNode.left=null;
                else
                    parentNode.right=null;
            }
            else if(findNode.left==null && findNode.right!=null){
                if(isLeft)
                    parentNode.left=findNode.right;
                else
                    parentNode.right=findNode.right;
            }else if(findNode.left!=null && findNode.right==null){
                if(isLeft)
                    parentNode.left=findNode.left;
                else
                    parentNode.right=findNode.left;
            }else{
                currentNode=findNode.right;
                TreeNode minNode=null;
                TreeNode minParentNode=null;
                while(currentNode.left!=null){
                    minParentNode=currentNode;
                    currentNode=currentNode.left;
                }
                minNode=currentNode;
                deleteOnSearchTree(minParentNode,(int)minNode.val);
                findNode.val=minNode.val;
            }
        }
        return root;
    }

    AvlTreeNode insertOnAvlTree(AvlTreeNode root,int value){
        if(root==null){
            root=new AvlTreeNode<>(value);
        }
        else if((int)root.val>value){
            root.left=insertOnAvlTree((AvlTreeNode) root.left,value);
            int leftHeight=getHeight((AvlTreeNode)root.left);
            int rightHeight=getHeight((AvlTreeNode)root.right);
            if(leftHeight-rightHeight==2){
                if((int)((AvlTreeNode) root.left).val>value){
                    root= rightRotateOnAvlTree(root);
                }
                else{
                    root.left= leftRotateOnAvlTree((AvlTreeNode)root.left);
                    root= rightRotateOnAvlTree(root);
                }
            }

        }else if((int)root.val<value) {
            root.right=insertOnAvlTree((AvlTreeNode) root.right,value);
            int leftHeight=getHeight((AvlTreeNode)root.left);
            int rightHeight=getHeight((AvlTreeNode)root.right);
            if(rightHeight-leftHeight==2){
                if((int)((AvlTreeNode) root.right).val<value){
                    root= leftRotateOnAvlTree(root);
                }
                else{
                    root.left= rightRotateOnAvlTree((AvlTreeNode)root.left);
                    root= leftRotateOnAvlTree(root);
                }
            }
        }
        root.height= maxSonHeight(root)+1;
        return root;
    }

    int getHeight(AvlTreeNode root){
        if(root==null)
            return -1;
        else
            return root.height;
    }

    int maxSonHeight(AvlTreeNode root){
        int leftHeight=getHeight((AvlTreeNode)root.left);
        int rightHeight=getHeight((AvlTreeNode)root.right);
        return Math.max(leftHeight,rightHeight);
    }

    AvlTreeNode leftRotateOnAvlTree(AvlTreeNode root){
        AvlTreeNode right=(AvlTreeNode) root.right;
        root.right=right.left;
        right.left=root;
        right.height=maxSonHeight(right)+1;
        root.height=maxSonHeight(root)+1;
        return right;
    }

    TreeNode leftRotate(TreeNode root){
        TreeNode right=root.right;
        root.right=right.left;
        right.left=root;
        return right;
    }

    AvlTreeNode rightRotateOnAvlTree(AvlTreeNode root){
        AvlTreeNode left=(AvlTreeNode)root.left;
        root.left=left.right;
        left.right=root;
        left.height= maxSonHeight(left)+1;
        root.height= maxSonHeight(root)+1;
        return left;
    }

    TreeNode rightRotate(TreeNode root){
        TreeNode left=root.left;
        root.left=left.right;
        left.right=root;
        return left;
    }


    TreeNode findOnSplayTree(TreeNode root,int value){
        TreeNode currentNode=root;
        //when direction is 1, meaning that the son is the left of father, need to rightRotate
        ArrayStack arrayStack=new ArrayStack();
        while(currentNode!=null){
            try {
                if((int)currentNode.val>value){
                    arrayStack.push(currentNode);
                    arrayStack.push(new Node<>(1));
                    currentNode=currentNode.left;
                }else if((int)currentNode.val<value){
                    arrayStack.push(currentNode);
                    arrayStack.push(new Node<>(2));
                    currentNode=currentNode.right;
                }else{
                    arrayStack.push(currentNode);
                    arrayStack.push(new Node<>(0));
                    TreeNode currentRoot=currentNode;
                    TreeNode fatherRoot=null;
                    int fatherDirection=0;
                    int direction=0;
                    while(arrayStack.top()!=null){
                        if(fatherRoot==null){
                            direction=(int)arrayStack.pop().val;
                            currentRoot=(TreeNode)arrayStack.pop();
                        }else{
                            direction=fatherDirection;
                            currentRoot=fatherRoot;
                        }
                        if(arrayStack.top()!=null){
                            fatherDirection=(int)arrayStack.pop().val;
                            fatherRoot=(TreeNode)arrayStack.pop();
                        }
                        if(direction==1){
                            if(fatherRoot!=null){
                                if(fatherDirection==1){
                                    fatherRoot.left=rightRotate(currentRoot);
                                    currentRoot=fatherRoot.left;
                                }else if(fatherDirection==2){
                                    fatherRoot.right=rightRotate(currentRoot);
                                    currentRoot=fatherRoot.right;
                                }
                            }else
                                currentRoot=rightRotate(currentRoot);
                        }
                        else if(direction==2){
                            if(fatherRoot!=null){
                                if(fatherDirection==1){
                                    fatherRoot.left=leftRotate(currentRoot);
                                    currentRoot=fatherRoot.left;
                                }else if(fatherDirection==2){
                                    fatherRoot.right=leftRotate(currentRoot);
                                    currentRoot=fatherRoot.right;
                                }
                            }else
                                currentRoot=leftRotate(currentRoot);
                        }
                    }
                    if(fatherDirection==1)
                        currentRoot=rightRotate(fatherRoot);
                    else
                        currentRoot=leftRotate(fatherRoot);
                    return currentRoot;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return root;
    }

    BTreeNode insertOnBTree(BTreeNode root,int value,boolean forward){
        if(root==null)
            return new BTreeNode<>(value,0);
        //forward用于避免父传子，子传父
        if(root.currentheight==0 || !forward){
            root.putNewEntry(value);
            if(root.numOfEntry==root.splitThreshold){
                root=splitBTree(root);
            }
            if(root.father!=null)
                root=root.father;
        }else{
            int insertPoint=root.findInsertPoint(value);
            root=insertOnBTree(root.entries[insertPoint].subNode,value);
        }
       return root;
    }

    BTreeNode insertOnBTree(BTreeNode root,int value){
        return insertOnBTree(root,value,true);
    }

    BTreeNode splitBTree(BTreeNode root){
        BTreeNode fatherRoot=root.father;
        if(fatherRoot==null) {
            fatherRoot=new BTreeNode<>((int)root.entries[2].value,root.currentheight+1);
            BTreeNode siblingNode=new BTreeNode<>((int)root.entries[3].value,root.currentheight);
            siblingNode.putNewEntry((int)root.entries[4].value);
            root.numOfEntry=2;
            siblingNode.numOfEntry=3;
            fatherRoot.entries[0].subNode=root;
            fatherRoot.entries[1].subNode=siblingNode;
            root.father=fatherRoot;
            siblingNode.father=fatherRoot;
            root.siblings.insertToHead(siblingNode);
            siblingNode.siblings.insertToHead(root);
        }else{
            fatherRoot=insertOnBTree(fatherRoot,(int)root.entries[2].value,false);
            BTreeNode siblingNode=new BTreeNode<>((int)root.entries[3].value,root.currentheight);
            siblingNode.putNewEntry((int)root.entries[4].value);
            root.numOfEntry=2;
            siblingNode.numOfEntry=3;
            fatherRoot.entries[fatherRoot.lastInsertIndex].subNode=siblingNode;
            siblingNode.father=fatherRoot;
            root.siblings.insertToHead(siblingNode);
            siblingNode.siblings.insertToHead(root);
        }
        return fatherRoot;
    }


    public static void main(String[] args){
        DataStructuresAndAlgorithm d=new DataStructuresAndAlgorithm();
        BTreeNode bTreeNode=d.insertOnBTree(null,1);
        bTreeNode=d.insertOnBTree(bTreeNode,2);
        bTreeNode=d.insertOnBTree(bTreeNode,3);
        bTreeNode=d.insertOnBTree(bTreeNode,4);
        bTreeNode=d.insertOnBTree(bTreeNode,5);
        bTreeNode=d.insertOnBTree(bTreeNode,6);

    }

}

class LinkedList<T>{

    Node head;

    Node find(T value){
        Node tempNode=head;
        while(tempNode!=null){
            if((Integer)tempNode.val==value)
                return tempNode;
            tempNode=tempNode.next;
        }
        return null;
    }

    void delete(T value){
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
    }

    void insert(Node previous, T value){
        Node newNode=new Node<>(value);
        if(previous!=null){
            newNode.next=previous.next;
            previous.next=newNode;
        }
        else{
            head=newNode;
        }
    }

    void insertToHead(T value){
        if(head==null)
            head=new Node<>(value);
        else{
            Node newHead=new Node<>(value);
            newHead.next=head;
            head=newHead;
        }
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


class Node<T>{
    Node next;
    T val;
    Node(T val){
        this.val=val;
    }

}

class TreeNode<T> extends Node<T>{
    //子类域声明与父相同会有覆盖行为
    TreeNode<T> left;
    TreeNode<T> right;
    TreeNode(T value){
        super(value);
    }

}

class AvlTreeNode<T> extends TreeNode<T>{
    int height;
    AvlTreeNode(T value){super(value);}

}


//example for 2-3 tree
//T need to addicted to Comparator
class BTreeNode<T extends Comparable<T>>{
    int splitThreshold=5;
    int currentheight;
    int numOfEntry;
    int lastInsertIndex;
    BTreeNode father;
    LinkedList<BTreeNode> siblings=new LinkedList<>();
    Entry[] entries=new Entry[splitThreshold];
    BTreeNode(T value,int currentheight){
        entries[0]=new Entry<>(Integer.MIN_VALUE,null);
        entries[1]=new Entry<>(value,null);
        numOfEntry=2;
        this.currentheight=currentheight;
    }
    void putNewEntry(T value){
        for(int i=0;i<numOfEntry;i++){
            if(entries[i].value.compareTo(value)!=-1){
                for(int j=numOfEntry-i+1;j>=i;j--){
                    entries[j+1]=entries[j];
                }
                entries[i]=new Entry<>(value,null);
                lastInsertIndex=i;
                numOfEntry++;
                return;
            }
        }
        entries[numOfEntry++]=new Entry<>(value,null);
        lastInsertIndex=numOfEntry-1;
    }

    int findInsertPoint(T value){
        int i;
        for(i=0;i<numOfEntry;i++){
            if(entries[i].value.compareTo(value)!=-1){
                return i-1;
            }
        }
        return i-1;
    }
}

class Entry<T extends Comparable<T>>{
    T value;
    BTreeNode subNode;
    Entry(T value,BTreeNode subNode){
        this.value=value;
        this.subNode=subNode;
    }
}