//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var mDebug  = "0";
var mOperate="";
var showInfo;
var arrDataSet;
var mSwitch  = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var QueryResult  = "";
var QueryCount   = 0;
var mulLineCount = 0;
var QueryWhere="";
var tSearch   = 0;
window.onfocus=myonfocus;

function afterCodeSelect( cCodeName, Field ){
    if(cCodeName == "InsuMult")
    {
    //ͨ�������޸ı�����Ǳ���
    var mult = fm.all('InsuMult').value;
    var tRiskCode=getRiskCount();
    //�鿴�ƻ����
    var tPlanType = fm.all('RiskPlan1').value;
    var tRiskPlan = fm.all('RiskPlan').value;
    if(tPlanType=="1"){
    for(j=0;j<tRiskCode.length;j++){
        var ttRiskCode = tRiskCode[j];
       //ͨ���ײ͵õ��ı��ϼƻ� ���޸ķ���ʱ �䱣�ѡ�����仯����ͨ����ѯһ����ı��ϼƻ� ���ǲ�ѯ�����ײ͵�ֵ
       var tSqlAmnt=" select d.CalFactorValue,a.dutycode	from LMRiskDutyFactor a, LMRisk b, LMDuty c, LDPlanDutyParam  d "
               +" where 1 = 1 and a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
               +" and a.DutyCode = d.DutyCode and a.CalFactor = d.CalFactor and b.RiskVer = d.RiskVersion "
               +" and ContPlanCode = '"+tRiskPlan+"' and b.riskcode = '"+ttRiskCode+"' and a.calfactor='Amnt'  order by a.RiskCode, d.MainRiskCode, a.DutyCode "
       var tSqlPrem=" select d.CalFactorValue,a.dutycode	from LMRiskDutyFactor a, LMRisk b, LMDuty c, LDPlanDutyParam  d "
               +" where 1 = 1 and a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
               +" and a.DutyCode = d.DutyCode and a.CalFactor = d.CalFactor and b.RiskVer = d.RiskVersion "
               +" and ContPlanCode = '"+tRiskPlan+"' and b.riskcode = '"+ttRiskCode+"' and a.calfactor='Prem'  order by a.RiskCode, d.MainRiskCode, a.DutyCode "
       var tAmntInfo = easyExecSql(tSqlAmnt);
       var tPremInfo = easyExecSql(tSqlPrem);
       if(tPremInfo!=null&&tPremInfo!=""&&tPremInfo!="null"){
       for (k=0;k<tPremInfo.length;k++){
            //�����ζౣ�� ��212401
            tPrem=tPremInfo[k][0];
            tDutyCode=tPremInfo[k][1];
            if(tPrem!=null&&tPrem!=""&&tPrem!="null"){
           for(i=0;i<ContPlanGrid.mulLineCount;i++){
              //alert("Factor��"+ContPlanGrid.getRowColData(i,5));
              //alert("RiskCode:"+ContPlanGrid.getRowColData(i,2));
              if(ContPlanGrid.getRowColData(i,5)=="Prem" && ContPlanGrid.getRowColData(i,8) != "" 
                &&ContPlanGrid.getRowColData(i,2) ==ttRiskCode
                &&ContPlanGrid.getRowColData(i,3) ==tDutyCode)
           		{
           		//alert(ttRiskCode+"! ��Ҫ�޸ı��ѣ�");
           		//alert(mult+"*"+tPrem);
           		 var newprem = mult*tPrem;
          		 ContPlanGrid.setRowColData(i,8,""+newprem);
          		 }
           }
          }
         }
        }
        //alert("tAmnt:"+tAmnt);
        if(tAmntInfo!=null&&tAmntInfo!=""||tAmntInfo!="null"){
        for (k=0;k<tAmntInfo.length;k++){
            //�����ζౣ�� ��212401
            tAmnt=tAmntInfo[k][0];
            tDutyCode=tAmntInfo[k][1];
            if(tAmnt!=null&&tAmnt!=""||tAmnt!="null"){
        for(i=0;i<ContPlanGrid.mulLineCount;i++){
              //alert("Factor��"+ContPlanGrid.getRowColData(i,5));
              //alert("RiskCode:"+ContPlanGrid.getRowColData(i,2));
              if(ContPlanGrid.getRowColData(i,5)=="Amnt" && ContPlanGrid.getRowColData(i,8) != "" 
                 &&ContPlanGrid.getRowColData(i,2) ==ttRiskCode
                 &&ContPlanGrid.getRowColData(i,3) ==tDutyCode)
           		{
           		//alert(ttRiskCode+"! ��Ҫ�޸ı��");
           		//alert(mult+"*"+tAmnt);
        		 var newamnt = mult*tAmnt;
         		 ContPlanGrid.setRowColData(i,8,""+newamnt);
         		 }
            }
           }
          }
         }
       }
    }else{
    //alert(tRiskCode);
    for(j=0;j<tRiskCode.length;j++){
        var ttRiskCode = tRiskCode[j];
        var tsqlprem=" select (calfactorvalue / remark2),a.dutycode  from lccontplandutyparam a ,lccontplan b where a.grpcontno = b.grpcontno and a.contplancode=b.contplancode and a.calfactor = 'Prem' and a.contplancode = '"+fm.all('ContPlanCode').value+"' and a.grpcontno = '"+fm.all('GrpContNo').value+"' and a.riskcode='"+ttRiskCode+"'";      
        var tsqlamnt=" select (calfactorvalue / remark2),a.dutycode  from lccontplandutyparam a ,lccontplan b where a.grpcontno = b.grpcontno and a.contplancode=b.contplancode and a.calfactor = 'Amnt' and a.contplancode = '"+fm.all('ContPlanCode').value+"' and a.grpcontno = '"+fm.all('GrpContNo').value+"' and a.riskcode='"+ttRiskCode+"'";      
        var tPremInfo = easyExecSql(tsqlprem);
        var tAmntInfo = easyExecSql(tsqlamnt);
       // alert("tPrem:"+tPrem);
        if(tPremInfo!=null&&tPremInfo!=""&&tPremInfo!="null"){
         for (k=0;k<tPremInfo.length;k++){
            //�����ζౣ�� ��212401
            tPrem=tPremInfo[k][0];
            tDutyCode=tPremInfo[k][1];
            if(tPrem!=null&&tPrem!=""&&tPrem!="null"){
               for(i=0;i<ContPlanGrid.mulLineCount;i++){
              //alert("Factor��"+ContPlanGrid.getRowColData(i,5));
              //alert("RiskCode:"+ContPlanGrid.getRowColData(i,2));
              if(ContPlanGrid.getRowColData(i,5)=="Prem" && ContPlanGrid.getRowColData(i,8) != "" 
                   &&ContPlanGrid.getRowColData(i,2) ==ttRiskCode
                   &&ContPlanGrid.getRowColData(i,3) ==tDutyCode)
           		{
           		//alert(ttRiskCode+"! ��Ҫ�޸ı��ѣ�");
           		//alert(mult+"*"+tPrem);
           		 var newprem = mult*tPrem;
          		 ContPlanGrid.setRowColData(i,8,""+newprem);
          		 }
             }
            }
           }
        }
        //alert("tAmnt:"+tAmnt);
        if(tAmntInfo!=null&&tAmntInfo!=""||tAmntInfo!="null"){
        for (k=0;k<tAmntInfo.length;k++){
            //�����ζౣ�� ��212401
            tAmnt=tAmntInfo[k][0];
            tDutyCode=tAmntInfo[k][1];
            if(tAmnt!=null&&tAmnt!=""||tAmnt!="null"){
               for(i=0;i<ContPlanGrid.mulLineCount;i++){
              //alert("Factor��"+ContPlanGrid.getRowColData(i,5));
              //alert("RiskCode:"+ContPlanGrid.getRowColData(i,2));
                if(ContPlanGrid.getRowColData(i,5)=="Amnt" && ContPlanGrid.getRowColData(i,8) != "" 
                    &&ContPlanGrid.getRowColData(i,2) ==ttRiskCode
                    &&ContPlanGrid.getRowColData(i,3) ==tDutyCode)
           		  {
           		  //alert(ttRiskCode+"! ��Ҫ�޸ı��");
           		  //alert(mult+"*"+tAmnt);
        		   var newamnt = mult*tAmnt;
         		   ContPlanGrid.setRowColData(i,8,""+newamnt);
         		   }
            }
           }
          }
        }
    }
    //var premsql = " select (calfactorvalue / remark2)  from lccontplandutyparam a ,lccontplan b where a.grpcontno = b.grpcontno and a.contplancode=b.contplancode and a.calfactor = 'Prem' and a.contplancode = '"+fm.all('ContPlanCode').value+"' and a.grpcontno = '"+fm.all('GrpContNo').value+"'";
    //var tPerPrem = easyExecSql(premsql);
    //var amntsql = " select (calfactorvalue / remark2)  from lccontplandutyparam a ,lccontplan b where a.grpcontno = b.grpcontno and a.contplancode=b.contplancode and a.calfactor = 'Amnt' and a.contplancode = '"+fm.all('ContPlanCode').value+"' and a.grpcontno = '"+fm.all('GrpContNo').value+"'";
    //var tPerAmnt = easyExecSql(amntsql);
    //var m=ContPlanGrid.mulLineCount
    //  if(m>=0)
    //  {
    //  var mult = fm.all('InsuMult').value;
      //   for(i=0; i<m; i++)
      //   {
      //     if(ContPlanGrid.getRowColData(i,5)=="Prem" && ContPlanGrid.getRowColData(i,8) != "")
      //     {
      //     var newprem = mult*tPerPrem;
      //     ContPlanGrid.setRowColData(i,8,""+newprem);
      //     }
      //     if(ContPlanGrid.getRowColData(i,5)=="Amnt" && ContPlanGrid.getRowColData(i,8) != "")
      //     {
      //     var newamnt = mult*tPerAmnt;
      //     ContPlanGrid.setRowColData(i,8,""+newamnt);
      ///     }
      //   }
     // }
     }
    }
	//�ж�˫������ִ�е���ʲô��ѯ
	if (cCodeName=="GrpRisk"){
		var tRiskFlag = fm.all('RiskFlag').value;
		//���ڸ����ղ���������¼�������ж���������ΪS��ʱ������
		if (tRiskFlag!="S"){
			divmainriskname.style.display = 'none';
			divmainrisk.style.display = 'none';
			fm.all('MainRiskCode').value = fm.all('RiskCode').value;
		}
		else{
			//divmainriskname.style.display = '';
			//divmainrisk.style.display = '';
			fm.all('MainRiskCode').value = fm.RiskCode.value;//edit by yaory 20050914
			//alert(fm.all('MainRiskCode').value);
		}
	}
	//�ж��Ƿ�ѡ�����ײ�
	//alert(cCodeName);//yaory
	if (cCodeName=="RiskPlan1"){
		var tRiskPlan1 = fm.all('RiskPlan1').value;
		if (tRiskPlan1!="0"){
			divriskcodename.style.display = 'none';
			divriskcode.style.display = 'none';
			divcontplanname.style.display = '';
			divcontplan.style.display = '';
			divplankind1name.style.display = '';
			divplankind1.style.display = '';	
			divplankind2name.style.display = '';
			divplankind2.style.display = '';
			divplankind3name.style.display = '';
			divplankind3.style.display = '';
			divRiskPlanPSave.style.display='';
			divRiskPlanSave.style.display='none';								
			ContPlanGrid.lock();
		}
		else{
			divriskcodename.style.display = '';
			divriskcode.style.display = '';
			divcontplanname.style.display = 'none';
			divcontplan.style.display = 'none';
			divplankind1name.style.display = 'none';
			divplankind1.style.display = 'none';	
			divplankind2name.style.display = 'none';
			divplankind2.style.display = 'none';
			divplankind3name.style.display = 'none';
			divplankind3.style.display = 'none';
			divRiskPlanPSave.style.display='none';
			divRiskPlanSave.style.display='';				
			ContPlanGrid.unLock();
		}
	}
	//�������ײ����ݴ������
	if (cCodeName=="RiskPlan"){
		var tRiskPlan = fm.all('RiskPlan').value;
		if (tRiskPlan!=null&&tRiskPlan!=""){
			//���ӷ�����GRID���ɱ༭
			initContPlanGridyry();
			showPlan();
			
			
		}
	}
}
function initContPlan(){
  		var tRiskPlan1 = fm.all('RiskPlan1').value;
		if (tRiskPlan1!="0"){
			divriskcodename.style.display = 'none';
			divriskcode.style.display = 'none';
			divcontplanname.style.display = '';
			divcontplan.style.display = '';
			divplankind1name.style.display = '';
			divplankind1.style.display = '';	
			divplankind2name.style.display = '';
			divplankind2.style.display = '';
			divplankind3name.style.display = '';
			divplankind3.style.display = '';
			divRiskPlanPSave.style.display='';
			divRiskPlanSave.style.display='none';								
			ContPlanGrid.lock();
			}
}

