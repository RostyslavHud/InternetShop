package com.internetshop.config;

import com.internetshop.mysqlModel.User;
import com.internetshop.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Objects;

@Configuration
public class UserLocalResolver extends SessionLocaleResolver {

    @Autowired
    UserService userService;

    @SneakyThrows
    @Override
    public Locale resolveLocale(HttpServletRequest request) {

        Locale locale = (Locale) WebUtils.getSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
        if (Objects.nonNull(locale)) {
            return locale;
        }

        SecurityContext securityContext = SecurityContextHolder.getContext();
        String userName = securityContext.getAuthentication().getName();
        User user = userService.findByName(userName);

        if (user != null) {
            return Locale.forLanguageTag(user.getLanguage().getName());
        }

        return request.getLocale();
    }
}
