var showInfo;
var turnPage = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
function initQuery()
{

	var strSql;
	//����
	
		var sqlid1="WorkFlowNotePadSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.WorkFlowNotePadSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.MissionID.value);//ָ������Ĳ���
	    strSql=mySql1.getString();	
	
//		strSql = " SELECT P.OTHERNO,P.NOTEPADCONT, D.activityname, P.OPERATOR, P.MAKEDATE, P.MAKETIME  " 
//				 + " FROM LWNOTEPAD P, lwactivity D " 
//				 + "WHERE D.ACTIVITYID = P.ACTIVITYID "
//				 + getWherePart('P.MISSIONID', 'MissionID')
//				 + "ORDER BY P.MAKEDATE, P.MAKETIME ";

	//alert(fm.NoType.value);
	if(fm.NoType.value == "1")
	{
		//����
//		strSql = " SELECT P.OTHERNO,P.NOTEPADCONT, D.activityname, P.OPERATOR, P.MAKEDATE, P.MAKETIME  " 
//				 + " FROM LWNOTEPAD P, lwactivity D " 
//				 + "WHERE D.ACTIVITYID = P.ACTIVITYID "
//				 + getWherePart('P.MISSIONID', 'MissionID');
				 
		var sqlid2="WorkFlowNotePadSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.WorkFlowNotePadSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(fm.MissionID.value);//ָ������Ĳ���
		var addStr = " and 1=1 " ;
	    	
				// alert("QueryFlag:"+QueryFlag);
				 if(QueryFlag ==null || QueryFlag=='')
				 {
				 //	alert('2');
				 	  addStr = " and 1=1 " ;
				 }
				 else
				 	{
				 		 //	alert('3');
//				   strSql = strSql + getWherePart('P.OTHERNO', 'PrtNo');
             addStr = " and P.OTHERNO='"+fm.PrtNo.value+"'";
             //	alert('4');
            // alert(addStr);
				   }
//				 strSql = strSql + "ORDER BY P.MAKEDATE, P.MAKETIME ";
				 mySql2.addSubPara(addStr);//ָ��ʹ�õ�Sql��id
				 
				// alert("mySql2"+mySql2);
				 strSql=mySql2.getString();

	}
	if(fm.NoType.value == "2")
	{
		//�ŵ�
		
		var sqlid3="WorkFlowNotePadSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("uw.WorkFlowNotePadSql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(fm.MissionID.value);//ָ������Ĳ���
	    strSql=mySql3.getString();	
		
//		strSql = " SELECT P.OTHERNO, P.NOTEPADCONT, D.activityname, P.OPERATOR, P.MAKEDATE, P.MAKETIME  " 
//				 + " FROM LWNOTEPAD P, lwactivity D " 
//				 + "WHERE D.ACTIVITYID = P.ACTIVITYID "
//				 + getWherePart('P.MISSIONID', 'MissionID')
//				 + "ORDER BY P.MAKEDATE, P.MAKETIME ";
			 
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
	//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
 
	fm.action =  "./WorkFlowNotePadSave.jsp";
	
	document.getElementById("fm").submit(); //�ύ	
	
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