var showInfo;

var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var sqlresourcename = "bq.PEdorTypeCTInputSql";

function getCustomerActBnfGrid()
{
    var tContNo=document.all("ContNo").value;
    if(tContNo!=null&&tContNo!="")
    {
//   	var sql="select name,sex,birthday,idtype,idno,BnfLot,Remark,bankcode,BankAccNo,AccName from lpbnf where edorno in (select edorno from lpedoritem where edoracceptno='"+document.all('EdorAcceptNo').value+"')";
    	
    var sql = "";
	var sqlid1="PEdorTypeCTInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(document.all('EdorAcceptNo').value);//指定传入的参数
	sql=mySql1.getString();
    	
    	arrResult = easyExecSql(sql);
    	if(arrResult)
    	{
    		displayMultiline(arrResult,CustomerActBnfGrid);
    		showPageOnly(this,divBackFeeTotal);
    	}
//    	else
//    	{
//	        var strSQL = "select a.appntname,appntsex,"
//	        	+"  a.appntbirthday,idtype"
//	        	+"  ,a.idno,"
//	        	//+"(select trim(m.code)||'-'||trim(m.CodeName) from LDCode m where trim(m.codetype) = 'bnftype' and trim(m.code) = trim(bnftype)), "
//	        	//+"  relationtoinsured,"
//	        	//+"  (select trim(m.code)||'-'||trim(m.CodeName) from LDCode m where trim(m.codetype) = 'bnforder' and trim(m.code) = trim(a.bnfgrade)),"
//	        	+"'','' ,a.bankcode,a.BankAccNo,a.AccName from lcappnt "
//	        	+"a where contno='"+tContNo+"' ";
//	        
//	        arrResult = easyExecSql(strSQL);
//	        if (arrResult)
//	        {
//	            displayMultiline(arrResult,CustomerActBnfGrid);
//	        }
//    	}
    }
}

function getPolGrid(tContNo)
{
    var EdorAppDate = fm.EdorItemAppDate.value;
    var tContno=document.all('ContNo').value;
    //alert(tContno);
/*    var strSQL = " select RiskCode," +
                 " (select RiskName from LMRisk where LMRisk.RiskCode = c.RiskCode)," +
                 " InsuredName, Amnt, " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "'  and p.polno = c.polno and p.payplantype in ('0')), 0), " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "'  and p.polno = c.polno and p.payplantype in ('01', '03')), 0), " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "'  and p.polno = c.polno and p.payplantype in ('02', '04')), 0), " +
                 " polno,CValiDate,PaytoDate,mainpolno " +
                 " from LCPol c where ContNo='" + tContNo + "' and appflag = '1'" +
                 " union" +
                 " select RiskCode," +
                 " (select RiskName from LMRisk where LMRisk.RiskCode = c.RiskCode)," +
                 " InsuredName, Amnt, " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "'  and p.polno = c.polno and p.payplantype in ('0')), 0), " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "'  and p.polno = c.polno and p.payplantype in ('01', '03')), 0), " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "' and payenddate >= '" + EdorAppDate + "' and p.polno = c.polno and p.payplantype in ('02', '04')), 0), " +
                 " polno,cvalidate,paytodate,mainpolno" 
                 + " from LCPol c where 1=1"
                 + " and (select count(*) from lccontstate s where trim(statetype) in('Terminate') and trim(state) = '1' and trim(StateReason) in ('07') and ((startdate <= '" + EdorAppDate + "' and '" + EdorAppDate + "' <= enddate )or(startdate <= '" + EdorAppDate + "' and enddate is null ))and s.polno = c.polno) > 0"
                 + " and appflag = '4' and contno = '" + tContNo + "'";
    //alert(strSQL);
    //prompt("",strSQL);
*/    
    var strSQL = "";
	var sqlid2="PEdorTypeCTInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(EdorAppDate);//指定传入的参数
	mySql2.addSubPara(EdorAppDate);
	mySql2.addSubPara(EdorAppDate);
	mySql2.addSubPara(tContNo);
	mySql2.addSubPara(EdorAppDate);
	mySql2.addSubPara(EdorAppDate);
	mySql2.addSubPara(EdorAppDate);
	mySql2.addSubPara(EdorAppDate);
	mySql2.addSubPara(EdorAppDate);
	mySql2.addSubPara(EdorAppDate);
	mySql2.addSubPara(EdorAppDate);
	mySql2.addSubPara(tContNo);
	strSQL=mySql2.getString();
    
    brrResult = easyExecSql(strSQL);
    if (brrResult)
    {
        displayMultiline(brrResult,PolGrid);
    }
}

