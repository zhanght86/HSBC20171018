<%
//GrPEdorTypeARSubmit.jsp
//程序功能：
//创建日期：2007-05-24 16:49:22
//创建人  ：ck
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
 <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@page import="java.lang.*"%>
 <%@page import="com.sinosoft.service.*" %> 
 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
 //接收信息，并作校验处理。
  //输入参数
  //个人批改信息
  System.out.println("-----RDsubmit---");
  CErrors tError = null;
  //后面要执行的动作：添加，修改      
  String FlagStr = "";
  String Content = "";
  String fmAction = request.getParameter("Transact");
  
  LPGrpEdorItemSchema tLPGrpEdorItemSchema=new LPGrpEdorItemSchema();
  //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
  tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
  tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
  tLPGrpEdorItemSchema.setEdorAppNo(request.getParameter("EdorAppNo"));
  tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
  tLPGrpEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
  
  LPInsuAccOutSchema tLPInsuAccOutSchema=new LPInsuAccOutSchema();

  String EdorNo=request.getParameter("EdorNo");
  String EdorType=request.getParameter("EdorType");
  String GrpContNo=request.getParameter("GrpContNo");
  String OutPolNo="";
  String OutInsuAccNo="";
  String OutPayPlanCode="";
  String AccOutType="";
  String AccOutUnit="";
  String AccOutBala="";

  if(fmAction.equals("INSERT||PMAIN"))
  {
	  String tLCGrpInsuAccNo[] = request.getParameterValues("LCGrpInsuAccGridNo"); //序号字段
	  String tRadio[] = request.getParameterValues("InpLCGrpInsuAccGridSel");
	  //注意：radio字段的名字结构为Inp+GridName+Sel，这里GridName=LCGrpInsuAccGrid
	  int tCount = tLCGrpInsuAccNo.length;   //纪录的总行数
	  int tSelNo =0 ;
	  for (int i = 0; i < tCount; i++){
		if (tRadio [i].equals("1")) {             //如果该行被选中
			   tSelNo = i;
		 }
	  }
	  String[] tOutPolNo=request.getParameterValues("LCGrpInsuAccGrid2");//赎回保单
	  String[] tOutInsuAccNo=request.getParameterValues("LCGrpInsuAccGrid3");//赎回账户
	  String[] tOutPayPlanCode=request.getParameterValues("LCGrpInsuAccGrid5");//赎回子账户
	  String[] tCanMoveUnit=request.getParameterValues("LCGrpInsuAccGrid7");//可赎回单位
	  String[] tAccOutUnit=request.getParameterValues("LCGrpInsuAccGrid9");//变动单位
	  String[] tAccOutBala=request.getParameterValues("LCGrpInsuAccGrid10");//变动金额
	  
	  OutPolNo=tOutPolNo[tSelNo];
	  OutInsuAccNo=tOutInsuAccNo[tSelNo];
	  OutPayPlanCode=tOutPayPlanCode[tSelNo];

	  if(tAccOutUnit[tSelNo]!=null&&!tAccOutUnit[tSelNo].equals(""))
	  {
	  	if(!PubFun.isNumeric(tAccOutUnit[tSelNo]))
	  	{
	  		FlagStr="Fail";
	  		Content = "请输入正确的赎回单位数!";
	  	}else
	  	{
		  	if(Double.parseDouble(tAccOutUnit[tSelNo])>Double.parseDouble(tCanMoveUnit[tSelNo]))
	  	  	{
	  	  		FlagStr="Fail";
		  		Content = "输入的单位数应小于或等于可赎回单位!";
	  	  	}else
	  		{
				AccOutType="1";
				AccOutUnit=tAccOutUnit[tSelNo];
	  		}
	  	}
	  }else if(tAccOutBala[tSelNo]!=null&&!tAccOutBala[tSelNo].equals(""))
	  {
	  	if(!PubFun.isNumeric(tAccOutBala[tSelNo]))
	  	{
	  		FlagStr="Fail";
	  		Content = "请输入正确的赎回金额数!";
	  	}else
	  	{
			AccOutType="2";
			AccOutBala=tAccOutBala[tSelNo];
	  	}
	  }
	  
	  /*设置账户赎回信息*/
	  tLPInsuAccOutSchema.setEdorNo(EdorNo);
  	  tLPInsuAccOutSchema.setEdorType(EdorType);
  	  tLPInsuAccOutSchema.setGrpContNo(GrpContNo);
  	  tLPInsuAccOutSchema.setOutPolNo(OutPolNo);
  	  tLPInsuAccOutSchema.setOutInsuAccNo(OutInsuAccNo);
  	  tLPInsuAccOutSchema.setOutPayPlanCode(OutPayPlanCode);
  	  tLPInsuAccOutSchema.setAccOutType(AccOutType);
  	  tLPInsuAccOutSchema.setAccOutUnit(AccOutUnit);
  	  tLPInsuAccOutSchema.setAccOutBala(AccOutBala);

	  System.out.println("AccOutType:"+AccOutType);
	  System.out.println("AccOutUnit:"+AccOutUnit);
	  System.out.println("AccOutBala:"+AccOutBala);
  }
  else if(fmAction.equals("DELETE||PMAIN"))//交易撤销
  {
  	  String tLPInsuAccNo[] = request.getParameterValues("LPInsuAccGridNo"); //序号字段
	  String aRadio[] = request.getParameterValues("InpLPInsuAccGridSel");
	  //注意：radio字段的名字结构为Inp+GridName+Sel，这里GridName=LPInsuAccGrid
	  int aCount = tLPInsuAccNo.length;   //纪录的总行数
	  int aSelNo =0 ;
	  for (int i = 0; i < aCount; i++){
		if (aRadio [i].equals("1")) {             //如果该行被选中
			   aSelNo = i;
		 }
	  }
	  String[] tSerialNo=request.getParameterValues("LPInsuAccGrid9");//流水号
	  String[] tOutPolNo=request.getParameterValues("LPInsuAccGrid2");//保单号
	  String[] tOutInsuAccNo=request.getParameterValues("LPInsuAccGrid3");//赎回账户
	  String[] tOutPayPlanCode=request.getParameterValues("LPInsuAccGrid4");//赎回子账户
  	  tLPInsuAccOutSchema.setSerialNo(tSerialNo[aSelNo]);
  	  tLPInsuAccOutSchema.setOutPolNo(tOutPolNo[aSelNo]);
  	  tLPInsuAccOutSchema.setOutInsuAccNo(tOutInsuAccNo[aSelNo]);
  	  tLPInsuAccOutSchema.setOutPayPlanCode(tOutPayPlanCode[aSelNo]);
  }    

  if(FlagStr.equals("Fail"))
  {
  	//错误处理
  }else
  {
	  try
	  {
	  	 //PEdorRDDetailUI tPEdorRDDetailUI   = new PEdorRDDetailUI();
	  	 String busiName="PEdorARDetailUI";
		 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
	  	 //准备传输数据 VData
	  	 VData tVData = new VData();  
		 //保存个人保单信息(保全)
		 tVData.addElement(tG);	 	
		 tVData.addElement(tLPGrpEdorItemSchema);
		 tVData.addElement(tLPInsuAccOutSchema);
	     //tPEdorRDDetailUI.submitData(tVData,fmAction);
	     tBusinessDelegate.submitData(tVData, fmAction,busiName);
			
	  	//如果在Catch中发现异常，则不从错误类中提取错误信息
	 	if (FlagStr=="")
	  	{
	    		tError = tBusinessDelegate.getCErrors();
	    		if (!tError.needDealError())
	    		{
	      			Content = " 操作成功";
	    			FlagStr = "Succ";
	    		}
	    		else
	    		{
	    			Content = " 操作失败，原因是:" + tError.getFirstError();
	    			FlagStr = "Fail";
	    		}
	  	}
	  }
	  catch(Exception ex)
	  {
		Content = fmAction+"失败，原因是:" + ex.toString();
		FlagStr = "Fail";
	  }
  }
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

