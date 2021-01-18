package internetshop.service;

import internetshop.model.VerificationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VerificationTokenService {

    List<VerificationToken> findAllByNotExpireDate();

    List<VerificationToken> findAllByExpireDate();

    void deleteById(Long id);
}
