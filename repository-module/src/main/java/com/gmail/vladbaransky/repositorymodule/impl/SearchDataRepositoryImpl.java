package com.gmail.vladbaransky.repositorymodule.impl;

import com.gmail.vladbaransky.repositorymodule.SearchDataRepository;
import com.gmail.vladbaransky.repositorymodule.model.Url;
import org.springframework.stereotype.Repository;

@Repository
public class SearchDataRepositoryImpl extends GenericDaoRepositoryImpl<Long, Url> implements SearchDataRepository {

/*    @Override
    public List<Article> getObjectByPage(int startPosition, int objectByPage) {
        String hql = "FROM " + entityClass.getSimpleName() + " a ORDER BY a.date DESC";
        Query query = entityManager.createQuery(hql);
        query.setFirstResult(startPosition);
        query.setMaxResults(objectByPage);
        return query.getResultList();
    }*/
}
