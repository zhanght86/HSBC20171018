<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%

//�ж��Ƿ�����ؽ��ѵı���

GlobalInput tG = (GlobalInput)session.getAttribute("GI");

String tCertifyCode = request.getParameter("CertifyCode");
LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
LJAPaySchema tLJAPaySchema = new LJAPaySchema();
tLJAPaySchema.setPayNo(request.getParameter("PayNo"));
tLJAPaySchema.setOtherNo(request.getParameter("OtherNo"));
loggerDebug("ExtendInvoiceF1PSave","==========================PayNo==================="+request.getParameter("PayNo"));
loggerDebug("ExtendInvoiceF1PSave","==========================OtherNo==================="+request.getParameter("OtherNo"));

String ChequNo = request.getParameter("ChequNo");
session.putValue("AutoCheckon",ChequNo);
CError cError = new CError( );
String otherno = request.getParameter("OtherNo");

String sql = " select agentcode ,managecom from lccont where contno = '"+otherno+"'";
ExeSQL seExeSQL = new ExeSQL();
SSRS seSSRS = seExeSQL.execSQL(sql);
String ssAgentCode =  seSSRS.GetText(1, 1);
String ssManageCom1 =  seSSRS.GetText(1, 2);
String tManageCom = "";
String tPolicyCom = "";
String tAgentcode = ssAgentCode;
ssAgentCode = "D"+ssAgentCode;

String ssManageCom =  "A"+ssManageCom1;
loggerDebug("ExtendInvoiceF1PSave",sql);
session.putValue("GI",tG);

//�����жϴ�����û�з��ŵ�֤���ʸ�
SSRS tAgentSSRS = new SSRS();
//lizhi
boolean tlizhi = false;

session.putValue("ssAgentCode", ssAgentCode);
session.putValue("ssManageCom", "A"+tG.ComCode);
loggerDebug("ExtendInvoiceF1PSave",tG.ComCode);
loggerDebug("ExtendInvoiceF1PSave",tG.ManageCom);
session.putValue("ChequNo",ChequNo );
session.putValue("PrintNo",request.getParameter("PreSeq"));
session.putValue("Print","35");
loggerDebug("ExtendInvoiceF1PSave","======================================================"+ssAgentCode);
loggerDebug("ExtendInvoiceF1PSave","======================================================"+ssManageCom);
loggerDebug("ExtendInvoiceF1PSave",ChequNo);
loggerDebug("ExtendInvoiceF1PSave",request.getParameter("PreSeq"));

tLOPRTManagerSchema.setPrtSeq(request.getParameter("PreSeq"));
tLOPRTManagerSchema.setStandbyFlag1(request.getParameter("GetNoticeNoHidden"));

String tGetNoticeNoHidden = request.getParameter("GetNoticeNoHidden");

loggerDebug("ExtendInvoiceF1PSave","=======================GetNoticeNoHidden==============================="+request.getParameter("GetNoticeNoHidden"));
VData tVData = new VData();
VData mResult = new VData();
tVData.addElement(tG);
tVData.addElement(tLOPRTManagerSchema);
//tVData.addElement(tLJAPaySchema);

String strErrMsg = "";
boolean Flag=true;
loggerDebug("ExtendInvoiceF1PSave","kaish yunxing pubui");
PubUI tPubUI = new PubUI();
String tPrintType = request.getParameter("PrintType");
String SubmitClass = "";
if(tPrintType.equals("01"))
{
  SubmitClass = "com.sinosoft.lis.operfee.ExtendInvoiceBL";
}
else
{
  SubmitClass = "com.sinosoft.lis.operfee.ExtendInvoicebBL";
}

