package com.tank;

import com.tank.util.props.KpConf;
import com.tank.util.vo.health.CustomRecordMeta;
import com.tank.util.vo.health.Health;
import io.vavr.collection.Stream;
import io.vavr.control.Try;
import lombok.val;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.tank.util.json.JsonUtil.toJson;


/**
 * @author tank198435163.com
 */
public class SourceKafka {
  public static void main(final String[] args) {
    val kafkaConfig = new KpConf("kafka-client.yml");
    val producer = new KafkaProducer<String, String>(kafkaConfig.initProps());
    val topic = "demo";
    val records = IntStream.range(0, 100).boxed()
            .map(index -> new Health(String.valueOf(index), String.format("%s%d", "message", index)))
            .map(health -> new ProducerRecord<String, String>(topic, health.getKey(), toJson(health)))
            .collect(Collectors.toList());
    Stream.ofAll(records).map(producer::send)
            .map(d -> Try.of(d::get))
            .map(Try::get)
            .map(meta -> new CustomRecordMeta(meta.topic(), meta.partition(), meta.offset()))
            .map(CustomRecordMeta::toString)
            .forEach(System.out::println);
    records.clear();
  }
}
