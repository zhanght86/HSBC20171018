//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass(); 
var mySql = new SqlClass();
//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
/*
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

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
	
	parent.fraMain.rows = "0,0,0,0,*";
}

*/

// 保全查询按钮
function easyQueryClick()
{
	// 初始化表格
	initPolGrid();
	
	// 书写SQL语句
	// 书写SQL语句
	var strSQL = "";
	var strSQL1 ="";
	var strSQL2="";
	
	//日期区间取   结案日期
	if(fm.StartDate.value!="")
	{
	  //strSQL2=" and  a.EndCaseDate >= '"+fm.StartDate.value+"'";	
	  mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportSimpleInputSql");
		mySql.setSqlId("LLGrpReportSimpleSql1");
		mySql.addSubPara(fm.StartDate.value ); 
		mySql.addSubPara(fm.StartDate.value ); 
		mySql.addSubPara(fm.RgtNo.value ); 
		mySql.addSubPara(fm.ManageCom.value ); 
        }
	 if(fm.EndDate.value!="")
	{
	  //strSQL2=strSQL2+" and a.EndCaseDate <= '"+fm.EndDate.value+"'";
	  mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportSimpleInputSql");
		mySql.setSqlId("LLGrpReportSimpleSql2");
		mySql.addSubPara(fm.StartDate.value ); 
		mySql.addSubPara(fm.EndDate.value ); 
		mySql.addSubPara(fm.StartDate.value ); 
		mySql.addSubPara(fm.EndDate.value ); 
		mySql.addSubPara(fm.RgtNo.value ); 
		mySql.addSubPara(fm.ManageCom.value ); 
	  	
        }
	/*strSQL = "select unique a.RgtNo,a.RealPay,to_char(a.EndCaseDate,'yyyy-mm-dd') EndCaseDate,b.appntname";
	strSQL = strSQL + " from LLClaim a,LLClaimPolicy b   ";
	strSQL = strSQL + "   where  a.ClmNo=b.ClmNo ";
	//拒赔和给付的应该都可以查出来
	strSQL = strSQL + "   and (a.GiveType='0' or a.GiveType='1' or a.GiveType='4')";
	
        strSQL = strSQL + strSQL2;
        strSQL = strSQL + " and a.RgtNo in (select RgtNo from LLRegister ";
        strSQL = strSQL + "  where EndCaseFlag='1'";
	strSQL = strSQL + " and  ClmState!='4'  and ClmState is not null";
        strSQL = strSQL + strSQL2;		
        	
	strSQL = strSQL + ")";
        strSQL = strSQL + " and a.ClmState!='4' ";				
        strSQL = strSQL + " and a.ClmState is not null";
	strSQL = strSQL + " and a.ClmState not in ('10','20','30','40')";
	strSQL = strSQL + getWherePart('a.ClmNo','RgtNo');*/

	//区站问题
	 if (fm.ManageCom.value=="" || fm.ManageCom.value==null || !isNumeric(fm.ManageCom.value))
        {
        	fm.ManageCom.value=fm.OPManageCom.value;
        }
	//strSQL = strSQL + getWherePart('a.mngcom','ManageCom','like');   //管理机构
    //    strSQL = strSQL + " order by a.RgtNo,to_char(a.EndCaseDate,'yyyy-mm-dd')";
	
//查询SQL，返回结果字符串
	  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);   
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
    alert("无满足条件的数据");
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = PolGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

function ReportQueryClick()
{
   	/*var tsql="select rptno,rptdate,accidentdate,grpname from llreport where mngcom like '"+document.all('ManageCom').value+"%%' and AvaliReason='01' "
   	        + getWherePart('RptNo','RgtNo')
   	        +"order by rptdate desc";   */
   	 mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportSimpleInputSql");
		mySql.setSqlId("LLGrpReportSimpleSql3");
		mySql.addSubPara(document.all('ManageCom').value); 
		mySql.addSubPara(fm.RgtNo.value );         
   	turnPage.queryModal(mySql.getString(),PolGrid,1,1);
   		
}

function ReportAccQueryClick()
{
   /*	var tsql="select rptno,rptdate,accidentdate,grpname from llreport where mngcom like '"+document.all('ManageCom').value+"%%' and AvaliReason='02' "
   	        + getWherePart('RptNo','RgtNo')
   	        +"order by rptdate desc";   */
   	mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportSimpleInputSql");
		mySql.setSqlId("LLGrpReportSimpleSql4");
		mySql.addSubPara(document.all('ManageCom').value); 
		mySql.addSubPara(fm.RgtNo.value );  
   	turnPage.queryModal(mySql.getString(),PolGrid,1,1);
   		
}

//取得选择的条目
function GetSelvalue()
{
	var tSel = PolGrid.getSelNo();
    document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
    	   	fm.submit();

}


//理赔批单打印
function PrintClaimBatch()
{
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要打印的赔案！");
	      return;
	      
	}	
	//add suox 2005-12-27
	var tSel = PolGrid.getSelNo();
        document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
        if (fm.BOX.checked)
				{
					fm.action = "./ClaimPrtPDF_VER1.jsp?operator=batch";
				}
				else
				{
					fm.action = "./ClaimPrtPDF.jsp?operator=batch";
		
				}
        fm.target=".";
	  		fm.submit();
	
}

