//���ļ�ΪԱ���ۿ۱���������в�������
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();

var RateGrid;
var mDebug="0";
var mOperate="";
var showInfo;
var tDivNo;
var Flag2 = "";
var aValue;
var bValue;
var cValue;
var dValue;
var sourcename="productdef.LARiskStaffRateSql";


window.onfocus=myonfocus;
//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus(){
	if(showInfo!=null){
		try{
	    	showInfo.focus();  
		}catch(ex){
	    	showInfo=null;
		}
	}
}

function dbclick(obj,code,name,codeName,codeType){
	
	if(obj==fm.all('ManageCom')){
		code = 'ManageCom';
		convalue = comcode+"%";
		condition = "comcode";
	}
 	
	if(fm.all('ManageCom').value!=""){
	  // if(obj==fm.all('AgentCom')){
	  // 	 if(!checkExistAgentCom(trim(fm.all('ManageCom').value))) return false;
	  // 	 codeType="ManageCom";
	  // 	 codeName=fm.all('ManageCom').value;
	  //}
	}
	
	if(obj==fm.all('ManageOrgan')){
		code = 'ManageCom';
		convalue = comcode+"%";
		condition = "comcode";
	}
	
	if(fm.all('ManageOrgan').value!=""){
		if(obj==fm.all('AgentOrgan')){
			if(!checkExistAgentCom(trim(fm.all('ManageOrgan').value))){
				return false;
			}
			codeType="ManageCom";
			codeName=fm.all('ManageOrgan').value;
		}
	}

	if(obj.readOnly == true){
    		return false;
	}else{
		return showCodeList(code,[obj,name],[0,1],null,codeName,codeType,1);
	}
} 
	
function keyup(obj,code,name,codeName,codeType){
	if(obj.readOnly == true){
		return false;
	}else{
		return showCodeListKey(code,[obj,name],[0,1],null,codeName,codeType,1);
	}
} 
	
//���ݴ�����������ѯ���������
function getManageCom(convalue){
//	var ManageCom;
//	var mySql;
//	mySql ="select ManageCom from LACom where AgentCom ='"+convalue+"'";
//	var strResult = easyQueryVer3(mySql, 1, 1, 1);
//	var arrDataSet = decodeEasyQueryResult(strResult);
//	ManageCom = arrDataSet[0][0];
//	
//	var mySql2;
//	mySql2 ="select * from LdCom where Comcode ='"+ManageCom+"'";
//	var strResult2 = easyQueryVer3(mySql, 1, 1, 1);
//	if(!strResult2){
//		alert("�ô������û�ж�Ӧ�Ĺ������");
//		ManageCom = "";
//		return false;		
//	}
//	return ManageCom;
}

//�����������Ϊ��ʱ��˫������ѡ��������ʱ��У��ù���������Ƿ���ڴ������
function checkExistAgentCom(ManageComvalue){
//	var mySql;
//	mySql ="select AgentCom from LACom where ManageCom ='"+ManageComvalue+"'";
//	var strResult = easyQueryVer3(mySql, 1, 1, 1);
//	if(!strResult){
//		alert("�ڸù���������޴�������������������������");
//		return false;
//	}
//	return true;
}

//������ѡ��������������������
function showManageOrgan(Comvalue){
   if(fm.all('AgentOrgan').value!=""){
//   	 fm.all('ManageOrgan').value = getManageCom(Comvalue);
 //  	 showOneCodeName('managecom','ManageOrgan','ManageOrganName'); 
  }
}
//������ѡ��������������������
function showManageCom(Comvalue){
//  if(fm.all('AgentCom').value!=""){
//   	 fm.all('ManageCom').value = getManageCom(Comvalue);
//   	 showOneCodeName('managecom','ManageCom','ManageComName'); 
//   	 
//  }
}
//�����������Ϊ��ʱ��˫������ѡ�����������������������������ƥ�䣬�򽫴���������
function checkAgCom(MaComValue){
//	if(fm.all('AgentCom').value!=""){
//	  var mySql;
//	  mySql = "select * from LaCom where AgentCom ='"+fm.all('AgentCom').value+"' and ManageCom = '"+MaComValue+"'";
//	  var strResult = easyQueryVer3(mySql, 1, 1, 1);
//	  if(!strResult){
//	  	fm.all('AgentCom').value="";
//	  	fm.all('AgentComName').value="";
//	  }
//	}
}

