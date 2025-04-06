package org.servlets;

import java.time.ZoneOffset;

public class ParseUTC {
    public static String parseUTC(String notFormatedUtc) {
        String raw = notFormatedUtc.replaceAll("(?i)(UTC|GMT)", "").replaceAll("\\s+", "");

        // перевірка на знак
        if (!raw.startsWith("+") && !raw.startsWith("-")) {
            raw = "+" + raw;
        }

        // Якщо є ":" повертаємо як є
        if (raw.matches("[+-]\\d{2}:\\d{2}")) {
            return ZoneOffset.of(raw).toString();
        }

        // Якщо: +2 → +02:00, +10 → +10:00
        if (raw.matches("[+-]?\\d{1,2}")) {
            int hours = Integer.parseInt(raw);
            return ZoneOffset.ofHours(hours).toString();
        }

        // Якщо формат +hhmm
        if (raw.matches("[+-]?\\d{3,4}")) {
            int hours = Integer.parseInt(raw.substring(0, raw.length() - 2));
            int minutes = Integer.parseInt(raw.substring(raw.length() - 2));
            return ZoneOffset.ofHoursMinutes(hours, minutes).toString();
        }
        throw new IllegalArgumentException("Невалидный формат для ZoneOffset: " + raw);
    }
}
