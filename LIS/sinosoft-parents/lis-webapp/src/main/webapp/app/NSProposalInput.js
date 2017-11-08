//               该文件中包含客户端需要处理的函数和事件
//only use for bq
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var showInfo;
var spanObj;
var mDebug = "100";
var mOperate = 0;
var mAction = "";
var arrResult = new Array();
var mShowCustomerDetail = "PROPOSAL";
var mCurOperateFlag = ""    // "1--录入，"2"--查询
var mGrpFlag = "";  //个人集体标志,"0"表示个人,"1"表示集体.
var cflag = "0";        //文件件录入位置
var sign=0;
var risksql;
var arrGrpRisk = null;
var mainRiskPolNo="";
var cMainRiskCode="";
var mSwitch = parent.VD.gVSwitch;
window.onfocus = myonfocus;
var hiddenBankInfo = "";

/*********************************************************************
 *  选择险种后的动作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterCodeSelect( cCodeName, Field ) {
    try {
    	//针对各种险种的特殊处理
    	try{
        	//交费期间
        	if(cCodeName.substring(1,cCodeName.indexOf("-"))=="PayEndYear")
            {	
        		//MS康泰重大疾病两全保险
        		if(fm.RiskCode.value=="111502"||fm.RiskCode.value=="111501"||fm.RiskCode.value=="112212"||fm.RiskCode.value=="112208")
        		{ 
                
                	if(Field.value=="88")
                	{
                		 fm.PayEndYearFlag.value="A";
                	}
                	else
                	{
                		 fm.PayEndYearFlag.value="Y";
                	}

                }
                
        		//MS康吉重大疾病保险 111501  ||  311501 MS康吉重大疾病保险（中介）
        		if(fm.RiskCode.value=="111501"||fm.RiskCode.value=="311501")
        		{ 
                	if(Field.value=="88"||Field.value=="55"||Field.value=="60")
                	{
                		 fm.PayEndYearFlag.value="A";
                	}
                	else
                	{
                		 fm.PayEndYearFlag.value="Y";
                	}

                }
        		//MS长瑞年金保险（分红型）
        		if(fm.RiskCode.value=="112401")
        		{ 
                
                	if(Field.value=="5"||Field.value=="10"||Field.value=="20")
                	{
                		 fm.PayEndYearFlag.value="Y";
                	}
                	else
                	{
                		 fm.PayEndYearFlag.value="A";
                	}

                }
        		//312204 MS金玉满堂两全保险D款（分红型）
        		if(fm.RiskCode.value=="312204")
        		{ 
                
                	if(Field.value=="5")
                	{
                		 fm.GetDutyKind.value="5";
                	}
                	else if (Field.value=="8")
                	{
                		 fm.GetDutyKind.value="8";
                	}

                }
                //112206 MS金榜题名少儿教育金两全保险（分红型）
                //122201 MS附加初中教育金两全保险（分红型）
                //122202 MS附加高中教育金两全保险（分红型）
        		if(fm.RiskCode.value=="112206"||fm.RiskCode.value=="122201"||fm.RiskCode.value=="122202")
        		{ 
                
                	if(Field.value=="5")
                	{
                		 fm.PayEndYearFlag.value="Y";
                	}
                	else
                	{
                		 fm.PayEndYearFlag.value="A";
                	}

                }
        		
        		//MS如意年年两全保险(分红型)
        		if(fm.RiskCode.value=="112207")
        		{ 
                
        			if(Field.value=="60"||Field.value=="70")
              		{
              			 document.all('PayEndYearFlag').value="A";
              		     document.all('GetDutyKind').value="0";
              		}
              		else if(Field.value=="5")
              	     {
              	     	document.all('PayEndYearFlag').value="Y";
              		     document.all('GetDutyKind').value="1";
              	     }
              	    else if(Field.value=="10")
              	    {
              	    	     document.all('PayEndYearFlag').value="Y";
              		     document.all('GetDutyKind').value="2";
              	    }
              	    else if(Field.value=="15")
              	    {
              		     document.all('PayEndYearFlag').value="Y";
              		     document.all('GetDutyKind').value="3";
              	    }
                	    else if(Field.value=="20")
              	    {
              		     document.all('PayEndYearFlag').value="Y";
              		     document.all('GetDutyKind').value="4";
              	    }      	

                }
    		 	//112202 MS长顺两全保险（分红型) 或者 112203 MS长裕两全保险（分红型）
    		 	if(fm.RiskCode.value=="112202"||fm.RiskCode.value=="112203")
        		  { 
        		  	if(Field.value=="1000"||Field.value=="60")
                	{
                		 fm.PayEndYearFlag.value="A";
                	}
                	else
                	{
                		 fm.PayEndYearFlag.value="Y";
                	}
                }
                          //111301 MS安康定期保险
              if(fm.RiskCode.value=="111301")
               { 
            	   if(Field.value=="70")
            	   {
            		   fm.PayEndYearFlag.value="A";
            	   }
                   else
               	   {
                       fm.PayEndYearFlag.value="Y";
               	   }

               } 
                //MS长宁终身寿险(分红型)||111504  MS康泰一生重大疾病保险
                if(fm.RiskCode.value=="112101"||fm.RiskCode.value=="111504")
        		  { 
        		  	if(Field.value=="1000")
                	{
                		 fm.PayEndYearFlag.value="A";
                	}
                	else
                	{
                		 fm.PayEndYearFlag.value="Y";
                	}
                }

			if(fm.RiskCode.value=="121508")
			{
			      if(Field.value=="10"||Field.value=="15"||Field.value=="20")
			      {
			            fm.PayEndYearFlag.value="Y";
			       }
			            	
			}
                
                //MS金榜题名两全保险(分红型)
                 if(fm.RiskCode.value=="112201")
        		  { 
                		 fm.PayEndYearFlag.value="Y";
                }
        		
        		return;
            }
            


           //针对保险期间的处理
           if(cCodeName.substring(1,cCodeName.indexOf("-"))=="InsuYear")
           {
                	
               //MS附加久久同寿定期寿险 
               if(fm.RiskCode.value=="121303")
               { 
    	           if(Field.value=="60")
    	           {
    	            	 fm.InsuYearFlag.value="A";
    	           }
    	           else
    	           {
    	                fm.InsuYearFlag.value="Y";
    	           }

               }
              //112212 MS富贵年年两全保险(分红型)  
               if(fm.RiskCode.value=="112212")
               { 
    	           if(Field.value=="80")
    	           {
    	            	 fm.GetDutyKind.value="2";
    	           }
    	           else
    	           {
    	                fm.GetDutyKind.value="1";
    	           }

               }
               //121305 MS附加定期寿
               //121505 MS附加额外给付重大疾病保险
               if(fm.RiskCode.value=="121305"||fm.RiskCode.value=="121505")
               { 
    	           if(Field.value=="60"||Field.value=="80"||Field.value=="88")
    	           {
    	            	 fm.InsuYearFlag.value="A";
    	           }
    	           else
    	           {
    	                fm.InsuYearFlag.value="Y";
    	           }

               }
                   
                    
                      
               
               //MS久久同瑞两全保险（分红型），保险期间进行处理  || 121504  MS附加久久同康额外给付重大疾病保险
               if(fm.RiskCode.value=="112211" ||fm.RiskCode.value=="121504")
               { 
            	   if(Field.value=="60")
            	   {
            		   fm.InsuYearFlag.value="A";
            	   }
                   else
               	   {
                       fm.InsuYearFlag.value="Y";
               	   }

               }
               //MS如意相伴两全保险（分红型）
               if(fm.RiskCode.value=="112213" || fm.RiskCode.value=="112216" || fm.RiskCode.value=="121509" || fm.RiskCode.value=="121510")
               { 
            	   if(Field.value=="60"||Field.value=="88")
            	   {
            		   fm.InsuYearFlag.value="A";
            	   }
                   else
               	   {
                       fm.InsuYearFlag.value="Y";
               	   }

               } 
               //MS康泰重大疾病两全保险，保险期间进行处理 ||  112208 MS长泰两全保险（分红型）
               //121501 MS附加提前给付重大疾病保险
               if(fm.RiskCode.value=="111502"||fm.RiskCode.value=="112208"||fm.RiskCode.value=="121501"||fm.RiskCode.value=="121508")
               { 
            	   if(Field.value=="88")
            	   {
            		   fm.InsuYearFlag.value="A";
            	   }
                   else
               	   {
                       fm.InsuYearFlag.value="Y";
               	   }

               }   


               return;
          }
           
           //交费方式
           if(cCodeName.substring(1,cCodeName.indexOf("-")) == 'PayIntv')
           {
               if (Field.value == "0") 
               {
                 //document.all('TDPayEndYear').innerHTML = "<Input class=codeno name=PayEndYear verify=\"缴费期限|code:!PayEndYear&notnull\" onfocus=\"checkPayIntv()\"><input class=codename name=PayEndYearName readonly=true elementtype=nacessary>";
                 //document.all("PayEndYear").readOnly = true;
                 //document.all("PayEndYear").value = "1000";
                 //document.all("PayEndYearName").value = "年";
             	  
             	  //2008-08-25 改为和5.3一致，但为趸交时交费期间和保险期间一致
             	  if(!(document.all("InsuYear").value==null||document.all("InsuYear").value==""))
             	  {
             		  document.all('PayEndYear').value=document.all('InsuYear').value;
           	  		  document.all('PayEndYearFlag').value=document.all('InsuYearFlag').value;	
           	  		  document.all('PayEndYearName').value=document.all('InsuYearName').value;
           	  		  document.all("PayEndYear").readOnly = true;
             	  }
               }
               
               return;
          }   	
        }
    	catch(ex){}
      //险种选择
      //alert("cCodeName=="+cCodeName);
      try{
        if( type =="noScan" && cCodeName == 'RiskInd')//个单无扫描件录入
          {
              //var strSql = "select distinct 1 from ldsysvar where VERIFYOPERATEPOPEDOM('"+Field.value+"','"+ManageCom+"','"+ManageCom+"',"+"'Pa')=1 ";  
            var sqlid1="NSProposalInputSql0";
          	var mySql1=new SqlClass();
          	mySql1.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
          	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
          	mySql1.addSubPara(Field.value);//指定传入的参数
          	mySql1.addSubPara(ManageCom);//指定传入的参数
          	mySql1.addSubPara(ManageCom);//指定传入的参数
          	var strSql=mySql1.getString();
           //alert(strSql);
          var arrResult = easyExecSql(strSql);
           //alert(arrResult);
          if (arrResult == null) {
            alert("机构 ["+ManageCom+"] 无权录入险种 ["+Field.value+"] 的投保单!");
            gotoInputPage();
            return ;
          }
            return;
      }

    }
    catch(ex) {}

      try{
        //alert(cCodeName);
        if(cCodeName == 'RiskInd'){
          if (typeof(prtNo)!="undefined" && typeof(type)=="undefined" )//个单有扫描件录入
          {
            //alert(cCodeName);
            //var strSql2 = "select distinct 1 from ldsysvar where VERIFYOPERATEPOPEDOM('"+Field.value+"','"+ManageCom+"','"+ManageCom+"',"+"'Pz')=1 ";
            var sqlid2="NSProposalInputSql1";
          	var mySql2=new SqlClass();
          	mySql2.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
          	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
          	mySql2.addSubPara(Field.value);//指定传入的参数
          	mySql2.addSubPara(ManageCom);//指定传入的参数
          	mySql2.addSubPara(ManageCom);//指定传入的参数
          	var strSql2=mySql2.getString();
            //alert(strSql2);
            var arrResult2 = easyExecSql(strSql2);
            //alert(arrResult2);
            if (arrResult2 == null) {
              alert("机构 ["+ManageCom+"] 无权录入险种 ["+Field.value+"] 的投保单!");
              gotoInputPage();
              return ;
            }
          }
          return;
        }
    }
    catch(ex){}

    //交费方式选择的处理
    try{
        //alert(cCodeName);
        if(cCodeName.substring(1,cCodeName.indexOf("-")) == 'PayIntv'){
          if (Field.value == "0") {
            //alert("hahha");
            document.all("PayEndYear").readOnly = true;
            document.all("PayEndYear").value = "1000";
            document.all("PayEndYearName").value = "趸交";
          }
          else if(document.all("PayEndYear").value == "1000"){
            //alert("here");
            document.all("PayEndYear").readOnly = false;
            document.all("PayEndYear").value = "";
            document.all("PayEndYearName").value = "";
            }
        }
       }
    catch(ex){}
   //alert(cCodeName);//mark by yaory 初始化险种界面
    if( cCodeName == "RiskInd" || cCodeName == "RiskGrp" || cCodeName == "RiskCode" || cCodeName.substring(0,3)=="***" || cCodeName.substring(0,2)=="**")
      {
        var tProposalGrpContNo = parent.VD.gVSwitch.getVar( "GrpContNo" );
        var tContPlanCode = parent.VD.gVSwitch.getVar( "ContPlanCode" );
        //alert("mainRiskPolNo:"+mainRiskPolNo);
        if(mainRiskPolNo==""&&parent.VD.gVSwitch.getVar("mainRiskPolNo")==true){
          mainRiskPolNo=parent.VD.gVSwitch.getVar("mainRiskPolNo");
        }
        if(cCodeName=="RiskCode"){
          if(!isSubRisk(Field.value)){
              cMainRiskCode=Field.value;
            }
          else if(isSubRisk(Field.value)&&mainRiskPolNo!=""){
            var mainRiskSql="select riskcode from lcpol where polno='"+mainRiskPolNo+"'";
            var arr = easyExecSql(mainRiskSql);
            cMainRiskCode=arr[0];
          }
          //alert("mainriskcode:"+cMainRiskCode);
          //alert("mainriskpolno:"+mainRiskPolNo);
        }
        //alert("mainRiskPolNo2:"+mainRiskPolNo);
        if(LoadFlag!=2&&LoadFlag!=7)
        {
        	//alert("wwww");
          getRiskInput(Field.value, LoadFlag);//LoadFlag在页面出始化的时候声明
        }
        else
        {
          if (tContPlanCode=='false'||tContPlanCode==null||tContPlanCode=='')
            {
                    //如果没有保险计划编码,查询有没有默认值，如果有默认值也需要调用后台查询

                    //var strsql = "select ContPlanCode from LCContPlan where ContPlanCode='00' and ProposalGrpContNo='"+tProposalGrpContNo+"'";
        	    var sqlid3="NSProposalInputSql2";
            	var mySql3=new SqlClass();
            	mySql3.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
            	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
            	mySql3.addSubPara(tProposalGrpContNo);//指定传入的参数
            	var strSql=mySql3.getString();
                    //alert(strsql);
                    var defultContPlanCode=easyExecSql(strsql);
                    //alert("defultContPlanCode"+defultContPlanCode);
                    if (defultContPlanCode=='00')
                    {
                        queryRiskPlan( tProposalGrpContNo,Field.value,defultContPlanCode,cMainRiskCode );
                    }
                    else
                    {
                        getRiskInput(Field.value, LoadFlag);//LoadFlag在页面出始化的时候声明
                    }

            }
            else
            {
                    //alert("aa");
                    queryRiskPlan( tProposalGrpContNo,Field.value,tContPlanCode,cMainRiskCode );
            }
        }
        //将扫描件图片翻到第一页
        try {
          goToPic(0);
        }
        catch(ex2){}
          return;
      }

      //自动填写受益人信息
      if (cCodeName == "customertype") {
        if (Field.value == "A") {
          if(ContType!="2")
          {
          var index = BnfGrid.mulLineCount;
          BnfGrid.setRowColData(index-1, 2, document.all("AppntName").value);
          BnfGrid.setRowColData(index-1, 4, document.all("AppntIDType").value);

          BnfGrid.setRowColData(index-1, 5, document.all("AppntIDNo").value);
          BnfGrid.setRowColData(index-1, 6,parent.VD.gVSwitch.getVar( "RelationToAppnt"));

          BnfGrid.setRowColData(index-1, 9, document.all("AppntHomeAddress").value);
          //hl
          BnfGrid.setRowColData(index-1, 10, document.all("AppntNo").value);

          }
          else
          {
            alert("投保人为团体，不能作为受益人！")
          var index = BnfGrid.mulLineCount;
          BnfGrid.setRowColData(index-1, 2, "");
          BnfGrid.setRowColData(index-1, 4, "");
          BnfGrid.setRowColData(index-1, 5, "");
          BnfGrid.setRowColData(index-1, 6, "");
          BnfGrid.setRowColData(index-1, 9, "");
          }
        }
        else if (Field.value == "I") {
          var index = BnfGrid.mulLineCount;
        BnfGrid.setRowColData(index-1, 2, document.all("Name").value);
        BnfGrid.setRowColData(index-1, 4, document.all("IDType").value);
        BnfGrid.setRowColData(index-1, 5, document.all("IDNo").value);
        BnfGrid.setRowColData(index-1, 6, "00");
        //BnfGrid.setRowColData(index-1, 8, document.all("AppntHomeAddress").value);
        //hl
        BnfGrid.setRowColData(index-1, 9, document.all("HomeAddress").value);
        BnfGrid.setRowColData(index-1, 10, document.all("InsuredNo").value);

        }
        return;
    }

        //收费方式选择
    if (cCodeName == "PayLocation") {
        if (Field.value != "0") {
          if (hiddenBankInfo=="") hiddenBankInfo = DivLCKind.innerHTML;
          document.all("BankCode").className = "readonly";
          document.all("BankCode").readOnly = true;
          document.all("BankCode").tabIndex = -1;
          document.all("BankCode").ondblclick = "";

          document.all("AccName").className = "readonly";
          document.all("AccName").readOnly = true;
          document.all("AccName").tabIndex = -1;
          document.all("AccName").ondblclick = "";

          document.all("BankAccNo").className = "readonly";
          document.all("BankAccNo").readOnly = true;
          document.all("BankAccNo").tabIndex = -1;
          document.all("BankAccNo").ondblclick = "";
        }
        else{
          if (hiddenBankInfo!="") DivLCKind.innerHTML = hiddenBankInfo;
        //strSql = "select BankCode,BankAccNo,AccName from LCAppNT where AppNtNo = '" + document.all('AppntCustomerNo').value + "' and contno='"+document.all('ContNo').value+"'";
        var sqlid4="NSProposalInputSql3";
    	var mySql4=new SqlClass();
    	mySql4.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
    	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
    	mySql4.addSubPara(document.all('AppntCustomerNo').value);//指定传入的参数
    	mySql4.addSubPara(document.all('ContNo').value);//指定传入的参数
    	 strSql=mySql4.getString();
            var arrAppNtInfo = easyExecSql(strSql);
            //alert(strSql);
          document.all("BankCode").value = arrAppNtInfo[0][0];
          document.all("AccName").value = arrAppNtInfo[0][2];
          document.all("BankAccNo").value = arrAppNtInfo[0][1];
          document.all("PayLocation").value = "0";
          document.all("PayLocation").focus();
        }
        return;
    }
      //责任代码选择
      if(cCodeName == "DutyCode"){
        var index = DutyGrid.mulLineCount;
        //var strSql = "select dutyname from lmduty where dutycode='"+Field.value+"'";
        var sqlid5="NSProposalInputSql4";
    	var mySql5=new SqlClass();
    	mySql5.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
    	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
    	mySql5.addSubPara(Field.value);//指定传入的参数
    	var strSql=mySql5.getString();
        var arrResult = easyExecSql(strSql);
        var dutyname= arrResult[0].toString();
        DutyGrid.setRowColData(index-1, 2, dutyname);
        return;
      }
      //alert(cCodeName);
      if(cCodeName == "PolCalRule1"){  //暂时未用
        if(Field.value=="1"){       //统一费率
          divFloatRate.style.display="none";
          divFloatRate2.style.display="";
        }
        else if(Field.value=="2"){  //约定费率折扣
          document.all('FloatRate').value="";
          divFloatRate.style.display="";
          divFloatRate2.style.display="none";
        }
        else{
        divFloatRate.style.display="none";
        divFloatRate2.style.display="none";
        }
        return;
      }
      if(cCodeName=="PayEndYear"){
        getOtherInfo(Field.value);
        return;
      }
      if(cCodeName=="GetYear"){
      getGetYearFlag(Field.value);
        return;
      }

      if(cCodeName=="PayRuleCode"){
        fm.action="./ProposalQueryPayRule.jsp?AppFlag=0";
        fm.submit();
        //parent.fraSubmit.window.location ="./ProposalQueryPayRule.jsp?GrpContNo="
        //      + document.all('GrpContNo').value+"&RiskCode="+document.all('RiskCode').value
        //      +"&InsuredNo="+document.all('InsuredNo').value
        //      +"&PayRuleCode="+document.all('PayRuleCode').value
        //      +"&JoinCompanyDate="+document.all(JoinCompanyDate).value
        //      +"&AppFlag=0";
        return;
      }
  }
    catch( ex ) {
    }

}
/*********************************************************************
 *  提交前的校验、计算
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function beforeSubmit()
{
  if( verifyInput() == false ) return false;
}

/*********************************************************************
 *  根据LoadFlag设置一些Flag参数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function convertFlag( cFlag )
{
  //alert("cFlag:" + cFlag);
    if( cFlag == "1"|| cFlag == "8")        // 个人投保单直接录入
    {
        mCurOperateFlag = "1";
        mGrpFlag = "0";
        return;
    }
    if( cFlag == "2" || cFlag == "7" || cFlag == "9" || cFlag == "13"||cFlag == "14"||cFlag == "16"||cFlag == "99"||cFlag=="23")        // 集体下个人投保单录入
    {
        mCurOperateFlag = "1";
        mGrpFlag = "1";
        return;
    }
    if( cFlag == "3"||cFlag == "6" )        // 个人投保单明细查询
    {
        mCurOperateFlag = "2";
        mGrpFlag = "0";
        return;
    }
    if( cFlag == "4" )      // 集体下个人投保单明细查询
    {
        mCurOperateFlag = "2";
        mGrpFlag = "1";
        return;
    }
    if( cFlag == "5"||cFlag=="25" )     // 个人投保单复核查询
    {
        mCurOperateFlag = "2";
        mGrpFlag = "3";
        return;
    }
    if(cFlag=="99"&&checktype=="1")
    {
        mGrpFlag = "0";
        return;
    }
    if(cFlag=="99"&&checktype=="2")
    {
        mGrpFlag = "1";
        return;
    }
}

/**
 * 获取该集体下所有险种信息
 */
