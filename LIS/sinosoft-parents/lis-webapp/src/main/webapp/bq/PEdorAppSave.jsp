<%
//程序名称：PEdorSave.jsp
//程序功能：
//创建日期：2002-07-19 16:49:22
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
//			zhangtao	2005-04-28
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
  <%@page import="com.sinosoft.service.*" %>
    <%@page import="com.sinosoft.workflow.bq.*" %>
    <%@page import="com.sinosoft.lis.bq.*" %>
<%
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI"); //添加页面控件的初始化。     
     
    LPEdorAppSchema tLPEdorAppSchema=new LPEdorAppSchema(); 
    tLPEdorAppSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo")); //保全受理号
    tLPEdorAppSchema.setOtherNo(request.getParameter("OtherNo")); //申请号码
    tLPEdorAppSchema.setOtherNoType(request.getParameter("OtherNoType")); //申请号码类型
    tLPEdorAppSchema.setEdorAppName(request.getParameter("EdorAppName")); //申请人名称
    tLPEdorAppSchema.setAppType(request.getParameter("AppType")); //申请方式    
	tLPEdorAppSchema.setEdorAppDate(request.getParameter("EdorAppDate")); //批改柜面受理日期
	tLPEdorAppSchema.setBankCode(request.getParameter("BankCode"));
	tLPEdorAppSchema.setBankAccNo(request.getParameter("BankAccNo"));
	tLPEdorAppSchema.setAccName(request.getParameter("AccName"));
	
	//add by jiaqiangli 2009-01-04增加代办代理人信息
	tLPEdorAppSchema.setBehalfName(request.getParameter("BfName"));
	tLPEdorAppSchema.setBehalfIDType(request.getParameter("BfIDType"));
	tLPEdorAppSchema.setBehalfIDNo(request.getParameter("BfIDNo"));
	tLPEdorAppSchema.setBehalfPhone(request.getParameter("BfPhone"));
	tLPEdorAppSchema.setBehalfCode(request.getParameter("BfAgentCode"));
	
	//add by jiaqiangli 2009-01-04增加代办代理人信息 申请资格人
	tLPEdorAppSchema.setEdorAppPhone(request.getParameter("CustomerPhone"));
	tLPEdorAppSchema.setBehalfCodeCom(request.getParameter("ManageCom"));
	tLPEdorAppSchema.setSwitchChnlType(request.getParameter("InternalSwitchChnl"));
	tLPEdorAppSchema.setSwitchChnlName(request.getParameter("InternalSwitchChnlName"));
	//add by jiaqiangli 2009-01-04增加代办代理人信息
	
	tLPEdorAppSchema.setPayGetName(request.getParameter("PayGetName"));  //补退费领取人
	tLPEdorAppSchema.setPersonID(request.getParameter("PersonID"));  //补退费领取人身份证号
	
	//add by sunsx 2010-04-27 增加申请资格人三项信息
	tLPEdorAppSchema.setPhone(request.getParameter("Mobile"));
	tLPEdorAppSchema.setPostalAddress(request.getParameter("PostalAddress"));
	tLPEdorAppSchema.setZipCode(request.getParameter("ZipCode"));
	tLPEdorAppSchema.setManageCom(tG.ManageCom); //管理机构	
	tLPEdorAppSchema.setEdorState("3");

		
  /** 输出参数 */
	CErrors tError = new CErrors();   
	String tRela  = "";                
	String FlagStr = "";
	String Content = "";
	String transact = request.getParameter("LoadFlag");
  String Result = "";
  String sActivityID = "";
  ExeSQL yExeSQL = new ExeSQL();
  SSRS ySSRS = new SSRS();
  /**zhangfh**/
  if (transact.equals("edorApp"))
  {
    	sActivityID = request.getParameter("ActivityID");
   		
    	
  }
  else if (transact.equals("scanApp"))
  {
  		sActivityID = request.getParameter("ActivityID");
  }
  else if (transact.equals("edorTest"))
  {
  		sActivityID = request.getParameter("ActivityID");
  }
  	/**  为工作流[保全申请确认]、[保全撤销]节点准备数据  */
	TransferData mTransferData = new TransferData();
	String sOtherNoType = request.getParameter("OtherNoType");
	mTransferData.setNameAndValue("EdorAcceptNo", request.getParameter("EdorAcceptNo"));   			
	mTransferData.setNameAndValue("MissionID", request.getParameter("MissionID"));
	mTransferData.setNameAndValue("SubMissionID", request.getParameter("SubMissionID"));	
	mTransferData.setNameAndValue("ActivityID", sActivityID);
    mTransferData.setNameAndValue("BusiType", "1002");
	mTransferData.setNameAndValue("OtherNo", request.getParameter("OtherNo"));
	mTransferData.setNameAndValue("OtherNoType", request.getParameter("OtherNoType"));
	mTransferData.setNameAndValue("DefaultOperator", tG.Operator);
	mTransferData.setNameAndValue("ICFlag", "");
	mTransferData.setNameAndValue("EdorAppName", request.getParameter("EdorAppName"));
	mTransferData.setNameAndValue("Apptype", request.getParameter("AppType"));
	mTransferData.setNameAndValue("EdorAppDate", request.getParameter("EdorAppDate"));	
	mTransferData.setNameAndValue("ManageCom",tG.ManageCom );          
	mTransferData.setNameAndValue("EdorState","3");	
	mTransferData.setNameAndValue("NodeID",sActivityID);		
	mTransferData.setNameAndValue("Transact","INSERT||EDORAPP");
	if (sOtherNoType != null && sOtherNoType.trim().equals("3"))
	{
		mTransferData.setNameAndValue("AppntName", request.getParameter("AppntName"));
		mTransferData.setNameAndValue("PaytoDate", request.getParameter("MainPolPaytoDate"));
	}
	else
	{
		mTransferData.setNameAndValue("AppntName", "");
		mTransferData.setNameAndValue("PaytoDate", "");
	}
	VData tVData = new VData();       
	

		tVData.addElement(tG);
		tVData.addElement(tLPEdorAppSchema);
		tVData.add(mTransferData);
		String ErrorMessage = "";
   // String busiName="tWorkFlowUI";
    // String busiName="WorkFlowUI";
    PEdorAppBeforeEndService tPEdorAppBeforeEndService=new PEdorAppBeforeEndService();
    if(!tPEdorAppBeforeEndService.submitData(tVData,"")){
    	Content = "保存失败，原因是:" +tPEdorAppBeforeEndService.mErrors.getFirstError();
		FlagStr = "Fail";
		System.out.println("error="+Content);
    }
    else{
	    VData qVData=tPEdorAppBeforeEndService.getResult();
	    MMap pedorMap =(MMap) qVData.getObjectByObjectName("MMap",0);
	    
	    PEdorAppAfterEndService tPEdorAppAfterEndService=new PEdorAppAfterEndService();
	    if(!tPEdorAppAfterEndService.submitData(tVData,"")){
	    	Content = "保存失败，原因是:" +tPEdorAppAfterEndService.mErrors.getFirstError();
			FlagStr = "Fail";
			System.out.println("error="+Content);
	    } else{
		    VData kVData=tPEdorAppAfterEndService.getResult();
		    MMap appMap =(MMap) kVData.getObjectByObjectName("MMap",0);
		    pedorMap.add(appMap);
		    
		    VData tResult=new VData();
		    tResult.add(pedorMap);
		    PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(tResult, "")) {
				// @@错误处理
				//this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "tPubSubmit";
				// tError.functionName = "submitData";
				// tError.errorMessage = "数据提交失败!";
				// this.mErrors.addOneError(tError);
				Content = "保存失败，原因是:" +tPubSubmit.mErrors.getFirstError();
				FlagStr = "Fail";
				System.out.println("error="+Content);
				//return false;
			}
			 else
		 	{
				Content ="保存成功！";
				FlagStr = "Succ";	
			}  	
	    }
    }
	//
//	  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//	    if(!tBusinessDelegate.submitData(tVData,"create",busiName))
//	    {    
//	        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
//	        { 
//	           ErrorMessage=tBusinessDelegate.getCErrors().getError(0).moduleName;
//					   Content = "保存失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
//					   FlagStr = "Fail";
//					}
//					else
//					{
//					   Content = "保存失败";
//					   FlagStr = "Fail";				
//					}		
//		 
//		 }
//		 else
//		 {
//					  Content ="保存成功！";
//			    	FlagStr = "Succ";	
//		 }  	
      
	System.out.println("ErrorMessage="+ErrorMessage);
%>                      
<html>
<script language="javascript">
    parent.fraInterface.CusPrintcheck("<%=ErrorMessage%>");
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>

