var showInfo;
var turnPage = new turnPageClass();
var tResourceName="uw.UWReportSql";

function initQuery()
{

	var strSql;
		//strSql = " SELECT P.otherno,P.reportcont, P.UWOPERATOR, P.MAKEDATE, P.MAKETIME  " 
		//		 + " FROM LCUWReport P " 
		//		 + "WHERE 1=1 "
		//		 + getWherePart('P.otherno', 'ContNo')
		//		 + "ORDER BY P.MAKEDATE, P.MAKETIME ";
		
    //20009-1-7 ln add --初始化查询商业因素标准体承保标记
    if(OperatePos == null || OperatePos==''||OperatePos=='null' ||OperatePos == "1")
    {
    	//strSql = "select bussflag from lccont where contno='"+fm.ContNo.value+"'";
    	strSql = wrapSql(tResourceName,"querysqldes1",[fm.ContNo.value]);
		var tBussFlag = easyExecSql(strSql);
	      if(tBussFlag!=null){
	       	  if(tBussFlag == 'Y')
	       	  	fm.BussFlag.checked = true;
	       	  else
	       	  	fm.BussFlag.checked = false;
	      }
    }
    else if(fm.OperatePos.value == "3")
    {
    	//strSql = "select bussflag from lpcont where contno='"+fm.ContNo.value+"' and EdorNo='"+EdorNo+"'";
    	strSql = wrapSql(tResourceName,"querysqldes2",[fm.ContNo.value,EdorNo]);
		var tBussFlag = easyExecSql(strSql);
	      if(tBussFlag!=null){
	       	  if(tBussFlag == 'Y')
	       	  	fm.BussFlag.checked = true;
	       	  else
	       	  	fm.BussFlag.checked = false;
	      }
    }
      
	if(fm.NoType.value == "1")
	{
		//个单
		/*strSql = " SELECT P.otherno,P.reportcont, P.UWOPERATOR, P.MAKEDATE, P.MAKETIME,serialno   " 
				 + " FROM LCUWReport P " 
				 + " WHERE 1=1 and p.othernotype = '1' ";*/
				 var sqltemp="";
				 if(fm.OperatePos.value == null ||OperatePos=='null')
					 //strSql = strSql + " and p.OperatePos is null ";
					 sqltemp= " and (p.OperatePos is null or p.OperatePos = '') ";
				 else
				 	 //strSql = strSql + " and p.OperatePos = '"+fm.OperatePos.value+"' "
				 	 sqltemp=" and p.OperatePos = '"+fm.OperatePos.value+"' ";
				 //strSql = strSql + getWherePart('P.otherno', 'ContNo')
				 /*strSql = strSql + " and P.otherno in (select prtno from lccont where contno='"+fm.ContNo.value+"' "
				 + " union select proposalno from lcpol where contno='"+fm.ContNo.value+"' or prtno='"+fm.ContNo.value+"'"
				 + " union select '"+fm.ContNo.value+"' from dual )"				 
				 + " ORDER BY P.MAKEDATE, P.MAKETIME ";*/
		strSql = wrapSql(tResourceName,"querysqldes3",[sqltemp,fm.ContNo.value]);

	}
	if(fm.NoType.value == "2")
	{
		//团单
		/*strSql = " SELECT P.otherno,P.reportcont, P.UWOPERATOR, P.MAKEDATE, P.MAKETIME,serialno " 
				 + " FROM LCUWReport P " 
				 + " WHERE 1=1 and p.othernotype = '2' "
				 + " and p.OperatePos = '"+fm.OperatePos.value+"' "
				 + getWherePart('P.otherno', 'ContNo')
				 + "ORDER BY P.MAKEDATE, P.MAKETIME ";*/
		strSql = wrapSql(tResourceName,"querysqldes4",[fm.OperatePos.value,document.all("ContNo").value]);
	}
	//理赔
	if(fm.NoType.value == "4")
	{
		//个单
		/*strSql = " SELECT P.otherno,P.reportcont, P.UWOPERATOR, P.MAKEDATE, P.MAKETIME,serialno   " 
				 + " FROM LCUWReport P " 
				 + " WHERE 1=1 and p.othernotype = '4' "
				 + " and p.OperatePos = '"+fm.OperatePos.value+"' "
				 + getWherePart('P.otherno', 'ClmNo')
				 + "ORDER BY P.MAKEDATE, P.MAKETIME ";*/
		strSql = wrapSql(tResourceName,"querysqldes5",[fm.OperatePos.value,document.all("ClmNo").value]);
	}
	
	//核保查询
	if(fm.NoType.value == "5")
	{
		//个单
		/*strSql = " SELECT P.otherno,P.reportcont, P.UWOPERATOR, P.MAKEDATE, P.MAKETIME,serialno   " 
				 + " FROM LCUWReport P " 
				 + " WHERE 1=1 and p.othernotype = '1' "
				 //+ " and p.OperatePos in ('1','2','3','4') "
				 + " and P.otherno in (select prtno from lccont where contno='"+fm.ContNo.value+"'"
				 + " union select '"+fm.ContNo.value+"' from dual "
				 + " union select proposalno from lcpol where contno='"+fm.ContNo.value+"')"
				 + "ORDER BY P.MAKEDATE, P.MAKETIME ";*/
		strSql = wrapSql(tResourceName,"querysqldes6",[fm.ContNo.value]);
	}
//	prompt("",strSql);
	//alert(111);
	turnPage.queryModal(strSql, ReportGrid); 	
}

