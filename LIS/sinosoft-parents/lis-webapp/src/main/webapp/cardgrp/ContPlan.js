//该文件中包含客户端需要处理的函数和事件
var mDebug  = "0";
var mOperate="";
var showInfo;
var arrDataSet;
var mSwitch  = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var QueryResult  = "";
var QueryCount   = 0;
var mulLineCount = 0;
var QueryWhere="";
var tSearch   = 0;
window.onfocus=myonfocus;

function afterCodeSelect( cCodeName, Field ){
    if(cCodeName == "InsuMult")
    {
    //通过险种修改保额或是保费
    var mult = fm.all('InsuMult').value;
    var tRiskCode=getRiskCount();
    //查看计划类别
    var tPlanType = fm.all('RiskPlan1').value;
    var tRiskPlan = fm.all('RiskPlan').value;
    if(tPlanType=="1"){
    for(j=0;j<tRiskCode.length;j++){
        var ttRiskCode = tRiskCode[j];
       //通过套餐得到的保障计划 在修改份数时 其保费、保额变化不是通过查询一保存的保障计划 而是查询保险套餐的值
       var tSqlAmnt=" select d.CalFactorValue,a.dutycode	from LMRiskDutyFactor a, LMRisk b, LMDuty c, LDPlanDutyParam  d "
               +" where 1 = 1 and a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
               +" and a.DutyCode = d.DutyCode and a.CalFactor = d.CalFactor and b.RiskVer = d.RiskVersion "
               +" and ContPlanCode = '"+tRiskPlan+"' and b.riskcode = '"+ttRiskCode+"' and a.calfactor='Amnt'  order by a.RiskCode, d.MainRiskCode, a.DutyCode "
       var tSqlPrem=" select d.CalFactorValue,a.dutycode	from LMRiskDutyFactor a, LMRisk b, LMDuty c, LDPlanDutyParam  d "
               +" where 1 = 1 and a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
               +" and a.DutyCode = d.DutyCode and a.CalFactor = d.CalFactor and b.RiskVer = d.RiskVersion "
               +" and ContPlanCode = '"+tRiskPlan+"' and b.riskcode = '"+ttRiskCode+"' and a.calfactor='Prem'  order by a.RiskCode, d.MainRiskCode, a.DutyCode "
       var tAmntInfo = easyExecSql(tSqlAmnt);
       var tPremInfo = easyExecSql(tSqlPrem);
       if(tPremInfo!=null&&tPremInfo!=""&&tPremInfo!="null"){
       for (k=0;k<tPremInfo.length;k++){
            //多责任多保费 如212401
            tPrem=tPremInfo[k][0];
            tDutyCode=tPremInfo[k][1];
            if(tPrem!=null&&tPrem!=""&&tPrem!="null"){
           for(i=0;i<ContPlanGrid.mulLineCount;i++){
              //alert("Factor："+ContPlanGrid.getRowColData(i,5));
              //alert("RiskCode:"+ContPlanGrid.getRowColData(i,2));
              if(ContPlanGrid.getRowColData(i,5)=="Prem" && ContPlanGrid.getRowColData(i,8) != "" 
                &&ContPlanGrid.getRowColData(i,2) ==ttRiskCode
                &&ContPlanGrid.getRowColData(i,3) ==tDutyCode)
           		{
           		//alert(ttRiskCode+"! 将要修改保费！");
           		//alert(mult+"*"+tPrem);
           		 var newprem = mult*tPrem;
          		 ContPlanGrid.setRowColData(i,8,""+newprem);
          		 }
           }
          }
         }
        }
        //alert("tAmnt:"+tAmnt);
        if(tAmntInfo!=null&&tAmntInfo!=""||tAmntInfo!="null"){
        for (k=0;k<tAmntInfo.length;k++){
            //多责任多保费 如212401
            tAmnt=tAmntInfo[k][0];
            tDutyCode=tAmntInfo[k][1];
            if(tAmnt!=null&&tAmnt!=""||tAmnt!="null"){
        for(i=0;i<ContPlanGrid.mulLineCount;i++){
              //alert("Factor："+ContPlanGrid.getRowColData(i,5));
              //alert("RiskCode:"+ContPlanGrid.getRowColData(i,2));
              if(ContPlanGrid.getRowColData(i,5)=="Amnt" && ContPlanGrid.getRowColData(i,8) != "" 
                 &&ContPlanGrid.getRowColData(i,2) ==ttRiskCode
                 &&ContPlanGrid.getRowColData(i,3) ==tDutyCode)
           		{
           		//alert(ttRiskCode+"! 将要修改保额！");
           		//alert(mult+"*"+tAmnt);
        		 var newamnt = mult*tAmnt;
         		 ContPlanGrid.setRowColData(i,8,""+newamnt);
         		 }
            }
           }
          }
         }
       }
    }else{
    //alert(tRiskCode);
    for(j=0;j<tRiskCode.length;j++){
        var ttRiskCode = tRiskCode[j];
        var tsqlprem=" select (calfactorvalue / remark2),a.dutycode  from lccontplandutyparam a ,lccontplan b where a.grpcontno = b.grpcontno and a.contplancode=b.contplancode and a.calfactor = 'Prem' and a.contplancode = '"+fm.all('ContPlanCode').value+"' and a.grpcontno = '"+fm.all('GrpContNo').value+"' and a.riskcode='"+ttRiskCode+"'";      
        var tsqlamnt=" select (calfactorvalue / remark2),a.dutycode  from lccontplandutyparam a ,lccontplan b where a.grpcontno = b.grpcontno and a.contplancode=b.contplancode and a.calfactor = 'Amnt' and a.contplancode = '"+fm.all('ContPlanCode').value+"' and a.grpcontno = '"+fm.all('GrpContNo').value+"' and a.riskcode='"+ttRiskCode+"'";      
        var tPremInfo = easyExecSql(tsqlprem);
        var tAmntInfo = easyExecSql(tsqlamnt);
       // alert("tPrem:"+tPrem);
        if(tPremInfo!=null&&tPremInfo!=""&&tPremInfo!="null"){
         for (k=0;k<tPremInfo.length;k++){
            //多责任多保费 如212401
            tPrem=tPremInfo[k][0];
            tDutyCode=tPremInfo[k][1];
            if(tPrem!=null&&tPrem!=""&&tPrem!="null"){
               for(i=0;i<ContPlanGrid.mulLineCount;i++){
              //alert("Factor："+ContPlanGrid.getRowColData(i,5));
              //alert("RiskCode:"+ContPlanGrid.getRowColData(i,2));
              if(ContPlanGrid.getRowColData(i,5)=="Prem" && ContPlanGrid.getRowColData(i,8) != "" 
                   &&ContPlanGrid.getRowColData(i,2) ==ttRiskCode
                   &&ContPlanGrid.getRowColData(i,3) ==tDutyCode)
           		{
           		//alert(ttRiskCode+"! 将要修改保费！");
           		//alert(mult+"*"+tPrem);
           		 var newprem = mult*tPrem;
          		 ContPlanGrid.setRowColData(i,8,""+newprem);
          		 }
             }
            }
           }
        }
        //alert("tAmnt:"+tAmnt);
        if(tAmntInfo!=null&&tAmntInfo!=""||tAmntInfo!="null"){
        for (k=0;k<tAmntInfo.length;k++){
            //多责任多保费 如212401
            tAmnt=tAmntInfo[k][0];
            tDutyCode=tAmntInfo[k][1];
            if(tAmnt!=null&&tAmnt!=""||tAmnt!="null"){
               for(i=0;i<ContPlanGrid.mulLineCount;i++){
              //alert("Factor："+ContPlanGrid.getRowColData(i,5));
              //alert("RiskCode:"+ContPlanGrid.getRowColData(i,2));
                if(ContPlanGrid.getRowColData(i,5)=="Amnt" && ContPlanGrid.getRowColData(i,8) != "" 
                    &&ContPlanGrid.getRowColData(i,2) ==ttRiskCode
                    &&ContPlanGrid.getRowColData(i,3) ==tDutyCode)
           		  {
           		  //alert(ttRiskCode+"! 将要修改保额！");
           		  //alert(mult+"*"+tAmnt);
        		   var newamnt = mult*tAmnt;
         		   ContPlanGrid.setRowColData(i,8,""+newamnt);
         		   }
            }
           }
          }
        }
    }
    //var premsql = " select (calfactorvalue / remark2)  from lccontplandutyparam a ,lccontplan b where a.grpcontno = b.grpcontno and a.contplancode=b.contplancode and a.calfactor = 'Prem' and a.contplancode = '"+fm.all('ContPlanCode').value+"' and a.grpcontno = '"+fm.all('GrpContNo').value+"'";
    //var tPerPrem = easyExecSql(premsql);
    //var amntsql = " select (calfactorvalue / remark2)  from lccontplandutyparam a ,lccontplan b where a.grpcontno = b.grpcontno and a.contplancode=b.contplancode and a.calfactor = 'Amnt' and a.contplancode = '"+fm.all('ContPlanCode').value+"' and a.grpcontno = '"+fm.all('GrpContNo').value+"'";
    //var tPerAmnt = easyExecSql(amntsql);
    //var m=ContPlanGrid.mulLineCount
    //  if(m>=0)
    //  {
    //  var mult = fm.all('InsuMult').value;
      //   for(i=0; i<m; i++)
      //   {
      //     if(ContPlanGrid.getRowColData(i,5)=="Prem" && ContPlanGrid.getRowColData(i,8) != "")
      //     {
      //     var newprem = mult*tPerPrem;
      //     ContPlanGrid.setRowColData(i,8,""+newprem);
      //     }
      //     if(ContPlanGrid.getRowColData(i,5)=="Amnt" && ContPlanGrid.getRowColData(i,8) != "")
      //     {
      //     var newamnt = mult*tPerAmnt;
      //     ContPlanGrid.setRowColData(i,8,""+newamnt);
      ///     }
      //   }
     // }
     }
    }
	//判定双击操作执行的是什么查询
	if (cCodeName=="GrpRisk"){
		var tRiskFlag = fm.all('RiskFlag').value;
		//由于附加险不带出主险录入框，因此判定当主附险为S的时候隐藏
		if (tRiskFlag!="S"){
			divmainriskname.style.display = 'none';
			divmainrisk.style.display = 'none';
			fm.all('MainRiskCode').value = fm.all('RiskCode').value;
		}
		else{
			//divmainriskname.style.display = '';
			//divmainrisk.style.display = '';
			fm.all('MainRiskCode').value = fm.RiskCode.value;//edit by yaory 20050914
			//alert(fm.all('MainRiskCode').value);
		}
	}
	//判断是否选择保险套餐
	//alert(cCodeName);//yaory
	if (cCodeName=="RiskPlan1"){
		var tRiskPlan1 = fm.all('RiskPlan1').value;
		if (tRiskPlan1!="0"){
			divriskcodename.style.display = 'none';
			divriskcode.style.display = 'none';
			divcontplanname.style.display = '';
			divcontplan.style.display = '';
			divplankind1name.style.display = '';
			divplankind1.style.display = '';	
			divplankind2name.style.display = '';
			divplankind2.style.display = '';
			divplankind3name.style.display = '';
			divplankind3.style.display = '';
			divRiskPlanPSave.style.display='';
			divRiskPlanSave.style.display='none';								
			ContPlanGrid.lock();
		}
		else{
			divriskcodename.style.display = '';
			divriskcode.style.display = '';
			divcontplanname.style.display = 'none';
			divcontplan.style.display = 'none';
			divplankind1name.style.display = 'none';
			divplankind1.style.display = 'none';	
			divplankind2name.style.display = 'none';
			divplankind2.style.display = 'none';
			divplankind3name.style.display = 'none';
			divplankind3.style.display = 'none';
			divRiskPlanPSave.style.display='none';
			divRiskPlanSave.style.display='';				
			ContPlanGrid.unLock();
		}
	}
	//将保险套餐数据带入界面
	if (cCodeName=="RiskPlan"){
		var tRiskPlan = fm.all('RiskPlan').value;
		if (tRiskPlan!=null&&tRiskPlan!=""){
			//增加方法是GRID不可编辑
			initContPlanGridyry();
			showPlan();
			
			
		}
	}
}
function initContPlan(){
  		var tRiskPlan1 = fm.all('RiskPlan1').value;
		if (tRiskPlan1!="0"){
			divriskcodename.style.display = 'none';
			divriskcode.style.display = 'none';
			divcontplanname.style.display = '';
			divcontplan.style.display = '';
			divplankind1name.style.display = '';
			divplankind1.style.display = '';	
			divplankind2name.style.display = '';
			divplankind2.style.display = '';
			divplankind3name.style.display = '';
			divplankind3.style.display = '';
			divRiskPlanPSave.style.display='';
			divRiskPlanSave.style.display='none';								
			ContPlanGrid.lock();
			}
}

