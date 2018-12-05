package net.tsystems.util;

public class GeneralUtils {
    public static final int MAX_PAGE_RESULT = 10;

    public static int parseIntForPage(String str, int min, int max) {
        int pageInt;
        try {
            pageInt = Integer.valueOf(str);

            if (pageInt < min)
                pageInt = min;
            else if (pageInt > max)
                pageInt = max;
        } catch (NumberFormatException e) {
            pageInt = 1;
        }
        return pageInt;
    }
}
