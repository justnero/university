package ru.justnero.sevsu.s6.toi.e3;

import java.util.Map;

import ru.justnero.sevsu.s6.toi.e3.main.WordStatistic;

public class MapToObjects {
    private MapToObjects() {
    }

    public static <K, V> Object[][] toObj(Map<K, V> data) {
        if (data.values().iterator().next() instanceof WordStatistic) {
            return toObjValueWordStatistic((Map<K, WordStatistic>) data);
        } else {
            return toObjSimplestMap(data);
        }
    }

    public static <K, V> Object[][] toObjSimplestMap(Map<K, V> data) {
        Object[][] rowDate = new Object[data.keySet().size()][2];
        int index = 0;

        for (K key : data.keySet()) {
            rowDate[index][0] = key;
            rowDate[index][1] = data.get(key);
            index++;
        }

        return rowDate;
    }


    public static <K> Object[][] toObjValueWordStatistic(Map<K, WordStatistic> data) {
        Object[][] rowDate = new Object[data.keySet().size()][4];
        int index = 0;

        for (K key : data.keySet()) {
            rowDate[index][0] = key;
            rowDate[index][1] = data.get(key).getRang();
            rowDate[index][2] = data.get(key).getNumber();
            rowDate[index][3] = data.get(key).getFrequency();

            index++;
        }

        return rowDate;
    }

}
