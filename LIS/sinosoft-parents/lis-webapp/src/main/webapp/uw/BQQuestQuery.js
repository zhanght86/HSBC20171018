//程序名称：QuestQuery.js
//程序功能：问题件查询
//创建日期：2002-09-24 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var k = 0;
var turnPage = new turnPageClass();
var cflag = "";  //问题件操作位置 1.核保
var canReplyFlag = false;

//提交,保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在保存数据,请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  fm.submit(); //提交
}

//提交,保存按钮对应操作
function IfPrint()
{
  var i = 0;
  var showStr="正在保存数据,请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
　cContNo = document.getELmentById("fm").ContNo.value;  //保单号码	
//　var strSql = "select * from LCPol where PolNo='" + cContNo + "' and  approveflag = '2'";

 var sqlid1="BQQuestQuerySql1";
 var mySql1=new SqlClass();
 mySql1.setResourceName("uw.BQQuestQuerySql");
 mySql1.setSqlId(sqlid1);//指定使用SQL的id
 mySql1.addSubPara(cContNo);//指定传入参数
 var strSql = mySql1.getString();

    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {     
   　　alert("已发核保通知书,但未打印,不容许在此时修改问题件的打印标志！");
          return;
      }      
  fm.action = "./QuestQueryUpdatePrintFlag.jsp";
  fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{  	
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    //showInfo.close();
    alert(content);
  }
  else if (FlagStr == "Succ") {
    content = "操作成功！";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    //alert(document.all('hiddenOperate').value);
    if(document.all('hiddenOperate').value!=null&&document.all('hiddenOperate').value=='finishMission')
    {    
        showInfo.close();	
    	top.opener.easyQueryClickSelf();
    	top.close();    	
    }
  }
  else
  { 
	var showStr="打印标记更新成功";
  	alert(showStr);
  }
}

//显示frmSubmit框架,用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
	parent.fraMain.rows = "0,0,50,82,*";
  }
  else 
  {
  	parent.fraMain.rows = "0,0,0,82,*";
  }
}
         

//显示div,第一个参数为一个div的引用,第二个参数为是否显示,如果为"true"则显示,否则不显示
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

function manuchkspecmain()
{
	fm.submit();
}

