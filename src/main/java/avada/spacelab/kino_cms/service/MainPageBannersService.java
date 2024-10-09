package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.model.entity.MainPageBanners;
import avada.spacelab.kino_cms.model.entity.MainPageBanners.Replacement;
import avada.spacelab.kino_cms.repository.MainPageBannersRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainPageBannersService {
    private MainPageBannersRepository  mainPageBannersRepository;

    public MainPageBannersService(
            @Autowired MainPageBannersRepository mainPageBannersRepository
            ) {
        this.mainPageBannersRepository = mainPageBannersRepository;
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
}
