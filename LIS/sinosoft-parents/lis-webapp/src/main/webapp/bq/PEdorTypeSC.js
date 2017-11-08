//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var turnPageP = new turnPageClass();
var sqlresourcename = "bq.PEdorTypeSCInputSql";

function returnParent()
{
  top.opener.getEdorItemGrid();
  top.close();
  top.opener.focus();
}

function edorTypeSCInert()
{
		var selno = LCCSpecGrid.getSelNo()-1;
	  if (selno>=0)
	  {
	      alert("你已经选择某条特约，请删除，或者进行修改操作！");
	      return;
	   }
	   if(fm.Speccontent.value=="" || fm.Speccontent.value==null)
	   {
	   	   alert("特约内容为空，请输入内容！");
	      return;
	   	}	
    fm.SpecType.value="bq";//保全新增特约的标记,hs,生效日回溯特约,yg, 员工特约
    if(!confirm("确认新增特约"))
    {
    	return ;
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
    fm.fmtransact.value="insert";
    fm.submit();

}
function edorTypeSCModify()
{
		var selno = LCCSpecGrid.getSelNo()-1;
	  if (selno <0)
	  {
	      alert("请选择要处理的特约！");
	      return;
	   }	
	  var showStr="正在修改数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.fmtransact.value="modify";
    fm.submit();
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
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
  initSpecBox();
  initPSpecBox();

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
function showNotePad()
{
    var MissionID = top.opener.document.all("MissionID").value;
    var SubMissionID = top.opener.document.all("SubMissionID").value;
    var ActivityID = '0000000003';
    var OtherNo = top.opener.document.all("OtherNo").value;

    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionID为空！");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");
}


function Edorquery()
{
    var ButtonFlag = top.opener.document.all('ButtonFlag').value;
    if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
    }
    else
    {
        divEdorquery.style.display = "";
    }
}

//撤消修改
function edorTypeSCCancel()
{
     document.all('Speccontent').value ="";
}

//删除特约
function edorTypeSCDelete()
{
		var selno = LCCSpecGrid.getSelNo()-1;
	  if (selno<0)
	  {
	      alert("请选择要删除的特约！");
	      return;
	   }	
    if(!confirm("确认删除特约"))
    {
    	return ;
    }
    var showStr="正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.fmtransact.value="delete";
    fm.submit();
}

function initPreSpecBox()
{
	var tEdorNo=fm.EdorNo.value;
	var tContNo=fm.ContNo.value;
	var tEdorType=fm.EdorType.value;  
	var tPolNo=fm.PolNo.value;
//	var tSQL="select contno,grpcontno,EndorsementNo,nvl(SpecContent,''),SerialNo,PrtSeq,SpecType from  LCCSpec  b where ContNo='"+tContNo+"' ";  //排除健康特约和//and not exists (select 'X' from LPCSpec  a where  a.SerialNo=b.SerialNo and a.ContNo=b.ContNo  and a.EdorNo='"+tEdorNo+"' and a.EdorType='SC')
	
	var tSQL = "";
	var sqlid1="PEdorTypeSCInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(tContNo);//指定传入的参数
	tSQL=mySql1.getString();
	
	turnPage.strQueryResult=easyQueryVer3(tSQL, 1, 1, 1);
    //判断是否查询成功
    if (!turnPage.strQueryResult) 
    {

	    return ;

   }
    //查询成功则拆分字符串，返回二维数组    
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //设置初始化过的MULTILINE对象
    turnPage.pageDisplayGrid =LCPReSpecGrid;    
    //保存SQL语句
    turnPage.strQuerySql = tSQL; 
    //设置查询起始位置
    turnPage.pageIndex = 0;  
    //在查询结果数组中取出符合页面显示大小设置的数组
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //调用MULTILINE对象显示查询结果
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	

	}

