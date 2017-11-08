//               该文件中包含客户端需要处理的函数和事件

var mDebug="0";
var mOperate="";
var showInfo;
var arrDataSet;
var turnPage = new turnPageClass();
var tResourceName="naagent.NALAAgentInputSql";

window.onfocus=myonfocus;
//全局变量存放育成人和被育成人中较小的branchattr
var branchattrlength;
//使得从该窗口弹出的窗口能够聚焦
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
//提交，保存按钮对应操作
function submitForm()
{
    
	//tongmeng 2006-05-16 add
	//由新职级查询老职级
//	alert("11:"+document.all('AgentGrade').value);
	if (queryOldAgentGrade()== false ) return false; 
//	alert("22:"+document.all('AgentGrade').value);
	//alert(mOperate);
	if (!confirm('确认您的操作'))
	{
		return false;
	}
  if (mOperate!='DELETE||MAIN')
  {
	    if (!beforeSubmit())
	    {
	      return false;
		}

    var QuafEndDate=document.all('QuafEndDate').value;
    var currDate=document.all('currDate').value;
    var DevNo1=document.all('DevNo1').value;
    var BankAccNo=document.all('BankAccNo').value;
    var ManageCom=document.all('ManageCom').value;
    if(QuafEndDate<currDate)
    {   
    	alert('证书结束日期必须大于等于当前日期!');
        return false;
    }
    //2010-04-21 zjc  对厦门中支增加交验。
    //alert(ManageCom);
    //alert(ManageCom.substring(0,6));
    if(ManageCom.substring(0,6)=='863502')
    {
 	   // 二次增员的身份证肯定是存在的.不需要做校验.离职的不允许做增员.
		if (document.all('initAgentState').value != '02'&&document.all('WFlag').value=='zy') {
			var strSQL = "";
			/*strSQL = "select * from LAAgent where 1=1 and (branchtype='1' or (branchtype<>'1'  and agentstate<'03')) "
					+ getWherePart('QuafNo');*/
			strSQL = wrapSql(tResourceName,"querysqldes1",[document.all('QuafNo').value]);
			//alert(strSQL);
			var strQueryResult = easyQueryVer3(strSQL, 1, 1, 1);
			if (strQueryResult) {
				alert('该代理人资格证号已存在!');
				document.all('QuafNo').value = '';
				return false;
			}
		}
    	if(DevNo1=null||DevNo1=='')
        {   
        	alert('发生错误，展业证号没有录入！');
            return false;
        }
    	if(BankAccNo=null||BankAccNo=='')
        {   
        	alert('发生错误，工资存折帐号没有录入！');
            return false;
        }
    }

  }
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=350;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  fm.hideOperate.value=mOperate;
  if (fm.hideOperate.value=="")
  {
    alert("操作控制数据丢失！");
  }
//  showSubmitFrame(mDebug);
  if (mOperate == "INSERT||MAIN")
    document.all('AgentState').value = document.all('initAgentState').value;
  document.getElementById("fm").submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{ 
  showInfo.close();
  //var wageSQL="select lawage.agentcode from lawage,laagent where lawage.agentcode ='"+document.all('AgentCode').value+"' and lawage.agentcode=laagent.agentcode and laagent.agentstate<'03'";
  var wageSQL = wrapSql(tResourceName,"querysqldes2",[document.all('AgentCode').value]);
       var wageQueryResult=easyQueryVer3(wageSQL,1,1,1)
       if (wageQueryResult)
       {//by jiaqiangli 2006-07-26 只有没算过佣金的代理人才可以修改行政信息     	
        document.all('AgentGrade1').disabled = true;
        document.all('ManageCom').disabled = true;
        document.all('IntroAgency').disabled = true;
        document.all('BranchCode').disabled = true;
        document.all('GroupManagerName').disabled = true;
        document.all('DepManagerName').disabled = true;
        //by jiaqiangli 2006-08-03
        document.all('DirManagerName').disabled = true;
        document.all('MajordomoName').disabled = true;
        document.all('RearAgent').disabled = true;
        document.all('RearDepartAgent').disabled = true;
        document.all('RearSuperintAgent').disabled = true;
        document.all('RearAreaSuperintAgent').disabled = true;
       }
  document.all('hideIsManager').value = false;
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
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();	
    if(document.all('initAgentState').value != '02')
    {
     	showDiv(operateButton,"true"); 
     	showDiv(inputButton,"false"); 
    }
    //执行下一步操作
  }
}



