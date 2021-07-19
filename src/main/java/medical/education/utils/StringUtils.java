package medical.education.utils;

public class StringUtils {
    public static String searchString(String string) {
        return string == null ? null : "%" + string.trim()
                .replaceAll(" ", "%") + "%";
    }
}
