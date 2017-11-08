//程序名称：UWGErrInit.jsp
//程序功能：集体核保未过原因查询
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var tPOLNO = "";
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();

//提交，保存按钮对应操作
function submitForm()
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

  //showSubmitFrame(mDebug);
  document.getElementById("fm").submit(); //提交
  //alert("submit");
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
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
    //执行下一步操作
  }
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

//既往投保信息
function showApp()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  cProposalNo=fm.ProposalNo.value;
  //showModalDialog("./UWApp.jsp?ProposalNo="+cProposalNo,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo1 = window.open ("./UWApp.jsp?ProposalNo="+cProposalNo,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo1.focus();
    showInfo.close();
}           

//以往核保记录
function showOldUWSub()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  cProposalNo=fm.ProposalNo.value;
  //showModalDialog("./UWSub.jsp?ProposalNo1="+cProposalNo,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo1 = window.open ("./UWSub.jsp?ProposalNo1="+cProposalNo,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo1.focus();
  //window.open("./UWSubMain.jsp?ProposalNo1="+cProposalNo,"window1");
  showInfo.close();
}

//当前核保记录
function showNewUWSub()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  cProposalNo=fm.ProposalNo.value;
  //showModalDialog("./UWSub.jsp?ProposalNo1="+cProposalNo,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo1 = window.open ("./UWSub.jsp?ProposalNo1="+cProposalNo,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo1.focus();
  showInfo.close();
}                      

//保单明细信息
function showPolDetail()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  cProposalNo=fm.ProposalNo.value;
  window.open("../app/ProposalDisplay.jsp?PolNo="+cProposalNo,"window1");
  showInfo.close();
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


//选择要人工核保保单
function queryUWErr()
{
	//showSubmitFrame(mDebug);
	document.getElementById("fm").submit();
}