//数据查询
function AddContClick(){
	if (fm.all('ContPlanCode').value == ""){
		alert("请输入计划编码！");
		fm.all('ContPlanCode').focus();
		return false;
	}
	if(fm.all('RiskCode').value ==""){
		alert("请选择险种！");
		fm.all('RiskCode').focus();
		return false;
	}
  else{
  	var risksql="select SubRiskFlag from lmriskapp where riskcode='"+fm.all('RiskCode').value+"'"
	  var arrRiskFlag = easyExecSql(risksql);
	  fm.all('RiskFlag').value=arrRiskFlag[0][0];
	  if(fm.all('RiskFlag').value=="M")
	  {
	  	fm.all('MainRiskCode').value=fm.all('RiskCode').value;
	  }
  }	
	if(fm.all('MainRiskCode').value ==""){
		alert("请输入主险信息！");
		fm.all('MainRiskCode').focus();
		return false;
	}
	var lineCount = 0;
	var MainRiskCode = fm.all('MainRiskCode').value
	var sRiskCode ="";
	var sMainRiskCode="";
	ContPlanDutyGrid.delBlankLine("ContPlanDutyGrid");
	lineCount = ContPlanDutyGrid.mulLineCount;
	//还需要添加一个校验，不过比较麻烦，暂时先作了
	for (i=0;i<ContPlanGrid.mulLineCount;i++){
		sRiskCode=ContPlanGrid.getRowColData(i,2);
		sMainRiskCode=ContPlanGrid.getRowColData(i,12);
		//主要是考虑附险会挂在不同的主险下，因此需要双重校验
		if (sRiskCode == fm.all('RiskCode').value && sMainRiskCode == MainRiskCode){
			alert("已添加过该险种保险计划要素！");
			return false;
		}
	}

	var getWhere = "(";
	for (i=0;i<lineCount;i++){
		if (ContPlanDutyGrid.getChkNo(i)){
			//alert(ContPlanDutyGrid.getRowColData(i,1));
			getWhere = getWhere + "'"+ContPlanDutyGrid.getRowColData(i,1)+"',"
		}
	}
	if (getWhere == "("){
		alert("请选则责任信息");
		return false;
	}
	getWhere = getWhere.substring(0,getWhere.length-1) + ")"
	//alert(getWhere);

	// 书写SQL语句
	var strSQL = "";
  QueryCount = 1;//方便在原有计划上修改
	if (QueryCount == 0){
		//查询该险种下的险种计算要素
		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,case a.CalFactorType when '1' then a.CalSql else '' end,'',b.RiskVer,d.GrpPolNo,'"+MainRiskCode+"',a.CalFactorType,c.CalMode "
			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCGrpPol d "
			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
			+ "and a.DutyCode in "+ getWhere + " and a.ChooseFlag in ('0','2') "
			+ "and GrpContNO = '"+GrpContNo+"' and a.RiskCode = '"+fm.all('RiskCode').value+"' order by a.RiskCode,a.DutyCode,a.FactorOrder";
		//fm.all('PlanSql').value = strSQL;
		//alert(strSQL);
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
		if (!turnPage.strQueryResult) {
			alert("没有该险种责任下要素信息！");
			return false;
		}
		QueryCount = 1;
		//查询成功则拆分字符串，返回二维数组
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
		turnPage.pageDisplayGrid = ContPlanGrid;
		//保存SQL语句
		turnPage.strQuerySql = strSQL;
		//设置查询起始位置
		turnPage.pageIndex = 0;
		//在查询结果数组中取出符合页面显示大小设置的数组
		//调用MULTILINE对象显示查询结果
		displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
	}
	else{
		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,case a.CalFactorType when '1' then a.CalSql else '' end,'',b.RiskVer,d.GrpPolNo,'',a.CalFactorType,c.CalMode "
			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCGrpPol d "
			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
			+ "and a.DutyCode in "+ getWhere + " and a.ChooseFlag in ('0','2') "
			+ "and GrpContNO = '"+GrpContNo+"' and a.RiskCode = '"+fm.all('RiskCode').value+"' order by a.RiskCode,a.DutyCode,a.FactorOrder";
		//fm.all('ContPlanName').value = strSQL;
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
		if (!turnPage.strQueryResult) {
			alert("没有该险种责任下要素信息！");
			return false;
		}
		//查询成功则拆分字符串，返回二维数组
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		mulLineCount = ContPlanGrid.mulLineCount;
		//alert(mulLineCount);
		for(i=0; i<turnPage.arrDataCacheSet.length; i++){
			ContPlanGrid.addOne("ContPlanGrid");
			ContPlanGrid.setRowColData(mulLineCount+i,1,turnPage.arrDataCacheSet[i][0]);
			ContPlanGrid.setRowColData(mulLineCount+i,2,turnPage.arrDataCacheSet[i][1]);
			ContPlanGrid.setRowColData(mulLineCount+i,3,turnPage.arrDataCacheSet[i][2]);
			ContPlanGrid.setRowColData(mulLineCount+i,4,turnPage.arrDataCacheSet[i][3]);
			ContPlanGrid.setRowColData(mulLineCount+i,5,turnPage.arrDataCacheSet[i][4]);
			ContPlanGrid.setRowColData(mulLineCount+i,6,turnPage.arrDataCacheSet[i][5]);
			ContPlanGrid.setRowColData(mulLineCount+i,7,turnPage.arrDataCacheSet[i][6]);
			ContPlanGrid.setRowColData(mulLineCount+i,8,turnPage.arrDataCacheSet[i][7]);
			ContPlanGrid.setRowColData(mulLineCount+i,10,turnPage.arrDataCacheSet[i][9]);
			ContPlanGrid.setRowColData(mulLineCount+i,11,turnPage.arrDataCacheSet[i][10]);
			ContPlanGrid.setRowColData(mulLineCount+i,12,MainRiskCode);
			ContPlanGrid.setRowColData(mulLineCount+i,13,turnPage.arrDataCacheSet[i][12]);
		}
	}
	//initContPlanDutyGrid();
}

