//程序名称：LLSecondUW.js
//程序功能：合同单人工核保------发起核保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
// 初始化按钮------------该被保险人下的所有合同 ,并列出 是否与当前赔案相关
function initLCContGridQuery()
{
	var tInsuredNo = fm.InsuredNo.value;
	var tCaseNo = fm.CaseNo.value;
	
	// 根据 “被保人号码” 查询 被保人姓名（解决在 汉字在 跳转传入时为 乱码的问题）,2009-01-20 zhangzheng 个险只能查询出个单
	//var ssql="select t.name from lcinsured t where t.insuredno='"+tInsuredNo+"'";
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLSecondUWInputSql");
	mySql.setSqlId("LLSecondUWSql1");
	mySql.addSubPara(tInsuredNo); 
	var arr = easyExecSql(mySql.getString());
	document.all('InsuredName').value =arr[0][0];	
	/*var strSQL =" select a.contno,a.appntno,a.appntname,a.managecom,a.proposalcontno,a.prtno ,'0-相关' as reflag"
 			+" from lccont a,lcinsured b where 1=1 and a.appflag='1' and a.contno = b.contno"	
 			+" and a.contno in (select c.contno from llclaimpolicy c where c.clmno='"+tCaseNo+"' )"
 			+" and a.grpcontno='00000000000000000000'"
 			+" and b.insuredno = '"+tInsuredNo+"' "
 			+" union "
 			+" select a.contno,a.appntno,a.appntname,a.managecom,a.proposalcontno,a.prtno,'1-不相关' as reflag"
 			+" from lccont a,lcinsured b where 1=1 and a.appflag='1' and a.contno = b.contno"	
 			+" and a.contno not in (select c.contno from llclaimpolicy c where c.clmno='"+tCaseNo+"' )"
 			+" and a.grpcontno='00000000000000000000'"
 			+" and b.insuredno = '"+tInsuredNo+"' "
 			+" order by reflag "  ;*/
 	mySql = new SqlClass();
	mySql.setResourceName("claim.LLSecondUWInputSql");
	mySql.setSqlId("LLSecondUWSql2");
	mySql.addSubPara(tCaseNo); 
	mySql.addSubPara(tInsuredNo); 
	mySql.addSubPara(tCaseNo); 
	mySql.addSubPara(tInsuredNo); 
	//prompt("发起二核初始化查询",strSQL);
	var arrResult = easyExecSql(mySql.getString());
    if (arrResult)
    {
    	displayMultiline(arrResult,LCContGrid);
    }
	return true;
}


//[确认]按钮-------发起二核 数据准备保存
function LLSeUWSaveClick()
{
	  //二核
    /*var strSql4 = "select InEffectFlag from LLCUWBatch where "
                + " caseno = '" + document.all('CaseNo').value + "'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLSecondUWInputSql");
	mySql.setSqlId("LLSecondUWSql3");
	mySql.addSubPara(document.all('CaseNo').value);
	var tState = easyExecSql(mySql.getString());

    if (tState)
    {

        for (var i = 0; i < tState.length; i++)
        {

            if (tState[i] == '0')
            {
                alert("发起的二核还没有做是否生效处理,不能再次发起二核!");
                return false;
            }
        }

        //检查是否存在二核的加费信息，如有必须核销(by zl 2006-1-6 11:40)
        if (!checkLjspay(document.all('CaseNo').value))
        {
            return false;//此处调用公有方法
        }
    }

    
    //检查是否填写数据()
    var row = LCContGrid.mulLineCount;
    var i=0;
    for(var m=0;m<row;m++ )
    {
    	if(LCContGrid.getChkNo(m))
    	{
    		i=i+1;
    		// 检测是否必须填写表格中的内容
    	}
    }
    if(i==0)
	{
		alert("你没有选择任何合同！");
		return;
	}
	if(trim(fm.Note.value)=="") 
	{
		alert("请填写出险人目前健康状况介绍！");
		return;
	}

	
	//var sysdatearr=easyExecSql("select to_date(sysdate) from dual");//取服务器的时间作为系统当前日期
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLSecondUWInputSql");
	mySql.setSqlId("LLSecondUWSql4");
	mySql.addSubPara("1");
	var sysdatearr=easyExecSql(mySql.getString());
	fm.CurrentDate.value=sysdatearr[0][0];

	
	fm.action="LLSecondUWSave.jsp";
	submitForm(); //提交
}

//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
  document.getElementById("fm").submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
}


//[保单查询]按钮
function showPolDetail()
{
	//检查是否填写数据()
    var row = LCContGrid.mulLineCount;
    var i=0;
    for(var m=0;m<row;m++ )
    {
    	if(LCContGrid.getChkNo(m))
    	{
    		i=i+1;
    	}
    }
    if(i==0)
	{
		alert("你没有选择任何合同！");
		return;
	}
	if(i>1)
	{
		alert("请只选择一条合同记录！");
		return;
	}
	var tContNo = LCContGrid.getRowColData(i-1,1);//保单号码
	var tPrtNo  = LCContGrid.getRowColData(i-1,6);//印刷号码
	var strUrl  = "../sys/PolDetailQueryMain.jsp?PrtNo="+tPrtNo+"&ContNo="+tContNo+"&IsCancelPolFlag=0";
	//window.open(strUrl, "保单查询", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");  
	window.open(strUrl, "","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");  		  
} 

//[保全明细]按钮
function showPolBqEdor()
{
	//检查是否填写数据()
    var row = LCContGrid.mulLineCount;
    var i=0;
    for(var m=0;m<row;m++ )
    {
    	if(LCContGrid.getChkNo(m))
    	{
    		i=i+1;
    	}
    }
    if(i==0)
	{
		alert("你没有选择任何合同！");
		return;
	}
	if(i>1)
	{
		alert("请只选择一条合同记录！");
		return;
	}
	var tContNo=LCContGrid.getRowColData(i-1,1);//合同号码
	var strUrl="../sys/PolBqEdorMain.jsp?ContNo="+tContNo+"&flag=0&IsCancelPolFlag=0";
	//window.open(strUrl, "保全明细", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");  	
	window.open(strUrl, "","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");  	  
} 







