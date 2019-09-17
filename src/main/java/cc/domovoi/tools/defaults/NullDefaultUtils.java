package cc.domovoi.tools.defaults;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class NullDefaultUtils {

    public static Boolean defaultBooleanValue;

    public static Double defaultDoubleValue;

    public static Integer defaultIntegerValue;

    public static Long defaultLongValue;

    public static Short defaultShortValue;

    public static Byte defaultByteValue;

    public static Float defaultFloatValue;

    public static String defaultStringValue;

    public static BigInteger defaultBigIntegerValue;

    public static BigDecimal defaultBigDecimalValue;

    public static Supplier<? extends LocalDateTime> defaultLocalDateTimeValue;

    public static Supplier<? extends LocalDate> defaultLocalDateValue;

    public static Supplier<? extends LocalTime> defaultLocalTimeValue;

    static {
        defaultBooleanValue = false;
        defaultDoubleValue = 0.0;
        defaultIntegerValue = 0;
        defaultLongValue = 0L;
        defaultShortValue = 0;
        defaultByteValue = 0;
        defaultFloatValue = 0.0f;
        defaultStringValue = "";
        defaultBigIntegerValue = BigInteger.ZERO;
        defaultBigDecimalValue = BigDecimal.ZERO;
        defaultLocalDateTimeValue = LocalDateTime::now;
        defaultLocalDateValue = LocalDate::now;
        defaultLocalTimeValue = LocalTime::now;
    }

    public static <T> T defaultValue(T value, T zero) {
        return Objects.nonNull(value) ? value : zero;
    }

    public static <T> T lazyDefaultValue(T value, Supplier<? extends T> zero) {
        return Objects.nonNull(value) ? value : zero.get();
    }

    public static <T extends DefaultValueInterface<T>> T defaultValueC(T value, Class<T> clazz) throws InstantiationException, IllegalAccessException {
        return Objects.nonNull(value) ? value : clazz.newInstance().defaultValue();
    }

    public static Boolean defaultBooleanValue(Boolean value) {
        return defaultValue(value, defaultBooleanValue);
    }

    public static Double defaultDoubleValue(Double value) {
        return defaultValue(value, defaultDoubleValue);
    }

    public static Integer defaultIntegerValue(Integer value) {
        return defaultValue(value, defaultIntegerValue);
    }

    public static Long defaultLongValue(Long value) {
        return defaultValue(value, defaultLongValue);
    }

    public static Short defaultShortValue(Short value) {
        return defaultValue(value, defaultShortValue);
    }

    public static Byte defaultByteValue(Byte value) {
        return defaultValue(value, defaultByteValue);
    }

    public static Float defaultFloatValue(Float value) {
        return defaultValue(value, defaultFloatValue);
    }

    public static String defaultStringValue(String value) {
        return defaultValue(value, defaultStringValue);
    }

    public static BigInteger defaultBigIntegerValue(BigInteger value) {
        return defaultValue(value, defaultBigIntegerValue);
    }

    public static BigDecimal defaultBigDecimalValue(BigDecimal value) {
        return defaultValue(value, defaultBigDecimalValue);
    }

    public static LocalDateTime defaultLocalDateTimeValue(LocalDateTime value) {
        return lazyDefaultValue(value, defaultLocalDateTimeValue);
    }

    public static LocalDate defaultLocalDateValue(LocalDate value) {
        return lazyDefaultValue(value, defaultLocalDateValue);
    }

    public static LocalTime defaultLocalTimeValue(LocalTime value) {
        return lazyDefaultValue(value, defaultLocalTimeValue);
    }

    public static <T> T nvl(Predicate<? super T> nullPredicate, T... value) {
        for (T v : value) {
            if (!nullPredicate.test(v)) {
                return v;
            }
        }
        return null;
    }

    public static <T> T lazyNvl(Predicate<? super T> nullPredicate, Supplier<? extends T>... value) {
        for (Supplier<? extends T> v : value) {
            T vv = v.get();
            if (!nullPredicate.test(vv)) {
                return vv;
            }
        }
        return null;
    }

    public static <T> T nvl(T... value) {
        return nvl(Objects::isNull, value);
    }

    public static <T> T lazyNvl(Supplier<? extends T>... value) {
        return lazyNvl(Objects::isNull, value);
    }

    public static <T, R> R nvl2(Predicate<? super T> nullPredicate, T v1, R v2, R v3) {
        return nullPredicate.test(v1) ? v3 : v2;
    }

    public static <T, R> R lazyNvl2(Predicate<? super T> nullPredicate, T v1, Supplier<? extends R> v2, Supplier<? extends R> v3) {
        return nullPredicate.test(v1) ? v3.get() : v2.get();
    }

    public static <T, R> R nvl2(T v1, R v2, R v3) {
        return nvl2(Objects::isNull, v1, v2, v3);
    }

    public static <T, R> R lazyNvl2(T v1, Supplier<? extends R> v2, Supplier<? extends R> v3) {
        return lazyNvl2(Objects::isNull, v1, v2, v3);
    }
}
