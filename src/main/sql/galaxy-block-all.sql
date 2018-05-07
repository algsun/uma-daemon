-- 插入存储过程及函数
-- DELIMITER加注释原因：maven测试插件不认识，在数据库用sql创建时，记得放开
-- DELIMITER ;;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;;
/*!40101 SET NAMES utf8 */;;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;;

-- Dumping structure for function galaxy_dev.fun_directionRate
DROP FUNCTION IF EXISTS `fun_directionRate`;;

CREATE FUNCTION `fun_directionRate`(directionCount_in INT, sum_in INT) RETURNS double
BEGIN
     DECLARE rate_d DOUBLE;
     SET rate_d = ROUND(directionCount_in/sum_in*100,1);
     RETURN rate_d;
 END;;



-- Dumping structure for function galaxy_dev.fun_getAvgGroupid
DROP FUNCTION IF EXISTS `fun_getAvgGroupid`;;

CREATE FUNCTION `fun_getAvgGroupid`(dete_in DATE) RETURNS int(11)
BEGIN
     DECLARE count_i INT;
     DECLARE groupid_i INT;
     SELECT COUNT(1),MAX(groupid)+1 INTO count_i,groupid_i FROM m_avgdata WHERE ms_date = ADDDATE(dete_in,-1);
     IF count_i = 0 THEN
         SET groupid_i = 1;
     END IF;
     RETURN groupid_i;
 END;;



-- Dumping structure for function galaxy_dev.fun_getChildren
DROP FUNCTION IF EXISTS `fun_getChildren`;;

CREATE FUNCTION `fun_getChildren`(id VARCHAR(50)) RETURNS TEXT CHARSET utf8
BEGIN
  DECLARE sTemp TEXT;
  DECLARE sTempChd TEXT;
  SET sTemp = "" ;
  SET sTempChd = CAST(id AS CHAR) ;
  WHILE
    sTempChd IS NOT NULL DO SET sTemp = CONCAT(sTemp, ',', sTempChd) ;
    SELECT 
      GROUP_CONCAT(zoneId) INTO sTempChd 
    FROM
      t_zone 
    WHERE FIND_IN_SET(parentId, sTempChd) > 0 ;
  END WHILE ;
  -- 注意这里截取字符串是因为最开始变量赋的是空串，第一次拼接会留下一个逗号
  SET sTemp = SUBSTRING(sTemp, 2) ;
  RETURN sTemp ;
END;;



-- Dumping structure for function galaxy_dev.fun_getLuxIntegral
DROP FUNCTION IF EXISTS `fun_getLuxIntegral`;;

CREATE FUNCTION `fun_getLuxIntegral`(stime_in DATETIME, svalue_in DOUBLE, etime_in DATETIME, evalue_in DOUBLE) RETURNS double
BEGIN
     DECLARE diffsecond_i INT; -- time diff
     DECLARE integral_d DOUBLE;
   
     -- get time diff 
     SET diffsecond_i = TIMESTAMPDIFF(SECOND,stime_in,etime_in);
     
     -- sum lux
     SET integral_d = (svalue_in + evalue_in)*diffsecond_i/2;
     
     RETURN integral_d;  
 END;;



-- Dumping structure for function galaxy_dev.fun_getrealcofficient
DROP FUNCTION IF EXISTS `fun_getrealcofficient`;;

CREATE FUNCTION `fun_getrealcofficient`(v_default CHAR(20), v_define CHAR(20)) RETURNS char(20) CHARSET utf8
BEGIN
     DECLARE realcofficient CHAR(20); 
     
     IF LENGTH(v_define) != 0 THEN
         SET realcofficient = v_define;
     ELSE
         SET realcofficient = v_default;
     END IF;
     RETURN realcofficient;
     END;;



-- Dumping structure for function galaxy_dev.fun_getSeason
DROP FUNCTION IF EXISTS `fun_getSeason`;;

CREATE FUNCTION `fun_getSeason`(date_in DATE) RETURNS int(11)
BEGIN
     DECLARE month_i INT;
     DECLARE season_i INT;
     
     SELECT MONTH(date_in) INTO month_i;    
     IF month_i >= 1 && month_i <= 3 THEN
     SET season_i = 1;
     ELSEIF month_i >= 4 && month_i <= 6 THEN
     SET season_i = 2;
     ELSEIF month_i >= 7 && month_i <= 9 THEN
     SET season_i = 3;
     ELSE
     SET season_i = 4;
     END IF;
     RETURN season_i;
 END;;



-- Dumping structure for function galaxy_dev.fun_isExistAvg
DROP FUNCTION IF EXISTS `fun_isExistAvg`;;

CREATE FUNCTION `fun_isExistAvg`(deviceid_in VARCHAR(50), date_in DATE,sensorPhysicalid_in INT) RETURNS tinyint(1)
BEGIN
         DECLARE isok BOOL DEFAULT FALSE;
         SELECT COUNT(1) INTO @count_i FROM m_avgdata WHERE nodeid = deviceid_in AND ms_date = date_in AND sensorPhysicalid = sensorPhysicalid_in;
         IF @count_i>0 THEN
             SET isok = TRUE;
         END IF;
         RETURN isok;
 END;;



-- Dumping structure for function galaxy_dev.fun_isExistDateRb
DROP FUNCTION IF EXISTS `fun_isExistDateRb`;;

CREATE FUNCTION `fun_isExistDateRb`(deviceid_in VARCHAR(20), date_in DATE) RETURNS tinyint(1)
BEGIN
     DECLARE isok BOOL DEFAULT FALSE;
     DECLARE count_i INT;
     
     SELECT COUNT(1) INTO count_i FROM m_tbl_rb_day_acc WHERE nodeid = deviceid_in AND ms_date = date_in;
     IF count_i>0 THEN
         SET isok = TRUE;
     END IF;
     RETURN isok;    
 END;;



-- Dumping structure for function galaxy_dev.fun_isExistHourLux
DROP FUNCTION IF EXISTS `fun_isExistHourLux`;;