//�����������Ϊ��ʱ��˫������ѡ�����������������������������ƥ�䣬�򽫴���������
function checkAgOrgan(MaComValue){
//	if(fm.all('AgentOrgan').value!=""){
//	  var mySql;
//	  mySql = "select * from LaCom where AgentCom ='"+fm.all('AgentOrgan').value+"' and ManageCom = '"+MaComValue+"'";
//	  var strResult = easyQueryVer3(mySql, 1, 1, 1);
//	  if(!strResult){
//	  	fm.all('AgentOrgan').value="";
//	  	fm.all('AgentOrganName').value="";
//	  }
//	}
}


//�ύ�����水ť��Ӧ����
function submitForm()
{ 
	if (mOperate=="")
  {
  	myAlert(""+"�����������ݶ�ʧ��"+"");
  	return false;
  }
 // if (!beforeSubmit())
   // return false;
if(!checkSRFlag())
{
	return false;
	}
  var i = 0;
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  fm.hideOperate.value=mOperate;
  fm.submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content ){
	showInfo.close();
	if (FlagStr == "Fail" ){             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
	    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	    mOperate="";
    	if(MFlag==1){
			MFlag = 2;
		}
	}else{ 
		var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
  		//parent.fraInterface.initForm();
    	showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 
   		// easyQueryClick(); 
   		if(mOperate=="DELETE||MAIN"){
   			addClick();
   		}
   	    if(mOperate=="INSERT||MAIN"){
   			addClick();
   		}
   		if(mOperate=="UPDATE||MAIN"){
   			addClick();
   		}
		if(MFlag==1){
			MFlag = 3;
		}
	}
	if(MFlag==3){
		addClick();
    }else if(MFlag==2){
    	 mOperate="INSERT||MAIN";
    	//document.getElementById("singleAdjustment").style.display='';
    }
    
   MFlag = 0;
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()

//�ύǰ��У�顢����  
function beforeSubmit()
{ 
  RateGrid.delBlankLine("RateGrid");
  lineCount = RateGrid.mulLineCount;

  if (lineCount==0)
  {
  	myAlert(""+"���ܱ�����У�"+"");
  	return false;
  }
 if(!RateGrid.checkValue2())
 {
 		return false;
 }
 
  return true;
}           

function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
		parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}
function selectClick(){
	checked=RateGrid.getSelNo();
	showSelectCommision(checked-1);
	mOperate="UPDATE||MAIN";
	fm.all('Operator').value = tOperator;
}

function showSelectCommision(row){
	
	var col=1;
	fm.all("ManageOrgan").value=RateGrid.getRowColData(row,col++);
	fm.all("AgentOrgan").value=RateGrid.getRowColData(row,col++);
	fm.all("Risk").value=RateGrid.getRowColData(row,col++);
	col++;
	fm.all("PayYears").value=RateGrid.getRowColData(row,col++);
	fm.all("InsureAgeStart").value=RateGrid.getRowColData(row,col++);
	fm.all("InsureAgeEnd").value=RateGrid.getRowColData(row,col++);
	fm.all("PayToAge").value=RateGrid.getRowColData(row,col++);
	fm.all("Currency").value=RateGrid.getRowColData(row,col++);
	fm.all("CurrencyName").value=RateGrid.getRowColData(row,col++);
	fm.all("PayType").value=RateGrid.getRowColData(row,col++);
	fm.all("PayTypeName").value=RateGrid.getRowColData(row,col++);
	fm.all("PayMode").value=RateGrid.getRowColData(row,col++);
	fm.all("PayModeName").value=RateGrid.getRowColData(row,col++);
	fm.all("ProtectionPlan").value=RateGrid.getRowColData(row,col++);
	fm.all("ProtectionPlanName").value=RateGrid.getRowColData(row,col++);
	fm.all("PremPayPeriod").value=RateGrid.getRowColData(row,col++);
	fm.all("STAFFRATE").value=RateGrid.getRowColData(row,col++);
	fm.all("StartDate2").value=RateGrid.getRowColData(row,col++);
	fm.all("AppState").value=RateGrid.getRowColData(row,col++);
	col++;
	fm.all("IDNo").value=RateGrid.getRowColData(row,col++);
	fm.all("CommissionRate").value=RateGrid.getRowColData(row,col++);
	//fm.all("SRFlag").value=RateGrid.getRowColData(row,col++);
      
       showOneCodeName('pd_ldcom','ManageOrgan','ManageOrganName');
  //     showOneCodeName('bankcom','AgentOrgan','AgentOrganName');
       showOneCodeName('pd_lmriskinfo','Risk','RiskCodeName');
 //      showOneCodeName('yesorno','SRFlag','SRFlagS');
 //      showOneCodeName('PayType','PayType','PayTypeName');
 
       
/*       document.fm.ManageOrgan.readOnly = true;
       document.fm.AgentOrgan.readOnly = true;
       document.fm.Risk.readOnly = true;
       document.fm.PremPayPeriod.readOnly = true;
       document.fm.InsureAgeStart.readOnly = true;
       document.fm.InsureAgeEnd.readOnly = true;
       document.fm.PayToAge.readOnly = true;
       document.fm.Currency.readOnly = true;
       document.fm.PayType.readOnly = true;
       document.fm.PayMode.readOnly = true;
       document.fm.ProtectionPlan.readOnly = true;
       document.fm.PayYears.readOnly = true;*/


}

