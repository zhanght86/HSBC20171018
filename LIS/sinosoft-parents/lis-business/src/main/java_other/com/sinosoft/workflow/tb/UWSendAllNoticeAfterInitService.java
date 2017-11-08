package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.cbcheck.UWSendAllPrintBL;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: 发核保通知书
 * </p>
 * 
 * <p>
 * Description:发核保通知书AfterInit服务类
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: ChinaSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */

public class UWSendAllNoticeAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(UWSendAllNoticeAfterInitService.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往界面传输数据的容器 */
	private VData mInputData = new VData();
	private MMap mMap = new MMap();

	private String mReason = ""; // 加费原因
	private String mSpecReason = ""; // 特约原因
	private String mQuesFlag = ""; // 是否有问题件标记
	private String mQuesOrgFlag = ""; // 机构问题件标记
	private String mQuesOpeFlag = ""; // 操作员问题件标记
	private String mPolType = ""; // 保单类型
	private String mAgentPrintFlag = ""; // 是否有返回业务员要打印的问题件标记
	private String mAgentQuesFlag = ""; // 是否有返回业务员问题件标记
	private String mAddFeeFlag = "";// 是否有加费标志
	private String mSpecFlag = "";// 是否有特约
	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	public UWSendAllNoticeAfterInitService() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// tongmeng 2007-11-09 modify
		
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 进行业务验证
		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		map.add(mMap);

		mResult.add(map);

		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		this.mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 获得所有需要生成的核保通知书的类型
		LOPRTManagerSet sLOPRTManagerSet = getAllNeedPrintData();
		for (int i = 1; i <= sLOPRTManagerSet.size(); i++) {
			LOPRTManagerSchema tempLOPRTManagerSchema = new LOPRTManagerSchema();
			tempLOPRTManagerSchema.setSchema(sLOPRTManagerSet.get(i));
			VData tVData = (VData) this.mInputData.clone();
			tVData.add(tempLOPRTManagerSchema);
			// 加入业务逻辑处理类
			UWSendAllPrintBL tUWSendAllPrintNewBL = new UWSendAllPrintBL();
			if (!tUWSendAllPrintNewBL.submitData(tVData, "")) {
				this.mErrors.copyAllErrors(tUWSendAllPrintNewBL.getErrors());
				return false;
			} else {
				mMap = (MMap) tUWSendAllPrintNewBL.getResult()
						.getObjectByObjectName("MMap", 0);
				mTransferData = null;
				mTransferData = (TransferData) tUWSendAllPrintNewBL.getResult()
						.getObjectByObjectName("TransferData", 0);
			}
		}
		return true;
	}

	/**
	 * 返回处理后的结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回工作流中的Lwfieldmap所描述的值
	 * 
	 * @return TransferData
	 */
	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	/**
	 * 返回错误对象
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 查询并生成所有需要生成的打印通知书数据
	 * 
	 * @return
	 */
	private LOPRTManagerSet getAllNeedPrintData() {
		LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();
		// 判断是否该出现相应类型的打印数据
		String tProposalContNo = (String) this.mTransferData
				.getValueByName("ProposalContNo");
		// 1-判断各种问题件类型
		this.checkQuestOpe(tProposalContNo);
		this.checkAgentQuest(tProposalContNo);
		this.checkQuest(tProposalContNo);
		this.checkQuestOrg(tProposalContNo);

		// 1-发送给业务员问题件的处理
		if (this.mAgentQuesFlag.equals("1")) {
			LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
			tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT);// 合同号类型
			tLOPRTManagerSchema.setOtherNo(tProposalContNo);// 合同号
			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_AGEN_QUEST);
			tLOPRTManagerSet.add(tLOPRTManagerSchema);
		}
		// 2-发送保户通知书(核保通知书)
		if (this.mQuesFlag.equals("1") || this.mAddFeeFlag.equals("1")
				|| this.mSpecFlag.equals("1")) {
			LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
			tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT);// 合同号类型
			tLOPRTManagerSchema.setOtherNo(tProposalContNo);// 合同号
			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_UW);
			tLOPRTManagerSet.add(tLOPRTManagerSchema);
		}
		// 3-发送给机构的问题件
		if (this.mQuesOrgFlag.equals("1")) {
			// 不需要发放通知书
		}
		// 4-发送给问题件修改岗的问题件
		if (this.mQuesOpeFlag.equals("1")) {
			// 不需要发放通知书
		}
		return tLOPRTManagerSet;

	}

	/**
	 * 校验是否有返回业务员问题件
	 * 
	 * @return
	 */
	private void checkAgentQuest(String tProposalContNo) {
		String tsql = "";
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();

		tsql = "select * from lcissuepol where ProposalContNo = '"
				+ "?ProposalContNo1?"
				+ "' and backobjtype = '3' and replyresult is null and needprint = 'Y'";
		 SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
	      sqlbv1.sql(tsql);
	      sqlbv1.put("ProposalContNo1",tProposalContNo);
		tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv1);
		if (tLCIssuePolSet.size() > 0) {
			mAgentPrintFlag = "1";
			mAgentQuesFlag = "1";
		}
		// return true;
	}

	/**
	 * 校验是否有返回保户问题件
	 * 
	 * @return
	 */
	private void checkQuest(String tProposalContNo) {
		String tsql = "";
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();

		tsql = "select * from lcissuepol where ProposalContNo = '"
				+ "?ProposalContNo2?"
				+ "' and backobjtype = '2' and replyresult is null and needprint = 'Y'";
		 SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
	      sqlbv2.sql(tsql);
	      sqlbv2.put("ProposalContNo2",tProposalContNo);
		tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv2);
		if (tLCIssuePolSet.size() > 0) {
			mQuesFlag = "1";
		}

		// tongmeng 2007-11-09 add
		// 判断是否有加费通知书

		// tongmeng 2007-11-09 add
		// 判断是否有特约通知书
		// 查询是否存在有录入未做发放的特约信息
		//"Oracle：select nvl(sum(prem),0) from ljtempfee_lmriskapp
		//改造为：select (case when sum(prem) is not null then sum(prem)  else 0 end)  from ljtempfee_lmriskapp;"

		/*String tSQL_Spec = "select count(A.m) from (select nvl((select stateflag "
				+ " from loprtmanager p "
				+ " where  p.prtseq = s.prtseq),'x') m "
				+ " from lcspec s where s.contno='"
				+ "?ProposalContNo3?"
				+ "') A "
				+ " where A.m='x' ";*/
		String tSQL_Spec = "select count(A.m) from (select (case when(select stateflag "
				+ " from loprtmanager p "
				+ " where  p.prtseq = s.prtseq) is not null then (select stateflag "
				+ " from loprtmanager p "
				+ " where  p.prtseq = s.prtseq) else 'x' end) m "
				+ " from lcspec s where s.contno='"
				+ "?ProposalContNo3?"
				+ "') A "
				+ " where A.m='x' ";
		 SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
	      sqlbv3.sql(tSQL_Spec);
	      sqlbv3.put("ProposalContNo3",tProposalContNo);
		ExeSQL tExeSQL = new ExeSQL();
		String tResult_Spec = tExeSQL.getOneValue(sqlbv3);
		if (Integer.parseInt(tResult_Spec) > 0) {
			mSpecFlag = "1";
		}
		// return true;
	}

	/**
	 * 校验是否有返回机构问题件
	 * 
	 * @return
	 */
	private void checkQuestOrg(String tProposalContNo) {
		String tsql = "";
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();

		tsql = "select * from lcissuepol where ProposalContNo = '"
				+ "?ProposalContNo4？"
				+ "' and backobjtype = '4' and replyresult is null";
		 SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
	      sqlbv4.sql(tsql);
	      sqlbv4.put("ProposalContNo4",tProposalContNo);
		tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv4);
		if (tLCIssuePolSet.size() > 0) {
			mQuesOrgFlag = "1";
		}
		// return true;
	}

	/**
	 * 校验是否有返操作员问题件
	 * 
	 * @return
	 */
	private void checkQuestOpe(String tProposalContNo) {
		String tsql = "";
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();

		tsql = "select * from lcissuepol where ProposalContNo = '"
				+ "?ProposalContNo5?"
				+ "' and backobjtype = '1' and replyresult is null";
		 SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
	      sqlbv5.sql(tsql);
	      sqlbv5.put("ProposalContNo5",tProposalContNo);
		tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv5);
		if (tLCIssuePolSet.size() > 0) {
			mQuesOpeFlag = "1";
		}
		// return true;
	}
}