function getCustomerGrid()
{

    var tContNo=document.all("ContNo").value;
    if(tContNo!=null&&tContNo!="")
    {
/*        var strSQL = " Select a.appntno, '投保人', a.appntname, "
                    +" (select trim(n.code)||'-'||trim(n.CodeName) from LDCode n where trim(n.codetype) = 'sex' and trim(n.code) = trim(appntsex)),"
                    +" a.appntbirthday, "
                    +" (select trim(m.code)||'-'||trim(m.CodeName) from LDCode m where trim(m.codetype) = 'idtype' and trim(m.code) = trim(idtype)), "
                    +" a.idno,(select '与被保人的关系--'||codename from ldcode where codetype = 'relation' and code = a.relationtoinsured) "
                    +" From lcappnt a Where contno='" + tContNo+"'"
                    +" Union"
                    +" Select i.insuredno, '被保人', i.name, "
                    +" (select trim(u.code)||'-'||trim(u.CodeName) from LDCode u where trim(u.codetype) = 'sex' and trim(u.code) = trim(sex)),"
                    +" i.Birthday,"
                    +" (select trim(y.code)||'-'||trim(y.CodeName) from LDCode y where trim(y.codetype) = 'idtype' and trim(y.code) = trim(idtype)), "
                    +" i.IDNo,(select '与投保人的关系--'||codename from ldcode where codetype = 'relation' and code = i.relationtoappnt) "
                    +" From lcinsured i Where contno='" + tContNo+"'";
*/        
    var strSQL = "";
	var sqlid3="PEdorTypeCTInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(tContNo);//指定传入的参数
	mySql3.addSubPara(tContNo);
	strSQL=mySql3.getString();
        
        arrResult = easyExecSql(strSQL);
        if (arrResult)
        {
            displayMultiline(arrResult,CustomerGrid);
        }
    }
}

function chkPol()
{
    var tContNo   = document.all('ContNo').value;
    var tEdorNo   = document.all('EdorNo').value;
    var tEdorType = document.all('EdorType').value;
/*    var strSQL = " select polno from lppol " +
                 " where edorno='" + tEdorNo +
                 "' and edortype='" + tEdorType +
                 "' and contno='"+tContno+"'";
*/    
    var strSQL = "";
	var sqlid4="PEdorTypeCTInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(tEdorNo);//指定传入的参数
	mySql4.addSubPara(tEdorType);
	mySql4.addSubPara(tContNo);
	strSQL=mySql4.getString();
    
    var arrResult2=easyExecSql(strSQL);
    var m=0;
    var n=0;

    if(arrResult2!=null)
    {
        var q = arrResult2.length;
        for(m = 0; m < PolGrid.mulLineCount; m++)
        {
            for(n = 0; n < q; n++)
           {
                if(PolGrid.getRowColData(m, 9) == arrResult2[n][0])
                {
                    PolGrid.checkBoxSel(m+1);
                }
            }
        }
        //alert("aa");
        showPageOnly(this,divAppnt);
    }
}


