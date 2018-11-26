package org.backingdata.twitter.crawler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.logging.Logger;

import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class KeywordCrawl {

	public static void main(String[] args) {


        PrintWriter writer = null;
        try {
            writer = new PrintWriter("log.txt", StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ConfigurationBuilder cb = new ConfigurationBuilder();
        //cb.setDebugEnabled(true).setJSONStoreEnabled(true);
		TwitterFactory tf = new TwitterFactory(cb.build());
		
		Twitter twitter = tf.getInstance();
        AccessToken accessToken = new AccessToken("1067069127016280064-kCd7kobkg5DpSwki6Pw9AVWcO2BWWG", "Asn6O3yCvQmYgzac05ZrolCxtX0Mv4fXUVqaPRckn5UMZ");
        twitter.setOAuthConsumer("dVyikw316kzOTXO0qwfuxg6kp", "iLOqZfykOxyg6EdB266OIE7GwRfUzr2nD1S4jQaW0RellrW43g");
		twitter.setOAuthAccessToken(accessToken);


        String[] keywords = {"sick", "bad", "unwell"};


        for (String key : keywords) {
            Query query = new Query(key);
            query.count(100);
            QueryResult result;
            try {
                result = twitter.search(query);
                Integer countTw = 1;
                System.out.println("Query result for " + key + ":");
                for (Status status : result.getTweets()) {
                    System.out.println(countTw++ + " > @" + status.getUser().getScreenName() + " (" + status.getCreatedAt().toString() + ") : " + status.getText() + "\n");
                    writer.println(countTw + " > @" + status.getUser().getScreenName() + " (" + status.getCreatedAt().toString() + ") : " + status.getText() + "\n");

                }
            } catch (TwitterException e) {
                System.out.println("Exception while searching for tweets by a query string: " + e.getMessage());
                e.printStackTrace();
            }
        }


        if (writer != null) {
            writer.close();
		}
	}

}
