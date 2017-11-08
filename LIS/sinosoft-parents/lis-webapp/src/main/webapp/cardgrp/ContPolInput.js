//               该文件中包含客户端需要处理的函数和事件
var showInfo;
window.onfocus=myonfocus;
var arrResult;
var mDebug = "0";
var mOperate = "";
var mAction = "";
//window.onfocus=focuswrap;
var mSwitch = parent.VD.gVSwitch;
var mShowCustomerDetail = "GROUPPOL";
 var turnPage = new turnPageClass();   
 var cflag = "5";
 var mWFlag = 0 ;
 var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";
 var mySql = new SqlClass();
 function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();  
	  }
	  catch(ex)                               
	  {
	    showInfo=null;
	  }
	}
}

/*********************************************************************
 *  功能	：	判断需要录入的业务人员的人数
 *  参数  ：  无
 *  返回值：  boolean
 *********************************************************************
 */
 	//=================20060309====changing start=======================
 	
 	function IsMultiAgent()
 	{	 		
	//销售渠道为交叉销售即代码为3的时候，要求至少录入两名从业务人员信息，保存时在此首先做判断  
  var tSaleChnl = "";		//销售渠道
  tSalChnl = fm.all('AgentType').value;
  if(tSalChnl == "03"){
  	
  	//记录集团报单下面业务员的个数；
  	var tMulLineRowNum = ""; 
  	tMulLineRowNum = MultiAgentGrid.mulLineCount;
  	var tMulLineColNum = MultiAgentGrid.colCount;
  	//alert("tMulLineRowNum = " + tMulLineRowNum);
  	//alert("tMulLineColNum = " + tMulLineColNum);
  	
  	//业务员少于两个的情况
  	if(tMulLineRowNum - 1 < 0){
  		alert("在销售渠道为交叉销售的情况下，要求至少录入两名业务人员信息！！NoPCardContInput.js->IsMultiAgent()发生异常！");  	  		  	  	
  		//fm.all("multiagentflag").value = true; //modify by liuqh
  		//fm.all("multiagentflag").checked = true;
  		//DivMultiAgent.style.display="";  	    
  		return false;
  	} //end if
  	
  	//业务员大于等于两个时必须至少有两个不为空！
  	else{
  		for (var i = 0; i < tMulLineRowNum; i++){
  			var tAgentCode = MultiAgentGrid.getRowColData(i,1);
  			var tManageCom = MultiAgentGrid.getRowColData(i,3);
  			//alert("tAgentCode = " + tAgentCode);  			  	  		
  			//alert("tManageCom = " + tManageCom);
  			if(!(tAgentCode == "" && tManageCom == "")){  				
  				if(!verifyNotNull("业务员代码", tAgentCode)
  				  	&& verifyNotNull("所属机构sss", tManageCom)){
  					alert("业务员代码不能为空！");
  					//fm.all("multiagentflag").value = true; //modify by liuqh
  					//fm.all("multiagentflag").checked = true;
  					//DivMultiAgent.style.display=""; 
  					return false;
  					}
  				if(!verifyNotNull("所属机构", tManageCom)
  				   && verifyNotNull("业务员代码", tAgentCode)){
  				  alert("所属机构不能为空！");	
  				 // fm.all("multiagentflag").value = true;  //modify by liuqh
  				//	fm.all("multiagentflag").checked = true;
  					//DivMultiAgent.style.display=""; 
  				  return false;
  					}  			
  				break;
  				} // end if
  			}	// end for
  		//没有有效的从业务员信息		
  		if(i == tMulLineRowNum){
  			alert("在销售渠道为交叉销售的情况下，要求至少录入两名业务人员信息！！NoPCardContInput.js发生异常！");   			
  			//fm.all("multiagentflag").value = true;  //modify by liuqh
  			//fm.all("multiagentflag").checked = true;
  			//DivMultiAgent.style.display="";  	    		   	  			
  			return false;  			
  			} //end if		  		  			  	
  	} //end else
  } //end if  	
  return true;
}
	//===========changing end============================ 		

function checkSaleChnl(){
  var tAgentType = fm.AgentType.value;
  var tSecondAgentType = fm.SecondAgentType.value;
  if(tAgentType=="01"){
    if(tSecondAgentType==""){
     alert("请选择团险渠道下的二级销售渠道！");
     return false;
     }
    }
  return true;
}
/*********************************************************************
 *  保存集体投保单的提交
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function submitForm()
{	
   if( verifyInput2() == false ) return false;
   if(checkSaleChnl()==false) return false;
   //var sysdate="select to_char(sysdate,'YYYY-MM-DD') from dual";
   mySql = new SqlClass();
	mySql.setResourceName("cardgrp.ContPolInputSql");
	mySql.setSqlId("ContPolSql2"); 
   var arrQuery=easyExecSql(mySql.getString(),1,0);
   if(fm.CValiDate.value<arrQuery[0][0]&&fm.all('BackDateRemark').value=="")
   {
   	alert("保单的生效日期存在追溯情况，请在保单的日期追溯备注处注明追溯期间的理赔特别约定");
   	return false;
   }
   /*
 	if(fm.all('GrpNo').value != "" && fm.all('GrpNo').value.length >0 )	{
		var sqlstr="select grpname from ldgrp where customerno='"+fm.all('GrpNo').value+"'" ;
  	arrResult = easyExecSql(sqlstr,1,0);
  	var i=arrResult.length;
  	if (i!=1){
  		alert("投保人客户号有误!");
  		return;
  	}
  	if (arrResult[0][0]!=fm.all('GrpName').value){
  		alert("单位名称名称有误!");
  		return;
  	}
	
	}
	if(fm.all('GetFlag').value=="4" && fm.all('BankCode').value==""){
		alert("请输入银行转帐的银行和该银行的帐号");
		fm.all('BankCode').focus();
		return false;
		}
	*/


	//if(fm.all('AgentType').value=="04" || fm.all('AgentType').value=="06" || fm.all('AgentType').value=="07")
	//{
		
	//	if(fm.AgentCom.value==" " || fm.AgentCom.value=="")
	//	{
	//		alert("请选择中介结构！");
	//		return;
	//	}
	//}
	
	//当销售渠道为交叉销售时，需要录入至少两名业务人员
	if(!IsMultiAgent()){
		return false;
		}
	//反洗钱信息的录入校验  //modify by liuqh 2008-11-27
  //if(fm.all('ClientCare').value=="")
  //{
  //	alert ("请录入客户关注功能！");
  //	return false;
  //}
 // if(fm.all('ClientNeedJudge').value=="")
  //{
  //  alert ("请录入投保单购买的产品与客户表述需求是否一致！");
  //	return false;	
  //}
 // else{
  //	if(fm.all('ClientNeedJudge').value=="N")
	//    {
	//	     if(fm.all('FundReason').value=="")
   //          {
   //            alert ("客户购买的保单与其需求明显不符时，请录入是否解释清楚！");
  	//           return false;	
    //          }
	//    }
  //}  
  //if(fm.all('FundJudge').value=="")
  //{
  //  alert ("请录入资金是否来源于投保单位！");
  //	return false;	
  //}
 //if(fm.all('GrpAddressNo').value=="")
 //{
 //    alert("请录入投保单位的单位地址编码！");
 //    return false;
 //}
// if(fm.all('DonateContflag').value=="1")
 // {
 //   if(fm.all('ExamAndAppNo').value==""){
 //     alert ("该团单是赠送保单，请录入审批号码！");    
 // 	  return false;	
 //   }
 // }
 	if(!checkZT())
		return false;
		
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  

		//if((dateDiff(fm.all('PolApplyDate').value, fm.all('CValiDate').value, "D")<=0) && (dateDiff(fm.all('PolApplyDate').value, fm.all('CValiDate').value, "M")<=0)){
		//alert("投保申请日期应在保单生效日期之前");
		//fm.all('PolApplyDate').focus();
		//return false;
		//}  	
	if( mAction == "" )
	{		
		mAction = "INSERT";
		fm.all( 'fmAction' ).value = mAction;
		fm.all( 'LoadFlag' ).value = LoadFlag;
	    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

		  lockScreen('lkscreen');  
		  tAction = fm.action;		  
		  fm.action="../cardgrp/ContPolSave.jsp"
		  fm.submit(); //提交
	//	}
	}
}

function ifupdateSpec(){
   var tSel = SpecInfoGrid.getSelNo();
   if(tSel>0){
     if(confirm("是否修改所选特约内容？")==false){
        fm.SpecFlag.value = "NO";
     }else{
        fm.SpecFlag.value = "YES";
     }
    }
  return true; 
}
/*********************
//添加被保人
************************
*/
function getintopersoninsured()
{/////edit by yaory
	if (fm.ProposalGrpContNo.value=="")
	{
	    alert("必须保存合同信息才能进入〔被保人清单〕信息界面！");  
	    return false;  
	} 
	checkManageCom();
  delGrpVar();
	addGrpVar();
  //parent.fraInterface.window.location = "../cardgrp/ContGrpInsuredInput.jsp?LoadFlag="+LoadFlag+"&scantype="+scantype+"&prtNo="+prtNo+"&polNo="+polNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID;    
    
	//top.fraInterface.window.location = "../cardgrp/ContInsuredInput.jsp?LoadFlag="+LoadFlag+"&ContType=2"+"&scantype="+scantype+"&ProposalGrpContNo="+fm.ProposalGrpContNo.value+"&checktype=2&display=1";
  parent.fraInterface.window.location = "../cardgrp/ContInsuredInput.jsp?LoadFlag="+LoadFlag+"&ContType=2"+"&scantype="+scantype+"&ProposalGrpContNo="+fm.PrtNo.value+"&checktype=2&display=1&polNo="+polNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID; 
}
function ctrlclaimduty()
{////edit by Sujie
	  var tGrpContNo = fm.all('PrtNo').value ;
 	  if(tGrpContNo==null ||tGrpContNo=="")
	  {	  	
	    	alert("团单合同信息未保存，不容许〔添加险种〕！");
	    	return ;
	  }
    showInfo = window.open("../cardgrp/CtrlClaimDuty.jsp?ProposalGrpContNo="+tGrpContNo+"&GrpContNo="+fm.GrpContNo.value+"&LoadFlag="+LoadFlag,"",sFeatures);
    //parent.fraInterface.window.location = "../cardgrp/CtrlClaimDuty.jsp?scantype="+scantype+"&MissionID="+MissionID+"&ManageCom="+ManageCom+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&GrpContNo="+fm.GrpContNo.value+"&ProposalGrpContNo="+tGrpContNo+"&LoadFlag="+LoadFlag;
	}
/*********************

************************
*/
function grpfilllist()
{
	top.fraInterface.window.location = "../cardgrp/ContInsuredInput.jsp?LoadFlag=18&ContType=2&prtNo="+polNo+"&scantype="+scantype+"&vContNo="+vContNo+"&ProposalGrpContNo="+fm.ProposalGrpContNo.value+"&checktype=2&display=1";
	//top.fraInterface.window.location = "../cardgrp/ContInsuredInput.jsp?LoadFlag="+LoadFlag+"&ContType=2"+"&scantype="+scantype+"&ProposalGrpContNo="+fm.ProposalGrpContNo.value+"&checktype=2&display=1";
	//parent.fraInterface.window.location = "../cardgrp/ContInsuredInput.jsp?LoadFlag=9&ContType=2&scantype="+ scantype+"&checktype=2"+"&ScanFlag="+ScanFlag+"&oldContNo="+oldContNo;
}
/*********************************************************************
 *  保存个人投保单的提交后的操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	
	unlockScreen('lkscreen');  
	showInfo.close();  
	fm.StandbyFlag1.value="";
	//alert(mSwitch.getVar('MissionID'));
	var tid=mSwitch.getVar('MissionID');
	//return;
	//增加核保流向提示，如果超出自己的权限则提示用户。
	//var tsql="select activityid,missionprop12 From lwmission where missionid='"+tid+"'";
	mySql = new SqlClass();
	mySql.setResourceName("cardgrp.ContPolInputSql");
	mySql.setSqlId("ContPolSql3"); 
	mySql.addSubPara(tid); 
	var arrQueryResult = easyExecSql(mySql.getString(), 1, 0);
	if(arrQueryResult!=null && arrQueryResult[0][0]=="0000011004")
	{
		//tsql="select uwpopedom From lduwuser where usercode='"+tuser+"'";
		mySql = new SqlClass();
		mySql.setResourceName("cardgrp.ContPolInputSql");
		mySql.setSqlId("ContPolSql4"); 
		mySql.addSubPara(tuser); 
		var tpope= easyExecSql(mySql.getString(), 1, 0);
		if(tpope[0][0]< arrQueryResult[0][1])
		{
			alert("该单已超过您的核保权限!");
		}
	}
	/////////////////////////////
	if( FlagStr == "Fail" )
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
		content = "处理成功！";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

		
		showDiv(operateButton, "true"); 
		showDiv(inputButton, "false");
		
	}
    fillriskgrid();	
	mAction = ""; 
  if(this.ScanFlag == "1"){
    fm.PrtNo.value=prtNo;
    initRiskGrid();
    fillriskgrid();               
  } 
  if(mWFlag == 1 && FlagStr != "Fail")
  {
    window.location.href("./ContPolInput.jsp");
  }
  querySpec();
  //location.reload();  
  try{
  	fm.RiskCode.value="";
  	fm.RiskCodeName.value="";
  	fm.PayIntv.value="";
  	fm.PayIntvName.value="";
  	fm.DistriFlag.value="";
  	fm.StandbyFlag1.value="";
  	fm.BonusRate.value="0.0";
  	fm.FixprofitRate.value="0.0";
  	fm.ChargeFeeRate.value="-1";
  	fm.CommRate.value="-1";
  	//删除合同信息后印刷号和管理机构要保持不变
  	fm.PrtNo.value = prtNo;
  	//alert("fm.ManageCom.value:"+fm.ManageCom.value);
  	//fm.ManageCom.value = ManageCom;
  	fm.GrpSpec.value = '';
  	showCodeName();
  }catch(ex){}    
}

function afterSubmit1( FlagStr, content )
{
	unlockScreen('lkscreen');  
	showInfo.close();  
	if( FlagStr == "Fail" )
	{             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();


	}
	else
	{ 
		content = "处理成功！";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		
		showDiv(operateButton, "true"); 
		showDiv(inputButton, "false");
		top.close();
	}
	
	mAction = ""; 
  if(this.ScanFlag == "1"){
    fm.PrtNo.value=prtNo;
    initRiskGrid();
    fillriskgrid();               
  } 
  if(mWFlag == 1 && FlagStr != "Fail")
  {
    window.location.href("./ContPolInput.jsp");
  }
  //location.reload();      
}

/*********************************************************************
 *  "重置"按钮对应操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function resetForm()
{
	try
	{
		initForm();
		fm.all('PrtNo').value = prtNo;
	}
	catch( re )
	{
		alert("在GroupPolInput.js-->resetForm函数中发生异常:初始化界面错误!");
	}
} 

/*********************************************************************
 *  "取消"按钮对应操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function cancelForm()
{
	showDiv(operateButton,"true"); 
	showDiv(inputButton,"false"); 
}
 
/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}

/*********************************************************************
 *  Click事件，当点击增加图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function addClick()
{
		if(fm.all('GetFlag').value=="4" && fm.all('BankCode').value==""){
		alert("请输入银行转帐的银行和该银行的帐号");
		fm.all('BankCode').focus();
		return false;
		}	
	//if(dateDiff(fm.all('PolApplyDate').value, fm.all('CValiDate').value, "D")<0){
	//	alert("投保申请日期应在保单生效日期之前");
	//	fm.all('PolApplyDate').focus();
	//	return false;
	//	}			
//员工总人数=在职人数+退休人数+其它人员人数
		var intPeoples=parseInt(fm.all('Peoples').value);
		var intAppntOnWorkPeoples=parseInt(fm.all('AppntOnWorkPeoples').value);
		var intAppntOffWorkPeoples=parseInt(fm.all('AppntOffWorkPeoples').value);
		var intAppntOtherPeoples=parseInt(fm.all('AppntOtherPeoples').value );
		var intPeoples3=parseInt(fm.all('Peoples3').value );
   
  if (intPeoples!=intAppntOnWorkPeoples+intAppntOffWorkPeoples+intAppntOtherPeoples){
  	alert("员工总人数应该为"+(intAppntOnWorkPeoples+intAppntOffWorkPeoples+intAppntOtherPeoples));
  	fm.all('Peoples').focus();
  	return false;
  	}

//员工总人数应大于等于被保险人(成员)
  if (intPeoples<intPeoples3){
  	alert("员工总人数应大于等于被保险人");
  	fm.all('Peoples3').focus();
  	return false;
  	}		
	
	//下面增加相应的代码
	//showDiv( operateButton, "false" ); 
	showDiv( operateButton, "true" ); 
	showDiv( inputButton, "true" ); 
	
	fm.all('RiskCode').value = "";
	
	//保全调用会传2过来，否则默认为0，将付值于保单表中的appflag字段
  if (BQFlag=="2") {
    //var strSql = "select grppolno, grpno from lcgrppol where prtno='" + prtNo + "' and riskcode in (select riskcode from lmriskapp where subriskflag='M')";
    mySql = new SqlClass();
		mySql.setResourceName("cardgrp.ContPolInputSql");
		mySql.setSqlId("ContPolSql5"); 
		mySql.addSubPara(prtNo); 
    var arrResult = easyExecSql(mySql.getString());
    //alert(arrResult);
    
    mOperate = 1;
    afterQuery(arrResult);
    
    //strSql = "select GrpNo,GrpName,GrpAddress,Satrap from LDGrp where GrpNo='" + arrResult[0][1] + "'";
    //arrResult = easyExecSql(strSql);
    //mOperate = 2;
    //afterQuery(arrResult);
    
    fm.all('RiskCode').value = BQRiskCode;
    fm.all('RiskCode').className = "readonly";
    fm.all('RiskCode').readOnly = true;
    fm.all('RiskCode').ondblclick = "";
  }
	
	fm.all('ContNo').value = "";
	fm.all('ProposalGrpContNo').value = "";
}           

/*********************************************************************
 *  Click事件，当点击“查询”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryClick()
{ 
       if(this.ScanFlag == "1"){
	  alert( "有扫描件录入不允许查询!" );
          return false;	  
        }
	if( mOperate == 0 )
	{		
		mOperate = 1;
		//cContNo = fm.all( 'ContNo' ).value;
		showInfo = window.open("./GroupPolQueryMain.jsp","",sFeatures);
	}
} 


/*********************************************************************
 *  Click事件，当点击“修改”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function updateClick()
{
	if(!checkZT())
		return false;
   //var sysdate="select to_char(sysdate,'YYYY-MM-DD') from dual";
   mySql = new SqlClass();
		mySql.setResourceName("cardgrp.ContPolInputSql");
		mySql.setSqlId("ContPolSql6");  
   var arrQuery=easyExecSql(mySql.getString(),1,0);
   if(fm.CValiDate.value<arrQuery[0][0]&&fm.BackDateRemark.value=="")
   {
   	alert("保单的生效日期存在追溯情况，请在保单的日期追溯备注处注明追溯期间的理赔特别约定");
   	return false;
   }
	if(fm.all('GetFlag').value=="4" && fm.all('BankCode').value==""){
		alert("请输入银行转帐的银行和该银行的帐号");
		fm.all('BankCode').focus();
		return false;
		}	
		
	//if(dateDiff(fm.all('PolApplyDate').value, fm.all('CValiDate').value, "D")<0){
	//	alert("投保申请日期应在保单生效日期之前");
	//	fm.all('PolApplyDate').focus();
	//	return false;
	//	}		
  //当销售渠道为交叉销售时，需要录入至少两名业务人员
	if(!IsMultiAgent()){
		return false;
		}
	//反洗钱信息的录入校验
  //if(fm.all('ClientCare').value=="")
  //{
  //	alert ("请录入客户关注功能！");
  //	return false;
  //}
  //if(fm.all('ClientNeedJudge').value=="")
  //{
  //  alert ("请录入投保单购买的产品与客户表述需求是否一致！");
  //	return false;	
  //}
  //else{
  //	if(fm.all('ClientNeedJudge').value=="N")
	//    {
	//	     if(fm.all('FundReason').value=="")
   //          {
   //            alert ("客户购买的保单与其需求明显不符时，请录入是否解释清楚！");
  	//           return false;	
   //           }
	//    }
  //}  
  //if(fm.all('FundJudge').value=="")
  //{
  //  alert ("请录入资金是否来源于投保单位！");
  //	return false;	
  //}		
  // if(fm.all('DonateContflag').value=="1")
  //{
  //  if(fm.all('ExamAndAppNo').value==""){
  //    alert ("该团单是赠送保单，请录入审批号码！");    
  //	  return false;	
  //  }
  //}
  //判断是否修改所选特约内容
    ifupdateSpec();
	var tProposalGrpContNo = "";
	tProposalGrpContNo = fm.all( 'ProposalGrpContNo' ).value;
	  if( verifyInput2() == false ) return false;
	  if(checkSaleChnl()==false) return false;
	//  ImpartGrid.delBlankLine(); 
	if( tProposalGrpContNo == null || tProposalGrpContNo == "" )
	      if(this.ScanFlag == "1"){
	      	alert( "还未录入数据,请先增加合同信息,再进行修改!" );
	       }
	       else 
	       {
		alert( "请先做投保单查询操作，再进行修改!" );
	       }
	else
	{
		var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		
		if( mAction == "" )
		{
			//showSubmitFrame(mDebug);
			//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			lockScreen('lkscreen');  
			mAction = "UPDATE";
			fm.all( 'fmAction' ).value = mAction;
			fm.action="../cardgrp/ContPolSave.jsp"
			fm.submit(); //提交
		}
	}
}           

/*********************************************************************
 *  Click事件，当点击“删除”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function deleteClick()
{
	var tProposalGrpContNo = "";
	tProposalGrpContNo = fm.all( 'ProposalGrpContNo' ).value;
	if( tProposalGrpContNo == null || tProposalGrpContNo == "" )
		alert( "请先做投保单查询操作，再进行删除!" );
	else
	{
	  if (confirm("您确定要删除该团单吗？"))
	  {
		var showStr = "正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		if(confirm("是否删除该团体合同下的所有特约内容？")){
		  fm.SpecFlag.value ="YES";
		}else{
		  fm.SpecFlag.value = "NO";
		}
		if( mAction == "" )
		{
			lockScreen('lkscreen');  
			//showSubmitFrame(mDebug);
			//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			mAction = "DELETE";
			fm.all( 'fmAction' ).value = mAction;
			fm.action="../cardgrp/ContPolSave.jsp"
			fm.submit(); //提交
		}
	 }
     }
}           

/*********************************************************************
 *  显示div
 *  参数  ：  第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
 *  返回值：  无
 *********************************************************************
 */
