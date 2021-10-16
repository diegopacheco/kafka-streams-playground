import com.github.diegopacheco.kafka.streams.topology.LeaderboardTopology;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;

import java.util.Properties;

public class Main{
  public static void main(String args[]){
    Topology topology = LeaderboardTopology.build();

    // set the required properties for running Kafka Streams
    Properties config = new Properties();
    config.put(StreamsConfig.APPLICATION_ID_CONFIG, "dev");
    config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");

    // build the topology and start streaming!
    KafkaStreams streams = new KafkaStreams(topology, config);

    Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    System.out.println("Starting Stateful streams");
    streams.start();
  }
}