function showDiv1(div){
	if(div.style.display=="none"){
  	//div.style.display="";
  }else{
  	//div.style.display="none";
  }
}
//Click�¼������������ʱ�����ú���
function addClick()
{
	  fm.all("ManageOrgan").value="";
      //fm.all("AgentOrgan").value="";
      fm.all("Risk").value="";
      fm.all("ManageOrganName").value="";
      fm.all("AgentOrganName").value="";
      fm.all("RiskCodeName").value="";      
      fm.all("PremPayPeriod").value="";
      fm.all("InsureAgeStart").value="";
      fm.all("InsureAgeEnd").value="";
      fm.all("PayToAge").value="";
      fm.all("Currency").value="";
      fm.all("CurrencyName").value="";
      fm.all("PayType").value="";
      fm.all("PayTypeName").value="";
      fm.all("PayMode").value="";
      fm.all("PayModeName").value="";
      fm.all("ProtectionPlan").value="";
      fm.all("ProtectionPlanName").value="";
      fm.all("PayYears").value="";         
      //fm.all("CommissionRate").value="";     
      fm.all("StartDate2").value="";
      fm.all("AppState").value="";
      fm.all("IDNo").value="";
      fm.all("STAFFRATE").value="";
      fm.all("SRFlag").value="";
      fm.all("SRFLagS").value="";
    
  
      
      
       document.fm.ManageOrgan.readOnly = false;
       document.fm.AgentOrgan.readOnly = false;
       document.fm.Risk.readOnly = false;
       document.fm.PremPayPeriod.readOnly = false;     
       document.fm.InsureAgeStart.readOnly = false;
       document.fm.InsureAgeEnd.readOnly = false;
       document.fm.PayToAge.readOnly = false;
       document.fm.Currency.readOnly = false;
       document.fm.PayType.readOnly = false;
       document.fm.PayMode.readOnly = false;
       document.fm.ProtectionPlan.readOnly = false;
       document.fm.PayYears.readOnly = false;
	
  //document.getElementById("singleAdjustment").style.display='';
  mOperate="INSERT||MAIN";
  fm.all('Operator').value = tOperator;
  //var col = ['1','2','3','5','6','7','8','10'];
 // checkHasSameRecord(RateGrid,col);
  //submitForm();
}        
function checkHasSameRecord(RateGrid,col){
	var idx=['0'];
	var lineCount = RateGrid.mulLineCount;
	var flag=0;
	for(var row=0;row<lineCount;row++){
		if(RateGrid.getChkNo(row)){	
			flag=1;
			idx.push(row);
		}
	}

	var flag2;
	for(var i=1;i<idx.length;i++){
		for(var j=i+1;j<idx.length;j++){
			for(var k=0;k<col.length;k++){
			if(RateGrid.getRowColData(idx[i],col[k])!=RateGrid.getRowColData(idx[j],col[k])){
				flag2=0;
				break;
			}else{
				flag2=1;				
			}
		}
		}
	}
	if(flag2==1){
	   myAlert(""+"������Ϣ������ͬ�ļ�¼!"+"");
	   	return false;
	}

}
   