function showDiv(cDiv,cShow)
{
	if( cShow == "true" )
		cDiv.style.display = "";
	else
		cDiv.style.display = "none";  
}

/*********************************************************************
 *  当点击“进入个人信息”按钮时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function intoPol()
{
	//下面增加相应的代码
	tProposalGrpContNo = fm.ProposalGrpContNo.value;
	if( tProposalGrpContNo == "" )
	{
		alert("您必须先录入集体信息才能进入个人信息部分。");
		return false
	}

	//把集体信息放入内存
	mSwitch = parent.VD.gVSwitch;  //桢容错
	putGrpPol();
	
	try { goToPic(2) } catch(e) {}
	
	try {
	  parent.fraInterface.window.location = "./ProposalGrpInput.jsp?LoadFlag=" + LoadFlag + "&type=" + type;
  }
  catch (e) {
    parent.fraInterface.window.location = "./ProposalGrpInput.jsp?LoadFlag=2&type=" + type;
  }
}           

/*********************************************************************
 *  把集体信息放入内存
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function putGrpPol()
{
	delGrpPolVar();
	addIntoGrpPol();
}

/*********************************************************************
 *  把集体信息放入加到变量中
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function addIntoGrpPol()
{
	try { mSwitch.addVar( "intoPolFlag", "", "GROUPPOL" ); } catch(ex) { };
	// body信息
	try { mSwitch.addVar( "BODY", "", window.document.body.innerHTML ); } catch(ex) { };
	// 集体信息
	//由"./AutoCreatLDGrpInit.jsp"自动生成
  try { mSwitch.addVar('GrpNo', '', fm.all('GrpNo').value); } catch(ex) { };
  try { mSwitch.addVar('PrtNo', '', fm.all('PrtNo').value); } catch(ex) { };
  try { mSwitch.addVar('Password', '', fm.all('Password').value); } catch(ex) { };
  try { mSwitch.addVar('GrpName', '', fm.all('GrpName').value); } catch(ex) { };
  try { mSwitch.addVar('GrpAddressCode', '', fm.all('GrpAddressCode').value); } catch(ex) { };
  try { mSwitch.addVar('GrpAddress', '', fm.all('GrpAddress').value); } catch(ex) { };
  try { mSwitch.addVar('GrpZipCode', '', fm.all('GrpZipCode').value); } catch(ex) { };
  try { mSwitch.addVar('BusinessType', '', fm.all('BusinessType').value); } catch(ex) { };
  try { mSwitch.addVar('GrpNature', '', fm.all('GrpNature').value); } catch(ex) { };
  try { mSwitch.addVar('Peoples', '', fm.all('Peoples').value); } catch(ex) { };
  try { mSwitch.addVar('RgtMoney', '', fm.all('RgtMoney').value); } catch(ex) { };
  try { mSwitch.addVar('Asset', '', fm.all('Asset').value); } catch(ex) { };
  try { mSwitch.addVar('NetProfitRate', '', fm.all('NetProfitRate').value); } catch(ex) { };
  try { mSwitch.addVar('MainBussiness', '', fm.all('MainBussiness').value); } catch(ex) { };
  try { mSwitch.addVar('Corporation', '', fm.all('Corporation').value); } catch(ex) { };
  try { mSwitch.addVar('ComAera', '', fm.all('ComAera').value); } catch(ex) { };
  try { mSwitch.addVar('LinkMan1', '', fm.all('LinkMan1').value); } catch(ex) { };
  try { mSwitch.addVar('Department1', '', fm.all('Department1').value); } catch(ex) { };
  try { mSwitch.addVar('HeadShip1', '', fm.all('HeadShip1').value); } catch(ex) { };
  try { mSwitch.addVar('Phone1', '', fm.all('Phone1').value); } catch(ex) { };
  try { mSwitch.addVar('E_Mail1', '', fm.all('E_Mail1').value); } catch(ex) { };
  try { mSwitch.addVar('Fax1', '', fm.all('Fax1').value); } catch(ex) { };
  try { mSwitch.addVar('LinkMan2', '', fm.all('LinkMan2').value); } catch(ex) { };
  try { mSwitch.addVar('Department2', '', fm.all('Department2').value); } catch(ex) { };
  try { mSwitch.addVar('HeadShip2', '', fm.all('HeadShip2').value); } catch(ex) { };
  try { mSwitch.addVar('Phone2', '', fm.all('Phone2').value); } catch(ex) { };
  try { mSwitch.addVar('E_Mail2', '', fm.all('E_Mail2').value); } catch(ex) { };
  try { mSwitch.addVar('Fax2', '', fm.all('Fax2').value); } catch(ex) { };
  try { mSwitch.addVar('Fax', '', fm.all('Fax').value); } catch(ex) { };
  try { mSwitch.addVar('Phone', '', fm.all('Phone').value); } catch(ex) { };
  try { mSwitch.addVar('GetFlag', '', fm.all('GetFlag').value); } catch(ex) { };
  try { mSwitch.addVar('Satrap', '', fm.all('Satrap').value); } catch(ex) { };
  try { mSwitch.addVar('EMail', '', fm.all('EMail').value); } catch(ex) { };
  try { mSwitch.addVar('FoundDate', '', fm.all('FoundDate').value); } catch(ex) { };
  try { mSwitch.addVar('AppntOnWorkPeoples', '', fm.all('AppntOnWorkPeoples').value); } catch(ex) { };
  try { mSwitch.addVar('AppntOffWorkPeoples', '', fm.all('AppntOffWorkPeoples').value); } catch(ex) { };
  try { mSwitch.addVar('AppntOtherPeoples', '', fm.all('AppntOtherPeoples').value); } catch(ex) { };      
  try { mSwitch.addVar('BankAccNo', '', fm.all('BankAccNo').value); } catch(ex) { };
  try { mSwitch.addVar('BankCode', '', fm.all('BankCode').value); } catch(ex) { };
  try { mSwitch.addVar('GrpGroupNo', '', fm.all('GrpGroupNo').value); } catch(ex) { };
  try { mSwitch.addVar('State', '', fm.all('State').value); } catch(ex) { };
  try { mSwitch.addVar('BlacklistFlag', '', fm.all('BlacklistFlag').value); } catch(ex) { };
  try { mSwitch.addVar('Currency', '', fm.all('Currency').value); } catch(ex) { };

  try { mSwitch.addVar( "ContNo", "", fm.all( 'ContNo' ).value ); } catch(ex) { };
  try { mSwitch.addVar( "ProposalGrpContNo", "", fm.all( 'ProposalGrpContNo' ).value ); } catch(ex) { };
  try { mSwitch.addVar( "ManageCom", "", fm.all( 'ManageCom' ).value ); } catch(ex) { };
  try { mSwitch.addVar( "SaleChnl", "", fm.all( 'SaleChnl' ).value ); } catch(ex) { };
  try { mSwitch.addVar( "AgentCom", "", fm.all( 'AgentCom' ).value ); } catch(ex) { };
  try { mSwitch.addVar( "AgentCode", "", fm.all( 'AgentCode' ).value ); } catch(ex) { };
  try { mSwitch.addVar( "AgentGroup", "", fm.all( 'AgentGroup' ).value ); } catch(ex) { };
  try { mSwitch.addVar( "AgentCode1", "", fm.all( 'AgentCode1' ).value ); } catch(ex) { };

  try { mSwitch.addVar( "RiskCode", "", fm.all( 'RiskCode' ).value ); } catch(ex) { };
  try { mSwitch.addVar( "RiskVersion", "", fm.all( 'RiskVersion' ).value ); } catch(ex) { };
  try { mSwitch.addVar( "CValiDate", "", fm.all( 'CValiDate' ).value ); } catch(ex) { };
  try { mSwitch.addVar( "PolApplyDate", "", fm.all( 'PolApplyDate' ).value ); } catch(ex) { };
  try { mSwitch.addVar('StandbyFlag1', '', fm.all('StandbyFlag1').value); } catch(ex) { };
  try { mSwitch.addVar('StandbyFlag2', '', fm.all('StandbyFlag2').value); } catch(ex) { };
  try { mSwitch.addVar('StandbyFlag3', '', fm.all('StandbyFlag3').value); } catch(ex) { };
  //------20060217---changing start----------------------------
  try { mSwitch.addVar('AgentType', '', fm.all('AgentType').value); } catch(ex) { };
  //-------------changing end------------------------------------

}

/*********************************************************************
 *  把集体信息从变量中删除
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function delGrpPolVar()
{
	try { mSwitch.deleteVar( "intoPolFlag" ); } catch(ex) { };
	// body信息
	try { mSwitch.deleteVar( "BODY" ); } catch(ex) { };
	// 集体信息
	//由"./AutoCreatLDGrpInit.jsp"自动生成
  try { mSwitch.deleteVar('GrpNo'); } catch(ex) { };
  try { mSwitch.deleteVar('PrtNo'); } catch(ex) { };
  try { mSwitch.deleteVar('Password'); } catch(ex) { };
  try { mSwitch.deleteVar('GrpName'); } catch(ex) { };
  try { mSwitch.deleteVar('GrpAddressCode'); } catch(ex) { };
  try { mSwitch.deleteVar('GrpAddress'); } catch(ex) { };
  try { mSwitch.deleteVar('GrpZipCode'); } catch(ex) { };
  try { mSwitch.deleteVar('BusinessType'); } catch(ex) { };
  try { mSwitch.deleteVar('GrpNature'); } catch(ex) { };
  try { mSwitch.deleteVar('Peoples'); } catch(ex) { };
  try { mSwitch.deleteVar('RgtMoney'); } catch(ex) { };
  try { mSwitch.deleteVar('Asset'); } catch(ex) { };
  try { mSwitch.deleteVar('NetProfitRate'); } catch(ex) { };
  try { mSwitch.deleteVar('MainBussiness'); } catch(ex) { };
  try { mSwitch.deleteVar('Corporation'); } catch(ex) { };
  try { mSwitch.deleteVar('ComAera'); } catch(ex) { };
  try { mSwitch.deleteVar('LinkMan1'); } catch(ex) { };
  try { mSwitch.deleteVar('Department1'); } catch(ex) { };
  try { mSwitch.deleteVar('HeadShip1'); } catch(ex) { };
  try { mSwitch.deleteVar('Phone1'); } catch(ex) { };
  try { mSwitch.deleteVar('E_Mail1'); } catch(ex) { };
  try { mSwitch.deleteVar('Fax1'); } catch(ex) { };
  try { mSwitch.deleteVar('LinkMan2'); } catch(ex) { };
  try { mSwitch.deleteVar('Department2'); } catch(ex) { };
  try { mSwitch.deleteVar('HeadShip2'); } catch(ex) { };
  try { mSwitch.deleteVar('Phone2'); } catch(ex) { };
  try { mSwitch.deleteVar('E_Mail2'); } catch(ex) { };
  try { mSwitch.deleteVar('Fax2'); } catch(ex) { };
  try { mSwitch.deleteVar('Fax'); } catch(ex) { };
  try { mSwitch.deleteVar('Phone'); } catch(ex) { };
  try { mSwitch.deleteVar('GetFlag'); } catch(ex) { };
  try { mSwitch.deleteVar('Satrap'); } catch(ex) { };
  try { mSwitch.deleteVar('EMail'); } catch(ex) { };
  try { mSwitch.deleteVar('FoundDate'); } catch(ex) { };
  try { mSwitch.deleteVar('AppntOnWorkPeoples'); } catch(ex) { };
  try { mSwitch.deleteVar('AppntOffWorkPeoples'); } catch(ex) { };
  try { mSwitch.deleteVar('AppntOtherPeoples'); } catch(ex) { };      
  try { mSwitch.deleteVar('BankAccNo'); } catch(ex) { };
  try { mSwitch.deleteVar('BankCode'); } catch(ex) { };
  try { mSwitch.deleteVar('GrpGroupNo'); } catch(ex) { };
  try { mSwitch.deleteVar('State'); } catch(ex) { };
  try { mSwitch.deleteVar('BlacklistFlag'); } catch(ex) { };
  try { mSwitch.deleteVar('Currency'); } catch(ex) { };
  //-------20060217------changint start--------------------------
  try { mSwitch.deleteVar('AgentType'); } catch(ex) { };
  //------------changing end-------------------------------------

	mSwitch.deleteVar( "ContNo" );
	mSwitch.deleteVar( "ProposalGrpContNo" );
	mSwitch.deleteVar( "ManageCom" );
	mSwitch.deleteVar( "SaleChnl" );
	mSwitch.deleteVar( "AgentCom" );
	mSwitch.deleteVar( "AgentCode" );
	mSwitch.deleteVar( "AgentCode1" );
	
	mSwitch.deleteVar( "RiskCode" );
	mSwitch.deleteVar( "RiskVersion" );
	mSwitch.deleteVar( "CValiDate" );

}

/*********************************************************************
 *  Click事件，当双击“投保单位客户号”录入框时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showAppnt1()
{
	if( mOperate == 0 )
	{
		mOperate = 2;
		showInfo = window.open( "../sys/GroupMain.html" ,"",sFeatures);
	}
}           
function showAppnt()
{
 if (fm.all("GrpNo").value == "" ) { 
 	showAppnt1();
 }
 else
 {
 	//arrResult = easyExecSql("select b.CustomerNo,b.GrpName,b.BusinessType,b.GrpNature,b.Peoples,b.RgtMoney,b.Asset,b.NetProfitRate,b.MainBussiness,b.Corporation,b.ComAera,b.Fax,b.Phone,b.FoundDate,b.OnWorkPeoples,b.OffWorkPeoples,b.OtherPeoples from LDGrp b where  b.CustomerNo='" + fm.all("GrpNo").value + "'", 1, 0);			
	 mySql = new SqlClass();
		mySql.setResourceName("cardgrp.ContPolInputSql");
		mySql.setSqlId("ContPolSql7");  
		mySql.addSubPara(fm.all("GrpNo").value  ); 
		arrResult = easyExecSql(mySql.getString(), 1, 0);			
	 if (arrResult == null) {
            alert("未查到投保单位信息");
	  } 
	  else {
	    displayAddress(arrResult[0]);
	  }
 }
}

/*********************************************************************
 *  查询返回明细信息时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
 *  参数  ：  查询返回的二维数组
 *  返回值：  无
 *********************************************************************
 */
