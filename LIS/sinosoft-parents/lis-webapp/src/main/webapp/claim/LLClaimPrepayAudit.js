//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass(); //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass();  //使用翻页功能，必须建立为全局变量
var sel="";
var mySql = new SqlClass();
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
	/*strSQL = "select missionprop1,missionprop2,missionprop3,missionprop4,missionprop5,missionprop6,missionprop7,missionprop8,missionprop9,missionprop10,missionprop12,missionprop19,missionprop20,missionprop18,missionid,submissionid,activityid from lwmission where 1=1 "
           + getWherePart( 'missionid','MissionID' )
		   + getWherePart( 'submissionid','SubMissionID' )
           + getWherePart( 'activityid','ActivityID' ); */
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPrepayAuditInputSql");
	mySql.setSqlId("LLClaimPrepayAuditSql1");
	mySql.addSubPara(fm.MissionID.value ); 
	mySql.addSubPara(fm.SubMissionID.value ); 
	mySql.addSubPara(fm.ActivityID.value ); 
	//prompt("initLLClaimPrepayNodeQuery-[查询预付节点信息]，为生成 审核节点 准备提供数据",strSQL);
    var arr = new Array();    
    arr=easyExecSql(mySql.getString());  
    if(arr==null)
    {
       alert("数据查询有误！");   
       return;  	
    }
	else
	{
		fm.ClmNo.value=arr[0][0];
		fm.tRptorState.value=arr[0][1];
		fm.CustomerNo.value=arr[0][2];
		fm.CustomerName.value=arr[0][3];
		fm.CustomerSex.value=arr[0][4];
		fm.tAccDate.value=arr[0][5];
		fm.tRgtClass.value=arr[0][6];
		fm.tRgtObj.value=arr[0][7]; 
		fm.tRgtObjNo.value=arr[0][8]; 
		fm.AuditPopedom.value=arr[0][9]; 
		fm.tPrepayFlag.value="1";
		fm.tComeWhere.value=arr[0][10];		
		fm.tAuditer.value=arr[0][11];	
		fm.tMngCom.value=arr[0][12];
		fm.tComFlag.value=arr[0][13];  // <!--//权限跨级标志-->
		fm.MissionID.value=arr[0][14];
		fm.SubMissionID.value=arr[0][15];
		fm.ActivityID.value=arr[0][16];	
	}	
}


//初始化“ 给付保项信息列表”，根据赔案号查询LLClaimDetail表
function initLLClaimDetailGridQuery()
{
	try
	{
		/*var	strSQL = " select t.clmno,t.caseno,t.polno,t.customerno,t.dutycode, "
		           +" (select codename from ldcode where codetype='llclaimtype' and trim(t.getdutykind)=trim(code)), "
		           +" t.getdutycode,t.CaseRelaNo,t.RealPay,t.PrepayFlag,t.PrepaySum,t.getdutykind"
		           //+" ,(select name from ldperson where trim(customerno)=trim(t.customerno)) "
	               + " from llclaimdetail t  where 1=1 "
		           + " and t.clmno='"+ fm.ClmNo.value +"'";	*/
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimPrepayAuditInputSql");
		mySql.setSqlId("LLClaimPrepayAuditSql2");
		mySql.addSubPara(fm.ClmNo.value ); 
		
		//prompt("initLLClaimDetailGridQuery-初始化“ 给付保项信息列表”，根据赔案号查询LLClaimDetail表",strSQL);
//		arr = easyExecSql( strSQL );
//	    if (arr==null ||arr=="")
//	    {
//			alert("在该赔案下未找到任何预付明细信息！");    
//			fm.Bnf.disabled=true;
//			fm.PrepayCofirm.disabled=true;
//			return;     	
//	    } 
        turnPage.queryModal(mySql.getString(),LLClaimDetailGrid);   
//        var rowNum=LLClaimDetailGrid. mulLineCount ; //行数 
//        if(rowNum==0)
//        {
//			alert("在该赔案下未找到任何预付明细信息！");    
//			fm.Bnf.disabled=true;  //[受益人分配]按钮不可用
//			fm.PrepayCofirm.disabled=true;//[预付确认]按钮不可用
//			return;          	
//        }
	}
	catch(ex)
    {
      alert("LLClaimPrepayAudit.js-->initLLClaimDetailGridQuery函数中发生异常:数据查询错误!");
    }

}


