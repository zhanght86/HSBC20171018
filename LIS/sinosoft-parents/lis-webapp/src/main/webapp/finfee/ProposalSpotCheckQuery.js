//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;

/*********************************************************************
 *  进行复核抽查
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function approvePol1()
{
	var tSel = PolGrid.getSelNo();
	if( tSel == null || tSel == 0 )
		alert("请选择一张收费单后，再进行复核抽检操作");
	else
	{
		var i = 0;
		var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		
		showSubmitFrame(mDebug);
		document.getElementById("fm").submit();//提交
	}
}

/*********************************************************************
 *  投保单复核的提交后的操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	
	//解除印刷号的锁定
    var TempFeeNo = PolGrid.getRowColData(PolGrid.getSelNo()-1, 2);
    var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo="+TempFeeNo+"&CreatePos=复核抽检&PolState=2002&Action=DELETE";
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
	var name='提示';   //网页名称，可为空; 
	var iWidth=1;      //弹出窗口的宽度; 
	var iHeight=1;     //弹出窗口的高度; 
	var iTop = 1; //获得窗口的垂直位置 
	var iLeft = 1;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	if (FlagStr == "Fail" )
	{             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	
  // 刷新查询结果
	easyQueryClick();		
}

function unlock(TempFeeNo)
{
	//alert("TempFeeNo"+TempFeeNo);	
	var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo="+TempFeeNo+"&CreatePos=复核抽检&PolState=2002&Action=DELETE";
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
	var name='提示';   //网页名称，可为空; 
	var iWidth=1;      //弹出窗口的宽度; 
	var iHeight=1;     //弹出窗口的高度; 
	var iTop = 1; //获得窗口的垂直位置 
	var iLeft = 1;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    easyQueryClick();		
}

/*********************************************************************
 *  显示div
 *  参数  ：  第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
 *  返回值：  无
 *********************************************************************
 */
function showDiv(cDiv,cShow)
{
	if (cShow=="true")
		cDiv.style.display="";
	else
		cDiv.style.display="none";  
}

/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}
        

