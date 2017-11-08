var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var strSumSql = "";
/**
 * 后台数据库查询
 */
function easyQueryClick() {
  // 拼SQL语句，从页面采集信息
  strSumSql = "select sum(paymoney) from ljtempfeeclass where 1=1";
//    var  sqlid1="TempFeeQueryByChqNoSql0";
//	var  mySql1=new SqlClass();
//	mySql1.setResourceName("finfee.TempFeeQueryByChqNoSql"); //指定使用的properties文件名
//	mySql1.setSqlId(sqlid1);//指定使用的Sql的id	
  fm.SumMoney.value='0';	
  if( verifyInput() == false ) return false;
  var strSql = "";
   // var  sqlid2 = "TempFeeQueryByChqNoSql1";
   //var  mySql2=new SqlClass();
   //mySql2.setResourceName("finfee.TempFeeQueryByChqNoSql"); //指定使用的properties文件名
   //mySql2.setSqlId(sqlid1);//指定使用的Sql的id
  if(_DBT==_DBO){
	  strSql = "select paymoney,tempfeeno,otherno,ManageCom,policycom,(select APPntName from lccont where 1=1 and (contno = ljtempfeeclass.otherno or prtno=ljtempfeeclass.otherno) and rownum = '1'),paymode,ChequeNo,ChequeDate,paydate,EnterAccDate,BankCode,BankAccNo,AccName,inbankcode from ljtempfeeclass where 1=1 "
  }else if(_DBT==_DBM){
	  strSql = "select paymoney,tempfeeno,otherno,ManageCom,policycom,(select APPntName from lccont where 1=1 and (contno = ljtempfeeclass.otherno or prtno=ljtempfeeclass.otherno) limit 0,1),paymode,ChequeNo,ChequeDate,paydate,EnterAccDate,BankCode,BankAccNo,AccName,inbankcode from ljtempfeeclass where 1=1 "

  }
   

  if (fm.ManageCom.value!=null&&fm.ManageCom.value!="")
   {  
  		strSql =strSql + " and managecom like '"+fm.ManageCom.value+"%%' ";
  		strSumSql =strSumSql + " and managecom like '"+fm.ManageCom.value+"%%' ";
   }
//  mySql2.addSubPara(fm.ManageCom.value);//指定传入的参数
//  mySql1.addSubPara(fm.ManageCom.value);//指定传入的参数

  if (fm.PolicyCom.value!=null&&fm.PolicyCom.value!="")
   {  
  		strSql =strSql + " and PolicyCom like '"+fm.PolicyCom.value+"%%' ";
      strSumSql =strSumSql + " and PolicyCom like '"+fm.PolicyCom.value+"%%' ";
   }
//  mySql2.addSubPara(fm.PolicyCom.value);//指定传入的参数
//  mySql1.addSubPara(fm.PolicyCom.value);//指定传入的参数
//  var cSql="";
//  var cSumql=""
  if (fm.TempFeeStatus1.value=='0')
   {
     strSql = strSql + " and enteraccdate is not null";	
     strSumSql = strSumSql + " and enteraccdate is not null";
    //  cSql= " and enteraccdate is not null" ;
     // cSumql= " and enteraccdate is not null";
   }
//  mySql2.addSubPara(cSql);//指定传入的参数
//  mySql1.addSubPara(cSumql);//指定传入的参数
//  cSql="";
//  cSumql=""
  if (fm.TempFeeStatus1.value=='1')
   {
     strSql = strSql + " and enteraccdate is null";	
     strSumSql = strSumSql + " and enteraccdate is null";
     //  cSql= " and enteraccdate is  null" ;
     // cSumql= " and enteraccdate is  null";
   }
//mySql2.addSubPara(cSql);//指定传入的参数
//mySql1.addSubPara(cSumql);//指定传入的参数
//  cSql="";
//  cSumql="" 
  if (fm.TempFeeStatus1.value=='2')
   {
     strSql = strSql + " and ConfDate is not null";	
     strSumSql = strSumSql + " and ConfDate is not null";
     //  cSql= " and ConfDate is not   null" ;
     // cSumql= " and ConfDate is not   null";
   }
//mySql2.addSubPara(cSql);//指定传入的参数
//mySql1.addSubPara(cSumql);//指定传入的参数
//  cSql="";
//  cSumql="" 
  if (fm.TempFeeStatus1.value=='3')
   {
     strSql = strSql + " and ConfDate is null";	
     strSumSql = strSumSql + " and ConfDate is null";
     //  cSql= " and ConfDate is  null" ;
     // cSumql= " and ConfDate is  null";
   }
//mySql2.addSubPara(cSql);//指定传入的参数
//mySql1.addSubPara(cSumql);//指定传入的参数
//  cSql="";
//  cSumql="" 
  if (fm.TempFeeStatus1.value=='4')
   {
	//  cSql= " ";
	     // cSumql= " ";
   }
//mySql2.addSubPara(cSql);//指定传入的参数
//mySql1.addSubPara(cSumql);//指定传入的参数
//  cSql="";
//  cSumql="" 
  if (fm.PayMode.value=='1')
   {
   	 strSql = strSql + " and paymode in ('1')";	
   	 strSumSql = strSumSql + " and paymode in ('1')";
   	 //  cSql= " and paymode in ('1')";
     // cSumql= " and paymode in ('1')";
   }
//mySql2.addSubPara(cSql);//指定传入的参数
//mySql1.addSubPara(cSumql);//指定传入的参数
//  cSql="";
//  cSumql="" 
  if (fm.PayMode.value=='2') 
   {
   	 strSql = strSql + " and paymode ='2'";	
   	 strSumSql = strSumSql + " and paymode ='2'";
 //  cSql= " and paymode in ('2')";
     // cSumql= " and paymode in ('2')";
   }
//mySql2.addSubPara(cSql);//指定传入的参数
//mySql1.addSubPara(cSumql);//指定传入的参数
//  cSql="";
//  cSumql="" 
  if (fm.PayMode.value=='3') 
   {
   	 strSql = strSql + " and paymode ='3'";	
   	 strSumSql = strSumSql + " and paymode ='3'";
    //  cSql= " and paymode in ('3')";
     // cSumql= " and paymode in ('3')";
   } 
//mySql2.addSubPara(cSql);//指定传入的参数
//mySql1.addSubPara(cSumql);//指定传入的参数
//  cSql="";
//  cSumql="" 
  if (fm.PayMode.value=='4') 
   {
   	 strSql = strSql + " and paymode ='4'";	
   	 strSumSql = strSumSql + " and paymode ='4'";
 //  cSql= " and paymode in ('4')";
     // cSumql= " and paymode in ('4')";
   } 
//mySql2.addSubPara(cSql);//指定传入的参数
//mySql1.addSubPara(cSumql);//指定传入的参数
//  cSql="";
//  cSumql="" 
  if (fm.PayMode.value=='5') 
   {
   	 strSql = strSql + " and paymode ='5'";	
   	 strSumSql = strSumSql + " and paymode ='5'";
 //  cSql= " and paymode in ('5')";
     // cSumql= " and paymode in ('5')";
   }
//mySql2.addSubPara(cSql);//指定传入的参数
//mySql1.addSubPara(cSumql);//指定传入的参数
//  cSql="";
//  cSumql="" 
  if (fm.PayMode.value=='6') 
   {
   	 strSql = strSql + " and paymode ='6'";	
   	 strSumSql = strSumSql + " and paymode ='6'";
 //  cSql= " and paymode in ('6')";
     // cSumql= " and paymode in ('6')";
   } 
//mySql2.addSubPara(cSql);//指定传入的参数
//mySql1.addSubPara(cSumql);//指定传入的参数
//  cSql="";
//  cSumql="" 
  if (fm.PayMode.value=='7') 
   {
   	 strSql = strSql + " and paymode ='7'";	
   	 strSumSql = strSumSql + " and paymode ='7'";
 //  cSql= " and paymode in ('7')";
     // cSumql= " and paymode in ('7')";
   } 
//mySql2.addSubPara(cSql);//指定传入的参数
//mySql1.addSubPara(cSumql);//指定传入的参数
//  cSql="";
//  cSumql="" 
  if (fm.PayMode.value=='8') 
   {
   	 strSql = strSql + " and paymode ='8'";	
   	 strSumSql = strSumSql + " and paymode ='8'";
 //  cSql= " and paymode in ('8')";
     // cSumql= " and paymode in ('8')";
   } 
//mySql2.addSubPara(cSql);//指定传入的参数
//mySql1.addSubPara(cSumql);//指定传入的参数
//  cSql="";
//  cSumql="" 
  if (fm.PayMode.value=='9') 
   {
   	 strSql = strSql + " and paymode ='9'";	
   	 strSumSql = strSumSql + " and paymode ='9'";
 //  cSql= " and paymode in ('9')";
     // cSumql= " and paymode in ('9')";
   } 
//mySql2.addSubPara(cSql);//指定传入的参数
//mySql1.addSubPara(cSumql);//指定传入的参数
//  cSql="";
//  cSumql="" 
  if (fm.PayMode.value=='0') 
   {
   	 strSql = strSql + " and paymode ='0'";	
   	 strSumSql = strSumSql + " and paymode ='0'";
 //  cSql= " and paymode in ('0')";
     // cSumql= " and paymode in ('0')";
   }
//mySql2.addSubPara(cSql);//指定传入的参数
//mySql1.addSubPara(cSumql);//指定传入的参数
//  cSql="";
//  cSumql="" 
  if (fm.PayMode.value=='A') 
   {
   	 strSql = strSql + " and paymode ='A'";	
   	 strSumSql = strSumSql + " and paymode ='A'";
 //  cSql= " and paymode in ('A')";
     // cSumql= " and paymode in ('A')";
   } 
//mySql2.addSubPara(cSql);//指定传入的参数
//mySql1.addSubPara(cSumql);//指定传入的参数
//  cSql="";
//  cSumql="" 
     
  if (fm.MinMoney.value !=null&&fm.MinMoney.value!="")
   {
     strSql = strSql + " and paymoney>="+fm.MinMoney.value+"";	
     strSumSql = strSumSql + " and paymoney>="+fm.MinMoney.value+"";
   } 
//  mySql2.addSubPara(fm.MinMoney.value);//指定传入的参数
//  mySql1.addSubPara(fm.MinMoney.value);//指定传入的参数
  if (fm.MaxMoney.value !=null&&fm.MaxMoney.value!="")
   {
     strSql = strSql + " and paymoney<="+fm.MaxMoney.value+"";	
     strSumSql = strSumSql + " and paymoney<="+fm.MaxMoney.value+"";
   } 
//  mySql2.addSubPara(fm.MaxMoney.value);//指定传入的参数
//  mySql1.addSubPara(fm.MaxMoney.value);//指定传入的参数
  if (fm.StartDate.value !=null&&fm.StartDate.value!="")
   {
     strSql = strSql + " and paydate>='"+fm.StartDate.value+"'";	
     strSumSql = strSumSql + " and paydate>='"+fm.StartDate.value+"'";
   }
//  mySql2.addSubPara(fm.StartDate.value);//指定传入的参数
//  mySql1.addSubPara(fm.StartDate.value);//指定传入的参数
  if (fm.EndDate.value !=null&&fm.EndDate.value!="")
   {
     strSql = strSql + " and paydate<='"+fm.EndDate.value+"'";	
     strSumSql = strSumSql + " and paydate<='"+fm.EndDate.value+"'";
   }
//  mySql2.addSubPara(fm.EndDate.value);//指定传入的参数
//  mySql1.addSubPara(fm.EndDate.value);//指定传入的参数
  if (fm.EnterAccStartDate.value !=null&&fm.EnterAccStartDate.value!="")
   {
     strSql = strSql + " and enteraccdate>='"+fm.EnterAccStartDate.value+"'";	
     strSumSql = strSumSql + " and enteraccdate>='"+fm.EnterAccStartDate.value+"'";
   }
//  mySql2.addSubPara(fm.EnterAccStartDate.value);//指定传入的参数
//  mySql1.addSubPara(fm.EnterAccStartDate.value);//指定传入的参数
  if (fm.EnterAccEndDate.value !=null&&fm.EnterAccEndDate.value!="")
   {
     strSql = strSql + " and enteraccdate<='"+fm.EnterAccEndDate.value+"'";	
     strSumSql = strSumSql + " and enteraccdate<='"+fm.EnterAccEndDate.value+"'";
   }
//  mySql2.addSubPara(fm.EnterAccStartDate.value);//指定传入的参数
//  mySql1.addSubPara(fm.EnterAccStartDate.value);//指定传入的参数
     
  if (fm.Operator.value !=null&&fm.Operator.value !="")
   {
  	 strSql = strSql + " and Operator = '" +fm.Operator.value+"'";
  	 strSumSql = strSumSql + " and Operator = '" +fm.Operator.value+"'";
   }
//  mySql2.addSubPara(fm.Operator.value);//指定传入的参数
//  mySql1.addSubPara(fm.Operator.value);//指定传入的参数
  if (fm.TempFeeNo.value !=null&&fm.TempFeeNo.value !="")
   {
  	 strSql = strSql + " and TempFeeNo = '" +fm.TempFeeNo.value+"'";
  	 strSumSql = strSumSql + " and TempFeeNo = '" +fm.TempFeeNo.value+"'";
   }
//  mySql2.addSubPara(fm.Operator.value);//指定传入的参数
//  mySql1.addSubPara(fm.Operator.value);//指定传入的参数
  if (fm.PrtNo.value !=null&&fm.PrtNo.value !="")
   {
  	 strSql = strSql + " and otherno = '" +fm.PrtNo.value+"'";
  	strSumSql = strSumSql + " and otherno = '" +fm.PrtNo.value+"'";
   }
//  mySql2.addSubPara(fm.PrtNo.value);//指定传入的参数
//  mySql1.addSubPara(fm.PrtNo.value);//指定传入的参数
                    
  if (fm.BankCode9.value !=null&&fm.BankCode9.value !="")
   {
  	 strSql = strSql + " and BankCode = '" +fm.BankCode9.value+"'";
  	 strSumSql = strSumSql + " and BankCode = '" +fm.BankCode9.value+"'";
   }
//  mySql2.addSubPara(fm.BankCode9.value);//指定传入的参数
//  mySql1.addSubPara(fm.BankCode9.value);//指定传入的参数
  if (fm.BankCode7.value !=null&&fm.BankCode7.value !="")
   {
  	 strSql = strSql + " and BankCode = '" +fm.BankCode7.value+"'";
  	 strSumSql = strSumSql + " and BankCode = '" +fm.BankCode7.value+"'";
   }
//  mySql2.addSubPara(fm.BankCode7.value);//指定传入的参数
//  mySql1.addSubPara(fm.BankCode7.value);//指定传入的参数
//cSql="";
//cSumql=""
  if (fm.PayType.value!=null&&fm.PayType.value !="")
  {
  	if (fm.PayType.value=='4')
  	{
  		strSql = strSql + " and Othernotype ='" +fm.PayType.value+"' ";	
     	strSumSql = strSumSql + " and Othernotype ='" +fm.PayType.value+"' ";
    //  cSql= " and Othernotype ='" +fm.PayType.value+"' ";
        // cSumql= " and Othernotype ='" +fm.PayType.value+"' ";
  	}
  	else
  	{
     	strSql = strSql + " and exists (select tempfeeno from ljtempfee where othernotype ='" +fm.PayType.value+"' and tempfeeno=ljtempfeeclass.tempfeeno)";	
     	strSumSql = strSumSql + " and exists (select tempfeeno from ljtempfee where othernotype ='" +fm.PayType.value+"' and tempfeeno=ljtempfeeclass.tempfeeno)";
     	 //  cSql=" and exists (select tempfeeno from ljtempfee where othernotype ='" +fm.PayType.value+"' and tempfeeno=ljtempfeeclass.tempfeeno)";
        // cSumql= " and exists (select tempfeeno from ljtempfee where othernotype ='" +fm.PayType.value+"' and tempfeeno=ljtempfeeclass.tempfeeno)"; 
    }
  }
//mySql2.addSubPara(cSql);//指定传入的参数
//mySql1.addSubPara(cSumql);//指定传入的参数
  
  if (fm.ChequeNo.value !=null&&fm.ChequeNo.value !="")
   {
  	 strSql = strSql + " and ChequeNo = '" +fm.ChequeNo.value+"'";
  	 strSumSql = strSumSql + " and ChequeNo = '" +fm.ChequeNo.value+"'";
//	  mySql2.addSubPara(fm.ChequeNo.value);//指定传入的参数
//	  mySql1.addSubPara(fm.ChequeNo.value);//指定传入的参数
   } 
 
  if (fm.ChequeDate.value !=null&&fm.ChequeDate.value !="")
   {
  	 strSql = strSql + " and ChequeDate = '" +fm.ChequeDate.value+"'";
  	 strSumSql = strSumSql + " and ChequeDate = '" +fm.ChequeDate.value+"'";
//	  mySql2.addSubPara(fm.ChequeDate.value);//指定传入的参数
//	  mySql1.addSubPara(fm.ChequeDate.value);//指定传入的参数
   }    
  
  if (fm.InBankCode.value !=null&&fm.InBankCode.value !="")
  {
  	 strSql = strSql + " and InBankCode = '" +fm.InBankCode.value+"'";
  	 strSumSql = strSumSql + " and InBankCode = '" +fm.InBankCode.value+"'";
//	  mySql2.addSubPara(fm.InBankCode.value);//指定传入的参数
//	  mySql1.addSubPara(fm.InBankCode.value);//指定传入的参数
  }
//   strSumSql=mySql1.getString();
//   strSql=mySql2.getString()
 	Sum();              
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
                  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {  
    //清空MULTILINE，使用方法见MULTILINE使用说明 
    TempQueryByChqNoGrid.clearData('TempQueryByChqNoGrid');  
    alert("没有查询到数据！");
    return false;
  }
  
  //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = TempQueryByChqNoGrid;
          
  //保存SQL语句
  turnPage.strQuerySql = strSql; 
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
 
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
 
  //控制是否显示翻页按钮
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
  
}
function Sum()
{
 	  var arrResultSum = easyExecSql(strSumSql);
	  if(arrResultSum!=null)
	  fm.SumMoney.value=arrResultSum;	
}

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
function afterCodeSelect(cCodeName, Field) {
	if(cCodeName == "paymodequery") { 		

		if(document.all("PayMode").value=='9'||document.all("PayMode").value=='A')
		{
			InBankCode.style.display = 'none';	
			divChequeNo.style.display = 'none';
			divBankCode7.style.display = 'none';
			divBankCode.style.display = '';		  
		}
	  else if (document.all("PayMode").value=='7')
	  	{
	  		InBankCode.style.display = 'none';	
	  		divChequeNo.style.display = 'none';
	  		divBankCode7.style.display = ''; 	  			  	  
	  	  divBankCode.style.display = 'none';
	  	}
	  else if (document.all("PayMode").value=='3')
	  	{
	  	 InBankCode.style.display = 'none';			  
	     divChequeNo.style.display = '';
	  	 divBankCode7.style.display = 'none'; 	  			  	  
	  	 divBankCode.style.display = 'none';	      
      }
    else if (document.all("PayMode").value=='2')
    	{
    			InBankCode.style.display = '';	    		
    			divChequeNo.style.display = 'none';
    			divBankCode.style.display = 'none';
    			divBankCode7.style.display = 'none'; 	    		
    	}  
    else
    {
    	InBankCode.style.display = 'none';	
    	divChequeNo.style.display = 'none';
    	divBankCode.style.display = 'none';
    	divBankCode7.style.display = 'none'; 	
    }
}
}

