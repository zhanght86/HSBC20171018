//程序名称：PEdorUWManuAdd.js
//程序功能：保全人工核保条件承保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var str = "";
var turnPage = new turnPageClass();
var turnPageF = new turnPageClass();
var mySql = new SqlClass();
var turnPage1 ;
var k = 0;
var cflag = "1";  //问题件操作位置 1.核保
var spanObj;
var mOperate;
/**=========================================================================
    修改状态：提交前的数据校验操作
    修改原因：
    修 改 人：续涛
    修改日期：2005.10.28
   =========================================================================
**/
function beforeSubmit()
{
    var i = 0;
    i = SpecGrid.mulLineCount ; 
    if(i==0)
    {
        alert("未录入新的核保加费信息!");
        return 1;
    }
            
    var cPolNo = fm.PolNo2.value ;
    if(cPolNo == null || cPolNo == "")
    {
        alert("未选择加费的投保单!");
        return 1;
    }
       
   
    var tSelNo = SpecGrid.getSelNo();
    if(tSelNo < 1)
    {
        alert("请选中一行记录！");
        return 1;
    }
         
    tSelNo = tSelNo - 1;           
    var tDutuCode     = SpecGrid.getRowColData(tSelNo,1);
    var tPayPlanType  = SpecGrid.getRowColData(tSelNo,2);
    var tPayStartDate = SpecGrid.getRowColData(tSelNo,3);           
    var tPayEndDate   = SpecGrid.getRowColData(tSelNo,4);

    var tAddFeeDirect = SpecGrid.getRowColData(tSelNo,7);
    var tPrem         = SpecGrid.getRowColData(tSelNo,8);           
    var tAddReason    = fm.AddReason.value;    
    if ( tDutuCode == null || tDutuCode ==''){
         alert('[加费类型]不能为空！');
        return 1;
    }else if ( tPayPlanType == null || tPayPlanType ==''){
         alert('[加费原因]不能为空！');
        return 1;
    }else if ( tPayStartDate == null || tPayStartDate ==''){
         alert('[起始日期]不能为空！');
        return 1;
    }else if ( tPayEndDate == null || tPayEndDate ==''){
         alert('[终止日期]不能为空！');
        return 1;
    }else if ( tAddFeeDirect == null || tAddFeeDirect ==''){
         alert('[加费对象]不能为空！');
        return 1;
    }else if ( tPrem == null || tPrem ==''){
         alert('[加费金额]不能为空！');
        return 1;
    }
    return 0;
}
    
/**=========================================================================
    修改状态：提交保存按钮对应操作
    修改原因：
    修 改 人：续涛
    修改日期：2005.10.28
   =========================================================================
**/
function saveClick()
{

    var rRet = beforeSubmit();
    if (rRet==0)
    {
        mOperate='SAVE';
        submitForm();
     }
}

/**=========================================================================
    修改状态：提交删除按钮对应操作
    修改原因：
    修 改 人：续涛
    修改日期：2005.10.28
   =========================================================================
**/

function deleteClick()
{   

    var rRet = beforeSubmit();
    if (rRet==0)
    {
        mOperate='DELETE';
        submitForm();
     }
}

/**=========================================================================
    修改状态：提交后台数据
    修改原因：
    修 改 人：续涛
    修改日期：2005.10.28
   =========================================================================
**/
function submitForm()
{   
   
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    
	fm.hideOperate.value=mOperate;
    fm.action= "./LLUWAddFeeSave.jsp";
    document.getElementById("fm").submit(); //提交       
}


//提交后操作,服务器数据返回后执行的操作
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
        if( mOperate == "DELETE")
        {
             SpecGrid.delRadioTrueLine();	
             fm.AddReason.value = "";
        }             
        if( mOperate!= "DOUBLE")
        {
             getPolGridCho();
        }
    }    
    
    mOperate = '';
}


//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
   if(cDebug=="1")
   {
		parent.fraMain.rows = "0,0,50,82,*";
   }
   else 
   {
  		parent.fraMain.rows = "0,0,0,82,*";
   }
}
         

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
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


function manuchkspecmain()
{
	document.getElementById("fm").submit();
}