function deleteClick()
{     
    if(!checkRiskCode()){
		return false;
	}
	//document.getElementById("singleAdjustment").style.display='none';
	if(!checkData()){
		return false;
	}
  //����������Ӧ�Ĵ���
  if (confirm(""+"��ȷʵ��ɾ��ѡ��ļ�¼��?"+""))
  {
    mOperate="DELETE||MAIN";
     fm.all('Operator').value = tOperator;
     
     checked=RateGrid.getSelNo();
     var idx = RateGrid.getRowColData(checked-1,22);
     fm.all("IDNo").value=idx;
    //fm.Flag.value='0';
    //demo fyb 20110617    
    submitForm();
    easyQueryClick();
  }
  else
  {
    mOperate="";
    myAlert(""+"��ȡ����ɾ��������"+"");
  }    
}           
function updateClick()
{    
    if(!checkRiskCode()){
		return false;
	}
	if(!checkData()){
		return false;
	}
	//����������Ӧ�Ĵ���
 	if(!checkRisk(trim(fm.all("Risk").value))) return false;
    mOperate="UPDATE||MAIN";
    fm.all('Operator').value = tOperator;
    //selectClick();
    submitForm();
   	//document.getElementById("singleAdjustment").style.display='';
		
 
}   
var MFlag = 0;       
function saveClick(){
    if(!checkRiskCode()){
		return false;
	}
	 var StrP = /^[0-9]+.?[0-9]*$/;
	 var StrInt = /^[0-9]*$/;
	 var decimal = /^[0-9]\.?\d{0,5}$/;
	if(fm.all("ManageOrgan").value==""){
		myAlert(""+"�����������Ϊ��!"+"");
		return false;	
	}
	//if(!checkComboBox('managecom','ManageOrgan','ManageOrganName',"�������"))
	 //return false;
//	if(fm.all("AgentOrgan").value==""){
//		alert("�����������Ϊ��!");
//		return false;
//	}
	//if(!checkComboBox('bankcom','AgentOrgan','AgentOrganName',"�������"))
	// return false;
	if(fm.all("Risk").value==""){
		myAlert(""+"���ֲ���Ϊ��!"+"");
		return false;
	}
	if(!checkComboBox('riskcode','Risk','RiskCodeName',""+"����"+""))
	 return false;

	if(fm.all("PremPayPeriod").value!=""){
		if(!StrInt.test(fm.all("PremPayPeriod").value)){
			myAlert(""+"�����ڼ�(��)������Ч������!"+"");
			fm.all("PremPayPeriod").value="";
			return false;
		}
	}
	/*if(fm.all("InsureAgeStart").value!=""){
		if(!StrInt.test(fm.all("PremPayPeriod").value)){
			alert("����������(��)������Ч������!");
			fm.all("InsureAgeStart").value="";
			return false;
		}
	}
	if(fm.all("InsureAgeEnd").value!=""){
		if(!StrInt.test(fm.all("InsureAgeEnd").value)){
			alert("����������(��)������Ч������!");
			fm.all("InsureAgeEnd").value="";
			return false;
		}
	}*/
	if(!checkInsureAge()) return false;
	
	if(fm.all("PayToAge").value!=""){
		if(!StrInt.test(fm.all("PayToAge").value)){
			myAlert(""+"��������������Ч������!"+"");
			fm.all("PayToAge").value="";
			return false;
		}
	}
	/*if(fm.all("PremPayPeriod").value!=""){
		 if(fm.all("InsureAgeStart").value!=""){
		 	alert("�����ڼ�(��)�뱻��������(��)֮��ֻ����һ����");
		 	fm.all("InsureAgeStart").value="";
		 	fm.all("InsureAgeEnd").value="";
		 	return false;
		 	
		}
		 if(fm.all("InsureAgeEnd").value!=""){
		 	alert("�����ڼ�(��)�뱻��������(��)֮��ֻ����һ����");
		 	fm.all("InsureAgeStart").value="";
		 	fm.all("InsureAgeEnd").value="";
		 	return false;
		}
		 if(fm.all("PayToAge").value!=""){
		 	alert("�����ڼ�(��)���������֮��ֻ����һ����");
		 	fm.all("PayToAge").value="";
		 	return false;
		}
	}

	if(fm.all("PremPayPeriod").value==""&&fm.all("InsureAgeStart").value==""&&fm.all("PayToAge").value==""){
	   alert("�����ڼ䡢����������ͽ�������������һ������Ϊ��!");
		 return false;
	}
	*/
//	if(fm.all("Currency").value==""){
//		alert("���ֲ���Ϊ��!");
//		return false;
//	}
	if(fm.all("Currency").value!=""){
		if(!checkComboBox('sacurrency','Currency','CurrencyName',""+"����"+""))
	 		return false;
	}
//	 if(fm.all("PayMode").value==""){
//		   alert("�ɷѷ�ʽ����Ϊ��!");
//		   return false;
//	    }
	 if(!checkComboBox('paymentmode','PayMode','PayModeName',""+"�ɷѷ�ʽ"+""))
	 return false;
	if(!checkRisk(trim(fm.all("Risk").value))) return false;
	
//	if(fm.all("PayYears").value==""){
//		alert("������ݲ���Ϊ��!");
//		return false;
//	}
	
	if(!StrInt.test(fm.all("PayYears").value)){
			myAlert(""+"������ݲ�����Ч������!"+"");
			fm.all("PayYears").value=""
			return false;
		}
	if(fm.all("STAFFRATE").value==""){
		myAlert(""+"Ա���ۿ۱�������Ϊ��!"+"");
		return false;
	}
	if(!decimal.test(fm.all("STAFFRATE").value)){
		myAlert(""+"Ա���ۿ۱���������Ч�����֣�С����������ֻ����λ!"+"");
		fm.all("CommissionRate").value="";
		return false;
	}
	if(fm.all("StartDate2").value==""){
		myAlert(""+"��Ч���ڲ���Ϊ��!"+"");
		return false;
	}
	if(!checkInsureAgeInterval()) return false;
	//document.getElementById("singleAdjustment").style.display='none';
	mOperate="INSERT||MAIN";
	fm.all('Operator').value = tOperator;
	submitForm();
  // easyQueryClick();
 
} 

