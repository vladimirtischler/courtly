package com.courtly.dao;

import com.courtly.entity.SurfaceType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SurfaceTypeDaoTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<SurfaceType> typedQuery;

    @InjectMocks
    private SurfaceTypeDao surfaceTypeDao;

    @Test
    void findByName_SurfaceTypeFound() {
        SurfaceType expected = new SurfaceType();
        expected.setName("Clay");

        when(entityManager.createNamedQuery(SurfaceType.FIND_BY_NAME, SurfaceType.class))
                .thenReturn(typedQuery);
        when(typedQuery.setParameter("name", "Clay")).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(expected);

        SurfaceType result = surfaceTypeDao.findByName("Clay");

        Assertions.assertEquals(result, expected);
    }

    @Test
    void findByName_CatchNRE() {
        when(entityManager.createNamedQuery(SurfaceType.FIND_BY_NAME, SurfaceType.class))
                .thenReturn(typedQuery);
        when(typedQuery.setParameter("name", "Unknown")).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenThrow(new NoResultException());

        SurfaceType result = surfaceTypeDao.findByName("Unknown");

        Assertions.assertNull(result);
    }

    @Test
    void save() {
        SurfaceType entity = new SurfaceType();

        surfaceTypeDao.save(entity);

        verify(entityManager).persist(entity);
    }

    @Test
    void update() {
        SurfaceType entity = new SurfaceType();

        surfaceTypeDao.update(entity);

        verify(entityManager).merge(entity);
    }

    @Test
    void findById() {
        SurfaceType expected = new SurfaceType();
        when(entityManager.find(SurfaceType.class, 1L)).thenReturn(expected);

        SurfaceType result = surfaceTypeDao.findById(1L);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void findAll() {
        List<SurfaceType> expectedList = List.of(new SurfaceType(), new SurfaceType());
        when(entityManager.createQuery("SELECT e FROM SurfaceType e WHERE e.deleted=FALSE", SurfaceType.class))
                .thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(expectedList);

        List<SurfaceType> result = surfaceTypeDao.findAll();

        Assertions.assertEquals(expectedList, result);
    }

    @Test
    void deleteShouldSetDeletedTrueAndMergeEntity() {
        SurfaceType entity = new SurfaceType();
        entity.setDeleted(false);

        surfaceTypeDao.delete(entity);

        Assertions.assertTrue(entity.getDeleted());
        verify(entityManager).merge(entity);
    }
}