//初始化“ 预付保项信息列表”，根据赔案号查询LLClaimDetail表
function initLLPrepayDetailGridQuery()
{
	try
	{
		/*var	strSQL = " select a.clmno,a.caseno,a.polno,a.getdutykind,a.getdutycode,a.prepayno,"
		           +"  a.serialno,a.prepaysum,a.modifydate from LLPrepayDetail a "
		           + " where 1=1 and a.clmno='"+ fm.ClmNo.value +"'";	*/
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimPrepayAuditInputSql");
		mySql.setSqlId("LLClaimPrepayAuditSql3");
		mySql.addSubPara(fm.ClmNo.value );
		//prompt("初始化时查询预付保项给付信息",strSQL);
		
        turnPage.queryModal(mySql.getString(),LLPrepayDetailGrid);   
        var rowNum=LLPrepayDetailGrid. mulLineCount ; //行数 
//        if(rowNum==0)
//        {
//			//alert("在该赔案下未找到任何预付明细信息！");    
//			fm.Bnf.disabled=true;  //[受益人分配]按钮不可用
//			fm.PrepayCofirm.disabled=true;//[预付确认]按钮不可用
//			return;          	
//        }
	}
	catch(ex)
    {
      alert("LLClaimPrepayAudit.js-->initLLPrepayDetailGridQuery函数中发生异常:数据查询错误!");
    }

}

//响应“ 给付保项信息列表”点击单选钮的函数，查询预付明细记录（LLPrepayDetail表）
function LLClaimDetailGridClick()
{	     
	var selno= LLClaimDetailGrid.getSelNo()-1;
	sel=LLClaimDetailGrid.getSelNo()-1;
	fm.ClmNo.value= LLClaimDetailGrid.getRowColData(selno, 1);//"赔案号" 
	fm.CaseNo.value= LLClaimDetailGrid.getRowColData(selno, 2);//"分案号"
	fm.PolNo.value= LLClaimDetailGrid.getRowColData(selno, 3);//"保单号"
	fm.DutyCode.value= LLClaimDetailGrid.getRowColData(selno, 5);//"责任编码"
	fm.GetDutyKind.value= LLClaimDetailGrid.getRowColData(selno, 13);//"给付责任类型 
	fm.GetDutyCode.value= LLClaimDetailGrid.getRowColData(selno, 7);//"给付责任编码"
	fm.CaseRelaNo.value= LLClaimDetailGrid.getRowColData(selno, 8);//"受理事故号"
	fm.Currency.value= LLClaimDetailGrid.getRowColData(selno, 9);//币种
	//initLLPrepayDetailGridQuery();//查询该记录的预付情况
	fm.LLPrepayAdd.disabled=false;//增加预付按钮可用
	divLLPrepayDetail.style.display="none";

}
	
