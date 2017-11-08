 //               该文件中包含客户端需要处理的函数和事件
var mOperate = "";
var showInfo1;
var mDebug="0";
var turnPage = new turnPageClass();
var arrResult;
var sFeatvarures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";
       
//存放添加动作执行的次数
var addAction = 0;
//暂交费总金额
var sumTempFee = 0.0;
//暂交费信息中交费金额累计
var tempFee = 0.0;
//暂交费分类信息中交费金额累计
var tempClassFee = 0.0;
//单击确定后，该变量置为真，单击添加一笔时，检验该值是否为真，为真继续，然后再将该变量置假
var confirmFlag=false;
//
var arrCardRisk;
//window.onfocus=focuswrap;
var mSwitch = parent.VD.gVSwitch;
//alert("!!!:"+mSwitch.getVar( "ContCValiDate" ));
//工作流flag
var mWFlag = 0;
//使得从该窗口弹出的窗口能够聚焦
function focuswrap()
{
	myonfocus(showInfo1);
}

//提交，保存按钮对应操作
function submitForm()
{
 }


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{ 
  try { showInfo.close(); } catch(e) {}
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  if (FlagStr=="Succ" && mWFlag == 0)
  {
  	if(confirm("是否继续录入其他客户？"))
  	{
  		emptyInsured();
  		if (fm.ContType.value==2)
  		{  
  			fm.ContNo.value="";
  			fm.ProposalContNo.value="";
  		}
  	}
  	//initForm();
/**************************************************
  	if(confirm("是否继续录入其他客户？"))
  	{
	       if(fm.InsuredSequencename.value=="第一被保险人资料")
	       {
	       	     //emptyFormElements();
	       	     param="122";
	       	     fm.pagename.value="122";
	       	     fm.SequenceNo.value="2";
                            fm.InsuredSequencename.value="第二被保险人资料";
                            if (scantype== "scan")
                            {
                            setFocus();
                            }
                            noneedhome();
                            return false;
	       }
	       if(fm.InsuredSequencename.value=="第二被保险人资料")
	       {
	       	     //emptyFormElements();
	       	     param="123";
	       	     fm.pagename.value="123";
	       	     fm.SequenceNo.value="3";
                            fm.InsuredSequencename.value="第三被保险人资料";
                            if (scantype== "scan")
                            {
                            setFocus();
                            }
                            noneedhome();
                            return false;
	       }
	       	if(fm.InsuredSequencename.value=="第三被保险人资料")
	       {
	       	     //emptyFormElements();
	       	     param="124";
	       	     fm.pagename.value="124";
	       	     fm.SequenceNo.value="";
                            fm.InsuredSequencename.value="第四被保险人资料";
                            if (scantype== "scan")
                            {
                            setFocus();
                            }
                            return false;
	       }
      }

****************************************************************/

  }


}

function afterSubmity( FlagStr, content )
{ 
  try { showInfo.close(); } catch(e) {}
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  if (FlagStr=="Succ" && mWFlag == 0)
  {
  	  initForm();
  		emptyInsured();
  		if (fm.ContType.value==2)
  		{  
  			fm.ContNo.value="";
  			fm.ProposalContNo.value="";
  		}

  }


}
//提交后操作,服务器数据返回后执行的操作
function afterSubmit3( FlagStr, content )
{
  try { showInfo.close(); } catch(e) {}
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  if (FlagStr=="Succ" && mWFlag == 0)
  {

 }
 //alert(LoadFlag);
 	//tongmeng 2009-04-17 add
 	//保存后重新刷新界面.
 	//alert('2222222@@@@@@@@@@@');
 	
 	//ContInsuredInput.jsp?ContNo="+PersonInsuredGrid.getRowColData(tRow,16)+"&ContType=2&LoadFlag="+LoadFlag+"&tNameFlag="+tInsuredType+"&ProposalGrpContNo="+fm.ProposalGrpContNo.value+"&scantype="+scantype+"&checktype=2&display=0&prtNo="+prtNo+"&polNo="+polNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&InsuredNo="+PersonInsuredGrid.getRowColData(tRow, 3);
 	//prompt('',"ContInsuredInput.jsp?LoadFlag=2&PolTypeFlag="+fm.PolTypeFlag.value+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&tNameFlag="+fm.all('PolTypeFlag').value+"&InsuredNo="+tInsuredNo+"&ContNo="+fm.ContNo.value);
 	if(LoadFlag=="2")
 	{
    	{  
    			//alert("ok");
       	  location.href="ContInsuredInput.jsp?LoadFlag=2&PolTypeFlag="+fm.PolTypeFlag.value+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&tNameFlag="+fm.all('PolTypeFlag').value+"&InsuredNo="+fm.InsuredNo.value+"&ContNo="+fm.ContNo.value; 
          return;
      }
 	}


}
/*********************************************************************
 *  查询职业类别
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */      
function throughwork()
{
	//alert("ok");
   var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.OccupationCode.value+"'";
   var arrResult = easyExecSql(strSql);
   if (arrResult != null)
   {
       fm.OccupationType.value = arrResult[0][0];
   }
   else
   {
       fm.OccupationType.value = '1';
   }
}

function intoInterface()
{
	//create by yaory
	//alert("ok");
	//return;
	//location.href="../cardgrp/ContInput.jsp?LoadFlag="+ LoadFlag + "&prtNo=" + fm.all("PrtNo").value;
	location.href="../cardgrp/ProposalInput.jsp?LoadFlag="+LoadFlag+"&prtNo="+prtNo+"&scantype="+scantype+"&checktype="+checktype+"";
}


//提交后操作,服务器数据返回后执行的操作
function afterQuery( FlagStr, content )
{

}

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{

}

//取消按钮对应操作
function cancelForm()
{

}

