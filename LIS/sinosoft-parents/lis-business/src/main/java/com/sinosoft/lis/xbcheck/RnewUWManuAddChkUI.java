package com.sinosoft.lis.xbcheck;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author ZhangRong
 * @version 1.0
 */
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class RnewUWManuAddChkUI {
private static Logger logger = Logger.getLogger(RnewUWManuAddChkUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public RnewUWManuAddChkUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mOperate = cOperate;
		RnewUWManuAddChkBL tRnewUWManuAddChkBL = new RnewUWManuAddChkBL();

		logger.debug("---RnewUWManuAddChkUI BEGIN---");
		if (tRnewUWManuAddChkBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tRnewUWManuAddChkBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWManuAddChkUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			this.mErrors.copyAllErrors(tRnewUWManuAddChkBL.mErrors);
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

		LCPremSet tLCPremSet = new LCPremSet();
		VData tInputData = new VData();
		String tPolNo = "86110020040210000246"; // 保全项目所针对的保单
		String tPolNo2 = "86110020040210000246"; // 保全加费所针对的保单
		String tContNo = "00000000000000000006";
		String tAddReason = "i like";

		if (!tPolNo.equals("")) {
			// 准备特约加费信息
			int feeCount = 1;
			String tdutycode[] = new String[feeCount];
			String tstartdate[] = new String[feeCount];
			String tenddate[] = new String[feeCount];
			String tpayplantype[] = new String[feeCount];
			String trate[] = new String[feeCount];
			tdutycode[0] = "296001";
			tstartdate[0] = "2004-11-16";
			tenddate[0] = "2014-11-17";
			tpayplantype[0] = "";
			trate[0] = "0";
			if (feeCount > 0) {
				for (int i = 0; i < feeCount; i++) {
					if (!tdutycode[i].equals("") && !tstartdate[i].equals("")
							&& !tenddate[i].equals("") && !trate[i].equals("")) {
						LCPremSchema tLCPremSchema = new LCPremSchema();
						tLCPremSchema.setPolNo(tPolNo2);
						tLCPremSchema.setDutyCode(tdutycode[i]);
						tLCPremSchema.setPayStartDate(tstartdate[i]);
						tLCPremSchema.setPayPlanType(tpayplantype[i]);
						tLCPremSchema.setPayEndDate(tenddate[i]);
						tLCPremSchema.setPrem(trate[i]);
						// tLCPremSchema.setSuppRiskScore( tsuppriskscore[i]);
						tLCPremSet.add(tLCPremSchema);
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
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema.setContNo(tContNo);
			tLCPolSchema.setPolNo(tPolNo);

			tInputData.add(tG);
			tInputData.add(tLCPolSchema);
			tInputData.add(tAddReason);
			tInputData.add(tLCPremSet);

		}

		logger.debug("flag:" + flag);
		try {
			if (flag == true) {
				// 准备传输数据 VData
				RnewUWManuAddChkUI tUWManuAddChkUI = new RnewUWManuAddChkUI();
				if (!tUWManuAddChkUI.submitData(tInputData, "")) {
					int n = tUWManuAddChkUI.mErrors.getErrorCount();
					Content = " 人工核保加费失败，原因是: "
							+ tUWManuAddChkUI.mErrors.getError(0).errorMessage;
					FlagStr = "Fail";
				}
				// 如果在Catch中发现异常，则不从错误类中提取错误信息
				if (FlagStr == "Fail") {
					tError = tUWManuAddChkUI.mErrors;
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
}
