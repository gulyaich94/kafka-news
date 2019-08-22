package com.gulyaich.news.kafkanews.dao;

import com.gulyaich.news.kafkanews.model.news.NewsResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsResponseDao extends CrudRepository<NewsResponse, Long> {

}
