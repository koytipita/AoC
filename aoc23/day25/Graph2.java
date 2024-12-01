package aoc23.day25;

import java.util.*;

public class Graph2 {
    private Map<String, Map<String, Integer>> G;
    private Map<String, String> parent;

    public Map<String, Map<String, Integer>> getG() {
        return G;
    }

    public Graph2(Map<String, Map<String, Integer>> G) {
        this.G = G;
        this.parent = new HashMap<>();
        for (String n : G.keySet()) {
            parent.put(n, null);
        }
    }

    public boolean bfs(String s, String t) {
        for (String n : G.keySet()) {
            parent.put(n, null);
        }
        parent.put(s, s);
        Queue<String> Q = new LinkedList<>();
        Q.add(s);
        while (!Q.isEmpty()) {
            String n = Q.poll();
            for (Map.Entry<String, Integer> entry : G.get(n).entrySet()) {
                String e = entry.getKey();
                int c = entry.getValue();
                if (c > 0 && parent.get(e) == null) {
                    parent.put(e, n);
                    Q.add(e);
                }
            }
        }
        return parent.get(t) != null;
    }

    public int minCut(String s, String t) {
        for (Map.Entry<String, Map<String, Integer>> entry : G.entrySet()) {
            for (String k : entry.getValue().keySet()) {
                G.get(entry.getKey()).put(k, 1);
            }
        }

        int maxFlow = 0;
        while (bfs(s, t)) {
            int flow = Integer.MAX_VALUE;
            String n = t;
            while (!n.equals(s)) {
                flow = Math.min(flow, G.get(parent.get(n)).get(n));
                n = parent.get(n);
            }

            maxFlow += flow;

            String v = t;
            while (!v.equals(s)) {
                String u = parent.get(v);
                G.get(u).put(v, G.get(u).get(v) - flow);
                G.get(v).put(u, G.get(v).get(u) + flow);
                v = u;
            }
        }
        return maxFlow;
    }

    public int solve() {
        long g1 = parent.values().stream().filter(Objects::nonNull).count();
        return (int) ((G.size() - g1) * g1);
    }
}
