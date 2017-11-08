package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web 业务系统 </p>
 * <p>Description: 增补健康告知BLF</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author Nicholas
 * @version 1.0
 */

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class PEdorHIDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorHIDetailBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	public PEdorHIDetailBLF() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		mInputData = (VData) cInputData.clone();

		// 数据准备操作
		if (!dealData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorHIDetailBLF";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败！";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 准备需要保存的数据 调用个人保全投保人信息变更业务逻辑处理类PEdorACBL进行处理
	 */
	private boolean dealData() {
		PEdorHIDetailBL tPEdorHIDetailBL = new PEdorHIDetailBL();
		if (!tPEdorHIDetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tPEdorHIDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorHIDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "数据提交失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		mResult = tPEdorHIDetailBL.getResult();
		return true;
	}

	/**
	 * 返回处理错误
	 * 
	 * @return: CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		PEdorHIDetailBLF tPEdorHIDetailBLF = new PEdorHIDetailBLF();

		// 个人批改信息
		// TransferData tTransferData = new TransferData();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		EdorDetailUI tEdorDetailUI = new EdorDetailUI();
		LCCustomerImpartSchema tLCCustomerImpartSchema = null;
		LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();

		// 后面要执行的动作：
		CErrors tError = null;
		String FlagStr = "";
		String Content = "";
		String transact = "";

		// transact = request.getParameter("fmtransact");
		transact = "INSERT||MAIN";
		// logger.debug("---transact: " + transact);

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";

		// 个人保单批改信息
		tLPEdorItemSchema.setEdorAcceptNo("86000000002047");
		// tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
		// tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
		tLPEdorItemSchema.setEdorType("HI");
		tLPEdorItemSchema.setInsuredNo("0000315260");
		// tLPEdorItemSchema.setPolNo(request.getParameter("PolNo"));
		// tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate"));
		// tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate"));

		// String tImpartNum[] = request.getParameterValues("ImpartGridNo");
		// //String tImpartVer[] = request.getParameterValues("ImpartGrid1");
		// //告知版别
		// String tImpartCode[] = request.getParameterValues("ImpartGrid1");
		// //告知编码
		// String tImpartContent[] = request.getParameterValues("ImpartGrid2");
		// //告知内容
		// String tImpartParamModle[] =
		// request.getParameterValues("ImpartGrid3"); //填写内容

		// int ImpartCount = 0;
		// if(tImpartNum != null) ImpartCount = tImpartNum.length;
		//
		// for(int i = 0; i < ImpartCount; i++){
		//
		tLCCustomerImpartSchema = new LCCustomerImpartSchema();
		// tLCCustomerImpartSchema.setProposalContNo(request.getParameter("ContNo"));
		// tLCCustomerImpartSchema.setContNo(request.getParameter("ContNo"));
		tLCCustomerImpartSchema.setCustomerNo("0000315260");
		// tLCCustomerImpartSchema.setCustomerNoType("0");
		tLCCustomerImpartSchema.setImpartCode("007");
		tLCCustomerImpartSchema
				.setImpartContent("您是否曾患有下列疾病或因下列疾病而接受检查或治疗： 消化道溃疡、消化道出血、穿孔、疝气、结肠炎、胰腺炎、肝脾肿大、肝功能异常、肝炎、病毒性肝炎携带者、肝硬化、胆道结石、胆囊结石、胰腺炎或其他有关肝、胆、胰腺、胃、小肠、结肠、直肠或肛门之疾病？");
		tLCCustomerImpartSchema.setImpartParamModle("n");
		tLCCustomerImpartSchema.setImpartVer("02");
		// tLCCustomerImpartSchema.setPatchNo("0");
		tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
		// }

		tLCCustomerImpartSchema = new LCCustomerImpartSchema();
		// tLCCustomerImpartSchema.setProposalContNo(request.getParameter("ContNo"));
		// tLCCustomerImpartSchema.setContNo(request.getParameter("ContNo"));
		tLCCustomerImpartSchema.setCustomerNo("0000315260");
		// tLCCustomerImpartSchema.setCustomerNoType("0");
		tLCCustomerImpartSchema.setImpartCode("019");
		tLCCustomerImpartSchema
				.setImpartContent("女性适用：  现在是否怀孕？若“是”，已怀孕________月？");
		tLCCustomerImpartSchema.setImpartParamModle("n,");
		tLCCustomerImpartSchema.setImpartVer("02");
		// tLCCustomerImpartSchema.setPatchNo("0");
		tLCCustomerImpartSet.add(tLCCustomerImpartSchema);

		// tLCCustomerImpartSchema = new LCCustomerImpartSchema();
		// //tLCCustomerImpartSchema.setProposalContNo(request.getParameter("ContNo"));
		// //tLCCustomerImpartSchema.setContNo(request.getParameter("ContNo"));
		// tLCCustomerImpartSchema.setCustomerNo("0000315260");
		// //tLCCustomerImpartSchema.setCustomerNoType("0");
		// tLCCustomerImpartSchema.setImpartCode("002");
		// tLCCustomerImpartSchema.setImpartContent("被保险人的祖父母、双亲、配偶、子女或兄弟姐妹中是否曾患肿瘤、癌症、心脏病、中风、高血压、糖尿病、精神病、抑郁症、乙型或非甲非乙型肝炎（包括病毒携带者）、结核病、白血病；或任何遗传疾病？是否有早于60岁因疾病去世者？");
		// tLCCustomerImpartSchema.setImpartParamModle("n");
		// tLCCustomerImpartSchema.setImpartVer("02");
		// //tLCCustomerImpartSchema.setPatchNo("0");
		// tLCCustomerImpartSet.add(tLCCustomerImpartSchema);

		try {
			// 准备传输数据 VData
			VData tVData = new VData();

			// 保存个人保单信息(保全)
			tVData.add(tG);
			tVData.add(tLPEdorItemSchema);
			// tVData.add(tTransferData);
			tVData.add(tLCCustomerImpartSet);

			boolean tag = tPEdorHIDetailBLF.submitData(tVData, transact);
		} catch (Exception ex) {
			Content = transact + "失败，原因是:" + ex.toString();
			FlagStr = "Fail";
		}
		// 如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "") {
			tError = tPEdorHIDetailBLF.mErrors;
			if (!tError.needDealError()) {
				FlagStr = "Success";
				Content = "保存成功！";
			} else {
				Content = " 保存失败，原因是:" + tError.getFirstError();
				FlagStr = "Fail";
			}
		}
		logger.debug(Content);
	}
}
