package day7;

import day6.day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static java.lang.Character.getNumericValue;
import static java.lang.Character.isDigit;

public class java7 {

    static Logger logger = Logger.getLogger(day6.class.getName());




    public static void main(String[] args) {

        String filePath = "day7/input.txt"; // Replace with the actual file path
        List<String> lines = readLinesFromFile(filePath);
        List<HandPower> hands = new ArrayList<>();
        for (String line: lines) {
           hands.add(calculatehandPower(line.substring(0,5),line.substring(6)));
        }
        List<HandPower> finalHands= hands.stream().sorted(new Comparator<>() {
                    @Override
                    public int compare(HandPower o1, HandPower o2) {
                        int handComp = Integer.compare(o1.getHandRankingsEnum().ordinal(), o2.getHandRankingsEnum().ordinal());
                        if (handComp != 0)
                            return handComp;
                        int sumComp = Double.compare(o1.getCardSum(), o2.getCardSum());
                        return sumComp;
                    }
                })
                .toList();
        Integer result = (int) IntStream.range(1, hands.size()+1).mapToDouble(index -> finalHands.get(index-1).getBet()*index).sum();
        System.out.println(result);
    }
    public static Double calculateHighestCards(String cards){
        Double cardSum = (double) 0;
        double digit = Math.pow(15,5);
        for (char c : cards.toCharArray()){
            if(isDigit(c)){
                cardSum += (getNumericValue(c)+1)*digit;
            }
            else{
                switch (c){
                    case 'A':
                        cardSum += 14*digit;
                        break;
                    case 'K':
                        cardSum += 13*digit;
                        break;
                    case 'Q':
                        cardSum += 12*digit;
                        break;
                    case 'J':
                        cardSum += 2*digit;
                        break;
                    case 'T':
                        cardSum += 11*digit;
                        break;
                }
            }
            digit = digit /15;
        }
        return cardSum;
    }
    public static HandPower calculatehandPower(String cards,String bet){
        Map<Character, Integer> charCount = new HashMap<>();

        // Count occurrences of each character
        for (char c : cards.toCharArray()) {
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }
        for (Map.Entry<Character, Integer> entry : charCount.entrySet()) {
            if (entry.getValue() == 5) {
                return new HandPower(calculateHighestCards(cards), Integer.parseInt(bet), HandPower.handRankingsEnum.FIVEOFAKIND);
            }
            if (entry.getValue() == 4) {
                if(charCount.get('J') != null && charCount.get('J') == 4){
                    return new HandPower(calculateHighestCards(cards), Integer.parseInt(bet), HandPower.handRankingsEnum.FIVEOFAKIND);
                }
                if(charCount.get('J') != null && charCount.get('J') == 1){
                    return new HandPower(calculateHighestCards(cards), Integer.parseInt(bet), HandPower.handRankingsEnum.FIVEOFAKIND);
                }
                return new HandPower(calculateHighestCards(cards), Integer.parseInt(bet), HandPower.handRankingsEnum.FOUROFAKIND);
            }
            if (entry.getValue() == 3) {
                if(charCount.size()==2){
                    if (charCount.get('J') != null && charCount.get('J') == 2){
                        return new HandPower(calculateHighestCards(cards), Integer.parseInt(bet), HandPower.handRankingsEnum.FIVEOFAKIND);
                    }
                    if (charCount.get('J') != null && charCount.get('J') == 3){
                        return new HandPower(calculateHighestCards(cards), Integer.parseInt(bet), HandPower.handRankingsEnum.FIVEOFAKIND);
                    }
                    return new HandPower(calculateHighestCards(cards), Integer.parseInt(bet), HandPower.handRankingsEnum.FULLHOUSE);
                }
                else{
                    if (charCount.get('J') != null && charCount.get('J') == 3){
                        return new HandPower(calculateHighestCards(cards), Integer.parseInt(bet), HandPower.handRankingsEnum.FOUROFAKIND);
                    }
                    if (charCount.get('J') != null && charCount.get('J') == 1){
                        return new HandPower(calculateHighestCards(cards), Integer.parseInt(bet), HandPower.handRankingsEnum.FOUROFAKIND);
                    }
                    return new HandPower(calculateHighestCards(cards), Integer.parseInt(bet), HandPower.handRankingsEnum.THREEOFAKIND);
                }
            }
            if(entry.getValue() == 2){
                if(charCount.size() == 4) {
                    if (charCount.get('J') != null && charCount.get('J') == 2){
                        return new HandPower(calculateHighestCards(cards), Integer.parseInt(bet), HandPower.handRankingsEnum.THREEOFAKIND);
                    }
                    if (charCount.get('J') != null && charCount.get('J') == 1){
                        return new HandPower(calculateHighestCards(cards), Integer.parseInt(bet), HandPower.handRankingsEnum.THREEOFAKIND);
                    }
                    return new HandPower(calculateHighestCards(cards), Integer.parseInt(bet), HandPower.handRankingsEnum.ONEPAIR);
                }
                if (charCount.size()==3){
                    if (charCount.get('J') != null && charCount.get('J') == 2){
                        return new HandPower(calculateHighestCards(cards), Integer.parseInt(bet), HandPower.handRankingsEnum.FOUROFAKIND);
                    }
                    if (charCount.get('J') != null && charCount.get('J') == 1){
                        return new HandPower(calculateHighestCards(cards), Integer.parseInt(bet), HandPower.handRankingsEnum.FULLHOUSE);
                    }
                    return new HandPower(calculateHighestCards(cards), Integer.parseInt(bet), HandPower.handRankingsEnum.TWOPAIR);
                }
            }
        }
        if (charCount.get('J') != null && charCount.get('J') == 1){
            return new HandPower(calculateHighestCards(cards), Integer.parseInt(bet), HandPower.handRankingsEnum.ONEPAIR);
        }
        return new HandPower(calculateHighestCards(cards), Integer.parseInt(bet), HandPower.handRankingsEnum.HIGHCARD);
    }


    public static List<String> readLinesFromFile(String filePath) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            logger.log(Level.WARNING,"Error reading file: " + e.getMessage());
        }
        return lines;
    }
}