CREATE FUNCTION `fun_isExistHourLux`(deviceid_in VARCHAR(20),datetime_in DATETIME) RETURNS tinyint(1)
BEGIN
     DECLARE isok BOOL DEFAULT FALSE;
     DECLARE count_i INT;
     
     SELECT COUNT(1) INTO count_i FROM m_tbl_lxh_acc WHERE nodeid = deviceid_in AND DATE_FORMAT(ms_datetime,'%Y-%m-%d %H') = DATE_FORMAT(datetime_in,'%Y-%m-%d %H');
     IF count_i>0 THEN
         SET isok = TRUE;
     END IF;
     
     RETURN isok; 
 END;;



-- Dumping structure for function galaxy_dev.fun_isExistHourRb
DROP FUNCTION IF EXISTS `fun_isExistHourRb`;;

CREATE FUNCTION `fun_isExistHourRb`(deviceid_in VARCHAR(20), datetime_in DATETIME) RETURNS tinyint(1)
BEGIN
     DECLARE isok BOOL DEFAULT FALSE;
     DECLARE count_i INT;
     
     SELECT COUNT(1) INTO count_i FROM m_tbl_rb_hour_acc WHERE nodeid = deviceid_in AND DATE_FORMAT(ms_datetime,'%Y-%m-%d %H') = DATE_FORMAT(datetime_in,'%Y-%m-%d %H');
     IF count_i>0 THEN
         SET isok = TRUE;
     END IF;
     
     RETURN isok;
 END;;



-- Dumping structure for function galaxy_dev.fun_isExistTable
DROP FUNCTION IF EXISTS `fun_isExistTable`;;

CREATE FUNCTION `fun_isExistTable`(tablename_in VARCHAR(50)) RETURNS tinyint(1)
BEGIN
     DECLARE isExist BOOL;
     DECLARE dbName VARCHAR(50);
     SELECT DATABASE() INTO dbName;
     SELECT COUNT(1) INTO isExist FROM `information_schema`.`TABLES` t WHERE t.TABLE_NAME = tablename_in AND t.TABLE_SCHEMA = dbName;
     RETURN isExist;
 END;;



-- Dumping structure for function galaxy_dev.fun_isExistWindRose
DROP FUNCTION IF EXISTS `fun_isExistWindRose`;;

CREATE FUNCTION `fun_isExistWindRose`(deviceid_in VARCHAR(20),date_in DATE) RETURNS tinyint(1)
BEGIN
      DECLARE isok BOOL DEFAULT FALSE;
      DECLARE count_i INT;
      
      SELECT COUNT(1) INTO count_i FROM m_windrose WHERE nodeid = deviceid_in  AND ms_date = date_in;
      IF count_i>0 THEN
          SET isok = TRUE;
      END IF;
      RETURN isok;
 END;;



-- Dumping structure for function galaxy_dev.fun_precision_filter
DROP FUNCTION IF EXISTS `fun_precision_filter`;;

CREATE FUNCTION `fun_precision_filter`(sensorPhysicalid_in INT, value_in DOUBLE) RETURNS double
BEGIN
     DECLARE _sensorprec INT;
     SET _sensorprec = 0;    -- set default precision is 0
     
     SELECT sensorPrecision INTO _sensorprec FROM m_sensorinfo WHERE sensorPhysicalid = sensorPhysicalid_in;
     RETURN ROUND(value_in,_sensorprec);
     END;;



-- Dumping structure for function galaxy_dev.fun_pt100_mapping
DROP FUNCTION IF EXISTS `fun_pt100_mapping`;;

CREATE FUNCTION `fun_pt100_mapping`(i_ohm DOUBLE) RETURNS double
BEGIN
    DECLARE r_temp DOUBLE; -- result of math
    SELECT COUNT(1), MAX(temp) INTO @tmp_count, @tmp_temp FROM m_tbl_pt100_mapping WHERE ohm = i_ohm;
    SELECT COUNT(1) INTO @min_count FROM m_tbl_pt100_mapping WHERE ohm <= i_ohm;
    SELECT COUNT(1) INTO @max_count FROM m_tbl_pt100_mapping WHERE ohm >= i_ohm;
    IF @tmp_count = 1 THEN -- search same record
	SET r_temp = @tmp_temp;
    ELSEIF @min_count = 0 THEN
	SELECT MIN(temp) INTO r_temp FROM m_tbl_pt100_mapping;
    ELSEIF @max_count = 0 THEN
	SELECT MAX(temp) INTO r_temp FROM m_tbl_pt100_mapping;
    ELSE
	SELECT ohm,temp INTO @R1, @T1 FROM m_tbl_pt100_mapping WHERE ohm <= i_ohm ORDER BY id DESC LIMIT 0,1;
	SELECT ohm,temp INTO @R2, @T2 FROM m_tbl_pt100_mapping WHERE ohm >= i_ohm LIMIT 0,1;
	SET r_temp = ROUND(((i_ohm-@R1)*(@T2-@T1)/(@R2-@R1))+@T1,1);	
    END IF;
    RETURN r_temp;
    END;;



-- Dumping structure for function galaxy_dev.fun_threshold_filter
DROP FUNCTION IF EXISTS `fun_threshold_filter`;;

CREATE FUNCTION `fun_threshold_filter`(deviceid_in CHAR(10), sensorid_in INT, value_in DOUBLE) RETURNS int(11)
BEGIN
     DECLARE state INT;
     SELECT TYPE INTO state FROM m_threshold WHERE ((value_in>=VALUE AND TYPE =3) OR (value_in<=VALUE AND TYPE = 2)) AND nodeid = deviceid_in AND sensorid = sensorid_in;
     IF state IS NULL THEN
         SET state = 1;
     END IF;
     RETURN state;
     END;;

-- Dumping structure for function galaxy_dev.fun_getBaseLogicGroupChildren
DROP FUNCTION IF EXISTS `fun_getBaseLogicGroupChildren`;;

CREATE FUNCTION `fun_getBaseLogicGroupChildren`(idi VARCHAR(50)) RETURNS text CHARSET utf8
BEGIN
 DECLARE sTemp TEXT;
 DECLARE sTempChd TEXT;
 SET sTemp = "" ;
 SET sTempChd = CAST(idi AS CHAR) ;
 WHILE
 sTempChd IS NOT NULL DO SET sTemp = CONCAT(sTemp, ',', sTempChd) ;
 SELECT
 GROUP_CONCAT(id) INTO sTempChd
 FROM
 t_logicgroup
 WHERE FIND_IN_SET(parentLogicGroupId, sTempChd) > 0 ;
 END WHILE ;
 SET sTemp = SUBSTRING(sTemp, 2);
 RETURN sTemp ;
 END;;
     


