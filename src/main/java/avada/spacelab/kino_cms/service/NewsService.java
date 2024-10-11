package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.NewsDto;
import avada.spacelab.kino_cms.model.entity.News;
import avada.spacelab.kino_cms.model.entity.SeoBlock;
import avada.spacelab.kino_cms.model.mapper.NewsMapper;
import avada.spacelab.kino_cms.repository.NewsRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {
    private final NewsRepository newsRepository;

    public NewsService(
            @Autowired NewsRepository newsRepository
    ) {
        this.newsRepository = newsRepository;
    }

    public List<NewsDto> getAllNews() {
        return newsRepository.findAll().stream()
                .map(NewsMapper.INSTANCE::fromEntityToDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public NewsDto getById(long id) {
        Optional<News> newsById = newsRepository.findById(id);
        if (newsById.isPresent()) {
            News news = newsById.get();
            if (news.getSeoBlock() == null) {
                news.setSeoBlock(new SeoBlock());
            }
            return NewsMapper.INSTANCE.fromEntityToDto(news);
        }
        return null;
    }

    public void deleteById(long id) {
        newsRepository.deleteById(id);
    }
}
