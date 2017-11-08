<%
//程序名称：PEdorTypeHISubmit.jsp
//程序功能： 
//创建日期：2005-07-09
//创建人  ：Nicholas
//修改时间：2005-07
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.LCContDB"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
  <%@page import="com.sinosoft.service.*" %> 

<%
  //个人批改信息
  //TransferData tTransferData = new TransferData();
  LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
//  EdorDetailUI tEdorDetailUI = new EdorDetailUI();
  	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  LPCustomerImpartSchema tLPCustomerImpartSchema = null;
  LPCustomerImpartSet tLPCustomerImpartSet = new LPCustomerImpartSet();
    
  //后面要执行的动作：
  CErrors tError = null;                 
  String FlagStr = "";
  String Content = "";
  String transact = "";
  
  //transact = request.getParameter("fmtransact");
  transact = "INSERT||MAIN";
  //System.out.println("---transact: " + transact);  
  
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	
  //个人保单批改信息
  tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
	tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
  tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
	tLPEdorItemSchema.setInsuredNo(request.getParameter("CustomerNo"));
  //tLPEdorItemSchema.setPolNo(request.getParameter("PolNo"));
	tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate")); 
	tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate")); 
	tLPEdorItemSchema.setStandbyFlag1(request.getParameter("CustomerRole")); 
	String tCustomerRole=request.getParameter("CustomerRole");
	
  String tImpartNum[] = request.getParameterValues("NewImpartGridNo");
  String tImpartVer[] = request.getParameterValues("NewImpartGrid1");            //告知版别
  String tImpartCode[] = request.getParameterValues("NewImpartGrid2");           //告知编码
  String tImpartContent[] = request.getParameterValues("NewImpartGrid3");        //告知内容
  String tImpartParamModle[] = request.getParameterValues("NewImpartGrid4");        //填写内容
  
//add by jiaqiangli 2009-02-18 LCCustomerImpart.ProposalContNo = lccont.prtno 参照新契约置法
	LCContDB tLCContDB = new LCContDB();
	tLCContDB.setContNo(request.getParameter("ContNo"));
	tLCContDB.getInfo();
	//add by jiaqiangli 2009-02-18 LCCustomerImpart.ProposalContNo = lccont.prtno 参照新契约置法
		
	int ImpartCount = 0;
	if(tImpartNum != null) ImpartCount = tImpartNum.length;
      
	for(int i = 0; i < ImpartCount; i++)
	{
  
		tLPCustomerImpartSchema = new LPCustomerImpartSchema();
    //tLPCustomerImpartSchema.setProposalContNo(request.getParameter("ContNo"));
    tLPCustomerImpartSchema.setProposalContNo(tLCContDB.getPrtNo());
    tLPCustomerImpartSchema.setContNo(request.getParameter("ContNo"));
		tLPCustomerImpartSchema.setCustomerNo(request.getParameter("CustomerNo"));
		tLPCustomerImpartSchema.setCustomerNoType(tCustomerRole);
		tLPCustomerImpartSchema.setImpartCode(tImpartCode[i]);
		tLPCustomerImpartSchema.setImpartContent(tImpartContent[i]);
		tLPCustomerImpartSchema.setImpartParamModle(tImpartParamModle[i]);
		tLPCustomerImpartSchema.setImpartVer(tImpartVer[i]) ;
		//tLPCustomerImpartSchema.setPatchNo("0");
		tLPCustomerImpartSet.add(tLPCustomerImpartSchema);
		System.out.println("------------------------" + tImpartVer[i]);
		System.out.println("------------------------" + tImpartCode[i]);
		System.out.println("------------------------" + tImpartContent[i]);
		System.out.println("------------------------" + tImpartParamModle[i]);
	}
	
  try 
  {
    // 准备传输数据 VData
    VData tVData = new VData();  
  	
    //保存个人保单信息(保全)	
		 tVData.add(tG);
		 tVData.add(tLPEdorItemSchema);
		 //tVData.add(tTransferData);
		 tVData.add(tLPCustomerImpartSet);
    
//     boolean tag = tEdorDetailUI.submitData(tVData,transact);
     boolean tag = tBusinessDelegate.submitData(tVData,transact,busiName);
  } 
  catch(Exception ex) 
  {
    Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
	}			
	
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr == "") 
  {
//  	tError = tEdorDetailUI.mErrors;
  	tError = tBusinessDelegate.getCErrors(); 
  	if (!tError.needDealError()) 
  	{        
  		FlagStr = "Success";                  
      Content = "保存成功！";
    } 
    else  
    {
  		Content = " 保存失败，原因是:" + tError.getFirstError();
  		FlagStr = "Fail";
  	}
	}
%>   
                   
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>
