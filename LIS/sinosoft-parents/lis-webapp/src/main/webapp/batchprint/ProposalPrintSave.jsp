<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  //�������ƣ�ProposalPrintSave.jsp
  //�����ܣ�
  //�������ڣ�2002-11-26
  //������  ��Kevin
  //�޸���  �������
  //���¼�¼��  ������    ��������     ����ԭ��/����
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
			throw new Exception("�����ļ�ʧ��.");
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
  //���session�е���Ա��Ϣ
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput) session.getValue("GI");
  String FlagStr = "";
  String Content = "";
  String tOperate = request.getParameter("fmtransact");
  String tinvoiceStartNo = request.getParameter("invoiceStartNo");
  loggerDebug("ProposalPrintSave","tinvoiceStartNo="+tinvoiceStartNo);
  String tinvoiceEndNo = request.getParameter("invoiceEndNo");
  String tContPrtSeq = ""; //������ӡ���κ�
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
  String sOutXmlPath = application.getRealPath("")+"/"; //xml�ļ����·��
  String RealPath = sOutXmlPath.replace('\\','/');
  loggerDebug("ProposalPrintSave","·��==temppaht=="+ OppoPath+"======" + RealPath+"       xmloutpath :"+sOutXmlPath);
  //���mutline�е�������Ϣ
  int nIndex = 0;
  String tLCTaskGrids[] = request.getParameterValues("TaskGridNo");
  //String tContNo[] = request.getParameterValues("TaskGrid1");
  String tContNo[] = request.getParameterValues("TaskGrid2"); //edit by yaory
  String tPrintCount[] = request.getParameterValues("TaskGrid6");
  String tChecks[] = request.getParameterValues("InpTaskGridChk");
  String PrtName = request.getParameter("tPrtName");
  String ChannelType = request.getParameter("SaleChnl");
  String Individ = request.getParameter("Individ"); //���Ի�
  String strManageCom = request.getParameter("ManageCom");
  String printercode = request.getParameter("printercode"); //��ӡ��
  if(printercode == null) printercode = "";
  tG.ManageCom = strManageCom;
  loggerDebug("ProposalPrintSave","���Ի���־��"+Individ);
  VData mResult = new VData();
  //������������
  LCContSchema tLCContSchema = new LCContSchema();
  LCContF1PUI tLCContF1PUI = new LCContF1PUI();
  InvoiceF1PBL tInvoiceF1PBL = new InvoiceF1PBL();
  LCGrpContF1PUI tLCGrpContF1PUI = new LCGrpContF1PUI();
  VData vData = new VData();
  boolean operFlag = true;
  //ѭ����ӡѡ�еı���
  XmlExport txmlExportAll = new XmlExport(); //����
  txmlExportAll.createDocuments(printercode, tG);
  int iFlag = 0;
  String templateName = null;
  String templateOp = null;
  List datasetList = null;
  XmlExport txmlExportAllpj = null; //Ʊ��

  int iFlagpj = 0;
  String templateNamepj = null;
  String templateOppj = null;
  List datasetListpj = null;
  //�������嵥��ӡ
  XmlExport txmlExportAllqd = null;
  //int iFlag=0;
  String templateNameqd = null;
  String templateOpqd = null;
  List datasetListqd = null;
  StringBuffer errSb = new StringBuffer();
  StringBuffer pBillSb = new StringBuffer(); //��֯�����嵥��Ҫ�����б�����,��ͬ�ı������� : �ֿ�;
  int contNoCount = 0;
  
  Writer bdWriter=null,pjWriter=null;
  String bdFileName=null,pjFileName=null;
  
