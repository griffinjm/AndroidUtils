package ie.jgriffin.androidutils.json;

import android.util.Log;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Created by JGriffin on 27/06/2014.
 */
public class CustomDateSerializer extends JsonSerializer<GregorianCalendar> {
    
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    //dummy constructor for proguard
    public CustomDateSerializer(){}

    @Override
    public void serialize(GregorianCalendar gregorianCalendar, JsonGenerator jsonGenerator, SerializerProvider serializerProvider){
        String formattedDate = dateFormat.format(gregorianCalendar.getTime());
        try {
            jsonGenerator.writeString(formattedDate);
        } catch (JsonGenerationException e) {
            Log.e(this.getClass().getCanonicalName(), e.getMessage());
        } catch (IOException e) {
            Log.e(this.getClass().getCanonicalName(), e.getMessage());
        }
    }
}
