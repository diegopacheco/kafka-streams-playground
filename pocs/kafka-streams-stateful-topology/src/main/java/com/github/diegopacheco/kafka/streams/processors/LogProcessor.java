package com.github.diegopacheco.kafka.streams.processors;

import com.github.diegopacheco.kafka.streams.model.Tweet;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.Record;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class LogProcessor implements Processor<Object, byte[], Void, Void> {

    private Gson gson =
            new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

    @Override
    public void process(Record<Object, byte[]> record) {
        try{
            String raw = new String(record.value(), StandardCharsets.UTF_8);
            System.out.println(" [Raw Value] " + raw);

            Tweet tweet = gson.fromJson(raw, Tweet.class);
            System.out.println(new Date() + " [Log Processor] " + tweet);
        }catch(Exception e){
            System.out.println("Error to process common log");
            e.printStackTrace();
        }
    }
}
