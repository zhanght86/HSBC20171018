package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;
import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xpath.XPathAPI;
import org.apache.xpath.objects.XObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sinosoft.lis.bq.GEdorDetailUI;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LMDutyPayDB;
import com.sinosoft.lis.db.LMDutyPayRelaDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.InsuAccBala;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPAccMoveSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPGrpEdorImportLogSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPInsureAccClassSchema;
import com.sinosoft.lis.tb.GrpPolImpInfo;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPAccMoveSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XMLPathTool;

/**
 * <p>Title:GrpEdorGBForLoadBL.java </p>
 * <p>Description: GB导入</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:sinosoft </p>
 * @author
 * @version 1.0
 * @date 2006-07-05
 */
public class GrpEdorGAForLoadBL implements BusinessService{
private static Logger logger = Logger.getLogger(GrpEdorGAForLoadBL.class);
    public GrpEdorGAForLoadBL() {
        bulidDocument();
    }

    //错误处理类
    public CErrors mErrors = new CErrors();
    private VData mInputData = new VData();
    /** 往前面传输数据的容器 */
    private VData mResult = new VData();
    private GrpPolImpInfo m_GrpPolImpInfo = new GrpPolImpInfo();
    private String FileName; // execl 文件名称
    private String XmlFileName; // xml 文件名称
    private String mPreFilePath; // 文件路径
    private String FilePath = "d:/temp/";
    private String ParsePath = "/DATASET/LLCASETABLE/ROW";
    //配置文件Xml节点描述
    private String ImportFileName;
    private String ConfigFileName;
    private String[] m_strDataFiles = null;
    private GlobalInput mGlobalInput = new GlobalInput();
    private TransferData mTransferData = new TransferData();
    private org.w3c.dom.Document m_doc = null;
    private Reflections mRef = new Reflections();
    private String mBatchNo = "";
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private String mGrpContNo= "";
    private String mEdorNo = "";
    private String mEdorType = "";
    private String mEdorAcceptNo="";
    private String mContNo = "";
    private String mPolNo = "";
    
    MMap mMap = new MMap();
    public boolean submitData(VData cInputData, String cOperate) {
           mInputData = (VData) cInputData.clone();
           //得到数据
           if (!getInputData()) {
               return false;
           }
           logger.debug("开始时间:" + PubFun.getCurrentTime());
           try {
               //把Excel转化为Xml
               if (!parseVts()) {
                   return false;
               }
               for (int nIndex = 0; nIndex < m_strDataFiles.length; nIndex++) {
                   //把Xml转化为Schema
                   XmlFileName = m_strDataFiles[nIndex];
                   if (!ParseXml()) {
                       return false;
                   }
               }
           } catch (Exception ex) {
               ex.printStackTrace();
               // @@错误处理
               CError tError = new CError();
               tError.moduleName = "GrpEdorGBForLoadBL";
               tError.functionName = "checkData";
               tError.errorMessage = "导入文件格式有误!";
               mErrors.addOneError(tError);
           }
           logger.debug("结束时间:" + PubFun.getCurrentTime());
           logger.debug("-----------complete---------"+this.mErrors.getErrorCount());
           if (this.mErrors.getErrorCount() > 0)
           {
               return false;
           }
           
           this.mResult.clear();
           
           return true;
       }

       /**
        * 检验文件是否存在
        * @return
        */
       private boolean checkXmlFileName() {
           File tFile = new File(XmlFileName);
           if (!tFile.exists()) {
               LDSysVarDB tLDSysVarDB = new LDSysVarDB();
               tLDSysVarDB.setSysVar("BqXmlPath");
               if (!tLDSysVarDB.getInfo()) {
                   // @@错误处理
                   CError tError = new CError();
                   tError.moduleName = "GrpEdorGAForLoadBL";
                   tError.functionName = "checkData";
                   tError.errorMessage = "缺少文件导入路径!";
                   this.mErrors.addOneError(tError);
                   return false;
               } else {
                   FilePath = tLDSysVarDB.getSysVarValue();
               }
               File tFile1 = new File(FilePath);
               if (!tFile1.exists()) {
                   tFile1.mkdirs();
               }
               XmlFileName = FilePath + XmlFileName;
               File tFile2 = new File(XmlFileName);
               if (!tFile2.exists()) {
                   // @@错误处理
                   CError tError = new CError();
                   tError.moduleName = "GrpEdorGAForLoadBL";
                   tError.functionName = "checkData";
                   tError.errorMessage = "请上传相应的数据文件到指定路径" +
                                         FilePath + "!";
                   this.mErrors.addOneError(tError);
                   return false;
               }
           }
           return true;
       }

