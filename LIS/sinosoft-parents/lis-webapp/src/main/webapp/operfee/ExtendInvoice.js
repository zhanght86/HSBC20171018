//               该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var turnPage1 = new turnPageClass();
var mDebug="0";
var mOperate="";
var showInfo;
var ttmanagecom = "";
window.onfocus=myonfocus;

//使得从该窗口弹出的窗口能够聚焦

function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
	}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  {

    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //showDiv(operateButton,"true");
    //showDiv(inputButton,"false");
  }
}

//////////////////////////////////////////////这个是要存储要答应的东西的
function easyQueryAddClick()
{
	//var tSelNo = PolGrid.getSelNo()-1;
	//fm.PolNo.value = PolGrid.getRowColData(tSelNo,1);	
        var arrReturn = new Array();
	var tSel = ContGrid.getSelNo();	 
	if( tSel == 0 || tSel == null )
	{
		alert( "请先选择一条记录，再点击返回按钮。" );
	}	
	else
	{
			var tRow = ContGrid.getSelNo() - 1;	        
             fm.PayCode1.value = ContGrid.getRowColData(tRow,1);
             fm.ContNo1.value = ContGrid.getRowColData(tRow,2);
             fm.AccNo1.value = ContGrid.getRowColData(tRow,3);
             fm.AccName1.value = ContGrid.getRowColData(tRow,4);
             fm.senddate.value = ContGrid.getRowColData(tRow,5);
             fm.paymoney.value = ContGrid.getRowColData(tRow,6); 
             
        } 	
}

//个单发票打印

function PPrint()
{
	var tSel = ContGrid.getSelNo();	 

	if( tSel == 0 || tSel == null )
	{
		alert( "请先选择一条进行打印。" );
	}	
	else
	{
		fm.AppntNoHidden.value = ContGrid.getRowColData(tSel-1,1); 
        fm.AppntNameHidden.value =  ContGrid.getRowColData(tSel-1,2);
	}
	fm.action="XQWoodcutterNoticeSave.jsp";
	fm.target="f1print";
	fm.fmtransact.value="";
	fm.submit();
}

function CheckDataCom()
{
	var tEnterDate = 0 ;

	if (fm.PrintType.value==02&&(fm.TempFeeNo.value==""||fm.TempFeeNo.value==null||fm.TempFeeNo.value=="null"))
	{
		alert("预收据换发票必须录入预存号，请输入预收据上的预存号。");
		fm.TempFeeNo.focus();
		return false;
		}

	 if (fm.StartDate.value !=null&&fm.StartDate.value!=""&& fm.EndDate.value !=null&&fm.EndDate.value!="")
   {
     if(fm.StartDate.value > fm.EndDate.value)
     {
     		alert("开始日期大于终止日期，请确认后重新输入！");
     		return false;
     	}
     tEnterDate = 1;
   }

	if(tEnterDate == 0)
	{
	 if (fm.ContNo.value !=null&&fm.ContNo.value!="")
   {
     	return true;
   }
  else
  	{
  		alert("日期和保单号，至少要录入其一");	
  		return false;
  	}
	}

	return true;
}

function EasyQueryClick()
{
	
	if( verifyInput2() == false ) return false;
	if(CheckDataCom() == false)
	{
				return false;
	}


	if(document.all('PrintType').value == '02')
	{
				EasyQueryb();
	}
 else
 	{
/* 				var checkSql = " select * from ljtempfeeclass "+
		 									 "  where paymode = '7' "+
		 									 "    and bankcode = (select code "+
		 									 "			                from ldcode1 "+
		 									 "                     where codetype = 'BankBigList' "+
		 									 "                       and comcode = '"+comCode.substring(1,4)+"'"+
		 									 "											 and code = ljtempfeeclass.bankcode) "
											 + getWherePart( 'OtherNo','ContNo' )
											 + getWherePart( 'TempFeeNo','GetNoticeNo' )
											 + getWherePart( 'TempFeeNo','TempFeeNo' )*/
				var checkSql= "";
 				var mySql1=new SqlClass();
 			    mySql1.setResourceName("operfee.ExtendInvoiceSql");
 			    mySql1.setSqlId("ExtendInvoiceSql1");
 			    mySql1.addSubPara(comCode.substring(1,4));
 			    mySql1.addSubPara(window.document.getElementsByName(trim('ContNo'))[0].value);
 			    mySql1.addSubPara(window.document.getElementsByName(trim('GetNoticeNo'))[0].value);
 			    mySql1.addSubPara(window.document.getElementsByName(trim('TempFeeNo'))[0].value);
 			   checkSql = mySql1.getString();
				var checkData = easyExecSql(checkSql,1,0);
				if(checkData == null)
				{
							EasyQueryC();
				}
				else
				{
							EasyQueryA();
				}
 	}
}

