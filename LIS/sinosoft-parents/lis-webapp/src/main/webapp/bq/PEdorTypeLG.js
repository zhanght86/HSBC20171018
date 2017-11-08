var showInfo;

var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var sqlresourcename = "bq.PEdorTypeLGInputSql";


function getCustomerGrid()
{
	//alert("a");
    var tContNo=document.all("ContNo").value;
    if(tContNo!=null&&tContNo!="")
    {
/*        var strSQL = " Select a.appntno, '投保人', a.appntname, "
                    +" (select trim(n.code)||'-'||trim(n.CodeName) from LDCode n where trim(n.codetype) = 'sex' and trim(n.code) = trim(appntsex)),"
                    +" a.appntbirthday, "
                    +" (select trim(m.code)||'-'||trim(m.CodeName) from LDCode m where trim(m.codetype) = 'idtype' and trim(m.code) = trim(idtype)), "
                    +" a.idno "
                    +" From lcappnt a Where contno='" + tContNo+"'"
                    +" Union"
                    +" Select i.insuredno, '被保人', i.name, "
                    +" (select trim(u.code)||'-'||trim(u.CodeName) from LDCode u where trim(u.codetype) = 'sex' and trim(u.code) = trim(sex)),"
                    +" i.Birthday,"
                    +" (select trim(y.code)||'-'||trim(y.CodeName) from LDCode y where trim(y.codetype) = 'idtype' and trim(y.code) = trim(idtype)), "
                    +" i.IDNo "
                    +" From lcinsured i Where contno='" + tContNo+"'";
        //prompt("",strSQL);
*/        
    var strSQL = "";
	var sqlid1="PEdorTypeLGInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(tContNo);//指定传入的参数
	mySql1.addSubPara(tContNo);
	strSQL=mySql1.getString();
        
        arrResult = easyExecSql(strSQL);
        
        if (arrResult)
        {
            displayMultiline(arrResult,CustomerGrid);
        }
    }
}

function getCustomerBnfGrid()
{
    var tContNo=document.all("ContNo").value;
    if(tContNo!=null&&tContNo!="")
    {
/*        var strSQL = "select a.name,(select trim(n.code)||'-'||trim(n.CodeName) from LDCode n where trim(n.codetype) = 'sex' and trim(n.code) = trim(sex)),"
        	+"  a.birthday,(select trim(m.code)||'-'||trim(m.CodeName) from LDCode m where trim(m.codetype) = 'idtype' and trim(m.code) = trim(idtype)) "
        	+"  ,a.idno,(select trim(m.code)||'-'||trim(m.CodeName) from LDCode m where trim(m.codetype) = 'bnftype' and trim(m.code) = trim(bnftype)), "
        	+"  (select trim(m.code)||'-'||trim(m.CodeName) from LDCode m where trim(m.codetype) = 'relation' and trim(m.code) = trim(a.relationtoinsured)),"
        	+"  (select trim(m.code)||'-'||trim(m.CodeName) from LDCode m where trim(m.codetype) = 'bnforder' and trim(m.code) = trim(a.bnfgrade)),a.bnflot,a.bankcode,a.BankAccNo,a.AccName from lcbnf "
        	+"a where contno='"+tContNo+"' and BnfType='0' order by bnfgrade,a.bnfno,bnflot";
*/        
    var strSQL = "";
	var sqlid2="PEdorTypeLGInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(tContNo);//指定传入的参数
	strSQL=mySql2.getString();
        
        arrResult = easyExecSql(strSQL);
        if (arrResult)
        {
            displayMultiline(arrResult,CustomerBnfGrid);
        }
    }
}


