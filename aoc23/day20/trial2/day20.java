package aoc23.day20.trial2;

import java.util.*;
import java.util.logging.Logger;

public class day20 {
    static Logger logger = Logger.getLogger(day20.class.getName());
    static final String EXAMPLE1_FILE_PATH = "day20/example1.txt";
    static final String EXAMPLE2_FILE_PATH = "day20/example2.txt";
    static final String EXAMPLE3_FILE_PATH = "day20/example3.txt";
    static final String ACTUAL_FILE_PATH = "day20/input.txt";
    static List<String> lines;
    static List<Item> items = new ArrayList<>();
    static List<FlipFlop> flipFlops;
    static List<Item> queue = new ArrayList<>();
    static List<Conjunction> conjunctions;
    static List<NonType> nonTypes = new ArrayList<>();
    static List<Pulse> pulses = new ArrayList<>();
    static int buttonPressCount = 0;
    static long highCount = 0L;
    static long lowCount = 0L;
    static long previousHighCount = 0L;
    static long previousLowCount = 0L;

    public static void main(String[] args) {
        lines = utils.FileParseUtil.readLinesFromFile(ACTUAL_FILE_PATH,logger);
        parseFlipFlops();
        parseConjunctions();
        parseNonTypes();
        propagationCycle();
        System.out.println((1000.0/buttonPressCount)*(1000.0/buttonPressCount)*highCount*lowCount);
    }
    public static void propagationCycle(){
        do {
            pressButton();
            do {
                Pulse pulse = pulses.getFirst();
                if( (pulse.getFrom().equals("db") && pulse.getValue().equals("HIGH")) ||
                    (pulse.getFrom().equals("dh") && pulse.getValue().equals("HIGH")) ||
                    (pulse.getFrom().equals("lm") && pulse.getValue().equals("HIGH")) ||
                    (pulse.getFrom().equals("sg") && pulse.getValue().equals("HIGH"))
                ){
                    System.out.println(pulse.getFrom() + buttonPressCount);
                    System.out.println(3851L*3889*4027*4079);
                }
                if (pulse.getValue().equals("HIGH")) highCount += 1;
                if (pulse.getValue().equals("LOW")) lowCount += 1;
                pulses.remove(0);
                List<Item> currentItems = items.stream().filter(item ->
                    item.getName().equals(pulse.getTo())).toList();
                currentItems.forEach(currentItem -> currentItem.pulseReceive(pulse.getValue(), pulse.getFrom()));
                List<Pulse> pulsesToTransfer = currentItems.stream()
                    .flatMap(currentItem -> Optional.ofNullable(currentItem.pulseTransfer(pulse.getValue())).stream().flatMap(Collection::stream))
                    .toList();
                if (!pulsesToTransfer.isEmpty()){
                    pulses.addAll(pulsesToTransfer);
                }
            }
            while(!pulses.isEmpty());
            //if (buttonPressCount >= 1000) break;
        }
        while (!detectCycle());
    }

    public static boolean detectCycle(){
        return items.stream()
                .filter(FlipFlop.class::isInstance)
                .allMatch(item ->{
                        FlipFlop flipflop = (FlipFlop) item;
                        return flipflop.getStatus().equals("OFF");
                        }
                );
    }

    public static void pressButton(){
        buttonPressCount += 1;
        lowCount +=1;
        String currentLine = lines.stream()
            .filter(line -> line.split(" ")[0].equals("broadcaster"))
            .findAny().orElseThrow();
        List<String> currentItemNames = Arrays.stream(currentLine.split(" -> ")[1].split(", ")).toList();
        currentItemNames
            .forEach(currentItemName ->{
                pulses.add(new Pulse("broadcaster",
                    currentItemName, "LOW"));
            });
    }

    public static void parseFlipFlops(){
        flipFlops = lines.stream()
            .filter(line -> {
                String[] items = line.split(" ");
                return items[0].charAt(0)=='%';
            })
            .map(line -> {
                String name = line.split(" -> ")[0].substring(1);
                List<String> outputNames = Arrays.stream(line.split(" -> ")[1].split(", ")).toList();
                return new FlipFlop(name, "OFF", new ArrayList<>(), outputNames);
            })
            .toList();

        lines.stream()
            .filter(line -> {
                List<String> lastItemNames = Arrays.stream(line.split(" -> ")[1].split(", ")).toList();
                return lastItemNames.stream()
                    .anyMatch(lastItemName -> flipFlops.stream()
                        .map(FlipFlop::getName)
                        .anyMatch(flipFlopName -> flipFlopName.equals(lastItemName)));
            })
            .forEach(line ->{
                List<String> lastItemNames = Arrays.stream(line.split(" -> ")[1].split(", ")).toList();
                lastItemNames.stream()
                    .filter(lastItemName -> flipFlops.stream()
                            .map(FlipFlop::getName)
                            .anyMatch(flipFlopName -> flipFlopName.equals(lastItemName))
                    )
                    .forEach(flipFlopName -> {
                        String inputName = line.split(" -> ")[0];
                        FlipFlop flipFlop = flipFlops.stream().filter(flipFlop1 -> flipFlop1.getName().equals(flipFlopName)).findAny().orElseThrow();
                        List<String> connectedInputNames = flipFlop.getConnectedInputNames();
                        inputName = (inputName.charAt(0) == '%' || inputName.charAt(0) == '&') ? inputName.substring(1) : inputName;
                        connectedInputNames.add(inputName);
                        flipFlop.setConnectedInputNames(connectedInputNames);
                    });
            });
        items.addAll(flipFlops);
    }

