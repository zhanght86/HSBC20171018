var showInfo;
var approvefalg;
var arrResult;
var arrResult1;
var mDebug = "0";
var mOperate = 0;
var mAction = "";
var mSwitch = parent.VD.gVSwitch;
var mShowCustomerDetail = "GROUPPOL";
var turnPage = new turnPageClass();
this.window.onfocus = myonfocus;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";

function afterCodeSelect( cCodeName, Field )
{
//alert("cCodeName:"+cCodeName);
 	//alert("cCodeName==="+cCodeName);
	if(cCodeName=="CardType"){
	//alert(123);
	//���뵥֤¼��ҳ�� ģ��proposalinput.jspҳ��
	    var CardType =fm.CardType.value;
		//alert("fm.CardType.value:"+fm.CardType.value);
		parent.fraInterface.location ='MSCardContInputMain.jsp?CardType='+CardType;
	//getRiskInput();
	}
	//�Զ���д��������Ϣ
	  if (cCodeName == "customertype") {
	  var ContType="1";
	  	//alert("12:"+Field.value);
	  	
	    if (Field.value == "A") {
	   // alert("ContType:"+ContType);
	  	  if(ContType!="2")
	  	  {
          var index = BnfGrid.mulLineCount;
          //alert("document.all(AppntName).value:"+fm.AppntName.value)
          BnfGrid.setRowColData(index-1, 2, document.all("AppntName").value);
          BnfGrid.setRowColData(index-1, 3, document.all("AppntSex").value);

          BnfGrid.setRowColData(index-1, 4, document.all("AppntIDType").value);
          BnfGrid.setRowColData(index-1, 5, document.all("AppntIDNo").value);
          BnfGrid.setRowColData(index-1, 6,parent.VD.gVSwitch.getVar( "RelationToAppnt"));

          BnfGrid.setRowColData(index-1, 9, document.all("AppntHomeAddress").value);
          //hl
          BnfGrid.setRowColData(index-1, 13, document.all("AppntNo").value);
          //alert("toubaoren:"+document.all("AppntNo").value)

	  	  }
	  	  else
	  	  {
	  	    alert("Ͷ����Ϊ���壬������Ϊ�����ˣ�")
          var index = BnfGrid.mulLineCount;
          BnfGrid.setRowColData(index-1, 2, "");
          BnfGrid.setRowColData(index-1, 3, "");
          BnfGrid.setRowColData(index-1, 4, "");
          BnfGrid.setRowColData(index-1, 5, "");
          BnfGrid.setRowColData(index-1, 8, "");
	  	  }
	  	}
	  	else if (Field.value == "I") {
	  	  var index = BnfGrid.mulLineCount;
        BnfGrid.setRowColData(index-1, 2, document.all("Name").value);
        //BnfGrid.setRowColData(index-1, 3, document.all("IDType").value);
        BnfGrid.setRowColData(index-1, 3, document.all("Sex").value);
        BnfGrid.setRowColData(index-1, 4, document.all("IDType").value);
        BnfGrid.setRowColData(index-1, 5, document.all("IDNo").value);
        BnfGrid.setRowColData(index-1, 6, "00");
        //BnfGrid.setRowColData(index-1, 8, document.all("AppntHomeAddress").value);
        //hl
        BnfGrid.setRowColData(index-1, 9, document.all("HomeAddress").value);
     
        BnfGrid.setRowColData(index-1, 13, document.all("InsuredNo").value);
	//alert("4544564"+document.all("InsuredNo").value);
	  	}
	  	return;
    }
}
function returnparent(){
    parent.fraInterface.location ='CardTypeSelect.jsp';
}

/*********************************************************************
 *  ���漯��Ͷ�������ύ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function submitForm()
{
try{
        fm.PrtNo.disabled =false;
        fm.ManageCom.disabled =false;
        fm.AppntCustomerNo.disabled =false;
        fm.InsuredNo.disabled=false;
        }catch(ex){}
//if(checkInfo()==false) return false;
//if(verifyInput()==false) return false;
    //modify by zhangxing
   // if (verifyInputNB("1") == false) return false;
   // if (verifyInputNB("2") == false) return false;
   if(fm.PrtNo.value==null||fm.PrtNo.value==""){
       alert("��¼��ӡˢ�ţ�");
       fm.PrtNo.focus();
       return ;
   }
   if(fm.FirstTrailDate){
    if (fm.FirstTrailDate.value.length != 10 || fm.FirstTrailDate.value.substring(4, 5) != '-' || fm.FirstTrailDate.value.substring(7, 8) != '-' || fm.FirstTrailDate.value.substring(0, 1) == '0')
    {
        alert("��������ȷ�����ڸ�ʽ��");
        fm.FirstTrailDate.focus();
        return;
    }
    }
   if(fm.PolExpireDate){
    if (fm.PolExpireDate.value.length != 10 || fm.PolExpireDate.value.substring(4, 5) != '-' || fm.PolExpireDate.value.substring(7, 8) != '-' || fm.PolExpireDate.value.substring(0, 1) == '0')
    {
        alert("��������ȷ�����ڸ�ʽ��");
        fm.PolExpireDate.focus();
        return;
    }
    }
   if(fm.PolApplyDate){
    if (fm.PolApplyDate.value.length != 10 || fm.PolApplyDate.value.substring(4, 5) != '-' || fm.PolApplyDate.value.substring(7, 8) != '-' || fm.PolApplyDate.value.substring(0, 1) == '0')
    {
        alert("��������ȷ�����ڸ�ʽ��");
        fm.PolApplyDate.focus();
        return;
    }
    }
    //���ź�Ͷ�����Ų�������ͬ
	//if(trim(fm.ContCardNo.value)==trim(fm.ProposalContNo.value))
	//{
	//	alert("���ź�Ͷ�����Ų�������ͬ��\n ������Ŀ��ź�Ͷ��������ͬ����������");
	//	return false;
	//}
	if(fm.AppntIDType&&fm.AppntIDNo){
    if (fm.AppntIDType.value != "" && fm.AppntIDNo.value == "" || fm.AppntIDType.value == "" && fm.AppntIDNo.value != "") {
        alert("֤�����ͺ�֤���������ͬʱ��д����");
        return false;
    }
   }
   if(fm.Mult){
		if(trim(document.all('Mult').value)== "")
		{
			alert('��������Ϊ��!');
			return false;
		}
		if(!isNumeric(trim(document.all('Mult').value)))
		{
				alert('��������Ϊ����!');
				document.all('Mult').value = "";
				document.all('Mult').focus();
				return false;
		}
		if(parseFloat(trim(document.all('Mult').value)) == 0)
		{
			alert('��������Ϊ��!');
			document.all('Mult').value = "";
			document.all('Mult').focus();
			return false;
		}
  }
    //for (i = 0; i < BnfGrid.mulLineCount; i++)
    //{

        //��������˵�����ݶ������˳��û��¼����Ĭ��Ϊ1
    //    if (BnfGrid.getRowColData(i, 7) == null || BnfGrid.getRowColData(i, 7) == '')
    //    {
    //        BnfGrid.setRowColData(i, 7, "1");
    //    }
    //   if (BnfGrid.getRowColData(i, 6) == null || BnfGrid.getRowColData(i, 6) == '')
    //    {
    //        BnfGrid.setRowColData(i, 6, "1");
    //    }
    //}
    if(verifyInput()==false) return false;
    //���ֵ�һЩУ��
    if(checkInfo()==false) return false;
		//������������Ϣ�б����Ϣ��ֵ�����������ؼ���
   //setValueFromPolOtherGrid();

    //getdetailwork();
    //getdetailwork2();
lockScreen('lkscreen');  
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.fmAction.value = "INSERT";
    fm.submit();
    //�ύ
}
function submitForm1()
{
try{
        fm.PrtNo.disabled =false;
        fm.ManageCom.disabled =false;
        fm.AppntCustomerNo.disabled =false;
        fm.InsuredNo.disabled=false;
        }catch(ex){}
//if(checkInfo()==false) return false;
//if(verifyInput()==false) return false;
    //modify by zhangxing
   // if (verifyInputNB("1") == false) return false;
   // if (verifyInputNB("2") == false) return false;
   if(fm.ProposalContNo.value==null||fm.ProposalContNo.value==""){
      alert("���ȱ��棬�����޸ģ�");
      return false;
   }
   if(fm.PrtNo.value==null||fm.PrtNo.value==""){
       alert("��¼��ӡˢ�ţ�");
       fm.PrtNo.focus();
       return ;
   }
   if(fm.FirstTrailDate){
    if (fm.FirstTrailDate.value.length != 10 || fm.FirstTrailDate.value.substring(4, 5) != '-' || fm.FirstTrailDate.value.substring(7, 8) != '-' || fm.FirstTrailDate.value.substring(0, 1) == '0')
    {
        alert("��������ȷ�����ڸ�ʽ��");
        fm.FirstTrailDate.focus();
        return;
    }
    }
   if(fm.PolExpireDate){
    if (fm.PolExpireDate.value.length != 10 || fm.PolExpireDate.value.substring(4, 5) != '-' || fm.PolExpireDate.value.substring(7, 8) != '-' || fm.PolExpireDate.value.substring(0, 1) == '0')
    {
        alert("��������ȷ�����ڸ�ʽ��");
        fm.PolExpireDate.focus();
        return;
    }
    }
   if(fm.PolApplyDate){
    if (fm.PolApplyDate.value.length != 10 || fm.PolApplyDate.value.substring(4, 5) != '-' || fm.PolApplyDate.value.substring(7, 8) != '-' || fm.PolApplyDate.value.substring(0, 1) == '0')
    {
        alert("��������ȷ�����ڸ�ʽ��");
        fm.PolApplyDate.focus();
        return;
    }
    }
    //���ź�Ͷ�����Ų�������ͬ
	//if(trim(fm.ContCardNo.value)==trim(fm.ProposalContNo.value))
	//{
	//	alert("���ź�Ͷ�����Ų�������ͬ��\n ������Ŀ��ź�Ͷ��������ͬ����������");
	//	return false;
	//}
	if(fm.AppntIDType&&fm.AppntIDNo){
    if (fm.AppntIDType.value != "" && fm.AppntIDNo.value == "" || fm.AppntIDType.value == "" && fm.AppntIDNo.value != "") {
        alert("֤�����ͺ�֤���������ͬʱ��д����");
        return false;
    }
   }
   if(fm.Mult){
		if(trim(document.all('Mult').value)== "")
		{
			alert('��������Ϊ��!');
			return false;
		}
		if(!isNumeric(trim(document.all('Mult').value)))
		{
				alert('��������Ϊ����!');
				document.all('Mult').value = "";
				document.all('Mult').focus();
				return false;
		}
		if(parseFloat(trim(document.all('Mult').value)) == 0)
		{
			alert('��������Ϊ��!');
			document.all('Mult').value = "";
			document.all('Mult').focus();
			return false;
		}
  }
    //for (i = 0; i < BnfGrid.mulLineCount; i++)
    //{

        //��������˵�����ݶ������˳��û��¼����Ĭ��Ϊ1
    //    if (BnfGrid.getRowColData(i, 7) == null || BnfGrid.getRowColData(i, 7) == '')
    //    {
    //        BnfGrid.setRowColData(i, 7, "1");
    //    }
    //   if (BnfGrid.getRowColData(i, 6) == null || BnfGrid.getRowColData(i, 6) == '')
    //    {
    //        BnfGrid.setRowColData(i, 6, "1");
    //    }
    //}
    if(verifyInput()==false) return false;
    //���ֵ�һЩУ��
    if(checkInfo()==false) return false;

		//������������Ϣ�б����Ϣ��ֵ�����������ؼ���
   //setValueFromPolOtherGrid();

    //getdetailwork();
    //getdetailwork2();
lockScreen('lkscreen');  
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.fmAction.value = "UPDATE";
    fm.submit();
    //�ύ
}



function checkInfo(){
  //У�����������ʹ�����
  var tSaleChnl=fm.SaleChnl.value;
  //��������У�鲻ͬ������һЩ����
  var tCardType =fm.RiskCode.value;
  var tAgentCode =fm.AgentCode.value;
  //var tCheckSQL1 ="select count(1) from ldcode1 where code ='"+tSaleChnl+"' and code1=(select branchtype from laagent where agentcode ='"+tAgentCode+"') and codetype ='salechnlagentctrl'";
  var sqlid1 = 'CardContInputSql1';
	var mySql1 = new SqlClass();
	mySql1.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
	mySql1.addSubPara(tSaleChnl);// ָ������Ĳ���
	mySql1.addSubPara(tAgentCode);// ָ������Ĳ���
	mySql1.setSqlId(sqlid1);// ָ��ʹ�õ�Sql��id
	strsql = mySql1.getString();
  var tCheckValue =easyExecSql(strsql);
//  if(tCheckValue==0&&tCardType!="311603"){
  if(tCheckValue==0){
     alert("������������������ƥ�䣡");
     return false;
  }
  /**
   * ѧ���ƻ�
   * */
  if(tCardType!=null&&tCardType=="111603"){
	  if(check111603()==false){
		  return false;
	  }
  }
  /**
   * ������
   * */
  if(tCardType!=null&&tCardType=="141812"){
	  if(check141812()==false){
		  return false;
	  }
  }
  /**
   * �����ó�
   * */
  if(tCardType!=null&&tCardType=="311603"){
  	  if(check311603()==false){
  		  return false;
   	  }
  }
  return true;
}