function getCustomerActBnfGrid()
{
    var tContNo=document.all("ContNo").value;
    if(tContNo!=null&&tContNo!="")
    {
//    	var sql="select name,sex,birthday,idtype,idno,relationtoinsured,bnflot,Remark,bankcode,BankAccNo,AccName from lpbnf where edorno in (select edorno from lpedoritem where edoracceptno='"+document.all('EdorAcceptNo').value+"')";
    	
    var sql = "";
	var sqlid3="PEdorTypeLGInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(document.all('EdorAcceptNo').value);//指定传入的参数
	sql=mySql3.getString();
    	
    	arrResult = easyExecSql(sql);
    	if(arrResult)
    	{
    		displayMultiline(arrResult,CustomerActBnfGrid);
    	}
    	else
    	{
/*	        var strSQL = "select a.name,sex,"
	        	+"  a.birthday,idtype"
	        	+"  ,a.idno,"
	        	//+"(select trim(m.code)||'-'||trim(m.CodeName) from LDCode m where trim(m.codetype) = 'bnftype' and trim(m.code) = trim(bnftype)), "
	        	+"  relationtoinsured,"
	        	//+"  (select trim(m.code)||'-'||trim(m.CodeName) from LDCode m where trim(m.codetype) = 'bnforder' and trim(m.code) = trim(a.bnfgrade)),"
	        	+"a.bnflot,'' ,a.bankcode,a.BankAccNo,a.AccName from lcbnf "
	        	+"a where contno='"+tContNo+"' and BnfType='0' and bnfgrade='1' order by bnfgrade,a.bnfno,bnflot";
*/	        
	var strSQL = "";
	var sqlid4="PEdorTypeLGInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(tContNo);//指定传入的参数
	strSQL=mySql4.getString();
	        
	        arrResult = easyExecSql(strSQL);
	        if (arrResult)
	        {
	            displayMultiline(arrResult,CustomerActBnfGrid);
	        }
    	}
    }
}



function sedorTypeDBSave()
{
  if (PolGrid.mulLineCount <= 0) {
    alert("没有符合条件的保单!");
    return false;
  }
  
 if(!verify())
 {
    return false;
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
  document.all("fmtransact").value = "EdorSave";
  fm.action="./PEdorTypeLGSubmit.jsp";
  fm.submit();
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{

    showInfo.close();
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    if (FlagStr == "Success")
    {
      queryBackFee();
    }
}


function returnParent()
{
    top.close();
    top.opener.focus();
    top.opener.getEdorItemGrid();
}

function showNotePad()
{
    var MissionID = top.opener.document.all("MissionID").value;
    var SubMissionID = top.opener.document.all("SubMissionID").value;;
    var ActivityID = "0000000003";
    var OtherNo = top.opener.document.all("OtherNo").value;;

    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionID为空！");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");
}


function Edorquery()
{
    var ButtonFlag = top.opener.document.all('ButtonFlag').value;
    if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
    }
    else
    {
        divEdorquery.style.display = "";
    }
}



function afterCount(tFlagStr,tContent,tMulArray)
{
        //初始化MulLine
        if (tFlagStr == "Success")
        {
           displayMultiline(tMulArray,PolGrid,turnPage);
        }else
        	{
        		
          var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + tContent ;
            //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
									
        }

}


