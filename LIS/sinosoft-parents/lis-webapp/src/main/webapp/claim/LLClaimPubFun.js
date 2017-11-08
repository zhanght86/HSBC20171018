/**
 * ��������: LLClaimPubFun.js 
 * ������: ����ǰ̨���÷����� 
 * �������ڣ�2005-12-26
 */
var mySql = new SqlClass();
/*****************************************************************************************
 * ���ܣ����ڸ�ʽת������
 * ������ֻ��������YYYYMMDD��YYYY-MM-DD��ʽ�����ڣ���YYYYMMDD���ڿ��Զ�ת��ΪYYYY-MM-DD��ʽ��
 *       �Է������ַ�����У��
 * ���أ�YYYY-MM-DD��ʽ������
 * ����: ������  2005-12-26 10:02
******************************************************************************************/
function CheckDate(Filed)
{
	var tDate = Filed.value;
	var Year  = "";
	var Month = "";
	var Day   = "";
	//�������ڰ�λ��YYYYMMDD��ʽ
	if(tDate.length == 8)
	{
		if(tDate.indexOf('-') == -1)
		{		
			Year  = tDate.substring(0,4);
			Month = tDate.substring(4,6);
			Day   = tDate.substring(6,8);	
			tDate = Year+"-"+Month+"-"+Day;
		}
	    else
		{
			alert("������������������������룡");
			return Filed.value = "";
		}
	}
	//��������10λ��YYYY-MM-DD��ʽ
	else if(tDate.length == 10)
	{
		if((tDate.substring(4,5) != '-')||(tDate.substring(7,8) != '-'))
		{
			alert("������������������������룡");
			return Filed.value = "";
		}		
		Year  = tDate.substring(0,4);
		Month = tDate.substring(5,7);
		Day   = tDate.substring(8,10);	
		tDate = Year+"-"+Month+"-"+Day;	
	}
	//�������ڼȲ���YYYYMMDD��ʽ��Ҳ����YYYY-MM-DD��ʽ
	else
	{
	    if(tDate == null||tDate == "")//����Ϊ�գ����ؿ�ֵ��������
	    {
	    	return Filed.value = "";
	    }
	    else//���벻Ϊ�գ���ʾ����
	    {
	  	    alert("������������������������룡");
	  	    return Filed.value = "";        	
	    }	
	}
    //У�����������Ƿ�Ϊ��������
	if((!isInteger(Year))||(!isInteger(Month))||(!isInteger(Day))||(Year == "0000")||(Month == "00")||(Day == "00"))
	{
	    alert("��������������������������룡");
	    return Filed.value = "";
	}	    
    //���·���������һ����ȷУ�� 
	if(Month>12){alert("������������һ��ֻ��12���£����������룡");return Filed.value = "";}
	if(Month=="01"&&Day>31){alert("������������һ��ֻ��31�գ����������룡");return Filed.value = ""; }
    if(Month=="02"&&Day>29){alert("�����������󣬶������ֻ��29�գ����������룡");return Filed.value = "";}
    if(Month=="02"&&Day==29)//����Ҫ�ж��Ƿ�Ϊ����
    {
    	if((Year%100==0)&&(Year%400!=0))//�������ж�
    	{
    		alert("�����������󣬷��������ֻ��28�գ����������룡");return Filed.value = "";
    	}
    	if((Year%100!=0)&&(Year%4!=0))//�������ж�
    	{
    		alert("�����������󣬷��������ֻ��28�գ����������룡");return Filed.value = "";
    	}
    } 
	if(Month=="03"&&Day>31){alert("����������������ֻ��31�գ����������룡");return Filed.value = "";}
	if(Month=="04"&&Day>30){alert("����������������ֻ��30�գ����������룡");return Filed.value = "";}
	if(Month=="05"&&Day>31){alert("����������������ֻ��31�գ����������룡");return Filed.value = "";}
	if(Month=="06"&&Day>30){alert("����������������ֻ��30�գ����������룡");return Filed.value = "";}
	if(Month=="07"&&Day>31){alert("����������������ֻ��31�գ����������룡");return Filed.value = "";}
	if(Month=="08"&&Day>31){alert("�����������󣬰���ֻ��31�գ����������룡");return Filed.value = "";}
	if(Month=="09"&&Day>30){alert("�����������󣬾���ֻ��30�գ����������룡");return Filed.value = "";}
	if(Month=="10"&&Day>31){alert("������������ʮ��ֻ��31�գ����������룡");return Filed.value = "";}
	if(Month=="11"&&Day>30){alert("������������ʮһ��ֻ��30�գ����������룡");return Filed.value = "";}
	if(Month=="12"&&Day>31){alert("������������ʮ����ֻ��31�գ����������룡");return Filed.value = "";}
	
	Filed.value = tDate;//У��ͨ���󣬷���ֵ
}

