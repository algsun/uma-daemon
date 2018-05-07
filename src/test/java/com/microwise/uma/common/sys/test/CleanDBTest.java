/**
 *
 */
package com.microwise.uma.common.sys.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.io.Closeables;
import com.google.common.io.Resources;
import com.microwise.uma.common.sys.Tests;
import com.microwise.uma.common.util.cleanDB.DatabaseAnalyzer;
import com.microwise.uma.util.ConfigFactory;
import com.microwise.uma.util.ConfigFactory.Configs;

/**
 * Dbunit测试工具类
 *
 * @author zhangpeng
 * @date 2012-12-13
 * @check 2012-12-13 xubaoji svn:805
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public abstract class CleanDBTest {
    public static final Logger log = LoggerFactory.getLogger("com.microwise.clean-db");

    private static Configs cfg;
    private static List<String> ignoredTables;

    static {
        ConfigFactory cfgFactory = ConfigFactory.getInstance();
        cfg = cfgFactory.getConfig(Tests.CLEAN_DB_CONFIG_PATH);
        String[] filterTbArr = cfg.get(Tests.FILTE_TABLE, null).split(",");
        ignoredTables = Arrays.asList(filterTbArr);
        try {
            Class.forName(cfg.get(Tests.DB_DRIVER, null));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @After
    public void cleanDB() throws Exception {
        tearDownOperation();
    }

    /**
     * 获取数据源
     */
    public static Connection getConn() throws SQLException,
            ClassNotFoundException {
        return DriverManager.getConnection(
                cfg.get(Tests.DB_URL, null),
                cfg.get(Tests.DB_NAME, null),
                cfg.get(Tests.DB_PASSWORD, null));
    }

    /**
     * 后置方法，清库（排除字典表等关键信息表）
     */
    public static void tearDownOperation() throws Exception {
        log.info("cleanDB...>>>");
        Connection conn = getConn();

        log.debug("cleanDatabase >>>");
        DatabaseAnalyzer.cleanDatabase(conn, ignoredTables);
        conn.close();
        log.debug("cleanDatabase <<<");
        log.info("cleanDB...<<<");
    }

    /**
     * 根据xml文件路径插库，准备数据
     */
    @SuppressWarnings("deprecation")
    public static void xmlInsert(String xmlPath) throws Exception {
        Connection conn = getConn();
        DatabaseOperation.INSERT.execute(new DatabaseConnection(conn),
                new FlatXmlDataSet(new FileInputStream(xmlPath)));
        conn.close();
    }

    /**
     * 根据xml文件路径插库，准备数据
     *
     * @param xmlPath classpath 路径
     */
    @SuppressWarnings("deprecation")
    public static void xmlInsert2(String xmlPath) throws Exception {
        Connection conn = null;
        try {
            conn = getConn();
            InputStream inputStream = null;
            try {
                inputStream = findInClasspath(xmlPath);
                DatabaseOperation.INSERT.execute(new DatabaseConnection(conn), new FlatXmlDataSet(inputStream));
            } finally {
                Closeables.close(inputStream, true);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * 返回 classpath 下的资源 input
     *
     * @param path
     * @return
     * @throws IOException
     */
    private static InputStream findInClasspath(String path) throws IOException {
        return Resources.newInputStreamSupplier(Resources.getResource(path)).getInput();
    }
}
