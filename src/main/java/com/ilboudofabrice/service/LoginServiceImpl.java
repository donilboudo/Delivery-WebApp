package com.ilboudofabrice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ilboudofabrice.dao.LoginDAOImpl;
import com.ilboudofabrice.domain.User;
import com.ilboudofabrice.service.interfaces.LoginService;
import com.ilboudofabrice.service.interfaces.UserService;
import com.ilboudofabrice.util.RandomGUIDGenerator;

/**
 * Created by filboudo on 2017-03-10.
 */
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserService userService;

    @Autowired
    private LoginDAOImpl loginDAO;

    public void setLoginDAO(LoginDAOImpl loginDAO) {
        this.loginDAO = loginDAO;
    }

    @Transactional
    public String login(User user) {
        String sessionId = RandomGUIDGenerator.generateRandomGUID();
        loginDAO.login(sessionId, user);

        return sessionId;
    }

    @Transactional
    public boolean isLogin(String sessionId){
        return loginDAO.isLogin(sessionId);
    }

    @Transactional
    public void logout(String sessionId){
        loginDAO.logout(sessionId);
    }
}
