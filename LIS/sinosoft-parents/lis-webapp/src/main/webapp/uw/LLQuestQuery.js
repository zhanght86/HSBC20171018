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
　cContNo = fm.ContNo.value;  //保单号码	
　var strSql = "select * from LCPol where PolNo='" + cContNo + "' and  approveflag = '2'";
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
    if(document.all('hiddenOperate').value!=null&&document.all('hiddenOperate').value=='finishMission')
    {
    	showInfo.close();
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
	//alert(113);
	// 书写SQL语句
	k++;
	var strSQL = "";
	var ifreply = fm.IfReply.value;
	var tOPos = fm.OperatePos.value;
	
	var tContNo = fm.ContNo.value;
	var tClmNo = fm.ClmNo.value;
	var tBatNo = fm.BatNo.value;
	
	if (tFlag == "")
	{
		alert("操作位置传输失败!");
		return "";
	}
	
	if (tFlag == "1"||tFlag == "3"||tFlag == "5")
	{
		if(tContNo == "")
		{
			alert("保单号不能为空!");
			return "";
		}
	}
	//alert(ifreply);
	//tongmeng 2007-10-22 modify
	//状态判断有误承保录入pos为1
	//人工核保不发放问题件
	//alert("ifreply "+ifreply);
	if (ifreply == "Y")
	{
	
	  if (tFlag == "1")
	  {
	//	strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(case OperatePos when '0' then '新单录入/复核修改/问题修改' when '1' then '人工核保' when '5' then '新单复核' when '3' then '问题件修改岗' when '3' then '问题件修改岗'  when 'A' then '新单复核' when 'I' then '新单复核' else OperatePos end ),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from lcissuepol where "+k+"="+k+" "				 	
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from lcissuepol where "+k+"="+k+" "				 	
//
//				 + " and OperatePos in ('0','1','3','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is not null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		
		var sqlid1="LLQuestQuerySql1";
	 	var mySql1=new SqlClass();
	 	mySql1.setResourceName("uw.LLQuestQuerySql");
	 	mySql1.setSqlId(sqlid1); //指定使用SQL的id
	 	mySql1.addSubPara(k);//指定传入参数
	 	mySql1.addSubPara(k);
	 	mySql1.addSubPara(manageCom);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("ContNo"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("BackObj"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("OManageCom"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("OperatePos"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("Quest"))[0].value);
	 	strSQL = mySql1.getString();
		
		
	  }		
	  else if(tFlag == "2")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('1','5')"				
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				  + " and ReplyMan is not null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest')
//				 + " and replyresult is null";
//		
		var sqlid2="LLQuestQuerySql2";
	 	var mySql2=new SqlClass();
	 	mySql2.setResourceName("uw.LLQuestQuerySql");
	 	mySql2.setSqlId(sqlid2); //指定使用SQL的id
	 	mySql2.addSubPara(k);//指定传入参数
	 	mySql2.addSubPara(k);
	 	mySql2.addSubPara(manageCom);
	 	mySql2.addSubPara(window.document.getElementsByName(trim("ContNo"))[0].value);
	 	mySql2.addSubPara(window.document.getElementsByName(trim("BackObj"))[0].value);
	 	mySql2.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
	 	mySql2.addSubPara(window.document.getElementsByName(trim("OManageCom"))[0].value);
	 	mySql2.addSubPara(window.document.getElementsByName(trim("OperatePos"))[0].value);
	 	mySql2.addSubPara(window.document.getElementsByName(trim("Quest"))[0].value);
	 	strSQL = mySql2.getString();
		
		
	  }
	  else if(tFlag == "3")
	  {
	  
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is not null "
//				 + " and ReplyResult is not null"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		
		var sqlid3="LLQuestQuerySql3";
	 	var mySql3=new SqlClass();
	 	mySql3.setResourceName("uw.LLQuestQuerySql");
	 	mySql3.setSqlId(sqlid3); //指定使用SQL的id
	 	mySql3.addSubPara(k);//指定传入参数
	 	mySql3.addSubPara(k);
	 	mySql3.addSubPara(manageCom);
	 	mySql3.addSubPara(window.document.getElementsByName(trim("ContNo"))[0].value);
	 	mySql3.addSubPara(window.document.getElementsByName(trim("BackObj"))[0].value);
	 	mySql3.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
	 	mySql3.addSubPara(window.document.getElementsByName(trim("OManageCom"))[0].value);
	 	mySql3.addSubPara(window.document.getElementsByName(trim("OperatePos"))[0].value);
	 	mySql3.addSubPara(window.document.getElementsByName(trim("Quest"))[0].value);
	 	strSQL = mySql3.getString();
		
	  }
	  else if(tFlag == "4")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','5','A','I','6')"				 
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is not null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest')
//				 + " and replyresult is null";	  
		
		var sqlid4="LLQuestQuerySql4";
	 	var mySql4=new SqlClass();
	 	mySql4.setResourceName("uw.LLQuestQuerySql");
	 	mySql4.setSqlId(sqlid4); //指定使用SQL的id
	 	mySql4.addSubPara(k);//指定传入参数
	 	mySql4.addSubPara(k);
	 	mySql4.addSubPara(manageCom);
	 	mySql4.addSubPara(window.document.getElementsByName(trim("ContNo"))[0].value);
	 	mySql4.addSubPara(window.document.getElementsByName(trim("BackObj"))[0].value);
	 	mySql4.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
	 	mySql4.addSubPara(window.document.getElementsByName(trim("OManageCom"))[0].value);
	 	mySql4.addSubPara(window.document.getElementsByName(trim("OperatePos"))[0].value);
	 	mySql4.addSubPara(window.document.getElementsByName(trim("Quest"))[0].value);
	 	strSQL = mySql4.getString();
		
	  }
	  else if(tFlag == "5")
	  {
		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is not null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	
		
		var sqlid5="LLQuestQuerySql5";
	 	var mySql5=new SqlClass();
	 	mySql5.setResourceName("uw.LLQuestQuerySql");
	 	mySql5.setSqlId(sqlid5); //指定使用SQL的id
	 	mySql5.addSubPara(k);//指定传入参数
	 	mySql5.addSubPara(k);
	 	mySql5.addSubPara(manageCom);
	 	mySql5.addSubPara(window.document.getElementsByName(trim("ContNo"))[0].value);
	 	mySql5.addSubPara(window.document.getElementsByName(trim("BackObj"))[0].value);
	 	mySql5.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
	 	mySql5.addSubPara(window.document.getElementsByName(trim("OManageCom"))[0].value);
	 	mySql5.addSubPara(window.document.getElementsByName(trim("OperatePos"))[0].value);
	 	mySql5.addSubPara(window.document.getElementsByName(trim("Quest"))[0].value);
	 	strSQL = mySql5.getString();
		
	  }
	  else if(tFlag == "16")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and GrpContNo = (select grpcontno from lccont where contno = '"+tContNo+"')"
//				 + " and ReplyMan is not null "
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	 
		
		var sqlid6="LLQuestQuerySql6";
	 	var mySql6=new SqlClass();
	 	mySql6.setResourceName("uw.LLQuestQuerySql");
	 	mySql6.setSqlId(sqlid6); //指定使用SQL的id
	 	mySql6.addSubPara(k);//指定传入参数
	 	mySql6.addSubPara(k);
	 	mySql6.addSubPara(manageCom);
	 	mySql6.addSubPara(tContNo);
	 	mySql6.addSubPara(window.document.getElementsByName(trim("BackObj"))[0].value);
	 	mySql6.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
	 	mySql6.addSubPara(window.document.getElementsByName(trim("OManageCom"))[0].value);
	 	mySql6.addSubPara(window.document.getElementsByName(trim("OperatePos"))[0].value);
	 	mySql6.addSubPara(window.document.getElementsByName(trim("Quest"))[0].value);
	 	strSQL = mySql6.getString();
		
		divButton.style.display = "none";
	  }
	  else
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is not null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		
		var sqlid7="LLQuestQuerySql7";
	 	var mySql7=new SqlClass();
	 	mySql7.setResourceName("uw.LLQuestQuerySql");
	 	mySql7.setSqlId(sqlid7); //指定使用SQL的id
	 	mySql7.addSubPara(k);//指定传入参数
	 	mySql7.addSubPara(k);
	 	mySql7.addSubPara(manageCom);
	 	mySql7.addSubPara(window.document.getElementsByName(trim("ContNo"))[0].value);
	 	mySql7.addSubPara(window.document.getElementsByName(trim("BackObj"))[0].value);
	 	mySql7.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
	 	mySql7.addSubPara(window.document.getElementsByName(trim("OManageCom"))[0].value);
	 	mySql7.addSubPara(window.document.getElementsByName(trim("OperatePos"))[0].value);
	 	mySql7.addSubPara(window.document.getElementsByName(trim("Quest"))[0].value);
	 	strSQL = mySql7.getString();
		
		
	  }	  	  	
}
 else if (ifreply == "N")
	{
	  if (tFlag == "1")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','3','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is  null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		
		var sqlid8="LLQuestQuerySql8";
	 	var mySql8=new SqlClass()
	 	mySql8.setResourceName("uw.LLQuestQuerySql");
	 	mySql8.setSqlId(sqlid8); //指定使用SQL的id
	 	mySql8.addSubPara(k);//指定传入参数
	 	mySql8.addSubPara(k);
	 	mySql8.addSubPara(manageCom);
	 	mySql8.addSubPara(window.document.getElementsByName(trim("ContNo"))[0].value);
	 	mySql8.addSubPara(window.document.getElementsByName(trim("BackObj"))[0].value);
	 	mySql8.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
	 	mySql8.addSubPara(window.document.getElementsByName(trim("OManageCom"))[0].value);
	 	mySql8.addSubPara(window.document.getElementsByName(trim("OperatePos"))[0].value);
	 	mySql8.addSubPara(window.document.getElementsByName(trim("Quest"))[0].value);
	 	strSQL = mySql8.getString();
		
	  }
	  else if(tFlag == "2")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('1','5')"				
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				  + " and ReplyMan is null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest')
//				 + " and replyresult is null";
		
		var sqlid9="LLQuestQuerySql9";
	 	var mySql9=new SqlClass()
	 	mySql9.setResourceName("uw.LLQuestQuerySql");
	 	mySql9.setSqlId(sqlid9); //指定使用SQL的id
	 	mySql9.addSubPara(k);//指定传入参数
	 	mySql9.addSubPara(k);
	 	mySql9.addSubPara(manageCom);
	 	mySql9.addSubPara(window.document.getElementsByName(trim("ContNo"))[0].value);
	 	mySql9.addSubPara(window.document.getElementsByName(trim("BackObj"))[0].value);
	 	mySql9.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
	 	mySql9.addSubPara(window.document.getElementsByName(trim("OManageCom"))[0].value);
	 	mySql9.addSubPara(window.document.getElementsByName(trim("OperatePos"))[0].value);
	 	mySql9.addSubPara(window.document.getElementsByName(trim("Quest"))[0].value);
	 	strSQL = mySql9.getString();
		
	  }
	  else if(tFlag == "3")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is  null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		
		var sqlid10="LLQuestQuerySql10";
	 	var mySql10=new SqlClass()
	 	mySql10.setResourceName("uw.LLQuestQuerySql");
	 	mySql10.setSqlId(sqlid10); //指定使用SQL的id
	 	mySql10.addSubPara(k);//指定传入参数
	 	mySql10.addSubPara(k);
	 	mySql10.addSubPara(manageCom);
	 	mySql10.addSubPara(window.document.getElementsByName(trim("ContNo"))[0].value);
	 	mySql10.addSubPara(window.document.getElementsByName(trim("BackObj"))[0].value);
	 	mySql10.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
	 	mySql10.addSubPara(window.document.getElementsByName(trim("OManageCom"))[0].value);
	 	mySql10.addSubPara(window.document.getElementsByName(trim("OperatePos"))[0].value);
	 	mySql10.addSubPara(window.document.getElementsByName(trim("Quest"))[0].value);
	 	strSQL = mySql10.getString();
		
	  }
	  else if(tFlag == "4")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','5','A','I','6')"				 
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is  null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest')
//				 + " and replyresult is null";	
		
		var sqlid11="LLQuestQuerySql11";
	 	var mySql11=new SqlClass()
	 	mySql11.setResourceName("uw.LLQuestQuerySql");
	 	mySql11.setSqlId(sqlid11); //指定使用SQL的id
	 	mySql11.addSubPara(k);//指定传入参数
	 	mySql11.addSubPara(k);
	 	mySql11.addSubPara(manageCom);
	 	mySql11.addSubPara(window.document.getElementsByName(trim("ContNo"))[0].value);
	 	mySql11.addSubPara(window.document.getElementsByName(trim("BackObj"))[0].value);
	 	mySql11.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
	 	mySql11.addSubPara(window.document.getElementsByName(trim("OManageCom"))[0].value);
	 	mySql11.addSubPara(window.document.getElementsByName(trim("OperatePos"))[0].value);
	 	mySql11.addSubPara(window.document.getElementsByName(trim("Quest"))[0].value);
	 	strSQL = mySql11.getString();
		
	  }
	  else if(tFlag == "5")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is  null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	
		
		var sqlid13="LLQuestQuerySql13";
	 	var mySql13=new SqlClass()
	 	mySql13.setResourceName("uw.LLQuestQuerySql");
	 	mySql13.setSqlId(sqlid13); //指定使用SQL的id
	 	mySql13.addSubPara(k);//指定传入参数
	 	mySql13.addSubPara(k);
	 	mySql13.addSubPara(manageCom);
	 	mySql13.addSubPara(window.document.getElementsByName(trim("ContNo"))[0].value);
	 	mySql13.addSubPara(window.document.getElementsByName(trim("BackObj"))[0].value);
	 	mySql13.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
	 	mySql13.addSubPara(window.document.getElementsByName(trim("OManageCom"))[0].value);
	 	mySql13.addSubPara(window.document.getElementsByName(trim("OperatePos"))[0].value);
	 	mySql13.addSubPara(window.document.getElementsByName(trim("Quest"))[0].value);
	 	strSQL = mySql13.getString();
		
	  }
	  else if(tFlag == "16")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and GrpContNo = (select grpcontno from lccont where contno = '"+tContNo+"')"
