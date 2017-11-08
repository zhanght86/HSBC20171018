var showInfo;
var turnPage = new turnPageClass();
var mySql = new SqlClass();

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





/**=========================================================================
    修改状态：开始
    修改原因：案件回退信息保存提交
    修 改 人：zhangzheng 增加校验
    修改日期：2009.01.10
   =========================================================================
**/
function CaseBackSave()
{
	
    //首先进行非空字段检验
    if (beforeSubmit())
    {
  	  fm.action = "./LLCaseBackInputSave.jsp?Flag=1";
      submitForm();
    }

}

/**=========================================================================
    修改状态：开始
    修改原因：案件回退信息完成操作
    修 改 人：万泽辉
    修改日期：2005.11.17
   =========================================================================
**/

function CaseBackSubmit()
{
	  if(fm.BackTypeQ.value=='0')
	  {
		  //只有实付金额>0时才需要判断是否已核销暂交费
		  var tClmNo = fm.ClmNo.value;
		  //var strSql = " select distinct 1 from ljaget where otherno='"+tClmNo+"' and Sumgetmoney>0";
		  //prompt("",strSql);
		  mySql = new SqlClass();
		mySql.setResourceName("claim.LLCaseBackInputSql");
		mySql.setSqlId("LLCaseBackSql1");
		mySql.addSubPara(tClmNo ); 
		  var tljagety = easyExecSql(mySql.getString());
		  if (tljagety)
		  {
			  //strSql = " select distinct 1 from ljapay,ljagetclaim where actugetno=payno and incomeno='"+tClmNo+"'";
			  //prompt("",strSql);
			  mySql = new SqlClass();
			mySql.setResourceName("claim.LLCaseBackInputSql");
			mySql.setSqlId("LLCaseBackSql2");
			mySql.addSubPara(tClmNo ); 
			  var tljapay = easyExecSql(mySql.getString());
			  if (tljapay == null)
			  {
				  alert("请先核销暂交费!");
				  return false;
			  }
		  } 
	  }
	  
	  fm.action = "./LLCaseBackInputSave.jsp?Flag=2";
      submitForm();
    
}
/**=========================================================================
    修改状态：开始
    修改原因：案件回退信息查询，其中tClmNo为传入的赔案号
    修 改 人：万泽辉
    修改日期：2005.10.23
   =========================================================================
**/
function CaseBackQuery()
{
    var tClmNo = fm.ClmNo.value;
	var strUrl="LLClaimQueryInput.jsp?claimNo="+tClmNo+"&phase=0";
	//赔案查询
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    
}
/**=========================================================================
    修改状态：开始
    修改原因：返回按钮 返回到案件回退页面
    修 改 人：万泽辉
    修改日期：2005.10.21
   =========================================================================
**/

function goToBack()
{
    location.href="LLCaseBackMissInput.jsp";
}
/**=========================================================================
    修改状态：开始
    修改原因：案件回退原因登记
    修 改 人：万泽辉
    修改日期：2005.11.17
   =========================================================================
**/

function queryCaseBack()
{
    var tClmNo = fm.ClmNo.value;
   /* var strSql = " select BackNo from LLCaseBack where "
               + " ClmNo = '" + tClmNo + "'";*/
     mySql = new SqlClass();
		mySql.setResourceName("claim.LLCaseBackInputSql");
		mySql.setSqlId("LLCaseBackSql3");
		mySql.addSubPara(tClmNo ); 
    var tBackCase = easyExecSql(mySql.getString());
    if (tBackCase != null)
    {
        fm.BackNo.value = tBackCase[0][0];
    }
}

/**=========================================================================
    修改状态：开始
    修改原因：列出赔案下的应收总表列表
    修 改 人：万泽辉
    修改日期：2005.11.22
   =========================================================================
**/

function queryLJsPayGrid()
{
	var tClmNo = fm.ClmNo.value;
	/*var strSQL = " select GetNoticeNo,AppntNo,SumDuePayMoney,PayDate,case BankOnTheWayFlag " 
	           + " when '1' then '是' when '0' then '否' end "
	           + " from ljspay where otherno ='" + tClmNo + "'" 
               + " order by GetNoticeNo ";*/
     mySql = new SqlClass();
		mySql.setResourceName("claim.LLCaseBackInputSql");
		mySql.setSqlId("LLCaseBackSql4");
		mySql.addSubPara(tClmNo ); 
	turnPage.pageLineNum = 5;
	turnPage.queryModal(mySql.getString(), LJsPayGrid);
}

