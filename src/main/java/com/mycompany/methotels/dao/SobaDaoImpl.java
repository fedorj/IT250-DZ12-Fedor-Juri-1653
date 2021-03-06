/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.dao;

import com.mycompany.methotels.entities.Soba;
import java.util.List;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Fedor
 */
public class SobaDaoImpl implements SobaDao {

    @Inject
    private Session session;

    @Override
    public List<Soba> getListaSvihSoba() {
        return session.createCriteria(Soba.class).list();
    }

    @Override
    public Soba getSobaById(Integer id) {
        return (Soba) session.createCriteria(Soba.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public void dodajSobu(Soba soba) {
        session.persist(soba);
    }

    @Override
    public void obrisiSobu(Integer id) {
        Soba soba = (Soba) session.createCriteria(Soba.class).add(Restrictions.eq("id", id)).uniqueResult();
        session.delete(soba);
    }

    @Override
    public Soba dodajIliUpdatujSobu(Soba soba) {
        return (Soba) session.merge(soba);
    }

    @Override
    public List<Soba> getListaSobaPoImenu(String ime) {
        return session.createCriteria(Soba.class).add(Restrictions.ilike("ime", ime + "%")).list();
    }

    @Override
    public int allActiveSizeSobe() {
        Long l = (Long) session.createCriteria(Soba.class).setProjection(Projections.rowCount()).uniqueResult();
        return l.intValue();
    }

    @Override
    public List<Soba> loadActiveFromTo(int from) {
        int page = (from - 1) * 20;
        List<Soba> lista
                = session.createCriteria(Soba.class).setFirstResult(page).setMaxResults(20).addOrder(Order.asc("id")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return lista;
    }

}
