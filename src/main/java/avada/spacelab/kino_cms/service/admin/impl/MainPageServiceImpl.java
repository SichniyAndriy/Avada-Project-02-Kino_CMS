package avada.spacelab.kino_cms.service.admin.impl;

import avada.spacelab.kino_cms.model.dto.admin.MainPageInfoDto;
import avada.spacelab.kino_cms.model.entity.MainPageBanner;
import avada.spacelab.kino_cms.model.entity.MainPageBanner.Replacement;
import avada.spacelab.kino_cms.model.entity.MainPageInfo;
import avada.spacelab.kino_cms.model.entity.SeoBlock;
import avada.spacelab.kino_cms.model.mapper.admin.MainPageInfoMapper;
import avada.spacelab.kino_cms.repository.MainPageBannersRepository;
import avada.spacelab.kino_cms.repository.MainPageInfoRepository;
import avada.spacelab.kino_cms.service.admin.MainPageService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainPageServiceImpl implements MainPageService {

    private final MainPageBannersRepository  mainPageBannersRepository;
    private final MainPageInfoRepository mainPageInfoRepository;


    public MainPageServiceImpl(
            @Autowired MainPageBannersRepository mainPageBannersRepository,
            @Autowired MainPageInfoRepository mainPageInfoRepository
    ) {
        this.mainPageBannersRepository = mainPageBannersRepository;
        this.mainPageInfoRepository = mainPageInfoRepository;
    }

    public List<MainPageBanner> getAllByReplacement(Replacement place) {
        return mainPageBannersRepository.findAllByPlace(place);
    }

    public void deleteAllByReplacement(Replacement place) {
        mainPageBannersRepository.removeAllByPlaceEquals(place);
    }

    public MainPageBanner save(MainPageBanner page) {
        return mainPageBannersRepository.save(page);
    }

    public List<MainPageBanner> saveAll(List<MainPageBanner> mainPages) {
        return mainPageBannersRepository.saveAllAndFlush(mainPages);
    }

    @Override
    public void saveInfo(MainPageInfo mainPageInfo) {
        mainPageInfoRepository.save(mainPageInfo);
    }

    @Override
    public void saveInfoDto(MainPageInfoDto mainPageInfoDto) {
        MainPageInfo mainPageInfo =
                MainPageInfoMapper.INSTANCE.fromDtoToEntity(mainPageInfoDto);
        mainPageInfoRepository.save(mainPageInfo);
    }

    @Override
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
