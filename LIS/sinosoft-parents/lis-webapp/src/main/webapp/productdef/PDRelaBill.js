//�������ƣ�PDRelaBill.js
//�����ܣ����θ����˵�����
//�������ڣ�2009-3-16
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var tResourceName = "productdef.PDRelaBillInputSql";
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function checkNullable()
{
 /* var GridObject = Mulline9Grid;
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
  		
  			alert("\"" + PropName + "\"����Ϊ��");
  			
  			return false;
  		}
  	}
  }*/
  var tbttnflag = fm.all('bttnflag').value;
  //fm.all('bttnflag').value = "1";
  if(tbttnflag=='1')
  {
  	var tLine = Mulline8Grid.mulLineCount ;
  	var tchooseFlag = false;
  	for(i=0;i<tLine;i++)
  	{
  	   if(Mulline8Grid.getChkNo(i))
  	   {
  	   	tchooseFlag = true;
  	   }
  	}
  	
  	if(!tchooseFlag)
  	{
  		myAlert(''+"��ѡ�������˵�"+'');
  		return false;
  	}
  }
  else
  {
  	var tLine = Mulline9Grid.mulLineCount ;
  	var tchooseFlag = false;
  	for(i=0;i<tLine;i++)
  	{
  	   if(Mulline9Grid.getChkNo(i))
  	   {
  	   	tchooseFlag = true;
  	   }
  	}
  	
  	if(!tchooseFlag)
  	{
  		myAlert(''+"��ѡ��סԺ�˵�"+'');
  		return false;
  	}
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
  if(!verifyInput())
	{
		return false;
	}
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.submit();
}

function update()
{
 var selNo = Mulline10Grid.getSelNo();
 if( selNo ==0 || selNo == null )
 {
 	myAlert(""+"��ѡ��һ����¼��"+"");
 	return;
 }		
 fm.all("operator").value="update";
 submitForm();
}
function del()
{
 var selNo = Mulline10Grid.getSelNo();
 if( selNo ==0 || selNo == null )
 {
 	myAlert(""+"��ѡ��һ����¼��"+"");
 	return;
 }		
 fm.all("operator").value="del";
 submitForm();
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
    if(fm.all("operator").value!="del")
    {
    	if(document.getElementById('CLMFEECALCODE').value!="")
    	{
    		InputCalCodeDefFace('99');
    	}
  	}
  	if(fm.all("operator").value=="del")
  	{
  		initDetail();
  	}
  } 
}
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline8GridTurnPage = new turnPageClass();
var Mulline10GridTurnPage = new turnPageClass(); 
function button248()
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
		if(name.indexOf(""+"�������������M���㷨"+"") == -1)
		{
			myAlert(""+"��ѡ���㷨��"+","+"���������趨���㷨"+"");
			return;
		}
	}
	
	showInfo = window.open("PDAlgoDefiMain.jsp?riskcode=" + fm.all("RiskCode").value+ "&algocode=" + Mulline9Grid.getRowColData(selNo-1,4));
}	

