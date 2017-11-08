//***********************************************
//程序名称：AmntAccumulate.js
//程序功能：保额累计
//创建日期：2005-06-01 11:10:36
//创建人  ：HL
//更新记录：  更新人    更新日期     更新原因/内容
//***********************************************

//               该文件中包含客户端需要处理的函数和事件


//全局变量
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();



/*********************************************************************
 *  投保单复核的提交后的操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 
 //提交，保存按钮对应操作
function submitForm()
{
  var tInsuredNo = fm.InsuredNo.value;
  if(tInsuredNo!=""){
      if(checkDate(tContNo,tInsuredNo)==false)
        {
          return false;
        }
      }else
         alert("没有被保险人编码！");
  /*if(specifyDisplay()==false)
     return false;*/
  if(fm.CvalidateConfirm.value==null||fm.CvalidateConfirm.value==""){
	  alert("请录入是否指定生效日期！");
	  return false;
  }
  if(checkAddPrem()==false)
    return false;  
    var i = 0;
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
  
  var tCvalidate = fm.Cvalidate.value ;
 
  if(tCvalidate == "")
    { 
      alert("请输入生效日期");      
      
    }
  fm.action= "./UWChangeCvalidateChk.jsp";
  document.getElementById("fm").submit(); //提交
}


function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
    //parent.close();
  }
  else
  { 
	var showStr="操作成功！";
  	showInfo.close();
  	alert(showStr);
  	initForm();
  	//updateCvalidate();
  	
    //执行下一步操作
  }
}

function updateCvalidate(){
   //此时已经提交成功 保单险种信息部分中的生效日期应从polapplydate 改为 现在 cvalidate
   
}


