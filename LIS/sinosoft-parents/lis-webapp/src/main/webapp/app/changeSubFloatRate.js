//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}

// ���ݷ��ظ�����
function returnParent()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			top.opener.afterQuery( arrReturn );
		}
		catch(ex)
		{
			alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
		}
		top.close();
	}
}

// ��ѯ��ť
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
function initQuery() {
	// ��ʼ�����
	initPolGrid();
	
	// ��дSQL���
//	var strSql = "select ProposalNo,PrtNo,RiskCode,FloatRate,AppntName,InsuredName from LCPol where "
//    				 + " PrtNo='" + prtNo + "'"
//    				 + " and PolNo<>MainPolNo";
	
	var sqlid1="changeSubFloatRateSql0";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.changeSubFloatRateSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(prtNo);//ָ������Ĳ���
	var strSql=mySql1.getString();
  
  //alert(strSql);
	turnPage.queryModal(strSql, PolGrid);
}

//�޸Ĳ���
var mSwitch = parent.VD.gVSwitch;
function modifyClick() { 
  if (PolGrid.getSelNo()) { 
  	var cPolNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 1); 	
  	mSwitch.deleteVar( "PolNo" );
  	mSwitch.addVar( "PolNo", "", cPolNo );
  	
  	//type="ChangePlan";
    var prtNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 2);
    window.open("../app/ProposalEasyScan.jsp?LoadFlag=10&Type="+type+"&prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");
  }
  else {
    alert("����ѡ��һ��������Ϣ��"); 
  }
}



