package com.courtly.dao;

import com.courtly.entity.SurfaceType;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@DataJpaTest
@Import(SurfaceTypeDao.class)
@ExtendWith(SpringExtension.class)
public class SurfaceTypeDaoTest {

    @Autowired
    private SurfaceTypeDao surfaceTypeDao;

//    @Test
//    public void findByName_ShouldReturnEntity_WhenExists() {
//        SurfaceType surfaceType = new SurfaceType();
//        surfaceType.setName("Grass");
//        surfaceType.setFirstTariff(20.0);
//        surfaceType.setSecondTariff(45.0);
//        surfaceType.setThirdTariff(55.0);
//        surfaceType.setFourthTariff(60.0);
//        surfaceType.setDeleted(Boolean.FALSE);
//        surfaceType.setCreated(LocalDateTime.now());
//        surfaceTypeDao.save(surfaceType);
//
//        SurfaceType found = surfaceTypeDao.findByName("Grass");
//
//        assertNotNull(found);
//        assertEquals("Grass", found.getName());
//    }
//
//    @Test
//    public void findByName_ShouldReturnNull_WhenNotExists() {
//        SurfaceType found = surfaceTypeDao.findByName("Unknown");
//        assertNull(found);
//    }
}
