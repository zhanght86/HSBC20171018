//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
//only use for bq
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var showInfo;
var spanObj;
var mDebug = "100";
var mOperate = 0;
var mAction = "";
var arrResult = new Array();
var mShowCustomerDetail = "PROPOSAL";
var mCurOperateFlag = ""    // "1--¼�룬"2"--��ѯ
var mGrpFlag = "";  //���˼����־,"0"��ʾ����,"1"��ʾ����.
var cflag = "0";        //�ļ���¼��λ��
var sign=0;
var risksql;
var arrGrpRisk = null;
var mainRiskPolNo="";
var cMainRiskCode="";
var mSwitch = parent.VD.gVSwitch;
window.onfocus = myonfocus;
var hiddenBankInfo = "";

/*********************************************************************
 *  ѡ�����ֺ�Ķ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterCodeSelect( cCodeName, Field ) {
    try {
    	//��Ը������ֵ����⴦��
    	try{
        	//�����ڼ�
        	if(cCodeName.substring(1,cCodeName.indexOf("-"))=="PayEndYear")
            {	
        		//MS��̩�ش󼲲���ȫ����
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
                
        		//MS�����ش󼲲����� 111501  ||  311501 MS�����ش󼲲����գ��н飩
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
        		//MS��������գ��ֺ��ͣ�
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
        		//312204 MS����������ȫ����D��ֺ��ͣ�
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
                //112206 MS��������ٶ���������ȫ���գ��ֺ��ͣ�
                //122201 MS���ӳ��н�������ȫ���գ��ֺ��ͣ�
                //122202 MS���Ӹ��н�������ȫ���գ��ֺ��ͣ�
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
        		
        		//MS����������ȫ����(�ֺ���)
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
    		 	//112202 MS��˳��ȫ���գ��ֺ���) ���� 112203 MS��ԣ��ȫ���գ��ֺ��ͣ�
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
                          //111301 MS�������ڱ���
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
                //MS������������(�ֺ���)||111504  MS��̩һ���ش󼲲�����
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
                
                //MS���������ȫ����(�ֺ���)
                 if(fm.RiskCode.value=="112201")
        		  { 
                		 fm.PayEndYearFlag.value="Y";
                }
        		
        		return;
            }
            


           //��Ա����ڼ�Ĵ���
           if(cCodeName.substring(1,cCodeName.indexOf("-"))=="InsuYear")
           {
                	
               //MS���Ӿþ�ͬ�ٶ������� 
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
              //112212 MS����������ȫ����(�ֺ���)  
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
               //121305 MS���Ӷ�����
               //121505 MS���Ӷ�������ش󼲲�����
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
                   
                    
                      
               
               //MS�þ�ͬ����ȫ���գ��ֺ��ͣ��������ڼ���д���  || 121504  MS���Ӿþ�ͬ����������ش󼲲�����
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
               //MS���������ȫ���գ��ֺ��ͣ�
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
               //MS��̩�ش󼲲���ȫ���գ������ڼ���д��� ||  112208 MS��̩��ȫ���գ��ֺ��ͣ�
               //121501 MS������ǰ�����ش󼲲�����
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
           
           //���ѷ�ʽ
           if(cCodeName.substring(1,cCodeName.indexOf("-")) == 'PayIntv')
           {
               if (Field.value == "0") 
               {
                 //document.all('TDPayEndYear').innerHTML = "<Input class=codeno name=PayEndYear verify=\"�ɷ�����|code:!PayEndYear&notnull\" onfocus=\"checkPayIntv()\"><input class=codename name=PayEndYearName readonly=true elementtype=nacessary>";
                 //document.all("PayEndYear").readOnly = true;
                 //document.all("PayEndYear").value = "1000";
                 //document.all("PayEndYearName").value = "��";
             	  
             	  //2008-08-25 ��Ϊ��5.3һ�£���Ϊ����ʱ�����ڼ�ͱ����ڼ�һ��
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
      //����ѡ��
      //alert("cCodeName=="+cCodeName);
      try{
        if( type =="noScan" && cCodeName == 'RiskInd')//������ɨ���¼��
          {
              //var strSql = "select distinct 1 from ldsysvar where VERIFYOPERATEPOPEDOM('"+Field.value+"','"+ManageCom+"','"+ManageCom+"',"+"'Pa')=1 ";  
            var sqlid1="NSProposalInputSql0";
          	var mySql1=new SqlClass();
          	mySql1.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
          	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
          	mySql1.addSubPara(Field.value);//ָ������Ĳ���
          	mySql1.addSubPara(ManageCom);//ָ������Ĳ���
          	mySql1.addSubPara(ManageCom);//ָ������Ĳ���
          	var strSql=mySql1.getString();
           //alert(strSql);
          var arrResult = easyExecSql(strSql);
           //alert(arrResult);
          if (arrResult == null) {
            alert("���� ["+ManageCom+"] ��Ȩ¼������ ["+Field.value+"] ��Ͷ����!");
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
          if (typeof(prtNo)!="undefined" && typeof(type)=="undefined" )//������ɨ���¼��
          {
            //alert(cCodeName);
            //var strSql2 = "select distinct 1 from ldsysvar where VERIFYOPERATEPOPEDOM('"+Field.value+"','"+ManageCom+"','"+ManageCom+"',"+"'Pz')=1 ";
            var sqlid2="NSProposalInputSql1";
          	var mySql2=new SqlClass();
          	mySql2.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
          	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
          	mySql2.addSubPara(Field.value);//ָ������Ĳ���
          	mySql2.addSubPara(ManageCom);//ָ������Ĳ���
          	mySql2.addSubPara(ManageCom);//ָ������Ĳ���
          	var strSql2=mySql2.getString();
            //alert(strSql2);
            var arrResult2 = easyExecSql(strSql2);
            //alert(arrResult2);
            if (arrResult2 == null) {
              alert("���� ["+ManageCom+"] ��Ȩ¼������ ["+Field.value+"] ��Ͷ����!");
              gotoInputPage();
              return ;
            }
          }
          return;
        }
    }
    catch(ex){}

    //���ѷ�ʽѡ��Ĵ���
    try{
        //alert(cCodeName);
        if(cCodeName.substring(1,cCodeName.indexOf("-")) == 'PayIntv'){
          if (Field.value == "0") {
            //alert("hahha");
            document.all("PayEndYear").readOnly = true;
            document.all("PayEndYear").value = "1000";
            document.all("PayEndYearName").value = "����";
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
   //alert(cCodeName);//mark by yaory ��ʼ�����ֽ���
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
          getRiskInput(Field.value, LoadFlag);//LoadFlag��ҳ���ʼ����ʱ������
        }
        else
        {
          if (tContPlanCode=='false'||tContPlanCode==null||tContPlanCode=='')
            {
                    //���û�б��ռƻ�����,��ѯ��û��Ĭ��ֵ�������Ĭ��ֵҲ��Ҫ���ú�̨��ѯ

                    //var strsql = "select ContPlanCode from LCContPlan where ContPlanCode='00' and ProposalGrpContNo='"+tProposalGrpContNo+"'";
        	    var sqlid3="NSProposalInputSql2";
            	var mySql3=new SqlClass();
            	mySql3.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
            	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
            	mySql3.addSubPara(tProposalGrpContNo);//ָ������Ĳ���
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
                        getRiskInput(Field.value, LoadFlag);//LoadFlag��ҳ���ʼ����ʱ������
                    }

            }
            else
            {
                    //alert("aa");
                    queryRiskPlan( tProposalGrpContNo,Field.value,tContPlanCode,cMainRiskCode );
            }
        }
        //��ɨ���ͼƬ������һҳ
        try {
          goToPic(0);
        }
        catch(ex2){}
          return;
      }

      //�Զ���д��������Ϣ
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
            alert("Ͷ����Ϊ���壬������Ϊ�����ˣ�")
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

        //�շѷ�ʽѡ��
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
    	mySql4.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
    	mySql4.addSubPara(document.all('AppntCustomerNo').value);//ָ������Ĳ���
    	mySql4.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
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
      //���δ���ѡ��
      if(cCodeName == "DutyCode"){
        var index = DutyGrid.mulLineCount;
        //var strSql = "select dutyname from lmduty where dutycode='"+Field.value+"'";
        var sqlid5="NSProposalInputSql4";
    	var mySql5=new SqlClass();
    	mySql5.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
    	mySql5.addSubPara(Field.value);//ָ������Ĳ���
    	var strSql=mySql5.getString();
        var arrResult = easyExecSql(strSql);
        var dutyname= arrResult[0].toString();
        DutyGrid.setRowColData(index-1, 2, dutyname);
        return;
      }
      //alert(cCodeName);
      if(cCodeName == "PolCalRule1"){  //��ʱδ��
        if(Field.value=="1"){       //ͳһ����
          divFloatRate.style.display="none";
          divFloatRate2.style.display="";
        }
        else if(Field.value=="2"){  //Լ�������ۿ�
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
 *  �ύǰ��У�顢����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function beforeSubmit()
{
  if( verifyInput() == false ) return false;
}

/*********************************************************************
 *  ����LoadFlag����һЩFlag����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function convertFlag( cFlag )
{
  //alert("cFlag:" + cFlag);
    if( cFlag == "1"|| cFlag == "8")        // ����Ͷ����ֱ��¼��
    {
        mCurOperateFlag = "1";
        mGrpFlag = "0";
        return;
    }
    if( cFlag == "2" || cFlag == "7" || cFlag == "9" || cFlag == "13"||cFlag == "14"||cFlag == "16"||cFlag == "99"||cFlag=="23")        // �����¸���Ͷ����¼��
    {
        mCurOperateFlag = "1";
        mGrpFlag = "1";
        return;
    }
    if( cFlag == "3"||cFlag == "6" )        // ����Ͷ������ϸ��ѯ
    {
        mCurOperateFlag = "2";
        mGrpFlag = "0";
        return;
    }
    if( cFlag == "4" )      // �����¸���Ͷ������ϸ��ѯ
    {
        mCurOperateFlag = "2";
        mGrpFlag = "1";
        return;
    }
    if( cFlag == "5"||cFlag=="25" )     // ����Ͷ�������˲�ѯ
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
 * ��ȡ�ü���������������Ϣ
 */
