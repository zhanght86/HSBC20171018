package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import java.util.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.f1print.PremBankPubFun;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

import java.io.*;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.Source;
import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamSource;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 银行文件转换到数据模块</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * @version 1.0
 */

public class ReadFromFileBL {
private static Logger logger = Logger.getLogger(ReadFromFileBL.class);
  /** 传入数据的容器 */
  private VData mInputData = new VData();
  /** 传出数据的容器 */
  private VData mResult = new VData();
  /** 数据操作字符串 */
  private String mOperate;
  /** 错误处理类 */
  public  CErrors mErrors = new CErrors();

  //业务数据
  private TransferData inTransferData = new TransferData();
  private GlobalInput inGlobalInput = new GlobalInput();
  private String fileName = "";

  private LYReturnFromBankSet outLYReturnFromBankSet = new LYReturnFromBankSet();
  private LYBankLogSet outLYBankLogSet = new LYBankLogSet();
  private String serialNo = "";
  private String bankCode = "";
  private String busstype = "";              //业务类型：S－代收、F－代付
  private String banksuccflag=null;
  private String appPath=null;

  private Document dataDoc = null;
  private Document resultDoc = null;

  private Reflections mReflections = new Reflections();
  private PremBankPubFun mPremBankPubFun = new PremBankPubFun();


  public ReadFromFileBL() {
  }

  /**
   * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
   * @param cInputData 传入的数据,VData对象
   * @param cOperate 数据操作字符串，主要包括"READ"和""
   * @return 布尔值（true--提交成功, false--提交失败）
   */
  public boolean submitData(VData cInputData, String cOperate) {
    //将操作数据拷贝到本类中
    this.mInputData = (VData)cInputData.clone();
    this.mOperate = cOperate;

    //得到外部传入的数据,将数据备份到本类中
    if (!getInputData()) return false;
    logger.debug("---End getInputData---");

		// 增加并发校验
	String key = "Bank" + serialNo;
	PubLock tPubLock = new PubLock();
	if (!tPubLock.lock(key, "读取" + serialNo + "回盘数据")) {
		this.mErrors.copyAllErrors(tPubLock.mErrors);
		return false;
	}
	try{

    //进行业务处理
    if (!dealData()) return false;
    logger.debug("---End dealData---");

    //银行代收
    if (mOperate.equals("READ")) {
      //准备往后台的数据
      if (!prepareOutputData()) return false;
      logger.debug("---End prepareOutputData---");

      logger.debug("Start ReadFromFile BLS Submit...");
      ReadFromFileBLS tReadFromFileBLS = new ReadFromFileBLS();
      if(tReadFromFileBLS.submitData(mInputData, cOperate) == false)	{
        // @@错误处理
        this.mErrors.copyAllErrors(tReadFromFileBLS.mErrors);
        mResult.clear();
        return false;
      }
      logger.debug("End ReadFromFile BLS Submit...");

      //如果有需要处理的错误，则返回
      if (tReadFromFileBLS.mErrors .needDealError())  {
        this.mErrors.copyAllErrors(tReadFromFileBLS.mErrors ) ;
      }
    }
    //银行代付
    else if (mOperate.equals("")) {
    }
	}finally{
		if (!tPubLock.unLock(key)) {
			CError.buildErr(this, serialNo + "解锁失败:"
					+ tPubLock.mErrors.getFirstError());
			return false;
		}
  	}
    return true;
  }

  /**
   * 将外部传入的数据分解到本类的属性中
   * @param: 无
   * @return: boolean
   */
  private boolean getInputData()	{
    try {
      inTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData", 0);
      inGlobalInput = (GlobalInput)mInputData.getObjectByObjectName("GlobalInput", 0);
      //获取返回文件名称
      fileName = (String)inTransferData.getValueByName("fileName");
      fileName = fileName.replace('\\', '/');

      serialNo = (String)inTransferData.getValueByName("serialNo");
      bankCode = (String)inTransferData.getValueByName("bankCode");
      busstype = (String)inTransferData.getValueByName("bussType");
      appPath = (String)inTransferData.getValueByName("appPath");

    }
    catch (Exception e) {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "ReadFromFileBL";
      tError.functionName = "getInputData";
      tError.errorMessage = "接收数据失败!!";
      this.mErrors .addOneError(tError) ;
      return false;
    }

    return true;
  }

