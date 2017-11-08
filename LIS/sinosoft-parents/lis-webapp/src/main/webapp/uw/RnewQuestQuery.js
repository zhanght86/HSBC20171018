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
var str_sql = "",sql_id = "", my_sql = "";   //绑定变量访问数据库
//提交,保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在保存数据,请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");

  document.getElementById("fm").submit(); //提交
}

//提交,保存按钮对应操作
function IfPrint()
{
  var i = 0;
  var showStr="正在保存数据,请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
　cContNo = fm.ContNo.value;  //保单号码	
//　var strSql = "select * from LCPol where PolNo='" + cContNo + "' and  approveflag = '2'";
sql_id = "RnewQuestQuerySql0";
my_sql = new SqlClass();
my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
my_sql.setSqlId(cContNo);//指定使用的Sql的id
my_sql.addSubPara(idstr);//指定传入的参数
str_sql = my_sql.getString();
    var arrResult = easyExecSql(str_sql);
       //alert(arrResult);
    if (arrResult != null) {     
   　　alert("已发核保通知书,但未打印,不容许在此时修改问题件的打印标志！");
          return;
      }      
  fm.action = "./QuestQueryUpdatePrintFlag.jsp";
  document.getElementById("fm").submit(); //提交
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
	var iHeight=350;     //弹出窗口的高度; 
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
	document.getElementById("fm").submit();
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
	var tFlag = fm.Flag.value;
	var tContNo = fm.ContNo.value;

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
	if (ifreply == "Y")
	{
	  if (tFlag == "1")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(case OperatePos when '0' then '新单录入/复核修改/问题修改' when '1' then '人工核保' when '5' then '新单复核' when '3' then '问题件修改岗' when '3' then '问题件修改岗'  when 'A' then '新单复核' when 'I' then '新单复核' else OperatePos end ),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','3','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is not null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		sql_id = "RnewQuestQuerySql1";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(manageCom);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();
	  }		
	  else if(tFlag == "2")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
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
		sql_id = "RnewQuestQuerySql2";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(manageCom);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "3")
	  {
	  
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is not null "
//				 + " and ReplyResult is not null"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		sql_id = "RnewQuestQuerySql3";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(manageCom);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "4")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','5','A','I','6')"				 
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is not null "
//				 + " and BackObjType = '4' "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest')
//				 + " and replyresult is null";	
		sql_id = "RnewQuestQuerySql4";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(manageCom);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "5")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is not null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	
		sql_id = "RnewQuestQuerySql5";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(manageCom);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "16")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and GrpContNo = (select grpcontno from lccont where contno = '"+tContNo+"')"
//				 + " and ReplyMan is not null "
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	  	
		divButton.style.display = "none";
		sql_id = "RnewQuestQuerySql6";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(manageCom);//指定传入的参数
		my_sql.addSubPara(tContNo);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();
	  }
	  else
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is not null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		sql_id = "RnewQuestQuerySql7";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(manageCom);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();
	  }	  	  	
}
 else if (ifreply == "N")
	{
	  if (tFlag == "1")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','3','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is  null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		sql_id = "RnewQuestQuerySql8";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(manageCom);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "2")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
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
		sql_id = "RnewQuestQuerySql9";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(manageCom);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "3")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is  null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		sql_id = "RnewQuestQuerySql10";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(manageCom);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "4")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
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
		sql_id = "RnewQuestQuerySql11";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(manageCom);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "5")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is  null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		sql_id = "RnewQuestQuerySql12";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(manageCom);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "16")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and GrpContNo = (select grpcontno from lccont where contno = '"+tContNo+"')"
