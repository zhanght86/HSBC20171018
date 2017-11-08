//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();

/**=========================================================================
    修改状态：新建
    修改原因：页面初始化的查询
    修 改 人：续涛
    修改日期：2005.07.14

   =========================================================================
**/


function initQuery()
{
    //显示赔付未涉及的合同信息
    /*var strSql = " select a.GrpContNo,a.ContNo,"
       +" case a.ContType when '1' then '个险' when '2' then '团险' end , "
       +" a.AppntNo,a.AppntName,a.AppntSex,a.AppntBirthday, "
       +" a.InsuredNo,a.InsuredName,a.InsuredSex,a.InsuredBirthday, "
       +" a.PolApplyDate,a.SignCom,a.SignDate,a.CValiDate, "       
       +" case a.AppFlag when '0' then '投保' when '1' then '承保' when '2' then '续保' end  "              
       +" from LCCont a where 1=1 "
       +" and a.ContNo not in (select distinct ContNo from LLClaimPolicy where ClmNo='"+fm.ClmNo.value+"')"
       +" and (a.AppntNo in ('"+fm.CustNo.value+"') or InsuredNo in ('"+fm.CustNo.value+"'))"       
       ;    */   
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLColQueryContDealInputSql");
	mySql.setSqlId("LLColQueryContDealSql1");
	mySql.addSubPara(fm.ClmNo.value ); 
	mySql.addSubPara(fm.CustNo.value ); 
	mySql.addSubPara(fm.CustNo.value ); 
    //显示所有数据
    var arr = easyExecSql(mySql.getString());
    if (arr) 
    {
        displayMultiline(arr,ContNoReferGrid);
        //arr[0][0]==null||arr[0][0]=='null'?'0':fm.PreGiveAmnt.value  = arr[0][0];
    }
    
    
   /* var strSqlA = " select distinct a.GrpContNo,a.ContNo,"
       +" case a.ContType when '1' then '个险' when '2' then '团险' end , "
       +" a.AppntNo,a.AppntName,a.AppntSex,a.AppntBirthday, "
       +" a.InsuredNo,a.InsuredName,a.InsuredSex,a.InsuredBirthday, "
       +" a.PolApplyDate,a.SignCom,a.SignDate,a.CValiDate, "
       +" case a.AppFlag when '0' then '投保' when '1' then '承保' when '2' then '续保' when '4' then '终止' when '9' then '续保' end,  "
       +" '0',"
       +" '赔案相关'"
       +" from LCCont a where 1=1 "
       +" and a.ContNo in (select distinct ContNo from LLClaimPolicy where ClmNo='"+fm.ClmNo.value+"' and GiveType in ('0','1'))"
       ;

    var strSqlB = " select distinct a.GrpContNo,a.ContNo,"
       +" case a.ContType when '1' then '个险' when '2' then '团险' end , "
       +" a.AppntNo,a.AppntName,a.AppntSex,a.AppntBirthday, "
       +" a.InsuredNo,a.InsuredName,a.InsuredSex,a.InsuredBirthday, "
       +" a.PolApplyDate,a.SignCom,a.SignDate,a.CValiDate, "
       +" case a.AppFlag when '0' then '投保' when '1' then '承保' when '2' then '续保' when '4' then '终止' when '9' then '续保' end,  "
       +" '0',"
       +" '赔案无关'"
       +" from LCCont a where 1=1 "
       +" and a.ContNo in (select distinct ContNo from LLCUWBatch where CaseNo='"+fm.ClmNo.value+"' and InEffectFlag in ('1','2') and State = '1' and ClaimRelFlag='1')"
       ;
                             
    var strSqlC = strSqlA + " union " + strSqlB;*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLColQueryContDealInputSql");
	mySql.setSqlId("LLColQueryContDealSql2");
	mySql.addSubPara(fm.ClmNo.value ); 
	mySql.addSubPara(fm.ClmNo.value );  
    var brr = easyExecSql(mySql.getString());
        
    //fm.memo.value = strSqlC;    
    if (brr) 
    {    
        displayMultiline(brr,ContReferGrid);
    }
    

}

/**=========================================================================
    修改状态：新建
    修改原因：当单选钮选中赔案涉及合同的MutiLine时触发的函数
    修 改 人：续涛
    修改日期：2005.07.14
   =========================================================================
**/
function getContReferGrid(spanID,parm)
{

    divContCalResult.style.display="";
    divContPolRefer.style.display="";
    divLLContState.style.display="";
        
    var tNo = ContReferGrid.getSelNo();
    var tContNo = ContReferGrid.getRowColData(tNo - 1,2);//合同号
    
    fm.ContNo.value = tContNo;  //将合同号赋值给隐藏的变量
                
    getLCPol(tContNo);          //得到险种信息
    getLLContState(tContNo,'null');
    getLLBalance(tContNo);      //调用查询计算结果的方法
    
    
    /*strSql = " select a.dealstate,"
       +" (select c.codename from ldcode c where c.codetype = 'llcontdealtype' and trim(c.code)=trim(a.dealstate)) "
       +" from LLContState a where 1=1 "
       +" and a.ClmNo  in ('"+fm.ClmNo.value+"')"
       +" and a.ContNo  in ('"+tContNo+"')";*/
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLColQueryContDealInputSql");
	mySql.setSqlId("LLColQueryContDealSql3");
	mySql.addSubPara(fm.ClmNo.value ); 
	mySql.addSubPara(tContNo);         
//    alert(strSql);
    arr = easyExecSql(mySql.getString());
    if (arr) 
    {
//        arr[0][0]==null||arr[0][0]=='null'?'0':fm.StateReason.value  = arr[0][0];
//        arr[0][1]==null||arr[0][1]=='null'?'0':fm.StateReasonName.value  = arr[0][1];        
    }
    else
    {
//        fm.StateReason.value  = "";
//        fm.StateReasonName.value  = "";
    }  
    
    fm.PolStateReason.value  = "";
    fm.PolStateReasonName.value  = "";   
}


/**=========================================================================
    修改状态：新建
    修改原因：当单选钮选中险种的MutiLine时触发的函数
    修 改 人：续涛
    修改日期：2005.07.14
   =========================================================================
**/
function getContPolReferGrid(spanID,parm)
{
        
    var tNo = ContPolReferGrid.getSelNo();
    var tContNo = ContPolReferGrid.getRowColData(tNo - 1,3);//合同号
    var tPolNo = ContPolReferGrid.getRowColData(tNo - 1,4);//险种号
        
    fm.ContNo.value = tContNo;  //将合同号赋值给隐藏的变量
    fm.PolNo.value = tPolNo;  //将合同号赋值给隐藏的变量



    fm.PolStateReason.value  = "";
    fm.PolStateReasonName.value  = "";
    
    getLLContState(tContNo,tPolNo,'Y');
    getLLBalance(tContNo);
}


/**=========================================================================
    修改状态：新建
    修改原因：当单选钮选中结算信息的MutiLine时触发的函数
    修 改 人：续涛
    修改日期：2005.07.14
   =========================================================================
**/
function getContCalResultGrid(spanID,parm)
{
        
    var tNo = ContCalResultGrid.getSelNo();
    divContCalResultAdjust.style.display="";
    
    var tPolNo = ContCalResultGrid.getRowColData(tNo - 1,5);//险种号    
    var tFeeOperationType = ContCalResultGrid.getRowColData(tNo - 1,1);//业务类型
    var tSubFeeOperationType = ContCalResultGrid.getRowColData(tNo - 1,3);//子业务类型
    var tNewPay = ContCalResultGrid.getRowColData(tNo - 1,8);//当前金额    
    var tOldPay = ContCalResultGrid.getRowColData(tNo - 1,9);//原始金额
    var tOldAdjRemark = ContCalResultGrid.getRowColData(tNo - 1,10);//历史调整原因

    fm.PolNo.value = tPolNo;  //将合同号赋值给隐藏的变量
    fm.FeeOperationType.value = tFeeOperationType;
    fm.SubFeeOperationType.value = tSubFeeOperationType;   
    fm.NewPay.value = tNewPay;    
    fm.OldPay.value = tOldPay;
    fm.OldAdjRemark.value = tOldAdjRemark;    
    
        
    fm.PolStateReason.value  = "";
    fm.PolStateReasonName.value  = "";
}



/**=========================================================================
    修改状态：新建
    修改原因：得到LCPol险种的信息
    修 改 人：续涛
    修改日期：2005.07.14
   =========================================================================
**/

function getLCPol(pContNo)
{
//    alert(pContNo);
    /*var strSql = " select a.GrpContNo,a.GrpPolNo,a.ContNo,a.PolNo,a.ContType,"
       +" a.MainPolNo,a.RiskCode,"
       +" a.InsuredNo,a.InsuredName,a.InsuredSex,a.InsuredBirthday,"
       +" '',a.SignCom,a.SignDate,a.CValiDate,"
       +" case a.AppFlag when '0' then '投保' when '1' then '承保' when '4' then '终止' when '9' then '续保' end  "
       +" from LCPol a where 1=1 "   
       +" and a.ContNo in ('"+pContNo+"')"
       +" and a.AppFlag in ('1','4')"              
       +" order by a.PolNo "
       ;*/

mySql = new SqlClass();
	mySql.setResourceName("claim.LLColQueryContDealInputSql");
	mySql.setSqlId("LLColQueryContDealSql4"); 
	mySql.addSubPara(pContNo);   
    //显示所有数据
    var arr = easyExecSql(mySql.getString());
    if (arr) 
    {
        displayMultiline(arr,ContPolReferGrid);
    }
    else
    {
        initContPolReferGrid();         //计算结果
    }        
}

/**=========================================================================
    修改状态：新建
    修改原因：得到LLContState的信息
    修 改 人：续涛
    修改日期：2005.07.14
   =========================================================================
**/

function getLLContState(pContNo,pPolNo,pType)
{
//    alert(pContNo);
   /* var strSql = " select a.ContNo,a.InsuredNo,a.PolNo,a.StateType,"
       +" a.State, case a.State when '0' then '有效' when '1' then '终止' end,"
       +" a.StartDate,a.EndDate,"
       +" a.DealState, "
       +" (select c.codename from ldcode c where c.codetype in ('llcontpoldealtype','llcontdealtype') and trim(c.code)=trim(a.DealState)),"
       +" a.ClmState,case a.ClmState when '0' then '合同处理' when '1' then '赔案处理' when '2' then '合同赔案处理' end "
       +" from LLContState a where 1=1 "
       +" and a.ClmNo in ('"+fm.ClmNo.value+"') "       
       +" and a.ContNo in ('"+pContNo+"') "
       +" and a.StateType in ('Terminate') "
	   +" and a.ClmState not in ('9') "      ;*/
       
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLColQueryContDealInputSql");
	mySql.setSqlId("LLColQueryContDealSql5"); 
	mySql.addSubPara(fm.ClmNo.value);   
	mySql.addSubPara(pContNo);   
	   
    if ( pType == "Y" )
    {
      // strSql = strSql + " and a.PolNo in ('"+pPolNo+"')"
       mySql = new SqlClass();
	mySql.setResourceName("claim.LLColQueryContDealInputSql");
	mySql.setSqlId("LLColQueryContDealSql6"); 
	mySql.addSubPara(fm.ClmNo.value);   
	mySql.addSubPara(pContNo);   
	mySql.addSubPara(pPolNo);   
    }
    
   // strSql = strSql + " order by a.PolNo ";
    
    //alert(strSql);
    //显示所有数据
    var arr = easyExecSql(mySql.getString());
    if (arr) 
    {
        displayMultiline(arr,LLContStateGrid);
    }
    else
    {
        initLLContStateGrid();         //
    }
}


/**=========================================================================
    修改状态：新建
    修改原因：得到LLBalance合同终止的计算结果
    修 改 人：续涛
    修改日期：2005.07.14
   =========================================================================
**/
function getLLBalance(pContNo)
{
    //alert(pContNo);
  /*  var strSql = " select a.FeeOperationType,"
       +" (select c.codename from ldcode c where c.codetype = 'llcontdealtype' and trim(c.code)=trim(a.FeeOperationType)), "
       +" a.SubFeeOperationType,"
       +" (select f.SubBalTypeDesc from LLBalanceRela f where f.SubBalType=a.SubFeeOperationType),"
       +" a.PolNo,RiskCode,GetDate,a.Pay, "
       +" a.OriPay,a.AdjRemark "          
       +" from LLBalance a where 1=1 "
       +" and a.ClmNo  in ('"+fm.ClmNo.value+"')"       
       +" and a.ContNo in ('"+pContNo+"')"
       +" and substr(a.FeeOperationType,1,1) in ('C','D') "*/
	 mySql = new SqlClass();
	mySql.setResourceName("claim.LLColQueryContDealInputSql");
	mySql.setSqlId("LLColQueryContDealSql7"); 
	mySql.addSubPara(fm.ClmNo.value);   
	mySql.addSubPara(pContNo);    
    //显示所有数据
    var arr = easyExecSql(mySql.getString());
    if (arr) 
    {
        displayMultiline(arr,ContCalResultGrid);
    }
    else
    {
        initContCalResultGrid();         //计算结果
    }                   
}


/**=========================================================================
    修改状态：新建
    修改原因：执行保存结论并计算的功能
    修 改 人：续涛
    修改日期：2005.07.14
   =========================================================================
**/
function saveContAutoClick()
{
    
    mOperate="CONTAUTO";
    fm.action = "./LLClaimContPolDealAutoSave.jsp";
    //alert("自动"+fm.action);
    submitForm();
}

/**=========================================================================
    修改状态：新建
    修改原因：执行保存结论并计算的功能
    修 改 人：续涛
    修改日期：2005.07.14
   =========================================================================
**/
function saveAndCalClick()
{
    mOperate="CONT";
    fm.action = "./LLClaimContDealSave.jsp";
    submitForm();
}

/**=========================================================================
    修改状态：新建
    修改原因：执行险种结论保存的功能
    修 改 人：续涛
    修改日期：2005.07.14
   =========================================================================
**/
function saveContPolClick()
{
    var tNo = ContPolReferGrid.getSelNo();
    if (tNo<1)
    {
        alert("请先选择一条险种信息,再执行此操作!");
        return;
    }
    mOperate="POL";
    fm.action = "./LLClaimContPolDealSave.jsp";
    submitForm();
}

/**=========================================================================
    修改状态：新建
    修改原因：公共函数--提交到后台操作
    修 改 人：续涛
    修改日期：2005.07.14
   =========================================================================
**/

function submitForm()
{
    var showStr="正在保存结论并生成计算数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
    fm.hideOperate.value=mOperate;    
    document.getElementById("fm").submit(); //提交
       
}

/**=========================================================================
    修改状态：新建
    修改原因：公共函数--Save页面--服务器数据返回后执行的操作
    修 改 人：续涛
    修改日期：2005.07.14
   =========================================================================
**/
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "FAIL" )
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
        mOperate = '';
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
        
        getLLBalance(fm.ContNo.value);    
        if (fm.hideOperate.value=='POL')
        {                
            getLLContState(fm.ContNo.value,fm.PolNo.value,'Y');        
        }
        else
        {
            getLLContState(fm.ContNo.value,fm.PolNo.value);        
        }
    }

}
