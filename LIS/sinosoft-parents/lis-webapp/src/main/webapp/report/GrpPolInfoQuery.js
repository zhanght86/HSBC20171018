var SQL = "";
var turnPage = new turnPageClass();
var showInfo;
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
	var StartDate = fm.StartDate.value;   			//��ʼʱ��
	var EndDate = fm.EndDate.value; 	  			//��ֹʱ��
	var RiskType = fm.RiskType.value;   			//�������
	var Blacklist = fm.Blacklist.value;           	//���������
	var InsuranceMoney = fm.InsuranceMoney.value;	//��ȫ�ӷ�
	var ManageCom = fm.ManageCom.value;
	var flag=compareDate(StartDate,EndDate);
	
	if(flag==1){
		alert("��ʼ�������ڽ�ֹ����");
		return false;
	}
	
	var tSQL = " and not exists(select 'X' from LPEdorItem where ContNo=b.ContNo and EdorType='NI' and EdorState='0') ";
	if(InsuranceMoney=="1"){
		tSQL = "";
	}
	SQL="select (select Name from LDCom where ComCode=a.ManageCom) �����������,"
        +" a.ManageCom �����������, "
        +" a.SaleChnl ��������, "
        +" a.AgentType ������������, "
        +" a.GrpContNo �����ͬ��, "
        +" a.PrtNo Ͷ������,a.grpname Ͷ����λ����, "
        +" a.RiskCode ���ִ���, "
        +" (select sum(Amnt) from LCPol b where b.GrpPolNo=a.GrpPolNo and AppFlag in('1','4')"+tSQL+" ) �ܱ���, "
        +" (select sum(Prem) from LCPol b where b.GrpPolNo=a.GrpPolNo and AppFlag in('1','4')"+tSQL+"  ) �ܱ���, "
        +" (select sum(InsuredPeoples) from LCPol b where b.GrpPolNo=a.GrpPolNo and AppFlag in('1','4')"+tSQL+"  ) ������������, "
        +" a.CValiDate ��Ч��, "
        +" (select SignDate from LCGrpCont where GrpContNo=a.GrpContNo) ǩ����, "
        +" a.AgentCode �����˱���, "
        +" (select Name from LAAgent where AgentCode=a.AgentCode) ����������, "
        +" (select RiskType from LMRiskApp where RiskCode=a.RiskCode) �������, "
        +" (select nvl(BlacklistFlag,'0') from LDGrp where CustomerNo=a.CustomerNo) ��������� "
		+" from LCGrpPol a where 1=1 "
		+" and a.AppFlag in ('1','4') "	
		+ getWherePart('a.AgentCode','AgentCode')		
		+ getWherePart('a.AgentType','AgentType')		
		+ getWherePart('a.RiskCode','RiskCode')
		+ getWherePart('a.SaleChnl','SaleChnl');
	
	if(ManageCom!=null && ManageCom!=""){
		SQL+=" and a.ManageCom like '"+ManageCom+"%' ";
	}
	 if(document.all('GrpName').value!=null&&document.all('GrpName').value!="")
	{
		SQL = SQL+" and grpname like '%%"+document.all('GrpName').value+"%%' "
	}
	if(RiskType!=null && RiskType!=""){
		SQL+=" and exists(select 'X' from LMRiskApp where RiskCode=a.RiskCode and RiskType='"+RiskType+"') ";
	}	
	if(Blacklist!=null && Blacklist!=""){
		SQL+=" and exists(select 'X' from LDGrp where CustomerNo=a.CustomerNo and nvl(BlacklistFlag,'0')='"+Blacklist+"') "
	}
	SQL+=" and exists(select 'X' from LCGrpCont where GrpContNo=a.GrpContNo and SignDate between date '"+StartDate+"' and '"+EndDate+"' )";
	
	fm.sql.value=SQL;
	
	turnPage.queryModal(SQL, PolInfoGrid);	  
	document.all("query").disabled="";
	if(PolInfoGrid.mulLineCount<1){
		initPolInfoGrid();
  	  	alert("û�в�ѯ�����������ļ�¼��");
  	  	return false; 
	}
}
//������EXCEL
function ToExcel() {  
	if(PolInfoGrid.mulLineCount<1){		
  	  	alert("û�пɵ����ļ�¼��");
  	  	return false;
	} 
	document.all("toExcel").disabled="true";	
	fm.action="./GrpPolInfoToExcel.jsp";
	fm.target="fraSubmit";	
	document.getElementById("fm").submit(); //�ύ	
}
// �ύ��ť
function chageState(){
	var i = 0;
	var flag = 0;
	for( i = 0; i < PolInfoGrid.mulLineCount; i++ )	{
		if( PolInfoGrid.getChkNo(i) == true ){
			flag = 1;
			break;
		}
	}
	if( flag == 0 ){
		alert("����ѡ������һ����¼��");
		return false;
	}
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	//disable��ӡ��ť����ֹ�û��ظ��ύ
	document.all("chageButton").disabled=true;
	document.getElementById("fm").submit();	
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content ){
	showInfo.close();window.focus();
	//���۴�ӡ�����Σ������¼����ӡ��ť
	document.all("chageButton").disabled=false;
	if (FlagStr == "Fail" ){
		//���ʧ�ܣ��򷵻ش�����Ϣ
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");			
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		easyQuery();
	}
	else{
		//����ύ�ɹ�����ִ�в�ѯ����
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		easyQuery();
	}
}
