package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import java.util.Date;

import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LDUWGradeDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LMUWDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.finfee.TempFeeWithdrawBL;
import com.sinosoft.lis.pubfun.CalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GetPayType;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.LockTableBL;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LBTempFeeClassSchema;
import com.sinosoft.lis.schema.LBTempFeeSchema;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCCUWSubSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSendTraceSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.schema.LDSysTraceSchema;
import com.sinosoft.lis.schema.LJAGetTempFeeSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.schema.LMUWSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LBTempFeeClassSet;
import com.sinosoft.lis.vschema.LBTempFeeSet;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.vschema.LDSysTraceSet;
import com.sinosoft.lis.vschema.LDUWGradeSet;
import com.sinosoft.lis.vschema.LJAGetTempFeeSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LMUWSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 新契约核保确认
 * </p>
 * <p>
 * Description: 工作流服务类:执行新契约核保确认
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ upd by zhangtao
 * @version 1.0
 * @CreateDate：2005-04-11
 */

public class UWConfirmBL {
private static Logger logger = Logger.getLogger(UWConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mReturnFeeData;

	private VData mInputData;
	/** 数据操作字符串 */
	private String mOperate;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private MMap mSplitMap = new MMap();

	/** 数据操作字符串 */
	private String mManageCom;
	private String mCalCode; // 计算编码

	/** 业务处理相关变量 */
	private LCPolSet mLCPolSet = new LCPolSet();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private String mOldPolNo = "";
	private String mUWFlag = ""; // 核保标志
	private String mUWIdea = ""; // 核保意见
	private String mStopFlag = "";
	private String mUWPopedom = ""; // 操作员核保级别
	private String mAppGrade = ""; // 上报级别
	private String mPolType = ""; // 保单类型
	private String mNewContNo = "";
	private String mUWCancelFlag = ""; // 核保/撤单区分标志
	private boolean mBLUWCancelFlag = false; // 核保/撤单区分标志

	private String mBackUWGrade = "";
	private String mBackAppGrade = "";
	private String mOperator = "";
	private Reflections mReflections = new Reflections();
	private MMap mmap = new MMap();
	private String mPrtNo = "";
	private String mContNo;

	private LJSPaySet outLJSPaySet = new LJSPaySet();
	private LJTempFeeSet outLJTempFeeSet = new LJTempFeeSet();
	private LBTempFeeSet outLBTempFeeSet = new LBTempFeeSet();
	private LJTempFeeClassSet outLJTempFeeClassSet = new LJTempFeeClassSet();
	private LBTempFeeClassSet outLBTempFeeClassSet = new LBTempFeeClassSet();

	/** 核保主表 */
	private LCCUWMasterSet mLCCUWMasterSet = new LCCUWMasterSet();
	private LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();
	private LCPolSet mUpdateLCPolSet = new LCPolSet();
	// tongmeng 2007-12-10 add
	// 增加延期时间
	private String mValiDate = "";
	/** 核保子表 */
	private LCUWSubSet mLCUWSubSet = new LCUWSubSet();
	private LCCUWSubSet mLCCUWSubSet = new LCCUWSubSet();

	/** 打印管理表 */
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();

	private LMUWSet mLMUWSet = new LMUWSet();
	private CalBase mCalBase = new CalBase();
	private GlobalInput mGlobalInput = new GlobalInput();
	private String mGetNoticeNo = "";
	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperater;

	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();
	private String mUWUpReport;
	private String mUWUpPopedom;

	private LCUWSendTraceSchema mLCUWSendTraceSchema = new LCUWSendTraceSchema();
	private MMap mMap;
	private String mSugIndUWFlag;
	private String mSugIndUWIdea;

	public UWConfirmBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("xingxingkaishigetinputdata");
		if (!getInputData()) {
			return false;
		}
		logger.debug("111########################");
		logger.debug("mBLUWCancelFlag==" + mBLUWCancelFlag);
		// 如果不是撤单则查询并校验核保权限
		if (!mBLUWCancelFlag) { // 撤保标志
			// 单独撤单操作不用校验核保权限
			logger.debug("xingxingkaishicheckdata");
			//tongmeng 2009-02-07 add
			//如果不是正常处理不做校验
			if(this.mUWUpReport.equals("0"))
			{
				if (!checkData()) {
					return false;
				}
			}
		}
		logger.debug("########################");
		logger.debug("mUWUpReport:" + mUWUpReport);
		// 如果不是上报则进行正常处理
		if (!mBLUWCancelFlag) {
			// tongmeng 2007-12-21 add
			// 为了核保操作轨迹中可以查询到上报操作,此处先更新下lwmission activityid='0000001110'
			// 借用 missionprop11 记录上报标记
			// 借用 missionprop12 记录当前操作员
			// 借用 missionprop13 记录上报的操作员
			
			//modify by lzf 2013-04-09
//			String tMissionId = (String) this.mTransferData
//					.getValueByName("MissionID");
//			if (tMissionId == null || tMissionId.equals("")) {
//				CError.buildErr(this, "获得工作流编码失败!") ;
//				return false;
//			}
//			String tUpdateSQL = "select * from lwmission where missionid='"
//					+ tMissionId + "' and activityid='0000001110'";
//			LWMissionSet tLWMissionSet = new LWMissionSet();
//			LWMissionDB tLWMissionDB = new LWMissionDB();
//			tLWMissionSet = tLWMissionDB.executeQuery(tUpdateSQL);
//			if (tLWMissionSet.size() < 1) {
//				CError.buildErr(this, "当前不允许下核保结论,请退出界面重新进入!") ;
//				return false;
//			}			
//			
//			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
//			tLWMissionSchema.setSchema(tLWMissionSet.get(1));
//			tLWMissionSchema.setMissionProp11(mUWUpReport);
//			tLWMissionSchema.setDefaultOperator(mOperater);
//			tLWMissionSchema.setMissionProp12(this.mGlobalInput.Operator);
//			tLWMissionSchema.setMissionProp13(mTransferData
//					.getValueByName("UWPopedomCode") == null ? ""
//					: (String) mTransferData.getValueByName("UWPopedomCode"));
//			tLWMissionDB = new LWMissionDB();
//			tLWMissionDB.setSchema(tLWMissionSchema);
//			if (!tLWMissionDB.update()) {
//				CError.buildErr(this, tLWMissionDB.mErrors.getFirstError()) ;
//				return false;
//			}
			mTransferData.removeByName("ReportType");
			mTransferData.setNameAndValue("ReportType", mUWUpReport);
			// //////////////////////////////////////////////
		}
		if (this.mUWUpReport.equals("0")) {

			// if (!checkBackFromBank())
			// {
			// return false;
			// }
			//add by lzf 2013-04-08
			String UWAuthority = (String)this.mTransferData.getValueByName("UWAuthority");
			//2008-12-11 ln add 处理完毕的校验核保级别
//			String tUpdateSQL = "select * from lwmission where missionprop2='"
//				+ mContNo + "' and activityid='0000001110' and "+UWAuthority+">" 
//				+ "(select uwpopedom from lduwuser where usercode='"+ mOperater +"' and uwtype='1') ";
//			LWMissionSet tLWMissionSet1 = new LWMissionSet();
//			LWMissionDB tLWMissionDB = new LWMissionDB();
//			tLWMissionSet1 = tLWMissionDB.executeQuery(tUpdateSQL);
//			if (tLWMissionSet1.size() > 0) {
//				CError.buildErr(this, "当前核保员核保级别不够，不允许下核保结论!") ;
//				return false;
//			}
			String tUpdateSQL = "select * from lccuwmaster where contno='"+ "?contno?" +"' and "+ "?uwpopedom?" +">"
		            + "(select uwpopedom from lduwuser where usercode='"+ "?usercode?" +"' and uwtype='1')";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(tUpdateSQL);
			sqlbv.put("contno", mContNo);
			sqlbv.put("uwpopedom", UWAuthority);
			sqlbv.put("usercode", mOperater);
			LCCUWMasterDB tLCCUWMasterDB1 = new LCCUWMasterDB();
			LCCUWMasterSet tLCCUWMasterSet1 = new LCCUWMasterSet();
			tLCCUWMasterSet1 = tLCCUWMasterDB1.executeQuery(sqlbv);
			if(tLCCUWMasterSet1.size()>0){
				CError.buildErr(this, "当前核保员核保级别不够，不允许下核保结论!") ;
				return false;
			}
			//end by lzf

			// 如果对个险的结论为延期或拒保，那么对总单的结论不能是正常承包或通融承保
			if (!checkPolConfirm()) {
				return false;
			}
			// tongmeng 2007-10-30 modify
			// 增加撤单结论
			if (mUWFlag.equals("1") || mUWFlag.equals("2")
					|| mUWFlag.equals("4") || mUWFlag.equals("9")
					|| mUWFlag.equals("a")) {
				// 次标准体校验核保员级别
				if (!checkStandGrade()) {
					return false;
				}
			}

			// 拒保或延期或撤单要校验核保员级别
			if (mUWFlag.equals("1") || mUWFlag.equals("2")
					|| mUWFlag.equals("a")) {
				if (!checkUserGrade()) {
					return false;
				}
			}

			// 进行业务处理
			if (!dealData()) {
				return false;
			}
			//tongmeng 2009-05-15 modify
			//如果是撤单,需要处理合同下的未撤单的险种的结论
			//tongmeng 2009-05-11 modify
		    //延期,拒保,撤单,合同下的所有险种都撤单
			if(this.mUWFlag.equals("a")||this.mUWFlag.equals("1")||this.mUWFlag.equals("2"))
			{
				/*
				String tSQL = " select count(*) from lcpol where uwflag not in ('1','2','a') and contno='"+mContNo+"'";
				ExeSQL tExeSQL = new ExeSQL();
				String tValue = tExeSQL.getOneValue(tSQL);
				if(tValue!=null&&!tValue.equals("")&&Integer.parseInt(tValue)>0)
				{
					CError.buildErr(this,"合同下有未延期/拒保/撤单的险种,请先对险种下结论!");
					return false;
				}*/
				if(!releaseAllPol())
				{
					return false;
				}
			}
			// 撤单退费处理
			logger.debug("Start Return Tempfee");
			// tongmeng 2007-10-30 modify
			// 撤单、拒保、延期如果有暂收需要做暂收退费
			if (mUWFlag.equals("a") || mUWFlag.equals("1")
					|| mUWFlag.equals("2")) {
				logger.debug("begin 撤单、拒保、延期暂收退费处理");
				if (!returnFee()) {
					return false;
				}
				

				logger.debug("end 撤单、拒保、延期暂收退费处理");
			}
		} else { // 如果是上报则进行上报处理
			UWSendTraceAllBL tUWSendTraceAllBL = new UWSendTraceAllBL();

			boolean tResult = tUWSendTraceAllBL.submitData(mInputData, "");
			if (tResult) {
				mMap = (MMap) tUWSendTraceAllBL.getResult()
						.getObjectByObjectName("MMap", 0);
				mTransferData = (TransferData) tUWSendTraceAllBL.getResult()
						.getObjectByObjectName("TransferData", 0);

			} else {
				this.mErrors.copyAllErrors(tUWSendTraceAllBL.getErrors());
				return false;
			}
		}
		
		//09-12-08  此处增加逻辑 如果是撤单 则需判断该投保人是否有其他保单并处于6状态 如果有
		//将其他6状态的保单转为3状态
		//只有核保流向是处理完毕时才回去更改
		if(mUWFlag.equals("a")&&"0".equals(mUWUpReport)){
			if(!DealOtherCont()){
				CError.buildErr(this,"更改该投保人其他保单状态失败！");
				return false;
			}
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		return true;
	}

	/**
	 * 数据操作撤单业务处理 输出：如果出错，则返回false,否则返回true
	 */
	private boolean checkBackFromBank() {
		// 拒保[1]、延期[2]或撤单[a]时，需要校验银行在途数据
		if (mUWFlag.equals("a") || mUWFlag.equals("1") || mUWFlag.equals("2")) {
			// 查询应收总表数据
			String strSql = "select * from ljspay where trim(otherno) in "
					+ " (select trim(contno) from lccont where prtno='"
					+ "?prtno?" + "' " + " union "
					+ " select trim(proposalcontno) from lccont where prtno='"
					+ "?prtno?" + "' " + " union "
					+ " select trim(prtno) from lccont where prtno='" + "?prtno?"
					+ "' )";
			logger.debug("strSql=" + strSql);
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(strSql);
			sqlbv1.put("prtno", mPrtNo);
			outLJSPaySet = (new LJSPayDB()).executeQuery(sqlbv1);

			for (int i = 1; i <= outLJSPaySet.size(); i++) {
				if (outLJSPaySet.get(i).getBankOnTheWayFlag().equals("1")) {
					// @@错误处理
					CError.buildErr(this, "有银行在途数据，不允许拒保、延期或撤单!") ;
					logger.debug("有银行在途数据，不允许拒保、延期或撤单!");
					return false;
				}
			}

			// 查询暂交费表数据
			strSql = "select * from ljtempfee where EnterAccDate is not null and trim(otherno) in "
					+ " (select trim(contno) from lccont where prtno='"
					+ "?prtno?"
					+ "' "
					+ " union "
					+ " select trim(proposalcontno) from lccont where prtno='"
					+ "?prtno?"
					+ "' "
					+ " union "
					+ " select trim(prtno) from lccont where prtno='"
					+ "?prtno?"
					+ "' )";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(strSql);
			sqlbv2.put("prtno", mPrtNo);
			logger.debug("strSql=" + strSql);
			outLJTempFeeSet = (new LJTempFeeDB()).executeQuery(sqlbv2);

			// 备份
			if (outLJTempFeeSet.size() > 0) {
				for (int i = 1; i <= outLJTempFeeSet.size(); i++) {
					LBTempFeeSchema tLBTempFeeSchema = new LBTempFeeSchema();
					mReflections.transFields(tLBTempFeeSchema, outLJTempFeeSet
							.get(i));
					tLBTempFeeSchema.setBackUpSerialNo(PubFun1.CreateMaxNo(
							"LBTempFee", 20));
					outLBTempFeeSet.add(tLBTempFeeSchema);

					LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
					tLJTempFeeClassDB.setTempFeeNo(outLJTempFeeSet.get(i)
							.getTempFeeNo());
					outLJTempFeeClassSet.add(tLJTempFeeClassDB.query());
				}

				for (int i = 1; i <= outLJTempFeeClassSet.size(); i++) {
					LBTempFeeClassSchema tLBTempFeeClassSchema = new LBTempFeeClassSchema();
					mReflections.transFields(tLBTempFeeClassSchema,
							outLJTempFeeClassSet.get(i));
					tLBTempFeeClassSchema.setBackUpSerialNo(PubFun1
							.CreateMaxNo("LBTFClass", 20));
					outLBTempFeeClassSet.add(tLBTempFeeClassSchema);
				}
			}
		}

		return true;
	}
	
	/**
	 * 合同下的险种的撤单处理
	 * @return
	 */
	private boolean releaseAllPol()
	{
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		String tSQL = " select * from lcpol where uwflag not in ('1','2','a') and contno='"+"?contno?"+"'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSQL);
		sqlbv3.put("contno", mContNo);
		tLCPolSet = tLCPolDB.executeQuery(sqlbv3);
		for(int i=1;i<=tLCPolSet.size();i++)
		{
			LCPolSchema tempLCPolSchema = new LCPolSchema();
			tempLCPolSchema = tLCPolSet.get(i);
			tempLCPolSchema.setUWFlag(this.mUWFlag);
			tempLCPolSchema.setUWCode(this.mOperater);
			this.mUpdateLCPolSet.add(tempLCPolSchema);
			// 准备核保主表信息
			LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
			tLCUWMasterDB.setProposalNo(tempLCPolSchema.getPolNo());
			if (tLCUWMasterDB.getInfo() == false) {
				// @@错误处理
				CError.buildErr(this,"查询核保主表失败");
				return false;
			}
			LCUWMasterSchema tempLCUWMasterSchema = new LCUWMasterSchema();
			tempLCUWMasterSchema.setSchema(tLCUWMasterDB);
			tempLCUWMasterSchema.setPassFlag(this.mUWFlag);
			tempLCUWMasterSchema.setAutoUWFlag("2");
			tempLCUWMasterSchema.setOperator(mOperater);
			tempLCUWMasterSchema.setManageCom(mManageCom);
			tempLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tempLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
			this.mLCUWMasterSet.add(tempLCUWMasterSchema);
			
			// 准备核保子表信息
			LCUWSubDB tLCUWSubDB = new LCUWSubDB();
			String sql = "select * from lcuwsub where ProposalNo='"+"?ProposalNo?"+"' order by uwno desc";			
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(sql);
			sqlbv4.put("ProposalNo", tempLCUWMasterSchema.getProposalNo());
			LCUWSubSet tLCUWSubSet = tLCUWSubDB.executeQuery(sqlbv4);
			if(tLCUWSubSet==null)
			{
				CError.buildErr(this,"查询险种核保子表出错!");
				return false;
			}
			LCUWSubSchema tempLCUWSubSchema = new LCUWSubSchema();
			tempLCUWSubSchema = tLCUWSubSet.get(1);
			
			tempLCUWSubSchema.setPassFlag(this.mUWFlag);
			tempLCUWSubSchema.setAutoUWFlag("2");
			tempLCUWSubSchema.setUWNo(tempLCUWSubSchema.getUWNo()+1);
			tempLCUWSubSchema.setOperator(mOperater);
			tempLCUWSubSchema.setManageCom(mManageCom);
			tempLCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tempLCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
			this.mLCUWSubSet.add(tempLCUWSubSchema);
		}
		/*
		ExeSQL tExeSQL = new ExeSQL();
		String tValue = tExeSQL.getOneValue(tSQL);
		if(tValue!=null&&!tValue.equals("")&&Integer.parseInt(tValue)>0)
		{
			CError.buildErr(this,"合同下有未延期/拒保/撤单的险种,请先对险种下结论!");
			return false;
		}*/
		return true;
	}

	/**
	 * 数据操作撤单业务处理 输出：如果出错，则返回false,否则返回true
	 */
	private boolean returnFee() {
		String payMode = ""; // 交费方式
		String BankCode = ""; // 银行编码
		String BankAccNo = ""; // 银行账号
		String AccName = ""; // 户名
		//增加校验是否银行在途
		//tongmeng 2011-01-25
		//保费折扣 方案 ,新契约银行发盘 不生成应收
		ExeSQL tExeSQL = new ExeSQL();
//		String tSQL_Fee = "select count(*) from ljspay where otherno ='"+mPrtNo+"' "
//		                + " and bankonthewayflag='1' " ;
//		
		String tSQL_Fee = "select count(*) from lysendtobank where polno ='"+"?polno?"+"' ";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tSQL_Fee);
		sqlbv5.put("polno", mPrtNo);
		String tCVAL = tExeSQL.getOneValue(sqlbv5);
		if(tCVAL!=null&&Integer.parseInt(tCVAL)>0)
		{
			CError.buildErr(this,"该投保单有银行在途数据,不允许操作!");
			return false;
		}
		// 测试该投保单是否有暂交费待退
		String strSql = "";
		strSql = "select distinct tempfeeno from ljtempfee where 1=1 and tempfeetype='1' "
				+ " and otherno='"
				+ "?otherno?"
				+ "'"
				+ " and (enteraccdate is not null  and enteraccdate<>'3000-01-01')"
				+ " and confdate is null";
		SSRS tSSRS = new SSRS();
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(strSql);
		sqlbv6.put("otherno", mPrtNo);
		tSSRS = tExeSQL.execSQL(sqlbv6);
		if(tSSRS.getMaxRow()==0)
		{
			logger.debug("Out ReturnFee,没有暂交费待退");
			return true;
		}
		
		
		// 如果通知书号不为空，找出退费方式（优先级依次为支票，银行，现金）
		GetPayType tGetPayType = new GetPayType();
		if (!tGetPayType.getPayTypeForLCPol(mPrtNo)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGetPayType.mErrors);
			logger.debug("查询出错信息  :" + tGetPayType.mErrors);
			return false;
		} else {
			payMode = tGetPayType.getPayMode(); // 交费方式
			BankCode = tGetPayType.getBankCode(); // 银行编码
			BankAccNo = tGetPayType.getBankAccNo(); // 银行账号
			AccName = tGetPayType.getAccName(); // 户名
		}

