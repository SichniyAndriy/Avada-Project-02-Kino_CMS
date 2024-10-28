package avada.spacelab.kino_cms.service.impl;

import avada.spacelab.kino_cms.model.dto.NewsDto;
import avada.spacelab.kino_cms.model.entity.News;
import avada.spacelab.kino_cms.model.entity.SeoBlock;
import avada.spacelab.kino_cms.model.mapper.NewsMapper;
import avada.spacelab.kino_cms.repository.NewsRepository;
import avada.spacelab.kino_cms.service.NewsService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;

    public NewsServiceImpl(
            @Autowired NewsRepository newsRepository
    ) {
        this.newsRepository = newsRepository;
    }

    public List<NewsDto> getAllNews() {
        return newsRepository.findAll().stream()
                .sorted(Comparator.comparingLong(News::getId))
                .map(NewsMapper.INSTANCE::fromEntityToDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public NewsDto getById(long id) {
        Optional<News> newsOptional = newsRepository.findById(id);
        newsOptional.ifPresent(news -> {
            if (news.getSeoBlock() == null) {
                news.setSeoBlock(new SeoBlock());
            }});
        return newsOptional.map(NewsMapper.INSTANCE::fromEntityToDto).orElse(NewsDto.EMPTY());
    }

    public void deleteById(long id) {
        newsRepository.deleteById(id);
    }

    public void save(NewsDto newsDto) {
        News news = NewsMapper.INSTANCE.fromDtoToEntity(newsDto);
        newsRepository.save(news);
    }
}
