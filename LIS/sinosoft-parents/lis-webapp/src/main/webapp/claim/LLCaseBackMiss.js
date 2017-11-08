
//程序名称：LLCaseBackMiss.js
//程序功能：处理界面上的相关操作
//创建日期：2005-10-20 11:50
//创建人  ：wanzh
//更新记录：

//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass();
var mySql = new SqlClass();

//提交完成后所作操作
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
        //直接取得数据跳转到立案界面
        location.href="LLAppealInput.jsp?claimNo="+fm.ClmNo.value;
    }
    tSaveFlag ="0";
}

//提交数据
function submitForm()
{
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
    document.getElementById("fm").submit(); //提交
    tSaveFlag ="0";
}

/**=========================================================================
    修改状态：开始
    修改原因：初始化表格1，显示可选赔案列表
    修 改 人：万泽辉
    修改日期：2005.10.21
   =========================================================================
**/

function queryGrid()
{
	if(fm.RptNo.value==null||fm.RptNo.value=="")
	{
	      alert("请录入要回退的赔案号！");
	      return;
	}
	
	//2009-01-09 zhangzheng 增加对未产生财务信息时的回退处理(结案保存前回退到审核阶段)
    /*var strSQL = "select k1,k2,k3,k4,k5,k6,k7,k8 from"
               + "( "
               + "  select rgtno k1,  rgtantname k2,  RgtDate k3, assigneename k4,   (case clmstate  when '50' then '结案' when '60' then  '完成'  end) k5, accidentdate k6,"	  
               + "  endcasedate k7,  '0' k8"
               + "  from llregister where 1 = 1"
               + getWherePart( 'RgtNo' ,'RptNo')
               + getWherePart( 'RgtantName' ,'RptorName','like')
               + getWherePart( 'RgtDate' ,'RgtDate')
               + getWherePart( 'accidentdate' ,'accidentdate')
               + getWherePart( 'endcasedate' ,'endcasedate')
               + getWherePart( 'AssigneeName' ,'AssigneeName','like')
               + " and MngCom like '" + fm.ManageCom.value + "%%'"
               + " and not exists(select 1 from llcaseback b where b.clmno=llregister.rgtno and backstate='0')"
               + " and ClmState in('60','50')"   
               + " and RgtState <> '13' "//RgtState 13 申诉案件
               + " and RgtClass='1'"
               + " )"
               + " order by k1 ";*/
	
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLCaseBackMissInputSql");
		mySql.setSqlId("LLCaseBackMissSql1");
		mySql.addSubPara(  fm.RptNo.value  );
		mySql.addSubPara(  fm.RptorName.value  );
		mySql.addSubPara(  fm.RgtDate.value  );
		mySql.addSubPara(  fm.accidentdate.value  );
		mySql.addSubPara(  fm.endcasedate.value  );
		mySql.addSubPara(  fm.AssigneeName.value  );
		mySql.addSubPara(  fm.ManageCom.value  );

    //prompt("回退管理查询可用赔案sql",strSQL);
    turnPage.queryModal(mySql.getString(),LLCaseBackGrid);
}
/**=========================================================================
    修改状态：开始
    修改原因：相应LLCaseBackGrid点击事件，选择一条赔案记录
    修 改 人：万泽辉
    修改日期：2005.10.21
   =========================================================================
**/

function LLCaseBackGridClick()
{
}


/**=========================================================================
    修改状态：开始
    修改原因：[回退]按钮，进入案件回退原因填写
    修 改 人：万泽辉				
    修改日期：2005.10.21
   =========================================================================
**/

function CaseBackClaim()
{
	var selno = LLCaseBackGrid.getSelNo() - 1;
	if (selno <0)
	{
	      alert("请选择要回退处理的赔案！");
	      return;
	}
	
	var tClmNo = LLCaseBackGrid.getRowColData(selno,1);
	var tBackType = LLCaseBackGrid.getRowColData(selno,8);

	
	var strSQL="";
	var tCount="";
	
	//2009-03-11 zhangzheng 如果本次赔案没有结案,需要判断下是否已经有预付赔案,如果有同样视为已给付
	if(tBackType=="1")
	{
		//strSQL = "select count(1) from ljagetclaim where feeoperationtype='B' and otherno='"+tClmNo+"'";
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLCaseBackMissInputSql");
		mySql.setSqlId("LLCaseBackMissSql2");
		mySql.addSubPara(  tClmNo);
		//prompt("校验是否存在预付赔款sql",strSQL)
	    tCount = easyExecSql(mySql.getString());
	    if (tCount != "0")
	    {
	    	tBackType='0';
	    }
	}
	
	//alert(tBackType);

	
	//校验是否有此案件存在正在处理的回退案件
	/*strSQL = "select count(1) from llcaseback "
                + " where clmno = '" + tClmNo + "'"
                + " and backstate = '0'";*/
   		mySql = new SqlClass();
		mySql.setResourceName("claim.LLCaseBackMissInputSql");
		mySql.setSqlId("LLCaseBackMissSql3");
		mySql.addSubPara(  tClmNo);
		
    tCount = easyExecSql(mySql.getString());
    if (tCount != "0")
    {
        alert("此案件存在未完成的回退,不允许申请!");
        return;
    }
	
	location.href="LLCaseBackInput.jsp?claimNo="+tClmNo+"&SubmitFlag=0&BackType="+tBackType+"";
	
}

/**=========================================================================
    修改状态：开始
    修改原因：未完成的回退工作队列
    修 改 人：万泽辉
    修改日期：2005.11.23
   =========================================================================
**/
function querySelfGrid()
{
   /* var strSQL = "select backno ,clmno ,backdate ,backdesc,backtype from llcaseback "
	         + " where 1=1 and operator = '" + fm.Operator.value + "'"
	         + " and backstate = '0'"
	         + " order by backno ";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLCaseBackMissInputSql");
	mySql.setSqlId("LLCaseBackMissSql4");
	mySql.addSubPara(   fm.Operator.value );
	//prompt("已保存回退记录的案件查询",strSQL);
    turnPage2.queryModal(mySql.getString(),SelfLLCaseBackGrid);
}

/**=========================================================================
    修改状态：开始
    修改原因：响应未回退完成的工作队列的函数SelfLLCaseBackGridClick
    修 改 人：万泽辉
    修改日期：2005.11.23
   =========================================================================
**/
function SelfLLCaseBackGridClick()
{
    var i = SelfLLCaseBackGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        
        var tBackNo   = SelfLLCaseBackGrid.getRowColData(i,1);
        var tClmNo    = SelfLLCaseBackGrid.getRowColData(i,2);
        var tBackDesc = SelfLLCaseBackGrid.getRowColData(i,4);
    	var tBackType = SelfLLCaseBackGrid.getRowColData(i,5);
    	//alert(tBackType);
        location.href = "LLCaseBackInput.jsp?claimNo="+tClmNo+"&SubmitFlag=1&BackType="+tBackType+"";
    }
}
