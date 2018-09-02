import java.util.Stack;

public class Sort {

    private static void bubbleSort(int[] src, int left, int right) {
        // 外层循环的i用于标志循环的次数，与位置无关
        int count = right - left;
        for (int i = 0; i < count; i++) {
            // 内层循环每次都从头开始，选出最大的元素放到后面
            for (int j = left; j < right - i; j++) {
                if (src[j] > src[j + 1]) {
                    swap(src, j, j + 1);
                }
            }
        }
    }

    private static void selectionSort(int[] src, int left, int right) {
        int count = right - left;
        for (int i = 0; i < count; i++) {
            int max = Integer.MIN_VALUE;
            int maxIndex = -1;
            for (int j = left; j < right - i; j++) {
                if (src[j] > max) {
                    max = src[j];
                    maxIndex = j;
                }
            }
            swap(src, maxIndex, right - i);
        }
    }

    private static void insertSort(int[] src, int left, int right) {
        int temp, j;
        for (int i = left; i <= right; i++) {
            temp = src[i];
            for (j = i; j > left && src[j - 1] > temp; j--)
                src[j] = src[j - 1];
            src[j] = temp;
        }
    }

    private static void shellSort(int[] src) {
        int stride, i, j, temp;
        //Hibbard序列为1,3,7,...2^k-1
        //Sedgewick序列为4^k-3*2^k+1
        for (stride = src.length / 2; stride > 0; stride /= 2) {
            for (i = stride; i < src.length; i++) {
                //从i往后每stride处进行插入排序
                temp = src[i];
                for (j = i; j >= stride && src[j - stride] > temp; j -= stride) {
                    src[j - stride] = src[j];
                }
                src[j] = temp;
            }
        }
    }


    private static void heapSort(int[] src) {
        BinaryHeap binaryHeap = BinaryHeap.buildHeap(src);
        for (int i = 0; i < src.length; i++)
            src[i] = (int) binaryHeap.deleteMin().val;
    }

    private static void mergeSort(int[] src) {
        int[] temp = new int[src.length];
        //启动方法初始空间和边界，其他交给递归
        mergeRecursion(src, temp, 0, src.length - 1);
    }

    private static void mergeRecursion(int[] src, int[] temp, int left, int right) {
        //对left和right的处理是关键，是递归的传递因子
        if (left < right) {
            int mid = (left + right) / 2;
            mergeRecursion(src, temp, left, mid);
            mergeRecursion(src, temp, mid + 1, right);
            mergeArray(src, left, mid + 1, right, temp);
        }
    }

    private static void mergeArray(int[] src, int leftStart, int rightStart, int rightEnd, int[] temp) {
        int leftIndex = leftStart, rightIndex = rightStart, finalIndex = leftIndex;
        while (leftIndex < rightStart && rightIndex <= rightEnd) {
            if (src[leftIndex] < src[rightIndex])
                temp[finalIndex++] = src[leftIndex++];
            else
                temp[finalIndex++] = src[rightIndex++];
        }
        if (leftIndex == rightStart)
            System.arraycopy(src, rightIndex, temp, finalIndex, rightEnd - rightIndex + 1);
        else
            System.arraycopy(src, leftIndex, temp, finalIndex, rightStart - leftStart);
        System.arraycopy(temp, leftStart, src, leftStart, rightEnd - leftStart + 1);
    }

    public static void quickSort(int[] src) {
        quickSortRecursion(src, 0, src.length - 1);
    }

    private static void quickSortRecursion(int[] src, int left, int right) {
        if (left >= right)
            return;
        if (right - left < 10) {
            insertSort(src, left, right);
        } else {
            int pivot = swapMedian(src, left, right);
            int i = left;
            int j = right - 1;
            while (true) {
                while (src[++i] < pivot) ;
                while (src[--j] > pivot) ;
                if (i < j) {
                    swap(src, i, j);
                } else {
                    break;
                }
            }
            swap(src, i, right - 1);
            quickSortRecursion(src, left, i - 1);
            quickSortRecursion(src, i + 1, right);
        }
    }

    private static int swapMedian(int[] src, int left, int right) {
        int mid = (left + right) / 2;
        if (src[left] > src[mid]) {
            swap(src, left, mid);
        }
        if (src[left] > src[right]) {
            swap(src, left, right);
        }
        if (src[mid] > src[right]) {
            swap(src, mid, right);
        }
        swap(src, mid, right - 1);
        return src[right - 1];
    }

    private static void swap(int[] src, int left, int right) {
        int temp = src[right];
        src[right] = src[left];
        src[left] = temp;
    }

    private static void quickSortWithStack(int[] src) {
        if (src == null || src.length < 2)
            return;
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        stack.push(src.length - 1);
        while (!stack.isEmpty()) {
            int right = stack.pop();
            int left = stack.pop();
            int mid = quickSortPartition(src, left, right);
            if (mid - 1 > left) {
                stack.push(left);
                stack.push(mid - 1);
            }
            if (mid + 1 < right) {
                stack.push(mid + 1);
                stack.push(right);
            }
        }
    }

    private static int quickSortPartition(int[] src, int left, int right) {
        int pivot = swapMedian(src, left, right);
        int i = left;
        int j = right - 1;
        while (true) {
            while (src[++i] < pivot) ;
            while (src[--j] > pivot) ;
            if (i < j) {
                swap(src, i, j);
            } else {
                break;
            }
        }
        swap(src, i, right - 1);
        return i;
    }

    public static void main(String[] args) {
        int[] src = new int[]{2, 5, 1, 3, 3, 6, 4};
        bubbleSort(src, 0, src.length - 1);
        for (int i : src) {
            System.out.print(i + " ");
        }
    }


}
