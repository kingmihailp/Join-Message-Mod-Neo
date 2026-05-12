package net.kingmihailp.joinmessagemod;

import net.minecraft.network.chat.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

/**
 * Parses chat strings with &#RRGGBB hex color codes and legacy & color codes into a Component.
 * Format: &#RRGGBB for hex, &a-&f / &0-&9 for legacy, &l &o &n &m &k for formatting, &r to reset.
 */
public class HexColorParser {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([0-9A-Fa-f]{6})");
    private static final Pattern LEGACY_PATTERN = Pattern.compile("&([0-9a-fklmnorA-FKLMNOR])");

    public static Component parse(String raw) {
        MutableComponent result = Component.empty();
        Style currentStyle = Style.EMPTY;

        // Split on any & code (hex or legacy)
        Pattern split = Pattern.compile("(&#[0-9A-Fa-f]{6}|&[0-9a-fklmnorA-FKLMNOR])");
        String[] parts = split.split(raw, -1);
        Matcher m = split.matcher(raw);

        List<String> codes = new ArrayList<>();
        while (m.find()) codes.add(m.group());

        // First segment (before any code)
        if (parts.length > 0 && !parts[0].isEmpty()) {
            result.append(Component.literal(parts[0]).withStyle(currentStyle));
        }

        for (int i = 0; i < codes.size(); i++) {
            String code = codes.get(i);
            currentStyle = applyCode(currentStyle, code);
            String text = (i + 1 < parts.length) ? parts[i + 1] : "";
            if (!text.isEmpty()) {
                result.append(Component.literal(text).withStyle(currentStyle));
            }
        }

        return result;
    }

    private static Style applyCode(Style style, String code) {
        if (code.startsWith("&#")) {
            int rgb = Integer.parseInt(code.substring(2), 16);
            return style.withColor(TextColor.fromRgb(rgb));
        }
        char c = Character.toLowerCase(code.charAt(1));
        return switch (c) {
            case '0' -> style.withColor(TextColor.fromRgb(0x000000));
            case '1' -> style.withColor(TextColor.fromRgb(0x0000AA));
            case '2' -> style.withColor(TextColor.fromRgb(0x00AA00));
            case '3' -> style.withColor(TextColor.fromRgb(0x00AAAA));
            case '4' -> style.withColor(TextColor.fromRgb(0xAA0000));
            case '5' -> style.withColor(TextColor.fromRgb(0xAA00AA));
            case '6' -> style.withColor(TextColor.fromRgb(0xFFAA00));
            case '7' -> style.withColor(TextColor.fromRgb(0xAAAAAA));
            case '8' -> style.withColor(TextColor.fromRgb(0x555555));
            case '9' -> style.withColor(TextColor.fromRgb(0x5555FF));
            case 'a' -> style.withColor(TextColor.fromRgb(0x55FF55));
            case 'b' -> style.withColor(TextColor.fromRgb(0x55FFFF));
            case 'c' -> style.withColor(TextColor.fromRgb(0xFF5555));
            case 'd' -> style.withColor(TextColor.fromRgb(0xFF55FF));
            case 'e' -> style.withColor(TextColor.fromRgb(0xFFFF55));
            case 'f' -> style.withColor(TextColor.fromRgb(0xFFFFFF));
            case 'l' -> style.withBold(true);
            case 'o' -> style.withItalic(true);
            case 'n' -> style.withUnderlined(true);
            case 'm' -> style.withStrikethrough(true);
            case 'k' -> style.withObfuscated(true);
            case 'r' -> Style.EMPTY;
            default  -> style;
        };
    }
}
