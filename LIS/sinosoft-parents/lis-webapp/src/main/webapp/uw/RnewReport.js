var showInfo;
var turnPage = new turnPageClass();
var str_sql = "",sql_id = "", my_sql = "";   //绑定变量访问数据库
function initQuery()
{

	var strSql;
		//strSql = " SELECT P.otherno,P.reportcont, P.UWOPERATOR, P.MAKEDATE, P.MAKETIME  " 
		//		 + " FROM RnewUWReport P " 
		//		 + "WHERE 1=1 "
		//		 + getWherePart('P.otherno', 'ContNo')
		//		 + "ORDER BY P.MAKEDATE, P.MAKETIME ";
	
	if(fm.NoType.value == "1")
	{
		//个单
//		strSql = " SELECT P.otherno,P.reportcont, P.UWOPERATOR, P.MAKEDATE, P.MAKETIME  " 
//				 + " FROM RnewUWReport P " 
//				 + " WHERE 1=1 and p.othernotype = '1' "
//				 + getWherePart('P.otherno', 'ProposalNo')
//				 + "ORDER BY P.MAKEDATE, P.MAKETIME ";
		sql_id = "RnewReportSql0";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewReportSql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(document.getElementsByName(trim('ProposalNo'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();

	}
	if(fm.NoType.value == "2")
	{
		//团单
//		strSql = " SELECT P.otherno,P.reportcont, P.UWOPERATOR, P.MAKEDATE, P.MAKETIME  " 
//				 + " FROM RnewUWReport P " 
//				 + " WHERE 1=1 and p.othernotype = '2' "
//				 + getWherePart('P.otherno', 'ProposalNo')
//				 + "ORDER BY P.MAKEDATE, P.MAKETIME ";
		sql_id = "RnewReportSql1";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewReportSql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(document.getElementsByName(trim('ProposalNo'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();	 
	}	
	//alert(111);
	turnPage.queryModal(str_sql, ReportGrid); 	
}

function addReport()
{

	if (trim(fm.Content.value) == "") 
	{
		alert("必须填写核保分析报告内容！");
		return;
	}

	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;    
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
 
	fm.action =  "./RnewReportSave.jsp";
	
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
		top.opener.checkReport(fm.ProposalNo.value);
		fm.Content.value="";
	}	
}

function showContent()
{
	/*var selno = ReportGrid.getSelNo()-1;
	if (selno <0)
	{
	      return;
	}	*/
	var strSql;
		//strSql = " SELECT P.otherno,P.reportcont, P.UWOPERATOR, P.MAKEDATE, P.MAKETIME  " 
		//		 + " FROM RnewUWReport P " 
		//		 + "WHERE 1=1 "
		//		 + getWherePart('P.otherno', 'ContNo')
		//		 + "ORDER BY P.MAKEDATE, P.MAKETIME ";
	
	if(fm.NoType.value == "1")
	{
		//个单
//		strSql = " SELECT P.reportcont  " 
//				 + " FROM RnewUWReport P " 
//				 + " WHERE 1=1 and p.othernotype = '1' "
//				 + getWherePart('P.otherno', 'ProposalNo')
//				 + "ORDER BY P.MAKEDATE, P.MAKETIME ";
		sql_id = "RnewReportSql2";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewReportSql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(document.getElementsByName(trim('ProposalNo'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();
	}
	if(fm.NoType.value == "2")
	{
		//团单
//		strSql = " SELECT P.reportcont  " 
//				 + " FROM RnewUWReport P " 
//				 + " WHERE 1=1 and p.othernotype = '2' "
//				 + getWherePart('P.otherno', 'ProposalNo')
//				 + "ORDER BY P.MAKEDATE, P.MAKETIME ";
		sql_id = "RnewReportSql3";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewReportSql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(document.getElementsByName(trim('ProposalNo'))[0].value);//指定传入的参数
		str_sql = my_sql.getString(); 
	}
	var Content = easyExecSql( str_sql );
	//var Content = ReportGrid.getRowColData(selno, 2);
	fm.Content.value = Content;
}