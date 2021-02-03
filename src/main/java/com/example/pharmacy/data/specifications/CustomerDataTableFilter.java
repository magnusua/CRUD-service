package com.example.pharmacy.data.specifications;


import com.example.pharmacy.data.entity.Customer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;

public class CustomerDataTableFilter implements org.springframework.data.jpa.domain.Specification<Customer> {

    String userQuery;

    public CustomerDataTableFilter(String queryString) {
        this.userQuery = queryString;
    }

    @Override
    public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (userQuery != null && !userQuery.equals("")) {
            predicates.add(criteriaBuilder.like(root.get("fullName"), '%' + userQuery + '%'));
            predicates.add(criteriaBuilder.like(root.get("birthday"), '%' + userQuery + '%'));
            predicates.add(criteriaBuilder.like(root.get("inn"), '%' + userQuery + '%'));
            predicates.add(criteriaBuilder.like(root.get("familyStatus"), '%' + userQuery + '%'));
            predicates.add(criteriaBuilder.like(root.get("education"), '%' + userQuery + '%'));
            predicates.add(criteriaBuilder.like(root.get("segment"), '%' + userQuery + '%'));
            predicates.add(criteriaBuilder.like(root.get("customerFamily"), '%' + userQuery + '%'));
        }

        return (!predicates.isEmpty() ? criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()])) : null);
    }
}
