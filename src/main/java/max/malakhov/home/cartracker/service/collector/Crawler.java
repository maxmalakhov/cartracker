package max.malakhov.home.cartracker.service.collector;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Max Malakhov <malakhovbox@gmail.com>
 * @version 0.1
 * @since 2013-02-17
 */
public class Crawler {

    private static final String HEADER_CONTENT_TYPE =  "Content-Type";

    private static final String URL_PROVIDER =  "http://sklad.lada-direct.ru/order/";
    private static final String PARAM_REGION_NAME = "region";
    private static final String PARAM_REGION_VALUE = "46"; // Kursk
    private static final String PARAM_CITY_NAME = "city";
    private static final String PARAM_CITY_VALUE = "938189"; // Kursk
    private static final String PARAM_MODEL_NAME = "choosen_model";
    private static final String PARAM_MODEL_VALUE = "6557223"; // Granta

    private HttpClient client = new DefaultHttpClient();

    public StringBuffer getRawData() {
        initSession();
        setLocation();
        StringBuffer rawData = getData();

        client.getConnectionManager().shutdown();

        return rawData;
    }

    private void initSession() {
        try {
            HttpGet get = new HttpGet(URL_PROVIDER);

            HttpResponse response = client.execute(get);
            System.out.println("Location: Status = "+response.getStatusLine());

            EntityUtils.consume(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setLocation() {
        try {
            HttpPost post = new HttpPost(URL_PROVIDER);
            List<NameValuePair> nameValuePairs = new ArrayList(2);
            nameValuePairs.add(new BasicNameValuePair(PARAM_REGION_NAME, PARAM_REGION_VALUE));
            nameValuePairs.add(new BasicNameValuePair(PARAM_CITY_NAME, PARAM_CITY_VALUE));
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = client.execute(post);
            System.out.println("Location: Status = "+response.getStatusLine());

            EntityUtils.consume(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private StringBuffer getData() {
        StringBuffer data = new StringBuffer();
        try {
            HttpPost post = new HttpPost(URL_PROVIDER);
            List<NameValuePair> nameValuePairs = new ArrayList(1);
            nameValuePairs.add(new BasicNameValuePair(PARAM_MODEL_NAME, PARAM_MODEL_VALUE));
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = client.execute(post);
            System.out.println("Data: Status = "+response.getStatusLine());

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                InputStream instream = entity.getContent();
                try {
                    Header contentType = response.getHeaders(HEADER_CONTENT_TYPE)[0];
                    String charset = contentType.getValue().substring(contentType.getValue().indexOf('=')+1);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(instream, charset));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        data.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (RuntimeException e) {
                    post.abort();
                    e.printStackTrace();
                } finally {
                    instream.close();
                }
            }

            EntityUtils.consume(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}
