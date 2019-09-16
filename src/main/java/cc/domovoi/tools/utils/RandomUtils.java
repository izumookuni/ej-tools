package cc.domovoi.tools.utils;

import org.joor.Reflect;

import java.time.*;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.joor.Reflect.*;

public class RandomUtils {

    public static int stringLength = 10;

    private static Random r = new Random();

    public static Map<String, Supplier<Object>> randomGenerateMap;



    static {

        randomGenerateMap = new HashMap<>();

        randomGenerateMap.put("Long", r::nextLong);
        randomGenerateMap.put("Float", r::nextFloat);
        randomGenerateMap.put("Integer", r::nextInt);
        randomGenerateMap.put("Double", r::nextDouble);
        randomGenerateMap.put("Boolean", r::nextBoolean);

        randomGenerateMap.put("String", RandomUtils::randomString);
        randomGenerateMap.put("LocalTime", RandomUtils::randomLocalTime);
        randomGenerateMap.put("LocalDate", RandomUtils::randomLocalDate);
        randomGenerateMap.put("LocalDateTime", RandomUtils::randomLocalDateTime);

    }

    // 62
    private static String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";

    public static Integer randomSign() {
        return r.nextInt(2) == 1 ? 1 : -1;
    }

    public static Integer randomInteger(Integer from, Integer until) {
        return from + r.nextInt(until - from);
    }

    public static Integer randomInteger(Integer until) {
        return r.nextInt(until);
    }

    public static Integer randomInteger() {
        return r.nextInt();
    }

    public static Double randomDouble(Double from, Double until) {
        return from + (until - from) * r.nextDouble();
    }

    public static Double randomDouble(Double until) {
        return r.nextDouble() * until;
    }

    public static Double randomDouble() {
        return r.nextDouble();
    }

    public static Boolean randomBoolean() {
        return r.nextBoolean();
    }

    public static String randomString(Integer length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(str.charAt(r.nextInt(62)));
        }
        return sb.toString();
    }

    public static String randomString() {
        return randomString(stringLength);
    }

    public static LocalTime randomLocalTime() {
        return LocalTime.of(r.nextInt(24), r.nextInt(60), r.nextInt(60));
    }

    public static LocalDate randomLocalDate() {
        final int year = randomInteger(1970, 2031);
        final int month = r.nextInt(12) + 1;
        final int day;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = r.nextInt(31) + 1;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                day = r.nextInt(30) + 1;
                break;
            case 2:
                day = Year.isLeap(year) ? r.nextInt(29) + 1 : r.nextInt(28) + 1;
                break;
            default:
                day = 1;
                break;
        }
        return LocalDate.of(year, month, day);
    }

    public static LocalDateTime randomLocalDateTime() {
        return LocalDateTime.of(randomLocalDate(), randomLocalTime());
    }

    public static <E> E randomEntity(Class<E> beanClass, List<String> ignoreList) {
        try {
            E bean = beanClass.newInstance();
            Reflect reflect = on(bean);
            reflect.fields().forEach((field, r) -> {
                if (!ignoreList.contains(field)) {
                    Supplier<Object> objectSupplier = randomGenerateMap.get(r.type().getSimpleName());
                    if (Objects.nonNull(objectSupplier)) {
                        reflect.set(field, objectSupplier.get());
                    }
                }
            });
            return bean;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <E> E randomEntity(Class<E> beanClass, String... ignore) {
        return randomEntity(beanClass, Stream.of(ignore).collect(Collectors.toList()));
    }

}
