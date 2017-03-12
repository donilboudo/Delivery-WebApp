package com.ilboudofabrice.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ilboudofabrice.dao.interfaces.DeliveryDAO;
import com.ilboudofabrice.domain.Delivery;
import com.ilboudofabrice.util.DeliveryConstants;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 * Created by filboudo on 2017-02-16.
 */
@Repository
public class DeliveryDAOImpl implements DeliveryDAO {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private Session getSession() {
        Session session;
        try
        {
            session = sessionFactory.getCurrentSession();
        }
        catch (HibernateException e)
        {
            session = sessionFactory.openSession();
        }

        return session;
    }

    public void saveDelivery(Delivery delivery) {
        getSession().save(delivery);
    }

    public List<Delivery> getDeliveries() {
        List<Delivery> deliveries = new ArrayList<Delivery>();
        try
        {
            deliveries.addAll(getSession().createQuery("from Delivery").list());

            return deliveries;
        }
        catch (Exception ex)
        {
            return deliveries;
        }
    }

    public List<Delivery> getDeliveriesByUserId(String userId) {
        return null;
    }

    public List<Delivery> getMyOpenDeliveries(String userId) {
        return null;
    }

    public Delivery getDeliveryById(String deliveryId) {
        return getSession().get(Delivery.class, deliveryId);
    }

    public void closeDelivery(String deliveryId, String receiveDate, String receiverReferences, String receiverComments) {
        Delivery delivery = getDeliveryById(deliveryId);
        if (delivery != null)
        {
            delivery.setReceiveDate(new Date(receiveDate));
            delivery.setReceiverReferences(receiverReferences);
            delivery.setReceiverComments(receiverComments);
            delivery.setStatus(DeliveryConstants.FINISH);

            getSession().update(delivery);
        }
    }
}
