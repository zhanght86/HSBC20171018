<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--用户校验类-->
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
// 输出参数
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
    //FlagStr="Fail:"+"没有得到客户端的session变量（印刷号）！";
    String PolNo=(String)session.getValue("PolNo_PrintEx");
    System.out.println("PolNo:"+PolNo);
  
    if(PolNo!=null)
    {
      ExeSQL tExeSQL = new ExeSQL();
      String strSql="update lcpol set PrintCount=1 where polno='"+PolNo+"'";
      System.out.println("执行SQL语句:"+strSql);
        System.out.println("=======================++++++++++++++++++++==================2"+PrintNo);
      if (!tExeSQL.execUpdateSQL(strSql))
      {
        FlagStr="Fail";
        Content="打印失败，请重新打印！";
      }
      else
      {
        FlagStr="Succ";
        Content="打印数据完毕！";
        session.putValue("PolNo_PrintEx",null );
      }
    }
    else
    {
    	System.out.println("打印数据完毕！");
      FlagStr="Succ";
      Content="打印数据完毕！";
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
      Content="更新打印数据成功！";
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
      Content="更新打印数据成功！";
      session.putValue("PrintNo",null );
      session.putValue("PrintQueue",null);

    }
    System.out.println("Print:"+FlagStr);
  }
  else if(PrintNo!=null && (Print.equals("32")||Print.equals("33")||Print.equals("35")))
  {
  	System.out.println("打印续期发票，打印管理表状态在发放的类中赋值！");
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
      Content="更新打印数据成功！";
// 	     session.putValue("PrintNo",null );

    }
    System.out.println("Print:"+FlagStr);

  }

  if (FlagStr.equals("Succ"))
  {
	
	  System.out.println("Succ");
    GlobalInput tGI1 = new GlobalInput();
    tGI1=(GlobalInput)session.getValue("GI");
    // 输出参数
    String FlagStr1 = "";
    String Content1 = "";
   PrintNo=(String)session.getValue("PrintNo");
    VData mResult1 = new VData();
    System.out.println("==================================================PrintNo+++++"+PrintNo);
   
    if(PrintNo!=null && (Print.equals("32")||Print.equals("33")||Print.equals("35")))
    {
		    System.out.println("&&&&&&&&&&&&&&&&&&&--- 进入单证发放测试... ---&&&&&&&&&&&&&&&&&&&&&&");

			  GlobalInput tG = (GlobalInput)session.getValue("GI");
        LZCardSet setLZCard = new LZCardSet();
        LZCardSchema schemaLZCard = new LZCardSchema();
        LZCardPrintSet setLZCardPrint = new LZCardPrintSet();


        schemaLZCard.setSendOutCom(String.valueOf(session.getValue("ssManageCom")));														// 发放机构
         System.out.println("==================================================ssManageCom+++++"+session.getValue("ssManageCom"));
        schemaLZCard.setReceiveCom(String.valueOf(session.getValue("ssAgentCode")));											// 接受机构
         System.out.println("==================================================ssAgentCode+++++"+session.getValue("ssAgentCode"));
        schemaLZCard.setCertifyCode((String)session.getValue("certifycode"));	
        schemaLZCard.setStartNo(String.valueOf(session.getValue("ChequNo")));															// 发放起始号码
        System.out.println("==================================================ChequNo+++++"+session.getValue("ChequNo"));
        schemaLZCard.setEndNo(String.valueOf(session.getValue("ChequNo")));																// 发放终止号码	
       // schemaLZCard.setSumCount(1);																	// 发放单证数量
        schemaLZCard.setHandler(tG.Operator);															// 经手人	
        schemaLZCard.setHandleDate(String.valueOf(PubFun.getCurrentDate()));											// 发放日期
        schemaLZCard.setInvaliDate(String.valueOf(PubFun.getCurrentDate()));											// 失效日期	
        //schemaLZCard.setState("0");																		// 单证状态
        schemaLZCard.setOperator( tG.Operator);															// 业务员

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
		     		System.out.println(" ******************** 失败!傻了吧,仔细看看下面的错误原因: *******************");
            FlagStr1="Fail:"+tCertSendOutBL.mErrors.getFirstError().toString();
        }
        else
      	{
	     		System.out.println(" ********************* 单证发放成功! ************************");
        	mResult1=tCertSendOutBL.getResult();
       System.out.println(" ******* 发票号码***********"+session.getValue("ChequNo"));
          System.out.println(" ******* 发票号码***********"+String.valueOf(session.getValue("ChequNo")));
           System.out.println(" ******* 单证类型***********"+(String)session.getValue("certifycode"));
          System.out.println(" ******* 开始操作***********"+tG.Operator);
        PubCertifyTakeBack tPubCertifyTakeBack = new PubCertifyTakeBack();
        if(!tPubCertifyTakeBack.CertifyTakeBack_A(String.valueOf(session.getValue("ChequNo")),String.valueOf(session.getValue("ChequNo")),(String)session.getValue("certifycode"),tG.Operator))
        {
            System.out.println("续期发票的回收错误:::::::::::::");
            System.out.println("结果："+tPubCertifyTakeBack.mErrors);
            FlagStr1="Fail";
          
        }
        else{
        /**
        *
        *单证的回收
        */
        System.out.println("续期发票回收成功");
        PubSubmit tPubSubmit = new PubSubmit();
        MMap tMap = new MMap();
        VData tVData = new VData();
        tMap = (MMap) tPubCertifyTakeBack.getResult().
                                           getObjectByObjectName("MMap", 0);
           
           
           tVData.add(tMap);
        if(!tPubSubmit.submitData(tVData, ""))
         {
           System.out.println("单证成功的提交完成.");
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