function query()
{
	var tFlag = fm.Flag.value;
	// 初始化表格
	initQuestGrid(tFlag);
	initContent();

	// 书写SQL语句
	k++;
	var strSQL = "";
	var ifreply = fm.IfReply.value;
	var tOPos = fm.OperatePos.value;
	
	var tContNo = fm.ContNo.value;
	var tEdorNo = fm.EdorNo.value;	
	
	if (ifreply == "Y")
	{
	
	  
//		strSQL = "select EdorNo,issuetype,issuecont,replyresult,operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(select codename from ldcode where codetype='bqbackobj' and comcode=BackObjType),needprint,serialno,makedate,modifydate,OperatePos from lpissuepol a where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is not null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'EdorNo','EdorNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')	  	
				 
		 var  ContNo2 = window.document.getElementsByName(trim("ContNo"))[0].value;
	     var  EdorNo2 = window.document.getElementsByName(trim("EdorNo"))[0].value;
	     var  BackObj2 = window.document.getElementsByName(trim("BackObj"))[0].value;
	     var  ManageCom2 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	     var  OManageCom2 = window.document.getElementsByName(trim("OManageCom"))[0].value;
	     var  OperatePos2 = window.document.getElementsByName(trim("OperatePos"))[0].value;
		 var sqlid2="BQQuestQuerySql2";
		 var mySql2=new SqlClass();
		 mySql2.setResourceName("uw.BQQuestQuerySql");
		 mySql2.setSqlId(sqlid2);//指定使用SQL的id
		 mySql2.addSubPara(k);//指定传入参数
		 mySql2.addSubPara(k);//指定传入参数
		 mySql2.addSubPara(manageCom);//指定传入参数
		 mySql2.addSubPara(ContNo2);//指定传入参数
		 mySql2.addSubPara(EdorNo2);//指定传入参数
		 mySql2.addSubPara(BackObj2);//指定传入参数
		 mySql2.addSubPara(ManageCom2);//指定传入参数
		 mySql2.addSubPara(OManageCom2);//指定传入参数
		 mySql2.addSubPara(OperatePos2);//指定传入参数
		 strSQL = mySql2.getString();
				 
	}
 	else if (ifreply == "N")
	{	  
//		strSQL = "select EdorNo,issuetype,issuecont,replyresult,operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(select codename from ldcode where codetype='bqbackobj' and comcode=BackObjType),needprint,serialno,makedate,'',OperatePos from lpissuepol a where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is  null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'EdorNo','EdorNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	  
		
		 var  ContNo3 = window.document.getElementsByName(trim("ContNo"))[0].value;
	     var  EdorNo3 = window.document.getElementsByName(trim("EdorNo"))[0].value;
	     var  BackObj3 = window.document.getElementsByName(trim("BackObj"))[0].value;
	     var  ManageCom3 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	     var  OManageCom3 = window.document.getElementsByName(trim("OManageCom"))[0].value;
	     var  OperatePos3 = window.document.getElementsByName(trim("OperatePos"))[0].value;
	     var  Quest3 = window.document.getElementsByName(trim("Quest"))[0].value;
		 var sqlid3="BQQuestQuerySql3";
		 var mySql3=new SqlClass();
		 mySql3.setResourceName("uw.BQQuestQuerySql");
		 mySql3.setSqlId(sqlid3);//指定使用SQL的id
		 mySql3.addSubPara(k);//指定传入参数
		 mySql3.addSubPara(k);//指定传入参数
		 mySql3.addSubPara(manageCom);//指定传入参数
		 mySql3.addSubPara(ContNo3);//指定传入参数
		 mySql3.addSubPara(EdorNo3);//指定传入参数
		 mySql3.addSubPara(BackObj3);//指定传入参数
		 mySql3.addSubPara(ManageCom3);//指定传入参数
		 mySql3.addSubPara(OManageCom3);//指定传入参数
		 mySql3.addSubPara(OperatePos3);//指定传入参数
		 mySql3.addSubPara(Quest3);//指定传入参数
		 strSQL = mySql3.getString();
		
	}
	//走这个分支
	else
	{
		
//		strSQL = "select EdorNo,issuetype,issuecont,replyresult,operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(select codename from ldcode where codetype='bqbackobj' and comcode=BackObjType),needprint,serialno,makedate,(case ReplyMan when '' then (case when (select donedate from LOPRTManager where prtseq=a.prtseq and stateflag='2') is not null then (select donedate from LOPRTManager where prtseq=a.prtseq and stateflag='2') else null end) else modifydate end),OperatePos from lpissuepol a where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'EdorNo','EdorNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	  	
		
		 var  ContNo4 = window.document.getElementsByName(trim("ContNo"))[0].value;
	     var  EdorNo4 = window.document.getElementsByName(trim("EdorNo"))[0].value;
	     var  BackObj4 = window.document.getElementsByName(trim("BackObj"))[0].value;
	     var  ManageCom4 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	     var  OManageCom4 = window.document.getElementsByName(trim("OManageCom"))[0].value;
	     var  OperatePos4 = window.document.getElementsByName(trim("OperatePos"))[0].value;
	     var  Quest4 = window.document.getElementsByName(trim("Quest"))[0].value;
		 var sqlid4="BQQuestQuerySql4";
		 var mySql4=new SqlClass();
		 mySql4.setResourceName("uw.BQQuestQuerySql");
		 mySql4.setSqlId(sqlid4);//指定使用SQL的id
		 mySql4.addSubPara(k);//指定传入参数
		 mySql4.addSubPara(k);//指定传入参数
		 mySql4.addSubPara(manageCom);//指定传入参数
		 mySql4.addSubPara(ContNo4);//指定传入参数
		 mySql4.addSubPara(EdorNo4);//指定传入参数
		 mySql4.addSubPara(BackObj4);//指定传入参数
		 mySql4.addSubPara(ManageCom4);//指定传入参数
		 mySql4.addSubPara(OManageCom4);//指定传入参数
		 mySql4.addSubPara(OperatePos4);//指定传入参数
		 mySql4.addSubPara(Quest4);//指定传入参数
		 strSQL = mySql4.getString();
		
	}
//	strSQL = strSQL + " order by a.makedate,a.maketime ";
	
//	//prompt('',strSQL);
	
  //查询SQL,返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (turnPage.strQueryResult == false) {
    alert("没有问题件");
    return "";
  }
  
  //查询成功则拆分字符串,返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象,HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = QuestGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;	
}

