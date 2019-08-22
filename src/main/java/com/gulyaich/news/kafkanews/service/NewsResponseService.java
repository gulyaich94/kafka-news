package com.gulyaich.news.kafkanews.service;

import com.gulyaich.news.kafkanews.dao.NewsResponseDao;
import com.gulyaich.news.kafkanews.model.news.NewsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsResponseService {

    @Autowired
    private NewsResponseDao newsResponseDao;

    public void save(NewsResponse newsResponse) {
        newsResponseDao.save(newsResponse);
    }
}