//大制盘打印发票
function EasyQueryA()
{
	/*var strSql = " select a.otherno,a.StandbyFlag1,a.StandbyFlag3,a.StandbyFlag4 ,"+
							 "   			a.PrtSeq ,'','', a.managecom "+
							 "   from loprtmanager a, ljtempfee b "+
							 " 	where 1=1 and b.tempfeeno = a.StandbyFlag1 "+
							 "    and b.enteraccdate is not null  and a.StateFlag = '0' "+
							 "		and b.PolicyCom like '"+comCode+"%%'"+
							 "		and  b.managecom like '"+comCode.substring(1,4)+'%%'+"'"
							 + getWherePart( 'a.OtherNo','ContNo' )
							 + getWherePart( 'b.TempFeeNo','TempFeeNo')
							 + getWherePart( 'a.StandbyFlag1','GetNoticeNo' )
							 + getWherePart( 'a.StandbyFlag3','StartDate','>=')
							 + getWherePart( 'a.StandbyFlag3','EndDate','<=' )

   if(fm.ManageCom.value!=null&&fm.ManageCom.value!="")
   {
   	 strSql = strSql + " and b.PolicyCom like '"+fm.ManageCom.value+"%%'";
   }

   if(fm.SecPayMode.value!=null&&fm.SecPayMode.value!="")
   {
   	 strSql = strSql + " and a.otherno in (select contno from lccont where paymode ='"+fm.SecPayMode.value+"' )";
   }

  if(fm.ContType.value !=null && fm.ContType.value != "")
  {
  	 if(fm.ContType.value == "01")
  	 {
  	 	strSql = strSql + " and a.code = '32'";
  	 }
  	 if(fm.ContType.value == "02")
  	 {
  	 	strSql = strSql + " and a.code = '33'";
  	 }
  	 if(fm.ContType.value == "03")
  	 {
  	 	strSql = strSql + " and a.code = '36'";
  	 }
  	 if(fm.ContType.value == "04")
  	 {
  	 	strSql = strSql + " and a.code = '36'";
  	 }
  }
	else
	{
		strSql = strSql + " and a.code in ('32','33','36')";
	}*/
	

	var addstr0 = "";
	if (fm.SecPayMode.value != null && fm.SecPayMode.value != "") {
		addstr0 = addstr0
				+ " and a.otherno in (select contno from lccont where paymode ='"
				+ fm.SecPayMode.value + "' )";
	}
	var addstr = "";
	if (fm.ContType.value != null && fm.ContType.value != "") {
		if (fm.ContType.value == "01") {
			addstr = addstr + " and a.code = '32'";
		}
		if (fm.ContType.value == "02") {
			addstr = addstr + " and a.code = '33'";
		}
		if (fm.ContType.value == "03") {
			addstr = addstr + " and a.code = '36'";
		}
		if (fm.ContType.value == "04") {
			addstr = addstr + " and a.code = '36'";
		}
	} else {
		addstr = addstr + " and a.code in ('32','33','36')";
	}
   var strSql = "";
   var mySql2=new SqlClass();
   mySql2.setResourceName("operfee.ExtendInvoiceSql");
   mySql2.setSqlId("ExtendInvoiceSql2");
   mySql2.addSubPara(comCode);
   mySql2.addSubPara(comCode.substring(1,4));
   mySql2.addSubPara(window.document.getElementsByName(trim('ContNo'))[0].value);
   mySql2.addSubPara(window.document.getElementsByName(trim('TempFeeNo'))[0].value);
   mySql2.addSubPara(window.document.getElementsByName(trim('GetNoticeNo'))[0].value);
   mySql2.addSubPara(window.document.getElementsByName(trim('StartDate'))[0].value);
   mySql2.addSubPara(window.document.getElementsByName(trim('EndDate'))[0].value);
   mySql2.addSubPara(fm.ManageCom.value);
   mySql2.addSubPara(addstr0);
   mySql2.addSubPara(addstr);
   strSql = mySql2.getString();
   turnPage.queryModal(strSql, ContGrid);

	if(ContGrid.mulLineCount ==0)
	{
		alert("没有符合条件的数据");
		initContGrid();
	}
}