function getContZTInfo()
{
    var MainPolNo;
    var MainPayIntv;
    var MainPayToDate;
    var MainPayEndDate;
    var MainRiskCode;
/*    strSQL = " select polno, paytodate, payintv, payenddate, riskcode from lcPol " +
             " where contno = '" + fm.ContNo.value +
             "' and polno = mainpolno";
*/    
	var sqlid5="PEdorTypeCTInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
	mySql5.addSubPara(fm.ContNo.value);//指定传入的参数
	strSQL=mySql5.getString();
    
    var hrr = easyExecSql(strSQL);
    if ( hrr )
    {
        hrr[0][0]==null||hrr[0][0]=='null'?'0':MainPolNo = hrr[0][0];
        hrr[0][1]==null||hrr[0][1]=='null'?'0':MainPayToDate = hrr[0][1];
        hrr[0][2]==null||hrr[0][2]=='null'?'0':MainPayIntv = hrr[0][2];
        hrr[0][3]==null||hrr[0][3]=='null'?'0':MainPayEndDate = hrr[0][3];
        hrr[0][4]==null||hrr[0][4]=='null'?'0':MainRiskCode = hrr[0][4];
    }
    else
    {
        alert("查找主险失败");
        return;
    }

/*    strSQL = " select customgetpoldate, ReceiveDate, cvalidate from lccont " +
             " where contno = '" + fm.ContNo.value + "' ";
*/    
    var sqlid6="PEdorTypeCTInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql6.setSqlId(sqlid6);//指定使用的Sql的id
	mySql6.addSubPara(fm.ContNo.value);//指定传入的参数
	strSQL=mySql6.getString();
    
    var drr = easyExecSql(strSQL);

    if ( drr )
    {
        drr[0][0]==null||drr[0][0]=='null'?'0':fm.CustomGetPolDate.value = drr[0][0];
        //drr[0][1]==null||drr[0][1]=='null'?'0':fm.ReceiveDate.value = drr[0][1];
        drr[0][2]==null||drr[0][2]=='null'?'0':fm.CvaliDate.value = drr[0][2];

/*        strSQL = " select count(*) from lccontstate  " +
                 " where statetype in('Available') and state = '1' " +
                 " and  ( (startdate <= '" + fm.EdorItemAppDate.value + "' and enddate >= '" + fm.EdorItemAppDate.value + "' ) " +
                 " or (startdate <= '" + fm.EdorItemAppDate.value + "' and enddate is null )) " +
                 " and polno = '" + MainPolNo + "' ";
*/        
    var sqlid7="PEdorTypeCTInputSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql7.setSqlId(sqlid7);//指定使用的Sql的id
	mySql7.addSubPara(fm.EdorItemAppDate.value);//指定传入的参数
	mySql7.addSubPara(fm.EdorItemAppDate.value);
	mySql7.addSubPara(fm.EdorItemAppDate.value);
	mySql7.addSubPara(MainPolNo);
	strSQL=mySql7.getString();
        
        var frr = easyExecSql(strSQL);
        var CalDate;
        if ( frr )
        {
            frr[0][0]==null||frr[0][0]=='null'?'0':disAvailable = frr[0][0];
            //alert(disAvailable);
            //判断主险是否失效
            if (disAvailable == 0)
            {
                //没有失效

                //判断交至日期

                var intv = dateDiffCT(fm.EdorItemAppDate.value, MainPayToDate, "D");
                //alert(intv);
                if (intv > 0)
                {
                    CalDate = fm.EdorItemAppDate.value;
                }
                else
                {
                    if (MainPayIntv == 0 || MainPayToDate == MainPayEndDate)
                    {
                        CalDate = fm.EdorItemAppDate.value;
                    }
                    else
                    {
                        CalDate = MainPayToDate;
                    }
                }

            }
            else
            {
                CalDate = MainPayToDate;
            }
        }

/*        strSQL = " select riskcode from lmrisksort " +
                 " where riskcode = '" + MainRiskCode +
                 "' and risksorttype = '30' ";
*/       
    var sqlid8="PEdorTypeCTInputSql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql8.setSqlId(sqlid8);//指定使用的Sql的id
	mySql8.addSubPara(MainRiskCode);//指定传入的参数
	strSQL=mySql8.getString();
        
        var srr = easyExecSql(strSQL);
        if ( srr )  //如果是不定期交费，保单年度显示实际已过年度
        {
            CalDate = fm.EdorItemAppDate.value;
        }
//				strSQL = "select floor(months_between('"+CalDate+"','"+fm.CvaliDate.value+"')) from dual"
        
    var sqlid9="PEdorTypeCTInputSql9";
	var mySql9=new SqlClass();
	mySql9.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql9.setSqlId(sqlid9);//指定使用的Sql的id
	mySql9.addSubPara(CalDate);//指定传入的参数
	mySql9.addSubPara(fm.CvaliDate.value);
	strSQL=mySql9.getString();
        
        srr = easyExecSql(strSQL);
        if(srr){
        	var intval = srr[0][0];
					//alert(intval);
        	var year = (intval- intval%12)/12;
        	var month = intval%12;
        	fm.Inteval.value = year + " 年 零 " + month + " 月";
				}
        if (MainPayIntv == 0)
        {
            //alert("趸交");
            //判断有无附加险
/*            strSQL = " select paytodate from lcPol " +
                     " where contno = '" + fm.ContNo.value +
                     "' and polno <> mainpolno and rownum = 1 and appflag = '1' ";
*/            
    var sqlid10="PEdorTypeCTInputSql10";
	var mySql10=new SqlClass();
	mySql10.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql10.setSqlId(sqlid10);//指定使用的Sql的id
	mySql10.addSubPara(fm.ContNo.value);//指定传入的参数
	strSQL=mySql10.getString();
            
            var mrr = easyExecSql(strSQL);
            if ( mrr )
            {
                mrr[0][0]==null||mrr[0][0]=='null'?'0':fm.PayToDate.value = mrr[0][0];
            }
            else
            {
                fm.PayToDate.value = "";
            }
        }
        else
        {
            //alert("期交");
            //判断期满
            //alert(MainPayEndDate);
            //alert(MainPayToDate);
            if (MainPayEndDate == MainPayToDate)
            {
                //判断有无附加险
/*                strSQL = " select paytodate from lcPol " +
                         " where contno = '" + fm.ContNo.value +
                         "' and polno <> mainpolno and rownum = 1  and appflag = '1' ";
*/               
    var sqlid11="PEdorTypeCTInputSql11";
	var mySql11=new SqlClass();
	mySql11.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql11.setSqlId(sqlid11);//指定使用的Sql的id
	mySql11.addSubPara(fm.ContNo.value);//指定传入的参数
	strSQL=mySql11.getString();
                
                var mrr = easyExecSql(strSQL);
                if ( mrr )
                {
                    mrr[0][0]==null||mrr[0][0]=='null'?'0':fm.PayToDate.value = mrr[0][0];
                }
                else
                {
                    fm.PayToDate.value = "";
                }
            }
            else
            {
                fm.PayToDate.value = MainPayToDate;
            }
        }
    }
}

