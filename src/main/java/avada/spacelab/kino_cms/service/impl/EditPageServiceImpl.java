package avada.spacelab.kino_cms.service.impl;

import avada.spacelab.kino_cms.model.dto.EditPageDto;
import avada.spacelab.kino_cms.model.dto.EditPagePictureDto;
import avada.spacelab.kino_cms.model.entity.EditPage;
import avada.spacelab.kino_cms.model.entity.EditPage.EditPageType;
import avada.spacelab.kino_cms.model.entity.EditPagePicture;
import avada.spacelab.kino_cms.model.mapper.EditPageMapper;
import avada.spacelab.kino_cms.model.mapper.EditPagePictureMapper;
import avada.spacelab.kino_cms.repository.EditPageRepository;
import avada.spacelab.kino_cms.service.EditPageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditPageServiceImpl implements EditPageService {
    private EditPageRepository editPageRepository;

    public EditPageServiceImpl(
        @Autowired EditPageRepository editPageRepository
    ) {
        this.editPageRepository = editPageRepository;
    }

//=====================================\ - About - /=====================================\\

    @Override
    public EditPageDto getAbout() {
        Optional<EditPage> optionalAbout =
                editPageRepository.findByType(EditPageType.ABOUT);
        return optionalAbout
                .map(EditPageMapper.INSTANCE::fromEntityToDto)
                .orElse(EditPageDto.EMPTY());
    }

    @Override
    public void saveAbout(EditPageDto aboutDto, String picturesJson) {
        EditPage about = EditPageMapper.INSTANCE.fromDtoToEntity(aboutDto);
        setPictures(about, picturesJson);
        about.setType(EditPageType.ABOUT);
        editPageRepository.save(about);
    }

//=====================================\ - Cafe-bar - /=====================================\\

    @Override
    public EditPageDto getCafeBar() {
        Optional<EditPage> optionalCafeBar =
                editPageRepository.findByType(EditPageType.CAFE_BAR);
        return optionalCafeBar
                .map(EditPageMapper.INSTANCE::fromEntityToDto)
                .orElse(EditPageDto.EMPTY());
    }

    @Override
    public void saveCafeBar(EditPageDto cafeDto, String picturesJson) {
        EditPage cafeBar = EditPageMapper.INSTANCE.fromDtoToEntity(cafeDto);
        setPictures(cafeBar, picturesJson);
        cafeBar.setType(EditPageType.CAFE_BAR);
        editPageRepository.save(cafeBar);
    }

//=====================================\ - VipRoom - /=====================================\\

    @Override
    public EditPageDto getVipRoom() {
        Optional<EditPage> optionalVipRoom =
                editPageRepository.findByType(EditPageType.VIP_ROOM);
        return optionalVipRoom
                .map(EditPageMapper.INSTANCE::fromEntityToDto)
                .orElse(EditPageDto.EMPTY());
    }

    @Override
    public void saveVipRoom(EditPageDto vipRoomDto, String picturesJson) {
        EditPage vipRoom = EditPageMapper.INSTANCE.fromDtoToEntity(vipRoomDto);
        setPictures(vipRoom, picturesJson);
        vipRoom.setType(EditPageType.VIP_ROOM);
        editPageRepository.save(vipRoom);
    }

//=====================================\ - Advertising - /=====================================\\

    @Override
    public EditPageDto getAdvertising() {
        Optional<EditPage> optionalAdvertising =
                editPageRepository.findByType(EditPageType.ADVERTISING);
        return optionalAdvertising
                .map(EditPageMapper.INSTANCE::fromEntityToDto)
                .orElse(EditPageDto.EMPTY());
    }

    @Override
    public void saveAdvertising(EditPageDto vipRoomDto, String picturesJson) {
        EditPage advertising = EditPageMapper.INSTANCE.fromDtoToEntity(vipRoomDto);
        setPictures(advertising, picturesJson);
        advertising.setType(EditPageType.ADVERTISING);
        editPageRepository.save(advertising);
    }

//=====================================\ -Child room - /=====================================\\

    @Override
    public EditPageDto getChildRoom() {
        Optional<EditPage> optionalChildRoom = editPageRepository.findByType(EditPageType.CHILD_ROOM);
        return optionalChildRoom
                .map(EditPageMapper.INSTANCE::fromEntityToDto)
                .orElse(EditPageDto.EMPTY());
    }

    @Override
    public void saveChildRoom(EditPageDto childRoomDto, String picturesJson) {
        EditPage childRoom = EditPageMapper.INSTANCE.fromDtoToEntity(childRoomDto);
        setPictures(childRoom, picturesJson);
        childRoom.setType(EditPageType.CHILD_ROOM);
        editPageRepository.save(childRoom);
    }

//=====================================\ - Private block  - /=====================================\\

    private void setPictures(EditPage editPage, String picturesJson) {
        List<EditPagePicture> pictures = parsePicturesJson(picturesJson);
        editPage.setPictures(pictures);
        pictures.forEach(picture -> picture.setEditPage(editPage));
    }

    private List<EditPagePicture> parsePicturesJson(String picturesJson) {
        List<EditPagePictureDto> pictureDtos;
        try {
            pictureDtos = new ObjectMapper().readValue(
                    picturesJson,
                    new TypeReference<List<EditPagePictureDto>>() {}
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return pictureDtos.stream()
                .map(EditPagePictureMapper.INSTANCE::fromDtoToEntity)
                .toList();
    }
}