       /**
        * 解析xml
        * @return
        */
       private boolean ParseXml() {
           /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
            No.1 检查xml文件存在
            －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
            */
           if (!checkXmlFileName()) {
               return false;
           }
           File tFile = new File(XmlFileName);
           XMLPathTool tXPT = new XMLPathTool(XmlFileName);
           this.mErrors.clearErrors();
           try {
               /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                No.2 批次号
                －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                */
               String tLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
               mBatchNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
           } catch (Exception ex) {
               ex.printStackTrace();
           }

           /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
            No.3 取得xml文件的第一类字段的值（出险人信息列）
            －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
            */
           NodeList nodeList = tXPT.parseN(ParsePath);
           int nNodeCount = 0;

           if (nodeList != null) {
               nNodeCount = nodeList.getLength();
           }
           /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
            No.4 单次提交出险人信息列
            －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
            */
           boolean allInput = true;
           for (int i = 0; i < nNodeCount; i++) {
               Node node = nodeList.item(i);
               try {
                   node = transformNode(node);
               } catch (Exception ex) {
                   ex.printStackTrace();
                   node = null;
               }
               String nodeName = "";
               nodeName = node.getNodeName();
               String tInsuredNo = parseNode(node,"InsuredNo");
               String tContNo=parseNode(node,"ContNo");
               
               LCContSchema tLCContSchema = new LCContSchema();
               LCContSet tLCContSet = new LCContSet();
               LCContDB tLCContDB = new LCContDB();
               tLCContDB.setContNo(tContNo);
               tLCContDB.setInsuredNo(tInsuredNo);
               tLCContSet = tLCContDB.query();
               if(tLCContSet.size()<1)
               {
                   CError tError = new CError();
                   tError.moduleName = "LJSPayPersonForLoadBL";
                   tError.functionName = "ParseXml";
                   tError.errorMessage = "未查询到团单号为"+mGrpContNo+"客户号为"+tInsuredNo+"的个人保单信息！(第"+(i+1)+"行)";
                   this.mErrors.addOneError(tError);
                   return false;
               }
               tLCContSchema = tLCContSet.get(1);
               mContNo = tLCContSchema.getContNo();
               LCPolSchema tLCPolSchema = new LCPolSchema();
               LCPolSet tLCPolSet = new LCPolSet();
               LCPolDB tLCPolDB = new LCPolDB();
               tLCPolDB.setContNo(tContNo);
               tLCPolDB.setInsuredNo(tInsuredNo);
               tLCPolSet = tLCPolDB.query();
               if(tLCPolSet.size()<1)
               {
                   CError tError = new CError();
                   tError.moduleName = "LJSPayPersonForLoadBL";
                   tError.functionName = "ParseXml";
                   tError.errorMessage = "未查询到团单号为"+mGrpContNo+"客户号为"+tInsuredNo+"的个人保单信息！(第"+(i+1)+"行)";
                   this.mErrors.addOneError(tError);
                   return false;
               }
               tLCPolSchema = tLCPolSet.get(1);
               mPolNo = tLCPolSchema.getPolNo();

               TransferData  mTransferData =new TransferData();
               mTransferData.setNameAndValue("EdorNo",mEdorNo);
               mTransferData.setNameAndValue("ContNo",mContNo);
               mTransferData.setNameAndValue("PolNo",mPolNo);
               mTransferData.setNameAndValue("Action","2");
               mTransferData.setNameAndValue("EdorType",mEdorType);
               logger.debug(mTransferData.getValueByName("EdorType"));

                LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
                LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
                LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
                if (nodeName.equals("#text")) {
                    continue;
                }
                if (nodeName.equals("ROW")) {
                    mLPEdorItemSchema = this.getLPEdorItem(node);
                    logger.debug("LPEdorItem");
                    mLPEdorItemSet.add(mLPEdorItemSchema);
                    mLPGrpEdorItemSchema = this.getLPGrpEdorItem(node);
                }
                if (mLPEdorItemSet == null) {
               CError tError = new CError();
               tError.moduleName = "GrpEdorGAForLoadBL";
               tError.functionName = "parserXml";
               tError.errorMessage = "获得导入的文件信息失败!";
               this.mErrors.addOneError(tError);
               }
               if (mLPGrpEdorItemSchema == null) {
               CError tError = new CError();
               tError.moduleName = "GrpEdorGAForLoadBL";
               tError.functionName = "parserXml";
               tError.errorMessage = "获得导入的文件信息失败!";
               this.mErrors.addOneError(tError);
               }
               
           	//新的起领日期类型
            String tGetStartType=parseNode(node,"GetStartType");
           	//确认结息日期,结息日期为起领日期
           	String aBalanceDate = "";
           	logger.debug("==> 确定领取日期类型为" + tGetStartType);

           	
           	//责任表的数据
           	LCDutySet tLCDutySet = new LCDutySet();
           	LCDutyDB tLCDutyDB = new LCDutyDB();
           	tLCDutyDB.setPolNo(mPolNo);
           	tLCDutySet = tLCDutyDB.query();
           	if(tLCDutySet == null ||tLCDutySet.size() < 1)
           	{
           		CError.buildErr(this, "责任表查询失败!(第"+(i+1)+"行)");
           		return false;

           	}
           	String sOldGetStartType = tLCDutySet.get(1).getGetStartType();
           
           	String strSQL = "select * from lcget a where polno ='"
           		+ tLCPolSchema.getPolNo() + "' and livegettype ='0'" //生存领取
           		+ "and exists (select getdutycode from lmdutyget b "
           		+ "where a.getdutycode=b.getdutycode and canget ='1') " //必须申请给付
           		+ "and exists (select * from lmriskduty c "
           		+ "where a.dutycode = c.dutycode and riskcode='"
           		+ tLCPolSchema.getRiskCode() + "')";

           	LCGetDB tLCGetDB = new LCGetDB();
           	LCGetSet tLCGetSet = tLCGetDB.executeQuery(strSQL);

           	logger.debug("共有" + tLCGetSet.size() + "条需要申请给付的生存领取");

           	if (tLCGetSet.size() <= 0)
           	{
           		// @@错误处理
           		CError.buildErr(this, "该保单不用进行给付申请!(第"+(i+1)+"行)");
           		return false;
           	}
           	if (!tGetStartType.equals(sOldGetStartType))
           	{
           		String sBaseDate = "";
           		String sCompDate = "";
           		//更改了领取日期类型，重新计算开始领取日期
           		if (tGetStartType.equals("S"))
           		{
           			//不能设置领取日期类型,结息点为生存金起领日期
           			sBaseDate = tLCPolSchema.getInsuredBirthday();
           			sCompDate = tLCPolSchema.getCValiDate();
           		}
           		if (tGetStartType.equals("B"))
           		{
           			//不能设置领取日期类型,结息点为生存金起领日期
           			sBaseDate = tLCPolSchema.getInsuredBirthday();
           			sCompDate = tLCPolSchema.getInsuredBirthday();
           		}
           		aBalanceDate = PubFun.calDate(sBaseDate,
           				tLCDutySet.get(1).getGetYear(), "Y", sCompDate);
           	}
           	else
           	{
           		//没有更改领取日期类型,起领日期不变
           		aBalanceDate = tLCGetSet.get(1).getGetStartDate();
           	}
           	logger.debug("结息日期为" + aBalanceDate + ",领取日期类型为"
           			+ tGetStartType);
           	LCInsureAccClassSet aLCInsureAccClassSet = new LCInsureAccClassSet();
           	
           	LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
               String strSql = "select * from LCInsureAcc where PolNo ='" + tLCPolSchema.getPolNo() + "'";
               LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.executeQuery(strSql);
               if (tLCInsureAccDB.mErrors.needDealError())
               {
                   CError.buildErr(this, "保单帐户查询失败!(第"+(i+1)+"行)");
                   return false;
               }
               if (tLCInsureAccSet == null || tLCInsureAccSet.size() < 1)
               {
                   CError.buildErr(this, "保单没有帐户数据!(第"+(i+1)+"行)");
                   return false;
               }
           	TransferData sTransferData = new TransferData();
           	sTransferData.setNameAndValue("InsuAccNo",tLCInsureAccSet.get(1).getInsuAccNo());
           	sTransferData.setNameAndValue("PolNo", tLCInsureAccSet.get(1).getPolNo());
           	sTransferData.setNameAndValue("BalaDate", aBalanceDate);
       		VData sVData = new VData();
       		sVData.add(mGlobalInput);
       		sVData.add(sTransferData);
       		//非万能险的账户型结算
       		InsuAccBala tInsuAccBala = new InsuAccBala();
       		if (!tInsuAccBala.submitData(sVData, "NonUniversal")) {
       			CError.buildErr(this, "分红结算失败！(第"+(i+1)+"行)");
       			return false;
       		}
       		VData tResult  = tInsuAccBala.getResult();
       		aLCInsureAccClassSet = (LCInsureAccClassSet)tResult.getObjectByObjectName("LCInsureAccClassSet", 0);


                TransferData tTransferData = new TransferData();
                tTransferData.setNameAndValue("EdorNo", mEdorNo);
                tTransferData.setNameAndValue("EdorType", mEdorType);
                tTransferData.setNameAndValue("EdorAcceptNo", mEdorAcceptNo);
                tTransferData.setNameAndValue("ContNo", mContNo);
                tTransferData.setNameAndValue("GrpContNo", mGrpContNo);
                tTransferData.setNameAndValue("InsuredNo", tInsuredNo);

               PEdorGADetailBL tPEdorGADetailBL = new PEdorGADetailBL();
               VData tVData = new VData();
               tVData.addElement(mGlobalInput);
               tVData.addElement(tTransferData);
               tVData.addElement(mLPEdorItemSet);
               tVData.addElement(mLPGrpEdorItemSchema);
               GEdorDetailUI tGEdorDetailUI = new GEdorDetailUI();
               if(!tGEdorDetailUI.submitData(tVData, "INSERT||EDOR")){
                   this.mErrors.copyAllErrors(tGEdorDetailUI.mErrors);
                   CError tError = new CError();
                   tError.moduleName = "PEdorRTDetailUI";
                   tError.functionName = "parserXml";
                   tError.errorMessage = "数据提交失败！";
                   this.mErrors.addOneError(tError);
               }




               String tErrorMsg = "导入成功！";
               LPGrpEdorImportLogSchema tLPGrpEdorImportLogSchema = null;
               logger.debug("---------进入tPGrpEdorGADetailBL！----");
               
               mLPEdorItemSchema.setPolNo(tLCPolSchema.getPolNo());
               LCInsureAccClassSchema tLCInsureAccClassSchema = null;
               LPInsureAccClassSchema tLPInsureAccClassSchema = null;
               LCGetSchema tLCGetSchema = null;
               LPGetSchema tLPGetSchema = null;
               for(int j = 1;j<=aLCInsureAccClassSet.size();j++)
               {
            	   tLCInsureAccClassSchema = aLCInsureAccClassSet.get(j);
            	   tLPInsureAccClassSchema = new LPInsureAccClassSchema();
            	   String tPayPlanCode = tLCInsureAccClassSchema.getPayPlanCode();
            	   LMDutyPayDB tLMDutyPayDB = new LMDutyPayDB();
            	   tLMDutyPayDB.setPayPlanCode(tPayPlanCode);
            	   if (!tLMDutyPayDB.getInfo()) {
            		   CError.buildErr(this, "查询责任缴费项信息失败");
            	   }						
            	   String tPayAimClass = tLMDutyPayDB.getPayAimClass();
            	   
            	   mRef.transFields(tLPInsureAccClassSchema, tLCInsureAccClassSchema);
            	   tLPInsureAccClassSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
            	   tLPInsureAccClassSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
            	   LPAccMoveSet tLPAccMoveSet = new LPAccMoveSet();
            	   for(int k = 1;k<=tLCGetSet.size();k++)
            	   {
            		   tLCGetSchema = tLCGetSet.get(k);
            		   String tDutyCode = tLCGetSchema.getDutyCode();
            		   LMDutyPayRelaDB tLMDutyPayRelaDB = new LMDutyPayRelaDB();
            		   tLMDutyPayRelaDB.setDutyCode(tDutyCode);
            		   tLMDutyPayRelaDB.setPayPlanCode(tPayPlanCode);
            		   if(tLMDutyPayRelaDB.getInfo())
            		   {
            			   tLPGetSchema = new LPGetSchema();
            			   mRef.transFields(tLPGetSchema, tLCGetSchema);
            			   //新的起领日期类型
            			   String tGetDutyKind=parseNode(node,"GetDutyKind");
            			   tLPGetSchema.setGetDutyKind(tGetDutyKind);
            			   tLPGetSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
            			   tLPGetSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
            		   }
            	   }
            	   if("2".equals(tPayAimClass)){
            		   //2为集体交费
            		   String cAnnuityCash = parseNode(node,"CACash"); //集体部份转年金的金额;
            		   String cAnnuityRate = parseNode(node,"CARate"); //集体部份转年金的比例;
            		   String annuityFlag = "0";
            		   if((cAnnuityCash != null && !cAnnuityCash.trim().equals(""))||(cAnnuityRate != null && !cAnnuityRate.trim().equals("")))
            		   {
            			   annuityFlag = "1";
            			   LPAccMoveSchema annuityLPAccMoveSchema = new LPAccMoveSchema();
            			   annuityLPAccMoveSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
            			   annuityLPAccMoveSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
            			   annuityLPAccMoveSchema.setPolNo(tLCPolSchema.getPolNo());
            			   annuityLPAccMoveSchema.setInsuAccNo(tLPInsureAccClassSchema.getInsuAccNo());
            			   annuityLPAccMoveSchema.setPayPlanCode(tLPInsureAccClassSchema.getPayPlanCode());
            			   annuityLPAccMoveSchema.setAccAscription(tLPInsureAccClassSchema.getAccAscription());
            			   annuityLPAccMoveSchema.setOtherNo(tLPGetSchema.getGetDutyCode());
            			   annuityLPAccMoveSchema.setAccMoveType("2");//转年金处理
            			   annuityLPAccMoveSchema.setAccMoveNo("000000");
            			   annuityLPAccMoveSchema.setAccMoveCode("000000");
            			   annuityLPAccMoveSchema.setAccMoveBala(cAnnuityCash);
            			   annuityLPAccMoveSchema.setAccMoveRate(cAnnuityRate);
            			   tLPAccMoveSet.add(annuityLPAccMoveSchema);
            		   }
            		   String cCashCash = parseNode(node,"CCCash");//集体部份转现金的金额;
            		   String cCashRate = parseNode(node,"CCRate");//集体部份转现金的比例;
            		   String cashFlag = "0";
            		   if((cCashCash != null && !cCashCash.trim().equals(""))||(cCashRate != null && !cCashRate.trim().equals("")))
            		   {
            			   cashFlag = "1";

            				//现金帐户转移轨迹
            				LPAccMoveSchema cashLPAccMoveSchema = new LPAccMoveSchema();
            				cashLPAccMoveSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
            				cashLPAccMoveSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
            				cashLPAccMoveSchema.setPolNo(tLCPolSchema.getPolNo());
            				cashLPAccMoveSchema.setInsuAccNo(tLPInsureAccClassSchema.getInsuAccNo());
            				cashLPAccMoveSchema.setPayPlanCode(tLPInsureAccClassSchema.getPayPlanCode());
            				cashLPAccMoveSchema.setAccAscription(tLPInsureAccClassSchema.getAccAscription());
            				cashLPAccMoveSchema.setOtherNo(tLPGetSchema.getGetDutyCode());
            				cashLPAccMoveSchema.setAccMoveType("1");//转现金处理
            				cashLPAccMoveSchema.setAccMoveNo("000000");
            				cashLPAccMoveSchema.setAccMoveCode("000000");
            				cashLPAccMoveSchema.setAccMoveBala(cCashCash);
            				cashLPAccMoveSchema.setAccMoveRate(cCashRate);
            				tLPAccMoveSet.add(cashLPAccMoveSchema);
            			
            		   }
            		   
            		   	tTransferData = new TransferData();
            		   	tTransferData.setNameAndValue("GetStartType", tGetStartType);
            			tTransferData.setNameAndValue("annuityFlag", annuityFlag);
            			tTransferData.setNameAndValue("cashFlag", cashFlag);
            			tTransferData.setNameAndValue("RAnnuity", cAnnuityRate);
            			tTransferData.setNameAndValue("CAnnuity", cAnnuityCash);
            			tTransferData.setNameAndValue("RCash", cCashRate);
            			tTransferData.setNameAndValue("CCash", cCashCash);
            			
            			tVData = new VData();
            			tVData.addElement(mGlobalInput);
            			tVData.addElement(mLPEdorItemSchema);
            			tVData.addElement(tLPGetSchema);
            			tVData.addElement(tLPInsureAccClassSchema);
            			tVData.addElement(tLPAccMoveSet);
            			tVData.addElement(tTransferData);
            			
             		   tPEdorGADetailBL = new PEdorGADetailBL();
            		   if (!tPEdorGADetailBL.submitData(tVData, "INSERT||MONEY")) {
            			   this.mErrors.copyAllErrors(tPEdorGADetailBL.mErrors);
            			   CError tError = new CError();
            			   tError.moduleName = "PEdorGADetailUI";
            			   tError.functionName = "parserXml";
            			   tError.errorMessage = "数据提交失败！";
            			   this.mErrors.addOneError(tError);
            		   }
            		   if (mErrors != null && mErrors.getErrorCount() > 0){

            			   //输出错误到日志表
            			   tErrorMsg = mErrors.getFirstError();
            			   //mErrors.clearErrors();
            			   allInput=false;
            		   }

            	   }else if("1".equals(tPayAimClass)){
            		   //1 为个人交费
            		   String pAnnuityCash = parseNode(node,"PACash"); //个人交费部份转年金的金额;
            		   String pAnnuityRate = parseNode(node,"PARate"); //个人交费部份转年金的比例;
            		   String annuityFlag = "0";
            		   if((pAnnuityCash != null && !pAnnuityCash.trim().equals(""))||(pAnnuityRate != null && !pAnnuityRate.trim().equals("")))
            		   {
            			   annuityFlag = "1";
            			   LPAccMoveSchema annuityLPAccMoveSchema = new LPAccMoveSchema();
            			   annuityLPAccMoveSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
            			   annuityLPAccMoveSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
            			   annuityLPAccMoveSchema.setPolNo(tLCPolSchema.getPolNo());
            			   annuityLPAccMoveSchema.setInsuAccNo(tLPInsureAccClassSchema.getInsuAccNo());
            			   annuityLPAccMoveSchema.setPayPlanCode(tLPInsureAccClassSchema.getPayPlanCode());
            			   annuityLPAccMoveSchema.setAccAscription(tLPInsureAccClassSchema.getAccAscription());
            			   annuityLPAccMoveSchema.setOtherNo(tLPGetSchema.getGetDutyCode());
            			   annuityLPAccMoveSchema.setAccMoveType("2");//转年金处理
            			   annuityLPAccMoveSchema.setAccMoveNo("000000");
            			   annuityLPAccMoveSchema.setAccMoveCode("000000");
            			   annuityLPAccMoveSchema.setAccMoveBala(pAnnuityCash);
            			   annuityLPAccMoveSchema.setAccMoveRate(pAnnuityRate);
            			   tLPAccMoveSet.add(annuityLPAccMoveSchema);
            		   }
            		   String pCashCash = parseNode(node,"PCCash");//个人交费部份转现金的金额;
            		   String pCashRate = parseNode(node,"PCRate");//个人交费部份转现金的比例;
            		   String cashFlag = "0";
            		   if((pCashCash != null && !pCashCash.trim().equals(""))||(pCashRate != null && !pCashRate.trim().equals("")))
            		   {
            			   cashFlag = "1";

            			   //现金帐户转移轨迹
            			   LPAccMoveSchema cashLPAccMoveSchema = new LPAccMoveSchema();
            			   cashLPAccMoveSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
            			   cashLPAccMoveSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
            			   cashLPAccMoveSchema.setPolNo(tLCPolSchema.getPolNo());
            			   cashLPAccMoveSchema.setInsuAccNo(tLPInsureAccClassSchema.getInsuAccNo());
            			   cashLPAccMoveSchema.setPayPlanCode(tLPInsureAccClassSchema.getPayPlanCode());
            			   cashLPAccMoveSchema.setAccAscription(tLPInsureAccClassSchema.getAccAscription());
            			   cashLPAccMoveSchema.setOtherNo(tLPGetSchema.getGetDutyCode());
            			   cashLPAccMoveSchema.setAccMoveType("1");//转现金处理
            			   cashLPAccMoveSchema.setAccMoveNo("000000");
            			   cashLPAccMoveSchema.setAccMoveCode("000000");
            			   cashLPAccMoveSchema.setAccMoveBala(pCashCash);
            			   cashLPAccMoveSchema.setAccMoveRate(pCashRate);
            			   tLPAccMoveSet.add(cashLPAccMoveSchema);

            		   }
            		   tTransferData = new TransferData();
            		   tTransferData.setNameAndValue("GetStartType", tGetStartType);
            		   tTransferData.setNameAndValue("annuityFlag", annuityFlag);
            		   tTransferData.setNameAndValue("cashFlag", cashFlag);
            		   tTransferData.setNameAndValue("RAnnuity", pAnnuityRate);
            		   tTransferData.setNameAndValue("CAnnuity", pAnnuityCash);
            		   tTransferData.setNameAndValue("RCash", pCashRate);
            		   tTransferData.setNameAndValue("CCash", pCashCash);

            		   tVData = new VData();
            		   tVData.addElement(mGlobalInput);
            		   tVData.addElement(mLPEdorItemSchema);
            		   tVData.addElement(tLPGetSchema);
            		   tVData.addElement(tLPInsureAccClassSchema);
            		   tVData.addElement(tLPAccMoveSet);
            		   tVData.addElement(tTransferData);
            		   
            		   tPEdorGADetailBL = new PEdorGADetailBL();
            		   if (!tPEdorGADetailBL.submitData(tVData, "INSERT||MONEY")) {
            			   this.mErrors.copyAllErrors(tPEdorGADetailBL.mErrors);
            			   CError tError = new CError();
            			   tError.moduleName = "PEdorGADetailUI";
            			   tError.functionName = "parserXml";
            			   tError.errorMessage = "数据提交失败！";
            			   this.mErrors.addOneError(tError);
            		   }
            		   if (mErrors != null && mErrors.getErrorCount() > 0){

            			   //输出错误到日志表
            			   tErrorMsg = mErrors.getFirstError();
            			   //mErrors.clearErrors();
            			   allInput=false;
            		   }

            	   }
               }

    		   tVData = new VData();
    		   tVData.addElement(mGlobalInput);
    		   tVData.addElement(mLPEdorItemSchema);
    		   tVData.addElement(tLPGetSchema);
    		   tVData.addElement(tLPInsureAccClassSchema);
    		   //tVData.addElement(tLPAccMoveSet);
    		   tVData.addElement(tTransferData);
    		   
    		   tPEdorGADetailBL = new PEdorGADetailBL();
    		   if (!tPEdorGADetailBL.submitData(tVData, "INSERT||CONFIRM")) {
    			   this.mErrors.copyAllErrors(tPEdorGADetailBL.mErrors);
    			   CError tError = new CError();
    			   tError.moduleName = "PEdorGADetailUI";
    			   tError.functionName = "parserXml";
    			   tError.errorMessage = "数据提交失败！";
    			   this.mErrors.addOneError(tError);
    		   }
    		   if (mErrors != null && mErrors.getErrorCount() > 0){

    			   //输出错误到日志表
    			   tErrorMsg = mErrors.getFirstError();
    			   //mErrors.clearErrors();
    			   allInput=false;
    		   }

               //输出日志信息到LPGrpEdorImportLog表中
               tLPGrpEdorImportLogSchema = new LPGrpEdorImportLogSchema();
               tLPGrpEdorImportLogSchema.setMakeDate(CurrentDate);
               tLPGrpEdorImportLogSchema.setMakeTime(CurrentTime);
               tLPGrpEdorImportLogSchema.setIDNo(String.valueOf(i));
               tLPGrpEdorImportLogSchema.setEdorNo(mEdorNo);
               tLPGrpEdorImportLogSchema.setEdorType(mEdorType);
               tLPGrpEdorImportLogSchema.setGrpContNo(mGrpContNo);
               tLPGrpEdorImportLogSchema.setContNo(mContNo);
               tLPGrpEdorImportLogSchema.setPolNo(tInsuredNo);
               tLPGrpEdorImportLogSchema.setReturnMSG(tErrorMsg);
               mMap.put(tLPGrpEdorImportLogSchema, "DELETE&INSERT");
               mResult = new VData();
               mResult.add(mMap);

               //数据提交
               PubSubmit tSubmit = new PubSubmit();
               if (!tSubmit.submitData(mResult, "")) {

                 // @@错误处理
                 this.mErrors.copyAllErrors(tSubmit.mErrors);
                 CError tError = new CError();
                 tError.moduleName   = "PEdorPTDetailBL";
                 tError.functionName = "submitData";
                 tError.errorMessage = "数据提交失败!";
                 this.mErrors.addOneError(tError);
                 logger.debug("return false;");
                 return false;
             }
           }
           /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
            No.6 解析完删除XML文件
                  －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
            */
           /*if (!tFile.delete())
                    {
               // @@错误处理
               CError tError = new CError();
               tError.moduleName = "LJSPayPersonForLoadBL";
               tError.functionName = "checkData";
               tError.errorMessage = "Xml文件删除失败!";
               this.mErrors.addOneError(tError);
               return false;
                    }*/
          /* logger.debug("===================================================="+allInput);
           if (allInput)
           {
               this.mErrors.addOneError("保全领取年龄导入成功！");
           }*/
           return true;
       }

