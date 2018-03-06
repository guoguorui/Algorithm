import java.util.ArrayList;

public class NewCoder {

    public boolean FindInArray(int target, int [][] array) {
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array[i].length;j++){
                if(array[i][j]==target)
                    return true;
            }
        }
        return false;
    }

    public String replaceSpace(StringBuffer str) {
        return str.toString().replaceAll(" ","%20");
    }

    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> values=new ArrayList<Integer>();
        ListNode first=listNode;
        ListNode currentLast;
        if(first==null){
            return values;
        }
        while(listNode.next!=null){
            listNode=listNode.next;
        }
        currentLast=listNode;
        values.add(currentLast.val);
        while(true){
            listNode=first;
            if(currentLast.equals(first))
                break;
            while(listNode.next!=currentLast){
                listNode=listNode.next;
            }
            currentLast=listNode;
            values.add(currentLast.val);
        }
        return values;
    }

}

class ListNode {
    int val;
    ListNode next = null;
    ListNode(int val) {
        this.val = val;
    }
}
