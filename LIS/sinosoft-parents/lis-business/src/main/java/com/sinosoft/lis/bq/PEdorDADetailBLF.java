package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Lizhuo
 * @version 1.0
 */
public class PEdorDADetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorDADetailBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */

	public PEdorDADetailBLF() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据
	 * @param: cOperate 数据操作字符串
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 数据准备操作
		if (!dealData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorPTDetailBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		return true;
	}

	/**
	 * 数据校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		return true;
	}

	/**
	 * 准备需要保存的数据 调用个人保全投保人信息变更业务逻辑处理类PEdorACBL进行处理
	 */
	private boolean dealData() {
		PEdorDADetailBL tPEdorDADetailBL = new PEdorDADetailBL();
		if (!tPEdorDADetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tPEdorDADetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorPTDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		mResult = tPEdorDADetailBL.getResult();
		return true;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "lee";
		tG.ManageCom = "86110000";
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		// LPEdorItemDB dd = new LPEdorItemDB();

		tLPEdorItemSchema
				.decode("6120050919000001|6020050919000001||DA|||000000006691|0000555860|210110000005920|||||||||0|0|0|0|||||||||||||||");

		LCCustomerImpartSet td = new LCCustomerImpartSet();
		td
				.decode("|000000006691|000000006691||006|02|您是否曾患有下列疾病或因下列疾病而接受检查或治疗： 慢性支气管炎、哮喘、支气管扩张症、肺气肿、肺脓肿、肺栓塞、肺结核、胸膜炎、尘肺、矽肺或其他呼吸器官之疾病或肺部之疾病？|Yes||||||||||0^|000000006691|000000006691||005|02|您是否曾患有下列疾病或因下列疾病而接受检查或治疗： 高血压病（收缩压140mmHg或舒张压90mmHg以上）、动脉硬化、冠心病、心肌梗死、心肌病、心脏瓣膜疾病、主动脉瘤、下肢静脉曲张或其他心脏和血管疾病？|Yes||||||||||0^|000000006691|000000006691||015|02|您是否曾患有下列疾病或因下列疾病而接受检查或治疗： 鼠疫、狂犬病、流行性脑脊髓膜炎、流行性乙形脑炎、炭疽、钩端螺旋体病、传染性非典型肺炎（含疑似）、性传播疾病等传染病。|Yes||||||||||0");
		VData tt = new VData();
		tt.add(tG);
		tt.add(tLPEdorItemSchema);
		tt.add(td);
		PEdorDADetailBLF aa = new PEdorDADetailBLF();
		aa.submitData(tt, "");
	}

}
