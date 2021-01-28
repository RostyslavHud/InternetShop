package com.internetshop.service.implementation;

import com.internetshop.mysqlModel.VerificationToken;
import com.internetshop.mysqlRepository.VerificationTokenRepository;
import com.internetshop.service.VerificationTokenService;
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
