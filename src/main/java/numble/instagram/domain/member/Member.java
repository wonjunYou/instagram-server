package numble.instagram.domain.member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.instagram.common.entity.TimeBaseEntity;
import numble.instagram.domain.member.vo.EncodedPassword;
import numble.instagram.domain.member.vo.Identifier;
import numble.instagram.domain.member.vo.Password;
import numble.instagram.domain.memberprofile.MemberProfile;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Identifier identifier;

    @Embedded
    private EncodedPassword password;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "memberprofile_id", nullable = false, unique = true)
    private MemberProfile memberProfile;

    @Builder
    public Member(Identifier identifier, EncodedPassword password, MemberProfile memberProfile) {
        this.identifier = identifier;
        this.password = password;
        this.memberProfile = memberProfile;
    }

    public boolean isPasswordMismatch(Password password) {
        return this.password.isMismatch(password);
    }
}
