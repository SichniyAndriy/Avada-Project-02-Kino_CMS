package avada.spacelab.kino_cms.service.admin;

import avada.spacelab.kino_cms.model.dto.admin.NewsDto;
import java.util.List;

public interface NewsService {

    List<NewsDto> getAllNews();

    NewsDto getById(long id);

    void deleteById(long id);

    void save(NewsDto newsDto);

}