function initSpecBox()
{
	var tEdorNo=fm.EdorNo.value;
	var tContNo=fm.ContNo.value;
	var tEdorType=fm.EdorType.value;  
	var tPolNo=fm.PolNo.value;
//	var tSQL="select contno,grpcontno,EndorsementNo,nvl(SpecContent,''),SerialNo,PrtSeq,SpecType from  LCCSpec  b where ContNo='"+tContNo+"' and   SpecType  not in ('hx','mn','nf','qt','sj','xi','xu','zx','ch')";  //排除健康特约和//and not exists (select 'X' from LPCSpec  a where  a.SerialNo=b.SerialNo and a.ContNo=b.ContNo  and a.EdorNo='"+tEdorNo+"' and a.EdorType='SC')
	
	var tSQL = "";
	var sqlid2="PEdorTypeSCInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(tContNo);//指定传入的参数
	tSQL=mySql2.getString();
	
	turnPage.strQueryResult=easyQueryVer3(tSQL, 1, 1, 1);
    //判断是否查询成功
    if (!turnPage.strQueryResult) 
    {

	    return ;

   }
    //查询成功则拆分字符串，返回二维数组    
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //设置初始化过的MULTILINE对象
    turnPage.pageDisplayGrid =LCCSpecGrid;    
    //保存SQL语句
    turnPage.strQuerySql = tSQL; 
    //设置查询起始位置
    turnPage.pageIndex = 0;  
    //在查询结果数组中取出符合页面显示大小设置的数组
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //调用MULTILINE对象显示查询结果
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	

	}
function showSpecInfo()
{
	var selno = LCCSpecGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要处理的任务！");
	      return;
	}	
	fm.Speccontent.value = LCCSpecGrid.getRowColData(selno, 4);

}

function initPSpecBox()
{
	initLPCSpecGrid();
	var tEdorNo=fm.EdorNo.value;
	var tContNo=fm.ContNo.value;
	var tEdorType=fm.EdorType.value;  
	var tPolNo=fm.PolNo.value;
//	var tSQL="select contno,grpcontno,EndorsementNo,nvl(SpecContent,''),SerialNo,PrtSeq,SpecType from LPCSpec where EdorNo='"+tEdorNo+"' and EdorType='"+tEdorType+"' and contno='"+tContNo+"' and  SpecType  not in ('hx','mn','nf','qt','sj','xi','xu','zx','ch')";	
	
	var tSQL = "";
	var sqlid3="PEdorTypeSCInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(tEdorNo);//指定传入的参数
	mySql3.addSubPara(tEdorType);
	mySql3.addSubPara(tContNo);
	tSQL=mySql3.getString();	
	turnPageP.queryModal(tSQL,LPCSpecGrid);	

	}
function showPSpecInfo()
{
	var selno = LPCSpecGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要处理的任务！");
	      return;
	}	
	fm.Speccontent.value = LPCSpecGrid.getRowColData(selno, 4);

}	

function getClickUpSpecTemp()
{
  	var tTempMain = document.all('HealthSpecTemp').value;
		sql_temp = " 1  and  temptype in (select distinct codealias from ldcode where codetype=#healthspcetemp# and code=#"+tTempMain+"#)  ";
   showCodeListKey('spectemp',[document.all('SpecTemp'),document.all('SpecTempname'),document.all('SpecReason'),document.all('Remark'),document.all('SpecCode'),document.all('SpecType')],[0,1,2,3,0,4],null,sql_temp,"1",1);
}

function getClickSpecTemp()
{
  	var tTempMain = document.all('HealthSpecTemp').value;
		sql_temp = " 1  and  temptype in (select distinct codealias from ldcode where codetype=#healthspcetemp# and code=#"+tTempMain+"#)  ";
   //alert(tManageCom.length);
  // alert(sql_temp);
   showCodeList('spectemp',[document.all('SpecTemp'),document.all('SpecTempname'),document.all('SpecReason'),document.all('Remark'),document.all('SpecCode'),document.all('SpecType')],[0,1,2,3,0,4],null,sql_temp,"1",1);
}
