var SQL = "";
var turnPage = new turnPageClass();
//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow){
  if (cShow=="true"){
    cDiv.style.display="";
  }
  else{
    cDiv.style.display="none";
  }
}

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug){
	if(cDebug=="1"){
		parent.fraMain.rows = "0,0,0,0,*";
	}
	else{
		parent.fraMain.rows = "0,0,0,0,*";
	}
	parent.fraMain.rows = "0,0,0,0,*";
}
// ��ѯ��ť
function easyQuery(){	
	if(fm.StartDate.value == "" ||fm.StartDate.value == null){
		alert("��ѡ����ʼ���ڣ�");
		return false;
	}
	if(fm.EndDate.value == "" ||fm.EndDate.value == null){
		alert("��ѡ���ֹ���ڣ�");
		return false;
	}	
	document.all("query").disabled="true";
	document.all("toExcel").disabled="";	
	var StartDate = fm.StartDate.value;   //��ʼʱ��
	var EndDate = fm.EndDate.value; 	  //��ֹʱ��	
	var ManageCom = fm.ManageCom.value;	  //��������	
	var ContPlanCode = fm.ContPlanCode.value; //�ײͼƻ�����
	var ManageCom = fm.ManageCom.value;	
	var flag=compareDate(StartDate,EndDate);
	if(flag==1){		
		alert("��ʼ�������ڽ�ֹ����");
		return false
	}
	
	SQL=" select (select Name from LDCom where ComCode=a.ManageCom)�����������, "
       	+" a.ManageCom �����������, "
       	+" a.GrpContNo �����ͬ��, "
       	+" a.PrtNo Ͷ������, "
       	+" a.InsuredName ����������, "
       	+" a.InsuredSex �Ա�, "
       	+" a.InsuredBirthday ��������, "
       	+" (select ContPlanCode from LCInsured where ContNo=a.ContNo and InsuredNo=a.InsuredNo) �ײͼƻ�����, "
       	+" a.Prem ����, "
       	+" a.mult ����, "
       	+" a.CValiDate ��Ч����, "
       	+" a.SignDate ǩ������ "       	
		+" from LCCont a,lcinsured b where a.contno =b.contno "
		+" and a.ContType='2' "
		+" and a.AppFlag in('1','4')"
		+" and a.SignDate between date'"+StartDate+"' and date'"+EndDate+"' "
		+" and exists(select 'X' from LCContPlan where GrpContNo=a.GrpContNo and PlanType='1' "+getWherePart('ContPlanCode','ContPlanCode')+") "
		+" and contplancode=b.contplancode"
		;
		
	if(ManageCom!=null && ManageCom!=""){
		SQL+=" and a.ManageCom like '"+ManageCom+"%' ";
	}
	fm.sql.value=SQL;	
	turnPage.queryModal(SQL, PlanInfoGrid);	
	document.all("query").disabled="";
	if(PlanInfoGrid.mulLineCount<1){
		initPlanInfoGrid();
  	  	alert("û�в�ѯ�����������ļ�¼��");
  	  	return false; 
	}
}

function queryContPlan(){
   var tManageCom =fm.ManageCom.value;
   var tSQL =" select ContPlanCode, ContPlanName from LDPlan where 1=1 and ContPlanCode <> '00' "
   			+" and managecom like '"+manageCom+"%%'"
   			+getWherePart('managecom','ManageCom','like')
   			+" order by ContPlanCode";
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(tSQL, 1, 0, 1);
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
    document.all("ContPlanCode").CodeData=tCodeData;
	
}

//������EXCEL
function ToExcel() {
	if(PlanInfoGrid.mulLineCount<1){		
  	  	alert("û�пɵ����ļ�¼��");
  	  	return false;
	}
	document.all("toExcel").disabled="true";
	fm.action="./GrpPlanInfoToExcel.jsp";
	fm.target="fraSubmit";	
	document.getElementById("fm").submit(); //�ύ	
}