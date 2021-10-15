package com.github.diegopacheco.kafka.streams.topology.test;

import com.github.diegopacheco.kafka.streams.model.Tweet;
import com.github.diegopacheco.kafka.streams.serde.SerdeService;
import com.github.diegopacheco.kafka.streams.topology.TwitterTopology;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.test.TestRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TwitterTopologyTest {

    private TopologyTestDriver testDriver;
    private TestInputTopic<byte[], Tweet> inputTopic;
    private TestOutputTopic<byte[], Tweet> outputTopic;

    @BeforeEach
    void setup() {
        Topology topology = TwitterTopology.build();

        // create a test driver. we will use this to pipe data to our topology
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
        testDriver = new TopologyTestDriver(topology, props);

        // create the test input topic
        inputTopic =
                testDriver.createInputTopic(
                        "tweets", Serdes.ByteArray().serializer(), new SerdeService().serializer());

        // create the test output topic
        outputTopic =
                testDriver.createOutputTopic(
                        "end-sink-topic",
                        Serdes.ByteArray().deserializer(),
                        new SerdeService().deserializer());
    }

    @Test
    void twitterEnrichmentTest() {
        Tweet tweet = new Tweet();
        tweet.setCreatedAt(System.currentTimeMillis());
        tweet.setId(123L);
        tweet.setLanguage("en");
        tweet.setRetweet(false);
        tweet.setText("this shaky stock market has rekindled interest in both bitcoin and ethereum");

        inputTopic.pipeInput(new byte[] {}, tweet);

        assertThat(outputTopic.isEmpty()).isFalse();

        List<TestRecord<byte[], Tweet>> outRecords = outputTopic.readRecordsToList();
        assertThat(outRecords).isNotNull();
        System.out.println("Result size is " + outRecords.size());

        Tweet record1 = outRecords.get(0).getValue();
        assertThat(record1.getAdditionalProps()).isNotNull();
    }

}
