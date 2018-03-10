public class HashTable<K,V>{
    int capacity=16;
    HashLinkedList[] linkedLists;
    int numOfNodes;
    int hashShift=4;

    HashTable(int initCapacity){
        if(initCapacity<=0)
            throw new RuntimeException("sorry you call the wrong number");
        this.capacity=capacity;
        linkedLists=new HashLinkedList[capacity];
        hashShift=0;
        while((1<<hashShift)<=capacity){
            hashShift++;
        }
    }

    HashTable(){
        linkedLists=new HashLinkedList[capacity];
    }

    int hash(K key){
        return key.hashCode() & ((1<<hashShift)-1);
    }

    void insertOnHashTable(K key,V value){
        int insertIndex=hash(key);
        hashShift++;
        linkedLists[insertIndex].insertToHead(key,value);
        numOfNodes++;
        if(numOfNodes>capacity*0.75){
            resize();
        }
    }

    void resize(){
        int newCapacity=capacity*2;
        HashLinkedList[] newLinkedLists=new HashLinkedList[newCapacity];
        int shiftFlag;
        for(int i=0;i<linkedLists.length;i++){
            HashLinkedList originList=null,shiftList=null;
            HashNode currentNode=linkedLists[i].head;
            while(currentNode!=null){
                shiftFlag=currentNode.key.hashCode() & (1<< hashShift);
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

    V findOnHashTable(K key){
        return (V)linkedLists[hash(key)].find(key);
    }

    void deleteOnHashTable(K key){
        linkedLists[hash(key)].delete(key);
    }

}

class HashNode<K,V> {
    K key;
    V value;
    HashNode next;
    HashNode(K key,V value){
        this.key=key;
        this.value=value;
    }
}

class HashLinkedList<K,V>{

    HashNode head;

    V find(K key){
        HashNode tempNode=head;
        while(tempNode!=null){
            if((Integer)tempNode.key==key)
                return (V)tempNode.value;
            tempNode=tempNode.next;
        }
        return null;
    }

    void delete(K key){
        HashNode tempNode=head,previousNode=head;
        while(tempNode!=null){
            if((Integer)tempNode.key==key){
                if(previousNode.next==null)
                    head=null;
                previousNode.next=tempNode.next;
                break;
            }
            previousNode=tempNode;
            tempNode=tempNode.next;
        }
    }

    void insert(HashNode previous, K key,V value){
        HashNode newNode=new HashNode<>(key,value);
        if(previous!=null){
            newNode.next=previous.next;
            previous.next=newNode;
        }
        else{
            head=newNode;
        }
    }

    void insertToHead(K key,V value){
        if(head==null)
            head=new HashNode<>(key,value);
        else{
            HashNode newHead=new HashNode<>(key,value);
            newHead.next=head;
            head=newHead;
        }
    }

    void insertToHead(HashNode node){
        if(head==null)
            head=node;
        else{
            node.next=head;
            head=node;
        }
    }

}