/*********************************************************************
 *  调用EasyQuery查询保单
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
function easyQueryClick()
{
	var addSQL = "";
	var tManageCom= document.all('ManageCom').value;

	// 初始化表格
	initPolGrid();
	
	// 书写SQL语句			 
	var strSql = "";
	if(_DBT==_DBO){
		strSql = "select (select operator from ldsystrace where ldsystrace.polno = rpad(a.doccode,20) and polstate='2002' and CreatePos='复核抽检' and rownum=1) operator,d.bussno,a.managecom,d.bpoid,a.makedate,a.maketime"
            +" from ES_DOC_MAIN a,BPOMissionState d"
            +" where a.doccode=d.bussno"
            +" and d.bussnotype='OF'"
            +" and d.dealtype='01' and a.makedate>=now()-30 and a.makedate<=now() and d.makedate>=now()-30 and d.makedate<=now() "
            +" and d.state='0' and a.managecom like '"+ComCode+"%%' "
            + getWherePart('a.makedate','ScanDate','<=')
	           + getWherePart('d.BussNo','TempFeeNo' );
		
//		    var  ScanDate = window.document.getElementsByName(trim("ScanDate"))[0].value;
//		    var  TempFeeNo = window.document.getElementsByName(trim("TempFeeNo"))[0].value;
//	     	var  sqlid1="ProposalSpotCheckQuerySql0";
//	     	var  mySql1=new SqlClass();
//	     	mySql1.setResourceName("finfee.ProposalSpotCheckQuerySql"); //指定使用的properties文件名
//	     	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
//	     	mySql1.addSubPara(ComCode);//指定传入的参数
//	     	mySql1.addSubPara(ScanDate);//指定传入的参数
//	     	mySql1.addSubPara(TempFeeNo);//指定传入的参数
//	     	strSql=mySql1.getString();
	}else if(_DBT==_DBM){
		strSql = "select (select operator from ldsystrace where ldsystrace.polno = rpad(a.doccode,20) and polstate='2002' and CreatePos='复核抽检' limit 0,1) operator,d.bussno,a.managecom,d.bpoid,a.makedate,a.maketime"
            +" from ES_DOC_MAIN a,BPOMissionState d"
            +" where a.doccode=d.bussno"
            +" and d.bussnotype='OF'"
            +" and d.dealtype='01' and a.makedate>=now()-30 and a.makedate<=now() and d.makedate>=now()-30 and d.makedate<=now() "
            +" and d.state='0' and a.managecom like '"+ComCode+"%%' "
            + getWherePart('a.makedate','ScanDate','<=')
	           + getWherePart('d.BussNo','TempFeeNo' );
		
//		var  ScanDate = window.document.getElementsByName(trim("ScanDate"))[0].value;
//	    var  TempFeeNo = window.document.getElementsByName(trim("TempFeeNo"))[0].value;
//     	var  sqlid2="ProposalSpotCheckQuerySql1";
//     	var  mySql2=new SqlClass();
//     	mySql2.setResourceName("finfee.ProposalSpotCheckQuerySql"); //指定使用的properties文件名
//     	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
//     	mySql2.addSubPara(ComCode);//指定传入的参数
//     	mySql2.addSubPara(ScanDate);//指定传入的参数
//     	mySql2.addSubPara(TempFeeNo);//指定传入的参数
//     	strSql=mySql2.getString();
	}
	             
	if(!(document.all('ManageCom').value==null||document.all('ManageCom').value==""))
	{
		 strSql=strSql+ " and a.managecom like '%25" + document.all('ManageCom').value + "%25'" ;
	}
	else
	{
		//管理机构为空，则默认以当前机构查询
		strSql=strSql+" and a.managecom like '%25" + ComCode + "%25'" ;
	}
	
	if(!(document.all('BPOID').value==null||document.all('BPOID').value==""))
	{
		 strSql=strSql+ " and d.BPOID = '" + document.all('BPOID').value + "'" ;
	}
	
	strSql=strSql+ addSQL+" order by a.makedate,a.maketime" ;
	
  //prompt("",strSql);
  //alert(strSql);
	//turnPage.queryModal(strSql, PolGrid);
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  

  //判断是否查询成功
  if (!turnPage.strQueryResult) {  
    //清空MULTILINE，使用方法见MULTILINE使用说明 
    PolGrid.clearData();  
    return false;
  }
  
  //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = PolGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSql; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  //控制是否显示翻页按钮
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
}



function SpotCheck() 
{ 
  if (PolGrid.getSelNo() == 0) {
    alert("请先选择一条收费单信息！");
    return false;
  } 
  
  var TempFeeNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 2);
  mSwitch.deleteVar( "TempFeeNo" );
  mSwitch.addVar( "TempFeeNo", "", TempFeeNo );
  var strSql = "";
  if(_DBT==_DBO){
	  //strSql = "select * from ldsystrace where PolNo='" + TempFeeNo + "' and CreatePos='复核抽检'  and  PolState='2002' and rownum='1'";
	  var  sqlid1="ProposalSpotCheckQuerySql0";
   	  var  mySql1=new SqlClass();
   	  mySql1.setResourceName("finfee.ProposalSpotCheckQuerySql"); //指定使用的properties文件名
   	  mySql1.setSqlId(sqlid1);//指定使用的Sql的id
   	  mySql1.addSubPara(TempFeeNo);//指定传入的参数
   	  strSql=mySql1.getString();
  }else if(_DBT==_DBM){
	  //strSql = "select * from ldsystrace where PolNo='" + TempFeeNo + "' and CreatePos='复核抽检'  and  PolState='2002' limit 0,1";
	  var  sqlid2="ProposalSpotCheckQuerySql1";
   	  var  mySql2=new SqlClass();
   	  mySql2.setResourceName("finfee.ProposalSpotCheckQuerySql"); //指定使用的properties文件名
   	  mySql2.setSqlId(sqlid2);//指定使用的Sql的id
   	  mySql2.addSubPara(TempFeeNo);//指定传入的参数
   	  strSql=mySql2.getString();
	  
  }
  
  //prompt("",strSql);
  var arrResult = easyExecSql(strSql);
  //alert("arrResult[0][1]"+arrResult[0][1]);
  //alert("Operator"+Operator);
  if (arrResult!=null && arrResult[0][1]!=Operator) {
    alert("该印刷号的投保单已经被操作员（" + arrResult[0][1] + "）在（" + arrResult[0][5] + "）位置锁定！您不能操作，请选其它的印刷号！");
    return;
  }
  //锁定该印刷号
  var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + TempFeeNo + "&CreatePos=复核抽检&PolState=2002&Action=INSERT";
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
  var name='提示';   //网页名称，可为空; 
	var iWidth=1;      //弹出窗口的宽度; 
	var iHeight=1;     //弹出窗口的高度; 
	var iTop = 1; //获得窗口的垂直位置 
	var iLeft = 1;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  easyScanWin = window.open("./FineWbProposalInputMain1.jsp?LoadFlag=4&DealType=01&TempFeeNo="+TempFeeNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");    
  
}


