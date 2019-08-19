package com.gulyaich.news.kafkanews.listener;

import com.gulyaich.news.kafkanews.model.NewsResponse;
import com.gulyaich.news.kafkanews.service.NewsResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @Autowired
    private NewsResponseService newsResponseService;

    @KafkaListener(topics = "${kafka.topic.requestreply-topic}", groupId = "${kafka.consumergroup}")
    public void saveNewsSaveResult(NewsResponse newsResponse) {
        newsResponseService.save(newsResponse);
    }
}