function afterQuery( arrQueryResult ) {
  //alert("here:" + arrQueryResult + "\n" + mOperate);
// alert("1");
//alert(arrQueryResult);
	if( arrQueryResult != null ) {
		//alert("ok");
		arrResult = arrQueryResult;
   //alert(mOperate);
		if( mOperate == 1 )	{		// 查询集体投保单	
			//alert("ok");
			fm.all( 'GrpContNo' ).value = arrQueryResult[0][0];
			//arrResult = easyExecSql("select * from LCGrpCont where GrpContNo = '" + arrQueryResult[0][0] + "'", 1, 0);                        
			mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql8");  
			mySql.addSubPara( arrQueryResult[0][0] ); 
			arrResult = easyExecSql(mySql.getString(), 1, 0);	
			if (arrResult == null) {
			  //alert("未查到团单信息");
			} else {
			   displayLCGrpCont(arrResult[0]);
//  alert("2");
			   var tgrpcontno=arrResult[0][0];
			   fillriskgrid();	   
//  alert("3");
			  // arrResult = easyExecSql("select a.* from LCGrpAddress a where a.AddressNo=(select AddressNo from LCGrpAppnt  where GrpContNo = '" + tgrpcontno + "') and a.CustomerNo=(select CustomerNo from LCGrpAppnt  where GrpContNo = '" + arrResult[0][0] + "')", 1, 0);
			   mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql9");  
			mySql.addSubPara(tgrpcontno); 
			mySql.addSubPara(arrResult[0][0]); 
			arrResult = easyExecSql(mySql.getString(), 1, 0);	
			   if (arrResult == null) {  
			       alert("未查到投保单位地址信息");
			   
			   }else {
			       displayAddress1(arrResult[0]);
//  alert("3");
			   }
			  // arrResult = easyExecSql("select b.GrpName,b.BusinessType,b.GrpNature,b.Peoples,b.RgtMoney,b.Asset,b.NetProfitRate,b.MainBussiness,b.Corporation,b.ComAera,b.Fax,b.Phone,b.FoundDate,b.OnWorkPeoples,b.OffWorkPeoples,b.OtherPeoples from LDGrp b where b.CustomerNo=(select CustomerNo from LCGrpAppnt  where GrpContNo = '" + tgrpcontno + "')",1,0);
			   mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql10");  
			mySql.addSubPara(tgrpcontno);  
			arrResult = easyExecSql(mySql.getString(), 1, 0);
			    if (arrResult == null) {  
			       alert("未查到投保单位信息");
			   
			   }else {
			       displayAddress2(arrResult[0]);
//  alert("4");
			   }
			  // arrResult = easyExecSql("select c.Name,c.PostalAddress,c.ZipCode,c.Phone from  LCGrpAppnt c where c.GrpContNo = '" + tgrpcontno + "'",1,0);
			     mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql11");  
			mySql.addSubPara(tgrpcontno);  
			arrResult = easyExecSql(mySql.getString(), 1, 0);
			    if (arrResult != null) {  
			       displayAddress3(arrResult[0]);
//  alert("5");
			   }			   			   
//////////////////////////////////////////////////////////////
//modifyed by wood 改之前的sql对以总公司登录时查询无法显示其他业务员信息
         var ttt;
		 var muliAgentSQL;
		  if(ManageCom!="%"&&ManageCom.length>0){
		   ttt = ManageCom.substring(0,2);
		   /* muliAgentSQL="select c.agentcode,d.name,d.managecom,b.name,c.busirate,a.agentgroup,b.branchattr "+ 
                          "from LCGrpCont a,labranchgroup b,lacommisiondetail c,laagent d "+ 
                          "where 1=1 "+
                          "and a.GrpContNo='" + arrQueryResult[0][0] + "' " +  
                          "and b.agentgroup=c.agentgroup " +
                          "and c.agentcode!=a.agentcode " +
                          "and d.agentcode=c.agentcode " +
                          "and d.agentcode!=a.agentcode " +
                          "and c.grpcontno=a.GrpContNo " ;*/
            mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql12");  
			mySql.addSubPara(arrQueryResult[0][0]);                 
		  }else{
		  	//ttt=ManageCom;
			/*muliAgentSQL="select c.agentcode,d.name,d.managecom,b.name,c.busirate,a.agentgroup,b.branchattr "+ 
                          "from LCGrpCont a,labranchgroup b,lacommisiondetail c,laagent d "+ 
                          "where 1=1 "+
                          "and a.GrpContNo='" + arrQueryResult[0][0] + "' " +  
                          "and b.agentgroup=c.agentgroup " +
                          "and c.agentcode!=a.agentcode " +
                          "and d.agentcode=c.agentcode " +
                          "and d.agentcode!=a.agentcode " +
                          "and c.grpcontno=a.GrpContNo " ;*/
            mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql13");  
			mySql.addSubPara(arrQueryResult[0][0]);                
			}
         // alert(ttt);
         //查询已有多业务员信息
  
                          ;
         //alert("--muliAgentSQL="+muliAgentSQL);
         //fm.Asset.value=muliAgentSQL;
         turnPage.strQueryResult = easyQueryVer3(mySql.getString(), 1, 0, 1);
         
         //alert("--turnPage.strQueryResult=="+turnPage.strQueryResult);
         //判断是否查询成功,成功则显示告知信息
         if (turnPage.strQueryResult) {
           //查询成功则拆分字符串，返回二维数组
           turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
           //设置初始化过的MULTILINE对象
           turnPage.pageDisplayGrid = MultiAgentGrid;    
           //保存SQL语句
           turnPage.strQuerySql = muliAgentSQL; 
           //设置查询起始位置
           turnPage.pageIndex = 0;  
           //在查询结果数组中取出符合页面显示大小设置的数组
           arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
           //调用MULTILINE对象显示查询结果
           displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
           //显示多业务员
           //alert("1");
           displayMultiAgent();
           //alert("2");
         }
         else{
           //初始化多业务员列表
           //alert("saafas");
           initMultiAgentGrid();
         }
         //alert("here!arrQueryResult[0][0]=="+arrQueryResult[0][0]);
         //查询主业务员信息
         /*arrResult=easyExecSql("select c.agentcode,d.name,d.managecom,b.name,c.busirate,a.agentgroup,b.branchattr " + 
                               "from LCGrpCont a,labranchgroup b,lacommisiondetail c,laagent d " +
                               "where 1=1 " +
                               "and a.GrpContNo='" + arrQueryResult[0][0] + "' "+
                               "and b.agentgroup=c.agentgroup " +
                               "and c.agentcode=a.agentcode " +
                               "and d.agentcode=a.agentcode " +
                               "and c.grpcontno=a.GrpContNo",1,0);*/
         mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql14");  
			mySql.addSubPara(arrQueryResult[0][0]);                         
         arrResult=easyExecSql(mySql.getString(),1,0);
         if(arrResult==null){
           //alert("未得到主业务员信息");
           //return;
         }else{
           displayMainAgent();       //显示主业务员的详细内容
         }
         

/////////////////////////////////////////////////////////////
			}    
		}
		
		if( mOperate == 2 )	{		// 投保单位信息
		  //arrResult = easyExecSql("select b.CustomerNo,b.GrpName,b.BusinessType,b.GrpNature,b.Peoples,b.RgtMoney,b.Asset,b.NetProfitRate,b.MainBussiness,b.Corporation,b.ComAera,b.Fax,b.Phone,b.FoundDate,b.OnWorkPeoples,b.OffWorkPeoples,b.OtherPeoples from LDGrp b where  b.CustomerNo='" + arrQueryResult[0][0] + "'", 1, 0);
			mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql15");  
			mySql.addSubPara(arrQueryResult[0][0]);  
			  arrResult=easyExecSql(mySql.getString(),1,0);
			if (arrResult == null) {
			  alert("未查到投保单位信息");
			} else {
			   displayAddress(arrResult[0]);
			}
		}
	}
	//turnPage.queryModal("select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where GrpContNo='" + arrQueryResult[0][0] + "'",ImpartGrid);	
	//turnPage.queryModal("select InsuYear,InsuYearFlag,InsuContent,Rate,EnsureContent,Peoples,RecompensePeoples,OccurMoney,RecompenseMoney,PendingMoney,SerialNo from LCHistoryImpart where GrpContNo='"+ arrQueryResult[0][0] + "'",HistoryImpartGrid);
	//turnPage.queryModal("select OcurTime,DiseaseName,DiseasePepoles,CureMoney,Remark,SerialNo from LCDiseaseImpart where GrpContNo='"+ arrQueryResult[0][0] + "'",DiseaseGrid);
	//客户需求服务
	//turnPage.queryModal("select ServKind,ServDetail,ServChoose,ServRemark from LCGrpServInfo where GrpContNo='" + arrQueryResult[0][0] + "'",ServInfoGrid);	

	mOperate = 0;		// 恢复初态
}
/*********************************************************************
 *  把查询返回的客户地址数据返回
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayAddress()
{ 
try {fm.all('GrpNo').value= ""; } catch(ex) { };
try {fm.all('CustomerNo').value= ""; } catch(ex) { };
try {fm.all('GrpAddressNo').value= ""; } catch(ex) { };
try {fm.all('GrpAddress').value= ""; } catch(ex) { };
try {fm.all('GrpZipCode').value= ""; } catch(ex) { };
try {fm.all('LinkMan1').value= ""; } catch(ex) { };
try {fm.all('Department1').value=""; } catch(ex) { };
try {fm.all('HeadShip1').value= ""; } catch(ex) { };
try {fm.all('Phone1').value= ""; } catch(ex) { };
try {fm.all('E_Mail1').value= ""; } catch(ex) { };
try {fm.all('Fax1').value= ""; } catch(ex) { };
try {fm.all('LinkMan2').value= ""; } catch(ex) { };
try {fm.all('Department2').value= ""; } catch(ex) { };
try {fm.all('HeadShip2').value= ""; } catch(ex) { };
try {fm.all('Phone2').value= ""; } catch(ex) { };
try {fm.all('E_Mail2').value= ""; } catch(ex) { };
try {fm.all('Fax2').value= ""; } catch(ex) { };
try {fm.all('Operator').value= ""; } catch(ex) { };
try {fm.all('MakeDate').value= ""; } catch(ex) { };
try {fm.all('MakeTime').value= ""; } catch(ex) { };
try {fm.all('ModifyDate').value= ""; } catch(ex) { };
try {fm.all('ModifyTime').value= ""; } catch(ex) { };
//以下是ldgrp表
try {fm.all('GrpNo').value= arrResult[0][0]; } catch(ex) { };  
try {fm.all('GrpName').value= arrResult[0][1];   } catch(ex) { };     
try {fm.all('BusinessType').value= arrResult[0][2];} catch(ex) { };        
try {fm.all('GrpNature').value= arrResult[0][3]; } catch(ex) { };       
try {fm.all('Peoples').value= arrResult[0][4]; } catch(ex) { };       
try {fm.all('RgtMoney').value= arrResult[0][5]; } catch(ex) { };       
try {fm.all('Asset').value= arrResult[0][6]; } catch(ex) { };       
try {fm.all('NetProfitRate').value= arrResult[0][7];} catch(ex) { };        
try {fm.all('MainBussiness').value= arrResult[0][8];} catch(ex) { };        
try {fm.all('Corporation').value= arrResult[0][9];  } catch(ex) { };      
try {fm.all('ComAera').value= arrResult[0][10]; } catch(ex) { };  
try {fm.all('Fax').value= arrResult[0][11]; } catch(ex) { };  
try {fm.all('Phone').value= arrResult[0][12]; } catch(ex) { };  
try {fm.all('FoundDate').value= arrResult[0][13]; } catch(ex) { };  
try {fm.all('AppntOnWorkPeoples').value= arrResult[0][14]; } catch(ex) { };
try {fm.all('AppntOffWorkPeoples').value= arrResult[0][15]; } catch(ex) { };
try {fm.all('AppntOtherPeoples').value= arrResult[0][16]; } catch(ex) { };           
}
function displayAddress1()
{
try {fm.all('GrpNo').value= arrResult[0][0]; } catch(ex) { };
try {fm.all('CustomerNo').value= arrResult[0][0]; } catch(ex) { };
try {fm.all('GrpAddressNo').value= arrResult[0][1]; } catch(ex) { };
try {fm.all('GrpAddress').value= arrResult[0][2]; } catch(ex) { };
try {fm.all('GrpZipCode').value= arrResult[0][3]; } catch(ex) { };
try {fm.all('LinkMan1').value= arrResult[0][4]; } catch(ex) { };
try {fm.all('Department1').value= arrResult[0][5]; } catch(ex) { };
try {fm.all('HeadShip1').value= arrResult[0][6]; } catch(ex) { };
try {fm.all('Phone1').value= arrResult[0][7]; } catch(ex) { };
try {fm.all('E_Mail1').value= arrResult[0][8]; } catch(ex) { };
try {fm.all('Fax1').value= arrResult[0][9]; } catch(ex) { };
try {fm.all('LinkMan2').value= arrResult[0][10]; } catch(ex) { };
try {fm.all('Department2').value= arrResult[0][11]; } catch(ex) { };
try {fm.all('HeadShip2').value= arrResult[0][12]; } catch(ex) { };
try {fm.all('Phone2').value= arrResult[0][13]; } catch(ex) { };
try {fm.all('E_Mail2').value= arrResult[0][14]; } catch(ex) { };
try {fm.all('Fax2').value= arrResult[0][15]; } catch(ex) { };
try {fm.all('Operator').value= arrResult[0][16]; } catch(ex) { };
try {fm.all('MakeDate').value= arrResult[0][17]; } catch(ex) { };
try {fm.all('MakeTime').value= arrResult[0][18]; } catch(ex) { };
try {fm.all('ModifyDate').value= arrResult[0][19]; } catch(ex) { };
try {fm.all('ModifyTime').value= arrResult[0][20]; } catch(ex) { };

          
}

function displayAddress2()
{
//以下是ldgrp表
try {fm.all('GrpName').value= arrResult[0][0];} catch(ex) { };   
//try {fm.all('BusinessType').value= arrResult[0][1];} catch(ex) { };        
try {fm.all('GrpNature').value= arrResult[0][2]; } catch(ex) { };       
try {fm.all('Peoples').value= arrResult[0][3]; } catch(ex) { };       
try {fm.all('RgtMoney').value= arrResult[0][4]; } catch(ex) { };       
try {fm.all('Asset').value= arrResult[0][5]; } catch(ex) { };       
try {fm.all('NetProfitRate').value= arrResult[0][6];} catch(ex) { };        
try {fm.all('MainBussiness').value= arrResult[0][7];} catch(ex) { };        
try {fm.all('Corporation').value= arrResult[0][8];  } catch(ex) { };      
try {fm.all('ComAera').value= arrResult[0][9]; } catch(ex) { };  
try {fm.all('Fax').value= arrResult[0][10]; } catch(ex) { };  
try {fm.all('Phone').value= arrResult[0][11]; } catch(ex) { };  
try {fm.all('FoundDate').value= arrResult[0][12]; } catch(ex) { };
try {fm.all('AppntOnWorkPeoples').value= arrResult[0][13]; } catch(ex) { };
try {fm.all('AppntOffWorkPeoples').value= arrResult[0][14]; } catch(ex) { };
try {fm.all('AppntOtherPeoples').value= arrResult[0][15]; } catch(ex) { };     
}
function displayAddress3()
{
try {fm.all('GrpName').value= arrResult[0][0];} catch(ex) { };   
try {fm.all('GrpAddress').value= arrResult[0][1]; } catch(ex) { };
try {fm.all('GrpZipCode').value= arrResult[0][2]; } catch(ex) { };
try {fm.all('Phone').value= arrResult[0][3]; } catch(ex) { };	
}		
/*********************************************************************
 *  把查询返回的客户数据显示到投保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayAppnt()
{
  //由"./AutoCreatLDGrpInit.jsp"自动生成
  try { fm.all('GrpNo').value = arrResult[0][0]; } catch(ex) { };
  try { fm.all('Password').value = arrResult[0][1]; } catch(ex) { };
  try { fm.all('GrpName').value = arrResult[0][2]; } catch(ex) { };
  try { fm.all('GrpAddressCode').value = arrResult[0][3]; } catch(ex) { };
  try { fm.all('GrpAddress').value = arrResult[0][4]; } catch(ex) { };
  try { fm.all('GrpZipCode').value = arrResult[0][5]; } catch(ex) { };
  try { fm.all('GrpNature').value = arrResult[0][7]; } catch(ex) { };
  try { fm.all('Peoples').value = arrResult[0][8]; } catch(ex) { };
  try { fm.all('RgtMoney').value = arrResult[0][9]; } catch(ex) { };
  try { fm.all('Asset').value = arrResult[0][10]; } catch(ex) { };
  try { fm.all('NetProfitRate').value = arrResult[0][11]; } catch(ex) { };
  try { fm.all('MainBussiness').value = arrResult[0][12]; } catch(ex) { };
  try { fm.all('Corporation').value = arrResult[0][13]; } catch(ex) { };
  try { fm.all('ComAera').value = arrResult[0][14]; } catch(ex) { };
  try { fm.all('LinkMan1').value = arrResult[0][15]; } catch(ex) { };
  try { fm.all('Department1').value = arrResult[0][16]; } catch(ex) { };
  try { fm.all('HeadShip1').value = arrResult[0][17]; } catch(ex) { };
  try { fm.all('Phone1').value = arrResult[0][18]; } catch(ex) { };
  try { fm.all('E_Mail1').value = arrResult[0][19]; } catch(ex) { };
  try { fm.all('Fax1').value = arrResult[0][20]; } catch(ex) { };
  try { fm.all('LinkMan2').value = arrResult[0][21]; } catch(ex) { };
  try { fm.all('Department2').value = arrResult[0][22]; } catch(ex) { };
  try { fm.all('HeadShip2').value = arrResult[0][23]; } catch(ex) { };
  try { fm.all('Phone2').value = arrResult[0][24]; } catch(ex) { };
  try { fm.all('E_Mail2').value = arrResult[0][25]; } catch(ex) { };
  try { fm.all('Fax2').value = arrResult[0][26]; } catch(ex) { };
  try { fm.all('Fax').value = arrResult[0][27]; } catch(ex) { };
  try { fm.all('Phone').value = arrResult[0][28]; } catch(ex) { };
  try { fm.all('GetFlag').value = arrResult[0][29]; } catch(ex) { };
  try { fm.all('Satrap').value = arrResult[0][30]; } catch(ex) { };
  try { fm.all('EMail').value = arrResult[0][31]; } catch(ex) { };
  try { fm.all('FoundDate').value = arrResult[0][32]; } catch(ex) { };
  try { fm.all('BankAccNo').value = arrResult[0][33]; } catch(ex) { };
  try { fm.all('BankCode').value = arrResult[0][34]; } catch(ex) { };
  try { fm.all('GrpGroupNo').value = arrResult[0][35]; } catch(ex) { };
  try { fm.all('State').value = arrResult[0][36]; } catch(ex) { };
  try { fm.all('Remark').value = arrResult[0][37]; } catch(ex) { };
  try { fm.all('BlacklistFlag').value = arrResult[0][38]; } catch(ex) { };
  try { fm.all('Operator').value = arrResult[0][39]; } catch(ex) { };
  try { fm.all('MakeDate').value = arrResult[0][40]; } catch(ex) { };
  try { fm.all('MakeTime').value = arrResult[0][41]; } catch(ex) { };
  try { fm.all('ModifyDate').value = arrResult[0][42]; } catch(ex) { };
  try { fm.all('ModifyTime').value = arrResult[0][43]; } catch(ex) { };
  try { fm.all('FIELDNUM').value = arrResult[0][44]; } catch(ex) { };
  try { fm.all('PK').value = arrResult[0][45]; } catch(ex) { };
  try { fm.all('fDate').value = arrResult[0][46]; } catch(ex) { };
  try { fm.all('mErrors').value = arrResult[0][47]; } catch(ex) { };
}
function displayLCGrpCont() {
  try {fm.all('GrpContNo').value= arrResult[0][0];} catch(ex) { }; 
  try {fm.all('ProposalGrpContNo').value= arrResult[0][1];} catch(ex) { }; 
  try {fm.all('PrtNo').value= arrResult[0][2];} catch(ex) { }; 
  try {fm.all('SaleChnl').value= arrResult[0][3];} catch(ex) { }; 
  try {fm.all('SecondAgentType').value= arrResult[0][6];} catch(ex) { }; 
  try {fm.all('ManageCom').value= arrResult[0][4];} catch(ex) { }; 
   try {fm.all('AgentManageCom').value= arrResult[0][4];} catch(ex) { }; 
  //alert(fm.all('ManageCom').value);
  try {fm.all('AgentCom').value= arrResult[0][5];} catch(ex) { }; 
  try {fm.all('AgentType').value= arrResult[0][3];} catch(ex) { }; 
  try {fm.all('AgentCode').value= arrResult[0][7];} catch(ex) { }; 
  try {fm.all('AgentGroup').value= arrResult[0][8];} catch(ex) { }; 
  try {fm.all('AgentCode1').value= arrResult[0][9];} catch(ex) { }; 
  try {fm.all('Password').value= arrResult[0][10];} catch(ex) { }; 
  try {fm.all('Password2').value= arrResult[0][11];} catch(ex) { }; 
  try {fm.all('AppntNo').value= arrResult[0][12];} catch(ex) { }; 
  try {fm.all('GrpAddressNo').value= arrResult[0][13];} catch(ex) { }; 
  try {fm.all('Peoples2').value= arrResult[0][14];} catch(ex) { }; 
  try {fm.all('GrpName').value= arrResult[0][15];} catch(ex) { }; 
  try {fm.all('BusinessType').value= arrResult[0][16];} catch(ex) { }; 
  try {fm.all('GrpNature').value= arrResult[0][17];} catch(ex) { }; 
  try {fm.all('RgtMoney').value= arrResult[0][18];} catch(ex) { }; 
  try {fm.all('Asset').value= arrResult[0][19];} catch(ex) { }; 
  try {fm.all('NetProfitRate').value= arrResult[0][20];} catch(ex) { }; 
  try {fm.all('MainBussiness').value= arrResult[0][21];} catch(ex) { }; 
  try {fm.all('Corporation').value= arrResult[0][22];} catch(ex) { }; 
  try {fm.all('ComAera').value= arrResult[0][23];} catch(ex) { }; 
  try {fm.all('Fax').value= arrResult[0][24];} catch(ex) { }; 
  try {fm.all('Phone').value= arrResult[0][25];} catch(ex) { }; 
  try {fm.all('GetFlag').value= arrResult[0][26];} catch(ex) { }; 
  try {fm.all('Satrap').value= arrResult[0][27];} catch(ex) { }; 
  try {fm.all('EMail').value= arrResult[0][28];} catch(ex) { }; 
  try {fm.all('FoundDate').value= arrResult[0][29];} catch(ex) { }; 
  try {fm.all('GrpGroupNo').value= arrResult[0][30];} catch(ex) { }; 
  try {fm.all('BankCode').value= arrResult[0][31];} catch(ex) { }; 
  try {fm.all('BankAccNo').value= arrResult[0][32];} catch(ex) { }; 
  try {fm.all('AccName').value= arrResult[0][33];} catch(ex) { }; 
  try {fm.all('DisputedFlag ').value= arrResult[0][34];} catch(ex) { }; 
  try {fm.all('OutPayFlag').value= arrResult[0][35];} catch(ex) { }; 
  try {fm.all('GetPolMode').value= arrResult[0][36];} catch(ex) { }; 
  try {fm.all('Lang').value= arrResult[0][37];} catch(ex) { }; 
  try {fm.all('Currency').value= arrResult[0][38];} catch(ex) { }; 
  try {fm.all('LostTimes').value= arrResult[0][39];} catch(ex) { }; 
  try {fm.all('PrintCount').value= arrResult[0][40];} catch(ex) { }; 
  try {fm.all('RegetDate').value= arrResult[0][41];} catch(ex) { }; 
  try {fm.all('LastEdorDate').value= arrResult[0][42];} catch(ex) { }; 
  try {fm.all('LastGetDate').value= arrResult[0][43];} catch(ex) { }; 
  try {fm.all('LastLoanDate').value= arrResult[0][44];} catch(ex) { }; 
  try {fm.all('SpecFlag').value= arrResult[0][45];} catch(ex) { }; 
  //try {fm.all('GrpSpec').value= arrResult[0][46];} catch(ex) { }; 
  try {fm.all('PayMode').value= arrResult[0][47];} catch(ex) { }; 
  try {fm.all('SignCom').value= arrResult[0][48];} catch(ex) { }; 
  try {fm.all('SignDate').value= arrResult[0][49];} catch(ex) { }; 
  try {fm.all('SignTime').value= arrResult[0][50];} catch(ex) { }; 
  try {fm.all('CValiDate').value= arrResult[0][51];} catch(ex) { }; 
  //try {fm.all('PayIntv').value= arrResult[0][52];} catch(ex) { }; 
  try {fm.all('ManageFeeRate').value= arrResult[0][53];} catch(ex) { }; 
  try {fm.all('ExpPeoples').value= arrResult[0][54];} catch(ex) { }; 
  try {fm.all('ExpPremium').value= arrResult[0][55];} catch(ex) { }; 
  try {fm.all('ExpAmnt').value= arrResult[0][56];} catch(ex) { }; 
  try {fm.all('Peoples').value= arrResult[0][57];} catch(ex) { }; 
  try {fm.all('Mult').value= arrResult[0][58];} catch(ex) { }; 
  try {fm.all('Prem').value= arrResult[0][59];} catch(ex) { }; 
  try {fm.all('Amnt').value= arrResult[0][60];} catch(ex) { }; 
  try {fm.all('SumPrem').value= arrResult[0][61];} catch(ex) { }; 
  try {fm.all('SumPay').value= arrResult[0][62];} catch(ex) { }; 
  try {fm.all('Dif').value= arrResult[0][63];} catch(ex) { }; 
  try {fm.all('Remark').value= arrResult[0][64];} catch(ex) { }; 
  try {fm.all('StandbyFlag1').value= arrResult[0][65];} catch(ex) { }; 
  try {fm.all('StandbyFlag2').value= arrResult[0][66];} catch(ex) { }; 
  try {fm.all('StandbyFlag3').value= arrResult[0][67];} catch(ex) { }; 
  try {fm.all('InputOperator').value= arrResult[0][68];} catch(ex) { }; 
  try {fm.all('InputDate').value= arrResult[0][69];} catch(ex) { }; 
  try {fm.all('InputTime').value= arrResult[0][70];} catch(ex) { }; 
  try {fm.all('ApproveFlag').value= arrResult[0][71];} catch(ex) { }; 
  try {fm.all('ApproveCode').value= arrResult[0][72];} catch(ex) { }; 
  try {fm.all('ApproveDate').value= arrResult[0][73];} catch(ex) { }; 
  try {fm.all('ApproveTime').value= arrResult[0][74];} catch(ex) { }; 
  try {fm.all('UWOperator').value= arrResult[0][75];} catch(ex) { }; 
  try {fm.all('UWFlag').value= arrResult[0][76];} catch(ex) { }; 
  try {fm.all('UWDate').value= arrResult[0][77];} catch(ex) { }; 
  try {fm.all('UWTime').value= arrResult[0][78];} catch(ex) { }; 
  try {fm.all('AppFlag').value= arrResult[0][79];} catch(ex) { }; 
  try {fm.all('PolApplyDate').value= arrResult[0][80];} catch(ex) { }; 
  try {fm.all('CustomGetPolDate').value= arrResult[0][81];} catch(ex) { }; 
  try {fm.all('GetPolDate').value= arrResult[0][82];} catch(ex) { }; 
  try {fm.all('GetPolTime').value= arrResult[0][83];} catch(ex) { }; 
  try {fm.all('State').value= arrResult[0][84];} catch(ex) { }; 
  try {fm.all('Operator').value= arrResult[0][85];} catch(ex) { }; 
  try {fm.all('MakeDate').value= arrResult[0][86];} catch(ex) { }; 
  try {fm.all('MakeTime').value= arrResult[0][87];} catch(ex) { }; 
  try {fm.all('ModifyDate').value= arrResult[0][88];} catch(ex) { }; 
  try {fm.all('ModifyTime').value= arrResult[0][89];} catch(ex) { };
  try {fm.all('EnterKind').value= arrResult[0][90]; } catch(ex) { };
  try {fm.all('AmntGrade').value= arrResult[0][91]; } catch(ex) { };
  try {fm.all('Peoples3').value= arrResult[0][92]; } catch(ex) { }; 
  try {fm.all('OnWorkPeoples').value= arrResult[0][93]; } catch(ex) { };     
  try {fm.all('OffWorkPeoples').value= arrResult[0][94]; } catch(ex) { };    
  try {fm.all('OtherPeoples').value= arrResult[0][95]; } catch(ex) { };      
  try {fm.all('RelaPeoples').value= arrResult[0][96]; } catch(ex) { };       
  try {fm.all('RelaMatePeoples').value= arrResult[0][97]; } catch(ex) { };   
  try {fm.all('RelaYoungPeoples').value= arrResult[0][98]; } catch(ex) { };  	
  try {fm.all('RelaOtherPeoples').value= arrResult[0][99]; } catch(ex) { };  
  	
  try {fm.all('ReportNo').value= arrResult[0][113];  } catch(ex) { };
//  try {alert(arrResult[0][114])                   ;  } catch(ex) { };
  try {fm.all('ConferNo').value= arrResult[0][114]; } catch(ex) { };
  try {fm.all('SignReportNo').value= arrResult[0][115]; } catch(ex) { };
  try {fm.all('AgentConferNo').value= arrResult[0][116]; } catch(ex) { };
  
  //try {fm.all('ClientCare').value= arrResult[0][118]; } catch(ex) { };
  //try {fm.all('ClientNeedJudge').value= arrResult[0][119]; } catch(ex) { };
  //try {fm.all('FundJudge').value= arrResult[0][120]; } catch(ex) { };
  //try {fm.all('FundReason').value= arrResult[0][121]; } catch(ex) { };
  
  if (arrResult[0][122]==null||arrResult[0][122]=="")
  {
    //try {fm.all('EdorCalType').value= "N"; } catch(ex) { }; //默认为N
  }else
  	{
      //try {fm.all('EdorCalType').value= arrResult[0][122]; } catch(ex) { };  		
  	}
  try {fm.all('BackDateRemark').value= arrResult[0][121]; } catch(ex) { };  
   if (arrResult[0][124]==null||arrResult[0][124]=="")
   {
   	 //try {fm.all('DonateContflag').value= "0"; } catch(ex) { }; //默认为0
   }else{
     //try {fm.all('DonateContflag').value= arrResult[0][124]; } catch(ex) { }; 
   }
  //try {fm.all('ExamAndAppNo').value= arrResult[0][125]; } catch(ex) { }; 
  
}
function displayLCGrpPol() {
  //由"./AutoCreatLCGrpPolInit.jsp"自动生成
  try { fm.all('ContNo').value = arrResult[0][0]; } catch(ex) { };
  try { fm.all('ProposalGrpContNo').value = arrResult[0][1]; } catch(ex) { };
  try { fm.all('PrtNo').value = arrResult[0][2]; } catch(ex) { };
  try { fm.all('KindCode').value = arrResult[0][3]; } catch(ex) { };
  try { fm.all('RiskCode').value = arrResult[0][5]; } catch(ex) { };
  try { fm.all('RiskVersion').value = arrResult[0][6]; } catch(ex) { };
  try { fm.all('SignCom').value = arrResult[0][7]; } catch(ex) { };
  try { fm.all('ManageCom').value = arrResult[0][8]; } catch(ex) { };
  //alert(fm.all('ManageCom').value );
  try { fm.all('AgentCom').value = arrResult[0][9]; } catch(ex) { };
  try { fm.all('AgentType').value = arrResult[0][10]; } catch(ex) { };
  try { fm.all('SaleChnl').value = arrResult[0][11]; } catch(ex) { };
  try { fm.all('Password').value = arrResult[0][12]; } catch(ex) { };
  try { fm.all('GrpNo').value = arrResult[0][13]; } catch(ex) { };
  try { fm.all('Password2').value = arrResult[0][14]; } catch(ex) { };
  try { fm.all('GrpName').value = arrResult[0][15]; } catch(ex) { };
  try { fm.all('GrpAddressCode').value = arrResult[0][16]; } catch(ex) { };
  try { fm.all('GrpAddress').value = arrResult[0][17]; } catch(ex) { };
  try { fm.all('GrpZipCode').value = arrResult[0][18]; } catch(ex) { };
  try { fm.all('BusinessType').value = arrResult[0][19]; } catch(ex) { };
  try { fm.all('GrpNature').value = arrResult[0][20]; } catch(ex) { };
  try { fm.all('Peoples2').value = arrResult[0][21]; } catch(ex) { };
  try { fm.all('RgtMoney').value = arrResult[0][22]; } catch(ex) { };
  try { fm.all('Asset').value = arrResult[0][23]; } catch(ex) { };
  try { fm.all('NetProfitRate').value = arrResult[0][24]; } catch(ex) { };
  try { fm.all('MainBussiness').value = arrResult[0][25]; } catch(ex) { };
  try { fm.all('Corporation').value = arrResult[0][26]; } catch(ex) { };
  try { fm.all('ComAera').value = arrResult[0][27]; } catch(ex) { };
  try { fm.all('LinkMan1').value = arrResult[0][28]; } catch(ex) { };
  try { fm.all('Department1').value = arrResult[0][29]; } catch(ex) { };
  try { fm.all('HeadShip1').value = arrResult[0][30]; } catch(ex) { };
  try { fm.all('Phone1').value = arrResult[0][31]; } catch(ex) { };
  try { fm.all('E_Mail1').value = arrResult[0][32]; } catch(ex) { };
  try { fm.all('Fax1').value = arrResult[0][33]; } catch(ex) { };
  try { fm.all('LinkMan2').value = arrResult[0][34]; } catch(ex) { };
  try { fm.all('Department2').value = arrResult[0][35]; } catch(ex) { };
  try { fm.all('HeadShip2').value = arrResult[0][36]; } catch(ex) { };
  try { fm.all('Phone2').value = arrResult[0][37]; } catch(ex) { };
  try { fm.all('E_Mail2').value = arrResult[0][38]; } catch(ex) { };
  try { fm.all('Fax2').value = arrResult[0][39]; } catch(ex) { };
  try { fm.all('Fax').value = arrResult[0][40]; } catch(ex) { };
  try { fm.all('Phone').value = arrResult[0][41]; } catch(ex) { };
  try { fm.all('GetFlag').value = arrResult[0][42]; } catch(ex) { };
  try { fm.all('Satrap').value = arrResult[0][43]; } catch(ex) { };
  try { fm.all('EMail').value = arrResult[0][44]; } catch(ex) { };
  try { fm.all('FoundDate').value = arrResult[0][45]; } catch(ex) { };
  try { fm.all('BankAccNo').value = arrResult[0][46]; } catch(ex) { };
  try { fm.all('BankCode').value = arrResult[0][47]; } catch(ex) { };
  try { fm.all('GrpGroupNo').value = arrResult[0][48]; } catch(ex) { };
  //try { fm.all('PayIntv').value = arrResult[0][49]; } catch(ex) { };
  try { fm.all('PayMode').value = arrResult[0][50]; } catch(ex) { };
  try { fm.all('CValiDate').value = arrResult[0][51]; } catch(ex) { };
  try { fm.all('GetPolDate').value = arrResult[0][52]; } catch(ex) { };
  try { fm.all('SignDate').value = arrResult[0][53]; } catch(ex) { };
  try { fm.all('FirstPayDate').value = arrResult[0][54]; } catch(ex) { };
  try { fm.all('PayEndDate').value = arrResult[0][55]; } catch(ex) { };
  try { fm.all('PaytoDate').value = arrResult[0][56]; } catch(ex) { };
  try { fm.all('RegetDate').value = arrResult[0][57]; } catch(ex) { };
  try { fm.all('Peoples').value = arrResult[0][58]; } catch(ex) { };
  try { fm.all('Mult').value = arrResult[0][59]; } catch(ex) { };
  try { fm.all('Prem').value = arrResult[0][60]; } catch(ex) { };
  try { fm.all('Amnt').value = arrResult[0][61]; } catch(ex) { };
  try { fm.all('SumPrem').value = arrResult[0][62]; } catch(ex) { };
  try { fm.all('SumPay').value = arrResult[0][63]; } catch(ex) { };
  try { fm.all('Dif').value = arrResult[0][64]; } catch(ex) { };
  try { fm.all('SSFlag').value = arrResult[0][65]; } catch(ex) { };
  try { fm.all('PeakLine').value = arrResult[0][66]; } catch(ex) { };
  try { fm.all('GetLimit').value = arrResult[0][67]; } catch(ex) { };
  try { fm.all('GetRate').value = arrResult[0][68]; } catch(ex) { };
  try { fm.all('MaxMedFee').value = arrResult[0][69]; } catch(ex) { };
  try { fm.all('ExpPeoples').value = arrResult[0][70]; } catch(ex) { };
  try { fm.all('ExpPremium').value = arrResult[0][71]; } catch(ex) { };
  try { fm.all('ExpAmnt').value = arrResult[0][72]; } catch(ex) { };
  try { fm.all('DisputedFlag').value = arrResult[0][73]; } catch(ex) { };
  try { fm.all('BonusRate').value = arrResult[0][74]; } catch(ex) { };
  try { fm.all('Lang').value = arrResult[0][75]; } catch(ex) { };
  try { fm.all('Currency').value = arrResult[0][76]; } catch(ex) { };
  try { fm.all('State').value = arrResult[0][77]; } catch(ex) { };
  try { fm.all('LostTimes').value = arrResult[0][78]; } catch(ex) { };
  try { fm.all('AppFlag').value = arrResult[0][79]; } catch(ex) { };
  try { fm.all('ApproveCode').value = arrResult[0][80]; } catch(ex) { };
  try { fm.all('ApproveDate').value = arrResult[0][81]; } catch(ex) { };
  try { fm.all('UWOperator').value = arrResult[0][82]; } catch(ex) { };
  try { fm.all('AgentCode').value = arrResult[0][83]; } catch(ex) { };
  try { fm.all('AgentGroup').value = arrResult[0][84]; } catch(ex) { };
  try { fm.all('AgentCode1').value = arrResult[0][85]; } catch(ex) { };
  try { fm.all('Remark').value = arrResult[0][86]; } catch(ex) { };
  try { fm.all('UWFlag').value = arrResult[0][87]; } catch(ex) { };
  try { fm.all('OutPayFlag').value = arrResult[0][88]; } catch(ex) { };
  try { fm.all('ApproveFlag').value = arrResult[0][89]; } catch(ex) { };
  try { fm.all('EmployeeRate').value = arrResult[0][90]; } catch(ex) { };
  try { fm.all('FamilyRate').value = arrResult[0][91]; } catch(ex) { };
  try { fm.all('Operator').value = arrResult[0][92]; } catch(ex) { };
  try { fm.all('MakeDate').value = arrResult[0][93]; } catch(ex) { };
  try { fm.all('MakeTime').value = arrResult[0][94]; } catch(ex) { };
  try { fm.all('ModifyDate').value = arrResult[0][95]; } catch(ex) { };
  try { fm.all('ModifyTime').value = arrResult[0][96]; } catch(ex) { };
  try { fm.all('FIELDNUM').value = arrResult[0][97]; } catch(ex) { };
  try { fm.all('PK').value = arrResult[0][98]; } catch(ex) { };
  try { fm.all('fDate').value = arrResult[0][99]; } catch(ex) { };
  try { fm.all('ManageFeeRate').value = arrResult[0][100]; } catch(ex) { };
  try { fm.all('GrpSpec').value = arrResult[0][101]; } catch(ex) { };
  try { fm.all('GetPolMode').value = arrResult[0][102]; } catch(ex) { };
  try { fm.all('PolApplyDate').value = arrResult[0][103]; } catch(ex) { };
  try { fm.all('StandbyFlag1').value = arrResult[0][105]; } catch(ex) { };  
  try { fm.all('StandbyFlag2').value = arrResult[0][106]; } catch(ex) { };  
  try { fm.all('StandbyFlag3').value = arrResult[0][107]; } catch(ex) { };  

}




/*********************************************************************
 *  Click事件，当点击“关联暂交费信息”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showFee()
{
	cPolNo = fm.ProposalGrpContNo.value;
	if( cPolNo == "" )
	{
		alert( "您必须先查询投保单才能进入暂交费信息部分。" );
		return false
	}
	
	showInfo = window.open( "./ProposalFee.jsp?PolNo=" + cPolNo + "&polType=GROUP" ,"",sFeatures);
} 
	
 

function queryCom()
{
	
	if(fm.AgentType.value!="01" && fm.AgentType.value!="07")
	{
		alert("您选择的销售渠道不用填写中介机构！");
		return;
	}
	//AgentCommonQueryMain
	showInfo = window.open("../cardgrp/ComCommonQueryMain.jsp?branchtype=2","",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
}

function queryBank()
{
			//alert ("-------1--------");
			//BankQueryMain
	showInfo = window.open("../cardgrp/BankQueryMain.jsp","",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  

}
function queryAgent()
{
	//alert("fm.all('AgentCode').value==="+fm.all('AgentCode').value);
	if(fm.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！"); 
		 return;
	}
	var tCom=(fm.all('ManageCom').value).substring(0,2);
	//alert(tCom);
	//return;
  if(fm.all('AgentCode').value == "")	{  
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+fm.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	   //分俩种情况 直销，交叉销售 现在为 团险直销、联办代理两部分
	   if(fm.AgentType.value=="01")
	   {
	   	//showInfo = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+fm.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
	   	//AgentCommonQueryMain
	   	showInfo = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+fm.all('ManageCom').value+"&branchtype=2","",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
	  }else if(fm.AgentType.value=="03")
	  	{
	  	//AgentCommonQueryMain
	  		showInfo = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+fm.all('ManageCom').value+"&branchtype=4","",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
	  	}else if(fm.AgentType.value=="12")//2010-5-14 综拓渠道改为12
   	{
   		//综合拓展 branchtype='1'
   		//AgentCommonQueryMain
   		 showInfo = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+tCom+"&queryflag=1&branchtype=1","",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
  }else{
  //AgentCommonQueryMain
	   showInfo = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+fm.all('ManageCom').value+"&branchtype=2","",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
	  }
	}
	if(fm.all('AgentCode').value != ""){
	  var cAgentCode = fm.AgentCode.value;  //保单号码	
    //alert("cAgentCode=="+cAgentCode);
    //如果业务员代码长度为8则自动查询出相应的代码名字等信息
    //alert("cAgentCode=="+cAgentCode);
    if(cAgentCode.length!=10){
    	return;
    }  
	  //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+fm.all('ManageCom').value+"'";
   	//var strSQL = "select a.*,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
	 //        + "and a.AgentCode = b.AgentCode and a.branchtype='2' and a.agentstate!='03' and a.agentstate!='04' and a.AgentGroup = c.AgentGroup and a.AgentCode='"+cAgentCode+"'"; 
	 /*var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name,a.idtype,a.idno from LAAgent a,LATree b,LABranchGroup c where 1=1 "
	            + "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and a.AgentCode='"+cAgentCode+"'"; 
	*/mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql16");  
			mySql.addSubPara(cAgentCode);   
	if(fm.all('AgentType').value=="12")//2010-5-14 综拓渠道改为12
  	{
  		//strSQL = strSQL + " and a.branchtype='1'";
  		mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql17");  
			mySql.addSubPara(cAgentCode);   
	}
    var arrResult = easyExecSql(mySql.getString());
    //alert(arrResult);
    if (arrResult != null) {
    	fm.AgentCode.value = arrResult[0][0];
  	fm.AgentName.value = arrResult[0][3];
  	fm.AgentManageCom.value = arrResult[0][2];
  	fm.AgentGroup.value = arrResult[0][1];
  	fm.BranchAttr.value = arrResult[0][10];
  	queryZT();  
      
      //alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else
    {
     fm.AgentGroup.value="";
     alert("编码为:["+fm.all('AgentCode').value+"]的代理人不存在，请确认!");
    }
	}	
}


