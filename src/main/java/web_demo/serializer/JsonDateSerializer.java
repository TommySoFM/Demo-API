package web_demo.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class JsonDateSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime dateTime, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        ZoneId HKZoneId = ZoneId.of("Asia/Hong_Kong");

        ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, HKZoneId);
        String formattedDate = zonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        gen.writeString(formattedDate);
    }
}
