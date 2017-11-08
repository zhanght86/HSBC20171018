package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.vschema.LPAppUWMasterMainSet;
import com.sinosoft.lis.vschema.LPAppUWSubMainSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPGrpEdorMainSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全申请-自动初次核保框架
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author pst
 * @version 1.0
 */
public class PGrpEdorAppPreAutoUWBL {
private static Logger logger = Logger.getLogger(PGrpEdorAppPreAutoUWBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private VData pInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private String mPreUWGrade = "A";
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
	private LPGrpEdorMainSet mLPGrpEdorMainSet = new LPGrpEdorMainSet();
	private LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();
	private LPAppUWMasterMainSet mLPAppUWMasterMainSet = new LPAppUWMasterMainSet();
	private LPAppUWSubMainSet mLPAppUWSubMainSet = new LPAppUWSubMainSet();
	private MMap mMap = new MMap();
	private MMap mUWFinaResult = new MMap();
	private String mPreUWState = "5";
	private String mGetNoticeNo = "";

	public PGrpEdorAppPreAutoUWBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate() {
		return this.mOperate;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		pInputData = new VData();
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 数据处理操作
		if (!dealData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();

		if (!tSubmit.submitData(mResult, "")) // 数据提交
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PGrpEdorAppAutoUWBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mResult.clear();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("PreUWFlag", mLPEdorAppSchema.getApproveFlag());
		tTransferData.setNameAndValue("PreUWGrade", mLPEdorAppSchema.getApproveGrade());
		// tTransferData.setNameAndValue("PayPrintParams", mPayPrintParams);
		mResult.add(tTransferData);

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 得到保单号
		mLPEdorAppSchema = (LPEdorAppSchema) cInputData.getObjectByObjectName("LPEdorAppSchema", 0);
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0));
		if (mLPEdorAppSchema == null || mGlobalInput == null) {
			mErrors.addOneError(new CError("传入数据不完全！"));
			return false;
		}
		if (mLPEdorAppSchema.getEdorAcceptNo() == null || mLPEdorAppSchema.getEdorAcceptNo().trim().equals("")) {
			mErrors.addOneError(new CError("申请主表数据不完全！"));
			return false;
		}
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mLPEdorAppSchema.getEdorAcceptNo());
		if (!tLPEdorAppDB.getInfo()) {
			mErrors.addOneError(new CError("保全申请主表中没有相关申请号的记录"));
			return false;
		}
		mLPEdorAppSchema = tLPEdorAppDB.getSchema();

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (mLPEdorAppSchema.getOtherNoType().equals("1")
				|| mLPEdorAppSchema.getOtherNoType().equals("3")) { // OtherNoType为1，3时表示是个人保全申请
			LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
			tLPEdorMainDB.setEdorAcceptNo(mLPEdorAppSchema.getEdorAcceptNo());
			mLPEdorMainSet = tLPEdorMainDB.query();
			if (mLPEdorMainSet.size() < 1) {
				mErrors.addOneError(new CError("保全受理号"
						+ mLPEdorAppSchema.getEdorAcceptNo() + "下无相应的批单记录！"));
				return false;
			}
			pInputData.clear();
			pInputData.addElement(mGlobalInput);
			pInputData.addElement(mLPEdorMainSet);
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("EdorAppLevel", "Y");
			pInputData.addElement(tTransferData);
			PEdorPreAutoUWBL tPEdorPreAutoUWBL = new PEdorPreAutoUWBL();
			if (!tPEdorPreAutoUWBL.submitData(pInputData, mOperate))// 项目级别权限控制
			{
				mErrors.copyAllErrors(tPEdorPreAutoUWBL.mErrors);
				mErrors.addOneError(new CError("保全受理号"
						+ mLPEdorAppSchema.getEdorAcceptNo() + "下的批单自动核保失败！"));
				// return false;
			}
			MMap map = new MMap();
			map = (MMap) tPEdorPreAutoUWBL.getResult().getObjectByObjectName(
					"MMap", 0);
			// TransferData tTransferData = new TransferData();
			tTransferData = (TransferData) tPEdorPreAutoUWBL.getResult()
					.getObjectByObjectName("TransferData", 0);
			mPreUWState = (String) tTransferData.getValueByName("PreUWFlag");
			mPreUWGrade = (String) tTransferData.getValueByName("PreUWGrade");

			if (map != null) {
				mMap.add(map);
			}
		}
		/**
		 * *****************Deleted by Pst
		 * 由于团险保全流程无自动初核流程，所以屏蔽掉，但接口仍然保留，重新写PGrPEdorPreAutoUWBL类即可**
		 */

