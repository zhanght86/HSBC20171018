<%
//程序名称：PEdorTypeENSubmit.jsp
//程序功能：
//创建日期：2005-06-23
//创建人  ：WangWei
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
  //基本类定义
  LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
  LCPolSchema tLCPolSchema = null;
  LCPolSet tLCPolSet = new LCPolSet();

//  EdorDetailUI tEdorDetailUI = new EdorDetailUI();
  	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

  GlobalInput tGlobalInput = new GlobalInput();
  tGlobalInput = (GlobalInput)session.getValue("GI");

  System.out.println("-----" + tGlobalInput.Operator);
  //输出参数
  CErrors tError = null;
  //后面要执行的动作：添加，修改，删除

  String FlagStr = "";
  String Content = "";
  VData Result = new VData();
  String transact = "";

  //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
  transact = request.getParameter("fmtransact");

  //个人批改信息
  tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
  tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
  tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
  tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
  tLPEdorItemSchema.setInsuredNo(request.getParameter("InsuredNo"));
  tLPEdorItemSchema.setPolNo(request.getParameter("PolNo"));
  tLPEdorItemSchema.setOperator(tGlobalInput.Operator);

	//获得用户选择的领取信息，程序将在表LJAGetDraw删除这些信息对应的记录
	String tGridNo[] = request.getParameterValues("PolGridNo");
	String tChk[] = request.getParameterValues("InpPolGridChk");
	String tPolNo[] =request.getParameterValues("PolGrid4");
	String tRnewFlag[] = request.getParameterValues("PolGrid7");
		
	int iCount = tGridNo.length;
	System.out.println("--icount:"+iCount);

	for (int i=0;i<iCount;i++)
	{ 	
		if (tChk[i].equals("1"))
		{
			tLCPolSchema = new LCPolSchema();
			tLCPolSchema.setPolNo(tPolNo[i]);
			tLCPolSchema.setRnewFlag(tRnewFlag[i]);
			System.out.println(tLCPolSchema.getRnewFlag());
			tLCPolSet.add(tLCPolSchema);
		}
	}

  try
  {
      // 准备传输数据 VData
      VData tVData = new VData();

      //保存信息(保全)
      tVData.addElement(tGlobalInput);
      tVData.addElement(tLPEdorItemSchema);
      tVData.addElement(tLCPolSet);

//      boolean tag = tEdorDetailUI.submitData(tVData,"");
      boolean tag = tBusinessDelegate.submitData(tVData,"",busiName);
      if (tag)
      {
          System.out.println("Successful");
          //Result = tEdorDetailUI.getResult();
          //Integer flag1 = (Integer) Result.getObjectByObjectName("Integer",0);
          //if (flag1.intValue() == 1)
          //{
          //  FlagStr = "Yes";        //成功且有暂交费
          //} else
          //{
          //  FlagStr = "True";       //成功无暂交费
          //}
      }
      else
      {
          System.out.println("Fail");
          FlagStr = "Fail";         //失败
      }
  }
  catch (Exception ex)
  {
      Content = "保存失败，原因是:" + ex.toString();
      FlagStr = "Fail";
  }
  //如果在Catch中发现异常，则不从错误类中提取错误信息
//  tError = tEdorDetailUI.mErrors;
  tError = tBusinessDelegate.getCErrors(); 
  if (!tError.needDealError())
      {
          Content = " 保存成功！";
          FlagStr = "Success";
      }
      else
      {
          Content = " 保存失败，原因是:" + tError.getFirstError();
          FlagStr = "Fail";
      }
  System.out.println("OK!");

  //添加各种预处理

%>

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=Result%>");
</script>
</html>
