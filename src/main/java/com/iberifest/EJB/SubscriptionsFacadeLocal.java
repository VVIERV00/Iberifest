/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.EJB;

import com.iberifest.modelo.Subscriptions;

import javax.ejb.Local;
import java.util.List;

/**
 * @author adolfo
 */
@Local
public interface SubscriptionsFacadeLocal {

    void create(Subscriptions subscriptions);

    void edit(Subscriptions subscriptions);

    void remove(Subscriptions subscriptions);

    Subscriptions find(Object id);

    List<Subscriptions> findAll();

    List<Subscriptions> findRange(int[] range);

    int count();

}
