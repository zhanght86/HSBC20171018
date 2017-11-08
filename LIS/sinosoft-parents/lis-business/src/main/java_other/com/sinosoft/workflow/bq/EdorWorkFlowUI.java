package com.sinosoft.workflow.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
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
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unascribed ReWrite ZhangRong
 * @version 1.0
 */

public class EdorWorkFlowUI implements BusinessService{
private static Logger logger = Logger.getLogger(EdorWorkFlowUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public EdorWorkFlowUI() {
	}

	public static void main(String[] args) {

		/** 传递变量 */
		// mTransferData.setNameAndValue("EdorNo","86110020030420000274") ;
		// mTransferData.setNameAndValue("PolNo","86110020030210001677");
		// mTransferData.setNameAndValue("RiskCode","111301");
		// mTransferData.setNameAndValue("RiskName","11130险种");
		// mTransferData.setNameAndValue("InsuredNo","0000000139");
		// mTransferData.setNameAndValue("InsuredName","王士宝");
		// mTransferData.setNameAndValue("AppntNo","0000000139");
		// mTransferData.setNameAndValue("AppntName","王士宝");
		// mTransferData.setNameAndValue("InsuredNo","0000010748");
		// //发体检通知书测试
		// LPPENoticeSchema tLPPENoticeSchema = new LPPENoticeSchema();
		// tLPPENoticeSchema.setEdorAcceptNo("86000000000228");
		// tLPPENoticeSchema.setPEAddress("11003001");
		// tLPPENoticeSchema.setPEDate("2003-07-01");
		// tLPPENoticeSchema.setPEBeforeCond("N");
		// tLPPENoticeSchema.setRemark("ReMark");
		// tLPPENoticeSchema.setCustomerNo("0000318860");
		// mTransferData.setNameAndValue("LPPENoticeSchema", tLPPENoticeSchema);
		//
		// LPPENoticeItemSet tLPPENoticeItemSet = new LPPENoticeItemSet();
		// LPPENoticeItemSchema tLPPENoticeItemSchema = new
		// LPPENoticeItemSchema();
		// tLPPENoticeItemSchema.setEdorAcceptNo("86000000000228");
		// tLPPENoticeItemSchema.setPEItemCode("001");
		// tLPPENoticeItemSchema.setPEItemName("普检");
		// tLPPENoticeItemSchema.setFreePE("N");
		//
		// LPPENoticeItemSchema tLPPENoticeItemSchema2 = new
		// LPPENoticeItemSchema();
		// tLPPENoticeItemSchema2.setEdorNo("86000000000228");
		// tLPPENoticeItemSchema2.setPEItemCode("002");
		// tLPPENoticeItemSchema2.setPEItemName("尿常规");
		// tLPPENoticeItemSchema2.setFreePE("N");
		//
		// tLPPENoticeItemSet.add(tLPPENoticeItemSchema);
		// tLPPENoticeItemSet.add(tLPPENoticeItemSchema2);
		// mTransferData.setNameAndValue("LPPENoticeItemSet",
		// tLPPENoticeItemSet);
		// mTransferData.setNameAndValue("EdorAcceptNo", "86000000000228");
		// mTransferData.setNameAndValue("MissionID", "00000000000000004874");
		// mTransferData.setNameAndValue("SubMissionID", "1");
		// mTransferData.setNameAndValue("InsuredNo", "0000318860");
		// 特约录入测试
		// LPSpecSchema tLPSpecSchema = new LPSpecSchema();
		// tLPSpecSchema.setPolNo("86110020030210009299");
		// tLPSpecSchema.setEndorsementNo("86110020030410000126");
		// tLPSpecSchema.setPolType("1");
		// tLPSpecSchema.setSpecContent("特约内容");
		// tLPSpecSchema.setSpecType("1");
		// tLPSpecSchema.setSpecCode("1");
		//
		// mTransferData.setNameAndValue("PolNo","86110020030210009299");
		// mTransferData.setNameAndValue("EdorNo","86110020030410000126") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000000014");
		// mTransferData.setNameAndValue("SpecReason","特约原因");
		// mTransferData.setNameAndValue("LPSpecSchema",tLPSpecSchema);
		// //加费录入测试
		// LPPremSet tLPPremSet = new LPPremSet();
		// LPPremSchema tLPPremSchema = new LPPremSchema();
		// tLPPremSchema.setPolNo("86110020030210034999");
		// tLPPremSchema.setEdorNo("86110020040410000054");
		// tLPPremSchema.setEdorType("AC");//保全加费
		// tLPPremSchema.setDutyCode("223001");
		// //tLPPremSchema.setPayPlanCode("00000001");//加费
		// tLPPremSchema.setPayStartDate("2003-03-18");
		// tLPPremSchema.setPayPlanType( "1");
		// tLPPremSchema.setPayEndDate( "2008-03-18");
		// tLPPremSchema.setPrem( 50);
		// tLPPremSet.add( tLPPremSchema );
		//
		// LPPremSchema tLPPremSchema2 = new LPPremSchema();
		// tLPPremSchema2.setPolNo("86110020030210034999");
		// tLPPremSchema2.setEdorNo("86110020040410000054");
		// tLPPremSchema2.setEdorType("AC");//保全加费
		// tLPPremSchema2.setDutyCode("223001");
		// //tLPPremSchema2.setPayPlanCode("00000002");//加费
		// tLPPremSchema2.setPayStartDate("2003-03-18");
		// tLPPremSchema2.setPayPlanType( "2");
		// tLPPremSchema2.setPayEndDate( "2008-03-18");
		// tLPPremSchema2.setPrem( 50);
		// tLPPremSet.add( tLPPremSchema2 );
		//
		// mTransferData.setNameAndValue("PolNo","86110020030210034999");
		// mTransferData.setNameAndValue("PolNo2","86110020030210034999");
		// mTransferData.setNameAndValue("EdorNo","86110020040410000054") ;
		// mTransferData.setNameAndValue("EdorType","AC") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000000037");
		// mTransferData.setNameAndValue("SubMissionID","1");
		// mTransferData.setNameAndValue("AddReason","加费原因");
		// mTransferData.setNameAndValue("LPPremSet",tLPPremSet);
		// //生调通知书测试
		// LPRReportSchema tLPRReportSchema = new LPRReportSchema();
		// tLPRReportSchema.setPolNo("86110020030210001677");
		// tLPRReportSchema.setContente("生调内容");
		//
		// mTransferData.setNameAndValue("PolNo","86110020030210001677");
		// mTransferData.setNameAndValue("EdorNo","86110020030410000129") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000000015");
		// mTransferData.setNameAndValue("LPRReportSchema",tLPRReportSchema);

		// //发送核保通知书
		// mTransferData.setNameAndValue("PolNo","86110020030210009537");
		// mTransferData.setNameAndValue("EdorNo","86110020030410000211") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000000027");
		// mTransferData.setNameAndValue("SubMissionID","1");
		//
		//
		// 准备传输工作流数据 VData
		// LPUWMasterMainSchema mLPUWMasterMainSchema = new
		// LPUWMasterMainSchema();
		// mLPUWMasterMainSchema.setPolNo("86110020030210009299");
		// mLPUWMasterMainSchema.setEdorNo("86110020030410000126");
		// mLPUWMasterMainSchema.setUWIdea("正常承保!");
		// mLPUWMasterMainSchema.setPassFlag("9");
		// mLPUWMasterMainSchema.setAppGrade("");
		//
		// 准备传输工作流数据 VData
		// mTransferData.setNameAndValue("PolNo","86110020030210001017");
		// mTransferData.setNameAndValue("EdorNo","86110020030410000195");
		// mTransferData.setNameAndValue("MissionID","00000000000000000017");
		// mTransferData.setNameAndValue("LPUWMasterMainSchema",mLPUWMasterMainSchema);
		//
		// //打印核保通知书
		// mTransferData.setNameAndValue("PrtSeq","86000020030810002053");
		// mTransferData.setNameAndValue("Code","23") ;
		// mTransferData.setNameAndValue("PolNo","86110020030210001092") ;
		// mTransferData.setNameAndValue("EdorNo","86110020030410000200") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000000023") ;
		// mTransferData.setNameAndValue("SubMissionID","1") ;
		// //打印生调通知书
		// mTransferData.setNameAndValue("PrtSeq","86000020030810002081");
		// mTransferData.setNameAndValue("Code","24") ;
		// mTransferData.setNameAndValue("PolNo","86110020030210004500") ;
		// mTransferData.setNameAndValue("EdorNo","86110020030410000213") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000000028") ;
		// mTransferData.setNameAndValue("SubMissionID","1") ;

		// 回收体检通知书
		// LZSysCertifySchema tLZSysCertifySchema = new LZSysCertifySchema();
		// tLZSysCertifySchema.setCertifyCode( "7773" );
		// tLZSysCertifySchema.setCertifyNo( "86000020030810002085" );
		// tLZSysCertifySchema.setTakeBackOperator( "001" );
		// tLZSysCertifySchema.setTakeBackDate( "2003-12-17" );
		// tLZSysCertifySchema.setTakeBackMakeDate( "2003-12-17" );
		// tLZSysCertifySchema.setSendOutCom( "A86" );
		// tLZSysCertifySchema.setReceiveCom( "D8611000091" );
		//
		// // 准备传输数据 VData
		// String tOperate = new String();
		// TransferData tTransferData = new TransferData();
		// tTransferData.setNameAndValue("CertifyNo","86000020030810002085");
		// tTransferData.setNameAndValue("CertifyCode","7773") ;
		// tTransferData.setNameAndValue("PolNo","86110020030210004500") ;
		// tTransferData.setNameAndValue("EdorNo","86110020030410000213") ;
		// tTransferData.setNameAndValue("MissionID","00000000000000000028") ;
		// tTransferData.setNameAndValue("SubMissionID","1") ;
		// tTransferData.setNameAndValue("LZSysCertifySchema",tLZSysCertifySchema);
		// 补体检通知书
		// LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		// tLOPRTManagerSchema.setPrtSeq("86000020030810002060") ;
		// mTransferData.setNameAndValue("Code","23") ;
		// mTransferData.setNameAndValue("PolNo","86110020030210018153") ;
		// mTransferData.setNameAndValue("EdorNo","86110020030410000202") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000000025") ;
		// mTransferData.setNameAndValue("SubMissionID","2") ;
		// mTransferData.setNameAndValue("LOPRTManagerSchema",tLOPRTManagerSchema)
		// ;
		// 补打生调通知书
		// LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		// tLOPRTManagerSchema.setPrtSeq("86000020030810002081") ;
		// mTransferData.setNameAndValue("Code","24") ;
		// mTransferData.setNameAndValue("PolNo","86110020030210004500") ;
		// mTransferData.setNameAndValue("EdorNo","86110020030410000213") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000000028") ;
		// mTransferData.setNameAndValue("SubMissionID","1") ;
		// mTransferData.setNameAndValue("LOPRTManagerSchema",tLOPRTManagerSchema)
		// ;
		// //回收生调通知书
		// LPRReportSchema tLPRReportSchema = new LPRReportSchema();
		// tLPRReportSchema.setPolNo("86110020030210004500");
		// tLPRReportSchema.setSerialNo("0");
		// tLPRReportSchema.setReplyContente("回复内容:dfdfdfd");
		//
		// //准备公共传输信息
		// mTransferData.setNameAndValue("PolNo","86110020030210004500");
		// mTransferData.setNameAndValue("EdorNo","86110020030410000213") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000000028");
		// mTransferData.setNameAndValue("PrtSeq","86000020030810002084");
		// mTransferData.setNameAndValue("SubMissionID","3");
		// mTransferData.setNameAndValue("LPRReportSchema",tLPRReportSchema);
		// //下核保结论
		// LPUWMasterMainSchema tLPUWMasterMainSchema = new
		// LPUWMasterMainSchema();
		// tLPUWMasterMainSchema.setPolNo("86110020030210001115");
		// tLPUWMasterMainSchema.setEdorNo("86110020040410000073");
		// tLPUWMasterMainSchema.setUWIdea("延期承保");
		// tLPUWMasterMainSchema.setPostponeDay("2") ;
		// tLPUWMasterMainSchema.setPassFlag("2");
		//
		// // 准备传输工作流数据 VData
		// mTransferData.setNameAndValue("EdorAcceptNo","86000000000192");
		// mTransferData.setNameAndValue("MissionID","00000000000040004072");
		// mTransferData.setNameAndValue("SubMissionID","1");
		// mTransferData.setNameAndValue("LPUWMasterMainSchema",tLPUWMasterMainSchema);
		// VData tVData3 = new VData();
		// mTransferData.setNameAndValue("PolNo","86110020030210035008");
		// mTransferData.setNameAndValue("EdorNo","86110020040410000039") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000000023");
		// mTransferData.setNameAndValue("SubMissionID","1");
		// 保全开始节点建立
		// VData tVData = new VData();
		// /** 全局变量 */
		// GlobalInput mGlobalInput = new GlobalInput();
		// mGlobalInput.Operator = "001";
		// mGlobalInput.ComCode = "86";
		// mGlobalInput.ManageCom = "86";
		//
		// TransferData mTransferData = new TransferData();
		// mTransferData.setNameAndValue("EdorAcceptNo","86000000000543");
		// mTransferData.setNameAndValue("MissionID","00000000000000004077");
		// mTransferData.setNameAndValue("SubMissionID","1");
		// mTransferData.setNameAndValue("EdorNo","0000000003");
		// mTransferData.setNameAndValue("ContNo","0");
		// mTransferData.setNameAndValue("OtherNo", "410000000000175");
		// mTransferData.setNameAndValue("OtherNoType", "03");
		// mTransferData.setNameAndValue("EdorAppName", "838");
		// mTransferData.setNameAndValue("Apptype", "1");
		// mTransferData.setNameAndValue("EdorAppDate", "2005-05-05");
		// mTransferData.setNameAndValue("ManageCom", mGlobalInput.ManageCom);
		// mTransferData.setNameAndValue("EdorState", "3");
		//
		// LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
		// tLPEdorAppSchema.setEdorAcceptNo("86000000000543");
		// tLPEdorAppSchema.setOtherNo("410000000000175"); //申请号码
		// tLPEdorAppSchema.setOtherNoType("03"); //申请号码类型
		// tLPEdorAppSchema.setEdorAppName("8848lhs"); //申请人名称
		// tLPEdorAppSchema.setAppType("1"); //申请方式
		// tLPEdorAppSchema.setEdorAppDate("2005-05-05"); //批改申请日期
		// tLPEdorAppSchema.setManageCom("86"); //管理机构
		//
		// tVData.addElement(mGlobalInput);
		// tVData.add(mTransferData);
		// tVData.addElement(tLPEdorAppSchema);
		//
		// EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI();
		// String mOperate = "0000000003";
		// try {
		// if (tEdorWorkFlowUI.submitData(tVData, mOperate)) {
		// //VData tResult = new VData();
		// //tResult = tActivityOperator.getResult() ;
		// }
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }
		// --------------- start test 保全受理
		// VData tVData = new VData();
		// LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
		// tLPEdorAppSchema.setEdorAcceptNo("86000000000667");
		//
		// TransferData tTransferData = new TransferData();
		// tTransferData.setNameAndValue("EdorAcceptNo", "86000000000667");
		// tTransferData.setNameAndValue("OtherNo", "230000000000009");
		// tTransferData.setNameAndValue("OtherNoType", "03");
		// tTransferData.setNameAndValue("EdorAppName", "黄钥匙");
		// tTransferData.setNameAndValue("Apptype", "1");
		// tTransferData.setNameAndValue("EdorAppDate", "2005-04-20");
		// tTransferData.setNameAndValue("ManageCom", "86");
		//
		//
		// tTransferData.setNameAndValue("MissionID", "00000000000000005737");
		// tTransferData.setNameAndValue("SubMissionID", "1");
		// String transact = "0000000001";
		// GlobalInput mGlobalInput = new GlobalInput();
		// mGlobalInput.Operator = "001";
		// mGlobalInput.ComCode = "86";
		// mGlobalInput.ManageCom = "86";
		//
		// EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI();
		// //PEdorAcceptAppConfirmUI tPEdorAcceptAppConfirmUI = new
		// PEdorAcceptAppConfirmUI();
		//
		// String Content = "";
		// String FlagStr = "";
		//
		// //String transact = "INSERT||EDORAPPCONFIRM";
		//
		//
		// try {
		// tVData.addElement(tLPEdorAppSchema);
		// tVData.addElement(mGlobalInput);
		// tVData.addElement(tTransferData);
		//
		// tEdorWorkFlowUI.submitData(tVData, transact);
		//
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// Content = transact + "失败，原因是:" + ex.toString();
		// FlagStr = "Fail";
		// }
		// logger.debug("Content----" + Content);
		// logger.debug("FlagStr----" + FlagStr);
		// --------------- end test 保全受理
		// start test 开始节点-----------------------
		// GlobalInput mGlobalInput = new GlobalInput();
		// mGlobalInput.Operator = "001";
		// mGlobalInput.ComCode = "86";
		// mGlobalInput.ManageCom = "86";
		//
		// LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
		// tLPEdorAppSchema.setOtherNo("230000000000053"); //申请号码
		// tLPEdorAppSchema.setOtherNoType("03"); //申请号码类型
		// tLPEdorAppSchema.setEdorAppName("langmanshan"); //申请人名称
		// tLPEdorAppSchema.setAppType("1"); //申请方式
		// tLPEdorAppSchema.setEdorAppDate("2005-04-21"); //批改申请日期
		// tLPEdorAppSchema.setManageCom(mGlobalInput.ManageCom); //管理机构
		// tLPEdorAppSchema.setEdorState("3");
		//
		// String Result = "";
		// String transact = "9999999999";
		//
		// /** 保全受理信息/核销信息 */
		// TransferData mTransferData = new TransferData();
		// //mTransferData.setNameAndValue("EdorAcceptNo", "");
		// mTransferData.setNameAndValue("OtherNo", "230110000000027");
		// mTransferData.setNameAndValue("OtherNoType", "03");
		// mTransferData.setNameAndValue("EdorAppName", "JiangWei");
		// mTransferData.setNameAndValue("Apptype", "1");
		// mTransferData.setNameAndValue("EdorAppDate", "2005-04-20");
		// mTransferData.setNameAndValue("ManageCom", mGlobalInput.ManageCom);
		// mTransferData.setNameAndValue("EdorState", "3");
		// mTransferData.setNameAndValue("NodeID", "9999999999");
		// mTransferData.setNameAndValue("Transact", "INSERT||EDORAPP");
		//
		// VData tVData = new VData();
		// tVData.addElement(mGlobalInput);
		// tVData.addElement(tLPEdorAppSchema);
		// tVData.add(mTransferData);
		// EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI();
		//
		// if (tEdorWorkFlowUI.submitData(tVData, transact)) {
		// tVData.clear();
		// tVData = tEdorWorkFlowUI.getResult();
		// LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
		// for (int i = 0; i < tVData.size(); i++) {
		// VData tempVData = new VData();
		// tempVData = (VData) tVData.get(i);
		//
		// mLPEdorAppSchema =
		// (LPEdorAppSchema)
		// tempVData.getObjectByObjectName("LPEdorAppSchema", 0);
		//
		// if (mLPEdorAppSchema != null) {
		// tLPEdorAppSchema.setEdorAcceptNo(mLPEdorAppSchema.
		// getEdorAcceptNo());
		// logger.debug("--AcceptNO---" +
		// tLPEdorAppSchema.getEdorAcceptNo());
		// break;
		// }
		//
		// }
		//
		// }
		// end test 开始节点-----------------------
		// start test---保全申请-------------------------
		// GlobalInput mGlobalInput = new GlobalInput();
		// mGlobalInput.Operator = "001";
		// mGlobalInput.ComCode = "86";
		// mGlobalInput.ManageCom = "86";
		//
		// LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
		// TransferData mTransferData = new TransferData();
		//
		// mTransferData.setNameAndValue("EdorAcceptNo", "86000000000682");
		// mTransferData.setNameAndValue("OtherNo", "230110000000027");
		// mTransferData.setNameAndValue("OtherNoType", "03");
		// mTransferData.setNameAndValue("EdorAppName", "JiangWei");
		// mTransferData.setNameAndValue("Apptype", "1");
		// mTransferData.setNameAndValue("ManageCom", mGlobalInput.ManageCom);
		//
		// mTransferData.setNameAndValue("MissionID", "00000000000000005763");
		// mTransferData.setNameAndValue("SubMissionID", "1");
		//
		// VData tVData = new VData();
		// CErrors tError = null;
		// String FlagStr = "";
		// String Content = "";
		// //String transact = "INSERT||EDORAPPCONFIRM";
		// String transact = "0000000003";
		// tLPEdorAppSchema.setEdorAcceptNo("86000000000666");
		// EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI();
		// try {
		// tVData.addElement(tLPEdorAppSchema);
		// tVData.addElement(mGlobalInput);
		// tVData.addElement(mTransferData);
		// tEdorWorkFlowUI.submitData(tVData, transact);
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// Content = transact + "失败，原因是:" + ex.toString();
		// FlagStr = "Fail";
		// }
		// end test---保全申请-----------------------
		// start test---自动核保-------------------------
		// LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
		LPEdorAppSchema tLPEdorAppSchem = new LPEdorAppSchema();
		EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI();
		TransferData tTransferData = new TransferData();
		GlobalInput mGlobalInput = new GlobalInput();
		mGlobalInput.Operator = "001";
		mGlobalInput.ComCode = "86";
		mGlobalInput.ManageCom = "86";

		// 输出参数
		CErrors tError = null;
		// 后面要执行的动作：添加，修改，删除

		boolean tResult = true;
		String FlagStr = "";
		String Content = "";
		String transact = "0000000004";
		String tEdorAcceptNo = "86000000000744";
		String tMissionID = "00000000000000005809";
		String tSubMissionID = "1";
		logger.debug("start....");
		tLPEdorAppSchem.setEdorAcceptNo(tEdorAcceptNo);
		// 个人批改信息
		tTransferData.setNameAndValue("EdorAcceptNo", tEdorAcceptNo);
		tTransferData.setNameAndValue("OtherNo", "230110000000482");
		tTransferData.setNameAndValue("OtherNoType", "03");
		tTransferData.setNameAndValue("EdorAppName", "黄嵘 ");
		tTransferData.setNameAndValue("Apptype", "1");
		tTransferData.setNameAndValue("ManageCom", mGlobalInput.ManageCom);
		tTransferData.setNameAndValue("MissionID", tMissionID);
		tTransferData.setNameAndValue("SubMissionID", tSubMissionID);

		VData tVData = new VData();
		// 保存个人保单信息(保全)
		tVData.addElement(tLPEdorAppSchem);
		tVData.addElement(tTransferData);
		tVData.addElement(mGlobalInput);

		if (tEdorWorkFlowUI.submitData(tVData, transact)) {
			tTransferData = (TransferData) tEdorWorkFlowUI.getResult()
					.getObjectByObjectName("TransferData", 0);
			if (((String) tTransferData.getValueByName("UWFlag")).equals("5")) {
				Content = "核保成功";
			} else {
				Content = "核保失败！";
			}
			logger.debug("-----------------------Content:" + Content
					+ "--------------------------");

		}
		;

		logger.debug("-----------------------核保方法执行失败！--------------------------");

		// end test---自动核保-------------------------
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		EdorWorkFlowBL tEdorWorkFlowBL = new EdorWorkFlowBL();
		// 工作流处理
		logger.debug("---EdorWorkFlowUI UI BEGIN---");
		if (tEdorWorkFlowBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tEdorWorkFlowBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowUI";
			tError.functionName = "submitData";
			tError.errorMessage = "保全受理失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		}
		mResult = tEdorWorkFlowBL.getResult();
		// LPEdorAppSchema temp1 = new LPEdorAppSchema();
		// temp1=
		// (LPEdorAppSchema)mResult.getObjectByObjectName("LPEdorAppSchema",0);
		// // logger.debug("getEdorAcceptNo:"+temp1.getEdorAcceptNo());
		logger.debug("method run successful");
		return true;
	}

	public VData getResult() {
		return mResult;
	}
	public CErrors getErrors() {
		return mErrors;
	}
}