       /**
        * Detach node from original document, fast XPathAPI process.
        * @param node
        * @return
        * @throws Exception
        */
       private org.w3c.dom.Node transformNode(org.w3c.dom.Node node) throws
               Exception {
           Node nodeNew = m_doc.importNode(node, true);
           return nodeNew;
       }

       /**
        * 解析excel并转换成xml文件,用完后删除xml文件
        * @return
        */
       private boolean parseVts() throws Exception {
           logger.debug("--------excel--导入-xml---开始----------");
           /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             No.1 初始化导入文件
            －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
            */
           if (!this.initImportFile()) {
               return false;
           }
           /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
            No.2 检查导入配置文件是否存在
            －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
            */
           if (!this.checkImportConfig()) {
               return false;
           }
           /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
            No.3 指定导入文件名称
            －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
            */
           LCTempCustomerVTSParser GCvp = new LCTempCustomerVTSParser();
           if (!GCvp.setFileName(ImportFileName)) {
               mErrors.copyAllErrors(GCvp.mErrors);
               return false;
           }
           /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
            No.4 指定配置文件名称
            －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
            */
           if (!GCvp.setConfigFileName(ConfigFileName)) {
               mErrors.copyAllErrors(GCvp.mErrors);
               return false;
           }
           /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
            No.5 转换excel到xml
            －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
            */
           if (!GCvp.transform()) {
               mErrors.copyAllErrors(GCvp.mErrors);
               return false;
           }
           /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
            No.5 得到生成的XML文件名列表
            －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
            */
           m_strDataFiles = GCvp.getDataFiles();
           logger.debug("-------excel--导入-xml----结束----------");
           return true;
       }