function queryMulline10Grid()
{
   var strSQL = "";
   //strSQL = "select a.getdutycode,a.getdutykind,a.clmfeecode,a.clmfeename from PD_LMdutyGetFeeRela a where a.getdutycode = '"+fm.all('GetDutyCode').value+"' and a.getdutykind = '"+fm.all('GetDutyKind').value+"'";
   //Mulline9GridTurnPage.pageLineNum  = 10;
   var mySql=new SqlClass();
	 var sqlid = "PDRelaBillInputSql2";
	 mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara(fm.all("GETDUTYCODE").value);//ָ������Ĳ���
	 mySql.addSubPara(fm.all("GETDUTYKIND").value);//ָ������Ĳ���
   var strSQL = mySql.getString();
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
   
}
function queryMulline8Grid()
{
   var strSQL = "select CODE,CODENAME from ldcode where codetype ='pd_llmedfeetype' and substr(code,1,1)='A'";
   Mulline8GridTurnPage.pageLineNum  = 3215;
   Mulline8GridTurnPage.queryModal(strSQL,Mulline8Grid);
}
function queryMulline9Grid()
{
   var strSQL = "select CODE,CODENAME from ldcode where codetype ='pd_llmedfeetype' and substr(code,1,1)='B'";
   Mulline9GridTurnPage.pageLineNum  = 3215;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
/*
function save()
{
 fm.all("operator").value="save";
 fm.all('bttnflag').value = "1";
 submitForm();
}*/
function save()
{
 fm.all("operator").value="save";
 submitForm();
}



function getOneData()
{

	
	var selNo = Mulline10Grid.getSelNo()-1;
	//alert(selNo);
	if(selNo>=0){
		var GetDutyCode = Mulline10Grid.getRowColData(selNo,1);
		var GetDutyKind = Mulline10Grid.getRowColData(selNo,3);
		var ClmFeeCode = Mulline10Grid.getRowColData(selNo,4);

		/*
		PDRiskFeeInputSql3_0= and FEECODE= ''{0}'' 
PDRiskFeeInputSql3_1= and PAYPLANCODE= ''{0}'' 
PDRiskFeeInputSql3_1= and INSUACCNO= ''{0}'' 
PDRiskFeeInputSql3_1= and FEECALMODE= ''{0}'' 
		*/
	var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDRelaBillInputSql3";
	 mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara(GetDutyCode);//ָ������Ĳ���
	 mySql.addSubPara(GetDutyKind);//ָ������Ĳ���
	  mySql.addSubPara(ClmFeeCode);//ָ������Ĳ���
	
	   
   strSQL = mySql.getString();
   //Mulline9GridTurnPage.pageLineNum  = 3215;
   //Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
    var tContent = easyExecSql(strSQL);
    
    
    
    //
   var mySql2=new SqlClass();
	 var sqlid2 = "PDRelaBillInputSql1";
	 mySql2.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	 mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	 mySql2.addSubPara('0');//ָ������Ĳ���
	
	   
   var sql = mySql2.getString();
 
 var rowcode = easyExecSql(sql,1,1,1);
    
    for(i=0;i<rowcode.length;i++)
    {
    	try{
    	var tCodeName = rowcode[i][1];
    	var tValue = tContent[0][i];
    	document.getElementById(tCodeName).value = tValue;
    	}
    	catch(ex)
    	{
    		
    	}
    	
    }
    
    document.getElementById('CLMFEECALTYPENAME').value="";
	
		
    showOneCodeName('pd_feerela','CLMFEECALTYPE','CLMFEECALTYPENAME');
   
    
    
    initCalCodeMain(document.getElementById('RiskCode').value,document.getElementById('CLMFEECALCODE').value);
   
	}
}

function initDetail()
{
	try{
		
		//document.getElementById('GETDUTYCODE').value="";
		//document.getElementById('GETDUTYKIND').value="";
		document.getElementById('CLMFEECODE').value="";
		//document.getElementById('GETDUTYNAME').value="";
		document.getElementById('CLMFEENAME').value="";
		document.getElementById('CLMFEECALTYPE').value="";
		document.getElementById('CLMFEECALCODE').value="";
		document.getElementById('CLMFEEDEFVALUE').value="";
		initMulline10Grid();
		queryMulline10Grid();
	}
	catch(Ex)
	{
		}
}

function setCalCode(tCalCode)
{
	initCalCodeMain(document.getElementById('RiskCode').value,tCalCode);	
	document.getElementById('CLMFEECALCODE').value = tCalCode;
}
function isshowbutton()
{   var value=getQueryState1();
	if(value=='0'||value==0){
	document.getElementById('savabuttonid').style.display = 'none';
	//document.getElementById('savabutton1').disabled=true;
	}

}