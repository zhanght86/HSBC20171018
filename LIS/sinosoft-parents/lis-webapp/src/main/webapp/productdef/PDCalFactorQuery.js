//程序名称：PDCalFactorQuery.js
//程序功能：测试要点提示查询界面
//创建日期：2009-3-18
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var showInfo;	
//定义sql配置文件
var tResourceName = "productdef.PDCalFactorQueryInputSql";
function submitForm()
{
  var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
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
var Mulline9GridTurnPage = new turnPageClass(); 
function query()
{
	 var mySql=new SqlClass();
	 var sqlid = "PDCalFactorQueryInputSql1";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.all("FactorType").value);//指定传入的参数
	 mySql.addSubPara(fm.all("Kind").value);//指定传入的参数
	 mySql.addSubPara(fm.all("Module").value);//指定传入的参数
	 mySql.addSubPara(fm.all("FactorCode").value);//指定传入的参数
	 mySql.addSubPara(fm.all("FactorValType").value);//指定传入的参数
	 mySql.addSubPara(fm.all("FactorDesc").value);//指定传入的参数
   	
   	if(fm.selectTable.value == null || fm.selectTable.value != "PD_CalFactor_Lib")
   	{
   		sqlid = "PDCalFactorQueryInputSql2";
   		mySql.setSqlId(sqlid);//指定使用的Sql的id
   		mySql.addSubPara(fm.all("FactorCode").value);//指定传入的参数
	 		mySql.addSubPara(fm.all("FactorDesc").value);//指定传入的参数
   	  if(fm.all("FactorType").value == "1")
   		{
   			mySql.addSubPara("0");//指定传入的参数
   		}else{
   			mySql.addSubPara("1");//指定传入的参数
   		}
   	}   	
   	var strSQL = mySql.getString();
   	   	
   Mulline9GridTurnPage.pageLineNum  = 10;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
var dicSelectedFactors = new ActiveXObject("Scripting.Dictionary");

function checkedEvent()
{	
	var columnIndexs = new Array();
	columnIndexs[0] = 1;
	columnIndexs[1] = 2;
	columnIndexs[2] = 3;
	columnIndexs[3] = 4;
	columnIndexs[4] = 5;
	columnIndexs[5] = 6;
	columnIndexs[6] = 7;
	columnIndexs[7] = 8;
	columnIndexs[8] = 9;	
		
	addCheckedResult(Mulline9Grid, Mulline9GridTurnPage, columnIndexs);
}

// obj:MulLineName,objTurnPage:MulLineName+TurnPage,columnIndexs:需要列的序号数组
function addCheckedResult(obj, objTurnPage, columnIndexs)
{
	var arrColValues = new Array();
	
	var pageIndex = objTurnPage.pageIndex;
	
	for(var i = 0; i < obj.mulLineCount; i++)
	{
		if(obj.getChkNo(i))	
		{
			if(!dicSelectedFactors.Exists(pageIndex + "_" + i))
			{
				for(var j = 0; j < columnIndexs.length; j++)
				{
					arrColValues[j] = obj.getRowColData(i, columnIndexs[j]);
				}
				
				dicSelectedFactors.Add(pageIndex + "_" + i, arrColValues);
			}
		}
		else
		{
			if(dicSelectedFactors.Exists(pageIndex + "_" + i))
			{
				dicSelectedFactors.Remove(pageIndex + "_" + i);
			}
		}
	}
}

function returnParent( )
{
	try
	  {
	  	//modify by nicole 如果dicSelectedFactors中没有add任何元素，则会报错
	  	var factorArr = dicSelectedFactors.Keys().toArray();
	  	if( factorArr.length > 0 )
	  	{
		  	top.opener.afterQueryFactor(dicSelectedFactors);	  		
	  	}
	  	top.close();
		top.opener.focus();
	  }
	  catch(ex)
	  {
	  	myAlert(""+"关闭录入页面则退出系统，请重新登录"+""+ex);
	  	window.open("PDCalFactorInput.jsp");
	  	top.close();
	  }
}
