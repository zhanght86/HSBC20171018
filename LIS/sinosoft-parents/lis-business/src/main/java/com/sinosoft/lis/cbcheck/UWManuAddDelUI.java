package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author guanwei
 * @version 1.0
 */
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
public class UWManuAddDelUI  implements BusinessService{
private static Logger logger = Logger.getLogger(UWManuAddDelUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public UWManuAddDelUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mOperate = cOperate;
		UWManuAddDelBL tUWManuAddDelBL = new UWManuAddDelBL();

		logger.debug("---UWManuAddChkUI BEGIN---");
		if (tUWManuAddDelBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tUWManuAddDelBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWManuAddChkUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			this.mErrors.copyAllErrors(tUWManuAddDelBL.mErrors);
		}
		return true;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return null;
	}
}
