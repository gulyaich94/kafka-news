package com.gulyaich.news.kafkanews.resourse;

import com.gulyaich.news.kafkanews.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
@CrossOrigin(origins = "http://localhost:3000")
public class NewsResource {

    private static final String TOPIC = "kafka_news";

    @Autowired
    private KafkaTemplate<String, News> kafkaTemplate;

    @PostMapping("/publish/")
    public String publishNews(@RequestBody News news) {
        kafkaTemplate.send(TOPIC, news);
        return "Success";
    }
}
