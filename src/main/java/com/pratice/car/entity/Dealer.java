package com.pratice.car.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@ToString(exclude = "cars")
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
@SequenceGenerator(name = "dealer_seq", sequenceName = "dealer_seq", allocationSize = 1, initialValue = 1)
// json igonre는 제외한다는 어노테이션
// 직렬화 프로세스 중에 cars필드를 무시하게 변경, 하이버네이트가 생성한 필드를 무시하도록 설정
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Dealer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dealer_seq")
    @Column(name = "dealer_id")
    private Long id;

    @Column
    private String name;
    @Column
    private String phone;
    @Column
    private String location;


    @Setter
    @OneToOne
    @JoinColumn(name="member_id")
    private Member member;

    @Setter
    @OneToMany(mappedBy = "dealer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Car> cars = new ArrayList<>();

}
