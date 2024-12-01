package aoc24.day20.trial2;

import java.util.List;

public class FlipFlop implements Item{

    private String name;
    private String status;
    private List<String> connectedInputNames;
    private List<String> connectedOutputNames;

    public FlipFlop(String name, String status, List<String> connectedInputNames, List<String> connectedOutputNames) {
        this.name = name;
        this.status = status;
        this.connectedInputNames = connectedInputNames;
        this.connectedOutputNames = connectedOutputNames;
    }

    public void pulseReceive(String pulse, String inputName){
        if (pulse.equals("LOW")){
            if (status.equals("OFF")){
                this.status = "ON";
            }
            else{
                this.status = "OFF";
            }
        }
    }

    public List<Pulse> pulseTransfer(String pulse){
        if (pulse.equals("LOW")){
            return connectedOutputNames.stream()
                .map(connectedOutputName ->
                    status.equals("ON") ? new Pulse(this.name, connectedOutputName,"HIGH") :
                        new Pulse(this.name, connectedOutputName,"LOW")
                )
                .toList();
        }
        return null;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
