<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--�û�У����-->
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.certify.*"%>
  <%@page import="com.sinosoft.utility.*"%>
    <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.schema.LZCardPrintSchema"%>
  <%@page import="com.sinosoft.lis.schema.LZCardSchema"%>
  <%@page import="com.sinosoft.lis.vschema.LZCardPrintSet"%>
  <%@page import="com.sinosoft.lis.vschema.LZCardSet"%>

<%
  GlobalInput tGI = new GlobalInput();
  tGI=(GlobalInput)session.getValue("GI");
// �������
  String Print = (String)session.getValue("Print");
  String FlagStr = "";
  String Content = "";
  String PrintNo=(String)session.getValue("PrintNo");
  String tEasyPrintNo = request.getParameter("EasyPrintNo");
  String tEasyPrintFlag = request.getParameter("EasyPrintFlag");
  loggerDebug("operPrintTable","operPrintTablePrintNO:"+PrintNo);
  loggerDebug("operPrintTable","operPrintTablePolNo_PrintEx:"+(String)session.getValue("PolNo_PrintEx"));
    loggerDebug("operPrintTable","=======================++++++++++++++++++++==================1"+PrintNo);
  String PrintNoEx = (String)session.getValue("PrintNoEx");
  if(PrintNo==null || (PrintNoEx!=null && PrintNoEx.equals("EX")) )
  {
    //FlagStr="Fail:"+"û�еõ��ͻ��˵�session������ӡˢ�ţ���";
    String PolNo=(String)session.getValue("PolNo_PrintEx");
    loggerDebug("operPrintTable","PolNo:"+PolNo);
  //09-11-16  ���ӹ����ӡ�ĵ�֤�ķ��š����ն���
    if(!(tEasyPrintFlag==null||"".equals(tEasyPrintFlag))){
	    DealEasyPrintSysBL tDealEasyPrintSysBL = new DealEasyPrintSysBL();
		VData tVData1 = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("PolNo", PolNo); 
		tTransferData.setNameAndValue("EasyPrintNo", tEasyPrintNo); 
	    tVData1.add(tGI);
	    tVData1.add(tTransferData);
	    if(PolNo!=null){
			if(!tDealEasyPrintSysBL.submitData(tVData1,"")){
				FlagStr="Fail";
		        Content="��ӡʧ�ܣ� ԭ���ǣ�";
		        for(int i=1;i<tDealEasyPrintSysBL.mErrors.getErrorCount();i++){
		        	Content = Content+tDealEasyPrintSysBL.mErrors.getError(i).errorMessage;
		        }
		        FlagStr = Content;
			}else{
				FlagStr="Succ";
		        Content="��ӡ������ϣ�";
		        session.putValue("PolNo_PrintEx",null );
			}
	    }else{
	    	loggerDebug("operPrintTable","��ӡ������ϣ�");
	      	FlagStr="Succ";
	      	Content="��ӡ������ϣ�";
	    }
    }else{
        if(PolNo!=null) {
          ExeSQL tExeSQL = new ExeSQL();
          String strSql="update lccont set PrintCount=1 where contno in (select contno from lcpol where polno='"+PolNo+"' )";
          loggerDebug("operPrintTable","ִ��SQL���:"+strSql);
            loggerDebug("operPrintTable","=======================++++++++++++++++++++==================2"+PrintNo);
          if (!tExeSQL.execUpdateSQL(strSql)) {
            FlagStr="Fail";
            Content="��ӡʧ�ܣ������´�ӡ��";
          }
          else {
            FlagStr="Succ";
            Content="��ӡ������ϣ�";
            session.putValue("PolNo_PrintEx",null );
          }
        }
        else {
        	loggerDebug("operPrintTable","��ӡ������ϣ�");
          FlagStr="Succ";
          Content="��ӡ������ϣ�";
        }
    }
  }
  else if(PrintNo!=null && PrintNo.equals("00"))
  {
    LOPRTManager2Schema tLOPRTManager2Schema = new LOPRTManager2Schema();
    tLOPRTManager2Schema = (LOPRTManager2Schema)session.getValue("PrintQueue");
    VData tVData = new VData();
    tVData.add(tLOPRTManager2Schema);
    tVData.add(tGI);

    PrintManager2BL tPrintManager2BL = new PrintManager2BL();
    if(!tPrintManager2BL.submitData(tVData,"REQUEST"))
    {
      FlagStr="Fail:"+tPrintManager2BL.mErrors.getFirstError().toString();
    }
    else
    {
      FlagStr="Succ";
      Content="���´�ӡ���ݳɹ���";
// 	       session.putValue("PrintNo",null );
// 	       session.putValue("PrintQueue",null);

    }
    loggerDebug("operPrintTable","Print:"+FlagStr);
  }
  else if(PrintNo!=null && PrintNo.equals("01"))
  {
    LOPRTManager2Schema tLOPRTManager2Schema = new LOPRTManager2Schema();
    tLOPRTManager2Schema = (LOPRTManager2Schema)session.getValue("PrintQueue");
    VData tVData = new VData();
    tVData.add(tLOPRTManager2Schema);
    tVData.add(tGI);

    PrintManager2BL tPrintManager2BL = new PrintManager2BL();
    
    if(!tPrintManager2BL.submitData(tVData,"POSTREPRINT"))
    {
      FlagStr="Fail:"+tPrintManager2BL.mErrors.getFirstError().toString();
    }

    else
    {
      FlagStr="Succ";
      Content="���´�ӡ���ݳɹ���";
      session.putValue("PrintNo",null );
      session.putValue("PrintQueue",null);

    }
    loggerDebug("operPrintTable","Print:"+FlagStr);
  }
  else if(PrintNo!=null && (PrintNo.equals("32")||PrintNo.equals("33")||PrintNo.equals("35")))
  {
  	loggerDebug("operPrintTable","��ӡ���ڷ�Ʊ����ӡ�����״̬�ڷ��ŵ����и�ֵ��");
  	FlagStr = "Succ";
  }
  else
  {
    LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
    tLOPRTManagerSchema.setPrtSeq(PrintNo);
    loggerDebug("operPrintTable","=======================++++++++++++++++++++=================="+PrintNo);
    VData tVData = new VData();
    tVData.add(tLOPRTManagerSchema);
    tVData.add(tGI);

    PrintManagerBL tPrintManagerBL = new PrintManagerBL();
    if(!tPrintManagerBL.submitData(tVData,"PRINT"))
    {
    
    loggerDebug("operPrintTable","================================================3");
      FlagStr="Fail:"+tPrintManagerBL.mErrors.getFirstError().toString();
    }
    else
    {
      FlagStr="Succ";
      Content="���´�ӡ���ݳɹ���";
// 	     session.putValue("PrintNo",null );

    }
    loggerDebug("operPrintTable","Print:"+FlagStr);

  }

  if (FlagStr.equals("Succ"))
  {
	
	  loggerDebug("operPrintTable","Succ");
    GlobalInput tGI1 = new GlobalInput();
    tGI1=(GlobalInput)session.getValue("GI");
    // �������
    String FlagStr1 = "";
    String Content1 = "";
   PrintNo=(String)session.getValue("PrintNo");
    VData mResult1 = new VData();
    loggerDebug("operPrintTable","==================================================PrintNo+++++"+PrintNo);
   
    if(PrintNo!=null && (PrintNo.equals("32")||PrintNo.equals("33")||PrintNo.equals("35")))
    {
		    loggerDebug("operPrintTable","&&&&&&&&&&&&&&&&&&&--- ���뵥֤����.. ---&&&&&&&&&&&&&&&&&&&&&&");

			  GlobalInput tG = (GlobalInput)session.getValue("GI");
        LZCardSet setLZCard = new LZCardSet();
        LZCardSchema schemaLZCard = new LZCardSchema();
        LZCardPrintSet setLZCardPrint = new LZCardPrintSet();


        schemaLZCard.setSendOutCom(String.valueOf(session.getValue("ssManageCom")));														// ���Ż���
         loggerDebug("operPrintTable","==================================================ssManageCom+++++"+session.getValue("ssManageCom"));
        schemaLZCard.setReceiveCom(String.valueOf(session.getValue("ssAgentCode")));											// ���ܻ���
         loggerDebug("operPrintTable","==================================================ssAgentCode+++++"+session.getValue("ssAgentCode"));
        schemaLZCard.setCertifyCode("FF01");														// ��֤����
        schemaLZCard.setStartNo(String.valueOf(session.getValue("ChequNo")));															// ������ʼ����
        loggerDebug("operPrintTable","==================================================ChequNo+++++"+session.getValue("ChequNo"));
        schemaLZCard.setEndNo(String.valueOf(session.getValue("ChequNo")));																// ������ֹ����	
       // schemaLZCard.setSumCount(1);																	// ���ŵ�֤����
        schemaLZCard.setHandler(tGI.Operator);															// ������	
        schemaLZCard.setHandleDate(String.valueOf(PubFun.getCurrentDate()));											// ��������
        schemaLZCard.setInvaliDate(String.valueOf(PubFun.getCurrentDate()));											// ʧЧ����	
        schemaLZCard.setState("0");																		// ��֤״̬
        schemaLZCard.setOperator( tGI.Operator);															// ҵ��Ա

        setLZCard.add(schemaLZCard);
 loggerDebug("operPrintTable","=================================PrintNo:"+PrintNo);
        VData vData = new VData();
        vData.addElement(PrintNo);
        vData.addElement(tG);
        vData.addElement(setLZCard);
        vData.addElement(setLZCardPrint);
        vData.addElement("YES");
        
        CertSendOutBL tCertSendOutBL = new CertSendOutBL();
        loggerDebug("operPrintTable","wo kai shi gei chuan zhi le ");
        if(!tCertSendOutBL.submitData(vData, "INSERT"))
        {
		     		loggerDebug("operPrintTable"," ******************** ʧ��!ɵ�˰�,��ϸ��������Ĵ���ԭ��: *******************");
            FlagStr1="Fail:"+tCertSendOutBL.mErrors.getFirstError().toString();
        }
        else
      	{
	     		loggerDebug("operPrintTable"," ********************* ��֤���ųɹ�! ************************");
        	mResult1=tCertSendOutBL.getResult();
        	FlagStr1="Lcm";
        	session.putValue("PrintNo",null );
        	session.putValue("PrintQueue",null);
      	}
    }
    else if(PrintNo!=null && PrintNo.equals("00"))
    {
      loggerDebug("operPrintTable","55555555555555555555555555");
      LOPRTManager2Schema tLOPRTManager2Schema;
      tLOPRTManager2Schema = (LOPRTManager2Schema)session.getValue("PrintQueue");
      loggerDebug("operPrintTable",tLOPRTManager2Schema.getPrtSeq());
      loggerDebug("operPrintTable",tLOPRTManager2Schema.getCode());
      VData tVData1 = new VData();
      tVData1.add(tLOPRTManager2Schema);
      tVData1.add(tGI1 );

      AutoSysCertSendOutBL tAutoSysCertSendOutBL = new AutoSysCertSendOutBL();
      if(!tAutoSysCertSendOutBL.submitData(tVData1,"ASCSO2"))
      {
        FlagStr1="Fail:"+tAutoSysCertSendOutBL.mErrors.getFirstError().toString();
      }
      else
      {
        mResult1=tAutoSysCertSendOutBL.getResult();
        FlagStr1=(String)mResult1.getObjectByObjectName("String",0);
        session.putValue("PrintNo",null );
        session.putValue("PrintQueue",null);
      }
      loggerDebug("operPrintTable","SysCert:"+FlagStr1);
    }else if(PrintNo==null||PrintNo.equals("")){
    	FlagStr1="Nothing";
    }
    else 
    {
      LOPRTManagerSchema tLOPRTManagerSchema1 = new LOPRTManagerSchema();
      tLOPRTManagerSchema1.setPrtSeq(PrintNo);
      VData tVData1 = new VData();
      tVData1.add(tLOPRTManagerSchema1);
      tVData1.add(tGI1 );

      AutoSysCertSendOutBL tAutoSysCertSendOutBL = new AutoSysCertSendOutBL();
      if(!tAutoSysCertSendOutBL.submitData(tVData1,"ASCSO"))
      {
        FlagStr1="Fail:"+tAutoSysCertSendOutBL.mErrors.getFirstError().toString();
      }
      else
      {
        //FlagStr1=tAutoSysCertSendOutBL.getResult();
        mResult1=tAutoSysCertSendOutBL.getResult();
        FlagStr1=(String)mResult1.getObjectByObjectName("String",0);
        session.putValue("PrintNo",null );
      }
      loggerDebug("operPrintTable","SysCert:"+FlagStr1);
    }
    loggerDebug("operPrintTable","FlagStr1:"+FlagStr1);
    FlagStr = FlagStr1;
  }
  
 loggerDebug("operPrintTable","FlagStr:"+FlagStr);
%>
<html>
<script language="javascript">
window.returnValue="<%=FlagStr%>";
  window.close();
</script>
</html>