//支付清单
function PrintClaimPayBill()
{
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要打印的赔案！");
	      return;
	}	
	var tSel = PolGrid.getSelNo();
	document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
        if (fm.BOX.checked)
        {
		fm.action = "./ClaimPrtPDF_VER1.jsp?operator=PayBill";
	}
	else
	{
		fm.action = "./ClaimPrtPDF.jsp?operator=PayBill";
		
		}
        fm.target=".";
        fm.submit();
	
}
//支付清单EXCEL版
function PrintClaimPayBillExcel()
{
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要打印的赔案！");
	      return;
	}	
	var tSel = PolGrid.getSelNo();
	document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
        if (fm.BOX.checked)
        {
		fm.action = "./ClaimPrtPDF_VER1.jsp?operator=PayBillExcel";
	}
	else
	{
		fm.action = "./ClaimPrtPDF.jsp?operator=PayBillExcel";
		
		}
        fm.target=".";
        fm.submit();
	
}

//赔款清单
function PrintClaimGetBill()
{
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要打印的赔案！");
	      return;
	}	
	var tSel = PolGrid.getSelNo();
        document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
	if (fm.BOX.checked)
	{
		fm.action = "./ClaimPrtPDF_VER1.jsp?operator=GetBill";
	}
	else
	{
		fm.action = "./ClaimPrtPDF.jsp?operator=GetBill";
		
		}
        fm.target=".";
	  fm.submit();
}
//领款收据
function PrintGetBill()
{
		var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要打印的赔案！");
	      return;
	}	
	var tSel = PolGrid.getSelNo();
        document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
	if (fm.BOX.checked)
	{
	fm.action = "./ClaimPrtPDF_VER1.jsp?operator=lksj";
	}
	else
	{
	fm.action = "./ClaimPrtPDF.jsp?operator=lksj";
		
	}
        fm.target=".";
	  fm.submit();
	
}
/*

//提交后的处理操作
function afterSubmita( FlagStr, content )
{
	//alert("ok");
   
	try {
		if(showInfo!=null)
	        showInfo.close();
		}
	catch(e){}
 
	if (FlagStr == "Fail" )
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
    
	    content = "生成文件成功！";
	    var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//	    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  	 	var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		
		showInfo.focus();


    }

}
*/



//费用清单Excel版
function  PrintFeeBill()
{
		var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要打印的赔案！");
	      return;
	}	
	var tSel = PolGrid.getSelNo();
        document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
	if (fm.BOX.checked)
	{
		fm.action = "./ClaimPrtPDF_VER1.jsp?operator=FeeBill";
	}
	else
	{
		fm.action = "./ClaimPrtPDF.jsp?operator=FeeBill";
		}
        fm.target=".";
	  fm.submit();
	
}

//赔款清单excel版
function  PrintclaimBill()
{
		var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要打印的赔案！");
	      return;
	}	
	var tSel = PolGrid.getSelNo();
        document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
	if (fm.BOX.checked)
	{
		fm.action = "./ClaimPrtPDF_VER1.jsp?operator=claimBillExcel";
	}
	else
	{
		fm.action = "./ClaimPrtPDF.jsp?operator=claimBillExcel";
	}
        fm.target=".";
	  fm.submit();
	
}

//理赔个人清单------------暂停
function  PrintClaimPer()
{
		var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要打印的赔案！");
	      return;
	}	
	var tSel = PolGrid.getSelNo();
        document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
	
	fm.action = "./ClaimPrtPDF.jsp?operator=ClaimPer";
        fm.target=".";
	  fm.submit();
	
}
//社保帐单打印
function  PrintCaseReceiptClass()
{
		var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要打印的赔案！");
	      return;
	}	
	var tSel = PolGrid.getSelNo();
        document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
	
	fm.action = "./ClaimPrtPDF.jsp?operator=PrintCaseReceiptClass";
        fm.target=".";
	  fm.submit();
	
}

//报案信息导出
function  PrintReportClass()
{	
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要导出的赔案！");
	      return;
	}	
	var tSel = PolGrid.getSelNo();
  document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
	
	fm.action = "./ClaimPrtPDF.jsp?operator=PrintReportClass";
  fm.target=".";
	fm.submit();
	
}

//报案信息导出2
function  PrintAccReportClass()
{	
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要导出的赔案！");
	      return;
	}	
	var tSel = PolGrid.getSelNo();
  document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
	
	fm.action = "./ClaimPrtPDF.jsp?operator=PrintAccReportClass";
  fm.target=".";
	fm.submit();
	
}

//理赔结案报告单
function PrintClaimOver()
{
		var selno = PolGrid.getSelNo()-1;
		if (selno <0)
		{
	      alert("请选择要打印的赔案！");
	      return;
		}	
		var tSel = PolGrid.getSelNo();
    document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
			if (fm.BOX.checked)
			{
				//打印原始文件
				fm.action = "./ClaimPrtPDF_VER1.jsp?operator=PrintClaimOver";
			}
			else
			{
				//打印新文件
				fm.action = "./ClaimPrtPDF.jsp?operator=PrintClaimOver";
			}

    fm.target=".";
	  fm.submit();
}

