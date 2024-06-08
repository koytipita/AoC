package day20.trial2;

import java.util.List;
import java.util.Map;

public class Conjunction implements Item{

    private String name;
    private Map<String,String> connectedInputsMemory;
    private List<String> connectedInputNames;
    private List<String> connectedOutputNames;
    private String remember;

    public Conjunction(String name, Map<String, String> connectedInputsMemory, List<String> connectedInputNames
        , List<String> connectedOutputNames) {
        this.name = name;
        this.connectedInputsMemory = connectedInputsMemory;
        this.connectedInputNames = connectedInputNames;
        this.connectedOutputNames = connectedOutputNames;
    }

    public void pulseReceive(String pulse, String inputName){
        connectedInputsMemory.put(inputName,pulse);
    }

    public List<Pulse> pulseTransfer(String pulse){
        return connectedOutputNames.stream()
            .map(connectedOutputName -> new Pulse(this.name, connectedOutputName
                , !connectedInputsMemory.containsValue("LOW") ? "LOW" : "HIGH"))
            .toList();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getConnectedInputNames() {
        return connectedInputNames;
    }

    public void setConnectedInputNames(List<String> connectedInputNames) {
        this.connectedInputNames = connectedInputNames;
    }

    public List<String> getConnectedOutputNames() {
        return connectedOutputNames;
    }

    public void setConnectedOutputNames(List<String> connectedOutputNames) {
        this.connectedOutputNames = connectedOutputNames;
    }

    public Map<String, String> getConnectedInputsMemory() {
        return connectedInputsMemory;
    }

    public void setConnectedInputsMemory(Map<String, String> connectedInputsMemory) {
        this.connectedInputsMemory = connectedInputsMemory;
    }
}
