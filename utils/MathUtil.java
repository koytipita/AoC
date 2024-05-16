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
}
