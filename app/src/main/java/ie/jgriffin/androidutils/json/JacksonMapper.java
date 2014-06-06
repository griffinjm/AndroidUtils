package ie.jgriffin.androidutils.json;

import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by JGriffin on 06/06/2014.
 */
public class JacksonMapper {
    private static final String TAG = "JacksonMapper";
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    /**
     * Example method which deserializes a collection of objects of type Object
     * @param input JSON String representation of a collection of Objects
     * @return An ArrayList of Objects
     */
    public static ArrayList<Object> deserializeObjects(String input){
        ArrayList<Object> objects = new ArrayList<Object>();
        try {
            objects = mapper.readValue(input, mapper.getTypeFactory().constructCollectionType(ArrayList.class, Object.class));
        } catch (JsonMappingException e) {
            Log.e(TAG, e.getMessage());
        } catch (JsonParseException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return objects;
    }
}
