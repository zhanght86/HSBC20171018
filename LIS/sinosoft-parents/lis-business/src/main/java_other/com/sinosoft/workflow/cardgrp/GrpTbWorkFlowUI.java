package com.sinosoft.workflow.cardgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;

/**
 * <p>
 * Title: 团体工作流
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */

public class GrpTbWorkFlowUI implements BusinessService{
private static Logger logger = Logger.getLogger(GrpTbWorkFlowUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	public GrpTbWorkFlowUI() {
	}

	public static void main(String[] args) {
		VData tVData = new VData();
		GlobalInput mGlobalInput = new GlobalInput();
		TransferData mTransferData = new TransferData();

		/** 全局变量 */
		mGlobalInput.Operator = "001";
		mGlobalInput.ComCode = "86";
		mGlobalInput.ManageCom = "86";
		// 创建起始节点
		// mTransferData.setNameAndValue("ContNo", "00000000000000000123");
		// mTransferData.setNameAndValue("PrtNo", "00002004111456");
		// mTransferData.setNameAndValue("Operator", "001");
		// mTransferData.setNameAndValue("ManageCom", "86110000");
		// mTransferData.setNameAndValue("MakeDate", "2004-11-22");
		// mTransferData.setNameAndValue("AppntNo", "000003345");
		// mTransferData.setNameAndValue("AppntName", "张三");
		// mTransferData.setNameAndValue("AgentCode", "8611000433");
		// 执行起始节点
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
		// 自动核保
		// LCContSchema tLCContSchema = new LCContSchema();
		// tLCContSchema.setContNo( "99999020040990000036" );
		// mTransferData.setNameAndValue("LCContSchema",tLCContSchema);
		// mTransferData.setNameAndValue("MissionID", "00000000000000001100");
		// mTransferData.setNameAndValue("SubMissionID", "1");

		// 新单复核
		// mTransferData.setNameAndValue("ContNo", "88999999999999");
		// mTransferData.setNameAndValue("MissionID", "00000000000000006212");
		// mTransferData.setNameAndValue("SubMissionID", "1");

		// 复核修改
		// mTransferData.setNameAndValue("ContNo", "00000000000000000006");
		// mTransferData.setNameAndValue("MissionID", "00000000000000000166");
		// mTransferData.setNameAndValue("SubMissionID", "1");
		// 核保订正
		// mTransferData.setNameAndValue("ContNo", "130110000000139");
		// mTransferData.setNameAndValue("PrtNo", "21210000000009");
		// mTransferData.setNameAndValue("AgentCode", "8611000433");
		// mTransferData.setNameAndValue("MakeDate", "2004-11-22");
		// mTransferData.setNameAndValue("AppntNo", "0000031226");
		// mTransferData.setNameAndValue("AppntName", "张三");
		// mTransferData.setNameAndValue("InsuredNo", "0000031226");
		// mTransferData.setNameAndValue("InsuredName", "张三");
		// mTransferData.setNameAndValue("UWFlag", "a");
		// mTransferData.setNameAndValue("MissionID", "00000000000000001102");
		// mTransferData.setNameAndValue("SubMissionID", "4");

		/** 传递变量 */
		// mTransferData.setNameAndValue("InsuredNo","0000010748");
		// //发体检通知书测试
		// LCPENoticeSchema tLCPENoticeSchema = new LCPENoticeSchema();
		// tLCPENoticeSchema.setEdorNo("86110020040410000009");
		// tLCPENoticeSchema.setPolNo("86110020030210001687");
		// tLCPENoticeSchema.setPEAddress("11003001");
		// tLCPENoticeSchema.setPEDate("2003-07-01");
		// tLCPENoticeSchema.setPEBeforeCond("N");
		// tLCPENoticeSchema.setRemark("ReMark");
		// tLCPENoticeSchema.setInsuredNo("0000011456");
		// mTransferData.setNameAndValue("LCPENoticeSchema",tLCPENoticeSchema);
		//
		// LCPENoticeItemSet tLCPENoticeItemSet = new LCPENoticeItemSet();
		// LCPENoticeItemSchema tLCPENoticeItemSchema = new
		// LCPENoticeItemSchema();
		// tLCPENoticeItemSchema.setEdorNo("86110020040410000009");
		// tLCPENoticeItemSchema.setPolNo("86110020030210001687");
		// tLCPENoticeItemSchema.setInsuredNo("0000011456") ;
		// tLCPENoticeItemSchema.setPEItemCode( "001");
		// tLCPENoticeItemSchema.setPEItemName( "普检腹部B超");
		// tLCPENoticeItemSchema.setFreePE( "N");
		// LCPENoticeItemSchema tLCPENoticeItemSchema2 = new
		// LCPENoticeItemSchema();
		// tLCPENoticeItemSchema2.setEdorNo("86110020040410000009");
		// tLCPENoticeItemSchema2.setPolNo("86110020030210001687");
		// tLCPENoticeItemSchema2.setInsuredNo("0000011456") ;
		// tLCPENoticeItemSchema2.setPEItemCode( "002");
		// tLCPENoticeItemSchema2.setPEItemName( "普检腹部B超");
		// tLCPENoticeItemSchema2.setFreePE( "N");
		// tLCPENoticeItemSet.add( tLCPENoticeItemSchema );
		// tLCPENoticeItemSet.add( tLCPENoticeItemSchema2 );
		// mTransferData.setNameAndValue("LCPENoticeItemSet",tLCPENoticeItemSet);
		// mTransferData.setNameAndValue("PolNo","86110020030210001687");
		// mTransferData.setNameAndValue("EdorNo","86110020040410000009") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000000009");
		// mTransferData.setNameAndValue("SubMissionID","1");
		// mTransferData.setNameAndValue("InsuredNo","0000011456");
		// 发体检通知书测试
		// LCContSchema tLCContSchema = new LCContSchema();
		// LCPENoticeSchema tLCPENoticeSchema = new LCPENoticeSchema();
		// tLCContSchema.setContNo("00000000000000000005");
		// tLCContSchema.setInsuredNo("0000031226");
		// LCPENoticeSchema tLCPENoticeSchema = new LCPENoticeSchema();
		// tLCPENoticeSchema.setContNo("00000000000000000005");
		// tLCPENoticeSchema.setPEAddress("11003001");
		// tLCPENoticeSchema.setPEDate("2003-07-01");
		// tLCPENoticeSchema.setPEBeforeCond("N");
		// tLCPENoticeSchema.setRemark("ReMark");
		// tLCPENoticeSchema.setCustomerNo("0000031226");
		//
		// LCPENoticeItemSet tLCPENoticeItemSet = new LCPENoticeItemSet();
		// LCPENoticeItemSchema tLCPENoticeItemSchema = new
		// LCPENoticeItemSchema();
		// tLCPENoticeItemSchema.setContNo("00000000000000000005");
		// tLCPENoticeItemSchema.setPEItemCode( "001");
		// tLCPENoticeItemSchema.setPEItemName( "普检腹部B超");
		// tLCPENoticeItemSchema.setFreePE( "N");
		// LCPENoticeItemSchema tLCPENoticeItemSchema2 = new
		// LCPENoticeItemSchema();
		// tLCPENoticeItemSchema2.setContNo("00000000000000000005");
		// tLCPENoticeItemSchema2.setPEItemCode( "002");
		// tLCPENoticeItemSchema2.setPEItemName( "普检腹部B超");
		// tLCPENoticeItemSchema2.setFreePE( "N");
		// tLCPENoticeItemSet.add( tLCPENoticeItemSchema );
		// tLCPENoticeItemSet.add( tLCPENoticeItemSchema2 );
		//
		//
		// mTransferData.setNameAndValue("LCPENoticeItemSet",tLCPENoticeItemSet);
		// mTransferData.setNameAndValue("LCPENoticeSchema",tLCPENoticeSchema);
		// mTransferData.setNameAndValue("MissionID","00000000000000000001");
		// mTransferData.setNameAndValue("SubMissionID","1");
		// mTransferData.setNameAndValue("SubMissionID","1");
		// mTransferData.setNameAndValue("CustomerNo","0000031226");
		// mTransferData.setNameAndValue("ContNo","00000000000000000005");
		// 打印体检通知书
		// mTransferData.setNameAndValue("PrtSeq","86000020040810000147");
		// mTransferData.setNameAndValue("Code","03") ;
		// mTransferData.setNameAndValue("ContNo","00000000000000000005") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000000001") ;
		// mTransferData.setNameAndValue("SubMissionID","1") ;
		// 回收体检通知书
		// LZSysCertifySchema tLZSysCertifySchema = new LZSysCertifySchema();
		// tLZSysCertifySchema.setCertifyCode( "8888" );
		// tLZSysCertifySchema.setCertifyNo( "86000020040810000147" );
		// tLZSysCertifySchema.setTakeBackOperator( "001" );
		// tLZSysCertifySchema.setTakeBackDate( "2004-03-31" );
		// tLZSysCertifySchema.setTakeBackMakeDate( "2004-03-31" );
		// tLZSysCertifySchema.setSendOutCom( "A86" );
		// tLZSysCertifySchema.setReceiveCom( "D8611000433" );
		// String tOperate = new String();
		// TransferData tTransferData = new TransferData();
		// mTransferData.setNameAndValue("CertifyNo","86000020040810000147");
		// mTransferData.setNameAndValue("CertifyCode","8888") ;
		// mTransferData.setNameAndValue("ContNo","00000000000000000005") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000000001") ;
		// mTransferData.setNameAndValue("SubMissionID","1") ;
		// mTransferData.setNameAndValue("LZSysCertifySchema",tLZSysCertifySchema);
		// //生调通知书测试
		// LCRReportSchema tLCRReportSchema = new LCRReportSchema();
		// tLCRReportSchema.setContNo("00000000000000000006");
		// tLCRReportSchema.setContente("我的生调内容：哈哈");
		// mTransferData.setNameAndValue("ContNo","00000000000000000006");
		// mTransferData.setNameAndValue("PrtNo","00002004111207") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000000001");
		// mTransferData.setNameAndValue("CustomerNo","0000031226");
		// mTransferData.setNameAndValue("SubMissionID","1");
		// mTransferData.setNameAndValue("LCRReportSchema",tLCRReportSchema);
		// 打印生调通知书
		// mTransferData.setNameAndValue("PrtSeq", "86000020040810000157");
		// mTransferData.setNameAndValue("Code", "04");
		// mTransferData.setNameAndValue("ContNo", "00000000000000000006");
		// mTransferData.setNameAndValue("PrtNo", "00002004111207");
		// mTransferData.setNameAndValue("MissionID", "00000000000000000001");
		// mTransferData.setNameAndValue("SubMissionID", "1");

		// **打印新契约问题件通知书
		// mTransferData.setNameAndValue("PrtSeq","810000000001228");
		// mTransferData.setNameAndValue("Code","56") ;
		// mTransferData.setNameAndValue("GrpContNo","140110000000697") ;
		// mTransferData.setNameAndValue("PrtNo","000517001") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000006116") ;
		// mTransferData.setNameAndValue("SubMissionID","2") ;
		// **补打团单新契约客户合并通知书
		/*
		 * // LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema(); //
		 * tLOPRTManagerSchema.setPrtSeq("810000000001219") ; //
		 * mTransferData.setNameAndValue("Code","56") ; //
		 * mTransferData.setNameAndValue("GrpContNo","140110000000697") ; //
		 * mTransferData.setNameAndValue("MissionID","00000000000000006116") ; //
		 * mTransferData.setNameAndValue("SubMissionID","1") ; //
		 * mTransferData.setNameAndValue("LOPRTManagerSchema",tLOPRTManagerSchema) ;
		 */
		// //回收团单新契约通知书
		/*
		 * LZSysCertifySchema tLZSysCertifySchema = new LZSysCertifySchema();
		 * tLZSysCertifySchema.setCertifyCode("0056");
		 * tLZSysCertifySchema.setCertifyNo("810000000001228");
		 * mTransferData.setNameAndValue("GrpContNo", "140110000000697");
		 * mTransferData.setNameAndValue("MissionID", "00000000000000006116");
		 * mTransferData.setNameAndValue("SubMissionID", "2");
		 * mTransferData.setNameAndValue("PrtNo", "000517001");
		 * mTransferData.setNameAndValue("CertifyCode", "0056");
		 * mTransferData.setNameAndValue("CertifyNo", "810000000001228");
		 * mTransferData.setNameAndValue("PrtSeq", "810000000001228");
		 * 
		 * 
		 * mTransferData.setNameAndValue("LZSysCertifySchema",tLZSysCertifySchema);
		 * 
		 */

		// //加费录入测试
		// //生调通知书测试
		// //发送核保通知书
		// mTransferData.setNameAndValue("ContNo","130110000000174");
		// mTransferData.setNameAndValue("PrtNo","00002004120211") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000001119");
		// mTransferData.setNameAndValue("SubMissionID","1");
		// 下核保结论
		// mTransferData.setNameAndValue("ContNo","130110000000188");
		// mTransferData.setNameAndValue("PrtNo","21210000000111") ;
		// mTransferData.setNameAndValue("UWFlag","1") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000001127");
		// mTransferData.setNameAndValue("SubMissionID","1");
		// 机构问题件回收
		// mTransferData.setNameAndValue("ContNo","130110000000175");
		// mTransferData.setNameAndValue("MissionID","00000000000000001120");
		// mTransferData.setNameAndValue("SubMissionID","1");

		// 补核保通知书
		// LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		// tLOPRTManagerSchema.setPrtSeq("810000000000035") ;
		// mTransferData.setNameAndValue("Code","03") ;
		// mTransferData.setNameAndValue("ContNo","00000000000000000006") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000000001") ;
		// mTransferData.setNameAndValue("SubMissionID","1") ;
		// mTransferData.setNameAndValue("LOPRTManagerSchema",tLOPRTManagerSchema)
		// ;
		// 准备传输工作流数据 VData
		// mTransferData.setNameAndValue("PolNo","86110020030210001017");
		// mTransferData.setNameAndValue("EdorNo","86110020030410000195");
		// mTransferData.setNameAndValue("MissionID","00000000000000000017");
		// mTransferData.setNameAndValue("LCUWMasterMainSchema",mLCUWMasterMainSchema);
		//
		// 回收核保通知书
		// LZSysCertifySchema tLZSysCertifySchema = new LZSysCertifySchema();
		// tLZSysCertifySchema.setCertifyCode( "9999" );
		// tLZSysCertifySchema.setCertifyNo( "86000020040810000190" );
		// tLZSysCertifySchema.setTakeBackOperator( "001" );
		// tLZSysCertifySchema.setTakeBackDate( "2004-12-01" );
		// tLZSysCertifySchema.setTakeBackMakeDate( "2004-12-01" );
		// tLZSysCertifySchema.setSendOutCom( "A86" );
		// tLZSysCertifySchema.setReceiveCom( "D8611000439 " );
		// String tOperate = new String();
		// TransferData tTransferData = new TransferData();
		// mTransferData.setNameAndValue("CertifyNo","86000020040810000190");
		// mTransferData.setNameAndValue("CertifyCode","9999") ;
		// mTransferData.setNameAndValue("ContNo","130110000000139") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000001102") ;
		// mTransferData.setNameAndValue("SubMissionID","4") ;
		// mTransferData.setNameAndValue("LZSysCertifySchema",tLZSysCertifySchema);
		//
		// //打印业务员通知书
		// mTransferData.setNameAndValue("PrtSeq","810000000000010");
		// mTransferData.setNameAndValue("Code","14") ;
		// mTransferData.setNameAndValue("ContNo","00000000000000000006") ;
		// mTransferData.setNameAndValue("PrtNo","00002004111207") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000000001") ;
		// mTransferData.setNameAndValue("SubMissionID","1") ;
		// 补业务员通知书
		// LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		// tLOPRTManagerSchema.setPrtSeq("810000000000010") ;
		// mTransferData.setNameAndValue("Code","14") ;
		// mTransferData.setNameAndValue("ContNo","00000000000000000006") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000000001") ;
		// mTransferData.setNameAndValue("SubMissionID","1") ;
		// mTransferData.setNameAndValue("LOPRTManagerSchema",tLOPRTManagerSchema)
		// ;
		// //回收业务员通知书
		// LZSysCertifySchema tLZSysCertifySchema = new LZSysCertifySchema();
		// tLZSysCertifySchema.setCertifyCode( "9996" );
		// tLZSysCertifySchema.setCertifyNo( "810000000000010" );
		// tLZSysCertifySchema.setTakeBackOperator( "001" );
		// tLZSysCertifySchema.setTakeBackDate( "2004-03-31" );
		// tLZSysCertifySchema.setTakeBackMakeDate( "2004-03-31" );
		// tLZSysCertifySchema.setSendOutCom( "A86" );
		// tLZSysCertifySchema.setReceiveCom( "D8611000434 " );
		// String tOperate = new String();
		// TransferData tTransferData = new TransferData();
		// mTransferData.setNameAndValue("CertifyNo","810000000000010");
		// mTransferData.setNameAndValue("CertifyCode","9996") ;
		// mTransferData.setNameAndValue("ContNo","00000000000000000006") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000000001") ;
		// mTransferData.setNameAndValue("SubMissionID","1") ;
		// mTransferData.setNameAndValue("LZSysCertifySchema",tLZSysCertifySchema);
		// 复核修改确认
		// mTransferData.setNameAndValue("ContNo", "86110020040990000060");
		// mTransferData.setNameAndValue("PrtNo", "21210000000011");
		// mTransferData.setNameAndValue("MissionID", "00000000000000000193");
		// mTransferData.setNameAndValue("SubMissionID", "2");
		// //精算数据导入
		// mTransferData.setNameAndValue("FileName","Data000000JS2004062.xls");
		// mTransferData.setNameAndValue("ConfigFileName","ExcelImportLFJSConfig.xml");
		// mTransferData.setNameAndValue("ItemType","04") ;
		// mTransferData.setNameAndValue("StatYear","2004") ;
		// mTransferData.setNameAndValue("StatMon","01");
		// mTransferData.setNameAndValue("MissionID","00000000000000000116");
		// mTransferData.setNameAndValue("SubMissionID","1");00000000000000000117
		// //精算数据导入确认
		// mTransferData.setNameAndValue("FileName","Data000000JS2004062.xls");
		// mTransferData.setNameAndValue("ConfigFileName","ExcelImportLFJSConfig.xml");
		// mTransferData.setNameAndValue("ItemType","07") ;
		// mTransferData.setNameAndValue("StatYear","2004") ;
		// mTransferData.setNameAndValue("StatMon","01");
		// mTransferData.setNameAndValue("MissionID","00000000000000000117");
		// mTransferData.setNameAndValue("SubMissionID","1");
		// //业务数据导入确认
		// mTransferData.setNameAndValue("FileName","Data000000JS2004062.xls");
		// mTransferData.setNameAndValue("ConfigFileName","ExcelImportLFJSConfig.xml");
		// mTransferData.setNameAndValue("ItemType","01") ;
		// mTransferData.setNameAndValue("StatYear","2004") ;
		// mTransferData.setNameAndValue("StatMon","07");
		// mTransferData.setNameAndValue("MissionID","00000000000000000117");
		// mTransferData.setNameAndValue("SubMissionID","1");
		//
		// LCContSchema tLCContSchema = new LCContSchema();
		// tLCContSchema.setContNo("00000000000000000005");
		// mTransferData.setNameAndValue("LCContSchema", tLCContSchema);
		// mTransferData.setNameAndValue("MissionID", "00000000000000000001");
		// mTransferData.setNameAndValue("SubMissionID", "1");
		//
		// 准备公共传输信息
		// mTransferData.setNameAndValue("WhereSQL", " || ||AND ItemType In
		// ('X1','X2','X3','X4','X5','X6','X7') AND ItemCode in(select itemcode
		// from LFItemRela where OutPutFlag='1' and IsMon='1') order by
		// ItemNum");
		// mTransferData.setNameAndValue("NeedItemKey", "1");
		// mTransferData.setNameAndValue("ReportDate", "2003-10-01");
		// mTransferData.setNameAndValue("makedate","2004-07-12");
		// mTransferData.setNameAndValue("maketime","10:10:10");
		// mTransferData.setNameAndValue("sDate", "2003-10-01");
		// mTransferData.setNameAndValue("eDate", "2003-10-31");
		//
		// //准备公共传输信息
		// mTransferData.setNameAndValue("PolNo","86110020030210004500");
		// mTransferData.setNameAndValue("EdorNo","86110020030410000213") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000000118");
		// mTransferData.setNameAndValue("PrtSeq","86000020030810002084");
		// mTransferData.setNameAndValue("SubMissionID","3");
		// mTransferData.setNameAndValue("LCRReportSchema",tLCRReportSchema);
		// 准备传输工作流数据 VData
		// mTransferData.setNameAndValue("StatYear","2004") ;
		// mTransferData.setNameAndValue("StatMonth","01");

		// mTransferData.setNameAndValue("MissionID","00000000000000000110");
		// mTransferData.setNameAndValue("SubMissionID","1");
		// VData tVData3 = new VData();
		// mTransferData.setNameAndValue("PolNo","86110020030210035008");
		// mTransferData.setNameAndValue("EdorNo","86110020040410000039") ;
		// mTransferData.setNameAndValue("MissionID","00000000000000000113");
		// mTransferData.setNameAndValue("SubMissionID","1");
		/** 签单测试 */
		// String[] testContNo = {"86110020040990000042"};
		// LCContSet tLCContSet = new LCContSet ();
		// for ( int i=0;i< testContNo.length;i++){
		// LCContSchema tLCContSchema = new LCContSchema();
		// tLCContSchema.setContNo(testContNo[i]);
		// tLCContSet.add( tLCContSchema );
		// }
		// mTransferData.setNameAndValue("MissionID","00000000000000000174");
		// mTransferData.setNameAndValue("SubMissionID","1");
		// //下核保结论
		// LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
		// tLCUWMasterSchema.setProposalNo("86110020040110000987");
		// tLCUWMasterSchema.setUWIdea("延期承保");
		// tLCUWMasterSchema.setPostponeDay("2天");
		// tLCUWMasterSchema.setPassFlag("2");
		// 准备传输工作流数据 VData
		// mTransferData.setNameAndValue("ContNo","130110000001189");
		// mTransferData.setNameAndValue("PrtNo","21210000000072");
		// mTransferData.setNameAndValue("AppUser","001");
		// mTransferData.setNameAndValue("UWFlag","1");
		// mTransferData.setNameAndValue("UWIdea","asdf");
		// mTransferData.setNameAndValue("MissionID","00000000000000001155");
		// mTransferData.setNameAndValue("SubMissionID","1");
		// mTransferData.setNameAndValue("LCUWMasterSchema",tLCUWMasterSchema);
		// 准备团单新契约开始结点
		// mTransferData.setNameAndValue("ProposalGrpContNo",
		// "140110000000698");
		// mTransferData.setNameAndValue("PrtNo", "000517002");
		// mTransferData.setNameAndValue("SaleChnl", "01");
		// mTransferData.setNameAndValue("AgentCode","00000033");
		// mTransferData.setNameAndValue("AgentGroup","000000000712");
		// mTransferData.setNameAndValue("ManageCom", "86110000");
		// mTransferData.setNameAndValue("GrpName", "testing");
		// mTransferData.setNameAndValue("CValiDate", "2004-12-17");
		// mTransferData.setNameAndValue("MissionID","null");
		// mTransferData.setNameAndValue("SubMissionID","null");
		// 准备团单新单复核节点
		mTransferData.setNameAndValue("ProposalGrpContNo", "99888888888888");
		mTransferData.setNameAndValue("GrpContNo", "99888888888888");
		mTransferData.setNameAndValue("PrtNo", "99888888888888");
		mTransferData.setNameAndValue("SaleChnl", "01");
		mTransferData.setNameAndValue("ManageCom", "86110000");
		mTransferData.setNameAndValue("GrpName", "1212");
		mTransferData.setNameAndValue("CValiDate", "2005-05-26");
		mTransferData.setNameAndValue("MissionID", "00000000000000006218");
		mTransferData.setNameAndValue("SubMissionID", "1");

		// 准备团单自核节点
		// LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		// LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		// tLCGrpContSchema.setGrpContNo("120110000001171");
		// mTransferData.setNameAndValue("MissionID", "00000000000000000381");
		// mTransferData.setNameAndValue("SubMissionID", "1");
		// tLCGrpContSet.add(tLCGrpContSchema);
		// 准备团单人工核保节点
		// LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		// LCGrpContSet tLCGrpContSet =new LCGrpContSet();
		//
		// tLCGrpContSchema.setGrpContNo("120110000000167");
		// tLCGrpContSchema.setUWFlag("4");
		// tLCGrpContSchema.setRemark("dsafdfa");
		// tLCGrpContSet.add(tLCGrpContSchema);
		// mTransferData.setNameAndValue("MissionID", "00000000000000001187");
		// mTransferData.setNameAndValue("SubMissionID", "1");
		// 准备团单人工核保节点
		// LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		// LCGrpContSet tLCGrpContSet =new LCGrpContSet();
		//
		// tLCGrpContSchema.setGrpContNo("140110000000199");
		// tLCGrpContSchema.setUWFlag("9");
		// tLCGrpContSchema.setRemark("dsafdfa");
		// tLCGrpContSet.add(tLCGrpContSchema);
		// mTransferData.setNameAndValue("MissionID", "00000000000000000626");
		// mTransferData.setNameAndValue("SubMissionID", "2");
		// 准备团单核保订正节点
		// mTransferData.setNameAndValue("GrpContNo","140110000000698");
		// mTransferData.setNameAndValue("MissionID", "00000000000000006117");
		// mTransferData.setNameAndValue("SubMissionID", "1");

		// 准备团单Sign核保节点
		// LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		//
		// tLCGrpContSchema.setGrpContNo("140110000000116");
		//
		// mTransferData.setNameAndValue("MissionID", "00000000000000000119");
		// mTransferData.setNameAndValue("SubMissionID", "1");
		// 创建工作流节点0000011230，add by zhangxing
		/*
		 * mTransferData.setNameAndValue("mGlobalInput", mGlobalInput);
		 * mTransferData.setNameAndValue("GrpContNo", "140110000000697");
		 * mTransferData.setNameAndValue("PrtNo", "000517001");
		 * 
		 * mTransferData.setNameAndValue("AgentCode", "00000033");
		 * mTransferData.setNameAndValue("AgentGroup", "000000000712");
		 * mTransferData.setNameAndValue("BranchAttr", "");
		 * mTransferData.setNameAndValue("ManageCom", "86110000");
		 * 
		 * mTransferData.setNameAndValue("MissionID", "00000000000000006116");
		 * mTransferData.setNameAndValue("SubMissionID", "1");
		 * 
		 * 
		 */
		/** 总变量 */
		tVData.add(mGlobalInput);
		// tVData.add( tLCGrpContSet );
		// tVData.add(tLCGrpContSchema);
		tVData.add(mTransferData);

		GrpTbWorkFlowUI tGrpTbWorkFlowUI = new GrpTbWorkFlowUI();
		try {
			if (tGrpTbWorkFlowUI.submitData(tVData, "0000011001")) {
				VData tResult = new VData();

				// tResult = tActivityOperator.getResult() ;
			} else {
				logger.debug(tGrpTbWorkFlowUI.mErrors.getError(0).errorMessage);
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

		GrpTbWorkFlowBL tGrpTbWorkFlowBL = new GrpTbWorkFlowBL();

		logger.debug("---GrpTbWorkFlowBL UI BEGIN---");
		if (tGrpTbWorkFlowBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGrpTbWorkFlowBL.mErrors);
			mResult.clear();
			return false;
		}
		return true;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return mResult;
	}
}