/**=========================================================================
    修改状态：加费险种查询初始化操作
    修改原因：
    修 改 人：续涛
    修改日期：2005.10.28
   =========================================================================
**/
function QueryPolAddGrid(tContNo,tInsuredNo)
{	
    var tSql;
    var arr = new Array;
    
        
    //获取原保单信息
   /* tSql = "select LCPol.PolNo,LCPol.ContNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.RiskVersion,LCPol.AppntName,LCPol.InsuredName,LCPol.standprem from LCPol where 1=1"				 			
			 + "  and LCPol.ContNo ='"+tContNo+"'"
             + "  and LCPol.AppFlag ='1' and LCPol.PolNo = LCPol.MainPolNo"             
			 + "  order by LCPol.polno";	*/		
     mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
	mySql.setSqlId("LLUWAddFeeSql1");
	mySql.addSubPara(tContNo);       
    //prompt("加费险种查询初始化操作",tSql);
    
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,PolAddGrid);
    }
    else
    {
        initPolAddGrid();
    }
                 
}
	
/**=========================================================================
    修改状态：点击险种信息时的事件
    修改原因：
    修 改 人：续涛
    修改日期：2005.10.28
   =========================================================================
**/    
function getPolGridCho()
{
    //var tSelNo        = PolAddGrid.getSelNo()-1;
    //tRow              = tSelNo;
    var cPolNo2       = PolAddGrid.getRowColData(0,1);   //得到保单号
    var cPolNo        = PolAddGrid.getRowColData(0,1);   //险种号
    var tRiskCode     = PolAddGrid.getRowColData(0,4);   //得到险种编码
    fm.PolNo.value    = cPolNo;
    fm.PolNo2.value   = cPolNo2 ;
    fm.RiskCode.value = tRiskCode;
    var tClmNo        = fm.ClmNo.value;
    if(cPolNo != null && cPolNo != "")
    {
         str = initlist(cPolNo);
         ///if( tSelNo < 0)
         //    alert("请指定序号！");
         //else
             initSpecGrid(str);
//         LLUWPremMaster(cPolNo,cPolNo2);
         QueryLCPrem(cPolNo);
         QueryAddReason(cPolNo2);
         QueryLLUWPremSub();  
    }
       
}

/**=========================================================================
    修改状态：查询返回值
    修改原因：
    修 改 人：续涛
    修改日期：2005.10.28
    
    修改状态：得到加费得起止日期
    修改原因：以前得取值不正确
    修 改 人：万泽辉
    修改日期：2005.11.24
   =========================================================================
**/
function initlist(tPolNo)
{   
	
	k++;
    var strSQL3 = "";
   /* strSQL3     = "select dutycode from lcduty where "+k+" = "+k
                + " and polno = '"+tPolNo+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
	mySql.setSqlId("LLUWAddFeeSql2");
	mySql.addSubPara(tPolNo);     

    str4        = easyQueryVer3(mySql.getString(), 1, 0, 1);
    var strSQL  = "";
    var tClmNo  = fm.ClmNo.value;
    //出险日期
    //strSQL      = "select accdate from llcase where caseno = '"+tClmNo+"'";
    mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
	mySql.setSqlId("LLUWAddFeeSql3");
	mySql.addSubPara(tClmNo);
    var str1    = easyExecSql(mySql.getString());
    var tContNo = fm.ContNo.value;
    //第一次交费日期
    //var strSQL1  = " select paystartdate from lcprem where contno = '"+tContNo+"'";
    mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
	mySql.setSqlId("LLUWAddFeeSql4");
	mySql.addSubPara(tContNo);
    var str0     = easyExecSql(mySql.getString());
    //得到出险日期和第一次交费日期的间隔月份
    var str3     = dateDiff(str0[0][0],str1[0][0],'M');
    var arr      = str0[0][0].split('-');
    
    //得到月份
    var month    = arr[1].split('0');
    if( month[0] != null&& month[0] != "" )
       var intMonth = parseInt(month[0])+ parseInt(str3)- 1  ;
    if( month[1] != null&& month[1] != "" )
       var intMonth = parseInt(month[1])+ parseInt(str3)- 1  ;
    var payStartDate ="" ;
   
    //获得加费后起始日期
    if( intMonth <= 12 )
    {
    	 arr[1]       = parseInt(intMonth) + 1 ;
    	 if(arr[1] < 10)
            arr[1] = "0" + arr[1];
    	 arr[2]       = parseInt(arr[2]) ;
         payStartDate = arr[0]+'-'+arr[1]+'-'+arr[2];
    }
    else
    {
        arr[2]      = parseInt(arr[2]) ;
        var intYear = parseInt(intMonth)/12 ;
        arr[0]      = parseInt(arr[0]) + parseInt(intYear) ;
        arr[1]      = parseInt(intMonth) - 12*parseInt(intYear) + 1 ;
        if(arr[1] < 10)
            arr[1] = "0" + arr[1];
        payStartDate = arr[0]+'-'+arr[1]+'-'+arr[2];
    }
    
    //获得加费后终止日期
    //var strSQL2 = " select payenddate from lcprem where polno = '"+tPolNo+"'";
    mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
	mySql.setSqlId("LLUWAddFeeSql5");
	mySql.addSubPara(tPolNo);
    var endDate = easyExecSql(mySql.getString());
    //得到加费类型，起止日期得字符串
    str = str4+'|'+payStartDate+'|'+endDate[0][0];
    return str;
   
}

/**=========================================================================
    修改状态：查询LLUWPremMaster表中加费的信息
    修改原因：
    修 改 人：续涛
    修改日期：2005.10.28
   =========================================================================
**/ 
function LLUWPremMaster(tPolNo,tContNo)
{
   
    // 书写SQL语句
    var strSQL = "";
    var i, j, m, n; 
    
    //alert(document.all('BatNo').value);
    
    //获取本次加费信息
    /*strSQL   = "select dutycode,payplantype,paystartdate,payenddate,suppriskscore,SecInsuAddPoint,AddFeeDirect,prem from LLUWPremMaster where 1=1 "
             + " and contno ='"+tContNo+"'"
             + " and BatNo ='"+document.all('BatNo').value+"'"
             + " and payplancode like '000000%%'"
             + " and state = '1'"; */
   mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
	mySql.setSqlId("LLUWAddFeeSql6");
	mySql.addSubPara(tContNo); 
	mySql.addSubPara(document.all('BatNo').value);          
//    prompt("获取本次加费信息",strSQL);
    turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1); 

    //判断是否查询成功
    if (turnPage.strQueryResult) 
    {  
        //查询成功则拆分字符串，返回二维数组
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
        turnPage.pageDisplayGrid = SpecGrid;    
        //保存SQL语句
        turnPage.strQuerySql     = strSQL; 
        //设置查询起始位置
        turnPage.pageIndex       = 0;  
        //在查询结果数组中取出符合页面显示大小设置的数组
        var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //调用MULTILINE对象显示查询结果
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }
    //获取工作流子任务号
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    return true;    
}


