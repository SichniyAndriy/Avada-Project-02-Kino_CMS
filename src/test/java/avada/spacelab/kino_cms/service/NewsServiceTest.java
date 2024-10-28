package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.NewsDto;
import avada.spacelab.kino_cms.model.entity.News;
import avada.spacelab.kino_cms.model.entity.SeoBlock;
import avada.spacelab.kino_cms.repository.NewsRepository;
import avada.spacelab.kino_cms.service.impl.NewsServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;
    @InjectMocks
    private NewsServiceImpl newsService;

    @Test
    @DisplayName("Test getAllNews() with valid array")
    void test_getAllNews_WithValidArray() {
        List<News> newsList = new ArrayList<>();
        for (int i = 1; i <= 3; ++i) {
            newsList.add(getNews(i));
        }

        when(newsRepository.findAll()).thenReturn(newsList);

        List<NewsDto> result = newsService.getAllNews();

        assertEquals(3, result.size() );
        assertEquals(newsList.get(0).getId(), result.get(0).id());
        assertEquals(newsList.get(1).getId(), result.get(1).id());
        assertEquals(newsList.get(2).getId(), result.get(2).id());
        verify(newsRepository).findAll();
    }

    @Test
    @DisplayName("Test getAllNews() with empty array")
    void test_getAllNews_WithEmptyArray() {
        when(newsRepository.findAll())
                .thenReturn(new ArrayList<>());

        List<NewsDto> result = newsService.getAllNews();

        assertEquals(0, result.size() );
        verify(newsRepository).findAll();
    }

    @Test
    @DisplayName("Test getById() with not empty optional and not empty SeoBlock")
    void test_getById_WithNotEmptyOptionalAndNotEmptySeoBlock() {
        News news = getNews(1);
        news.setSeoBlock(new SeoBlock());
        when(newsRepository.findById(anyLong()))
                .thenReturn(Optional.of(news));

        NewsDto result = newsService.getById(1);

        assertEquals(getNews(1).getId(), result.id());
        verify(newsRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Test getById() with not empty optional and empty SeoBlock")
    void test_getById_WithNotEmptyOptionalAndEmptySeoBlock() {
        when(newsRepository.findById(anyLong()))
                .thenReturn(Optional.of(getNews(1)));

        NewsDto result = newsService.getById(1);

        assertEquals(getNews(1).getId(), result.id());
        assertNotNull(result.seoBlock());
        verify(newsRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Test getById() with empty optional")
    void test_getById_WithEmptyOptional() {
        when(newsRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        NewsDto result = newsService.getById(1);

        assertNotNull(result);
        assertNotNull(result.seoBlock());
        verify(newsRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Test deleteById()")
    void test_deleteById() {
        doNothing().when(newsRepository).deleteById(anyLong());

        newsService.deleteById(1);
        newsService.deleteById(1);

        verify(newsRepository, times(2)).deleteById(anyLong());
    }

    @Test
    @DisplayName("Test save() with valid parameter")
    void test_save_withValidParameter() {
        News news = getNews(1);
        news.setSeoBlock(new SeoBlock());

        when(newsRepository.save(any(News.class))).thenReturn(news);

        newsService.save(NewsDto.EMPTY());

        verify(newsRepository).save(any(News.class));
    }

    @Test
    @DisplayName("Test save() with valid parameter")
    void test_save_withInvalidParameter() {
        doThrow(NullPointerException.class).when(newsRepository).save(isNull());

        assertThrows(
                NullPointerException.class,
                () -> newsService.save(null)
        );
        verify(newsRepository).save(isNull());
    }

    private News getNews(long id) {
        News news = new News();
        news.setId(id);
        news.setTitle("test " + id);
        return news;
    }

}