		TransferData sTansferData = new TransferData();
		sTansferData.setNameAndValue("PayMode", payMode);
		sTansferData.setNameAndValue("NotBLS", "1");
		sTansferData.setNameAndValue("SubmitFlag", "NoSubmit");
		if (payMode.equals("1")) {
			sTansferData.setNameAndValue("BankFlag", "0");
		} else {
			sTansferData.setNameAndValue("BankCode", BankCode);
			sTansferData.setNameAndValue("AccNo", BankAccNo);
			sTansferData.setNameAndValue("AccName", AccName);
			sTansferData.setNameAndValue("BankFlag", "1");
		}
		sTansferData.setNameAndValue("OtherNoFlag", "1");
		// 生成给付通知书号
		String tLimit = PubFun.getNoLimit(mManageCom);
		mGetNoticeNo = PubFun1.CreateMaxNo("GETNOTICENO", tLimit); // 产生给付通知书号
		sTansferData.setNameAndValue("GetNoticeNo", mGetNoticeNo);
		// tongmeng 2007-10-30 add
		// 向全局变量中增加给付通知书号
		this.mTransferData.setNameAndValue("GetNoticeNo", mGetNoticeNo);
		//tongmeng 2009-06-04 add
		//增加标记,财务退费程序排除锁定印刷号
		sTansferData.setNameAndValue("ReleaseContNo", "Y");
		// 由于同一投保单可能存在多笔交费，即存在多个 暂交费收据号码，
		// 且 退费程序每次只能处理一笔交费，故必须针对多笔交费循环调用退费处理程序
		for (int index = 1; index <= tSSRS.getMaxRow(); index++) {
			logger.debug("----------处理第  " + index
					+ " 笔暂收费开始------------ ");
			LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
			LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
			tLJTempFeeSchema.setTempFeeNo(tSSRS.GetText(index,1));
			tLJTempFeeSet.add(tLJTempFeeSchema);

			LJAGetTempFeeSet tLJAGetTempFeeSet = new LJAGetTempFeeSet();
			LJAGetTempFeeSchema tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
			tLJAGetTempFeeSchema.setGetReasonCode("99");
			tLJAGetTempFeeSet.add(tLJAGetTempFeeSchema);
			

			// 准备传输数据 VData
			VData tVData = new VData();
			tVData.add(tLJTempFeeSet);
			tVData.add(tLJAGetTempFeeSet);
			tVData.add(sTansferData);
			tVData.add(mGlobalInput);
			// 数据传输-----准备调用退费程序
			logger.debug("--------开始传输数据---------");
			TempFeeWithdrawBL tTempFeeWithdrawBL = new TempFeeWithdrawBL();
			if (tTempFeeWithdrawBL.submitData(tVData, "INSERT")) {
				logger.debug("---ok---");
			} else {
				logger.debug("---NO---");
			}
			if (tTempFeeWithdrawBL.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tTempFeeWithdrawBL.mErrors);
				return false;
			}
			VData ReturnFeeData = new VData();
			ReturnFeeData = tTempFeeWithdrawBL.getResult();
			MMap ReturnFeeMap = new MMap();
			ReturnFeeMap = (MMap) ReturnFeeData
					.getObjectByObjectName("MMap", 0);
			mmap.add(ReturnFeeMap);
			logger.debug("-----------处理第  " + index
					+ " 笔暂收费 成功-------------");
		}

		logger.debug("Out ReturnFee");
		return true;
	}

	/**
	 * 次标准体校验核保员级别 输出：如果发生错误则返回false,否则返回true
	 * 
	 * @return boolean
	 * 
	 */
	private boolean checkStandGrade() {
		CheckKinds("1");
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setContNo(mContNo);
		mLCPolSet = tLCPolDB.query();
		// 准备险种保单的复核标志
		for (int j = 1; j < mLCPolSet.size(); j++) {
			mOldPolNo = mLCPolSet.get(j).getPolNo();

			LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
			LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();

			tLCUWMasterDB.setProposalNo(mOldPolNo);

			if (tLCUWMasterDB.getInfo() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
				CError.buildErr(this, "LCUWMaster表取数失败!") ;
				return false;
			} else {
				tLCUWMasterSchema = tLCUWMasterDB.getSchema();
			}

			// 有特约，加费，保险计划变更为次标准体
			if (!tLCUWMasterSchema.getSpecFlag().equals("0")
					|| !tLCUWMasterSchema.getChangePolFlag().equals("0")) {
				if (mLMUWSet.size() > 0) {
					for (int i = 1; i <= mLMUWSet.size(); i++) {
						LMUWSchema tLMUWSchema = new LMUWSchema();
						tLMUWSchema = mLMUWSet.get(i);

						mCalCode = tLMUWSchema.getCalCode(); // 次标准体计算公式代码
						String tempuwgrade = CheckPol();
						if (tempuwgrade != null) {
							if (mUWPopedom.compareTo(tempuwgrade) < 0) {
								CError.buildErr(this, "无此次标准体投保件核保权限，需要上报上级核保师!") ;
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * 核保险种信息校验,准备核保算法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean CheckKinds(String tFlag) {
		mLMUWSet.clear();
		LMUWSchema tLMUWSchema = new LMUWSchema();
		// 查询算法编码
		if (tFlag.equals("1")) {
			tLMUWSchema.setUWType("13"); // 非标准体
		}

		if (tFlag.equals("2")) {
			tLMUWSchema.setUWType("14"); // 拒保延期
		}

		tLMUWSchema.setRiskCode("000000");
		tLMUWSchema.setRelaPolType("I");

		LMUWDB tLMUWDB = new LMUWDB();
		tLMUWDB.setSchema(tLMUWSchema);

		mLMUWSet = tLMUWDB.query();
		if (tLMUWDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMUWDB.mErrors);
			CError.buildErr(this, "核保级别校验算法读取失败!") ;
			// mLMUWDBSet.clear();
			return false;
		}
		return true;
	}

	/**
	 * 拒保，撤单校验核保员级别 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkUserGrade() {
		CheckKinds("2");

		if (mLMUWSet.size() > 0) {
			for (int i = 1; i <= mLMUWSet.size(); i++) {
				LMUWSchema tLMUWSchema = new LMUWSchema();
				tLMUWSchema = mLMUWSet.get(i);

				mCalCode = tLMUWSchema.getCalCode(); // 延期拒保计算公式代码
				String tempuwgrade = CheckPol();
				if (tempuwgrade != null) {
					if (mUWPopedom.compareTo(tempuwgrade) < 0) {
						CError.buildErr(this, "无此单拒保，延期权限，需上报上级核保师!") ;
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * 个人单核保 输出：如果发生错误则返回false,否则返回true
	 */
	private String CheckPol() {
		// 准备数据
		CheckPolInit(mLCPolSchema);

		String tUWGrade = "";

		// 计算
		Calculator mCalculator = new Calculator();
		mCalculator.setCalCode(mCalCode);
		// 增加基本要素
		mCalculator.addBasicFactor("Get", mCalBase.getGet());
		mCalculator.addBasicFactor("Mult", mCalBase.getMult());
		mCalculator.addBasicFactor("Prem", mCalBase.getPrem());
		// mCalculator.addBasicFactor("PayIntv", mCalBase.getPayIntv() );
		// mCalculator.addBasicFactor("GetIntv", mCalBase.getGetIntv() );
		mCalculator.addBasicFactor("AppAge", mCalBase.getAppAge());
		mCalculator.addBasicFactor("Sex", mCalBase.getSex());
		mCalculator.addBasicFactor("Job", mCalBase.getJob());
		mCalculator.addBasicFactor("PayEndYear", mCalBase.getPayEndYear());
		mCalculator.addBasicFactor("GetStartDate", "");
		// mCalculator.addBasicFactor("GetYear", mCalBase.getGetYear() );
		mCalculator.addBasicFactor("Years", mCalBase.getYears());
		mCalculator.addBasicFactor("Grp", "");
		mCalculator.addBasicFactor("GetFlag", "");
		mCalculator.addBasicFactor("ValiDate", "");
		mCalculator.addBasicFactor("Count", mCalBase.getCount());
		mCalculator.addBasicFactor("FirstPayDate", "");
		// mCalculator.addBasicFactor("AddRate", mCalBase.getAddRate() );
		// mCalculator.addBasicFactor("GDuty", mCalBase.getGDuty() );
		mCalculator.addBasicFactor("PolNo", mCalBase.getPolNo());
		mCalculator.addBasicFactor("InsuredNo", mLCPolSchema.getInsuredNo());
		;

		String tStr = "";
		tStr = mCalculator.calculate();
		if (tStr.trim().equals("")) {
			tUWGrade = "";
		} else {
			tUWGrade = tStr.trim();
		}

		logger.debug("AmntGrade:" + tUWGrade);

		return tUWGrade;
	}

	/**
	 * 个人单核保数据准备 输出：如果发生错误则返回false,否则返回true
	 */
	private void CheckPolInit(LCPolSchema tLCPolSchema) {
		mCalBase = new CalBase();
		mCalBase.setPrem(tLCPolSchema.getPrem());
		mCalBase.setGet(tLCPolSchema.getAmnt());
		mCalBase.setMult(tLCPolSchema.getMult());
		// mCalBase.setYears( tLCPolSchema.getYears() );
		mCalBase.setAppAge(tLCPolSchema.getInsuredAppAge());
		mCalBase.setSex(tLCPolSchema.getInsuredSex());
		mCalBase.setJob(tLCPolSchema.getOccupationType());
		mCalBase.setCount(tLCPolSchema.getInsuredPeoples());
		mCalBase.setPolNo(tLCPolSchema.getPolNo());
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();
		map.add(mMap);

		map.put(mLCContSchema, "UPDATE");
		map.put(mLCCUWMasterSet, "UPDATE");
		map.put(mLCCUWSubSet, "INSERT");
		if(this.mUpdateLCPolSet.size()>0)
		{
			map.put(this.mLCUWMasterSet,"UPDATE");
			map.put(this.mLCUWSubSet,"INSERT");
			map.put(this.mUpdateLCPolSet,"UPDATE");
		}
		// map.put(outLJSPaySet, "DELETE");
		// map.put(outLBTempFeeSet, "INSERT");
		// map.put(outLBTempFeeClassSet, "INSERT");
		map.put(mLOPRTManagerSet, "INSERT");

		map.add(mmap);
		map.add(mSplitMap);
		mResult.add(mTransferData);
		mResult.add(map);
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		/**
		 * comment by HYQ 20050425 核保师已在核保师权限表中定义 //校验核保员权限 LDUserDB tLDUserDB = new
		 * LDUserDB(); tLDUserDB.setUserCode(mOperater);
		 * logger.debug("mOperate" + mOperater); if (!tLDUserDB.getInfo()) {
		 * CError tError = new CError(); tError.moduleName = "UWManuNormChkBL";
		 * tError.functionName = "checkUWGrade"; tError.errorMessage =
		 * "无此操作员信息，不能核保!" + "（操作员：" + mOperater + "）";
		 * this.mErrors.addOneError(tError); return false; }
		 * 
		 * mUWPopedom = tLDUserDB.getUWPopedom();; mAppGrade = mUWPopedom;
		 */
		// LDUWUserDB tLDUWUserDB = new LDUWUserDB();
		// tLDUWUserDB.setUserCode(mOperater);
		// tLDUWUserDB.setUWType("1");
		// // tLDUWUserDB.setUWPopedom("H1");
		// if (!tLDUWUserDB.getInfo())
		// {
		// CError tError = new CError();
		// tError.moduleName = "UWManuNormChkBL";
		// tError.functionName = "checkUWGrade";
		// tError.errorMessage = "无此核保师信息，不能核保!" +
		// "（操作员：" + mOperater + "）";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		//
		// mUWPopedom = tLDUWUserDB.getUWPopedom();
		// mAppGrade = mUWPopedom;
		// 核保师核保权限的校验
		//tongmeng 2009-01-15 add
		//对于5,不做处理
		if(!mUWFlag.equals("5"))
		{
		logger.debug("kaishijiaoyan");
		LDUWGradeDB tLDUWGradeDB = new LDUWGradeDB();
		tLDUWGradeDB.setUserCode(mOperater);
		tLDUWGradeDB.setUWState(mUWFlag);
		tLDUWGradeDB.setUWType("1");
		LDUWGradeSet tLDUWGradeSet = new LDUWGradeSet();
		tLDUWGradeSet = tLDUWGradeDB.query();
		logger.debug("tLDUWGradeSet.size():" + tLDUWGradeSet.size());
			if (tLDUWGradeSet.size() == 0) {
				CError.buildErr(this, "核保师:" + mOperater + "没有权限下" + mUWFlag
						+ "核保结论!");
				return false;

			}
		}
		// 校验保单信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "保单" + mContNo + "信息查询失败!") ;
			return false;
		}
		mLCContSchema.setSchema(tLCContDB);

		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setContNo(mLCContSchema.getContNo());
		mLCPolSet.set(tLCPolDB.query());
		if (tLCPolDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);
			CError.buildErr(this, "险种保单查询失败!") ;
			return false;
		}

		// 准备险种保单的复核标志
		for (int i = 1; i <= mLCPolSet.size(); i++) {
			if (!mLCPolSet.get(i).getUWFlag().equals("1")
					&& !mLCPolSet.get(i).getUWFlag().equals("2")
					&& !mLCPolSet.get(i).getUWFlag().equals("3")
					&& !mLCPolSet.get(i).getUWFlag().equals("4")
					&& !mLCPolSet.get(i).getUWFlag().equals("9")
					&& !mLCPolSet.get(i).getUWFlag().equals("a")
					&& !mLCPolSet.get(i).getUWFlag().equals("b")
					&& !mLCPolSet.get(i).getUWFlag().equals("c")
					&& !mLCPolSet.get(i).getUWFlag().equals("d")) {
				{
					CError.buildErr(this, "险种保单" + mLCPolSet.get(i).getPolNo()
							+ "未下核保结论") ;
					return false;
				}
			}
		}

		// 如果不是上报
		// tongmeng 2007-10-30 modify
		// 增加撤单结论
		if (this.mUWUpReport.equals("0")) {
			if (!mUWFlag.equals("1") && !mUWFlag.equals("2")
					&& !mUWFlag.equals("4") && !mUWFlag.equals("9")
					&& !mUWFlag.equals("a")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCPolDB.mErrors);
				CError.buildErr(this, "请输入正确的核保结论代码!") ;
				return false;

			}
			//tongmeng 2009-05-11 modify
		    //延期,拒保,撤单,需要校验合同下的所有险种都撤单
			if(this.mUWFlag.equals("a")||this.mUWFlag.equals("1")||this.mUWFlag.equals("2"))
			{
				/*
				String tSQL = " select count(*) from lcpol where uwflag not in ('1','2','a') and contno='"+mContNo+"'";
				ExeSQL tExeSQL = new ExeSQL();
				String tValue = tExeSQL.getOneValue(tSQL);
				if(tValue!=null&&!tValue.equals("")&&Integer.parseInt(tValue)>0)
				{
					CError.buildErr(this,"合同下有未延期/拒保/撤单的险种,请先对险种下结论!");
					return false;
				}*/
			}
			//tongmeng 2009-05-14 modify
			//如果不是延期,拒保,撤单的合同,需要校验合同的加费评点是否超标.
			if(!(this.mUWFlag.equals("a")||this.mUWFlag.equals("1")||this.mUWFlag.equals("2")))
			{
				String tSQL_AddFee = " select (case count(*) when 0 then 0 else 1 end) from lduwuser where uwtype='1' "
					               + " and usercode='"+"?usercode?"+"' " 
					               + " and addpoint<( select (case when max(suppriskscore) is null then 0 else max(suppriskscore) end) from lcprem "
					               + " where contno ='"+"?contno?"+"') ";
				SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
				sqlbv7.sql(tSQL_AddFee);
				sqlbv7.put("usercode", mOperater);
				sqlbv7.put("contno", mContNo);
				String tValue_AddFee = "";
				ExeSQL tExeSQL = new ExeSQL();
				tValue_AddFee = tExeSQL.getOneValue(sqlbv7);
				if(tValue_AddFee!=null&&!tValue_AddFee.equals("")&&Integer.parseInt(tValue_AddFee)>0)
				{
					CError.buildErr(this,"该单加费评点超过您的核保权限,不能核保通过!");
					return false;
					
				}
				
			}
			/**
			 * 根据个人下险种的核保结论，判断总单的核保结论
			 * 个单：1-拒保,2－延期,3－加费承保,4－特约承保,9－正常承保,a－退保,b-减额,c-减费,d-限额
			 * 总单：1-拒保，2－延期，4-非标准体，9-标准体
			 * 
			 * 
			 * 主险 附加险 核保结论 标准承保 标准承保 标准体 拒保 * 拒保 延期 * 延期
			 * 
			 */

			// ExeSQL tExeSQL;
			// String tSql = "";
			// String rs = "";
			int aUWFlag1 = 0; // 拒保
			int aUWFlag2 = 0; // 延期
			int aUWFlag3 = 0; // 加费
			int aUWFlag4 = 0; // 特约
			int aUWFlag9 = 0; // 正常承保
			int aUWFlaga = 0; // 退保
			int aUWFlagb = 0; // 减额
			int aUWFlagc = 0; // 减费
			int aUWFlagd = 0; // 限额

			int bUWFlag1 = 0; // 拒保
			int bUWFlag2 = 0; // 延期
			int bUWFlag3 = 0; // 加费
			int bUWFlag4 = 0; // 特约
			int bUWFlag9 = 0; // 正常承保
			int bUWFlaga = 0; // 退保
			int bUWFlagb = 0; // 减额
			int bUWFlagc = 0; // 减费
			int bUWFlagd = 0; // 限额
			int CancleForegetSpecFlag = 0; // 取消提前给付特约

			int aCount = 0;
			int bCount = 0;

			// 准备险种保单的核保标志
			for (int i = 1; i <= mLCPolSet.size(); i++) {
				// 主险核保结论
				if (mLCPolSet.get(i).getMainPolNo().equals(
						mLCPolSet.get(i).getPolNo())) {
					aCount++;
					if (mLCPolSet.get(i).getUWFlag().equals("1")) {
						aUWFlag1++;
					} else if (mLCPolSet.get(i).getUWFlag().equals("2")) {
						aUWFlag2++;
					} else if (mLCPolSet.get(i).getUWFlag().equals("3")) {
						aUWFlag3++;
					} else if (mLCPolSet.get(i).getUWFlag().equals("4")) {
						aUWFlag4++;
					} else if (mLCPolSet.get(i).getUWFlag().equals("9")) {
						aUWFlag9++;
					} else if (mLCPolSet.get(i).getUWFlag().equals("a")) {
						aUWFlaga++;
					} else if (mLCPolSet.get(i).getUWFlag().equals("b")) {
						aUWFlagb++;
					} else if (mLCPolSet.get(i).getUWFlag().equals("c")) {
						aUWFlagc++;
					} else if (mLCPolSet.get(i).getUWFlag().equals("d")) {
						aUWFlagd++;
					}
				} else {
					bCount++;
					if (mLCPolSet.get(i).getUWFlag().equals("1")) {
						bUWFlag1++;
					} else if (mLCPolSet.get(i).getUWFlag().equals("2")) {
						bUWFlag2++;
					} else if (mLCPolSet.get(i).getUWFlag().equals("3")) {
						bUWFlag3++;
					} else if (mLCPolSet.get(i).getUWFlag().equals("4")) {
						bUWFlag4++;
					} else if (mLCPolSet.get(i).getUWFlag().equals("9")) {
						bUWFlag9++;
					} else if (mLCPolSet.get(i).getUWFlag().equals("a")) {
						bUWFlaga++;
					} else if (mLCPolSet.get(i).getUWFlag().equals("b")) {
						bUWFlagb++;
					} else if (mLCPolSet.get(i).getUWFlag().equals("c")) {
						bUWFlagc++;
					} else if (mLCPolSet.get(i).getUWFlag().equals("d")) {
						bUWFlagd++;
					}
				}
				// if (!mLCPolSet.get(i).getUWFlag().equals("1") &&
				// !mLCPolSet.get(i).getUWFlag().equals("2") &&
				// !mLCPolSet.get(i).getUWFlag().equals("4") &&
				// !mLCPolSet.get(i).getUWFlag().equals("9"))
				// {
				// tExeSQL = new ExeSQL();
				// //校验此保单被保人是否暂停。如果暂停则不校验是否以下核保结论
				// tSql = " select INSUREDSTAT from lcinsured where 1=1 " +
				// " and ContNo = '" + mContNo + "'" +
				// " and insuredno = '" +
				// mLCPolSet.get(i).getInsuredNo() + "'";
				// rs = tExeSQL.getOneValue(tSql);
				// logger.debug(tSql);
				// logger.debug(rs);
				// if (rs != null && rs.equals("1"))
				// {
				// //Right DoNoThing
				// }
				// else
				// {
				// CError tError = new CError();
				// tError.moduleName = "UWRReportAfterInitService";
				// tError.functionName = "checkData";
				// tError.errorMessage = "险种保单" + mLCPolSet.get(i).getPolNo() +
				// "未下核保结论";
				// this.mErrors.addOneError(tError);
				// return false;
				// }
				// }
				
				if(mLCPolSet.get(i).getCancleForegetSpecFlag()!=null && mLCPolSet.get(i).getCancleForegetSpecFlag().equals("1"))
					CancleForegetSpecFlag = 1;

			}
			logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			logger.debug("aUWFlag1==" + aUWFlag1);
			logger.debug("aUWFlag2==" + aUWFlag2);
			logger.debug("aUWFlag3==" + aUWFlag3);
			logger.debug("aUWFlag4==" + aUWFlag4);
			logger.debug("aUWFlag9==" + aUWFlag9);
			logger.debug("aUWFlaga==" + aUWFlaga);
			logger.debug("aUWFlagb==" + aUWFlagb);
			logger.debug("aUWFlagc==" + aUWFlagc);
			logger.debug("aUWFlagd==" + aUWFlagd);
			logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			logger.debug("bUWFlag1==" + bUWFlag1);
			logger.debug("bUWFlag2==" + bUWFlag2);
			logger.debug("bUWFlag3==" + bUWFlag3);
			logger.debug("bUWFlag4==" + bUWFlag4);
			logger.debug("bUWFlag9==" + bUWFlag9);
			logger.debug("bUWFlaga==" + bUWFlaga);
			logger.debug("bUWFlagb==" + bUWFlagb);
			logger.debug("bUWFlagc==" + bUWFlagc);
			logger.debug("bUWFlagd==" + bUWFlagd);
			logger.debug("aCount==" + aCount + "  bCount==:" + bCount);
			logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			/**
			 * 进行总单结论判断
			 */
			if (aUWFlag9 != aCount || bUWFlag9 != bCount) {
				// 如果没有主险拒保延期
				if ((aUWFlag1 == 0) && (aUWFlag2 == 0) && (aUWFlaga == 0)) {
					if (!mUWFlag.equals("4")&&!mUWFlag.equals("1")&&!mUWFlag.equals("2")&&!mUWFlag.equals("a")) {
						CError.buildErr(this, "险种保单下有非“标准承保”结论，总单只可以下“非标准体”核保结论！") ;
						return false;

					}
				}
			}

			if (CancleForegetSpecFlag ==1) {
				// 如果有取消提前给付特约
					if (mUWFlag.equals("9")) {
						CError.buildErr(this, "合同有取消提前给付特约的操作，不能下标准体核保结论！") ;
						return false;

					}
			}
//			if (aUWFlag9 == aCount && bUWFlag9 == bCount) {
//				if (!mUWFlag.equals("9")) {
//					CError.buildErr(this, "险种保单下没有“非标准承保”结论，总单只可以下“标准体”核保结论！") ;
//					return false;
//
//				}
//
//			}

			if (aUWFlag1 > 0) {
				if (mUWFlag == null || !(mUWFlag.equals("1"))) {

					CError.buildErr(this, "主险“拒保”，总单必须下“拒保”核保结论！") ;
					return false;
				}

			}
			if (aUWFlag2 > 0) {
				if (mUWFlag == null || !(mUWFlag.equals("2"))) {

					CError.buildErr(this, "主险“延期”，总单必须下“延期”核保结论！") ;
					return false;
				}

			}
			if (aUWFlaga > 0) {
				if (mUWFlag == null || !(mUWFlag.equals("a"))) {

					CError.buildErr(this, "主险“撤单”，总单必须下“撤单”核保结论！") ;
					return false;
				}

			}
			
			//2008-12-12 ln add 如果有健康特约(加费、承保计划变更)则不能下标准体
			LCCSpecDB tLCCSpecDB = new LCCSpecDB();
			LCCSpecSet tLCCSpecSet = new LCCSpecSet();
			String sql = "select * from lccspec where ProposalContNo = '"+"?ProposalContNo?"
			           +"' and spectype in(select code from ldcode where 1 = 1 and codetype = 'healthspcetemp')";
			logger.debug(sql);
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql(sql);
			sqlbv8.put("ProposalContNo", mLCContSchema.getProposalContNo());
			tLCCSpecSet = tLCCSpecDB.executeQuery(sqlbv8);
			if (tLCCSpecDB.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCCSpecDB.mErrors);
				CError.buildErr(this, "特约信息查询失败!") ;
				return false;
			}
			if (tLCCSpecSet!=null && tLCCSpecSet.size()>0 && mUWFlag.equals("9")) {
				// @@错误处理
				CError.buildErr(this, "合同有健康特约，不能下标准体核保结论!") ;
				return false;
			}
			LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
			tLCUWMasterDB.setContNo(mLCContSchema.getContNo());
			LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
			tLCUWMasterSet = tLCUWMasterDB.query();
			if (tLCUWMasterDB.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
				CError.buildErr(this, "LCUWMaster表取数失败!") ;
				return false;
			}

			int n = tLCUWMasterSet.size();
			logger.debug("该投保单的核保主表当前记录条数:  " + n);
            for(int i=1;i<=n;i++)
            {
            	if(((tLCUWMasterSet.get(i).getAddPremFlag()!=null && tLCUWMasterSet.get(i).getAddPremFlag().equals("1"))
        				|| (tLCUWMasterSet.get(i).getChangePolFlag()!=null && tLCUWMasterSet.get(i).getChangePolFlag().equals("1")))
        				&&mUWFlag.equals("9"))
        		{
        			CError.buildErr(this, "险种保单（"+tLCUWMasterSet.get(i).getPolNo()+"）有加费或保险计划变更，合同不能下标准体核保结论!") ;
        			return false;
        		}
            }
			
			//end
			/**
			 * @todo 对于需要进行职业加费的投保单是否已经进行了职业加费进行判断
			 */

			/**
			 * @todo 取得投保单下5,6类职业的投保人，被保人，以及第二被保人
			 */
		/*	String tSQL = "select lcpol.polno,lcpol.appntno, lcpol.riskcode, 'A'"
					+ " from lcpol" + " where 1 = 1" + " and lcpol.contno = '"
					+ mLCContSchema.getContNo()
					+ "'"
					+ " and exists (select 'x'"
					+ " from lcappnt"
					+ " where 1 = 1"
					+ " and lcappnt.contno = '"
					+ mLCContSchema.getContNo()
					+ "'"
					+ " and lcappnt.appntno = lcpol.appntno"
					+ " and lcappnt.occupationtype in ('5', '6'))"
					+ " union"
					+ " select polno,insuredno, riskcode, 'I1'"
					+ " from lcpol"
					+ " where 1 = 1"
					+ " and contno = '"
					+ mLCContSchema.getContNo()
					+ "'"
					+ " and occupationtype in ('5', '6')"
					+ " union"
					+ " select a.polno,a.customerno, b.riskcode, 'I2'"
					+ " from lcinsuredrelated a, lcpol b"
					+ " where 1 = 1"
					+ " and a.polno = b.polno"
					+ " and b.contno = '"
					+ mLCContSchema.getContNo()
					+ "'"
					+ " and exists (select 'x'"
					+ " from lcinsured"
					+ " where 1 = 1"
					+ " and lcinsured.contno = '"
					+ mLCContSchema.getContNo()
					+ "'"
					+ " and lcinsured.insuredno = a.customerno"
					+ " and lcinsured.occupationtype in ('5', '6'))";

			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = tExeSQL.execSQL(tSQL);
			// 如果存在则进行判断是否已经进行了加费
			if (tSSRS.getMaxRow() > 0) {
				for (int i = 0; i < tSSRS.getMaxRow(); i++) {
					if (tSSRS.GetText(i + 1, 4) != null
							&& tSSRS.GetText(i + 1, 4).equals("I1")) {

						/**
						 * @todo 判断该险种是否需要进行职业加费
						 */
				/*		tSQL = "select *"
								+ " from lmdutypayaddfee"
								+ " where lmdutypayaddfee.riskcode = '"
								+ tSSRS.GetText(i + 1, 3)
								+ "'"
								+ " and lmdutypayaddfee.addfeetype = '02'"
								+ " and lmdutypayaddfee.addfeeobject in ('00', '02', '03')";

						SSRS bSSRS = tExeSQL.execSQL(tSQL);
						if (bSSRS.getMaxRow() >= 1) {
							tSQL = "select * from lcprem where polno='"
									+ tSSRS.GetText(i + 1, 1)
									+ "' and lcprem.payplantype='02' and lcprem.addfeedirect in ('02','03')";
							SSRS aSSRS = tExeSQL.execSQL(tSQL);
							if (aSSRS.getMaxRow() <= 0) {
								CError.buildErr(this, "该保单需要对被保人进行职业加费，不能进行核保确认！") ;
								return false;

							}
						}
						continue;
					} else if (tSSRS.GetText(i + 1, 4) != null
							&& tSSRS.GetText(i + 1, 4).equals("A")) {
						/**
						 * @todo 判断该险种是否需要进行职业加费
						 */
					/*	tSQL = "select *"
								+ " from lmdutypayaddfee"
								+ " where lmdutypayaddfee.riskcode = '"
								+ tSSRS.GetText(i + 1, 3)
								+ "'"
								+ " and lmdutypayaddfee.addfeetype = '02'"
								+ " and lmdutypayaddfee.addfeeobject in ('00', '01')";

						SSRS bSSRS = tExeSQL.execSQL(tSQL);
						/**
						 * 如果险种需要职业加费则进行职业加费判断
						 */
					/*	if (bSSRS.getMaxRow() >= 1) {
							tSQL = "select * from lcprem where polno='"
									+ tSSRS.GetText(i + 1, 1)
									+ "' and lcprem.payplantype='02' and lcprem.addfeedirect='01'";

							SSRS aSSRS = tExeSQL.execSQL(tSQL);
							if (aSSRS.getMaxRow() <= 0) {
								CError.buildErr(this, "该保单需要对投保人进行职业加费，不能进行核保确认！") ;
								return false;

							}
						}
						continue;
					} else if (tSSRS.GetText(i + 1, 4) != null
							&& tSSRS.GetText(i + 1, 4).equals("I2")) {
						/**
						 * @todo 判断该险种是否需要进行职业加费
						 */
				/*		tSQL = "select *"
								+ " from lmdutypayaddfee"
								+ " where lmdutypayaddfee.riskcode = '"
								+ tSSRS.GetText(i + 1, 3)
								+ "'"
								+ " and lmdutypayaddfee.addfeetype = '02'"
								+ " and lmdutypayaddfee.addfeeobject in ('00', '02' ,'03')";

						SSRS bSSRS = tExeSQL.execSQL(tSQL);
						/**
						 * 如果险种需要职业加费则进行职业加费判断
						 */

					/*	if (bSSRS.getMaxRow() >= 1) {
							tSQL = "select * from lcprem where polno='"
									+ tSSRS.GetText(i + 1, 1)
									+ "' and lcprem.payplantype='02' and lcprem.addfeedirect in ('03','04')";

							SSRS aSSRS = tExeSQL.execSQL(tSQL);
							if (aSSRS.getMaxRow() <= 0) {
								CError.buildErr(this, "该保单需要对被保人进行职业加费，不能进行核保确认！") ;
								return false;

							}
						}
						continue;
					}
				}

			}*/

		}
		return true;
	}

	private boolean checkPolConfirm() {
		String sql = " select count(1) from lcpol where uwflag not in ('1','2') and contno = '"
				+ "?contno?" + "'";
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(sql);
		sqlbv9.put("contno", mContNo);
		ExeSQL tExeSQL = new ExeSQL();
		String rs = tExeSQL.getOneValue(sqlbv9);
		logger.debug("rs==" + rs);
		logger.debug("mUWFlag==" + mUWFlag);
		if (mUWFlag.equals("9") && rs.equals("0")) {
			CError.buildErr(this, "个险的结论为延期或拒保，总单的结论不能是正常承包或通融承保") ;
			return false;

		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		// 获得全局公共数据
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		if (mGlobalInput == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据失败!") ;
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据Operate失败!") ;
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据ManageCom失败!") ;
			return false;
		}

		// 获得当前工作任务的保单号
		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中ContNo失败!") ;
			return false;
		}

		// 获得当前工作任务的印刷号
		mPrtNo = (String) mTransferData.getValueByName("PrtNo");
		if (mPrtNo == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中PrtNo失败!") ;
			return false;
		}

		// 获得当前工作任务的核保结论标志
		mUWFlag = (String) mTransferData.getValueByName("UWFlag");
		if (mUWFlag == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中UWFlag失败!") ;
			return false;
		}

		// 获得当前工作任务的核保意见
		mUWIdea = (String) mTransferData.getValueByName("UWIdea");
		if (mUWIdea == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中UWIdea失败!") ;
			return false;
		}
		// 核保/撤单区分标志
		mUWCancelFlag = (String) mTransferData.getValueByName("UWCancelFlag");
		if (mUWCancelFlag != null && mUWCancelFlag.equals("UWCancelFlag")) {
			mBLUWCancelFlag = true;
		}
		// 核保上报流向
		mUWUpReport = (String) mTransferData.getValueByName("UWUpReport");
		if (mUWUpReport == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中UWUpReport失败!") ;
			return false;
		}
		// 核保上报级别
		mUWUpPopedom = (String) mTransferData.getValueByName("UWPopedom");
		if (!mUWUpReport.equals("0")) {
			mLCUWSendTraceSchema = (LCUWSendTraceSchema) mTransferData
					.getValueByName("LCUWSendTraceSchema");
		}

		// 建议核保结论
		mSugIndUWFlag = (String) mTransferData.getValueByName("SugIndUWFlag");
		// 建议核保意见
		mSugIndUWIdea = (String) mTransferData.getValueByName("SugIndUWIdea");

		this.mValiDate = mTransferData.getValueByName("ValiDate") == null ? ""
				: (String) mTransferData.getValueByName("ValiDate");
		return true;
	}

	/**
	 * stopInsured 暂停被保人
	 * 
	 * @return boolean
	 */
	private boolean stopInsured() {
		SplitFamilyBL tSplitFamilyBL = new SplitFamilyBL();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ContNo", mContNo);

		VData tVData = new VData();
		tVData.add(mGlobalInput);
		tVData.add(tTransferData);

		tSplitFamilyBL.submitData(tVData, "");

		if (tSplitFamilyBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tSplitFamilyBL.mErrors);
			return false;
		} else {
			mNewContNo = (String) tSplitFamilyBL.getResult().getObject(0);
			logger.debug("NewContNo==" + mNewContNo);
			mSplitMap = (MMap) tSplitFamilyBL.getResult()
					.getObjectByObjectName("MMap", 0);
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		int tflag = 0;
		// 准备合同表数据
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "合同" + mContNo + "信息查询失败!") ;
			return false;
		}
		mLCContSchema.setSchema(tLCContDB);

		// 准备保单的复核标志
		mLCContSchema.setUWFlag(mUWFlag);
		mLCContSchema.setUWDate(PubFun.getCurrentDate());
		mLCContSchema.setUWTime(PubFun.getCurrentTime());
		mLCContSchema.setModifyDate(mLCContSchema.getUWDate());
		mLCContSchema.setModifyTime(mLCContSchema.getUWTime());
		mLCContSchema.setUWOperator(this.mOperater);
		/*
		 * 险种核保结论和合同核保结论分开下 LCPolDB tLCPolDB = new LCPolDB();
		 * tLCPolDB.setContNo(mLCContSchema.getContNo()); mLCPolSet =
		 * tLCPolDB.query(); //准备险种保单的复核标志 for (int i = 1; i <= mLCPolSet.size();
		 * i++) { mLCPolSet.get(i).setUWFlag(mUWFlag);
		 * mLCPolSet.get(i).setUWDate(PubFun.getCurrentDate()); }
		 */
		// 准备合同复核表数据
		if (prepareContUW() == false) {
			return false;
		}
		// 准备险种复核表数据
		// if (prepareAllUW() == false)
		// return false;

		// 不是团体下个人单
		// tongmeng 2007-10-30 注释到此处
		// 统一生成拒保、延期、撤单通知书
		/*
		 * if (!mPolType.equals("2")) { if (mUWFlag.equals("a")) {
		 * //撤单时判断是否有暂交费待退，如果有则生成打印撤单退费通知书记录 String strSql =" select
		 * tempfeeno,tempfeetype,riskcode from ljtempfee where 1=1 and
		 * tempfeetype='1' " + " and otherno='" + mPrtNo + "'" + " and enteraccdate
		 * is not null " + " and confdate is null"; LJTempFeeDB tLJTempFeeDB = new
		 * LJTempFeeDB(); LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		 * tLJTempFeeSet.set(tLJTempFeeDB.executeQuery(strSql));
		 * logger.debug("暂交费数量: " + tLJTempFeeSet.size()); if
		 * (tLJTempFeeSet.size()>0) { logger.debug("有暂交费待退，准备生成撤单退费通知书打印记录");
		 * if (print() == false) { return false; } } } }
		 */
		// 注释结束。。。。。。。。。。。。。。
		if (mUWFlag.equals("2")) {
			TimeAccept();
		}
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(mContNo);
		LCInsuredSet tLCInsuredSet = tLCInsuredDB.query();
		if (tLCInsuredSet == null || tLCInsuredSet.size() <= 0) {
			CError.buildErr(this, "被保人信息失败!") ;
			return false;
		}
		for (int i = 1; i <= tLCInsuredSet.size(); i++) {
			if (tLCInsuredSet.get(i).getInsuredStat() != null
					&& tLCInsuredSet.get(i).getInsuredStat().length() > 0
					&& tLCInsuredSet.get(i).getInsuredStat().equals("1")) {
				tflag = 1;
			}
		}
		// if (tflag == 1)
		// {
		// mStopFlag = "1";
		// if (!stopInsured())
		// {
		// return false;
		// }
		// }

		return true;
	}

	/**
	 * 延期承保 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean TimeAccept() {
		Date temp;
		String temp1 = "D";
		Date temp2;

		FDate tFDate = new FDate();
		temp = null;
		temp2 = tFDate.getDate(mLCPolSchema.getCValiDate());
		// delete by yt 20030719,将延期日期的类型修改为字符串，该部分代码不再有效
		// mvalidate = PubFun.calDate(temp2,mpostday,temp1,temp);

		// logger.debug("---TimeAccept---");
		// mLCPolSchema.setCValiDate(mvalidate);
		// logger.debug("---mvalidate---"+mvalidate);
		return true;
	}

	/**
	 * 打印信息表
	 * 
	 * @return
	 */
	private boolean print() {
		String tPrtSeq = "";
		String tLimit = "";
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

		tLOPRTManagerSchema.setOtherNo(mContNo);
		logger.debug("ContNo:" + mContNo);
		tLOPRTManagerSchema.setManageCom(mLCPolSchema.getManageCom());
		tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT);
		tLOPRTManagerSchema.setAgentCode(mLCContSchema.getAgentCode());
		tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT);
		tLOPRTManagerSchema.setReqOperator(mOperator);
		tLOPRTManagerSchema.setReqCom(mManageCom);
		tLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom());
		tLOPRTManagerSchema.setStateFlag("0"); // 打印标志“0”表示未打
		tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
		if (mUWFlag.equals("1")) { // 拒保通知书
			tLimit = PubFun.getNoLimit(mManageCom);
			tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_DECLINE);
			tLOPRTManagerSchema.setStandbyFlag1(mContNo);
			tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
			tLOPRTManagerSchema.setOldPrtSeq(tPrtSeq);
		}
		if (mUWFlag.equals("2")) { // 延期通知书
			tLimit = PubFun.getNoLimit(mManageCom);
			tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_DEFER);
			tLOPRTManagerSchema.setStandbyFlag1(mContNo);
			tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
			tLOPRTManagerSchema.setOldPrtSeq(tPrtSeq);
		}

		if (mUWFlag.equals("a")) { // 撤单
			tLimit = PubFun.getNoLimit(mManageCom);
			tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_WITHDRAW);
			tLOPRTManagerSchema.setStandbyFlag1(mContNo);
			tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
			tLOPRTManagerSchema.setOldPrtSeq(tPrtSeq);
		}

		mLOPRTManagerSet.add(tLOPRTManagerSchema);
		return true;
	}

	/**
	 * 检查是不是需要送核保通知书到打印队列
	 * 
	 * @return
	 */
	private String checkBackOperator(String tPrintFlag) {
		LCPolSet tLCPolSet = new LCPolSet();

		for (int i = 1; i <= mLCPolSet.size(); i++) {
			LCPolSchema tLCPolSchema = new LCPolSchema();

			tLCPolSchema = mLCPolSet.get(i);

			// 有返回保户需要打印
			// String tsql = "select * from lcpol where ProposalNo in ( select
			// ProposalNo from LCIssuePol where ((makedate >= (select max(makedate)
			// from lcissuepol where backobjtype in ('1','4') and ProposalNo =
			// '"+tLCPolSchema.getPolNo()+"' and makedate is not null)) or ((select
			// max(makedate) from lcissuepol where backobjtype in ('1','4') and
			// ProposalNo = '"+tLCPolSchema.getPolNo()+"') is null))"
			String tsql = "select * from lcpol where  ProposalNo in ( select ProposalNo from LCIssuePol where 1 = 1 "
					+ " and backobjtype  = '3'"
					+ " and ProposalNo = '"
					+ "?ProposalNo?"
					+ "'"
					+ " and makedate is not null"
					+ " and replyresult is null"
					+ " and needprint = 'Y')";
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(tsql);
			sqlbv10.put("ProposalNo", tLCPolSchema.getPolNo());
			logger.debug("printchecksql:" + tsql);
			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet t2LCPolSet = new LCPolSet();
			t2LCPolSet = tLCPolDB.executeQuery(sqlbv10);
			if (t2LCPolSet.size() > 0) {
				tPrintFlag = "2";
			}
			// 只返回给操作员,机构不打印
			// tsql = "select * from lcpol where ProposalNo in ( select ProposalNo
			// from LCIssuePol where ((makedate >= (select max(makedate) from
			// lcissuepol where backobjtype in ('2','3') and ProposalNo =
			// '"+tLCPolSchema.getPolNo()+"' and makedate is not null)) or ((select
			// max(makedate) from lcissuepol where backobjtype in ('3','2') and
			// ProposalNo = '"+tLCPolSchema.getPolNo()+"') is null))"
			tsql = "select * from lcpol where  ProposalNo in ( select ProposalNo from LCIssuePol where 1 = 1 "
					+ " and backobjtype = '1'"
					+ " and ProposalNo = '"
					+ "?ProposalNo?"
					+ "'"
					+ " and makedate is not null"
					+ " and replyresult is null)"
					+ " and ProposalNo not in ( select ProposalNo from LCIssuePol where 1 = 1 "
					+ " and backobjtype in ('2','3')"
					+ " and ProposalNo = '"
					+ "?ProposalNo?"
					+ "'"
					+ " and makedate is not null"
					+ " and replyresult is null"
					+ " and needprint = 'Y')"
					+ " and ProposalNo not in ( select ProposalNo from LCIssuePol where 1 = 1 "
					+ " and backobjtype = '4'"
					+ " and ProposalNo = '"
					+ "?ProposalNo?"
					+ "'"
					+ " and makedate is not null" + " and replyresult is null)";
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql(tsql);
			sqlbv11.put("ProposalNo", tLCPolSchema.getPolNo());
			logger.debug("printchecksql2:" + tsql);
			tLCPolDB = new LCPolDB();
			t2LCPolSet = new LCPolSet();

			t2LCPolSet = tLCPolDB.executeQuery(sqlbv11);
			if (t2LCPolSet.size() > 0) {
				// 复核标记
				tLCPolSchema.setApproveFlag("1");
			}
			tLCPolSet.add(tLCPolSchema);

		}
		mLCPolSet.clear();
		mLCPolSet.add(tLCPolSet);
		if (tPrintFlag.equals("2")) {
			tPrintFlag = "0";
		} else {
			tPrintFlag = "1";
		}
		return tPrintFlag;
	}
	/**
	 * 准备主附险核保信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareContUW() {
		mLCCUWMasterSet.clear();
		mLCCUWSubSet.clear();

		LCCUWMasterSchema tLCCUWMasterSchema = new LCCUWMasterSchema();
		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(mLCContSchema.getContNo());
		LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();
		tLCCUWMasterSet = tLCCUWMasterDB.query();
		if (tLCCUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);
			CError.buildErr(this, "LCCUWMaster表取数失败!") ;
			return false;
		}

		int n = tLCCUWMasterSet.size();
		logger.debug("该投保单的核保主表当前记录条数:  " + n);
		if (tLCCUWMasterSet == null || tLCCUWMasterSet.size() != 1) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);
			CError.buildErr(this, "LCCUWMaster表取数据不唯一!") ;
			return false;
		}

		tLCCUWMasterSchema = tLCCUWMasterSet.get(1);

		// 为核保订正回退保存核保级别和核保人
		mBackUWGrade = tLCCUWMasterSchema.getUWGrade();
		mBackAppGrade = tLCCUWMasterSchema.getAppGrade();
		mOperator = tLCCUWMasterSchema.getOperator();

		// tLCCUWMasterSchema.setUWNo(tLCCUWMasterSchema.getUWNo()+1);核保主表中的UWNo表示该投保单经过几次人工核保(等价于经过几次自动核保次数),而不是人工核保结论(包括核保通知书,上报等)下过几次.所以将其注释.sxy-2003-09-19
		tLCCUWMasterSchema.setPassFlag(mUWFlag); // 通过标志
		tLCCUWMasterSchema.setUpReport(mUWUpReport); // 核保流向标志
		tLCCUWMasterSchema.setState(mUWFlag);
		tLCCUWMasterSchema.setUWIdea(mUWIdea);
		tLCCUWMasterSchema.setAutoUWFlag("2"); // 1 自动核保 2 人工核保
		tLCCUWMasterSchema.setPostponeDay(this.mValiDate);
		tLCCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		tLCCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		
		//ln 2008-10-17 add 撤单原因 延期原因 拒保原因
		String tDelayReason = (String) mTransferData.getValueByName("DelayReason");
		String tRefuReason = (String) mTransferData.getValueByName("RefuReason");
		String tWithDReason = (String) mTransferData.getValueByName("WithDReason");
		String tDelayReasonCode = (String) mTransferData.getValueByName("DelayReasonCode");//2009-2-7 --增加原因代码
		String tRefuReasonCode = (String) mTransferData.getValueByName("RefuReasonCode");//2009-2-7
		String tWithDReasonCode = (String) mTransferData.getValueByName("WithDReasonCode");//2009-2-7
		// 延期
		if (mUWFlag.equals("2")) {
			tLCCUWMasterSchema.setCommonReason(tDelayReason);	
			tLCCUWMasterSchema.setCommonReasonCode(tDelayReasonCode);
		}
		// 拒保
		if (mUWFlag.equals("1")) {
			tLCCUWMasterSchema.setCommonReason(tRefuReason);	
			tLCCUWMasterSchema.setCommonReasonCode(tRefuReasonCode);
		}
		// 撤单
		if (mUWFlag.equals("a")) {
			tLCCUWMasterSchema.setCommonReason(tWithDReason);
			tLCCUWMasterSchema.setCommonReasonCode(tWithDReasonCode);
		}

		// 核保订正
		if (mUWFlag.equals("z")) {
			tLCCUWMasterSchema.setAutoUWFlag("1");
			tLCCUWMasterSchema.setState("5");
			tLCCUWMasterSchema.setPassFlag("5");
			// 恢复核保级别和核保员
			tLCCUWMasterSchema.setUWGrade(mBackUWGrade);
			tLCCUWMasterSchema.setAppGrade(mBackAppGrade);
			tLCCUWMasterSchema.setOperator(mOperator);
			// 解锁
			LDSysTraceSchema tLDSysTraceSchema = new LDSysTraceSchema();
			tLDSysTraceSchema.setPolNo(mContNo);
			tLDSysTraceSchema.setCreatePos("人工核保");
			tLDSysTraceSchema.setPolState("1001");
			LDSysTraceSet inLDSysTraceSet = new LDSysTraceSet();
			inLDSysTraceSet.add(tLDSysTraceSchema);

			VData tVData = new VData();
			tVData.add(mGlobalInput);
			tVData.add(inLDSysTraceSet);

			LockTableBL LockTableBL1 = new LockTableBL();
			if (!LockTableBL1.submitData(tVData, "DELETE")) {
				logger.debug("解锁失败！");
			}
		}

		mLCCUWMasterSet.add(tLCCUWMasterSchema);

		// 核保轨迹表
		LCCUWSubSchema tLCCUWSubSchema = new LCCUWSubSchema();
		LCCUWSubDB tLCCUWSubDB = new LCCUWSubDB();
		tLCCUWSubDB.setContNo(mLCContSchema.getContNo());
		LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
		tLCCUWSubSet = tLCCUWSubDB.query();
		if (tLCCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWSubDB.mErrors);
			CError.buildErr(this, "LCCUWSub表取数失败!") ;
			return false;
		}

		int m = tLCCUWSubSet.size();
		logger.debug("subcount=" + m);
		if (m > 0) {
			m++; // 核保次数
			tLCCUWSubSchema = new LCCUWSubSchema();
			tLCCUWSubSchema.setUWNo(m); // 第几次核保
			tLCCUWSubSchema.setContNo(tLCCUWMasterSchema.getContNo());
			tLCCUWSubSchema.setGrpContNo(tLCCUWMasterSchema.getGrpContNo());
			tLCCUWSubSchema.setProposalContNo(tLCCUWMasterSchema
					.getProposalContNo());
			tLCCUWSubSchema.setOperator(mOperater);
			if (mUWFlag != null && mUWFlag.equals("z")) { // 核保订正
				tLCCUWSubSchema.setPassFlag(mUWFlag); // 核保意见
				tLCCUWSubSchema.setUWGrade(mUWPopedom); // 核保级别
				tLCCUWSubSchema.setAppGrade(mAppGrade); // 申请级别
				tLCCUWSubSchema.setAutoUWFlag("2");
				tLCCUWSubSchema.setState(mUWFlag);
				tLCCUWSubSchema.setOperator(mOperater); // 操作员

			}

			tLCCUWSubSchema.setManageCom(tLCCUWMasterSchema.getManageCom());
			tLCCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			tLCCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			tLCCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
			tLCCUWSubSchema.setAutoUWFlag("2");// 人工核保
			tLCCUWSubSchema.setPassFlag(mUWFlag);
			tLCCUWSubSchema.setUpReport(tLCCUWMasterSchema.getUpReport());//核保流向
			tLCCUWSubSchema.setUWIdea(mUWIdea);

		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWSubDB.mErrors);
			CError.buildErr(this, "LCCUWSub表取数失败!") ;
			return false;
		}

		mLCCUWSubSet.add(tLCCUWSubSchema);

		return true;
	}

	/**
	 * 返回处理后的结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回工作流中的Lwfieldmap所描述的值
	 * 
	 * @return TransferData
	 */
	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	/**
	 * 返回错误对象
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}
	
	/**
	 * 如果核保结论为撤单
	 * 查询该投保人下时候有其他单子处于6状态，如果有，将其置为3状态
	 * */
	private boolean DealOtherCont(){
		//modify by lzf 2013-04-07
//		String statusSql="update lwmission set missionprop18='3',defaultoperator='',missionprop14='' where lwmission.missionprop1 in "
//						+ " (select prtno from lcinsured where insuredno in" 
//						+ " (select insuredno from lccont where prtno='"+mContNo+"'))"
//						+ " and missionprop1<>'"+mContNo+"' and activityid='0000001100' and missionprop18='6'";
//		String UWIdeaSql = "update lccuwmaster set uwidea='6状态跳转' where contno in "
//						+ " ( select missionprop1 from lwmission where missionprop1 in"
//						+ " (select prtno from lcinsured where insuredno in" 
//						+ " (select insuredno from lccont where prtno='"+mContNo+"'))"
//						+ " and activityid='0000001100' and missionprop18='6' "
//						+ " ) and contno<>'"+mContNo+"' ";
//				//pubsubmit中调用执行UPDATE语句时是有序的   先放进map的语句会优先被执行
//		mmap.put(UWIdeaSql, "UPDATE");
//		mmap.put(statusSql, "UPDATE");
		return true;
	}
}
