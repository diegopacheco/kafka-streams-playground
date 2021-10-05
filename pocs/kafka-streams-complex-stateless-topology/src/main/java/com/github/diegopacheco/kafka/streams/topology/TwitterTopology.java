package com.github.diegopacheco.kafka.streams.topology;


import com.github.diegopacheco.kafka.streams.model.Tweet;
import com.github.diegopacheco.kafka.streams.serde.SerdeService;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;

import java.util.Arrays;
import java.util.List;

public class TwitterTopology {

    private static final List<String> currencies = Arrays.asList("bitcoin", "ethereum");

    public static Topology build() {
        StreamsBuilder builder = new StreamsBuilder();

        // start streaming tweets using our custom value serdes. Note: regarding
        // the key serdes (Serdes.ByteArray()), if could also use Serdes.Void()
        // if we always expect our keys to be null
        KStream<byte[], Tweet> stream =
                builder.stream("tweets", Consumed.with(Serdes.ByteArray(), new SerdeService()));
        stream.print(Printed.<byte[], Tweet>toSysOut().withLabel("tweets-stream"));

        // filter out retweets
        KStream<byte[], Tweet> filtered =
                stream.filterNot(
                        (key, tweet) -> {
                            return tweet.isRetweet();
                        });

        // match all tweets that specify English as the source language
        Predicate<byte[], Tweet> englishTweets = (key, tweet) -> tweet.getLanguage().equals("en");

        // match all other tweets
        Predicate<byte[], Tweet> nonEnglishTweets = (key, tweet) -> !tweet.getLanguage().equals("en");

        // branch based on tweet language
        KStream<byte[], Tweet>[] branches = filtered.branch(englishTweets, nonEnglishTweets);

        // English tweets
        KStream<byte[], Tweet> englishStream = branches[0];
        englishStream.print(Printed.<byte[], Tweet>toSysOut().withLabel("tweets-english"));

        // non-English tweets
        KStream<byte[], Tweet> nonEnglishStream = branches[1];
        nonEnglishStream.print(Printed.<byte[], Tweet>toSysOut().withLabel("tweets-non-english"));

        // for non-English tweets, translate the tweet text first.
        KStream<byte[], Tweet> translatedStream =
                nonEnglishStream.mapValues(
                        (tweet) -> {
                            tweet.setText("Translated " + tweet.getText());
                            return tweet;
                        });

        // merge the two streams
        KStream<byte[], Tweet> merged = englishStream.merge(translatedStream);

        merged.to(
                "end-sink-topic",
                Produced.with(Serdes.ByteArray(), new SerdeService()));

        return builder.build();
    }
}