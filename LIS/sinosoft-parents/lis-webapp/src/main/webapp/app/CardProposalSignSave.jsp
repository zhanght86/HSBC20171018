
<%
//�������ƣ�CardProposalSignSave.jsp
//�����ܣ�
//�������ڣ�2006-02-09 14:10:36
//������  ��CR
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
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
/**������Ʋ���*/
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
	
	// Ͷ�����б�
	LCContSet mLCContSet = new LCContSet();
	LCContSchema tLCContSchema = new LCContSchema();
	String tChk[] = request.getParameterValues("InpCardPolGridChk");
	String tProposalContNo[]= request.getParameterValues("CardPolGrid1"); //Ͷ������
	String tContCardNo[]= request.getParameterValues("CardPolGrid2"); //����
	try{
	for(int i=0;i<tChk.length;i++){
		tLCContSchema =new LCContSchema();
		if(tChk[i].equals("1")){
			if (!lockNo(tContCardNo[i])) {
				loggerDebug("CardProposalSignSave","ӡˢ�ţ�["+tContCardNo[i]+"]��������ʧ��!");
				Content =Content+"ӡˢ�ţ�["+tContCardNo[i]+"]��������ʧ��!,��ȷ�ϸõ��Ƿ����ڽ������������������ڲ����ж϶�������";
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
			Content = " ����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		else 
		{
			Content = " �����ɹ�! ";
			FlagStr = "Succ";
			loggerDebug("CardProposalSignSave","�����ɹ�����������������������");
		}
		//ǩ��ʧ�ܺ�,������־��Ϣ
		if(FlagStr.equals("Fail"))
		{
			VData mVData = new VData();
			mVData.add(tG);
			mVData.add(mLCContSet);
			mVData.add(Content);
			LCSignLogBL tLCSignLogBL = new LCSignLogBL();
			if(tLCSignLogBL.submitData(mVData,""))
			{
				loggerDebug("CardProposalSignSave","��־����ɹ�!!");
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
