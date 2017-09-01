package com.iycharge.server.domain.repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.iycharge.server.report.entity.AcDcDataBean;
import com.iycharge.server.report.entity.DataBean;
import com.iycharge.server.report.entity.RecordData;

@Repository
public class BeanRepository {
	
	private final JdbcTemplate jdbcTemplate;

    @Autowired   
    public BeanRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
	
    public List<DataBean> getAll(String sql) {
        //String sql = "select province as province,SUM(moneyac) as money,DATE_FORMAT(day,'%Y-%m') as time FROM r_charging_data GROUP BY province,DATE_FORMAT(day,'%Y-%m')";
        List<DataBean> data = 
        		jdbcTemplate.query(sql, new RowMapper<DataBean>() {
            @Override           
            public DataBean mapRow(ResultSet resultSet, int i) throws SQLException {
            	DataBean dataBean = new DataBean();
            	dataBean.setTypevalue(resultSet.getString("typevalue"));
            	dataBean.setCountnums(resultSet.getString("countnums"));
            	dataBean.setTime(resultSet.getString("time"));
            	dataBean.setAcvalue(resultSet.getString("acvalue"));
            	dataBean.setDcvalue(resultSet.getString("dcvalue"));
            	dataBean.setAppvalue(resultSet.getString("appvalue"));
            	dataBean.setCardvalue(resultSet.getString("cardvalue"));
                return dataBean;
            }
        });

        return data;
    }
    public List<DataBean> findEventNum(String sql) {
        //String sql = "select province as province,SUM(moneyac) as money,DATE_FORMAT(day,'%Y-%m') as time FROM r_charging_data GROUP BY province,DATE_FORMAT(day,'%Y-%m')";
        List<DataBean> data = 
        		jdbcTemplate.query(sql, new RowMapper<DataBean>() {
            @Override           
            public DataBean mapRow(ResultSet resultSet, int i) throws SQLException {
            	DataBean dataBean = new DataBean();
            	dataBean.setTypevalue(resultSet.getString("typevalue"));
            	dataBean.setCountnums(resultSet.getString("countnums"));
            	dataBean.setTime(resultSet.getString("time"));
                return dataBean;
            }
        });

        return data;
    }
    public List<RecordData> findMoneyCount(String sql) {
        List<RecordData> data = 
        		jdbcTemplate.query(sql, new RowMapper<RecordData>() {
            @Override           
            public RecordData mapRow(ResultSet resultSet, int i) throws SQLException {
            	RecordData dataBean = new RecordData();
            	dataBean.setCompanyCost(resultSet.getBigDecimal("companyCost"));
            	dataBean.setCompanyRecord(resultSet.getBigDecimal("companyRecord"));
            	
            	dataBean.setPersonRecord(resultSet.getBigDecimal("personRecord"));
            	dataBean.setPersonCost(resultSet.getBigDecimal("personCost"));
            	
            	dataBean.setCostTotal(resultSet.getBigDecimal("costTotal"));
            	dataBean.setRecordTotal(resultSet.getBigDecimal("recordTotal"));
            	
            	dataBean.setTime(resultSet.getString("time"));
                return dataBean;
            }
        });

        return data;
    }
    public List<DataBean> findEventType(String sql) {
        //String sql = "select province as province,SUM(moneyac) as money,DATE_FORMAT(day,'%Y-%m') as time FROM r_charging_data GROUP BY province,DATE_FORMAT(day,'%Y-%m')";
        List<DataBean> data = 
        		jdbcTemplate.query(sql, new RowMapper<DataBean>() {
            @Override           
            public DataBean mapRow(ResultSet resultSet, int i) throws SQLException {
            	DataBean dataBean = new DataBean();
            	dataBean.setTypevalue(resultSet.getString("typevalue"));
            	dataBean.setEventnums(resultSet.getLong("eventnums"));
                return dataBean;
            }
        });

        return data;
    }
    
    public List<String> getType(String sql) {
        //String sql = "select province as province,SUM(moneyac) as money,DATE_FORMAT(day,'%Y-%m') as time FROM r_charging_data GROUP BY province,DATE_FORMAT(day,'%Y-%m')";
        List<String> type = 
        		jdbcTemplate.query(sql, new RowMapper<String>() {
            @Override           
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("typevalue");
            }
        });

        return type;
    }
    
    public List<AcDcDataBean> findAcDc(String sql) {
        //String sql = "select province as province,SUM(moneyac) as money,DATE_FORMAT(day,'%Y-%m') as time FROM r_charging_data GROUP BY province,DATE_FORMAT(day,'%Y-%m')";
        List<AcDcDataBean> acdc = 
        		jdbcTemplate.query(sql, new RowMapper<AcDcDataBean>() {
            @Override           
            public AcDcDataBean mapRow(ResultSet resultSet, int i) throws SQLException {
            	AcDcDataBean acDcDataBean = new AcDcDataBean();
            	acDcDataBean.setAcvalue(resultSet.getString("acvalue"));
            	acDcDataBean.setDcvalue(resultSet.getString("dcvalue"));
            	acDcDataBean.setAppvalue(resultSet.getString("appvalue"));
            	acDcDataBean.setCardvalue(resultSet.getString("cardvalue"));
                return acDcDataBean;
            }
        });

        return acdc;
    }
}
