package twitterapi;

import org.json.JSONArray;
import twitterapi.json.JsonParser;
import twitterapi.json.JsonParserImpl;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;

public class TwitterApi {

    String appOnlyAuthUrl = "https://api.twitter.com/oauth2/token";

    String consumerKey = null;
    String consumerSecret = null;
    String encodedCombo = null;         // will not really be used in this project
    String base64EncodedCombo = null;
    String bearerToken = null;
    String trendsStr = null;
    ArrayList<String> trends = null;

    HttpsURLConnection conn = null;
    URL url = null;
    OutputStreamWriter outputStream = null;
    Scanner scanner = null;
    JsonParser jsonParser = null;

    public TwitterApi(String consumerKey, String consumerSecret) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.base64EncodedCombo = convertToBase64(
                concatKeyAndSecret(consumerKey, consumerSecret));
    }

    public void authenticate() {
        connect(appOnlyAuthUrl);
        try {
            setConnectionForBearerTokenRequest();
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        try {
            sendRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
        parseBearerToken();
    }

    public void apiCall(String apiCallUrl) {
        connect(apiCallUrl);
        try {
            setConnectionForApiCall();
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }

    public void connect(String urlStr) {
        try {
            url = new URL(urlStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            conn = (HttpsURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setConnectionForBearerTokenRequest() throws ProtocolException {
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty(host().a, host().b);
        conn.setRequestProperty(userAgent().a, userAgent().b);
        conn.setRequestProperty(auth().a, auth().b);
        conn.setRequestProperty(contentType().a, contentType().b);
        conn.setRequestProperty(contentLength().a, contentLength().b);
    }

    public void setConnectionForApiCall() throws ProtocolException {
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("GET");
        conn.setRequestProperty(host().a, host().b);
        conn.setRequestProperty(userAgent().a, userAgent().b);
        conn.setRequestProperty(authBearer().a, authBearer().b);
    }

    public String sendRequest() throws IOException {
        outputStream = new OutputStreamWriter(conn.getOutputStream());
        outputStream.write(grantType());
        outputStream.flush();
        return null;
    }

    public String read() {
        try {
            scanner = new Scanner(conn.getInputStream());
            return scanner.useDelimiter("\\Z").next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void parseBearerToken() {
        bearerToken = read();
        jsonParser = new JsonParserImpl(bearerToken);
        bearerToken = jsonParser.getString("access_token");
    }

    public void parseAllTwitterTrends() {
        trendsStr = "";
        trends = new ArrayList<>();
        jsonParser = new JsonParserImpl(read());
        jsonParser.gotoList("trends");
        for (int i = 0; i < jsonParser.getCurrentObjectSize(); i++) {
            trends.add(jsonParser.getCurrentArray().getJSONObject(i).getString("name"));
            trendsStr += jsonParser.getCurrentArray().getJSONObject(i).getString("name");
            if (i < jsonParser.getCurrentObjectSize() - 1) {
                trendsStr += ", ";
            }
        }
    }

    public void parseTopTenTwitterTrends() {
        trendsStr = "";
        trends = new ArrayList<>();
        jsonParser = new JsonParserImpl(read());
        jsonParser.gotoList("trends");
        for (int i = 0; i < 10; i++) {
            trends.add(jsonParser.getCurrentArray().getJSONObject(i).getString("name"));
            trendsStr += jsonParser.getCurrentArray().getJSONObject(i).getString("name");
            if (i < 9) {
                trendsStr += ", ";
            }
        }
    }

    private String concatKeyAndSecret(String key, String secret) {
        return key + ":" + secret;
    }

    private String convertToBase64(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    public Tuple<String, String> host() {
        return new Tuple("Host", "api.twitter.com");
    }

    public Tuple<String, String> userAgent() {
        return new Tuple("User-Agent", "twttrbot");
    }

    public Tuple<String, String> auth() {
        return new Tuple("Authorization", "Basic " + base64EncodedCombo);
    }

    public Tuple<String, String> authBearer() {
        return new Tuple("Authorization", "Bearer " + bearerToken);
    }

    public Tuple<String, String> contentType() {
        return new Tuple("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
    }

    public Tuple<String, String> contentLength() {
        return new Tuple("Content-Length", "29");
    }

    public String grantType() {
        return "grant_type=client_credentials";
    }

    public String getBearerToken() {
        return bearerToken;
    }

    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    public String getTrendsStr() {
        return trendsStr;
    }

    public ArrayList<String> getTrends() {
        return trends;
    }
}