function queryGrpPol(prtNo, riskCode) {
  var findFlag = false;

  //根据印刷号查找出所有的相关险种
  if (arrGrpRisk == null) {
    //var strSql = "select GrpProposalNo, RiskCode from LCGrpPol where PrtNo = '" + prtNo + "'";
    var sqlid6="NSProposalInputSql5";
	var mySql6=new SqlClass();
	mySql6.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
	mySql6.setSqlId(sqlid6);//指定使用的Sql的id
	mySql6.addSubPara(prtNo);//指定传入的参数
	var strSql=mySql6.getString();
    arrGrpRisk  = easyExecSql(strSql);

    //通过承保描述定义表找到主险
    for (i=0; i<arrGrpRisk.length; i++) {
//      strSql = "select SubRiskFlag from LMRiskApp where RiskCode = '"
//             + arrGrpRisk[i][1] + "' and RiskVer = '2002'";
      var sqlid7="NSProposalInputSql6";
  	  var mySql7=new SqlClass();
  	  mySql7.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
  	  mySql7.setSqlId(sqlid7);//指定使用的Sql的id
  	  mySql7.addSubPara(arrGrpRisk[i][1]);//指定传入的参数
  	   strSql=mySql7.getString();
      var riskDescribe = easyExecSql(strSql);

      if (riskDescribe == "M") {
        top.mainRisk = arrGrpRisk[i][1];
        break;
      }
    }
  }
  //alert(arrGrpRisk);

  //获取选择的险种和集体投保单号码
  for (i=0; i<arrGrpRisk.length; i++) {
    if (arrGrpRisk[i][1] == riskCode) {
      document.all("RiskCode").value = arrGrpRisk[i][1];
      document.all("GrpPolNo").value = arrGrpRisk[i][0];

      if (arrGrpRisk[i][1] == top.mainRisk) {
        //top.mainPolNo = "";
        mainRiskPolNo ="";
      }

      findFlag = true;
      break;
    }
  }

  if (arrGrpRisk.length > 1) {
    document.all("RiskCode").className = "code";
    document.all("RiskCode").readOnly = false;
  }
  else {
    document.all("RiskCode").onclick = "";
  }

  return findFlag;
}

/**
 * 根据身份证号码获取出生日期
 */
function grpGetBirthdayByIdno() {
  var id = document.all("IDNo").value;

  if (document.all("IDType").value == "0") {
    if (id.length == 15) {
      id = id.substring(6, 12);
      id = "19" + id;
      id = id.substring(0, 4) + "-" + id.substring(4, 6) + "-" + id.substring(6);
      document.all("Birthday").value = id;
    }
    else if (id.length == 18) {
      id = id.substring(6, 14);
      id = id.substring(0, 4) + "-" + id.substring(4, 6) + "-" + id.substring(6);
      document.all("Birthday").value = id;
    }
  }
}

/**
 * 校验主附险包含关系
 */
function checkRiskRelation(tPolNo, cRiskCode) {
  // 集体下个人投保单
  if (mGrpFlag == "1") {
//    var strSql = "select RiskCode from LCGrpPol where GrpProposalNo = '" + tPolNo
//               + "' and trim(RiskCode) in trim((select Code1 from LDCode1 where Code = '" + cRiskCode + "'))";
      var sqlid8="NSProposalInputSql7";
	  var mySql8=new SqlClass();
	  mySql8.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
	  mySql8.setSqlId(sqlid8);//指定使用的Sql的id
	  mySql8.addSubPara(tPolNo);//指定传入的参数
	  mySql8.addSubPara(cRiskCode);//指定传入的参数
	  var strSql=mySql8.getString();
    return true;
  }
  else {
//    var strSql = "select RiskCode from LCPol where PolNo = '" + tPolNo
//               + "' and trim(RiskCode) in trim((select Code1 from LDCode1 where Code = '" + cRiskCode + "'))";
	  var sqlid9="NSProposalInputSql8";
	  var mySql9=new SqlClass();
	  mySql9.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
	  mySql9.setSqlId(sqlid9);//指定使用的Sql的id
	  mySql9.addSubPara(tPolNo);//指定传入的参数
	  mySql9.addSubPara(cRiskCode);//指定传入的参数
	  var strSql=mySql9.getString();
  }

  return easyQueryVer3(strSql);
}

/**
 * 出错返回到险种选择界面
 */
function gotoInputPage() {
  // 集体下个人投保单
  if (mGrpFlag == "1") {
    //top.fraInterface.window.location = "../app/ProposalGrpInput.jsp?LoadFlag=" + LoadFlag;
    top.fraInterface.window.location = "../app/ProposalInput.jsp?LoadFlag=" + LoadFlag;
  }
  // 个人有扫描件投保单
  else if (typeof(prtNo)!="undefined" && typeof(type)=="undefined") {
    top.fraInterface.window.location = "../app/ProposalInput.jsp?prtNo=" + prtNo;
  }
  // 个人无扫描件投保单
  else {
    top.fraInterface.window.location = "../app/ProposalOnlyInput.jsp?type=noScan";
  }
}

function showSaleChnl() {
  showCodeList('SaleChnl',[this]);
}
<!--add by yaory-2005-7-11-->
function addRisk()
{
//alert(fm.RiskCode.value);
 parent.fraInterface.window.location.href="ProposalInput.jsp?LoadFlag=1&ContType=1&scantype=null&MissionID=00000000000000008139&riskcode="+fm.RiskCode.value;
 return;
}
function addAppRisk()
{
alert("ok");
}
<!--end add-->
/*********************************************************************
 *  根据不同的险种,读取不同的代码
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
var isReturnFromRiskPage = false;

function getRiskInput(cRiskCode, cFlag) {

    var tPolNo = "";
  //alert("cFlag=="+cFlag);
    convertFlag(cFlag);
  //alert("mGrpFlag=="+mGrpFlag);

    var urlStr = "";
    // 首次进入集体下个人录入
//var rSql = "select risksortvalue from lmrisksort where risksorttype='00' and riskcode='"+cRiskCode+"'";
var sqlid10="NSProposalInputSql9";
var mySql10=new SqlClass();
mySql10.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
mySql10.setSqlId(sqlid10);//指定使用的Sql的id
mySql10.addSubPara(cRiskCode);//指定传入的参数
var rSql=mySql10.getString();
var RiskInterface = easyExecSql(rSql);
var tFileUrl;
//alert(RiskInterface+ ".jsp?riskcode="+ cRiskCode);
    if( mGrpFlag == "0" )       // 个人投保单
    {
        urlStr = "../riskinput/Risk" + RiskInterface+ ".jsp?riskcode="+ cRiskCode;
        tFileUrl="../riskinput/Risk" + RiskInterface+ ".jsp";
      }
    if( mGrpFlag == "1" )       // 集体下个人投保单
    {
        urlStr = "../riskgrp/Risk" + RiskInterface+ ".jsp?riskcode="+ cRiskCode;
        tFileUrl="../riskgrp/Risk" + RiskInterface+ ".jsp";
      }
    if( mGrpFlag == "3" )       // 个人投保单复核
    {
        urlStr = "../riskinput/Risk" + RiskInterface+ ".jsp?riskcode="+ cRiskCode;
        tFileUrl="../riskinput/Risk" + RiskInterface+ ".jsp";
      }

   //是否查找到指定的险种输入界面文件

  // if(!ReportFileStatus(tFileUrl))
  // {
  // 	if(mGrpFlag=="3" || mGrpFlag=="0")
  // 	{
  // 	   urlStr = "../riskinput/Risk000000.jsp?riskcode="+ cRiskCode;
  // 	}else
  // 		{
  // 			urlStr = "../riskgrp/Risk000000.jsp?riskcode="+ cRiskCode;
  // 		}
  // }
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    //读取险种的界面描述
    //showInfo = window.showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;dialogTop:-800;dialogLeft:-800;resizable=1");
	var name='提示';   //网页名称，可为空; 
	var iWidth=1;      //弹出窗口的宽度; 
	var iHeight=1;     //弹出窗口的高度; 
	var iTop = 1; //获得窗口的垂直位置 
	var iLeft = 1;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	if(isReturnFromRiskPage)
		   returnFromRiskInput();
	   return;
}

function returnFromRiskInput(cRiskCode){
    //按照不同的LoadFlag进行不同的处理
    //得到界面的调用位置,默认为1,表示个人保单直接录入.
    /**********************************************
     *LoadFlag=1 -- 个人投保单直接录入
     *LoadFlag=2 -- 集体下个人投保单录入
     *LoadFlag=3 -- 个人投保单明细查询
     *LoadFlag=4 -- 集体下个人投保单明细查询
     *LoadFlag=5 -- 复核
     *LoadFlag=6 -- 查询
     *LoadFlag=7 -- 保全新保加人
     *LoadFlag=8 -- 保全新增附加险
     *LoadFlag=9 -- 无名单补名单
     *LoadFlag=10-- 浮动费率
     *LoadFlag=13-- 集体下投保单复核修改
     *LoadFlag=16-- 集体下投保单查询
     *LoadFlag=25-- 人工核保修改投保单
     *LoadFlag=99-- 随动定制
     *
     ************************************************/
//  alert("LoadFlag=="+LoadFlag);
  if(LoadFlag=="1"){
  //alert(cRiskCode);
  ////校验是不是主险 add by yaory
//  strSql = "select subriskflag from lmriskapp where riskcode='"+cRiskCode+"'";
//    var mark = easyExecSql(strSql);
//    //alert(mark);

  ////end
    showDiv(inputButton, "true");
    showDiv(divInputContButton, "true"); //rollback by yaory
//    if(mark=='S'){
//    showDiv(divInputYaory2Button, "true");}
//    if(mark=='M'){
//    showDiv(divInputYaoryButton, "true");}
//   ///////////add by yaory for query how many records if 0 then button-ADDapp is unsee.
//   strSql = "select * from lmriskrela where riskcode='"+cRiskCode+"'";
//    var queryAppRisk = easyExecSql(strSql);
//    //alert(queryAppRisk);
//    if(queryAppRisk==null)
//    {
//    fm.riskbutton31.style.display='none';
//    fm.riskbutton32.style.display='none';
//    }

  }


    if (LoadFlag == "2"){
        var aGrpContNo=parent.VD.gVSwitch.getVar( "GrpContNo" );
        if(isSubRisk(cRiskCode)){
          document.all('MainPolNo').value=mainRiskPolNo;
        }
        //strSql = "select PayIntv from LCGrpPol where RiskCode = '" + cRiskCode + "' and GrpContNo ='"+aGrpContNo+"'";
        var sqlid11="NSProposalInputSql10";
        var mySql11=new SqlClass();
        mySql11.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
        mySql11.setSqlId(sqlid11);//指定使用的Sql的id
        mySql11.addSubPara(cRiskCode);//指定传入的参数
        mySql11.addSubPara(aGrpContNo);//指定传入的参数
         strSql=mySql11.getString();
    var PayIntv = easyExecSql(strSql);
    try{
      fm.PayIntv.value=PayIntv;
    }
    catch(ex){
    }
    showDiv(inputButton, "true");
//    showDiv(divInputContButton, "false");
    showDiv(divGrpContButton, "true");
    showDiv(inputQuest, "false");

    }


    if (LoadFlag == "3") {
      document.all("SaleChnl").readOnly = false;
    document.all("SaleChnl").className = "code";
    document.all("SaleChnl").ondblclick= showSaleChnl;
    showDiv(inputButton, "true");
    divApproveModifyContButton.style.display="";
    }

  if(LoadFlag=="4"){
    showDiv(inputQuest, "true");
  }
  if(LoadFlag=="5"){
    showDiv(inputQuest, "true");
  }

  if(LoadFlag=="6"){
    //showDiv(inputButton, "true");
    //showDiv(divInputContButton, "true");
  }

  if(LoadFlag=="7"){
    showDiv(inputButton, "true");
    showDiv(divInputContButton, "true");
  }

  if(LoadFlag=="8"){
    showDiv(inputButton, "true");
    showDiv(divInputContButtonBQBack, "true");
    
    var rRiskName = "select riskname from lmrisk where  riskcode='"+cRiskCode+"'";
    var RiskName = easyExecSql(rRiskName);
    if(RiskName==null)
    {
   	  alert("查询险种名称失败");
   	  return false;
    }else
   	{
   		fm.RiskCodeName.value=RiskName[0][0];  		
   	}
    showDiv(DivLCSpec, "false");
    
    var aContNo = top.opener.document.all('ContNo').value
    document.all("RiskCode").CodeData = getRiskCodeNS(aContNo);
    var riskBtn = document.getElementById("RiskCode");
    riskBtn.ondblclick = function(){
    	showCodeListEx('RiskCode',[this,'RiskCodeName'],[0,1]);
    }
    riskBtn.onkeyup = function(){
    	showCodeListKeyEx('RiskCode',[this,'RiskCodeName'],[0,1]);
    }
    //showDiv(DivLCBnf, "false");
  }

  if(LoadFlag=="9"){
    showDiv(inputButton, "true");
    showDiv(divInputContButton, "true");
  }

  if(LoadFlag=="10"){
    showDiv(inputButton, "true");
    showDiv(divInputContButton, "true");
  }

  if(LoadFlag=="13"){
    showDiv(inputButton, "true");
    showDiv(divInputContButton, "true");
  }

  if(LoadFlag=="14"){
    showDiv(inputButton, "true");
    showDiv(divInputContButton, "true");
 }

  if(LoadFlag=="16"){
    showDiv(inputButton, "true");
    showDiv(divInputContButton, "true");
  }

  if(LoadFlag=="23"){
    showDiv(inputButton, "true");
    showDiv(divInputContButton, "true");
  }

  if(LoadFlag=="25"){
    showDiv(inputButton, "true");
    showDiv(divUWContButton, "true");
  }

    if (LoadFlag == "99")
    {
    showDiv(inputButton, "false");
    showDiv(inputQuest, "false");
        showDiv(autoMoveButton, "true");
    }

  try {
    //出始化公共信息
      emptyForm();

    //根据需求单放开销售渠道只读的限制，by Minim at 2003-11-24
    document.all("SaleChnl").readOnly = false;
    document.all("SaleChnl").className = "code";
    document.all("SaleChnl").ondblclick= showSaleChnl;

    //无扫描件录入
    if (typeof(type)!="undefined" && type=="noScan") {
//      document.all("PrtNo").readOnly = false;
//      document.all("PrtNo").className = "common";

      //通过承保描述定义表找到主险
//      strSql = "select SubRiskFlag from LMRiskApp where RiskCode = '"
//             + cRiskCode + "' and RiskVer = '2002'";
      
      var sqlid12="NSProposalInputSql11";
      var mySql12=new SqlClass();
      mySql12.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
      mySql12.setSqlId(sqlid12);//指定使用的Sql的id
      mySql12.addSubPara(cRiskCode);//指定传入的参数
      strSql2=mySql12.getString();
      var riskDescribe = easyExecSql(strSql);

//      if (riskDescribe == "M") {
//        top.mainPolNo = "";
//      }

    }
    if(scantype=="scan") {
      //document.all("PrtNo").value = prtNo;
      document.all("RiskCode").value=cRiskCode;
      setFocus();
    }
    getContInput();
    getContInputnew();
  }
  catch(e){}

    //传入险种和印刷号信息
    document.all("RiskCode").value = cRiskCode;
        //alert(fm.RiskCode.value);
    //将焦点移动到印刷号，以方便随动录入
    //document.all("PrtNo").focus();

  //初始化界面的销售渠道
  try {
    //alert("prtNo=="+prtNo);
    prtNo = document.all("PrtNo").value;
    //MS的渠道取法以保单为主
    
//     var  strSaleSql = "select salechnl from lccont  where contno = '"
//             + fm.ContNo.value + "'";
     var sqlid13="NSProposalInputSql12";
     var mySql13=new SqlClass();
     mySql13.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
     mySql13.setSqlId(sqlid13);//指定使用的Sql的id
     mySql13.addSubPara(fm.ContNo.value);//指定传入的参数
     var strSaleSql=mySql13.getString();
      var SaleChnl = easyExecSql(strSaleSql);
    if(SaleChnl==null || SaleChnl =='')
    {
    	  alert("查询保单的销售渠道失败");
        return false;
    	}  
    document.all("SaleChnl").value = SaleChnl;
    //alert(document.all("SaleChnl").value )
    
   // var riskType = prtNo.substring(2, 4);
   // if (riskType == "11") {
   //     document.all("SaleChnl").value = "02";
   // }
   // else if (riskType == "12") {
   //     document.all("SaleChnl").value = "01";
   // }
   // else if (riskType == "15") {
   //     document.all("SaleChnl").value = "03";
   // }
   // else if (riskType == "16") {
   //   document.all("SaleChnl").value = "02";
   //   document.all("SaleChnl").readOnly = false;
   //   document.all("SaleChnl").className = "code";
   //   document.all("SaleChnl").ondblclick= showSaleChnl;
   // }
  }
  catch(e) {}

