package com.odde.atddv2;

import com.github.leeonky.cucumber.restful.RestfulStep;
import com.github.leeonky.jfactory.JFactory;
import com.odde.atddv2.repo.OrderRepo;
import com.odde.atddv2.repo.UserRepo;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {CucumberConfiguration.class}, loader = SpringBootContextLoader.class)
@CucumberContextConfiguration
public class ApplicationSteps {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    JFactory jFactory;

    @Before(order = 1)
    public void clearDB() {
        userRepo.deleteAll();
        orderRepo.deleteAll();
        jFactory.getDataRepository().clear();
    }

    @Autowired
    private RestfulStep restfulStep;

    @Before(order = 1)
    public void setBaseUrl() {
        restfulStep.setBaseUrl("http://127.0.0.1:10081/api/");
    }

    @Before("@logistics-api")
    public void setLogisticsBaseUrl() {
        restfulStep.setBaseUrl("http://127.0.0.1:10083/api/");
    }
}
