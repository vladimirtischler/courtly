package com.courtly.config;

import com.courtly.dao.CourtDao;
import com.courtly.dao.SurfaceTypeDao;
import com.courtly.dao.UserDao;
import com.courtly.entity.Court;
import com.courtly.entity.SurfaceType;
import com.courtly.entity.User;
import com.courtly.enums.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.logging.Logger;

@Configuration
public class DefaultInitDataConfig {
    private static final Logger LOGGER = Logger.getLogger(DefaultInitDataConfig.class.getSimpleName());

    @Value("${admin.default.username}")
    private String defaultAdminUsername;

    @Value("${admin.default.password}")
    private String defaultAdminPassword;

    @Value("${app.data.init.enabled}")
    private Boolean initDataEnabled;

    @Bean
    public CommandLineRunner createDefaultAdmin(UserDao userDao,
                                                PasswordEncoder passwordEncoder) {
        User admin = new User();
        admin.setUsername(defaultAdminUsername);
        admin.setPassword(passwordEncoder.encode(defaultAdminPassword));
        admin.setRole(Role.ADMIN);
        admin.setDeleted(false);

        return args -> {
            userDao.save(admin);
            LOGGER.info("Default admin user was created");
        };
    }

    @Bean
    public CommandLineRunner initDefaultData(SurfaceTypeDao surfaceTypeDao,
                                             CourtDao courtDao) {

        if (!initDataEnabled){
            LOGGER.info("Initialization of data is disabled");
            return null;
        }

        LOGGER.info("Initialization of data is enabled");

        SurfaceType concrete = new SurfaceType();
        concrete.setName("Concrete");
        concrete.setFirstTariff(20.0);
        concrete.setSecondTariff(30.0);
        concrete.setThirdTariff(45.0);
        concrete.setFourthTariff(50.0);

        SurfaceType grass = new SurfaceType();
        grass.setName("Synthetic grass");
        grass.setFirstTariff(25.0);
        grass.setSecondTariff(35.0);
        grass.setThirdTariff(45.0);
        grass.setFourthTariff(55.0);

        Court first = new Court();
        first.setSurfaceType(grass);
        first.setName("First");

        Court second = new Court();
        second.setSurfaceType(grass);
        second.setName("Second");

        Court third = new Court();
        third.setSurfaceType(concrete);
        third.setName("Third");

        Court fourth = new Court();
        fourth.setSurfaceType(concrete);
        fourth.setName("Fourth");

        return args -> {
            surfaceTypeDao.save(grass);
            surfaceTypeDao.save(concrete);

            courtDao.save(first);
            courtDao.save(second);
            courtDao.save(third);
            courtDao.save(fourth);
        };
    }
}
