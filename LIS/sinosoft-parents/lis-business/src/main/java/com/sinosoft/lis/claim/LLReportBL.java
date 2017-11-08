/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLAccidentDB;
import com.sinosoft.lis.db.LLAccidentSubDB;
import com.sinosoft.lis.db.LLCaseRelaDB;
import com.sinosoft.lis.db.LLReportDB;
import com.sinosoft.lis.db.LLReportReasonDB;
import com.sinosoft.lis.db.LLReportRelaDB;
import com.sinosoft.lis.db.LLSubReportDB;
import com.sinosoft.lis.db.LLReportLogDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLAccidentSchema;
import com.sinosoft.lis.schema.LLAccidentSubSchema;
import com.sinosoft.lis.schema.LLCaseRelaSchema;
import com.sinosoft.lis.schema.LLReportReasonSchema;
import com.sinosoft.lis.schema.LLReportRelaSchema;
import com.sinosoft.lis.schema.LLReportSchema;
import com.sinosoft.lis.schema.LLSubReportSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LLReportLogSchema;
import com.sinosoft.lis.vschema.LLReportReasonSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 报案登记业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zl
 * @修改记录：2005/6/9 增加BLF层，删除BL层的数据提交，数据处理统一为"DELETE&INSERT"
 * 
 * @version 1.0
 */
