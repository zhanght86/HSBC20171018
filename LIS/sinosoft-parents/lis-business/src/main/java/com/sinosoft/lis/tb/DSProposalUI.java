package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.tb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.pubfun.*;
import java.text.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:投保功能类（界面输入）
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author YT
 * @version 1.0
 */
public class DSProposalUI
{
private static Logger logger = Logger.getLogger(DSProposalUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public  CErrors mErrors=new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData =new VData();
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
        private String mOperate;

        //业务处理相关变量
        /** 接受前台传输数据的容器 */
        private TransferData mTransferData = new TransferData();
	/** 全局数据 */
	private GlobalInput mGlobalInput =new GlobalInput() ;
	private boolean mNeedDuty=false;

	public DSProposalUI() {
	}

	public static void main(String[] args){
		
	}

	/**
	传输数据的公共方法
	*/
	public boolean submitData(VData cInputData,String cOperate)
	{
		//将操作数据拷贝到本类中
		this.mOperate =cOperate;

		//得到外部传入的数据,将数据备份到本类中
		//if (!getInputData(cInputData))
		//	return false;

		//进行业务处理
		//if (!dealData())
		//	return false;

		//准备往后台的数据
		//if (!prepareOutputData())
		//	return false;

		DSProposalBL tDSProposalBL = new DSProposalBL();
		if (!tDSProposalBL.submitData( cInputData, cOperate)){
                    this.mErrors.copyAllErrors(tDSProposalBL.mErrors);
                    CError tError = new CError();
                    tError.moduleName = "ProposalUI";
                    tError.functionName = "submitData";
                    tError.errorMessage = "数据提交失败!";
                    this.mErrors .addOneError(tError) ;
                    return false;
                }
		mInputData=null;
		mResult = tDSProposalBL.getResult();

		return true;
	}




	public VData getResult()
	{
		return mResult;
	}

}
