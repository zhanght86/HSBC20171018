//程序名称：PDTestPointClewQuery.js
//程序功能：测试要点提示查询界面
//创建日期：2009-3-18
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var showInfo;	//function returnParent(){		top.opener.focus();		top.close();}
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

function returnParent()
{
	var arrReturn = new Array();
	var tSel = Mulline9Grid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"请先选择一条记录，再点击返回按钮。"+"" );
	else
	{
		var Id = Mulline9Grid.getRowColData(Mulline9Grid.getSelNo()-1, 5);		
  	try
		{
			top.opener.focus();
			top.opener.afterQuery(Id);
			top.close();
		}
		catch(ex)
		{
			myAlert(""+"关闭录入页面则退出系统，请重新登录"+"");
			window.open("PDTestPlanClewInput.jsp");
			top.close();
		}
	}
}

function queryMulline9Grid()
{
   var strSQL = "";
   strSQL = "select (select d.codename from ldcode d where d.codetype = 'plankind' and d.code = t.testplankind) , (select d.codename from ldcode d where d.codetype = 'riskkind' and d.code = t.testplanriskkind), t.clewcontent, t.remark,t.clewcontentcode from pd_testplanclew_lib t where 1 = 1 "
   + getWherePart('testplankind','plankind')    + getWherePart('testplanriskkind','riskkind')
   + getWherePart('t.Clewcontent','ClewContent','like')
   + " order by t.clewcontentcode";
   Mulline9GridTurnPage.pageLineNum  = 3215;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}

function afterCodeSelect(cOperator)
{
	if(cOperator == 'pdalltablecode')
	{
		var sql = "select fieldcode,fieldname from Pd_Basefield where tablecode = '" + fm.all("TableName").value + "'";
		
		var result = easyQueryVer3(sql,1,1,1); 
		fm.all('FieldName').CodeData = result;
	}
}
