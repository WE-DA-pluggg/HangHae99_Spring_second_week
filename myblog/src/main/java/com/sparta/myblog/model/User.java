package com.sparta.myblog.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
@Table(name="User_info_myblog")
public class User {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id //PK 설정
    private Long id;

    // nullable: null 허용 여부
// unique: 중복 허용 여부 (false 일때 중복 허용)
    @Column(nullable = false, unique = true) //유니크 -> 중복허용
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true) //유니크 -> 중복허용
    private String email;



    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
