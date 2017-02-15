package ru.justnero.sevsu.s6.toi.e3.main;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetKeyWords {
    private static GetKeyWords ourInstance = new GetKeyWords();
    private Function<Map<String, WordStatistic>, Map<String, WordStatistic>> keyGetter;
    private static final Function<Map<String, WordStatistic>, Map<String, WordStatistic>>
            defaultKeyGetter = new HalfFrequency();

    private GetKeyWords() {
    }

    public static GetKeyWords getInstance() {
        return ourInstance;
    }

    public static void setKeyGetter(Function<Map<String, WordStatistic>, Map<String, WordStatistic>> keyGetter) {
        GetKeyWords instance = getInstance();
        Function<Map<String, WordStatistic>, Map<String, WordStatistic>> oldKeyGetter = instance.keyGetter;
        instance.keyGetter = (oldKeyGetter == null ? keyGetter : oldKeyGetter);
    }

    public static Map<String, WordStatistic> get(Map<String, WordStatistic> allDate) {
        Function<Map<String, WordStatistic>, Map<String, WordStatistic>> keyGetter = ourInstance.keyGetter;

        return (keyGetter == null ? defaultKeyGetter.apply(allDate) : keyGetter.apply(allDate));
    }


    public static class HalfFrequency implements Function<Map<String, WordStatistic>, Map<String, WordStatistic>> {
        @Override
        public Map<String, WordStatistic> apply(Map<String, WordStatistic> allData) {
            Map<String, WordStatistic> keyWords = new LinkedHashMap<>();
            List<Integer> distinctNumbers =
                    allData.values().stream()
                            .map(WordStatistic::getNumber)
                            .distinct()
                            .sorted()
                            .collect(Collectors.toList());

            int numberUnicFrequency = distinctNumbers.size();
            int minValueInBound, maxValueBound;
            minValueInBound = distinctNumbers.get(numberUnicFrequency / 4 + 1);
            maxValueBound = distinctNumbers.get(3 * numberUnicFrequency / 4);
            distinctNumbers.removeIf(integer -> integer < minValueInBound || integer > maxValueBound);

            allData.entrySet().stream()
                    .filter(stringWordStatisticEntry ->
                            distinctNumbers.contains(stringWordStatisticEntry.getValue().getNumber()))
                    .forEachOrdered(entry -> keyWords.put(entry.getKey(), entry.getValue()));

            return keyWords;
        }
    }
}