/**=========================================================================
    修改状态：查询已经录入加费原因
    修改原因：
    修 改 人：续涛
    修改日期：2005.10.28
   =========================================================================
**/ 
function QueryAddReason(tPolNo)
{
    var tSql;
    var arr = new Array;	
    var tClmNo = fm.ClmNo.value;
        
    //查询加费原因
   /* tSql = "select changepolreason from llcuwmaster where 1=1 "
        + " and CaseNo='"+tClmNo+"'"
        + " and contno = '"+tPolNo+"'"
        ; */
    mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
	mySql.setSqlId("LLUWAddFeeSql7");
	mySql.addSubPara(tClmNo); 
	mySql.addSubPara(tPolNo);                           
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        arr[0][0]==null||arr[0][0]=='null'?'0':fm.AddReason.value  = arr[0][0];
    }    
}

/**=========================================================================
    修改状态：当双击“加费金额”录入框时触发该函数
    修改原因：
    修 改 人：续涛 
    修改日期：2005.10.28
    
    修改状态：当双击“加费金额”录入框时触发该函数
    修改原因：实现这个“加费金额”计算
    修 改 人：万泽辉
    修改日期：2005.11.08
   =========================================================================
**/
function CalHealthAddFee(span)
{
    spanObj = span;
    var tSelNo11 = SpecGrid.getSelNo();
    mOperate = "DOUBLE";
    if(tSelNo11 < 1)
     {
        alert("请选中对应序号！");
        return 1;
     }
   // var tSelNo = PolAddGrid.getSelNo()-1; 
    var tRiskCode = PolAddGrid.getRowColData(0,4);
    var tPolNo = PolAddGrid.getRowColData(0,1);
    var tMainPolNo = PolAddGrid.getRowColData(0,2);
    //查询该责任下的保准保费
    /*var tSql = " select sum(standprem) from lcprem where 1 = 1 "
             + " and polno = '"+tPolNo+"' and dutycode='"+document.all( spanObj ).all( 'SpecGrid1' ).value+"' "
             + " and payplancode in (select payplancode from lmdutypayrela where dutycode='"+document.all( spanObj ).all( 'SpecGrid1' ).value+"')";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
	mySql.setSqlId("LLUWAddFeeSql8");
	mySql.addSubPara(tPolNo); 
	mySql.addSubPara(document.all( spanObj ).all( 'SpecGrid1' ).value);   
	mySql.addSubPara(document.all( spanObj ).all( 'SpecGrid1' ).value);
    turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);   
    if (!turnPage.strQueryResult){
       alert("查询该责任下的保准保费金额失败")
       return ;
    }
    else{
    	 turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
       var tPrem = turnPage.arrDataCacheSet[0][0];  
    }
  
    var tSuppRiskScore = document.all( spanObj ).all( 'SpecGrid5' ).value;
    //如果该险种是附加险，判断该附加险又没有加费算法。
   /* var tSql = " select * from LMDutyPayAddFee where 1=1 "
             + " and riskcode = '"+tRiskCode+"'"
             + " and dutycode = '"+document.all( spanObj ).all( 'SpecGrid1' ).value+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
	mySql.setSqlId("LLUWAddFeeSql9");
	mySql.addSubPara(tRiskCode);  
	mySql.addSubPara(document.all( spanObj ).all( 'SpecGrid1' ).value);       
    turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);   
   
    if((tPolNo != tMainPolNo) && !(turnPage.strQueryResult)){     
    
       /*var tSql = " select addpoint from lduwuser where 1 = 1 "
             + " and usercode='"+tOperator+"' and uwtype='1'";*/
       mySql = new SqlClass();
		mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
		mySql.setSqlId("LLUWAddFeeSql10");
		mySql.addSubPara(tOperator);  
       turnPage.strQueryResult = easyQueryVer3(mySql.getString(), 1, 1, 1);    
      //判断是否查询成功
 
     if (!turnPage.strQueryResult)
     {
       alert("查询附加险加费评点权限有问题")
       return ;
     }  
     turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    
     var RiskScore = turnPage.arrDataCacheSet[0][0];          
   
     if(parseFloat(tSuppRiskScore) > parseFloat(RiskScore) )
     {
        alert("加费评点过大，超过该核保是的核保权限")
        return;
     }
     else{
        var tAddPrem = tSuppRiskScore/100*tPrem;
        document.all( spanObj ).all( 'SpecGrid8' ).value = tAddPrem;      
     }
    
   }else{
    document.all('DutyCode').value      = document.all( spanObj ).all( 'SpecGrid1' ).value;
    document.all('AddFeeType').value    = document.all( spanObj ).all( 'SpecGrid2' ).value;
    document.all('SuppRiskScore').value = document.all( spanObj ).all( 'SpecGrid5' ).value;
    document.all('SecondScore').value   = document.all( spanObj ).all( 'SpecGrid6' ).value;
    document.all('AddFeeObject').value  = document.all( spanObj ).all( 'SpecGrid7' ).value;
     
    var i = 0;
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    
	fm.action= "./LLUWAddFeeCalSave.jsp";
    document.getElementById("fm").submit(); //提交
   }
}

