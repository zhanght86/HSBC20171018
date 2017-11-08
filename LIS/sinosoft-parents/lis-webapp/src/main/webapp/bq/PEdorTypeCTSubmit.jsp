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
	if(!transact.equals("EDORITEM|UPDATE"))
	{
		transact = "EDORITEM|INPUT";
	}
	String edorAcceptNo = request.getParameter("EdorAcceptNo");
	String edorNo = request.getParameter("EdorNo");
	String edorType = request.getParameter("EdorType");
	String contNo = request.getParameter("ContNo");
	String sEdorReasonCode = request.getParameter("SurrReason");
	String sEdorReason = request.getParameter("Remark");
	String sRelationToAppnt = request.getParameter("RelationToAppnt");
	String sWTContFLag=request.getParameter("WTContFLag");

	System.out.println("HHHHHHHHHHH"+sEdorReason);
    String tPolNo[]= request.getParameterValues("PolGrid9");
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
  tLPEdorItemSchema.setStandbyFlag3(sWTContFLag); //用于判断在选择险种退保时，如果选择主险，则要求将附件险一起选择
		
  String LPBnfName[] = request.getParameterValues("CustomerActBnfGrid1"); //姓名
  String LPBnfSex[] = request.getParameterValues("CustomerActBnfGrid2"); //性别
  String LPBnfDate[] = request.getParameterValues("CustomerActBnfGrid3"); //生日
  String LPBnfIDType[] = request.getParameterValues("CustomerActBnfGrid4"); //证件类型
  String LPBnfIDNo[] = request.getParameterValues("CustomerActBnfGrid5"); //证件号码
  //String LPBnfRelation[] = request.getParameterValues("CustomerActBnfGrid6"); //与被保人关系
  String LPBnfLot[] = request.getParameterValues("CustomerActBnfGrid6"); //领取金额
  String LPBnfGetFrom[] = request.getParameterValues("CustomerActBnfGrid7"); //领取方式
  String LPBnfBankCode[] = request.getParameterValues("CustomerActBnfGrid8"); //银行编码
  String LPBnfBankAcc[] = request.getParameterValues("CustomerActBnfGrid9"); //银行账号
  String LPBnfBankAccName[] = request.getParameterValues("CustomerActBnfGrid10"); //银行帐户名
  
  LPBnfSet tLPBnfSet = new LPBnfSet();
  if(LPBnfName!=null)
  {
	  for(int i=0;i<LPBnfName.length;i++)
	  {
	  	LPBnfSchema tLPBnfSchema = new LPBnfSchema();
	  	tLPBnfSchema.setName(LPBnfName[i]);
	  	tLPBnfSchema.setSex(LPBnfSex[i]);
	  	tLPBnfSchema.setBirthday(LPBnfDate[i]);
	  	tLPBnfSchema.setIDType(LPBnfIDType[i]);
	  	tLPBnfSchema.setIDNo(LPBnfIDNo[i]);
	  	//tLPBnfSchema.setRelationToInsured(LPBnfRelation[i]);
	  	tLPBnfSchema.setBnfLot(LPBnfLot[i]);
	  	tLPBnfSchema.setRemark(LPBnfGetFrom[i]);
	  	tLPBnfSchema.setBankCode(LPBnfBankCode[i]);
	  	tLPBnfSchema.setBankAccNo(LPBnfBankAcc[i]);
	  	tLPBnfSchema.setAccName(LPBnfBankAccName[i]);
	  	tLPBnfSet.add(tLPBnfSchema);
	  }
  }

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
		tVData.add(tLPBnfSet);
		//tEdorDetailUI.submitData(tVData, "EDORITEM|INPUT");
//		tEdorDetailUI.submitData(tVData, transact);
		tBusinessDelegate.submitData(tVData, transact,busiName);
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

