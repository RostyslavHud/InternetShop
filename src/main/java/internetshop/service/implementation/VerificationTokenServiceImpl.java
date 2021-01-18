package internetshop.service.implementation;

import internetshop.model.VerificationToken;
import internetshop.repository.VerificationTokenRepository;
import internetshop.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service("verificationService")
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Override
    public List<VerificationToken> findAllByNotExpireDate() {
        return verificationTokenRepository.findAllByNotExpireDate(LocalDateTime.now());
    }

    @Override
    public List<VerificationToken> findAllByExpireDate() {
        return verificationTokenRepository.findAllByExpireDate(LocalDateTime.now());
    }

    @Override
    public void deleteById(Long id) {
        verificationTokenRepository.deleteById(id);
    }
}
