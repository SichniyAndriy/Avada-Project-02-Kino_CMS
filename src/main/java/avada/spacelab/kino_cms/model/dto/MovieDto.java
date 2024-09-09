package avada.spacelab.kino_cms.model.dto;

import avada.spacelab.kino_cms.model.entity.MovieDetails;
import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link avada.spacelab.kino_cms.model.entity.Movie}
 */
public record MovieDto(
        Long id,
        String nativeTitle,
        String uaTitle,
        String description,
        String posterUrl,
        String trailerUrl,
        String detailsUrl,
        Boolean has2D,
        Boolean has3D,
        Boolean hasImax,
        Integer ageCenz,
        MovieDetails movieDetails,
        SeoBlockDto seoBlock,
        List<MoviePictureDto> images,
        List<ScheduleDto> schedules
) implements Serializable {
}
