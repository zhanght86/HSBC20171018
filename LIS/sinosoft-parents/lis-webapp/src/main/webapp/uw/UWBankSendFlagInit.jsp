<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWBankSendFlagInit.jsp
//�����ܣ������˹��˱�
//�������ڣ�2002-09-24 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%> 
<script language="JavaScript">
function initInpBox()
{ 
	//alert("2.1");
  fm.MissionID.value = tMissionID ;
  //alert("2.2");
	fm.SubMissionID.value = tSubMissionID ;
	//alert("2.3");
	fm.ActivityID.value = tActivityID ;
	//alert("2.4");
	fm.PrtNo.value = tPrtNo ;
	//alert("2");
    //var tSql = "select (case when NewAutoSendBankFlag is not null then NewAutoSendBankFlag else '1' end) from LCCont where ContNo='"+fm.PrtNo.value+"'";
  
    var sqlid1="UWBankSendFlagInitSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("uw.UWBankSendFlagInitSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addPara(fm.PrtNo.value);//ָ������Ĳ���
    strSql=mySql1.getString();		
  
	//alert("tSql: "+tSql);
	var tNewAutoSendBankFlagResult=easyExecSql(strSql);
  //alert("tNewAutoSendBankFlagResult: "+tNewAutoSendBankFlagResult);
  tOldSendBankFlag = tNewAutoSendBankFlagResult[0][0];
  try
  {                                  
    document.all('NewAutoSendBankFlag').value = tNewAutoSendBankFlagResult[0][0];
  }
  catch(ex)
  {
    alert("��UWBankSendFlagInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
  	//alert("1");
		initInpBox();
  }
  catch(re)
  {
    alert("��PManuUWInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  
  }
}
</script>
