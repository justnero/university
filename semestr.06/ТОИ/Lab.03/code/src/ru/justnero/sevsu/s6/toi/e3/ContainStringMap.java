package ru.justnero.sevsu.s6.toi.e3;

import java.util.LinkedHashMap;

public class ContainStringMap extends LinkedHashMap<String, Integer> {

    public Integer put(String key) {
        if (containsKey(key)) {
            return put(key, get(key) + 1);
        } else {
            return put(key, 1);
        }
    }
}
