public class AvlTree {

    private AvlTreeNode root;

    public void insert(int value){
        root=insertOnAvlTree(root,value);
    }

    private AvlTreeNode insertOnAvlTree(AvlTreeNode root,int value){
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

    private int getHeight(AvlTreeNode root){
        if(root==null)
            return -1;
        else
            return root.height;
    }

    private int maxSonHeight(AvlTreeNode root){
        int leftHeight=getHeight((AvlTreeNode)root.left);
        int rightHeight=getHeight((AvlTreeNode)root.right);
        return Math.max(leftHeight,rightHeight);
    }

    private AvlTreeNode leftRotateOnAvlTree(AvlTreeNode root){
        AvlTreeNode right=(AvlTreeNode) root.right;
        root.right=right.left;
        right.left=root;
        right.height=maxSonHeight(right)+1;
        root.height=maxSonHeight(root)+1;
        return right;
    }



    private AvlTreeNode rightRotateOnAvlTree(AvlTreeNode root){
        AvlTreeNode left=(AvlTreeNode)root.left;
        root.left=left.right;
        left.right=root;
        left.height= maxSonHeight(left)+1;
        root.height= maxSonHeight(root)+1;
        return left;
    }

    private class AvlTreeNode<T> extends TreeNode<T>{
        int height;
        AvlTreeNode(T value){super(value);}
    }
}

