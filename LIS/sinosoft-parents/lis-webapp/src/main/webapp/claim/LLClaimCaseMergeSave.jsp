<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.sys.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.service.*" %>
<%
  

	  String mlastClmNo="";
	  String mlastAccNo="";
	
	  String tRadio[] = request.getParameterValues("InpLLClaimMergeGridSel");             
	  String lastClmNo[] = request.getParameterValues("LLClaimMergeGrid1");
	  String lastAccNo[] = request.getParameterValues("LLClaimMergeGrid8");
	 
	
	  //先判断哪一行被选中
	   for(int i=0;i<tRadio.length;i++)
	   {
	      loggerDebug("LLClaimCaseMergeSave","***"+i);
	       if(tRadio[i].equals("1"))     
	       {      
	        	loggerDebug("LLClaimCaseMergeSave","第"+(i+1)+"行被选中");
	        
	        	//得到已结案案件的立案号
	        	mlastClmNo=lastClmNo[i];
	        	mlastAccNo=lastAccNo[i];
	  			loggerDebug("LLClaimCaseMergeSave","已结案案件的赔案号:"+mlastClmNo+",事件号:"+mlastAccNo);
	  		}
	   }     
	  
		  
	  String mAccNo=request.getParameter("AccNo");
      String mClmNo=request.getParameter("ClmNo");
      
      loggerDebug("LLClaimCaseMergeSave","本次案件的赔案号:"+mClmNo+",事件号:"+mAccNo);

	  
	  //操作类型标志
	  String FlagStr = "";
	  String Content = "";
	  CErrors tError = null;
	
	  GlobalInput tG = new GlobalInput();
	  tG=(GlobalInput)session.getValue("GI");
	  
	  String mfmtransact=request.getParameter("fmtransact");
	  loggerDebug("LLClaimCaseMergeSave","操作类型:"+mfmtransact);

	    
	  //String使用TransferData打包后提交
	  TransferData mTransferData = new TransferData();
	  mTransferData.setNameAndValue("ClmNo",mClmNo);
	  mTransferData.setNameAndValue("AccNo",mAccNo);
	  mTransferData.setNameAndValue("LastClmNo",mlastClmNo);
	  mTransferData.setNameAndValue("LastAccNo",mlastAccNo);
	  
	  //LLClaimMergeBL tLLClaimMergeBL=new LLClaimMergeBL();
	  String busiName="LLClaimMergeBL";
	  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  
	  String newAccNo=""; //返回的新事件号

	  try
	  {
	    VData tVData = new VData();
	    tVData.addElement(mTransferData);
	    tVData.addElement(tG);

	    //提交处理
//        if(!tLLClaimMergeBL.submitData(tVData,mfmtransact))
//        {
//            Content = " 数据提交失败，原因是: " + tLLClaimMergeBL.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
		 if(!tBusinessDelegate.submitData(tVData,mfmtransact,busiName))
		   {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
						   Content = "数据提交失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
						   FlagStr = "Fail";
				}
				else
				{
						   Content = "数据提交失败";
						   FlagStr = "Fail";				
				}
		   }

        else
        {
            Content = " 数据提交成功！";
            FlagStr = "Succ";
            
            VData tResult = new VData();
           // tResult = tLLClaimMergeBL.getResult();	
             tResult = tBusinessDelegate.getResult();	
            newAccNo = (String)tResult.getObjectByObjectName("String",0);
            loggerDebug("LLClaimCaseMergeSave","案件"+mClmNo+"原有事件号"+mAccNo+",新事件号:"+newAccNo);
        }
	  }
	  catch(Exception ex)
	  {
	    Content = "数据提交失败失败，原因是:" + ex.toString();
	    FlagStr = "Fail";
	  }	  
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=newAccNo%>");
</script>
</html>