    public static void parseConjunctions(){
        conjunctions = lines.stream()
            .filter(line -> {
                String[] items = line.split(" ");
                return items[0].charAt(0)=='&';
            })
            .map(line -> {
                String name = line.split(" -> ")[0].substring(1);
                List<String> outputNames = Arrays.stream(line.split(" -> ")[1].split(", ")).toList();
                return new Conjunction(name, new HashMap<>(), new ArrayList<>(), outputNames);
            })
            .toList();
        lines.stream()
            .filter(line -> {
                List<String> lastItemNames = Arrays.stream(line.split(" -> ")[1].split(", ")).toList();
                return lastItemNames.stream()
                    .anyMatch(lastItemName -> conjunctions.stream()
                        .map(Conjunction::getName)
                        .anyMatch(conjunctionName -> conjunctionName.equals(lastItemName)));
            })
            .forEach(line ->{
                List<String> lastItemNames = Arrays.stream(line.split(" -> ")[1].split(", ")).toList();
                lastItemNames.stream()
                    .filter(lastItemName -> conjunctions.stream()
                        .map(Conjunction::getName)
                        .anyMatch(conjunctionName -> conjunctionName.equals(lastItemName))
                    )
                    .forEach(conjunctionName -> {
                        String inputName = line.split(" -> ")[0];
                        Conjunction conjunction = conjunctions.stream().filter(conjunction1 -> conjunction1.getName().equals(conjunctionName)).findAny().orElseThrow();
                        List<String> connectedInputNames = conjunction.getConnectedInputNames();
                        Map<String, String> connectedInputsMemory = conjunction.getConnectedInputsMemory();
                        inputName = (inputName.charAt(0) == '%' || inputName.charAt(0) == '&') ? inputName.substring(1) : inputName;
                        connectedInputNames.add(inputName);
                        connectedInputsMemory.put(inputName, "LOW");
                        conjunction.setConnectedInputNames(connectedInputNames);
                        conjunction.setConnectedInputsMemory(connectedInputsMemory);
                    });
            });
        items.addAll(conjunctions);
    }
    public static void parseNonTypes(){
        lines.stream()
            .filter(line -> {
                List<String> lastItemNames = Arrays.stream(line.split(" -> ")[1].split(", ")).toList();
                return lastItemNames.stream()
                    .anyMatch(lastItemName -> flipFlops.stream()
                        .map(FlipFlop::getName)
                        .noneMatch(flipFlopName -> flipFlopName.equals(lastItemName)) &&
                        conjunctions.stream()
                            .map(Conjunction::getName)
                            .noneMatch(conjunctionName -> conjunctionName.equals(lastItemName)));
            })
            .forEach(line ->{
                List<String> lastItemNames = Arrays.stream(line.split(" -> ")[1].split(", ")).toList();
                lastItemNames.stream()
                    .filter(lastItemName -> flipFlops.stream()
                        .map(FlipFlop::getName)
                        .noneMatch(flipFlopName -> flipFlopName.equals(lastItemName)) &&
                        conjunctions.stream()
                            .map(Conjunction::getName)
                            .noneMatch(conjunctionName -> conjunctionName.equals(lastItemName))
                    )
                    .forEach(nonTypeName -> {
                        String inputName = line.split(" -> ")[0];
                        NonType nonType = new NonType(nonTypeName, new ArrayList<>());
                        List<String> connectedInputNames = nonType.getConnectedInputNames();
                        inputName = (inputName.charAt(0) == '%' || inputName.charAt(0) == '&') ? inputName.substring(1) : inputName;
                        connectedInputNames.add(inputName);
                        nonType.setConnectedInputNames(connectedInputNames);
                        nonTypes.add(nonType);
                    });
            });
        items.addAll(nonTypes);
    }

}
