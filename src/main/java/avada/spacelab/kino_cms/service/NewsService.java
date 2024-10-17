package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.NewsDto;
import java.util.List;

public interface NewsService {
    List<NewsDto> getAllNews();

    NewsDto getById(long id);

    void deleteById(long id);

    void save(NewsDto newsDto);
}
