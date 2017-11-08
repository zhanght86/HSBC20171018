package com.sinosoft.lis.get;
import org.apache.log4j.Logger;

import javax.xml.parsers.*;

import org.w3c.dom.*;

import com.sinosoft.lis.bqgrp.LCTempCustomerVTSParser;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

import org.apache.xpath.XPathAPI;
import org.apache.xpath.objects.XObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Title:GEdorTypeCAForLoadBL.java </p>
 * <p>Description: 分账户给付信息导入</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * @author sunsx 2009-02-03
 * @version 1.0
 */
public class SubAccForLoadBL {
private static Logger logger = Logger.getLogger(SubAccForLoadBL.class);
    public SubAccForLoadBL() {
        bulidDocument();
    }

    //错误处理类
    public CErrors mErrors = new CErrors();
    private VData mInputData = new VData();
    /** 往前面传输数据的容器 */
    private VData mResult = new VData();
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

    private String mGrpContNo= "";

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
                tError.moduleName = "SubAccForLoadBL";
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
                tError.moduleName = "SubAccForLoadBL";
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
        List iContNoList = new ArrayList();
    
        MMap tMap = new MMap();
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
            String tContNo = parseNode(node, "ContNo");//合同号

            
            if(iContNoList.contains(tContNo))
            {
                // @@错误处理
                CError.buildErr(this, "导入的模板中出现重复合同号:"+tContNo+"(第"+(i+1)+"行)");
                return false;
            }

            LCContDB tLCContDB = new LCContDB();
            

            //查询转出帐户信息
            tLCContDB.setContNo(tContNo);
            LCContSet tLCContSet =tLCContDB.query();
            if(tLCContSet == null ||tLCContSet.size()<1)
            {
                CError tError = new CError();
                tError.moduleName = "SubAccForLoadBL";
                tError.functionName = "ParseXml";
                tError.errorMessage = "未查询到合同号为"+tContNo+"的个人保单信息！(第"+(i+1)+"行)";
                this.mErrors.addOneError(tError);
                return false;
            }
            LCContSchema tLCContSchema = tLCContSet.get(1);

            if(!tLCContSchema.getGrpContNo().equals(mGrpContNo))
            {
            	  CError.buildErr(this, "合同号为"+tContNo+"个人保单信息不属于本团单！(第"+(i+1)+"行)");
                  return false;
            }

            
            iContNoList.add(tContNo);
            
            String tPayMode = parseNode(node, "PayMode");//给付方式 对应BNF的Remark
            String tDrawer = parseNode(node, "Drawer");//领取人姓名,对应BNF的Name
            String tIDNo =  parseNode(node, "IDNo");//领取人的身份证号,对应BNF的IDNo
            String tBankCode = parseNode(node, "BankCode");//领取人的开户银行编码,对应BNF的BankCode
            String tBankAccNo = parseNode(node, "BankAccNo");//领取人的开户银行账户,对应BNF的BankAccNo
            String tAccName = parseNode(node, "AccName");//领取人的开户银行户名,对应BNF的AccName
            if(tPayMode == null || tDrawer == null || tIDNo == null)
            {
            	CError.buildErr(this, "合同号为"+tContNo+"给付信息录入不完整(第"+(i+1)+"行)");
            	return false;
            }
            if(tPayMode.equals("4")){
            	if(tBankCode == null ||tBankAccNo == null|| tAccName == null){
            		CError.buildErr(this, "合同号为"+tContNo+"的给付方式选择了银行转帐,请录入银行的相关信息(第"+(i+1)+"行)");
            		return false;
            	}
            	LDBankDB tLDBankDB = new LDBankDB();
            	tLDBankDB.setBankCode(tBankCode);
            	LDBankSet tLDBankSet = tLDBankDB.query();
            	if(tLDBankSet == null || tLDBankSet.size() <1)
            	{
            		CError.buildErr(this, "查询合同号为"+tContNo+"的给付银行(编码:"+tBankCode+")信息出错(第"+(i+1)+"行)");
            		return false;
            	}
            }
            
