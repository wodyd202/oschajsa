package com.ljy.oschajsa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

//@SpringBootTest
class OschajsaApplicationTests {

    @Test
    void contextLoads() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("T(java.lang.String).format('%f-%f-%f', 1.1, 2.2, 3.3)");
        Object value = exp.getValue();
        System.out.println(value);
    }

}
