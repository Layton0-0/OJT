package com.yoon.practice.generator;

import com.google.common.base.CaseFormat;
import com.yoon.practice.board.file.FileInfoService;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.metamodel.model.domain.IdentifiableDomainType;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;
import java.util.stream.Stream;

@Component
public class CustomGenerator implements IdentifierGenerator{

    private String prefix;

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        prefix = params.getProperty("prefix");
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        String objName = object.getClass().getSimpleName();

        // 구아바 이용 방식(카멜 -> 스네이크)
        objName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, objName);
        /* 정규식 사용하는 노가다 방식 (카멜 -> 스네이크)
                   String regex = "([a-z])([A-Z]+)";
                   String replacement = "$1_$2";
                   System.out.println("CamelCaseToSomethingElse"
                           .replaceAll(regex, replacement)
                           .toLowerCase());
        * */

        String query = String.format("select * from %s", objName);
        long count = session.createNativeQuery(query).stream().count();

        count++;
        return prefix + count;
    }

}
