package ru.justnero.sevsu.s6.toi.e4.main;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ru.justnero.sevsu.s6.toi.e3.main.GetKeyWords;
import ru.justnero.sevsu.s6.toi.e3.main.WordStatistic;

public final class AbstractGetter {

    private AbstractGetter() {
    }

    public static String getAbstract(String text, Map<String, WordStatistic> allData, int sentenceNumber) {
        Map<String, WordStatistic> keyWords;
        GlossaryMap glossaryMap;
        String[] textArray;

        List<String> list = Arrays.stream(text.split("[.!?]"))
                .filter(s -> s.length() > 0)
                .collect(Collectors.toList());
        textArray = new String[list.size()];
        list.toArray(textArray);

        keyWords = GetKeyWords.get(allData);
        glossaryMap = GlossaryMap.createFromText(textArray);

        return getText(textArray, getSentenceNumbers(keyWords, glossaryMap, sentenceNumber));
    }

    protected static LinkedHashSet<Integer> getSentenceNumbers(
            Map<String, WordStatistic> keyWords, GlossaryMap glossaryMap, int lineNumber) {

        LinkedHashSet<Integer> result = new LinkedHashSet<>();

        for (String key : keyWords.keySet()) {
            if (result.size() + glossaryMap.get(key).size() <= lineNumber || lineNumber == -1) {
                result.addAll(glossaryMap.get(key));
            } else {
                for (int index : glossaryMap.get(key)) {
                    if (!result.contains(index)) {
                        result.add(index);
                        if (result.size() == lineNumber) {
                            return result;
                        }
                    }
                }
            }
        }

//        return result;

        return result.stream()
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    protected static String getText(String[] text, LinkedHashSet<Integer> indexes) {
        return indexes.stream()
                .map(i -> new StringBuilder(text[i]))
                .reduce(new StringBuilder("\t"), (s1, s2) -> s1.append("\n\t").append(s2))
                .toString();
    }
}