//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
  	fm.AgentName.value = arrResult[0][3];
  	fm.AgentManageCom.value = arrResult[0][2];
  	fm.AgentGroup.value = arrResult[0][1];
  	fm.BranchAttr.value = arrResult[0][10];
  	showCodeName();
  	queryZT();
  }
}

//查询综拓专员和助理信息
function queryZT()
{  //2010-5-14综拓渠道改为12
  if(fm.all('AgentType').value=="12")
  {  
  	/*var strSQL="select a.upagentcode,(select name from laagent where agentcode=a.upagentcode),a.agentcode,(select name from laagent where agentcode=a.agentcode) from laxagent a where a.managecom='"+fm.AgentManageCom.value+"'"
  						+ " and exists (select 1 from laagent b where a.agentcode=b.agentcode and b.branchtype=("
  						+ "select branchtype from laagent where agentcode = '"+fm.AgentCode.value+"'))";
  	*/mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql18");  
			mySql.addSubPara(fm.AgentManageCom.value);  
			mySql.addSubPara(fm.AgentCode.value);  
  	var arrResult1=easyExecSql(mySql.getString());
    if(arrResult1!=null)
  	{
     fm.all('AgentCodeOper').value= arrResult1[0][0];
     fm.all('AgentNameOper').value= arrResult1[0][1];
     fm.all('AgentCodeAssi').value= arrResult1[0][2];
     fm.all('AgentNameAssi').value= arrResult1[0][3];
    }
    else
    {
     fm.all('AgentCodeOper').value= '';
     fm.all('AgentNameOper').value= '';
     fm.all('AgentCodeAssi').value= '';
     fm.all('AgentNameAssi').value= '';
    }
  }
}

