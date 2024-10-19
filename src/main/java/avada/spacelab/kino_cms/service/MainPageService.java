package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.MainPageInfoDto;
import avada.spacelab.kino_cms.model.entity.MainPageBanners;
import avada.spacelab.kino_cms.model.entity.MainPageBanners.Replacement;
import avada.spacelab.kino_cms.model.entity.MainPageInfo;
import java.util.List;

public interface MainPageService {
    void deleteAllByReplacement(Replacement place);

    List<MainPageBanners> getAllByReplacement(Replacement place);

    MainPageBanners save(MainPageBanners page);

    List<MainPageBanners> saveAll(List<MainPageBanners> mainPages);

    void saveInfo(MainPageInfo mainPageInfo);

    void saveInfoDto(MainPageInfoDto mainPageInfoDto);

    MainPageInfoDto getInfo();
}