-- 存储过程

-- Dumping structure for procedure galaxy_dev.sp_createViewByDate
DROP PROCEDURE IF EXISTS `sp_createViewByDate`;;

CREATE PROCEDURE `sp_createViewByDate`(deviceid_in VARCHAR(20),date_in DATE)
BEGIN
     DECLARE tableName_s VARCHAR(50);
     
     SET @date_str = DATE_FORMAT(date_in, '%Y_%m_%d');
     SET tableName_s = CONCAT("v_data_",deviceid_in,"_",@date_str);
     
     SET @date_str = DATE_FORMAT(date_in,'%Y-%m-%d');  
      
      SET @sql_str = CONCAT("CREATE VIEW  ",tableName_s," AS SELECT * FROM  `",deviceid_in,"`  WHERE DATE(CREATETIME) = '",@date_str,"'");
      PREPARE sql_createView FROM  @sql_str;
      EXECUTE sql_createView ;
      DEALLOCATE PREPARE sql_createView;  
 END;;



-- Dumping structure for procedure galaxy_dev.sp_datePeakAvgMath
DROP PROCEDURE IF EXISTS `sp_datePeakAvgMath`;;

CREATE PROCEDURE `sp_datePeakAvgMath`(deviceid_in VARCHAR(50), date_in DATE,OUT isok_out INT)
top:BEGIN
	-- iscreate 1:create 0:update
	DECLARE dataVersion_i INT DEFAULT -1;
	DECLARE sensorPhysicalid_i    INT;
	DECLARE max_d         DOUBLE;
	DECLARE min_d         DOUBLE;
	DECLARE avg_d         DOUBLE;
	DECLARE state_i       INT DEFAULT 1;
	DECLARE isExist       INT DEFAULT 0;
	DECLARE createdate_t  DATE;
        DECLARE tablename_s   VARCHAR(50);
	DECLARE _done         INT DEFAULT 0;
	
	-- define cursor is mast at after of define handler
	DECLARE cur CURSOR FOR SELECT a.sensorPhysicalid,b.max,b.min,b.AVG FROM m_nodesensor a LEFT JOIN v_datePeakAvg_tmp b ON 
	a.sensorPhysicalid = b.sensorPhysicalid WHERE a.nodeid = deviceid_in;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET _done = 1;
        DECLARE EXIT HANDLER FOR SQLEXCEPTION SET isok_out = 0;
	
	 
        -- init out parameter
        SET isok_out = 1;
        -- check date if input date lesser than createdate return.
        SELECT createtime INTO createdate_t FROM m_nodeinfo WHERE nodeid = deviceid_in;	       
        
        IF date_in < createdate_t THEN
            LEAVE top;
        END IF;       
        -- check table. if not exist table than create it
        SET @date_str = DATE_FORMAT(date_in, '%Y_%m_%d');
        SET tablename_s = CONCAT("v_data_",deviceid_in,"_",@date_str);    
        -- create tmp view
        SET @date_str = DATE_FORMAT(date_in, '%Y_%m_%d');
        SET @sql_str = CONCAT("CREATE VIEW  v_datePeakAvg_tmp as SELECT sensorPhysicalid,MAX(sensorPhysicalvalue+0) MAX,MIN(sensorPhysicalvalue+0) MIN,AVG(sensorPhysicalvalue+0) AVG FROM v_data_",deviceid_in,"_",@date_str," WHERE state IN (1,2,3) GROUP BY sensorPhysicalid");
                
        PREPARE sql_createView FROM  @sql_str;
        EXECUTE sql_createView;
        DEALLOCATE PREPARE sql_createView;
        START TRANSACTION;        
        -- exec cursor
        OPEN cur;
            REPEAT
	    FETCH cur INTO sensorPhysicalid_i,max_d,min_d,avg_d;
	    IF NOT _done THEN
	        IF avg_d IS NULL THEN
	            SET state_i = 4;
	        ELSE
	            SET state_i = 1;	        
	        END IF;  
	        SET max_d = fun_precision_filter(sensorPhysicalid_i,max_d);
	        SET min_d = fun_precision_filter(sensorPhysicalid_i,min_d);
	        SET avg_d = fun_precision_filter(sensorPhysicalid_i,avg_d);
	        SET isExist = fun_isExistAvg(deviceid_in,date_in,sensorPhysicalid_i);
	        IF isExist THEN 
	            SELECT dataVersion INTO dataVersion_i FROM m_avgdata WHERE nodeid = deviceid_in  AND ms_date = date_in AND sensorPhysicalid = sensorPhysicalid_i;            
	            
	            IF dataVersion_i > 0 THEN
			UPDATE m_avgdata SET `maxValue`=max_d,minValue=min_d,avgValue=avg_d, isupdate = 0, dataVersion = (dataVersion_i+1) WHERE nodeid = deviceid_in AND ms_date = date_in AND sensorPhysicalid = sensorPhysicalid_i;
		    ELSE
			UPDATE m_avgdata SET `maxValue`=max_d,minValue=min_d,avgValue=avg_d, isupdate = 0 WHERE nodeid = deviceid_in AND ms_date = date_in AND sensorPhysicalid = sensorPhysicalid_i;
		    END IF;
	        ELSE       
	            INSERT INTO m_avgdata(id,nodeid,sensorPhysicalid,`maxValue`,minValue,avgValue,waveValue,ms_date) VALUES((SELECT UUID()),deviceid_in,sensorPhysicalid_i,max_d,min_d,avg_d,(max_d-min_d),date_in);
	        END IF;
	    END IF;
	UNTIL _done END REPEAT;
	CLOSE cur;
	COMMIT;	
	-- drop tmp view
	DROP VIEW v_datePeakAvg_tmp;
    END;;



-- Dumping structure for procedure galaxy_dev.sp_dateRbMath
DROP PROCEDURE IF EXISTS `sp_dateRbMath`;;