//数据提交（保存）
function submitForm()
{

    if(verifyInput() == false)
     return false;
	if (!beforeSubmit())
	{
		return false;
	}	
	if(!SaveRiskSubmit())
	{
		return false;
	}
    if(checkCalFactor()==false) return false;
	fm.all('mOperate').value = "INSERT||MAIN";
	if (fm.all('mOperate').value == "INSERT||MAIN")
	{
		if (!confirm('计划 '+fm.all('ContPlanCode').value+' 下的全部险种要素信息是否已录入完毕，您是否要确认操作？'))
		{
			return false;
		}
	}
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	QueryCount = 0;	//重新初始化查询次数
	fm.action="../cardgrp/ContPlanSave.jsp";
	fm.submit(); //提交
}

//返回上一步
function returnparent()
{
	top.close();
	top.opener.fillriskgrid();
}

function getRiskCount(){
    var j=0;
    var k=0;
    var tRiskCode=new Array(); 
    for(i=0;i<ContPlanGrid.mulLineCount;i++)
    {
        var tGetCode = ContPlanGrid.getRowColData(i,2);
        if(tRiskCode[k]==tGetCode){
             continue;
        }else{
           tRiskCode[j]=tGetCode;
           k=j;
           j=j+1;
        }
    }
    return tRiskCode;
}

