/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.operfee.IndiDueFeeCancelBL;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

/**
 * <p>
 * Title: 保单挂起
 * </p>
 * <p>
 * Description: 报案确认时的保单挂起类
 * </p>
 * Copyright (c) 2005 sinosoft Co. Ltd.
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author: zhangzheng
 * @version 1.0
 */

public class LLLpContHangUpBL {
private static Logger logger = Logger.getLogger(LLLpContHangUpBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;
	private MMap map = new MMap();


	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private LCContHangUpStateSet mLCContHangUpStateSet = new LCContHangUpStateSet();
	private GlobalInput mG = new GlobalInput();

	private String mClmNo = ""; // 赔案号
	private String tGrpContno;

	private TransferData mTransferData = new TransferData();

	public LLLpContHangUpBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------LLLpContHangUpBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		if (!getInputData())// 得到外部传入的数据,将数据备份到本类中
		{
			return false;
		}
		logger.debug("----------after getInputData----------");
		if (!checkInputData()) // 检查数据合法性
		{
			return false;
		}
		logger.debug("----------after checkInputData----------");
		if (!dealData())// 进行业务处理
		{
			return false;
		}
		logger.debug("----------after dealData----------");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------after prepareOutputData----------");

		mInputData = null;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("--start getInputData()");

		// 获取页面报案信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);// 按类取值

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mClmNo = (String) mTransferData.getValueByName("RgtNo"); // 赔案号
		//增加团险理赔团单号的获取
		tGrpContno = (String) mTransferData.getValueByName("GrpContNo"); 

		if (mClmNo == null)// @@错误处理
		{
			CError.buildErr(this, "传入的赔案号为空!");
			return false;
		}

		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		logger.debug("----------begin checkInputData----------");
		try {
			// 检测数据
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "在校验输入的数据时出错!");
			return false;
		}
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("------start dealData-----");


		String strSQL = "";
		LCContSet tLCContSet = new LCContSet();

		// 查询出险人集合
//		strSQL = "select * from llsubreport where SubRptNo = '" + mClmNo + "'";
//		LLSubReportDB tLLSubReportDB = new LLSubReportDB();
//		LLSubReportSet tSet = tLLSubReportDB.executeQuery(strSQL);
//		if (tSet == null || tSet.size() <= 0) {
//			// @@错误处理
//			CError.buildErr(this, "查询出险人信息失败!");
//			return false;
//		}

		// 判断是否死亡类案件
		strSQL = "";
		strSQL = "select * from LLReportReason b where"
				+ " substr(b.ReasonCode,2,2) = '02' " + " and b.RpNo = '"
				+ mClmNo + "'";
		LLReportReasonDB tLLReportReasonDB = new LLReportReasonDB();
		LLReportReasonSet tSet = new LLReportReasonSet();
		tSet = tLLReportReasonDB.executeQuery(strSQL);
		String tFlag = String.valueOf(tSet.size());
//		ExeSQL tExeSQL = new ExeSQL();
//		String tFlag = tExeSQL.getOneValue(strSQL);
//		if (tFlag == "" || tFlag == null) {
//			// @@错误处理
//			CError.buildErr(this, "查询理赔类型信息失败!");
//			return false;
//		}

		// ===by zl====为防止联生险按出险人查询时重复提交挂起导致插入失败====2006-3-22 15:12====beg
		// 组成被保人集合
		String tNo = "";
		for (int i = 1; i <= tSet.size(); i++) {
			tNo = tNo + "'" + tSet.get(1).getCustomerNo() + "'";
			if (i < tSet.size()) {
				tNo = tNo + ",";
			}
		}