function queryone(parm1,parm2)
{	
	k++;
	var strSQL = "";
	var tcho;
	var tOperatePos = fm.Flag.value;
	var tselno=QuestGrid.getSelNo()-1;
	//tongmeng 2007-10-22 modify
	//直接取操作位置。
	//tPos=QuestGrid.getRowColData(tselno,7);
	tPos=QuestGrid.getRowColData(tselno,13);	
	tQuest = QuestGrid.getRowColData(tselno,2);;
	tSerialNo = QuestGrid.getRowColData(tselno,10);;
	
	document.all('HideOperatePos').value=tPos;
	document.all('HideQuest').value=tQuest;
	document.all('HideSerialNo').value=tSerialNo;
	document.all('SerialNo').value=tSerialNo;
	tContNo = fm.ContNo.value;
	if (tPos == "")
	{
		alert("请选择问题件!")
		return "";
	}	
	
	if (tContNo == "")
	{
		alert("保单号不能为空！");
		return "";
	}
	if (tQuest == "")
	{
		alert("问题件不能为空！");
		return "";
	}
	if (tSerialNo == "")
	{
		alert("问题件编码不能为空！");
		return "";
	}
	
//	strSQL = "select issuecont,replyresult,issuetype,OperatePos,operator,makedate,modifydate from lpissuepol where "+k+"="+k+" "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'EdorNo','EdorNo' )
//				 + getWherePart( 'issuetype','HideQuest' )
//				 //+ getWherePart( 'OperatePos','HideOperatePos')
//				 + getWherePart( 'SerialNo','HideSerialNo');
//				 //+ getWherePart( 'BackObjType','BackObj')
//				 //+ getWherePart( 'ManageCom','ManageCom')
//				 //+ getWherePart( 'IssueManageCom','OManageCom')
	
				 if(document.all('HideOperatePos').value=="5")
				 {
					strSQL = strSQL + " and OperatePos in ('5','I','A')";
					
					 var  ContNo5 = window.document.getElementsByName(trim("ContNo"))[0].value;
				     var  EdorNo5 = window.document.getElementsByName(trim("EdorNo"))[0].value;				    
				     var  HideQuest5 = window.document.getElementsByName(trim("HideQuest"))[0].value;
				     var  HideSerialNo5 = window.document.getElementsByName(trim("HideSerialNo"))[0].value;
					 var sqlid5="BQQuestQuerySql5";
					 var mySql5=new SqlClass();
					 mySql5.setResourceName("uw.BQQuestQuerySql");
					 mySql5.setSqlId(sqlid5);//指定使用SQL的id
					 mySql5.addSubPara(k);//指定传入参数
					 mySql5.addSubPara(k);//指定传入参数
					 mySql5.addSubPara(ContNo5);//指定传入参数
					 mySql5.addSubPara(EdorNo5);//指定传入参数
					 mySql5.addSubPara(HideQuest5);//指定传入参数
					 mySql5.addSubPara(HideSerialNo5);//指定传入参数
					 strSQL = mySql5.getString();
				 }
				 else
				 {
				//	strSQL = strSQL + getWherePart( 'OperatePos','HideOperatePos');
					
					 var  ContNo6 = window.document.getElementsByName(trim("ContNo"))[0].value;
				     var  EdorNo6 = window.document.getElementsByName(trim("EdorNo"))[0].value;				    
				     var  HideQuest6 = window.document.getElementsByName(trim("HideQuest"))[0].value;
				     var  HideSerialNo6 = window.document.getElementsByName(trim("HideSerialNo"))[0].value;
				     var  HideOperatePos6 = window.document.getElementsByName(trim("HideOperatePos"))[0].value;
					 var sqlid6="BQQuestQuerySql6";
					 var mySql6=new SqlClass();
					 mySql6.setResourceName("uw.BQQuestQuerySql");
					 mySql6.setSqlId(sqlid6);//指定使用SQL的id
					 mySql6.addSubPara(k);//指定传入参数
					 mySql6.addSubPara(k);//指定传入参数
					 mySql6.addSubPara(ContNo6);//指定传入参数
					 mySql6.addSubPara(EdorNo6);//指定传入参数
					 mySql6.addSubPara(HideQuest6);//指定传入参数
					 mySql6.addSubPara(HideSerialNo6);//指定传入参数
					 mySql6.addSubPara(HideOperatePos6);//指定传入参数
					 strSQL = mySql6.getString();
				 }		 
				 

  //查询SQL,返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有录入过问题件！");
    return "";
  }
  
  //查询成功则拆分字符串,返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  var returnstr = "";
  var tcont = "";
  var treply = "";
  var ttype = "";
  var tOperatePos = "";
  var tOperator="";
  var tMakeDate = "";
  var tModifyDate="";
  var n = turnPage.arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
  	m = turnPage.arrDataCacheSet[0].length;
  		//alert("M:"+m);
  		if (m > 1)
  		{
  			//alert("turnPage:"+turnPage.arrDataCacheSet[0][0]);
			tcont = turnPage.arrDataCacheSet[0][0];
			treply = turnPage.arrDataCacheSet[0][1];
			ttype = turnPage.arrDataCacheSet[0][2];
			tOperatePos = turnPage.arrDataCacheSet[0][3];
			tOperator= turnPage.arrDataCacheSet[0][4];
			tMakeDate = turnPage.arrDataCacheSet[0][5];
			tModifyDate= turnPage.arrDataCacheSet[0][6];
  		}
  		else
  		{
  			alert("没有录入过问题件！");
  			return "";
  		}
  	
  }
  else
  {
  	alert("没有录入过问题件！");
	return "";
  }
 
  if (tcont == "")
  {
  	alert("没有录入过问题件！");
  	return "";
  }
  
  document.all('Content').value = tcont;
  
  if (treply == "") {
    document.all('ReplyResult').readOnly = false;
    canReplyFlag = true;
  }
  else {
    document.all('ReplyResult').readOnly = true;
    canReplyFlag = false;
  }
  document.all('ReplyResult').value = treply;
  
  document.all('Type').value = ttype;
  document.all('OperatePos').value = tOperatePos;
   document.all('Operator').value = tOperator;
  
  document.all('MakeDate').value = tMakeDate;
  //document.all('ReplyDate').value = tModifyDate;
  document.all('Type').value = ttype;
  
  return returnstr;
}