/*****************************************************************************************
 * ���ܣ���ѯ�ⰸ��һЩ��Ҫ��־
 * ��������ʾ�ⰸ�Ƿ���������\�ʱ�\Ԥ��\���ˣ�Ŀǰֻ��������������ʹ��
 * ���أ���
 * ����: ����  2005-12-27 14:02
******************************************************************************************/
function QueryClaimFlag()
{
    var tFlag = "";
    
    //����
    /*
    var strSql1 = "select count(*) from LLInqConclusion where "
               + "clmno = '" + fm.RptNo.value + "'";
               */
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPubFunSql");
	mySql.setSqlId("LLClaimPubFunSql1");
	mySql.addSubPara(fm.RptNo.value );
    var tFiniFlag = easyExecSql(mySql.getString());
    if (tFiniFlag != '0')
    {
        tFlag = tFlag + "����";
    }
    
    //�ʱ�
    /*
    var strSql2 = "select count(*) from LLSubmitApply where "
               + "clmno = '" + fm.RptNo.value + "'";
     */          
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPubFunSql");
	mySql.setSqlId("LLClaimPubFunSql2");
	mySql.addSubPara(fm.RptNo.value );
	
    var tSubState = easyExecSql(mySql.getString());
    if (tSubState != '0')
    {
        if (tFlag == "")
        {
            tFlag = tFlag + "�ʱ�";
        }
        else
        {
            tFlag = tFlag + "���ʱ�";
        }
    }
    
    //Ԥ��
    /*
    var strSql3 = "select count(*) from llprepayclaim where "
                + " clmno = '" + fm.RptNo.value + "'";
     */           
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPubFunSql");
	mySql.setSqlId("LLClaimPubFunSql3");
	mySql.addSubPara(fm.RptNo.value );
	
    var tPrey = easyExecSql(mySql.getString());
    if (tPrey != '0')
    {
        if (tFlag == "")
        {
            tFlag = tFlag + "Ԥ��";
        }
        else
        {
            tFlag = tFlag + "��Ԥ��";;
        }
    }
    
    //����
    /*
    var strSql4 = "select count(*) from LLCUWBatch where "
                + " caseno = '" + fm.RptNo.value + "'";
    */            
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPubFunSql");
	mySql.setSqlId("LLClaimPubFunSql4");
	mySql.addSubPara(fm.RptNo.value );
	
    var tState = easyExecSql(mySql.getString());
    if (tState != '0')
    {
        if (tFlag == "")
        {
            tFlag = tFlag + "����";
        }
        else
        {
            tFlag = tFlag + "������";;
        }
    }
    
    fm.ClaimFlag.value = tFlag;
}

/*****************************************************************************************
 * ���ܣ�У���Ƿ���δ�������ⰸ������Ӧ����Ϣ
 * ��������Ҫ�Ƕ��˼ӷѲ�����Ӧ����Ϣ
 * �������ⰸ��tClmNo
 * ���أ�ture or false
 * ����: ����  2006-1-6 10:02
******************************************************************************************/
function checkLjspay(tClmNo)
{
   /* var strQSql = "select count(1) from ljspay where othernotype = '9' and getnoticeno like 'CLMUW%%' "
                + " and otherno = '" + tClmNo + "'";*/
   // var strQSql = "select count(1) from ljspay   where  otherno ='"+tClmNo+"' ";

	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPubFunSql");
	mySql.setSqlId("LLClaimPubFunSql5");
	mySql.addSubPara(tClmNo);
	
    var tCount = easyExecSql(mySql.getString());

    if (tCount == null)
    {
        alert("��ѯ�ӷ���Ϣ����");
        return false;
    }
    else if (tCount == 0)
    {
        return true; //û�ж�Ӧ��Ӧ��
    }
    else
    {
        alert("���ں˱��ӷѵ�Ӧ����Ϣδ������");
        return false; //���ڶ�Ӧ��Ӧ��
    }
}

