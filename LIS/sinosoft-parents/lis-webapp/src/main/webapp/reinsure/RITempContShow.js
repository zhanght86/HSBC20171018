//���ļ��а����ͻ�����Ҫ����ĺ������¼�

//�������ƣ�RITempContShow.js
//�����ܣ��ٷ���˲�ѯ
//�������ڣ�2011-11-09
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
window.onfocus=myonfocus;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();


//��ѯ��ť
function button134(){
	var sql = "select  a.Proposalgrpcontno,a.Proposalcontno,decode(a.State,'01','"+"�ٷַ���"+"','02','"+"�ٷ����"+"','03','"+"�ٷִ���"+"','04','"+"�������"+"'),b.Riskcode,(select x.Nameen from lcinsured x where x.Insuredno=b.Insuredno and x.Contno=b.Contno),b.Prem,b.Amnt,b.Riskamnt,a.Exetype,a.State,a.SerialNo " +
			" from RIGrpState a,lcpol b " +
			" where a.Proposalcontno=b.contno " +
			" and a.state='04' " +
			" and a.Proposalcontno like '"+fm.Polno.value+"%' ";
	turnPage.queryModal(sql, IndTempToalListGrid);
}

function  listSelect(){
	var tSel = IndTempToalListGrid.getSelNo();	
	var SerialNo = IndTempToalListGrid.getRowColData(tSel-1,11);
	mSQL = " select b.Contno A1,a.ProposalNo A2,(select x.Nameen from lcinsured x where x.Insuredno=b.Insuredno and x.Contno=b.Contno) A3,b.RiskCode A4,c.dutycode A5,c.Prem A6,c.Amnt A7,c.RiskAmnt A8,decode(a.State, '02', '"+"���ٷ����"+"', '03', '"+"�ٷ�"+"', '04', '"+"�ٷִ������"+"') A9,a.State A10,(select x.Ricontno  from RITempContLink x where x.ProposalNo=a.Proposalno and x.Dutycode=a.Dutycode ) A11,(select x.Ripreceptno  from RITempContLink x where x.ProposalNo=a.Proposalno and x.Dutycode=a.Dutycode ) A12" +
			" from RIDutyState a, lcpol b, lcduty c " +
			" where 1=1 " +
			" and a.Proposalno=b.polno " +
			" and a.Proposalno=c.polno " +
			" and a.Dutycode=c.Dutycode " +
			" and a.SerialNo='"+SerialNo+"'" +
		    " and exists(select 'X'from RIGrpState d where d.SerialNo='"+SerialNo+"' and d.state='04')";
	turnPage2.queryModal(mSQL, IndTempListGrid);
}
//�ύǰ��У�顢����  
function beforeSubmit(){
  //��Ӳ��� 
}

//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus(){
 if(showInfo!=null){
   try{
     showInfo.focus();  
   }
   catch(ex){
     showInfo=null;
   }
 }
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content ){
  showInfo.close();
  if (FlagStr == "Fail" ){             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + encodeURI(encodeURI(content)) ;  
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else{ 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + encodeURI(encodeURI(content)) ;  
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
    //ִ����һ������
  }
}

function showDiv(cDiv,cShow){
  if (cShow=="true"){
    cDiv.style.display="";
  }
  else{
    cDiv.style.display="none";  
  }
}
