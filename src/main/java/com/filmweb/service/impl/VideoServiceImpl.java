package com.filmweb.service.impl;

import com.filmweb.dao.VideoDao;
import com.filmweb.dto.VideoDto;
import com.filmweb.entity.Video;
import com.filmweb.mapper.VideoMapper;
import com.filmweb.service.VideoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Timestamp;
import java.util.List;

@ApplicationScoped
public class VideoServiceImpl implements VideoService {

    @Inject
    VideoDao videoDao;


    @Inject
    VideoMapper videoMapper;

    @Override
    public VideoDto findByHref(String href) {
        Video video = videoDao.findByHref(href);
        return videoMapper.toDto(video);
    }

    @Override
    public List<Video> findTrending(int limit) {
        return videoDao.findTrending(limit);
    }

    @Override
    public List<VideoDto> findAll(int page, int limit) {
        List<Video> videos = videoDao.findAll(page, limit);
        return videos.stream()
                .map(videoMapper::toDto)
                .toList();
    }

    @Override
    public long count() {
        return videoDao.count(true);
    }

    @Override
    public List<VideoDto> findAllDisabled(int page, int limit) {
        return videoDao.findAllDeletedVideos(page, limit).stream()
                .map(videoMapper::toDto)
                .toList();
    }

    @Override
    public long countDisabled() {
        return videoDao.count(false);
    }

    @Override
    public List<VideoDto> findByKeyword(String keyword) {
        List<Video> videos = videoDao.findByKeyword(keyword);
        return videos.stream()
                .map(videoMapper::toDto)
                .toList();
    }

    @Override
    public VideoDto create(String title, String href, String poster, String director, String actor, String category, String description, String formattedPrice, String content) {
        Long price = Long.parseLong(formattedPrice.replace(".", ""));
        Video video = videoDao.create(
                Video.builder()
                        .title(title)
                        .href(href)
                        .poster(poster)
                        .director(director)
                        .actor(actor)
                        .category(category)
                        .heading(description)
                        .price(price)
                        .description(content)
                        .createdAt(new Timestamp(System.currentTimeMillis()))
                        .isActive(Boolean.TRUE)
                        .views(0)
                        .share(0)
                        .build()
        );
        return videoMapper.toDto(video);
    }

    @Override
    public VideoDto update(String title, String href, String director, String actor, String category, String heading, String formattedPrice, String description) {
        Video video = videoDao.findByHref(href);
        video.setTitle(title);
        video.setDirector(director);
        video.setActor(actor);
        video.setCategory(category);
        video.setHeading(heading);
        Long price = Long.parseLong(formattedPrice.replace(".", ""));
        video.setPrice(price);
        video.setDescription(description);
        return videoMapper.toDto(videoDao.update(video));
    }

    @Override
    public VideoDto restore(String href) {
        Video video = videoDao.findByHref(href);
        video.setActive(Boolean.TRUE);
        return videoMapper.toDto(videoDao.update(video));
    }

    @Override
    public VideoDto delete(String href) {
        Video video = videoDao.findByHref(href);
        video.setActive(Boolean.FALSE);
        return videoMapper.toDto(videoDao.update(video));
    }
}