//  if (!(typeof(top.type)!="undefined" && (top.type=="ChangePlan" || top.type=="SubChangePlan"))) {
//    //将是否指定生效日期在录入时失效
//    document.all("SpecifyValiDate").readOnly = true;
//    document.all("SpecifyValiDate").className = "readOnly";
//    document.all("SpecifyValiDate").ondblclick = "";
//    document.all("SpecifyValiDate").onkeyup = "";
//    //document.all("SpecifyValiDate").value = "N";
//    document.all("SpecifyValiDate").tabIndex = -1;
//  }

  //alert("mCurOperateFlag:"+mCurOperateFlag);
  if(mainRiskPolNo==""){
    mainRiskPolNo=parent.VD.gVSwitch.getVar("mainRiskPolNo");
  }

  //alert("main 5:"+mCurOperateFlag);
  if( mCurOperateFlag == "1" ) {             // 录入
        // 集体下个人投保单
        //alert("mGrpFlag:"+mGrpFlag);
        if( mGrpFlag == "1" )   {
            getGrpPolInfo();                       // 带入集体部分信息
            getPayRuleInfo();

            //支持集体下个人，录入身份证号带出出生日期
            document.all("IDNo").onblur = grpGetBirthdayByIdno;
            //暂时不去掉去掉问题件按钮,随动定制时例外   hl
//          if(LoadFlag!="99")
//          inputQuest.style.display = "none";

            // 获取该集体下所有险种信息
            //alert("judging if the RiskCode input has been input in group info.");
            //if (!queryGrpPol(document.all("PrtNo").value, cRiskCode))   {
            //  alert("集体团单没有录入这个险种，集体下个人不允许录入！");
            //  document.all("RiskCode").value="";
            //  //alert("now go to the new location- ProposalGrpInput.jsp");
            //  top.fraInterface.window.location = "./ProposalGrpInput.jsp?LoadFlag=" + LoadFlag;
            //  //alert("top.location has been altered");
            //  return false; //hezy
            //}
        }
        //if(initDealForSpecRisk(cRiskCode)==false)//特殊险种在初始化时的特殊处理-houzm添加
        //{
            //alert("险种："+cRiskCode+"初始化时特殊处理失败！");
            //return false;
        //}



        //特殊险种在初始化时的特殊处理扩展-guoxiang add at 2004-9-6 16:33
    if(initDealForSpecRiskEx(cRiskCode)==false)
        {
            alert("险种："+cRiskCode+"初始化时特殊处理失败！");
            return false;
        }

        //alert("getRiskInput.isSubRisk begin...");
        //alert("cRiskCode:"+cRiskCode);
        try{
          document.all('SelPolNo').value=parent.VD.gVSwitch.getVar("PolNo");
          //alert("kjlkdsjal");
          if (document.all('SelPolNo').value=='false')
          {
            document.all('SelPolNo').value='';
          }
          if(document.all('SelPolNo').value!=''){ //更换险种
           // var tSql="select riskcode from lcpol where polno='"+document.all('SelPolNo').value+"'";
            var sqlid14="NSProposalInputSql13";
            var mySql14=new SqlClass();
            mySql14.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
            mySql14.setSqlId(sqlid14);//指定使用的Sql的id
            mySql14.addSubPara(document.all('SelPolNo').value);//指定传入的参数
            var tSql=mySql14.getString();
            var arr=easyExecSql(tSql);            
            if(arr[0]!=cRiskCode){
              document.all('SelPolNo').value='';
            }
          }
      }
      catch(ex) {}
        //alert("selPolNo:"+document.all('SelPolNo').value);
        if( isSubRisk( cRiskCode ) == true&&document.all('SelPolNo').value=="" ) {   // 附险
          //如果是新增附加险，则不清空缓存的主险保单号
          if (LoadFlag != "8" && LoadFlag != "2") {
              //top.mainPolNo = ""; //hezy add
              mainRiskPolNo = "";
            }
            tPolNo = getMainRiskNo(cRiskCode);   //弹出录入附险的窗口,得到主险保单号码
            //alert("tPolNo:"+tPolNo);
            /*if (!checkRiskRelation(tPolNo, cRiskCode)) {
              alert("主附险包含关系错误，输入的主险号不能带这个附加险！");
              gotoInputPage();
              //top.mainPolNo = "";
              mainRiskPolNo = "";
              return false;
            }*/
//-----------------------------------------------------------------------
         if(cRiskCode=='121301'||cRiskCode=='321601')//出始化特殊的附险信息--houzm添加--可单独提出为一个函数
         {
                if(cRiskCode=='121301')
                {
                                if (!initPrivateRiskInfo121301(tPolNo))
                                {
                                  gotoInputPage();
                                  return false;
                                }
                        }
                        if(cRiskCode=='321601')
                {
                                if (!initPrivateRiskInfo321601(tPolNo))
                                {
                                  gotoInputPage();
                                  return false;
                                }
                        }
         }
         else
         {
                //出始化附险信息
                        if (!initPrivateRiskInfo(tPolNo))
                        {
                          gotoInputPage();
                          return false;
                        }
         }

//          try {}  catch(ex1) { alert( "初始化险种出错" + ex1 );   }
        } // end of 附险if

      //alert("ManageCom=="+ManageCom);
       //return false;

    } // end of 录入if


    mCurOperateFlag = "";
    mGrpFlag = "";
    
    // 回调查询页面ProposalQueryDetail.jsp进行赋值，无查询赋值时捕获异常忽略
    try{parent.fraTitle.setValue();}catch(e){}
    return;
}

/*********************************************************************
 *  判断该险种是否是附险,在这里确定既可以做主险,又可以做附险的代码
 *  参数  ：  险种代码
 *  返回值：  无
 *********************************************************************
 */
function isSubRisk(cRiskCode) {
  //alert(cRiskCode);
  if (cRiskCode=="")
  {
   return false;
  }
  var arrQueryResult = easyExecSql("select SubRiskFlag from LMRiskApp where RiskCode='" + cRiskCode + "'", 1, 0);

    if(arrQueryResult[0] == "S")    //需要转成大写
        return true;
    if(arrQueryResult[0] == "M")
        return false;

    if (arrQueryResult[0].toUpperCase() == "A")
    {
        if (confirm("该险种既可以是主险,又可以是附险!选择确定进入主险录入,选择取消进入附险录入"))
            return false;
        else
            return true;
  }
    return false;
}

/*********************************************************************
 *  弹出录入附险的窗口,得到主险保单号码
 *  参数  ：  险种代码
 *  返回值：  无
 *********************************************************************
 */
function getMainRiskNo(cRiskCode) {
    var urlStr = "../app/MainRiskNoInput.jsp";
    var tPolNo="";
  //alert("mainRiskPolNo:"+mainRiskPolNo);
  if (typeof(mainRiskPolNo)!="undefined" && mainRiskPolNo!="") {
    //tPolNo = top.mainPolNo;
    tPolNo = mainRiskPolNo;
  }
  else{
    //tPolNo = window.showModalDialog(urlStr,tPolNo,"status:no;help:0;close:0;dialogWidth:310px;dialogHeight:100px;center:yes;");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	tPolNo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	tPolNo.focus();
    //top.mainPolNo = tPolNo;
    mainRiskPolNo = tPolNo;
  }

    return tPolNo;
}

/*********************************************************************
 *  初始化附险信息
 *  参数  ：  投保单号
 *  返回值：  无
 *********************************************************************
 */
function initPrivateRiskInfo(cPolNo) {
    if(cPolNo=="") {
        alert("没有主险保单号,不能进行附加险录入!");
        mCurOperateFlag="";        //清空当前操作类型,使得不能进行当前操作.
        return false
    }

    var arrLCPol = new Array();
    var arrQueryResult = null;
    // 主保单信息部分
//    var sql = "select * from lcpol where polno='" + cPolNo + "' "
//            + "and riskcode in "
//            + "( select riskcode from LMRiskApp where SubRiskFlag = 'M' )";
    var sqlid15="NSProposalInputSql14";
    var mySql15=new SqlClass();
    mySql15.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
    mySql15.setSqlId(sqlid15);//指定使用的Sql的id
    mySql15.addSubPara(cPolNo);//指定传入的参数
    var sql=mySql15.getString(); 

    arrQueryResult = easyExecSql( sql , 1, 0);

    if (arrQueryResult == null) {
        mCurOperateFlag="";        //清空当前操作类型,使得不能进行当前操作.

        //top.mainPolNo = "";
        mainRiskPolNo = "";

        alert("读取主险信息失败,不能进行附加险录入!");
        return false
    }

    arrLCPol = arrQueryResult[0];
    displayPol( arrLCPol );

    document.all("MainPolNo").value = cPolNo;
    var tAR;

    //投保人信息
    if (arrLCPol[6]=="2") {     //集体投保人信息
      arrQueryResult = null;
      //arrQueryResult = easyExecSql("select * from lcappntgrp where polno='"+cPolNo+"'"+" and grpno='"+arrLCPol[28]+"'", 1, 0);
      arrQueryResult = easyExecSql("select * from lcgrpappnt where grpcontno='"+arrLCPol[0]+"'"+" and customerno='"+arrLCPol[28]+"'", 1, 0);
      tAR = arrQueryResult[0];
      displayPolAppntGrp(tAR);
    } else {                     //个人投保人信息
     /* arrQueryResult = null;
      arrQueryResult = easyExecSql("select * from lcappntind where polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[28]+"'", 1, 0);
      tAR = arrQueryResult[0];
      displayPolAppnt(tAR);*/
    }

    // 被保人信息部分
    if (arrLCPol[21] == arrLCPol[28]) {
      document.all("SamePersonFlag").checked = true;
        parent.fraInterface.isSamePersonQuery();
    parent.fraInterface.document.all("CustomerNo").value = arrLCPol[21];
    }
    //else {
        arrQueryResult = null;
        arrQueryResult = easyExecSql("select * from lcinsured where contno='"+arrLCPol[2]+"'"+" and insuredno='"+arrLCPol[21]+"'", 1, 0);
        tAR = arrQueryResult[0];
        displayPolInsured(tAR);
    //}
    return true;
}

/*********************************************************************
 *  校验投保单的输入
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function verifyProposal() {
  var passVerify = true;

  //保全新增附加险
  if (LoadFlag == "8") {
    var newCValidate = document.all('CValiDate').value;
    /*
    if (oldCValidate.substring(4) != newCValidate.substring(4)) {
      alert("指定的生效日期必须是主险生效日期的周年对应日");
      return false;
    }*/

    //正常的新增附加险不能这么限制
    //if (oldCValidate == newCValidate) {
    //  alert("根据新增附加险规则，不能指定为主险生效日期");
    //  return false;
    //}

    //if (!confirm("指定的附加险生效日期为：(" + newCValidate + ")，确定吗？")) {
    //  return false;
    //}
  }

  if(fm.AppntRelationToInsured.value=="00"){
    if(fm.AppntCustomerNo.value!= fm.CustomerNo.value){
      alert("投保人与被保人关系为本人，但客户号不一致");
      return false;
    }
  }
   if(needVerifyRiskcode()==true)
   {
        //if(verifyInput() == false) passVerify = false;

        BnfGrid.delBlankLine();

        if(BnfGrid.checkValue("BnfGrid") == false) passVerify = false;

         //校验单证是否发放给业务员
         if (!verifyPrtNo(document.all("PrtNo").value)) passVerify = false;
    }
    try {
      var strChkIdNo = "";

          //以年龄和性别校验身份证号
          if (document.all("AppntIDType").value == "0")
            strChkIdNo = chkIdNo(document.all("AppntIDNo").value, document.all("AppntBirthday").value, document.all("AppntSex").value);
          if (document.all("IDType").value == "0")
            strChkIdNo = chkIdNo(document.all("IDNo").value, document.all("Birthday").value, document.all("Sex").value);

          if (strChkIdNo != "") {
            alert(strChkIdNo);
            passVerify = false;


      }

      //校验职业和职业代码
//    var arrCode = new Array();
//    arrCode = verifyCode("职业（工种）", document.all("AppntWorkType").value, "code:OccupationCode", 1);
//    if (arrCode!=true && document.all("AppntOccupationCode").value!=arrCode[0]) {
//      alert("投保人职业和职业代码不匹配！");
//      passVerify = false;
//    }
//    arrCode = verifyCode("职业（工种）", document.all("WorkType").value, "code:OccupationCode", 1);
//    if (arrCode!=true && document.all("OccupationCode").value!=arrCode[0]) {
//      alert("被保人职业和职业代码不匹配！");
//      passVerify = false;
//    }

      //校验受益比例
      var i;
      var sumLiveBnf = new Array();
      var sumDeadBnf = new Array();
      for (i=0; i<BnfGrid.mulLineCount; i++)
      {
        if (BnfGrid.getRowColData(i, 8)==null||BnfGrid.getRowColData(i, 7)=='')
        {
            BnfGrid.setRowColData(i, 8,"1");
        }
        if (BnfGrid.getRowColData(i, 7)==null||BnfGrid.getRowColData(i, 7)=='')
        {
            BnfGrid.setRowColData(i, 7,"1");
        }
        if (BnfGrid.getRowColData(i, 1) == "0")
        {
          if (typeof(sumLiveBnf[parseInt(BnfGrid.getRowColData(i, 7))]) == "undefined")
            sumLiveBnf[parseInt(BnfGrid.getRowColData(i, 7))] = 0;
          sumLiveBnf[parseInt(BnfGrid.getRowColData(i, 7))] = sumLiveBnf[parseInt(BnfGrid.getRowColData(i, 7))] + parseFloat(BnfGrid.getRowColData(i, 8));
        }
        else if (BnfGrid.getRowColData(i, 1) == "1")
        {
          if (typeof(sumDeadBnf[parseInt(BnfGrid.getRowColData(i, 7))]) == "undefined")
            sumDeadBnf[parseInt(BnfGrid.getRowColData(i, 7))] = 0;
          sumDeadBnf[parseInt(BnfGrid.getRowColData(i, 7))] = sumDeadBnf[parseInt(BnfGrid.getRowColData(i, 7))] + parseFloat(BnfGrid.getRowColData(i, 8));
        }

      }

      for (i=0; i<sumLiveBnf.length; i++)
      {
        if (typeof(sumLiveBnf[i])!="undefined" && sumLiveBnf[i]>1)
        {
            alert("生存受益人受益顺序 " + i + " 的受益比例和为：" + sumLiveBnf[i]+ " 。大于100%，不能提交！");
            passVerify = false;
        }
        else if (typeof(sumLiveBnf[i])!="undefined" && sumLiveBnf[i]<1)
        {
            alert("注意：生存受益人受益顺序 " + i + " 的受益比例和为：" + sumLiveBnf[i] + " 。小于100%");
            passVerify = false;
        }
      }

      for (i=0; i<sumDeadBnf.length; i++)
      {
        if (typeof(sumDeadBnf[i])!="undefined" && sumDeadBnf[i]>1)
        {
          alert("死亡受益人受益顺序 " + i + " 的受益比例和为：" + sumDeadBnf[i] + " 。大于100%，不能提交！");
          passVerify = false;
        }
        else if (typeof(sumDeadBnf[i])!="undefined" && sumDeadBnf[i]<1)
        {
            alert("注意：死亡受益人受益顺序 " + i + " 的受益比例和为：" + sumDeadBnf[i] + " 。小于100%");
            passVerify = false;
        }
      }


      if (trim(fm.BankCode.value)=="0101")
      {
        if (trim(fm.BankAccNo.value).length!=19 || !isInteger(trim(fm.BankAccNo.value)))
        {
            alert("工商银行的账号必须是19位的数字，最后一个星号（*）不要！\n如果客户填写无误，请一定发问题件！");
            passVerify = false;
        }
      }

    //校验客户是否死亡
    if (fm.AppntCustomerNo.value!="" && isDeath(fm.AppntCustomerNo.value)) {
      alert("投保人已经死亡！");
      passVerify = false;
    }

    if (fm.CustomerNo.value!="" && isDeath(fm.CustomerNo.value)) {
      alert("被保人已经死亡！");
      passVerify = false;
    }
    }
    catch(e) {}

    if (!passVerify) {
      if (!confirm("投保单录入可能有错误，是否继续保存？")) return false;
      else return true;
    }
}

//校验客户是否死亡
function isDeath(CustomerNo) {
  //var strSql = "select DeathDate from LDPerson where CustomerNo='" + CustomerNo + "'";
  var sqlid16="NSProposalInputSql15";
  var mySql16=new SqlClass();
  mySql16.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
  mySql16.setSqlId(sqlid16);//指定使用的Sql的id
  mySql16.addSubPara(CustomerNo);//指定传入的参数
  var strSql=mySql16.getString();
  var arrResult = easyExecSql(strSql);

  if (arrResult == ""||arrResult == null) return false;
  else return true;
}

/*********************************************************************
 *  保存个人投保单的提交
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function submitForm() {

  if(!chkDutyNo()){                  //校验CheckBox：只允许同时选中一个责任项
                                                                             //create by malong 2005-7-13
       return false;
    }
    if(!chkMult() || !checkMult())      //判断份数是否为空或整数 chkMult适用于多责任险种,checkMult适用于单责任险种,
                                                                            //create by malong 2005-7-8
  {
     // alert("here!");
     return false;
  }


    if(sign != 0)
    {
       alert("请不要重复点击!");
       return;
    }

    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

  var verifyGrade = "1";


  //根据特殊的险种做特殊处的理函数
  //modify by malong 2005-7-8
  try {

     if(specDealByRisk()==false)
      return;

    }
     catch(e){}

  //检验出生日期，如果空，从身份证号取
  //try {checkBirthday(); } catch(e){}

    // 校验被保人是否同投保人，相同则进行复制
  try { verifySamePerson(); } catch(e) {}

        // 校验录入数据
    if( verifyProposal() == false ) return;

    if (trim(document.all('ProposalNo').value) != "") {
      alert("该投保单号已经存在，不允许再次新增，请重新进入录入界面！");
      return false;
    }
    if(checkSameRiskCode()==false)
    {
       alert("此保单中尚有未终止的此险种，不可以进行重复新增");
       return false;
    	}
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    if (LoadFlag=="1") {
        mAction = "INSERTPERSON";
    }
    else {
        mAction = "INSERTGROUP";
    }

    document.all( 'fmAction' ).value = mAction;
    fm.action="../app/ProposalSave.jsp"

    //为保全增加，add by Minim
    if (LoadFlag=="7" || LoadFlag=="8") {
      document.all('MainPolNo').value=top.opener.document.all('PolNo').value;
      //alert(document.all('MainPolNo').value);
      fm.action="../app/ProposalSave.jsp?BQFlag=2&EdorType="+EdorType+"&LoadFlag="+LoadFlag;
      //document.all("BQFlag").value=BQFlag;
    }

    //为无名单补名单增加，add by Minim
    if (LoadFlag=="9") {
      fm.action="../app/ProposalSave.jsp?BQFlag=4&MasterPolNo=" + parent.VD.gVSwitch.getVar('MasterPolNo');
      //alert(fm.action);return;
    }
    sign = 1;
    //alert("payIntv:"+document.all('PayIntv').value);
  //beforeSubmit();
    fm.submit(); //提交
    sign = 0;
}

/**
 * 强制解除锁定
 */
