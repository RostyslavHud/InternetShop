package com.internetshop.service;

import com.internetshop.exception.ServiceException;
import com.internetshop.mysqlModel.Language;
import org.springframework.stereotype.Service;

@Service
public interface LanguageService {

    Language findByName(String name) throws ServiceException;
}
