<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  //程序名称：ProposalPrintSave.jsp
  //程序功能：
  //创建日期：2002-11-26
  //创建人  ：Kevin
  //修改人  ：朱向峰
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="org.jdom.Document"%>
<%@page import="org.jdom.Element"%>
<%@page import="org.jdom.output.*"%>
<%@page import="java.util.List"%>
<%@page import="java.math.BigDecimal"%>
<%!
  	String encoding = "GBK";
  	String path,fileName;
  
  
    /**
     * Get an OutputStreamWriter, use preferred encoding.
     */
    protected Writer makeWriter(OutputStream out) throws java.io.UnsupportedEncodingException 
    {
        Writer writer = new OutputStreamWriter(new BufferedOutputStream(out), this.encoding);
        return writer;
    }

	Writer createFileWriter(String path,String fileName) throws Exception 
	{
		loggerDebug("ProposalPrintSave","NEW FILE .. awoo "+path+fileName+FileNameQueue.XML_PRE);
		File outFile = new File(path,fileName+FileNameQueue.XML_PRE);
		if(outFile.createNewFile())
		{
			this.path = path;
			this.fileName = fileName;
		}
		else
		{
			throw new Exception("创建文件失败.");
		}
		return makeWriter(new FileOutputStream(outFile));
		
	}
	
	boolean outputHeader(XmlExport xe,Writer to) throws Exception
	{
		XMLOutputter outputter=null;
        FileWriter writer=null;
        //setup this like outputDocument
		outputter = new XMLOutputter("  ", true, "GBK");
		//output to a file
		String head = outputter.outputString(xe.getDocument());
		
		to.write("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		to.write("\n<DATASETS>\n");
		outputter.output(xe.getDocument().getRootElement().getChild("CONTROL"),to);
		//outputter.output(x, writer);

		return true;
	}

	boolean output(Element xe,Writer to) throws Exception
	{
		
		loggerDebug("ProposalPrintSave","write check file exists .. awoo "+new File(path,fileName+FileNameQueue.XML_PRE).exists());
		XMLOutputter outputter=null;
		outputter = new XMLOutputter("  ", true, "GBK");
		
		outputter.output(xe,to);
		return true;
	}
	
	boolean outputEnd(Writer to) throws Exception
	{
		to.write("\n</DATASETS>");
		return true;
	}
	
	
	boolean deleteFile(String path,String file) throws Exception
	{
		File outFile = new File(path,file+FileNameQueue.XML_PRE);
		return outFile.delete();

	}

%>
<%
  MMap map = new MMap();
  MMap map1 = new MMap();
  MMap map2 = new MMap();
  //获得session中的人员信息
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput) session.getValue("GI");
  String FlagStr = "";
  String Content = "";
  String tOperate = request.getParameter("fmtransact");
  String tinvoiceStartNo = request.getParameter("invoiceStartNo");
  loggerDebug("ProposalPrintSave","tinvoiceStartNo="+tinvoiceStartNo);
  String tinvoiceEndNo = request.getParameter("invoiceEndNo");
  String tContPrtSeq = ""; //保单打印批次号
  BigDecimal tstartNo = new BigDecimal("0");
  if(tinvoiceStartNo!=null&&tinvoiceStartNo.length()>0)
  {
  tstartNo = new BigDecimal(tinvoiceStartNo);
  //tstartNo = Long.parseLong(tinvoiceStartNo);
  }
  int tinvoicenoLen = tinvoiceStartNo.length();
  int tinvoicenoLen1 = tinvoiceEndNo.length();
  ExeSQL exeSql = new ExeSQL();
  String OppoPath = exeSql.getOneValue("select sysvarvalue From ldsysvar where sysvar='XmlPath'");
  String sOutXmlPath = application.getRealPath("")+"/"; //xml文件输出路径
  String RealPath = sOutXmlPath.replace('\\','/');
  loggerDebug("ProposalPrintSave","路径==temppaht=="+ OppoPath+"======" + RealPath+"       xmloutpath :"+sOutXmlPath);
  //获得mutline中的数据信息
  int nIndex = 0;
  String tLCTaskGrids[] = request.getParameterValues("TaskGridNo");
  //String tContNo[] = request.getParameterValues("TaskGrid1");
  String tContNo[] = request.getParameterValues("TaskGrid2"); //edit by yaory
  String tPrintCount[] = request.getParameterValues("TaskGrid6");
  String tChecks[] = request.getParameterValues("InpTaskGridChk");
  String PrtName = request.getParameter("tPrtName");
  String ChannelType = request.getParameter("SaleChnl");
  String Individ = request.getParameter("Individ"); //个性化
  String strManageCom = request.getParameter("ManageCom");
  String printercode = request.getParameter("printercode"); //打印机
  if(printercode == null) printercode = "";
  tG.ManageCom = strManageCom;
  loggerDebug("ProposalPrintSave","个性化标志："+Individ);
  VData mResult = new VData();
  //操作对象及容器
  LCContSchema tLCContSchema = new LCContSchema();
  LCContF1PUI tLCContF1PUI = new LCContF1PUI();
  InvoiceF1PBL tInvoiceF1PBL = new InvoiceF1PBL();
  LCGrpContF1PUI tLCGrpContF1PUI = new LCGrpContF1PUI();
  VData vData = new VData();
  boolean operFlag = true;
  //循环打印选中的保单
  XmlExport txmlExportAll = new XmlExport(); //保单
  txmlExportAll.createDocuments(printercode, tG);
  int iFlag = 0;
  String templateName = null;
  String templateOp = null;
  List datasetList = null;
  XmlExport txmlExportAllpj = null; //票据

  int iFlagpj = 0;
  String templateNamepj = null;
  String templateOppj = null;
  List datasetListpj = null;
  //保单的清单打印
  XmlExport txmlExportAllqd = null;
  //int iFlag=0;
  String templateNameqd = null;
  String templateOpqd = null;
  List datasetListqd = null;
  StringBuffer errSb = new StringBuffer();
  StringBuffer pBillSb = new StringBuffer(); //组织保单清单需要的所有保单号,不同的保单号用 : 分开;
  int contNoCount = 0;
  
  Writer bdWriter=null,pjWriter=null;
  String bdFileName=null,pjFileName=null;
  