//导出收费明细-----------added by guanwei 2006-4-1 14:39
function SFDetailExecel()
{
    if (fm.ManageCom.value==null||fm.ManageCom.value=="")
     {
       alert("请选择收费机构");
       fm.ManageCom.focus();
       return false;
     }
     if (fm.StartDate.value==""&&fm.EndDate.value==""&&fm.EnterAccStartDate.value==""&&fm.EnterAccEndDate.value==""){
     	alert("请输入至少一组起止日期。");
     	fm.StartDate.focus();
     	return;
     	}
    else if ((fm.StartDate.value==""&&fm.EndDate.value!="")||(fm.EndDate.value==""&&fm.StartDate.value!=""))
     {
       alert("请输入交费起止日期。");
       if(fm.StartDate.value==""){
       	fm.StartDate.focus();
      }else {
      fm.EndDate.focus();
      }
       return false;
     }
    else if ((fm.EnterAccStartDate.value==""&&fm.EnterAccEndDate.value!="")||(fm.EnterAccEndDate.value==""&&fm.EnterAccStartDate.value!=""))
     {
       alert("请输入到帐起止日期。");
       if(fm.EnterAccStartDate.value==""){
       	fm.EnterAccStartDate.focus();
      }else {
      fm.EnterAccEndDate.focus();
      }
       return false;
     }
   fm.action="./RNSFDetailExecelsave.jsp";
   fm.submit(); //提交
}