//У�鱻��������
function checkInsureAge(){
	InsureAgeStart = trim(fm.all("InsureAgeStart").value);
	InsureAgeEnd = trim(fm.all("InsureAgeEnd").value);
	if(InsureAgeStart!=""&&InsureAgeEnd==""){
	   myAlert(""+"�������ڲ���Ϊ�գ�"+"");
	   return false;
	}
	if(InsureAgeStart==""&&InsureAgeEnd!=""){
	   myAlert(""+"����ֹ�ڲ���Ϊ�գ�"+"");
	   return false;
	}
	if(InsureAgeStart>InsureAgeEnd){
	   myAlert(""+"�������ڲ��ܴ�������ֹ�ڣ�"+"");
	   fm.all("InsureAgeStart").value="";
	   fm.all("InsureAgeEnd").value="";
	   return false;
	}
	return true;
	
}
//У�鱻������������

function checkInsureAgeInterval(){
	InsureAgeStart = trim(fm.all("InsureAgeStart").value);
	InsureAgeEnd = trim(fm.all("InsureAgeEnd").value);
	var date = new Date();
	mySql = new SqlClass();
	mySql.setResourceName(sourcename);
	mySql.setSqlId("LARiskStaffRateSql3");	
	mySql.addSubPara(date.getTime());	       		
	mySql.addSubPara(trim(fm.all('ManageOrgan').value));
	mySql.addSubPara(trim(fm.all('Risk').value));
	mySql.addSubPara(trim(fm.all('PayToAge').value));
	mySql.addSubPara(trim(fm.all('Currency').value));
	mySql.addSubPara(trim(fm.all('PayType').value));
	mySql.addSubPara(trim(fm.all('PayMode').value));
	mySql.addSubPara(trim(fm.all('ProtectionPlan').value));
	mySql.addSubPara(trim(fm.all('PayYears').value));
	mySql.addSubPara(trim(fm.all('StartDate2').value));

	turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 1, 1);  
	var arrDataSet = decodeEasyQueryResult(turnPage.strQueryResult);     
		if(arrDataSet){
			for(var i=0;i<arrDataSet.length;i++){
				StartAge = arrDataSet[i][0];
				EndAge = arrDataSet[i][1];
				/*if(StartAge!=""&&EndAge!=""){
				if(InsureAgeEnd>=StartAge&&InsureAgeEnd<=EndAge){
					alert("�ñ��������������Ѵ��ڽ���1");
					fm.all("InsureAgeEnd").value="";
					return false;
				}
				if(InsureAgeStart>=StartAge&&InsureAgeStart<=EndAge){
					alert("�ñ��������������Ѵ��ڽ���2");
					fm.all("InsureAgeStart").value="";
					return false;
				}
				if(InsureAgeEnd>=EndAge&&InsureAgeStart<=StartAge){
					alert("�ñ��������������Ѵ��ڽ���3");
					fm.all("InsureAgeStart").value="";
					fm.all("InsureAgeEnd").value="";
					return false;
				}
			}*/
		}
 } 
 return true;
}
//���沢����
function saveAndAdd(){
   saveClick();  
   MFlag = 1; 
}
function submitClick()
{
  //�ύ���
  mOperate="UPDATE||MAIN";
  fm.Flag.value='1'; //�ύ��˱�־
  submitForm();
}           
//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    //cDiv.style.display="";
  }
  else
  {
    //cDiv.style.display="none";  
  }
}
function checkvalidcom()
{

	 var GIManageCom = comcode;	
	if((fm.ManageCom.value!="")&&(fm.ManageCom.value!=null))
	{
		if(!checkManageComPre(fm.ManageCom.value,GIManageCom))
		{
			myAlert(""+"����Ļ��������ڵ�½����"+"");
			return false;
		}
	}
	return true;
}

