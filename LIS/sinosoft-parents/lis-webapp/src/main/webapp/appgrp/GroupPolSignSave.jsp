<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GroupPolSignSave.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��HST
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
   <%@page import="com.sinosoft.workflow.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
	//�������
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";
        String contno="";
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");
	TransferData tTransferData = new TransferData();

  	//������Ϣ
  	// Ͷ�����б�
  LCGrpContSet tLCGrpContSet = new LCGrpContSet(); //yaory
	LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
	String tGrpContNo[] = request.getParameterValues("GrpGrid1");
	String tChk[] = request.getParameterValues("InpGrpGridChk");
	String tMissionID[] = request.getParameterValues("GrpGrid7");
	String tSubMissionID[] = request.getParameterValues("GrpGrid8");
        boolean SignFlag=false;
	boolean flag = false;
	int grouppolCount = tGrpContNo.length;
	for (int i = 0; i < grouppolCount; i++)
	{
		loggerDebug("GroupPolSignSave","��"+"["+(i+1)+"]��ѭ��");
		if( tGrpContNo[i] != null && tChk[i].equals( "1" ))
		{
            loggerDebug("GroupPolSignSave","GrpContNo:"+i+":"+tGrpContNo[i]);
            loggerDebug("GroupPolSignSave","MissionID:"+i+":"+tMissionID[i]);
            loggerDebug("GroupPolSignSave","SubMissionID:"+i+":"+tSubMissionID[i]);
		    tLCGrpContSchema.setPrtNo( tGrpContNo[i] );
		    tTransferData.removeByName("MissionID");
		    tTransferData.removeByName("SubMissionID");
		    tTransferData.setNameAndValue("MissionID",tMissionID[i] );
		    contno=tGrpContNo[i];
	     	tTransferData.setNameAndValue("SubMissionID",tSubMissionID[i] );
	
		// ׼���������� VData
		VData tVData = new VData();
		tLCGrpContSet.clear();
		tLCGrpContSet.add(tLCGrpContSchema);
		//tVData.add( tLCGrpContSchema );
		tVData.add( tLCGrpContSet );
		tVData.add( tTransferData );
		tVData.add( tG );

		// ���ݴ���
		//LCGrpContSignBL tLCGrpContSignBL   = new LCGrpContSignBL();

		//boolean bl = tLCGrpContSignBL.submitData( tVData, "INSERT" );
		//int n = tLCGrpContSignBL.mErrors.getErrorCount();
       // GrpTbWorkFlowUI tTbWorkFlowUI = new GrpTbWorkFlowUI();
		
        String busiName="tbgrpGrpTbWorkFlowUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        
        boolean bl= tBusinessDelegate.submitData( tVData, "0000002006",busiName);
		int n = tBusinessDelegate.getCErrors().getErrorCount();
		if( n == 0 )
	    {
		    if ( bl )
		    {
			SignFlag=true;
	    		Content = " ǩ���ɹ�! ";
		   		FlagStr = "Succ";


	    	}
	    	else
	    	{
	    	   Content = "����Ͷ����ǩ��ʧ��";
	    	   FlagStr ="Succ" ;
	    	}
	    }
	    else
	    {

	    	String strErr = "";
			//for (int i = 0; i < n; i++)
			//{
				//strErr += (i+1) + ": " + tTbWorkFlowUI.mErrors.getError(i).errorMessage + "; ";
				strErr =  tBusinessDelegate.getCErrors().getError(0).errorMessage + "; ";
				//loggerDebug("GroupPolSignSave",tTbWorkFlowUI.mErrors.getError(i).errorMessage );
			//}
			 if ( bl )
			 {
			SignFlag=true;
	    		Content = " ����ǩ���ɹ�,������������Ϣ: " +strErr;
		   		FlagStr = "Succ";
	    	}
	    	else
	    	{
		 		Content = "����Ͷ����ǩ��ʧ�ܣ�ԭ����: " + strErr;
				FlagStr = "Fail";
			}
		} // end of if
	    //���ͱ����������ݴ���
	      ExeSQL ttExeSQL=new ExeSQL();
		    SSRS ttSSRS=new SSRS();
		  String SQLq="select GrpcontNo,donatecontflag from lcgrpcont where prtno='"+tLCGrpContSchema.getPrtNo()+"'";
	      ttSSRS=ttExeSQL.execSQL(SQLq);
	   if(SignFlag&&"1".equals(ttSSRS.GetText(1,2))){
	         String newGrpcontNo=ttSSRS.GetText(1,1);
	         MMap map = new MMap();
	         String sql1="update  LJTempFee set PayMoney=0 where OtherNo='"+newGrpcontNo+"' and OperState='0'";
	         String sql2="update  LJTempFeeClass set PayMoney=0 where OtherNo='"+newGrpcontNo+"' and OperState='0'";
           String Sql3="update LJAPayGrp set SumDuePayMoney=0, SumActuPayMoney=0, FeeMoney=0 where GrpContNo='"+newGrpcontNo+"' and OperState='0'";
	         String Sql4="update LJAPay set SumActuPayMoney=0 where OtherNo='"+newGrpcontNo+"'";
	         String Sql5="update ljapayperson set SumDuePayMoney=0, SumActuPayMoney=0 where grpcontno='"+newGrpcontNo+"'";
           String Sql6="update  lcgrpcont set   dif=0  where grpcontno='"+newGrpcontNo+"' and DonateContflag='1' ";

	         map.put(sql1,"UPDATE");
	         map.put(sql2,"UPDATE");
	         map.put(Sql3,"UPDATE");
	         map.put(Sql4,"UPDATE");
	         map.put(Sql5,"UPDATE");
	         map.put(Sql6,"UPDATE");
           //�ύ����
           VData contData = new VData();
           contData.add(map);
           PubSubmit pubSubmit = new PubSubmit();
          if (!pubSubmit.submitData(contData, "")) {
	         //return false;
	         	Content = " ǩ���ɹ�,�������ͱ���������Ϣ����ʧ�� ";
          }
          //return true;

	   }

		//���б�������

		    MMap tempMMap=new MMap();
		    ExeSQL tExeSQL=new ExeSQL();
		    SSRS tSSRS=new SSRS();
		    String ttsql="select state from lcgrpcont where prtno='"+tLCGrpContSchema.getPrtNo()+"'";
		    tSSRS=tExeSQL.execSQL(ttsql);
		    if(tSSRS.GetText(1,1).equals("1"))
		    {
		    }else{
        tempMMap.put("update lcgrpcont set state='0' where prtno='"+tLCGrpContSchema.getPrtNo()+"'","UPDATE");
        }
        tempMMap.put("delete from lcoutmanagepay where grpcontno='"+tLCGrpContSchema.getPrtNo()+"'","DELETE");
        PubSubmit tempSubmit = new PubSubmit();
        VData temVData = new VData();
        temVData.add(tempMMap);
        if (!tempSubmit.submitData(temVData, null)) {
                CError.buildErr(this, "��������ʧ�ܣ�");

            } //
	 } // end of if
  } //end for()

%>
<html>
<script language="javascript">
  parent.fraInterface.fm.tt.value="<%=contno%>";
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
