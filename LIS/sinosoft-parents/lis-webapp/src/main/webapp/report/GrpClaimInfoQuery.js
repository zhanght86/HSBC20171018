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
	if(verifyInput()==false) return false;		
	document.all("query").disabled="true";
	document.all("toExcel").disabled="";
	
	var StartDate = fm.StartDate.value;   	//��ʼʱ��
	var EndDate = fm.EndDate.value; 	  	//��ֹʱ��	
	var GrpName=fm.GrpName.value; 			//Ͷ����λ����	
	var Blacklist=fm.Blacklist.value; 		//Ͷ����λ���������
	var flag=compareDate(StartDate,EndDate);
	var ManageCom = fm.ManageCom.value;
	
	if(flag==1){		
		alert("��ʼ�������ڽ�ֹ����");
		return false;
	}	
	SQL=" select " 
       +" a.ManageCom �����������, "
       +" (select Name from LDCom where ComCode=a.ManageCom) �����������, "
       +" a.GrpContNo �ŵ���ͬ��, "
       +" a.RiskCode ���ִ���, "
       +" (select RiskName from LMRiskApp where RiskCode = a.RiskCode) ��������, "
       +" a.Peoples2 ����������, "       
       +" a.CustomerNo Ͷ����λ����, "
       +" (select GrpName from LCGrpCont where GrpContNo=a.GrpContNo) Ͷ����λ����, "
       +" (select CodeName from LDCode where CodeType='businesstype' and Code=(select BusinessType from LCGrpCont where GrpContNo=a.GrpContNo)) ��ҵ�������, "
       +" a.Prem �ۼƱ���, "       
       +" (select sum(Pay) from LJAGetClaim where GrpPolNo = a.GrpPolNo and GrpContNo=a.GrpContNo and RiskCode=a.RiskCode) �ۼ��⸶���, "
       +" a.CValiDate ��Ч����, "
       +" (select nvl(BlackListFlag,'0') from LDGrp where CustomerNo=(select AppntNo from LCGrpCont where GrpContNo=a.GrpContNo)) Ͷ����λ��������� "
       +" from LCGrpPol a where 1=1 and a.AppFlag in('1','4') "
       +" and exists(select 'X' from LJAGetClaim where GrpPolNo=a.GrpPolNo) "
       + getWherePart('a.GrpContNo','GrpContNo')
       + getWherePart('a.RiskCode','RiskCode');
     
    if(ManageCom!=null && ManageCom!=""){
		SQL+=" and a.ManageCom like '"+ManageCom+"%' ";
	}
      
    if(EndDate!=null && EndDate!=""){
		SQL+=" and a.CValiDate <= date'"+EndDate+"' ";	
	}	
	if(StartDate!=null && StartDate!=""){
		SQL+=" and a.CValiDate >= date'"+StartDate+"' ";	
	}
	if(GrpName!=null && GrpName!=""){
		SQL+=" and (select GrpName from LCGrpCont where GrpContNo=a.GrpContNo) like '%"+GrpName+"%' ";	
	}
	if(Blacklist!=null && Blacklist!=""){
		SQL+=" and exists(select 'X' from LDGrp where CustomerNo=(select AppntNo from LCGrpCont where GrpContNo=a.GrpContNo) and nvl(BlackListFlag,'0')='"+Blacklist+"') ";
	}    
	
	SQL+=" order by a.ManageCom,a.CustomerNo,a.GrpContNo,a.RiskCode ";
	
	fm.sql.value=SQL;
	
	turnPage.queryModal(SQL, ClaimInfoGrid);
	
	document.all("query").disabled="";
	if(ClaimInfoGrid.mulLineCount<1){
		initClaimInfoGrid();
  	  	alert("û�в�ѯ�����������ļ�¼��");
  	  	return false; 
	}	
}

//������EXCEL
function ToExcel() {
	if(ClaimInfoGrid.mulLineCount<1){		
  	  	alert("û�пɵ����ļ�¼��");
  	  	return false;
	}
	document.all("toExcel").disabled="true";
	fm.action="./GrpClaimInfoToExcel.jsp";
	fm.target="fraSubmit";	
	document.getElementById("fm").submit(); //�ύ
}