CREATE PROCEDURE `sp_dateRbMath`(uid VARCHAR(100),deviceid_in VARCHAR(50),date_in DATE,OUT isok_out INT)
top:BEGIN
    DECLARE dataVersion_i INT DEFAULT -1;
    DECLARE dateRbSum_d     DOUBLE;
    DECLARE isExist         BOOL;
    DECLARE createdate_t DATE;
    DECLARE startdatetime_t DATETIME;
    DECLARE enddatetime_t   DATETIME;     
    DECLARE EXIT HANDLER FOR SQLEXCEPTION SET isok_out = 0;
    -- init out parameter   
    
    SET isok_out = 1;   
    
    -- check date if input date lesser than createdate return.
    SELECT createtime INTO createdate_t FROM m_nodeinfo WHERE nodeid = deviceid_in;     
    
    IF date_in < createdate_t THEN
        LEAVE top;
    END IF;
        
    SET startdatetime_t = DATE_ADD(date_in,INTERVAL +8 HOUR);
    SET enddatetime_t = DATE_ADD(startdatetime_t, INTERVAL +1 DAY);
    SET enddatetime_t = DATE_ADD(enddatetime_t, INTERVAL -1 HOUR);      
    
    SELECT SUM(rb) INTO dateRbSum_d FROM m_tbl_rb_hour_acc WHERE nodeid = deviceid_in AND ms_datetime BETWEEN startdatetime_t AND enddatetime_t;    
    
    IF dateRbSum_d IS NULL THEN
        SET dateRbSum_d = 0;
    END IF;
    
    SELECT fun_isExistDateRb(deviceid_in,date_in) INTO isExist;
    
    START TRANSACTION;
    IF isExist THEN
        SELECT dataVersion INTO dataVersion_i FROM m_tbl_rb_day_acc WHERE nodeid=deviceid_in AND ms_date = date_in;
        IF dataVersion_i > 0 THEN
		UPDATE m_tbl_rb_day_acc SET rb = dateRbSum_d, isupdate = 0, dataVersion = (dataVersion_i+1) WHERE nodeid=deviceid_in AND ms_date = date_in;
	ELSE
		UPDATE m_tbl_rb_day_acc SET rb = dateRbSum_d, isupdate = 0 WHERE nodeid=deviceid_in AND ms_date = date_in;
	END IF;
    ELSE
        INSERT INTO m_tbl_rb_day_acc(id,nodeid,rb,ms_date) VALUES(uid,deviceid_in,dateRbSum_d,date_in);
    END IF;
    COMMIT;
END;;



-- Dumping structure for procedure galaxy_dev.sp_dateWindRoseMath
DROP PROCEDURE IF EXISTS `sp_dateWindRoseMath`;;