//
//function initLLPrepayDetailGridQuery()
//{
//	var strSQL="select clmno,caseno,polno,getdutykind,getdutycode,PrepayNo,SerialNo,prepaysum,MakeDate "
//	             + " from llprepaydetail where 1=1"
//		         + " and clmno='"+ fm.ClmNo.value +"'"	
//		         + " and caseno='"+ fm.CaseNo.value +"'"	
//		         + " and polno='"+ fm.PolNo.value +"'"			         
//		         + " and getdutykind='"+ fm.GetDutyKind.value +"'"	
//		         + " and getdutycode='"+ fm.GetDutyCode.value +"'";	   	         
//    turnPage2.queryModal(strSQL,LLPrepayDetailGrid);     	         
//}

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

	if(!KillTwoWindows(fm.ClmNo.value,'30'))
	{
	  	  return false;
	}
	
	if(fm.PrepaySum.value==null ||fm.PrepaySum.value=="" )
	{
		alert("预付金额未填写！");
		return;		
	}
	
	if (!isNumeric(fm.PrepaySum.value))
    {
        alert("费用金额填写有误！");
        return;
    }

	 
	if(fm.PrepaySum.value<=0)
	{
		alert("预付金额必须大于0！");
		return;		
	}
	
	
	if(fm.CasePayMode.value=="" ||fm.CasePayMode.value=="" )
	{
		alert("支付方式未选择！");
		return;		
	}

	 
	fm.fmtransact.value="Prepay||Insert";
	fm.action="LLClaimPrepaySave.jsp";
	submitForm(); //提交	
}

//受益人分配
function showBnf()
{
	  if(!KillTwoWindows(fm.ClmNo.value,'30'))
	  {
	  	  return false;
	  }	
	var rptNo = fm.ClmNo.value;//赔案号
//    var strUrl="LLBnfMain.jsp?RptNo="+rptNo+"&BnfKind=B&InsuredNo="+document.all('customerNo').value;
	var strUrl="LLBnfMain.jsp?RptNo="+rptNo+"&BnfKind=B&InsuredNo="+fm.CustomerNo.value;  //wyc
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"");
}


//“返回”按钮
function Return()
{
    location.href="./LLClaimAuditMissInput.jsp?";//返回	
}

//公共提交方法
function submitForm()
{
    //提交数据
    var i = 0;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");'
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        initLLPrepayDetailGridQuery();
    	initLLClaimDetailGridQuery();//刷新页面  
        initLLClaimUWMainQuery(); //初始化“ 审核意见”，根据赔案号查询LLClaimUWMain表
    }
    tSaveFlag ="0";

}

//预付确认提交后操作,返回
function afterSubmit2( FlagStr, content)
{
    showInfo.close();
    //alert(FlagStr);
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        Return();//返回审核队列
    }
    
    tSaveFlag ="0";

}


//预付审核结论提交后操作,重新查询
function afterSubmit3( FlagStr, content )
{
    showInfo.close();
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        queryRegister();
    }
    tSaveFlag ="0";
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


