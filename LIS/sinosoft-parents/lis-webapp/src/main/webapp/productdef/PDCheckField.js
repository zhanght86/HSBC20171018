//程序名称：PDCheckField.js
//程序功能：投保规�?
//创建日期�?009-3-14
//该文件中包含客户端需要处理的函数和事�?
var turnPage = new turnPageClass();
//定义sql配置文件
var tResourceName = "productdef.PDCheckFieldInputSql";
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function checkNullable()
{

   if(!verifyInput())
  {
  	return false;
  }
    
  return true;
}


function setCalCode(tCalCode)
{
	initCalCodeMain(document.getElementById('RiskCode').value,tCalCode);	
	document.getElementById('CalCode').value = tCalCode;
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
	
	lockPage(""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"");
  // var showStr="正在处理数据，请您稍候并且不要修改屏幕上的�?或链接其他页�?;
//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
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
   /*
    var selNo = Mulline10Grid.getSelNo();
		
		
    initForm(); 
    if(selNo >= 0)
		{
			try
			{
				if(Mulline10Grid.canSel==1&&Mulline10Grid.mulLineCount==1)
 			{
 				//alert('@@');
 				fm.all('Mulline10Grid').checked=true;
 				//eval("fm.all('MultMainRiskGridSel')[" + MultMainRiskGrid.mulLineCount + "-1].checked=true");
 				
 			}
 			else
 			{
 				 if(fm.all("operator").value=="update")
 				 {
 				 
 					eval("fm.all('Mulline10GridSel')[" + selNo + "-1].checked=true")
 				}
 			}
 			}
 			catch(Ex)
 			{
 			}
 			updateDisplayState();
 			
		}
		*/
    if(fm.all("operator").value!="del")
    {
    	//InputCalCodeDefFace();
    	InputCalCodeDefFace('02');
    		var selNo = 0;
    	if(fm.all("operator").value=="save")
    	{
    			Mulline10Grid.addOne();
    			selNo = Mulline10Grid.mulLineCount;
    	}
    	else if(fm.all("operator").value=="update")
    	{
    		selNo = Mulline10Grid.getSelNo();
    	}

    			Mulline10Grid.setRowColData(selNo-1,1,document.getElementById('RiskCode').value);
    			Mulline10Grid.setRowColData(selNo-1,2,document.getElementById('CheckField').value);
					Mulline10Grid.setRowColData(selNo-1,3,document.getElementById('Serialno').value);
					Mulline10Grid.setRowColData(selNo-1,4,document.getElementById('CalCode').value) ;
					Mulline10Grid.setRowColData(selNo-1,5,document.getElementById('Msg').value) ;
					Mulline10Grid.setRowColData(selNo-1,6,document.getElementById('STANDBYFLAG1').value) ;
					
					
						try
				{
					if(Mulline10Grid.canSel==1&&Mulline10Grid.mulLineCount==1)
 				{
 					//alert('@@');
 					fm.all('Mulline10Grid').checked=true;
 					//eval("fm.all('MultMainRiskGridSel')[" + MultMainRiskGrid.mulLineCount + "-1].checked=true");
 				
 				}
	 				else
	 			{
 				 	if(fm.all("operator").value=="save")
 				 	{
 				 
 						eval("fm.all('Mulline10GridSel')[" + selNo + "-1].checked=true")
 					}
 				}
 			}
 			catch(Ex)
 			{
 			}
    
  	}
  	else
  	{
  		initForm(); 
  	}
    
      
  } 
}
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 


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
function button192()
{
  var selNo = Mulline10Grid.getSelNo();
	if(selNo == 0)
	{
		myAlert(""+"��ѡ��һ��Ͷ�������ٵ��"+"");
		return;
	}
	else
	{
		var name = Mulline10Grid.getRowColData(selNo-1,4);
		if(name == null || name =="")
		{
			myAlert(""+"����¼���㷨����"+"");
			return;
		}
	}
	showInfo = window.open("PDAlgoDefiMain.jsp?riskcode=" + fm.all("RiskCode").value+ "&algocode=" + Mulline10Grid.getRowColData(selNo-1,4));
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

function button193()
{
  showInfo = window.open("");
}

function queryMulline9Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDCheckFieldInputSql1";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件�?
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara("sqlid");//指定传入的参�?
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
   		if(tFieldName=='FIELDNAME')
   		{
   			tFieldName = 'CheckField';
   		}
   		else if(tFieldName=='SERIALNO')
   		{
   			tFieldName = 'Serialno';
   		}
   		else if(tFieldName=='CALCODE')
   		{
   			tFieldName = 'CalCode';
   		}
   			else if(tFieldName=='MSG')
   		{
   			tFieldName = 'Msg';
   		}
   		
   		
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
   //Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function queryMulline10Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
   if(mType==null||mType=='')
   {
	 var sqlid = "PDCheckFieldInputSql2";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件�?
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.RiskCode.value);//指定传入的参�?
   strSQL = mySql.getString();
  }
  else if(mType=='TB')
  {
  	 var sqlid = "PDCheckFieldInputSql3";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件�?
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.RiskCode.value);//指定传入的参�?
   strSQL = mySql.getString();
  }
  else
  {
  	 var sqlid = "PDCheckFieldInputSql4";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件�?
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	  mySql.addSubPara(fm.RiskCode.value);//指定传入的参�?
	 mySql.addSubPara(mEdorType);//指定传入的参�?
   strSQL = mySql.getString();
  }
   Mulline10GridTurnPage.pageLineNum  = 3215;
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
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
		myAlert(""+"����ѡ��һ��������Ϣ���ٵ�����鿴�������ݡ���"+"");
		return;
	}
	InputCalCodeDefFace();
}
//-------- end
function wrapSql1(sqlId,param){
	
	var mysql=new SqlClass();
	
	mysql.setResourceName(tResourceName);
	mysql.setSqlId(sqlId);
	for(i=0;i<param.length;i++){
		 mysql.addSubPara(param[i]);
		
	}
	
	return mysql.getString();
	
}