CREATE PROCEDURE `sp_dateWindRoseMath`(uid VARCHAR(100), deviceid_in VARCHAR(50),date_in DATE,OUT isok_out INT)
top:BEGIN
     DECLARE dataVersion_i INT DEFAULT -1;
     DECLARE sum_i        INT;
     DECLARE windcalmCount_i   INT;
     DECLARE windmark_s   VARCHAR(10);
     DECLARE directionCount_i INT;
     DECLARE speedValue_d DOUBLE;
     DECLARE isExist BOOL;
     DECLARE season_i INT;
     DECLARE createdate_t DATE;
     DECLARE tablename_s VARCHAR(50);
     DECLARE o_n DOUBLE; -- north direction
     DECLARE s_n DOUBLE; -- north speed
     
     DECLARE o_nne DOUBLE; -- Northeast North direction
     DECLARE s_nne DOUBLE; -- Northeast North speed    
     
     DECLARE o_ne DOUBLE; -- Northeast direction
     DECLARE s_ne DOUBLE; -- Northeast speed
     
     DECLARE o_ene DOUBLE; -- Northeast East direction
     DECLARE s_ene DOUBLE; -- Northeast East speed
     
     DECLARE o_e DOUBLE; -- East direction
     DECLARE s_e DOUBLE; -- East speed
     
     DECLARE o_ese DOUBLE; -- Southeast East direction
     DECLARE s_ese DOUBLE; -- Southeast East speed
     
     DECLARE o_se DOUBLE; -- Southeast direction
     DECLARE s_se DOUBLE; -- Southeast speed
     
     DECLARE o_sse DOUBLE; -- Southeast South direction
     DECLARE s_sse DOUBLE; -- Southeast South speed
     
     DECLARE o_s DOUBLE; -- South direction
     DECLARE s_s DOUBLE; -- South speed
     
     DECLARE o_ssw DOUBLE; -- SouthWest West direction
     DECLARE s_ssw DOUBLE; -- SouthWest West speed
     
     DECLARE o_sw DOUBLE; -- SouthWest direction
     DECLARE s_sw DOUBLE; -- SouthWest speed
     
     DECLARE o_wsw DOUBLE; -- SouthWest West direction
     DECLARE s_wsw DOUBLE; -- SouthWest West speed
     
     DECLARE o_w DOUBLE; -- West direction
     DECLARE s_w DOUBLE; -- West speed
     
     DECLARE o_wnw DOUBLE; -- NorthWest West direction
     DECLARE s_wnw DOUBLE; -- NorthWest West speed
     
     DECLARE o_nw DOUBLE; -- NorthWest direction
     DECLARE s_nw DOUBLE; -- NorthWest speed
     
     DECLARE o_nnw DOUBLE; -- NorthWest North direction
     DECLARE s_nnw DOUBLE; -- NorthWest North speed
     
     DECLARE calm DOUBLE; -- calm speed less than 0.2
    
     -- define cursor 
     DECLARE _done         INT         DEFAULT 0;
     DECLARE cur CURSOR FOR SELECT a.windmark,b.windDirectionCount,b.windSpeedAvg FROM m_windmark a LEFT JOIN v_datewindrosereal b ON a.windmark = b.windDirection;
     DECLARE CONTINUE HANDLER FOR NOT FOUND SET _done = 1;
     
     -- init out parameter
     SET isok_out = 1;
     
     -- check date if input date lesser than createdate return.
     SELECT createtime INTO createdate_t FROM m_nodeinfo WHERE nodeid = deviceid_in;
     IF date_in < createdate_t THEN
      LEAVE top;
     END IF;
     
     -- check table. if not exist table than create it
     SET @date_str = DATE_FORMAT(date_in, '%Y_%m_%d');
     SET tablename_s = CONCAT("v_data_",deviceid_in,"_",@date_str);
     SET isExist = fun_isExistTable(tablename_s);
     IF !isExist THEN
         CALL sp_createViewByDate(deviceid_in,date_in);
     END IF;
     -- 1 direction
     
     SET @sql_direction_str = CONCAT("CREATE OR REPLACE VIEW  v_datewinddirection AS SELECT sensorPhysicalvalue,createtime FROM ",tablename_s," WHERE  sensorPhysicalid = 48 AND state IN (1,2,3)");
     PREPARE sql_createDirectionView FROM  @sql_direction_str;
     EXECUTE sql_createDirectionView ;
     DEALLOCATE PREPARE sql_createDirectionView;  
     
     -- 2 speed
     SET @date_str = DATE_FORMAT(date_in, '%Y_%m_%d');
     SET @sql_speed_str = CONCAT("CREATE OR REPLACE VIEW  v_datewindspeed AS SELECT sensorPhysicalvalue,createtime FROM ",tablename_s," WHERE sensorPhysicalid = 49 AND state IN (1,2,3)");
     PREPARE sql_createSpeedView FROM  @sql_speed_str;
     EXECUTE sql_createSpeedView;
     DEALLOCATE PREPARE sql_createSpeedView;
     
     -- 6.sum
     SELECT COUNT(1) INTO sum_i FROM v_datewindrosebase;
     -- 7.season
     SELECT fun_getSeason(date_in) INTO season_i;
     
     -- 8.check is Exist Record    
     SELECT fun_isExistWindRose(deviceid_in,date_in) INTO isExist;
     IF !isExist THEN
         INSERT INTO m_windrose(id,nodeid,ms_date,season) VALUES(uid,deviceid_in,date_in,season_i);
     END IF;
     -- 9.wind protected
     OPEN cur;
     REPEAT
     FETCH cur INTO windmark_s, directionCount_i, speedValue_d;
         IF speedValue_d IS NULL THEN
             SET speedValue_d = 0;
         END IF;
         
         IF directionCount_i IS NULL THEN 
             SET directionCount_i = 0;
         END IF;
         
     IF NOT _done THEN
     CASE windmark_s
         WHEN '0' THEN
             SELECT fun_directionRate(directionCount_i,sum_i) INTO o_n;
         SET s_n = speedValue_d;
         
     WHEN '22.5' THEN
         SELECT fun_directionRate(directionCount_i,sum_i) INTO o_nne;
         SET s_nne = speedValue_d;
         
     WHEN '45' THEN
         SELECT fun_directionRate(directionCount_i,sum_i) INTO o_ne;
         SET s_ne = speedValue_d;
     
     WHEN '67.5' THEN
         SELECT fun_directionRate(directionCount_i,sum_i) INTO o_ene;
         SET s_ene = speedValue_d;
     
     WHEN '90' THEN
         SELECT fun_directionRate(directionCount_i,sum_i) INTO o_e;
         SET s_e = speedValue_d;
     
     WHEN '112.5' THEN
         SELECT fun_directionRate(directionCount_i,sum_i) INTO o_ese;
         SET s_ese = speedValue_d;
     
     WHEN '135' THEN
         SELECT fun_directionRate(directionCount_i,sum_i) INTO o_se;
         SET s_se = speedValue_d;
     
     WHEN '157.5' THEN
         SELECT fun_directionRate(directionCount_i,sum_i) INTO o_sse;
         SET s_sse = speedValue_d;
     
     WHEN '180' THEN
         SELECT fun_directionRate(directionCount_i,sum_i) INTO o_s;
         SET s_s = speedValue_d;
     
     WHEN '202.5' THEN
         SELECT fun_directionRate(directionCount_i,sum_i) INTO o_ssw;
         SET s_ssw = speedValue_d;
     
     WHEN '225' THEN
         SELECT fun_directionRate(directionCount_i,sum_i) INTO o_sw;
         SET s_sw = speedValue_d;
     
     WHEN '247.5' THEN
         SELECT fun_directionRate(directionCount_i,sum_i) INTO o_wsw;
         SET s_wsw = speedValue_d;
     
     WHEN '270' THEN
         SELECT fun_directionRate(directionCount_i,sum_i) INTO o_w;
         SET s_w = speedValue_d;
     
     WHEN '292.5' THEN
         SELECT fun_directionRate(directionCount_i,sum_i) INTO o_wnw;
         SET s_wnw = speedValue_d;
     
     WHEN '315' THEN
         SELECT fun_directionRate(directionCount_i,sum_i) INTO o_nw;
         SET s_nw = speedValue_d;
     
     WHEN '337.5' THEN
         SELECT fun_directionRate(directionCount_i,sum_i) INTO o_nnw;
         SET s_nnw = speedValue_d;    
     
         ELSE
         SELECT @size;
     END CASE;
     END IF;
     UNTIL _done END REPEAT;
     CLOSE cur;
     -- 10.calm
     SELECT COUNT(1) INTO windcalmCount_i FROM v_datewindrosebase WHERE windSpeed<=0.2;
     SELECT fun_directionRate(windcalmCount_i,sum_i) INTO calm;
     
     START TRANSACTION;
     -- 11.update table data
     SELECT dataVersion INTO dataVersion_i FROM m_windrose WHERE nodeid = deviceid_in  AND ms_date = date_in;
     IF dataVersion_i > 0 THEN
	     UPDATE m_windrose SET 
	    `O_N` = o_n,
	    `S_N` = s_n,
	    `O_NNE` = o_nne,
	    `S_NNE` = s_nne,
	    `O_NE` = o_ne,
	    `S_NE` = s_ne,
	    `O_ENE` = o_ene,
	    `S_ENE` = s_ene,
	    `O_E` = o_e,
	    `S_E` = s_e,
	    `O_ESE` = o_ese,
	    `S_ESE` = s_ese,
	    `O_SE` = o_se,
	    `S_SE` = s_se,
	    `O_SSE` = o_sse,
	    `S_SSE` = s_sse,
	    `O_S` = o_s,
	    `S_S` = s_s,
	    `O_SSW` = o_ssw,
	    `S_SSW` = s_ssw,
	    `O_SW` = o_sw,
	    `S_SW` = s_sw,
	    `O_WSW` = o_wsw,
	    `S_WSW` = s_wsw,
	    `O_W` = o_w,
	    `S_W` = s_w,
	    `O_WNW` = o_wnw,
	    `S_WNW` = s_wnw,
	    `O_NW` = o_nw,
	    `S_NW` = s_nw,
	    `O_NNW` = o_nnw,
	    `S_NNW` = s_nnw,
	    `windcalm` = calm,
	    `sum` = sum_i,
	    isupdate = 0,
	    dataVersion = (dataVersion_i+1)
	    WHERE 
	    nodeid = deviceid_in AND ms_date = date_in;
    ELSE
	    UPDATE m_windrose SET 
	    `O_N` = o_n,
	    `S_N` = s_n,
	    `O_NNE` = o_nne,
	    `S_NNE` = s_nne,
	    `O_NE` = o_ne,
	    `S_NE` = s_ne,
	    `O_ENE` = o_ene,
	    `S_ENE` = s_ene,
	    `O_E` = o_e,
	    `S_E` = s_e,
	    `O_ESE` = o_ese,
	    `S_ESE` = s_ese,
	    `O_SE` = o_se,
	    `S_SE` = s_se,
	    `O_SSE` = o_sse,
	    `S_SSE` = s_sse,
	    `O_S` = o_s,
	    `S_S` = s_s,
	    `O_SSW` = o_ssw,
	    `S_SSW` = s_ssw,
	    `O_SW` = o_sw,
	    `S_SW` = s_sw,
	    `O_WSW` = o_wsw,
	    `S_WSW` = s_wsw,
	    `O_W` = o_w,
	    `S_W` = s_w,
	    `O_WNW` = o_wnw,
	    `S_WNW` = s_wnw,
	    `O_NW` = o_nw,
	    `S_NW` = s_nw,
	    `O_NNW` = o_nnw,
	    `S_NNW` = s_nnw,
	    `windcalm` = calm,
	    `sum` = sum_i,
	    isupdate = 0 
	    WHERE 
	    nodeid = deviceid_in AND  ms_date = date_in;
    END IF;
    COMMIT;
