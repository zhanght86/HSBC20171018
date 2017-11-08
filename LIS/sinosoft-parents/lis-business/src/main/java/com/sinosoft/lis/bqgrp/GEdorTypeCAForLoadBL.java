package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xpath.XPathAPI;
import org.apache.xpath.objects.XObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LPAccMoveDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LPAccMoveSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPInsureAccClassSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LPAccMoveSet;
import com.sinosoft.lis.vschema.LPInsureAccClassSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XMLPathTool;


/**
 * <p>Title:GEdorTypeCAForLoadBL.java </p>
 * <p>Description: CA导入</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * @author sunsx 2009-02-03
 * @version 1.0
 */
public class GEdorTypeCAForLoadBL implements BusinessService {
private static Logger logger = Logger.getLogger(GEdorTypeCAForLoadBL.class);
    public GEdorTypeCAForLoadBL() {
        bulidDocument();
    }

    //错误处理类
    public CErrors mErrors = new CErrors();
    private VData mInputData = new VData();
    /** 往前面传输数据的容器 */
    private VData mResult = new VData();
    private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
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
            tError.moduleName = "GrpEdorLBForLoadBL";
            tError.functionName = "checkData";
            tError.errorMessage = "导入文件格式有误!";
            mErrors.addOneError(tError);
        }
        logger.debug("结束时间:" + PubFun.getCurrentTime());
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
                tError.moduleName = "GrpEdorCAForLoadBL";
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
                tError.moduleName = "GrpEdorCAForLoadBL";
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
     * 解析xml MD变态人提出的变态导入需求!!!!!!!
     * @author sunsx 
     * @return true or false!
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
//            String tLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
//            mBatchNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
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
        LPAccMoveSet tLPAccMoveSet = new LPAccMoveSet();
        List moContNo = new ArrayList();
        List miContNo = new ArrayList();
        TransferData tContMoveMoney = new TransferData();
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
            String outContNo = parseNode(node, "MOContNo");//转出合同号
            String inContNo = parseNode(node, "MIContNo");//转入合同号
            if(outContNo.equals(inContNo))
            {
                // @@错误处理
                CError.buildErr(this, "不可以在相同帐户间进行资金调整!(第"+(i+1)+"行)");
                return false;
            }
            LCContSet outLCContSet = new LCContSet();
            LCContSet inLCContSet = new LCContSet();
            LCContDB outLCContDB = new LCContDB();
            LCContDB inLCContDB = new LCContDB();
            
            //查询转出帐户信息
            outLCContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
            outLCContDB.setContNo(outContNo);
            outLCContSet=outLCContDB.query();
            if(outLCContSet.size()<1)
            {
                CError tError = new CError();
                tError.moduleName = "GrpEdorCAForLoadBL";
                tError.functionName = "ParseXml";
                tError.errorMessage = "未查询到团单号为"+mLPGrpEdorItemSchema.getGrpContNo()+"合同号为"+outContNo+"的个人保单信息！(第"+(i+1)+"行)";
                this.mErrors.addOneError(tError);
                return false;
            }
            
            inLCContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
            inLCContDB.setContNo(inContNo);
            inLCContSet=inLCContDB.query();
            if(inLCContSet.size()<1)
            {
                CError tError = new CError();
                tError.moduleName = "GrpEdorCAForLoadBL";
                tError.functionName = "ParseXml";
                tError.errorMessage = "未查询到团单号为"+mLPGrpEdorItemSchema.getGrpContNo()+"合同号为"+inContNo+"的个人保单信息！(第"+(i+1)+"行)";
                this.mErrors.addOneError(tError);
                return false;
            }
            LCContSchema outLCContSchema = outLCContSet.get(1);
            LCContSchema inLCContSchema = inLCContSet.get(1);
            
            LCInsureAccClassDB outLCInsureAccClassDB = new LCInsureAccClassDB();
            LCInsureAccClassDB inLCInsureAccClassDB = new LCInsureAccClassDB();
            LCInsureAccClassSet outLCInsureAccClassSet = null;
            LCInsureAccClassSet inLCInsureAccClassSet = null;
            LCInsureAccClassSchema outLCInsureAccClassSchema = null;
            LCInsureAccClassSchema inLCInsureAccClassSchema = null;
            
            //查询转出的集体交费部分或公共帐户
            if("2".equals(outLCContSchema.getPolType()))
            {
            	//公共帐户
            	String tSQL = "select * from LCInsureAccClass where ContNo = '"+outLCContSchema.getContNo()+"'";
            	outLCInsureAccClassSet = outLCInsureAccClassDB.executeQuery(tSQL);
            	outLCInsureAccClassSchema = outLCInsureAccClassSet.get(1);
            }else{
            	String tSQL = "select a.* from LCInsureAccClass a where a.ContNo = '"+outLCContSchema.getContNo()+"' "
            				+ " and exists (select 1 from LMDutyPay where PayAimClass = '2' and PayPlanCode = a.PayPlanCode) ";
            	outLCInsureAccClassSet = outLCInsureAccClassDB.executeQuery(tSQL);
            	outLCInsureAccClassSchema = outLCInsureAccClassSet.get(1);
            }
            
            if("2".equals(inLCContSchema.getPolType()))
            {
            	//公共帐户
            	String tSQL = "select * from LCInsureAccClass where ContNo = '"+inLCContSchema.getContNo()+"'";
            	inLCInsureAccClassSet = inLCInsureAccClassDB.executeQuery(tSQL);
            	inLCInsureAccClassSchema = inLCInsureAccClassSet.get(1);
            }else{
            	String tSQL = "select a.* from LCInsureAccClass a where a.ContNo = '"+inLCContSchema.getContNo()+"' "
            				+ " and exists (select 1 from LMDutyPay where PayPlanCode = a.PayPlanCode and PayAimClass = '2') ";
            	inLCInsureAccClassSet = inLCInsureAccClassDB.executeQuery(tSQL);
            	inLCInsureAccClassSchema = inLCInsureAccClassSet.get(1);
            }
            
            //对转出帐户进行结息校验
            AccountManage tAccountManage = new AccountManage();
            TransferData tTransferData = tAccountManage.getAccClassInterestNewMS(outLCInsureAccClassSchema,mLPGrpEdorItemSchema.getEdorValiDate(),"Y","D");
            //转出帐户本息和
            String outSumMoney = String.valueOf(tTransferData.getValueByName("aAccClassSumPay"));
            //转出帐户利息
            String outInterest = String.valueOf(tTransferData.getValueByName("aAccClassInterest"));
            
            String sMoveMoney = parseNode(node, "MoveMoney");
            String sMoveRate = parseNode(node, "MoveRate");
            
            double dMoveMoney = 0.0;
            if (((sMoveMoney == null) || sMoveMoney.trim().equals(""))
                    && ((sMoveRate == null) || sMoveRate.trim().equals("")))
            {
                // @@错误处理
                CError.buildErr(this, "请对帐户进行转移处理!(第"+(i+1)+"行)");
                return false;
            }

            if ((sMoveMoney.trim().length() > 0)
                    && (sMoveRate.trim().length() > 0))
            {
                // @@错误处理
                CError.buildErr(this, "您只能选择金额或比例其中一种转移方式!(第"+(i+1)+"行)");
                return false;
            } else {
            	
                if (sMoveMoney.trim().length() > 0){
                    //按现金转移
                	try {
						dMoveMoney = Double.parseDouble(sMoveMoney);
					} catch (NumberFormatException e) {
						 CError.buildErr(this, "录入的转移金额格式不是正确的数字!(第"+(i+1)+"行)");
			             return false;
					}
                } else{
                    //按比例转移
                	double dMoveRate = 0.0;
                    try {
						dMoveRate = Double.parseDouble(sMoveRate);
					} catch (NumberFormatException e) {
						CError.buildErr(this, "录入的转移比例格式不是正确的数字!(第"+(i+1)+"行)");
			            return false;
					}
					if(dMoveRate>1 ||dMoveRate<=0)
					{
						CError.buildErr(this, "请录入正确的转移比例格式(大于0小于1的数字)(第"+(i+1)+"行)");
			            return false;
					}
					dMoveMoney = Double.parseDouble(outSumMoney)*dMoveRate;
                }
            }
            logger.debug("该帐户的可用的本息和为:"+outSumMoney);
            logger.debug("该帐户的当前的利息和为:"+outInterest);
            logger.debug("本次转移的金额为:"+dMoveMoney);
            if(dMoveMoney<=0)
            {
            	CError.buildErr(this, "保单合同号"+outContNo+"的转移金额:"+dMoveMoney+"应大于0!(第"+(i+1)+"行)");
	            return false;
            }
            if(dMoveMoney - Double.parseDouble(outSumMoney)>0){
            	
				CError.buildErr(this, "保单合同号"+outContNo+"的转移金额:"+dMoveMoney+"大于实际该保单集体交费部分的实际可用金额:"+outSumMoney+"(第"+(i+1)+"行)");
	            return false;
            }
            //查询转入的集体交费部分或公共帐户
            
            //转出合同号校验
            if(miContNo.contains(outContNo))
            {
            	//转入帐号集合里已经存在转出帐号,报错
            	CError.buildErr(this, "一次保全同一个帐户不可既转出又转入(第"+(i+1)+"行)");
            	return false;
            }
            
            double dTotleMoveMoney = 0.0;
            if(!moContNo.contains(outContNo))
            {
            	//如果转出帐号在转出集合里不存在,新增加记录
            	moContNo.add(outContNo);
            	dTotleMoveMoney= dMoveMoney;
            	//存进去第一笔钱,哈哈,有了部分准备金了
            	tContMoveMoney.setNameAndValue(outContNo, String.valueOf(dTotleMoveMoney));
            }else {
            	//如果存在,校验钱啊,钱!!!!不能冒了
            	//先把钱取出来
            	String sTotleMoveMoney = (String)tContMoveMoney.getValueByName(outContNo);
            	//销户,兔死狗烹
            	tContMoveMoney.removeByName(outContNo);
            	dTotleMoveMoney = Double.parseDouble(sTotleMoveMoney)+dMoveMoney;
                if(dTotleMoveMoney - Double.parseDouble(outSumMoney)>0){
                	
    				CError.buildErr(this, "保单合同号"+outContNo+"的转移总金额已大于实际该保单集体交费部分的实际可用金额:"+outSumMoney+"!(第"+(i+1)+"行)");
    	            return false;
                }
                //连同本次转移的再存进去
                logger.debug("该帐户累计转移的金额已达:"+dTotleMoveMoney);
                tContMoveMoney.setNameAndValue(outContNo, String.valueOf(dTotleMoveMoney));
            }
            
            //对转入帐户进行校验
            if(moContNo.contains(inContNo))
            {
            	//转出帐号集合里已经存在转入帐号,报错
            	CError.buildErr(this, "一次保全同一个帐户不可既转出又转入(第"+(i+1)+"行)");
            	return false;
            }
            
            if(!miContNo.contains(inContNo))
            {
            	//如果转入帐号在转入集合里不存在,新增加记录
            	miContNo.add(inContNo);
            }
            //开始添加转移记录吧,NND
    		LPAccMoveSchema tLPAccMoveSchema = new LPAccMoveSchema();
    		tLPAccMoveSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
    		tLPAccMoveSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
    		tLPAccMoveSchema.setPolNo(outLCInsureAccClassSchema.getPolNo());
    		tLPAccMoveSchema.setInsuAccNo(outLCInsureAccClassSchema.getInsuAccNo());
    		tLPAccMoveSchema.setPayPlanCode(outLCInsureAccClassSchema.getPayPlanCode());
    		tLPAccMoveSchema.setRiskCode(outLCInsureAccClassSchema.getRiskCode());
    		tLPAccMoveSchema.setAccType(outLCInsureAccClassSchema.getAccType());
    		tLPAccMoveSchema.setAccMoveType("C");
    		tLPAccMoveSchema.setOtherNo(inLCInsureAccClassSchema.getPolNo());
    		tLPAccMoveSchema.setOtherType(outLCInsureAccClassSchema.getOtherType());
    		tLPAccMoveSchema.setAccAscription(outLCInsureAccClassSchema.getAccAscription());
    		tLPAccMoveSchema.setAccMoveNo(inLCInsureAccClassSchema.getInsuAccNo());
    		tLPAccMoveSchema.setAccMoveCode(inLCInsureAccClassSchema.getPayPlanCode());
    		tLPAccMoveSchema.setAccMoveBala(dMoveMoney);
    		tLPAccMoveSchema.setOperator(mGlobalInput.Operator);
    		tLPAccMoveSchema.setMakeDate(PubFun.getCurrentDate());
    		tLPAccMoveSchema.setMakeTime(PubFun.getCurrentTime());
    		tLPAccMoveSchema.setModifyDate(PubFun.getCurrentDate());
    		tLPAccMoveSchema.setModifyTime(PubFun.getCurrentTime());
    		LPAccMoveDB tLPAccMoveDB = new LPAccMoveDB();
    		tLPAccMoveDB.setSchema(tLPAccMoveSchema);
    		if(tLPAccMoveDB.getInfo())
    		{
            	CError.buildErr(this, "不可以重复导入相同的转移记录(第"+(i+1)+"行)");
            	return false;
    		}
    		tLPAccMoveSet.add(tLPAccMoveSchema);
            if (nodeName.equals("#text")) {
                continue;
            }
            if (nodeName.equals("ROW")) {
            }
            


        }
        LPAccMoveSchema tLPAccMoveSchema = null;
        for(int i = 1;i<=tLPAccMoveSet.size();i++)
        {
        	tLPAccMoveSchema = tLPAccMoveSet.get(i);
        	
    		LPInsureAccClassSchema outLPInsureAccClassSchema = new LPInsureAccClassSchema();
    		LPInsureAccClassSchema inLPInsureAccClassSchema = new LPInsureAccClassSchema();
    		LPInsureAccClassSet tLPInsureAccClassSet = new LPInsureAccClassSet();
    		outLPInsureAccClassSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
    		outLPInsureAccClassSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
    		outLPInsureAccClassSchema.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
    		outLPInsureAccClassSchema.setPolNo(tLPAccMoveSchema.getPolNo());
    		outLPInsureAccClassSchema.setInsuAccNo(tLPAccMoveSchema.getInsuAccNo());
    		outLPInsureAccClassSchema.setPayPlanCode(tLPAccMoveSchema.getPayPlanCode());
    		
    		inLPInsureAccClassSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
    		inLPInsureAccClassSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
    		inLPInsureAccClassSchema.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
    		inLPInsureAccClassSchema.setPolNo(tLPAccMoveSchema.getOtherNo());
    		inLPInsureAccClassSchema.setInsuAccNo(tLPAccMoveSchema.getAccMoveNo());
    		inLPInsureAccClassSchema.setPayPlanCode(tLPAccMoveSchema.getAccMoveCode());
    		
    		tLPInsureAccClassSet.add(outLPInsureAccClassSchema);//先添转出再添转入
    		tLPInsureAccClassSet.add(inLPInsureAccClassSchema);
        	mInputData.clear();
        	mInputData.add(mGlobalInput);
        	mInputData.add(mLPGrpEdorItemSchema);
        	VData tVData = new VData();
			tVData.addElement(mGlobalInput);
			tVData.addElement(mLPGrpEdorItemSchema);
			tVData.addElement(tLPInsureAccClassSet);
			tVData.addElement(String.valueOf(tLPAccMoveSchema.getAccMoveBala()));

        	GEdorCADetailBL tGEdorCADetailBL = new GEdorCADetailBL();
        	if (!tGEdorCADetailBL.submitData(tVData, "INSERT||MONEY")) {
        		CError tError = new CError();
        		tError.moduleName = "GrpEdorCAForLoadBL";
        		tError.functionName = "parserXml";
        		tError.errorMessage = "数据提交失败！";
        		this.mErrors.addOneError(tError);
        		this.mErrors.copyAllErrors(tGEdorCADetailBL.mErrors);
        	}
        }
