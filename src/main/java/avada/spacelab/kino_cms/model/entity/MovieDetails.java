package avada.spacelab.kino_cms.model.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class MovieDetails {

    private String year;
    private String country;
    private String actors;
    private String directors;
    private String screenwriters;
    private String compositors;
    private String producers;
    private String genres;
    private String budget;
    private String time;


    public static MovieDetails EMPTY() {
        return new MovieDetails();
    }

}
