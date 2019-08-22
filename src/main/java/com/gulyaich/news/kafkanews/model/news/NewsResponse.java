package com.gulyaich.news.kafkanews.model.news;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "NEWS_RESPONSE")
public class NewsResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "news_response_generator")
    @SequenceGenerator(name="news_response_generator", sequenceName = "SEQ_NEWS_RESPONSE_ID")
    @Column(name = "NEWS_RESPONSE_ID")
    private Long id;

    @NotNull
    @Column(name = "NEWS_STATUS")
    private NewsStatus newsStatus;

    @Column(name = "ERROR_REASON")
    private String errorReason;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "BODY")
    private String body;

    @Column(name = "DATE")
    private Date date;

    public NewsStatus getNewsStatus() {
        return newsStatus;
    }

    public void setNewsStatus(NewsStatus newsStatus) {
        this.newsStatus = newsStatus;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "NewsResponse{" +
                "id=" + id +
                ", newsStatus=" + newsStatus +
                ", errorReason='" + errorReason + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", date=" + date +
                '}';
    }
}
