var showInfo;
var turnPage = new turnPageClass();
var sqlresourcename = "uwgrp.WorkFlowNotePadInputSql";
function initQuery()
{

	var strSql;
	
	if(fm.NoType.value == "1")
	{
		//个单
		/*
		strSql = " SELECT P.OTHERNO,P.NOTEPADCONT, D.activityname, P.OPERATOR, P.MAKEDATE, P.MAKETIME  " 
				 + " FROM LWNOTEPAD P, lwactivity D " 
				 + "WHERE D.ACTIVITYID = P.ACTIVITYID "
				 + getWherePart('P.MISSIONID', 'MissionID')
				 //+ getWherePart('P.SUBMISSIONID', 'SubMissionID')
				 //+ getWherePart('P.ACTIVITYID', 'ActivityID')
				 //+ getWherePart('P.OTHERNOTYPE', 'NoType')
				 //+ getWherePart('P.OTHERNO', 'PrtNo')
				 + "ORDER BY P.MAKEDATE, P.MAKETIME ";
*/
		var sqlid1="WorkFlowNotePadInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(fm.MissionID.value);
		
		strSql = mySql1.getString();
	}
	if(fm.NoType.value == "2")
	{
		//团单
		/*
		strSql = " SELECT P.OTHERNO, P.NOTEPADCONT, D.activityname, P.OPERATOR, P.MAKEDATE, P.MAKETIME  " 
				 + " FROM LWNOTEPAD P, lwactivity D " 
				 + "WHERE D.ACTIVITYID = P.ACTIVITYID "
				 + getWherePart('P.MISSIONID', 'MissionID')
				 //+ getWherePart('P.SUBMISSIONID', 'SubMissionID')
				 //+ getWherePart('P.ACTIVITYID', 'ActivityID')
				 //+ getWherePart('P.OTHERNOTYPE', 'NoType')
				 //+ getWherePart('P.OTHERNO', 'PrtNo')
				 + "ORDER BY P.MAKEDATE, P.MAKETIME ";
			*/	 
		var sqlid2="WorkFlowNotePadInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(fm.MissionID.value); 
		
		strSql = mySql2.getString();
	}	
	
	
	//团单再保险
	if(fm.NoType.value == "3")
	{
		//团单
		/*
		strSql = " SELECT P.OTHERNO, P.NOTEPADCONT, '团体保单再保险', P.OPERATOR, P.MAKEDATE, P.MAKETIME  " 
				 + " FROM LWNOTEPAD P " 
				 + "WHERE 1=1 "
				 + getWherePart('P.MISSIONID', 'MissionID')
				 //+ getWherePart('P.SUBMISSIONID', 'SubMissionID')
				 //+ getWherePart('P.ACTIVITYID', 'ActivityID')
				 //+ getWherePart('P.OTHERNOTYPE', 'NoType')
				 //+ getWherePart('P.OTHERNO', 'PrtNo')
				 + "ORDER BY P.MAKEDATE, P.MAKETIME ";
				 */
		var sqlid3="WorkFlowNotePadInputSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(fm.MissionID.value); 
		strSql = mySql3.getString();
	}	


	
	turnPage.queryModal(strSql, QuestGrid); 	
}

function addNote()
{

	if (trim(fm.Content.value) == "") 
	{
		alert("必须填写记事本内容！");
		return;
	}

	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;    
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	 //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

	fm.action =  "../uwgrp/WorkFlowNotePadSave.jsp";
	fm.submit(); //提交	
}

function afterSubmit( FlagStr, content )
{
	showInfo.close();
            
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	 //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
	if(FlagStr == "Succ")
	{
		initQuery();
	}
	
}

function showContent()
{
	var selno = QuestGrid.getSelNo()-1;
	if (selno <0)
	{
	      return;
	}	
	var Content = QuestGrid.getRowColData(selno, 2);
	fm.Content.value = Content;
}