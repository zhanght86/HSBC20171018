//程序名称：UWErr.js
//程序功能：人工核保未过原因查询
//创建日期：2002-06-19 11:10:36
//创建人  ：sxy
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var tPOLNO = "";
var turnPage = new turnPageClass();
//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  if(!checkChk())
	{
		return false;
	}
  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	//校验是否选择了数据.
	
  //showSubmitFrame(mDebug);
  fm.submit(); //提交
  //alert("submit");
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
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
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    //执行下一步操作
  }
  easyQueryClick();
}


//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
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


// 查询按钮
function easyQueryClick()
{
	// 初始化表格
	initUWErrGrid();
	
	// 书写SQL语句
	var strSQL = "";
	var ContNo = document.all('ContNo').value;
	
	var PolNo = document.all('PolNo').value;
	
//合同号,被保人,险种编码,险种名称,核保信息,修改时间,是否审阅,投保单号,流水号,核保序列号,合同险种标记,投保单号
//	strSQL = "select A.contno,A.insuredno,A.riskcode,A.riskname,A.uwerror,A.modifydate,A.passname, "
//	       + " A.x,A.serialno,A.uwno,A.flag,A.proposalno from ( "
//         + " select /*+index (a PK_LCUWERROR)*/a.contno,b.insuredno,b.riskcode, "
//         + " (select riskname from lmriskapp where riskcode=b.riskcode) riskname,a.uwerror, "
//         + " concat(concat(to_char(a.modifydate,'yyyy-mm-dd'),' '),a.modifytime) modifydate, "
//         + " (select codename from ldcode where codetype='autouwpassflag' and code=(case when a.sugpassflag is not null then a.sugpassflag else 'N' end)) passname, "
//         + " a.proposalno x,a.serialno,a.uwno,'risk' flag,b.proposalno proposalno "
//	       + " from LCUWError a,lcpol b "
//         + " where a.proposalno=b.proposalno "
//	       + " and b.contno='"+ContNo+"' "
//	   //    + " and (a.uwno = (select max(b.uwno) "
//		//		 + " from LCUWError b where b.ContNo = '"+ContNo+"' "
//		//		 + " and b.PolNo = a.PolNo)) "
//				+" and (a.uwno in (select c.batchno "
//				+" from LCUWMaster c where c.ContNo = '"+ContNo+"' "
//				+ " and c.proposalno = a.proposalno)) "
//		//合同核保信息查询
//         + " union "
//         + " select c.contno,d.insuredno,'000000' riskcode,'合同核保信息' riskname, c.uwerror, "
//         + "concat(concat( to_char(c.modifydate,'yyyy-mm-dd'),' '),c.modifytime) modifydate, "
//         + " (select codename from ldcode where codetype='autouwpassflag' and code=(case when c.sugpassflag is not null then c.sugpassflag else 'N' end)) passname, "
//         + " c.proposalcontno x,c.serialno,c.uwno,'cont' flag,'999999999999999' proposalno "
//	       + " from LCCUWError c,lccont d "
//         + " where 1 = 1 "
//	       + " and c.ContNo = '"+ContNo+"' "
//         + " and c.contno=d.contno "
//	  //     + " and (c.uwno = (select max(d.uwno) "
//		//		 + " from LCCUWError d where d.ContNo = '"+ContNo+"')) "
//				+ " and (c.uwno in (select e.batchno "
//				 + " from LCCUWMaster e where e.ContNo = '"+ContNo+"')) "
//		//被保人核保信息查询
//		 + " union "
//         + " select c.contno,d.insuredno,'000000' riskcode,'被保人核保信息' riskname, c.uwerror, "
//         + " concat(concat(to_char(c.modifydate,'yyyy-mm-dd'),' '),c.modifytime) modifydate, "
//         + " (select codename from ldcode where codetype='autouwpassflag' and code=(case when c.sugpassflag is not null then c.sugpassflag else 'N' end)) passname, "
//         + " c.proposalcontno x,c.serialno,c.uwno,'insured' flag,'999999999999999' proposalno "
//	       + " from LCIndUWError c,lcinsured d "
//         + " where 1 = 1 "
//	       + " and c.ContNo in (select prtno from lccont where contno='"+ContNo+"') "
//         + " and c.proposalcontno=d.prtno "
//         + " and c.insuredno=d.insuredno "
//				+ " and (c.uwno in (select e.batchno "
//				 + " from LCIndUWMaster e where e.ContNo = '"+ContNo+"' and e.insuredno=c.insuredno)) "
//         + " ) A order by A.contno,A.insuredno,A.proposalno,A.riskcode,A.modifydate";
//	prompt("",strSQL);
	//execEasyQuery( strSQL );
	
	var sqlid1="UWErrSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("uw.UWErrSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(PrtNo);//指定传入的参数
	mySql1.addSubPara(ContNo);//指定传入的参数
	mySql1.addSubPara(ContNo);//指定传入的参数
	mySql1.addSubPara(ContNo);//指定传入的参数
	mySql1.addSubPara(ContNo);//指定传入的参数
	mySql1.addSubPara(ContNo);//指定传入的参数
	mySql1.addSubPara(ContNo);//指定传入的参数
	strSQL = mySql1.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
	  //如果是老系统转入的数据，则上面的SQL会查不到自核信息 ，增加下面的查询，查询老系统的自核信息，如果两条SQL都查询
	  //不到数据则认为无自动核保信息05-21
//	  var tSql = " select contno,insuredno,'','合同核保信息',uwerror,concat(concat(to_char(b.outdate, 'yyyy-mm-dd') , ' ' ), b.outtime),'已审阅'"
//		  	 + " from lcuwerror a,lbmission b where contno='"+ContNo+"' and b.missionprop1=(select prtno from lccont where contno='"+ContNo+"')"
//		  	 + " and b.serialno=(select max(serialno) from lbmission where missionprop1=b.missionprop1 and activityid='0000001003')";
	
	  var sqlid2="UWErrSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.UWErrSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(PrtNo);//指定传入的参数
		mySql2.addSubPara(ContNo);//指定传入的参数
		mySql2.addSubPara(ContNo);//指定传入的参数
		var tSql = mySql2.getString();
		
	  turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1); 
	  if(!turnPage.strQueryResult){
	    alert("无自动核保信息！");
	    return "";
	  }
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = UWErrGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;

}

function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "没有找到相关的数据!" );
	else
	{
		arrGrid = arrResult;
		// 显示查询结果
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				UWErrGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}
function checkChk()
{
	var tCount = 0;
	var i;
	for(i=0;i<UWErrGrid.mulLineCount;i++)
	{
		  var t =UWErrGrid.getChkNo(i);
		  //alert(t);
			if(t)
			{
				tCount++;
			}
	}
	if(tCount<=0)
	{
		alert("请选择需要审阅的核保信息!");
		return false;
	}
	return true;
}