function unLockTable() {
  if (fm.PrtNo.value == "") {
    alert("需要填写印刷号！");
    return;
  }

  var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + fm.PrtNo.value + "&CreatePos=承保录单&PolState=1002&Action=DELETE";
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1");
  var name='提示';   //网页名称，可为空; 
  var iWidth=0;      //弹出窗口的宽度; 
  var iHeight=0;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

}

function afterSubmit(FlagStr, content)
{
   //alert(mCurOperateFlag);
    try {
        if(showInfo!=null)
            showInfo.close();
        }
    catch(e)
  {

    }
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=F&content=" + content ;
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
        //add by yaory
//        fm.riskbutton31.disabled='';
//        fm.riskbutton32.disabled='';
        //alert("loadflag:"+LoadFlag);
    if (fm.ContType.value == "1" && cflag=="1")
    {
          if (confirm("保存成功！\n要解除该印刷号的锁定，让复核人员能操作吗？")) {
            unLockTable();
          }
      }
      else {
          try{mainRiskPolNo=parent.VD.gVSwitch.getVar("mainRiskPolNo");} catch(ex){}
          //alert("loadflag:"+LoadFlag);
          if(LoadFlag == '3'){
            inputQuestButton.disabled = false;
                    //////add by yaory
//                    fm.riskbutton31.disabled=true;
//                    fm.riskbutton32.disabled=true;
          }
        content = "保存成功！";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
      //document.all('AppendRiskCode').focus();
        //////add by yaory
//        fm.riskbutton31.focus();
//        fm.riskbutton32.focus();
      if (mAction=="CONFIRM") {
        window.location.href("./ContPolInput.jsp");
          }
    }
     if(LoadFlag == 1)
      {
        top.close();
        }
        //暂时保存主险投保单号码，方便附险的录入，重新选择扫描件后失效
        //try { if (top.mainPolNo == "") top.mainPolNo = document.all("ProposalNo").value } catch(e) {}
        //try { if (mainRiskPolNo == "") mainRiskPolNo = document.all("ProposalNo").value } catch(e) {alert("err");}
    }

    //承保计划变更，附险终止的后续处理
    if (mAction=="DELETE") {
      if (typeof(top.type)!="undefined" && top.type=="SubChangePlan") {
        var tProposalNo = document.all('ProposalNo').value;
        var tPrtNo = document.all('PrtNo').value;
        var tRiskCode = document.all('RiskCode').value;

        parent.fraTitle.window.location = "./ChangePlanSubWithdraw.jsp?polNo=" + tProposalNo + "&prtNo=" + tPrtNo + "&riskCode=" + tRiskCode;
    }

    returnparent();
    }
    mAction = "";
}

/*********************************************************************
 *  保存个人投保单的提交后的操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmita( FlagStr, content )
{
   //alert(mCurOperateFlag);
   showInfo.close();
    //try {
    //  if(showInfo!=null)
    //      alert(1);
    //  }
    //catch(e)
  //{
  //
    //}
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=F&content=" + content ;
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
        //add by yaory
//        fm.riskbutton31.disabled='';
//        fm.riskbutton32.disabled='';
        //alert("loadflag:"+LoadFlag);
    if (fm.ContType.value == "1" && cflag=="1")
    {
          if (confirm("保存成功！\n要解除该印刷号的锁定，让复核人员能操作吗？")) {
            unLockTable();
          }
      }
      else {
          try{mainRiskPolNo=parent.VD.gVSwitch.getVar("mainRiskPolNo");} catch(ex){}
          //alert("loadflag:"+LoadFlag);
          if(LoadFlag == '3'){
            inputQuestButton.disabled = false;
                    //////add by yaory
//                    fm.riskbutton31.disabled=true;
//                    fm.riskbutton32.disabled=true;
          }
        content = "保存成功！";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
      //document.all('AppendRiskCode').focus();
        //////add by yaory
//        fm.riskbutton31.focus();
//        fm.riskbutton32.focus();
      if (mAction=="CONFIRM") {
        window.location.href("./ContPolInput.jsp");
          }
    }

        //暂时保存主险投保单号码，方便附险的录入，重新选择扫描件后失效
        //try { if (top.mainPolNo == "") top.mainPolNo = document.all("ProposalNo").value } catch(e) {}
        //try { if (mainRiskPolNo == "") mainRiskPolNo = document.all("ProposalNo").value } catch(e) {alert("err");}
    }

    //承保计划变更，附险终止的后续处理
    if (mAction=="DELETE") {
      if (typeof(top.type)!="undefined" && top.type=="SubChangePlan") {
        var tProposalNo = document.all('ProposalNo').value;
        var tPrtNo = document.all('PrtNo').value;
        var tRiskCode = document.all('RiskCode').value;

        parent.fraTitle.window.location = "./ChangePlanSubWithdraw.jsp?polNo=" + tProposalNo + "&prtNo=" + tPrtNo + "&riskCode=" + tRiskCode;
    }

    returnparent();
    }
    mAction = "";
}

/*********************************************************************
 *  "重置"按钮对应操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function resetForm()
{
    try {
        //initForm();
        var tRiskCode = fm.RiskCode.value;
        var prtNo = fm.PrtNo.value;

        emptyForm();

        fm.RiskCode.value = tRiskCode;
        fm.PrtNo.value = prtNo;

        if (LoadFlag == "2") {
          getGrpPolInfo();
        }
    }
    catch(re)   {
        alert("在ProposalInput.js-->resetForm函数中发生异常:初始化界面错误!");
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
    //showDiv(operateButton,"true");
    //showDiv(inputButton,"false");
}

/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
    //if( cDebug == "1" )
        //parent.fraMain.rows = "0,0,50,82,*";
    //else
        //parent.fraMain.rows = "0,0,80,72,*";
}

/*********************************************************************
 *  Click事件，当点击增加图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function addClick()
{
    //下面增加相应的代码
    //showDiv( operateButton, "false" );
    //showDiv( inputButton, "true" );
}

/*********************************************************************
 *  Click事件，当点击“查询”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryClick() {
    if( mOperate == 0 ) {
        mOperate = 1;

        cGrpPolNo = document.all( 'GrpPolNo' ).value;
        cContNo = document.all( 'ContNo' ).value;
        window.open("./ProposalQueryMain.jsp?GrpPolNo=" + cGrpPolNo + "&ContNo=" + cContNo);
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
    //alert("ok");
    var index = BnfGrid.mulLineCount;
    var i;
    for(i=0;i<index;i++)
    {
        if (BnfGrid.getRowColData(i, 9) == "A")
        {
          if(ContType!="2")
          {

                  BnfGrid.setRowColData(i, 2, document.all("AppntName").value);
                  BnfGrid.setRowColData(i, 4, document.all("AppntIDType").value);

                  BnfGrid.setRowColData(i, 5, document.all("AppntIDNo").value);
                  BnfGrid.setRowColData(i, 6,parent.VD.gVSwitch.getVar( "RelationToAppnt"));

                  BnfGrid.setRowColData(i, 9, document.all("AppntHomeAddress").value);
                  //hl
                  //BnfGrid.setRowColData(i, 9, document.all("AppntNo").value);

          }
          else
          {
                  alert("投保人为团体，不能作为受益人！")

                  BnfGrid.setRowColData(i, 2, "");
                  BnfGrid.setRowColData(i, 4, "");
                  BnfGrid.setRowColData(i, 5, "");
                  BnfGrid.setRowColData(i, 6, "");
                  BnfGrid.setRowColData(i, 9, "");
          }
        }
       else if (BnfGrid.getRowColData(i,10)== "I")
        {

                  BnfGrid.setRowColData(i, 2, document.all("Name").value);
                  BnfGrid.setRowColData(i, 4, document.all("IDType").value);
                  BnfGrid.setRowColData(i, 5, document.all("IDNo").value);
                  BnfGrid.setRowColData(i, 6, "00");
                      BnfGrid.setRowColData(i, 9, document.all("HomeAddress").value);

                  //BnfGrid.setRowColData(i, 9, document.all("InsuredNo").value);

         }

        }
    var tProposalNo = "";
    tProposalNo = document.all( 'ProposalNo' ).value;

    if( tProposalNo == null || tProposalNo == "" )
        alert( "请先做投保单查询操作，再进行修改!" );
    else {
        // 校验录入数据
        if (document.all('DivLCInsured').style.display == "none") {
      for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {
          if (fm.elements[elementsNum].verify != null && fm.elements[elementsNum].name.indexOf("Appnt") != -1) {
            fm.elements[elementsNum].verify = "";
          }
        }
    }

        if( verifyProposal() == false ) return;
             if(chkMult()==false)  return;
        // 校验被保人是否同投保人，相同则进行复制
    try { verifySamePerson(); } catch(e) {}

        var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

        if( mAction == "" ) {
            //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
            if (LoadFlag=="1"||LoadFlag=="3"||LoadFlag=="8") {
                mAction = "UPDATEPERSON";
            }
            else {
                mAction = "UPDATEGROUP";
            }
            if(LoadFlag=="8"){
            	
                 fm.action="../app/ProposalSave.jsp?BQFlag=2&EdorType="+EdorType;
            }

            document.all( 'fmAction' ).value = mAction;
      //alert(mAction);
            //承保计划变更(保持投保单状态不变：复核状态，核保状态)
            if (typeof(window.ChangePlanSub) == "object") document.all('fmAction').value = "ChangePlan" + document.all('fmAction').value;
            //修改浮动费率(保持投保单状态不变：复核状态，核保状态,作用比承保计划变更大一项，能修改浮动费率，为权限考虑)
            if(LoadFlag=="10") document.all('fmAction').value = "ChangePlan" + document.all('fmAction').value;
            //yaoryif(LoadFlag=="3") document.all('fmAction').value = "Modify" + document.all('fmAction').value;
            //inputQuestButton.disabled = false;
            fm.submit(); //提交
        }

        try {
          if (typeof(top.opener.modifyClick) == "object") top.opener.initQuery();
        }
        catch(e) {
        }
    }
}

/*********************************************************************
 *  Click事件，当点击“删除”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function deleteClick() {
    var tProposalNo = document.all('ProposalNo').value;

    if(tProposalNo==null || tProposalNo=="") {
        alert( "请先做投保单查询操作，再进行删除!" );
    }
    else {
        var showStr = "正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

        if( mAction == "" ) {
            //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            mAction = "DELETE";
            document.all( 'fmAction' ).value = mAction;
            fm.submit(); //提交
        }
    }
}

/*********************************************************************
 *  Click事件，当点击“选择责任”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function chooseDuty()
{
    cRiskCode = fm.RiskCode.value;
    cRiskVersion = fm.RiskVersion.value

    if( cRiskCode == "" || cRiskVersion == "" )
    {
        alert( "您必须先录入险种和险种版本才能修改该投保单的责任项。" );
        return false
    }

    showInfo = window.open("./ChooseDutyInput.jsp?RiskCode="+cRiskCode+"&RiskVersion="+cRiskVersion);
    return true
}

/*********************************************************************
 *  Click事件，当点击“查询责任信息”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showDuty()
{
    //下面增加相应的代码
    cPolNo = fm.ProposalNo.value;
    if( cPolNo == "" )
    {
        alert( "您必须先保存投保单才能查看该投保单的责任项。" );
        return false
    }

    var showStr = "正在查询数据，请您稍候......";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //showModalDialog( "./ProposalDuty.jsp?PolNo="+cPolNo,window,"status:no;help:0;close:0;dialogWidth=18cm;dialogHeight=14cm");
	var name='提示';   //网页名称，可为空; 
	var iWidth=18+"cm";      //弹出窗口的宽度; 
	var iHeight=14+"cm";     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo1 = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo1.focus();
    showInfo.close();
}

/*********************************************************************
 *  Click事件，当点击“关联暂交费信息”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showFee()
{
    cPolNo = fm.ProposalNo.value;
    var prtNo = fm.PrtNo.value;

    if( cPolNo == "" )
    {
        alert( "您必须先保存投保单才能进入暂交费信息部分。" );
        return false
    }

    showInfo = window.open( "./ProposalFee.jsp?PolNo=" + cPolNo + "&polType=PROPOSAL&prtNo=" + prtNo );
}

/*********************************************************************
 *  Click事件，当双击“投保人客户号”录入框时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showAppnt()
{
    if( mOperate == 0 )
    {
        mOperate = 2;
        showInfo = window.open( "../sys/LDPersonMain.html" );
    }
}

/*********************************************************************
 *  Click事件，当双击“被保人客户号”录入框时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showInsured()
{
    if( mOperate == 0 )
    {
        mOperate = 3;
        showInfo = window.open( "../sys/LDPersonMain.html" );
    }
}

/*********************************************************************
 *  Click事件，当双击“连带被保人客户号”录入框时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubInsured( span, arrPara )
{
    if( mOperate == 0 )
    {
        mOperate = 4;
        spanObj = span;
        showInfo = window.open( "../sys/LDPersonMain.html" );
    }
}

/*********************************************************************
 *  把数组中的数据显示到投保人部分
 *  参数  ：  个人客户的信息
 *  返回值：  无
 *********************************************************************
 */
function displayPol(cArr)
{
    try
    {
    /*
        try { document.all('PrtNo').value = cArr[6]; } catch(ex) { };
        try { document.all('ManageCom').value = cArr[12]; } catch(ex) { };
        try { document.all('SaleChnl').value = cArr[15]; } catch(ex) { };
        try { document.all('AgentCom').value = cArr[13]; } catch(ex) { };
        try { document.all('AgentType').value = cArr[14]; } catch(ex) { };
        try { document.all('AgentCode').value = cArr[87]; } catch(ex) { };
        try { document.all('AgentGroup').value = cArr[88]; } catch(ex) { };
        //try { document.all('Handler').value = cArr[82]; } catch(ex) { };
        //try { document.all('AgentCode1').value = cArr[89]; } catch(ex) { };
        try { document.all('Remark').value = cArr[90]; } catch(ex) { };

        try { document.all('ContNo').value = cArr[1]; } catch(ex) { };

        //try { document.all('Amnt').value = cArr[43]; } catch(ex) { };
        try { document.all('CValiDate').value = cArr[29]; } catch(ex) { };
        try { document.all('PolApplyDate').value = cArr[128]; } catch(ex) { };
        try { document.all('HealthCheckFlag').value = cArr[72]; } catch(ex) { };
        try { document.all('OutPayFlag').value = cArr[97]; } catch(ex) { };
        try { document.all('PayLocation').value = cArr[59]; } catch(ex) { };
        try { document.all('BankCode').value = cArr[102]; } catch(ex) { };
        try { document.all('BankAccNo').value = cArr[103]; } catch(ex) { };
        try { document.all('AccName').value = cArr[118]; } catch(ex) { };
        try { document.all('LiveGetMode').value = cArr[98]; } catch(ex) { };
        try { document.all('BonusGetMode').value = cArr[100]; } catch(ex) { };
        try { document.all('AutoPayFlag').value = cArr[65]; } catch(ex) { };
        try { document.all('InterestDifFlag').value = cArr[66]; } catch(ex) { };

        try { document.all('InsuYear').value = cArr[111]; } catch(ex) { };
        try { document.all('InsuYearFlag').value = cArr[110]; } catch(ex) { };
        try { document.all('PolTypeFlag').value = cArr[69]; } catch(ex) { };
        try { document.all('InsuredPeoples').value = cArr[24]; } catch(ex) { };
        try { document.all('InsuredAppAge').value = cArr[22]; } catch(ex) { };


        try { document.all('StandbyFlag1').value = cArr[78]; } catch(ex) { };
        try { document.all('StandbyFlag2').value = cArr[79]; } catch(ex) { };
        try { document.all('StandbyFlag3').value = cArr[80]; } catch(ex) { };
    */
        try { document.all('PrtNo').value = cArr[5]; } catch(ex) { };
        try { document.all('ManageCom').value = cArr[13]; } catch(ex) { };
        try { document.all('SaleChnl').value = cArr[19]; } catch(ex) { };
        try { document.all('AgentCom').value = cArr[14]; } catch(ex) { };
        try { document.all('AgentType').value = cArr[15]; } catch(ex) { };
        try { document.all('AgentCode').value = cArr[16]; } catch(ex) { };
        try { document.all('AgentGroup').value = cArr[17]; } catch(ex) { };
        try { document.all('Handler').value = cArr[20]; } catch(ex) { };
        try { document.all('AgentCode1').value = cArr[18]; } catch(ex) { };
        try { document.all('Remark').value = cArr[92]; } catch(ex) { };

        try { document.all('ContNo').value = cArr[2]; } catch(ex) { };

        try { document.all('CValiDate').value = cArr[30]; } catch(ex) { };
        try { document.all('PolApplyDate').value = cArr[101]; } catch(ex) { };
        try { document.all('HealthCheckFlag').value = cArr[81]; } catch(ex) { };
        //try { document.all('OutPayFlag').value = cArr[97]; } catch(ex) { };
        try { document.all('PayLocation').value = cArr[51]; } catch(ex) { };
        //try { document.all('BankCode').value = cArr[102]; } catch(ex) { };
        //try { document.all('BankAccNo').value = cArr[103]; } catch(ex) { };
        //try { document.all('AccName').value = cArr[118]; } catch(ex) { };
        try { document.all('LiveGetMode').value = cArr[86]; } catch(ex) { };
        try { document.all('BonusGetMode').value = cArr[88]; } catch(ex) { };
        try { document.all('AutoPayFlag').value = cArr[77]; } catch(ex) { };
        try { document.all('InterestDifFlag').value = cArr[78]; } catch(ex) { };

        try { document.all('InsuYear').value = cArr[45]; } catch(ex) { };
        try { document.all('InsuYearFlag').value = cArr[44]; } catch(ex) { };
        try { document.all('PolTypeFlag').value = cArr[7]; } catch(ex) { };
        try { document.all('InsuredPeoples').value = cArr[26]; } catch(ex) { };
        try { document.all('InsuredAppAge').value = cArr[25]; } catch(ex) { };


        try { document.all('StandbyFlag1').value = cArr[104]; } catch(ex) { };
        try { document.all('StandbyFlag2').value = cArr[105]; } catch(ex) { };
        try { document.all('StandbyFlag3').value = cArr[106]; } catch(ex) { };

    } catch(ex) {
      //alert("displayPol err:" + ex + "\ndata is:" + cArr);
    }
}

/*********************************************************************
 *  把保单中的投保人信息显示到投保人部分
 *  参数  ：  个人客户的信息
 *  返回值：  无
 *********************************************************************
 */