//���ݲ�ѯ
function AddContClick(){
	if (fm.all('ContPlanCode').value == ""){
		alert("������ƻ����룡");
		fm.all('ContPlanCode').focus();
		return false;
	}
	if(fm.all('RiskCode').value ==""){
		alert("��ѡ�����֣�");
		fm.all('RiskCode').focus();
		return false;
	}
  else{
  	var risksql="select SubRiskFlag from lmriskapp where riskcode='"+fm.all('RiskCode').value+"'"
	  var arrRiskFlag = easyExecSql(risksql);
	  fm.all('RiskFlag').value=arrRiskFlag[0][0];
	  if(fm.all('RiskFlag').value=="M")
	  {
	  	fm.all('MainRiskCode').value=fm.all('RiskCode').value;
	  }
  }	
	if(fm.all('MainRiskCode').value ==""){
		alert("������������Ϣ��");
		fm.all('MainRiskCode').focus();
		return false;
	}
	var lineCount = 0;
	var MainRiskCode = fm.all('MainRiskCode').value
	var sRiskCode ="";
	var sMainRiskCode="";
	ContPlanDutyGrid.delBlankLine("ContPlanDutyGrid");
	lineCount = ContPlanDutyGrid.mulLineCount;
	//����Ҫ���һ��У�飬�����Ƚ��鷳����ʱ������
	for (i=0;i<ContPlanGrid.mulLineCount;i++){
		sRiskCode=ContPlanGrid.getRowColData(i,2);
		sMainRiskCode=ContPlanGrid.getRowColData(i,12);
		//��Ҫ�ǿ��Ǹ��ջ���ڲ�ͬ�������£������Ҫ˫��У��
		if (sRiskCode == fm.all('RiskCode').value && sMainRiskCode == MainRiskCode){
			alert("����ӹ������ֱ��ռƻ�Ҫ�أ�");
			return false;
		}
	}

	var getWhere = "(";
	for (i=0;i<lineCount;i++){
		if (ContPlanDutyGrid.getChkNo(i)){
			//alert(ContPlanDutyGrid.getRowColData(i,1));
			getWhere = getWhere + "'"+ContPlanDutyGrid.getRowColData(i,1)+"',"
		}
	}
	if (getWhere == "("){
		alert("��ѡ��������Ϣ");
		return false;
	}
	getWhere = getWhere.substring(0,getWhere.length-1) + ")"
	//alert(getWhere);

	// ��дSQL���
	var strSQL = "";
  QueryCount = 1;//������ԭ�мƻ����޸�
	if (QueryCount == 0){
		//��ѯ�������µ����ּ���Ҫ��
		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,case a.CalFactorType when '1' then a.CalSql else '' end,'',b.RiskVer,d.GrpPolNo,'"+MainRiskCode+"',a.CalFactorType,c.CalMode "
			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCGrpPol d "
			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
			+ "and a.DutyCode in "+ getWhere + " and a.ChooseFlag in ('0','2') "
			+ "and GrpContNO = '"+GrpContNo+"' and a.RiskCode = '"+fm.all('RiskCode').value+"' order by a.RiskCode,a.DutyCode,a.FactorOrder";
		//fm.all('PlanSql').value = strSQL;
		//alert(strSQL);
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
		if (!turnPage.strQueryResult) {
			alert("û�и�����������Ҫ����Ϣ��");
			return false;
		}
		QueryCount = 1;
		//��ѯ�ɹ������ַ��������ض�ά����
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
		turnPage.pageDisplayGrid = ContPlanGrid;
		//����SQL���
		turnPage.strQuerySql = strSQL;
		//���ò�ѯ��ʼλ��
		turnPage.pageIndex = 0;
		//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
		//����MULTILINE������ʾ��ѯ���
		displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
	}
	else{
		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,case a.CalFactorType when '1' then a.CalSql else '' end,'',b.RiskVer,d.GrpPolNo,'',a.CalFactorType,c.CalMode "
			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCGrpPol d "
			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
			+ "and a.DutyCode in "+ getWhere + " and a.ChooseFlag in ('0','2') "
			+ "and GrpContNO = '"+GrpContNo+"' and a.RiskCode = '"+fm.all('RiskCode').value+"' order by a.RiskCode,a.DutyCode,a.FactorOrder";
		//fm.all('ContPlanName').value = strSQL;
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
		if (!turnPage.strQueryResult) {
			alert("û�и�����������Ҫ����Ϣ��");
			return false;
		}
		//��ѯ�ɹ������ַ��������ض�ά����
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		mulLineCount = ContPlanGrid.mulLineCount;
		//alert(mulLineCount);
		for(i=0; i<turnPage.arrDataCacheSet.length; i++){
			ContPlanGrid.addOne("ContPlanGrid");
			ContPlanGrid.setRowColData(mulLineCount+i,1,turnPage.arrDataCacheSet[i][0]);
			ContPlanGrid.setRowColData(mulLineCount+i,2,turnPage.arrDataCacheSet[i][1]);
			ContPlanGrid.setRowColData(mulLineCount+i,3,turnPage.arrDataCacheSet[i][2]);
			ContPlanGrid.setRowColData(mulLineCount+i,4,turnPage.arrDataCacheSet[i][3]);
			ContPlanGrid.setRowColData(mulLineCount+i,5,turnPage.arrDataCacheSet[i][4]);
			ContPlanGrid.setRowColData(mulLineCount+i,6,turnPage.arrDataCacheSet[i][5]);
			ContPlanGrid.setRowColData(mulLineCount+i,7,turnPage.arrDataCacheSet[i][6]);
			ContPlanGrid.setRowColData(mulLineCount+i,8,turnPage.arrDataCacheSet[i][7]);
			ContPlanGrid.setRowColData(mulLineCount+i,10,turnPage.arrDataCacheSet[i][9]);
			ContPlanGrid.setRowColData(mulLineCount+i,11,turnPage.arrDataCacheSet[i][10]);
			ContPlanGrid.setRowColData(mulLineCount+i,12,MainRiskCode);
			ContPlanGrid.setRowColData(mulLineCount+i,13,turnPage.arrDataCacheSet[i][12]);
		}
	}
	//initContPlanDutyGrid();
}

