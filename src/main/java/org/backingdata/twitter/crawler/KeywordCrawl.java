package org.backingdata.twitter.crawler;


import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class KeywordCrawl {

	public static void main(String[] args) {


        PrintWriter writer = null;
        try {
            writer = new PrintWriter("test.csv", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.println("cities");




        //GeoLocation Lyon = new GeoLocation(45.759491, 4.841083); // -> Lyon, France

        ConfigurationBuilder cb = new ConfigurationBuilder();
        //cb.setDebugEnabled(true).setJSONStoreEnabled(true);
		TwitterFactory tf = new TwitterFactory(cb.build());
		
		Twitter twitter = tf.getInstance();
        AccessToken accessToken = new AccessToken("1067069127016280064-kCd7kobkg5DpSwki6Pw9AVWcO2BWWG", "Asn6O3yCvQmYgzac05ZrolCxtX0Mv4fXUVqaPRckn5UMZ");
        twitter.setOAuthConsumer("dVyikw316kzOTXO0qwfuxg6kp", "iLOqZfykOxyg6EdB266OIE7GwRfUzr2nD1S4jQaW0RellrW43g");
		twitter.setOAuthAccessToken(accessToken);


        String[] keywords = {"I am sick", "Je suis mal"};


        for (String key : keywords) {
            Query query = new Query(key);
            query.count(100);
            //query.setGeoCode(Lyon, 100, Query.Unit.km);
            QueryResult result;
            try {
                result = twitter.search(query);
                Integer countTw = 1;
                System.out.println("Query result for " + key + ":");
                for (Status status : result.getTweets()) {
                    System.out.println(countTw++ + " > @" + status.getUser().getScreenName() + " (" + status.getCreatedAt().toString() + ") : " + status.getText() + " Location: " + status.getUser().getLocation() + "\n");
                    String location = status.getUser().getLocation();
                    if(location != null && !location.isEmpty()) writer.println(location);


                }
            } catch (TwitterException e) {
                System.out.println("Exception while searching for tweets by a query string: " + e.getMessage());
                e.printStackTrace();
            }

        }
        writer.close();


    }


}
