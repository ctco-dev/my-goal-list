package lv.ctco.javaschool.goal.control;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagParser {
    private static final String[] PATTERN_LIST = new String[]{
            "change|become|i|language|field|apply|app|application|start|end|more|this|that",
            "maybe|year|years|one|two|three|four|five|six|seven|eight|nine|ten|from|i|a|and",
            "are|if|of|off|on|by|next|last|use|using|used|do|doing|what|determined|am|want",
            "an|wanted|goal|goals|achieve|me|my|in|out|above|wish|will|was|is|not|new|old",
            "get|got|going|to|for|have|has|the|can|will|be|about"
    };

    public static List<String> generateTagsList(String goal) {
        String noSymbols = goal.replaceAll("[$,.:;#@!?&*()1234567890]", "");
        for (String s : PATTERN_LIST) {
            Pattern stopWords = Pattern.compile("\\b(?:" + s + ")\\b\\s*", Pattern.CASE_INSENSITIVE);
            Matcher matcher = stopWords.matcher(noSymbols);
            noSymbols = matcher.replaceAll("");
        }
        return Arrays.asList(noSymbols.split(" "));
    }

    public static boolean isEqualSets(Set<?> set1, Set<?> set2) {
        if (set1 == null && set2 == null) {
            return true;
        }
        if (set1 == null || set2 == null) {
            return false;
        }
        if (set1.size() != set2.size()) {
            return false;
        }
        return set1.containsAll(set2);
    }

    public static boolean isEqualLists(List<?> list1, List<?> list2) {
        if (list1 == null && list2 == null) {
            return true;
        }
        if (list1 == null || list2 == null) {
            return false;
        }
        if (list1.size() != list2.size()) {
            return false;
        }
        return list1.containsAll(list2);
    }

}