function edorActBnfSave()
{
	//var row = BackFeeDetailGrid.mulLineCount;
	//alert("row:"+fm.BackFeeGetMoney.value);
	CustomerActBnfGrid.delBlankLine();
//	if(!CustomerActBnfGrid.checkValue2())
//	{
//		  return false;
//	}
	var row = CustomerActBnfGrid.mulLineCount;
	if(row<=0)
	{
		alert("未填写实际领取人！");
		return false;
	}
	var money = 0.0;
	for(var i=0;i<row;i++)
	{
			var tIDType=CustomerActBnfGrid.getRowColData(i,4);
		var tIDNo=CustomerActBnfGrid.getRowColData(i,5);
		if(tIDType=="0")
		{
		   tBirthday=getBirthdatByIdNo(tIDNo);
		   tSex=getSexByIDNo(tIDNo);
		   if(tBirthday=="输入的身份证号位数错误"||tSex=="输入的身份证号位数错误")
		   {
		   	  str="第"+(i+1)+"行身份证号位数输入错误!!!" ;
		   	  return false;
		   }
		   else
		   {  
			   CustomerActBnfGrid.setRowColData(i,3,tBirthday) ;
			   CustomerActBnfGrid.setRowColData(i,2,tSex) ;
		   }
	    }
		if(CustomerActBnfGrid.getRowColData(i,7)!="1"&&CustomerActBnfGrid.getRowColData(i,7)!="4"&&CustomerActBnfGrid.getRowColData(i,7)!="9")
		{
			alert("实际领取人列表中第"+(i+1)+"行领取方式不正确，请下拉选择！");
			return false;
		}
		
		if(CustomerActBnfGrid.getRowColData(i,7)=="4" || CustomerActBnfGrid.getRowColData(i,7)=="9")
		{
			if(CustomerActBnfGrid.getRowColData(i,8)==null||CustomerActBnfGrid.getRowColData(i,8)==""||
					CustomerActBnfGrid.getRowColData(i,9)==null||CustomerActBnfGrid.getRowColData(i,9)==""||
					CustomerActBnfGrid.getRowColData(i,10)==null||CustomerActBnfGrid.getRowColData(i,10)=="")
			{
				alert("实际领取人列表中第"+(i+1)+"行领取方式为银行转账或网银代付，所以必须填写银行编码、银行账号和银行帐户名");
				return false;
			}
			//if(CustomerActBnfGrid.getRowColData(i,9))
						//银行转帐基础上加上银行帐号过虑校验
			if(!checkBank(CustomerActBnfGrid.getRowColData(i,8),CustomerActBnfGrid.getRowColData(i,9)))
			{
			  //CustomerActBnfGrid.setRowColData(i,8,"");
			  CustomerActBnfGrid.setRowColData(i,9,"");
			  return false;
			}
		}
		money+=parseFloat(CustomerActBnfGrid.getRowColData(i,6))*100;
	}
//	if(money!=parseFloat(fm.BackFeeGetMoney.value))
//	{
//		alert("实际领取金额合计不等于本次付费总金额！")
//		return false;
//	}
	 if(money!=100)
	  {
		  alert("实际领取人领取比例合计不是100%！请重新录入领取比例！");
		  return false;
	  }

	fm.fmtransact.value="EDORITEM|UPDATE";
	//alert(fm.fmtransact.value);
	  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();	  
		document.getElementById("fm").submit();
}


