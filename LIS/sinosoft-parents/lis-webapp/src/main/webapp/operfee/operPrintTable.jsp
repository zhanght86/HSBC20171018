<%@page contentType="text/html;charset=GBK" %>
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
<%@page import="com.sinosoft.lis.certify.CertSendOutBL"%>
<%@page import="com.sinosoft.lis.certify.PubCertifyTakeBack"%>

<%
  GlobalInput tGI = new GlobalInput();
  tGI=(GlobalInput)session.getValue("GI");
// �������
  String Print = (String)session.getValue("Print");
  String FlagStr = "";
  String Content = "";
  String PrintNo=(String)session.getValue("PrintNo");
  System.out.println("operPrintTablePrintNO:"+PrintNo);
  System.out.println("operPrintTablePolNo_PrintEx:"+(String)session.getValue("PolNo_PrintEx"));
    System.out.println("=======================++++++++++++++++++++==================1"+PrintNo);
  String PrintNoEx = (String)session.getValue("PrintNoEx");
  if(PrintNo==null || (PrintNoEx!=null && PrintNoEx.equals("EX")) )
  {
    //FlagStr="Fail:"+"û�еõ��ͻ��˵�session������ӡˢ�ţ���";
    String PolNo=(String)session.getValue("PolNo_PrintEx");
    System.out.println("PolNo:"+PolNo);
  
    if(PolNo!=null)
    {
      ExeSQL tExeSQL = new ExeSQL();
      String strSql="update lcpol set PrintCount=1 where polno='"+PolNo+"'";
      System.out.println("ִ��SQL���:"+strSql);
        System.out.println("=======================++++++++++++++++++++==================2"+PrintNo);
      if (!tExeSQL.execUpdateSQL(strSql))
      {
        FlagStr="Fail";
        Content="��ӡʧ�ܣ������´�ӡ��";
      }
      else
      {
        FlagStr="Succ";
        Content="��ӡ������ϣ�";
        session.putValue("PolNo_PrintEx",null );
      }
    }
    else
    {
    	System.out.println("��ӡ������ϣ�");
      FlagStr="Succ";
      Content="��ӡ������ϣ�";
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
    System.out.println("Print:"+FlagStr);
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
    System.out.println("Print:"+FlagStr);
  }
  else if(PrintNo!=null && (Print.equals("32")||Print.equals("33")||Print.equals("35")))
  {
  	System.out.println("��ӡ���ڷ�Ʊ����ӡ�����״̬�ڷ��ŵ����и�ֵ��");
  	FlagStr = "Succ";
  }
  else
  {
    LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
    tLOPRTManagerSchema.setPrtSeq(PrintNo);
    System.out.println("=======================++++++++++++++++++++=================="+PrintNo);
    VData tVData = new VData();
    tVData.add(tLOPRTManagerSchema);
    tVData.add(tGI);

    PrintManagerBL tPrintManagerBL = new PrintManagerBL();
    if(!tPrintManagerBL.submitData(tVData,"PRINT"))
    {
    
    System.out.println("================================================3");
      FlagStr="Fail:"+tPrintManagerBL.mErrors.getFirstError().toString();
    }
    else
    {
      FlagStr="Succ";
      Content="���´�ӡ���ݳɹ���";
// 	     session.putValue("PrintNo",null );

    }
    System.out.println("Print:"+FlagStr);

  }

  if (FlagStr.equals("Succ"))
  {
	
	  System.out.println("Succ");
    GlobalInput tGI1 = new GlobalInput();
    tGI1=(GlobalInput)session.getValue("GI");
    // �������
    String FlagStr1 = "";
    String Content1 = "";
   PrintNo=(String)session.getValue("PrintNo");
    VData mResult1 = new VData();
    System.out.println("==================================================PrintNo+++++"+PrintNo);
   
    if(PrintNo!=null && (Print.equals("32")||Print.equals("33")||Print.equals("35")))
    {
		    System.out.println("&&&&&&&&&&&&&&&&&&&--- ���뵥֤���Ų���... ---&&&&&&&&&&&&&&&&&&&&&&");

			  GlobalInput tG = (GlobalInput)session.getValue("GI");
        LZCardSet setLZCard = new LZCardSet();
        LZCardSchema schemaLZCard = new LZCardSchema();
        LZCardPrintSet setLZCardPrint = new LZCardPrintSet();


        schemaLZCard.setSendOutCom(String.valueOf(session.getValue("ssManageCom")));														// ���Ż���
         System.out.println("==================================================ssManageCom+++++"+session.getValue("ssManageCom"));
        schemaLZCard.setReceiveCom(String.valueOf(session.getValue("ssAgentCode")));											// ���ܻ���
         System.out.println("==================================================ssAgentCode+++++"+session.getValue("ssAgentCode"));
        schemaLZCard.setCertifyCode((String)session.getValue("certifycode"));	
        schemaLZCard.setStartNo(String.valueOf(session.getValue("ChequNo")));															// ������ʼ����
        System.out.println("==================================================ChequNo+++++"+session.getValue("ChequNo"));
        schemaLZCard.setEndNo(String.valueOf(session.getValue("ChequNo")));																// ������ֹ����	
       // schemaLZCard.setSumCount(1);																	// ���ŵ�֤����
        schemaLZCard.setHandler(tG.Operator);															// ������	
        schemaLZCard.setHandleDate(String.valueOf(PubFun.getCurrentDate()));											// ��������
        schemaLZCard.setInvaliDate(String.valueOf(PubFun.getCurrentDate()));											// ʧЧ����	
        //schemaLZCard.setState("0");																		// ��֤״̬
        schemaLZCard.setOperator( tG.Operator);															// ҵ��Ա

        setLZCard.add(schemaLZCard);
 System.out.println("=================================asdfasdfsadfasfasfasdfChequNo+++++"+PrintNo);
        VData vData = new VData();
        vData.addElement(PrintNo);
        vData.addElement(tG);
        vData.addElement(setLZCard);
        vData.addElement(setLZCardPrint);
        vData.addElement("YES");
        
        CertSendOutBL tCertSendOutBL = new CertSendOutBL();
        System.out.println("wo kai shi gei chuan zhi le ");
        if(!tCertSendOutBL.submitData(vData, "INSERT", PrintNo))
        {
		     		System.out.println(" ******************** ʧ��!ɵ�˰�,��ϸ��������Ĵ���ԭ��: *******************");
            FlagStr1="Fail:"+tCertSendOutBL.mErrors.getFirstError().toString();
        }
        else
      	{
	     		System.out.println(" ********************* ��֤���ųɹ�! ************************");
        	mResult1=tCertSendOutBL.getResult();
       System.out.println(" ******* ��Ʊ����***********"+session.getValue("ChequNo"));
          System.out.println(" ******* ��Ʊ����***********"+String.valueOf(session.getValue("ChequNo")));
           System.out.println(" ******* ��֤����***********"+(String)session.getValue("certifycode"));
          System.out.println(" ******* ��ʼ����***********"+tG.Operator);
        PubCertifyTakeBack tPubCertifyTakeBack = new PubCertifyTakeBack();
        if(!tPubCertifyTakeBack.CertifyTakeBack_A(String.valueOf(session.getValue("ChequNo")),String.valueOf(session.getValue("ChequNo")),(String)session.getValue("certifycode"),tG.Operator))
        {
            System.out.println("���ڷ�Ʊ�Ļ��մ���:::::::::::::");
            System.out.println("�����"+tPubCertifyTakeBack.mErrors);
            FlagStr1="Fail";
          
        }
        else{
        /**
        *
        *��֤�Ļ���
        */
        System.out.println("���ڷ�Ʊ���ճɹ�");
        PubSubmit tPubSubmit = new PubSubmit();
        MMap tMap = new MMap();
        VData tVData = new VData();
        tMap = (MMap) tPubCertifyTakeBack.getResult().
                                           getObjectByObjectName("MMap", 0);
           
           
           tVData.add(tMap);
        if(!tPubSubmit.submitData(tVData, ""))
         {
           System.out.println("��֤�ɹ����ύ���.");
         }
        	FlagStr1="Succ";
        	session.putValue("PrintNo",null );
        	session.putValue("PrintQueue",null);
        	}
      	}
    }
    else if(PrintNo!=null && PrintNo.equals("00"))
    {
      System.out.println("55555555555555555555555555");
      LOPRTManager2Schema tLOPRTManager2Schema;
      tLOPRTManager2Schema = (LOPRTManager2Schema)session.getValue("PrintQueue");
      System.out.println(tLOPRTManager2Schema.getPrtSeq());
      System.out.println(tLOPRTManager2Schema.getCode());
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
      System.out.println("SysCert:"+FlagStr1);
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
      System.out.println("SysCert:"+FlagStr1);
    }
    System.out.println("FlagStr1:"+FlagStr1);
    FlagStr = FlagStr1;
  }

 System.out.println("FlagStr:"+FlagStr);
%>
<html>
<script language="javascript">
window.returnValue="<%=FlagStr%>";
  window.close();
</script>
</html>