//提交前的校验、计算
function beforeSubmit()
{
  //添加操作

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

//问题件录入
function QuestInput()
{
    cContNo = fm.ContNo.value;  //保单号码
    if(LoadFlag=="2"||LoadFlag=="4"||LoadFlag=="13")
    {
        if(mSwitch.getVar( "ProposalGrpContNo" )=="")
        {
            alert("尚无集体合同投保单号，请先保存!");
        }
		else
		{
            window.open("./GrpQuestInputMain.jsp?GrpContNo="+mSwitch.getVar( "ProposalGrpContNo" )+"&Flag="+LoadFlag,"",sFeatvarures);
        }
    }
    else
    {
        if(cContNo == "")
        {
            alert("尚无合同投保单号，请先保存!");
        }
        else
        {
            window.open("../uwgrp/QuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag,"window1",sFeatvarures);
        }
    }
}
//问题件查询
function QuestQuery()
{
   cContNo = fm.all("ContNo").value;  //保单号码
    if(LoadFlag=="2"||LoadFlag=="4"||LoadFlag=="13")
    {
         if(mSwitch.getVar( "ProposalGrpContNo" )==""||mSwitch.getVar( "ProposalGrpContNo" )==null)
         {
          	alert("请先选择一个团体主险投保单!");
          	return ;
         }
         else
         {
              window.open("./GrpQuestQueryMain.jsp?GrpContNo="+mSwitch.getVar( "ProposalGrpContNo" )+"&Flag="+LoadFlag,"",sFeatvarures);
         }
         }
    else
    {
        if(cContNo == "")
        {
	       alert("尚无合同投保单号，请先保存!");
        }
	    else
	    {
            window.open("../uwgrp/QuestQueryMain.jsp?ContNo="+cContNo+"&Flag="+LoadFlag,"window1",sFeatvarures);
        }
    }
}
//Click事件，当点击增加图片时触发该函数
function addClick()
{

}

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{

}

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{

}

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{

}

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow)
{

}

function queryBank()
{ 
	//alert ("-------1--------");
	showInfo = window.open("../cardgrp/BankQueryMain.jsp","BankQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
}

function afterQuery6(arrResult)
{
  if(arrResult!=null)
  {
  	fm.BankCode.value = arrResult[0][0];  	
  }
}

/*********************************************************************
 *  进入险种信息录入
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function intoRiskInfo()
{
	//alert(mSwitch.getVar( "ProposalGrpContNo" ))
         //alert(fm.BPNo.value);//add by yaory
	if(fm.InsuredNo.value==""||fm.ContNo.value=="")
	{
		alert("请先添加，选择被保人");
		return false;
	}
	//mSwitch =parent.VD.gVSwitch;
	delInsuredVar();
	addInsuredVar();
  //alert(fm.SelPolNo.value);
  try{mSwitch.addVar('SelPonNo','',fm.SelPolNo.value);}catch(ex){}; //选择险种单进入险种界面带出已保存的信息

	if ((LoadFlag=='5'||LoadFlag=='4'||LoadFlag=='6'||LoadFlag=='16')&&(mSwitch.getVar( "PolNo" ) == null || mSwitch.getVar( "PolNo" ) == ""))
	{
		alert("请先选择被保险人险种信息！");
		return;
	}
	try{mSwitch.addVar('SelPolNo','',fm.SelPolNo.value);}catch(ex){};
	try{mSwitch.deleteVar('ContNo');}catch(ex){};
	try{mSwitch.addVar('ContNo','',fm.ContNo.value);}catch(ex){};
	//yaory
	//alert(fm.InsuredPeoples.value);
	try{mSwitch.deleteVar('InsuredPeoples');}catch(ex){};
	try{mSwitch.addVar('InsuredPeoples','',fm.InsuredPeoples.value);}catch(ex){};
	try{mSwitch.updateVar('ContNo','',fm.ContNo.value);}catch(ex){};
	try{mSwitch.deleteVar('mainRiskPolNo');}catch(ex){};
        //////edit by yaory add parameters
//  alert(NameType);
  //alert(fm.PolTypeFlag.value);  
  if(prtNo=="null")
     //jixf {
      	//prtNo=GrpContNo;
     //jixf }
      //jixffm.PrtNo.value=prtNo;
      //jixffm.GrpContNo.value=GrpContNo;
      if(LoadFlag=="18")//如果是补名单，要注意合同号与印刷号的正确性
      {
      fm.PrtNo.value=GrpContNo;
      fm.GrpContNo.value=prtNo;
      
      } 
	parent.fraInterface.window.location = "./ProposalInput.jsp?EdorTypeCal="+EdorTypeCal+"&InsuredNo="+fm.InsuredNo.value+"&PolTypeFlag="+fm.PolTypeFlag.value+"&LoadFlag=" + LoadFlag+"&ContType="+ContType+"&scantype=" + scantype+ "&MissionID=" + MissionID+ "&SubMissionID=" + SubMissionID+"&BQFlag="+BQFlag+"&tBpno="+mSwitch.getVar( "ProposalGrpContNo" )+"&EdorType="+EdorType+"&EdorTypeCal="+EdorTypeCal+"&hh=1&checktype="+checktype+"&NameType="+fm.all('PolTypeFlag').value+"&display="+display; 
	//jixfparent.fraInterface.window.location = "./ProposalInput.jsp?InsuredNo="+fm.InsuredNo.value+"&PolTypeFlag="+fm.PolTypeFlag.value+"&LoadFlag=" + LoadFlag+"&ContType="+ContType+"&scantype=" + scantype+ "&MissionID=" + MissionID+ "&SubMissionID=" + SubMissionID+"&BQFlag="+BQFlag+"&tBpno="+GrpContNo+"&EdorType="+EdorType+"&EdorTypeCal="+EdorTypeCal+"&hh=1&checktype="+checktype+"&NameType="+fm.all('PolTypeFlag').value+"&display="+display; 
}

/*********************************************************************
 *  删除缓存中被保险人信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function delInsuredVar()
{
    try{mSwitch.deleteVar('ContNo');}catch(ex){};
    try{mSwitch.deleteVar('InsuredNo');}catch(ex){};
    try{mSwitch.deleteVar('PrtNo');}catch(ex){};
    try{mSwitch.deleteVar('GrpContNo');}catch(ex){};
 //   try{mSwitch.deleteVar('AppntNo');}catch(ex){};
 //   try{mSwitch.deleteVar('ManageCom');}catch(ex){};
    try{mSwitch.deleteVar('ExecuteCom');}catch(ex){};
    try{mSwitch.deleteVar('FamilyType');}catch(ex){};
    try{mSwitch.deleteVar('RelationToMainInsure');}catch(ex){};
    try{mSwitch.deleteVar('RelationToAppnt');}catch(ex){};
    try{mSwitch.deleteVar('AddressNo');}catch(ex){};
    try{mSwitch.deleteVar('SequenceNo');}catch(ex){};
    try{mSwitch.deleteVar('Name');}catch(ex){};
    try{mSwitch.deleteVar('Sex');}catch(ex){};
    try{mSwitch.deleteVar('Birthday');}catch(ex){};
    try{mSwitch.deleteVar('IDType');}catch(ex){};
    try{mSwitch.deleteVar('IDNo');}catch(ex){};
    try{mSwitch.deleteVar('RgtAddress');}catch(ex){};
    try{mSwitch.deleteVar('Marriage');}catch(ex){};
    try{mSwitch.deleteVar('MarriageDate');}catch(ex){};
    try{mSwitch.deleteVar('Health');}catch(ex){};
    try{mSwitch.deleteVar('Stature');}catch(ex){};
    try{mSwitch.deleteVar('Avoirdupois');}catch(ex){};
    try{mSwitch.deleteVar('Degree');}catch(ex){};
    try{mSwitch.deleteVar('CreditGrade');}catch(ex){};
    try{mSwitch.deleteVar('BankCode');}catch(ex){};
    try{mSwitch.deleteVar('BankAccNo');}catch(ex){};
    try{mSwitch.deleteVar('AccName');}catch(ex){};
    try{mSwitch.deleteVar('JoinCompanyDate');}catch(ex){};
    try{mSwitch.deleteVar('StartWorkDate');}catch(ex){};
    try{mSwitch.deleteVar('Position');}catch(ex){};
    try{mSwitch.deleteVar('Salary');}catch(ex){};
    try{mSwitch.deleteVar('OccupationType');}catch(ex){};
    try{mSwitch.deleteVar('OccupationCode');}catch(ex){};
    try{mSwitch.deleteVar('WorkType');}catch(ex){};
    try{mSwitch.deleteVar('PluralityType');}catch(ex){};
    try{mSwitch.deleteVar('SmokeFlag');}catch(ex){};
    try{mSwitch.deleteVar('ContPlanCode');}catch(ex){};
    try{mSwitch.deleteVar('Operator');}catch(ex){};
    try{mSwitch.deleteVar('MakeDate');}catch(ex){};
    try{mSwitch.deleteVar('MakeTime');}catch(ex){};
    try{mSwitch.deleteVar('ModifyDate');}catch(ex){};
    try{mSwitch.deleteVar('ModifyTime');}catch(ex){};
    
    //tongmeng 2009-03-23 Add
    //团单支持个单生效日的制定.
     try{mSwitch.deleteVar('ContCValiDate');}catch(ex){};
}

/*********************************************************************
 *  将被保险人信息加入到缓存中
 *  参数  ：  无 
 *  返回值：  无
 *********************************************************************
 */
function addInsuredVar()
{
    try{mSwitch.addVar('ContNo','',fm.ContNo.value);}catch(ex){};
    //alert("ContNo:"+fm.ContNo.value);
    try{mSwitch.addVar('InsuredNo','',fm.InsuredNo.value);}catch(ex){};
    try{mSwitch.addVar('PrtNo','',fm.PrtNo.value);}catch(ex){};
    try{mSwitch.addVar('GrpContNo','',fm.GrpContNo.value);}catch(ex){};
 //   try{mSwitch.addVar('AppntNo','',fm.AppntNo.value);}catch(ex){};
 //   try{mSwitch.addVar('ManageCom','',fm.ManageCom.value);}catch(ex){};
    try{mSwitch.addVar('ExecuteCom','',fm.ExecuteCom.value);}catch(ex){};
    try{mSwitch.addVar('FamilyType','',fm.FamilyType.value);}catch(ex){};
    try{mSwitch.addVar('RelationToMainInsure','',fm.RelationToMainInsure.value);}catch(ex){};
    try{mSwitch.addVar('RelationToAppnt','',fm.RelationToAppnt.value);}catch(ex){};
    try{mSwitch.addVar('AddressNo','',fm.AddressNo.value);}catch(ex){};
    try{mSwitch.addVar('SequenceNo','',fm.SequenceNo.value);}catch(ex){};
    try{mSwitch.addVar('Name','',fm.Name.value);}catch(ex){};
    try{mSwitch.addVar('Sex','',fm.Sex.value);}catch(ex){};
    try{mSwitch.addVar('Birthday','',fm.Birthday.value);}catch(ex){};
    try{mSwitch.addVar('IDType','',fm.IDType.value);}catch(ex){};
    try{mSwitch.addVar('IDNo','',fm.IDNo.value);}catch(ex){};
    try{mSwitch.addVar('RgtAddress','',fm.RgtAddress.value);}catch(ex){};
    try{mSwitch.addVar('Marriage','',fm.Marriage.value);}catch(ex){};
    try{mSwitch.addVar('MarriageDate','',fm.MarriageDate.value);}catch(ex){};
    try{mSwitch.addVar('Health','',fm.Health.value);}catch(ex){};
    try{mSwitch.addVar('Stature','',fm.Stature.value);}catch(ex){};
    try{mSwitch.addVar('Avoirdupois','',fm.Avoirdupois.value);}catch(ex){};
    try{mSwitch.addVar('Degree','',fm.Degree.value);}catch(ex){};
    try{mSwitch.addVar('CreditGrade','',fm.CreditGrade.value);}catch(ex){};
    try{mSwitch.addVar('BankCode','',fm.BankCode.value);}catch(ex){};
    try{mSwitch.addVar('BankAccNo','',fm.BankAccNo.value);}catch(ex){};
    try{mSwitch.addVar('AccName','',fm.AccName.value);}catch(ex){};
    try{mSwitch.addVar('JoinCompanyDate','',fm.JoinCompanyDate.value);}catch(ex){};
    try{mSwitch.addVar('StartWorkDate','',fm.StartWorkDate.value);}catch(ex){};
    try{mSwitch.addVar('Position','',fm.Position.value);}catch(ex){};
    try{mSwitch.addVar('Salary','',fm.Salary.value);}catch(ex){};
    try{mSwitch.addVar('OccupationType','',fm.OccupationType.value);}catch(ex){};
    try{mSwitch.addVar('OccupationCode','',fm.OccupationCode.value);}catch(ex){};
    try{mSwitch.addVar('WorkType','',fm.WorkType.value);}catch(ex){};
    try{mSwitch.addVar('PluralityType','',fm.PluralityType.value);}catch(ex){};
    try{mSwitch.addVar('SmokeFlag','',fm.SmokeFlag.value);}catch(ex){};
    try{mSwitch.addVar('ContPlanCode','',fm.ContPlanCode.value);}catch(ex){};
		try{mSwitch.addVar('Operator','',fm.Operator.value);}catch(ex){};
    try{mSwitch.addVar('MakeDate','',fm.MakeDate.value);}catch(ex){};
    try{mSwitch.addVar('MakeTime','',fm.MakeTime.value);}catch(ex){};
    try{mSwitch.addVar('ModifyDate','',fm.ModifyDate.value);}catch(ex){};
    try{mSwitch.addVar('ModifyTime','',fm.ModifyTime.value);}catch(ex){};
		//tongmeng 2009-03-23 add
		//支持团单下个单生效日的指定
	//	alert(fm.ContCValiDate.value);
		try{mSwitch.addVar('ContCValiDate','',fm.ContCValiDate.value);}catch(ex){};
}

/*********************************************************************
 *  添加被保险人
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function addRecord()
{
    if(fm.all('ContNo').value!=""){
        alert("请尝试通过[修改被保险人]来完成此次修改！");
        return false; 
    }
    if (fm.all('PolTypeFlag').value==0)
    {
        if( verifyInput2() == false ) return false;
    }
    if(checkAcc()==false) return false;//公共账户不能通过保障计划保存险种信息
    //卡单起止号码的教验
    if(fm.all('StartCode').value == ""||fm.all('EndCode').value == "")
    {
       if(fm.all('StartCode').value == "")
          fm.all('StartCode').value = fm.all('EndCode').value;
       else
          fm.all('EndCode').value = fm.all('StartCode').value;
    }
//2005.03.18 chenhq 对此进行修改
  if(LoadFlag==1||LoadFlag==3||LoadFlag==5||LoadFlag==6){
	    var tPrtNo=fm.all("PrtNo").value;
	    var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";
      arrResult=easyExecSql(sqlstr,1,0);
      if(arrResult!=null){
      for(var sequencenocout=0; sequencenocout<arrResult.length;sequencenocout++ )
      {
      if(fm.SequenceNo.value==arrResult[sequencenocout][0]){
	  	alert("已经存在该客户内部号");
	  	fm.SequenceNo.focus();
	  	return false;
	  	}
	    }
	  }
	}
//增加公共帐户与法人帐户的校验，只能添加一个
if(LoadFlag==2||LoadFlag==13)
{
	if(fm.PolTypeFlag.value=="2" || fm.PolTypeFlag.value=="3")
	{
		//alert(GrpContNo);
		//return;
	var arrQueryResult = null;
	var sql = "select * from lccont where poltype='"+fm.PolTypeFlag.value+"' and grpcontno='"+GrpContNo+"'";
	arrQueryResult = easyExecSql( sql , 1, 0);
	if(arrQueryResult!=null)
	{
		alert("公共账户与法人账户每个保单只能添加一个！");
		return ;
	}
	}
}
    if(LoadFlag==1)
    {
    	if(fm.Marriage.value=="")
    	{
    		alert("请填写婚姻状况！");
    		return false;
    	}
    	if(fm.RelationToMainInsured.value=="")
    	{
    		alert("请填写与主被保险人关系！");
    		return false;
    	}
    	if(fm.RelationToAppnt.value=="")
    	{
    		alert("请填写与投保人关系！！");
    		return false;
    	}
    }
    if(fm.all('IDType').value=="0")
    {
       var strChkIdNo = chkIdNo(trim(fm.all('IDNo').value),trim(fm.all('Birthday').value),trim(fm.all('Sex').value));
       if (strChkIdNo != "")
       {
        alert(strChkIdNo);
	      return false;
       }
    }
    //如果是无名单或公共帐户年龄，出生日期，性别可以不录
    if(fm.PolTypeFlag.value=="0")
    {
    	if(fm.Sex.value=="")
    	{
    		alert("请选择性别！");
    		return;
    	}
    	if(fm.Birthday.value=="")
    	{
    		alert("请输入出生日期！");
    		return;
    	}
    }
    
    if(fm.OccupationType.value=="" && (fm.PolTypeFlag.value=="0" || fm.PolTypeFlag.value=="1"))
    {
    	alert("请选择职业等级！");
    	return;
    }
    //if(!checkself())
    //return false;

    //if(!checkrelation())
    //return false;
    if(ImpartGrid.checkValue2(ImpartGrid.name,ImpartGrid)== false)return false;
    //if(ImpartDetailGrid.checkValue2(ImpartDetailGrid.name,ImpartDetailGrid)== false)return false;
	  //将页面中使用的muliline中的空白行删除
	  ImpartGrid.delBlankLine();
	  //ImpartDetailGrid.delBlankLine();
    // if (fm.InsuredNo.value==''&&fm.InsuredAddressNo.value!='')
    //{
    //    alert("被保险人客户号为空，不能有地址号码");
    //    return false;
    //}
    
    fm.all('ContType').value=ContType;
    fm.all( 'BQFlag' ).value = BQFlag;
    fm.all( 'EdorType' ).value = EdorType;
    fm.all( 'EdorTypeCal' ).value = EdorTypeCal;
    fm.all( 'EdorValiDate' ).value = EdorValiDate;
    
    fm.all('fmAction').value="INSERT||CONTINSURED";
    //alert(fm.ContPlanCode.value);
    if(fm.ContPlanCode.value!="")
    {
    	//alert("1");
    	//先保存EXCEL文件，然后直接调用磁盘投保程序
    	
    	//jixf temp chg fm.action="./GrpContInsuredSave2.jsp";//有保障计划的保存
	    //jixf temp chg fm.submit();
	    
	    
	    if(LoadFlag=="18")//如果是补名单，要注意合同号与印刷号的正确性
      {
      fm.PrtNo.value=GrpContNo;
      fm.GrpContNo.value=prtNo;
      fm.vContNo.value=vContNo;
      }
      if(LoadFlag=="7")//如果是新增被保险人,保单生效日期为保全生效日期
      {
			fm.ContCValiDate.value=EdorValiDate;
      }
    var showStr="正在添加被保人，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
   
      fm.submit();

	    
	    
    }else{
      //alert("2");
      //alert(prtNo);
      //alert(GrpContNo);
      //alert(LoadFlag);
      //jixf
      //if(prtNo=="null")
      //{
      //	prtNo=GrpContNo;
      //}
      //fm.PrtNo.value=prtNo;
      //fm.GrpContNo.value=GrpContNo;
      //jixf
      if(LoadFlag=="18")//如果是补名单，要注意合同号与印刷号的正确性
      {
      fm.PrtNo.value=GrpContNo;
      fm.GrpContNo.value=prtNo;
      fm.vContNo.value=vContNo;
      }
      
      //yaory
      
    var showStr="正在添加被保人，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
   
      fm.submit();
  }
    
    
 }

/*********************************************************************
 *  修改被选中的被保险人
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function modifyRecord()
{


    if (fm.all('PolTypeFlag').value==0)
    {
        if( verifyInput2() == false ) return false;
    }
    if(checkAcc()==false) return false;//公共账户不能通过保障计划保存险种信息
    if(!checkself())
        return false;
    if (fm.Name.value=='')
    {
        alert("请选中需要修改的客户！")
        return false;
    }
    //alert("SelNo:"+InsuredGrid.getSelNo());
    if (InsuredGrid.mulLineCount==0)
    {
        alert("该被保险人还没有保存，无法进行修改！");
        return false;
    }
    fm.InsuredAddressNo.value="";
    if (fm.InsuredNo.value==''&&fm.InsuredAddressNo.value!='')
    {
        alert("被保险人客户号为空，不能有地址号码");
        return false;
    }
    if(ImpartGrid.checkValue2(ImpartGrid.name,ImpartGrid)== false)return false;
    //if(ImpartDetailGrid.checkValue2(ImpartDetailGrid.name,ImpartDetailGrid)== false)return false;
	ImpartGrid.delBlankLine();
	//ImpartDetailGrid.delBlankLine();
    //alert(ContType);
    fm.all('ContType').value=ContType;
    fm.all('fmAction').value="UPDATE||CONTINSURED";
    var showStr="正在修改被保人，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
     if(LoadFlag=="7")//如果是新增被保险人,保单生效日期为保全生效日期
      {
			fm.ContCValiDate.value=EdorValiDate;
      }
    fm.submit();
}
function checkInsuredRela(){
   var tCodeData = "此被保人为主被保人，他(她)的连带被保人包括:";
   var tCustomerNo = fm.all('InsuredNo').value;
   var tCheckSQL="select customerno,name from lcinsuredRelated where maincustomerno='"+tCustomerNo+"' and customerno!='"+tCustomerNo+"'"
                +" and polno in (select polno from lcpol where grpcontno = '"+GrpContNo+"')";
   turnPage.strQueryResult  = easyQueryVer3(tCheckSQL, 1, 0, 1);
   if(turnPage.strQueryResult !=""){
      turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
      m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		tCodeData = tCodeData + "" + turnPage.arrDataCacheSet[i][0]+"-"+turnPage.arrDataCacheSet[i][1]+";" ;
    	}
    	tCodeData = tCodeData + "如果删除则其连带被保人也会删除！是否删除？";
    	if(!confirm(tCodeData)) return false;
   }
   return true;
}
/*********************************************************************
 *  删除被选中的被保险人
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 function deleteRecord()
{
    if (fm.InsuredNo.value=='')
    {
        alert("请选中需要删除的客户！")
        return false;
    }
    if (InsuredGrid.mulLineCount==0)
    {
        alert("该被保险人还没有保存，无法进行修改！");
        return false;
    }
     if (fm.InsuredNo.value==''&&fm.InsuredAddressNo.value!='')
    {
        alert("被保险人客户号为空，不能有地址号码");
        return false;
    }
    if (checkInsuredRela()==false) return false;
    fm.all('ContType').value=ContType;
    fm.all('fmAction').value="DELETE||CONTINSURED";
    var showStr="正在删除被保人，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
   
    fm.submit();
}
/*********************************************************************
 *  返回上一页面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function returnparent()
{
  	var backstr=fm.all("ContNo").value;

	mSwitch.addVar("PolNo", "", backstr);
	mSwitch.updateVar("PolNo", "", backstr);
	try
	{
	    mSwitch.deleteVar('ContNo');
	}
	catch(ex){};
	if(LoadFlag=="1"||LoadFlag=="3")
	{
		//alert(fm.all("PrtNo").value);
  	    location.href="../cardgrp/ContInput.jsp?LoadFlag="+ LoadFlag + "&prtNo=" + fm.all("PrtNo").value;
        }
        if(LoadFlag=="5"||LoadFlag=="25")
	{
		//alert(fm.all("PrtNo").value);
  	    location.href="../cardgrp/ContInput.jsp?LoadFlag="+ LoadFlag + "&prtNo=" + fm.all("PrtNo").value;
        }

	if(LoadFlag=="2")
	{
  	    location.href="../cardgrp/ContGrpInsuredInput.jsp?LoadFlag="+ LoadFlag + "&polNo=" + fm.all("GrpContNo").value+"&scantype="+scantype;
        }

	else if (LoadFlag=="6")
	{
	    location.href="ContInput.jsp?LoadFlag="+ LoadFlag + "&ContNo=" + backstr+"&prtNo="+fm.all("PrtNo").value;
	    return;
	}
	else if (LoadFlag=="7")
	{
	    location.href="../bq/GEdorTypeNI.jsp?BQFlag="+BQFlag;
	    return;
	}
	else if (LoadFlag=="8")
	{
	    location.href="../bq/GrpEdorTypeNR.jsp?BQFlag="+BQFlag;
	    return;
	}
	else if(LoadFlag=="4"||LoadFlag=="16"||LoadFlag=="13"||LoadFlag=="14"||LoadFlag=="23")
	{
	    if(Auditing=="1")
	    {
	    	top.close();
	    }
	    else
	    {
	    mSwitch.deleteVar("PolNo");
            parent.fraInterface.window.location = "../cardgrp/ContGrpInsuredInput.jsp?LoadFlag="+LoadFlag+"&scantype="+scantype;
	    }
	}
	else if (LoadFlag=="99")
	{
	    location.href="ContPolInput.jsp?LoadFlag="+ LoadFlag+"&scantype="+scantype;
	    return;
	}else if (LoadFlag=="18")
	{
		//alert(prtNo);
		//return;
		//location.href="../cardgrp/GroupPolApproveInfo.jsp?LoadFlag=18&polNo="+GrpContNo;
		top.fraInterface.window.location="../cardgrp/GroupPolApproveInfo.jsp?LoadFlag=18&vContNo="+vContNo+"&polNo="+prtNo+"";
		//top.fraInterface.window.location = "../cardgrp/ContInsuredInput.jsp?LoadFlag=18&ContType=2&prtNo="+prtNo+"&scantype="+scantype+"&ProposalGrpContNo="+GrpContNo+"&checktype=2&display=1";
	}
/*    else
    {
        location.href="ContInput.jsp?LoadFlag="+ LoadFlag;
	}  针对	各种情况的不同暂不支持else方式
*/
}
/*********************************************************************
 *  进入险种计划界面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 function grpRiskPlanInfo()
{
    var newWindow = window.open("../cardgrp/GroupRiskPlan.jsp","",sFeatvarures);
}
/*********************************************************************
 *  代码选择后触发时间
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterCodeSelect( cCodeName, Field )
{
    try
    {   //如果是家庭单。 
    	  if(cCodeName == "FamilyType")
    	  {
    	  	choosetype();
    	  	
    	  }
    	  if(cCodeName == "OccupationCode")
    	  {
    	  	//alert(fm.OccupationCode.value);
    	  	var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.OccupationCode.value+"'";
   var arrResult = easyExecSql(strSql);
   if (arrResult != null)
   {
       fm.OccupationType.value = arrResult[0][0];
   }
   else
   {
       fm.OccupationType.value = '1';
   }
    	  	
    	  }
    	  
    	  
    	  if(cCodeName=="GetAddressNo"){
    	  	//alert("ok");
 	var strSQL="select postaladdress,zipcode from LCAddress b where b.AddressNo='"+fm.InsuredAddressNo.value+"' and b.CustomerNo='"+fm.InsuredNo.value+"'";
   //alert(strSQL);
   arrResult=easyExecSql(strSQL);
   try{fm.all('PostalAddress').value= arrResult[0][0];}catch(ex){};
   try{fm.all('ZipCode').value= arrResult[0][1];}catch(ex){};
   
   
   return;
 }
        //如果是无名单
        if( cCodeName == "PolTypeFlag")
        {
            if (Field.value=="1")
            {   
            	  DivLCBasicInfo.style.display = "none"; 
            	  fm.all('IDType').value="9";
            	  fm.all('InsuredPeoples').value="10";
            	  //fm.all('InsuredAppAge').value="30";
            	  fm.all('InsuredPeoples').readOnly=false;
                fm.all('InsuredAppAge').readOnly=false; 
            	  fm.all('Name').value="无名单";
                
            }
          else if(Field.value=="2")
          	{
          		DivLCBasicInfo.style.display = "none"; 
            	  fm.all('IDType').value="9";
            	  fm.all('InsuredPeoples').value="0";
            	  //fm.all('InsuredAppAge').value="30";
            	  fm.all('InsuredPeoples').readOnly=true;
                fm.all('InsuredAppAge').readOnly=false; 
            	  fm.all('Name').value="公共帐户";
            	  divContPlan.style.display = "none"; 
          	}
          	else if(Field.value=="3")
          	{
          		DivLCBasicInfo.style.display = "none"; 
            	  fm.all('IDType').value="9";
            	  fm.all('InsuredPeoples').value="0";
            	  //fm.all('InsuredAppAge').value="30";
            	  fm.all('InsuredPeoples').readOnly=true;
                fm.all('InsuredAppAge').readOnly=false; 
            	  fm.all('Name').value="法人帐户";
            	  divContPlan.style.display = "none"; 
          	}
            else
            {  
            	  DivLCBasicInfo.style.display = "";   
             	  fm.all('InsuredPeoples').value="";
            	  fm.all('InsuredAppAge').value=""; 
            	  fm.all('Name').value="";
                fm.all('InsuredPeoples').readOnly=true;
                fm.all('InsuredPeoples').value="1";
                fm.all('InsuredAppAge').readOnly=true; 
            }
        }
         if( cCodeName == "ImpartCode")
         {

         }
         if( cCodeName == "SequenceNo")
         {
	   if(Field.value=="1"&&fm.SamePersonFlag.checked==false)
	   {
	   	     emptyInsured();
		     param="121";
		     fm.pagename.value="121";
                     fm.InsuredSequencename.value="第一被保险人资料";
                     fm.RelationToMainInsured.value="00";
	   }
	   if(Field.value=="2"&&fm.SamePersonFlag.checked==false)
	   {
	   	if(InsuredGrid.mulLineCount==0)
	   	{
	   		alert("请先添加第一被保人");
	   		fm.SequenceNo.value="1";
	   		return false;
	   	}
	   	     emptyInsured();
                     noneedhome();
		     param="122";
		     fm.pagename.value="122";
                     fm.InsuredSequencename.value="第二被保险人资料";
	   }
	   if(Field.value=="3"&&fm.SamePersonFlag.checked==false)
	   {
	   	if(InsuredGrid.mulLineCount==0)
	   	{
	   		alert("请先添加第一被保人");
	   		Field.value="1";
	   		return false;
	   	}
	   	if(InsuredGrid.mulLineCount==1)
	   	{
	   		alert("请先添加第二被保人");
	   		Field.value="1";
	   		return false;
	   	}
	   	     emptyInsured();
                     noneedhome();
		     param="123";
		     fm.pagename.value="123";
                     fm.InsuredSequencename.value="第三被保险人资料";
	   }
           if (scantype== "scan")
           {
           setFocus();
           }
         }
         if( cCodeName == "CheckPostalAddress")
         {
	 if(fm.CheckPostalAddress.value=="1")
	 {
	 	fm.all('PostalAddress').value=fm.all('GrpAddress').value;
                fm.all('ZipCode').value=fm.all('GrpZipCode').value;
                fm.all('Phone').value= fm.all('GrpPhone').value;

	 }
	 else if(fm.CheckPostalAddress.value=="2")
	 {
	 	fm.all('PostalAddress').value=fm.all('HomeAddress').value;
                fm.all('ZipCode').value=fm.all('HomeZipCode').value;
                fm.all('Phone').value= fm.all('HomePhone').value;
	 }
	 else if(fm.CheckPostalAddress.value=="3")
	 {
	 	fm.all('PostalAddress').value="";
                fm.all('ZipCode').value="";
                fm.all('Phone').value= "";
	 }
        }

    }
    catch(ex) {}

}
/*********************************************************************
 *  显示家庭单下被保险人的信息
 *  返回值：  无
 *********************************************************************
 */
function getInsuredInfo()
{
    var ContNo=fm.all("ContNo").value;
    if(ContNo!=null&&ContNo!="")
    {
        var strSQL ="select InsuredNo,Name,Sex,Birthday,RelationToMainInsured,SequenceNo from LCInsured where ContNo='"+ContNo+"' and insuredno ='"+InsuredNo+"'";
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
        //判断是否查询成功
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //查询成功则拆分字符串，返回二维数组
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //设置初始化过的MULTILINE对象
        turnPage.pageDisplayGrid = InsuredGrid;
        //保存SQL语句
        turnPage.strQuerySql = strSQL;
        //设置查询起始位置
        turnPage.pageIndex = 0;
        //在查询结果数组中取出符合页面显示大小设置的数组
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //调用MULTILINE对象显示查询结果
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }
    
}

/*********************************************************************
 *  获得个单合同的被保人信息
 *  返回值：  无
 *********************************************************************
 */
function getProposalInsuredInfo()
{
    var tContNo=fm.all("ContNo").value;
    arrResult=easyExecSql("select * from LCInsured where ContNo='"+tContNo+"' and insuredno ='"+InsuredNo+"'",1,0);
// alert(arrResult);
    if(arrResult==null||InsuredGrid.mulLineCount>1)
    {
        return;
    }
    else
    {
    	if(InsuredGrid.mulLineCount=1){
        DisplayInsured();//该合同下的被投保人信息
        var tCustomerNo = arrResult[0][2];		// 得到投保人客户号
        var tAddressNo = arrResult[0][10]; 		// 得到投保人地址号
        arrResult=easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+tCustomerNo+"'",1,0);
       }
        if(arrResult==null)
        {
            //alert("未得到用户信息");
            //return;
        }
        else
        {
            //displayAppnt();       //显示投保人详细内容
//    alert("2");
            emptyUndefined();
            fm.InsuredAddressNo.value=tAddressNo;
            getdetailaddress();//显示投保人地址详细内容
//    alert("3");
        }
    }
   // alert("222");
    getInsuredPolInfo();
//    alert("2");
    getImpartInfo();
    //getImpartDetailInfo();
}

