<%
//程序名称：PEdorTypeWTSubmit.jsp
//程序功能：
//创建日期：2002-07-19 16:49:22
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
  <%@page import="com.sinosoft.service.*" %>

<%
 //接收信息，并作校验处理。
  //输入参数
  //个人批改信息
  
  
  CErrors tError = null;
  //后面要执行的动作：添加，修改
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String transact = "";

  LPPolSet mLPPolSet=new LPPolSet();
  

  //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
  	GlobalInput tGlobalInput = new GlobalInput();
	tGlobalInput = (GlobalInput)session.getValue("GI");

	transact = request.getParameter("fmtransact");
	String edorAcceptNo = request.getParameter("EdorAcceptNo");
	String edorNo = request.getParameter("EdorNo");
	String edorType = request.getParameter("EdorType");
	String contNo = request.getParameter("ContNo");
	String sEdorReasonCode = request.getParameter("SurrReason");
	String sEdorReason = request.getParameter("Remark");
	String sRelationToAppnt = request.getParameter("RelationToAppnt");
	String sFeeGetFlag=request.getParameter("FeeGetFlag"); //获得是否扣除工本费的标记
	 //犹豫期退保标志，确定是整单退还是只退附加险，0,只退附加险，1 退全部
	String sWTContFLag=request.getParameter("WTContFLag");
	System.out.println("否扣除工本费的标记(0,不扣除，1，扣除);"+sFeeGetFlag);
	
	System.out.println("犹豫期退保标志，确定是整单退还是只退附加险(0,只退附加险，1 退全部);"+sWTContFLag);

		System.out.println("退保原因;"+sEdorReason);
    String tPolNo[]= request.getParameterValues("PolGrid8");
    String tChk[] = request.getParameterValues("InpPolGridChk");
    for (int i=0; i<tChk.length; i++)
    {
        System.out.println("$$$$$$$$$$$44"+tChk[i]);
        if (tChk[i].equals("1"))
        {
            LPPolSchema tLPPolSchema=new LPPolSchema();
            tLPPolSchema.setPolNo(tPolNo[i]);
            System.out.println("++++++++++++++++"+tPolNo[i]);
            mLPPolSet.add(tLPPolSchema);
        }
    }
    if (mLPPolSet == null || mLPPolSet.size() < 1)
    {
	      Content = "请选择要退保的保单!";
	      FlagStr = "Fail";
    }            

	LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
	tLPEdorItemSchema.setEdorAcceptNo(edorAcceptNo);
	tLPEdorItemSchema.setEdorNo(edorNo);
	tLPEdorItemSchema.setContNo(contNo);
	tLPEdorItemSchema.setEdorType(edorType);
	tLPEdorItemSchema.setInsuredNo("000000");
	tLPEdorItemSchema.setPolNo("000000");
	tLPEdorItemSchema.setEdorReasonCode(sEdorReasonCode);
	tLPEdorItemSchema.setEdorReason(sEdorReason);
	tLPEdorItemSchema.setStandbyFlag2(sRelationToAppnt); //投保人与业务员关系
	tLPEdorItemSchema.setStandbyFlag1(sFeeGetFlag); //是否扣除工本费，默认扣除 Add By PST
  tLPEdorItemSchema.setStandbyFlag3(sWTContFLag); //用于判断在选择险种退保时，如果选择主险，则要求将附件险一起选择
	

	// 准备传输数据 VData	
        	
	VData tVData = new VData();
//	EdorDetailUI tEdorDetailUI   = new EdorDetailUI();
	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	try
	{		   
		tVData.add(tGlobalInput);
		tVData.add(tLPEdorItemSchema);
		tVData.add(mLPPolSet);
//		tEdorDetailUI.submitData(tVData, "");
		tBusinessDelegate.submitData(tVData, "" ,busiName);
	}
	catch(Exception ex)
	{
	      Content = "保存失败，原因是:" + ex.toString();
	      FlagStr = "Fail";
	}
	if ("".equals(FlagStr))
	{
//		    tError = tEdorDetailUI.mErrors;
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {
				Content ="保存成功！";
		    	FlagStr = "Succ";               
		    }
		    else                                                                           
		    {
		    	Content = "保存失败，原因是:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
	}   

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