//导出新契约承保明细-----------added by guanwei 2006-4-1 14:39
function NCDetailExecel()
{
    if (fm.PolicyCom.value==null||fm.PolicyCom.value=="")
     {
       alert("请选择管理机构");
       fm.PolicyCom.focus();
       return false;
     }
    if (fm.StartDate.value=="")
     {
       alert("请输入交费开始日期。");
       fm.StartDate.focus();
       return false;
     }
    if (fm.EndDate.value=="")
     {
       alert("请输入交费结束日期。");
       fm.EndDate.focus();
       return false;
     }
    if (fm.EnterAccStartDate.value=="")
     {
       alert("请输入到帐开始日期。");
       fm.EnterAccStartDate.focus();
       return false;
     }
    if (fm.EnterAccEndDate.value=="")
     {
       alert("请输入到帐结束日期。");
       fm.EnterAccEndDate.focus();
       return false;
     }

   fm.action="./RNNCDetailExecelsave.jsp";
   fm.submit(); //提交
}

//导出保全收费明细-----------added by guanwei 2006-4-3 15:21
function BQDetailExecel()
{
  if( verifyInput2() == false ) return false;

   fm.action="./RNBQDetailExecelsave.jsp";
   fm.submit(); //提交
	}

function verifyInput()
{
  if(fm.PrtNo.value==""&&fm.TempFeeNo.value==""&&fm.ChequeNo.value=="")
  {
  	if (fm.StartDate.value==""&&fm.EndDate.value==""&&fm.EnterAccStartDate.value==""&&fm.EnterAccEndDate.value=="")
  	{
  				alert("请选择日期条件");
  				return false;
  				}
     	if((fm.StartDate.value!=""&&fm.EndDate.value=="")||(fm.StartDate.value==""&&fm.EndDate.value!=""))
     	{
     		alert("请输入一组相应的起始日期。");
     		return false;
     		}
     	if((fm.EnterAccStartDate.value!=""&&fm.EnterAccEndDate.value=="")||(fm.EnterAccStartDate.value==""&&fm.EnterAccEndDate.value!=""))
     	{
     		alert("请输入一组相应的起始日期。");
     		return false;
     		}
//     	if(fm.StartDate.value!=""&&fm.EndDate.value!="")
//     	{
//     		var SQLInterval1 = "select * from dual where add_months('"+fm.StartDate.value+"',2) > '"+fm.EndDate.value+"'";
//     		var Interval1 = easyExecSql(SQLInterval1);
//     		if (Interval1==null||Interval1==""||Interval1=="null")
//     		{
//		     			alert("日期跨度过大，请选择两个月以内的时间。");
//     					return false;
//     		}
//     	}
//     	if(fm.EnterAccStartDate.value!=""&&fm.EnterAccEndDate.value!="")
//     	{
//		   			var SQLInterval2 = "select * from dual where add_months('"+fm.EnterAccStartDate.value+"',2) > '"+fm.EnterAccEndDate.value+"'";
//		   			var Interval2 = easyExecSql(SQLInterval2);
//    	 			if(Interval2==null||Interval2==""||Interval2=="null")
//     				{
//				   				alert("日期跨度过大，请选择两个月以内的时间");
//		  						return false;
//    	 			}
//     	}
  	if(fm.PolicyCom.value==""||fm.PolicyCom.value==null||fm.PolicyCom.value=="null")
  	{
  				alert("请选择管理机构");
  				fm.PolicyCom.focus();
  				return false;
  				}
  	if (fm.ManageCom.value=="")
  	{
  				alert("请选择收费机构");
  				fm.ManageCom.focus();
  				return false;
  				}
  				if((fm.ManageCom.value).length<4)
  				{
  							alert("收费机构过大，请选择四位及以下机构。");
  							fm.ManageCom.focus();
  							return false;
  							}
  }
  return true;
}

