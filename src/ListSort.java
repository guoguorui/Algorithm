public class ListSort {

    public ListNode mergeSort(ListNode head) {
        if(head==null || head.next==null)
            return head;
        ListNode midNode=getMid(head);
        ListNode rightNode=midNode.next;
        midNode.next=null;
        return merge(mergeSort(head), mergeSort(rightNode));
    }

    private ListNode merge(ListNode left,ListNode right){
        if(left==null)
            return right;
        if(right==null)
            return left;
        ListNode leftIndex=left,rightIndex=right;
        ListNode fakeHead=new ListNode(-1);
        ListNode currentHead=fakeHead;
        while(leftIndex!=null && rightIndex!=null){
            if(leftIndex.val<rightIndex.val){
                currentHead.next=leftIndex;
                leftIndex=leftIndex.next;
            }else{
                currentHead.next=rightIndex;
                rightIndex=rightIndex.next;
            }
            currentHead=currentHead.next;
        }
        currentHead.next=leftIndex==null?rightIndex:leftIndex;
        return fakeHead.next;
    }

    private ListNode getMid(ListNode head){
        if(head==null || head.next==null)
            return head;
        ListNode fastNode=head,slowNode=head;
        while(fastNode.next!=null && fastNode.next.next!=null){
            fastNode=fastNode.next.next;
            slowNode=slowNode.next;
        }
        return slowNode;
    }

}