//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
//    showDiv(operateButton,"true"); 
//    showDiv(inputButton,"false"); 

	document.all('AgentGrade').disabled = false;
	document.all('AgentGrade1').disabled = false;
    document.all('ManageCom').disabled = false;
    document.all('IntroAgency').disabled = false;
    document.all('BranchCode').disabled = false;
    document.all('GroupManagerName').disabled = false;
    document.all('DepManagerName').disabled = false;
    //by jiaqiangli 2006-08-03
    document.all('DirManagerName').disabled = false;
    document.all('MajordomoName').disabled = false;
    document.all('RearAgent').disabled = false;
    document.all('RearDepartAgent').disabled = false;
    document.all('RearSuperintAgent').disabled = false;
    document.all('RearAreaSuperintAgent').disabled = false;
    
	  initForm();
  }
  catch(re)
  {
  	alert("在NALAAgent.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 

//取消按钮对应操作
function cancelForm()
{
//  window.location="../common/html/Blank.html";
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}
 
//提交前的校验、计算  


//function changeIdType1()
//{
//   没有onchange aftercodeselect 代替
   //}
function beforeSubmit()
{

   
  //添加操作	
  //document.all('ManageCom').value = document.all('hideManageCom').value;
    
  //by jiaqiangli 2006-09-04
  //总监的录入，要求只有二级机构（含）以上人管有权限进行操作
  if(document.all('initOperate').value == 'INSERT') {
     if(ControlA09() == false) return false;
  }
  
  if( verifyInput() == false ) return false;
  
  
  
  if(checkPhone()== false) return false;
  
  //add by jiaqiangli 2007-05-14 代理人资格证号为16或20位的数字或英文字母
  if (document.all('QuafNo').value!=null && document.all('QuafNo').value!="") {
	 if (document.all('QuafNo').value.length!=16 && document.all('QuafNo').value.length!=20) {
		alert("代理人资格证号不为16或20位的数字或英文字母");
		return false;
	  }
  }

  //tongmeng 2006-06-18 add
  //增加对机构版本的维护
  if(!checkEdition())
     return false;
     
    
   if(document.all('IdType').value!=null && document.all('IdType').value!='' && document.all('IdType').value=='0') 
   {   if(document.all('Birthday').value==null||document.all('Birthday').value=='')
   
   {alert("出生年月不能为空");
    return false;
   }
   if(document.all('Sex').value==null||document.all('Sex').value=='')
   
   {alert("性别不能为空");
    return false;
   }
   
  var strChkIdNo = chkIdNo(trim(document.all('IDNo').value),trim(document.all('Birthday').value),trim(document.all('Sex').value))
  if (strChkIdNo != "")
  {  	
        alert(strChkIdNo);
	return false;
  }
  }
//  alert("111:"+document.all('UpAgent').value);
//  alert("222:"+document.all('AgentGrade').value);
  if ((trim(document.all('UpAgent').value)=='')&&(trim(document.all('AgentGrade').value)<='A03'))
  {
     alert('请先确定该销售机构的负责人！');
     return false;	
  }  
  if (trim(document.all('UpAgent').value)=='')
  {  	
     if (confirm("请先进行上级代理人的维护！"))
     {     	
        resetForm();
	return false;
     }
     else
     {
	if (!confirm("确定该代理人没有上级代理人"))
	{
	  alert("请先进行上级代理人的维护！");
	  resetForm();
	  return false;
	}
     }
  }
 

  //alert(document.all('AgentGrade').value);
  //alert(document.all('ManagerCode').value+" , "+document.all('AgentGrade').value+','+document.all('upBranchAttr').value);
  if (!judgeManager(document.all('ManagerCode').value,document.all('AgentGrade').value,document.all('upBranchAttr').value))
    return false;
  //add by tongmeng 2005-06-07
  //除了北分,8613,8635之外，其余公司的代理人资格证号码不能为空
  //modify by tongmeng 2005-06-10
  //增加8651机构下的代理人资格证编码可以为空
 // alert(document.all("ManageCom").value.substring(0,4));
 //modify by tomgmeng 2005-06-10
 //修改如果已经入司的代理人，则不对其代理人资格证是否为空做判断
 //alert(document.all("initOperate").value);
 if(document.all("initOperate").value=='INSERT')
 {
 	//if((document.all("ManageCom").value!='86110000')&&trim(document.all("ManageCom").value.substring(0,4))!='8613'&&trim(document.all("ManageCom").value.substring(0,4))!='8635'&&trim(document.all("ManageCom").value.substring(0,4))!='8651')

 	//add by tongmeng 2005-06-10
 	//动态查找数据库，判断哪个机构的代理人增员时应该输入代理人资格证书
 	//othersign为1或空 要求必须输入代理人资格证 
	//othersign为0 ,则代理人资格证可以为空
 	//var mSQL="select othersign from ldcode where codetype='station' and code='"+document.all("ManageCom").value+"'";
 	var mSQL = wrapSql(tResourceName,"querysqldes3",[document.all('ManageCom').value]);
 	//alert(mSQL);
 	var msqlResult = easyQueryVer3(mSQL,1,1,1);
 	//alert(msqlResult);
    if(!msqlResult){ 
    	return false; 
    }
    else  
    {
    	var tarr = decodeEasyQueryResult(msqlResult);
        var mflag = tarr[0][0];
        if(mflag==null||mflag=='1'||mflag=='')
        {
  			var tQuafNo=document.all("QuafNo").value;
  			tQuafNo=trim(tQuafNo);
    		if(tQuafNo==null||tQuafNo=='')
    		{
     			 alert("代理人资格证号码不能为空!");
      				return false;
    		}
 	}
    }
}
    
//佟盟2005-03-15增加
//判断增加的育成人是否符合条件(是否与该代理人的职级相符,是否与该代理人在同一个管理机构下)
//modify by tongmeng 2005-06-21
if(document.all('initOperate').value!='UPDATE||PART')
{
	if(document.all('AgentGrade').value>='A04')
	{
		if(trim(document.all('RearAgent').value)!='')
			{
   				 if(!DecideRearAgent('1'))
   				 {
        			alert("组育成人输入错误,请重新输入!");
    				document.all('RearAgent').value='';
        			return false;
    			 }
			}
	if(trim(document.all('RearDepartAgent').value)!='')
	{
    	if(!DecideRearAgent('2'))
    	{
        	alert("部育成人输入错误,请重新输入!");
    		document.all('RearDepartAgent').value='';
    		return false;
    	}
	}
	if(document.all('RearSuperintAgent').value!='')
	{
    	if(!DecideRearAgent('3'))
    	{
    		alert("区育成人输入错误,请重新输入!");
    		document.all('RearSuperintAgent').value='';
    		return false;
    	}
	}
	if(document.all('RearAreaSuperintAgent').value!='')
	{
    	if(!DecideRearAgent('4'))
    	{
       		alert("总监育成人输入错误,请重新输入!");
    		document.all('RearAreaSuperintAgent').value='';
    		return false;
    	}
	}
	}
}
  //tongmeng 2006-05-16 add
  //如果增员人为空,提示
  if(document.all('IntroAgency').value=='')
  {
  	if(!confirm("推荐人为空，是否保存?"))
  	{
  		return false;
  	}
  }
   //检查应有的育成人是否录入完全  tongmeng   
  if(document.all('AgentGrade').value>='A04')
  {
  	 if(!checkAllRearAgent())
  		return false;
  }
  //tongmeng 2006-05-16 add
  //如果增员一个主管,增加对各级育成人的校验
  //1 - 如果各级育成人同时为空 允许继续
  //2 - 如果
  if (!checkRearAgent())
    return false;
  
  //by jiaqiangli 2006-07-28
  //增加育成约束条件
  //by jiaqiangli 2006-08-31 育成人限制只在增员时
  if(document.all('initOperate').value == 'INSERT') {
  if (!checkRearRestrict())
     return false;
  }
  //modify by tongmeng 2006-02-09
  //去掉对增员入司年龄的限制 
  //
  /*
  //增加增员人员年龄限制校验  
  //modify by pengcheng 2005-09-20
  if(document.all('initOperate').value == 'INSERT')
  {
   if(!checkage())
    return false;
  }
  */
    
  //检查担保人信息是否录入 
  //by jiaqiangli 2006-07-25
  //所有担保人的信息不为空  
  if(!WarrantorGrid.checkValue())
    return false;
     
    var lineCount = 0;
    var tempObj = document.all('WarrantorGridNo'); //假设在表单fm中
    if (tempObj == null)
    {
      alert("请填写担保人信息！");
      return false;
    }
    WarrantorGrid.delBlankLine("WarrantorGrid");
    lineCount = WarrantorGrid.mulLineCount;
    if (lineCount == 0)
    {
      alert("请填写担保人信息！");
      return false;
    }else
    {
      var sValue;
      var strChkIdNo;	
      for(var i=0;i<lineCount;i++)
      {
      	sValue = WarrantorGrid.getRowColData(i,1);
      	//by jiaqiangli 2006-07-27 此处的非空校验在initWarrantorGrid中
      	//by jiaqiangli 注意此处的正则表达式理解起来有点绕
      	//by jiaqiangli 请参考代理人姓名的onkeyup事件的正则表达式处理
      	//by jiaqiangli 2007-03-20 取消字符数量的限制
      	if(checkWarrantorName(sValue))
      	{
      	   alert('要求第'+(i+1)+'个担保人姓名只能输入汉字或英文字母');
      	   return false;
      	}
      	/*
      	sValue = WarrantorGrid.getRowColData(i,2);
      	if ((trim(sValue)=='')||(sValue==null))
      	{
      	   alert('请输入担保人性别！');
      	   return false;
      	}
      	sValue = WarrantorGrid.getRowColData(i,3);
      	if ((trim(sValue)=='')||(sValue==null))
      	{
      	   alert('请输入担保人身份证！');
      	   return false;
      	}*/
      	/*
      	sValue = WarrantorGrid.getRowColData(i,4);
      	if ((trim(sValue)=='')||(sValue==null))
      	{
      	   alert('请输入担保人出生日期！');
      	   return false;
      	}*/
      	//by jiaqiangli 2006-07-27 增加担保人性别与身份证的校验
        strChkIdNo = WarrantorchkIdNo(trim(WarrantorGrid.getRowColData(i,3)),trim(WarrantorGrid.getRowColData(i,2)))
        if (strChkIdNo != "")
        {  	
          alert('第'+(i+1)+'个担保人'+strChkIdNo);
	  return false;
        }
      }	//end of for
    }
     var tbranchattr = document.all('BranchCode').value;
     var pstrSQL = "";
     // 佟盟 2005-08-18 修改
     //增加branchtype='1'条件
        //pstrSQL = "select branchtype from labranchgroup where branchattr='"+tbranchattr+"' and branchtype='1'";
        pstrSQL = wrapSql(tResourceName,"querysqldes4",[tbranchattr]);
     var strQueryResult  = easyQueryVer3(pstrSQL, 1, 1, 1);
     var arr = decodeEasyQueryResult(strQueryResult);
     document.all('BranchType').value = trim(arr[0][0]);   
    
    document.all('AgentGrade').disabled = false;
    document.all('AgentGrade1').disabled = false;
    document.all('ManageCom').disabled = false;
    document.all('IntroAgency').disabled = false;
    document.all('BranchCode').disabled = false;
    document.all('GroupManagerName').disabled = false;
    document.all('DepManagerName').disabled = false;
    //by jiaqiangli 2006-08-03
    document.all('DirManagerName').disabled = false;
    document.all('MajordomoName').disabled = false;
    document.all('RearAgent').disabled = false;
    document.all('RearDepartAgent').disabled = false;
    document.all('RearSuperintAgent').disabled = false;
    document.all('RearAreaSuperintAgent').disabled = false;
    
    //by jiaqiangli 2006-08-04
    //将不应该输入的育成人代码清空
    ControlRearInput();
    //by jiaqiangli 2006-09-06
    //将不可修改的姓名和身份证号重新变为不灰，否则save.jsp得不到值
    if (document.all('Name').disabled == true)
        document.all('Name').disabled = false;
    if (document.all('IdType').disabled == true)
        document.all('IdType').disabled = false;  
        
       
    if (document.all('IDNo').disabled == true)
        document.all('IDNo').disabled = false;
        
        //8g repair by jiaqiangli 2008-06-18
    if (document.all('BankCode').disabled == true)
        document.all('BankCode').disabled = false;
    if (document.all('BankAccNo').disabled == true)
        document.all('BankAccNo').disabled = false;
        
    return true;
}
//by jiaqiangli 2006-07-28 增加育成约束条件
//部经理A入司：如果录入部育成人B，那么A与B有相同的区经理或B是A的直接主管。
//组育成人C必须在B辖下。其他职级的同理
function checkRearRestrict()
{
	var lengtha,lengthb,branchattrlength;
	var branchattra,branchattrb=document.all('BranchCode').value;
	//被育成人职级:带a表示育成人,带b表示被育成人
	if(document.all('AgentGrade').value=='A09')
             lengthb=8;
        else if(document.all('AgentGrade').value=='A08')
             lengthb=10;
        else if(document.all('AgentGrade').value=='A07')
             lengthb=12;
        else if(document.all('AgentGrade').value=='A05') 
             lengthb=15;	
	var strSQL="";
	var strQueryResult;
	var arr;
	if(document.all('AgentGrade').value=='A05')
	{
		if(trim(document.all('RearAgent').value)!='')
		{
			//strSQL="select a.branchattr from labranchgroup a,latree b where a.agentgroup=b.agentgroup"
			  //    +" and b.agentcode='"+trim(document.all('RearAgent').value)+"' ";
			strSQL = wrapSql(tResourceName,"querysqldes5",[trim(document.all('RearAgent').value)]);
                        strQueryResult=easyQueryVer3(strSQL, 1, 1, 1);
                        arr=decodeEasyQueryResult(strQueryResult);
                        //育成人销售机构
                        branchattra=trim(arr[0][0]);
                        lengtha=branchattra.length;
                        //取两者之间的较小 
                        if(lengtha>=lengthb)
                            branchattrlength=lengthb;
                        else
                            branchattrlength=lengtha;
                        if(branchattra.substr(0,branchattrlength)!=branchattrb.substr(0,branchattrlength))
                        {
                        	alert('要求被育成人和组育成人'+document.all('RearAgent').value+'有相同的部经理或是他的直接主管');
                        	document.all('RearAgent').value='';
                        	return false;
                        }  
		}
	}
        else if(document.all('AgentGrade').value=='A07')
        {
        	//若部育成人不为空，则组育成人肯定不为空
        	if(trim(document.all('RearDepartAgent').value)!='')
        	{
        		//strSQL="select a.branchattr from labranchgroup a,latree b where a.agentgroup=b.agentgroup"
			      //+" and b.agentcode='"+trim(document.all('RearDepartAgent').value)+"' ";
        		strSQL = wrapSql(tResourceName,"querysqldes5",[trim(document.all('RearDepartAgent').value)]);
                        strQueryResult=easyQueryVer3(strSQL, 1, 1, 1);
                        arr=decodeEasyQueryResult(strQueryResult);
                        //育成人销售机构
                        branchattra=trim(arr[0][0]);
                        lengtha=branchattra.length;
                        //取两者之间的较小 
                        if(lengtha>=lengthb)
                            branchattrlength=lengthb;
                        else
                            branchattrlength=lengtha;
                        if(branchattra.substr(0,branchattrlength)!=branchattrb.substr(0,branchattrlength))
                        {
                        	alert('要求被育成人和部育成人'+document.all('RearDepartAgent').value+'有相同的区经理或是他的直接主管');
                        	document.all('RearDepartAgent').value='';
                        	return false;
                        }
                        //判断组育成人是否在部育成人的辖下,对输入的是同一个人的情况不判断(即自己在自己的辖下)
                        if(trim(document.all('RearDepartAgent').value)!=trim(document.all('RearAgent').value))
                        {
                        	//此时组育成人相对部育成人为被育成人
                        	//strSQL="select a.agentgrade,b.branchattr from latree a,labranchgroup b where a.agentgroup=b.agentgroup "
			              //+" and a.agentcode='"+trim(document.all('RearAgent').value)+"' ";
                        	strSQL = wrapSql(tResourceName,"querysqldes6",[trim(document.all('RearAgent').value)]);
			        strQueryResult=easyQueryVer3(strSQL, 1, 1, 1);
                                arr=decodeEasyQueryResult(strQueryResult);
                                branchattrb=trim(arr[0][1]);
                                if(trim(arr[0][0])=='A09')
                                     lengthb=8;
                                else if(trim(arr[0][0])=='A08')
                                     lengthb=10;
                                else if(trim(arr[0][0])=='A07')
                                     lengthb=12;
                                else if(trim(arr[0][0])=='A05') 
                                     lengthb=15;
                                //取两者之间的较小 
                                if(lengtha>=lengthb)
                                     branchattrlength=lengthb;
                                else
                                     branchattrlength=lengtha;
                                if(branchattra.substr(0,branchattrlength)!=branchattrb.substr(0,branchattrlength))
                                {
                        	    alert('组育成人必须在部育成人辖下!');
                        	    document.all('RearAgent').value='';
                        	    return false;
                                }
                        } 
        	}
        } 
        else if(document.all('AgentGrade').value=='A08')
        {
        	if(trim(document.all('RearSuperintAgent').value)!='')
        	{
        		//strSQL="select a.branchattr from labranchgroup a,latree b where a.agentgroup=b.agentgroup"
			      //+" and b.agentcode='"+trim(document.all('RearSuperintAgent').value)+"' ";
        		strSQL = wrapSql(tResourceName,"querysqldes5",[trim(document.all('RearSuperintAgent').value)]);
                        strQueryResult=easyQueryVer3(strSQL, 1, 1, 1);
                        arr=decodeEasyQueryResult(strQueryResult);
                        //育成人销售机构
                        branchattra=trim(arr[0][0]);
                        lengtha=branchattra.length;
                        //取两者之间的较小 
                        if(lengtha>=lengthb)
                            branchattrlength=lengthb;
                        else
                            branchattrlength=lengtha;
                        if(branchattra.substr(0,branchattrlength)!=branchattrb.substr(0,branchattrlength))
                        {
                        	alert('要求被育成人和区育成人'+document.all('RearSuperintAgent').value+'有相同的总监或是他的直接主管');
                        	document.all('RearSuperintAgent').value='';
                        	return false;
                        }
                        //判断组部育成人是否在区育成人的辖下 
                        if(trim(document.all('RearSuperintAgent').value)!=trim(document.all('RearDepartAgent').value))
                        {
                        	//此时部育成人相对区育成人为被育成人
                        	/*strSQL="select a.agentgrade,b.branchattr from latree a,labranchgroup b where a.agentgroup=b.agentgroup "
			              +" and a.agentcode='"+trim(document.all('RearDepartAgent').value)+"' ";*/
                        	strSQL = wrapSql(tResourceName,"querysqldes6",[trim(document.all('RearDepartAgent').value)]);
			        strQueryResult=easyQueryVer3(strSQL, 1, 1, 1);
                                arr=decodeEasyQueryResult(strQueryResult);
                                branchattrb=trim(arr[0][1]);
                                if(trim(arr[0][0])=='A09')
                                     lengthb=8;
                                else if(trim(arr[0][0])=='A08')
                                     lengthb=10;
                                else if(trim(arr[0][0])=='A07')
                                     lengthb=12;
                                else if(trim(arr[0][0])=='A05') 
                                     lengthb=15;
                                //取两者之间的较小 
                                if(lengtha>=lengthb)
                                     branchattrlength=lengthb;
                                else
                                     branchattrlength=lengtha;
                                if(branchattra.substr(0,branchattrlength)!=branchattrb.substr(0,branchattrlength))
                                {
                        	    alert('部育成人必须在区育成人辖下!');
                        	    document.all('RearDepartAgent').value='';
                        	    return false;
                                }
                        }
                        if(trim(document.all('RearSuperintAgent').value)!=trim(document.all('RearAgent').value))
                        {
                        	//此时组育成人相对区育成人为被育成人
                        	/*strSQL="select a.agentgrade,b.branchattr from latree a,labranchgroup b where a.agentgroup=b.agentgroup "
			              +" and a.agentcode='"+trim(document.all('RearAgent').value)+"' ";*/
                        	strSQL = wrapSql(tResourceName,"querysqldes6",[trim(document.all('RearAgent').value)]);
			        strQueryResult=easyQueryVer3(strSQL, 1, 1, 1);
                                arr=decodeEasyQueryResult(strQueryResult);
                                branchattrb=trim(arr[0][1]);
                                if(trim(arr[0][0])=='A09')
                                     lengthb=8;
                                else if(trim(arr[0][0])=='A08')
                                     lengthb=10;
                                else if(trim(arr[0][0])=='A07')
                                     lengthb=12;
                                else if(trim(arr[0][0])=='A05') 
                                     lengthb=15;
                                //取两者之间的较小 
                                if(lengtha>=lengthb)
                                     branchattrlength=lengthb;
                                else
                                     branchattrlength=lengtha;
                                if(branchattra.substr(0,branchattrlength)!=branchattrb.substr(0,branchattrlength))
                                {
                        	    alert('组育成人必须在区育成人辖下!');
                        	    document.all('RearAgent').value='';
                        	    return false;
                                }
                        }
        	}
        }
        else if(document.all('AgentGrade').value=='A09')
        {
        	if(trim(document.all('RearAreaSuperintAgent').value)!='')
        	{
        		/*strSQL="select a.branchattr from labranchgroup a,latree b where a.agentgroup=b.agentgroup"
			      +" and b.agentcode='"+trim(document.all('RearAreaSuperintAgent').value)+"' ";*/
        		strSQL = wrapSql(tResourceName,"querysqldes5",[trim(document.all('RearAreaSuperintAgent').value)]);
                        strQueryResult=easyQueryVer3(strSQL, 1, 1, 1);
                        arr=decodeEasyQueryResult(strQueryResult);
                        //育成人销售机构
                        branchattra=trim(arr[0][0]);
                        lengtha=branchattra.length;
                        //取两者之间的较小 
                        if(lengtha>=lengthb)
                            branchattrlength=lengthb;
                        else
                            branchattrlength=lengtha;
                        if(branchattra.substr(0,branchattrlength)!=branchattrb.substr(0,branchattrlength))
                        {
                        	alert('要求被育成人和总监育成人'+document.all('RearAreaSuperintAgent').value+'有相同的营销服务部');
                        	document.all('RearAreaSuperintAgent').value='';
                        	return false;
                        }
                        //判断组部区育成人是否在总监育成人的辖下
                        if(trim(document.all('RearAreaSuperintAgent').value)!=trim(document.all('RearSuperintAgent').value))
                        {
                        	//此时区育成人相对总监育成人为被育成人
                        	/*strSQL="select a.agentgrade,b.branchattr from latree a,labranchgroup b where a.agentgroup=b.agentgroup "
			              +" and a.agentcode='"+trim(document.all('RearSuperintAgent').value)+"' ";*/
                        	strSQL = wrapSql(tResourceName,"querysqldes6",[trim(document.all('RearSuperintAgent').value)]);
			        strQueryResult=easyQueryVer3(strSQL, 1, 1, 1);
                                arr=decodeEasyQueryResult(strQueryResult);
                                branchattrb=trim(arr[0][1]);
                                if(trim(arr[0][0])=='A09')
                                     lengthb=8;
                                else if(trim(arr[0][0])=='A08')
                                     lengthb=10;
                                else if(trim(arr[0][0])=='A07')
                                     lengthb=12;
                                else if(trim(arr[0][0])=='A05') 
                                     lengthb=15;
                                //取两者之间的较小 
                                if(lengtha>=lengthb)
                                     branchattrlength=lengthb;
                                else
                                     branchattrlength=lengtha;
                                if(branchattra.substr(0,branchattrlength)!=branchattrb.substr(0,branchattrlength))
                                {
                        	    alert('区育成人必须在总监育成人辖下!');
                        	    document.all('RearSuperintAgent').value='';
                        	    return false;
                                }
                        }
                        if(trim(document.all('RearAreaSuperintAgent').value)!=trim(document.all('RearDepartAgent').value))
                        {
                        	//此时部育成人相对总监育成人为被育成人
                        	/*strSQL="select a.agentgrade,b.branchattr from latree a,labranchgroup b where a.agentgroup=b.agentgroup "
			              +" and a.agentcode='"+trim(document.all('RearDepartAgent').value)+"' ";*/
                        	strSQL = wrapSql(tResourceName,"querysqldes6",[trim(document.all('RearDepartAgent').value)]);
			        strQueryResult=easyQueryVer3(strSQL, 1, 1, 1);
                                arr=decodeEasyQueryResult(strQueryResult);
                                branchattrb=trim(arr[0][1]);
                                if(trim(arr[0][0])=='A09')
                                     lengthb=8;
                                else if(trim(arr[0][0])=='A08')
                                     lengthb=10;
                                else if(trim(arr[0][0])=='A07')
                                     lengthb=12;
                                else if(trim(arr[0][0])=='A05') 
                                     lengthb=15;
                                //取两者之间的较小 
                                if(lengtha>=lengthb)
                                     branchattrlength=lengthb;
                                else
                                     branchattrlength=lengtha;
                                if(branchattra.substr(0,branchattrlength)!=branchattrb.substr(0,branchattrlength))
                                {
                        	    alert('部育成人必须在总监育成人辖下!');
                        	    document.all('RearDepartAgent').value='';
                        	    return false;
                                }
                        }
                        if(trim(document.all('RearAreaSuperintAgent').value)!=trim(document.all('RearAgent').value))
                        {
                        	//此时组育成人相对总监育成人为被育成人
                        	/*strSQL="select a.agentgrade,b.branchattr from latree a,labranchgroup b where a.agentgroup=b.agentgroup "
			              +" and a.agentcode='"+trim(document.all('RearAgent').value)+"' ";*/
                        	strSQL = wrapSql(tResourceName,"querysqldes6",[trim(document.all('RearAgent').value)]);
			        strQueryResult=easyQueryVer3(strSQL, 1, 1, 1);
                                arr=decodeEasyQueryResult(strQueryResult);
                                branchattrb=trim(arr[0][1]);
                                if(trim(arr[0][0])=='A09')
                                     lengthb=8;
                                else if(trim(arr[0][0])=='A08')
                                     lengthb=10;
                                else if(trim(arr[0][0])=='A07')
                                     lengthb=12;
                                else if(trim(arr[0][0])=='A05') 
                                     lengthb=15;
                                //取两者之间的较小 
                                if(lengtha>=lengthb)
                                     branchattrlength=lengthb;
                                else
                                     branchattrlength=lengtha;
                                if(branchattra.substr(0,branchattrlength)!=branchattrb.substr(0,branchattrlength))
                                {
                        	    alert('组育成人必须在总监育成人辖下!');
                        	    document.all('RearAgent').value='';
                        	    return false;
                                }
                        } 
        	}
        } 
        return true;
}
//by jiaqiangli 2006-07-27 增加担保人姓名校验 
function checkWarrantorName(tName)
{
   //by jiaqiangli 担保人姓名和代理人姓名一样只能输入汉字和英文字母
   //by jiaqiangli 正则表达式注释 注意此处有点绕
   ///[^A-Za-z\u4E00-\u9FA5]/g 是去匹配汉字和英文字母以外的字符 
   var namePattern=/[^A-Za-z\u4E00-\u9FA5]/g; //匹配汉字和英文字母
   if(!namePattern.test(tName)) 
   {
   	return false;
   }
   return true;
}       
/**
 * by jiaqiangli 2006-07-27 校验担保人性别和身份证号
 * 参数，输入的证件号码，性别
 * 返回  布尔值
 */
function WarrantorchkIdNo(iIdNo,iSex)
{
  var tmpStr="";
  var tmpInt=0;
  var strReturn = "";
  iIdNo = trim(iIdNo);
  iSex = trim(iSex);
  if ((iIdNo.length!=15) && (iIdNo.length!=18))
  {
    strReturn = "输入的身份证号位数错误";
    return strReturn;
  }
  if (iSex!='0' && iSex!='1')
  {
  	strReturn = "输入的性别不明确";
  	return strReturn;
  }
  if (iIdNo.length==15)
  {
      if (iSex=="0")
      {
      	tmpInt = parseInt(iIdNo.substring(14));
      	tmpInt = tmpInt % 2
      	if (tmpInt==0)
      	{
      	  strReturn = "输入的性别与身份证号的信息不一致";
          return strReturn;
      	}
      }
      else
      {
      	tmpInt = parseInt(iIdNo.substring(14));      	
      	tmpInt = tmpInt % 2
      	if (tmpInt!=0)
      	{
      	  strReturn = "输入的性别与身份证号的信息不一致";
          return strReturn;
      	}
      }  
      return strReturn;
  }
  if (iIdNo.length==18)
  {
      if (iSex=="0")
      {
      	tmpInt = parseInt(iIdNo.substring(16,17)); 
      	tmpInt = tmpInt % 2
      	if (tmpInt==0)
      	{
      	  strReturn = "输入的性别与身份证号的信息不一致";
          return strReturn;
      	}
      }
      else
      {
      	tmpInt = parseInt(iIdNo.substring(16,17));      	
      	tmpInt = tmpInt % 2
      	if (tmpInt!=0)
      	{
      	  strReturn = "输入的性别与身份证号的信息不一致";
          return strReturn;
      	}
      }
      return strReturn;
  }
}
//by jiaqiangli 2006-07-07 电话和手机要求至少一个不为空
function checkPhone()
{
	if(document.all('Phone').value==''&&document.all('Mobile').value=='')
	{
		alert('电话和手机要求至少一个不为空');
		return false;
	}
	return true;
}
//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,500,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}


//Click事件，当点击增加图片时触发该函数
function addClick()
{
  //下面增加相应的代码
  if (document.all('initOperate').value == 'INSERT')
  {
    mOperate="INSERT||MAIN";
    showDiv(operateButton,"false"); 
    showDiv(inputButton,"true"); 
    
    //document.all('AgentCode').value = '';
    if (document.all('AgentCode').value !='')
      resetForm();
  }else
    alert('在此不能新增！');	
}             

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{ if (document.all('initOperate').value == 'INSERT')
  {
    alert('在此不能修改！');	
  }else
  {
    if ((document.all("AgentCode").value == null)||(document.all("AgentCode").value == ''))
    {
       alert('请先查询出要修改的代理人记录！');    
       document.all('AgentCode').focus();
    }else
    {
       //下面增加相应的代码
       if (confirm("您确实想修改该记录吗?"))
       {  	
          mOperate=document.all('initOperate').value;
          submitForm();
       }
       else
       {
         mOperate="";
         alert("您取消了修改操作！");
       }
    }
  }
}           

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{//下面增加相应的代码
  mOperate="QUERY||MAIN";
  showInfo=window.open("./NALAAgentQueryInput.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}           

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  if (document.all('initOperate').value == 'INSERT')
  {
     alert('在此不能删除！');	
  }else
  {
     if ((document.all("AgentCode").value == null)||(document.all("AgentCode").value == ''))
     {
       alert('请先查询出要删除的代理人记录！');    
       document.all('AgentCode').focus();
     }else
     {	
         //下面增加相应的删除代码
         if (confirm("您确实想删除该记录吗?"))
         {
           mOperate="DELETE||MAIN";  
           submitForm();
         }
         else
         {
           mOperate="";
           alert("您取消了删除操作！");
         }
     }
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

function changeGroup()
{//by jiaqiangli 2006-07-24 焦点移不出
//   if (getWherePart('BranchCode')=='')
//     return false;
   if (queryOldAgentGrade()== false ) return false;  
   var tAgentGrade = trim(document.all('AgentGrade').value);
   if (tAgentGrade==null ||tAgentGrade==''){
     alert('请先录入代理人职级！');
     document.all('BranchCode').value = '';
     return false;
   }
   
   var strSQL = "";
   /*strSQL = "select BranchAttr,ManageCom,BranchManager,AgentGroup,BranchManagerName,UpBranch,UpBranchAttr "
           +" from LABranchGroup where 1=1 "
           +" and BranchType = '1' and EndFlag <> 'Y' and BranchLevel = '01' and (state<>'1' or state is null)"
           + getWherePart('BranchAttr','BranchCode');
           //+ getWherePart('ManageCom');	*/
   strSQL = wrapSql(tResourceName,"querysqldes7",[document.all('BranchCode').value]);
     	 //alert(strSQL);
   var strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
   if (!strQueryResult)
   {
   	alert('不存在该销售机构！');
   	document.all('BranchCode').value = '';
   	document.all('UpAgent').value = '';
   	document.all('GroupManagerName').value = '';
   	document.all('DepManagerName').value = '';
   	document.all('ManageCom').value = '';
   	//document.all('hideManageCom').value = '';
   	document.all('hideBranchCode').value = '';
   	document.all('ManagerCode').value = '';
   	document.all('upBranchAttr').value = '';
   	return false;
   }  
   var arr = decodeEasyQueryResult(strQueryResult);
   //判断管理人员
   //if (!judgeManager(arr[0][2],tAgentGrade,arr[0][6]))
     //return false;
   document.all('ManageCom').value = trim(arr[0][1]);
   //document.all('hideManageCom').value = trim(arr[0][1]);
   document.all('hideBranchCode').value = trim(arr[0][3]);
   document.all('ManagerCode').value = trim(arr[0][2]);
   document.all('upBranchAttr').value = trim(arr[0][6]);
   //var tBranchLevel = trim(arr[0][4]);
   if (tAgentGrade <= 'A05')
   {
     document.all('GroupManagerName').value = trim(arr[0][4]); //组经理
     if (tAgentGrade <= 'A03')
        document.all('UpAgent').value = trim(arr[0][2]);
   }   
   
   //判断上级机构，确定上级代理人
   if (arr[0][5]!=null && trim(arr[0][5])!='')
   {
        var tUpBranch = trim(arr[0][0]);  
        //得到该代理人的职级对应的机构显示代码
        switch (eval(tAgentGrade.substr(1)))
        {
          case 4,5:
             tUpBranch = document.all('BranchCode').value;
             break;
          case 6: 
             tUpBranch = tUpBranch.substr(0,tUpBranch.length-3); //高级经理
             break;
          case 7: 
             tUpBranch = tUpBranch.substr(0,tUpBranch.length-3); //高级经理
             break;
          case 8: 
             tUpBranch = tUpBranch.substr(0,tUpBranch.length-6);   //督导长
             break;
        } 
          	
   	//查询上级机构
   	//modify by tongmeng 2005-08-01
   	//增加branchtype='1'的条件
        /*strSQL = "select AgentGroup,BranchManager,BranchManagerName from LABranchGroup where 1=1 "
                +" and EndFlag <> 'Y' and AgentGroup = (select UpBranch from LABranchGroup where branchAttr = '"+tUpBranch+"' and branchtype='1' )" 
                +" and (state<>'1' or state is null) and branchtype='1' ";*/	
        strSQL = wrapSql(tResourceName,"querysqldes8",[tUpBranch]);
     	 //alert(strSQL);
     	// prompt("11"+strSQL);
     	 //
        strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
        if (!strQueryResult)
        {
           //if (document.all('AgentGrade').value!='A09')
   	     //alert('所录销售机构的上级机构不存在,无法显示部经理！');
   	   //return false;
        }else
        {  
          arr = decodeEasyQueryResult(strQueryResult);
          //if (document.all('hideIsManager').value == 'true')
          if (tAgentGrade > 'A03')
            document.all('UpAgent').value = trim(arr[0][1]);
          if (tAgentGrade < 'A06')
            document.all('DepManagerName').value = trim(arr[0][2]);
        }
   }
   //by jiaqiangli 2006-07-27
   queryDistMajor(document.all('BranchCode').value);       
   return true;
}
//by jiaqiangli 2006-07-27 查询区和总监姓名
function queryDistMajor(tBranchAttr)
{
   //by jiaqiangli 2006-07-25 督导区经理姓名
   var tupbranch=tBranchAttr.substr(0,12); 
  /* var tsql="select BranchManagerName from LABranchGroup where 1=1 "
           +" and EndFlag <> 'Y' and branchAttr = '"+tupbranch+"' " 
           +" and (state<>'1' or state is null) and branchtype='1' ";*/
   var tsql = wrapSql(tResourceName,"querysqldes9",[tupbranch]);
   var tQueryResult=easyQueryVer3(tsql, 1, 1, 1); 
   var tarr;
   if(tQueryResult)
   {
   	tarr=decodeEasyQueryResult(tQueryResult);
   	document.all('DirManagerName').value=tarr[0][0];
   }
   //by jiaqiangli 2006-07-25 总监姓名
   tupbranch=tBranchAttr.substr(0,10);
   /*tsql="select BranchManagerName from LABranchGroup where 1=1 "
           +" and EndFlag <> 'Y' and branchAttr = '"+tupbranch+"' " 
           +" and (state<>'1' or state is null) and branchtype='1' ";*/
   tsql = wrapSql(tResourceName,"querysqldes9",[tupbranch]);
   tQueryResult=easyQueryVer3(tsql, 1, 1, 1);
   if(tQueryResult)
   {
   	tarr=decodeEasyQueryResult(tQueryResult);
   	document.all('MajordomoName').value=tarr[0][0];
   } 
   return true;
}
function judgeManager(cManager,cAgentGrade,cZSValue)
{
   if ((cManager==null)||(trim(cManager)==''))
   {
   	//增员非经理级的的代理人不能操作 
   	if (cAgentGrade <= 'A03')
   	{
   		alert('必须增员该销售机构的管理人员！');
   		document.all('AgentGrade').value = '';
   		return false;
        }
        //增员经理级的代理人若该人的职级与销售机构的级别一致则提示将该代理人设为管理人员
        else
        {     
        	if (cAgentGrade > 'A03')
        	{
        	    if (trim(cZSValue)=='0' && cAgentGrade > 'A05')
        	    {
        	    	 alert("该营业组为非直辖组，该代理人不属于该组！");
   	                 document.all('BranchCode').value = '';
   	                 document.all('UpAgent').value = '';
   	                 document.all('GroupManagerName').value = '';
   	                 document.all('DepManagerName').value = '';
   	                 document.all('ManageCom').value = '';
   	                 //document.all('hideManageCom').value = '';
   	                 document.all('hideBranchCode').value = '';
   	                 document.all('ManagerCode').value = '';
   	                 document.all('upBranchAttr').value = '';
        	    	 return false;
        	    }
        	    var str = "";
        	    if (cAgentGrade < 'A06')
        	    {
        	      str = "是否指定该代理人为该销售单位的管理人员？";
        	    }
        	    else
        	    {
        	    	//校验所增人员的级别与直辖组的父机构级别是否对应
        	    	var tBranch = trim(document.all('BranchCode').value);
        	    	var tAgentGrade = trim(document.all('AgentGrade').value);  
//        	    	alert(tAgentGrade);      	    	
                        switch (eval(tAgentGrade.substr(1)))
                        {
                           case 6: 
                           {
                            tBranch = tBranch.substr(0,tBranch.length-3); //高级经理
                            break;
                          }
                          case 7: 
                          {
                           tBranch = tBranch.substr(0,tBranch.length-3); //高级经理
                            break;
                          }
                          case 8: 
                          {
                            tBranch = tBranch.substr(0,tBranch.length-6);   //督导长
                            break;
                          }
                          case 9: 
                          {
                            tBranch = tBranch.substr(0,tBranch.length-8);   //区督导长
                            break;
                          }
                        } 
                        //modify by tongmeng 2005-08-01
                        //增加branchtype='1'的条件
                       /* var strSQL = "select upBranchAttr from laBranchGroup where BranchAttr = '"+tBranch+"' and (state<>'1' or state is null)"
                                     +" and branchtype='1' ";	*/
                        var strSQL = wrapSql(tResourceName,"querysqldes10",[tBranch]);
                       // alert(strSQL);
                        var strQueryResult = easyQueryVer3(strSQL, 1, 1, 1);
                        if (strQueryResult)
                        {                           	
                           var arr = decodeEasyQueryResult(strQueryResult);
                           if (arr[0][0]=='0')
                           {
        	              str = "是否指定该销售单位为该代理人的直辖组？";
                           }
                           else
                           {
                              alert("该营业组不是该职级人员的直辖组！");
                              document.all('AgentGrade').value = '';
                              return false;
                           }                              
                        }else
                        {
                        	alert("销售机构维护不全！");
                        	return false;
                        }
        	    }
        	    if (confirm(str))
        	       document.all('hideIsManager').value = 'true';
        	    else
        	    {
        	       document.all('hideIsManager').value  = 'false';
        	       document.all('BranchCode').value     = '';
        	       //document.all('hideBranchCode').value = '';
        	       return false;
        	    }
        	}        	
        }
   }else
   {
   	//alert('  '+cManager);
   	var tACode = document.all('AgentCode').value;
   	if (tACode == null)
   	  tACode = '';
   	  ////////////////////////////////////
   	  //tongmeng 2005-03-09修改 增加对该机构的管理人员是否在职的判断
   	  if((cAgentGrade>'A03')&&(tACode != cManager))
   	  {var LSQL = "";
   	  	//LSQL="select agentcode from latree where agentcode ='"+cManager+"' and state<>'3'";
   	 LSQL = wrapSql(tResourceName,"querysqldes11",[cManager]);
   	  	//alert('cManager'+cManager);
       var LSQLResult=easyQueryVer3(LSQL,1,1,1)
       if(LSQLResult){
       	alert('该销售单位已存在管理人员！');
   	      	document.all('BranchCode').value = '';
   	        document.all('UpAgent').value = '';
   	        document.all('GroupManagerName').value = '';
   	        document.all('DepManagerName').value = '';
   	        //document.all('hideManageCom').value = '';
   	        document.all('ManageCom').value = '';
   	        document.all('hideBranchCode').value = '';
                document.all('ManagerCode').value = '';
   	        document.all('upBranchAttr').value = '';
   	        document.all('DirManagerName').value = '';
   	        document.all('MajordomoName').value = '';
   	      	return false;
       	    }
   	  	}
   	  ////////////////////////////////////
   	  
  /* 	if ((cAgentGrade > 'A03')&&(tACode != cManager))
   	{
   		alert('该销售单位已存在管理人员！');
   	      	document.all('BranchCode').value = '';
   	        document.all('UpAgent').value = '';
   	        document.all('GroupManagerName').value = '';
   	        document.all('DepManagerName').value = '';
   	        //document.all('hideManageCom').value = '';
   	        document.all('ManageCom').value = '';
   	        document.all('hideBranchCode').value = '';
                document.all('ManagerCode').value = '';
   	        document.all('upBranchAttr').value = '';
   	      	return false;
   	}*/
   	
   	if (cAgentGrade > 'A03')
   	   document.all('hideIsManager').value = 'true';
        else
           document.all('hideIsManager').value  = 'false'; 	
   }
   return true;
}

function changeIntroAgency()
{	
     //by jiaqiangli 2006-07-24
     //解决在输入了推荐人后清空推荐人代码时焦点无法移动的问题
//   if (getWherePart('IntroAgency')=='')
//     return false;
   var tagentcode=trim(document.all('AgentCode').value);
   var tintroagency=trim(document.all('IntroAgency').value);
   if(tagentcode != '')
   {
     if (tintroagency==tagentcode)
     {
        alert('不能与原代理人编码相同!')
       document.all('IntroAgency').value = '';
       return false;
     }
   }
   //by jiaqiangli 增加推荐人管理机构的限制 6位管理机构一致
   //by jiaqiangli 2006-08-31 6位校验放在增员中
//   if(document.all('initOperate').value == 'INSERT') {
       if(!checkIntroMag(document.all('ManageCom').value))
       {
          return false;
       }
//   }
   if(tintroagency != '') {
       var strSQL = "";
       //佟盟 2005-08-18 修改
       // 增加branchtype='1'条件
       /*strSQL = "select AgentCode,ManageCom, AgentGroup from LAAgent  where 1=1 "
           + "and (AgentState is null or AgentState < '03') "
           +" and branchtype='1' "
           //+ "and a.AgentGroup = b.AgentGroup "
           //+ getWherePart('a.AgentGroup','hideAgentGroup')
           //+ getWherePart('a.ManageCom','ManageCom')
           + getWherePart('AgentCode','IntroAgency');	*/
       strSQL = wrapSql(tResourceName,"querysqldes12",[document.all('IntroAgency').value]);
       //alert(strSQL);
       var strQueryResult = easyQueryVer3(strSQL, 1, 1, 1);
       if (!strQueryResult)
       {
   	   alert('不存在该代理人！');
   	   document.all('IntroAgency').value = '';
   	   return false;
        }  
 	//tongmeng 2006-05-16 add
 	getInputAgentName(1);
  }
   /*
   var arr = decodeEasyQueryResult(strQueryResult);
   document.all('AgentGroup').value = arr[0][1]
   document.all('ManageCom').value = arr[0][2];
   //document.all('hideManageCom').value = arr[0][2];
   document.all('hideAgentGroup').value = arr[0][3];*/
   return true;
}
//by jiaqiangli 增加推荐人管理机构的限制 6位管理机构一致
function checkIntroMag(tManageCom)
{	//若不为空,则肯定存在此代理人
	if((document.all('IntroAgency').value) != '')
	{
		var strSQL = "";
                /*strSQL = "select ManageCom from LAAgent  where 1=1 "
                + "and (AgentState is null or AgentState < '03') "
                +" and branchtype='1' "
                + getWherePart('AgentCode','IntroAgency');*/
                strSQL = wrapSql(tResourceName,"querysqldes13",[document.all('IntroAgency').value]);
                var strQueryResult = easyQueryVer3(strSQL, 1, 1, 1);
                if (strQueryResult) {	
                   var arr = decodeEasyQueryResult(strQueryResult);
                   if ((tManageCom == null)||(tManageCom == '')) {
                   	alert('请先录入销售机构后录入推荐人');
                	document.all('IntroAgency').value='';
                	return false;
                   }
                   else {
                      if(tManageCom.substr(0,6)!=arr[0][0].substr(0,6))
                      {
                	alert('推荐人与被推荐人前6位管理机构不一致');
                	document.all('IntroAgency').value='';
                	return false;
                      } 
                  } 
                }
	}
	return true;
}
function afterQuery(arrQueryResult)
{	
	var arrResult = new Array();
	
	resetForm();
	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;		                                  
                document.all('AgentCode').value = arrResult[0][0];
                //document.all('Password').value = arrResult[0][3];
                //document.all('EntryNo').value = arrResult[0][4];
                document.all('Name').value = arrResult[0][5];
                document.all('Sex').value = arrResult[0][6];
                document.all('Birthday').value = arrResult[0][7];
                document.all('NativePlace').value = arrResult[0][8];
                document.all('Nationality').value = arrResult[0][9];
                //document.all('Marriage').value = arrResult[0][10];
                //document.all('CreditGrade').value = arrResult[0][11];
                //document.all('HomeAddressCode').value = arrResult[0][12];
                document.all('HomeAddress').value = arrResult[0][13];
                //document.all('PostalAddress').value = arrResult[0][14];
                document.all('ZipCode').value = arrResult[0][15];
                document.all('Phone').value = arrResult[0][16];
                document.all('BP').value = arrResult[0][17];
                document.all('Mobile').value = arrResult[0][18];
                document.all('EMail').value = arrResult[0][19];
                //document.all('MarriageDate').value = arrResult[0][20];
                document.all('IDNo').value = arrResult[0][21];
                //document.all('Source').value = arrResult[0][22];
                //document.all('BloodType').value = arrResult[0][23];
                document.all('PolityVisage').value = arrResult[0][24];
                document.all('Degree').value = arrResult[0][25];
                document.all('GraduateSchool').value = arrResult[0][26];
                document.all('Speciality').value = arrResult[0][27];
                document.all('PostTitle').value = arrResult[0][28];
                //document.all('ForeignLevel').value = arrResult[0][29];
                //document.all('WorkAge').value = arrResult[0][30];
                document.all('OldCom').value = arrResult[0][31];
                document.all('OldOccupation').value = arrResult[0][32];
                document.all('HeadShip').value = arrResult[0][33];
                //document.all('RecommendAgent').value = arrResult[0][34];
                //document.all('Business').value = arrResult[0][35];
                //document.all('SaleQuaf').value = arrResult[0][36];
                document.all('QuafNo').value = arrResult[0][37];
                //document.all('QuafStartDate').value = arrResult[0][38];
                document.all('QuafEndDate').value = arrResult[0][39];
                document.all('DevNo1').value = arrResult[0][40];
                //document.all('DevNo2').value = arrResult[0][41];
                //document.all('RetainContNo').value = arrResult[0][42];
                //document.all('AgentKind').value = arrResult[0][43];
                //document.all('DevGrade').value = arrResult[0][44];
                //document.all('InsideFlag').value = arrResult[0][45];
                //document.all('FullTimeFlag').value = arrResult[0][46];
                //document.all('NoWorkFlag').value = arrResult[0][47];
                document.all('TrainPeriods').value = arrResult[0][73];
                document.all('EmployDate').value = arrResult[0][49];
                //document.all('InDueFormDate').value = arrResult[0][50];
                //document.all('OutWorkDate').value = arrResult[0][51];
                //document.all('Approver').value = arrResult[0][57];
                //document.all('ApproveDate').value = arrResult[0][58];
                document.all('AssuMoney').value = arrResult[0][59];
                document.all('AgentState').value = arrResult[0][61];
                //document.all('QualiPassFlag').value = arrResult[0][62];
                //document.all('SmokeFlag').value = arrResult[0][63];
                document.all('RgtAddress').value = arrResult[0][64];
                document.all('BankCode').value = arrResult[0][65];
                document.all('BankAccNo').value = arrResult[0][66];
                document.all('Remark').value = arrResult[0][60];
                document.all('Operator').value = arrResult[0][67];   
                document.all('IdType').value=arrResult[0][77]
             //二次增员时，不显示代理人以前的行政信息
             if (document.all('initAgentState').value != '02')
             {      
                //行政信息
//                alert('agentgroup:'+arrResult[0][81]);
                document.all('BranchCode').value = arrResult[0][81+1];
                document.all('hideBranchCode').value = arrResult[0][87+1];
                //document.all('hideManageCom').value = arrResult[0][2];
                document.all('ManageCom').value = arrResult[0][2];
                document.all('IntroAgency').value = arrResult[0][78+1];
                document.all('AgentSeries').value = arrResult[0][79+1];
                document.all('AgentGrade1').value = arrResult[0][80+1];
                //tongmeng 2006-05-18 add
                //查询内部职级
                if (queryOldAgentGrade()== false ) return false; 
                //
                document.all('ManagerCode').value = arrResult[0][77+1];
   	        document.all('upBranchAttr').value = arrResult[0][86+1];
                if (arrResult[0][82+1]!=null && trim(arrResult[0][82+1])!='')
                {                	
                  if (arrResult[0][82+1].indexOf(":")!=-1)
                  {
                    var arrRear = arrResult[0][82+1].split(":");
                    document.all('RearAgent').value = arrRear.length>0?arrRear[0]:'';
                    document.all('RearDepartAgent').value = arrRear.length>1?arrRear[1]:'';
                    document.all('RearSuperintAgent').value = arrRear.length>2?arrRear[2]:'';
                    document.all('RearAreaSuperintAgent').value = arrRear.length>3?arrRear[3]:'';
                  }
                  else
                    document.all('RearAgent').value = arrResult[0][82+1];
                }
                //显式机构代码
                //77-BranchManager 78-IntroAgency 79-AgentSeries 80-AgentGrade 81-BranchAttr(所属组的显式代码) 
                //82-AscriptSeries 83-BranchLevel 84-upBranch 85-BranchManagerName 86-upBranchAttr 87-BranchCode(所属组的隐式代码)
                //alert(arrResult[0][77]+','+arrResult[0][0]);
                //trim(arrResult[0][77])!=trim(arrResult[0][0]) ;)&&(arrResult[0][80]<='A05')
                if (arrResult[0][77+1]!=null)//管理人员=代理人代码
                {
                  //if (arrResult[0][80] <= 'A03') 
                  if(document.all('AgentGrade').value<= 'A03')
                     document.all('UpAgent').value = arrResult[0][77+1]; //组经理 
                  document.all('GroupManagerName').value = arrResult[0][85+1];                 
                }
                //确定部经理 arrResult[0][82]:上级机构
                if ((arrResult[0][84+1]!=null)&&(trim(arrResult[0][84+1])!=''))
                {
                   /*var strSQL = "select BranchManager,BranchManagerName from LABranchGroup where 1=1 "
                              + " and EndFlag <> 'Y' and AgentGroup = '"+arrResult[0][84+1]+"' and (state<>'1' or state is null)";*/	
                   var strSQL = wrapSql(tResourceName,"querysqldes14",[arrResult[0][84+1]]);
     	           //alert('11--'+strSQL+'  '+arrResult[0][82]);
                   var strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
                   if (strQueryResult)
                   {
                       var arr = decodeEasyQueryResult(strQueryResult);
                       if (trim(arrResult[0][77+1])==trim(arrResult[0][0]))
                         document.all('UpAgent').value = trim(arr[0][0]);
                       document.all('DepManagerName').value = trim(arr[0][1]); 
                   }
                }
                //if (arrResult[0][80] >= 'A06')
                if(document.all('AgentGrade').value>='A06')
                  document.all('DepManagerName').value = trim(arrResult[0][5]); //部经理是他本身
                //by jiaqiangli add 2006-07-27 还要查询出区经理和总监姓名
                queryDistMajor(trim(arrResult[0][81+1]));
            }
        }
        
        //tongmeng 2005-08-18 modify
        //add laagent.branchtype='1'
       //var wageSQL="select lawage.agentcode from lawage,laagent where lawage.agentcode ='"+document.all('AgentCode').value+"' and laagent.agentcode=lawage.agentcode and laagent.agentstate<'03' and laagent.branchtype='1'";
       var wageSQL = wrapSql(tResourceName,"querysqldes15",[document.all('AgentCode').value]);
       var wageQueryResult=easyQueryVer3(wageSQL,1,1,1)
       if (wageQueryResult)
       {//by jiaqiangli 2006-07-26 算过佣金之后行政信息不可以修改
        document.all('AgentGrade1').disabled = true;
        document.all('ManageCom').disabled = true;
        document.all('IntroAgency').disabled = true;
        document.all('BranchCode').disabled = true;
        document.all('GroupManagerName').disabled = true;
        document.all('DepManagerName').disabled = true;
        //by jiaqiangli 2006-08-03 区和总监readonly属性的input可以不设这个disabled属性
        document.all('DirManagerName').disabled = true;
        document.all('MajordomoName').disabled = true;
        document.all('RearAgent').disabled = true;
        document.all('RearDepartAgent').disabled = true;
        document.all('RearSuperintAgent').disabled = true;
        document.all('RearAreaSuperintAgent').disabled = true;
       }
       
        
        WarrantorGrid.clearData("WarrantorGrid");
        easyQuery();
        //by jiaqiangli 2006-09-05
        //对于基本信息维护中，关于姓名及身份证进行修改，要求只有二级机构（含）以上人管有权限进行操作
        //初始信息修改在总公司，基本信息修改在分公司
        //by renhailong 2008-05-12
        //对于基本信息维护，初始信息维护，和二次增员要求全部改为总公司
           //by 2009-08-14  将权限下放到分公司.
        if(document.all('hideManageCom').value.length > 4) {
        	   document.all('Name').disabled = true;
        	   document.all('IdType').disabled = true;
        	   document.all('IDNo').disabled = true;
        	   document.all('BankCode').disabled = true;
               document.all('BankAccNo').disabled = true;
     
       }
      
}
function easyQuery()
{  
   // 书写SQL语句
   var strSQL = "";
   /*strSQL = "select * from LAWarrantor where 1=1 "
           + getWherePart('AgentCode');	*/
   strSQL = wrapSql(tResourceName,"querysqldes16",[document.all('AgentCode').value]);
     	 //alert(strSQL);
   turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  
    //alert("担保人信息查询失败！");
    return false;
    }
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  //tArr = decodeEasyQueryResult(turnPage.strQueryResult);
  //turnPage.arrDataCacheSet = chooseArray(tArr,[0,1,2,3,4,5]);
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = WarrantorGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  var tArr = new Array();
  tArr = chooseArray(arrDataSet,[2,3,4,6,8,18,9,10,11]); 
  //调用MULTILINE对象显示查询结果
  
  //displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  displayMultiline(tArr, turnPage.pageDisplayGrid);
}

function changeQuafNo()
{
    //onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')"
     //   onmouseover="value=value.replace(/[^a-zA-Z0-9]/g,'')"   
    //    onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^a-zA-Z0-9]/g,''))"          

	if (getWherePart('QuafNo')=='')
    {
     document.all('QuafNo').value = '';
   return false;
    }
   var strSQL = "";

   //二次增员的身份证肯定是存在的.不需要做校验.离职的不允许做增员.
  if (document.all('initAgentState').value != '02')
   {
  //strSQL = "select * from LAAgent where 1=1 and (branchtype='1' or (branchtype<>'1'  and agentstate<'03')) "
  //+ getWherePart('QuafNo');	
  strSQL = wrapSql(tResourceName,"querysqldes17",[document.all('QuafNo').value]);
  //alert(strSQL);
  var strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1); 
  if (strQueryResult)
  { 
  	alert('该代理人资格证号已存在!');
  	document.all('QuafNo').value = '';
  	return false;
  }
   }
  return false;
}
function changeIDNo()
{
   if (getWherePart('IDNo')==''|| getWherePart('IdType')=='')
     {
      document.all('IDNo').value = '';
    return false;
     }
    var strSQL = "";
   //tongmeng 2005-08-18 modify
   //add branchtype='1'
   //tongmeng 2005-11-04 modify
   //修改对身份证的校验方式
   //二次增员的身份证肯定是存在的.不需要做校验.离职的不允许做增员.
   if (document.all('initAgentState').value != '02')
    {
   /*strSQL = "select * from LAAgent where 1=1 and (branchtype='1' or (branchtype<>'1'  and agentstate<'03')) "
   + getWherePart('IDNo')+getWherePart('IdType');*/
   strSQL = wrapSql(tResourceName,"querysqldes18",[document.all('IDNo').value,document.all('IdType').value]);
   var strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1); 
   if (strQueryResult)
   { 
   	alert('该身份证号已存在!');
   	document.all('IDNo').value = '';
   	return false;
   }
   if(document.all('IdType').value=='0')
   { 
   var IDNo=trim(document.all('IDNo').value);
   if(IDNo.length==15 )
   {
   var IDNo=trim(document.all('IDNo').value);
   /*var tsql1="select agentcode from laagent where 1=1 and (branchtype='1' or (branchtype<>'1'  and agentstate<'03'))"
   +" and idno like substr('"+IDNo+"',0,6)||'%%'||substr('"+IDNo+"',7,9)||'%%' ";*/
   var tsql1 = wrapSql(tResourceName,"querysqldes19",[IDNo,IDNo]);
   var strQueryResult=easyQueryVer3(tsql1, 1, 1, 1);
   if (strQueryResult)
   {
   	alert('已存在18位的身份证号');
  
   	document.all('IDNo').value = '';
   
   	return false;
   }  
    }    
    if(IDNo.length==18)
   {
      /*var tsql2="select agentcode from laagent where 1=1 and (branchtype='1' or (branchtype<>'1'  and agentstate<'03'))"
      +" and  idno like substr('"+IDNo+"',0,6) ||'%%'||substr('"+IDNo+"',9,9)||'%%' ";*/
      var tsql2 = wrapSql(tResourceName,"querysqldes20",[IDNo,IDNo]);
      var strQueryResult=easyQueryVer3(tsql2, 1, 1, 1);
      if (strQueryResult)
   {
   	alert('已存在15位的身份证号！');
   	document.all('IDNo').value = '';
   return false;
   } 
    }
    }
   }
  //add by renhailong 2008-07-22
if(document.all('initOperate').value == 'INSERT'){
   if(document.all('Name').value==null || document.all('Name').value=='')
   {alert('必须先录入姓名');
   document.all('IDNo').value = '';
   return false;}
   if(document.all('IdType').value==null || document.all('IdType').value=='')
   {alert('必须先录入证件类型');
   document.all('IDNo').value = '';
   return false;
   }
   strSQL = "";
   /*strSQL="select a.assumoney from laassumoney  a where 1=1 and branchtype='1' and ConfMakeDate is not null and AssuCheckState='0'"
    +" and not exists (select 1 from lajagetassumoney where a.SerialNo=SerialNo) "
    + getWherePart('agentname','Name')+getWherePart('IdType')+getWherePart('IDNo');*/
   strSQL = wrapSql(tResourceName,"querysqldes21",[document.all('Name').value,document.all('IdType').value,document.all('IDNo').value]);
    var strQueryResult=easyQueryVer3(strSQL, 1, 1, 1);
   if (!strQueryResult)
   {
   	alert('该代理人没有交押金！');
   	document.all('AssuMoney').value = '';
   	document.all('IDNo').value = '';
    return false;
   }  
  var arr = decodeEasyQueryResult(strQueryResult);
 document.all('AssuMoney').value = trim(arr[0][0]);
   
   }
   return true; 
}
//校验育成代理人
function checkRearAgent()
{
   var strSQL = "",str = "";
   var strQueryResult = null;
   // tongmeng 2005-08-18 modify
   //add branchtype='1'
   //strSQL = "select AgentCode from LAAgent where (AgentState < '03' or AgentState is not null) and branchtype='1'"
   strSQL = wrapSql(tResourceName,"querysqldes22",["1"]);
   //育成代理人
   if (trim(document.all('RearAgent').value)=='')
     return true;
   if (trim(document.all('RearAgent').value)==trim(document.all('AgentCode').value)) 
   {
      alert('与原代理人编码相同!');
      document.all('RearAgent').value = '';
      return false;
   } 
   str = getWherePart('AgentCode','RearAgent');
            
   //alert(strSQL+str);
   strQueryResult = easyQueryVer3(strSQL+str, 1, 1, 1);
   if (!strQueryResult)
   {
   	alert('不存在该育成代理人！');
   	document.all('RearAgent').value = '';
   	return false;
   }  
   //增部代理人
   if (trim(document.all('RearDepartAgent').value)=='')
     return true;
   if(trim(document.all('RearDepartAgent').value)==trim(document.all('AgentCode').value))
   {
     alert('与原代理人编码相同!');
     document.all('RearDepartAgent').value = '';
     return false;
   }
   str = getWherePart('AgentCode','RearDepartAgent');
            
   //alert(strSQL+str);
   strQueryResult = easyQueryVer3(strSQL+str, 1, 1, 1);
   if (!strQueryResult)
   {
   	alert('不存在该增部代理人！');
   	document.all('RearDepartAgent').value = '';
   	return false;
   } 
   //育成督导长代理人
   if (trim(document.all('RearSuperintAgent').value)=='')
     return true;
     
   if (trim(document.all('RearSuperintAgent').value)==trim(document.all('AgentCode').value))
   {
     alert('与原代理人编码相同!');
     document.all('RearSuperintAgent').value = '';
     return false;
   }  
   str = getWherePart('AgentCode','RearSuperintAgent');
            
   //alert(strSQL+str);
   strQueryResult = easyQueryVer3(strSQL+str, 1, 1, 1);
   if (!strQueryResult)
   {
   	alert('不存在该育成督导代理人！');
   	document.all('RearSuperintAgent').value = '';
   	return false;
   }
   //育成区域督导长代理人
   if (trim(document.all('RearAreaSuperintAgent').value)=='')
     return true;
   if (trim(document.all('RearAreaSuperintAgent').value)==trim(document.all('AgentCode').value))
   {
     alert('与原代理人编码相同!'); 
     document.all('RearAreaSuperintAgent').value = '';
     return false;
   } 
   str = getWherePart('AgentCode','RearAreaSuperintAgent');
            
   //alert(strSQL+str);
   strQueryResult = easyQueryVer3(strSQL+str, 1, 1, 1);
   if (!strQueryResult)
   {
   	alert('不存在该育成区域督导代理人！');
   	document.all('RearAreaSuperintAgent').value = '';
   	return false;
   }
   return true;
}

function agentConfirm()
{

   if (getWherePart('AgentCode')=='')
   {
     return false;
   }     
   // 书写SQL语句
   var strSQL = "";
   //tongmeng 2005-11-04 modify
   //只有个险的人才能作二次增员操作
   /*strSQL = "select a.* from LAAgent a where 1=1 and a.branchtype='1' "
          //+ "and a.AgentGroup = b.AgentGroup "
	  + getWherePart('a.AgentCode','AgentCode');*/
   strSQL = wrapSql(tResourceName,"querysqldes23",[document.all('AgentCode').value]);
//   alert(strSQL);   
   var strQueryResult = easyQueryVer3(strSQL,1,1,1);
   if(!strQueryResult)
   {
     alert('不存在该代理人！');
     document.all('AgentCode').value = '';
     return false;	
   }
   var arr = decodeEasyQueryResult(strQueryResult);
   //tongmeng 2005-11-07 add
   //防止做二次增员时，该代理人有一个在职的续收员的工号
   var tIdno = arr[0][21];
   /*strQ = "select * from laagent where 1=1 and (branchtype='4' or branchtype='5') "
        + " and idno='"+tIdno+"' and agentstate<='02'" ;*/
   strQ = wrapSql(tResourceName,"querysqldes24",[tIdno]);
    //   prompt('1',strQ);
   var tQueryResult = easyQueryVer3(strQ,1,1,1);
   if(tQueryResult)
   {
     alert('存在一个在职的续收员与该代理人身份证号相同！');
     document.all('AgentCode').value = '';
     return false;	
   }
   var AgentState = arr[0][61];
   if (AgentState==null||AgentState=='01'||AgentState=='02')
   {
   	alert('该代理人未离职，不能作二次增员！');
   	document.all('AgentCode').value = '';
   	return false;
   }
   if (trim(AgentState) == '04')
   {
   	alert('该代理人已离职两次，不能再次增员！');
   	document.all('AgentCode').value = '';
   	return false;
   }	 
  // alert(AgentState);
   if (trim(AgentState)=='03')
   {
   	//tongmeng 2006-02-28 modify
   	//如果操作员是王淳,杨华,王莉的话,不受离司半年的限制
   	
   	var tOperator = document.all('hideOperator').value;
   	var	currentdate=document.all('hideCurrentDate').value;
 
   	//var tTempAgentCode = document.all('AgentCode').value;
//   	if(tOperator!='000055'&&tOperator!='000029'&&tOperator!='000136')
//   	{ if(tTempAgentCode.substr(0,4)!='8621') {
//   		if (!compare(arr[0][51]))
//   		{
//   	  		alert("该代理人在离职半年后才可重新入司!");
//   	  		document.all('AgentCode').value='';
//   	  		return false;
//   		}
//   	 }
//    }
//    else
//    {
//comment by jiaqiangli 2007-04-26 马化伟需求取消王淳,杨华,王莉的8611特权
      //add by jiaqiangli 2007-06-28 8621老兵回营方案结束 恢复6个月的校验
      //add by jiaqiangli 2008-03-26 允许她有权限
    	
      
    	if(tOperator != '000146' && (tOperator!='210027'||currentdate>='2009-01-01'))
    	{
    		if (!compare(arr[0][51]))
   			{
   	  			alert("该代理人在离职半年后才可重新入司!");
   	  			document.all('AgentCode').value='';
   	  			return false;
   			}
    	}
    //}	
   }
   
   //add by tongmeng 2005-08-10
   //修改增加对考核清退人员离司次数的校验
    if (trim(AgentState)=='05')
    {
//    	var tSQL="select departtimes from ladimission where agentcode='"+document.all('AgentCode').value+"' ";
    	var tSQL = wrapSql(tResourceName,"querysqldes25",[document.all('AgentCode').value]);
    	var strQueryResult1 = easyQueryVer3(tSQL,1,1,1);
    	if(!strQueryResult1)
   		{
     		alert('查询该代理人离职信息错误!')
     		document.all('AgentCode').value = '';
     		return false;	
   		}
    	var arr1 = decodeEasyQueryResult(strQueryResult1);
    	if(arr1[0][0]=='2')
    	{
    		alert("该代理人已离职两次，不能再次增员!");
    		document.all('AgentCode').value='';
    		return false;
    	}
    		//tongmeng 2006-02-28 modify
   			//如果操作员是王淳,杨华,王莉的话,不受离司半年的限制
   	
   	    var tOperator = document.all('hideOperator').value;
   	    var	currentdate=document.all('hideCurrentDate').value;
   	    //alert(tOperator);
   	    //var tTempAgentCode = document.all('AgentCode').value;
//    	if(tOperator!='000055'&&tOperator!='000029'&&tOperator!='000136')
//   	    {  if(tTempAgentCode.substr(0,4)!='8621') {
//    		if (!compare(arr[0][51]))
//   			{
//   	  			alert("该代理人在离职半年后才可重新入司!");
//   	  			document.all('AgentCode').value='';
//   	  			return false;
//   			}
//   		 }
//   		}
//   		else
//    	{
//comment by jiaqiangli 2007-04-26 马化伟需求取消王淳,杨华,王莉的8611特权
        //add by jiaqiangli 2007-06-28 8621老兵回营方案结束 恢复6个月的校验
        //add by jiaqiangli 2008-03-26 允许她有权限
    	if(tOperator != '000146' && (tOperator!='210027'||currentdate>='2009-01-01'))
    		{
    			if (!compare(arr[0][51]))
   				{
   	  				alert("该代理人在离职半年后才可重新入司!");
   	  				document.all('AgentCode').value='';
   	  				return false;
   				}
    		}
    	//}	
    }
    
   
   if (!afterQuery(arr))
     
     return false;
      return true;
     
}

//二次增员必须是在离职后半年
function compare(DepartDate)
{
	var d = new Date();
	var month,year,day;
	day = d.getDate();
	month = d.getMonth() + 1 - 6;
	
//	alert("month"+month);
	if (month>0)
	{
		year=d.getYear();
	}
       else	
	if (month == 0)
	{
		//tongmeng 2006-06-12 modify
		//如果month为0的话,应该把month改为12
	   month=12;
	   year=d.getYear()-1;
	}else
	if (month < 0)
	{
	   month=12+month;
	   year=d.getYear()-1;
	}	
	
	if (month.toString().length == 1)
	  month='0'+month;
	if (day.toString().length == 1)
	  day='0'+day;
	var dd = year+'-'+month+'-'+day;
//	alert("year"+year);
//	alert(month);
//	alert("day"+day);
//	alert(dd+'   '+DepartDate);
	if (trim(dd)<trim(DepartDate))
	  return false;
  	return true;
}
function saveForm()
{
	mOperate = "INSERT||MAIN";
	submitForm();
}
//佟盟2005-03-15增加
//判断增加的育成人是否符合条件(是否与该代理人的职级相符,是否与该代理人在同一个管理机构下)
function DecideRearAgent(t)
{
	var tsql="";
	//tsql="select agentcode from latree where 1=1 and agentgrade like 'A%%' ";
	var sqlpara1='';
	var sqlpara2='';
	var sqlpara3='';
	var sqlpara4='';
	//组育成人
	if(t=='1')
	//tsql=tsql+" and agentcode='"+document.all('RearAgent').value+"' and agentgrade>'A03'";
	sqlpara1 = document.all('RearAgent').value;
	//部育成人
	else if(t=='2')
   //tsql=tsql+" and agentcode='"+document.all('RearDepartAgent').value+"' and agentgrade>'A05'";
	sqlpara2 = document.all('RearDepartAgent').value;
    //区育成人
	else if(t=='3')
    //tsql=tsql+" and agentcode='"+document.all('RearSuperintAgent').value+"' and agentgrade>'A07'";
	sqlpara3 = document.all('RearSuperintAgent').value;
    //总监育成人 
	else if(t=='4')
    //tsql=tsql+" and agentcode='"+document.all('RearAreaSuperintAgent').value+"' and agentgrade>'A08'";
	sqlpara4 = document.all('RearAreaSuperintAgent').value;
    
    //tsql=tsql+"and state<>'3'"+getWherePart("managecom","ManageCom","like");
    tsql = wrapSql(tResourceName,"querysqldes26",[sqlpara1,sqlpara2,sqlpara3,sqlpara4,document.all('ManageCom').value]);
    var tsqlResult = easyQueryVer3(tsql,1,1,1);
    if(!tsqlResult){ 
    	return false; 
    }    
    return true;    	    	    	  	
}

function checkage()
{
  var managecom = document.all('hideManageCom').value;
  var idno=document.all('IDNo').value;
  
  var dirthdate=document.all('Birthday').value;
  var currentdate=getCurrentDate();
  //var strSQL="select months_between('"+currentdate+"','"+dirthdate+"') from dual";
  var strSQL = wrapSql(tResourceName,"querysqldes27",[currentdate,dirthdate]);
  var strQueryResult = easyQueryVer3(strSQL, 1, 1, 1);
  var arr = decodeEasyQueryResult(strQueryResult);
  var months=arr[0][0];
  if(managecom!='86'&&(months>=612))
  {
      //var sql="select * from LAAgentAuthorize where idno='"+idno+"' and managecom like '"+managecom+"%%' and flag='1'";
      var sql = wrapSql(tResourceName,"querysqldes28",[idno,managecom]);
      strQueryResult = easyQueryVer3(sql, 1, 1, 1);
      if (strQueryResult)
      {
   	return true;
      } 	
  	
    alert("没有权限对五十周岁以上的人员增员!");
    return false;
  }
   return true;		
}
/////////////////////////////
//tongmeng 2006-04-26 add
//由新职级去查询对应的老职级
function queryOldAgentGrade()
{
  var sAgentGrade = document.all('AgentGrade1').value;
  /*var sSQL = "select gradecode from laagentgrade where gradecode like 'A%%' and branchtype='1' "
             + "  and gradeid =(select max(gradeid) from laagentgrade where gradecode1='"+sAgentGrade+"' ) ";*/
  var sSQL = wrapSql(tResourceName,"querysqldes29",["'"+sAgentGrade+"'"]);
  var strQueryResult = easyQueryVer3(sSQL,1,1,1);
  if(!strQueryResult)
  {
  	document.all('AgentGrade').value ='';
  	//by jiaqiangli 2006-10-08 add
  	alert('M系列职级转化为A系列职级出错!');
  	document.all('AgentGrade1').value ='';
  	document.all('BranchCode').value ='';
  	return false;
  }
  else
  {
  	var arr = decodeEasyQueryResult(strQueryResult);
  	var sResult=arr[0][0];
  //	if(sResult == 'A05')
  //	   	sResult = 'A04';
  //	if(sResult == 'A07')
  //	   	sResult = 'A06';
    document.all('AgentGrade').value = sResult; 
    return true;
  }
}
//////////////////////////
//tongmeng 2006-04-26 add
//增加如果增员主管是主管级别以上人员,如果有多级育成人
//,并且多级育成人不相同,或者不同为空,给予提示
//by jiaqiangli modified 2006-08-01 增加新需求
function checkAllRearAgent()
{
	var sAgentGrade = document.all('AgentGrade').value;
	var sRear1 = "";
	var sRear2 = "";
	var sRear3 = "";
	var sRear4 = "";
	//var stempFlag = "";
	//stempFlag: 00 :各级育成人为空
	//           01 :各级育成人不同
	//           02 :漏录
	sRear1 = trim(document.all('RearAgent').value);
	sRear2 = trim(document.all('RearDepartAgent').value);
	sRear3 = trim(document.all('RearSuperintAgent').value);
	sRear4 = trim(document.all('RearAreaSuperintAgent').value);
	//处理组育成人
	if(sAgentGrade>='A04' && sAgentGrade<='A05')
	{
		if(sRear1 == null||sRear1 == '')
		{
			if(!confirm("组育成人为空，是否保存?"))
			{
				return false;
			}
		    else
		    	return true;
		}
	}
    else if(sAgentGrade>='A06' && sAgentGrade<='A07')
    {
    	if((sRear1 == null||sRear1 == '')&&(sRear2 == null||sRear2 == ''))
		{
			if(!confirm("各级育成人均为空，是否保存?"))
			{
				return false;
			}
		    else
		    	return true;
		}
	    //两者至少有一不为空	
	    else 
	    {
	    	if(sRear1 == '' && sRear2 != '')
	    	{
	    		alert("组育成人漏录,请重新输入");
	    		return false;
	    	}
	        else if(sRear1 != '' && sRear2 == '')
	        {
	        	if(!confirm("部育成人为空，是否保存?"))
			{
				return false;
			}
		        else
		    	        return true;
	        }
	        //两者都不为空
	        else if(sRear1 != sRear2)
	        {
	            if(!confirm("各级育成人不同,是否保存?"))
	            	return false;
	            else 
	            	return true;
	        }
	    }
    } 
    else if(sAgentGrade=='A08')
    {   //包含yyy(y表示为空)(n表示不为空)
    	if((sRear1 == null||sRear1 == '')&&(sRear2 == null||sRear2 == '')&&(sRear3 == null||sRear3 == ''))
		{
			if(!confirm("各级育成人均为空，是否保存?"))
			{
				return false;
			}
		    else
		    	return true;
		}
	    //三者至少有一不为空
	    else 
	    {   //包含yyn,ynn,nyn,yny
	    	if(((sRear1 == '' || sRear2 == '')&&sRear3 != '') || (sRear1 == '' && sRear2 != ''&&sRear3 == ''))
	    	{
	    		alert("组或部育成人漏录,请重新输入");
	    		return false;
	    	}
	    	//包含nyy,nny
	        else if((sRear1 != '' && sRear2 == ''&&sRear3 == '') ||((sRear1 != '' && sRear2 != ''&&sRear3 == '')))
	        {
	        	if(!confirm("育成人漏录,只有组育成人或只有组部育成人,是否保存?"))
	            	     return false;
	                else 
	            	     return true;
	        }
	    	//包含nnn
	        else if((sRear1 != sRear2)||(sRear1 != sRear3))
	        {
	            if(!confirm("各级育成人不同,是否保存?"))
	            	return false;
	            else 
	            	return true;
	        }
	    }
    } 
    else if(sAgentGrade=='A09')
    {   //包含yyyy(y表示为空)(n表示不为空)
    	if((sRear1 == null||sRear1 == '')&&(sRear2 == null||sRear2 == '')&&(sRear3 == null||sRear3 == '')&&(sRear4 == null||sRear4 == ''))
		{
			if(!confirm("各级育成人均为空，是否保存?"))
			{
				return false;
			}
		    else
		    	return true;
		}
	    else 
	    {   //第一个条件包含了7情况,前三者至少有一为空
	    	//第二个条件包含了4情况
	    	if(((sRear1 == '' || sRear2 == ''||sRear3 == '')&&sRear4 != '') ||
	    	   (sRear1 == '' && sRear2 != ''&&sRear3 == ''&&sRear4 == '') ||
	    	   (sRear1 == '' && sRear2 != ''&&sRear3 != ''&&sRear4 == '') ||
	    	   (sRear1 != '' && sRear2 == ''&&sRear3 != ''&&sRear4 == '') ||
	    	   (sRear1 == '' && sRear2 == ''&&sRear3 != ''&&sRear4 == '')
	    	   )
	    	{
	    		alert("组或部或区育成人漏录,请重新输入");
	    		return false;
	    	}
	    	//包含了3情况
	        else if ((sRear1 != '' && sRear2 == ''&&sRear3 == ''&&sRear4 == '') ||
	                 (sRear1 != '' && sRear2 != ''&&sRear3 == ''&&sRear4 == '') ||
	                 (sRear1 != '' && sRear2 != ''&&sRear3 != ''&&sRear4 == '')
	                  )
	        {
	        	if(!confirm("育成人漏录,只有组或组部或组部区育成人,是否保存?"))
	            	     return false;
	                else 
	            	     return true;
	        }
	    	//包含nnnn
	        else if((sRear1 != sRear2)||(sRear1 != sRear3)||(sRear1 != sRear4))
	        {
	            if(!confirm("各级育成人不同,是否保存?"))
	            	return false;
	            else 
	            	return true;
	        }
	    }
    }
    return true; 
}
//tongmeng 2006-05-16 add
//提示录入的代理人姓名
function getInputAgentName(sType)
{
   var stempAgentGrade = "";
   stempAgentGrade = document.all('AgentGrade1').value;
   if(stempAgentGrade == null||stempAgentGrade == '')
   {	
   		alert("请先输入代理人职级");
   		switch(eval(sType))
   		{
   			case 1:
   				document.all('IntroAgency').value='';
   				break;
   			case 2:
   				document.all('RearAgent').value='';
   				break;
   	    	        case 3:
   				document.all('RearDepartAgent').value='';
   				break;
   			case 4:
   				document.all('RearSuperintAgent').value='';
   				break;
   			case 5:
   				document.all('RearAreaSuperintAgent').value='';
   				break;
   		}
   		return false;
   }
   //by jiaqiangli 增加育成人输入限制提示
//   if(CheckRearInput(sType) == false)
//      return false;
   var sAgentCode = "";
   var sAlertName = "";
   switch(eval(sType))
   {
   		case 1:
   			sAlertName = '推荐人姓名:';
   			sAgentCode = document.all('IntroAgency').value;
   			break;
   		case 2:
   			sAlertName = '组育成人姓名:';
   			sAgentCode = document.all('RearAgent').value;
   			break;
   	        case 3:
   			sAlertName = '部育成人姓名:';
   			sAgentCode = document.all('RearDepartAgent').value;
   			break;
   		case 4:
   			sAlertName = '区育成人姓名:';
   			sAgentCode = document.all('RearSuperintAgent').value;
   			break;
   		case 5:
   			sAlertName = '总监育成人姓名:';
   			sAgentCode = document.all('RearAreaSuperintAgent').value;
   			break;
   }
   var sResultName = "";
   if(sAgentCode!='')
   {
   		//var tempSql = "select name from laagent where agentcode='"+sAgentCode+"' ";
   		var tempSql = wrapSql(tResourceName,"querysqldes30",[sAgentCode]);
   
   		var tempQueryResult = easyQueryVer3(tempSql, 1, 1, 1);
   	if(!tempQueryResult)
   	{
   		return false;
   	}
   	else
   	{
   	   	var arr = decodeEasyQueryResult(tempQueryResult);
  	   	sResultName=arr[0][0];
   	}
   	alert(sAlertName+sResultName);
   }
   return true;
}
function checkEdition()
{
	//ldcode  基本法与分公司对照关系
	//relatype='agentedition'
	//code1 :采用基本法的机构 
	//code2 :基本法版本   01:老版本  02:新版本
	//code3 :销售渠道
	//othersign :数据迁移状态  1:数据迁移前  2:数据迁移后
	var dEdition = "";
	var dState = "";
	var dCom = trim(document.all('ManageCom').value);
	dCom = dCom.substr(0,4);
	/*var dSQL = "select code2,othersign from ldcoderela where relatype='agentedition' "
	         + " and code3 = '1' and code1='"+dCom+"' ";*/
	var dSQL = wrapSql(tResourceName,"querysqldes31",[dCom]);
	var dQueryResult = easyQueryVer3(dSQL, 1, 1, 1);
	if(!dQueryResult)
   	{
   		alert("没有指定"+dCom+"的基本法版本!");
   		return false;
   	}
   	else
   	{
   	   	var arr = decodeEasyQueryResult(dQueryResult);
  	   	dEdition = arr[0][0];
  	   	dState = arr[0][1];
  	   	if(dEdition=='01')
  	   	{
  	   		alert(dCom+"使用老版基本法,不能在此进行操作");
  	   		return false;
  	   	}
  	    else  if(dEdition=='02')
  	    {
  	    	if(dState=='1')
  	    	{
  	    		alert(dCom+"还没有做数据迁移,请在数据迁移完成后再操作!");
  	    		return false;
  	    	}
  	        else if(dState=='2')
  	        {
  	    		return true;
  	    	}
  	    }
   	}
   	return true;
}
//by jiaqiangli 2006-08-04
//根据输入职级确定的育成人输入框变灰的需求处理
//不允许输入的在保存之前将其清空，防止用户输入了职级之后变化了职级
//输入框控件的不支持，另有一种方法将职级选择框换成输入框
//输入育成人代码时增加判断
//所有的这些只是符合用户习惯，但是即使是录入了不应该输入的后台也不处理
function ControlRearInput()
{      
	var agentgrade=trim(document.all('AgentGrade').value);
	//业务员没有育成人
	if(agentgrade<'A04')
	{
		document.all('RearAgent').value = '';
                document.all('RearDepartAgent').value = '';
                document.all('RearSuperintAgent').value = '';
                document.all('RearAreaSuperintAgent').value = '';
	}
	//组经理最多只有组育成人
        else if(agentgrade<='A05')
        {
                document.all('RearDepartAgent').value = '';
                document.all('RearSuperintAgent').value = '';
                document.all('RearAreaSuperintAgent').value = '';
        }
        //部经理最多只有组部育成人
        else if(agentgrade<='A07')
        {
                document.all('RearSuperintAgent').value = '';
                document.all('RearAreaSuperintAgent').value = '';
        }
        //区经理最多只有组部区育成人
        else if(agentgrade=='A08')
        {
                document.all('RearAreaSuperintAgent').value = '';
        }
}
//by jiaqiangli 2006-08-04 输入育成时给出必要的警告
//sType 表示录入的育成人
//2 输入组育成人   3 输入部育成人
//4 输入区育成人   5 输入总监育成人
function CheckRearInput(sType)
{
	//先将旧职级换算到新职级
	if (queryOldAgentGrade()== false ) return false; 
	var AAgentGrade=document.all('AgentGrade').value;
	if(sType!=1) {
	//业务员没有育成人
	if(AAgentGrade<'A04')
	{
		alert('业务员没有育成人');
		document.all('RearAgent').value = '';
                document.all('RearDepartAgent').value = '';
                document.all('RearSuperintAgent').value = '';
                document.all('RearAreaSuperintAgent').value = '';
                return false;
	}
	//组经理最多只有组育成人
        else if(AAgentGrade<='A05')
        {       
        	if(sType==3)
        	{
        		alert('组经理最多只有组育成人');
        		document.all('RearDepartAgent').value = '';
                        return false;
                }
                else if(sType==4)
                {
                	alert('组经理最多只有组育成人');
                        document.all('RearSuperintAgent').value = '';
                        return false;
                }
                else if(sType==5) 
                {
                	alert('组经理最多只有组育成人');
                        document.all('RearAreaSuperintAgent').value = '';
                        return false;
                }
        }
        //部经理最多只有组部育成人
        else if(AAgentGrade<='A07')
        {
        	if(sType==4)
        	{
        		alert('部经理最多只有组部育成人');
                        document.all('RearSuperintAgent').value = '';
                        return false;
                }
                else if(sType==5)
                {
                	alert('部经理最多只有组部育成人');
                        document.all('RearAreaSuperintAgent').value = '';
                        return false;
                }
        }
        //区经理最多只有组部区育成人
        else if(AAgentGrade=='A08')
        {
        	if(sType==5)
        	{
        		alert('区经理最多只有组部区育成人');
                        document.all('RearAreaSuperintAgent').value = '';
                        return false;
                }
        }
        return true;
        }
}
function afterCodeSelect( cCodeName, Field ) {
	try	{
	   
	   
	   //相当于下拉框的onchange事件的接口;
	    if(cCodeName =='IdType'){
	    
	     if (document.all('initAgentState').value != '02')
      { 
       if( document.all('hideManageCom').value.length > 2)
         { 
       
         if(document.all('IdType').value!='0')  
           {
           
           alert("录入除身份证以外的其它证件类型仅限于总公司");
               document.all('IdType').value="";
                return false;}
            }
     }
	
	}
		if(cCodeName =='AgentGrade1')
		{
			var agentgrade = Field.value;
			if(agentgrade<'M03')
			{
		        document.all('RearAgent').disabled = true;
                document.all('RearDepartAgent').disabled = true;
                document.all('RearSuperintAgent').disabled = true;
                document.all('RearAreaSuperintAgent').disabled = true;
			}
			//组经理最多只有组育成人
       	 	else if(agentgrade=='M03')
        	{	
        	    document.all('RearAgent').disabled = false;
                document.all('RearDepartAgent').disabled = true;
                document.all('RearSuperintAgent').disabled = true;
                document.all('RearAreaSuperintAgent').disabled = true;
        	}
        	//部经理最多只有组部育成人
        	else if(agentgrade=='M04')
        	{
        	    document.all('RearAgent').disabled = false;
        	    document.all('RearDepartAgent').disabled = false;
                document.all('RearSuperintAgent').disabled = true;
                document.all('RearAreaSuperintAgent').disabled = true;
       	 	}
        	//区经理最多只有组部区育成人
        	else if(agentgrade=='M05')
        	{
        	    document.all('RearAgent').disabled = false;
        	    document.all('RearDepartAgent').disabled = false;
                document.all('RearSuperintAgent').disabled = false;
                document.all('RearAreaSuperintAgent').disabled = true;
        	}
        	else if(agentgrade=='M06')
        	{
        	    document.all('RearAgent').disabled = false;
        	    document.all('RearDepartAgent').disabled = false;
                document.all('RearSuperintAgent').disabled = false;
                document.all('RearAreaSuperintAgent').disabled = false;
        	}
    	}
    	
    	
	}
	catch( ex ) {
	}
}
function ControlA09()
{
	if(document.all('AgentGrade1').value=='M06'||document.all('AgentGrade1').value=='M05') {
		    //by jiaqiangli 2006-09-05
        	//总监的录入，要求只有二级机构（含）以上人管有权限进行操作
        	//初始信息修改在总公司，基本信息修改在分公司
        	//by zhujch 2008-06-18
        	//添加对督导级别的新增限制  要求只有二级机构（含）以上人管有权限进行操作
        	if(document.all('hideManageCom').value.length > 4) {
        		alert('总监或督导的录入，要求只有二级机构（含）以上人管有权限进行操作');
        		document.all('AgentGrade1').value='';
        		document.all('BranchCode').value='';
        		return false;
        	}
	}
	return true;
}
