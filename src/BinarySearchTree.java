public class BinarySearchTree {

    private TreeNode root;

    //此返回类型不安全
    public TreeNode find(int value){
        return findOnSearchTree(root,value);
    }

    private TreeNode findOnSearchTree(TreeNode root,int value){
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

    public void insert(int value){
        root=insertOnSearchTree(root,value);
    }

    private TreeNode insertOnSearchTree(TreeNode root, int value){
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

    public void delete(int value){
        root=deleteOnSearchTree(root,value);
    }

    private TreeNode deleteOnSearchTree(TreeNode root,int value){
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
            else if((int) currentNode.val == value){
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
            else if(findNode.left==null){
                if(isLeft)
                    parentNode.left=findNode.right;
                else
                    parentNode.right=findNode.right;
            }else if(findNode.right==null){
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

    public String traverseInfixTree(){
        return traverseInfixTree(root,new StringBuilder()).toString();
    }

    private StringBuilder traverseInfixTree(TreeNode root,StringBuilder appendedTo){
        if(root==null)
            return appendedTo;
        traverseInfixTree(root.left,appendedTo);
        appendedTo.append(root.val);
        traverseInfixTree(root.right,appendedTo);
        return appendedTo;
    }
}
