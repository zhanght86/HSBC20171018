<%
//程序名称：PEdorSave.jsp
//程序功能：
//创建日期：2002-07-19 16:49:22
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>

<%
  LPEdorAppSchema tLPEdorAppSchema=new LPEdorAppSchema();
  
//  tLPEdorAppSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo")); //保全受理号
    tLPEdorAppSchema.setOtherNo(request.getParameter("OtherNo")); //申请号码
    tLPEdorAppSchema.setOtherNoType(request.getParameter("OtherNoType")); //申请号码类型
    tLPEdorAppSchema.setEdorAppName(request.getParameter("EdorAppName")); //申请人名称
    tLPEdorAppSchema.setAppType(request.getParameter("AppType")); //申请方式
    tLPEdorAppSchema.setManageCom(request.getParameter("ManageCom")); //管理机构
//    tLPEdorAppSchema.setChgPrem(request.getParameter("ChgPrem")); //变动的保费
//    tLPEdorAppSchema.setChgAmnt(request.getParameter("ChgAmnt")); //变动的保额
//    tLPEdorAppSchema.setChgGetAmnt(request.getParameter("ChgGetAmnt")); //变动的领取保额
//    tLPEdorAppSchema.setGetMoney(request.getParameter("GetMoney")); //补/退费金额
//    tLPEdorAppSchema.setGetInterest(request.getParameter("GetInterest")); //补/退费利息
    tLPEdorAppSchema.setEdorAppDate(request.getParameter("EdorAppDate")); //批改柜面受理日期
//    tLPEdorAppSchema.setEdorState(request.getParameter("EdorState")); //批改状态
//    tLPEdorAppSchema.setBankCode(request.getParameter("BankCode")); //银行编码
//    tLPEdorAppSchema.setBankAccNo(request.getParameter("BankAccNo")); //银行帐号
//    tLPEdorAppSchema.setAccName(request.getParameter("AccName")); //银行帐户名


  //个人批改信息
  LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
  LPEdorMainSet tLPEdorMainSet = new LPEdorMainSet();
  
  
 
 //   tLPEdorMainSchema.setEdorNo(request.getParameter("EdorNo")); //批单号
    tLPEdorMainSchema.setContNo(request.getParameter("ContNo")); //合同号码
    tLPEdorMainSchema.setEdorAppNo(request.getParameter("EdorNo")); //批改申请号
    tLPEdorMainSchema.setManageCom(request.getParameter("ManageCom")); //管理机构
    tLPEdorMainSchema.setEdorAppDate(request.getParameter("EdorAppDate")); //批改柜面受理日期
    tLPEdorMainSchema.setEdorValiDate(request.getParameter("EdorValiDate")); //批改生效日期
    
    tLPEdorMainSet.add(tLPEdorMainSchema);
  
  //个人保单信息
  LCContSchema tLCContSchema = new LCContSchema();
  LCContSet tLCContSet = new LCContSet();

  PEdorMainUI tPEdorMainUI   = new PEdorMainUI();
 	CheckFieldUI tCheckFieldUI = new CheckFieldUI();

  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
  
  System.out.println("-----"+tG.Operator);
  System.out.println("-----"+tG.ManageCom);
  
  //输出参数
  CErrors tError = null;   
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String transact = "";
  String Result = "";
  String strEdorType = "";
  
  transact = request.getParameter("fmtransact");
  System.out.println("------transact:"+transact);
 
  
	
	////针对可以减少为0 的保全项目，设置是否修改的标记。
	//strEdorType = request.getParameter("EdorType");
	//if(strEdorType.equals("BC") || strEdorType.equals("SC"))
	//{
	//   System.out.println("==> Make Flag of Chenged,Flag=" + strEdorType + "NOC0");   
	//   tLPEdorMainSchema.setCalCode(strEdorType+"NOC0");
	//}
	//
	//tLPEdorMainSet.add(tLPEdorMainSchema);

    try
    {  
  	    VData tVCheckData = new VData();   
        
        tVCheckData.addElement(tG);
  	    tVCheckData.addElement(tLPEdorMainSchema);
  	    tVCheckData.addElement("VERIFY||BEGIN");
  	    tVCheckData.addElement("PEDORINPUT#EDORTYPE");
  	    
   	    //if (tCheckFieldUI.submitData(tVCheckData,"CHECK||FIELD")) 
   	    //{ 	    
   	    //    if (tCheckFieldUI.getResult()!=null&&tCheckFieldUI.getResult().size()>0) 
   	    //    {
       	//	    for (int i=1;i<=tCheckFieldUI.getResult().size();i++) 
       	//	    {
       	//	        Result = Result + (String)tCheckFieldUI.getResult().get(i)+"\n";
       	//	    }
   	    //    }
   						
  	        VData tVData = new VData();  
  	    
  	        //保存个人保单信息(保全)
  	        tVData.addElement(tLPEdorAppSchema);			 
  	        tVData.addElement(tLPEdorMainSet);
  	        tVData.addElement(tG);
  	    
  	        if (tPEdorMainUI.submitData(tVData,transact)) 
  	        {		
        	    if (transact.equals("INSERT||EDOR")) 
        	    {        			    	
        	        tVData.clear();
        	        tVData = tPEdorMainUI.getResult();
        	                		    	
        	        LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema(); 
        	        LPEdorMainSet mLPEdorMainSet=new LPEdorMainSet();
        	        mLPEdorMainSet=(LPEdorMainSet)tVData.getObjectByObjectName("LPEdorMainSet",0);
        	        mLPEdorMainSchema=mLPEdorMainSet.get(1);
        	        %>
        	        <SCRIPT language="javascript">
        	    	    parent.fraInterface.fm.EdorNo.value="<%=mLPEdorMainSchema.getEdorNo()%>";
                        parent.fraInterface.fm.EdorAcceptNo.value="<%=mLPEdorMainSchema.getEdorAcceptNo()%>";
        	        </SCRIPT>
        	        <%	
        	    }
            }  
     //   }
    } 
    catch(Exception ex) 
    {
      Content = transact+"失败，原因是:" + ex.toString();
      FlagStr = "Fail";
    }			
  
  //如果在Catch中发现异常，则不从错误类中提取错误信息
 	tError = tCheckFieldUI.mErrors;
 	
 	if (tError.getErrType().equals(CError.TYPE_NONEERR))
 	{
 		tError = tPEdorMainUI.mErrors;
 	}
    if (tError.getErrType().equals(CError.TYPE_NONEERR))
    {                          
        Content = " 保存成功" + ":"+ Result.trim();
        FlagStr = "Success";
    }
  
    if (FlagStr.equals(""))
    {
        String ErrorContent = tError.getErrContent();  
        
        if (tError.getErrType().equals(CError.TYPE_ALLOW)) 
        {
            Content = " 保存成功，但是：" + ErrorContent;
            FlagStr = "Success";
        }
        else 
        {
        	Content = "保存失败，原因是：" + ErrorContent;
        	FlagStr = "Fail";
        }
    
        Content = PubFun.changForHTML(Content);
    }

  //添加各种预处理
  //System.out.println("End here "+tError.getErrContent());
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>