//      －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//      No.6 解析完删除XML文件
//      －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

        if (!tFile.delete())
        {
        	// @@错误处理
        	CError tError = new CError();
        	tError.moduleName = "GrpEdorCAForLoadBL";
        	tError.functionName = "checkData";
        	tError.errorMessage = "Xml文件删除失败!";
        	this.mErrors.addOneError(tError);
        	return false;
        }
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

        ConfigFileName = filePath + "GrpEdorCA.xml";
        File tFile2 = new File(ConfigFileName);
        if (!tFile2.exists()) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "GrpEdorCAForLoadBL";
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
        ImportFileName = mPreFilePath + FilePath + "BqUp/"+ FileName;
        File tFile = new File(ImportFileName);
        if (!tFile.exists()) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "GrpEdorCAForLoadBL";
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
            tError.moduleName = "GrpEdorCAForLoadBL";
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
        mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData.getObjectByObjectName(
                "LPGrpEdorItemSchema", 0);
        mTransferData = (TransferData) mInputData.getObjectByObjectName(
                "TransferData", 0);
        mPreFilePath = (String) mTransferData.getValueByName("FilePath");
        FileName = (String) mTransferData.getValueByName("FileName");

        if (mGlobalInput == null) {
            CError tError = new CError();
            tError.moduleName = "GrpEdorCAForLoadBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受前台传入的GlobalInput数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (mTransferData == null) {
            CError tError = new CError();
            tError.moduleName = "GrpEdorCAForLoadBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受前台传入的TransferData数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (FileName == null) {
            CError tError = new CError();
            tError.moduleName = "GrpEdorCAForLoadBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受前台传入的FileName数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (mLPGrpEdorItemSchema == null) {
            CError tError = new CError();
            tError.moduleName = "GrpEdorCAForLoadBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受前台传入的团体保单号数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
        tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
        tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
        tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
        mLPGrpEdorItemSchema = tLPGrpEdorItemDB.query().get(1);

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
        
    }

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}

}