//校验销售渠道为综拓时，是否有综拓专员和助理信息
function checkZT()
{    //2010-5-14  综拓渠道改为12
  	if(fm.all('AgentType').value=="12")
  	{
  		if(fm.all('AgentCodeOper').value==null || (fm.all('AgentCodeOper').value)=="")
  		{
  			alert("综合拓展专员信息不能为空！");
  			return false;
  		}
  		if(fm.all('AgentCodeAssi').value==null || (fm.all('AgentCodeAssi').value)=="")
  		{
  			alert("综合拓展助理信息不能为空！");
  			return false;
  		}
  	}
  	return true;
}

//返回查询结果，赋给多业务员multline
function afterQuery3(arrResult)
{	
  if(arrResult!=null)
  {
		fm.all( tField ).all( 'MultiAgentGrid1').value = arrResult[0][0];
		fm.all( tField ).all( 'MultiAgentGrid2').value = arrResult[0][3];
		fm.all( tField ).all( 'MultiAgentGrid3').value = arrResult[0][2];
		fm.all( tField ).all( 'MultiAgentGrid4').value = arrResult[0][10];
		fm.all( tField ).all( 'MultiAgentGrid6').value = arrResult[0][1];
		fm.all( tField ).all( 'MultiAgentGrid7').value = arrResult[0][8];
  }
 
}

function afterQuery4(arrResult)
{	
  if(arrResult!=null)
  {
		fm.GrpNo.value = arrResult[0][0];
		fm.GrpName.value = arrResult[0][1];
		fm.Asset.value = arrResult[0][2];
		fm.GrpNature.value = arrResult[0][3];
		fm.BusinessType.value = arrResult[0][4];
		fm.Peoples.value = arrResult[0][5];
		fm.Fax.value = arrResult[0][6];
  }

}

function queryAgent2()
{
	if(fm.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！"); 
		 return;
	}
	if(fm.all('AgentCode').value != "" && fm.all('AgentCode').value.length==8 )	 {
	var cAgentCode = fm.AgentCode.value;  //保单号码	
	//var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+fm.all('ManageCom').value+"'";
    mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql19");  
			mySql.addSubPara(cAgentCode);  
			mySql.addSubPara(fm.all('ManageCom').value);  
    var arrResult = easyExecSql(mySql.getString());
       //alert(arrResult);
    if (arrResult != null) {
      fm.AgentGroup.value = arrResult[0][2];
      alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("编码为:["+fm.all('AgentCode').value+"]的代理人不存在，请确认!");
     }
	}	
}

function afterCodeSelect( cCodeName, Field )
{
	
 if(cCodeName=="GetGrpAddressNo"){
     //var strSQL="select b.AddressNo,b.GrpAddress,b.GrpZipCode,b.LinkMan1,b.Department1,b.HeadShip1,b.Phone1,b.E_Mail1,b.Fax1,b.LinkMan2,b.Department2,b.HeadShip2,b.Phone2,b.E_Mail2,b.Fax2 from LCGrpAddress b where b.AddressNo='"+fm.GrpAddressNo.value+"' and b.CustomerNo='"+fm.GrpNo.value+"'";
    mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql20");  
			mySql.addSubPara(fm.GrpAddressNo.value);  
			mySql.addSubPara(fm.GrpNo.value);  
     arrResult=easyExecSql(mySql.getString());
     try {fm.all('GrpAddressNo').value= arrResult[0][0]; } catch(ex) { };
     try {fm.all('GrpAddress').value= arrResult[0][1]; } catch(ex) { };
     try {fm.all('GrpZipCode').value= arrResult[0][2]; } catch(ex) { };
     try {fm.all('LinkMan1').value= arrResult[0][3]; } catch(ex) { };
     try {fm.all('Department1').value= arrResult[0][4]; } catch(ex) { };
     try {fm.all('HeadShip1').value= arrResult[0][5]; } catch(ex) { };
     try {fm.all('Phone1').value= arrResult[0][6]; } catch(ex) { };
     try {fm.all('E_Mail1').value= arrResult[0][7]; } catch(ex) { };
     try {fm.all('Fax1').value= arrResult[0][8]; } catch(ex) { };
     try {fm.all('LinkMan2').value= arrResult[0][9]; } catch(ex) { };
     try {fm.all('Department2').value= arrResult[0][10]; } catch(ex) { };
     try {fm.all('HeadShip2').value= arrResult[0][11]; } catch(ex) { };
     try {fm.all('Phone2').value= arrResult[0][12]; } catch(ex) { };
     try {fm.all('E_Mail2').value= arrResult[0][13]; } catch(ex) { };
     try {fm.all('Fax2').value= arrResult[0][14]; } catch(ex) { };
  }
  
  if(cCodeName=="AgentType"){
     var tAgentType=fm.AgentType.value;
     if(tAgentType=="01"){
        fm.SecondAgentType.disabled = false;
     }else{
        fm.SecondAgentType.value = "";
        fm.SecondAgentType.disabled = true;
     }//2010-5-14 综拓渠道改为12
     if(tAgentType=="12"){
        DivAssiOper.style.display='';
     }else{
        DivAssiOper.style.display='none';        
     }
  	 try {fm.all('AgentCode').value= ""; } catch(ex) { };
     try {fm.all('AgentName').value= ""; } catch(ex) { };
     try {fm.all('AgentManageCom').value= ""; } catch(ex) { };
     try {fm.all('BranchAttr').value= ""; } catch(ex) { };
     try {fm.all('AgentCodeOper').value= ""; } catch(ex) { };
	 try {fm.all('AgentNameOper').value= ""; } catch(ex) { };
	 try {fm.all('AgentCodeAssi').value= ""; } catch(ex) { };
	 try {fm.all('AgentNameAssi').value= ""; } catch(ex) { };
     try{initMultiAgentGrid();} catch(ex) { };
  }
  
  if(cCodeName=="RiskGrp")
  {
  	//var strSql = "select bonusflag From lmriskapp  where riskcode ='"+ fm.RiskCode.value +"'";
    mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql21");  
			mySql.addSubPara(fm.RiskCode.value);    
    var arrResult = easyExecSql(mySql.getString());
    var ttflag="0";
    if(arrResult!=null && arrResult[0][0]=="1")
  	
  	{
  		divExplain.style.display='';
  	}else{
  	  divExplain.style.display='none';
	 }
  }
  if(cCodeName=="RiskGrpWithOutEC")
  {
  	//var strSql = "select bonusflag From lmriskapp  where riskcode ='"+ fm.RiskCode.value +"'";
    mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql22");  
			mySql.addSubPara(fm.RiskCode.value);    
    var arrResult = easyExecSql(mySql.getString());
    var ttflag="0";
    if(arrResult!=null && arrResult[0][0]=="1")
  	
  	{
  		divExplain.style.display='';
  	}else{
  	  divExplain.style.display='none';
	 }
  }
}

function checkMainAppender(cRiskCode)
{
	if( isSubRisk( cRiskCode ) == true ) 
	{   // 附险
		var tPolNo = getMainRiskNo(cRiskCode);   //弹出录入附险的窗口,得到主险保单号码
		if (!checkRiskRelation(tPolNo, cRiskCode)) 
		{
			alert("主附险包含关系错误，输入的主险号不能带这个附加险！"); 
			return false;
		}
	}
  return true;
}


function isSubRisk(cRiskCode) {
 // var arrQueryResult = easyExecSql("select SubRiskFlag from LMRiskApp where RiskCode='" + cRiskCode + "'", 1, 0);
			mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql23");  
			mySql.addSubPara(cRiskCode);  
	var arrQueryResult = easyExecSql(mySql.getString()	, 1, 0);	
	if(arrQueryResult[0] == "S")    //需要转成大写
		return true;
	if(arrQueryResult[0] == "M")
		return false;

	if (arrQueryResult[0].toUpperCase() == "A")
		if (confirm("该险种既可以是主险,又可以是附险!选择确定进入主险录入,选择取消进入附险录入"))
			return false;
		else
			return true;

	return false;
}



function checkRiskRelation(tPolNo, cRiskCode) {
  // 集体下个人投保单
 /*var strSql = "select RiskCode from LCGrpPol where GrpPolNo = '" + tPolNo 
               + "' and RiskCode in (select Code1 from LDCode1 where Code = '" + cRiskCode + "' and codetype='grpchkappendrisk')";
  */mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql24");  
			mySql.addSubPara(tPolNo);  
			mySql.addSubPara(cRiskCode);  
  return easyQueryVer3(mySql.getString());
}

function getMainRiskNo(cRiskCode) {
	var urlStr = "../cardgrp/MainRiskNoInput.jsp";
	var tPolNo="";
  
    //tPolNo = window.showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:310px;dialogHeight:100px;center:yes;");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	tPolNo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	tPolNo.focus();
    return tPolNo;
}

function grpRiskInfo()
{
	//alert('a');
	//var newWindow = window.open("../cardgrp/GroupRisk.jsp");
	if (fm.ProposalGrpContNo.value=="")
	{
	    alert("必须保存合同信息才能进入〔险种信息〕！");    
	}
	delGrpVar();
	addGrpVar();
	showInfo = window.open("../cardgrp/GroupRisk.jsp","",sFeatures);
}

function grpInsuInfo()
{
	if (fm.ProposalGrpContNo.value=="")
	{
	    alert("必须保存合同信息才能〔添加被保人〕信息！");  
	    return false;  
	}
	//alert("1111"+fm.GrpContNo.value);
	fm.GrpContNo.value=fm.ProposalGrpContNo.value;
	delGrpVar();
	addGrpVar();
	parent.fraInterface.window.location = "../cardgrp/ContInsuredInput.jsp?LoadFlag=2&ContType=2&scantype="+ scantype;
}

function grpInsuList()
{  
	
	//alert("ok");add by yaory
//var prtNo ="<%=request.getParameter("prtNo")%>";
//var polNo ="<%=request.getParameter("polNo")%>";
//var scantype ="<%=request.getParameter("scantype")%>";
//var MissionID ="<%=request.getParameter("MissionID")%>";
//var ManageCom ="<%=request.getParameter("ManageCom")%>";
//var SubMissionID ="<%=request.getParameter("SubMissionID")%>";
//var ActivityID = "<%=request.getParameter("ActivityID")%>";
//var NoType = "<%=request.getParameter("NoType")%>";
//var GrpContNo ="<%=request.getParameter("GrpContNo")%>";
	if (fm.ProposalGrpContNo.value=="")
	{
	    alert("必须保存合同信息才能进入〔被保人清单〕信息界面！");  
	    return false;  
	} 
	//进入被保人清单时会将managecom等数据保存到缓存中,在之后添加lccont和lcpol时会用到,
	//所以要保证fm.managecom.value与lcgrpcont中的管理机构相同
	checkManageCom();
  delGrpVar();
	addGrpVar();
  parent.fraInterface.window.location = "../cardgrp/ContGrpInsuredInput.jsp?LoadFlag="+LoadFlag+"&scantype="+scantype+"&prtNo="+polNo+"&polNo="+polNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID;    
    
}

function checkManageCom(){
  var tNowMangeCom =fm.ManageCom.value;
  //alert("tMangeCom:"+tNowMangeCom);
  //alert("prtno:"+prtNo);
  //var tSql ="select ManageCom from lcgrpcont where prtno = '"+prtNo+"'";
  mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql25");  
			mySql.addSubPara(prtNo);   
  var tOriManageCom = easyExecSql(mySql.getString());
  if(tNowMangeCom != tOriManageCom){
     //alert("管理机构与保存的合同的管理机构不一致！");
     //fm.ManageCom.focus();
     fm.ManageCom.value = tOriManageCom;
     //return false;
  }
}
function grpRiskPlanInfo()
{
	if (fm.ProposalGrpContNo.value=="")
	{
	    alert("必须保存合同信息才能进行〔保险计划制定〕！");  
	    return false;  
	}
  showInfo = window.open("../cardgrp/ContPlan.jsp?GrpContNo="+fm.GrpContNo.value+"&LoadFlag="+LoadFlag+"&CValiDate="+fm.CValiDate.value,"",sFeatures);
	//parent.fraInterface.window.location="../cardgrp/ContPlan.jsp?scantype="+scantype+"&MissionID="+MissionID+"&ManageCom="+ManageCom+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&GrpContNo="+fm.GrpContNo.value+"&LoadFlag="+LoadFlag+"";
}

