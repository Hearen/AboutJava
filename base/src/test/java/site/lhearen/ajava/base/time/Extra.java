package site.lhearen.ajava.base.time;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

import org.junit.Test;

public class Extra {
    // https://docs.oracle.com/javase/tutorial/datetime/iso/period.html
    @Test
    public void testLocalDateTimeDurationPrint() {
        LocalDateTime fromDateTime = LocalDateTime.of(1984, 12, 16, 7, 45, 55);
        LocalDateTime toDateTime = LocalDateTime.of(2014, 9, 10, 6, 40, 45);

        LocalDateTime tempDateTime = LocalDateTime.from( fromDateTime );

        long years = tempDateTime.until( toDateTime, ChronoUnit.YEARS);
        tempDateTime = tempDateTime.plusYears( years );

        long months = tempDateTime.until( toDateTime, ChronoUnit.MONTHS);
        tempDateTime = tempDateTime.plusMonths( months );

        long days = tempDateTime.until( toDateTime, ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays( days );


        long hours = tempDateTime.until( toDateTime, ChronoUnit.HOURS);
        tempDateTime = tempDateTime.plusHours( hours );

        long minutes = tempDateTime.until( toDateTime, ChronoUnit.MINUTES);
        tempDateTime = tempDateTime.plusMinutes( minutes );

        long seconds = tempDateTime.until( toDateTime, ChronoUnit.SECONDS);

        //prints: 29 years 8 months 24 days 22 hours 54 minutes 50 seconds.
        System.out.println( years + " years " +
                months + " months " +
                days + " days " +
                hours + " hours " +
                minutes + " minutes " +
                seconds + " seconds.");

    }

    @Test
    public void testLocalDateTimeDurationUntil() {
        LocalDateTime fromDateTime = LocalDateTime.of(1984, 12, 16, 7, 45, 55);
        LocalDateTime toDateTime = LocalDateTime.of(2014, 9, 10, 6, 40, 45);

        LocalDate tempDate = LocalDate.from( fromDateTime );

        long years = tempDate.until(toDateTime.toLocalDate()).get(ChronoUnit.YEARS);
        long months = tempDate.until(toDateTime.toLocalDate()).get(ChronoUnit.MONTHS);
        long days = tempDate.until(toDateTime.toLocalDate()).get(ChronoUnit.DAYS);

        LocalTime tempTime = LocalTime.from( fromDateTime );

        long hours = tempTime.until( toDateTime.toLocalTime(), ChronoUnit.HOURS);
        tempTime = tempTime.plusHours( hours );

        long minutes = tempTime.until( toDateTime, ChronoUnit.MINUTES);
        tempTime = tempTime.plusMinutes( minutes );

        long seconds = tempTime.until( toDateTime, ChronoUnit.SECONDS);

        //prints: 29 years 8 months 24 days 22 hours 54 minutes 50 seconds.
        System.out.println( years + " years " +
                months + " months " +
                days + " days " +
                hours + " hours " +
                minutes + " minutes " +
                seconds + " seconds.");

    }
    @Test
    public void testLocalDateTimeDurationPrintUsingChronoUnit() {
        LocalDateTime fromDateTime = LocalDateTime.of(1984, 12, 16, 7, 45, 55);
        LocalDateTime toDateTime = LocalDateTime.of(2014, 9, 10, 6, 40, 45);
        Long years = ChronoUnit.YEARS.between(fromDateTime, toDateTime);
        Long months = ChronoUnit.MONTHS.between(fromDateTime, toDateTime = toDateTime.minusYears(years));
        Long days = ChronoUnit.DAYS.between(fromDateTime, toDateTime = toDateTime.minusMonths(months));
        Long hours = ChronoUnit.HOURS.between(fromDateTime, toDateTime = toDateTime.minusDays(days));
        Long minutes = ChronoUnit.MINUTES.between(fromDateTime, toDateTime = toDateTime.minusHours(hours));
        Long seconds = ChronoUnit.SECONDS.between(fromDateTime, toDateTime.minusMinutes(minutes));
        System.out.println( years + " years " +
                months + " months " +
                days + " days " +
                hours + " hours " +
                minutes + " minutes " +
                seconds + " seconds.");
    }

    @Test
    public void testLocalDateTimeDurationPrintUsingCustomMethod() {
        LocalDateTime fromDateTime = LocalDateTime.of(1984, 12, 16, 7, 45, 55);
        LocalDateTime toDateTime = LocalDateTime.of(2014, 9, 10, 6, 40, 45);
        Long years = dateTimeDifference(fromDateTime, toDateTime, ChronoUnit.YEARS);
        Long months = dateTimeDifference(fromDateTime, toDateTime = toDateTime.minusYears(years), ChronoUnit.MONTHS);
        Long days = dateTimeDifference(fromDateTime, toDateTime = toDateTime.minusMonths(months), ChronoUnit.DAYS);
        Long hours = dateTimeDifference(fromDateTime, toDateTime = toDateTime.minusDays(days), ChronoUnit.HOURS);
        Long minutes = dateTimeDifference(fromDateTime, toDateTime = toDateTime.minusHours(hours), ChronoUnit.MINUTES);
        Long seconds = dateTimeDifference(fromDateTime, toDateTime.minusMinutes(minutes), ChronoUnit.SECONDS);
        System.out.println( years + " years " +
                months + " months " +
                days + " days " +
                hours + " hours " +
                minutes + " minutes " +
                seconds + " seconds.");
    }


    private Long dateTimeDifference(Temporal d1, Temporal d2, ChronoUnit unit) {
        return unit.between(d1, d2);
    }

    @Test
    public void testLocalToInstant() {
        LocalDate date = LocalDate.now();
        Instant instantDefault = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
        System.out.println(LocalDateTime.now().atZone(ZoneId.systemDefault()));
        System.out.println(instantDefault);

        Instant instantUtc = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);

        System.out.println(instantUtc);

        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }
}
