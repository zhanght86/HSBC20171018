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
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "uwgrp.QuestQuerySql";
//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  fm.submit(); //提交
}

//提交，保存按钮对应操作
function IfPrint()
{
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
　cContNo = fm.ContNo.value;  //保单号码	
　//var strSql = "select * from LCPol where PolNo='" + cContNo + "' and  approveflag = '2'";
  
  	var sqlid1="QuestQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(cContNo);
		
  
    var arrResult = easyExecSql(mySql1.getString());
       //alert(arrResult);
    if (arrResult != null) {     
   　　alert("已发核保通知书，但未打印,不容许在此时修改问题件的打印标志！");
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
    //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
  }
  else
  { 
	var showStr="打印标记更新成功";
  	alert(showStr);
  	//showInfo.close();
  	
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
  else 
  {
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


function manuchkspecmain()
{
	fm.submit();
}

function query()
{
	// 初始化表格
	initQuestGrid();
	initContent();

	// 书写SQL语句
	k++;
	var strSQL = "";
	var ifreply = fm.IfReply.value;
	var tOPos = fm.OperatePos.value;
	var tOperatePos = fm.Flag.value;
	var tContNo = fm.ContNo.value;

	if (tOperatePos == "")
	{
		alert("操作位置传输失败!");
		return "";
	}
	//if(tOPos == "")
	//{
	//	alert("操作位置不能为空！");
	//	return "";
	//}
	
	if (tOperatePos == "1"||tOperatePos == "3"||tOperatePos == "5")
	{
		if(tContNo == "")
		{
			alert("保单号不能为空!");
			return "";
		}
	}
	
	
	if (ifreply == "Y")
	{
	
	  if (tOperatePos == "1")
	  {
	  /*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "				 	
				 + " and OperatePos in ('0','1','5')"
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + " and ReplyMan is not null "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');
		*/		 
		var sqlid2="QuestQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(k);
		mySql2.addSubPara(k);
		mySql2.addSubPara(manageCom);
		mySql2.addSubPara(fm.ContNo.value);
		mySql2.addSubPara(fm.BackObj.value);
		mySql2.addSubPara(fm.ManageCom.value);
		mySql2.addSubPara(fm.OManageCom.value);
		mySql2.addSubPara(fm.OperatePos.value);
		mySql2.addSubPara(fm.Quest.value);
		
		strSQL = mySql2.getString();
	  }		
	  else if(tOperatePos == "2")
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "				 	
				 + " and OperatePos in ('1','5')"				
				 + " and IsueManageCom like '" + manageCom + "%%'"
				  + " and ReplyMan is not null "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest')
				 + " and replyresult is null";
		*/		 
				 
	   var sqlid3="QuestQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(k);
		mySql3.addSubPara(k);
		mySql3.addSubPara(manageCom);
		mySql3.addSubPara(fm.ContNo.value);
		mySql3.addSubPara(fm.BackObj.value);
		mySql3.addSubPara(fm.ManageCom.value);
		mySql3.addSubPara(fm.OManageCom.value);
		mySql3.addSubPara(fm.OperatePos.value);
		mySql3.addSubPara(fm.Quest.value);
		
		strSQL = mySql3.getString();
	  }
	  else if(tOperatePos == "3")
	  {
	  /*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "				 					 
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + " and ReplyMan is not null "
				 + " and ReplyResult is not null"
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');
				 
		*/		 
		 var sqlid4="QuestQuerySql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename);
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(k);
		mySql4.addSubPara(k);
		mySql4.addSubPara(manageCom);
		mySql4.addSubPara(fm.ContNo.value);
		mySql4.addSubPara(fm.BackObj.value);
		mySql4.addSubPara(fm.ManageCom.value);
		mySql4.addSubPara(fm.OManageCom.value);
		mySql4.addSubPara(fm.OperatePos.value);
		mySql4.addSubPara(fm.Quest.value);
		
		strSQL = mySql4.getString();
				
	  }
	  else if(tOperatePos == "4")
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "				 	
				 + " and OperatePos in ('0','1','5')"				 
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + " and ReplyMan is not null "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest')
				 + " and replyresult is null";	
		*/		 
	   var sqlid5="QuestQuerySql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(sqlresourcename);
		mySql5.setSqlId(sqlid5);
		mySql5.addSubPara(k);
		mySql5.addSubPara(k);
		mySql5.addSubPara(manageCom);
		mySql5.addSubPara(fm.ContNo.value);
		mySql5.addSubPara(fm.BackObj.value);
		mySql5.addSubPara(fm.ManageCom.value);
		mySql5.addSubPara(fm.OManageCom.value);
		mySql5.addSubPara(fm.OperatePos.value);
		mySql5.addSubPara(fm.Quest.value);
		
		strSQL = mySql5.getString();  
	  }
	  else if(tOperatePos == "5")
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint ,serialno from lcissuepol where "+k+"="+k+" "				 	
				 + " and OperatePos in ('0','1','5')"
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + " and ReplyMan is not null "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');	 
		*/		 
				 
		 var sqlid6="QuestQuerySql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(sqlresourcename);
		mySql6.setSqlId(sqlid6);
		mySql6.addSubPara(k);
		mySql6.addSubPara(k);
		mySql6.addSubPara(manageCom);
		mySql6.addSubPara(fm.ContNo.value);
		mySql6.addSubPara(fm.BackObj.value);
		mySql6.addSubPara(fm.ManageCom.value);
		mySql6.addSubPara(fm.OManageCom.value);
		mySql6.addSubPara(fm.OperatePos.value);
		mySql6.addSubPara(fm.Quest.value);
		
		strSQL = mySql6.getString();  	
	  }
	  else if(tOperatePos == "16")
	  {
	  /*
		strSQL = "select GrpContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint ,serialno from lcgrpissuepol where "+k+"="+k+" "
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + " and GrpContNo = (select grpcontno from lccont where contno = '"+tContNo+"')"
				 + " and ReplyMan is not null "
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');	 
		*/		 
				 
		var sqlid7="QuestQuerySql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName(sqlresourcename);
		mySql7.setSqlId(sqlid7);
		mySql7.addSubPara(k);
		mySql7.addSubPara(k);
		mySql7.addSubPara(manageCom);
		mySql7.addSubPara(tContNo);
		mySql7.addSubPara(fm.BackObj.value);
		mySql7.addSubPara(fm.ManageCom.value);
		mySql7.addSubPara(fm.OManageCom.value);
		mySql7.addSubPara(fm.OperatePos.value);
		mySql7.addSubPara(fm.Quest.value);
		
		strSQL = mySql7.getString();  			 
				  	
		divButton.style.display = "none";
	  }
	  else
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + " and ReplyMan is not null "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');
		*/		 
		var sqlid8="QuestQuerySql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename);
		mySql8.setSqlId(sqlid8);
		mySql8.addSubPara(k);
		mySql8.addSubPara(k);
		mySql8.addSubPara(manageCom);
		mySql8.addSubPara(fm.ContNo.value);
		mySql8.addSubPara(fm.BackObj.value);
		mySql8.addSubPara(fm.ManageCom.value);
		mySql8.addSubPara(fm.OManageCom.value);
		mySql8.addSubPara(fm.OperatePos.value);
		mySql8.addSubPara(fm.Quest.value);
		
		strSQL = mySql8.getString(); 
	  }	  	  	
}
 else if (ifreply == "N")
	{
		
	  if (tOperatePos == "1")
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "				 	
				 + " and OperatePos in ('0','1','5')"
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + " and ReplyMan is  null "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');
		*/		 
		var sqlid9="QuestQuerySql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName(sqlresourcename);
		mySql9.setSqlId(sqlid9);
		mySql9.addSubPara(k);
		mySql9.addSubPara(k);
		mySql9.addSubPara(manageCom);
		mySql9.addSubPara(fm.ContNo.value);
		mySql9.addSubPara(fm.BackObj.value);
		mySql9.addSubPara(fm.ManageCom.value);
		mySql9.addSubPara(fm.OManageCom.value);
		mySql9.addSubPara(fm.OperatePos.value);
		mySql9.addSubPara(fm.Quest.value);
		
		strSQL = mySql9.getString(); 
	  }		
	  else if(tOperatePos == "2")
	  {
	  /*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "				 	
				 + " and OperatePos in ('1','5')"				
				 + " and IsueManageCom like '" + manageCom + "%%'"
				  + " and ReplyMan is null "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest')
				 + " and replyresult is null";
		*/		 
		var sqlid10="QuestQuerySql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName(sqlresourcename);
		mySql10.setSqlId(sqlid10);
		mySql10.addSubPara(k);
		mySql10.addSubPara(k);
		mySql10.addSubPara(manageCom);
		mySql10.addSubPara(fm.ContNo.value);
		mySql10.addSubPara(fm.BackObj.value);
		mySql10.addSubPara(fm.ManageCom.value);
		mySql10.addSubPara(fm.OManageCom.value);
		mySql10.addSubPara(fm.OperatePos.value);
		mySql10.addSubPara(fm.Quest.value);
		
		strSQL = mySql10.getString(); 
	  }
	  else if(tOperatePos == "3")
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "				 					 
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + " and ReplyMan is  null "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');
		*/		 
				 
				 
	  var sqlid11="QuestQuerySql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName(sqlresourcename);
		mySql11.setSqlId(sqlid11);
		mySql11.addSubPara(k);
		mySql11.addSubPara(k);
		mySql11.addSubPara(manageCom);
		mySql11.addSubPara(fm.ContNo.value);
		mySql11.addSubPara(fm.BackObj.value);
		mySql11.addSubPara(fm.ManageCom.value);
		mySql11.addSubPara(fm.OManageCom.value);
		mySql11.addSubPara(fm.OperatePos.value);
		mySql11.addSubPara(fm.Quest.value);
		
		strSQL = mySql11.getString(); 
	  }
	  else if(tOperatePos == "4")
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "				 	
				 + " and OperatePos in ('0','1','5')"				 
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + " and ReplyMan is  null "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest')
				 + " and replyresult is null";	
		*/		 
	    var sqlid12="QuestQuerySql12";
		var mySql12=new SqlClass();
		mySql12.setResourceName(sqlresourcename);
		mySql12.setSqlId(sqlid12);
		mySql12.addSubPara(k);
		mySql12.addSubPara(k);
		mySql12.addSubPara(manageCom);
		mySql12.addSubPara(fm.ContNo.value);
		mySql12.addSubPara(fm.BackObj.value);
		mySql12.addSubPara(fm.ManageCom.value);
		mySql12.addSubPara(fm.OManageCom.value);
		mySql12.addSubPara(fm.OperatePos.value);
		mySql12.addSubPara(fm.Quest.value);
		
		strSQL = mySql12.getString();   
	  }
	  else if(tOperatePos == "5")
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint ,serialno from lcissuepol where "+k+"="+k+" "				 	
				 + " and OperatePos in ('0','1','5')"
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + " and ReplyMan is  null "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');	
				 */
		var sqlid13="QuestQuerySql13";
		var mySql13=new SqlClass();
		mySql13.setResourceName(sqlresourcename);
		mySql13.setSqlId(sqlid13);
		mySql13.addSubPara(k);
		mySql13.addSubPara(k);
		mySql13.addSubPara(manageCom);
		mySql13.addSubPara(fm.ContNo.value);
		mySql13.addSubPara(fm.BackObj.value);
		mySql13.addSubPara(fm.ManageCom.value);
		mySql13.addSubPara(fm.OManageCom.value);
		mySql13.addSubPara(fm.OperatePos.value);
		mySql13.addSubPara(fm.Quest.value);
		
		strSQL = mySql13.getString();     	
	  }
	  else if(tOperatePos == "16")
	  {/*
		strSQL = "select GrpContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint ,serialno from lcgrpissuepol where "+k+"="+k+" "
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + " and GrpContNo = (select grpcontno from lccont where contno = '"+tContNo+"')"
				  + " and ReplyMan is  null "
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');	
				 
		*/		 
		var sqlid14="QuestQuerySql14";
		var mySql14=new SqlClass();
		mySql14.setResourceName(sqlresourcename);
		mySql14.setSqlId(sqlid14);
		mySql14.addSubPara(k);
		mySql14.addSubPara(k);
		mySql14.addSubPara(manageCom);
		mySql14.addSubPara(tContNo);
		mySql14.addSubPara(fm.BackObj.value);
		mySql14.addSubPara(fm.ManageCom.value);
		mySql14.addSubPara(fm.OManageCom.value);
		mySql14.addSubPara(fm.OperatePos.value);
		mySql14.addSubPara(fm.Quest.value);  
		
		strSQL = mySql14.getString(); 
			
		divButton.style.display = "none";
	  }
	  else
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');
		*/		 
				 
				 
	    var sqlid15="QuestQuerySql15";
		var mySql15=new SqlClass();
		mySql15.setResourceName(sqlresourcename);
		mySql15.setSqlId(sqlid15);
		mySql15.addSubPara(k);
		mySql15.addSubPara(k);
		mySql15.addSubPara(manageCom);
		mySql15.addSubPara(fm.ContNo.value);
		mySql15.addSubPara(fm.BackObj.value);
		mySql15.addSubPara(fm.ManageCom.value);
		mySql15.addSubPara(fm.OManageCom.value);
		mySql15.addSubPara(fm.OperatePos.value);
		mySql15.addSubPara(fm.Quest.value);  
		
		strSQL = mySql15.getString(); 
	  }	  	  	
}

