package web.financialmanagement.dto.transaction;

import java.util.List;

public record MonthOption(Integer number, String name) {
    private static final List<MonthOption> MONTHS = List.of(
            new MonthOption(1, "Janeiro"),
            new MonthOption(2, "Fevereiro"),
            new MonthOption(3, "Março"),
            new MonthOption(4, "Abril"),
            new MonthOption(5, "Maio"),
            new MonthOption(6, "Junho"),
            new MonthOption(7, "Julho"),
            new MonthOption(8, "Agosto"),
            new MonthOption(9, "Setembro"),
            new MonthOption(10, "Outubro"),
            new MonthOption(11, "Novembro"),
            new MonthOption(12, "Dezembro"));

    public static List<MonthOption> all() {
        return MONTHS;
    }

    public static String nameOf(int number) {
        return MONTHS.get(number - 1).name();
    }
}
