var showInfo;
var turnPage = new turnPageClass();
var sqlresourcename = "uwgrp.WorkFlowNotePadInputSql";
function initQuery()
{

	var strSql;
	
	if(fm.NoType.value == "1")
	{
		//����
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
		//�ŵ�
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
	
	
	//�ŵ��ٱ���
	if(fm.NoType.value == "3")
	{
		//�ŵ�
		/*
		strSql = " SELECT P.OTHERNO, P.NOTEPADCONT, '���屣���ٱ���', P.OPERATOR, P.MAKEDATE, P.MAKETIME  " 
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
		alert("������д���±����ݣ�");
		return;
	}

	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;    
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	 //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

	fm.action =  "../uwgrp/WorkFlowNotePadSave.jsp";
	fm.submit(); //�ύ	
}

function afterSubmit( FlagStr, content )
{
	showInfo.close();
            
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	 //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
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