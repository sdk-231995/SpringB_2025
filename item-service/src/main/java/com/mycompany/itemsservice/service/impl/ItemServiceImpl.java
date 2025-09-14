package com.mycompany.itemsservice.service.impl;

import com.mycompany.itemsservice.converter.ItemConverter;
import com.mycompany.itemsservice.dto.ItemDTO;
import com.mycompany.itemsservice.entity.ItemEntity;
import com.mycompany.itemsservice.repository.ItemRepository;
import com.mycompany.itemsservice.service.ItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemConverter itemConverter;
    @Override
    public ItemDTO addItem(ItemDTO itemDTO) {
        ItemEntity itemEntity = new ItemEntity();
        BeanUtils.copyProperties(itemDTO, itemEntity);

        itemEntity.setCreatedAt(LocalDateTime.now());
        itemEntity.setUpdatedAt(LocalDateTime.now());

        itemEntity = itemRepository.save(itemEntity);
        BeanUtils.copyProperties(itemEntity, itemDTO);
        itemDTO.set_id(itemEntity.getId());

        return itemDTO;
    }

    @Override
    public List<ItemDTO> allItems() {
        List<ItemEntity> itemEntities = itemRepository.findAll();
        List<ItemDTO> dtoList = null;
        if(!itemEntities.isEmpty()){
            ItemDTO itemDTO = null;
            dtoList = new ArrayList<>();
            for(ItemEntity ie : itemEntities){
                itemDTO = new ItemDTO();
                BeanUtils.copyProperties(ie, itemDTO);
                itemDTO.set_id(ie.getId());
                dtoList.add(itemDTO);
            }
        }
        return dtoList;
    }

    @Override
    public List<ItemDTO> searchItemByTitle(String title) {
        List<ItemEntity> itemEntities = itemRepository.findAllByTitleContains(title);
        List<ItemDTO> dtoList = null;
        if(!itemEntities.isEmpty()){
            dtoList = new ArrayList<>();
            for(ItemEntity ie : itemEntities){
                dtoList.add(itemConverter.convertEntity2Dto(ie));
            }
        }
        return dtoList;
    }

    @Override
    public ItemDTO getDetail(Long id) {
        Optional<ItemEntity> optIe = itemRepository.findById(id);
        ItemDTO itemDTO = null;
        if(!optIe.isEmpty()){
            itemDTO = itemConverter.convertEntity2Dto(optIe.get());
        }
        return itemDTO;
    }

    @Override
    public ItemDTO updateItem(Long id, ItemDTO itemDTO) {//full update
        Optional<ItemEntity> optIe = itemRepository.findById(id);
        if(optIe.isPresent()){
            ItemEntity ie = optIe.get();
            ie.setTitle(itemDTO.getTitle());
            ie.setDescription(itemDTO.getDescription());
            ie.setUpdatedAt(LocalDateTime.now());
            ie = itemRepository.save(ie);
            itemDTO = itemConverter.convertEntity2Dto(ie);
        }
        return itemDTO;
    }

    @Override
    public ItemDTO updateItemTitle(Long id, String title) {
        Optional<ItemEntity> optIe = itemRepository.findById(id);
        ItemDTO itemDTO = null;
        if(optIe.isPresent()){
            ItemEntity ie = optIe.get();
            ie.setTitle(title);
            ie.setUpdatedAt(LocalDateTime.now());
            ie = itemRepository.save(ie);
            itemDTO = itemConverter.convertEntity2Dto(ie);
        }
        return itemDTO;
    }

    @Override
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
