package cl.divap.modoaps.app.helpers.string;

import org.springframework.stereotype.Component;

@Component
public class HelperString {

    public static String toTitleCase(String text) {

        if (text == null || text.isEmpty()) {
            return text;
        }

        text = text.replaceAll("\\s+", " ").trim();

        StringBuilder converted = new StringBuilder();

        boolean convertNext = true;

        for (char ch : text.toCharArray()) {
            if (Character.isSpaceChar(ch)) {
                convertNext = true;
            } else if (convertNext) {
                ch = Character.toTitleCase(ch);
                convertNext = false;
            } else {
                ch = Character.toLowerCase(ch);
            }
            converted.append(ch);
        }

        return converted.toString();
    }

}
