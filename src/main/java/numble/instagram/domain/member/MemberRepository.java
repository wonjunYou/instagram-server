package numble.instagram.domain.member;

import java.util.Optional;
import numble.instagram.domain.member.vo.Identifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByIdentifier(Identifier identifier);
}