function verify()
{
	if(!PolGrid.checkValue2())
	{
		return false;
	}
	var money=0;
  for (i=0; i<PolGrid.mulLineCount; i++)
  {
	  if(PolGrid.getRowColData(i, 10)!=null&&PolGrid.getRowColData(i, 10)!="")
	  {
	      if ((parseFloat(PolGrid.getRowColData(i, 10)) > PolGrid.getRowColData(i, 6)))
	      {
	        alert("第" + (i+1) + "行实领大于可领金额，请确认金额！");
	        return false;
	      }
	      
	      if ( (parseFloat(PolGrid.getRowColData(i, 10))<=0))
      		{
        		alert("第" + (i+1) + "行实领金额请录入大于0的数值！");
        		return false;
      		}
      		
	      money = money+parseFloat(PolGrid.getRowColData(i, 10));
	  }
  }
  if(money==0)
  {
  	    alert("实际领取金额为0，请录入可领金额!如果可领金额值为0，表示没有可领取的生存金，请撤销本次保全！");
        return false;
  }
  //alert(money);
  CustomerActBnfGrid.delBlankLine();
  if(!CustomerActBnfGrid.checkValue2())
  {
	  return false;
  }
  
  if(CustomerActBnfGrid.mulLineCount<=0)
  {
	  alert("未录入实际领取人！");
	  return false;
  }
  var bnflot=0;
  //alert("CustomerActBnfGrid.mulLineCount:::::"+CustomerActBnfGrid.mulLineCount); 
  var getformflag;
  for (i=0;i<CustomerActBnfGrid.mulLineCount;i++)
  {
	  //CustomerActBnfGrid.getRowColData(i,1)
		var tIDType=CustomerActBnfGrid.getRowColData(i,4);
		var tIDNo=CustomerActBnfGrid.getRowColData(i,5);
		if(tIDType=="0")
		{
		   tBirthday=getBirthdatByIdNo(tIDNo);
		   tSex=getSexByIDNo(tIDNo);
		   if(tBirthday=="输入的身份证号位数错误"||tSex=="输入的身份证号位数错误")
		   {
		   	  str="第"+(i+1)+"行身份证号位数输入错误!!!" ;
		   	  alert(str);
		   	  return false;
		   }
		   else
		   {  
			   CustomerActBnfGrid.setRowColData(i,3,tBirthday) ;
			   CustomerActBnfGrid.setRowColData(i,2,tSex) ;
		   }
	    }
		if(CustomerActBnfGrid.getRowColData(i,8)!="1"&&CustomerActBnfGrid.getRowColData(i,8)!="4"&&CustomerActBnfGrid.getRowColData(i,8)!="9")
		{
			alert("实际领取人列表中第"+(i+1)+"行领取方式不正确，请下拉选择！");
			return false;
		}
		
		
		if(i==0)
		{
			getformflag=CustomerActBnfGrid.getRowColData(i,8);
		}
		else
		{
			if(getformflag!=CustomerActBnfGrid.getRowColData(i,8))
			{
				alert("领取方式错误：实际领取人的领取方式必须一致！");
				return false;
			}
		}
		
		
		if(CustomerActBnfGrid.getRowColData(i,8)=="4"||CustomerActBnfGrid.getRowColData(i,8)=="9")
		{
			if(CustomerActBnfGrid.getRowColData(i,9)==null||CustomerActBnfGrid.getRowColData(i,9)==""||
					CustomerActBnfGrid.getRowColData(i,10)==null||CustomerActBnfGrid.getRowColData(i,10)==""||
					CustomerActBnfGrid.getRowColData(i,11)==null||CustomerActBnfGrid.getRowColData(i,11)=="")
			{
				alert("实际领取人列表中第"+(i+1)+"行领取方式为【银行转账】或者【网银代付】，所以必须填写【银行编码】、【银行账号】和【银行帐户名】");
				return false;
			}
			//银行转帐基础上加上银行帐号过虑校验
			if(!checkBank(CustomerActBnfGrid.getRowColData(i,9),CustomerActBnfGrid.getRowColData(i,10)))
			{
			  //CustomerActBnfGrid.setRowColData(i,8,"");
			  CustomerActBnfGrid.setRowColData(i,10,"");
			  return false;
			}
			
			if(CustomerActBnfGrid.getRowColData(i,11)!=CustomerActBnfGrid.getRowColData(i,1))
			{
				alert("实际领取人列表中第"+(i+1)+"行领取人姓名和银行帐户名不一致");
				return false;
			}

		}
		bnflot += parseFloat(CustomerActBnfGrid.getRowColData(i,7))*100;
  }
  if(bnflot!=100)
  {
	  alert("实际领取人领取比例合计不是100%！请重新录入领取比例！");
	  return false;
  }
  
  return true;
}


function confirmSecondInput1(aObject,aEvent)
{
 {
  if(theFirstValue!="")
  {
   clipboardData.setData('text','');
   theSecondValue = aObject.value;
   if(theSecondValue=="")
   {
    alert("请再次录入！");
    aObject.value="";
    aObject.focus();
    return;
   }
   if(theSecondValue==theFirstValue)
   {
    aObject.value = theSecondValue;
    theSecondValue="";
    theFirstValue="";
    return;
   }
   else
   {
    alert("两次录入结果不符，请重新录入！");
    theFirstValue="";
    theSecondValue="";
    aObject.value="";
    aObject.focus();
    return;
   }
  }
  else
  {
   theFirstValue = aObject.value;
   theSecondValue="";
   if(theFirstValue=="")
   {
    return;
   }
   aObject.value="";
   aObject.focus();
   clipboardData.setData('text','');
   return;
  }
 }
}

/**
 * 银行帐号过虑
 */
function checkBank(tBankCode,tBankAccNo)
{
	if(tBankCode.length>0 && tBankAccNo.length>0)
	{
		if(!checkBankAccNo(tBankCode,tBankAccNo))
		{	
			return false;
		}
	}
	return true;
}

  