/*********************************************************************
 *  返回上一页
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function returnParent(){
  top.close();	
	
}



/*********************************************************************
 *  查询已承保保单
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function queryCont(){
  //var aSQL=" select a.contno,a.appntname,a.appntbirthday,a.insuredname,a.insuredbirthday,a.polapplydate,"
  //        +" nvl((select enteraccdate from ljtempfee where tempfeeno = a.tempfeeno),'1900-01-01'), a.insuredno"
  //        +" from lccont a where 1 = 1"
  //        +" and ContNo = '"+tContNo+"'";         
//  var aSQL = " select distinct(a.insuredno),a.contno,a.appntname,b.appntbirthday, a.insuredname,"
//            +" b.insuredbirthday, b.polapplydate,(select makedate from es_doc_main where doccode=b.prtno and subtype='UA001'),(case when (select max(enteraccdate) from ljtempfee where otherno = b.contno) is not null then to_char((select max(enteraccdate) from ljtempfee where otherno = b.contno),'yyyy-mm-dd') else '' end),b.cvalidate"
//            +"  from lcpol a,lccont b"
//            +" where a.contno = b.contno and b.contno ='"+tContNo+"'"
            
            var sqlid1="UWChangeCvalidateSql0";
        	var mySql1=new SqlClass();
        	mySql1.setResourceName("uw.UWChangeCvalidateSql"); //指定使用的properties文件名
        	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
        	mySql1.addSubPara(tContNo);//指定传入的参数
        	strSql=mySql1.getString();
        	
  turnPage.queryModal(strSql, ContGrid);
  //默认选择一个
  if(ContGrid.canSel==1&&ContGrid.mulLineCount>1)
 {
 			document.all('ContGridSel')[0].checked=true;
 }
 if(ContGrid.canSel==1&&ContGrid.mulLineCount==1)
 {
 			document.all('ContGridSel').checked=true;
 }
}
function specifyvalidate(tContNO,tInsuredNo){
   //判断是否回溯
  var tNowInsuredNo="";
  if(tInsuredNo=="1"){
  tNowInsuredNo = fm.InsuredNo.value;
  }else{
     tNowInsuredNo = tInsuredNo;
  }
  // var tSQL = "select distinct(specifyvalidate) from lcpol where contno ='"+tContNO+"' and insuredno = '"+tNowInsuredNo+"'";
   
    var sqlid2="UWChangeCvalidateSql1";
	var mySql2=new SqlClass();
	mySql2.setResourceName("uw.UWChangeCvalidateSql"); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(tContNO);//指定传入的参数
	mySql2.addSubPara(tNowInsuredNo);//指定传入的参数
	strSql=mySql2.getString();
	
   var tSpecifyvalidate = easyExecSql(strSql);
   if(tSpecifyvalidate!=""){
   fm.Specifyvalidate.value =tSpecifyvalidate;
   fm.CvalidateConfirm.value = tSpecifyvalidate;
   if(tSpecifyvalidate =="Y"){
        fm.Flag.value ="2"
      	document.all('Button1').disabled='';
      	//divChangeResult.style.display= "";
      	//var tLCCSpecSQL ="select a.spectype,a.speccode,a.speccontent,a.specreason,a.serialno from lccspec a where contno  ='"+tContNo+"' and speccode = 'hs001'";
      	
      	var sqlid3="UWChangeCvalidateSql2";
     	var mySql3=new SqlClass();
     	mySql3.setResourceName("uw.UWChangeCvalidateSql"); //指定使用的properties文件名
     	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
     	mySql3.addSubPara(tContNo);//指定传入的参数
     	strSql=mySql3.getString();
     	
      	var arrResult1 = easyExecSql(strSql);
      	if(arrResult1!=null){
      	fm.SpecType.value = arrResult1[0][0];
     	fm.CvalidateIdea.value =arrResult1[0][2];
     	fm.SpecCode.value = arrResult1[0][1];
     	fm.SpecReason.value = arrResult1[0][3];
      	fm.SerialNo.value = arrResult1[0][4];
      	fm.Operate.value="UPDATE";//LCCSpec表中已经又一条合同特约
      	}else{
      	fm.SpecType.value = "";
     	fm.CvalidateIdea.value ="";
     	fm.SpecCode.value = "";
     	fm.SpecReason.value = "";
      	fm.SerialNo.value = "";
      	}
  	 }else{
     	 document.all('Button1').disabled='true';
     	 divChangeResult.style.display= "none";
     	 
     	 //var tLCCSpecTempletSQL = "select templetcode,specreason,speccontent,temptype from lccspectemplet where templetcode = 'hs001'";
     	 
     	var sqlid4="UWChangeCvalidateSql3";
     	var mySql4=new SqlClass();
     	mySql4.setResourceName("uw.UWChangeCvalidateSql"); //指定使用的properties文件名
     	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
     	strSql=mySql4.getString();
     	
     	 var arrResult = easyExecSql(strSql);
     	 if(arrResult!=null){
     	 fm.SpecType.value = arrResult[0][3];
     	 fm.CvalidateIdea.value =arrResult[0][2];
     	 fm.SpecCode.value = arrResult[0][0];
     	 fm.SpecReason.value = arrResult[0][1];
     	 //fm.SerialNo.value = arrReault[0][5];
     	 }
     	 //m.CvalidateIdea.value =fm.Date.value;
  	 }
   }
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
     //divChangeResult.style.display= "";  
}

function querySpecInput(tContNo){
	//初始化时判断是否录入特约
   if(((fm.Flag.value=="1")&&(fm.Specifyvalidate.value="N"))
         ||((fm.Specifyvalidate.value="N")&&(fm.CvalidateIdea.value==""||fm.CvalidateIdea.value==null))){
        //var tquery = "select 1 from es_doc_relation where bussno='"+tContNo+"' and subtype = 'TB2'";
        
    	var sqlid5="UWChangeCvalidateSql4";
     	var mySql5=new SqlClass();
     	mySql5.setResourceName("uw.UWChangeCvalidateSql"); //指定使用的properties文件名
     	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
     	mySql5.addSubPara(tContNo);
     	strSql=mySql5.getString();
        
	    var arrResult1 = easyExecSql(strSql);
	    if(arrResult1!=null){	     	 	
	       fm.Flag.value="2";
	       document.all('Button1').disabled='';
	      divChangeResult.style.display= "";
	      fm.Operate.value = "INSERT";
	     // var tLCCSpecTempletSQL = "select templetcode,specreason,speccontent,temptype from lccspectemplet where templetcode = 'hs001'";

	      var sqlid6="UWChangeCvalidateSql5";
	     	var mySql6=new SqlClass();
	     	mySql6.setResourceName("uw.UWChangeCvalidateSql"); //指定使用的properties文件名
	     	mySql6.setSqlId(sqlid6);//指定使用的Sql的id
	     	strSql=mySql6.getString();
	     	
	      var arrResult = easyExecSql(strSql);
	     	 if(arrResult!=null){
	     	 fm.SpecType.value = arrResult[0][3];
	     	 fm.CvalidateIdea.value =arrResult[0][2];
	     	 fm.SpecCode.value = arrResult[0][0];
	     	 fm.SpecReason.value = arrResult[0][1];
	     	 //fm.SerialNo.value = arrReault[0][5];
	     	 }
	    }else{
	       //不录入特约  BL中不会向LCCSpec表中添加数据 控件不提交
	       fm.Operate.value = "INSERT&&NOSPEC";
	    }
   } 	
}

function checkAddPrem()
{	
	var tContNo = fm.ContNo.value;
	var tSelNo = ContGrid.getSelNo()-1;
	var tInsuredNo = ContGrid.getRowColData(tSelNo,1);
  	var tInsuredBirthday = ContGrid.getRowColData(tSelNo,6);
  	var tAppntBirthday = ContGrid.getRowColData(tSelNo,4);
	var tInsuredAge=calAgeNew(tInsuredBirthday,document.all("Cvalidate").value);
	//var tSQL1 = "select insuredappage from lcpol where ContNo='"+tContNo+"' and insuredno = '"+tInsuredNo+"'";
	
    var sqlid7="UWChangeCvalidateSql6";
   	var mySql7=new SqlClass();
   	mySql7.setResourceName("uw.UWChangeCvalidateSql"); //指定使用的properties文件名
   	mySql7.setSqlId(sqlid7);//指定使用的Sql的id
   	mySql7.addSubPara(tContNo);//指定传入的参数
	mySql7.addSubPara(tInsuredNo);//指定传入的参数
   	strSql=mySql7.getString();
   	
    var arrResult1 = easyExecSql(strSql);
    var tInsuredAge1 ='';
    if(arrResult1!=null)
    {
    	tInsuredAge1=arrResult1[0];
    }	
	//var tSQL = "select 1 from lcprem where polno in (select polno from lcpol where ContNo='"+tContNo+"' and insuredno = '"+tInsuredNo+"') and payplancode like '000000%'";
	//prompt('',tSQL);
	//alert(tInsuredAge);
	//alert(tInsuredAge1);
	
    var sqlid8="UWChangeCvalidateSql7";
   	var mySql8=new SqlClass();
   	mySql8.setResourceName("uw.UWChangeCvalidateSql"); //指定使用的properties文件名
   	mySql8.setSqlId(sqlid8);//指定使用的Sql的id
   	mySql8.addSubPara(tContNo);//指定传入的参数
	mySql8.addSubPara(tInsuredNo);//指定传入的参数
   	strSql=mySql8.getString();
   	
    var arrResult = easyExecSql(strSql);
    if(arrResult!=null && tInsuredAge!=tInsuredAge1){
     	 if (confirm("被保人年龄发生变化且有加费信息，是否继续？") == true)
    	 	return true;
    	 else
    	 	return false;
    }
    //var tSQL2 = "select cvalidate from lcpol where ContNo='"+tContNo+"' and insuredno = '"+tInsuredNo+"' and riskcode='121301'";
    
    var sqlid9="UWChangeCvalidateSql8";
   	var mySql9=new SqlClass();
   	mySql9.setResourceName("uw.UWChangeCvalidateSql"); //指定使用的properties文件名
   	mySql9.setSqlId(sqlid9);//指定使用的Sql的id
   	mySql9.addSubPara(tContNo);//指定传入的参数
	mySql9.addSubPara(tInsuredNo);//指定传入的参数
   	strSql=mySql9.getString();
   	
    var arrResult2 = easyExecSql(strSql);
    if(arrResult2!=null){
    	 var oldCvalidate = arrResult2[0]+"";
   		 var tAppntAgeOld=calAgeNew(tAppntBirthday,oldCvalidate);
   		 //alert(tAppntAgeOld);
    	 var tAppntAgeNew=calAgeNew(tAppntBirthday,document.all("Cvalidate").value);
    	 //alert(tAppntAgeNew);
    	 if(tAppntAgeOld!=tAppntAgeNew)
    	 {
    	 	if (confirm("投保人年龄发生变化且有豁免险121301，是否继续？") == true)
	    	 	return true;
	    	 else
	    	 	return false;
    	 }     	 
    }
    return true;
}

function specifyDisplay(){
   //确定后判断是否录入特约
   if(((fm.Flag.value=="1")&&(fm.Specifyvalidate.value="N"))
         ||((fm.Specifyvalidate.value="N")&&(fm.CvalidateIdea.value==""||fm.CvalidateIdea.value==null))){
   if (confirm("是否录入生效日回溯特约？") == true)
    {
       fm.Flag.value="2";
       document.all('Button1').disabled='';
      divChangeResult.style.display= "";
      fm.Operate.value = "INSERT";
      //var tLCCSpecTempletSQL = "select templetcode,specreason,speccontent,temptype from lccspectemplet where templetcode = 'hs001'";
      
      var sqlid10="UWChangeCvalidateSql9";
     	var mySql10=new SqlClass();
     	mySql10.setResourceName("uw.UWChangeCvalidateSql"); //指定使用的properties文件名
     	mySql10.setSqlId(sqlid10);//指定使用的Sql的id
     	strSql=mySql10.getString();
     	
     	 var arrResult = easyExecSql(strSql);
     	 if(arrResult!=null){
     	 fm.SpecType.value = arrResult[0][3];
     	 fm.CvalidateIdea.value =arrResult[0][2];
     	 fm.SpecCode.value = arrResult[0][0];
     	 fm.SpecReason.value = arrResult[0][1];
     	 //fm.SerialNo.value = arrReault[0][5];
     	 }
      	return false;
    }else{
       //不录入特约  BL中不会向LCCSpec表中添加数据 控件不提交
       fm.Operate.value = "INSERT&&NOSPEC";
    }
   }
   return true;
}


function queryPersonInfo(tContNo,tInsuredNo){
var tNowInsuredNo="";
if(tInsuredNo=="1"){
  tNowInsuredNo = fm.InsuredNo.value;
  }else{
     tNowInsuredNo = tInsuredNo;
  }
  //var aSQL = "select a.ContNo,a.Cvalidate,to_date(concat(concat(substr(a.cvalidate,1,4),'-'),substr(to_char(subdate(a.insuredbirthday,1),'yyyy-mm-dd'),6,5)),'yyyy-mm-dd'),(select proposalcontno from lccont where contno = a.contno) from lcpol a where a.ContNo='"+tContNo+"' and insuredno = '"+tNowInsuredNo+"'";	
  
  var sqlid11="UWChangeCvalidateSql10";
	var mySql11=new SqlClass();
	mySql11.setResourceName("uw.UWChangeCvalidateSql"); //指定使用的properties文件名
	mySql11.setSqlId(sqlid11);//指定使用的Sql的id
	mySql11.addSubPara(tContNo);//指定传入的参数
	mySql11.addSubPara(tNowInsuredNo);//指定传入的参数
	strSql=mySql11.getString();
	
  var arrResult = easyExecSql(strSql);
  //2010-03-08  考虑到如果被保人出生日期为闰年3月1日时上面的SQL会抛出异常 
  //增加一步查询如果上面的SQL没有查出数据则将被保人的出生日期-2
  if(arrResult==null){
	  //aSQL = "select a.ContNo,a.Cvalidate,to_date(concat(concat(substr(a.cvalidate,1,4),'-'),substr(to_char(subdate(a.insuredbirthday,2),'yyyy-mm-dd'),6,5)),'yyyy-mm-dd'),(select proposalcontno from lccont where contno = a.contno) from lcpol a where a.ContNo='"+tContNo+"' and insuredno = '"+tNowInsuredNo+"'";
	  
	  var sqlid12="UWChangeCvalidateSql11";
		var mySql12=new SqlClass();
		mySql12.setResourceName("uw.UWChangeCvalidateSql"); //指定使用的properties文件名
		mySql12.setSqlId(sqlid12);//指定使用的Sql的id
		mySql12.addSubPara(tContNo);//指定传入的参数
		mySql12.addSubPara(tNowInsuredNo);//指定传入的参数
		strSql=mySql12.getString();
		
	  arrResult = easyExecSql(strSql);
	  if(arrResult==null){
		  alert("计算新的生效日期时出错！");
		  return false;
	  }
  }
  document.all('ContNo').value = arrResult[0][0];
  document.all('CvalidateHide').value = arrResult[0][2];
  document.all('ProposalContNo').value = arrResult[0][3];
  if(fm.Specifyvalidate.value =="N"){
     document.all('Cvalidate').value = arrResult[0][2];
     }else
     document.all('Cvalidate').value ="";
}

function checkDate(tContNo,tInsuredNo){
  //校验指定生效日 规则：默认为被保险人生日前一天（月/日），年份为当年；可进一步修改，回溯指定不能超过61天，向后指定不能超过27天；
  var tNowInsuredNo="";
  if(tInsuredNo=="1"){
  tNowInsuredNo = fm.InsuredNo.value;
  }else{
     tNowInsuredNo = tInsuredNo;
     
  }
   var tCvalidate = fm.Cvalidate.value;
   if(tCvalidate==""||tCvalidate==null)
   {
      alert("没有录入指定生效日期！");
      return false;
   }
   var tCvalidateSql = "";
   if(_DBT==_DBO){
	  // tCvalidateSql = "SELECT to_char(subdate(polapplydate,61),'yyyy-mm-dd'),(case when (select to_char(adddate(max(makedate),30),'yyyy-mm-dd') from lccuwsub where contno=lcpol.contno and passflag in ('9','4','1','2','a')) is not null then (select to_char(adddate(max(makedate),30),'yyyy-mm-dd') from lccuwsub where contno=lcpol.contno and passflag in ('9','4','1','2','a')) else to_char(adddate(now(),30),'yyyy-mm-dd') end),polapplydate FROM lcpol where contno='"+tContNo+"' and insuredno = '"+tNowInsuredNo+"' and rownum = 1 ";
	   
	   var sqlid13="UWChangeCvalidateSql12";
		var mySql13=new SqlClass();
		mySql13.setResourceName("uw.UWChangeCvalidateSql"); //指定使用的properties文件名
		mySql13.setSqlId(sqlid13);//指定使用的Sql的id
		mySql13.addSubPara(tContNo);//指定传入的参数
		mySql13.addSubPara(tNowInsuredNo);//指定传入的参数
		strSql=mySql13.getString();
		
   }else if(_DBT==_DBM){
	   //tCvalidateSql = "SELECT to_char(subdate(polapplydate,61),'yyyy-mm-dd'),(case when (select to_char(adddate(max(makedate),30),'yyyy-mm-dd') from lccuwsub where contno=lcpol.contno and passflag in ('9','4','1','2','a')) is not null then (select to_char(adddate(max(makedate),30),'yyyy-mm-dd') from lccuwsub where contno=lcpol.contno and passflag in ('9','4','1','2','a')) else to_char(adddate(now(),30),'yyyy-mm-dd') end),polapplydate FROM lcpol where contno='"+tContNo+"' and insuredno = '"+tNowInsuredNo+"' limit 1 ";
	   
	   var sqlid14="UWChangeCvalidateSql13";
		var mySql14=new SqlClass();
		mySql14.setResourceName("uw.UWChangeCvalidateSql"); //指定使用的properties文件名
		mySql14.setSqlId(sqlid14);//指定使用的Sql的id
		mySql14.addSubPara(tContNo);//指定传入的参数
		mySql14.addSubPara(tNowInsuredNo);//指定传入的参数
		strSql=mySql14.getString();
		
   }
   var arrResult = easyExecSql(strSql);
   var tStartDate = arrResult[0][0];
   var tEndDate = arrResult[0][1];
   fm.PolApplyDate.value =arrResult[0][2];
   //alert("判断指定的生效日期"+tCvalidate+"是否符合要求！回溯向前指定不能超过61天，向后指定不能超过27天!即是否在"+tStartDate+"和"+tEndDate+"之间！");
   if((tCvalidate>tStartDate)&&(tCvalidate<tEndDate)){}else{
      //alert("指定的生效日期"+tCvalidate+"不符合要求！在原来生效时间"+fm.PolApplyDate.value+"的基础上，回溯向前指定不能超过61天，向后指定不能超过27天!即应该在"+tStartDate+"和"+tEndDate+"之间！");
      alert("指定的生效日期"+tCvalidate+"不符合要求！应该在"+tStartDate+"和"+tEndDate+"之间！");
      fm.Cvalidate.value="";
      return false;
   }
   return true;
}

function getContDetail(){
	
	
}

function initPolInfo(){
   //初始化为一体被保险人信息
   if(ContGrid.mulLineCount>0){
   var tFirstInsuredNo = ContGrid.getRowColData(0,1);
//   var tPolInfoSQL =" select a.polno,a.riskcode,(select riskname from lmrisk where riskcode =a.riskcode),"
//            +" a.amnt,a.mult,a.prem,"
//            +" a.cvalidate,a.payyears, a.years,a.cvalidate from lcpol a where 1=1"
//            +" and a.ContNo = '"+tContNo+"' and a.insuredno ='"+tFirstInsuredNo+"' ";
   
    var sqlid15="UWChangeCvalidateSql14";
	var mySql15=new SqlClass();
	mySql15.setResourceName("uw.UWChangeCvalidateSql"); //指定使用的properties文件名
	mySql15.setSqlId(sqlid15);//指定使用的Sql的id
	mySql15.addSubPara(tContNo);//指定传入的参数
	mySql15.addSubPara(tFirstInsuredNo);//指定传入的参数
	strSql=mySql15.getString();
	
    turnPage.queryModal(strSql,PolGrid);
   fm.InsuredNo.value = tFirstInsuredNo;
   }else{
     alert("没有投、被保人信息！");
   }
}

function getPolInfo(){
  var tSelNo = ContGrid.getSelNo()-1;
  var tInsuredNo = ContGrid.getRowColData(tSelNo,1);
//  var aSQL = " select a.polno,a.riskcode,(select riskname from lmrisk where riskcode =a.riskcode),"
//            +"  a.amnt,a.mult,a.prem,"
//            +" a.cvalidate,a.payyears, a.years from lcpol a where 1=1"
//            +" and a.ContNo = '"+tContNo+"' and a.insuredno ='"+tInsuredNo+"' ";
  
  var sqlid16="UWChangeCvalidateSql15";
	var mySql16=new SqlClass();
	mySql16.setResourceName("uw.UWChangeCvalidateSql"); //指定使用的properties文件名
	mySql16.setSqlId(sqlid16);//指定使用的Sql的id
	mySql16.addSubPara(tContNo);//指定传入的参数
	mySql16.addSubPara(tInsuredNo);//指定传入的参数
	strSql=mySql16.getString();
	
  turnPage2.queryModal(strSql, PolGrid);
  fm.InsuredNo.value =tInsuredNo;
  fm.Flag.value ="1"
  //改变默认指定生效日
   specifyvalidate(tContNo,tInsuredNo);
	queryPersonInfo(tContNo,tInsuredNo)
   //checkDate(tContNo,tInsuredNo);
}




//判断是否有财务缴费信息
function haveFeeInfo(){
	return;
}

//判断是否有影像资料
function havePicData(){
	return;
}

//判断是否有核保结论
function haveUWResult(){
	//var aSQL = "select * from LCCUWMaster where contno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"'";
  //alert(aSQL);
	
	  var sqlid17="UWChangeCvalidateSql16";
		var mySql17=new SqlClass();
		mySql17.setResourceName("uw.UWChangeCvalidateSql"); //指定使用的properties文件名
		mySql17.setSqlId(sqlid17);//指定使用的Sql的id
		mySql17.addPara(ContGrid.getRowColData(ContGrid.getSelNo()-1,1));//指定传入的参数
		strSql=mySql17.getString();
		
  var arrResult = easyExecSql(strSql);
  if(arrResult!=null){
    fm.button4.disabled="";	
    return;
  }
  else{
    fm.button4.disabled="true";	
    return;
  }
	return;
}

function getContDetailInfo(){
	 window.open("../app/ProposalEasyScan.jsp?LoadFlag=6&prtNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"&ContNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1), "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");  	  
}

function showTempFee()
{
	window.open("./UWTempFeeQryMain.jsp?PrtNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1),"window11");
}