       /**
        * 检查导入配置文件是否存在
        * @return
        */
       private boolean checkImportConfig() {
           this.getFilePath();

           String filePath = mPreFilePath + FilePath;
           File tFile1 = new File(filePath);
           if (!tFile1.exists()) {
               //初始化创建目录
               tFile1.mkdirs();
           }

           ConfigFileName = filePath + "GrpEdorGA.xml";
           File tFile2 = new File(ConfigFileName);
            if (!tFile2.exists()) {
               // @@错误处理
               CError tError = new CError();
               tError.moduleName = "GrpEdorGAForLoadBL";
               tError.functionName = "checkImportConfig";
               tError.errorMessage = "请上传配置文件到指定路径" +
                                     FilePath + "!";
               this.mErrors.addOneError(tError);
               return false;
           }
           return true;
       }

       /**
        * 初始化上传文件
        * @return
        */
       private boolean initImportFile() {
           this.getFilePath();
           ImportFileName = mPreFilePath + FilePath+"BqUp/"+ FileName;
           File tFile = new File(ImportFileName);
           if (!tFile.exists()) {
               // @@错误处理
               CError tError = new CError();
               tError.moduleName = "LJSPayPersonForLoadBL";
               tError.functionName = "checkImportConfig";
               tError.errorMessage = "未上传文件到指定路径" +
                                     FilePath + "!";
               this.mErrors.addOneError(tError);
               return false;
           }
           return true;
       }

