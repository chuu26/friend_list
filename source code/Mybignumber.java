import java.util.Scanner;

/**
 * Tác giả: Trần Hữu Phát.
 * sum là hàm để thực hiện phép cộng 2 chuỗi số
 */

public class MyBigNumber {

    /**
Đây là cmt.
*/

    public static String sum(final String a,final String b) {
        int c = a.length();
        int d = b.length();
        String result = "";
        int sodu = 0;
        int max = 0;
        int flag;
        int so1;
        int so2;
        char num1;
        char num2;
        
        //so sánh độ dài 2 chuổi.
        
        if (c >= d) {
            max = c;
        } else {
            max = d;
        }
        
        //vòng lặp
        
        for (int i = 0; i < max;i++) {
            
            //dùng lấy vị trí cuối của dãy số
            so1 = c - i - 1;
            so2 = d - i - 1;
            
            //lấy số ở vị trí đó ra
            num1 = '0';
            num2 = '0';
            if (so1 >= 0) {
                num1 = a.charAt(so1); 
            } else if (so2 >= 0) {
                num2 = b.charAt(so2);
            }
            
            //cộng từng số
            flag = (num1 - '0') + (num2 - '0') + sodu;
            
            //tách và cộng ký tự vào chuổi
            if (so1 > 0) {
                result = (flag % 10) + result;
            } else {
                result = flag + result;
            }
            
            //Tính số dư
            sodu = flag / 10;
        }
        
        return result;
    }
}