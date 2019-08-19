package com.gulyaich.news.kafkanews.resourse;

import com.gulyaich.news.kafkanews.model.News;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/kafka")
@CrossOrigin(origins = "http://localhost:3000")
public class NewsResource {

    private static final String TOPIC = "kafka_news";

    @Autowired
    private ReplyingKafkaTemplate<String, News, News> replyingKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, News> kafkaTemplate;

    @Value("${kafka.topic.request-topic}")
    private String requestTopic;

    @Value("${kafka.topic.requestreply-topic}")
    private String requestReplyTopic;

    @PostMapping("/publish/")
    public void publishNews(@RequestBody News news) throws InterruptedException, ExecutionException {
//        kafkaTemplate.send(requestTopic, news);
        ProducerRecord<String, News> record = new ProducerRecord<String, News>(requestTopic, news);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));
        RequestReplyFuture<String, News, News> sendAndReceive = replyingKafkaTemplate.sendAndReceive(record);
//         confirm if producer produced successfully
//        SendResult<String, News> sendResult = sendAndReceive.getSendFuture().get();
//

        //print all headers
        //sendResult.getProducerRecord().headers()
        //.forEach(header -> System.out.println(header.key() + ":" + header.value().toString()));

        // get consumer record
//        ConsumerRecord<String, News> consumerRecord = sendAndReceive.get();
        // return consumer value
//        System.out.println( consumerRecord.value());
    }
}