function addReport()
{

    if (document.all("BussFlag").checked == true) 
		fm.BussFlagSave.value = 'Y';
	else
		fm.BussFlagSave.value = 'N';
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
 
	fm.action =  "./UWReportSave.jsp";
	
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
		top.opener.checkReport(fm.ContNo.value);
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
		//		 + " FROM LCUWReport P " 
		//		 + "WHERE 1=1 "
		//		 + getWherePart('P.otherno', 'ContNo')
		//		 + "ORDER BY P.MAKEDATE, P.MAKETIME ";
	
	if(fm.NoType.value == "1")
	{
		//个单
		/*strSql = " SELECT P.reportcont  " 
				 + " FROM LCUWReport P " 
				 + " WHERE 1=1"
//				 + " and p.othernotype = '1' "
				 //+ getWherePart('P.otherno', 'ContNo')
				 + " and serialno = '"+ ReportGrid.getRowColData(ReportGrid.getSelNo()-1,6) +"'"
				 + "ORDER BY P.MAKEDATE, P.MAKETIME ";*/
		strSql = wrapSql(tResourceName,"querysqldes7",[ReportGrid.getRowColData(ReportGrid.getSelNo()-1,6)]);

	}
	if(fm.NoType.value == "2")
	{
		//团单
		/*strSql = " SELECT P.reportcont  " 
				 + " FROM LCUWReport P " 
				 + " WHERE 1=1 and p.othernotype = '2' "
				 + getWherePart('P.otherno', 'ContNo')
				 + " and serialno = '"+ ReportGrid.getRowColData(ReportGrid.getSelNo()-1,6) +"'"
				 + "ORDER BY P.MAKEDATE, P.MAKETIME ";*/
		strSql = wrapSql(tResourceName,"querysqldes8",[document.all("ContNo").value, ReportGrid.getRowColData(ReportGrid.getSelNo()-1,6)]);
	}
	if(fm.NoType.value == "4")
	{
		//理赔
		/*strSql = " SELECT P.reportcont  " 
				 + " FROM LCUWReport P " 
				 + " WHERE 1=1 and p.othernotype = '4' "
				 + getWherePart('P.otherno', 'ClmNo')
				 + " and serialno = '"+ ReportGrid.getRowColData(ReportGrid.getSelNo()-1,6) +"'"
				 + "ORDER BY P.MAKEDATE, P.MAKETIME ";*/
		strSql = wrapSql(tResourceName,"querysqldes9",[document.all("ClmNo").value, ReportGrid.getRowColData(ReportGrid.getSelNo()-1,6)]);
	}
	
	if(fm.NoType.value == "5")
	{
		//保单核保查询
		/*strSql = " SELECT P.reportcont  " 
				 + " FROM LCUWReport P " 
				 + " WHERE 1=1 "
//				 +"and p.othernotype = '4' "
				 //+ " and P.otherno in ((select prtno from lccont where contno='"+fm.ContNo.value+"'),'"+fm.ContNo.value+"' )"
				 + " and serialno = '"+ ReportGrid.getRowColData(ReportGrid.getSelNo()-1,6) +"'"
				 + "ORDER BY P.MAKEDATE, P.MAKETIME ";*/
		strSql = wrapSql(tResourceName,"querysqldes7",[ReportGrid.getRowColData(ReportGrid.getSelNo()-1,6)]);
	}
	var Content = easyExecSql( strSql );
	//var Content = ReportGrid.getRowColData(selno, 2);
	fm.Content.value = Content;
}