  /**
   * 生成银行日志数据
   * @param tLYSendToBankSchema
   * @return
   */
  private LYBankLogSet getLYBankLog() {
    LYBankLogSchema tLYBankLogSchema = new LYBankLogSchema();
    LYBankLogSet tLYBankLogSet = new LYBankLogSet();

    //获取日志记录
    //tLYBankLogSchema.setSerialNo(tLYReturnFromBankSet.get(1).getSerialNo());
    tLYBankLogSchema.setSerialNo(serialNo);
    tLYBankLogSet.set(tLYBankLogSchema.getDB().query());

    if (tLYBankLogSet.size() > 0) {
      tLYBankLogSchema.setSchema(tLYBankLogSet.get(1));

      if (tLYBankLogSchema.getInFile() != null) throw new NullPointerException("该批次号的银行返回文件已经上传过了，在等待核销处理中……");

      tLYBankLogSchema.setReturnDate(PubFun.getCurrentDate());
      tLYBankLogSchema.setReturnOperator(inGlobalInput.Operator);
      tLYBankLogSchema.setInFile(fileName);

      tLYBankLogSet.clear();
      tLYBankLogSet.add(tLYBankLogSchema);
    }

    return tLYBankLogSet;
  }

  /**
   * 创建一个xml文档对象DOM
   * @return
   */
  private Document buildDocument() {
    try {
      //Create the document builder
      DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docbuilder = dbfactory.newDocumentBuilder();

      //Create the new document(s)
      return docbuilder.newDocument();
    }
    catch (Exception e) {
      logger.debug("Problem creating document: " + e.getMessage());
      return null;
    }
  }

  /**
   * 获取xsl文件路径
   * @return
   */
  public String getXslPath()
  {
      String xslpath = "";

      LDBankDB tLDBankDB = new LDBankDB();
      tLDBankDB.setBankCode(bankCode);
      //如果有该银行数据，则根据业务类型选择对应的发盘模板路径（默认打开代收的路径）
      LDBankSet tLDBankSet= tLDBankDB.query();
      if(tLDBankSet.size()!=1)
    	  throw new RuntimeException("ldbank中的银行配置信息错误");
      LDBankSchema tLDBankSchema = tLDBankSet.get(1);
      xslpath = (busstype.equals("F")) ? tLDBankSchema.getAgentGetReceiveF() : tLDBankSchema.getAgentPayReceiveF();
      banksuccflag = (busstype.equals("F")) ? tLDBankSchema.getAgentGetSuccFlag() : tLDBankSchema.getAgentPaySuccFlag();
//      logger.debug("xsl : " + xslpath);
      return new File(appPath,xslpath).getAbsolutePath();
  }

  /**
   * Simple sample code to show how to run the XSL processor
   * from the API.
   */
  public boolean xmlTransform() {
    try {
      String xslPath = getXslPath();
      logger.debug("xslPath:" + xslPath);

      File fStyle = new File(xslPath);

      Source source = new DOMSource(dataDoc);
      Result result = new DOMResult(resultDoc);
      Source style = new StreamSource(fStyle);

      //Create the Transformer
      TransformerFactory transFactory = TransformerFactory.newInstance();
      Transformer transformer = transFactory.newTransformer(style);

      //Transform the Document
      transformer.transform(source, result);

      logger.debug("Transform Success!");
    }
    catch(Exception e) {
      e.printStackTrace();
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "WriteToFileBLS";
      tError.functionName = "SimpleTransform";
      tError.errorMessage = "Xml处理失败!!"+e.getMessage();
      this.mErrors .addOneError(tError);
      return false;
    }

    return true;
  }