// 查询按钮
function easyQueryClick()
{
	// 初始化表格
	initUWGErrGrid();
	initUWErrGrid();
	
	// 书写SQL语句
	var strSQL = "";
	var GrpContNo = document.all('GrpContNo').value;
	var BqFlag = "";
	try{
	   BqFlag = document.all('BqFlag').value;
  }catch(ex){}
  	if(BqFlag == null || BqFlag != 1){
  		
  var sqlid825101757="DSHomeContSql825101757";
var mySql825101757=new SqlClass();
mySql825101757.setResourceName("uw.UWGErrInputSql");//指定使用的properties文件名
mySql825101757.setSqlId(sqlid825101757);//指定使用的Sql的id
mySql825101757.addSubPara(GrpContNo);//指定传入的参数
mySql825101757.addSubPara(GrpContNo);//指定传入的参数
mySql825101757.addSubPara(GrpContNo);//指定传入的参数
mySql825101757.addSubPara(GrpContNo);//指定传入的参数
mySql825101757.addSubPara(GrpContNo);//指定传入的参数
mySql825101757.addSubPara(GrpContNo);//指定传入的参数
mySql825101757.addSubPara(GrpContNo);//指定传入的参数
mySql825101757.addSubPara(GrpContNo);//指定传入的参数
mySql825101757.addSubPara(GrpContNo);//指定传入的参数
mySql825101757.addSubPara(GrpContNo);//指定传入的参数
mySql825101757.addSubPara(GrpContNo);//指定传入的参数
strSQL=mySql825101757.getString();		
  
//	strSQL = "select a.grpcontno,a.grppolno,b.RiskCode x, a.uwno y,a.uwerror,a.modifydate from  LCGUWError a ,LCGrpPol b where 1=1 "
//	     + " and a.GrpPolNo = b.GrpPolNo"
//			 + " and a.GrpContNo ='" + GrpContNo + "'"
//			 + " and (a.uwno = (select max(b.uwno) from LCGUWError b where b.GrpContNo = '" + GrpContNo + "' and b.GrpPolNo = a.GrpPolNo))"
//			 + " and a.uwerror not like '%团单必须进行人工核保%'"
//			 + " union "
//			 + "select c.grpcontno,'','' x, c.uwno y,c.uwerror,c.modifydate from LCGCUWError c where 1=1"
//			 + " and c.GrpContNo ='" + GrpContNo + "'"
//			 + " and (c.uwno = (select max(d.uwno) from LCGCUWError d where d.GrpContNo = '" + GrpContNo + "'))"
//			 + " and c.uwerror not like '%团单必须进行人工核保%'"
//			 + " union "
//			 + "select distinct a.grpcontno,b.grppolno,b.riskcode x,"
//			 + "(select max(LCGUWError.uwno) from LCGUWError where LCGUWError.GrpContNo = '" + GrpContNo + "' and LCGUWError.GrpPolNo = b.GrpPolNo) y,"
//			 + "a.uwerror,a.modifydate from  LCUWError a ,LCPol b where 1=1 "
//	         + " and b.GrpContNo ='" + GrpContNo + "'"
//	         + " and b.PolNo = a.PolNo"
//			 + " and a.GrpContNo ='" + GrpContNo + "'"
//			 + " and (a.uwno = (select max(b.uwno) from LCUWError b where b.GrpContNo = '" + GrpContNo + "'))"
//			 + " union "
//			 + "select distinct c.grpcontno,'','' x,"
//			 + "(select max(LCGCUWError.uwno) from LCGCUWError where LCGCUWError.GrpContNo = '" + GrpContNo + "') y,"
//			 + "c.uwerror,c.modifydate from LCCUWError c where 1=1"
//			 + " and c.GrpContNo ='" + GrpContNo + "'"
//			 + " and (c.uwno = (select max(d.uwno) from LCCUWError d where d.GrpContNo = '" + GrpContNo + "'))"
//			 + " order by x,y";
			 
	//execEasyQuery( strSQL );
	turnPage.queryModal(strSQL,UWGErrGrid);
//	if (UWGErrGrid.mulLineCount == 0)
//	{
//	    alert("没有自动核保信息，团单必须进入人工核保！");
//	}
	
	var sqlid825103211="DSHomeContSql825103211";
var mySql825103211=new SqlClass();
mySql825103211.setResourceName("uw.UWGErrInputSql");//指定使用的properties文件名
mySql825103211.setSqlId(sqlid825103211);//指定使用的Sql的id
mySql825103211.addSubPara(GrpContNo);//指定传入的参数
mySql825103211.addSubPara(GrpContNo);//指定传入的参数
mySql825103211.addSubPara(GrpContNo);//指定传入的参数
mySql825103211.addSubPara(GrpContNo);//指定传入的参数
mySql825103211.addSubPara(GrpContNo);//指定传入的参数
mySql825103211.addSubPara(GrpContNo);//指定传入的参数
mySql825103211.addSubPara(GrpContNo);//指定传入的参数
strSQL=mySql825103211.getString();
	
//	strSQL = "select a.grpcontno,a.contno z,a.polno,c.customerseqno,a.insuredname,b.RiskCode x, a.uwno y,a.uwerror,a.modifydate from  LCUWError a ,LCPol b,LCInsured c where 1=1 "
//	         + " and b.GrpContNo='" + GrpContNo + "'"
//	         + " and b.PolNo = a.PolNo"
//			 + " and a.GrpContNo ='" + GrpContNo + "'"
//			 + " and (a.uwno = (select max(b.uwno) from LCUWError b where b.GrpContNo = '" + GrpContNo + "' and b.PolNo = a.PolNo))"
//			 + " and c.contno=a.contno and c.insuredno=a.insuredno"
//			 + " and c.grpcontno='" + GrpContNo + "'"
//			 + " union "
//			 + "select c.grpcontno,c.contno z,'',aa.customerseqno,c.insuredname,'' x, c.uwno y,c.uwerror,c.modifydate from LCCUWError c,LCInsured aa where 1=1"
//			 + " and c.GrpContNo ='" + GrpContNo + "'"
//			 + " and aa.contno=c.contno and aa.insuredno=c.insuredno"
//			 + " and aa.grpcontno='" + GrpContNo + "'"
//			 + " and (c.uwno = (select max(d.uwno) from LCCUWError d where d.GrpContNo = '" + GrpContNo + "'))"
//			 + " order by z,x,y"
	
	//execEasyQuery( strSQL );
	turnPage1.queryModal(strSQL,UWErrGrid);
  }else if(BqFlag !=null && BqFlag == 1){
  	//alert(EdorNo);
  	
  	var sqlid825105754="DSHomeContSql825105754";
var mySql825105754=new SqlClass();
mySql825105754.setResourceName("uw.UWGErrInputSql");//指定使用的properties文件名
mySql825105754.setSqlId(sqlid825105754);//指定使用的Sql的id
mySql825105754.addSubPara(GrpContNo);//指定传入的参数
mySql825105754.addSubPara(EdorNo);//指定传入的参数
mySql825105754.addSubPara(GrpContNo);//指定传入的参数
mySql825105754.addSubPara(EdorNo);//指定传入的参数
mySql825105754.addSubPara(GrpContNo);//指定传入的参数
mySql825105754.addSubPara(EdorNo);//指定传入的参数
mySql825105754.addSubPara(EdorNo);//指定传入的参数
mySql825105754.addSubPara(GrpContNo);//指定传入的参数
mySql825105754.addSubPara(EdorNo);//指定传入的参数
mySql825105754.addSubPara(GrpContNo);//指定传入的参数
mySql825105754.addSubPara(GrpContNo);//指定传入的参数
mySql825105754.addSubPara(GrpContNo);//指定传入的参数
mySql825105754.addSubPara(GrpContNo);//指定传入的参数
mySql825105754.addSubPara(GrpContNo);//指定传入的参数
mySql825105754.addSubPara(GrpContNo);//指定传入的参数
strSQL=mySql825105754.getString();
  	
//	strSQL = "select a.grpcontno,a.grppolno,b.RiskCode x, nvl(to_char(a.uwno),' ') y,a.uwerror,a.modifydate from  LPGUWError a ,LPGrpPol b where 1=1 "
//	     + " and a.GrpPolNo = b.GrpPolNo and a.EdorNo = b.EdorNo "
//			 + " and a.GrpContNo ='" + GrpContNo + "'"
//			 + " and a.EdorNo ='" + EdorNo + "'"
//			 + " and (a.uwno = (select max(b.uwno) from LPGUWError b where b.GrpContNo = '" + GrpContNo + "' and b.GrpPolNo = a.GrpPolNo and b.EdorNo = a.EdorNo))"
//			 + " and a.uwerror not like '%团单必须进行人工核保%'"
//			 + " union "
//			 + "select c.grpcontno,' ',' ' x, nvl(to_char(c.uwno),' ') y,c.uwerror,c.modifydate from LPGCUWError c where 1=1"
//			 + " and c.GrpContNo ='" + GrpContNo + "'"
//			 + " and c.EdorNo ='" + EdorNo + "'"
//			 + " and (c.uwno = (select max(d.uwno) from LPGCUWError d where d.GrpContNo = '" + GrpContNo + "' and d.EdorNo = c.EdorNo))"
//			 + " and c.uwerror not like '%团单必须进行人工核保%'"
//			 + " union "
//			 + "select distinct a.grpcontno,b.grppolno,b.riskcode x,"
//			 + "(select nvl(to_char(max(LPGUWError.uwno)),' ') from LPGUWError where LPGUWError.GrpContNo = '" + GrpContNo + "' and LPGUWError.GrpPolNo = b.GrpPolNo) y,"
//			 +"a.uwerror,a.modifydate from  LPUWError a ,LPPol b where 1=1 "
//	                 + " and b.PolNo = a.PolNo and b.EdorNo = a.EdorNo"
//			 + " and a.GrpContNo ='" + GrpContNo + "'"
//			 + " and a.EdorNo ='" + EdorNo + "'"
//			 + " and (a.uwno = (select max(b.uwno) from LPUWError b where b.GrpContNo = '" + GrpContNo + "' and b.EdorNo = a.EdorNo))"
//			 + " union "
//			 + "select distinct c.grpcontno,' ',' ' x,"
//			 + "(select nvl(to_char(max(LPGCUWError.uwno)),' ') from LPGCUWError where LPGCUWError.GrpContNo = '" + GrpContNo + "' and LPGCUWError.EdorNo = '"+EdorNo+"') y,"
//			 + "c.uwerror,c.modifydate from LPCUWError c where 1=1"
//			 + " and c.GrpContNo ='" + GrpContNo + "'"
//			 + " and c.EdorNo ='" + EdorNo + "'"
//			 + " and (c.uwno = (select max(d.uwno) from LPCUWError d where d.GrpContNo = '" + GrpContNo + "' and d.EdorNo = c.EdorNo))"
//			 + " order by x,y";
			 
			 turnPage.queryModal(strSQL,UWGErrGrid);
			 
	var sqlid825111043="DSHomeContSql825111043";
var mySql825111043=new SqlClass();
mySql825111043.setResourceName("uw.UWGErrInputSql");//指定使用的properties文件名
mySql825111043.setSqlId(sqlid825111043);//指定使用的Sql的id
mySql825111043.addSubPara(GrpContNo);//指定传入的参数
mySql825111043.addSubPara(GrpContNo);//指定传入的参数
mySql825111043.addSubPara(EdorNo);//指定传入的参数
mySql825111043.addSubPara(GrpContNo);//指定传入的参数
mySql825111043.addSubPara(EdorNo);//指定传入的参数
mySql825111043.addSubPara(GrpContNo);//指定传入的参数
strSQL=mySql825111043.getString();

	
//	strSQL = "select a.grpcontno,a.contno z,a.polno,c.customerseqno,a.insuredname,b.RiskCode x, a.uwno y,a.uwerror,a.modifydate from  LPUWError a ,LPPol b,LPInsured c where 1=1 "
//	     + " and b.PolNo = a.PolNo and a.EdorNo = b.EdorNo and a.Edorno = c.EdorNo"
//			 + " and a.GrpContNo ='" + GrpContNo + "'"
//			 + " and (a.uwno = (select max(uwno) from LPUWError  where GrpContNo = '" + GrpContNo + "' and PolNo = a.PolNo and EdorNo = a.EdorNo))"
//			 + " and c.contno=a.contno and c.insuredno=a.insuredno and a.EdorNo ='"+EdorNo+"'"
//			 + " union "
//			 + "select c.grpcontno,c.contno z,'',aa.customerseqno,c.insuredname,'' x, c.uwno y,c.uwerror,c.modifydate from LPCUWError c,LPInsured aa where 1=1"
//			 + " and c.GrpContNo ='" + GrpContNo + "'"
//			 + " and c.EdorNo ='" + EdorNo + "'"
//			 + " and aa.contno=c.contno and aa.insuredno=c.insuredno and aa.EdorNo = c.EdorNo"
//			 + " and (c.uwno = (select max(d.uwno) from LPCUWError d where d.GrpContNo = '" + GrpContNo + "' and d.EdorNo= c.EdorNo))"
//			 + " order by z,x,y";
			 
	turnPage1.queryModal(strSQL,UWErrGrid);
  }	
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
				UWGErrGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}

//[打印]按钮函数
function uwPrint()
{
	//alert("查询到的记录行数："+CardInfoGrid.mulLineCount);
   	if(UWErrGrid.mulLineCount==0)
   	{
   		alert("没有可以打印的数据！！！");
   		return false;
   	}
	easyQueryPrint(2,'UWErrGrid','turnPage1');	
}

function grpUWPrint()
{
    //alert("查询到的记录行数："+CardInfoGrid.mulLineCount);
   	if(UWGErrGrid.mulLineCount==0)
   	{
   		alert("没有可以打印的数据！！！");
   		return false;
   	}
	easyQueryPrint(2,'UWGErrGrid','turnPage');
}
