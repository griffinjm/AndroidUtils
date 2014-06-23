package ie.jgriffin.androidutils.json;

import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

import ie.jgriffin.androidutils.GenUtils;

/**
 * Created by JGriffin on 23/06/2014.
 */
public class CustomDotNetJsonDateToLongDeserializer extends JsonDeserializer<Long> {

    //dummy constructor for proguard
    public CustomDotNetJsonDateToLongDeserializer() {
    }

    @Override
    public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        try {
            return GenUtils.getLongFromString(jsonParser.getText());
        } catch (JsonParseException e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage());
        } catch (IOException e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage());
        }
        return 0L;
    }
}