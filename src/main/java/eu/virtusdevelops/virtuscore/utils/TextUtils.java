package eu.virtusdevelops.virtuscore.utils;


import com.google.common.base.Strings;
import net.md_5.bungee.api.ChatColor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TextUtils {

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


    public static String formatDecimals(Double number){
        DecimalFormat dformater = new DecimalFormat("###.##");

        String formated = dformater.format(number);

        return formated;

    }

    public static String formatNumbers(Double number){
        DecimalFormat dformater = new DecimalFormat("###,###,###,###,###.###");
        return dformater.format(number);
    }

    public static String formatNames(Double number){
        char[] suffix = {' ', 'k', 'M', 'B', 'T', 'P', 'E', 'Z', 'Y'};
        double numValue = number;
        int value = (int) Math.floor(Math.log10(numValue));
        int base = value / 3;
        if (value >= 3 && base < suffix.length) {
            return new DecimalFormat("#0.0").format(numValue / Math.pow(10, base * 3)) + suffix[base];
        } else {
            return new DecimalFormat("#,###.##").format(numValue);
        }
    }

    public static String formatValue(int format, double value){

        switch(format){
            case 1: return formatDecimals(value);
            case 2: return formatNumbers(value); // STOP
            case 3: return formatNames(value);
            default: return ""+value;
        }
    }

    public static String formatTime(double time) {
        final int days = (int)(time / 86400L);
        time -= 86400 * days;
        final int hours = (int)(time / 3600L);
        time -= 3600 * hours;
        final int minutes = (int)(time / 60L);
        time -= 60 * minutes;
        final int seconds = (int)time;
        final StringBuilder sb = new StringBuilder();

        if (days != 0) {
            sb.append(days).append("d ");
        }
        if (hours != 0) {
            sb.append(hours).append("h ");
        }
        if (minutes != 0) {
            sb.append(minutes).append("m ");
        }
        sb.append(seconds).append("s ");


        return sb.toString().trim();
    }

}