/**=========================================================================
    修改状态：开始
    修改原因：回退案件未处理完的回退队列 
              添加“原理赔结论”，“原给付总金额”，“回退原因类型”
    修 改 人：万泽辉
    修改日期：2005.12.28 
   =========================================================================
**/
function selectCaseBack()   
{
	var tClmNo = fm.ClmNo.value;
    /*var strSql = " select backno,clmno,backdesc,backreason,remark ,applyuser,"
               + " applydate,orirealypay,case origivetype when '0' then '给付' when '1' then '拒付' when '2' "
	           + " then '客户撤案' when '3' then '公司撤案' end,newgivetype,"
	           +" CheckBackPreFlag, (select codename from ldcode where codetype = 'whetherbackpreflag' and code = CheckBackPreFlag) "
               + " from llcaseback where "
               + " clmno = '" + tClmNo + "'"
               + " and backstate = '0'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLCaseBackInputSql");
	mySql.setSqlId("LLCaseBackSql5");
	mySql.addSubPara(tClmNo ); 
    //prompt("",strSql);
    var tCaseBack = easyExecSql(mySql.getString());
    if (tCaseBack != null)
    {
    	
        fm.BackNo.value      = tCaseBack[0][0];//回退号
        fm.ClmNo.value       = tCaseBack[0][1];//赔案号
        fm.BackDesc.value    = tCaseBack[0][2];//回退描述
        fm.BackReason.value  = tCaseBack[0][3];//回退原因
        fm.Remark.value      = tCaseBack[0][4];//备注
        fm.ApplyUesr.value   = tCaseBack[0][5];//申请人
        fm.ApplyDate.value   = tCaseBack[0][6];//回退申请时间
        fm.OriRealyPay.value = tCaseBack[0][7];//原给付总额
        fm.OriGiveType.value = tCaseBack[0][8];//原理赔结论
        fm.NewGiveType.value = tCaseBack[0][9];//新理赔结论
        fm.whetherBackPre.value = tCaseBack[0][10];//是否同时回退预付信息标志
        fm.whetherBackPreName.value = tCaseBack[0][11];//是否同时回退预付信息名称
        showOneCodeName('llclaimconclusion','NewGiveType','NewGiveTypeName');
        showOneCodeName('llbackreason','BackReason','BackReasonName');
        
    }
    
}

/**=========================================================================
   =========================================================================
**/
    

function afterSubmit( FlagStr, content )
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
        //fm.CaseBackSubmitBt.disabled = true;
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
        goToBack();
    }
    
}

/**=========================================================================
=========================================================================
**/
function afterSubmit2( FlagStr, content )
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
        fm.CaseBackSubmitBt.disabled = true;
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
        selectCaseBack();
        queryLJsPayGrid();
        fm.CaseBackSubmitBt.disabled = false;
    }
    
}



/**=========================================================================
    修改原因：案件回退涉及的应收记录的核销
    修 改 人：周磊
    修改日期：2005-11-23 17:42
   =========================================================================
**/
function CaseFeeCancel()
{
	  fm.action = "./LLCaseBackFeeSave.jsp";
      fm.target = "fraSubmit";
      submitForm();
}

//提交，保存按钮对应操作
function PrintClick()
{

  
  //var arrReturn = new Array();
  var tSel = LJsPayGrid.getSelNo();
  if( tSel == 0 || tSel == null )
  {
		alert( "请先选择一条记录，再点击打印通知书按钮。" );
	  return;
	}
	else
	{
		var tPrtNoticeNo = LJsPayGrid.getRowColData(tSel-1,1);
		fm.PrtNoticeNo.value = tPrtNoticeNo;
		fm.action = "./LLCaseBackNoticeSave.jsp";
		fm.target = "../f1print";
		submitForm();
	    //fm.submit();
	}
	//submitForm();
	showInfo.close();
	fm.target="fraSubmit";//Modify by zhaorx 2006-11-29
}


//2009-01-10 zhangzheng 双击下拉框后的响应函数
function afterCodeSelect( cCodeName, Field ) {

    //alert(cCodeName);  

	//是否同时回退预付信息
    if(cCodeName=="whetherbackpreflag"){
    	
    	if(fm.whetherBackPre.value=='1'&&fm.BackTypeQ.value=='1'){
    		
    		alert("案件还没有到完成状态,不能同时回退预付信息!");
        }
    	else{
    		

    	}
    	
        return true;
    }
}


//提交前的校验、计算
function beforeSubmit()
{

	  if(fm.NewGiveType.value == ""|| fm.NewGiveType.value == null)
	  {
	  	   alert("申请回退后的理赔结论不能为空！");
	  	   return false;
	  }
	  
	  if(fm.BackReason.value == "" || fm.BackReason.value == null)
	  {
	  	   alert("回退原因不能为空！");
	  	   return false;
	  }
	  
	  if(fm.BackDesc.value == "" || fm.BackDesc.value == null)
	  {
	  	   alert("回退原因详细说明不能为空！");
	  	   return false;
	  }
	  
	  //赔案状态为完成状态时必须选择是否回退预付信息
	  if(fm.BackTypeQ.value=='0')
	  {
		  if(fm.whetherBackPre.value == "" || fm.whetherBackPre.value == null)
		  {
		  	   alert("赔案状态为完成状态时需要选择是否回退预付信息!");
		  	   return false;
		  }
	  }
	  
	  return true;
}