//add by jiaqiangli 2009-06-23
function GetBankCode(spanID) {
	if (document.all(spanID).all('CustomerActBnfGrid7').value == null || document.all(spanID).all('CustomerActBnfGrid7').value == "")
		return;
	//银行转帐 ldbank 与银行接口签发合作协议的
	if (document.all(spanID).all('CustomerActBnfGrid7').value == "4") {
		showCodeList('bank',[document.all(spanID).all('CustomerActBnfGrid8')],null,null,null,null,"1",null,"1");
	}
	//网银代付 ldcode
	else if (document.all(spanID).all('CustomerActBnfGrid7').value == "9") {
		//finabank
		showCodeList('finabank',[document.all(spanID).all('CustomerActBnfGrid8')],null,null,null,null,"1",null,"1");
	}		
}
//add by jiaqiangli 2009-06-23

function edorTypeCTSave()
{

    var row = PolGrid.mulLineCount;
    //用于判断在选择险种退保时，如果选择主险，则要求将附件险一起选择
    var tFlag=false;
    var i = 0;
      for(var m = 0; m < row; m++ )
      {
        if(PolGrid.getChkNo(m))
        {
        	var tmpPolNo = PolGrid.getRowColData(m,9);
         if(isMainPolno(tmpPolNo))
         {
        	tFlag=true;
         }
         //add by sunsx 校验分红险种是否有未分配红利 2010-01-27
//         var tChkBonusSql = "select distinct 1 from lcpol a where polno = '"+tmpPolNo+"' and Exists (select 1 from lmriskapp b where a.riskcode = b.riskcode and bonusflag = '1') and ((sysdate >= paytodate and floor(months_between(paytodate, cvalidate) / 12) > (select count(1) from lobonuspol where polno = a.polno and bonusflag = '1')) or (sysdate < paytodate and floor(months_between(sysdate, cvalidate) / 12) > (select count(1) from lobonuspol where polno = a.polno and bonusflag = '1')))";
    
    var tChkBonusSql = "";     
    var sqlid12="PEdorTypeCTInputSql12";
	var mySql12=new SqlClass();
	mySql12.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql12.setSqlId(sqlid12);//指定使用的Sql的id
	mySql12.addSubPara(tmpPolNo);//指定传入的参数
	tChkBonusSql=mySql12.getString();
         
         var tArr = easyExecSql(tChkBonusSql, 1, 0, 1);
         if (tArr != null)
				 {
		  		 var tBonusRet = tArr[0][0]; 
		  		 if(tBonusRet == "1")
		  		 {
		  		 		alert("选中的退保险种："+PolGrid.getRowColData(m,2)+"存在未分配红利,不能退保！");
		  		 		return;
		  		 }
				 }
         i = i+1;
        }
      }
      if(i == 0)
      {
        alert("请选择需要退保的险种！");
        return false;
      }
      //
      var tNoChek=false;
      for(var k = 0; k < row;k++ )
      {
        if(!PolGrid.getChkNo(k))
        {
         if(!isMainPolno(PolGrid.getRowColData(k,9)))
         {
          tNoChek=true;
         }
       }
      }
      if(tNoChek&&tFlag)
      {
        alert("已经选择主险，请选择附加险一起退保");
        return false;
      }
     //判断是整单退还是部分险种退
     if(tFlag)
     {
     	fm.WTContFLag.value="1";
     }else
     	{
     	fm.WTContFLag.value="0";
     		}

    if (fm.SurrReason.value == null || fm.SurrReason.value == "")
    {
        alert("请录入退保原因!");
        return;
    }
    if(fm.SurrReason.value=='06')
    {
    	divRemarkInfo.style.display="block";
    	fm.SurrReasonName.value=fm.Remark.value;
    	}
    //add by sunsx 2008-08-26 有补发记录的校验	
    if(fm.TrueLostTimes.value > 0 )
    	{
    		if(fm.LostTimes.value == null || fm.LostTimes.value == "")
    		{
    			alert("该保单有补发记录，请录入补发记录！");
    			fm.LostTimes.focus();
    			return;
    		}
    		
    		if(fm.LostTimes.value != fm.TrueLostTimes.value)
    		{
    			alert("输入的补发次数不正确请核实！");
    			return;
    		}
    		//return;
    	}

    //<!-- XinYQ modified on 2006-03-01 : 补发保单退保时提示补发打印日期 : BGN -->
    //if (!checkIsReissue()) return; //sunsx modified on 2008-08-26
    //<!-- XinYQ modified on 2006-03-01 : 补发保单退保时提示补发打印日期 : END -->

  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  document.getElementById("fm").submit();
}
function showInfo()
{
	    //alert(12);
	    if(fm.SurrReason.value=='06')
    {
    	divRemarkInfo.style.display="block";
    	fm.SurrReasonName.value=fm.Remark.value;
    	}
	}

