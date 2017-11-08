var showInfo;
 var turnPage = new turnPageClass();
 var turnPage1 = new turnPageClass();
 var mAllType = '2';
//是否为数字
function isdigit(c){
  return(c>='0'&&c<='9');
}

function submitForm()
{
	
if(verifyInput()) 
  {	  	
  	var i = 0;
	  var showStr="正在准备打印数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//	  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	  var name='提示';   //网页名称，可为空; 
	  var iWidth=550;      //弹出窗口的宽度; 
	  var iHeight=250;     //弹出窗口的高度; 
	  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	  showInfo.focus();

	  document.getElementById("fm").submit(); //提交
	}
}

function addData()
{
	
	if(!verifyInput())
	{
		return false;
	}
	document.all('hideaction').value='INSERT';
	if(!submitForm())
	{
		return false;
	}
	document.all('hideaction').value='';
}
function addDataSub()
{
	
	if(!verifyInput())
	{
		return false;
	}
	
	//如果为空,初始化一个格
	if(ComGroupMapGrid.mulLineCount<=0)
	{
		initComGroupMapGrid();
	}
	document.all('hideaction').value='INSERTSUB';
	if(!submitForm())
	{
		return false;
	}
	document.all('hideaction').value='';
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	document.all('hideaction').value='';
	showInfo.close();
	
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=350;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
  	parent.fraInterface.document.all('compute').disabled = false;
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  	var name='提示';   //网页名称，可为空; 
  	var iWidth=550;      //弹出窗口的宽度; 
  	var iHeight=350;     //弹出窗口的高度; 
  	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  	showInfo.focus();

  	initForm();
  }
}






//扩充算法数据保存
function saveOtherData()
{
	var ComGroupCode = document.all('ComGroupCode').value;
	var BatchNo = document.all('BatchNo').value;
	if(ComGroupCode==null||trim(ComGroupCode)==''||BatchNo==null||trim(BatchNo)=='')
	{
		alert('请先选择机构组!');
		return false;
	}
	document.all('hideaction').value='OTHERSAVE';
	if(!submitForm())
	{
		return false;
	}
	document.all('hideaction').value='';
}


function setDefaultClass()
{
		var ComGroupCode = document.all('ComGroupCode').value;
	var BatchNo = document.all('BatchNo').value;
	if(ComGroupCode==null||trim(ComGroupCode)==''||BatchNo==null||trim(BatchNo)=='')
	{
		alert('请先选择机构组!');
		return false;
	}
	if(document.all('checkbox1').checked == true)
	{
	  document.all('divOtherGrid').style.display="";
	  document.all('WorkDetail').value = "select comcode,shortname from ldcom a where 1=1 ";
	                           
          var tSQL = " select trim(calsql) from LDComGroup where comgroup='"+ComGroupCode+"' and batchno='"+BatchNo+"'";
          //prompt('1',tSQL);
          var strQueryResult1 = easyQueryVer3(tSQL,1,1,1);
          if(!strQueryResult1)
          {
          	document.all('OtherCondition').value='';
          }
          else 
     	  {
     	    var arr = decodeEasyQueryResult(strQueryResult1);
     	    document.all('OtherCondition').value=arr[0][0];
     	  }
        }
        else
	{
	   document.all('divOtherGrid').style.display="none";
	}	
}




function queryComGroupConfig()
{
	var tComGroupCode = document.all('ComGroupCode').value;
	var tComGroupName = document.all('ComGroupName').value;

	var tSQL = "select comgroup,comgroupname,remark,batchno from LDComGroup "
	         + " where 1=1 " ;
	       //  + getWherePart( 'LDComGroup.comgroupname','ComGroupName','like' );
  if(tComGroupCode!=null&&tComGroupCode!='')
  {
  	tSQL = tSQL + " and comgroup ='"+tComGroupCode+"' ";
  }  
  if(tComGroupName!=null&&tComGroupName!='')
  {
  	tSQL = tSQL + " and comgroupname like '%"+tComGroupName+"%' ";
  }
                                 
   tSQL = tSQL + " order by comgroup ";
  turnPage1.queryModal(tSQL,ComGroupGrid);
}

function getComGroupDetail(param1,param2)
{
	document.all('checkbox1').checked = false;
	var tSelNo = ComGroupGrid.getSelNo() - 1;
	var ComGroupCode = ComGroupGrid.getRowColData(tSelNo, 1);
	//alert(RiskCode);
	var ComGroupName = ComGroupGrid.getRowColData(tSelNo, 2);
	var GroupInfo = ComGroupGrid.getRowColData(tSelNo, 3);
	var BatchNo = ComGroupGrid.getRowColData(tSelNo, 4);
	
	
	document.all('ComGroupCode').value = ComGroupCode;
	document.all('ComGroupName').value = ComGroupName;
	document.all('GroupInfo').value = GroupInfo;
	document.all('BatchNo').value = BatchNo;
	initComGroupMapGrid();  
  //查询机构组映射
	queryComGroupMap();
}

function queryComGroupMap()
{
	var ComGroupCode = document.all('ComGroupCode').value;
	var BatchNo = document.all('BatchNo').value;
	if(ComGroupCode==null||trim(ComGroupCode)==''||BatchNo==null||trim(BatchNo)=='')
	{
		alert('请先选择机构组!');
		return false;
	}
	var tSQL = "select comcode,(select shortname from ldcom where comcode=a.comcode)from LDComToComGroup a "
	         + " where comgroup='"+ComGroupCode+"' "
	         + " order by comcode "
	
	turnPage.queryModal(tSQL,ComGroupMapGrid,null,null,50);
}

function addData1()
{
	var ComGroupCode = document.all('ComGroupCode').value;
	var BatchNo = document.all('BatchNo').value;
	if(ComGroupCode==null||trim(ComGroupCode)==''||BatchNo==null||trim(BatchNo)=='')
	{
		alert('请先选择机构组!');
		return false;
	}
	if(!verifyInput())
	{
		return false;
	}
	document.all('hideaction').value='INSERTSUB';
	if(!submitForm())
	{
		return false;
	}
	document.all('hideaction').value='';
}

//扩充算法数据保存
function queryOtherData()
{
	//alert('1');
	var ComGroupCode = document.all('ComGroupCode').value;
	var BatchNo = document.all('BatchNo').value;
	if(ComGroupCode==null||trim(ComGroupCode)==''||BatchNo==null||trim(BatchNo)=='')
	{
		alert('请先选择机构组!');
		return false;
	}
	
	var tSQL = "";
//	alert(document.all('OtherCondition').value);
	tSQL = document.all('WorkDetail').value + " " + document.all('OtherCondition').value;
//	prompt(tSQL);
	 turnPage.strQueryResult  = easyQueryVer3(tSQL, 1, 0, 1);
		//alert(strSQL);
		if (!turnPage.strQueryResult) 
		{
			alert("扩充算法有误");
			return false;
		}
		
		//查询成功则拆分字符串，返回二维数组
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult,0,0,turnPage);
		//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
		turnPage.pageDisplayGrid = ComGroupMapGrid;
		//保存SQL语句
		turnPage.strQuerySql = tSQL;
		//设置查询起始位置
		turnPage.pageIndex = 0;
		//在查询结果数组中取出符合页面显示大小设置的数组
		var tArr = new Array();
                tArr=turnPage.getData(turnPage.arrDataCacheSet,turnPage.pageIndex,50,turnPage);
		//调用MULTILINE对象显示查询结果
		displayMultiline(tArr, turnPage.pageDisplayGrid,turnPage);
		
}