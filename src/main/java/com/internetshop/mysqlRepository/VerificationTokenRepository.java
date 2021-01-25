package com.internetshop.mysqlRepository;

import com.internetshop.mysqlModel.VerificationToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

    VerificationToken findByToken(String verificationToken);

    @Query("select v from VerificationToken v where v.expiryDate > ?1")
    List<VerificationToken> findAllByNotExpireDate(LocalDateTime localDateTime);

    @Query("select v from VerificationToken v where v.expiryDate < ?1")
    List<VerificationToken> findAllByExpireDate(LocalDateTime localDateTime);
}