       /**
        * 得到生成文件路径
        * @return
        */
       private boolean getFilePath() {
           LDSysVarDB tLDSysVarDB = new LDSysVarDB();
           tLDSysVarDB.setSysVar("BqXmlPath");
           if (!tLDSysVarDB.getInfo()) {
               // @@错误处理
               CError tError = new CError();
               tError.moduleName = "LJSPayPersonForLoadBL";
               tError.functionName = "checkData";
               tError.errorMessage = "缺少文件导入路径!";
               this.mErrors.addOneError(tError);
               return false;
           } else {
               FilePath = tLDSysVarDB.getSysVarValue();
           }
           logger.debug("FilePath：" + FilePath);
           return true;
       }

       /**
        * getInputData
        */
       private boolean getInputData() {
           mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
                   "GlobalInput",
                   0);
           mTransferData = (TransferData) mInputData.getObjectByObjectName(
                   "TransferData", 0);
           mPreFilePath = (String) mTransferData.getValueByName("FilePath");
           FileName = (String) mTransferData.getValueByName("FileName");
           mGrpContNo = (String) mTransferData.getValueByName("GrpContNo");
           mEdorAcceptNo = (String) mTransferData.getValueByName("EdorAcceptNo");
           mEdorNo = (String) mTransferData.getValueByName("EdorNo");
           mEdorType = (String) mTransferData.getValueByName("EdorType");
           //mContNo = (String) mTransferData.getValueByName("ContNo");
           //mPolNo = (String) mTransferData.getValueByName("PolNo");