/*********************************************************************
 *  MulLine的RadioBox点击事件，获得被保人详细信息，填入被保人信息表
 *  返回值：  无
 *********************************************************************
 */
function getInsuredDetail(parm1,parm2)
{
    var InsuredNo=fm.all(parm1).all('InsuredGrid1').value;
    var ContNo = fm.ContNo.value;
    //被保人详细信息
    var strSQL ="select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+InsuredNo+"'";
    arrResult=easyExecSql(strSQL);
    if(arrResult!=null)
    {
        displayAppnt();
    }
    strSQL ="select * from LCInsured where ContNo = '"+ContNo+"' and InsuredNo='"+InsuredNo+"'";
    arrResult=easyExecSql(strSQL);
    if(arrResult!=null)
    {
        DisplayInsured();
    }
    var tAddressNo = arrResult[0][10]; 		// 得到被保人地址号
    fm.InsuredAddressNo.value=tAddressNo;
    getdetailaddress();
    getInsuredPolInfo();
    getImpartInfo();
    //getImpartDetailInfo();
    InsuredChkNew();
    
    
}
/*********************************************************************
 *  把查询返回的客户数据显示到投保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function DisplayCustomer()
{
	try{fm.all('Nationality').value= arrResult[0][8]; }catch(ex){};

}
/*********************************************************************
 *  把查询返回的客户地址数据显示到投保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function DisplayAddress()
{
	try{fm.all('PostalAddress').value= arrResult[0][2]; }catch(ex){};
	try{fm.all('ZipCode').value= arrResult[0][3]; }catch(ex){};
	try{fm.all('Phone').value= arrResult[0][4]; }catch(ex){};
	try{fm.all('Mobile').value= arrResult[0][14]; }catch(ex){};
	try{fm.all('EMail').value= arrResult[0][16]; }catch(ex){};
	//try{fm.all('GrpName').value= arrResult[0][2]; }catch(ex){};
	try{fm.all('GrpPhone').value= arrResult[0][12]; }catch(ex){};
	try{fm.all('CompanyAddress').value= arrResult[0][10]; }catch(ex){};
	try{fm.all('GrpZipCode').value= arrResult[0][11]; }catch(ex){};
}

/*********************************************************************
 *  显示被保人详细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function DisplayInsured()
{
    try{fm.all('GrpContNo').value=arrResult[0][0];}catch(ex){};
    try{fm.all('ContNo').value=arrResult[0][1];}catch(ex){};
    try{fm.all('InsuredNo').value=arrResult[0][2];}catch(ex){};
    try{fm.all('PrtNo').value=arrResult[0][3];}catch(ex){};
    try{fm.all('AppntNo').value=arrResult[0][4];}catch(ex){};
    try{fm.all('ManageCom').value=arrResult[0][5];}catch(ex){};
    try{fm.all('ExecuteCom').value=arrResult[0][6];}catch(ex){};
    try{fm.all('FamilyID').value=arrResult[0][7];}catch(ex){};
    try{fm.all('RelationToMainInsured').value=arrResult[0][8];}catch(ex){};
    try{fm.all('RelationToAppnt').value=arrResult[0][9];}catch(ex){};
    try{fm.all('InsuredAddressNo').value=arrResult[0][10];}catch(ex){};
    try{fm.all('SequenceNo').value=arrResult[0][11];}catch(ex){};
    try{fm.all('Name').value=arrResult[0][12];}catch(ex){};
    try{fm.all('Sex').value=arrResult[0][13];}catch(ex){};
    try{fm.all('Birthday').value=arrResult[0][14];}catch(ex){};
    try{fm.all('IDType').value=arrResult[0][15];}catch(ex){};
    try{fm.all('IDNo').value=arrResult[0][16];}catch(ex){};
    try{fm.all('NativePlace').value=arrResult[0][17];}catch(ex){};
    try{fm.all('Nationality').value=arrResult[0][18];}catch(ex){};
    try{fm.all('RgtAddress').value=arrResult[0][19];}catch(ex){};
    try{fm.all('Marriage').value=arrResult[0][20];}catch(ex){};
    try{fm.all('MarriageDate').value=arrResult[0][21];}catch(ex){};
    try{fm.all('Health').value=arrResult[0][22];}catch(ex){};
    try{fm.all('Stature').value=arrResult[0][23];}catch(ex){};
    try{fm.all('Avoirdupois').value=arrResult[0][24];}catch(ex){};
    try{fm.all('Degree').value=arrResult[0][25];}catch(ex){};
    try{fm.all('CreditGrade').value=arrResult[0][26];}catch(ex){};
    try{fm.all('BankCode').value=arrResult[0][27];}catch(ex){};
    try{fm.all('BankAccNo').value=arrResult[0][28];}catch(ex){};
    try{fm.all('AccName').value=arrResult[0][29];}catch(ex){};
    try{fm.all('JoinCompanyDate').value=arrResult[0][30];}catch(ex){};
    try{fm.all('StartWorkDate').value=arrResult[0][31];}catch(ex){};
    try{fm.all('Position').value=arrResult[0][32];}catch(ex){};
    try{fm.all('Salary').value=arrResult[0][33];}catch(ex){};
    try{fm.all('OccupationType').value=arrResult[0][34];}catch(ex){};
    try{fm.all('OccupationCode').value=arrResult[0][35];}catch(ex){};
    try{fm.all('WorkType').value=arrResult[0][36];}catch(ex){};
    try{fm.all('PluralityType').value=arrResult[0][37];}catch(ex){};
    try{fm.all('SmokeFlag').value=arrResult[0][38];}catch(ex){};
    try{fm.all('ContPlanCode').value=arrResult[0][39];}catch(ex){};
    try{fm.all('Operator').value=arrResult[0][40];}catch(ex){};
    try{fm.all('MakeDate').value=arrResult[0][41];}catch(ex){};
    try{fm.all('MakeTime').value=arrResult[0][42];}catch(ex){};
    try{fm.all('ModifyDate').value=arrResult[0][43];}catch(ex){};
    try{fm.all('ModifyTime').value=arrResult[0][44];}catch(ex){};
    try{fm.all('WorkNo').value=arrResult[0][55];}catch(ex){};
    //alert(arrResult[0][55]);
    getAge();   
    
}
function displayissameperson()
{
    try{fm.all('InsuredNo').value= mSwitch.getVar( "AppntNo" ); }catch(ex){};
    try{fm.all('Name').value= mSwitch.getVar( "AppntName" ); }catch(ex){};
    try{fm.all('Sex').value= mSwitch.getVar( "AppntSex" ); }catch(ex){};
    try{fm.all('Birthday').value= mSwitch.getVar( "AppntBirthday" ); }catch(ex){};
    try{fm.all('IDType').value= mSwitch.getVar( "AppntIDType" ); }catch(ex){};
    try{fm.all('IDNo').value= mSwitch.getVar( "AppntIDNo" ); }catch(ex){};
    try{fm.all('Password').value= mSwitch.getVar( "AppntPassword" ); }catch(ex){};
    try{fm.all('NativePlace').value= mSwitch.getVar( "AppntNativePlace" ); }catch(ex){};
    try{fm.all('Nationality').value= mSwitch.getVar( "AppntNationality" ); }catch(ex){};
    try{fm.all('InsuredAddressNo').value= mSwitch.getVar( "AppntAddressNo" ); }catch(ex){};
    try{fm.all('RgtAddress').value= mSwitch.getVar( "AppntRgtAddress" ); }catch(ex){};
    try{fm.all('Marriage').value= mSwitch.getVar( "AppntMarriage" );}catch(ex){};
    try{fm.all('MarriageDate').value= mSwitch.getVar( "AppntMarriageDate" );}catch(ex){};
    try{fm.all('Health').value= mSwitch.getVar( "AppntHealth" );}catch(ex){};
    try{fm.all('Stature').value= mSwitch.getVar( "AppntStature" );}catch(ex){};
    try{fm.all('Avoirdupois').value= mSwitch.getVar( "AppntAvoirdupois" );}catch(ex){};
    try{fm.all('Degree').value= mSwitch.getVar( "AppntDegree" );}catch(ex){};
    try{fm.all('CreditGrade').value= mSwitch.getVar( "AppntDegreeCreditGrade" );}catch(ex){};
    try{fm.all('OthIDType').value= mSwitch.getVar( "AppntOthIDType" );}catch(ex){};
    try{fm.all('OthIDNo').value= mSwitch.getVar( "AppntOthIDNo" );}catch(ex){};
    try{fm.all('ICNo').value= mSwitch.getVar( "AppntICNo" );}catch(ex){};
    try{fm.all('GrpNo').value= mSwitch.getVar( "AppntGrpNo" );}catch(ex){};
    try{fm.all( 'JoinCompanyDate' ).value = mSwitch.getVar( "JoinCompanyDate" ); if(fm.all( 'JoinCompanyDate' ).value=="false"){fm.all( 'JoinCompanyDate' ).value="";} } catch(ex) { };
    try{fm.all('StartWorkDate').value= mSwitch.getVar( "AppntStartWorkDate" );}catch(ex){};
    try{fm.all('Position').value= mSwitch.getVar( "AppntPosition" );}catch(ex){};
    try{fm.all( 'Position' ).value = mSwitch.getVar( "Position" ); if(fm.all( 'Position' ).value=="false"){fm.all( 'Position' ).value="";} } catch(ex) { };
    try{fm.all('Salary').value= mSwitch.getVar( "AppntSalary" );}catch(ex){};
    try{fm.all( 'Salary' ).value = mSwitch.getVar( "Salary" ); if(fm.all( 'Salary' ).value=="false"){fm.all( 'Salary' ).value="";} } catch(ex) { };
    try{fm.all('OccupationType').value= mSwitch.getVar( "AppntOccupationType" );}catch(ex){};
    try{fm.all('OccupationCode').value= mSwitch.getVar( "AppntOccupationCode" );}catch(ex){};
    try{fm.all('WorkType').value= mSwitch.getVar( "AppntWorkType" );}catch(ex){};
    try{fm.all('PluralityType').value= mSwitch.getVar( "AppntPluralityType" );}catch(ex){};
    try{fm.all('DeathDate').value= mSwitch.getVar( "AppntDeathDate" );}catch(ex){};
    try{fm.all('SmokeFlag').value= mSwitch.getVar( "AppntSmokeFlag" );}catch(ex){};
    try{fm.all('BlacklistFlag').value= mSwitch.getVar( "AppntBlacklistFlag" );}catch(ex){};
    try{fm.all('Proterty').value= mSwitch.getVar( "AppntProterty" );}catch(ex){};
    try{fm.all('Remark').value= mSwitch.getVar( "AppntRemark" );}catch(ex){};
    try{fm.all('State').value= mSwitch.getVar( "AppntState" );}catch(ex){};
    try{fm.all('Operator').value= mSwitch.getVar( "AppntOperator" );}catch(ex){};
    try{fm.all('MakeDate').value= mSwitch.getVar( "AppntMakeDate" );}catch(ex){};
    try{fm.all('MakeTime').value= mSwitch.getVar( "AppntMakeTime" );}catch(ex){};
    try{fm.all('ModifyDate').value= mSwitch.getVar( "AppntModifyDate" );}catch(ex){};
    try{fm.all('ModifyTime').value= mSwitch.getVar( "AppntModifyTime" );}catch(ex){};
    try{fm.all('PostalAddress').value= mSwitch.getVar( "AppntPostalAddress" );}catch(ex){};
    try{fm.all('PostalAddress').value= mSwitch.getVar( "AppntPostalAddress" );}catch(ex){};
    try{fm.all('ZipCode').value= mSwitch.getVar( "AppntZipCode" );}catch(ex){};
    try{fm.all('Phone').value= mSwitch.getVar( "AppntPhone" );}catch(ex){};
    try{fm.all('Fax').value= mSwitch.getVar( "AppntFax" );}catch(ex){};
    try{fm.all('Mobile').value= mSwitch.getVar( "AppntMobile" );}catch(ex){};
    try{fm.all('EMail').value= mSwitch.getVar( "AppntEMail" );}catch(ex){};
    try{fm.all('GrpName').value= mSwitch.getVar( "AppntGrpName" );}catch(ex){};
    try{fm.all('GrpPhone').value= mSwitch.getVar( "AppntGrpPhone" );}catch(ex){};
    try{fm.all('GrpAddress').value= mSwitch.getVar( "CompanyAddress" );}catch(ex){};
    try{fm.all('GrpZipCode').value= mSwitch.getVar( "AppntGrpZipCode" );}catch(ex){};
    try{fm.all('GrpFax').value= mSwitch.getVar( "AppntGrpFax" );}catch(ex){};
    try{fm.all('HomeAddress').value= mSwitch.getVar( "AppntHomeAddress" );}catch(ex){};
    try{fm.all('HomePhone').value= mSwitch.getVar( "AppntHomePhone" );}catch(ex){};
    try{fm.all('HomeZipCode').value= mSwitch.getVar( "AppntHomeZipCode" );}catch(ex){};
    try{fm.all('HomeFax').value= mSwitch.getVar( "AppntHomeFax" );}catch(ex){};
    getAge();
}
/*********************************************************************
 *  查询告知信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getImpartInfo()
{
    initImpartGrid();
    //alert(fm.all('GrpContNo').value);
    var InsuredNo=fm.all("InsuredNo").value;
    //alert("InsuredNo="+InsuredNo);
    var ContNo=fm.all("ContNo").value; 
    //alert("ContNo="+ContNo); 
    //告知信息初始化
    if(InsuredNo!=null&&InsuredNo!="")
    {
        var strSQL ="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where GrpContNo='"+ fm.all('GrpContNo').value+"' and  CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and CustomerNoType='1' and PatchNo='0'";
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
        //判断是否查询成功
        if (!turnPage.strQueryResult)
        { 
            return false;
        }
        //查询成功则拆分字符串，返回二维数组
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //设置初始化过的MULTILINE对象
        turnPage.pageDisplayGrid = ImpartGrid;
        //保存SQL语句
        turnPage.strQuerySql = strSQL;
        //设置查询起始位置
        turnPage.pageIndex = 0;
        //在查询结果数组中取出符合页面显示大小设置的数组
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //调用MULTILINE对象显示查询结果
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }
}
/*********************************************************************
 *  查询告知信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getImpartDetailInfo()
{
    initImpartDetailGrid();
    var InsuredNo=fm.all("InsuredNo").value;
    var ContNo=fm.all("ContNo").value;
    //告知信息初始化
    if(InsuredNo!=null&&InsuredNo!="")
    {
        var strSQL ="select ImpartVer,ImpartCode,ImpartDetailContent,StartDate,EndDate,Prover,CurrCondition,IsProved from LCCustomerImpartDetail where CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and CustomerNoType='I'";
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
        //判断是否查询成功
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //查询成功则拆分字符串，返回二维数组
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //设置初始化过的MULTILINE对象
        turnPage.pageDisplayGrid = ImpartDetailGrid;
        //保存SQL语句
        turnPage.strQuerySql = strSQL;
        //设置查询起始位置
        turnPage.pageIndex = 0;
        //在查询结果数组中取出符合页面显示大小设置的数组
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //调用MULTILINE对象显示查询结果
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }
}
/*********************************************************************
 *  获得被保人险种信息，写入MulLine
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getInsuredPolInfo()
{
    initPolGrid();
    var InsuredNo=fm.all("InsuredNo").value;
    var ContNo=fm.all("ContNo").value;
    //险种信息初始化
    if(InsuredNo!=null&&InsuredNo!="")
    {
        var strSQL ="select PolNo,RiskCode,Prem,Amnt,case polstate when '0' then '保单(保全)未生效' when '1' then '生效' when '2' then '保单失效' when '3' then '保单责任终止' when '4' then '保单退保' when '5' then '保单领取开始' when '6' then '保单领取终止' when '7' then '死亡理赔' when '8' then '拒保' end from LCPol where InsuredNo='"+InsuredNo+"' and ContNo='"+ContNo+"'";
        //alert(strSQL);
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
        //判断是否查询成功
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //查询成功则拆分字符串，返回二维数组
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //设置初始化过的MULTILINE对象
        turnPage.pageDisplayGrid = PolGrid;
        //保存SQL语句
        turnPage.strQuerySql = strSQL;
        //设置查询起始位置
        turnPage.pageIndex = 0;
        //在查询结果数组中取出符合页面显示大小设置的数组
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //调用MULTILINE对象显示查询结果
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }
    //alert(fm.InsuredNo.value);
    //strSql = "select * from ldperson where Name='"+fm.Name.value+"' and Sex='"+fm.Sex.value+"' and Birthday=to_date('"+fm.Birthday.value+"','YYYY-MM-DD') and CustomerNo<>'"+fm.InsuredNo.value+"'"
    //              + " union select * from ldperson where IDType = '"+fm.IDType.value+"' and IDNo = '"+fm.IDNo.value+"' and CustomerNo<>'"+fm.InsuredNo.value+"'"; 
    //var PayIntv = easyExecSql(strSql);
    //alert(strSql);
    //fm.Name.value=strSql;
    //if(PayIntv==null)
    //{
    //	fm.InsuredChkButton2.disabled=true;
    //}
    //edit by yaory originwriter:yaory
}
/*********************************************************************
 *  MulLine的RadioBox点击事件，获得被保人险种详细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getPolDetail(parm1,parm2)
{
    var PolNo=fm.all(parm1).all('PolGrid1').value
    if(LoadFlag=="8")
    {
    	var strSQL="select appflag from lcpol where polno='"+ PolNo +"'";
      arrResult=easyExecSql(strSQL);
      if(arrResult[0][0]=="1")
      {
      	alert("该险种已经生效，不能对其进行操作!");
      	getInsuredPolInfo();
      	return false;
      }  
    }
    try{mSwitch.deleteVar('PolNo')}catch(ex){};
    try{mSwitch.addVar('PolNo','',PolNo);}catch(ex){};
    fm.SelPolNo.value=PolNo;
}
/*********************************************************************
 *  根据家庭单类型，隐藏界面控件
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function choosetype(){
	if(fm.FamilyType.value=="1")
	divTempFeeInput.style.display="";
	if(fm.FamilyType.value=="0")
	divTempFeeInput.style.display="none";
}
/*********************************************************************
 *  校验被保险人与主被保险人关系
 *  参数  ：  无
 *  返回值：  true or false
 *********************************************************************
 */
