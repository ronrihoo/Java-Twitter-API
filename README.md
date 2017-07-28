# Java Twitter API

[![Release](https://img.shields.io/github/release/ronrihoo/Java-Twitter-API/all.svg)](https://github.com/ronrihoo/Java-Twitter-API/releases)

A light Twitter API for Java.

## Setup

1. Either build the JAR or download it from the latest [release](https://github.com/ronrihoo/Java-Twitter-API/releases)

2. Create a folder, "lib", in the directory of a new/existing project

3. Move or copy the JAR file into the _lib_ folder

4. Add the JAR file to the build path

   IDEA: From the _Project_ window, select and right-click the _lib_ folder, then click **Add as Library...**

   Eclipse: From the _Package Explorer_, select and right-click the JAR file, then select **Build** and click **Add to Build Path**

## Usage

1. In a Java source file, import the API

```java
import twitterapi.TwitterApi;
```

2. Create an instance of the API using the consumer key and secret acquired from Twitter (pass both as strings)

```java
TwitterApi twitterApi = new TwitterApi(consumerKey, consumerSecret);
```

3. Invoke authentication

```java
twitterApi.authenticate();
```

4. Pass a query to the apiCall(String) method

```java
String apiCallUrl = "https://api.twitter.com/1.1/trends/place.json?id=1";
twitterApi.apiCall(apiCallUrl);
```

5. Parse top ten trends 

```java
twitterApi.parseTopTenTwitterTrends();
```

6. Get results

```java
String topTenTrends = "Trends: " + twitterApi.getTrendsStr();
```

## A Complete Example

```java
import twitterapi.TwitterApi;

public class Main {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        String apiCallUrl = "https://api.twitter.com/1.1/trends/place.json?id=1";
        String consumerKey = "place-key-here";
        String consumerSecret = "place-secret-here";
        TwitterApi twitterApi = new TwitterApi(consumerKey, consumerSecret);
        twitterApi.authenticate();
        twitterApi.apiCall(apiCallUrl);
        twitterApi.parseTopTenTwitterTrends();
        System.out.println("Trends: " + twitterApi.getTrendsStr());
    }

}
```

## API Reference

`authenticate(String)`

&nbsp;&nbsp;&nbsp;&nbsp;Connects to Twitter's web API service to authenticate credentials

`apiCall(String)`

&nbsp;&nbsp;&nbsp;&nbsp;Queries the Twitter web API based on the given URL and retrieves the results 

`parseTopTenTwitterTrends()`

&nbsp;&nbsp;&nbsp;&nbsp;Parses the top ten trends from the retrieved results

`getTrendsStr()`

&nbsp;&nbsp;&nbsp;&nbsp;Returns the retrieved/parsed results in a string

Note: This API includes more methods; however, this covers the majority of its functionality at this time.

## Contributing

All pull requests are welcome.