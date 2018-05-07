package com.microwise.uma.common.sys.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.microwise.uma.common.sys.Tests;
import com.microwise.uma.common.util.cleanDB.DatabaseAnalyzer;
import com.microwise.uma.util.ConfigFactory;

/**
 * @author gaohui
 * @date 13-4-10 12:33
 */
public class CleanDatabaseTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection conn = CleanDBTest.getConn();
        ConfigFactory.Configs cfg;
        cfg = ConfigFactory.getInstance().getConfig(Tests.CLEAN_DB_CONFIG_PATH);

        String[] filterTbArr = cfg.get(Tests.FILTE_TABLE,null).split(",");
        List<String> filteTBs = Arrays.asList(filterTbArr);

        // 110秒  without columns
        // 120秒  with columns
        System.out.println(new Date());
        for (int i = 0; i < 10; i++) {
            DatabaseAnalyzer.cleanDatabase(conn, filteTBs);
        }
        System.out.println(new Date());

        conn.close();
    }
}
