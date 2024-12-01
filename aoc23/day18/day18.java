package aoc23.day18;

import java.util.*;
import java.util.logging.Logger;

public class day18 {
    static Logger logger = Logger.getLogger(day18.class.getName());
    static final String EXAMPLE1_FILE_PATH = "day18/example1.txt";
    static final String EXAMPLE2_FILE_PATH = "day18/example2.txt";
    static final String EXAMPLE3_FILE_PATH = "day18/example3.txt";
    static final String ACTUAL_FILE_PATH = "day18/input.txt";
    static int startX = 0;
    static int startY = 0;
    static String direction;
    static List<String> lines;
    static String image;

    public static String compoundStrings(String stringBefore, String strCompound) {
        List<String> imageBefore = new ArrayList<>(Arrays.asList(stringBefore.split("\n")));
        int imageBeforeHeight = imageBefore.size();
        int imageBeforeWidth = imageBefore.get(0).length();

        List<String> gridCompound = new ArrayList<>(Arrays.asList(strCompound.split("\n")));
        int gridCompoundHeight = gridCompound.size();
        int gridCompoundWidth = gridCompound.get(0).length();

        List<List<Character>> imageBeforeArrayList = new ArrayList<>();
        for (String line : imageBefore) {
            List<Character> row = new ArrayList<>();
            for (char c : line.toCharArray()) {
                row.add(c);
            }
            imageBeforeArrayList.add(row);
        }

        if ("L".equals(direction)) {
            int index = 0;
            for (int i = 1; i < gridCompoundWidth + 1; i++) {
                index = startX - i;
                if (index < 0) {
                    for (List<Character> row : imageBeforeArrayList) {
                        row.addFirst('.');
                    }
                    index = 0;
                }
                imageBeforeArrayList.get(startY).set(index, gridCompound.getFirst().charAt(i - 1));
            }
            startX = index;
        } else if ("R".equals(direction)) {
            int index = 0;
            for (int i = 1; i < gridCompoundWidth + 1; i++) {
                index = startX + i;
                if (index >= imageBeforeWidth) {
                    for (List<Character> row : imageBeforeArrayList) {
                        row.add('.');
                    }
                }
                imageBeforeArrayList.get(startY).set(startX + i, gridCompound.get(0).charAt(i - 1));
            }
            startX = index;
        }else if ("U".equals(direction)) {
            int index = 0;
            for (int i = 1; i < gridCompoundHeight + 1; i++) {
                index = startY - i;
                if (index < 0) {
                    imageBeforeArrayList.addFirst(new ArrayList<>(Arrays.asList(new Character[imageBeforeWidth])));
                    index = 0;
                }
                imageBeforeArrayList.get(index).set(startX, gridCompound.get(i - 1).charAt(0));
            }
            startY = index;
        }else if ("D".equals(direction)) {
            int index = 0;
            for (int i = 1; i < gridCompoundHeight + 1; i++) {
                index = startY + i;
                if (index >= imageBeforeHeight) {
                    imageBeforeArrayList.add(new ArrayList<>(Arrays.asList(new Character[imageBeforeWidth])));
                }
                imageBeforeArrayList.get(startY + i).set(startX, gridCompound.get(i - 1).charAt(0));
            }
            startY = index;
        }

        StringBuilder compoundedString = new StringBuilder();
        for (List<Character> line : imageBeforeArrayList) {
            for (Character c : line) {
                compoundedString.append(c == null ? '.' : c);
            }
            compoundedString.append("\n");
        }

        return compoundedString.toString();
    }

    public static String convertPlanToString(String line){
        List<String> command = new ArrayList<>(Arrays.asList(line.split(" ")));
        StringBuilder dig = new StringBuilder();
        direction = command.getFirst();
        for (int i = 0; i < Integer.parseInt(command.get(1)) ; i++) {
            dig.append("#");
            if (command.getFirst().equals("U") || command.getFirst().equals("D")){
                dig.append("\n");
            }
        }
        return dig.toString();
    }

    public static String convertPlanToStringPartTwo(String line){
        List<String> command = new ArrayList<>(Arrays.asList(line.split(" ")));
        StringBuilder dig = new StringBuilder();
        String hexValue = command.getLast().substring(2,command.getLast().length()-2);
        int value = utils.MathUtil.hexadecimalToDecimal(hexValue);
        String directionNum = command.getLast().substring(command.getLast().length()-2,command.getLast().length()-1);
        switch (directionNum){
            case "0" -> direction = "R";
            case "1" -> direction = "D";
            case "2" -> direction = "L";
            case "3" -> direction = "U";
        }
        for (int i = 0; i < value ; i++) {
            dig.append("#");
            if (direction.equals("U") || direction.equals("D")){
                dig.append("\n");
            }
        }
        return dig.toString();
    }


    public static String getImageFromDigPlan(Boolean isPartOne){
        String currString = "#";
        startX = 0;
        if(isPartOne){
            for (String line : lines) {
                String tempString = convertPlanToString(line);
                currString = compoundStrings(currString, tempString);
            }
            return currString;
        }
        for (String line : lines) {
            String tempString = convertPlanToStringPartTwo(line);
            currString = compoundStrings(currString, tempString);
        }
        return currString;
    }

    public static int getCountInnerFloodFill(int startPointX, int startPointY) {
        List<String> imageList = new ArrayList<>(Arrays.asList(image.split("\n")));
        if (imageList.get(startPointY).charAt(startPointX) == '#') {
            return 0;
        }

        int count = 0;
        int rows = imageList.size();
        int cols = imageList.get(0).length();

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startPointX, startPointY});
        imageList.set(startPointY, replaceChar(imageList.get(startPointY), startPointX, '#'));

        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            int x = point[0];
            int y = point[1];
            count++;

            for (int[] direction : new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}}) {
                int newX = x + direction[0];
                int newY = y + direction[1];

                if (newX >= 0 && newX < cols && newY >= 0 && newY < rows &&
                        imageList.get(newY).charAt(newX) == '.') {
                    queue.add(new int[]{newX, newY});
                    imageList.set(newY, replaceChar(imageList.get(newY), newX, '#'));
                }
            }
        }
        StringBuilder newImage = new StringBuilder();
        for (String line : imageList) {
            newImage.append(line).append("\n");
        }
        image = newImage.toString().trim();

        return count;
    }

    private static String replaceChar(String str, int index, char replace) {
        char[] chars = str.toCharArray();
        chars[index] = replace;
        return new String(chars);
    }

    public static void main(String[] args) {
        lines = utils.FileParseUtil.readLinesFromFile(EXAMPLE1_FILE_PATH, logger);
        boolean isPartOne = false;
        image = getImageFromDigPlan(isPartOne);
        int boundaryCount = image.length()
                - image.replace("#", "").length();
        int innerCount = getCountInnerFloodFill(1,1);
        System.out.println(String.valueOf(boundaryCount+innerCount));
    }
}
