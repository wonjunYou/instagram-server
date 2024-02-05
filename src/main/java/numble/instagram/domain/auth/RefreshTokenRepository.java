package numble.instagram.domain.auth;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query("SELECT rt FROM RefreshToken rt JOIN FETCH rt.member m where rt.token= :token AND rt.isRevoked = false")
    Optional<RefreshToken> findByTokenAndIsRevokedFalse(final EncryptedToken token);

}
