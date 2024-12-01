package aoc23.day20.trial2;

import java.util.ArrayList;
import java.util.List;

public class NonType implements Item{
    private String name;
    private List<String> connectedInputNames;
    private List<String> connectedOutputNames;

    public NonType(String name, List<String> connectedInputNames) {
        this.name = name;
        this.connectedInputNames = connectedInputNames;
        this.connectedOutputNames = new ArrayList<>();
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

    @Override
    public void pulseReceive(String pulse, String inputName) {

    }

    @Override
    public List<Pulse> pulseTransfer(String pulse) {
        return null;
    }

    public void setConnectedOutputNames(List<String> connectedOutputNames) {
        this.connectedOutputNames = connectedOutputNames;
    }
}