/*********************************************************************************************
 * ���ܣ����ʱ��У��,Ҫ���ʱ������6λ����
 * ��������Ҫ��¼�롰�������ʱࡱʱʹ��
 * �������ʱ�tPostCode
 * ���أ�true/false
 * ������niuzj,2006-01-12
**********************************************************************************************/
function checkPostCode(tPostCode)
{
	  var ttPostCode = tPostCode.value;
	  if(ttPostCode == null || ttPostCode == "")
	  {
		   return true;
	  }
    else
    {
  	   if(ttPostCode.length == 6 && isInteger(ttPostCode))
  	   {
  	   	  return true;
  	   }
  	   else
  	   {
  	   	  alert("������ʱ�����,����������!");
  	   	  return false;
  	   }
    }
}


/*********************************************************************************************
 * ���ܣ���ֹͬһ�û�ͬʱ���������ڲ���ͬһ���ⰸ���ܲ����Ĵ���
 * �����������������ύ�Ĳ���ǰ���顣����˵������ֻ���������js�ļ�����Ӽ��飬Ҳ����©���ģ�
         ���ⰸ״̬�ı�֮ǰ�û��ѽ���֧���ܵ�ҳ��򿪣���������һ�����洰�ڲ����ı��ⰸ״̬��
         �ٻص���ǰ�Ѵ򿪵ķ�֧�����в���������ʧЧ��Ҫ��������⣬���ڷ�֧�����js��Ҳ���жϡ�
 * ������tClmNo�ⰸ�ţ�����Ϊ�ְ��ţ���tClmState1,tClmState2�ⰸ״̬������ֻʹ��һ������
 * ���أ�true/false
 * ������zhaorx 2006-11-22
**********************************************************************************************/
function KillTwoWindows(tClmNo,tClmState1,tClmState2)
{
    //var tSQLF = " select clmstate from llregister where rgtno = '"+tClmNo+"'";
    
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPubFunSql");
	mySql.setSqlId("LLClaimPubFunSql6");
	mySql.addSubPara(tClmNo);
	
    var tClmState = easyExecSql(mySql.getString());  
    if(tClmState==null)//�ⰸ������������֮ǰ������ȷ��δ�� 
    {   
			  //var tSQLS   = " select rgtobj from llreport where rptno='"+tClmNo+"' ";
			  
			  	mySql = new SqlClass();
				mySql.setResourceName("claim.LLClaimPubFunSql");
				mySql.setSqlId("LLClaimPubFunSql7");
				mySql.addSubPara(tClmNo);
	
			  var tRgtObj = easyExecSql(mySql.getString());          
			  if(tRgtObj=='1')//����
			  {/*
			  	  var tSQLT1 = " select activityid from lwmission where activityid='0000005005' "
			  	             + " and missionprop1='"+tClmNo+"' ";
			  	*/             
			    mySql = new SqlClass();
				mySql.setResourceName("claim.LLClaimPubFunSql");
				mySql.setSqlId("LLClaimPubFunSql8");
				mySql.addSubPara(tClmNo);
	        //prompt("��ֹͬһ�û�ͬʱ���������ڲ���ͬһ���ⰸ���ܲ����Ĵ���",tSQLT1);
            var tActD1 = easyExecSql(mySql.getString());
            if(tActD1==null)//��Ϊ�ѱ���ȷ�ϣ�����״̬ͬ����
            {
            	  tClmState="20";
            }
            else//���������ڣ�û�б���ȷ��
            {
              	tClmState="10";
            } 			  	             
			  }
			  if(tRgtObj=='2')//����
			  {
			  /*
			  	  var tSQLT2 = " select activityid from lwmission where activityid='0000005205' and exists "
			  	             + " (select 'X' from llreport where missionprop1=trim(rgtobjno) and rptno='"+tClmNo+"') ";
            */
            	mySql = new SqlClass();
				mySql.setResourceName("claim.LLClaimPubFunSql");
				mySql.setSqlId("LLClaimPubFunSql9");
				mySql.addSubPara(tClmNo);
				
            var tActD2 = easyExecSql(mySql.getString()); 	
            if(tActD2==null)
            {
            	  tClmState="20";
            }
            else
            {
              	tClmState="10";
            }            		  	
			  }
    } 
	  if(tClmState1!=tClmState&&tClmState2!=tClmState)
	   {
	    	 alert("�ⰸ״̬�ѷ����仯�������µ�½�������");
	    	 return false;
	   }    
    return true;      
}

