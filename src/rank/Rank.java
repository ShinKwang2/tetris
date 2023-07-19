package rank;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Rank {

    private static Map<String, Info> ranks = new HashMap<>();

    private static List<JLabel> rankLabels;

    public Rank(List<JLabel> rankLabels) {
        this.rankLabels = rankLabels;
    }

    public static void registerWin(String name, int win) {
        if (validate(name)) {
            return;
        }

        Info info = ranks.get(name);
        if (info == null) {
            ranks.put(name, new Info(name, win, 0));
            return;
        }
        info.addWin();
        ranks.put(name, info);
    }
    public static void registerLose(String name, int lose) {
        if (validate(name)) {
            return;
        }

        Info info = ranks.get(name);
        if (info == null) {
            ranks.put(name, new Info(name, 0, lose));
            return;
        }
        info.addLose();
        ranks.put(name, info);
    }

    public static void showRankers() {
        List<Info> sortedRanks = ranks.values().stream().sorted((info1, info2) -> {
            if (info1.win == info2.win) {
                return info1.lose - info2.lose;
            }
            return info2.win - info1.win;
        }).collect(Collectors.toList());

        for (int i = 0; i < sortedRanks.size(); i++) {
            Info ranker = sortedRanks.get(i);
            JLabel label = rankLabels.get(i);
            label.setText(ranker.toString());
        }
    }

    static class Info {

        private String name;
        private int win;
        private int lose;

        public Info(String name, int win, int lose) {
            this.name = name;
            this.win = win;
            this.lose = lose;
        }

        public void addWin() {
            this.win++;
        }

        public void addLose() {
            this.lose++;
        }

        @Override
        public String toString() {
            return name + " : " + win + " 승 " + lose + " 패 ";
        }
    }

    private static boolean validate(String name) {
        if (name == null) {
            return true;
        }
        return false;
    }
}