END;;



-- Dumping structure for procedure galaxy_dev.sp_hourLuxMath
DROP PROCEDURE IF EXISTS `sp_hourLuxMath`;;

CREATE PROCEDURE `sp_hourLuxMath`(uid VARCHAR(100),deviceid_in VARCHAR(50),datetime_in DATETIME, OUT isok_out INT)
BEGIN
     DECLARE hour_i INT;
     DECLARE starttime_t   DATETIME DEFAULT datetime_in;
     DECLARE startvalue_d DOUBLE DEFAULT 0;
     DECLARE currenttime_t DATETIME;
     DECLARE currentvalue_d DOUBLE DEFAULT 0;
     DECLARE endtime_t     DATETIME DEFAULT ADDDATE(datetime_in,INTERVAL +1 HOUR);
     DECLARE endvalue_d DOUBLE DEFAULT 0;
     DECLARE isfirst BOOL DEFAULT TRUE;
     DECLARE sumlux_d DOUBLE DEFAULT 0;
     DECLARE season_i INT;
     DECLARE isExist BOOL;
     DECLARE dataVersion_i INT DEFAULT -1;
     
     DECLARE _done         INT         DEFAULT 0;
     DECLARE cur CURSOR FOR SELECT sensorPhysicalvalue,createtime FROM v_hourlux_tmp;
     DECLARE CONTINUE HANDLER FOR NOT FOUND SET _done = 1;
     DECLARE EXIT HANDLER FOR SQLEXCEPTION SET isok_out = 0;
            
     -- init out parameter
     SET isok_out = 1;
            
     -- create tmp hourlux view
     SET hour_i = HOUR(datetime_in);
     SET @date_str = DATE_FORMAT(datetime_in, '%Y_%m_%d');
     SET @sql_str = CONCAT("CREATE VIEW  v_hourlux_tmp as SELECT sensorPhysicalvalue,createtime FROM v_data_",deviceid_in,"_",@date_str," WHERE  sensorPhysicalid = 41 AND HOUR(createtime) = ",hour_i," AND state IN (1,2,3)");  
     PREPARE sql_createHourLuxView FROM  @sql_str;
     EXECUTE sql_createHourLuxView; 
     DEALLOCATE PREPARE sql_createHourLuxView;
     
     -- exec cursor
     OPEN cur;
     REPEAT
         FETCH cur INTO currentvalue_d,currenttime_t;         
         IF NOT _done THEN
             IF isfirst THEN
                 SET startvalue_d = currentvalue_d;
                 SET isfirst = FALSE;            
             END IF;
             
             -- sum lux
             SET sumlux_d = sumlux_d + fun_getLuxIntegral(starttime_t,startvalue_d,currenttime_t,currentvalue_d);
         END IF;
     UNTIL _done END REPEAT;
     CLOSE cur;
     -- add end lux
     SET endvalue_d = currentvalue_d;
     SET sumlux_d = sumlux_d + fun_getLuxIntegral(starttime_t,startvalue_d,endtime_t,endvalue_d);
     -- get hour integral
     SET sumlux_d = ROUND(sumlux_d/3600,0);
     -- get season
     SET season_i = fun_getSeason(DATE(datetime_in));    
     -- data into table
     SET isExist = fun_isExistHourLux(deviceid_in, datetime_in);
     START TRANSACTION;    
     IF isExist THEN
	 SELECT dataVersion INTO dataVersion_i FROM m_tbl_lxh_acc WHERE nodeid = deviceid_in AND DATE_FORMAT(ms_datetime,'%Y-%m-%d %H') = DATE_FORMAT(datetime_in,'%Y-%m-%d %H');
	 IF dataVersion_i > 0 THEN
		UPDATE m_tbl_lxh_acc SET lx_h = sumlux_d, season = season_i, isupdate = 0, dataVersion = (dataVersion_i+1) WHERE nodeid = deviceid_in and DATE_FORMAT(ms_datetime,'%Y-%m-%d %H') = DATE_FORMAT(datetime_in,'%Y-%m-%d %H');
	 ELSE
		UPDATE m_tbl_lxh_acc SET lx_h = sumlux_d, season = season_i, isupdate = 0 WHERE nodeid = deviceid_in AND DATE_FORMAT(ms_datetime,'%Y-%m-%d %H') = DATE_FORMAT(datetime_in,'%Y-%m-%d %H');
	 END IF;
     ELSE
         INSERT INTO m_tbl_lxh_acc(id,nodeid,lx_h,ms_datetime,season) VALUES(uid,deviceid_in,sumlux_d,DATE_FORMAT(datetime_in,'%Y-%m-%d %H:00:00'),season_i);
     END IF;
     COMMIT;
     
     -- drop temp view
     DROP VIEW v_hourlux_tmp;
 END;;



