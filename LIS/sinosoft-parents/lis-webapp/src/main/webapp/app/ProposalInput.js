//Modify by niuzj,2006-08-23,Ӣ����Ҫ��¼����������Ϣʱ����һ�����Ա��ֶ�
//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();          // ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPageMultRisk = new turnPageClass(); 
var turnPageDiscount = new turnPageClass(); 
var showInfo;
var spanObj;
var mDebug = "100";
var mOperate = 0;
var mAction = "";
var arrResult = new Array();
var mShowCustomerDetail = "PROPOSAL";
var mCurOperateFlag = ""    // "1--¼�룬"2"--��ѯ
var mGrpFlag = "";  // ���˼����־,"0"��ʾ����,"1"��ʾ����.
var cflag = "0";        // �ļ���¼��λ��
var sign=0;
var risksql;
var arrGrpRisk = null;
var mainRiskPolNo="";
var cMainRiskCode="";
var mSwitch = parent.VD.gVSwitch;
window.onfocus = myonfocus;
var hiddenBankInfo = "";
var parameter1="";
var parameter2="";
var mRollSpeedOperator = 0;
var mRollSpeedSelf = 0;
var mRollSpeedBase = 0;
var mRiskName = "";  // add by Marlon 20060713 ������������
var mCValiDate = parent.VD.gVSwitch.getVar('CValiDate');
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var RelationToAppnt = "";
/*******************************************************************************
 * ѡ�����ֺ�Ķ��� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */

function intorisk()
{
	
    if(document.all('RiskCode').value=="")
     {
        alert("����ѡ������!");
        return;
     }

    if( verifyInput() == false ) return false;

    try {
        if(document.all('RiskCode').value!=null){
            getRiskInput(document.all('RiskCode').value, "1");// LoadFlag��ҳ���ʼ����ʱ������
			
          if(fm.RiskCode.value=="00144000"){
              InsuredGrid.setRowColData(0, 6, "1");
          }
        }else{
            getRiskInput(parameter2.value, "1");// LoadFlag��ҳ���ʼ����ʱ������
        }
        // ������Լ��Ϣ
        DivLCSpec.style.display="none";
    }catch( ex ) {}
}