  /**
   * 从发送盘表中获取数据
   * @param tLYReturnFromBankSchema
 * @param num 
   * @return
   */
  private boolean prepareReturnData(LYReturnFromBankSchema tLYReturnFromBankSchema, LYReturnFromBankSet tLYReturnFromBankSet) {
      LYSendToBankSchema tLYSendToBankSchema = new LYSendToBankSchema();

      //批次号，从操作界面中获取
//      tLYSendToBankSchema.setSerialNo(tLYReturnFromBankSchema.getSerialNo());

      //账号，从返回数据中取，因为有些银行（如工行）的账号中最后有*号，但发送数据中没有，所以会匹配失败
//      tLYSendToBankSchema.setAccNo(tLYReturnFromBankSchema.getAccNo());
      //金额，从返回数据中取
//      tLYSendToBankSchema.setPayMoney(tLYReturnFromBankSchema.getPayMoney());
      //保单号，从返回数据中取，有些银行不一定有，已经没有用，所有银行都不发
//      if (tLYReturnFromBankSchema.getPolNo() != null) {
//        tLYSendToBankSchema.setPolNo(tLYReturnFromBankSchema.getPolNo());
//      }

      //根据以上数据查询发送银行盘表
//      LYSendToBankDB tLYSendToBankDB = new LYSendToBankDB();
//      tLYSendToBankDB.setSchema(tLYSendToBankSchema);
//      LYSendToBankSet tLYSendToBankSet = tLYSendToBankDB.query();

      boolean enough = false;
      String strSql = "select * from lysendtobank where serialno='" + "?serialno?" + "'";

      if (tLYReturnFromBankSchema.getAccNo() != null) {
        strSql = strSql + " and AccNo='" + "?AccNo?" + "'";
        enough = true;
      }
      if (tLYReturnFromBankSchema.getPayCode() != null) {
        strSql = strSql + " and (PayCode='" + "?PayCode?" + "'" 
        		+" or PayCode='" + "?PayCode?" + "')";
        enough = true;
      }
      //去掉回盘时金额的判断－农行回盘信息中没有金额的信息，而lyreturnfrombank对象中paymoney字段的默认值为0
      if (tLYReturnFromBankSchema.getPayMoney() > 0)
      {
        strSql = strSql + " and paymoney=" + "?tLYReturn?";
        
      }
      strSql = strSql + " order by modifydate";

      if (!enough) {
//        throw new Exception("获取返回数据不足，必须有账号或者收据号！");
    	  return false;	// don't borther others, just consider as failure.
      }
      SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
      sqlbv1.sql(strSql);
      sqlbv1.put("serialno", tLYReturnFromBankSchema.getSerialNo());
      sqlbv1.put("AccNo", tLYReturnFromBankSchema.getAccNo());
      sqlbv1.put("PayCode", tLYReturnFromBankSchema.getPayCode());
      sqlbv1.put("PayCode", tLYReturnFromBankSchema.getPayCode().trim().substring(2));
      sqlbv1.put("tLYReturn",  tLYReturnFromBankSchema.getPayMoney());
      logger.debug("strSql: " + strSql);
      LYSendToBankSet tLYSendToBankSet = tLYSendToBankSchema.getDB().executeQuery(sqlbv1);

      if (tLYSendToBankSet.size() == 0) {
//    	  String message = "第"+(num+1)+"行回盘记录无法与发送盘表数据匹配！";
//    	  if(tLYReturnFromBankSchema.getAccNo() != null)
//    		  message+="银行编码："+tLYReturnFromBankSchema.getAccNo()+"。";
//    	  if(tLYReturnFromBankSchema.getPayCode()!=null)
//    		  message+="收据号："+tLYReturnFromBankSchema.getPayCode()+"。";
//    	  if (tLYReturnFromBankSchema.getPayMoney() > 0)
//    		  message+="金额："+tLYReturnFromBankSchema.getPayMoney()+"。";
//		throw new Exception(message);
    	  return false;	// don't borther others, just consider as failure.
      }
     else if (tLYSendToBankSet.size() == 1) {
        tLYSendToBankSchema = tLYSendToBankSet.get(1);
      }
      //确保同一账号，同一金额的多条数据能够被正确取出
      else {
        for (int i=0; i<tLYSendToBankSet.size(); i++) {
          boolean isExist = false;

          for (int j=0; j<tLYReturnFromBankSet.size(); j++) {
            //PayCode对应应收总表的getnoticeno,唯一
            if (tLYSendToBankSet.get(i+1).getPayCode().equals(tLYReturnFromBankSet.get(j+1).getPayCode())) {
              isExist = true;
              break;
            }
          }

          if (!isExist) {
            tLYSendToBankSchema = tLYSendToBankSet.get(i+1);
            break;
          }
        }
      }

      //获取数据
      String BankDealDate = tLYReturnFromBankSchema.getBankDealDate();
      String BankSuccFlag = tLYReturnFromBankSchema.getBankSuccFlag();
      
      mReflections.transFields(tLYReturnFromBankSchema,
					tLYSendToBankSchema);
      
      //20110513 modified by Cuizhe 有的回盘文件中没有要求返回银行处理日期，默认取系统当前日期
            if ( BankDealDate == null || BankDealDate.equals ( "" )|| BankDealDate.equals ( "null" ) )
            {
            	BankDealDate = PubFun.getCurrentDate () ;
            }
            
			// check bankdealdate
			if (BankDealDate==null || PubFun.calInterval(BankDealDate, PubFun.getCurrentDate(), "D") < 0
					|| PubFun.calInterval(BankDealDate, tLYReturnFromBankSchema
							.getSendDate(), "D") > 0)
				throw new IllegalArgumentException("银行回盘时间bankdealdate错误："+BankDealDate);
			if(BankSuccFlag==null || "".equals(BankSuccFlag))
				throw new IllegalArgumentException("银行成功标记BankSuccFlag错误："+BankSuccFlag);

      tLYReturnFromBankSchema.setBankDealDate(BankDealDate);
      if(mPremBankPubFun.verifyBankSuccFlag(banksuccflag, BankSuccFlag))	//使用统一正确编码
    	  tLYReturnFromBankSchema.setBankSuccFlag("0000");
      else
    	  tLYReturnFromBankSchema.setBankSuccFlag(BankSuccFlag);
      //因为没有设计操作员字段，所以暂时保存在备注字段中，add by Minim at 2004-2-5
      tLYReturnFromBankSchema.setRemark(inGlobalInput.Operator);
      tLYReturnFromBankSchema.setModifyDate(PubFun.getCurrentDate());
      tLYReturnFromBankSchema.setModifyTime(PubFun.getCurrentTime());

    return true;
  }