           if (mGlobalInput == null) {
               CError tError = new CError();
               tError.moduleName = "GrpEdorGBForLoadBL";
               tError.functionName = "getInputData";
               tError.errorMessage = "接受前台传入的GlobalInput数据失败!";
               this.mErrors.addOneError(tError);
               return false;
           }
           if (mTransferData == null) {
               CError tError = new CError();
               tError.moduleName = "GrpEdorGBForLoadBL";
               tError.functionName = "getInputData";
               tError.errorMessage = "接受前台传入的TransferData数据失败!";
               this.mErrors.addOneError(tError);
               return false;
           }
           if (FileName == null) {
               CError tError = new CError();
               tError.moduleName = "GrpEdorGBForLoadBL";
               tError.functionName = "getInputData";
               tError.errorMessage = "接受前台传入的FileName数据失败!";
               this.mErrors.addOneError(tError);
               return false;
           }
           if (mGrpContNo == null) {
               CError tError = new CError();
               tError.moduleName = "GrpEdorGBForLoadBL";
               tError.functionName = "getInputData";
               tError.errorMessage = "接受前台传入的团体保单号数据失败!";
               this.mErrors.addOneError(tError);
               return false;
           }
           if (mEdorNo == null) {
               CError tError = new CError();
               tError.moduleName = "GrpEdorGBForLoadBL";
               tError.functionName = "getInputData";
               tError.errorMessage = "接受前台传入的保全号数据失败!";
               this.mErrors.addOneError(tError);
               return false;
           }
           if (mEdorType == null) {
               CError tError = new CError();
               tError.moduleName = "GrpEdorGBForLoadBL";
               tError.functionName = "getInputData";
               tError.errorMessage = "接受前台传入的保全号数据失败!";
               this.mErrors.addOneError(tError);
               return false;
           }