function queryLRInfo()
{
//		var tSQL = "select c.losttimes from lccont c where c.contno ='"+fm.ContNo.value+"'";
		
	var tSQL = "";     
    var sqlid13="PEdorTypeCTInputSql13";
	var mySql13=new SqlClass();
	mySql13.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql13.setSqlId(sqlid13);//指定使用的Sql的id
	mySql13.addSubPara(fm.ContNo.value);//指定传入的参数
	tSQL=mySql13.getString();
		
		var ret = easyExecSql(tSQL);
		if(ret)
		{
			var tLostTimes = ret[0][0];
			if(tLostTimes > 0)
			{
				fm.TrueLostTimes.value = tLostTimes;
				divLRInfo.style.display="";
			}
			else
			{
				fm.TrueLostTimes.value = 0;
			}
		}
}
//<!-- XinYQ added on 2006-03-01 : 补发保单退保时提示补发打印日期 : BGN -->
/*============================================================================*/

/**
 * 检查是否是补发打印保单
 */
function checkIsReissue()
{
    var QuerySQL, arrResult;
    //查询是否做过保单补发
/*    QuerySQL = "select 'X' "
             +   "from LPEdorItem "
             +  "where 1 = 1 "
             +  getWherePart("ContNo", "ContNo")
             +    "and EdorType = 'LR' "
             +    "and EdorState = '0'";
    //alert(QuerySQL);
*/      
    var sqlid14="PEdorTypeCTInputSql14";
	var mySql14=new SqlClass();
	mySql14.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql14.setSqlId(sqlid14);//指定使用的Sql的id
	mySql14.addSubPara(fm.ContNo.value);//指定传入的参数
	QuerySQL=mySql14.getString();
    
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("警告：查询是否做过保单补发出现异常！ ");
        return true;
    }
    if (arrResult != null)
    {
        var sConfirmMsg = "该保单做过补发，";
        //查询补发保单打印日期
/*        QuerySQL = "select distinct max(MakeDate) "
                 +   "from LDContInvoiceMap "
                 +  "where 1 = 1 "
                 +  getWherePart("ContNo", "ContNo")
                 +    "and OperType = '4'";
        //alert(QuerySQL);
*/        
    var sqlid15="PEdorTypeCTInputSql15";
	var mySql15=new SqlClass();
	mySql15.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql15.setSqlId(sqlid15);//指定使用的Sql的id
	mySql15.addSubPara(fm.ContNo.value);//指定传入的参数
	QuerySQL=mySql15.getString();
        
        try
        {
            arrResult = easyExecSql(QuerySQL, 1, 0);
        }
        catch (ex)
        {
            alert("警告：查询补发或重新出单的打印日期出现异常！ ");
            return true;
        }
        if (arrResult != null && trim(arrResult[0][0]) != "")
        {
            sConfirmMsg += "最近一次打印日期为 " + trim(arrResult[0][0]) + "，";
        }
        sConfirmMsg += "是否继续退保？ ";
        //确认提示退保
        if (!confirm(sConfirmMsg))
        {
            return false;
        }
    }
    return true;
}