function easyQueryClick()
{
	/*if(checkvalidcom()==false)
	{
		return false;
	}*/
     if(verifyInput2()!=true)
    {
    		return false;
    }

    initRateGrid();
	var mySql = new SqlClass();
        mySql.setResourceName(sourcename);
	mySql.setSqlId("LARiskStaffRateSql1");		       		
	mySql.addSubPara(trim(fm.all('ManageCom').value));
	mySql.addSubPara(trim(fm.all('RiskCode').value));
	mySql.addSubPara(trim(fm.all('StartDate').value));	
	mySql.addSubPara(trim(fm.all('DefaultFlag').value));	
		           
	turnPage.queryModal(mySql.getString(),RateGrid);
	if (!turnPage.strQueryResult) {
		myAlert(""+"��Ҫ��ѯ����Ϣ�����ڣ����������룡"+"");
		return false;
	}
}

//�ڽ��С�+������ʱ���������Զ���ӵ�mulLine�Ķ�Ӧ��
function addRateGrid(){
	  //��ȡ�������ֵ
    var vManageCom=fm.ManageCom.value;
    var vAgentCom=fm.AgentCom.value;
    var vRiskCode=fm.RiskCode.value;
    var vRiskName=fm.RiskName.value;
    var vStartDate=fm.StartDate.value;
    
    lineCount = RateGrid.mulLineCount;
    for(var i=0;i<lineCount;i++)
    {
      value1 = RateGrid.getRowColData(i,1);    
      value2 = RateGrid.getRowColData(i,2);        	
      value3 = RateGrid.getRowColData(i,3);
      value4 = RateGrid.getRowColData(i,4);       	
      value10 = RateGrid.getRowColData(i,10);        	    	
  	if ((value1==null||value1==""))
  	{
  		RateGrid.setRowColData(i,1,vManageCom); 
  	}        
  	if ((value2==null||value2==""))
  	{
  		RateGrid.setRowColData(i,2,vAgentCom); 
  	}        
  	if ((value3==null||value3==""))
  	{
  		RateGrid.setRowColData(i,3,vRiskCode);  
  		RateGrid.setRowColData(i,4,vRiskName)
  	}        
  	if ((value10==null||value10==""))
  	{
  		RateGrid.setRowColData(i,10,vStartDate); 
  	}          	       
   }    
}

function checkData(){
	RateGrid.delBlankLine("RateGrid");
	var lineCount = RateGrid.mulLineCount;
	if (lineCount==0)
  {
  	myAlert(""+"��Ӷ�������Ϣ�����ܽ����޸�"+"");
  	return false;
  }    
   checked=RateGrid.getSelNo();
	//var flag=0;
	//for(var row=0;row<lineCount;row++){
	//	if(RateGrid.getChkNo(row)){	
		//	flag=1;
	//	}
	//}
	//if(flag==0){
		//alert("��ѡ����Ҫ��������");
		//return false;
	//}
	 if(!checked){
     	  myAlert(""+"����ѡ��һ����Ϣ"+"");
     	 return false;
     }
	return true;
}