function displayPolAppnt(cArr)
{
    // 从LCAppntInd表取数据
    try { document.all('AppntCustomerNo').value = cArr[1]; } catch(ex) { };
    try { document.all('AppntRelationToInsured').value = cArr[4]; } catch(ex) { };
    try { document.all('AppntPassword').value = cArr[5]; } catch(ex) { };
    try { document.all('AppntName').value = cArr[6]; } catch(ex) { };
    try { document.all('AppntSex').value = cArr[7]; } catch(ex) { };
    try { document.all('AppntBirthday').value = cArr[8]; } catch(ex) { };
    try { document.all('AppntNativePlace').value = cArr[9]; } catch(ex) { };
    try { document.all('AppntNationality').value = cArr[10]; } catch(ex) { };
    try { document.all('AppntMarriage').value = cArr[11]; } catch(ex) { };
    try { document.all('AppntMarriageDate').value = cArr[12]; } catch(ex) { };
    try { document.all('AppntOccupationType').value = cArr[13]; } catch(ex) { };
    try { document.all('AppntStartWorkDate').value = cArr[14]; } catch(ex) { };
    try { document.all('AppntSalary').value = cArr[15]; } catch(ex) { };
    try { document.all('AppntHealth').value = cArr[16]; } catch(ex) { };
    try { document.all('AppntStature').value = cArr[17]; } catch(ex) { };
    try { document.all('AppntAvoirdupois').value = cArr[18]; } catch(ex) { };
    try { document.all('AppntCreditGrade').value = cArr[19]; } catch(ex) { };
    try { document.all('AppntIDType').value = cArr[20]; } catch(ex) { };
    try { document.all('AppntProterty').value = cArr[21]; } catch(ex) { };
    try { document.all('AppntIDNo').value = cArr[22]; } catch(ex) { };
    try { document.all('AppntOthIDType').value = cArr[23]; } catch(ex) { };
    try { document.all('AppntOthIDNo').value = cArr[24]; } catch(ex) { };
    try { document.all('AppntICNo').value = cArr[25]; } catch(ex) { };
    try { document.all('AppntHomeAddressCode').value = cArr[26]; } catch(ex) { };
    try { document.all('AppntHomeAddress').value = cArr[27]; } catch(ex) { };
    try { document.all('AppntPostalAddress').value = cArr[28]; } catch(ex) { };
    try { document.all('AppntZipCode').value = cArr[29]; } catch(ex) { };
    try { document.all('AppntPhone').value = cArr[30]; } catch(ex) { };
    try { document.all('AppntBP').value = cArr[31]; } catch(ex) { };
    try { document.all('AppntMobile').value = cArr[32]; } catch(ex) { };
    try { document.all('AppntEMail').value = cArr[33]; } catch(ex) { };
    try { document.all('AppntJoinCompanyDate').value = cArr[34]; } catch(ex) { };
    try { document.all('AppntPosition').value = cArr[35]; } catch(ex) { };
    try { document.all('AppntGrpNo').value = cArr[36]; } catch(ex) { };
    try { document.all('AppntGrpName').value = cArr[37]; } catch(ex) { };
    try { document.all('AppntGrpPhone').value = cArr[38]; } catch(ex) { };
    try { document.all('AppntGrpAddressCode').value = cArr[39]; } catch(ex) { };
    try { document.all('AppntGrpAddress').value = cArr[40]; } catch(ex) { };
    try { document.all('AppntDeathDate').value = cArr[41]; } catch(ex) { };
    try { document.all('AppntRemark').value = cArr[42]; } catch(ex) { };
    try { document.all('AppntState').value = cArr[43]; } catch(ex) { };
    try { document.all('AppntWorkType').value = cArr[46]; } catch(ex) { };
    try { document.all('AppntPluralityType').value = cArr[47]; } catch(ex) { };
    try { document.all('AppntOccupationCode').value = cArr[48]; } catch(ex) { };
    try { document.all('AppntDegree').value = cArr[49]; } catch(ex) { };
    try { document.all('AppntGrpZipCode').value = cArr[50]; } catch(ex) { };
    try { document.all('AppntSmokeFlag').value = cArr[51]; } catch(ex) { };
    try { document.all('AppntRgtAddress').value = cArr[52]; } catch(ex) { };
    try { document.all('AppntHomeZipCode').value = cArr[53]; } catch(ex) { };
    try { document.all('AppntPhone2').value = cArr[54]; } catch(ex) { };

}

/*********************************************************************
 *  把保单中的投保人数据显示到投保人部分
 *  参数  ：  集体客户的信息
 *  返回值：  无
 *********************************************************************
 */
function displayPolAppntGrp( cArr )
{

    /*
    // 从LCAppntGrp表取数据
    try { document.all('AppntPolNo').value = cArr[0]; } catch(ex) { };
    try { document.all('AppntGrpNo').value = cArr[1]; } catch(ex) { };
    try { document.all('AppntRelationToInsured').value = cArr[2]; } catch(ex) { };
    try { document.all('AppntAppntGrade').value = cArr[3]; } catch(ex) { };
    try { document.all('AppntPassword').value = cArr[4]; } catch(ex) { };
    try { document.all('AppntGrpName').value = cArr[5]; } catch(ex) { };
    try { document.all('AppntGrpAddressCode').value = cArr[6]; } catch(ex) { };
    try { document.all('AppntGrpAddress').value = cArr[7]; } catch(ex) { };
    try { document.all('AppntGrpZipCode').value = cArr[8]; } catch(ex) { };
    try { document.all('AppntBusinessType').value = cArr[9]; } catch(ex) { };
    try { document.all('AppntGrpNature').value = cArr[10]; } catch(ex) { };
    try { document.all('AppntPeoples').value = cArr[11]; } catch(ex) { };
    try { document.all('AppntRgtMoney').value = cArr[12]; } catch(ex) { };
    try { document.all('AppntAsset').value = cArr[13]; } catch(ex) { };
    try { document.all('AppntNetProfitRate').value = cArr[14]; } catch(ex) { };
    try { document.all('AppntMainBussiness').value = cArr[15]; } catch(ex) { };
    try { document.all('AppntCorporation').value = cArr[16]; } catch(ex) { };
    try { document.all('AppntComAera').value = cArr[17]; } catch(ex) { };
    try { document.all('AppntLinkMan1').value = cArr[18]; } catch(ex) { };
    try { document.all('AppntDepartment1').value = cArr[19]; } catch(ex) { };
    try { document.all('AppntHeadShip1').value = cArr[20]; } catch(ex) { };
    try { document.all('AppntPhone1').value = cArr[21]; } catch(ex) { };
    try { document.all('AppntE_Mail1').value = cArr[22]; } catch(ex) { };
    try { document.all('AppntFax1').value = cArr[23]; } catch(ex) { };
    try { document.all('AppntLinkMan2').value = cArr[24]; } catch(ex) { };
    try { document.all('AppntDepartment2').value = cArr[25]; } catch(ex) { };
    try { document.all('AppntHeadShip2').value = cArr[26]; } catch(ex) { };
    try { document.all('AppntPhone2').value = cArr[27]; } catch(ex) { };
    try { document.all('AppntE_Mail2').value = cArr[28]; } catch(ex) { };
    try { document.all('AppntFax2').value = cArr[29]; } catch(ex) { };
    try { document.all('AppntFax').value = cArr[30]; } catch(ex) { };
    try { document.all('AppntPhone').value = cArr[31]; } catch(ex) { };
    try { document.all('AppntGetFlag').value = cArr[32]; } catch(ex) { };
    try { document.all('AppntSatrap').value = cArr[33]; } catch(ex) { };
    try { document.all('AppntEMail').value = cArr[34]; } catch(ex) { };
    try { document.all('AppntFoundDate').value = cArr[35]; } catch(ex) { };
    try { document.all('AppntBankAccNo').value = cArr[36]; } catch(ex) { };
    try { document.all('AppntBankCode').value = cArr[37]; } catch(ex) { };
    try { document.all('AppntGrpGroupNo').value = cArr[38]; } catch(ex) { };
    try { document.all('AppntState').value = cArr[39]; } catch(ex) { };
    try { document.all('AppntRemark').value = cArr[40]; } catch(ex) { };
    try { document.all('AppntBlacklistFlag').value = cArr[41]; } catch(ex) { };
    try { document.all('AppntOperator').value = cArr[42]; } catch(ex) { };
    try { document.all('AppntMakeDate').value = cArr[43]; } catch(ex) { };
    try { document.all('AppntMakeTime').value = cArr[44]; } catch(ex) { };
    try { document.all('AppntModifyDate').value = cArr[45]; } catch(ex) { };
    try { document.all('AppntModifyTime').value = cArr[46]; } catch(ex) { };
    try { document.all('AppntFIELDNUM').value = cArr[47]; } catch(ex) { };
    try { document.all('AppntPK').value = cArr[48]; } catch(ex) { };
    try { document.all('AppntfDate').value = cArr[49]; } catch(ex) { };
    try { document.all('AppntmErrors').value = cArr[50]; } catch(ex) { };
    */
    try { document.all('AppntPolNo').value = cArr[0]; } catch(ex) { };
    try { document.all('AppntGrpNo').value = cArr[1]; } catch(ex) { };
    try { document.all('AppntRelationToInsured').value = cArr[2]; } catch(ex) { };
    try { document.all('AppntAppntGrade').value = cArr[3]; } catch(ex) { };
    try { document.all('AppntPassword').value = cArr[4]; } catch(ex) { };
    try { document.all('AppntGrpName').value = cArr[5]; } catch(ex) { };
    try { document.all('AppntGrpAddressCode').value = cArr[6]; } catch(ex) { };
    try { document.all('AppntGrpAddress').value = cArr[7]; } catch(ex) { };
    try { document.all('AppntGrpZipCode').value = cArr[8]; } catch(ex) { };
    try { document.all('AppntBusinessType').value = cArr[9]; } catch(ex) { };
    try { document.all('AppntGrpNature').value = cArr[10]; } catch(ex) { };
    try { document.all('AppntPeoples').value = cArr[11]; } catch(ex) { };
    try { document.all('AppntRgtMoney').value = cArr[12]; } catch(ex) { };
    try { document.all('AppntAsset').value = cArr[13]; } catch(ex) { };
    try { document.all('AppntNetProfitRate').value = cArr[14]; } catch(ex) { };
    try { document.all('AppntMainBussiness').value = cArr[15]; } catch(ex) { };
    try { document.all('AppntCorporation').value = cArr[16]; } catch(ex) { };
    try { document.all('AppntComAera').value = cArr[17]; } catch(ex) { };
    try { document.all('AppntLinkMan1').value = cArr[18]; } catch(ex) { };
    try { document.all('AppntDepartment1').value = cArr[19]; } catch(ex) { };
    try { document.all('AppntHeadShip1').value = cArr[20]; } catch(ex) { };
    try { document.all('AppntPhone1').value = cArr[21]; } catch(ex) { };
    try { document.all('AppntE_Mail1').value = cArr[22]; } catch(ex) { };
    try { document.all('AppntFax1').value = cArr[23]; } catch(ex) { };
    try { document.all('AppntLinkMan2').value = cArr[24]; } catch(ex) { };
    try { document.all('AppntDepartment2').value = cArr[25]; } catch(ex) { };
    try { document.all('AppntHeadShip2').value = cArr[26]; } catch(ex) { };
    try { document.all('AppntPhone2').value = cArr[27]; } catch(ex) { };
    try { document.all('AppntE_Mail2').value = cArr[28]; } catch(ex) { };
    try { document.all('AppntFax2').value = cArr[29]; } catch(ex) { };
    try { document.all('AppntFax').value = cArr[30]; } catch(ex) { };
    try { document.all('AppntPhone').value = cArr[31]; } catch(ex) { };
    try { document.all('AppntGetFlag').value = cArr[32]; } catch(ex) { };
    try { document.all('AppntSatrap').value = cArr[33]; } catch(ex) { };
    try { document.all('AppntEMail').value = cArr[34]; } catch(ex) { };
    try { document.all('AppntFoundDate').value = cArr[35]; } catch(ex) { };
    try { document.all('AppntBankAccNo').value = cArr[36]; } catch(ex) { };
    try { document.all('AppntBankCode').value = cArr[37]; } catch(ex) { };
    try { document.all('AppntGrpGroupNo').value = cArr[38]; } catch(ex) { };
    try { document.all('AppntState').value = cArr[39]; } catch(ex) { };
    try { document.all('AppntRemark').value = cArr[40]; } catch(ex) { };
    try { document.all('AppntBlacklistFlag').value = cArr[41]; } catch(ex) { };
    try { document.all('AppntOperator').value = cArr[42]; } catch(ex) { };
    try { document.all('AppntMakeDate').value = cArr[43]; } catch(ex) { };
    try { document.all('AppntMakeTime').value = cArr[44]; } catch(ex) { };
    try { document.all('AppntModifyDate').value = cArr[45]; } catch(ex) { };
    try { document.all('AppntModifyTime').value = cArr[46]; } catch(ex) { };
    try { document.all('AppntFIELDNUM').value = cArr[47]; } catch(ex) { };
    try { document.all('AppntPK').value = cArr[48]; } catch(ex) { };
    try { document.all('AppntfDate').value = cArr[49]; } catch(ex) { };
    try { document.all('AppntmErrors').value = cArr[50]; } catch(ex) { };
}

/*********************************************************************
 *  把保单中的被保人数据显示到被保人部分
 *  参数  ：  客户的信息
 *  返回值：  无
 *********************************************************************
 */
function displayPolInsured(cArr)
{
    // 从LCInsured表取数据
    try { document.all('ContNo').value=cArr[1];} catch(ex){};
    //alert("contno mm:"+document.all('ContNo').value);
    try { document.all('CustomerNo').value = cArr[2]; } catch(ex) { };
    try { document.all('SequenceNo').value = cArr[11]; } catch(ex) { };
    //try { document.all('InsuredGrade').value = cArr[3]; } catch(ex) { };
    try { document.all('RelationToInsured').value = cArr[8]; } catch(ex) { };
    //try { document.all('Password').value = cArr[5]; } catch(ex) { };
    try { document.all('Name').value = cArr[12]; } catch(ex) { };
    try { document.all('Sex').value = cArr[13]; } catch(ex) { };
    try { document.all('Birthday').value = cArr[14]; } catch(ex) { };
    try { document.all('NativePlace').value = cArr[17]; } catch(ex) { };
    try { document.all('Nationality').value = cArr[18]; } catch(ex) { };
    try { document.all('Marriage').value = cArr[20]; } catch(ex) { };
    try { document.all('MarriageDate').value = cArr[21]; } catch(ex) { };
    try { document.all('OccupationType').value = cArr[34]; } catch(ex) { };
    try { document.all('StartWorkDate').value = cArr[31]; } catch(ex) { };
    try { document.all('Salary').value = cArr[33]; } catch(ex) { };
    try { document.all('Health').value = cArr[22]; } catch(ex) { };
    try { document.all('Stature').value = cArr[23]; } catch(ex) { };
    try { document.all('Avoirdupois').value = cArr[24]; } catch(ex) { };
    try { document.all('CreditGrade').value = cArr[26]; } catch(ex) { };
    try { document.all('IDType').value = cArr[15]; } catch(ex) { };
    //try { document.all('Proterty').value = cArr[21]; } catch(ex) { };
    try { document.all('IDNo').value = cArr[16]; } catch(ex) { };
    //try { document.all('OthIDType').value = cArr[23]; } catch(ex) { };
    //try { document.all('OthIDNo').value = cArr[24]; } catch(ex) { };
    //try { document.all('ICNo').value = cArr[25]; } catch(ex) { };
    //try { document.all('HomeAddressCode').value = cArr[26]; } catch(ex) { };
    //try { document.all('HomeAddress').value = cArr[27]; } catch(ex) { };
    //try { document.all('PostalAddress').value = cArr[28]; } catch(ex) { };
    //try { document.all('ZipCode').value = cArr[29]; } catch(ex) { };
    //try { document.all('Phone').value = cArr[30]; } catch(ex) { };
    //try { document.all('BP').value = cArr[31]; } catch(ex) { };
    //try { document.all('Mobile').value = cArr[32]; } catch(ex) { };
    //try { document.all('EMail').value = cArr[33]; } catch(ex) { };
    //try { document.all('JoinCompanyDate').value = cArr[34]; } catch(ex) { };
    //try { document.all('Position').value = cArr[35]; } catch(ex) { };
    //try { document.all('GrpNo').value = cArr[4]; } catch(ex) { };
    //try { document.all('GrpName').value = cArr[37]; } catch(ex) { };
    //try { document.all('GrpPhone').value = cArr[38]; } catch(ex) { };
    //try { document.all('GrpAddressCode').value = cArr[39]; } catch(ex) { };
    //try { document.all('GrpAddress').value = cArr[40]; } catch(ex) { };
    //try { document.all('DeathDate').value = cArr[41]; } catch(ex) { };
    //try { document.all('State').value = cArr[43]; } catch(ex) { };
    try { document.all('WorkType').value = cArr[36]; } catch(ex) { };
    try { document.all('PluralityType').value = cArr[37]; } catch(ex) { };
    try { document.all('OccupationCode').value = cArr[35]; } catch(ex) { };
    try { document.all('Degree').value = cArr[25]; } catch(ex) { };
    //try { document.all('GrpZipCode').value = cArr[50]; } catch(ex) { };
    try { document.all('SmokeFlag').value = cArr[38]; } catch(ex) { };
    try { document.all('RgtAddress').value = cArr[19]; } catch(ex) { };
    //try { document.all('HomeZipCode').value = cArr[53]; } catch(ex) { };
    //try { document.all('Phone2').value = cArr[54]; } catch(ex) { };
    return;

}

/*********************************************************************
 *  把查询返回的客户数据显示到连带被保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displaySubInsured()
{
    document.all( spanObj ).all( 'SubInsuredGrid1' ).value = arrResult[0][0];
    document.all( spanObj ).all( 'SubInsuredGrid2' ).value = arrResult[0][2];
    document.all( spanObj ).all( 'SubInsuredGrid3' ).value = arrResult[0][3];
    document.all( spanObj ).all( 'SubInsuredGrid4' ).value = arrResult[0][4];
}

/*********************************************************************
 *  查询返回明细信息时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
 *  参数  ：  查询返回的二维数组
 *  返回值：  无
 *********************************************************************
 */
function afterQuery( arrQueryResult ) {
    if( arrQueryResult != null ) {
        arrResult = arrQueryResult;

        if( mOperate == 1 ) {           // 查询保单明细
            //alert("abc");
            var tPolNo = arrResult[0][0];

            // 查询保单明细
            queryPolDetail( tPolNo );
        }

        if( mOperate == 2 ) {       // 投保人信息
            arrResult = easyExecSql("select * from LDPerson where CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
            if (arrResult == null) {
              alert("未查到投保人信息");
            } else {
               displayAppnt(arrResult[0]);
            }

      }
        if( mOperate == 3 ) {       // 主被保人信息
            arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ),b.PostalAddress,b.ZipCode,b.Phone,b.HomePhone from LDPerson a Left Join LCAddress b On b.CustomerNo =a.CustomerNo Where 1=1 and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
            if (arrResult == null) {
              alert("未查到主被保人信息");
            } else {
               displayInsured(arrResult[0]);
            }

      }
        if( mOperate == 4 ) {       // 连带被保人信息
            displaySubInsured(arrResult[0]);
      }
    }
    mOperate = 0;       // 恢复初态

    emptyUndefined();
}