function verifyInput2()
{
    if (fm.StartDate.value==""||fm.EndDate.value==""||fm.EnterAccStartDate.value==""||fm.EnterAccEndDate.value=="")
     {
     			alert("交费开始日期、交费结束日期 、到帐开始日期 、到帐结束日期缺一不可，请输入。");
     			fm.StartDate.focus();
     			return false;
     			}
     	if(fm.StartDate.value!=""&&fm.EndDate.value!="")
     	{
     		var SQLInterval3 = "select * from dual where add_months('"+fm.StartDate.value+"',2) > '"+fm.EndDate.value+"'";
     		var Interval3 = easyExecSql(SQLInterval3);
     		if (Interval3==null||Interval3==""||Interval3=="null")
     		{
		     			alert("日期跨度过大，请选择两个月以内的时间。");
     					return false;
     		}
     	}
     	if(fm.EnterAccStartDate.value!=""&&fm.EnterAccEndDate.value!="")
     	{
		   			var SQLInterval4 = "select * from dual where add_months('"+fm.EnterAccStartDate.value+"',2) > '"+fm.EnterAccEndDate.value+"'";
		   			var Interval4 = easyExecSql(SQLInterval4);
    	 			if(Interval4==null||Interval4==""||Interval4=="null")
     				{
				   				alert("日期跨度过大，请选择两个月以内的时间");
		  						return false;
    	 			}
     	}
    if (fm.PolicyCom.value==null||fm.PolicyCom.value=="")
     {
     			alert("请选择管理机构");
     			fm.PolicyCom.focus();
     			return false;
     			}
     if(fm.ManageCom.value==null||fm.ManageCom.value=="")
     {
     			alert("请选择收费机构");
     			fm.ManageCom.focus();
     			return false;
     			}
     			if((fm.ManageCom.value).length<6)
     			{
     				alert("收费机构过大，请选择六位及以下机构");
     				fm.ManageCom.focus();
     				return false;
     				}
  return true;
}