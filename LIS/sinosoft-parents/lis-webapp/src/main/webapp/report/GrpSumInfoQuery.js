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
	document.all("query").disabled="true";
	document.all("toExcel").disabled="";
	var StartDate = fm.StartDate.value;   //��ʼʱ��
	var EndDate = fm.EndDate.value; 	  //��ֹʱ��	
	var flag=compareDate(StartDate,EndDate);  //�Ƚ�����
	var ManageCom = fm.ManageCom.value;	
	if(ManageCom==null||ManageCom==""){
	  alert("��¼����������");
	  document.all("query").disabled="";
	  return false;
	}
	if(StartDate==null||StartDate==""||EndDate==null||EndDate==""){
	   alert("��¼�뿪ʼ���ںͽ������ڣ�");
	   document.all("query").disabled="";
	   return false;
	}
	if(flag==1){		
		alert("��ʼ�������ڽ�ֹ����");
		document.all("query").disabled="";
		return false;
	}	
	SQL="select a.RiskCode ���ִ���,"
        +"a.ManageCom �����������,"
        +"(select Name from LDCom where ComCode=a.ManageCom) �����������,"
        +"count(distinct a.GrpContNo) ��������,"
        +"sum(InsuredPeoples) �б�����,"
        +"sum(Amnt) �ܱ���,"
        +"sum(Prem) �ܱ��� "
		+"from LCPol a where 1=1 "
		+"and a.ContType='2' "
		+"and a.AppFlag in('1','4')";		
		
	if(ManageCom!=null && ManageCom!=""){
		SQL+=" and a.ManageCom like '"+ManageCom+"%' ";
	}
	if(EndDate!=null && EndDate!=""){
		SQL+="and a.SignDate <= date'"+EndDate+"' ";	
	}
	if(StartDate!=null && StartDate!=""){
		SQL+="and a.SignDate >= date'"+StartDate+"' ";	
	}	
	SQL+=" group by a.RiskCode,a.ManageCom ";	
	fm.sql.value=SQL;	
	turnPage.queryModal(SQL, SumInfoGrid);	
	document.all("query").disabled="";
	if(SumInfoGrid.mulLineCount<1){
		initSumInfoGrid();
  	  	alert("û�в�ѯ�����������ļ�¼��");
  	  	document.all("query").disabled="";
  	  	return false; 
	}	
}
//������EXCEL
function ToExcel() {
	if(SumInfoGrid.mulLineCount<1){		
  	  	alert("û�пɵ����ļ�¼��");
  	  	return false;
	}
	document.all("toExcel").disabled="true";
	fm.action="./GrpSumInfoToExcel.jsp";
	fm.target="fraSubmit";	
	document.getElementById("fm").submit(); //�ύ	
}