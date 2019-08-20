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
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
@CrossOrigin(origins = "http://localhost:3000")
public class NewsResource {

    @Value("${kafka.topic.news.request}")
    private String requestTopic;

    @Value("${kafka.topic.news.reply}")
    private String replyTopic;

    @Autowired
    private ReplyingKafkaTemplate<String, News, News> replyKafkaTemplate;

    @PostMapping("/publish/")
    public String publishNews(@RequestBody News news) {

        ProducerRecord<String, News> record = new ProducerRecord<>(requestTopic, news);
        // set reply topic in header
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, replyTopic.getBytes()));
        // post in kafka topic
        RequestReplyFuture<String, News, News> sendAndReceive = replyKafkaTemplate.sendAndReceive(record);
        sendAndReceive.addCallback(new ListenableFutureCallback<ConsumerRecord<String, News>>() {
            @Override
            public void onSuccess(ConsumerRecord<String, News> result) {
                // get consumer record value
                News replyNews = result.value();
                System.out.println("Reply news: " + replyNews);
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                System.out.println("Error: " + throwable.getMessage());
            }
        });
        
        return "Success";
    }
}
