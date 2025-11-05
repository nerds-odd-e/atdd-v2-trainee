package com.odde.atddv2;

import com.github.leeonky.jfactory.CompositeRepository;
import com.github.leeonky.jfactory.DataRepository;
import com.github.leeonky.jfactory.JFactory;
import com.github.leeonky.jfactory.repo.JPADataRepository;
import com.odde.atddv2.mybatis.entity.OrderLinePo;
import com.odde.atddv2.mybatis.entity.OrderPo;
import com.odde.atddv2.mybatis.mapper.OrderMapper;
import lombok.SneakyThrows;
import org.mockserver.client.MockServerClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.net.URL;
import java.util.Collection;
import java.util.List;

@Configuration
public class Factories {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @SneakyThrows
    @Bean
    public MockServerClient createMockServerClient(@Value("${mock-server.endpoint}") String endpoint) {
        URL url = new URL(endpoint);
        return new MockServerClient(url.getHost(), url.getPort()) {
            @Override
            public void close() {
            }
        };
    }

    @Bean
    public JFactory factorySet(OrderMapper orderMapper) {
        JPADataRepository dataRepository = new JPADataRepository(entityManagerFactory.createEntityManager());

        DataRepository myBatisRepository = new DataRepository() {
            @Override
            public <T> Collection<T> queryAll(Class<T> aClass) {
                if (aClass.equals(OrderPo.class))
                    return (Collection<T>) orderMapper.findAll();
                if (aClass.equals(OrderLinePo.class))
                    return (Collection<T>) orderMapper.findAllLines();
                return List.of();
            }

            @Override
            public void clear() {
            }

            @Override
            public void save(Object o) {
                if (o instanceof OrderPo po)
                    orderMapper.insertOrder(po);
                if (o instanceof OrderLinePo po)
                    orderMapper.insertOrderLine(po);
            }
        };
        CompositeRepository compositeRepository = new CompositeRepository();
        compositeRepository.addRepository(c -> c.getPackageName().contains("mybatis"), myBatisRepository);
        compositeRepository.addRepository(c -> !c.getPackageName().contains("mybatis"), dataRepository);
        return new EntityFactory(compositeRepository);
    }
}
