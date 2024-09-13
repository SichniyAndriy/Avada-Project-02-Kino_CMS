package avada.spacelab.kino_cms.model.mapper;

import avada.spacelab.kino_cms.model.dto.NewsDto;
import avada.spacelab.kino_cms.model.entity.News;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NewsMapper {
    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    NewsDto fromEntityToDto(News entity);
    News fromDtoToEntity(NewsDto dto);
}
