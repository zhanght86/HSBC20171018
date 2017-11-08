package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;

import com.sinosoft.utility.*;

import org.jdom.*;

import java.io.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author ck
 * @version 1.0
 */
public class FileImportPreParePayPersonBL
{
private static Logger logger = Logger.getLogger(FileImportPreParePayPersonBL.class);

    private static String PATH_GRPPOLNO = "/DATASET/TABLE/ROW/FIELD/GrpPolNo[position()=1]";

    //错误处理类，每个需要错误处理的类中都放置该类
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData;
    private GlobalInput tG;

    /** 数据操作字符串 */
    private String mOperate;
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private VData mResult = new VData();

    //应收个人交费表
    private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
    private LJSPayPersonSet mLJSPayPersonInSertSet = new LJSPayPersonSet();
    private LJSPayPersonSet mLJSPayPersonUpdateSet = new LJSPayPersonSet();
    private LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
    private LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();

    //FIleImport
    private TransferData mTransferData = new TransferData();
    private String grpPolNo;
    private String GrpContNo;
    private String mImportFileName;
    private String mConfigFileName;
    private String XmlFileName;
    private String XmlPath;

    //存储要检查的字段
    private VData checkVData = new VData();
    private String mFilePath = "C:/Lis/";
    private String ParseRootPath = "/DATASET/BATCHID";
    private String ParsePath = "/DATASET/TABLE/ROW";
    private String sysvar = "PrePayPerson_File";
    private String mPayType;

    //业务处理相关变量
    public FileImportPreParePayPersonBL()
    {
    }

    public static void main(String[] args)
    {
    }

    //传输数据的公共方法
    public boolean submitData(VData cInputData, String cOperate)
    {
        this.mOperate = cOperate;

        if (!getInputData(cInputData))
        {
            return false;
        }

        if (!dealData())
        {
            return false;
        }

        if (!prepareOutputData())
        {
            return false;
        }

        FileImportPreParePayPersonBLS tFileImportPreParePayPersonBLS = new FileImportPreParePayPersonBLS();
        tFileImportPreParePayPersonBLS.submitData(mResult, cOperate);

        //如果有需要处理的错误，则返回
        if (tFileImportPreParePayPersonBLS.mErrors.needDealError())
        {
            CError tError = new CError();
            tError.moduleName = "FileImportPreParePayPersonBLS";
            tError.functionName = "BLS";
            tError.errorMessage = "插入数据错误!";
            this.mErrors.addOneError(tError);

            return false;
        }

        mInputData = null;

        return true;
    }

    //根据前面的输入数据，进行逻辑处理
    //如果在处理过程中出错，则返回false,否则返回true
    private boolean dealData()
    {
        try
        {
            if (!this.parseXMLandExcel())
            {
                return false;
            }

            logger.debug("ReportJSImportAfterInitService生成Xml成功:" +
                               PubFun.getCurrentTime());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();

            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "CircAutoImportBL";
            tError.functionName = "submitData";
            tError.errorMessage = "导入文件格式有误!" + ex.getMessage();
            this.mErrors.addOneError(tError);

            return false;
        }

        logger.debug("结束时间:" + PubFun.getCurrentTime());

        return true;
    }

    /**
      * 从输入数据中得到所有对象
      *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
      */
    private boolean getInputData(VData mInputData)
    {
        mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData",
                                                                        0);
        tG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
        mOperate = tG.Operator;

        grpPolNo = (String) mTransferData.getValueByName("GrpPolNo");
        GrpContNo = (String) mTransferData.getValueByName("GrpContNo");
        mImportFileName = (String) mTransferData.getValueByName("FileName");
        mConfigFileName = (String) mTransferData.getValueByName("ConfigFileName");
        XmlPath = (String) mTransferData.getValueByName("XmlPath");

        if ((mOperate == null) || mOperate.trim().equals(""))
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ReportJSImportAfterInitService";
            tError.functionName = "checkData";
            tError.errorMessage = "操作员代码丢失！";
            this.mErrors.addOneError(tError);

