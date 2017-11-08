//程序名称：UWModifyFloatRate.js
//程序功能：客户品质管理
//创建日期：2008-11-3 11:10:36
//创建人  ：liuqh
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
/*
select max(getdutykind)	from lcget where getdutycode in (select othercode from lmdutyctrl"
                       +" where fieldname = 'GetDutyKind' and dutycode = '"+tDutyCode+"' and InpFlag ='Y')
                       
*/

function initFloatRate(){
//  var tSql = " select (select riskname from lmriskapp where "
//            +" riskcode in (select riskcode from lcpol where polno=a.polno)) a,"
//            +" insuyear,floatrate,"
//            +" case "
//            +" when "
//            +" (select count(*) from lmriskdiscount where riskcode in (select riskcode from lcpol where polno =a.polno) and payintv =a.payintv) ='0' "
//            +" then (case when (select floatrate from lmriskdiscount where payintv!='0' and riskcode in (select riskcode from lcpol where polno=a.polno)) is not null then (select floatrate from lmriskdiscount where payintv!='0' and riskcode in (select riskcode from lcpol where polno=a.polno)) else floatrate end) "
//            +" else (case when (select floatrate from lmriskdiscount where payintv ='0' and riskcode in (select riskcode from lcpol where polno=a.polno)) is not null then (select floatrate from lmriskdiscount where payintv ='0' and riskcode in (select riskcode from lcpol where polno=a.polno)) else floatrate end) end ,polno "
//            + ",(select max(getdutykind) from lcget where contno=a.contno and livegettype='0' "
//            + " and getdutycode in (select othercode from lmdutyctrl "
//            +" where fieldname = 'GetDutyKind' and dutycode = a.dutycode and InpFlag ='Y'))"
//            +" from lcduty a where contno='"+contNo+"' and polno in (select polno from lcpol where insuredno = '"+insuredNo+"') order by a";
  
  var sqlid1="UWModifyFloatRateSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("uw.UWModifyFloatRateSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(contNo);//指定传入的参数
	mySql1.addSubPara(insuredNo);//指定传入的参数
	var tSql=mySql1.getString();
	
  var arrResult = easyExecSql(tSql);
  if(arrResult != null){
  turnPage.queryModal(tSql, RiskFloatRateGrid);     
  }
     
}

function initSpecIdea(){
   //初始化proposalno
   //var tSql ="select proposalno from lcpol where contno ='"++"' and insuredno ='"++"' ";
   //初始化合同下的员工特约信息
        //var tLCCSpecSQL ="select a.spectype,a.speccode,a.speccontent,a.specreason,a.serialno from lccspec a where contno  ='"+contNo+"' and speccode = 'yg001'";
      	
        var sqlid2="UWModifyFloatRateSql2";
    	var mySql2=new SqlClass();
    	mySql2.setResourceName("uw.UWModifyFloatRateSql"); //指定使用的properties文件名
    	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
    	mySql2.addSubPara(contNo);//指定传入的参数
    	var tLCCSpecSQL=mySql2.getString();
    	
        var arrResult1 = easyExecSql(tLCCSpecSQL);
      	if(arrResult1!=null){
      	fm.SpecType.value = arrResult1[0][0];
     	fm.FloatRateIdea.value =arrResult1[0][2];
     	fm.SpecCode.value = arrResult1[0][1];
     	fm.SpecReason.value = arrResult1[0][3];
      	fm.SerialNo.value = arrResult1[0][4];
      	fm.SpecOperate.value="UPDATE";//LCCSpec表中已经又一条合同特约
      	}else{
      	divChangeResult.style.display= "none";
      	fm.Button1.disabled = 'true';
      	fm.SpecType.value = "";
     	fm.FloatRateIdea.value ="";
     	fm.SpecCode.value = "";
     	fm.SpecReason.value = "";
      	fm.SerialNo.value = "";
      	}      	
      	
   //var tSpecSQL="  select speccontent from lccspec where contno='"+contNo+"' and spectype = 'yg' ";
   //var tSpecContent =easyExecSql(tSpecSQL);
   //if(tSpecContent==""||tSpecContent ==null){
   //   fm.Button1.disabled = 'true';
   //}else{
   //fm.FloatRateIdea.value = tSpecContent;
   //}
}