/**
 * У��11603--ѧ���ƻ�
 * */
function check111603(){
	
	var tPolApplyDate = fm.PolApplyDate.value;
    var tFirstTrialDate =fm.FirstTrailDate.value;
//    var tCValiDate =fm.CValiDate.value;
//    var tDaySQL ="select  to_char(trunc(to_date('"+tCValiDate+"')-to_date('"+tPolApplyDate+"','yyyy-mm-dd')))   from   dual";
//    var tDays =easyExecSql(tDaySQL);
    //var tForCValiDateSql = "select  to_char(trunc_date(adddate(to_date('"+fm.PolApplyDate.value+"','yyyy-mm-dd'),7)),'yyyy-mm-dd')   from   dual";
    var sqlid2 = 'CardContInputSql2';
	var mySql2 = new SqlClass();
	mySql2.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
	mySql2.addSubPara(fm.PolApplyDate.value);// ָ������Ĳ���
	mySql2.setSqlId(sqlid2);// ָ��ʹ�õ�Sql��id
	var tForCValiDateSql = mySql2.getString();
    
    var tCValiDate = easyExecSql(tForCValiDateSql);
    fm.CValiDate.value = tCValiDate;
    var tFirstTrailDate= fm.FirstTrailDate.value;
    //var tNowDaySQL ="select  to_char(trunc_date(datediff(to_date(substr(now(),1,10),'yyyy-mm-dd'),to_date(substr('"+tCValiDate+"',1,10),'yyyy-mm-dd'))),'yyyy-mm-dd')   from   dual";
    var sqlid3 = 'CardContInputSql3';
	var mySql3 = new SqlClass();
	mySql3.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
	mySql3.addSubPara(tCValiDate);// ָ������Ĳ���
	mySql3.setSqlId(sqlid3);// ָ��ʹ�õ�Sql��id
	var tNowDaySQL = mySql3.getString();
    var tIntervel =easyExecSql(tNowDaySQL);
    if(tIntervel >0){
      alert("��Ч���ڲ�������¼�����ڣ�������ȷ��!");
     return false;
    }

	//�����������Ͷ�����˹�ϵ��У��
	var rBirthday =fm.Birthday.value;
	var tRelationToInsured =fm.RelationToInsured.value;
	var tAge = getAge(rBirthday);//alert(tAge);
	for(i=0;i<BnfGrid.mulLineCount;i++){
		var tBnfRelation =BnfGrid.getRowColData(i,6);
		var tBnfName =BnfGrid.getRowColData(i,2);
		if((tBnfName!=null&&tBnfName!="")||(tBnfRelation!=null&&tBnfRelation!="")){
			if(tBnfName==null||tBnfName==""||tBnfRelation==null||tBnfRelation==""){
				alert("��������Ϣ¼�벻����!");
				return false;
			}
		}
		if(fm.Name.value==tBnfName){
        	if(!confirm("��������˲���Ϊ�������˱��ˣ��Ƿ�ǿ�Ʊ���?")){
        		return false;
        	}
        }
		if(tAge<18&&tBnfRelation!="03"&&tBnfRelation!="04"&&tBnfRelation!="31"){
			alert("δ��������������˱���Ϊ�丸ĸ��������Ͷ����");
			return false;
		}
		//��������Ϣ
		if(tBnfRelation=="30"){
			alert("����������뱻�����˹�ϵ���󣬲���Ͷ��");
			return false;
		}
	}
	if(tAge<3||tAge>22){
	    alert("������Ͷ�����䲻����Ͷ������涨������Ͷ��");
	    return false;
	}
	if(tAge<18&&(tRelationToInsured!="03"&&tRelationToInsured!="04")){
	    alert("Ͷ�����뱻�����˹�ϵ������Ͷ������涨������Ͷ��");
	    return false;
	}
	if(tAge>=18&&tRelationToInsured=="30"){
	    alert("Ͷ�����뱻�����˹�ϵ������Ͷ������涨������Ͷ��");
	    return false;
	}
	if(tAge>=18){
//		//������������18����ʱ���ñ�������Ͷ����������6�ݣ�����ʾ�����������Ͷ�������޶�Ϊ6�ݣ�����Ͷ����
		var tAgentCode =fm.AgentCode.value;
		var tManageCom =fm.ManageCom.value;
	    var tInsuredName =fm.Name.value;
	    var tInsuredSex = fm.Sex.value;
	    var tInsuredBirthday =fm.Birthday.value;
//	    var tMultSQL =" select count(1) from lcpol where agentcode ='"+tAgentCode+"' and managecom ='"+tManageCom+"' and insuredname='"+tInsuredName+"'"
//	                 +" and insuredbirthday='"+tInsuredBirthday+"' and riskcode ='111603' and prtno !='"+fm.PrtNo.value+"'";
//	    var tMultSQL ="select" 
//					+"(select count(1) from lccontstate where statetype='Available' and state='0' and enddate is null and polno in("
//					+"select polno from lcpol a where a.agentcode = '"+tAgentCode+"'"
//					//+" and a.managecom = '"+tManageCom+"'"
//					+" and exists( select 1 from lcinsured b where a.prtno=b.prtno" 
//					+" and b.name='"+tInsuredName+"'"
//					+" and b.sex='"+tInsuredSex+"'"
//					+" and b.birthday='"+tInsuredBirthday+"')"
//					+" and a.riskcode = '111603'"
//					+" and a.prtno != '"+fm.PrtNo.value+"'"
//					+" and a.appflag !='0'"
//					+" and a.uwflag not in ('1', '2', 'a')"
//					+" )) + (select count(1) from lcpol d where d.agentcode='"+tAgentCode+"'"
//			    	//+" and d.managecom = '"+tManageCom+"'"
//					+" and exists( select 1 from lcinsured c where d.prtno=c.prtno" 
//					+" and c.name='"+tInsuredName+"'"
//					+" and c.sex='"+tInsuredSex+"'"
//					+" and c.birthday='"+tInsuredBirthday+"')"
//					+" and d.riskcode = '111603'"
//					+" and d.prtno != '"+fm.PrtNo.value+"'"
//					+" and d.appflag = '0'"
//					+" and d.uwflag not in ('1', '2', 'a')"
//					//+" and d.prem = 30"
//					+") from dual";
	    
	    var sqlid4 = 'CardContInputSql4';
		var mySql4 = new SqlClass();
		mySql4.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql4.addSubPara(tAgentCode);// ָ������Ĳ���
		mySql4.addSubPara(tInsuredName);// ָ������Ĳ���
		mySql4.addSubPara(tInsuredSex);// ָ������Ĳ���
		mySql4.addSubPara(tInsuredBirthday);// ָ������Ĳ���
		mySql4.addSubPara(fm.PrtNo.value);// ָ������Ĳ���
		mySql4.addSubPara(tAgentCode);// ָ������Ĳ���
		mySql4.addSubPara(tInsuredName);// ָ������Ĳ���
		mySql4.addSubPara(tInsuredSex);// ָ������Ĳ���
		mySql4.addSubPara(tInsuredBirthday);// ָ������Ĳ���
		mySql4.addSubPara(fm.PrtNo.value);// ָ������Ĳ���
		
		mySql4.setSqlId(sqlid4);// ָ��ʹ�õ�Sql��id
		var tMultSQL = mySql4.getString();
		var tCount =easyExecSql(tMultSQL);
	    if(tCount>=6){
	    	  alert("�˱������˱��������Ͷ�������޶�Ϊ6�ݣ�����Ͷ��!");
	    	  return false;
	    }
	}else{
	    //ѡ��30-�ƻ�һ�����������Ϊ8611(����)��8631(�Ϻ�)ʱ���ñ�������Ͷ����������6�ݣ�����ʾ���ñ������˵��ۼ���ʱ��ս���ܺͳ����涨�޶����Ͷ����
		var tAgentCode =fm.AgentCode.value;
	    var tManageCom =fm.ManageCom.value;
	    var tcheckManageCom ="";
	    if(tManageCom.length>=4){
	    	tcheckManageCom =tManageCom.substring(0,4);
	    }else{
	        tcheckManageCom =tManageCom+"00";
	    }
	    var tInsuredName =fm.Name.value;
	    var tInsuredBirthday =fm.Birthday.value;
	    var tInsuredSex = fm.Sex.value;
	    var tPrem = fm.Prem.value;
//	    var tMultSQL =" select count(1) from lcpol where agentcode ='"+tAgentCode+"' and managecom ='"+tManageCom+"' and insuredname='"+tInsuredName+"'"
//	                +" and insuredbirthday='"+tInsuredBirthday+"' and riskcode ='111603' and prtno !='"+fm.PrtNo.value+"' and appflag<>'4'"
//	                +" and uwflag not in ('1','2','a')";
/*	    var	tMultSQL ="select" 
	    			+"(select count(1) from lccontstate where statetype='Available' and state='0' and enddate is null and polno in("
	    			+"select polno from lcpol a where a.agentcode = '"+tAgentCode+"'"
	    			//+" and a.managecom = '"+tManageCom+"'"
	    			+" and exists( select 1 from lcinsured b where a.prtno=b.prtno" 
	    			+" and b.name='"+tInsuredName+"'"
	    			+" and b.sex='"+tInsuredSex+"'"
	    			+" and b.birthday='"+tInsuredBirthday+"')"
	    			+" and a.riskcode = '111603'"
	    			+" and a.prtno != '"+fm.PrtNo.value+"'"
	    			+" and a.appflag !='0'"
	    			+" and a.uwflag not in ('1', '2', 'a')";*/
	    
	    var sqlid5 = 'CardContInputSql5';
		var mySql5 = new SqlClass();
		mySql5.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql5.addSubPara(tAgentCode);// ָ������Ĳ���
		mySql5.addSubPara(tInsuredName);// ָ������Ĳ���
		mySql5.addSubPara(tInsuredSex);// ָ������Ĳ���
		mySql5.addSubPara(tInsuredBirthday);// ָ������Ĳ���
		mySql5.addSubPara(fm.PrtNo.value);// ָ������Ĳ���
		mySql5.setSqlId(sqlid5);// ָ��ʹ�õ�Sql��id
		var tMultSQL = mySql5.getString();
	    if(tPrem =="30"&&(tcheckManageCom=="8611"||tcheckManageCom =="8631")){
	    	tMultSQL =tMultSQL+" and a.prem =30 )) + (select count(1) from lcpol d where d.agentcode='"+tAgentCode+"'"
			    	//+" and d.managecom = '"+tManageCom+"'"
					+" and exists( select 1 from lcinsured c where d.prtno=c.prtno" 
					+" and c.name='"+tInsuredName+"'"
					+" and c.sex='"+tInsuredSex+"'"
					+" and c.birthday='"+tInsuredBirthday+"')"
					+" and d.riskcode = '111603'"
					+" and d.prtno != '"+fm.PrtNo.value+"'"
					+" and d.appflag = '0'"
					+" and d.uwflag not in ('1', '2', 'a')"
					+" and d.prem = 30"
	    			+") from dual";
	    	//prompt("",tMultSQL);
		    var tCount =easyExecSql(tMultSQL);
	        if(tCount>=6){
	        	alert("�ñ������˵��ۼ���ʱ��ս���ܺͳ����涨�޶����Ͷ��!");
	        	return false;
	        }
	    }
	    if(tPrem=="30"&&(tcheckManageCom!="8611"&&tcheckManageCom!="8631")){
	        tMultSQL =tMultSQL+" and a.prem =30)) + (select count(1) from lcpol d where d.agentcode='"+tAgentCode+"'"
			    	//+" and d.managecom = '"+tManageCom+"'"
					+" and exists( select 1 from lcinsured c where d.prtno=c.prtno" 
					+" and c.name='"+tInsuredName+"'"
					+" and c.sex='"+tInsuredSex+"'"
					+" and c.birthday='"+tInsuredBirthday+"')"
					+" and d.riskcode = '111603'"
					+" and d.prtno != '"+fm.PrtNo.value+"'"
					+" and d.appflag = '0'"
					+" and d.uwflag not in ('1', '2', 'a')"
					+" and d.prem=30"
	    			+") from dual";
	        //prompt("",tMultSQL);
		    var tCount =easyExecSql(tMultSQL);
	        if(tCount>=3){
	        	alert("�ñ������˵��ۼ���ʱ��ս���ܺͳ����涨�޶����Ͷ��!");
	        	return false;
	        }
	    }
	    if(tPrem=="50"&&(tcheckManageCom =="8611"||tcheckManageCom=="8631")){
	        tMultSQL =tMultSQL+" and a.prem =50)) + (select count(1) from lcpol d where d.agentcode='"+tAgentCode+"'"
			    	//+" and d.managecom = '"+tManageCom+"'"
					+" and exists( select 1 from lcinsured c where d.prtno=c.prtno" 
					+" and c.name='"+tInsuredName+"'"
					+" and c.sex='"+tInsuredSex+"'"
					+" and c.birthday='"+tInsuredBirthday+"')"
					+" and d.riskcode = '111603'"
					+" and d.prtno != '"+fm.PrtNo.value+"'"
					+" and d.appflag = '0'"
					+" and d.uwflag not in ('1', '2', 'a')"
					+" and d.prem=50"
	    			+") from dual";
	        //prompt("",tMultSQL);
		    var tCount =easyExecSql(tMultSQL);
	        if(tCount>=4){
	        	alert("�ñ������˵��ۼ���ʱ��ս���ܺͳ����涨�޶����Ͷ��!");
	        	return false;
	        }
	    }
	    if(tPrem=="50"&&(tcheckManageCom !="8611"&&tcheckManageCom !="8631")){
	        tMultSQL =tMultSQL+" and a.prem =50)) + (select count(1) from lcpol d where d.agentcode='"+tAgentCode+"'"
			    	//+" and d.managecom = '"+tManageCom+"'"
					+" and exists( select 1 from lcinsured c where d.prtno=c.prtno" 
					+" and c.name='"+tInsuredName+"'"
					+" and c.sex='"+tInsuredSex+"'"
					+" and c.birthday='"+tInsuredBirthday+"')"
					+" and d.riskcode = '111603'"
					+" and d.prtno != '"+fm.PrtNo.value+"'"
					+" and d.appflag = '0'"
					+" and d.uwflag not in ('1', '2', 'a')"
					+" and d.prem=50"
	    			+") from dual";
		    var tCount =easyExecSql(tMultSQL);
	        if(tCount>=2){
	        	alert("�ñ������˵��ۼ���ʱ��ս���ܺͳ����涨�޶����Ͷ��!");
	        	return false;
	        }
	    }
	}
	//һ��û�õ�У��
	var PolStartDate =fm.CValiDate.value;
	//09-08-26  ɾ����ֹ���ڲ�ɾ�����У��
//	var PolEndDate =fm.PolExpireDate.value;
//	var tDaySQL =" select  to_char(trunc(to_date('"+PolEndDate+"','yyyy-mm-dd')-to_date('"+PolStartDate+"','yyyy-mm-dd')))   from   dual";
//	tDays =easyExecSql(tDaySQL);
//	if(tDays!=364){
//		alert("����ֹ��Ӧ�ڱ�����Ч�պ�һ��!");
//	    return false;
//	}
	//ͬһ��У��
	var tRelationToInsured =fm.RelationToInsured.value;
	var tAppntName =fm.AppntName.value;
	var tInsuredName =fm.Name.value;
	if(tRelationToInsured=="00"){
		if(tInsuredName!=tAppntName){
			alert("Ͷ�����뱻���˹�ϵΪ00-���ˣ���Ͷ�����˵���������ͬ����ȷ�Ϻ󱣴棡");
	  	 	return false;
	  	}else{
	  	 	    //fm.SameFersonFlag.value="Y";
	  	}
	}
	//¼������tNow->ǩ������tS->��Ч����tP
	//var tCSSQL =" select  to_char(trunc(to_date('"+PolEndDate+"')-to_date('"+PolStartDate+"','yyyy-mm-dd')))   from   dual";
//	var SignDate = fm.SignDate.value;
	tP=new Date(PolStartDate.replace("-",",")).getTime(); 
	//tS=new Date(SignDate.replace("-",",")).getTime();
	tNow=new Date(CurrentDate.replace("-",",")).getTime();
//	if(tS>tNow) {
//		alert("¼������Ӧ������ǩ������.������ȷ��!");
//	    return false;
//	}   
//	if(tP<tS){
//		alert("ǩ������Ӧ��������Ч����.������ȷ��!");
//	    return false;
//	}
	//�������޵Ŀ�ʼ���ڲ�������ǩ�����ڡ���������ǩ������5�����ϣ��Ҳ�������¼�����ڡ�
	//��������¼������5�����ϣ�����ʱ��ʾ���������޵Ŀ�ʼ���ڲ�������ǩ�����ڡ���������ǩ������5�����ϣ�
	//�Ҳ�������¼�����ڡ���������¼������5������
	//alert("PolStartDate:"+PolStartDate+"||SignDate:"+SignDate);
//	var tDaySQL ="select  to_char(trunc(to_date('"+PolStartDate+"')-to_date('"+SignDate+"','yyyy-mm-dd')))   from   dual";
//	var tDays =easyExecSql(tDaySQL);
//	if(tDays>5){
//		alert("�������޵Ŀ�ʼ���ڲ�������ǩ������5������.������ȷ��!");
//	    return false;
//	}
	/*var tDaySQL1 ="select  to_char(trunc(datediff(to_date('"+PolStartDate+"','yyyy-mm-dd'),to_date(substr(now(),1,10),'yyyy-mm-dd'))))   from   dual";*/
	 var sqlid6 = 'CardContInputSql6';
		var mySql6 = new SqlClass();
		mySql6.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql6.addSubPara(PolStartDate);// ָ������Ĳ���
		mySql6.setSqlId(sqlid6);// ָ��ʹ�õ�Sql��id
		var tDaySQL1 = mySql6.getString();
	
	var ttDays =easyExecSql(tDaySQL1);
	/*var tDaySQL2 ="select  to_char(trunc(datediff(to_date(substr(now(),1,10),'yyyy-mm-dd'),to_date('"+PolStartDate+"','yyyy-mm-dd'))))   from   dual";*/
	var sqlid7 = 'CardContInputSql7';
	var mySql7 = new SqlClass();
	mySql7.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
	mySql7.addSubPara(PolStartDate);// ָ������Ĳ���
	mySql7.setSqlId(sqlid7);// ָ��ʹ�õ�Sql��id
	var tDaySQL2 = mySql7.getString();
	
	var ttDays2 =easyExecSql(tDaySQL2);
	if(ttDays>5||ttDays2>5){
		//alert("�������޵Ŀ�ʼ���ڲ�������¼������5������,��������¼������5������.������ȷ��!");
	    //return false;
	}
//	var tDaySQL3 ="select  to_char(trunc(to_date(substr(sysdate,0,10),'yyyy-mm-dd')-to_date('"+SignDate+"','yyyy-mm-dd')))   from   dual";
//	var ttDays3 =easyExecSql(tDaySQL3);
//	if(ttDays3<0){
//		alert("ǩ������Ӧ������¼������!");
//	    return false;
//	}
    var tF =new Date(tFirstTrailDate.replace("-",",")).getTime(); 
    //alert(tPA+"||"+tF);
    /*var tDaySQL11 ="select  to_char(trunc(datediff(to_date('"+tFirstTrailDate+"','yyyy-mm-dd'),to_date('"+tPolApplyDate+"','yyyy-mm-dd'))))   from   dual";*/
    var sqlid8 = 'CardContInputSql8';
	var mySql8 = new SqlClass();
	mySql8.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
	mySql8.addSubPara(tFirstTrailDate);// ָ������Ĳ���
	mySql8.addSubPara(tPolApplyDate);// ָ������Ĳ���
	mySql8.setSqlId(sqlid8);// ָ��ʹ�õ�Sql��id
	var tDaySQL11 = mySql8.getString();
    
    var tDays11=easyExecSql(tDaySQL11);
    if(tDays11<0||tDays11>7){
       alert("Ͷ�����ڲ������ڳ������ڣ��Ҳ������ڳ�������7�죬������ȷ�ϣ�");
       return false;
    }
    /*var tDaySQL12 ="select  to_char(trunc(datediff(to_date('"+tFirstTrailDate+"','yyyy-mm-dd'),to_date(substr(now(),1,10),'yyyy-mm-dd'))))   from   dual";*/
    var sqlid9 = 'CardContInputSql9';
	var mySql9 = new SqlClass();
	mySql9.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
	mySql9.addSubPara(tFirstTrailDate);// ָ������Ĳ���
	mySql9.setSqlId(sqlid9);// ָ��ʹ�õ�Sql��id
	var tDaySQL12 = mySql9.getString();
    
    var tDays12=easyExecSql(tDaySQL12);
    if(tDays12>0){
       alert("�������ڲ�������¼������(���Ե���¼������)��");
       return false;
    }
}

