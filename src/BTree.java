public class BTree {

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
        BTreeNode siblingNode=new BTreeNode<>((int)root.entries[3].value,root.currentheight);
        siblingNode.putNewEntry((int)root.entries[4].value);
        root.numOfEntry=2;
        siblingNode.numOfEntry=3;
        BTreeNode fatherRoot=root.father;
        if(fatherRoot==null) {
            fatherRoot=new BTreeNode<>((int)root.entries[2].value,root.currentheight+1);
            fatherRoot.entries[0].subNode=root;
            fatherRoot.entries[1].subNode=siblingNode;
            root.father=fatherRoot;
            siblingNode.father=fatherRoot;
        }else{
            fatherRoot=insertOnBTree(fatherRoot,(int)root.entries[2].value,false);
            fatherRoot.entries[fatherRoot.lastInsertIndex].subNode=siblingNode;
            siblingNode.father=fatherRoot;
        }
        return fatherRoot;
    }

    BTreeEntry findOnBTree(BTreeNode root, int value){
        if(root!=null){
            if(root.currentheight==0){
                for(int i=1;i<root.numOfEntry;i++){
                    if(root.entries[i].value.compareTo(value)==0)
                        return root.entries[i];
                }
            }else{
                BTreeEntry BTreeEntry;
                int insertPoint=root.findInsertPoint(value);
                if(root.entries[insertPoint].value.compareTo(value)==0)
                    return root.entries[insertPoint];
                BTreeEntry =findOnBTree(root.entries[insertPoint].subNode,value);
                return BTreeEntry;
            }
        }
        return null;
    }

    void merge(BTreeNode root){
        BTreeNode fatherRoot=root.father;
        if(fatherRoot!=null){
            for(int i=1;i<fatherRoot.numOfEntry-1;i++){
                if(fatherRoot.entries[i].subNode.equals(root)){
                    BTreeNode siblingNode=fatherRoot.entries[i+1].subNode;
                    if(siblingNode.numOfEntry>1){
                        root.entries[1].value=fatherRoot.entries[i].value;
                        fatherRoot.entries[i].value=fatherRoot.entries[i+1].value;
                        fatherRoot.entries[i+1].value=siblingNode.entries[1].value;
                        int j;
                        for(j=1;j<siblingNode.numOfEntry-1;j++)
                            siblingNode.entries[j]=siblingNode.entries[j+1];
                        siblingNode.entries[j]=null;
                        siblingNode.numOfEntry--;
                        root.numOfEntry++;
                    }
                }
                break;
            }
        }
    }

    void deleteOnBTree(BTreeNode root,int value,boolean forward){
        if(root==null)
            return;
        if(root.currentheight==0 || !forward){
            for(int i=1;i<root.numOfEntry;i++){
                if(root.entries[i].value.compareTo(value)==0){
                    for(int j=i;j<root.numOfEntry-1;j++)
                        root.entries[j]=root.entries[j+1];
                    root.numOfEntry--;
                    if(root.numOfEntry==1){
                        merge(root);
                    }
                    break;
                }
            }
        }else{
            BTreeEntry BTreeEntry;
            int deletePoint=root.findInsertPoint(value);
            if(root.entries[deletePoint].value.compareTo(value)==0){
                deleteOnBTree(root,value,false);
            }else{
                deleteOnBTree(root.entries[deletePoint].subNode,value,forward);
            }

        }
    }

    void deleteOnBTree(BTreeNode root,int value){
        deleteOnBTree(root,value,true);
    }

}

//example for 2-3 tree
//T need to addicted to Comparator
class BTreeNode<T extends Comparable<T>>{
    int splitThreshold=5;
    int currentheight;
    int numOfEntry;
    int lastInsertIndex;
    BTreeNode father;
    BTreeEntry[] entries=new BTreeEntry[splitThreshold];
    BTreeNode(T value,int currentheight){
        entries[0]=new BTreeEntry<>(Integer.MIN_VALUE,null);
        entries[1]=new BTreeEntry<>(value,null);
        numOfEntry=2;
        this.currentheight=currentheight;
    }
    void putNewEntry(T value){
        for(int i=0;i<numOfEntry;i++){
            if(entries[i].value.compareTo(value)!=-1){
                for(int j=numOfEntry-i+1;j>=i;j--){
                    entries[j+1]=entries[j];
                }
                entries[i]=new BTreeEntry<>(value,null);
                lastInsertIndex=i;
                numOfEntry++;
                return;
            }
        }
        entries[numOfEntry++]=new BTreeEntry<>(value,null);
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

class BTreeEntry<T extends Comparable<T>>{
    T value;
    BTreeNode subNode;
    BTreeEntry(T value, BTreeNode subNode){
        this.value=value;
        this.subNode=subNode;
    }
}