/*********************************************************************
 *  点击扫描件查询
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function ScanQuery2()
{
	var arrReturn = new Array();

	var prtNo=fm.all("ProposalGrpContNo").value;
	
	if(prtNo==""||prtNo==null)
	{
	  	alert("请先选择一个团体投保单!");
  		return ;
  	}

    //var tsql="select subtype From es_doc_main where doccode='"+prtNo+"' and subtype!='3000' "; //这里排出查协议的扫描件，如果既有协议又有"团险投保书_年金险种投保单"扫描件那么会有问题。
 	//			var crr = easyExecSql(tsql);
 	//			var tsubtype="";
 	//			//alert(crr);
 	//			if(crr!=null)
 	//			{
 	//			  if(crr[0][0]=="1000")
 	//			  {
 	//			    tsubtype="TB1002";
 	//			  }else{
 	//			    tsubtype="TB1003";
 	//			  }
 	//		  }else{
 	//		     tsubtype="TB1002";
 	//	    }
 	//var strSql2="select missionprop5 from lbmission where activityid='0000011099' and missionprop1='"+prtNo+"'";
	mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql26");  
			mySql.addSubPara(prtNo); 
	var crrResult = easyExecSql(mySql.getString());
	var SubType="";
	if(crrResult!=null)
	{
	SubType = crrResult[0][0];
	}
	window.open("../sys/ProposalEasyScan.jsp?BussType=TB&BussNoType=12&SubType="+SubType+"&prtNo="+prtNo,"", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
}

function focuswrap(){
    myonfocus(showInfo);
}

function delGrpVar(){
    //删除可能留在缓存中的个人合同信息
    try { mSwitch.deleteVar('ContNo'); } catch(ex) { };
    try { mSwitch.deleteVar('ProposalContNo'); } catch(ex) { };
    
    //团体合同信息
    try { mSwitch.deleteVar('GrpContNo'); } catch(ex) { };
    try { mSwitch.deleteVar('ProposalGrpContNo'); } catch(ex) { };
    try { mSwitch.deleteVar('PrtNo'); } catch(ex) { };
    try { mSwitch.deleteVar('SaleChnl'); } catch(ex) { };
    try { mSwitch.deleteVar('ManageCom'); } catch(ex) { };
    try { mSwitch.deleteVar('AgentCom'); } catch(ex) { };
    try { mSwitch.deleteVar('AgentType'); } catch(ex) { };
    try { mSwitch.deleteVar('AgentCode'); } catch(ex) { };
    try { mSwitch.deleteVar('AgentGroup'); } catch(ex) { };
    try { mSwitch.deleteVar('AgentCode1'); } catch(ex) { };
    try { mSwitch.deleteVar('Password'); } catch(ex) { };
    try { mSwitch.deleteVar('Password2'); } catch(ex) { };
    try { mSwitch.deleteVar('AppntNo'); } catch(ex) { };
    try { mSwitch.deleteVar('AddressNo'); } catch(ex) { };
    try { mSwitch.deleteVar('Peoples2'); } catch(ex) { };
    try { mSwitch.deleteVar('GrpName'); } catch(ex) { };
    try { mSwitch.deleteVar('BusinessType'); } catch(ex) { };
    try { mSwitch.deleteVar('GrpNature'); } catch(ex) { };
    try { mSwitch.deleteVar('RgtMoney'); } catch(ex) { };
    try { mSwitch.deleteVar('Asset'); } catch(ex) { };
    try { mSwitch.deleteVar('NetProfitRate'); } catch(ex) { };
    try { mSwitch.deleteVar('MainBussiness'); } catch(ex) { };
    try { mSwitch.deleteVar('Corporation'); } catch(ex) { };
    try { mSwitch.deleteVar('ComAera'); } catch(ex) { };
    try { mSwitch.deleteVar('Fax'); } catch(ex) { };
    try { mSwitch.deleteVar('Phone'); } catch(ex) { };
    try { mSwitch.deleteVar('GetFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('Satrap'); } catch(ex) { };
    try { mSwitch.deleteVar('EMail'); } catch(ex) { };
    try { mSwitch.deleteVar('FoundDate'); } catch(ex) { };
    try { mSwitch.deleteVar('AppntOnWorkPeoples'); } catch(ex) { };
    try { mSwitch.deleteVar('AppntOffWorkPeoples'); } catch(ex) { };
    try { mSwitch.deleteVar('AppntOtherPeoples'); } catch(ex) { };            
    try { mSwitch.deleteVar('GrpGroupNo'); } catch(ex) { };
    try { mSwitch.deleteVar('BankCode'); } catch(ex) { };
    try { mSwitch.deleteVar('BankAccNo'); } catch(ex) { };
    try { mSwitch.deleteVar('AccName'); } catch(ex) { };
    try { mSwitch.deleteVar('DisputedFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('OutPayFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('GetPolMode'); } catch(ex) { };
    try { mSwitch.deleteVar('Lang'); } catch(ex) { };
    try { mSwitch.deleteVar('Currency'); } catch(ex) { };
    try { mSwitch.deleteVar('LostTimes'); } catch(ex) { };
    try { mSwitch.deleteVar('PrintCount'); } catch(ex) { };
    try { mSwitch.deleteVar('RegetDate'); } catch(ex) { };
    try { mSwitch.deleteVar('LastEdorDate'); } catch(ex) { };
    try { mSwitch.deleteVar('LastGetDate'); } catch(ex) { };
    try { mSwitch.deleteVar('LastLoanDate'); } catch(ex) { };
    try { mSwitch.deleteVar('SpecFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('GrpSpec'); } catch(ex) { };
    try { mSwitch.deleteVar('PayMode'); } catch(ex) { };
    try { mSwitch.deleteVar('SignCom'); } catch(ex) { };
    try { mSwitch.deleteVar('SignDate'); } catch(ex) { };
    try { mSwitch.deleteVar('SignTime'); } catch(ex) { };
    try { mSwitch.deleteVar('CValiDate'); } catch(ex) { };
    try { mSwitch.deleteVar('PayIntv'); } catch(ex) { };
    try { mSwitch.deleteVar('ManageFeeRate'); } catch(ex) { };
    try { mSwitch.deleteVar('ExpPeoples'); } catch(ex) { };
    try { mSwitch.deleteVar('ExpPremium'); } catch(ex) { };
    try { mSwitch.deleteVar('ExpAmnt'); } catch(ex) { };
    try { mSwitch.deleteVar('Peoples'); } catch(ex) { };
    try { mSwitch.deleteVar('Mult'); } catch(ex) { };
    try { mSwitch.deleteVar('Prem'); } catch(ex) { };
    try { mSwitch.deleteVar('Amnt'); } catch(ex) { };
    try { mSwitch.deleteVar('SumPrem'); } catch(ex) { };
    try { mSwitch.deleteVar('SumPay'); } catch(ex) { };
    try { mSwitch.deleteVar('Dif'); } catch(ex) { };
    try { mSwitch.deleteVar('Remark'); } catch(ex) { };
    try { mSwitch.deleteVar('StandbyFlag1'); } catch(ex) { };
    try { mSwitch.deleteVar('StandbyFlag2'); } catch(ex) { };
    try { mSwitch.deleteVar('StandbyFlag3'); } catch(ex) { };
    try { mSwitch.deleteVar('InputOperator'); } catch(ex) { };
    try { mSwitch.deleteVar('InputDate'); } catch(ex) { };
    try { mSwitch.deleteVar('InputTime'); } catch(ex) { };
    try { mSwitch.deleteVar('ApproveFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('ApproveCode'); } catch(ex) { };
    try { mSwitch.deleteVar('ApproveDate'); } catch(ex) { };
    try { mSwitch.deleteVar('ApproveTime'); } catch(ex) { };
    try { mSwitch.deleteVar('UWOperator'); } catch(ex) { };
    try { mSwitch.deleteVar('UWFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('UWDate'); } catch(ex) { };
    try { mSwitch.deleteVar('UWTime'); } catch(ex) { };
    try { mSwitch.deleteVar('AppFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('PolApplyDate'); } catch(ex) { };
    try { mSwitch.deleteVar('CustomGetPolDate'); } catch(ex) { };
    try { mSwitch.deleteVar('GetPolDate'); } catch(ex) { };
    try { mSwitch.deleteVar('GetPolTime'); } catch(ex) { };
    try { mSwitch.deleteVar('State'); } catch(ex) { };
    //集体投保人信息
    try { mSwitch.deleteVar('GrpNo'); } catch(ex) { };
    try { mSwitch.deleteVar('AddressNo'); } catch(ex) { };
    try { mSwitch.deleteVar('AppntGrade'); } catch(ex) { };
    try { mSwitch.deleteVar('GrpName'); } catch(ex) { };
    try { mSwitch.deleteVar('PostalAddress'); } catch(ex) { };
    try { mSwitch.deleteVar('GrpZipCode'); } catch(ex) { };
    try { mSwitch.deleteVar('Phone'); } catch(ex) { };
    try { mSwitch.deleteVar('Password'); } catch(ex) { };
    try { mSwitch.deleteVar('State'); } catch(ex) { };
    try { mSwitch.deleteVar('AppntType'); } catch(ex) { };
    try { mSwitch.deleteVar('RelationToInsured'); } catch(ex) { };

                                                      
}

function addGrpVar(){   
	//add activityid
	//alert(ScanFlag);
	try { mSwitch.addVar('ScanFlag','',ScanFlag); } catch(ex) { }; 
	//end      
    try { mSwitch.addVar('ContNo','',''); } catch(ex) { };   
    try { mSwitch.addVar('ProposalContNo','',''); } catch(ex) { }; 
    //集体合同信息
    try { mSwitch.addVar('GrpContNo','',fm.GrpContNo.value); } catch(ex) { };
    try { mSwitch.addVar('ProposalGrpContNo','',fm.ProposalGrpContNo.value); } catch(ex) { };
    try { mSwitch.addVar('PrtNo','',fm.PrtNo.value); } catch(ex) { };
    try { mSwitch.addVar('SaleChnl','',fm.SaleChnl.value); } catch(ex) { };
    try { mSwitch.addVar('ManageCom','',fm.ManageCom.value); } catch(ex) { };
    try { mSwitch.addVar('AgentCom','',fm.AgentCom.value); } catch(ex) { };
    try { mSwitch.addVar('AgentType','',fm.AgentType.value); } catch(ex) { };
    try { mSwitch.addVar('AgentCode','',fm.AgentCode.value); } catch(ex) { };
    try { mSwitch.addVar('AgentGroup','',fm.AgentGroup.value); } catch(ex) { };
    try { mSwitch.addVar('AgentCode1','',fm.AgentCode1.value); } catch(ex) { };
    try { mSwitch.addVar('Password','',fm.Password.value); } catch(ex) { };
    try { mSwitch.addVar('Password2','',fm.Password2.value); } catch(ex) { };
    try { mSwitch.addVar('AppntNo','',fm.AppntNo.value); } catch(ex) { };
    try { mSwitch.addVar('Addressno','',fm.AddressNo.value); } catch(ex) { };
    try { mSwitch.addVar('Peoples2','',fm.Peoples2.value); } catch(ex) { };
    try { mSwitch.addVar('GrpName','',fm.GrpName.value); } catch(ex) { };
    try { mSwitch.addVar('BusinessType','',fm.BusinessType.value); } catch(ex) { };
    try { mSwitch.addVar('GrpNature','',fm.GrpNature.value); } catch(ex) { };
    try { mSwitch.addVar('RgtMoney','',fm.RgtMoney.value); } catch(ex) { };
    try { mSwitch.addVar('Asset','',fm.Asset.value); } catch(ex) { };
    try { mSwitch.addVar('NetProfitRate','',fm.NetProfitRate.value); } catch(ex) { };
    try { mSwitch.addVar('MainBussiness','',fm.MainBussiness.value); } catch(ex) { };
    try { mSwitch.addVar('Corporation','',fm.Corporation.value); } catch(ex) { };
    try { mSwitch.addVar('ComAera','',fm.ComAera.value); } catch(ex) { };
    try { mSwitch.addVar('Fax','',fm.Fax.value); } catch(ex) { };
    try { mSwitch.addVar('Phone','',fm.Phone.value); } catch(ex) { };
    try { mSwitch.addVar('GetFlag','',fm.GetFlag.value); } catch(ex) { };
    try { mSwitch.addVar('Satrap','',fm.Satrap.value); } catch(ex) { };
    try { mSwitch.addVar('EMail','',fm.EMail.value); } catch(ex) { };
    try { mSwitch.addVar('FoundDate','',fm.FoundDate.value); } catch(ex) { };
    try { mSwitch.addVar('GrpGroupNo','',fm.GrpGroupNo.value); } catch(ex) { };
    try { mSwitch.addVar('BankCode','',fm.BankCode.value); } catch(ex) { };
    try { mSwitch.addVar('BankAccNo','',fm.BankAccNo.value); } catch(ex) { };
    try { mSwitch.addVar('AccName','',fm.AccName.value); } catch(ex) { };
    try { mSwitch.addVar('DisputedFlag','',fm.DisputedFlag.value); } catch(ex) { };
    try { mSwitch.addVar('OutPayFlag','',fm.OutPayFlag.value); } catch(ex) { };
    try { mSwitch.addVar('GetPolMode','',fm.GetPolMode.value); } catch(ex) { };
    try { mSwitch.addVar('Lang','',fm.Lang.value); } catch(ex) { };
    try { mSwitch.addVar('Currency','',fm.Currency.value); } catch(ex) { };
    try { mSwitch.addVar('LostTimes','',fm.LostTimes.value); } catch(ex) { };
    try { mSwitch.addVar('PrintCount','',fm.PrintCount.value); } catch(ex) { };
    try { mSwitch.addVar('RegetDate','',fm.RegetDate.value); } catch(ex) { };
    try { mSwitch.addVar('LastEdorDate','',fm.LastEdorDate.value); } catch(ex) { };
    try { mSwitch.addVar('LastGetDate','',fm.LastGetDate.value); } catch(ex) { };
    try { mSwitch.addVar('LastLoanDate','',fm.LastLoanDate.value); } catch(ex) { };
    try { mSwitch.addVar('SpecFlag','',fm.SpecFlag.value); } catch(ex) { };
    try { mSwitch.addVar('GrpSpec','',fm.GrpSpec.value); } catch(ex) { };
    try { mSwitch.addVar('PayMode','',fm.PayMode.value); } catch(ex) { };
    try { mSwitch.addVar('SignCom','',fm.SignCom.value); } catch(ex) { };
    try { mSwitch.addVar('SignDate','',fm.SignDate.value); } catch(ex) { };
    try { mSwitch.addVar('SignTime','',fm.SignTime.value); } catch(ex) { };
    try { mSwitch.addVar('CValiDate','',fm.CValiDate.value); } catch(ex) { };
    try { mSwitch.addVar('PayIntv','',fm.PayIntv.value); } catch(ex) { };
    try { mSwitch.addVar('ManageFeeRate','',fm.ManageFeeRate.value); } catch(ex) { };
    try { mSwitch.addVar('ExpPeoples','',fm.ExpPeoples.value); } catch(ex) { };
    try { mSwitch.addVar('ExpPremium','',fm.ExpPremium.value); } catch(ex) { };
    try { mSwitch.addVar('ExpAmnt','',fm.ExpAmnt.value); } catch(ex) { };
    try { mSwitch.addVar('Peoples','',fm.Peoples.value); } catch(ex) { };
    try { mSwitch.addVar('Mult','',fm.Mult.value); } catch(ex) { };
    try { mSwitch.addVar('Prem','',fm.Prem.value); } catch(ex) { };
    try { mSwitch.addVar('Amnt','',fm.Amnt.value); } catch(ex) { };
    try { mSwitch.addVar('SumPrem','',fm.SumPrem.value); } catch(ex) { };
    try { mSwitch.addVar('SumPay','',fm.SumPay.value); } catch(ex) { };
    try { mSwitch.addVar('Dif','',fm.Dif.value); } catch(ex) { };
    try { mSwitch.addVar('Remark','',fm.Remark.value); } catch(ex) { };
    try { mSwitch.addVar('StandbyFlag1','',fm.StandbyFlag1.value); } catch(ex) { };
    try { mSwitch.addVar('StandbyFlag2','',fm.StandbyFlag2.value); } catch(ex) { };
    try { mSwitch.addVar('StandbyFlag3','',fm.StandbyFlag3.value); } catch(ex) { };
    try { mSwitch.addVar('InputOperator','',fm.InputOperator.value); } catch(ex) { };
    try { mSwitch.addVar('InputDate','',fm.InputDate.value); } catch(ex) { };
    try { mSwitch.addVar('InputTime','',fm.InputTime.value); } catch(ex) { };
    try { mSwitch.addVar('ApproveFlag','',fm.ApproveFlag.value); } catch(ex) { };
    try { mSwitch.addVar('ApproveCode','',fm.ApproveCode.value); } catch(ex) { };
    try { mSwitch.addVar('ApproveDate','',fm.ApproveDate.value); } catch(ex) { };
    try { mSwitch.addVar('ApproveTime','',fm.ApproveTime.value); } catch(ex) { };
    try { mSwitch.addVar('UWOperator','',fm.UWOperator.value); } catch(ex) { };
    try { mSwitch.addVar('UWFlag','',fm.UWFlag.value); } catch(ex) { };
    try { mSwitch.addVar('UWDate','',fm.UWDate.value); } catch(ex) { };
    try { mSwitch.addVar('UWTime','',fm.UWTime.value); } catch(ex) { };
    try { mSwitch.addVar('AppFlag','',fm.AppFlag.value); } catch(ex) { };
    try { mSwitch.addVar('PolApplyDate','',fm.PolApplyDate.value); } catch(ex) { };
    try { mSwitch.addVar('CustomGetPolDate','',fm.CustomGetPolDate.value); } catch(ex) { };
    try { mSwitch.addVar('GetPolDate','',fm.GetPolDate.value); } catch(ex) { };
    try { mSwitch.addVar('GetPolTime','',fm.GetPolTime.value); } catch(ex) { };
    try { mSwitch.addVar('State','',fm.State.value); } catch(ex) { };
    //集体投保人信息

    try { mSwitch.addVar('GrpNo','',fm.GrpNo.value); } catch(ex) { };
    try { mSwitch.addVar('PrtNo','',fm.PrtNo.value); } catch(ex) { };
    try { mSwitch.addVar('AddressNo','',fm.AddressNo.value); } catch(ex) { };
    try { mSwitch.addVar('AppntGrade','',fm.AppntGrade.value); } catch(ex) { };
    try { mSwitch.addVar('GrpName','',fm.Name.value); } catch(ex) { };
    try { mSwitch.addVar('PostalAddress','',fm.PostalAddress.value); } catch(ex) { };
    try { mSwitch.addVar('ZipCode','',fm.ZipCode.value); } catch(ex) { };
    try { mSwitch.addVar('Phone','',fm.Phone.value); } catch(ex) { };
    try { mSwitch.addVar('Password','',fm.Password.value); } catch(ex) { };
    try { mSwitch.addVar('State','',fm.State.value); } catch(ex) { };
    try { mSwitch.addVar('AppntType','',fm.AppntType.value); } catch(ex) { };
    try { mSwitch.addVar('RelationToInsured','',fm.RelationToInsured.value); } catch(ex) { };

}                       
function checkuseronly(comname)
{
//arrResult = easyExecSql("select * from LDGrp  where GrpName='" + comname + "'", 1, 0);			
	 mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql27");  
			mySql.addSubPara(prtNo); 
	 arrResult = easyExecSql(mySql.getString(), 1, 0);			
	 if (arrResult == null) {
            
	  } 
	  else {      	
 	        // arrResult = easyExecSql("select b.GrpName,b.BusinessType,b.GrpNature,b.Peoples,b.RgtMoney,b.Asset,b.NetProfitRate,b.MainBussiness,b.Corporation,b.ComAera,b.Fax,b.Phone,b.FoundDate,b.CustomerNo,b.OnWorkPeoples,b.OffWorkPeoples,b.OtherPeoples from LDGrp b where  b.CustomerNo='" + arrResult[0][0] + "'", 1, 0);			
	         mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql28");  
			mySql.addSubPara( arrResult[0][0] ); 
	 arrResult = easyExecSql(mySql.getString(), 1, 0);		
	         if (arrResult == null) {
                    alert("未查到投保单位信息");
	         } 
	         else {
	         try {fm.all('GrpName').value= arrResult[0][0];} catch(ex) { };  
	          try {fm.all('BusinessType').value= arrResult[0][1];} catch(ex) { };        
                 try {fm.all('GrpNature').value= arrResult[0][2]; } catch(ex) { };       
                 try {fm.all('Peoples').value= arrResult[0][3]; } catch(ex) { };       
                 try {fm.all('RgtMoney').value= arrResult[0][4]; } catch(ex) { };       
                 try {fm.all('Asset').value= arrResult[0][5]; } catch(ex) { };       
                 try {fm.all('NetProfitRate').value= arrResult[0][6];} catch(ex) { };        
                 try {fm.all('MainBussiness').value= arrResult[0][7];} catch(ex) { };        
                 try {fm.all('Corporation').value= arrResult[0][8];  } catch(ex) { };      
                 try {fm.all('ComAera').value= arrResult[0][9]; } catch(ex) { };  
                 try {fm.all('Fax').value= arrResult[0][10]; } catch(ex) { };  
                 try {fm.all('Phone').value= arrResult[0][11]; } catch(ex) { };  
                 try {fm.all('FoundDate').value= arrResult[0][12]; } catch(ex) { };
                 try {fm.all('GrpNo').value= arrResult[0][13]; } catch(ex) { };
                 try {fm.all('AppntAppntOnWorkPeoples').value= arrResult[0][14]; } catch(ex) { };
                 try {fm.all('AppntOffWorkPeoples').value= arrResult[0][15]; } catch(ex) { };
                 try {fm.all('AppntOtherPeoples').value= arrResult[0][16]; } catch(ex) { };                                                   
	        }
	  }	
}                        
//来自险种页面的函数，未必用到
function InputPolicy()
{
	var newWindow = window.open("../cardgrp/NewProposal.jsp?RiskCode=111302","",sFeatures);
}
function InputPolicyNoList()
{
	var newWindow = window.open("../cardgrp/NewProposal.jsp?NoListFlag=1&RiskCode=111302","",sFeatures);
}

//添加一笔险种纪录
function addRecord()
{
	  var tRiskCode = fm.all('RiskCode').value ;
	  var tPayIntv = fm.all('PayIntv').value ;
	  var tDistriFlag=fm.all('DistriFlag').value ;
	  //var ExpPeoples = fm.all('ExpPeoples').value ;	  
	  var tGrpContNo = fm.all('GrpContNo').value ;
	  fm.all( 'LoadFlag' ).value = LoadFlag ;
	  
	  //查询数据库判断是否已经保存合同信息
	  //var aSQL = "select * from lcgrpcont where grpcontno='"+tGrpContNo+"'";
	  mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql29");  
			mySql.addSubPara(tGrpContNo);  
	  var aResult = easyExecSql(mySql.getString(), 1, 0);
	  
	  if(aResult==null)
	  {	  	
	  	alert("团单合同信息未保存，不容许〔添加险种〕！");
	  	return ;
	  }
	  
	  if(tRiskCode==null ||tRiskCode==""|| tPayIntv==null|| tPayIntv=="")
	  {	  
	  	alert("险种信息未完整录入！");
	  	return ;
	  }


	 // var accSql="select bonusflag from lmriskapp where riskcode='"+tRiskCode+"' ";
	  mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql30");  
			mySql.addSubPara(tRiskCode);  
	  var tBonusFlag = easyExecSql(mySql.getString());
	  if(tBonusFlag=="1")
	  {
	  	//if(fm.StandbyFlag1.value=="")
	  	//{
	  	//	alert("请选择分红标记!");
	  	//	return false;
	  	//}
	  }
	  
	  /*********
	  if(fm.all('ExpPeoples').value != "" && isNaN(parseFloat(fm.all('ExpPeoples').value )) && !isNumeric(fm.all('ExpPeoples').value ))
	   {	  
	   	alert("请为'预计人数'录入数字！");	
	  	return ;
	  }
***********/
    RiskGrid.delBlankLine();
    fm.all('fmAction').value="INSERT||GROUPRISK";
    
    var showStr="正在添加团单险种信息，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    lockScreen('lkscreen');  
    fm.action="../cardgrp/GroupRiskSave.jsp"
    fm.submit();
}
  
//删除一笔险种纪录
  function deleteRecord()
{
  
    RiskGrid.delBlankLine();
    var showStr="正在删除险种信息，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.all('fmAction').value="DELETE||GROUPRISK";
    lockScreen('lkscreen');  
    fm.action="../cardgrp/GroupRiskSave.jsp"
    fm.submit();  
    
}