/**
 * У��141812--������
 * */
function check141812(){
	var tCount = BnfGrid.mulLineCount ;
    for(i=0;i<BnfGrid.mulLineCount;i++){
      var tBnfName =BnfGrid.getRowColData(i,2);
      var tBnfRelation =BnfGrid.getRowColData(i,6);
      if((tBnfName!=null&&tBnfName!="")||(tBnfRelation!=null&&tBnfRelation!="")){
         if(tBnfName==null||tBnfName==""||tBnfRelation==null||tBnfRelation==""){
         alert("��������Ϣ¼�벻����!");
         return false;
         }
      }
    }
    //����Ͷ������У��
    //�±��շ������� ��Ч����Ϊֻ������ϵͳ�Զ����  �ͻ�������һ���̶���-_-|||
    //var tForCValiDateSql = "select  to_char(trunc(to_date('"+fm.PolApplyDate.value+"')+7))   from   dual";
    var sqlid10 = 'CardContInputSql10';
	var mySql10 = new SqlClass();
	mySql10.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
	mySql10.addSubPara(fm.PolApplyDate.value);// ָ������Ĳ���
	mySql10.setSqlId(sqlid10);// ָ��ʹ�õ�Sql��id
	var tForCValiDateSql = mySql10.getString();
    
    var tCValiDate = easyExecSql(tForCValiDateSql);
    fm.CValiDate.value = tCValiDate;
   // var tNowDaySQL ="select  to_char(trunc(datediff(to_date(substr(now(),1,10),'yyyy-mm-dd'),to_date('"+tCValiDate+"','yyyy-mm-dd'))))   from   dual";
    
    var sqlid11 = 'CardContInputSql11';
	var mySql11 = new SqlClass();
	mySql11.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
	mySql11.addSubPara(tCValiDate);// ָ������Ĳ���
	mySql11.setSqlId(sqlid11);// ָ��ʹ�õ�Sql��id
	var tNowDaySQL = mySql11.getString();
    var tIntervel =easyExecSql(tNowDaySQL);
    if(tIntervel >0){
      alert("��Ч���ڲ�������¼�����ڣ�������ȷ��!");
      return false;
    }
    var tFirstTrailDate= fm.FirstTrailDate.value;
    var tPolApplyDate = fm.PolApplyDate.value;
    var tFirstTrialDate =fm.FirstTrailDate.value;
//    var tCValiDate =fm.CValiDate.value;
//    var tDaySQL ="select  to_char(trunc(to_date('"+tCValiDate+"')-to_date('"+tPolApplyDate+"','yyyy-mm-dd')))   from   dual";
//    var tDays =easyExecSql(tDaySQL);
    //var tNowDaySQL ="select  to_char(trunc(datediff(to_date(substr(now(),1,10),'yyyy-mm-dd'),to_date('"+tPolApplyDate+"','yyyy-mm-dd'))))   from   dual";
    
    var sqlid12 = 'CardContInputSql12';
	var mySql12 = new SqlClass();
	mySql12.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
	mySql12.addSubPara(tPolApplyDate);// ָ������Ĳ���
	mySql12.setSqlId(sqlid12);// ָ��ʹ�õ�Sql��id
	var tNowDaySQL = mySql12.getString();
    
    var tIntervel =easyExecSql(tNowDaySQL);
    if(tIntervel <0){
      alert("Ͷ������Ӧ������¼������!");
     return false;
    }
//    var tPA =new Date(tPolApplyDate.replace("-",",")).getTime(); 
//    var tNowDate =new Date(tPolApplyDate.replace("-",",")).getTime(); 
//    if(tDays!=5||tNowDate>tPA){
//       alert("Ͷ������ӦΪ��Ч���ڵ�ǰ5�죬�Ҳ�������¼������.������ȷ��!");
//       return false;
//    }
    //var tDaySQL1 ="select  to_char(trunc(to_date('"+tPolApplyDate+"')-to_date(substr(sysdate,0,10),'yyyy-mm-dd')))   from   dual";
    //var ttDays =easyExecSql(tDaySQL1);
    //if(ttDays<0){
    //  alert("Ͷ�����ڲ�������¼������.������ȷ��!");
    //  return false;
    //}
   // var tDaySQL1 ="select  to_char(trunc(datediff(to_date('"+tCValiDate+"','yyyy-mm-dd'),to_date(substr(now(),1,10),'yyyy-mm-dd'))))   from   dual";
    
    var sqlid13 = 'CardContInputSql13';
	var mySql13 = new SqlClass();
	mySql13.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
	mySql13.addSubPara(tCValiDate);// ָ������Ĳ���
	mySql13.setSqlId(sqlid13);// ָ��ʹ�õ�Sql��id
	var tDaySQL1 = mySql13.getString();
    
    var ttDays =easyExecSql(tDaySQL1);
    //var tFirstTrailSQL="select  to_char(trunc(to_date('"+tFirstTrialDate+"')-to_date('"+tPolApplyDate+"','yyyy-mm-dd')))   from   dual";
    //var tFirstTrailDays =easyExecSql(tFirstTrailSQL);
    var tF =new Date(tFirstTrailDate.replace("-",",")).getTime(); 
    //alert(tPA+"||"+tF);
   // var tDaySQL11 ="select  to_char(trunc(datediff(to_date('"+tFirstTrailDate+"','yyyy-mm-dd'),to_date('"+tPolApplyDate+"','yyyy-mm-dd'))))   from   dual";
    
    var sqlid14 = 'CardContInputSql14';
	var mySql14 = new SqlClass();
	mySql14.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
	mySql14.addSubPara(tFirstTrailDate);// ָ������Ĳ���
	mySql14.addSubPara(tPolApplyDate);// ָ������Ĳ���
	mySql14.setSqlId(sqlid14);// ָ��ʹ�õ�Sql��id
	var tDaySQL11 = mySql14.getString();
    
    var tDays11=easyExecSql(tDaySQL11);
    if(tDays11<0||tDays11>7){
       alert("Ͷ�����ڲ������ڳ������ڣ��Ҳ������ڳ�������7�죬������ȷ�ϣ�");
       return false;
    }
    //var tDaySQL12 ="select  to_char(trunc(datediff(to_date('"+tFirstTrailDate+"','yyyy-mm-dd'),to_date(substr(now(),1,10),'yyyy-mm-dd'))))   from   dual";
    var sqlid15 = 'CardContInputSql15';
	var mySql15 = new SqlClass();
	mySql15.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
	mySql15.addSubPara(tFirstTrailDate);// ָ������Ĳ���
	mySql15.setSqlId(sqlid15);// ָ��ʹ�õ�Sql��id
	var tDaySQL12 = mySql15.getString();
    
    var tDays12=easyExecSql(tDaySQL12);
    if(tDays12>0){
       alert("�������ڲ�������¼������(���Ե���¼������)��");
       return false;
    }
    //ְҵ���
    var WorkType=fm.OccupationType.value;
    if(WorkType>4){
      alert("��������ְҵ������Ͷ������涨��������ȷ��(ְҵ���Ϊ5�༰5������)��");
      return false;
    }
    //������δ��18�� �����˱���Ϊ��ĸ 31
    var tCount = BnfGrid.mulLineCount ;
    var rBirthday =fm.Birthday.value;
 	 var tRelationToInsured =fm.RelationToInsured.value;
 	 var tAge = getAge(rBirthday);
 	 if(tAge<3||tAge>60){
 	   alert("������Ͷ�����䲻����Ͷ������涨������Ͷ��!");
 	   return false;
 	 }
 	 if(tAge>=18&&tRelationToInsured=="30"){
 	   alert("Ͷ�����뱻�����˹�ϵ������Ͷ������涨������Ͷ��!");
 	   return false;
 	 }
 	 //�类�����˲���18���꣬Ͷ�����뱻���˹�ϵ����ѡ��Ϊ����ĸ��
 	 if(tAge<18&&tRelationToInsured!="31"){
 	 	  alert("Ͷ�����뱻�����˹�ϵ������Ͷ������涨������Ͷ��");
         return false;
 	 }
    for(i=0;i<BnfGrid.mulLineCount;i++){
    	var tBnfRelation =BnfGrid.getRowColData(i,6);
    	var tBnfName = BnfGrid.getRowColData(i,2);
    	if(tAge<18&&tBnfRelation!="31"){
    		alert("δ��������������˱���Ϊ�丸ĸ��������Ͷ����");
    		return false;
        }
        //��������Ϣ
        if(tBnfRelation=="30"){
        	alert("����������뱻�����˹�ϵ���󣬲���Ͷ��!");
        	return false;
        }
        if(fm.Name.value==tBnfName){
        	if(!confirm("��������˲���Ϊ�������˱��ˣ��Ƿ�ǿ�Ʊ���?")){
        		return false;
        	}
        }
    }
      
      //ͬһ��У��
 	 	var tRelationToInsured =fm.RelationToInsured.value;
 	 	var tAppntName =fm.AppntName.value;
 	 	var tInsuredName =fm.Name.value;
 	 	if(tRelationToInsured=="00"){
 	 	 if(tInsuredName!=tAppntName){
 	 	    alert("Ͷ�����뱻���˹�ϵΪ00-���ˣ���Ͷ�����˵���������ͬ����ȷ�Ϻ󱣴棡");
 	 	   return false;
 	 	  }else{
 	 	    //fm.SameFersonFlag.value="Y";
 	 	  }
 	 	}
      //���֤У��
      if(checkidtype()==false) return false;
      
      //09-08-24 �±��շ�������� ��ϵ�绰����Ϊ��
      if(fm.AppntPhone.value==""||fm.AppntPhone.value==null){
   	   alert("��ϵ�绰����Ϊ�գ�");
   	   return false;
      }
      //09-08-28 �±��շ�����
      if(fm.ManageCom.value==""||fm.ManageCom.value==null){
   	   alert("�����������Ϊ�գ�");
   	   return false;
      }
}
/**
 * У��311603--�����ó�
 * */