/*********************************************************************
 *  根据查询返回的信息查询投保单明细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryPolDetail( cPolNo )
{
    emptyForm();
    //alert("ccc");
    //var showStr = "正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    //var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    //parent.fraSubmit.window.location = "./ProposalQueryDetail.jsp?PolNo=" + cPolNo;
    parent.fraTitle.window.location = "./ProposalQueryDetail.jsp?PolNo=" + cPolNo;
}


/*********************************************************************
 *  根据查询返回的信息查询险种的保险计划明细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryRiskPlan( tProposalGrpContNo,tRiskCode,tContPlanCode,tMainRiskCode )
{
    emptyForm();
    //var showStr = "正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    //var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //alert("./ProposalQueryRiskPlan.jsp?ProposalGrpContNo="
    //                                                                      + tProposalGrpContNo+"&RiskCode="+tRiskCode+"&ContPlanCode="+tContPlanCode);
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    //parent.fraSubmit.window.location = "./ProposalQueryDetail.jsp?PolNo=" + cPolNo;
    parent.fraSubmit.window.location = "./ProposalQueryRiskPlan.jsp?ProposalGrpContNo="
                                                                            + tProposalGrpContNo+"&RiskCode="+tRiskCode+"&ContPlanCode="+tContPlanCode+"&MainRiskCode="+tMainRiskCode;
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

//*************************************************************
//被保人客户号查询按扭事件
function queryInsuredNo() {
  if (document.all("CustomerNo").value == "") {
    showInsured1();
  //} else if (LoadFlag != "1" && LoadFlag != "2") {
  //  alert("只能在投保单录入时进行操作！");
  }  else {
      arrResult=easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ),b.PostalAddress,b.ZipCode,b.Phone,b.HomePhone from LDPerson a Left Join LCAddress b On b.CustomerNo =a.CustomerNo Where 1=1 and a.CustomerNo = '" + document.all("CustomerNo").value + "'", 1, 0);
    if (arrResult == null) {
      alert("未查到主被保人信息");
      displayInsured(new Array());
      emptyUndefined();
    } else {

      displayInsured(arrResult[0]);
    }
  }
}

//*************************************************************
//投保人客户号查询按扭事件
function queryAppntNo() {
  if (document.all("AppntCustomerNo").value == "" && LoadFlag == "1") {
    showAppnt1();
  //} else if (LoadFlag != "1" && LoadFlag != "2") {
  //  alert("只能在投保单录入时进行操作！");
  } else {
    arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ),b.PostalAddress,b.ZipCode,b.Phone,b.HomePhone from LDPerson a Left Join LCAddress b On b.CustomerNo =a.CustomerNo Where 1=1 and a.CustomerNo = '" + document.all("AppntCustomerNo").value + "'", 1, 0);
    if (arrResult == null) {
      alert("未查到投保人信息");
      displayAppnt(new Array());
      emptyUndefined();
    } else {
      displayAppnt(arrResult[0]);
    }
  }
}

//*************************************************************
//投保人与被保人相同选择框事件
function isSamePerson() {
  //对应未选同一人，又打钩的情况
  if (fm.AppntRelationToInsured.value!="00" && fm.SamePersonFlag.checked==true) {
    document.all('DivLCInsured').style.display = "";
    fm.SamePersonFlag.checked = false;
    alert("投保人与被保人关系不是本人，不能进行该操作！");
  }
  //对应是同一人，又打钩的情况
  else if (fm.SamePersonFlag.checked == true) {
    document.all('DivLCInsured').style.display = "none";
  }
  //对应不选同一人的情况
  else if (fm.SamePersonFlag.checked == false) {
    document.all('DivLCInsured').style.display = "";
  }

  for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {
      if (fm.elements[elementsNum].name.indexOf("Appnt") != -1) {
        try {
          insuredName = fm.elements[elementsNum].name.substring(fm.elements[elementsNum].name.indexOf("t") + 1);
          if (document.all('DivLCInsured').style.display == "none") {
            document.all(insuredName).value = fm.elements[elementsNum].value;
          }
          else {
            document.all(insuredName).value = "";
          }
        }
        catch (ex) {}
      }
    }

}

//*************************************************************
//保存时校验投保人与被保人相同选择框事件
function verifySamePerson() {
  if (fm.SamePersonFlag.checked == true) {
    for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {
      if (fm.elements[elementsNum].name.indexOf("Appnt") != -1) {
        try {
          insuredName = fm.elements[elementsNum].name.substring(fm.elements[elementsNum].name.indexOf("t") + 1);
          if (document.all('DivLCInsured').style.display == "none") {
            document.all(insuredName).value = fm.elements[elementsNum].value;
          }
          else {
            document.all(insuredName).value = "";
          }
        }
        catch (ex) {}
      }
      }
  }
  else if (fm.SamePersonFlag.checked == false) {

  }

}


/*********************************************************************
 *  把数组中的数据显示到投保人部分
 *  参数  ：  个人客户的信息
 *  返回值：  无
 *********************************************************************
 */
function displayAppnt()
{
    // 从LDPerson表取数据
try{document.all('AppntCustomerNo').value= arrResult[0][0]; }catch(ex){};
try{document.all('AppntName').value= arrResult[0][1]; }catch(ex){};
try{document.all('AppntSex').value= arrResult[0][2]; }catch(ex){};
try{document.all('AppntBirthday').value= arrResult[0][3]; }catch(ex){};
try{document.all('AppntIDType').value= arrResult[0][4]; }catch(ex){};
try{document.all('AppntIDNo').value= arrResult[0][5]; }catch(ex){};
try{document.all('AppntPassword').value= arrResult[0][6]; }catch(ex){};
try{document.all('AppntNativePlace').value= arrResult[0][7]; }catch(ex){};
try{document.all('AppntNationality').value= arrResult[0][8]; }catch(ex){};
try{document.all('AppntRgtAddress').value= arrResult[0][9]; }catch(ex){};
try{document.all('AppntMarriage').value= arrResult[0][10];}catch(ex){};
try{document.all('AppntMarriageDate').value= arrResult[0][11];}catch(ex){};
try{document.all('AppntHealth').value= arrResult[0][12];}catch(ex){};
try{document.all('AppntStature').value= arrResult[0][13];}catch(ex){};
try{document.all('AppntAvoirdupois').value= arrResult[0][14];}catch(ex){};
try{document.all('AppntDegree').value= arrResult[0][15];}catch(ex){};
try{document.all('AppntCreditGrade').value= arrResult[0][16];}catch(ex){};
try{document.all('AppntOthIDType').value= arrResult[0][17];}catch(ex){};
try{document.all('AppntOthIDNo').value= arrResult[0][18];}catch(ex){};
try{document.all('AppntICNo').value= arrResult[0][19];}catch(ex){};
try{document.all('AppntGrpNo').value= arrResult[0][20];}catch(ex){};
try{document.all('AppntJoinCompanyDate').value= arrResult[0][21];}catch(ex){};
try{document.all('AppntStartWorkDate').value= arrResult[0][22];}catch(ex){};
try{document.all('AppntPosition').value= arrResult[0][23];}catch(ex){};
try{document.all('AppntSalary').value= arrResult[0][24];}catch(ex){};
try{document.all('AppntOccupationType').value= arrResult[0][25];}catch(ex){};
try{document.all('AppntOccupationCode').value= arrResult[0][26];}catch(ex){};
try{document.all('AppntWorkType').value= arrResult[0][27];}catch(ex){};
try{document.all('AppntPluralityType').value= arrResult[0][28];}catch(ex){};
try{document.all('AppntDeathDate').value= arrResult[0][29];}catch(ex){};
try{document.all('AppntSmokeFlag').value= arrResult[0][30];}catch(ex){};
try{document.all('AppntBlacklistFlag').value= arrResult[0][31];}catch(ex){};
try{document.all('AppntProterty').value= arrResult[0][32];}catch(ex){};
try{document.all('AppntRemark').value= arrResult[0][33];}catch(ex){};
try{document.all('AppntState').value= arrResult[0][34];}catch(ex){};
try{document.all('AppntOperator').value= arrResult[0][35];}catch(ex){};
try{document.all('AppntMakeDate').value= arrResult[0][36];}catch(ex){};
try{document.all('AppntMakeTime').value= arrResult[0][37];}catch(ex){};
try{document.all('AppntModifyDate').value= arrResult[0][38];}catch(ex){};
try{document.all('AppntModifyTime').value= arrResult[0][39];}catch(ex){};
try{document.all('AppntGrpName').value= arrResult[0][40];}catch(ex){};
try{document.all('AppntHomeAddress').value= arrResult[0][41];}catch(ex){};
try{document.all('AppntHomeZipCode').value= arrResult[0][42];}catch(ex){};
try{document.all('AppntPhone').value= arrResult[0][43];}catch(ex){};
try{document.all('AppntPhone2').value= arrResult[0][44];}catch(ex){};
}

/*********************************************************************
 *  把数组中的数据显示到投保人部分
 *  参数  ：  集体客户的信息
 *  返回值：  无
 *********************************************************************
 */
function displayAddress1()
{
try {document.all('GrpNo').value= arrResult[0][0]; } catch(ex) { };
try {document.all('GrpCustomerNo').value= arrResult[0][0]; } catch(ex) { };
try {document.all('AddressNo').value= arrResult[0][1]; } catch(ex) { };
try {document.all('AppGrpAddress').value= arrResult[0][2]; } catch(ex) { };
try {document.all('AppGrpZipCode').value= arrResult[0][3]; } catch(ex) { };
try {document.all('LinkMan1').value= arrResult[0][4]; } catch(ex) { };
try {document.all('Department1').value= arrResult[0][5]; } catch(ex) { };
try {document.all('HeadShip1').value= arrResult[0][6]; } catch(ex) { };
try {document.all('GrpPhone1').value= arrResult[0][7]; } catch(ex) { };
try {document.all('E_Mail1').value= arrResult[0][8]; } catch(ex) { };
try {document.all('Fax1').value= arrResult[0][9]; } catch(ex) { };
try {document.all('LinkMan2').value= arrResult[0][10]; } catch(ex) { };
try {document.all('Department2').value= arrResult[0][11]; } catch(ex) { };
try {document.all('HeadShip2').value= arrResult[0][12]; } catch(ex) { };
try {document.all('GrpPhone2').value= arrResult[0][13]; } catch(ex) { };
try {document.all('E_Mail2').value= arrResult[0][14]; } catch(ex) { };
try {document.all('Fax2').value= arrResult[0][15]; } catch(ex) { };
try {document.all('Operator').value= arrResult[0][16]; } catch(ex) { };
try {document.all('MakeDate').value= arrResult[0][17]; } catch(ex) { };
try {document.all('MakeTime').value= arrResult[0][18]; } catch(ex) { };
try {document.all('ModifyDate').value= arrResult[0][19]; } catch(ex) { };
try {document.all('ModifyTime').value= arrResult[0][20]; } catch(ex) { };
//以下是ldgrp表
try {document.all('BusinessType').value= arrResult[0][22];} catch(ex) { };
try {document.all('GrpNature').value= arrResult[0][23]; } catch(ex) { };
try {document.all('Peoples').value= arrResult[0][24]; } catch(ex) { };
try {document.all('RgtMoney').value= arrResult[0][25]; } catch(ex) { };
try {document.all('Asset').value= arrResult[0][26]; } catch(ex) { };
try {document.all('NetProfitRate').value= arrResult[0][27];} catch(ex) { };
try {document.all('MainBussiness').value= arrResult[0][28];} catch(ex) { };
try {document.all('Corporation').value= arrResult[0][29];  } catch(ex) { };
try {document.all('ComAera').value= arrResult[0][30]; } catch(ex) { };
try {document.all('Fax').value= arrResult[0][31]; } catch(ex) { };
try {document.all('Phone').value= arrResult[0][32]; } catch(ex) { };
try {document.all('FoundDate').value= arrResult[0][33]; } catch(ex) { };
try {document.all('AppGrpNo').value= arrResult[0][34]; } catch(ex) { };
try {document.all('AppGrpName').value= arrResult[0][35]; } catch(ex) { };
}
function displayAppntGrp( cArr )
{
    // 从LDGrp表取数据
    try { document.all('AppGrpNo').value = cArr[0]; } catch(ex) { };
    try { document.all('Password').value = cArr[1]; } catch(ex) { };
    try { document.all('AppGrpName').value = cArr[2]; } catch(ex) { };
    try { document.all('GrpAddressCode').value = cArr[3]; } catch(ex) { };
    try { document.all('AppGrpAddress').value = cArr[4]; } catch(ex) { };
    try { document.all('AppGrpZipCode').value = cArr[5]; } catch(ex) { };
    try { document.all('BusinessType').value = cArr[6]; } catch(ex) { };
    try { document.all('GrpNature').value = cArr[7]; } catch(ex) { };
    try { document.all('Peoples').value = cArr[8]; } catch(ex) { };
    try { document.all('RgtMoney').value = cArr[9]; } catch(ex) { };
    try { document.all('Asset').value = cArr[10]; } catch(ex) { };
    try { document.all('NetProfitRate').value = cArr[11]; } catch(ex) { };
    try { document.all('MainBussiness').value = cArr[12]; } catch(ex) { };
    try { document.all('Corporation').value = cArr[13]; } catch(ex) { };
    try { document.all('ComAera').value = cArr[14]; } catch(ex) { };
    try { document.all('LinkMan1').value = cArr[15]; } catch(ex) { };
    try { document.all('Department1').value = cArr[16]; } catch(ex) { };
    try { document.all('HeadShip1').value = cArr[17]; } catch(ex) { };
    try { document.all('Phone1').value = cArr[18]; } catch(ex) { };
    try { document.all('E_Mail1').value = cArr[19]; } catch(ex) { };
    try { document.all('Fax1').value = cArr[20]; } catch(ex) { };
    try { document.all('LinkMan2').value = cArr[21]; } catch(ex) { };
    try { document.all('Department2').value = cArr[22]; } catch(ex) { };
    try { document.all('HeadShip2').value = cArr[23]; } catch(ex) { };
    try { document.all('Phone2').value = cArr[24]; } catch(ex) { };
    try { document.all('E_Mail2').value = cArr[25]; } catch(ex) { };
    try { document.all('Fax2').value = cArr[26]; } catch(ex) { };
    try { document.all('Fax').value = cArr[27]; } catch(ex) { };
    try { document.all('Phone').value = cArr[28]; } catch(ex) { };
    try { document.all('GetFlag').value = cArr[29]; } catch(ex) { };
    try { document.all('Satrap').value = cArr[30]; } catch(ex) { };
    try { document.all('EMail').value = cArr[31]; } catch(ex) { };
    try { document.all('FoundDate').value = cArr[32]; } catch(ex) { };
    try { document.all('BankAccNo').value = cArr[33]; } catch(ex) { };
    try { document.all('BankCode').value = cArr[34]; } catch(ex) { };
    try { document.all('GrpGroupNo').value = cArr[35]; } catch(ex) { };
    try { document.all('State').value = cArr[36]; } catch(ex) { };
}

/*********************************************************************
 *  把查询返回的客户数据显示到被保人部分
 *  参数  ：  客户的信息
 *  返回值：  无
 *********************************************************************
 */
function displayInsured()
{
    // 从LDPerson表取数据
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
    try{document.all('InsuredStat').value=arrResult[0][41];}catch(ex){};
    try{document.all('MakeDate').value=arrResult[0][42];}catch(ex){};
    try{document.all('MakeTime').value=arrResult[0][43];}catch(ex){};
    try{document.all('ModifyDate').value=arrResult[0][44];}catch(ex){};
    try{document.all('ModifyTime').value=arrResult[0][45];}catch(ex){};
    try{document.all('GrpName').value= arrResult[0][46];}catch(ex){};
    try{document.all('HomeAddress').value= arrResult[0][47];}catch(ex){};
    try{document.all('HomeZipCode').value= arrResult[0][48];}catch(ex){};
    try{document.all('Phone').value= arrResult[0][49];}catch(ex){};
    try{document.all('Phone2').value= arrResult[0][50];}catch(ex){};
    //alert("joindate:"+document.all('JoinCompanyDate').value);
    //alert("grpcontno:"+document.all('GrpContNo').value);
}

//*********************************************************************
function showAppnt1()
{
    if( mOperate == 0 )
    {
        mOperate = 2;
        showInfo = window.open( "../sys/LDPersonQuery.html" );
    }
}

//*********************************************************************
function showInsured1()
{
    if( mOperate == 0 )
    {
        mOperate = 3;
        showInfo = window.open( "../sys/LDPersonQuery.html" );
    }
}

function isSamePersonQuery() {
  fm.SamePersonFlag.checked = true;
  //divSamePerson.style.display = "none";
  DivLCInsured.style.display = "none";
}

