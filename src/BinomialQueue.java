public class BinomialQueue {

    private TreeNode[] trees;

    BinomialQueue(){
        this(16);
    }

    BinomialQueue(int capacity){
        trees=new TreeNode[capacity];
    }

    public void insert(int value){
        TreeNode newNode=new TreeNode<>(value);
        conflictMerge(newNode,0);
    }


    //forward用于判断是否该位置的树由前个位置推上来的，如果是就去除前一棵树
    private void conflictMerge(TreeNode tree,int i,boolean isForwared){
        conflictMerge(tree,i);
        if(isForwared)
            trees[i-1]=null;
    }

    //清空遗留的树
    private void conflictMerge(TreeNode tree,int i){
        if(i>=trees.length){
            TreeNode[] newTrees=new TreeNode[trees.length*2];
            System.arraycopy(trees,0,newTrees,0,trees.length);
            trees=newTrees;
        }
        if(trees[i]==null)
            trees[i]=tree;
        else{
            //将所有儿子节点分为两种，体量最大的作为root的左子节点，其他儿子左右左子节点的右节点
            if((int)trees[i].val<(int)tree.val){
                TreeNode oldLeft=trees[i].left;
                trees[i].left=tree;
                tree.right=oldLeft;
                conflictMerge(trees[i],i+1,true);
            }else{
                TreeNode oldLeft=tree.left;
                tree.left=trees[i];
                trees[i].right=oldLeft;
                conflictMerge(tree,i+1,true);
            }
        }
    }

    public static BinomialQueue merge(BinomialQueue one,BinomialQueue two){
        boolean oneIsLonger=false;
        if(one.trees.length>two.trees.length)
            oneIsLonger=true;
        if(oneIsLonger){
            for(int i=0;i<two.trees.length;i++)
                one.conflictMerge(two.trees[i],i,false);
            return one;
        }else{
            for(int i=0;i<one.trees.length;i++)
                two.conflictMerge(one.trees[i],i,false);
        }
        return one;
    }

    public TreeNode deleteMin(){
        int minValue=Integer.MAX_VALUE,minIndex=-1;
        for(int i=0;i<trees.length;i++){
            if(trees[i]==null)
                continue;
            if((int)trees[i].val<minValue){
                minIndex=i;
                minValue=(int)trees[i].val;
            }
        }
        TreeNode subNode=trees[minIndex].left;
        while(subNode!=null){
            int leftLength=0;
            TreeNode tempNode=subNode;
            while(tempNode.left!=null){
                leftLength++;
                tempNode=tempNode.left;
            }
            conflictMerge(subNode,leftLength,false);
            subNode=subNode.right;
        }
        return trees[minIndex];
    }

}
