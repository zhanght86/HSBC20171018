 //               该文件中包含客户端需要处理的函数和事件
var mOperate = "";
var showInfo1;
var mDebug="0";
var turnPage = new turnPageClass(); 
var arrResult;

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
  	
  }
  	
	
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
            window.open("./GrpQuestInputMain.jsp?GrpContNo="+mSwitch.getVar( "ProposalGrpContNo" )+"&Flag="+LoadFlag);
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
            window.open("../uwgrp/QuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag,"window1");
        }
    }
}
//问题件查询
function QuestQuery()
{
   cContNo = document.all("ContNo").value;  //保单号码
    if(LoadFlag=="2"||LoadFlag=="4"||LoadFlag=="13")
    {
         if(mSwitch.getVar( "ProposalGrpContNo" )==""||mSwitch.getVar( "ProposalGrpContNo" )==null)
         {
          	alert("请先选择一个团体主险投保单!");
          	return ;
         }
         else
         {
              window.open("./GrpQuestQueryMain.jsp?GrpContNo="+mSwitch.getVar( "ProposalGrpContNo" )+"&Flag="+LoadFlag);
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
            window.open("../uwgrp/QuestQueryMain.jsp?ContNo="+cContNo+"&Flag="+LoadFlag,"window1");
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

/*********************************************************************
 *  进入险种信息录入
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function intoRiskInfo()
{
	if(fm.InsuredNo.value==""||fm.ContNo.value=="")
	{
		alert("请先添加，选择被保人");
		return false;
	}
	//mSwitch =parent.VD.gVSwitch;
	delInsuredVar();
	addInsuredVar();

  try{mSwitch.addVar('SelPonNo','',fm.SelPolNo.value);}catch(ex){}; //选择险种单进入险种界面带出已保存的信息
  
	if ((LoadFlag=='4'||LoadFlag=='6'||LoadFlag=='16')&&(mSwitch.getVar( "PolNo" ) == null || mSwitch.getVar( "PolNo" ) == ""))
	{
		alert("请先选择被保险人险种信息！");
		return;
	}
	try{mSwitch.addVar('SelPolNo','',fm.SelPolNo.value);}catch(ex){};
	try{mSwitch.deleteVar('ContNo');}catch(ex){};
	try{mSwitch.addVar('ContNo','',fm.ContNo.value);}catch(ex){};
	try{mSwitch.updateVar('ContNo','',fm.ContNo.value);}catch(ex){};
	try{mSwitch.deleteVar('mainRiskPolNo');}catch(ex){};
	parent.fraInterface.window.location = "./ProposalInput.jsp?LoadFlag=" + LoadFlag+"&ContType="+ContType+"&scantype=" + scantype+ "&MissionID=" + MissionID+ "&SubMissionID=" + SubMissionID+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype;
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

}

/*********************************************************************
 *  添加被保险人
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function addRecord()
{    
		  var tPrtNo=document.all("PrtNo").value;
      arrResult=easyExecSql("select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'",1,0);	
      if(arrResult!=null)
      {
      for(var sequencenocout=0; sequencenocout<arrResult[0].length;sequencenocout++ )
      {
      if(fm.SequenceNo.value==arrResult[sequencenocout][0])
      {	  	
	  	alert("已经存在该客户内部号");
	  	fm.SequenceNo.focus();
	  	return false;	  	
       }
      }
      } 
    if(LoadFlag==1)
    {
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
    if (document.all('PolTypeFlag').value==0)
    {
        if( verifyInput2() == false ) return false;
    }
    if(document.all('IDType').value=="0")
    {
       var strChkIdNo = chkIdNo(trim(document.all('IDNo').value),trim(document.all('Birthday').value),trim(document.all('Sex').value));
       if (strChkIdNo != "")
       {  	
             alert(strChkIdNo);
	     return false;
       }  
    }  
    if(!checkself())
    return false;

    if(!checkrelation())
    return false;
    if(ImpartGrid.checkValue2(ImpartGrid.name,ImpartGrid)== false)return false;    
    if(ImpartDetailGrid.checkValue2(ImpartDetailGrid.name,ImpartDetailGrid)== false)return false;    
	ImpartGrid.delBlankLine();
	ImpartDetailGrid.delBlankLine();	
     if (fm.InsuredNo.value==''&&fm.AddressNo.value!='')
    {
        alert("被保险人客户号为空，不能有地址号码");
        return false;    
    }
    document.all('ContType').value=ContType;
    document.all( 'BQFlag' ).value = BQFlag;    
    document.all('fmAction').value="INSERT||CONTINSURED";
		fm.RelationToMainInsured.disabled = false	;
    fm.submit();
 }
 
/*********************************************************************
 *  修改被选中的被保险人
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */  
function modifyRecord()
{
/*
		    var tPrtNo=document.all("PrtNo").value;
      arrResult=easyExecSql("select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'",1,0);	
      for(var sequencenocout=0; sequencenocout<arrResult[0].length;sequencenocout++ )
      {
      if(fm.SequenceNo.value==arrResult[0][sequencenocout]){	  	
	  	alert("已经存在该客户内部号");
	  	fm.SequenceNo.focus();
	  	return false;	  	
	  	}
	    }
*/	
	
    if (document.all('PolTypeFlag').value==0)
    {
        if( verifyInput2() == false ) return false;
    }
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
    if (fm.InsuredNo.value==''&&fm.AddressNo.value!='')
    {
        alert("被保险人客户号为空，不能有地址号码");
        return false;    
    }
    if(ImpartGrid.checkValue2(ImpartGrid.name,ImpartGrid)== false)return false;    
    if(ImpartDetailGrid.checkValue2(ImpartDetailGrid.name,ImpartDetailGrid)== false)return false;    
	ImpartGrid.delBlankLine();
	ImpartDetailGrid.delBlankLine();     
     
    document.all('ContType').value=ContType;
    document.all('fmAction').value="UPDATE||CONTINSURED";
    fm.submit();
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
     if (fm.InsuredNo.value==''&&fm.AddressNo.value!='')
    {
        alert("被保险人客户号为空，不能有地址号码");
        return false;    
    }
    document.all('ContType').value=ContType;
    document.all('fmAction').value="DELETE||CONTINSURED";
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
  	var backstr=document.all("ContNo").value;
  	
	mSwitch.addVar("PolNo", "", backstr);
	mSwitch.updateVar("PolNo", "", backstr);
	try
	{
	    mSwitch.deleteVar('ContNo');
	}
	catch(ex){};
	if(LoadFlag=="1"||LoadFlag=="3")
	{
		//alert(document.all("PrtNo").value);
  	    location.href="../appgrp/BankContInput.jsp?LoadFlag="+ LoadFlag + "&prtNo=" + document.all("PrtNo").value;
        }
        if(LoadFlag=="5"||LoadFlag=="25")
	{
		//alert(document.all("PrtNo").value);
  	    location.href="../appgrp/ContInput.jsp?LoadFlag="+ LoadFlag + "&prtNo=" + document.all("PrtNo").value;
        }
        
	if(LoadFlag=="2")
	{
  	    location.href="../appgrp/ContGrpInsuredInput.jsp?LoadFlag="+ LoadFlag + "&polNo=" + document.all("GrpContNo").value+"&scantype="+scantype;
        }
        
	else if (LoadFlag=="6")
	{
	    location.href="ContInput.jsp?LoadFlag="+ LoadFlag + "&ContNo=" + backstr+"&prtNo="+document.all("PrtNo").value;
	    return;
	}
	else if (LoadFlag=="7")
	{
	    location.href="../bq/GEdorTypeNI.jsp?BQFlag="+BQFlag;
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
            parent.fraInterface.window.location = "../appgrp/ContGrpInsuredInput.jsp?LoadFlag="+LoadFlag+"&scantype="+scantype; 
	    }
	}
	else if (LoadFlag=="99")
	{
	    location.href="ContPolInput.jsp?LoadFlag="+ LoadFlag+"&scantype="+scantype;
	    return;
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
    var newWindow = window.open("../appgrp/GroupRiskPlan.jsp");
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
    {
				if(cCodeName == "FamilyType"){
					if(Field.value == "0"){
						fm.RelationToMainInsured.value="00";
						fm.RelationToMainInsured.disabled = true;
						fm.SequenceNo.disabled = true;
					}else if(Field.value == "1"){
						fm.RelationToMainInsured.disabled = false;
						fm.SequenceNo.disabled = false;
					}
				}
        //如果是无名单
        if( cCodeName == "PolTypeFlag")
        {
            if (Field.value!='0')
            {
                document.all('InsuredPeoples').readOnly=false;
                document.all('InsuredAppAge').readOnly=false;
            }
            else
            {
                document.all('InsuredPeoples').readOnly=true;
                document.all('InsuredAppAge').readOnly=true;
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
	 	document.all('PostalAddress').value=document.all('GrpAddress').value;
                document.all('ZipCode').value=document.all('GrpZipCode').value;
                document.all('Phone').value= document.all('GrpPhone').value;

	 }      
	 else if(fm.CheckPostalAddress.value=="2") 
	 {      
	 	document.all('PostalAddress').value=document.all('HomeAddress').value;
                document.all('ZipCode').value=document.all('HomeZipCode').value;
                document.all('Phone').value= document.all('HomePhone').value;
	 }
	 else if(fm.CheckPostalAddress.value=="3") 
	 {
	 	document.all('PostalAddress').value="";
                document.all('ZipCode').value="";
                document.all('Phone').value= "";
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
    var ContNo=document.all("ContNo").value;
    if(ContNo!=null&&ContNo!="")
    {
        var strSQL ="select InsuredNo,Name,Sex,Birthday,RelationToMainInsured,SequenceNo from LCInsured where ContNo='"+ContNo+"'";
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
    var tContNo=document.all("ContNo").value;
    arrResult=easyExecSql("select * from LCInsured where ContNo='"+tContNo+"'",1,0);
    if(arrResult==null)
    {
      //alert("未得到被投保人信息");
        return;
    }
    else
    {
        DisplayInsured();//该合同下的被投保人信息
        var tCustomerNo = arrResult[0][2];		// 得到投保人客户号
        var tAddressNo = arrResult[0][10]; 		// 得到投保人地址号
        arrResult=easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+tCustomerNo+"'",1,0);
        if(arrResult==null)
        {
            //alert("未得到用户信息");
            //return;
        }
        else
        {
            //displayAppnt();       //显示投保人详细内容
            emptyUndefined();
            fm.AddressNo.value=tAddressNo;
            getdetailaddress();//显示投保人地址详细内容
        } 
    } 
    getInsuredPolInfo();
    getImpartInfo();
    getImpartDetailInfo();
}

/*********************************************************************
 *  MulLine的RadioBox点击事件，获得被保人详细信息，填入被保人信息表
 *  返回值：  无
 *********************************************************************
 */
function getInsuredDetail(parm1,parm2)
{
    var InsuredNo=document.all(parm1).all('InsuredGrid1').value;
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
    fm.AddressNo.value=tAddressNo;
    getdetailaddress();  	
    getInsuredPolInfo();
    getImpartInfo();
    getImpartDetailInfo();
}
/*********************************************************************
 *  把查询返回的客户数据显示到投保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function DisplayCustomer()
{
	try{document.all('Nationality').value= arrResult[0][8]; }catch(ex){}; 
	
}
/*********************************************************************
 *  把查询返回的客户地址数据显示到投保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function DisplayAddress()
{
	try{document.all('PostalAddress').value= arrResult[0][2]; }catch(ex){}; 
	try{document.all('ZipCode').value= arrResult[0][3]; }catch(ex){}; 
	try{document.all('Phone').value= arrResult[0][4]; }catch(ex){}; 
	try{document.all('Mobile').value= arrResult[0][14]; }catch(ex){}; 
	try{document.all('EMail').value= arrResult[0][16]; }catch(ex){}; 
	//try{document.all('GrpName').value= arrResult[0][2]; }catch(ex){}; 
	try{document.all('GrpPhone').value= arrResult[0][12]; }catch(ex){}; 
	try{document.all('CompanyAddress').value= arrResult[0][10]; }catch(ex){}; 
	try{document.all('GrpZipCode').value= arrResult[0][11]; }catch(ex){}; 
}
/*********************************************************************
 *  显示被保人详细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function DisplayInsured()
{
    try{document.all('GrpContNo').value=arrResult[0][0];}catch(ex){};            
    try{document.all('ContNo').value=arrResult[0][1];}catch(ex){};               
    try{document.all('InsuredNo').value=arrResult[0][2];}catch(ex){};            
    try{document.all('PrtNo').value=arrResult[0][3];}catch(ex){};                
    try{document.all('AppntNo').value=arrResult[0][4];}catch(ex){};              
    try{document.all('ManageCom').value=arrResult[0][5];}catch(ex){};            
    try{document.all('ExecuteCom').value=arrResult[0][6];}catch(ex){};           
    try{document.all('FamilyID').value=arrResult[0][7];}catch(ex){};             
    try{document.all('RelationToMainInsured').value=arrResult[0][8];}catch(ex){};
    try{document.all('RelationToAppnt').value=arrResult[0][9];}catch(ex){};      
    try{document.all('AddressNo').value=arrResult[0][10];}catch(ex){};           
    try{document.all('SequenceNo').value=arrResult[0][11];}catch(ex){};          
    try{document.all('Name').value=arrResult[0][12];}catch(ex){};                
    try{document.all('Sex').value=arrResult[0][13];}catch(ex){};                 
    try{document.all('Birthday').value=arrResult[0][14];}catch(ex){};            
    try{document.all('IDType').value=arrResult[0][15];}catch(ex){};              
    try{document.all('IDNo').value=arrResult[0][16];}catch(ex){};
    try{document.all('NativePlace').value=arrResult[0][17];}catch(ex){};
    try{document.all('Nationality').value=arrResult[0][18];}catch(ex){};                  
    try{document.all('RgtAddress').value=arrResult[0][19];}catch(ex){};          
    try{document.all('Marriage').value=arrResult[0][20];}catch(ex){};            
    try{document.all('MarriageDate').value=arrResult[0][21];}catch(ex){};        
    try{document.all('Health').value=arrResult[0][22];}catch(ex){};              
    try{document.all('Stature').value=arrResult[0][23];}catch(ex){};             
    try{document.all('Avoirdupois').value=arrResult[0][24];}catch(ex){};         
    try{document.all('Degree').value=arrResult[0][25];}catch(ex){};              
    try{document.all('CreditGrade').value=arrResult[0][26];}catch(ex){};         
    try{document.all('BankCode').value=arrResult[0][27];}catch(ex){};            
    try{document.all('BankAccNo').value=arrResult[0][28];}catch(ex){};           
    try{document.all('AccName').value=arrResult[0][29];}catch(ex){};             
    try{document.all('JoinCompanyDate').value=arrResult[0][30];}catch(ex){};     
    try{document.all('StartWorkDate').value=arrResult[0][31];}catch(ex){};       
    try{document.all('Position').value=arrResult[0][32];}catch(ex){};            
    try{document.all('Salary').value=arrResult[0][33];}catch(ex){};              
    try{document.all('OccupationType').value=arrResult[0][34];}catch(ex){};      
    try{document.all('OccupationCode').value=arrResult[0][35];}catch(ex){};      
    try{document.all('WorkType').value=arrResult[0][36];}catch(ex){};            
    try{document.all('PluralityType').value=arrResult[0][37];}catch(ex){};       
    try{document.all('SmokeFlag').value=arrResult[0][38];}catch(ex){}; 
    try{document.all('ContPlanCode').value=arrResult[0][39];}catch(ex){};          
    try{document.all('Operator').value=arrResult[0][40];}catch(ex){};            
    try{document.all('MakeDate').value=arrResult[0][41];}catch(ex){};            
    try{document.all('MakeTime').value=arrResult[0][42];}catch(ex){};            
    try{document.all('ModifyDate').value=arrResult[0][43];}catch(ex){};          
    try{document.all('ModifyTime').value=arrResult[0][44];}catch(ex){};   
}
function displayissameperson()
{
    try{document.all('InsuredNo').value= mSwitch.getVar( "AppntNo" ); }catch(ex){};         
    try{document.all('Name').value= mSwitch.getVar( "AppntName" ); }catch(ex){};          
    try{document.all('Sex').value= mSwitch.getVar( "AppntSex" ); }catch(ex){};           
    try{document.all('Birthday').value= mSwitch.getVar( "AppntBirthday" ); }catch(ex){};        
    try{document.all('IDType').value= mSwitch.getVar( "AppntIDType" ); }catch(ex){};        
    try{document.all('IDNo').value= mSwitch.getVar( "AppntIDNo" ); }catch(ex){};          
    try{document.all('Password').value= mSwitch.getVar( "AppntPassword" ); }catch(ex){};      
    try{document.all('NativePlace').value= mSwitch.getVar( "AppntNativePlace" ); }catch(ex){};   
    try{document.all('Nationality').value= mSwitch.getVar( "AppntNationality" ); }catch(ex){}; 
    try{document.all('AddressNo').value= mSwitch.getVar( "AddressNo" ); }catch(ex){};       
    try{document.all('RgtAddress').value= mSwitch.getVar( "AppntRgtAddress" ); }catch(ex){};    
    try{document.all('Marriage').value= mSwitch.getVar( "AppntMarriage" );}catch(ex){};      
    try{document.all('MarriageDate').value= mSwitch.getVar( "AppntMarriageDate" );}catch(ex){};  
    try{document.all('Health').value= mSwitch.getVar( "AppntHealth" );}catch(ex){};        
    try{document.all('Stature').value= mSwitch.getVar( "AppntStature" );}catch(ex){};       
    try{document.all('Avoirdupois').value= mSwitch.getVar( "AppntAvoirdupois" );}catch(ex){};   
    try{document.all('Degree').value= mSwitch.getVar( "AppntDegree" );}catch(ex){};        
    try{document.all('CreditGrade').value= mSwitch.getVar( "AppntDegreeCreditGrade" );}catch(ex){};   
    try{document.all('OthIDType').value= mSwitch.getVar( "AppntOthIDType" );}catch(ex){};     
    try{document.all('OthIDNo').value= mSwitch.getVar( "AppntOthIDNo" );}catch(ex){};       
    try{document.all('ICNo').value= mSwitch.getVar( "AppntICNo" );}catch(ex){};          
    try{document.all('GrpNo').value= mSwitch.getVar( "AppntGrpNo" );}catch(ex){};         
    try{document.all( 'JoinCompanyDate' ).value = mSwitch.getVar( "JoinCompanyDate" ); if(document.all( 'JoinCompanyDate' ).value=="false"){document.all( 'JoinCompanyDate' ).value="";} } catch(ex) { };
    try{document.all('StartWorkDate').value= mSwitch.getVar( "AppntStartWorkDate" );}catch(ex){}; 
    try{document.all('Position').value= mSwitch.getVar( "AppntPosition" );}catch(ex){};      
    try{document.all( 'Position' ).value = mSwitch.getVar( "Position" ); if(document.all( 'Position' ).value=="false"){document.all( 'Position' ).value="";} } catch(ex) { }; 
    try{document.all('Salary').value= mSwitch.getVar( "AppntSalary" );}catch(ex){};        
    try{document.all( 'Salary' ).value = mSwitch.getVar( "Salary" ); if(document.all( 'Salary' ).value=="false"){document.all( 'Salary' ).value="";} } catch(ex) { }; 
    try{document.all('OccupationType').value= mSwitch.getVar( "AppntOccupationType" );}catch(ex){};
    try{document.all('OccupationCode').value= mSwitch.getVar( "AppntOccupationCode" );}catch(ex){};
    try{document.all('WorkType').value= mSwitch.getVar( "AppntWorkType" );}catch(ex){};      
    try{document.all('PluralityType').value= mSwitch.getVar( "AppntPluralityType" );}catch(ex){}; 
    try{document.all('DeathDate').value= mSwitch.getVar( "AppntDeathDate" );}catch(ex){};     
    try{document.all('SmokeFlag').value= mSwitch.getVar( "AppntSmokeFlag" );}catch(ex){};     
    try{document.all('BlacklistFlag').value= mSwitch.getVar( "AppntBlacklistFlag" );}catch(ex){}; 
    try{document.all('Proterty').value= mSwitch.getVar( "AppntProterty" );}catch(ex){};      
    try{document.all('Remark').value= mSwitch.getVar( "AppntRemark" );}catch(ex){};        
    try{document.all('State').value= mSwitch.getVar( "AppntState" );}catch(ex){};         
    try{document.all('Operator').value= mSwitch.getVar( "AppntOperator" );}catch(ex){};      
    try{document.all('MakeDate').value= mSwitch.getVar( "AppntMakeDate" );}catch(ex){};      
    try{document.all('MakeTime').value= mSwitch.getVar( "AppntMakeTime" );}catch(ex){};      
    try{document.all('ModifyDate').value= mSwitch.getVar( "AppntModifyDate" );}catch(ex){};    
    try{document.all('ModifyTime').value= mSwitch.getVar( "AppntModifyTime" );}catch(ex){};    
    try{document.all('PostalAddress').value= mSwitch.getVar( "AppntPostalAddress" );}catch(ex){}; 
    try{document.all('PostalAddress').value= mSwitch.getVar( "AppntPostalAddress" );}catch(ex){};
    try{document.all('ZipCode').value= mSwitch.getVar( "AppntZipCode" );}catch(ex){}; 
    try{document.all('Phone').value= mSwitch.getVar( "AppntPhone" );}catch(ex){}; 
    try{document.all('Fax').value= mSwitch.getVar( "AppntFax" );}catch(ex){};     
    try{document.all('Mobile').value= mSwitch.getVar( "AppntMobile" );}catch(ex){}; 
    try{document.all('EMail').value= mSwitch.getVar( "AppntEMail" );}catch(ex){}; 
    try{document.all('GrpName').value= mSwitch.getVar( "AppntGrpName" );}catch(ex){}; 
    try{document.all('GrpPhone').value= mSwitch.getVar( "AppntGrpPhone" );}catch(ex){}; 
    try{document.all('GrpAddress').value= mSwitch.getVar( "CompanyAddress" );}catch(ex){}; 
    try{document.all('GrpZipCode').value= mSwitch.getVar( "AppntGrpZipCode" );}catch(ex){};     
    try{document.all('GrpFax').value= mSwitch.getVar( "AppntGrpFax" );}catch(ex){};     
    try{document.all('HomeAddress').value= mSwitch.getVar( "AppntHomeAddress" );}catch(ex){}; 
    try{document.all('HomePhone').value= mSwitch.getVar( "AppntHomePhone" );}catch(ex){}; 
    try{document.all('HomeZipCode').value= mSwitch.getVar( "AppntHomeZipCode" );}catch(ex){}; 
    try{document.all('HomeFax').value= mSwitch.getVar( "AppntHomeFax" );}catch(ex){};        	
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
    var InsuredNo=document.all("InsuredNo").value;
    var ContNo=document.all("ContNo").value;
    //告知信息初始化
    if(InsuredNo!=null&&InsuredNo!="")
    {
        var strSQL ="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNo='"+InsuredNo+"' and ProposalContNo='"+ContNo+"' and CustomerNoType='I'";
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
    var InsuredNo=document.all("InsuredNo").value;
    var ContNo=document.all("ContNo").value;
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
function info(){
	alert("---------------");
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
    var InsuredNo=document.all("InsuredNo").value;
    var ContNo=document.all("ContNo").value;
    //险种信息初始化
    if(InsuredNo!=null&&InsuredNo!="")
    {
        var strSQL ="select PolNo,RiskCode,Prem,Amnt from LCPol where InsuredNo='"+InsuredNo+"' and ContNo='"+ContNo+"'";
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
}
/*********************************************************************
 *  MulLine的RadioBox点击事件，获得被保人险种详细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */       
function getPolDetail(parm1,parm2)
{
    var PolNo=document.all(parm1).all('PolGrid1').value
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
        if (document.all('ContNo').value != "") 
        {
            alert("团单的个单不能有多被保险人");
            return false;
        }
        else 
            return true;
    }
    else
    {
        if (document.all('ContNo').value != ""&&fm.FamilyType.value=="0") 
        {
            var strSQL="select * from LCInsured where contno='"+document.all('ContNo').value +"'";
            turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
            if(turnPage.strQueryResult)
            {
                alert("个单不能有多被保险人");
                return false;
            }
            else
                return true;
        }
        else if (document.all('ContNo').value != ""&&fm.FamilyType.value=="1"&&InsuredGrid.mulLineCount>0&&fm.RelationToMainInsured.value=="00") 
        {
            alert("家庭单只能有一个主被保险人");
            return false;
        }                 
        else if (document.all('ContNo').value != ""&&fm.FamilyType.value=="1"&&fm.RelationToAppnt.value=="00") 
        {
            var strSql="select * from LCInsured where contno='"+document.all('ContNo').value +"' and RelationToAppnt='00' ";
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
      document.all('DivLCInsured').style.display = "";
      divLCInsuredPerson.style.display = "none";
      divSalary.style.display = "none";   
      fm.SamePersonFlag.checked = true;
      fm.RelationToAppnt.value="00" 
      displayissameperson();      
    }
    //对应是同一人，又打钩的情况
    else if (fm.SamePersonFlag.checked == true) 
    {
      document.all('DivLCInsured').style.display = "";
      divLCInsuredPerson.style.display = "none";  
      divSalary.style.display = "none";    
      displayissameperson();     
    }
    //对应不选同一人的情况
    else if (fm.SamePersonFlag.checked == false) 
    {
    document.all('DivLCInsured').style.display = "";
    divLCInsuredPerson.style.display = "none"; 
    divSalary.style.display = "none";     
    try{document.all('Name').value=""; }catch(ex){};          
    try{document.all('Sex').value= ""; }catch(ex){};           
    try{document.all('Birthday').value= ""; }catch(ex){};        
    try{document.all('IDType').value= "0"; }catch(ex){};        
    try{document.all('IDNo').value= ""; }catch(ex){};          
    try{document.all('Password').value= ""; }catch(ex){};      
    try{document.all('NativePlace').value= ""; }catch(ex){};   
    try{document.all('Nationality').value=""; }catch(ex){};   
    try{document.all('RgtAddress').value= ""; }catch(ex){};    
    try{document.all('Marriage').value= "";}catch(ex){};      
    try{document.all('MarriageDate').value= "";}catch(ex){};  
    try{document.all('Health').value= "";}catch(ex){};     
    try{document.all('Stature').value= "";}catch(ex){};       
    try{document.all('Avoirdupois').value= "";}catch(ex){};   
    try{document.all('Degree').value= "";}catch(ex){};        
    try{document.all('CreditGrade').value= "";}catch(ex){};   
    try{document.all('OthIDType').value= "";}catch(ex){};     
    try{document.all('OthIDNo').value= "";}catch(ex){};       
    try{document.all('ICNo').value="";}catch(ex){};          
    try{document.all('GrpNo').value= "";}catch(ex){};         
    try{document.all('JoinCompanyDate').value= "";}catch(ex){}
    try{document.all('StartWorkDate').value= "";}catch(ex){}; 
    try{document.all('Position').value= "";}catch(ex){};      
    try{document.all('Salary').value= "";}catch(ex){};        
    try{document.all('OccupationType').value= "";}catch(ex){};
    try{document.all('OccupationCode').value= "";}catch(ex){};
    try{document.all('WorkType').value= "";}catch(ex){};      
    try{document.all('PluralityType').value= "";}catch(ex){}; 
    try{document.all('DeathDate').value= "";}catch(ex){};     
    try{document.all('SmokeFlag').value= "";}catch(ex){};     
    try{document.all('BlacklistFlag').value= "";}catch(ex){}; 
    try{document.all('Proterty').value= "";}catch(ex){};      
    try{document.all('Remark').value= "";}catch(ex){};        
    try{document.all('State').value= "";}catch(ex){};         
    try{document.all('Operator').value= "";}catch(ex){};      
    try{document.all('MakeDate').value= "";}catch(ex){};      
    try{document.all('MakeTime').value="";}catch(ex){};      
    try{document.all('ModifyDate').value= "";}catch(ex){};    
    try{document.all('ModifyTime').value= "";}catch(ex){};    
    try{document.all('PostalAddress').value= "";}catch(ex){}; 
    try{document.all('PostalAddress').value= "";}catch(ex){};
    try{document.all('ZipCode').value= "";}catch(ex){}; 
    try{document.all('Phone').value= "";}catch(ex){}; 
    try{document.all('Mobile').value= "";}catch(ex){}; 
    try{document.all('EMail').value="";}catch(ex){}; 
    try{document.all('GrpName').value= "";}catch(ex){}; 
    try{document.all('GrpPhone').value= "";}catch(ex){}; 
    try{document.all('GrpAddress').value="";}catch(ex){}; 
    try{document.all('GrpZipCode').value= "";}catch(ex){};      
        
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
    if (document.all("InsuredNo").value == "") 
    {                    
      showAppnt1();                                                                                      
    } 
    else 
    {                                                                   
        //arrResult = easyExecSql("select a.*,b.AddressNo,b.PostalAddress,b.ZipCode,b.HomePhone,b.Mobile,b.EMail,a.GrpNo,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode from LDPerson a,LCAddress b where 1=1 and a.CustomerNo=b.CustomerNo and a.CustomerNo = '" + document.all("InsuredNo").value + "'", 1, 0);
        arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("InsuredNo").value + "'", 1, 0);
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
		showInfo = window.open( "../sys/LDPersonQueryNew.html" );               
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
    try{document.all('InsuredNo').value= arrResult[0][0]; }catch(ex){}; 
    try{document.all('Name').value= arrResult[0][1]; }catch(ex){}; 
    try{document.all('Sex').value= arrResult[0][2]; }catch(ex){}; 
    try{document.all('Birthday').value= arrResult[0][3]; }catch(ex){}; 
    try{document.all('IDType').value= arrResult[0][4]; }catch(ex){}; 
    try{document.all('IDNo').value= arrResult[0][5]; }catch(ex){}; 
    try{document.all('Password').value= arrResult[0][6]; }catch(ex){}; 
    try{document.all('NativePlace').value= arrResult[0][7]; }catch(ex){}; 
    try{document.all('Nationality').value= arrResult[0][8]; }catch(ex){}; 
    try{document.all('RgtAddress').value= arrResult[0][9]; }catch(ex){}; 
    try{document.all('Marriage').value= arrResult[0][10];}catch(ex){}; 
    try{document.all('MarriageDate').value= arrResult[0][11];}catch(ex){}; 
    try{document.all('Health').value= arrResult[0][12];}catch(ex){}; 
    try{document.all('Stature').value= arrResult[0][13];}catch(ex){}; 
    try{document.all('Avoirdupois').value= arrResult[0][14];}catch(ex){}; 
    try{document.all('Degree').value= arrResult[0][15];}catch(ex){}; 
    try{document.all('CreditGrade').value= arrResult[0][16];}catch(ex){}; 
    try{document.all('OthIDType').value= arrResult[0][17];}catch(ex){}; 
    try{document.all('OthIDNo').value= arrResult[0][18];}catch(ex){}; 
    try{document.all('ICNo').value= arrResult[0][19];}catch(ex){}; 
    try{document.all('GrpNo').value= arrResult[0][20];}catch(ex){}; 
    try{document.all('JoinCompanyDate').value= arrResult[0][21];}catch(ex){}; 
    try{document.all('StartWorkDate').value= arrResult[0][22];}catch(ex){}; 
    try{document.all('Position').value= arrResult[0][23];}catch(ex){}; 
    try{document.all('Salary').value= arrResult[0][24];}catch(ex){}; 
    try{document.all('OccupationType').value= arrResult[0][25];}catch(ex){}; 
    try{document.all('OccupationCode').value= arrResult[0][26];}catch(ex){}; 
    try{document.all('WorkType').value= arrResult[0][27];}catch(ex){}; 
    try{document.all('PluralityType').value= arrResult[0][28];}catch(ex){}; 
    try{document.all('DeathDate').value= arrResult[0][29];}catch(ex){}; 
    try{document.all('SmokeFlag').value= arrResult[0][30];}catch(ex){}; 
    try{document.all('BlacklistFlag').value= arrResult[0][31];}catch(ex){}; 
    try{document.all('Proterty').value= arrResult[0][32];}catch(ex){}; 
    try{document.all('Remark').value= arrResult[0][33];}catch(ex){}; 
    try{document.all('State').value= arrResult[0][34];}catch(ex){}; 
    try{document.all('Operator').value= arrResult[0][35];}catch(ex){}; 
    try{document.all('MakeDate').value= arrResult[0][36];}catch(ex){}; 
    try{document.all('MakeTime').value= arrResult[0][37];}catch(ex){}; 
    try{document.all('ModifyDate').value= arrResult[0][38];}catch(ex){}; 
    try{document.all('ModifyTime').value= arrResult[0][39];}catch(ex){}; 
    try{document.all('GrpName').value= arrResult[0][40];}catch(ex){}; 
    //地址显示部分的变动
    try{document.all('AddressNo').value= "";}catch(ex){}; 
    try{document.all('PostalAddress').value=  "";}catch(ex){}; 
    try{document.all('ZipCode').value=  "";}catch(ex){}; 
    try{document.all('Phone').value=  "";}catch(ex){}; 
    try{document.all('Mobile').value=  "";}catch(ex){}; 
    try{document.all('EMail').value=  "";}catch(ex){}; 
    //try{document.all('GrpName').value= arrResult[0][46];}catch(ex){}; 
    try{document.all('GrpPhone').value=  "";}catch(ex){}; 
    try{document.all('GrpAddress').value=  "";}catch(ex){}; 
    try{document.all('GrpZipCode').value=  "";}catch(ex){}; 
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
            document.all( 'ContNo' ).value = arrQueryResult[0][0];
            
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
  	    if(document.all('ProposalContNo').value == "") 
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
        if(document.all('ProposalContNo').value == "") 
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
    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
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

    var strSQL="select b.AddressNo,b.PostalAddress,b.ZipCode,b.Phone,b.Mobile,b.EMail,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode,b.homephone from LCAddress b where b.AddressNo='"+fm.AddressNo.value+"' and b.CustomerNo='"+fm.InsuredNo.value+"'";
    arrResult=easyExecSql(strSQL);
    try{document.all('AddressNo').value= arrResult[0][0];}catch(ex){}; 
    try{document.all('PostalAddress').value= arrResult[0][1];}catch(ex){}; 
    try{document.all('ZipCode').value= arrResult[0][2];}catch(ex){}; 
    try{document.all('Phone').value= arrResult[0][3];}catch(ex){}; 
    try{document.all('Mobile').value= arrResult[0][4];}catch(ex){}; 
    try{document.all('EMail').value= arrResult[0][5];}catch(ex){}; 
    //try{document.all('GrpName').value= arrResult[0][6];}catch(ex){}; 
    try{document.all('InsuredCompanyPhone').value= arrResult[0][6];}catch(ex){}; 
    try{document.all('GrpAddress').value= arrResult[0][7];}catch(ex){}; 
    try{document.all('GrpZipCode').value= arrResult[0][8];}catch(ex){}; 
    try{document.all('InsuredHomePhone').value= arrResult[0][9];}catch(ex){}; 

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
        divContPlan.style.display="none";   
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
	
	try{document.all('InsuredNo').value= ""; }catch(ex){}; 
	try{document.all('ExecuteCom').value= ""; }catch(ex){}; 
	try{document.all('FamilyID').value= ""; }catch(ex){}; 
	try{document.all('RelationToMainInsured').value= ""; }catch(ex){}; 
	try{document.all('RelationToAppnt').value= ""; }catch(ex){}; 
	try{document.all('AddressNo').value= ""; }catch(ex){}; 
	//try{document.all('SequenceNo').value= ""; }catch(ex){}; 
	try{document.all('Name').value= ""; }catch(ex){}; 
	try{document.all('Sex').value= ""; }catch(ex){}; 
	try{document.all('Birthday').value= ""; }catch(ex){}; 
	try{document.all('IDType').value= "0"; }catch(ex){}; 
	try{document.all('IDNo').value= ""; }catch(ex){}; 
	try{document.all('NativePlace').value= ""; }catch(ex){}; 
	try{document.all('Nationality').value= ""; }catch(ex){}; 
	try{document.all('RgtAddress').value= ""; }catch(ex){}; 
	try{document.all('Marriage').value= ""; }catch(ex){}; 
	try{document.all('MarriageDate').value= ""; }catch(ex){}; 
	try{document.all('Health').value= ""; }catch(ex){}; 
	try{document.all('Stature').value= ""; }catch(ex){}; 
	try{document.all('Avoirdupois').value= ""; }catch(ex){}; 
	try{document.all('Degree').value= ""; }catch(ex){}; 
	try{document.all('CreditGrade').value= ""; }catch(ex){}; 
	try{document.all('BankCode').value= ""; }catch(ex){}; 
	try{document.all('BankAccNo').value= ""; }catch(ex){}; 
	try{document.all('AccName').value= ""; }catch(ex){}; 
	try{document.all('JoinCompanyDate').value= ""; }catch(ex){}; 
	try{document.all('StartWorkDate').value= ""; }catch(ex){}; 
	try{document.all('Position').value= ""; }catch(ex){}; 
	try{document.all('Salary').value= ""; }catch(ex){}; 
	try{document.all('OccupationType').value= ""; }catch(ex){}; 
	try{document.all('OccupationCode').value= ""; }catch(ex){}; 
	try{document.all('WorkType').value= ""; }catch(ex){}; 
	try{document.all('PluralityType').value= ""; }catch(ex){}; 
	try{document.all('SmokeFlag').value= ""; }catch(ex){}; 
	try{document.all('ContPlanCode').value= ""; }catch(ex){}; 
        try{document.all('GrpName').value= ""; }catch(ex){}; 	
        try{document.all('HomeAddress').value= ""; }catch(ex){}; 	
        try{document.all('HomeZipCode').value= ""; }catch(ex){}; 	
        try{document.all('HomePhone').value= ""; }catch(ex){}; 	
        try{document.all('HomeFax').value= ""; }catch(ex){}; 	
        try{document.all('GrpFax').value= ""; }catch(ex){}; 
        try{document.all('Fax').value= ""; }catch(ex){}; 	                                       
	emptyAddress();
	ImpartGrid.clearData(); 
	ImpartGrid.addOne();
	ImpartDetailGrid.clearData(); 
	ImpartDetailGrid.addOne();	
}      

/*********************************************************************
 *  清空客户地址数据
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function emptyAddress()
{
	try{document.all('PostalAddress').value= "";  }catch(ex){}; 
	try{document.all('ZipCode').value= "";  }catch(ex){}; 
	try{document.all('Phone').value= "";  }catch(ex){}; 
	try{document.all('Mobile').value= "";  }catch(ex){}; 
	try{document.all('EMail').value= "";  }catch(ex){}; 
	//try{document.all('GrpName').value= arrResult[0][2]; }catch(ex){}; 
	try{document.all('GrpPhone').value= "";  }catch(ex){}; 
	try{document.all('GrpAddress').value= ""; }catch(ex){}; 
	try{document.all('GrpZipCode').value= "";  }catch(ex){}; 
}
/*********************************************************************
 *  根据身份证号取得出生日期和性别
 *  参数  ：  身份证号
 *  返回值：  无
 *********************************************************************
 */

function getBirthdaySexByIDNo(iIdNo)
{
	if(document.all('IDType').value=="0")
	{
		document.all('Birthday').value=getBirthdatByIdNo(iIdNo);
		document.all('Sex').value=getSexByIDNo(iIdNo);
	}	
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
	    					+" and lwmission.processid = '0000000004'"
	    					+" and lwmission.activityid = '0000002001'"
	    					+" and lwmission.missionprop1 = '"+fm.ProposalGrpContNo.value+"'";
	    turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
	    if (turnPage.strQueryResult) 
	    {
	        alert("该团单合同已经做过保存！");
	        return;
	    }
		if(document.all('ProposalGrpContNo').value == "") 
	    {
	        alert("团单合同信息未保存,不容许您进行 [录入完毕] 确认！");
	        return;
	    }
		fm.WorkFlowFlag.value = "6999999999";			//录入完毕
    }
    else if (wFlag ==2)//复核完毕确认
    {
        if(document.all('ProposalGrpContNo').value == "") 
	    {
	        alert("未查询出团单合同信息,不容许您进行 [复核完毕] 确认！");
	        return;
	    }
		fm.WorkFlowFlag.value = "0000002002";					//复核完毕
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;
	}
	else if (wFlag ==3)
    {
  	    if(document.all('ProposalGrpContNo').value == "") 
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
		if(document.all('ProposalGrpContNo').value == "") 
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
    document.all("AddressNo").CodeData=tCodeData;
}                  

function getImpartCode(parm1,parm2){
  //alert("hehe:"+document.all(parm1).all('ImpartGrid1').value);
  var impartVer=document.all(parm1).all('ImpartGrid1').value;	
  window.open("../appgrp/ImpartCodeSel.jsp?ImpartVer="+impartVer);
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
 		  window.open("../sys/LDPersonQueryAll.html?Name="+fm.Name.value+"&IDType="+fm.IDType.value+"&IDNo="+fm.IDNo.value,"newwindow","height=10,width=1090,top=180,left=180, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no,status=no");
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
	document.all('fmAction').value="DELETE||INSUREDRISK";
	fm.action="./DelIsuredRisk.jsp?polno="+tpolno;
	fm.submit(); //提交
	
}
function InsuredChk()
{
	var tSel =InsuredGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert("请先选择被保险人！");	
		return false;
	}
	var tRow = InsuredGrid.getSelNo() - 1;	
	var tInsuredNo=InsuredGrid.getRowColData(tRow,1);
	var tInsuredName=InsuredGrid.getRowColData(tRow,2);	
	var tInsuredSex=InsuredGrid.getRowColData(tRow,3);
	var tBirthday=InsuredGrid.getRowColData(tRow,4);		
        var sqlstr="select *from ldperson where Name='"+tInsuredName+"' and Sex='"+tInsuredSex+"' and Birthday='"+tBirthday+"' and CustomerNo<>'"+tInsuredNo+"'";
        arrResult = easyExecSql(sqlstr,1,0);	
        if(arrResult==null)
        {
	   alert("该没有与该被保人保人相似的客户,无需校验");
	   return false;
        }

	window.open("../uwgrp/InsuredChkMain.jsp?ProposalNo1="+fm.ContNo.value+"&InsuredNo="+tInsuredNo+"&Flag=I","window1");	
}
function getdetailaccount()
{
	if(fm.AccountNo.value=="1")
	{
           document.all('BankAccNo').value=mSwitch.getVar("AppntBankAccNo");
           document.all('BankCode').value=mSwitch.getVar("AppntBankCode");
           document.all('AccName').value=mSwitch.getVar("AppntAccName");		
	}
	if(fm.AccountNo.value=="2")
	{
           document.all('BankAccNo').value="";
           document.all('BankCode').value="";
           document.all('AccName').value="";			
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
  
       try{document.all('InsuredHomePhone').value= arrResult[0][2];}catch(ex){};  
       try{document.all('HomeAddress').value= arrResult[0][0];}catch(ex){}; 
       try{document.all('HomeZipCode').value= arrResult[0][1];}catch(ex){}; 

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