/**=========================================================================
    修改状态：初始化加费对象[废弃]
    修改原因：
    修 改 人：续涛
    修改日期：2005.10.28
   =========================================================================
**/

//初始化加费对象
function initAddObj(span)
{
	spanObj = span;
	var tRiskCode = PolAddGrid.getRowColData(0,4);
	var tDutyCode = document.all( spanObj ).all( 'SpecGrid1' ).value;
	var tAddFeeType = document.all(spanObj).all('SpecGrid2').value;
	
  if(tAddFeeType = '01')
  {
	/*var srtSql = "select AddFeeObject from LMDutyPayAddFee where 1=1 "
	           + " and riskcode = '"+tRiskCode+"'"
	           + " and DutyCode = '"+tDutyCode+"'"
	           + " and AddFeeType = '01'";*/
	mySql = new SqlClass();
		mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
		mySql.setSqlId("LLUWAddFeeSql11");
		mySql.addSubPara(tRiskCode);
		mySql.addSubPara(tDutyCode);  
	turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1); 

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	document.all( spanObj ).all( 'SpecGrid8' ).value = "";
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  document.all( spanObj ).all( 'SpecGrid1' ).value = turnPage.arrDataCacheSet[0][0];  

}
else
	{
		document.all( spanObj ).all( 'SpecGrid7' ).value = "";
    return "";
	}
    return true;	
	
}
	