function checkself()
{
	if(fm.FamilyType.value=="0"&&fm.RelationToMainInsured.value=="")
	{
	    fm.RelationToMainInsured.value="00";
	    return true;
    }
	else if(fm.FamilyType.value=="0"&&fm.RelationToMainInsured.value!="00")
	{
	    alert("个人单中'与主被保险人关系'只能是'本人'");
	    fm.RelationToMainInsured.value="00";
	    return false;
    }
	else if(fm.FamilyType.value=="1"&&fm.RelationToMainInsured.value==""&&InsuredGrid.mulLineCount==0)
	{
	    fm.RelationToMainInsured.value="00";
	    return true;
    }
    else if(fm.FamilyType.value=="1"&&fm.RelationToMainInsured.value!="00"&&InsuredGrid.mulLineCount==0)
    {
	    alert("家庭单中第一位被保险人的'与主被保险人关系'只能是'本人'");
	    fm.RelationToMainInsured.value="00";
	    return false;
    }
    else
        return true;
}
/*********************************************************************
 *  校验保险人
 *  参数  ：  无
 *  返回值：  true or false
 *********************************************************************
 */
function checkrelation()
{
	if(LoadFlag==2||LoadFlag==7)
	{
        //if (fm.all('ContNo').value != "")
        //{
        //    alert("团单的个单不能有多被保险人");
        //    return false;
        //}
        //else
            //return true;
    }
    else
    {
        if (fm.all('ContNo').value != ""&&fm.FamilyType.value=="0")
        {
            var strSQL="select * from LCInsured where contno='"+fm.all('ContNo').value +"'";
            turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
            if(turnPage.strQueryResult)
            {
                alert("个单不能有多被保险人");
                return false;
            }
            else
                return true;
        }
//        else if (fm.all('ContNo').value != ""&&fm.FamilyType.value=="1"&&InsuredGrid.mulLineCount>0&&fm.RelationToMainInsured.value=="00")
//        {
//            
//            alert("家庭单只能有一个主被保险人");
//            return false;
//        }
        else if (fm.all('ContNo').value != ""&&fm.FamilyType.value=="1"&&fm.RelationToAppnt.value=="00")
        {
            var strSql="select * from LCInsured where contno='"+fm.all('ContNo').value +"' and RelationToAppnt='00' ";
            turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
            if(turnPage.strQueryResult)
		    {
                alert("投保人已经是该合同号下的被保险人");
                return false;
            }
		    else
		        return true;
        }
        else
            return true;
    }
	//select count(*) from ldinsured

}
/*********************************************************************
 *  投保人与被保人相同选择框事件
 *  参数  ：  无
 *  返回值：  true or false
 *********************************************************************
 */