//预收据换发票
function EasyQueryb()
{
	/*var strSql = " select a.otherno,a.StandbyFlag1,a.StandbyFlag3,a.StandbyFlag4 ,a.PrtSeq ,'','',"+
							 " 				a.managecom from loprtmanager a  , ljapay b ,ljtempfee c "+
							 "	where 1=1 and b.otherno = c.otherno and b.getnoticeno = a.StandbyFlag1 "+
							 "    and b.enteraccdate is not null  and a.StateFlag = '0' and c.tempfeetype = '3'"+
							 " 		and c.managecom like '"+comCode+"%%'"
							 + getWherePart( 'a.OtherNo','ContNo' )
							 + getWherePart( 'c.TempFeeNo','TempFeeNo' )
							 + getWherePart( 'a.StandbyFlag1','GetNoticeNo' )
							 + getWherePart( 'a.StandbyFlag3','StartDate','>=' )
							 + getWherePart( 'a.StandbyFlag3','EndDate','<=' )
							 + getWherePart( 'c.ManageCom','ManageCom','like' )

   if(fm.SecPayMode.value!=null&&fm.SecPayMode.value!="")
   {
   	 strSql = strSql + " and a.otherno in (select contno from lccont where paylocation ='"+fm.SecPayMode.value+"' )";
   }

  if(fm.ContType.value !=null && fm.ContType.value != "")
  {
  	 if(fm.ContType.value == "01")
  	 {
  	 	strSql = strSql + " and a.code = '32'";
  	 }
  	 if(fm.ContType.value == "02")
  	 {
  	 	strSql = strSql + " and a.code = '33'";
  	 }
  	 if(fm.ContType.value == "03")
  	 {
  	 	strSql = strSql + " and a.code = '36'";
  	 }
  	 if(fm.ContType.value == "04")
  	 {
  	 	strSql = strSql + " and a.code = '36'";
  	 }
  }
	else
	{
		strSql = strSql + " and a.code in ('32','33','36')";
	}*/
	var addstr0 = "";
	if (fm.SecPayMode.value != null && fm.SecPayMode.value != "") {
		addstr0 = addstr0
				+ " and a.otherno in (select contno from lccont where paylocation ='"
				+ fm.SecPayMode.value + "' )";
	}
	var addstr = "";
	if (fm.ContType.value != null && fm.ContType.value != "") {
		if (fm.ContType.value == "01") {
			addstr = addstr + " and a.code = '32'";
		}
		if (fm.ContType.value == "02") {
			addstr = addstr + " and a.code = '33'";
		}
		if (fm.ContType.value == "03") {
			addstr = addstr + " and a.code = '36'";
		}
		if (fm.ContType.value == "04") {
			addstr = addstr + " and a.code = '36'";
		}
	} else {
		addstr = addstr + " and a.code in ('32','33','36')";
	}
   var strSql = "";
   var mySql3=new SqlClass();
   mySql3.setResourceName("operfee.ExtendInvoiceSql");
   mySql3.setSqlId("ExtendInvoiceSql2");
   mySql3.addSubPara(comCode);
   mySql3.addSubPara(window.document.getElementsByName(trim('ContNo'))[0].value);
   mySql3.addSubPara(window.document.getElementsByName(trim('TempFeeNo'))[0].value);
   mySql3.addSubPara(window.document.getElementsByName(trim('GetNoticeNo'))[0].value);
   mySql3.addSubPara(window.document.getElementsByName(trim('StartDate'))[0].value);
   mySql3.addSubPara(window.document.getElementsByName(trim('EndDate'))[0].value);
   mySql3.addSubPara(fm.ManageCom.value);
   mySql3.addSubPara(addstr0);
   mySql3.addSubPara(addstr);
   strSql = mySql3.getString();
   turnPage.queryModal(strSql, ContGrid);

	if(ContGrid.mulLineCount ==0)
	{
		alert("没有符合条件的数据");
		initContGrid();
	}
}


