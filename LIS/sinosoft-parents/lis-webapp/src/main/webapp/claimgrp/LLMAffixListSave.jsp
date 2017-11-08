<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLMAffixListSave.jsp
//程序功能：报案单证信息保存
//创建日期：2005-05-25 12:06
//创建人  ：潘志豪
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.tb.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="java.util.*"%>

<%
    //准备通用参数
    CErrors tError = null;
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    String tOperate = request.getParameter("Operate");
    if(tG == null)
    {
        loggerDebug("LLMAffixListSave","登录信息没有获取!!!");
        return;
     } else if (tOperate == null || tOperate == "") {
        loggerDebug("LLMAffixListSave","没有获取操作符!!!");
        return;
    }

    String tCaseNo=request.getParameter("CaseNo");
    String tCustomerNo=request.getParameter("whoNo");
    int tCols=Integer.parseInt(request.getParameter("cols"));
    String[] tAffixGridNo=request.getParameterValues("AffixGridNo");
    int tRows=tAffixGridNo.length;

    Vector tRowsChk=new Vector();
    String tChk[] = request.getParameterValues("InpAffixGridChk");
    if (tChk!=null&&tChk.length>0){
      for(int index=0;index<tChk.length;index++){
        if(tChk[index].equals("1")){
	  tRowsChk.add(new Integer(index));
    	}
      }

      loggerDebug("LLMAffixListSave","Cols:"+tCols+"||Rows:"+tRowsChk.size());

      //得到前台选中条目的相关数据
      String[] tAffixGridColValue=new String[tCols];
      String[][] tAffixGridValues=new String[tRows][tCols];
      for (int i=1;i<=tCols;i++){
        tAffixGridColValue=request.getParameterValues("AffixGrid"+i);
        for(int j=0;j<tRows;j++){
      	  if (tRowsChk.indexOf(new Integer(j))!=-1){
            tAffixGridValues[j][i-1]=StrTool.unicodeToGBK(tAffixGridColValue[j]);
            loggerDebug("LLMAffixListSave","Col:"+i+";Row:"+j+";Value:"+StrTool.unicodeToGBK(tAffixGridColValue[j]));
      	  }
        }
      }
    }

    //准备数据容器信息
    TransferData tTransferData = new TransferData();

    LLReportAffixSet tLLReportAffixSet=new LLReportAffixSet();

    LLAffixSet tLLAffixSet=new LLAffixSet();

    int tRow=0;
    //LLFeeMainSchema tLLFeeMainSchema = new LLFeeMainSchema();
    //LLCaseReceiptSchema tLLCaseReceiptSchema = new LLCaseReceiptSchema();

    //单证信息

    if (tOperate.equals("Rpt||INSERT"))		//报案阶段生成单证
    {
	//准备后台数据
	for(int i=0;i<tRowsChk.size();i++){
	   LLReportAffixSchema tLLReportAffixSchema=new LLReportAffixSchema();
          tLLReportAffixSchema.setRptNo(tCaseNo); //赔案号
          tLLReportAffixSchema.setCustomerNo(tCustomerNo); //客户号
          tRow=((Integer)tRowsChk.get(i)).intValue();
          tLLReportAffixSchema.setAffixNo(tAffixGridValues[tRow][8]); //单证号码
          tLLReportAffixSchema.setAffixCode(tAffixGridValues[tRow][0]); //单证代码
          tLLReportAffixSchema.setNeedFlag(tAffixGridValues[tRow][3]); //是否必需标志
          tLLReportAffixSchema.setProperty(tAffixGridValues[tRow][6]); //单证属性标志
          tLLReportAffixSchema.setAffixName(tAffixGridValues[tRow][1]); //单证名称
          tLLReportAffixSchema.setReadyCount(tAffixGridValues[tRow][4]); //单证件数
          tLLReportAffixSet.add(tLLReportAffixSchema);
	}
	tTransferData.setNameAndValue("RptNo",tCaseNo);

    }
    else if (tOperate.equals("Rgt||UPDATE"))	//立案阶段单证回销
    {
	//准备后台数据
	for(int i=0;i<tRows;i++){
	   LLAffixSchema tLLAffixSchema=new LLAffixSchema();
          tLLAffixSchema.setRgtNo(tCaseNo); //赔案号
          tLLAffixSchema.setCustomerNo(tCustomerNo); //客户号
          tLLAffixSchema.setAffixNo(tAffixGridValues[i][8]); //单证号码
          tLLAffixSchema.setAffixCode(tAffixGridValues[i][0]); //单证代码
          tLLAffixSchema.setSubFlag(tAffixGridValues[i][2]); //是否已提交标志
          tLLAffixSchema.setReturnFlag(tAffixGridValues[i][7]); //是否退还原件标志
          /*tLLAffixSchema.setAffixName(tAffixGridValues[tRow][1]); //单证名称
          tLLAffixSchema.setCount(tAffixGridValues[tRow][3]); //单证件数*/
          tLLAffixSet.add(tLLAffixSchema);
	}
	tTransferData.setNameAndValue("RgtNo",tCaseNo);
    }
    else if (tOperate.equals("RgtAdd||INSERT"))	//立案阶段补充单证
    {
	//准备后台数据
	for(int i=0;i<tRowsChk.size();i++){
	   LLAffixSchema tLLAffixSchema=new LLAffixSchema();
          tLLAffixSchema.setRptNo(tCaseNo); //赔案号
          tLLAffixSchema.setCustomerNo(tCustomerNo); //客户号
          tRow=((Integer)tRowsChk.get(i)).intValue();
          tLLAffixSchema.setAffixNo(tAffixGridValues[tRow][8]); //单证号码
          tLLAffixSchema.setAffixCode(tAffixGridValues[tRow][0]); //单证代码
          tLLAffixSchema.setNeedFlag(tAffixGridValues[tRow][3]); //是否必需标志
          tLLAffixSchema.setProperty(tAffixGridValues[tRow][6]); //单证属性标志
          tLLAffixSchema.setAffixName(tAffixGridValues[tRow][1]); //单证名称
          tLLAffixSchema.setReadyCount(tAffixGridValues[tRow][4]); //单证件数
          tLLAffixSchema.setSubFlag(tAffixGridValues[i][2]); //是否已提交标志
          tLLAffixSchema.setReturnFlag(tAffixGridValues[i][7]); //是否退还原件标志
          tLLAffixSet.add(tLLAffixSchema);
	}
	tTransferData.setNameAndValue("RgtNo",tCaseNo);
    }

    tTransferData.setNameAndValue("CustomerNo",tCustomerNo);
