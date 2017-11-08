package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPAppntIndSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:被保险人资料变更功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Tjj
 * @version 1.0
 */
public class PEdorPTDetailUI implements BusinessService {
private static Logger logger = Logger.getLogger(PEdorPTDetailUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public PEdorPTDetailUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		PEdorPTDetailBLF tPEdorPTDetailBLF = new PEdorPTDetailBLF();
		logger.debug("---UI BEGIN---" + mOperate);
		if (tPEdorPTDetailBLF.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPEdorPTDetailBLF.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorPTDetailUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else
			mResult = tPEdorPTDetailBLF.getResult();
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		LPAppntIndSchema tLPAppntIndSchema = new LPAppntIndSchema();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		PEdorPTDetailUI tPEdorPTDetailUI = new PEdorPTDetailUI();
		String transact = "INSERT||MAIN";
		// logger.debug("------transact:"+transact);
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";

		String polNo = "HB020526161000098325";
		String edorNo = "6020050911000087";
		String edorType = "PT";
		String contNo = "HB020526161000098";
		String InsuredNo = "000026572";
		String edorAcceptNo = "6120050911000059";
		// 个人批改信息
		tLPEdorItemSchema.setPolNo(polNo);
		tLPEdorItemSchema.setEdorNo(edorNo);
		tLPEdorItemSchema.setEdorType(edorType);
		tLPEdorItemSchema.setInsuredNo(InsuredNo);
		tLPEdorItemSchema.setEdorAcceptNo(edorAcceptNo);
		tLPEdorItemSchema.setContNo(contNo);
		tLPEdorItemSchema.setEdorReason("22");
		tLPEdorItemSchema.setEdorReasonCode("22");
		try {
			// 准备传输数据 VData

			VData tVData = new VData();

			// 保存个人保单信息(保全)
			tVData.addElement(tG);
			tLPEdorItemSchema
					.decode("6120050911000059|6020050911000103||PT|||HB020526161000098|000000|000000|||||||||0|0|0|0|||||||||||||||");
			tVData.addElement(tLPEdorItemSchema);

			LPPolSchema tLPPolSchema = new LPPolSchema();
			tLPPolSchema.setPolNo(polNo);
			tLPPolSchema.setEdorNo(edorNo);
			tLPPolSchema.setEdorType(edorType);
			tLPPolSchema.setAmnt(20000);
			tLPPolSchema.setMult(0);
			tLPPolSchema
					.decode("|||||HB020526161000098325||||||||||||||||||||||0|0|||||||||||||||0||0||0||0|||||0|0|0|0|0||0|0|0|0|20000.0|0|0|0|0|0|0||||||0||||||||||||||||||||||||||||||||||||||0|||||");

			tVData.addElement(tLPPolSchema);

			logger.debug("start UI....");
			tPEdorPTDetailUI.submitData(tVData, transact);

		} catch (Exception ex) {
			// Content = transact + "失败，原因是:" + ex.toString();
			// FlagStr = "Fail";
		}

	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
