public class SplayTree {

    TreeNode leftRotate(TreeNode root){
        TreeNode right=root.right;
        root.right=right.left;
        right.left=root;
        return right;
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

}