public class LLReportBL {
private static Logger logger = Logger.getLogger(LLReportBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private String flag;
	private boolean mFlag = true;
	private MMap map = new MMap();


	private LLAccidentSchema mLLAccidentSchema = new LLAccidentSchema();
	private LLAccidentSubSchema mLLAccidentSubSchema = new LLAccidentSubSchema();
	private LLReportSchema mLLReportSchema = new LLReportSchema();
	private LLSubReportSchema mLLSubReportSchema = new LLSubReportSchema();
	private LLReportRelaSchema mLLReportRelaSchema = new LLReportRelaSchema();
	private LLCaseRelaSchema mLLCaseRelaSchema = new LLCaseRelaSchema();
	private LLReportReasonSchema mLLReportReasonSchema = new LLReportReasonSchema();
	private LLReportReasonSet mLLReportReasonSet = new LLReportReasonSet();
	private LLReportLogSchema mLLReportLogSchema = new LLReportLogSchema();

	private GlobalInput mG = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private boolean mIsReportExist;
	private boolean mIsAccExist;

	public LLReportBL() {
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
		logger.debug("----------LLReportBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData())
			return false;
		logger.debug("----------LLReportBL after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------LLReportBL after checkInputData----------");
		
		// 进行业务处理
		if (!dealData(cOperate)) {
			return false;
		}
		logger.debug("----------LLReportBL after dealData----------");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------LLReportBL after prepareOutputData----------");
		
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
		mLLAccidentSchema = (LLAccidentSchema) mInputData
				.getObjectByObjectName("LLAccidentSchema", 0);
		mLLReportSchema = (LLReportSchema) mInputData.getObjectByObjectName(
				"LLReportSchema", 0);
		mLLSubReportSchema = (LLSubReportSchema) mInputData
				.getObjectByObjectName("LLSubReportSchema", 0);
		mLLReportReasonSet = (LLReportReasonSet) mInputData
				.getObjectByObjectName("LLReportReasonSet", 0);

		if (mLLAccidentSchema == null && mLLReportSchema == null
				&& mLLSubReportSchema == null) {
			
			// @@错误处理
			CError.buildErr(this, "传入的信息为空!");
			return false;
		}
		flag = (String) mTransferData.getValueByName("flag");
		if (flag != null) {
			if (flag.equals("N")) {
				mFlag = false;
			}
		}
		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		logger.debug("----------begin checkInputData----------");
		ExeSQL exesql = new ExeSQL();
		SSRS ppSSRS = null;

		try {
			// 非空字段检验
			if (mLLAccidentSchema.getAccDate() == null)// 意外事故发生日期
			{
				// @@错误处理
				CError.buildErr(this, "传入的意外事故发生日期为空!");
				return false;
			}

			if (mLLSubReportSchema.getCustomerNo() == null)// 出险人编码
			{
				// @@错误处理
				CError.buildErr(this, "传入的出险人编码为空!");
				return false;
			}
			
			//修改信息不执行该校验
			if(!mOperate.equals("UPDATE")){
				
				 //死亡类的案件不能重复报案或立案
				 for(int i=1;i<mLLReportReasonSet.size();i++)
				 {
					 if(mLLReportReasonSet.get(i).getReasonCode().equals("102")||mLLReportReasonSet.get(i).getReasonCode().equals("202"))
					 {
						 String sql="select * from llsubreport,llreportreason"
						        +" where llsubreport.subrptno=llreportreason.rpno"
						        +" and llsubreport.customerno=llreportreason.customerno"
						        +" and llsubreport.customerno='"+"?customerno?"+"'"
						        +" and reasoncode in('102','202')";
						 logger.debug("查询出险人"+mLLSubReportSchema.getCustomerNo()+"是否多次进行死亡报案的sql="+sql);
						 SQLwithBindVariables sqlbv = new SQLwithBindVariables();
						 sqlbv.sql(sql);
						 sqlbv.put("customerno", mLLSubReportSchema.getCustomerNo());
						 ppSSRS=exesql.execSQL(sqlbv);
						 logger.debug("查询到的行数是"+ppSSRS.getMaxRow()+"行");
						 if(ppSSRS.MaxRow>0)
						 {
							 //@@错误处理
							 CError.buildErr(this,"出险人"+mLLSubReportSchema.getCustomerNo()+"已经进行过死亡报案或立案，请注意!");
							 //return false;
						 }
					 }
				 }
			}

			

		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "校验数据异常!");
			return false;
		}
		finally{
			
			//释放强引用
			ppSSRS=null;
			exesql=null;
		}
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(String cOperate) {
		
		logger.debug("----------BL start dealData----------");
		boolean tReturn = false;
		String deleteSql = "";

		// 流水号类型,详细见SysMaxNo
		String RptNo = "";
		String AccNo = "";
		
		//获得事件号
		if (mLLAccidentSchema.getAccNo() == null||mLLAccidentSchema.getAccNo().equals("")) {
			
			// 事件不存在
			mIsAccExist = false;
			AccNo = PubFun1.CreateMaxNo("ACCNO", 10); // 生成事件号流水号
			AccNo = "8" + AccNo;
			logger.debug("-----生成事件号= " + AccNo);
			
		} else {
			// 事件存在
			mIsAccExist = true;
			AccNo = mLLAccidentSchema.getAccNo();
			logger.debug("-----已有事件号= " + AccNo);
		}
		
		
		//获得报案号
		if (mLLReportSchema.getRptNo() == null||mLLReportSchema.getRptNo().equals("") ) {
			
			// 报案不存在
			mIsReportExist = false;
			String strlimit = PubFun.getNoLimit(mG.ManageCom);
			logger.debug("strlimit=" + strlimit);
			RptNo = PubFun1.CreateMaxNo("RPTNO", strlimit);
			logger.debug("-----生成报案号= " + RptNo);
		} else {
			// 报案存在
			mIsReportExist = true;
			RptNo = mLLReportSchema.getRptNo();
			logger.debug("-----已有报案号= " + RptNo);
		}

		// 立案时添加纪录或新增报案时
		if (cOperate.equals("INSERT") || cOperate.equals("9999999999")
				|| cOperate.equals("9899999999")||cOperate.equals("insertnoflow")) {
			logger.debug("----------INSERT dealData----------");
			// 报案不存在
			if (mIsReportExist == false) {
				logger.debug("----go to false deal;");

				// 事件表
				mLLAccidentSchema.setAccNo(AccNo); // 事件号
				mLLAccidentSchema.setOperator(mG.Operator);
				mLLAccidentSchema.setMngCom(mG.ManageCom);
				mLLAccidentSchema.setMakeDate(CurrentDate);
				mLLAccidentSchema.setMakeTime(CurrentTime);
				mLLAccidentSchema.setModifyDate(CurrentDate);
				mLLAccidentSchema.setModifyTime(CurrentTime);
				
				// 分事件表
				mLLAccidentSubSchema.setAccNo(AccNo); // 事件号
				mLLAccidentSubSchema.setCustomerNo(mLLSubReportSchema.getCustomerNo()); // 出险人编码
				
				// 报案表
				mLLReportSchema.setRptNo(RptNo); // 报案号=赔案号
				mLLReportSchema.setRgtObj("1");
				mLLReportSchema.setRgtObjNo(RptNo);// 备份报案号，在立案时可能需要覆盖报案表的报案号，取这个字段保留当时生成的报案号
				mLLReportSchema.setRptDate(CurrentDate); // 报案受理日期
				mLLReportSchema.setAvaiFlag("10"); // 案件有效标志10表示保存未确认
				mLLReportSchema.setOperator(mG.Operator);
				mLLReportSchema.setMngCom(mG.ManageCom);
				mLLReportSchema.setMakeDate(CurrentDate);
				mLLReportSchema.setMakeTime(CurrentTime);
				mLLReportSchema.setModifyDate(CurrentDate);
				mLLReportSchema.setModifyTime(CurrentTime);
				
				// 分案表
				mLLSubReportSchema.setSubRptNo(mLLReportSchema.getRptNo()); // 分案号=赔案号
				mLLSubReportSchema.setOperator(mG.Operator);
				mLLSubReportSchema.setMngCom(mG.ManageCom);
				mLLSubReportSchema.setMakeDate(CurrentDate);
				mLLSubReportSchema.setMakeTime(CurrentTime);
				mLLSubReportSchema.setModifyDate(CurrentDate);
				mLLSubReportSchema.setModifyTime(CurrentTime);
				
				// 报案分案关联表
				mLLReportRelaSchema.setRptNo(mLLReportSchema.getRptNo()); // 分案号=赔案号
				mLLReportRelaSchema.setSubRptNo(mLLReportSchema.getRptNo()); // 分案号=赔案号
				// 分案事件关联表
				mLLCaseRelaSchema.setCaseRelaNo(mLLAccidentSchema.getAccNo()); // 事件号
				mLLCaseRelaSchema.setCaseNo(mLLReportSchema.getRptNo()); // 分案号=赔案号
				mLLCaseRelaSchema.setSubRptNo(mLLReportSchema.getRptNo()); // 分案号=赔案号
				
				// 理赔类型表(多条添加)
				for (int i = 1; i <= mLLReportReasonSet.size(); i++) {
					mLLReportReasonSet.get(i).setRpNo(mLLReportSchema.getRptNo()); // 分案号=赔案号
					mLLReportReasonSet.get(i).setOperator(mG.Operator);
					mLLReportReasonSet.get(i).setMngCom(mG.ManageCom);
					mLLReportReasonSet.get(i).setMakeDate(CurrentDate);
					mLLReportReasonSet.get(i).setMakeTime(CurrentTime);
					mLLReportReasonSet.get(i).setModifyDate(CurrentDate);
					mLLReportReasonSet.get(i).setModifyTime(CurrentTime);
					// 如有伤残或高残,则生成伤残打印数据
					String tCode = mLLReportReasonSet.get(i).getReasonCode()
							.substring(1, 3);
					if (tCode.equals("01")||tCode.equals("03")) {
						// 生成打印数据--伤残鉴定通知书
						if (!insertLOPRTManager("PCT001")) {
							return false;
						}
					}
				}
				//新增个人报案轨迹表
				mLLReportLogSchema.setRptNo(RptNo);
				mLLReportLogSchema.setMngCom(mG.ManageCom);
				mLLReportLogSchema.setOperator(mG.Operator);
				mLLReportLogSchema.setMakeDate(CurrentDate);
				mLLReportLogSchema.setMakeTime(CurrentTime);
				mLLReportLogSchema.setModifyDate(CurrentDate);
				mLLReportLogSchema.setModifyTime(CurrentTime);
				mLLReportLogSchema.setRgtState("11"); //案件类型  11—普通案件 01—简易案件
				mLLReportLogSchema.setReportState("0"); //报案状态 0—报案  1—报案完成
				
				
				// 生成打印数据--单证通知书
				if (!insertLOPRTManager("PCT002")) {
					return false;
				}

				map.put(mLLAccidentSchema, "DELETE&INSERT");
				map.put(mLLAccidentSubSchema, "DELETE&INSERT");
				map.put(mLLReportSchema, "DELETE&INSERT");
				map.put(mLLSubReportSchema, "DELETE&INSERT");
				map.put(mLLReportRelaSchema, "DELETE&INSERT");
				map.put(mLLCaseRelaSchema, "DELETE&INSERT");
				map.put(mLLReportReasonSet, "DELETE&INSERT");
				map.put(mLLReportLogSchema, "DELETE&INSERT");
				
				
				tReturn = true;
			} else {
				//2008-11-28 zhangzheng 个险系统不支持多出险人,屏蔽这段程序 
//				logger.debug("-----go to true deal;");
//				// 报案已存在,增加出险人，只需更新分案表
//				if (mLLSubReportSchema.getSubRptNo() != null) {
//					String strSql = "select subrptno from llsubreport where "
//							+ "CustomerNo = '"
//							+ mLLSubReportSchema.getCustomerNo()
//							+ "' and SubRptNo = '"
//							+ mLLSubReportSchema.getSubRptNo() + "'";
//					ExeSQL exesql = new ExeSQL();
//					String tResult = exesql.getOneValue(strSql);
//					if (tResult.length() > 0) {
//						// @@错误处理
//						CError tError = new CError();
//						tError.moduleName = "LLReportBL";
//						tError.functionName = "dealData";
//						tError.errorMessage = "出险人已存在于此报案中!";
//						this.mErrors.addOneError(tError);
//						return false;
//					} 
//					else {
//						// 事件表(不更新只同步数据用)
//						LLAccidentDB tLLAccidentDB = new LLAccidentDB();
//						tLLAccidentDB.setAccNo(mLLAccidentSchema.getAccNo());
//						tLLAccidentDB.getInfo();
//						mLLAccidentSchema = tLLAccidentDB.getSchema();
//
//						// 分事件表
//						mLLAccidentSubSchema.setAccNo(mLLAccidentSchema
//								.getAccNo()); // 事件号
//						mLLAccidentSubSchema.setCustomerNo(mLLSubReportSchema
//								.getCustomerNo()); // 出险人编码
//
//						// 报案表(不更新只同步数据用)
//						LLReportDB tLLReportDB = new LLReportDB();
//						tLLReportDB.setRptNo(mLLReportSchema.getRptNo());
//						tLLReportDB.getInfo();
//						mLLReportSchema = tLLReportDB.getSchema();
//
//						// 分案表
//						mLLSubReportSchema.setOperator(mG.Operator);
//						mLLSubReportSchema.setMngCom(mG.ManageCom);
//						mLLSubReportSchema.setMakeDate(CurrentDate);
//						mLLSubReportSchema.setMakeTime(CurrentTime);
//						mLLSubReportSchema.setModifyDate(CurrentDate);
//						mLLSubReportSchema.setModifyTime(CurrentTime);
//
//						// 报案分案关联表(不更新只同步数据用)
//						LLReportRelaDB tLLReportRelaDB = new LLReportRelaDB();
//						tLLReportRelaDB.setRptNo(mLLReportSchema.getRptNo());
//						tLLReportRelaDB.setSubRptNo(mLLSubReportSchema
//								.getSubRptNo());
//						tLLReportRelaDB.getInfo();
//						mLLReportRelaSchema = tLLReportRelaDB.getSchema();
//
//						// 分案事件关联表(不更新只同步数据用)
//						LLCaseRelaDB tLLCaseRelaDB = new LLCaseRelaDB();
//						tLLCaseRelaDB.setCaseNo(mLLReportSchema.getRptNo());
//						tLLCaseRelaDB.setCaseRelaNo(mLLAccidentSchema
//								.getAccNo());
//						tLLCaseRelaDB.setSubRptNo(mLLSubReportSchema
//								.getSubRptNo());
//						tLLCaseRelaDB.getInfo();
//						mLLCaseRelaSchema = tLLCaseRelaDB.getSchema();
//
//						// 理赔类型表(多条添加)
//						for (int i = 1; i <= mLLReportReasonSet.size(); i++) {
//							mLLReportReasonSet.get(i).setOperator(mG.Operator);
//							mLLReportReasonSet.get(i).setMngCom(mG.ManageCom);
//							mLLReportReasonSet.get(i).setMakeDate(CurrentDate);
//							mLLReportReasonSet.get(i).setMakeTime(CurrentTime);
//							mLLReportReasonSet.get(i)
//									.setModifyDate(CurrentDate);
//							mLLReportReasonSet.get(i)
//									.setModifyTime(CurrentTime);
//							// //如有伤残,则生成伤残打印数据
//							// String tCode =
//							// mLLReportReasonSet.get(i).getReasonCode().substring(2,3);
//							// if (tCode.equals("01"))
//							// {
//							// //生成打印数据--伤残鉴定通知书
//							// if (!insertLOPRTManager("PCT001"))
//							// {
//							// return false;
//							// }
//							// }
//						}
//						map.put(mLLAccidentSchema, "DELETE&INSERT");
//						map.put(mLLAccidentSubSchema, "DELETE&INSERT");
//						map.put(mLLReportSchema, "DELETE&INSERT");
//						map.put(mLLSubReportSchema, "DELETE&INSERT");
//						map.put(mLLReportRelaSchema, "DELETE&INSERT");
//						map.put(mLLCaseRelaSchema, "DELETE&INSERT");
//						map.put(mLLReportReasonSet, "DELETE&INSERT");
//						tReturn = true;
//					}
//				}
			}
		}

		// 更新纪录
		if (cOperate.equals("UPDATE")) {
			logger.debug("----------UPDATE dealData----------");
			// 事件表(不更新只同步数据用)
			LLAccidentDB tLLAccidentDB = new LLAccidentDB();
			tLLAccidentDB.setAccNo(mLLAccidentSchema.getAccNo());
			tLLAccidentDB.getInfo();
			tLLAccidentDB.setAccDate(mLLAccidentSchema.getAccDate());
			mLLAccidentSchema = tLLAccidentDB.getSchema();
			mLLAccidentSchema.setAccPlace(mLLReportSchema.getAccidentSite());
			mLLAccidentSchema.setAccDesc(mLLSubReportSchema.getAccDesc());
			mLLAccidentSchema.setModifyDate(PubFun.getCurrentDate());
			mLLAccidentSchema.setModifyTime(PubFun.getCurrentTime());


			// 分事件表(不更新只同步数据用)
			LLAccidentSubDB tLLAccidentSubDB = new LLAccidentSubDB();
			tLLAccidentSubDB.setAccNo(mLLAccidentSchema.getAccNo());
			tLLAccidentSubDB.setCustomerNo(mLLSubReportSchema.getCustomerNo());
			tLLAccidentSubDB.getInfo();
			mLLAccidentSubSchema = tLLAccidentSubDB.getSchema();

			// 更新报案信息
			LLReportDB tLLReportDB = new LLReportDB();
			tLLReportDB.setRptNo(mLLReportSchema.getRptNo());
			tLLReportDB.getInfo();
			mLLReportSchema.setRgtObj(tLLReportDB.getRgtObj());
			mLLReportSchema.setRgtObjNo(tLLReportDB.getRgtObjNo());
			mLLReportSchema.setRgtClass(tLLReportDB.getRgtClass());
			mLLReportSchema.setRgtState(tLLReportDB.getRgtState()); // *案件类型*
			mLLReportSchema.setRptDate(tLLReportDB.getRptDate()); // 报案受理日期Modify by zhaorx 2006-12-14QC8085
			mLLReportSchema.setAvaiFlag("10"); // 案件有效标志10表示保存未确认
			mLLReportSchema.setRgtFlag("10"); // 立案标志为10表示未立案
			mLLReportSchema.setMngCom(tLLReportDB.getMngCom());
			mLLReportSchema.setOperator(tLLReportDB.getOperator());
			mLLReportSchema.setMakeDate(tLLReportDB.getMakeDate());
			mLLReportSchema.setMakeTime(tLLReportDB.getMakeTime());
			mLLReportSchema.setModifyDate(CurrentDate);
			mLLReportSchema.setModifyTime(CurrentTime);
			mLLReportSchema.setPeoples2(tLLReportDB.getPeoples2());
	

			// 更新分案信息表的字段()
			LLSubReportDB tLLSubReportDB = new LLSubReportDB();
			tLLSubReportDB.setSubRptNo(mLLSubReportSchema.getSubRptNo());
			tLLSubReportDB.setCustomerNo(mLLSubReportSchema.getCustomerNo());
			tLLSubReportDB.getInfo();
			mLLSubReportSchema.setMngCom(tLLSubReportDB.getMngCom());
			mLLSubReportSchema.setOperator(tLLSubReportDB.getOperator());
			mLLSubReportSchema.setMakeDate(tLLSubReportDB.getMakeDate());
			mLLSubReportSchema.setMakeTime(tLLSubReportDB.getMakeTime());
			mLLSubReportSchema.setModifyDate(CurrentDate);
			mLLSubReportSchema.setModifyTime(CurrentTime);

			// 报案事件信息
			LLReportRelaDB tLLReportRelaDB = new LLReportRelaDB();
			tLLReportRelaDB.setRptNo(mLLReportSchema.getRptNo());
			tLLReportRelaDB.setSubRptNo(mLLSubReportSchema.getSubRptNo());
			tLLReportRelaDB.getInfo();
			mLLReportRelaSchema = tLLReportRelaDB.getSchema();

			// 分案事件关联表
			LLCaseRelaDB tLLCaseRelaDB = new LLCaseRelaDB();
			tLLCaseRelaDB.setCaseNo(mLLReportSchema.getRptNo());
			tLLCaseRelaDB.setCaseRelaNo(mLLAccidentSchema.getAccNo());
			tLLCaseRelaDB.setSubRptNo(mLLSubReportSchema.getSubRptNo());
			tLLCaseRelaDB.getInfo();
			mLLCaseRelaSchema = tLLCaseRelaDB.getSchema();
			
			LLReportLogDB tLLReportLogDB = new LLReportLogDB();
			tLLReportLogDB.setRptNo(mLLReportSchema.getRptNo());
			tLLReportLogDB.getInfo();
			mLLReportLogSchema = tLLReportLogDB.getSchema();
			mLLReportLogSchema.setRptNo(tLLReportLogDB.getRptNo());
			mLLReportLogSchema.setMngCom(tLLReportLogDB.getMngCom());
			mLLReportLogSchema.setOperator(tLLReportLogDB.getOperator());
			mLLReportLogSchema.setMakeDate(tLLReportLogDB.getMakeDate());
			mLLReportLogSchema.setMakeTime(tLLReportLogDB.getMakeTime());
			mLLReportLogSchema.setModifyDate(CurrentDate);
			mLLReportLogSchema.setModifyTime(CurrentTime);
			mLLReportLogSchema.setRgtState(tLLReportLogDB.getRgtState()); //案件类型  11—普通案件 01—简易案件
			mLLReportLogSchema.setReportState(tLLReportLogDB.getReportState()); //报案状态 0—报案  1—报案完成

			// ------------------------------------------------------------------BEG
			// 理赔类型表,首先删除所有该分案下的所有理赔类型,然后再添加前台更改后数据
			// ------------------------------------------------------------------
			deleteSql = " delete from LLReportReason where " + " RpNo = '"
					+ "?RpNo?" + "'"
					+ " and CustomerNo = '"
					+ "?CustomerNo?" + "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(deleteSql);
			sqlbv1.put("RpNo", mLLSubReportSchema.getSubRptNo());
			sqlbv1.put("CustomerNo", mLLSubReportSchema.getCustomerNo());
			map.put(sqlbv1, "DELETE");

			for (int i = 1; i <= mLLReportReasonSet.size(); i++) {
				LLReportReasonDB tLLReportReasonDB = new LLReportReasonDB();
				tLLReportReasonDB.setRpNo(mLLReportReasonSet.get(i).getRpNo());
				tLLReportReasonDB.setCustomerNo(mLLReportReasonSet.get(i)
						.getCustomerNo());
				tLLReportReasonDB.setReasonCode(mLLReportReasonSet.get(i)
						.getReasonCode());
				tLLReportReasonDB.getInfo();
				if (tLLReportReasonDB.getMakeDate() == null) {
					mLLReportReasonSet.get(i).setOperator(mG.Operator);
					mLLReportReasonSet.get(i).setMngCom(mG.ManageCom);
					mLLReportReasonSet.get(i).setMakeDate(CurrentDate);
					mLLReportReasonSet.get(i).setMakeTime(CurrentTime);
					mLLReportReasonSet.get(i).setModifyDate(CurrentDate);
					mLLReportReasonSet.get(i).setModifyTime(CurrentTime);

					// 如有伤残,则生成伤残打印数据
					String tCode = mLLReportReasonSet.get(i).getReasonCode()
							.substring(1, 3);
					if (tCode.equals("01")) {
						// 先删除伤残打印数据
						String tsql = "delete from LOPRTManager where "
								+ " otherno = '" + mLLReportSchema.getRptNo()
								+ "'" + " and code = 'PCT001'";
						SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
						sqlbv2.sql(tsql);
						sqlbv2.put("otherno", mLLReportSchema.getRptNo());
						map.put(sqlbv2, "DELETE");
						// 生成打印数据--伤残鉴定通知书
						if (!insertLOPRTManager("PCT001")) {
							return false;
						}
					}
				} else {
					mLLReportReasonSet.get(i).setSchema(
							tLLReportReasonDB.getSchema());
				}
			}
			// ------------------------------------------------------------------End
			
			map.put(mLLAccidentSchema, "DELETE&INSERT");
			map.put(mLLAccidentSubSchema, "DELETE&INSERT");
			map.put(mLLReportSchema, "DELETE&INSERT");
			map.put(mLLSubReportSchema, "DELETE&INSERT");
			map.put(mLLReportRelaSchema, "DELETE&INSERT");
			map.put(mLLCaseRelaSchema, "DELETE&INSERT");
			map.put(mLLReportReasonSet, "DELETE&INSERT");
			map.put(mLLReportLogSchema,"DELETE&INSERT");
			tReturn = true;
		}



		// 更新mTransferData中的值
		if (!perpareMissionProp()) {
			// @@错误处理
			CError.buildErr(this, "为工作流准备数据失败!");
			tReturn = false;
		}
		logger.debug(tReturn);
		return tReturn;
	}

	/**
	 * 更新mTransferData中的值，为工作流准备数据
	 * 
	 * @return boolean
	 */
	private boolean perpareMissionProp() {
		if (mTransferData != null) {
			
			logger.debug("mTransferData.findIndexByName(RptNo)="+ mTransferData.findIndexByName("RptNo"));
			
			if (mTransferData.findIndexByName("RptNo") != -1) {
				mTransferData.removeByName("RptNo");
			}
			
			mTransferData.setNameAndValue("RptNo", mLLReportSchema.getRptNo());
			
			logger.debug("mTransferData.findIndexByName(RgtNo)="+ mTransferData.findIndexByName("RgtNo"));
			
			if (mTransferData.findIndexByName("RgtNo") != -1) {
				mTransferData.removeByName("RgtNo");
			}
			mTransferData.setNameAndValue("RgtNo", mLLReportSchema.getRptNo());
		}
		return true;
	}

	/**
	 * 添加打印数据 2005-8-16 14:49
	 * 
	 * @return boolean
	 */
	private boolean insertLOPRTManager(String tCode) {
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

		// 生成印刷流水号
		String strNolimit = PubFun.getNoLimit(mG.ManageCom);
		logger.debug("strlimit=" + strNolimit);
		String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNolimit);
		logger.debug("-----生成的LOPRTManager的印刷流水号= " + tPrtSeq);

		tLOPRTManagerSchema.setPrtSeq(tPrtSeq); // 印刷流水号
		tLOPRTManagerSchema.setOtherNo(mLLReportSchema.getRptNo()); // 对应其它号码
		tLOPRTManagerSchema.setOtherNoType("05"); // 其它号码类型--05赔案号
		tLOPRTManagerSchema.setCode(tCode); // 单据编码
		tLOPRTManagerSchema.setManageCom(mG.ManageCom); // 管理机构
		tLOPRTManagerSchema.setReqCom(mG.ComCode); // 发起机构
		tLOPRTManagerSchema.setReqOperator(mG.Operator); // 发起人
		tLOPRTManagerSchema.setPrtType("0"); // 打印方式

		// 打印状态：0都可用，1在线已打，2批打已打，3批打不打,4批打未开放
		tLOPRTManagerSchema.setStateFlag("3"); // 打印状态

		tLOPRTManagerSchema.setMakeDate(CurrentDate); // 入机日期(报案日期)
		tLOPRTManagerSchema.setMakeTime(CurrentTime); // 入机时间
		tLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
		tLOPRTManagerSchema.setStandbyFlag1(CurrentDate); // 批打检索日期
		tLOPRTManagerSchema.setStandbyFlag4(mLLSubReportSchema.getCustomerNo()); // 客户号码
		// tLOPRTManagerSchema.setStandbyFlag5(CurrentDate); //立案日期
		tLOPRTManagerSchema.setStandbyFlag6("10"); // 赔案状态
		map.put(tLOPRTManagerSchema, "INSERT");
		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mResult.add(map);
			mResult.add(mLLReportSchema);
			mResult.add(mTransferData);
			mResult.add(mG);
			mResult.add(mLLAccidentSchema);
			mResult.add(mLLAccidentSubSchema);
			mResult.add(mLLSubReportSchema);
			mResult.add(mLLReportRelaSchema);
			mResult.add(mLLCaseRelaSchema);
			mResult.add(mLLReportReasonSet);
	
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错");
			return false;
		}

		return true;
	}

}