function checkRisk(RiskCode){
	var date = new Date();
	      mySql = new SqlClass();
		    mySql.setResourceName(sourcename);
		        mySql.setSqlId("LARiskStaffRateSql2");	
		        mySql.addSubPara(date.getTime());	       		
		        mySql.addSubPara(RiskCode);
      
      turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 1, 1);  
      var arrDataSet = decodeEasyQueryResult(turnPage.strQueryResult);     
		if (arrDataSet) {		
		var risktype=arrDataSet[0][0];
		if(risktype == '0'){
			//if(fm.all("PayType").value!=""||fm.all("ProtectionPlan").value!=""){
			if(fm.all("PayType").value!=""){
				myAlert(""+"�����ֲ���Ͷ���ջ������գ�������дͶ�����͡�"+"");
				fm.all("PayType").value="";
				fm.all("PayTypeName").value="";
				//fm.all("ProtectionPlan").value="";
				//fm.all("ProtectionPlanName").value="";  
				return false;
			}
		}else{
			if(fm.all("PayType")!=""){
				if(!checkComboBox('partbelong','PayType','PayTypeName',""+"Ͷ������"+"")){
	 				return false;
	   			}
			}
	    	
	    	if(fm.all("ProtectionPlan").value!=""){
				if(!checkComboBox('protection','ProtectionPlan','ProtectionPlanName',""+"���ϼƻ�"+"")){
					return false;
				}
			}
			
		}
	}else{
		myAlert(""+"�����ֲ�����"+"");
		return false;
	}
	return true;
}
//У��������������������Ƿ���ȷ
function checkComboBox(code,obj1,obj2,chname){
	if(fm.all(obj1).value!=""){
   showOneCodeName(code,obj1,obj2);  
	   if(fm.all(obj2).value==""){
	   	var out = chname+""+"���벻�������ݿ�涨��ȡֵ��Χ������Ļ�˫�������ѡ��"+"";
	   	myAlert(out);
	   	fm.all(obj1).value="";
	   	return false;
	  }
	}
  return true;
}
function checkSRFlag()
{
	try{
		var SRFlag=fm.all("SRFlag").value;
		if(SRFlag=='Y'){
			if(fm.all("CommissionRate").value!=fm.all("STAFFRATE").value){
				myAlert(""+"Ա���ۿ�������Ӷ���������ͬ��"+"");
				return false;
			}
		}
		
	}catch(ex){
		myAlert(""+"Ա���ۿۻ�������Ӷ�������д����"+"");
		return false;
	}
	return true;
}
function isshowbutton()
{   var value=getQueryState1();
	if(value=='0'||value==0){
	//document.getElementById('savabuttonid').style.display = 'none';
	document.getElementById('savabutton1').disabled=true;
	document.getElementById('savabutton2').disabled=true;
	document.getElementById('savabutton3').disabled=true;
	}
}
//-------- add by jucy
//ֻ���������޸ĵ�ǰ���ֵ�Ӷ��
function checkRiskCode(){
	var checkRiskCodeValue = fm.all("Risk").value;
	if(mRiskCode != checkRiskCodeValue){
		myAlert(""+"ֻ�����������޸ġ�ɾ����ǰ���ڶ���Ĳ�Ʒ��"+""+mRiskCode+""+"��"+"");
		return false;
	}
	return true;
}
//-------- end
// ===============================================

function initEdorType1(cObj1,cObj2){
	if(trim(fm.ManageCom.value).length <= 4){
		mEdorType = " #1# and managecom=#"+fm.ManageCom.value+"# and branchtype=#"+fm.BranchType.value+"# ";
		showCodeList('agentcom',[cObj1,cObj2],[0,1], null, mEdorType, "1");
	}else if(trim(fm.ManageCom.value).length == 8){
		mEdorType = " #1# and banktype=#02# and managecom=substr(#"+fm.ManageCom.value+"#,0,6) and branchtype=#"+fm.BranchType.value+"# ";
		showCodeList('agentcom',[cObj1,cObj2],[0,1], null, mEdorType, "1");
	}
}

//������ѡ��������������������
function showManageOrgan(Comvalue){
   if(fm.all('AgentOrgan').value!=""){
   	 fm.all('ManageOrgan').value = getManageCom(Comvalue);
   	 showOneCodeName('managecom','ManageOrgan','ManageOrganName'); 
  }
}


