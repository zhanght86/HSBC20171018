var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass(); 
var mySql = new SqlClass();
// ������ѯ��ť
function QueryCont()
{
	//У���¼�����¼��
	if(vertifyInput() == false)
	{
		  return;
	}
	
	initFamilyCardInfo();	
	/*var strSQL = "select familyid,contno,appntno,appntname,insuredno,insuredname from lccont where 1=1 "
             + getWherePart( 'familyid','FamilyIDNo' );		*/
    mySql = new SqlClass();
	mySql.setResourceName("sys.FamilyContQuerySql");
	mySql.setSqlId("FamilyContQuerySql1");
	mySql.addSubPara(fm.FamilyIDNo.value );          	
		turnPage.queryModal(mySql.getString(),FamilyCardInfoGrid);
	
}


//У���¼���Ϊ��
function vertifyInput()
{	 
	  //���¼�뼤������,�����¼����ʼ����ֹ����,����ֻ¼��һ��
	  if(document.all('FamilyIDNo').value==null||document.all('FamilyIDNo').value=="")
	  {	
	  	 	alert("�������ͥ����");
	  	 	return false;
	  	 
	  }	  
}


// ���ղ�ѯ��ť
function QueryFee()
{
	//У���¼�����¼��
	if(vertifyInput() == false)
	{
		  return;
	}

	var cContNo = document.all('FamilyIDNo').value;

	if (cContNo == "")
	return;
	//window.open("../sys/PolTempFeeQueryMain.jsp?ContNo=" + cContNo  + "&IsCancelPolFlag=1"  );
window.open("../sys/PolTempFeeQueryMain.jsp?ContNo=" + cContNo  + "&IsCancelPolFlag=1" ,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes" );
	
}


function PolClick(){
	var tSel = FamilyCardInfoGrid.getSelNo();
	
	if( tSel == null || tSel == 0 ) 
	{
		alert( "����ѡ��һ����ͬ��¼" );
		return false;
	}
	else
	{

		var cContNo =  FamilyCardInfoGrid.getRowColData(tSel - 1,2);
		if (cContNo == "")
		return;
		OpenWindowNew("../sys/PolDetailQueryMain.jsp?ContNo=" + cContNo +"&IsCancelPolFlag=0","������ϸ","left");
	}
	
}