function check311603(){
	//���Ӽ��㱣����Ч����
	 //var tForCValiDateSql = "select  to_char(trunc(adddate(to_date('"+fm.PolApplyDate.value+"','yyyy-mm-dd'),7)))   from   dual";
	 var sqlid16 = 'CardContInputSql16';
		var mySql16 = new SqlClass();
		mySql16.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql16.addSubPara(fm.PolApplyDate.value);// ָ������Ĳ���
		mySql16.setSqlId(sqlid16);// ָ��ʹ�õ�Sql��id
		var tForCValiDateSql = mySql16.getString();
	 
	 var tCValiDate = easyExecSql(tForCValiDateSql);
	 fm.CValiDate.value = tCValiDate;
//	var tAge =fm.InsuredAppAge.value;
	var tAge = getAge(fm.Birthday.value);
	fm.InsuredAppAge.value=tAge;
	if(fm.SaleChnl.value=="03"&&(fm.AgentCom.value==null||fm.AgentCom.value=="")){
		alert("��������Ϊ��03-���д����򡰴������������¼�룡");
	    return false;
	}
    var tCount = BnfGrid.mulLineCount ;
 	for(i=0;i<BnfGrid.mulLineCount;i++){
 		var tBnfName =BnfGrid.getRowColData(i,2);
    	var tBnfRelation =BnfGrid.getRowColData(i,6);
    	if((tBnfName!=null&&tBnfName!="")||(tBnfRelation!=null&&tBnfRelation!="")){
    		if(tBnfName==null||tBnfName==""||tBnfRelation==null||tBnfRelation==""){
    			alert("��������Ϣ¼�벻����!");
    			return false;
    		}
    	}
    	if(tBnfRelation=="30"){
    		alert("����������뱻�����˹�ϵ���󣬲���Ͷ��!");
    		return false;
    	}
    	if(fm.Name.value==tBnfName){
        	if(!confirm("��������˲���Ϊ�������˱��ˣ��Ƿ�ǿ�Ʊ���?")){
        		return false;
        	}
        }
//    	if(tAge<18&&tBnfRelation!="03"&&tBnfRelation!="04"){
//    		alert("δ��������������˱���Ϊ�丸ĸ��������Ͷ����");
//    		return false;
//    	}
 	}
    //Ͷ�����ڲ�������¼�����ڡ���������¼������5������
 	var tFirstTrailDate = fm.FirstTrailDate.value;
 	var tPolApplyDate = fm.PolApplyDate.value;
    var tF =new Date(tFirstTrailDate.replace("-",",")).getTime(); 
    //alert(tPA+"||"+tF);
    //var tDaySQL11 ="select  to_char(trunc(datediffto_date('"+tFirstTrailDate+"','yyyy-mm-dd'),to_date('"+tPolApplyDate+"','yyyy-mm-dd'))))   from   dual";
    var sqlid17 = 'CardContInputSql17';
	var mySql17 = new SqlClass();
	mySql17.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
	mySql17.addSubPara(tFirstTrailDate);// ָ������Ĳ���
	mySql17.addSubPara(tPolApplyDate);
	mySql17.setSqlId(sqlid17);// ָ��ʹ�õ�Sql��id
	var tDaySQL11 = mySql17.getString();
    
    var tDays11=easyExecSql(tDaySQL11);
    if(tDays11<0||tDays11>7){
       alert("Ͷ�����ڲ������ڳ������ڣ��Ҳ������ڳ�������7�죬������ȷ�ϣ�");
       return false;
    }
    //var tDaySQL12 ="select  to_char(trunc(datediff(to_date('"+tFirstTrailDate+"','yyyy-mm-dd'),to_date(substr(now(),1,10),'yyyy-mm-dd'))))   from   dual";
    
    var sqlid18 = 'CardContInputSql18';
	var mySql18 = new SqlClass();
	mySql18.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
	mySql18.addSubPara(tFirstTrailDate);// ָ������Ĳ���
	mySql18.setSqlId(sqlid18);// ָ��ʹ�õ�Sql��id
	var tDaySQL12 = mySql18.getString();
    var tDays12=easyExecSql(tDaySQL12);
    if(tDays12>0){
       alert("�������ڲ�������¼������(���Ե���¼������)��");
       return false;
    }
    //ͬһ��������ֻ�ܹ���һ�ݣ���ҵ��Ա�����������������������������������Ա����䡢֤�������һ�£�
    //��Ĭ��Ϊͬһ�ˣ�����ʱϵͳУ�������ֻ������Ч����Ϊһ��
    var tAgentCode =fm.AgentCode.value;
    var tAgentCom =fm.AgentCom.value;
    var tManageCom =fm.ManageCom.value;
    var tInsuredName =fm.Name.value;
    var tInsuredSex=fm.Sex.value;
    var tIDNo =fm.IDNo.value;
    var tInsuredAge =fm.InsuredAppAge.value;
    var tMult =fm.Mult.value;
//    var tCountSQL =" select count(1) from lcpol a where agentcode ='"+tAgentCode+"'  and managecom='"+tManageCom+"' "
//                  +" and insuredname ='"+tInsuredName+"' and insuredsex='"+tInsuredSex+"' and insuredappage='"+tInsuredAge+"' "
//                  +" and exists (select 1 from lcinsured where idno ='"+tIDNo+"' and prtno =a.prtno)";
//    if(tAgentCom==null||tAgentCom ==""){
//		tCountSQL =tCountSQL+" and agentcom is null ";         
//    }
//    var tCountMult =easyExecSql(tCountSQL);
//    if(tCountMult>=1||tMult>1){
//    	alert("ͬһ��������ֻ�ܹ���һ��!");
//    	return false;
//    }
    //���֤У��
    //if(checkidtype()==false) return false;
    //var rBirthday =fm.Birthday.value;
	var tRelationToInsured =fm.RelationToInsured.value;
	//var tAge = getAge(rBirthday);
	if(tAge<18||tAge>70){
		alert("������Ͷ�����䲻����Ͷ������涨������Ͷ��!");
	   	return false;
	}
	//�类�����˲���18���꣬�������ѡ��Ϊ�����ס�ĸ�ס�  
	//2009-08-27�˴��޸�Ϊ  31-��ĸ
	//���ն��µĿ������� ȥ�������У��
//	if(tAge<18&&tRelationToInsured!="31"){
//		alert("Ͷ�����뱻�����˹�ϵ������Ͷ������涨������Ͷ��!");
//	 	return false;
//	}
	if(tAge>=18&&tRelationToInsured=="30"){
		alert("Ͷ�����뱻�����˹�ϵ������Ͷ������涨������Ͷ��");
	 	return false;
	}
	//ͬһ��У��
	var tRelationToInsured =fm.RelationToInsured.value;
	var tAppntName =fm.AppntName.value;
	var tInsuredName =fm.Name.value;
	if(tRelationToInsured=="00"){
		if(tInsuredName!=tAppntName){
			alert("Ͷ�����뱻���˹�ϵΪ00-���ˣ���Ͷ�����˵���������ͬ����ȷ�Ϻ󱣴棡");
	 	   	return false;
		}else{
			//fm.SameFersonFlag.value="Y";
	 	    //alert("fm.SameFersonFlag.value:"+fm.SameFersonFlag.value);
	 	}
	}
	//У�����֤���룬���ܹ�ǿ�б���
	if(checkidtype()==false) return false;
}

function InitBnfType(){
   var tCount =BnfGrid.mulLineCount()-1;
   //alert(tCount);
   BnfGrid.setRowColData(tCount,1,"1");
}

//311603 ͨ��������㱻��������
function calBirthday(){
   var tAge =fm.InsuredAppAge.value;
   //var tSQL="select year(now())-concat(concat('"+tAge+"','-'),substr(now(),6,6)) from dual";
   
    var sqlid19 = 'CardContInputSql19';
    var mySql19 = new SqlClass();
	mySql19.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
	mySql19.addSubPara(tAge);// ָ������Ĳ���
	mySql19.setSqlId(sqlid19);// ָ��ʹ�õ�Sql��id
	var tSQL = mySql19.getString();
   fm.Birthday.value =easyExecSql(tSQL);
   //alert(fm.Birthday.value);
}