function afterCodeSelect( cCodeName, Field ) {
    parameter1=cCodeName;
    parameter2=Field;
    // alert(parameter1);
    if(parameter1=="RiskCode" && LoadFlag==1){
    	  // showMultRiskGrid(Field.value);
    	
        return;
    }
    if(cCodeName=="YesNo")
    {
	    try
	    {
	    // alert(123);
		    if(fm.RiskCode.value=="111302"||fm.RiskCode.value=="111303")
		    {
		    	if(Field.value=="Y")
		            {
		            	document.all("CValiDate").readOnly=false;
		            	
		            }else{
		            	// alert(456);
		            	// fm.CValiDate.value="";
		            	document.all("CValiDate").readOnly=true;
		            	
		            }
		    	}
		}catch(ex){}
    }
    // ��Ը������ֵ����⴦��
    try
    {
    	
    	if(cCodeName.substring(1,cCodeName.indexOf("-"))=="Getdutykind")
    	{
    		if(fm.RiskCode.value=='GLB001')
    		{
    			if(Field.value=="A")
    			{
    				fm.Amnt.value='10000000';
    				fm.Mult.value='1';
    			}
    			else if(Field.value=="B")
    			{
    				fm.Amnt.value='5000000';
    				fm.Mult.value='1';
    			}
    			else if(Field.value=="C")
    			{
    				fm.Amnt.value='2000000';
    				fm.Mult.value='1';
    			}
    				
    		}
    		
    	}
    	// �����ڼ�
    	if(cCodeName.substring(1,cCodeName.indexOf("-"))=="PayEndYear")
        {	
    		// MS��̩�ش󼲲���ȫ����
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
    		// MS�����ش󼲲����� 111501 || 311501 MS�����ش󼲲����գ��н飩
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
    		// MS��������գ��ֺ��ͣ�
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
    		// 312204 MS����������ȫ����D��ֺ��ͣ�
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
            // 112206 MS��������ٶ���������ȫ���գ��ֺ��ͣ�
            // 122201 MS���ӳ��н�������ȫ���գ��ֺ��ͣ�
            // 122202 MS���Ӹ��н�������ȫ���գ��ֺ��ͣ�
            // 112219 �Ҹ�360�ٶ�
            // 121511 �����Ҹ������ؼ�
    		if(fm.RiskCode.value=="112206"||fm.RiskCode.value=="122201"||fm.RiskCode.value=="122202"||fm.RiskCode.value=="112219"||fm.RiskCode.value=="121511")
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
    		
    		// MS����������ȫ����(�ֺ���)
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
		 	// 112202 MS��˳��ȫ���գ��ֺ���) ���� 112203 MS��ԣ��ȫ���գ��ֺ��ͣ�
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
                      // 111301 MS�������ڱ���
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
           if(fm.RiskCode.value=="r01212")
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
           
            // MS������������(�ֺ���)||111504 MS��̩һ���ش󼲲�����
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
            

            
            // MS���������ȫ����(�ֺ���)
             if(fm.RiskCode.value=="112201")
    		  { 
            		 fm.PayEndYearFlag.value="Y";
            }
    		
    		return;
        }
        


       // ��Ա����ڼ�Ĵ���
       if(cCodeName.substring(1,cCodeName.indexOf("-"))=="InsuYear")
       {
            	
           // MS���Ӿþ�ͬ�ٶ�������
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
          // 112212 MS����������ȫ����(�ֺ���)
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
           // 121305 MS���Ӷ�����
           // 121505 MS���Ӷ�������ش󼲲�����
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
               
                
                  
           
           // MS�þ�ͬ����ȫ���գ��ֺ��ͣ��������ڼ���д��� || 121504 MS���Ӿþ�ͬ����������ش󼲲�����|| 112217
			// ����˫ӯA�� ||112218����˫ӯB��
           if(fm.RiskCode.value=="112211" ||fm.RiskCode.value=="121504"||fm.RiskCode.value=="112217"||fm.RiskCode.value=="112218")
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
           // MS���������ȫ���գ��ֺ��ͣ�
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
           // MS��̩�ش󼲲���ȫ���գ������ڼ���д��� || 112208 MS��̩��ȫ���գ��ֺ��ͣ�
           // 121501 MS������ǰ�����ش󼲲�����
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
            // 312207 MS������Ե��ȫ����(�ֺ���)
           if(fm.RiskCode.value=="312207")
           { 
	           if(Field.value=="15")
	           {
	            	 fm.GetDutyKind.value="1";
	           }
	           else
	           {
	                fm.GetDutyKind.value="2";
	           }

           }   


           return;
      }
       
       // ���ѷ�ʽ
       if(cCodeName.substring(1,cCodeName.indexOf("-")) == 'PayIntv')
       {
           if (Field.value == "0") 
           {
             // document.all('TDPayEndYear').innerHTML = "<Input class=codeno
				// name=PayEndYear verify=\"�ɷ�����|code:!PayEndYear&notnull\"
				// onfocus=\"checkPayIntv()\"><input class=codename
				// name=PayEndYearName readonly=true elementtype=nacessary>";
             // document.all("PayEndYear").readOnly = true;
             // document.all("PayEndYear").value = "1000";
             // document.all("PayEndYearName").value = "��";
         	  
         	  // 2008-08-25 ��Ϊ��5.3һ�£���Ϊ����ʱ�����ڼ�ͱ����ڼ�һ��
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
    

    try {
        if (parameter1 == "PolCalRule"){
            if (Field.value == "2"){
                fm.FloatRate.disabled = false;
            }else{
                fm.FloatRate.value = "";
                fm.FloatRate.disabled = true;
            }
        }
    }
    catch(ex){}
    try {
      // ����ѡ��
      try{
        if( type =="noScan" && cCodeName == 'RiskInd')// ������ɨ���¼��
          {
		  	
		var sqlid1="ProposalInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);// ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(Field.value);// ָ������Ĳ���
		mySql1.addSubPara(ManageCom);// ָ������Ĳ���
		mySql1.addSubPara(ManageCom);// ָ������Ĳ���
	    var strSql=mySql1.getString();	
			
              // var strSql = "select distinct 1 from ldsysvar where
				// VERIFYOPERATEPOPEDOM('"+Field.value+"','"+ManageCom+"','"+ManageCom+"',"+"'Pa')=1
				// ";
              var arrResult = easyExecSql(strSql);
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
        if(cCodeName == 'RiskInd'){
          if (typeof(prtNo)!="undefined" && typeof(type)=="undefined" )// ������ɨ���¼��
          {
		  	
		var sqlid2="ProposalInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);// ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(Field.value);// ָ������Ĳ���
		mySql2.addSubPara(ManageCom);// ָ������Ĳ���
		mySql2.addSubPara(ManageCom);// ָ������Ĳ���
	    var strSql2=mySql2.getString();	
			
            // var strSql2 = "select distinct 1 from ldsysvar where
			// VERIFYOPERATEPOPEDOM('"+Field.value+"','"+ManageCom+"','"+ManageCom+"',"+"'Pz')=1
			// ";
            var arrResult2 = easyExecSql(strSql2);
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


   // alert("cCodeName="+cCodeName);//mark by yaory ��ʼ�����ֽ���
    if( cCodeName == "RiskInd" || cCodeName == "RiskGrp" || cCodeName == "RiskCode" || cCodeName.substring(0,3)=="***" || cCodeName.substring(0,2)=="**" || cCodeName.substring(0,3)=="GAI")
      {
        var tProposalGrpContNo = parent.VD.gVSwitch.getVar( "GrpContNo" );
        var tContPlanCode = parent.VD.gVSwitch.getVar( "ContPlanCode" );
        if(mainRiskPolNo==""&&parent.VD.gVSwitch.getVar("mainRiskPolNo")==true){
          mainRiskPolNo=parent.VD.gVSwitch.getVar("mainRiskPolNo");
        }

        if(cCodeName=="RiskCode" || cCodeName.substring(0,2)=="**")
        {

          if(!isSubRisk(Field.value)){ cMainRiskCode=Field.value;}
          else if(isSubRisk(Field.value)&&mainRiskPolNo!="")
          {
//            var mainRiskSql="select riskcode from lcpol where polno='"+mainRiskPolNo+"'";
            var sqlid80="ProposalInputSql80";
    		var mySql80=new SqlClass();
    		mySql80.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
    		mySql80.setSqlId(sqlid80);// ָ��ʹ�õ�Sql��id
    		mySql80.addSubPara(mainRiskPolNo);// ָ������Ĳ���
    		var mainRiskSql=mySql80.getString();	
            var arr = easyExecSql(mainRiskSql);
            cMainRiskCode=arr[0];
          }
        }
        else if(cCodeName.substring(0,3)=="GAI" && cMainRiskCode=="")
        {
            try{cMainRiskCode=cCodeName.substring(4,cCodeName.length);}catch(ex){}
        }
        else
        {}

        if(LoadFlag!=7 && LoadFlag!=2 &&LoadFlag!=13 )
        {
          getRiskInput(Field.value, LoadFlag);// LoadFlag��ҳ���ʼ����ʱ������
        }
        else
        {
        	
          if (tContPlanCode=='false'||tContPlanCode==null||tContPlanCode=='')
            {

                    // ���û�б��ռƻ�����,��ѯ��û��Ĭ��ֵ�������Ĭ��ֵҲ��Ҫ���ú�̨��ѯ

//                    var strsql = "select ContPlanCode from LCContPlan where ContPlanCode='00' and ProposalGrpContNo='"+tProposalGrpContNo+"'";
//                    if(LoadFlag ==7){
//                        strsql = "select ContPlanCode from LCContPlan where ContPlanCode='00' and GrpContNo='"+tProposalGrpContNo+"'";
//                    }
                    var para1 =tProposalGrpContNo;var para2;
        	  		if(LoadFlag ==7){
                    	para2=para1;para1="";
                    }
                    var sqlid81="ProposalInputSql81";
            		var mySql81=new SqlClass();
            		mySql81.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
            		mySql81.setSqlId(sqlid81);// ָ��ʹ�õ�Sql��id
            		mySql81.addSubPara(tProposalGrpContNo);// ָ������Ĳ���
            		mySql81.addSubPara(tProposalGrpContNo);// ָ������Ĳ���
            		var strsql=mySql81.getString();
                    var defultContPlanCode=easyExecSql(strsql);

                    if (defultContPlanCode=='00')
                    {
                        if(LoadFlag ==7){
                           var ttProposalGrpContNo = parent.VD.gVSwitch.getVar( "ProposalGrpContNo" );
                           BqQueryRiskPlan( ttProposalGrpContNo,Field.value,defultContPlanCode,cMainRiskCode,EdorValiDate,CValiDate );
                        }else{
                           queryRiskPlan( tProposalGrpContNo,Field.value,defultContPlanCode,cMainRiskCode );
                        }
                    }
                    else
                    {
                        getRiskInput(Field.value, LoadFlag);// LoadFlag��ҳ���ʼ����ʱ������
                    }

            }
            else
            {
                    queryRiskPlan( tProposalGrpContNo,Field.value,tContPlanCode,cMainRiskCode );
            }
        }
        // ������Լ��Ϣ
        DivLCSpec.style.display="none";
        // ��ɨ���ͼƬ������һҳ
        try {
          goToPic(0);
        }
        catch(ex2){}
          return;
        }

      // -------------------------------------------------Beg
      // �޸��ˣ�chenrong
      // �޸����ڣ�2006-03-16
      // ��ȡ��ʽΪ����ʱ������Ҫ������ȡƵ�ʵ�

      if (trim(cCodeName).substring(1,13) == "GetDutyKind1")
      {
          if (Field.value == "0")
          {
            try
            {
                for (i = 2;i <= fm.GetDutyKindFlag.value; i++)
                {
                    document.all('GetDutyKind' + i).value = "";
                    document.all('GetDutyKind' + i + 'Name').value = "";
                    document.all('GetDutyKind' + i).disabled = true;
                }
            }
            catch(ex)
            {
            }
          }
          else
          {
            try
            {
                for (i = 2;i <= fm.GetDutyKindFlag.value; i++)
                {
                    document.all('GetDutyKind' + i).disabled = false;
                }
            }
            catch(ex)
            {
            }
          }
      }
      // -------------------------------------------------End
	
      // �Զ���д��������Ϣ
      if (cCodeName == "customertype") {
        if (Field.value == "A") {
          if(ContType!="2")
          {
        
          // alert("1111111");
          var index = BnfGrid.mulLineCount;
          BnfGrid.setRowColData(index-1, 2, document.all("AppntName").value);
          BnfGrid.setRowColData(index-1, 3, document.all("AppntSex").value);
          BnfGrid.setRowColData(index-1, 4, document.all("AppntIDType").value);
          BnfGrid.setRowColData(index-1, 5, document.all("AppntIDNo").value);
          // tongmeng 2007-12-18 Modify
          // �޸������˹�ϵ����
          // BnfGrid.setRowColData(index-1, 6,parent.VD.gVSwitch.getVar(
			// "RelationToAppnt"));
          BnfGrid.setRowColData(index-1, 6, document.all("AppntRelationToInsured").value);
          BnfGrid.setRowColData(index-1, 9, document.all("AppntHomeAddress").value);
          // hl
          // BnfGrid.setRowColData(index-1, 9, document.all("AppntNo").value);
          // alert("toubaoren:"+document.all("AppntNo").value)
          }
          else
          {

            alert("Ͷ����Ϊ���壬������Ϊ�����ˣ�")
          var index = BnfGrid.mulLineCount;
          BnfGrid.setRowColData(index-1, 2, "");
          BnfGrid.setRowColData(index-1, 3, "");
          BnfGrid.setRowColData(index-1, 4, "");
          BnfGrid.setRowColData(index-1, 5, "");
          BnfGrid.setRowColData(index-1, 6, "");
          BnfGrid.setRowColData(index-1, 9, "");
          }
        }
        else if (Field.value == "I") {
        // alert("2222222");
        // alert("�������뱻���˹�ϵУ��");
          var index = BnfGrid.mulLineCount;
        BnfGrid.setRowColData(index-1, 2, document.all("Name").value);
        BnfGrid.setRowColData(index-1, 3, document.all("Sex").value);
        BnfGrid.setRowColData(index-1, 4, document.all("IDType").value);
        BnfGrid.setRowColData(index-1, 5, document.all("IDNo").value);
        BnfGrid.setRowColData(index-1, 6, "00");
        // BnfGrid.setRowColData(index-1, 8, document.all("AppntHomeAddress").value);
        // hl
        BnfGrid.setRowColData(index-1, 9, document.all("HomeAddress").value);
				// tongmeng 2007-12-28 add
					// ��������˲���Ϊ����
					var tempDeadBnf = BnfGrid.getRowColData(index-1, 1);
          if(tempDeadBnf==null||trim(tempDeadBnf)=='')
          {
          	// Ĭ��Ϊ����������
          	tempDeadBnf = '0'
          }
          if(fm.RiskCode.value == "112212")
          {	// ��������У������������һ��Ҫ�Ǳ�������
           	 if(tempDeadBnf=='0')
          	 {
          	 	
          	 }
          	
          }
          if(tempDeadBnf=='1')
          {
        	  // alert('��������˲���Ϊ����1!');
          	alert('��������˲���Ϊ����!');
          	// alert("3333333");
          	BnfGrid.setRowColData(index-1, 2, '');
          	BnfGrid.setRowColData(index-1, 3, '');
          	BnfGrid.setRowColData(index-1, 4, '');
          	BnfGrid.setRowColData(index-1, 5, '');
          	BnfGrid.setRowColData(index-1, 6, '');
          	BnfGrid.setRowColData(index-1, 9, '');
          	return ;
          }
        // BnfGrid.setRowColData(index-1, 10, document.all("InsuredNo").value);
                // alert("4544564"+document.all("InsuredNo").value);
        }
        return;
    }


        // �շѷ�ʽѡ��
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
		  
		 var sqlid3="ProposalInputSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);// ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(fm.AppntCustomerNo.value);// ָ������Ĳ���
		mySql3.addSubPara(fm.ContNo.value);// ָ������Ĳ���
	    strSql=mySql3.getString();	
		  
         // strSql = "select BankCode,BankAccNo,AccName from LCAppNT where
			// AppNtNo = '" + document.all('AppntCustomerNo').value + "' and
			// contno='"+document.all('ContNo').value+"'";
          var arrAppNtInfo = easyExecSql(strSql);
          document.all("BankCode").value = arrAppNtInfo[0][0];
          document.all("AccName").value = arrAppNtInfo[0][2];
          document.all("BankAccNo").value = arrAppNtInfo[0][1];
          document.all("PayLocation").value = "0";
          document.all("PayLocation").focus();
        }
        return;
    }
      // ���δ���ѡ��
      if(cCodeName == "DutyCode"){
        var index = DutyGrid.mulLineCount;
		
		var sqlid4="ProposalInputSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);// ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(Field.value);// ָ������Ĳ���
	    var strSql =mySql4.getString();	
		
       // var strSql = "select dutyname from lmduty where
		// dutycode='"+Field.value+"'";
        var arrResult = easyExecSql(strSql);
        var dutyname= arrResult[0].toString();
        DutyGrid.setRowColData(index-1, 2, dutyname);
        return;
      }
      // add by yaory
      if(cCodeName == "insuredpeople")
      {
      	// alert("4444444");
        var index = BnfGrid.mulLineCount;
          BnfGrid.setRowColData(index-1, 10, document.all("CustomerNo").value);
          // BnfGrid.setRowColData(index-1, 11, document.all("AppntIDType").value);
          // BnfGrid.setRowColData(index-1, 12, document.all("AppntIDNo").value);
      }
      if(cCodeName == "PolCalRule1"){  // ��ʱδ��
        if(Field.value=="1"){       // ͳһ����
          divFloatRate.style.display="none";
          divFloatRate2.style.display="";
        }
        else if(Field.value=="2"){  // Լ�������ۿ�
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
      


      // ------------------------Beg
      // add by:chenrong
      // date:2006.05.22
      // ��������
      if(cCodeName=="PayRuleCode"){
        fm.action="./ProposalQueryPayRule.jsp?AppFlag=0";
        document.getElementById("fm").submit();
        return;
      }
      // -----------------------End
  }
    catch( ex ) {
    }

}
/*******************************************************************************
 * �ύǰ��У�顢���� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
 
function forInsuredNo(){
	BnfGrid.setRowColData(index-1, 13, document.all("AppntIDNo").value);
}
 
function beforeSubmit()
{

  if( verifyInput() == false ) return false;
}

/*******************************************************************************
 * ����LoadFlag����һЩFlag���� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function convertFlag( cFlag )
{
    if( cFlag == "1"|| cFlag == "8"||cFlag == "99")     // ����Ͷ����ֱ��¼��
    {
        mCurOperateFlag = "1";
        mGrpFlag = "0";
        return;
    }
    if( cFlag == "2" || cFlag == "7" || cFlag == "9" || cFlag == "13"||cFlag == "14"||cFlag == "16"||cFlag=="23")       // �����¸���Ͷ����¼��
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

  // ����ӡˢ�Ų��ҳ����е��������
  if (arrGrpRisk == null) {
  	
			var sqlid5="ProposalInputSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);// ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(prtNo);// ָ������Ĳ���
	    var strSql =mySql5.getString();	
	
    // var strSql = "select GrpProposalNo, RiskCode from LCGrpPol where PrtNo =
	// '" + prtNo + "'";
    arrGrpRisk  = easyExecSql(strSql);

    // ͨ���б�����������ҵ�����
    for (i=0; i<arrGrpRisk.length; i++) {
		
		var sqlid6="ProposalInputSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);// ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(prtNo);// ָ������Ĳ���
	   strSql=mySql6.getString();	
		
// strSql = "select SubRiskFlag from LMRiskApp where RiskCode = '"
// + arrGrpRisk[i][1] + "' and RiskVer = '2002'";
      var riskDescribe = easyExecSql(strSql);

      if (riskDescribe == "M") {
        top.mainRisk = arrGrpRisk[i][1];
        break;
      }
    }
  }

  // ��ȡѡ������ֺͼ���Ͷ��������
  for (i=0; i<arrGrpRisk.length; i++) {
    if (arrGrpRisk[i][1] == riskCode) {
      document.all("RiskCode").value = arrGrpRisk[i][1];
      document.all("GrpPolNo").value = arrGrpRisk[i][0];

      if (arrGrpRisk[i][1] == top.mainRisk) {
        // top.mainPolNo = "";
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
  	
		var sqlid7="ProposalInputSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);// ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(tPolNo);// ָ������Ĳ���
		mySql7.addSubPara(cRiskCode);// ָ������Ĳ���
	   var strSql=mySql7.getString();	
	
// var strSql = "select RiskCode from LCGrpPol where GrpProposalNo = '" + tPolNo
// + "' and trim(RiskCode) in trim((select Code1 from LDCode1 where Code = '" +
// cRiskCode + "'))";
    return true;
  }
  else {
  	
			var sqlid8="ProposalInputSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql8.setSqlId(sqlid8);// ָ��ʹ�õ�Sql��id
		mySql8.addSubPara(tPolNo);// ָ������Ĳ���
		mySql8.addSubPara(cRiskCode);// ָ������Ĳ���
	   var strSql=mySql8.getString();	
	
// var strSql = "select RiskCode from LCPol where PolNo = '" + tPolNo
// + "' and trim(RiskCode) in trim((select Code1 from LDCode1 where Code = '" +
// cRiskCode + "'))";
  }

  return easyQueryVer3(strSql);
}

/**
 * �����ص�����ѡ�����
 */
function gotoInputPage() {
  // �����¸���Ͷ����
  if (mGrpFlag == "1") {
    // top.fraInterface.window.location =
	// "../app/ProposalGrpInput.jsp?LoadFlag=" + LoadFlag;
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
//<!--add by yaory-2005-7-11-->
function addRisk()
{
 parent.fraInterface.window.location.href="ProposalInput.jsp?LoadFlag=1&ContType=1&scantype=null&MissionID=00000000000000008139&riskcode="+fm.RiskCode.value;
 return;
}
function addAppRisk()
{
// alert("ok");
}
//<!--end add-->
/*******************************************************************************
 * ���ݲ�ͬ������,��ȡ��ͬ�Ĵ��� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */

var isReturnFromRiskPage = false;

function getRiskInput(cRiskCode, cFlag, needSetValue) {
   if (typeof(needSetValue) == "undefined"){
	   needSetValue = "";
   }
	
   var tPolNo = "";
// var tRiskName = fm.RiskCodeName.value;
   convertFlag(cFlag);
   var tProposalContNo = fm.ProposalContNo.value;
   var urlStr = "";
    
   var sqlid79="ProposalInputSql79";
   var mySql79=new SqlClass();
   mySql79.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
   mySql79.setSqlId(sqlid79);// ָ��ʹ�õ�Sql��id
   mySql79.addSubPara(cRiskCode);// ָ������Ĳ���
   var strSql79=mySql79.getString();	
   var RiskInterface79 = easyExecSql(strSql79);
   if(RiskInterface79 != null && RiskInterface79[0][0]==1){
	   var js_element=document.createElement("script");//����Ǵ���һ��script��ǩ
	   js_element.setAttribute("type","text/javascript");//�ű��õ���javascript
	   js_element.setAttribute("src","../riskinput/RiskProductdef.js");//script��ǩ������b.js�ļ�
	   document.getElementsByTagName("head")[0].appendChild(js_element);//��script��ǩ���뵽<head></head>��
	   urlStr = "../riskinput/RiskProductdef.jsp?riskcode="+ cRiskCode+"&ProposalContNo="+tProposalContNo;
	   var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	   var mSql = "select riskname from lmriskapp where riskcode='"+cRiskCode+"'";
	   mRiskName = easyExecSql(mSql);
	   
	   //showInfo = window.showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;dialogTop:-800;dialogLeft:-800;resizable=1");
	   var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=1;      //�������ڵĿ��; 
		var iHeight=1;     //�������ڵĸ߶�; 
		var iTop = 1; //��ô��ڵĴ�ֱλ�� 
		var iLeft = 1;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		//showInfo.focus();

	   jRiskCode = cRiskCode;
	   jStandbyFlag1 = '01';
	   initAllInput();
	   
   }else{
    // �״ν��뼯���¸���¼��
		var sqlid9="ProposalInputSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql9.setSqlId(sqlid9);// ָ��ʹ�õ�Sql��id
		mySql9.addSubPara(cRiskCode);// ָ������Ĳ���
	    var strSql=mySql9.getString();	
	    var RiskInterface = easyExecSql(strSql);
	    if (LoadFlag == "5" && scantype == "scan")
	    {
	        mRollSpeedOperator = fm.RollSpeedOperator.value;
	        mRollSpeedSelf = fm.RollSpeedSelf.value;
	        mRollSpeedBase = fm.RollSpeedBase.value;
	    }
	    if( mGrpFlag == "0" )       // ����Ͷ����
	        urlStr = "../riskinput/Risk" + RiskInterface+ ".jsp?riskcode="+ cRiskCode+"&ProposalContNo="+tProposalContNo+"&needSetValue="+needSetValue;
	    if( mGrpFlag == "1" )       // �����¸���Ͷ����
	        urlStr = "../riskgrp/Risk" + RiskInterface+ ".jsp?riskcode="+ cRiskCode+"&ProposalContNo="+tProposalContNo+"&needSetValue="+needSetValue;
	    if( mGrpFlag == "3" )       // ����Ͷ��������
	        urlStr = "../riskinput/Risk" + RiskInterface+ ".jsp?riskcode="+ cRiskCode+"&ProposalContNo="+tProposalContNo+"&needSetValue="+needSetValue;
		
	    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	    var mSql = "select riskname from lmriskapp where riskcode='"+cRiskCode+"'";
	    mRiskName = easyExecSql(mSql);
	    // ��ȡ���ֵĽ�������
	    //showInfo = window.showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;dialogTop:-800;dialogLeft:-800;resizable=1");
 
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=1;      //�������ڵĿ��; 
		var iHeight=1;     //�������ڵĸ߶�; 
		var iTop = 1; //��ô��ڵĴ�ֱλ�� 
		var iLeft = 1;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		//showInfo.focus();

 
   }
   if(isReturnFromRiskPage)
	   returnFromRiskInput();
   return;
}

function returnFromRiskInput(cRiskCode, needSetValue){
    if(LoadFlag=="1"){         // Ŀǰֻ�Ը���Ͷ����ֱ��¼��������
        document.all("RiskCodeName").value = mRiskName;
    }
    // alert('1');
    // .setAttribute('title',\"" + newData + "\")
		// document.all('RiskCode').setAttribute = ('readonly','');
    // ���ղ�ͬ��LoadFlag���в�ͬ�Ĵ���
    // �õ�����ĵ���λ��,Ĭ��Ϊ1,��ʾ���˱���ֱ��¼��.
    /***************************************************************************
	 * LoadFlag=1 -- ����Ͷ����ֱ��¼�� LoadFlag=2 -- �����¸���Ͷ����¼�� LoadFlag=3 -- ����Ͷ������ϸ��ѯ
	 * LoadFlag=4 -- �����¸���Ͷ������ϸ��ѯ LoadFlag=5 -- ���� LoadFlag=6 -- ��ѯ LoadFlag=7 --
	 * ��ȫ�±����� LoadFlag=8 -- ��ȫ���������� LoadFlag=9 -- ������������ LoadFlag=10-- ��������
	 * LoadFlag=13-- ������Ͷ���������޸� LoadFlag=16-- ������Ͷ������ѯ LoadFlag=25-- �˹��˱��޸�Ͷ����
	 * LoadFlag=99-- �涯����
	 * 
	 **************************************************************************/
  if(LoadFlag=="1"){
  // //У���ǲ������� add by yaory
// strSql = "select subriskflag from lmriskapp where riskcode='"+cRiskCode+"'";
// var mark = easyExecSql(strSql);
    showDiv(inputButton, "true");
    showDiv(divInputContButton, "true"); // rollback by yaory
// if(mark=='S'){
// showDiv(divInputYaory2Button, "true");}
// if(mark=='M'){
// showDiv(divInputYaoryButton, "true");}
// ///////////add by yaory for query how many records if 0 then button-ADDapp is
// unsee.
// strSql = "select * from lmriskrela where riskcode='"+cRiskCode+"'";
// var queryAppRisk = easyExecSql(strSql);
// //alert(queryAppRisk);
// if(queryAppRisk==null)
// {
// fm.riskbutton31.style.display='none';
// fm.riskbutton32.style.display='none';
// }

  }

    if (LoadFlag == "2"){
        var aGrpContNo=parent.VD.gVSwitch.getVar( "GrpContNo" );
        if(isSubRisk(cRiskCode)){
          document.all('MainPolNo').value=mainRiskPolNo;
        }
		
		var sqlid10="ProposalInputSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql10.setSqlId(sqlid10);// ָ��ʹ�õ�Sql��id
		mySql10.addSubPara(cRiskCode);// ָ������Ĳ���
		mySql10.addSubPara(aGrpContNo);// ָ������Ĳ���
	    strSql=mySql10.getString();	
		
      // strSql = "select PayIntv from LCGrpPol where RiskCode = '" +
		// cRiskCode + "' and GrpContNo ='"+aGrpContNo+"'";
        var PayIntv = easyExecSql(strSql);
        try{
          fm.PayIntv.value=PayIntv;
        }
        catch(ex){
        }
    showDiv(inputButton, "true");
// showDiv(divInputContButton, "false");
    showDiv(divGrpContButton, "true");
    showDiv(inputQuest, "false");
    getContInput();

    }


    if (LoadFlag == "3") {
      document.all("SaleChnl").readOnly = false;
    document.all("SaleChnl").className = "code";
    document.all("SaleChnl").ondblclick= showSaleChnl;
    showDiv(inputButton, "true");
    divApproveModifyContButton.style.display="";
    }

  if(LoadFlag=="4"){
    // showDiv(inputQuest, "true");
  }
  if(LoadFlag=="5"){
    showDiv(inputQuest, "true");
  }

  if(LoadFlag=="6"){
    // showDiv(inputButton, "true");
    // showDiv(divInputContButton, "true");
  }

  if(LoadFlag=="7"){
    showDiv(inputButton, "true");
    showDiv(divBqNSButton, "true");
  }

  if(LoadFlag=="8"){
    showDiv(inputButton, "true");
    showDiv(divInputContButtonBQ, "true");
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
    showDiv(divInputContButton, "false");
    showDiv(divUWContButton,"true");
  }

  if(LoadFlag=="14"){
    showDiv(inputButton, "true");
    showDiv(divInputContButton, "true");
 }

  if(LoadFlag=="16"){
    // showDiv(inputButton, "true");
    // showDiv(divInputContButton, "true");
    showDiv(divUWContButton,"true");
  }

  if(LoadFlag=="23"){
    showDiv(inputButton, "true");
    showDiv(divInputContButton, "true");
  }

  

    if (LoadFlag == "99")
    {
    showDiv(inputButton, "false");
    showDiv(inputQuest, "false");
    showDiv(autoMoveButton, "true");
    }

  try {
    // ��ʼ��������Ϣ
      emptyForm();

    // �������󵥷ſ���������ֻ�������ƣ�by Minim at 2003-11-24
    document.all("SaleChnl").readOnly = false;
    document.all("SaleChnl").className = "code";
    document.all("SaleChnl").ondblclick= showSaleChnl;

    // ��ɨ���¼��
    if (typeof(type)!="undefined" && type=="noScan") {
// document.all("PrtNo").readOnly = false;
// document.all("PrtNo").className = "common";

      // ͨ���б�����������ҵ�����
	  
	  		var sqlid11="ProposalInputSql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql11.setSqlId(sqlid11);// ָ��ʹ�õ�Sql��id
		mySql11.addSubPara(cRiskCode);// ָ������Ĳ���
		mySql11.addSubPara(aGrpContNo);// ָ������Ĳ���
	    strSql=mySql11.getString();	
	  
// strSql = "select SubRiskFlag from LMRiskApp where RiskCode = '"
// + cRiskCode + "' ";
      var riskDescribe = easyExecSql(strSql);

// if (riskDescribe == "M") {
// top.mainPolNo = "";
// }

    }
    if(scantype=="scan") {
      // document.all("PrtNo").value = prtNo;
      document.all("RiskCode").value=cRiskCode;
      ScanViewCreate();
      setFocus();
      
    }
    
  }
  catch(e){}
    try{getContInput();}catch(e){}
    
    if(LoadFlag=="25"){
        showDiv(inputButton, "true");
        showDiv(divUWContButton, "true");
        var polNo = parent.VD.gVSwitch.getVar( "PolNo" );
        getAppntAndInsuredForChangeRiskPlan(polNo);
        fm.target='fraSubmit1';
    }
    
    try{getContInputnew();}catch(e){}
    
    // �������ֺ�ӡˢ����Ϣ
    document.all("RiskCode").value = cRiskCode;
    showRiskParamCodeName();
// try{
// document.all("RiskCodeName").value = tRiskName;
// }catch(ex){}
        // alert(fm.RiskCode.value);
    // �������ƶ���ӡˢ�ţ��Է����涯¼��
    // document.all("PrtNo").focus();

  // ��ʼ���������������
  try {
    // alert("prtNo=="+prtNo);
    prtNo = document.all("PrtNo").value;
    var riskType = prtNo.substring(2, 4);
    // tongmeng 2009-04-24 Modify
    // �˴��߼�������
    /*
	 * if (riskType == "11") { document.all("SaleChnl").value = "02"; } else if
	 * (riskType == "12") { document.all("SaleChnl").value = "01"; } else if (riskType ==
	 * "15") { document.all("SaleChnl").value = "03"; } else if (riskType == "16") {
	 * document.all("SaleChnl").value = "02"; document.all("SaleChnl").readOnly = false;
	 * document.all("SaleChnl").className = "code"; document.all("SaleChnl").ondblclick=
	 * showSaleChnl; }
	 */
  }
  catch(e) {}
// if (!(typeof(top.type)!="undefined" && (top.type=="ChangePlan" ||
// top.type=="SubChangePlan"))) {
// //���Ƿ�ָ����Ч������¼��ʱʧЧ
// document.all("SpecifyValiDate").readOnly = true;
// document.all("SpecifyValiDate").className = "readOnly";
// document.all("SpecifyValiDate").ondblclick = "";
// document.all("SpecifyValiDate").onkeyup = "";
// //document.all("SpecifyValiDate").value = "N";
// document.all("SpecifyValiDate").tabIndex = -1;
// }
  if(mainRiskPolNo==""){
    mainRiskPolNo=parent.VD.gVSwitch.getVar("mainRiskPolNo");
  }
  if( mCurOperateFlag == "1" || mCurOperateFlag=="2") {             // ¼��
        // �����¸���Ͷ����
        if( mGrpFlag == "1" )   {
            getGrpPolInfo();                       // ���뼯�岿����Ϣ
            // getPayRuleInfo(); //hl

            // ֧�ּ����¸��ˣ�¼�����֤�Ŵ�����������
            document.all("IDNo").onblur = grpGetBirthdayByIdno;
            // ��ʱ��ȥ��ȥ���������ť,�涯����ʱ���� hl
// if(LoadFlag!="99")
// inputQuest.style.display = "none";

            // ��ȡ�ü���������������Ϣ
            // alert("judging if the RiskCode input has been input in group
			// info.");
            // if (!queryGrpPol(document.all("PrtNo").value, cRiskCode)) {
            // alert("�����ŵ�û��¼��������֣������¸��˲�����¼�룡");
            // document.all("RiskCode").value="";
            // //alert("now go to the new location- ProposalGrpInput.jsp");
            // top.fraInterface.window.location =
			// "./ProposalGrpInput.jsp?LoadFlag=" + LoadFlag;
            // //alert("top.location has been altered");
            // return false; //hezy
            // }
        }
        // if(initDealForSpecRisk(cRiskCode)==false)//���������ڳ�ʼ��ʱ�����⴦��-houzm���
        // {
            // alert("���֣�"+cRiskCode+"��ʼ��ʱ���⴦��ʧ�ܣ�");
            // return false;
        // }
      // tongmeng 2008-07-02 ADD
      // ֧�ֶ�����¼��
      // alert('tm test');
       showMultRiskGrid(cRiskCode);
       
       
       
        // tongmeng
   // alert("fuhe");
    if(ifTLrisk(cRiskCode))
	  {  
	  	
	  	try{
	  // alert("fuhe1");
		      // DivChooseDuty.style.display = "none";
			    // DutyGrid.checkBoxAll();
			    // tongmeng 2010-11-16 Modify
			    // ֻ�б�����Ĳ�Ʒ�ų�ʼ��Ͷ�ʽ���
			    // alert("11111"+tProposalContNo);
		// alert("fuhe2");
			    if(ifSavedRiskCode(cRiskCode))
			    {
			    	divInvestPlanRate.style.display="";
			     showDetailForCont2(cRiskCode);	
			     
			    	InvestPlanInputInit(1);	
			  	}
			  	else
			  	{
			  		divInvestPlanRate.style.display="none";
			  	}
			    
			   }
			   catch(e)
			   {
			   	}
					
		// alert("fuhe3");
					// alert("3111");
								
	  }	      	  
	  
     // alert('8');
        document.all('RiskCode').readOnly= true;
   // document.all('RiskCode').setAttribute = ('readonly','true');
        // ���������ڳ�ʼ��ʱ�����⴦����չ-guoxiang add at 2004-9-6 16:33
    if(initDealForSpecRiskEx(cRiskCode)==false)
        {
            alert("���֣�"+cRiskCode+"��ʼ��ʱ���⴦��ʧ�ܣ�");
            return false;
        }
        try{
          document.all('SelPolNo').value=parent.VD.gVSwitch.getVar("PolNo");
          if (document.all('SelPolNo').value=='false')
          {
            document.all('SelPolNo').value='';
          }
          if(document.all('SelPolNo').value!=''){ // ��������
          
		var sqlid12="ProposalInputSql12";
		var mySql12=new SqlClass();
		mySql12.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql12.setSqlId(sqlid12);// ָ��ʹ�õ�Sql��id
		mySql12.addSubPara(document.all('SelPolNo').value);// ָ������Ĳ���
	    var tSql=mySql12.getString();	
		  
           // var tSql="select riskcode from lcpol where
			// polno='"+document.all('SelPolNo').value+"'";
            var arr=easyExecSql(tSql);
            if(arr[0]!=cRiskCode){
              document.all('SelPolNo').value='';
            }
          }
      }
      catch(ex) {}
        if( isSubRisk( cRiskCode ) == true&&document.all('SelPolNo').value=="" ) {   // ����
          // ��������������գ�����ջ�������ձ�����
          if (LoadFlag != "8" && LoadFlag != "2") {
              // top.mainPolNo = ""; //hezy add
              mainRiskPolNo = "";
            }
            // edit by yaory tPolNo = getMainRiskNo(cRiskCode);
			// //����¼�븽�յĴ���,�õ����ձ�������
            // alert("tPolNo:"+tPolNo);
// edit by yaory if (!checkRiskRelation(tPolNo, cRiskCode)) {
// alert("�����հ�����ϵ������������պŲ��ܴ���������գ�");
// gotoInputPage();
// //top.mainPolNo = "";
// mainRiskPolNo = "";
// return false;
// }
// -----------------------------------------------------------------------
         if(cRiskCode=='121301'||cRiskCode=='321601')// ��ʼ������ĸ�����Ϣ--houzm���--�ɵ������Ϊһ������
         {
            // if(cRiskCode=='121301')
            // {
                    // if (!initPrivateRiskInfo121301(tPolNo))
                    // {
                    // gotoInputPage();
                    // return false;
                    // }
                    // }
                    // if(cRiskCode=='321601')
            // {
                    // if (!initPrivateRiskInfo321601(tPolNo))
                    // {
                    // gotoInputPage();
                    // return false;
                    // }
                    // }
         }
         else
         {

                // //��ʼ��������Ϣ
                        // if (!initPrivateRiskInfo(tPolNo))
                        // {
                        // gotoInputPage();
                        // return false;
                        // }
         }

// try {} catch(ex1) { alert( "��ʼ�����ֳ���" + ex1 ); }
        }
        // ��ʼ���ۿ���Ϣ
          Discount();
        //return false;

    }
	// fm.ProposalContNo.value=tProposalContNo;
    mCurOperateFlag = "";
    mGrpFlag = "";
    
    if (needSetValue != "") {
	    // �ص���ѯҳ��ProposalQueryDetail.jsp���и�ֵ���޲�ѯ��ֵʱ�����쳣����
	    try{parent.fraTitle.setValue();}catch(e){}
    }
    
    showRiskParamCodeName();
    return;
}

/*******************************************************************************
 * �жϸ������Ƿ��Ǹ���,������ȷ���ȿ���������,�ֿ��������յĴ��� ���� �� ���ִ��� ����ֵ�� ��
 * ********************************************************************
 */
function isSubRisk(cRiskCode) {
  // alert(cRiskCode);
  if (cRiskCode=="")
  {
   return false;
  }
  
  		var sqlid13="ProposalInputSql13";
		var mySql13=new SqlClass();
		mySql13.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql13.setSqlId(sqlid13);// ָ��ʹ�õ�Sql��id
		mySql13.addSubPara(cRiskCode);// ָ������Ĳ���
	    var tSql13=mySql13.getString();	
  
  var arrQueryResult = easyExecSql(tSql13, 1, 0);
  // var arrQueryResult = easyExecSql("select SubRiskFlag from LMRiskApp where
	// RiskCode='" + cRiskCode + "'", 1, 0);

    if(arrQueryResult[0] == "S")    // ��Ҫת�ɴ�д
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

/*******************************************************************************
 * ����¼�븽�յĴ���,�õ����ձ������� ���� �� ���ִ��� ����ֵ�� ��
 * ********************************************************************
 */
function getMainRiskNo(cRiskCode) {
    var urlStr = "../app/MainRiskNoInput.jsp";
    var tPolNo="";
  if (typeof(mainRiskPolNo)!="undefined" && mainRiskPolNo!="") {
    // tPolNo = top.mainPolNo;
    tPolNo = mainRiskPolNo;
  }
  else{
    //tPolNo = window.showModalDialog(urlStr,tPolNo,"status:no;help:0;close:0;dialogWidth:310px;dialogHeight:100px;center:yes;");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=310;      //�������ڵĿ��; 
	var iHeight=100;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	tPolNo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	tPolNo.focus();
    // top.mainPolNo = tPolNo;
    mainRiskPolNo = tPolNo;
  }

    return tPolNo;
}

/*******************************************************************************
 * ��ʼ��������Ϣ ���� �� Ͷ������ ����ֵ�� ��
 * ********************************************************************
 */
function initPrivateRiskInfo(cPolNo) {
    if(cPolNo=="") {
        alert("û�����ձ�����,���ܽ��и�����¼��!");
        mCurOperateFlag="";        // ��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.
        return false
    }

    var arrLCPol = new Array();
    var arrQueryResult = null;
    // ��������Ϣ����
	
	  	var sqlid14="ProposalInputSql14";
		var mySql14=new SqlClass();
		mySql14.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql14.setSqlId(sqlid14);// ָ��ʹ�õ�Sql��id
		mySql14.addSubPara(cPolNo);// ָ������Ĳ���
	    var sql=mySql14.getString();	
	
// var sql = "select * from lcpol where polno='" + cPolNo + "' "
// + "and riskcode in "
// + "( select riskcode from LMRiskApp where SubRiskFlag = 'M' )";

    // alert(sql);
    arrQueryResult = easyExecSql( sql , 1, 0);

    if (arrQueryResult == null) {
        mCurOperateFlag="";        // ��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.

        // top.mainPolNo = "";
        mainRiskPolNo = "";

        alert("��ȡ������Ϣʧ��,���ܽ��и�����¼��!");
        return false
    }

    arrLCPol = arrQueryResult[0];
    displayPol( arrLCPol );

    document.all("MainPolNo").value = cPolNo;
    var tAR;

    // Ͷ������Ϣ
    if (arrLCPol[6]=="2") {     // ����Ͷ������Ϣ
      arrQueryResult = null;
	  
	  	 var sqlid15="ProposalInputSql15";
		var mySql15=new SqlClass();
		mySql15.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql15.setSqlId(sqlid15);// ָ��ʹ�õ�Sql��id
		mySql15.addSubPara(arrLCPol[0]);// ָ������Ĳ���
		mySql15.addSubPara(arrLCPol[28]);// ָ������Ĳ���
	    var sql15=mySql15.getString();	
		
	   arrQueryResult = easyExecSql(sql15, 1, 0);
     // arrQueryResult = easyExecSql("select * from lcgrpappnt where
		// grpcontno='"+arrLCPol[0]+"'"+" and customerno='"+arrLCPol[28]+"'", 1,
		// 0);
      tAR = arrQueryResult[0];
      displayPolAppntGrp(tAR);
    } else {                     // ����Ͷ������Ϣ
      arrQueryResult = null;
	  
	  	 var sqlid16="ProposalInputSql16";
		var mySql16=new SqlClass();
		mySql16.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql16.setSqlId(sqlid16);// ָ��ʹ�õ�Sql��id
		mySql16.addSubPara(cPolNo);// ָ������Ĳ���
		mySql16.addSubPara(arrLCPol[28]);// ָ������Ĳ���
	    var sql16=mySql16.getString();	
	  
	  arrQueryResult = easyExecSql(sql16, 1, 0);
      // arrQueryResult = easyExecSql("select * from lcappntind where
		// polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[28]+"'", 1, 0);
      tAR = arrQueryResult[0];
      displayPolAppnt(tAR);
    }

    // ��������Ϣ����
    if (arrLCPol[21] == arrLCPol[28]) {
      document.all("SamePersonFlag").checked = true;
        parent.fraInterface.isSamePersonQuery();
    parent.fraInterface.document.all("CustomerNo").value = arrLCPol[21];
    }
    // else {
        arrQueryResult = null;
		
		var sqlid17="ProposalInputSql17";
		var mySql17=new SqlClass();
		mySql17.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql17.setSqlId(sqlid17);// ָ��ʹ�õ�Sql��id
		mySql17.addSubPara(arrLCPol[2]);// ָ������Ĳ���
		mySql17.addSubPara(arrLCPol[21]);// ָ������Ĳ���
	    var sql17=mySql17.getString();	
		
		arrQueryResult = easyExecSql(sql17, 1, 0);
        // arrQueryResult = easyExecSql("select * from lcinsured where
		// contno='"+arrLCPol[2]+"'"+" and insuredno='"+arrLCPol[21]+"'", 1, 0);
        tAR = arrQueryResult[0];
        displayPolInsured(tAR);
    // }
    return true;
}

/*******************************************************************************
 * У��Ͷ���������� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function verifyProposal() {

  var passVerify = true;

  // ��ȫ����������
  if (LoadFlag == "8") {
    var newCValidate = document.all('CValiDate').value;
    /*
	 * if (oldCValidate.substring(4) != newCValidate.substring(4)) {
	 * alert("ָ������Ч���ڱ�����������Ч���ڵ������Ӧ��"); return false; }
	 */

    // ���������������ղ�����ô����
    // if (oldCValidate == newCValidate) {
    // alert("�������������չ��򣬲���ָ��Ϊ������Ч����");
    // return false;
    // }

    if (!confirm("ָ���ĸ�������Ч����Ϊ��(" + newCValidate + ")��ȷ����")) {
      return false;
    }
  }

  if(fm.AppntRelationToInsured.value=="00"){
    if(fm.AppntCustomerNo.value!= fm.CustomerNo.value){
      alert("Ͷ�����뱻���˹�ϵΪ���ˣ����ͻ��Ų�һ��");
      return false;
    }
  }
   if(needVerifyRiskcode()==true)
   {
        // if(verifyInput() == false) passVerify = false;
		// alert("5555555");
        BnfGrid.delBlankLine();

        if(BnfGrid.checkValue("BnfGrid") == false) passVerify = false;

         // У�鵥֤�Ƿ񷢷Ÿ�ҵ��Ա
         if (!verifyPrtNo(document.all("PrtNo").value)) passVerify = false;
    }
    try {
      var strChkIdNo = "";

          // ��������Ա�У�����֤��
          if (document.all("AppntIDType").value == "0")
            strChkIdNo = chkIdNo(document.all("AppntIDNo").value, document.all("AppntBirthday").value, document.all("AppntSex").value);
          if (document.all("IDType").value == "0")
            strChkIdNo = chkIdNo(document.all("IDNo").value, document.all("Birthday").value, document.all("Sex").value);

          if (strChkIdNo != "") {
            alert(strChkIdNo);
            passVerify = false;


      }

      // У��ְҵ��ְҵ����
// var arrCode = new Array();
// arrCode = verifyCode("ְҵ�����֣�", document.all("AppntWorkType").value,
// "code:OccupationCode", 1);
// if (arrCode!=true && document.all("AppntOccupationCode").value!=arrCode[0]) {
// alert("Ͷ����ְҵ��ְҵ���벻ƥ�䣡");
// passVerify = false;
// }
// arrCode = verifyCode("ְҵ�����֣�", document.all("WorkType").value,
// "code:OccupationCode", 1);
// if (arrCode!=true && document.all("OccupationCode").value!=arrCode[0]) {
// alert("������ְҵ��ְҵ���벻ƥ�䣡");
// passVerify = false;
// }

      // У���������
      var i;
      var sumLiveBnf = new Array();
      var sumDeadBnf = new Array();
			// tongmeng 2007-12-26 Modify
			// �޸�������У��
			var sumLiveBnf_final = 0;
			var sumDeadBnf_final = 0; 
			var LiveBnfFlag=false;
			var DeadBnfFlag=false;

      for (i=0; i<BnfGrid.mulLineCount; i++)
      {
      	// tongmeng 2007-12-28 add
      	// ������������˵�У��
      	
    // alert(i);
		// alert(BnfGrid.getRowColData(i, 1));
  // BnfGrid.setRowColData(i, 1, "1");

        if (BnfGrid.getRowColData(i, 7)==null||BnfGrid.getRowColData(i, 7)=='')
        {
            BnfGrid.setRowColData(i, 7,"1");
        }
        if (BnfGrid.getRowColData(i, 8)==null||BnfGrid.getRowColData(i, 8)=='')
        {
            BnfGrid.setRowColData(i, 8,"1");
        }
				if(BnfGrid.getRowColData(i, 1)==null||BnfGrid.getRowColData(i, 1)=='')
				{
					// Ĭ��Ϊ���������
					BnfGrid.setRowColData(i, 1, "1");
				}
				// alert(BnfGrid.getRowColData(i,
				// 1)+":"+BnfGrid.getRowColData(i, 6));
				//
				if(BnfGrid.getRowColData(i, 1) == "1")
				{	
					if(BnfGrid.getRowColData(i, 6)=="00")
					{
						alert('��������˲����Ǳ���!');
						return false;
					}
				}
				// tongmeng 2008-12-18 Modify
				// �޸�У������������
        if (BnfGrid.getRowColData(i, 1) == "0")
        {
        	LiveBnfFlag = true;
        	
          if (typeof(sumLiveBnf[parseInt(BnfGrid.getRowColData(i, 7))]) == "undefined")
            sumLiveBnf[parseInt(BnfGrid.getRowColData(i, 7))] = 0;
          sumLiveBnf[parseInt(BnfGrid.getRowColData(i, 7))] = sumLiveBnf[parseInt(BnfGrid.getRowColData(i, 7))] + parseFloat(BnfGrid.getRowColData(i, 8))*100;
          
          // sumLiveBnf_final = sumLiveBnf_final +
			// parseFloat(BnfGrid.getRowColData(i, 8))*100;
        }
        else if (BnfGrid.getRowColData(i, 1) == "1")
        {
        	// alert("6666666");
          if (typeof(sumDeadBnf[parseInt(BnfGrid.getRowColData(i, 7))]) == "undefined")
          sumDeadBnf[parseInt(BnfGrid.getRowColData(i, 7))] = 0;
          sumDeadBnf[parseInt(BnfGrid.getRowColData(i, 7))] = sumDeadBnf[parseInt(BnfGrid.getRowColData(i, 7))] + parseFloat(BnfGrid.getRowColData(i, 8))*100;
        	
        	// sumDeadBnf_final = sumDeadBnf_final +
			// parseFloat(BnfGrid.getRowColData(i, 8))*100;
        	DeadBnfFlag = true;
        }
      }
     
      if((fm.RiskCode.value == "121505") || (fm.RiskCode.value == "112212"))
      {
    	  var i;
    	  for (i=0; i<BnfGrid.mulLineCount; i++)
          {
				if(BnfGrid.getRowColData(i, 1) == "0")
				{	
					if(BnfGrid.getRowColData(i, 6)!="00")
					{
						alert('���������������˱���Ϊ����!');
						return false;
					}
				}
          }
      }
      
      var tempcode=fm.RiskCode.value;
   // add by yaory ����������ֶ�Ϳ��Դ���1
	// alert(sumLiveBnf_final+":"+sumDeadBnf_final);
	if(tempcode =="314301")
	{
		if(LiveBnfFlag)
		{
			alert("������ֻ�������������!");
			passVerify = false;
			return false;
		}
	}
	// û��������ε� ������ ¼�����������
	if(tempcode =="111801"||tempcode =="121301"||tempcode =="121501"||tempcode =="121704"||
		tempcode =="121705"||tempcode =="121801"||tempcode =="131601"||tempcode =="121505"||
		tempcode =="121504"||tempcode =="111802"||tempcode =="121507"||tempcode =="121703"||
		tempcode =="121506"||tempcode =="121701"||tempcode =="121702" )
	{
		if(DeadBnfFlag)
		{
			alert("������ֻ��������������!");
			passVerify = false;
			return false;
		}
	}
   if(tempcode!="00150000" && tempcode!="00146000" && tempcode!="00144000")
   {
   	 /*
		 * if(LiveBnfFlag) { if(parseFloat(sumLiveBnf_final)/100>1) {
		 * alert("���������˵����������Ϊ��" + sumLiveBnf_final+ " ������100%�������ύ��");
		 * 
		 * passVerify = false; return false;
		 *  } else if(parseFloat(sumLiveBnf_final)/100<1) {
		 * alert("���������˵����������Ϊ��" + sumLiveBnf_final+ " ��С��100%�������ύ��");
		 * 
		 * passVerify = false; return false;
		 *  } } if(DeadBnfFlag) { if(parseFloat(sumDeadBnf_final)/100>1) {
		 * alert("��������˵����������Ϊ��" + sumDeadBnf_final+ " ������100%�������ύ��");
		 * 
		 * passVerify = false; return false;
		 *  } else if(parseFloat(sumDeadBnf_final)/100<1) {
		 * alert("��������˵����������Ϊ��" + sumDeadBnf_final+ " ��С��100%�������ύ��");
		 * 
		 * passVerify = false; return false;
		 *  } }
		 */
   	
      for (i=0; i<sumLiveBnf.length; i++)
      {
        if (typeof(sumLiveBnf[i])!="undefined"){sumLiveBnf[i]=parseFloat(sumLiveBnf[i])/100;}
        if (typeof(sumLiveBnf[i])!="undefined" && sumLiveBnf[i]>1)
        {
            alert("��������������˳�� " + i + " �����������Ϊ��" + sumLiveBnf[i]+ " ������100%�������ύ��");

            passVerify = false;
            return false;
        }
        else if (typeof(sumLiveBnf[i])!="undefined" && sumLiveBnf[i]<1)
        {
            alert("ע�⣺��������������˳�� " + i + " �����������Ϊ��" + sumLiveBnf[i] + " ��С��100%");

            passVerify = false;
            return false;
        }
      }

      for (i=0; i<sumDeadBnf.length; i++)
      {
        if (typeof(sumDeadBnf[i])!="undefined"){sumDeadBnf[i]=parseFloat(sumDeadBnf[i])/100;}
        if (typeof(sumDeadBnf[i])!="undefined" && sumDeadBnf[i]>1)
        {

          alert("��������������˳�� " + i + " �����������Ϊ��" + sumDeadBnf[i] + " ������100%�������ύ��");
          passVerify = false;
          return false;
        }
        else if (typeof(sumDeadBnf[i])!="undefined" && sumDeadBnf[i]<1)
        {
            alert("ע�⣺��������������˳�� " + i + " �����������Ϊ��" + sumDeadBnf[i] + " ��С��100%");
            passVerify = false;
            return false;
        }
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

    // У��ͻ��Ƿ�����
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

// У��ͻ��Ƿ�����
function isDeath(CustomerNo) {
	
			var sqlid18="ProposalInputSql18";
		var mySql18=new SqlClass();
		mySql18.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql18.setSqlId(sqlid18);// ָ��ʹ�õ�Sql��id
		mySql18.addSubPara(CustomerNo);// ָ������Ĳ���
	    var strSql=mySql18.getString();	
	
  // var strSql = "select DeathDate from LDPerson where CustomerNo='" +
	// CustomerNo + "'";
  var arrResult = easyExecSql(strSql);

  if (arrResult == ""||arrResult == null) return false;
  else return true;
}
function dospecialrisk()
{
// var code=fm.RiskCode.value;
// ��ѯ������˳���

			var sqlid19="ProposalInputSql19";
		var mySql19=new SqlClass();
		mySql19.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql19.setSqlId(sqlid19);// ָ��ʹ�õ�Sql��id
		mySql19.addSubPara(prtNo);// ָ������Ĳ���
		mySql19.addSubPara(fm.CustomerNo.value);// ָ������Ĳ���
	    var rSql=mySql19.getString();	

// var rSql = "select sequenceno from lcinsured where contno='"+prtNo+"' and
// insuredno='"+fm.CustomerNo.value+"'";
var seque = easyExecSql(rSql);
// �ж������ǲ���00150000

			var sqlid20="ProposalInputSql20";
		var mySql20=new SqlClass();
		mySql20.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql20.setSqlId(sqlid20);// ָ��ʹ�õ�Sql��id
		mySql20.addSubPara(prtNo);// ָ������Ĳ���
	    rSql=mySql20.getString();	

// rSql="select riskcode from lcpol where contno='"+prtNo+"' and
// polno=mainpolno";
var code= easyExecSql(rSql);
if(seque!=null && seque=="1" && code=="00150000")
{
    return 1;
}else{
   return 0;
}
}
/*******************************************************************************
 * �������Ͷ�������ύ ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function submitForm() {
	//��ֵdutygrid��premgrid
	/*
	if(null!=$("#dutyGrid1").datagrid('getData')){
		for(var i=0;i<$("#dutyGrid1").datagrid('getData').rows.length;i++){
			DutyGrid.addOne("DutyGrid");
			DutyGrid.setRowColData(0,1,$("#dutyGrid1").datagrid('getData').rows[i].DutyCode);
			DutyGrid.setRowColData(0,2,$("#dutyGrid1").datagrid('getData').rows[i].DutyName);
			DutyGrid.setRowColData(0,3,$("#dutyGrid1").datagrid('getData').rows[i].InsuYear);
			DutyGrid.setRowColData(0,4,$("#dutyGrid1").datagrid('getData').rows[i].InsuYearFlag);
			DutyGrid.setRowColData(0,5,$("#dutyGrid1").datagrid('getData').rows[i].PayYear);
			DutyGrid.setRowColData(0,6,$("#dutyGrid1").datagrid('getData').rows[i].PayYearFlag);
			DutyGrid.setRowColData(0,7,$("#dutyGrid1").datagrid('getData').rows[i].GetYear);
			DutyGrid.setRowColData(0,8,$("#dutyGrid1").datagrid('getData').rows[i].GetYearFlag);
			DutyGrid.setRowColData(0,9,$("#dutyGrid1").datagrid('getData').rows[i].Prem);
			DutyGrid.setRowColData(0,10,$("#dutyGrid1").datagrid('getData').rows[i].Amnt);
			DutyGrid.setRowColData(0,11,$("#dutyGrid1").datagrid('getData').rows[i].Mult);
			DutyGrid.checkBoxSel(1);
		}
	}
	if(null!=$("#payGrid").datagrid('getData')){
		for(var i=0;i<$("#payGrid").datagrid('getData').rows.length;i++){
			PremGrid.addOne("PremGrid");
			PremGrid.setRowColData(0,1,$("#payGrid").datagrid('getData').rows[i].DutyCode);
			PremGrid.setRowColData(0,2,$("#payGrid").datagrid('getData').rows[i].PayPlanCode);
			PremGrid.setRowColData(0,3,$("#payGrid").datagrid('getData').rows[i].PayPlanName);
			PremGrid.setRowColData(0,4,$("#payGrid").datagrid('getData').rows[i].Prem);
			PremGrid.setRowColData(0,5,$("#payGrid").datagrid('getData').rows[i].Currency);
			PremGrid.checkBoxSel(i+1);
		}
	}
	*/
	 var tCurrencyCode = "";
	 try{
	 tCurrencyCode = fm.CurrencyCode.value
	}
	catch(E)
	{
		 tCurrencyCode = "01";
		}
	   if(tCurrencyCode=="" || tCurrencyCode==null){
			alert("������Ϣ����Ϊ��");
			return false;
		}
	// ���ӶԲ���Ҫ¼����������ֵı���Ѳ���Ϊ�յ�У��
	
	    var sqlid21="ProposalInputSql21";
		var mySql21=new SqlClass();
		mySql21.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql21.setSqlId(sqlid21);// ָ��ʹ�õ�Sql��id
		mySql21.addSubPara(document.all("RiskCode").value);// ָ������Ĳ���
	    vSql=mySql21.getString();
	
	// vSql="select AmntFlag from lmduty where dutycode in (select dutycode from
	// lmriskduty where riskcode = '"+fm.RiskCode.value+"')";
	var tAmntFlag = easyExecSql(vSql);
	if(tAmntFlag != null && tAmntFlag == "1"){
		if((fm.Prem.value==""||fm.Prem.value==null)&&(fm.Amnt.value==""||fm.Amnt.value==null)){
			alert("����Ѳ��ܶ�Ϊ�գ�");
			return false;
		}
	}
	
	if(fm.RiskCode.value == "111302"||fm.RiskCode.value == "111303"){
		
		if(fm.SpecifyValiDate.value=="N" || fm.SpecifyValiDate.value==null){
			if(fm.CValiDate.value=="" || fm.CValiDate.value==null){
				
				fm.CValiDate.value=mCValiDate;
			}
		}
		
		if(fm.CValiDate.value=="" || fm.CValiDate.value==null){
			alert("������Ч���ڲ���Ϊ��");
			return false;
		}
		
	}
     // if(dospecialrisk()==1)//add by yaory ����00150000
        // {
            // alert("����00150000�ĸ�����ֻ�ܸ��ӵ��ڶ������ˣ�");
            // return;
     // }
    if (fm.RiskCode.value == "212403")
    {
        if (verifyInput2() == false)
            return;
    }

    // У������ۿۣ����ܴ���1С��0
    if (verifyFloatRate() == false)
    {
        return;
    }

    var index = BnfGrid.mulLineCount;
    var i;
    for(i=0;i<index;i++)
    {
        var idType=BnfGrid.getRowColData(i,4);
        if(idType=="0")
        {
            var idNum=BnfGrid.getRowColData(i,5);
            if(idNum==null||(!(idNum.length==15||idNum.length==18)))
            {
                alert("����������Ϣ ��"+(i+1)+"�е����֤������������!����������!");
                BnfGrid.setRowColData(i,5,"");
                BnfGrid.setFocus(i,5);
                return ;
            }
        }
 
        if (BnfGrid.getRowColData(i, 10) == "A") {
          if(ContType!="2")
          {
			// alert("7777777");
          BnfGrid.setRowColData(i, 2, document.all("AppntName").value);
          BnfGrid.setRowColData(i, 3, document.all("AppntSex").value);
          BnfGrid.setRowColData(i, 4, document.all("AppntIDType").value);
          BnfGrid.setRowColData(i, 5, document.all("AppntIDNo").value);
          BnfGrid.setRowColData(i, 6,parent.VD.gVSwitch.getVar( "RelationToInsured"));
          BnfGrid.setRowColData(i, 9, document.all("AppntHomeAddress").value);
          // hl
          // BnfGrid.setRowColData(i, 9, document.all("AppntNo").value);

          }
          else
          {
            alert("Ͷ����Ϊ���壬������Ϊ�����ˣ�")

          BnfGrid.setRowColData(i, 2, "");
          BnfGrid.setRowColData(i, 3, "");
          BnfGrid.setRowColData(i, 4, "");
          BnfGrid.setRowColData(i, 5, "");
          BnfGrid.setRowColData(i, 6, "");
          BnfGrid.setRowColData(i, 9, "");
          }
        }
        else if (BnfGrid.getRowColData(i,10)== "I") {
		// alert("8888888");
        BnfGrid.setRowColData(i, 2, document.all("Name").value);
        BnfGrid.setRowColData(i, 3, document.all("Sex").value);
        BnfGrid.setRowColData(i, 4, document.all("IDType").value);
        BnfGrid.setRowColData(i, 5, document.all("IDNo").value);
        BnfGrid.setRowColData(i, 6, "00");
        // BnfGrid.setRowColData(index-1, 8, document.all("AppntHomeAddress").value);
        // hl
        BnfGrid.setRowColData(i, 9, document.all("HomeAddress").value);
        // BnfGrid.setRowColData(i, 9, document.all("InsuredNo").value);
    // alert("document.all("InsuredNo")"+document.all("InsuredNo").value);
        }
    }
    

  // -------------------------------------------------Beg
  // �޸��ˣ�chenrong
  // �޸����ڣ�2006-03-13
  // ����ȡ����ֵ����ַ���
  if (setDutyKind() == "")
  {
      alert("��ȡ����ֵ����Ϊ�գ�������!");
      return;
  }
  // -------------------------------------------------End

  if(!chkDutyNo()){                  // У��CheckBox��ֻ����ͬʱѡ��һ��������
                                                                             // create
																				// by
																				// malong
																				// 2005-7-13
       return false;
    }
  

    if(!chkMult() || !checkMult())      // �жϷ����Ƿ�Ϊ�ջ�����
										// chkMult�����ڶ���������,checkMult�����ڵ���������,
                                                                            // create
																			// by
																			// malong
																			// 2005-7-8
  {
     return false;
  }
    
  if(fm.RiskCode.value!='00332000')
  {
 if(!chkDuty()){                  // У��280��ͬ����ѡ������
                                                                             // create
																				// by
																				// zhangyang
																				// 2005-7-29
       return false;
    }
 
 if(!chkPayEndYear()){           // У��ɷѷ�ʽΪ����ʱ���������ڲ���ѡ��
       return false;                    // create by zhangyang 2005-10-21
    }    
}

	if(DiscountGrid.checkValue("DiscountGrid") == false) 
    {
       return;
    }
  
    if(sign != 0)
    {
       alert("�벻Ҫ�ظ����!");
       return;
    }

    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

  var verifyGrade = "1";


  // ������������������⴦������
  // modify by malong 2005-7-8
  try 
  {
      if(specDealByRisk()==false)
      {  	  
    	  return;
      }

   }
   catch(e){}
   

  // ����������ڣ�����գ������֤��ȡ
  // try {checkBirthday(); } catch(e){}

    // У�鱻�����Ƿ�ͬͶ���ˣ���ͬ����и���
  try { verifySamePerson(); } catch(e) {}

        // У��¼������
    if( verifyProposal() == false ) return;

    if (trim(document.all('ProposalNo').value) != "") {
      alert("��Ͷ�������Ѿ����ڣ��������ٴ������������½���¼����棡");
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
    var tDutyCode;
    try{
        if(document.all('inpNeedDutyGrid').value==0)
        {
            tDutyCode = getDutyCode(fm.RiskCode.value);
            if (tDutyCode != null && tDutyCode != "null" && tDutyCode != "")
            {
                fm.action="../app/ProposalSave.jsp?DutyCode=" + tDutyCode;
            }
        }
    }
    catch(ex){}
    // Ϊ��ȫ���ӣ�add by Minim
    if (LoadFlag=="7" || LoadFlag=="8") {
        if (tDutyCode != null && tDutyCode != "null" && tDutyCode != ""){
            fm.action="../app/ProposalSave.jsp?BQFlag=2&EdorType="+EdorType+ "&DutyCode=" + tDutyCode;
        } else {
            fm.action="../app/ProposalSave.jsp?BQFlag=2&EdorType="+EdorType;
        }
      // document.all("BQFlag").value=BQFlag;
    }

    // Ϊ���������������ӣ�add by Minim
    if (LoadFlag=="9") {
      fm.action="../app/ProposalSave.jsp?BQFlag=4&MasterPolNo=" + parent.VD.gVSwitch.getVar('MasterPolNo');
      // alert(fm.action);return;
    }
    if(LoadFlag=="2")
    {
        try{
            fm.InsuredPeoples.value=Peoples2;
            fm.InsuredAppAge.value = InsuredAge;
        }
        catch(ex){}
    }

    sign = 1;
  // beforeSubmit();
    fm.submit(); // �ύ
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
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

}

/*******************************************************************************
 * �������Ͷ�������ύ��Ĳ���,���������ݷ��غ�ִ�еĲ��� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function afterSubmit( FlagStr, content )
{
    try {
        if(showInfo!=null)
            showInfo.close();
        }
    catch(e)
  {

    }
    if (FlagStr == "Fail")
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=F&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

    }
    else
    {
        // add by yaory
// fm.riskbutton31.disabled='';
// fm.riskbutton32.disabled='';
        // alert("loadflag:"+LoadFlag);
    if (fm.ContType.value == "1" && cflag=="1")
    {
          if (confirm("����ɹ���\nҪ�����ӡˢ�ŵ��������ø�����Ա�ܲ�����")) {
            unLockTable();
          }
      }
      else {
          try{mainRiskPolNo=parent.VD.gVSwitch.getVar("mainRiskPolNo");} catch(ex){}
          if(LoadFlag == '3'){
            inputQuestButton.disabled = false;
                    // ////add by yaory
// fm.riskbutton31.disabled=true;
// fm.riskbutton32.disabled=true;
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

      // document.all('AppendRiskCode').focus();
        // ////add by yaory
// fm.riskbutton31.focus();
// fm.riskbutton32.focus();
      if (mAction=="CONFIRM") {
        window.location.href("./ContPolInput.jsp");
          }
    }
     if(LoadFlag == 1)
      {
        top.close();
        }
        if(LoadFlag == 5){
        top.close();
        }
        // ��ʱ��������Ͷ�������룬���㸽�յ�¼�룬����ѡ��ɨ�����ʧЧ
        // try { if (top.mainPolNo == "") top.mainPolNo =
		// document.all("ProposalNo").value } catch(e) {}
        // try { if (mainRiskPolNo == "") mainRiskPolNo =
		// document.all("ProposalNo").value } catch(e) {alert("err");}
    }

    // �б��ƻ������������ֹ�ĺ�������
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

function afterSubmita( FlagStr, content )
{
    try {
        if(showInfo!=null)
            showInfo.close();
        }
    catch(e)
  {

    }
    
    // alert("FlagStr:"+FlagStr);
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
    	
    	try{
			// alert(1111);
			var cRiskCode = fm.RiskCode.value;
	 if(ifTLrisk(cRiskCode))
	  {  
	  	// alert('��Ͷ������');
	  	try{
		      // DivChooseDuty.style.display = "none";
			    // DutyGrid.checkBoxAll();
			    // tongmeng 2010-11-16 Modify
			    // ֻ�б�����Ĳ�Ʒ�ų�ʼ��Ͷ�ʽ���
			    // alert("11111"+tProposalContNo);
			    if(ifSavedRiskCode(cRiskCode))
			    {
			    	divInvestPlanRate.style.display="";
			     showDetailForCont2(cRiskCode);	
			     
			    	InvestPlanInputInit(1);	
			  	}
			  	else
			  	{
			  		divInvestPlanRate.style.display="none";
			  	}
			    
			   }
			   catch(e)
			   {
			   	}
					
					// alert("3111");
								
	  }
		}
		catch(ex){}
		
        // add by yaory
// fm.riskbutton31.disabled='';
// fm.riskbutton32.disabled='';
    if (fm.ContType.value == "1" && cflag=="1")
    {
          if (confirm("����ɹ���\nҪ�����ӡˢ�ŵ��������ø�����Ա�ܲ�����")) {
            unLockTable();
          }
      }
      else {
          try{mainRiskPolNo=parent.VD.gVSwitch.getVar("mainRiskPolNo");} catch(ex){}
          if(LoadFlag == '3'){
            inputQuestButton.disabled = false;
                    // ////add by yaory
// fm.riskbutton31.disabled=true;
// fm.riskbutton32.disabled=true;
          }
        // content = "����ɹ���";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

      // document.all('AppendRiskCode').focus();
        // ////add by yaory
// fm.riskbutton31.focus();
// fm.riskbutton32.focus();
      if (mAction=="CONFIRM") {
        window.location.href("./ContPolInput.jsp");
          }
    }

        // ��ʱ��������Ͷ�������룬���㸽�յ�¼�룬����ѡ��ɨ�����ʧЧ
        // try { if (top.mainPolNo == "") top.mainPolNo =
		// document.all("ProposalNo").value } catch(e) {}
        // try { if (mainRiskPolNo == "") mainRiskPolNo =
		// document.all("ProposalNo").value } catch(e) {alert("err");}
    }

    // �б��ƻ������������ֹ�ĺ�������
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

/*******************************************************************************
 * "����"��ť��Ӧ���� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function resetForm()
{
    try {
        // initForm();
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

/*******************************************************************************
 * "ȡ��"��ť��Ӧ���� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function cancelForm()
{
    // showDiv(operateButton,"true");
    // showDiv(inputButton,"false");
}

/*******************************************************************************
 * ��ʾfrmSubmit��ܣ��������� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function showSubmitFrame(cDebug)
{
    // if( cDebug == "1" )
        // parent.fraMain.rows = "0,0,50,82,*";
    // else
        // parent.fraMain.rows = "0,0,80,72,*";
}

/*******************************************************************************
 * Click�¼������������ͼƬʱ�����ú��� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function addClick()
{
    // ����������Ӧ�Ĵ���
    // showDiv( operateButton, "false" );
    // showDiv( inputButton, "true" );
}

/*******************************************************************************
 * Click�¼������������ѯ��ͼƬʱ�����ú��� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function queryClick() {
    if( mOperate == 0 ) {
        mOperate = 1;

        cGrpPolNo = document.all( 'GrpPolNo' ).value;
        cContNo = document.all( 'ContNo' ).value;
        window.open("./ProposalQueryMain.jsp?GrpPolNo=" + cGrpPolNo + "&ContNo=" + cContNo,"",sFeatures);
    }
}

/*******************************************************************************
 * Click�¼�����������޸ġ�ͼƬʱ�����ú��� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function updateClick()
{
   var tCurrencyCode = "";
	 try{
	 tCurrencyCode = fm.CurrencyCode.value
	}
	catch(E)
	{
		 tCurrencyCode = "01";
		}
	   if(tCurrencyCode=="" || tCurrencyCode==null){
			alert("������Ϣ����Ϊ��");
			return false;
		}
		
    if (fm.RiskCode.value == "212403")
    {
        if (verifyInput2() == false)
            return;
    }

    // У������ۿۣ����ܴ���1С��0
    if (verifyFloatRate() == false)
        return;

    var index = BnfGrid.mulLineCount;
    var i;
    for(i=0;i<index;i++)
    {

        var idType=BnfGrid.getRowColData(i,4);
        if(idType=="0")
        {
            var idNum=BnfGrid.getRowColData(i,5);
            if(idNum==null||(!(idNum.length==15||idNum.length==18)))
            {
                alert("����������Ϣ ��"+(i+1)+"�е����֤������������!����������!");
                BnfGrid.setRowColData(i,5,"");
                BnfGrid.setFocus(i,5);
                return ;
            }
        }





        if (BnfGrid.getRowColData(i, 10) == "A")
        {
          if(ContType!="2")
          {
					// alert("9999999");
                  BnfGrid.setRowColData(i, 2, document.all("AppntName").value);
                  BnfGrid.setRowColData(i, 3, document.all("AppntSex").value);
                  BnfGrid.setRowColData(i, 4, document.all("AppntIDType").value);
                  BnfGrid.setRowColData(i, 5, document.all("AppntIDNo").value);
                  // BnfGrid.setRowColData(i, 6,parent.VD.gVSwitch.getVar(
					// "RelationToAppnt"));
                  BnfGrid.setRowColData(i, 6,parent.VD.gVSwitch.getVar( "RelationToInsured"));
                  BnfGrid.setRowColData(i, 9, document.all("AppntHomeAddress").value);
                  // hl
                  // BnfGrid.setRowColData(i, 9, document.all("AppntNo").value);

          }
          else
          {
                  alert("Ͷ����Ϊ���壬������Ϊ�����ˣ�")

                  BnfGrid.setRowColData(i, 2, "");
                  BnfGrid.setRowColData(i, 3, "");
                  BnfGrid.setRowColData(i, 4, "");
                  BnfGrid.setRowColData(i, 5, "");
                  BnfGrid.setRowColData(i, 6, "");
                  BnfGrid.setRowColData(i, 9, "");
          }
        }
       else if (BnfGrid.getRowColData(i,10)== "I")
        {
					// alert("0000000");
                  BnfGrid.setRowColData(i, 2, document.all("Name").value);
                  BnfGrid.setRowColData(i, 3, document.all("Sex").value);
                  BnfGrid.setRowColData(i, 4, document.all("IDType").value);
                  BnfGrid.setRowColData(i, 5, document.all("IDNo").value);
                  BnfGrid.setRowColData(i, 6, "00");
              BnfGrid.setRowColData(i, 9, document.all("HomeAddress").value);

                  // BnfGrid.setRowColData(i, 9, document.all("InsuredNo").value);

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

      // -------------------------------------------------Beg
      // �޸��ˣ�chenrong
      // �޸����ڣ�2006-03-13
      // ����ȡ����ֵ����ַ���
      if (setDutyKind() == "")
      {
          alert("��ȡ����ֵ����Ϊ�գ�������!");
          return;
      }
      // -------------------------------------------------End

        if( verifyProposal() == false ) return;
    if(!chkMult() || !checkMult())      // �жϷ����Ƿ�Ϊ�ջ�����
										// chkMult�����ڶ���������,checkMult�����ڵ���������,
                                                                            // create
																			// by
																			// malong
																			// 2005-7-8
      {
         return;
      }
	
      try 
  	  {
      	if(specDealByRisk()==false)
      	{  	  
    	  	return;
      	}

   	  }catch(e){}


         // У�鱻�����Ƿ�ͬͶ���ˣ���ͬ����и���
    try { verifySamePerson(); } catch(e) {}
    
    if(DiscountGrid.checkValue("DiscountGrid") == false) 
    {
       return;
    }

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

            if (LoadFlag=="1"||LoadFlag=="3") {
                mAction = "UPDATEPERSON";
            }
            else if(LoadFlag=="25")
            {
            	mAction = "ChangePlanUPDATEGROUP";
            }
            else {
                mAction = "UPDATEGROUP";
            }

            document.all( 'fmAction' ).value = mAction;
            // �б��ƻ����(����Ͷ����״̬���䣺����״̬���˱�״̬)
            if (typeof(window.ChangePlanSub) == "object") document.all('fmAction').value = "ChangePlan" + document.all('fmAction').value;
            // �޸ĸ�������(����Ͷ����״̬���䣺����״̬���˱�״̬,���ñȳб��ƻ������һ����޸ĸ������ʣ�ΪȨ�޿���)
            if(LoadFlag=="10") document.all('fmAction').value = "ChangePlan" + document.all('fmAction').value;
            // yaoryif(LoadFlag=="3") document.all('fmAction').value = "Modify" +
			// document.all('fmAction').value;
            // inputQuestButton.disabled = false;
            try{
                if(document.all('inpNeedDutyGrid').value==0)
                {
                    var tDutyCode = getDutyCode(fm.RiskCode.value);
                    if (tDutyCode != null && tDutyCode != "null" && tDutyCode != "")
                    {
                        fm.action="../app/ProposalSave.jsp?DutyCode=" + tDutyCode;
                    }
                }
            }
            catch(ex){}
            fm.submit(); // �ύ
        }

        try {
          if (typeof(top.opener.modifyClick) == "object") top.opener.initQuery();
        }
        catch(e) {
        }
    }
}

/*******************************************************************************
 * Click�¼����������ɾ����ͼƬʱ�����ú��� ���� �� �� ����ֵ�� ��
 * ********************************************************************
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
            fm.submit(); // �ύ
        }
    }
}

/*******************************************************************************
 * Click�¼����������ѡ�����Ρ�ͼƬʱ�����ú��� ���� �� �� ����ֵ�� ��
 * ********************************************************************
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

    showInfo = window.open("./ChooseDutyInput.jsp?RiskCode="+cRiskCode+"&RiskVersion="+cRiskVersion,"",sFeatures);
    return true
}

/*******************************************************************************
 * Click�¼������������ѯ������Ϣ��ͼƬʱ�����ú��� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function showDuty()
{
    // ����������Ӧ�Ĵ���
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
	var strUrl1="./ProposalDuty.jsp?PolNo="+cPolNo
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (strUrl1,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    showInfo.close();
}

/*******************************************************************************
 * Click�¼���������������ݽ�����Ϣ��ͼƬʱ�����ú��� ���� �� �� ����ֵ�� ��
 * ********************************************************************
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

    showInfo = window.open( "./ProposalFee.jsp?PolNo=" + cPolNo + "&polType=PROPOSAL&prtNo=" + prtNo,"",sFeatures );
}

/*******************************************************************************
 * Click�¼�����˫����Ͷ���˿ͻ��š�¼���ʱ�����ú��� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function showAppnt()
{
    if( mOperate == 0 )
    {
        mOperate = 2;
        showInfo = window.open( "../sys/LDPersonMain.html" ,"",sFeatures);
    }
}

/*******************************************************************************
 * Click�¼�����˫���������˿ͻ��š�¼���ʱ�����ú��� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function showInsured()
{
    if( mOperate == 0 )
    {
        mOperate = 3;
        showInfo = window.open( "../sys/LDPersonMain.html" ,"",sFeatures);
    }
}

/*******************************************************************************
 * Click�¼�����˫�������������˿ͻ��š�¼���ʱ�����ú��� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function showSubInsured( span, arrPara )
{
    if( mOperate == 0 )
    {
        mOperate = 4;
        spanObj = span;
        showInfo = window.open( "../sys/LDPersonMain.html" ,"",sFeatures);
    }
}

/*******************************************************************************
 * �������е�������ʾ��Ͷ���˲��� ���� �� ���˿ͻ�����Ϣ ����ֵ�� ��
 * ********************************************************************
 */
function displayPol(cArr)
{
    try
    {
    // alert(2328);
    /*
	 * try { document.all('PrtNo').value = cArr[6]; } catch(ex) { }; try {
	 * document.all('ManageCom').value = cArr[12]; } catch(ex) { }; try {
	 * document.all('SaleChnl').value = cArr[15]; } catch(ex) { }; try {
	 * document.all('AgentCom').value = cArr[13]; } catch(ex) { }; try {
	 * document.all('AgentType').value = cArr[14]; } catch(ex) { }; try {
	 * document.all('AgentCode').value = cArr[87]; } catch(ex) { }; try {
	 * document.all('AgentGroup').value = cArr[88]; } catch(ex) { }; //try {
	 * document.all('Handler').value = cArr[82]; } catch(ex) { }; //try {
	 * document.all('AgentCode1').value = cArr[89]; } catch(ex) { }; try {
	 * document.all('Remark').value = cArr[90]; } catch(ex) { };
	 * 
	 * try { document.all('ContNo').value = cArr[1]; } catch(ex) { };
	 * 
	 * //try { document.all('Amnt').value = cArr[43]; } catch(ex) { }; try {
	 * document.all('CValiDate').value = cArr[29]; } catch(ex) { }; try {
	 * document.all('PolApplyDate').value = cArr[128]; } catch(ex) { }; try {
	 * document.all('HealthCheckFlag').value = cArr[72]; } catch(ex) { }; try {
	 * document.all('OutPayFlag').value = cArr[97]; } catch(ex) { }; try {
	 * document.all('PayLocation').value = cArr[59]; } catch(ex) { }; try {
	 * document.all('BankCode').value = cArr[102]; } catch(ex) { }; try {
	 * document.all('BankAccNo').value = cArr[103]; } catch(ex) { }; try {
	 * document.all('AccName').value = cArr[118]; } catch(ex) { }; try {
	 * document.all('LiveGetMode').value = cArr[98]; } catch(ex) { }; try {
	 * document.all('BonusGetMode').value = cArr[100]; } catch(ex) { }; try {
	 * document.all('AutoPayFlag').value = cArr[65]; } catch(ex) { }; try {
	 * document.all('InterestDifFlag').value = cArr[66]; } catch(ex) { };
	 * 
	 * try { document.all('InsuYear').value = cArr[111]; } catch(ex) { }; try {
	 * document.all('InsuYearFlag').value = cArr[110]; } catch(ex) { }; try {
	 * document.all('PolTypeFlag').value = cArr[69]; } catch(ex) { }; try {
	 * document.all('InsuredPeoples').value = cArr[24]; } catch(ex) { }; try {
	 * document.all('InsuredAppAge').value = cArr[22]; } catch(ex) { };
	 * 
	 * 
	 * try { document.all('StandbyFlag1').value = cArr[78]; } catch(ex) { }; try {
	 * document.all('StandbyFlag2').value = cArr[79]; } catch(ex) { }; try {
	 * document.all('StandbyFlag3').value = cArr[80]; } catch(ex) { };
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
        // try { document.all('OutPayFlag').value = cArr[97]; } catch(ex) { };
        try { document.all('PayLocation').value = cArr[51]; } catch(ex) { };
        // try { document.all('BankCode').value = cArr[102]; } catch(ex) { };
        // try { document.all('BankAccNo').value = cArr[103]; } catch(ex) { };
        // try { document.all('AccName').value = cArr[118]; } catch(ex) { };
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
      alert("displayPol err:" + ex + "\ndata is:" + cArr);
    }
}

/*******************************************************************************
 * �ѱ����е�Ͷ������Ϣ��ʾ��Ͷ���˲��� ���� �� ���˿ͻ�����Ϣ ����ֵ�� ��
 * ********************************************************************
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

/*******************************************************************************
 * �ѱ����е�Ͷ����������ʾ��Ͷ���˲��� ���� �� ����ͻ�����Ϣ ����ֵ�� ��
 * ********************************************************************
 */
function displayPolAppntGrp( cArr )
{

    /*
	 * // ��LCAppntGrp��ȡ���� try { document.all('AppntPolNo').value = cArr[0]; }
	 * catch(ex) { }; try { document.all('AppntGrpNo').value = cArr[1]; } catch(ex) { };
	 * try { document.all('AppntRelationToInsured').value = cArr[2]; } catch(ex) { };
	 * try { document.all('AppntAppntGrade').value = cArr[3]; } catch(ex) { }; try {
	 * document.all('AppntPassword').value = cArr[4]; } catch(ex) { }; try {
	 * document.all('AppntGrpName').value = cArr[5]; } catch(ex) { }; try {
	 * document.all('AppntGrpAddressCode').value = cArr[6]; } catch(ex) { }; try {
	 * document.all('AppntGrpAddress').value = cArr[7]; } catch(ex) { }; try {
	 * document.all('AppntGrpZipCode').value = cArr[8]; } catch(ex) { }; try {
	 * document.all('AppntBusinessType').value = cArr[9]; } catch(ex) { }; try {
	 * document.all('AppntGrpNature').value = cArr[10]; } catch(ex) { }; try {
	 * document.all('AppntPeoples').value = cArr[11]; } catch(ex) { }; try {
	 * document.all('AppntRgtMoney').value = cArr[12]; } catch(ex) { }; try {
	 * document.all('AppntAsset').value = cArr[13]; } catch(ex) { }; try {
	 * document.all('AppntNetProfitRate').value = cArr[14]; } catch(ex) { }; try {
	 * document.all('AppntMainBussiness').value = cArr[15]; } catch(ex) { }; try {
	 * document.all('AppntCorporation').value = cArr[16]; } catch(ex) { }; try {
	 * document.all('AppntComAera').value = cArr[17]; } catch(ex) { }; try {
	 * document.all('AppntLinkMan1').value = cArr[18]; } catch(ex) { }; try {
	 * document.all('AppntDepartment1').value = cArr[19]; } catch(ex) { }; try {
	 * document.all('AppntHeadShip1').value = cArr[20]; } catch(ex) { }; try {
	 * document.all('AppntPhone1').value = cArr[21]; } catch(ex) { }; try {
	 * document.all('AppntE_Mail1').value = cArr[22]; } catch(ex) { }; try {
	 * document.all('AppntFax1').value = cArr[23]; } catch(ex) { }; try {
	 * document.all('AppntLinkMan2').value = cArr[24]; } catch(ex) { }; try {
	 * document.all('AppntDepartment2').value = cArr[25]; } catch(ex) { }; try {
	 * document.all('AppntHeadShip2').value = cArr[26]; } catch(ex) { }; try {
	 * document.all('AppntPhone2').value = cArr[27]; } catch(ex) { }; try {
	 * document.all('AppntE_Mail2').value = cArr[28]; } catch(ex) { }; try {
	 * document.all('AppntFax2').value = cArr[29]; } catch(ex) { }; try {
	 * document.all('AppntFax').value = cArr[30]; } catch(ex) { }; try {
	 * document.all('AppntPhone').value = cArr[31]; } catch(ex) { }; try {
	 * document.all('AppntGetFlag').value = cArr[32]; } catch(ex) { }; try {
	 * document.all('AppntSatrap').value = cArr[33]; } catch(ex) { }; try {
	 * document.all('AppntEMail').value = cArr[34]; } catch(ex) { }; try {
	 * document.all('AppntFoundDate').value = cArr[35]; } catch(ex) { }; try {
	 * document.all('AppntBankAccNo').value = cArr[36]; } catch(ex) { }; try {
	 * document.all('AppntBankCode').value = cArr[37]; } catch(ex) { }; try {
	 * document.all('AppntGrpGroupNo').value = cArr[38]; } catch(ex) { }; try {
	 * document.all('AppntState').value = cArr[39]; } catch(ex) { }; try {
	 * document.all('AppntRemark').value = cArr[40]; } catch(ex) { }; try {
	 * document.all('AppntBlacklistFlag').value = cArr[41]; } catch(ex) { }; try {
	 * document.all('AppntOperator').value = cArr[42]; } catch(ex) { }; try {
	 * document.all('AppntMakeDate').value = cArr[43]; } catch(ex) { }; try {
	 * document.all('AppntMakeTime').value = cArr[44]; } catch(ex) { }; try {
	 * document.all('AppntModifyDate').value = cArr[45]; } catch(ex) { }; try {
	 * document.all('AppntModifyTime').value = cArr[46]; } catch(ex) { }; try {
	 * document.all('AppntFIELDNUM').value = cArr[47]; } catch(ex) { }; try {
	 * document.all('AppntPK').value = cArr[48]; } catch(ex) { }; try {
	 * document.all('AppntfDate').value = cArr[49]; } catch(ex) { }; try {
	 * document.all('AppntmErrors').value = cArr[50]; } catch(ex) { };
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

/*******************************************************************************
 * �ѱ����еı�����������ʾ�������˲��� ���� �� �ͻ�����Ϣ ����ֵ�� ��
 * ********************************************************************
 */
function displayPolInsured(cArr)
{
    // ��LCInsured��ȡ����
    try { document.all('ContNo').value=cArr[1];} catch(ex){};
    try { document.all('CustomerNo').value = cArr[2]; } catch(ex) { };
    try { document.all('SequenceNo').value = cArr[11]; } catch(ex) { };
    // try { document.all('InsuredGrade').value = cArr[3]; } catch(ex) { };
    try { document.all('RelationToInsured').value = cArr[8]; } catch(ex) { };
    // try { document.all('Password').value = cArr[5]; } catch(ex) { };
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
    // try { document.all('Proterty').value = cArr[21]; } catch(ex) { };
    try { document.all('IDNo').value = cArr[16]; } catch(ex) { };
    // try { document.all('OthIDType').value = cArr[23]; } catch(ex) { };
    // try { document.all('OthIDNo').value = cArr[24]; } catch(ex) { };
    // try { document.all('ICNo').value = cArr[25]; } catch(ex) { };
    // try { document.all('HomeAddressCode').value = cArr[26]; } catch(ex) { };
    // try { document.all('HomeAddress').value = cArr[27]; } catch(ex) { };
    // try { document.all('PostalAddress').value = cArr[28]; } catch(ex) { };
    // try { document.all('ZipCode').value = cArr[29]; } catch(ex) { };
    // try { document.all('Phone').value = cArr[30]; } catch(ex) { };
    // try { document.all('BP').value = cArr[31]; } catch(ex) { };
    // try { document.all('Mobile').value = cArr[32]; } catch(ex) { };
    // try { document.all('EMail').value = cArr[33]; } catch(ex) { };
    // try { document.all('JoinCompanyDate').value = cArr[34]; } catch(ex) { };
    // try { document.all('Position').value = cArr[35]; } catch(ex) { };
    // try { document.all('GrpNo').value = cArr[4]; } catch(ex) { };
    // try { document.all('GrpName').value = cArr[37]; } catch(ex) { };
    // try { document.all('GrpPhone').value = cArr[38]; } catch(ex) { };
    // try { document.all('GrpAddressCode').value = cArr[39]; } catch(ex) { };
    // try { document.all('GrpAddress').value = cArr[40]; } catch(ex) { };
    // try { document.all('DeathDate').value = cArr[41]; } catch(ex) { };
    // try { document.all('State').value = cArr[43]; } catch(ex) { };
    try { document.all('WorkType').value = cArr[36]; } catch(ex) { };
    try { document.all('PluralityType').value = cArr[37]; } catch(ex) { };
    try { document.all('OccupationCode').value = cArr[35]; } catch(ex) { };
    try { document.all('Degree').value = cArr[25]; } catch(ex) { };
    // try { document.all('GrpZipCode').value = cArr[50]; } catch(ex) { };
    try { document.all('SmokeFlag').value = cArr[38]; } catch(ex) { };
    try { document.all('RgtAddress').value = cArr[19]; } catch(ex) { };
    // try { document.all('HomeZipCode').value = cArr[53]; } catch(ex) { };
    // try { document.all('Phone2').value = cArr[54]; } catch(ex) { };
    return;

}

/*******************************************************************************
 * �Ѳ�ѯ���صĿͻ�������ʾ�����������˲��� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function displaySubInsured()
{
    document.all( spanObj ).all( 'SubInsuredGrid1' ).value = arrResult[0][0];
    document.all( spanObj ).all( 'SubInsuredGrid2' ).value = arrResult[0][2];
    document.all( spanObj ).all( 'SubInsuredGrid3' ).value = arrResult[0][3];
    document.all( spanObj ).all( 'SubInsuredGrid4' ).value = arrResult[0][4];
}

/*******************************************************************************
 * ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ ���� �� ��ѯ���صĶ�ά���� ����ֵ�� ��
 * ********************************************************************
 */
function afterQuery( arrQueryResult ) {
    if( arrQueryResult != null ) {
        arrResult = arrQueryResult;

        if( mOperate == 1 ) {           // ��ѯ������ϸ
            var tPolNo = arrResult[0][0];

            // ��ѯ������ϸ
            queryPolDetail( tPolNo );
      // �жϼ��±��еļ�¼����

        }

        if( mOperate == 2 ) {       // Ͷ������Ϣ
        
		var sqlid22="ProposalInputSql22";
		var mySql22=new SqlClass();
		mySql22.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql22.setSqlId(sqlid22);// ָ��ʹ�õ�Sql��id
		mySql22.addSubPara(arrQueryResult[0][0] );// ָ������Ĳ���
	    var Sql22=mySql22.getString();
		
		 arrResult = easyExecSql(Sql22, 1, 0);
           // arrResult = easyExecSql("select * from LDPerson where CustomerNo
			// = '" + arrQueryResult[0][0] + "'", 1, 0);
            if (arrResult == null) {
              alert("δ�鵽Ͷ������Ϣ");
            } else {
               displayAppnt(arrResult[0]);
            }

      }
        if( mOperate == 3 ) {       // ����������Ϣ
        
		var sqlid23="ProposalInputSql23";
		var mySql23=new SqlClass();
		mySql23.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql23.setSqlId(sqlid23);// ָ��ʹ�õ�Sql��id
		mySql23.addSubPara(arrQueryResult[0][0]);// ָ������Ĳ���
	    var Sql23=mySql23.getString();
		
		    arrResult = easyExecSql(Sql23, 1, 0);
            // arrResult = easyExecSql("select a.*,(select GrpName from LDGrp
			// where CustomerNo=(select GrpNo from LDPerson where
			// CustomerNo=a.CustomerNo)
			// ),b.PostalAddress,b.ZipCode,b.Phone,b.HomePhone from LDPerson a
			// Left Join LCAddress b On b.CustomerNo =a.CustomerNo Where 1=1 and
			// a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
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

/*******************************************************************************
 * ���ݲ�ѯ���ص���Ϣ��ѯͶ������ϸ��Ϣ ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function queryPolDetail( cPolNo )
{
    emptyForm();
    // var showStr = "���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    // var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr
	// ;

    // showInfo =
	// window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    // parent.fraSubmit.window.location = "./ProposalQueryDetail.jsp?PolNo=" +
	// cPolNo;
    parent.fraTitle.window.location = "./ProposalQueryDetail.jsp?PolNo=" + cPolNo;
}


/*******************************************************************************
 * ���ݲ�ѯ���ص���Ϣ��ѯ���ֵı��ռƻ���ϸ��Ϣ ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function queryRiskPlan( tProposalGrpContNo,tRiskCode,tContPlanCode,tMainRiskCode)
{

    emptyForm();
    // var showStr = "���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    // var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr
	// ;
    // alert("./ProposalQueryRiskPlan.jsp?ProposalGrpContNo="
    // +
	// tProposalGrpContNo+"&RiskCode="+tRiskCode+"&ContPlanCode="+tContPlanCode);
    // showInfo =
	// window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    // parent.fraSubmit.window.location = "./ProposalQueryDetail.jsp?PolNo=" +
	// cPolNo;

    parent.fraSubmit.window.location = "./ProposalQueryRiskPlan.jsp?ProposalGrpContNo="
                                                                            + tProposalGrpContNo+"&RiskCode="+tRiskCode+"&ContPlanCode="+tContPlanCode+"&MainRiskCode="+tMainRiskCode;
}

/*******************************************************************************
 * ���ݲ�ѯ���ص���Ϣ��ѯ���ֵı��ռƻ���ϸ��Ϣ ��ȫ ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function BqQueryRiskPlan( tProposalGrpContNo,tRiskCode,tContPlanCode,tMainRiskCode,EdorValiDate,CValiDate )
{
    emptyForm();
    parent.fraSubmit.window.location = "./ProposalQueryRiskPlan.jsp?ProposalGrpContNo="
                                                                            + tProposalGrpContNo+"&RiskCode="+tRiskCode+"&ContPlanCode="+tContPlanCode+"&MainRiskCode="+tMainRiskCode+"&EdorValiDate="+EdorValiDate+"&CValiDate="+CValiDate;
}
/*******************************************************************************
 * ��ʾdiv ���� �� ��һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ ����ֵ�� ��
 * ********************************************************************
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

// *************************************************************
// �����˿ͻ��Ų�ѯ��Ť�¼�
function queryInsuredNo() {
  if (document.all("CustomerNo").value == "") {
    showInsured1();
  // } else if (LoadFlag != "1" && LoadFlag != "2") {
  // alert("ֻ����Ͷ����¼��ʱ���в�����");
  }  else {
  	
			var sqlid24="ProposalInputSql24";
		var mySql24=new SqlClass();
		mySql24.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql24.setSqlId(sqlid24);// ָ��ʹ�õ�Sql��id
		mySql24.addSubPara( document.all("CustomerNo").value );// ָ������Ĳ���
	    var Sql24=mySql24.getString();
		
	 arrResult=easyExecSql(Sql24, 1, 0);
      // arrResult=easyExecSql("select a.*,(select GrpName from LDGrp where
		// CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo)
		// ),b.PostalAddress,b.ZipCode,b.Phone,b.HomePhone from LDPerson a Left
		// Join LCAddress b On b.CustomerNo =a.CustomerNo Where 1=1 and
		// a.CustomerNo = '" + document.all("CustomerNo").value + "'", 1, 0);
    if (arrResult == null) {
      alert("δ�鵽����������Ϣ");
      displayInsured(new Array());
      emptyUndefined();
    } else {

      displayInsured(arrResult[0]);
    }
  }
}

// *************************************************************
// Ͷ���˿ͻ��Ų�ѯ��Ť�¼�
function queryAppntNo() {
  if (document.all("AppntCustomerNo").value == "" && LoadFlag == "1") {
    showAppnt1();
  // } else if (LoadFlag != "1" && LoadFlag != "2") {
  // alert("ֻ����Ͷ����¼��ʱ���в�����");
  } else {
  	
		var sqlid25="ProposalInputSql25";
		var mySql25=new SqlClass();
		mySql25.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql25.setSqlId(sqlid25);// ָ��ʹ�õ�Sql��id
		mySql25.addSubPara( document.all("AppntCustomerNo").value );// ָ������Ĳ���
	    var Sql25=mySql25.getString();
	
	arrResult = easyExecSql(Sql25, 1, 0);
    // arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where
	// CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo)
	// ),b.PostalAddress,b.ZipCode,b.Phone,b.HomePhone from LDPerson a Left Join
	// LCAddress b On b.CustomerNo =a.CustomerNo Where 1=1 and a.CustomerNo = '"
	// + document.all("AppntCustomerNo").value + "'", 1, 0);
    if (arrResult == null) {
      alert("δ�鵽Ͷ������Ϣ");
      displayAppnt(new Array());
      emptyUndefined();
    } else {
      displayAppnt(arrResult[0]);
    }
  }
}

// *************************************************************
// Ͷ�����뱻������ͬѡ����¼�
function isSamePerson() {
  // ��Ӧδѡͬһ�ˣ��ִ򹳵����
  if (fm.AppntRelationToInsured.value!="00" && fm.SamePersonFlag.checked==true) {
    document.all('DivLCInsured').style.display = "";
    fm.SamePersonFlag.checked = false;
    alert("Ͷ�����뱻���˹�ϵ���Ǳ��ˣ����ܽ��иò�����");
  }
  // ��Ӧ��ͬһ�ˣ��ִ򹳵����
  else if (fm.SamePersonFlag.checked == true) {
    document.all('DivLCInsured').style.display = "none";
  }
  // ��Ӧ��ѡͬһ�˵����
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

// *************************************************************
// ����ʱУ��Ͷ�����뱻������ͬѡ����¼�
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


/*******************************************************************************
 * �������е�������ʾ��Ͷ���˲��� ���� �� ���˿ͻ�����Ϣ ����ֵ�� ��
 * ********************************************************************
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

/*******************************************************************************
 * �������е�������ʾ��Ͷ���˲��� ���� �� ����ͻ�����Ϣ ����ֵ�� ��
 * ********************************************************************
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
// ������ldgrp��
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

/*******************************************************************************
 * �Ѳ�ѯ���صĿͻ�������ʾ�������˲��� ���� �� �ͻ�����Ϣ ����ֵ�� ��
 * ********************************************************************
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
}

// *********************************************************************
function showAppnt1()
{
    if( mOperate == 0 )
    {
        mOperate = 2;
        showInfo = window.open( "../sys/LDPersonQuery.html" ,"",sFeatures);
    }
}

// *********************************************************************
function showInsured1()
{
    if( mOperate == 0 )
    {
        mOperate = 3;
        showInfo = window.open( "../sys/LDPersonQuery.html" ,"",sFeatures);
    }
}

function isSamePersonQuery() {
  fm.SamePersonFlag.checked = true;
  // divSamePerson.style.display = "none";
  DivLCInsured.style.display = "none";
}

// �����¼��
function QuestInput()
{
   
    cContNo = document.all("ContNo").value;  // ��������
        if(LoadFlag=="2"||LoadFlag=="4"){
            if(mSwitch.getVar( "ProposalGrpContNo" )==""){
              alert("���޼����ͬͶ�����ţ����ȱ���!");
                }
                else{
            window.open("./GrpQuestInputMain.jsp?GrpContNo="+mSwitch.getVar( "ProposalGrpContNo" )+"&Flag="+LoadFlag,"",sFeatures);
                    }
            }
            else{
                    if(cContNo == ""){
                   alert("���޺�ͬͶ�����ţ����ȱ���!");
                     }
                     else
                    {
                window.open("../uw/MSQuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+ActivityID,"window1",sFeatures);
                    }
            }
}
function QuestQuery()
{

   cContNo = document.all("ContNo").value;  // ��������

   if(LoadFlag=="2"||LoadFlag=="4"||LoadFlag=="13"){
    if(mSwitch.getVar( "ProposalGrpContNo" )==""||mSwitch.getVar( "ProposalGrpContNo" )==null)
    {
        alert("����ѡ��һ����������Ͷ����!");
        return ;
        }
        else{
            window.open("./GrpQuestQueryMain.jsp?GrpContNo="+mSwitch.getVar( "ProposalGrpContNo" )+"&Flag="+LoadFlag,"",sFeatures);
        }
   }
   else{
        if(cContNo == ""){
           alert("���޺�ͬͶ�����ţ����ȱ���!");
     }
    else{
               window.open("../uw/QuestQueryMain.jsp?ContNo="+cContNo+"&Flag="+LoadFlag,"window1",sFeatures);
        }
   }
}
// ��ʾͶ��������
function showAppntAge() {
  var age = calAge(document.all("AppntBirthday").value);
  var today = new Date();

  document.all("AppntBirthday").title = "Ͷ���˵����� " + today.toLocaleString()
                                + " \n������Ϊ��" + age + " ��!";
}

// ��ʾ����������
function showAge() {
  var age = calAge(document.all("Birthday").value);
  var today = new Date();

  document.all("Birthday").title = "�����˵����� " + today.toLocaleString()
                           + " \n������Ϊ��" + age + " ��!";
}

// ����Ͷ���˳������ڣ�����գ������֤���У�������֤ȡ��ȡ�������ؿո�;
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

// У��¼��������Ƿ���ҪУ�飬�����Ҫ����true,���򷵻�false
function needVerifyRiskcode()
{

  try {
       var riskcode=document.all("RiskCode").value;


		var sqlid26="ProposalInputSql26";
		var mySql26=new SqlClass();
		mySql26.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql26.setSqlId(sqlid26);// ָ��ʹ�õ�Sql��id
		mySql26.addSubPara("1");// ָ������Ĳ���
	    var tSql=mySql26.getString();

       // var tSql = "select Sysvarvalue from LDSysVar where
		// Sysvar='NotVerifyRiskcode'";
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


/*******************************************************************************
 * ��ʼ������ĸ�����Ϣ-121301���������ֵĳ�ʼ����һ�� ���� �� Ͷ������ ����ֵ�� ��
 * ********************************************************************
 */
function initPrivateRiskInfo121301(cPolNo) {
    if(cPolNo=="") {
        alert("û�����ձ�����,���ܽ��и�����¼��!");
        mCurOperateFlag="";        // ��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.
        return false
    }

    var arrLCPol = new Array();
    var arrQueryResult = null;
    // ��������Ϣ����
	
		var sqlid27="ProposalInputSql27";
		var mySql27=new SqlClass();
		mySql27.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql27.setSqlId(sqlid27);// ָ��ʹ�õ�Sql��id
		mySql27.addSubPara(cPolNo);// ָ������Ĳ���
	    var sql=mySql27.getString();
	
// var sql = "select * from lcpol where polno='" + cPolNo + "' "
// + "and riskcode in "
// + "( select riskcode from LMRiskApp where SubRiskFlag = 'M' )";

    arrQueryResult = easyExecSql( sql , 1, 0);

    if (arrQueryResult == null) {
        mCurOperateFlag="";        // ��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.

        // top.mainPolNo = "";
        mainRiskPolNo = "";

        alert("��ȡ������Ϣʧ��,���ܽ��и�����¼��!");
        return false
    }

    arrLCPol = arrQueryResult[0];
    displayPol( arrLCPol );
    displayPolSpec( arrLCPol );// ��ʼ������Ҫ��ı�����Ϣ

    document.all("MainPolNo").value = cPolNo;
    var tAR;

    // ����Ͷ������Ϣ
      arrQueryResult = null;
	  
	  	var sqlid28="ProposalInputSql28";
		var mySql28=new SqlClass();
		mySql28.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql28.setSqlId(sqlid28);// ָ��ʹ�õ�Sql��id
		mySql28.addSubPara(cPolNo);// ָ������Ĳ���
		mySql28.addSubPara(arrLCPol[26]);// ָ������Ĳ���
	    var sql28=mySql28.getString();
	  
	  arrQueryResult = easyExecSql(sql28, 1, 0);
      // arrQueryResult = easyExecSql("select * from lcappntind where
		// polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[26]+"'", 1, 0);
      tAR = arrQueryResult[0];
      displayPolAppnt(tAR);
      try { document.all('AppntRelationToInsured').value = '00'; } catch(ex) { };
      try { document.all("SamePersonFlag").checked = true; } catch(ex) { };
      try {isSamePerson();} catch(ex) { };
      try { document.all("SamePersonFlag").disabled=true} catch(ex) { };


    // ��������Ϣ����
    // arrQueryResult = null;
    // arrQueryResult = easyExecSql("select * from lcappntind where
	// polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[26]+"'", 1, 0);
    // tAR = arrQueryResult[0];
    // displayPolInsuredSpec(tAR);


    return true;
}



/*******************************************************************************
 * �ѱ��������е�������ʾ�������������Ϣ��ʾ����-121301, ���� �� ��������Ϣ ����ֵ�� ��
 * ********************************************************************
 */
function displayPolSpec(cArr)
{
    try
    {
        try { document.all('PayEndYear').value = cArr[109]; } catch(ex) { };
        try { document.all('PayEndYearFlag').value = cArr[108]; } catch(ex) { };
        try { document.all('PayIntv').value = cArr[57]; } catch(ex) { };
        try { document.all('Amnt').value = cArr[39]; } catch(ex) { };     // ���յı��Ѽ����յı���

    } catch(ex) {
      alert("displayPolSpec err:" + ex + "\ndata is:" + cArr);
    }
}



/*******************************************************************************
 * ��ʼ������ĸ�����Ϣ-321601���������ֵĳ�ʼ����һ�� ���� �� Ͷ������ ����ֵ�� ��
 * ********************************************************************
 */
function initPrivateRiskInfo321601(cPolNo) {
    if(cPolNo=="") {
        alert("û�����ձ�����,���ܽ��и�����¼��!");
        mCurOperateFlag="";        // ��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.
        return false
    }

    var arrLCPol = new Array();
    var arrQueryResult = null;
    // ��������Ϣ����
	
		  	var sqlid29="ProposalInputSql29";
		var mySql29=new SqlClass();
		mySql29.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql29.setSqlId(sqlid29);// ָ��ʹ�õ�Sql��id
		mySql29.addSubPara(cPolNo);// ָ������Ĳ���
	    var sql=mySql29.getString();
	
// var sql = "select * from lcpol where polno='" + cPolNo + "' "
// + "and riskcode in "
// + "( select riskcode from LMRiskApp where SubRiskFlag = 'M' )";

    arrQueryResult = easyExecSql( sql , 1, 0);

    if (arrQueryResult == null) {
        mCurOperateFlag="";        // ��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.

        // top.mainPolNo = "";
        document.all('mainRiskPolNo').value = "";

        alert("��ȡ������Ϣʧ��,���ܽ��и�����¼��!");
        return false
    }

    arrLCPol = arrQueryResult[0];
    displayPol( arrLCPol );

    // ��ʼ������Ҫ��ı�����Ϣ--//���յı��Ѽ����յı���(ȡ���ձ��Ѻ�500000֮��Сֵ)
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

    // Ͷ������Ϣ
    if (arrLCPol[28]=="2") {     // ����Ͷ������Ϣ
      arrQueryResult = null;
	  
	  	var sqlid30="ProposalInputSql30";
		var mySql30=new SqlClass();
		mySql30.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql30.setSqlId(sqlid30);// ָ��ʹ�õ�Sql��id
		mySql30.addSubPara(cPolNo);// ָ������Ĳ���
		mySql30.addSubPara(arrLCPol[26]);// ָ������Ĳ���
	    var sql30=mySql30.getString();
	  
	  arrQueryResult = easyExecSql(sql30, 1, 0);
      // arrQueryResult = easyExecSql("select * from lcappntgrp where
		// polno='"+cPolNo+"'"+" and grpno='"+arrLCPol[26]+"'", 1, 0);
      tAR = arrQueryResult[0];
      displayPolAppntGrp(tAR);
    } else {                     // ����Ͷ������Ϣ
      arrQueryResult = null;
	  
	  	var sqlid31="ProposalInputSql31";
		var mySql31=new SqlClass();
		mySql31.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql31.setSqlId(sqlid31);// ָ��ʹ�õ�Sql��id
		mySql31.addSubPara(cPolNo);// ָ������Ĳ���
		mySql31.addSubPara(arrLCPol[26]);// ָ������Ĳ���
	    var sql31=mySql31.getString();
	  
	  arrQueryResult = easyExecSql(sql31, 1, 0);
      // arrQueryResult = easyExecSql("select * from lcappntind where
		// polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[26]+"'", 1, 0);
      tAR = arrQueryResult[0];
      displayPolAppnt(tAR);
    }

    // ��������Ϣ����
    if (arrLCPol[18] == arrLCPol[26]) {
      document.all("SamePersonFlag").checked = true;
        parent.fraInterface.isSamePersonQuery();
    parent.fraInterface.document.all("CustomerNo").value = arrLCPol[18];
    }
    // else {
        arrQueryResult = null;
		
		var sqlid32="ProposalInputSql32";
		var mySql32=new SqlClass();
		mySql32.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql32.setSqlId(sqlid32);// ָ��ʹ�õ�Sql��id
		mySql32.addSubPara(cPolNo);// ָ������Ĳ���
		mySql32.addSubPara(arrLCPol[18]);// ָ������Ĳ���
	    var sql32=mySql32.getString();
		
		 arrQueryResult = easyExecSql(sql32, 1, 0);
       // arrQueryResult = easyExecSql("select * from lcinsured where
		// polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[18]+"'", 1, 0);
        tAR = arrQueryResult[0];
        displayPolInsured(tAR);
    // }

    return true;
}


/*******************************************************************************
 * �������ִ������������е�Ͷ����������ʾ�������˲��� ���� �� �ͻ�����Ϣ ����ֵ�� ��
 * ********************************************************************
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
    // try { document.all('BankCode').value = cArr[34]; } catch(ex) { };
    // try { document.all('BankAccNo').value = cArr[35]; } catch(ex) { };
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




// ����ҳ���ʼ��ʱ���������ֵ����⴦��
function initDealForSpecRisk(cRiskCode)
{
  try{
    // �����211801
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

    // �������
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

    // ��ҵ����
    if(cRiskCode=='211701')
    {
		
		var sqlid33="ProposalInputSql33";
		var mySql33=new SqlClass();
		mySql33.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql33.setSqlId(sqlid33);// ָ��ʹ�õ�Sql��id
		mySql33.addSubPara(cRiskCode);// ָ������Ĳ���
	    var strSql=mySql33.getString();
		
// var strSql = "select * from lmdutypayrela where dutycode in "
// + " (select dutycode from lmriskduty where riskcode='"+cRiskCode+"')";
        turnPage.queryModal(strSql, PremGrid);
        PremGrid.lock;
    }

  }catch(ex) {}

}

/*******************************************************************************
 * ����ҳ���ʼ��ʱ���������ֵ����⴦����չ add by guoxiang at 2004-9-6 16:21 for update up function
 * initDealForSpecRisk not write function for every risk
 * ********************************************************************
 */
function initDealForSpecRiskEx(cRiskCode)
{
  try{
        var strSql="";
        // alert("document.all('inpNeedDutyGrid').value:"+document.all('inpNeedDutyGrid').value);
        if(document.all('inpNeedDutyGrid').value==1){
         initDutyGrid();  // �������ֳ�ʼ������¼���
         
		 var sqlid34="ProposalInputSql34";
		var mySql34=new SqlClass();
		mySql34.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql34.setSqlId(sqlid34);// ָ��ʹ�õ�Sql��id
		mySql34.addSubPara(cRiskCode);// ָ������Ĳ���
	    strSqll=mySql34.getString();
		 
// strSql = "select dutycode,dutyname,'','','','','','','','','','' from lmduty
// where dutycode in "
// + " (select dutycode from lmriskduty where riskcode='"+cRiskCode+"' and
// choflag!='B' and SpecFlag='N')";
         turnPage.queryModal(strSqll, DutyGrid);
         var cDutyCode="";
         var tSql="";
         for(var i=0;i<=DutyGrid.mulLineCount-1;i++){
           cDutyCode=DutyGrid.getRowColData(i,1);
		   
		var sqlid35="ProposalInputSql35";
		var mySql35=new SqlClass();
		mySql35.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql35.setSqlId(sqlid35);// ָ��ʹ�õ�Sql��id
		mySql35.addSubPara(cRiskCode);// ָ������Ĳ���
		mySql35.addSubPara(cDutyCode);// ָ������Ĳ���
	    tSql=mySql35.getString();
		   
        // tSql="select choflag from lmriskduty where riskcode='"+cRiskCode+"'
		// and dutycode='"+cDutyCode+"'";
           var arrResult=easyExecSql(tSql,1,0);
           if(arrResult[0]=="M"){
             DutyGrid.checkBoxSel(i+1);
           }
         }
         DutyGrid.lock;

        }
        
      // alert("document.all('inpNeedPremGrid').value:"+document.all('inpNeedPremGrid').value);
      var needPremCurrency = '0';
      try
      {
      	 needPremCurrency = document.all('needPremCurrency').value;
      	// alert("needPremCurrency@@@:"+needPremCurrency);
      	}
      	catch(e)
      	{
      		inpNeedPremCurrencyFlag = '0';
      		}
      		
     // alert("inpNeedPremCurrencyFlag:"+inpNeedPremCurrencyFlag);
      
        if(document.all('inpNeedPremGrid').value==1&&needPremCurrency=='0'){
			// initPremGrid();
		var sqlid36="ProposalInputSql36";
		var mySql36=new SqlClass();
		mySql36.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql36.setSqlId(sqlid36);// ָ��ʹ�õ�Sql��id
		mySql36.addSubPara(cRiskCode);// ָ������Ĳ���
	    tSql=mySql36.getString();
			
// strSql = "select a.dutycode,a.payplancode,a.payplanname,'','','','','',''
// from lmdutypayrela a where dutycode in "
// + " (select dutycode from lmriskduty where riskcode='"+cRiskCode+"' and
// SpecFlag='N')";

          turnPage.queryModal(tSql, PremGrid);
          PremGrid.lock;

        }
        
         if(document.all('inpNeedPremGrid').value==1&&needPremCurrency=='1'){
			// initPremGrid('1');
		var sqlid78="ProposalInputSql78";
		var mySql78=new SqlClass();
		mySql78.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql78.setSqlId(sqlid78);// ָ��ʹ�õ�Sql��id
		mySql78.addSubPara(cRiskCode);// ָ������Ĳ���
	    tSql=mySql78.getString();
			
// strSql = "select a.dutycode,a.payplancode,a.payplanname,'','','','','',''
// from lmdutypayrela a where dutycode in "
// + " (select dutycode from lmriskduty where riskcode='"+cRiskCode+"' and
// SpecFlag='N')";

          turnPage.queryModal(tSql, PremGrid);
          // alert("PremGrid.mulLineCount:"+PremGrid.mulLineCount);
          // PremGrid.checkAll(PremGrid,PremGrid.mulLineCount);
        /*
		 * for(i=1;i<=PremGrid.mulLineCount;i++) { PremGrid.checkBoxSel(i); }
		 */
        	// PremGrid.checkBoxAll();
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
      // var newWindow =
		// window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
      var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
      }
    if(document.all('AgentCode').value != "")  {
    var cAgentCode = fm.AgentCode.value;  // ��������
    
			var sqlid37="ProposalInputSql37";
		var mySql37=new SqlClass();
		mySql37.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql37.setSqlId(sqlid37);// ָ��ʹ�õ�Sql��id
		mySql37.addSubPara(cAgentCode);// ָ������Ĳ���
	    var strSql =mySql37.getString();
	
   // var strSql = "select AgentCode,Name,AgentGroup from LAAgent where
	// AgentCode='" + cAgentCode +"'";// and ManageCom =
	// '"+document.all('ManageCom').value+"'";
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

// ��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
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
    var cAgentCode = fm.AgentCode.value;  // ��������
    
		var sqlid38="ProposalInputSql38";
		var mySql38=new SqlClass();
		mySql38.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql38.setSqlId(sqlid38);// ָ��ʹ�õ�Sql��id
		mySql38.addSubPara(cAgentCode);// ָ������Ĳ���
	    var strSql =mySql38.getString();
	
   // var strSql = "select AgentCode,Name,AgentGroup from LAAgent where
	// AgentCode='" + cAgentCode +"' and ManageCom =
	// '"+document.all('ManageCom').value+"'";
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
  function returnparent()
  {
    // edit by yaory
    if(LoadFlag=="99")
    {
        location.href="ContInsuredInput.jsp?LoadFlag="+LoadFlag+"&prtNo="+prtNo+"&scantype="+scantype+"&checktype="+checktype+"";
        return;
    }
    var backstr=document.all("ContNo").value;
    mSwitch.deleteVar("ContNo");
      mSwitch.addVar("ContNo", "", backstr);
      mSwitch.updateVar("ContNo", "", backstr);
// location.href="ContInsuredInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype;
    // ���ڸ��գ����շ��ص�ҳ�治ͬ�ڴ˽�������
    // LoadFlag='1' ����
    // LoadFlag='2' ����
    // edit by yaory

    if(LoadFlag == "7" && EdorType =="NI"){
      location.href="../bq/GEdorTypeNI.jsp";
      return;
    }
    if(LoadFlag == "8" && EdorType =="NS"){
      location.href="../bq/PEdorTypeNSInput.jsp";
      return;
    }
    if(LoadFlag=="1")
    {
        if(BankFlag=="1")
        {
        	if(specScanFlag == "bposcan")
        	{
        		location.href="BankContInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype=bposcan&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
        	}
         else
        	{
           location.href="BankContInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
          }
         return;
        }
        else if(BankFlag=="5")
        {
            location.href="DirectContInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
            return;
        }
        else
        {
        location.href="ContInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
        return;
        }
    }
    if(LoadFlag=="2"||LoadFlag=="13"||LoadFlag=="4"||LoadFlag=="16"||LoadFlag=="23")
    {
        var aGrpContNo=parent.VD.gVSwitch.getVar( "GrpContNo" );
        if(BankFlag=="1")
          {
            location.href="BankContInsuredInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType + "&ProposalGrpContNo=" + aGrpContNo;
            return;
            }
        else
            {
          location.href="ContInsuredInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&ActivityID="+ActivityID+"&NoType="+NoType+"&tNameFlag="+NameType+"&display="+display+"&ProposalGrpContNo=" + aGrpContNo;
          return;
       }
    }
    if(LoadFlag=="3")
    {
        if(BankFlag=="1")
        {
        location.href="BankContInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
        return;
        }
        else if(BankFlag=="5")
        {
            location.href="DirectContInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
            return;
        }
        else
        {
        location.href="ContInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
        return;
        }
    }
    if(LoadFlag=="5")
    {
        if(BankFlag=="1")
        {
        location.href="BankContCheckInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
        return;
        }
        else if(BankFlag=="5")
        {
            location.href="DirectContCheckInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
            return;
        }
        else
        {
        location.href="ContCheckInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+backstr+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
        return;
        }
    }
    if(LoadFlag=="6")
    {
        if(BankFlag=="1")
        {
        location.href="BankContInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
        return;
        }
        else if(BankFlag=="5")
        {
            location.href="DirectContInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
            return;
        }
        else
        {
        location.href="ContInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
        return;
        }
    }
    if(LoadFlag=="25")
    {
        if(BankFlag=="1")
          {
            location.href="BankContInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
            return;
            }
            else if(BankFlag=="5")
        {
            location.href="DirectContInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
            return;
        }
        else
            {
          location.href="ContInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype+"&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ScanFlag="+ScanFlag+"&ActivityID="+ActivityID+"&NoType="+NoType;
          return;
        }
    }

}
// (GrpContNo,LoadFlag);//���ݼ����ͬ�Ų��������Ϣ
function getRiskByGrpPolNo(GrpContNo,LoadFlag)
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
	
		var sqlid39="ProposalInputSql39";
		var mySql39=new SqlClass();
		mySql39.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql39.setSqlId(sqlid39);// ָ��ʹ�õ�Sql��id
		mySql39.addSubPara(GrpContNo);// ָ������Ĳ���
	   strsql =mySql39.getString();
	
   // strsql = "select riskcode,riskname from lmriskapp where riskcode in
	// (select riskcode from LCGrpPol where GrpContNo='"+GrpContNo+"')" ;
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        m = turnPage.arrDataCacheSet.length;
        for (i = 0; i < m; i++)
        {
            j = i + 1;
// tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|"
// + turnPage.arrDataCacheSet[i][1];
            tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
        }
    }

    return tCodeData;
}
function getRiskByGrpAll(){
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
	
		var sqlid40="ProposalInputSql40";
		var mySql40=new SqlClass();
		mySql40.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql40.setSqlId(sqlid40);// ָ��ʹ�õ�Sql��id
		// mySql40.addSubPara(GrpContNo);//ָ������Ĳ���
	   strsql =mySql40.getString();
	
   // strsql = "select RiskCode, RiskName from LMRiskApp where RiskProp in
	// ('G','A','B','D') order by RiskCode" ;
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != ""){
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        m = turnPage.arrDataCacheSet.length;
        for (i = 0; i < m; i++){
            j = i + 1;
// tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|"
// + turnPage.arrDataCacheSet[i][1];
            tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
        }
    }
    return tCodeData;
}
function getRisk(){
        var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
	
			var sqlid41="ProposalInputSql41";
		var mySql41=new SqlClass();
		mySql41.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql41.setSqlId(sqlid41);// ָ��ʹ�õ�Sql��id
		// mySql41.addSubPara(GrpContNo);//ָ������Ĳ���
	   strsql =mySql41.getString();
	
// strsql = "select RiskCode, RiskName from LMRiskApp where RiskProp in
// ('I','A','C','D')"
// + " order by RiskCode";;
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        m = turnPage.arrDataCacheSet.length;
        for (i = 0; i < m; i++)
        {
            j = i + 1;
// tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|"
// + turnPage.arrDataCacheSet[i][1];
            tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
        }
    }
    return tCodeData;
}

function getRiskByContPlan(GrpContNo,ContPlanCode){
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
	
		var sqlid42="ProposalInputSql42";
		var mySql42=new SqlClass();
		mySql42.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql42.setSqlId(sqlid42);// ָ��ʹ�õ�Sql��id
        mySql42.addSubPara(GrpContNo);// ָ������Ĳ���
        mySql42.addSubPara(ContPlanCode);// ָ������Ĳ���
	   strsql =mySql42.getString();
	
    // strsql = "select b.RiskCode, b.RiskName from LCContPlanRisk a,LMRiskApp b
	// where a.GrpContNo='"+GrpContNo+"' and a.ContPlanCode='"+ContPlanCode+"'
	// and a.riskcode=b.riskcode";
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != ""){
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        m = turnPage.arrDataCacheSet.length;
        for (i = 0; i < m; i++){
            j = i + 1;
// tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|"
// + turnPage.arrDataCacheSet[i][1];
            tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
        }
    }
    return tCodeData;
}

/*******************************************************************************
 * ��¼������ʱ��ϵͳ���ɵı���������¼��ı��ѽ���У�飬 �������¼��ı�����ϵͳ���ɵı��Ѳ�����ϵͳҪ������ʾ�����Կɱ���,�����Կ���ת �����������ͬ��<Ͷ������>;
 * ����:true or false
 ******************************************************************************/
function checkpayfee(ContNo){
    var tContNo=ContNo;
    var tTempFee="";// ����¼��ı���
    var tPremFee="";// ϵͳ���ɵı���
    // ��ѯ����¼��ı���
	
		var sqlid43="ProposalInputSql43";
		var mySql43=new SqlClass();
		mySql43.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql43.setSqlId(sqlid43);// ָ��ʹ�õ�Sql��id
        mySql43.addSubPara(tContNo);// ָ������Ĳ���
	   var tempfeeSQL =mySql43.getString();
	
// var tempfeeSQL="select sum(nvl(paymoney,0)) from ljtempfee where
// tempfeetype='1' and confdate is null "
// +" and otherno=(select contno from lccont where prtno= '"+tContNo+"')";
    var TempFeeArr=easyExecSql(tempfeeSQL);
    if(TempFeeArr!=null){
        tTempFee=TempFeeArr[0][0];
    }
    // ��ѯϵͳ���ɵı���
	
			var sqlid44="ProposalInputSql44";
		var mySql44=new SqlClass();
		mySql44.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql44.setSqlId(sqlid44);// ָ��ʹ�õ�Sql��id
        mySql44.addSubPara(tContNo);// ָ������Ĳ���
	   var premfeeSQL =mySql44.getString();
	
// var premfeeSQL="select sum(nvl(prem,0)) from lcpol where 1=1 "
// +" and contno=(select contno from lccont where prtno= '"+tContNo+"')";
		
    var PremFeeArr=easyExecSql(premfeeSQL);
    tPremFee=PremFeeArr[0][0];
    if(PremFeeArr!=null){
        tPremFee=PremFeeArr[0][0];
        if(tPremFee==null || tPremFee=="" || tPremFee=="null")
        {
        alert("��ѯ��Ͷ������������Ϣ�����ɱ�������ʧ�ܣ�����");
        return false;
        }
    }
    // �Ƚϡ���ѯ����¼��ı��ѡ� �� ����ѯϵͳ���ɵı��ѡ��Ƿ���ȣ��粻����򵯳���Ϣ��ʾ
    if(tTempFee!="" && tPremFee!="" && tTempFee!="null" && tPremFee!="null" && (tTempFee!=tPremFee)){
        var ErrInfo="ע�⣺����¼��ı���["+tTempFee+"]��ϵͳ���ɵı���["+tPremFee+"]���ȡ�\n";
        ErrInfo=ErrInfo+"ȷ����Ҫ������ȷ�豣���밴��ȷ���������򰴡�ȡ������";
        if(confirm(ErrInfo)==false){
           return false;
        }
    }
    return true;
}

 /***************************************************************************
	 * �Ѻ�ͬ������Ϣ¼�����ȷ�� ���� �� �� ����ֵ�� ��
	 * ********************************************************************
	 */
function inputConfirm(wFlag){

    if (wFlag ==1 ) // ¼�����ȷ��
    {
        if(document.all('ContNo').value == "")
      {
        alert("��ͬ��Ϣδ����,������������ [¼�����] ȷ�ϣ�");
        return;
      }
      // ���¼�����ʴ����У��<����¼��ı�����ϵͳ���ɵı��Ѳ�����ϵͳҪ������ʾ�����Կɱ���>
        if(checkpayfee(document.all('ContNo').value)==false)
        {
            return false;
        }
	  if(CheckInvestInfo()==false)
	  {
	  	return  false;	
	  }
        
        // alert("ProposalInput.js_specScanFlag: "+specScanFlag);
        if(ScanFlag=="1" && specScanFlag != "bposcan"){  // ������ɨ���¼��
          fm.WorkFlowFlag.value = "0000001099";
        }
       else if(ScanFlag=="1" && specScanFlag == "bposcan"){   // ����Ͷ����¼��
          fm.WorkFlowFlag.value = "0000001094";
        }
       else{
          fm.WorkFlowFlag.value = "0000001098";
       }
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;
  }
  else if (wFlag ==2)// �������ȷ��
  {
    // return;
    // AppntChkFlag="false";
    if(AppntChkFlag=="false")
      {
	  	
		var sqlid45="ProposalInputSql45";
		var mySql45=new SqlClass();
		mySql45.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql45.setSqlId(sqlid45);// ָ��ʹ�õ�Sql��id
        mySql45.addSubPara(fm.ContNo.value);// ָ������Ĳ���
        mySql45.addSubPara(fm.AppntNo.value);// ָ������Ĳ���
	   var strSql =mySql45.getString();
		
        // var strSql = "select * from LCIssuePol where contno =
		// '"+fm.ContNo.value+"' and issuetype = '99' and questionobj =
		// '"+document.all('AppntNo').value+"'";
        var brrResult = easyExecSql(strSql);
        if(brrResult==null)
         {
          if(confirm("�Ƿ����Ͷ����У�飿")){
           return;
          }

         }
      }

    if(InsuredChkFlag=="false")
      {
	  	
		var sqlid46="ProposalInputSql46";
		var mySql46=new SqlClass();
		mySql46.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql46.setSqlId(sqlid46);// ָ��ʹ�õ�Sql��id
        mySql46.addSubPara(fm.ContNo.value);// ָ������Ĳ���
        mySql46.addSubPara(fm.ContNo.value);// ָ������Ĳ���
	    strSql =mySql46.getString();
		
       // strSql = "select * from LCIssuePol where contno =
		// '"+fm.ContNo.value+"' and issuetype = '99' and questionobj in (select
		// insuredno from lcinsured where contno='"+fm.ContNo.value+"')";
        var crrResult = easyExecSql(strSql);
        if(crrResult==null)
         {
          if(confirm("�Ƿ���б�����У�飿")){
            return;
          }
         }
       }

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
	    // У��Ͷ�������Ƿ���¼Ͷ�ʼƻ�
	  if(CheckInvestInfo()==false)
	  {
	  	return  false;	
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
  fm.submit(); // �ύ
}

// �˺�����Ŀ���ǣ���ѯ�ŵ����߸����µ�Ͷ������Ͷ����Ϣ
function getContInputnew(){
    // ȡ�ø���Ͷ���˵�������Ϣ
    if(fm.AppntCustomerNo.value!=""&&fm.AppntCustomerNo.value!="false"){
      // arrResult=easyExecSql("select a.*,(select GrpName from LDGrp where
		// CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo)
		// ),b.PostalAddress,b.ZipCode,b.Phone,b.HomePhone from LDPerson a Left
		// Join LCAddress b On b.CustomerNo =a.CustomerNo Where 1=1 and
		// a.CustomerNo ='"+fm.AppntCustomerNo.value+"'",1,0);
         // arrResult=easyExecSql("select a.* from LDPerson a Where 1=1 and
			// trim(a.CustomerNo) =trim('"+fm.AppntCustomerNo.value+"')");
    // alert("aaa=="+document.all('RelationToAppnt').value);
    // displayAppnt(arrResult[0]);
  }
    // ȡ��Ͷ����λ�� ������Ϣ
    if(fm.GrpContNo.value!=""&&fm.GrpContNo.value!="false"){
		
		var sqlid47="ProposalInputSql47";
		var mySql47=new SqlClass();
		mySql47.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql47.setSqlId(sqlid47);// ָ��ʹ�õ�Sql��id
        mySql47.addSubPara(fm.GrpContNo.value);// ָ������Ĳ���
	   var  strSql47 =mySql47.getString();
		
		arrResult = easyExecSql(strSql47, 1, 0);
      // arrResult = easyExecSql("select
		// a.*,b.GrpName,b.BusinessType,b.GrpNature,b.Peoples,b.RgtMoney,b.Asset,b.NetProfitRate,b.MainBussiness,b.Corporation,b.ComAera,b.Fax,b.Phone,b.FoundDate,b.CustomerNo,b.GrpName
		// from LCGrpAddress a,LDGrp b where a.CustomerNo = b.CustomerNo and
		// b.CustomerNo=(select CustomerNo from LCGrpAppnt where GrpContNo = '"
		// + fm.GrpContNo.value + "')", 1, 0);
      if(arrResult!=null)
         displayAddress1(arrResult[0]);
    }
    // ȡ�ñ�Ͷ���˵�������Ϣ
    if(document.all('CustomerNo').value!=""&&document.all('CustomerNo').value!="false"){
    var tcustomerNo=document.all('CustomerNo').value;
    var tContNo=document.all('ContNo').value;
	
		var sqlid48="ProposalInputSql48";
		var mySql48=new SqlClass();
		mySql48.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql48.setSqlId(sqlid48);// ָ��ʹ�õ�Sql��id
        mySql48.addSubPara(tcustomerNo);// ָ������Ĳ���
        mySql48.addSubPara(tContNo);// ָ������Ĳ���
	   var  strSql48 =mySql48.getString();
	
	 arrResult=easyExecSql(strSql48,1,0);
   // arrResult=easyExecSql("select a.*,(select GrpName from LDGrp where
	// CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.InsuredNo)
	// ),b.PostalAddress,b.ZipCode,b.Phone,b.HomePhone from LCInsured a Left
	// Join LCAddress b On b.CustomerNo =a.InsuredNo Where 1=1 and a.InsuredNo
	// ='"+tcustomerNo+"' and a.ContNo='"+tContNo+"'",1,0);
    displayInsured(arrResult[0]);
  }
}

function GrpConfirm(ScanFlag){// ScanFlag
     var tGrpContNo = parent.VD.gVSwitch.getVar( "GrpContNo" );


		var sqlid49="ProposalInputSql49";
		var mySql49=new SqlClass();
		mySql49.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql49.setSqlId(sqlid49);// ָ��ʹ�õ�Sql��id
        mySql49.addSubPara(tGrpContNo);// ָ������Ĳ���
	   strSql =mySql49.getString();

// strSql = "select peoples2 from LCGrpCont where GrpContNo = '" + tGrpContNo +
// "'";
    var tPeoplesCount = easyExecSql(strSql);

    if(tPeoplesCount==null||tPeoplesCount[0][0]<=0){
        alert("����ʧ�ܣ�Ͷ��������Ϊ0��");
        return;
    }

		var sqlid50="ProposalInputSql50";
		var mySql50=new SqlClass();
		mySql50.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql50.setSqlId(sqlid50);// ָ��ʹ�õ�Sql��id
        mySql50.addSubPara(tGrpContNo);// ָ������Ĳ���
	   strSql =mySql50.getString();

// strSql = "select peoples2,riskcode from LCGrpPol where GrpContNo = '" +
// tGrpContNo + "'";
    tPeoplesCount = easyExecSql(strSql);
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


		var sqlid51="ProposalInputSql51";
		var mySql51=new SqlClass();
		mySql51.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql51.setSqlId(sqlid51);// ָ��ʹ�õ�Sql��id
        mySql51.addSubPara(tGrpContNo);// ָ������Ĳ���
	   strSql =mySql51.getString();

// strSql = "select
// SaleChnl,AgentCode,AgentGroup,ManageCom,GrpName,CValiDate,PrtNo from
// LCGrpCont where GrpContNo = '"
// + tGrpContNo + "'";

    var grpContInfo = easyExecSql(strSql);
    var queryString = 'SaleChnl='+grpContInfo[0][0]+'&AgentCode='+grpContInfo[0][1]+'&AgentGroup='+grpContInfo[0][2]
        +'&ManageCom='+grpContInfo[0][3]+'&GrpName='+grpContInfo[0][4]+'&CValiDate='+grpContInfo[0][5];

// strSql = " select missionID,SubMissionID from lwmission where 1=1 "
// +" and lwmission.processid = '0000000004'"
// +" and lwmission.activityid = '0000002098'"
// +" and lwmission.missionprop1 = '"+grpContInfo[0][6]+"'";
					
					
		var sqlid52="ProposalInputSql52";
		var mySql52=new SqlClass();
		mySql52.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql52.setSqlId(sqlid52);// ָ��ʹ�õ�Sql��id
        mySql52.addSubPara(grpContInfo[0][6]);// ָ������Ĳ���
	    strSql =mySql52.getString();
					
    var missionInfo = easyExecSql(strSql);
	
    queryString = queryString+"&MissionID="+missionInfo[0][0]+"&SubMissionID="+missionInfo[0][1];
// var tStr= " select * from lwmission where 1=1 "
// +" and lwmission.processid = '0000000004'"
// +" and lwmission.activityid = '0000002001'"
// +" and lwmission.missionprop1 = '"+document.all('ProposalGrpContNo').value+"'";
					
		var sqlid53="ProposalInputSql53";
		var mySql53=new SqlClass();
		mySql53.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql53.setSqlId(sqlid53);// ָ��ʹ�õ�Sql��id
        mySql53.addSubPara(document.all('ProposalGrpContNo').value);// ָ������Ĳ���
	    var tStr =mySql53.getString();				
					
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
    fm.submit(); // �ύ

}

/*******************************************************************************
 * �Ѻ�ͬ������Ϣ¼�����ȷ�� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */
function inputConfirm2(wFlag)
{
    if (wFlag ==1 ) // ¼�����ȷ��
    {
		
		var sqlid54="ProposalInputSql54";
		var mySql54=new SqlClass();
		mySql54.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql54.setSqlId(sqlid54);// ָ��ʹ�õ�Sql��id
        mySql54.addSubPara(fm.ContNo.value);// ָ������Ĳ���
	    var tStr =mySql54.getString();			
		
   // var tStr= " select * from lwmission where 1=1 and lwmission.activityid in
	// ('0000001001','0000001002','0000001220','0000001230') and
	// lwmission.missionprop1 = '"+fm.ContNo.value+"'";
        turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
        if (turnPage.strQueryResult) {
          alert("�ú�ͬ�Ѿ��������棡");
          return;
        }

        fm.WorkFlowFlag.value = "0000001098";
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;          // ¼�����
  }
  else if (wFlag ==2)// �������ȷ��
  {
    if(document.all('ProposalContNo').value == "")
       {
          alert("δ��ѯ����ͬ��Ϣ,������������ [�������] ȷ�ϣ�");
          return;
       }
        fm.WorkFlowFlag.value = "0000001001";                   // �������
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
        fm.WorkFlowFlag.value = "0000001002";                   // �����޸����
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
        fm.WorkFlowFlag.value = "0000001021";                   // �����޸�
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
  fm.submit(); // �ύ
}

/*******************************************************************************
 * У�����ֽ���ķ����Ƿ�Ϊ���� ���� �� �� ����ֵ�� �� create by malong 2005-7-11
 * ********************************************************************
 */
function checkMult()
{
    var tSql="";
	
		var sqlid55="ProposalInputSql55";
		var mySql55=new SqlClass();
		mySql55.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql55.setSqlId(sqlid55);// ָ��ʹ�õ�Sql��id
        mySql55.addSubPara(fm.RiskCode.value);// ָ������Ĳ���
	   tSql =mySql55.getString();		
	
 // tSql="select AmntFlag from lmduty a,lmriskduty b where
	// b.riskcode='"+document.all('RiskCode').value+"' and a.dutycode=b.dutycode";
    var arrResult=easyExecSql(tSql);
    if(arrResult[0]=="2" && document.all('inpNeedDutyGrid').value == "0")
    {
        if(document.all('Mult').value== "")
        {
            alert('��������Ϊ��!');
            return false;
        }
        if(!isNumeric(document.all('Mult').value))
        {
                alert('��������Ϊ����!');
                document.all('mult').value = "";
                document.all('mult').focus();
                return false;
        }

        if(parseFloat(document.all('Mult').value) == 0)
        {
            alert('��������Ϊ��!');
            document.all('Mult').value = "";
            document.all('Mult').focus();
            return false;
        }
    }
    return true;
}
/*******************************************************************************
 * У��280���ֽ���Ķ�ѡ���ε����� ���� �� �� ����ֵ�� �� create by zhangyang 2005-7-29
 * ********************************************************************
 */
function chkDuty(){
    var tSql="";
  // tSql="select RiskCode from lcpol where
	// polno='"+document.all('MainPolNo').value+"'";
  
  		var sqlid56="ProposalInputSql56";
		var mySql56=new SqlClass();
		mySql56.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql56.setSqlId(sqlid56);// ָ��ʹ�õ�Sql��id
        mySql56.addSubPara(parent.VD.gVSwitch.getVar("mainRiskPolNo"));// ָ������Ĳ���
	   tSql =mySql56.getString();	
  
  var arrResult=easyExecSql(tSql);

 if(arrResult=="00608000"|| arrResult=="00609000" || arrResult=="00613000"){
    if(DutyGrid.getChkNo(1)){
      alert("�����ղ���ѡ�����ζ�!");
        return false;
        }
    }
  if(arrResult=="00615000"){
        if(DutyGrid.getChkNo(0)){
            alert("�����ղ���ѡ������һ!");
            return false;
            }
        }
    return true;
}
/*******************************************************************************
 * У��ɷѷ�ʽΪ����ʱ�ɷ��ڼ�ͱ����ڼ����һ�� ���� �� �� ����ֵ�� �� create by zhangzheng 2008-08-25
 * ********************************************************************
 */
 function chkPayEndYear(){
 if(fm.PayIntv!=null && fm.PayEndYear!=null && document.all('PayIntv').value!=null && document.all('PayEndYear').value!=null && document.all('PayIntv').value!="" && document.all('PayEndYear').value!=""){
   if(document.all('PayIntv').value=="0"){
       // if(document.all('PayEndYear').value!="1000" &&
		// document.all('PayEndYear').value!="0"){
         // alert("�ɷѷ�ʽΪ����ʱ,�ɷ����ڱ���Ϊ1000!");
         // return false;
        // }
	   if( fm.InsuYear!=null &&document.all('InsuYear').value!=null && document.all('InsuYear').value!="" ){
			   if(document.all('InsuYear').value!=document.all('PayEndYear').value)
			   {
				   alert("�ɷѷ�ʽΪ����ʱ,�ɷ����ڱ����뱣���ڼ�һ��");
			       return false;
			   }
			   if(document.all('InsuYearFlag').value!=document.all('PayEndYearFlag').value)
			   {
				   alert("�ɷѷ�ʽΪ����ʱ,�ɷ����ڱ�־�����뱣���ڼ��־һ��");
			       return false;
			   }
	   }
	   
    }
    return true;
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
	 
	   	var sqlid57="ProposalInputSql57";
		var mySql57=new SqlClass();
		mySql57.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql57.setSqlId(sqlid57);// ָ��ʹ�õ�Sql��id
        mySql57.addSubPara(fm.ContNo.value);// ָ������Ĳ���
	    tSql =mySql57.getString();	
	 
  // iSql="select a.relariskcode,m.riskname from lmriskrela a,lmrisk m,lcpol p
	// where a.relariskcode = m.riskcode and a.riskcode=p.riskcode and
	// p.mainpolno = p.polno and p.contno = '"+ContNo+"'";
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
     return tCodeData;
}

function gotoBnf(){
  if(scantype=="scan"){
      if(BankFlag=="1"){
          goToPic(0); top.fraPic.scrollTo(0, 792); showPosition(126+hx, 861+hy, 1079, 139);
      // goToPic(0); top.fraPic.scrollTo(94, 787); showPosition(114+hx,
		// 870+hy, 225, 120);
      }
    else{
          goToPic(0); top.fraPic.scrollTo(0, 1402); showPosition(77+hx, 1446+hy, 1105, 235);
          // goToPic(0); top.fraPic.scrollTo(0, 1440); showPosition(71+hx,
			// 1494+hy, 132, 187);
    }

  }
}


function showNotePad()
{
// var selno = SelfGrpGrid.getSelNo()-1;
// if (selno <0)
// {
// alert("��ѡ��һ������");
// return;
// }

  var MissionID = tMissionID;
  var SubMissionID = tSubMissionID;
// var ActivityID = SelfGrpGrid.getRowColData(selno, 6);
    var ActivityID = "0000001001";
// var PrtNo = SelfGrpGrid.getRowColData(selno, 2);
  var PrtNo = document.all.PrtNo.value;
// var NoType = document.all.NoType.value;
    var NoType = "1";
    if(PrtNo == null || PrtNo == "")
    {
        alert("Ͷ������Ϊ�գ�");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");

}

// ǿ�ƽ����˹��˱�
function forceUW(){
    // ��ѯ�Ƿ��Ѿ�����ѡ��ǿ�ƽ����˹��˱�
  var ContNo=document.all("ContNo").value;
  
  	   	var sqlid58="ProposalInputSql58";
		var mySql58=new SqlClass();
		mySql58.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql58.setSqlId(sqlid58);// ָ��ʹ�õ�Sql��id
        mySql58.addSubPara(ContNo);// ָ������Ĳ���
	    var sqlstr =mySql58.getString();	
  
  // var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
  arrResult = easyExecSql(sqlstr,1,0);
  if(arrResult==null){
    alert("�����ڸ�Ͷ������");
  }
  else{
    window.open("../uw/ForceUWMain.jsp?ContNo="+ContNo,"window1",sFeatures);
  }

}

// -------------------------------------------------Beg
// ��ӽɷ���֤
// �޸��ˣ�chenrong
// �޸����ڣ�2006-02-24

// У�飬����������ɷѷ�ʽ����������ɷ�����
function checkPayIntv()
{
    if (trim(fm.PayIntv.value) == "" || fm.PayIntv.value == null)
    {
        alert("����¼��ɷѷ�ʽ��Ϣ!");
        fm.PayIntv.focus();
        return ;
    }
}

// �ɷ�����˫��ʱ������ɷѷ�ʽΪ��������ʾ����
function payEndYearDBLClick()
{
    if (fm.PayIntv.value == "0")
    {
        return ;
    }
    var codeName = "!PayEndYear-"+fm.RiskCode.value+"*0&0";
    showCodeList(codeName,[fm.PayEndYear,fm.PayEndYearName],[0,1]);
}

// �ɷ����޴����ʱ������ɷѷ�ʽΪ��������ʾ����
function payEndYearKeyUP()
{
    if (fm.PayIntv.value == "0")
    {
        return ;
    }
    var codeName = "!PayEndYear-"+fm.RiskCode.value+"*0&0";
    return showCodeListKey(codeName,[fm.PayEndYear,fm.PayEndYearName],[0,1]);
}

// -----------------------------------------------End


// -------------------------------------------------Beg
// �޸��ˣ�chenrong
// �޸����ڣ�2006-03-13

// ����������Ϣʱ��������ȡ�������ȡ��ʽ����ȡƵ�ʵȣ�ƴ��һ���ַ�������
function setDutyKind()
{
    var tDutyKindFlag = 0;
    var tDutyKind = "";
    var tDutyKindN = "";
    if (fm.GetDutyKindFlag == null)
        return null;

    tDutyKindFlag = document.all('GetDutyKindFlag').value;

    if (tDutyKindFlag == null || tDutyKindFlag == "" || tDutyKindFlag == "null")
        return null;

    for (i = 0; i < tDutyKindFlag; i++)
    {
        m = i + 1;
        tDutyKindN = "";
        tDutyKindN = trim(document.all('GetDutyKind' + m).value);
        if (tDutyKindN == null || tDutyKindN == "" || tDutyKindN == "null")
        {
            tDutyKind = "";
            document.all('GetDutyKind' + m).focus();
            return tDutyKind;
        }

        if (m == 1 && tDutyKindN == "0") // ��������죬������ֵ��Ч.
        {
            tDutyKind = "000000"
            break;
        }
        else
        {
            if (tDutyKindN.length == 1)
                tDutyKindN = "0" + tDutyKindN;
            tDutyKind = trim(tDutyKind) + tDutyKindN;
        }

    }
    document.all('GetDutyKind').value = tDutyKind;
}

// ��ѯ���ֽ����ʼ��ʱ��GetDutyKind�������ֵ���GetDutyKind�ַ���
function splitDutyKind()
{
    try
    {
        if (fm.GetDutyKindFlag == null)
            return ;
        var tGetDutyKindFlag = document.all('GetDutyKindFlag').value;
        var tGetDutyKind = document.all('GetDutyKind').value;
        if (tGetDutyKind == null || tGetDutyKind == "" || tGetDutyKind == "null")
        {
            return;
        }

        if (tGetDutyKind == "000000")  // ��������죬������ȡ�����ֵ
        {
            document.all('GetDutyKind1').value = "0";
            return;
        }

        for (i = 0; i < tGetDutyKindFlag; i++)
        {
            m = i + 1;
            j = i * 2;
            document.all('GetDutyKind' + m).value = tGetDutyKind.substring(j,j + 2);
        }
    }
    catch(ex)
    {
    }
}

// -----------------------------------------------End

// ------------------------Beg
// Add By Chenrong
// Date:2006.5.12

// ��ѯ����Ա�涯�ٶ�
function initQueryRollSpeed()
{
	
	  	 var sqlid59="ProposalInputSql59";
		var mySql59=new SqlClass();
		mySql59.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql59.setSqlId(sqlid59);// ָ��ʹ�õ�Sql��id
     // mySql59.addSubPara(ContNo);//ָ������Ĳ���
	    var strSQL =mySql59.getString();	
	
  // var strSQL = "select SYSVAR,SYSVARVALUE from LDSysvar where SYSVAR like
	// 'ROLLSPEED%25' order by SYSVAR ASC";
    var arrSpeed = easyExecSql(strSQL);
    if (arrSpeed != null)
    {
        fm.RollSpeedBase.value = arrSpeed[0][1];
        fm.RollSpeedSelf.value = arrSpeed[1][1];
    }

	  	 var sqlid60="ProposalInputSql60";
		var mySql60=new SqlClass();
		mySql60.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql60.setSqlId(sqlid60);// ָ��ʹ�õ�Sql��id
       mySql60.addSubPara( Operator);// ָ������Ĳ���
	    var strSQL =mySql60.getString();	

    // strSQL = "select rollspeed from LDRollSpeed where UserCode='" +
	// fm.Operator.value + "'";
    arrSpeed1 = null;
    arrSpeed1 = easyExecSql(strSQL);
    if (arrSpeed1 != null)
    {
        fm.RollSpeedOperator.value = arrSpeed1[0][0];
    }
    else
        fm.RollSpeedOperator.value = 1;
}

// ����������Ϣ���ý���Ŀؼ�����
var totalFieldArray = new Array(
        "RiskCode","BnfGrid2","BnfGrid3","BnfGrid4","BnfGrid5"
        ,"BnfGrid6","BnfGrid7","BnfGrid10","InsuYear",
        "PayIntv","PayEndYear","GetYear","GetDutyKind",
        "Mult", "Amnt","Prem"
    );

// ��Բ�ͬ���֣���ȡ���������Զ���ý���Ŀؼ�����
var autoFieldArray = new Array
function setAutoField()
{
	
    var tFieldNum = 0;
    var tBnfCount = 0;
    var tBnfFieldNum = 0;
    for (i = 0; i < totalFieldArray.length; i++)
    {
        try
        {
            tValue = eval("fm." + totalFieldArray[i] + ".value");
            if (totalFieldArray[i].indexOf("BnfGrid") >= 0 && BnfGrid.mulLineCount >1)
            {
                // ���BnfGrid����������1���������ý����BnfGrid1��BnfGrid2...�ȣ�
                // Ҫ�����ǵ��������ƣ�������BnfGrid1[0]��BnfGrid1[1]�ȣ�������ֱ�Ӵ���BnfGrid1��BnfGrid2...�ȣ�
                tBnfCount = tBnfCount + 1;

                // �����BnfGrid�ĵ�һ�У���autoFieldArray��ƫ����ΪtFieldNumֵ
                // ����Ϊ��ԭƫ��ֵ�ϼ�1
                if (tBnfCount == 1)
                   tBnfFieldNum = tFieldNum;
                else
                    tBnfFieldNum++;
                tBnfLength = eval("fm." + totalFieldArray[i] + ".length"); // ȡ���еĳ���
                for (j = 0; j < tBnfLength; j++)
                {
                    // ��Ϊ���߸�BnfGrid�����ý���
                    // ��ǰ�еĵ�j�пؼ�������autoFieldArray��λ�þ���j*7����ƫ������ֵ
                    autoFieldArray[j * 7 + tBnfFieldNum] = totalFieldArray[i] + "[" + j + "]";
                }
                tFieldNum = tFieldNum + tBnfLength;
            }
            else
            {
                autoFieldArray[tFieldNum] = totalFieldArray[i];
                tFieldNum++;
            }
        }
        catch(ex)
        {
            continue;
        }
    }
}

// �Զ���ý���
function AutoTab()
{
      try
      {
        if (LoadFlag != "5" || scantype != "scan")
        return;
    var tObject=document.activeElement;
    if (tObject.tagName == "INPUT")
        return;
    setAutoField();
        initAutoValue(autoFieldArray,mRollSpeedOperator,mRollSpeedSelf,mRollSpeedBase);
      }
      catch(ex)
      {}

}
// -----------------------End

// -----------------------Begin
// Add By Chenrong
// Date:2006.6.20
// У������ۿۣ����ܴ���1С��0
function verifyFloatRate()
{
    try{
        if (fm.CalRule.value == "2")
        {
            if (checkNumber(fm.FloatRate) == false)
            {
                return false;
            }
            if (!(parseFloat(fm.FloatRate.value)<1 && parseFloat(fm.FloatRate.value)>0))
            {
                try{
                    fm.FloatRate.focus();
                    fm.FloatRate.select();
                }
                catch(ex){return true;}

                alert("�����ۿ۲���С��0����1������ȷ���룡");
                return false;
            }
        }
        return true;
    }
    catch(ex){return true;}
}

function getDutyCode(cRiskCode)
{
    // ��ѯ�˻����е�������Ϣ
	
	    var sqlid61="ProposalInputSql61";
		var mySql61=new SqlClass();
		mySql61.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql61.setSqlId(sqlid61);// ָ��ʹ�õ�Sql��id
       mySql61.addSubPara(cRiskCode);// ָ������Ĳ���
	    var tSql =mySql61.getString();	
	
// var tSql="select a.dutycode,a.specflag from lmriskduty a ,lmrisk b where 1=1"
// +" and a.riskcode=b.riskcode and b.insuaccflag='Y'"
// +" and a.riskcode='"+cRiskCode+"'";
		
    var arrResult=easyExecSql(tSql);
    var isPubacc = "1";
    var tDutyCode = "";
    if (arrResult != null)
    {
        for (i = 0; i < arrResult.length; i++)
        {
            if (arrResult[i][1] == "Y")
            {
                isPubacc = "0";
            }
            else
            {
                tDutyCode = arrResult[i][0];
            }
        }
    }
    if (isPubacc == "1")
        tDutyCode = "";
    return tDutyCode;
}
// -----------------------End

function showRiskParamCodeName(){
    var cRiskCode = fm.RiskCode.value;
    var tPayEndYearCode="!PayEndYear-"+cRiskCode+"*0&0";
    var tPayIntvCode="!PayIntv-"+cRiskCode+"*0&0";
    var tGetYearCode="!GetYear-"+cRiskCode+"*0&0";
    var tGetDutyKindCode="!GetDutyKind-"+cRiskCode+"*0&0";
    var tInsuYearCode="!InsuYear-"+cRiskCode+"*0&0";
    var tLiveGetMode="!LiveGetMode-"+cRiskCode+"*0&0";
    var tInterestDifTypeCode="!InterestDifType-"+cRiskCode+"*0&0";
    var tPayEndYearFlag="!PayEndYearFlag-"+cRiskCode+"*0&0";
    try{showOneCodeName(tPayEndYearCode,'PayEndYear','PayEndYearName');}catch(ex){};
    try{showOneCodeName(tPayIntvCode,'PayIntv','PayIntvName');}catch(ex){};
    try{showOneCodeName(tGetYearCode,'GetYear','GetYearName');}catch(ex){};
    try{showOneCodeName(tGetDutyKindCode,'GetDutyKind','GetDutyKindName');}catch(ex){};
    try{showOneCodeName(tInsuYearCode,"InsuYear","InsuYearName");
       }catch(ex){};
    try{showOneCodeName(tInterestDifTypeCode,'InterestDifType','InterestDifTypeName');}catch(ex){};
    try{showOneCodeName(tLiveGetMode,'LiveGetMode','LiveGetModeName');}catch(ex){};
    try{showOneCodeName(tPayEndYearFlag,'PayEndYearFlag','PayEndYearFlagName');}catch(ex){};
}

// ���ݱ�ȫ��������,������Ч���ںͱ�������ֹ��������˵ı���ֹ��
// ���� oldInY Ϊ �ƻ�ֹ�� ���� 12
// ���� oldUnitΪ �ƻ�ֹ�ڵ�λ ���� M
// D|��^M|��^Y|��
// XinYQ rewrote on 2007-04-03
function getNIInsuYear(oldInY, oldUnit)
{
    var nResultInsuYear = 0;
    var sCValiDate = CValiDate.substring(0,10);
    var sEdorValiDate = EdorValiDate;
    if (oldUnit != "M")
    {
        nResultInsuYear = oldInY;
    }
    else
    {
        sCValiDate = getDate(sCValiDate);
        sEdorValiDate = getDate(sEdorValiDate);
        var startD = sCValiDate.getDate();
        var startM = sCValiDate.getMonth();
        var startY = sCValiDate.getFullYear();
        var endD = sEdorValiDate.getDate();
        var endM = sEdorValiDate.getMonth();
        var endY = sEdorValiDate.getFullYear();
        var nMonthDiff = (endY - startY) * 12 + (endM - startM);
        // ����һ���°�һ���¼���
        if (endD >= startD)
        {
            nResultInsuYear = oldInY - nMonthDiff;
        }
        else
        {
            nResultInsuYear = oldInY - nMonthDiff + 1;
        }
    }
    return nResultInsuYear;
}

// tongmeng
function showMultRiskGrid(tRiskCodeVar)
{
	 // return ;
	 // alert('@@!!!!!!!!!!!');
	 	// var tSQL_Display = "select 1 from lmriskapp where
		// riskcode='"+tRiskCodeVar+"' and subriskflag='M'";
		
	    var sqlid62="ProposalInputSql62";
		var mySql62=new SqlClass();
		mySql62.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql62.setSqlId(sqlid62);// ָ��ʹ�õ�Sql��id
       mySql62.addSubPara(tRiskCodeVar);// ָ������Ĳ���
	    var tSQL_Display =mySql62.getString();	
		
	 	arrResult=easyExecSql(tSQL_Display, 1, 0);
    if (arrResult != null) {
    	// ���¼������,�������޸�.���Ҳ���ʼ������
    	initMultMainRiskGrid('0');
    }
    else
    {
    	initMultMainRiskGrid('1');
    }
    // alert("��ǰ��������Ϣ:"+parent.VD.gVSwitch.getVar("InsuredNo"));
    // and insuredno!='"+parent.VD.gVSwitch.getVar("InsuredNo")+"'
	  	var tPrtNo = mSwitch.getVar("ContNo");
		
		var sqlid63="ProposalInputSql63";
		var mySql63=new SqlClass();
		mySql63.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql63.setSqlId(sqlid63);// ָ��ʹ�õ�Sql��id
        mySql63.addSubPara(tPrtNo);// ָ������Ĳ���
        mySql63.addSubPara(parent.VD.gVSwitch.getVar("InsuredNo"));// ָ������Ĳ���
	    var tSQL =mySql63.getString();	
		
// var tSQL = " select a.riskcode,b.riskname,a.mainpolno,b.subriskflag "
// + " from lcpol a,lmriskapp b where mainpolno=polno and a.riskcode=b.riskcode
// "
// + " and prtno='"+tPrtNo+"' "
// + " and insuredno='"+parent.VD.gVSwitch.getVar("InsuredNo")+"' ";
 				  // prompt('',tSQL);
 		turnPage.queryModal(tSQL,MultMainRiskGrid);
 		// ����Ǹ�����,����ֻ��һ��������Ϣʱ,Ĭ��ѡ���һ��!
 		if(MultMainRiskGrid.canSel==1&&MultMainRiskGrid.mulLineCount==1)
 		{
 			// alert('@@');
 			document.all('MultMainRiskGridSel').checked=true;
 			// eval("document.all('MultMainRiskGridSel')[" +
			// MultMainRiskGrid.mulLineCount + "-1].checked=true");
 			onMultMainRiskGridSelect();
 		}
 		// �����¼��������Ϣ,��ʾ�ѱ���������Ϣ.
 		if(MultMainRiskGrid.mulLineCount>=1)
 		{
 			document.all('divMultMainRisk').style.display = "";
 		}
 		// �������ֽ������ձ�������Ϣ
 		
 		// alert("@@"+MultMainRiskGrid.canSel+"##"+MultMainRiskGrid.mulLineCount);
 		
}

function onMultMainRiskGridSelect(parm1,parm2)
{
	// alert('111');
	document.all('MainPolNo').value=MultMainRiskGrid.getRowColData(MultMainRiskGrid.getSelNo() - 1, 3);
	// alert(MultMainRiskGrid.getRowColData(MultMainRiskGrid.getSelNo() - 1,
	// 3));
}

function checkCValiDatedate()
{
	if(trim(fm.CValiDate.value)==""){return ;}
    else if (fm.CValiDate.value.length == 8)
	{
		if(fm.CValiDate.value.indexOf('-')==-1)
		{
			var Year =     fm.CValiDate.value.substring(0,4);
			var Month =    fm.CValiDate.value.substring(4,6);
			var Day =      fm.CValiDate.value.substring(6,8);
			fm.CValiDate.value = Year+"-"+Month+"-"+Day;
			if(Year=="0000"||Month=="00"||Day=="00")
			{
				alert("���������������!");
				fm.CValiDate.value = "";
				fm.CValiDate.focus();
				return;
			}
		}
	}
	else if(fm.CValiDate.value.length == 10)
	{
		var Year =     fm.CValiDate.value.substring(0,4);
		var Month =    fm.CValiDate.value.substring(5,7);
		var Day =      fm.CValiDate.value.substring(8,10);
		if(Year=="0000"||Month=="00"||Day=="00")
		{
		alert("���������������!");
		fm.CValiDate.value = "";
		fm.CValiDate.focus();
		return;
		}
	}
	else
	{
		alert("���������������!");
        fm.CValiDate.value = "";
        fm.CValiDate.focus();
        return;
	}
}

// tongmeng 2008-10-09 add
// ���Ͷ���˺ͱ���������,���ŵ�������.
function getAppntAndInsuredForChangeRiskPlan(tPolNo)
{
	
		var sqlid64="ProposalInputSql64";
		var mySql64=new SqlClass();
		mySql64.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql64.setSqlId(sqlid64);// ָ��ʹ�õ�Sql��id
        mySql64.addSubPara(tPolNo);// ָ������Ĳ���
	    var tSQL_Cont =mySql64.getString();	
	
	// var tSQL_Cont = "select contno,appntno,insuredno,managecom from lcpol
	// where polno='"+tPolNo+"'";
	var arrResult = easyExecSql(tSQL_Cont, 1, 0);
	if(arrResult!=null)
	{
	
		document.all('AppntCustomerNo').value= arrResult[0][1];
		document.all('CustomerNo').value= arrResult[0][2];
		// ��ѯͶ���˺ͱ�����
		queryAppntNo();
		queryInsuredNo();
		document.all('ContNo').value= arrResult[0][0];
		document.all('PrtNo').value= arrResult[0][0];
		document.all('ManageCom').value= arrResult[0][3];
	}
	// �޸Ľ���ĳ�ʼ��,���س�������֮�����Ϣ.
	// alert(document.getElementById("divLCBnf1"));
	
	document.getElementById("divLCBnf1Main").style.display="none";
	document.getElementById("divLCBnf1").style.display="none";
	document.getElementById("DivLCSpec").style.display="none";
	// DivLCSpec.style.display='none';
	// DivLCSpec1.style.display='none';
	
	// DivLCBnf.style.display='none';
	// divLCBnf1.style.display='none';
}

// tongmeng 2008-10-23 add
// �ڴ�����֮�����س�������֮�����Ϣ
function afterDealForChangeRiskPlan()
{
	// DivLCSpec.style.display='none';
	// DivLCSpec1.style.display='none';
	
	// DivLCBnf.style.display='none';
	// divLCBnf1.style.display='none';
}

// �����ύ�����棩
function submitFormPlan()
{
 // ifSavedRiskCode(document.all("RiskCode").value);
	// ����У��

	var tSQL="";
	var tContNo=fm.ContNo.value;
	var riskcode=fm.RiskCode.value;
	var tResult="";
	
	
			var sqlid68="ProposalInputSql68";
		var mySql68=new SqlClass();
		mySql68.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql68.setSqlId(sqlid68);// ָ��ʹ�õ�Sql��id
        mySql68.addSubPara(tContNo);// ָ������Ĳ���
         mySql68.addSubPara(riskcode);// ָ������Ĳ���
	    var tempSQL =mySql68.getString();	
			
	tResult = easyExecSql(tempSQL);
    if (tResult != null && tResult.length > 0)
    {
    	document.all("PolNo").value=tResult[0][0];
    }
	 if(document.all("PolNo").value=="")
	{
		alert("�ȱ���������Ϣ!");
		return false;
	}	
	
	  if(InvestPlanRate.mulLineCount==0)
    { 
		    alert("û��������Ϣ��");
		     return false;
	  }else{
	      
// var sql="select count(*) from LCPerInvestPlan where
// PolNo='"+document.all("PolNo").value+"'";
// /////////////////////add by sunyh on 20070914///////////////////////
		
// /////////////////////end by sunyh on 20070914 add///////////////////////
	var sqlid69="ProposalInputSql69";
		var mySql69=new SqlClass();
		mySql69.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql69.setSqlId(sqlid69);// ָ��ʹ�õ�Sql��id
        mySql69.addSubPara(document.all("PolNo").value);// ָ������Ĳ���

	    var tempSQL1 =mySql69.getString();	
		


	         var counta=new Array;
	         counta=easyExecSql(tempSQL1);
	      	 if(counta>0)
	      	 {
	      	 	alert("�������Ѵ��ڣ���ѡ��������!");
	      	 	return false;
	      	}
	   }	
	document.all('mPlanOperate').value = "INSERT";
	
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		fm.action="./InvestPlanInputSave.jsp";
	 fm.submit(); // �ύ
}
function updateClickPlan()
{
	
	   	 if(InvestPlanRate.mulLineCount==0)
    	 { 
		    alert("û��������Ϣ��");
		     return false;
	      }	
	  
	document.all('mPlanOperate').value = "UPDATE";
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		fm.action="./InvestPlanInputSave.jsp";
	fm.submit(); // �ύ

}


// �����ύ��ɾ����
function DeleteClickPlan()
{
 
	   	 if(InvestPlanRate.mulLineCount==0)
    	 { 
		    alert("û��������Ϣ��");
		     return false;
	      }
	document.all('mPlanOperate').value = "DELETE";
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action="./InvestPlanInputSave.jsp";
	fm.submit(); // �ύ

}
function ifTLrisk(riskcode)
{
	var tSQL="";
	var tResult="";
// tSQL="select * from LmriskApp where risktype3='3' and
// riskcode='"+riskcode+"'";
	var sqlid67="ProposalInputSql67";
		var mySql67=new SqlClass();
		mySql67.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql67.setSqlId(sqlid67);// ָ��ʹ�õ�Sql��id
        mySql67.addSubPara(riskcode);// ָ������Ĳ���
	    var tempSQL =mySql67.getString();	
			

	    	tResult = easyExecSql(tempSQL);
	    // alert(tResult.length)
	    if(tResult != null && tResult.length > 0)
	    {
	    		// divInvestPlanRate.style.display="";
	    	return true;
	    }else{
	    	// divInvestPlanRate.style.display="none";
	    	return false;
	    }
	    
}
function  CheckInvestInfo()
{
	
	var sqlid65="ProposalInputSql65";
		var mySql65=new SqlClass();
		mySql65.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql65.setSqlId(sqlid65);// ָ��ʹ�õ�Sql��id
        mySql65.addSubPara(fm.ContNo.value);// ָ������Ĳ���
        mySql65.addSubPara(fm.CustomerNo.value);// ָ������Ĳ���
	    var tSQL_Cont =mySql65.getString();	
	
	// var tSQL_Cont = "select contno,appntno,insuredno,managecom from lcpol
	// where polno='"+tPolNo+"'";

	var riskarr =new Array;
	turnPage.strQueryResult  = easyQueryVer3(tSQL_Cont);
	  if (!turnPage.strQueryResult) {
    alert("�������������Ϣ��");
    return false;
    }
	 riskarr = decodeEasyQueryResult(turnPage.strQueryResult);
	 
	 for(var i=0; i<riskarr.length; i++){
	 	// �����Ͷ�����֣�������Ƿ��Ѿ�¼��Ͷ�ʼƻ�
	
	 	if(riskarr[i][1]==3)
	 	{
	 		
// var tsql="select * from LCPerInvestPlan where polno='"+riskarr[i][2]+"'";
// /////////////////////add by sunyh on 20070914///////////////////////
              // mySql.setSqlId("ProposalInputInputSql_84");
              // mySql.addPara('polno',riskarr[i][2]);
// /////////////////////end by sunyh on 20070914 add///////////////////////
			
				var sqlid66="ProposalInputSql66";
		var mySql66=new SqlClass();
		mySql66.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql66.setSqlId(sqlid66);// ָ��ʹ�õ�Sql��id
        mySql66.addSubPara(riskarr[i][2]);// ָ������Ĳ���
	    var tempSQL =mySql66.getString();	
			


	 		var planarr=new Array;
	 		planarr=easyExecSql(tempSQL);
	 		if (planarr==null)
   {
     alert ("���Ͷ������"+riskarr[i][0]+" ���Ͷ�ʼƻ���");	
     return false;
   } 
	 	} 	
	}
	 return true;
}

function CheckTLRiskData(criskcode)
{
	
	if(criskcode=='SPUL')
	{
			var cPayIntv=fm.PayIntv.value;
			if(cPayIntv!=0)
			{
				alert("�ɷѷ�ʽ����");
		 		 return false;
			}
	}
	if(criskcode=='RPUL')
	{
		var prem1="";
		var prem2="";
		var cPayIntv=fm.PayIntv.value;
		 prem1=PremGrid.getRowColData(0,4);
		 prem2=PremGrid.getRowColData(1,4);
		 fm.Mult.value="";
		 if(document.all('Amnt').value=='')
		 {
		 	alert("����¼�뱣��");
		 	return false;
		 }else{
		 DutyGrid.setRowColData(0,14,document.all('Amnt').value);
	  }
	 	for(var k=0;k<=DutyGrid.mulLineCount-1;k++)
	   {
	     DutyGrid.setRowColData(k,18,document.all('PayIntv').value);
	   }  				 	   
	    if(!PremGrid.getChkNo(0))
		 {
		 	alert("�ڽɱ��ѱ�ѡ��");
		 	return false;
	   }
	   if(cPayIntv==0){
		alert("�ɷѷ�ʽ����");
		 		 return false;
	  }
/*
 * if(PremGrid.getChkNo(1)){ if(prem2!=''){ if(prem2<500) {
 * alert("׷�ӱ���Ӧ�ô��ڵ���500"); return false; } if(prem2%100!=0){
 * alert("׷�ӱ���Ӧ��Ϊ100��������"); return false; } } }
 */
	
	// ���屣��
	var strAmnt = document.all('Amnt').value;
	
	/*
	 * if(cPayIntv==12) { if(prem1<2000||prem1>500000) {
	 * alert("��ɱ���Ӧ�ô��ڵ���2000��С�ڵ���500000"); return false; }
	 * if(prem1>=6000&strAmnt<120000) { alert("��ͱ���Ϊ120000"); return false; }
	 * if(prem1<6000&strAmnt<prem1*20) { alert("��ͱ���Ϊ"+prem1*20); return
	 * false; } }else if(cPayIntv==6){ if(prem1<1000||prem1>250000) {
	 * alert("����ɱ���Ӧ�ô��ڵ���1000��С�ڵ���250000"); return false; }
	 * if(prem1>=3000&strAmnt<60000) { alert("��ͱ���Ϊ60000"); return false; }
	 * if(prem1<3000&strAmnt<prem1*20) { alert("��ͱ���Ϊ"+prem1*20); return
	 * false; }
	 * 
	 * }else if(cPayIntv==3){ if(prem1<500||prem1>100000) {
	 * alert("���ɱ���Ӧ�ô��ڵ���500��С�ڵ���100000"); return false; }
	 * 
	 * if(prem1>=1500&strAmnt<30000) { alert("��ͱ���Ϊ30000"); return false; }
	 * if(prem1<1500&strAmnt<prem1*20) { alert("��ͱ���Ϊ"+prem1*20); return
	 * false; }
	 * 
	 * }else if(cPayIntv==1){ if(prem1<200||prem1>50000) {
	 * alert("�½ɱ���Ӧ�ô��ڵ���200��С�ڵ���50000"); return false; }
	 * 
	 * if(prem1>=500&strAmnt<10000) { alert("��ͱ���Ϊ10000"); return false; }
	 * if(prem1<500&strAmnt<prem1*20) { alert("��ͱ���Ϊ"+prem1*20); return false; }
	 * 
	 * }else{ alert("�ɷѷ�ʽ����"); return false; }
	 */
	}
	
	return true;
}
function IsSetNext(){
	if(fm.RiskAlias.value.toUpperCase()=="SPUL"){
		fm.Prem.focus();
	}
}

function gotoInvestPlan(){
	
	if(scantype=="scan"){
		if(BankFlag=="1")
		{		 
		}
		else
		{
			if(BussSubType == "04")
			{
				goToPic(1); top.fraPic.scrollTo(679, 594); showPosition(962+hx, 641+hy, 691, 315); 
			}
			else
			{					
				// goToPic(1); top.fraPic.scrollTo(263, 986);
				// showPosition(649+hx, 991+hy, 430, 252);
				goToPic(1); top.fraPic.scrollTo(632, 662); showPosition(1004+hx, 689+hy, 539, 292);
			}
		}
	
	}
}

function ifSavedRiskCode(riskcode)
{ 
	try{
	var tSQL="";
	var tContNo=fm.ContNo.value;
	var tInsuredNo=fm.CustomerNo.value;
	var tResult="";
	var tProposalContNo = fm.ProposalContNo.value;
	// alert("tContNo:"+tContNo);
			var sqlid70="ProposalInputSql70";
		var mySql70=new SqlClass();
		mySql70.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql70.setSqlId(sqlid70);// ָ��ʹ�õ�Sql��id
        mySql70.addSubPara(tContNo);// ָ������Ĳ���
         mySql70.addSubPara(riskcode);// ָ������Ĳ���
          mySql70.addSubPara(tInsuredNo);// ָ������Ĳ���
	    var tempSQL =mySql70.getString();	
			
	tResult = easyQueryVer3(tempSQL);
	// alert("w1");
// alert("%%%%:"+tResult);
    if (tResult != null && tResult.length > 0)
    {
    	if(scantype=="scan") {
			setFocus();
		}	  
	// alert("w2");
	  	// emptyForm();
	  		try{emptyInputInfo();   }catch(ex){}  
	  	try{fm.PolNo.value = tResult[0][0];}catch(ex){}  	
    	try{fm.MainPolNo.value = tResult[0][1];}catch(ex){}
    	try{fm.RiskCode.value = tResult[0][2];}catch(ex){}
    	try{fm.PayIntv.value = tResult[0][3];}catch(ex){}
    	try{fm.PayIntvName.value = tResult[0][15];}catch(ex){}
    	try{fm.PayEndYear.value = tResult[0][4];}catch(ex){}
    	try{fm.PayEndYearFlag.value = tResult[0][5];}catch(ex){}
    	try{fm.InsuYear.value = tResult[0][6];}catch(ex){}
    	try{fm.InsuYearFlag.value = tResult[0][7];}catch(ex){}
    	try{fm.Amnt.value = tResult[0][8];}catch(ex){}
    	try{fm.Prem.value = tResult[0][9];}catch(ex){}
    	try{fm.Mult.value = tResult[0][10];}catch(ex){}    	
    	try{fm.BonusGetMode.value = tResult[0][12];}catch(ex){}
    	try{fm.AutoPayFlag.value = tResult[0][13];}catch(ex){}
    	try{fm.RiskAliasName.value = tResult[0][14];}catch(ex){}
    	try{fm.Currency.value = tResult[0][22];}catch(ex){}
    	try{fm.CurrencyName.value = tResult[0][23];}catch(ex){}
    	try{fm.StandbyFlag1.value = tResult[0][24];}catch(ex){}
    	try{fm.StandbyFlag1Name.value = tResult[0][25];}catch(ex){} 
    	
    	try{fm.BonusFlag.value = tResult[0][16]; }catch(ex){} 
    	
    	try{tSubRiskFlag = tResult[0][17];}catch(ex){} 
    	
    	try{fm.AutoPay.value = tResult[0][18];}catch(ex){} 
    	
    	try{tCountDuty = tResult[0][19];}catch(ex){} 
    	
    	try{tCountPrem = tResult[0][20];}catch(ex){} 
    	
    	try{fm.ProposalNo.value = tResult[0][21];  }catch(ex){} 
    	
// alert("w3"+riskcode);
		var tISTL = ifTLrisk(riskcode);		
		
	// alert("w42");
		
	
		try
		{ 
    	if (tCountDuty != null && parseInt(tCountDuty)> 0)
		{
			fm.inpNeedDutyGrid.value = 1;
			if (tISTL == false)
			{
				DivChooseDuty.style.display = "";	
				initQueryDuty(fm.PolNo.value);
			}
			// else
		    // {
		    // initDutyGrid();
		    // if(initDealForSpecRiskEx(fm.RiskCode.value)==false)
			// {
			// myAlert(I18NMsg("M0000054491")+cRiskCode+I18NMsg("M0000054492"));
			// return false;
			// }
		   // }
		}
	    else
		{
			fm.inpNeedDutyGrid.value = 0;
			DivChooseDuty.style.display = "none";
		}
	}
	catch(e){}

	// alert("tISTL"+tISTL)
		if (tISTL == true)
		{
			isMullPrem(fm.RiskCode.value); 
			if (fm.inpNeedPremGrid.value == '1')
			{	
				initQueryPrem(fm.PolNo.value,fm.RiskCode.value); 
			}
			try{
				// �ж��Ƿ�ΪͶ������
				// DivChooseDuty.style.display = "none";
				DutyGrid.checkBoxAll(); 	    
				InvestPlanInputInit(LoadFlag);		
				
				// *****kongyan
				if (document.all('ContNo').value != null && document.all('ContNo').value != "") {
//				var countSQL = "select count(*) from LCPerInvestPlan where contno = '"+document.all('ContNo').value+"'";
				var sqlid82="ProposalInputSql82";
				var mySql57=new SqlClass();
				mySql82.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
				mySql82.setSqlId(sqlid82);// ָ��ʹ�õ�Sql��id
				mySql82.addSubPara(document.all('ContNo').value);// ָ������Ĳ���
				var countSQL =mySql82.getString();	
				var count = easyExecSql(countSQL);
				if (count > 0) {
					countSQL = "";
//					countSQL = "select distinct a.insuaccno, a.insuaccname, '','', p.investrate,p.currency from LCPerInvestPlan p, LMRisktoAcc a where p.riskcode = a.riskcode " 
//					         + " and p.insuaccno = a.insuaccno and p.contno = '"+document.all('ContNo').value+"' and p.riskcode = '"+cRiskCode+"' order by a.insuaccno asc";
					var sqlid83="ProposalInputSql83";
					var mySql83=new SqlClass();
					mySql83.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
					mySql83.setSqlId(sqlid83);// ָ��ʹ�õ�Sql��id
					mySql83.addSubPara(document.all('ContNo').value);// ָ������Ĳ���
					mySql83.addSubPara(cRiskCode);// ָ������Ĳ���
					countSQL =mySql83.getString();	
					turnPage.queryModal(countSQL, InvestPlanRate);							         
				}
			}				
						
				
			}catch(ex){}	
		}
	    else
	    {
			if (tCountPrem != null && parseInt(tCountPrem)> 0)
			{
				fm.inpNeedPremGrid.value = 1;		
				DivPremGrid.style.display = "";		
				initQueryPrem(fm.PolNo.value,fm.RiskCode.value);
			}
			else{
				fm.inpNeedPremGrid.value = 0;
				DivPremGrid.style.display = "none";
			}
		}

    	if(tSubRiskFlag == "S"){
			showDiv(DivLCBnf, "false");
		}
		else
		{
			showDiv(DivLCBnf, "true");
			initQueryBnf(fm.PolNo.value);
		}
		
		if (fm.BonusFlag.value == "0")
		{
			showDiv(divBonusGetMode, "false");
		}
	    else
	    {
	    	showDiv(divBonusGetMode, "true");
	    } 
	    
	    if (fm.AutoPay.value == "0")
	    {
	    	showDiv(divAutoPay, "true");
	    }
	    else
	    {
	    	showDiv(divAutoPay, "false");
	    } 

		return true;
	}
	else
	{
		fm.ProposalNo.value = "";
		fm.PolNo.value = "";
		fm.MainPolNo.value = "";
		return false;
	}
} 
catch(E)
{}
	return true;    
}

// //ΪͶ�ʼƻ����ӵĺ���
function showDetailForCont2(cRiskCode)
{
	// ���ڸı佹��ĺ�����
// var mySql=new SqlClass();
// alert("cRiskCode��"+cRiskCode);
// mySql.setJspName("../../app/ProposalInputInputSql.jsp");
// alert("ifSavedRiskCode(cRiskCode):"+ifSavedRiskCode(cRiskCode));
		if(!ifSavedRiskCode(cRiskCode))
		{
			
		// alert('no save');

			var sqlid71="ProposalInputSql71";
		var mySql71=new SqlClass();
		mySql71.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql71.setSqlId(sqlid71);// ָ��ʹ�õ�Sql��id
         mySql71.addSubPara(cRiskCode);// ָ������Ĳ���
	// alert('no save1112');
	    var tempSQL2 =mySql71.getString();	
	// alert("tempSQL2:"+tempSQL2);
		try{
			initInvestPlanRate();
		}
		catch(e)
		{
		// alert(e);
			}
	// alert('no save1222');
   	 turnPage.queryModal(tempSQL2,InvestPlanRate);		  
   	   // alert('no sav333');
	  			// InvestPlanRate.setFocus(0,5);
	  				// fm.UintLinkValiFlag.focus();
	  	// alert('no sav444');
   	    	// fm.UintLinkValiFlag.select();
   	  // alert("document.all('mPlanOperate').value:"+document.all('mPlanOperate').value);
   	    		if(document.all('mPlanOperate').value =="INSERT"){
   	    			fm.RiskAlias.focus();
   	    			fm.RiskAlias.select(); 
   	    		}
	 	}	
}

function showDetailForCont()
{

	  var tSQL="";
	  var strSQL="";
		var tContNo= fm.ContNo.value;	
		var tResult="";
	
		var sqlid72="ProposalInputSql72";
		var mySql72=new SqlClass();
		mySql72.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql72.setSqlId(sqlid72);// ָ��ʹ�õ�Sql��id
         mySql72.addSubPara(tContNo);// ָ������Ĳ���

	    var tempSQL2 =mySql72.getString();	
		
	    // alert(tSQL);
	    	tResult = easyExecSql(tempSQL2);
	    // alert("$$$tResult:"+tResult)
	 // alert("33333wwwwwwww1");
	    if(tResult != null && tResult.length > 0)
	    {
	  // alert("2222222wwwwwwww1");
	    	document.all("PolNo").value=tResult[0][0];
	    	tPolNo=document.all("PolNo").value;
	    	
// strSQL="select distinct
// a.InsuAccNo,c.INSUACCNAME,a.InvestMinRate,a.InvestMaxRate,a.InvestRate from
// LCPerInvestPlan a,lmriskinsuacc c "
// + "where a.PolNo='"+tPolNo+"' and trim(a.InsuAccNo)=trim(c.InsuAccNo) ";
// ///////////

// alert(1);
						var sqlid73="ProposalInputSql73";
		var mySql73=new SqlClass();
		mySql73.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql73.setSqlId(sqlid73);// ָ��ʹ�õ�Sql��id
         mySql73.addSubPara(tPolNo);// ָ������Ĳ���

	    var tempSQL3=mySql73.getString();	


// /////////////////////end by sunyh on 20070914 add///////////////////////
		  	turnPage.queryModal(tempSQL3,InvestPlanRate);	
		  	     if(LoadFlag=='6'){
		  	     	
		  	     		var sqlid74="ProposalInputSql74";
		var mySql74=new SqlClass();
		mySql74.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql74.setSqlId(sqlid74);// ָ��ʹ�õ�Sql��id
         mySql74.addSubPara(tPolNo);// ָ������Ĳ���

	    var tempSQL4=mySql74.getString();	
		  	     	
		  	     
              	turnPage.queryModal(tempSQL4,QueryInvestPlanRateGrip);	
		  	    }
		  	var tflagResult="";
		  	
		 
        	var sqlid75="ProposalInputSql75";
		var mySql75=new SqlClass();
		mySql75.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql75.setSqlId(sqlid75);// ָ��ʹ�õ�Sql��id
         mySql75.addSubPara(tPolNo);// ָ������Ĳ���

	    var tempSQL5=mySql75.getString();	
        
     // alert(11);
           tflagResult= easyExecSql(tempSQL5,1,0,1); 
           if(tflagResult != null && tflagResult.length > 0){
              fm.UintLinkValiFlag.value=tflagResult[0][0];
              if(fm.UintLinkValiFlag.value==2){
              	fm.UintLinkValiFlagName.value="ǩ������Ч";
              }else if(fm.UintLinkValiFlag.value==4){
              	fm.UintLinkValiFlagName.value="����ԥ�ں���Ч";
              }    	
           }
     // alert(21);
            	
         
		  }else{
// strSQL="select a.InsuAccNo, a.INSUACCNAME from lmriskinsuacc a where
// a.InsuAccNo in (select distinct InsuAccNo from lmriskaccpay where Riskcode in
// (select c.riskcode from lcpol c,LmriskApp b where c.contno='"+tContNo+"' and
// b.riskcode=c.riskcode and b.risktype3='3')) order by a.InsuAccNo ";
// /////////////////////add by sunyh on 20070914///////////////////////
        // alert("11wwwwwwww1");
              var sqlid76="ProposalInputSql76";
							var mySql76=new SqlClass();
							mySql76.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
							mySql76.setSqlId(sqlid76);// ָ��ʹ�õ�Sql��id
              mySql76.addSubPara(tContNo);// ָ������Ĳ���
// alert("11wwwwwwww1222222222222222222");
	          var tempSQL6=mySql76.getString();	
         // alert("tempSQL6:"+tempSQL6);
// /////////////////////end by sunyh on 20070914 add///////////////////////
// alert("11hhhhh222h1");
   	  		turnPage.queryModal(tempSQL6,InvestPlanRate);	
   	  	// alert("11hhhhhh1");
   	  // InvestPlanRate.setFocus(0,5);
   	  		document.all('mPlanOperate').value = "";
   	 }	 
   		         
   	   	
}

/**
 * �ύ�����, ���������ݷ��غ�ִ�еĲ���
 */
function afterSubmitQuery(tPolNo)
{
    queryPolDetail(tPolNo);
  }


// tongmeng 2010-11-16 add
// ��ʼ��Ͷ����
function initULRisk()
{

	var cRiskCode = document.all('RiskCode').value;
	// alert("cRiskCode:"+cRiskCode);
// alert("ifTLrisk(cRiskCode):"+ifTLrisk(cRiskCode));
 if(ifTLrisk(cRiskCode))
	  {  
	  	
	  	try{
		      // DivChooseDuty.style.display = "none";
		       }
			   catch(e)
			   {
			   	}
			   	
			  // alert("222222:");
			   	try{
			    // DutyGrid.checkBoxAll();
			    // tongmeng 2010-11-16 Modify
			    // ֻ�б�����Ĳ�Ʒ�ų�ʼ��Ͷ�ʽ���
			    // alert("11111"+tProposalContNo);
			// alert("@@!#!#@!#!@#:"+ifSavedRiskCode(cRiskCode));
			// alert("3333333:");
			    if(ifSavedRiskCode(cRiskCode))
			    {
			   // alert("4444444:");
			    	divInvestPlanRate.style.display="";
			   // showDetailForCont2(cRiskCode);
			     
			    // InvestPlanInputInit(1);
			    showDetailForCont(cRiskCode);
			   // alert("5555:");
			  	}
			  	else
			  	{
			 // alert("5555:");
			  		divInvestPlanRate.style.display="none";
			  	}
			    
			   }
			   catch(e)
			   {
			   	}
			    finally
			    {			    
					}
					// alert("3111");
								
	  }	
}



function Discount()
{
	var cPolNo = document.all('ProposalNo').value;
	if(mCurOperateFlag=="2")
		cPolNo = document.all('SelPolNo').value;
	
	var sql="select DiscountCode,DiscountType,discounttypename,CalCode,dutycode,dutyname,ifsel,corder"
			+" from ("
			+"select DiscountCode,DiscountType,(select codename from ldcode where codetype='discounttype' and code=DiscountType) discounttypename,(select othersign from ldcode where codetype='discounttype' and code=DiscountType) order1,CalCode,dutycode,(select dutyname from lmduty where dutycode=a.dutycode) dutyname,'1' ifsel,(select max(concat('',corder)) from LCDiscount where PolNo ='"+cPolNo+"' and DiscountCode=a.DiscountCode) corder,(select polapplydate from lccont where contno ='"+document.all('ContNo').value+"') polapplydate,StartDate,EndDate from LMDiscount a where riskcode in ('"+document.all('RiskCode').value+"','000000') and '"+cPolNo+"' is not null and exists(select 1 from LCDiscount where PolNo ='"+cPolNo+"' and DiscountCode=a.DiscountCode) "
			+" union all"
			+" select DiscountCode,DiscountType,(select codename from ldcode where codetype='discounttype' and code=DiscountType) discounttypename,(select othersign from ldcode where codetype='discounttype' and code=DiscountType) order1,CalCode,dutycode,(select dutyname from lmduty where dutycode=a.dutycode) dutyname,'0' ifsel,'' corder,(select polapplydate from lccont where contno ='"+document.all('ContNo').value+"') polapplydate,StartDate,EndDate from LMDiscount a where riskcode in ('"+document.all('RiskCode').value+"','000000') and (('"+cPolNo+"' is not null and not exists(select 1 from LCDiscount where PolNo ='"+cPolNo+"' and DiscountCode=a.DiscountCode)) or '"+cPolNo+"' is null) "
			+" ) aTable"
			+" where polapplydate>= StartDate and (EndDate is null or EndDate>=polapplydate) order by order1";
	if(mCurOperateFlag=="2")
	{
		sql="select DiscountCode,DiscountType,discounttypename,CalCode,dutycode,dutyname,ifsel,corder"
			+" from ("
			+"select DiscountCode,DiscountType,(select codename from ldcode where codetype='discounttype' and code=DiscountType) discounttypename,(select othersign from ldcode where codetype='discounttype' and code=DiscountType) order1,CalCode,dutycode,(select dutyname from lmduty where dutycode=a.dutycode) dutyname,'1' ifsel,(select max(concat('',corder)) from LCDiscount where PolNo ='"+cPolNo+"' and DiscountCode=a.DiscountCode) corder,(select polapplydate from lccont where contno in (select contno from lcpol where polno='"+cPolNo+"')) polapplydate,StartDate,EndDate from LMDiscount a where riskcode in ('"+document.all('RiskCode').value+"','000000') and '"+cPolNo+"' is not null and exists(select 1 from LCDiscount where PolNo ='"+cPolNo+"' and DiscountCode=a.DiscountCode) "
			+" union all"
			+" select DiscountCode,DiscountType,(select codename from ldcode where codetype='discounttype' and code=DiscountType) discounttypename,(select othersign from ldcode where codetype='discounttype' and code=DiscountType) order1,CalCode,dutycode,(select dutyname from lmduty where dutycode=a.dutycode) dutyname,'0' ifsel,'' corder,(select polapplydate from lccont where contno in (select contno from lcpol where polno='"+cPolNo+"')) polapplydate,StartDate,EndDate from LMDiscount a where riskcode in ('"+document.all('RiskCode').value+"','000000') and (('"+cPolNo+"' is not null and not exists(select 1 from LCDiscount where PolNo ='"+cPolNo+"' and DiscountCode=a.DiscountCode)) or '"+cPolNo+"' is null) "
			+" ) aTable"
			+" where polapplydate>= StartDate and (EndDate is null or EndDate>=polapplydate) order by order1";
	}		
	var sqlid84="ProposalInputSql84";
	var mySql84=new SqlClass();
	mySql84.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
	mySql84.setSqlId(sqlid84);// ָ��ʹ�õ�Sql��id
	mySql84.addSubPara(cPolNo);// ָ������Ĳ���
	mySql84.addSubPara(document.all('ContNo').value);// ָ������Ĳ���
	mySql84.addSubPara(document.all('RiskCode').value);// ָ������Ĳ���
	mySql84.addSubPara(cPolNo);// ָ������Ĳ���
	mySql84.addSubPara(cPolNo);// ָ������Ĳ���
	mySql84.addSubPara(document.all('ContNo').value);// ָ������Ĳ���
	mySql84.addSubPara(document.all('RiskCode').value);// ָ������Ĳ���
	mySql84.addSubPara(cPolNo);// ָ������Ĳ���
	mySql84.addSubPara(cPolNo);// ָ������Ĳ���
	mySql84.addSubPara(cPolNo);// ָ������Ĳ���
   var sql =mySql84.getString();
   if(mCurOperateFlag=="2"){
   var sqlid85="ProposalInputSql85";
	var mySql85=new SqlClass();
	mySql85.setResourceName("app.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
	mySql85.setSqlId(sqlid85);// ָ��ʹ�õ�Sql��id
	mySql85.addSubPara(cPolNo);// ָ������Ĳ���
	mySql85.addSubPara(cPolNo);// ָ������Ĳ���
	mySql85.addSubPara(document.all('RiskCode').value);// ָ������Ĳ���
	mySql85.addSubPara(cPolNo);// ָ������Ĳ���
	mySql85.addSubPara(cPolNo);// ָ������Ĳ���
	mySql85.addSubPara(cPolNo);// ָ������Ĳ���
	mySql85.addSubPara(document.all('RiskCode').value);// ָ������Ĳ���
	mySql85.addSubPara(cPolNo);// ָ������Ĳ���
	mySql85.addSubPara(cPolNo);// ָ������Ĳ���
	mySql85.addSubPara(cPolNo);// ָ������Ĳ���
	sql =mySql85.getString();
   }
    turnPageDiscount.queryModal(sql,DiscountGrid);
  	var rowNum=DiscountGrid. mulLineCount ;  // �õ���¼������
    for(var i=0;i<rowNum;i++)
    {
    	if(DiscountGrid.getRowColData(i,7)=="1")
    	{
    		DiscountGrid.checkBoxSel(i+1);
    	}
    // �ر��ۿ���Ҫ¼���ۿ��㷨���˴�ֱ����mulline��ʼ��ʱ����Ϊ�ɱ༭���պ���Ҫ�Ľ�
    }
    
}