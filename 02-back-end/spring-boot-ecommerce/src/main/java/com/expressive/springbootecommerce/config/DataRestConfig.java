package com.expressive.springbootecommerce.config;

import com.expressive.springbootecommerce.entity.Product;
import com.expressive.springbootecommerce.entity.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {

    private EntityManager entityManager_;

    @Autowired
    public DataRestConfig(EntityManager entityManager){
        entityManager_ = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        HttpMethod[] unsuportedActions = {HttpMethod.PUT,HttpMethod.POST,HttpMethod.DELETE};

        //Desabilitar métodos Http para produtos: PUT, POST e DELETE
        config.getExposureConfiguration()
                .forDomainType(Product.class)
                .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(unsuportedActions)))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(unsuportedActions)));

        //Desabilitar métodos Http para categoria de produtos: PUT, POST e DELETE
        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(unsuportedActions)))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(unsuportedActions)));

        //Chamar o helper interno para expor os ids
        exposeIds(config);
    }

    private void exposeIds(RepositoryRestConfiguration config) {

        //expose entity ids

        //Obter uma lista de classes de entidades do gestor de entidades
        Set<EntityType<?>> entidades = entityManager_.getMetamodel().getEntities();

        //Criar um array de tipos de entidades
        List<Class> entityClasses = new ArrayList<>();

        //obter tipos de entidades para entidades
        for (EntityType tempEntityType:entidades){
            entityClasses.add(tempEntityType.getJavaType());
        }

        //-expor os ids para o Array de entidades/ e tipos de dominio
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);

    }
}