//选中SubReportGrid响应事件,tPhase=0为初始化时调用
function SubReportGridClick(tPhase)
{
	//********************************************Beg
	//置空相关表单
	//********************************************
	fm.customerName.value = "";
	fm.customerAge.value = "";
	fm.customerSex.value = "";
	fm.SexName.value = "";
	//fm.IsVip.value = "";
	//fm.VIPValueName.value = "";
	fm.hospital.value = "";
	fm.TreatAreaName.value = "";
	fm.OtherAccidentDate.value = "";
	fm.MedicalAccidentDate.value = "";
	fm.accidentDetail.value = "";
	fm.accidentDetailName.value = "";
//	fm.IsDead.value = "";
//	fm.IsDeadName.value = "";
	fm.claimType.value = "";
	fm.cureDesc.value = "";
	fm.cureDescName.value = "";
	fm.AccResult1.value = "";
	fm.AccResult1Name.value = "";
	fm.AccResult2.value = "";
	fm.AccResult2Name.value = "";
	//理赔类型置空
    for (var j = 0;j < fm.claimType.length; j++)
    {
    	  fm.claimType[j].checked = false;
    }
    //********************************************End

    //取得数据
    var i = "";
    if (tPhase == "0")
    {
        i = 1;
    }
    else
    {
        i = SubReportGrid.getSelNo();
    }
    if (i != '0')
    {
        i = i - 1;
        fm.CustomerNo.value = SubReportGrid.getRowColData(i,1);
        fm.customerName.value = SubReportGrid.getRowColData(i,2);
        fm.customerSex.value = SubReportGrid.getRowColData(i,3);
        fm.customerAge.value = calAge(SubReportGrid.getRowColData(i,5));
        //fm.IsVip.value = SubReportGrid.getRowColData(i,6);
        //fm.VIPValueName.value = SubReportGrid.getRowColData(i,7);
        showOneCodeName('sex','customerSex','SexName');//性别
    }

	
    //查询获得理赔类型
    var tClaimType = new Array;
    /*var strSQL1 = "select ReasonCode from LLAppClaimReason where "
                + " CaseNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.CustomerNo.value+"'";
//    alert(tClaimType);
;*/
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimPrepayAuditInputSql");
		mySql.setSqlId("LLClaimPrepayAuditSql4");
		mySql.addSubPara(fm.RptNo.value );
		mySql.addSubPara(fm.CustomerNo.value );
    tClaimType = easyExecSql(mySql.getString());
    if (tClaimType == null)
    {
    	  alert("理赔类型为空，请检查此记录的有效性！");
    	  return;
    }
    else
    {
        for(var j=0;j<fm.claimType.length;j++)
        {
        	  for (var l=0;l<tClaimType.length;l++)
        	  {
        	  	  var tClaim = tClaimType[l].toString();
        	  	  tClaim = tClaim.substring(tClaim.length-2,tClaim.length);//取理赔类型后两位
//        	  	  alert(tClaim);
        	  	  if(fm.claimType[j].value == tClaim)
        	  	  {
                	  fm.claimType[j].checked = true;
            	  }
            }
        }
    }
    
    //查询分案表信息
    var tSubReport = new Array;
   //modifyed by niuzj,2005-11-05
    /*var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccdentDesc,AccResult1,AccResult2,EditFlag,AffixConclusion,(case EditFlag when '0' then '否' when '1' then '是' end),(case AffixConclusion when '0' then '否' when '1' then '是' end),medaccdate  from LLCase where 1=1 "
                + getWherePart( 'CaseNo','RptNo' )
                + getWherePart( 'CustomerNo','CustomerNo' );*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimPrepayAuditInputSql");
		mySql.setSqlId("LLClaimPrepayAuditSql5");
		mySql.addSubPara(fm.RptNo.value );
		mySql.addSubPara(fm.CustomerNo.value );            
    //prompt("预付管理-查询分案表信息",strSQL2);
    tSubReport = easyExecSql(mySql.getString());
//    alert(tSubReport);
    fm.hospital.value = tSubReport[0][0];
    fm.OtherAccidentDate.value = tSubReport[0][1];
    fm.accidentDetail.value = tSubReport[0][2];
//    fm.IsDead.value = tSubReport[0][3];
    fm.cureDesc.value = tSubReport[0][4];
    fm.Remark.value = tSubReport[0][5];
    fm.AccDesc.value = tSubReport[0][6];
    fm.AccResult1.value = tSubReport[0][7];
    fm.AccResult2.value = tSubReport[0][8];
    
    
    //*************Added by niuzj,2005-11-05*********************
       fm.IsModify.value = tSubReport[0][9];
       fm.IsAllReady.value = tSubReport[0][10];
       fm.IsModifyName.value = tSubReport[0][11];
       fm.IsAllReadyName.value = tSubReport[0][12];
       fm.MedicalAccidentDate.value = tSubReport[0][13];
    //************************************************************
       
    
    showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院
    showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//出险细节
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//死亡标识
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//治疗情况
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//出险结果1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//出险结果2
    queryResult('AccResult1','AccResult1Name');
    queryResult('AccResult2','AccResult2Name');
    queryHospital('hospital','TreatAreaName');    
}


//立案查询
function queryRegister()
{
    var rptNo = fm.RptNo.value;
    if(rptNo == "")
    {
        alert("传入赔案为空！");
        return;
    }
    //检索事件号、意外事故发生日期(一条)
    /*var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimPrepayAuditInputSql");
		mySql.setSqlId("LLClaimPrepayAuditSql6");
		mySql.addSubPara(rptNo);
		   
    //prompt("预付管理-检索事件号、意外事故发生日期(一条):",strSQL1);
    var AccNo = easyExecSql(mySql.getString());

    //检索立案号及其他立案信息(一条)
    //----------------------1------2-----------3-----------4------------5--------6------------7-------8--------9-----------10---------11--------12-----------13--------------14---------15-----------16------------17------------18---------19
    /*var strSQL2 =" select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate, "
                +" (select username from llclaimuser where usercode = llregister.Operator),mngcom, "
                +" (case assigneetype when '0' then '业务员' when '1' then '客户' end),assigneecode,assigneename, "
                +" (case assigneesex when '0' then '男' when '1' then '女' when '2' then '未知' end), "
                +" assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion,RgtState,RgtClass, "
                +" (case RgtClass when '1' then '个人' when '2' then '团体' when '3' then '家庭' end), "
                +" (case getmode when '1' then '一次统一给付' when '2' then '按年金方式领取' when '3' then '分期支付' end)"
                +" from llregister where 1=1 "
                +" and rgtno = '"+rptNo+"'";*/
     mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimPrepayAuditInputSql");
		mySql.setSqlId("LLClaimPrepayAuditSql7");
		mySql.addSubPara(rptNo);
    //prompt("预付管理-检索立案号及其他立案信息(一条):",strSQL2);
    var RptContent = easyExecSql(mySql.getString());
    

    //更新页面内容
    fm.AccNo.value = AccNo[0][0];
    fm.AccidentDate.value = AccNo[0][1];

    fm.RptNo.value = RptContent[0][0];
    fm.RptorName.value = RptContent[0][1];

    fm.RptorPhone.value = RptContent[0][2];
    fm.RptorAddress.value = RptContent[0][3];
    fm.Relation.value = RptContent[0][4];
    fm.AccidentSite.value = RptContent[0][5];
    fm.RptDate.value = RptContent[0][6];
    fm.Operator.value = RptContent[0][7];
    fm.MngCom.value = RptContent[0][8];

    fm.AssigneeType.value = RptContent[0][9];
    fm.AssigneeCode.value = RptContent[0][10];
    fm.AssigneeName.value = RptContent[0][11];
    fm.AssigneeSex.value = RptContent[0][12];
    fm.AssigneePhone.value = RptContent[0][13];
    fm.AssigneeAddr.value = RptContent[0][14];
    fm.AssigneeZip.value = RptContent[0][15];
    fm.occurReason.value = RptContent[0][16];

    fm.RgtConclusion.value = RptContent[0][17];

    fm.tRgtClass.value = RptContent[0][19];
    fm.RgtClassName.value = RptContent[0][20];

    fm.GetMode.value = RptContent[0][21];
    showOneCodeName('llrgrelation','Relation','RelationName');//报案人与事故人关系

    showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
    showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//立案结论

    //****************************************************
    //更新页面权限
    //****************************************************
    fm.AccidentDate.readonly = true;

    fm.claimType.disabled=true;
    fm.Remark.disabled=true;

    //设置按钮++++++++++++++++++++++++++++++++++++++++待添加

    //检索分案关联出险人信息(多条)
   /* var strSQL3 = " select CustomerNo,Name,"
                         + " Sex,"
                         + " (case trim(Sex) when '0' then '男' when '1' then '女' when '2' then '不详' end) as SexNA,"
                         + " Birthday,"
                         + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                         + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end) as 是否有社保标志 "
                         + " from LDPerson where "
                         + " CustomerNo in("
                         + " select CustomerNo from LLCase where CaseNo = '"+ rptNo +"')";*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimPrepayAuditInputSql");
		mySql.setSqlId("LLClaimPrepayAuditSql8");
		mySql.addSubPara(rptNo);
    //prompt("预付管理-检索分案关联出险人信息(多条):",strSQL3);
    easyQueryVer3Modal(mySql.getString(), SubReportGrid);
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        SubReportGridClick(0);
    }
    
    //查询赔案标志
    QueryClaimFlag();
}

//查询出险结果
function queryResult(tCode,tName)
{
    /*var strSql = " select ICDName from LDDisease where "
               + " trim(ICDCode) = '" + document.all(tCode).value + "'";*/
     mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimPrepayAuditInputSql");
		mySql.setSqlId("LLClaimPrepayAuditSql9");
		mySql.addSubPara(document.all(tCode).value);
    var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        document.all(tName).value = tICDName;
    }
}

