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
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var cflag = "";  //问题件操作位置 1.核保
var canReplyFlag = false;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

//提交,保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在保存数据,请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  document.getElementById("fm").submit(); //提交
}

//提交,保存按钮对应操作
function IfPrint()
{
  var i = 0;
  var showStr="正在保存数据,请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
　cContNo = fm.ContNo.value;  //保单号码	

	var sqlid19="QuestInputSql19";
var mySql19=new SqlClass();
mySql19.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql19.setSqlId(sqlid19);//指定使用的Sql的id
mySql19.addSubPara(cContNo);//指定传入的参数
　var strSql=mySql19.getString();		 

//　var strSql = "select * from LCPol where PolNo='" + cContNo + "' and  approveflag = '2'";
    var arrResult = easyExecSql(strSql);
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
	unlockScreen('lkscreen');  
	fm.ErrorFlagAction.value = "0";
	//query();
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
  document.all('Quest').value="";
  initForm(document.all('ContNo').value,document.all('Flag').value,document.all('MissionID').value,document.all('SubMissionID').value);
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
//	alert(mMasterCenter);
	if (ifreply == "Y")
	{

	  if (tFlag == "1")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(case OperatePos when '0' then '新单录入/复核修改/问题修改' when '1' then '人工核保' when '5' then '新单复核' when '3' then '问题件修改岗' when '3' then '问题件修改岗'  when 'A' then '新单复核' when 'I' then '新单复核' else OperatePos end ),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from lcissuepol where "+k+"="+k+" "				 	
		
			var sqlid20="QuestInputSql20";
var mySql20=new SqlClass();
mySql20.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql20.setSqlId(sqlid20);//指定使用的Sql的id
mySql20.addSubPara('1');//指定传入的参数
mySql20.addSubPara(manageCom);//指定传入的参数
mySql20.addSubPara(fm.ContNo.value);//指定传入的参数
mySql20.addSubPara(fm.BackObj.value);//指定传入的参数
mySql20.addSubPara(fm.ManageCom.value);//指定传入的参数
mySql20.addSubPara(fm.OManageCom.value);//指定传入的参数
mySql20.addSubPara(fm.OperatePos.value);//指定传入的参数
mySql20.addSubPara(fm.Quest.value);//指定传入的参数
strSQL=mySql20.getString();	

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
	  }		
	  else if(tFlag == "2")
	  {
	  	
		var sqlid21="QuestInputSql21";
var mySql21=new SqlClass();
mySql21.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql21.setSqlId(sqlid21);//指定使用的Sql的id
mySql21.addSubPara('1');//指定传入的参数
mySql21.addSubPara(manageCom);//指定传入的参数
mySql21.addSubPara(fm.ContNo.value);//指定传入的参数
mySql21.addSubPara(fm.BackObj.value);//指定传入的参数
mySql21.addSubPara(fm.ManageCom.value);//指定传入的参数
mySql21.addSubPara(fm.OManageCom.value);//指定传入的参数
mySql21.addSubPara(fm.OperatePos.value);//指定传入的参数
mySql21.addSubPara(fm.Quest.value);//指定传入的参数
strSQL=mySql21.getString();	
		
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
	  }
	  else if(tFlag == "3")
	  {
	  
	  		var sqlid22="QuestInputSql22";
var mySql22=new SqlClass();
mySql22.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql22.setSqlId(sqlid22);//指定使用的Sql的id
mySql22.addSubPara('1');//指定传入的参数
mySql22.addSubPara(manageCom);//指定传入的参数
mySql22.addSubPara(fm.ContNo.value);//指定传入的参数
mySql22.addSubPara(fm.BackObj.value);//指定传入的参数
mySql22.addSubPara(fm.ManageCom.value);//指定传入的参数
mySql22.addSubPara(fm.OManageCom.value);//指定传入的参数
mySql22.addSubPara(fm.OperatePos.value);//指定传入的参数
mySql22.addSubPara(fm.Quest.value);//指定传入的参数
strSQL=mySql22.getString();	
	  
	  
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
	  }
	  else if(tFlag == "4")
	  {
	  	
		var mySql23=new SqlClass();
		var sqlid23="QuestInputSql23";
mySql23.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql23.setSqlId(sqlid23);//指定使用的Sql的id
mySql23.addSubPara('1');//指定传入的参数
mySql23.addSubPara(manageCom);//指定传入的参数
mySql23.addSubPara(fm.ContNo.value);//指定传入的参数
mySql23.addSubPara(fm.BackObj.value);//指定传入的参数
mySql23.addSubPara(fm.ManageCom.value);//指定传入的参数
mySql23.addSubPara(fm.OManageCom.value);//指定传入的参数
mySql23.addSubPara(fm.OperatePos.value);//指定传入的参数
mySql23.addSubPara(fm.Quest.value);//指定传入的参数
strSQL=mySql23.getString();	
		
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
				 if(mMasterCenter=='Y')
				 {
				 	 strSQL = strSQL + " and needprint='Y' ";
				 }
	  }
	  else if(tFlag == "5")
	  {
	  	
				var mySql24=new SqlClass();
				var sqlid24="QuestInputSql24";
mySql24.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql24.setSqlId(sqlid24);//指定使用的Sql的id
mySql24.addSubPara('1');//指定传入的参数
mySql24.addSubPara(manageCom);//指定传入的参数
mySql24.addSubPara(fm.BackObj.value);//指定传入的参数
mySql24.addSubPara(fm.ManageCom.value);//指定传入的参数
mySql24.addSubPara(fm.OManageCom.value);//指定传入的参数
mySql24.addSubPara(fm.OperatePos.value);//指定传入的参数
mySql24.addSubPara(fm.Quest.value);//指定传入的参数
mySql24.addSubPara(fm.ContNo.value);//指定传入的参数
strSQL=mySql24.getString();	
		
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is not null "
//				 
////				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	  	
//				 if(fm.ContNo.value!=null||fm.ContNo.value!=""){
//					 strSQL = strSQL+" and contno in (select contno from lccont where  prtno='"+fm.ContNo.value+"') " ;
//				 }
	  }
	  else if(tFlag == "16")
	  {
	  	
						var mySql25=new SqlClass();
						var sqlid25="QuestInputSql25";
mySql25.setResourceName("uw.QuestInputSql25"); //指定使用的properties文件名
mySql25.setSqlId(sqlid25);//指定使用的Sql的id
mySql25.addSubPara('1');//指定传入的参数
mySql25.addSubPara(manageCom);//指定传入的参数
mySql25.addSubPara(fm.ContNo.value);//指定传入的参数
mySql25.addSubPara(fm.BackObj.value);//指定传入的参数
mySql25.addSubPara(fm.ManageCom.value);//指定传入的参数
mySql25.addSubPara(fm.OManageCom.value);//指定传入的参数
mySql25.addSubPara(fm.OperatePos.value);//指定传入的参数
mySql25.addSubPara(fm.Quest.value);//指定传入的参数

strSQL=mySql25.getString();	
		
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and GrpContNo = (select grpcontno from lccont where contno = '"+tContNo+"')"
//				 + " and ReplyMan is not null "
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	  	
		divButton.style.display = "none";
	  }
	  else
	  {
	  	
								var mySql26=new SqlClass();
								var sqlid26="QuestInputSql26";
mySql26.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql26.setSqlId(sqlid26);//指定使用的Sql的id
mySql26.addSubPara('1');//指定传入的参数
mySql26.addSubPara(manageCom);//指定传入的参数
mySql26.addSubPara(fm.ContNo.value);//指定传入的参数
mySql26.addSubPara(fm.BackObj.value);//指定传入的参数
mySql26.addSubPara(fm.ManageCom.value);//指定传入的参数
mySql26.addSubPara(fm.OManageCom.value);//指定传入的参数
mySql26.addSubPara(fm.OperatePos.value);//指定传入的参数
mySql26.addSubPara(fm.Quest.value);//指定传入的参数

strSQL=mySql26.getString();	
		
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is not null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
	  }	  	  	
}
 else if (ifreply == "N")
	{
	  if (tFlag == "1")
	  {
	  	
										var mySql27=new SqlClass();
										var sqlid27="QuestInputSql27";
mySql27.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql27.setSqlId(sqlid27);//指定使用的Sql的id
mySql27.addSubPara('1');//指定传入的参数
mySql27.addSubPara(manageCom);//指定传入的参数
mySql27.addSubPara(fm.ContNo.value);//指定传入的参数
mySql27.addSubPara(fm.BackObj.value);//指定传入的参数
mySql27.addSubPara(fm.ManageCom.value);//指定传入的参数
mySql27.addSubPara(fm.OManageCom.value);//指定传入的参数
mySql27.addSubPara(fm.OperatePos.value);//指定传入的参数
mySql27.addSubPara(fm.Quest.value);//指定传入的参数

strSQL=mySql27.getString();	
		
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
	  }
	  else if(tFlag == "2")
	  {
	  	
var mySql28=new SqlClass();
var sqlid28="QuestInputSql28";
mySql28.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql28.setSqlId(sqlid28);//指定使用的Sql的id
mySql28.addSubPara('1');//指定传入的参数
mySql28.addSubPara(manageCom);//指定传入的参数
mySql28.addSubPara(fm.ContNo.value);//指定传入的参数
mySql28.addSubPara(fm.BackObj.value);//指定传入的参数
mySql28.addSubPara(fm.ManageCom.value);//指定传入的参数
mySql28.addSubPara(fm.OManageCom.value);//指定传入的参数
mySql28.addSubPara(fm.OperatePos.value);//指定传入的参数
mySql28.addSubPara(fm.Quest.value);//指定传入的参数

strSQL=mySql28.getString();	
		
		
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
	  }
	  else if(tFlag == "3")
	  {
	  	
		var mySql29=new SqlClass();
		var sqlid29="QuestInputSql29";
mySql29.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql29.setSqlId(sqlid29);//指定使用的Sql的id
mySql29.addSubPara('1');//指定传入的参数
mySql29.addSubPara(manageCom);//指定传入的参数
mySql29.addSubPara(fm.ContNo.value);//指定传入的参数
mySql29.addSubPara(fm.BackObj.value);//指定传入的参数
mySql29.addSubPara(fm.ManageCom.value);//指定传入的参数
mySql29.addSubPara(fm.OManageCom.value);//指定传入的参数
mySql29.addSubPara(fm.OperatePos.value);//指定传入的参数
mySql29.addSubPara(fm.Quest.value);//指定传入的参数

strSQL=mySql29.getString();	
		
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is  null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
	  }
	  else if(tFlag == "4")
	  {
	  	
				var mySql30=new SqlClass();
				var sqlid30="QuestInputSql30";
mySql30.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql30.setSqlId(sqlid30);//指定使用的Sql的id
mySql30.addSubPara('1');//指定传入的参数
mySql30.addSubPara(manageCom);//指定传入的参数
mySql30.addSubPara(fm.ContNo.value);//指定传入的参数
mySql30.addSubPara(fm.BackObj.value);//指定传入的参数
mySql30.addSubPara(fm.ManageCom.value);//指定传入的参数
mySql30.addSubPara(fm.OManageCom.value);//指定传入的参数
mySql30.addSubPara(fm.OperatePos.value);//指定传入的参数
mySql30.addSubPara(fm.Quest.value);//指定传入的参数

strSQL=mySql30.getString();	
		
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
				 if(mMasterCenter=='Y')
				 {
				 	 strSQL = strSQL + " and needprint='Y' ";
				 }
	  }
	  else if(tFlag == "5")
	  {
	  	
						var mySql31=new SqlClass();
						var sqlid31="QuestInputSql31";
mySql31.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql31.setSqlId(sqlid31);//指定使用的Sql的id
mySql31.addSubPara('1');//指定传入的参数
mySql31.addSubPara(manageCom);//指定传入的参数
mySql31.addSubPara(fm.ContNo.value);//指定传入的参数
mySql31.addSubPara(fm.BackObj.value);//指定传入的参数
mySql31.addSubPara(fm.ManageCom.value);//指定传入的参数
mySql31.addSubPara(fm.OManageCom.value);//指定传入的参数
mySql31.addSubPara(fm.OperatePos.value);//指定传入的参数
mySql31.addSubPara(fm.Quest.value);//指定传入的参数
strSQL=mySql31.getString();	
		
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
	  }
	  else if(tFlag == "16")
	  {
	  	
								var mySql32=new SqlClass();
								var sqlid32="QuestInputSql32";
mySql32.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql32.setSqlId(sqlid32);//指定使用的Sql的id
mySql32.addSubPara('1');//指定传入的参数
mySql32.addSubPara(manageCom);//指定传入的参数
mySql32.addSubPara(fm.ContNo.value);//指定传入的参数
mySql32.addSubPara(fm.BackObj.value);//指定传入的参数
mySql32.addSubPara(fm.ManageCom.value);//指定传入的参数
mySql32.addSubPara(fm.OManageCom.value);//指定传入的参数
mySql32.addSubPara(fm.OperatePos.value);//指定传入的参数
mySql32.addSubPara(fm.Quest.value);//指定传入的参数
		strSQL=mySql32.getString();	
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and GrpContNo = (select grpcontno from lccont where contno = '"+tContNo+"')"
//				  + " and ReplyMan is  null "
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	  	
		divButton.style.display = "none";
	  }
	  else
	  {
	    //alert(1);
		
var mySql33=new SqlClass();
var sqlid33="QuestInputSql33";
mySql33.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql33.setSqlId(sqlid33);//指定使用的Sql的id
mySql33.addSubPara('1');//指定传入的参数
mySql33.addSubPara(manageCom);//指定传入的参数
mySql33.addSubPara(fm.ContNo.value);//指定传入的参数
mySql33.addSubPara(fm.BackObj.value);//指定传入的参数
mySql33.addSubPara(fm.ManageCom.value);//指定传入的参数
mySql33.addSubPara(fm.OManageCom.value);//指定传入的参数
mySql33.addSubPara(fm.OperatePos.value);//指定传入的参数
mySql33.addSubPara(fm.Quest.value);//指定传入的参数
strSQL=mySql33.getString();	
		
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is  null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
	  }	  	  	
}
//走这个分支
else
	{
//		alert(355);
		//tongmeng 
	  if (tFlag == "1")
	  {
	  	
		var mySql34=new SqlClass();
		var sqlid34="QuestInputSql34";
mySql34.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql34.setSqlId(sqlid34);//指定使用的Sql的id
mySql34.addSubPara('1');//指定传入的参数
//mySql34.addSubPara(k);//指定传入的参数
//alert(fm.BackObj.value);
mySql34.addSubPara(manageCom);//指定传入的参数
mySql34.addSubPara(fm.ContNo.value);//指定传入的参数
mySql34.addSubPara(fm.BackObj.value);//指定传入的参数
mySql34.addSubPara(fm.ManageCom.value);//指定传入的参数
mySql34.addSubPara(fm.OManageCom.value);//指定传入的参数
mySql34.addSubPara(fm.OperatePos.value);//指定传入的参数
mySql34.addSubPara(fm.Quest.value);//指定传入的参数
		strSQL=mySql34.getString();	
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,decode(ReplyMan,'','',modifydate),OperatePos,nvl(errflag,'N') from lcissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','3','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
	  }		
	  else if(tFlag == "2")
	  {
	  	
var mySql35=new SqlClass();
var sqlid35="QuestInputSql35";
mySql35.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql35.setSqlId(sqlid35);//指定使用的Sql的id
mySql35.addSubPara('1');//指定传入的参数
mySql35.addSubPara(manageCom);//指定传入的参数
mySql35.addSubPara(fm.ContNo.value);//指定传入的参数
mySql35.addSubPara(fm.BackObj.value);//指定传入的参数
mySql35.addSubPara(fm.ManageCom.value);//指定传入的参数
mySql35.addSubPara(fm.OManageCom.value);//指定传入的参数
mySql35.addSubPara(fm.OperatePos.value);//指定传入的参数
mySql35.addSubPara(fm.Quest.value);//指定传入的参数
strSQL=mySql35.getString();			
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,decode(ReplyMan,'','',modifydate),OperatePos,nvl(errflag,'N') from lcissuepol where "+k+"="+k+" "				 	
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
	  }
	  else if(tFlag == "3")
	  {
	  	
		var mySql36=new SqlClass();
		var sqlid36="QuestInputSql36";
mySql36.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql36.setSqlId(sqlid36);//指定使用的Sql的id
mySql36.addSubPara('1');//指定传入的参数
//mySql36.addSubPara(k);//指定传入的参数
mySql36.addSubPara(manageCom);//指定传入的参数
mySql36.addSubPara(fm.ContNo.value);//指定传入的参数
mySql36.addSubPara(fm.BackObj.value);//指定传入的参数
mySql36.addSubPara(fm.ManageCom.value);//指定传入的参数
mySql36.addSubPara(fm.OManageCom.value);//指定传入的参数
mySql36.addSubPara(fm.OperatePos.value);//指定传入的参数
mySql36.addSubPara(fm.Quest.value);//指定传入的参数
strSQL=mySql36.getString();	
		
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,decode((ReplyMan||decode(state,'','','2','2','')),'','',modifydate),OperatePos,nvl(errflag,'N') from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and state is not null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
	  }
	  else if(tFlag == "4")
	  {
	  	
var mySql37=new SqlClass();
var sqlid37="QuestInputSql37";
mySql37.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql37.setSqlId(sqlid37);//指定使用的Sql的id
mySql37.addSubPara('1');//指定传入的参数
//mySql37.addSubPara(k);//指定传入的参数
mySql37.addSubPara(manageCom);//指定传入的参数
mySql37.addSubPara(fm.ContNo.value);//指定传入的参数
mySql37.addSubPara(fm.BackObj.value);//指定传入的参数
mySql37.addSubPara(fm.ManageCom.value);//指定传入的参数
mySql37.addSubPara(fm.OManageCom.value);//指定传入的参数
mySql37.addSubPara(fm.OperatePos.value);//指定传入的参数
//alert(fm.Quest.value);
mySql37.addSubPara(fm.Quest.value);//指定传入的参数
mySql37.addSubPara(mMasterCenter);//指定传入的参数
mySql37.addSubPara(mMasterCenter);//指定传入的参数
		strSQL=mySql37.getString();	
		//prompt('',strSQL);
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos) ),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,decode(ReplyMan,'','',modifydate),OperatePos,nvl(errflag,'N') from lcissuepol where "+k+"="+k+" "				 	
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
				 //if(mMasterCenter=='Y')
				// {
				// 	 strSQL = strSQL + " and needprint=''Y' ";
				// }
				// prompt("",strSQL);
	  }
	  else if(tFlag == "5")
	  {
	  	
		var mySql38=new SqlClass();
		var sqlid38="QuestInputSql38";
mySql38.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql38.setSqlId(sqlid38);//指定使用的Sql的id
mySql38.addSubPara('1');//指定传入的参数
//mySql38.addSubPara(k);//指定传入的参数
mySql38.addSubPara(manageCom);//指定传入的参数
mySql38.addSubPara(fm.BackObj.value);//指定传入的参数
mySql38.addSubPara(fm.ManageCom.value);//指定传入的参数
mySql38.addSubPara(fm.OManageCom.value);//指定传入的参数
mySql38.addSubPara(fm.OperatePos.value);//指定传入的参数
mySql38.addSubPara(fm.Quest.value);//指定传入的参数
mySql38.addSubPara(fm.ContNo.value);//指定传入的参数
		strSQL=mySql38.getString();	
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,decode(ReplyMan,'','',modifydate),OperatePos,nvl(errflag,'N') from lcissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 
////				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	
//				 if(fm.ContNo.value!=null||fm.ContNo.value!=""){
//					 strSQL = strSQL+" and contno in (select contno from lccont where  prtno='"+fm.ContNo.value+"') "; 
//				 }  	
	  }
	  else if(tFlag == "16")
	  {
	  	
var mySql39=new SqlClass();
var sqlid39="QuestInputSql39";
mySql39.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql39.setSqlId(sqlid39);//指定使用的Sql的id
mySql39.addSubPara('1');//指定传入的参数
mySql39.addSubPara(manageCom);//指定传入的参数
mySql39.addSubPara(tContNo);//指定传入的参数
mySql39.addSubPara(fm.BackObj.value);//指定传入的参数
mySql39.addSubPara(fm.ManageCom.value);//指定传入的参数
mySql39.addSubPara(fm.OManageCom.value);//指定传入的参数
mySql39.addSubPara(fm.OperatePos.value);//指定传入的参数
mySql39.addSubPara(fm.Quest.value);//指定传入的参数
		strSQL=mySql39.getString();	
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,decode(ReplyMan,'','',modifydate),OperatePos,nvl(errflag,'N') from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and GrpContNo = (select grpcontno from lccont where contno = '"+tContNo+"')"
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	  	
		divButton.style.display = "none";
	  }
	  else
	  {
	  	
		var mySql40=new SqlClass();
		var sqlid40="QuestInputSql40";
mySql40.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql40.setSqlId(sqlid40);//指定使用的Sql的id
mySql40.addSubPara('1');//指定传入的参数
mySql40.addSubPara(manageCom);//指定传入的参数
mySql40.addSubPara(tContNo);//指定传入的参数
mySql40.addSubPara(fm.BackObj.value);//指定传入的参数
mySql40.addSubPara(fm.ManageCom.value);//指定传入的参数
mySql40.addSubPara(fm.OManageCom.value);//指定传入的参数
mySql40.addSubPara(fm.OperatePos.value);//指定传入的参数
mySql40.addSubPara(fm.Quest.value);//指定传入的参数
		strSQL=mySql40.getString();	
		
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,decode(ReplyMan,'','',modifydate),OperatePos,nvl(errflag,'N') from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
	  }	  	  	
}
	
	turnPage1.queryModal(strSQL,QuestGrid);
  //查询SQL,返回结果字符串
 /* turnPage1.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (turnPage1.strQueryResult == false) {
    alert("没有问题件");
    return "";
  }
  
  //查询成功则拆分字符串,返回二维数组
  turnPage1.arrDataCacheSet = decodeEasyQueryResult(turnPage1.strQueryResult);
  
  //设置初始化过的MULTILINE对象,HealthGrid为在初始化页中定义的全局变量
  turnPage1.pageDisplayGrid = QuestGrid;    
          
  //保存SQL语句
  turnPage1.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage1.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage1.getData(turnPage1.arrDataCacheSet, turnPage1.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage1.pageDisplayGrid);
  */
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
	//alert(tSerialNo);
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
	
		var mySql41=new SqlClass();
		var sqlid41="QuestInputSql41";
