package org.example.springtestci.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("FlowerRepository 도메인 계약 단위 테스트")
class FlowerRepositoryTest {

    // 도메인 계층 목(mock)으로 계약만 검증
    private final FlowerRepository flowerRepository = mock(FlowerRepository.class);

    @Test
    @DisplayName("count 호출 시 저장된 꽃의 총 개수를 반환한다")
    void count_returnsTotalNumberOfFlowers() {
        // given: 저장소가 개수 3을 반환하도록 설정
        given(flowerRepository.count()).willReturn(3L);

        // when: count 메서드를 호출
        long count = flowerRepository.count();

        // then: 설정한 개수와 일치하는지 검증
        assertThat(count).isEqualTo(3L);
    }

    @Test
    @DisplayName("save 호출 시 전달한 꽃 정보를 그대로 반환한다")
    void save_returnsSavedFlower() {
        // given: 저장할 꽃 객체와 반환값 설정
        Flower flower = new Flower("장미", "빨강", 5000);
        given(flowerRepository.save(flower)).willReturn(flower);

        // when: save 메서드를 호출
        Flower savedFlower = flowerRepository.save(flower);

        // then: 반환된 객체가 원본과 동일한지 검증
        assertThat(savedFlower).isEqualTo(flower);
        verify(flowerRepository, times(1)).save(flower);
    }

    @Test
    @DisplayName("findAll 호출 시 저장된 모든 꽃 목록을 반환한다")
    void findAll_returnsAllFlowers() {
        // given: 저장소가 꽃 목록을 반환하도록 설정
        Flower rose = new Flower("장미", "빨강", 5000);
        Flower tulip = new Flower("튤립", "노랑", 3000);
        given(flowerRepository.findAll()).willReturn(List.of(rose, tulip));

        // when: findAll 메서드를 호출
        List<Flower> flowers = flowerRepository.findAll();

        // then: 반환된 목록이 예상 값과 일치하는지 검증
        assertThat(flowers).containsExactly(rose, tulip);
    }

    @Test
    @DisplayName("findAll 호출 시 저장된 꽃이 없으면 빈 목록을 반환한다")
    void findAll_returnsEmptyListWhenNoFlowers() {
        // given: 저장소가 빈 목록을 반환하도록 설정
        given(flowerRepository.findAll()).willReturn(List.of());

        // when: findAll 메서드를 호출
        List<Flower> flowers = flowerRepository.findAll();

        // then: 빈 목록이 반환되는지 검증
        assertThat(flowers).isEmpty();
    }
}