/*****************************************************************************************
 * ���ܣ�У���Ƿ�ҽԺ���ڹ�����ѡ��������
 * ����: yhy  2011-5-23 10:02
******************************************************************************************/

function showCountry(){
   if(fm.hospitalContr1.value=='4'){   
      document.getElementById('DivotherCountry').style.display='';         
   }else{
   	  document.getElementById('DivotherCountry').style.display='none';  	
   }
   if(fm.hospitalContr2.value=='4'){   
      document.getElementById('otherCountryDiv').style.display='';         
   }else{
   	  document.getElementById('otherCountryDiv').style.display='none';  	
   }
   if(fm.hospitalContr3.value=='4'){   
      document.getElementById('otherCountryDiv1').style.display='';         
   }else{
   	  document.getElementById('otherCountryDiv1').style.display='none';  	
   }
}

/*********************************************************************************************
 * ���ܣ��Ե绰��У��,Ҫ��绰����8λ��ֵ
 * ��������Ҫ��¼�롰�����˵绰��ʱʹ��
 * �������ʱ�tRptorPhone
 * ���أ�true/false
 * ������yhy,2011-06-13
**********************************************************************************************/
function checkPhone(tRptorPhone)
{
	  var tRptorPhone1 = tRptorPhone.value;
	  if(tRptorPhone1 == null || tRptorPhone1 == "")
	  {
		   return true;
	  }
    else
    {
  	   if(tRptorPhone1.length >= 8 && isInteger(tRptorPhone1) && tRptorPhone1.length <= 20)
  	   {
  	    
  	   	  return true;
  	   }
  	   else
  	   {
  	   	  alert("����ĵ绰����,����������!");
  	   	  tRptorPhone.value = "";
  	   	  return false;
  	   }
    }
}

//�ж��ַ����Ƿ�Ϊ��
function isnull(str){
	return str == "" || str == null;
}
//����Ϊ��������,��1980-5-9 
function getAge(birth){
    var nowYear = mCurrentDate.substring(0,4);	
    var nowMonth = mCurrentDate.substring(5,7);
    var nowDate = mCurrentDate.substring(8,10);
    var oneYear = birth.substring(0,4);
    var oneMonth = birth.substring(5,7);
    var oneDate = birth.substring(8,10);
    var age = parseInt(nowYear) - parseInt(oneYear);
    if(parseInt(nowMonth) < parseInt(oneMonth) 
    		|| (parseInt(nowMonth) == parseInt(oneMonth) && nowDate < oneDate)) {
    	age--;
    }
	if (age <= 0){
        age = 0
    }
    return age;
}

//�м���ת����codeNoֵΪ��ʱ�����codeName��ֵ
function getCodeName(strCode, showObjCode, showObjName){
	try{
		if(isnull(document.all(showObjCode).value)){
			document.all(showObjName).value = "";
		}else{
			showOneCodeName(strCode, showObjCode, showObjName);
		}
	}catch(e){
	}
}

//ȡ��ie11��������򵥻���ʾ��ʷ������Ϣ����
/*****************************************************************
 * BEGIN
 *****************************************************************/
if(document.attachEvent){
	window.attachEvent("onload",dealAutoComplete);
}else{
	window.addEventListener("load",dealAutoComplete,false);
}
document.getElementsByClassName = function(className, oBox) {
	// �����ڻ�ȡĳ��HTML�����ڲ�����ĳһ�ض�className������HTMLԪ��
	this.d = oBox || document;
	var children = this.d.getElementsByTagName('*') || document.all;
	var elements = new Array();
	for (var ii = 0; ii < children.length; ii++) {
		var child = children[ii];
		var classNames = child.className.split(' ');
		for (var j = 0; j < classNames.length; j++) {
			if (classNames[j] == className) {
				elements.push(child);
				break;
			}
		}
	}
	return elements;
}
function dealAutoComplete(){
	var tElement = document.getElementsByClassName("codeno");
	for(var i=0;i<tElement.length;i++){
		tElement[i].setAttribute('autocomplete','off');
	}
}
//�ύ����Ϣ������ʾ��
function showAfterInfo(FlagStr,content,Flag){
	var tFlag = FlagStr.substring(0,1);
	tFlag = tFlag=="S"?"S":"F";
	var urlStr="../common/jsp/MessagePage.jsp?picture="+tFlag+"&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    return false;
}
//�ύ��Ϣ����ʾ
function showBeforeInfo(){
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
    return window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
}
//��ѯ��������
function getObName(tCode, tName,tSqlId){
	if (isnull(document.all(tCode).value)) {
		document.all(tName).value = "";
		return;
	}
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPubFunSql");
	mySql.setSqlId(tSqlId);
	mySql.addSubPara(document.all(tCode).value);

	var tICDName = easyExecSql(mySql.getString());
	if (tICDName) {
		document.all(tName).value = tICDName;
	}
}
//��ѯ������������
function getResultName(tCode, tName) {
	var tSqlId = "LLClaimPubFunSql10";
	getObName(tCode, tName,tSqlId);
}
//��ѯ���Ʒ�ʽ����
function getCureName(tCode,tName){
	var tSqlId = "LLClaimPubFunSql11";
	getObName(tCode, tName,tSqlId);
}

