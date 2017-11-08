package com.sinosoft.lis.easyscan.service.util;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;

public class PolStateCheck {
	private static Logger logger = Logger.getLogger(PolStateCheck.class);

	public static boolean isPolAfterUW(String prtno) {
		// modify by lzf 2013-04-19
		// String sql = "select MissionProp18 from lwmission where "
		// + "activityid='0000001100' and MissionProp1='" + prtno + "'";
		String sql = "select b.uwstate from lwmission a,lccuwmaster b where "
				+ " a.activityid in (select activityid from lwactivity where functionid ='10010028')"
				+ " and a.missionprop1 = b.contno and a.MissionProp1='"
				+ "?prtno?" + "'";
		SQLwithBindVariables sqlnv1 = new SQLwithBindVariables();
		sqlnv1.sql(sql);
		sqlnv1.put("prtno", prtno);
		SSRS tSSRS = new ExeSQL().execSQL(sqlnv1);
		if (tSSRS.getMaxRow() > 1) {
			throw new RuntimeException("保单包含多个核保状态");
		}
		if (tSSRS.getMaxRow() == 1) {
			String state = tSSRS.GetText(1, 1);
			if (state.compareTo("1") < 0 || state.compareTo("8") > 0)
				throw new RuntimeException("保单核保状态错误" + state);
			return true;
		}
		
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			sql = "select b.uwstate from lbmission a,lccuwmaster b where "
					+ "a.activityid in (select activityid from lwactivity where functionid ='10010028')"
					+ " and a.missionprop1 = b.contno and a.MissionProp1='"
					+ "?prtno?"
					+ "' and rownum=1 order by a.makedate,a.maketime desc";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			sql = "select b.uwstate from lbmission a,lccuwmaster b where "
					+ "a.activityid in (select activityid from lwactivity where functionid ='10010028')"
					+ " and a.missionprop1 = b.contno and a.MissionProp1='"
					+ "?prtno?"
					+ "' order by a.makedate,a.maketime desc limit 1";
		}

