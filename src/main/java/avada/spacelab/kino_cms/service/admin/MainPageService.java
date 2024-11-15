package avada.spacelab.kino_cms.service.admin;

import avada.spacelab.kino_cms.model.dto.admin.MainPageInfoDto;
import avada.spacelab.kino_cms.model.entity.MainPageBanner;
import avada.spacelab.kino_cms.model.entity.MainPageBanner.Replacement;
import avada.spacelab.kino_cms.model.entity.MainPageInfo;
import java.util.List;

public interface MainPageService {

    void deleteAllByReplacement(Replacement place);

    List<MainPageBanner> getAllByReplacement(Replacement place);

    MainPageBanner save(MainPageBanner page);

    List<MainPageBanner> saveAll(List<MainPageBanner> mainPages);

    void saveInfo(MainPageInfo mainPageInfo);

    void saveInfoDto(MainPageInfoDto mainPageInfoDto);

    MainPageInfoDto getInfo();

}
