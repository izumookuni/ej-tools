package cc.domovoi.tools.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;

public class DoubleSerializer extends JsonSerializer<Double> {

    private static DecimalFormat decimalFormat;

    static {
        decimalFormat = new DecimalFormat("#.00");
    }

    public static DecimalFormat decimalFormat(String pattern) {
        decimalFormat = new DecimalFormat(pattern);
        return decimalFormat;
    }

    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (Objects.nonNull(value)) {
            gen.writeString(decimalFormat.format(value));
        }
    }
}
