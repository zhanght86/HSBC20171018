//�������ƣ�PDDutyGetAlive.js
//�����ܣ����θ�������
//�������ڣ�2009-3-16
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var dic;
var turnPage = new turnPageClass();
//����sql�����ļ�
var tResourceName = "productdef.PDDutyGetAliveInputSql";
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function checkNullable()
{
  if(!verifyInput())
  {
  	return false;
  }  
  return true;
}

function submitForm()
{
if(fm.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"����Ȩִ�д˲���"+"");
  	return;
  }
  
	if(!checkNullable())
	{
		return false;
	}
  //var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  lockPage(""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"");
  fm.submit();
}

function afterSubmit( FlagStr, content )
{
  //showInfo.close();
  unLockPage();

  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    //initForm();    
     if(fm.all("operator").value!="del")
    {
    	InputCalCodeDefFace('99');
  	}
  	//else
  	{
  		initForm(); 
  	}
  	if(fm.all("operator").value=="del")
  	{
  		initGetAliveDetail();
  	}
  } 
}
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 
function queryDutyGetLib()
{
  showInfo = window.open("PDDutyGetAliveLibInput.jsp");
}

function afterQuery(getDutyCode2)
{
	fm.all("getDutyCode2").value = getDutyCode2;
	
	var mySql=new SqlClass();
	var sqlid = "PDDutyGetAliveInputSql1";
	mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.all("tableName").value);//ָ������Ĳ���
 var sql = mySql.getString();
 var result = easyExecSql(sql,1,1,1);
 var rowCount = result[0][0];
 
 if(dic == null || dic("GETDUTYCODE2") != fm.all("getDutyCode2").value)
 {
 	var arr = new Array();
 	arr[0] = new Array();
 	arr[0][0] = "GETDUTYCODE2";
 	arr[0][1] = getDutyCode2;
 	
	dic = GetDicOfTableByKeys(fm.all("tableName2").value, arr);
 }

 for(var i = 0; i < rowCount; i++)
 {
 	if(Mulline9Grid.getRowColData(i,2).toUpperCase() != "GETDUTYCODE"  
 		&& (Mulline9Grid.getRowColData(i,2)).toUpperCase() != "GETDUTYKIND")
 	{
		var tContent = dic((Mulline9Grid.getRowColData(i,2)).toUpperCase());
	
		Mulline9Grid.setRowColDataCustomize(i,4,tContent,null);
	}
 }
}


function save()
{
 fm.all("operator").value="save";
 submitForm();
}
function update()
{
 fm.all("operator").value="update";
 submitForm();
}
function del()
{
 fm.all("operator").value="del";
 submitForm();
}
function button239()
{
  	var selNo = Mulline9Grid.getSelNo();
		if(selNo == 0)
		{
			myAlert(""+"��ѡ���㷨���ٵ��"+"");
			return;
		}
		else
		{
			var name = Mulline9Grid.getRowColData(selNo-1,1); 
			if(name.indexOf(""+"�㷨"+"") == -1)
			{
				myAlert(""+"��ѡ���㷨��"+","+"���������趨���㷨"+"");
				return;
			}
		}
		
		//showInfo = window.open("PDAlgoDefiMain.jsp?riskcode=" + fm.all("RiskCode").value+ "&algocode=" + Mulline9Grid.getRowColData(selNo-1,4));
		//tongmeng 2011-03-01 modify
		 var tURL = "../ibrms/IbrmsPDAlgoDefiMain.jsp?riskcode="+document.getElementById("RiskCode").value
             + "&RuleName="+Mulline9Grid.getRowColData(selNo-1,4)
             + "&RuleDes="+Mulline9Grid.getRowColData(selNo-1,4)
             + "&Creator="+mOperator
             + "&RuleStartDate="+mRequDate
             + "&CreateModul=1"
             + "&Business=99&State=0";
   			 showInfo = window.open(tURL);
}

		function afterQueryAlgo(AlgoCode)
		{
			var selNo = Mulline9Grid.getSelNo();
			if(selNo == 0)
			{
				myAlert(""+"ѡ���еĽ��㶪ʧ"+"");
				return;
			}
			
			Mulline9Grid.setRowColDataCustomize(selNo-1,4,AlgoCode);
		}


function button240()
{
  showInfo = window.open("");
}

function queryMulline9Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDDutyGetAliveInputSql2";
	 mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara("sqlid");//ָ������Ĳ���
   strSQL = mySql.getString();
    //tongmeng 2011-07-13 modify
   // 
   var arrInfo = easyExecSql(strSQL);
   if(arrInfo.length>0)
   {
   	for(i=0;i<arrInfo.length;i++)
   	{
   		try{
   		var tFieldName = arrInfo[i][1];
   		var tTitle = arrInfo[i][4];
   		if(tTitle!=null&&tTitle!='')
   		{
   			// tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "').setAttribute('title',\"" + newData + "\")";
   			document.getElementById(tFieldName).title = tTitle;
   		}
   		}
   		catch(Ex)
   		{
   			}
   	}
   }
   //Mulline9GridTurnPage.pageLineNum  = 3215;
  // Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function queryMulline10Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDDutyGetAliveInputSql3";
	 mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara(fm.all("GetDutyCode").value);//ָ������Ĳ���
   strSQL = mySql.getString();
   Mulline10GridTurnPage.pageLineNum  = 3215;
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
   
}

function setCalCode(tCalCode)
{
	initCalCodeMain(document.getElementById('RiskCode').value,tCalCode);	
	document.getElementById('CALCODE').value = tCalCode;
}
function isshowbutton()
{   var value=getQueryState1();
	if(value=='0'||value==0){
		document.getElementById('savabuttonid').style.display = 'none';
		document.getElementById('checkFunc').style.display = '';
	}else{
		document.getElementById('savabuttonid').style.display = '';
		document.getElementById('checkFunc').style.display = 'none';
	}

}
//-------- add by jucy
function InputCalCodeDefFace2(){
	var selNo = Mulline10Grid.getSelNo();
	if(selNo == 0)
	{
		myAlert(""+"����ѡ��һ�����θ���������ٵ�����鿴�㷨���ݡ���"+"");
		return;
	}
	InputCalCodeDefFace();
}
//-------- end