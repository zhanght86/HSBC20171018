//�������ƣ�PDRiskInsuAcc.js
//�����ܣ������˻�����
//�������ڣ�2009-3-12
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var showInfo;
//����sql�����ļ�
var tResourceName = "productdef.PDRiskInsuAccInputSql";
function checkNullable()
{
  var GridObject = Mulline10Grid;
  var rowCount = GridObject.mulLineCount;
  
  for(var i = 0; i < rowCount; i++)
  {
    var PropName = GridObject.getRowColData(i,1);
    var PropValue = GridObject.getRowColData(i,4);
    
  	if(PropName.indexOf("[*]") > -1)
  	{
  		if(PropValue == null || PropValue == "")
  		{
  			PropName = PropName.substring(3,PropName.length);
  		
  			myAlert("\"" + PropName + ""+"\"����Ϊ��"+"");
  			
  			return false;
  		}
  	}
  }
  
  return true;
}

function submitForm(){
	if(fm.all("IsReadOnly").value == "1"){
  		myAlert(""+"����Ȩִ�д˲���"+"");
  		return;
	}
	var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
	fm.submit();
}

function afterSubmit( FlagStr, content )
{
  showInfo.close();

  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    initForm();    
  } 
}

function setGuarInteRate()
{
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"��ѡ��һ�������˻���"+"");
		return;
	}
	//alert(Mulline9Grid.getRowColData(selNo-1,2));
	//alert("here");
	window.open("PDAccGuratRateMain.jsp?riskcode=" + fm.all("RiskCode").value+"&insuaccno=" + Mulline9Grid.getRowColData(selNo-1,2));
}

var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 
function save(){
	if(!checkNullable()){
		return false;
	}
	//-------- add by jucy
	var checkRiskToAccNo = Mulline10Grid.getRowColData(1, 4);
	var strSQL = "";
	var mySql=new SqlClass();
	var sqlid = "PDRiskInsuAccInputSql3";
	mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(checkRiskToAccNo);//ָ������Ĳ���
	arr = easyExecSql( mySql.getString() );
	if(arr!=null&&arr!=""){
		if(!confirm(""+"�����˻����룺"+""+arr[0][0]+""+"���Ѵ��ڣ������������޸ı����˻���Ϣ���Ƿ�ֻ���Ӳ�Ʒ�뱣���˻��Ķ�Ӧ��ϵ��"+"")){
	     	return;
		}
	}
	
	var accType=Mulline10Grid.getRowColData(3, 4);
	if("007"==accType){
		var mySql2=new SqlClass();
		var sql = "PDRiskInsuAccInputSql4";
		mySql2.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sql);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(checkRiskToAccNo);//ָ������Ĳ���
		mySql2.addSubPara(accType);
		arr = easyExecSql( mySql2.getString() );
		if(arr==null||arr==""){
			myAlert(""+"��������û�еĻ����˻���"+"");
			return false;
		}
	}
	//-------- end
	fm.all("operator").value="save";
	
	submitForm();
}
function update(){
	
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null ){
		myAlert(""+"��ѡ��һ����¼��"+"");
		return;
	}
	//-------- add by jucy
	if(!checkNullable()){
		return false;
	}
	var checkRiskToAccNo = Mulline10Grid.getRowColData(1, 4);
	var checkAccNo = Mulline9Grid.getRowColData(selNo-1, 2);
	if(checkRiskToAccNo != checkAccNo){
		myAlert(""+"�����˻�����Ϊ��"+""+checkAccNo+""+"��ϵͳֻ�����޸ĳ������˻���������������˻�������Ϣ��"+"");
		Mulline10Grid.setRowColData(1, 4,checkAccNo);
		return false;
	}
	var strSQL = "";
	var mySql=new SqlClass();
	var sqlid = "PDRiskInsuAccInputSql3";
	mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(checkRiskToAccNo);//ָ������Ĳ���
	arr = easyExecSql( mySql.getString() );
	if(arr!=null&&arr!=""){
		var checkAccType = arr[0][1];
		if(checkAccType=='007'){
			if(!confirm(""+"�����˻����룺"+""+arr[0][0]+""+"���˻�����Ϊ��007"+"-"+"�����˻������޸Ļ����˻�������Ϣ�����ܻ�Ӱ��û�����ϵͳ�еĴ����Ƿ�����޸ģ�"+"")){
		     	return false;
			};
		}
	}
	//-------- end
	
	fm.all("operator").value="update";
	submitForm();
}
function del(){
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null ){
		myAlert(""+"��ѡ��һ����¼��"+"");
		return;
	}
	//-------- add by jucy
	if(!checkNullable()){
		return false;
	}
	var checkRiskToAccNo = Mulline10Grid.getRowColData(1, 4);
	var strSQL = "";
	var mySql=new SqlClass();
	var sqlid = "PDRiskInsuAccInputSql3";
	mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(checkRiskToAccNo);//ָ������Ĳ���
	arr = easyExecSql( mySql.getString() );
	if(arr!=null&&arr!=""){
		var checkAccType = arr[0][1];
		if(checkAccType=='007'){
			if(!confirm(""+"�����˻����룺"+""+arr[0][0]+""+"���˻�����Ϊ��007"+"-"+"�����˻����˲�����ɾ���˻����˻���ȫ����Ϣ���Ƿ�����˲�����"+"")){
		     	return false;
			};
		}
	}
	//-------- end
	
	fm.all("operator").value="del";
	submitForm();
}
//-------- add by jucy
function delrel(){
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null ){
		myAlert(""+"��ѡ��һ����¼��"+"");
		return;
	}
	if(!checkNullable()){
		return false;
	}
	var checkAccNo = Mulline9Grid.getRowColData(selNo-1, 2);
	if(!confirm(""+"��ǰ������ɾ�����ִ��룺"+""+fm.all("RiskCode").value+""+"���뱣���˻����룺"+""+checkAccNo+""+"�Ĺ�����ϵ���Ƿ�����˲�����"+"")){
     	return false;
	};
	fm.all("operator").value="delrel";
	submitForm();
	
}
//-------- end



function returnParent()
{
  top.opener.focus();
	top.close();
}

function queryMulline9Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDRiskInsuAccInputSql2";
	 mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara(fm.all("RiskCode").value);//ָ������Ĳ���
   strSQL = mySql.getString();
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function queryMulline10Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDRiskInsuAccInputSql1";
	 mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara("0");//ָ������Ĳ���
   strSQL = mySql.getString();
   Mulline10GridTurnPage.pageLineNum  = 3215;
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}
function isshowbutton()
{   var value=getQueryState1();
	if(value=='0'||value==0){
	//document.getElementById('savabuttonid').style.display = 'none';
	document.getElementById('savabutton1').disabled=true;
	document.getElementById('savabutton2').disabled=true;
	document.getElementById('savabutton3').disabled=true;
	document.getElementById('savabutton4').disabled=true;
	//document.getElementById('savabutton4').disabled=true;
	}

}