function isSamePerson()
{
    //对应未选同一人，又打钩的情况
    if ( fm.SamePersonFlag.checked==true&&fm.RelationToAppnt.value!="00")
    {
      fm.all('DivLCInsured').style.display = "none";
      divLCInsuredPerson.style.display = "none";
      divSalary.style.display = "none";
      fm.SamePersonFlag.checked = true;
      fm.RelationToAppnt.value="00"
      displayissameperson();
    }
    //对应是同一人，又打钩的情况
    else if (fm.SamePersonFlag.checked == true)
    {
      fm.all('DivLCInsured').style.display = "none";
      divLCInsuredPerson.style.display = "none";
      divSalary.style.display = "none";
      displayissameperson();
    }
    //对应不选同一人的情况
    else if (fm.SamePersonFlag.checked == false)
    {
    fm.all('DivLCInsured').style.display = "";
    divLCInsuredPerson.style.display = "";
    divSalary.style.display = "";
    try{fm.all('Name').value=""; }catch(ex){};
    try{fm.all('Sex').value= ""; }catch(ex){};
    try{fm.all('Birthday').value= ""; }catch(ex){};
    try{fm.all('IDType').value= "0"; }catch(ex){};
    try{fm.all('IDNo').value= ""; }catch(ex){};
    try{fm.all('Password').value= ""; }catch(ex){};
    try{fm.all('NativePlace').value= ""; }catch(ex){};
    try{fm.all('Nationality').value=""; }catch(ex){};
    try{fm.all('RgtAddress').value= ""; }catch(ex){};
    try{fm.all('Marriage').value= "";}catch(ex){};
    try{fm.all('MarriageDate').value= "";}catch(ex){};
    try{fm.all('Health').value= "";}catch(ex){};
    try{fm.all('Stature').value= "";}catch(ex){};
    try{fm.all('Avoirdupois').value= "";}catch(ex){};
    try{fm.all('Degree').value= "";}catch(ex){};
    try{fm.all('CreditGrade').value= "";}catch(ex){};
    try{fm.all('OthIDType').value= "";}catch(ex){};
    try{fm.all('OthIDNo').value= "";}catch(ex){};
    try{fm.all('ICNo').value="";}catch(ex){};
    try{fm.all('GrpNo').value= "";}catch(ex){};
    try{fm.all('JoinCompanyDate').value= "";}catch(ex){}
    try{fm.all('StartWorkDate').value= "";}catch(ex){};
    try{fm.all('Position').value= "";}catch(ex){};
    try{fm.all('Salary').value= "";}catch(ex){};
    try{fm.all('OccupationType').value= "";}catch(ex){};
    try{fm.all('OccupationCode').value= "";}catch(ex){};
    try{fm.all('WorkType').value= "";}catch(ex){};
    try{fm.all('PluralityType').value= "";}catch(ex){};
    try{fm.all('DeathDate').value= "";}catch(ex){};
    try{fm.all('SmokeFlag').value= "";}catch(ex){};
    try{fm.all('BlacklistFlag').value= "";}catch(ex){};
    try{fm.all('Proterty').value= "";}catch(ex){};
    try{fm.all('Remark').value= "";}catch(ex){};
    try{fm.all('State').value= "";}catch(ex){};
    try{fm.all('Operator').value= "";}catch(ex){};
    try{fm.all('MakeDate').value= "";}catch(ex){};
    try{fm.all('MakeTime').value="";}catch(ex){};
    try{fm.all('ModifyDate').value= "";}catch(ex){};
    try{fm.all('ModifyTime').value= "";}catch(ex){};
    try{fm.all('PostalAddress').value= "";}catch(ex){};
    try{fm.all('PostalAddress').value= "";}catch(ex){};
    try{fm.all('ZipCode').value= "";}catch(ex){};
    try{fm.all('Phone').value= "";}catch(ex){};
    try{fm.all('Mobile').value= "";}catch(ex){};
    try{fm.all('EMail').value="";}catch(ex){};
    try{fm.all('GrpName').value= "";}catch(ex){};
    try{fm.all('GrpPhone').value= "";}catch(ex){};
    try{fm.all('GrpAddress').value="";}catch(ex){};
    try{fm.all('GrpZipCode').value= "";}catch(ex){};

    }
}
/*********************************************************************
 *  投保人客户号查询按扭事件
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryInsuredNo()
{
    if (fm.all("InsuredNo").value == "")
    {
      showAppnt1();
    }
    else
    {
        //arrResult = easyExecSql("select a.*,b.AddressNo,b.PostalAddress,b.ZipCode,b.HomePhone,b.Mobile,b.EMail,a.GrpNo,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode from LDPerson a,LCAddress b where 1=1 and a.CustomerNo=b.CustomerNo and a.CustomerNo = '" + fm.all("InsuredNo").value + "'", 1, 0);
        arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + fm.all("InsuredNo").value + "'", 1, 0);
        if (arrResult == null)
        {
          alert("未查到投保人信息");
          displayAppnt(new Array());
          emptyUndefined();
        }
        else
        {
          displayAppnt(arrResult[0]);
        }
    }
}
/*********************************************************************
 *  投保人查询按扭事件
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showAppnt1()
{
	if( mOperate == 0 )
	{
		mOperate = 2;
		showInfo = window.open( "../sys/LDPersonQueryNew.html" ,"",sFeatvarures);
	}
}  
/*********************************************************************
 *  显示投保人信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayAppnt()
{
    try{fm.all('InsuredNo').value= arrResult[0][0]; }catch(ex){};
    try{fm.all('Name').value= arrResult[0][1]; }catch(ex){};
    try{fm.all('Sex').value= arrResult[0][2]; }catch(ex){};
    try{fm.all('Birthday').value= arrResult[0][3]; }catch(ex){};
    try{fm.all('IDType').value= arrResult[0][4]; }catch(ex){};
    try{fm.all('IDNo').value= arrResult[0][5]; }catch(ex){};
    try{fm.all('Password').value= arrResult[0][6]; }catch(ex){};
    try{fm.all('NativePlace').value= arrResult[0][7]; }catch(ex){};
    try{fm.all('Nationality').value= arrResult[0][8]; }catch(ex){};
    try{fm.all('RgtAddress').value= arrResult[0][9]; }catch(ex){};
    try{fm.all('Marriage').value= arrResult[0][10];}catch(ex){};
    try{fm.all('MarriageDate').value= arrResult[0][11];}catch(ex){};
    try{fm.all('Health').value= arrResult[0][12];}catch(ex){};
    try{fm.all('Stature').value= arrResult[0][13];}catch(ex){};
    try{fm.all('Avoirdupois').value= arrResult[0][14];}catch(ex){};
    try{fm.all('Degree').value= arrResult[0][15];}catch(ex){};
    try{fm.all('CreditGrade').value= arrResult[0][16];}catch(ex){};
    try{fm.all('OthIDType').value= arrResult[0][17];}catch(ex){};
    try{fm.all('OthIDNo').value= arrResult[0][18];}catch(ex){};
    try{fm.all('ICNo').value= arrResult[0][19];}catch(ex){};
    try{fm.all('GrpNo').value= arrResult[0][20];}catch(ex){};
    try{fm.all('JoinCompanyDate').value= arrResult[0][21];}catch(ex){};
    try{fm.all('StartWorkDate').value= arrResult[0][22];}catch(ex){};
    try{fm.all('Position').value= arrResult[0][23];}catch(ex){};
    try{fm.all('Salary').value= arrResult[0][24];}catch(ex){};
    try{fm.all('OccupationType').value= arrResult[0][25];}catch(ex){};
    try{fm.all('OccupationCode').value= arrResult[0][26];}catch(ex){};
    try{fm.all('WorkType').value= arrResult[0][27];}catch(ex){};
    try{fm.all('PluralityType').value= arrResult[0][28];}catch(ex){};
    try{fm.all('DeathDate').value= arrResult[0][29];}catch(ex){};
    try{fm.all('SmokeFlag').value= arrResult[0][30];}catch(ex){};
    try{fm.all('BlacklistFlag').value= arrResult[0][31];}catch(ex){};
    try{fm.all('Proterty').value= arrResult[0][32];}catch(ex){};
    try{fm.all('Remark').value= arrResult[0][33];}catch(ex){};
    try{fm.all('State').value= arrResult[0][34];}catch(ex){};
    try{fm.all('Operator').value= arrResult[0][35];}catch(ex){};
    try{fm.all('MakeDate').value= arrResult[0][36];}catch(ex){};
    try{fm.all('MakeTime').value= arrResult[0][37];}catch(ex){};
    try{fm.all('ModifyDate').value= arrResult[0][38];}catch(ex){};
    try{fm.all('ModifyTime').value= arrResult[0][39];}catch(ex){};
    try{fm.all('GrpName').value= arrResult[0][40];}catch(ex){};
    getAge();
    getCertifyInfo(arrResult[0][0]);
    
    //地址显示部分的变动
    try{fm.all('InsuredAddressNo').value= "";}catch(ex){};
    try{fm.all('PostalAddress').value=  "";}catch(ex){};
    try{fm.all('ZipCode').value=  "";}catch(ex){};
    try{fm.all('Phone').value=  "";}catch(ex){};
    try{fm.all('Mobile').value=  "";}catch(ex){};
    try{fm.all('EMail').value=  "";}catch(ex){};
    //try{fm.all('GrpName').value= arrResult[0][46];}catch(ex){};
    try{fm.all('GrpPhone').value=  "";}catch(ex){};
    try{fm.all('GrpAddress').value=  "";}catch(ex){};
    try{fm.all('GrpZipCode').value=  "";}catch(ex){};
    if(fm.all('Salary').value=="null"||fm.all('Salary').value==null){
    	fm.all('Salary').value="0";
    }
}

function getCertifyInfo( ){
   var strSQL = "select a.CertifyCode,a.StartCode,a.EndCode from lcinsured a where a.insuredno='"+arrResult[0][0]+"'";
   arrResult=easyExecSql(strSQL);
   try{fm.all('CertifyCode').value=arrResult[0][0]}catch(ex){};
   try{fm.all('StartCode').value=arrResult[0][1]}catch(ex){};
   try{fm.all('EndCode').value=arrResult[0][2]}catch(ex){};
}
/*********************************************************************
 *  查询返回后触发
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterQuery( arrQueryResult )
{
  //alert("here:" + arrQueryResult + "\n" + mOperate);
    if( arrQueryResult != null )
    {
        arrResult = arrQueryResult;

        if( mOperate == 1 )
        {		// 查询投保单
            fm.all( 'ContNo' ).value = arrQueryResult[0][0];

            arrResult = easyExecSql("select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,Remark from LCCont where LCCont = '" + arrQueryResult[0][0] + "'", 1, 0);

            if (arrResult == null)
            {
                alert("未查到投保单信息");
            }
            else
            {
                displayLCContPol(arrResult[0]);
            }
        }

        if( mOperate == 2 )
        {		// 投保人信息
        	//arrResult = easyExecSql("select a.*,b.AddressNo,b.PostalAddress,b.ZipCode,b.HomePhone,b.Mobile,b.EMail,a.GrpNo,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode from LDPerson a,LCAddress b where 1=1 and a.CustomerNo=b.CustomerNo and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
        	arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
        	if (arrResult == null)
        	{
        	    alert("未查到投保人信息");
        	}
        	else
        	{
        	    displayAppnt(arrResult[0]);
        	}
        }
    }

	mOperate = 0;		// 恢复初态
}
/*********************************************************************
 *  查询职业类别
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getdetailwork()
{
    var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.OccupationCode.value+"'";
    var arrResult = easyExecSql(strSql);
    if (arrResult != null)
    {
        fm.OccupationType.value = arrResult[0][0];
    }
    else
    {
        fm.OccupationType.value = '';
    }
}
/*获得个人单信息，写入页面控件
function getProposalInsuredInfo(){
  var ContNo = fm.ContNo.value;
  //被保人详细信息
  var strSQL ="select * from ldperson where CustomerNo in (select InsuredNo from LCInsured where ContNo='"+ContNo+"')";
  arrResult=easyExecSql(strSQL);
  if(arrResult!=null){
  	DisplayCustomer();
  }

  strSQL ="select * from LCInsured where ContNo = '"+ContNo+"'";
  arrResult=easyExecSql(strSQL);

  if(arrResult!=null){
  	   DisplayInsured();
  }else{


    return;
  }

  var tAddressNo = arrResult[0][10]; 		// 得到被保人地址号
  var InsuredNo=arrResult[0][2];
  var strSQL="select * from LCAddress where AddressNo='"+tAddressNo+"' and CustomerNo='"+InsuredNo+"'";
  arrResult=easyExecSql(strSQL);
    if(arrResult!=null){
  	DisplayAddress();
    }

    getInsuredPolInfo();

}*/


