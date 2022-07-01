package com.nowcoder.community;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class KafkaTests {
    @Autowired
    private kafkaProducer kafkaProducer;

    @Test
    public void  testKafka(){
        kafkaProducer.sendMessage("test","你好");
        kafkaProducer.sendMessage("test","在吗");

        try {
            Thread.sleep(1000*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
@Component
class kafkaProducer{
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendMessage(String topic,String content){
        kafkaTemplate.send(topic,content);
    }
}

@Component
class kafkaConsumer{

    @KafkaListener(topics={"test"})
    public void handleMessage(ConsumerRecord record){
        System.out.println(record.value());
    }
}
