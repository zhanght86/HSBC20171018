

package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LoBonusRiskRemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LoBonusRiskRemSchema;
import com.sinosoft.lis.vschema.LoBonusRiskRemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;


/**
 * <p>Title: lis</p>
 * <p>Description: 个单分红计算 - 分红险种数据校验 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft</p>
 * @author pst
 * @version 1.0
 */
public class BonusRiskCheckPreBL {
private static Logger logger = Logger.getLogger(BonusRiskCheckPreBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private MMap map = new MMap();
	/** 数据操作字符串 */
	private String mOperate;
	/** 业务处理相关变量 */
	private LoBonusRiskRemSet mLoBonusRiskRemSet = new LoBonusRiskRemSet();
	
	private LoBonusRiskRemSet rLoBonusRiskRemSet = new LoBonusRiskRemSet();


	private String tCurMakeDate = PubFun.getCurrentDate();

	private String tCurMakeTime = PubFun.getCurrentTime();
	public BonusRiskCheckPreBL() {
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
		if (!getInputData(cInputData)) {
			return false;
		}
		if (!checkDate()) {
			return false;
		}
		// 进行业务处理
		if (!dealData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BonusRiskCheckPreBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败BonusRiskCheckPreBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		mInputData = null;
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL校验 很多校验已经在前台实现，这里只对日期进行校验 如果在处理过程中出错，则返回false,否则返回true
	 */

	private boolean checkDate() {

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		if (mLoBonusRiskRemSet != null && mLoBonusRiskRemSet.size()>0) {
			
			for(int k=1;k<=mLoBonusRiskRemSet.size();k++)
			{
				LoBonusRiskRemSchema tLoBonusRiskRemSchema = new LoBonusRiskRemSchema();				
				LoBonusRiskRemSet tLoBonusRiskRemSet = new LoBonusRiskRemSet();
				LoBonusRiskRemDB tLoBonusRiskRemDB = new LoBonusRiskRemDB();     
				tLoBonusRiskRemDB.setFisCalYear(mLoBonusRiskRemSet.get(k).getFisCalYear());
				tLoBonusRiskRemDB.setRiskCode(mLoBonusRiskRemSet.get(k).getRiskCode());					
				tLoBonusRiskRemSet = tLoBonusRiskRemDB.query();
				if (tLoBonusRiskRemSet.size() < 1) {
					CError tError = new CError();
					tError.moduleName = "BonusRiskCheckPreBL";
					tError.functionName = "submitData";
					tError.errorMessage = "没有相关数据";
					this.mErrors.addOneError(tError);
					return false;
				} 
				tLoBonusRiskRemSchema=tLoBonusRiskRemSet.get(1);
				tLoBonusRiskRemSchema.setState("1");
				tLoBonusRiskRemSchema.setOperator(mGlobalInput.Operator);
				tLoBonusRiskRemSchema.setModifyDate(tCurMakeDate);
				tLoBonusRiskRemSchema.setModifyTime(tCurMakeTime);
				rLoBonusRiskRemSet.add(tLoBonusRiskRemSet.get(1));
			}  
			//校验完毕
			String tUPDateSQL="update LOBonusMain set ComputeState='2',ModifyDate='"+"?tCurMakeDate?"+"',ModifyTime='"+"?tCurMakeTime?"+"'"
            +" where GroupID='1' and FiscalYear="+"?FiscalYear?";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tUPDateSQL);
			sqlbv.put("tCurMakeDate", tCurMakeDate);
			sqlbv.put("tCurMakeTime", tCurMakeTime);
			sqlbv.put("FiscalYear", rLoBonusRiskRemSet.get(1).getFisCalYear());
			map.put(sqlbv, "UPDATE");
			map.put(rLoBonusRiskRemSet, "UPDATE");
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mLoBonusRiskRemSet = ((LoBonusRiskRemSet) cInputData
				.getObjectByObjectName("LoBonusRiskRemSet", 0));
		mGlobalInput = ((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		return true;
	}

	private boolean prepareOutputData() {
		try {
			this.mResult.add(map);

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BonusRiskCheckPreBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			CError tError = new CError();
			tError.moduleName = "BonusRiskCheckPreBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "数据提交失败。";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}
}
