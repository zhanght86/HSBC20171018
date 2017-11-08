package com.sinosoft.lis.xbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCIndUWMasterSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */

public class RnewInsuredUWUI {
private static Logger logger = Logger.getLogger(RnewInsuredUWUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public RnewInsuredUWUI() {
	}

	/**
	 * 数据提交方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return: boolean
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mOperate = cOperate;

		RnewInsuredUWBL tRnewInsuredUWBLL = new RnewInsuredUWBL();

		logger.debug("----UI BEGIN---");
		if (tRnewInsuredUWBLL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tRnewInsuredUWBLL.mErrors);
			return false;
		}
		logger.debug("----UI END---");
		return true;
	}

	public static void main(String[] args) {
		CErrors tError = null;
		String FlagStr = "Fail";
		String Content = "";

		boolean flag = true;
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86110000";
		tG.ComCode = "86110000";

		// 接收信息
		String tContNo = "130110000013410";
		String tInsuredNo = "0000489550";

		LCIndUWMasterSchema tLCIndUWMasterSchema = new LCIndUWMasterSchema();
		tLCIndUWMasterSchema.setContNo(tContNo);
		tLCIndUWMasterSchema.setPassFlag("9");
		tLCIndUWMasterSchema.setUWIdea("sdfsdf");
		tLCIndUWMasterSchema.setSugPassFlag("5");
		tLCIndUWMasterSchema.setSugUWIdea("sdfsdf");

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ContNo", tContNo);
		tTransferData.setNameAndValue("InsuredNo", tInsuredNo);
		tTransferData.setNameAndValue("LCIndUWMasterSchema",
				tLCIndUWMasterSchema);

		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add(tLCIndUWMasterSchema);
		tVData.add(tTransferData);
		tVData.add(tG);

		// 数据传输
		RnewInsuredUWUI tRnewInsuredUWUI = new RnewInsuredUWUI();
		if (!tRnewInsuredUWUI.submitData(tVData, "submit")) {

			int n = tRnewInsuredUWUI.mErrors.getErrorCount();
			Content = " 核保特约失败，原因是: "
					+ tRnewInsuredUWUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}

	}

}
