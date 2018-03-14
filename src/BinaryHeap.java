public class BinaryHeap {

    private TreeNode root;

    private TreeNode[] nodes;

    private int size;

    BinaryHeap(){
        this(17);
    }

    BinaryHeap(int capacity){
        nodes=new TreeNode[capacity];
        nodes[0]=new TreeNode<>(Integer.MIN_VALUE);
        size=1;
    }

    public void insert(int value){
        if(size==nodes.length){
            TreeNode[] newNodes=new TreeNode[nodes.length*2];
            System.arraycopy(nodes,0,newNodes,0,nodes.length);
            nodes=newNodes;
        }
        //把低位的气泡往根方向推，可能终结在任意一层
        nodes[size]=new TreeNode<>(value);
        int i;
        for(i=size;(int)nodes[i/2].val>value;i/=2)
            nodes[i].val=nodes[i/2].val;
        nodes[i].val=value;
        size++;
    }

    //把最高位的气泡往树叶方向推，一定推到叶子层
    public TreeNode deleteMin(){
        if(size==1)
            return null;
        //nodes[1]会被修改，需要拷贝
        TreeNode minNode=new TreeNode<>(nodes[1].val);
        if(size==2){
            nodes[1]=null;
            return minNode;
        }
        int nextMinIndex=0;
        for(int i=1;i*2<size;i=nextMinIndex){
            if(i*2+1<size && (int)nodes[i*2].val>(int)nodes[i*2+1].val){
                nextMinIndex=i*2+1;
            }else
                nextMinIndex=i*2;
            nodes[i].val=nodes[nextMinIndex].val;
        }
        if(size>3)
            nodes[nextMinIndex].val=nodes[size-1].val;
        nodes[size-1]=null;
        size--;
        return minNode;
    }

    public static BinaryHeap buildHeap(int[] srcs){
        BinaryHeap binaryHeap=new BinaryHeap(srcs.length+1);
        for(int i=0;i<srcs.length;i++)
            binaryHeap.nodes[i+1]=new TreeNode<>(srcs[i]);
        binaryHeap.size=srcs.length+1;
        //倒数第二层的对后一个节点开始,这里无法完全包围所有情况，需要修正
        int sizeAlignment=alignment(binaryHeap.size);
        for(int j=sizeAlignment/2-1;j>0;j--){
            exchange(binaryHeap,j);
        }
        return binaryHeap;
    }

    private static int alignment(int n){
        if(n<=0)
            return 1;
        int i=0,t=0;
        while((t=DataStructuresAndAlgorithm.pow(2,i))<n)
            i++;
        return t;
    }


    private static void exchange(BinaryHeap binaryHeap,int j){
        int minIndex;
        while(j*2<binaryHeap.size){
            if(j*2+1<binaryHeap.size && (int)binaryHeap.nodes[j*2+1].val<(int)binaryHeap.nodes[j*2].val)
                minIndex=j*2+1;
            else
                minIndex=j*2;
            if((int)binaryHeap.nodes[minIndex].val<(int)binaryHeap.nodes[j].val){
                int temp=(int)binaryHeap.nodes[j].val;
                binaryHeap.nodes[j].val=binaryHeap.nodes[minIndex].val;
                binaryHeap.nodes[minIndex].val=temp;
                j=minIndex;
            }else
                break;
        }

    }


    public void printArray(){
        for(int i=1;i<size;i++){
            System.out.println(nodes[i].val+" ");
        }
    }


    public static void main(String[] args){
        /*BinaryHeap binaryHeap=new BinaryHeap();
        binaryHeap.insert(13);
        binaryHeap.insert(21);
        binaryHeap.insert(24);
        binaryHeap.insert(26);
        binaryHeap.insert(31);
        binaryHeap.insert(19);
        binaryHeap.insert(16);
        binaryHeap.insert(65);
        binaryHeap.insert(68);
        binaryHeap.insert(32);
        binaryHeap.printArray();
        System.out.println();
        binaryHeap.deleteMin();
        binaryHeap.printArray();*/
        int[] a=new int[]{150,80,40,30,10,70,110,100,20,90,60,50,120,140,130};
        BinaryHeap binaryHeap=BinaryHeap.buildHeap(a);
        binaryHeap.printArray();
    }
}
