public class LeftHeap {

    private LeftHeapNode root;

    public LeftHeapNode merge(LeftHeapNode leftRoot,LeftHeapNode rightRoot){
        if(leftRoot==null)
            return rightRoot;
        if(rightRoot==null)
            return leftRoot;
        if((int)leftRoot.val<(int)rightRoot.val)
            return mergeOnLeftHeap(leftRoot,rightRoot);
        else
            return mergeOnLeftHeap(rightRoot,leftRoot);
    }

    private LeftHeapNode mergeOnLeftHeap(LeftHeapNode leftRoot,LeftHeapNode rightRoot){
        if(leftRoot.left==null)
            leftRoot.left=rightRoot;
        else{
            //只有右子节点被更新时才需要更新npl，否则一路只有左节点就都为0
            leftRoot.right=merge(leftRoot.right,rightRoot);
            if(leftRoot.left.npl<leftRoot.right.npl){
                LeftHeapNode tempNode=leftRoot.left;
                leftRoot.left=leftRoot.right;
                leftRoot.right=tempNode;
            }
            leftRoot.npl=leftRoot.right.npl+1;
        }
        return leftRoot;
    }

    public void insert(int value){
        if(root==null){
            root=new LeftHeapNode<>(value);
            return;
        }
        root=merge(root,new LeftHeapNode<>(value));
    }

    public LeftHeapNode deleteMin(){
        LeftHeapNode minNode=root;
        root=merge(root.left,root.right);
        return minNode;
    }

    class LeftHeapNode<T>{
        private int npl=0;
        private T val;
        private LeftHeapNode left;
        private LeftHeapNode right;
        LeftHeapNode(T value){
            this.val=value;
        }

    }

    public static void main(String[] args){
        LeftHeap leftHeap=new LeftHeap();
        leftHeap.insert(2);
        leftHeap.insert(3);
        leftHeap.insert(1);
        leftHeap.insert(4);
    }

}


