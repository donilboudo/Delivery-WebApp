package com.ilboudofabrice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ilboudofabrice.domain.Delivery;
import com.ilboudofabrice.service.LoginServiceImpl;
import com.ilboudofabrice.service.interfaces.ClientService;
import com.ilboudofabrice.service.interfaces.DeliveryService;
import com.ilboudofabrice.service.interfaces.LoginService;
import com.ilboudofabrice.service.interfaces.UserService;
import com.ilboudofabrice.util.LoginConstants;

/**
 * Created by filboudo on 2017-02-16.
 */
@Controller
public class DeliveryController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private LoginService loginService;

    @RequestMapping(path = "/newDelivery", method = RequestMethod.GET)
    public String newDelivery(ModelMap modelMap) {
        modelMap.put("employees", userService.getUsers());
        modelMap.put("clients", clientService.getClients());

        return "newDelivery";
    }

    @RequestMapping(path = "/newDelivery", method = RequestMethod.POST)
    public String newDelivery(
            HttpSession httpSession,
            @RequestParam String clientId,
            String employeeId,
            String sendDate,
            String senderReferences,
            String senderComments,
            String receiver,
            String receiverAddress
    ) {
        if (loginService.isValidSession((String) httpSession.getAttribute(LoginServiceImpl.USER_SESSION)))
        {
            deliveryService.addNewDelivery(clientId, employeeId, sendDate, senderReferences, senderComments, receiver, receiverAddress);

            return "redirect:deliveries";
        }
        else
        {
            return LoginConstants.REDIRECT_LOGIN_PAGE;
        }
    }

    @RequestMapping(path = "/mobile/newDelivery", method = RequestMethod.POST)
    public void newDeliveryFromMobile(
            HttpSession httpSession,
            @RequestParam String clientId,
            String employeeId,
            String sendDate,
            String senderReferences,
            String senderComments,
            String receiver,
            String receiverAddress
    ) {
        if (loginService.isValidSession((String) httpSession.getAttribute(LoginServiceImpl.USER_SESSION)))
        {
            deliveryService.addNewDelivery(clientId, employeeId, sendDate, senderReferences, senderComments, receiver, receiverAddress);
        }
    }

    @RequestMapping(path = "/myDeliveries", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Delivery> myDeliveries(@RequestParam String userId) {
        if (userId != null && !userId.isEmpty())
        {
            return deliveryService.getMyDeliveries(userId);
        }

        return new ArrayList<Delivery>();
    }

    @RequestMapping(path = "/myOpenDeliveries", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Delivery> myOpenDeliveries(@RequestParam String userId) {
        if (userId != null && !userId.isEmpty())
        {
            return deliveryService.getMyOpenDeliveries(userId);
        }

        return new ArrayList<Delivery>();
    }

    @RequestMapping(path = "/delivery")
    public
    @ResponseBody
    Delivery getDeliveryById(@RequestParam String deliveryId) {
        if (deliveryId != null && !deliveryId.isEmpty())
        {
            return deliveryService.getDeliveryById(deliveryId);
        }
        return null;
    }

    @RequestMapping(path = "/closeDelivery/{id}", method = RequestMethod.GET)
    public String closeDelivery(@PathVariable String id, ModelMap modelMap, HttpSession httpSession) {
        if (loginService.isValidSession((String) httpSession.getAttribute(LoginServiceImpl.USER_SESSION)))
        {
            if (id != null && !id.isEmpty())
            {
                Delivery delivery = deliveryService.getDeliveryById(id);
                if (delivery != null)
                {
                    modelMap.put("delivery", delivery);
                }
            }

            return "endDelivery";
        }
        else
        {
            return LoginConstants.REDIRECT_LOGIN_PAGE;
        }
    }

    @RequestMapping(path = "/closeDelivery/{deliveryId}", method = RequestMethod.POST)
    public String closeDelivery(HttpSession httpSession, @PathVariable String deliveryId, @RequestParam String receiveDate, String receiverReferences, String receiverComments) {
        if (loginService.isValidSession((String) httpSession.getAttribute(LoginServiceImpl.USER_SESSION)))
        {
            if (deliveryId != null && !deliveryId.isEmpty())
            {
                deliveryService.closeDelivery(deliveryId, receiveDate, receiverReferences, receiverComments);
            }

            return "redirect:deliveries";
        }
        else
        {
            return LoginConstants.REDIRECT_LOGIN_PAGE;
        }
    }

    @RequestMapping(path = "/mobile/closeDelivery", method = RequestMethod.POST)
    public void closeDeliveryFromMobile(HttpSession httpSession, @RequestParam String deliveryId, String receiveDate, String receiverReferences, String receiverComments) {
        String userSessionId = (String) httpSession.getAttribute(LoginServiceImpl.USER_SESSION);
        if (loginService.isValidSession(userSessionId))
        {
            if (deliveryId != null && !deliveryId.isEmpty())
            {
                deliveryService.closeDelivery(deliveryId, receiveDate, receiverReferences, receiverComments);
            }
        }
    }

    @RequestMapping(path = "/deliveries/{id}")
    public void getMyDeliveries(@PathVariable String id) {

    }

    @RequestMapping(path = "/deliveries", method = RequestMethod.GET)
    public String getDeliveries(ModelMap modelMap, HttpSession httpSession) {
        if (loginService.isValidSession((String) httpSession.getAttribute(LoginServiceImpl.USER_SESSION)))
        {
            List<Delivery> deliveries = deliveryService.getDeliveries();
            if (deliveries.size() > 0)
            {
                modelMap.put("deliveries", deliveries);
            }

            return "deliveries";
        }
        else
        {
            return LoginConstants.REDIRECT_LOGIN_PAGE;
        }
    }

    @RequestMapping(path = "/openedDeliveries", method = RequestMethod.GET)
    public String openedDeliveries(ModelMap modelMap, HttpSession httpSession) {
        if (loginService.isValidSession((String) httpSession.getAttribute(LoginServiceImpl.USER_SESSION)))
        {
            modelMap.put("deliveries", deliveryService.getOpenedDeliveries());

            return "deliveries";
        }
        else
        {
            return LoginConstants.REDIRECT_LOGIN_PAGE;
        }
    }

    @RequestMapping(path = "/closedDeliveries", method = RequestMethod.GET)
    public String closedDeliveries(ModelMap modelMap, HttpSession httpSession) {
        if (loginService.isValidSession((String) httpSession.getAttribute(LoginServiceImpl.USER_SESSION)))
        {
            modelMap.put("deliveries", deliveryService.getClosedDeliveries());

            return "deliveries";
        }
        else
        {
            return LoginConstants.REDIRECT_LOGIN_PAGE;
        }
    }
}