//				  + " and ReplyMan is  null "
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	
		
		var sqlid12="LLQuestQuerySql12";
	 	var mySql12=new SqlClass()
	 	mySql12.setResourceName("uw.LLQuestQuerySql");
	 	mySql12.setSqlId(sqlid12); //指定使用SQL的id
	 	mySql12.addSubPara(k);//指定传入参数
	 	mySql12.addSubPara(k);
	 	mySql12.addSubPara(manageCom);
	 	mySql12.addSubPara(tContNo);
	 	mySql12.addSubPara(window.document.getElementsByName(trim("BackObj"))[0].value);
	 	mySql12.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
	 	mySql12.addSubPara(window.document.getElementsByName(trim("OManageCom"))[0].value);
	 	mySql12.addSubPara(window.document.getElementsByName(trim("OperatePos"))[0].value);
	 	mySql12.addSubPara(window.document.getElementsByName(trim("Quest"))[0].value);
	 	strSQL = mySql12.getString();
		
		
		divButton.style.display = "none";
	  }
	  else
	  {
	    //alert(1);
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is  null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		
		var sqlid14="LLQuestQuerySql14";
	 	var mySql14=new SqlClass()
	 	mySql14.setResourceName("uw.LLQuestQuerySql");
	 	mySql14.setSqlId(sqlid14); //指定使用SQL的id
	 	mySql14.addSubPara(k);//指定传入参数
	 	mySql14.addSubPara(k);
	 	mySql14.addSubPara(manageCom);
	 	mySql14.addSubPara(window.document.getElementsByName(trim("ContNo"))[0].value);
	 	mySql14.addSubPara(window.document.getElementsByName(trim("BackObj"))[0].value);
	 	mySql14.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
	 	mySql14.addSubPara(window.document.getElementsByName(trim("OManageCom"))[0].value);
	 	mySql14.addSubPara(window.document.getElementsByName(trim("OperatePos"))[0].value);
	 	mySql14.addSubPara(window.document.getElementsByName(trim("Quest"))[0].value);
	 	strSQL = mySql14.getString();
		
	  }	  	  	
}
//走这个分支
else
	{
		//alert(tOperatePos);
	  if (tFlag == "1")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,('理赔'),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,(case ReplyMan when '' then '' else modifydate end),OperatePos"
////				 +",nvl(errflag,'N') "
//				 + " from llissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('4')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//
////				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'ClmNo','ClmNo' )
//				 //+ getWherePart( 'BatNo','BatNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		
		var sqlid15="LLQuestQuerySql15";
	 	var mySql15=new SqlClass()
	 	mySql15.setResourceName("uw.LLQuestQuerySql");
	 	mySql15.setSqlId(sqlid15); //指定使用SQL的id
	 	mySql15.addSubPara(k);//指定传入参数
	 	mySql15.addSubPara(k);
	 	mySql15.addSubPara(manageCom);
	 	mySql15.addSubPara(window.document.getElementsByName(trim("ClmNo"))[0].value);
	 	mySql15.addSubPara(window.document.getElementsByName(trim("BackObj"))[0].value);
	 	mySql15.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
	 	mySql15.addSubPara(window.document.getElementsByName(trim("OManageCom"))[0].value);
	 	mySql15.addSubPara(window.document.getElementsByName(trim("OperatePos"))[0].value);
	 	mySql15.addSubPara(window.document.getElementsByName(trim("Quest"))[0].value);
	 	strSQL = mySql15.getString();
		
	  }		
	  else if(tFlag == "2")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,(case ReplyMan when '' then '' else modifydate end),OperatePos,( case when errflag is not null then errflag else 'N' end) from lcissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('1','5')"