		// else if (mLPEdorAppSchema.getOtherNoType().equals("2") ||
		// mLPEdorAppSchema.getOtherNoType().equals("4"))
		// { //OtherNoType为2,4时表示是团体保全申请
		// LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
		// tLPGrpEdorMainDB.setEdorAcceptNo(mLPEdorAppSchema.getEdorAcceptNo());
		// mLPGrpEdorMainSet = tLPGrpEdorMainDB.query();
		// if (mLPGrpEdorMainSet.size() < 1)
		// {
		// mErrors.addOneError(new CError("保全受理号" +
		// mLPEdorAppSchema.getEdorAcceptNo() +
		// "下无相应的批单记录！"));
		// return false;
		// }
		// for (int i = 0; i < mLPGrpEdorMainSet.size(); i++)
		// {
		// pInputData.clear();
		// pInputData.addElement(mGlobalInput);
		// pInputData.addElement(mLPGrpEdorMainSet.get(i + 1));
		// TransferData tTransferData = new TransferData();
		// tTransferData.setNameAndValue("EdorAppLevel", "Y");
		// pInputData.addElement(tTransferData);
		// PGrPEdorPreAutoUWBL tPGrPEdorPreAutoUWBL = new PGrPEdorPreAutoUWBL();
		// if (!tPGrPEdorPreAutoUWBL.submitData(pInputData, mOperate))
		// {
		// mErrors.copyAllErrors(tPGrPEdorPreAutoUWBL.mErrors);
		// mErrors.addOneError(new CError("保全受理号" +
		// mLPEdorAppSchema.getEdorAcceptNo() +
		// "下的批单自动核保失败！"));
		// continue;
		// }
		// MMap map = new MMap();
		// map = (MMap) tPGrPEdorPreAutoUWBL.getResult().
		// getObjectByObjectName("MMap", 0);
		// if (map != null)
		// {
		// mMap.add(map);
		// }
		//
		// tTransferData = (TransferData) tPGrPEdorPreAutoUWBL.getResult().
		// getObjectByObjectName("TransferData", 0);
		// mUWState = (String) tTransferData.getValueByName("UWFlag");
		// mUWGrade = (String) tTransferData.getValueByName("UWGrade");
		// }
		// }
		/** ***************************************************************************************** */
		if ("5".equals(mPreUWState)) {
			mLPEdorAppSchema.setApproveFlag("5");

		} else {
			mLPEdorAppSchema.setApproveFlag("9");
		}
		
		ExeSQL tExeSQL = new ExeSQL();
		String tGetMoneySQL = "select abs(nvl(sum(getmoney*decode(getflag,'1',1,-1)),0)) from ljsgetendorse where endorsementno in "
				+ "(select edorno from lpedoritem where edoracceptno='"+ mLPEdorAppSchema.getEdorAcceptNo()+"') and contno = '"
				+ mLPEdorMainSet.get(1).getContNo()+"' ";
		double tGetMoney = Double.parseDouble(tExeSQL.getOneValue(tGetMoneySQL));
		
		// add by jiaqiangli 2009-03-02 特殊的银代险换成函数取
		String tIYSQL = "select BQCSIYRISK('"+mLPEdorMainSet.get(1).getContNo()+"') from dual";
		String tIYProp = tExeSQL.getOneValue(tIYSQL);
		
