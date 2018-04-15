/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author NACHO
 * @param <T> Tipo de entidad a tratar
 */
public class Consulta<T> {

    EntityManager em;
    CriteriaBuilder builder;
    CriteriaQuery<T> criteriaQuery;
    List<Predicate> criterios;
    Root<T> consultaRoot;
    List<Order> lOrderBy;
    Class clase;

    Map<String, Map<CriteriaType, Object>> mapCriterios;

    public enum CriteriaType {

        LIKE,
        EQUAL,
        IN
    }

    public enum OrderByType {

        ASC,
        DESC
    }

    public void addOrderBy(String campo, OrderByType direction) {
        switch (direction) {
            case ASC:
                lOrderBy.add(builder.asc(consultaRoot.get(campo)));
                break;
            case DESC:
                lOrderBy.add(builder.desc(consultaRoot.get(campo)));
                break;
            default:
                break;
        }
    }

    public Consulta(EntityManager manager, Class clase) {
        em = manager;
        builder = em.getCriteriaBuilder();
        criteriaQuery = builder.createQuery(clase);
        consultaRoot = criteriaQuery.from(clase);
        this.clase = clase;
        criteriaQuery.select(consultaRoot);

        criterios = new ArrayList<>();
        lOrderBy = new ArrayList<>();
        mapCriterios = new HashMap<>();
    }

    public void addCriteria(CriteriaType tipo, String campo, Object valor) {
        Map<CriteriaType, Object> map = new HashMap<>();
        map.put(tipo, valor);
        mapCriterios.put(campo, map);
        criterios.add(createPredicate(tipo, campo, valor, builder, consultaRoot));
    }

    private Predicate createPredicate(CriteriaType tipo, String campo, Object valor, CriteriaBuilder cb, Root cr) {
        Predicate result = null;
        switch (tipo) {
            case LIKE:
                result = cb.like(cr.<String>get(campo), "" + valor);
                break;
            case EQUAL:
                result = cb.equal(cr.get(campo), valor);
                break;
            case IN:
                result = cr.get(campo).in((List) valor);
                break;
            default:
                break;
        }
        return result;
    }

    public Collection<T> getResultList() {
        criteriaQuery.where(builder.and(criterios.toArray(new Predicate[0]))).orderBy(lOrderBy);

        TypedQuery<T> typedQuery = em.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    public Collection<T> getResultList(Integer pageNumber, Integer pageSize) {
        criteriaQuery.where(builder.and(criterios.toArray(new Predicate[0]))).orderBy(lOrderBy);

        TypedQuery<T> typedQuery = em.createQuery(criteriaQuery);
        if (pageNumber != null || pageSize != null) {
            typedQuery.setFirstResult((pageNumber - 1) * pageSize);
            typedQuery.setMaxResults(pageSize);
        } else {
            typedQuery.setFirstResult(0);
            typedQuery.setMaxResults(1);
        }
        return typedQuery.getResultList();
    }

    public Long totalResults() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> cr = cq.from(clase);
        cq.select(builder.count(cr));
        List<Predicate> condiciones = new ArrayList<>();
        for (String campo : mapCriterios.keySet()) {
            for (CriteriaType ct : mapCriterios.get(campo).keySet()) {
                condiciones.add(createPredicate(ct, campo, mapCriterios.get(campo).get(ct), cb, cr));
            }
        }
        cq.where(cb.and(condiciones.toArray(new Predicate[0])));
        return em.createQuery(cq).getSingleResult();
    }
}
