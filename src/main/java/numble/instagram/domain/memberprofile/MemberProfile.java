package numble.instagram.domain.memberprofile;

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
import numble.instagram.common.entity.BaseEntity;
import numble.instagram.domain.member.GenderType;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberProfile extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    private String name;

    @Enumerated(EnumType.STRING)
    private GenderType genderType;

    private String phoneNumber;

    private boolean status;

    private String imageUrl;

    @Builder
    public MemberProfile(String email, String nickname, String name, GenderType genderType,
        String phoneNumber) {
        this.email = email;
        this.nickname = nickname;
        this.name = name;
        this.genderType = genderType;
        this.phoneNumber = phoneNumber;
        this.status = true;
    }
}
