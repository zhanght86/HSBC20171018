package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import com.sinosoft.utility.DBConnPool;

public class QueryViewParameter {
private static Logger logger = Logger.getLogger(QueryViewParameter.class);
	
	//RuleMapCached mRuleMapCached = RuleMapCached.getInstance();
	
	public QueryViewParameter() {

	}

	
	public String queryForViewParameter(String tableName, String id,String tLan) {
		String viewParameter = "";
		String sql = "select viewparameter from " + tableName + " where id=?";

		Connection conn = DBConnPool.getConnection();
		try {

			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, id);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				viewParameter = rs.getString(1);
			}
			rs.close();
			st.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return viewParameter;
	}

	public static void main(String[] args) {
		QueryViewParameter aQueryViewParameter = new QueryViewParameter();
		String value = aQueryViewParameter.queryForViewParameter("LRTemplateT",
				"00000000000000000094","");
		logger.debug("============================================");
		logger.debug("value::" + value);
		logger.debug("============================================");
	}

}
