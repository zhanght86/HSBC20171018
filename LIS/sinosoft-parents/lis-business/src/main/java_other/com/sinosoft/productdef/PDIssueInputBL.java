

/**
 * <p>Title: PDIssueInput</p>
 * <p>Description: 问题件录入</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-4-2
 */

package com.sinosoft.productdef;

import com.sinosoft.lis.db.PD_IssueDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.PD_IssueSchema;
import com.sinosoft.lis.vschema.PD_IssueSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PDIssueInputBL implements BusinessService{
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	
	public boolean mIsNeadTransfer = false;

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 接收传入的transferData */
	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private MMap mMap = new MMap();

	private PD_IssueSchema mPD_IssueSchema = new PD_IssueSchema();

	public PDIssueInputBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		this.mOperate = cOperate;
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		if (!check()) {
			return false;
		}
		// 准备所有要打印的数据
		try
		{
			if (!dealData()) {
				return false;
			}
		}
		catch(Exception ex)
		{
 			this.mErrors.addOneError("提交失败");
			return false;
		}
		
		mResult.clear();
		mResult.addElement(mMap);
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PDIssueInputBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		
		this.mResult.add(0,this.mIsNeadTransfer);
		return true;
	}

	private boolean check() {
		if ("delete".equals(this.mOperate)) {
			PD_IssueDB tPD_IssueDB = new PD_IssueDB();
			tPD_IssueDB.setSchema(this.mPD_IssueSchema);
			if (!tPD_IssueDB.getInfo()) {
				CError tError = new CError();
				tError.moduleName = "PDIssueInputBL";
				tError.functionName = "check";
				tError.errorMessage = "删除的问题件不存在，请检查!";
				this.mErrors.addOneError(tError);
				return false;
			}
		} else if ("deal".equals(this.mOperate)) {
			PD_IssueDB tPD_IssueDB = new PD_IssueDB();
			tPD_IssueDB.setRiskCode(mPD_IssueSchema.getRiskCode());
			// tPD_IssueDB.query();
			PD_IssueSet ttPD_IssueSet = tPD_IssueDB.query();
			if (ttPD_IssueSet.size() == 0) {
				CError tError = new CError();
				tError.moduleName = "PDIssueInputBL";
				tError.functionName = "check";
				tError.errorMessage = "不存在需要发送的问题件!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		try {
			System.out.println("here");
			mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mTransferData = (TransferData) cInputData.getObjectByObjectName(
					"TransferData", 0);

			if (mGlobalInput == null || mTransferData == null) {
				mErrors.addOneError(new CError("数据传输不完全！"));
				return false;
			}

			mPD_IssueSchema.setSerialNO((String) mTransferData
					.getValueByName("SerialNo"));
			mPD_IssueSchema.setRiskCode((String) mTransferData
					.getValueByName("RiskCode"));
			mPD_IssueSchema.setBackPost((String) mTransferData
					.getValueByName("BackPost"));
			mPD_IssueSchema.setOperPost((String) mTransferData
					.getValueByName("PostCode"));
			mPD_IssueSchema.setIssueType((String) mTransferData
					.getValueByName("IssueType"));
			mPD_IssueSchema.setIssueCont((String) mTransferData
					.getValueByName("IssueContDesc"));
			String tFilePath = (String) mTransferData
					.getValueByName("Filepath");
			if (tFilePath != null && !tFilePath.equals("")) {
				mPD_IssueSchema.setIssueFilePath(tFilePath
						+ (String) mTransferData.getValueByName("Filename"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, "接收前台数据失败!");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() throws Exception{
        if ("insert".equals(this.mOperate)) {
            String tSerialNo = PubFun1.CreateMaxNo("PDIssueID", 20);
            this.mPD_IssueSchema.setSerialNO(tSerialNo);
            mPD_IssueSchema.setOperPostMan(mGlobalInput.Operator);
            mPD_IssueSchema.setIssueState("0"); // 0表示新增
            mPD_IssueSchema.setOperator(mGlobalInput.Operator);
            mPD_IssueSchema.setMakeDate(PubFun.getCurrentDate());
            mPD_IssueSchema.setMakeTime(PubFun.getCurrentTime());
            mPD_IssueSchema.setModifyDate(PubFun.getCurrentDate());
            mPD_IssueSchema.setModifyTime(PubFun.getCurrentTime());
            this.mMap.put(mPD_IssueSchema, "INSERT");
        } else if ("delete".equals(this.mOperate)) {
            this.mMap.put(mPD_IssueSchema, "DELETE");
        } else if ("deal".equals(this.mOperate)) {
            String tUpdateSQL =
                    "update PD_Issue set IssueState = '1' where RiskCode = '" +
                    mPD_IssueSchema.getRiskCode()
                    + "' and IssueState = '0' and operator = '" +
                    mGlobalInput.Operator + "' and Serialno = '"+mPD_IssueSchema.getSerialNO()+"' ";
            this.mMap.put(tUpdateSQL, "UPDATE");
            
            if(!updateConfirmFlag())
            {
            	return false;
            }
            
        }
        return true;
    }
	
	// 如果返回岗位是契约、保全、理赔，更新lwmission中对应的标记位，让其能修改信息。
	private boolean updateConfirmFlag() throws Exception
	{
		String selectBackPostSQL = "select distinct BackPost from PD_Issue where RiskCode='" + this.mPD_IssueSchema.getRiskCode() + "' and operpost = '" + this.mPD_IssueSchema.getOperPost() + "' and issuestate = '0'";
		SSRS backPosts = new ExeSQL().execSQL(selectBackPostSQL);
				
		for(int i = 0; i < backPosts.MaxRow; i++)
		{
			String tBackPost = backPosts.GetText(i+1, 1);
			
			String updateFieldSQL = "select distinct codealias from ldcode where codetype = 'pdEnabledNode' and othersign = '" + tBackPost + "'";
			String updateField = new ExeSQL().getOneValue(updateFieldSQL);
			
			if(updateField.equals(""))
			{
				this.mIsNeadTransfer = true;
				return true;
			}
			
			String update = "update lwmission set " + updateField + " = null where " + updateField + " is not null and missionprop2 = '" + mPD_IssueSchema.getRiskCode() + "' and activityid = 'pd00000001'";
			this.mMap.put(update, "UPDATE");
		}
		
		String sql = "select 1 from dual where '" + mPD_IssueSchema.getOperPost() + "' in (select othersign from ldcode where codetype = 'pdEnabledNode')";
		if(!(new ExeSQL().getOneValue(sql)).equals("1"))
		{
			this.mIsNeadTransfer = true;
		}
		
		return true;
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