-- Dumping structure for procedure galaxy_dev.sp_hourRbMath
DROP PROCEDURE IF EXISTS `sp_hourRbMath`;;

CREATE PROCEDURE `sp_hourRbMath`(uid VARCHAR(100),deviceid_in VARCHAR(50), datetime_in DATETIME, OUT isok_out INT)
BEGIN
     DECLARE hour_i INT;
     DECLARE isExist BOOL;
     DECLARE dataVersion_i INT DEFAULT -1;
     DECLARE EXIT HANDLER FOR SQLEXCEPTION SET isok_out = 0;
     
     
     -- init out parameter
     SET isok_out = 1;  
     SET hour_i = HOUR(datetime_in);
     SET @date_str = DATE_FORMAT(datetime_in, '%Y_%m_%d');
     SET @sql_str = CONCAT("SELECT SUM(sensorPhysicalvalue) INTO @hourRbSum_d FROM v_data_",deviceid_in,"_",@date_str," WHERE sensorPhysicalid = 47 AND state IN (1,2,3) AND HOUR(createtime) = ",hour_i);
     PREPARE sql_selectHourRb FROM  @sql_str;
     EXECUTE sql_selectHourRb ;
     DEALLOCATE PREPARE sql_selectHourRb;
     SET isExist = fun_isExistHourRb(deviceid_in, datetime_in);
     IF @hourRbSum_d IS NULL THEN
         SET @hourRbSum_d = 0;
     END IF;
     START TRANSACTION;    
     IF isExist THEN
	 SELECT dataVersion INTO dataVersion_i FROM m_tbl_rb_hour_acc WHERE nodeid = deviceid_in AND DATE_FORMAT(ms_datetime,'%Y-%m-%d %H') = DATE_FORMAT(datetime_in,'%Y-%m-%d %H');
	 IF dataVersion_i > 0 THEN
		UPDATE m_tbl_rb_hour_acc SET rb = @hourRbSum_d, isupdate = 0, dataVersion = (dataVersion_i+1) WHERE nodeid = deviceid_in AND DATE_FORMAT(ms_datetime,'%Y-%m-%d %H') = DATE_FORMAT(datetime_in,'%Y-%m-%d %H');
	 ELSE
		UPDATE m_tbl_rb_hour_acc SET rb = @hourRbSum_d, isupdate = 0 WHERE nodeid = deviceid_in AND  DATE_FORMAT(ms_datetime,'%Y-%m-%d %H') = DATE_FORMAT(datetime_in,'%Y-%m-%d %H');
         END IF;
     ELSE 
         INSERT INTO m_tbl_rb_hour_acc(id,nodeid,rb,ms_datetime) VALUES(uid,deviceid_in,@hourRbSum_d,DATE_FORMAT(datetime_in,'%Y-%m-%d %H:00:00'));
     END IF;
     COMMIT;   
 END;;



-- Dumping structure for procedure galaxy_dev.sp_reMathFlag
DROP PROCEDURE IF EXISTS `sp_reMathFlag`;;