//�����ύ�����棩
function submitForm()
{

    if(verifyInput() == false)
     return false;
	if (!beforeSubmit())
	{
		return false;
	}	
	if(!SaveRiskSubmit())
	{
		return false;
	}
    if(checkCalFactor()==false) return false;
	fm.all('mOperate').value = "INSERT||MAIN";
	if (fm.all('mOperate').value == "INSERT||MAIN")
	{
		if (!confirm('�ƻ� '+fm.all('ContPlanCode').value+' �µ�ȫ������Ҫ����Ϣ�Ƿ���¼����ϣ����Ƿ�Ҫȷ�ϲ�����'))
		{
			return false;
		}
	}
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
	QueryCount = 0;	//���³�ʼ����ѯ����
	fm.action="../cardgrp/ContPlanSave.jsp";
	fm.submit(); //�ύ
}

//������һ��
function returnparent()
{
	top.close();
	top.opener.fillriskgrid();
}

function getRiskCount(){
    var j=0;
    var k=0;
    var tRiskCode=new Array(); 
    for(i=0;i<ContPlanGrid.mulLineCount;i++)
    {
        var tGetCode = ContPlanGrid.getRowColData(i,2);
        if(tRiskCode[k]==tGetCode){
             continue;
        }else{
           tRiskCode[j]=tGetCode;
           k=j;
           j=j+1;
        }
    }
    return tRiskCode;
}

