package avada.spacelab.kino_cms.service.admin;

import avada.spacelab.kino_cms.model.dto.admin.EditPageDto;

public interface EditPageService {

    EditPageDto getAbout();

    void saveAbout(EditPageDto aboutDto, String picturesJson);

    EditPageDto getCafeBar();

    void saveCafeBar(EditPageDto cafeDto, String picturesJson);

    EditPageDto getVipRoom();

    void saveVipRoom(EditPageDto vipRoomDto, String picturesJson);

    EditPageDto getAdvertising();

    void saveAdvertising(EditPageDto advertisingDto, String picturesJson);

    EditPageDto getChildRoom();

    void saveChildRoom(EditPageDto childDto, String picturesJson);

}