/*============================================================================*/
//<!-- XinYQ added on 2006-03-01 : 补发保单退保时提示补发打印日期 : END -->


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

    if (FlagStr == "Succ")
    {
        try
        {
            chkPol();
            getZTMoney();
            queryBackFee();
            top.opener.getEdorItemGrid();
            getCustomerActBnfGrid();
        } catch (ex) {}
    }
}


function returnParent()
{
    top.close();
    top.opener.focus();
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


/**
* 计算两个日期的差,返回差的月数(M)或天数(D) (其中天数除2.29这一天)
* <p><b>Example: </b><p>
* <p>dateDiff("2002-10-1", "2002-10-3", "D") returns "2"<p>
* <p>dateDiff("2002-1-1", "2002-10-3", "M") returns "9"<p>
* @param dateStart 减日期
* @param dateEnd 被减日期
* @param MD 标记，“M”为要求返回差的月数；“D”为要求返回差的天数
* @return 返回两个日期差的月数(M)或天数(D)
*/
function dateDiffCT(dateStart,dateEnd,MD)
{
  if(dateStart==""||dateEnd=="")
  {
    return false;
  }
  if (typeof(dateStart) == "string") {
    dateStart = getDate(dateStart);
  }

  if (typeof(dateEnd) == "string") {
    dateEnd = getDate(dateEnd);
  }

  var i;
  if(MD=="D") //按天计算差
  {
    var endD = dateEnd.getDate();
    var endM = dateEnd.getMonth();
    var endY = dateEnd.getFullYear();
    var startD = dateStart.getDate();
    var startM = dateStart.getMonth();
    var startY = dateStart.getFullYear();
    var startT=new Date(startY,startM-1,startD);
    var endT=new Date(endY,endM-1,endD);
    var diffDay=(endT.valueOf()-startT.valueOf())/86400000;
    return diffDay;
  }
  else //按月计算差
  {
    var endD = dateEnd.getDate();
    var endM = dateEnd.getMonth();
    var endY = dateEnd.getFullYear();
    var startD = dateStart.getDate();
    var startM = dateStart.getMonth();
    var startY = dateStart.getFullYear();

    if(endD<startD)
    {
      return (endY-startY)*12 + (endM-startM) -1;  //只算整月数
    }
    else
    {
      return (endY-startY)*12 + (endM-startM);
    }
  }
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

/**
* 获取日期对象
* @param strDate 日期字符串
* @param splitOp 分割符
* @return 返回日期对象
*/
function getDate(strDate, splitOp) {
  if (splitOp == null) splitOp = "-";

  var arrDate = strDate.split(splitOp);
  if (arrDate[2] == "31")
  {
    arrDate[2] = "30";
  }
  if (arrDate[1].length == 1) arrDate[1] = "0" + arrDate[1];
  if (arrDate[2].length == 1) arrDate[2] = "0" + arrDate[2];

  return new Date(arrDate[0], arrDate[1], arrDate[2]);

}

function getZTMoney()
{
/*    strSQL = " select getmoney,EdorReasonCode,EdorReason, standbyflag2, " +
    		 " (select trim(n.codename) from LDCode n where trim(n.codetype) = 'cttype' and trim(n.code) = trim(standbyflag1))" +
    		 " from lpedoritem " +
    		 " where edoracceptno = '" + fm.EdorAcceptNo.value +
    		 "' and edorno = '" + fm.EdorNo.value +
    		 "' and edortype = 'CT' ";
*/
	var sqlid16="PEdorTypeCTInputSql16";
	var mySql16=new SqlClass();
	mySql16.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql16.setSqlId(sqlid16);//指定使用的Sql的id
	mySql16.addSubPara(fm.EdorAcceptNo.value);//指定传入的参数
	mySql16.addSubPara(fm.EdorNo.value);
	strSQL=mySql16.getString();

    var brr = easyExecSql(strSQL);

    if ( brr )
    {
        //brr[0][0]==null||brr[0][0]=='null'?'0':fm.GetMoney.value     	= brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.SurrReason.value     	= brr[0][1];
        brr[0][2]==null||brr[0][2]=='null'?'0':fm.SurrReasonName.value  = brr[0][2];
        //brr[0][3]==null||brr[0][3]=='null'?'0':fm.RelationToAppnt.value  = brr[0][3];
        brr[0][4]==null||brr[0][4]=='null'?'0':fm.CTType.value  = brr[0][4];
    }

}

function checkMainPol(spanId)
{
		var rowNum=PolGrid.mulLineCount;//总行数
		var rowLine = spanId.substr(11,1); //当前被选中的行数；
		
		if(document.all(spanId).all('InpPolGridChk').value=='1')
		{
			var tPolNo = PolGrid.getRowColData(rowLine,9);
			var tMainPolNo = PolGrid.getRowColData(rowLine,12);
			if(tPolNo == tMainPolNo)
			{
				var i;
				var hasAdd = false;
				for(i = 0;i<rowNum;i++)
				{
					var rMainPolNo = PolGrid.getRowColData(i,12);
					var rPolNo = PolGrid.getRowColData(i,9);
					if(rMainPolNo == tMainPolNo && rMainPolNo != rPolNo)
					{
						//alert(tRiskCode);
						hasAdd = true;
						PolGrid.checkBoxSel(i+1);
					}
				}
				if(hasAdd)
				{
						alert("提示：您选中的是主险保单，该保单下的所有附加险将一并解除。");
				}
			}
		}
		
		if(document.all(spanId).all('InpPolGridChk').value=='0')
		{
			var tPolNo = PolGrid.getRowColData(rowLine,9);
			var tMainPolNo = PolGrid.getRowColData(rowLine,12);
			if(tPolNo == tMainPolNo)
			{
				var i;
				for(i = 0;i<rowNum;i++)
				{
					var rMainPolNo = PolGrid.getRowColData(i,12);
					if(rMainPolNo == tMainPolNo)
					{
						//alert(tRiskCode);
						PolGrid.checkBoxNotSel(i+1);
					}
				}
				//alert("提示：您选中的是主险保单，该保单下的所有附加险将一并解除。");
			}
		}
}

/**判断此险种是否是主险，*/
function isMainPolno(tPolNo)
{
//	var tSQL="select 1 from lcpol  where polno='"+tPolNo+"' and mainpolno=polno and contno='"+document.all('ContNo').value+"'";
	
	var tSQL="";
	var sqlid17="PEdorTypeCTInputSql17";
	var mySql17=new SqlClass();
	mySql17.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql17.setSqlId(sqlid17);//指定使用的Sql的id
	mySql17.addSubPara(tPolNo);//指定传入的参数
	mySql17.addSubPara(document.all('ContNo').value);
	tSQL=mySql17.getString();
	
	var tFlag=easyExecSql(tSQL);
	//alert(tPolNo);
	if(tFlag==null)
	{
		return false;
	}else
		{
			return true;
			}
	
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

