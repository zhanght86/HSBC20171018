package com.sinosoft.workflow.xb;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LZSysCertifySchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title:新契约工作流
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

public class RnewWorkFlowUI implements BusinessService{
private static Logger logger = Logger.getLogger(RnewWorkFlowUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;
	private HttpServletRequest httprequest;

	public RnewWorkFlowUI() {
	}

	public static void main(String[] args) {
		VData tVData = new VData();
		GlobalInput mGlobalInput = new GlobalInput();
		TransferData mTransferData = new TransferData();

		/** 全局变量 */
		mGlobalInput.Operator = "001";
		mGlobalInput.ComCode = "86";
		mGlobalInput.ManageCom = "86";
		// 撤销保单

		// //补打客户合并通知书
		// LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		// tLOPRTManagerSchema.setPrtSeq("810000000001056");
		// mTransferData.setNameAndValue("Code", "84");
		// // mTransferData.setNameAndValue("ContNo", "130110000014018");
		// // mTransferData.setNameAndValue("MissionID",
		// "00000000000000005872");
		// mTransferData.setNameAndValue("SubMissionID", "4");
		// mTransferData.setNameAndValue("LOPRTManagerSchema",
		// tLOPRTManagerSchema);
		// mTransferData.setNameAndValue("PrtSeq", "810000000001056");
		// //回收客户合并更通知书
		LZSysCertifySchema tLZSysCertifySchema = new LZSysCertifySchema();
		tLZSysCertifySchema.setCertifyCode("9996");
		tLZSysCertifySchema.setCertifyNo("810000000001741");
		tLZSysCertifySchema.setTakeBackOperator("001");
		tLZSysCertifySchema.setTakeBackDate("2005-04-28");
		tLZSysCertifySchema.setTakeBackMakeDate("2005-04-28");
		tLZSysCertifySchema.setSendOutCom("A86");
		tLZSysCertifySchema.setReceiveCom("D8611000439 ");

		mTransferData.setNameAndValue("CertifyNo", "810000000001741");
		mTransferData.setNameAndValue("CertifyCode", "9996");
		mTransferData.setNameAndValue("ContNo", "00100000000058");
		mTransferData.setNameAndValue("MissionID", "00000000000000008345");
		mTransferData.setNameAndValue("SubMissionID", "1");
		mTransferData
				.setNameAndValue("LZSysCertifySchema", tLZSysCertifySchema);
		//
		// //执行起始节点
		// mTransferData.setNameAndValue("ContNo", "00000000000000000006");
		// mTransferData.setNameAndValue("PrtNo", "00002004111207");
		// mTransferData.setNameAndValue("Operator", "001");
		// mTransferData.setNameAndValue("ApproveCode", "001");
		// mTransferData.setNameAndValue("MakeDate", "2004-11-22");
		// mTransferData.setNameAndValue("AppntNo", "0000031226");
		// mTransferData.setNameAndValue("AppntName", "张三");
		// mTransferData.setNameAndValue("InsuredNo", "0000031226");
		// mTransferData.setNameAndValue("InsuredName", "张三");
		// mTransferData.setNameAndValue("MissionID", "00000000000000000166");
		// mTransferData.setNameAndValue("SubMissionID", "1");
		// /** 传递变量 */
		// //自动核保
		// LCContSchema tLCContSchema = new LCContSchema();
		// tLCContSchema.setContNo("99999020040990000036");
		// mTransferData.setNameAndValue("LCContSchema", tLCContSchema);
		// mTransferData.setNameAndValue("MissionID", "00000000000000000200");
		// mTransferData.setNameAndValue("SubMissionID", "1");
		// //无扫描录入
		// mTransferData.setNameAndValue("ContNo", "130110000014077");
		//
		// mTransferData.setNameAndValue("MissionID", "00000000000000005986");
		//
		// //回收体检通知书
		//
		// LZSysCertifySchema tLZSysCertifySchema = new LZSysCertifySchema();
		// tLZSysCertifySchema.setCertifyCode("1113");
		// tLZSysCertifySchema.setCertifyNo("810000000001359");
		// tLZSysCertifySchema.setTakeBackDate("2004-03-31");
		// tLZSysCertifySchema.setTakeBackMakeDate("2004-03-31");
		// tLZSysCertifySchema.setSendOutCom("A86");
		// tLZSysCertifySchema.setReceiveCom("D86110484");
		// String tOperate = new String();
		// TransferData tTransferData = new TransferData();
		// mTransferData.setNameAndValue("CertifyNo", "810000000001359");
		// mTransferData.setNameAndValue("CertifyCode", "0088");
		// mTransferData.setNameAndValue("ContNo", "05059999999999");
		// mTransferData.setNameAndValue("MissionID", "00000000000000006424");
		// mTransferData.setNameAndValue("SubMissionID", "3");
		// mTransferData.setNameAndValue("LZSysCertifySchema",
		// tLZSysCertifySchema);

		// // LCContSet tLCContSet = new LCContSet();
		// // LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();
		// // LCContSchema tLCContSchema = new LCContSchema();
		// // LCCUWMasterSchema tLCCUWMasterSchema = new LCCUWMasterSchema();
		// //个人合同核保最近结果表
		//
		// tLCContSchema.setPolNo(tContNo);
		// tLCContSchema.setContNo("130110000013775");
		// tLCContSchema.setUWFlag("9"); //核保结论
		// tLCContSchema.setRemark("dfdf"); //核保意见
		//
		// tLCCUWMasterSchema.setContNo("130110000013775");
		// tLCCUWMasterSchema.setUWIdea("dfdf"); //核保意见
		// tLCCUWMasterSchema.setSugPassFlag("9"); //核保结论
		// tLCCUWMasterSchema.setSugUWIdea("fg"); //核保意见
		//
		// tLCContSet.add(tLCContSchema);
		// tLCCUWMasterSet.add(tLCCUWMasterSchema);
		// TransferData nTransferData = new TransferData();
		// nTransferData.setNameAndValue("ContNo", "130110000013775");
		// nTransferData.setNameAndValue("PrtNo", "20050325005");
		// nTransferData.setNameAndValue("UWFlag", "9");
		// nTransferData.setNameAndValue("UWIdea", "dfdf");
		// nTransferData.setNameAndValue("MissionID", "00000000000000005168");
		// nTransferData.setNameAndValue("SubMissionID", "1");
		// 录入体检结论

		// TransferData tTransferData = new TransferData();
		//
		// LCPENoticeItemSet tLCPENoticeItemSet = new LCPENoticeItemSet();
		// LCPENoticeItemSchema tLCPENoticeItemSchema = new
		// LCPENoticeItemSchema();
		// tLCPENoticeItemSchema.setContNo("00000000000008");
		// tLCPENoticeItemSchema.setPrtSeq("810000000001672");
		// tLCPENoticeItemSchema.setProposalContNo("00000000000008");
		// tLCPENoticeItemSchema.setPEItemCode("01");
		// tLCPENoticeItemSchema.setPEItemName("aa");
		// tLCPENoticeItemSchema.setPEItemResult("abc");
		// tLCPENoticeItemSet.add(tLCPENoticeItemSchema);
		//
		// LCPENoticeSchema tLCPENoticeSchema = new LCPENoticeSchema();
		// LCPENoticeSet tLCPENoticeSet = new LCPENoticeSet();
		// tLCPENoticeSchema.setContNo("00000000000008");
		// tLCPENoticeSchema.setPrtSeq("810000000001672");
		// tLCPENoticeSchema.setCustomerNo("0000001121");
		// tLCPENoticeSchema.setPEResult("abc");
		// tLCPENoticeSchema.setPEAddress("aa");
		// tLCPENoticeSchema.setPEDoctor("cc");
		// tLCPENoticeSchema.setPEDate("2005-05-30");
		// tLCPENoticeSchema.setREPEDate("2005-05-30");
		// tLCPENoticeSet.add(tLCPENoticeSchema);
		//
		// LCPENoticeResultSet tLCPENoticeResultSet = new LCPENoticeResultSet();
		// LCPENoticeResultSchema tLCPENoticeResultSchema = new
		// LCPENoticeResultSchema();
		// tLCPENoticeResultSchema.setContNo("00000000000008");
		// tLCPENoticeResultSchema.setProposalContNo("00000000000008");
		// tLCPENoticeResultSchema.setPrtSeq("810000000001672");
		// tLCPENoticeResultSchema.setCustomerNo("0000001121");
		// tLCPENoticeResultSchema.setDisDesb("bb");
		// tLCPENoticeResultSchema.setDisResult("cc");
		// // tLCPENoticeResultSchema.setICDCode("");
		// tLCPENoticeResultSet.add(tLCPENoticeResultSchema);
		// tTransferData.setNameAndValue("LCPENoticeItemSet",tLCPENoticeItemSet);
		// tTransferData.setNameAndValue("LCPENoticeSet",tLCPENoticeSet);
		// tTransferData.setNameAndValue("LCPENoticeResultSet",tLCPENoticeResultSet);
		// tTransferData.setNameAndValue("MissionID","00000000000000007244");
		// tTransferData.setNameAndValue("SubMissionID","1");
		// tTransferData.setNameAndValue("PrtNo","00000000000008");
		// tTransferData.setNameAndValue("ContNo","00000000000008");
		// tTransferData.setNameAndValue("PrtSeq","810000000001672");
		// tTransferData.setNameAndValue("CustomerNo","0000314430");

		//
		// mTransferData.setNameAndValue("ContNo","05022222222222");
		// mTransferData.setNameAndValue("Code","88");
		// mTransferData.setNameAndValue("CustomerNo","0000314500");
		// mTransferData.setNameAndValue("MissionID","00000000000000006293" );
		// mTransferData.setNameAndValue("SubMissionID","3" );
		// mTransferData.setNameAndValue("RReportNo","810000000001411");
		// 生调录入
		// LCRReportSchema tLCRReportSchema = new LCRReportSchema();
		// tLCRReportSchema.setContNo("00200503300000");
		//
		//
		// //准备公共传输信息
		// mTransferData.setNameAndValue("ContNo","00200503300000");
		// mTransferData.setNameAndValue("PrtNo","00200503300000") ;
		// mTransferData.setNameAndValue("CustomerNo","0000497430") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000006272");
		// mTransferData.setNameAndValue("SubMissionID","1");
		// mTransferData.setNameAndValue("LCRReportSchema",tLCRReportSchema);
		//
		// LCRReportItemSchema tLCRReportItemSchema = new LCRReportItemSchema();
		// LCRReportItemSet tLCRReportItemSet = new LCRReportItemSet();
		//
		// tLCRReportItemSchema.setRReportItemCode("00001");
		// tLCRReportItemSchema.setRReportItemName("fdjlfjldj");
		//
		// tLCRReportItemSet.add(tLCRReportItemSchema);
		//
		//
		// mTransferData.setNameAndValue("LCRReportItemSet",tLCRReportItemSet);
		// 打印通知书
		// mTransferData.setNameAndValue("PrtSeq","810000000001359");
		// mTransferData.setNameAndValue("Code","04") ;
		// mTransferData.setNameAndValue("ContNo","00000000000000000006") ;
		// mTransferData.setNameAndValue("PrtNo","00002004111207") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000000001") ;

		// 声调处理

		// mTransferData.setNameAndValue("ContNo","05092222222222");
		// mTransferData.setNameAndValue("Code","04");
		// mTransferData.setNameAndValue("CustomerNo","0000498010");
		// mTransferData.setNameAndValue("MissionID","00000000000000006604");
		// mTransferData.setNameAndValue("SubMissionID","3" );
		// mTransferData.setNameAndValue("PrtSeq","810000000001432");
		// LCRReportPrtSchema tLCRReportPrtSchema = new LCRReportPrtSchema();
		// LCRReportPrtSet tLCRReportPrtSet = new LCRReportPrtSet();
		// tLCRReportPrtSchema.setAskCode("87");
		//
		// tLCRReportPrtSet.add(tLCRReportPrtSchema);
		//
		//
		// mTransferData.setNameAndValue("LCRReportPrtSet",tLCRReportPrtSet);
		// 补打生调通知书
		// LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		// tLOPRTManagerSchema.setPrtSeq("810000000001432");
		// mTransferData.setNameAndValue("ContNo","05092222222222");
		// mTransferData.setNameAndValue("Code","04");
		// mTransferData.setNameAndValue("PrtNo","05092222222222");
		// mTransferData.setNameAndValue("MissionID","00000000000000006604");
		// mTransferData.setNameAndValue("SubMissionID","1");
		// mTransferData.setNameAndValue("LOPRTManagerSchema",tLOPRTManagerSchema);

		// //生调回销
		// LCRReportSchema tLCRReportSchema = new LCRReportSchema();
		// tLCRReportSchema.setReplyContente("1346464");
		// tLCRReportSchema.setProposalContNo("05081111111111");
		// tLCRReportSchema.setPrtSeq("810000000001445");
		// // tLCRReportSchema.setCustomerNo("00000000000000006604");
		//
		//
		//
		// mTransferData.setNameAndValue("LCRReportSchema",tLCRReportSchema);
		//
		// mTransferData.setNameAndValue("PrtNo","05081111111111");
		// mTransferData.setNameAndValue("ContNo","05081111111111");
		//
		// mTransferData.setNameAndValue("MissionID","00000000000000006495");
		// mTransferData.setNameAndValue("SubMissionID","1");

		// //提起再保呈报
		// LCReinsureReportSchema tLCReinsureReportSchema = new
		// LCReinsureReportSchema();
		// tLCReinsureReportSchema.setContNo("00200506210000");
		// tLCReinsureReportSchema.setReportRemark("dfjfljkdjfldjfl");
		// mTransferData.setNameAndValue("LCReinsureReportSchema",tLCReinsureReportSchema);
		//
		//
		// mTransferData.setNameAndValue("ContNo","00200506210000");
		//
		// mTransferData.setNameAndValue("MissionID","00000000000000006905");
		// mTransferData.setNameAndValue("SubMissionID","7");

		// 再保呈报处理
		// LCReinsureReportSchema tLCReinsureReportSchema = new
		// LCReinsureReportSchema();
		// tLCReinsureReportSchema.setContNo("00200506210000");
		// tLCReinsureReportSchema.setReinsuredResult("1");
		// tLCReinsureReportSchema.setReinsuDesc("dfjfljkdjfldjfl");
		// tLCReinsureReportSchema.setReinsuRemark("ddddddddddddddd");
		// mTransferData.setNameAndValue("LCReinsureReportSchema",tLCReinsureReportSchema);
		//
		//
		// mTransferData.setNameAndValue("ContNo","00200506210000");
		//
		// mTransferData.setNameAndValue("MissionID","00000000000000006905");
		// mTransferData.setNameAndValue("SubMissionID","1");

		tVData.clear();

		tVData.add(mTransferData);
		// tVData.add(tLCRReportSchema);

		/** 总变量 */
		tVData.add(mGlobalInput);

		RnewWorkFlowUI tRnewWorkFlowUI = new RnewWorkFlowUI();
		try {
			if (tRnewWorkFlowUI.submitData(tVData, "0000001250")) {
				VData tResult = new VData();

			} else {
				logger.debug(tRnewWorkFlowUI.mErrors.getError(0).errorMessage);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		RnewWorkFlowBL tRnewWorkFlowBL = new RnewWorkFlowBL();

		logger.debug("---RnewWorkFlowBL UI BEGIN---");
		if (tRnewWorkFlowBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tRnewWorkFlowBL.mErrors);
			mResult.clear();
			return false;
		}
		mResult = tRnewWorkFlowBL.getResult();
		return true;
	}

	/**
	 * 传输数据的公共方法
	 */
	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}

}