function queryGrpPol(prtNo, riskCode) {
  var findFlag = false;

  //����ӡˢ�Ų��ҳ����е��������
  if (arrGrpRisk == null) {
    //var strSql = "select GrpProposalNo, RiskCode from LCGrpPol where PrtNo = '" + prtNo + "'";
    var sqlid6="NSProposalInputSql5";
	var mySql6=new SqlClass();
	mySql6.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
	mySql6.addSubPara(prtNo);//ָ������Ĳ���
	var strSql=mySql6.getString();
    arrGrpRisk  = easyExecSql(strSql);

    //ͨ���б�����������ҵ�����
    for (i=0; i<arrGrpRisk.length; i++) {
//      strSql = "select SubRiskFlag from LMRiskApp where RiskCode = '"
//             + arrGrpRisk[i][1] + "' and RiskVer = '2002'";
      var sqlid7="NSProposalInputSql6";
  	  var mySql7=new SqlClass();
  	  mySql7.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
  	  mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
  	  mySql7.addSubPara(arrGrpRisk[i][1]);//ָ������Ĳ���
  	   strSql=mySql7.getString();
      var riskDescribe = easyExecSql(strSql);

      if (riskDescribe == "M") {
        top.mainRisk = arrGrpRisk[i][1];
        break;
      }
    }
  }
  //alert(arrGrpRisk);

  //��ȡѡ������ֺͼ���Ͷ��������
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
 * �������֤�����ȡ��������
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
 * У�������հ�����ϵ
 */
function checkRiskRelation(tPolNo, cRiskCode) {
  // �����¸���Ͷ����
  if (mGrpFlag == "1") {
//    var strSql = "select RiskCode from LCGrpPol where GrpProposalNo = '" + tPolNo
//               + "' and trim(RiskCode) in trim((select Code1 from LDCode1 where Code = '" + cRiskCode + "'))";
      var sqlid8="NSProposalInputSql7";
	  var mySql8=new SqlClass();
	  mySql8.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	  mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
	  mySql8.addSubPara(tPolNo);//ָ������Ĳ���
	  mySql8.addSubPara(cRiskCode);//ָ������Ĳ���
	  var strSql=mySql8.getString();
    return true;
  }
  else {
//    var strSql = "select RiskCode from LCPol where PolNo = '" + tPolNo
//               + "' and trim(RiskCode) in trim((select Code1 from LDCode1 where Code = '" + cRiskCode + "'))";
	  var sqlid9="NSProposalInputSql8";
	  var mySql9=new SqlClass();
	  mySql9.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	  mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
	  mySql9.addSubPara(tPolNo);//ָ������Ĳ���
	  mySql9.addSubPara(cRiskCode);//ָ������Ĳ���
	  var strSql=mySql9.getString();
  }

  return easyQueryVer3(strSql);
}

/**
 * �����ص�����ѡ�����
 */
function gotoInputPage() {
  // �����¸���Ͷ����
  if (mGrpFlag == "1") {
    //top.fraInterface.window.location = "../app/ProposalGrpInput.jsp?LoadFlag=" + LoadFlag;
    top.fraInterface.window.location = "../app/ProposalInput.jsp?LoadFlag=" + LoadFlag;
  }
  // ������ɨ���Ͷ����
  else if (typeof(prtNo)!="undefined" && typeof(type)=="undefined") {
    top.fraInterface.window.location = "../app/ProposalInput.jsp?prtNo=" + prtNo;
  }
  // ������ɨ���Ͷ����
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
 *  ���ݲ�ͬ������,��ȡ��ͬ�Ĵ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
var isReturnFromRiskPage = false;

function getRiskInput(cRiskCode, cFlag) {

    var tPolNo = "";
  //alert("cFlag=="+cFlag);
    convertFlag(cFlag);
  //alert("mGrpFlag=="+mGrpFlag);

    var urlStr = "";
    // �״ν��뼯���¸���¼��
//var rSql = "select risksortvalue from lmrisksort where risksorttype='00' and riskcode='"+cRiskCode+"'";
var sqlid10="NSProposalInputSql9";
var mySql10=new SqlClass();
mySql10.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
mySql10.addSubPara(cRiskCode);//ָ������Ĳ���
var rSql=mySql10.getString();
var RiskInterface = easyExecSql(rSql);
var tFileUrl;
//alert(RiskInterface+ ".jsp?riskcode="+ cRiskCode);
    if( mGrpFlag == "0" )       // ����Ͷ����
    {
        urlStr = "../riskinput/Risk" + RiskInterface+ ".jsp?riskcode="+ cRiskCode;
        tFileUrl="../riskinput/Risk" + RiskInterface+ ".jsp";
      }
    if( mGrpFlag == "1" )       // �����¸���Ͷ����
    {
        urlStr = "../riskgrp/Risk" + RiskInterface+ ".jsp?riskcode="+ cRiskCode;
        tFileUrl="../riskgrp/Risk" + RiskInterface+ ".jsp";
      }
    if( mGrpFlag == "3" )       // ����Ͷ��������
    {
        urlStr = "../riskinput/Risk" + RiskInterface+ ".jsp?riskcode="+ cRiskCode;
        tFileUrl="../riskinput/Risk" + RiskInterface+ ".jsp";
      }

   //�Ƿ���ҵ�ָ����������������ļ�

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
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    //��ȡ���ֵĽ�������
    //showInfo = window.showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;dialogTop:-800;dialogLeft:-800;resizable=1");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=1;      //�������ڵĿ��; 
	var iHeight=1;     //�������ڵĸ߶�; 
	var iTop = 1; //��ô��ڵĴ�ֱλ�� 
	var iLeft = 1;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	if(isReturnFromRiskPage)
		   returnFromRiskInput();
	   return;
}

function returnFromRiskInput(cRiskCode){
    //���ղ�ͬ��LoadFlag���в�ͬ�Ĵ���
    //�õ�����ĵ���λ��,Ĭ��Ϊ1,��ʾ���˱���ֱ��¼��.
    /**********************************************
     *LoadFlag=1 -- ����Ͷ����ֱ��¼��
     *LoadFlag=2 -- �����¸���Ͷ����¼��
     *LoadFlag=3 -- ����Ͷ������ϸ��ѯ
     *LoadFlag=4 -- �����¸���Ͷ������ϸ��ѯ
     *LoadFlag=5 -- ����
     *LoadFlag=6 -- ��ѯ
     *LoadFlag=7 -- ��ȫ�±�����
     *LoadFlag=8 -- ��ȫ����������
     *LoadFlag=9 -- ������������
     *LoadFlag=10-- ��������
     *LoadFlag=13-- ������Ͷ���������޸�
     *LoadFlag=16-- ������Ͷ������ѯ
     *LoadFlag=25-- �˹��˱��޸�Ͷ����
     *LoadFlag=99-- �涯����
     *
     ************************************************/
//  alert("LoadFlag=="+LoadFlag);
  if(LoadFlag=="1"){
  //alert(cRiskCode);
  ////У���ǲ������� add by yaory
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
        mySql11.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
        mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
        mySql11.addSubPara(cRiskCode);//ָ������Ĳ���
        mySql11.addSubPara(aGrpContNo);//ָ������Ĳ���
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
   	  alert("��ѯ��������ʧ��");
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
    //��ʼ��������Ϣ
      emptyForm();

    //�������󵥷ſ���������ֻ�������ƣ�by Minim at 2003-11-24
    document.all("SaleChnl").readOnly = false;
    document.all("SaleChnl").className = "code";
    document.all("SaleChnl").ondblclick= showSaleChnl;

    //��ɨ���¼��
    if (typeof(type)!="undefined" && type=="noScan") {
//      document.all("PrtNo").readOnly = false;
//      document.all("PrtNo").className = "common";

      //ͨ���б�����������ҵ�����
//      strSql = "select SubRiskFlag from LMRiskApp where RiskCode = '"
//             + cRiskCode + "' and RiskVer = '2002'";
      
      var sqlid12="NSProposalInputSql11";
      var mySql12=new SqlClass();
      mySql12.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
      mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
      mySql12.addSubPara(cRiskCode);//ָ������Ĳ���
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

    //�������ֺ�ӡˢ����Ϣ
    document.all("RiskCode").value = cRiskCode;
        //alert(fm.RiskCode.value);
    //�������ƶ���ӡˢ�ţ��Է����涯¼��
    //document.all("PrtNo").focus();

  //��ʼ���������������
  try {
    //alert("prtNo=="+prtNo);
    prtNo = document.all("PrtNo").value;
    //MS������ȡ���Ա���Ϊ��
    
//     var  strSaleSql = "select salechnl from lccont  where contno = '"
//             + fm.ContNo.value + "'";
     var sqlid13="NSProposalInputSql12";
     var mySql13=new SqlClass();
     mySql13.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
     mySql13.setSqlId(sqlid13);//ָ��ʹ�õ�Sql��id
     mySql13.addSubPara(fm.ContNo.value);//ָ������Ĳ���
     var strSaleSql=mySql13.getString();
      var SaleChnl = easyExecSql(strSaleSql);
    if(SaleChnl==null || SaleChnl =='')
    {
    	  alert("��ѯ��������������ʧ��");
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
//    //���Ƿ�ָ����Ч������¼��ʱʧЧ
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
  if( mCurOperateFlag == "1" ) {             // ¼��
        // �����¸���Ͷ����
        //alert("mGrpFlag:"+mGrpFlag);
        if( mGrpFlag == "1" )   {
            getGrpPolInfo();                       // ���뼯�岿����Ϣ
            getPayRuleInfo();

            //֧�ּ����¸��ˣ�¼�����֤�Ŵ�����������
            document.all("IDNo").onblur = grpGetBirthdayByIdno;
            //��ʱ��ȥ��ȥ���������ť,�涯����ʱ����   hl
//          if(LoadFlag!="99")
//          inputQuest.style.display = "none";

            // ��ȡ�ü���������������Ϣ
            //alert("judging if the RiskCode input has been input in group info.");
            //if (!queryGrpPol(document.all("PrtNo").value, cRiskCode))   {
            //  alert("�����ŵ�û��¼��������֣������¸��˲�����¼�룡");
            //  document.all("RiskCode").value="";
            //  //alert("now go to the new location- ProposalGrpInput.jsp");
            //  top.fraInterface.window.location = "./ProposalGrpInput.jsp?LoadFlag=" + LoadFlag;
            //  //alert("top.location has been altered");
            //  return false; //hezy
            //}
        }
        //if(initDealForSpecRisk(cRiskCode)==false)//���������ڳ�ʼ��ʱ�����⴦��-houzm���
        //{
            //alert("���֣�"+cRiskCode+"��ʼ��ʱ���⴦��ʧ�ܣ�");
            //return false;
        //}



        //���������ڳ�ʼ��ʱ�����⴦����չ-guoxiang add at 2004-9-6 16:33
    if(initDealForSpecRiskEx(cRiskCode)==false)
        {
            alert("���֣�"+cRiskCode+"��ʼ��ʱ���⴦��ʧ�ܣ�");
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
          if(document.all('SelPolNo').value!=''){ //��������
           // var tSql="select riskcode from lcpol where polno='"+document.all('SelPolNo').value+"'";
            var sqlid14="NSProposalInputSql13";
            var mySql14=new SqlClass();
            mySql14.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
            mySql14.setSqlId(sqlid14);//ָ��ʹ�õ�Sql��id
            mySql14.addSubPara(document.all('SelPolNo').value);//ָ������Ĳ���
            var tSql=mySql14.getString();
            var arr=easyExecSql(tSql);            
            if(arr[0]!=cRiskCode){
              document.all('SelPolNo').value='';
            }
          }
      }
      catch(ex) {}
        //alert("selPolNo:"+document.all('SelPolNo').value);
        if( isSubRisk( cRiskCode ) == true&&document.all('SelPolNo').value=="" ) {   // ����
          //��������������գ�����ջ�������ձ�����
          if (LoadFlag != "8" && LoadFlag != "2") {
              //top.mainPolNo = ""; //hezy add
              mainRiskPolNo = "";
            }
            tPolNo = getMainRiskNo(cRiskCode);   //����¼�븽�յĴ���,�õ����ձ�������
            //alert("tPolNo:"+tPolNo);
            /*if (!checkRiskRelation(tPolNo, cRiskCode)) {
              alert("�����հ�����ϵ������������պŲ��ܴ���������գ�");
              gotoInputPage();
              //top.mainPolNo = "";
              mainRiskPolNo = "";
              return false;
            }*/
//-----------------------------------------------------------------------
         if(cRiskCode=='121301'||cRiskCode=='321601')//��ʼ������ĸ�����Ϣ--houzm���--�ɵ������Ϊһ������
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
                //��ʼ��������Ϣ
                        if (!initPrivateRiskInfo(tPolNo))
                        {
                          gotoInputPage();
                          return false;
                        }
         }

//          try {}  catch(ex1) { alert( "��ʼ�����ֳ���" + ex1 );   }
        } // end of ����if

      //alert("ManageCom=="+ManageCom);
       //return false;

    } // end of ¼��if


    mCurOperateFlag = "";
    mGrpFlag = "";
    
    // �ص���ѯҳ��ProposalQueryDetail.jsp���и�ֵ���޲�ѯ��ֵʱ�����쳣����
    try{parent.fraTitle.setValue();}catch(e){}
    return;
}

/*********************************************************************
 *  �жϸ������Ƿ��Ǹ���,������ȷ���ȿ���������,�ֿ��������յĴ���
 *  ����  ��  ���ִ���
 *  ����ֵ��  ��
 *********************************************************************
 */
function isSubRisk(cRiskCode) {
  //alert(cRiskCode);
  if (cRiskCode=="")
  {
   return false;
  }
  var arrQueryResult = easyExecSql("select SubRiskFlag from LMRiskApp where RiskCode='" + cRiskCode + "'", 1, 0);

    if(arrQueryResult[0] == "S")    //��Ҫת�ɴ�д
        return true;
    if(arrQueryResult[0] == "M")
        return false;

    if (arrQueryResult[0].toUpperCase() == "A")
    {
        if (confirm("�����ּȿ���������,�ֿ����Ǹ���!ѡ��ȷ����������¼��,ѡ��ȡ�����븽��¼��"))
            return false;
        else
            return true;
  }
    return false;
}

/*********************************************************************
 *  ����¼�븽�յĴ���,�õ����ձ�������
 *  ����  ��  ���ִ���
 *  ����ֵ��  ��
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
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	tPolNo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	tPolNo.focus();
    //top.mainPolNo = tPolNo;
    mainRiskPolNo = tPolNo;
  }

    return tPolNo;
}

/*********************************************************************
 *  ��ʼ��������Ϣ
 *  ����  ��  Ͷ������
 *  ����ֵ��  ��
 *********************************************************************
 */
function initPrivateRiskInfo(cPolNo) {
    if(cPolNo=="") {
        alert("û�����ձ�����,���ܽ��и�����¼��!");
        mCurOperateFlag="";        //��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.
        return false
    }

    var arrLCPol = new Array();
    var arrQueryResult = null;
    // ��������Ϣ����
//    var sql = "select * from lcpol where polno='" + cPolNo + "' "
//            + "and riskcode in "
//            + "( select riskcode from LMRiskApp where SubRiskFlag = 'M' )";
    var sqlid15="NSProposalInputSql14";
    var mySql15=new SqlClass();
    mySql15.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql15.setSqlId(sqlid15);//ָ��ʹ�õ�Sql��id
    mySql15.addSubPara(cPolNo);//ָ������Ĳ���
    var sql=mySql15.getString(); 

    arrQueryResult = easyExecSql( sql , 1, 0);

    if (arrQueryResult == null) {
        mCurOperateFlag="";        //��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.

        //top.mainPolNo = "";
        mainRiskPolNo = "";

        alert("��ȡ������Ϣʧ��,���ܽ��и�����¼��!");
        return false
    }

    arrLCPol = arrQueryResult[0];
    displayPol( arrLCPol );

    document.all("MainPolNo").value = cPolNo;
    var tAR;

    //Ͷ������Ϣ
    if (arrLCPol[6]=="2") {     //����Ͷ������Ϣ
      arrQueryResult = null;
      //arrQueryResult = easyExecSql("select * from lcappntgrp where polno='"+cPolNo+"'"+" and grpno='"+arrLCPol[28]+"'", 1, 0);
      arrQueryResult = easyExecSql("select * from lcgrpappnt where grpcontno='"+arrLCPol[0]+"'"+" and customerno='"+arrLCPol[28]+"'", 1, 0);
      tAR = arrQueryResult[0];
      displayPolAppntGrp(tAR);
    } else {                     //����Ͷ������Ϣ
     /* arrQueryResult = null;
      arrQueryResult = easyExecSql("select * from lcappntind where polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[28]+"'", 1, 0);
      tAR = arrQueryResult[0];
      displayPolAppnt(tAR);*/
    }

    // ��������Ϣ����
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
 *  У��Ͷ����������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function verifyProposal() {
  var passVerify = true;

  //��ȫ����������
  if (LoadFlag == "8") {
    var newCValidate = document.all('CValiDate').value;
    /*
    if (oldCValidate.substring(4) != newCValidate.substring(4)) {
      alert("ָ������Ч���ڱ�����������Ч���ڵ������Ӧ��");
      return false;
    }*/

    //���������������ղ�����ô����
    //if (oldCValidate == newCValidate) {
    //  alert("�������������չ��򣬲���ָ��Ϊ������Ч����");
    //  return false;
    //}

    //if (!confirm("ָ���ĸ�������Ч����Ϊ��(" + newCValidate + ")��ȷ����")) {
    //  return false;
    //}
  }

  if(fm.AppntRelationToInsured.value=="00"){
    if(fm.AppntCustomerNo.value!= fm.CustomerNo.value){
      alert("Ͷ�����뱻���˹�ϵΪ���ˣ����ͻ��Ų�һ��");
      return false;
    }
  }
   if(needVerifyRiskcode()==true)
   {
        //if(verifyInput() == false) passVerify = false;

        BnfGrid.delBlankLine();

        if(BnfGrid.checkValue("BnfGrid") == false) passVerify = false;

         //У�鵥֤�Ƿ񷢷Ÿ�ҵ��Ա
         if (!verifyPrtNo(document.all("PrtNo").value)) passVerify = false;
    }
    try {
      var strChkIdNo = "";

          //��������Ա�У�����֤��
          if (document.all("AppntIDType").value == "0")
            strChkIdNo = chkIdNo(document.all("AppntIDNo").value, document.all("AppntBirthday").value, document.all("AppntSex").value);
          if (document.all("IDType").value == "0")
            strChkIdNo = chkIdNo(document.all("IDNo").value, document.all("Birthday").value, document.all("Sex").value);

          if (strChkIdNo != "") {
            alert(strChkIdNo);
            passVerify = false;


      }

      //У��ְҵ��ְҵ����
//    var arrCode = new Array();
//    arrCode = verifyCode("ְҵ�����֣�", document.all("AppntWorkType").value, "code:OccupationCode", 1);
//    if (arrCode!=true && document.all("AppntOccupationCode").value!=arrCode[0]) {
//      alert("Ͷ����ְҵ��ְҵ���벻ƥ�䣡");
//      passVerify = false;
//    }
//    arrCode = verifyCode("ְҵ�����֣�", document.all("WorkType").value, "code:OccupationCode", 1);
//    if (arrCode!=true && document.all("OccupationCode").value!=arrCode[0]) {
//      alert("������ְҵ��ְҵ���벻ƥ�䣡");
//      passVerify = false;
//    }

      //У���������
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
            alert("��������������˳�� " + i + " �����������Ϊ��" + sumLiveBnf[i]+ " ������100%�������ύ��");
            passVerify = false;
        }
        else if (typeof(sumLiveBnf[i])!="undefined" && sumLiveBnf[i]<1)
        {
            alert("ע�⣺��������������˳�� " + i + " �����������Ϊ��" + sumLiveBnf[i] + " ��С��100%");
            passVerify = false;
        }
      }

      for (i=0; i<sumDeadBnf.length; i++)
      {
        if (typeof(sumDeadBnf[i])!="undefined" && sumDeadBnf[i]>1)
        {
          alert("��������������˳�� " + i + " �����������Ϊ��" + sumDeadBnf[i] + " ������100%�������ύ��");
          passVerify = false;
        }
        else if (typeof(sumDeadBnf[i])!="undefined" && sumDeadBnf[i]<1)
        {
            alert("ע�⣺��������������˳�� " + i + " �����������Ϊ��" + sumDeadBnf[i] + " ��С��100%");
            passVerify = false;
        }
      }


      if (trim(fm.BankCode.value)=="0101")
      {
        if (trim(fm.BankAccNo.value).length!=19 || !isInteger(trim(fm.BankAccNo.value)))
        {
            alert("�������е��˺ű�����19λ�����֣����һ���Ǻţ�*����Ҫ��\n����ͻ���д������һ�����������");
            passVerify = false;
        }
      }

    //У��ͻ��Ƿ�����
    if (fm.AppntCustomerNo.value!="" && isDeath(fm.AppntCustomerNo.value)) {
      alert("Ͷ�����Ѿ�������");
      passVerify = false;
    }

    if (fm.CustomerNo.value!="" && isDeath(fm.CustomerNo.value)) {
      alert("�������Ѿ�������");
      passVerify = false;
    }
    }
    catch(e) {}

    if (!passVerify) {
      if (!confirm("Ͷ����¼������д����Ƿ�������棿")) return false;
      else return true;
    }
}

//У��ͻ��Ƿ�����
function isDeath(CustomerNo) {
  //var strSql = "select DeathDate from LDPerson where CustomerNo='" + CustomerNo + "'";
  var sqlid16="NSProposalInputSql15";
  var mySql16=new SqlClass();
  mySql16.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
  mySql16.setSqlId(sqlid16);//ָ��ʹ�õ�Sql��id
  mySql16.addSubPara(CustomerNo);//ָ������Ĳ���
  var strSql=mySql16.getString();
  var arrResult = easyExecSql(strSql);

  if (arrResult == ""||arrResult == null) return false;
  else return true;
}

/*********************************************************************
 *  �������Ͷ�������ύ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function submitForm() {

  if(!chkDutyNo()){                  //У��CheckBox��ֻ����ͬʱѡ��һ��������
                                                                             //create by malong 2005-7-13
       return false;
    }
    if(!chkMult() || !checkMult())      //�жϷ����Ƿ�Ϊ�ջ����� chkMult�����ڶ���������,checkMult�����ڵ���������,
                                                                            //create by malong 2005-7-8
  {
     // alert("here!");
     return false;
  }


    if(sign != 0)
    {
       alert("�벻Ҫ�ظ����!");
       return;
    }

    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

  var verifyGrade = "1";


  //������������������⴦������
  //modify by malong 2005-7-8
  try {

     if(specDealByRisk()==false)
      return;

    }
     catch(e){}

  //����������ڣ�����գ������֤��ȡ
  //try {checkBirthday(); } catch(e){}

    // У�鱻�����Ƿ�ͬͶ���ˣ���ͬ����и���
  try { verifySamePerson(); } catch(e) {}

        // У��¼������
    if( verifyProposal() == false ) return;

    if (trim(document.all('ProposalNo').value) != "") {
      alert("��Ͷ�������Ѿ����ڣ��������ٴ������������½���¼����棡");
      return false;
    }
    if(checkSameRiskCode()==false)
    {
       alert("�˱���������δ��ֹ�Ĵ����֣������Խ����ظ�����");
       return false;
    	}
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
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

    //Ϊ��ȫ���ӣ�add by Minim
    if (LoadFlag=="7" || LoadFlag=="8") {
      document.all('MainPolNo').value=top.opener.document.all('PolNo').value;
      //alert(document.all('MainPolNo').value);
      fm.action="../app/ProposalSave.jsp?BQFlag=2&EdorType="+EdorType+"&LoadFlag="+LoadFlag;
      //document.all("BQFlag").value=BQFlag;
    }

    //Ϊ���������������ӣ�add by Minim
    if (LoadFlag=="9") {
      fm.action="../app/ProposalSave.jsp?BQFlag=4&MasterPolNo=" + parent.VD.gVSwitch.getVar('MasterPolNo');
      //alert(fm.action);return;
    }
    sign = 1;
    //alert("payIntv:"+document.all('PayIntv').value);
  //beforeSubmit();
    fm.submit(); //�ύ
    sign = 0;
}

/**
 * ǿ�ƽ������
 */
function unLockTable() {
  if (fm.PrtNo.value == "") {
    alert("��Ҫ��дӡˢ�ţ�");
    return;
  }

  var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + fm.PrtNo.value + "&CreatePos=�б�¼��&PolState=1002&Action=DELETE";
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=0;      //�������ڵĿ��; 
  var iHeight=0;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
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
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
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
          if (confirm("����ɹ���\nҪ�����ӡˢ�ŵ��������ø�����Ա�ܲ�����")) {
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
        content = "����ɹ���";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
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
        //��ʱ��������Ͷ�������룬���㸽�յ�¼�룬����ѡ��ɨ�����ʧЧ
        //try { if (top.mainPolNo == "") top.mainPolNo = document.all("ProposalNo").value } catch(e) {}
        //try { if (mainRiskPolNo == "") mainRiskPolNo = document.all("ProposalNo").value } catch(e) {alert("err");}
    }

    //�б��ƻ������������ֹ�ĺ�������
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
 *  �������Ͷ�������ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
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
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
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
          if (confirm("����ɹ���\nҪ�����ӡˢ�ŵ��������ø�����Ա�ܲ�����")) {
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
        content = "����ɹ���";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
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

        //��ʱ��������Ͷ�������룬���㸽�յ�¼�룬����ѡ��ɨ�����ʧЧ
        //try { if (top.mainPolNo == "") top.mainPolNo = document.all("ProposalNo").value } catch(e) {}
        //try { if (mainRiskPolNo == "") mainRiskPolNo = document.all("ProposalNo").value } catch(e) {alert("err");}
    }

    //�б��ƻ������������ֹ�ĺ�������
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
 *  "����"��ť��Ӧ����
 *  ����  ��  ��
 *  ����ֵ��  ��
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
        alert("��ProposalInput.js-->resetForm�����з����쳣:��ʼ���������!");
    }
}

/*********************************************************************
 *  "ȡ��"��ť��Ӧ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function cancelForm()
{
    //showDiv(operateButton,"true");
    //showDiv(inputButton,"false");
}

/*********************************************************************
 *  ��ʾfrmSubmit��ܣ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
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
 *  Click�¼������������ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function addClick()
{
    //����������Ӧ�Ĵ���
    //showDiv( operateButton, "false" );
    //showDiv( inputButton, "true" );
}

/*********************************************************************
 *  Click�¼������������ѯ��ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
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
 *  Click�¼�����������޸ġ�ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
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
                  alert("Ͷ����Ϊ���壬������Ϊ�����ˣ�")

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
        alert( "������Ͷ������ѯ�������ٽ����޸�!" );
    else {
        // У��¼������
        if (document.all('DivLCInsured').style.display == "none") {
      for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {
          if (fm.elements[elementsNum].verify != null && fm.elements[elementsNum].name.indexOf("Appnt") != -1) {
            fm.elements[elementsNum].verify = "";
          }
        }
    }

        if( verifyProposal() == false ) return;
             if(chkMult()==false)  return;
        // У�鱻�����Ƿ�ͬͶ���ˣ���ͬ����и���
    try { verifySamePerson(); } catch(e) {}

        var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

        if( mAction == "" ) {
            //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
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
            //�б��ƻ����(����Ͷ����״̬���䣺����״̬���˱�״̬)
            if (typeof(window.ChangePlanSub) == "object") document.all('fmAction').value = "ChangePlan" + document.all('fmAction').value;
            //�޸ĸ�������(����Ͷ����״̬���䣺����״̬���˱�״̬,���ñȳб��ƻ������һ����޸ĸ������ʣ�ΪȨ�޿���)
            if(LoadFlag=="10") document.all('fmAction').value = "ChangePlan" + document.all('fmAction').value;
            //yaoryif(LoadFlag=="3") document.all('fmAction').value = "Modify" + document.all('fmAction').value;
            //inputQuestButton.disabled = false;
            fm.submit(); //�ύ
        }

        try {
          if (typeof(top.opener.modifyClick) == "object") top.opener.initQuery();
        }
        catch(e) {
        }
    }
}

/*********************************************************************
 *  Click�¼����������ɾ����ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function deleteClick() {
    var tProposalNo = document.all('ProposalNo').value;

    if(tProposalNo==null || tProposalNo=="") {
        alert( "������Ͷ������ѯ�������ٽ���ɾ��!" );
    }
    else {
        var showStr = "����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

        if( mAction == "" ) {
            //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            mAction = "DELETE";
            document.all( 'fmAction' ).value = mAction;
            fm.submit(); //�ύ
        }
    }
}

/*********************************************************************
 *  Click�¼����������ѡ�����Ρ�ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function chooseDuty()
{
    cRiskCode = fm.RiskCode.value;
    cRiskVersion = fm.RiskVersion.value

    if( cRiskCode == "" || cRiskVersion == "" )
    {
        alert( "��������¼�����ֺ����ְ汾�����޸ĸ�Ͷ�����������" );
        return false
    }

    showInfo = window.open("./ChooseDutyInput.jsp?RiskCode="+cRiskCode+"&RiskVersion="+cRiskVersion);
    return true
}

/*********************************************************************
 *  Click�¼������������ѯ������Ϣ��ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showDuty()
{
    //����������Ӧ�Ĵ���
    cPolNo = fm.ProposalNo.value;
    if( cPolNo == "" )
    {
        alert( "�������ȱ���Ͷ�������ܲ鿴��Ͷ�����������" );
        return false
    }

    var showStr = "���ڲ�ѯ���ݣ������Ժ�......";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //showModalDialog( "./ProposalDuty.jsp?PolNo="+cPolNo,window,"status:no;help:0;close:0;dialogWidth=18cm;dialogHeight=14cm");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=18+"cm";      //�������ڵĿ��; 
	var iHeight=14+"cm";     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo1 = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo1.focus();
    showInfo.close();
}

/*********************************************************************
 *  Click�¼���������������ݽ�����Ϣ��ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showFee()
{
    cPolNo = fm.ProposalNo.value;
    var prtNo = fm.PrtNo.value;

    if( cPolNo == "" )
    {
        alert( "�������ȱ���Ͷ�������ܽ����ݽ�����Ϣ���֡�" );
        return false
    }

    showInfo = window.open( "./ProposalFee.jsp?PolNo=" + cPolNo + "&polType=PROPOSAL&prtNo=" + prtNo );
}

/*********************************************************************
 *  Click�¼�����˫����Ͷ���˿ͻ��š�¼���ʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
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
 *  Click�¼�����˫���������˿ͻ��š�¼���ʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
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
 *  Click�¼�����˫�������������˿ͻ��š�¼���ʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
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
 *  �������е�������ʾ��Ͷ���˲���
 *  ����  ��  ���˿ͻ�����Ϣ
 *  ����ֵ��  ��
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
 *  �ѱ����е�Ͷ������Ϣ��ʾ��Ͷ���˲���
 *  ����  ��  ���˿ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayPolAppnt(cArr)
{
    // ��LCAppntInd��ȡ����
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
 *  �ѱ����е�Ͷ����������ʾ��Ͷ���˲���
 *  ����  ��  ����ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayPolAppntGrp( cArr )
{

    /*
    // ��LCAppntGrp��ȡ����
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
 *  �ѱ����еı�����������ʾ�������˲���
 *  ����  ��  �ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayPolInsured(cArr)
{
    // ��LCInsured��ȡ����
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
 *  �Ѳ�ѯ���صĿͻ�������ʾ�����������˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
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
 *  ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
 *  ����  ��  ��ѯ���صĶ�ά����
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterQuery( arrQueryResult ) {
    if( arrQueryResult != null ) {
        arrResult = arrQueryResult;

        if( mOperate == 1 ) {           // ��ѯ������ϸ
            //alert("abc");
            var tPolNo = arrResult[0][0];

            // ��ѯ������ϸ
            queryPolDetail( tPolNo );
        }

        if( mOperate == 2 ) {       // Ͷ������Ϣ
            arrResult = easyExecSql("select * from LDPerson where CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
            if (arrResult == null) {
              alert("δ�鵽Ͷ������Ϣ");
            } else {
               displayAppnt(arrResult[0]);
            }

      }
        if( mOperate == 3 ) {       // ����������Ϣ
            arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ),b.PostalAddress,b.ZipCode,b.Phone,b.HomePhone from LDPerson a Left Join LCAddress b On b.CustomerNo =a.CustomerNo Where 1=1 and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
            if (arrResult == null) {
              alert("δ�鵽����������Ϣ");
            } else {
               displayInsured(arrResult[0]);
            }

      }
        if( mOperate == 4 ) {       // ������������Ϣ
            displaySubInsured(arrResult[0]);
      }
    }
    mOperate = 0;       // �ָ���̬

    emptyUndefined();
}

/*********************************************************************
 *  ���ݲ�ѯ���ص���Ϣ��ѯͶ������ϸ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryPolDetail( cPolNo )
{
    emptyForm();
    //alert("ccc");
    //var showStr = "���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    //var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    //parent.fraSubmit.window.location = "./ProposalQueryDetail.jsp?PolNo=" + cPolNo;
    parent.fraTitle.window.location = "./ProposalQueryDetail.jsp?PolNo=" + cPolNo;
}


/*********************************************************************
 *  ���ݲ�ѯ���ص���Ϣ��ѯ���ֵı��ռƻ���ϸ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryRiskPlan( tProposalGrpContNo,tRiskCode,tContPlanCode,tMainRiskCode )
{
    emptyForm();
    //var showStr = "���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    //var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //alert("./ProposalQueryRiskPlan.jsp?ProposalGrpContNo="
    //                                                                      + tProposalGrpContNo+"&RiskCode="+tRiskCode+"&ContPlanCode="+tContPlanCode);
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    //parent.fraSubmit.window.location = "./ProposalQueryDetail.jsp?PolNo=" + cPolNo;
    parent.fraSubmit.window.location = "./ProposalQueryRiskPlan.jsp?ProposalGrpContNo="
                                                                            + tProposalGrpContNo+"&RiskCode="+tRiskCode+"&ContPlanCode="+tContPlanCode+"&MainRiskCode="+tMainRiskCode;
}
/*********************************************************************
 *  ��ʾdiv
 *  ����  ��  ��һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
 *  ����ֵ��  ��
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
//�����˿ͻ��Ų�ѯ��Ť�¼�
function queryInsuredNo() {
  if (document.all("CustomerNo").value == "") {
    showInsured1();
  //} else if (LoadFlag != "1" && LoadFlag != "2") {
  //  alert("ֻ����Ͷ����¼��ʱ���в�����");
  }  else {
      arrResult=easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ),b.PostalAddress,b.ZipCode,b.Phone,b.HomePhone from LDPerson a Left Join LCAddress b On b.CustomerNo =a.CustomerNo Where 1=1 and a.CustomerNo = '" + document.all("CustomerNo").value + "'", 1, 0);
    if (arrResult == null) {
      alert("δ�鵽����������Ϣ");
      displayInsured(new Array());
      emptyUndefined();
    } else {

      displayInsured(arrResult[0]);
    }
  }
}

//*************************************************************
//Ͷ���˿ͻ��Ų�ѯ��Ť�¼�
function queryAppntNo() {
  if (document.all("AppntCustomerNo").value == "" && LoadFlag == "1") {
    showAppnt1();
  //} else if (LoadFlag != "1" && LoadFlag != "2") {
  //  alert("ֻ����Ͷ����¼��ʱ���в�����");
  } else {
    arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ),b.PostalAddress,b.ZipCode,b.Phone,b.HomePhone from LDPerson a Left Join LCAddress b On b.CustomerNo =a.CustomerNo Where 1=1 and a.CustomerNo = '" + document.all("AppntCustomerNo").value + "'", 1, 0);
    if (arrResult == null) {
      alert("δ�鵽Ͷ������Ϣ");
      displayAppnt(new Array());
      emptyUndefined();
    } else {
      displayAppnt(arrResult[0]);
    }
  }
}

//*************************************************************
//Ͷ�����뱻������ͬѡ����¼�
function isSamePerson() {
  //��Ӧδѡͬһ�ˣ��ִ򹳵����
  if (fm.AppntRelationToInsured.value!="00" && fm.SamePersonFlag.checked==true) {
    document.all('DivLCInsured').style.display = "";
    fm.SamePersonFlag.checked = false;
    alert("Ͷ�����뱻���˹�ϵ���Ǳ��ˣ����ܽ��иò�����");
  }
  //��Ӧ��ͬһ�ˣ��ִ򹳵����
  else if (fm.SamePersonFlag.checked == true) {
    document.all('DivLCInsured').style.display = "none";
  }
  //��Ӧ��ѡͬһ�˵����
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
//����ʱУ��Ͷ�����뱻������ͬѡ����¼�
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
 *  �������е�������ʾ��Ͷ���˲���
 *  ����  ��  ���˿ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayAppnt()
{
    // ��LDPerson��ȡ����
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
 *  �������е�������ʾ��Ͷ���˲���
 *  ����  ��  ����ͻ�����Ϣ
 *  ����ֵ��  ��
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
//������ldgrp��
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
    // ��LDGrp��ȡ����
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
 *  �Ѳ�ѯ���صĿͻ�������ʾ�������˲���
 *  ����  ��  �ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayInsured()
{
    // ��LDPerson��ȡ����
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

//�����¼��
function QuestInput()
{
    cContNo = document.all("ContNo").value;  //��������
    //alert("cContNo:"+cContNo)
        if(LoadFlag=="2"||LoadFlag=="4"){
            if(mSwitch.getVar( "ProposalGrpContNo" )==""){
              alert("���޼����ͬͶ�����ţ����ȱ���!");
                }
                else{
            window.open("./GrpQuestInputMain.jsp?GrpContNo="+mSwitch.getVar( "ProposalGrpContNo" )+"&Flag="+LoadFlag);
                    }
            }
            else{
                    if(cContNo == ""){
                   alert("���޺�ͬͶ�����ţ����ȱ���!");
                     }
                     else
                    {
                window.open("../uw/QuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag,"window1");
                    }
            }
}
function QuestQuery()
{

   cContNo = document.all("ContNo").value;  //��������

   if(LoadFlag=="2"||LoadFlag=="4"||LoadFlag=="13"){
    if(mSwitch.getVar( "ProposalGrpContNo" )==""||mSwitch.getVar( "ProposalGrpContNo" )==null)
    {
        alert("����ѡ��һ����������Ͷ����!");
        return ;
        }
        else{
            window.open("./GrpQuestQueryMain.jsp?GrpContNo="+mSwitch.getVar( "ProposalGrpContNo" )+"&Flag="+LoadFlag);
        }
   }
   else{
        if(cContNo == ""){
           alert("���޺�ͬͶ�����ţ����ȱ���!");
     }
    else{
               window.open("../uw/QuestQueryMain.jsp?ContNo="+cContNo+"&Flag="+LoadFlag,"window1");
        }
   }
}
//��ʾͶ��������
function showAppntAge() {
  var age = calAge(document.all("AppntBirthday").value);
  var today = new Date();

  document.all("AppntBirthday").title = "Ͷ���˵����� " + today.toLocaleString()
                                + " \n������Ϊ��" + age + " ��!";
}

//��ʾ����������
function showAge() {
  var age = calAge(document.all("Birthday").value);
  var today = new Date();

  document.all("Birthday").title = "�����˵����� " + today.toLocaleString()
                           + " \n������Ϊ��" + age + " ��!";
}

//����Ͷ���˳������ڣ�����գ������֤���У�������֤ȡ��ȡ�������ؿո�;
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

//У��¼��������Ƿ���ҪУ�飬�����Ҫ����true,���򷵻�false
function needVerifyRiskcode()
{

  try {
       var riskcode=document.all("RiskCode").value;

       //var tSql = "select Sysvarvalue from LDSysVar where Sysvar='NotVerifyRiskcode'";
       var sqlid17="NSProposalInputSql16";
   	   var mySql17=new SqlClass();
       mySql17.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
       mySql17.setSqlId(sqlid17);//ָ��ʹ�õ�Sql��id
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
 *  ��ʼ������ĸ�����Ϣ-121301���������ֵĳ�ʼ����һ��
 *  ����  ��  Ͷ������
 *  ����ֵ��  ��
 *********************************************************************
 */
function initPrivateRiskInfo121301(cPolNo) {
    if(cPolNo=="") {
        alert("û�����ձ�����,���ܽ��и�����¼��!");
        mCurOperateFlag="";        //��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.
        return false
    }

    var arrLCPol = new Array();
    var arrQueryResult = null;
    // ��������Ϣ����
//    var sql = "select * from lcpol where polno='" + cPolNo + "' "
//            + "and riskcode in "
//            + "( select riskcode from LMRiskApp where SubRiskFlag = 'M' )";
    var sqlid18="NSProposalInputSql17";
	var mySql18=new SqlClass();
	mySql18.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql18.setSqlId(sqlid18);//ָ��ʹ�õ�Sql��id
	mySql18.addSubPara(cPolNo);//ָ������Ĳ���
	var sql=mySql18.getString();

    arrQueryResult = easyExecSql( sql , 1, 0);

    if (arrQueryResult == null) {
        mCurOperateFlag="";        //��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.

        //top.mainPolNo = "";
        mainRiskPolNo = "";

        alert("��ȡ������Ϣʧ��,���ܽ��и�����¼��!");
        return false
    }

    arrLCPol = arrQueryResult[0];
    displayPol( arrLCPol );
    displayPolSpec( arrLCPol );//��ʼ������Ҫ��ı�����Ϣ

    document.all("MainPolNo").value = cPolNo;
    var tAR;

    //����Ͷ������Ϣ
      arrQueryResult = null;
      arrQueryResult = easyExecSql("select * from lcappntind where polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[26]+"'", 1, 0);
      tAR = arrQueryResult[0];
      displayPolAppnt(tAR);
      try { document.all('AppntRelationToInsured').value = '00'; } catch(ex) { };
      try { document.all("SamePersonFlag").checked = true; } catch(ex) { };
      try {isSamePerson();} catch(ex) { };
      try { document.all("SamePersonFlag").disabled=true} catch(ex) { };


    // ��������Ϣ����
    //  arrQueryResult = null;
    //  arrQueryResult = easyExecSql("select * from lcappntind where polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[26]+"'", 1, 0);
    //  tAR = arrQueryResult[0];
    //  displayPolInsuredSpec(tAR);


    return true;
}



/*********************************************************************
 *  �ѱ��������е�������ʾ�������������Ϣ��ʾ����-121301,
 *  ����  ��  ��������Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayPolSpec(cArr)
{
    try
    {

        try { document.all('PayEndYear').value = cArr[109]; } catch(ex) { };
        try { document.all('PayEndYearFlag').value = cArr[108]; } catch(ex) { };
        try { document.all('PayIntv').value = cArr[57]; } catch(ex) { };
        try { document.all('Amnt').value = cArr[39]; } catch(ex) { };     //���յı��Ѽ����յı���

    } catch(ex) {
      //alert("displayPolSpec err:" + ex + "\ndata is:" + cArr);
    }
}



/*********************************************************************
 *  ��ʼ������ĸ�����Ϣ-321601���������ֵĳ�ʼ����һ��
 *  ����  ��  Ͷ������
 *  ����ֵ��  ��
 *********************************************************************
 */
function initPrivateRiskInfo321601(cPolNo) {
    if(cPolNo=="") {
        alert("û�����ձ�����,���ܽ��и�����¼��!");
        mCurOperateFlag="";        //��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.
        return false
    }

    var arrLCPol = new Array();
    var arrQueryResult = null;
    // ��������Ϣ����
//    var sql = "select * from lcpol where polno='" + cPolNo + "' "
//            + "and riskcode in "
//            + "( select riskcode from LMRiskApp where SubRiskFlag = 'M' )";
    var sqlid19="NSProposalInputSql18";
	var mySql19=new SqlClass();
	mySql19.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql19.setSqlId(sqlid19);//ָ��ʹ�õ�Sql��id
	mySql19.addSubPara(cPolNo);//ָ������Ĳ���
	var sql=mySql19.getString();

    arrQueryResult = easyExecSql( sql , 1, 0);

    if (arrQueryResult == null) {
        mCurOperateFlag="";        //��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.

        //top.mainPolNo = "";
        document.all('mainRiskPolNo').value = "";

        alert("��ȡ������Ϣʧ��,���ܽ��и�����¼��!");
        return false
    }

    arrLCPol = arrQueryResult[0];
    displayPol( arrLCPol );

    //��ʼ������Ҫ��ı�����Ϣ--//���յı��Ѽ����յı���(ȡ���ձ��Ѻ�500000֮��Сֵ)
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

    //Ͷ������Ϣ
    if (arrLCPol[28]=="2") {     //����Ͷ������Ϣ
      arrQueryResult = null;
      arrQueryResult = easyExecSql("select * from lcappntgrp where polno='"+cPolNo+"'"+" and grpno='"+arrLCPol[26]+"'", 1, 0);
      tAR = arrQueryResult[0];
      displayPolAppntGrp(tAR);
    } else {                     //����Ͷ������Ϣ
      arrQueryResult = null;
      arrQueryResult = easyExecSql("select * from lcappntind where polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[26]+"'", 1, 0);
      tAR = arrQueryResult[0];
      displayPolAppnt(tAR);
    }

    // ��������Ϣ����
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
 *  �������ִ������������е�Ͷ����������ʾ�������˲���
 *  ����  ��  �ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayPolInsuredSpec(cArr)
{
        // ��LCAppntInd��ȡ����
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


///����ҳ�������ύʱ���������ֵ����⴦��
//function specDealByRisk()
//{
//  //�����ͬ�Ŀ�-�����ó���
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
//  //�����������ҵ����ҽ����
//  if(document.all('RiskCode').value=='211801')
//  {
//      //���Զ���������У��
//      var strChooseDuty="";
//      for(i=0;i<=8;i++)
//      {
//          if(DutyGrid.getChkNo(i)==true)
//          {
//              strChooseDuty=strChooseDuty+"1";
//              DutyGrid.setRowColData(i, 5, document.all('PayEndYear').value);//��������
//              DutyGrid.setRowColData(i, 6, document.all('PayEndYearFlag').value);//�������ڵ�λ
//              DutyGrid.setRowColData(i, 9, document.all('InsuYear').value);//��������
//              DutyGrid.setRowColData(i, 10, document.all('InsuYearFlag').value);//�������ڵ�λ
//              DutyGrid.setRowColData(i, 11, document.all('PayIntv').value);//�ɷѷ�ʽ
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
//  //�����MS��ҵ����Ա�������ƻ� add by guoxiang 2004-9-8 10:24
//  if(document.all('RiskCode').value=='211701')
//  {
//      //���Զ���������У��
//      var strChooseDuty="";
//      for(i=0;i<=2;i++)
//      {
//          if(DutyGrid.getChkNo(i)==true)
//          {
//              strChooseDuty=strChooseDuty*1+1.0;
//                DutyGrid.setRowColData(i, 3, document.all('Prem').value);//����
//              DutyGrid.setRowColData(i, 9, document.all('InsuYear').value);//��������
//              DutyGrid.setRowColData(i, 10, document.all('InsuYearFlag').value);//�������ڵ�λ
//              DutyGrid.setRowColData(i, 11, document.all('PayIntv').value);//�ɷѷ�ʽ
//              DutyGrid.setRowColData(i, 12, document.all('ManageFeeRate').value);//����ѱ���
//
//          }
//          else
//          {
//              strChooseDuty=strChooseDuty*1+0.0;
//          }
//      }
//      if(strChooseDuty>1){
//         alert("��ҵ����Ա������ÿ�ű���ֻ����ѡ��һ�����Σ���ѡ������δ���Ϊ"+strChooseDuty+"�����޸ģ�����");
//         return false;
//      }
//      return true;
//  }
//
//  //����Ǹ��˳�����
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
//              alert("�����ڼ����ѡ��");
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
//  //����Ǿ���������
//  if(document.all('RiskCode').value=='241801')
//  {
//      try
//      {
//          var InsurCount = document.all('StandbyFlag2').value;
//          if(InsurCount>4||InsurCount<0)
//          {
//              alert("�����������������ܳ���4��");
//              return false;
//          }
//          SubInsuredGrid.delBlankLine("SubInsuredGrid");
//          var rowNum=SubInsuredGrid.mulLineCount;
//          if(InsurCount!=rowNum)
//          {
//              alert("���������������Ͷ��������������������Ϣ�����������ϣ� ");
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

//����ҳ���ʼ��ʱ���������ֵ����⴦��
function initDealForSpecRisk(cRiskCode)
{
  try{
    //�����211801
    if(cRiskCode=='211801')
    {
        DutyGrid.addOne();
        DutyGrid.setRowColData(0, 1, '610001');
        DutyGrid.setRowColData(0, 2, '��������1��');
        DutyGrid.addOne();
        DutyGrid.setRowColData(1, 1, '610002');
        DutyGrid.setRowColData(1, 2, '��������2��');
        DutyGrid.addOne();
        DutyGrid.setRowColData(2, 1, '610003');
        DutyGrid.setRowColData(2, 2, '��������3��');
        DutyGrid.addOne();
        DutyGrid.setRowColData(3, 1, '610004');
        DutyGrid.setRowColData(3, 2, '��������4��');
        DutyGrid.addOne();
        DutyGrid.setRowColData(4, 1, '610005');
        DutyGrid.setRowColData(4, 2, '��������5��');
        DutyGrid.addOne();
        DutyGrid.setRowColData(5, 1, '610006');
        DutyGrid.setRowColData(5, 2, '��������6��');
        DutyGrid.addOne();
        DutyGrid.setRowColData(6, 1, '610007');
        DutyGrid.setRowColData(6, 2, '��������7��');
        DutyGrid.addOne();
        DutyGrid.setRowColData(7, 1, '610008');
        DutyGrid.setRowColData(7, 2, '��������8��');
        DutyGrid.addOne();
        DutyGrid.setRowColData(8, 1, '610009');
        DutyGrid.setRowColData(8, 2, 'ŮԱ����������');
        DutyGrid.lock();


    }

    //�������
    if(cRiskCode=='212401')
    {

        PremGrid.addOne();
        PremGrid.setRowColData(0, 1, '601001');
        PremGrid.setRowColData(0, 2, '601101');
        PremGrid.setRowColData(0, 3, '���彻��');
        PremGrid.addOne();
        PremGrid.setRowColData(1, 1, '601001');
        PremGrid.setRowColData(1, 2, '601102');
        PremGrid.setRowColData(1, 3, '���˽���');
        PremGrid.lock();

    }

    //��ҵ����
    if(cRiskCode=='211701')
    {
//        var strSql = "select *  from lmdutypayrela where dutycode in  "
//                   + " (select dutycode from lmriskduty where riskcode='"+cRiskCode+"')";
        var sqlid20="NSProposalInputSql19";
    	var mySql20=new SqlClass();
    	mySql20.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql20.setSqlId(sqlid20);//ָ��ʹ�õ�Sql��id
    	mySql20.addSubPara(cRiskCode);//ָ������Ĳ���
    	var strSql=mySql20.getString();
 
        
        turnPage.queryModal(strSql, PremGrid);
        PremGrid.lock;
    }

  }catch(ex) {}

}

/*********************************************************************
* ����ҳ���ʼ��ʱ���������ֵ����⴦����չ
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
         initDutyGrid();  //�������ֳ�ʼ������¼���
//         strSql = "select dutycode,dutyname,'','','','','','','','','',''  from lmduty where dutycode in "
//                   + " (select dutycode from lmriskduty where riskcode='"+cRiskCode+"' and choflag!='B')";
         var sqlid21="NSProposalInputSql20";
     	 var mySql21=new SqlClass();
     	 mySql21.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
     	 mySql21.setSqlId(sqlid21);//ָ��ʹ�õ�Sql��id
     	 mySql21.addSubPara(cRiskCode);//ָ������Ĳ���
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
       	 mySql22.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
       	 mySql22.setSqlId(sqlid22);//ָ��ʹ�õ�Sql��id
       	 mySql22.addSubPara(cRiskCode);//ָ������Ĳ���
       	 mySql22.addSubPara(cDutyCode);//ָ������Ĳ���
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
      	  mySql23.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
      	  mySql23.setSqlId(sqlid23);//ָ��ʹ�õ�Sql��id
      	  mySql23.addSubPara(cRiskCode);//ָ������Ĳ���
      	  strSql=mySql23.getString();
          turnPage.queryModal(strSql, PremGrid);
          PremGrid.lock;

        }


  }catch(ex) {}

}
function queryAgent()
{
    if(document.all('ManageCom').value==""){
         alert("����¼����������Ϣ��");
         return;
    }
    if(document.all('AgentCode').value == "") {
      //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
      var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
      }
    if(document.all('AgentCode').value != "")  {
    var cAgentCode = fm.AgentCode.value;  //��������
    //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
    var sqlid24="NSProposalInputSql23";
	var mySql24=new SqlClass();
	mySql24.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql24.setSqlId(sqlid24);//ָ��ʹ�õ�Sql��id
	mySql24.addSubPara(cAgentCode);//ָ������Ĳ���
	var strSql=mySql24.getString();
    var arrResult = easyExecSql(strSql);
    if (arrResult != null) {
      fm.AgentGroup.value = arrResult[0][2];
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
     }
    }
}

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
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
         alert("����¼����������Ϣ��");
         return;
    }
    if(document.all('AgentCode').value != "" && document.all('AgentCode').value.length==10 )     {
    var cAgentCode = fm.AgentCode.value;  //��������
    //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
    var sqlid25="NSProposalInputSql24";
	var mySql25=new SqlClass();
	mySql25.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql25.setSqlId(sqlid25);//ָ��ʹ�õ�Sql��id
	mySql25.addSubPara(cAgentCode);//ָ������Ĳ���
	mySql25.addSubPara(document.all('ManageCom').value);//ָ������Ĳ���
	var strSql=mySql25.getString();
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {
      fm.AgentGroup.value = arrResult[0][2];
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
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
    //���ڸ��գ����շ��ص�ҳ�治ͬ�ڴ˽�������
    //LoadFlag='1' ����
    //LoadFlag='2' ����
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
//(GrpContNo,LoadFlag);//���ݼ����ͬ�Ų��������Ϣ
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
	mySql26.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql26.setSqlId(sqlid26);//ָ��ʹ�õ�Sql��id
	mySql26.addSubPara(GrpContNo);//ָ������Ĳ���
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
	mySql27.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql27.setSqlId(sqlid27);//ָ��ʹ�õ�Sql��id
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
       	mySql28.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
       	mySql28.setSqlId(sqlid28);//ָ��ʹ�õ�Sql��id
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
	mySql29.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql29.setSqlId(sqlid29);//ָ��ʹ�õ�Sql��id
	mySql29.addSubPara(GrpContNo);//ָ������Ĳ���
	mySql29.addSubPara(ContPlanCode);//ָ������Ĳ���
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
 *  �Ѻ�ͬ������Ϣ¼�����ȷ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function inputConfirm(wFlag)
{


    //alert("wFlag in ProposalInput=="+wFlag);
    //alert("abcd=="+document.all('ContNo').value);
    if (wFlag ==1 ) //¼�����ȷ��
    {
        if(document.all('ContNo').value == "")
      {
        alert("��ͬ��Ϣδ����,������������ [¼�����] ȷ�ϣ�");
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
  else if (wFlag ==2)//�������ȷ��
  {
    if(document.all('ProposalContNo').value == "")
      {
        alert("δ��ѯ����ͬ��Ϣ,������������ [�������] ȷ�ϣ�");
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
          alert("δ��ѯ����ͬ��Ϣ,������������ [�����޸����] ȷ�ϣ�");
          return;
       }
        fm.WorkFlowFlag.value = "0000001002";
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;
    }
    else
        return;

  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "./InputConfirm.jsp";
  fm.submit(); //�ύ
}

//�˺�����Ŀ���ǣ���ѯ�ŵ����߸����µ�Ͷ������Ͷ����Ϣ
function getContInputnew(){
    //ȡ�ø���Ͷ���˵�������Ϣ
    if(fm.AppntCustomerNo.value!=""&&fm.AppntCustomerNo.value!="false"){
      //arrResult=easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ),b.PostalAddress,b.ZipCode,b.Phone,b.HomePhone from LDPerson a Left Join LCAddress b On b.CustomerNo =a.CustomerNo Where 1=1 and a.CustomerNo ='"+fm.AppntCustomerNo.value+"'",1,0);
         // arrResult=easyExecSql("select a.* from LDPerson a Where 1=1 and trim(a.CustomerNo) =trim('"+fm.AppntCustomerNo.value+"')");
    //alert("aaa=="+document.all('RelationToAppnt').value);
    //alert(arrResult[0][5]);
    //displayAppnt(arrResult[0]);
    //alert("bbb=="+document.all('AppntIDNo').value);
  }
    //ȡ��Ͷ����λ�� ������Ϣ
    if(fm.GrpContNo.value!=""&&fm.GrpContNo.value!="false"){
      arrResult = easyExecSql("select a.*,b.GrpName,b.BusinessType,b.GrpNature,b.Peoples,b.RgtMoney,b.Asset,b.NetProfitRate,b.MainBussiness,b.Corporation,b.ComAera,b.Fax,b.Phone,b.FoundDate,b.CustomerNo,b.GrpName from LCGrpAddress a,LDGrp b where a.CustomerNo = b.CustomerNo and b.CustomerNo=(select CustomerNo from LCGrpAppnt  where GrpContNo = '" + fm.GrpContNo.value + "')", 1, 0);
      displayAddress1(arrResult[0]);
    }
    //ȡ�ñ�Ͷ���˵�������Ϣ
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
	mySql30.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql30.setSqlId(sqlid30);//ָ��ʹ�õ�Sql��id
	mySql30.addSubPara(tGrpContNo);//ָ������Ĳ���
	strSql=mySql30.getString();
    var tPeoplesCount = easyExecSql(strSql);

    if(tPeoplesCount==null||tPeoplesCount[0][0]<=0){
        alert("����ʧ�ܣ�Ͷ��������Ϊ0��");
        return;
    }

    //strSql = "select peoples2,riskcode from LCGrpPol    where GrpContNo = '" + tGrpContNo + "'";
    var sqlid31="NSProposalInputSql30";
	var mySql31=new SqlClass();
	mySql31.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql31.setSqlId(sqlid31);//ָ��ʹ�õ�Sql��id
	mySql31.addSubPara(tGrpContNo);//ָ������Ĳ���
	strSql=mySql31.getString();
    tPeoplesCount = easyExecSql(strSql);
    //alert(tPeoplesCount);
    if(tPeoplesCount!=null)
    {
        for(var i=0;i<tPeoplesCount.length;i++)
        {
            if(tPeoplesCount[i][0]<=0)
            {
                alert("����ʧ�ܣ�����"+tPeoplesCount[i][1]+"��Ͷ��������Ϊ0��");
                return;
            }
        }
    }

//    strSql = "select SaleChnl,AgentCode,AgentGroup,ManageCom,GrpName,CValiDate,PrtNo from LCGrpCont     where GrpContNo = '"
//    + tGrpContNo + "'";
    var sqlid32="NSProposalInputSql31";
	var mySql32=new SqlClass();
	mySql32.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql32.setSqlId(sqlid32);//ָ��ʹ�õ�Sql��id
	mySql32.addSubPara(tGrpContNo);//ָ������Ĳ���
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
	mySql33.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql33.setSqlId(sqlid33);//ָ��ʹ�õ�Sql��id
	mySql33.addSubPara(grpContInfo[0][6]);//ָ������Ĳ���
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
	mySql34.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql34.setSqlId(sqlid34);//ָ��ʹ�õ�Sql��id
	mySql34.addSubPara(document.all('ProposalGrpContNo').value);//ָ������Ĳ���
	tStr=mySql34.getString();
    turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
    if (turnPage.strQueryResult) {
    alert("���ŵ���ͬ�Ѿ��������棡");
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
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "./GrpInputConfirm.jsp?FrameType=0&"+queryString;
    fm.submit(); //�ύ

}

/*********************************************************************
 *  �Ѻ�ͬ������Ϣ¼�����ȷ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function inputConfirm2(wFlag)
{
    //alert("wFlag=="+wFlag);
    if (wFlag ==1 ) //¼�����ȷ��
    {
    //var tStr= " select * from lwmission where 1=1 and lwmission.activityid in ('0000001001','0000001002','0000001220','0000001230') and lwmission.missionprop1 = '"+fm.ContNo.value+"'";
    var sqlid35="NSProposalInputSql34";
    var mySql35=new SqlClass();
	mySql35.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql35.setSqlId(sqlid35);//ָ��ʹ�õ�Sql��id
	mySql35.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	tStr=mySql35.getString();
        turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
        if (turnPage.strQueryResult) {
          alert("�ú�ͬ�Ѿ��������棡");
          return;
        }

        fm.WorkFlowFlag.value = "0000001098";
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;          //¼�����
  }
  else if (wFlag ==2)//�������ȷ��
  {
    if(document.all('ProposalContNo').value == "")
       {
          alert("δ��ѯ����ͬ��Ϣ,������������ [�������] ȷ�ϣ�");
          return;
       }
        fm.WorkFlowFlag.value = "0000001001";                   //�������
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;
        approvefalg="2";
    }
      else if (wFlag ==3)
  {
    if(document.all('ProposalContNo').value == "")
       {
          alert("δ��ѯ����ͬ��Ϣ,������������ [�����޸����] ȷ�ϣ�");
          return;
       }
        fm.WorkFlowFlag.value = "0000001002";                   //�����޸����
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;
    }
    else if(wFlag == 4)
    {
         if(document.all('ProposalContNo').value == "")
       {
          alert("δ��ѯ����ͬ��Ϣ,������������ [�޸����] ȷ�ϣ�");
          return;
       }
        fm.WorkFlowFlag.value = "0000001021";                   //�����޸�
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;
    }
    else
        return;

  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "./InputConfirm.jsp";
  fm.submit(); //�ύ
}

/*********************************************************************
 *  У�����ֽ���ķ����Ƿ�Ϊ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *  create by malong 2005-7-11
 *********************************************************************
 */
function checkMult(){
    var tSql="";
  //tSql="select CalMode from lmduty a,lmriskduty b where b.riskcode='"+document.all('RiskCode').value+"' and a.dutycode=b.dutycode";
    var sqlid36="NSProposalInputSql35";
    var mySql36=new SqlClass();
	mySql36.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql36.setSqlId(sqlid36);//ָ��ʹ�õ�Sql��id
	mySql36.addSubPara(document.all('RiskCode').value);//ָ������Ĳ���
	tSql=mySql36.getString();
  var arrResult=easyExecSql(tSql);

  if(arrResult[0]=="O" && document.all('inpNeedDutyGrid').value == "0"){
        if(document.all('mult').value == "0")
        {
            alert('��������Ϊ��!');
            document.all('mult').value = "";
            document.all('mult').focus();
            return false;
        }
        else if(document.all('mult').value== "")
        {
            alert('��������Ϊ��!');
            return false;
        }
        else if(!isInteger(document.all('mult').value))
        {
                alert('��������Ϊ����!');
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
	mySql37.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql37.setSqlId(sqlid37);//ָ��ʹ�õ�Sql��id
	mySql37.addSubPara(ContNo);//ָ������Ĳ���
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
/**��ѯ�Ƿ�������Ƶ�����*/
function checkSameRiskCode()
{
	//var tSQL="select riskcode from lcpol where contno='"+fm.ContNo.value+"' and appflag in ('1','2')";
	    var sqlid38="NSProposalInputSql37";
	    var mySql38=new SqlClass();
		mySql38.setResourceName("app.NSProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql38.setSqlId(sqlid38);//ָ��ʹ�õ�Sql��id
		mySql38.addSubPara(fm.ContNo.value);//ָ������Ĳ���
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