function checkCalFactor(){
   //У��ƻ�Ҫ�ص�ֵ�Ƿ���Ϻ�̨����Ҫ��
   var tCalRule="";
   var tCalRuleInfo="";//�жϼ�������Ƿ��¼
   var tFloatRate="";
   var tFloatRateInfo="";//�жϷ����Ƿ��¼
   var tAmnt="";
   var tAmntInfo="";//�жϱ����Ƿ��¼
   var tPrem="";
   var tPremInfo="";//�жϱ����Ƿ��¼
   var tCalFactor="";
   var tMult="";
   var tMultInfo="";
   var tStanbyFlag="";
   var tStanbyFlagInfo="";
   var tRiskCode=getRiskCount();//�õ��ƻ��ж���������� ������ֵı��ϼƻ�Ҫ�ֱ��ÿ�����ֵ�Ҫ�ؽ���У��
   for(j=0;j<tRiskCode.length;j++){
        var tNowRiskCode = tRiskCode[j];
   		var tRiskName = "";
  		 for(i=0;i<ContPlanGrid.mulLineCount;i++)
  		 {
  		  if(tNowRiskCode==ContPlanGrid.getRowColData(i,2)){
  		  tRiskName = ContPlanGrid.getRowColData(i,1);
  		    tCalFactor = ContPlanGrid.getRowColData(i,5);
  	    //alert("tCalFactor:"+tCalFactor);
  		      if(tCalFactor=="CalRule")
   		      {
      		       tCalRule = ContPlanGrid.getRowColData(i,8);
      		       tCalRuleInfo = ContPlanGrid.getRowColData(i,7);
      		       //alert("tCalRule:"+tCalRule);
             //alert("tCalRuleInfo:"+tCalRuleInfo);
     		    }
     		    if(tCalFactor=="Prem")
    		     {
   		          tPrem = ContPlanGrid.getRowColData(i,8);
    		         tPremInfo = ContPlanGrid.getRowColData(i,6);
    		         //alert("tPrem:"+tPrem);
    		     }
    		     if(tCalFactor=="Amnt")
    		     {
    		         tAmnt = ContPlanGrid.getRowColData(i,8);
     	        tAmntInfo = ContPlanGrid.getRowColData(i,6);
             //alert("tAmnt:"+tAmnt);
      		   }
     		    if(tCalFactor=="FloatRate")
      		   {
     		        tFloatRate = ContPlanGrid.getRowColData(i,8);
     		        tFloatRateInfo = ContPlanGrid.getRowColData(i,6);
            // alert("tFloatRate:"+tFloatRate);
      		   }
     		    if(tCalFactor=="Mult"){
     		    	tMult = ContPlanGrid.getRowColData(i,8);
     		    	tMultInfo = ContPlanGrid.getRowColData(i,6);
     		    	if(tMult!=""){
     		           return true;
     		    	}
     		    }
     		    if(tCalFactor=="StandbyFlag1"){
     		    	tStanbyFlag = ContPlanGrid.getRowColData(i,8);
     		    	tStanbyFlagInfo = ContPlanGrid.getRowColData(i,6);
     		    	if(tStanbyFlag!=""){
     		           return true;
     		    	}
     		    }
     		    if(tCalFactor=="StandbyFlag2"){
     		    	tStanbyFlag = ContPlanGrid.getRowColData(i,8);
     		    	tStanbyFlagInfo = ContPlanGrid.getRowColData(i,6);
     		    	if(tStanbyFlag!=""){
     		           return true;
     		    	}
     		    }
     		    if(tCalFactor=="StandbyFlag3"){
     		    	tStanbyFlag = ContPlanGrid.getRowColData(i,8);
     		    	tStanbyFlagInfo = ContPlanGrid.getRowColData(i,6);
     		    	if(tStanbyFlag!=""){
     		           return true;
     		    	}
     		    }
      
   		}
   		}
  		//0-������ 1-Լ������ 2-�������ۿ� 3-Լ�����ѱ���
   		if(tCalRuleInfo!=""){
  		 if(tCalRule==""){
  		   alert(tRiskName+"!��¼�������򣡡�"+tCalRuleInfo+"��");
   		   return false;
		   }else{
		     if(tCalRule=="1")
		     {
      //Լ������  ����=����*��������
      		if(tAmntInfo!=""&&tPremInfo!=""&&tFloatRateInfo!=""){
        //�ܹ�¼�뱣����ѡ�����ʱ�Ŷ����ּ������������У��
      			if(tAmnt==""||tPrem==""||tFloatRate==""){
     			    alert(tRiskName+"!��¼�뱣�ѡ�����������ʣ�");
     			    return false;
     			 }else{
     			    var tCalPrem=tAmnt*tFloatRate;
    			     if(tCalPrem!=tPrem){
    			          alert(tRiskName+"!¼��ı���"+tPrem+"���������ı���"+tCalPrem+"����,������¼�룡");
    			          return false;         
    			     }
    		 	 }
    		 	}
   			 }else if(tCalRule=="2"){
   		     //�������ۿ� ֻ¼�뱣���������
   			     if(tAmntInfo!=""&&tPremInfo!=""&&tFloatRateInfo!=""){
   	     //�ܹ�¼�뱣����ѡ�����ʱ�Ŷ����ּ������������У��
   			     if(tPrem==""){
   	 		      if(tAmnt==""||tFloatRate==""){
   	  		         alert(tRiskName+"!��¼�뱣��������ʣ�");
   	 		          return false;
   	 		      }
   	 		    }else{
   	 		       alert(tRiskName+"!���������ۿۡ�������¼�뱣�ѣ�");
   	 	       return false;
   			     }
   			    }
  		 	 }else if(tCalRule=="3"){
  		 	 if(tAmntInfo!=""&&tPremInfo!=""&&tFloatRateInfo!=""){
   	 //�ܹ�¼�뱣����ѡ�����ʱ�Ŷ����ּ������������У��
   			      if(tFloatRate==""){
   	 		       if(tAmnt==""||tPrem==""){
   	 		          alert(tRiskName+"!��¼�뱣�ѡ����");
   	 		          return false;
   	 		       }
   	 		     }else{
   			        alert(tRiskName+"!��Լ�����ѱ��������¼�븡�����ʣ�");
   	 		       return false;
   			      }
   	 		    }
 		  	 }else if(tCalRule=="0"){
 		  	 if(tFloatRateInfo!=""){
 	  	 //�ܹ�¼�����ʱ�ڻ�������У��
 		  	      if(tFloatRate!=""){
  		 	        alert(tRiskName+"!�������ʡ�������¼�븡�����ʣ�");
  		 	        return false;
  		 	      }
  		 	      if(tAmnt==""&&tPrem==""){
  		 	        alert(tRiskName+"!�������ʡ�������Ѳ���ͬʱΪ�գ�");
  		 	        return false;
  		 	      }
  		 	     }
 		  	 }else{
  		 	     alert(tRiskName+"!¼��ļ�����򲻺Ϸ�,������¼�룡��"+tCalRuleInfo+"��");
  		 	     return false;
 		  	 }
 		  }
 		  //��������������� У����������Ҫ��
 		  var tCalRule="";
   var tCalRuleInfo="";//�жϼ�������Ƿ��¼
   var tFloatRate="";
   var tFloatRateInfo="";//�жϷ����Ƿ��¼
   var tAmnt="";
   var tAmntInfo="";//�жϱ����Ƿ��¼
   var tPrem="";
   var tPremInfo="";//�жϱ����Ƿ��¼
   var tCalFactor="";
  		 }else{
  		   //У��221702��221703��������ÿ��סԺ��������
  		   if(tNowRiskCode!=""&&(tNowRiskCode=="221702"||tNowRiskCode=="221703")){
  		     var tMult=fm.all('InsuMult').value;
  		     if(tMult!=""&&tMult=="1"){
  		       //һ��ʱ ���������30��50
  		       if(tAmnt!="30"&&tAmnt!="50"){
  		         alert(tRiskName+"!��Ͷ������Ϊ 1 ʱ�����ս�ֻ��Ϊ30��50");
  		         return false;
  		       }
  		     }else{
  		       //Ϊ���ʱ У�鱣�ս���Ƿ�Ϊ30��50 ��������
  		       if(Math.round(tAmnt/tMult) == tAmnt/tMult  )
   				{
   					if(tAmnt/tMult == 30  || tAmnt/tMult == 50)
   					{
   					alert(tRiskName+"!��¼��ı���"+tAmnt+"���ܱ���,���ݱ���Ϊ"+tAmnt/tMult+ "Ԫ");
					//if( !confirm(tRiskName+"��¼��ı���"+tAmnt+"���ܱ���,���ݱ���Ϊ"+tAmnt/tMult+ "Ԫ,�Ƿ񱣴�!" ))
					 // return false;
   		 		   }else{
   		  		  	alert(tRiskName+"!���ս���30Ԫ��50Ԫ��������,����!");
						return false;
   		 		   }
   		    
   				}
   				else{
   					alert(tRiskName+"!���ս���30Ԫ��50Ԫ��������,����!");
					return false;
   				}
  		     }
  		   }
  		 }
   }
   return true;
}
//�����ύ��ɾ����
function DelContClick(){
  //  if(verifyInput()==false)
   //   return false;
	//�˷����õ���������Ҫ-1����
	var line = ContPlanCodeGrid.getSelNo();
	if (line == 0)
	{
		alert("��ѡ��Ҫɾ���ļƻ���");
		fm.all('ContPlanCode').value = "";
		return false;
	}
	else
	{
		fm.all('ContPlanCode').value = ContPlanCodeGrid.getRowColData(line-1,1);
	}	
	fm.all('mOperate').value = "DELETE||MAIN";	
	
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
	QueryCount = 0;	//���³�ʼ����ѯ����
	fm.submit(); //�ύ
}

