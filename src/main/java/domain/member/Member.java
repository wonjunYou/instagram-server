package domain.member;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Embedded
    private Password password;

    private String nickname;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String telephone;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean status;

    private String imageUrl;

    @Builder
    public Member(String email, Password password, String nickname, String name, Gender gender,
        String telephone) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.gender = gender;
        this.telephone = telephone;
        this.role = Role.USER;
        this.status = false;
        this.imageUrl = null;
    }
}
