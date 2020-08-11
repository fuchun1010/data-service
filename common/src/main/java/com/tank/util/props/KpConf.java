package com.tank.util.props;

import com.tank.util.vo.conf.KpConfVo;
import com.tank.util.yaml.YmlExtractor;
import io.vavr.collection.Stream;
import lombok.NonNull;
import lombok.val;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Optional;
import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

/**
 * kafka producer configuration from yaml mapping to KpConfVo
 *
 * @author tank198435163.com
 */
public class KpConf {

  public KpConf(@NonNull final String fileName) {
    this.ymlExtractor = new YmlExtractor(fileName);
  }

  public Properties initProps() {
    val vo = this.ymlExtractor.extractConfig(KpConfVo.class);
    val props = new Properties();
    val serial = StringSerializer.class.getName();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, vo.toBrokerList());
    props.put(ProducerConfig.ACKS_CONFIG, vo.getAck());
    props.put(ProducerConfig.LINGER_MS_CONFIG, vo.getLinger());
    
    Optional.ofNullable(vo.getPartitionerClassName()).ifPresent(partitioner -> props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, partitioner));

    props.put(KEY_SERIALIZER_CLASS_CONFIG, serial);
    props.put(VALUE_SERIALIZER_CLASS_CONFIG, serial);

    return props;
  }

  public <T extends Serializer<T>> Properties initProps(@NonNull final Class<T> clazz) {
    val props = this.initProps();
    Stream.of(KEY_SERIALIZER_CLASS_CONFIG, VALUE_SERIALIZER_CLASS_CONFIG).forEach(props::remove);
    props.put(KEY_SERIALIZER_CLASS_CONFIG, clazz.getName());
    props.put(VALUE_SERIALIZER_CLASS_CONFIG, clazz.getName());
    return props;
  }

  private final YmlExtractor ymlExtractor;


}
