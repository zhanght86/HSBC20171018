//               该文件中包含客户端需要处理的函数和事件
var turnPage1 = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var turnPage = new turnPageClass(); 
var turnPage2 = new turnPageClass(); 
var mSwitch = parent.VD.gVSwitch;

function submitForm()
{
	  var n = 0;
	  var i = 0;    

   
   
    	  var tEndDate = fm.StatDate.value;
    	  
    	  if(tEndDate ==""||tEndDate == null)
    	  {
    	  	  alert("请输入恢复日期！");
    	  	  return;
    	  }
    	  else
    		{   
    				var tOldEndDate = fm.OldEndDate.value;

						var retCPDate = compareDate(tOldEndDate,tEndDate);
						if(retCPDate != "2")
						{
							alert("恢复日期应在当前保单的中断日期之后!");
							return;
  					}

				    	  var mOperator = "INSERT";
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
								
							  fm.action ="./GrpEdorTypeBRSave.jsp?Operator="+mOperator;
							  fm.submit(); 

			  }

}
function returnParent()
{
	top.opener.getEdorItemGrid();
	top.close();
}
//显示提交结果
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	queryClick();
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	//showInfo = showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}

//查询按钮
function queryClick()
{
	var tGrpContNo = fm.GrpContNo.value;
	var tEdorNo = fm.EdorNo.value;
	var tEdorType = fm.EdorType.value;
	var strSql = "select c.grpcontno,c.grppolno ,c.riskcode ,c.grpname,c.cvalidate,d.startdate ,d.remark,(select startdate from lpgrpcontstate where grpcontno = '"+tGrpContNo+"' and grppolno = c.grppolno and edorno = '"+tEdorNo+"' and edortype = '"+fm.EdorType.value+"' and enddate is null and statetype = 'Available' and state = '0'),(select payenddate from lpgrppol where grppolno = c.grppolno and edorno = '"+tEdorNo+"' and edortype = '"+fm.EdorType.value+"')"
	    +" from lcgrppol c,lcgrpcontstate d where c.grpcontno = '"+tGrpContNo+"' and c.grpcontno = d.grpcontno and c.grppolno = d.grppolno and d.enddate is null and d.statetype='Available' and d.state = '1' and c.riskcode in (select riskcode from lmriskedoritem where edorcode = '"+fm.EdorType.value+"')";

	turnPage1.queryModal(strSql, LCGrpPolGrid);
	
	var strSQL = "select 1 from lpgrppol p where p.grpcontno = '"+tGrpContNo+"' and p.edorNo = '"+tEdorNo+"' and p.edortype = '"+tEdorType+"'";
	var prr = easyExecSql(strSQL);
	if(prr)
	{
		strSQL = "select min(startdate) from lpgrpcontstate where grpcontno = '"+tGrpContNo+"' and edorno = '"+tEdorNo+"' and edortype = '"+fm.EdorType.value+"' and enddate is null and statetype = 'Available' and state = '0'";
  	var trr = easyExecSql(strSQL);
  	if(trr)
		{
			fm.StatDate.value = trr[0][0];
		}
	}else{
		 fm.StatDate.value = fm.OldEndDate.value;
	}	 
}

	
function QueryEdorInfo()
{
		 var tEdortype=document.all('EdorType').value;
		 var strSQL = "";
		 if(tEdortype!=null || tEdortype !='')
		 {
		    strSQL="select distinct edorcode, edorname from lmedoritem where edorcode = '" + tEdortype + "'";
	      var arrSelected = new Array();	
				turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
				arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
				if(tEdortype!=null || tEdortype !='')
				{
				    fm.EdorType.value     = arrSelected[0][0];
				    fm.EdorTypeName.value = arrSelected[0][1];
		    }
		    else
				{
					  alert('未查询到保全批改项目信息！');
				}
     }
	   else
		 {
			   alert('保全批改项目信息为空！');
		 }
}


function GrpPolSel()
{
	var tGrpContNo = fm.GrpContNo.value;
	var tEdorNo = fm.EdorNo.value;
	var tEdorType = fm.EdorType.value;
	var strSql = "select c.grpcontno,c.grppolno ,decode(c.state,'0','有效','1','失效','未知') ,c.startdate,c.enddate "
	    +" from lcgrpcontstate c where c.grpcontno = '"+tGrpContNo+"' order by grppolno,startdate";

	turnPage2.queryModal(strSql, LCGrpContStateGrid);	 
}
/*
function CheckLCGrpContStopEdorStateInfo()
{
	   var tGrpContNo = fm.GrpContNo.value;
	   var tSQL = "select * from LCGrpContStopEdorState where grpcontno = '"+tGrpContNo+"'"
	            +" and flag = '0'";
	   var arrSelected = new Array();	
		 turnPage2.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
		 arrSelected = decodeEasyQueryResult(turnPage2.strQueryResult);
		 if)
}*/