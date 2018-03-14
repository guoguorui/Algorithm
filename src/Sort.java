public class Sort {

    public static int[] insertSort(int[] src){
        int temp,j;
        for(int i=0;i<src.length;i++){
            temp=src[i];
            for(j=i;j>0 && src[j-1]>temp;j--)
                src[j]=src[j-1];
            src[j]=temp;
        }
        return src;
    }

    public static int[] shellSort(int[] src){
        int stride,i,j,temp;
        //Hibbard序列为1,3,7,...2^k-1
        //Sedgewick序列为4^k-3*2^k+1
        for(stride=src.length/2;stride>0;stride/=2){
            for(i=stride;i<src.length;i++){
                //从i往后每stride处进行插入排序
                temp=src[i];
                for(j=i;j>=stride && src[j-stride]>temp;j-=stride){
                        src[j-stride]=src[j];
                }
                src[j]=temp;
            }
        }
        return src;
    }

    public static int[] heapSort(int[] src){
        BinaryHeap binaryHeap=BinaryHeap.buildHeap(src);
        for(int i=0;i<src.length;i++)
            src[i]=(int)binaryHeap.deleteMin().val;
        return src;
    }

    public static int[] mergeSort(int[] src){
        int[] temp=new int[src.length];
        //启动方法初始空间和边界，其他交给递归
        mergeRecursion(src,temp,0,src.length-1);
        return temp;
    }

    private static void mergeRecursion(int[] src,int[] temp,int left,int right){
        //对left和right的处理是关键，是递归的传递因子
        if(left<right){
            int mid=(left+right)/2;
            mergeRecursion(src,temp,left,mid);
            mergeRecursion(src,temp,mid+1,right);
            mergeArray(src,left,mid+1,right,temp);
        }
    }

    private static void mergeArray(int[] src,int leftStart,int rightStart,int rightEnd,int[] temp){
        int leftIndex=leftStart,rightIndex=rightStart,finalIndex=leftIndex;
        while(leftIndex<rightStart && rightIndex<=rightEnd){
            if(src[leftIndex]<src[rightIndex])
                temp[finalIndex++]=src[leftIndex++];
            else
                temp[finalIndex++]=src[rightIndex++];
        }
        if(leftIndex==rightStart)
            System.arraycopy(src,rightIndex,temp,finalIndex,rightEnd-rightIndex+1);
        else
            System.arraycopy(src,leftIndex,temp,finalIndex,rightStart-leftStart);
        System.arraycopy(temp,leftStart,src,leftStart,rightEnd-leftStart+1);
    }

    public static int[] quickSort(int[] src){
        quickSortRecursion(src,0,src.length-1);
        return src;
    }

    private static void quickSortRecursion(int[] src,int left,int right){
        if(left>=right)
            return;
        int smallerIndex=left+1,largerIndex=right-1;
        int mid=(left+right)/2;
        swapMedian(src,left,mid,right);
        while(smallerIndex<largerIndex){
            while(src[smallerIndex]<src[right])
                smallerIndex++;
            while(src[largerIndex]>src[right])
                largerIndex--;
            if(smallerIndex<largerIndex)
                swap(src,smallerIndex,largerIndex);
            else
                break;
            smallerIndex++;
            largerIndex--;
        }
        if(src[largerIndex]<src[right])
            largerIndex++;
        swap(src,largerIndex,right);
        quickSortRecursion(src,left,largerIndex-1);
        quickSortRecursion(src,largerIndex+1,right);
    }

    private static void swapMedian(int[] src,int left,int mid,int right){
        int temp;
        if(src[left]>src[mid]){
            swap(src,left,mid);
        }
        if(src[left]>src[right]){
            swap(src,left,right);
        }
        if(src[mid]>src[right]){
            swap(src,mid,right);
        }
        swap(src,mid,right);
    }

    private static void swap(int[] src,int left,int right){
        int temp=src[right];
        src[right]=src[left];
        src[left]=temp;
    }


    public static void main(String[] args){
        int[] src=new int[]{2,46,13,23};
        int[] temp=quickSort(src);
        for(int i=0;i<temp.length;i++)
            System.out.print(temp[i]+" ");
    }


}