  /**
   * 将数据存入数据库
   * @param tLYReturnFromBankSet
   * @return
   */
  private boolean xmlToDatabase(LYReturnFromBankSet tLYReturnFromBankSet) {
    try {
      displayDocument(resultDoc);

      //get all rows
      NodeList rows = resultDoc.getDocumentElement().getChildNodes();
//      LYBankLogDB mBankLogDB = new LYBankLogDB();
//      mBankLogDB.setSerialNo(serialNo);
//      int Filenum = mBankLogDB.query().get(1).getTotalNum();
////      double stMoney = mBankLogDB.query().get(1).getTotalMoney();         //发盘金额    2008-5-19 取消金额的限制
//      double rtMoney =0.00;                       //返盘金额
//      if(Filenum != rows.getLength())
//      {
//    	  CError.buildErr(this, "返盘文件中笔数为"+rows.getLength()+"，发盘笔数为"+Filenum+"不一致，请核对！");
//    	  return false;
//    	  
//      }


      for(int i=0; i<rows.getLength(); i++){
        //For each row, get the row element and name
        Element thisRow = (Element)rows.item(i);

        //Get the columns for thisRow
        NodeList columns = thisRow.getChildNodes();
        LYReturnFromBankSchema tLYReturnFromBankSchema = new LYReturnFromBankSchema();

        for(int j=0; j<columns.getLength(); j++){
          Element thisColumn = (Element)columns.item(j);

          String colName = thisColumn.getNodeName();
          String colValue = thisColumn.getFirstChild().getNodeValue();
          //logger.debug("colName:" + colName + ":" + colValue);

          //根据标签名往数据库的同名字段里插入数据
          tLYReturnFromBankSchema.setV(colName, colValue);
        }
        //获取返盘金额
//        rtMoney = rtMoney +tLYReturnFromBankSchema.getPayMoney();

        tLYReturnFromBankSchema.setSerialNo(serialNo);
        tLYReturnFromBankSchema.setModifyDate(PubFun.getCurrentDate());
        tLYReturnFromBankSchema.setModifyTime(PubFun.getCurrentTime());

        //从发送盘表中获取数据
        if (!prepareReturnData(tLYReturnFromBankSchema, tLYReturnFromBankSet))
        	continue;

        tLYReturnFromBankSet.add(tLYReturnFromBankSchema);
      }
      
//      //校验发盘金额总数之和与返盘金额总数之和
//      if(Math.abs(rtMoney-stMoney)>0.001)
//      {
//    	  CError.buildErr(this, "返盘文件中金额总数为"+rtMoney+"，发盘文件中金额总数为"+stMoney+"不一致，请核对！");
//    	  return false;
//    	  
//      }
      
    }
    catch (Exception e) {
      e.printStackTrace();
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "WriteToFileBLS";
      tError.functionName = "xmlToDatabase";
      tError.errorMessage = "Xml转入数据库处理失败!" + e.getMessage();
      this.mErrors .addOneError(tError);
      return false;
    }

    return true;
  }