//问题件录入
function QuestInput()
{
    cContNo = document.all("ContNo").value;  //保单号码
    //alert("cContNo:"+cContNo)
        if(LoadFlag=="2"||LoadFlag=="4"){
            if(mSwitch.getVar( "ProposalGrpContNo" )==""){
              alert("尚无集体合同投保单号，请先保存!");
                }
                else{
            window.open("./GrpQuestInputMain.jsp?GrpContNo="+mSwitch.getVar( "ProposalGrpContNo" )+"&Flag="+LoadFlag);
                    }
            }
            else{
                    if(cContNo == ""){
                   alert("尚无合同投保单号，请先保存!");
                     }
                     else
                    {
                window.open("../uw/QuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag,"window1");
                    }
            }
}
function QuestQuery()
{

   cContNo = document.all("ContNo").value;  //保单号码

   if(LoadFlag=="2"||LoadFlag=="4"||LoadFlag=="13"){
    if(mSwitch.getVar( "ProposalGrpContNo" )==""||mSwitch.getVar( "ProposalGrpContNo" )==null)
    {
        alert("请先选择一个团体主险投保单!");
        return ;
        }
        else{
            window.open("./GrpQuestQueryMain.jsp?GrpContNo="+mSwitch.getVar( "ProposalGrpContNo" )+"&Flag="+LoadFlag);
        }
   }
   else{
        if(cContNo == ""){
           alert("尚无合同投保单号，请先保存!");
     }
    else{
               window.open("../uw/QuestQueryMain.jsp?ContNo="+cContNo+"&Flag="+LoadFlag,"window1");
        }
   }
}
//显示投保人年龄
function showAppntAge() {
  var age = calAge(document.all("AppntBirthday").value);
  var today = new Date();

  document.all("AppntBirthday").title = "投保人到今天 " + today.toLocaleString()
                                + " \n的年龄为：" + age + " 岁!";
}

//显示被保人年龄
function showAge() {
  var age = calAge(document.all("Birthday").value);
  var today = new Date();

  document.all("Birthday").title = "被保人到今天 " + today.toLocaleString()
                           + " \n的年龄为：" + age + " 岁!";
}

//检验投保人出生日期，如果空，且身份证号有，则从身份证取。取不到返回空格;
function checkBirthday()
{
    try{
          var strBrithday = "";
          if(trim(document.all("AppntBirthday").value)==""||document.all("AppntBirthday").value==null)
          {
            if (trim(document.all("AppntIDType").value) == "0")
             {
               strBrithday= getBirthdatByIdNo(document.all("AppntIDNo").value);
               if(strBrithday=="") passVerify=false;

               document.all("AppntBirthday").value= strBrithday;
             }
          }
     }
     catch(e)
     {

     }
}

//校验录入的险种是否不需要校验，如果需要返回true,否则返回false
function needVerifyRiskcode()
{

  try {
       var riskcode=document.all("RiskCode").value;

       //var tSql = "select Sysvarvalue from LDSysVar where Sysvar='NotVerifyRiskcode'";
       var sqlid17="NSProposalInputSql16";
   	   var mySql17=new SqlClass();
       mySql17.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
       mySql17.setSqlId(sqlid17);//指定使用的Sql的id
   	   var tSql=mySql17.getString();
       var tResult = easyExecSql(tSql, 1, 1, 1);
       var strRiskcode = tResult[0][0];
       var strValue=strRiskcode.split("/");
       var i=0;
       while(i<strValue.length)
       {
        if(riskcode==strValue[i])
        {
           return false;
        }
        i++;
       }
     }
    catch(e)
     {}

    return true;


}


/*********************************************************************
 *  初始化特殊的附险信息-121301和其它险种的初始化不一样
 *  参数  ：  投保单号
 *  返回值：  无
 *********************************************************************
 */
function initPrivateRiskInfo121301(cPolNo) {
    if(cPolNo=="") {
        alert("没有主险保单号,不能进行附加险录入!");
        mCurOperateFlag="";        //清空当前操作类型,使得不能进行当前操作.
        return false
    }

    var arrLCPol = new Array();
    var arrQueryResult = null;
    // 主保单信息部分
//    var sql = "select * from lcpol where polno='" + cPolNo + "' "
//            + "and riskcode in "
//            + "( select riskcode from LMRiskApp where SubRiskFlag = 'M' )";
    var sqlid18="NSProposalInputSql17";
	var mySql18=new SqlClass();
	mySql18.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
	mySql18.setSqlId(sqlid18);//指定使用的Sql的id
	mySql18.addSubPara(cPolNo);//指定传入的参数
	var sql=mySql18.getString();

    arrQueryResult = easyExecSql( sql , 1, 0);

    if (arrQueryResult == null) {
        mCurOperateFlag="";        //清空当前操作类型,使得不能进行当前操作.

        //top.mainPolNo = "";
        mainRiskPolNo = "";

        alert("读取主险信息失败,不能进行附加险录入!");
        return false
    }

    arrLCPol = arrQueryResult[0];
    displayPol( arrLCPol );
    displayPolSpec( arrLCPol );//初始化特殊要求的保单信息

    document.all("MainPolNo").value = cPolNo;
    var tAR;

    //个人投保人信息
      arrQueryResult = null;
      arrQueryResult = easyExecSql("select * from lcappntind where polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[26]+"'", 1, 0);
      tAR = arrQueryResult[0];
      displayPolAppnt(tAR);
      try { document.all('AppntRelationToInsured').value = '00'; } catch(ex) { };
      try { document.all("SamePersonFlag").checked = true; } catch(ex) { };
      try {isSamePerson();} catch(ex) { };
      try { document.all("SamePersonFlag").disabled=true} catch(ex) { };


    // 被保人信息部分
    //  arrQueryResult = null;
    //  arrQueryResult = easyExecSql("select * from lcappntind where polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[26]+"'", 1, 0);
    //  tAR = arrQueryResult[0];
    //  displayPolInsuredSpec(tAR);


    return true;
}



/*********************************************************************
 *  把保单数组中的数据显示到特殊的险种信息显示部分-121301,
 *  参数  ：  保单的信息
 *  返回值：  无
 *********************************************************************
 */
function displayPolSpec(cArr)
{
    try
    {

        try { document.all('PayEndYear').value = cArr[109]; } catch(ex) { };
        try { document.all('PayEndYearFlag').value = cArr[108]; } catch(ex) { };
        try { document.all('PayIntv').value = cArr[57]; } catch(ex) { };
        try { document.all('Amnt').value = cArr[39]; } catch(ex) { };     //主险的保费即附险的保额

    } catch(ex) {
      //alert("displayPolSpec err:" + ex + "\ndata is:" + cArr);
    }
}



/*********************************************************************
 *  初始化特殊的附险信息-321601和其它险种的初始化不一样
 *  参数  ：  投保单号
 *  返回值：  无
 *********************************************************************
 */
function initPrivateRiskInfo321601(cPolNo) {
    if(cPolNo=="") {
        alert("没有主险保单号,不能进行附加险录入!");
        mCurOperateFlag="";        //清空当前操作类型,使得不能进行当前操作.
        return false
    }

    var arrLCPol = new Array();
    var arrQueryResult = null;
    // 主保单信息部分
//    var sql = "select * from lcpol where polno='" + cPolNo + "' "
//            + "and riskcode in "
//            + "( select riskcode from LMRiskApp where SubRiskFlag = 'M' )";
    var sqlid19="NSProposalInputSql18";
	var mySql19=new SqlClass();
	mySql19.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
	mySql19.setSqlId(sqlid19);//指定使用的Sql的id
	mySql19.addSubPara(cPolNo);//指定传入的参数
	var sql=mySql19.getString();

    arrQueryResult = easyExecSql( sql , 1, 0);

    if (arrQueryResult == null) {
        mCurOperateFlag="";        //清空当前操作类型,使得不能进行当前操作.

        //top.mainPolNo = "";
        document.all('mainRiskPolNo').value = "";

        alert("读取主险信息失败,不能进行附加险录入!");
        return false
    }

    arrLCPol = arrQueryResult[0];
    displayPol( arrLCPol );

    //初始化特殊要求的保单信息--//主险的保费即附险的保额(取主险保费和500000之间小值)
    try
    {
         if(arrLCPol[39]<500000)
           document.all('Amnt').value = arrLCPol[39];
         else
           document.all('Amnt').value = 500000;
    }
     catch(ex) { alert(ex);}


    document.all("MainPolNo").value = cPolNo;
    var tAR;

    //投保人信息
    if (arrLCPol[28]=="2") {     //集体投保人信息
      arrQueryResult = null;
      arrQueryResult = easyExecSql("select * from lcappntgrp where polno='"+cPolNo+"'"+" and grpno='"+arrLCPol[26]+"'", 1, 0);
      tAR = arrQueryResult[0];
      displayPolAppntGrp(tAR);
    } else {                     //个人投保人信息
      arrQueryResult = null;
      arrQueryResult = easyExecSql("select * from lcappntind where polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[26]+"'", 1, 0);
      tAR = arrQueryResult[0];
      displayPolAppnt(tAR);
    }

    // 被保人信息部分
    if (arrLCPol[18] == arrLCPol[26]) {
      document.all("SamePersonFlag").checked = true;
        parent.fraInterface.isSamePersonQuery();
    parent.fraInterface.document.all("CustomerNo").value = arrLCPol[18];
    }
    //else {
        arrQueryResult = null;
        arrQueryResult = easyExecSql("select * from lcinsured where polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[18]+"'", 1, 0);
        tAR = arrQueryResult[0];
        displayPolInsured(tAR);
    //}

    return true;
}


/*********************************************************************
 *  特殊险种处理：把主保单中的投保人数据显示到被保人部分
 *  参数  ：  客户的信息
 *  返回值：  无
 *********************************************************************
 */
function displayPolInsuredSpec(cArr)
{
        // 从LCAppntInd表取数据
    try { document.all('CustomerNo').value = cArr[1]; } catch(ex) { };
    try { document.all('Password').value = cArr[5]; } catch(ex) { };
    try { document.all('Name').value = cArr[6]; } catch(ex) { };
    try { document.all('Sex').value = cArr[7]; } catch(ex) { };
    try { document.all('Birthday').value = cArr[8]; } catch(ex) { };
    try { document.all('NativePlace').value = cArr[9]; } catch(ex) { };
    try { document.all('Nationality').value = cArr[10]; } catch(ex) { };
    try { document.all('Marriage').value = cArr[11]; } catch(ex) { };
    try { document.all('MarriageDate').value = cArr[12]; } catch(ex) { };
    try { document.all('OccupationType').value = cArr[13]; } catch(ex) { };
    try { document.all('StartWorkDate').value = cArr[14]; } catch(ex) { };
    try { document.all('Salary').value = cArr[15]; } catch(ex) { };
    try { document.all('Health').value = cArr[16]; } catch(ex) { };
    try { document.all('Stature').value = cArr[17]; } catch(ex) { };
    try { document.all('Avoirdupois').value = cArr[18]; } catch(ex) { };
    try { document.all('CreditGrade').value = cArr[19]; } catch(ex) { };
    try { document.all('IDType').value = cArr[20]; } catch(ex) { };
    try { document.all('Proterty').value = cArr[21]; } catch(ex) { };
    try { document.all('IDNo').value = cArr[22]; } catch(ex) { };
    try { document.all('OthIDType').value = cArr[23]; } catch(ex) { };
    try { document.all('OthIDNo').value = cArr[24]; } catch(ex) { };
    try { document.all('ICNo').value = cArr[25]; } catch(ex) { };
    try { document.all('HomeAddressCode').value = cArr[26]; } catch(ex) { };
    try { document.all('HomeAddress').value = cArr[27]; } catch(ex) { };
    try { document.all('PostalAddress').value = cArr[28]; } catch(ex) { };
    try { document.all('ZipCode').value = cArr[29]; } catch(ex) { };
    try { document.all('Phone').value = cArr[30]; } catch(ex) { };
    try { document.all('BP').value = cArr[31]; } catch(ex) { };
    try { document.all('Mobile').value = cArr[32]; } catch(ex) { };
    try { document.all('EMail').value = cArr[33]; } catch(ex) { };
    //try { document.all('BankCode').value = cArr[34]; } catch(ex) { };
    //try { document.all('BankAccNo').value = cArr[35]; } catch(ex) { };
    try { document.all('JoinCompanyDate').value = cArr[34]; } catch(ex) { };
    try { document.all('Position').value = cArr[35]; } catch(ex) { };
    try { document.all('GrpNo').value = cArr[36]; } catch(ex) { };
    try { document.all('GrpName').value = cArr[37]; } catch(ex) { };
    try { document.all('GrpPhone').value = cArr[38]; } catch(ex) { };
    try { document.all('GrpAddressCode').value = cArr[39]; } catch(ex) { };
    try { document.all('GrpAddress').value = cArr[40]; } catch(ex) { };
    try { document.all('DeathDate').value = cArr[41]; } catch(ex) { };
    try { document.all('State').value = cArr[43]; } catch(ex) { };
    try { document.all('WorkType').value = cArr[46]; } catch(ex) { };
    try { document.all('PluralityType').value = cArr[47]; } catch(ex) { };
    try { document.all('OccupationCode').value = cArr[49]; } catch(ex) { };
    try { document.all('Degree').value = cArr[48]; } catch(ex) { };
    try { document.all('GrpZipCode').value = cArr[50]; } catch(ex) { };
    try { document.all('SmokeFlag').value = cArr[51]; } catch(ex) { };
    try { document.all('RgtAddress').value = cArr[52]; } catch(ex) { };

}


///险种页面数据提交时对特殊险种的特殊处理
//function specDealByRisk()
//{
//  //如果是同心卡-阳光旅程险
//  if(document.all('RiskCode').value=='311603')
//  {
//     if(trim(document.all("AppntBirthday").value)==""||document.all("AppntBirthday").value==null)
//     {
//      if (trim(document.all("AppntIDType").value) != "0"||document.all("AppntIDNo").value==null||trim(document.all("AppntIDNo").value)=="")
//      {
//          document.all("AppntBirthday").value='1970-1-1';
//      }
//     }
//
//      try
//      {
//            var strBrithday = "";
//            if(trim(document.all("Birthday").value)==""||document.all("Birthday").value==null)
//            {
//              if (trim(document.all("IDType").value) == "0")
//               {
//                 strBrithday= getBirthdatByIdNo(document.all("IDNo").value);
//                 if(strBrithday=="") passVerify=false;
//
//                 document.all("Birthday").value= strBrithday;
//               }
//            }
//       }
//       catch(e)
//       {
//      }
//      return true;
//  }
//  //如果是团体商业补充医疗险
//  if(document.all('RiskCode').value=='211801')
//  {
//      //可以对险种条件校验
//      var strChooseDuty="";
//      for(i=0;i<=8;i++)
//      {
//          if(DutyGrid.getChkNo(i)==true)
//          {
//              strChooseDuty=strChooseDuty+"1";
//              DutyGrid.setRowColData(i, 5, document.all('PayEndYear').value);//交费年期
//              DutyGrid.setRowColData(i, 6, document.all('PayEndYearFlag').value);//交费年期单位
//              DutyGrid.setRowColData(i, 9, document.all('InsuYear').value);//保险年期
//              DutyGrid.setRowColData(i, 10, document.all('InsuYearFlag').value);//保险年期单位
//              DutyGrid.setRowColData(i, 11, document.all('PayIntv').value);//缴费方式
//          }
//          else
//          {
//              strChooseDuty=strChooseDuty+"0";
//          }
//      }
//      //alert(strChooseDuty);
//      //document.all('StandbyFlag1').value=strChooseDuty;
//      return true;
//  }
//  //如果是MS基业长青员工福利计划 add by guoxiang 2004-9-8 10:24
//  if(document.all('RiskCode').value=='211701')
//  {
//      //可以对险种条件校验
//      var strChooseDuty="";
//      for(i=0;i<=2;i++)
//      {
//          if(DutyGrid.getChkNo(i)==true)
//          {
//              strChooseDuty=strChooseDuty*1+1.0;
//                DutyGrid.setRowColData(i, 3, document.all('Prem').value);//保费
//              DutyGrid.setRowColData(i, 9, document.all('InsuYear').value);//保险年期
//              DutyGrid.setRowColData(i, 10, document.all('InsuYearFlag').value);//保险年期单位
//              DutyGrid.setRowColData(i, 11, document.all('PayIntv').value);//缴费方式
//              DutyGrid.setRowColData(i, 12, document.all('ManageFeeRate').value);//管理费比例
//
//          }
//          else
//          {
//              strChooseDuty=strChooseDuty*1+0.0;
//          }
//      }
//      if(strChooseDuty>1){
//         alert("基业长青员工福利每张保单只允许选择一个责任，您选择的责任次数为"+strChooseDuty+"，请修改！！！");
//         return false;
//      }
//      return true;
//  }
//
//  //如果是个人长瑞险
//  if(document.all('RiskCode').value=='112401')
//  {
//      if(document.all('GetYear').value!=''&&document.all('InsuYear').value!='')
//      {
//          if(document.all('InsuYear').value=='A')
//          {
//              document.all('InsuYear').value='88';
//              document.all('InsuYearFlag').value='A';
//          }
//          else if(document.all('InsuYear').value=='B')
//          {
//              document.all('InsuYear').value=20+Number(document.all('GetYear').value);
//              document.all('InsuYearFlag').value='A';
//          }
//          else
//          {
//              alert("保险期间必须选择！");
//              return false;
//
//          }
//          if(document.all('PayIntv').value=='0')
//          {
//              document.all('PayEndYear').value=document.all('InsuYear').value;
//              document.all('PayEndYearFlag').value=document.all('InsuYearFlag').value;
//          }
//
//      }
//      return true;
//  }
//  //如果是君安行险种
//  if(document.all('RiskCode').value=='241801')
//  {
//      try
//      {
//          var InsurCount = document.all('StandbyFlag2').value;
//          if(InsurCount>4||InsurCount<0)
//          {
//              alert("连带被保人人数不能超过4人");
//              return false;
//          }
//          SubInsuredGrid.delBlankLine("SubInsuredGrid");
//          var rowNum=SubInsuredGrid.mulLineCount;
//          if(InsurCount!=rowNum)
//          {
//              alert("连带被保人人数和多行输入的连带被保人信息的行数不符合！ ");
//              return false;
//          }
//
//      }
//      catch(ex)
//      {
//        alert(ex);
//        return false;
//      }
//      return true;
//  }
//
//
//}

//险种页面初始化时对特殊险种的特殊处理
function initDealForSpecRisk(cRiskCode)
{
  try{
    //如果是211801
    if(cRiskCode=='211801')
    {
        DutyGrid.addOne();
        DutyGrid.setRowColData(0, 1, '610001');
        DutyGrid.setRowColData(0, 2, '基本责任1档');
        DutyGrid.addOne();
        DutyGrid.setRowColData(1, 1, '610002');
        DutyGrid.setRowColData(1, 2, '基本责任2档');
        DutyGrid.addOne();
        DutyGrid.setRowColData(2, 1, '610003');
        DutyGrid.setRowColData(2, 2, '基本责任3档');
        DutyGrid.addOne();
        DutyGrid.setRowColData(3, 1, '610004');
        DutyGrid.setRowColData(3, 2, '基本责任4档');
        DutyGrid.addOne();
        DutyGrid.setRowColData(4, 1, '610005');
        DutyGrid.setRowColData(4, 2, '基本责任5档');
        DutyGrid.addOne();
        DutyGrid.setRowColData(5, 1, '610006');
        DutyGrid.setRowColData(5, 2, '基本责任6档');
        DutyGrid.addOne();
        DutyGrid.setRowColData(6, 1, '610007');
        DutyGrid.setRowColData(6, 2, '公共责任7档');
        DutyGrid.addOne();
        DutyGrid.setRowColData(7, 1, '610008');
        DutyGrid.setRowColData(7, 2, '公共责任8档');
        DutyGrid.addOne();
        DutyGrid.setRowColData(8, 1, '610009');
        DutyGrid.setRowColData(8, 2, '女员工生育责任');
        DutyGrid.lock();


    }

    //众悦年金
    if(cRiskCode=='212401')
    {

        PremGrid.addOne();
        PremGrid.setRowColData(0, 1, '601001');
        PremGrid.setRowColData(0, 2, '601101');
        PremGrid.setRowColData(0, 3, '集体交费');
        PremGrid.addOne();
        PremGrid.setRowColData(1, 1, '601001');
        PremGrid.setRowColData(1, 2, '601102');
        PremGrid.setRowColData(1, 3, '个人交费');
        PremGrid.lock();

    }

    //基业长青
    if(cRiskCode=='211701')
    {
//        var strSql = "select *  from lmdutypayrela where dutycode in  "
//                   + " (select dutycode from lmriskduty where riskcode='"+cRiskCode+"')";
        var sqlid20="NSProposalInputSql19";
    	var mySql20=new SqlClass();
    	mySql20.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
    	mySql20.setSqlId(sqlid20);//指定使用的Sql的id
    	mySql20.addSubPara(cRiskCode);//指定传入的参数
    	var strSql=mySql20.getString();
 
        
        turnPage.queryModal(strSql, PremGrid);
        PremGrid.lock;
    }

  }catch(ex) {}

}

/*********************************************************************
* 险种页面初始化时对特殊险种的特殊处理扩展
*  add by guoxiang  at 2004-9-6 16:21
*  for update up function initDealForSpecRisk
*  not write function for every risk
*********************************************************************
 */
function initDealForSpecRiskEx(cRiskCode)
{
  try{
        var strSql="";
        if(document.all('inpNeedDutyGrid').value==1){
         initDutyGrid();  //根据险种初始化多行录入框
//         strSql = "select dutycode,dutyname,'','','','','','','','','',''  from lmduty where dutycode in "
//                   + " (select dutycode from lmriskduty where riskcode='"+cRiskCode+"' and choflag!='B')";
         var sqlid21="NSProposalInputSql20";
     	 var mySql21=new SqlClass();
     	 mySql21.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
     	 mySql21.setSqlId(sqlid21);//指定使用的Sql的id
     	 mySql21.addSubPara(cRiskCode);//指定传入的参数
     	  strSql=mySql21.getString();
         
         //alert("sql:"+strSql);
         turnPage.queryModal(strSql, DutyGrid);
         var cDutyCode="";
         var tSql="";
         for(var i=0;i<=DutyGrid.mulLineCount-1;i++){
           cDutyCode=DutyGrid.getRowColData(i,1);
           //tSql="select choflag from lmriskduty where riskcode='"+cRiskCode+"' and dutycode='"+cDutyCode+"'";
         var sqlid22="NSProposalInputSql21";
       	 var mySql22=new SqlClass();
       	 mySql22.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
       	 mySql22.setSqlId(sqlid22);//指定使用的Sql的id
       	 mySql22.addSubPara(cRiskCode);//指定传入的参数
       	 mySql22.addSubPara(cDutyCode);//指定传入的参数
       	 tSql=mySql22.getString();
           var arrResult=easyExecSql(tSql,1,0);
           //alert("ChoFlag:"+arrResult[0]);
           if(arrResult[0]=="M"){
             DutyGrid.checkBoxSel(i+1);
           }
         }
         DutyGrid.lock;

        }
        if(document.all('inpNeedPremGrid').value==1){
//          strSql = "select a.dutycode,a.payplancode,a.payplanname,'','','','','','' from lmdutypayrela a where dutycode in  "
//                   + " (select dutycode from lmriskduty where riskcode='"+cRiskCode+"')";
          var sqlid23="NSProposalInputSql22";
      	  var mySql23=new SqlClass();
      	  mySql23.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
      	  mySql23.setSqlId(sqlid23);//指定使用的Sql的id
      	  mySql23.addSubPara(cRiskCode);//指定传入的参数
      	  strSql=mySql23.getString();
          turnPage.queryModal(strSql, PremGrid);
          PremGrid.lock;

        }


  }catch(ex) {}

}
function queryAgent()
{
    if(document.all('ManageCom').value==""){
         alert("请先录入管理机构信息！");
         return;
    }
    if(document.all('AgentCode').value == "") {
      //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
      var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
      }
    if(document.all('AgentCode').value != "")  {
    var cAgentCode = fm.AgentCode.value;  //保单号码
    //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
    var sqlid24="NSProposalInputSql23";
	var mySql24=new SqlClass();
	mySql24.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
	mySql24.setSqlId(sqlid24);//指定使用的Sql的id
	mySql24.addSubPara(cAgentCode);//指定传入的参数
	var strSql=mySql24.getString();
    var arrResult = easyExecSql(strSql);
    if (arrResult != null) {
      fm.AgentGroup.value = arrResult[0][2];
      alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("编码为:["+document.all('AgentCode').value+"]的代理人不存在，请确认!");
     }
    }
}

//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery2(arrResult)
{
  if(arrResult!=null)
  {
    fm.AgentCode.value = arrResult[0][0];
    fm.AgentGroup.value = arrResult[0][1];
  }
}

function queryAgent2()
{
    if(document.all('ManageCom').value==""){
         alert("请先录入管理机构信息！");
         return;
    }
    if(document.all('AgentCode').value != "" && document.all('AgentCode').value.length==10 )     {
    var cAgentCode = fm.AgentCode.value;  //保单号码
    //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
    var sqlid25="NSProposalInputSql24";
	var mySql25=new SqlClass();
	mySql25.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
	mySql25.setSqlId(sqlid25);//指定使用的Sql的id
	mySql25.addSubPara(cAgentCode);//指定传入的参数
	mySql25.addSubPara(document.all('ManageCom').value);//指定传入的参数
	var strSql=mySql25.getString();
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {
      fm.AgentGroup.value = arrResult[0][2];
      alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("编码为:["+document.all('AgentCode').value+"]的代理人不存在，请确认!");
     }
    }
}
  function returnparent()
  {

    var backstr=document.all("ContNo").value;
    //alert(backstr+"backstr");
    mSwitch.deleteVar("ContNo");
      mSwitch.addVar("ContNo", "", backstr);
      mSwitch.updateVar("ContNo", "", backstr);
//      location.href="ContInsuredInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype;
    //对于个险，团险返回的页面不同在此进行区分
    //LoadFlag='1' 个险
    //LoadFlag='2' 团险
    //alert(ScanFlag);
    if(LoadFlag == "8" && EdorType =="NS"){
      location.href="../bq/PEdorTypeNSInput.jsp?ContNo="+backstr;
      return;
    }
    if(LoadFlag=="1"){
      location.href="ContInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag;
      return;
    }
    if(LoadFlag=="2"){
        location.href="ContInsuredInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype;
      return;
    }
    if(LoadFlag=="3"){
      location.href="ContInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag;
      return;
    }
    if(LoadFlag=="5"){
      location.href="ContInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag;
      return;
    }
    if(LoadFlag=="6"){
      location.href="ContInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag;
      return;
    }
}
//(GrpContNo,LoadFlag);//根据集体合同号查出险种信息
function getRiskByGrpPolNo(GrpContNo,LoadFlag)
{
        //alert("1");
        var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
    //strsql = "select riskcode,riskname from lmriskapp where riskcode in (select riskcode from LCGrpPol where GrpContNo='"+GrpContNo+"')" ;
    var sqlid26="NSProposalInputSql25";
	var mySql26=new SqlClass();
	mySql26.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
	mySql26.setSqlId(sqlid26);//指定使用的Sql的id
	mySql26.addSubPara(GrpContNo);//指定传入的参数
	strsql=mySql26.getString();
    //alert("strsql :" + strsql);
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        m = turnPage.arrDataCacheSet.length;
        for (i = 0; i < m; i++)
        {
            j = i + 1;
//          tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
            tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
        }
    }
    //alert ("tcodedata : " + tCodeData);

    return tCodeData;
}
function getRiskByGrpAll()
{
        //alert("1");
        var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
    //strsql = "select RiskCode, RiskName from LMRiskApp where RiskProp in ('G','A','B','D') order by RiskCode" ;
    var sqlid27="NSProposalInputSql26";
	var mySql27=new SqlClass();
	mySql27.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
	mySql27.setSqlId(sqlid27);//指定使用的Sql的id
	strsql=mySql27.getString();
    //alert("strsql :" + strsql);
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        m = turnPage.arrDataCacheSet.length;
        for (i = 0; i < m; i++)
        {
            j = i + 1;
//          tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
            tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
        }
    }
    //alert ("tcodedata : " + tCodeData);

    return tCodeData;
}
function getRisk(){
        var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
//    strsql = "select RiskCode, RiskName from LMRiskApp where RiskProp in ('I','A','C','D')"
//           + " order by RiskCode";;
        var sqlid28="NSProposalInputSql27";
       	var mySql28=new SqlClass();
       	mySql28.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
       	mySql28.setSqlId(sqlid28);//指定使用的Sql的id
       	strsql=mySql28.getString();
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        m = turnPage.arrDataCacheSet.length;
        for (i = 0; i < m; i++)
        {
            j = i + 1;
//          tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
            tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
        }
    }

    return tCodeData;



}

function getRiskByContPlan(GrpContNo,ContPlanCode){
    //alert(GrpContNo+":"+ContPlanCode);
        var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
    //strsql = "select b.RiskCode, b.RiskName from LCContPlanRisk a,LMRiskApp b where  a.GrpContNo='"+GrpContNo+"' and a.ContPlanCode='"+ContPlanCode+"' and a.riskcode=b.riskcode";
    var sqlid29="NSProposalInputSql28";
	var mySql29=new SqlClass();
	mySql29.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
	mySql29.setSqlId(sqlid29);//指定使用的Sql的id
	mySql29.addSubPara(GrpContNo);//指定传入的参数
	mySql29.addSubPara(ContPlanCode);//指定传入的参数
	strsql=mySql29.getString();
    //alert(strsql);
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        m = turnPage.arrDataCacheSet.length;
        for (i = 0; i < m; i++)
        {
            j = i + 1;
//          tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
            tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
        }
    }
    //alert("tCodeData:"+tCodeData);
    return tCodeData;



}


 /*********************************************************************
 *  把合同所有信息录入结束确认
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function inputConfirm(wFlag)
{


    //alert("wFlag in ProposalInput=="+wFlag);
    //alert("abcd=="+document.all('ContNo').value);
    if (wFlag ==1 ) //录入完毕确认
    {
        if(document.all('ContNo').value == "")
      {
        alert("合同信息未保存,不容许您进行 [录入完毕] 确认！");
        return;
      }
     // alert("aaa=="+document.all('ContNo').value);
        if(ScanFlag=="1"){
          fm.WorkFlowFlag.value = "0000001099";
        }
      else{
          fm.WorkFlowFlag.value = "0000001098";
      }
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;
    //  alert("tMissionID=="+tMissionID);
    //  alert("tSubMissionID=="+tSubMissionID);
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

//此函数的目的是：查询团单或者个单下的投保，被投保信息
function getContInputnew(){
    //取得个人投保人的所有信息
    if(fm.AppntCustomerNo.value!=""&&fm.AppntCustomerNo.value!="false"){
      //arrResult=easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ),b.PostalAddress,b.ZipCode,b.Phone,b.HomePhone from LDPerson a Left Join LCAddress b On b.CustomerNo =a.CustomerNo Where 1=1 and a.CustomerNo ='"+fm.AppntCustomerNo.value+"'",1,0);
         // arrResult=easyExecSql("select a.* from LDPerson a Where 1=1 and trim(a.CustomerNo) =trim('"+fm.AppntCustomerNo.value+"')");
    //alert("aaa=="+document.all('RelationToAppnt').value);
    //alert(arrResult[0][5]);
    //displayAppnt(arrResult[0]);
    //alert("bbb=="+document.all('AppntIDNo').value);
  }
    //取得投保单位的 所有信息
    if(fm.GrpContNo.value!=""&&fm.GrpContNo.value!="false"){
      arrResult = easyExecSql("select a.*,b.GrpName,b.BusinessType,b.GrpNature,b.Peoples,b.RgtMoney,b.Asset,b.NetProfitRate,b.MainBussiness,b.Corporation,b.ComAera,b.Fax,b.Phone,b.FoundDate,b.CustomerNo,b.GrpName from LCGrpAddress a,LDGrp b where a.CustomerNo = b.CustomerNo and b.CustomerNo=(select CustomerNo from LCGrpAppnt  where GrpContNo = '" + fm.GrpContNo.value + "')", 1, 0);
      displayAddress1(arrResult[0]);
    }
    //取得被投保人的所有信息
    if(document.all('CustomerNo').value!=""&&document.all('CustomerNo').value!="false"){
    var tcustomerNo=document.all('CustomerNo').value;
    //alert("contno:"+document.all('ContNo').value);
    var tContNo=document.all('ContNo').value;
    arrResult=easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.InsuredNo) ),b.PostalAddress,b.ZipCode,b.Phone,b.HomePhone from LCInsured a Left Join LCAddress b On b.CustomerNo =a.InsuredNo Where 1=1 and a.InsuredNo ='"+tcustomerNo+"' and a.ContNo='"+tContNo+"'",1,0);
    displayInsured(arrResult[0]);
  }
}

function GrpConfirm(ScanFlag){//ScanFlag
    //alert('ProposalGrpContNo:'+document.all('ProposalGrpContNo').value);
    //alert('PrtNo:'+document.all('PrtNo').value);
    //alert('MissionID:'+document.all('MissionID').value);
     var tGrpContNo = parent.VD.gVSwitch.getVar( "GrpContNo" );

   // strSql = "select peoples2 from LCGrpCont    where GrpContNo = '" + tGrpContNo + "'";
    var sqlid30="NSProposalInputSql29";
	var mySql30=new SqlClass();
	mySql30.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
	mySql30.setSqlId(sqlid30);//指定使用的Sql的id
	mySql30.addSubPara(tGrpContNo);//指定传入的参数
	strSql=mySql30.getString();
    var tPeoplesCount = easyExecSql(strSql);

    if(tPeoplesCount==null||tPeoplesCount[0][0]<=0){
        alert("检验失败，投保总人数为0！");
        return;
    }

    //strSql = "select peoples2,riskcode from LCGrpPol    where GrpContNo = '" + tGrpContNo + "'";
    var sqlid31="NSProposalInputSql30";
	var mySql31=new SqlClass();
	mySql31.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
	mySql31.setSqlId(sqlid31);//指定使用的Sql的id
	mySql31.addSubPara(tGrpContNo);//指定传入的参数
	strSql=mySql31.getString();
    tPeoplesCount = easyExecSql(strSql);
    //alert(tPeoplesCount);
    if(tPeoplesCount!=null)
    {
        for(var i=0;i<tPeoplesCount.length;i++)
        {
            if(tPeoplesCount[i][0]<=0)
            {
                alert("检验失败，险种"+tPeoplesCount[i][1]+"下投保总人数为0！");
                return;
            }
        }
    }

//    strSql = "select SaleChnl,AgentCode,AgentGroup,ManageCom,GrpName,CValiDate,PrtNo from LCGrpCont     where GrpContNo = '"
//    + tGrpContNo + "'";
    var sqlid32="NSProposalInputSql31";
	var mySql32=new SqlClass();
	mySql32.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
	mySql32.setSqlId(sqlid32);//指定使用的Sql的id
	mySql32.addSubPara(tGrpContNo);//指定传入的参数
	strSql=mySql32.getString();

    var grpContInfo = easyExecSql(strSql);
    //alert(grpContInfo);
    var queryString = 'SaleChnl='+grpContInfo[0][0]+'&AgentCode='+grpContInfo[0][1]+'&AgentGroup='+grpContInfo[0][2]
        +'&ManageCom='+grpContInfo[0][3]+'&GrpName='+grpContInfo[0][4]+'&CValiDate='+grpContInfo[0][5];

//    strSql = "  select missionID,SubMissionID from lwmission where 1=1 "
//                    +" and lwmission.processid = '0000000004'"
//                    +" and lwmission.activityid = '0000002098'"
//                    +" and lwmission.missionprop1 = '"+grpContInfo[0][6]+"'";
    
    var sqlid33="NSProposalInputSql32";
    var mySql33=new SqlClass();
	mySql33.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
	mySql33.setSqlId(sqlid33);//指定使用的Sql的id
	mySql33.addSubPara(grpContInfo[0][6]);//指定传入的参数
	strSql=mySql33.getString();
    var missionInfo = easyExecSql(strSql);
    queryString = queryString+"&MissionID="+missionInfo[0][0]+"&SubMissionID="+missionInfo[0][1];
    //alert(queryString);
//    var tStr= " select * from lwmission where 1=1 "
//                    +" and lwmission.processid = '0000000004'"
//                    +" and lwmission.activityid = '0000002001'"
//                    +" and lwmission.missionprop1 = '"+document.all('ProposalGrpContNo').value+"'";
    var sqlid34="NSProposalInputSql33";
    var mySql34=new SqlClass();
	mySql34.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
	mySql34.setSqlId(sqlid34);//指定使用的Sql的id
	mySql34.addSubPara(document.all('ProposalGrpContNo').value);//指定传入的参数
	tStr=mySql34.getString();
    turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
    if (turnPage.strQueryResult) {
    alert("该团单合同已经做过保存！");
    return;
    }
    var WorkFlowFlag = "";
    if(ScanFlag==0)
    {
        WorkFlowFlag = "0000002098";
    }
    if(ScanFlag==1)
    {
         WorkFlowFlag = "0000002099";
    }
    queryString = queryString+"&WorkFlowFlag="+WorkFlowFlag;
    mAction = "CONFIRM";
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
    fm.action = "./GrpInputConfirm.jsp?FrameType=0&"+queryString;
    fm.submit(); //提交

}

/*********************************************************************
 *  把合同所有信息录入结束确认
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function inputConfirm2(wFlag)
{
    //alert("wFlag=="+wFlag);
    if (wFlag ==1 ) //录入完毕确认
    {
    //var tStr= " select * from lwmission where 1=1 and lwmission.activityid in ('0000001001','0000001002','0000001220','0000001230') and lwmission.missionprop1 = '"+fm.ContNo.value+"'";
    var sqlid35="NSProposalInputSql34";
    var mySql35=new SqlClass();
	mySql35.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
	mySql35.setSqlId(sqlid35);//指定使用的Sql的id
	mySql35.addSubPara(fm.ContNo.value);//指定传入的参数
	tStr=mySql35.getString();
        turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
        if (turnPage.strQueryResult) {
          alert("该合同已经做过保存！");
          return;
        }

        fm.WorkFlowFlag.value = "0000001098";
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;          //录入完毕
  }
  else if (wFlag ==2)//复核完毕确认
  {
    if(document.all('ProposalContNo').value == "")
       {
          alert("未查询出合同信息,不容许您进行 [复核完毕] 确认！");
          return;
       }
        fm.WorkFlowFlag.value = "0000001001";                   //复核完毕
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;
        approvefalg="2";
    }
      else if (wFlag ==3)
  {
    if(document.all('ProposalContNo').value == "")
       {
          alert("未查询出合同信息,不容许您进行 [复核修改完毕] 确认！");
          return;
       }
        fm.WorkFlowFlag.value = "0000001002";                   //复核修改完毕
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;
    }
    else if(wFlag == 4)
    {
         if(document.all('ProposalContNo').value == "")
       {
          alert("未查询出合同信息,不容许您进行 [修改完毕] 确认！");
          return;
       }
        fm.WorkFlowFlag.value = "0000001021";                   //问题修改
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
 *  校验险种界面的份数是否为零或空
 *  参数  ：  无
 *  返回值：  无
 *  create by malong 2005-7-11
 *********************************************************************
 */
