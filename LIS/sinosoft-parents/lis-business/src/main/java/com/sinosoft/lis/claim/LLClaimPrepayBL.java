/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 预付管理提交逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author: zl
 * @version 1.0
 */
public class LLClaimPrepayBL {
private static Logger logger = Logger.getLogger(LLClaimPrepayBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private GlobalInput mG = new GlobalInput();
	private MMap map = new MMap();
	private LLClaimDetailSchema mLLClaimDetailSchema = new LLClaimDetailSchema();
	private LLPrepayDetailSchema mLLPrepayDetailSchema = new LLPrepayDetailSchema();
	private LLPrepayClaimSchema mLLPrepayClaimSchema = new LLPrepayClaimSchema();
	private LLClaimSchema mLLClaimSchema = new LLClaimSchema();
	private LLBalanceSchema mLLBalanceSchema = new LLBalanceSchema();

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	// double tPrepaySum=0;//预付金额

	public LLClaimPrepayBL() {
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
		logger.debug("----------LLClaimPrepayBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData())
			return false;
		logger.debug("----------after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------after checkInputData----------");
		// 进行业务处理
		if (!dealData(cOperate)) {
			return false;
		}
		logger.debug("----------after dealData----------");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------after prepareOutputData----------");
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, cOperate)) {
			// @@错误处理
			CError.buildErr(this, "数据提交失败,"+tPubSubmit.mErrors.getLastError());
			return false;
		}

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
		// 获取页面信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);// 按类取值
		mLLClaimDetailSchema = (LLClaimDetailSchema) mInputData
				.getObjectByObjectName("LLClaimDetailSchema", 0);// 按类取值
		mLLPrepayClaimSchema = (LLPrepayClaimSchema) mInputData
				.getObjectByObjectName("LLPrepayClaimSchema", 0);// 按类取值
		mLLPrepayDetailSchema = (LLPrepayDetailSchema) mInputData
				.getObjectByObjectName("LLPrepayDetailSchema", 0);// 按类取值
		mLLClaimSchema = (LLClaimSchema) mInputData.getObjectByObjectName(
				"LLClaimSchema", 0);// 按类取值
		mLLBalanceSchema = (LLBalanceSchema) mInputData.getObjectByObjectName(
				"LLBalanceSchema", 0);// 按类取值

		if (mLLClaimDetailSchema == null) {
			// @@错误处理
			CError.buildErr(this, "接受的信息理算保项信息为空!");
			return false;
		}
		if (mLLPrepayClaimSchema == null) {
			// @@错误处理
			CError.buildErr(this, "接受的信息预付保项信息为空!");
			return false;
		}
		if (mLLPrepayDetailSchema == null) {
			// @@错误处理
			CError.buildErr(this, "接受的信息预付保项明细信息为空!");
			return false;
		}
		if (mLLClaimSchema == null) {
			// @@错误处理
			CError.buildErr(this, "接受的信息赔案信息为空!");
			return false;
		}
		if (mLLBalanceSchema == null) {
			// @@错误处理
			CError.buildErr(this, "接受的信息理赔结算信息为空!");
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
			
			// 非空字段检验
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
	private boolean dealData(String cOperate) {
		logger.debug("------start dealData-----");
		boolean tReturn = false;
		// 生成预付明细记录
		if (cOperate.equals("Prepay||Insert")) {
			// ====zl====2006-2-13
			// 10:32=====分配前先删除已经存在受益人信息======================BEG
			String tClmNo = mLLClaimDetailSchema.getClmNo();
			String tPolNo = mLLClaimDetailSchema.getPolNo();
			
			String tSql1 = "delete from LLBnfGather where ClmNo = '" + "?clmno?" + "'"
			+ " and BnfKind = 'B'" + " ";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(tSql1);
			sqlbv.put("clmno", tClmNo);
			map.put(sqlbv, "DELETE");
	
			String tSql2 = "delete from LLBnf where ClmNo = '" + "?clmno?" + "'"
					+ " and BnfKind = 'B'" + " and polno = '" + "?polno?" + "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSql2);
			sqlbv1.put("clmno", tClmNo);
			sqlbv1.put("polno", tPolNo);
			map.put(sqlbv1, "DELETE");

			String tSql3 = "delete from LJSGet where OtherNo = '" + "?clmno?"
					+ "'" + " and OtherNoType='5' " + " and getnoticeno in ("
					+ " select distinct getnoticeno from ljsgetclaim "
					+ " where otherno = '" + "?clmno?" + "'"
					+ " and othernotype = '5'" + " and polno = '" + "?polno?"
					+ "')";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSql3);
			sqlbv2.put("clmno", tClmNo);
			sqlbv2.put("polno", tPolNo);
			map.put(sqlbv2, "DELETE");

			String tSql4 = "delete from LJSGetClaim where OtherNo = '" + "?clmno?"
					+ "'" + " and OtherNoType='5'" + " and polno = '" + "?polno?"
					+ "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tSql4);
			sqlbv3.put("clmno", tClmNo);
			sqlbv3.put("polno", tPolNo);
			map.put(sqlbv3, "DELETE");
			// ====zl====2006-2-13
			// 10:32=====分配前先删除已经存在受益人信息======================END

			logger.debug("----- dealData---transact= " + cOperate);
			
			/**
			 * 2009-03-26 zhangzheng 
			 *  由于受益人分配导致一个人一个实付号,二次赔付时无法准确定位到上次的预付赔款冲销掉,所以决定取消财务类型中的预付赔款(YFPK)
			 *  查询出数据库在理算时确定的结算记录,取得确定的财务类型
			 */
			LLBalanceDB mLLBalanceDB = new LLBalanceDB();
			mLLBalanceDB.setClmNo(mLLClaimDetailSchema.getClmNo());
			mLLBalanceDB.setFeeOperationType("A");//A:赔款
			mLLBalanceDB.setPolNo(mLLClaimDetailSchema.getPolNo());
			mLLBalanceDB.setDutyCode(mLLClaimDetailSchema.getDutyCode());
			mLLBalanceDB.setGetDutyCode(mLLClaimDetailSchema.getGetDutyCode());
			mLLBalanceDB.setGetDutyKind(mLLClaimDetailSchema.getGetDutyKind());
			mLLBalanceDB.setBatNo("0");
			
			LLBalanceSet mLLBalanceSet=new LLBalanceSet();
			mLLBalanceSet=mLLBalanceDB.query();
			
			if (mLLBalanceSet.size() == 0||mLLBalanceSet.size()!=1) {
				// @@错误处理
				CError.buildErr(this, "查询不到案件的结算信息!");
				return false;
			}

			
			// 查询 赔付明细----LLClaimDetail，用于准备数据
			LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
			tLLClaimDetailDB.setClmNo(mLLClaimDetailSchema.getClmNo());
			tLLClaimDetailDB.setCaseNo(mLLClaimDetailSchema.getCaseNo());
			tLLClaimDetailDB.setPolNo(mLLClaimDetailSchema.getPolNo());
			tLLClaimDetailDB.setDutyCode(mLLClaimDetailSchema.getDutyCode());
			tLLClaimDetailDB.setGetDutyCode(mLLClaimDetailSchema.getGetDutyCode());
			tLLClaimDetailDB.setGetDutyKind(mLLClaimDetailSchema.getGetDutyKind());
			tLLClaimDetailDB.setCaseRelaNo(mLLClaimDetailSchema.getCaseRelaNo());
			tLLClaimDetailDB.setCustomerNo(mLLClaimDetailSchema.getCustomerNo());
			tLLClaimDetailDB.getInfo();

			// 生成预付明细记录
			// 赔案号、分案号、保单号、批次号、给付责任类型、给付责任编码<主键>、预付金额由前台传入
			mLLPrepayDetailSchema.setSerialNo(PubFun1.CreateMaxNo("SerialNo",
					10));// 序号(生成)<主键>
			mLLPrepayDetailSchema.setRgtNo(tLLClaimDetailDB.getRgtNo());
			mLLPrepayDetailSchema.setDeclineNo(tLLClaimDetailDB.getDeclineNo());
			mLLPrepayDetailSchema.setGrpContNo(tLLClaimDetailDB.getGrpContNo());
			mLLPrepayDetailSchema.setGrpPolNo(tLLClaimDetailDB.getGrpPolNo());
			mLLPrepayDetailSchema.setContNo(tLLClaimDetailDB.getContNo());
			mLLPrepayDetailSchema.setKindCode(tLLClaimDetailDB.getKindCode());
			mLLPrepayDetailSchema.setRiskCode(tLLClaimDetailDB.getRiskCode());
			mLLPrepayDetailSchema.setRiskVer(tLLClaimDetailDB.getRiskVer());
			mLLPrepayDetailSchema.setPolMngCom(tLLClaimDetailDB.getPolMngCom());
			mLLPrepayDetailSchema.setSaleChnl(tLLClaimDetailDB.getSaleChnl());
			mLLPrepayDetailSchema.setDutyCode(tLLClaimDetailDB.getDutyCode());
			mLLPrepayDetailSchema.setCaseRelaNo(tLLClaimDetailDB.getCaseRelaNo());//事件号
			mLLPrepayDetailSchema.setMngCom(mG.ManageCom);
			mLLPrepayDetailSchema.setOperator(mG.Operator);
			mLLPrepayDetailSchema.setMakeDate(CurrentDate);
			mLLPrepayDetailSchema.setMakeTime(CurrentTime);
			mLLPrepayDetailSchema.setModifyDate(CurrentDate);
			mLLPrepayDetailSchema.setModifyTime(CurrentTime);
			map.put(mLLPrepayDetailSchema, "INSERT");

			// 生成或修改汇总记录，不存在则生成，存在则修改
			// 判断汇总记录是否存在,条件(赔案号)
			LLPrepayClaimDB ttLLPrepayClaimDB = new LLPrepayClaimDB();
			ttLLPrepayClaimDB.setClmNo(mLLPrepayClaimSchema.getClmNo());
			LLPrepayClaimSet ttLLPrepayClaimSet=ttLLPrepayClaimDB.query();
			logger.debug("-----查询预付赔案记录表所得记录= "
					+ ttLLPrepayClaimDB.getCount());
			if (ttLLPrepayClaimSet.size() == 0)// 创建预付赔案汇总记录
			{
				logger.debug("-----创建预付赔案汇总记录------");
				// 赔案号、立案号、分案号、预付赔付金额由前台传入
				mLLPrepayClaimSchema.setMngCom(mG.ManageCom);
				mLLPrepayClaimSchema.setOperator(mG.Operator);
				mLLPrepayClaimSchema.setMakeDate(CurrentDate);
				mLLPrepayClaimSchema.setMakeTime(CurrentTime);
				mLLPrepayClaimSchema.setModifyDate(CurrentDate);
				mLLPrepayClaimSchema.setModifyTime(CurrentTime);
				map.put(mLLPrepayClaimSchema, "INSERT");
			} else // 修改预付赔案记录
			{
				logger.debug("-----修改预付赔案记录，对预付总金额进行累加------");
				double tPrepaySum = mLLPrepayClaimSchema.getPrepaySum();
				LLPrepayClaimDB tLLPrepayClaimDB = new LLPrepayClaimDB();
				tLLPrepayClaimDB.setClmNo(mLLPrepayClaimSchema.getClmNo());
				tLLPrepayClaimDB.getInfo();
				mLLPrepayClaimSchema.setSchema(ttLLPrepayClaimSet.get(1).getSchema());
				;
				mLLPrepayClaimSchema.setPrepaySum(ttLLPrepayClaimSet.get(1)
						.getPrepaySum()
						+ tPrepaySum);
				mLLPrepayClaimSchema.setModifyDate(CurrentDate);
				mLLPrepayClaimSchema.setModifyTime(CurrentTime);
				map.put(mLLPrepayClaimSchema, "UPDATE");

			}

			// 修改赔付明细表（LLClaimDetail）预付标志（PrepayFlag置为“1”）

			// 检测是否预付《每个保单只能进行一次预付》

			// 检查“预付金额”必须不超过“核赔赔付金额 X 预付比例”
			// 查询预付比例
			LDCodeDB tLDCodeDB = new LDCodeDB();
			LDCodeSet tLDCodeSet = new LDCodeSet();
			double tllprepayscale = 0;
			tLDCodeDB.setCodeType("llprepayscale");
			tLDCodeSet.set(tLDCodeDB.query());
			if (tLDCodeSet.size() == 1) {
				String llprepayscale = tLDCodeSet.get(1).getCode();
				tllprepayscale = Double.parseDouble(llprepayscale);
				logger.debug("-----预付比例------" + tllprepayscale);
			} else {
				CError.buildErr(this, "查询预付比例出错!");
				return false;
			}
			// 计算可以预付的金额

			double tRealPay = tllprepayscale * tLLClaimDetailDB.getRealPay()
					- tLLClaimDetailDB.getPrepaySum();
			logger.debug("------------可以预付的金额(总)====" + tllprepayscale
					* tLLClaimDetailDB.getRealPay());
			logger.debug("-------------已经预付金额()====="
					+ tLLClaimDetailDB.getPrepaySum());
			double ttPrepaySum = mLLClaimDetailSchema.getPrepaySum(); // 预付金额
			logger.debug("------------还可以预付的金额====" + tRealPay);
			logger.debug("-------------准备预付金额=====" + ttPrepaySum);
			if (tRealPay < (ttPrepaySum)) {
				CError.buildErr(this, "预付金额已经超过核赔赔付金额  " + tllprepayscale
						+ ",还可预付金额为" + tRealPay);
				return false;
			}
			mLLClaimDetailSchema.setSchema(tLLClaimDetailDB.getSchema());
			mLLClaimDetailSchema.setPrepaySum(tLLClaimDetailDB.getPrepaySum()
					+ ttPrepaySum);
			mLLClaimDetailSchema.setPrepayFlag("1");// 预付标志置为“1--已经预付”
			mLLClaimDetailSchema.setModifyDate(CurrentDate);
			mLLClaimDetailSchema.setModifyTime(CurrentTime);
			map.put(mLLClaimDetailSchema, "DELETE&INSERT");

			// 在理赔结算表（LLBalance）中生成理赔结算记录，如果存在则更新累加 “赔付金额”（pay---------存负数值）
			// 判断汇总记录是否存在,条件(赔案号、业务类型、子业务类型、财务类型、批次号、保单号码)
			LLBalanceDB ttLLBalanceDB = new LLBalanceDB();
			
			ttLLBalanceDB.setClmNo(mLLBalanceSchema.getClmNo());
			ttLLBalanceDB.setFeeOperationType(mLLBalanceSchema.getFeeOperationType());
			
			//ttLLBalanceDB.setSubFeeOperationType(mLLBalanceSchema.getSubFeeOperationType());		
			//ttLLBalanceDB.setFeeFinaType(mLLBalanceSchema.getFeeFinaType());
			
			//ttLLBalanceDB.setBatNo(mLLBalanceSchema.getBatNo());
			ttLLBalanceDB.setPolNo(mLLBalanceSchema.getPolNo());
			//ttLLBalanceDB.getInfo();			
			LLBalanceSet tLLBalanceSet = ttLLBalanceDB.query();
			
			if (tLLBalanceSet.size() == 0)// 创建理赔结算记录
			{
				logger.debug("-----创建理赔结算记录------");
				// 赔案号、业务类型、子业务类型、财务类型、批次号、保单号码、赔付金额由前台传入
				// “赔付金额”存储负值 
				mLLBalanceSchema.setOtherNo("0");
				mLLBalanceSchema.setOtherNoType("0");
				mLLBalanceSchema.setGetDutyCode(tLLClaimDetailDB.getDutyCode());
				mLLBalanceSchema.setGetDutyKind(tLLClaimDetailDB.getGetDutyKind());
				mLLBalanceSchema.setDutyCode(tLLClaimDetailDB.getDutyCode());
				mLLBalanceSchema.setGrpContNo(tLLClaimDetailDB.getGrpContNo());
				mLLBalanceSchema.setGrpPolNo(tLLClaimDetailDB.getGrpPolNo());
				mLLBalanceSchema.setContNo(tLLClaimDetailDB.getContNo());
				mLLBalanceSchema.setKindCode(tLLClaimDetailDB.getKindCode());
				mLLBalanceSchema.setRiskCode(tLLClaimDetailDB.getRiskCode());
				mLLBalanceSchema.setRiskVersion(tLLClaimDetailDB.getRiskVer());
				
				mLLBalanceSchema.setSubFeeOperationType(mLLBalanceSet.get(1).getSubFeeOperationType());//沿用结算时产生的子业务类型
				mLLBalanceSchema.setFeeFinaType(mLLBalanceSet.get(1).getFeeFinaType());//沿用结算时产生的财务类型
				mLLBalanceSchema.setSaleChnl(mLLBalanceSet.get(1).getSaleChnl());//销售渠道
				mLLBalanceSchema.setAgentCode(mLLBalanceSet.get(1).getAgentCode());//代理人编码
				mLLBalanceSchema.setAgentGroup(mLLBalanceSet.get(1).getAgentGroup());//代理人组别
				mLLBalanceSchema.setGetDate(mLLBalanceSet.get(1).getGetDate());//GetDate
				
				mLLBalanceSchema.setState("0");
				mLLBalanceSchema.setDealFlag("0");
				
				double tPay = -1 * mLLBalanceSchema.getPay();
				mLLBalanceSchema.setPay(tPay);
				
				mLLBalanceSchema.setOperator(mG.Operator);
				mLLBalanceSchema.setManageCom(mG.ManageCom);
				mLLBalanceSchema.setMakeDate(CurrentDate);
				mLLBalanceSchema.setMakeTime(CurrentTime);
				mLLBalanceSchema.setModifyDate(CurrentDate);
				mLLBalanceSchema.setModifyTime(CurrentTime);
				map.put(mLLBalanceSchema, "INSERT");
			} 
			else // 修改记录
			{
				logger.debug("-----修改理赔结算记录，赔付金额进行累加------");
				double tPay = -1 * mLLBalanceSchema.getPay();

				// ====zl====2006-2-13
				// 10:32=====由于llbalance变更主键,修改查询方式=================BEG
				//LLBalanceDB tLLBalanceDB = new LLBalanceDB();
				//tLLBalanceDB.setClmNo(mLLBalanceSchema.getClmNo());
				//tLLBalanceDB.setFeeOperationType(mLLBalanceSchema.getFeeOperationType());
				//tLLBalanceDB.setSubFeeOperationType(mLLBalanceSchema.getSubFeeOperationType());
				//tLLBalanceDB.setFeeFinaType(mLLBalanceSchema.getFeeFinaType());
				//tLLBalanceDB.setBatNo(mLLBalanceSchema.getBatNo());
				//tLLBalanceDB.setPolNo(mLLBalanceSchema.getPolNo());
				//LLBalanceSet tLLBalanceSet = tLLBalanceDB.query();
				if (tLLBalanceSet.size() != 1) {
					CError.buildErr(this, "查询理赔结算表失败!");
					return false;
				}

				// 使用sql提交,前提是预付对于每个险种只有一条记录
				String tSQL = "update llbalance a " + " set a.pay = a.pay + "
						+ "?pay?" + " " + " where ClmNo = '"
						+ "?ClmNo?" + "'"
						+ " and FeeOperationType = '"
						+ "?FeeOperationType?" + "'"
						//+ " and SubFeeOperationType = '"
						//+ mLLBalanceSchema.getSubFeeOperationType() + "'"
						//+ " and FeeFinaType = '"
						//+ mLLBalanceSchema.getFeeFinaType() + "'"
						//+ " and BatNo = '" + mLLBalanceSchema.getBatNo() + "'"
						+ " and PolNo = '" + "?PolNo?" + "'";
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql(tSQL);
				sqlbv4.put("pay", tPay);
				sqlbv4.put("ClmNo", mLLBalanceSchema.getClmNo());
				sqlbv4.put("FeeOperationType", mLLBalanceSchema.getFeeOperationType());
				sqlbv4.put("PolNo", mLLBalanceSchema.getPolNo());
				map.put(sqlbv4, "UPDATE");
				// ====zl====2006-2-13
				// 10:32=====由于llbalance变更主键,修改查询方式=================END

			}

			// 修改赔案表中的“先给付金额（BeforePay）”=预付汇总表的“预付赔付金额（PrepaySum）”
			LLClaimDB tLLClaimDB = new LLClaimDB();
			tLLClaimDB.setClmNo(mLLClaimSchema.getClmNo());
			tLLClaimDB.getInfo();
			// LLPrepayClaimDB ttLLPrepayClaimDB = new LLPrepayClaimDB();
			ttLLPrepayClaimDB.setClmNo(mLLClaimSchema.getClmNo());
			ttLLPrepayClaimDB.getInfo();
			mLLClaimSchema = tLLClaimDB.getSchema();
			mLLClaimSchema.setBeforePay(tLLClaimDetailDB.getPrepaySum()
					+ ttPrepaySum);// 先给付金额
			mLLClaimSchema.setModifyDate(CurrentDate);
			mLLClaimSchema.setModifyTime(CurrentTime);
			map.put(mLLClaimSchema, "UPDATE");

			tReturn = true;
		} else {
			tReturn = false;
		}

		return tReturn;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
			return true;
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错!");
			return false;
		}
	}

}
