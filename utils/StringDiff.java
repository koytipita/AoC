package utils;
public class StringDiff {

    public static String StringDiff(String s1, String s2) {
        // Start from the end of s1 and beginning of s2
        int s1Length = s1.length();
        int s2Length = s2.length();
        int minLength = Math.min(s1Length, s2Length);

        // Find the longest common suffix of s1 and prefix of s2
        for (int i = 1; i <= minLength; i++) {
            if (s1.substring(s1Length - i).equals(s2.substring(0, i))) {
                return s1.substring(s1Length - i);
            }
        }

        return ""; // Return empty string if no common suffix-prefix found
    }
}