//�����ύ���޸ģ�
function UptContClick(){
    if(verifyInput()==false)
       return false;
	if (tSearch == 0){
		alert("���Ȳ�ѯҪ�޸ĵı��ϼƻ���");
		return false;
	}

	fm.all('mOperate').value = "UPDATE||MAIN";
	if (!beforeSubmit()){
		return false;
	}
	if(checkCalFactor()==false) return false;
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
	QueryCount = 0;	//���³�ʼ����ѯ����
	fm.submit(); //�ύ
	
	//����
	parent.scrollTo(0,-25000);
}

//����У��
function beforeSubmit(){
	if (fm.all('ContPlanCode').value == ""){
		alert("������ƻ����룡");
		fm.all('ContPlanCode').focus();
		return false;
	}
	if (fm.all('ContPlanName').value == ""){
		alert("�����뱣�ռƻ����ƣ�");
		fm.all('ContPlanName').focus();
		return false;
	}
	if (fm.all('Peoples3').value == ""){
		alert("������Ͷ��������");
		fm.all('Peoples3').focus();
		return false;
	}
	if (ContPlanGrid.mulLineCount == 0){
		alert("�����뱣�ռƻ���ϸ��Ϣ");
		return false;
	}
	var lineCount = ContPlanGrid.mulLineCount;
	var sValue;
	var sCalMode;
	//
	var dutycode;
	var hierarhy;
	var flag109;//�Ƿ����109����
	var flagerror;//�������109���ֿ�ѡ���εĵ��δ��ڻ���������Ϊ1
	var duty1;
	var duty2;
	var duty3;
	var duty4;
	//���Ҫ��ֵ��ϢУ��
	for(var i=0;i<lineCount;i++){
      	sValue = ContPlanGrid.getRowColData(i,8);
      	sCalMode = ContPlanGrid.getRowColData(i,5);
      	dutycode=ContPlanGrid.getRowColData(i,3);
      	hierarhy=ContPlanGrid.getRowColData(i,8);
      	//alert(dutycode);
      	//return;
      	//���Ӷ�109������У��
      	if(dutycode.substring(0,3)=="109")
      	{
      		flag109="1";
      	}
      	if(dutycode=="109001" && sCalMode=="StandbyFlag1")
      	{
      		duty1=hierarhy;
      		//alert(duty1);
      	}else if(dutycode=="109002" && sCalMode=="StandbyFlag1")
      		{
      			duty2=hierarhy;
      			//alert(duty2);
      		}else if(dutycode=="109003" && sCalMode=="StandbyFlag1")
      			{
      				duty3=hierarhy;
      				//alert(duty3);
      			}else if(dutycode=="109004" && sCalMode=="StandbyFlag1")
      				{
      					duty4=hierarhy;
      					//alert(duty4);
      				}
      	//alert(sCalMode);
      	//����У��
      	if (sCalMode == "Amnt" || sCalMode == "FloatRate" || sCalMode == "InsuYear" || sCalMode == "Mult" || sCalMode == "Prem" || sCalMode == "PayEndYear" || sCalMode == "Count" || sCalMode == "GetLimit" || sCalMode == "GetRate" || sCalMode == "PeakLine")
      	{
			if (sValue!=""){
				if (!isNumeric(sValue)){
					alert("��¼�����֣�");
					ContPlanGrid.setFocus(i,8);
					return false;
				}
			}
		}
		
		if (sCalMode == "CalRule")
      	{
			if (sValue!=""){
				if (sValue!="1" && sValue!="2" && sValue!="3" && sValue!="0"){
					alert("��¼����ȷ�ļ������");
					ContPlanGrid.setFocus(i,8);
					return false;
				}
			}
		}
		
		if (sCalMode == "PayIntv")
      	{
			if (sValue!=""){
				if (sValue!="1" && sValue!="3" && sValue!="6" && sValue!="0" && sValue!="12"){
					alert("��¼����ȷ�Ľ��ѷ�ʽ��");
					ContPlanGrid.setFocus(i,8);
					return false;
				}
			}
		}
		
		if (sCalMode == "EndDate")
      	{
			if (sValue!=""){
				if (!isDate(sValue)){
					alert("��¼����ȷ����ֹ���ڣ�");
					ContPlanGrid.setFocus(i,8);
					return false;
				}
			}
		}
		//�����㱣��У��
		//alert(sCalMode);
//      	if (sCalMode == "P"){
//			if (sValue==""){
//				alert("��¼�뱣�ѣ�");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//			if (!isNumeric(sValue)){
//				alert("��¼�����֣�");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//			if(sValue=0){
//				alert("���Ѳ���Ϊ0��");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//		}
		//�����㱣��У��
//      	if (sCalMode == "G"){
//			if (sValue==""){
//				alert("��¼�����֣�");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//			if (!isNumeric(sValue)){
//				alert("��¼�����֣�");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//			if(sValue==0){
//				alert("�����Ϊ0��");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//		}
		//���������㱣�ѱ���У��
//      	if (sCalMode == "O"){
//			if (sValue==""){
//				alert("��¼��Ҫ��ֵ��");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//			if (!isNumeric(sValue)){
//				alert("��¼�����֣�");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//			if(sValue==0){
//				alert("Ҫ��ֵ����Ϊ0��");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//		}
		//¼�뱣�ѱ���У��
//      	if (sCalMode == "I"){
//			if (sValue!=""){
//				if (!isNumeric(sValue)){
//					alert("��¼�����֣�");
//					ContPlanGrid.setFocus(i,8);
//					return false;
//				}
//			}
//		}
	}
	//���Ӷ�109�Ĵ���
	if(duty1!="")
	{
		if(duty2!="")
		{
			if(duty2>duty1)
			{
				flagerror="1";
			}
		}
		if(duty3!="")
		{
			if(duty3>duty1)
			{
				flagerror="1";
			}
		}
		if(duty4!="")
		{
			if(duty4>duty1)
			{
				flagerror="1";
			}
		}
	}
	if(flagerror=="1")
	{
		alert("109���ֵĿ�ѡ���εĵ��β��ܴ��ڻ�������!");
		return;
	}
	return true;
   
}