function input()
{
	cContNo = fm.ContNo.value;  //保单号码
	
	//showModalDialog("./QuestInputMain.jsp?ContNo1="+cContNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("./QuestInputMain.jsp?ContNo1="+cContNo+"&Flag="+cflag,"window1");
	
}

function quickReply() {
  if (QuestGrid.getSelNo() == "0") {
    alert("请先选择一条问题件！");
    return;
  }
  
  fm.ReplyResult.value = "问题修改完成！";
  reply();
}
function replySave()
{
		fm.QuesFlag.value = "allover";   //置"回复完毕"标志
		fm.action = "./QuestReplyChk.jsp";
		fm.submit();	
}
function reply()
{ 
	fm.QuesFlag.value = "";
  if (fm.ReplyResult.value == "") {
    alert("没有回复内容,不能回复！");  
    return;
  }
  
  if (!canReplyFlag) {
    alert("该问题件已回复,不能再次回复！");  
    return;
  }
  
  	cEdorNo = fm.EdorNo.value;
	cContNo = fm.ContNo.value;  //保单号码
	cQuest = fm.HideQuest.value;            //问题件类型
	cflag = fm.HideOperatePos.value;        //问题件操作位置 
	
	fm.ContNoHide.value = fm.ContNo.value;  //保单号码
	fm.Quest.value = fm.HideQuest.value;            //问题件类型
	fm.Flag.value = fm.HideOperatePos.value;        //问题件操作位置 
	fm.SerialNo.value = fm.HideSerialNo.value;
	if(cQuest == "")
	{
		alert("请选择要回复问题件!");
	}
	else
	{
		//showModalDialog("./QuestInputMain.jsp?ContNo1="+cContNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
		//window.open("./QuestReplyMain.jsp?ContNo1="+cContNo+"&Flag="+cflag+"&Quest="+cQuest,"window2");
		fm.action = "./BQQuestReplyChk.jsp";
		fm.submit();
	}
	
}