/*********************************************************************
 *  把合同所有信息录入结束确认
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function inputConfirm(wFlag)
{
	//alert("LoadFlag=="+LoadFlag);


    if (wFlag ==1 ) //录入完毕确认
    {
        var tStr= "	select * from lwmission where 1=1 and lwmission.missionprop1 = '"+fm.ContNo.value+"'";
        turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
        if (turnPage.strQueryResult)
        {
		    alert("该合同已经做过保存！");
		    return;
		}
		fm.AppntNo.value = AppntNo;
		fm.AppntName.value = AppntName;
		fm.WorkFlowFlag.value = "7999999999";
    }
    else if (wFlag ==2)//复核完毕确认
    {
  	    if(fm.all('ProposalContNo').value == "")
        {
            alert("未查询出合同信息,不容许您进行 [复核完毕] 确认！");
            return;
        }
		fm.WorkFlowFlag.value = "0000001001";
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
    }
    else if (wFlag ==3)
    {
        if(fm.all('ProposalContNo').value == "")
        {
            alert("未查询出合同信息,不容许您进行 [复核修改完毕] 确认！");
            return;
        }
		fm.WorkFlowFlag.value = "0000001002";
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
	}
	else
		return;

    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = "./InputConfirm.jsp";
    fm.submit(); //提交
}
/*********************************************************************
 *  查询被保险人详细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getdetailaddress()
{
//alert("1");
    var strSQL="select b.AddressNo,b.PostalAddress,b.ZipCode,b.HomePhone,b.Mobile,b.EMail,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode from LCAddress b where b.AddressNo='"+fm.InsuredAddressNo.value+"' and b.CustomerNo='"+fm.InsuredNo.value+"'";
    arrResult=easyExecSql(strSQL);
    try{fm.all('InsuredAddressNo').value= arrResult[0][0];}catch(ex){};
//alert("2");
    try{fm.all('PostalAddress').value= arrResult[0][1];}catch(ex){};
//alert("3");
    try{fm.all('ZipCode').value= arrResult[0][2];}catch(ex){};
//alert("4");
    try{fm.all('HomePhone').value= arrResult[0][3];}catch(ex){};
//alert("5");
    try{fm.all('Mobile').value= arrResult[0][4];}catch(ex){};
//alert("6");
    try{fm.all('EMail').value= arrResult[0][5];}catch(ex){};
    //try{fm.all('GrpName').value= arrResult[0][6];}catch(ex){};
//alert("7");
    try{fm.all('GrpPhone').value= arrResult[0][6];}catch(ex){};
//alert("8")
   var strSQL1 = "select a.certifycode,a.startcode,a.endcode,workno from lcinsured a where a.insuredno='"+fm.InsuredNo.value+"'"
               + " and contno='"+fm.all("ContNo").value+"'";
   arrResult1=easyExecSql(strSQL1);
   try{fm.all('CertifyCode').value=arrResult1[0][0]}catch(ex){};
   try{fm.all('StartCode').value=arrResult1[0][1]}catch(ex){};
   try{fm.all('EndCode').value=arrResult1[0][2]}catch(ex){};
   try{fm.all('WorkNo').value=arrResult1[0][3]}catch(ex){};
 //tongmeng 2009-03-20 Add
 //增加个人保单生效日指定
// alert('ContNo'+ContNo);
    var strSQL2 = "select cvalidate from lccont a where a.contno='"+fm.ContNo.value+"' ";
   arrResult2=easyExecSql(strSQL2);
 //alert(arrResult2[0][0]);
  try{fm.all('ContCValiDate').value=arrResult2[0][0]}catch(ex){};
   // try{fm.all('GrpAddress').value= arrResult[0][7];}catch(ex){};
    //try{fm.all('GrpZipCode').value= arrResult[0][8];}catch(ex){};
}
/*********************************************************************
 *  查询保险计划
 *  参数  ：  集体合同投保单号
 *  返回值：  无
 *********************************************************************
 */
function getContPlanCode(tProposalGrpContNo)
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
    strsql = "select ContPlanCode,ContPlanName from LCContPlan where ContPlanCode!='00' and ProposalGrpContNo='"+tProposalGrpContNo+"'";
    //alert("strsql :" + strsql);
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    	 divContPlan.style.display="";
    }
    else
    {
      //alert("保险计划没查到");
        divContPlan.style.display="";
    }
    //alert ("tcodedata : " + tCodeData);
    return tCodeData;
}

/*********************************************************************
 *  查询处理机构
 *  参数  ：  集体合同投保单号
 *  返回值：  无
 *********************************************************************
 */
function getExecuteCom(tProposalGrpContNo)
{
    	//alert("1");
    var i = 0;
	var j = 0;
	var m = 0;
	var n = 0;
	var strsql = "";
	var tCodeData = "0|";
	strsql = "select ExecuteCom,Name from LCGeneral a,LDCom b where a.GrpContNo='"+tProposalGrpContNo+"' and a.ExecuteCom=b.ComCode";
	//alert("strsql :" + strsql);
	turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
	if (turnPage.strQueryResult != "")
	{
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		m = turnPage.arrDataCacheSet.length;
		for (i = 0; i < m; i++)
		{
			j = i + 1;
//			tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
			tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
		}
		divExecuteCom.style.display="";
	}
	else
	{
	    divExecuteCom.style.display="none";
    }
	//alert ("tcodedata : " + tCodeData);

	return tCodeData;
}

