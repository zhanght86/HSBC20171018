
package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;


import com.sinosoft.lis.bq.BQBankTransferFailureNotice;
import com.sinosoft.lis.claim.LLClaimBankTrancferFailureNotice;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: 保全收付费银行转帐失败通知书TASK类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009-02-27</p>
 * <p>Company: SinoSoft</p>
 * @author lijiaqiang
 * @version 1.0
 */

public class LPBankFailureNoticeTask extends TaskThread {
private static Logger logger = Logger.getLogger(LPBankFailureNoticeTask.class);
	public CErrors mErrors = new CErrors();

	public LPBankFailureNoticeTask() {
	}

	public boolean dealMain() {		
		LLClaimBankTrancferFailureNotice tLLClaimBankTrancferFailureNotice = new LLClaimBankTrancferFailureNotice();
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";
		tGlobalInput.ComCode = "86";
		 //日志监控,初始化           	
		tGlobalInput.LogID[0]="TASK"+(String)mParameters.get("TaskCode");          
		tGlobalInput.LogID[1]=(String)mParameters.get("SerialNo"); 
		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tLLClaimBankTrancferFailureNotice.submitData(tVData, "");
	
		return true;
	}

	public static void main(String[] args) {
	}
}
