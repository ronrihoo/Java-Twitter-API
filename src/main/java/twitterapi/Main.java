package twitterapi;

public class Main {

    public static void main(String[] args) {
        //testApiCall();
    }

    public static void testApiCall() {
        String apiCallUrl = "https://api.twitter.com/1.1/trends/place.json?id=1";
        String consumerKey = "";
        String consumerSecret = "";
        TwitterApi twitterApi = new TwitterApi(consumerKey, consumerSecret);
        twitterApi.authenticate();
        twitterApi.apiCall(apiCallUrl);
        twitterApi.parseTopTenTwitterTrends();
        System.out.println("Trends: " + twitterApi.getTrendsStr());
    }

}