//				 + " and backobjtype = '1'"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest')
//				 + " and replyresult is null";
		
		var sqlid16="LLQuestQuerySql16";
	 	var mySql16=new SqlClass()
	 	mySql16.setResourceName("uw.LLQuestQuerySql");
	 	mySql16.setSqlId(sqlid16); //指定使用SQL的id
	 	mySql16.addSubPara(k);//指定传入参数
	 	mySql16.addSubPara(k);
	 	mySql16.addSubPara(manageCom);
	 	mySql16.addSubPara(window.document.getElementsByName(trim("ContNo"))[0].value);
	 	mySql16.addSubPara(window.document.getElementsByName(trim("BackObj"))[0].value);
	 	mySql16.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
	 	mySql16.addSubPara(window.document.getElementsByName(trim("OManageCom"))[0].value);
	 	mySql16.addSubPara(window.document.getElementsByName(trim("OperatePos"))[0].value);
	 	mySql16.addSubPara(window.document.getElementsByName(trim("Quest"))[0].value);
	 	strSQL = mySql16.getString();
		
	  }
	  else if(tFlag == "3")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,(case ReplyMan when '' then '' else modifydate end),OperatePos,(case when errflag is not null then errflag else 'N' end) from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and state is not null "
//				 //+ " and replyresult is null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		
		var sqlid17="LLQuestQuerySql17";
	 	var mySql17=new SqlClass()
	 	mySql17.setResourceName("uw.LLQuestQuerySql");
	 	mySql17.setSqlId(sqlid17); //指定使用SQL的id
	 	mySql17.addSubPara(k);//指定传入参数
	 	mySql17.addSubPara(k);
	 	mySql17.addSubPara(manageCom);
	 	mySql17.addSubPara(window.document.getElementsByName(trim("ContNo"))[0].value);
	 	mySql17.addSubPara(window.document.getElementsByName(trim("BackObj"))[0].value);
	 	mySql17.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
	 	mySql17.addSubPara(window.document.getElementsByName(trim("OManageCom"))[0].value);
	 	mySql17.addSubPara(window.document.getElementsByName(trim("OperatePos"))[0].value);
	 	mySql17.addSubPara(window.document.getElementsByName(trim("Quest"))[0].value);
	 	strSQL = mySql17.getString();
		
	  }
	  else if(tFlag == "4")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos) ),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,(case ReplyMan when '' then '' else modifydate end),OperatePos,(case when errflag is not null then errflag else 'N' end) from lcissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','3','5','6')"				 
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest')
//				 //+ " and replyresult is null"
//				 ;	  
		
		var sqlid18="LLQuestQuerySql18";
	 	var mySql18=new SqlClass()
	 	mySql18.setResourceName("uw.LLQuestQuerySql");
	 	mySql18.setSqlId(sqlid18); //指定使用SQL的id
	 	mySql18.addSubPara(k);//指定传入参数
	 	mySql18.addSubPara(k);
	 	mySql18.addSubPara(manageCom);
	 	mySql18.addSubPara(window.document.getElementsByName(trim("ContNo"))[0].value);
	 	mySql18.addSubPara(window.document.getElementsByName(trim("BackObj"))[0].value);
	 	mySql18.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
	 	mySql18.addSubPara(window.document.getElementsByName(trim("OManageCom"))[0].value);
	 	mySql18.addSubPara(window.document.getElementsByName(trim("OperatePos"))[0].value);
	 	mySql18.addSubPara(window.document.getElementsByName(trim("Quest"))[0].value);
	 	strSQL = mySql18.getString();
		
	  }
	  else if(tFlag == "5")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,(case ReplyMan when '' then '' else modifydate end),OperatePos,(case when errflag is not null then errflag else 'N' end) from lcissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	  	