//正常发票打印
function EasyQueryC()
{
	/*var strSql = " select a.otherno,a.StandbyFlag1,a.StandbyFlag3,a.StandbyFlag4 ,"+
							 "   			a.PrtSeq ,'','', a.managecom "+
							 "   from loprtmanager a, ljtempfee b "+
							 " 	where 1=1 and b.tempfeeno = a.StandbyFlag1 "+
							 "    and b.enteraccdate is not null  and a.StateFlag = '0' "+
							 "    and exists (select 'X' from ljtempfeeclass where tempfeeno = a.standbyflag1 and managecom like '"+comCode+"%%') "+
							 "		and b.managecom like '"+comCode+"%%'"
							 + getWherePart( 'a.OtherNo','ContNo' )
							 + getWherePart( 'b.TempFeeNo','TempFeeNo')
							 + getWherePart( 'a.StandbyFlag1','GetNoticeNo' )
							 + getWherePart( 'a.StandbyFlag3','StartDate','>=')
							 + getWherePart( 'a.StandbyFlag3','EndDate','<=' )
							 + getWherePart( 'a.ManageCom','ManageCom','like' )

   if(fm.SecPayMode.value!=null&&fm.SecPayMode.value!="")
   {
   	 strSql = strSql + " and a.otherno in (select contno from lccont where paymode ='"+fm.SecPayMode.value+"' )";
   }

  if(fm.ContType.value !=null && fm.ContType.value != "")
  {
  	 if(fm.ContType.value == "01")
  	 {
  	 	strSql = strSql + " and a.code = '32'";
  	 }
  	 if(fm.ContType.value == "02")
  	 {
  	 	strSql = strSql + " and a.code = '33'";
  	 }
  	 if(fm.ContType.value == "03")
  	 {
  	 	strSql = strSql + " and a.code = '36'";
  	 }  	 
  	 if(fm.ContType.value == "04")
  	 {
  	 	strSql = strSql + " and a.code = '36'";
  	 }
  }
else
	{
		strSql = strSql + " and a.code in ('32','33','36')";
	} */  
	var addstr0 = "";
	if (fm.SecPayMode.value != null && fm.SecPayMode.value != "") {
		addstr0 = addstr0
				+ " and a.otherno in (select contno from lccont where paymode ='"
				+ fm.SecPayMode.value + "' )";
	}
	var addstr = "";
	if (fm.ContType.value != null && fm.ContType.value != "") {
		if (fm.ContType.value == "01") {
			addstr = addstr + " and a.code = '32'";
		}
		if (fm.ContType.value == "02") {
			addstr = addstr + " and a.code = '33'";
		}
		if (fm.ContType.value == "03") {
			addstr = addstr + " and a.code = '36'";
		}
		if (fm.ContType.value == "04") {
			addstr = addstr + " and a.code = '36'";
		}
	} else {
		addstr = addstr + " and a.code in ('32','33','36')";
	}
   var strSql = "";
   var mySql4=new SqlClass();
   mySql4.setResourceName("operfee.ExtendInvoiceSql");
   mySql4.setSqlId("ExtendInvoiceSql2");
   mySql4.addSubPara(comCode);
   mySql4.addSubPara(comCode);
   mySql4.addSubPara(window.document.getElementsByName(trim('ContNo'))[0].value);
   mySql4.addSubPara(window.document.getElementsByName(trim('TempFeeNo'))[0].value);
   mySql4.addSubPara(window.document.getElementsByName(trim('GetNoticeNo'))[0].value);
   mySql4.addSubPara(window.document.getElementsByName(trim('StartDate'))[0].value);
   mySql4.addSubPara(window.document.getElementsByName(trim('EndDate'))[0].value);
   mySql4.addSubPara(fm.ManageCom.value);
   mySql4.addSubPara(addstr0);
   mySql4.addSubPara(addstr);
   strSql = mySql4.getString();
   turnPage.queryModal(strSql, ContGrid);

	if(ContGrid.mulLineCount ==0)
	{
		alert("没有符合条件的数据");
		initContGrid();
	}
}

	function certifyInput()
	{
		var tSel = ContGrid.getSelNo();	
//		var tSql = " select * from lzcard where startno <='"+fm.ChequNo.value+"'  and  endno >='"+fm.ChequNo.value+"' and certifycode = '"+fm.CertifyCode.value+"' and stateflag <> '1' ";
		var tSql = "";
		var mySql5 = new SqlClass();
		mySql5.setResourceName("operfee.ExtendInvoiceSql");
		mySql5.setSqlId("ExtendInvoiceSql5");
		mySql5.addSubPara(fm.ChequNo.value);
		mySql5.addSubPara(fm.ChequNo.value);
		mySql5.addSubPara(fm.CertifyCode.value);
		strSql = mySql5.getString();
		var arrResult3 = easyExecSql(tSql, 1, 0);
		  if(arrResult3 == null)
		  {
		   alert("此发票号不存在,或者没有此单证的类型");	
		   return false;
		  }
		  
	 return true;
	}

function showOne(parm1, parm2) {	
  //判断该行是否确实被选中
	if(document.all(parm1).all('InpPolGridSel').value == '1' ) {
	  var index = (document.all(parm1).all('PolGridNo').value - 1) % (turnPage.blockPageNum * turnPage.pageLineNum);
	  fm.PrtSeq.value = turnPage.arrDataCacheSet[index][0];
  }
}

function NoPrint() {	
  var i = 0;
  var tSel = ContGrid.getSelNo();	
      	 
       if( fm.ChequNo.value =='' ||  fm.ChequNo.value == 'null' )
        {
      	alert( "您没有输入发票号码，请确认后给定发票号码" );
      	return false;
      }
       
	  if (!certifyInput()) return false;     
	  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
   

  if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		ContNo = ContGrid.getRowColData(tSel-1,1);
		fm.OtherNo.value = ContNo;
		fm.GetNoticeNoHidden.value = ContGrid.getRowColData(tSel-1,2);
		fm.PreSeq.value = ContGrid.getRowColData(tSel-1,5);
		ttmanagecom = ContGrid.getRowColData(tSel-1,6);
	
		fm.type.value =  ContGrid.getRowColData(tSel-1,7);
		fm.fmtransact.value = "Print";
		fm.target = "f1print";	
		fm.submit();
		showInfo.close();
		initContGrid();
	}
}
