package aoc23.day20.trial2;

import java.util.List;

public interface Item {
    public String getName();
    public List<String> getConnectedInputNames();
    public List<String> getConnectedOutputNames();
    public void pulseReceive(String pulse, String inputName);
    public List<Pulse> pulseTransfer(String pulse);
}