else
	{
		
	  if (tOperatePos == "1")
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "				 	
				 + " and OperatePos in ('0','1','5')"
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');
		*/		 
		var sqlid16="QuestQuerySql16";
		var mySql16=new SqlClass();
		mySql16.setResourceName(sqlresourcename);
		mySql16.setSqlId(sqlid16);
		mySql16.addSubPara(k);
		mySql16.addSubPara(k);
		mySql16.addSubPara(manageCom);
		mySql16.addSubPara(fm.ContNo.value);
		mySql16.addSubPara(fm.BackObj.value);
		mySql16.addSubPara(fm.ManageCom.value);
		mySql16.addSubPara(fm.OManageCom.value);
		mySql16.addSubPara(fm.OperatePos.value);
		mySql16.addSubPara(fm.Quest.value);  
		
		strSQL = mySql16.getString(); 
	  }		
	  else if(tOperatePos == "2")
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "				 	
				 + " and OperatePos in ('1','5')"
				 + " and backobjtype = '1'"
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest')
				 + " and replyresult is null";
		*/		 
		var sqlid17="QuestQuerySql17";
		var mySql17=new SqlClass();
		mySql17.setResourceName(sqlresourcename);
		mySql17.setSqlId(sqlid17);
		mySql17.addSubPara(k);
		mySql17.addSubPara(k);
		mySql17.addSubPara(manageCom);
		mySql17.addSubPara(fm.ContNo.value);
		mySql17.addSubPara(fm.BackObj.value);
		mySql17.addSubPara(fm.ManageCom.value);
		mySql17.addSubPara(fm.OManageCom.value);
		mySql17.addSubPara(fm.OperatePos.value);
		mySql17.addSubPara(fm.Quest.value);  
		
		strSQL = mySql17.getString(); 
	  }
	  else if(tOperatePos == "3")
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "				 					 
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');
		*/		 
	    var sqlid18="QuestQuerySql18";
		var mySql18=new SqlClass();
		mySql18.setResourceName(sqlresourcename);
		mySql18.setSqlId(sqlid18);
		mySql18.addSubPara(k);
		mySql18.addSubPara(k);
		mySql18.addSubPara(manageCom);
		mySql18.addSubPara(fm.ContNo.value);
		mySql18.addSubPara(fm.BackObj.value);
		mySql18.addSubPara(fm.ManageCom.value);
		mySql18.addSubPara(fm.OManageCom.value);
		mySql18.addSubPara(fm.OperatePos.value);
		mySql18.addSubPara(fm.Quest.value);  
		
		strSQL = mySql18.getString(); 
	  }
	  else if(tOperatePos == "4")
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "				 	
				 + " and OperatePos in ('0','1','5')"				 
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest')
				 + " and replyresult is null";	
	    */
	    var sqlid19="QuestQuerySql19";
		var mySql19=new SqlClass();
		mySql19.setResourceName(sqlresourcename);
		mySql19.setSqlId(sqlid19);
		mySql19.addSubPara(k);
		mySql19.addSubPara(k);
		mySql19.addSubPara(manageCom);
		mySql19.addSubPara(fm.ContNo.value);
		mySql19.addSubPara(fm.BackObj.value);
		mySql19.addSubPara(fm.ManageCom.value);
		mySql19.addSubPara(fm.OManageCom.value);
		mySql19.addSubPara(fm.OperatePos.value);
		mySql19.addSubPara(fm.Quest.value);  
		
		strSQL = mySql19.getString();   
	  }
	  else if(tOperatePos == "5")
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint ,serialno from lcissuepol where "+k+"="+k+" "				 	
				 + " and OperatePos in ('0','1','5')"
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');	
				 */
		var sqlid20="QuestQuerySql20";
		var mySql20=new SqlClass();
		mySql20.setResourceName(sqlresourcename);
		mySql20.setSqlId(sqlid20);
		mySql20.addSubPara(k);
		mySql20.addSubPara(k);
		mySql20.addSubPara(manageCom);
		mySql20.addSubPara(fm.ContNo.value);
		mySql20.addSubPara(fm.BackObj.value);
		mySql20.addSubPara(fm.ManageCom.value);
		mySql20.addSubPara(fm.OManageCom.value);
		mySql20.addSubPara(fm.OperatePos.value);
		mySql20.addSubPara(fm.Quest.value);  
		
		strSQL = mySql20.getString();   	
	  }
	  else if(tOperatePos == "16")
	  {/*
		strSQL = "select GrpContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint ,serialno from lcgrpissuepol where "+k+"="+k+" "
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + " and GrpContNo = (select grpcontno from lccont where contno = '"+tContNo+"')"
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');	
		*/		 
		var sqlid21="QuestQuerySql21";
		var mySql21=new SqlClass();
		mySql21.setResourceName(sqlresourcename);
		mySql21.setSqlId(sqlid21);
		mySql21.addSubPara(k);
		mySql21.addSubPara(k);
		mySql21.addSubPara(manageCom);
		mySql21.addSubPara(tContNo);
		mySql21.addSubPara(fm.BackObj.value);
		mySql21.addSubPara(fm.ManageCom.value);
		mySql21.addSubPara(fm.OManageCom.value);
		mySql21.addSubPara(fm.OperatePos.value);
		mySql21.addSubPara(fm.Quest.value);  
		
		strSQL = mySql21.getString();   	
		divButton.style.display = "none";
	  }
	  else
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');
				 */
		var sqlid22="QuestQuerySql22";
		var mySql22=new SqlClass();
		mySql22.setResourceName(sqlresourcename);
		mySql22.setSqlId(sqlid22);
		mySql22.addSubPara(k);
		mySql22.addSubPara(k);
		mySql22.addSubPara(manageCom);
		mySql22.addSubPara(tContNo);
		mySql22.addSubPara(fm.BackObj.value);
		mySql22.addSubPara(fm.ManageCom.value);
		mySql22.addSubPara(fm.OManageCom.value);
		mySql22.addSubPara(fm.OperatePos.value);
		mySql22.addSubPara(fm.Quest.value);  
		
		strSQL = mySql22.getString();   
	  }	  	  	
}
	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (turnPage.strQueryResult == false) {
    alert("没有问题件");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
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
	
	// 书写SQL语句
	//alert("begin");
	k++;

	var strSQL = "";
	var tcho;
	var tOperatePos = fm.Flag.value;
	if(fm.all(parm1).all('InpQuestGridSel').value == '1' )
	{
	//当前行第1列的值设为：选中
   		tPos = fm.all(parm1).all('QuestGrid7').value;
   		tQuest = fm.all(parm1).all('QuestGrid2').value;
   		tSerialNo = fm.all(parm1).all('QuestGrid10').value;
  	}
  	
	fm.all('HideOperatePos').value=tPos;
	fm.all('HideQuest').value=tQuest;
    fm.all('HideSerialNo').value=tSerialNo;
    fm.all('SerialNo').value=tSerialNo;
	tContNo = fm.ContNo.value;
	//tQuest = fm.Quest.value;
	//tPos = fm.OperatePos.value;
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
	
	if (tOperatePos == "16")
	{
	
		//strSQL = "select issuecont,replyresult,issuetype,OperatePos from lcgrpissuepol where "+k+"="+k+" "
		//				+ " and grpcontno = (select grpcontno from lccont where contno = '"+tContNo+"')"
		
		var sqlid23="QuestQuerySql23";
		var mySql23=new SqlClass();
		mySql23.setResourceName(sqlresourcename);
		mySql23.setSqlId(sqlid23);
		mySql23.addSubPara(k);
		mySql23.addSubPara(k);
		mySql23.addSubPara(tContNo);

		strSQL = mySql23.getString();   
		//alert(strSQL);
	}
	else
	{/*
		strSQL = "select issuecont,replyresult,issuetype,OperatePos,operator,makedate,modifydate from lcissuepol where "+k+"="+k+" "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'issuetype','HideQuest' )
				 + getWherePart( 'OperatePos','HideOperatePos')
				 + getWherePart( 'SerialNo','HideSerialNo');
		*/		 
		var sqlid24="QuestQuerySql24";
		var mySql24=new SqlClass();
		mySql24.setResourceName(sqlresourcename);
		mySql24.setSqlId(sqlid24);
		mySql24.addSubPara(k);
		mySql24.addSubPara(k);
		mySql24.addSubPara(fm.ContNo.value);
		mySql24.addSubPara(fm.HideQuest.value);
		mySql24.addSubPara(fm.HideOperatePos.value);
		mySql24.addSubPara(fm.HideSerialNo.value);

		strSQL = mySql24.getString(); 
				 //+ getWherePart( 'BackObjType','BackObj')
				 //+ getWherePart( 'ManageCom','ManageCom')
				 //+ getWherePart( 'IssueManageCom','OManageCom')
	}
	
	//alert(strSQL);
	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有录入过问题件！");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
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
  
  fm.all('Content').value = tcont;
  
  if (treply == "") {
    fm.all('replyresult').readOnly = false;
    canReplyFlag = true;
  }
  else {
    fm.all('replyresult').readOnly = true;
    canReplyFlag = false;
  }
  fm.all('replyresult').value = treply;
  
  fm.all('Type').value = ttype;
  fm.all('OperatePos').value = tOperatePos;
   fm.all('Operator').value = tOperator;
  
  fm.all('MakeDate').value = tMakeDate;
  fm.all('ReplyDate').value = tModifyDate;
  return returnstr;
}