//��ѯ����ҽԺ����
function getHospitalName(tCode, tName) {
	var tSqlId = "LLClaimPubFunSql12";
	getObName(tCode, tName,tSqlId);
}
//��ѯ����ҽ������
function getDoctorName(tCode, tName) {
	var tSqlId = "LLClaimPubFunSql13";
	getObName(tCode, tName,tSqlId);
}

//��ѯ��������
function getCityName(tCode, tName,tSqlId) {
	var tSqlId = "LLClaimPubFunSql15";
	getObName(tCode, tName,tSqlId);
}

//��ѯ������
function getAreaName(tCode, tName) {
	var tSqlId = "LLClaimPubFunSql16";
	getObName(tCode, tName,tSqlId);
}

//��ʾ������ѯҳ��
function showDisease(tCode, tName){
	var strUrl = "LLQueryDiseaseMain.jsp?code=" + tCode + "&name=" + tName;
	openPage(strUrl);
}

//��ʾ���Ʒ�ʽ��ѯҳ��
function showCure(tCode, tName){
	var strUrl = "LLQueryCureMain.jsp?code=" + tCode + "&name=" + tName;
	openPage(strUrl);
}

//����ҽ����ѯҳ��
function showDoctor(tCode, tName) {
	var strUrl = "LLColQueryDoctorMain.jsp?codeno=" + tCode + "&codename="
			+ tName;
	openPage(strUrl);
}

//���Ӻ˱������ѯҳ��
function showTransfer(tCode,tName){
	var strUrl = "LLColQueryTransferMain.jsp?codeno=" + tCode +"&codename=" + tName;
	openPage(strUrl);
}

//��ʾҽԺ��ѯҳ��
function showHospital(tCode, tName) {
	var strUrl = "LLColQueryHospitalInput.jsp?codeno=" + tCode + "&codename=" + tName;
	openPage(strUrl);
}

//����ȫ����ť���û����� tFlag=true ���� tFlag=false ����
function setAllButton(tFlag){
	var elementsNum = 0;// FORM�е�Ԫ����
	for (elementsNum = 0; elementsNum < fm.elements.length; elementsNum++) {
		if (fm.elements[elementsNum].type == "button") {
			fm.elements[elementsNum].disabled = tFlag;
		}
	}
}