try
{
 		 if(!tPubUI.submitData(tVData,"Print",SubmitClass))
 		 {
			    if( tPubUI.mErrors.needDealError())
			    {
			    		Flag=false;
			    		strErrMsg = tPubUI.mErrors.getFirstError();
			    }
			    		else
			    		{
			    				strErrMsg = "tPubUI�������󣬵���û���ṩ��ϸ�ĳ�����Ϣ";
			    		}

					if( !strErrMsg.equals(""))
					{
							loggerDebug("ExtendInvoiceF1PSave","outputStream object has been open");
							%>
							<script language="javascript" type="">
								alert('<%= strErrMsg %>');
								window.opener = null;
								window.close();
								</script>
								<%
					}
		 return;
		 }
}
	catch(Exception ex)
	{
			loggerDebug("ExtendInvoiceF1PSave","Error is:"+ex);
  }
  mResult = tPubUI.getResult();

  XmlExport txmlExport = (XmlExport)mResult.getObjectByObjectName("XmlExport",0);

  if (txmlExport==null)
  {
  		loggerDebug("ExtendInvoiceF1PSave","null");
  }

  txmlExport.outputDocumentToFile("C:\\","tttttt");
  ExeSQL tExeSQL = new ExeSQL();

  PubPrtConfigure tPubPrtConfigure = new PubPrtConfigure();
  TransferData t12TransferData = new TransferData();

  loggerDebug("ExtendInvoiceF1PSave","ddddddddddddddddddssManageCom1dddddddddddd"+ssManageCom1);
  t12TransferData.setNameAndValue("ManageCom",tG.ManageCom.substring(0,4));
  loggerDebug("ExtendInvoiceF1PSave","������������������"+tG.ManageCom.substring(0,4)+"��������������������");
  String templatepath = tPubPrtConfigure.getPrtPath(t12TransferData,"32");

  SSRS sPrintVts = new SSRS();

  session.putValue("certifycode",tCertifyCode);

  //�õ����ѻ����Ĺ���������� �ж��Ƿ���Ҫ���ŵ�֤
  String tPrintVts = " select managecom from ljtempfee where tempfeeno = '"+tGetNoticeNoHidden+"'";
  sPrintVts = tExeSQL.execSQL(tPrintVts);
  //�ж��Ƿ�����Ҫ�����е�֤�ķ���
  boolean tFanCertify = false;

  try
  {
  		loggerDebug("ExtendInvoiceF1PSave","======================="+sPrintVts.GetText(1,1).substring(0,4));
    	loggerDebug("ExtendInvoiceF1PSave","======================="+ssManageCom1.substring(0,4));
    	if(sPrintVts.GetText(1,1).substring(0,4).equals(ssManageCom1.substring(0,4)))
    	{
    	  loggerDebug("ExtendInvoiceF1PSave","xiang===============================================tong");
    	  tFanCertify = true;
    	}
  }
  catch(Exception e)
  {
  		tFanCertify = false;
  }

  //��ȡ��ʱ�ļ���
  String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
  String strFilePath = tExeSQL.getOneValue(strSql);
  String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName()+".vts";

  //��ȡ�����ʱ�ļ���·��
  String strRealPath = application.getRealPath("/").replace('\\','/');
  String strVFPathName = strRealPath + strVFFileName;

  CombineVts tcombineVts = null;
  loggerDebug("ExtendInvoiceF1PSave","YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY"+templatepath+"YYYYYYYYYYYYYYYYYYYYYYYYYYY"+strSql);

  //�ϲ�VTS�ļ�
  String strTemplatePath = application.getRealPath(templatepath) + "/";
  tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

  ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
  tcombineVts.output(dataStream);

  //��dataStream�洢�������ļ�
  AccessVtsFile.saveToFile(dataStream,strVFPathName);
  loggerDebug("ExtendInvoiceF1PSave","==> Write VTS file to disk ");

  loggerDebug("ExtendInvoiceF1PSave","===strVFFileName : "+strVFFileName);
  session.putValue("RealPath", strVFPathName);
  sql = "select agentcode from laagent where (agentstate='03' or agentstate='04')  and agentcode='"+tAgentcode+"'";
  tAgentSSRS = seExeSQL.execSQL(sql);
  //panduan
  if(tAgentSSRS.MaxRow>0 && tAgentSSRS.GetText(1,1) !=null && !tAgentSSRS.GetText(1,1).equals("null") && !tAgentSSRS.GetText(1,1).equals(""))
  {
    loggerDebug("ExtendInvoiceF1PSave","====================================================1");
    tlizhi = true;
  }
  loggerDebug("ExtendInvoiceF1PSave",sql);
  sql = "select agentcode from laagent where (salequaf='N' OR salequaf is null) and agentcode='"+tAgentcode+"'";
  tAgentSSRS = seExeSQL.execSQL(sql);
  if(tAgentSSRS.MaxRow>0 && tAgentSSRS.GetText(1,1) !=null && !tAgentSSRS.GetText(1,1).equals("null") && !tAgentSSRS.GetText(1,1).equals(""))
  {
    tlizhi = true;
    loggerDebug("ExtendInvoiceF1PSave","===================================================2");
  }
  loggerDebug("ExtendInvoiceF1PSave",sql);
  loggerDebug("ExtendInvoiceF1PSave",tG.ManageCom);
  if (tPrintType=="1"){
    String tSql = "select ManageCom ,Policycom from LJTempFee where TempFeeNo  = '"+tGetNoticeNoHidden+"'";
    loggerDebug("ExtendInvoiceF1PSave","��ѯLJTempFee��������Լ����ѻ���=================="+tSql);
    loggerDebug("ExtendInvoiceF1PSave","====================tGetNoticeNoHidden===================="+tGetNoticeNoHidden);
    SSRS tComSSRS = new SSRS();
    tComSSRS = seExeSQL.execSQL(tSql);
    tManageCom = tComSSRS.GetText(1, 1).substring(0,6);
    loggerDebug("ExtendInvoiceF1PSave","�����ɷѻ���==============="+tManageCom);
    tPolicyCom = tComSSRS.GetText(1, 2).substring(0,6);
    loggerDebug("ExtendInvoiceF1PSave","�����������==============="+tPolicyCom);
  }
  //if(!tlizhi && tFanCertify )
  if(!tlizhi && tFanCertify && tManageCom.equals(tPolicyCom))
  {
  		loggerDebug("ExtendInvoiceF1PSave","!tlizhi && tFanCertify");
    	//�����������get��ʽ�������ļ�·��
    	//response.sendRedirect("./GetF1PrintJ1.jsp?RealPath="+strVFPathName);
    	request.getRequestDispatcher("./GetF1PrintJ1.jsp?RealPath="+strVFPathName).forward(request,response);
  }
  else
  {
  		loggerDebug("ExtendInvoiceF1PSave","else");
    	//response.sendRedirect("../f1print/GetF1PrintJ1.jsp");
    
    	request.getRequestDispatcher("../f1print/GetF1PrintJ1.jsp").forward(request,response);
  }

  loggerDebug("ExtendInvoiceF1PSave","put session value");
%>
