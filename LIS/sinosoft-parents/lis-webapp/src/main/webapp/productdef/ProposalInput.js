/**
 * Date                Developer             ReleaseDate  MantisNo    Description
 * ==========================================================
 * 2015-01-30   Goeast Hui            2015-03-13                          Whole Life Yearly Coupon Plan
 */

//Modify by niuzj,2006-08-23,Ӣ����Ҫ��¼����������Ϣʱ����һ�����Ա��ֶ�
//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPageMultRisk = new turnPageClass(); 
var turnPageMultAddRisk = new turnPageClass(); 
var turnPageDiscount = new turnPageClass(); 
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
var parameter1="";
var parameter2="";
var mRollSpeedOperator = 0;
var mRollSpeedSelf = 0;
var mRollSpeedBase = 0;
var mRiskName = "";  //add by Marlon 20060713 ������������
var mCValiDate = parent.VD.gVSwitch.getVar('CValiDate');
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

//20110919,cmj,add
var ABCValiDate;
var ABPayIntv;
var ABMainPolNo;
var IsCValidate=null;
var ckRet = true;
var lazyTime = 500;

/*********************************************************************
 *  ѡ�����ֺ�Ķ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function intorisk()
{
    if(fm.all('RiskCode').value=="")
     {
        myAlert(""+"����ѡ������!"+"");
        return;
     }

    if( verifyInput() == false ) return false;

    try {
        if(fm.all('RiskCode').value!=null){
            getRiskInput(fm.all('RiskCode').value, "1");//LoadFlag��ҳ���ʼ����ʱ������
			
          if(fm.RiskCode.value=="00144000"){
              InsuredGrid.setRowColData(0, 6, "1");
          }
        }else{
            getRiskInput(parameter2.value, "1");//LoadFlag��ҳ���ʼ����ʱ������
        }
    }catch( ex ) {}
}

function initSubRisk()
{   
	//2011-10-21 add by zhengyj 
    if((LoadFlag==1&&fm.PayIntv.value=="" )|| (LoadFlag==3&&fm.PayIntv.value=="" ))
    {
    	var NBContNo=mSwitch.getVar("ContNo");
    	var mainPolTest = "SELECT 1 FROM lccont a, lcpol b WHERE b.riskcode='" + fm.all('RiskCode').value + "' AND a.contno=b.contno AND b.polno=b.mainpolno and a.contno='" + NBContNo + "'";
    	var mainPolResult=easyExecSql(mainPolTest);
    	if (mainPolResult != 1)  {
        	// 2011-11-23 ���������ֽ���Ҫ����������һ�£���Ҫ��ѯ������Ϣ Modify by kongyan for BOCGL
        	var NBsql="select a.payintv,a.currency,b.staffflag,a.insuyear,a.payendyear,a.payendyearflag,a.amnt,a.enddate,a.insuyearflag,a.insuredbirthday,a.cvalidate,a.payenddate  from lcpol a,lccont b where a.mainpolno=a.polno and a.contno='"+NBContNo+"' and b.contno='"+NBContNo+"' and exists (select 'X' from lmriskapp where subriskflag = 'M' and riskcode = a.riskcode)";
        	var NBResult=easyExecSql(NBsql);
        	if(NBResult!=null)
        	{
        	  try{ 
        		  var NBRiskcodeResult1 = "";
        		  var NBInsuyear = "";
        		  try{
        		  //�ɷѷ�ʽ
        		  fm.PayIntv.value=NBResult[0][0];
        		  fm.PayIntv.readOnly=true;
        		  fm.PayIntv.ondblclick=function(){};
        		  fm.PayIntv.onkeyup=function(){};
//        		  showOneCodeName('payintv', 'PayIntv', 'PayIntvName');
        		  }catch(ex){}
        		  try{
        		  //����
        		  fm.CurrencyCode.value=NBResult[0][1];
        		  fm.CurrencyCode.readOnly=true;
        		  fm.CurrencyCode.ondblclick=function(){};
        		  fm.CurrencyCode.onkeyup=function(){};
        		  showOneCodeName('currency', 'CurrencyCode', 'CurrencyName');
        		  }catch(ex){}
        		  try{
        		  	//Ա����
        		  	fm.StaffFlag.value=NBResult[0][2];
        		  	fm.StaffFlag.readOnly=true;
        		  	fm.StaffFlag.ondblclick=function(){};
        		  	fm.StaffFlag.onkeyup=function(){};
//        		  	showOneCodeName('StaffFlag-IBW30*0&0', 'CurrencyCode', 'CurrencyName');
        		  }catch(ex){}
        		  try{
        		  //�ɷ��ڼ� 
        			//fm.PayEndYear.value=NBResult[0][4];
        		    fm.PayEndYear.readOnly=true;
        		    fm.PayEndYear.ondblclick=function(){};
        		    fm.PayEndYear.onkeyup=function(){};
        		  }catch(ex){}
        		  try{
        		    //�ɷ��ڼ䵥λ
            	    //fm.PayEndYearFlag.value=NBResult[0][5];
        		    fm.PayEndYearFlag.readOnly=true;
        		    fm.PayEndYearFlag.ondblclick=function(){};
        		    fm.PayEndYearFlag.onkeyup=function(){};
        		    //showOneCodeName('insuyearflag', 'PayEndYearFlag', 'PayEndYearFlagName');
        		  }catch(ex){}
        		  try{
        		  //�����ڼ�
        		  fm.InsuYear.value="";
        		  fm.InsuYear.readOnly=true;
        		  fm.InsuYear.ondblclick=function(){};
        		  fm.InsuYear.onkeyup=function(){};     		  
        		  }catch(ex){}
        		  try{
        		  //�����ڼ䵥λ
        		  fm.InsuYearFlag.value="Y";
        		  fm.InsuYearFlag.readOnly=true;
        		  fm.InsuYearFlag.ondblclick=function(){};
        		  fm.InsuYearFlag.onkeyup=function(){};
        		  showOneCodeName('insuyearflag', 'InsuYearFlag', 'InsuYearFlagName'); 
        		  }catch(ex){alert(ex);}
        		  var NBLMrisksortsql="select risksortvalue from lmrisksort where risksorttype='STMFlag' and riskcode ='"+fm.BOCRiskCode.value+"'";
        		  var NBLMrisksortResult=easyExecSql(NBLMrisksortsql);
        		  if(NBLMrisksortResult!=null || NBLMrisksortResult !=""){
        		  	var NBRisksortvalue=NBLMrisksortResult[0][0];
        		  //alert("NBRisksortvalue="+NBRisksortvalue);
        		  	//�������Ʒ�ı���������һ��
        		  	if(NBRisksortvalue=="1"){
        		  		fm.Amnt.value=NBResult[0][6];
        		  		fm.Amnt.readOnly=true;
        		  		fm.Amnt.ondblclick=function(){};
        		  		fm.Amnt.onkeyup=function(){};
        		  	}
        		  }
        		  // ����enddate
        		  var NBEnddate=NBResult[0][7];
        		  //var NBEnddateDate=new Date(NBEnddate.replace(/-/g,"/"));//ת�������ڸ�ʽ
        		  // ������������
        		  var NBBirthday=NBResult[0][9];
        		  //var NBBirthdayDate=new Date(NBBirthday.replace(/-/g,"/"));//ת�������ڸ�ʽ
        		  // Ͷ��������
        		  var ANBBirthdayResult=easyExecSql("select p.appntbirthday from lcappnt p, lccont c where p.contno=c.contno and p.appntno=c.appntno and c.contno='"+NBContNo+"'");
         		  var ANBBirthday=ANBBirthdayResult[0][0];
         		  //var ANBBirthdayDate=new Date(ANBBirthday.replace(/-/g,"/"));//ת�������ڸ�ʽ
        		  // ��Ч����
        		  var NBCValidate=NBResult[0][10];
        		  //var NBCValidateDate=new Date(NBCValidate.replace(/-/g,"/"));
        		  var NBPayEnddate=NBResult[0][11];
        		 
        		  // 2011-11-23 �жϸø����U���U���gӋ�㷽ʽ Add by kongyan for BOCGL    		
                  //0-���ܱ���**�q �� �c���ٵĻ�������Ӌ������������ͬ���������ߞ�ʣ�
                  //1-���ܱ���**�q �� �c���ٵĻ�������Ӌ���U�M������ͬ���������ߞ�ʣ�
                  //2-��������**�q �� ���ܱ���**�q �� �c��������Ӌ������������ͬ(�������ߞ��)��
                  //3-��������**�q �� ���ܱ���**�q �� �c��������Ӌ���U�M������ͬ(�������ߞ��)��
        		  var InsuAge;
        		  var AppInsuAge;
        		  var checkSql = "select risksortvalue from lmrisksort where riskcode='"+fm.BOCRiskCode.value+"' and risksorttype='AIMFlag' ";
        		  var checkResult=easyExecSql(checkSql);
        		  //alert(checkResult[0][0]);
        		  
        		  if((checkResult==null || checkResult =="") || checkResult[0][0]=="0" || checkResult[0][0]=="1"){
        		    var InsuAgeResult = easyExecSql("select risksortvalue from lmrisksort where riskcode='"+fm.BOCRiskCode.value+"' and risksorttype='InsuAge'  ");
        		    InsuAge = InsuAgeResult[0][0];
        		    
        		  }     		  
//        		  else if(checkResult[0][0]!="4"){
        		  else if(checkResult[0][0]!="4" && checkResult[0][0]!="5"){
        		  	var InsuAgeResult = easyExecSql("select risksortvalue from lmrisksort where riskcode='"+fm.BOCRiskCode.value+"' and risksorttype='InsuAge'  ");
        		    InsuAge = InsuAgeResult[0][0];
        		  	var AppInsuAgeResult = easyExecSql("select risksortvalue from lmrisksort where riskcode='"+fm.BOCRiskCode.value+"' and risksorttype='AInsuAge'  ");
        		    AppInsuAge = AppInsuAgeResult[0][0];

        		  }    		    		
        		  	
        		  // �����ڼ䵥λ
         		  var NBInsuyearflag="Y";
         		  	
        		  // �����ڼ�
        		  var NBRiskcodesql1="";
        		  // �����ڼ�
        		  var NBRiskcodesql2="";
        		  if((checkResult==null || checkResult =="") || checkResult[0][0]=="0"){
        		  	//0-���ܱ���**�q �� �c���ٵĻ�������Ӌ������������ͬ���������ߞ�ʣ� 		  	
        		  	NBRiskcodesql1 = "select months_between(least(add_months(to_date('"+NBCValidate+"','yyyy-mm-dd'),("+InsuAge+"-floor(months_between(to_date('"+NBCValidate+"','yyyy-mm-dd'), to_date('"+NBBirthday+"','yyyy-mm-dd'))/12))*12),to_date('"+NBEnddate+"','yyyy-mm-dd')),to_date('"+NBCValidate+"','yyyy-mm-dd'))/12 from dual ";

           		  NBRiskcodeResult1=easyExecSql(NBRiskcodesql1);
           		  NBInsuyear=NBRiskcodeResult1[0][0];  
           		  if (NBInsuyear<0) {
           		  	NBInsuyear=0;
           		  }   		     		
           		  fm.InsuYear.value = NBInsuyear;
           		  fm.InsuYearName.value = fm.InsuYear.value;
           		  fm.PayEndYear.value=NBInsuyear;
    			  fm.PayEndYearName.value=fm.PayEndYear.value;
        		  fm.PayEndYearFlag.value=NBInsuyearflag;
        		  showOneCodeName('insuyearflag', 'PayEndYearFlag', 'PayEndYearFlagName');
        		  } 
        		  else if(checkResult[0][0]=="1") {
        		  	//1-���ܱ���**�q �� �c���ٵĻ�������Ӌ���U�M������ͬ���������ߞ�ʣ�    		  	
        		  	NBRiskcodesql1 = "select months_between(least(add_months(to_date('"+NBCValidate+"','yyyy-mm-dd'),("+InsuAge+"-floor(months_between(to_date('"+NBCValidate+"','yyyy-mm-dd'), to_date('"+NBBirthday+"','yyyy-mm-dd'))/12))*12),to_date('"+NBPayEnddate+"','yyyy-mm-dd')),to_date('"+NBCValidate+"','yyyy-mm-dd'))/12 from dual ";

           		  NBRiskcodeResult1=easyExecSql(NBRiskcodesql1);
           		  NBInsuyear=NBRiskcodeResult1[0][0];     
           		  if (NBInsuyear<0) {
           		  	NBInsuyear=0;
           		  }
           		  fm.InsuYear.value = NBInsuyear;
    			      fm.PayEndYear.value=NBInsuyear;
    			      fm.InsuYearName.value = fm.InsuYear.value;
    			      fm.PayEndYearName.value=fm.PayEndYear.value;
        		    fm.PayEndYearFlag.value=NBInsuyearflag;
        		    showOneCodeName('insuyearflag', 'PayEndYearFlag', 'PayEndYearFlagName');
        		  }
        		  else if(checkResult[0][0]=="2") {
        		  	//2-��������**�q �� ���ܱ���**�q �� �c��������Ӌ������������ͬ(�������ߞ��)��
        		  	NBRiskcodesql1 = "select months_between(least(least(add_months(to_date('"+NBCValidate+"', 'yyyy-mm-dd'), ("+InsuAge+" - floor(months_between(to_date('"+NBCValidate+"', 'yyyy-mm-dd'), to_date('"+NBBirthday+"', 'yyyy-mm-dd')) / 12)) * 12), add_months(to_date('"+NBCValidate+"', 'yyyy-mm-dd'), ("+AppInsuAge+" - floor(months_between(to_date('"+NBCValidate+"', 'yyyy-mm-dd'), to_date('"+ANBBirthday+"', 'yyyy-mm-dd')) / 12)) * 12)), to_date('"+NBEnddate+"','yyyy-mm-dd')),to_date('"+NBCValidate+"', 'yyyy-mm-dd'))/12 from dual ";    		    

           		  NBRiskcodeResult1=easyExecSql(NBRiskcodesql1);
           		  NBInsuyear=NBRiskcodeResult1[0][0];   
           		  if (NBInsuyear<0) {
           		  	NBInsuyear=0;
           		  }  		     		
           		  fm.InsuYear.value = NBInsuyear;
    			      fm.PayEndYear.value=NBInsuyear;
    			      fm.InsuYearName.value = fm.InsuYear.value;
    			      fm.PayEndYearName.value=fm.PayEndYear.value;
        		    fm.PayEndYearFlag.value=NBInsuyearflag;
        		    showOneCodeName('insuyearflag', 'PayEndYearFlag', 'PayEndYearFlagName');
        		  } 
        		  else if(checkResult[0][0]=="3") {
        		    //3-��������**�q �� ���ܱ���**�q �� �c��������Ӌ���U�M������ͬ(�������ߞ��)��
        		    NBRiskcodesql1 = "select months_between(least(least(add_months(to_date('"+NBCValidate+"', 'yyyy-mm-dd'), ("+InsuAge+" - floor(months_between(to_date('"+NBCValidate+"', 'yyyy-mm-dd'), to_date('"+NBBirthday+"', 'yyyy-mm-dd')) / 12)) * 12), add_months(to_date('"+NBCValidate+"', 'yyyy-mm-dd'), ("+AppInsuAge+" - floor(months_between(to_date('"+NBCValidate+"', 'yyyy-mm-dd'), to_date('"+ANBBirthday+"', 'yyyy-mm-dd')) / 12)) * 12)), to_date('"+NBPayEnddate+"','yyyy-mm-dd')),to_date('"+NBCValidate+"', 'yyyy-mm-dd'))/12 from dual ";    		    

           		  NBRiskcodeResult1=easyExecSql(NBRiskcodesql1);
           		  NBInsuyear=NBRiskcodeResult1[0][0];     
           		  if (NBInsuyear<0) {
           		  	NBInsuyear=0;
           		  }		     		
           		  fm.InsuYear.value = NBInsuyear;
    			      fm.PayEndYear.value=NBInsuyear;
    			      fm.InsuYearName.value = fm.InsuYear.value;
    			      fm.PayEndYearName.value=fm.PayEndYear.value;
        		    fm.PayEndYearFlag.value=NBInsuyearflag;
        		    showOneCodeName('insuyearflag', 'PayEndYearFlag', 'PayEndYearFlagName');
        		  }
        		  else if(checkResult[0][0]=="4") { 
        		  	//4-ȡ����������
        		  	NBRiskcodesql1 = "select min(paramscode) from lmriskparamsdef  where paramstype='insuyear' and riskcode='"+fm.BOCRiskCode.value+"'";
        		  	NBRiskcodesql2 = "select paramscode from lmriskparamsdef  where paramstype='payendyear' and riskcode='"+fm.BOCRiskCode.value+"'";
        		  	NBRiskcodesql3 = "select paramscode from lmriskparamsdef  where paramstype='payendyearflag' and riskcode='"+fm.BOCRiskCode.value+"'";
        		  	
        		  	NBRiskcodeResult1=easyExecSql(NBRiskcodesql1);
           		  NBInsuyear=NBRiskcodeResult1[0][0];     
           		  if (NBInsuyear<0) {
           		  	NBInsuyear=0;
           		  }		     		
           		  fm.InsuYear.value = NBInsuyear;
           		  fm.InsuYearName.value = fm.InsuYear.value;
           		  
        	  	  var NBRiskcodeResult2=easyExecSql(NBRiskcodesql2);
        	  	  var NBRiskcodeResult3=easyExecSql(NBRiskcodesql3);
         	      fm.PayEndYear.value = NBRiskcodeResult2[0][0]; 
         	      fm.PayEndYearName.value = fm.PayEndYear.value; 
                  fm.PayEndYearFlag.value = NBRiskcodeResult3[0][0];
                  showOneCodeName('insuyearflag', 'PayEndYearFlag', 'PayEndYearFlagName');
        		  }
          		  else if(checkResult[0][0]=="5") { 
                  	//5
             		  	NBRiskcodesql1 = "select min(paramscode) from lmriskparamsdef  where paramstype='insuyear' and riskcode='"+fm.BOCRiskCode.value+"'";
            		  	NBRiskcodeResult1=easyExecSql(NBRiskcodesql1);
                		NBRiskcodesql2 = "select min(paramscode) from lmriskparamsdef  where paramstype='insuyearflag' and riskcode='"+fm.BOCRiskCode.value+"'";
             		  	NBRiskcodeResult2=easyExecSql(NBRiskcodesql2);

            		  	NBInsuyear=NBRiskcodeResult1[0][0];     
                 		if (NBInsuyear<0) {
                 		  	NBInsuyear=0;
                 		}		     		
                 		fm.InsuYear.value = NBInsuyear;
                 		fm.InsuYearName.value = fm.InsuYear.value;
                 		fm.InsuYearFlag.value = NBRiskcodeResult2[0][0];

                 		fm.PayEndYear.value=NBResult[0][4];
             	        fm.PayEndYearName.value = fm.PayEndYear.value; 
                	    fm.PayEndYearFlag.value=NBResult[0][5];
                      showOneCodeName('insuyearflag', 'PayEndYearFlag', 'PayEndYearFlagName');
             		  }
        		  //alert(NBRiskcodesql1);
         		  //alert(fm.InsuYear.value);
        		}catch(e){}
        	}
    	}
    	
    }
    //start 2012-2-21 ��ȫ���������� limj
    if(LoadFlag==8&&fm.PayIntv.value=="")
    {
      var NBContNo=mSwitch.getVar("ContNo");
      
      CalCValiDate();
      
      //ABCValiDate
      
      var sqlx="select nvl(sum(ceil(months_between(date'"+ABCValiDate+"',cvalidate)/12)),0) from lcpol where polno=mainpolno and contno='"+NBContNo+"'";
      var ABYears=parseInt(easyExecSql(sqlx));
      
    	
    	// 2011-11-23 ���������ֽ���Ҫ����������һ�£���Ҫ��ѯ������Ϣ Modify by kongyan for BOCGL
    	var NBsql="select a.payintv,a.currency,b.staffflag,a.insuyear,a.payendyear,a.payendyearflag,a.amnt,a.enddate,a.insuyearflag,a.insuredbirthday,a.cvalidate,a.payenddate  from lcpol a,lccont b, lmriskrela r where a.mainpolno=a.polno and a.contno='"+NBContNo+"' and b.contno='"+NBContNo+"'  and r.riskcode = a.riskcode and r.relariskcode='"+fm.BOCRiskCode.value+"' and exists (select 'X' from lmriskapp where subriskflag = 'M' and riskcode = a.riskcode)";
    	var NBResult=easyExecSql(NBsql);
    	
    	if(NBResult!=null)
    	{
    	  try{ 
    		  var NBRiskcodeResult1 = "";
    		  var NBInsuyear = "";
    		  try{
    		  //�ɷѷ�ʽ
    		  fm.PayIntv.value=NBResult[0][0];
    		  fm.PayIntv.readOnly=true;
    		  fm.PayIntv.ondblclick=function(){};
    		  fm.PayIntv.onkeyup=function(){};
    		  //showOneCodeName('payintv', 'PayIntv', 'PayIntvName');
    		  }catch(ex){}
    		  try{
    		  //����
    		  fm.CurrencyCode.value=NBResult[0][1];
    		  fm.CurrencyCode.readOnly=true;
    		  fm.CurrencyCode.ondblclick=function(){};
    		  fm.CurrencyCode.onkeyup=function(){};
    		  //showOneCodeName('currency', 'CurrencyCode', 'CurrencyName');
    		  
    		  }catch(ex){}
    		  try{
    		  	//Ա����
    		  	fm.StaffFlag.value=NBResult[0][2];
    		  	fm.StaffFlag.readOnly=true;
    		  	fm.StaffFlag.ondblclick=function(){};
    		  	fm.StaffFlag.onkeyup=function(){};
    		  	//showOneCodeName('staffflag', 'StaffFlag', 'StaffFlagName');
    		  }catch(ex){}
    		  try{
    		  //�ɷ��ڼ� 
//    			  fm.PayEndYear.value=NBResult[0][4];
    		  fm.PayEndYear.value="";
    		  fm.PayEndYear.readOnly=true;
    		  fm.PayEndYear.ondblclick=function(){};
    		  fm.PayEndYear.onkeyup=function(){};
    		  //showOneCodeName('payendyear', 'PayEndYear', 'PayEndYearName');
    		  }catch(ex){}
    		  try{
    		  //�ɷ��ڼ䵥λ
//        		  fm.PayEndYearFlag.value=NBResult[0][5];
        	  fm.PayEndYearFlag.value="";
    		  fm.PayEndYearFlag.readOnly=true;
    		  fm.PayEndYearFlag.ondblclick=function(){};
    		  fm.PayEndYearFlag.onkeyup=function(){};
    		  showOneCodeName('payendyearflag', 'PayEndYearFlag', 'PayEndYearFlagName');
    		  }catch(ex){}
    		  try{
    		  //�����ڼ�
    		  fm.InsuYear.value="";
    		  fm.InsuYear.readOnly=true;
    		  fm.InsuYear.ondblclick=function(){};
    		  fm.InsuYear.onkeyup=function(){};     		  
    		  }catch(ex){}
    		  try{
    		  //�����ڼ䵥λ
    		  fm.InsuYearFlag.value="Y";
    		  fm.InsuYearFlag.readOnly=true;
    		  fm.InsuYearFlag.ondblclick=function(){};
    		  fm.InsuYearFlag.onkeyup=function(){};
    		  //showOneCodeName('insuyearflag', 'InsuYearFlag', 'InsuYearFlagName'); 
    		  }catch(ex){alert(ex);}
    		  var NBLMrisksortsql="select risksortvalue from lmrisksort where risksorttype='STMFlag' and riskcode ='"+fm.BOCRiskCode.value+"'";
    		  var NBLMrisksortResult=easyExecSql(NBLMrisksortsql);
    		  if(NBLMrisksortResult!=null || NBLMrisksortResult !=""){
    		  	var NBRisksortvalue=NBLMrisksortResult[0][0];
    		  //alert("NBRisksortvalue="+NBRisksortvalue);
    		  	//�������Ʒ�ı���������һ��
    		  	if(NBRisksortvalue=="1"){
    		  		fm.Amnt.value=NBResult[0][6];
    		  		fm.Amnt.readOnly=true;
    		  		fm.Amnt.ondblclick=function(){};
    		  		fm.Amnt.onkeyup=function(){};
    		  		//showOneCodeName('amnt', 'Amnt', 'AmntName');
    		  	}
    		  }
    		  // ����enddate
    		  var NBEnddate=NBResult[0][7];
    		  //var NBEnddateDate=new Date(NBEnddate.replace(/-/g,"/"));//ת�������ڸ�ʽ
   
    		  // ������������
    		  var NBBirthday=NBResult[0][9];
    		  //var NBBirthdayDate=new Date(NBBirthday.replace(/-/g,"/"));//ת�������ڸ�ʽ
 
    		  // Ͷ��������
    		  var ANBBirthdayResult=easyExecSql("select p.appntbirthday from lcappnt p, lccont c where p.contno=c.contno and p.appntno=c.appntno and c.contno='"+NBContNo+"'");
     		  var ANBBirthday=ANBBirthdayResult[0][0];
     		  //var ANBBirthdayDate=new Date(ANBBirthday.replace(/-/g,"/"));//ת�������ڸ�ʽ
  
    		  // ��Ч����
    		  var NBCValidate=NBResult[0][10];
    		  //var NBCValidateDate=new Date(NBCValidate.replace(/-/g,"/"));
    		 
    		  var NBPayEnddate=NBResult[0][11];
    		 
    		  // 2011-11-23 �жϸø����U���U���gӋ�㷽ʽ Add by kongyan for BOCGL    		
          //0-���ܱ���**�q �� �c���ٵĻ�������Ӌ������������ͬ���������ߞ�ʣ�
          //1-���ܱ���**�q �� �c���ٵĻ�������Ӌ���U�M������ͬ���������ߞ�ʣ�
          //2-��������**�q �� ���ܱ���**�q �� �c��������Ӌ������������ͬ(�������ߞ��)��
          //3-��������**�q �� ���ܱ���**�q �� �c��������Ӌ���U�M������ͬ(�������ߞ��)��
    		
    		  var InsuAge;
    		  var AppInsuAge;
    		  var checkSql = "select risksortvalue from lmrisksort where riskcode='"+fm.BOCRiskCode.value+"' and risksorttype='AIMFlag' ";
    		  var checkResult=easyExecSql(checkSql);
    		  //alert(checkResult[0][0]);
    		  
    		  if((checkResult==null || checkResult =="") || checkResult[0][0]=="0" || checkResult[0][0]=="1"){
    		    var InsuAgeResult = easyExecSql("select risksortvalue from lmrisksort where riskcode='"+fm.BOCRiskCode.value+"' and risksorttype='InsuAge'  ");
    		    InsuAge = InsuAgeResult[0][0];
    		    
    		  }
//    		  else if(checkResult[0][0]!="4"){
    		  else if(checkResult[0][0]!="4" && checkResult[0][0]!="5"){
    		  	var InsuAgeResult = easyExecSql("select risksortvalue from lmrisksort where riskcode='"+fm.BOCRiskCode.value+"' and risksorttype='InsuAge'  ");
    		    InsuAge = InsuAgeResult[0][0];
    		  	var AppInsuAgeResult = easyExecSql("select risksortvalue from lmrisksort where riskcode='"+fm.BOCRiskCode.value+"' and risksorttype='AInsuAge'  ");
    		    AppInsuAge = AppInsuAgeResult[0][0];

    		  }
    		  // �����ڼ䵥λ
     		  var NBInsuyearflag="Y";
    		  // �����ڼ�
    		  var NBRiskcodesql1="";
    		  // �����ڼ�
    		  var NBRiskcodesql2="";
    		  if((checkResult==null || checkResult =="") || checkResult[0][0]=="0"){
    		  	//0-���ܱ���**�q �� �c���ٵĻ�������Ӌ������������ͬ���������ߞ�ʣ� 		  	
    		  	NBRiskcodesql1 = "select months_between(least(add_months(to_date('"+NBCValidate+"','yyyy-mm-dd'),("+InsuAge+"-floor(months_between(to_date('"+NBCValidate+"','yyyy-mm-dd'), to_date('"+NBBirthday+"','yyyy-mm-dd'))/12))*12),to_date('"+NBEnddate+"','yyyy-mm-dd')),to_date('"+NBCValidate+"','yyyy-mm-dd'))/12 from dual ";

       		  NBRiskcodeResult1=easyExecSql(NBRiskcodesql1);
       		  NBInsuyear=NBRiskcodeResult1[0][0]-ABYears;  
       		  if (NBInsuyear<0) {
       		  	NBInsuyear=0;
       		  }   		     		
       		  fm.InsuYear.value = NBInsuyear;
			      fm.PayEndYear.value=NBInsuyear;
    		    fm.PayEndYearFlag.value=NBInsuyearflag;
    		  } 
    		  else if(checkResult[0][0]=="1") {
    		  	//1-���ܱ���**�q �� �c���ٵĻ�������Ӌ���U�M������ͬ���������ߞ�ʣ�    		  	
    		  	NBRiskcodesql1 = "select months_between(least(add_months(to_date('"+NBCValidate+"','yyyy-mm-dd'),("+InsuAge+"-floor(months_between(to_date('"+NBCValidate+"','yyyy-mm-dd'), to_date('"+NBBirthday+"','yyyy-mm-dd'))/12))*12),to_date('"+NBPayEnddate+"','yyyy-mm-dd')),to_date('"+NBCValidate+"','yyyy-mm-dd'))/12 from dual ";

       		  NBRiskcodeResult1=easyExecSql(NBRiskcodesql1);
       		  NBInsuyear=NBRiskcodeResult1[0][0]-ABYears;     
       		  if (NBInsuyear<0) {
       		  	NBInsuyear=0;
       		  }		     		
       		  fm.InsuYear.value = NBInsuyear;
			      fm.PayEndYear.value=NBInsuyear;
    		    fm.PayEndYearFlag.value=NBInsuyearflag;
    		  }
    		  else if(checkResult[0][0]=="2") {
    		  	//2-��������**�q �� ���ܱ���**�q �� �c��������Ӌ������������ͬ(�������ߞ��)��
    		  	NBRiskcodesql1 = "select months_between(least(least(add_months(to_date('"+NBCValidate+"', 'yyyy-mm-dd'), ("+InsuAge+" - floor(months_between(to_date('"+NBCValidate+"', 'yyyy-mm-dd'), to_date('"+NBBirthday+"', 'yyyy-mm-dd')) / 12)) * 12), add_months(to_date('"+NBCValidate+"', 'yyyy-mm-dd'), ("+AppInsuAge+" - floor(months_between(to_date('"+NBCValidate+"', 'yyyy-mm-dd'), to_date('"+ANBBirthday+"', 'yyyy-mm-dd')) / 12)) * 12)), to_date('"+NBEnddate+"','yyyy-mm-dd')),to_date('"+NBCValidate+"', 'yyyy-mm-dd'))/12 from dual ";    		    
       		  NBRiskcodeResult1=easyExecSql(NBRiskcodesql1);
       		  NBInsuyear=NBRiskcodeResult1[0][0]-ABYears;   
       		  if (NBInsuyear<0) {
       		  	NBInsuyear=0;
       		  }  		     		
       		  fm.InsuYear.value = NBInsuyear;
			      fm.PayEndYear.value=NBInsuyear;
    		    fm.PayEndYearFlag.value=NBInsuyearflag;
    		  } 
    		  else if(checkResult[0][0]=="3") {
    		    //3-��������**�q �� ���ܱ���**�q �� �c��������Ӌ���U�M������ͬ(�������ߞ��)��
    		    NBRiskcodesql1 = "select months_between(least(least(add_months(to_date('"+NBCValidate+"', 'yyyy-mm-dd'), ("+InsuAge+" - floor(months_between(to_date('"+NBCValidate+"', 'yyyy-mm-dd'), to_date('"+NBBirthday+"', 'yyyy-mm-dd')) / 12)) * 12), add_months(to_date('"+NBCValidate+"', 'yyyy-mm-dd'), ("+AppInsuAge+" - floor(months_between(to_date('"+NBCValidate+"', 'yyyy-mm-dd'), to_date('"+ANBBirthday+"', 'yyyy-mm-dd')) / 12)) * 12)), to_date('"+NBPayEnddate+"','yyyy-mm-dd')),to_date('"+NBCValidate+"', 'yyyy-mm-dd'))/12 from dual ";    		    

       		  NBRiskcodeResult1=easyExecSql(NBRiskcodesql1);
       		  NBInsuyear=NBRiskcodeResult1[0][0]-ABYears;     
       		  if (NBInsuyear<0) {
       		  	NBInsuyear=0;
       		  }		     		
       		  fm.InsuYear.value = NBInsuyear;
			      fm.PayEndYear.value=NBInsuyear;
    		    fm.PayEndYearFlag.value=NBInsuyearflag;
    		  }   
    		  else if(checkResult[0][0]=="4") { 
    		  	//4-ȡ����������
    		  	NBInsuyear=NBInsuyear;
    		  	NBRiskcodesql1 = "select min(paramscode) from lmriskparamsdef  where paramstype='insuyear' and riskcode='"+fm.BOCRiskCode.value+"'";
    		  	NBRiskcodesql2 = "select paramscode from lmriskparamsdef  where paramstype='payendyear' and riskcode='"+fm.BOCRiskCode.value+"'";
    		  	NBRiskcodesql3 = "select paramscode from lmriskparamsdef  where paramstype='payendyearflag' and riskcode='"+fm.BOCRiskCode.value+"'";
    		  	
    		  	NBRiskcodeResult1=easyExecSql(NBRiskcodesql1);
       		  NBInsuyear=NBRiskcodeResult1[0][0];     
       		  if (NBInsuyear<0) {
       		  	NBInsuyear=0;
       		  }		     		
       		  fm.InsuYear.value = NBInsuyear;
    		  var NBRiskcodeResult2=easyExecSql(NBRiskcodesql2);
    		  var NBRiskcodeResult3=easyExecSql(NBRiskcodesql3);
     		  fm.PayEndYear.value = NBRiskcodeResult2[0][0]; 
              fm.PayEndYearFlag.value = NBRiskcodeResult3[0][0];
    		  }
    		  else if(checkResult[0][0]=="5") { 
                	//5
            		NBRiskcodesql1 = "select min(paramscode) from lmriskparamsdef  where paramstype='insuyear' and riskcode='"+fm.BOCRiskCode.value+"'";
         		  	NBRiskcodeResult1=easyExecSql(NBRiskcodesql1);
            		NBRiskcodesql2 = "select min(paramscode) from lmriskparamsdef  where paramstype='insuyearflag' and riskcode='"+fm.BOCRiskCode.value+"'";
         		  	NBRiskcodeResult2=easyExecSql(NBRiskcodesql2);

      		  	NBInsuyear=NBRiskcodeResult1[0][0];     
             		if (NBInsuyear<0) {
             		  	NBInsuyear=0;
             		}		     		
             		fm.InsuYear.value = NBInsuyear;
             		fm.InsuYearFlag.value = NBRiskcodeResult2[0][0];

             		fm.PayEndYear.value=NBResult[0][4];
                	fm.PayEndYearFlag.value=NBResult[0][5];
         		  }
    		  //alert(NBRiskcodesql1);
     		  //alert(fm.InsuYear.value);
     		  
    		}catch(e){}
    	}
    	
    }
    //end 2012-2-21 ��ȫ���������� limj

    try{showOneCodeName('pd_currency', 'CurrencyCode', 'CurrencyCodeName');            }catch(e){}
    try{showOneCodeName('pd_currency', 'CurrencyCode', 'CurrencyName');            }catch(e){}
    try{showOneCodeName('insuyear', 'InsuYear', 'InsuYearName');                    }catch(e){}
    try{showOneCodeName('payendyear', 'PayEndYear', 'PayEndYearName');              }catch(e){}
    try{showOneCodeName('payintv', 'PayIntv', 'PayIntvName');                       }catch(e){}
    try{showOneCodeName('mateflag', 'MateFlag', 'MateFlagName');                    }catch(e){}
    try{showOneCodeName('insuyearflag', 'InsuYearFlag', 'InsuYearFlagName');        }catch(e){}
    try{showOneCodeName('joinflag', 'JoinFlag', 'JoinFlagName');                    }catch(e){}
    try{showOneCodeName('payendyearflag', 'PayEndYearFlag', 'PayEndYearFlagName');  }catch(e){}
    try{showOneCodeName('staffflag', 'StaffFlag', 'StaffFlagName');                 }catch(e){}
    try{showOneCodeName('getyear', 'GetYear', 'GetYearName');                       }catch(e){}
    try{showOneCodeName('planlevel', 'PlanLevel', 'PlanLevelName');                 }catch(e){}
    try{showOneCodeName('insuyearflag', 'GetYearFlag', 'GetYearFlagName');           }catch(e){}
    
    

	//alert(fm.all("RiskCode").value);
    //�������������Ϣ
    var NBContNo=mSwitch.getVar("ContNo");
    var PropoRiskCode = fm.all("RiskCode").value;
	initPropoRiskInfo(NBContNo,PropoRiskCode);
}

function initPropoRiskInfo(NBContNo,PropoRiskCode)
{	
    var PropoSQL = "select x.PayEndYearFlag,x.PayEndYear,x.InsuYearFlag,x.InsuYear,x.PayIntv,x.Prem,x.Amnt,x.Currency from lcpolproposal x,lccont y where x.contno=y.proposalno and y.contno='"+NBContNo+"' and x.riskcode = '"+PropoRiskCode+"' and not exists (select 1 from lcpol y where y.contno = '"+NBContNo+"' and x.riskcode=y.riskcode)";
    var PropoResult = easyExecSql(PropoSQL);
    //alert(PropoResult);
    if(PropoResult!=null)
    {
    	//����д�Զ�������Ϣ���߼�
    	fm.PayEndYearFlag.value = PropoResult[0][0];
    	fm.PayEndYear.value = PropoResult[0][1];

    	fm.InsuYearFlag.value = PropoResult[0][2];
    	fm.InsuYear.value = PropoResult[0][3];
    	
    	fm.PayIntv.value = PropoResult[0][4];
    	fm.Prem.value = PropoResult[0][5];
    	fm.Amnt.value = PropoResult[0][6];
    	fm.CurrencyCode.value = PropoResult[0][7];
    	

    }
}

function afterCodeSelect( cCodeName, Field ) {
    parameter1=cCodeName;
    parameter2=Field;
    
    if(cCodeName=="!PayEndYear-IBW37*0&0"&&Field.value!="1"){
    	document.getElementById('isISPFlag').style.display = 'none';
    }else if(cCodeName=="!PayEndYear-IBW37*0&0"&&Field.value=="1"){
    	document.getElementById('isISPFlag').style.display = '';
    }
    if(cCodeName=="!PayEndYear-IBW38*0&0"&&Field.value!="1"){
    	document.getElementById('isISPFlag').style.display = 'none';
    }else if(cCodeName=="!PayEndYear-IBW38*0&0"&&Field.value=="1"){
    	document.getElementById('isISPFlag').style.display = '';
    }
    if(cCodeName=="!PayEndYear-IBW39*0&0"&&Field.value!="1"){
    	document.getElementById('isISPFlag').style.display = 'none';
    }else if(cCodeName=="!PayEndYear-IBW39*0&0"&&Field.value=="1"){
    	document.getElementById('isISPFlag').style.display = '';
    }

    // Start - Survivorship Option Enhancement - (2014/10/03)
    try {
        var xSql="select count(1) from lcpol where SurvivorshipOption is not null and contno='" + mSwitch.getVar("ContNo") + "' and riskcode='" + fm.RiskCode.value +"'";
        var xResult = easyExecSql(xSql);
    	if (xResult > 0) {
    		document.getElementById('gSurvivorshipOption').style.display = '';    			    		
    	}
        xSql="select count(1) from lmriskparamsdef where paramstype='survivorshipoption' and riskcode='"+ fm.RiskCode.value +"'";
        xResult = easyExecSql(xSql);
    	if (xResult > 0) {
    		if (cCodeName.indexOf("PlanLevel-") != -1) {
            	document.getElementById('gSurvivorshipOption').style.display = 'none';
//            	document.getElementById('SurvivorshipOption').verify = document.getElementById('SurvivorshipOption').verify.replace("|notnullx", "|notnull");       				
    			var xPlanLevel = Field.value;
        		xSql="select count(1) from lmrisksort  where upper(risksorttype)='SODATE" + xPlanLevel + "' and riskcode='"+fm.RiskCode.value+"' and to_date(risksortvalue, 'yyyy-mm-dd')<=(select POLAPPLYDATE from lccont where contno='" + mSwitch.getVar("ContNo") + "')";
        		xResult = easyExecSql(xSql);
        		if (xResult > 0) {
            		document.getElementById('gSurvivorshipOption').style.display = '';    			
        		}
       			xSql="select count(1) from lmrisksort  where upper(risksorttype)='SODATE' and riskcode='"+fm.RiskCode.value+"' and to_date(risksortvalue, 'yyyy-mm-dd')<=(select POLAPPLYDATE from lccont where contno='" + mSwitch.getVar("ContNo") + "')";
       			xResult = easyExecSql(xSql);
       			if (xResult > 0) { 
       				document.getElementById('gSurvivorshipOption').style.display = '';
        		}
    		}
    	}    	
		if (document.getElementById('gSurvivorshipOption').style.display == 'none') {
//           	document.getElementById('SurvivorshipOption').verify = document.getElementById('SurvivorshipOption').verify.replace("|notnull", "|notnullx");
           	document.getElementById('SurvivorshipOption').value = '';
           	document.getElementById('BeneficiaryAPaymentFlag').value = '';
  		}
    } catch (ex) {};
    // Ended

    // Start - Mantis#518 - Enhance the NB Capture for HNW products ���U��ֻ��5���U��10���U
    if (cCodeName=="!PayEndYear-IBU06*0&0" || cCodeName=="!PayEndYear-IBU07*0&0" || cCodeName=="!PayEndYear-IBU08*0&0") {
    	if (Field.value=="5" || Field.value=="10") {
        	fm.PayIntv.value='12';    		
        	fm.PayEndYearFlag.value='Y';
        	showOneCodeName('insuyearflag', 'PayEndYearFlag', 'PayEndYearFlagName');
    	}
    	if (Field.value=="120") {
        	fm.PayIntv.value='0';    		    	
        	fm.PayEndYearFlag.value='A';
        	showOneCodeName('insuyearflag', 'PayEndYearFlag', 'PayEndYearFlagName');
    	}
    }
    if (cCodeName=="!PayIntv-IBU06*0&0" || cCodeName=="!PayIntv-IBU07*0&0" || cCodeName=="!PayIntv-IBU08*0&0") {
    	if (Field.value=="12") {
    		fm.PayEndYear.value='';
    		fm.PayEndYearName.value='';
    	}
    }
    if (cCodeName=="!PayEndYearFlag-IBU06*0&0" || cCodeName=="!PayEndYearFlag-IBU07*0&0" || cCodeName=="!PayEndYearFlag-IBU08*0&0") {
    	if (Field.value=="A") {
    		fm.PayIntv.value = '0';
    		fm.PayEndYear.value = '120';
    		fm.PayEndYearName.value = '120';
    		showOneCodeName('payintv', 'PayIntv', 'PayIntvName');
    	} else if (Field.value=="Y") {
    		fm.PayIntv.value = '12';
    		fm.PayEndYear.value = '';
    		fm.PayEndYearName.value = '';
    		showOneCodeName('payintv', 'PayIntv', 'PayIntvName');
    	}
    }
    // Ended

    //�����Ƿ��������ڽɵ�У��
    var sqlid108="ProposalInputSql108";
  	var mySql108=new SqlClass();
  	mySql108.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
  	mySql108.setSqlId(sqlid108);//ָ��ʹ�õ�Sql��id
  	mySql108.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
  	var strArr108 = easyExecSql(mySql108.getString());
  	if(strArr108!=0){
  	  if(fm.PayEndYear.value=='1'){
  		  if(fm.IsISP.value.length==0 || fm.IsISP.value=='0' || fm.IsISP.value=='2'){
  			  fm.PayIntv.value='0';
  		  }else{
  			  fm.PayIntv.value='3';
  		  }
  	  }else if(fm.PayEndYear.value=='5'||fm.PayEndYear.value=='10'){
  		  fm.PayIntv.value='12';
  		  fm.IsISP.value='';
  	  }
  	}

    try{showOneCodeName('payintv', 'PayIntv', 'PayIntvName');                       }catch(e){}
  	
    //�Զ�Ĭ������������
	if (cCodeName == "bnfrelation") {
		if(Field.value == "07")
		{
			var index = BnfGrid.mulLineCount;
			for(var i=0;i<index;i++)
			{
				if(BnfGrid.getRowColData(i, 6)=="07")
				{
					BnfGrid.setRowColData(i, 2, 'Own Estate');
				}
			}
      
			}
		if(Field.value == "11")
		{
			var index = BnfGrid.mulLineCount;
			for(var i=0;i<index;i++)
			{
				if(BnfGrid.getRowColData(i, 6)=="11")
				{
					BnfGrid.setRowColData(i, 2, 'Legal Successor');
				}
			}
			}
	}
    
    //20110901,cmj,��ӶԽ��Ѽ����Ĭ�ϸ�ֵ
    //20111024,zyj,��ȫ�������������Ҫ�������ձ���һ��
    var ModifyFlag=mSwitch.getVar("ModifyFlag");
    
    if(LoadFlag==8&&ModifyFlag!="Y"&&fm.PayIntv.value==""){
    	$("input[name='CValiDate']").val(ABCValiDate);
    	$("input[name='MainPolNo']").val(ABMainPolNo);
   }
    
    //alert(cCodeName+"|"+Field.value)
    //������ֻ��ѡǰ����
    if(cCodeName=='BnfType'&&Field.value!='L'&&Field.value!='B')
    {
    	myAlert(''+"������ѡ������������"+'');
      Field.value='';
    	return false;	
    }
    
    //alert(parameter1);
    if(parameter1=="RiskCode" && LoadFlag==1){
    	  // showMultRiskGrid(Field.value);
        return;
    }
    if(cCodeName=="YesNo")
    {
	    try
	    {
	    //alert(123);
		    if(fm.RiskCode.value=="111302"||fm.RiskCode.value=="111303")
		    {
		    	if(Field.value=="Y")
		            {
		            	fm.all("CValiDate").readOnly=false;
		            	
		            }else{
		            	//alert(456);
		            	//fm.CValiDate.value="";
		            	fm.all("CValiDate").readOnly=true;
		            	
		            }
		    	}
		}catch(ex){}
    }
    //��Ը������ֵ����⴦��
    try
    {       
       //���ѷ�ʽ
       if(cCodeName.substring(1,cCodeName.indexOf("-")) == 'PayIntv')
       {
           if (Field.value == "0" && strArr108==0) 
           {
             //document.all('TDPayEndYear').innerHTML = "<Input class=codeno name=PayEndYear verify=\"�ɷ�����|code:!PayEndYear&notnull\" onfocus=\"checkPayIntv()\"><input class=codename name=PayEndYearName readonly=true elementtype=nacessary>";
             //fm.all("PayEndYear").readOnly = true;
             //fm.all("PayEndYear").value = "1000";
             //fm.all("PayEndYearName").value = "��";
         	  
         	  //2008-08-25 ��Ϊ��5.3һ�£���Ϊ����ʱ�����ڼ�ͱ����ڼ�һ��
         	  if(!(fm.all("InsuYear").value==null||fm.all("InsuYear").value==""))
         	  {
         		  fm.all('PayEndYear').value=fm.all('InsuYear').value;
       	  		  fm.all('PayEndYearFlag').value=fm.all('InsuYearFlag').value;	
       	  		  fm.all('PayEndYearName').value=fm.all('InsuYearName').value;
       	  		  fm.all("PayEndYear").readOnly = true;
       	  		  showOneCodeName('insuyearflag', 'PayEndYearFlag', 'PayEndYearFlagName');
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
      //����ѡ��
      try{
        if( type =="noScan" && cCodeName == 'RiskInd')//������ɨ���¼��
          {
		  	
					var sqlid1="ProposalInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(Field.value);//ָ������Ĳ���
		mySql1.addSubPara(ManageCom);//ָ������Ĳ���
		mySql1.addSubPara(ManageCom);//ָ������Ĳ���
	    var strSql=mySql1.getString();	
			
              //var strSql = "select distinct 1 from ldsysvar where VERIFYOPERATEPOPEDOM('"+Field.value+"','"+ManageCom+"','"+ManageCom+"',"+"'Pa')=1 ";
              var arrResult = easyExecSql(strSql);
          if (arrResult == null) {
            myAlert(""+I18NMsg(/*����*/"M0000061670")+" ["+ManageCom+"] "+I18NMsg(/*��Ȩ¼������*/"G0000002932")+" ["+Field.value+"] "+I18NMsg(/*��Ͷ����!*/"G0000002933")+"");
            gotoInputPage();
            return ;
          }
            return;
      }

    }
    catch(ex) {}

      try{
        if(cCodeName == 'RiskInd'){
          if (typeof(prtNo)!="undefined" && typeof(type)=="undefined" )//������ɨ���¼��
          {
		  	
		var sqlid2="ProposalInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(Field.value);//ָ������Ĳ���
		mySql2.addSubPara(ManageCom);//ָ������Ĳ���
		mySql2.addSubPara(ManageCom);//ָ������Ĳ���
	    var strSql2=mySql2.getString();	
			
            //var strSql2 = "select distinct 1 from ldsysvar where VERIFYOPERATEPOPEDOM('"+Field.value+"','"+ManageCom+"','"+ManageCom+"',"+"'Pz')=1 ";
            var arrResult2 = easyExecSql(strSql2);
            if (arrResult2 == null) {
              myAlert(""+"����"+" ["+ManageCom+"] "+"��Ȩ¼������"+" ["+Field.value+"] "+"��Ͷ����!"+"");
              gotoInputPage();
              return ;
            }
          }
          return;
        }
    }
    catch(ex){}


   //alert("cCodeName="+cCodeName);//mark by yaory ��ʼ�����ֽ���
    if( cCodeName == "RiskInd" || cCodeName == "RiskGrp" || cCodeName == "RiskCode" || cCodeName.substring(0,3)=="***" || cCodeName.substring(0,2)=="**" || cCodeName.substring(0,3)=="GAI")
      {
        var tProposalGrpContNo = parent.VD.gVSwitch.getVar( "GrpContNo" );
        var tContPlanCode = parent.VD.gVSwitch.getVar( "ContPlanCode" );
        var tContNo=mSwitch.getVar("ContNo");//add by zyj
        if(mainRiskPolNo==""&&parent.VD.gVSwitch.getVar("mainRiskPolNo")==true){
          mainRiskPolNo=parent.VD.gVSwitch.getVar("mainRiskPolNo");
        }

        if(cCodeName=="RiskCode" || cCodeName.substring(0,2)=="**")
        {

          if(!isSubRisk(Field.value)){ cMainRiskCode=Field.value;}
          else if(isSubRisk(Field.value)&&mainRiskPolNo!="")
          {
            //var mainRiskSql="select riskcode from lcpol where polno='"+mainRiskPolNo+"'";
            var mainRiskSql="select riskcode from lcpol where polno=mainpolno and contno='"+tContNo+"'";//modify by zyj
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
          getRiskInput(Field.value, LoadFlag);//LoadFlag��ҳ���ʼ����ʱ������
        }
        else
        {
          if (tContPlanCode=='false'||tContPlanCode==null||tContPlanCode=='')
            {

                    //���û�б��ռƻ�����,��ѯ��û��Ĭ��ֵ�������Ĭ��ֵҲ��Ҫ���ú�̨��ѯ

                    var strsql = "select ContPlanCode from LCContPlan where ContPlanCode='00' and ProposalGrpContNo='"+tProposalGrpContNo+"'";
                    if(LoadFlag ==7){
                        strsql = "select ContPlanCode from LCContPlan where ContPlanCode='00' and GrpContNo='"+tProposalGrpContNo+"'";
                    }
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
                        getRiskInput(Field.value, LoadFlag);//LoadFlag��ҳ���ʼ����ʱ������
                    }

            }
            else
            {
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

      //-------------------------------------------------Beg
      //�޸��ˣ�chenrong
      //�޸����ڣ�2006-03-16
      //��ȡ��ʽΪ����ʱ������Ҫ������ȡƵ�ʵ�

      if (trim(cCodeName).substring(1,13) == "GetDutyKind1")
      {
          if (Field.value == "0")
          {
            try
            {
                for (i = 2;i <= fm.GetDutyKindFlag.value; i++)
                {
                    fm.all('GetDutyKind' + i).value = "";
                    fm.all('GetDutyKind' + i + 'Name').value = "";
                    fm.all('GetDutyKind' + i).disabled = true;
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
                    fm.all('GetDutyKind' + i).disabled = false;
                }
            }
            catch(ex)
            {
            }
          }
      }
      //-------------------------------------------------End
	
	

	
      //�Զ���д��������Ϣ
      if (cCodeName == "customertype") {
    	  if(LoadFlag==1){
        if (Field.value == "A1") {
          if(ContType!="2")
          {
        
          //alert("1111111");
          var index = BnfGrid.mulLineCount;
          //������e - �Ԅӏ���һ�з�����������, �Kdefault ������e�� B, ͬdefault �Ƿ�鲻����Q�����˞�N
          BnfGrid.setRowColData(index-1, 1, 'B');
          BnfGrid.setRowColData(index-1, 15, 'N');
          
          BnfGrid.setRowColData(0, 1, 'L');
       	  BnfGrid.setRowColData(0, 2, fm.all("AppntNameEn").value);
          BnfGrid.setRowColData(0, 3, fm.all("AppntSex").value);
          BnfGrid.setRowColData(0, 4, fm.all("AppntIDType").value);
          BnfGrid.setRowColData(0, 5, fm.all("AppntIDNo").value);
          //tongmeng 2007-12-18 Modify
          //�޸������˹�ϵ����
          //BnfGrid.setRowColData(index-1, 6,parent.VD.gVSwitch.getVar( "RelationToAppnt"));
          BnfGrid.setRowColData(0, 7, 'P');
          BnfGrid.setRowColData(0, 6, fm.all("AppntRelationToInsured").value);
          BnfGrid.setRowColData(0, 8, '1');
          BnfGrid.setRowColData(0, 9, fm.all("AppntHomeAddress").value);
          BnfGrid.setRowColData(0, 15, 'N');
          BnfGrid.setRowClass(index-1,'warn');
          //hl
          //BnfGrid.setRowColData(index-1, 9, fm.all("AppntNo").value);
          //alert("toubaoren:"+fm.all("AppntNo").value)    
          }
          else
          {

            myAlert(""+"Ͷ����Ϊ���壬������Ϊ�����ˣ�"+"")
          var index = BnfGrid.mulLineCount;
          //������e - �Ԅӏ���һ�з�����������, �Kdefault ������e�� B, ͬdefault �Ƿ�鲻����Q�����˞�N
          BnfGrid.setRowColData(index-1, 1, 'B');
          BnfGrid.setRowColData(index-1, 15, 'N');
          
          BnfGrid.setRowColData(0, 2, "");
          BnfGrid.setRowColData(0, 3, "");
          BnfGrid.setRowColData(0, 4, "");
          BnfGrid.setRowColData(0, 5, "");
          BnfGrid.setRowColData(0, 6, "");
          BnfGrid.setRowColData(0, 9, "");
          BnfGrid.setRowClass(index-1,'warn');
          }
        }else if (Field.value == "A") {
            if(ContType!="2")
            {
          
            //alert("1111111");
            var index = BnfGrid.mulLineCount;
            //������e - �Ԅӏ���һ�з�����������, �Kdefault ������e�� B, ͬdefault �Ƿ�鲻����Q�����˞�N
            BnfGrid.setRowColData(index-1, 1, 'B');
            BnfGrid.setRowColData(index-1, 2, fm.all("AppntNameEn").value);
            BnfGrid.setRowColData(index-1, 3, fm.all("AppntSex").value);
            BnfGrid.setRowColData(index-1, 4, fm.all("AppntIDType").value);
            BnfGrid.setRowColData(index-1, 5, fm.all("AppntIDNo").value);
            BnfGrid.setRowColData(index-1, 15, 'N');
            
            BnfGrid.setRowColData(0, 1, 'L');
         	BnfGrid.setRowColData(0, 2, fm.all("AppntNameEn").value);
            BnfGrid.setRowColData(0, 3, fm.all("AppntSex").value);
            BnfGrid.setRowColData(0, 4, fm.all("AppntIDType").value);
            BnfGrid.setRowColData(0, 5, fm.all("AppntIDNo").value);
            //tongmeng 2007-12-18 Modify
            //�޸������˹�ϵ����
            //BnfGrid.setRowColData(index-1, 6,parent.VD.gVSwitch.getVar( "RelationToAppnt"));
            BnfGrid.setRowColData(0, 7, 'P');
            BnfGrid.setRowColData(0, 6, fm.all("AppntRelationToInsured").value);
            BnfGrid.setRowColData(0, 8, '1');
            BnfGrid.setRowColData(0, 9, fm.all("AppntHomeAddress").value);
            BnfGrid.setRowColData(0, 15, 'N');
            BnfGrid.setRowClass(index-1,'warn');
            //hl
            //BnfGrid.setRowColData(index-1, 9, fm.all("AppntNo").value);
            //alert("toubaoren:"+fm.all("AppntNo").value)    
            }
            else
            {

              myAlert(""+"Ͷ����Ϊ���壬������Ϊ�����ˣ�"+"")
            var index = BnfGrid.mulLineCount;
            //������e - �Ԅӏ���һ�з�����������, �Kdefault ������e�� B, ͬdefault �Ƿ�鲻����Q�����˞�N
            BnfGrid.setRowColData(index-1, 1, 'B');
            BnfGrid.setRowColData(index-1, 15, 'N');
            
            BnfGrid.setRowColData(0, 2, "");
            BnfGrid.setRowColData(0, 3, "");
            BnfGrid.setRowColData(0, 4, "");
            BnfGrid.setRowColData(0, 5, "");
            BnfGrid.setRowColData(0, 6, "");
            BnfGrid.setRowColData(0, 9, "");
            BnfGrid.setRowClass(index-1,'warn');
            }
          }
        else if (Field.value == "I") {
        //alert("2222222");
        //alert("�������뱻���˹�ϵУ��");
          var index = BnfGrid.mulLineCount;
        //������e - �Ԅӏ���һ�з�����������, �Kdefault ������e�� B, ͬdefault �Ƿ�鲻����Q�����˞�N
          BnfGrid.setRowColData(index-1, 1, 'B');
          BnfGrid.setRowColData(index-1, 2, fm.all("Name").value);
          BnfGrid.setRowColData(index-1, 3, fm.all("Sex").value);
          BnfGrid.setRowColData(index-1, 4, fm.all("IDType").value);
          BnfGrid.setRowColData(index-1, 5, fm.all("IDNo").value);
          BnfGrid.setRowColData(index-1, 15, 'N');
          
        BnfGrid.setRowColData(0, 2, fm.all("Name").value);
        BnfGrid.setRowColData(0, 3, fm.all("Sex").value);
        BnfGrid.setRowColData(0, 4, fm.all("IDType").value);
        BnfGrid.setRowColData(0, 5, fm.all("IDNo").value);
        BnfGrid.setRowColData(0, 6, "00");
        //BnfGrid.setRowColData(index-1, 8, fm.all("AppntHomeAddress").value);
        BnfGrid.setRowColData(0, 8, '1');
        //hl
        BnfGrid.setRowColData(0, 9, fm.all("HomeAddress").value);
        BnfGrid.setRowClass(index-1,'warn');
				//tongmeng 2007-12-28 add
					//��������˲���Ϊ����
					var tempDeadBnf = BnfGrid.getRowColData(0, 1);
          if(tempDeadBnf==null||trim(tempDeadBnf)=='')
          {
          	//Ĭ��Ϊ����������
          	tempDeadBnf = '0'
          }
          if(tempDeadBnf=='1')
          {
        	  //alert('��������˲���Ϊ����1!');
          	myAlert(''+"��������˲���Ϊ����!"+'');
          	//alert("3333333");
          	BnfGrid.setRowColData(0, 2, '');
          	BnfGrid.setRowColData(0, 3, '');
          	BnfGrid.setRowColData(0, 4, '');
          	BnfGrid.setRowColData(0, 5, '');
          	BnfGrid.setRowColData(0, 6, '');
          	BnfGrid.setRowColData(0, 9, '');
          	return ;
          }
        //BnfGrid.setRowColData(index-1, 10, fm.all("InsuredNo").value);
                //alert("4544564"+fm.all("InsuredNo").value);
        }
    	  }else{
    		  
    		  if (Field.value == "A"||Field.value == "A1") {
    	          if(ContType!="2")
    	          {
    	        
    	          //alert("1111111");
    	          var index = BnfGrid.mulLineCount;
    	          BnfGrid.setRowColData(index-1, 1, 'L');
    	       	  BnfGrid.setRowColData(index-1, 2, fm.all("AppntNameEn").value);
    	          BnfGrid.setRowColData(index-1, 3, fm.all("AppntSex").value);
    	          BnfGrid.setRowColData(index-1, 4, fm.all("AppntIDType").value);
    	          BnfGrid.setRowColData(index-1, 5, fm.all("AppntIDNo").value);
    	          //tongmeng 2007-12-18 Modify
    	          //�޸������˹�ϵ����
    	          //BnfGrid.setRowColData(index-1, 6,parent.VD.gVSwitch.getVar( "RelationToAppnt"));
    	          BnfGrid.setRowColData(index-1, 7, 'P');
    	          BnfGrid.setRowColData(index-1, 6, fm.all("AppntRelationToInsured").value);
    	          BnfGrid.setRowColData(index-1, 8, '1');
    	          BnfGrid.setRowColData(index-1, 9, fm.all("AppntHomeAddress").value);
    	          BnfGrid.setRowColData(index-1, 15, 'N');
    	          //hl
    	          //BnfGrid.setRowColData(index-1, 9, fm.all("AppntNo").value);
    	          //alert("toubaoren:"+fm.all("AppntNo").value)    
    	          }
    	          else
    	          {

    	            myAlert(""+"Ͷ����Ϊ���壬������Ϊ�����ˣ�"+"")
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
    	        //alert("2222222");
    	        //alert("�������뱻���˹�ϵУ��");
    	          var index = BnfGrid.mulLineCount;
    	        BnfGrid.setRowColData(index-1, 2, fm.all("Name").value);
    	        BnfGrid.setRowColData(index-1, 3, fm.all("Sex").value);
    	        BnfGrid.setRowColData(index-1, 4, fm.all("IDType").value);
    	        BnfGrid.setRowColData(index-1, 5, fm.all("IDNo").value);
    	        BnfGrid.setRowColData(index-1, 6, "00");
    	        //BnfGrid.setRowColData(index-1, 8, fm.all("AppntHomeAddress").value);
    	        BnfGrid.setRowColData(index-1, 8, '1');
    	        //hl
    	        BnfGrid.setRowColData(index-1, 9, fm.all("HomeAddress").value);
    					//tongmeng 2007-12-28 add
    						//��������˲���Ϊ����
    						var tempDeadBnf = BnfGrid.getRowColData(index-1, 1);
    	          if(tempDeadBnf==null||trim(tempDeadBnf)=='')
    	          {
    	          	//Ĭ��Ϊ����������
    	          	tempDeadBnf = '0'
    	          }
    	          if(tempDeadBnf=='1')
    	          {
    	        	  //alert('��������˲���Ϊ����1!');
    	          	myAlert(''+"��������˲���Ϊ����!"+'');
    	          	//alert("3333333");
    	          	BnfGrid.setRowColData(index-1, 2, '');
    	          	BnfGrid.setRowColData(index-1, 3, '');
    	          	BnfGrid.setRowColData(index-1, 4, '');
    	          	BnfGrid.setRowColData(index-1, 5, '');
    	          	BnfGrid.setRowColData(index-1, 6, '');
    	          	BnfGrid.setRowColData(index-1, 9, '');
    	          	return ;
    	          }
    	        //BnfGrid.setRowColData(index-1, 10, fm.all("InsuredNo").value);
    	                //alert("4544564"+fm.all("InsuredNo").value);
    	        }
    	  }
        return;
    }


        //�շѷ�ʽѡ��
    if (cCodeName == "PayLocation") {
        if (Field.value != "0") {
          if (hiddenBankInfo=="") hiddenBankInfo = DivLCKind.innerHTML;
          fm.all("BankCode").className = "readonly";
          fm.all("BankCode").readOnly = true;
          fm.all("BankCode").tabIndex = -1;
          fm.all("BankCode").ondblclick = "";

          fm.all("AccName").className = "readonly";
          fm.all("AccName").readOnly = true;
          fm.all("AccName").tabIndex = -1;
          fm.all("AccName").ondblclick = "";

          fm.all("BankAccNo").className = "readonly";
          fm.all("BankAccNo").readOnly = true;
          fm.all("BankAccNo").tabIndex = -1;
          fm.all("BankAccNo").ondblclick = "";
        }
        else{
          if (hiddenBankInfo!="") DivLCKind.innerHTML = hiddenBankInfo;
		  
		 var sqlid3="ProposalInputSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(fm.AppntCustomerNo.value);//ָ������Ĳ���
		mySql3.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	    strSql=mySql3.getString();	
		  
         // strSql = "select BankCode,BankAccNo,AccName from LCAppNT where AppNtNo = '" + fm.all('AppntCustomerNo').value + "' and contno='"+fm.all('ContNo').value+"'";
          var arrAppNtInfo = easyExecSql(strSql);
          fm.all("BankCode").value = arrAppNtInfo[0][0];
          fm.all("AccName").value = arrAppNtInfo[0][2];
          fm.all("BankAccNo").value = arrAppNtInfo[0][1];
          fm.all("PayLocation").value = "0";
          fm.all("PayLocation").focus();
        }
        return;
    }
      //���δ���ѡ��
      if(cCodeName == "DutyCode"){
        var index = DutyGrid.mulLineCount;
		
		var sqlid4="ProposalInputSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(Field.value);//ָ������Ĳ���
	    var strSql =mySql4.getString();	
		
       // var strSql = "select dutyname from lmduty where dutycode='"+Field.value+"'";
        var arrResult = easyExecSql(strSql);
        var dutyname= arrResult[0].toString();
        DutyGrid.setRowColData(index-1, 2, dutyname);
        return;
      }
      //add by yaory
      if(cCodeName == "insuredpeople")
      {
      	//alert("4444444");
        var index = BnfGrid.mulLineCount;
          BnfGrid.setRowColData(index-1, 10, fm.all("CustomerNo").value);
          //BnfGrid.setRowColData(index-1, 11, fm.all("AppntIDType").value);
          //BnfGrid.setRowColData(index-1, 12, fm.all("AppntIDNo").value);
      }
      if(cCodeName == "PolCalRule1"){  //��ʱδ��
        if(Field.value=="1"){       //ͳһ����
          divFloatRate.style.display="none";
          divFloatRate2.style.display="";
        }
        else if(Field.value=="2"){  //Լ�������ۿ�
          fm.all('FloatRate').value="";
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
      


      //------------------------Beg
      //add by:chenrong
      //date:2006.05.22
      //��������
      if(cCodeName=="PayRuleCode"){
        fm.action="./ProposalQueryPayRule.jsp?AppFlag=0";
        fm.submit();
        return;
      }
      //-----------------------End
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
 
function forInsuredNo(){
	BnfGrid.setRowColData(index-1, 13, fm.all("AppntIDNo").value);
}
 
function beforeSubmit()
{

  if( verifyInput() == false ) return false;
  //�����Ƿ��������ڽɵ�У��
  var sqlid108="ProposalInputSql108";
	var mySql108=new SqlClass();
	mySql108.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql108.setSqlId(sqlid108);//ָ��ʹ�õ�Sql��id
	mySql108.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	var strArr108 = easyExecSql(mySql108.getString());
	if(strArr108!=0){
	  if(fm.PayEndYear.value=='1'){
		  if(fm.IsISP.value.length==0){
			  myAlert(""+I18NMsg(/*Ո����Ƿ��O�������U!*/"L0000026158")+"");
			  return false;
		  }
		  if(fm.IsISP.value=='0' || fm.IsISP.value=='2'){
			  fm.PayIntv.value='0';
		  }else{
			  fm.PayIntv.value='3';
		  }
	  }else if(fm.PayEndYear.value=='5'||fm.PayEndYear.value=='10'){
		  fm.PayIntv.value='12';
		  fm.IsISP.value='';
	  }
	}

    // Start - Survivorship Option Enhancement - (2014/10/03)
    try {
		if (document.getElementById('gSurvivorshipOption').style.display == 'none') {
//           	document.getElementById('SurvivorshipOption').verify = document.getElementById('SurvivorshipOption').verify.replace("|notnull", "|notnullx");
           	document.getElementById('SurvivorshipOption').value = '';
           	document.getElementById('BeneficiaryAPaymentFlag').value = '';
  		} else if(document.getElementById('SurvivorshipOption').value == '') {
           	myAlert(""+I18NMsg(/*Ո����������Iȡ����x��!*/"L0000026264")+"");
           	return false;
  		}
    } catch (ex) {};
    // Ended

  var sqlid95="ProposalInputSql95";
	var mySql95=new SqlClass();
	mySql95.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql95.setSqlId(sqlid95);//ָ��ʹ�õ�Sql��id
	mySql95.addSubPara(parent.VD.gVSwitch.getVar("ContNo"));//ָ������Ĳ���
	mySql95.addSubPara(parent.VD.gVSwitch.getVar("InsuredNo"));//ָ������Ĳ���
	mySql95.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	var strArr95 = easyExecSql(mySql95.getString());
  if(strArr95!=null&&strArr95!=""){
  	myAlert(""+"�����˞�������ʿ�������x�������"+"");
  	return false;
  }

  var sqlid101="ProposalInputSql101";
	var mySql101=new SqlClass();
	mySql101.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql101.setSqlId(sqlid101);//ָ��ʹ�õ�Sql��id
	mySql101.addSubPara(parent.VD.gVSwitch.getVar("ContNo"));//ָ������Ĳ���
	mySql101.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	var strArr101 = easyExecSql(mySql101.getString());
  if(strArr101!=null&&strArr101!=""){
	myAlert(/*���ڼ����ڽ��Ѿ�ֻ���ܸ�Ԫ���ÿ�ת��*/""+""+"");
	//return false;
  }

//  var sqlid99="ProposalInputSql99";
//	var mySql99=new SqlClass();
//	mySql99.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
//	mySql99.setSqlId(sqlid99);//ָ��ʹ�õ�Sql��id
//	mySql99.addSubPara(parent.VD.gVSwitch.getVar("ContNo"));//ָ������Ĳ���
//	mySql99.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
//	var strArr99 = easyExecSql(mySql99.getString());
//  if(strArr99!=null&&strArr99!=""){
//	alert(/*���ڽ���ֻ���ܸ�Ԫ���ÿ�ת��*/""+""+"");
//	//return false;
//  }

  var sqlid100="ProposalInputSql100";
	var mySql100=new SqlClass();
	mySql100.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql100.setSqlId(sqlid100);//ָ��ʹ�õ�Sql��id
	mySql100.addSubPara(parent.VD.gVSwitch.getVar("ContNo"));//ָ������Ĳ���
	mySql100.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	var strArr100 = easyExecSql(mySql100.getString());
  if(strArr100!=null&&strArr100!=""){
	myAlert(/*���ڼ����ڽ��Ѿ�ֻ���ܸ�Ԫ/��������ÿ�ת��*/""+""+"");
	//return false;
  }

  // Start - Added the new validation to check which product cannot be selected the "Staff Flag" under sales channel
  try {
		if (fm.all("StaffFlag").value != null && fm.all("StaffFlag").value == "1") {
			var mySql104 = new SqlClass();
			mySql104.setResourceName("app.ProposalInputSql");
			mySql104.setSqlId("ProposalInputSql104");
			mySql104.addSubPara(fm.RiskCode.value);
			mySql104.addSubPara(parent.VD.gVSwitch.getVar("ContNo"));
			var strArr104 = easyExecSql(mySql104.getString());
			if (strArr104 != null) {
				if (strArr104[0][0] != null && strArr104[0][0] == "1"){
					// START [Whole Life Yearly Coupon Plan] change validation from confirm to alert (by Goeast on 2015-01-22) 
					//if (confirm(""+""+I18NMsg(/*�����UӋ���*/"L0000026159")+"" + strArr104[0][1] + ""+I18NMsg(/*����δ�O�ІT����*/"L0000026160")+", "+I18NMsg(/*�Ƿ���Ҫ����?*/"L0000026161")+"")) {
					myAlert(""+I18NMsg(/*�T���β��m��춴��N������*/"L0000026822"));
					// END [Whole Life Yearly Coupon Plan] change validation from confirm to alert (by Goeast on 2015-01-22)
					return false;
				}						
			}
		}	  
  } catch( ex ) {}
  // Ended - Added the new validation to check which product cannot be selected the "Staff Flag" under sales channel 

  try {
	if (fm.all("PDAFlag").value != null && fm.all("PDAFlag").value == "1") {
//		var mySql110 = new SqlClass();
//		mySql110.setResourceName("app.ProposalInputSql");
//		mySql110.setSqlId("ProposalInputSql110");
//		mySql110.addSubPara(fm.RiskCode.value);
//		mySql110.addSubPara(fm.PayEndYear.value);
//		var strArr110 = easyExecSql(mySql110.getString());
//		if (strArr110 != null && strArr110[0][0] == "1") {
//			var mySql109 = new SqlClass();
//			mySql109.setResourceName("app.ProposalInputSql");
//			mySql109.setSqlId("ProposalInputSql109");
//			mySql109.addSubPara(fm.RiskCode.value);
//			var strArr109 = easyExecSql(mySql109.getString());
//			if (confirm(""+"���M�U�M���ڞ�" + strArr109[0][0] + "��, ���x���A�U���M���� (ֻ�����U��ʽ), �Ƿ���Ҫ����?"+"")) {
//				return false;
//			}
//		}

		var mySql111 = new SqlClass();
		mySql111.setResourceName("app.ProposalInputSql");
		mySql111.setSqlId("ProposalInputSql111");
		mySql111.addSubPara(fm.RiskCode.value);
		mySql111.addSubPara(fm.PayEndYear.value);
		var strArr111 = easyExecSql(mySql111.getString());
		if (strArr111 != null && strArr111[0][0] == "1") {
			var mySql112 = new SqlClass();
			mySql112.setResourceName("app.ProposalInputSql");
			mySql112.setSqlId("ProposalInputSql112");
			mySql112.addSubPara(fm.RiskCode.value);
			var strArr112 = easyExecSql(mySql112.getString());
			if (confirm(""+""+I18NMsg(/*���M�U�M���ڞ�*/"L0000026162")+"" + strArr112[0][0] + ""+"��"+", "+I18NMsg(/*�����x���A�U���M����*/"L0000026163")+", "+I18NMsg(/*�Ƿ���Ҫ����?*/"L0000026161")+""+"")) {
				return false;
			}
		}
	}	  
  } catch( ex ) {}
  
//  var sqlid97="ProposalInputSql97";
//	var mySql97=new SqlClass();
//	mySql97.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
//	mySql97.setSqlId(sqlid97);//ָ��ʹ�õ�Sql��id
//	mySql97.addSubPara(parent.VD.gVSwitch.getVar("ContNo"));//ָ������Ĳ���
//	mySql97.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
//	var strArr97 = easyExecSql(mySql97.getString());
//  if(strArr97!=null&&strArr97!=""){
//	alert(/*���ڽ���ֻ���ܸ�Ԫ/��������ÿ�ת��*/""+""+"");
//	//return false;
//  }

//  var sqlid102="ProposalInputSql102";
//	var mySql102=new SqlClass();
//	mySql102.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
//	mySql102.setSqlId(sqlid102);//ָ��ʹ�õ�Sql��id
//	mySql102.addSubPara(parent.VD.gVSwitch.getVar("ContNo"));//ָ������Ĳ���
//	var strArr102 = easyExecSql(mySql102.getString());
//  if(strArr102!=null&&strArr102!=""){
//	alert(/*�����������ÿ�, ֻ������������ÿ�ת��, ���򲻻����ת��*/""+""+", "+""+", "+""+"");
//	//return false;
//  }

// Start-ԓ�aƷ�����������������ÿ��U�����ڼ��m�ڱ��M-xf
  try {
			var mySql106 = new SqlClass();
			mySql106.setResourceName("app.ProposalInputSql");
			mySql106.setSqlId("ProposalInputSql106");
			mySql106.addSubPara(fm.RiskCode.value);
			mySql106.addSubPara(parent.VD.gVSwitch.getVar("ContNo"));
			var strArr106 = easyExecSql(mySql106.getString());
			if (strArr106 != null) {
				if (strArr106[0][0] != null && strArr106[0][0] == "1"){
					if (confirm(""+""+I18NMsg(/*�����UӋ���*/"L0000026275")+"" + strArr106[0][1] + ""+I18NMsg(/*���������������ÿ��U�����ڼ��m�ڱ��M*/"L0000026164")+", "+I18NMsg(/*�Ƿ���Ҫ����?*/"L0000026161")+""+"")) {
						return false;
					}
				}						
			}
  } catch( ex ) {}
  // Ended  
// Start-ԓ�aƷ���������Uֻ���������y���ÿ��U���m�ڱ��M-xf
  try {
		if (fm.all("PayIntv").value != null && fm.all("PayIntv").value == "12") {
			var mySql107 = new SqlClass();
			mySql107.setResourceName("app.ProposalInputSql");
			mySql107.setSqlId("ProposalInputSql107");
			mySql107.addSubPara(fm.RiskCode.value);
			mySql107.addSubPara(parent.VD.gVSwitch.getVar("ContNo"));
			var strArr107 = easyExecSql(mySql107.getString());
			if (strArr107 != null) {
				if (strArr107[0][0] != null && strArr107[0][0] == "1"){
					if (confirm(""+""+I18NMsg(/*�����UӋ���*/"L0000026276")+"" + strArr107[0][1] + ""+I18NMsg(/*�������U�rֻ���������y���ÿ��U���m�ڱ��M*/"L0000026265")+", "+I18NMsg(/*�Ƿ���Ҫ����?*/"L0000026161")+"")) {
						return false;
					}
				}						
			}
		}	  
  } catch( ex ) {}
  // Ended 

  // Start - New validation on DVD and GCP which are compare with Maturity Pay Flag (Mantis#164) - (2014/10/03)
  try {
	  var xMessage = null;
	  xMessageCheque = ""+I18NMsg(/*��"�ڝM�𸶿�ָʾ"��"�y���D�~"*/"L0000026266")+", "+I18NMsg(/*"�t���Iȡ��ʽ"������"�F��(֧Ʊ)"\n\n�Ƿ���Ҫ����?*/"L0000026763")+"";
	  xMessageTransfer = ""+I18NMsg(/*��"�ڝM�𸶿�ָʾ"��"֧Ʊ"*/"L0000026268")+", "+I18NMsg(/*"�t���Iȡ��ʽ"������"�F��(�y���D�~)"\n\n�Ƿ���Ҫ����?*/"L0000026764")+"";
	  // Check BonusGetMode
	  if (fm.all("BonusGetMode").value != null && fm.all("BonusGetMode").value == "0") {
		  var mySql113 = new SqlClass();
		  mySql113.setResourceName("app.ProposalInputSql");
		  mySql113.setSqlId("ProposalInputSql113");
		  mySql113.addSubPara(parent.VD.gVSwitch.getVar("ContNo"));
		  var strArr113 = easyExecSql(mySql113.getString());
		  if (strArr113 != null) {
			  if (strArr113[0][0] != null && strArr113[0][0] == "0"){
				  if (confirm(xMessageCheque)) {
					  return false;
				  }
			  }						
		  }
	  }	  
	  if (fm.all("BonusGetMode").value != null && fm.all("BonusGetMode").value == "1") {
		  var mySql113 = new SqlClass();
		  mySql113.setResourceName("app.ProposalInputSql");
		  mySql113.setSqlId("ProposalInputSql113");
		  mySql113.addSubPara(parent.VD.gVSwitch.getVar("ContNo"));
		  var strArr113 = easyExecSql(mySql113.getString());
		  if (strArr113 != null) {
			  if (strArr113[0][0] != null && strArr113[0][0] == "Q"){
				  if (confirm(xMessageTransfer)) {
					  return false;
				  }
			  }						
		  }
	  }	  
  } catch( ex ) {}
  try {
	  var xMessage = null;
	  xMessageCheque = ""+I18NMsg(/*��"�ڝM�𸶿�ָʾ"��"�y���D�~"*/"L0000026266")+", "+I18NMsg(/*"���C��֧ȡ�F��/���C��֧ȡ���/ÿ�걣�C��Ϣ�Ľo����ʽ"������"�F��(֧Ʊ)"\n\n�Ƿ���Ҫ����?*/"L0000026765")+"";
	  xMessageTransfer = ""+I18NMsg(/*��"�ڝM�𸶿�ָʾ"��"֧Ʊ"*/"L0000026268")+", "+I18NMsg(/*"���C��֧ȡ�F��/���C��֧ȡ���/ÿ�걣�C��Ϣ�Ľo����ʽ"������"�F��(�y���D�~)"\n\n�Ƿ���Ҫ����?*/"L0000026766")+"";
	  // Check GCPGetMode
	  if (fm.all("GCPGetMode").value != null && fm.all("GCPGetMode").value == "0") {
		  var mySql113 = new SqlClass();
		  mySql113.setResourceName("app.ProposalInputSql");
		  mySql113.setSqlId("ProposalInputSql113");
		  mySql113.addSubPara(parent.VD.gVSwitch.getVar("ContNo"));
		  var strArr113 = easyExecSql(mySql113.getString());
		  if (strArr113 != null) {
			  if (strArr113[0][0] != null && strArr113[0][0] == "0"){
				  if (confirm(xMessageCheque)) {
					  return false;
				  }
			  }						
		  }
	  }	  
	  if (fm.all("GCPGetMode").value != null && fm.all("GCPGetMode").value == "1") {
		  var mySql113 = new SqlClass();
		  mySql113.setResourceName("app.ProposalInputSql");
		  mySql113.setSqlId("ProposalInputSql113");
		  mySql113.addSubPara(parent.VD.gVSwitch.getVar("ContNo"));
		  var strArr113 = easyExecSql(mySql113.getString());
		  if (strArr113 != null) {
			  if (strArr113[0][0] != null && strArr113[0][0] == "Q"){
				  if (confirm(xMessageTransfer)) {
					  return false;
				  }
			  }						
		  }
	  }	  
  } catch( ex ) {}
  // Ended

  var sqlid98="ProposalInputSql98";
	var mySql98=new SqlClass();
	mySql98.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql98.setSqlId(sqlid98);//ָ��ʹ�õ�Sql��id
	mySql98.addSubPara(parent.VD.gVSwitch.getVar("ContNo"));//ָ������Ĳ���
	var strArr98 = easyExecSql(mySql98.getString());
  if(strArr98!=null&&strArr98!=""){
	myAlert(/*������������˫�����ÿ�, ֻ���ܸ�Ԫ���ÿ�ת��, ���򲻻����ת��*/""+""+", "+""+", "+""+"");
	//return false;
  }
}

/*********************************************************************
 *  ����LoadFlag����һЩFlag����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
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

  //����Ͷ�����Ų��ҳ����е��������
  if (arrGrpRisk == null) {
  	
			var sqlid5="ProposalInputSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(prtNo);//ָ������Ĳ���
	    var strSql =mySql5.getString();	
	
    //var strSql = "select GrpProposalNo, RiskCode from LCGrpPol where PrtNo = '" + prtNo + "'";
    arrGrpRisk  = easyExecSql(strSql);

    //ͨ���б�����������ҵ�����
    for (i=0; i<arrGrpRisk.length; i++) {
		
		var sqlid6="ProposalInputSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(prtNo);//ָ������Ĳ���
	   strSql=mySql6.getString();	
		
//      strSql = "select SubRiskFlag from LMRiskApp where RiskCode = '"
//             + arrGrpRisk[i][1] + "' and RiskVer = '2002'";
      var riskDescribe = easyExecSql(strSql);

      if (riskDescribe == "M") {
        top.mainRisk = arrGrpRisk[i][1];
        break;
      }
    }
  }

  //��ȡѡ������ֺͼ���Ͷ��������
  for (i=0; i<arrGrpRisk.length; i++) {
    if (arrGrpRisk[i][1] == riskCode) {
      fm.all("RiskCode").value = arrGrpRisk[i][1];
      fm.all("GrpPolNo").value = arrGrpRisk[i][0];

      if (arrGrpRisk[i][1] == top.mainRisk) {
        //top.mainPolNo = "";
        mainRiskPolNo ="";
      }

      findFlag = true;
      break;
    }
  }

  if (arrGrpRisk.length > 1) {
    fm.all("RiskCode").className = "code";
    fm.all("RiskCode").readOnly = false;
  }
  else {
    fm.all("RiskCode").onclick = "";
  }

  return findFlag;
}

/**
 * �������֤�����ȡ��������
 */
function grpGetBirthdayByIdno() {
  var id = fm.all("IDNo").value;

  if (fm.all("IDType").value == "0") {
    if (id.length == 15) {
      id = id.substring(6, 12);
      id = "19" + id;
      id = id.substring(0, 4) + "-" + id.substring(4, 6) + "-" + id.substring(6);
      fm.all("Birthday").value = id;
    }
    else if (id.length == 18) {
      id = id.substring(6, 14);
      id = id.substring(0, 4) + "-" + id.substring(4, 6) + "-" + id.substring(6);
      fm.all("Birthday").value = id;
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
		mySql7.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(tPolNo);//ָ������Ĳ���
		mySql7.addSubPara(cRiskCode);//ָ������Ĳ���
	   var strSql=mySql7.getString();	
	
//    var strSql = "select RiskCode from LCGrpPol where GrpProposalNo = '" + tPolNo
//               + "' and trim(RiskCode) in trim((select Code1 from LDCode1 where Code = '" + cRiskCode + "'))";
    return true;
  }
  else {
  	
			var sqlid8="ProposalInputSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
		mySql8.addSubPara(tPolNo);//ָ������Ĳ���
		mySql8.addSubPara(cRiskCode);//ָ������Ĳ���
	   var strSql=mySql8.getString();	
	
//    var strSql = "select RiskCode from LCPol where PolNo = '" + tPolNo
//               + "' and trim(RiskCode) in trim((select Code1 from LDCode1 where Code = '" + cRiskCode + "'))";
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
function addRisk()
{
 parent.fraInterface.window.location.href="ProposalInput.jsp?LoadFlag=1&ContType=1&scantype=null&MissionID=00000000000000008139&riskcode="+fm.RiskCode.value;
 return;
}
function addAppRisk()
{
//alert("ok");
}

function showInvestPlanRateGrid(riskcode) {
	if ("IBL00" == riskcode) {
		document.getElementById("titleInvestPlanRate").innerHTML=""+"ÿ�궨�ڹ���������"+" "+"(���ڻ�������"+" + "+"���ڶ��⹩��)"+"";
		document.getElementById("titleInvestPlanRate2").innerHTML=""+"�Ƕ��ڹ���������"+"";
		initGridInvestPlanRate();
		document.getElementById("titleInvestPlanRate2").style.display="";
		document.getElementById("tableInvestPlanRate2").style.display="";
	  	var mySql89=new SqlClass();
	  	mySql89.setResourceName("app.ProposalInputSql")
	  	mySql89.setSqlId("ProposalInputSql89")
	  	mySql89.addSubPara(tPolNo)
	  	var tempSQL89=mySql89.getString();
	  	turnPage.queryModal(tempSQL89,GridInvestPlanRate2);	
	}
	else if ("IBL04"==riskcode) {
		document.getElementById("titleInvestPlanRate").innerHTML=""+"���ڻ������ѻ������"+"";
		document.getElementById("titleInvestPlanRate2").innerHTML=""+"���Ᵽ�ѻ������"+"";
		initGridInvestPlanRate();
		document.getElementById("titleInvestPlanRate2").style.display="";
		document.getElementById("tableInvestPlanRate2").style.display="";
	  	var mySql89=new SqlClass();
	  	mySql89.setResourceName("app.ProposalInputSql")
	  	mySql89.setSqlId("ProposalInputSql89")
	  	mySql89.addSubPara(tPolNo)
	  	var tempSQL89=mySql89.getString();
	  	turnPage.queryModal(tempSQL89,GridInvestPlanRate2);	
	}
}

/*********************************************************************
 *  ���ݲ�ͬ������,��ȡ��ͬ�Ĵ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getRiskInput(cRiskCode, cFlag) {
    var tPolNo = "";
//  var tRiskName = fm.RiskCodeName.value;
    convertFlag(cFlag);
	var tProposalContNo = fm.ProposalContNo.value;
    var urlStr = "";
    // �״ν��뼯���¸���¼��
	
		var sqlid9="ProposalInputSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
		mySql9.addSubPara(cRiskCode);//ָ������Ĳ���
	   var strSql=mySql9.getString();	
	
    //var rSql = "select risksortvalue from lmrisksort where risksorttype='00' and riskcode='"+cRiskCode+"'";
    var RiskInterface = easyExecSql(strSql);

    if (LoadFlag == "5" && scantype == "scan")
    {
        mRollSpeedOperator = fm.RollSpeedOperator.value;
        mRollSpeedSelf = fm.RollSpeedSelf.value;
        mRollSpeedBase = fm.RollSpeedBase.value;
    }
    if( mGrpFlag == "0" )       // ����Ͷ����
    {
    	urlStr = "../riskinput/Risk" + RiskInterface+ ".jsp?riskcode="+ cRiskCode+"&ProposalContNo="+tProposalContNo;
    	//���ж�ƽ̨�Ƿ�������
    	var SQL = "select count(1) from lmRiskTypeTemplate a,lmriskshowcol b where a.templateid=b.templateid and a.risktype='"+cRiskCode+"'";
    	 var PDT_FLAG = easyExecSql(SQL);
    	 if(PDT_FLAG[0][0]!=0)
    	 {
    	 	urlStr = "../riskinput/RiskPDT.jsp?riskcode="+ cRiskCode+"&ProposalContNo="+tProposalContNo;
    	 	}
    	}
        
    if( mGrpFlag == "1" )       // �����¸���Ͷ����
        urlStr = "../riskgrp/Risk" + RiskInterface+ ".jsp?riskcode="+ cRiskCode+"&ProposalContNo="+tProposalContNo;
    if( mGrpFlag == "3" )       // ����Ͷ��������
    	{
    	urlStr = "../riskinput/Risk" + RiskInterface+ ".jsp?riskcode="+ cRiskCode+"&ProposalContNo="+tProposalContNo;
    	//���ж�ƽ̨�Ƿ�������
    	var SQL = "select count(1) from lmRiskTypeTemplate a,lmriskshowcol b where a.templateid=b.templateid and a.risktype='"+cRiskCode+"'";
    	 var PDT_FLAG = easyExecSql(SQL);
	    	 if(PDT_FLAG[0][0]!=0)
	    	 {
    	 	urlStr = "../riskinput/RiskPDT.jsp?riskcode="+ cRiskCode+"&ProposalContNo="+tProposalContNo;
    	 	}
    	}

    //urlStr = "../riskgrp/Risk212403.jsp?riskcode="+ cRiskCode;
    var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";

        //add by Marlone 20060710 �Ȳ�ѯ�������ƣ������棬�ں���Խ����ϵĿؼ����¸�ֵ���������ݻᶪʧ��ԭ����
    var mSql = "select riskname||'-'||riskenname from lmrisk where riskcode='"+cRiskCode+"'";
    var mRiskName = easyExecSql(mSql);
    //��ȡ���ֵĽ�������
    showInfo = window.showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;dialogTop:-800;dialogLeft:-800;resizable=1");
    fm.all("RiskCodeName").value = mRiskName;
    //add by Marlone 20060710 �������������¸�ֵ
    if(LoadFlag=="1"||LoadFlag=="25"){         //Ŀǰֻ�Ը���Ͷ����ֱ��¼��������
        fm.all("RiskCodeName").value = mRiskName;
    }
        
    
    if(isFATCARiskcode(cRiskCode)){
    	try{
    		fm.FATCAInfoInput.style.display="";//
    		if(LoadFlag=="6"){
   			 fm.FATCAInfoInput.value=""+"FATCA��Ϣ��ѯ"+"";//
   		 	}
    	}catch(ex){}
    	try{
    		fm.FATCAInfoInput1.style.display="";//
    		if(LoadFlag=="6"){
    		fm.FATCAInfoInput1.value=""+"FATCA��Ϣ��ѯ"+"";//
    		}
    	}catch(ex){}
    	try{
    		fm.FATCAInfoInput2.style.display="";//
    		if(LoadFlag=="6"){
    		fm.FATCAInfoInput2.value=""+"FATCA��Ϣ��ѯ"+"";//
    		}
    	}catch(ex){}
    }else{
    	try{
    	fm.FATCAInfoInput.style.display="none";//
    	}catch(ex){}
    	try{
    	fm.FATCAInfoInput1.style.display="none";//
    	}catch(ex){}
    	try{
    	fm.FATCAInfoInput2.style.display="none";//
    	}catch(ex){}
    }
    
   //    alert(cRiskCode);
//    alert("�ж��Ƿ��Ǳ�������������ʾ�������ʰ�ť");
//    alert(isBFRZRiskcode(cRiskCode));
    // �ж��Ƿ��Ǳ�������������ʾ�������ʰ�ť 
    if(isBFRZRiskcode(cRiskCode)){
    	try{
//    		fm.MortgageTransfer1.style.display="";
    		fm.MortgageTransfer1.style.display="none";
    	}catch(ex){}
    	try{
//    		fm.MortgageTransfer2.style.display="";
    		fm.MortgageTransfer2.style.display="none";
    	}catch(ex){}
    	try{
//    		fm.MortgageTransfer3.style.display="";
    		fm.MortgageTransfer3.style.display="none";
    	}catch(ex){}
    	try{
//    		fm.MortgageTransfer4.style.display="";
    		fm.MortgageTransfer4.style.display="none";
    	}catch(ex){}
    	try{
//    		fm.MortgageTransfer10.style.display="";
    		fm.MortgageTransfer10.style.display="none";
    	}catch(ex){}
    	try{
//    		fm.MortgageTransfer12.style.display="";
    		fm.MortgageTransfer12.style.display="none";
    	}catch(ex){}
    	try{
//    		fm.MortgageTransfer121.style.display="";//�˹��˱�������ϸ��ѯ��ʾ
    		fm.MortgageTransfer121.style.display="none";
    	}catch(ex){}
    	try{
    		fm.PremFinanc.value=ContPremFinanc;
    	}catch(ex){}
    }else{
    	try{
    		fm.MortgageTransfer1.style.display="none";
    	}catch(ex){}
    	try{
    		fm.MortgageTransfer2.style.display="none";
    	}catch(ex){}
    	try{
   		 	fm.MortgageTransfer3.style.display="none";
    	}catch(ex){}
    	try{
   	 		fm.MortgageTransfer4.style.display="none";
    	}catch(ex){}
    	try{
    		fm.MortgageTransfer10.style.display="none";
    	}catch(ex){}
    	try{
    		fm.MortgageTransfer12.style.display="none";
    	}catch(ex){}
    	try{
    		fm.MortgageTransfer121.style.display="none";//�˹��˱�������ϸ��ѯ��ʾ
    	}catch(ex){}
    }
    
	  
    //alert('1');
    //.setAttribute('title',\"" + newData + "\")
		//fm.all('RiskCode').setAttribute = ('readonly','');
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

  if(LoadFlag=="1"){
  ////У���ǲ������� add by yaory
//  strSql = "select subriskflag from lmriskapp where riskcode='"+cRiskCode+"'";
//    var mark = easyExecSql(strSql);

    showDiv(inputButton, "true");
    showDiv(divInputContButton, "true"); //rollback by yaory
  	fm.riskbutto.style.display="none";
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
          fm.all('MainPolNo').value=mainRiskPolNo;
        }
		
		var sqlid10="ProposalInputSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
		mySql10.addSubPara(cRiskCode);//ָ������Ĳ���
		mySql10.addSubPara(aGrpContNo);//ָ������Ĳ���
	    strSql=mySql10.getString();	
		
      //  strSql = "select PayIntv from LCGrpPol where RiskCode = '" + cRiskCode + "' and GrpContNo ='"+aGrpContNo+"'";
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
    getContInput();

    }


    if (LoadFlag == "3") {
      fm.all("SaleChnl").readOnly = false;
    fm.all("SaleChnl").className = "code";
    fm.all("SaleChnl").ondblclick= showSaleChnl;
    showDiv(inputButton, "true");
    divApproveModifyContButton.style.display="";
    }

  if(LoadFlag=="4"){
    //showDiv(inputQuest, "true");
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
    showDiv(divBqNSButton, "true");
  }

  if(LoadFlag=="8"){
  	//20110920,cmj,��ʾ���滹���޸�
  	var ModifyFlag=mSwitch.getVar("ModifyFlag");
    if(ModifyFlag=="Y"){
    	showDiv(modifyButton, "true");
    }else{
    	showDiv(inputButton, "true");
    }
    
    showDiv(divInputContButtonBQBack, "true");
    var rRiskName = "select riskname from lmrisk where  riskcode='"+cRiskCode+"'";
    var RiskName = easyExecSql(rRiskName);
    if(RiskName==null)
    {
   	  myAlert(""+"��ѯ��������ʧ��"+"");
   	  return false;
    }else
   	{
   		fm.RiskCodeName.value=RiskName[0][0];  		
   	}
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
    //showDiv(inputButton, "true");
    //showDiv(divInputContButton, "true");
    showDiv(divUWContButton,"true");
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
    fm.all("SaleChnl").readOnly = false;
    fm.all("SaleChnl").className = "code";
    fm.all("SaleChnl").ondblclick= showSaleChnl;

    //��ɨ���¼��
    if (typeof(type)!="undefined" && type=="noScan") {
//      fm.all("PrtNo").readOnly = false;
//      fm.all("PrtNo").className = "common";

      //ͨ���б�����������ҵ�����
	  
	  		var sqlid11="ProposalInputSql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
		mySql11.addSubPara(cRiskCode);//ָ������Ĳ���
		mySql11.addSubPara(aGrpContNo);//ָ������Ĳ���
	    strSql=mySql11.getString();	
	  
//      strSql = "select SubRiskFlag from LMRiskApp where RiskCode = '"
//             + cRiskCode + "' ";
      var riskDescribe = easyExecSql(strSql);

//      if (riskDescribe == "M") {
//        top.mainPolNo = "";
//      }

    }
    if(scantype=="scan") {
      //fm.all("PrtNo").value = prtNo;
      fm.all("RiskCode").value=cRiskCode;
      ScanViewCreate();
      setFocus();
      
    }
    
  }
  catch(e){}
    try{

        fm.all("RiskCode").value=cRiskCode;
    	getContInput();
    	
    }catch(e){}
    try{getContInputnew();}catch(e){}
    //�������ֺ�Ͷ��������Ϣ
    fm.all("RiskCode").value = cRiskCode;
    showRiskParamCodeName();
//  try{
//      fm.all("RiskCodeName").value = tRiskName;
//  }catch(ex){}
        //alert(fm.RiskCode.value);
    //�������ƶ���Ͷ�����ţ��Է����涯¼��
    //fm.all("PrtNo").focus();

  //��ʼ���������������
  try {
    //alert("prtNo=="+prtNo);
    prtNo = fm.all("PrtNo").value;
    var riskType = prtNo.substring(2, 4);
    //tongmeng 2009-04-24 Modify
    //�˴��߼�������
    /*if (riskType == "11") {
        fm.all("SaleChnl").value = "02";
    }
    else if (riskType == "12") {
        fm.all("SaleChnl").value = "01";
    }
    else if (riskType == "15") {
        fm.all("SaleChnl").value = "03";
    }
    else if (riskType == "16") {
      fm.all("SaleChnl").value = "02";
      fm.all("SaleChnl").readOnly = false;
      fm.all("SaleChnl").className = "code";
      fm.all("SaleChnl").ondblclick= showSaleChnl;
    }*/
  }
  catch(e) {}
// if (!(typeof(top.type)!="undefined" && (top.type=="ChangePlan" || top.type=="SubChangePlan"))) {
//   //���Ƿ�ָ����Ч������¼��ʱʧЧ
//   fm.all("SpecifyValiDate").readOnly = true;
//   fm.all("SpecifyValiDate").className = "readOnly";
//   fm.all("SpecifyValiDate").ondblclick = "";
//   fm.all("SpecifyValiDate").onkeyup = "";
//   //fm.all("SpecifyValiDate").value = "N";
//   fm.all("SpecifyValiDate").tabIndex = -1;
// }
  if(mainRiskPolNo==""){
    mainRiskPolNo=parent.VD.gVSwitch.getVar("mainRiskPolNo");
  }
  if( mCurOperateFlag == "1" || mCurOperateFlag=="2") {             // ¼��
        // �����¸���Ͷ����
        if( mGrpFlag == "1" )   {
            getGrpPolInfo();                       // ���뼯�岿����Ϣ
            //getPayRuleInfo();  //hl

            //֧�ּ����¸��ˣ�¼�����֤�Ŵ�����������
            fm.all("IDNo").onblur = grpGetBirthdayByIdno;
            //��ʱ��ȥ��ȥ���������ť,�涯����ʱ����   hl
//          if(LoadFlag!="99")
//          inputQuest.style.display = "none";

            // ��ȡ�ü���������������Ϣ
            //alert("judging if the RiskCode input has been input in group info.");
            //if (!queryGrpPol(fm.all("PrtNo").value, cRiskCode))   {
            //  alert("�����ŵ�û��¼��������֣������¸��˲�����¼�룡");
            //  fm.all("RiskCode").value="";
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
      //tongmeng 2008-07-02 ADD
      //֧�ֶ�����¼��
      //alert('tm test');
       showMultRiskGrid(cRiskCode);
       
       
       
        //tongmeng
   // alert("fuhe");
    if(ifTLrisk(cRiskCode))
	  {  
	  	
	  	try{
	  //		  alert("fuhe1");
		      //DivChooseDuty.style.display = "none";
			    //DutyGrid.checkBoxAll();
			    //tongmeng 2010-11-16 Modify
			    //ֻ�б�����Ĳ�Ʒ�ų�ʼ��Ͷ�ʽ���
			    //alert("11111"+tProposalContNo);
		//	      alert("fuhe2");
			    if(ifSavedRiskCode(cRiskCode))
			    {
			    	divInvestPlanRate.style.display="";
			    	fm.riskbutto.style.display="";
			    	fm.riskbutto5.style.display="";
			    	fm.riskbutto6.style.display="";
			     showDetailForCont2(cRiskCode);	
			    	InvestPlanInputInit(1);	
			    	showInvestPlanRateGrid(cRiskCode);
			  	}
			  	else
			  	{
			  		divInvestPlanRate.style.display="none";
			  		fm.riskbutto.style.display="none";
			  	}
			    
			   }
			   catch(e)
			   {
			   	}
					
		//			  alert("fuhe3");
					//alert("3111");
								
	  }	      	  
	  
     //  alert('8');
        fm.all('RiskCode').readOnly= true;
   // fm.all('RiskCode').setAttribute = ('readonly','true');
        //���������ڳ�ʼ��ʱ�����⴦����չ-guoxiang add at 2004-9-6 16:33
    if(initDealForSpecRiskEx(cRiskCode)==false)
        {
            myAlert(""+"���֣�"+""+cRiskCode+""+"��ʼ��ʱ���⴦��ʧ�ܣ�"+"");
            return false;
        }
        try{
          fm.all('SelPolNo').value=parent.VD.gVSwitch.getVar("PolNo");
          if (fm.all('SelPolNo').value=='false')
          {
            fm.all('SelPolNo').value='';
          }
          if(fm.all('SelPolNo').value!=''){ //��������
          
		var sqlid12="ProposalInputSql12";
		var mySql12=new SqlClass();
		mySql12.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
		mySql12.addSubPara(fm.all('SelPolNo').value);//ָ������Ĳ���
	    var tSql=mySql12.getString();	
		  
           // var tSql="select riskcode from lcpol where polno='"+fm.all('SelPolNo').value+"'";
            var arr=easyExecSql(tSql);
            if(arr[0]!=cRiskCode){
              fm.all('SelPolNo').value='';
            }
          }
      }
      catch(ex) {}
        if( isSubRisk( cRiskCode ) == true&&fm.all('SelPolNo').value=="" ) {   // ����
          //��������������գ�����ջ�������ձ�����
          if (cFlag != "8" && cFlag != "2") {
              //top.mainPolNo = ""; //hezy add
              mainRiskPolNo = "";
            }
            //edit by yaory tPolNo = getMainRiskNo(cRiskCode);   //����¼�븽�յĴ���,�õ����ձ�������
            //alert("tPolNo:"+tPolNo);
//edit by yaory         if (!checkRiskRelation(tPolNo, cRiskCode)) {
//            alert("�����հ�����ϵ������������պŲ��ܴ���������գ�");
//            gotoInputPage();
//            //top.mainPolNo = "";
//            mainRiskPolNo = "";
//            return false;
//          }
//-----------------------------------------------------------------------
         if(cRiskCode=='121301'||cRiskCode=='321601')//��ʼ������ĸ�����Ϣ--houzm���--�ɵ������Ϊһ������
         {
            //  if(cRiskCode=='121301')
            //  {
                    //          if (!initPrivateRiskInfo121301(tPolNo))
                    //          {
                    //            gotoInputPage();
                    //            return false;
                    //          }
                    //  }
                    //  if(cRiskCode=='321601')
            //  {
                    //          if (!initPrivateRiskInfo321601(tPolNo))
                    //          {
                    //            gotoInputPage();
                    //            return false;
                    //          }
                    //  }
         }
         else
         {

                ////��ʼ��������Ϣ
                        //if (!initPrivateRiskInfo(tPolNo))
                        //{
                        //  gotoInputPage();
                        //  return false;
                        //}
         }

//          try {}  catch(ex1) { alert( "��ʼ�����ֳ���" + ex1 );   }
        }
        //��ʼ���ۿ���Ϣ
          Discount();
          
        return false;

    }

	//fm.ProposalContNo.value=tProposalContNo;
    mCurOperateFlag = "";
    mGrpFlag = "";
    
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
  
  		var sqlid13="ProposalInputSql13";
		var mySql13=new SqlClass();
		mySql13.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql13.setSqlId(sqlid13);//ָ��ʹ�õ�Sql��id
		mySql13.addSubPara(cRiskCode);//ָ������Ĳ���
	    var tSql13=mySql13.getString();	
  
  var arrQueryResult = easyExecSql(tSql13, 1, 0);
  //var arrQueryResult = easyExecSql("select SubRiskFlag from LMRiskApp where RiskCode='" + cRiskCode + "'", 1, 0);

    if(arrQueryResult[0] == "S")    //��Ҫת�ɴ�д
        return true;
    if(arrQueryResult[0] == "M")
        return false;

    if (arrQueryResult[0].toUpperCase() == "A")
    {
        if (confirm(""+"�����ּȿ���������"+","+"�ֿ����Ǹ���!ѡ��ȷ����������¼��"+","+"ѡ��ȡ�����븽��¼��"+""))
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
  if (typeof(mainRiskPolNo)!="undefined" && mainRiskPolNo!="") {
    //tPolNo = top.mainPolNo;
    tPolNo = mainRiskPolNo;
  }
  else{
    tPolNo = window.showModalDialog(urlStr,tPolNo,"status:no;help:0;close:0;dialogWidth:310px;dialogHeight:100px;center:yes;");
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
        myAlert(""+"û�����ձ�����"+","+"���ܽ��и�����¼��!"+"");
        mCurOperateFlag="";        //��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.
        return false
    }

    var arrLCPol = new Array();
    var arrQueryResult = null;
    // ��������Ϣ����
	
	  	var sqlid14="ProposalInputSql14";
		var mySql14=new SqlClass();
		mySql14.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql14.setSqlId(sqlid14);//ָ��ʹ�õ�Sql��id
		mySql14.addSubPara(cPolNo);//ָ������Ĳ���
	    var sql=mySql14.getString();	
	
//    var sql = "select * from lcpol where polno='" + cPolNo + "' "
//            + "and riskcode in "
//            + "( select riskcode from LMRiskApp where SubRiskFlag = 'M' )";

    //alert(sql);
    arrQueryResult = easyExecSql( sql , 1, 0);

    if (arrQueryResult == null) {
        mCurOperateFlag="";        //��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.

        //top.mainPolNo = "";
        mainRiskPolNo = "";

        myAlert(""+"��ȡ������Ϣʧ��"+","+"���ܽ��и�����¼��!"+"");
        return false
    }

    arrLCPol = arrQueryResult[0];
    displayPol( arrLCPol );

    fm.all("MainPolNo").value = cPolNo;
    var tAR;

    //Ͷ������Ϣ
    if (arrLCPol[6]=="2") {     //����Ͷ������Ϣ
      arrQueryResult = null;
	  
	  	 var sqlid15="ProposalInputSql15";
		var mySql15=new SqlClass();
		mySql15.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql15.setSqlId(sqlid15);//ָ��ʹ�õ�Sql��id
		mySql15.addSubPara(arrLCPol[0]);//ָ������Ĳ���
		mySql15.addSubPara(arrLCPol[28]);//ָ������Ĳ���
	    var sql15=mySql15.getString();	
		
	   arrQueryResult = easyExecSql(sql15, 1, 0);
     // arrQueryResult = easyExecSql("select * from lcgrpappnt where grpcontno='"+arrLCPol[0]+"'"+" and customerno='"+arrLCPol[28]+"'", 1, 0);
      tAR = arrQueryResult[0];
      displayPolAppntGrp(tAR);
    } else {                     //����Ͷ������Ϣ
      arrQueryResult = null;
	  
	  	 var sqlid16="ProposalInputSql16";
		var mySql16=new SqlClass();
		mySql16.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql16.setSqlId(sqlid16);//ָ��ʹ�õ�Sql��id
		mySql16.addSubPara(cPolNo);//ָ������Ĳ���
		mySql16.addSubPara(arrLCPol[28]);//ָ������Ĳ���
	    var sql16=mySql16.getString();	
	  
	  arrQueryResult = easyExecSql(sql16, 1, 0);
      //arrQueryResult = easyExecSql("select * from lcappntind where polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[28]+"'", 1, 0);
      tAR = arrQueryResult[0];
      displayPolAppnt(tAR);
    }

    // ��������Ϣ����
    if (arrLCPol[21] == arrLCPol[28]) {
      fm.all("SamePersonFlag").checked = true;
        parent.fraInterface.isSamePersonQuery();
    parent.fraInterface.fm.all("CustomerNo").value = arrLCPol[21];
    }
    //else {
        arrQueryResult = null;
		
		var sqlid17="ProposalInputSql17";
		var mySql17=new SqlClass();
		mySql17.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql17.setSqlId(sqlid17);//ָ��ʹ�õ�Sql��id
		mySql17.addSubPara(arrLCPol[2]);//ָ������Ĳ���
		mySql17.addSubPara(arrLCPol[21]);//ָ������Ĳ���
	    var sql17=mySql17.getString();	
		
		arrQueryResult = easyExecSql(sql17, 1, 0);
        //arrQueryResult = easyExecSql("select * from lcinsured where contno='"+arrLCPol[2]+"'"+" and insuredno='"+arrLCPol[21]+"'", 1, 0);
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
  		if(fm.PayIntv.value!=ABPayIntv){
			myAlert(""+"�����սɷ�Ƶ�α���������һ�£�"+"");
			return false;
		}
		
    	if (!confirm(""+"ָ���ĸ�������Ч����Ϊ��("+"" + ABCValiDate + ""+")��ȷ����"+"")) {
      		return false;
    	}
    	//����ProposalBL��ȥ���˶Ա�ȫ��У�飬Ҫ���������¼��� ����x��PDA�aƷ���ܸ���WP & PB �����U, �����΁K�]��PDA modify by liushuo
    	if(checkPDA()) {
    		if(!confirm(""+"���ѡ��pda��Ʒ���ܸ���wp��pb������"+","+"�Ƿ������"+"")) {
    			return false;
    		}
    	}
  	}


   if(needVerifyRiskcode()==true)
   {
        //if(verifyInput() == false) passVerify = false;
		//alert("5555555");
        BnfGrid.delBlankLine();

        if(BnfGrid.checkValue("BnfGrid") == false) passVerify = false;

         //У�鵥֤�Ƿ񷢷Ÿ�ҵ��Ա
         if (!verifyPrtNo(fm.all("PrtNo").value)) passVerify = false;
    }
    try {
      var strChkIdNo = "";

          //��������Ա�У�����֤��
          if (fm.all("AppntIDType").value == "0")
            strChkIdNo = chkIdNo(fm.all("AppntIDNo").value, fm.all("AppntBirthday").value, fm.all("AppntSex").value);
          if (fm.all("IDType").value == "0")
            strChkIdNo = chkIdNo(fm.all("IDNo").value, fm.all("Birthday").value, fm.all("Sex").value);

          if (strChkIdNo != "") {
            myAlert(strChkIdNo);
            passVerify = false;


      }

      //У��ְҵ��ְҵ����
//    var arrCode = new Array();
//    arrCode = verifyCode("ְҵ�����֣�", fm.all("AppntWorkType").value, "code:OccupationCode", 1);
//    if (arrCode!=true && fm.all("AppntOccupationCode").value!=arrCode[0]) {
//      alert("Ͷ����ְҵ��ְҵ���벻ƥ�䣡");
//      passVerify = false;
//    }
//    arrCode = verifyCode("ְҵ�����֣�", fm.all("WorkType").value, "code:OccupationCode", 1);
//    if (arrCode!=true && fm.all("OccupationCode").value!=arrCode[0]) {
//      alert("������ְҵ��ְҵ���벻ƥ�䣡");
//      passVerify = false;
//    }

      //У���������
      var i;
      var sumLiveBnf = new Array();
      var sumDeadBnf = new Array();
			//tongmeng 2007-12-26 Modify
			//�޸�������У��
			var sumLiveBnf_final = 0;
			var sumDeadBnf_final = 0; 
			var LiveBnfFlag=false;
			var DeadBnfFlag=false;

      for (i=0; i<BnfGrid.mulLineCount; i++)
      {
      	//tongmeng 2007-12-28 add
      	//������������˵�У��
      	
    //  	alert(i);
		//		alert(BnfGrid.getRowColData(i, 1));
  //      BnfGrid.setRowColData(i, 1, "1");

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
					//Ĭ��Ϊ���������
					//BnfGrid.setRowColData(i, 1, "1");
					//alert(2007);
				}
				//alert(BnfGrid.getRowColData(i, 1)+":"+BnfGrid.getRowColData(i, 6));
				//
				if(BnfGrid.getRowColData(i, 1) == "1")
				{	
					if(BnfGrid.getRowColData(i, 6)=="00")
					{
						myAlert(''+"��������˲����Ǳ���!"+'');
						return false;
					}
				}
				//tongmeng 2008-12-18 Modify
				//�޸�У������������
        if (BnfGrid.getRowColData(i, 1) == "0")
        {
        	LiveBnfFlag = true;
        	
          if (typeof(sumLiveBnf[parseInt(BnfGrid.getRowColData(i, 7))]) == "undefined")
            sumLiveBnf[parseInt(BnfGrid.getRowColData(i, 7))] = 0;
          sumLiveBnf[parseInt(BnfGrid.getRowColData(i, 7))] = sumLiveBnf[parseInt(BnfGrid.getRowColData(i, 7))] + parseFloat(BnfGrid.getRowColData(i, 8))*100;
          
          //sumLiveBnf_final = sumLiveBnf_final + parseFloat(BnfGrid.getRowColData(i, 8))*100;
        }
        else if (BnfGrid.getRowColData(i, 1) == "1")
        {
        	//alert("6666666");
          if (typeof(sumDeadBnf[parseInt(BnfGrid.getRowColData(i, 7))]) == "undefined")
          sumDeadBnf[parseInt(BnfGrid.getRowColData(i, 7))] = 0;
          sumDeadBnf[parseInt(BnfGrid.getRowColData(i, 7))] = sumDeadBnf[parseInt(BnfGrid.getRowColData(i, 7))] + parseFloat(BnfGrid.getRowColData(i, 8))*100;
        	
        	//sumDeadBnf_final = sumDeadBnf_final + parseFloat(BnfGrid.getRowColData(i, 8))*100;
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
						myAlert(''+"���������������˱���Ϊ����!"+'');
						return false;
					}
				}
          }
      }
      
      var tempcode=fm.RiskCode.value;
   //add by yaory ����������ֶ�Ϳ��Դ���1
	//alert(sumLiveBnf_final+":"+sumDeadBnf_final);
	if(tempcode =="314301")
	{
		if(LiveBnfFlag)
		{
			myAlert(""+"������ֻ�������������!"+"");
			passVerify = false;
			return false;
		}
	}
	//û��������ε� ������ ¼�����������
	if(tempcode =="111801"||tempcode =="121301"||tempcode =="121501"||tempcode =="121704"||
		tempcode =="121705"||tempcode =="121801"||tempcode =="131601"||tempcode =="121505"||
		tempcode =="121504"||tempcode =="111802"||tempcode =="121507"||tempcode =="121703"||
		tempcode =="121506"||tempcode =="121701"||tempcode =="121702" )
	{
		if(DeadBnfFlag)
		{
			myAlert(""+"������ֻ��������������!"+"");
			passVerify = false;
			return false;
		}
	}
   if(tempcode!="00150000" && tempcode!="00146000" && tempcode!="00144000")
   {
   	 /*if(LiveBnfFlag)
   	 {
   	 if(parseFloat(sumLiveBnf_final)/100>1)
   	 {
   	 	     alert("���������˵����������Ϊ��" + sumLiveBnf_final+ " ������100%�������ύ��");

            passVerify = false;
            return false;

   	 	}
   	 	else if(parseFloat(sumLiveBnf_final)/100<1)
   	 	{
   	 		   alert("���������˵����������Ϊ��" + sumLiveBnf_final+ " ��С��100%�������ύ��");

            passVerify = false;
            return false;

   	 	}
   	}
   	 if(DeadBnfFlag)
   	 {
   	 	if(parseFloat(sumDeadBnf_final)/100>1)
   	 {
   	 	     alert("��������˵����������Ϊ��" + sumDeadBnf_final+ " ������100%�������ύ��");

            passVerify = false;
            return false;

   	 	}
   	 	else if(parseFloat(sumDeadBnf_final)/100<1)
   	 	{
   	 		   alert("��������˵����������Ϊ��" + sumDeadBnf_final+ " ��С��100%�������ύ��");

            passVerify = false;
            return false;

   	 	}
   	}*/
   	
      for (i=0; i<sumLiveBnf.length; i++)
      {
        if (typeof(sumLiveBnf[i])!="undefined"){sumLiveBnf[i]=parseFloat(sumLiveBnf[i])/100;}
        if (typeof(sumLiveBnf[i])!="undefined" && sumLiveBnf[i]>1)
        {
            myAlert(""+"��������������˳��"+" " + i + " "+"�����������Ϊ��"+"" + sumLiveBnf[i]+ " "+"������100"+"%"+"�������ύ��"+"");

            passVerify = false;
            return false;
        }
        else if (typeof(sumLiveBnf[i])!="undefined" && sumLiveBnf[i]<1)
        {
            myAlert(""+"ע�⣺��������������˳��"+" " + i + " "+"�����������Ϊ��"+"" + sumLiveBnf[i] + " "+"��С��100"+"%");

            passVerify = false;
            return false;
        }
      }

      for (i=0; i<sumDeadBnf.length; i++)
      {
        if (typeof(sumDeadBnf[i])!="undefined"){sumDeadBnf[i]=parseFloat(sumDeadBnf[i])/100;}
        if (typeof(sumDeadBnf[i])!="undefined" && sumDeadBnf[i]>1)
        {

          myAlert(""+"��������������˳��"+" " + i + " "+"�����������Ϊ��"+"" + sumDeadBnf[i] + " "+"������100"+"%"+"�������ύ��"+"");
          passVerify = false;
          return false;
        }
        else if (typeof(sumDeadBnf[i])!="undefined" && sumDeadBnf[i]<1)
        {
            myAlert(""+"ע�⣺��������������˳��"+" " + i + " "+"�����������Ϊ��"+"" + sumDeadBnf[i] + " "+"��С��100"+"%");
            passVerify = false;
            return false;
        }
      }
}
      if (trim(fm.BankCode.value)=="0101")
      {
        if (trim(fm.BankAccNo.value).length!=19 || !isInteger(trim(fm.BankAccNo.value)))
        {
            myAlert(""+"�������е��˺ű�����19λ�����֣����һ���Ǻţ�*����Ҫ��\n����ͻ���д������һ�����������"+"");
            passVerify = false;
        }
      }

    //У��ͻ��Ƿ�����
    if (fm.AppntCustomerNo.value!="" && isDeath(fm.AppntCustomerNo.value)) {
      myAlert(""+"Ͷ�����Ѿ�������"+"");
      passVerify = false;
    }

    if (fm.CustomerNo.value!="" && isDeath(fm.CustomerNo.value)) {
      myAlert(""+"�������Ѿ�������"+"");
      passVerify = false;
    }
    }
    catch(e) {}

    if (!passVerify) {
      if (!confirm(""+"Ͷ����¼������д����Ƿ�������棿"+"")) return false;
      else return true;
    }
}

//У��ͻ��Ƿ�����
function isDeath(CustomerNo) {
	myAlert(CustomerNo);
			var sqlid18="ProposalInputSql18";
		var mySql18=new SqlClass();
		mySql18.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql18.setSqlId(sqlid18);//ָ��ʹ�õ�Sql��id
		mySql18.addSubPara(CustomerNo);//ָ������Ĳ���
	    var strSql=mySql18.getString();	
	
  //var strSql = "select DeathDate from LDPerson where CustomerNo='" + CustomerNo + "'";
  var arrResult = easyExecSql(strSql);

  if (arrResult == ""||arrResult == null) return false;
  else return true;
}
function dospecialrisk()
{
//var code=fm.RiskCode.value;
//��ѯ������˳���

			var sqlid19="ProposalInputSql19";
		var mySql19=new SqlClass();
		mySql19.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql19.setSqlId(sqlid19);//ָ��ʹ�õ�Sql��id
		mySql19.addSubPara(prtNo);//ָ������Ĳ���
		mySql19.addSubPara(fm.CustomerNo.value);//ָ������Ĳ���
	    var rSql=mySql19.getString();	

//var rSql = "select sequenceno from lcinsured where contno='"+prtNo+"' and insuredno='"+fm.CustomerNo.value+"'";
var seque = easyExecSql(rSql);
//�ж������ǲ���00150000

			var sqlid20="ProposalInputSql20";
		var mySql20=new SqlClass();
		mySql20.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql20.setSqlId(sqlid20);//ָ��ʹ�õ�Sql��id
		mySql20.addSubPara(prtNo);//ָ������Ĳ���
	    rSql=mySql20.getString();	

//rSql="select riskcode from lcpol where contno='"+prtNo+"'  and polno=mainpolno";
var code= easyExecSql(rSql);
if(seque!=null && seque=="1" && code=="00150000")
{
    return 1;
}else{
   return 0;
}
}
/*********************************************************************
 *  �������Ͷ�������ύ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function submitForm11() {
	if (beforeSubmit()== false )  return false;
	var sqlid91="ProposalInputSql91";
	var mySql91=new SqlClass();
	mySql91.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql91.setSqlId(sqlid91);//ָ��ʹ�õ�Sql��id
	mySql91.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	
	var strArr = easyExecSql(mySql91.getString());
    var flag=0;
    if(strArr>0){
    	
    	var sqlid92="ProposalInputSql92";
    	var mySql92=new SqlClass();
    	mySql92.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql92.setSqlId(sqlid92);//ָ��ʹ�õ�Sql��id
    	mySql92.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
    	
    	var strArrPayPlanCode = easyExecSql(mySql92.getString());
    	
//    	var StrPayPlanCode ;
//    	if(strArrPayPlanCode>0){
//    		StrPayPlanCode = strArrPayPlanCode[0][0];
//    	}
//    	alert(strArrPayPlanCode);
//    	alert(StrPayPlanCode);
    	
//    	for(var i=0;i<DutyGrid.mulLineCount;i++){
//    		if(DutyGrid.getRowColData(i,1)==strArrPayPlanCode){
////    			alert(DutyGrid.getRowColData(i,1));
//    			continue;
//    		}
//    		if(DutyGrid.getRowColData(i,13)==null||DutyGrid.getRowColData(i,13)==''){
//    			flag++;
//    		}
//    	}
//	    if(flag>0){
//	    	alert('��¼�붨�ڹ���ѻ��߷Ƕ��ڹ����');
//	    	return false;
//	    }
    }
	 var tCurrencyCode = "";
	 try{
	 tCurrencyCode = fm.CurrencyCode.value;
	}
	catch(E)
	{
		 tCurrencyCode = "01";
		}
//	   if(tCurrencyCode=="" || tCurrencyCode==null){
//			alert("������Ϣ����Ϊ��");
//			return false;
//		}
	//���ӶԲ���Ҫ¼����������ֵı���Ѳ���Ϊ�յ�У��
	
	    var sqlid21="ProposalInputSql21";
		var mySql21=new SqlClass();
		mySql21.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql21.setSqlId(sqlid21);//ָ��ʹ�õ�Sql��id
		mySql21.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	    vSql=mySql21.getString();
	
	//vSql="select AmntFlag from lmduty where dutycode in (select dutycode from lmriskduty where riskcode = '"+fm.RiskCode.value+"')";
//	var tAmntFlag = easyExecSql(vSql);
//	if(tAmntFlag != null && tAmntFlag == "1"){
//		if((fm.Prem.value==""||fm.Prem.value==null)&&(fm.Amnt.value==""||fm.Amnt.value==null)){
//			alert("����Ѳ��ܶ�Ϊ�գ�");
//			return false;
//		}
//	}

	if(fm.RiskCode.value == "IBL03"){
		if(fm.PayIntv.value==0&&(fm.PlanLevel.value=="2"||fm.PlanLevel.value=="3"))
		{
			myAlert(""+"���ɷѷ�ʽΪ����ʱ�����ϼƻ�ֻ��ѡ��1"+"-"+"�ʱ��������ٱ���"+"");
			return false;
		}
		if(fm.PlanLevel.value=="1"&&fm.Prem.value==""){
			myAlert(""+"���Ѳ���Ϊ�գ�"+"");
			return false;
		}
		if((fm.PlanLevel.value=="2"||fm.PlanLevel.value=="3")&&fm.Amnt.value=="")
		{
			myAlert(""+"�����Ϊ�գ�"+"");
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
			myAlert(""+"������Ч���ڲ���Ϊ��"+"");
			return false;
		}
		
	}
     //if(dospecialrisk()==1)//add by yaory ����00150000
        //{
            //alert("����00150000�ĸ�����ֻ�ܸ��ӵ��ڶ������ˣ�");
            //return;
     //   }
    if (fm.RiskCode.value == "212403")
    {
        if (verifyInput2() == false)
            return;
    }

    //У������ۿۣ����ܴ���1С��0
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
                myAlert(""+"����������Ϣ"+" "+"��"+""+(i+1)+""+"�е����֤������������!����������!"+"");
                BnfGrid.setRowColData(i,5,"");
                BnfGrid.setFocus(i,5);
                return ;
            }
        }
 
        if (BnfGrid.getRowColData(i, 10) == "A") {
          if(ContType!="2")
          {
			//alert("7777777");
          BnfGrid.setRowColData(i, 2, fm.all("AppntName").value);
          BnfGrid.setRowColData(i, 3, fm.all("AppntSex").value);
          BnfGrid.setRowColData(i, 4, fm.all("AppntIDType").value);
          BnfGrid.setRowColData(i, 5, fm.all("AppntIDNo").value);
          BnfGrid.setRowColData(i, 6,parent.VD.gVSwitch.getVar( "RelationToInsured"));
          BnfGrid.setRowColData(i, 9, fm.all("AppntHomeAddress").value);
          //hl
          //BnfGrid.setRowColData(i, 9, fm.all("AppntNo").value);

          }
          else
          {
            myAlert(""+"Ͷ����Ϊ���壬������Ϊ�����ˣ�"+"")

          BnfGrid.setRowColData(i, 2, "");
          BnfGrid.setRowColData(i, 3, "");
          BnfGrid.setRowColData(i, 4, "");
          BnfGrid.setRowColData(i, 5, "");
          BnfGrid.setRowColData(i, 6, "");
          BnfGrid.setRowColData(i, 9, "");
          }
        }
        else if (BnfGrid.getRowColData(i,10)== "I") {
		//alert("8888888");
        BnfGrid.setRowColData(i, 2, fm.all("Name").value);
        BnfGrid.setRowColData(i, 3, fm.all("Sex").value);
        BnfGrid.setRowColData(i, 4, fm.all("IDType").value);
        BnfGrid.setRowColData(i, 5, fm.all("IDNo").value);
        BnfGrid.setRowColData(i, 6, "00");
        //BnfGrid.setRowColData(index-1, 8, fm.all("AppntHomeAddress").value);
        //hl
        BnfGrid.setRowColData(i, 9, fm.all("HomeAddress").value);
        //BnfGrid.setRowColData(i, 9, fm.all("InsuredNo").value);
    //alert("fm.all("InsuredNo")"+fm.all("InsuredNo").value);
        }
    }
    

  //-------------------------------------------------Beg
  //�޸��ˣ�chenrong
  //�޸����ڣ�2006-03-13
  //����ȡ����ֵ����ַ���
  if (setDutyKind() == "")
  {
      myAlert(""+"��ȡ����ֵ����Ϊ�գ�������!"+"");
      return;
  }
  //-------------------------------------------------End

  if(!chkDutyNo()){                  //У��CheckBox��ֻ����ͬʱѡ��һ��������
                                                                             //create by malong 2005-7-13
       return false;
    }
  

    if(!chkMult() || !checkMult())      //�жϷ����Ƿ�Ϊ�ջ����� chkMult�����ڶ���������,checkMult�����ڵ���������,
                                                                            //create by malong 2005-7-8
  {
     return false;
  }
    
  if(fm.RiskCode.value!='00332000')
  {
 if(!chkDuty()){                  //У��280��ͬ����ѡ������
                                                                             //create by zhangyang 2005-7-29
       return false;
    }
 
 if(!chkPayEndYear()){           //У��ɷѷ�ʽΪ����ʱ���������ڲ���ѡ��
       return false;                    //create by zhangyang 2005-10-21
    }    
}

	if(DiscountGrid.checkValue("DiscountGrid") == false) 
    {
       return;
    }
  
    if(sign != 0)
    {
       myAlert(""+"�벻Ҫ�ظ����!"+"");
       return;
    }

    var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+showStr;

  var verifyGrade = "1";


  //������������������⴦������
  //modify by malong 2005-7-8
  try 
  {
      if(specDealByRisk()==false)
      {  	  
    	  return;
      }

   }
   catch(e){}
   

  //����������ڣ�����գ������֤��ȡ
  //try {checkBirthday(); } catch(e){}
    // У�鱻�����Ƿ�ͬͶ���ˣ���ͬ����и���
  try { verifySamePerson(); } catch(e) {}

        // У��¼������
    if( verifyProposal() == false ) return;
    if (trim(fm.all('ProposalNo').value) != "") {
      myAlert(""+"��Ͷ�������Ѿ����ڣ��������ٴ������������½���¼����棡"+"");
      return false;
    }

    if (LoadFlag=="1") {
        mAction = "INSERTPERSON";
    }
    else {	
        mAction = "INSERTGROUP";
    }
    fm.all( 'fmAction' ).value = mAction;
    fm.action="../app/ProposalSave.jsp"
    var tDutyCode;
    try{
        if(fm.all('inpNeedDutyGrid').value==0)
        {
            tDutyCode = getDutyCode(fm.RiskCode.value);
            if (tDutyCode != null && tDutyCode != "null" && tDutyCode != "")
            {
                fm.action="../app/ProposalSave.jsp?DutyCode=" + tDutyCode;
            }
        }
    }
    catch(ex){}
    //Ϊ��ȫ���ӣ�add by Minim
    if (LoadFlag=="7" || LoadFlag=="8") {
    	if(!checkRiskCode()){
    		myAlert(""+"�������Ѵ���"+","+"�����ظ����!"+"");
    		return;
    	}
        if (tDutyCode != null && tDutyCode != "null" && tDutyCode != ""){
            fm.action="../app/ProposalSave.jsp?BQFlag=2&EdorType="+EdorType+ "&DutyCode=" + tDutyCode;
        } else {
            fm.action="../app/ProposalSave.jsp?BQFlag=2&EdorType="+EdorType;
        }
      //fm.all("BQFlag").value=BQFlag;
    }

    //Ϊ���������������ӣ�add by Minim
    if (LoadFlag=="9") {
      fm.action="../app/ProposalSave.jsp?BQFlag=4&MasterPolNo=" + parent.VD.gVSwitch.getVar('MasterPolNo');
      //alert(fm.action);return;
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
  
    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    
    fm.submit(); //�ύ
    sign = 0;
}

/**
 * ǿ�ƽ������
 */
function unLockTable() {
  if (fm.PrtNo.value == "") {
    myAlert(""+"��Ҫ��дͶ�����ţ�"+"");
    return;
  }

  var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + fm.PrtNo.value + "&CreatePos="+"�б�¼��"+"&PolState=1002&Action=DELETE";
  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1");
}

/*********************************************************************
 *  �������Ͷ�������ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
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
var urlStr="../common/jsp/MessagePage.jsp?picture=F&content="+content;
        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    }
    else
    {
        //add by yaory
//        fm.riskbutton31.disabled='';
//        fm.riskbutton32.disabled='';
        //alert("loadflag:"+LoadFlag);
    if (fm.ContType.value == "1" && cflag=="1")
    {
          if (confirm(""+"����ɹ���\nҪ�����Ͷ�����ŵ��������ø�����Ա�ܲ�����"+"")) {
            unLockTable();
          }
      }
      else {
          try{mainRiskPolNo=parent.VD.gVSwitch.getVar("mainRiskPolNo");} catch(ex){}
          if(LoadFlag == '3'){
            inputQuestButton.disabled = false;
                    //////add by yaory
//                    fm.riskbutton31.disabled=true;
//                    fm.riskbutton32.disabled=true;
          }
        content = ""+"����ɹ���"+"";
var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+content;
        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
      //fm.all('AppendRiskCode').focus();
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
        if(LoadFlag == 5){
        top.close();
        }
        //��ʱ��������Ͷ�������룬���㸽�յ�¼�룬����ѡ��ɨ�����ʧЧ
        //try { if (top.mainPolNo == "") top.mainPolNo = fm.all("ProposalNo").value } catch(e) {}
        //try { if (mainRiskPolNo == "") mainRiskPolNo = fm.all("ProposalNo").value } catch(e) {alert("err");}
    }

    //�б��ƻ������������ֹ�ĺ�������
    if (mAction=="DELETE") {
      if (typeof(top.type)!="undefined" && top.type=="SubChangePlan") {
        var tProposalNo = fm.all('ProposalNo').value;
        var tPrtNo = fm.all('PrtNo').value;
        var tRiskCode = fm.all('RiskCode').value;

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
    
    //alert("FlagStr:"+FlagStr);
    if (FlagStr == "Fail" )
    {
var urlStr="../common/jsp/MessagePage.jsp?picture=F&content="+content;
        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    }
    else
    {
    	
    	try{
			//alert(1111);
			var cRiskCode = fm.RiskCode.value;
	 if(ifTLrisk(cRiskCode))
	  {  
	  	//alert('��Ͷ������');
	  	try{
		      //DivChooseDuty.style.display = "none";
			    //DutyGrid.checkBoxAll();
			    //tongmeng 2010-11-16 Modify
			    //ֻ�б�����Ĳ�Ʒ�ų�ʼ��Ͷ�ʽ���
			    //alert("11111"+tProposalContNo);
			    if(ifSavedRiskCode(cRiskCode))
			    {
			    	divInvestPlanRate.style.display="";
			     showDetailForCont2(cRiskCode);	
			    	InvestPlanInputInit(1);
			    	showInvestPlanRateGrid(cRiskCode);
			  	}
			  	else
			  	{
			  		divInvestPlanRate.style.display="none";
			  	}
			    
			   }
			   catch(e)
			   {
			   	}
					
					//alert("3111");
								
	  }
		}
		catch(ex){}
		
        //add by yaory
//        fm.riskbutton31.disabled='';
//        fm.riskbutton32.disabled='';
    if (fm.ContType.value == "1" && cflag=="1")
    {
          if (confirm(""+"����ɹ���\nҪ�����Ͷ�����ŵ��������ø�����Ա�ܲ�����"+"")) {
            unLockTable();
          }
      }
      else {
          try{mainRiskPolNo=parent.VD.gVSwitch.getVar("mainRiskPolNo");} catch(ex){}
          if(LoadFlag == '3'){
            inputQuestButton.disabled = false;
                    //////add by yaory
//                    fm.riskbutton31.disabled=true;
//                    fm.riskbutton32.disabled=true;
          }
        //content = "����ɹ���";
var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+content;
        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
      //fm.all('AppendRiskCode').focus();
        //////add by yaory
//        fm.riskbutton31.focus();
//        fm.riskbutton32.focus();
      if (mAction=="CONFIRM") {
        window.location.href("./ContPolInput.jsp");
          }
    }

        //��ʱ��������Ͷ�������룬���㸽�յ�¼�룬����ѡ��ɨ�����ʧЧ
        //try { if (top.mainPolNo == "") top.mainPolNo = fm.all("ProposalNo").value } catch(e) {}
        //try { if (mainRiskPolNo == "") mainRiskPolNo = fm.all("ProposalNo").value } catch(e) {alert("err");}
    }

    //�б��ƻ������������ֹ�ĺ�������
    if (mAction=="DELETE") {
      if (typeof(top.type)!="undefined" && top.type=="SubChangePlan") {
        var tProposalNo = fm.all('ProposalNo').value;
        var tPrtNo = fm.all('PrtNo').value;
        var tRiskCode = fm.all('RiskCode').value;

        parent.fraTitle.window.location = "./ChangePlanSubWithdraw.jsp?polNo=" + tProposalNo + "&prtNo=" + tPrtNo + "&riskCode=" + tRiskCode;
    }

    returnparent();
    }
    
    if (mAction == "UPDATEPERSON") {
    	var NBContNo=mSwitch.getVar("ContNo");
    	if (!checkMainAndSubRiskInfo(NBContNo)) {
			var updateMsg = ""+"�����սɷѷ�ʽ����ִ��ڲ�ͬ���뷵�����±��渽������Ϣ��"+"";
			var updateUrl = "../common/jsp/MessagePage.jsp?picture=F&content="+encodeURI(encodeURI(updateMsg));
			showModalDialog(updateUrl,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    	} 
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
        myAlert(""+"��ProposalInput.js"+"-->"+"resetForm�����з����쳣:��ʼ���������!"+"");
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

        cGrpPolNo = fm.all( 'GrpPolNo' ).value;
        cContNo = fm.all( 'ContNo' ).value;
        window.open("./ProposalQueryMain.jsp?GrpPolNo=" + cGrpPolNo + "&ContNo=" + cContNo,"",sFeatures);
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
	if (beforeSubmit()== false )  return false;
	 if(!chkPayEndYear()){           //У��ɷѷ�ʽΪ����ʱ���������ڲ���ѡ��
	       return false;                    //create by zhangyang 2005-10-21
	    }
	var sqlid91="ProposalInputSql91";
	var mySql91=new SqlClass();
	mySql91.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql91.setSqlId(sqlid91);//ָ��ʹ�õ�Sql��id
	mySql91.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	var strArr = easyExecSql(mySql91.getString());
    var flag=0;
    if(strArr>0){

    	var sqlid92="ProposalInputSql92";
    	var mySql92=new SqlClass();
    	mySql92.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql92.setSqlId(sqlid92);//ָ��ʹ�õ�Sql��id
    	mySql92.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
    	
    	var strArrPayPlanCode = easyExecSql(mySql92.getString());
    	
//    	var StrPayPlanCode ;
//    	if(strArrPayPlanCode>0){
//    		StrPayPlanCode = strArrPayPlanCode[0][0];
//    	}
//    	alert(strArrPayPlanCode);
//    	alert(StrPayPlanCode);
    	
//    	for(var i=0;i<DutyGrid.mulLineCount;i++){
//    		if(DutyGrid.getRowColData(i,1)==strArrPayPlanCode){
////    			alert(DutyGrid.getRowColData(i,1));
//    			continue;
//    		}
//    		if(DutyGrid.getRowColData(i,13)==null||DutyGrid.getRowColData(i,13)==''){
//    			flag++;
//    		}
//    	}
//	    if(flag>0){
//	    	alert('��¼�붨�ڹ���ѻ��߷Ƕ��ڹ����');
//	    	return false;
//	    }
    }	 
	
	if(fm.RiskCode.value == "IBL03"){
		if(fm.PayIntv.value==0&&(fm.PlanLevel.value=="2"||fm.PlanLevel.value=="3"))
		{
			myAlert(""+"���ɷѷ�ʽΪ����ʱ�����ϼƻ�ֻ��ѡ��1"+"-"+"�ʱ��������ٱ���"+"");
			return false;
		}
		if(fm.PlanLevel.value=="1"&&fm.Prem.value==""){
			myAlert(""+"���Ѳ���Ϊ�գ�"+"");
			return false;
		}
		if((fm.PlanLevel.value=="2"||fm.PlanLevel.value=="3")&&(fm.Amnt.value==""||fm.Amnt.value==0.0))
		{
			myAlert(""+"�����Ϊ�ջ�0��"+"");
			return false;
		}
				
			
	}
	var rowNum=DiscountGrid. mulLineCount; 
	var arr=0;
	var q=0;
	for(var i=0;i<rowNum;i++){
		if(DiscountGrid.getChkNo(i)==true){
			q++;
			arr=(i+1);
		}
	}
	if(q>1){
		myAlert(""+"ֻ��ѡ��һ����Ʒ"+"");
		return false;
	}else{
		if(!arr==0){
			DiscountGrid.checkBoxSel(arr);
		}else{
			DiscountGrid.checkBoxAllNot();
		}
	}
   var tCurrencyCode = "";
	 try{
	 tCurrencyCode = fm.CurrencyCode.value;
	}
	catch(E)
	{
		 tCurrencyCode = "01";
		}
//	   if(tCurrencyCode=="" || tCurrencyCode==null){
//			alert("������Ϣ����Ϊ��");
//			return false;
//		}
		
    if (fm.RiskCode.value == "212403")
    {
        if (verifyInput2() == false)
            return;
    }

    //У������ۿۣ����ܴ���1С��0
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
                myAlert(""+"����������Ϣ"+" "+"��"+""+(i+1)+""+"�е����֤������������!����������!"+"");
                BnfGrid.setRowColData(i,5,"");
                BnfGrid.setFocus(i,5);
                return ;
            }
        }
        if (BnfGrid.getRowColData(i, 10) == "A")
        {
          if(ContType!="2")
          {
					//alert("9999999");
                  BnfGrid.setRowColData(i, 2, fm.all("AppntName").value);
                  BnfGrid.setRowColData(i, 3, fm.all("AppntSex").value);
                  BnfGrid.setRowColData(i, 4, fm.all("AppntIDType").value);
                  BnfGrid.setRowColData(i, 5, fm.all("AppntIDNo").value);
                  //BnfGrid.setRowColData(i, 6,parent.VD.gVSwitch.getVar( "RelationToAppnt"));
                  BnfGrid.setRowColData(i, 6,parent.VD.gVSwitch.getVar( "RelationToInsured"));
                  BnfGrid.setRowColData(i, 9, fm.all("AppntHomeAddress").value);
                  //hl
                  //BnfGrid.setRowColData(i, 9, fm.all("AppntNo").value);

          }
          else
          {
                  myAlert(""+"Ͷ����Ϊ���壬������Ϊ�����ˣ�"+"")

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
					//alert("0000000");
                  BnfGrid.setRowColData(i, 2, fm.all("Name").value);
                  BnfGrid.setRowColData(i, 3, fm.all("Sex").value);
                  BnfGrid.setRowColData(i, 4, fm.all("IDType").value);
                  BnfGrid.setRowColData(i, 5, fm.all("IDNo").value);
                  BnfGrid.setRowColData(i, 6, "00");
              BnfGrid.setRowColData(i, 9, fm.all("HomeAddress").value);

                  //BnfGrid.setRowColData(i, 9, fm.all("InsuredNo").value);

         }

        }
    var tProposalNo = "";
    tProposalNo = fm.all( 'ProposalNo' ).value;

    if( tProposalNo == null || tProposalNo == "" )
        myAlert( ""+"������Ͷ������ѯ�������ٽ����޸�!"+"" );
    else {
        // У��¼������
        if (fm.all('DivLCInsured').style.display == "none") {
      for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {
          if (fm.elements[elementsNum].verify != null && fm.elements[elementsNum].name.indexOf("Appnt") != -1) {
            fm.elements[elementsNum].verify = "";
          }
        }
    }

      //-------------------------------------------------Beg
      //�޸��ˣ�chenrong
      //�޸����ڣ�2006-03-13
      //����ȡ����ֵ����ַ���
      if (setDutyKind() == "")
      {
          myAlert(""+"��ȡ����ֵ����Ϊ�գ�������!"+"");
          return;
      }
      //-------------------------------------------------End

        if( verifyProposal() == false ) return;
    if(!chkMult() || !checkMult())      //�жϷ����Ƿ�Ϊ�ջ����� chkMult�����ڶ���������,checkMult�����ڵ���������,
                                                                            //create by malong 2005-7-8
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

        var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+showStr;

        if( mAction == "" ) {
            showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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

            fm.all( 'fmAction' ).value = mAction;
            fm.action="../app/ProposalSave.jsp";

            //�б��ƻ����(����Ͷ����״̬���䣺����״̬���˱�״̬)
            if (typeof(window.ChangePlanSub) == "object") fm.all('fmAction').value = "ChangePlan" + fm.all('fmAction').value;
            //�޸ĸ�������(����Ͷ����״̬���䣺����״̬���˱�״̬,���ñȳб��ƻ������һ����޸ĸ������ʣ�ΪȨ�޿���)
            if(LoadFlag=="10") fm.all('fmAction').value = "ChangePlan" + fm.all('fmAction').value;
            //yaoryif(LoadFlag=="3") fm.all('fmAction').value = "Modify" + fm.all('fmAction').value;
            //inputQuestButton.disabled = false;
            
            var tDutyCode = getDutyCode(fm.RiskCode.value);
            
            try{
                if(fm.all('inpNeedDutyGrid').value==0)
                {
                    if (tDutyCode != null && tDutyCode != "null" && tDutyCode != "")
                    {
                        fm.action="../app/ProposalSave.jsp?DutyCode=" + tDutyCode;
                    }
                }
            }
            catch(ex){}
            
            //20111011,cmj,��ȫ����������
            if(LoadFlag=="8"){
            	fm.action="../app/ProposalSave.jsp?DutyCode=" + tDutyCode+"&BQFlag=2";
            }
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
  
    var tProposalNo = fm.all('ProposalNo').value;

    if(tProposalNo==null || tProposalNo=="") {
        myAlert( ""+"������Ͷ������ѯ�������ٽ���ɾ��!"+"" );
    }
    else {
        var showStr = ""+"����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+showStr;

        if( mAction == "" ) {
            showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
            mAction = "DELETE";
            fm.all( 'fmAction' ).value = mAction;
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
        myAlert( ""+"��������¼�����ֺ����ְ汾�����޸ĸ�Ͷ�����������"+"" );
        return false
    }

    showInfo = window.open("./ChooseDutyInput.jsp?RiskCode="+cRiskCode+"&RiskVersion="+cRiskVersion,"",sFeatures);
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
        myAlert( ""+"�������ȱ���Ͷ�������ܲ鿴��Ͷ�����������"+"" );
        return false
    }

    var showStr = ""+"���ڲ�ѯ���ݣ������Ժ�......"+"";
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

    showModalDialog( "./ProposalDuty.jsp?PolNo="+cPolNo,window,"status:no;help:0;close:0;dialogWidth=18cm;dialogHeight=14cm");
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
        myAlert( ""+"�������ȱ���Ͷ�������ܽ����ݽ�����Ϣ���֡�"+"" );
        return false
    }

    showInfo = window.open( "./ProposalFee.jsp?PolNo=" + cPolNo + "&polType=PROPOSAL&prtNo=" + prtNo,"",sFeatures );
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
        showInfo = window.open( "../sys/LDPersonMain.html" ,"",sFeatures);
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
        showInfo = window.open( "../sys/LDPersonMain.html" ,"",sFeatures);
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
        showInfo = window.open( "../sys/LDPersonMain.html" ,"",sFeatures);
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
    //alert(2328);
    /*
        try { fm.all('PrtNo').value = cArr[6]; } catch(ex) { };
        try { fm.all('ManageCom').value = cArr[12]; } catch(ex) { };
        try { fm.all('SaleChnl').value = cArr[15]; } catch(ex) { };
        try { fm.all('AgentCom').value = cArr[13]; } catch(ex) { };
        try { fm.all('AgentType').value = cArr[14]; } catch(ex) { };
        try { fm.all('AgentCode').value = cArr[87]; } catch(ex) { };
        try { fm.all('AgentGroup').value = cArr[88]; } catch(ex) { };
        //try { fm.all('Handler').value = cArr[82]; } catch(ex) { };
        //try { fm.all('AgentCode1').value = cArr[89]; } catch(ex) { };
        try { fm.all('Remark').value = cArr[90]; } catch(ex) { };

        try { fm.all('ContNo').value = cArr[1]; } catch(ex) { };

        //try { fm.all('Amnt').value = cArr[43]; } catch(ex) { };
        try { fm.all('CValiDate').value = cArr[29]; } catch(ex) { };
        try { fm.all('PolApplyDate').value = cArr[128]; } catch(ex) { };
        try { fm.all('HealthCheckFlag').value = cArr[72]; } catch(ex) { };
        try { fm.all('OutPayFlag').value = cArr[97]; } catch(ex) { };
        try { fm.all('PayLocation').value = cArr[59]; } catch(ex) { };
        try { fm.all('BankCode').value = cArr[102]; } catch(ex) { };
        try { fm.all('BankAccNo').value = cArr[103]; } catch(ex) { };
        try { fm.all('AccName').value = cArr[118]; } catch(ex) { };
        try { fm.all('LiveGetMode').value = cArr[98]; } catch(ex) { };
        try { fm.all('BonusGetMode').value = cArr[100]; } catch(ex) { };
        try { fm.all('AutoPayFlag').value = cArr[65]; } catch(ex) { };
        try { fm.all('InterestDifFlag').value = cArr[66]; } catch(ex) { };

        try { fm.all('InsuYear').value = cArr[111]; } catch(ex) { };
        try { fm.all('InsuYearFlag').value = cArr[110]; } catch(ex) { };
        try { fm.all('PolTypeFlag').value = cArr[69]; } catch(ex) { };
        try { fm.all('InsuredPeoples').value = cArr[24]; } catch(ex) { };
        try { fm.all('InsuredAppAge').value = cArr[22]; } catch(ex) { };


        try { fm.all('StandbyFlag1').value = cArr[78]; } catch(ex) { };
        try { fm.all('StandbyFlag2').value = cArr[79]; } catch(ex) { };
        try { fm.all('StandbyFlag3').value = cArr[80]; } catch(ex) { };
    */
        try { fm.all('PrtNo').value = cArr[5]; } catch(ex) { };
        try { fm.all('ManageCom').value = cArr[13]; } catch(ex) { };
        try { fm.all('SaleChnl').value = cArr[19]; } catch(ex) { };
        try { fm.all('AgentCom').value = cArr[14]; } catch(ex) { };
        try { fm.all('AgentType').value = cArr[15]; } catch(ex) { };
        try { fm.all('AgentCode').value = cArr[16]; } catch(ex) { };
        try { fm.all('AgentGroup').value = cArr[17]; } catch(ex) { };
        try { fm.all('Handler').value = cArr[20]; } catch(ex) { };
        try { fm.all('AgentCode1').value = cArr[18]; } catch(ex) { };
        try { fm.all('Remark').value = cArr[92]; } catch(ex) { };

        try { fm.all('ContNo').value = cArr[2]; } catch(ex) { };

        try { fm.all('CValiDate').value = cArr[30]; } catch(ex) { };
        try { fm.all('PolApplyDate').value = cArr[101]; } catch(ex) { };
        try { fm.all('HealthCheckFlag').value = cArr[81]; } catch(ex) { };
        //try { fm.all('OutPayFlag').value = cArr[97]; } catch(ex) { };
        try { fm.all('PayLocation').value = cArr[51]; } catch(ex) { };
        //try { fm.all('BankCode').value = cArr[102]; } catch(ex) { };
        //try { fm.all('BankAccNo').value = cArr[103]; } catch(ex) { };
        //try { fm.all('AccName').value = cArr[118]; } catch(ex) { };
        try { fm.all('LiveGetMode').value = cArr[86]; } catch(ex) { };
        try { fm.all('BonusGetMode').value = cArr[88]; } catch(ex) { };
        try { fm.all('AutoPayFlag').value = cArr[77]; } catch(ex) { };
        try { fm.all('InterestDifFlag').value = cArr[78]; } catch(ex) { };
        try { fm.all('InsuYear').value = cArr[45]; } catch(ex) { };
        try { fm.all('InsuYearFlag').value = cArr[44]; } catch(ex) { };
        try { fm.all('PolTypeFlag').value = cArr[7]; } catch(ex) { };
        try { fm.all('InsuredPeoples').value = cArr[26]; } catch(ex) { };
        try { fm.all('InsuredAppAge').value = cArr[25]; } catch(ex) { };


        try { fm.all('StandbyFlag1').value = cArr[104]; } catch(ex) { };
        try { fm.all('StandbyFlag2').value = cArr[105]; } catch(ex) { };
        try { fm.all('StandbyFlag3').value = cArr[106]; } catch(ex) { };

    } catch(ex) {
      myAlert("displayPol err:" + ex + "\ndata is:" + cArr);
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
    try { fm.all('AppntCustomerNo').value = cArr[1]; } catch(ex) { };
    try { fm.all('AppntRelationToInsured').value = cArr[4]; } catch(ex) { };
    try { fm.all('AppntPassword').value = cArr[5]; } catch(ex) { };
    try { fm.all('AppntName').value = cArr[6]; } catch(ex) { };
    try { fm.all('AppntSex').value = cArr[7]; } catch(ex) { };
    try { fm.all('AppntBirthday').value = cArr[8]; } catch(ex) { };
    try { fm.all('AppntNativePlace').value = cArr[9]; } catch(ex) { };
    try { fm.all('AppntNationality').value = cArr[10]; } catch(ex) { };
    try { fm.all('AppntMarriage').value = cArr[11]; } catch(ex) { };
    try { fm.all('AppntMarriageDate').value = cArr[12]; } catch(ex) { };
    try { fm.all('AppntOccupationType').value = cArr[13]; } catch(ex) { };
    try { fm.all('AppntStartWorkDate').value = cArr[14]; } catch(ex) { };
    try { fm.all('AppntSalary').value = cArr[15]; } catch(ex) { };
    try { fm.all('AppntHealth').value = cArr[16]; } catch(ex) { };
    try { fm.all('AppntStature').value = cArr[17]; } catch(ex) { };
    try { fm.all('AppntAvoirdupois').value = cArr[18]; } catch(ex) { };
    try { fm.all('AppntCreditGrade').value = cArr[19]; } catch(ex) { };
    try { fm.all('AppntIDType').value = cArr[20]; } catch(ex) { };
    try { fm.all('AppntProterty').value = cArr[21]; } catch(ex) { };
    try { fm.all('AppntIDNo').value = cArr[22]; } catch(ex) { };
    try { fm.all('AppntOthIDType').value = cArr[23]; } catch(ex) { };
    try { fm.all('AppntOthIDNo').value = cArr[24]; } catch(ex) { };
    try { fm.all('AppntICNo').value = cArr[25]; } catch(ex) { };
    try { fm.all('AppntHomeAddressCode').value = cArr[26]; } catch(ex) { };
    try { fm.all('AppntHomeAddress').value = cArr[27]; } catch(ex) { };
    try { fm.all('AppntPostalAddress').value = cArr[28]; } catch(ex) { };
    try { fm.all('AppntZipCode').value = cArr[29]; } catch(ex) { };
    try { fm.all('AppntPhone').value = cArr[30]; } catch(ex) { };
    try { fm.all('AppntBP').value = cArr[31]; } catch(ex) { };
    try { fm.all('AppntMobile').value = cArr[32]; } catch(ex) { };
    try { fm.all('AppntEMail').value = cArr[33]; } catch(ex) { };
    try { fm.all('AppntJoinCompanyDate').value = cArr[34]; } catch(ex) { };
    try { fm.all('AppntPosition').value = cArr[35]; } catch(ex) { };
    try { fm.all('AppntGrpNo').value = cArr[36]; } catch(ex) { };
    try { fm.all('AppntGrpName').value = cArr[37]; } catch(ex) { };
    try { fm.all('AppntGrpPhone').value = cArr[38]; } catch(ex) { };
    try { fm.all('AppntGrpAddressCode').value = cArr[39]; } catch(ex) { };
    try { fm.all('AppntGrpAddress').value = cArr[40]; } catch(ex) { };
    try { fm.all('AppntDeathDate').value = cArr[41]; } catch(ex) { };
    try { fm.all('AppntRemark').value = cArr[42]; } catch(ex) { };
    try { fm.all('AppntState').value = cArr[43]; } catch(ex) { };
    try { fm.all('AppntWorkType').value = cArr[46]; } catch(ex) { };
    try { fm.all('AppntPluralityType').value = cArr[47]; } catch(ex) { };
    try { fm.all('AppntOccupationCode').value = cArr[48]; } catch(ex) { };
    try { fm.all('AppntDegree').value = cArr[49]; } catch(ex) { };
    try { fm.all('AppntGrpZipCode').value = cArr[50]; } catch(ex) { };
    try { fm.all('AppntSmokeFlag').value = cArr[51]; } catch(ex) { };
    try { fm.all('AppntRgtAddress').value = cArr[52]; } catch(ex) { };
    try { fm.all('AppntHomeZipCode').value = cArr[53]; } catch(ex) { };
    try { fm.all('AppntPhone2').value = cArr[54]; } catch(ex) { };

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
    try { fm.all('AppntPolNo').value = cArr[0]; } catch(ex) { };
    try { fm.all('AppntGrpNo').value = cArr[1]; } catch(ex) { };
    try { fm.all('AppntRelationToInsured').value = cArr[2]; } catch(ex) { };
    try { fm.all('AppntAppntGrade').value = cArr[3]; } catch(ex) { };
    try { fm.all('AppntPassword').value = cArr[4]; } catch(ex) { };
    try { fm.all('AppntGrpName').value = cArr[5]; } catch(ex) { };
    try { fm.all('AppntGrpAddressCode').value = cArr[6]; } catch(ex) { };
    try { fm.all('AppntGrpAddress').value = cArr[7]; } catch(ex) { };
    try { fm.all('AppntGrpZipCode').value = cArr[8]; } catch(ex) { };
    try { fm.all('AppntBusinessType').value = cArr[9]; } catch(ex) { };
    try { fm.all('AppntGrpNature').value = cArr[10]; } catch(ex) { };
    try { fm.all('AppntPeoples').value = cArr[11]; } catch(ex) { };
    try { fm.all('AppntRgtMoney').value = cArr[12]; } catch(ex) { };
    try { fm.all('AppntAsset').value = cArr[13]; } catch(ex) { };
    try { fm.all('AppntNetProfitRate').value = cArr[14]; } catch(ex) { };
    try { fm.all('AppntMainBussiness').value = cArr[15]; } catch(ex) { };
    try { fm.all('AppntCorporation').value = cArr[16]; } catch(ex) { };
    try { fm.all('AppntComAera').value = cArr[17]; } catch(ex) { };
    try { fm.all('AppntLinkMan1').value = cArr[18]; } catch(ex) { };
    try { fm.all('AppntDepartment1').value = cArr[19]; } catch(ex) { };
    try { fm.all('AppntHeadShip1').value = cArr[20]; } catch(ex) { };
    try { fm.all('AppntPhone1').value = cArr[21]; } catch(ex) { };
    try { fm.all('AppntE_Mail1').value = cArr[22]; } catch(ex) { };
    try { fm.all('AppntFax1').value = cArr[23]; } catch(ex) { };
    try { fm.all('AppntLinkMan2').value = cArr[24]; } catch(ex) { };
    try { fm.all('AppntDepartment2').value = cArr[25]; } catch(ex) { };
    try { fm.all('AppntHeadShip2').value = cArr[26]; } catch(ex) { };
    try { fm.all('AppntPhone2').value = cArr[27]; } catch(ex) { };
    try { fm.all('AppntE_Mail2').value = cArr[28]; } catch(ex) { };
    try { fm.all('AppntFax2').value = cArr[29]; } catch(ex) { };
    try { fm.all('AppntFax').value = cArr[30]; } catch(ex) { };
    try { fm.all('AppntPhone').value = cArr[31]; } catch(ex) { };
    try { fm.all('AppntGetFlag').value = cArr[32]; } catch(ex) { };
    try { fm.all('AppntSatrap').value = cArr[33]; } catch(ex) { };
    try { fm.all('AppntEMail').value = cArr[34]; } catch(ex) { };
    try { fm.all('AppntFoundDate').value = cArr[35]; } catch(ex) { };
    try { fm.all('AppntBankAccNo').value = cArr[36]; } catch(ex) { };
    try { fm.all('AppntBankCode').value = cArr[37]; } catch(ex) { };
    try { fm.all('AppntGrpGroupNo').value = cArr[38]; } catch(ex) { };
    try { fm.all('AppntState').value = cArr[39]; } catch(ex) { };
    try { fm.all('AppntRemark').value = cArr[40]; } catch(ex) { };
    try { fm.all('AppntBlacklistFlag').value = cArr[41]; } catch(ex) { };
    try { fm.all('AppntOperator').value = cArr[42]; } catch(ex) { };
    try { fm.all('AppntMakeDate').value = cArr[43]; } catch(ex) { };
    try { fm.all('AppntMakeTime').value = cArr[44]; } catch(ex) { };
    try { fm.all('AppntModifyDate').value = cArr[45]; } catch(ex) { };
    try { fm.all('AppntModifyTime').value = cArr[46]; } catch(ex) { };
    try { fm.all('AppntFIELDNUM').value = cArr[47]; } catch(ex) { };
    try { fm.all('AppntPK').value = cArr[48]; } catch(ex) { };
    try { fm.all('AppntfDate').value = cArr[49]; } catch(ex) { };
    try { fm.all('AppntmErrors').value = cArr[50]; } catch(ex) { };
    */
    try { fm.all('AppntPolNo').value = cArr[0]; } catch(ex) { };
    try { fm.all('AppntGrpNo').value = cArr[1]; } catch(ex) { };
    try { fm.all('AppntRelationToInsured').value = cArr[2]; } catch(ex) { };
    try { fm.all('AppntAppntGrade').value = cArr[3]; } catch(ex) { };
    try { fm.all('AppntPassword').value = cArr[4]; } catch(ex) { };
    try { fm.all('AppntGrpName').value = cArr[5]; } catch(ex) { };
    try { fm.all('AppntGrpAddressCode').value = cArr[6]; } catch(ex) { };
    try { fm.all('AppntGrpAddress').value = cArr[7]; } catch(ex) { };
    try { fm.all('AppntGrpZipCode').value = cArr[8]; } catch(ex) { };
    try { fm.all('AppntBusinessType').value = cArr[9]; } catch(ex) { };
    try { fm.all('AppntGrpNature').value = cArr[10]; } catch(ex) { };
    try { fm.all('AppntPeoples').value = cArr[11]; } catch(ex) { };
    try { fm.all('AppntRgtMoney').value = cArr[12]; } catch(ex) { };
    try { fm.all('AppntAsset').value = cArr[13]; } catch(ex) { };
    try { fm.all('AppntNetProfitRate').value = cArr[14]; } catch(ex) { };
    try { fm.all('AppntMainBussiness').value = cArr[15]; } catch(ex) { };
    try { fm.all('AppntCorporation').value = cArr[16]; } catch(ex) { };
    try { fm.all('AppntComAera').value = cArr[17]; } catch(ex) { };
    try { fm.all('AppntLinkMan1').value = cArr[18]; } catch(ex) { };
    try { fm.all('AppntDepartment1').value = cArr[19]; } catch(ex) { };
    try { fm.all('AppntHeadShip1').value = cArr[20]; } catch(ex) { };
    try { fm.all('AppntPhone1').value = cArr[21]; } catch(ex) { };
    try { fm.all('AppntE_Mail1').value = cArr[22]; } catch(ex) { };
    try { fm.all('AppntFax1').value = cArr[23]; } catch(ex) { };
    try { fm.all('AppntLinkMan2').value = cArr[24]; } catch(ex) { };
    try { fm.all('AppntDepartment2').value = cArr[25]; } catch(ex) { };
    try { fm.all('AppntHeadShip2').value = cArr[26]; } catch(ex) { };
    try { fm.all('AppntPhone2').value = cArr[27]; } catch(ex) { };
    try { fm.all('AppntE_Mail2').value = cArr[28]; } catch(ex) { };
    try { fm.all('AppntFax2').value = cArr[29]; } catch(ex) { };
    try { fm.all('AppntFax').value = cArr[30]; } catch(ex) { };
    try { fm.all('AppntPhone').value = cArr[31]; } catch(ex) { };
    try { fm.all('AppntGetFlag').value = cArr[32]; } catch(ex) { };
    try { fm.all('AppntSatrap').value = cArr[33]; } catch(ex) { };
    try { fm.all('AppntEMail').value = cArr[34]; } catch(ex) { };
    try { fm.all('AppntFoundDate').value = cArr[35]; } catch(ex) { };
    try { fm.all('AppntBankAccNo').value = cArr[36]; } catch(ex) { };
    try { fm.all('AppntBankCode').value = cArr[37]; } catch(ex) { };
    try { fm.all('AppntGrpGroupNo').value = cArr[38]; } catch(ex) { };
    try { fm.all('AppntState').value = cArr[39]; } catch(ex) { };
    try { fm.all('AppntRemark').value = cArr[40]; } catch(ex) { };
    try { fm.all('AppntBlacklistFlag').value = cArr[41]; } catch(ex) { };
    try { fm.all('AppntOperator').value = cArr[42]; } catch(ex) { };
    try { fm.all('AppntMakeDate').value = cArr[43]; } catch(ex) { };
    try { fm.all('AppntMakeTime').value = cArr[44]; } catch(ex) { };
    try { fm.all('AppntModifyDate').value = cArr[45]; } catch(ex) { };
    try { fm.all('AppntModifyTime').value = cArr[46]; } catch(ex) { };
    try { fm.all('AppntFIELDNUM').value = cArr[47]; } catch(ex) { };
    try { fm.all('AppntPK').value = cArr[48]; } catch(ex) { };
    try { fm.all('AppntfDate').value = cArr[49]; } catch(ex) { };
    try { fm.all('AppntmErrors').value = cArr[50]; } catch(ex) { };
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
    try { fm.all('ContNo').value=cArr[1];} catch(ex){};
    try { fm.all('CustomerNo').value = cArr[2]; } catch(ex) { };
    try { fm.all('SequenceNo').value = cArr[11]; } catch(ex) { };
    //try { fm.all('InsuredGrade').value = cArr[3]; } catch(ex) { };
    try { fm.all('RelationToInsured').value = cArr[8]; } catch(ex) { };
    //try { fm.all('Password').value = cArr[5]; } catch(ex) { };
    //try { fm.all('Name').value = cArr[12]; } catch(ex) { };
    try { fm.all('Sex').value = cArr[13]; } catch(ex) { };
    try { fm.all('Birthday').value = cArr[14]; } catch(ex) { };
    try { fm.all('NativePlace').value = cArr[17]; } catch(ex) { };
    try { fm.all('Nationality').value = cArr[18]; } catch(ex) { };
    try { fm.all('Marriage').value = cArr[20]; } catch(ex) { };
    try { fm.all('MarriageDate').value = cArr[21]; } catch(ex) { };
    try { fm.all('OccupationType').value = cArr[34]; } catch(ex) { };
    try { fm.all('StartWorkDate').value = cArr[31]; } catch(ex) { };
    try { fm.all('Salary').value = cArr[33]; } catch(ex) { };
    try { fm.all('Health').value = cArr[22]; } catch(ex) { };
    try { fm.all('Stature').value = cArr[23]; } catch(ex) { };
    try { fm.all('Avoirdupois').value = cArr[24]; } catch(ex) { };
    try { fm.all('CreditGrade').value = cArr[26]; } catch(ex) { };
    try { fm.all('IDType').value = cArr[15]; } catch(ex) { };
    //try { fm.all('Proterty').value = cArr[21]; } catch(ex) { };
    try { fm.all('IDNo').value = cArr[16]; } catch(ex) { };
    //try { fm.all('OthIDType').value = cArr[23]; } catch(ex) { };
    //try { fm.all('OthIDNo').value = cArr[24]; } catch(ex) { };
    //try { fm.all('ICNo').value = cArr[25]; } catch(ex) { };
    //try { fm.all('HomeAddressCode').value = cArr[26]; } catch(ex) { };
    //try { fm.all('HomeAddress').value = cArr[27]; } catch(ex) { };
    //try { fm.all('PostalAddress').value = cArr[28]; } catch(ex) { };
    //try { fm.all('ZipCode').value = cArr[29]; } catch(ex) { };
    //try { fm.all('Phone').value = cArr[30]; } catch(ex) { };
    //try { fm.all('BP').value = cArr[31]; } catch(ex) { };
    //try { fm.all('Mobile').value = cArr[32]; } catch(ex) { };
    //try { fm.all('EMail').value = cArr[33]; } catch(ex) { };
    //try { fm.all('JoinCompanyDate').value = cArr[34]; } catch(ex) { };
    //try { fm.all('Position').value = cArr[35]; } catch(ex) { };
    //try { fm.all('GrpNo').value = cArr[4]; } catch(ex) { };
    //try { fm.all('GrpName').value = cArr[37]; } catch(ex) { };
    //try { fm.all('GrpPhone').value = cArr[38]; } catch(ex) { };
    //try { fm.all('GrpAddressCode').value = cArr[39]; } catch(ex) { };
    //try { fm.all('GrpAddress').value = cArr[40]; } catch(ex) { };
    //try { fm.all('DeathDate').value = cArr[41]; } catch(ex) { };
    //try { fm.all('State').value = cArr[43]; } catch(ex) { };
    try { fm.all('WorkType').value = cArr[36]; } catch(ex) { };
    try { fm.all('PluralityType').value = cArr[37]; } catch(ex) { };
    try { fm.all('OccupationCode').value = cArr[35]; } catch(ex) { };
    try { fm.all('Degree').value = cArr[25]; } catch(ex) { };
    //try { fm.all('GrpZipCode').value = cArr[50]; } catch(ex) { };
    try { fm.all('SmokeFlag').value = cArr[38]; } catch(ex) { };
    try { fm.all('RgtAddress').value = cArr[19]; } catch(ex) { };
    //try { fm.all('HomeZipCode').value = cArr[53]; } catch(ex) { };
    //try { fm.all('Phone2').value = cArr[54]; } catch(ex) { };
    return;

}

/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ�������ʾ�����������˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displaySubInsured(){
    //fm.all( spanObj ).all( 'SubInsuredGrid1' ).value = arrResult[0][0];
    //fm.all( spanObj ).all( 'SubInsuredGrid2' ).value = arrResult[0][2];
    //fm.all( spanObj ).all( 'SubInsuredGrid3' ).value = arrResult[0][3];
    //fm.all( spanObj ).all( 'SubInsuredGrid4' ).value = arrResult[0][4];
    
    getGridElement(spanObj,"SubInsuredGrid1").value = arrResult[0][0];
    getGridElement(spanObj,"SubInsuredGrid2").value = arrResult[0][2];
    getGridElement(spanObj,"SubInsuredGrid3").value = arrResult[0][3];
    getGridElement(spanObj,"SubInsuredGrid4").value = arrResult[0][4];
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
            var tPolNo = arrResult[0][0];

            // ��ѯ������ϸ
            queryPolDetail( tPolNo );
      //�жϼ��±��еļ�¼����
        }

        if( mOperate == 2 ) {       // Ͷ������Ϣ
        
		var sqlid22="ProposalInputSql22";
		var mySql22=new SqlClass();
		mySql22.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql22.setSqlId(sqlid22);//ָ��ʹ�õ�Sql��id
		mySql22.addSubPara(arrQueryResult[0][0] );//ָ������Ĳ���
	    var Sql22=mySql22.getString();
		
		 arrResult = easyExecSql(Sql22, 1, 0);
           // arrResult = easyExecSql("select * from LDPerson where CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
            if (arrResult == null) {
              myAlert(""+"δ�鵽Ͷ������Ϣ"+"");
            } else {
               displayAppnt(arrResult[0]);
            }

      }
        if( mOperate == 3 ) {       // ����������Ϣ
        
		var sqlid23="ProposalInputSql23";
		var mySql23=new SqlClass();
		mySql23.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql23.setSqlId(sqlid23);//ָ��ʹ�õ�Sql��id
		mySql23.addSubPara(arrQueryResult[0][0]);//ָ������Ĳ���
	    var Sql23=mySql23.getString();
		
		    arrResult = easyExecSql(Sql23, 1, 0);
            //arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ),b.PostalAddress,b.ZipCode,b.Phone,b.HomePhone from LDPerson a Left Join LCAddress b On b.CustomerNo =a.CustomerNo Where 1=1 and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
            if (arrResult == null) {
              myAlert(""+"δ�鵽����������Ϣ"+"");
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
    //var showStr = "���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
//var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+showStr;

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
function queryRiskPlan( tProposalGrpContNo,tRiskCode,tContPlanCode,tMainRiskCode)
{

    emptyForm();
    //var showStr = "���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
//var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
    //alert("./ProposalQueryRiskPlan.jsp?ProposalGrpContNo="
    //                                                                      + tProposalGrpContNo+"&RiskCode="+tRiskCode+"&ContPlanCode="+tContPlanCode);
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    //parent.fraSubmit.window.location = "./ProposalQueryDetail.jsp?PolNo=" + cPolNo;
    parent.fraSubmit.window.location = "./ProposalQueryRiskPlan.jsp?ProposalGrpContNo="
                                                                            + tProposalGrpContNo+"&RiskCode="+tRiskCode+"&ContPlanCode="+tContPlanCode+"&MainRiskCode="+tMainRiskCode;
}

/*********************************************************************
 *  ���ݲ�ѯ���ص���Ϣ��ѯ���ֵı��ռƻ���ϸ��Ϣ ��ȫ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function BqQueryRiskPlan( tProposalGrpContNo,tRiskCode,tContPlanCode,tMainRiskCode,EdorValiDate,CValiDate )
{
    emptyForm();
    parent.fraSubmit.window.location = "./ProposalQueryRiskPlan.jsp?ProposalGrpContNo="
                                                                            + tProposalGrpContNo+"&RiskCode="+tRiskCode+"&ContPlanCode="+tContPlanCode+"&MainRiskCode="+tMainRiskCode+"&EdorValiDate="+EdorValiDate+"&CValiDate="+CValiDate;
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
  if (fm.all("CustomerNo").value == "") {
    showInsured1();
  //} else if (LoadFlag != "1" && LoadFlag != "2") {
  //  alert("ֻ����Ͷ����¼��ʱ���в�����");
  }  else {
  	
			var sqlid24="ProposalInputSql24";
		var mySql24=new SqlClass();
		mySql24.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql24.setSqlId(sqlid24);//ָ��ʹ�õ�Sql��id
		mySql24.addSubPara( fm.all("CustomerNo").value );//ָ������Ĳ���
	    var Sql24=mySql24.getString();
		
	 arrResult=easyExecSql(Sql24, 1, 0);
      //arrResult=easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ),b.PostalAddress,b.ZipCode,b.Phone,b.HomePhone from LDPerson a Left Join LCAddress b On b.CustomerNo =a.CustomerNo Where 1=1 and a.CustomerNo = '" + fm.all("CustomerNo").value + "'", 1, 0);
    if (arrResult == null) {
      myAlert(""+"δ�鵽����������Ϣ"+"");
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
  if (fm.all("AppntCustomerNo").value == "" && LoadFlag == "1") {
    showAppnt1();
  //} else if (LoadFlag != "1" && LoadFlag != "2") {
  //  alert("ֻ����Ͷ����¼��ʱ���в�����");
  } else {
  	
		var sqlid25="ProposalInputSql25";
		var mySql25=new SqlClass();
		mySql25.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql25.setSqlId(sqlid25);//ָ��ʹ�õ�Sql��id
		mySql25.addSubPara( fm.all("AppntCustomerNo").value );//ָ������Ĳ���
	    var Sql25=mySql25.getString();
	
	arrResult = easyExecSql(Sql25, 1, 0);
    //arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ),b.PostalAddress,b.ZipCode,b.Phone,b.HomePhone from LDPerson a Left Join LCAddress b On b.CustomerNo =a.CustomerNo Where 1=1 and a.CustomerNo = '" + fm.all("AppntCustomerNo").value + "'", 1, 0);
    if (arrResult == null) {
      myAlert(""+"δ�鵽Ͷ������Ϣ"+"");
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
    fm.all('DivLCInsured').style.display = "";
    fm.SamePersonFlag.checked = false;
    myAlert(""+"Ͷ�����뱻���˹�ϵ���Ǳ��ˣ����ܽ��иò�����"+"");
  }
  //��Ӧ��ͬһ�ˣ��ִ򹳵����
  else if (fm.SamePersonFlag.checked == true) {
    fm.all('DivLCInsured').style.display = "none";
  }
  //��Ӧ��ѡͬһ�˵����
  else if (fm.SamePersonFlag.checked == false) {
    fm.all('DivLCInsured').style.display = "";
  }

  for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {
      if (fm.elements[elementsNum].name.indexOf("Appnt") != -1) {
        try {
          insuredName = fm.elements[elementsNum].name.substring(fm.elements[elementsNum].name.indexOf("t") + 1);
          if (fm.all('DivLCInsured').style.display == "none") {
            fm.all(insuredName).value = fm.elements[elementsNum].value;
          }
          else {
            fm.all(insuredName).value = "";
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
          if (fm.all('DivLCInsured').style.display == "none") {
            fm.all(insuredName).value = fm.elements[elementsNum].value;
          }
          else {
            fm.all(insuredName).value = "";
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
try{fm.all('AppntCustomerNo').value= arrResult[0][0]; }catch(ex){};
try{fm.all('AppntName').value= arrResult[0][1]; }catch(ex){};
try{fm.all('AppntSex').value= arrResult[0][2]; }catch(ex){};
try{fm.all('AppntBirthday').value= arrResult[0][3]; }catch(ex){};
try{fm.all('AppntIDType').value= arrResult[0][4]; }catch(ex){};
try{fm.all('AppntIDNo').value= arrResult[0][5]; }catch(ex){};
try{fm.all('AppntPassword').value= arrResult[0][6]; }catch(ex){};
try{fm.all('AppntNativePlace').value= arrResult[0][7]; }catch(ex){};
try{fm.all('AppntNationality').value= arrResult[0][8]; }catch(ex){};
try{fm.all('AppntRgtAddress').value= arrResult[0][9]; }catch(ex){};
try{fm.all('AppntMarriage').value= arrResult[0][10];}catch(ex){};
try{fm.all('AppntMarriageDate').value= arrResult[0][11];}catch(ex){};
try{fm.all('AppntHealth').value= arrResult[0][12];}catch(ex){};
try{fm.all('AppntStature').value= arrResult[0][13];}catch(ex){};
try{fm.all('AppntAvoirdupois').value= arrResult[0][14];}catch(ex){};
try{fm.all('AppntDegree').value= arrResult[0][15];}catch(ex){};
try{fm.all('AppntCreditGrade').value= arrResult[0][16];}catch(ex){};
try{fm.all('AppntOthIDType').value= arrResult[0][17];}catch(ex){};
try{fm.all('AppntOthIDNo').value= arrResult[0][18];}catch(ex){};
try{fm.all('AppntICNo').value= arrResult[0][19];}catch(ex){};
try{fm.all('AppntGrpNo').value= arrResult[0][20];}catch(ex){};
try{fm.all('AppntJoinCompanyDate').value= arrResult[0][21];}catch(ex){};
try{fm.all('AppntStartWorkDate').value= arrResult[0][22];}catch(ex){};
try{fm.all('AppntPosition').value= arrResult[0][23];}catch(ex){};
try{fm.all('AppntSalary').value= arrResult[0][24];}catch(ex){};
try{fm.all('AppntOccupationType').value= arrResult[0][25];}catch(ex){};
try{fm.all('AppntOccupationCode').value= arrResult[0][26];}catch(ex){};
try{fm.all('AppntWorkType').value= arrResult[0][27];}catch(ex){};
try{fm.all('AppntPluralityType').value= arrResult[0][28];}catch(ex){};
try{fm.all('AppntDeathDate').value= arrResult[0][29];}catch(ex){};
try{fm.all('AppntSmokeFlag').value= arrResult[0][30];}catch(ex){};
try{fm.all('AppntBlacklistFlag').value= arrResult[0][31];}catch(ex){};
try{fm.all('AppntProterty').value= arrResult[0][32];}catch(ex){};
try{fm.all('AppntRemark').value= arrResult[0][33];}catch(ex){};
try{fm.all('AppntState').value= arrResult[0][34];}catch(ex){};
try{fm.all('AppntOperator').value= arrResult[0][35];}catch(ex){};
try{fm.all('AppntMakeDate').value= arrResult[0][36];}catch(ex){};
try{fm.all('AppntMakeTime').value= arrResult[0][37];}catch(ex){};
try{fm.all('AppntModifyDate').value= arrResult[0][38];}catch(ex){};
try{fm.all('AppntModifyTime').value= arrResult[0][39];}catch(ex){};
try{fm.all('AppntGrpName').value= arrResult[0][40];}catch(ex){};
try{fm.all('AppntHomeAddress').value= arrResult[0][41];}catch(ex){};
try{fm.all('AppntHomeZipCode').value= arrResult[0][42];}catch(ex){};
try{fm.all('AppntPhone').value= arrResult[0][43];}catch(ex){};
try{fm.all('AppntPhone2').value= arrResult[0][44];}catch(ex){};
}

/*********************************************************************
 *  �������е�������ʾ��Ͷ���˲���
 *  ����  ��  ����ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayAddress1()
{
try {fm.all('GrpNo').value= arrResult[0][0]; } catch(ex) { };
try {fm.all('GrpCustomerNo').value= arrResult[0][0]; } catch(ex) { };
try {fm.all('AddressNo').value= arrResult[0][1]; } catch(ex) { };
try {fm.all('AppGrpAddress').value= arrResult[0][2]; } catch(ex) { };
try {fm.all('AppGrpZipCode').value= arrResult[0][3]; } catch(ex) { };
try {fm.all('LinkMan1').value= arrResult[0][4]; } catch(ex) { };
try {fm.all('Department1').value= arrResult[0][5]; } catch(ex) { };
try {fm.all('HeadShip1').value= arrResult[0][6]; } catch(ex) { };
try {fm.all('GrpPhone1').value= arrResult[0][7]; } catch(ex) { };
try {fm.all('E_Mail1').value= arrResult[0][8]; } catch(ex) { };
try {fm.all('Fax1').value= arrResult[0][9]; } catch(ex) { };
try {fm.all('LinkMan2').value= arrResult[0][10]; } catch(ex) { };
try {fm.all('Department2').value= arrResult[0][11]; } catch(ex) { };
try {fm.all('HeadShip2').value= arrResult[0][12]; } catch(ex) { };
try {fm.all('GrpPhone2').value= arrResult[0][13]; } catch(ex) { };
try {fm.all('E_Mail2').value= arrResult[0][14]; } catch(ex) { };
try {fm.all('Fax2').value= arrResult[0][15]; } catch(ex) { };
try {fm.all('Operator').value= arrResult[0][16]; } catch(ex) { };
try {fm.all('MakeDate').value= arrResult[0][17]; } catch(ex) { };
try {fm.all('MakeTime').value= arrResult[0][18]; } catch(ex) { };
try {fm.all('ModifyDate').value= arrResult[0][19]; } catch(ex) { };
try {fm.all('ModifyTime').value= arrResult[0][20]; } catch(ex) { };
//������ldgrp��
try {fm.all('BusinessType').value= arrResult[0][22];} catch(ex) { };
try {fm.all('GrpNature').value= arrResult[0][23]; } catch(ex) { };
try {fm.all('Peoples').value= arrResult[0][24]; } catch(ex) { };
try {fm.all('RgtMoney').value= arrResult[0][25]; } catch(ex) { };
try {fm.all('Asset').value= arrResult[0][26]; } catch(ex) { };
try {fm.all('NetProfitRate').value= arrResult[0][27];} catch(ex) { };
try {fm.all('MainBussiness').value= arrResult[0][28];} catch(ex) { };
try {fm.all('Corporation').value= arrResult[0][29];  } catch(ex) { };
try {fm.all('ComAera').value= arrResult[0][30]; } catch(ex) { };
try {fm.all('Fax').value= arrResult[0][31]; } catch(ex) { };
try {fm.all('Phone').value= arrResult[0][32]; } catch(ex) { };
try {fm.all('FoundDate').value= arrResult[0][33]; } catch(ex) { };
try {fm.all('AppGrpNo').value= arrResult[0][34]; } catch(ex) { };
try {fm.all('AppGrpName').value= arrResult[0][35]; } catch(ex) { };
}
function displayAppntGrp( cArr )
{
    // ��LDGrp��ȡ����
    try { fm.all('AppGrpNo').value = cArr[0]; } catch(ex) { };
    try { fm.all('Password').value = cArr[1]; } catch(ex) { };
    try { fm.all('AppGrpName').value = cArr[2]; } catch(ex) { };
    try { fm.all('GrpAddressCode').value = cArr[3]; } catch(ex) { };
    try { fm.all('AppGrpAddress').value = cArr[4]; } catch(ex) { };
    try { fm.all('AppGrpZipCode').value = cArr[5]; } catch(ex) { };
    try { fm.all('BusinessType').value = cArr[6]; } catch(ex) { };
    try { fm.all('GrpNature').value = cArr[7]; } catch(ex) { };
    try { fm.all('Peoples').value = cArr[8]; } catch(ex) { };
    try { fm.all('RgtMoney').value = cArr[9]; } catch(ex) { };
    try { fm.all('Asset').value = cArr[10]; } catch(ex) { };
    try { fm.all('NetProfitRate').value = cArr[11]; } catch(ex) { };
    try { fm.all('MainBussiness').value = cArr[12]; } catch(ex) { };
    try { fm.all('Corporation').value = cArr[13]; } catch(ex) { };
    try { fm.all('ComAera').value = cArr[14]; } catch(ex) { };
    try { fm.all('LinkMan1').value = cArr[15]; } catch(ex) { };
    try { fm.all('Department1').value = cArr[16]; } catch(ex) { };
    try { fm.all('HeadShip1').value = cArr[17]; } catch(ex) { };
    try { fm.all('Phone1').value = cArr[18]; } catch(ex) { };
    try { fm.all('E_Mail1').value = cArr[19]; } catch(ex) { };
    try { fm.all('Fax1').value = cArr[20]; } catch(ex) { };
    try { fm.all('LinkMan2').value = cArr[21]; } catch(ex) { };
    try { fm.all('Department2').value = cArr[22]; } catch(ex) { };
    try { fm.all('HeadShip2').value = cArr[23]; } catch(ex) { };
    try { fm.all('Phone2').value = cArr[24]; } catch(ex) { };
    try { fm.all('E_Mail2').value = cArr[25]; } catch(ex) { };
    try { fm.all('Fax2').value = cArr[26]; } catch(ex) { };
    try { fm.all('Fax').value = cArr[27]; } catch(ex) { };
    try { fm.all('Phone').value = cArr[28]; } catch(ex) { };
    try { fm.all('GetFlag').value = cArr[29]; } catch(ex) { };
    try { fm.all('Satrap').value = cArr[30]; } catch(ex) { };
    try { fm.all('EMail').value = cArr[31]; } catch(ex) { };
    try { fm.all('FoundDate').value = cArr[32]; } catch(ex) { };
    try { fm.all('BankAccNo').value = cArr[33]; } catch(ex) { };
    try { fm.all('BankCode').value = cArr[34]; } catch(ex) { };
    try { fm.all('GrpGroupNo').value = cArr[35]; } catch(ex) { };
    try { fm.all('State').value = cArr[36]; } catch(ex) { };
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
    try{fm.all('AddressNo').value=arrResult[0][10];}catch(ex){};
    try{fm.all('SequenceNo').value=arrResult[0][11];}catch(ex){};
    //try{fm.all('Name').value=arrResult[0][12];}catch(ex){};
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
    try{fm.all('InsuredStat').value=arrResult[0][41];}catch(ex){};
    try{fm.all('MakeDate').value=arrResult[0][42];}catch(ex){};
    try{fm.all('MakeTime').value=arrResult[0][43];}catch(ex){};
    try{fm.all('ModifyDate').value=arrResult[0][44];}catch(ex){};
    try{fm.all('ModifyTime').value=arrResult[0][45];}catch(ex){};
    try{fm.all('GrpName').value= arrResult[0][46];}catch(ex){};
    try{fm.all('HomeAddress').value= arrResult[0][47];}catch(ex){};
    try{fm.all('HomeZipCode').value= arrResult[0][48];}catch(ex){};
    try{fm.all('Phone').value= arrResult[0][49];}catch(ex){};
    try{fm.all('Phone2').value= arrResult[0][50];}catch(ex){};
}
//FATCA��Ϣ¼�� 
function FATCAInput(){

	window.open("../app/FatcaInputMain.jsp?ContNo="+fm.all("ContNo").value+"&LoadFlag="+LoadFlag+"&ManageCom="+fm.all("ManageCom").value+"&RiskCode="+fm.all("RiskCode").value, "",sFeatures);
}
//*********************************************************************
function showAppnt1()
{
    if( mOperate == 0 )
    {
        mOperate = 2;
        showInfo = window.open( "../sys/LDPersonQuery.html" ,"",sFeatures);
    }
}

//*********************************************************************
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
  //divSamePerson.style.display = "none";
  DivLCInsured.style.display = "none";
}
//��ϴǮ��Ϣ¼��
function AMLInput(){
			 
		var mySql85=new SqlClass();
		var sqlid85="ProposalInputSql85";
		mySql85.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql85.setSqlId(sqlid85);//ָ��ʹ�õ�Sql��id
		mySql85.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	
	    var strArr = easyExecSql(mySql85.getString());
	    
	    if (strArr == "0" || strArr == null) {
			myAlert(""+"����û��¼�����֣���¼�룡"+"");
			return false;
    	}
	
	window.open("../uw/UWAMLQueryMain.jsp?showFlag="+1+"&ContNo="+fm.all("ContNo").value+"&AppntNo="+fm.AppntNo.value, "window1",sFeatures);  	
}

//Ͷ��Ͷ���˸�֪¼��
function ULAppntImaprt(flag){
	window.open("../app/AppntImpartMain.jsp?ContNo="+fm.all("ContNo").value+"&flag="+flag, "window1",sFeatures);
}
//������Ѻ��ת��
function MortgageTransferButton(){
	if(LoadFlag==1||LoadFlag==3){
		showSave = "Y";
	}else{
		showSave = "N";
	}
	window.open("../app/ContTranferInfoMain.jsp?ContNo="+fm.all("ContNo").value+"&ManageCom="+fm.all("ManageCom").value+"&showSave="+showSave, "window1",sFeatures);
}
//�����¼��
function QuestInput()
{
   
    cContNo = fm.all("ContNo").value;  //��������
        if(LoadFlag=="2"||LoadFlag=="4"){
            if(mSwitch.getVar( "ProposalGrpContNo" )==""){
              myAlert(""+"���޼����ͬͶ�����ţ����ȱ���!"+"");
                }
                else{
            window.open("./GrpQuestInputMain.jsp?GrpContNo="+mSwitch.getVar( "ProposalGrpContNo" )+"&Flag="+LoadFlag,"",sFeatures);
                    }
            }
            else{
                    if(cContNo == ""){
                   myAlert(""+"���޺�ͬͶ�����ţ����ȱ���!"+"");
                     }
                     else
                    {
                window.open("../uw/MSQuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+ActivityID,"window1",sFeatures);
                    }
            }
}
function QuestQuery()
{

   cContNo = fm.all("ContNo").value;  //��������

   if(LoadFlag=="2"||LoadFlag=="4"||LoadFlag=="13"){
    if(mSwitch.getVar( "ProposalGrpContNo" )==""||mSwitch.getVar( "ProposalGrpContNo" )==null)
    {
        myAlert(""+"����ѡ��һ����������Ͷ����!"+"");
        return ;
        }
        else{
            window.open("./GrpQuestQueryMain.jsp?GrpContNo="+mSwitch.getVar( "ProposalGrpContNo" )+"&Flag="+LoadFlag,"",sFeatures);
        }
   }
   else{
        if(cContNo == ""){
           myAlert(""+"���޺�ͬͶ�����ţ����ȱ���!"+"");
     }
    else{
               window.open("../uw/QuestQueryMain.jsp?ContNo="+cContNo+"&Flag="+LoadFlag,"window1",sFeatures);
        }
   }
}
//��ʾͶ��������
function showAppntAge() {
  var age = calAge(fm.all("AppntBirthday").value);
  var today = new Date();

  fm.all("AppntBirthday").title = ""+"Ͷ���˵�����"+" " + today.toLocaleString()
                                + " "+"\n������Ϊ��"+"" + age + " "+"��!"+"";
}

//��ʾ����������
function showAge() {
  var age = calAge(fm.all("Birthday").value);
  var today = new Date();

  fm.all("Birthday").title = ""+"�����˵�����"+" " + today.toLocaleString()
                           + " "+"\n������Ϊ��"+"" + age + " "+"��!"+"";
}

//����Ͷ���˳������ڣ�����գ������֤���У�������֤ȡ��ȡ�������ؿո�;
function checkBirthday()
{
    try{
          var strBrithday = "";
          if(trim(fm.all("AppntBirthday").value)==""||fm.all("AppntBirthday").value==null)
          {
            if (trim(fm.all("AppntIDType").value) == "0")
             {
               strBrithday= getBirthdatByIdNo(fm.all("AppntIDNo").value);
               if(strBrithday=="") passVerify=false;

               fm.all("AppntBirthday").value= strBrithday;
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
       var riskcode=fm.all("RiskCode").value;


		var sqlid26="ProposalInputSql26";
		var mySql26=new SqlClass();
		mySql26.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql26.setSqlId(sqlid26);//ָ��ʹ�õ�Sql��id
		mySql26.addSubPara("1");//ָ������Ĳ���
	    var tSql=mySql26.getString();

       //var tSql = "select Sysvarvalue from LDSysVar where Sysvar='NotVerifyRiskcode'";
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
        myAlert(""+"û�����ձ�����"+","+"���ܽ��и�����¼��!"+"");
        mCurOperateFlag="";        //��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.
        return false
    }

    var arrLCPol = new Array();
    var arrQueryResult = null;
    // ��������Ϣ����
	
		var sqlid27="ProposalInputSql27";
		var mySql27=new SqlClass();
		mySql27.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql27.setSqlId(sqlid27);//ָ��ʹ�õ�Sql��id
		mySql27.addSubPara(cPolNo);//ָ������Ĳ���
	    var sql=mySql27.getString();
	
//    var sql = "select * from lcpol where polno='" + cPolNo + "' "
//            + "and riskcode in "
//            + "( select riskcode from LMRiskApp where SubRiskFlag = 'M' )";

    arrQueryResult = easyExecSql( sql , 1, 0);

    if (arrQueryResult == null) {
        mCurOperateFlag="";        //��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.

        //top.mainPolNo = "";
        mainRiskPolNo = "";

        myAlert(""+"��ȡ������Ϣʧ��"+","+"���ܽ��и�����¼��!"+"");
        return false
    }

    arrLCPol = arrQueryResult[0];
    displayPol( arrLCPol );
    displayPolSpec( arrLCPol );//��ʼ������Ҫ��ı�����Ϣ

    fm.all("MainPolNo").value = cPolNo;
    var tAR;

    //����Ͷ������Ϣ
      arrQueryResult = null;
	  
	  	var sqlid28="ProposalInputSql28";
		var mySql28=new SqlClass();
		mySql28.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql28.setSqlId(sqlid28);//ָ��ʹ�õ�Sql��id
		mySql28.addSubPara(cPolNo);//ָ������Ĳ���
		mySql28.addSubPara(arrLCPol[26]);//ָ������Ĳ���
	    var sql28=mySql28.getString();
	  
	  arrQueryResult = easyExecSql(sql28, 1, 0);
      //arrQueryResult = easyExecSql("select * from lcappntind where polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[26]+"'", 1, 0);
      tAR = arrQueryResult[0];
      displayPolAppnt(tAR);
      try { fm.all('AppntRelationToInsured').value = '00'; } catch(ex) { };
      try { fm.all("SamePersonFlag").checked = true; } catch(ex) { };
      try {isSamePerson();} catch(ex) { };
      try { fm.all("SamePersonFlag").disabled=true} catch(ex) { };


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

        try { fm.all('PayEndYear').value = cArr[109]; } catch(ex) { };
        try { fm.all('PayEndYearFlag').value = cArr[108]; } catch(ex) { };
        try { fm.all('PayIntv').value = cArr[57]; } catch(ex) { };
        try { fm.all('Amnt').value = cArr[39]; } catch(ex) { };     //���յı��Ѽ����յı���

    } catch(ex) {
      myAlert("displayPolSpec err:" + ex + "\ndata is:" + cArr);
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
        myAlert(""+"û�����ձ�����"+","+"���ܽ��и�����¼��!"+"");
        mCurOperateFlag="";        //��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.
        return false
    }

    var arrLCPol = new Array();
    var arrQueryResult = null;
    // ��������Ϣ����
	
		  	var sqlid29="ProposalInputSql29";
		var mySql29=new SqlClass();
		mySql29.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql29.setSqlId(sqlid29);//ָ��ʹ�õ�Sql��id
		mySql29.addSubPara(cPolNo);//ָ������Ĳ���
	    var sql=mySql29.getString();
	
//    var sql = "select * from lcpol where polno='" + cPolNo + "' "
//            + "and riskcode in "
//            + "( select riskcode from LMRiskApp where SubRiskFlag = 'M' )";

    arrQueryResult = easyExecSql( sql , 1, 0);

    if (arrQueryResult == null) {
        mCurOperateFlag="";        //��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.

        //top.mainPolNo = "";
        fm.all('mainRiskPolNo').value = "";

        myAlert(""+"��ȡ������Ϣʧ��"+","+"���ܽ��и�����¼��!"+"");
        return false
    }

    arrLCPol = arrQueryResult[0];
    displayPol( arrLCPol );

    //��ʼ������Ҫ��ı�����Ϣ--//���յı��Ѽ����յı���(ȡ���ձ��Ѻ�500000֮��Сֵ)
    try
    {
         if(arrLCPol[39]<500000)
           fm.all('Amnt').value = arrLCPol[39];
         else
           fm.all('Amnt').value = 500000;
    }
     catch(ex) { myAlert(ex);}


    fm.all("MainPolNo").value = cPolNo;
    var tAR;

    //Ͷ������Ϣ
    if (arrLCPol[28]=="2") {     //����Ͷ������Ϣ
      arrQueryResult = null;
	  
	  	var sqlid30="ProposalInputSql30";
		var mySql30=new SqlClass();
		mySql30.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql30.setSqlId(sqlid30);//ָ��ʹ�õ�Sql��id
		mySql30.addSubPara(cPolNo);//ָ������Ĳ���
		mySql30.addSubPara(arrLCPol[26]);//ָ������Ĳ���
	    var sql30=mySql30.getString();
	  
	  arrQueryResult = easyExecSql(sql30, 1, 0);
      //arrQueryResult = easyExecSql("select * from lcappntgrp where polno='"+cPolNo+"'"+" and grpno='"+arrLCPol[26]+"'", 1, 0);
      tAR = arrQueryResult[0];
      displayPolAppntGrp(tAR);
    } else {                     //����Ͷ������Ϣ
      arrQueryResult = null;
	  
	  	var sqlid31="ProposalInputSql31";
		var mySql31=new SqlClass();
		mySql31.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql31.setSqlId(sqlid31);//ָ��ʹ�õ�Sql��id
		mySql31.addSubPara(cPolNo);//ָ������Ĳ���
		mySql31.addSubPara(arrLCPol[26]);//ָ������Ĳ���
	    var sql31=mySql31.getString();
	  
	  arrQueryResult = easyExecSql(sql31, 1, 0);
      //arrQueryResult = easyExecSql("select * from lcappntind where polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[26]+"'", 1, 0);
      tAR = arrQueryResult[0];
      displayPolAppnt(tAR);
    }

    // ��������Ϣ����
    if (arrLCPol[18] == arrLCPol[26]) {
      fm.all("SamePersonFlag").checked = true;
        parent.fraInterface.isSamePersonQuery();
    parent.fraInterface.fm.all("CustomerNo").value = arrLCPol[18];
    }
    //else {
        arrQueryResult = null;
		
		var sqlid32="ProposalInputSql32";
		var mySql32=new SqlClass();
		mySql32.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql32.setSqlId(sqlid32);//ָ��ʹ�õ�Sql��id
		mySql32.addSubPara(cPolNo);//ָ������Ĳ���
		mySql32.addSubPara(arrLCPol[18]);//ָ������Ĳ���
	    var sql32=mySql32.getString();
		
		 arrQueryResult = easyExecSql(sql32, 1, 0);
       // arrQueryResult = easyExecSql("select * from lcinsured where polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[18]+"'", 1, 0);
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
    try { fm.all('CustomerNo').value = cArr[1]; } catch(ex) { };
    try { fm.all('Password').value = cArr[5]; } catch(ex) { };
    //try { fm.all('Name').value = cArr[6]; } catch(ex) { };
    try { fm.all('Sex').value = cArr[7]; } catch(ex) { };
    try { fm.all('Birthday').value = cArr[8]; } catch(ex) { };
    try { fm.all('NativePlace').value = cArr[9]; } catch(ex) { };
    try { fm.all('Nationality').value = cArr[10]; } catch(ex) { };
    try { fm.all('Marriage').value = cArr[11]; } catch(ex) { };
    try { fm.all('MarriageDate').value = cArr[12]; } catch(ex) { };
    try { fm.all('OccupationType').value = cArr[13]; } catch(ex) { };
    try { fm.all('StartWorkDate').value = cArr[14]; } catch(ex) { };
    try { fm.all('Salary').value = cArr[15]; } catch(ex) { };
    try { fm.all('Health').value = cArr[16]; } catch(ex) { };
    try { fm.all('Stature').value = cArr[17]; } catch(ex) { };
    try { fm.all('Avoirdupois').value = cArr[18]; } catch(ex) { };
    try { fm.all('CreditGrade').value = cArr[19]; } catch(ex) { };
    try { fm.all('IDType').value = cArr[20]; } catch(ex) { };
    try { fm.all('Proterty').value = cArr[21]; } catch(ex) { };
    try { fm.all('IDNo').value = cArr[22]; } catch(ex) { };
    try { fm.all('OthIDType').value = cArr[23]; } catch(ex) { };
    try { fm.all('OthIDNo').value = cArr[24]; } catch(ex) { };
    try { fm.all('ICNo').value = cArr[25]; } catch(ex) { };
    try { fm.all('HomeAddressCode').value = cArr[26]; } catch(ex) { };
    try { fm.all('HomeAddress').value = cArr[27]; } catch(ex) { };
    try { fm.all('PostalAddress').value = cArr[28]; } catch(ex) { };
    try { fm.all('ZipCode').value = cArr[29]; } catch(ex) { };
    try { fm.all('Phone').value = cArr[30]; } catch(ex) { };
    try { fm.all('BP').value = cArr[31]; } catch(ex) { };
    try { fm.all('Mobile').value = cArr[32]; } catch(ex) { };
    try { fm.all('EMail').value = cArr[33]; } catch(ex) { };
    //try { fm.all('BankCode').value = cArr[34]; } catch(ex) { };
    //try { fm.all('BankAccNo').value = cArr[35]; } catch(ex) { };
    try { fm.all('JoinCompanyDate').value = cArr[34]; } catch(ex) { };
    try { fm.all('Position').value = cArr[35]; } catch(ex) { };
    try { fm.all('GrpNo').value = cArr[36]; } catch(ex) { };
    try { fm.all('GrpName').value = cArr[37]; } catch(ex) { };
    try { fm.all('GrpPhone').value = cArr[38]; } catch(ex) { };
    try { fm.all('GrpAddressCode').value = cArr[39]; } catch(ex) { };
    try { fm.all('GrpAddress').value = cArr[40]; } catch(ex) { };
    try { fm.all('DeathDate').value = cArr[41]; } catch(ex) { };
    try { fm.all('State').value = cArr[43]; } catch(ex) { };
    try { fm.all('WorkType').value = cArr[46]; } catch(ex) { };
    try { fm.all('PluralityType').value = cArr[47]; } catch(ex) { };
    try { fm.all('OccupationCode').value = cArr[49]; } catch(ex) { };
    try { fm.all('Degree').value = cArr[48]; } catch(ex) { };
    try { fm.all('GrpZipCode').value = cArr[50]; } catch(ex) { };
    try { fm.all('SmokeFlag').value = cArr[51]; } catch(ex) { };
    try { fm.all('RgtAddress').value = cArr[52]; } catch(ex) { };

}




//����ҳ���ʼ��ʱ���������ֵ����⴦��
function initDealForSpecRisk(cRiskCode)
{
  try{
    //�����211801
    if(cRiskCode=='211801')
    {
        DutyGrid.addOne();
        DutyGrid.setRowColData(0, 1, '610001');
        DutyGrid.setRowColData(0, 2, ''+"��������1��"+'');
        DutyGrid.addOne();
        DutyGrid.setRowColData(1, 1, '610002');
        DutyGrid.setRowColData(1, 2, ''+"��������2��"+'');
        DutyGrid.addOne();
        DutyGrid.setRowColData(2, 1, '610003');
        DutyGrid.setRowColData(2, 2, ''+"��������3��"+'');
        DutyGrid.addOne();
        DutyGrid.setRowColData(3, 1, '610004');
        DutyGrid.setRowColData(3, 2, ''+"��������4��"+'');
        DutyGrid.addOne();
        DutyGrid.setRowColData(4, 1, '610005');
        DutyGrid.setRowColData(4, 2, ''+"��������5��"+'');
        DutyGrid.addOne();
        DutyGrid.setRowColData(5, 1, '610006');
        DutyGrid.setRowColData(5, 2, ''+"��������6��"+'');
        DutyGrid.addOne();
        DutyGrid.setRowColData(6, 1, '610007');
        DutyGrid.setRowColData(6, 2, ''+"��������7��"+'');
        DutyGrid.addOne();
        DutyGrid.setRowColData(7, 1, '610008');
        DutyGrid.setRowColData(7, 2, ''+"��������8��"+'');
        DutyGrid.addOne();
        DutyGrid.setRowColData(8, 1, '610009');
        DutyGrid.setRowColData(8, 2, ''+"ŮԱ����������"+'');
        DutyGrid.lock();


    }

    //�������
    if(cRiskCode=='212401')
    {

        PremGrid.addOne();
        PremGrid.setRowColData(0, 1, '601001');
        PremGrid.setRowColData(0, 2, '601101');
        PremGrid.setRowColData(0, 3, ''+"���彻��"+'');
        PremGrid.addOne();
        PremGrid.setRowColData(1, 1, '601001');
        PremGrid.setRowColData(1, 2, '601102');
        PremGrid.setRowColData(1, 3, ''+"���˽���"+'');
        PremGrid.lock();

    }

    //��ҵ����
    if(cRiskCode=='211701')
    {
		
		var sqlid33="ProposalInputSql33";
		var mySql33=new SqlClass();
		mySql33.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql33.setSqlId(sqlid33);//ָ��ʹ�õ�Sql��id
		mySql33.addSubPara(cRiskCode);//ָ������Ĳ���
	    var strSql=mySql33.getString();
		
//        var strSql = "select *  from lmdutypayrela where dutycode in  "
//                   + " (select dutycode from lmriskduty where riskcode='"+cRiskCode+"')";
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
        //alert("fm.all('inpNeedDutyGrid').value:"+fm.all('inpNeedDutyGrid').value);
        if(fm.all('inpNeedDutyGrid').value==1){
         initDutyGrid();  //�������ֳ�ʼ������¼���
         
		 var sqlid34="ProposalInputSql34";
		var mySql34=new SqlClass();
		mySql34.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql34.setSqlId(sqlid34);//ָ��ʹ�õ�Sql��id
		mySql34.addSubPara(cRiskCode);//ָ������Ĳ���
	    strSqll=mySql34.getString();
		 
//         strSql = "select dutycode,dutyname,'','','','','','','','','',''  from lmduty where dutycode in "
//                   + " (select dutycode from lmriskduty where riskcode='"+cRiskCode+"' and choflag!='B' and SpecFlag='N')";
         turnPage.queryModal(strSqll, DutyGrid);
         var cDutyCode="";
         var tSql="";
         for(var i=0;i<=DutyGrid.mulLineCount-1;i++){
           cDutyCode=DutyGrid.getRowColData(i,1);
           if(cDutyCode=="U17302"){
        	   DutyGrid.setRowColDataCustomize(i,13,DutyGrid.getRowColData(i,13),null,"readonly");
           }
		var sqlid35="ProposalInputSql35";
		var mySql35=new SqlClass();
		mySql35.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql35.setSqlId(sqlid35);//ָ��ʹ�õ�Sql��id
		mySql35.addSubPara(cRiskCode);//ָ������Ĳ���
		mySql35.addSubPara(cDutyCode);//ָ������Ĳ���
	    tSql=mySql35.getString();
		   
        //   tSql="select choflag from lmriskduty where riskcode='"+cRiskCode+"' and dutycode='"+cDutyCode+"'";
           var arrResult=easyExecSql(tSql,1,0);
           if(arrResult[0]=="M"){
               //DutyGrid.checkBoxSel(i+1);
        	   checkDuty("DutyGrid",i,true);
             //fm.all('DutyGridChk')[i].checked=true;
        	 //DutyGrid.checkBoxClick(DutyGrid,26);
           }
         }
         DutyGrid.lock;

        }
        
      //  alert("fm.all('inpNeedPremGrid').value:"+fm.all('inpNeedPremGrid').value);
      var needPremCurrency = '0';
      try
      {
      	 needPremCurrency = fm.all('needPremCurrency').value;
      	// alert("needPremCurrency@@@:"+needPremCurrency);
      	}
      	catch(e)
      	{
      		inpNeedPremCurrencyFlag = '0';
      		}
      		
     // 		alert("inpNeedPremCurrencyFlag:"+inpNeedPremCurrencyFlag);
      
        if(fm.all('inpNeedPremGrid').value==1&&needPremCurrency=='0'){
			//initPremGrid();
		var sqlid36="ProposalInputSql36";
		var mySql36=new SqlClass();
		mySql36.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql36.setSqlId(sqlid36);//ָ��ʹ�õ�Sql��id
		mySql36.addSubPara(cRiskCode);//ָ������Ĳ���
	    tSql=mySql36.getString();
			
//          strSql = "select a.dutycode,a.payplancode,a.payplanname,'','','','','','' from lmdutypayrela a where dutycode in  "
//                   + " (select dutycode from lmriskduty where riskcode='"+cRiskCode+"' and SpecFlag='N')";

          turnPage.queryModal(tSql, PremGrid);
          PremGrid.lock;

        }
        
         if(fm.all('inpNeedPremGrid').value==1&&needPremCurrency=='1'){
			//initPremGrid('1');
		var sqlid78="ProposalInputSql78";
		var mySql78=new SqlClass();
		mySql78.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql78.setSqlId(sqlid78);//ָ��ʹ�õ�Sql��id
		mySql78.addSubPara(cRiskCode);//ָ������Ĳ���
	    tSql=mySql78.getString();
			
//          strSql = "select a.dutycode,a.payplancode,a.payplanname,'','','','','','' from lmdutypayrela a where dutycode in  "
//                   + " (select dutycode from lmriskduty where riskcode='"+cRiskCode+"' and SpecFlag='N')";

          turnPage.queryModal(tSql, PremGrid);
          //alert("PremGrid.mulLineCount:"+PremGrid.mulLineCount);
          //PremGrid.checkAll(PremGrid,PremGrid.mulLineCount);
        /*  for(i=1;i<=PremGrid.mulLineCount;i++)
          {
         	 PremGrid.checkBoxSel(i);
        	}*/
        	//PremGrid.checkBoxAll();
          PremGrid.lock;
	
        }
        


  }catch(ex) {}

}
function queryAgent()
{
    if(fm.all('ManageCom').value==""){
         myAlert(""+"����¼����������Ϣ��"+"");
         return;
    }
    if(fm.all('AgentCode').value == "") {
      //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+fm.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
      var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+fm.all('ManageCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
      }
    if(fm.all('AgentCode').value != "")  {
    var cAgentCode = fm.AgentCode.value;  //��������
    
			var sqlid37="ProposalInputSql37";
		var mySql37=new SqlClass();
		mySql37.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql37.setSqlId(sqlid37);//ָ��ʹ�õ�Sql��id
		mySql37.addSubPara(cAgentCode);//ָ������Ĳ���
	    var strSql =mySql37.getString();
	
   // var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+fm.all('ManageCom').value+"'";
    var arrResult = easyExecSql(strSql);
    if (arrResult != null) {
      fm.AgentGroup.value = arrResult[0][2];
      myAlert(""+"��ѯ���:"+"  "+"�����˱���:["+""+arrResult[0][0]+"] "+"����������Ϊ:["+""+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     myAlert(""+"����Ϊ:["+""+fm.all('AgentCode').value+""+"]�Ĵ����˲����ڣ���ȷ��!"+"");
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
    if(fm.all('ManageCom').value==""){
         myAlert(""+"����¼����������Ϣ��"+"");
         return;
    }
    if(fm.all('AgentCode').value != "" && fm.all('AgentCode').value.length==10 )     {
    var cAgentCode = fm.AgentCode.value;  //��������
    
		var sqlid38="ProposalInputSql38";
		var mySql38=new SqlClass();
		mySql38.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql38.setSqlId(sqlid38);//ָ��ʹ�õ�Sql��id
		mySql38.addSubPara(cAgentCode);//ָ������Ĳ���
	    var strSql =mySql38.getString();
	
   // var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+fm.all('ManageCom').value+"'";
    var arrResult = easyExecSql(strSql);
    if (arrResult != null) {
      fm.AgentGroup.value = arrResult[0][2];
      myAlert(""+"��ѯ���:"+"  "+"�����˱���:["+""+arrResult[0][0]+"] "+"����������Ϊ:["+""+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     myAlert(""+"����Ϊ:["+""+fm.all('AgentCode').value+""+"]�Ĵ����˲����ڣ���ȷ��!"+"");
     }
    }
}
  function returnparent()
  {
 
    //edit by yaory
    if(LoadFlag=="99")
    {
        location.href="ContInsuredInput.jsp?LoadFlag="+LoadFlag+"&prtNo="+prtNo+"&scantype="+scantype+"&checktype="+checktype+"";
        return;
    }
    var backstr=fm.all("ContNo").value;
    mSwitch.deleteVar("ContNo");
      mSwitch.addVar("ContNo", "", backstr);
      mSwitch.updateVar("ContNo", "", backstr);
//      location.href="ContInsuredInput.jsp?LoadFlag="+LoadFlag+"&ContType="+ContType+"&scantype="+scantype+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&checktype="+checktype;
    //���ڸ��գ����շ��ص�ҳ�治ͬ�ڴ˽�������
    //LoadFlag='1' ����
    //LoadFlag='2' ����
    //edit by yaory

    if(LoadFlag == "7" && EdorType =="NI"){
      location.href="../bq/GEdorTypeNI.jsp";
      return;
    }
    if(LoadFlag == "8" && EdorType =="AB"){
    	var ContNo=mSwitch.getVar("ContNo");
    	var EdorAppDate=mSwitch.getVar("EdorAppDate");
    	if(IsCValidate==null){
    	IsCValidate=mSwitch.getVar("IsCValidate");
    	}
    	var EdorAcceptNo=mSwitch.getVar("EdorAcceptNo");
    		
    	var params={ContNo:ContNo,
    				EdorAppDate:EdorAppDate,
    				IsCValidate:IsCValidate,
    				EdorAcceptNo:EdorAcceptNo,
    				Flag:"ABBack"
    	};
    	$.post("../bq/PEdorTypeABQuery.jsp?DateFlag="+new Date(),
    		params,
    		function(data){
    			var QueryFlag=mSwitch.getVar("QueryFlag");
    			if(QueryFlag=="EdorQuery"){
    				location.href="../bqs/PEdorTypeABInput.jsp";
    			}else{
 					location.href="../bq/PEdorTypeABInput.jsp";
    			}
    		},
    	  "json");
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
//(GrpContNo,LoadFlag);//���ݼ����ͬ�Ų��������Ϣ
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
		mySql39.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql39.setSqlId(sqlid39);//ָ��ʹ�õ�Sql��id
		mySql39.addSubPara(GrpContNo);//ָ������Ĳ���
	   strsql =mySql39.getString();
	
   // strsql = "select riskcode,riskname from lmriskapp where riskcode in (select riskcode from LCGrpPol where GrpContNo='"+GrpContNo+"')" ;
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
function getRiskByGrpAll(){
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
	
		var sqlid40="ProposalInputSql40";
		var mySql40=new SqlClass();
		mySql40.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql40.setSqlId(sqlid40);//ָ��ʹ�õ�Sql��id
		//mySql40.addSubPara(GrpContNo);//ָ������Ĳ���
	   strsql =mySql40.getString();
	
   // strsql = "select RiskCode, RiskName from LMRiskApp where RiskProp in ('G','A','B','D') order by RiskCode" ;
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != ""){
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        m = turnPage.arrDataCacheSet.length;
        for (i = 0; i < m; i++){
            j = i + 1;
//          tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
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
		mySql41.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql41.setSqlId(sqlid41);//ָ��ʹ�õ�Sql��id
		//mySql41.addSubPara(GrpContNo);//ָ������Ĳ���
	   strsql =mySql41.getString();
	
//    strsql = "select RiskCode, RiskName from LMRiskApp where RiskProp in ('I','A','C','D')"
//           + " order by RiskCode";;
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
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
	
		var sqlid42="ProposalInputSql42";
		var mySql42=new SqlClass();
		mySql42.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql42.setSqlId(sqlid42);//ָ��ʹ�õ�Sql��id
        mySql42.addSubPara(GrpContNo);//ָ������Ĳ���
        mySql42.addSubPara(ContPlanCode);//ָ������Ĳ���
	   strsql =mySql42.getString();
	
    //strsql = "select b.RiskCode, b.RiskName from LCContPlanRisk a,LMRiskApp b where  a.GrpContNo='"+GrpContNo+"' and a.ContPlanCode='"+ContPlanCode+"' and a.riskcode=b.riskcode";
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != ""){
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        m = turnPage.arrDataCacheSet.length;
        for (i = 0; i < m; i++){
            j = i + 1;
//          tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
            tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
        }
    }
    return tCodeData;
}

/*************************************************************************************
��¼������ʱ��ϵͳ���ɵı���������¼��ı��ѽ���У�飬
�������¼��ı�����ϵͳ���ɵı��Ѳ�����ϵͳҪ������ʾ�����Կɱ���,�����Կ���ת
�����������ͬ��<Ͷ������>;     ����:true or false
*************************************************************************************/
function checkpayfee(ContNo){
    var tContNo=ContNo;
    var tTempFee="";//����¼��ı���
    var tPremFee="";//ϵͳ���ɵı���
    //��ѯ����¼��ı���
	
		var sqlid43="ProposalInputSql43";
		var mySql43=new SqlClass();
		mySql43.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql43.setSqlId(sqlid43);//ָ��ʹ�õ�Sql��id
        mySql43.addSubPara(tContNo);//ָ������Ĳ���
	   var tempfeeSQL =mySql43.getString();
	
//    var tempfeeSQL="select sum(nvl(paymoney,0)) from ljtempfee where tempfeetype='1' and confdate is null "
//        +" and otherno=(select contno from lccont where prtno= '"+tContNo+"')";
    var TempFeeArr=easyExecSql(tempfeeSQL);
    if(TempFeeArr!=null){
        tTempFee=TempFeeArr[0][0];
    }
    //��ѯϵͳ���ɵı���
	
			var sqlid44="ProposalInputSql44";
		var mySql44=new SqlClass();
		mySql44.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql44.setSqlId(sqlid44);//ָ��ʹ�õ�Sql��id
        mySql44.addSubPara(tContNo);//ָ������Ĳ���
	   var premfeeSQL =mySql44.getString();
	
//    var premfeeSQL="select sum(nvl(prem,0)) from lcpol where 1=1 "
//        +" and contno=(select contno from lccont where prtno= '"+tContNo+"')";
		
    var PremFeeArr=easyExecSql(premfeeSQL);
    tPremFee=PremFeeArr[0][0];
    if(PremFeeArr!=null){
        tPremFee=PremFeeArr[0][0];
        if(tPremFee==null || tPremFee=="" || tPremFee=="null")
        {
        myAlert(""+"��ѯ��Ͷ������������Ϣ�����ɱ�������ʧ�ܣ�����"+"");
        return false;
        }
    }
    //�Ƚϡ���ѯ����¼��ı��ѡ� �� ����ѯϵͳ���ɵı��ѡ��Ƿ���ȣ��粻����򵯳���Ϣ��ʾ
    if(tTempFee!="" && tPremFee!="" && tTempFee!="null" && tPremFee!="null" && (tTempFee!=tPremFee)){
        var ErrInfo=""+"ע�⣺����¼��ı���["+""+tTempFee+""+"]��ϵͳ���ɵı���["+""+tPremFee+""+"]���ȡ�\n"+"";
        ErrInfo=ErrInfo+""+"ȷ����Ҫ������ȷ�豣���밴��ȷ���������򰴡�ȡ������"+"";
        if(confirm(ErrInfo)==false){
           return false;
        }
    }
    return true;
}

 /*********************************************************************
 *  �Ѻ�ͬ������Ϣ¼�����ȷ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function inputConfirm(wFlag){
    if (wFlag ==1 ) //¼�����ȷ��
    {
        if(fm.all('ContNo').value == "")
      {
        myAlert(""+"��ͬ��Ϣδ����"+","+"������������"+" "+"[¼�����]"+" "+"ȷ�ϣ�"+"");
        return;
      }
      if (!checkMainAndSubRiskInfo(fm.all('ContNo').value)) {
    	  myAlert(""+"�����սɷѷ�ʽ����ִ��ڲ�ͬ���뷵�����±��渽������Ϣ��"+"");
    	  return false;
      }

      var mySql123 = new SqlClass();
      mySql123.setResourceName("app.ContInputSql"); // ָ��ʹ�õ�properties�ļ���
      mySql123.setSqlId("ContInputSql123");// ָ��ʹ�õ�Sql��id
      mySql123.addSubPara(fm.ContNo.value);// ָ������Ĳ���
      mySql123.addSubPara(fm.ContNo.value);// ָ������Ĳ���
      var res123 = easyExecSql(mySql123.getString());
      if(res123=="1"){
    	  myAlert(""+/*���պ͸����յı��ͬ����ɾ�������ղ�����¼�룡*/""+""+""+"");
    	  return false;
      }
      
      var mySql124 = new SqlClass();
      mySql124.setResourceName("app.ContInputSql"); // ָ��ʹ�õ�properties�ļ���
      mySql124.setSqlId("ContInputSql124");// ָ��ʹ�õ�Sql��id
      mySql124.addSubPara(fm.ContNo.value);// ָ������Ĳ���
      mySql124.addSubPara(fm.ContNo.value);// ָ������Ĳ���
      var res124 = easyExecSql(mySql124.getString());
      if(res124=="1"){
    	  myAlert(""+/*���պ͸����յĽɸ����ڲ�ͬ����ɾ�������ղ�����¼�룡*/""+""+""+"");
    	  return false;
      }

      var mySql130 = new SqlClass();
      mySql130.setResourceName("app.ContInputSql"); // ָ��ʹ�õ�properties�ļ���
      mySql130.setSqlId("ContInputSql130");// ָ��ʹ�õ�Sql��id
      mySql130.addSubPara(fm.ContNo.value);// ָ������Ĳ���
      var res130 = easyExecSql(mySql130.getString());
      if(res130=="1"){
    	  myAlert(""+""+/*����FPD�~���A�U���M���罻���M̎��������*/""+"<F-"+/*�N�~�~��*/"����˻�"+">"+"");
      }
		
      //���¼�����ʴ����У��<����¼��ı�����ϵͳ���ɵı��Ѳ�����ϵͳҪ������ʾ�����Կɱ���>
        if(checkpayfee(fm.all('ContNo').value)==false)
        {
            return false;
        }
        
      //���fatca-������������У�鰡,�����ӵ�������fatca��Ʒ��������У�飬���û��������֣��������ֲ���fatca��Ʒ������Ҫ����У��
		var sqlid122 = "ContInputSql122";
		var mySql122 = new SqlClass();
		mySql122.setResourceName("app.ContInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql122.setSqlId(sqlid122);// ָ��ʹ�õ�Sql��id
		mySql122.addSubPara(fm.ContNo.value);// ָ������Ĳ���
		var res122 = easyExecSql(mySql122.getString());
//		###################################
//		### 2014-07-28 fatca phase 2.5 fix by Arthur To -- Start 
		if(res122=="1" && fm.SaleChnl.value != "04"){
			var sqlid120 = "ContInputSql120";
			var mySql120 = new SqlClass();
			mySql120.setResourceName("app.ContInputSql"); // ָ��ʹ�õ�properties�ļ���
			mySql120.setSqlId(sqlid120);// ָ��ʹ�õ�Sql��id
			mySql120.addSubPara(fm.ContNo.value);// ָ������Ĳ���
			mySql120.addSubPara(fm.ContNo.value);// ָ������Ĳ���
			var res120 = easyExecSql(mySql120.getString());
			if(res120=="1"){
				myAlert(""+""+","+""+"");
//				myAlert("�U�N��FATCA�aƷ,Ͷ���˻��׌�˞邀��,������FATCA�ļ�.");
//				myAlert(""+""+"");
//				### 2014-07-28 fatca phase 2.5 fix by Arthur To -- Finish
//				###################################
				return false;
			}
		}
		
        // Start - Validate the correctness of INTERFACEPTOSINFO and SUBRISKCODETOPLANCODEINFO - (2014/09/12)
		try {
			var mInterface = "<"+I18NMsg(/*Ոע��*/"L0000026170")+">"+I18NMsg(/*\n\n���������U�N*/"L0000026171")+" (" + fm.RiskCode.value + ") "+I18NMsg(/*δ�ܳɹ�����  Product Mapping*/"L0000026172")+", "+I18NMsg(/*����^�m��Ԓ*/"L0000026272")+", "+I18NMsg(/*��Ӱ�����ϵ�y���\��\n\n�Ƿ���Ҫ����?*/"L0000026770")+"";
	        var sInterface = "SELECT A.RiskCode, getRiskCodeToPlanCode(A.RiskCode, A.CValiDate, B.MateFlag, B.StaffFlag, C.JoinFlag, A.PayEndYear, A.PayEndYearFlag, A.Amnt, C.DutyCode, C.PlanLevel, A.InsuYear, A.InsuYearFlag, B.SaleChnl, A.Currency, A.NonParFlag, B.FirstTrialOperator, A.PayIntv) AS PlanCode FROM LCPOL A, LCCONT B, LCDUTY C WHERE A.CONTNO=B.CONTNO AND A.POLNO=C.POLNO AND A.MAINPOLNO=A.POLNO AND A.CONTNO='" + fm.ContNo.value + "'";
			var rInterface = easyExecSql(sInterface);
			if (rInterface != null) {
				if (rInterface[0][1] == "" || rInterface[0][1] == "FALSE") {
					if (confirm(mInterface)) {
						return false;
					}					
				} else {
					sInterface = "SELECT A.RiskCode, getSubRiskCodeToPlanCode('" + rInterface[0][0] + "', '" + rInterface[0][1] + "', A.RiskCode, B.PlanLevel, A.InsuredNo) AS SubPlanCode FROM LCPOL A, LCDUTY B WHERE A.POLNO=B.POLNO AND A.MAINPOLNO<>A.POLNO AND A.CONTNO='" + fm.ContNo.value + "'";
					rInterface = easyExecSql(sInterface);
					if (rInterface != null) {
						for(var i=0; i<rInterface.length; i++) {
							if (rInterface[i][1] == "" || rInterface[i][1] == "FALSE") {
								if (confirm(mInterface)) {
									return false;
								}													
							}
						}																		
					}
				}
			} else {
				if (confirm(mInterface)) {
					return false;
				}											
			}
		} catch( ex ) {
			var eInterface = "<"+I18NMsg(/*Ոע��*/"L0000026170")+">"+I18NMsg(/*\n\n���������U�Nδ�ܳɹ�����  Product Mapping*/"L0000026175")+", "+I18NMsg(/*����^�m��Ԓ*/"L0000026272")+", "+I18NMsg(/*��Ӱ�����ϵ�y���\��\n\n�Ƿ���Ҫ����?*/"L0000026770")+"";
			if (confirm(eInterface)) {
				return false;
			}														
		}
        // Ended
       
	  if(CheckInvestInfo()==false)
	  {
	  	return  false;	
	  }
        
        //alert("ProposalInput.js_specScanFlag: "+specScanFlag);
        if(ScanFlag=="1" && specScanFlag != "bposcan"){  //������ɨ���¼��
          fm.WorkFlowFlag.value = "0000001099";
        }
       else if(ScanFlag=="1" && specScanFlag == "bposcan"){   //����Ͷ����¼��
          fm.WorkFlowFlag.value = "0000001094";
        }
       else{
          fm.WorkFlowFlag.value = "0000001098";
       }
       var mySql = new SqlClass();
		mySql.setResourceName("uw.UWAMLQuerySql");
		mySql.setSqlId("UWAMLQuerySql3");
		mySql.addSubPara(fm.ContNo.value);
		mySql.addSubPara(fm.AppntNo.value);
		var strArr = easyExecSql(mySql.getString());
		if (strArr == null) {
			myAlert(""+"��ͬ��Ϣδ���з�ϴǮ��Ϣ¼��"+","+"������������"+" "+"[¼�����]"+" "+"ȷ�ϣ�"+"");
			return;
		}
		var mySql87 = new SqlClass();
		mySql87.setResourceName("app.ProposalInputSql");
		mySql87.setSqlId("ProposalInputSql87");
		mySql87.addSubPara(fm.ContNo.value);
		var strArr87 = easyExecSql(mySql87.getString());
		var mySql88 = new SqlClass();
		mySql88.setResourceName("app.ProposalInputSql");
		mySql88.setSqlId("ProposalInputSql88");
		mySql88.addSubPara(fm.ContNo.value);
		var strArr88 = easyExecSql(mySql88.getString());
		
		if (strArr88[0][0]!=0&&strArr87[0][0] == 0) {
			myAlert(""+"��ͬ��Ϣδ����Ͷ����֪��Ϣ¼��"+","+"������������"+" "+"[¼�����]"+" "+"ȷ�ϣ�"+"");
			return;
		}
		   var mySql83 = new SqlClass();
			mySql83.setResourceName("app.ProposalInputSql");
			mySql83.setSqlId("ProposalInputSql83");
			mySql83.addSubPara(fm.ContNo.value);
			var strArr83= easyExecSql(mySql83.getString());
		//�ɵ��Ƿ�������ڽ�
		if(strArr83!=null){
			var mySql115 = new SqlClass();
			mySql115.setResourceName("app.ContInputSql");
			mySql115.setSqlId("ContInputSql115");
			mySql115.addSubPara(fm.ContNo.value);
			var strArr115 = easyExecSql(mySql115.getString());
		for(var i=0;i<strArr83.length;i++){
			var q=0;
			var cot=strArr83[i][0];
			var mySql84 = new SqlClass();
			mySql84.setResourceName("app.ProposalInputSql");
			mySql84.setSqlId("ProposalInputSql84");
			mySql84.addSubPara(cot);
			var strArr84 = easyExecSql(mySql84.getString());
			if(null == strArr115){
				if (strArr84[0][0]!='0') {
						myAlert(""+"�ɵ�"+""+cot+""+"�Ѹ������ڽ�"+"");
						return;
					}
			}else{
				for(var oca=0;oca<strArr115.length;oca++){
						if(cot==strArr115[0][oca]){
							q++;
						}
					}
					if(q==0){	
							if(strArr84[0][0] != '0'){
								myAlert(""+"�ɵ�"+"" + cot + ""+"�Ѹ������ڽ�"+"");
								return;
						}
					}
				}
			}
		}
		    //���Ų�ԃ
		    var mySql86 = new SqlClass();  
			mySql86.setResourceName("app.ProposalInputSql");
			mySql86.setSqlId("ProposalInputSql86");
			mySql86.addSubPara('LocalCurrency');
			var strArr86 = easyExecSql(mySql86.getString());
		//�ɵ����ڽ����
		if(strArr83!=null){
		for(var i=0;i<strArr83.length;i++){
			var cot=strArr83[i][0];
			var mySql103 = new SqlClass();
			mySql103.setResourceName("app.ContInputSql");
			mySql103.setSqlId("ContInputSql103");
			mySql103.addSubPara(cot);
			var strArr103 = easyExecSql(mySql103.getString());
			var mySql105 = new SqlClass();
			mySql105.setResourceName("app.ContInputSql");
			mySql105.setSqlId("ContInputSql104");
			mySql105.addSubPara(fm.ContNo.value);
			var strArr105 = easyExecSql(mySql105.getString());
			if (null==strArr103) {
				var mySql104 = new SqlClass();
				mySql104.setResourceName("app.ContInputSql");
				mySql104.setSqlId("ContInputSql104");
				mySql104.addSubPara(cot);
				var strArr104 = easyExecSql(mySql104.getString());
				if(strArr104[0][0]!=strArr105[0][0] && strArr104[0][0]!=strArr86[0][0]){
					// Early Maturity - Start
					// Remark: Release the error message to warning message for RMB & CNY exchange
					// myAlert(""+"�������������ڽ�������±������ֲ�ͬ"+"");
					// return false;
					var retVal = confirm(""+"�������������ڽ�������±������ֲ�ͬ"+"\n\n"+"�Ƿ������"+"");
					if( retVal == false ){
						return false;
					}
					// Early Maturity - Ended
				}
			}else{
				if(strArr103[0][0]!=strArr105[0][0] && strArr103[0][0]!=strArr86[0][0]){
					// Early Maturity - Start
					// Remark: Release the error message to warning message for RMB & CNY exchange
					// myAlert(""+"�������������ڽ�������±������ֲ�ͬ"+"");
					// return false;
					var retVal = confirm(""+"�������������ڽ�������±������ֲ�ͬ"+"\n\n"+"�Ƿ������"+"");
					if( retVal == false ){
						return false;
					}
					// Early Maturity - Ended
				}
			}
		}
		}
		if(NewPayMode=='K'){
				var mySql108 = new SqlClass();
				mySql108.setResourceName("app.ContInputSql");
				mySql108.setSqlId("ContInputSql108");
				mySql108.addSubPara(fm.ContNo.value);
				var strArr108 = easyExecSql(mySql108.getString());
				if(strArr108[0][0]!=null&&strArr108[0][0]!=strArr108[0][1]&&strArr108[0][0]!='13'){
					myAlert(""+"���ÿ����ֲ��Ǳ��һ��뱣�����ֲ���ͬ"+"");
					return false;
				}
		}
    	//���������� start
		var mySql116 = new SqlClass();
		mySql116.setResourceName("app.ContInputSql");
		mySql116.setSqlId("ContInputSql116");
		mySql116.addSubPara(fm.ContNo.value);
		var strArr116 = easyExecSql(mySql116.getString());
		if(strArr116[0][0]=='0'||strArr116==0){

		}else{
			var mySql117 = new SqlClass();
			mySql117.setResourceName("app.ContInputSql");
			mySql117.setSqlId("ContInputSql117");
			mySql117.addSubPara(fm.ContNo.value);
			var strArr117 = easyExecSql(mySql117.getString());
			if(strArr117[0][0]=='0'||strArr117==0){
				myAlert(""+"û�б���������Ϣ��"+"");
				return false;
			}
		}
		//���������� end
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;
		
        if(!fatcaCompanyCheck()){
 			return false;
 		}
    
  }
  else if (wFlag ==2)//�������ȷ��
  {
    //return;
    //AppntChkFlag="false";
    if(AppntChkFlag=="false")
      {
	  	
		var sqlid45="ProposalInputSql45";
		var mySql45=new SqlClass();
		mySql45.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql45.setSqlId(sqlid45);//ָ��ʹ�õ�Sql��id
        mySql45.addSubPara(fm.ContNo.value);//ָ������Ĳ���
        mySql45.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
	   var strSql =mySql45.getString();
		
        //var strSql = "select * from LCIssuePol where contno = '"+fm.ContNo.value+"' and issuetype = '99' and questionobj = '"+fm.all('AppntNo').value+"'";
        var brrResult = easyExecSql(strSql);
        if(brrResult==null)
         {
          if(confirm(""+"�Ƿ����Ͷ����У�飿"+"")){
           return;
          }

         }
      }

    if(InsuredChkFlag=="false")
      {
	  	
		var sqlid46="ProposalInputSql46";
		var mySql46=new SqlClass();
		mySql46.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql46.setSqlId(sqlid46);//ָ��ʹ�õ�Sql��id
        mySql46.addSubPara(fm.ContNo.value);//ָ������Ĳ���
        mySql46.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	    strSql =mySql46.getString();
		
       // strSql = "select * from LCIssuePol where contno = '"+fm.ContNo.value+"' and issuetype = '99' and questionobj in (select insuredno from lcinsured where contno='"+fm.ContNo.value+"')";
        var crrResult = easyExecSql(strSql);
        if(crrResult==null)
         {
          if(confirm(""+"�Ƿ���б�����У�飿"+"")){
            return;
          }
         }
       }

    if(fm.all('ProposalContNo').value == "")
      {
        myAlert(""+"δ��ѯ����ͬ��Ϣ"+","+"������������"+" "+"[�������]"+" "+"ȷ�ϣ�"+"");
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
          myAlert(""+"δ��ѯ����ͬ��Ϣ"+","+"������������"+" "+"[�����޸����]"+" "+"ȷ�ϣ�"+"");
          return;
       }
	    //У��Ͷ�������Ƿ���¼Ͷ�ʼƻ�
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

    var showStr=""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    fm.action = "./InputConfirm.jsp";
  fm.submit(); //�ύ
}

//�˺�����Ŀ���ǣ���ѯ�ŵ����߸����µ�Ͷ������Ͷ����Ϣ
function getContInputnew(){
    //ȡ�ø���Ͷ���˵�������Ϣ
    if(fm.AppntCustomerNo.value!=""&&fm.AppntCustomerNo.value!="false"){
      //arrResult=easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ),b.PostalAddress,b.ZipCode,b.Phone,b.HomePhone from LDPerson a Left Join LCAddress b On b.CustomerNo =a.CustomerNo Where 1=1 and a.CustomerNo ='"+fm.AppntCustomerNo.value+"'",1,0);
         // arrResult=easyExecSql("select a.* from LDPerson a Where 1=1 and trim(a.CustomerNo) =trim('"+fm.AppntCustomerNo.value+"')");
    //alert("aaa=="+fm.all('RelationToAppnt').value);
    //displayAppnt(arrResult[0]);
  }
    //ȡ��Ͷ����λ�� ������Ϣ
    if(fm.GrpContNo.value!=""&&fm.GrpContNo.value!="false"){
		
		var sqlid47="ProposalInputSql47";
		var mySql47=new SqlClass();
		mySql47.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql47.setSqlId(sqlid47);//ָ��ʹ�õ�Sql��id
        mySql47.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
	   var  strSql47 =mySql47.getString();
		
		arrResult = easyExecSql(strSql47, 1, 0);
      //arrResult = easyExecSql("select a.*,b.GrpName,b.BusinessType,b.GrpNature,b.Peoples,b.RgtMoney,b.Asset,b.NetProfitRate,b.MainBussiness,b.Corporation,b.ComAera,b.Fax,b.Phone,b.FoundDate,b.CustomerNo,b.GrpName from LCGrpAddress a,LDGrp b where a.CustomerNo = b.CustomerNo and b.CustomerNo=(select CustomerNo from LCGrpAppnt  where GrpContNo = '" + fm.GrpContNo.value + "')", 1, 0);
      if(arrResult!=null)
         displayAddress1(arrResult[0]);
    }
    //ȡ�ñ�Ͷ���˵�������Ϣ
    if(fm.all('CustomerNo').value!=""&&fm.all('CustomerNo').value!="false"){
    var tcustomerNo=fm.all('CustomerNo').value;
    var tContNo=fm.all('ContNo').value;
	
		var sqlid48="ProposalInputSql48";
		var mySql48=new SqlClass();
		mySql48.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql48.setSqlId(sqlid48);//ָ��ʹ�õ�Sql��id
        mySql48.addSubPara(tcustomerNo);//ָ������Ĳ���
        mySql48.addSubPara(tContNo);//ָ������Ĳ���
	   var  strSql48 =mySql48.getString();
	
	 arrResult=easyExecSql(strSql48,1,0);
   // arrResult=easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.InsuredNo) ),b.PostalAddress,b.ZipCode,b.Phone,b.HomePhone from LCInsured a Left Join LCAddress b On b.CustomerNo =a.InsuredNo Where 1=1 and a.InsuredNo ='"+tcustomerNo+"' and a.ContNo='"+tContNo+"'",1,0);
    displayInsured(arrResult[0]);
  }
}

function GrpConfirm(ScanFlag){//ScanFlag
     var tGrpContNo = parent.VD.gVSwitch.getVar( "GrpContNo" );


		var sqlid49="ProposalInputSql49";
		var mySql49=new SqlClass();
		mySql49.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql49.setSqlId(sqlid49);//ָ��ʹ�õ�Sql��id
        mySql49.addSubPara(tGrpContNo);//ָ������Ĳ���
	   strSql =mySql49.getString();

//    strSql = "select peoples2 from LCGrpCont    where GrpContNo = '" + tGrpContNo + "'";
    var tPeoplesCount = easyExecSql(strSql);

    if(tPeoplesCount==null||tPeoplesCount[0][0]<=0){
        myAlert(""+"����ʧ�ܣ�Ͷ��������Ϊ0��"+"");
        return;
    }

		var sqlid50="ProposalInputSql50";
		var mySql50=new SqlClass();
		mySql50.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql50.setSqlId(sqlid50);//ָ��ʹ�õ�Sql��id
        mySql50.addSubPara(tGrpContNo);//ָ������Ĳ���
	   strSql =mySql50.getString();

//    strSql = "select peoples2,riskcode from LCGrpPol    where GrpContNo = '" + tGrpContNo + "'";
    tPeoplesCount = easyExecSql(strSql);
    if(tPeoplesCount!=null)
    {
        for(var i=0;i<tPeoplesCount.length;i++)
        {
            if(tPeoplesCount[i][0]<=0)
            {
                myAlert(""+"����ʧ�ܣ�����"+""+tPeoplesCount[i][1]+""+"��Ͷ��������Ϊ0��"+"");
                return;
            }
        }
    }


		var sqlid51="ProposalInputSql51";
		var mySql51=new SqlClass();
		mySql51.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql51.setSqlId(sqlid51);//ָ��ʹ�õ�Sql��id
        mySql51.addSubPara(tGrpContNo);//ָ������Ĳ���
	   strSql =mySql51.getString();

//    strSql = "select SaleChnl,AgentCode,AgentGroup,ManageCom,GrpName,CValiDate,PrtNo from LCGrpCont     where GrpContNo = '"
//    + tGrpContNo + "'";

    var grpContInfo = easyExecSql(strSql);
    var queryString = 'SaleChnl='+grpContInfo[0][0]+'&AgentCode='+grpContInfo[0][1]+'&AgentGroup='+grpContInfo[0][2]
        +'&ManageCom='+grpContInfo[0][3]+'&GrpName='+grpContInfo[0][4]+'&CValiDate='+grpContInfo[0][5];

//    strSql = "  select missionID,SubMissionID from lwmission where 1=1 "
//                    +" and lwmission.processid = '0000000004'"
//                    +" and lwmission.activityid = '0000002098'"
//                    +" and lwmission.missionprop1 = '"+grpContInfo[0][6]+"'";
					
					
		var sqlid52="ProposalInputSql52";
		var mySql52=new SqlClass();
		mySql52.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql52.setSqlId(sqlid52);//ָ��ʹ�õ�Sql��id
        mySql52.addSubPara(grpContInfo[0][6]);//ָ������Ĳ���
	    strSql =mySql52.getString();
					
    var missionInfo = easyExecSql(strSql);
	
    queryString = queryString+"&MissionID="+missionInfo[0][0]+"&SubMissionID="+missionInfo[0][1];
//    var tStr= " select * from lwmission where 1=1 "
//                    +" and lwmission.processid = '0000000004'"
//                    +" and lwmission.activityid = '0000002001'"
//                    +" and lwmission.missionprop1 = '"+fm.all('ProposalGrpContNo').value+"'";
					
		var sqlid53="ProposalInputSql53";
		var mySql53=new SqlClass();
		mySql53.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql53.setSqlId(sqlid53);//ָ��ʹ�õ�Sql��id
        mySql53.addSubPara(fm.all('ProposalGrpContNo').value);//ָ������Ĳ���
	    var tStr =mySql53.getString();				
					
    turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
    if (turnPage.strQueryResult) {
    myAlert(""+"���ŵ���ͬ�Ѿ��������棡"+"");
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
    var showStr=""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
    if (wFlag ==1 ) //¼�����ȷ��
    {
		
		var sqlid54="ProposalInputSql54";
		var mySql54=new SqlClass();
		mySql54.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql54.setSqlId(sqlid54);//ָ��ʹ�õ�Sql��id
        mySql54.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	    var tStr =mySql54.getString();			
		
   // var tStr= " select * from lwmission where 1=1 and lwmission.activityid in ('0000001001','0000001002','0000001220','0000001230') and lwmission.missionprop1 = '"+fm.ContNo.value+"'";
        turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
        if (turnPage.strQueryResult) {
          myAlert(""+"�ú�ͬ�Ѿ��������棡"+"");
          return;
        }

        fm.WorkFlowFlag.value = "0000001098";
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;          //¼�����
  }
  else if (wFlag ==2)//�������ȷ��
  {
    if(fm.all('ProposalContNo').value == "")
       {
          myAlert(""+"δ��ѯ����ͬ��Ϣ"+","+"������������"+" "+"[�������]"+" "+"ȷ�ϣ�"+"");
          return;
       }
        fm.WorkFlowFlag.value = "0000001001";                   //�������
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;
        approvefalg="2";
    }
      else if (wFlag ==3)
  {
    if(fm.all('ProposalContNo').value == "")
       {
          myAlert(""+"δ��ѯ����ͬ��Ϣ"+","+"������������"+" "+"[�����޸����]"+" "+"ȷ�ϣ�"+"");
          return;
       }
        fm.WorkFlowFlag.value = "0000001002";                   //�����޸����
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;
    }
    else if(wFlag == 4)
    {
         if(fm.all('ProposalContNo').value == "")
       {
          myAlert(""+"δ��ѯ����ͬ��Ϣ"+","+"������������"+" "+"[�޸����]"+" "+"ȷ�ϣ�"+"");
          return;
       }
        fm.WorkFlowFlag.value = "0000001021";                   //�����޸�
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;
    }
    else
        return;

  var showStr=""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
function checkMult()
{
    var tSql="";
	
		var sqlid55="ProposalInputSql55";
		var mySql55=new SqlClass();
		mySql55.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql55.setSqlId(sqlid55);//ָ��ʹ�õ�Sql��id
        mySql55.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	   tSql =mySql55.getString();		
	
 //   tSql="select AmntFlag from lmduty a,lmriskduty b where b.riskcode='"+fm.all('RiskCode').value+"' and a.dutycode=b.dutycode";
    var arrResult=easyExecSql(tSql);
    if(arrResult[0]=="2" && fm.all('inpNeedDutyGrid').value == "0")
    {
        if(fm.all('mult').value== "")
        {
            myAlert(''+"��������Ϊ��!"+'');
            return false;
        }
        if(!isNumeric(fm.all('mult').value))
        {
                myAlert(''+"��������Ϊ����!"+'');
                fm.all('mult').value = "";
                fm.all('mult').focus();
                return false;
        }

        if(parseFloat(fm.all('mult').value) == 0)
        {
            myAlert(''+"��������Ϊ��!"+'');
            fm.all('mult').value = "";
            fm.all('mult').focus();
            return false;
        }
    }
    return true;
}
/*********************************************************************
 *  У��280���ֽ���Ķ�ѡ���ε�����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *  create by zhangyang 2005-7-29
 *********************************************************************
 */
function chkDuty(){
    var tSql="";
  //tSql="select RiskCode from lcpol where polno='"+fm.all('MainPolNo').value+"'";
  
  		var sqlid56="ProposalInputSql56";
		var mySql56=new SqlClass();
		mySql56.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql56.setSqlId(sqlid56);//ָ��ʹ�õ�Sql��id
        mySql56.addSubPara(fm.MainPolNo.value);//ָ������Ĳ���
	   tSql =mySql56.getString();	
  
  var arrResult=easyExecSql(tSql);

 if(arrResult=="00608000"|| arrResult=="00609000" || arrResult=="00613000"){
    if(DutyGrid.getChkNo(1)){
      myAlert(""+"�����ղ���ѡ�����ζ�!"+"");
        return false;
        }
    }
  if(arrResult=="00615000"){
        if(DutyGrid.getChkNo(0)){
            myAlert(""+"�����ղ���ѡ������һ!"+"");
            return false;
            }
        }
    return true;
}
/*********************************************************************
 *  У��ɷѷ�ʽΪ����ʱ�ɷ��ڼ�ͱ����ڼ����һ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *  create by zhangzheng 2008-08-25
 *********************************************************************
 */
 function chkPayEndYear(){
 if(fm.PayIntv!=null && fm.PayEndYear!=null && fm.all('PayIntv').value!=null && fm.all('PayEndYear').value!=null && fm.all('PayIntv').value!="" && fm.all('PayEndYear').value!=""){
   //�����ų���IBW37/38/39��У�飬���Ϊ����������ҪУ��ɷ��ڼ�ͱ����ڼ�
		var mySql105=new SqlClass();
		mySql105.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql105.setSqlId("ProposalInputSql105");//ָ��ʹ�õ�Sql��id
		mySql105.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
	    var arrayResult = easyExecSql(mySql105.getString());
	 
	 if(fm.all('PayIntv').value=="0"&&arrayResult!="1"){
       //if(fm.all('PayEndYear').value!="1000" && fm.all('PayEndYear').value!="0"){
         //alert("�ɷѷ�ʽΪ����ʱ,�ɷ����ڱ���Ϊ1000!");
         //return false;
        //}
	   if( fm.InsuYear!=null &&fm.all('InsuYear').value!=null && fm.all('InsuYear').value!="" ){
			   if(fm.all('InsuYear').value!=fm.all('PayEndYear').value)
			   {
				   myAlert(""+"�ɷѷ�ʽΪ����ʱ"+","+"�ɷ����ڱ����뱣���ڼ�һ��"+"");
			       return false;
			   }
			   
			   if(fm.all('InsuYearFlag').value!=fm.all('PayEndYearFlag').value)
			   {
				   myAlert(""+"�ɷѷ�ʽΪ����ʱ"+","+"�ɷ����ڱ�־�����뱣���ڼ��־һ��"+"");
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
		mySql57.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql57.setSqlId(sqlid57);//ָ��ʹ�õ�Sql��id
        mySql57.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	    tSql =mySql57.getString();	
	 
  // iSql="select a.relariskcode,m.riskname from lmriskrela a,lmrisk m,lcpol p where a.relariskcode = m.riskcode and a.riskcode=p.riskcode and p.mainpolno = p.polno and p.contno = '"+ContNo+"'";
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
      //goToPic(0); top.fraPic.scrollTo(94, 787); showPosition(114+hx, 870+hy, 225, 120);
      }
    else{
          goToPic(0); top.fraPic.scrollTo(0, 1402); showPosition(77+hx, 1446+hy, 1105, 235);
          //goToPic(0); top.fraPic.scrollTo(0, 1440); showPosition(71+hx, 1494+hy, 132, 187);
    }

  }
}


function showNotePad()
{
//  var selno = SelfGrpGrid.getSelNo()-1;
//  if (selno <0)
//  {
//        alert("��ѡ��һ������");
//        return;
//  }

  var MissionID = tMissionID;
  var SubMissionID = tSubMissionID;
//  var ActivityID = SelfGrpGrid.getRowColData(selno, 6);
    var ActivityID = "0000001001";
//  var PrtNo = SelfGrpGrid.getRowColData(selno, 2);
  var PrtNo = fm.PrtNo.value;
//  var NoType = fm.NoType.value;
    var NoType = "1";
    if(PrtNo == null || PrtNo == "")
    {
        myAlert(""+"Ͷ������Ϊ�գ�"+"");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,""+"���������±��鿴"+"","left");

}

//ǿ�ƽ����˹��˱�
function forceUW(){
    //��ѯ�Ƿ��Ѿ�����ѡ��ǿ�ƽ����˹��˱�
  var ContNo=fm.all("ContNo").value;
  
  	   	var sqlid58="ProposalInputSql58";
		var mySql58=new SqlClass();
		mySql58.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql58.setSqlId(sqlid58);//ָ��ʹ�õ�Sql��id
        mySql58.addSubPara(ContNo);//ָ������Ĳ���
	    var sqlstr =mySql58.getString();	
  
  //var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
  arrResult = easyExecSql(sqlstr,1,0);
  if(arrResult==null){
    myAlert(""+"�����ڸ�Ͷ������"+"");
  }
  else{
    window.open("../uw/ForceUWMain.jsp?ContNo="+ContNo,"window1",sFeatures);
  }

}

//-------------------------------------------------Beg
//��ӽɷ���֤
//�޸��ˣ�chenrong
//�޸����ڣ�2006-02-24

//У�飬����������ɷѷ�ʽ����������ɷ�����
function checkPayIntv()
{
    if (trim(fm.PayIntv.value) == "" || fm.PayIntv.value == null)
    {
        myAlert(""+"����¼��ɷѷ�ʽ��Ϣ!"+"");
        fm.PayIntv.focus();
        return ;
    }
}

//�ɷ�����˫��ʱ������ɷѷ�ʽΪ��������ʾ����
function payEndYearDBLClick()
{
    if (fm.PayIntv.value == "0")
    {
        return ;
    }
    var codeName = "!PayEndYear-"+fm.RiskCode.value+"*0&0";
    showCodeList(codeName,[fm.PayEndYear,fm.PayEndYearName],[0,1]);
}

//�ɷ����޴����ʱ������ɷѷ�ʽΪ��������ʾ����
function payEndYearKeyUP()
{
    if (fm.PayIntv.value == "0")
    {
        return ;
    }
    var codeName = "!PayEndYear-"+fm.RiskCode.value+"*0&0";
    return showCodeListKey(codeName,[fm.PayEndYear,fm.PayEndYearName],[0,1]);
}

//-----------------------------------------------End


//-------------------------------------------------Beg
//�޸��ˣ�chenrong
//�޸����ڣ�2006-03-13

//����������Ϣʱ��������ȡ�������ȡ��ʽ����ȡƵ�ʵȣ�ƴ��һ���ַ�������
function setDutyKind()
{
    var tDutyKindFlag = 0;
    var tDutyKind = "";
    var tDutyKindN = "";
    if (fm.GetDutyKindFlag == null)
        return null;

    tDutyKindFlag = fm.all('GetDutyKindFlag').value;

    if (tDutyKindFlag == null || tDutyKindFlag == "" || tDutyKindFlag == "null")
        return null;

    for (i = 0; i < tDutyKindFlag; i++)
    {
        m = i + 1;
        tDutyKindN = "";
        tDutyKindN = trim(fm.all('GetDutyKind' + m).value);
        if (tDutyKindN == null || tDutyKindN == "" || tDutyKindN == "null")
        {
            tDutyKind = "";
            fm.all('GetDutyKind' + m).focus();
            return tDutyKind;
        }

        if (m == 1 && tDutyKindN == "0") //��������죬������ֵ��Ч.
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
    fm.all('GetDutyKind').value = tDutyKind;
}

//��ѯ���ֽ����ʼ��ʱ��GetDutyKind�������ֵ���GetDutyKind�ַ���
function splitDutyKind()
{
    try
    {
        if (fm.GetDutyKindFlag == null)
            return ;
        var tGetDutyKindFlag = fm.all('GetDutyKindFlag').value;
        var tGetDutyKind = fm.all('GetDutyKind').value;
        if (tGetDutyKind == null || tGetDutyKind == "" || tGetDutyKind == "null")
        {
            return;
        }

        if (tGetDutyKind == "000000")  //��������죬������ȡ�����ֵ
        {
            fm.all('GetDutyKind1').value = "0";
            return;
        }

        for (i = 0; i < tGetDutyKindFlag; i++)
        {
            m = i + 1;
            j = i * 2;
            fm.all('GetDutyKind' + m).value = tGetDutyKind.substring(j,j + 2);
        }
    }
    catch(ex)
    {
    }
}

//-----------------------------------------------End

//------------------------Beg
//Add By Chenrong
//Date:2006.5.12

//��ѯ����Ա�涯�ٶ�
function initQueryRollSpeed()
{
	
	  	 var sqlid59="ProposalInputSql59";
		var mySql59=new SqlClass();
		mySql59.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql59.setSqlId(sqlid59);//ָ��ʹ�õ�Sql��id
     //   mySql59.addSubPara(ContNo);//ָ������Ĳ���
	    var strSQL =mySql59.getString();	
	
  //  var strSQL = "select SYSVAR,SYSVARVALUE from LDSysvar where SYSVAR like 'ROLLSPEED%25' order by SYSVAR ASC";
    var arrSpeed = easyExecSql(strSQL);
    if (arrSpeed != null)
    {
        fm.RollSpeedBase.value = arrSpeed[0][1];
        fm.RollSpeedSelf.value = arrSpeed[1][1];
    }

	  	 var sqlid60="ProposalInputSql60";
		var mySql60=new SqlClass();
		mySql60.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql60.setSqlId(sqlid60);//ָ��ʹ�õ�Sql��id
       mySql60.addSubPara( fm.Operator.value);//ָ������Ĳ���
	    var strSQL =mySql60.getString();	

    //strSQL = "select rollspeed from LDRollSpeed where UserCode='" + fm.Operator.value + "'";
    arrSpeed1 = null;
    arrSpeed1 = easyExecSql(strSQL);
    if (arrSpeed1 != null)
    {
        fm.RollSpeedOperator.value = arrSpeed1[0][0];
    }
    else
        fm.RollSpeedOperator.value = 1;
}

//����������Ϣ���ý���Ŀؼ�����
var totalFieldArray = new Array(
        "RiskCode","BnfGrid2","BnfGrid3","BnfGrid4","BnfGrid5"
        ,"BnfGrid6","BnfGrid7","BnfGrid10","InsuYear",
        "PayIntv","PayEndYear","GetYear","GetDutyKind",
        "Mult", "Amnt","Prem"
    );

//��Բ�ͬ���֣���ȡ���������Զ���ý���Ŀؼ�����
var autoFieldArray = new Array;
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
                //���BnfGrid����������1���������ý����BnfGrid1��BnfGrid2...�ȣ�
                //Ҫ�����ǵ��������ƣ�������BnfGrid1[0]��BnfGrid1[1]�ȣ�������ֱ�Ӵ���BnfGrid1��BnfGrid2...�ȣ�
                tBnfCount = tBnfCount + 1;

                //�����BnfGrid�ĵ�һ�У���autoFieldArray��ƫ����ΪtFieldNumֵ
                //����Ϊ��ԭƫ��ֵ�ϼ�1
                if (tBnfCount == 1)
                   tBnfFieldNum = tFieldNum;
                else
                    tBnfFieldNum++;
                tBnfLength = eval("fm." + totalFieldArray[i] + ".length"); //ȡ���еĳ���
                for (j = 0; j < tBnfLength; j++)
                {
                    //��Ϊ���߸�BnfGrid�����ý���
                    //��ǰ�еĵ�j�пؼ�������autoFieldArray��λ�þ���j*7����ƫ������ֵ
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

//�Զ���ý���
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
//-----------------------End

//-----------------------Begin
//Add By Chenrong
//Date:2006.6.20
//У������ۿۣ����ܴ���1С��0
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

                myAlert(""+"�����ۿ۲���С��0����1������ȷ���룡"+"");
                return false;
            }
        }
        return true;
    }
    catch(ex){return true;}
}

function getDutyCode(cRiskCode)
{
    //��ѯ�˻����е�������Ϣ
	
	    var sqlid61="ProposalInputSql61";
		var mySql61=new SqlClass();
		mySql61.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql61.setSqlId(sqlid61);//ָ��ʹ�õ�Sql��id
       mySql61.addSubPara(cRiskCode);//ָ������Ĳ���
	    var tSql =mySql61.getString();	
	
//    var tSql="select a.dutycode,a.specflag from lmriskduty a ,lmrisk b where 1=1"
//        +" and a.riskcode=b.riskcode and b.insuaccflag='Y'"
//        +" and a.riskcode='"+cRiskCode+"'";
		
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
//-----------------------End

function showRiskParamCodeName(){
    var cRiskCode = fm.RiskCode.value;
    var tPayEndYearCode="!PayEndYear-"+cRiskCode+"*0&0";
    var tPayIntvCode="!PayIntv-"+cRiskCode+"*0&0";
    var tGetYearCode="!GetYear-"+cRiskCode+"*0&0";
    var tGetDutyKindCode="!GetDutyKind-"+cRiskCode+"*0&0";
    var tInsuYearCode="!InsuYear-"+cRiskCode+"*0&0";
    var tStaffflag="!StaffFlag-"+cRiskCode+"*0&0";
    var tInterestDifTypeCode="!InterestDifType-"+cRiskCode+"*0&0";
    try{showOneCodeName(tPayEndYearCode,'PayEndYear','PayEndYearName');}catch(ex){};
    try{showOneCodeName(tPayIntvCode,'PayIntv','PayIntvName');}catch(ex){};
    try{showOneCodeName(tGetYearCode,'GetYear','GetYearName');}catch(ex){};
    try{showOneCodeName(tGetDutyKindCode,'GetDutyKind','GetDutyKindName');}catch(ex){};
    try{showOneCodeName(tInsuYearCode,"InsuYear","InsuYearName");}catch(ex){};
    try{showOneCodeName(tInterestDifTypeCode,'InterestDifType','InterestDifTypeName');}catch(ex){};
    try{showOneCodeName(tStaffflag,'StaffFlag','StaffFlagName');}catch(ex){};
}

//���ݱ�ȫ��������,������Ч���ںͱ�������ֹ��������˵ı���ֹ��
//���� oldInY Ϊ �ƻ�ֹ�� ���� 12
//���� oldUnitΪ �ƻ�ֹ�ڵ�λ ���� M
//D|��^M|��^Y|��
//XinYQ rewrote on 2007-04-03
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
        //����һ���°�һ���¼���
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

//tongmeng
function showMultRiskGrid(tRiskCodeVar)
{
	 //return ;
	 //alert('@@!!!!!!!!!!!');
	 	//var tSQL_Display = "select 1 from lmriskapp where riskcode='"+tRiskCodeVar+"' and subriskflag='M'";
		
	    var sqlid62="ProposalInputSql62";
		var mySql62=new SqlClass();
		mySql62.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql62.setSqlId(sqlid62);//ָ��ʹ�õ�Sql��id
       mySql62.addSubPara(tRiskCodeVar);//ָ������Ĳ���
	    var tSQL_Display =mySql62.getString();	
		
	 	arrResult=easyExecSql(tSQL_Display, 1, 0);
    if (arrResult != null) {
    	//���¼������,�������޸�.���Ҳ���ʼ������
    	initMultMainRiskGrid('0');
    }
    else
    {
    	initMultMainRiskGrid('1');
    }
    //alert("��ǰ��������Ϣ:"+parent.VD.gVSwitch.getVar("InsuredNo"));
    //and insuredno!='"+parent.VD.gVSwitch.getVar("InsuredNo")+"'
	  	var tPrtNo = mSwitch.getVar("ContNo");
		
		var sqlid63="ProposalInputSql63";
		var mySql63=new SqlClass();
		mySql63.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql63.setSqlId(sqlid63);//ָ��ʹ�õ�Sql��id
        mySql63.addSubPara(tPrtNo);//ָ������Ĳ���
        mySql63.addSubPara(parent.VD.gVSwitch.getVar("InsuredNo"));//ָ������Ĳ���
	    var tSQL =mySql63.getString();	
		
//    	var tSQL = " select a.riskcode,b.riskname,a.mainpolno,b.subriskflag "
//    	  	      + " from lcpol a,lmriskapp b where mainpolno=polno and a.riskcode=b.riskcode "
// 								+ " and prtno='"+tPrtNo+"' "
// 								+ " and insuredno='"+parent.VD.gVSwitch.getVar("InsuredNo")+"' ";
 				  //prompt('',tSQL);
 		turnPage.queryModal(tSQL,MultMainRiskGrid);
 		//����Ǹ�����,����ֻ��һ��������Ϣʱ,Ĭ��ѡ���һ��!
 		if(MultMainRiskGrid.canSel==1&&MultMainRiskGrid.mulLineCount==1)
 		{
 			//alert('@@');
 			fm.all('MultMainRiskGridSel').checked=true;
 			//eval("fm.all('MultMainRiskGridSel')[" + MultMainRiskGrid.mulLineCount + "-1].checked=true");
 			onMultMainRiskGridSelect();
 		}
 		//�����¼��������Ϣ,��ʾ�ѱ���������Ϣ.
 		if(MultMainRiskGrid.mulLineCount>=1)
 		{
 			fm.all('divMultMainRisk').style.display = "";
 		}
 		//�������ֽ������ձ�������Ϣ
 		
 		//alert("@@"+MultMainRiskGrid.canSel+"##"+MultMainRiskGrid.mulLineCount);
 		
 		initMultAddRiskGrid();
 		var sqlid81="ProposalInputSql81";
		var mySql81=new SqlClass();
		mySql81.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql81.setSqlId(sqlid81);//ָ��ʹ�õ�Sql��id
    mySql81.addSubPara(tPrtNo);//ָ������Ĳ���
    mySql81.addSubPara(parent.VD.gVSwitch.getVar("InsuredNo"));//ָ������Ĳ���
	  var tSQL =mySql81.getString();
 		turnPageMultAddRisk.queryModal(tSQL,MultAddRiskGrid);
 		
}

function onMultMainRiskGridSelect(parm1,parm2)
{
	//alert('111');
	fm.all('MainPolNo').value=MultMainRiskGrid.getRowColData(MultMainRiskGrid.getSelNo() - 1, 3);
	//alert(MultMainRiskGrid.getRowColData(MultMainRiskGrid.getSelNo() - 1, 3));
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
				myAlert(""+"���������������!"+"");
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
		myAlert(""+"���������������!"+"");
		fm.CValiDate.value = "";
		fm.CValiDate.focus();
		return;
		}
	}
	else
	{
		myAlert(""+"���������������!"+"");
        fm.CValiDate.value = "";
        fm.CValiDate.focus();
        return;
	}
}

//tongmeng 2008-10-09 add
//���Ͷ���˺ͱ���������,���ŵ�������.
function getAppntAndInsuredForChangeRiskPlan(tPolNo)
{
	
		var sqlid64="ProposalInputSql64";
		var mySql64=new SqlClass();
		mySql64.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql64.setSqlId(sqlid64);//ָ��ʹ�õ�Sql��id
        mySql64.addSubPara(tPolNo);//ָ������Ĳ���
	    var tSQL_Cont =mySql64.getString();	
	
	//var tSQL_Cont = "select contno,appntno,insuredno,managecom from lcpol where polno='"+tPolNo+"'";
	var arrResult = easyExecSql(tSQL_Cont, 1, 0);
	if(arrResult!=null)
	{
	
		fm.all('AppntCustomerNo').value= arrResult[0][1];
		fm.all('CustomerNo').value= arrResult[0][2];
		//��ѯͶ���˺ͱ�����
		queryAppntNo();
		queryInsuredNo();
		fm.all('ContNo').value= arrResult[0][0];
		fm.all('PrtNo').value= arrResult[0][0];
		fm.all('ManageCom').value= arrResult[0][3];
	}
	//�޸Ľ���ĳ�ʼ��,���س�������֮�����Ϣ.
	//alert(document.getElementById("divLCBnf1"));
	
	document.getElementById("divLCBnf1Main").style.display='none';
	document.getElementById("divLCBnf1").style.display='none';
	document.getElementById("DivLCSpec").style.display='none';
	//DivLCSpec.style.display='none';
	//DivLCSpec1.style.display='none';
	
	//DivLCBnf.style.display='none';
	//divLCBnf1.style.display='none';
}

//tongmeng 2008-10-23 add
//�ڴ�����֮�����س�������֮�����Ϣ
function afterDealForChangeRiskPlan()
{
	//DivLCSpec.style.display='none';
	//DivLCSpec1.style.display='none';
	
	//DivLCBnf.style.display='none';
	//divLCBnf1.style.display='none';
}

//�����ύ�����棩
function submitFormPlan()
{
 // 	ifSavedRiskCode(fm.all("RiskCode").value);
	//����У��	
	var tSQL="";
	var tContNo=fm.ContNo.value;
	var riskcode=fm.RiskCode.value;
	var tResult="";
			var sqlid68="ProposalInputSql68";
		var mySql68=new SqlClass();
		mySql68.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql68.setSqlId(sqlid68);//ָ��ʹ�õ�Sql��id
        mySql68.addSubPara(tContNo);//ָ������Ĳ���
         mySql68.addSubPara(riskcode);//ָ������Ĳ���
	    var tempSQL =mySql68.getString();	
			
	tResult = easyExecSql(tempSQL);
    if (tResult != null && tResult.length > 0)
    {
    	fm.all("PolNo").value=tResult[0][0];
    }
	 if(fm.all("PolNo").value=="")
	{
		myAlert(""+"�ȱ���������Ϣ!"+"");
		return false;
	}	
	if ("IBL00"==riskcode) {
		if(InvestPlanRate.mulLineCount==0){ 
			myAlert(""+"��¼�붨�ڹ��������䣡"+"");
			return false;
		}
	}
	if ("IBL04"==riskcode) {
		if(InvestPlanRate.mulLineCount==0){ 
			myAlert(""+"��¼�붨�ڻ������M������䣡"+"");
			return false;
		}
	}
	if(InvestPlanRate.mulLineCount==0){ 
		myAlert(""+"û��������Ϣ��"+"");
		return false;
	}
	else{
	      
//	      	var sql="select count(*) from LCPerInvestPlan where PolNo='"+fm.all("PolNo").value+"'";
///////////////////////add by sunyh on 20070914///////////////////////
		
///////////////////////end     by sunyh on 20070914  add///////////////////////
	    var sqlid69="ProposalInputSql69";
		var mySql69=new SqlClass();
		mySql69.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql69.setSqlId(sqlid69);//ָ��ʹ�õ�Sql��id
        mySql69.addSubPara(fm.all("PolNo").value);//ָ������Ĳ���

	    var tempSQL1 =mySql69.getString();	
	    var counta=new Array;
	    counta=easyExecSql(tempSQL1);
	    if(counta>0){
	      	myAlert(""+"�������Ѵ��ڣ���ѡ��������!"+"");
	      	return false;
	    }
	  }	
	fm.all('mPlanOperate').value = "INSERT";
	var showStr=""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    fm.action="./InvestPlanInputSave.jsp";
	fm.submit(); //�ύ
}
function updateClickPlan()
{ 
	var riskcode=fm.RiskCode.value;
	if ("IBL00"==riskcode) {
		if(InvestPlanRate.mulLineCount==0){ 
			myAlert(""+"��¼�붨�ڹ��������䣡"+"");
			return false;
		}
	}
	if ("IBL04"==riskcode) {
		if(InvestPlanRate.mulLineCount==0){ 
			myAlert(""+"��¼�붨�ڻ������M������䣡"+"");
			return false;
		}
	}
	   	 if(InvestPlanRate.mulLineCount==0)
    	 { 
		    myAlert(""+"û��������Ϣ��"+"");
		     return false;
	      }	
	  
	fm.all('mPlanOperate').value = "UPDATE";
	var showStr=""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		fm.action="./InvestPlanInputSave.jsp";
	fm.submit(); //�ύ	

}


//�����ύ��ɾ����
function DeleteClickPlan()
{
	var riskcode=fm.RiskCode.value;
	if ("IBL00"==riskcode) {
		if(InvestPlanRate.mulLineCount==0){ 
			myAlert(""+"��¼�붨�ڹ��������䣡"+"");
			return false;
		}
	}
	if ("IBL04"==riskcode) {
		if(InvestPlanRate.mulLineCount==0){ 
			myAlert(""+"��¼�붨�ڻ������M������䣡"+"");
			return false;
		}
	}
	   	 if(InvestPlanRate.mulLineCount==0)
    	 { 
		    myAlert(""+"û��������Ϣ��"+"");
		     return false;
	      }
	fm.all('mPlanOperate').value = "DELETE";
	var showStr=""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	fm.action="./InvestPlanInputSave.jsp";
	fm.submit(); //�ύ

}
//�ж�FATCA��־.
function isFATCARiskcode(riskcode){
	var tSQL="";
		var tResult= new Array();
		var sqlid96="ProposalInputSql96";
			var mySql96=new SqlClass();
			mySql96.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql96.setSqlId(sqlid96);//ָ��ʹ�õ�Sql��id
	        mySql96.addSubPara(riskcode);//ָ������Ĳ���
		    var tempSQL =mySql96.getString();
	
		    tResult = easyExecSql(tempSQL);
		    if("1"==tResult[0][0])
		    {
		    	return true;
		    }else{
		    	return false;
		    }
}
function ifTLrisk(riskcode)
{
	var tSQL="";
	var tResult="";
//	tSQL="select * from LmriskApp where risktype3='3' and riskcode='"+riskcode+"'";
	var sqlid67="ProposalInputSql67";
		var mySql67=new SqlClass();
		mySql67.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql67.setSqlId(sqlid67);//ָ��ʹ�õ�Sql��id
        mySql67.addSubPara(riskcode);//ָ������Ĳ���
	    var tempSQL =mySql67.getString();	
			

	    	tResult = easyExecSql(tempSQL);
	    //	alert(tResult.length)
	    if(tResult != null && tResult.length > 0)
	    {
	    		//divInvestPlanRate.style.display="";
	    	return true;
	    }else{
	    	//divInvestPlanRate.style.display="none";
	    	return false;
	    }
	    
}
/*
 * �ж��Ƿ��Ǳ����������� 
 * */
function isBFRZRiskcode(riskcode)
{
	var tSQL="";
	var tResult= new Array();
	var sqlid90="ProposalInputSql90";
		var mySql90=new SqlClass();
		mySql90.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql90.setSqlId(sqlid90);//ָ��ʹ�õ�Sql��id
        mySql90.addSubPara(riskcode);//ָ������Ĳ���
	    var tempSQL =mySql90.getString();	

	    tResult = easyExecSql(tempSQL);
//	    tResult = easyExecSql("SELECT COUNT(1) FROM LMRISKPARAMSDEF WHERE 1=1 AND PARAMSTYPE = 'premfinanc' and riskcode='"+riskcode+"'");
//	    alert(tempSQL);
//	    alert(tResult);
//	    alert(tResult.length);
	    if(tResult[0][0] > 0)
	    {
	    		//divInvestPlanRate.style.display="";
	    	return true;
	    }else{
	    	//divInvestPlanRate.style.display="none";
	    	return false;
	    }
	    
}

function  CheckInvestInfo()
{
	
	var sqlid65="ProposalInputSql65";
		var mySql65=new SqlClass();
		mySql65.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql65.setSqlId(sqlid65);//ָ��ʹ�õ�Sql��id
        mySql65.addSubPara(fm.ContNo.value);//ָ������Ĳ���
        mySql65.addSubPara(fm.CustomerNo.value);//ָ������Ĳ���
	    var tSQL_Cont =mySql65.getString();	
	
	//var tSQL_Cont = "select contno,appntno,insuredno,managecom from lcpol where polno='"+tPolNo+"'";

	var riskarr =new Array;
	turnPage.strQueryResult  = easyQueryVer3(tSQL_Cont);
	  if (!turnPage.strQueryResult) {
    myAlert(""+"�������������Ϣ��"+"");
    return false;
    }
	 riskarr = decodeEasyQueryResult(turnPage.strQueryResult);
	 
	 for(var i=0; i<riskarr.length; i++){
	 	//�����Ͷ�����֣�������Ƿ��Ѿ�¼��Ͷ�ʼƻ�
	
	 	if(riskarr[i][1]==3)
	 	{
	 		
//	 		var tsql="select * from LCPerInvestPlan where polno='"+riskarr[i][2]+"'";
///////////////////////add by sunyh on 20070914///////////////////////
              //mySql.setSqlId("ProposalInputInputSql_84");
              //mySql.addPara('polno',riskarr[i][2]);
///////////////////////end     by sunyh on 20070914  add///////////////////////
			
				var sqlid66="ProposalInputSql66";
		var mySql66=new SqlClass();
		mySql66.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql66.setSqlId(sqlid66);//ָ��ʹ�õ�Sql��id
        mySql66.addSubPara(riskarr[i][2]);//ָ������Ĳ���
	    var tempSQL =mySql66.getString();	
			


	 		var planarr=new Array;
	 		planarr=easyExecSql(tempSQL);
	 		if (planarr==null)
   {
     myAlert (""+"���Ͷ������"+""+riskarr[i][0]+" "+"���Ͷ�ʼƻ���"+"");	
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
				myAlert(""+"�ɷѷ�ʽ����"+"");
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
		 if(fm.all('Amnt').value=='')
		 {
		 	myAlert(""+"����¼�뱣��"+"");
		 	return false;
		 }else{
		 DutyGrid.setRowColData(0,14,fm.all('Amnt').value);
	  }
	 	for(var k=0;k<=DutyGrid.mulLineCount-1;k++)
	   {
	     DutyGrid.setRowColData(k,18,fm.all('PayIntv').value);
	   }  				 	   
	    if(!PremGrid.getChkNo(0))
		 {
		 	myAlert(""+"�ڽɱ��ѱ�ѡ��"+"");
		 	return false;
	   }
	   if(cPayIntv==0){
		myAlert(""+"�ɷѷ�ʽ����"+"");
		 		 return false;
	  }
/*	 if(PremGrid.getChkNo(1)){
	  	if(prem2!=''){
	 	 if(prem2<500)
		 {
		 	alert("׷�ӱ���Ӧ�ô��ڵ���500");
		 		 return false;
		 }
		 if(prem2%100!=0){
			alert("׷�ӱ���Ӧ��Ϊ100��������");
			 return false;
		}
	  }
	}*/
	
	//���屣��
	var strAmnt = fm.all('Amnt').value;
	
	/*	 if(cPayIntv==12)
		 {
		 	if(prem1<2000||prem1>500000)
		 	{
		 		alert("��ɱ���Ӧ�ô��ڵ���2000��С�ڵ���500000");
		 		 return false;
		 	}
		 	if(prem1>=6000&strAmnt<120000)
		 	{
		 		alert("��ͱ���Ϊ120000");
		 		 return false;
		 		}
		 	if(prem1<6000&strAmnt<prem1*20)
		 	{
		 		alert("��ͱ���Ϊ"+prem1*20);
		 		return false;
		 		}
		 }else if(cPayIntv==6){
		 	if(prem1<1000||prem1>250000)
		 	{
		 		alert("����ɱ���Ӧ�ô��ڵ���1000��С�ڵ���250000");
		 		 return false;
		 	}
		 	if(prem1>=3000&strAmnt<60000)
		 	{
		 		alert("��ͱ���Ϊ60000");
		 		 return false;
		 		}
		 	if(prem1<3000&strAmnt<prem1*20)
		 	{
		 		alert("��ͱ���Ϊ"+prem1*20);
		 		return false;
		 		}
		 
	  }else if(cPayIntv==3){
	  	if(prem1<500||prem1>100000)
		 	{
		 		alert("���ɱ���Ӧ�ô��ڵ���500��С�ڵ���100000");
		 		 return false;
		 	}
		 	
		 	if(prem1>=1500&strAmnt<30000)
		 	{
		 		alert("��ͱ���Ϊ30000");
		 		 return false;
		 		}
		 	if(prem1<1500&strAmnt<prem1*20)
		 	{
		 		alert("��ͱ���Ϊ"+prem1*20);
		 		return false;
		 		}
		 	
	  }else if(cPayIntv==1){
	  	if(prem1<200||prem1>50000)
		 	{
		 		alert("�½ɱ���Ӧ�ô��ڵ���200��С�ڵ���50000");
		 		 return false;
		 	}
		 	
		 	if(prem1>=500&strAmnt<10000)
		 	{
		 		alert("��ͱ���Ϊ10000");
		 		 return false;
		 		}
		 	if(prem1<500&strAmnt<prem1*20)
		 	{
		 		alert("��ͱ���Ϊ"+prem1*20);
		 		return false;
		 		}
		 		
	  }else{
	  	alert("�ɷѷ�ʽ����");
		 		 return false;
	  }*/
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
				//goToPic(1); top.fraPic.scrollTo(263, 986); showPosition(649+hx, 991+hy, 430, 252);
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
	//alert("tContNo:"+tContNo);
			var sqlid70="ProposalInputSql70";
		var mySql70=new SqlClass();
		mySql70.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql70.setSqlId(sqlid70);//ָ��ʹ�õ�Sql��id
        mySql70.addSubPara(tContNo);//ָ������Ĳ���
         mySql70.addSubPara(riskcode);//ָ������Ĳ���
          mySql70.addSubPara(tInsuredNo);//ָ������Ĳ���
	    var tempSQL =mySql70.getString();	
			
	tResult = easyQueryVer3(tempSQL);
	//alert("w1");
//	alert("%%%%:"+tResult);
    if (tResult != null && tResult.length > 0)
    {
    	if(scantype=="scan") {
			setFocus();
		}	  
	//		alert("w2");
	  	//emptyForm();    
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
    	
//	alert("w3"+riskcode);
		var tISTL = ifTLrisk(riskcode);		
		
	//	alert("w42");
		
	
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
			//else
		    //{
		    //	initDutyGrid();
		    //	if(initDealForSpecRiskEx(fm.RiskCode.value)==false)
			//	{
			//		myAlert("���֣�"+cRiskCode+"��ʼ���������!");
			//		return false;
			//	}
		   // }
		}
	    else
		{
			fm.inpNeedDutyGrid.value = 0;
			DivChooseDuty.style.display = "none";
		}
	}
	catch(e){}

	//	alert("tISTL"+tISTL)
		if (tISTL == true)
		{
			isMullPrem(fm.RiskCode.value); 
			if (fm.inpNeedPremGrid.value == '1')
			{	
				initQueryPrem(fm.PolNo.value,fm.RiskCode.value); 
			}
			try{
				//�ж��Ƿ�ΪͶ������
				//DivChooseDuty.style.display = "none";
				DutyGrid.checkBoxAll(); 	    
				InvestPlanInputInit(LoadFlag);		
				
				//*****kongyan 
				if (fm.all('ContNo').value != null && fm.all('ContNo').value != "") {
				var countSQL = "select count(*) from LCPerInvestPlan where contno = '"+fm.all('ContNo').value+"'";
				var count = easyExecSql(countSQL);
				if (count > 0) {
					countSQL = "";
					countSQL = "select distinct a.insuaccno, a.insuaccname, '','', p.investrate,p.currency from LCPerInvestPlan p, LMRisktoAcc a where p.riskcode = a.riskcode " 
					         + " and p.insuaccno = a.insuaccno and p.contno = '"+fm.all('ContNo').value+"' and p.riskcode = '"+cRiskCode+"' order by a.insuaccno asc";
					
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

////ΪͶ�ʼƻ����ӵĺ���
function showDetailForCont2(cRiskCode)
{
	//���ڸı佹��ĺ�����
//	var mySql=new SqlClass();
//alert("cRiskCode��"+cRiskCode);
//    mySql.setJspName("../../app/ProposalInputInputSql.jsp");  
//alert("ifSavedRiskCode(cRiskCode):"+ifSavedRiskCode(cRiskCode));  
		if(!ifSavedRiskCode(cRiskCode))
		{
			
		//	alert('no save');

			var sqlid71="ProposalInputSql71";
		var mySql71=new SqlClass();
		mySql71.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql71.setSqlId(sqlid71);//ָ��ʹ�õ�Sql��id
         mySql71.addSubPara(cRiskCode);//ָ������Ĳ���
	//alert('no save1112');
	    var tempSQL2 =mySql71.getString();	
	//		alert("tempSQL2:"+tempSQL2);
		try{
			initInvestPlanRate();
		}
		catch(e)
		{
		//	alert(e);
			}
	//			alert('no save1222');
		
   	 turnPage.queryModal(tempSQL2,InvestPlanRate);		  
   	   //  										alert('no sav333');
	  			//	InvestPlanRate.setFocus(0,5);
	  				//fm.UintLinkValiFlag.focus();
	  	//			alert('no sav444');
   	    	//fm.UintLinkValiFlag.select();  	
   	  //  	alert("fm.all('mPlanOperate').value:"+fm.all('mPlanOperate').value);
   	    		if(fm.all('mPlanOperate').value =="INSERT"){
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
		mySql72.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql72.setSqlId(sqlid72);//ָ��ʹ�õ�Sql��id
         mySql72.addSubPara(tContNo);//ָ������Ĳ���

	    var tempSQL2 =mySql72.getString();	
		
	    //alert(tSQL);  
	    	tResult = easyExecSql(tempSQL2);
	    //	alert("$$$tResult:"+tResult)
	 //    alert("33333wwwwwwww1");
	    if(tResult != null && tResult.length > 0)
	    {
	  //  	 alert("2222222wwwwwwww1");
	    	fm.all("PolNo").value=tResult[0][0];
	    	tPolNo=fm.all("PolNo").value;
	    	
//	    	strSQL="select distinct a.InsuAccNo,c.INSUACCNAME,a.InvestMinRate,a.InvestMaxRate,a.InvestRate from LCPerInvestPlan a,lmriskinsuacc c "
//		       + "where a.PolNo='"+tPolNo+"' and  trim(a.InsuAccNo)=trim(c.InsuAccNo) ";
/////////////

//alert(1);
						var sqlid73="ProposalInputSql73";
		var mySql73=new SqlClass();
		mySql73.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql73.setSqlId(sqlid73);//ָ��ʹ�õ�Sql��id
         mySql73.addSubPara(tPolNo);//ָ������Ĳ���
	    var tempSQL3=mySql73.getString();


///////////////////////end     by sunyh on 20070914  add///////////////////////
	   
		  	turnPage.queryModal(tempSQL3,InvestPlanRate);
		  	

		  	
		  	     if(LoadFlag=='6'){
		  	     	
		  	     		var sqlid74="ProposalInputSql74";
		var mySql74=new SqlClass();
		mySql74.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql74.setSqlId(sqlid74);//ָ��ʹ�õ�Sql��id
         mySql74.addSubPara(tPolNo);//ָ������Ĳ���

	    var tempSQL4=mySql74.getString();	
		  	     	
		  	     
              	turnPage.queryModal(tempSQL4,QueryInvestPlanRateGrip);	
		  	 
		  	     
		  	     }
		  	var tflagResult="";
		  	
		 
        	var sqlid75="ProposalInputSql75";
		var mySql75=new SqlClass();
		mySql75.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql75.setSqlId(sqlid75);//ָ��ʹ�õ�Sql��id
         mySql75.addSubPara(tPolNo);//ָ������Ĳ���

	    var tempSQL5=mySql75.getString();	
        
     //   alert(11);
           tflagResult= easyExecSql(tempSQL5,1,0,1); 
           if(tflagResult != null && tflagResult.length > 0){
              fm.UintLinkValiFlag.value=tflagResult[0][0];
              if(fm.UintLinkValiFlag.value==2){
              	fm.UintLinkValiFlagName.value=""+"ǩ������Ч"+"";
              }else if(fm.UintLinkValiFlag.value==4){
              	fm.UintLinkValiFlagName.value=""+"����ԥ�ں���Ч"+"";
              }    	
           }
     //    alert(21);
            	
         
		  }else{
//	    strSQL="select  a.InsuAccNo, a.INSUACCNAME from  lmriskinsuacc a where  a.InsuAccNo in (select distinct InsuAccNo from lmriskaccpay where Riskcode in (select c.riskcode from lcpol c,LmriskApp b where c.contno='"+tContNo+"' and b.riskcode=c.riskcode and b.risktype3='3')) order by a.InsuAccNo ";
///////////////////////add by sunyh on 20070914///////////////////////
        //      alert("11wwwwwwww1");
              var sqlid76="ProposalInputSql76";
							var mySql76=new SqlClass();
							mySql76.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
							mySql76.setSqlId(sqlid76);//ָ��ʹ�õ�Sql��id
              mySql76.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
              mySql76.addSubPara(fm.CurrencyCode.value);
//alert("11wwwwwwww1222222222222222222");
	          var tempSQL6=mySql76.getString();	
         //     alert("tempSQL6:"+tempSQL6);
///////////////////////end     by sunyh on 20070914  add///////////////////////
//alert("11hhhhh222h1");
	       
   	  		turnPage.queryModal(tempSQL6,InvestPlanRate);	
   	  	
   	  	//	  alert("11hhhhhh1");
   	  //	InvestPlanRate.setFocus(0,5);
   	  		fm.all('mPlanOperate').value = "";
   	 }	 
   		         
   	   	
}

/**
 * �ύ�����, ���������ݷ��غ�ִ�еĲ���
 */
function afterSubmitQuery(tPolNo)
{
    queryPolDetail(tPolNo);
  }


//tongmeng 2010-11-16 add
//��ʼ��Ͷ����
function initULRisk()
{

	var cRiskCode = fm.all('RiskCode').value;
	//alert("cRiskCode:"+cRiskCode);
//	alert("ifTLrisk(cRiskCode):"+ifTLrisk(cRiskCode));
 if(ifTLrisk(cRiskCode))
	  {  
	  	
	  	try{
		      //DivChooseDuty.style.display = "none";
		       }
			   catch(e)
			   {
			   	}
			   	
			  // 		alert("222222:");
			   	try{
			    //DutyGrid.checkBoxAll();
			    //tongmeng 2010-11-16 Modify
			    //ֻ�б�����Ĳ�Ʒ�ų�ʼ��Ͷ�ʽ���
			    //alert("11111"+tProposalContNo);
			//    alert("@@!#!#@!#!@#:"+ifSavedRiskCode(cRiskCode));
			//    	alert("3333333:");
			    if(ifSavedRiskCode(cRiskCode))
			    {
			   // 		alert("4444444:");
			    	divInvestPlanRate.style.display="";
			   //  showDetailForCont2(cRiskCode);	
			     
			    //	InvestPlanInputInit(1);	
			    showDetailForCont(cRiskCode);
			    showInvestPlanRateGrid(cRiskCode);
			   // alert("5555:");
			  	}
			  	else
			  	{
			 // 			alert("5555:");
			  		divInvestPlanRate.style.display="none";
			  	}
			    
			   }
			   catch(e)
			   {
			   	}
			    finally
			    {			    
					}
					//alert("3111");
								
	  }	
}



function Discount()
{
	var cPolNo = fm.all('ProposalNo').value;
	if(mCurOperateFlag=="2")
		cPolNo = fm.all('SelPolNo').value;
	
	var sql="select DiscountCode,DiscountType,discounttypename,CalCode,dutycode,dutyname,ifsel,corder"
			+" from ("
			+"select DiscountCode,DiscountType,(select codename from ldcode where codetype='discounttype' and code=DiscountType) discounttypename,(select othersign from ldcode where codetype='discounttype' and code=DiscountType) order1,CalCode,dutycode,(select dutyname from lmduty where dutycode=a.dutycode) dutyname,'1' ifsel,(select max(''||corder) from LCDiscount where PolNo ='"+cPolNo+"' and DiscountCode=a.DiscountCode) corder,(select polapplydate from lccont where contno ='"+fm.all('ContNo').value+"') polapplydate,StartDate,EndDate from LMDiscount a where riskcode in ('"+fm.all('RiskCode').value+"','000000') and '"+cPolNo+"' is not null and exists(select 1 from LCDiscount where PolNo ='"+cPolNo+"' and DiscountCode=a.DiscountCode) "
			+" union all"
			+" select DiscountCode,DiscountType,(select codename from ldcode where codetype='discounttype' and code=DiscountType) discounttypename,(select othersign from ldcode where codetype='discounttype' and code=DiscountType) order1,CalCode,dutycode,(select dutyname from lmduty where dutycode=a.dutycode) dutyname,'0' ifsel,'' corder,(select polapplydate from lccont where contno ='"+fm.all('ContNo').value+"') polapplydate,StartDate,EndDate from LMDiscount a where riskcode in ('"+fm.all('RiskCode').value+"','000000') and (('"+cPolNo+"' is not null and not exists(select 1 from LCDiscount where PolNo ='"+cPolNo+"' and DiscountCode=a.DiscountCode)) or '"+cPolNo+"' is null) "
			+" )"
			+" where polapplydate>= StartDate and (EndDate is null or EndDate>=polapplydate) order by order1";
	if(mCurOperateFlag=="2")
	{
		sql="select DiscountCode,DiscountType,discounttypename,CalCode,dutycode,dutyname,ifsel,corder"
			+" from ("
			+"select DiscountCode,DiscountType,(select codename from ldcode where codetype='discounttype' and code=DiscountType) discounttypename,(select othersign from ldcode where codetype='discounttype' and code=DiscountType) order1,CalCode,dutycode,(select dutyname from lmduty where dutycode=a.dutycode) dutyname,'1' ifsel,(select max(''||corder) from LCDiscount where PolNo ='"+cPolNo+"' and DiscountCode=a.DiscountCode) corder,(select polapplydate from lccont where contno in (select contno from lcpol where polno='"+cPolNo+"')) polapplydate,StartDate,EndDate from LMDiscount a where riskcode in ('"+fm.all('RiskCode').value+"','000000') and '"+cPolNo+"' is not null and exists(select 1 from LCDiscount where PolNo ='"+cPolNo+"' and DiscountCode=a.DiscountCode) "
			+" union all"
			+" select DiscountCode,DiscountType,(select codename from ldcode where codetype='discounttype' and code=DiscountType) discounttypename,(select othersign from ldcode where codetype='discounttype' and code=DiscountType) order1,CalCode,dutycode,(select dutyname from lmduty where dutycode=a.dutycode) dutyname,'0' ifsel,'' corder,(select polapplydate from lccont where contno in (select contno from lcpol where polno='"+cPolNo+"')) polapplydate,StartDate,EndDate from LMDiscount a where riskcode in ('"+fm.all('RiskCode').value+"','000000') and (('"+cPolNo+"' is not null and not exists(select 1 from LCDiscount where PolNo ='"+cPolNo+"' and DiscountCode=a.DiscountCode)) or '"+cPolNo+"' is null) "
			+" )"
			+" where polapplydate>= StartDate and (EndDate is null or EndDate>=polapplydate) order by order1";
	}		
		sql="select a.campaigncode,a.campaignname,a.campaignnameen,c.giftcode,c.giftname,c.giftnameen,c.gifttype,d.riskcode," +
				"(select count(*) from lcdiscount x where x.discountcode=c.giftcode and x.campaigncode=a.campaigncode and x.contno='"+mSwitch.getVar("ContNo")+"')" +
				" from lcacampaign a, lcagift c,lcagiftriskrela d ,lcacampgiftrela b"+
				" left join lcdiscount x on b.campaigncode=x.campaigncode and b.giftcode=x.discountcode and x.contno='"+mSwitch.getVar("ContNo")+"' "+
 "where a.campaigncode=b.campaigncode and b.giftcode=c.giftcode and c.giftcode=d.giftcode "+
 "and a.startdate <=(select e.polapplydate from lccont e where e.contno='"+mSwitch.getVar("ContNo")+"') "+
 "and a.enddate >=(select e.polapplydate from lccont e where e.contno='"+mSwitch.getVar("ContNo")+"')"+
 "and d.riskcode='"+fm.all('RiskCode').value+"' order by x.contno asc ";

    turnPageDiscount.queryModal(sql,DiscountGrid);
  	var rowNum=DiscountGrid. mulLineCount ;  //�õ���¼������
    for(var i=0;i<rowNum;i++)
    {
    	if(DiscountGrid.getRowColData(i,9)=="1")
    	{
    		DiscountGrid.checkBoxSel(i+1);
    	}
    //�ر��ۿ���Ҫ¼���ۿ��㷨���˴�ֱ����mulline��ʼ��ʱ����Ϊ�ɱ༭���պ���Ҫ�Ľ�
    }
    
}

//20110831,cmj,������Ч��
function CalCValiDate(){
	var QueryFlag=mSwitch.getVar("QueryFlag");
	if(QueryFlag=="EdorQuery"){
		return;
	}
	
    if(LoadFlag=="8"){
    	var ModifyFlag=mSwitch.getVar("ModifyFlag");
    	var ModifyPolNo=mSwitch.getVar("ModifyPolNo");
    	
    	if(ModifyFlag=="Y"){
    		var mySql=new SqlClass();
			mySql.setResourceName("app.NSProposalInputSql");
			mySql.setSqlId("NSProposalInputSql1");
			mySql.addSubPara(ModifyPolNo);
            var result = easyExecSql(mySql.getString());
            ABPayIntv=result[0][0];
    		//fm.PayIntv.value=result[0][0];
    		//fm.CValiDate.value=result[0][1];
    		//fm.MainPolNo.value=result[0][2];
    		//fm.PayIntv.readOnly=true;
    		//fm.PayIntv.ondblclick=function(){};
    		//fm.PayIntv.onkeyup=function(){};
    		showOneCodeName('payintv', 'PayIntv', 'PayIntvName');
            //return;
    	}
    	
    	var ContNo=mSwitch.getVar("ContNo");
    	var EdorAppDate=mSwitch.getVar("EdorAppDate");
    	if(IsCValidate==null){
    	IsCValidate=mSwitch.getVar("IsCValidate");
    	}
    	var EdorAcceptNo=mSwitch.getVar("EdorAcceptNo");
    		
    	var params={ContNo:ContNo,
    				EdorAppDate:EdorAppDate,
    				IsCValidate:IsCValidate,
    				EdorAcceptNo:EdorAcceptNo,
    				Flag:"ABInsert"};
    	
		$.post("../bq/PEdorTypeABQuery.jsp?DateFlag="+new Date(),
    			params,
    			function(data){
    				try{
    					ABPayIntv=data[0][1];
    					ABMainPolNo=data[0][2];
    					if(IsCValidate=="Y"){
    						ABCValiDate=data[0][3];
    					}else if(IsCValidate=="N"){
    						ABCValiDate=data[0][0];
    					}
    				}catch(e){
    					myAlert(""+"ҳ���ʼ��ʧ�ܣ�"+"");
    				}
    			},
    			"json");
    }
}

//20111101,cmj,У�������Ƿ����
function checkRiskCode(){
	var mySql=new SqlClass();
	mySql.setResourceName("app.ProposalInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql.setSqlId("ProposalInputSql82");//ָ��ʹ�õ�Sql��id
    mySql.addSubPara(fm.ContNo.value);
    mySql.addSubPara(fm.RiskCode.value);	
    var result= easyExecSql(mySql.getString());
    if(result!=null&&result[0][0]=="X"){
    	return false;
    }
    return true;
}

function checkPDA() {
	var mySql93 = new SqlClass();
	mySql93.setResourceName("app.ProposalInputSql");
	mySql93.setSqlId("ProposalInputSql93");
	mySql93.addSubPara(fm.RiskCode.value);
	var result93= easyExecSql(mySql93.getString());
	if(result93!=null&&result93[0][0]=="1") {
		var mySql94 = new SqlClass();
		mySql94.setResourceName("app.ProposalInputSql");
		mySql94.setSqlId("ProposalInputSql94");
		mySql94.addSubPara(fm.ContNo.value);
		var result94= easyExecSql(mySql94.getString());
		if(result94!=null&&result94[0][0]=="1") {
			if("IRD04" != fm.RiskCode.value){
				return true;
			}
		}
	}
	return false;
}

function checkDuty(grid,row,b) {
	//IE11-Document-Mulline
	//fm.all(grid+'Chk')[row].checked=b;
	document.all(grid+'Chk')[row].checked=b;
	eval(grid+".checkBoxClick("+grid+",26)");
}

function checkMainAndSubRiskInfo(contno) {
	var sql = "SELECT count(1) FROM (SELECT DISTINCT PayIntv, Currency FROM lcpol WHERE contno='" + contno + "')"
	var result = easyExecSql(sql);
	if (result > 1) {
		return false;
	}
	return true;
}


function fatcaCompanyCheck(){


	//��ѯ�����Ƿ�����fatca��Χ
	var sqlid129 = "ContInputSql129";
	var mySql129 = new SqlClass();
	mySql129.setResourceName("app.ContInputSql"); // ָ��ʹ�õ�properties�ļ���
	mySql129.setSqlId(sqlid129);// ָ��ʹ�õ�Sql��id
	mySql129.addSubPara(fm.all('RiskCode').value);
	var makedatesql2 = mySql129.getString();

	var makedatearr2 = easyExecSql(makedatesql2);
	if(makedatearr2=="1"){//��������fatca��Χ
		
		// ��ѯͶ���˻�������Ƿ��ǹ�˾�ͻ�
		var sqlid125 = "ContInputSql125";
		var mySql125 = new SqlClass();
		mySql125.setResourceName("app.ContInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql125.setSqlId(sqlid125);// ָ��ʹ�õ�Sql��id
		mySql125.addSubPara(fm.all('ContNo').value);
		var makedatesql3 = mySql125.getString();
		var makedatearr3 = easyExecSql(makedatesql3);
		
		var sqlid126 = "ContInputSql126";
		var mySql126 = new SqlClass();
		mySql126.setResourceName("app.ContInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql126.setSqlId(sqlid126);// ָ��ʹ�õ�Sql��id
		mySql126.addSubPara(fm.all('ContNo').value);
		var makedatesql4 = mySql126.getString();
		var makedatearr4 = easyExecSql(makedatesql4);
		if(makedatearr3=="1"){
			
			var sqlid128 = "ContInputSql128";
			var mySql128 = new SqlClass();
			mySql128.setResourceName("app.ContInputSql"); // ָ��ʹ�õ�properties�ļ���
			mySql128.setSqlId(sqlid128);// ָ��ʹ�õ�Sql��id
			mySql128.addSubPara(fm.all('ContNo').value);
			mySql128.addSubPara(fm.all('ContNo').value);
			var makedatesql = mySql128.getString();
			var makedatearr = easyExecSql(makedatesql);
			
			if(makedatearr != "1"){
				myAlert(""+""+"");
				return false;
			}
			
		}
		if(makedatearr4=="1"){
			
			var sqlid127 = "ContInputSql127";
			var mySql127 = new SqlClass();
			mySql127.setResourceName("app.ContInputSql"); // ָ��ʹ�õ�properties�ļ���
			mySql127.setSqlId(sqlid127);// ָ��ʹ�õ�Sql��id
			mySql127.addSubPara(fm.all('ContNo').value);
			mySql127.addSubPara(fm.all('ContNo').value);
			var makedatesql5 = mySql127.getString();
			var makedatearr5 = easyExecSql(makedatesql5);
			
			if(makedatearr5 != "1"){
				myAlert(""+""+"");
				return false ;
			}

		}
		
	}
	
	return true;

}


function submitForm()
{
	
	var insuredNo = '000000';
	
	lockPage(""+I18NMsg(/*����ִ��ǰ��У��*/"G0000011648")+"");
	

	
	
	if($('#ckPanel').length>0){
		$('#ckPanel').window('open');	
	}else{
			var wh = ($(window).height()-200)/2;
			var ww = ($(window).width()-500)/2;
			var wHtml = "<div id=\"ckPanel\">"+I18NMsg(/*ǰ��У��&nbsp;&nbsp;*/"G0000011649")+"<img id=\"ckBefore\" src=\"../common/images/edor_loading.gif\"><br><div id=\"ckRules\"></div></div>";
			$(document.body).append(wHtml);
		
			$('#ckPanel').window({
				title:' ',
				width:500,
				height:200,
				border: true,
				left: ww,
				top: wh,
				shadow: true,
				resizable:false,
				closable:false,
				collapsible:false,
				minimizable:false,
				maximizable:false,
				draggable:false,
				onClose:function(){
					unLockPage();	
					
					$('#ckBefore').attr("src","../common/images/edor_loading.gif");
				}
			});
	}
	var params = {
			oper:'init',
			CustomerNo:fm.all( 'CustomerNo' ).value,
			ContNo:parent.VD.gVSwitch.getVar( "ContNo" ),
			PrtNo:fm.all( 'PrtNo' ).value,
			insuredNo:insuredNo,
			RiskCode: fm.all('RiskCode').value,

			loadFlag:LoadFlag
  	};
  	var size;
		$.ajax({
			type:'post',
			async:false,//ͬ��
			data:params,
			url:"ProposalCheck.jsp",
			cache:false,//����
			dataType:'json',
			success:function(rtData){
				size = rtData.Size;
				if(size>0){
					var ruleList = rtData.ruleList;//ȡ����ҪУ���״̬����
					$.each(ruleList,function(key,val){
						//�ֱ�Ӽ���ͼƬ
						$("#ckRules").append(""+I18NMsg(/*&nbsp;&nbsp;&nbsp;&nbsp;У��*/"G0000011650")+""+val.show+"&nbsp;&nbsp;<img id=\"ckPic"+key+"\" src=\"../common/images/edor_loading.gif\"><br>");
					});
					ckRet = true;
					var i = 0;
					chkOneByOne(ruleList,i,size,insuredNo);//��ʼУ��
				}else{
					submitForm11();	
				}
			}
		});
	
}
function chkOneByOne(ruleList,i,size,insuredNo){
	
	var ins="0";
	try {
		ins=fm.all( 'InsuYear' ).value;
	}catch(ex){}
	var pda;
	try {
		pda=fm.all( 'FPDFlag' ).value;
	}catch(ex){}
	var IsisP;
	try {
		IsisP=fm.all( 'IsISP' ).value;
	}catch(ex){}
	var amnt="0";
	try {
		amnt=fm.all( 'Amnt' ).value;
	}catch(ex){}
	
	var bonusGetMode;
	try {
		bonusGetMode=fm.all( 'BonusGetMode' ).value;
	}catch(ex){}
	
	var currency;
	try {
		currency=parent.VD.gVSwitch.getVar( "Currency");
	}catch(ex){}
	
	var floatRate="0";
	try {
		floatRate=fm.all( 'FloatRate' ).value;
		
	}catch(ex){}
	
	
	
	var gbgetMode;
	try{
		gbgetMode=fm.all( 'GBGetMode' ).value;
	}catch(ex){}
	
	var gcpGetMode;
	try{
		gcpGetMode=fm.all( 'GCPGetMode' ).value;
	}catch(ex){}
	
	var getMode;
	try{
		getMode=fm.all( 'GetMode' ).value;
	}catch(ex){}
	
	var getStartType;
	try{
		getStartType=fm.all( 'GetStartType' ).value;
	}catch(ex){}
	
	var getYear="0";
	try{
		getYear=fm.all( 'GetYear' ).value;
	}catch(ex){}
	
	var getYearFlag;
	try{
		getYearFlag=fm.all( 'GetYearFlag' ).value;
	}catch(ex){}
	
	var icGetMode;
	try{
		icGetMode=fm.all( 'ICGetMode' ).value;
	}catch(ex){}
	
	var insuYearFlag;
	try{
		insuYearFlag=fm.all( 'InsuYearFlag' ).value;
	}catch(ex){}
	
	var joinFlag;
	try{
		joinFlag=fm.all( 'JoinFlag' ).value;
	}catch(ex){}
	
	var liveGetMode;
	try{
		liveGetMode=fm.all( 'LiveGetMode' ).value;
	}catch(ex){}
	
	var mateFlag;
	try{
		mateFlag=fm.all( 'MateFlag' ).value;
	}catch(ex){}
	
	var mortDate;
	try{
		mortDate=fm.all( 'MortDate' ).value;
	}catch(ex){}
	
	var mult="0";
	try{
		mult=fm.all( 'Mult' ).value;
	}catch(ex){}
	
	var occupationLevel;
	try{
		occupationLevel=fm.all( 'OccupationLevel' ).value;
	}catch(ex){}
	
	var pdaFlag;
	try{
		pdaFlag=fm.all( 'PDAFlag' ).value;
	}catch(ex){}
	
	var payEndYearFlag;
	try{
		payEndYearFlag=fm.all( 'PayEndYearFlag' ).value;
	}catch(ex){}
	
	var payIntv="0";
	try{
		payIntv=fm.all( 'PayIntv' ).value;
	}catch(ex){}
	
	var payendYear="0";
	try{
		payendYear=fm.all( 'PayendYear' ).value;
	}catch(ex){}
	
	var planLevel;
	try{
		planLevel=fm.all( 'PlanLevel' ).value;
	}catch(ex){}
	
	var prem="0";
	try{
		prem=fm.all( 'Prem' ).value;
	}catch(ex){}
	
	var premFinanc;
	try{
		premFinanc=fm.all( 'PremFinanc' ).value;
	}catch(ex){}
	
	var staffFlag;
	try{
		staffFlag=fm.all( 'StaffFlag' ).value;
	}catch(ex){}
	
	var standbyFlag1;
	try{
		standbyFlag1=fm.all( 'StandbyFlag1' ).value;
	}catch(ex){}
	
	var standbyFlag2;
	try{
		standbyFlag2=fm.all( 'StandbyFlag2' ).value;
	}catch(ex){}
	
	var survivorshipOption;
	try{
		survivorshipOption=fm.all( 'SurvivorshipOption' ).value;
	}catch(ex){}
	
	
	var ckParams = {
		oper:'check',
		CustomerNo:fm.all( 'CustomerNo' ).value,
		ContNo:parent.VD.gVSwitch.getVar( "ContNo" ),
		PrtNo:fm.all( 'PrtNo' ).value,
		RiskCode: fm.all('RiskCode').value,

		insuredNo:insuredNo,
		loadFlag:LoadFlag,
		
		Amnt:amnt,
		BonusGetMode:bonusGetMode,
		CurrencyCode:currency,
		FPDFlag:pda,
		FloatRate:floatRate,
		GBGetMode:gbgetMode,
		GCPGetMode:gcpGetMode,
		GetMode:getMode,
		GetStartType:getStartType,
		GetYear:getYear,
		GetYearFlag:getYearFlag,
		ICGetMode:icGetMode,
		InsuYear:ins,
		InsuYearFlag:insuYearFlag,
		IsISP:IsisP,
		JoinFlag:joinFlag,
		LiveGetMode:liveGetMode,
		MateFlag:mateFlag,
		MortDate:mortDate,
		Mult:mult,
		OccupationLevel:occupationLevel,
		PDAFlag:pdaFlag,
		PayEndYearFlag:payEndYearFlag,
		PayIntv:payIntv,
		PayendYear:payendYear,
		PlanLevel:planLevel,
		Prem:prem,
		PremFinanc:premFinanc,
		StaffFlag:staffFlag,
		StandbyFlag1:standbyFlag1,
		StandbyFlag2:standbyFlag2,
		SurvivorshipOption:survivorshipOption,
		
		calCode:ruleList[i].calCode
		
	};
	var msg = "";
	
	$.ajax({
		type:'post',
		async:false,
		data:ckParams,
		url:"ProposalCheck.jsp",
		cache:false,
		dataType:'json',
		success:function(cData){
			if(cData.flag == "succ"){
				$("#ckPic"+i).attr("src","../common/images/edor_ok.png");
			}else{
				$("#ckPic"+i).attr("src","../common/images/edor_no.png");
				$('#ckBefore').attr("src","../common/images/edor_no.png");
				msg = cData.errorMsg;
				ckRet = false;
			}
			var ctop = $("#ckPic"+i).offset().top;
			var wt = $('#ckPanel').window('options').top;
			var wh = $('#ckPanel').window('options').height;
			var ah = 24
			if((i+1)==size){
				ah = 48;
			}
			if((ctop+24)>(wt+wh)){
				$('#ckPanel').window('resize',{height:ctop+ah-wt})
			}
		}
	});	
	if(!ckRet){
		myAlert(ruleList[i].msg);
		setTimeout(function(){
			$("#ckRules").html(""); 
			$('#ckPanel').window('close');
		},lazyTime);
		return;
	}

	if(i+1<size){
		i++
		setTimeout(function(){//��ʱ����
		chkOneByOne(ruleList,i,size,insuredNo);//�ݹ�
		},lazyTime);
	}else{
		
		$("#ckRules").html("");
		$('#ckPanel').window('close');
		$('#ckRules').append(""+""+""+"<img id=\"ckItem\" src=\"../common/images/edor_loading.gif\"><br>");
		
		submitForm11();
	}
}

