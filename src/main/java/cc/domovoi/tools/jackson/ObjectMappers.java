package cc.domovoi.tools.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class ObjectMappers {

    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static JavaTimeModule javaTimeModule;

    public static ObjectMapper objectMapper;

    static {
        initJavaTimeModule(jTM -> {});
        initObjectMapper(oM -> {});
    }

    private static void initJavaTimeModule(Consumer<? super JavaTimeModule> op) {
        javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
        op.accept(javaTimeModule);
    }

    private static void initObjectMapper(Consumer<? super ObjectMapper> op) {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(javaTimeModule);
        op.accept(objectMapper);
    }

    public static DateTimeFormatter dateTimeFormatter(String pattern) {
        dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter;
    }

    public static DateTimeFormatter dateFormatter(String pattern) {
        dateFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateFormatter;
    }

    public static DateTimeFormatter timeFormatter(String pattern) {
        timeFormatter = DateTimeFormatter.ofPattern(pattern);
        return timeFormatter;
    }

    public static JavaTimeModule javaTimeModule(Consumer<? super JavaTimeModule> op) {
        initJavaTimeModule(op);
        return javaTimeModule;
    }

    public static ObjectMapper objectMapper(Consumer<? super ObjectMapper> op) {
        initObjectMapper(op);
        return objectMapper;
    }

}
