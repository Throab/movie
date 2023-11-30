package com.filmweb.mapper;

import com.filmweb.dto.VideoDto;
import com.filmweb.entity.History;
import com.filmweb.entity.Video;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class VideoMapper {
    public VideoDto toDto(Video entity) {
        if(entity != null){
            return new VideoDto(
                    entity.getId(),
                    entity.getTitle(),
                    entity.getHref(),
                    entity.getPoster(),
                    entity.getViews(),
                    entity.getShare(),
                    entity.getHeading(),
                    entity.getDirector(),
                    entity.getActor(),
                    entity.getCategory(),
                    entity.getDescription(),
                    entity.getPrice(),
                    entity.isActive(),
                    entity.getCreatedAt().toLocalDateTime(),
                    entity.getHistories().stream()
                            .filter(History::getIsLiked)
                            .toList().size(),
                    entity.getComments().size()
            );
        }
        return null;
    }
}