function checkMult(){
    var tSql="";
  //tSql="select CalMode from lmduty a,lmriskduty b where b.riskcode='"+document.all('RiskCode').value+"' and a.dutycode=b.dutycode";
    var sqlid36="NSProposalInputSql35";
    var mySql36=new SqlClass();
	mySql36.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
	mySql36.setSqlId(sqlid36);//指定使用的Sql的id
	mySql36.addSubPara(document.all('RiskCode').value);//指定传入的参数
	tSql=mySql36.getString();
  var arrResult=easyExecSql(tSql);

  if(arrResult[0]=="O" && document.all('inpNeedDutyGrid').value == "0"){
        if(document.all('mult').value == "0")
        {
            alert('份数不能为零!');
            document.all('mult').value = "";
            document.all('mult').focus();
            return false;
        }
        else if(document.all('mult').value== "")
        {
            alert('份数不能为空!');
            return false;
        }
        else if(!isInteger(document.all('mult').value))
        {
                alert('份数必须为整数!');
                document.all('mult').value = "";
                document.all('mult').focus();
                return false;
        }
    }
    return true;
}

function getRiskCodeNS(ContNo){
     var i = 0;
     var j = 0;
     var m = 0;
     var n = 0;
     var strsql = "";
     var tCodeData = "0|";
     var iSql="";
   //iSql="select a.relariskcode,m.riskname from lmriskrela a,lmrisk m,lcpol p where a.relariskcode = m.riskcode and a.riskcode=p.riskcode and p.mainpolno = p.polno and p.contno = '"+ContNo+"'";
    var sqlid37="NSProposalInputSql36";
    var mySql37=new SqlClass();
	mySql37.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
	mySql37.setSqlId(sqlid37);//指定使用的Sql的id
	mySql37.addSubPara(ContNo);//指定传入的参数
	iSql=mySql37.getString();
   turnPage.strQueryResult = easyQueryVer3(iSql, 1, 0, 1);
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

     return tCodeData;

}


function ReportFileStatus(filespec)
{
   var fso, s = filespec;
   //alert(filespec);
   fso = new ActiveXObject("Scripting.FileSystemObject");
   if (fso.FileExists(filespec))
   {
   	  //alert(1);
      return true;
   }else 
   	{
   	  return false;
   	}

}
/**查询是否存在相似的险种*/
function checkSameRiskCode()
{
	//var tSQL="select riskcode from lcpol where contno='"+fm.ContNo.value+"' and appflag in ('1','2')";
	    var sqlid38="NSProposalInputSql37";
	    var mySql38=new SqlClass();
		mySql38.setResourceName("app.NSProposalInputSql"); //指定使用的properties文件名
		mySql38.setSqlId(sqlid38);//指定使用的Sql的id
		mySql38.addSubPara(fm.ContNo.value);//指定传入的参数
	  var tSQL=mySql38.getString();
	var  mrr = easyExecSql(tSQL);
    if ( mrr )
    {
    	  for(var t=0;t<mrr.length;t++)
    	  {
    	  	 if(fm.RiskCode.value==mrr[t][0])
    	  	 {
    	  	 	 return false;
    	  	 	}   	  	
    	  	}
        
    }else
    {
    	   return false;
    }
	
	  return true;
	}