//�������������У��
function confirmSecondInput1(aObject,aEvent)
{
	{
		if(theFirstValue!="")
		{
			theSecondValue = aObject.value;
			if(theSecondValue=="")
			{
				alert("���ٴ�¼�룡");
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
				alert("����¼����������������¼�룡");
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
			return;
		}
	}
}
function checkidtype()
{
	if(fm.IDType.value=="")
	{
		//alert("����ѡ��֤�����ͣ�");
		//fm.IDNo.value="";
    }
//   alert("+++++++++"+fm.Name.value+"++++++++");
if(fm.Name.value=="")
{
	return false;
	}
  var iIdNo = fm.IDNo.value;
    if(fm.IDType.value=="0")
	{
		if ((iIdNo.length!=15) && (iIdNo.length!=18))
        {
//            strReturn = "֤������λ����������������!";
//            alert(strReturn);
            if(!confirm("֤������λ������, �Ƿ�ǿ�Ʊ��棿")){
            	fm.IDNo.focus();
            	fm.IDNo.select();
            	return false;
            }
        } 
        if(getBirthdaySexByIDNo(iIdNo)==false) return false;
        //getAge();
        //getallinfo(); 	     
	}
    
    return true;
}

/*********************************************************************
 *  �������֤��ȡ�ó������ں��Ա�
 *  ����  ��  ���֤��
 *  ����ֵ��  ��
 *********************************************************************
 */

function getBirthdaySexByIDNo(iIdNo)
{
	if(document.all('IDType').value=="0")
	{ 
		document.all('CalBirthday').value=getBirthdatByIdNo(iIdNo);
		document.all('CalSex').value=getSexByIDNo(iIdNo);
//		alert("���㣺CalBirthday-"+fm.CalBirthday.value+";CalSex-"+fm.CalSex.value);
//		alert("¼�룺Birthday-"+fm.Birthday.value+";Sex-"+fm.Sex.value);
		var tCardType =fm.RiskCode.value;
//		if(tCardType!=null&&tCardType=="311603"){
		  //ֻУ���Ա�
			if(document.all('CalSex').value!=fm.Sex.value){
			   if(!confirm("���֤�������Ա�һ��! �Ƿ�ǿ�Ʊ��棿")){
			      return false ;
			   }
			}
//		}else{
			if(document.all('CalBirthday').value!=fm.Birthday.value||document.all('CalSex').value!=fm.Sex.value){
		 	  if(!confirm("���֤�������Ա��������ڲ�һ��! �Ƿ�ǿ�Ʊ��棿")){
			      return false ;
			   }
			}
//		}
		
		//if(document.all('Sex').value=="0")
		//  document.all('SexName').value="��";
		//else
		//	{
		//	 document.all('SexName').value="Ů";
		//	}
	}
	return true;
}



function getAge(tBirthday)
{
    var BirthDay=tBirthday;
    var Age ="";
	if(tBirthday=="")
	{
		return;
	}
	if(tBirthday.indexOf('-')==-1)
	{
		var Year =tBirthday.substring(0,4);
		var Month =tBirthday.substring(4,6);
		var Day =tBirthday.substring(6,8);
		BirthDay = Year+"-"+Month+"-"+Day;
	}
	if(calAge(BirthDay)<0)
	{
		alert("��������ֻ��Ϊ��ǰ������ǰ");
		//fm.AppntAge.value="";
		return;
	}
	if(!fm.PolApplyDate||(fm.PolApplyDate.value==null||fm.PolApplyDate.value=="")){
	  Age=calPolAppntAge(BirthDay,fm.CValiDate.value);
	}else{
	  Age=calPolAppntAge(BirthDay,fm.PolApplyDate.value);
	}
//	fm.AppntAge.value=calAge(fm.AppntBirthday.value);
    //tPolApplyDate="2009-03-27";
	//Age=calPolAppntAge(BirthDay,fm.PolApplyDate.value);
  	return Age;
}

/**
* ����Ͷ���˱��������䡶Ͷ������������֮��=Ͷ���˱��������䡷,2005-11-18�����
* ��������������yy��mm��dd��Ͷ������yy��mm��dd
* ����  ����
*/
function calPolAppntAge(BirthDate,PolAppntDate)
{
	var age ="";
	if(BirthDate=="" || PolAppntDate=="")
	{
		age ="";
		return age;
	}
	var arrBirthDate = BirthDate.split("-");
	if (arrBirthDate[1].length == 1) arrBirthDate[1] = "0" + arrBirthDate[1];
	if (arrBirthDate[2].length == 1) arrBirthDate[2] = "0" + arrBirthDate[2];
//	alert("����"+arrBirthDate);	
	var arrPolAppntDate = PolAppntDate.split("-");
	if (arrPolAppntDate[1].length == 1) arrPolAppntDate[1] = "0" + arrBirthDate[1];
	if (arrPolAppntDate[2].length == 1) arrPolAppntDate[2] = "0" + arrBirthDate[2];
//	alert("Ͷ������"+arrPolAppntDate);
	if(arrPolAppntDate[0]<=99)
	{
		arrBirthDate[0]=	arrBirthDate[0]-1900;
	}
	age = arrPolAppntDate[0] - arrBirthDate[0] - 1;
	//��ǰ�´��ڳ�����
	//alert(arrPolAppntDate[1] + " | " + arrBirthDate[1] + " | " + (arrPolAppntDate[1] > arrBirthDate[1]));
	if (arrPolAppntDate[1] > arrBirthDate[1]) 
	{
		age = age + 1;
		return age;
	}
	//��ǰ��С�ڳ�����
	else if (arrPolAppntDate[1] < arrBirthDate[1]) 
	{
		return age;
	}
  	//��ǰ�µ��ڳ����µ�ʱ�򣬿�������
	else if (arrPolAppntDate[2] >= arrBirthDate[2])
	{
		age = age + 1;
		return age;
  	}
	else
	{
		return age;
	}
}
function queryAgent()
{
	//add by wangyc
	//reutrn;
	
	//alert("document.all('AgentCode').value==="+document.all('AgentCode').value);
	if(document.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��");
		 return;
	}
  if(document.all('AgentCode').value == "")	{
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
  }
	if(document.all('AgentCode').value != ""&& document.all('AgentCode').value.length==10){
	  var cAgentCode = fm.AgentCode.value;  //��������
    //alert("cAgentCode=="+cAgentCode);
    //���ҵ��Ա���볤��Ϊ8���Զ���ѯ����Ӧ�Ĵ������ֵ���Ϣ
    //alert("cAgentCode=="+cAgentCode);
	  //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
/*   	var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
	         + "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and a.AgentState < '03'  and a.AgentCode='"+cAgentCode+"' and a.managecom like '"+document.all("ManageCom").value+"%'";*/
   //alert(strSQL);
   	
    var sqlid20 = 'CardContInputSql20';
    var mySql20 = new SqlClass();
	mySql20.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
	mySql20.addSubPara(cAgentCode);// ָ������Ĳ���
	mySql20.addSubPara(document.all("ManageCom").value);// ָ������Ĳ���
	mySql20.setSqlId(sqlid20);// ָ��ʹ�õ�Sql��id
	var strSQL = mySql20.getString();
    var arrResult = easyExecSql(strSQL); 
    //alert(arrResult);
    if (arrResult != null) {
    	fm.AgentCode.value = arrResult[0][0];
    	fm.BranchAttr.value = arrResult[0][8];
    	fm.AgentGroup.value = arrResult[0][1];
  	  fm.AgentName.value = arrResult[0][3];
      //fm.AgentManageCom.value = arrResult[0][2];
      //alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][3]+"]");
    }
    else
    {
      fm.AgentCode.value ="";
    	fm.BranchAttr.value ="";
    	fm.AgentGroup.value ="";
  	  fm.AgentName.value ="";
     alert("����Ϊ:["+cAgentCode+"]��ҵ��Ա�����ڣ����߲��ڸù�������£������Ѿ���ְ�� ��ȷ��!");
    }
	}
}
//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery2(arrResult)
{
	if(arrResult!=null)
	{
	fm.AgentCode.value = arrResult[0][0];
	fm.BranchAttr.value = arrResult[0][10];
	fm.AgentGroup.value = arrResult[0][1];
	//alert(12);
	fm.AgentName.value = arrResult[0][3];
	//fm.AgentManageCom.value = arrResult[0][2];
	fm.ManageCom.value=arrResult[0][2];
	//showOneCodeName('comcode','ManageCom','ManageComName');
	//showOneCodeName('comcode','AgentManageCom','AgentManageComName');
	}
}

//Ͷ���˿ͻ��Ų�ѯ��Ť�¼�
function queryAppntNo() 
{
	if (document.all("AppntNo").value == "" )
	{
		showAppnt1();
	}
	else if (loadFlag != "1" && loadFlag != "2")
	{
		alert("ֻ����Ͷ����¼��ʱ���в�����");
	}
	else
	{
		
		var sqlid21 = 'CardContInputSql21';
	    var mySql21 = new SqlClass();
		mySql21.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql21.addSubPara(document.all("AppntNo").value);// ָ������Ĳ���
		mySql21.setSqlId(sqlid21);// ָ��ʹ�õ�Sql��id
		 //"select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("AppntNo").value + "'", 1, 0
		arrResult = easyExecSql(mySql21.getString());
		if (arrResult == null) 
		{
			alert("δ�鵽Ͷ������Ϣ");
			displayAppnt(new Array());
			emptyUndefined();
		}
		else
		{
			displayAppnt(arrResult[0]);
			showAppntCodeName();
			getdetailaddress();
		}
	}
}

/*********************************************************************
 *  Ͷ���˿ͻ��Ų�ѯ��Ť�¼�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryInsuredNo()
{
    if (document.all("InsuredNo").value == "")
    {
      //showAppnt1();
      showInsured1();
    }
    else
    {
    	
    	var sqlid22 = 'CardContInputSql22';
	    var mySql22 = new SqlClass();
		mySql22.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql22.addSubPara(document.all("InsuredNo").value);// ָ������Ĳ���
		mySql22.setSqlId(sqlid22);// ָ��ʹ�õ�Sql��id
		//"select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("InsuredNo").value + "'", 1, 0
    	arrResult = easyExecSql(mySql22.getString());
        if (arrResult == null)
        {
          alert("δ�鵽��������Ϣ");
          displayInsuredInfo(new Array());
          emptyUndefined();
        }
        else
        {
          //displayAppnt(arrResult[0]);
          displayInsuredInfo(arrResult[0]);
          showInsuredCodeName();
        }
        
    }
}

function noneedhome()
{

    var insuredno="";
    if(InsuredGrid.mulLineCount>=1)
    {
        for(var personcount=0;personcount<=InsuredGrid.mulLineCount;personcount++)
        {
        	if(InsuredGrid.getRowColData(personcount,5)=="00")
        	{
        		insuredno=InsuredGrid.getRowColData(personcount,1);

        		break;
        	}
        }
      // var strhomea="select HomeAddress,HomeZipCode,HomePhone from lcaddress where customerno='"+insuredno+"' and addressno=(select addressno from lcinsured where contno='"+fm.ContNo.value+"' and insuredno='"+insuredno+"')";
       
       var sqlid23 = 'CardContInputSql23';
	   var mySql23 = new SqlClass();
	   mySql23.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
	   mySql23.addSubPara(insuredno);// ָ������Ĳ���
	   mySql23.addSubPara(fm.ContNo.value);// ָ������Ĳ���
	   mySql23.addSubPara(insuredno);// ָ������Ĳ���
	   mySql23.setSqlId(sqlid23);// ָ��ʹ�õ�Sql��id
	   var strhomea = mySql23.getString();
       arrResult=easyExecSql(strhomea,1,0);
       try{document.all('HomeAddress').value= arrResult[0][0];}catch(ex){};

       try{document.all('HomeZipCode').value= arrResult[0][1];}catch(ex){};

       try{document.all('HomePhone').value= arrResult[0][2];}catch(ex){};

       fm.InsuredAddressNo.value = "";
       fm.InsuredNo.value = "";
    }
}

function displayInsuredInfo()
{
	  //alert("asdfasfsf");
    try{document.all('InsuredNo').value= arrResult[0][0]; }catch(ex){};
    try{document.all('Name').value= arrResult[0][1]; }catch(ex){};
    try{document.all('Sex').value= arrResult[0][2]; }catch(ex){};
    try{document.all('Birthday').value= arrResult[0][3]; }catch(ex){};
    try{document.all('IDType').value= arrResult[0][4]; }catch(ex){};
    try{document.all('IDNo').value= arrResult[0][5]; }catch(ex){};
    try{document.all('Password').value= arrResult[0][6]; }catch(ex){};
    try{document.all('NativePlace').value= arrResult[0][7]; }catch(ex){};
    try{document.all('Nationality').value= arrResult[0][8]; }catch(ex){};
    //try{document.all('InsuredPlace').value= arrResult[0][9]; }catch(ex){};
    try{document.all('RgtAddress').value= arrResult[0][9]; }catch(ex){};
    try{document.all('Marriage').value= arrResult[0][10];}catch(ex){};
    try{document.all('MarriageDate').value= arrResult[0][11];}catch(ex){};
    try{document.all('Health').value= arrResult[0][12];}catch(ex){};
   /* if(arrResult[0][13]!=null&&arrResult[0][13]!=0){
      try{document.all('Stature').value= arrResult[0][13];}catch(ex){};
    }
    if(arrResult[0][14]!=null&&arrResult[0][14]!=0){
      try{document.all('Avoirdupois').value= arrResult[0][14];}catch(ex){};
    }*/
    try{document.all('Degree').value= arrResult[0][15];}catch(ex){};
    try{document.all('CreditGrade').value= arrResult[0][16];}catch(ex){};
    try{document.all('OthIDType').value= arrResult[0][17];}catch(ex){};
    try{document.all('OthIDNo').value= arrResult[0][18];}catch(ex){};
    try{document.all('ICNo').value= arrResult[0][19];}catch(ex){};
    try{document.all('GrpNo').value= arrResult[0][20];}catch(ex){};
    try{document.all('JoinCompanyDate').value= arrResult[0][21];}catch(ex){};
    try{document.all('StartWorkDate').value= arrResult[0][22];}catch(ex){};
    try{document.all('Position').value= arrResult[0][23];}catch(ex){};
    try{document.all('Salary').value= arrResult[0][24];}catch(ex){};
    try{document.all('OccupationType').value= arrResult[0][25];}catch(ex){};
    try{document.all('OccupationCode').value= arrResult[0][26];}catch(ex){};
    try{document.all('WorkType').value= arrResult[0][27];}catch(ex){};
    try{document.all('PluralityType').value= arrResult[0][28];}catch(ex){};
    try{document.all('DeathDate').value= arrResult[0][29];}catch(ex){};
    try{document.all('SmokeFlag').value= arrResult[0][30];}catch(ex){};
    try{document.all('BlacklistFlag').value= arrResult[0][31];}catch(ex){};
    try{document.all('Proterty').value= arrResult[0][32];}catch(ex){};
    //try{document.all('Remark').value= arrResult[0][33];}catch(ex){};
    try{document.all('State').value= arrResult[0][34];}catch(ex){};
    try{document.all('VIPValue1').value= arrResult[0][35];}catch(ex){};
    try{document.all('Operator').value= arrResult[0][36];}catch(ex){};
    try{document.all('MakeDate').value= arrResult[0][37];}catch(ex){};
    try{document.all('MakeTime').value= arrResult[0][38];}catch(ex){};
    try{document.all('ModifyDate').value= arrResult[0][39];}catch(ex){};
    try{document.all('ModifyTime').value= arrResult[0][40];}catch(ex){};
    try{document.all('LicenseType').value= arrResult[0][43];}catch(ex){};
    try{document.all('GrpName').value= arrResult[0][41];}catch(ex){};



    //��ַ��ʾ���ֵı䶯
    try{document.all('InsuredAddressNo').value= "";}catch(ex){};
    try{document.all('PostalAddress').value=  "";}catch(ex){};
    try{document.all('ZipCode').value=  "";}catch(ex){};
    try{document.all('Phone').value=  "";}catch(ex){};
    try{document.all('Mobile').value=  "";}catch(ex){};
    try{document.all('EMail').value=  "";}catch(ex){};
    //try{document.all('GrpName').value= arrResult[0][46];}catch(ex){};
    try{document.all('Phone').value=  "";}catch(ex){};
    try{document.all('GrpAddress').value=  "";}catch(ex){};
    try{document.all('GrpZipCode').value=  "";}catch(ex){};
}

