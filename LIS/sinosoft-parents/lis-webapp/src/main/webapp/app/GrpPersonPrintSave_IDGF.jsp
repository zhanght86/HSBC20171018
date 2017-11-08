<%
//�������ƣ�GrpPersonPrintSave.jsp
//�����ܣ�
//�������ڣ�2007-04-06 10:00
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
  <%@page import="com.sinosoft.service.*" %> 
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%!
  
  
  InputStream ins = null;
  	
  String handleFunction(HttpSession session, HttpServletRequest request, String szTemplatePath)
  {
    loggerDebug("GrpPersonPrintSave_IDGF","111");
    
    GlobalInput globalInput = new GlobalInput();
		
    if( (GlobalInput)session.getValue("GI") == null )
    {
      return "��ҳ��ʱ������û�в���Ա��Ϣ�������µ�¼";
    }
    else
    {
      globalInput.setSchema((GlobalInput)session.getValue("GI"));
    }
    
    String transact = request.getParameter("fmtransact");
    loggerDebug("GrpPersonPrintSave_IDGF","fmtransact = " + transact);
    VData vData = new VData();
    
    if(transact!=null && transact.equals("PRINT||ALLGRPPERSON"))
    {
      String tGrpContNo[] = request.getParameterValues("GrpPolGrid1");
      LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
      if(tGrpContNo[0]==null || tGrpContNo[0].equals(""))
      {
        return "��ȡ��ӡ�ŵ���ʧ��!";
      }
      tLCGrpContSchema.setGrpContNo(tGrpContNo[0]);
		
      vData.addElement(tLCGrpContSchema);
      vData.add(globalInput);
    }
    else if(transact!=null && transact.equals("PRINT||GRPPERSON"))
    {
      String tContNo[] = request.getParameterValues("PolGrid1");
      String tChecks[] = request.getParameterValues("InpPolGridChk");
      LCContSet tLCContSet = new LCContSet();
      
      for(int nIndex = 0; nIndex < tChecks.length; nIndex++ )
      {
        if( !tChecks[nIndex].equals("1") )
        {
          continue;
        }
        LCContSchema tLCContSchema = new LCContSchema();
        if(tContNo[nIndex]==null || tContNo[nIndex].equals(""))
        {
          return "��ȡ��ӡ�ŵ����˱�����ʧ��!";
        }
        tLCContSchema.setContNo(tContNo[nIndex]);
        tLCContSet.add(tLCContSchema);
      }
      
      vData.addElement(tLCContSet);
      vData.add(globalInput);
    }
    
    
    String busiName="f1printLCGrpPersonF1P_IDGFBL";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    
	//  LCGrpPersonF1P_IDGFBL tLCGrpPersonF1P_IDGFBL = new LCGrpPersonF1P_IDGFBL();
    
    try
    {
    	tBusinessDelegate.submitData(vData, transact,busiName);
      if ( tBusinessDelegate.getCErrors().needDealError())
      {
        return tBusinessDelegate.getCErrors().getFirstError();
      }
      else
      {
        ins = (InputStream)(tBusinessDelegate.getResult().get(0));
        
        String tSN = "";
        tSN = PubFun1.CreateMaxNo("SNPolPrint",10);
        if (tSN.equals(""))
        {
          return "������ˮ��ʧ�ܣ��������´�ӡ";
        }
         
        //���ɴ�ӡXML�ļ�
        LDSysVarDB tLDSysVarDB = new LDSysVarDB();
        tLDSysVarDB.setSysVar("printdatanew");
        if ((!tLDSysVarDB.getInfo()) || (tLDSysVarDB.getSchema().getSysVarValue() == null)  || (tLDSysVarDB.getSchema().getSysVarValue().trim() == "") )
        {
          return "�޷��õ�ģ���ļ�·����";
        }
              
        String strTemplatePath = tLDSysVarDB.getSchema().getSysVarValue().trim();
	      //strTemplatePath ="c:/XML/";
        // It is important to use buffered streams to enhance performance
        BufferedInputStream bufIs = new BufferedInputStream(ins);
	
        String strTime = PubFun.getCurrentDate() + "_" + PubFun.getCurrentTime();
        strTime = strTime.replace(':', '_').replace('-', '_') ;
        strTime = strTime +"_"+tSN.trim();
        loggerDebug("GrpPersonPrintSave_IDGF","***************************************"+strTime);
//      add by ssx 2008-04-24 �����ּ�Ŀ¼
        String[] step = strTime.split("_");
        String year =  step[0];
        String month = step[1];
        String day = step[2];

        strTemplatePath += year+"/"+month+"/"+day+"/";
        File file = new File(strTemplatePath);
        if(!file.exists()){
      	  loggerDebug("GrpPersonPrintSave_IDGF","��ʼ�½��ļ���!");
      	  file.mkdirs();
        }else{
      	  loggerDebug("GrpPersonPrintSave_IDGF","Ŀ¼�Ѿ�����: ");
        } //add end
        loggerDebug("GrpPersonPrintSave_IDGF","$$$$$$$$$$$"+strTemplatePath);
        FileOutputStream fos = new FileOutputStream(strTemplatePath + "LCGrpPersonData" + strTime + ".xml");
        BufferedOutputStream bufOs = new BufferedOutputStream(fos);
        int n = 0;
	
        while( ( n = bufIs.read() ) != -1 )
        {
          bufOs.write(n);
        }
        bufOs.flush();
        bufOs.close();
          
        //FTP�Ĵ�ӡ������
       LisFtpClient tLisFtpClient = new LisFtpClient();
       if (!tLisFtpClient.UpLoadFile(strTemplatePath + "LCGrpPersonData" + strTime + ".xml", "LCGrpPersonData" + strTime + ".xml"))
       {
         return  "FTPģ���ļ�ʧ�ܣ�";
       }
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      return ex.getMessage();
    }
    return "";
  } //End handleFunction
%>

<%
  
  String FlagStr = "";
  String Content = "";
  String szTemplatePath = application.getRealPath("f1print/template/") + "/";
	
  //add by yt 20040426��Ϊ�˽��ͬʱ������ڴ�ӡ�ᵼ�²���ͬһ���ļ��������⡣

    try
    {
      loggerDebug("GrpPersonPrintSave_IDGF","Begin Print");
      Content = handleFunction(session, request, szTemplatePath);
			
      if( Content.equals("") )
      {
        FlagStr = "Succ";
        Content = "�����ɹ����";
      }
      else
      {
        FlagStr = "Fail";
      }
    }
    catch (Exception ex)
    {
      FlagStr = "Fail";
      Content = "д���ݿ�ɹ�������д�ļ�ʧ�ܣ������Щ�������в���";
      ex.printStackTrace();
    }
%>

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