		SQLwithBindVariables sqlnv2 = new SQLwithBindVariables();
		sqlnv2.sql(sql);
		sqlnv2.put("prtno", prtno);
		tSSRS = new ExeSQL().execSQL(sqlnv2);
		if (tSSRS.getMaxRow() == 1) {
			String state = tSSRS.GetText(1, 1);
			if (state.compareTo("1") < 0 || state.compareTo("8") > 0)
				throw new RuntimeException("保单核保状态错误" + state);
			return true;
		}
		return false;
	}

	/**
	 * 校验核保4状态
	 * 
	 * @param prtno
	 * @return
	 */
	public static boolean isPolAtUW4(String prtno) {
		// 未签单的需申请
		String sql = "select count(1) from lwmission where "
				+ "activityid in(select activityid from lwactivity where functionid ='10010042') and MissionProp1='"
				+ "?prtno?" + "'";
		SQLwithBindVariables sqlnv3 = new SQLwithBindVariables();
		sqlnv3.sql(sql);
		sqlnv3.put("prtno", prtno);
		if (!new ExeSQL().getOneValue(sqlnv3).equals("0"))
			return false;

		// 人工核保4状态
		// sql = "select MissionProp18 from lwmission where "
		// + "activityid='0000001100' and MissionProp1='" + prtno + "'";
		sql = "select b.uwstate from lwmission a,lccuwmaster b where "
				+ " a.activityid in (select activityid from lwactivity where functionid ='10010028')"
				+ " and a.missionprop1 = b.contno and a.MissionProp1='"
				+ "?prtno?" + "'";
		SQLwithBindVariables sqlnv4 = new SQLwithBindVariables();
		sqlnv4.sql(sql);
		sqlnv4.put("prtno", prtno);
		SSRS tSSRS = new ExeSQL().execSQL(sqlnv4);
		if (tSSRS.getMaxRow() > 1) {
			throw new RuntimeException("保单包含多个核保状态");
		}
		if (tSSRS.getMaxRow() == 1) {
			String state = tSSRS.GetText(1, 1);
			if ("6".equals(state))
				return true;
			if ("4".equals(state)) {
				// 如果是四状态,需要看一下是否在问题件修改岗,如果在问题件修改岗,并且已回复,不允许修改.
				// String tSQL =
				// "select decode(count(*),0,0,1) from lwmission where activityid = '0000001002' "
				// +
				// " and processid = '0000000003' and missionprop1='"+prtno+"' "
				// + " and missionprop9 = '1' ";
				String tSQL = "select ( case count(*) when 0 then 0 else 1 end) from lwmission where activityid in (select activityid from lwactivity where functionid ='10010004') "
						+ " and missionprop1='"
						+ "?prtno?"
						+ "' "
						+ " and missionprop9 = '1' ";
				SQLwithBindVariables sqlnv5 = new SQLwithBindVariables();
				sqlnv5.sql(tSQL);
				sqlnv5.put("prtno", prtno);
				ExeSQL tExeSQL = new ExeSQL();
				String tValue = tExeSQL.getOneValue(sqlnv5);
				if (tValue == null || tValue.equals("")
						|| Integer.parseInt(tValue) <= 0) {
					//
					// 如果处于客户号手工合并岗,也不允许上传
					// tongmeng 2009-05-12 modify
					// 如果处于客户号手工合并,也不允许处理
					// sql = "select 1 from lwmission where "
					// + "activityid='0000001404' and MissionProp1='" + prtno +
					// "'";
					sql = "select 1 from lwmission where "
							+ "activityid in (select activityid from lwactivity where functionid ='10010056') and MissionProp1='"
							+ "?prtno?" + "'";
					SQLwithBindVariables sqlnv6 = new SQLwithBindVariables();
					sqlnv6.sql(sql);
					sqlnv6.put("prtno", prtno);
					tSSRS = new ExeSQL().execSQL(sqlnv6);
					if (tSSRS.getMaxRow() >= 1) {
						return false;
					} else {
						return true;
					}
				}

				else if (Integer.parseInt(tValue) > 0) {
					return false;
				}

			} else {
				return false;
			}
			// return true;
		} else {
			// String tSQL =
			// "select decode(count(*),0,0,1) from lwmission where activityid = '0000001002' "
			// + " and processid = '0000000003' and missionprop1='"+prtno+"' "
			// + " and missionprop9 = '1' ";
			String tSQL = "select ( case count(*) when 0 then 0 else 1 end) from lwmission where activityid in (select activityid from lwactivity where functionid ='10010004') "
					+ " and missionprop1='"
					+ "?prtno?"
					+ "' "
					+ " and missionprop9 = '1' ";
			SQLwithBindVariables sqlnv7 = new SQLwithBindVariables();
			sqlnv7.sql(tSQL);
			sqlnv7.put("prtno", prtno);
			ExeSQL tExeSQL = new ExeSQL();
			String tValue = tExeSQL.getOneValue(sqlnv7);
			if (tValue == null || tValue.equals("")
					|| Integer.parseInt(tValue) <= 0) {
				//
				// 如果处于客户号手工合并岗,也不允许上传
				// tongmeng 2009-05-12 modify
				// 如果处于客户号手工合并,也不允许处理
				// sql = "select 1 from lwmission where "
				// + "activityid='0000001404' and MissionProp1='" + prtno + "'";
				sql = "select 1 from lwmission where "
						+ "activityid in (select activityid from lwactivity where functionid ='10010056') and MissionProp1='"
						+ "?prtno?" + "'";
				SQLwithBindVariables sqlnv8 = new SQLwithBindVariables();
				sqlnv8.sql(sql);
				sqlnv8.put("prtno", prtno);
				tSSRS = new ExeSQL().execSQL(sqlnv8);
				if (tSSRS.getMaxRow() >= 1) {
					return false;
				} else {
					return true;
				}
			}

			else if (Integer.parseInt(tValue) > 0) {
				return false;
			}
		}
		/*
		 * else { //tongmeng 2009-05-12 modify //如果处于客户号手工合并,也不允许处理 sql =
		 * "select 1 from lwmission where " +
		 * "activityid='0000001404' and MissionProp1='" + prtno + "'"; tSSRS =
		 * new ExeSQL().execSQL(sql); if (tSSRS.getMaxRow() >= 1) { return
		 * false; } }
		 */
		// 校验主状态是否处于待签单状态,处于,不允许上传,否则可以上传
		// sql = "select 1 from lwmission where "
		// + "activityid='0000001150' and MissionProp1='" + prtno + "'";
		// tSSRS = new ExeSQL().execSQL(sql);
		// if (tSSRS.getMaxRow() > 1) {
		// return false;
		// }
		// else
		// {
		// return true;
		// }
		/*
		 * // 复核岗下发业务员问题件未回复状态 sql = "select 1 from lcissuepol where contno='" +
		 * prtno + "' and operatepos='5' and state is null and needprint='Y'";
		 * tSSRS = new ExeSQL().execSQL(sql); if (tSSRS.getMaxRow() >= 1) {
		 * return true; }
		 */
		return false;
	}

	public static boolean isPolAvailable(String prtno) {
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setPrtNo(prtno);
		tLCContDB.setAppFlag("1");
		return tLCContDB.getCount() > 0;
	}

	/**
	 * 校验是否录入完毕
	 * 
	 * @param prtno
	 * @return
	 */
	public static boolean isPolInputed(String prtno) {
		String sql = "select 1 from es_doc_main where doccode='" + "?prtno?"
				+ "' and inputstate='1' and subtype like 'UA%'";
		SQLwithBindVariables sqlnv9 = new SQLwithBindVariables();
		sqlnv9.sql(sql);
		sqlnv9.put("prtno", prtno);
		return new ExeSQL().execSQL(sqlnv9).getMaxRow() > 0;
	}

	/**
	 * 校验是否扫描完毕
	 * 
	 * @param prtno
	 * @return
	 */
	public static boolean isPolScaned(String prtno) {
		String sql = "select 1 from es_doc_main where doccode='" + "?prtno?"
				+ "' and subtype like 'UA%'";
		SQLwithBindVariables sqlnv10 = new SQLwithBindVariables();
		sqlnv10.sql(sql);
		sqlnv10.put("prtno", prtno);
		return new ExeSQL().execSQL(sqlnv10).getMaxRow() > 0;
	}

	public static boolean isPolSameManage(String prtno, String managecom) {
		// String
		// sql="select 1 from es_doc_main where doccode='"+prtno+"' and subtype like 'UA%' and managecom like '"+managecom+"%'";
		// return new ExeSQL().execSQL(sql).getMaxRow()>0;
		return true;
	}

	public static boolean isPolInSystem(String prtno) {
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setPrtNo(prtno);
		return tLCContDB.getCount() > 0;
	}

	public static boolean isPolHasHZ(String prtno) {
		String sql = "select count(1) from lccont where CustomGetPolDate is not null and appflag='1' and prtno='"
				+ "?prtno?" + "'";
		SQLwithBindVariables sqlnv11 = new SQLwithBindVariables();
		sqlnv11.sql(sql);
		sqlnv11.put("prtno", prtno);
		return !new ExeSQL().getOneValue(sqlnv11).equals("0");
	}

	public static boolean isPolBpo(String prtno) {
		String sql = "select count(1) from lwmission where missionprop1='"
				+ "?prtno?"
				+ "' and activityid in (select activityid from lwactivity where functionid ='10010006')";
		SQLwithBindVariables sqlnv12 = new SQLwithBindVariables();
		sqlnv12.sql(sql);
		sqlnv12.put("prtno", prtno);
		return !new ExeSQL().getOneValue(sqlnv12).equals("0");
	}

	public static boolean isPolHasWorkflow(String prtno) {
		String sql = "select count(1) from lwmission where missionprop1='"
				+ "?prtno?" + "'";
		SQLwithBindVariables sqlnv13 = new SQLwithBindVariables();
		sqlnv13.sql(sql);
		sqlnv13.put("prtno", prtno);
		return !new ExeSQL().getOneValue(sqlnv13).equals("0");
	}

	public static boolean isPolSigned(String prtno) {
		String sql = "select count(1) from lccont where prtno='" + "?prtno?"
				+ "' and appflag<>'0'";
		SQLwithBindVariables sqlnv14 = new SQLwithBindVariables();
		sqlnv14.sql(sql);
		sqlnv14.put("prtno", prtno);
		return !new ExeSQL().getOneValue(sqlnv14).equals("0");
	}

	/** 如果未打印，不允许扫描上传回执 */
	public static boolean isPrinted(String prtno) {
		String sql = "select count(1) from lccont where prtno='" + "?prtno?"
				+ "' and printcount>0";
		SQLwithBindVariables sqlnv15 = new SQLwithBindVariables();
		sqlnv15.sql(sql);
		sqlnv15.put("prtno", prtno);
		return !new ExeSQL().getOneValue(sqlnv15).equals("0");
	}
}
