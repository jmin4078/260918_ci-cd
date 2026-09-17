package org.example.springtestci.infra;

import org.example.springtestci.domain.Flower;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("FlowerRepositoryImpl 단위 테스트")
class FlowerRepositoryImplTest {

    @Mock
    private FlowerJpaRepository flowerJpaRepository;

    @InjectMocks
    private FlowerRepositoryImpl flowerRepository;

    @Test
    @DisplayName("JPA 저장소의 꽃 개수를 반환한다")
    void count_returnsJpaRepositoryCount() {
        // given: 꽃 두 송이가 저장되어 있다
        given(flowerJpaRepository.count()).willReturn(2L);

        // when: 전체 꽃 개수를 조회한다
        long count = flowerRepository.count();

        // then: JPA 저장소 개수를 반환한다
        assertThat(count).isEqualTo(2L);
        then(flowerJpaRepository).should().count();
    }

    @Test
    @DisplayName("꽃을 엔티티로 변환해 저장한다")
    void save_convertsAndSavesFlower() {
        // given: 저장할 장미를 준비한다
        Flower rose = new Flower("장미", "빨강", 5_000);
        given(flowerJpaRepository.save(any(FlowerJpaEntity.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when: 장미를 저장한다
        Flower savedFlower = flowerRepository.save(rose);

        // then: 변환된 엔티티와 꽃을 검증한다
        ArgumentCaptor<FlowerJpaEntity> captor =
                ArgumentCaptor.forClass(FlowerJpaEntity.class);
        then(flowerJpaRepository).should().save(captor.capture());

        FlowerJpaEntity savedEntity = captor.getValue();
        assertThat(savedEntity.getId()).isNull();
        assertThat(savedEntity.getName()).isEqualTo("장미");
        assertThat(savedEntity.getColor()).isEqualTo("빨강");
        assertThat(savedEntity.getPrice()).isEqualTo(5_000);
        assertThat(savedFlower).isEqualTo(rose);
    }

    @Test
    @DisplayName("엔티티 목록을 꽃 목록으로 변환한다")
    void findAll_convertsEntitiesToFlowers() {
        // given: 꽃 엔티티 목록을 준비한다
        Flower rose = new Flower("장미", "빨강", 5_000);
        Flower tulip = new Flower("튤립", "노랑", 3_000);
        given(flowerJpaRepository.findAll()).willReturn(List.of(
                new FlowerJpaEntity(rose.name(), rose.color(), rose.price()),
                new FlowerJpaEntity(tulip.name(), tulip.color(), tulip.price())
        ));

        // when: 모든 꽃을 조회한다
        List<Flower> flowers = flowerRepository.findAll();

        // then: 도메인 꽃 목록을 반환한다
        assertThat(flowers).containsExactly(rose, tulip);
        then(flowerJpaRepository).should().findAll();
    }
}