/*********************************************************************
 *  团单险种信息的的录入
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function grpFeeInput()
{
   	var tGrpContNo = fm.all('GrpContNo').value ;
 	  if(tGrpContNo==null ||tGrpContNo=="")
	  {	  	
	  	alert("团单合同信息未保存，不容许〔添加险种〕！");
	  	return ;
	  }
    //showInfo = window.open("../cardgrp/GrpFeeInput.jsp?ProposalGrpContNo="+fm.GrpContNo.value+"&LoadFlag="+LoadFlag);
    parent.fraInterface.window.location = "../cardgrp/GrpFeeInput.jsp?ProposalGrpContNo="+tGrpContNo+"&LoadFlag="+LoadFlag;     
}

/*********************************************************************
 *  初始化险种显示，包括 人数和保费的统计
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function fillriskgrid(){
	//alert("ok");
	//增加卡单的支持
  if(fm.ProposalGrpContNo.value!=""){
  	var tcontno=fm.ProposalGrpContNo.value;
  	//var tsql="select markettype from lcgrpcont where prtno='"+tcontno+"'";
    mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql31");  
			mySql.addSubPara(tcontno);  
    var ttresult=easyExecSql(mySql.getString());
    if(ttresult==null){
    //删除合同后为null
      return false;
      }
    if(ttresult[0][0]==0)
    {
    /* var  strSql = "Select a.riskcode ,b.riskname ,a.PayIntv,"
			               +"(select count(1) from lcpol where grppolno =a.grppolno),"
			               +"(select count(1) from lcpol where grppolno =a.grppolno)+(select count(1) from lcinsuredrelated where polno in (select polno from lcpol where grppolno=a.grppolno)),"

			               +"(select case when  Sum(prem) is null then 0 else Sum(prem)  end From lcpol Where grppolno=a.Grppolno and appflag<>'4'),"                  
			               +"(select case when  Sum(amnt) is null then 0 else Sum(amnt)  end From lcpol Where grppolno=a.Grppolno and appflag<>'4'),"                  
			               +"a.payenddate, "
			               +"a.StandbyFlag1, "
			               +"nvl(a.chargefeerate,0),"
			               +"nvl(a.commrate,0),"
			               +"a.bonusrate,"
			               +"nvl(a.fixprofitrate,0) "
			               +"From lcgrppol a,LMRiskApp b " 
			               +"Where  a.riskcode=b.riskcode and a.GrpContNo='" + fm.all('GrpContNo').value +"'";
		  */ mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql32");  
			mySql.addSubPara(fm.all('GrpContNo').value);  
		    turnPage.queryModal(mySql.getString(), RiskGrid);
		  }else{
		  
		 /* var  strSql = "Select a.riskcode ,b.riskname ,a.PayIntv,"
			                +"a.Peoples2,"
			               +"a.Peoples2,"
			               
			               +"a.prem,"                  
			               +"a.amnt,"                  
			               +"a.payenddate, "
			               +"a.StandbyFlag1 ,"
			               +"nvl(a.chargefeerate,0),"
			               +"nvl(a.commrate,0),"
			               +"a.bonusrate,"
			               +"nvl(a.fixprofitrate,0) "
			               +"From lcgrppol a,LMRiskApp b " 
			               +"Where  a.riskcode=b.riskcode and a.GrpContNo='" + fm.all('GrpContNo').value +"'";
		  */  mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql33");  
			mySql.addSubPara(fm.all('GrpContNo').value);  
		    
		    turnPage.queryModal(mySql.getString(), RiskGrid);
		}
	  
        }
  else{ 
  
        return false;  
  }
} 

function getapproveinfo(){
	mOperate=1;
	var approveinfo=new Array(); 
	approveinfo[0]=new Array(); 
	approveinfo[0][0]=polNo;
    afterQuery(approveinfo);
//alert("2");
	queryAgent();
	//var arrResult = easyExecSql("select agentcodeoper,agentcodeassi from LCGrpCont where GrpContNo = '" + fm.all('GrpContNo').value + "'");                        
	mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql34");  
			mySql.addSubPara(fm.all('GrpContNo').value);  
	var arrResult = easyExecSql(mySql.getString());		
	if (arrResult != null) {
		try {fm.all('AgentCodeOper').value= arrResult[0][0]; } catch(ex) { };
	  try {fm.all('AgentCodeAssi').value= arrResult[0][1]; } catch(ex) { };
	  fm.all('AgentNameOper').value="";
	  fm.all('AgentNameAssi').value="";
	  if (arrResult[0][0]!=null&&arrResult[0][0]!="")
	  {
	  //	var strSQL = "select name from laagent where agentcode='"+arrResult[0][0]+"'";
	  	mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql35");  
			mySql.addSubPara(arrResult[0][0]);  
	  	var arrResult1 = easyExecSql(mySql.getString());
	  	if (arrResult1 != null) fm.AgentNameOper.value = arrResult1[0][0];
	  }
	  if (arrResult[0][1]!=null&&arrResult[0][1]!="")
	  {
	  //	var strSQL = "select name from laagent where agentcode='"+arrResult[0][1]+"'";
	  	mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql36");  
			mySql.addSubPara(arrResult[0][0]);  
	  	var arrResult1 = easyExecSql(mySql.getString());
	  	if (arrResult1 != null) fm.AgentNameAssi.value = arrResult1[0][0];
	  }
	  if(fm.AgentType.value=="12")//2010-5-14 综拓渠道改为12
	  	DivAssiOper.style.display='';
   }
}
/*********************************************************************
 *  选中团单问题件的录入
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function GrpQuestInput()
{   
	var cGrpProposalContNo = fm.ProposalGrpContNo.value;  //团体保单号码	
	if(cGrpProposalContNo==""||cGrpProposalContNo==null)
	{
  		alert("请先选择一个团体投保单!");
  		return ;
         }
	showInfo=window.open("./GrpQuestInputMain.jsp?GrpContNo="+cGrpProposalContNo+"&Flag="+LoadFlag,"",sFeatures);
	
}
/*********************************************************************
 *  选中团单问题件的查询
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */                        
function GrpQuestQuery()
{
	var cGrpContNo = fm.GrpContNo.value;  //团单投保单号码
	if(cGrpContNo==""||cGrpContNo==null)
	{
  		alert("请先选择一个团体主险投保单!");
  		return ;
    }
	showInfo=window.open("./GrpQuestQueryMain.jsp?GrpContNo="+cGrpContNo+"&Flag="+LoadFlag,"",sFeatures);
}                        
/*********************************************************************
 *  复核通过该团单
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function gmanuchk()
{
	//团单投保单号码
	cProposalGrpContNo = fm.ProposalGrpContNo.value;  
	cflag="5";	

	if(MissionID == "null" || SubMissionID == "null")
	{
		fm.MissionID.value = mSwitch.getVar('MissionID');
		fm.SubMissionID.value = mSwitch.getVar('SubMissionID');
	}
	else
	{
		mSwitch.deleteVar("MissionID");
		mSwitch.deleteVar("SubMissionID");
	  mSwitch.addVar("MissionID", "", MissionID);
	  mSwitch.addVar("SubMissionID", "", SubMissionID);
	  mSwitch.updateVar("MissionID", "", MissionID);
	  mSwitch.updateVar("SubMissionID", "", SubMissionID);
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;			
	}
	if( cProposalGrpContNo == null || cProposalGrpContNo == "" )
		alert("请选择集体主险投保单后，再进行复核操作");
	else
	{
	  if (confirm("该操作将复核通过该保单号下的所有投保信息,确定吗？"))
	      {
		var i = 0;
		var showStr="正在复核集体投保单，请您稍候并且不要修改屏幕上的值或链接其他页面,团体投保单号是:"+cProposalGrpContNo;
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	 lockScreen('lkscreen');  
	  fm.action="./GroupPolApproveSave.jsp?ProposalGrpContNo1="+cProposalGrpContNo+"&Flag1=5";
    fm.submit();
    }
    else
    return false;
	}

}
/*********************************************************************
 *  点击返回按钮,关闭当前页面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function goback()
{  
	
	  if(LoadFlag=='16'){top.close();	}
    if (LoadFlag=='14'||LoadFlag=='23')
    {
          top.opener.querygrp();
    }
    else
    {
    	  top.opener.easyQueryClick();
    	  top.opener.easyQueryClickSelf();
    }
     top.close();	
}                        
                        
/*********************************************************************
 *  复核修改该团单
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function approveupdate()
{
	
	cProposalGrpContNo = fm.ProposalGrpContNo.value;  //团单投保单号码
	cflag="5";	
	if( cProposalGrpContNo == null || cProposalGrpContNo == "" )
		alert("请选择集体主险投保单后，再进行复核修改确认操作");
	else
	{
	  if (confirm("该操作表示所有的修改已完成,确定吗？"))
	      {
		var i = 0;
		var showStr="正在复核修改集体投保单，请您稍候并且不要修改屏幕上的值或链接其他页面,团体投保单号是:"+cProposalGrpContNo;
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		  //window.open("./GroupPolApproveSave.jsp?ProposalGrpContNo="+cProposalGrpContNo+"&Flag1=5","windows1");
	        fm.action="./GrpApproveModifyMakeSure.jsp?ProposalGrpContNo="+cProposalGrpContNo+"&Flag1=5";
                fm.submit();
          }
          else
          return false;
	    //window.close();
	    //fm.submit(); //提交
	}

}                        


/*********************************************************************
 *  团单分单定制
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */                        
function grpSubContInfo()
{
	if (fm.ProposalGrpContNo.value=="")
	{
	    alert("必须保存合同信息才能进行〔分单定制〕！");    
		return;
	}
	delGrpVar();
	addGrpVar();
	cGrpContNo = fm.all("GrpContNo").value;
	cPrtNo = fm.all("PrtNo").value;
showInfo = window.open("../cardgrp/SubContPolMain.jsp?GrpContNo=" + cGrpContNo + "&PrtNo=" + cPrtNo+"&LoadFlag="+LoadFlag,"",sFeatures);
	//parent.fraInterface.window.location="../cardgrp/SubContPolMain.jsp?scantype="+scantype+"&MissionID="+MissionID+"&ManageCom="+ManageCom+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&GrpContNo=" + cGrpContNo + "&PrtNo=" + cPrtNo+"&LoadFlag="+LoadFlag+"";
}

//归属规则定义
function ascriptDefine()
{
	if (fm.ProposalGrpContNo.value=="")
	{
	    alert("必须保存合同信息才能进行〔归属规则定义〕！");    
		return;
	}
	
	cGrpContNo = fm.all("GrpContNo").value;
	
showInfo = window.open("../cardgrp/AscriptionRuleMain.jsp?GrpContNo=" + cGrpContNo + "&LoadFlag="+LoadFlag,"",sFeatures);

}
//管理费定义
function feeDefine()
{
	if (fm.ProposalGrpContNo.value=="")
	{
	    alert("必须保存合同信息才能进行〔管理费定义〕！");    
		return;
	}
	
	cGrpContNo = fm.all("GrpContNo").value;
	
showInfo = window.open("../cardgrp/OutRiskFeeMain.jsp?GrpContNo=" + cGrpContNo + "&LoadFlag="+LoadFlag,"",sFeatures);

}
//缴费规则定义 
function payDefine()
{
	if (fm.ProposalGrpContNo.value=="")
	{
	    alert("必须保存合同信息才能进行〔管理费定义〕！");    
		return;
	}
	
	cGrpContNo = fm.all("GrpContNo").value;
	
showInfo = window.open("../cardgrp/PayRuleMain.jsp?GrpContNo=" + cGrpContNo + "&LoadFlag="+LoadFlag,"",sFeatures);
	
}
function getdetailaddress(){
//var strSQL="select b.AddressNo,b.GrpAddress,b.GrpZipCode,b.LinkMan1,b.Department1,b.HeadShip1,b.Phone1,b.E_Mail1,b.Fax1,b.LinkMan2,b.Department2,b.HeadShip2,b.Phone2,b.E_Mail2,b.Fax2 from LCGrpAddress b where b.AddressNo='"+fm.GrpAddressNo.value+"' and b.CustomerNo='"+fm.GrpNo.value+"'";
  mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql37");  
			mySql.addSubPara(fm.GrpAddressNo.value);  
			mySql.addSubPara(fm.GrpNo.value);    
  arrResult=easyExecSql(mySql.getString());
try {fm.all('GrpAddressNo').value= arrResult[0][0]; } catch(ex) { };
try {fm.all('GrpAddress').value= arrResult[0][1]; } catch(ex) { };
try {fm.all('GrpZipCode').value= arrResult[0][2]; } catch(ex) { };
try {fm.all('LinkMan1').value= arrResult[0][3]; } catch(ex) { };
try {fm.all('Department1').value= arrResult[0][4]; } catch(ex) { };
try {fm.all('HeadShip1').value= arrResult[0][5]; } catch(ex) { };
try {fm.all('Phone1').value= arrResult[0][6]; } catch(ex) { };
try {fm.all('E_Mail1').value= arrResult[0][7]; } catch(ex) { };
try {fm.all('Fax1').value= arrResult[0][8]; } catch(ex) { };
try {fm.all('LinkMan2').value= arrResult[0][9]; } catch(ex) { };
try {fm.all('Department2').value= arrResult[0][10]; } catch(ex) { };
try {fm.all('HeadShip2').value= arrResult[0][11]; } catch(ex) { };
try {fm.all('Phone2').value= arrResult[0][12]; } catch(ex) { };
try {fm.all('E_Mail2').value= arrResult[0][13]; } catch(ex) { };
try {fm.all('Fax2').value= arrResult[0][14]; } catch(ex) { };
}                                                                 
                              
/*********************************************************************
 *  团体合同信息录入完毕确认
 *  参数  ：  wFlag--各状态时调用此函数所走的分支
 *  返回值：  无
 *********************************************************************
 */
