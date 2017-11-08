var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass(); 
var mySql = new SqlClass();
// 保单查询按钮
function QueryCont()
{
	//校验必录项必须录入
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


//校验必录项不能为空
function vertifyInput()
{	 
	  //如果录入激活日期,则必须录入起始和终止日期,不能只录入一个
	  if(document.all('FamilyIDNo').value==null||document.all('FamilyIDNo').value=="")
	  {	
	  	 	alert("请输入家庭单号");
	  	 	return false;
	  	 
	  }	  
}


// 暂收查询按钮
function QueryFee()
{
	//校验必录项必须录入
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
		alert( "请先选择一条合同记录" );
		return false;
	}
	else
	{

		var cContNo =  FamilyCardInfoGrid.getRowColData(tSel - 1,2);
		if (cContNo == "")
		return;
		OpenWindowNew("../sys/PolDetailQueryMain.jsp?ContNo=" + cContNo +"&IsCancelPolFlag=0","保单明细","left");
	}
	
}
