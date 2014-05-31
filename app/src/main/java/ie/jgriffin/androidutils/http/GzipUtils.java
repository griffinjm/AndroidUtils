package ie.jgriffin.androidutils.http;

import android.util.Log;

import org.apache.http.HttpEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

/**
 * Created by JGriffin on 31/05/2014.
 */
public class GzipUtils {
    private static final String UTF = "UTF-8";
    private static final String TAG = "GzipUtils";

    public static String unGzip(HttpEntity entity){
        StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader;
        String charset = UTF;
        String line;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(entity.getContent()), charset));
            line  = bufferedReader.readLine();

            while (line != null){
                inputStringBuilder.append(line);
                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        return inputStringBuilder.toString();
    }
}
