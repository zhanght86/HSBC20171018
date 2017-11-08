



/**
 * <p>Title: PDRiskAppDefi</p>
 * <p>Description: 险种承保定义</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-12
 */

package com.sinosoft.productdef;

import com.sinosoft.lis.db.*;
// import com.sinosoft.lis.vdb.*;
// import com.sinosoft.lis.sys.*;
import com.sinosoft.lis.schema.*;
// import com.sinosoft.lis.vschema.*;

// import com.sinosoft.lis.vbl.*;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

import java.util.ArrayList;

public class PDRiskInsuAccBL implements BusinessService{
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private VData tResult = new VData();

	/** 往后面传输数据的容器 */
	// private VData mInputData = new VData();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	// private String mOperate;
	/** 业务处理相关变量 */
	private MMap map = new MMap();

	// private String mTableName = "";

	private String mRiskcode = "";

	private TransferData mTransferData = new TransferData();

	private ArrayList mList = new ArrayList();

	private ArrayList mList2 = new ArrayList();
	
	public PDRiskInsuAccBL() {
	}
	
	//-------- add by jucy
	//增加全局变量，判断pd_lmrisktoacc是否需要从页面取值
	String newRelRiskToAcc = "0";
	//-------- end

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!check()) {
			return false;
		}
		if (!getInputData(cInputData)) {
			return false;
		}
		
		//-------- update by jucy
		
		String checkInsuAccNo = (String)mList.get(1);
		ExeSQL exelm = new ExeSQL();
		ExeSQL exepd = new ExeSQL();
		String checkLMInsuAccNoExit = "Select insuaccno From  LMRiskInsuAcc Where insuaccno='"+checkInsuAccNo+"'";
		String checkPDInsuAccNoExit = "Select insuaccno From  PD_LMRiskInsuAcc Where insuaccno='"+checkInsuAccNo+"'";
		SSRS LmInsuAccNoExitSql = exelm.execSQL(checkLMInsuAccNoExit);
		SSRS PdInsuAccNoExitSql = exepd.execSQL(checkPDInsuAccNoExit);
		int LmInsuAccNo = LmInsuAccNoExitSql.getMaxRow();
		int PdInsuAccNo = PdInsuAccNoExitSql.getMaxRow();
		
		if("save".equals(cOperate)&&LmInsuAccNo==0&&PdInsuAccNo==0){
			//如果返回结果为空，则需要新增新增LMRiskInsuAcc
			//进行公共方法业务处理
			CommonBase commonBase = new CommonBase();
			if (!commonBase.prepareSubmitData(cInputData, cOperate)) {
				this.mErrors.addOneError("PDRiskInsuAccBL.submitData处理失败，"+ commonBase.mErrors.getFirstError());
				return false;
			} else {
				newRelRiskToAcc = "1";
				this.mResult.add(commonBase.getResult());
			}
		}else if("update".equals(cOperate)){
			//进行公共方法业务处理
			CommonBase commonBase = new CommonBase();
			if (!commonBase.prepareSubmitData(cInputData, cOperate)) {
				this.mErrors.addOneError("PDRiskInsuAccBL.submitData处理失败，"+ commonBase.mErrors.getFirstError());
				return false;
			} else {
				newRelRiskToAcc = "1";
				this.mResult.add(commonBase.getResult());
			}
		}else if("del".equals(cOperate)){
			ExeSQL pddel = new ExeSQL();
			ExeSQL lmdel = new ExeSQL();
			String checklmdel = "Select insuaccno From  LMRiskToAcc Where  insuaccno='"+checkInsuAccNo+"'";
			String checkpddel = "Select insuaccno From  PD_LMRiskToAcc Where insuaccno='"+checkInsuAccNo+"'";
			SSRS checklmdelSql = exelm.execSQL(checklmdel);
			SSRS checkpddelSql = exepd.execSQL(checkpddel);
			int lmNo = checklmdelSql.getMaxRow();
			int pdNo = checkpddelSql.getMaxRow();
			if(lmNo>1||pdNo>1){
				mErrors.addOneError("此账户已与其他产品关联，不允许删除。");
				return false;
			}
			
			LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();
			PD_LMRiskInsuAccDB tPD_LMRiskInsuAccDB = new PD_LMRiskInsuAccDB();
			tLMRiskInsuAccDB.setInsuAccNo(checkInsuAccNo);
			tPD_LMRiskInsuAccDB.setInsuAccNo(checkInsuAccNo);
			this.map.put(tLMRiskInsuAccDB.getSchema(), "DELETE");
			this.map.put(tPD_LMRiskInsuAccDB.getSchema(), "DELETE");
		}
		//-------- end 
		
		
		// 进行业务处理
		if (!dealData(cOperate)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "$tableName$BL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败$tableName$BL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备给后台的数据
		if (!prepareOutputData(cOperate)) {
			return false;
		}
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(tResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProdDefWorkFlowBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		RiskState.setState(mRiskcode, "产品条款定义->险种账户定义", "1");
		return true;
	}

	private boolean check() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mList = (ArrayList) mTransferData.getValueByName("list");
		mList2 = (ArrayList) mTransferData.getValueByName("list2");
		mRiskcode = (String) mTransferData.getValueByName("RiskCode");
		
		this.mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData(String cOperate) {
		System.out.println(" operator : " + cOperate);
		
		if ("save".equals(cOperate)) {
			// 下面处理险种账户关联表
			//-------- add by jucy
			PD_LMRiskToAccDB tPD_LMRiskToAccDB = new PD_LMRiskToAccDB();
			String checkInsuAccNo = (String)mList.get(1);
			tPD_LMRiskToAccDB.setRiskCode(mRiskcode);
			tPD_LMRiskToAccDB.setInsuAccNo(checkInsuAccNo);
			if (tPD_LMRiskToAccDB.getInfo()) {
				mErrors.addOneError("已存在产品与账户关联，新增失败。");
				return false;
			}
			PD_LMRiskToAccSchema tPD_LMRiskToAccSchema = new PD_LMRiskToAccSchema();
			if(newRelRiskToAcc=="1"){
				tPD_LMRiskToAccSchema.setRiskCode(this.mRiskcode);
				for (int i = 0; i < mList2.size(); i++) {
					if ("INSUACCNO".equalsIgnoreCase(String.valueOf(mList2.get(i)))) {
						tPD_LMRiskToAccSchema.setInsuAccNo(String.valueOf(mList.get(i)));
					} else if ("INSUACCNAME".equalsIgnoreCase(String.valueOf(mList2.get(i)))) {
						tPD_LMRiskToAccSchema.setInsuAccName(String.valueOf(mList.get(i)));
					}
				}
				tPD_LMRiskToAccSchema.setOperator(this.mGlobalInput.Operator);
				tPD_LMRiskToAccSchema.setMakeDate(PubFun.getCurrentDate());
				tPD_LMRiskToAccSchema.setMakeTime(PubFun.getCurrentTime());
				tPD_LMRiskToAccSchema.setModifyDate(PubFun.getCurrentDate());
				tPD_LMRiskToAccSchema.setModifyTime(PubFun.getCurrentTime());
				tPD_LMRiskToAccSchema.setRiskVer("2011");
			}else{
				PD_LMRiskInsuAccDB tPD_LMRiskInsuAccDB= new PD_LMRiskInsuAccDB();
				tPD_LMRiskInsuAccDB.setInsuAccNo(checkInsuAccNo);
				tPD_LMRiskInsuAccDB.getInfo();
				tPD_LMRiskToAccSchema.setRiskCode(this.mRiskcode);
				tPD_LMRiskToAccSchema.setInsuAccNo(tPD_LMRiskInsuAccDB.getInsuAccNo());
				tPD_LMRiskToAccSchema.setInsuAccName(tPD_LMRiskInsuAccDB.getInsuAccName());
				tPD_LMRiskToAccSchema.setOperator(this.mGlobalInput.Operator);
				tPD_LMRiskToAccSchema.setMakeDate(PubFun.getCurrentDate());
				tPD_LMRiskToAccSchema.setMakeTime(PubFun.getCurrentTime());
				tPD_LMRiskToAccSchema.setModifyDate(PubFun.getCurrentDate());
				tPD_LMRiskToAccSchema.setModifyTime(PubFun.getCurrentTime());
				tPD_LMRiskToAccSchema.setRiskVer("2011");
			}
			this.map.put(tPD_LMRiskToAccSchema, "INSERT");
			
		} else if ("update".equals(cOperate) || "del".equals(cOperate)||"delrel".equals(cOperate)) {
			PD_LMRiskToAccDB tPD_LMRiskToAccDB = new PD_LMRiskToAccDB();
			tPD_LMRiskToAccDB.setRiskCode(mRiskcode);
			String tInsuAccName = "";
			for (int i = 0; i < mList2.size(); i++) {
				if ("INSUACCNO".equalsIgnoreCase(String.valueOf(mList2.get(i)))) {
					tPD_LMRiskToAccDB
							.setInsuAccNo(String.valueOf(mList.get(i)));
				} else if ("INSUACCNAME".equalsIgnoreCase(String.valueOf(mList2
						.get(i)))) {
					tInsuAccName = String.valueOf(mList.get(i));
				}
			}
			
			
			if (!tPD_LMRiskToAccDB.getInfo()) {
				mErrors.addOneError("账户属性定义信息查询失败！");
				return false;
			}
			if ("update".equals(cOperate)) {
				tPD_LMRiskToAccDB.setInsuAccName(tInsuAccName);
				tPD_LMRiskToAccDB.setModifyDate(PubFun.getCurrentDate());
				tPD_LMRiskToAccDB.setModifyTime(PubFun.getCurrentTime());

				this.map.put(tPD_LMRiskToAccDB.getSchema(), "UPDATE");
			} else {
				this.map.put(tPD_LMRiskToAccDB.getSchema(), "DELETE");
			}
		}
		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData(String cOperate) {
		try {
			//-------- add by jucy
			VData tData = new VData();
			Object sResult = mResult.getObject(0);
			if ("save".equalsIgnoreCase(cOperate)) {
				if(sResult==null || sResult.equals("")){
					tResult.add(map);
				}else{
					tData = (VData) mResult.get(0);
					MMap tmap = (MMap) tData.getObjectByObjectName("MMap", 0);
					tmap.add(map);
					tResult.add(tmap);
				}
			}else{
				if(sResult==null || sResult.equals("")){
					tResult.add(map);
				}else{
					tData = (VData) mResult.get(0);
					MMap tmap = (MMap) tData.getObjectByObjectName("MMap", 0);
					map.add(tmap);
					tResult.add(map);
				}
			}
			//------- end
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return this.mResult;
	}
	
	public static void main(String[] args) {
	}
}

