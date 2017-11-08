package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保单查询业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 */
public class TempFeeTypeQueryBL {
private static Logger logger = Logger.getLogger(TempFeeTypeQueryBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	/** 暂交费表 */
	private LCPolSet mLCPolSet ;
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCGrpPolSet mLCGrpPolSet ;
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();

	public TempFeeTypeQueryBL() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
			return false;
		logger.debug("---getInputData---");

		// 进行业务处理
		if (!queryType())
			return false;
		logger.debug("---queryType---");
		if (!prepareOutputData())
			return false;

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 检验查询条件
		mLCContSchema = (LCContSchema) cInputData.getObjectByObjectName(
				"LCContSchema", 0);

		mLCGrpContSchema = (LCGrpContSchema) cInputData.getObjectByObjectName(
				"LCGrpContSchema", 0);

		// if(mLCContSchema == null)
		// {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "TempFeeTypeQueryBL";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "请输入查询条件!";
		// this.mErrors.addOneError(tError) ;
		// return false;
		// }
		return true;
	}

	/**
	 * 查询暂交费表信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean queryType() {
		// 保单信息
		String PolNo = "";
		String sqlStr = "";
		if (mLCContSchema != null) {
			// 查询个人保单表
			PolNo = mLCContSchema.getContNo();
			mLCPolSet =new LCPolSet();
            LCPolDB tLCPolDB = new LCPolDB();

			sqlStr = "select * from lcpol where ContNo='?PolNo?' and AppFlag='1' and payintv='-1' ";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sqlStr);
			sqlbv.put("PolNo", PolNo);
			//MS目前财务收费都采用8位机构登陆，所以排除对管理机构的这种控制
			logger.debug("个人sql:" + sqlStr);
			mLCPolSet = tLCPolDB.executeQuery(sqlbv);
			if (tLCPolDB.mErrors.needDealError() == true) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCPolDB.mErrors);
				CError.buildErr(this, "个人保单表查询失败!");;
				mLCPolSet.clear();
				return false;
			}
			if (mLCPolSet.size() == 0) {
				// @@错误处理
				CError.buildErr(this, "未找到相关数据!");
				mLCPolSet.clear();
				return false;
			}
			return true;
		} else {
			// 查询集体保单表
			String tGrpContNo = mLCGrpContSchema.getGrpContNo();
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			mLCGrpPolSet = new LCGrpPolSet();
			sqlStr = "select * from lcgrppol where GrpContNo='?tGrpContNo?' and AppFlag='1'and payintv='-1' ";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(sqlStr);
			sqlbv1.put("tGrpContNo", tGrpContNo);
			//MS目前财务收费都采用8位机构登陆，所以排除对管理机构的这种控制;
			logger.debug("集体sql:" + sqlStr);
			mLCGrpPolSet = tLCGrpPolDB.executeQuery(sqlbv1);
			if (tLCGrpPolDB.mErrors.needDealError() == true) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
				CError.buildErr(this, "集体保单表查询失败!");
				mLCGrpPolSet.clear();
				return false;
			}
			if (mLCGrpPolSet.size() == 0) {
				// @@错误处理
				CError.buildErr(this, "未找到相关数据!");
				mLCGrpPolSet.clear();
				return false;
			}
			return true;
		}
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		try {
			if (mLCPolSet == null)
				mResult.add(mLCGrpPolSet);
			else
				mResult.add(mLCPolSet);
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错。");
			return false;
		}
		return true;
	}
}