function GrpInputConfirm(wFlag)
{
    //if(checkOtherQuest()==false)
     // return false;
	mWFlag = 1;
	if (wFlag ==1 ) //录入完毕确认
	{
		//首先校验是否投保139险种，如果投保必须录入公共帐户(139.151.162)
		//var sql="select * From lcgrppol where grpcontno='"+fm.PrtNo.value+"' and riskcode in (select riskcode from lmriskapp where risktype6 in ('1','3'))";
		mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql38");  
			mySql.addSubPara(fm.PrtNo.value);    
		var rresult = easyExecSql(mySql.getString(), 1, 0);
		if(rresult!=null)
		{
			//sql="select * From lccont where poltype='2' and grpcontno='"+fm.PrtNo.value+"'";
			mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql39");  
			mySql.addSubPara(fm.PrtNo.value);  
			var tt=easyExecSql(mySql.getString(), 1, 0);
			if(tt==null)
			{
				alert("该投保单下已经投保需要增加公共账户的险种，请增加公共帐户!");
				return;
			}
		else
			{
			//sql="select count(*) from lcpol where poltypeflag='2' and grpcontno='"+fm.PrtNo.value+"'";
			mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql40");  
			mySql.addSubPara(fm.PrtNo.value); 
			var aa=easyExecSql(mySql.getString(), 1, 0);
				
			//var sqll="select count(*) From lcgrppol where grpcontno='"+fm.PrtNo.value+"' and riskcode in (select riskcode from lmriskapp where risktype6 in ('1','3'))";
		
			mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql41");  
			mySql.addSubPara(fm.PrtNo.value); 
			var bb=easyExecSql(mySql.getString(), 1, 0);
			if(aa<bb)
			{
				alert("该投保单下已经投保需要增加公共账户的险种，请增加公共帐户!");
				return;				
			}
			}			
		}
		//(162,188,198)
		//sql="select * From lcgrppol where grpcontno='"+fm.PrtNo.value+"' and riskcode in (select riskcode from lmriskapp where risktype6 in ('4','3'))";
		mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql42");  
			mySql.addSubPara(fm.PrtNo.value); 
		rresult = easyExecSql(mySql.getString(), 1, 0);
		if(rresult!=null)
		{
			//sql="select * From lccont where poltype='3' and grpcontno='"+fm.PrtNo.value+"'";
			mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql43");  
			mySql.addSubPara(fm.PrtNo.value); 
			var tt=easyExecSql(mySql.getString(), 1, 0);
			if(tt==null)
			{
				alert("该投保单下已经投保需要增加法人账户的险种，请增加法人帐户!");
				return;
			}
		else
			{
			//sql="select count(*) from lcpol where poltypeflag='3' and grpcontno='"+fm.PrtNo.value+"'";
			mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql44");  
			mySql.addSubPara(fm.PrtNo.value); 
			
			//var sqll="select count(*) From lcgrppol where grpcontno='"+fm.PrtNo.value+"' and riskcode in (select riskcode from lmriskapp where risktype6 in ('4','3'))";
			var mySql2 = new SqlClass();
			mySql2 = new SqlClass();
			mySql2.setResourceName("cardgrp.ContPolInputSql");
			mySql2.setSqlId("ContPolSql45");  
			mySql2.addSubPara(fm.PrtNo.value); 
			
			if(easyExecSql(mySql.getString(), 1, 0)<easyExecSql(mySql2.getString(), 1, 0))
			{
				alert("该投保单下已经投保需要增加法人账户的险种，请增加法人帐户!");
				return;				
			}
			}
		}
		//139和分红险，不能录入“无名单”被保险人的校验
		//sql="select count(*) from lcpol where poltypeflag='1' and grpcontno='"+fm.PrtNo.value+"' and riskcode in (select riskcode from lmriskapp where risktype6='1' or RiskPeriod='L')";
		mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql46");  
			mySql.addSubPara(fm.PrtNo.value); 
		var num1=easyExecSql(mySql.getString(), 1, 0);
		if(num1>0)
		{
				alert("该投保单下存在录入了无名单的长险或139险种!");
				return;				
		}
		//不能添加法人帐户的险种添加的法人帐户要校验
		//sql="select count(*) from lcpol where poltypeflag='3' and grpcontno='"+fm.PrtNo.value+"' and riskcode not in (select riskcode from lmriskapp where risktype6 in('4','3'))";
		mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql47");  
			mySql.addSubPara(fm.PrtNo.value); 
		var num2=easyExecSql(mySql.getString(), 1, 0);
		if(num2>0)
		{
				alert("该投保单下存在录错法人帐户的险种!");
				return;				
		}	
		//不能添加公共帐户的险种添加的公共帐户要校验
		//sql="select count(*) from lcpol where poltypeflag='2' and grpcontno='"+fm.PrtNo.value+"' and riskcode not in (select riskcode from lmriskapp where risktype6 in('1','2','3'))";
		mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql48");  
			mySql.addSubPara(fm.PrtNo.value); 
		var num3=easyExecSql(mySql.getString(), 1, 0);
		if(num3>0)
		{
				alert("该投保单下存在录错公共帐户的险种!");
				return;				
		}				
		//对万能险只能先做财务,后录业务,同时保单生效日期不得早于财务暂收费的到帐日期
		//sql="select count(*) From lcgrppol where grpcontno='"+fm.PrtNo.value+"' and riskcode in (select riskcode from lmriskapp where risktype6 in ('4'))";
		mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql49");  
			mySql.addSubPara(fm.PrtNo.value); 
		var num4=easyExecSql(mySql.getString(), 1, 0);
		if(num4>0)
		{
				///var wSql="select enteraccdate from ljtempfee where otherno='"+fm.PrtNo.value+"' and tempfeetype='1' and confflag='0' ";
				mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql50");  
			mySql.addSubPara(fm.PrtNo.value); 
				var tenteraccdate=easyExecSql(mySql.getString());
				if (tenteraccdate!=null&&tenteraccdate!="")
				{
					  if(fm.CValiDate.value<tenteraccdate)
					  {
				     alert("保单生效日不能在到帐日期 "+tenteraccdate+" 前,请删除投保单重新录入!");
				     return;						  	
					  }
				}else
					{
				    alert("该投保单下不存在已到帐的交费，请先交费!");
				    return;				
				  }  
		}			
		var tt=easyExecSql(mySql.getString(), 1, 0);
		/*var tStr= "	select * from lwmission where 1=1 "
							+" and lwmission.processid = '0000000011'"
							+" and lwmission.activityid = '0000011001'"
							+" and lwmission.missionprop1 = '"+fm.PrtNo.value+"'";*/
		mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql51");  
			mySql.addSubPara(fm.PrtNo.value); 
		turnPage.strQueryResult = easyQueryVer3(mySql.getString(), 1, 0, 1);
		  if (turnPage.strQueryResult) {
		  	alert("该团单合同已经做过保存！");
		  	return;
		  	}
		if(fm.all('ProposalGrpContNo').value == "") 
	   {
	   	  alert("团单合同信息未保存,不容许您进行 [录入完毕] 确认！");
	   	  return;
	   }
	   var rc=mSwitch.getVar('ScanFlag');
	   if(rc=="0")
	   {
	   		fm.WorkFlowFlag.value = "0000011098";
	   }else{
	   	 fm.WorkFlowFlag.value = "0000011099";
	   }
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
		//增加提示管理费的操作(139,151,162,188,198)
	//var sql="select * from lcgrppol where grpcontno='" + fm.ProposalGrpContNo.value + "' and riskcode in (select riskcode from lmriskapp where risktype6 in ('1','4','3'))";
	mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql52");  
			mySql.addSubPara(fm.ProposalGrpContNo.value); 
	var Result = easyExecSql(mySql.getString(), 1, 0);
	if(Result!=null)
	{
		//sql="select * From lcgrpfee where grpcontno='"+fm.ProposalGrpContNo.value+"'";
		mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql53");  
			mySql.addSubPara(fm.ProposalGrpContNo.value); 
		Result = easyExecSql(mySql.getString(), 1, 0);
		if(Result==null)
		{ 
			 if (!confirm('保单 '+fm.ProposalGrpContNo.value+' 下的管理费没有定义，您是否要确认操作？'))
		{
			return false;
		}
		}
	}
	
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
lockScreen('lkscreen');  
	fm.action = "./GrpInputConfirm.jsp";
  fm.submit(); //提交
}
 
function checkOtherQuest(){
   var tGrpContNo = fm.all("GrpContNo").value;;
   //var tcheckSQL = "select * from LCGrpIssuePol where grpcontno = '"+tGrpContNo+"' and replyresult is null";
   mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql54");  
			mySql.addSubPara(tGrpContNo); 
   var arrResult = easyExecSql(mySql.getString());
   if(arrResult!=null)
   {
      alert("合同下还有问题件未回复，请回复后在进行[问题件修改确认]操作！");
      return false;
   }
   return true;
} 
  
 function showNotePad()
{

//	var selno = SelfGrpGrid.getSelNo()-1;
//	if (selno <0)
//	{
//	      alert("请选择一条任务");
//	      return;
//	}
	
//var MissionID = SelfGrpGrid.getRowColData(selno, 4);
  var MissionID = fm.all.MissionID.value;
//  alert(MissionID);
  
//var SubMissionID = SelfGrpGrid.getRowColData(selno, 5);
  var SubMissionID = fm.all.SubMissionID.value;
//  alert(SubMissionID);
  
//	var ActivityID = SelfGrpGrid.getRowColData(selno, 6);
	var ActivityID = fm.all.ActivityID.value;
//	alert("ActivityID="+ActivityID);
	
//	var PrtNo = SelfGrpGrid.getRowColData(selno, 2);
  var PrtNo = fm.all('prtNo').value;
//  alert("PrtNo="+ fm.all('prtNo').value);
  
	var NoType = fm.all.NoType.value;
//	alert("NoType="+NoType);
	if(PrtNo == null || PrtNo == "")
	{
		alert("印刷号为空！");
		return;
	}		
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType=2";//+ NoType;
	var newWindow = OpenWindowNew("../uwgrp/WorkFlowNotePadFrame.jsp?Interface=../uwgrp/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");	
		
} 
  
  

                  
/*********************************************************************
 *  代码汉化的反显
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showCodeName()
{
	//alert("show1") 
	showOneCodeName('comcode','ManageCom','ManageComName');
	showOneCodeName('comcode','AgentManageCom','AppntManageComName');
	//alert("codename1") 
}

/*********************************************************************
 *  初始化工作流MissionID
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function initMissionID()
{
	if(MissionID == "null" || SubMissionID == "null")
	{
		MissionID = mSwitch.getVar('MissionID');
		SubMissionID = mSwitch.getVar('SubMissionID');
	}
	else
	{
		mSwitch.deleteVar("MissionID");
		mSwitch.deleteVar("SubMissionID");
	  mSwitch.addVar("MissionID", "", MissionID);
	  mSwitch.addVar("SubMissionID", "", SubMissionID);
	  mSwitch.updateVar("MissionID", "", MissionID);
	  mSwitch.updateVar("SubMissionID", "", SubMissionID);
	}
}
function getaddresscodedata()
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
    //strsql = "select AddressNo,GrpAddress from LCGrpAddress where CustomerNo ='"+fm.GrpNo.value+"'";
    mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql55");  
			mySql.addSubPara(fm.GrpNo.value); 
    
    //alert("strsql :" + strsql);
    turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);  
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
    fm.all("GrpAddressNo").CodeData=tCodeData;
}

function grpPersonAge()
{
	//showInfo = window.open("./GrpPersonAgeInput.jsp?GrpContNo="+fm.GrpContNo.value+");
	var tProposalGrpContNo = "";
	tProposalGrpContNo = fm.all( 'ProposalGrpContNo' ).value;
	if( tProposalGrpContNo == null || tProposalGrpContNo == "" )
	{
		alert( "必须保存合同信息才能进入〔险种信息〕！" );
		return false
	}
	delGrpVar();
	addGrpVar();
	
	var cGrpContNo = fm.all("GrpContNo").value;
	var	cPrtNo = fm.all("PrtNo").value;
	showInfo = window.open("GrpPersonAgeMain.jsp?GrpContNo=" + cGrpContNo + "&PrtNo=" + cPrtNo,"",sFeatures);
}

//从LJTempFee 表中得到保单生效日期
function QueryCValiDate()
{   
	prtNo=fm.all('PrtNo').value;
	 //alert("prtNo============="+prtNo);  
	//保单生效日为enteraccdate字段值的第二天,由于在以下SQL中使用"+"号会出问题，故用 - (-1)代替。
	//var brrResult = easyExecSql("select to_date(max(enteraccdate)) - (-1) from ljtempfee where othernotype='4' and otherno='"+prtNo+"' and operstate='0' ");
	  mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql56");  
			mySql.addSubPara(prtNo); 
	var brrResult = easyExecSql(mySql.getString() );		
	var CValiDate =""; 
	if(brrResult[0][0]=="")
	  { 
		  alert("无法得到保单生效日!"); 
		  return;
		}
  else
    { 
  	  try {fm.all('CValiDate').value = brrResult[0][0];} catch(ex) { alert("保单生效日期有问题!");}; 
  	} 
}

function QueryChargeDate()
{
	  prtNo=fm.all('PrtNo').value;
		//var arrResult = easyExecSql("select to_date(max(enteraccdate)) from ljtempfee where othernotype='4' and otherno='"+prtNo+"' and operstate='0' ");
	  mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql57");  
			mySql.addSubPara(prtNo); 
	  var arrResult = easyExecSql(mySql.getString());
	  var ChargeDate =""; 
	  if(arrResult[0][0]=="")
	  { 
		  //alert("无法得到财务收费日!"); 
		  //return;
		}
  else
    { 
  	  try {fm.all('PayDate').value = arrResult[0][0];} catch(ex) { alert("财务收日期有问题!");}; 
  	} 
  	
  	//tongmeng 2009-04-20 add
  	//增加保全换人比例
  //	var tSQL = "select decode(EdorTransPercent,'-1','',EdorTransPercent) from lcgrpcont where grpcontno='"+prtNo+"' ";
  	mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql58");  
			mySql.addSubPara(prtNo); 
  	var arrResult1 = easyExecSql(mySql.getString());
  	//alert(arrResult1);
    { 
  	  try {fm.all('EdorTransPercent').value = arrResult1[0][0];} catch(ex) { }; 
  	} 
  	
}

function initDetail()
{ 
 // var arrResult = easyExecSql("select * from LCGrpCont where PrtNo = '" + prtNo + "'", 1, 0);
  mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql59");  
			mySql.addSubPara(prtNo); 
	var arrResult = easyExecSql(mySql.getString(), 1, 0);	
  if(arrResult==null)
  { 
   return;
  }else
  {
  try {fm.all('GrpContNo').value= arrResult[0][0];} catch(ex) {alert(0); }; 
  try {fm.all('ProposalGrpContNo').value= arrResult[0][1];} catch(ex) { alert(1);}; 
  try {fm.all('PrtNo').value= arrResult[0][2];} catch(ex) { alert(2);}; 
  try {fm.all('SaleChnl').value= arrResult[0][3];} catch(ex) { alert(3);}; 
  try {fm.all('ManageCom').value= arrResult[0][4];} catch(ex) { alert(4);}; 
  //alert(fm.all('ManageCom').value);
  try {fm.all('AgentCom').value= arrResult[0][5];} catch(ex) {alert(5); }; 
  try {fm.all('AgentType').value= arrResult[0][6];} catch(ex) {alert(6); }; 
  try {fm.all('AgentCode').value= arrResult[0][7];} catch(ex) {alert(7); }; 
  try {fm.all('AgentGroup').value= arrResult[0][8];} catch(ex) { alert(8);}; 
  try {fm.all('AgentCode1').value= arrResult[0][9];} catch(ex) { alert(9);}; 
  try {fm.all('GrpNo').value= arrResult[0][12];} catch(ex) { alert(12);}; //changed by tuqiang
  try {fm.all('GrpAddressNo').value= arrResult[0][13];} catch(ex) {alert(13); }; 
  try {fm.all('GrpName').value= arrResult[0][15];} catch(ex) {alert(15); }; 
  try {fm.all('BusinessType').value= arrResult[0][16];} catch(ex) {alert(16); }; 
  try {fm.all('GrpNature').value= arrResult[0][17];} catch(ex) { alert(17);}; 
  try {fm.all('RgtMoney').value= arrResult[0][18];} catch(ex) { alert(18);}; 
  try {fm.all('Asset').value= arrResult[0][19];} catch(ex) { alert(19);}; 
  try {fm.all('NetProfitRate').value= arrResult[0][20];} catch(ex) {alert(20); }; 
  try {fm.all('MainBussiness').value= arrResult[0][21];} catch(ex) {alert(21); }; 
  try {fm.all('Corporation').value= arrResult[0][22];} catch(ex) {alert(22); }; 
  try {fm.all('ComAera').value= arrResult[0][23];} catch(ex) { alert(23);}; 
  try {fm.all('Fax').value= arrResult[0][24];} catch(ex) {alert(24); }; 
  try {fm.all('Phone').value= arrResult[0][25];} catch(ex) { alert(25);}; 
  try {fm.all('GetFlag').value= arrResult[0][26];} catch(ex) { alert(26);}; 
  try {fm.all('FoundDate').value= arrResult[0][29];} catch(ex) {alert(29); }; 
  try {fm.all('BankCode').value= arrResult[0][31];} catch(ex) {alert(31); }; 
  try {fm.all('BankAccNo').value= arrResult[0][32];} catch(ex) {alert(32); }; 
  try {fm.all('OutPayFlag').value= arrResult[0][35];} catch(ex) {alert(35); }; 
  try {fm.all('Currency').value= arrResult[0][38];} catch(ex) {alert(38); };
  try {fm.all('CValiDate').value= arrResult[0][51];} catch(ex) { alert(51);}; 
  try {fm.all('ExpPeoples').value= arrResult[0][54];} catch(ex) {alert(2); }; 
  try {fm.all('Remark').value= arrResult[0][64];} catch(ex) {alert(3); };
  try {
  	alert(arrResult[0][126]);
  	if(arrResult[0][126]==null||arrResult[0][126]==-1)
  	{
  		fm.all('EdorTransPercent').value = "";
  	}
  	else
  		{
  		fm.all('EdorTransPercent').value= arrResult[0][126];
  		}
  	} catch(ex) {alert(3); }; 
  }
}
function haveMultiAgent(){
	//alert("aa＝＝"+fm.all("multiagentflag").checked);
  //if(fm.all("multiagentflag").checked){   //modify by liuqh 不支持多业务员
  //	DivMultiAgent.style.display="";	
  //}
  //else{
  //  DivMultiAgent.style.display="none";	
  //}
}                                                                           
//Muline 表单发佣分配表 parm1
function queryAgentGrid(Field)
{	
	//alert("Field=="+Field);
	tField=Field;
	if(fm.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！");
		 return;
	}
	var tCom=(fm.all('ManageCom').value).substring(0,2);
	//if(fm.all(Field).all('MultiAgentGrid1').value==""){  
	//}	
	if(fm.AgentType.value=="03")
	{
		//交叉销售
		//showInfo = window.open("./CertifyInfoQuery.jsp");
		 showInfo = window.open("./AgentCommonQueryMain.jsp?ManageCom="+tCom+"&queryflag=1&branchtype=3","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
   }else if(fm.AgentType.value=="01")
   	{
   		//直销 团险直销 branchtype='2'
   		 showInfo = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+tCom+"&queryflag=1&branchtype=2","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
  }else{
   showInfo = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+tCom+"&queryflag=1&branchtype=2","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
}
	
  

//	if(fm.all( Field ).all('MultiAgentGrid1').value != "")	 {
//	var cAgentCode = fm.all( Field ).all('MultiAgentGrid1').value;  //保单号码
//	var strSql = "select AgentCode from LAAgent where AgentCode='" + cAgentCode +"'";
//    var arrResult = easyExecSql(strSql);
//       //alert(arrResult);
//    if (arrResult == null) {
//      alert("编码为:["+fm.all( Field ).all('MultiAgentGrid1').value+"]的代理人不存在，请确认!");
//    }
//  } 
}


//初始化主业务员信息
function displayMainAgent(){
  fm.all("BranchAttr").value = arrResult[0][3];
  fm.all("AgentName").value = arrResult[0][1];
}                                                                             

function displayMultiAgent(){
   //fm.all('multiagentflag').checked="true"; //modify by liuqh
   //haveMultiAgent();	
}    
//校验投保日期
function checkapplydate(){
  if(fm.PolApplyDate.value.length==8){
  if(fm.PolApplyDate.value.indexOf('-')==-1){ 
  	var Year =     fm.PolApplyDate.value.substring(0,4);
  	var Month =    fm.PolApplyDate.value.substring(4,6);
  	var Day =      fm.PolApplyDate.value.substring(6,8);
  	fm.PolApplyDate.value = Year+"-"+Month+"-"+Day;
  	if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("您输入的投保日期有误!");
   	   fm.PolApplyDate.value = ""; 
   	   return;
  	  }
  }
} 

else {var Year =     fm.PolApplyDate.value.substring(0,4);
	    var Month =    fm.PolApplyDate.value.substring(5,7);
	    var Day =      fm.PolApplyDate.value.substring(8,10);
	    if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("您输入的投保日期有误!");
   	   fm.PolApplyDate.value = "";
   	   return;
  	     }
  }

}

function checkCValidate(){
  if(fm.CValiDate.value.length==8){
  if(fm.CValiDate.value.indexOf('-')==-1){ 
  	var Year =     fm.CValiDate.value.substring(0,4);
  	var Month =    fm.CValiDate.value.substring(4,6);
  	var Day =      fm.CValiDate.value.substring(6,8);
  	fm.CValiDate.value = Year+"-"+Month+"-"+Day;
  	if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("您输入的投保日期有误!");
   	   fm.CValiDate.value = ""; 
   	   return;
  	  }
  }
} 

else {var Year =     fm.CValiDate.value.substring(0,4);
	    var Month =    fm.CValiDate.value.substring(5,7);
	    var Day =      fm.CValiDate.value.substring(8,10);
	    if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("您输入的投保日期有误!");
   	   fm.CValiDate.value = "";
   	   return; 
  	     }
  }
} 
                                                                         
function afterQuery5(arrResult)
{
	var tt=ManageCom.substring(0,2);
 // var sql="select agentcode,name,managecom,popedom from laperagent where agentcode='" + arrResult + "' and managecom like '"+tt+"' union select agentcode,name,managecom,agentgroup from laagent where agentcode='" + arrResult + "'";
	mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql60");  
			mySql.addSubPara(arrResult); 
			mySql.addSubPara(tt); 
			mySql.addSubPara(arrResult); 
	var arrQueryResult = easyExecSql(mySql.getString(), 1, 0);	
	//alert("oo");
	if(arrQueryResult!=null)
	{
		//alert("ok");
	  fm.all( tField ).all( 'MultiAgentGrid1').value = arrQueryResult[0][0];
		fm.all( tField ).all( 'MultiAgentGrid2').value = arrQueryResult[0][1];
		fm.all( tField ).all( 'MultiAgentGrid3').value = arrQueryResult[0][2];
		fm.all( tField ).all( 'MultiAgentGrid6').value = arrQueryResult[0][3];
	}
		
}
function afterQuery6(arrResult)
{
  if(arrResult!=null)
  {
  	fm.BankCode.value = arrResult[0][0];
  	fm.BankCodeName.value=arrResult[0][1];  	
  }
}

function ManageFeeCal()
{
	
var strUrl = "../cardgrp/CalMangeFeeMain.jsp?grpcontno="+ fm.PrtNo.value
   showInfo=window.open(strUrl,"管理费计算","width=600, height=250, top=150, left=250, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=yes");
}

 function AlladdNameClick()
{
	var strUrl = "../cardgrp/GrpIndManagerDiskInput.html"
   showInfo=window.open(strUrl,"批量补有名",sFeatures);
}

function showRealFee()
{
	window.open("../uwgrp/RealFeeInp.jsp?GrpContNo="+fm.PrtNo.value+"","管理费金额","width=300, height=150, top=150, left=250, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=yes");
}


function initRiskGrp(cObjCode,cObjName)
{
    fm.all('PayIntv').value="";
    fm.all('PayIntvName').value="";
    showCodeList('RiskGrp',[cObjCode,cObjName],[0,1],null,null,null,1,'400');
}

function getcodedata2()
{
	/*var sql="select RiskCode, RiskName from LMRiskApp where (enddate is null or enddate>'"+fm.sysdate.value+"') and riskprop in ('G','D') "
	         +" union select riskcode,(select riskname from lmrisk where riskcode=lmriskcomctrl.riskcode) from LMRiskComCtrl where "
	         +" startdate<='"+fm.sysdate.value+"' and (enddate is null or enddate>'"+fm.sysdate.value+"') and ManageComGrp='"+fm.all('ManageCom').value+"' "
	         +"  and (select distinct(riskprop) from lmriskapp where riskcode =lmriskcomctrl.riskcode) in ('G','D') order by RiskCode";
	*/var tCodeData = "0|";
	mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql61");  
			mySql.addSubPara(fm.sysdate.value); 
			mySql.addSubPara(fm.sysdate.value); 
			mySql.addSubPara(fm.sysdate.value); 
	mySql.addSubPara(fm.all('ManageCom').value); 
	
	turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);
	//prompt("查询险种代码:",sql);
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
    //alert(tCodeData);
    fm.all("RiskCode").CodeData=tCodeData;
	
}

function getSpecContent(){
   var tSel = SpecInfoGrid.getSelNo()-1;
   fm.GrpSpec.value = SpecInfoGrid.getRowColData(tSel,2);
}
function querySpec(){
   //var tSQL ="select grpcontno,speccontent,operator,makedate,serialno,proposalgrpcontno from lccgrpspec where grpcontno ='"+prtNo+"' order by makedate,maketime";
     mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql62");  
			mySql.addSubPara(prtNo);  
     
     turnPage.queryModal(mySql.getString(),SpecInfoGrid);
}

function showSpecInfo()
{
	window.open("../uwgrp/SpecInpMain.jsp?GrpContNo="+fm.PrtNo.value+"&LoadFlag=11","",sFeatures);
}

//控制人工核保界面按钮的明暗显示
function ctrlButtonDisabled(tContNo,LoadFlag){

 //修改为在Function里直接执行函数，返回结果集合(二维数组:第一列为按钮名称，第二列为disabled属性)。
    try
    {
	 //   var arrStr = easyExecSql("select ctrlGrpSigUWButton('"+tContNo+"','"+LoadFlag+"') from dual");
	  mySql = new SqlClass();
			mySql.setResourceName("cardgrp.ContPolInputSql");
			mySql.setSqlId("ContPolSql63");  
			mySql.addSubPara(tContNo);  
	  mySql.addSubPara(LoadFlag); 
	   var arrStr = easyExecSql(mySql.getString() );
	  // prompt('',arrStr);
	    if(arrStr!=null)
	    {
	    	for(var i=0; i<arrStr.length; i++)
	    	{
	    		//alert(arrStr[i][0]+":"+arrStr[i][1]+":"+arrStr[i][2]);
	    		try {
	    			if(arrStr[i][1]==0)
	    			{
	    				eval("fm.all('"+arrStr[i][0]+"').disabled='true'");
	    			}
	    			else
	    			{  				
	    				eval("fm.all('"+arrStr[i][0]+"').disabled=''");
	    			}
	    		} 
	    		catch(e) {}
	    		 
	    	}
	    }
    }
    catch(ex)
    {
    
    }     	
}