package ru.justnero.sevsu.s6.toi.e2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Algorithm {

    public static int interpolationSearch(int[] arr, int n) {
        int low = 0, high = arr.length - 1, mid;
        while (low <= high) {
            mid = low + (n - arr[low]) / (arr[high] - arr[low]) * (high - low);
            if (arr[mid] == n) {
                return mid;
            }
            if (arr[mid] > n) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    public static List<Integer> rabinSearch(String haystack, String needle) {
        List<Integer> result = new ArrayList<>();
        ExtendedString textHash, keyHash;
        textHash = new ExtendedString(haystack, needle.length());
        keyHash = new ExtendedString(needle);

        textHash.hashCode();
        keyHash.hashCode();

        while (textHash.hasNextHash()) {
            if (textHash.getHash() == keyHash.getHash() &&
                    haystack.substring(textHash.getStartPosition(), textHash.getStartPosition() + textHash.length).equals(needle)) {
//                System.out.println(haystack.substring(textHash.getStartPosition(), textHash.getStartPosition()+textHash.length));
                result.add(textHash.getStartPosition());
            }
            textHash.nextHash();
        }

        if (result.size() > 0) {
            return result;
        } else {
            return Collections.EMPTY_LIST;
        }
    }
}
