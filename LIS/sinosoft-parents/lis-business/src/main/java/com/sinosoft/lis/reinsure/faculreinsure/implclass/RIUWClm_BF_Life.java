

package com.sinosoft.lis.reinsure.faculreinsure.implclass;

import com.sinosoft.lis.db.LLClaimDetailDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.reinsure.faculreinsure.RIUWClm;
import com.sinosoft.lis.vschema.LLClaimDetailSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class RIUWClm_BF_Life implements RIUWClm{
	private VData mInputData = new VData();
    /** 前台传入的公共变量 */
	private TransferData tTransferData;
    private GlobalInput globalInput = new GlobalInput();
	private String mCaseNo;
	private String mResult="0";

	public boolean submitData(VData cInputData, String cOperate) {
		// TODO Auto-generated method stub
		if(!getInputData(cInputData)){
			   return false;
			}
			if(!dealData()){
			   return false;
			}
		return true;
	}

	public boolean getInputData(VData cInputData){
		this.mInputData = (VData) cInputData.clone();
		if (mInputData == null){
			buildError("getInputData", "前台传输全局公共数据失败");
            return false;
		}
        this.tTransferData = (TransferData) mInputData.getObjectByObjectName( "TransferData", 0);
        this.mCaseNo = (String)tTransferData.getValueByName("CaseNo");
		return true;
	}
	
	public boolean dealData(){
		//属于女性特定疾病责任
		StringBuffer sql = new StringBuffer(" Select * from llclaimdetail a where  a.realpay > 60000 and a.getdutycode in ('DDD041','DDD042','DDD043','DDD044','DDD241','DDD242','DDD243','DDD244') and a.clmno = '"+mCaseNo+"' ");
		sql.append(" union all ");
		//属于一般重大疾病责任
		sql.append(" select * from llclaimdetail b where  b.realpay > 300000 and b.getdutycode in ('DDD040','DDD240') and b.clmno = '"+mCaseNo+"' ");
		
		System.out.println(sql.toString());
		
		LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();	
		LLClaimDetailSet tLLClaimDetailSet = tLLClaimDetailDB.executeQuery(sql.toString());
		
		if(tLLClaimDetailSet!=null&&tLLClaimDetailSet.size()> 0){
			mResult = "1";			
		}
		return true;
	}
	
	public String getResult(){
		return this.mResult;
	}
	public CErrors getCErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}
    private void buildError(String szFunc, String szErrMsg) {
        CError cError = new CError();
        cError.moduleName = "ReComManageBL";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }
    
    public static void main(String[] args){
    	VData mVData = new VData();
        GlobalInput globalInput = new GlobalInput();
        globalInput.ManageCom = "8611";
        globalInput.Operator = "001";
        TransferData tF = new TransferData();
        //90000000056,90000000058
        tF.setNameAndValue("CaseNo","90000000056");
        mVData.add(globalInput);
        mVData.add(tF);
    	RIUWClm_BF_Life tRIUWClm_BF_Life = new RIUWClm_BF_Life();
    	tRIUWClm_BF_Life.submitData(mVData, "");
    	System.out.println("==============Result:"+tRIUWClm_BF_Life.getResult());
    }
	
}
