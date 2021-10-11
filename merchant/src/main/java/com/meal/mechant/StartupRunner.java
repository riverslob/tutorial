/*
package com.meal.mechant;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Order(1)
public class StartupRunner implements CommandLineRunner {
    private final NamedParameterJdbcTemplate template;

    public StartupRunner(@Qualifier("cacheTemplate") NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void run(String... args) throws Exception {
//        template.getJdbcOperations().update("");
        String s = "create table engine.t_merchant_account\n" +
                "(\n" +
                "    merchant_id integer,\n" +
                "    account_no  varchar(100),\n" +
                "    amount      decimal(22, 6)\n" +
                ");";
        template.update(s, new HashMap<>());
        template.update("insert into engine.t_merchant_account(merchant_id, account_no, amount)\n" +
                "values (1, '6654321', 100);", new HashMap<>());
        List<Map<String, Object>> maps = template.queryForList("select * from engine.t_merchant_account", new HashMap<>());
        System.out.println("run");
    }
}
*/
