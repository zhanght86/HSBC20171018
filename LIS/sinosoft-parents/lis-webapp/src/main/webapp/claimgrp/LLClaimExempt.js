var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//【清除页面上表单数据函数】
function ClearPagedata()
{
	fm.UrgePayFlag.value="";//
	fm.NeedAcc.value="";//
	fm.PayTimes.value="";//
	fm.Rate.value="";//
	fm.PayStartDate.value="";//
	fm.PayEndDate.value="";//
	fm.PaytoDate.value="";//
	fm.PayIntv.value="";//
	fm.StandPrem.value="";//
	fm.Prem.value="";//
	fm.SumPrem.value="";//
	fm.SuppRiskScore.value="";//
	fm.FreeFlag.value="";//
	fm.FreeRate.value="";//
	fm.FreeStartDate.value="";
	fm.FreeEndDate.value="";//
	fm.State.value="";//
	fm.FreeFlagName.value="";//
	fm.FreeRateName.value="";//
	fm.StateName.value="";//
	fm.UrgePayFlagName.value="";//
	fm.PayIntvName.value="";//
	fm.ExemptReason.value="";
	fm.ExemptReasonname.value="";
	fm.ExemptDesc.value="";//
	
}

//【豁免信息表格查询函数】---在表格显示该赔案的详细豁免信息记录
function LLClaimExemptGridQuery()
{
	/*var strSQL="select ClmNo,GrpContNo,ContNo,PolNo,DutyCode,PayPlanCode,PayPlanType,AppntType,AppntNo from llexempt"
		+" where 1=1 and clmno='"+fm.ClmNo.value+"'";*/
	mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimExemptInputSql");
mySql.setSqlId("LLClaimExemptSql1");
mySql.addSubPara(fm.ClmNo.value ); 
	turnPage.queryModal(mySql.getString(),LLClaimExemptGrid);	
	var row=LLClaimExemptGrid.mulLineCount; //行数
//	alert(row);
	if(row>0)
	{
		fm.exemptquery.disabled =true;//[提取豁免信息]按钮
	}
//    var arr = easyExecSql(strSQL);
//    if (arr == "" || arr == null) 
//    {
//       alert("该赔案没有豁免信息");
//       return;
//    }
//    else
//    {
//		displayMultiline(arr,LLClaimExemptGrid);
//    }			
}

//【查询选中记录的详细豁免信息】---被LLClaimExemptGridClick()调用
function LLClaimExemptInfoQuery()
{
	/*var strSQL="select clmno,grpcontno,contno,polno,dutycode,payplancode,payplantype,appnttype,appntno"
		//---------------1------2--------3------4-------5---------6---------7-----------8--------9---------
		+",urgepayflag,needacc,paytimes,rate,paystartdate,payenddate,paytodate,payintv"
		//----10--------11------12-----13--------14---------15---------16--------17---------------------------------------		
		+",standprem,prem,sumprem,suppriskscore,freeflag,freerate,freestartdate,freeenddate,state,exemptreason,exemptdesc,ExemptPeriod"
		//---18------19---20-------21-----------22----------23--------24-----------25--------26----------------------------
		//+",managecom,operator,makedate,maketime,modifydate,modifytime,addfeedirect"
		//-----27-----28--------29-----30-------31----------32--------33-----------
		+" from llexempt where 1=1 "
		+" and clmno='"+fm.ClmNo.value+"' "
		+" and polno='"+fm.PolNo.value+"' " 
		+" and dutycode='"+fm.DutyCode.value+"' "
		+" and payplancode='"+fm.PayPlanCode.value+"'";
//		+" order by clmno,polno";	*/
	mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimExemptInputSql");
mySql.setSqlId("LLClaimExemptSql2");
mySql.addSubPara(fm.ClmNo.value ); 
mySql.addSubPara(fm.PolNo.value ); 	
mySql.addSubPara(fm.DutyCode.value ); 	
mySql.addSubPara(fm.PayPlanCode.value ); 		
	var arr = easyExecSql(mySql.getString());
    if (arr == "" || arr == null) 
    {
       alert("查询得信息为空!");
       return;
    }
    else
    {
    	ClearPagedata();//清除数据
		fm.UrgePayFlag.value=arr[0][9];//
		fm.NeedAcc.value=arr[0][10];//
		fm.PayTimes.value=arr[0][11];//
		fm.Rate.value=arr[0][12];//
		fm.PayStartDate.value=arr[0][13];//
		fm.PayEndDate.value=arr[0][14];//
		fm.PaytoDate.value=arr[0][15];//
		fm.PayIntv.value=arr[0][16];//
		fm.StandPrem.value=arr[0][17];//
		fm.Prem.value=arr[0][18];//
		fm.SumPrem.value=arr[0][19];//
		fm.SuppRiskScore.value=arr[0][20];//
		fm.FreeFlag.value=arr[0][21];//
		fm.FreeRate.value=arr[0][22];//
		fm.FreeStartDate.value=arr[0][23];//
		fm.FreeEndDate.value=arr[0][24];//
		fm.State.value=arr[0][25];//
		fm.ExemptReason.value=arr[0][26];//
		fm.ExemptDesc.value=arr[0][27];//
        fm.ExemptPeriod.value=arr[0][28];//
        
        var tExemptPeriod = fm.ExemptPeriod.value;
        var tPrem = fm.Prem.value;
        fm.ExemptSumAmnnt.value=tExemptPeriod * tPrem;
                
		showOneCodeName('FreeFlag','FreeFlag','FreeFlagName');
		showOneCodeName('FreeRate','FreeRate','FreeRateName');    
		showOneCodeName('State','State','StateName');	 
		showOneCodeName('UrgePayFlag','UrgePayFlag','UrgePayFlagName');    
		showOneCodeName('PayIntv','PayIntv','PayIntvName');	 	
		showOneCodeName('llexemptreason','ExemptReason','ExemptReasonname');	
    }			
}

