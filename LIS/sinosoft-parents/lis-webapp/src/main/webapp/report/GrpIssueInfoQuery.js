var SQL = "";
var turnPage = new turnPageClass();
//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow){
  if (cShow=="true") {
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
	document.all("query").disabled="true";
	document.all("toExcel").disabled="";
	var StartDate = fm.StartDate.value;   //��ʼʱ��
	var EndDate = fm.EndDate.value; 	  //��ֹʱ��	
	var ManageCom = fm.ManageCom.value;	
	var flag=compareDate(StartDate,EndDate);
	if(flag==1){		
		alert("��ʼ�������ڽ�ֹ����");
		return false;
	}
		
	SQL=" select a.IsueManageCom �����������,"
        +" a.GrpContNo �����ͬ��,"
        +" a.ProposalGrpContNo Ͷ������,"
        +" a.IssueType ���������,"
        +" a.IssueCont ���������,"
        +" a.Operator ¼����,"
   		+" (select DefaultOperator from LBMission where ProcessID='0000000004' and "
		+" ActivityID='0000002001' and MissionProp1=a.ProposalGrpContNo and SubMissionID='1') ������Ա,"                
    	+" a.MakeDate ¼������,"      
    	+" (select MakeDate from LBMission where ProcessID='0000000004' and ActivityID='0000002001' "
		+" and MissionProp1=a.ProposalGrpContNo and SubMissionID='1') ��������, "		
    	+" (select SignDate from LCGrpCont where GrpContNo=a.GrpContNo) ǩ������,"
        +" a.OperatePos ����λ��,"
        +" a.BackObj ���ض���"
		+" from LCGrpIssuePol a where 1=1 "
		+" and exists(select 'X' from LCGrpCont where GrpContNo=a.GrpContNo and AppFlag<>'0')";
		
    if(ManageCom!=null && ManageCom!=""){
		SQL+=" and a.ManageCom like '"+ManageCom+"%' ";
	}
	
	if(EndDate!=null && EndDate!=""){
		SQL+="and a.MakeDate <= date'"+EndDate+"' ";	
	} 
	if(StartDate!=null && StartDate!=""){
		SQL+="and a.MakeDate >= date'"+StartDate+"' ";	
	}  
	fm.sql.value=SQL;
	turnPage.queryModal(SQL, IssueInfoGrid);	
	document.all("query").disabled="";
	if(IssueInfoGrid.mulLineCount<1){
		initIssueInfoGrid();
  	  	alert("û�в�ѯ�����������ļ�¼��");
  	  	return false; 
	}	
	
}
//������EXCEL
function ToExcel() {
	if(IssueInfoGrid.mulLineCount<1){		
  	  	alert("û�пɵ����ļ�¼��");
  	  	return false;
	}
	document.all("toExcel").disabled="true";
	fm.action="./GrpIssueInfoToExcel.jsp";
	fm.target="fraSubmit";	
	document.getElementById("fm").submit(); //�ύ	
}