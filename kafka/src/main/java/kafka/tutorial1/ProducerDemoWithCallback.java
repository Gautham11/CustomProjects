package kafka.tutorial1;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ProducerDemoWithCallback {

    public static void main(String[] args) throws InterruptedException, IOException {

        final Logger logger = LoggerFactory.getLogger(ProducerDemoWithCallback.class);

        String bootstrapServers = "kafka-e300b68-gautham1189-7df9.aivencloud.com:18801";

        // create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(10));
        properties.put("security.protocol", "SSL");
        properties.put("ssl.endpoint.identification.algorithm", "");
        properties.put("ssl.truststore.location", "C:/openssl/bin/client.truststore.jks");
        properties.put("ssl.truststore.password", "secret");
        properties.put("ssl.keystore.type", "PKCS12");
        properties.put("ssl.keystore.location", "C:/openssl/bin/client.keystore.p12");
        properties.put("ssl.keystore.password", "secret");
        properties.put("ssl.key.password", "secret");
        //properties.setProperty("security.protocol", "SASL_SSL");
        //properties.setProperty("sasl.mechanism", "PLAIN");
        /*String propfile = "kafka.properties";
        InputStream inputStream = ProducerDemoWithCallback.class.getClassLoader().getResourceAsStream(propfile);
        if(inputStream != null) {
        	properties.load(inputStream);
        }*/
        // create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);


        for (int i=0; i<10; i++ ) {
            // create a producer record
            ProducerRecord<String, String> record =
                    new ProducerRecord<String, String>("first_topic", "hello world " + Integer.toString(i));

            // send data - asynchronous
            producer.send(record, new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    // executes every time a record is successfully sent or an exception is thrown
                    if (e == null) {
                        // the record was successfully sent
                        logger.info("Received new metadata. \n" +
                                "Topic:" + recordMetadata.topic() + "\n" +
                                "Partition: " + recordMetadata.partition() + "\n" +
                                "Offset: " + recordMetadata.offset() + "\n" +
                                "Timestamp: " + recordMetadata.timestamp());
                    } else {
                        logger.error("Error while producing", e);
                    }
                }
            });
        }
        logger.info("Before flushing");
        Thread.currentThread().sleep(50000);
        logger.info("After sleeping");
        // flush data
        //producer.flush();
        // flush and close producer
        producer.close();

    }
}
