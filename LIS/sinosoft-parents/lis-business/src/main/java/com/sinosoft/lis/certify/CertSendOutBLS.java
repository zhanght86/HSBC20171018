package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LZCardAppDB;
import com.sinosoft.lis.db.LZCardDB;
import com.sinosoft.lis.db.LZCardPrintDB;
import com.sinosoft.lis.db.LZCardTrackDB;
import com.sinosoft.lis.schema.LZCardAppSchema;
import com.sinosoft.lis.schema.LZCardPrintSchema;
import com.sinosoft.lis.schema.LZCardSchema;
import com.sinosoft.lis.schema.LZCardTrackSchema;
import com.sinosoft.lis.vdb.LZCardDBSet;
import com.sinosoft.lis.vschema.LZCardSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:单证管理普通单证发放操作
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author 周平
 * @version 1.0
 */

public class CertSendOutBLS {
private static Logger logger = Logger.getLogger(CertSendOutBLS.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	public CertSendOutBLS() {
	}

	public static void main(String[] args) {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		boolean bReturn = true;

		if (cOperate.equals("INSERT")) {
			Connection conn = DBConnPool.getConnection();
			if (conn == null) {
				buildError("saveData", "连接数据库失败");
				return false;
			}

			try {
				// 开始事务
				conn.setAutoCommit(false);

				for (int nIndex = 0; nIndex < cInputData.size(); nIndex++) {
					if (!saveData((VData) cInputData.get(nIndex), conn)) {
						bReturn = false;
						break;
					}
				}

				if (bReturn) {
					conn.commit();
				} else {
					conn.rollback();
				}

				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				try {
					if (conn != null) {
						conn.rollback();
						conn.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				bReturn = false;
			}
		} else {
			buildError("submitData", "不支持的操作字符串");
			bReturn = false;
		}

		if (!bReturn) {
			if (CertifyFunc.mErrors.needDealError()) {
				mErrors.copyAllErrors(CertifyFunc.mErrors);
			} else {
				buildError("submitData", "发生错误，但是CertifyFunc没有提供详细信息");
			}
		}

		return bReturn;
	}

	/**
	 * Kevin, 2003-03-24 保存数据。在传入的VData中:
	 * 第一到第四个元素都是LZCardSchema，第一个元素是要删除的数据，其它的数据是要插入的数据。
	 * 第五个元素是要插入到LZCardTrack表中的数据。 第六个元素是要更新LZCardPrint表的数据。
	 * 第七个元素是LZCardSet，发放到部门或代理人拆分为单条记录的单证。
	 * 第八个元素是LZCardAppSchema，更新LZCardApp的增领单证信息。
	 * 
	 * @param vData
	 * @return
	 */
	private boolean saveData(VData vData, Connection conn) {

		LZCardDB tLZCardDB = new LZCardDB(conn);
		LZCardDBSet tLZCardDBSet = new LZCardDBSet(conn);
		LZCardTrackDB tLZCardTrackDB = new LZCardTrackDB(conn);
		LZCardPrintDB tLZCardPrintDB = new LZCardPrintDB(conn);
		LZCardAppDB tLZCardAppDB = new LZCardAppDB(conn);

		LZCardSchema tLZCardSchema = null;
		LZCardSet tLZCardSet = null;
		LZCardTrackSchema tLZCardTrackSchema = null;
		LZCardPrintSchema tLZCardPrintSchema = null;
		LZCardAppSchema tLZCardAppSchema = null;

		try {
			tLZCardSchema = (LZCardSchema) vData.get(0);
			if (tLZCardSchema != null) {
				// 防止一个进程刚执行完对本次单证的操作，本次操作的进程在BL层进行查询的时候并没有发现本次需要发放的单证发生了变化而导致的并发现象
				String sql = "select 1 from lzcard where certifycode='" + "?certifycode?"
						+ "' and startno='" + "?startno?" + "' and endno='"
						+ "?endno?" + "' for update nowait";
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(sql);
				sqlbv.put("certifycode", tLZCardSchema.getCertifyCode());
				sqlbv.put("startno", tLZCardSchema.getStartNo());
				sqlbv.put("endno", tLZCardSchema.getEndNo());
				logger.debug("CertSendOutBLS:校验是否有其他进行对本次发放的单证进行过操作的sql是" + sql);
				ExeSQL tExeSql = new ExeSQL(conn);
				String rs = tExeSql.getOneValue(sqlbv);
				logger.debug("CertSendOutBLS:校验是否有其他进行对本次发放的单证进行过操作的结果是" + rs);
				if (rs.equals("") || rs == null) {
					buildError("saveData", "单证号：" + tLZCardSchema.getCertifyCode() + "起始号为"
							+ tLZCardSchema.getStartNo() + ",终止号为" + tLZCardSchema.getEndNo()
							+ "的记录已经被其他用户操作，本次操作终止!");
					conn.rollback();
					conn.close();
					return false;
				}
				if (tExeSql.mErrors.needDealError()) {
					mErrors.copyAllErrors(tExeSql.mErrors);
					throw new Exception("资源忙，请稍候！");
				}

				// 校验本次删除的单证记录的在lzcard中的唯一性
				if (!CertifyFunc.verifySingleLZCard(tLZCardSchema, conn)) {
					buildError("verifySingleLZCard", "操作失败:可能为并发操作,请重新操作。");
					throw new Exception("操作失败:可能为并发操作,请重新操作。");
				}
			}

			if (tLZCardSchema != null) {
				logger.debug("CertSendOutBLS:本次要删除的单证起始号终止号为" + tLZCardSchema.getStartNo() + " - "
						+ tLZCardSchema.getEndNo());
				tLZCardDB.setSchema(tLZCardSchema);
				if (!tLZCardDB.delete()) {
					mErrors.copyAllErrors(tLZCardDB.mErrors);
					throw new Exception("删除旧的LZCard数据时出错");
				}
			}

			tLZCardSchema = (LZCardSchema) vData.get(1);
			if (tLZCardSchema != null) {
				logger.debug("CertSendOutBLS:插入拆分后的第一部分LZCard的起始号和终止号: " + tLZCardSchema.getStartNo()
						+ " - " + tLZCardSchema.getEndNo());
				tLZCardDB.setSchema(tLZCardSchema);
				if (!tLZCardDB.insert()) {
					mErrors.copyAllErrors(tLZCardDB.mErrors);
					throw new Exception("插入拆分后的第一部分LZCard出错");
				}
			}

			tLZCardSchema = (LZCardSchema) vData.get(2);
			if (tLZCardSchema != null) {
				logger.debug("CertSendOutBLS:插入拆分后的第二部分LZCard的起始号和终止号: " + tLZCardSchema.getStartNo()
						+ " - " + tLZCardSchema.getEndNo());
				tLZCardDB.setSchema(tLZCardSchema);
				if (!tLZCardDB.insert()) {
					mErrors.copyAllErrors(tLZCardDB.mErrors);
					throw new Exception("插入拆分后的第二部分LZCard出错");
				}
			}

			tLZCardSchema = (LZCardSchema) vData.get(3);
			if (tLZCardSchema != null) {
				logger.debug("CertSendOutBLS:插入拆分后的第三部分LZCard的起始号和终止号: " + tLZCardSchema.getStartNo()
						+ " - " + tLZCardSchema.getEndNo());
				tLZCardDB.setSchema(tLZCardSchema);
				if (!tLZCardDB.insert()) {
					mErrors.copyAllErrors(tLZCardDB.mErrors);
					throw new Exception("插入新的LZCard出错");
				}
			}

			tLZCardTrackSchema = (LZCardTrackSchema) vData.get(4);
			if (tLZCardTrackSchema != null) {
				logger.debug("CertSendOutBLS:插入单证轨迹的起始号和终止号:" + tLZCardTrackSchema.getStartNo() + " - "
						+ tLZCardTrackSchema.getEndNo());
				tLZCardTrackDB.setSchema(tLZCardTrackSchema);
				if (!tLZCardTrackDB.insert()) {
					mErrors.copyAllErrors(tLZCardTrackDB.mErrors);
					throw new Exception("插入单证轨迹时出错");
				}
			}

			tLZCardPrintSchema = (LZCardPrintSchema) vData.get(5);
			if (tLZCardPrintSchema != null) {
				logger.debug("CertSendOutBLS:更新单证印刷表印刷号:" + tLZCardPrintSchema.getPrtNo());
				tLZCardPrintDB.setSchema(tLZCardPrintSchema);
				if (!tLZCardPrintDB.update()) {
					mErrors.copyAllErrors(tLZCardPrintDB.mErrors);
					throw new Exception("更新单证印刷表失败");
				}
			}

			tLZCardSet = (LZCardSet) vData.get(6);
			if (tLZCardSet != null) {
				logger.debug("CertSendOutBLS:插入拆分后的单条记录LZCardSet的数量: " + tLZCardSet.size());
				tLZCardDBSet.add(tLZCardSet);
				if (!tLZCardDBSet.insert()) {
					mErrors.copyAllErrors(tLZCardDB.mErrors);
					throw new Exception("插入拆分后的第七部分LZCardSet出错");
				}
			}

			tLZCardAppSchema = (LZCardAppSchema) vData.get(7);
			if (tLZCardAppSchema != null) {
				logger.debug("CertSendOutBLS:更新LZCardApp的增领单证申请号: " + tLZCardAppSchema.getApplyNo());
				tLZCardAppDB.setSchema(tLZCardAppSchema);
				if (!tLZCardAppDB.update()) {
					mErrors.copyAllErrors(tLZCardDB.mErrors);
					throw new Exception("更新LZCardApp出错");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

		return true;
	}

	/*
	 * add by kevin, 2002-09-23
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "CertifySendOutBLS";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;

		this.mErrors.addOneError(cError);
	}
}
