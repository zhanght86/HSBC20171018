package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LZCardAppDB;
import com.sinosoft.lis.db.LZCardBDB;
import com.sinosoft.lis.db.LZCardTrackBDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LZCardAppSchema;
import com.sinosoft.lis.schema.LZCardBSchema;
import com.sinosoft.lis.schema.LZCardSchema;
import com.sinosoft.lis.vschema.LZCardAppSet;
import com.sinosoft.lis.vschema.LZCardBSet;
import com.sinosoft.lis.vschema.LZCardSet;
import com.sinosoft.lis.vschema.LZCardTrackBSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:单证管理单证发放操作
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author mw
 * @version 1.0
 */

public class CertDestroyApplyBL extends CertifyBO {
private static Logger logger = Logger.getLogger(CertDestroyApplyBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/** 私有成员 */
	private String mszOperate = "";

	private String mApplyNo = "";

	/** 业务相关的数据 */
	private GlobalInput globalInput = new GlobalInput();

	private String curDate = PubFun.getCurrentDate();

	private String curTime = PubFun.getCurrentTime();

	private LZCardAppSet mLZCardAppSet = new LZCardAppSet();

	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();

	private VData mResult = new VData();

	public CertDestroyApplyBL() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		mszOperate = cOperate;

		if (!getInputData(cInputData))
			return false;

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		// 数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mResult, "")) {
			buildError("submitData", "数据提交失败!");
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			return false;
		}