            return false;
        }

        if ((grpPolNo == null) || grpPolNo.trim().equals(""))
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ReportJSImportAfterInitService";
            tError.functionName = "checkData";
            tError.errorMessage = "请输入团单号码!";
            this.mErrors.addOneError(tError);

            return false;
        }

        if ((mImportFileName == null) || mImportFileName.trim().equals(""))
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ReportJSImportAfterInitService";
            tError.functionName = "checkData";
            tError.errorMessage = "前台传入导入Excel文件名信息丢失!";
            this.mErrors.addOneError(tError);

            return false;
        }

        if ((mConfigFileName == null) || mConfigFileName.trim().equals(""))
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ParseGuideIn";
            tError.functionName = "checkData";
            tError.errorMessage = "前台传入的XML配置文件名信息丢失!";
            this.mErrors.addOneError(tError);

            return false;
        }
        if ((XmlPath == null) || XmlPath.trim().equals(""))
        {
        	// @@错误处理
        	CError tError = new CError();
        	tError.moduleName = "ParseGuideIn";
        	tError.functionName = "checkData";
        	tError.errorMessage = "前台传入的XML配置文件路径错误!";
        	this.mErrors.addOneError(tError);
        	
        	return false;
        }
        
        LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
        tLCGrpPolDB.setGrpContNo(grpPolNo);
        LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
        if(tLCGrpPolSet.size()>0)
        {
        	LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
        	tLMRiskAppDB.setRiskCode(tLCGrpPolSet.get(1).getRiskCode());
        	if(tLMRiskAppDB.getInfo())
        	{
        		if("1".equals(tLMRiskAppDB.getHealthType()))
        		{
        			this.mPayType="TM";
        		}
        		else
        		{
        			this.mPayType="ZC";
        		}
        	}
        }
        

        tLJSPayPersonSchema.setGrpContNo(grpPolNo);
        tLJSPayPersonSchema.setPayType(this.mPayType);

        LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
        tLJSPayPersonDB.setSchema(tLJSPayPersonSchema);
        tLJSPayPersonSet = tLJSPayPersonDB.query();

        //    if(tLJSPayPersonSet.size()>0)
        //    {
        //      CError tError = new CError();
        //      tError.moduleName = "ReportJSImportAfterInitService";
        //      tError.functionName = "checkData";
        //      tError.errorMessage = "个人应交表已有数据，请先删除再导入!";
        //      this.mErrors .addOneError(tError) ;
        //      return false;
        //    }
        //确定导入的物理源EXCEL文件（如：ExcelImportLFFinace.xml）已到指定目的地存在
        if (!this.checkImportFile())
        {
            return false;
        }

        int i = 0;

        //确定配置文件（如：ExcelImportLFFinaceConfig.xml）存在
        if (!this.checkImportConfigFile())
        {
            return false;
        }

        return true;
    }

    /**
     * 检验文件是否存在
     * @return
     */
    private boolean checkXmlFile()
    {
        //    XmlFileName = (String)mTransferData.getValueByName("FileName");
        File tFile = new File(XmlFileName);

        if (!tFile.exists())
        {
            LDSysVarDB tLDSysVarDB = new LDSysVarDB();
            tLDSysVarDB.setSysVar("TranDataPath");

            if (!tLDSysVarDB.getInfo())
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "ParseXML";
                tError.functionName = "checkData";
                tError.errorMessage = "缺少文件导入路径!";
                this.mErrors.addOneError(tError);

                return false;
            }
            else
            {
                mFilePath = tLDSysVarDB.getSysVarValue();
            }

            File tFile1 = new File(mFilePath);

            if (!tFile1.exists())
            {
                tFile1.mkdirs();
            }

            XmlFileName = mFilePath + XmlFileName;

            File tFile2 = new File(XmlFileName);

            if (!tFile2.exists())
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "ReportJSImportAfterInitService";
                tError.functionName = "checkData";
                tError.errorMessage = "请上传相应的数据文件到指定路径" + mFilePath + "!";
                this.mErrors.addOneError(tError);

                return false;
            }
        }

        return true;
    }

    /**
     * 得到生成文件路径
     * @return
     */

    //
    private boolean getFilePath(String name)
    {
        LDSysVarDB tLDSysVarDB = new LDSysVarDB();
        tLDSysVarDB.setSysVar(name);

        if (!tLDSysVarDB.getInfo())
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ParseXML";
            tError.functionName = "getFilePath";
            tError.errorMessage = "缺少文件导入路径!";
            this.mErrors.addOneError(tError);

            return false;
        }
        else
        {
            mFilePath = tLDSysVarDB.getSysVarValue();
        }

        return true;
    }

    /**
     * 检查导入配置文件是否存在
     * @return
     */
    private boolean checkImportConfigFile()
    {
    	//采用相对路径
        this.getFilePath("PrePayPerson");
    	mFilePath =XmlPath+mFilePath;
        File tFile1 = new File(mFilePath);

        if (!tFile1.exists())
        {
            tFile1.mkdirs();
        }

        String tConfigFileName = mFilePath + mConfigFileName;
        File tFile2 = new File(tConfigFileName);

        if (!tFile2.exists())
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ReportJSImportAfterInitService";
            tError.functionName = "checkData";
            tError.errorMessage = "请上传配置文件到指定路径" + mFilePath + "!";
            this.mErrors.addOneError(tError);

            return false;
        }

        logger.debug("物理导入Excel文件的配置模板文件:" + mConfigFileName + "在" +
                           mFilePath + "下已存在！");

        return true;
    }

    /**
     * 初始化上传文件
     * @return
     */
    private boolean checkImportFile()
    {
        this.getFilePath("PrePayPerson_File");

        String tFileName = mFilePath + mImportFileName;
        File tFile = new File(tFileName);

        if (!tFile.exists())
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ReportJSImportAfterInitService";
            tError.functionName = "checkImportFile";
            tError.errorMessage = "未上传文件到指定路径" + mFilePath + "!";
            this.mErrors.addOneError(tError);

            return false;
        }

        logger.debug("物理导入Excel文件:" + mImportFileName + "在路径" +
                           mFilePath + "下已存在！");

        return true;
    }

    /**
     * 解析操作
     * @return
     */
    private boolean parseXMLandExcel() throws Exception
    {
        //更据配置模板，解析EXCEL文件，更据配置模板提取信息生成配置模板指定格式的XML文件
        ParserXml tParserXml = new ParserXml();

        //设置文件输入输出路径
        //       getFilePath("PrePayPerson_File");
        //设置xml配置文件的路径
        tParserXml.setConfigFilePathName(mFilePath);

        //设置上传文件的路径
        getFilePath("PrePayPerson_File");
        tParserXml.setFilePathName(mFilePath);

        //设置物理源EXCEL文件名
        String tFileName = getDefaultName(mImportFileName);

        if (!tParserXml.setDataExcelFileName(tFileName))
        {
            mErrors.copyAllErrors(tParserXml.mErrors);

            return false;
        }

        //设指物理源EXCEL文件的配置文件名
        String tFileName2 = getDefaultName(mConfigFileName);

        if (!tParserXml.setConfigFileName(tFileName2))
        {
            mErrors.copyAllErrors(tParserXml.mErrors);

            return false;
        }

        // 转换操作，将磁盘投保文件转换成指定格式的XML文件。
        //设置要传送的参数（操作员代码）
        tParserXml.setOperator(mOperate);

        //设置要传送的参数（团单号）
        tParserXml.setGrpPolNo(grpPolNo);
        tParserXml.setGrpContNo(GrpContNo);
        logger.debug("传入的团单号为：" + grpPolNo);
        
        tParserXml.setPayType(this.mPayType);

        if (!tParserXml.transform())
        {
            mErrors.copyAllErrors(tParserXml.mErrors);

            return false;
        }

        //得到生成的目标XML数据
        Element tElement = tParserXml.getXMLTarget();

        if (tElement == null)
        {
            mErrors.copyAllErrors(tParserXml.mErrors);

            return false;
        }

        //	// 生成的目标XML数据到目标数据库表中
        addCheckData();

        if (!tParserXml.insertIntoPhyTable(tElement, checkVData))
        {
            mErrors.copyAllErrors(tParserXml.mErrors);

            return false;
        }
        else
        {
            if ((tParserXml.getResult() != null) &&
                    (tParserXml.getResult().size() >= 0))
            {
                //先删除再插入最新的数据
                MMap map = new MMap();
                map.put(tLJSPayPersonSet, "DELETE");

                VData mVData = new VData();
                mVData = tParserXml.getResult();

                VData tVData = new VData();
                tVData.add(map);
                mResult.add(tVData);
                mResult.add(mVData);
                logger.debug("in ReportJSImportAfterInitService");
            }
        }

        return true;
    }

    //存储要检查的数据，保证导入数据的正确性
    private void addCheckData()
    {
        checkVData.add("polno");

        //        checkVData.add("dutycode");
        //        checkVData.add("payplancode");
        checkVData.add("grpcontno");
    }

    /**
    * 生成缺省文件名（）
    * @return
    */
    private String getDefaultName(String tFileName)
    {
        String tBatchID = "";
        File tFile = new File(tFileName);
        String name = tFile.getName();

        for (int i = name.length() - 1; i >= 0; i--)
        {
            if (name.substring(i - 1, i).equals("."))
            {
                tBatchID = name.substring(0, i - 1);

                //logger.debug("BatchID:"+tBatchID);
                return tBatchID;
            }
        }

        return tBatchID;
    }

    //准备往后层输出所需要的数据
    //输出：如果准备数据时发生错误则返回false,否则返回true
    private boolean prepareOutputData()
    {
        return true;
    }
}
