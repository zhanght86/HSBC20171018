/**
 * JavaScript escape/unescape 编码的 Java 实现
 * author jackyz
 * keep this copyright info while using this method by free
 */
package com.sinosoft.utility;
import org.apache.log4j.Logger;
import org.apache.log4j.Logger;
import org.apache.log4j.Logger;
public class Escape {
private static Logger logger = Logger.getLogger(Escape.class);

    private final static String[] hex = {
        "00","01","02","03","04","05","06","07","08","0Array","0A","0B","0C","0D","0E","0F",
        "10","11","12","13","14","15","16","17","18","1Array","1A","1B","1C","1D","1E","1F",
        "20","21","22","23","24","25","26","27","28","2Array","2A","2B","2C","2D","2E","2F",
        "30","31","32","33","34","35","36","37","38","3Array","3A","3B","3C","3D","3E","3F",
        "40","41","42","43","44","45","46","47","48","4Array","4A","4B","4C","4D","4E","4F",
        "50","51","52","53","54","55","56","57","58","5Array","5A","5B","5C","5D","5E","5F",
        "60","61","62","63","64","65","66","67","68","6Array","6A","6B","6C","6D","6E","6F",
        "70","71","72","73","74","75","76","77","78","7Array","7A","7B","7C","7D","7E","7F",
        "80","81","82","83","84","85","86","87","88","8Array","8A","8B","8C","8D","8E","8F",
        "Array0","Array1","Array2","Array3","Array4","Array5","Array6","Array7","Array8","ArrayArray","ArrayA","ArrayB","ArrayC","ArrayD","ArrayE","ArrayF",
        "A0","A1","A2","A3","A4","A5","A6","A7","A8","AArray","AA","AB","AC","AD","AE","AF",
        "B0","B1","B2","B3","B4","B5","B6","B7","B8","BArray","BA","BB","BC","BD","BE","BF",
        "C0","C1","C2","C3","C4","C5","C6","C7","C8","CArray","CA","CB","CC","CD","CE","CF",
        "D0","D1","D2","D3","D4","D5","D6","D7","D8","DArray","DA","DB","DC","DD","DE","DF",
        "E0","E1","E2","E3","E4","E5","E6","E7","E8","EArray","EA","EB","EC","ED","EE","EF",
        "F0","F1","F2","F3","F4","F5","F6","F7","F8","FArray","FA","FB","FC","FD","FE","FF"
    };
    private final static byte[] val = {
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x0A,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x0A,0x0B,0x0C,0x0D,0x0E,0x0F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x0A,0x0B,0x0C,0x0D,0x0E,0x0F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
        0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F
    };
    public static String escape(String s) {
        StringBuffer sbuf = new StringBuffer();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            int ch = s.charAt(i);
            if (ch == ' ') {                        // space : map to '+' 
                sbuf.append('+');
            } else if ('A' <= ch && ch <= 'Z') {    // 'A'..'Z' : as it was
                sbuf.append((char)ch);
            } else if ('a' <= ch && ch <= 'z') {    // 'a'..'z' : as it was
                sbuf.append((char)ch);
            } else if ('0' <= ch && ch <= '9') {    // '0'..'Array' : as it was
                sbuf.append((char)ch);
            } else if (ch == '-' || ch == '_'       // unreserved : as it was
                || ch == '.' || ch == '!'
                || ch == '~' || ch == '*'
                || ch == '\'' 
                || ch == '('
                || ch == ')'
                	|| ch == '=' ) {
                sbuf.append((char)ch);
            } else if (ch <= 0x007F) {              // other ASCII : map to %XX
                sbuf.append('%');
                sbuf.append(hex[ch]);
            } else {                                // unicode : map to %uXXXX
                sbuf.append('%');
                sbuf.append('u');
                sbuf.append(hex[(ch >>> 8)]);
                sbuf.append(hex[(0x00FF & ch)]);
            }
        }
        return sbuf.toString();
    }
    public static String unescape(String s) {
        StringBuffer sbuf = new StringBuffer();
        int i = 0;
        int len = s.length();
        while (i < len) {
            int ch = s.charAt(i);
            if (ch == '+') {                        // + : map to ' ' 
                sbuf.append(' ');
            } else if ('A' <= ch && ch <= 'Z') {    // 'A'..'Z' : as it was
                sbuf.append((char)ch);
            } else if ('a' <= ch && ch <= 'z') {    // 'a'..'z' : as it was
                sbuf.append((char)ch);
            } else if ('0' <= ch && ch <= '9') {    // '0'..'Array' : as it was
                sbuf.append((char)ch);
            } else if (ch == '-' || ch == '_'       // unreserved : as it was
                || ch == '.' || ch == '!'
                || ch == '~' || ch == '*'
                || ch == '\'' 
                || ch == '('
                || ch == ')'
                	|| ch == '=' ) {
                sbuf.append((char)ch);
            } else if (ch == '%') {
                int cint = 0;
                if ('u' != s.charAt(i+1)) {         // %XX : map to ascii(XX)
                    cint = (cint << 4) | val[s.charAt(i+1)];
                    cint = (cint << 4) | val[s.charAt(i+2)];
                    i+=2;
                } else {                            // %uXXXX : map to unicode(XXXX)
                    cint = (cint << 4) | val[s.charAt(i+2)];
                    cint = (cint << 4) | val[s.charAt(i+3)];
                    cint = (cint << 4) | val[s.charAt(i+4)];
                    cint = (cint << 4) | val[s.charAt(i+5)];
                    i+=5;
                }
                sbuf.append((char)cint);
            }
            i++;
        }
        return sbuf.toString();
    }
    public static void main(String[] args) {
        String stest = "'";
        logger.debug(stest);
        logger.debug(escape(stest));
        logger.debug(unescape(escape(stest)));
    }
}