//【免交标志响应函数】---如果免交标志选为0不免交，则免交比率, 免交起期, 免交止期不显示；如果为1免交，免交比率, 免交起期, 免交止期需要显示
function FreeFlagClick()
{
	if(fm.FreeFlag.value=="1")
	{
		divLLClaimExemptFreeInfo.style.display="";
	}
	else
	{
		divLLClaimExemptFreeInfo.style.display="none";
		fm.FreeRate.value="";//
		fm.FreeStartDate.value="";
		fm.FreeEndDate.value="";//
		fm.FreeRateName.value="";//
		fm.ExemptReason.value="";//
		fm.ExemptReasonname.value="";//
		fm.ExemptDesc.value="";//
	}
}

//【豁免信息表格单选钮响应函数】---显示选中记录的详细豁免信息
function LLClaimExemptGridClick()
{
//	alert("开发中！");
//	return;
	var selno = LLClaimExemptGrid.getSelNo()-1;
//	fm.ClmNo.value=LLClaimExemptGrid.getRowColData(selno,1);//<!--赔案号-->  
	fm.PolNo.value=LLClaimExemptGrid.getRowColData(selno,4);//<!--保单险种号码-->  
	fm.DutyCode.value=LLClaimExemptGrid.getRowColData(selno,5);//<!--责任编码-->  
	fm.PayPlanCode.value=LLClaimExemptGrid.getRowColData(selno,6);//<!--交费计划编码-->  
	LLClaimExemptInfoQuery();//【查询选中记录的详细豁免信息】
	FreeFlagClick();//【免交标志响应函数】
    

    divLLClaimExempt.style.display="";
}

//【豁免信息按钮】----提取豁免信息
function LLExemptQueryClick()
{
	fm.exemptquery.disabled =true;
	fm.fmtransact.value="LLExempt||Get";//获得豁免记录
	fm.action="LLClaimExemptSave.jsp";
	submitForm();
}

//【保存数据检测】---保存豁免信息的修改提交前数据检测
function BeforeSaveCheck()  
{
//	fm.PayIntv.value="0";
	if(fm.PayIntv.value=="0") // 0  －－ 趸缴
	{
		alert("交费间隔为趸缴，不能进行豁免操作！");
		return false;
	}
	if(fm.FreeFlag.value=="1")
	{
		if(fm.FreeStartDate.value=="" ||fm.FreeEndDate.value=="")
		{
			alert("免交比率，免交起期或免交止期不能为空！");
			return false;
		}
		if(dateDiff(fm.FreeEndDate.value,fm.FreeStartDate.value,'D') > 0)
	    {
	    	alert("免交起期不能晚于免交止期！");
	    	return false;
	    }
	    if(fm.ExemptReason.value=="" ||fm.ExemptDesc.value=="")
		{
			alert("豁免原因、豁免描述不能为空！");
			return false;
		}
	}
}

//【保存按钮】---保存豁免信息的修改
function LLExemptSaveClick()  
{
	var selno = LLClaimExemptGrid.getSelNo()-1;
	if(selno<0)
	{
		alert("请选择一条豁免记录！");
		return;
	}
	if(BeforeSaveCheck()==false){return;};
	fm.fmtransact.value="LLExempt||Save";//修改保存豁免记录信息
	fm.action="LLClaimExemptSave.jsp";
	submitForm();
}

//【提交数据】-----通过Save页面向后台提交数据
function submitForm()
{
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

//【提交数据后操作,返回】-----返回成功或失败信息（原因）
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


	   	fm.exemptquery.disabled =true;//[提取豁免信息]按钮
	   	ClearPagedata();//【清除页面上表单数据函数】    
	    LLClaimExemptGridQuery();//【豁免信息表格查询函数】
    }
    tSaveFlag ="0";

}