        	if(tBankCode==null){
        		tBankCode = "";
        	}
        	if(tBankAccNo==null){
        		tBankAccNo = "";
        	}
        	if(tAccName==null){
        		tAccName = "";
        	}
            


            if (nodeName.equals("#text")) {
                continue;
            }
            if (nodeName.equals("ROW")) {
            }
    		String tSQL = "Select distinct 1 From LjsGet a,LCCont b Where a.OtherNo=b.ContNo and a.OtherNoType='2' "
			    + " and a.GetDate <= now() and b.GrpContNo ='?mGrpContNo?' and b.ContNo = '?tContNo?'";
    		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
    		sqlbv.sql(tSQL);
    		sqlbv.put("mGrpContNo", mGrpContNo);
    		sqlbv.put("tContNo", tContNo);
    		ExeSQL tExeSQL = new ExeSQL();
    		String tHasGetFlag = tExeSQL.getOneValue(sqlbv);
    		if (tHasGetFlag == null || !"1".equals(tHasGetFlag)) {
    			//说明没有领取信息的记录存在
    			CError.buildErr(this, "该团单下个人单"+tContNo+"无需要领取的记录，请检查！(第"+(i+1)+"行)");
    			return false;
    		}
    		String tSql = "Update LjsGet set "
				+ "PayMode = '?tPayMode?', Drawer = '?tDrawer?', "
				+ "BankAccNo = '?tBankAccNo?', AccName = '?tAccName?' ,"
				+ "DrawerID = '?tIDNo?', BankCode = '?tBankCode?' Where OtherNo = '?tContNo?' and OtherNoType = '2' and GetDate <= now()";
    		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
    		sqlbv1.sql(tSql);
    		sqlbv1.put("tPayMode", tPayMode);
    		sqlbv1.put("tDrawer", tDrawer);
    		sqlbv1.put("tBankAccNo", tBankAccNo);
    		sqlbv1.put("tAccName", tAccName);
    		sqlbv1.put("tIDNo", tIDNo);
    		sqlbv1.put("tBankCode", tBankCode);
    		sqlbv1.put("tContNo", tContNo);
    		tMap.put(sqlbv1, "UPDATE");
           

        }
        
        
        VData tVData = new VData();
        tVData.add(tMap);
        //数据提交
        PubSubmit tSubmit = new PubSubmit();
        if (!tSubmit.submitData(tVData, "")) {

        	// @@错误处理
        	this.mErrors.copyAllErrors(tSubmit.mErrors);
        	CError tError = new CError();
        	tError.moduleName   = "SubAccForLoadBL";
        	tError.functionName = "submitData";
        	tError.errorMessage = "数据提交失败!";
        	this.mErrors.addOneError(tError);
        	logger.debug("return false;");
        	return false;
        }
        
//      －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//      No.6 解析完删除XML文件
//      －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

        if (!tFile.delete())
        {
        	
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

        ConfigFileName = filePath + "GrpGetAcc.xml";
        File tFile2 = new File(ConfigFileName);
        if (!tFile2.exists()) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "SubAccForLoadBL";
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
        ImportFileName = mPreFilePath + FilePath+ "BqUp/" + FileName;
        File tFile = new File(ImportFileName);
        if (!tFile.exists()) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "SubAccForLoadBL";
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
            tError.moduleName = "SubAccForLoadBL";
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

        if (mGlobalInput == null) {
            CError tError = new CError();
            tError.moduleName = "SubAccForLoadBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受前台传入的GlobalInput数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (mTransferData == null) {
            CError tError = new CError();
            tError.moduleName = "SubAccForLoadBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受前台传入的TransferData数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (FileName == null) {
            CError tError = new CError();
            tError.moduleName = "SubAccForLoadBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受前台传入的FileName数据失败!";
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

}