function checkCalFactor(){
   //校验计划要素的值是否符合后台计算要求
   var tCalRule="";
   var tCalRuleInfo="";//判断计算规则是否可录
   var tFloatRate="";
   var tFloatRateInfo="";//判断费率是否可录
   var tAmnt="";
   var tAmntInfo="";//判断保额是否可录
   var tPrem="";
   var tPremInfo="";//判断保费是否可录
   var tCalFactor="";
   var tMult="";
   var tMultInfo="";
   var tStanbyFlag="";
   var tStanbyFlagInfo="";
   var tRiskCode=getRiskCount();//得到计划有多少险种组成 多个险种的保障计划要分别对每个险种的要素进行校验
   for(j=0;j<tRiskCode.length;j++){
        var tNowRiskCode = tRiskCode[j];
   		var tRiskName = "";
  		 for(i=0;i<ContPlanGrid.mulLineCount;i++)
  		 {
  		  if(tNowRiskCode==ContPlanGrid.getRowColData(i,2)){
  		  tRiskName = ContPlanGrid.getRowColData(i,1);
  		    tCalFactor = ContPlanGrid.getRowColData(i,5);
  	    //alert("tCalFactor:"+tCalFactor);
  		      if(tCalFactor=="CalRule")
   		      {
      		       tCalRule = ContPlanGrid.getRowColData(i,8);
      		       tCalRuleInfo = ContPlanGrid.getRowColData(i,7);
      		       //alert("tCalRule:"+tCalRule);
             //alert("tCalRuleInfo:"+tCalRuleInfo);
     		    }
     		    if(tCalFactor=="Prem")
    		     {
   		          tPrem = ContPlanGrid.getRowColData(i,8);
    		         tPremInfo = ContPlanGrid.getRowColData(i,6);
    		         //alert("tPrem:"+tPrem);
    		     }
    		     if(tCalFactor=="Amnt")
    		     {
    		         tAmnt = ContPlanGrid.getRowColData(i,8);
     	        tAmntInfo = ContPlanGrid.getRowColData(i,6);
             //alert("tAmnt:"+tAmnt);
      		   }
     		    if(tCalFactor=="FloatRate")
      		   {
     		        tFloatRate = ContPlanGrid.getRowColData(i,8);
     		        tFloatRateInfo = ContPlanGrid.getRowColData(i,6);
            // alert("tFloatRate:"+tFloatRate);
      		   }
     		    if(tCalFactor=="Mult"){
     		    	tMult = ContPlanGrid.getRowColData(i,8);
     		    	tMultInfo = ContPlanGrid.getRowColData(i,6);
     		    	if(tMult!=""){
     		           return true;
     		    	}
     		    }
     		    if(tCalFactor=="StandbyFlag1"){
     		    	tStanbyFlag = ContPlanGrid.getRowColData(i,8);
     		    	tStanbyFlagInfo = ContPlanGrid.getRowColData(i,6);
     		    	if(tStanbyFlag!=""){
     		           return true;
     		    	}
     		    }
     		    if(tCalFactor=="StandbyFlag2"){
     		    	tStanbyFlag = ContPlanGrid.getRowColData(i,8);
     		    	tStanbyFlagInfo = ContPlanGrid.getRowColData(i,6);
     		    	if(tStanbyFlag!=""){
     		           return true;
     		    	}
     		    }
     		    if(tCalFactor=="StandbyFlag3"){
     		    	tStanbyFlag = ContPlanGrid.getRowColData(i,8);
     		    	tStanbyFlagInfo = ContPlanGrid.getRowColData(i,6);
     		    	if(tStanbyFlag!=""){
     		           return true;
     		    	}
     		    }
      
   		}
   		}
  		//0-表定费率 1-约定费率 2-表定费率折扣 3-约定保费保额
   		if(tCalRuleInfo!=""){
  		 if(tCalRule==""){
  		   alert(tRiskName+"!请录入计算规则！“"+tCalRuleInfo+"”");
   		   return false;
		   }else{
		     if(tCalRule=="1")
		     {
      //约定费率  保费=保额*浮动费率
      		if(tAmntInfo!=""&&tPremInfo!=""&&tFloatRateInfo!=""){
        //能够录入保额、保费、费率时才对这种计算规则做如下校验
      			if(tAmnt==""||tPrem==""||tFloatRate==""){
     			    alert(tRiskName+"!请录入保费、保额、浮动费率！");
     			    return false;
     			 }else{
     			    var tCalPrem=tAmnt*tFloatRate;
    			     if(tCalPrem!=tPrem){
    			          alert(tRiskName+"!录入的保费"+tPrem+"与计算出来的保费"+tCalPrem+"不等,请重新录入！");
    			          return false;         
    			     }
    		 	 }
    		 	}
   			 }else if(tCalRule=="2"){
   		     //表定费率折扣 只录入保额、浮动费率
   			     if(tAmntInfo!=""&&tPremInfo!=""&&tFloatRateInfo!=""){
   	     //能够录入保额、保费、费率时才对这种计算规则做如下校验
   			     if(tPrem==""){
   	 		      if(tAmnt==""||tFloatRate==""){
   	  		         alert(tRiskName+"!请录入保额、浮动费率！");
   	 		          return false;
   	 		      }
   	 		    }else{
   	 		       alert(tRiskName+"!“表定费率折扣”规则不能录入保费！");
   	 	       return false;
   			     }
   			    }
  		 	 }else if(tCalRule=="3"){
  		 	 if(tAmntInfo!=""&&tPremInfo!=""&&tFloatRateInfo!=""){
   	 //能够录入保额、保费、费率时才对这种计算规则做如下校验
   			      if(tFloatRate==""){
   	 		       if(tAmnt==""||tPrem==""){
   	 		          alert(tRiskName+"!请录入保费、保额！");
   	 		          return false;
   	 		       }
   	 		     }else{
   			        alert(tRiskName+"!“约定保费保额”规则不能录入浮动费率！");
   	 		       return false;
   			      }
   	 		    }
 		  	 }else if(tCalRule=="0"){
 		  	 if(tFloatRateInfo!=""){
 	  	 //能够录入费率时在会做如下校验
 		  	      if(tFloatRate!=""){
  		 	        alert(tRiskName+"!“表定费率”规则不能录入浮动费率！");
  		 	        return false;
  		 	      }
  		 	      if(tAmnt==""&&tPrem==""){
  		 	        alert(tRiskName+"!“表定费率”保额、保费不能同时为空！");
  		 	        return false;
  		 	      }
  		 	     }
 		  	 }else{
  		 	     alert(tRiskName+"!录入的计算规则不合法,请重新录入！“"+tCalRuleInfo+"”");
  		 	     return false;
 		  	 }
 		  }
 		  //清空所有险种内容 校验其他险种要素
 		  var tCalRule="";
   var tCalRuleInfo="";//判断计算规则是否可录
   var tFloatRate="";
   var tFloatRateInfo="";//判断费率是否可录
   var tAmnt="";
   var tAmntInfo="";//判断保额是否可录
   var tPrem="";
   var tPremInfo="";//判断保费是否可录
   var tCalFactor="";
  		 }else{
  		   //校验221702、221703附加团体每日住院给付保险
  		   if(tNowRiskCode!=""&&(tNowRiskCode=="221702"||tNowRiskCode=="221703")){
  		     var tMult=fm.all('InsuMult').value;
  		     if(tMult!=""&&tMult=="1"){
  		       //一份时 保额必须是30或50
  		       if(tAmnt!="30"&&tAmnt!="50"){
  		         alert(tRiskName+"!当投保份数为 1 时“保险金额”只能为30或50");
  		         return false;
  		       }
  		     }else{
  		       //为多份时 校验保险金额是否为30或50 的整数倍
  		       if(Math.round(tAmnt/tMult) == tAmnt/tMult  )
   				{
   					if(tAmnt/tMult == 30  || tAmnt/tMult == 50)
   					{
   					alert(tRiskName+"!您录入的保额"+tAmnt+"是总保额,单份保额为"+tAmnt/tMult+ "元");
					//if( !confirm(tRiskName+"您录入的保额"+tAmnt+"是总保额,单份保额为"+tAmnt/tMult+ "元,是否保存!" ))
					 // return false;
   		 		   }else{
   		  		  	alert(tRiskName+"!保险金额不是30元或50元的整数倍,请检查!");
						return false;
   		 		   }
   		    
   				}
   				else{
   					alert(tRiskName+"!保险金额不是30元或50元的整数倍,请检查!");
					return false;
   				}
  		     }
  		   }
  		 }
   }
   return true;
}
//数据提交（删除）
function DelContClick(){
  //  if(verifyInput()==false)
   //   return false;
	//此方法得到的行数需要-1处理
	var line = ContPlanCodeGrid.getSelNo();
	if (line == 0)
	{
		alert("请选择要删除的计划！");
		fm.all('ContPlanCode').value = "";
		return false;
	}
	else
	{
		fm.all('ContPlanCode').value = ContPlanCodeGrid.getRowColData(line-1,1);
	}	
	fm.all('mOperate').value = "DELETE||MAIN";	
	
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	QueryCount = 0;	//重新初始化查询次数
	fm.submit(); //提交
}