function initFactoryType(tRiskCode)
{
	// ��дSQL���
	var k=0;
	var strSQL = "";
	strSQL = "select distinct a.FactoryType,b.FactoryTypeName,a.FactoryType||"+tRiskCode+" from LMFactoryMode a ,LMFactoryType b  where 1=1 "
		   + " and a.FactoryType= b.FactoryType "
		   + " and (RiskCode = '"+tRiskCode+"' or RiskCode ='000000' )";
    var str  = easyQueryVer3(strSQL, 1, 0, 1);
    return str;
}

/*********************************************************************
 *  Click�¼�������������ռƻ�ҪԼ¼�롱��ťʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function nextstep()
{
	var newWindow = window.open("../cardgrp/ContPlanNextInput.jsp?GrpContNo="+fm.all('GrpContNo').value+"&LoadFlag="+LoadFlag,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

function afterSubmit(FlagStr,content){
	showInfo.close();
	if( FlagStr == "Fail" ){
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	else{
		content = "�����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	    initContPlanCodeGrid();
	    initContPlanDutyGrid();
	    initContPlanGrid();
	    easyQueryClick();
	    tSearch = 0;
	    QueryCount = 0;
	}
	fm.all('mOperate').value = "";
}

function QueryDutyClick(){
	if (fm.all('ContPlanCode').value == ""){
		alert("�����뱣�ϼ���");
		fm.all('ContPlanCode').focus();
		return false;
	}
	if(fm.all('RiskCode').value ==""){
		alert("��ѡ�����֣�");
		fm.all('RiskCode').focus();
		return false;
	}

	initContPlanDutyGrid();

	//��ѯ�������µ����ּ���Ҫ��
	var sql="select risktype6 from lmriskapp where riskcode='"+fm.all('RiskCode').value+"'";
	var Result = easyExecSql(sql, 1, 0);
	if(Result=="1")//139 151
	{
		strSQL = "select distinct a.DutyCode,b.DutyName,a.ChoFlag,case a.ChoFlag when 'M' then '��ѡ' when 'B' then '����' else '��ѡ' end ChoFlagName "
		+ "from LMRiskDuty a, LMDuty b ,LMRiskDutyFactor c "
		+ "where a.DutyCode = b.DutyCode and a.RiskCode = c.RiskCode and a.DutyCode = c.DutyCode "
		+ "and a.RiskCode = '"+fm.all('RiskCode').value+"' order by a.DutyCode";
	}else if(Result=="4"){//188 198
	strSQL = "select distinct a.DutyCode,b.DutyName,a.ChoFlag,case a.ChoFlag when 'M' then '��ѡ' when 'B' then '����' else '��ѡ' end ChoFlagName "
		+ "from LMRiskDuty a, LMDuty b ,LMRiskDutyFactor c "
		+ "where a.DutyCode = b.DutyCode and a.RiskCode = c.RiskCode and a.DutyCode = c.DutyCode "
		+ "and a.RiskCode = '"+fm.all('RiskCode').value+"' order by a.DutyCode";
	}else if(Result=="3"){//162
	strSQL = "select distinct a.DutyCode,b.DutyName,a.ChoFlag,case a.ChoFlag when 'M' then '��ѡ' when 'B' then '����' else '��ѡ' end ChoFlagName "
		+ "from LMRiskDuty a, LMDuty b ,LMRiskDutyFactor c "
		+ "where a.DutyCode = b.DutyCode and a.RiskCode = c.RiskCode and a.DutyCode = c.DutyCode "
		+ "and a.RiskCode = '"+fm.all('RiskCode').value+"'  order by a.DutyCode";
	
	}else{
	strSQL = "select distinct a.DutyCode,b.DutyName,a.ChoFlag,case a.ChoFlag when 'M' then '��ѡ' when 'B' then '����' else '��ѡ' end ChoFlagName "
		+ "from LMRiskDuty a, LMDuty b ,LMRiskDutyFactor c "
		+ "where a.DutyCode = b.DutyCode and a.RiskCode = c.RiskCode and a.DutyCode = c.DutyCode "
		+ "and a.RiskCode = '"+fm.all('RiskCode').value+"' order by a.DutyCode";
}
	//prompt("�������β�ѯ",strSQL);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	if (!turnPage.strQueryResult) {
		alert("û�и������µ�������Ϣ��");
		return false;
	}
	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	turnPage.pageDisplayGrid = ContPlanDutyGrid;
	//����SQL���
	turnPage.strQuerySql = strSQL;
	//���ò�ѯ��ʼλ��
	turnPage.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
	var cDutyCode="";
	var tSql="";
	for(var i=0;i<=ContPlanDutyGrid.mulLineCount-1;i++){
	  cDutyCode=ContPlanDutyGrid.getRowColData(i,1);
	  tSql="select choflag from lmriskduty where riskcode='"+fm.all('RiskCode').value+"' and dutycode='"+cDutyCode+"'";
	  var arrResult=easyExecSql(tSql,1,0);
	  //alert("ChoFlag:"+arrResult[0]);
	  if(arrResult[0]=="M"){
	  	 ContPlanDutyGrid.checkBoxSel(i+1);
	  }
	}
}

function easyQueryClick(){
	initContPlanCodeGrid();

	//��ѯ�������µ����ּ���Ҫ��
	strSQL = "select ContPlanCode,ContPlanName,PlanSql,Peoples3 "
		     + "from LCContPlan "
		     + "where 1=1 "
		     + "and GrpContNo = '"+fm.all('GrpContNo').value+"' and ContPlanCode <> '00' order by ContPlanCode";
	//fm.all('ContPlanName').value = strSQL;
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//���û����Ҳ���쳣
	if (!turnPage.strQueryResult) {
		//return false;
	}
	else{
		//QueryCount = 1;
		//��ѯ�ɹ������ַ��������ض�ά����
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
		turnPage.pageDisplayGrid = ContPlanCodeGrid;
		//����SQL���
		turnPage.strQuerySql = strSQL;
		//���ò�ѯ��ʼλ��
		turnPage.pageIndex = 0;
		//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
		//����MULTILINE������ʾ��ѯ���
		displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
	}
	//����ǰ��Ĳ�ѯʲô�������ִ������Ĳ�ѯ
	//��������������󣬵��²�ѯʧ�ܣ������������
	strSQL = "select GrpContNo,ProposalGrpContNo,ManageCom,AppntNo,GrpName from LCGrpCont where 1 = 1 and GrpContNo = '" + GrpContNo + "'";
	turnPage.strQueryResult   = easyQueryVer3(strSQL, 1, 0, 1);
	turnPage.arrDataCacheSet  = decodeEasyQueryResult(turnPage.strQueryResult);
	if(turnPage.arrDataCacheSet != null)
	{
	fm.all('GrpContNo').value = turnPage.arrDataCacheSet[0][0];
	fm.all('ProposalGrpContNo').value = turnPage.arrDataCacheSet[0][1];
	fm.all('ManageCom').value = turnPage.arrDataCacheSet[0][2];
	fm.all('AppntNo').value   = turnPage.arrDataCacheSet[0][3];
	fm.all('GrpName').value   = turnPage.arrDataCacheSet[0][4];
	}
	  
}

//��ѡ���������¼�
function ShowContPlan(parm1,parm2){
	if(fm.all(parm1).all('InpContPlanCodeGridSel').value == '1'){
		//��ǰ�е�1�е�ֵ��Ϊ��ѡ��
		var cContPlanCode = fm.all(parm1).all('ContPlanCodeGrid1').value;	//�ƻ�����
		var cContPlanName = fm.all(parm1).all('ContPlanCodeGrid2').value;	//�ƻ�����
		var cPlanSql = fm.all(parm1).all('ContPlanCodeGrid3').value;	//����˵��
		var cPeoples3 = fm.all(parm1).all('ContPlanCodeGrid4').value;	//�ɱ�����
		fm.all('ContPlanCode').value = cContPlanCode;
		fm.all('ContPlanName').value = cContPlanName;
		fm.all('PlanSql').value = cPlanSql;
		fm.all('Peoples3').value = cPeoples3;
		//alert(cContPlanCode);
		var cGrpContNo = fm.all('GrpContNo').value;
		
		//����Ǳ����ײ���Ҫ���ƻ������뱣���ײ����Ƹ�ֵ
		var arrQueryResult = easyExecSql("select plantype,remark,remark2 from lccontplan where grpcontno='"+GrpContNo+"' and contplancode='"+cContPlanCode+"'", 1, 0);
    if(arrQueryResult==null)
    {
    	alert("���ϼƻ���ѯʧ�ܣ�");
    	return;
    }
  
    if(arrQueryResult[0][0]=="1")
    {
    	divriskcodename.style.display = 'none';
			divriskcode.style.display = 'none';
			divcontplanname.style.display = '';
			divcontplan.style.display = '';
			if (LoadFlag==4||LoadFlag==16||LoadFlag==11||LoadFlag==18)//��init�г�ʼ������һ��
			{
		      divRiskPlanPSave.style.display='none';
			    divRiskPlanSave.style.display='none';						
			}else
				{
		      divRiskPlanPSave.style.display='';
			    divRiskPlanSave.style.display='none';					
				}

			ContPlanGrid.lock();
			fm.RiskPlan1.value="1";
			fm.RiskPlan1Name.value="�����ײ�";
			fm.RiskPlan.value=arrQueryResult[0][1];
			fm.InsuMult.value = arrQueryResult[0][2];
			fm.RiskPlanName.value=cContPlanName;
    }
   else
   	{
			fm.RiskPlan1.value="0";
			fm.RiskPlan1Name.value="�ǹ̶��ƻ�";  
			fm.InsuMult.value = arrQueryResult[0][2];
			if (LoadFlag==4||LoadFlag==16||LoadFlag==11||LoadFlag==18)//��init�г�ʼ������һ��
			{			 		
   	     divRiskPlanPSave.style.display='none';
				 divRiskPlanSave.style.display='none';
		  }else
		  	{
   	     divRiskPlanPSave.style.display='none';
				 divRiskPlanSave.style.display='';		  		
		  	}
			divcontplanname.style.display = 'none';
			divcontplan.style.display = 'none';
   	}

		//��ѯ�������µ����ּ���Ҫ��
		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,d.CalFactorValue,d.Remark,b.RiskVer,d.GrpPolNo,d.MainRiskCode,d.CalFactorType,c.CalMode "
			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCContPlanDutyParam d "
			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
			+ "and a.DutyCode = d.DutyCode and a.CalFactor = d.CalFactor and b.RiskVer = d.RiskVersion "
			+ "and ContPlanCode = '"+fm.all('ContPlanCode').value+"'"
			+ "and GrpContNO = '"+GrpContNo+"' order by a.RiskCode,d.MainRiskCode,a.DutyCode,a.factororder";
		//fm.all('PlanSql').value = strSQL;
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
		//ԭ���ϲ���ʧ�ܣ��ٺ�
		if (!turnPage.strQueryResult) {
			alert("��ѯʧ�ܣ�");
			return false;
		}
		//��ѯ�ɹ������ַ��������ض�ά����
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
		turnPage.pageDisplayGrid = ContPlanGrid;
		//����SQL���
		turnPage.strQuerySql = strSQL;
		//���ò�ѯ��ʼλ��
		turnPage.pageIndex = 0;
		//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
		//����MULTILINE������ʾ��ѯ���
		displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
		QueryCount = 1;
		tSearch = 1;
	}
}

//ѡ�����ײ�
function showPlan(){
	var arrResult  = new Array();
	strSQL = "select a.ContPlanCode,a.ContPlanName,a.PlanSql,a.Peoples3,b.RiskCode,b.MainRiskCode from LDPlan a,LDPlanRisk b where a.ContPlanCode = b.ContPlanCode and a.ContPlanCode='"+fm.all("RiskPlan").value+"'";

	var cGrpContNo = fm.all('GrpContNo').value;

	arrResult =  decodeEasyQueryResult(easyQueryVer3(strSQL, 1, 0, 1));
	if(arrResult==null){
		alert("��ѯ�����ײ�����ʧ�ܣ�");
	}else{
		fm.all('ContPlanCode').value = arrResult[0][0];
		fm.all('ContPlanName').value = arrResult[0][1];
		fm.all('PlanSql').value      = arrResult[0][2];
		fm.all('Peoples3').value     = arrResult[0][3];
		fm.all('RiskCode').value     = arrResult[0][4];
		fm.all('MainRiskCode').value = arrResult[0][5];
	}
	//��ѯ�������µ����ּ���Ҫ��
	//strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,d.CalFactorValue,d.Remark,b.RiskVer,e.GrpPolNo,d.MainRiskCode,d.CalFactorType,c.CalMode "
	//	+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LDPlanDutyParam d,LCGrpPol e "
	//	+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
	//	+ "and a.DutyCode = d.DutyCode and a.CalFactor = d.CalFactor and b.RiskVer = d.RiskVersion "
	//	+ "and ContPlanCode = '"+fm.all('RiskPlan').value+"' "
	//	+ "and  e.RiskCode = d.RiskCode  order by a.RiskCode,d.MainRiskCode,a.DutyCode";
	
	strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,d.CalFactorValue,d.Remark,b.RiskVer,'',d.MainRiskCode,d.CalFactorType,c.CalMode "
			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LDPlanDutyParam d "
			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
			+ "and a.DutyCode = d.DutyCode and a.CalFactor = d.CalFactor and b.RiskVer = d.RiskVersion "
			+ "and ContPlanCode = '"+fm.all('RiskPlan').value+"'"
			+ "order by a.RiskCode,d.MainRiskCode,a.DutyCode";

	//fm.all('PlanSql').value = strSQL;
	//alert(strSQL);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//ԭ���ϲ���ʧ��
		
	if (!turnPage.strQueryResult) {
		alert("��ѯʧ�ܣ�");
		return false;
	}
		
	//��ѯ�ɹ������ַ��������ض�ά����
		
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);	
	//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	turnPage.pageDisplayGrid = ContPlanGrid;
	//����SQL���
	turnPage.strQuerySql = strSQL;
	//���ò�ѯ��ʼλ��
	turnPage.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
	QueryCount = 1;
	tSearch = 1;
}

//�ƻ�������޸Ĳ�ѯ״̬����
function ChangePlan(){
	//alert(fm.RiskPlan1.value);
	var ch=fm.RiskPlan1.value;
	if(ch == 0)
	{
	  QueryCount = 0;
    //initContPlanDutyGrid();
    //initContPlanGrid();
  }
}
function initPlanKind2(cObjCode)
{
	var tPlanKind1=fm.all('PlanKind1').value;
	//alert ("PlanKind1:"+tPlanKind1);
	if(tPlanKind1!='')
	{
	mPlnaKind2="1 and plankind1=#"+tPlanKind1+"#"; 
  }	
  else if(tPlanKind1=='')
  {
  mPlnaKind2="1"; 
  }  
  showCodeList('PlanKind2',[cObjCode],[0], null, mPlnaKind2, "1"); 
}
function initPlanKind3(cObjCode)
{
	var tPlanKind1=fm.all('PlanKind1').value;
	var tPlanKind2=fm.all('PlanKind2').value;
	//alert ("PlanKind1:"+tPlanKind1);
	//alert ("PlanKind2:"+tPlanKind2);
	if(tPlanKind1!=''&&tPlanKind2!='')
	{
	mPlnaKind3="1 and plankind1=#"+tPlanKind1+"# and plankind2=#"+tPlanKind2+"#"; 
  }	
  else if(tPlanKind2==''&&tPlanKind1!='')
  {
  mPlnaKind3="1 and plankind1=#"+tPlanKind1+"#"; 
  }
  else if(tPlanKind2!=''&&tPlanKind1=='')
  {
  mPlnaKind3="1 and plankind2=#"+tPlanKind2+"#"; 
  }  
  else if(tPlanKind2==''&&tPlanKind1=='')
  {
  mPlnaKind3="1"; 
  }   
  showCodeList('PlanKind3',[cObjCode],[0], null, mPlnaKind3, "1"); 
}
function initRiskPlan(cObjCode,cObjName)
{
	var tPlanKind1=fm.all('PlanKind1').value;
	var tPlanKind2=fm.all('PlanKind2').value;
	var tPlanKind3=fm.all('PlanKind3').value;
	mRiskPlan="1"
	if(tPlanKind1!='')
	{
		mRiskPlan=mRiskPlan+" and plankind1=#"+tPlanKind1+"#";
	}
  if(tPlanKind2!='')
  {
  	mRiskPlan=mRiskPlan+" and plankind2=#"+tPlanKind2+"#";
  }
  if(tPlanKind3!='')
  {
  	mRiskPlan=mRiskPlan+" and plankind3=#"+tPlanKind3+"#";
  }
	showCodeList('RiskPlan',[cObjCode,cObjName],[0,1], null, mRiskPlan, "1");    
}
function SaveRiskSubmit()
{
		if(fm.all('RiskPlan1').value=="1")
	{
		//alert("riskplan:"+fm.all('RiskPlan1').value);
  fm.all('mOperate').value="INSERT||GROUPRISK";
  
  //var showStr="��������ŵ�������Ϣ�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  
  fm.action="../cardgrp/GroupPlanRiskSave.jsp";
  fm.submit();
  return true;
  //alert("���ؽ����"+fm.all("mFlagStr").value);
  //if(fm.all("mFlagStr").value=="Succ")
  //{
  //return true;
  //}
  //else
  //	{
  //	alert("���ֱ���ʧ�ܣ�");
  //	return true;	
  //	}
  }
else
	{
		return true;	
	}


}