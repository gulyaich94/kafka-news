package com.gulyaich.news.kafkanews.controller;

import com.gulyaich.news.kafkanews.model.news.News;
import com.gulyaich.news.kafkanews.model.news.NewsResponse;
import com.gulyaich.news.kafkanews.service.NewsResponseService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class NewsController {

    @Value("${kafka.topic.news.request}")
    private String requestTopic;

    @Value("${kafka.topic.news.reply}")
    private String replyTopic;

    @Autowired
    private ReplyingKafkaTemplate<String, News, NewsResponse> replyKafkaTemplate;

    @Autowired
    private NewsResponseService newsResponseService;

    @PostMapping("/publish/")
    public void publishNews(@RequestBody News news) {

        ProducerRecord<String, News> record = new ProducerRecord<>(requestTopic, news);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, replyTopic.getBytes()));
        RequestReplyFuture<String, News, NewsResponse> sendAndReceive = replyKafkaTemplate.sendAndReceive(record);

        sendAndReceive.addCallback(new ListenableFutureCallback<ConsumerRecord<String, NewsResponse>>() {
            @Override
            public void onSuccess(ConsumerRecord<String, NewsResponse> result) {
                NewsResponse newsResponse = result.value();
                System.out.println("News response: " + newsResponse);
                newsResponseService.save(newsResponse);
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("Error: " + throwable.getMessage());
                throwable.printStackTrace();
            }
        });
    }

}
