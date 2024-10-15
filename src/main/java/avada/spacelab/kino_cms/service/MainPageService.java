package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.dto.MainPageInfoDto;
import avada.spacelab.kino_cms.model.entity.MainPageBanners;
import avada.spacelab.kino_cms.model.entity.MainPageBanners.Replacement;
import avada.spacelab.kino_cms.model.entity.MainPageInfo;
import avada.spacelab.kino_cms.model.entity.SeoBlock;
import avada.spacelab.kino_cms.model.mapper.MainPageInfoMapper;
import avada.spacelab.kino_cms.repository.MainPageBannersRepository;
import avada.spacelab.kino_cms.repository.MainPageInfoRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainPageService {
    private final MainPageBannersRepository  mainPageBannersRepository;
    private final MainPageInfoRepository mainPageInfoRepository;

    public MainPageService(
            @Autowired MainPageBannersRepository mainPageBannersRepository,
            @Autowired MainPageInfoRepository mainPageInfoRepository
    ) {
        this.mainPageBannersRepository = mainPageBannersRepository;
        this.mainPageInfoRepository = mainPageInfoRepository;
    }

    @Transactional
    public void deleteAllByReplacement(Replacement place) {
        mainPageBannersRepository.removeAllByPlaceEquals(place);
    }

    public List<MainPageBanners> getAllByReplacement(Replacement place) {
        return mainPageBannersRepository.findAllByPlace(place);
    }

    public MainPageBanners save(MainPageBanners page) {
        return mainPageBannersRepository.save(page);
    }

    public List<MainPageBanners> saveAll(List<MainPageBanners> mainPages) {
        return mainPageBannersRepository.saveAllAndFlush(mainPages);
    }

    public void saveInfo(MainPageInfo mainPageInfo) {
        mainPageInfoRepository.save(mainPageInfo);
    }

    public MainPageInfoDto getInfo() {
        Optional<MainPageInfo> infoOptional = mainPageInfoRepository.findById(1L);
        infoOptional.ifPresent(mainPageInfo -> {
            if(mainPageInfo.getSeoBlock() == null) {
                mainPageInfo.setSeoBlock(new SeoBlock());
            }
        });
        return infoOptional
                .map(MainPageInfoMapper.INSTANCE::fromEntityToDto)
                .orElse(MainPageInfoDto.EMPTY());
    }
}
