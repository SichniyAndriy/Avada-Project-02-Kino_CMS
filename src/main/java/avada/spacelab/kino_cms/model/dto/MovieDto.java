package avada.spacelab.kino_cms.model.dto;

import avada.spacelab.kino_cms.model.entity.MovieDetails;
import java.io.Serializable;
import java.util.Collections;
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
        Boolean has2D,
        Boolean has3D,
        Boolean hasImax,
        Integer ageCenz,
        MovieDetails movieDetails,
        SeoBlockDto seoBlock,
        List<MoviePictureDto> images,
        List<ScheduleDto> schedules
) implements Serializable {
    public MovieDto(
            Long id,
            String nativeTitle,
            String uaTitle,
            String description,
            String posterUrl,
            String trailerUrl,
            Boolean has2D,
            Boolean has3D,
            Boolean hasImax,
            Integer ageCenz,
            MovieDetails movieDetails,
            SeoBlockDto seoBlock,
            List<MoviePictureDto> images,
            List<ScheduleDto> schedules
    ) {
        this.id = id;
        this.nativeTitle = nativeTitle;
        this.uaTitle = uaTitle;
        this.description = description;
        this.posterUrl = posterUrl;
        this.trailerUrl = trailerUrl;
        this.has2D = has2D;
        this.has3D = has3D;
        this.hasImax = hasImax;
        this.ageCenz = ageCenz;
        this.movieDetails = movieDetails;
        this.seoBlock = seoBlock;
        this.images = images;
        this.schedules = schedules;
    }

    public static MovieDto EMPTY() {
        return new MovieDto(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                SeoBlockDto.EMPTY(),
                Collections.emptyList(),
                Collections.emptyList()
        );
    }
}
