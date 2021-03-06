package ie.jgriffin.androidutils.http;

import org.apache.http.HttpVersion;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

/**
 * Created by JGriffin on 24/05/2014.
 */
public class HighlanderClient extends DefaultHttpClient {

    private static final int CONNECTION_TIMEOUT = 40000;
    private static final int SOCKET_TIMEOUT = 40000;
    private static final int MAX_CONNECTIONS = 100;
    private static final int HTTP_PORT = 80;
    private static final int HTTPS_PORT = 443;

    public static final String DEFAULT_USER_AGENT_STRING = "Highlander";
    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    private static String userAgentString = DEFAULT_USER_AGENT_STRING;

    //There can be only one!
    private static HighlanderClient instance = null;

    private HighlanderClient(ClientConnectionManager ccm, HttpParams params) {
        super(ccm, params);
    }

    public static synchronized HighlanderClient getInstance() {
        if (instance == null) {
            HttpParams httpParameters = new BasicHttpParams();

            ConnManagerParams.setMaxTotalConnections(httpParameters, MAX_CONNECTIONS);
            HttpProtocolParams.setVersion(httpParameters, HttpVersion.HTTP_1_1);
            HttpConnectionParams.setConnectionTimeout(httpParameters, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpParameters, SOCKET_TIMEOUT);
            HttpProtocolParams.setUseExpectContinue(httpParameters, false);
            HttpProtocolParams.setUserAgent(httpParameters, userAgentString);

            SchemeRegistry schemeRegistry = new SchemeRegistry();

            SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
            sslSocketFactory.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
            schemeRegistry.register(new Scheme(HTTP, PlainSocketFactory.getSocketFactory(), HTTP_PORT));
            schemeRegistry.register(new Scheme(HTTPS, sslSocketFactory, HTTPS_PORT));

            ClientConnectionManager cm = new ThreadSafeClientConnManager(httpParameters, schemeRegistry);

            instance = new HighlanderClient(cm, httpParameters);
        }
        return instance;
    }




    public String getUserAgentString() {
        return userAgentString;
    }

    public synchronized void setUserAgentString(String userAgentString) {
        HighlanderClient.userAgentString = userAgentString;
        updateUAString();
    }

    private void updateUAString(){
        /*
        getInstance has been called at this stage
        so the instance is not null and the params have been created
         */
        HttpProtocolParams.setUserAgent(instance.getParams(), userAgentString);
    }


}
