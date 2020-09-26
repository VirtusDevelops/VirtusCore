package eu.virtusdevelops.virtuscore.utils;


import com.google.common.base.Strings;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class TextUtil {

    public static String colorFormat(String message){
        return HexUtil.colorify(message);
    }

    public static List<String> colorFormatList(List<String> list){
        List<String> formated = new ArrayList<>();
        for(String string : list){
            formated.add(colorFormat(string));
        }
        return formated;
    }

    public static List<String> formatList(List<String> list, String... replacements){
        List<String> newList = new ArrayList<>();
        for(String st : list){
            for(String replace : replacements ) {
                String[] data = replace.split(":");
                st = st.replace(data[0], data[1]);
            }
            newList.add(colorFormat(st));
        }
        return newList;
    }

    public static String formatString(String name, String... replacements){
        for(String replace : replacements ) {
            String[] data = replace.split(":");
            name = name.replace(data[0], data[1]);
        }
        return colorFormat(name);
    }

    public static String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor,
                                 ChatColor notCompletedColor) {

        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars)
                + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }

}
