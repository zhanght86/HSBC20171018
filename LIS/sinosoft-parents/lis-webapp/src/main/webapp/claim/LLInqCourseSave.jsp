<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLInqCourseSave.jsp
//程序功能：用于提交调查过程信息
//创建日期：2005-6-22
//更新记录：  更新人:yuejw    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.claim.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
    //输出参数
	CErrors tError = null;
    String FlagStr = "Fail";
    String Content = "";
	GlobalInput tG = new GlobalInput(); 
	tG=(GlobalInput)session.getValue("GI");
  	if(tG == null) 
  	{
		  loggerDebug("LLInqCourseSave","session has expired");
		return;
	}
    //接收信息
    loggerDebug("LLInqCourseSave","Start Save...");	
    //调查过程表 
	LLInqCourseSchema tLLInqCourseSchema = new LLInqCourseSchema(); 
	tLLInqCourseSchema.setClmNo(request.getParameter("ClmNo")); //赔案号
	tLLInqCourseSchema.setInqNo(request.getParameter("InqNo")); //调查序号
	//tLLInqCourseSchema.setCouNo("");	//过程序号由系统生成
	tLLInqCourseSchema.setInqDate(request.getParameter("InqDate")); //调查日期
	tLLInqCourseSchema.setInqMode(request.getParameter("InqMode")); //调查方式
	tLLInqCourseSchema.setInqSite(request.getParameter("InqSite")); //调查地点
	tLLInqCourseSchema.setInqByPer(request.getParameter("InqByPer")); //被调查人
	tLLInqCourseSchema.setInqDept(request.getParameter("InqDept")); //调查机构
	tLLInqCourseSchema.setInqPer1(request.getParameter("InqPer1")); //第一调查人
	tLLInqCourseSchema.setInqPer2(request.getParameter("InqPer2")); //第二调查人
	tLLInqCourseSchema.setInqCourse(request.getParameter("InqCourse")); //调查过程
	tLLInqCourseSchema.setRemark(request.getParameter("InqCourseRemark")); //调查过程备注	 

    //接收MulLine表格中数据集合---------调查过程单证信息
    LLInqCertificateSet tLLInqCertificateSet = new LLInqCertificateSet();
    String tGridNo[] = request.getParameterValues("LLInqCertificateGridNo");  //得到序号列的所有值
    if(tGridNo==null)
    {
    	loggerDebug("LLInqCourseSave","调查过程单证数目Count====0");
    }	
    else
    {
    	int Count = tGridNo.length; //得到接受到的记录数
    	loggerDebug("LLInqCourseSave","调查过程单证数目Count====0="+Count);
    	String tGrid1[] = request.getParameterValues("LLInqCertificateGrid1"); //得到第1列，单证类型
	    String tGrid2[] = request.getParameterValues("LLInqCertificateGrid2"); //得到第2列，单证名称
	    String tGrid3[] = request.getParameterValues("LLInqCertificateGrid3"); //得到第3列，原件标志
	    String tGrid4[] = request.getParameterValues("LLInqCertificateGrid4"); //得到第4列，张数
	    String tGrid5[] = request.getParameterValues("LLInqCertificateGrid5"); //得到第5列，备注信息
        for(int index=0;index < Count;index++)
		{
			LLInqCertificateSchema tLLInqCertificateSchema=new LLInqCertificateSchema();
			tLLInqCertificateSchema.setClmNo(request.getParameter("ClmNo"));/** 赔案号 */
			tLLInqCertificateSchema.setInqNo(request.getParameter("InqNo"));/** 调查序号 */
			//tLLInqCertificateSchema.setCouNo("");/** 过程序号---过程序号由系统生成 */
			//tLLInqCertificateSchema.setCerNo("");/** 单证序号---单证序号由系统生成*/
			tLLInqCertificateSchema.setCerType(tGrid1[index]);/** 单证类型 */
			tLLInqCertificateSchema.setCerName(tGrid2[index]);/** 单证名称 */
			tLLInqCertificateSchema.setOriFlag(tGrid3[index]);/** 原件标志 */
			tLLInqCertificateSchema.setCerCount(tGrid4[index]);/** 张数 */
			tLLInqCertificateSchema.setRemark(tGrid5[index]);/** 备注 */
			tLLInqCertificateSet.add(tLLInqCertificateSchema);
		}
    }

    
    // 准备传输数据 VData
    VData tVData = new VData();	
    tVData.add(tG);
    tVData.add(tLLInqCourseSchema);
    tVData.add(tLLInqCertificateSet);
    
    // 数据传输
//    LLInqCourseUI tLLInqCourseUI   = new LLInqCourseUI();
//	if (!tLLInqCourseUI.submitData(tVData,""))
//	{
//     	Content = " 保存失败，原因是: " + tLLInqCourseUI.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//	} 
	String busiName="LLInqCourseUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if(!tBusinessDelegate.submitData(tVData,"",busiName))
	{    
	    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	    { 
			Content = "保存失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		else
		{
			Content = "保存失败";
			FlagStr = "Fail";				
		}
	}

   //如果在Catch中发现异常，则不从错误类中提取错误信息
   if (FlagStr == "Fail")
   {
    	//tError = tLLInqCourseUI.mErrors;
    	tError = tBusinessDelegate.getCErrors();
    	if (!tError.needDealError())
    	{                          
    		Content = " 保存成功! ";
    		FlagStr = "Succ";
    	}
    	else                                                                           
    	{
    		Content = " 保存失败，原因是:" + tError.getFirstError();
    		FlagStr = "Fail";
    	}
    }
    //添加各种预处理
    loggerDebug("LLInqCourseSave","Content:"+Content);
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

