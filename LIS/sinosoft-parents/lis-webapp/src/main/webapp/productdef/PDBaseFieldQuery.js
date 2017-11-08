//绋嬪簭鍚嶇О锛歅DBaseFieldQuery.js
//绋嬪簭鍔熻兘锛氬熀纭?俊鎭瓧娈垫煡璇?
//鍒涘缓鏃ユ湡锛?009-3-18
//璇ユ枃浠朵腑鍖呭惈瀹㈡埛绔渶瑕佸鐞嗙殑鍑芥暟鍜屼簨浠?
var turnPage = new turnPageClass();
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
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
	if(!verifyInput2()){
		return false;
	}
  var strSQL = "";
  strSQL = "select a.tablecode,a.tablename,b.fieldcode,b.fieldname,b.fieldtype,b.isdisplay,b.officialdesc,b.busidesc,b.fieldtypename "
			+ " from Pd_Basetable a,Pd_Basefield b "
			+ " where a.tablecode=b.tablecode "
  		+ getWherePart('a.tablecode', 'TableCode')
			+ getWherePart('b.fieldcode', 'FieldCode')
			+ getWherePart('b.fieldtypename', 'FieldType')
			+ getWherePart('b.isdisplay', 'IsDisplayCode')
			+ getWherePart('b.officialdesc', 'OfficialDesc')
			+ getWherePart('b.busidesc', 'BusiDesc')
			+ " order by a.tablecode,b.displayorder ";
	Mulline9GridTurnPage.queryModal(strSQL, Mulline9Grid);
	return true;
}
// 鏁版嵁杩斿洖鐖剁獥鍙?
function returnParent()
{
	var arrReturn = new Array();
	var tSel = Mulline9Grid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"请先选择一条记录，再点击返回按钮。"+"" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			top.opener.afterReturnParent( arrReturn );
		}
		catch(ex)
		{
			myAlert( ""+"没有发现父窗口接口"+"" + ex );
		}
		top.close();
	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = Mulline9Grid.getSelNo();
	if( tRow == 0 || tRow == null || Mulline9Grid == null )
		return arrSelected;
	arrSelected = new Array();
	//璁剧疆闇?杩斿洖鐨勬暟缁?
	arrSelected = Mulline9Grid.getRowData(tRow-1);
	return arrSelected;
}

function queryMulline9Grid()
{
   var strSQL = "";
   strSQL = "";
   Mulline9GridTurnPage.pageLineNum  = 3215;
   //Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