           return true;
       }

       /**
        * 得到日志显示结果
        * @return VData
        */
       public VData getResult() {
//        mResult.add(mBatchNo);
           return mResult;

       }

       /**
        * 利用XPathAPI取得某个节点的节点值
        * @param node
        * @param strPath
        * @return
        */
       private static String parseNode(Node node,
                                       String strPath) {
           String strValue = "";
           try {
               XObject xobj = XPathAPI.eval(node, strPath);
               strValue = xobj == null ? "" : xobj.toString();
           } catch (Exception ex) {
               ex.printStackTrace();
           }
           return strValue;
       }
       private LPEdorItemSchema getLPEdorItem(Node node) {
             // String tContNo = parseNode(node, "ContNo");
              String tInsuredNo = parseNode(node, "InsuredNo");

              LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
              tLPEdorItemSchema.setEdorAcceptNo(mEdorAcceptNo);
              tLPEdorItemSchema.setEdorNo(mEdorNo);
              tLPEdorItemSchema.setEdorType(mEdorType);
              tLPEdorItemSchema.setContNo(mContNo);
              tLPEdorItemSchema.setInsuredNo(tInsuredNo);
              tLPEdorItemSchema.setPolNo(mPolNo);
              tLPEdorItemSchema.setOperator(mGlobalInput.ManageCom);
              tLPEdorItemSchema.setManageCom(mGlobalInput.Operator);
              tLPEdorItemSchema.setMakeDate(PubFun.getCurrentDate());
              tLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
              tLPEdorItemSchema.setMakeTime(PubFun.getCurrentTime());
              tLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
              tLPEdorItemSchema.setEdorState("3");

              return tLPEdorItemSchema;
    }
            private LPGrpEdorItemSchema getLPGrpEdorItem(Node node) {
         // String tContNo = parseNode(node, "ContNo");
          //String tInsuredNo = parseNode(node, "InsuredNo");

          LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
          tLPGrpEdorItemSchema.setGrpContNo(mGrpContNo);
          tLPGrpEdorItemSchema.setEdorAcceptNo(mEdorAcceptNo);
          tLPGrpEdorItemSchema.setEdorNo(mEdorNo);
          tLPGrpEdorItemSchema.setEdorType(mEdorType);
          tLPGrpEdorItemSchema.setOperator(mGlobalInput.ManageCom);
          tLPGrpEdorItemSchema.setManageCom(mGlobalInput.Operator);
          tLPGrpEdorItemSchema.setMakeDate(PubFun.getCurrentDate());
          tLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
          tLPGrpEdorItemSchema.setMakeTime(PubFun.getCurrentTime());
          tLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
          tLPGrpEdorItemSchema.setEdorState("3");

          return tLPGrpEdorItemSchema;
}




       /**
        * Build a instance document object for function transfromNode()
        */
       private void bulidDocument() {
           DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
           dfactory.setNamespaceAware(true);

           try {
               m_doc = dfactory.newDocumentBuilder().newDocument();
           } catch (Exception ex) {
               ex.printStackTrace();
           }
       }

       public static void main(String[] args) {
           /**LJSPayPersonForLoadBL tPGI = new LJSPayPersonForLoadBL();
           VData tVData = new VData();
           TransferData tTransferData = new TransferData();
           tTransferData.setNameAndValue("FileName", "20060606.xls");
           tTransferData.setNameAndValue("FilePath", "d:/lis/ui");
           tTransferData.setNameAndValue("GrpContNo", "280101000618");
           GlobalInput tG = new GlobalInput();
           tG.Operator = "claim";
           tG.ManageCom = "86";
           tG.ComCode = "0101";
           long a = System.currentTimeMillis();
           logger.debug(a);
           tVData.add(tTransferData);
           tVData.add(tG);
           if (!tPGI.submitData(tVData, "")) {
               logger.debug("提交失败！！！！");
           }
           logger.debug(System.currentTimeMillis() - a);
           */
       }

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}
}