		// 查询复合条件(具有审批权限)的最小权限 ------------start
		String tSql = "";
		if (mGlobalInput.ManageCom.length() == 8) {
			tSql = "select min(popedom) from ( "
					+ " select  min(b.edorpopedom) as popedom from LPEdorPopedom a,LDEdorUser b where "
					+ " approveflag=1 and riskprop = '"+tIYProp+"' "
					+ " and b.usertype='1' and b.userstate='0' "
					+ " and edorcode =(select distinct edortype from lpedoritem where "
					+ " edoracceptno='"
					+ mLPEdorAppSchema.getEdorAcceptNo()
					+ "') "
					// add by jiaqiangli 2009-03-02 还需要判断金额
					+ "and "+tGetMoney+"<=a.limitgetmoney "
					+ " and a.edorpopedom=b.edorpopedom and b.usercode in "
					+ " (select usercode from LDEdorUser where usercode in "
					+ " (select usercode from lduser where managecom='"
					+ mGlobalInput.ManageCom
					+ "'))"
					+ " union "
					+ " select  min(b.edorpopedom) as popedom from LPEdorPopedom a,LDEdorUser b where "
					+ " approveflag=1 and riskprop = '"+tIYProp+"' "
					+ " and b.usertype='1' and b.userstate='0' "
					+ " and edorcode =(select distinct edortype from lpedoritem where "
					+ " edoracceptno='"
					+ mLPEdorAppSchema.getEdorAcceptNo()
					+ "') "
					// add by jiaqiangli 2009-03-02 还需要判断金额
					+ "and "+tGetMoney+"<=a.limitgetmoney "
					+ " and a.edorpopedom=b.edorpopedom and b.usercode in "
					+ " (select usercode from LDEdorUser where usercode in "
					+ " (select usercode from lduser where managecom='"
					+ mGlobalInput.ManageCom.substring(0, 6)
					+ "'))"
					+ " union "
					+ " select  min(b.edorpopedom) as popedom from LPEdorPopedom a,LDEdorUser b where "
					+ " approveflag=1 and riskprop = '"+tIYProp+"' "
					+ " and b.usertype='1' and b.userstate='0' "
					+ " and edorcode =(select distinct edortype from lpedoritem where "
					+ " edoracceptno='"
					+ mLPEdorAppSchema.getEdorAcceptNo()
					+ "') "
					// add by jiaqiangli 2009-03-02 还需要判断金额
					+ "and "+tGetMoney+"<=a.limitgetmoney "
					+ " and a.edorpopedom=b.edorpopedom and b.usercode in "
					+ " (select usercode from LDEdorUser where usercode in "
					+ " (select usercode from lduser where managecom='"
					+ mGlobalInput.ManageCom.substring(0, 4)
					+ "'))"
					+ " union "
					+ " select  min(b.edorpopedom) as popedom from LPEdorPopedom a,LDEdorUser b "
					+ " where approveflag=1 and riskprop = '"+tIYProp+"' "
					+ " and b.usertype='1' and b.userstate='0' "
					+ " and edorcode =(select distinct edortype from lpedoritem where "
					+ " edoracceptno='"
					+ mLPEdorAppSchema.getEdorAcceptNo()
					+ "') "
					// add by jiaqiangli 2009-03-02 还需要判断金额
					+ "and "+tGetMoney+"<=a.limitgetmoney "
					+ " and a.edorpopedom=b.edorpopedom and b.usercode in "
					+ " (select usercode from LDEdorUser where usercode in "
					+ " (select usercode from lduser where managecom='86')) "
					+ "	)";
		} else if (mGlobalInput.ManageCom.length() == 6) {
			tSql = "select min(popedom) from ( "
					+ " select  min(b.edorpopedom) as popedom from LPEdorPopedom a,LDEdorUser b where "
					+ " approveflag=1 and riskprop = '"+tIYProp+"' "
					+ " and b.usertype='1' and b.userstate='0' "
					+ " and edorcode =(select distinct edortype from lpedoritem where "
					+ " edoracceptno='"
					+ mLPEdorAppSchema.getEdorAcceptNo()
					+ "') "
					// add by jiaqiangli 2009-03-02 还需要判断金额
					+ "and "+tGetMoney+"<=a.limitgetmoney "
					+ " and a.edorpopedom=b.edorpopedom and b.usercode in "
					+ " (select usercode from LDEdorUser where usercode in "
					+ " (select usercode from lduser where managecom='"
					+ mGlobalInput.ManageCom
					+ "'))"
					+ " union "
					+ " select  min(b.edorpopedom) as popedom from LPEdorPopedom a,LDEdorUser b where "
					+ " approveflag=1 and riskprop = '"+tIYProp+"' "
					+ " and b.usertype='1' and b.userstate='0' "
					+ " and edorcode =(select distinct edortype from lpedoritem where "
					+ " edoracceptno='"
					+ mLPEdorAppSchema.getEdorAcceptNo()
					+ "') "
					// add by jiaqiangli 2009-03-02 还需要判断金额
					+ "and "+tGetMoney+"<=a.limitgetmoney "
					+ " and a.edorpopedom=b.edorpopedom and b.usercode in "
					+ " (select usercode from LDEdorUser where usercode in "
					+ " (select usercode from lduser where managecom='"
					+ mGlobalInput.ManageCom.substring(0, 4)
					+ "'))"
					+ " union "
					+ " select  min(b.edorpopedom) as popedom from LPEdorPopedom a,LDEdorUser b "
					+ " where approveflag=1 and riskprop = '"+tIYProp+"' "
					+ " and b.usertype='1' and b.userstate='0' "
					+ " and edorcode =(select distinct edortype from lpedoritem where "
					+ " edoracceptno='"
					+ mLPEdorAppSchema.getEdorAcceptNo()
					+ "') "
					// add by jiaqiangli 2009-03-02 还需要判断金额
					+ "and "+tGetMoney+"<=a.limitgetmoney "
					+ " and a.edorpopedom=b.edorpopedom and b.usercode in "
					+ " (select usercode from LDEdorUser where usercode in "
					+ " (select usercode from lduser where managecom='86')) "
					+ "	)";
		} else if (mGlobalInput.ManageCom.length() == 4) {
			tSql = "select min(popedom) from ( "
					+ " select  min(b.edorpopedom) as popedom from LPEdorPopedom a,LDEdorUser b where "
					+ " approveflag=1 and riskprop = '"+tIYProp+"' "
					+ " and b.usertype='1' and b.userstate='0' "
					+ " and edorcode =(select distinct edortype from lpedoritem where "
					+ " edoracceptno='"
					+ mLPEdorAppSchema.getEdorAcceptNo()
					+ "') "
					// add by jiaqiangli 2009-03-02 还需要判断金额
					+ "and "+tGetMoney+"<=a.limitgetmoney "
					+ " and a.edorpopedom=b.edorpopedom and b.usercode in "
					+ " (select usercode from LDEdorUser where usercode in "
					+ " (select usercode from lduser where managecom='"
					+ mGlobalInput.ManageCom
					+ "'))"
					+ " union "
					+ " select  min(b.edorpopedom) as popedom from LPEdorPopedom a,LDEdorUser b "
					+ " where approveflag=1 and riskprop = '"+tIYProp+"' "
					+ " and b.usertype='1' and b.userstate='0' "
					+ " and edorcode =(select distinct edortype from lpedoritem where "
					+ " edoracceptno='" + mLPEdorAppSchema.getEdorAcceptNo()
					+ "') "
					// add by jiaqiangli 2009-03-02 还需要判断金额
					+ "and "+tGetMoney+"<=a.limitgetmoney "
					+ " and a.edorpopedom=b.edorpopedom and b.usercode in "
					+ " (select usercode from LDEdorUser where usercode in "
					+ " (select usercode from lduser where managecom='86')) "
					+ "	)";
		} else {
			tSql = " select  min(b.edorpopedom) as popedom from LPEdorPopedom a,LDEdorUser b "
					+ " where approveflag=1 and riskprop = '"+tIYProp+"' "
					+ " and b.usertype='1' and b.userstate='0' "
					+ " and edorcode =(select distinct edortype from lpedoritem where "
					+ " edoracceptno='"
					+ mLPEdorAppSchema.getEdorAcceptNo()
					+ "') "
					// add by jiaqiangli 2009-03-02 还需要判断金额
					+ "and "+tGetMoney+"<=a.limitgetmoney "
					+ " and a.edorpopedom=b.edorpopedom and b.usercode in "
					+ " (select usercode from LDEdorUser where usercode in "
					+ " (select usercode from lduser where managecom='86')) ";
		}