  /**
   * 读取银行返回文件
   * @param fileName
   * @return
   */
  private LYReturnFromBankSet readBankFile(String fileName) {
    LYReturnFromBankSet tLYReturnFromBankSet = new LYReturnFromBankSet();

    //Declare the document
    dataDoc = buildDocument();
    resultDoc = buildDocument();
    BufferedReader in =null;

    try {
      //读入文件到BUFFER中以提高处理效率
      in = new BufferedReader(
          new FileReader(fileName));

      //将所有文本以行为单位读入到VECTOR中
      String strLine = "";

      //创建根标签
      Element dataRoot = dataDoc.createElement("BANKDATA");

      //循环获取每一行
      while(true) {
        strLine = in.readLine();
        if (strLine == null) break;
        strLine = strLine.trim();
        //去掉空行
        if (strLine.length() < 3) continue;
        logger.debug(strLine);
        //logger.debug("strLen: " + strLine.length());

        //Create the element to hold the row
        Element rowEl = dataDoc.createElement("ROW");

        Element columnEl = dataDoc.createElement("COLUMN");
        columnEl.appendChild(dataDoc.createTextNode(strLine));
        rowEl.appendChild(columnEl);

        //Add the row element to the root
        dataRoot.appendChild(rowEl);
      }

      //Add the root to the document
      dataDoc.appendChild(dataRoot);
//      NodeList tables = dataDoc.getDocumentElement().getChildNodes();
      //logger.debug("tables.getLength():" + tables.getLength());

      displayDocument(dataDoc);

      //转换xml
      if (!xmlTransform()) throw new Exception();

      //将数据存入数据库
      if (!xmlToDatabase(tLYReturnFromBankSet)) throw new Exception();
      
      dealBadRecord(tLYReturnFromBankSet);
    }
    catch (Exception e) {
      e.printStackTrace();
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "WriteToFileBLS";
      tError.functionName = "readBankFile";
      tError.errorMessage = "读取银行返回文件失败!" + e.getMessage();
      this.mErrors .addOneError(tError);
      return null;
    }finally{
        try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    return tLYReturnFromBankSet;
  }

  private void dealBadRecord(LYReturnFromBankSet returnFromBankSet) {
		if (getLYBankLog().get(1).getTotalNum() == returnFromBankSet.size())
			return;
		HashSet dealrecords = new HashSet();
		for (int i = 1; i <= returnFromBankSet.size(); i++) {
			LYReturnFromBankSchema tLYReturnFromBankSchema = returnFromBankSet
					.get(i);
			dealrecords.add(tLYReturnFromBankSchema.getPayCode() + ":"
					+ tLYReturnFromBankSchema.getPolNo());
		}
		String sql = "select paycode, polno from lysendtobank where serialno='"
				+ "?serialno?" + "'";
		  SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
	      sqlbv1.sql(sql);
	      sqlbv1.put("serialno", serialNo);
		SSRS tSSRS = new ExeSQL().execSQL(sqlbv1);
		int num=0;
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			String r = tSSRS.GetText(i, 1) + ":" + tSSRS.GetText(i, 2);
			if (dealrecords.contains(r))
				continue;
			num++;
			LYReturnFromBankSchema tLYReturnFromBankSchema = new LYReturnFromBankSchema();
			tLYReturnFromBankSchema.setSerialNo(serialNo);
			tLYReturnFromBankSchema.setModifyDate(PubFun.getCurrentDate());
			tLYReturnFromBankSchema.setModifyTime(PubFun.getCurrentTime());
			tLYReturnFromBankSchema.setPayCode(tSSRS.GetText(i, 1));
			tLYReturnFromBankSchema.setBankDealDate(PubFun.getCurrentDate());
			tLYReturnFromBankSchema.setBankSuccFlag("9999"); // default fail code

			// 从发送盘表中获取数据
			if(!prepareReturnData(tLYReturnFromBankSchema, returnFromBankSet))
				throw new RuntimeException("unknown");
			
			returnFromBankSet.add(tLYReturnFromBankSchema);

		}
		if(num>0)
			CError.buildErr(this, "存在"+num+"笔与发盘记录不匹配的数据");
	}

/**
   * 根据前面的输入数据，进行逻辑处理
   * @return 如果在处理过程中出错，则返回false,否则返回true
   */
  private boolean dealData() {
    try {
      //获取银行文件数据
      if (mOperate.equals("READ")) {
        //获取银行返回数据信息
        outLYReturnFromBankSet.set(readBankFile(fileName));
        if (outLYReturnFromBankSet.size() == 0) throw new NullPointerException("文件中无数据！");

        //生成银行日志数据
        outLYBankLogSet.set(getLYBankLog());
        if (outLYBankLogSet.size() == 0) throw new NullPointerException("银行日志中无数据！");
      }
    }
    catch(Exception e) {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "ReadFromFileBL";
      tError.functionName = "dealData";
      tError.errorMessage = "数据处理错误! " + e.getMessage();
      this.mErrors .addOneError(tError);
      return false;
    }

    return true;
  }

