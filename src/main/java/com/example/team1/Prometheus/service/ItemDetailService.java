package com.example.team1.Prometheus.service;

import com.example.team1.Prometheus.entity.Item;
import com.example.team1.Prometheus.entity.ItemDeleteResponse;
import com.example.team1.Prometheus.entity.ItemDetailRequest;
import com.example.team1.Prometheus.entity.ItemDetailResponse;
import com.example.team1.Prometheus.repository.ItemDetailRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemDetailService {
    private final ItemDetailRepository itemDetailRepository;

    public ItemDetailResponse findById(long id) {
        return new ItemDetailResponse(itemDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id)));
    }


    @Transactional
    public ItemDetailResponse updateItem(long id, ItemDetailRequest request) {

        // 1. 엔티티를 데이터베이스에서 조회
        Item item = itemDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        // 2. 요청 객체의 내용을 엔티티에 매핑
        Item updatedItem = Item.builder()
                .itemId(item.getItemId())
                .userId(item.getUserId())
                .name(request.getName())
                .price(item.getPrice())
                .category(item.getCategory())
                .imagePath(item.getImagePath())
                .description(request.getDescription())
                .createdAt(item.getCreatedAt())
                .build();

        // 3. 업데이트된 엔티티를 저장
        itemDetailRepository.save(updatedItem);

        // 4. 업데이트된 엔티티를 응답 DTO로 반환
        return new ItemDetailResponse(updatedItem);
    }

    public void deleteItem(long id) {
        itemDetailRepository.deleteById(id);
    }

}
