//程序名称：PDBaseFieldQuery.js
//程序功能：基�?��息字段查�?
//创建日期�?009-3-18
//该文件中包含客户端需要处理的函数和事�?
var turnPage = new turnPageClass();
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function submitForm()
{
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
// 数据返回父窗�?
function returnParent()
{
	var arrReturn = new Array();
	var tSel = Mulline9Grid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"����ѡ��һ����¼���ٵ�����ذ�ť��"+"" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			top.opener.afterReturnParent( arrReturn );
		}
		catch(ex)
		{
			myAlert( ""+"û�з��ָ����ڽӿ�"+"" + ex );
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
	//设置�?��返回的数�?
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