  /**
   * 准备往后层输出所需要的数据
   * @return 如果准备数据时发生错误则返回false,否则返回true
   */
  private boolean prepareOutputData() {
    mInputData = new VData();

    try {
      mInputData.add(outLYReturnFromBankSet);
      mInputData.add(outLYBankLogSet);
      mInputData.add(inTransferData);
    }
    catch(Exception ex) {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="ReadFromFileBL";
      tError.functionName="prepareOutputData";
      tError.errorMessage="在准备往后层处理所需要的数据时出错。";
      this.mErrors .addOneError(tError) ;
      return false;
    }

    return true;
  }

  /**
   * 数据输出方法，供外界获取数据处理结果
   * @return 包含有数据查询结果字符串的VData对象
   */
  public VData getResult() {
    return mResult;
  }

  public static void main(String[] args)
  {
      try{
          GlobalInput tGlobalInput = new GlobalInput();
          tGlobalInput.ComCode = "8613";
          tGlobalInput.Operator = "debug";

          TransferData tTransferData = new TransferData();
          tTransferData.setNameAndValue("fileName","e:/MSDS1760.txt");
          tTransferData.setNameAndValue("serialNo","00000000000000016229");
          tTransferData.setNameAndValue("bankCode","0403");
          tTransferData.setNameAndValue("bussType","S");

          VData tVData = new VData();
          tVData.add(tGlobalInput);
          tVData.add(tTransferData);

          ReadFromFileBL ReadFromFileBL1 = new ReadFromFileBL();
          ReadFromFileBL1.submitData(tVData,"READ");
      }catch(Exception ex)
      {}
  }

  private int num = 0;
  private void displayDocument(Node d) {
    num += 2;

    if (d.hasChildNodes()) {
      NodeList nl = d.getChildNodes();

      for (int i=0; i<nl.getLength(); i++) {
        Node n = nl.item(i);

        for (int j=0; j<num; j++) {
          logger.debug(" ");
        }
        if (n.getNodeValue() == null) {
          logger.debug("<" + n.getNodeName() + ">");
        }
        else {
          logger.debug(n.getNodeValue());
        }

        displayDocument(n);

        num -= 2;
//        logger.debug("num:" + num);

        if (n.getNodeValue() == null) {
          for (int j=0; j<num; j++) {
            logger.debug(" ");
          }
          logger.debug("</" + n.getNodeName() + ">");
        }

      }

    }
  }
}
