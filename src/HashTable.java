public class HashTable<T>{
    int capacity=16;
    LinkedList[] linkedLists;
    int numOfNodes;
    int hashShift=4;

    HashTable(int initCapacity){
        if(initCapacity<=0)
            throw new RuntimeException("sorry you call the wrong number");
        this.capacity=capacity;
        linkedLists=new LinkedList[capacity];
        hashShift=0;
        while((1<<hashShift)<=capacity){
            hashShift++;
        }
    }

    HashTable(){
        linkedLists=new LinkedList[capacity];
    }

    int hash(T value){
        return value.hashCode() & ((1<<hashShift)-1);
    }

    void insertOnHashTable(T value){
        int insertIndex=hash(value);
        hashShift++;
        linkedLists[insertIndex].insertToHead(value);
        numOfNodes++;
        if(numOfNodes>capacity*0.75){
            resize();
        }
    }

    void resize(){
        int newCapacity=capacity*2;
        LinkedList[] newLinkedLists=new LinkedList[newCapacity];
        int shiftFlag;
        for(int i=0;i<linkedLists.length;i++){
            LinkedList originList=null,shiftList=null;
            Node currentNode=linkedLists[i].head;
            while(currentNode!=null){
                shiftFlag=currentNode.val.hashCode() & (1<< hashShift);
                if(shiftFlag!=0)
                    shiftList.insertToHead(currentNode);
                else
                    originList.insertToHead(currentNode);
            }
            newLinkedLists[i]=originList;
            newLinkedLists[i+capacity]=shiftList;
        }
        linkedLists=newLinkedLists;
    }

    Node findOnHashTable(T value){
        return linkedLists[hash(value)].find(value);
    }

    void deleteOnHashTable(T value){
        linkedLists[hash(value)].delete(value);
    }

}