try 
{
  	if ("bd".equals(PrtName)) 
  	{
		String tLimit = PubFun.getNoLimit(tG.ManageCom);
		tContPrtSeq = PubFun1.CreateMaxNo("CONTPRTSEQ", tLimit); //������ӡ���κ�
		loggerDebug("ProposalPrintSave","batch no is ==========" + tContPrtSeq);
	  	for (nIndex = 0; nIndex < tChecks.length; nIndex++) 
	  	{
	    	//If this line isn't selected, continue�����û��ѡ�е�ǰ�У������
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
	    	Content = "�޸Ĵ�ӡ״̬ʧ�ܣ�"+tPSPre.mErrors.getFirstError().toString();
	    }
	}
    for (nIndex = 0; operFlag == true && nIndex < tChecks.length; nIndex++) 
    {
      	//If this line isn't selected, continue�����û��ѡ�е�ǰ�У������
      	if (!tChecks[nIndex].equals("1")) 
      	{
        	continue;
      	}
      	//��֯�����嵥��Ҫ�����б�����,��ͬ�ı������� : �ֿ�;
      	final String colon = ":";
      	if (pBillSb.length() != 0) pBillSb.append(colon);
      	pBillSb.append(tContNo[nIndex]);
      	contNoCount++;
      	//�ж���ӡģʽ
      	if (tPrintCount[nIndex].compareTo("0") == 0) 
      	{
       	 	tOperate = "PRINT";
      	}
      	else 
      	{
        	tOperate = "REPRINT";
      	}
      	//�����ݼ��Ϸ��������У�׼�������̨����
      	vData = new VData();
		TransferData tTransferData = new TransferData();
		vData.add(tG);
		//ִ�к�̨����
		XmlExport txmlExport = null; //����
		XmlExport txmlExportpj = null; //Ʊ��
		loggerDebug("ProposalPrintSave","PrtName is :"+PrtName);
		if (!"sj".equals(PrtName)) 
		{
			String strInvoiceNo = tstartNo.toString();
	      	if(tinvoicenoLen == tinvoicenoLen1)
	      	strInvoiceNo = StrTool.getStringWith(strInvoiceNo,tinvoicenoLen,'0','L');
	      	tTransferData.setNameAndValue("ContPrtSeq", tContPrtSeq);
	      	tTransferData.setNameAndValue("StartNo", strInvoiceNo);
	      	tTransferData.setNameAndValue("RealPath", RealPath);
	      	tTransferData.setNameAndValue("Individ", Individ); //���Ի�
	      	//tstartNo++; //��Ʊ��
	      	tstartNo = tstartNo.add(new BigDecimal("1"));
	      	loggerDebug("ProposalPrintSave","invoiceno is :"+tstartNo.toString());
        	loggerDebug("ProposalPrintSave","ChannelType is "+ ChannelType);
        	if ("1".equals(ChannelType) || "3".equals(ChannelType)||"5".equals(ChannelType)) //����/��������
        	{ 
          		loggerDebug("ProposalPrintSave","��ӡ���˱���");
          		//�����ݷ����ͬ��������
				tLCContSchema = new LCContSchema();
				tLCContSchema.setContNo(tContNo[nIndex]);
				vData.add(tLCContSchema);
				vData.add(tTransferData);
          		if (!tLCContF1PUI.submitData(vData, "PRINT")) 
          		{
		            loggerDebug("ProposalPrintSave","��ӡ���˱�������");
		            Content = "������ӡ"+tContNo[nIndex]+"�ű���ʧ�ܣ�ԭ����:" + tLCContF1PUI.mErrors.getFirstError();
		            FlagStr = "Fail";
		            operFlag = false;
		            break;
          		}
          		mResult = tLCContF1PUI.getResult();
			}
        	else if ("2".equals(ChannelType)) //���屣��
        	{ 
        		loggerDebug("ProposalPrintSave","��ӡ���屣��");
				LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
				tLCGrpContSchema.setGrpContNo(tContNo[nIndex]);
				vData.add(tLCGrpContSchema);
				vData.add(tTransferData);
          		if (!tLCGrpContF1PUI.submitData(vData, "PRINT")) 
          		{
		            loggerDebug("ProposalPrintSave","��ӡ���屣������");
		            Content = "������ӡ"+tContNo[nIndex]+"�ű���ʧ�ܣ�ԭ����:" + tLCGrpContF1PUI.mErrors.getFirstError();
		            FlagStr = "Fail";
		            operFlag = false;
		            break;
          		}
          		mResult = tLCGrpContF1PUI.getResult();
			}     
        
        	//��������
	        txmlExport = (XmlExport) mResult.getObjectByObjectName("XmlExport", 0);
	        MMap tmap = (MMap) mResult.getObjectByObjectName("MMap", 0);
	        map.add(tmap);
	        //txmlExport.outputDocumentToFile(OppoPath,"contract000");
        	Element telement = txmlExport.getDocument().getRootElement();
        	if (templateName == null) //����
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
								loggerDebug("ProposalPrintSave","��ӡ���˱��������ļ����ɹ�����ӡʧ�ܣ�");
		            Content = "������ӡ����ʧ�ܣ�ԭ���������ļ����ɹ�:" + ep.getMessage();
		            FlagStr = "Fail";
		            operFlag = false;
		            break;
							}
							loggerDebug("ProposalPrintSave",templateOp+" create bdWriter .. awoo "+OppoPath+bdFileName);
							try{
								outputHeader(txmlExportAll,bdWriter);
							}catch(Exception eo){
								loggerDebug("ProposalPrintSave","��ӡ���˱���д�ļ�ͷ���ɹ�����ӡʧ�ܣ�");
		            Content = "������ӡ����ʧ�ܣ�ԭ����д�ļ�ͷ���ɹ�:" + eo.getMessage();
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
				loggerDebug("ProposalPrintSave","��ӡ���˱���д�ļ����ɹ�����ӡʧ�ܣ�");
				Content = "������ӡ"+tContNo[nIndex]+"�ű���ʱʧ�ܣ�ԭ����д�ļ����ɹ�:" + es.getMessage();
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
		          Content = "������ӡ"+tContNo[nIndex]+"�ű�����Ʊʧ�ܣ�ԭ����:" + tInvoiceF1PBL.mErrors.getFirstError();
		          FlagStr = "Fail";
		          operFlag = false;
		          break;
        	}
        
        	mResult = tInvoiceF1PBL.getResult();
        	//Ʊ�ݲ���;
        	txmlExportpj = (XmlExport) mResult.getObjectByObjectName("XmlExport", 0, 1);

        	Element telementpj = txmlExportpj.getDocument().getRootElement();
        	if (templateNamepj == null)  //Ʊ��
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
	} //for ѭ������
	
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
  	
    //�����嵥xml�ļ�����
    if (operFlag == true && !"sj".equals(PrtName)&&("1".equals(ChannelType) || "3".equals(ChannelType)||"5".equals(ChannelType))) 
    { //��ӡ������ʱ�����嵥,(sj)�վݲ������嵥.
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
	      		loggerDebug("ProposalPrintSave","û����������嵥");
	      	}
      	}
      	catch(Exception eqd)
      	{
	      	eqd.printStackTrace();
	      	loggerDebug("ProposalPrintSave","�嵥��ӡ���ش���");
      	}
    }
    if(operFlag)
    {
            loggerDebug("ProposalPrintSave","=================��ӡ�ɹ�");
            FlagStr = "Success";
            Content = "������ӡ�ļ����ɳɹ�!";
    }
    else
    {
    	loggerDebug("ProposalPrintSave","��ӡʧ�� .. awoo "+Content);	
    }
    
	//����,ֻҪ��һ�� dataset,������xml�ļ�
   	if (operFlag == true && bdWriter!=null) 
    {
			try
			{
				output(txmlExportAllqd.getDocument().getRootElement(),bdWriter);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				Content = "������ӡ�����ļ����ɳɹ���������嵥û�����ɣ�";
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
			    Content = "���������ļ�ʧ��:" +OppoPath+bdFileName;
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
	    //Content = "���������ļ�ʧ��:" +OppoPath+bdFileName;
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
			    Content = "���������ļ�ʧ��:" +OppoPath+pjFileName;
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
    Content = "������ӡʧ�ܻ򲿷�δ��ӡ,ԭ����:" + ex.getMessage();
}
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmitbd("<%=FlagStr%>","<%=Content%>");
</script></html>