CREATE PROCEDURE `sp_reMathFlag`(OUT isok_out INT)
top:BEGIN
     DECLARE _done         INT DEFAULT 0;
     DECLARE sensorPhysicalid_i    INT;
     DECLARE type_i        INT;
     DECLARE deviceid_s    VARCHAR(50);
     DECLARE starttime_t  DATETIME;
     DECLARE endtime_t    DATETIME;
     DECLARE count_i      INT;
     DECLARE id_i	  INT;
     
     DECLARE enumPort_s    INT;
     DECLARE sensorid_s    INT;
     DECLARE dataVersion_i INT DEFAULT -1;
      
     -- define cursor is mast at after of define handler
     DECLARE cur CURSOR FOR SELECT sensorPhysicalid FROM m_nodesensor WHERE nodeid = deviceid_s;
     DECLARE CONTINUE HANDLER FOR NOT FOUND SET _done = 1;
     DECLARE EXIT HANDLER FOR SQLEXCEPTION SET isok_out = 0;
     
     SET isok_out = 1;
     
     -- check is exist record
     SELECT COUNT(1) INTO count_i FROM m_tbl_sdcardrecord WHERE step = 3;
     IF count_i = 0 THEN
         LEAVE top;
     END IF;
         
     -- check is exist record
     SELECT id,deviceid,stime,etime INTO id_i,deviceid_s,starttime_t,endtime_t FROM m_tbl_sdcardrecord WHERE step = 3 LIMIT 0,1; 
      
     -- check device type 
     SELECT nodeType INTO type_i FROM m_nodeinfo WHERE nodeid = deviceid_s;
     IF type_i <> 1 THEN -- only math node device
         UPDATE m_tbl_sdcardrecord SET step = 4 WHERE id = id_i;
         LEAVE top;
     END IF;              
     
     START TRANSACTION;
     -- exec cursor
     OPEN cur;
     REPEAT
          FETCH cur INTO sensorPhysicalid_i;
          IF NOT _done THEN
              
          CASE sensorPhysicalid_i
              WHEN 41 THEN -- lux
		  -- 判断数据同步版本号是否为0，不为0则版本号+1
		  SELECT dataVersion INTO dataVersion_i FROM m_tbl_lxh_acc WHERE nodeid = deviceid_s AND ms_datetime BETWEEN DATE_FORMAT(starttime_t,'%Y-%m-%d %H:00:00') AND DATE_FORMAT(endtime_t,'%Y-%m-%d %H:59:59');
		  IF dataVersion_i > 0 THEN
		     UPDATE m_tbl_lxh_acc SET isupdate = 1, dataVersion = (dataVersion_i+1) WHERE nodeid = deviceid_s AND ms_datetime BETWEEN DATE_FORMAT(starttime_t,'%Y-%m-%d %H:00:00') AND DATE_FORMAT(endtime_t,'%Y-%m-%d %H:59:59');
		  ELSE
		     UPDATE m_tbl_lxh_acc SET isupdate = 1 WHERE nodeid = deviceid_s  AND ms_datetime BETWEEN DATE_FORMAT(starttime_t,'%Y-%m-%d %H:00:00') AND DATE_FORMAT(endtime_t,'%Y-%m-%d %H:59:59');
                  END IF;
                  SET dataVersion_i = -1;
                  SELECT dataVersion INTO dataVersion_i FROM m_avgdata WHERE nodeid = deviceid_s AND ms_date BETWEEN DATE(starttime_t) AND DATE(endtime_t);
                  IF dataVersion_i> 0 THEN
                        UPDATE m_avgdata SET isupdate = 1, dataVersion = (dataVersion_i+1) WHERE nodeid = deviceid_s  AND ms_date BETWEEN DATE(starttime_t) AND DATE(endtime_t);
                  ELSE
                        UPDATE m_avgdata SET isupdate = 1 WHERE nodeid = deviceid_s AND  ms_date BETWEEN DATE(starttime_t) AND DATE(endtime_t);
                  END IF;
              
              WHEN 47 THEN -- rb
                  SELECT dataVersion INTO dataVersion_i FROM m_tbl_rb_hour_acc WHERE nodeid = deviceid_s AND ms_datetime BETWEEN DATE_FORMAT(starttime_t,'%Y-%m-%d %H:00:00') AND DATE_FORMAT(endtime_t,'%Y-%m-%d %H:59:59');
                  IF dataVersion_i > 0 THEN
                        UPDATE m_tbl_rb_hour_acc SET isupdate = 1,dataVersion = (dataVersion_i+1) WHERE nodeid = deviceid_s AND  ms_datetime BETWEEN DATE_FORMAT(starttime_t,'%Y-%m-%d %H:00:00') AND DATE_FORMAT(endtime_t,'%Y-%m-%d %H:59:59');
                  ELSE
                        UPDATE m_tbl_rb_hour_acc SET isupdate = 1 WHERE nodeid = deviceid_s  AND ms_datetime BETWEEN DATE_FORMAT(starttime_t,'%Y-%m-%d %H:00:00') AND DATE_FORMAT(endtime_t,'%Y-%m-%d %H:59:59');
                  END IF;
                  
                  SET dataVersion_i = -1;
                  SELECT dataVersion INTO dataVersion_i FROM m_tbl_rb_day_acc WHERE nodeid = deviceid_s AND  ms_date BETWEEN DATE(starttime_t) AND DATE(endtime_t);
                  IF dataVersion_i > 0 THEN
                        UPDATE m_tbl_rb_day_acc SET isupdate = 1,dataVersion = (dataVersion_i+1) WHERE nodeid = deviceid_s AND  ms_date BETWEEN DATE(starttime_t) AND DATE(endtime_t); 
                  ELSE
                        UPDATE m_tbl_rb_day_acc SET isupdate = 1 WHERE nodeid = deviceid_s AND  ms_date BETWEEN DATE(starttime_t) AND DATE(endtime_t); 
                  END IF;
                  
                  SET dataVersion_i = -1;
                  SELECT dataVersion INTO dataVersion_i FROM m_avgdata WHERE nodeid = deviceid_s AND  ms_date BETWEEN DATE(starttime_t) AND DATE(endtime_t);
                  IF dataVersion_i > 0 THEN		
			UPDATE m_avgdata SET isupdate = 1, dataVersion = (dataVersion_i+1) WHERE nodeid = deviceid_s AND  ms_date BETWEEN DATE(starttime_t) AND DATE(endtime_t);
		  ELSE
			UPDATE m_avgdata SET isupdate = 1 WHERE nodeid = deviceid_s  AND ms_date BETWEEN DATE(starttime_t) AND DATE(endtime_t);
		  END IF;
              
              WHEN 48 THEN -- WDD
		  SELECT dataVersion INTO dataVersion_i FROM m_windrose WHERE nodeid = deviceid_s  AND ms_date BETWEEN DATE(starttime_t) AND DATE(endtime_t);
		  IF dataVersion_i > 0 THEN
			UPDATE m_windrose SET isupdate = 1, dataVersion = (dataVersion_i+1) WHERE nodeid = deviceid_s AND ms_date BETWEEN DATE(starttime_t) AND DATE(endtime_t); 
		  ELSE
			UPDATE m_windrose SET isupdate = 1 WHERE nodeid = deviceid_s AND  ms_date BETWEEN DATE(starttime_t) AND DATE(endtime_t); 
                  END IF;
                  
                  SET dataVersion_i = -1;
                  SELECT dataVersion INTO dataVersion_i FROM m_avgdata WHERE nodeid = deviceid_s AND  ms_date BETWEEN DATE(starttime_t) AND DATE(endtime_t);
                  IF dataVersion_i > 0 THEN
			UPDATE m_avgdata SET isupdate = 1, dataVersion = (dataVersion_i+1) WHERE nodeid = deviceid_s AND ms_date BETWEEN DATE(starttime_t) AND DATE(endtime_t);
		  ELSE
			UPDATE m_avgdata SET isupdate = 1 WHERE nodeid = deviceid_s AND ms_date BETWEEN DATE(starttime_t) AND DATE(endtime_t);
                  END IF;
                               
              ELSE -- avg&peak
                  SELECT dataVersion INTO dataVersion_i FROM m_avgdata WHERE nodeid = deviceid_s AND  ms_date BETWEEN DATE(starttime_t) AND DATE(endtime_t);
                  IF dataVersion_i > 0 THEN
			UPDATE m_avgdata SET isupdate = 1, dataVersion = (dataVersion_i+1) WHERE nodeid = deviceid_s  AND ms_date BETWEEN DATE(starttime_t) AND DATE(endtime_t);
		  ELSE
			UPDATE m_avgdata SET isupdate = 1 WHERE nodeid = deviceid_s  AND ms_date BETWEEN DATE(starttime_t) AND DATE(endtime_t);
                  END IF;
          END CASE;
          
          END IF;
      UNTIL _done END REPEAT;
      CLOSE cur;   
      
      UPDATE m_tbl_sdcardrecord SET step = 4 WHERE id = id_i;
      COMMIT; 
END;;

/*!40014 SET FOREIGN_KEY_CHECKS=1 */;;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;;

-- DELIMITER ;