//		
		var sqlid19="LLQuestQuerySql19";
	 	var mySql19=new SqlClass()
	 	mySql19.setResourceName("uw.LLQuestQuerySql");
	 	mySql19.setSqlId(sqlid19); //指定使用SQL的id
	 	mySql19.addSubPara(k);//指定传入参数
	 	mySql19.addSubPara(k);
	 	mySql19.addSubPara(manageCom);
	 	mySql19.addSubPara(window.document.getElementsByName(trim("ContNo"))[0].value);
	 	mySql19.addSubPara(window.document.getElementsByName(trim("BackObj"))[0].value);
	 	mySql19.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
	 	mySql19.addSubPara(window.document.getElementsByName(trim("OManageCom"))[0].value);
	 	mySql19.addSubPara(window.document.getElementsByName(trim("OperatePos"))[0].value);
	 	mySql19.addSubPara(window.document.getElementsByName(trim("Quest"))[0].value);
	 	strSQL = mySql19.getString();
		
	  }
	  else if(tFlag == "16")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,(case ReplyMan when '' then '' else modifydate end),OperatePos,(case when errflag is not null then errflag else 'N' end) from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and GrpContNo = (select grpcontno from lccont where contno = '"+tContNo+"')"
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	  
		
		var sqlid20="LLQuestQuerySql20";
	 	var mySql20=new SqlClass()
	 	mySql20.setResourceName("uw.LLQuestQuerySql");
	 	mySql20.setSqlId(sqlid20); //指定使用SQL的id
	 	mySql20.addSubPara(k);//指定传入参数
	 	mySql20.addSubPara(k);
	 	mySql20.addSubPara(manageCom);
	 	mySql20.addSubPara(tContNo);
	 	mySql20.addSubPara(window.document.getElementsByName(trim("BackObj"))[0].value);
	 	mySql20.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
	 	mySql20.addSubPara(window.document.getElementsByName(trim("OManageCom"))[0].value);
	 	mySql20.addSubPara(window.document.getElementsByName(trim("OperatePos"))[0].value);
	 	mySql20.addSubPara(window.document.getElementsByName(trim("Quest"))[0].value);
	 	strSQL = mySql20.getString();
		
		divButton.style.display = "none";
	  }
	  else
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,(case ReplyMan when '' then '' else modifydate end),OperatePos,(case when errflag is not null then errflag else 'N' end) from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		
		var sqlid21="LLQuestQuerySql21";
	 	var mySql21=new SqlClass()
	 	mySql21.setResourceName("uw.LLQuestQuerySql");
	 	mySql21.setSqlId(sqlid21); //指定使用SQL的id
	 	mySql21.addSubPara(k);//指定传入参数
	 	mySql21.addSubPara(k);
	 	mySql21.addSubPara(manageCom);
	 	mySql21.addSubPara(window.document.getElementsByName(trim("ContNo"))[0].value);
	 	mySql21.addSubPara(window.document.getElementsByName(trim("BackObj"))[0].value);
	 	mySql21.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
	 	mySql21.addSubPara(window.document.getElementsByName(trim("OManageCom"))[0].value);
	 	mySql21.addSubPara(window.document.getElementsByName(trim("OperatePos"))[0].value);
	 	mySql21.addSubPara(window.document.getElementsByName(trim("Quest"))[0].value);
	 	strSQL = mySql21.getString();
		
	  }	  	  	
}
	//prompt("",strSQL);
  //查询SQL,返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (turnPage.strQueryResult == false) {
    alert("没有问题件!!!");
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
	
	var tErrFlag = "";
	tErrFlag = QuestGrid.getRowColData(tselno,14);
	//将中文字符还原为数字,避免在sql查询时出错
	if(tPos == '理赔')
	{
		tPos = 4;
	}
    else if(tPos == '人工核保')
    {
    	tPos = 1;
    }
    else if(tPos == '新单复核') 
    {
    	tPos = 5;
    }	
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
	
	if (tOperatePos == "16")
	{
	
//		strSQL = "select issuecont,substr(replyresult,1,76),issuetype,OperatePos from lcgrpissuepol where "+k+"="+k+" "
//						+ " and grpcontno = (select grpcontno from lccont where contno = '"+tContNo+"')";
//		
		var sqlid22="LLQuestQuerySql22";
	 	var mySql22=new SqlClass()
	 	mySql22.setResourceName("uw.LLQuestQuerySql");
	 	mySql22.setSqlId(sqlid22); //指定使用SQL的id
	 	mySql22.addSubPara(k);//指定传入参数
	 	mySql22.addSubPara(k);
	 	mySql22.addSubPara(tContNo);
	 	strSQL = mySql22.getString();
		
	}
	else
	{
//		strSQL = "select issuecont,substr(replyresult,1,76),issuetype,OperatePos,operator,makedate,modifydate from llissuepol where "+k+"="+k+" "
//				 + getWherePart( 'ClmNo','ClmNo' )
//				 //+ getWherePart( 'BatNo','BatNo' )
//				 + getWherePart( 'issuetype','HideQuest' )
//				 //+ getWherePart( 'OperatePos','HideOperatePos')
//				 + getWherePart( 'SerialNo','HideSerialNo');
//				 //+ getWherePart( 'BackObjType','BackObj')
//				 //+ getWherePart( 'ManageCom','ManageCom')
//				 //+ getWherePart( 'IssueManageCom','OManageCom')
//		
		
		var HideOperatePos1="";
		var HideOperatePos2="";
				 if(document.all('HideOperatePos').value=="5")
				 {
					 HideOperatePos1="5";
//					strSQL = strSQL + " and OperatePos in ('5','I','A')";
				 }
				 else
				 {
					 HideOperatePos2=window.document.getElementsByName(trim("HideOperatePos"))[0].value
//					strSQL = strSQL + getWherePart( 'OperatePos','HideOperatePos');
				 }
				 
				    var sqlid23="LLQuestQuerySql23";
				 	var mySql23=new SqlClass()
				 	mySql23.setResourceName("uw.LLQuestQuerySql");
				 	mySql23.setSqlId(sqlid23); //指定使用SQL的id
				 	mySql23.addSubPara(k);//指定传入参数
				 	mySql23.addSubPara(k);
				 	mySql23.addSubPara(window.document.getElementsByName(trim("ClmNo"))[0].value);
				 	mySql23.addSubPara(window.document.getElementsByName(trim("HideQuest"))[0].value);
				 	mySql23.addSubPara(window.document.getElementsByName(trim("HideSerialNo"))[0].value);
				 	mySql23.addSubPara(HideOperatePos1);
				 	mySql23.addSubPara(HideOperatePos2);
				 	strSQL = mySql23.getString();
				 
				 
				 
	}
	//prompt("问题件明细",strSQL);
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
  
   document.all('ErrorFlag').value = tErrFlag;
