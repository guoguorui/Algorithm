public class HashTable<K,V>{
    private int capacity=16;
    private HashLinkedList[] linkedLists;
    private int numOfNodes;
    private int hashShift=4;

    HashTable(int initCapacity){
        if(initCapacity<=0)
            throw new RuntimeException("sorry you call the wrong number");
        this.capacity=capacity;
        //linkedLists=new HashLinkedList[capacity];
        initLists(capacity);
        hashShift=0;
        while((1<<hashShift)<=capacity){
            hashShift++;
        }
    }

    HashTable(){
        //linkedLists=new HashLinkedList[capacity];
        initLists(capacity);
    }

    private void initLists(int capacity){
        linkedLists=new HashLinkedList[capacity];
        for(int i=0;i<capacity;i++)
            linkedLists[i]=new HashLinkedList();
    }

    private int hash(K key){
        return key.hashCode() & ((1<<hashShift)-1);
    }

    public void insertOnHashTable(K key,V value){
        int insertIndex=hash(key);
        linkedLists[insertIndex].insertToHead(key,value);
        numOfNodes++;
        if(numOfNodes>capacity*0.75){
            hashShift++;
            resize();
        }
    }

    private void resize(){
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

    public V findOnHashTable(K key){
        return (V)linkedLists[hash(key)].find(key);
    }

    public void deleteOnHashTable(K key){
        linkedLists[hash(key)].delete(key);
    }

    private class HashLinkedList<K,V>{

        HashNode head;

        V find(K key){
            HashNode tempNode=head;
            while(tempNode!=null){
                if(tempNode.key==key)
                    return (V)tempNode.value;
                tempNode=tempNode.next;
            }
            return null;
        }

        void delete(K key){
            HashNode tempNode=head,previousNode=head;
            while(tempNode!=null){
                if(tempNode.key==key){
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

    private class HashNode<K,V> {
        K key;
        V value;
        HashNode next;
        HashNode(K key,V value){
            this.key=key;
            this.value=value;
        }
    }


}








