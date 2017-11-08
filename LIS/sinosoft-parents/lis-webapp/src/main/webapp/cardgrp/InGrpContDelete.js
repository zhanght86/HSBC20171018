
var turnPage = new turnPageClass();
var showInfo;

function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}

function querygCont()
{
	initGrpGrid();
	var strSQL = "select a.ContNo,a.InsuredNo,a.InsuredName,decode(a.insuredsex,0,'男',1,'女',2,'不详',''),a.InsuredBirthday,a.InsuredIDType,a.InsuredIDNo from LCCont a where "
    				 + " a.GrpContNo='"+ GrpContNo +"'";
  				 
  if(fm.ContNo.value.length!=0)  strSQL = strSQL + " and a.ContNo='"+ fm.ContNo.value +"'"; 
  if(fm.InsuredNo.value.length!=0)     strSQL = strSQL + " and a.InsuredNo='"+ fm.InsuredNo.value +"'";
  
  
  //alert(strSQL);
 turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    
  
    alert("查询失败！");
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = GrpGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
}


function deleteCont()
{
  var i = 0;
	var flag = 0;
	for( i = 0; i < GrpGrid.mulLineCount; i++ ) {
		if( GrpGrid.getChkNo(i) == true ) {
			//如果发现有选择，则直接退出循环，继续
			flag = 1;
			break;
		}
	}
	if( flag == 0 ) {
		alert("请先选择一条记录");
		return false;
	}
  
  if (!confirm("确认要删除该保单吗？"))
	{
		return;
	}
    
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  lockScreen('lkscreen');  
  fm.submit();

}

function afterSubmit( FlagStr, content )
{ 
	unlockScreen('lkscreen');  
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    alert(content);
  }
  else
  { 
    alert("操作成功");
  }
   querygCont();
  
}	