		// 提取需要挂起合同集合
		if (tFlag.equals("0")) {
			
			// 非死亡类案件查找所有作为被保人及与赔案相关的保单
//			strSQL = "select * from LcCont where contno in "
//					+ "(select distinct contno from lcinsured where "
//					+ " insuredno in (" + tNo + ")" + " union "
//					+ " select distinct contno from llclaimpolicy where "
//					+ " clmno = '" + mClmNo + "')";
//			LCContSet tCSet = new LCContSet();
//			LCContDB tCDB = new LCContDB();
//			tCSet = tCDB.executeQuery(strSQL);
//			tLCContSet.add(tCSet);
			//zy 如果是康福的团险理赔则进行保单挂起
			//判断保单是否被保全挂起
			ContHangUpBL tContHangUpBL = new ContHangUpBL(mG);
			if (!tContHangUpBL.checkGrpHangUpState(tGrpContno, "2")) // 2-保全
			{
				mErrors.copyAllErrors(tContHangUpBL.mErrors);
				 CError.buildErr(this, "合同"+tGrpContno+"正处于保全挂起状态，不允许做报案操作!");
				return false;
			} 
			else
			{
				String kSql = "select distinct riskcode from lcgrppol where grpcontno='"+tGrpContno+"' and riskcode='211901' and grpname like 'MSRS保险股份有限公司%' ";
				ExeSQL tExeSQL = new ExeSQL();
				SSRS kSSRS = new SSRS();
				kSSRS = tExeSQL.execSQL(kSql);
				if(kSSRS.MaxRow>0)
				{
					if("211901".equals(kSSRS.GetText(1, 1)))
					{
						// 提交给挂起处理类											
					// 挂起时传入的挂起表记录只需将要挂起的字段置为1即可，不需要挂起的字段可以不用置值
					LCGrpContHangUpStateSchema tLCGrpContHangUpStateSchema = new LCGrpContHangUpStateSchema();
					tLCGrpContHangUpStateSchema.setGrpContNo(tGrpContno);
					tLCGrpContHangUpStateSchema.setHangUpType("4"); // 1-新契约 2-保全 3-续期
																	// 4-理赔 5-渠道
					tLCGrpContHangUpStateSchema.setHangUpNo(mClmNo); // 理赔立案号
//					tLCGrpContHangUpStateSchema.setPosFlag("1"); // 保全
					 tLCGrpContHangUpStateSchema.setClaimFlag("1"); //理赔
					// tLCGrpContHangUpStateSchema.setRNFlag("1"); //续期
					map = tContHangUpBL.hangUpGrpCont(tLCGrpContHangUpStateSchema);
					if (map == null) {
						mErrors.copyAllErrors(tContHangUpBL.mErrors);
						return false;
				}	
			   }
			}
		  }
		
			
		} else {
			
			
			/*
			 * 身故类型的保单首先删除理赔人员通过保单挂起功能手工挂起的保单，防止在自动挂起时被校验卡住(自动挂起时会挂起出险人作为投,被人的所有保单)
			 */
			
		    if(!eliminateHungUpCont())
		    {
				return false;
		    }
		    
		    
			// 死亡类案件,查找所有作为投保和被保人的合同
			strSQL = "(select * from LcCont where AppntNo in (" + tNo + "))"
					+ " union" + "(select * from LcCont where contno in "
					+ " (select distinct contno from lcinsured where "
					+ " insuredno in (" + tNo + ")))";
			LCContSet tCSet = new LCContSet();
			LCContDB tCDB = new LCContDB();
			tCSet = tCDB.executeQuery(strSQL);
			tLCContSet.add(tCSet);
			
			
			VData CResult = new VData();
			MMap cmap = null;
			
			// 提交给挂起处理类
			if (tLCContSet != null && tLCContSet.size() != 0) {
				for (int j = 1; j <= tLCContSet.size(); j++) {
					LCContHangUpStateSchema tLCContHangUpStateSchema = new LCContHangUpStateSchema(); // 个人保单表
					tLCContHangUpStateSchema.setContNo(tLCContSet.get(j)
							.getContNo()); // 合同号
					tLCContHangUpStateSchema.setInsuredNo("000000"); // 被保险人号码
					tLCContHangUpStateSchema.setPolNo("000000"); // 保单险种号
					tLCContHangUpStateSchema.setPosFlag("1"); // 保全挂起状态
					tLCContHangUpStateSchema.setRNFlag("1"); // 续期挂起状态
					mLCContHangUpStateSet.add(tLCContHangUpStateSchema);
					
					
					//撤销续期催收
					VData ppVData= new VData();
					ppVData.add(tLCContSet.get(j));
					ppVData.add(mG);	
					IndiDueFeeCancelBL IndiDueFeeCancelBL = new IndiDueFeeCancelBL();
					if(!IndiDueFeeCancelBL.submitData(ppVData, "LiPeiDelete"))
					{
						mErrors.copyAllErrors(IndiDueFeeCancelBL.mErrors);
						CError.buildErr(this, "续期催收撤销失败！");
						//return false;
					}
					else
					{
						CResult = IndiDueFeeCancelBL.getResult();
						cmap = new MMap();
						
						if(CResult.size()>0)
						{
							cmap =(MMap)CResult.get(0);
							mResult.add(cmap);
							
							cmap=null;
						}
					}
					
					ppVData=null;
				}

				// 保单挂起处理
				if (!dealHangUp()) {
					// @@错误处理
					CError.buildErr(this, "保单挂起处理失败!");
					return false;
				}
				

			}
		}
		// ===by zl====为防止联生险按出险人查询时重复提交挂起导致插入失败====2006-3-22 15:12====beg

		