//   var tSQL_Err = "select codename from ldcode where codetype='yesno' and code='"+tErrFlag+"' ";
   
    var sqlid24="LLQuestQuerySql24";
	var mySql24=new SqlClass()
	mySql24.setResourceName("uw.LLQuestQuerySql");
	mySql24.setSqlId(sqlid24); //指定使用SQL的id
	mySql24.addSubPara(tErrFlag);//指定传入参数
	var tSQL_Err = mySql24.getString();
   
   var errResult = easyExecSql(tSQL_Err);
    var SubType="";
    if(errResult!=null){
      document.all('ErrorFlagName').value = errResult[0][0];
    }
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
//		strSQL = "select code,cont from ldcodemod where "+k+"="+k				 	
//				 + " and codetype = 'Question'";
		
		var sqlid25="LLQuestQuerySql25";
	 	var mySql25=new SqlClass()
	 	mySql25.setResourceName("uw.LLQuestQuerySql");
	 	mySql25.setSqlId(sqlid25); //指定使用SQL的id
	 	mySql25.addSubPara(k);//指定传入参数
	 	mySql25.addSubPara(k);
	 	strSQL = mySql25.getString();
		
	//}
	
	//alert(strSQL);
	
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
     return;	 
   }
   var ContNo=QuestGrid.getRowColData(tsel,1); 
	window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");								
 	
}