function emptyInsured()
{

	try{fm.all('InsuredNo').value= ""; }catch(ex){};
	try{fm.all('ExecuteCom').value= ""; }catch(ex){};
	try{fm.all('FamilyID').value= ""; }catch(ex){};
	try{fm.all('RelationToMainInsured').value= ""; }catch(ex){};
	try{fm.all('RelationToAppnt').value= ""; }catch(ex){};
	try{fm.all('InsuredAddressNo').value= ""; }catch(ex){};
	//try{fm.all('SequenceNo').value= ""; }catch(ex){};
	try{fm.all('Name').value= ""; }catch(ex){};
	try{fm.all('Sex').value= ""; }catch(ex){};
	try{fm.all('Birthday').value= ""; }catch(ex){};
	try{fm.all('IDType').value= "9"; }catch(ex){};
	try{fm.all('IDNo').value= ""; }catch(ex){};
	try{fm.all('NativePlace').value= ""; }catch(ex){};
	try{fm.all('Nationality').value= ""; }catch(ex){};
	try{fm.all('RgtAddress').value= ""; }catch(ex){};
	try{fm.all('Marriage').value= ""; }catch(ex){};
	try{fm.all('MarriageDate').value= ""; }catch(ex){};
	try{fm.all('Health').value= ""; }catch(ex){};
	try{fm.all('Stature').value= ""; }catch(ex){};
	try{fm.all('Avoirdupois').value= ""; }catch(ex){};
	try{fm.all('Degree').value= ""; }catch(ex){};
	try{fm.all('CreditGrade').value= ""; }catch(ex){};
	try{fm.all('BankCode').value= ""; }catch(ex){};
	try{fm.all('BankAccNo').value= ""; }catch(ex){};
	try{fm.all('AccName').value= ""; }catch(ex){};
	try{fm.all('JoinCompanyDate').value= ""; }catch(ex){};
	try{fm.all('StartWorkDate').value= ""; }catch(ex){};
	try{fm.all('Position').value= ""; }catch(ex){};
	try{fm.all('Salary').value= ""; }catch(ex){};
	try{fm.all('OccupationType').value= ""; }catch(ex){};
	try{fm.all('OccupationCode').value= ""; }catch(ex){};
	try{fm.all('WorkType').value= ""; }catch(ex){};
	try{fm.all('PluralityType').value= ""; }catch(ex){};
	try{fm.all('SmokeFlag').value= ""; }catch(ex){};
	try{fm.all('ContPlanCode').value= ""; }catch(ex){};
        try{fm.all('GrpName').value= ""; }catch(ex){};
        try{fm.all('HomeAddress').value= ""; }catch(ex){};
        try{fm.all('HomeZipCode').value= ""; }catch(ex){};
        try{fm.all('HomePhone').value= ""; }catch(ex){};
        try{fm.all('HomeFax').value= ""; }catch(ex){};
        try{fm.all('GrpFax').value= ""; }catch(ex){};
        try{fm.all('Fax').value= ""; }catch(ex){};
	emptyAddress();
	ImpartGrid.clearData();
	ImpartGrid.addOne();
	//ImpartDetailGrid.clearData();
	//ImpartDetailGrid.addOne();
}

/*********************************************************************
 *  清空客户地址数据
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function emptyAddress()
{
	try{fm.all('PostalAddress').value= "";  }catch(ex){};
	try{fm.all('ZipCode').value= "";  }catch(ex){};
	try{fm.all('Phone').value= "";  }catch(ex){};
	try{fm.all('Mobile').value= "";  }catch(ex){};
	try{fm.all('EMail').value= "";  }catch(ex){};
	//try{fm.all('GrpName').value= arrResult[0][2]; }catch(ex){};
	try{fm.all('GrpPhone').value= "";  }catch(ex){};
	try{fm.all('GrpAddress').value= ""; }catch(ex){};
	try{fm.all('GrpZipCode').value= "";  }catch(ex){};
}

function isNumeric(strValue)
{
  var NUM="0123456789";
  var i;
  if(strValue==null ||strValue=="") return false;
  for(i=0;i<strValue.length;i++)
  {
    if(NUM.indexOf(strValue.charAt(i))<0) return false
  }
  if(strValue.indexOf(".")!=strValue.lastIndexOf(".")) return false;
  return true;
}
/*********************************************************************
 *  根据身份证号取得出生日期和性别
 *  参数  ：  身份证号
 *  返回值：  无
 *********************************************************************
 */