function submitForm(){
     //一些判断     
     if(Speccheck()==false)
       return false;
     if(!checkFLoatRate())
        return false;
        /*
     var tDutyCodeSQL =  "select dutycode from lcget where contno = '"+contNo+"' and rownum ='1'";
     var tDutyCode = easyExecSql(tDutyCodeSQL);
     if(tDutyCode !="")
     {
       var tDutyKindSQL = " select max(getdutykind)	from lcget where getdutycode in (select othercode from lmdutyctrl"
                       +" where fieldname = 'GetDutyKind' and dutycode = '"+tDutyCode+"' and InpFlag ='Y')"
                       +" and contno = '"+contNo+"'";
       var tArr = easyExecSql(tDutyKindSQL);
       if(tArr!=null)
       {
       	 fm.GetDutyKind.value = tArr[0][0];
      }
     }*/
     //alert("fm.GetDutyKind.value:"+fm.GetDutyKind.value);
     //if(fm.GetDutyKind.value ==""||fm.GetDutyKind.value==null)
    //  return false;
//     var tSamePersonSql = "select count(*) from lcpol a where a.insuredno=a.appntno and polno='"+polNo+"'";
     
     var sqlid3="UWModifyFloatRateSql3";
 	var mySql3=new SqlClass();
 	mySql3.setResourceName("uw.UWModifyFloatRateSql"); //指定使用的properties文件名
 	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
 	mySql3.addSubPara(polNo);//指定传入的参数
 	var tSamePersonSql=mySql3.getString();
 	
     var tSameFlag = easyExecSql(tSamePersonSql);
     if(tSameFlag == "0"){
     //不是同一个人
     fm.SamePersonFlag.value = "0";
     }else{
     fm.SamePersonFlag.value = "1";
     }
     fm.ContNo.value = contNo;
     fm.PolNo.value = polNo;
     lockScreen('lkscreen');
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
}

function Speccheck(){
    //判断是否录入特约
    if((fm.FloatRateIdea.value==""||fm.FloatRateIdea.value==null)&&fm.SpecFlag.value=="1"){
      if(confirm("是否录入员工特约？")==true){
        fm.Button1.disabled = '';
       fm.SpecFlag.value = "2";
//       var tSpecTempletSQL = "select a.templetcode,a.temptype,a.specreason,a.speccontent from lccspectemplet a  where a.templetcode='yg001'";
       
       var sqlid4="UWModifyFloatRateSql4";
    	var mySql4=new SqlClass();
    	mySql4.setResourceName("uw.UWModifyFloatRateSql"); //指定使用的properties文件名
    	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
    	var tSpecTempletSQL=mySql4.getString();
    	
       var arrResult = easyExecSql(tSpecTempletSQL);
       if(arrResult!=null){
       fm.SpecCode.value =arrResult[0][0];
       fm.SpecType.value =arrResult[0][1];
       fm.SpecReason.value =arrResult[0][2];
       fm.FloatRateIdea.value =arrResult[0][3];
       fm.SpecOperate.value = "INSERT";
       }else{
           alert("初始化员工特约内容相关信息错误！");
       }
       //fm.all('Button1').disabled=''
       return false;
    }else{
       fm.Button1.disable = "true";
       fm.SpecFlag.value = "1";
       fm.SpecOperate.value = "INSERT&&NOSPEC";
     }
     }
     return true;
}

function specInput(){
   //显示员工特约
   if(fm.DivFlag.value == "1"){
   divChangeResult.style.display = "";
   fm.DivFlag.value = "2";
   }else{
   divChangeResult.style.display = "none";
   fm.DivFlag.value = "1";
   }
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	try {
		if(showInfo!=null)
		{
 	showInfo.close();
}
}
catch(e)
{
	}
  if (FlagStr == "Fail" )
  {         
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
   
    alert(content);
   // parent.close();
  }
  else 
  { 
	var showStr="操作成功！";  	
  	//showInfo.close();
  	alert(showStr);
  //	parent.close();
  	
    //执行下一步操作
  }
  unlockScreen('lkscreen');
  initFloatRate();
  initSpecIdea();
  
}

function checkFLoatRate(){
   var k=0;
   for(i=0;i<RiskFloatRateGrid.mulLineCount;i++)
   {
    if(RiskFloatRateGrid.getChkNo(i))
    {
       var tOldFloatrate = RiskFloatRateGrid.getRowColData(i,3);
       var tNewFloatrate = RiskFloatRateGrid.getRowColData(i,4);
       if(tOldFloatrate == tNewFloatrate)
       {
         alert("第["+(i+1)+"]行的浮动费率与原来的浮动费率相同,此行不会被修改！");
       }
       k++;
     }   
   }
   if(k==0)
   {
      alert("请选择一条记录！");
      return false;
   }
   return true;
}

function returnParent(){
  top.close();
	
}

//tongmeng 2009-05-09 add
//删除员工特约
function deleteSpec()
{
	 fm.SpecOperate.value = "DELETE&&YGSPEC";
	 lockScreen('lkscreen');
	 fm.submit();
}