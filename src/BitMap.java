public class BitMap {

    private int[] bits;
    private int length;
    private static final int[] BIT_VALUE = { 0x00000001, 0x00000002, 0x00000004, 0x00000008, 0x00000010, 0x00000020,
            0x00000040, 0x00000080, 0x00000100, 0x00000200, 0x00000400, 0x00000800, 0x00001000, 0x00002000, 0x00004000,
            0x00008000, 0x00010000, 0x00020000, 0x00040000, 0x00080000, 0x00100000, 0x00200000, 0x00400000, 0x00800000,
            0x01000000, 0x02000000, 0x04000000, 0x08000000, 0x10000000, 0x20000000, 0x40000000, 0x80000000 };

    BitMap(int length){
        bits=new int[(int)(length>>5)+((length & 31)>0 ? 1:0)];
        this.length =length;
    }

    public void setBit(int num){
        if(num<0 || num>length)
            throw new RuntimeException("range error");
        int belowIndex=(num-1) >> 5;
        int shift=(num-1) & 31;
        bits[belowIndex] |= BIT_VALUE[shift];
    }

    public int getBit(int num){
        if(num<0 || num>length)
            throw new RuntimeException("range error");
        int belowIndex=(num-1) >> 5;
        int shift=(num-1) & 31;
        int temp=bits[belowIndex];
        //必须是>>>用0填充高位
        return (temp & BIT_VALUE[shift]) >>> shift;
    }

}