/*********************************************************************
 *  �����˴���򺺻�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showInsuredCodeName()
{
try{
	quickGetName('SequenceNo',fm.SequenceNo,fm.SequenceNoName);
	quickGetName('vipvalue',fm.VIPValue1,fm.AppntVIPFlagname1);
	quickGetName('Relation',fm.RelationToMainInsured,fm.RelationToMainInsuredName);
	quickGetName('Relation',fm.RelationToAppnt,fm.RelationToAppntName);
	quickGetName('IDType',fm.IDType,fm.IDTypeName);
	quickGetName('Sex',fm.Sex,fm.SexName);
	quickGetName('Marriage',fm.Marriage,fm.MarriageName);
	quickGetName('OccupationCode',fm.OccupationCode,fm.OccupationCodeName);
	quickGetName('occupationtype',fm.OccupationType,fm.OccupationTypeName);
	quickGetName('NativePlace',fm.NativePlace,fm.NativePlaceName);
	quickGetName('LicenseType',fm.LicenseType,fm.LicenseTypeName);
//	quickGetName('Province',fm.InsuredProvince,fm.InsuredProvinceName);
//	quickGetName('City',fm.InsuredCity,fm.InsuredCityName);
//	quickGetName('District',fm.InsuredDistrict,fm.InsuredDistrictName);
	getAddressName('province','InsuredProvince','InsuredProvinceName');
	getAddressName('city','InsuredCity','InsuredCityName');
	getAddressName('district','InsuredDistrict','InsuredDistrictName');
	//quickGetName('incomeway',fm.IncomeWay,fm.IncomeWayName);
	}catch(ex){}
}
/**
* ���ݴ���ѡ��Ĵ�����Ҳ���ʾ���ƣ���ʾָ����һ��
* strCode - ����ѡ��Ĵ���
* showObjCode - �����ŵĽ������
* showObjName - Ҫ��ʾ���ƵĽ������
*/
function quickGetName(strCode, showObjc, showObjn) {
	var formsNum = 0;	//�����е�FORM��
	var elementsNum = 0;	//FORM�е�Ԫ����
	var urlStr = "";
	var sFeatures = "";
	var strQueryResult = "";	//����ѡ��Ĳ�ѯ�����
	var arrCode = null;	//�������
	var strCodeValue = "";	//����ֵ
	var cacheFlag = false;	//�ڴ��������ݱ�־
	var showObjn;
	var showObjc;
	//��������FORM


	//��������������ݲ�Ϊ�գ��Ų�ѯ���������κβ���
	if (showObjc.value != "")
	{
		//Ѱ��������
		if(cacheWin==null)
		{
			 cacheWin = searchMainWindow(this);
			if (cacheWin == false) { cacheWin = this; }
		}

		//��������������ݣ���������ȡ����
		if (cacheWin.parent.VD.gVCode.getVar(strCode))
		{
			arrCode = cacheWin.parent.VD.gVCode.getVar(strCode);
			cacheFlag = true;
		}
		else
		{
			urlStr = "../common/jsp/CodeQueryWindow.jsp?codeType=" + strCode;
			sFeatures = "status:no;help:0;close:0;dialogWidth:150px;dialogHeight:0px;dialogLeft:-1;dialogTop:-1;resizable=1";
			//�������ݿ����CODE��ѯ�����ز�ѯ�����strQueryResult
			//strQueryResult = window.showModalDialog(urlStr, "", sFeatures);
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=1;      //�������ڵĿ��; 
			var iHeight=1;     //�������ڵĸ߶�; 
			var iTop = 1; //��ô��ڵĴ�ֱλ�� 
			var iLeft = 1;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
		}
		//��ֳ�����
		try {
			if (!cacheFlag)
			{
				try
				{
					arrCode = decodeEasyQueryResult(strQueryResult);
				}
				catch(ex)
				{
					alert("ҳ��ȱ������EasyQueryVer3.js");
				}
				strCodeSelect = "";
				for (i=0; i<arrCode.length; i++)
				{
					strCodeSelect = strCodeSelect + "<option value=" + arrCode[i][0] + ">";
					strCodeSelect = strCodeSelect + arrCode[i][0] + "-" + arrCode[i][1];
					strCodeSelect = strCodeSelect + "</option>";
				}
				//����ֺõ����ݷŵ��ڴ���
				cacheWin.parent.VD.gVCode.addArrVar(strCode, "", arrCode);
				//�����Ƿ������ݴӷ������˵õ�,�����øñ���
				cacheWin.parent.VD.gVCode.addVar(strCode+"Select","",strCodeSelect);
			}
			cacheFlag = false;
			for (i=0; i<arrCode.length; i++)
			{
				if (showObjc.value == arrCode[i][0])
				{
					showObjn.value = arrCode[i][1];
					break;
				}
			}
		}
		catch(ex)
		{}
	}
}

//���ݵ�ַ�����ѯ��ַ������Ϣ,��ѯ��ַ�����<ldaddress>
//�������,��ַ����<province--ʡ;city--��;district--��/��;>,��ַ����<�������>,��ַ������Ϣ<���ֱ���>
//����,ֱ��Ϊ--��ַ������Ϣ<���ֱ���>--��ֵ
function getAddressName(strCode, strObjCode, strObjName)
{
	var PlaceType="";
	var PlaceCode="";
	//�жϵ�ַ����,<province--ʡ--01;city--��--02;district--��/��--03;>
	switch(strCode)
	{
	   case "province":
	      	PlaceType="01";
	      	break;
	   case "city":
	      	PlaceType="02";
	      	break;
	   case "district":
	      	PlaceType="03";
	      	break;   
	   default:
	   		PlaceType="";   		   	
	}
	//����FORM�е�����ELEMENT
	for (formsNum=0; formsNum<window.document.forms.length; formsNum++)
	{
		for (elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++)
		{
			//Ѱ�Ҵ���ѡ��Ԫ��
			if (window.document.forms[formsNum].elements[elementsNum].name == strObjCode)
			{
				strObjCode = window.document.forms[formsNum].elements[elementsNum];
			}
			if (window.document.forms[formsNum].elements[elementsNum].name == strObjName)
			{
				strObjName = window.document.forms[formsNum].elements[elementsNum];
				break;
			}
		}
	}
	//��������������ݳ���Ϊ[6]�Ų�ѯ���������κβ���

//	strObjName.value="";
	if(strObjCode.value !="")
	{
		PlaceCode=strObjCode.value;
		//var strSQL="select placecode,placename from ldaddress where placecode='"+PlaceCode+"' and placetype='"+PlaceType+"'";
		
		   var sqlid24 = 'CardContInputSql24';
		   var mySql24 = new SqlClass();
		   mySql24.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
		   mySql24.addSubPara(PlaceCode);// ָ������Ĳ���
		   mySql24.addSubPara(PlaceType);// ָ������Ĳ���
		   mySql24.setSqlId(sqlid24);// ָ��ʹ�õ�Sql��id
		   var strSQL = mySql24.getString();
		var arrAddress=easyExecSql(strSQL);
		if(arrAddress!=null)
		{   
			strObjName.value = arrAddress[0][1];
		}
		else
		{
			strObjName.value="";
		}
	}	
}


/*********************************************************************
 *  ��ѯ���غ󴥷�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterQuery21( arrQueryResult )
{
  //alert("1111here:" + arrQueryResult + "\n" + mOperate);

    if( arrQueryResult != null )
    {
        arrResult = arrQueryResult;
        var sqlid25 = 'CardContInputSql25';
		   var mySql25 = new SqlClass();
		   mySql25.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
		   mySql25.addSubPara(arrQueryResult[0][0]);// ָ������Ĳ���
		   mySql25.setSqlId(sqlid25);// ָ��ʹ�õ�Sql��id
		   //"select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0
        	arrResult = easyExecSql(mySql25.getString());
        	if (arrResult == null)
        	{
        	    alert("δ�鵽Ͷ������Ϣ");
        	}
        	else
        	{
        	   displayInsuredInfo(arrResult[0]);
        	  // showCodeName();
        	}
    }

	mOperate = 0;		// �ָ���̬
	//showCodeName();
}
/*********************************************************************
 *  Ͷ���˲�ѯ��Ť�¼�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
//function showAppnt1()
function showInsured1()
{
		showInfo = window.open( "../sys/LDPersonQueryNewMain.jsp?queryFlag=queryInsured" ,"",sFeatures);
}
/*********************************************************************
 *  �Ѳ�ѯ���ص�Ͷ����������ʾ��Ͷ���˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayAppnt()
{
	try{document.all('AppntCustomerNo').value= arrResult[0][0]; }catch(ex){};
	try{document.all('AppntName').value= arrResult[0][1]; }catch(ex){};
	try{document.all('AppntSex').value= arrResult[0][2]; }catch(ex){};
	try{document.all('AppntBirthday').value= arrResult[0][3]; }catch(ex){};
	try{document.all('AppntIDType').value= arrResult[0][4]; }catch(ex){};
	try{document.all('AppntIDNo').value= arrResult[0][5]; }catch(ex){};
	try{document.all('AppntPassword').value= arrResult[0][6]; }catch(ex){};
	try{document.all('AppntNativePlace').value= arrResult[0][7]; }catch(ex){};
	try{document.all('AppntNationality').value= arrResult[0][8]; }catch(ex){};
	try{document.all('AppntPlace').value= arrResult[0][9]; }catch(ex){};
	try{document.all('AppntMarriage').value= arrResult[0][10];}catch(ex){};
	try{document.all('AppntMarriageDate').value= arrResult[0][11];}catch(ex){};
	try{document.all('AppntHealth').value= arrResult[0][12];}catch(ex){};
	/*if(arrResult[0][13]!=null&&arrResult[0][13]!=0)
	{
		try{document.all('AppntStature').value= arrResult[0][13];}catch(ex){};
	}
	if(arrResult[0][14]!=null&&arrResult[0][14]!=0)
	{
		try{document.all('AppntAvoirdupois').value= arrResult[0][14];}catch(ex){};
	}*/
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
	try{document.all('AppntVIPValue').value= arrResult[0][35];}catch(ex){};
	try{document.all('AppntOperator').value= arrResult[0][36];}catch(ex){};
	try{document.all('AppntMakeDate').value= arrResult[0][37];}catch(ex){};
	try{document.all('AppntMakeTime').value= arrResult[0][38];}catch(ex){};
	try{document.all('AppntModifyDate').value= arrResult[0][39];}catch(ex){};
	try{document.all('AppntModifyTime').value= arrResult[0][40];}catch(ex){};
	try{document.all('AppntGrpName').value= arrResult[0][41];}catch(ex){};
	try{document.all('AppntLicenseType').value= arrResult[0][43];}catch(ex){};	
	//alert(arrResult[0][44]);
	try{document.all('RelationToInsured').value= arrResult[0][44];}catch(ex){};	
	//˳�㽫Ͷ���˵�ַ��Ϣ�Ƚ��г�ʼ��
	try{document.all('AppntPostalAddress').value= "";}catch(ex){};
	try{document.all('AppntPostalAddress').value= "";}catch(ex){};
	try{document.all('AppntZipCode').value= "";}catch(ex){};
	try{document.all('AppntPhone').value= "";}catch(ex){};
	try{document.all('AppntFax').value= "";}catch(ex){};
	try{document.all('AppntMobile').value= "";}catch(ex){};
	try{document.all('AppntEMail').value= "";}catch(ex){};
	//try{document.all('AppntGrpName').value= "";}catch(ex){};
	try{document.all('AppntHomeAddress').value= "";}catch(ex){};
	try{document.all('AppntHomeZipCode').value= "";}catch(ex){};
	try{document.all('AppntHomePhone').value= "";}catch(ex){};
	try{document.all('AppntHomeFax').value= "";}catch(ex){};
	try{document.all('AppntPhone').value= "";}catch(ex){};
	try{document.all('CompanyAddress').value= "";}catch(ex){};
	try{document.all('AppntGrpZipCode').value= "";}catch(ex){};
	try{document.all('AppntGrpFax').value= "";}catch(ex){};
}