//问题件回复完毕函数
function finishMission()
{
	//判断是否所有需要回复的问题件都做了回复
//	var		strSQL = "select * from lcissuepol where 1=1 "				 	
//				 //+ " and OperatePos in ('0','1','5','6')"				 
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				// + getWherePart( 'OperatePos','OperatePos')
//				 //+ getWherePart( 'IssueType','Quest')
//				 + " and replyresult is not null "
//				 + " and state is not null and state='1' "
//				 ;	  
	
			var sqlid26="LLQuestQuerySql26";
		 	var mySql26=new SqlClass()
		 	mySql26.setResourceName("uw.LLQuestQuerySql");
		 	mySql26.setSqlId(sqlid26); //指定使用SQL的id
		 	mySql26.addSubPara(manageCom);//指定传入参数
		 	mySql26.addSubPara(window.document.getElementsByName(trim("ContNo"))[0].value);
		 	mySql26.addSubPara(window.document.getElementsByName(trim("BackObj"))[0].value);
		 	mySql26.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
		 	mySql26.addSubPara(window.document.getElementsByName(trim("OManageCom"))[0].value);
		 	var strSQL = mySql26.getString();
	
	var brr = easyExecSql(strSQL);
	if ( brr )  //所有的都保存了.
 	{
 		var showStr="正在保存数据,请您稍候并且不要修改屏幕上的值或链接其他页面";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

 		fm.action = "./QuestMissionChk.jsp?MissionId="+tMissionID+"&SubMissionId="+tSubMissionID+"&ContNo="+tContNo+" ";
 		document.all('hiddenOperate').value="finishMission";
 		fm.submit();
	}
	else
	{
		alert("有未回复的机构问题件,请先回复");
		return false;
	}
}
