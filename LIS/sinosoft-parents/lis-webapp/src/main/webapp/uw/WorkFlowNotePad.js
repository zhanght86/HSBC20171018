var showInfo;
var turnPage = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
function initQuery()
{

	var strSql;
	//个单
	
		var sqlid1="WorkFlowNotePadSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.WorkFlowNotePadSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(fm.MissionID.value);//指定传入的参数
	    strSql=mySql1.getString();	
	
//		strSql = " SELECT P.OTHERNO,P.NOTEPADCONT, D.activityname, P.OPERATOR, P.MAKEDATE, P.MAKETIME  " 
//				 + " FROM LWNOTEPAD P, lwactivity D " 
//				 + "WHERE D.ACTIVITYID = P.ACTIVITYID "
//				 + getWherePart('P.MISSIONID', 'MissionID')
//				 + "ORDER BY P.MAKEDATE, P.MAKETIME ";

	//alert(fm.NoType.value);
	if(fm.NoType.value == "1")
	{
		//个单
//		strSql = " SELECT P.OTHERNO,P.NOTEPADCONT, D.activityname, P.OPERATOR, P.MAKEDATE, P.MAKETIME  " 
//				 + " FROM LWNOTEPAD P, lwactivity D " 
//				 + "WHERE D.ACTIVITYID = P.ACTIVITYID "
//				 + getWherePart('P.MISSIONID', 'MissionID');
				 
		var sqlid2="WorkFlowNotePadSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.WorkFlowNotePadSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(fm.MissionID.value);//指定传入的参数
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
				 mySql2.addSubPara(addStr);//指定使用的Sql的id
				 
				// alert("mySql2"+mySql2);
				 strSql=mySql2.getString();

	}
	if(fm.NoType.value == "2")
	{
		//团单
		
		var sqlid3="WorkFlowNotePadSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("uw.WorkFlowNotePadSql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(fm.MissionID.value);//指定传入的参数
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
		alert("必须填写记事本内容！");
		return;
	}

	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;   
	//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
 
	fm.action =  "./WorkFlowNotePadSave.jsp";
	
	document.getElementById("fm").submit(); //提交	
	
}

function afterSubmit( FlagStr, content )
{
	//alert(0);
	showInfo.close();
            
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
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