function getBirthdaySexByIDNo(iIdNo)
{
	try{
	if(fm.all('IDType').value=="0")
	{
		//做身份证的处理
//		if(isNumeric(iIdNo)==false)
//  {
//    alert("身份证号码只能是数字！");
//    return;
//    }
    
    if(iIdNo.length!=18&&iIdNo.length!=15)
    {
    alert("身份证长度只能为15或18位！");
    return;
    }
    
    if(iIdNo.length==18)
    {
    	if(iIdNo.substring(10,12)>12){
    	alert("身份证中代表出生日期的数字填写错误！");
    	return;
    	}
    }
    
    ///////////
    if(iIdNo.length==18)
    {
    if(iIdNo.substring(10,12)=="12"||iIdNo.substring(10,12)=="01"||iIdNo.substring(10,12)=="03"||iIdNo.substring(10,12)=="05"||iIdNo.substring(10,12)=="07"||iIdNo.substring(10,12)=="08"||iIdNo.substring(10,12)=="10")
    {
    if(iIdNo.substring(12,14)>31)
    {
    alert("身份证中代表出生日期的数字填写错误！");
    return;
    }
    }
    if(iIdNo.substring(10,12)=="04"||iIdNo.substring(10,12)=="06"||iIdNo.substring(10,12)=="09"||iIdNo.substring(10,12)=="11")
    {
    if(iIdNo.substring(12,14)>30)
    {
    alert("身份证中代表出生日期的数字填写错误！");
    return;
    }
    }

    if(iIdNo.substring(10,12)=="02")
    {

     if(iIdNo.substring(6,10)%4!=0)
     {
    if(iIdNo.substring(12,14)>28)
    {
    alert("身份证中代表出生日期的数字填写错误！");
    return;
    }
     }
     if(iIdNo.substring(6,10)%4==0)
     {
    if(iIdNo.substring(12,14)>29)
    {
    alert("身份证中代表出生日期的数字填写错误！");
    return;
    }
     }
    }
  }
  
  if(iIdNo.length==15)
  {
    if(iIdNo.substring(8,10)>12){
    alert("身份证中代表出生日期填写错误！");
    return;
    }
  }
  if(iIdNo.length==15)
  {

    //判断日期
    if(iIdNo.substring(8,10)=="12"||iIdNo.substring(8,10)=="01"||iIdNo.substring(8,10)=="03"||iIdNo.substring(8,10)=="05"||iIdNo.substring(8,10)=="07"||iIdNo.substring(8,10)=="08"||iIdNo.substring(8,10)=="10")
    {
    if(iIdNo.substring(10,12)>31)
    {
    alert("身份证中代表出生日期的数字填写错误！");
    return;
    }
    }
    if(iIdNo.substring(8,10)=="04"||iIdNo.substring(8,10)=="06"||iIdNo.substring(8,10)=="09"||iIdNo.substring(8,10)=="11")
    {
    if(iIdNo.substring(10,12)>30)
    {
    alert("身份证中代表出生日期的数字填写错误！");
    return;
    }
    }
    if(iIdNo.substring(8,10)=="02")
    {

     if(19+(iIdNo.substring(6,8))%4!=0)
     {
    if(iIdNo.substring(10,12)>28)
    {
    alert("身份证中代表出生日期的数字填写错误！");
    return;
    }
     }

     if(19+(iIdNo.substring(6,8)+1900)%4==0)
     {
    if(iIdNo.substring(10,12)>29)
    {
    alert("身份证中代表出生日期的数字填写错误！");
    return;
    }
     }
    }
  }
  //////////////////////////
  if(trim(iIdNo).length==18)
{
var sex;
var sexq;
var birthday;
birthday=trim(iIdNo).substring(6,10)+"-"+trim(iIdNo).substring(10,12)+"-"+trim(iIdNo).substring(12,14);

 fm.Birthday.value=birthday;

  sex=trim(iIdNo).substring(16,17)
  
  
   if(sex%2==1){
  sexq='0';
}else{
sexq='1';
}
  
   fm.Sex.value=sexq;

}

//////
if(trim(iIdNo).length==15)
{
var sex;
var sexq;
var birthday;
birthday="19"+trim(iIdNo).substring(6,8)+"-"+trim(iIdNo).substring(8,10)+"-"+trim(iIdNo).substring(10,12);
   
   fm.Birthday.value=birthday;

  sex=trim(iIdNo).substring(14,15)
  
  
   if(sex%2==1){
  sexq='0';
}else{
sexq='1';
}
  
   fm.Sex.value=sexq;

}
/////////
var aBirthday=trim(fm.Birthday.value);
         var d;
         var y1,y2,m1,m2,d1,d2
         var age=0;
         var s="";
         //*****************
  if(fm.EdorType.value=='NI')
  {
  var compardate=fm.EdorValiDate.value;
  //alert("compardate"+compardate);	
  }
else
	{         
  var strSql = "select cvalidate from lcgrpcont where prtno='"+ GrpContNo +"'";
  var arrResult = easyExecSql(strSql);
  //alert(arrResult);
  var compardate=arrResult[0][0];
  }  //
   var birthday=trim(fm.Birthday.value);
   //alert(birthday);
   var arrBirthday = birthday.split("-");
  if (arrBirthday[1].length == 1) arrBirthday[1] = "0" + arrBirthday[1];
  if (arrBirthday[2].length == 1) arrBirthday[2] = "0" + arrBirthday[2];
  
   var arrToday=compardate.split("-");
  if (arrToday[1].length == 1) arrToday[1] = "0" + arrToday[1];
  if (arrToday[2].length == 1) arrToday[2] = "0" + arrToday[2];
  
  if(arrToday[0]<=99)
  {
    arrBirthday[0]=	arrBirthday[0]-1900;
  }
  var age = arrToday[0] - arrBirthday[0] - 1;
  //当前月大于出生月
  //alert(arrToday[1] + " | " + arrBirthday[1] + " | " + (arrToday[1] > arrBirthday[1]));
  if (arrToday[1] > arrBirthday[1]) {
    age = age + 1;
    fm.InsuredAppAge.value=age;
  }
  //当前月小于出生月
  else if (arrToday[1] < arrBirthday[1]) {
    fm.InsuredAppAge.value=age;
  }
  //当前月等于出生月的时候，看出生日
  else if (arrToday[2] >= arrBirthday[2]) {
    age = age + 1;
    fm.InsuredAppAge.value=age;
  }
  else {
    fm.InsuredAppAge.value=age;
  }
		//fm.all('Birthday').value=getBirthdatByIdNo(iIdNo);
		//fm.all('Sex').value=getSexByIDNo(iIdNo);
		if(fm.all('Sex').value=="0")
		 {
			 fm.all('SexName').value="男";
		 }
		else
			 fm.all('SexName').value="女";
	}
}catch(ex)
{}
}
/*********************************************************************
 *  合同信息录入完毕确认
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function GrpInputConfirm(wFlag)
{
	mWFlag = 1;
	if (wFlag ==1 ) //录入完毕确认
	{
	    var tStr= "	select * from lwmission where 1=1 "
	    					+" and lwmission.processid = '0000000011'"
	    					+" and lwmission.activityid = '0000011001'"
	    					+" and lwmission.missionprop1 = '"+fm.ProposalGrpContNo.value+"'";
	    turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
	    if (turnPage.strQueryResult)
	    {
	        alert("该团单合同已经做过保存！");
	        return;
	    }
		if(fm.all('ProposalGrpContNo').value == "")
	    {
	        alert("团单合同信息未保存,不容许您进行 [录入完毕] 确认！");
	        return;
	    }
		fm.WorkFlowFlag.value = "6999999999";			//录入完毕
    }
    else if (wFlag ==2)//复核完毕确认
    {
        if(fm.all('ProposalGrpContNo').value == "")
	    {
	        alert("未查询出团单合同信息,不容许您进行 [复核完毕] 确认！");
	        return;
	    }
		fm.WorkFlowFlag.value = "0000011002";					//复核完毕
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;
	}
	else if (wFlag ==3)
    {
  	    if(fm.all('ProposalGrpContNo').value == "")
	    {
	        alert("未查询出合同信息,不容许您进行 [复核修改完毕] 确认！");
	        return;
	    }
		fm.WorkFlowFlag.value = "0000001002";					//复核修改完毕
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;
	}
	else if(wFlag == 4)
	{
		if(fm.all('ProposalGrpContNo').value == "")
	    {
	       alert("未查询出合同信息,不容许您进行 [修改完毕] 确认！");
	       return;
	    }
	    fm.WorkFlowFlag.value = "0000001021";					//问题修改
	    fm.MissionID.value = MissionID;
	    fm.SubMissionID.value = SubMissionID;
	}
	else
		return;

    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action = "./GrpInputConfirm.jsp";
    fm.submit(); //提交
}
function getaddresscodedata()
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
    
    strsql = "select AddressNo,PostalAddress from LCAddress where CustomerNo ='"+fm.InsuredNo.value+"'";
    //alert("strsql :" + strsql);
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert ("tcodedata : " + tCodeData);
    //return tCodeData;
    fm.all("InsuredAddressNo").CodeData=tCodeData;
}                  

function getImpartCode(parm1,parm2){
  //alert("hehe:"+fm.all(parm1).all('ImpartGrid1').value);
  var impartVer=fm.all(parm1).all('ImpartGrid1').value;
  window.open("../cardgrp/ImpartCodeSel.jsp?ImpartVer="+impartVer,"",sFeatvarures);
}
function checkidtype()
{
	if(fm.IDType.value=="")
	{
		alert("请先选择证件类型！");
		fm.IDNo.value="";
  }
}
function getallinfo()
{
 	if(fm.Name.value!=""&&fm.IDType.value!=""&&fm.IDNo.value!="")
 	{
	    strSQL = "select a.CustomerNo, a.Name, a.Sex, a.Birthday, a.IDType, a.IDNo from LDPerson a where 1=1 "
                +"  and Name='"+fm.Name.value
                +"' and IDType='"+fm.IDType.value
                +"' and IDNo='"+fm.IDNo.value
		+"' order by a.CustomerNo";
             turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
             if (turnPage.strQueryResult != "")
             {
 		  mOperate = 2;
 		  //window.open("../sys/LDPersonQueryAll.html?Name="+fm.Name.value+"&IDType="+fm.IDType.value+"&IDNo="+fm.IDNo.value,"newwindow","height=10,width=1090,top=180,left=180, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no,status=no");
 	     }
 	     else
 	     return;
 	}
}
function DelRiskInfo()
{
	if(fm.InsuredNo.value=="")
	{
		alert("请先选择被保人");
		return false;
	}
	var tSel =PolGrid.getSelNo();
	if( tSel == 0 || tSel == null )
	{
		alert("该客户没有险种或者您忘记选择了？");
		return false;
	}
	var tRow = PolGrid.getSelNo() - 1;
	var tpolno=PolGrid.getRowColData(tRow,1)
	
	//tongmeng 2009-03-30 Modify
	//如果被保人是按照保障计划投保的,不允许删除险种.
	var tSQL = "select decode(count(*),0,0,1) from lcinsured where InsuredNo='"+fm.InsuredNo.value+"'"
	         + " and contplancode is null ";
	//prompt('',tSQL);
	var tArrRst= easyExecSql(tSQL);
	if(tArrRst!=null)
	{
		 if(tArrRst[0][0]<=0)
		 {
		 	  alert("该客户以保障计划承保,不能单独删除险种!");
				return false;
		 }
	}
	fm.all('fmAction').value="DELETE||INSUREDRISK";
	fm.action="./DelIsuredRisk.jsp?polno="+tpolno;
	fm.submit(); //提交

}
function InsuredChk()
{
	//var tSel =InsuredGrid.getSelNo();
	//if( tSel == 0 || tSel == null )
	//{
	//	alert("请先选择被保险人！");
	//	return false;
	//}
	//var tRow = InsuredGrid.getSelNo() - 1;
	var tInsuredNo=fm.InsuredNo.value;
	var tInsuredName=fm.Name.value;
	var tInsuredSex=fm.Sex.value;
	var tBirthday=fm.Birthday.value;
	//alert(tInsuredNo);
	//alert(tInsuredName);
	//alert(tInsuredSex);
	//alert(tBirthday);
	
        var sqlstr="select * from ldperson where Name='"+tInsuredName+"' and Sex='"+tInsuredSex+"' and Birthday='"+tBirthday+"' and CustomerNo<>'"+tInsuredNo+"'"
                  + " union select * from ldperson where IDType = '"+fm.IDType.value+"' and IDType <> null and IDNo = '"+fm.IDNo.value+"' and IDNo <> null and CustomerNo<>'"+tInsuredNo+"'" ;
        arrResult = easyExecSql(sqlstr,1,0);

        if(arrResult==null)
        {
	   alert("该没有与该被保人保人相似的客户,无需校验");
	   return false;
        }
 //alert(GrpContNo);
	window.open("../cardgrp/InsuredChkMain.jsp?ProposalNo1="+GrpContNo+"&InsuredNo="+tInsuredNo+"&Flag=I","window2",sFeatvarures);
}
function getdetailaccount()
{
	if(fm.AccountNo.value=="1")
	{
           fm.all('BankAccNo').value=mSwitch.getVar("AppntBankAccNo");
           fm.all('BankCode').value=mSwitch.getVar("AppntBankCode");
           fm.all('AccName').value=mSwitch.getVar("AppntAccName");
	}
	if(fm.AccountNo.value=="2")
	{
           fm.all('BankAccNo').value="";
           fm.all('BankCode').value="";
           fm.all('AccName').value="";
	}

}
function AutoMoveForNext()
{
	if(fm.AutoMovePerson.value=="定制第二被保险人")
	{
		     //emptyFormElements();
		     param="122";
		     fm.pagename.value="122";
                     fm.AutoMovePerson.value="定制第三被保险人";
                     return false;
	}
	if(fm.AutoMovePerson.value=="定制第三被保险人")
	{
		     //emptyFormElements();
		     param="123";
		     fm.pagename.value="123";
                     fm.AutoMovePerson.value="定制第一被保险人";
                     return false;
	}
		if(fm.AutoMovePerson.value=="定制第一被保险人")
	{
		     //emptyFormElements();
		     param="121";
		     fm.pagename.value="121";
                     fm.AutoMovePerson.value="定制第二被保险人";
                     return false;
	}
}
function noneedhome()
{
    var insuredno="";
    if(InsuredGrid.mulLineCount>=1)
    {
        for(var personcount=0;personcount<=InsuredGrid.mulLineCount;personcount++)
        {
        	if(InsuredGrid.getRowColData(personcount,5)=="00")
        	{
        		insuredno=InsuredGrid.getRowColData(personcount,1);
        		break;
        	}
        }
       var strhomea="select HomeAddress,HomeZipCode,HomePhone from lcaddress where customerno='"+insuredno+"' and addressno=(select addressno from lcinsured where contno='"+fm.ContNo.value+"' and insuredno='"+insuredno+"')";
       arrResult=easyExecSql(strhomea,1,0);
       try{fm.all('HomeAddress').value= arrResult[0][0];}catch(ex){};
       try{fm.all('HomeZipCode').value= arrResult[0][1];}catch(ex){};
       try{fm.all('HomePhone').value= arrResult[0][2];}catch(ex){};
    }
}
function getdetail()
{
var strSql = "select BankCode,AccName from LCAccount where BankAccNo='" + fm.BankAccNo.value+"'";
arrResult = easyExecSql(strSql);
if (arrResult != null) {
      fm.BankCode.value = arrResult[0][0];
      fm.AccName.value = arrResult[0][1];
    }
}


// 在初始化body时自动效验投保人信息
function InsuredChkNew(){

        var tRow = InsuredGrid.getSelNo() - 1;
	var tInsuredNo=InsuredGrid.getRowColData(tRow,1);
	var tInsuredName=InsuredGrid.getRowColData(tRow,2);
	var tInsuredSex=InsuredGrid.getRowColData(tRow,3);
	var tBirthday=InsuredGrid.getRowColData(tRow,4);
        var sqlstr="select * from ldperson where Name='"+tInsuredName+"' and Sex='"+tInsuredSex+"' and Birthday='"+tBirthday+"' and CustomerNo<>'"+tInsuredNo+"'"
                  + " union select * from ldperson where IDType = '"+fm.IDType.value+"' and IDType <> null and IDNo = '"+fm.IDNo.value+"' and IDNo <> null and CustomerNo<>'"+tInsuredNo+"'" ;
        arrResult = easyExecSql(sqlstr,1,0);


        if(arrResult==null)
        {//disabled"投保人效验"按钮

					fm.InsuredChkButton.disabled = true;
//				  return false;
        }else{
					//如果有相同姓名、性别、生日不同客户号的用户显示"投保人校验"按钮
				}
}

function getoccupationtype(){

   //alert(fm.all('InsuredNo').value);
   //var strSql = "select occupationtype from ldperson where customerno='"+ fm.all('InsuredNo').value+"'";
   //arrResult = easyExecSql(strSql);
   //if (arrResult != null) {
   // fm.all('OccupationCode').value= arrResult[0][0];
   // }
}



function checkappntbirthday(){
  if(fm.Birthday.value.length==8){
  if(fm.Birthday.value.indexOf('-')==-1){
  	var Year =     fm.Birthday.value.substring(0,4);
  	var Month =    fm.Birthday.value.substring(4,6);
  	var Day =      fm.Birthday.value.substring(6,8);
  	fm.Birthday.value = Year+"-"+Month+"-"+Day;
  	if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("您输入的投保人出生日期有误!");
   	   fm.Birthday.value = "";
   	   return;
  	  }
  }
}
else {var Year =     fm.Birthday.value.substring(0,4);
	    var Month =    fm.Birthday.value.substring(5,7);
	    var Day =      fm.Birthday.value.substring(8,10);
	    if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("您输入的投保人出生日期有误!");
   	   fm.Birthday.value = "";
   	   return;
  	     }
  }
}
function getAge(){
	
	//alert("fm.Birthday.value=="+fm.Birthday.value);
	try{
  if(fm.Birthday.value==""){
  	return;
  }
  //alert("fm.Birthday.value.indexOf('-')=="+fm.Birthday.value.indexOf('-'));a
  if(fm.Birthday.value.indexOf('-')==-1){
  	var Year =     fm.Birthday.value.substring(0,4);
  	var Month =    fm.Birthday.value.substring(4,6);
  	var Day =      fm.Birthday.value.substring(6,8);
  	fm.Birthday.value = Year+"-"+Month+"-"+Day;
  	}
  	if(calAge(fm.Birthday.value)<0)
  	{
      alert("出生日期只能为当前日期以前");
      fm.InsuredAppAge.value="";
      return;
    }
  	fm.InsuredAppAge.value=calAge(fm.Birthday.value);
    return;
  //}
 
   
  	  if(calAge(fm.Birthday.value)<0)
  	 {
      alert("出生日期只能为当前日期以前");
      fm.InsuredAppAge.value="";
      return;
     }
  //日期计算不是系统日期而是生效日期。
  //alert("EdorType"+fm.EdorType.value);
  
  if(fm.EdorType.value=='NI')
  {
  var compardate=fm.EdorValiDate.value;
  //alert("compardate"+compardate);	
  }
else
  {
  var strSql = "select cvalidate from lcgrpcont where prtno='"+ GrpContNo +"'";
  var arrResult = easyExecSql(strSql);
  //alert(arrResult);
  var compardate=arrResult[0][0];
  }
  //
   var birthday=fm.Birthday.value;
   //alert(birthday);
   var arrBirthday = birthday.split("-");
  if (arrBirthday[1].length == 1) arrBirthday[1] = "0" + arrBirthday[1];
  if (arrBirthday[2].length == 1) arrBirthday[2] = "0" + arrBirthday[2];
  
   var arrToday=compardate.split("-");
  if (arrToday[1].length == 1) arrToday[1] = "0" + arrToday[1];
  if (arrToday[2].length == 1) arrToday[2] = "0" + arrToday[2];
  
  if(arrToday[0]<=99)
  {
    arrBirthday[0]=	arrBirthday[0]-1900;
  }
  var age = arrToday[0] - arrBirthday[0] - 1;
  //当前月大于出生月
  //alert(arrToday[1] + " | " + arrBirthday[1] + " | " + (arrToday[1] > arrBirthday[1]));
  if (arrToday[1] > arrBirthday[1]) {
    age = age + 1;
    fm.InsuredAppAge.value=age;
  }
  //当前月小于出生月
  else if (arrToday[1] < arrBirthday[1]) {
    fm.InsuredAppAge.value=age;
  }
  //当前月等于出生月的时候，看出生日
  else if (arrToday[2] >= arrBirthday[2]) {
    age = age + 1;
    fm.InsuredAppAge.value=age;
  }
  else {
    fm.InsuredAppAge.value=age;
  }
  
}catch(ex)
{
}
  //fm.InsuredAppAge.value=calAge(fm.Birthday.value);
  return;

}



//校验被保人出生日期
function checkinsuredbirthday(){
	if(fm.Birthday.value.length==8){
  if(fm.Birthday.value.indexOf('-')==-1){
  	var Year =     fm.Birthday.value.substring(0,4);
  	var Month =    fm.Birthday.value.substring(4,6);
  	var Day =      fm.Birthday.value.substring(6,8);
  	fm.Birthday.value = Year+"-"+Month+"-"+Day;
  	if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("您输入的被保人出生日期有误!");
   	   fm.Birthday.value = "";
   	   return;
  	  }
  }
}
else {//var Year =     fm.Birthday.value.substring(0,4);
	    //var Month =    fm.Birthday.value.substring(5,7);
	    //var Day =      fm.Birthday.value.substring(8,10);
	    //if(Year=="0000"||Month=="00"||Day=="00"){
     	// alert("您输入的被保人出生日期有误!");
   	  // fm.Birthday.value = "";
   	  // return;
   	  //}
   	  alert("您输入的被保人出生日期必须是8位，格式如：“20060801”!");
   	  fm.Birthday.value="";
   	  return;
  	     
  }
}

function InitOccupationType()
{
	//alert(vContNo);
	var sql="select occupationtype from lcinsured where contno='"+vContNo+"'";
	var QueryResult = easyExecSql(sql, 1, 0);
	if(QueryResult!=null)
	{
		fm.OccupationType.value=QueryResult[0][0];
	}
}

function checkAcc(){
   //alert("checkAcc");
   var tPolTypeFlag = fm.all('PolTypeFlag').value;
   var tContPlanCode = fm.all('ContPlanCode').value;
   //alert("tPolTypeFlag:"+tPolTypeFlag);
   //alert("tContPlanCode:"+tContPlanCode);
   if(tPolTypeFlag=="2"){
     if(tContPlanCode==""){
      }else{
       alert("[公共账户]不能通过保障计划保存险种信息!");
       fm.ContPlanCode.value = "";
       return false;
      } 
   }
   return true;
}