mySql41.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql41.setSqlId(sqlid41);//指定使用的Sql的id
mySql41.addSubPara('1');//指定传入的参数
//mySql41.addSubPara(k);//指定传入的参数
mySql41.addSubPara(tContNo);//指定传入的参数

strSQL=mySql41.getString();	
	
//		strSQL = "select issuecont,substr(replyresult,1,76),issuetype,OperatePos from lcgrpissuepol where "+k+"="+k+" "
//						+ " and grpcontno = (select grpcontno from lccont where contno = '"+tContNo+"')";
	}
	else
	{
		
				var mySql42=new SqlClass();
				var sqlid42="QuestInputSql42";
				var addStr = " and 1 = 1 ";
mySql42.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql42.setSqlId(sqlid42);//指定使用的Sql的id
mySql42.addSubPara('1');//指定传入的参数
//mySql42.addSubPara(k);//指定传入的参数
mySql42.addSubPara(fm.HideQuest.value);//指定传入的参数
mySql42.addSubPara(fm.HideSerialNo.value);//指定传入的参数
mySql42.addSubPara(fm.ContNo.value);//指定传入的参数
		
//		strSQL = "select issuecont,substr(replyresult,1,76),issuetype,OperatePos,operator,makedate,modifydate from lcissuepol where "+k+"="+k+" "
//				 + getWherePart( 'issuetype','HideQuest' )
//				 + getWherePart( 'serialno','HideSerialNo')
//				 if(fm.ContNo.value!=null||fm.ContNo.value!=""){
//					 strSQL = strSQL+" and contno in (select contno from lccont where prtno='"+fm.ContNo.value+"')";
//				 }
				 //alert(fm.HideSerialNo.value);
				 if(document.all('HideOperatePos').value=="5")
				 {
					 addStr = " and OperatePos in ('5','I','A')";
				 }
				 else
				 {
					 addStr = getWherePart( 'OperatePos','HideOperatePos');
				 }
				 mySql42.addSubPara(addStr);
				 strSQL=mySql42.getString();	
	}
  //查询SQL,返回结果字符串
  turnPage2.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  //prompt("",strSQL);
  //判断是否查询成功
  if (!turnPage2.strQueryResult) {
var addStr1 = " and 1=1 ";
var addStr2 = " and 2=2 ";  	
var mySql43=new SqlClass();
var sqlid43="QuestInputSql43";
mySql43.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql43.setSqlId(sqlid43);//指定使用的Sql的id
//mySql43.addSubPara(k);//指定传入的参数
//mySql43.addSubPara(k);//指定传入的参数
mySql43.addSubPara(fm.HideQuest.value);//指定传入的参数
mySql43.addSubPara(fm.HideSerialNo.value);//指定传入的参数

//strSQL=mySql43.getString();	
	
//	  strSQL = "select issuecont,substr(replyresult,1,76),issuetype,OperatePos,operator,makedate,modifydate from lcissuepol where "+k+"="+k+" "
//		 + getWherePart( 'issuetype','HideQuest' )
//		 + getWherePart( 'serialno','HideSerialNo')
		 if(fm.ContNo.value!=null||fm.ContNo.value!=""){
//			 strSQL = strSQL+" and contno = '"+fm.ContNo.value+"'";
             addStr1 = " and contno = '"+fm.ContNo.value+"'";
		 }
		 //alert(fm.HideSerialNo.value);
		 if(document.all('HideOperatePos').value=="5")
		 {
//			strSQL = strSQL + " and OperatePos in ('5','I','A')";
            addStr2 = " and OperatePos in ('5','I','A')";
		 }
		 else
		 {
			addStr2 = getWherePart( 'OperatePos','HideOperatePos');
		 }
mySql43.addSubPara(addStr1);//指定传入的参数
mySql43.addSubPara(addStr2);//指定传入的参数
strSQL=mySql43.getString();
		 turnPage2.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  //prompt("",strSQL);
		 if(!turnPage2.strQueryResult){
		    alert("没有录入过问题件！");
		    return "";
		 }
  }
  
  //查询成功则拆分字符串,返回二维数组
  turnPage2.arrDataCacheSet = decodeEasyQueryResult(turnPage2.strQueryResult);
  
  var returnstr = "";
  var tcont = "";
  var treply = "";
  var ttype = "";
  var tOperatePos = "";
  var tOperator="";
  var tMakeDate = "";
  var tModifyDate="";
  var n = turnPage2.arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
  	m = turnPage2.arrDataCacheSet[0].length;
  		//alert("M:"+m);
  		if (m > 1)
  		{
  			//alert("turnPage1:"+turnPage1.arrDataCacheSet[0][0]);
			tcont = turnPage2.arrDataCacheSet[0][0];
			treply = turnPage2.arrDataCacheSet[0][1];
			ttype = turnPage2.arrDataCacheSet[0][2];
			tOperatePos = turnPage2.arrDataCacheSet[0][3];
			tOperator= turnPage2.arrDataCacheSet[0][4];
			tMakeDate = turnPage2.arrDataCacheSet[0][5];
			tModifyDate= turnPage2.arrDataCacheSet[0][6];
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
   
   var mySql44=new SqlClass();
   var sqlid44="QuestInputSql44";
mySql44.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql44.setSqlId(sqlid44);//指定使用的Sql的id
mySql44.addSubPara(tErrFlag);//指定传入的参数

   var tSQL_Err=mySql44.getString();	
   
   //var tSQL_Err = "select codename from ldcode where codetype='yesno' and code='"+tErrFlag+"' ";
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
		document.getElementById("fm").submit();	
}
function reply()
{
	var tsel=QuestGrid.getSelNo()-1; 
   if(tsel<0){
     alert("请选择要回复问题件!");
     return;	 
   }
   var replydate=QuestGrid.getRowColData(tsel,12);
   //alert(replydate);
  if (replydate!='') {
    alert("该问题件已回复,不能再次回复！");  
    return;
  }

	fm.QuesFlag.value = "";
  if (fm.ReplyResult.value == "") {
    alert("没有回复内容,不能回复！");  
    return;
  }  
  
	cContNo = fm.ContNo.value;  //保单号码
	cQuest = fm.HideQuest.value;            //问题件类型
	cflag = fm.HideOperatePos.value;        //问题件操作位置 
	//add by lzf 
	ActivityID = fm.ActivityID.value;   //活动号
	//alert("ActivityID-------->"+ActivityID);return false;
	fm.ContNoHide.value = fm.ContNo.value;  //保单号码
	fm.Quest.value = fm.HideQuest.value;            //问题件类型
	//fm.Flag.value = fm.HideOperatePos.value;        //问题件操作位置 
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
	//if (tFlag == "1")
	//{
	
	   var mySql45=new SqlClass();
var sqlid45="QuestInputSql45";
mySql45.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql45.setSqlId(sqlid45);//指定使用的Sql的id
mySql45.addSubPara("1");//指定传入的参数

strSQL=mySql45.getString();	
	
//		strSQL = "select code,cont from ldcodemod where "+k+"="+k				 	
//				 + " and codetype = 'Question'";
	//}
	
	//alert(strSQL);
	
  //查询SQL,返回结果字符串
  turnPage1.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage1.strQueryResult) {
    alert("没有问题件描述");
    return "";
  }
  
  //查询成功则拆分字符串,返回二维数组
  turnPage1.arrDataCacheSet = decodeEasyQueryResult(turnPage1.strQueryResult);
  
  //设置初始化过的MULTILINE对象,HealthGrid为在初始化页中定义的全局变量
  //turnPage1.pageDisplayGrid = QuestGrid;    
          
  //保存SQL语句
  //turnPage1.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  //turnPage1.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  //var arrDataSet           = turnPage1.getData(turnPage1.arrDataCacheSet, turnPage1.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  //displayMultiline(arrDataSet, turnPage1.pageDisplayGrid);
  var returnstr = "";
  var n = turnPage1.arrDataCacheSet.length;  
    if (n > 0)
  {
  	for( i = 0;i < n; i++)
  	{
  		m = turnPage1.arrDataCacheSet[i].length;
  		//alert("M:"+m);
  		if (m > 0)
  		{
  			for( j = 0; j< m; j++)
  			{
  				if (i == 0 && j == 0)
  				{
  					returnstr = "0|^"+turnPage1.arrDataCacheSet[i][j];
  				}
  				if (i == 0 && j > 0)
  				{
  					returnstr = returnstr + "|" + turnPage1.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j == 0)
  				{
  					returnstr = returnstr+"^"+turnPage1.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j > 0)
  				{
  					returnstr = returnstr+"|"+turnPage1.arrDataCacheSet[i][j];
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
//	 alert(ContNo); 
	 if(tsel<0){
     alert("请选择保单!");
     return;	 
   }
   var ContNo=QuestGrid.getRowColData(tsel,1); 
   if(ContNo.length!=14){
   	
		   var mySql46=new SqlClass();
		   var sqlid46="QuestInputSql46";
mySql46.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql46.setSqlId(sqlid46);//指定使用的Sql的id
mySql46.addSubPara(ContNo);//指定传入的参数

	   var PrtSql =mySql46.getString();	
	
	 //  var PrtSql = "select prtno from lccont where contno='"+ContNo+"'";
	   var ContNo = easyExecSql(PrtSql);
   }
	window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);								
 	
}

//问题件回复完毕函数
function finishMission()
{
	var ActivityID = fm.ActivityID.value;
//	alert("ActivityID----"+ActivityID);
//	return false;
	//判断是否所有需要回复的问题件都做了回复
	//tongmeng 2009-05-18 modify
	//问题件支持needprint='Y'
	
			   var mySql47=new SqlClass();
			   var sqlid47="QuestInputSql47";
mySql47.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql47.setSqlId(sqlid47);//指定使用的Sql的id
mySql47.addSubPara(manageCom);//指定传入的参数
mySql47.addSubPara(fm.ContNo.value);//指定传入的参数
mySql47.addSubPara(fm.BackObj.value);//指定传入的参数
mySql47.addSubPara(fm.ManageCom.value);//指定传入的参数
mySql47.addSubPara(fm.OManageCom.value);//指定传入的参数
 var strSQL =mySql47.getString();	
/*	
	var		strSQL = "select * from lcissuepol where 1=1 "				 	
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + " and replyresult is not null "
				 + " and state is not null and state='1' "
				 + " and needprint='Y' "
				 ;	*/  
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

 		fm.action = "./QuestMissionChk.jsp?MissionId="+tMissionID+"&SubMissionId="+tSubMissionID+"&ContNo="+tContNo+"&ActivtiyID="+ActivityID+" ";
 		document.all('hiddenOperate').value="finishMission";
 		lockScreen('lkscreen');  
 		document.getElementById("fm").submit();
	}
	else
	{
		alert("有未回复的机构问题件,请先回复");
		return false;
	}
}


//tongmeng 2009-03-26 add
//增加计入误发按钮.


function UpdateErrorFlag()
{
	fm.QuesFlag.value = "";
  if (fm.ReplyResult.value == "") {
    alert("没有回复内容,请先回复再修改误发标记!");  
    return;
  }
  
	cContNo = fm.ContNo.value;  //保单号码
	cQuest = fm.HideQuest.value;            //问题件类型
	cflag = fm.HideOperatePos.value;        //问题件操作位置 
	
	fm.ContNoHide.value = fm.ContNo.value;  //保单号码
	fm.Quest.value = fm.HideQuest.value;            //问题件类型
	//fm.Flag.value = fm.HideOperatePos.value;        //问题件操作位置 
	fm.SerialNo.value = fm.HideSerialNo.value;
	fm.ErrorFlagAction.value = "1";
	if(cQuest == "")
	{
		alert("请选择要计入误发的问题件!");
	}
	else
	{
		//showModalDialog("./QuestInputMain.jsp?ContNo1="+cContNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
		//window.open("./QuestReplyMain.jsp?ContNo1="+cContNo+"&Flag="+cflag+"&Quest="+cQuest,"window2");
		fm.action = "./QuestReplyChk.jsp";
		document.getElementById("fm").submit();
	}
	
}
