package day24;

import java.math.BigDecimal;
import java.math.MathContext;
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
    private static final MathContext CONTEXT = MathContext.DECIMAL128;



    public static void main(String[] args) {
        lines = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH,logger);
        System.out.println(countAllCross());
        System.out.println(getCommonPositionVelocityPair());
    }

    public static long getCommonPositionVelocityPair(){
        String vel1 = lines.get(0).split(" @ ")[1];
        String pos1 = lines.get(0).split(" @ ")[0];
        long[] x1 = Arrays.stream(pos1.split(", ")).map(String::trim).mapToLong(Long::parseLong).toArray();
        long[] v1 = Arrays.stream(vel1.split(", ")).map(String::trim).mapToLong(Long::parseLong).toArray();
        // turns out - Java double is 64 bit, and the numbers in the real input are too large to be accurately
        // represented. The final result is 5 off due to rounding errors. 128 Bit-BigDecimals are good enough.
        BigDecimal[][] matrix = new BigDecimal[4][5];
        for (int i = 0; i < 4; i++) {
            String vel2 = lines.get(i+1).split(" @ ")[1];
            String pos2 = lines.get(i+1).split(" @ ")[0];
            long[] x2 = Arrays.stream(pos2.split(", ")).map(String::trim).mapToLong(Long::parseLong).toArray();
            long[] v2 = Arrays.stream(vel2.split(", ")).map(String::trim).mapToLong(Long::parseLong).toArray();
            matrix[i][0] = bd(x1[1]).subtract(bd(x2[1]));
            matrix[i][1] = bd(x2[0]).subtract(bd(x1[0]));
            matrix[i][2] = bd(v2[1]).subtract(bd(v1[1]));
            matrix[i][3] = bd(v1[0]).subtract(bd(v2[0]));
            matrix[i][4] = bd(x1[1]).multiply(bd(v1[0]))
                .subtract(bd(x1[0]).multiply(bd(v1[1])))
                .subtract(bd(x2[1]).multiply(bd(v2[0])))
                .add(bd(x2[0]).multiply(bd(v2[1])));
        }

        gauss(matrix);

        BigDecimal rsy = matrix[3][4].divide(matrix[3][3], CONTEXT);
        BigDecimal rsx = (matrix[2][4].subtract(matrix[2][3].multiply(rsy))).divide(matrix[2][2], CONTEXT);
        BigDecimal rvy = (matrix[1][4].subtract(matrix[1][3].multiply(rsy)).subtract(matrix[1][2].multiply(rsx))).divide(matrix[1][1], CONTEXT);
        BigDecimal rvx = (matrix[0][4].subtract(matrix[0][3].multiply(rsy)).subtract(matrix[0][2].multiply(rsx)).subtract(matrix[0][1].multiply(rvy)).divide(matrix[0][0], CONTEXT));

        BigDecimal t1 = (bd(x1[0]).subtract(rsx)).divide(rvx.subtract(bd(v1[0])), CONTEXT);
        BigDecimal z1 = bd(x1[2]).add(t1.multiply(bd(v1[2])));
        String vel2 = lines.get(1).split(" @ ")[1];
        String pos2 = lines.get(1).split(" @ ")[0];
        long[] x2 = Arrays.stream(pos2.split(", ")).map(String::trim).mapToLong(Long::parseLong).toArray();
        long[] v2 = Arrays.stream(vel2.split(", ")).map(String::trim).mapToLong(Long::parseLong).toArray();
        BigDecimal t2 = (bd(x2[0]).subtract(rsx)).divide(rvx.subtract(bd(v2[0])), CONTEXT);
        BigDecimal z2 = bd(x2[2]).add(t2.multiply(bd(v2[2])));
        BigDecimal rvz = (z2.subtract(z1)).divide(t2.subtract(t1), CONTEXT);
        BigDecimal rsz = z1.subtract(rvz.multiply(t1));

        return rsx.add(rsy).add(rsz).longValue();
    }

    public static BigDecimal bd(long in) {
        return BigDecimal.valueOf(in);
    }

    public static void gauss(BigDecimal[][] matrix) {
        int pivotRow = 0;
        int pivotCol = 0;
        int nRows = matrix.length;
        int nCols = matrix[0].length;
        while (pivotRow < nRows && pivotCol < nCols) {
            BigDecimal max = BigDecimal.ZERO;
            int idxMax = -1;
            for (int i = pivotRow; i < nRows; i++) {
                BigDecimal cand = matrix[i][pivotCol].abs();
                if (cand.compareTo(max) > 0) {
                    max = cand;
                    idxMax = i;
                }
            }
            if (matrix[idxMax][pivotCol].equals(BigDecimal.ZERO)) {
                pivotCol++;
            } else {
                // swap rows idxMax and pivotRow
                BigDecimal[] tmp = matrix[pivotRow];
                matrix[pivotRow] = matrix[idxMax];
                matrix[idxMax] = tmp;
                for (int i = pivotRow + 1; i < nRows; i++) {
                    BigDecimal factor = matrix[i][pivotCol].divide(matrix[pivotRow][pivotCol], CONTEXT);
                    matrix[i][pivotCol] = BigDecimal.ZERO;
                    for (int j = pivotCol + 1; j < nCols; j++) {
                        matrix[i][j] = matrix[i][j].subtract(factor.multiply(matrix[pivotRow][j]));
                    }
                }
            }
            pivotCol++;
            pivotRow++;
        }
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

        double a2 = (double) D[1] - C[1];
        double b2 = (double) C[0] - D[0];
        double c2 = a2*(C[0])+ b2*(C[1]);

        double determinant = a1*b2 - a2*b1;

        if (determinant == 0)
        {
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