//				  + " and ReplyMan is  null "
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	  	
		divButton.style.display = "none";
		sql_id = "RnewQuestQuerySql13";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(manageCom);//指定传入的参数
		my_sql.addSubPara(tContNo);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();
	  }
	  else
	  {
	    //alert(1);
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is  null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		sql_id = "RnewQuestQuerySql14";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(manageCom);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();
	  }	  	  	
}
//走这个分支
else
	{
		//alert(tOperatePos);
		//tongmeng 
	  if (tFlag == "1")
	  {
	    
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,(case ReplyMan when '' then '' else to_char(modifydate,'yyyy-mm-dd') end),OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','3','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		sql_id = "RnewQuestQuerySql15";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(manageCom);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();
	  }		
	  else if(tFlag == "2")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,(case ReplyMan when '' then '' else to_char(modifydate,'yyyy-mm-dd') end),OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
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
		sql_id = "RnewQuestQuerySql16";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(manageCom);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "3")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,(case ReplyMan when '' then '' else to_char(modifydate,'yyyy-mm-dd') end),OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and state is not null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		sql_id = "RnewQuestQuerySql17";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(manageCom);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "4")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos) ),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,(case ReplyMan when '' then '' else to_char(modifydate,'yyyy-mm-dd') end),OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','3','5','6')"				 
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		sql_id = "RnewQuestQuerySql18";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(manageCom);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "5")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,(case ReplyMan when '' then '' else to_char(modifydate,'yyyy-mm-dd') end),OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	  
		sql_id = "RnewQuestQuerySql19";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(manageCom);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "16")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,(case ReplyMan when '' then '' else to_char(modifydate,'yyyy-mm-dd') end),OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and GrpContNo = (select grpcontno from lccont where contno = '"+tContNo+"')"
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	  	
		divButton.style.display = "none";
		sql_id = "RnewQuestQuerySql20";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(manageCom);//指定传入的参数
		my_sql.addSubPara(tContNo);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();
	  }
	  else
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,(case ReplyMan when '' then '' else to_char(modifydate,'yyyy-mm-dd') end),OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		sql_id = "RnewQuestQuerySql21";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(manageCom);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();
	  }	  	  	
}
	
  //查询SQL,返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1);  
  
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
	//将中文字符还原为数字,避免在sql查询时出错
	if(tPos == '新单录入/复核修改/问题修改')
	{
		tPos = 0;
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
		sql_id = "RnewQuestQuerySql22";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(tContNo);//指定传入的参数
		str_sql = my_sql.getString();
	}
	else
	{
//		strSQL = "select issuecont,substr(replyresult,1,76),issuetype,OperatePos,operator,makedate,modifydate from Rnewissuepol where "+k+"="+k+" "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'issuetype','HideQuest' )
//				 + getWherePart( 'SerialNo','HideSerialNo');
				 if(document.all('HideOperatePos').value=="5")
				 {
//					strSQL = strSQL + " and OperatePos in ('5','I','A')";
					sql_id = "RnewQuestQuerySql23";
					my_sql = new SqlClass();
					my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
					my_sql.setSqlId(sql_id);//指定使用的Sql的id
					my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//指定传入的参数
					my_sql.addSubPara(document.getElementsByName(trim('HideQuest'))[0].value);//指定传入的参数
					my_sql.addSubPara(document.getElementsByName(trim('HideSerialNo'))[0].value);//指定传入的参数
					str_sql = my_sql.getString();
				 }
				 else
				 {
//					strSQL = strSQL + getWherePart( 'OperatePos','HideOperatePos');
					 sql_id = "RnewQuestQuerySql24";
						my_sql = new SqlClass();
						my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
						my_sql.setSqlId(sql_id);//指定使用的Sql的id
						my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//指定传入的参数
						my_sql.addSubPara(document.getElementsByName(trim('HideQuest'))[0].value);//指定传入的参数
						my_sql.addSubPara(document.getElementsByName(trim('HideSerialNo'))[0].value);//指定传入的参数
						my_sql.addSubPara(document.getElementsByName(trim('HideOperatePos'))[0].value);//指定传入的参数
						str_sql = my_sql.getString();
				 }
				 
	}
  //查询SQL,返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(my_sql, 1, 0, 1);  
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
		document.getElementById("fm").submit();	
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
		fm.action = "./RnewQuestReplyChk.jsp";
		document.getElementById("fm").submit();
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
//		strSQL = "select code,cont from ldcodemod where "+k+"="+k				 	
//				 + " and codetype = 'Question'";
		 sql_id = "RnewQuestQuerySql25";
			my_sql = new SqlClass();
			my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
			my_sql.setSqlId(sql_id);//指定使用的Sql的id
			str_sql = my_sql.getString();
  //查询SQL,返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1);  
  
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
//	var		strSQL = "select * from Rnewissuepol where 1=1 "				 			 
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + " and replyresult is not null "
//				 + " and state is not null and state='1' ";
	 sql_id = "RnewQuestQuerySql26";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(manageCom);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();
	var brr = easyExecSql(str_sql);
	if ( brr )  //所有的都保存了.
 	{
 		var showStr="正在保存数据,请您稍候并且不要修改屏幕上的值或链接其他页面";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.QuesFlag.value = "allover";
 		fm.action = "./RnewQuestReplyChk.jsp";
 		document.all('hiddenOperate').value="finishMission";
 		document.getElementById("fm").submit();
	}
	else
	{
		alert("有未回复的机构问题件,请先回复");
		return false;
	}
}