/*********************************************************************
 *  Ͷ���˴���򺺻�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showAppntCodeName()
{
try{
	quickGetName('vipvalue',fm.AppntVIPValue,fm.AppntVIPFlagname);
	quickGetName('IDType',fm.AppntIDType,fm.AppntIDTypeName);
	quickGetName('Sex',fm.AppntSex,fm.AppntSexName);
	quickGetName('Marriage',fm.AppntMarriage,fm.AppntMarriageName);
	quickGetName('NativePlace',fm.AppntNativePlace,fm.AppntNativePlaceName);
	quickGetName('OccupationCode',fm.AppntOccupationCode,fm.AppntOccupationCodeName);
	quickGetName('occupationtype',fm.AppntOccupationType,fm.AppntOccupationTypeName);
//  quickGetName('province',fm.AppntProvince,fm.AppntProvinceName);
//  quickGetName('city',fm.AppntCity,fm.AppntCityName);
//  quickGetName('district',fm.AppntDistrict,fm.AppntDistrictName);
	//getAddressName('province','AppntProvince','AppntProvinceName');
	//getAddressName('city','AppntCity','AppntCityName');
	getAddressName('district','AppntDistrict','AppntDistrictName');
	quickGetName('paymode',fm.PayMode,fm.PayModeName);
	quickGetName('paylocation',fm.SecPayMode,fm.SecPayModeName);
	quickGetName('bank',fm.AppntBankCode,fm.AppntBankCodeName);
	quickGetName('bank',fm.SecAppntBankCode,fm.SecAppntBankCodeName);
	//quickGetName('incomeway',fm.IncomeWay0,fm.IncomeWayName0);
	quickGetName('relation',fm.RelationToInsured,fm.RelationToInsuredName);
	}catch(ex){}

}

/********************
**
** ��ѯͶ���˵�ַ��Ϣ
**
*********************/
function getdetailaddress()
{
	var sqlid26 = 'CardContInputSql26';
	   var mySql26 = new SqlClass();
	   mySql26.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
	   mySql26.addSubPara(fm.AppntAddressNo.value);// ָ������Ĳ���
	   mySql26.addSubPara(fm.AppntNo.value);// ָ������Ĳ���
	   mySql26.setSqlId(sqlid26);// ָ��ʹ�õ�Sql��id
	   var strSQL = mySql26.getString();
  // var strSQL="select b.* from LCAddress b where b.AddressNo='"+fm.AppntAddressNo.value+"' and b.CustomerNo='"+fm.AppntNo.value+"'";
   arrResult=easyExecSql(strSQL);
   try{document.all('AppntNo').value= arrResult[0][0];}catch(ex){};
   try{document.all('AppntAddressNo').value= arrResult[0][1];}catch(ex){};
   try{document.all('AddressNoName').value= arrResult[0][2];}catch(ex){};
   try{document.all('AppntPostalAddress').value= arrResult[0][2];}catch(ex){};
   try{document.all('AppntZipCode').value= arrResult[0][3];}catch(ex){};
   try{document.all('AppntPhone').value= arrResult[0][4];}catch(ex){};
   try{document.all('AppntFax').value= arrResult[0][5];}catch(ex){};
   try{document.all('AppntHomeAddress').value= arrResult[0][6];}catch(ex){};
   try{document.all('AppntHomeZipCode').value= arrResult[0][7];}catch(ex){};
   try{document.all('AppntHomePhone').value= arrResult[0][8];}catch(ex){};
   try{document.all('AppntHomeFax').value= arrResult[0][9];}catch(ex){};
   try{document.all('CompanyAddress').value= arrResult[0][10];}catch(ex){};
   try{document.all('AppntGrpZipCode').value= arrResult[0][11];}catch(ex){};
   //try{document.all('AppntPhone').value= arrResult[0][12];}catch(ex){};
   try{document.all('AppntGrpFax').value= arrResult[0][13];}catch(ex){};
   try{document.all('AppntMobile').value= arrResult[0][14];}catch(ex){};
   try{document.all('AppntMobileChs').value= arrResult[0][15];}catch(ex){};
   try{document.all('AppntEMail').value= arrResult[0][16];}catch(ex){};
   try{document.all('AppntBP').value= arrResult[0][17];}catch(ex){};
   try{document.all('AppntMobile2').value= arrResult[0][18];}catch(ex){};
   try{document.all('AppntMobileChs2').value= arrResult[0][19];}catch(ex){};
   try{document.all('AppntEMail2').value= arrResult[0][20];}catch(ex){};
   try{document.all('AppntBP2').value= arrResult[0][21];}catch(ex){};
   try{document.all('AppntProvince').value= arrResult[0][28];}catch(ex){};
   try{document.all('AppntCity').value= arrResult[0][29];}catch(ex){};
   try{document.all('AppntDistrict').value= arrResult[0][30];}catch(ex){};
   try{document.all('AppntGrpName').value= arrResult[0][27];}catch(ex){};
}

function showAppnt1()
{
		showInfo = window.open( "../sys/LDPersonQueryNewMain.jsp?queryFlag=queryAppnt" ,"",sFeatures);
}

/*********************************************************************
 *  ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
 *  ����  ��  ��ѯ���صĶ�ά����
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterQuery( arrQueryResult ) 
{
	if( arrQueryResult != null ) 
	{
		arrResult = arrQueryResult;
			// Ͷ������Ϣ
			//alert("arrQueryResult[0][0]=="+arrQueryResult[0][0]);
		var sqlid27 = 'CardContInputSql27';
		   var mySql27 = new SqlClass();
		   mySql27.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
		   mySql27.addSubPara(arrQueryResult[0][0]);// ָ������Ĳ���
		   mySql27.setSqlId(sqlid27);// ָ��ʹ�õ�Sql��id
		   //"select a.*  from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0
			arrResult = easyExecSql(mySql27.getString());
			if (arrResult == null) 
			{
				alert("δ�鵽Ͷ������Ϣ");
			} 
			else 
			{
				displayAppnt(arrResult[0]);
			}
	}
	mOperate = 0;		// �ָ���̬
	//showCodeName();
}

function afterSubmit4(FlagStr, content)
{
	unlockScreen('lkscreen');  
    showInfo.close();
    if (FlagStr == "Fail")
    {
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        content = "�����ɹ���";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        //var tContSQL ="select contno ,proposalno from lcpol where prtno ='"+fm.PrtNo.value+"'";
        var sqlid28 = 'CardContInputSql28';
 	   var mySql28 = new SqlClass();
 	   mySql28.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
 	   mySql28.addSubPara(fm.PrtNo.value);// ָ������Ĳ���
 	   mySql28.setSqlId(sqlid28);// ָ��ʹ�õ�Sql��id
 	   var tContSQL = mySql28.getString();

        var arrResult = easyExecSql(tContSQL);
        if(arrResult==null||arrResult==""||arrResult=="null"){}else{
           document.all('ProposalContNo').value = arrResult[0][0];
           document.all('ProposalNo').value = arrResult[0][1];
           //alert(document.all('ProposalNo').value);
        }
       // var tt = "select amnt,prem from lcpol where prtno='"+fm.PrtNo.value+"'";
        var sqlid29 = 'CardContInputSql29';
  	   var mySql29 = new SqlClass();
  	   mySql29.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
  	   mySql29.addSubPara(fm.PrtNo.value);// ָ������Ĳ���
  	   mySql29.setSqlId(sqlid29);// ָ��ʹ�õ�Sql��id
  	   var tt = mySql29.getString();

        var Result = easyExecSql(tt, 1, 0);
        //alert(Result);
        if (Result != null)
        {
            fm.Amnt.value = Result[0][0];
            fm.Prem.value = Result[0][1];
        }
        backInfo();
        //��ֹ�޸�һЩ����
        try{
        fm.PrtNo.disabled =true;
        fm.ManageCom.disabled =true;
        fm.AppntCustomerNo.disabled =true;
        fm.InsuredNo.disabled=true;
        }catch(ex){}

    }

}
function backInfo(){
  // var tappntSQL =" select b.postaladdress,b.zipcode,b.phone,b.homeaddress,b.homezipcode,b.homephone,a.* from lcappnt a,lcaddress b where a.prtno ='"+fm.PrtNo.value+"' and a.appntno=b.customerno";
   
   var sqlid30 = 'CardContInputSql30';
	   var mySql30 = new SqlClass();
	   mySql30.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
	   mySql30.addSubPara(fm.PrtNo.value);// ָ������Ĳ���
	   mySql30.setSqlId(sqlid30);// ָ��ʹ�õ�Sql��id
	   var tappntSQL = mySql30.getString();
   var arrResult =easyExecSql(tappntSQL);
  // var tinsurdSQL =" select * from lcinsured  where prtno ='"+fm.PrtNo.value+"'";
   
   var sqlid31 = 'CardContInputSql31';
   var mySql31 = new SqlClass();
   mySql31.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
   mySql31.addSubPara(fm.PrtNo.value);// ָ������Ĳ���
   mySql31.setSqlId(sqlid31);// ָ��ʹ�õ�Sql��id
   var tinsurdSQL = mySql31.getString();
   var arrResult1 =easyExecSql(tinsurdSQL);
   //Ͷ����
   try{fm.AppntCustomerNo.value = arrResult[0][9];}catch(ex){}
   try{fm.AppntName.value = arrResult[0][11];}catch(ex){}
   try{fm.AppntPhone.value =arrResult[0][2];}catch(ex){}
   try{fm.AppntPostalAddress.value =arrResult[0][0];}catch(ex){}
   try{fm.AppntZipCode.value= arrResult[0][1];}catch(ex){}
   //������
   try{fm.InsuredNo.value =arrResult1[0][2];}catch(ex){}
   try{fm.Name.value =arrResult1[0][12];}catch(ex){}
   try{fm.Sex.value =arrResult1[0][13];}catch(ex){}
   try{fm.Birthday.value =arrResult1[0][14];}catch(ex){}
   try{fm.IDType.value =arrResult1[0][15];}catch(ex){}
   try{fm.IDNo.value =arrResult1[0][16];}catch(ex){}
   
   
}

/*********************************************************************
 *  delete�¼����������ɾ������ťʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function deleteClick2()
{
try{
        fm.PrtNo.disabled =false;
        fm.ManageCom.disabled =false;
        fm.AppntCustomerNo.disabled =false;
        fm.InsuredNo.disabled=false;
        }catch(ex){}
    if (trim(fm.ProposalContNo.value) == "")
    {
        alert("���ȱ��濨�����ٽ���ɾ������!")
        return;
    }
   // strSql = "select * from lccont where contno = '" + fm.ProposalContNo.value + "' and CardFlag='4'";
    
    var sqlid32 = 'CardContInputSql32';
    var mySql32 = new SqlClass();
    mySql32.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
    mySql32.addSubPara(fm.ProposalContNo.value);// ָ������Ĳ���
    mySql32.setSqlId(sqlid32);// ָ��ʹ�õ�Sql��id
    var strSql = mySql32.getString();
    var HaveTB = easyExecSql(strSql);
    if (HaveTB == null)
    {
        alert("�ñ��������ڣ��޷�����ɾ��!");
        return;
    }
    else
    {
        var showStr = "����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

        if (mAction == "")
        {
            //showSubmitFrame(mDebug);
            //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            mAction = "DELETE";
            document.all('fmAction').value = mAction;
            fm.action = "CardContSave.jsp";
            fm.submit();
            //�ύ
        }
    }


    //top.close();
}

function checkPrtNo(){
   var tPrtNo =fm.PrtNo.value
   if(tPrtNo!=null&&tPrtNo.length!=14){
       alert("ӡˢ�Ų�Ϊ14λ��������¼��!");
       return false;
   }

}

/*********************************************************************
 *  Ͷ�����뱻������ͬѡ����¼�
 *  ����  ��  ��
 *  ����ֵ��  true or false
 *********************************************************************
 */
