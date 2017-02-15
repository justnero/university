package ru.justnero.sevsu.s6.toi.e4.main;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import ru.justnero.sevsu.s6.toi.e3.main.SplitScale;

public class GlossaryMap extends LinkedHashMap<String, LinkedHashSet<Integer>> {
    public static GlossaryMap createFromText(String[] text) {
        GlossaryMap res = new GlossaryMap();

        for (int i = 0; i < text.length; i++) {
            String line = text[i];
            final int index = i;

            Arrays.stream(line.split(SplitScale.BY_WORDS.getSplitRegex()))
                    .filter(s -> s.length() > 0)
                    .map(String::toLowerCase)
                    .forEach(s -> res.put(s, index));
        }

        return res;
    }

    @Override
    public LinkedHashSet<Integer> put(String key, LinkedHashSet<Integer> value) {
        if (!containsKey(key)) {
            return super.put(key, value);
        } else {
            get(key).addAll(value);
            return get(key);
        }
    }

    public LinkedHashSet<Integer> put(String key, int value) {
        LinkedHashSet<Integer> resList;
        if (!containsKey(key)) {
            resList = new LinkedHashSet<>();
            resList.add(value);

            return super.put(key, resList);
        } else {
            resList = get(key);
            resList.add(value);
            return resList;
        }
    }


}
