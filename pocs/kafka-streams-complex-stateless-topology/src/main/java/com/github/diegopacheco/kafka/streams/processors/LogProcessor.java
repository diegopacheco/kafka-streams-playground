package com.github.diegopacheco.kafka.streams.processors;

import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.Record;
import java.util.Date;

public class LogProcessor implements Processor<Void, String, Void, Void> {
    @Override
    public void process(Record<Void, String> record) {
        System.out.println(new Date() + " [Log Processor] " + record.value());
    }
}
