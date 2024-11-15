package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.admin.NewsDto;
import avada.spacelab.kino_cms.model.entity.News;
import avada.spacelab.kino_cms.model.entity.SeoBlock;
import avada.spacelab.kino_cms.model.mapper.admin.NewsMapper;
import avada.spacelab.kino_cms.repository.NewsRepository;
import avada.spacelab.kino_cms.service.admin.impl.NewsServiceImpl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;
    @InjectMocks
    private NewsServiceImpl newsService;

    private final long ID = 1L;


    @Test
    @DisplayName("Test getAllNews() with valid array")
    void test_getAllNews_WithValidArray() {
        List<News> newsList = new ArrayList<>();
        for (long i = 1; i <= 3; ++i) {
            newsList.add(getNews(i, true));
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
        News news = getNews(ID, true);
        news.setSeoBlock(new SeoBlock());
        when(newsRepository.findById(anyLong()))
                .thenReturn(Optional.of(news));

        NewsDto result = newsService.getById(1);

        assertEquals(getNews(ID, true).getId(), result.id());
        verify(newsRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Test getById() with not empty optional and empty SeoBlock")
    void test_getById_WithNotEmptyOptionalAndEmptySeoBlock() {
        when(newsRepository.findById(anyLong()))
                .thenReturn(Optional.of(getNews(1, true)));

        NewsDto result = newsService.getById(1);

        assertEquals(getNews(ID, true).getId(), result.id());
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

     @TestFactory
    @DisplayName("Test save()")
    List<DynamicNode> test_save() {
        return List.of(
                dynamicTest(
                        "With valid parameters",
                        () -> {
                            when(newsRepository.save(any(News.class)))
                                    .thenReturn(getNews(ID, true));

                            newsService.save(getNewsDto(ID, true));

                            verify(newsRepository, times(1))
                                    .save(any(News.class));
                        }
                ),
                 dynamicTest(
                        "With valid parameters and null date",
                        () -> {
                            when(newsRepository.save(any(News.class)))
                                    .thenReturn(getNews(ID, false));

                            newsService.save(getNewsDto(ID, false));

                            verify(newsRepository, times(2))
                                    .save(any(News.class));
                        }
                ),
                dynamicTest(
                        "With null parameter",
                        () -> {
                            assertThrows(
                                    NullPointerException.class,
                                    () -> newsService.save(null)
                            );
                            verify(newsRepository, never())
                                    .save(isNull());
                        }
                )
        );
    }

    private News getNews(long id, boolean withDate) {
        News news = new News();
        news.setId(id);
        news.setTitle("test " + id);
        news.setDate(withDate ? LocalDate.now() : null);
        return news;
    }

    private NewsDto getNewsDto(long id, boolean withDate) {
        News news = getNews(id, withDate);
        return NewsMapper.INSTANCE.fromEntityToDto(news);
    }

}