/*********************************************************************
 *  Click事件，当双击“加费金额”录入框时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showUWSpec( span)
{
	spanObj = span;
	
	// 书写SQL语句
	var strSQL = "";
	var tRiskCode="";
	var tInsuredSex="";
	var tInsuredAppAge="";
	var tSuppRiskCore="";
	var tAddFeeKind="";
	var tPayEndYear="";
    //校验录入信息的完整性
	if(document.all( spanObj ).all( 'SpecGrid1' ).value == ""){
		alert("请录入加费类型信息！");
		return;
	}
	
	if(document.all( spanObj ).all( 'SpecGrid2' ).value == "")
	{
		alert("请录入加费原因信息！");
		return;
	}
	else
	tAddFeeKind=document.all( spanObj ).all( 'SpecGrid2' ).value;
	
 	if(document.all( spanObj ).all( 'SpecGrid5' ).value=="")
 	{
		alert("请录入加费评点信息！");
		return;
	}
	else
	tSuppRiskCore=document.all( spanObj ).all( 'SpecGrid5' ).value;
	
	
   //此处对职业加费处理还没有确定。
   if(tAddFeeKind=="1"||tAddFeeKind=="3")
   {
 
	   	//准备加费评点要素	
	  
		//strSQL = "select AddFeeAMNT("+tAddFeeKind+",riskcode,polno,"+tSuppRiskCore+") from LCpol where polno='"+PolAddGrid.getRowColData(0,1)+"'";	
		
			mySql = new SqlClass();
			mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
			mySql.setSqlId("LLUWAddFeeSql12");
			mySql.addSubPara(tAddFeeKind);
			mySql.addSubPara(tSuppRiskCore);
			mySql.addSubPara(PolAddGrid.getRowColData(0,1));
					
		turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);   
	
	    //判断是否查询成功
	    if (!turnPage.strQueryResult) 
	    {
		  	alert("保单加费评点计算失败！");
		    return "";
	    }
	   
	    //查询成功则拆分字符串，返回二维数组
	    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	    document.all( spanObj ).all( 'SpecGrid6' ).value = turnPage.arrDataCacheSet[0][0];	
	    return true;	
    }
    else
  	{
	    alert("未定义职业加费评点！");
	    return "";
    }
   	
}    

/**=========================================================================
    修改状态：查询LCPrem表，得到保单的缴费信息列表
    修改原因：
    修 改 人：万泽辉
    修改日期：2005.11.03
   =========================================================================
**/ 
function QueryLCPrem(tPolNo)
{
	turnPage1 = new turnPageClass();

	var strSQL = "";
	/*strSQL = "select dutycode,(case payplantype when '01' then '首期健康加费'  "
	       + " when '02' then '首期职业加费'  when '03' then '复效健康加费'  when '04' "
	       + " then '复效职业加费' end),paystartdate,payenddate,suppriskscore,"
	       + " SecInsuAddPoint,(case addfeedirect when '01' then '投保人' when '02'"
	       + " then '被保人' when '03' then '多被保险人' when '04' then '第二被保险人' end),"
	       + " prem  from lcprem where polno = '"+tPolNo+"' and payplancode like '000000%%' "
	       + " order by dutycode";*/
		mySql = new SqlClass();
		mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
		mySql.setSqlId("LLUWAddFeeSql13");
		mySql.addSubPara(tPolNo);
	turnPage1.queryModal(mySql.getString(),LCPremGrid);
}

function RadioSelected()
{
	 var tSelNo = SpecGrid.getSelNo();
     if(tSelNo < 1)
     {
         alert("请选中一行记录！");
         return 1;
     }
}

//加费轨迹信息查询 2006-02-09 Add by zhaorx
function QueryLLUWPremSub()
{
	//var turnPageF = new turnPageClass();
	var tClmNoF = fm.ClmNo.value;
	var tPolNoF = PolAddGrid.getRowColData(0,1);
	
	/*var tSQLF = " select batno,dutycode,(case payplantype when '01' then '首期健康加费' when '02' then '首期职业加费' when '03' then '复效健康加费' when '04' then '复效职业加费' end),"
              + " paystartdate,payenddate,suppriskscore,SecInsuAddPoint, "
              + " (case addfeedirect when '01' then '投保人' when '02' then '被保人' when '03' then '多被保险人' when '04' then '第二被保险人' end),"
              + " prem from lluwpremsub where 1=1 and polno = '"+tPolNoF+"' and clmno = '"+tClmNoF+"' order by batno ";*/
		mySql = new SqlClass();
		mySql.setResourceName("claimnb.LLUWAddFeeInputSql");
		mySql.setSqlId("LLUWAddFeeSql14");
		mySql.addSubPara(tPolNoF);
		mySql.addSubPara(tClmNoF);
	turnPageF.queryModal(mySql.getString(),LLUWPremSubGrid);
}