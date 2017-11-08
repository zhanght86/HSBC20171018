package com.sinosoft.lis.maxnomanage;

import java.sql.Connection;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.CachedLDMaxNo;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDMaxNoRuleLimitSchema;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class SerialNo extends MaxNoElement {
	public SerialNo(String tType, Hashtable tProps, Hashtable tOtherValue) {
		super(tType, tProps, tOtherValue);
	}

	private static Logger logger = Logger.getLogger(SerialNo.class);

	private CachedLDMaxNo mCached = CachedLDMaxNo.getInstance();
	private String Limit;

	private String getLimit() {
		String cNoType = this.getType();
		Hashtable tOthers = this.getOthers();
		VData tMaxNoVData = this.mCached.findLDMaxNoRuleLimit(cNoType);
		if (tMaxNoVData == null) {
			return null;
		}
		String tFinalNo = "";
		MaxNoElement tMaxNoElement = null;
		for (int i = 0; i < tMaxNoVData.size(); i++) {
			String tTempNo = "";
			VData tempMaxNoData = (VData) tMaxNoVData.get(i);

			LDMaxNoRuleLimitSchema tempLDMaxNoRuleLimitSchema = (LDMaxNoRuleLimitSchema) tempMaxNoData
					.getObjectByObjectName("LDMaxNoRuleLimitSchema", 0);
			Hashtable propHashtable = new Hashtable();
			propHashtable = (Hashtable) tempMaxNoData.getObjectByObjectName(
					"Hashtable", 0);

			String tCode = tempLDMaxNoRuleLimitSchema.getCode();
			logger.debug("tCode:" + tCode);

			if (tCode.equals("SerialNo")) {
				tMaxNoElement = new SerialNo(cNoType, propHashtable, tOthers);
			} else if (tCode.equals("StringNo")) {
				tMaxNoElement = new StringNo(cNoType, propHashtable, tOthers);
			} else if (tCode.equals("ComNo")) {
				tMaxNoElement = new ComNo(cNoType, propHashtable, tOthers);
			} else if (tCode.equals("YearNo")) {
				tMaxNoElement = new YearNo(cNoType, propHashtable, tOthers);
			}

			tTempNo = tMaxNoElement.CreateMaxNo();
			tFinalNo = tFinalNo + tTempNo;

		}
		return tFinalNo;
	}

	private CreateMaxNoImp mCreateMaxNoImp;

	public String CreateMaxNo() {
		String tTempNo = "";
		Hashtable props = this.getProps();
		String cNoType = this.getType();
		String cNoLimit = this.getLimit();
		// 如果不配置号段约束,默认为SN
		if (cNoLimit == null || cNoLimit.equals("")) {
			cNoLimit = "SN";
		}
		String tSeqFlag = props.get("SeqFlag") == null ? "0" : (String) props
				.get("SeqFlag");// 0.不使用,1,使用
		String tCover = props.get("Cover") == null ? "0" : (String) props
				.get("Cover");// 不足位补充内容
		String tLength = props.get("Length") == null ? "1" : (String) props
				.get("Length");// 长度,默认为1

		int tMaxNo = 1;
		if (tSeqFlag.equals("0")) {
			// ldmaxno 表
			tMaxNo = getNoByLDMaxNo(cNoType, cNoLimit);
		} else if (tSeqFlag.equals("1")) {
			//
			tMaxNo = getNoBySequence(cNoType, cNoLimit);
		} else if (tSeqFlag.equals("2")) {
			//
			tMaxNo = getNoByFunction(cNoType, cNoLimit);
		}

		if (tMaxNo == 0) {
			return null;
		}

		String tStr = String.valueOf(tMaxNo);
		if (!tCover.equals("-1")) {
			tTempNo = PubFun.LCh(tStr, tCover, Integer.parseInt(tLength));
		} else {
			tTempNo = tStr;
		}

		return tTempNo;
	}

	// 使用function生成
	private int getNoByFunction(String cNoType, String cNoLimit) {
		int tMaxNo;
		String tStr = "";
		// 使用序列
		try {

			Connection conn = DBConnPool.getConnection();

			if (conn == null) {
				logger.debug("CreateMaxNo : fail to get db connection");
				return 0;
			}
			try {
				conn.setAutoCommit(false);
				String tSQL = " select createmaxno(?,?) from dual ";
				ExeSQL tExeSQL = new ExeSQL();
				VData tParamVData = new VData();
				TransferData params = new TransferData();
				params.setNameAndValue("0", "string:"+cNoType);
				params.setNameAndValue("1", "string:"+cNoLimit);
				tParamVData.add(tSQL);
				tParamVData.add(params);
				tStr = tExeSQL.getOneValue(tParamVData);
				conn.commit();
				conn.close();
			} catch (Exception Ex) {
				try {
					conn.rollback();
					conn.close();

					return 0;
				} catch (Exception e1) {
					e1.printStackTrace();
					return 0;
				}
			}
		} catch (Exception ex) {
			// 连接异常，则返回null，表示生成最大号失败
			ex.printStackTrace();
			return 0;
		}
		tMaxNo = Integer.parseInt(tStr);
		return tMaxNo;
	}

	// 使用序列生成
	private int getNoBySequence(String cNoType, String cNoLimit) {
		//JiangJian 2016-04-11 仅ORACLE数据库使用序列方式生成
		if(!"ORACLE".equalsIgnoreCase(SysConst.DBTYPE)){
			return getNoByLDMaxNo(cNoType, cNoLimit);
		}
		
		int tMaxNo;
		String tStr = "";
		// 使用序列
		try {

			StringBuffer tSBSql = new StringBuffer(128);
			tSBSql.append("select SEQ_");
			tSBSql.append(cNoType.toUpperCase());
			tSBSql.append("_");
			tSBSql.append(cNoLimit.toUpperCase());
			tSBSql.append(".nextval from dual");

			ExeSQL tExeSQL = new ExeSQL();
			tStr = tExeSQL.getOneValue(tSBSql.toString());
			// 如果返回信息为空，表示序列不存在
			if (tStr.equals("")) {
				StringBuffer tSBDDLSql = new StringBuffer(128);
				tSBDDLSql.append("create sequence SEQ_");
				tSBDDLSql.append(cNoType);
				tSBDDLSql.append("_");
				tSBDDLSql.append(cNoLimit);
				tSBDDLSql
						.append(" minvalue 1 maxvalue 999999999999 start with 1 increment by 1 nocache");

				boolean tFlag = tExeSQL.execUpdateSQL(tSBDDLSql.toString());
				// 判断序列创建是否成功
				if (tFlag) {
					tStr = tExeSQL.getOneValue(tSBSql.toString());
				} else {
					// 如果创建sequence失败，则返回null，表示生成最大号失败
					logger.debug(tExeSQL.mErrors.getFirstError());
					return 0;
				}
			}
		} catch (Exception ex) {
			// 连接异常，则返回null，表示生成最大号失败
			ex.printStackTrace();
			return 0;
		}
		tMaxNo = Integer.parseInt(tStr);
		return tMaxNo;
	}

	private int getNoByLDMaxNo(String cNoType, String cNoLimit) {
		int tMaxNo;
		// 使用最大号表
		// 其他
		Connection conn = DBConnPool.getConnection();

		if (conn == null) {
			logger.debug("CreateMaxNo : fail to get db connection");

			return 0;
		}
		try {
			conn.setAutoCommit(false);

			StringBuffer tSBql = new StringBuffer(256);
			tSBql.append("select MaxNo from LDMaxNo where notype='");
			tSBql.append("?cNoType?");
			tSBql.append("' and nolimit='");
			tSBql.append("?cNoLimit?");
			tSBql.append("' for update");
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSBql.toString());
			sqlbv.put("cNoType", cNoType);
			sqlbv.put("cNoLimit", cNoLimit);

			ExeSQL exeSQL = new ExeSQL(conn);
			String result = null;
			result = exeSQL.getOneValue(sqlbv);

			if (exeSQL.mErrors.needDealError()) {
				logger.debug("查询LDMaxNo出错，请稍后!");
				conn.rollback();
				conn.close();
				return 0;
			}

			if ((result == null) || result.equals("")) {
				tSBql = new StringBuffer(256);
				tSBql
						.append("insert into ldmaxno(notype, nolimit, maxno) values('");
				tSBql.append("?cNoType?");
				tSBql.append("', '");
				tSBql.append("?cNoLimit?");
				tSBql.append("', 1)");
				SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
				sqlbv1.sql(tSBql.toString());
				sqlbv1.put("cNoType", cNoType);
				sqlbv1.put("cNoLimit", cNoLimit);


				exeSQL = new ExeSQL(conn);
				if (!exeSQL.execUpdateSQL(sqlbv1)) {
					logger.debug("CreateMaxNo 插入失败，请重试!");
					conn.rollback();
					conn.close();
					return 0;
				} else {
					tMaxNo = 1;
				}
			} else {
				tSBql = new StringBuffer(256);
				tSBql
						.append("update ldmaxno set maxno = maxno + 1 where notype = '");
				tSBql.append("?cNoType?");
				tSBql.append("' and nolimit = '");
				tSBql.append("?cNoLimit?");
				tSBql.append("'");
				SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				sqlbv2.sql(tSBql.toString());
				sqlbv2.put("cNoType", cNoType);
				sqlbv2.put("cNoLimit", cNoLimit);
				exeSQL = new ExeSQL(conn);
				if (!exeSQL.execUpdateSQL(sqlbv2)) {
					logger.debug("CreateMaxNo 更新失败，请重试!");
					conn.rollback();
					conn.close();
					return 0;
				} else {
					tMaxNo = Integer.parseInt(result) + 1;
				}
			}
			conn.commit();
			conn.close();
		} catch (Exception Ex) {
			try {
				conn.rollback();
				conn.close();

				return 0;
			} catch (Exception e1) {
				e1.printStackTrace();

				return 0;
			}
		}

		return tMaxNo;
	}

	@Override
	public String CreatePrviewMaxNo() {
		String tTempNo = "";
		Hashtable props = this.getProps();
		String cNoType = this.getType();
		// String cNoLimit = this.getLimit();
		String tSeqFlag = props.get("SeqFlag") == null ? "0" : (String) props
				.get("SeqFlag");// 0.不使用,1,使用
		String tCover = props.get("Cover") == null ? "0" : (String) props
				.get("Cover");// 不足位补充内容
		String tLength = props.get("Length") == null ? "1" : (String) props
				.get("Length");// 长度,默认为1

		int tMaxNo = 1;
		// 预览,直接默认返回值为1
		String tStr = String.valueOf(tMaxNo);
		if (!tCover.equals("-1")) {
			tTempNo = PubFun.LCh(tStr, tCover, Integer.parseInt(tLength));
		} else {
			tTempNo = tStr;
		}

		return tTempNo;
	}
}
