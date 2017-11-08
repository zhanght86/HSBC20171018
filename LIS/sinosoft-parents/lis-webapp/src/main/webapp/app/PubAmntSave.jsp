<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：PubAmntSave.jsp
//程序功能：公共保额保存
//创建日期：2005-10-10 20:05:52
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
//      
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
//接收信息，并作校验处理。
//输入参数
//LCContSchema tLCContSchema = new LCContSchema();
LCPolSchema tLCPolSchema = new LCPolSchema();
LCDutySet tLCDutySet = new LCDutySet();
LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
LCInsuredSchema tmLCInsuredSchema =new LCInsuredSchema();
LCInsuredSet tLCInsuredSet = new LCInsuredSet();
LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
TransferData tTransferData = new TransferData(); 
//ParseGuideIn tParseGuideIn   = new ParseGuideIn();
String busiName="tbParseGuideIn";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//输出参数
String FlagStr = "";
String Content = "";

GlobalInput tGI = new GlobalInput(); //repair:
tGI=(GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp
loggerDebug("PubAmntSave","tGI"+tGI);
if(tGI==null)
{
    loggerDebug("PubAmntSave","页面失效,请重新登陆");   
    FlagStr = "Fail";        
    Content = "页面失效,请重新登陆";  
}
else //页面有效
{
    CErrors tError = null;
    String tBmCert = "";
    loggerDebug("PubAmntSave","开始保存");

    /*        
      String tLimit="";
      String CustomerNo="";
    */   
    int lineCount = 0;
    String arrCount[] = request.getParameterValues("PubAmntGridNo");
    if (arrCount != null)
    {
    	String tRadio[] = request.getParameterValues("InpPubAmntGridSel");	//Radio选项       
        String tRiskCode[] = request.getParameterValues("PubAmntGrid6");  //险种编码   
        String tPubAmntName[] = request.getParameterValues("PubAmntGrid3"); //公共保额名称
        String tDutyCode[] = request.getParameterValues("PubAmntGrid7"); //责任编码
        String tAmnt[] = request.getParameterValues("PubAmntGrid4");  //保额
        String tPrem[] = request.getParameterValues("PubAmntGrid5");  //保费
                
        lineCount = arrCount.length; //行数

    	//选择单选框被选中的数据提交
    	for(int i=0;i<lineCount;i++)
    	{
    		if(tRadio[i].equals("1"))
    		{
    		    loggerDebug("PubAmntSave","险种编码 "+tRiskCode[i]);
                loggerDebug("PubAmntSave","公共保额名称 "+tPubAmntName[i]);
                loggerDebug("PubAmntSave","责任编码 "+tDutyCode[i]);
                loggerDebug("PubAmntSave","保费 "+tPrem[i]);
                loggerDebug("PubAmntSave","保额 "+tAmnt[i]);
                        
    		    tLCGrpContSchema.setGrpContNo(request.getParameter("GrpContNo"));
                tLCGrpContSchema.setProposalGrpContNo(request.getParameter("GrpContNo"));
                loggerDebug("PubAmntSave","集体投保单 ====" +tLCGrpContSchema.getProposalGrpContNo());
    
                tmLCInsuredSchema.setInsuredNo("");
                tmLCInsuredSchema.setName(tPubAmntName[i]); //公共保额名称
                tmLCInsuredSchema.setRelationToMainInsured("00");
                tmLCInsuredSchema.setContNo("1");
                tmLCInsuredSchema.setPrtNo("1");
                tmLCInsuredSchema.setGrpContNo(request.getParameter("GrpContNo"));
                //tmLCInsuredSchema.setContPlanCode(request.getParameter("ContPlanCode"));
                //tmLCInsuredSchema.setExecuteCom(request.getParameter("ExecuteCom"));
                tmLCInsuredSchema.setInsuredPeoples(0);
                tmLCInsuredSchema.setCreditGrade("2"); //借用 被保人信用等级 字段保存 保单类型标记
                tmLCInsuredSchema.setSex("0");
                tmLCInsuredSchema.setBirthday("1975-01-01");
    
                loggerDebug("PubAmntSave","tmLCInsuredSchema.getPrtNo()===="+tmLCInsuredSchema.getPrtNo());
    
                tLCInsuredSet.add(tmLCInsuredSchema);
    
                tLCPolSchema.setInsuredNo("1");
                tLCPolSchema.setGrpContNo(request.getParameter("GrpContNo"));
                tLCPolSchema.setManageCom(request.getParameter("ManageCom"));
                tLCPolSchema.setInsuredPeoples(0); //被保人人数
                tLCPolSchema.setPolTypeFlag("2"); //保单类型标记
                tLCPolSchema.setGrpContNo(request.getParameter("GrpContNo"));
                tLCPolSchema.setMult(1);                       //份数
                tLCPolSchema.setPrem(tPrem[i]);                 //保费
                tLCPolSchema.setAmnt(tAmnt[i]);                    //保额
                tLCPolSchema.setRiskCode(tRiskCode[i]);
                tLCPolSchema.setMainPolNo("1");
                tLCPolSchema.setContNo("1");
                 
                //LCDutySchema tLCDutySchema = new LCDutySchema(); //若有多个公共保额，这段代码是有用的。需作判断，暂时没有判断。
                //tLCDutySchema.setDutyCode(tDutyCode[i]);
                //tLCDutySet.add(tLCDutySchema);
                //loggerDebug("PubAmntSave","tLCDutySchema.getDutyCode()  "+tLCDutySchema.getDutyCode());
            }
        }
    }
		
    LCBnfSet tLCBnfSet = new LCBnfSet();
    LCBnfSchema tLCBnfSchema = new LCBnfSchema();
    tLCBnfSchema.setRelationToInsured("00");
    tLCBnfSet.add(tLCBnfSchema);
     
    LCInsuredRelatedSet tLCInsuredRelatedSet=new LCInsuredRelatedSet();
    
    tTransferData.setNameAndValue("SavePolType","0"); //保全保存标记，默认为0，标识非保全  
    //tTransferData.setNameAndValue("FamilyType","0"); //家庭单标记 
    tTransferData.setNameAndValue("ContType","2"); //团单，个人单标记
    tTransferData.setNameAndValue("PolTypeFlag","2"); //无名单标记
    tTransferData.setNameAndValue("InsuredPeoples",0); //被保险人人数
    tTransferData.setNameAndValue("PubAmntFlag","Y"); //公共保额标记
    loggerDebug("PubAmntSave","------PubAmntFlag-----"+tTransferData.getValueByName("PubAmntFlag"));

    try
    {
        // 准备传输数据 VData
        VData tVData = new VData();
        tVData.add(tTransferData);
        tVData.add(tGI);
        tVData.add(tLCGrpContSchema);
        tVData.add(tLCInsuredSet);
        tVData.add(tLCPolSchema);
        tVData.add(tLCBnfSet);
        tVData.add(tLCDutySet);
        tVData.addElement(tLCInsuredRelatedSet);
          
         //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
        //if ( tParseGuideIn.submitData2(tVData,""))
        if ( tBusinessDelegate.submitData(tVData,"",busiName))
        {
	    	    loggerDebug("PubAmntSave","Start to save PubAmnt **************");
	    	    tVData.clear();
	    	    tVData = tBusinessDelegate.getResult();
        }
	}
		            
    catch(Exception ex)
    {
        Content = "保存失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    }
  

    //如果在Catch中发现异常，则不从错误类中提取错误信息
    if (FlagStr=="")
    {
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
    loggerDebug("PubAmntSave","FlagStr:"+FlagStr+"Content:"+Content);
  
} //页面有效区
%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterAmntSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