//数据提交（修改）
function UptContClick(){
    if(verifyInput()==false)
       return false;
	if (tSearch == 0){
		alert("请先查询要修改的保障计划！");
		return false;
	}

	fm.all('mOperate').value = "UPDATE||MAIN";
	if (!beforeSubmit()){
		return false;
	}
	if(checkCalFactor()==false) return false;
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	QueryCount = 0;	//重新初始化查询次数
	fm.submit(); //提交
	
	//上拉
	parent.scrollTo(0,-25000);
}

//数据校验
function beforeSubmit(){
	if (fm.all('ContPlanCode').value == ""){
		alert("请输入计划编码！");
		fm.all('ContPlanCode').focus();
		return false;
	}
	if (fm.all('ContPlanName').value == ""){
		alert("请输入保险计划名称！");
		fm.all('ContPlanName').focus();
		return false;
	}
	if (fm.all('Peoples3').value == ""){
		alert("请输入投保人数！");
		fm.all('Peoples3').focus();
		return false;
	}
	if (ContPlanGrid.mulLineCount == 0){
		alert("请输入保险计划详细信息");
		return false;
	}
	var lineCount = ContPlanGrid.mulLineCount;
	var sValue;
	var sCalMode;
	//
	var dutycode;
	var hierarhy;
	var flag109;//是否存在109险种
	var flagerror;//如果存在109险种可选责任的档次大于基本责任则为1
	var duty1;
	var duty2;
	var duty3;
	var duty4;
	//添加要素值信息校验
	for(var i=0;i<lineCount;i++){
      	sValue = ContPlanGrid.getRowColData(i,8);
      	sCalMode = ContPlanGrid.getRowColData(i,5);
      	dutycode=ContPlanGrid.getRowColData(i,3);
      	hierarhy=ContPlanGrid.getRowColData(i,8);
      	//alert(dutycode);
      	//return;
      	//增加对109的特殊校验
      	if(dutycode.substring(0,3)=="109")
      	{
      		flag109="1";
      	}
      	if(dutycode=="109001" && sCalMode=="StandbyFlag1")
      	{
      		duty1=hierarhy;
      		//alert(duty1);
      	}else if(dutycode=="109002" && sCalMode=="StandbyFlag1")
      		{
      			duty2=hierarhy;
      			//alert(duty2);
      		}else if(dutycode=="109003" && sCalMode=="StandbyFlag1")
      			{
      				duty3=hierarhy;
      				//alert(duty3);
      			}else if(dutycode=="109004" && sCalMode=="StandbyFlag1")
      				{
      					duty4=hierarhy;
      					//alert(duty4);
      				}
      	//alert(sCalMode);
      	//互算校验
      	if (sCalMode == "Amnt" || sCalMode == "FloatRate" || sCalMode == "InsuYear" || sCalMode == "Mult" || sCalMode == "Prem" || sCalMode == "PayEndYear" || sCalMode == "Count" || sCalMode == "GetLimit" || sCalMode == "GetRate" || sCalMode == "PeakLine")
      	{
			if (sValue!=""){
				if (!isNumeric(sValue)){
					alert("请录入数字！");
					ContPlanGrid.setFocus(i,8);
					return false;
				}
			}
		}
		
		if (sCalMode == "CalRule")
      	{
			if (sValue!=""){
				if (sValue!="1" && sValue!="2" && sValue!="3" && sValue!="0"){
					alert("请录入正确的计算规则！");
					ContPlanGrid.setFocus(i,8);
					return false;
				}
			}
		}
		
		if (sCalMode == "PayIntv")
      	{
			if (sValue!=""){
				if (sValue!="1" && sValue!="3" && sValue!="6" && sValue!="0" && sValue!="12"){
					alert("请录入正确的交费方式！");
					ContPlanGrid.setFocus(i,8);
					return false;
				}
			}
		}
		
		if (sCalMode == "EndDate")
      	{
			if (sValue!=""){
				if (!isDate(sValue)){
					alert("请录入正确的终止日期！");
					ContPlanGrid.setFocus(i,8);
					return false;
				}
			}
		}
		//保费算保额校验
		//alert(sCalMode);
//      	if (sCalMode == "P"){
//			if (sValue==""){
//				alert("请录入保费！");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//			if (!isNumeric(sValue)){
//				alert("请录入数字！");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//			if(sValue=0){
//				alert("保费不能为0！");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//		}
		//保额算保费校验
//      	if (sCalMode == "G"){
//			if (sValue==""){
//				alert("请录入数字！");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//			if (!isNumeric(sValue)){
//				alert("请录入数字！");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//			if(sValue==0){
//				alert("保额不能为0！");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//		}
		//其他因素算保费保额校验
//      	if (sCalMode == "O"){
//			if (sValue==""){
//				alert("请录入要素值！");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//			if (!isNumeric(sValue)){
//				alert("请录入数字！");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//			if(sValue==0){
//				alert("要素值不能为0！");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//		}
		//录入保费保额校验
//      	if (sCalMode == "I"){
//			if (sValue!=""){
//				if (!isNumeric(sValue)){
//					alert("请录入数字！");
//					ContPlanGrid.setFocus(i,8);
//					return false;
//				}
//			}
//		}
	}
	//增加对109的处理
	if(duty1!="")
	{
		if(duty2!="")
		{
			if(duty2>duty1)
			{
				flagerror="1";
			}
		}
		if(duty3!="")
		{
			if(duty3>duty1)
			{
				flagerror="1";
			}
		}
		if(duty4!="")
		{
			if(duty4>duty1)
			{
				flagerror="1";
			}
		}
	}
	if(flagerror=="1")
	{
		alert("109险种的可选责任的档次不能大于基本责任!");
		return;
	}
	return true;
   
}