		String tPopeDom = tExeSQL.getOneValue(tSql);
		// add by jiaqiangli 2009-02-02 有些保全项是不需要审批的
		if (tPopeDom == null || tPopeDom.equals("")) {
			// modify by jiaqiangli 2009-03-02
			tPopeDom = mPreUWGrade;
// mErrors.addOneError(new CError("权限配置不正确，找不到符合条件的审批权限！"));
// return false;
		}
		
		//防止意外情况发生
		if (tPopeDom.compareTo(mPreUWGrade) < 0) {
			tPopeDom = mPreUWGrade;
		}
		
//		if (mLPEdorAppSchema.getOtherNoType().equals("1") || mLPEdorAppSchema.getOtherNoType().equals("3")) {
//			// add by jiaqiangli 需要单独特殊处理一下IC的ICCS02 这个是提交到F级的
//			// 如果此时tPopeDom为D的话最后的级别为F
//			String tEdorTypeSQL = "select edortype from lpedoritem where edoracceptno = '"
//					+ mLPEdorAppSchema.getEdorAcceptNo() + "' and contno = '" + mLPEdorMainSet.get(1).getContNo()
//					+ "' ";
//			String tEdorType = tExeSQL.getOneValue(tEdorTypeSQL);
//			if (tEdorType.equals("IC")) {
//				// 通过判断此表来看自动初次核保是否校验ICCS02不通过 mLPEdorUWErrorSet
//				String tICCS02UWErrorSQL = "select (select uwgrade from lmuw where relapoltype='CS' and uwtype='IC' and calcode=a.uwrulecode) from lpedoruwerror a where edoracceptno = '"
//						+ mLPEdorAppSchema.getEdorAcceptNo()
//						+ "' and contno = '"
//						+ mLPEdorMainSet.get(1).getContNo()
//						+ "' "
//						// ICCS02 没办法此处必须这样写
//						+ "and edortype='IC' and uwrulecode='BQCS01' ";
//				String tErrorGrade = tExeSQL.getOneValue(tICCS02UWErrorSQL);
//				// 主要uwgrade描述成大写 两者取大者
//				if (!tErrorGrade.equals("") && "F".compareTo(mPreUWGrade) > 0) {
//					tPopeDom = tErrorGrade;
//				}
//			}
//		}
		//add by jiaqiangli 2009-03-02 特殊处理
		
		// 查询复合条件的最小权限 ------------end
		mLPEdorAppSchema.setAppPreGrade(tPopeDom);// 当前审批权限
		mLPEdorAppSchema.setApproveGrade(mPreUWGrade);// 最后审批级别
		mLPEdorAppSchema.setApproveFlag(mPreUWState);
		mLPEdorAppSchema.setApproveOperator(mGlobalInput.Operator);
		mLPEdorAppSchema.setApproveDate(PubFun.getCurrentDate());
		mLPEdorAppSchema.setApproveTime(PubFun.getCurrentTime());
		mMap.put(mLPEdorAppSchema, "UPDATE");

		mResult.add(mMap);

		return true;
	}

	public static void main(String[] args) {
		VData tVData = new VData();
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";
		tVData.addElement(tGlobalInput);
		LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
		tLPEdorAppSchema.setEdorAcceptNo("86000000000093");
		tVData.addElement(tLPEdorAppSchema);

		PGrpEdorAppAutoUWBL tPGrpEdorAppAutoUWBL = new PGrpEdorAppAutoUWBL();
		tPGrpEdorAppAutoUWBL.submitData(tVData, "");

	}

}
