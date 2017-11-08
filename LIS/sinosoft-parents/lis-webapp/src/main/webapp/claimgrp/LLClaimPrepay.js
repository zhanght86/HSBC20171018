//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass(); //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass();  //使用翻页功能，必须建立为全局变量
var sel="";

//用来打开或关闭“DIV区域”
function showMyPage(spanID,flag)
{
  if(!flag)//关闭
  { 
    spanID.style.display="none";
  }
  else    //打开
  {
    spanID.style.display="";
  }
}

//页面初始化,申请批次号<默认为10位>,
function initApplyPrepayNo()
{
//	//检索最大批次号,如果存在则在最大的批次号加“10”。<第一个定为--1000000000>
//    var strSQL = "select max(prepayno) from llprepaydetail";// order by PrepayNo DESC";
//    var arr = easyExecSql(strSQL);
////    alert(arr);
//    if (arr == "" || arr == null) 
//    {
//    	  tMaxNo = 1000000000; 	  
//    	  tMaxNo = parseInt(tMaxNo)+10;
//    }
//    else
//    {
//    	  tMaxNo = parseInt(arr[0][0])+10;
//    }	
//	tMaxNo = parseInt(tMaxNo);
//	fm.PrepayNo.value = "";
	fm.PrepayNo.value ="0";
}

//[查询预付节点信息]，为生成 审核节点 准备提供数据
function initLLClaimPrepayNodeQuery()
{
//    查询“预付节点”数据，为生成待审核节点 准备数据
	strSQL = "select missionprop1,missionprop2,missionprop3,missionprop4,missionprop5,missionprop6,missionprop7,missionprop8,missionprop9,missionprop10,missionprop12,missionprop19,missionprop20,missionprop18,missionid,submissionid,activityid from lwmission where 1=1 "
           + getWherePart( 'missionid','tMissionID' )
		   + getWherePart( 'submissionid','tSubMissionID' )
           + getWherePart( 'activityid','tActivityID' ); 
//    alert(strSQL);     
    var arr = new Array();    
    arr=easyExecSql(strSQL);  
    if(arr==null)
    {
       alert("数据查询有误！");   
       return;  	
    }
	else
	{
		fm.tRptNo.value=arr[0][0];
		fm.tRptorState.value=arr[0][1];
		fm.tCustomerNo.value=arr[0][2];
		fm.tCustomerName.value=arr[0][3];
		fm.tCustomerSex.value=arr[0][4];
		fm.tAccDate.value=arr[0][5];
		fm.tRgtClass.value=arr[0][6];
		fm.tRgtObj.value=arr[0][7]; 
		fm.tRgtObjNo.value=arr[0][8]; 
		fm.tPopedom.value=arr[0][9]; 
		fm.tPrepayFlag.value="1";
		fm.tComeWhere.value=arr[0][10];		
		fm.tAuditer.value=arr[0][11];	
		fm.tMngCom.value=arr[0][12];
		fm.tComFlag.value=arr[0][13];  // <!--//权限跨级标志-->
		fm.tMissionID.value=arr[0][14];
		fm.tSubMissionID.value=arr[0][15];
		fm.tActivityID.value=arr[0][16];	
	}	
}


//初始化“ 给付保项信息列表”，根据赔案号查询LLClaimDetail表
function initLLClaimDetailGridQuery()
{
	try
	{
		var	strSQL = " select t.clmno,t.caseno,t.polno,t.customerno,dutycode,t.getdutykind,t.getdutycode,t.CaseRelaNo,t.RealPay,t.PrepayFlag,t.PrepaySum"
	             + " from llclaimdetail t  where 1=1"
		         + " and t.clmno='"+ fm.tRptNo.value +"'";	
//		arr = easyExecSql( strSQL );
//	    if (arr==null ||arr=="")
//	    {
//			alert("在该赔案下未找到任何预付明细信息！");    
//			fm.Bnf.disabled=true;
//			fm.PrepayCofirm.disabled=true;
//			return;     	
//	    } 
        turnPage.queryModal(strSQL,LLClaimDetailGrid);   
        var rowNum=LLClaimDetailGrid. mulLineCount ; //行数 
        if(rowNum==0)
        {
			alert("在该赔案下未找到任何预付明细信息！");    
			fm.Bnf.disabled=true;  //[受益人分配]按钮不可用
			fm.PrepayCofirm.disabled=true;//[预付确认]按钮不可用
			return;          	
        }
	}
	catch(ex)
    {
      alert("LLClaimPrepay.js-->initLLClaimDetailGridQuery函数中发生异常:数据查询错误!");
    }

}

//响应“ 给付保项信息列表”点击单选钮的函数，查询预付明细记录（LLPrepayDetail表）
function LLClaimDetailGridClick()
{
//	  alert(strSQl);	     
	var selno= LLClaimDetailGrid.getSelNo()-1;
	sel=LLClaimDetailGrid.getSelNo()-1;
	fm.ClmNo.value= LLClaimDetailGrid.getRowColData(selno, 1);//"赔案号" 
	fm.CaseNo.value= LLClaimDetailGrid.getRowColData(selno, 2);//"分案号"
	fm.PolNo.value= LLClaimDetailGrid.getRowColData(selno, 3);//"保单号"
	fm.DutyCode.value= LLClaimDetailGrid.getRowColData(selno, 5);//"责任编码"
	fm.GetDutyKind.value= LLClaimDetailGrid.getRowColData(selno, 6);//"给付责任类型 
	fm.GetDutyCode.value= LLClaimDetailGrid.getRowColData(selno, 7);//"给付责任编码"
	fm.CaseRelaNo.value= LLClaimDetailGrid.getRowColData(selno, 8);//"受理事故号"
	initLLPrepayDetailGridQuery();//查询该记录的预付情况
	fm.LLPrepayAdd.disabled=false;//增加预付按钮可用
	divLLPrepayDetail.style.display="none";

}
	