//����ҳ��
function openPage(strUrl){
	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	
	var win = window.open(strUrl,"",'top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	win.resizeTo(intPageWidth,intPageHeight);
}

//�����û����У��
function userChange(activityid,processid,clmNo,user){
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPubFunSql");
	mySql.setSqlId("LLClaimPubFunSql14");
	mySql.addSubPara(clmNo);
	mySql.addSubPara(activityid);
	mySql.addSubPara(processid);
	var nowUser = easyExecSql(mySql.getString());
	if(nowUser){
		if(user==nowUser){
			return false;
		}
	}
	alert("�����û��Ѹı䣬�����µ�¼�������");
	return true;
}

/**
 * ������������������������������������������������������������������������������������������������������
 * mulline���򷽷� ��������BEGIN
 * @param tsortturnPage
 * @param i
 * ������������������������������������������������������������������������������������������������������
 */
function gridOrderByName(colName,cObjInstance){
	var tObjInstance = cObjInstance;
	var sortTurnPage = tObjInstance.SortPage;
	try{
		if(sortTurnPage == null || sortTurnPage == "") throw "init";
		for(i=1;i<tObjInstance.arraySaveOra.length;i++){
			if(colName == tObjInstance.arraySaveOra[i][0]){sortData(sortTurnPage,i);break;}
		}
	}catch(ex){
		switch (ex){
			case "init":alert("���Ȳ�ѯ��");break;
			default:alert("�� MulLine.js --> OrderByName �����з����쳣��" +  ex);break;
		}
	}
}
function sortData(tsortturnPage,i){
    var sortturnPage =tsortturnPage;
    allowsortcol=i-1;//Ϊʲô������pageȡ�ã���������turnpageclass����...?
    if(sortturnPage.sortdesc[allowsortcol]!="asc"){
        sortturnPage.arrDataCacheSet = sortturnPage.arrDataCacheSet.sort(sortDataParam);
        sortturnPage.sortdesc[allowsortcol]="asc";
        for(var n=0;n<sortturnPage.pageDisplayGrid.colCount;n++){
            if(n!=allowsortcol) sortturnPage.sortdesc[n]="desc";
        }
    }else{
        sortturnPage.arrDataCacheSet = sortturnPage.arrDataCacheSet.reverse();
        for(var n=0;n<sortturnPage.pageDisplayGrid.colCount;n++){
            if(n!=allowsortcol) sortturnPage.sortdesc[n]="desc";
        }
    }
    //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
    //���ò�ѯ��ʼλ��
    sortturnPage.pageIndex = 0;
    //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
    var arrDataSet = sortturnPage.getData(sortturnPage.arrDataCacheSet, sortturnPage.pageIndex, sortturnPage.pageLineNum);
    //����MULTILINE������ʾ��ѯ���
    displayMultiline(arrDataSet, sortturnPage.pageDisplayGrid, sortturnPage);
    //�����Ƿ���ʾ��ҳ��ť ,���������ҳ add by jnn
    //if (cTurnPage.queryAllRecordCount > cTurnPage.pageLineNum)
    if(sortturnPage.showTurnPageDiv==1){
        try{
            window.document.all(sortturnPage.pageDivName).style.display = "";
        }
        catch (ex) {}
    }else{
    	try{
            window.document.all(sortturnPage.pageDivName).style.display = "none";
        }
        catch (ex) {}
    }
    if(sortturnPage.showTurnPageDiv==1){
		try{
			sortturnPage.pageDisplayGrid.setPageMark(sortturnPage);
		}
		catch(ex){}
  	}
}

function sortDataParam(x,y){
	var sort1 = x[allowsortcol];
	var sort2 = y[allowsortcol];
	if(!isNaN(sort1) && !isNaN(sort2)
			&& sort1.split('.')[0].length != sort2.split('.')[0].length){
		if (sort1.split('.')[0].length > sort2.split('.')[0].length){
			for(var i=0;i<(sort1.split('.')[0].length-sort2.split('.')[0].length);i++){
				sort2 = "0" + sort2;
			}
	    }else{
	    	for(var i=0;i<(sort2.split('.')[0].length-sort1.split('.')[0].length);i++){
	    		sort1 = "0" + sort1;
			}
	    }
	}
    if (sort1 > sort2){
        return 1;
    }else if (sort1 < sort2){
        return -1;
    }else{
        return 0;
    }
    //���ݶ�ά����ĵ����еĵ�һ����ĸ��ASCII������������
}

/**
 * ������������������������������������������������������������������������������������������������������
 * END
 * ������������������������������������������������������������������������������������������������������
 */

//�ı���һ��ָ�����ڵ�Ԫ�ص�class  
/** 
 * tElement:��Ԫ��  
 * tElementName����Ԫ��Name 
 * tElementClass����Ԫ�ظı���className 
 */
function setPNodeClassByName(tElement,tElementName,tElementClass){
	//�������ѭ��10��
	for(var i=0;tElement.parentNode != null || i < 10;i++){
		tElement = tElement.parentNode;
		if(tElement.nodeName == tElementName){
			tElement.className = tElementClass;
			return;
		}
	}
}

//У���˺źϷ�
function checkAcc(tEle){
	if(isnull(tEle.value)){
		return;
	}
	if(!/^\d{16,19}$/.test(tEle.value)){
		alert("�˺�����������������룡");
		tEle.value = "";
		tEle.focus();
	}
}

//����ת��Ϊ���ַ���
function nullToEmpty(string){
	if ((string == "null") || (string == "undefined")){
		string = "";
	}
	return string;
}

//���ڿؼ���ʾ
document.getElementsByClassName = function(className, oBox) {
	// �����ڻ�ȡĳ��HTML�����ڲ�����ĳһ�ض�className������HTMLԪ��
	this.d = oBox || document;
	var children = this.d.getElementsByTagName('*') || document.all;
	var elements = new Array();
	for (var ii = 0; ii < children.length; ii++) {
		var child = children[ii];
		var classNames = child.className.split(' ');
		for (var j = 0; j < classNames.length; j++) {
			if (classNames[j] == className) {
				elements.push(child);
				break;
			}
		}
	}
	return elements;
}

function formatDatePicker(element){ 
	formatDate(element);
}
function formatDate(element){
    try{
        if (element != null){
            var sCurrentValue = element.value;
            if (sCurrentValue != null && sCurrentValue != "") {
                if ((sCurrentValue.length != 8 && sCurrentValue.length != 10) || (!isDate(sCurrentValue) && !isDateI(sCurrentValue) && !isDateN(sCurrentValue))){
                    var oldClass = element.className;
                    element.className = "warndate";
                    alert("���ڸ�ʽ���󣬱�׼���ڸ�ʽ��YYYY-MM-DD");
                    element.className = oldClass;
                    element.value = "";
                    element.focus();
                }
                else if (isDateI(sCurrentValue))
                {
                    //���ڸ�ʽ yyyy/mm/dd
                    element.value = replace(sCurrentValue, "/", "-");
                    if (!isDate(element.value)) {
                        var oldClass = element.className;
                        element.className = "warndate";
                        alert("���ڸ�ʽ���󣬱�׼���ڸ�ʽ��YYYY-MM-DD");
                        element.className = oldClass;
                        element.value = "";
                        element.focus();
                    }
                }
                else if (isDateN(sCurrentValue)){
                    element.value = sCurrentValue.substring(0, 4) + "-" + sCurrentValue.substring(4, 6) + "-" + sCurrentValue.substring(6, 8);
                    if (!isDate(element.value)) {
                        var oldClass = element.className;
                        element.className = "warndate";
                        alert("���ڸ�ʽ���󣬱�׼���ڸ�ʽ��YYYY-MM-DD");
                        element.className = oldClass;
                        element.value = "";
                        element.focus();
                    }
                }
            }
        }
    }
    catch (ex) {}
}

if (window.attachEvent){ 
	window.attachEvent('onload',initDatePicker);
}else{
	window.addEventListener('load',initDatePicker,false);
}

function initDatePicker(){
	if(fm.all){
		return;
	}
	var DatePickers = document.getElementsByClassName('multiDatePicker');
	var i=0;  
	while(i<DatePickers.length){
		DatePickers[i].setAttribute("onblur", "formatDatePicker(this);");
		if (DatePickers[i].getAttribute("DatePickerFlag") == null){
			DatePickers[i].setAttribute("DatePickerFlag", "1");
			DatePickers[i].maxLength=10;
			DatePickers[i].insertAdjacentHTML("afterEnd", "<img   src='../common/images/calendar.gif' vspace='1' onclick='calendar(" + DatePickers[i].name + ",\"" + DatePickers[i].id + "\")'>");
		}
		DatePickers[i].className = "coolDatePicker"; 
		i++;
	}
	var DatePickers = document.getElementsByClassName('coolDatePicker');
	var i=0;  
	while(i<DatePickers.length){
		DatePickers[i].setAttribute("onblur", "formatDatePicker(this);");
		if (DatePickers[i].getAttribute("DatePickerFlag") == null){
			DatePickers[i].setAttribute("DatePickerFlag", "1");
			DatePickers[i].maxLength=10;
			DatePickers[i].insertAdjacentHTML("afterEnd", "<img   src='../common/images/calendar.gif' vspace='1' onclick='calendar(" + DatePickers[i].name + ",\"" + DatePickers[i].id + "\")'>");
		}
		i++;
	}
}

//�ύ���ݺ󷵻���ʾ��
function myPromptBox(FlagStr,content,Flag){
	var dheight = 0;
	var dWidth = 0;
	
 	var sh=document.body.scrollHeight;//�������ĸ߶�
	var ch =document.body.clientHeight;	
	dheight = sh>ch? sh:ch;
	
	var sw = document.body.scrollWidth;
	var cw = document.body.clientWidth;
	dWidth = sw>cw? sw:cw; 
	
	var bDiv = document.createElement('div'); 
	bDiv.id = 'promptmask';
	bDiv.className = "datagrid-mask";
	bDiv.style.width = dWidth;
	bDiv.style.height = dheight;
	bDiv.style.position = "absolute";
	bDiv.style.left = "0";
	bDiv.style.top = "0";
	bDiv.style.display = "block";
	bDiv.style.background = "#ccc";
	bDiv.style.opacity = "0.3";
	bDiv.style.filter = "alpha(opacity=30)";
	
	document.body.appendChild(bDiv); 
	
	var mDiv = document.createElement('div'); 
	mDiv.id = 'promptmsg';
	mDiv.className = "promptmsg";
	mDiv.style.width = 400;
	mDiv.style.height = 200;
	mDiv.style.top = (ch-200)/2;
	mDiv.style.left = (cw-500)/2;
	mDiv.style.display = "block";
	mDiv.style.position = "fixed";
	if(fm && fm.all){
		mDiv.style.position = "absolute";
	}
	mDiv.style.padding = "0px 0px 10px 0px";
	mDiv.style.border = "2px solid #6593CF";
	mDiv.style.color = "#222";
	mDiv.style.background = "#fff";
//	mDiv.style.cursor = "wait";
	mDiv.style.textAlign = "center";
	mDiv.innerHTML = "<div style='height:55;background:#9cf;text-align:right;padding-top:7;padding-right:8;'>" +
			"<img src='../common/images/hidemenu.gif' onClick='hidePromptBox();' style='cursor:pointer;'></img></div>" +
			"<div style='padding:8px 10px 5px 10px;word-break: break-all;: break-word;font-size:14px;max-height:85;'>"+content+"</div>" +
			"<div style='position:absolute; bottom:15px;left:165;'><img src='../common/images/butOk.gif' onClick='hidePromptBox();afterOk(\""+FlagStr+"\",\""+Flag+"\");' style='cursor:pointer'></img></div>";
	
	document.body.appendChild(mDiv); 
}

function hidePromptBox(){
	document.body.removeChild(promptmask);
	document.body.removeChild(promptmsg);
}
function afterOk(FlagStr,Flag){
}

function llLockPage(msg){ 
	var loadMsg = "Loading........";
	if(msg != null && msg != ""){
			loadMsg = msg;
	}
	var dheight = 0;
	var dWidth = 0;
 	var sh=document.body.scrollHeight;//�������ĸ߶�
	var ch =document.body.clientHeight;	
	if(sh > ch){
		dheight=sh;
	}else{
		dheight=ch;
	}
	var sw = document.body.scrollWidth;
	var cw =document.body.clientWidth;
	if(sw >cw ){
		dWidth=sw; 
	}else{
		dWidth=cw;
	}
	var bDiv = document.createElement('div'); 
	bDiv.id = 'datagridMask';
	bDiv.className = "datagrid-mask";
	bDiv.style.width = dWidth;
	bDiv.style.height = dheight;
	bDiv.style.display = "block";
	bDiv.style.position = "absolute";
	bDiv.style.left = "0";
	bDiv.style.top = "0";
	bDiv.style.background = "#ccc";
	bDiv.style.opacity = "0.3";
	bDiv.style.filter = "alpha(opacity=30)";
	
	document.body.appendChild(bDiv); 
	
	var mDiv = document.createElement('div');
	mDiv.id = 'datagridMaskMsg';
	mDiv.className = "datagrid-mask-msg";
	mDiv.style.position = "absolute";
	mDiv.style.cursor1 = "wait";
	mDiv.style.top = (ch-16)/2;
	mDiv.style.left = (cw-250)/2;
	mDiv.style.display = "block";
	mDiv.style.position = "fixed";
	if(fm && fm.all){
		mDiv.style.position = "absolute";
	}
	mDiv.style.width = "auto";
	mDiv.style.height = "16px";
	mDiv.style.padding = "12px 5px 10px 30px";
	mDiv.style.background = "#fff url('images/pagination_loading.gif') no-repeat scroll 5px 10px";
	mDiv.style.border = "2px solid #6593CF";
	mDiv.style.color = "#222";
	mDiv.innerHTML = msg;
	
	document.body.appendChild(mDiv); 
} 

function llUnLockPage(){ 
	document.body.removeChild(datagridMask);
	document.body.removeChild(datagridMaskMsg);
}