function input()
{
	cContNo = fm.ContNo.value;  //保单号码
	
	//showModalDialog("./QuestInputMain.jsp?ContNo1="+cContNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("./QuestInputMain.jsp?ContNo1="+cContNo+"&Flag="+cflag,"window1",sFeatures);
	
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
    alert("没有回复内容，不能回复！");  
    return;
  }
  
  if (!canReplyFlag) {
    alert("该问题件已回复，不能再次回复！");  
    return;
  }
  
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
		fm.action = "./QuestReplyChk.jsp";
		fm.submit();
	}
	
}

function QuestQuery(tContNo, tFlag)
{
	// 初始化表格
	var i,j,m,n;
	//initQuestGrid();
	
	
	// 书写SQL语句
	k++;
	var strSQL = "";
	//if (tFlag == "1")
	//{
		//strSQL = "select code,cont from ldcodemod where "+k+"="+k				 	
			//	 + " and codetype = 'Question'";
	//}
	  var sqlid25="QuestQuerySql25";
		var mySql25=new SqlClass();
		mySql25.setResourceName(sqlresourcename);
		mySql25.setSqlId(sqlid25);
		mySql25.addSubPara(k);
		mySql25.addSubPara(k);
		
	//alert(strSQL);
	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(mySql25.getString(), 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有问题件描述");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
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
	alert("建设中……");
	 var tsel=QuestGrid.getSelNo()-1; 
	 //alert(ContNo); 
	 if(tsel<0){
     alert("请选择保单!");
     retutn;	 
   }
   var ContNo=QuestGrid.getRowColData(tsel,1); 
	window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");								
    
	
 	
}