		return true;
	}

	// 从输入数据中得到所有对象
	private boolean getInputData(VData vData) {
		if (mszOperate.equals("APPLY")) {
			globalInput.setSchema((GlobalInput) vData.getObjectByObjectName("GlobalInput", 0));
			mLZCardAppSet.set((LZCardAppSet) vData.getObjectByObjectName("LZCardAppSet", 0));
		} else {
			buildError("getInputData", "不支持的操作字符串");
			return false;
		}
		
		long cardnum=0;
		for (int nIndex = 1; nIndex <= mLZCardAppSet.size(); nIndex++)
		{
			LZCardAppSchema tLZCardAppSchema =mLZCardAppSet.get(nIndex);
			//20101-02-01增加单证数量的控制
			cardnum=cardnum+ CertifyFunc.bigIntegerDiff(tLZCardAppSchema.getEndNo(), tLZCardAppSchema
						.getStartNo()) + 1;		
		}
		if(cardnum>20000)
		{
			CError.buildErr(this, "一次单证操作的单证数量不能超过20000张，请减少单证数量");
			return false;
		}
			

		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	private boolean dealData() {
		
		ExeSQL tExeSQL =null;
		
		for (int nIndex = 0; nIndex < mLZCardAppSet.size(); nIndex++) {
			
			LZCardAppSchema tLZCardAppSchema = mLZCardAppSet.get(nIndex + 1);
			
			/**
			 * 2009-06-05 防止查询记录过多,增加对查询记录数量的限制
			 * zhangzheng
			 */
			String checkSql="	select count(1) from lzcardapp a where a.operateflag = '4' and a.certifycode = '"
				+ "?certifycode?" + "' and a.startno <= '"
				+ "?startno?" + "' and a.endno >= '" + "?endno?"
				+ "'";
			SQLwithBindVariables sqlbv =new SQLwithBindVariables();
			sqlbv.sql(checkSql);
			sqlbv.put("certifycode", tLZCardAppSchema.getCertifyCode());
			sqlbv.put("startno", tLZCardAppSchema.getEndNo());
			sqlbv.put("endno", tLZCardAppSchema.getStartNo());
			tExeSQL = new ExeSQL();
			if(Integer.parseInt(tExeSQL.getOneValue(sqlbv))>10000)
			{
				buildError("dealData", "根据录入的起始号和终止号查询的记录数超过了10000条,请详细查询后录入准确的需要操作的单证号码！");
				return false;
			}
			
			tExeSQL=null;
			checkSql=null;
			
			// 校验录入单证是否已经做过销毁提交
			String sql = "	select * from lzcardapp a where a.operateflag = '4' and a.certifycode = '"
					+ "?certifycode?" + "' and a.startno <= '"
					+ "?startno?" + "' and a.endno >= '" + "?endno?"
					+ "'";
			SQLwithBindVariables sqlbv1 =new SQLwithBindVariables();
			sqlbv1.sql(sql);
			sqlbv1.put("certifycode", tLZCardAppSchema.getCertifyCode());
			sqlbv1.put("startno", tLZCardAppSchema.getEndNo());
			sqlbv1.put("endno", tLZCardAppSchema.getStartNo());
			LZCardAppDB tLZCardAppDB = new LZCardAppDB();
			LZCardAppSet tLZCardAppSet = tLZCardAppDB.executeQuery(sqlbv1);
			if (tLZCardAppSet != null && tLZCardAppSet.size() >= 1) {
				buildError("dealData", "单证已做过销毁操作，请检查录入信息！");
				return false;
			}

			//增加与销毁确认一致的单证校验规则（排除批量销毁提交功能的申请校验）
			if(!"ImporAPPLY".equals(mszOperate) )
			{
				// 单证状态校验
				String szSql = "SELECT * FROM LZCardB WHERE CertifyCode = '" + "?certifycode?"
							+ "' AND StateFlag in ('6','7') AND StartNo <= '" + "?startno?" + "' AND EndNo >= '"
							+ "?endno?" + "' ";
				logger.debug("splitCertifyBlankOut: 单证状态校验的SQL:" + szSql);
				SQLwithBindVariables sqlbv2 =new SQLwithBindVariables();
				sqlbv2.sql(szSql);
				sqlbv2.put("certifycode", tLZCardAppSchema.getCertifyCode());
				sqlbv2.put("endno", tLZCardAppSchema.getEndNo());
				sqlbv2.put("startno", tLZCardAppSchema.getStartNo());
				LZCardBDB dbLZCardB = new LZCardBDB();
				LZCardBSet set = dbLZCardB.executeQuery(sqlbv2);
				if (set.size() > 1) { // 输入的单证号在可用的单证号内
					buildError("splitCertifyDestroyApply", "输入的单证信息为历史错误单证，请进入批量销毁导入功能进行销毁操作!");
					return false;
				}
			}
			// 产生申请号码,记录申请轨迹LZCardApp
			mApplyNo = PubFun1.CreateMaxNo("ApplyNo", PubFun.getNoLimit(globalInput.ComCode));
			tLZCardAppSchema.setApplyNo(mApplyNo);
			tLZCardAppSchema.setSubCode("0");
			tLZCardAppSchema.setRiskCode("0");
			tLZCardAppSchema.setRiskVersion("0");
			tLZCardAppSchema.setOperateFlag("4");// 4、销毁申请
			tLZCardAppSchema.setApplyCom(globalInput.ComCode);
			tLZCardAppSchema.setOperator(globalInput.Operator);
			tLZCardAppSchema.setMakeDate(curDate);
			tLZCardAppSchema.setMakeTime(curTime);
			tLZCardAppSchema.setModifyDate(curDate);
			tLZCardAppSchema.setModifyTime(curTime);
			tLZCardAppSchema.setStateFlag("1");// 1、申请待批复
			tLZCardAppSchema.setSumCount((int) CertifyFunc.bigIntegerDiff(tLZCardAppSchema.getEndNo(),
					tLZCardAppSchema.getStartNo()) + 1);

			LZCardSchema tLZCardSchema = new LZCardSchema();
			tLZCardSchema.setCertifyCode(tLZCardAppSchema.getCertifyCode());
			tLZCardSchema.setStartNo(tLZCardAppSchema.getStartNo());
			tLZCardSchema.setEndNo(tLZCardAppSchema.getEndNo());
			tLZCardSchema.setHandler(tLZCardAppSchema.getHandler());
			tLZCardSchema.setHandleDate(tLZCardAppSchema.getHandleDate());

			// 校验销毁的单证是否都处于作废状态
			LZCardBSet tLZCardBSet = CertifyFunc.formatCardListDestroy(tLZCardSchema);
			if (tLZCardBSet == null || tLZCardBSet.size() == 0) {
				buildError("dealData", "只有作废的单证可以销毁，单证状态不合法，不能销毁");
				return false;
			}
		}
		map.put(mLZCardAppSet, "INSERT");

		return true;
	}

	public VData getResult() {
		mResult.clear();
		return mResult;
	}

	// 准备往后层输出所需要的数据
	// 输出：如果准备数据时发生错误则返回false,否则返回true
	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(map);
		} catch (Exception ex) {
			buildError("prepareData", "在准备往后层处理所需要的数据时出错。");
			return false;
		}
		return true;
	}

	/*
	 * add by kevin, 2002-09-23
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "CertLossRegisterBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
}
