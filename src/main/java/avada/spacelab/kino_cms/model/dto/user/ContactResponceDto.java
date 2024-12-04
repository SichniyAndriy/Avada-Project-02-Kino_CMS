package avada.spacelab.kino_cms.model.dto.user;

public record ContactResponceDto(
        String title,
        String address,
        String coordinates,
        String logoUrl,
        String bannerUrl,
        String phone1,
        String phone2
) {
}