//查询治疗医院，Added by niuzj,2005-11-26
function queryHospital(tCode,tName)
{
	 /* var strSql =" select hospitalname from llcommendhospital  "
	             +" where trim(hospitalcode)='"+document.all(tCode).value+"' ";*/
	  mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimPrepayAuditInputSql");
		mySql.setSqlId("LLClaimPrepayAuditSql10");
		mySql.addSubPara(document.all(tCode).value);
	  var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        document.all(tName).value = tICDName;
    }
}


//保单查询
//按照”客户号“在LCpol中进行查询，显示该客户的保单明细
function showInsuredLCPol()
{
//  var row = SubReportGrid.getSelNo();
//  if(row < 1)
//  {
//      alert("请选中一行记录！");
//      return;
//  }
//	var CustomerNo = SubReportGrid.getRowColData(row-1,1);
//Modifyed by niuzj,2005-12-24
var CustomerNo = fm.customerNo.value;
	if (CustomerNo == "")
	{
	    alert("请选择出险人！");
	    return;
	}
  var strUrl="LLLcContQueryMain.jsp?AppntNo="+CustomerNo;
  window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//既往赔案查询
function showOldInsuredCase()
{
//  var row = SubReportGrid.getSelNo();
//  if(row < 1)
//  {
//      alert("请选中一行记录！");
//      return;
//  }
//	var CustomerNo = SubReportGrid.getRowColData(row-1,1);
//Modifyed by niuzj,2005-12-24
var CustomerNo = fm.customerNo.value;
	if (CustomerNo == "")
	{
	    alert("请选择出险人！");
	    return;
	}
  var strUrl="LLLClaimQueryMain.jsp?AppntNo="+CustomerNo+"&ClmNo="+fm.RptNo.value;
  window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
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




//校验受益人分配必须完成才能点击审核结论保存
function checkBnf()
{
	/*var strSql = "select bnfkind from llbnf where 1=1 and  bnfkind='B' "
        + getWherePart( 'ClmNo','ClmNo' );*/
	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimPrepayAuditInputSql");
		mySql.setSqlId("LLClaimPrepayAuditSql11");
		mySql.addSubPara(fm.ClmNo.value);
	var tBNF = easyExecSql(mySql.getString());
	
	//prompt("保存审核结论时校验是否执行受益人分配",strSql);
	if (tBNF==null)
	{
		alert("受益人分配没有完成,不能点击保存审核结论!");
		return false;
	}
	
	return true;
}



//审核结论保存
function ConclusionSaveClick()
{
	if(!KillTwoWindows(fm.RptNo.value,'30'))
	{
	  	  return false;
	}	
	
	//判断表单输入是否为空
	if (fm.AuditConclusion.value == "" || fm.AuditConclusion.value == null)
	{
		alert("请先选择审核结论!");
      return;
	}

  
  //如果上报,提交前检验
  if (fm.AuditConclusion.value == "0" )
  {
      //校验受益人分配
      if (!checkBnf())
      {
          return;
      } 
    
  }
  
  
  if (fm.AuditIdea.value == "" || fm.AuditIdea.value == null){
		alert("请先填写审核意见!");
      return;
  }
  
  // 审核意见超过700字符时，提示！2006-01-01 小赵
  if(fm.AuditIdea.value.length >=700)
  {
  	alert("审核意见不能超过700个字符，请您重新填写！");
  	return;
  }
  
  //提交
  fm.action="./LLClaimPrepayAuditConclusionSave.jsp";
  fm.fmtransact.value = "INSERT";
  submitForm();
}

//校验是否可以预付确认
function checkConfirmPay()
{
    
    //var sql="select AuditConclusion from LLClaimUWMain a where a.clmno='"+document.all('ClmNo').value+"' and CheckType='1'";
    //prompt("预付审核确认前校验是否保存审核结论的sql",sql);
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimPrepayAuditInputSql");
		mySql.setSqlId("LLClaimPrepayAuditSql12");
		mySql.addSubPara(document.all('ClmNo').value);
	var tAuditConclusion = easyExecSql(mySql.getString());
	if (tAuditConclusion)
	{
		for (var i = 0; i < tAuditConclusion.length; i++)
		{
		    if (tAuditConclusion[i] == null||tAuditConclusion[i]=="")
		    {
		        alert("还没有保存审核结论,不能点击预付确认!");
		        return false;
		    }
		    else
		    {
		    	fm.tAuditConclusion.value=tAuditConclusion[i];
		    	//alert("fm.tAuditConclusion.value:"+fm.tAuditConclusion.value);
		    }
		}
	}
	else
	{
		alert("还没有保存审核结论,不能点击预付确认!");
		return false;
	}
	
	return true;
	
}


//"预付确认"按钮，预付节点消亡，生成待审核节点，任务回到待审核工作队列中，并标上预付标志。
function LLClaimPrepayCofirm()
{
	
	if(!KillTwoWindows(fm.ClmNo.value,'30'))
	{
	  	  return false;
	}	
	
    //校验是否可以预付确认
    if (!checkConfirmPay())
    {
        return;
    } 

	
    fm.action="./LLClaimAuditShiftPreSave.jsp";
    fm.fmtransact.value = "SHIFT";

	submitForm(); //提交		
}


//初始化“ 审核意见”，根据赔案号查询LLClaimUWMain表
function initLLClaimUWMainQuery()
{
	try
	{
		/*var	strSQL = "select a.auditconclusion,(select codename from ldcode where codetype = 'llclaimpreconclusion' and code = a.auditconclusion),"
	    	         +" a.auditidea from LLClaimUWMain a where clmno = '"+fm.ClmNo.value+"'";*/
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimPrepayAuditInputSql");
		mySql.setSqlId("LLClaimPrepayAuditSql13");
		mySql.addSubPara(fm.ClmNo.value);
		//prompt("initLLClaimUWMainQuery-初始化“ 审核意见”，根据赔案号查询LLClaimUWMain表",strSQL);

		var tAuditConclusion = easyExecSql(mySql.getString());
		
		if (tAuditConclusion)
		{
			for (var i = 0; i < tAuditConclusion.length; i++)
			{
			    fm.AuditConclusion.value=tAuditConclusion[0][0];
			    fm.AuditConclusionName.value=tAuditConclusion[0][1];
			    fm.AuditIdea.value=tAuditConclusion[0][2];
			}
		}
	}
	catch(ex)
    {
      alert("LLClaimPrepayAudit.js-->initLLClaimUWMainQuery函数中发生异常:数据查询错误!");
    }

}


//案件核赔履历表--查询 2005-08-16添加
function LLQueryUWMDetailClick()
{
//	alert("案件核赔履历查询");
    var strUrl="LLColQueryClaimUWMDetailMain.jsp?ClmNo="+fm.ClmNo.value;
//    window.open(strUrl,"案件核赔履历查询");
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}