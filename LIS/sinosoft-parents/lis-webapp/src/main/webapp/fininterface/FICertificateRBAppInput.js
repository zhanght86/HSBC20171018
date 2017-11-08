
//创建日期： 
//创建人   jw
//更新记录：  更新人    更新日期     更新原因/内容
var showInfo;

var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var k = 0;

/*********************************************************************
 *  执行新契约扫描的“开始录入”
 *  描述:进入无扫描录入页面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function ApplyInput()
{

	if(fm.CertificateId.value==''){
		alert("凭证类型编号不能为空");
		return ;
	}
	if(fm.BusinessNo.value==''){
		alert("业务号码不能为空");
		return ;
	}
	if(fm.DetailIndexID.value==''){
		alert("明细索引代码不能为空");
		return ;
	}
	if(fm.DetailIndexName.value==''){
		alert("明细索引名称不能为空");
		return ;
	}
	if(fm.DetailReMark.value==''){
		alert("细节描述不能为空");
		return ;
	}
	if(fm.ReasonType.value==''){
		alert("红冲原因类型不能为空");
		return ;
	}
	if(fm.DetailReMark.value==''){
		alert("申请人不能为空");
		return ;
	}
	if(fm.AppDate.value==''){
		alert("申请日期不能为空");
		return ;
	}
        var showStr="正在提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面";                                             
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;                                             
        var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	document.getElementById("fm").submit();
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{ 
  try
  {
  showInfo.close(); 
  if (FlagStr == "Fail" )
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
    }catch(ex){}
  queryRBResultGrid();
}


function queryRBResultGrid()
{
 
    initRBResultGrid() ;
     
    var strSQL = ""; 
    /**
    strSQL = "select AppNo,CertificateID,DetailIndexID,DetailIndexName,BusinessNo,ReasonType,DetailReMark,AppDate,Applicant,AppState from FIDataFeeBackApp where 1=1"
				 + getWherePart('BusinessNo','BusinessNo')
				 + getWherePart('DetailIndexID','DetailIndexID')
				 + " and Applicant = '" + operator + "'"
				 ;
				 */
	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.FICertificateRBAppInputSql"); //指定使用的properties文件名
		mySql1.setSqlId("FICertificateRBAppInputSql1");//指定使用的Sql的id
		mySql1.addSubPara(fm.BusinessNo.value);//指定传入的参数
		mySql1.addSubPara(fm.DetailIndexID.value);//指定传入的参数
		mySql1.addSubPara(operator);//指定传入的参数
		strSQL= mySql1.getString();
				 
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    if (!turnPage.strQueryResult)
    {
         alert("未查询到满足条件的数据！");
	 return false;
    }
    
    //设置查询起始位置
    turnPage.pageIndex = 0;
    //在查询结果数组中取出符合页面显示大小设置的数组
    turnPage.pageLineNum = 30 ;
    //查询成功则拆分字符串，返回二维数组
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //设置初始化过的MULTILINE对象
    turnPage.pageDisplayGrid = RBResultGrid;
    //保存SQL语句
    turnPage.strQuerySql = strSQL ;
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
    //调用MULTILINE对象显示查询结果
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
 
}


function ReturnData()
{
        var arrReturn = new Array();
	var tSel = CostDataKeyDefInputGrid.getSelNo();	
		
	if( tSel == 0 || tSel == null )

	    alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		
	   document.all('KeyID').value = arrResult[0][2];
	   document.all('KeyID1').value = arrResult[0][2];
	   document.all('KeyName').value = arrResult[0][3];
           document.all('KeyOrder').value = arrResult[0][4];
	   document.all('Remark').value = arrResult[0][5];            
		
	}
}





 