function isSamePerson()
{
    //��Ӧδѡͬһ�ˣ��ִ򹳵����
     //��Ӧδѡͬһ�ˣ��ִ򹳵����
    if (fm.RelationToInsured.value!="00" && fm.SamePersonFlag.checked==true) 
    {
      document.all('DivLCInsured').style.display = "";
      fm.SamePersonFlag.checked = false;
      alert("Ͷ�����뱻���˹�ϵ���Ǳ��ˣ����ܽ��иò�����");
    }
    
    if ( fm.SamePersonFlag.checked==true
       //&&fm.RelationToAppnt.value!="00"
       )
    {
      //DivLCInsured.style.display = "none";
      //divLCInsuredPerson.style.display = "none";
      //divSalary.style.display = "none";
      fm.SamePersonFlag.checked = true;
      //fm.RelationToAppnt.value="00"
      displayissameperson();
    }
    //��Ӧ��ͬһ�ˣ��ִ򹳵����
    else if (fm.SamePersonFlag.checked == true)
    {
      //document.all('DivLCInsured').style.display = "none";
      //divLCInsuredPerson.style.display = "none";
      //divSalary.style.display = "none";

      displayissameperson();
    }
    //��Ӧ��ѡͬһ�˵����
    else if (fm.SamePersonFlag.checked == false)
    {
      document.all('DivLCInsured').style.display = "";
      //divLCInsuredPerson.style.display = "";
     // divSalary.style.display = "";
      //if(InsuredGrid.mulLineCount==0)
	  //{
		//fm.SequenceNo.value="1";//�ڵ�һ��¼�뱻������ʱĬ��Ϊ��1���������ˡ�
		//fm.RelationToMainInsured.value="00";//���һ�������˹�ϵ:���������˿ͻ��ڲ���Ϊ��1���������ˡ�ʱ��Ĭ��Ϊ��00�����ˡ�
	  //}
	  //else
	  //{
	  // try{document.all('SequenceNo').value=""; }catch(ex){};
     //  try{document.all('RelationToMainInsured').value=""; }catch(ex){};
	 // }
      try{document.all('InsuredNo').value=""; }catch(ex){};
      try{document.all('Name').value=""; }catch(ex){};
      try{document.all('Sex').value= ""; }catch(ex){};
      try{document.all('Birthday').value= ""; }catch(ex){};
      try{document.all('IDType').value= "0"; }catch(ex){};
      try{document.all('IDNo').value= ""; }catch(ex){};
      try{document.all('Password').value= ""; }catch(ex){};
      try{document.all('NativePlace').value= ""; }catch(ex){};
      try{document.all('Nationality').value=""; }catch(ex){};
      try{document.all('InsuredPlace').value= ""; }catch(ex){};
      try{document.all('Marriage').value= "";}catch(ex){};
      try{document.all('MarriageDate').value= "";}catch(ex){};
      try{document.all('Health').value= "";}catch(ex){};
      /*try{document.all('Stature').value= "";}catch(ex){};
      try{document.all('Avoirdupois').value= "";}catch(ex){};*/
      try{document.all('Degree').value= "";}catch(ex){};
      try{document.all('CreditGrade').value= "";}catch(ex){};
      try{document.all('OthIDType').value= "";}catch(ex){};
      try{document.all('OthIDNo').value= "";}catch(ex){};
      try{document.all('ICNo').value="";}catch(ex){};
      try{document.all('GrpNo').value= "";}catch(ex){};
      try{document.all('JoinCompanyDate').value= "";}catch(ex){}
      try{document.all('StartWorkDate').value= "";}catch(ex){};
      try{document.all('Position').value= "";}catch(ex){};
      try{document.all('Salary').value= "";}catch(ex){};
      try{document.all('OccupationType').value= "";}catch(ex){};
      try{document.all('OccupationCode').value= "";}catch(ex){};
      try{document.all('WorkType').value= "";}catch(ex){};
      try{document.all('PluralityType').value= "";}catch(ex){};
      try{document.all('DeathDate').value= "";}catch(ex){};
      try{document.all('SmokeFlag').value= "";}catch(ex){};
      try{document.all('BlacklistFlag').value= "";}catch(ex){};
      try{document.all('Proterty').value= "";}catch(ex){};
      //try{document.all('Remark').value= "";}catch(ex){};
      try{document.all('State').value= "";}catch(ex){};
      try{document.all('Operator').value= "";}catch(ex){};
      try{document.all('MakeDate').value= "";}catch(ex){};
      try{document.all('MakeTime').value="";}catch(ex){};
      try{document.all('ModifyDate').value= "";}catch(ex){};
      try{document.all('ModifyTime').value= "";}catch(ex){};
      try{document.all('PostalAddress').value= "";}catch(ex){};
      try{document.all('PostalAddress').value= "";}catch(ex){};
      try{document.all('ZipCode').value= "";}catch(ex){};
      try{document.all('Phone').value= "";}catch(ex){};
      try{document.all('Mobile').value= "";}catch(ex){};
      try{document.all('EMail').value="";}catch(ex){};
      try{document.all('GrpName').value= "";}catch(ex){};
      try{document.all('Phone').value= "";}catch(ex){};
      try{document.all('GrpAddress').value="";}catch(ex){};
      try{document.all('GrpZipCode').value= "";}catch(ex){};
      try{document.all('RelationToAppnt').value= "";}catch(ex){};
      try{document.all('Fax').value= "";}catch(ex){};
      try{document.all('HomePhone').value= "";}catch(ex){};
     /* try{document.all('IncomeWay').value=  "";}catch(ex){};
      try{document.all('Income').value=  "";}catch(ex){};*/

    }
}

function displayissameperson()
{
  try{fm.SequenceNo.value="1";}catch(ex){};//�ڵ�һ��¼�뱻������ʱĬ��Ϊ��1���������ˡ�
  try{fm.RelationToMainInsured.value="00";}catch(ex){};//���һ�������˹�ϵ:���������˿ͻ��ڲ���Ϊ��1���������ˡ�ʱ��Ĭ��Ϊ��00�����ˡ�
//  try{fm.RelationToAppnt.value="00";}catch(ex){};
��try{document.all('InsuredNo').value= document.all("AppntNo").value;                 }catch(ex){};
��try{document.all('Name').value= document.all("AppntName").value;                    }catch(ex){};
��try{document.all('Sex').value= document.all("AppntSex").value;                      }catch(ex){};
��try{document.all('Birthday').value= document.all("AppntBirthday").value;            }catch(ex){};
��try{document.all('IDType').value= document.all("AppntIDType").value;                }catch(ex){};
��try{document.all('IDNo').value= document.all("AppntIDNo").value;                    }catch(ex){};
��try{document.all('Password').value= document.all( "AppntPassword" ).value;          }catch(ex){};
��try{document.all('NativePlace').value= document.all("AppntNativePlace").value;      }catch(ex){};
��try{document.all('Nationality').value= document.all("AppntNationality").value;      }catch(ex){};
��try{document.all('InsuredAddressNo').value= document.all("AppntAddressNo").value;   }catch(ex){};
��try{document.all('InsuredPlace').value= document.all( "AppntPlace" ).value;      	  }catch(ex){};
��try{document.all('Marriage').value= document.all( "AppntMarriage" ).value;          }catch(ex){};
��try{document.all('MarriageDate').value= document.all( "AppntMarriageDate" ).value;  }catch(ex){};
��try{document.all('Health').value= document.all( "AppntHealth" ).value;              }catch(ex){};
��//try{document.all('Stature').value= document.all( "AppntStature" ).value;            }catch(ex){};
��//try{document.all('Avoirdupois').value= document.all( "AppntAvoirdupois" ).value;    }catch(ex){};
��try{document.all('Degree').value= document.all( "AppntDegree" ).value;              }catch(ex){};
��try{document.all('CreditGrade').value= document.all( "AppntDegreeCreditGrade" ).value;}catch(ex){};
��try{document.all('OthIDType').value= document.all( "AppntOthIDType" ).value;}catch(ex){};
��try{document.all('OthIDNo').value= document.all( "AppntOthIDNo" ).value;}catch(ex){};
��try{document.all('ICNo').value= document.all("AppntICNo").value;}catch(ex){};
��try{document.all('GrpNo').value= document.all("AppntGrpNo").value;}catch(ex){};
��try{document.all( 'JoinCompanyDate' ).value = document.all("JoinCompanyDate").value; if(document.all( 'JoinCompanyDate' ).value=="false"){document.all( 'JoinCompanyDate' ).value="";} } catch(ex) { };
��try{document.all('StartWorkDate').value= document.all("AppntStartWorkDate").value;}catch(ex){};
��try{document.all('Position').value= document.all("AppntPosition").value;}catch(ex){};
��try{document.all( 'Position' ).value = document.all("Position").value;} catch(ex) { };
��try{document.all('Salary').value= document.all("AppntSalary").value;}catch(ex){};
��try{document.all('OccupationType').value= document.all("AppntOccupationType").value;}catch(ex){};
��try{document.all('OccupationCode').value= document.all("AppntOccupationCode").value;}catch(ex){};
��try{document.all('WorkType').value= document.all("AppntWorkType").value;}catch(ex){};
��try{document.all('PluralityType').value= document.all("AppntPluralityType").value;}catch(ex){};
��try{document.all('DeathDate').value= document.all("AppntDeathDate").value;}catch(ex){};
��try{document.all('SmokeFlag').value= document.all("AppntSmokeFlag").value;}catch(ex){};
��try{document.all('BlacklistFlag').value= document.all("AppntBlacklistFlag").value;}catch(ex){};
��try{document.all('Proterty').value= document.all("AppntProterty").value;}catch(ex){};
��//try{document.all('Remark').value= document.all("AppntRemark").value;}catch(ex){};
��try{document.all('State').value= document.all("AppntState").value;}catch(ex){};
��try{document.all('Operator').value= document.all("AppntOperator").value;}catch(ex){};
��try{document.all('MakeDate').value= document.all("AppntMakeDate").value;}catch(ex){};
��try{document.all('MakeTime').value= document.all("AppntMakeTime").value;}catch(ex){};
��try{document.all('ModifyDate').value= document.all("AppntModifyDate").value;}catch(ex){};
��try{document.all('ModifyTime').value= document.all("AppntModifyTime").value;}catch(ex){};
��try{document.all('PostalAddress').value= document.all("AppntPostalAddress").value;}catch(ex){};
��try{document.all('ZipCode').value= document.all("AppntZipCode").value;}catch(ex){};
��try{document.all('Phone').value= document.all("AppntPhone").value;}catch(ex){};
��try{document.all('Fax').value= document.all("AppntFax").value;}catch(ex){};
��try{document.all('Mobile').value= document.all("AppntMobile").value;}catch(ex){};
��try{document.all('EMail').value= document.all("AppntEMail").value;}catch(ex){};
��try{document.all('GrpName').value= document.all("AppntGrpName").value;}catch(ex){};
��try{document.all('Phone').value= document.all("AppntPhone").value;}catch(ex){};
��try{document.all('GrpAddress').value= document.all("CompanyAddress").value;}catch(ex){};
��try{document.all('GrpZipCode').value= document.all("AppntGrpZipCode").value;}catch(ex){};
��try{document.all('GrpFax').value= document.all("AppntGrpFax").value;}catch(ex){};
��try{document.all('HomeAddress').value= document.all("AppntHomeAddress").value;}catch(ex){};
��try{document.all('HomePhone').value= document.all("AppntHomePhone").value;}catch(ex){};
��try{document.all('HomeZipCode').value= document.all("AppntHomeZipCode").value;}catch(ex){};
��try{document.all('HomeFax').value= document.all("AppntHomeFax").value;}catch(ex){};
��try{document.all('RelationToAppnt').value="00";}catch(ex){};
��try{document.all('InsuredProvince').value= document.all("AppntProvince").value;}catch(ex){};
��try{document.all('InsuredCity').value= document.all("AppntCity").value;}catch(ex){};
��try{document.all('InsuredDistrict').value= document.all("AppntDistrict").value;}catch(ex){};
��try{document.all('LicenseType').value= document.all("AppntLicenseType").value;}catch(ex){};
  /*try{document.all('IncomeWay').value= document.all("IncomeWay0").value;}catch(ex){};
  try{document.all('Income').value= document.all("Income0").value;}catch(ex){};
  try{document.all('Stature').value= document.all("AppntStature").value;}catch(ex){};
  try{document.all('Avoirdupois').value= document.all("AppntAvoirdupois").value;}catch(ex){};
  showOneCodeName('incomeway','IncomeWay','IncomeWayName');*/

/***************************
  if(document.all('Position').value=="false"){
	  document.all('Position').value="";
	}
  if(document.all('Salary').value=="false"){
  	document.all( 'Salary' ).value="";
  }
***************************/
}

function getcodedata2()
{
	/*var sql="select RiskCode, RiskName from LMRiskApp where (enddate is null or enddate>'"+fm.sysdate.value+"') and lmriskapp.PolType ='C' "
	         +" union select riskcode,(select riskname from lmrisk where riskcode=lmriskcomctrl.riskcode) from LMRiskComCtrl where "
	         +" startdate<='"+fm.sysdate.value+"' and (enddate is null or enddate>'"+fm.sysdate.value+"') and ManageComGrp='"+document.all('ManageCom').value+"' "
	         +"  and (select distinct(riskprop) from lmriskapp where riskcode =lmriskcomctrl.riskcode) in ('C') order by RiskCode";*/
	
	var sqlid33 = 'CardContInputSql33';
    var mySql33 = new SqlClass();
    mySql33.setResourceName("card.CardContInputSql"); // ָ��ʹ�õ�properties�ļ���
    mySql33.addSubPara(fm.sysdate.value);// ָ������Ĳ���
    mySql33.addSubPara(fm.sysdate.value);// ָ������Ĳ���
    mySql33.addSubPara(fm.sysdate.value);// ָ������Ĳ���
    mySql33.addSubPara(document.all('ManageCom').value);// ָ������Ĳ���
    mySql33.setSqlId(sqlid33);// ָ��ʹ�õ�Sql��id
    var sql = mySql33.getString();
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
	//prompt("��ѯ���ִ���:",sql);
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
    document.all("RiskCode").CodeData=tCodeData;
	
}

function InitBnfType(parm1,parm2){
  var tCount =BnfGrid.mulLineCount -1;
  BnfGrid.setRowColData(tCount,1,"1");
}
