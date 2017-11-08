
<%
//程序名称：CardProposalSignSave.jsp
//程序功能：
//创建日期：2006-02-09 14:10:36
//创建人  ：CR
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.text.*"%>
<%@page import="com.sinosoft.service.*" %>
<%! 
/**申请控制并发*/
private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
private boolean lockNo(String tPrtNo) {
	if (!mPubLock.lock(tPrtNo, "LC0033")) {
		return false;
	}
	return true;
}
%>
<%
	String FlagStr = "Succ";
	String Content = "";
	loggerDebug("CardProposalSignSave","ok");
	VData tVData = new VData();
	
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
	
	String mCurrentTime = PubFun.getCurrentTime();
    String mCurrentDate = PubFun.getCurrentDate();
	
	// 投保单列表
	LCContSet mLCContSet = new LCContSet();
	LCContSchema tLCContSchema = new LCContSchema();
	String tChk[] = request.getParameterValues("InpCardPolGridChk");
	String tProposalContNo[]= request.getParameterValues("CardPolGrid1"); //投保单号
	String tContCardNo[]= request.getParameterValues("CardPolGrid2"); //卡号
	try{
	for(int i=0;i<tChk.length;i++){
		tLCContSchema =new LCContSchema();
		if(tChk[i].equals("1")){
			if (!lockNo(tContCardNo[i])) {
				loggerDebug("CardProposalSignSave","印刷号：["+tContCardNo[i]+"]锁定号码失败!");
				Content =Content+"印刷号：["+tContCardNo[i]+"]锁定号码失败!,请确认该单是否正在进行其他操作或是由于操作中断而锁定。";
				//this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
				//tLockFlag = false;
				//mPubLock.unLock();
				continue;
			}
			tLCContSchema.setContNo(tContCardNo[i]);
			mLCContSet.add(tLCContSchema);
		}
	}
	if(mLCContSet.size()>0){
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("CurrentDate",mCurrentDate);
		tTransferData.setNameAndValue("CurrentTime",mCurrentTime);
		tTransferData.setNameAndValue("mark", "card");
		tTransferData.setNameAndValue("CardContNo",request.getParameter("ContCardNo"));
		
		tVData.add(tG);
		tVData.add(mLCContSet);
		tVData.add(tTransferData);
		//LCContSignBL tLCContSignBL = new LCContSignBL();
		String busiName="tbLCContSignBL";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		String flag = "sign";
		if (tBusinessDelegate.submitData(tVData, flag,busiName) == false) 
		{
			Content = " 操作失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		else 
		{
			Content = " 操作成功! ";
			FlagStr = "Succ";
			loggerDebug("CardProposalSignSave","操作成功。。。。。。。。。。。");
		}
		//签单失败后,保存日志信息
		if(FlagStr.equals("Fail"))
		{
			VData mVData = new VData();
			mVData.add(tG);
			mVData.add(mLCContSet);
			mVData.add(Content);
			LCSignLogBL tLCSignLogBL = new LCSignLogBL();
			if(tLCSignLogBL.submitData(mVData,""))
			{
				loggerDebug("CardProposalSignSave","日志保存成功!!");
			}
		}
	}else{
		FlagStr="Fail";
	}
	}catch(Exception ex){
		ex.getStackTrace();
	}finally{
		mPubLock.unLock();
	}
%>                
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
