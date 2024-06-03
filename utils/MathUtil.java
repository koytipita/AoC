package utils;

public class MathUtil {
    // Gcd of u and v
    // using recursive method
    public static double GCD(double u, double v)
    {
        if (u == 0)
            return v;
        return GCD(v % u, u);
    }

    // LCM of two numbers
    public static double LCM(double u, double v)
    {
        return (u / GCD(u, v)) * v;
    }

    public static int hexadecimalToDecimal(String hexVal)
    {
        int len = hexVal.length();
        int base = 1;
        int dec_val = 0;
        for (int i = len - 1; i >= 0; i--) {
            if (hexVal.charAt(i) >= '0'
                    && hexVal.charAt(i) <= '9') {
                dec_val += (hexVal.charAt(i) - 48) * base;
                base = base * 16;
            }
            else if (hexVal.charAt(i) >= 'A'
                    && hexVal.charAt(i) <= 'F') {
                dec_val += (hexVal.charAt(i) - 55) * base;
                base = base * 16;
            }
            else if (hexVal.charAt(i) >= 'a'
                    && hexVal.charAt(i) <= 'f') {
                dec_val += (hexVal.charAt(i) - 87) * base;
                base = base * 16;
            }
        }
        return dec_val;
    }

}
