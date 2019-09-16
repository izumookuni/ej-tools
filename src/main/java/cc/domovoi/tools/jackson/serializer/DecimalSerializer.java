package cc.domovoi.tools.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class DecimalSerializer extends JsonSerializer<BigDecimal> {

    private static int scale;

    private static RoundingMode roundingMode;

    static {
        scale = 2;
        roundingMode = RoundingMode.HALF_UP;
    }

    public static int scale(int newScale) {
        scale = newScale;
        return scale;
    }

    public static RoundingMode roundingMode(RoundingMode newRoundingMode) {
        roundingMode = newRoundingMode;
        return roundingMode;
    }

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (Objects.nonNull(value)) {
            gen.writeString(value.setScale(scale, roundingMode).toEngineeringString());
        }
    }
}
