package com.sinosoft.lis.easyscan;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: 单证业务关联处理统一调用接口类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author tuqiang
 * @version 1.0
 */

import com.sinosoft.lis.db.ES_DOC_RELATIONDB;
import com.sinosoft.lis.db.ES_PROCESS_DEFDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.ES_DOC_RELATIONSet;
import com.sinosoft.lis.vschema.ES_PROCESS_DEFSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class CallService {
private static Logger logger = Logger.getLogger(CallService.class);

	public CallService() {
	}

	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 接收数据的容器 */
	private VData mResult = new VData();

	/** 传出数据的容器 */
	private VData tResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;
	
	/** whether use 0 to valid*/
	private String mFlag;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public boolean submitData(VData cInputData, String cOperate,
			String strServiceType) {
		ES_PROCESS_DEFDB queryES_PROCESS_DEF = new ES_PROCESS_DEFDB();

		/** 对于单证信息的数据* */
		logger.debug("callservice------begin");
		mInputData = (VData) cInputData.clone();
		ES_DOC_MAINSet tES_DOC_MAINSet = (ES_DOC_MAINSet) mInputData.get(0);
		ES_DOC_MAINSchema nES_DOC_MAINSchema = tES_DOC_MAINSet.get(1);
		/*
		 * by wellhi 2005.07.21 如果DocFlag为0,则为新上载的单证,调用的服务类型(Servicetype)为1
		 * (事前扫描)或者 2(事后扫描); 事前扫描要触发工作流,事后扫描不触发工作流
		 * 如果DocFlag为1,则为单证修改,调用的服务类型(Servicetype)为3 (单证修改);
		 */
		if (tES_DOC_MAINSet.get(1).getDocFlag().toString() == "0") {
			// added by wellhi 2005.05.30
			// 如果是修改单证信息,则不用调用服务
			ES_DOC_RELATIONDB tES_DOC_RELATIONDB = new ES_DOC_RELATIONDB();
			tES_DOC_RELATIONDB.setDocID(nES_DOC_MAINSchema.getDocID());
			ES_DOC_RELATIONSet tES_DOC_RELATIONSet = tES_DOC_RELATIONDB.query();
			if (tES_DOC_RELATIONSet.size() > 0) {
				logger.debug("Relation exist!");
				return true;
			}
		}
		//
		// if (tES_DOC_MAINSet.get(1).getDocFlag().equals("1")) {
		// logger.debug("@@@@@@@@@@@@@DocFlag=" +
		// tES_DOC_MAINSet.get(1).getDocFlag());
		// return true;
		// }
		/*
		 * String strServiceType = ""; if
		 * (nES_DOC_MAINSchema.getDocFlag().equals("0")) { strServiceType = "1"; }
		 * //预留给事后扫描 // else if(nES_DOC_MAINSchema.getDocFlag()==0){ //
		 * strServiceType="2"; else if
		 * (nES_DOC_MAINSchema.getDocFlag().equals("1")) { strServiceType = "3"; }
		 */
		// queryES_PROCESS_DEF.setBussType(nES_DOC_MAINSchema.getBussType());
		// queryES_PROCESS_DEF.setSubType(nES_DOC_MAINSchema.getSubType());
		// queryES_PROCESS_DEF.setServiceType(strServiceType);
		// 单证处理定义表查询
		// 单证处理定义表查询
		String tStr = "select * from ES_PROCESS_DEF where BussType = '"
				+ "?BussType?"+"'" + " and SubType = '"
				+"?SubType?"+ "'"
				+ " and ServiceType = '" + "?strServiceType?"
				+ "' and validflag='0' order by ProcessCode";
		// logger.debug(tStr);
		
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tStr);
		sqlbv1.put("BussType",nES_DOC_MAINSchema.getBussType());
		sqlbv1.put("SubType",nES_DOC_MAINSchema.getSubType());
		sqlbv1.put("strServiceType",strServiceType);
		ES_PROCESS_DEFSet tES_PROCESS_DEFSet = queryES_PROCESS_DEF
				.executeQuery(sqlbv1);

		// ES_PROCESS_DEFSet tES_PROCESS_DEFSet = queryES_PROCESS_DEF.query();
		if (tES_PROCESS_DEFSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "CallService";
			tError.functionName = "save";
			tError.errorNo = "-1";
			tError.errorMessage = "单证处理定义表查询出错";
			logger.debug("callservice单证处理定义表查询出错");
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			// 返回需要数据库提交的数据
			for (int i = 0; i < tES_PROCESS_DEFSet.size(); i++) {
				Class tClass = Class.forName(tES_PROCESS_DEFSet.get(i + 1)
						.getProcessService());
				logger.debug(1 + tES_PROCESS_DEFSet.get(i + 1)
						.getProcessService());
				EasyScanService tservice = (EasyScanService) tClass
						.newInstance();
				if (!tservice.submitData(mInputData, cOperate)) {
					CError tError = new CError();
					tError.moduleName = "CallService";
					tError.functionName = "save";
					tError.errorNo = "-1";
					tError.errorMessage = "service处理出错";
					this.mErrors.clearErrors();
					this.mErrors.copyAllErrors(tservice.getErrors());
					return false;
				}
				mResult.add(tservice.getResult());
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "CallService";
			tError.functionName = "save";
			tError.errorNo = "-1";
			tError.errorMessage = "服务初始化出错";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 准备提交的数据
		if (!prepareOutData()) {
			CError tError = new CError();
			tError.moduleName = "prepareOutData";
			tError.functionName = "CallService";
			tError.errorNo = "-1";
			tError.errorMessage = "准备输出数据出错";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public boolean prepareOutData() {
		MMap tmap = new MMap();
		for (int i = 0; i < mResult.size(); i++) {
			VData tData = new VData();
			logger.debug("Map" + i);
			tData = (VData) mResult.get(i);
			MMap map = (MMap) tData.getObjectByObjectName("MMap", 0);
			tmap.add(map);
		}
		tResult.add(tmap);
		return true;
	}

	public VData getResult() {
		return tResult;
	}
}