try 
{
  	if ("bd".equals(PrtName)) 
  	{
		String tLimit = PubFun.getNoLimit(tG.ManageCom);
		tContPrtSeq = PubFun1.CreateMaxNo("CONTPRTSEQ", tLimit); //保单打印批次号
		loggerDebug("ProposalPrintSave","batch no is ==========" + tContPrtSeq);
	  	for (nIndex = 0; nIndex < tChecks.length; nIndex++) 
	  	{
	    	//If this line isn't selected, continue，如果没有选中当前行，则继续
	      	if (!tChecks[nIndex].equals("1")) 
	      	{
	        	continue;
	      	}
	      	map1.put("update lccont set printcount = 1 where contno = '"+tContNo[nIndex]+"'","UPDATE");
	      	map2.put("update lccont set printcount = 0 where contno = '"+tContNo[nIndex]+"'","UPDATE");
	    }
	    VData tVDataPre = new VData();
	    tVDataPre.add(map1);
	    PubSubmit tPSPre = new PubSubmit();
	    if(!tPSPre.submitData(tVDataPre,""))
	    {
	    	operFlag = false;
	    	Content = "修改打印状态失败！"+tPSPre.mErrors.getFirstError().toString();
	    }
	}
    for (nIndex = 0; operFlag == true && nIndex < tChecks.length; nIndex++) 
    {
      	//If this line isn't selected, continue，如果没有选中当前行，则继续
      	if (!tChecks[nIndex].equals("1")) 
      	{
        	continue;
      	}
      	//组织保单清单需要的所有保单号,不同的保单号用 : 分开;
      	final String colon = ":";
      	if (pBillSb.length() != 0) pBillSb.append(colon);
      	pBillSb.append(tContNo[nIndex]);
      	contNoCount++;
      	//判定打印模式
      	if (tPrintCount[nIndex].compareTo("0") == 0) 
      	{
       	 	tOperate = "PRINT";
      	}
      	else 
      	{
        	tOperate = "REPRINT";
      	}
      	//将数据集合放入容器中，准备传入后台处理
      	vData = new VData();
		TransferData tTransferData = new TransferData();
		vData.add(tG);
		//执行后台操作
		XmlExport txmlExport = null; //保单
		XmlExport txmlExportpj = null; //票据
		loggerDebug("ProposalPrintSave","PrtName is :"+PrtName);
		if (!"sj".equals(PrtName)) 
		{
			String strInvoiceNo = tstartNo.toString();
	      	if(tinvoicenoLen == tinvoicenoLen1)
	      	strInvoiceNo = StrTool.getStringWith(strInvoiceNo,tinvoicenoLen,'0','L');
	      	tTransferData.setNameAndValue("ContPrtSeq", tContPrtSeq);
	      	tTransferData.setNameAndValue("StartNo", strInvoiceNo);
	      	tTransferData.setNameAndValue("RealPath", RealPath);
	      	tTransferData.setNameAndValue("Individ", Individ); //个性化
	      	//tstartNo++; //发票号
	      	tstartNo = tstartNo.add(new BigDecimal("1"));
	      	loggerDebug("ProposalPrintSave","invoiceno is :"+tstartNo.toString());
        	loggerDebug("ProposalPrintSave","ChannelType is "+ ChannelType);
        	if ("1".equals(ChannelType) || "3".equals(ChannelType)||"5".equals(ChannelType)) //个人/银代保单
        	{ 
          		loggerDebug("ProposalPrintSave","打印个人保单");
          		//将数据放入合同保单集合
				tLCContSchema = new LCContSchema();
				tLCContSchema.setContNo(tContNo[nIndex]);
				vData.add(tLCContSchema);
				vData.add(tTransferData);
          		if (!tLCContF1PUI.submitData(vData, "PRINT")) 
          		{
		            loggerDebug("ProposalPrintSave","打印个人保单出错");
		            Content = "批量打印"+tContNo[nIndex]+"号保单失败，原因是:" + tLCContF1PUI.mErrors.getFirstError();
		            FlagStr = "Fail";
		            operFlag = false;
		            break;
          		}
          		mResult = tLCContF1PUI.getResult();
			}
        	else if ("2".equals(ChannelType)) //团体保单
        	{ 
        		loggerDebug("ProposalPrintSave","打印团体保单");
				LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
				tLCGrpContSchema.setGrpContNo(tContNo[nIndex]);
				vData.add(tLCGrpContSchema);
				vData.add(tTransferData);
          		if (!tLCGrpContF1PUI.submitData(vData, "PRINT")) 
          		{
		            loggerDebug("ProposalPrintSave","打印团体保单出错");
		            Content = "批量打印"+tContNo[nIndex]+"号保单失败，原因是:" + tLCGrpContF1PUI.mErrors.getFirstError();
		            FlagStr = "Fail";
		            operFlag = false;
		            break;
          		}
          		mResult = tLCGrpContF1PUI.getResult();
			}     
        
        	//保单部分
	        txmlExport = (XmlExport) mResult.getObjectByObjectName("XmlExport", 0);
	        MMap tmap = (MMap) mResult.getObjectByObjectName("MMap", 0);
	        map.add(tmap);
	        //txmlExport.outputDocumentToFile(OppoPath,"contract000");
        	Element telement = txmlExport.getDocument().getRootElement();
        	if (templateName == null) //保单
        	{ 
          		String templateNameTemp = telement.getChild("CONTROL").getChild("TEMPLATE").getTextTrim();
          		int n = templateNameTemp.indexOf(".");
          		if (n != -1) 
          		{
		            templateNameTemp = templateNameTemp.substring(0, n);
		            templateOp = templateNameTemp + tG.Operator;
          		}
          		templateName = templateNameTemp;
        	}
        	if (iFlag == 0) 
        	{
          		txmlExportAll.setTemplateName(txmlExportAll.getDocument().getRootElement(), telement);
          		bdFileName = FileNameQueue.getFileName(OppoPath, templateOp);
          		try{
								bdWriter = createFileWriter(OppoPath, bdFileName);
							}catch(Exception ep){
								loggerDebug("ProposalPrintSave","打印个人保单生成文件不成功，打印失败！");
		            Content = "批量打印保单失败，原因是生成文件不成功:" + ep.getMessage();
		            FlagStr = "Fail";
		            operFlag = false;
		            break;
							}
							loggerDebug("ProposalPrintSave",templateOp+" create bdWriter .. awoo "+OppoPath+bdFileName);
							try{
								outputHeader(txmlExportAll,bdWriter);
							}catch(Exception eo){
								loggerDebug("ProposalPrintSave","打印个人保单写文件头不成功，打印失败！");
		            Content = "批量打印保单失败，原因是写文件头不成功:" + eo.getMessage();
		            FlagStr = "Fail";
		            operFlag = false;
		            break;
							}
							loggerDebug("ProposalPrintSave","created bdWriter .. awoo ");
							
							iFlag ++;
        	}
			//txmlExportAll.addDataSet(txmlExportAll.getDocument().getRootElement(), telement);
				
			try{
				output(telement,bdWriter);
			}catch(Exception es){
				loggerDebug("ProposalPrintSave","打印个人保单写文件不成功，打印失败！");
				Content = "批量打印"+tContNo[nIndex]+"号保单时失败，原因是写文件不成功:" + es.getMessage();
				FlagStr = "Fail";
				operFlag = false;
				break;
			}
 			loggerDebug("ProposalPrintSave","output to bdWriter .. awoo ");

      }
      else if ("sj".equals(PrtName)) 
      {
			tInvoiceF1PBL = new InvoiceF1PBL();
			tLCContSchema = new LCContSchema();
			tLCContSchema.setGrpContNo(tContNo[nIndex]);
			tLCContSchema.setContNo(tContNo[nIndex]);
			vData.add(tLCContSchema);
			vData.add(tTransferData);
        	if (!tInvoiceF1PBL.submitData(vData, "PRINT")) 
        	{
		          Content = "批量打印"+tContNo[nIndex]+"号保单发票失败，原因是:" + tInvoiceF1PBL.mErrors.getFirstError();
		          FlagStr = "Fail";
		          operFlag = false;
		          break;
        	}
        
        	mResult = tInvoiceF1PBL.getResult();
        	//票据部分;
        	txmlExportpj = (XmlExport) mResult.getObjectByObjectName("XmlExport", 0, 1);

        	Element telementpj = txmlExportpj.getDocument().getRootElement();
        	if (templateNamepj == null)  //票据
        	{
          		String templateNameTemp = telementpj.getChild("CONTROL").getChild("TEMPLATE").getTextTrim();
          		int n = templateNameTemp.indexOf(".");
          		if (n != -1) 
          		{
            		templateNameTemp = templateNameTemp.substring(0, n);
            		templateOppj = templateNameTemp + tG.Operator;
          		}
          		templateNamepj = templateNameTemp;
        	}
        	if (iFlagpj == 0) 
        	{
				txmlExportAllpj = new XmlExport();
				txmlExportAllpj.createDocuments(printercode, tG);
				txmlExportAllpj.setTemplateName(txmlExportAllpj.getDocument().getRootElement(), telementpj);
					
				pjFileName = FileNameQueue.getFileName(OppoPath, templateOppj);
				pjWriter = createFileWriter(OppoPath, pjFileName);
				loggerDebug("ProposalPrintSave","Create pjWriter .. awoo");
				outputHeader(txmlExportAllpj,pjWriter);
				loggerDebug("ProposalPrintSave","Created pjWriter .. awoo");
          		
          		iFlagpj ++;
        	}
        	loggerDebug("ProposalPrintSave","output to pjWriter .. awoo");
        	output(telementpj,pjWriter);
      }
	} //for 循环结束
	
	if(!operFlag)
	{
			VData tVDataFail = new VData();
			tVDataFail.add(map2);
			PubSubmit tPSFail = new PubSubmit();
			tPSFail.submitData(tVDataFail,"");
		}

    
		VData tsVData = new VData();
		tsVData.add(map);
		PubSubmit tPS = new PubSubmit();
		if(operFlag && !tPS.submitData(tsVData,""))
		{
			operFlag = false;
			Content = tPS.mErrors.getFirstError().toString();
			VData tVDataAft = new VData();
			tVDataAft.add(map2);
			PubSubmit tPSAft = new PubSubmit();
			tPSAft.submitData(tVDataAft,"");
		}
  	
    //保单清单xml文件生成
    if (operFlag == true && !"sj".equals(PrtName)&&("1".equals(ChannelType) || "3".equals(ChannelType)||"5".equals(ChannelType))) 
    { //打印保单的时生成清单,(sj)收据不生成清单.
		VData cpBillVData = new VData();
		loggerDebug("ProposalPrintSave","========" + pBillSb.toString());
		cpBillVData.add(String.valueOf(contNoCount));
		cpBillVData.add(pBillSb.toString());
		cpBillVData.add(tG);
		cpBillVData.add(tContPrtSeq);
		loggerDebug("ProposalPrintSave","========" + tContPrtSeq);
		PrintService tbl = null;
      	if("1".equals(ChannelType)||"5".equals(ChannelType))
      	{
        	tbl = new PersonBillBL();
      	}
      	else if("3".equals(ChannelType))
      	{
        	tbl = new BankAgentBillBL();
      	}
      	loggerDebug("ProposalPrintSave","ChannelType="+ChannelType);
      	loggerDebug("ProposalPrintSave","tOperate="+tOperate);
      	try
      	{
			if (("1".equals(ChannelType)||"3".equals(ChannelType)||"5".equals(ChannelType)) && tbl.submitData(cpBillVData, "PRINT")) 
	      	{
	      		loggerDebug("ProposalPrintSave","Got QD...");
		        mResult = tbl.getResult();
		        txmlExportAllqd = (XmlExport) mResult.getObjectByObjectName("XmlExport", 0);
		        txmlExportAllqd.setPrinterName(printercode);
		        Element telementqd = txmlExportAllqd.getDocument().getRootElement();
	        	if (templateNameqd == null) 
	        	{
		          	String templateNameTemp = telementqd.getChild("CONTROL").getChild("TEMPLATE").getTextTrim();
		          	int n = templateNameTemp.indexOf(".");
	          		if (n != -1) 
	          		{
	            		templateNameTemp = templateNameTemp.substring(0, n);
	            		templateOpqd = templateNameTemp + tG.Operator;
	          		}
	          		templateNameqd = templateNameTemp;
	        	}
	      	}
	      	else
	      	{
	      		loggerDebug("ProposalPrintSave","没有生成清分清单");
	      	}
      	}
      	catch(Exception eqd)
      	{
	      	eqd.printStackTrace();
	      	loggerDebug("ProposalPrintSave","清单打印严重错误");
      	}
    }
    if(operFlag)
    {
            loggerDebug("ProposalPrintSave","=================打印成功");
            FlagStr = "Success";
            Content = "批量打印文件生成成功!";
    }
    else
    {
    	loggerDebug("ProposalPrintSave","打印失败 .. awoo "+Content);	
    }
    
	//保单,只要有一个 dataset,就生成xml文件
   	if (operFlag == true && bdWriter!=null) 
    {
			try
			{
				output(txmlExportAllqd.getDocument().getRootElement(),bdWriter);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				Content = "批量打印保单文件生成成功，但清分清单没有生成！";
			}
    }

    if(bdWriter!=null)
    {
    	outputEnd(bdWriter);
    	bdWriter.flush();
    	bdWriter.close();
    	if(operFlag)
    	{
    		loggerDebug("ProposalPrintSave","FILE exists? .. awoo "+new File(OppoPath,bdFileName+FileNameQueue.XML_PRE).exists());
    		if(FileNameQueue.rename(OppoPath,bdFileName))
    		{
    			loggerDebug("ProposalPrintSave","success rename .. awoo "+OppoPath+bdFileName);
    		}
    		else
    		{
			    operFlag = false;
			    FlagStr = "Fail";
			    Content = "重新命名文件失败:" +OppoPath+bdFileName;
				VData tVDataAft1 = new VData();
			    tVDataAft1.add(map2);
			    PubSubmit tPSAft1 = new PubSubmit();
			    tPSAft1.submitData(tVDataAft1,"");
    		}
    	}
    	else
    	{
    		deleteFile(OppoPath,bdFileName);
    		loggerDebug("ProposalPrintSave","delete bd .. awoo ");
    	}
    }
    if(bdWriter == null && !"sj".equals(PrtName))
    {
    	operFlag = false;
	    FlagStr = "Fail";
	    //Content = "重新命名文件失败:" +OppoPath+bdFileName;
		  VData tVDataAft2 = new VData();
	    tVDataAft2.add(map2);
	    PubSubmit tPSAft2 = new PubSubmit();
	    tPSAft2.submitData(tVDataAft2,"");
    }
    if(pjWriter!=null)
    {
		outputEnd(pjWriter);
		pjWriter.flush();
    	pjWriter.close();
    	if(operFlag)
    	{
    		if(FileNameQueue.rename(OppoPath,pjFileName))
    		{
    			loggerDebug("ProposalPrintSave","success rename .. awoo "+OppoPath+pjFileName);
    		}
    		else
    		{
			    operFlag = false;
			    FlagStr = "Fail";
			    Content = "重新命名文件失败:" +OppoPath+pjFileName;
    		}
    	}
    	else
    	{
    		deleteFile(OppoPath,pjFileName);
    		loggerDebug("ProposalPrintSave","delete pj .. awoo ");
    	} 	
    }    
}
catch (Exception ex) 
{
  	ex.printStackTrace();
    operFlag = false;
    FlagStr = "Fail";
    Content = "批量打印失败或部分未打印,原因是:" + ex.getMessage();
}
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmitbd("<%=FlagStr%>","<%=Content%>");
</script></html>
