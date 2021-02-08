package com.internetshop.security;

import com.internetshop.exception.ServiceException;
import com.internetshop.mysqlModel.User;
import com.internetshop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

@Slf4j
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {

        Locale locale = (Locale) WebUtils.getSessionAttribute(httpServletRequest,
                SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
        if (locale == null) {
            locale = new Locale(LocaleContextHolder.getLocale().toString());
        }

        String username = httpServletRequest.getParameter("username");

        try {
            User user = userService.findByName(username);
            if (Objects.nonNull(user)) {
                if (user.isAccountNonLocked()) {
                    userService.updateFailAttempts(username);
                    e = new CredentialsExpiredException(ResourceBundle.getBundle("messages", locale)
                            .getString("error.incorrect-password-or-username"));
                } else {
                    e = new LockedException(ResourceBundle.getBundle("messages", locale)
                            .getString("error.locked-account"));
                }
            }

        } catch (ServiceException serviceException) {
            log.error(serviceException.getErrors().getMessage());
        }
        super.setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
    }
}
