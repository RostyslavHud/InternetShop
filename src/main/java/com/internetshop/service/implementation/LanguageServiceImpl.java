package com.internetshop.service.implementation;

import com.internetshop.enums.Errors;
import com.internetshop.exception.ServiceException;
import com.internetshop.mysqlModel.Language;
import com.internetshop.mysqlRepository.LanguageRepository;
import com.internetshop.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service("languageService")
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    LanguageRepository languageRepository;

    @Override
    @Cacheable(value = "language")
    public Language findByName(String name) throws ServiceException {
        if (name.equals("und")) {
            name = "en";
        }
        return languageRepository.findByName(name).orElseThrow(() -> new ServiceException(Errors.LANGUAGE_NOT_FOUND));
    }
}