//    String tTest1 = request.getParameter("ClinicMedFeeTypeName");
//    String tTest2 = new String(tTest1.getBytes("ISO-8859-1"),"GB2312");
    loggerDebug("LLMAffixListSave","LLMAffixListSave.jsp测试--"+tOperate);


    try
    {
        // 准备传输数据 VData
        VData tVData = new VData();

        tVData.add( tG );
        tVData.add( tTransferData );
	if (tOperate.equals("Rpt||INSERT"))		//报案阶段生成单证
        {
          tVData.add(tLLReportAffixSet);
        }
        else if (tOperate.equals("Rgt||UPDATE"))	//立案阶段单证回销
        {
          tVData.add(tLLAffixSet);
        }
	else if (tOperate.equals("RgtAdd||INSERT"))	//立案阶段补充单证
	{

	}
        LLMAffixListUI tLLMAffixListUI = new LLMAffixListUI();
       if (tLLMAffixListUI.submitData(tVData,tOperate) == false)
        {
            int n = tLLMAffixListUI.mErrors.getErrorCount();
            for (int i = 0; i < n; i++)
            {
                Content = Content + "单证信息保存错误，原因是: " + tLLMAffixListUI.mErrors.getError(i).errorMessage;
                FlagStr = "FAIL";
            }
        } else {
            Content = " 保存成功! ";
            FlagStr = "SUCC";
        }

    }
    catch(Exception e)
    {
        e.printStackTrace();
        Content = Content.trim()+".提示：异常终止!";
    }

//    loggerDebug("LLMAffixListSave","LLMedicalFeeInpSave测试--"+FlagStr);

%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