		return true;
	}
	
	
	/**
	 * 身故类型的保单首先删除理赔人员通过保单挂起功能手工挂起的保单，防止在自动挂起时被校验卡住(自动挂起时会挂起出险人作为投,被人的所有保单)
	 */
	private boolean eliminateHungUpCont() {
		
		logger.debug("----------begin eliminateHungUpCont----------");
		
		MMap tMap=null;
		String sql="";
		VData ttInputData=null;
		PubSubmit tPubSubmit=null;
		
		try {
			sql="delete from lcconthangupstate where hangupno='"+mClmNo+"'";
			tMap = new MMap();
			tMap.put(sql, "DELETE");
			ttInputData=new VData();
			ttInputData.add(tMap);

			tPubSubmit= new PubSubmit();
			if (!tPubSubmit.submitData(ttInputData, "")) {
				// @@错误处理
				CError.buildErr(this, "删除手工挂起保单失败!");
				return false;
			}

			// 检测数据
		} 
		catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "删除手工挂起保单失败!");
			return false;
		}
		finally{
			//释放强引用
			tMap=null;
			sql=null;
			ttInputData=null;
			tPubSubmit=null;
		}
		
		logger.debug("----------end eliminateHungUpCont----------");
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealHangUp() {
		// 调用公用挂起方法
		LCContHangUpStateSet tLCContHangUpStateSaveSet = new LCContHangUpStateSet(); // 保存

		for (int i = 1; i <= mLCContHangUpStateSet.size(); i++) {
			String tContNo = mLCContHangUpStateSet.get(i).getContNo();

			// 判断该合同是否被挂起
			LCContHangUpBL tLCContHangUpBL = new LCContHangUpBL(mG, mClmNo,
					"4", tContNo);
			if (!tLCContHangUpBL.queryHungUpForContNo()) {
				mErrors.copyAllErrors(tLCContHangUpBL.mErrors);
				return false;
			}

			String tPosFlag = mLCContHangUpStateSet.get(i).getPosFlag();
			String tRNFlag = mLCContHangUpStateSet.get(i).getRNFlag();
			tLCContHangUpBL.setPosFlag(tPosFlag); // 1挂起,0正常
			tLCContHangUpBL.setRnFlag(tRNFlag);
			tLCContHangUpBL.setClmFlag("1");//2008-11-22 zhangzheng 挂起时也必须同时挂起理赔,即不能同时对同一个合同进行交叉理赔
			LCContHangUpStateSchema tLCContHangUpStateSchema = tLCContHangUpBL
					.getHungUp();

			tLCContHangUpStateSaveSet.add(tLCContHangUpStateSchema);

		}

		map.put(tLCContHangUpStateSaveSet, "DELETE&INSERT");

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLLpContHangUpBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private void jbInit() throws Exception {
		//
	}
}
