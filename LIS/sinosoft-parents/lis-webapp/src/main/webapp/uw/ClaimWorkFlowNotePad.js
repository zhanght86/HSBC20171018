var showInfo;
var turnPage = new turnPageClass();
var tResourceName="uw.ClaimWorkFlowNotePadInputSql";

function initQuery()
{

	var strSql;
	//����
		/*strSql = " SELECT P.OTHERNO,P.NOTEPADCONT, D.activityname, P.OPERATOR, P.MAKEDATE, P.MAKETIME  " 
				 + " FROM LWNOTEPAD P, lwactivity D " 
				 + "WHERE D.ACTIVITYID = P.ACTIVITYID "
				 + getWherePart('P.MISSIONID', 'MissionID')
				 //+ getWherePart('P.SUBMISSIONID', 'SubMissionID')
				 //+ getWherePart('P.ACTIVITYID', 'ActivityID')
				 //+ getWherePart('P.OTHERNOTYPE', 'NoType')
				 //+ getWherePart('P.OTHERNO', 'PrtNo')
				 + "ORDER BY P.MAKEDATE, P.MAKETIME ";*/
		strSql = wrapSql(tResourceName,"querysqldes1",[document.all('MissionID').value]);
	
	if(fm.NoType.value == "1")
	{
		//����
		/*strSql = " SELECT P.OTHERNO,P.NOTEPADCONT, D.activityname, P.OPERATOR, P.MAKEDATE, P.MAKETIME  " 
				 + " FROM LWNOTEPAD P, lwactivity D " 
				 + "WHERE D.ACTIVITYID = P.ACTIVITYID "
				 + getWherePart('P.MISSIONID', 'MissionID');
				 //+ getWherePart('P.SUBMISSIONID', 'SubMissionID')
				 //+ getWherePart('P.ACTIVITYID', 'ActivityID')
				 //+ getWherePart('P.OTHERNOTYPE', 'NoType')
				 if(QueryFlag ==null || QueryFlag=='')
				 {}
				 else
				   strSql = strSql + getWherePart('P.OTHERNO', 'PrtNo');
				   
				 strSql = strSql + "ORDER BY P.MAKEDATE, P.MAKETIME ";*/
		strSql = wrapSql(tResourceName,"querysqldes2",[document.all('MissionID').value,document.all('PrtNo').value]);

	}
	if(fm.NoType.value == "2")
	{
		//�ŵ�
		/*strSql = " SELECT P.OTHERNO, P.NOTEPADCONT, D.activityname, P.OPERATOR, P.MAKEDATE, P.MAKETIME  " 
				 + " FROM LWNOTEPAD P, lwactivity D " 
				 + "WHERE D.ACTIVITYID = P.ACTIVITYID "
				 + getWherePart('P.MISSIONID', 'MissionID')
				 //+ getWherePart('P.SUBMISSIONID', 'SubMissionID')
				 //+ getWherePart('P.ACTIVITYID', 'ActivityID')
				 //+ getWherePart('P.OTHERNOTYPE', 'NoType')
				 //+ getWherePart('P.OTHERNO', 'PrtNo')
				 + "ORDER BY P.MAKEDATE, P.MAKETIME ";*/
		strSql = wrapSql(tResourceName,"querysqldes1",[document.all('MissionID').value]);
	}	
	//alert(111);
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
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

 
	fm.action =  "./WorkFlowNotePadSave.jsp";
	
	fm.submit(); //�ύ	
	
}

function afterSubmit( FlagStr, content )
{
	//alert(0);
	showInfo.close();
            
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	if(FlagStr == "Succ")
	{
		initQuery();
		//alert(fm.PrtNo.value);
		top.opener.checkNotePad(fm.MissionID.value);
	}
	fm.Content.value="";
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


function returnParent()
{
	 try
	 {
	 	 this.close();
	 	}
	 	catch(e)
	 	{
	 		}
}