function QuestQuery()
{
	// 初始化表格
	var i,j,m,n;
	//initQuestGrid();
	
	
	// 书写SQL语句
	k++;
	var strSQL = "";
	//if (tFlag == "1")
	//{
//		strSQL = "select code,cont from ldcodemod where "+k+"="+k				 	
//				 + " and codetype = 'Question'";
	//}
	
	//alert(strSQL);
	
		 var sqlid7="BQQuestQuerySql7";
		 var mySql7=new SqlClass();
		 mySql7.setResourceName("uw.BQQuestQuerySql");
		 mySql7.setSqlId(sqlid7);//指定使用SQL的id
		 mySql7.addSubPara(k);//指定传入参数
		 mySql7.addSubPara(k);//指定传入参数
		 strSQL = mySql7.getString();
		
  //查询SQL,返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有问题件描述");
    return "";
  }
  
  //查询成功则拆分字符串,返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象,HealthGrid为在初始化页中定义的全局变量
  //turnPage.pageDisplayGrid = QuestGrid;    
          
  //保存SQL语句
  //turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  //turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  //var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  //displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;  
    if (n > 0)
  {
  	for( i = 0;i < n; i++)
  	{
  		m = turnPage.arrDataCacheSet[i].length;
  		//alert("M:"+m);
  		if (m > 0)
  		{
  			for( j = 0; j< m; j++)
  			{
  				if (i == 0 && j == 0)
  				{
  					returnstr = "0|^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i == 0 && j > 0)
  				{
  					returnstr = returnstr + "|" + turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j == 0)
  				{
  					returnstr = returnstr+"^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j > 0)
  				{
  					returnstr = returnstr+"|"+turnPage.arrDataCacheSet[i][j];
  				}
  				
  			}
  		}
  		else
  		{
  			alert("查询失败!!");
  			return "";
  		}
  	}
}
else
{
	alert("查询失败!");
	return "";
}
  //alert("returnstr:"+returnstr);		
  fm.Quest.CodeData = returnstr;
  return "";	
}


function QuestPicQuery(){
	//alert("建设中……");
	 var tsel=QuestGrid.getSelNo()-1; 
	 //alert(ContNo); 
	 if(tsel<0){
     alert("请选择保单!");
     retutn;	 
   }
   var ContNo=QuestGrid.getRowColData(tsel,1); 
	window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");								
 	
}

//问题件回复完毕函数
function finishMission()
{
	//判断是否所有需要回复的问题件都做了回复
//	var	strSQL = "select 1 from lpissuepol where 1=1 "				 	
//				 //+ " and OperatePos in ('0','1','5','6')"				 
//				 //+ " and IsueManageCom like '" + manageCom + "%%'"				
//				 + getWherePart( 'EdorNo','EdorNo' )
//				 + getWherePart( 'ContNo','ContNo' )
//				 //+ getWherePart( 'BackObjType','BackObj' )
//				 //+ getWherePart( 'ManageCom','ManageCom' )
//				 //+ getWherePart( 'IsueManageCom','OManageCom')
//				// + getWherePart( 'OperatePos','OperatePos')
//				 //+ getWherePart( 'IssueType','Quest')
//				 //+ " and replyresult is not null "
//				 + " and state is not null and state='0' "
//				 + " and backobjtype = '4' and replyman is null "
//				 + " and needprint='Y'"
//				 ;	  
	
	 var  EdorNo8 = window.document.getElementsByName(trim("EdorNo"))[0].value;
     var  ContNo8 = window.document.getElementsByName(trim("ContNo"))[0].value;
	 var sqlid8="BQQuestQuerySql8";
	 var mySql8=new SqlClass();
	 mySql8.setResourceName("uw.BQQuestQuerySql");
	 mySql8.setSqlId(sqlid8);//指定使用SQL的id
	 mySql8.addSubPara(EdorNo8);//指定传入参数
	 mySql8.addSubPara(ContNo8);//指定传入参数
	 var strSQL = mySql8.getString();
	
	var brr = easyExecSql(strSQL);
	//alert(strSQL);
	//alert(brr);
	if ( brr )  //存在没有保存的问题件.
	{
		alert("有未回复的机构问题件,请先回复");
		return false;
	}
	else
 	{
 		//alert(2);return false;
 		var showStr="正在保存数据,请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		fm.QuesFlag.value = "allover";
 		fm.action = "./BQQuestReplyChk.jsp";
 		document.all('hiddenOperate').value="finishMission";
 		fm.submit();
	}
	
}
