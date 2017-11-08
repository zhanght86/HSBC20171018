<%
//程序名称：PEdorTypeGMSubmit.jsp
//程序功能：
//创建日期：
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.get.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.service.*" %>   

<%
 //接收信息，并作校验处理。
  //输入参数
  //个人批改信息
  
//System.out.println("-----GMsubmit---");



LPGetSchema tLPGetSchema   = new LPGetSchema(); 
LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();

TransferData aTransferData = new TransferData();
//PEdorGMDetailUI tPEdorGMDetailUI = new PEdorGMDetailUI();
//EdorDetailUI tEdorDetailUI = new EdorDetailUI();
//GEdorDetailUI tGEdorDetailUI = new GEdorDetailUI();
     String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	 String GbusiName="GEdorDetailUI";
	 BusinessDelegate GBusinessDelegate=BusinessDelegate.getBusinessDelegate();

CErrors tError = null;
//后面要执行的动作：添加，修改

String tRela  = "";
String FlagStr = "";
String Content = "";
String transact = "";
String Result="";
double OldGetMoney = 0;
double NewGetMoney = 0;

//执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
transact = request.getParameter("fmtransact");
System.out.println("------transact:"+transact);
GlobalInput tG = new GlobalInput();
tG = (GlobalInput)session.getValue("GI");

String tPGrpFlag = request.getParameter("PGrpFlag"); //团险个险标记：Group为团险受益人变更，Person为个险受益人变更

try
{
    if (transact != null && transact.equals("QUEYR||GETMONEY"))  //领取标准重算并查询
    {
        //LCPolDB tLCPolDB = new LCPolDB();
        //tLCPolDB.setPolNo(request.getParameter("PolNo"));
        //if (tLCPolDB.getInfo())
        //{   
        //    PayPlanBL tPayPlanBL = new PayPlanBL(tLCPolDB.getSchema(), PubFun.getCurrentDate());
        //    LCGetDB tLCGetDB = new LCGetDB();
        //    tLCGetDB.setContNo(request.getParameter("ContNo"));
        //    tLCGetDB.setPolNo(request.getParameter("PolNo"));
        //    tLCGetDB.setDutyCode(request.getParameter("DutyCode"));
        //    tLCGetDB.setGetDutyCode(request.getParameter("GetDutyCode"));
        //    tLCGetDB.setGetDutyKind(request.getParameter("GetDutyKind"));
        //    if (tLCGetDB.getInfo())
        //    {
        //        OldGetMoney = tPayPlanBL.calGetMoney(tLCGetDB.getSchema());
        //    }
        //    LPGetDB tLPGetDB = new LPGetDB();
        //    tLPGetDB.setEdorNo(request.getParameter("EdorNo"));
        //    tLPGetDB.setEdorType(request.getParameter("EdorType"));
        //    tLPGetDB.setContNo(request.getParameter("ContNo"));
        //    tLPGetDB.setPolNo(request.getParameter("PolNo"));
        //    tLPGetDB.setDutyCode(request.getParameter("DutyCode"));
        //    tLPGetDB.setGetDutyCode(request.getParameter("GetDutyCode"));
        //    tLPGetDB.setGetDutyKind(request.getParameter("GetDutyKind"));
        //    if (tLPGetDB.getInfo())
        //    {
				//LCGetSchema tLCGetSchema = new LCGetSchema();
        //        Reflections tRef = new Reflections();
        //        tRef.transFields(tLCGetSchema, tLPGetDB.getSchema());
        //        NewGetMoney = tPayPlanBL.calGetMoney(tLCGetSchema);
        //    }
        //    
        //}
    }
    else if (transact != null && transact.equals("UPDATE||MAIN"))
    {
        //个人保单批改信息
        tLPEdorItemSchema.setPolNo(request.getParameter("PolNo"));
        tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
        tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
        tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
        tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));		
        tLPEdorItemSchema.setInsuredNo(request.getParameter("InsuredNo"));
        //
        tLPGetSchema.setEdorNo(request.getParameter("EdorNo"));
        tLPGetSchema.setEdorType(request.getParameter("EdorType"));
        tLPGetSchema.setContNo(request.getParameter("ContNo"));
        tLPGetSchema.setPolNo(request.getParameter("PolNo"));
        tLPGetSchema.setDutyCode(request.getParameter("DutyCode"));
        tLPGetSchema.setGetDutyCode(request.getParameter("GetDutyCode"));
        tLPGetSchema.setGetDutyKind(request.getParameter("GetDutyKind"));
        
        // 准备传输数据 VData
  	    VData tVData = new VData();  
        //保存个人保单信息(保全)	
          tVData.addElement(tG);
        tVData.addElement(tLPEdorItemSchema);
        tVData.addElement(tLPGetSchema);
        boolean tag = true; 
        if("Group".equals(tPGrpFlag))
        {
            System.out.println("Group Submit!");
            LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
            tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
            tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
            tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
            LPGetSet tLPGetSet = new LPGetSet();
            tLPGetSet.add(tLPGetSchema);
            tVData.addElement(tLPGrpEdorItemSchema);
            tVData.addElement(tLPGetSet);
//            tag = tGEdorDetailUI.submitData(tVData,"GM||PERSON");
            tag = GBusinessDelegate.submitData(tVData,"GM||PERSON",busiName);         
        }
        else
        {
            System.out.println("Person Submit");
//            tag = tEdorDetailUI.submitData(tVData,transact);
            tag = tBusinessDelegate.submitData(tVData,transact,busiName);         
        }
    }
}
catch (Exception ex)
{
    Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
}

    //如果在Catch中发现异常，则不从错误类中提取错误信息
    if (FlagStr=="") {
    	if("Group".equals(tPGrpFlag))
    	{
//    		tError = tGEdorDetailUI.mErrors;
    		tError = GBusinessDelegate.getCErrors();
    	}
        else
        {
//    		tError = tEdorDetailUI.mErrors;
    		tError = tBusinessDelegate.getCErrors();
    	}
    	if (!tError.needDealError()) 
    	{
            Content = " 保存成功";
    		FlagStr = "Success";  
    	}else{
    		Content = " 保存失败，原因是:" + tError.getFirstError();
    		FlagStr = "Fail";
    	}
  	}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=OldGetMoney%>","<%=NewGetMoney%>");
</script>
</html>

