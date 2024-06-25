package day24;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class day24 {
    static Logger logger = Logger.getLogger(day24.class.getName());
    static final String EXAMPLE1_FILE_PATH = "day24/example1.txt";
    static final String EXAMPLE2_FILE_PATH = "day24/example2.txt";
    static final String EXAMPLE3_FILE_PATH = "day24/example3.txt";
    static final String ACTUAL_FILE_PATH = "day24/input.txt";
    static long least = 200000000000000L;
    static long most = 400000000000000L;
    static List<String> lines;



    public static void main(String[] args) {
        lines = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH,logger);
        System.out.println(countAllCross());


    }

    public static long countAllCross(){
        return IntStream.range(0, lines.size()).mapToLong(index1 -> IntStream.range(index1+1, lines.size()).filter(index2 -> {
            String pos1 = lines.get(index1).split(" @ ")[0];
            String vel1 = lines.get(index1).split(" @ ")[1];
            String pos2 = lines.get(index2).split(" @ ")[0];
            String vel2 = lines.get(index2).split(" @ ")[1];
            return checkCross(pos1,vel1,pos2,vel2);
        }).count()).sum();
    }
    public static boolean checkCross(String pos1, String vel1, String pos2, String vel2){
        long[] line1position1 = Arrays.stream(pos1.split(", ")).map(String::trim).mapToLong(Long::parseLong).toArray();
        long[] line2position1 = Arrays.stream(pos2.split(", ")).map(String::trim).mapToLong(Long::parseLong).toArray();
        long[] velocity1 = Arrays.stream(vel1.split(", ")).map(String::trim).mapToLong(Long::parseLong).toArray();
        long[] velocity2 = Arrays.stream(vel2.split(", ")).map(String::trim).mapToLong(Long::parseLong).toArray();
        long[] line1position2 = addTwoArrays(line1position1,velocity1);
        long[] line2position2 = addTwoArrays(line2position1,velocity2);
        double[] intersection = calculateIntersection(line1position1,line1position2,line2position1,line2position2);

        if (intersection == null){
            return false;
        }

        double[] step1 = substractTwoArrays(intersection,line1position1);
        double[] step2 = substractTwoArrays(intersection,line2position1);

        return checkInSameDirection(step1, step2, velocity1, velocity2);
    }

    public static boolean checkInSameDirection(double[] step1, double[] step2, long[] velocity1, long[] velocity2){
        return step1[0] * velocity1[0] > 0 && step2[0] * velocity2[0] > 0;
    }

    public static double[] substractTwoArrays(double[] arr1, long[] arr2){
        double[] arr3 = new double[arr1.length];

        for(int i =0; i< arr1.length; i++)
        {
            arr3[i] = arr1[i] - arr2[i];
        }
        return arr3;
    }

    public static long[] addTwoArrays(long[] arr1, long[] arr2){
        long[] arr3 = new long[arr1.length];

        for(int i =0; i< arr1.length; i++)
        {
            arr3[i] = arr1[i] + arr2[i];
        }
        return arr3;
    }
    public static double[] calculateIntersection(long[] A, long[] B, long[] C, long[] D) {
        double a1 = (double) B[1] - A[1];
        double b1 = (double) A[0] - B[0];
        double c1 = a1*(A[0]) + b1*(A[1]);

        // Line CD represented as a2x + b2y = c2
        double a2 = (double) D[1] - C[1];
        double b2 = (double) C[0] - D[0];
        double c2 = a2*(C[0])+ b2*(C[1]);

        double determinant = a1*b2 - a2*b1;

        if (determinant == 0)
        {
            // The lines are parallel. This is simplified
            // by returning a null
            return null;
        }
        else
        {
            double x = (b2*c1 - b1*c2)/determinant;
            double y = (a1*c2 - a2*c1)/determinant;

            if (x >= least && x <= most && y >= least && y <= most){
                return new double[]{x, y};
            }
            return null;
        }
    }
}