function initLLPrepayDetailGridQuery()
{
	var strSQL="select clmno,caseno,polno,getdutykind,getdutycode,PrepayNo,SerialNo,prepaysum,MakeDate "
	             + " from llprepaydetail where 1=1"
		         + " and clmno='"+ fm.ClmNo.value +"'"	
		         + " and caseno='"+ fm.CaseNo.value +"'"	
		         + " and polno='"+ fm.PolNo.value +"'"			         
		         + " and getdutykind='"+ fm.GetDutyKind.value +"'"	
		         + " and getdutycode='"+ fm.GetDutyCode.value +"'";	   	         
    turnPage2.queryModal(strSQL,LLPrepayDetailGrid);     	         
}

//"增加"按钮
function LLPrepayDetailAdd()
{
	var selno= LLClaimDetailGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择一条要预付的给付保项！");
	      return;
	}	
	fm.PrepaySum.value="";
    divLLPrepayDetail.style.display="";
    fm.LLPrepayAdd.disabled=true;
    fm.LLPrepayCancel.disabled=false;
//	showMyPage(divLLPrepayDetail,true);
}
//"取消"按钮
function LLPrepayDetailCancel()
{
//	showMyPage(divLLPrepayDetail,false);
    divLLPrepayDetail.style.display="none";
    fm.LLPrepayAdd.disabled=false;
    fm.LLPrepayCancel.disabled=true;
}
//[保存]后刷新页面数据
function RenewPage()
{

}

//"保存"按钮
function LLPrepayDetailSave()
{
	if(fm.CasePayMode.value=="" ||fm.PrepaySum.value=="" )
	{
		alert("预付金额未填写或支付方式未选择！");
		return;		
	}
	 if (!isNumeric(fm.PrepaySum.value))
    {
        alert("费用金额填写有误！");
        return;
    }
	fm.fmtransact.value="Prepay||Insert";
	fm.action="LLClaimPrepaySave.jsp";
	submitForm(); //提交	
}

//受益人分配
function showBnf()
{
	var rptNo = fm.tRptNo.value;//赔案号
    var strUrl="LLBnfMain.jsp?RptNo="+rptNo+"&BnfKind=B";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"受益人分配");
}

//"预付确认"按钮，预付节点消亡，生成待审核节点，任务回到待审核工作队列中，并标上预付标志。
function LLClaimPrepayCofirm()
{
	//作数据校验，是否进行完成“受益人分配”工作，如果没有则不允许进行 预付确认<是否转到后台进行，有待确定>
	//查询LLBnf表（受益人账户），条件（赔案号，受益性质（BnfKind）==B），统计所有的受益金额
	var str_llbnf="select clmno,sum(getmoney) from llbnf where clmno='"+ document.all('tRptNo').value +"' and bnfkind='B' group by clmno"; 
	//查询LLPrepayClaim（预付赔案记录）生成一条汇总的预付赔案的预付金额
	var str_prepay="select clmno,prepaysum from llprepayclaim where clmno='"+ document.all('tRptNo').value +"' ";
	var arr_llbnf = easyExecSql( str_llbnf ); //所有的受益金额（已经分配）
	var arr_prepay = easyExecSql( str_prepay );//该赔案的预付金额
	if(arr_llbnf=="" || arr_llbnf==null)
	{
		alert("未进行受益人分配！");
		return;
	}    
	if(parseFloat(arr_llbnf[0][1]) != parseFloat(arr_prepay[0][1]))
	{
		alert("受益人分配未进行完毕！");
		return;
	}
	fm.fmtransact.value="Prepay||Confirm";	//
	fm.action="LLClaimPrepayCofirmSave.jsp";
	submitForm(); //提交		
}

//“返回”按钮
function Return()
{
    location.href="LLClaimPrepayMissInput.jsp?";//返回	
}

//公共提交方法
function submitForm()
{
    //提交数据
    var i = 0;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();


    fm.submit(); //提交
    tSaveFlag ="0";    
}

//预付保存提交后操作,返回<用于[保存]按钮的返回>
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();


        initLLPrepayDetailGridQuery();
    	initLLClaimDetailGridQuery();//刷新页面  
    }
    tSaveFlag ="0";

}

//预付确认提交后操作,返回
function afterSubmit2( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();


    }
    tSaveFlag ="0";
    Return();//“返回”按钮，返回预付队列
}

//影像查询---------------2005-08-14添加
function colQueryImage()
{
    var strUrl="LLColQueryImageMain.jsp?claimNo="+document.all('ClmNo').value+"&subtype=LP1001";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//打印赔案号条形码
function colBarCodePrint()
{
    fm.action="LLClaimBarCodePrintSave.jsp";
    fm.target = "../f1print";
    fm.submit();
}