function initFactoryType(tRiskCode)
{
	// 书写SQL语句
	var k=0;
	var strSQL = "";
	strSQL = "select distinct a.FactoryType,b.FactoryTypeName,a.FactoryType||"+tRiskCode+" from LMFactoryMode a ,LMFactoryType b  where 1=1 "
		   + " and a.FactoryType= b.FactoryType "
		   + " and (RiskCode = '"+tRiskCode+"' or RiskCode ='000000' )";
    var str  = easyQueryVer3(strSQL, 1, 0, 1);
    return str;
}

/*********************************************************************
 *  Click事件，当点击“保险计划要约录入”按钮时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function nextstep()
{
	var newWindow = window.open("../cardgrp/ContPlanNextInput.jsp?GrpContNo="+fm.all('GrpContNo').value+"&LoadFlag="+LoadFlag,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

function afterSubmit(FlagStr,content){
	showInfo.close();
	if( FlagStr == "Fail" ){
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
	else{
		content = "操作成功！";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	    initContPlanCodeGrid();
	    initContPlanDutyGrid();
	    initContPlanGrid();
	    easyQueryClick();
	    tSearch = 0;
	    QueryCount = 0;
	}
	fm.all('mOperate').value = "";
}

function QueryDutyClick(){
	if (fm.all('ContPlanCode').value == ""){
		alert("请输入保障级别！");
		fm.all('ContPlanCode').focus();
		return false;
	}
	if(fm.all('RiskCode').value ==""){
		alert("请选择险种！");
		fm.all('RiskCode').focus();
		return false;
	}

	initContPlanDutyGrid();

	//查询该险种下的险种计算要素
	var sql="select risktype6 from lmriskapp where riskcode='"+fm.all('RiskCode').value+"'";
	var Result = easyExecSql(sql, 1, 0);
	if(Result=="1")//139 151
	{
		strSQL = "select distinct a.DutyCode,b.DutyName,a.ChoFlag,case a.ChoFlag when 'M' then '必选' when 'B' then '备用' else '可选' end ChoFlagName "
		+ "from LMRiskDuty a, LMDuty b ,LMRiskDutyFactor c "
		+ "where a.DutyCode = b.DutyCode and a.RiskCode = c.RiskCode and a.DutyCode = c.DutyCode "
		+ "and a.RiskCode = '"+fm.all('RiskCode').value+"' order by a.DutyCode";
	}else if(Result=="4"){//188 198
	strSQL = "select distinct a.DutyCode,b.DutyName,a.ChoFlag,case a.ChoFlag when 'M' then '必选' when 'B' then '备用' else '可选' end ChoFlagName "
		+ "from LMRiskDuty a, LMDuty b ,LMRiskDutyFactor c "
		+ "where a.DutyCode = b.DutyCode and a.RiskCode = c.RiskCode and a.DutyCode = c.DutyCode "
		+ "and a.RiskCode = '"+fm.all('RiskCode').value+"' order by a.DutyCode";
	}else if(Result=="3"){//162
	strSQL = "select distinct a.DutyCode,b.DutyName,a.ChoFlag,case a.ChoFlag when 'M' then '必选' when 'B' then '备用' else '可选' end ChoFlagName "
		+ "from LMRiskDuty a, LMDuty b ,LMRiskDutyFactor c "
		+ "where a.DutyCode = b.DutyCode and a.RiskCode = c.RiskCode and a.DutyCode = c.DutyCode "
		+ "and a.RiskCode = '"+fm.all('RiskCode').value+"'  order by a.DutyCode";
	
	}else{
	strSQL = "select distinct a.DutyCode,b.DutyName,a.ChoFlag,case a.ChoFlag when 'M' then '必选' when 'B' then '备用' else '可选' end ChoFlagName "
		+ "from LMRiskDuty a, LMDuty b ,LMRiskDutyFactor c "
		+ "where a.DutyCode = b.DutyCode and a.RiskCode = c.RiskCode and a.DutyCode = c.DutyCode "
		+ "and a.RiskCode = '"+fm.all('RiskCode').value+"' order by a.DutyCode";
}
	//prompt("险种责任查询",strSQL);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	if (!turnPage.strQueryResult) {
		alert("没有该险种下的责任信息！");
		return false;
	}
	//查询成功则拆分字符串，返回二维数组
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
	turnPage.pageDisplayGrid = ContPlanDutyGrid;
	//保存SQL语句
	turnPage.strQuerySql = strSQL;
	//设置查询起始位置
	turnPage.pageIndex = 0;
	//在查询结果数组中取出符合页面显示大小设置的数组
	//调用MULTILINE对象显示查询结果
	displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
	var cDutyCode="";
	var tSql="";
	for(var i=0;i<=ContPlanDutyGrid.mulLineCount-1;i++){
	  cDutyCode=ContPlanDutyGrid.getRowColData(i,1);
	  tSql="select choflag from lmriskduty where riskcode='"+fm.all('RiskCode').value+"' and dutycode='"+cDutyCode+"'";
	  var arrResult=easyExecSql(tSql,1,0);
	  //alert("ChoFlag:"+arrResult[0]);
	  if(arrResult[0]=="M"){
	  	 ContPlanDutyGrid.checkBoxSel(i+1);
	  }
	}
}

function easyQueryClick(){
	initContPlanCodeGrid();

	//查询该险种下的险种计算要素
	strSQL = "select ContPlanCode,ContPlanName,PlanSql,Peoples3 "
		     + "from LCContPlan "
		     + "where 1=1 "
		     + "and GrpContNo = '"+fm.all('GrpContNo').value+"' and ContPlanCode <> '00' order by ContPlanCode";
	//fm.all('ContPlanName').value = strSQL;
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//如果没数据也无异常
	if (!turnPage.strQueryResult) {
		//return false;
	}
	else{
		//QueryCount = 1;
		//查询成功则拆分字符串，返回二维数组
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
		turnPage.pageDisplayGrid = ContPlanCodeGrid;
		//保存SQL语句
		turnPage.strQuerySql = strSQL;
		//设置查询起始位置
		turnPage.pageIndex = 0;
		//在查询结果数组中取出符合页面显示大小设置的数组
		//调用MULTILINE对象显示查询结果
		displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
	}
	//不管前面的查询什么结果，都执行下面的查询
	//如果发生参数错误，导致查询失败，这里程序会出错
	strSQL = "select GrpContNo,ProposalGrpContNo,ManageCom,AppntNo,GrpName from LCGrpCont where 1 = 1 and GrpContNo = '" + GrpContNo + "'";
	turnPage.strQueryResult   = easyQueryVer3(strSQL, 1, 0, 1);
	turnPage.arrDataCacheSet  = decodeEasyQueryResult(turnPage.strQueryResult);
	if(turnPage.arrDataCacheSet != null)
	{
	fm.all('GrpContNo').value = turnPage.arrDataCacheSet[0][0];
	fm.all('ProposalGrpContNo').value = turnPage.arrDataCacheSet[0][1];
	fm.all('ManageCom').value = turnPage.arrDataCacheSet[0][2];
	fm.all('AppntNo').value   = turnPage.arrDataCacheSet[0][3];
	fm.all('GrpName').value   = turnPage.arrDataCacheSet[0][4];
	}
	  
}

//单选框点击触发事件
function ShowContPlan(parm1,parm2){
	if(fm.all(parm1).all('InpContPlanCodeGridSel').value == '1'){
		//当前行第1列的值设为：选中
		var cContPlanCode = fm.all(parm1).all('ContPlanCodeGrid1').value;	//计划编码
		var cContPlanName = fm.all(parm1).all('ContPlanCodeGrid2').value;	//计划名称
		var cPlanSql = fm.all(parm1).all('ContPlanCodeGrid3').value;	//分类说明
		var cPeoples3 = fm.all(parm1).all('ContPlanCodeGrid4').value;	//可保人数
		fm.all('ContPlanCode').value = cContPlanCode;
		fm.all('ContPlanName').value = cContPlanName;
		fm.all('PlanSql').value = cPlanSql;
		fm.all('Peoples3').value = cPeoples3;
		//alert(cContPlanCode);
		var cGrpContNo = fm.all('GrpContNo').value;
		
		//如果是保险套餐需要给计划类型与保险套餐名称给值
		var arrQueryResult = easyExecSql("select plantype,remark,remark2 from lccontplan where grpcontno='"+GrpContNo+"' and contplancode='"+cContPlanCode+"'", 1, 0);
    if(arrQueryResult==null)
    {
    	alert("保障计划查询失败！");
    	return;
    }
  
    if(arrQueryResult[0][0]=="1")
    {
    	divriskcodename.style.display = 'none';
			divriskcode.style.display = 'none';
			divcontplanname.style.display = '';
			divcontplan.style.display = '';
			if (LoadFlag==4||LoadFlag==16||LoadFlag==11||LoadFlag==18)//与init中初始化保持一致
			{
		      divRiskPlanPSave.style.display='none';
			    divRiskPlanSave.style.display='none';						
			}else
				{
		      divRiskPlanPSave.style.display='';
			    divRiskPlanSave.style.display='none';					
				}

			ContPlanGrid.lock();
			fm.RiskPlan1.value="1";
			fm.RiskPlan1Name.value="保险套餐";
			fm.RiskPlan.value=arrQueryResult[0][1];
			fm.InsuMult.value = arrQueryResult[0][2];
			fm.RiskPlanName.value=cContPlanName;
    }
   else
   	{
			fm.RiskPlan1.value="0";
			fm.RiskPlan1Name.value="非固定计划";  
			fm.InsuMult.value = arrQueryResult[0][2];
			if (LoadFlag==4||LoadFlag==16||LoadFlag==11||LoadFlag==18)//与init中初始化保持一致
			{			 		
   	     divRiskPlanPSave.style.display='none';
				 divRiskPlanSave.style.display='none';
		  }else
		  	{
   	     divRiskPlanPSave.style.display='none';
				 divRiskPlanSave.style.display='';		  		
		  	}
			divcontplanname.style.display = 'none';
			divcontplan.style.display = 'none';
   	}

		//查询该险种下的险种计算要素
		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,d.CalFactorValue,d.Remark,b.RiskVer,d.GrpPolNo,d.MainRiskCode,d.CalFactorType,c.CalMode "
			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCContPlanDutyParam d "
			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
			+ "and a.DutyCode = d.DutyCode and a.CalFactor = d.CalFactor and b.RiskVer = d.RiskVersion "
			+ "and ContPlanCode = '"+fm.all('ContPlanCode').value+"'"
			+ "and GrpContNO = '"+GrpContNo+"' order by a.RiskCode,d.MainRiskCode,a.DutyCode,a.factororder";
		//fm.all('PlanSql').value = strSQL;
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
		//原则上不会失败，嘿嘿
		if (!turnPage.strQueryResult) {
			alert("查询失败！");
			return false;
		}
		//查询成功则拆分字符串，返回二维数组
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
		turnPage.pageDisplayGrid = ContPlanGrid;
		//保存SQL语句
		turnPage.strQuerySql = strSQL;
		//设置查询起始位置
		turnPage.pageIndex = 0;
		//在查询结果数组中取出符合页面显示大小设置的数组
		//调用MULTILINE对象显示查询结果
		displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
		QueryCount = 1;
		tSearch = 1;
	}
}

//选择保险套餐
function showPlan(){
	var arrResult  = new Array();
	strSQL = "select a.ContPlanCode,a.ContPlanName,a.PlanSql,a.Peoples3,b.RiskCode,b.MainRiskCode from LDPlan a,LDPlanRisk b where a.ContPlanCode = b.ContPlanCode and a.ContPlanCode='"+fm.all("RiskPlan").value+"'";

	var cGrpContNo = fm.all('GrpContNo').value;

	arrResult =  decodeEasyQueryResult(easyQueryVer3(strSQL, 1, 0, 1));
	if(arrResult==null){
		alert("查询保险套餐数据失败！");
	}else{
		fm.all('ContPlanCode').value = arrResult[0][0];
		fm.all('ContPlanName').value = arrResult[0][1];
		fm.all('PlanSql').value      = arrResult[0][2];
		fm.all('Peoples3').value     = arrResult[0][3];
		fm.all('RiskCode').value     = arrResult[0][4];
		fm.all('MainRiskCode').value = arrResult[0][5];
	}
	//查询该险种下的险种计算要素
	//strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,d.CalFactorValue,d.Remark,b.RiskVer,e.GrpPolNo,d.MainRiskCode,d.CalFactorType,c.CalMode "
	//	+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LDPlanDutyParam d,LCGrpPol e "
	//	+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
	//	+ "and a.DutyCode = d.DutyCode and a.CalFactor = d.CalFactor and b.RiskVer = d.RiskVersion "
	//	+ "and ContPlanCode = '"+fm.all('RiskPlan').value+"' "
	//	+ "and  e.RiskCode = d.RiskCode  order by a.RiskCode,d.MainRiskCode,a.DutyCode";
	
	strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,d.CalFactorValue,d.Remark,b.RiskVer,'',d.MainRiskCode,d.CalFactorType,c.CalMode "
			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LDPlanDutyParam d "
			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
			+ "and a.DutyCode = d.DutyCode and a.CalFactor = d.CalFactor and b.RiskVer = d.RiskVersion "
			+ "and ContPlanCode = '"+fm.all('RiskPlan').value+"'"
			+ "order by a.RiskCode,d.MainRiskCode,a.DutyCode";

	//fm.all('PlanSql').value = strSQL;
	//alert(strSQL);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//原则上不会失败
		
	if (!turnPage.strQueryResult) {
		alert("查询失败！");
		return false;
	}
		
	//查询成功则拆分字符串，返回二维数组
		
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);	
	//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
	turnPage.pageDisplayGrid = ContPlanGrid;
	//保存SQL语句
	turnPage.strQuerySql = strSQL;
	//设置查询起始位置
	turnPage.pageIndex = 0;
	//在查询结果数组中取出符合页面显示大小设置的数组
	//调用MULTILINE对象显示查询结果
	displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
	QueryCount = 1;
	tSearch = 1;
}

//计划变更则修改查询状态变量
function ChangePlan(){
	//alert(fm.RiskPlan1.value);
	var ch=fm.RiskPlan1.value;
	if(ch == 0)
	{
	  QueryCount = 0;
    //initContPlanDutyGrid();
    //initContPlanGrid();
  }
}
function initPlanKind2(cObjCode)
{
	var tPlanKind1=fm.all('PlanKind1').value;
	//alert ("PlanKind1:"+tPlanKind1);
	if(tPlanKind1!='')
	{
	mPlnaKind2="1 and plankind1=#"+tPlanKind1+"#"; 
  }	
  else if(tPlanKind1=='')
  {
  mPlnaKind2="1"; 
  }  
  showCodeList('PlanKind2',[cObjCode],[0], null, mPlnaKind2, "1"); 
}
function initPlanKind3(cObjCode)
{
	var tPlanKind1=fm.all('PlanKind1').value;
	var tPlanKind2=fm.all('PlanKind2').value;
	//alert ("PlanKind1:"+tPlanKind1);
	//alert ("PlanKind2:"+tPlanKind2);
	if(tPlanKind1!=''&&tPlanKind2!='')
	{
	mPlnaKind3="1 and plankind1=#"+tPlanKind1+"# and plankind2=#"+tPlanKind2+"#"; 
  }	
  else if(tPlanKind2==''&&tPlanKind1!='')
  {
  mPlnaKind3="1 and plankind1=#"+tPlanKind1+"#"; 
  }
  else if(tPlanKind2!=''&&tPlanKind1=='')
  {
  mPlnaKind3="1 and plankind2=#"+tPlanKind2+"#"; 
  }  
  else if(tPlanKind2==''&&tPlanKind1=='')
  {
  mPlnaKind3="1"; 
  }   
  showCodeList('PlanKind3',[cObjCode],[0], null, mPlnaKind3, "1"); 
}
function initRiskPlan(cObjCode,cObjName)
{
	var tPlanKind1=fm.all('PlanKind1').value;
	var tPlanKind2=fm.all('PlanKind2').value;
	var tPlanKind3=fm.all('PlanKind3').value;
	mRiskPlan="1"
	if(tPlanKind1!='')
	{
		mRiskPlan=mRiskPlan+" and plankind1=#"+tPlanKind1+"#";
	}
  if(tPlanKind2!='')
  {
  	mRiskPlan=mRiskPlan+" and plankind2=#"+tPlanKind2+"#";
  }
  if(tPlanKind3!='')
  {
  	mRiskPlan=mRiskPlan+" and plankind3=#"+tPlanKind3+"#";
  }
	showCodeList('RiskPlan',[cObjCode,cObjName],[0,1], null, mRiskPlan, "1");    
}
function SaveRiskSubmit()
{
		if(fm.all('RiskPlan1').value=="1")
	{
		//alert("riskplan:"+fm.all('RiskPlan1').value);
  fm.all('mOperate').value="INSERT||GROUPRISK";
  
  //var showStr="正在添加团单险种信息，请您稍候并且不要修改屏幕上的值或链接其他页面";
	//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  
  fm.action="../cardgrp/GroupPlanRiskSave.jsp";
  fm.submit();
  return true;
  //alert("返回结果："+fm.all("mFlagStr").value);
  //if(fm.all("mFlagStr").value=="Succ")
  //{
  //return true;
  //}
  //else
  //	{
  //	alert("险种保存失败！");
  //	return true;	
  //	}
  }
else
	{
		return true;	
	}


}