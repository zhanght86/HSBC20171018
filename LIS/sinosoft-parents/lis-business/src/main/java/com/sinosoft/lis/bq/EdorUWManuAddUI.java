package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/**
 * <p>Title:保全人工核保加费 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author liurx
 * @version 1.0
 */
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class EdorUWManuAddUI implements BusinessService{
private static Logger logger = Logger.getLogger(EdorUWManuAddUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public EdorUWManuAddUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mOperate = cOperate;
		EdorUWManuAddBL tEdorUWManuAddBL = new EdorUWManuAddBL();

		logger.debug("---EdorUWManuAddUI BEGIN---");
		if (tEdorUWManuAddBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tEdorUWManuAddBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorUWManuAddUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			this.mErrors.copyAllErrors(tEdorUWManuAddBL.mErrors);
		}
		return true;
	}

	public static void main(String[] a) {
		CErrors tError = null;
		String FlagStr = "Fail";
		String Content = "";
		boolean flag = true;
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";

		LPPremSet tLPPremSet = new LPPremSet();
		VData tInputData = new VData();
		String tPolNo = "NJE10322651000196000"; // 保全项目所针对的保单
		String tPolNo2 = "NJE10322651000196000"; // 保全加费所针对的保单
		String tContNo = "NJE10322651000196";
		String tEdorNo = "6020060427000039";
		String tEdorType = "HI";
		String tAddReason = "i like";

		if (!tPolNo.equals("")) {
			// 准备特约加费信息
			int feeCount = 1;
			String tdutycode[] = new String[feeCount];
			String tstartdate[] = new String[feeCount];
			String tPaytodate[] = new String[feeCount];
			String tenddate[] = new String[feeCount];
			String tpayplantype[] = new String[feeCount];
			String trate[] = new String[feeCount];
			tdutycode[0] = "265000";
			tstartdate[0] = "2006-10-19";
			tPaytodate[0] = "2006-10-19";
			tenddate[0] = "2013-10-19";
			tpayplantype[0] = "01";
			trate[0] = "200";
			if (feeCount > 0) {
				for (int i = 0; i < feeCount; i++) {
					if (!tdutycode[i].equals("") && !tstartdate[i].equals("")
							&& !tenddate[i].equals("") && !trate[i].equals("")) {
						LPPremSchema tLPPremSchema = new LPPremSchema();
						tLPPremSchema.setPolNo(tPolNo2);
						tLPPremSchema.setContNo(tPolNo2);
						tLPPremSchema.setEdorNo(tEdorNo);
						tLPPremSchema.setEdorType(tEdorType);
						tLPPremSchema.setDutyCode(tdutycode[i]);
						tLPPremSchema.setPayStartDate(tstartdate[i]);
						tLPPremSchema.setPaytoDate(tPaytodate[i]);
						tLPPremSchema.setPayPlanType(tpayplantype[i]);
						tLPPremSchema.setPayEndDate(tenddate[i]);
						tLPPremSchema.setPrem(trate[i]);
						tLPPremSchema.setSuppRiskScore(30);
						tLPPremSchema.setAddFeeDirect("02");
						tLPPremSchema.setSecInsuAddPoint(0);
						tLPPremSet.add(tLPPremSchema);
						flag = true;

						logger.debug("i:" + i);
						logger.debug("责任编码" + i + ":  " + tdutycode[i]);
					} // End of if
					else {
						Content = "加费信息未填写完整,请确认!";
						flag = false;
						FlagStr = "Fail";
						break;
					}
				} // End of for
			} // End of if

			// 准备公共传输信息
			LPPolSchema tLPPolSchema = new LPPolSchema();
			tLPPolSchema.setContNo(tContNo);
			tLPPolSchema.setPolNo(tPolNo);
			tLPPolSchema.setEdorType(tEdorType);
			tLPPolSchema.setEdorNo(tEdorNo);

			tInputData.add(tG);
			tInputData.add(tLPPolSchema);
			tInputData.add(tAddReason);
			tInputData.add(tLPPremSet);

		}

		logger.debug("flag:" + flag);
		try {
			if (flag == true) {
				// 准备传输数据 VData
				EdorUWManuAddUI tEdorUWManuAddUI = new EdorUWManuAddUI();
				if (!tEdorUWManuAddUI.submitData(tInputData, "")) {
					int n = tEdorUWManuAddUI.mErrors.getErrorCount();
					Content = " 人工核保加费失败，原因是: "
							+ tEdorUWManuAddUI.mErrors.getError(0).errorMessage;
					FlagStr = "Fail";
				}
				// 如果在Catch中发现异常，则不从错误类中提取错误信息
				if (FlagStr == "Fail") {
					tError = tEdorUWManuAddUI.mErrors;
					if (!tError.needDealError()) {
						Content = " 人工核保加费成功! ";
						FlagStr = "Succ";
					} else {
						FlagStr = "Fail";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Content = Content.trim() + ".提示：异常终止!";
		}
	}
	public VData getResult() {
		return mResult;
	}
	public CErrors getErrors() {
		return mErrors;
	}
}
