//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var showInfo;
var mDebug="0";
var InputFlag=true;
var addFlag = false; //�Ƿ���Ӧ�ռ�¼���Ƿ��������
var contFlag = false; //�ж��Ƿ��б�����¼���Ƿ��������
var edorFlag = false; //�ж��Ƿ��б�����¼���Ƿ��������
var clmFlag = false; //�ж��Ƿ��б�����¼���Ƿ��������
var urgeFlag = false; //�ж��Ƿ��б�����¼���Ƿ��������

var ImportPath; 

//�����Ӷ���ִ�еĴ���
var addAction = 0;
//�ݽ����ܽ��
var sumTempFee = 0.0;
//�ݽ�����Ϣ�н��ѽ���ۼ�
var tempFee = 0.0;
//�ݽ��ѷ�����Ϣ�н��ѽ���ۼ�
var tempClassFee = 0.0;
//����ȷ���󣬸ñ�����Ϊ�棬�������һ��ʱ�������ֵ�Ƿ�Ϊ�棬Ϊ�������Ȼ���ٽ��ñ����ü�
var confirmFlag=false;

var arrCardRisk;

var todayDate = new Date();
var sdate = todayDate.getDate();
var smonth= todayDate.getMonth() + 1;
var syear= todayDate.getYear();

var mySql=new SqlClass();
mySql.setJspName("../../finfee/FinFeeInputSql.jsp");
//�ύ�����水ť��Ӧ����
function submitForm(){
	
	if(FinFeeGrid.mulLineCount==0){
		alert(" ��¼������շ���Ϣ��");
		return false;
	}
	if(TempToGrid.mulLineCount==0){
		alert(" �����ҵ�������Ϣ��");
		return false;
	}
	if(!parseFloat(fm.OperateSub.value)==0){
		alert("�����շѽ����ҵ����úϼƲ�ƥ�䣡");
		return false;
	}
	var ccontno="";
	//У�鱾�ν��ѵı����Ƿ�������Լ¼��ʱ�ı���һ�£�����һ�£��������ʾ��
	//�µ�����
	if(fm.InputNo1.value!=null&&fm.InputNo1.value!="")
	{
		ccontno=fm.InputNo1.value;
		}
	//���ڽ���
	else if(fm.InputNo3.value!=null&&fm.InputNo3.value!="")
	{
		ccontno=fm.InputNo3.value;
		}
	//����Ԥ�ս���
	else if
	(fm.InputNo5.value!=null&&fm.InputNo5.value!="")
	{
		ccontno=fm.InputNo5.value;
		}
	//�����ڽ���
	else if(fm.InputNo22.value!=null&&fm.InputNo22.value!="")
	{
		ccontno=fm.InputNo22.value;
		}
		//��ȫ����
	else if(fm.InputNo13.value!=null&&fm.InputNo13.value!="")
	{
		mySql.setSqlId("TempFeeInputInputSql_EdorCont");
    mySql.addPara('EdorAcceptNo', fm.InputNo13.value);
    var strArray1=easyExecSql(mySql.getString());
	  if(strArray1 != null && strArray1 != "")
	  {
	  	ccontno=strArray1[0][0];
	  	}
		}
		//���⽻��
//  	else if(fm.InputNo12.value!=null&&fm.InputNo12.value!="")
//	  {
//		mySql.setSqlId("TempFeeInputInputSql_ClaimCont");
//    mySql.addPara('ClmNo', fm.InputNo12.value);
//    var strArray1=easyExecSql(mySql.getString());
//	  if(strArray1 != null && strArray1 != "")
//	  {
//	  	ccontno=strArray1[0][0];
//	  	}
//		 }
	if(ccontno!="")
	{
    mySql.setSqlId("TempFeeInputInputSql_cur");
    mySql.addPara('OpeCurrency' , fm.OpeCurrency.value);
    mySql.addPara('ContNo',ccontno);
    var strArray=easyExecSql(mySql.getString());
	  if(strArray != null && strArray != ""){
	  	var Currency = strArray[0][0];
	  	if(Currency != fm.OpeCurrency.value){
	  			alert("���ν�����ѡ�����뱣����ԭ���ֲ�һ�£������ı��ֱ���Ϊ"+Currency+"��������ѡ��")
	  			return false;

	  	}
		}
	}
	fm.OperateType.value="INSERT";
	try {
		if( verifyInput() == true) //&& FinFeeGrid.checkValue("FinFeeGrid")&&MatchInfoGrid.checkValue("MatchInfoGrid")
		{
		  	var i = 0; 
		  	
		  	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"; 
		  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		  	var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
			showInfo.focus(); 
				fm.action = "./TempFinFeeSave.jsp"; 
		  	document.getElementById("fm").submit(); //�ύ
	  }
  } catch(ex){
  	showInfo.close( );
  	alert(ex);
  }
}

//��ӡƱ��
function printInvoice()
{
  window.open("./TempFeeInputPrintMain.jsp");
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, Content)
{
	showInfo.close();
	try {
		if (FlagStr == "Fail" ) {             
	    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + Content ;  
	    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	  } else { 
		  var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + Content;
		  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=350;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
		  clearFormData();
		  initTTempToGrid();
		  initTTempClassToGrid();
		  fmImport.fileName.value="";
		  fmReset();
	  }
  } catch(ex){
  	showInfo.close( );
  	alert(ex);
  }
}

function fmReset(){
	mDebug="0";
	InputFlag=true;
	addFlag = false; //�Ƿ���Ӧ�ռ�¼���Ƿ��������
	contFlag = false; //�ж��Ƿ��б�����¼���Ƿ��������
	edorFlag = false; //�ж��Ƿ��б�����¼���Ƿ��������
	clmFlag = false; //�ж��Ƿ��б�����¼���Ƿ��������
	urgeFlag = false; //�ж��Ƿ��б�����¼���Ƿ��������
	addAction = 0;
	sumTempFee = 0.0;
	tempFee = 0.0;
	tempClassFee = 0.0;
	confirmFlag=false;
	fmImport.fileName.value="";
	clearFormData();
	FinFeeGrid.clearData();
}

function PrintData()
{
	
}

//��Ӳ��񽻷�
function addMul(){
	if(!verifyInput6()){
		return false;
	}
	FinFeeGrid.clearData();
	FinFeeGrid.addOne("FinFeeGrid");
	if(fm.PayMode.value=='1'){
		payCash();
	}
	if(fm.PayMode.value=='2'){
		payCheque();
	}
	if(fm.PayMode.value=='3'){
		payCashCheque();
	}
	if(fm.PayMode.value=='4'){
		payBankCount();
	}
	if(fm.PayMode.value=='5'){
		payTransAccout();
	}
	if(fm.PayMode.value=='6'){
		payPos();
	}
	if(fm.PayMode.value=='9')
	{
		payVoucher();
		}
	sumTempFee = parseFloat(FinFeeGrid.getRowColData(0,3)) * 1000000000;
	
	clearFinInfo();
}

function clearFinInfo(){
	fm.PayMode.value					=	'';
	fm.PayModeName.value			=	'';
	fm.Currency.value					=	'';
	fm.CurrencyName.value			=	'';
	fm.PayFee1.value					=	'';
	fm.PayFee2.value					=	'';
	
	fm.ChequeNo2.value				=	'';
//	fm.ChequeDate2.value			=	'';
	fm.BankCode2Name.value		=	'';
//	fm.BankCode2.value				=	'';
	fm.BankAccNo2.value				=	'';
	fm.AccName2.value					=	'';
	
	fm.AccName3.value					=	'';
//	fm.InBankCode3.value			=	'';
	fm.InBankCode3Name.value	=	'';
	fm.InBankAccNo3.value			=	'';
	fm.PayFee3.value					=	'';
	fm.ChequeNo3.value				=	'';
	fm.ChequeDate3.value			=	'';
//	fm.BankCode3.value				=	'';
	fm.BankCode3Name.value		=	'';
	fm.BankAccNo3.value				=	'';
	fm.AccName3.value					=	'';
	
	fm.PayFee4.value					=	'';
	fm.InBankCode4.value			=	'';
	fm.InBankCode4Name.value	=	'';
	fm.InBankAccNo4.value			=	'';
	fm.ChequeNo4.value				=	'';
	fm.ChequeDate4.value			=	'';
	
	fm.PayFee6.value					=	'';
	fm.InBankCode6.value			=	'';
	fm.InBankAccNo6.value			=	'';
	fm.PayFee7.value					=	'';
	fm.BankCode7.value				=	'';
	fm.BankAccNo7.value				=	'';
	fm.AccName7.value					=	'';
	fm.IDType7.value					=	'';
	fm.IDNo7.value						=	'';
	fm.ChequeNo2.value				=	'';
	fm.ChequeNo6.value				=	'';
	
	fm.PayFee9.value					=	'';
	fm.ChequeNo9.value				=	'';
	fm.ChequeDate9.value			=	'';
	fm.BankCode9Name.value		=	'';
//	fm.BankCode9.value				=	'';
	fm.BankAccNo9.value				=	'';
	fm.AccName9.value					=	'';
	fm.InBankCode9.value			=	'';
	fm.InBankCode9Name.value	=	'';
	fm.InBankAccNo9.value			=	'';
	
	fm.InBankCode6Name.value	=	''; //POS�տ� �տ�������
	                           	
	fm.TempFeeNo1.value				=	'';
	fm.PayName.value					=	''
	
	return true;
}

//�����շ���ϢУ��
function verifyInput6(){
	if((fm.ManageCom.value).length<4)
  {
    alert("�շѻ�����������ֻ����֧����Ӫ���������շ�Ȩ��");
    fm.PayMode.focus();
    return false;
  }
	
	if(TempToGrid. mulLineCount!=0){
		alert("���ȳ���ҵ�������Ϣ�ٽ���ȷ�ϣ�");
		return false;
	}
	
	if(fm.PayMode.value==''||fm.PayMode.value==null){
		alert("��ѡ�񽻷����ͣ�");
		return false;
	}
	if(fm.Currency.value==''||fm.Currency.value==null){
		alert("��ѡ�������Ϣ��");
		return false;
	}
	if(fm.PayMode.value=='1'){
		if(fm.PayFee1.value==''||fm.PayFee1.value==null){
			alert("��¼�뽻�ѽ�");
			return false;
		}
		if(!verifyNumber("���ѽ��",fm.PayFee1.value)){
			alert("���ѽ�����Ч���֣�");
			return false;
		}
	}
	//�ֽ�֧Ʊ
	if(fm.PayMode.value=='2'){
		if(fm.PayFee2.value==''||fm.PayFee2.value==null){
			alert("��¼�뽻�ѽ�");
			return false;
		}
	 if(!verifyNumber("���ѽ��",fm.PayFee2.value)){
			alert("���ѽ�����Ч���֣�");
			return false;
		}
		if(fm.ChequeNo2.value==''||fm.ChequeNo2.value==null){
			alert("��¼��Ʊ�ݺ��룡");
			return false;
		}
		if(fm.ChequeDate2.value==''||fm.ChequeDate2.value==null){
			alert("��¼��Ʊ�����ڣ�");
			return false;
		}
		if(fm.BankCode2Name.value==''||fm.BankCode2Name.value==null){
			alert("��¼�뿪�����У�");
			return false;
		}
		if(fm.BankAccNo2.value==''||fm.BankAccNo2.value==null){
			alert("��¼���˺ţ�");
			return false;
		}
		if(fm.AccName2.value==''||fm.AccName2.value==null){
			alert("��¼�뻧����");
			return false;
		}
		
				if(fm.InBankCode2.value==''||fm.InBankCode2.value==null){
			alert("��¼���տ����У�");
			return false;
		}
		if(fm.InBankAccNo2.value==''||fm.InBankAccNo2.value==null){
			alert("��¼���տ������˺ţ�");
			return false;
		}
			
	}
	//ת��֧Ʊ
	if(fm.PayMode.value=='3'){
		if(fm.PayFee3.value==''||fm.PayFee3.value==null){
			alert("��¼�뽻�ѽ�");
			return false;
		}
		if(!verifyNumber("���ѽ��",fm.PayFee3.value)){
			alert("���ѽ�����Ч���֣�");
			return false;
		}
		if(fm.ChequeNo3.value==''||fm.ChequeNo3.value==null){
			alert("��¼��Ʊ�ݺ��룡");
			return false;
		}
		if(fm.ChequeDate3.value==''||fm.ChequeDate3.value==null){
			alert("��¼��Ʊ�����ڣ�");
			return false;
		}
		if(fm.BankCode3Name.value==''||fm.BankCode3Name.value==null){
			alert("��¼�뿪�����У�");
			return false;
		}
		if(fm.BankAccNo3.value==''||fm.BankAccNo3.value==null){
			alert("��¼�������˺ţ�");
			return false;
		}
		if(fm.AccName3.value==''||fm.AccName3.value==null){
			alert("��¼�뻧����");
			return false;
		}
		if(fm.InBankCode3.value==''||fm.InBankCode3.value==null){
			alert("��¼���տ����У�");
			return false;
		}
		if(fm.InBankAccNo3.value==''||fm.InBankAccNo3.value==null){
			alert("��¼���տ������˺ţ�");
			return false;
		}
	}
	if(fm.PayMode.value=='4'){
		if(fm.PayFee4.value==''||fm.PayFee4.value==null){
			alert("��¼�뽻�ѽ�");
			return false;
		}
		if(!verifyNumber("���ѽ��",fm.PayFee4.value)){
			alert("���ѽ�����Ч���֣�");
			return false;
		}
		if(fm.ChequeNo4.value==''||fm.ChequeNo4.value==null){
			alert("��¼��Ʊ�ݺ��룡");
			return false;
		}
		if(fm.ChequeDate4.value==''||fm.ChequeDate4.value==null){
			alert("��¼��Ʊ�����ڣ�");
			return false;
		}
		if(fm.InBankCode4.value==''||fm.InBankCode4.value==null){
			alert("��¼���տ����У�");
			return false;
		}
		if(fm.InBankAccNo4.value==''||fm.InBankAccNo4.value==null){
			alert("��¼���տ������˺ţ�");
			return false;
		}
	}
	if(fm.PayMode.value=='5'){
		if(fm.PayFee5.value==''||fm.PayFee5.value==null){
			alert("��¼�뽻�ѽ�");
			return false;
		}
		if(!verifyNumber("���ѽ��",fm.PayFee5.value)){
			alert("���ѽ�����Ч���֣�");
			return false;
		}
		if((fm.ActuGetNo5.value==''||fm.ActuGetNo5.value==null)&&(fm.OtherNo5.value==''||fm.OtherNo5.value==null)){
			alert("��¼��ʵ��֪ͨ��ź�ҵ����벻��ͬʱΪ�գ�");
			return false;
		}
	}
	if(fm.PayMode.value=='6'){
	if(fm.PayFee6.value==''||fm.PayFee6.value==null){
			alert("��¼�뽻�ѽ�");
			return false;
		}
		if(!verifyNumber("���ѽ��",fm.PayFee6.value)){
			alert("���ѽ�����Ч���֣�");
			return false;
		}
		if(fm.ChequeNo6.value==''||fm.ChequeNo6.value==null){
			alert("��¼��Ʊ�ݺ��룡");
			return false;
		}
		if(fm.InBankCode6.value==''||fm.InBankCode6.value==null){
			alert("��¼���տ����У�");
			return false;
		}
		if(fm.InBankAccNo6.value==''||fm.InBankAccNo6.value==null){
			alert("��¼���տ������˺ţ�");
			return false;
		}
	}
	
	//����ƾ֤
	if(fm.PayMode.value=='9'){
		if(fm.PayFee9.value==''||fm.PayFee9.value==null){
			alert("��¼�뽻�ѽ�");
			return false;
		}
	 if(!verifyNumber("���ѽ��",fm.PayFee9.value)){
			alert("���ѽ�����Ч���֣�");
			return false;
		}
		if(fm.ChequeNo9.value==''||fm.ChequeNo9.value==null){
			alert("��¼��Ʊ�ݺ��룡");
			return false;
		}
		if(fm.ChequeDate9.value==''||fm.ChequeDate9.value==null){
			alert("��¼��Ʊ�����ڣ�");
			return false;
		}
//		if(fm.BankCode9.value==''||fm.BankCode9.value==null){
//			alert("��¼�뿪�����У�");
//			return false;
//		}
		if(fm.BankAccNo9.value==''||fm.BankAccNo9.value==null){
			alert("��¼���˺ţ�");
			return false;
		}
		if(fm.AccName9.value==''||fm.AccName9.value==null){
			alert("��¼�뻧����");
			return false;
		}
				if(fm.InBankCode9.value==''||fm.InBankCode9.value==null){
			alert("��¼�뿪�����У�");
			return false;
		}
		if(fm.InBankAccNo9.value==''||fm.InBankAccNo9.value==null){
			alert("��¼���˺ţ�");
			return false;
		}
	}
	
	return true;
}

//�ֽ�ɷ�
function payCash(){
	temp = pointTwo(fm.PayFee1.value);
	fm.PayFee1.value=temp;
	FinFeeGrid.setRowColData(0,1,fm.PayMode.value);
	FinFeeGrid.setRowColData(0,2,fm.PayModeName.value);
	FinFeeGrid.setRowColData(0,3,temp);
	FinFeeGrid.setRowColData(0,4,fm.Currency.value);
	FinFeeGrid.setRowColData(0,20,fm.CurrencyName.value);
	
}

//�ֽ�֧Ʊ
function payCheque(){
	temp=pointTwo(fm.PayFee2.value);
	fm.PayFee2.value=temp;
	FinFeeGrid.setRowColData(0,1,fm.PayMode.value);
	FinFeeGrid.setRowColData(0,2,fm.PayModeName.value);
	FinFeeGrid.setRowColData(0,3,temp);
	FinFeeGrid.setRowColData(0,4,fm.Currency.value);
	FinFeeGrid.setRowColData(0,5,fm.ChequeNo2.value); //Ʊ�ݺ�
	FinFeeGrid.setRowColData(0,6,fm.ChequeDate2.value); //Ʊ������
	FinFeeGrid.setRowColData(0,20,fm.CurrencyName.value);
	FinFeeGrid.setRowColData(0,7,fm.BankCode2Name.value); //��������
	FinFeeGrid.setRowColData(0,9,fm.BankAccNo2.value); //�����˺�
	FinFeeGrid.setRowColData(0,10,fm.AccName2.value); //����
	
	FinFeeGrid.setRowColData(0,11,fm.InBankCode2.value); 
	FinFeeGrid.setRowColData(0,12,fm.InBankAccNo2.value);
}

//�ֽ�֧Ʊ
function payVoucher(){
	temp=pointTwo(fm.PayFee9.value);
	fm.PayFee2.value=temp;
	FinFeeGrid.setRowColData(0,1,fm.PayMode.value);
	FinFeeGrid.setRowColData(0,2,fm.PayModeName.value);
	FinFeeGrid.setRowColData(0,3,temp);
	FinFeeGrid.setRowColData(0,4,fm.Currency.value);
	FinFeeGrid.setRowColData(0,5,fm.ChequeNo9.value); //Ʊ�ݺ�
	FinFeeGrid.setRowColData(0,6,fm.ChequeDate9.value); //Ʊ������
	FinFeeGrid.setRowColData(0,20,fm.CurrencyName.value);
	FinFeeGrid.setRowColData(0,7,fm.BankCode9Name.value); //��������
	FinFeeGrid.setRowColData(0,9,fm.BankAccNo9.value); //�����˺�
	FinFeeGrid.setRowColData(0,10,fm.AccName9.value); //����
	
	FinFeeGrid.setRowColData(0,11,fm.InBankCode9.value); 
	FinFeeGrid.setRowColData(0,12,fm.InBankAccNo9.value);
}
//ת��֧Ʊ
function payCashCheque(){
	temp=pointTwo(fm.PayFee3.value);
	fm.PayFee3.value=temp;
	FinFeeGrid.setRowColData(0,1,fm.PayMode.value);
	FinFeeGrid.setRowColData(0,2,fm.PayModeName.value);
	FinFeeGrid.setRowColData(0,3,temp);
	FinFeeGrid.setRowColData(0,4,fm.Currency.value);
	FinFeeGrid.setRowColData(0,5,fm.ChequeNo3.value); //Ʊ�ݺ�
	FinFeeGrid.setRowColData(0,6,fm.ChequeDate3.value);
	FinFeeGrid.setRowColData(0,7,fm.BankCode3Name.value);	
	FinFeeGrid.setRowColData(0,9,fm.BankAccNo3.value);	
	FinFeeGrid.setRowColData(0,10,fm.AccName3.value);	
	FinFeeGrid.setRowColData(0,20,fm.CurrencyName.value);
	
	FinFeeGrid.setRowColData(0,11,fm.InBankCode3.value); 
	FinFeeGrid.setRowColData(0,12,fm.InBankAccNo3.value);
	
}
//���й���
function payBankCount(){
	temp=pointTwo(fm.PayFee4.value);
	fm.PayFee4.value=temp;
	FinFeeGrid.setRowColData(0,1,fm.PayMode.value);
	FinFeeGrid.setRowColData(0,2,fm.PayModeName.value);
	FinFeeGrid.setRowColData(0,3,temp);
	FinFeeGrid.setRowColData(0,4,fm.Currency.value);
	FinFeeGrid.setRowColData(0,5,fm.ChequeNo4.value); //Ʊ�ݺ�
	FinFeeGrid.setRowColData(0,6,fm.ChequeDate4.value);
	FinFeeGrid.setRowColData(0,20,fm.CurrencyName.value);
	
	FinFeeGrid.setRowColData(0,11,fm.InBankCode4.value); 
	FinFeeGrid.setRowColData(0,12,fm.InBankAccNo4.value); 
	
}

//�ڲ�ת��
function payTransAccout(){
	temp=pointTwo(fm.PayFee5.value);
	fm.PayFee5.value=temp;
	FinFeeGrid.setRowColData(0,1,fm.PayMode.value);
	FinFeeGrid.setRowColData(0,2,fm.PayModeName.value);
	FinFeeGrid.setRowColData(0,3,temp);
	FinFeeGrid.setRowColData(0,4,fm.Currency.value);	
	FinFeeGrid.setRowColData(0,17,fm.ActuGetNo5.value);	
	FinFeeGrid.setRowColData(0,18,fm.Drawer5.value);	
	FinFeeGrid.setRowColData(0,19,fm.DrawerID.value);	
	FinFeeGrid.setRowColData(0,20,fm.CurrencyName.value);
	
}

//Pos��
function payPos(){
	temp=pointTwo(parseFloat(fm.PayFee6.value));
	fm.PayFee6.value=temp;
	FinFeeGrid.setRowColData(0,1,fm.PayMode.value);
	FinFeeGrid.setRowColData(0,2,fm.PayModeName.value);
	FinFeeGrid.setRowColData(0,3,temp);
	FinFeeGrid.setRowColData(0,4,fm.Currency.value);
	FinFeeGrid.setRowColData(0,5,fm.ChequeNo6.value); //Ʊ�ݺ�
	FinFeeGrid.setRowColData(0,11,fm.InBankCode6.value);
	FinFeeGrid.setRowColData(0,12,fm.InBankAccNo6.value);
	FinFeeGrid.setRowColData(0,20,fm.CurrencyName.value);
	
}

/*********************************************************************
 *  �շѷ�ʽѡ���ڲ�ת��ʱ����ѯʵ������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryLJAGet()
{
   if(verifyInput6())
   {
      EasyQueryPay();
   }
}

function EasyQueryPay ()
{
  // ƴSQL��䣬��ҳ��ɼ���Ϣ
  var strSql = "";
  mySql.setSqlId("TempFeeInputInputSql_34");
  strSql = mySql.getSQL();
  if (fm.all('ActuGetNo5').value!="")
  {
  	mySql.setSqlId("TempFeeInputInputSql_35");
  	mySql.addPara('ActuGetNo5',fm.all('ActuGetNo5').value);
  	strSql += mySql.getSQL();	
  }
  if(fm.all('OtherNo5').value!="")
	{
		mySql.setSqlId("TempFeeInputInputSql_36");
  	mySql.addPara('OtherNo5',fm.all('OtherNo5').value);
  	strSql += mySql.getSQL();
	}
  
  //��ѯ���ѻ���sql,���ؽ��
  QueryLJAGetGrid.clearData('QueryLJAGetGrid');  
  var strArray = easyExecSql(strSql );
  if(strArray==null){
  	alert("û�в�ѯ�����ݣ�");
  	return false;
  }
  turnPage.queryModal(strSql, QueryLJAGetGrid);
} 

function GetRecord(){
  var tSel = QueryLJAGetGrid.getSelNo();
	fm.ActuGetNo5.value	= QueryLJAGetGrid.getRowColData(tSel-1,1);
	fm.OtherNo5.value		=	QueryLJAGetGrid.getRowColData(tSel-1,2);
	fm.PayFee5.value		=	pointTwo(QueryLJAGetGrid.getRowColData(tSel-1,3));
	fm.Drawer5.value		=	QueryLJAGetGrid.getRowColData(tSel-1,4);
	fm.DrawerID.value		=	QueryLJAGetGrid.getRowColData(tSel-1,5);
}

function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}

function queryClick(){
  fm.OperateType.value="QUERY";
  
  window.open("./FrameAccRDQuery.jsp?Serial=","windows1");
}

function updateClick(){
	fm.OperateType.value="UPDATE";
	try {
		if( verifyInput()==true&&MatchInfoGrid.checkValue("MatchInfoGrid")) {	
			if (verifyInput4()==true){
		  	var i = 0;
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
				fm.action="./TempFinFeeSave.jsp";
		  	document.getElementById("fm").submit(); //�ύ
	  	}
	  	else{
	  	}
	  }
  } catch(ex) 
  {
  	showInfo.close( );
  	alert(ex);
  }
}

function deleteClick(){
	fm.OperateType.value="DELETE";
	if(!confirm("��ȷ��Ҫɾ�����ۼƷ�����")){
		return false;
	}
	try {
		if( verifyInput()) {
			var i = 0;
		  var showStr="����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			fm.action="./LRAccRDSave.jsp";
		  document.getElementById("fm").submit(); //�ύ
	  }else{
	  	
	  }
  } catch(ex){
  	showInfo.close( );
  	alert(ex);
  }
}

//����У��
function verifyInput3(){
	var rowNum=FinFeeGrid. mulLineCount ; //���� 
	if(rowNum==0){
		alert("��¼����񽻷Ѽ�¼!");
		return false;
	}
	for(var i=0;i<rowNum;i++)
	{
		num=i+1;
		//�ֽ�֧Ʊ
		if(FinFeeGrid.getRowColData(i,1)=='2'&&(FinFeeGrid.getRowColData(i,5)==null||FinFeeGrid.getRowColData(i,5)==''))
		{
			alert("���ѷ�ʽΪ�ֽ�֧Ʊ��¼��Ʊ�ݺ���!");
			return false;
		}
		//ת��֧Ʊ
		if(FinFeeGrid.getRowColData(i,1)=='3'&&(FinFeeGrid.getRowColData(i,5)==null||FinFeeGrid.getRowColData(i,5)==''))
		{
			alert("���ѷ�ʽΪת��֧Ʊ��¼��Ʊ�ݺ���!");
			return false;
		}
		if(FinFeeGrid.getRowColData(i,1)=='3'&&(FinFeeGrid.getRowColData(i,6)==null||FinFeeGrid.getRowColData(i,6)==''))
		{
			alert("���ѷ�ʽΪת��֧Ʊ��¼��Ʊ������!");
			return false;
		}
		if(FinFeeGrid.getRowColData(i,1)=='3'&&(FinFeeGrid.getRowColData(i,7)==null||FinFeeGrid.getRowColData(i,7)==''))
		{
			alert("���ѷ�ʽΪת��֧Ʊ��¼�뿪������!");
			return false;
		}
		if(FinFeeGrid.getRowColData(i,1)=='3'&&(FinFeeGrid.getRowColData(i,9)==null||FinFeeGrid.getRowColData(i,9)==''))
		{
			alert("���ѷ�ʽΪת��֧Ʊ��¼�������˺�!");
			return false;
		}
		if(FinFeeGrid.getRowColData(i,1)=='3'&&(FinFeeGrid.getRowColData(i,10)==null||FinFeeGrid.getRowColData(i,10)==''))
		{
			alert("���ѷ�ʽΪת��֧Ʊ��¼�뻧��!");
			return false;
		}
		//�ڲ�ת��
		if(FinFeeGrid.getRowColData(i,1)=='5'&&(FinFeeGrid.getRowColData(i,5)==null||FinFeeGrid.getRowColData(i,5)==''))
		{
			alert("���ѷ�ʽΪת��֧Ʊ��¼��Ʊ�ݺ���!");
			return false;
		}
		if(FinFeeGrid.getRowColData(i,1)=='5'&&(FinFeeGrid.getRowColData(i,11)==null||FinFeeGrid.getRowColData(i,11)==''))
		{
			alert("���ѷ�ʽΪת��֧Ʊ��¼���տ�����!");
			return false;
		}
		if(FinFeeGrid.getRowColData(i,1)=='5'&&(FinFeeGrid.getRowColData(i,12)==null||FinFeeGrid.getRowColData(i,12)==''))
		{
			alert("���ѷ�ʽΪת��֧Ʊ��¼���տ������˺�!");
			return false;
		}
		//POS�տ�
		if(FinFeeGrid.getRowColData(i,1)=='6'&&(FinFeeGrid.getRowColData(i,5)==null||FinFeeGrid.getRowColData(i,5)==''))
		{
			alert("���ѷ�ʽΪת��֧Ʊ��¼��Ʊ�ݺ���!");
			return false;
		}
		if(FinFeeGrid.getRowColData(i,1)=='6'&&(FinFeeGrid.getRowColData(i,11)==null||FinFeeGrid.getRowColData(i,11)==''))
		{
			alert("���ѷ�ʽΪת��֧Ʊ��¼���տ�����!");
			return false;
		}
		if(FinFeeGrid.getRowColData(i,1)=='6'&&(FinFeeGrid.getRowColData(i,12)==null||FinFeeGrid.getRowColData(i,12)==''))
		{
			alert("���ѷ�ʽΪת��֧Ʊ��¼���տ������˺�!");
			return false;
		}
		//������������
		if(FinFeeGrid.getRowColData(i,1)=='7')
		{
			alert("�����������۲��ܽ��й��潻��!");
			return false;
		}
	}
	return true;
}

/**
	�޸�У��
*/
function verifyInput4(){
	if(!confirm("��ȷ��Ҫ�޸�ƥ����Ϣ��")){
		return false;
	}
	if(fm.FinFeeNo.value==''||fm.FinFeeNo.value==null){
		alert("���Ȳ�ѯ�����շ���Ϣ�ٽ����޸�!");
		return false;
	}
	//�ظ���У��
	for(var n=0;n<MatchInfoGrid.mulLineCount;n++) 
	{ 
	   for(var m=n+1;m<MatchInfoGrid.mulLineCount;m++) 
	   { 
	     if(MatchInfoGrid.getRowColData(n,1)==MatchInfoGrid.getRowColData(m,1)) 
	     {
	         alert("����¼���ظ���ҵ��ƥ�����");	
	         return false; 
	     }
	   }
	}
	return true;
}

function afterQuery(){
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm(){
  try{
	  fm.FinFeeNo.value='';
	  FinFeeGrid.clearData();
	  MatchInfoGrid.clearData();
  }
  catch(re){
  	alert("��CertifySendOutInput.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//�ύǰ��У�顢����  
function beforeSubmit(){
  //��Ӳ���
}

function afterCodeSelect( cCodeName, Field ){
	//ѡ���˴���
	if(cCodeName == "TempFeeType")
	{ 
	  showTempFeeTypeInput(Field.value);
	  if(Field.value=="1"){
	  	AgentCode.style.display='';
			AgentCode1.style.display='none';
	  }else if(Field.value=="4"||Field.value=="5"){
	  	AgentCode.style.display='none';
			AgentCode1.style.display='none';
	  }
	  else{
	  	AgentCode.style.display='none';
			AgentCode1.style.display='';
	  }
	  clearOpeTypeInfo();
	}
	
	if(cCodeName == "paymodequery")
	{
		showTempClassInput(Field.value);
		//PayModePrem();
	}
	if(cCodeName == "comtobank"){
		fm.InBankAccNo3.value="";
	}
	
}

function clearOpeTypeInfo(){
	fm.PolicyCom.value='';
	fm.OpeCurrency.value='';
	fm.OpeCurrencyName.value='';
	fm.AgentCode.value='';
	fm.AgentName.value='';
	fm.AgentGroup.value='';
	fm.AgentCode1.value='';
	fm.AgentName1.value='';
	fm.AgentGroup1.value='';
	
	fm.InputNo1.value='';
	fm.InputNob.value='';
	fm.OpeFee1.value='';
	fm.OpeFee2.value='';
	fm.InputNo3.value='';
	fm.GetNoticeNo.value='';
	fm.OpeFee3.value='';
	fm.InputNo5.value='';
	fm.OpeFee4.value='';
	fm.InputNo7.value='';
	fm.InputNo8.value='';
	fm.OpeFee5.value='';
	fm.InputNo11.value='';
	fm.InputNo12.value='';
	fm.InputNo22.value='';
	
	fm.OpeFee6.value='';
	fm.TempFeeNo1.value='';
	
}

/*********************************************************************
 *  ����ѡ��ͬ�Ľ��ѷ�ʽ����ʼ��ҳ�� gaoht
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showTempClassInput(type)
{
	for(i=0;i<=9;i++)
	{
		
		if(i==type)
		{
			fm.all("divPayMode"+i).style.display='';
		}
		else
		{
		  fm.all("divPayMode"+i).style.display='none';
		}
	}
}

/********************************************************************
 *
 *
 *
 ********************************************************************
 */
function PayModePrem()
{
  var SumPrem =0.0;
	var TempCount = TempGrid.mulLineCount;
  for(i=0;i<TempCount;i++)
  {
  	SumPrem = SumPrem + pointTwo(parseFloat(TempGrid.getRowColData(i,3)));//���ѽ���ۼ�
  }
  SumPrem = pointTwo(SumPrem);
  if (fm.all("PayMode").value == '0')
  {
  	fm.all("PayFee0").value = SumPrem;
  	}
  if (fm.all("PayMode").value == '1')
  {
  	fm.all("PayFee1").value = SumPrem;
  	}
  if (fm.all("PayMode").value == '2')
  {
  	fm.all("PayFee2").value = SumPrem;
    }
  if (fm.all("PayMode").value == '3')
  {
  	fm.all("PayFee3").value = SumPrem;
    }
  if (fm.all("PayMode").value == '4')
  {
  	fm.all("PayFee4").value = SumPrem;
  }
  
  if (fm.all("PayMode").value == '6')
  {
  	fm.all("PayFee6").value = SumPrem;
  }
  if (fm.all("PayMode").value == '7')
  {
  	fm.all("PayFee7").value = SumPrem;
  }
  if (fm.all("PayMode").value == '8')
  {
  	fm.all("PayFee8").value = SumPrem;
  }
  if (fm.all("PayMode").value == '9')
  {
  	fm.all("PayFee9").value = SumPrem;
  }
}

/*********************************************************************
 *  ����ѡ��ͬ���ݽ�������  ����ʼ��ҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showTempFeeTypeInput(type) {
    if (type==7)
     type = 3;
  for (i=0; i<9; i++) {
    if ((i+1) == type) {
      fm.all("TempFeeType" + (i+1)).style.display = '';
    }
    else {
      fm.all("TempFeeType" + (i+1)).style.display = 'none';
    }
  }
}

function queryAgent()
{
	if(fm.all('ManageCom').value==""){
		alert("����¼���շѻ�����Ϣ��"); 
		return;
	}
	if(fm.AgentCode.value != ""&&fm.AgentCode.value != null){
		var cAgentCode = fm.AgentCode.value;
		var strSql = "";
  	mySql.setSqlId("TempFeeInputInputSql_13");
  	mySql.addPara('cAgentCode',cAgentCode);
  	var arrResult = easyExecSql( mySql.getString());
  	if (arrResult != null) {
  	  alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
  	} 
	}else{
		var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+fm.all('ManageCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
}

function GetManageCom()
{
  if(fm.AgentCode.value!=null&&fm.AgentCode.value!="")
  {
  	var strSql = "";
   	mySql.setSqlId("TempFeeInputInputSql_24");
   	mySql.addPara('AgentCode',fm.AgentCode.value);
   	var arrResult = easyExecSql(mySql.getString());
   	if(arrResult==null||arrResult=="NULL"||arrResult=="")
   	{
   	  alert("ҵ��Ա��Ŵ����޽��ѻ�����Ϣ");	
   	  fm.AgentCode.value="";
   	  fm.AgentName.value="";
   	  fm.AgentGroup.value="";
   	  fm.PolicyCom.value="";
   	  
   	  return false;
   	}
  	else
  	{
     fm.PolicyCom.value=arrResult;	  	  
    }
  	var aSql = "";
    mySql.setSqlId("TempFeeInputInputSql_25");
    mySql.addPara('AgentCode',fm.AgentCode.value);
  	arrResult = easyExecSql(mySql.getString());
   	if(arrResult==null||arrResult=="NULL"||arrResult=="")
   	{
   	  alert("ҵ��Ա��Ŵ����������Ϣ");	
   	  fm.AgentCode.value="";
   	  return false;
   	}
  	else
  	{
  		fm.AgentGroup.value=arrResult;	
      var nSql = "";        
      mySql.setSqlId("TempFeeInputInputSql_26");
      mySql.addPara('AgentCode',fm.AgentCode.value);
      arrResult = easyExecSql(mySql.getString());
      fm.AgentName.value=arrResult;
    } 
  }
}

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery2(arrResult)
{
	if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
  	fm.PolicyCom.value = arrResult[0][2];
  }
  var aSql = "";
  mySql.setSqlId("TempFeeInputInputSql_14");
  mySql.addPara('AgentCode',fm.AgentCode.value);
  arrResult = easyExecSql(mySql.getString());
  if(arrResult==null||arrResult=="NULL"||arrResult=="")
  {
    alert("ҵ��Ա��Ŵ����������Ϣ");	
    fm.AgentCode.value="";
    return false;
  }
  else
  {
   fm.AgentGroup.value=arrResult;
  } 
}

function confirm1(){
	if(!verifyInput7()){
		return false;
	}
	
	
  if(fm.TempFeeNo1.value == null || fm.TempFeeNo1.value == '')
	{
		//������վ�Ϊ�գ���Ҫ������ʱ���վ�
	  CreateTempNo();
	
	}
	else
	{
		CreateTempData();
	}	
	

}

function CreateTempData()
{
		if(fm.TempFeeType.value=='1'){//�µ�����
		if(newPolicy()){
			clearOpeTypeInfo();
		}
	}
	//else if(fm.TempFeeType.value=='2'){//���ڴ߽�
	//	if(conPolicy()){
	//		clearOpeTypeInfo();
	//	}
	//}
	else if(fm.TempFeeType.value=='3'){//����Ԥ��
		if(conBefPolicy()){
			clearOpeTypeInfo();
		}
	}else if(fm.TempFeeType.value=='4'){//��ȫ����
		if(edorPolicy()){
			clearOpeTypeInfo();
		}
	}else if(fm.TempFeeType.value=='5'){//���⽻��
		if(claimPolicy()){
			clearOpeTypeInfo();
		}
	}
	//else if(fm.TempFeeType.value=='6'){//�����ڽ���
	//	if(urgePayPolicy()){
	//		clearOpeTypeInfo();
	//	}
	//}
	
}

function CreateTempNo()
{
	 //var i = 0; 		  	
	 //var showStr="���������������վݺţ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"; 
	 //var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	 //var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
    showInfo.focus(); 
	 fmSave.action = "./TempFinFeeCrTempNo.jsp?ManageCom="+fm.ManageCom.value; 
	 fmSave.submit(); //�ύ
	 return false;

}

function AfterCreateTempNo(tTempFeeNo)
{
	if(fm.TempFeeNo1.value == null || fm.TempFeeNo1.value == "")
	{
		fm.TempFeeNo1.value = tTempFeeNo;
	}
	
	if(fm.TempFeeNo1.value == null || fm.TempFeeNo1.value == "")
	{
		alert("�����������վݺŴ���!");
		return;
	}
	else
	{
		CreateTempData();
	}
}

//�µ�
function newPolicy(){
	if(parseFloat(fm.OpeFee1.value)<=0){
		alert("���ý���С�ڵ���0");
		return false;
	}
	tempFee = 1000000000 * parseFloat(fm.OpeFee1.value); 
	//ȡ�û�����Ϣ 
	var count=TempToGrid.mulLineCount; //�õ�MulLine������ 
	for(var i=0;i<count;i++){ 
		tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
	}
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		//InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("ҵ����ϼƴ��ڲ����շ��ܺͣ�������¼��ҵ�������Ϣ��");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((sumTempFee-tempFee)/1000000000);
	
	TempToGrid.addOne("TempToGrid");
	TempToGrid.setRowColData(count,	2	,	fm.TempFeeType.value			);	 
	TempToGrid.setRowColData(count,	3	,	fm.PayDate.value						); 
	TempToGrid.setRowColData(count,	4	,	pointTwo(parseFloat(fm.OpeFee1.value))); 
	TempToGrid.setRowColData(count,	5	,	fm.OpeCurrency.value				);	//ҵ�������Ϣ
	TempToGrid.setRowColData(count,	7	,	fm.PolicyCom.value					); 	//���������managecom���շѻ���
	TempToGrid.setRowColData(count,	9,	fm.AgentGroup.value					);	//�����˱���
	TempToGrid.setRowColData(count,	10,	fm.AgentCode.value					);	//�����˱���
	TempToGrid.setRowColData(count,	16,	fm.InputNob.value						);	//Ͷ����
	TempToGrid.setRowColData(count,	11,	fm.InputNo1.value						); 	//ҵ�����
	TempToGrid.setRowColData(count,	12,	'6' 												);	//ҵ���������
	TempToGrid.setRowColData(count,	19	,	fm.OpeCurrencyName.value	);
	TempToGrid.setRowColData(count,	20	,	fm.TempFeeNo1.value				);
	TempToGrid.setRowColData(count,	2	,	FinFeeGrid.getRowColData(0,1));	
	TempToGrid.setRowColData(count,	18,	fm.TempFeeType.value									);    //	
	
	
	return true;
}

//���ڴ���
function conPolicy(){
	if(!addFlag){
		alert("û�в鵽Ӧ�ռ�¼");
		return false ;
	}
	if(parseFloat(fm.OpeFee2.value)<=0){
		alert("���ý���С�ڵ���0");
		return false;
	}
	tempFee = 1000000000 * parseFloat(fm.OpeFee2.value); 
	//ȡ�û�����Ϣ 
	var count=TempToGrid.mulLineCount; //�õ�MulLine������ 
	for(var i=0;i<count;i++){ 
		tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
	}
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		//InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("ҵ����ϼƴ��ڲ����շ��ܺͣ�������¼��ҵ�������Ϣ��");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((sumTempFee-tempFee)/1000000000);
	
	TempToGrid.addOne("TempToGrid");
	TempToGrid.setRowColData(count,	1	,	fm.GetNoticeNo.value									);
	TempToGrid.setRowColData(count,	3	,	fm.PayDate.value											);
	TempToGrid.setRowColData(count,	4	,	pointTwo(parseFloat(fm.OpeFee2.value)));
	TempToGrid.setRowColData(count,	5	,	fm.OpeCurrency.value									);		//����
	TempToGrid.setRowColData(count,	7	,	fm.PolicyCom.value										); 		//�������
	TempToGrid.setRowColData(count,	9,	fm.AgentGroup1.value									);		//�����˱���
	TempToGrid.setRowColData(count,	10,	fm.AgentCode1.value										);		//�����˱���
	TempToGrid.setRowColData(count,	11,	fm.InputNo3.value											); 		//ҵ�����
	TempToGrid.setRowColData(count,	12,	'2' 																	); 		//ҵ���������   
	TempToGrid.setRowColData(count,	19,	fm.OpeCurrencyName.value							);
	TempToGrid.setRowColData(count,	20	,	fm.TempFeeNo1.value									);
	TempToGrid.setRowColData(count,	2	,	FinFeeGrid.getRowColData(0,1));	
	TempToGrid.setRowColData(count,	18,	fm.TempFeeType.value									);    //	
	
	return true;
}
//����Ԥ��
function conBefPolicy(){
	if(!contFlag){
		alert("û�в鵽������¼");
		return false ;
	}
	if(parseFloat(fm.OpeFee3.value)<=0){
		alert("���ý���С�ڵ���0");
		return false;
	}
	tempFee = 1000000000 * parseFloat(fm.OpeFee3.value); 
	//ȡ�û�����Ϣ 
	var count=TempToGrid.mulLineCount; //�õ�MulLine������ 
	for(var i=0;i<count;i++){ 
		tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
	}
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		//InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("ҵ����ϼƴ��ڲ����շ��ܺͣ�������¼��ҵ�������Ϣ��");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((sumTempFee-tempFee)/1000000000);
	
	TempToGrid.addOne("TempToGrid");
	TempToGrid.setRowColData(count,	1	,	fm.InputNo11.value										); 		//����֪ͨ��
	TempToGrid.setRowColData(count,	3	,	fm.PayDate.value											);		//
	TempToGrid.setRowColData(count,	4	,	pointTwo(parseFloat(fm.OpeFee3.value)));		//
	TempToGrid.setRowColData(count,	5	,	fm.OpeCurrency.value									);		//
	TempToGrid.setRowColData(count,	7	,	fm.PolicyCom.value										); 		//�������
	TempToGrid.setRowColData(count,	9,	fm.AgentGroup1.value									);		//�����˱���
	TempToGrid.setRowColData(count,	10,	fm.AgentCode1.value										);		//�����˱���
	TempToGrid.setRowColData(count,	11,	fm.InputNo5.value											); 		//ҵ�����
	TempToGrid.setRowColData(count,	12,	'2' 																	); 		//ҵ���������
	TempToGrid.setRowColData(count,	18,	fm.TempFeeType.value									);    //
	TempToGrid.setRowColData(count,	19,	fm.OpeCurrencyName.value							);
	TempToGrid.setRowColData(count,	20,	fm.TempFeeNo1.value										);
	TempToGrid.setRowColData(count,	2	,	FinFeeGrid.getRowColData(0,1));	
	TempToGrid.setRowColData(count,	18,	fm.TempFeeType.value									);    //			
	
	return true;
}

//��ȫ
function edorPolicy(){
	
	if(!edorFlag){
		alert("û�в鵽Ӧ�ռ�¼");
		return false ;
	}
	if(parseFloat(fm.OpeFee4.value)<=0){
		alert("���ý���С�ڵ���0");
		return false;
	}
	tempFee = 1000000000 * parseFloat(fm.OpeFee4.value); 
	//ȡ�û�����Ϣ 
	var count=TempToGrid.mulLineCount; //�õ�MulLine������ 
	for(var i=0;i<count;i++){ 
		tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
	}
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		//InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("ҵ����ϼƴ��ڲ����շ��ܺͣ�������¼��ҵ�������Ϣ��");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((sumTempFee-tempFee)/1000000000);
	////////
	TempToGrid.addOne("TempToGrid");
	TempToGrid.setRowColData(count,	1	,	fm.InputNo8.value											);
	TempToGrid.setRowColData(count,	3	,	fm.PayDate.value											);
	TempToGrid.setRowColData(count,	4	,	pointTwo(parseFloat(fm.OpeFee4.value)));
	TempToGrid.setRowColData(count,	5	,	fm.OpeCurrency.value									);
	TempToGrid.setRowColData(count,	7	,	fm.PolicyCom.value										); 		//�������
	TempToGrid.setRowColData(count,	9 ,	fm.AgentGroup1.value									);		//�����˱���
	TempToGrid.setRowColData(count,	10,	fm.AgentCode1.value										);		//�����˱���
	TempToGrid.setRowColData(count,	11,	fm.InputNo7.value											); 	  //ҵ�����
	TempToGrid.setRowColData(count,	12,	'10' 																	); 		//ҵ���������
	TempToGrid.setRowColData(count,	19,	fm.OpeCurrencyName.value							);
	TempToGrid.setRowColData(count,	20,	fm.TempFeeNo1.value										);
	TempToGrid.setRowColData(count,	2	,	FinFeeGrid.getRowColData(0,1));	
	TempToGrid.setRowColData(count,	18,	fm.TempFeeType.value									);    //		
	
	return true;
}

//����
function claimPolicy(){
	if(!clmFlag){
		alert("û�в鵽Ӧ�ռ�¼");
		return false ;
	}
	if(parseFloat(fm.OpeFee5.value)<=0){
		alert("���ý���С�ڵ���0");
		return false;
	}
	tempFee = 1000000000 * parseFloat(fm.OpeFee5.value); 
	//ȡ�û�����Ϣ 
	var count=TempToGrid.mulLineCount; //�õ�MulLine������ 
	for(var i=0;i<count;i++){ 
		tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
	}
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		//InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("ҵ����ϼƴ��ڲ����շ��ܺͣ�������¼��ҵ�������Ϣ��");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((sumTempFee-tempFee)/1000000000);
	
	TempToGrid.addOne("TempToGrid");
	TempToGrid.setRowColData(count,	1	,	fm.InputNo11.value										);		
	TempToGrid.setRowColData(count,	3	,	fm.PayDate.value											);		
	TempToGrid.setRowColData(count,	4	,	pointTwo(parseFloat(fm.OpeFee5.value)));		
	TempToGrid.setRowColData(count,	5	,	fm.OpeCurrency.value									);		
	TempToGrid.setRowColData(count,	7	,	fm.PolicyCom.value										); 		//�������
	TempToGrid.setRowColData(count,	9 ,	fm.AgentGroup1.value									);		//�����˱���
	TempToGrid.setRowColData(count,	10,	fm.AgentCode1.value										);		//�����˱���
	TempToGrid.setRowColData(count,	11,	fm.InputNo12.value										);		//ҵ�����
	TempToGrid.setRowColData(count,	12,	'9' 																	); 		//ҵ��������� ���⡮9��
	TempToGrid.setRowColData(count,	19,	fm.OpeCurrencyName.value							);
	TempToGrid.setRowColData(count,	20,	fm.TempFeeNo1.value										);
	TempToGrid.setRowColData(count,	2	,	FinFeeGrid.getRowColData(0,1));	
	TempToGrid.setRowColData(count,	18,	fm.TempFeeType.value									);    //		
	
	return true;
}

//�����ڽɷ�
function urgePayPolicy(){
	if(!urgeFlag){
		alert("û�в鵽������¼");
		return false ;
	}
	if(parseFloat(fm.OpeFee6.value)<=0){
		alert("���ý���С�ڵ���0");
		return false;
	}
	tempFee = 1000000000 * parseFloat(fm.OpeFee6.value); 
	//ȡ�û�����Ϣ 
	var count=TempToGrid.mulLineCount; //�õ�MulLine������ 
	for(var i=0;i<count;i++){ 
		tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
	}
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		//InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("ҵ����ϼƴ��ڲ����շ��ܺͣ�������¼��ҵ�������Ϣ��");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((sumTempFee-tempFee)/1000000000);
	
	TempToGrid.addOne("TempToGrid");
	TempToGrid.setRowColData(count,	1	,	fm.InputNo11.value										);
	TempToGrid.setRowColData(count,	3	,	fm.PayDate.value											);
	TempToGrid.setRowColData(count,	4	,	pointTwo(parseFloat(fm.OpeFee6.value)));
	TempToGrid.setRowColData(count,	5	,	fm.OpeCurrency.value									);
	TempToGrid.setRowColData(count,	7	,	fm.PolicyCom.value										); 		//�������
	TempToGrid.setRowColData(count,	9 ,	fm.AgentGroup1.value									);		//�����˱���
	TempToGrid.setRowColData(count,	10,	fm.AgentCode1.value										);		//�����˱���
	TempToGrid.setRowColData(count,	11,	fm.InputNo22.value										); 		//ҵ�����
	TempToGrid.setRowColData(count,	12,	'2' 																	); 		//ҵ���������
	TempToGrid.setRowColData(count,	18,	fm.TempFeeType.value									);    //	
	TempToGrid.setRowColData(count,	19,	fm.OpeCurrencyName.value							);
	TempToGrid.setRowColData(count,	20,	fm.TempFeeNo1.value										);
	TempToGrid.setRowColData(count,	2	,	FinFeeGrid.getRowColData(0,1));	
	
	return true;
}

//�µ�
function modNewPolicy(){
	if(parseFloat(fm.OpeFee1.value)<=0){
		alert("���ý���С�ڵ���0");
		return false;
	}
	var tSel = TempToGrid.getSelNo();
	tempFee = 1000000000 * parseFloat(fm.OpeFee1.value); 
	//ȡ�û�����Ϣ 
	var count = TempToGrid.mulLineCount; //�õ�MulLine������ 
	for(var i=0 ; i < count;i++){ 
		if(i!=(tSel-1)){
			tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
		}
	}
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		//InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("ҵ����ϼƴ��ڲ����շ��ܺͣ�������¼��ҵ�������Ϣ��");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((tempFee-sumTempFee)/1000000000);
	
	TempToGrid.setRowColData(tSel-1	,	3	,	fm.PayDate.value						);
	TempToGrid.setRowColData(tSel-1	,	4	,	pointTwo(parseFloat(fm.OpeFee1.value))); //���ý��
	TempToGrid.setRowColData(tSel-1	,	5	,	fm.OpeCurrency.value				);	//ҵ�������Ϣ
	TempToGrid.setRowColData(tSel-1	,	7	,	fm.PolicyCom.value					); 	//���������managecom���շѻ���
	TempToGrid.setRowColData(tSel-1	,	9,	fm.AgentGroup.value					);	//�����˱���
	TempToGrid.setRowColData(tSel-1	,	10,	fm.AgentCode.value					);	//�����˱���
	TempToGrid.setRowColData(tSel-1	,	16,	fm.InputNob.value						);	//Ͷ����
	TempToGrid.setRowColData(tSel-1	,	11,	fm.InputNo1.value						); 	//ҵ�����
	TempToGrid.setRowColData(tSel-1	,	12,	'6' 												);	//ҵ���������
	TempToGrid.setRowColData(tSel-1,	18,	fm.TempFeeType.value									);    //	
	TempToGrid.setRowColData(tSel-1	,	19,	fm.OpeCurrencyName.value		);
	TempToGrid.setRowColData(tSel-1	,	20,	fm.TempFeeNo1.value					);
	TempToGrid.setRowColData(tSel-1,	2	,	FinFeeGrid.getRowColData(0,1));	
	
	return true;
}

//���ڴ���
function modConPolicy(){
	if(!addFlag){
		alert("û�в鵽Ӧ�ռ�¼");
		return false ;
	}
	if(parseFloat(fm.OpeFee2.value)<=0){
		alert("���ý���С�ڵ���0");
		return false;
	}
	var tSel = TempToGrid.getSelNo();
	tempFee = 1000000000 * parseFloat(fm.OpeFee2.value); 
	//ȡ�û�����Ϣ 
	var count=TempToGrid.mulLineCount; //�õ�MulLine������ 
	for(var i=0;i<count;i++){ 
		if(i!=(tSel-1)){
			tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
		}
	}
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		//InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("ҵ����ϼƴ��ڲ����շ��ܺͣ�������¼��ҵ�������Ϣ��");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((tempFee-sumTempFee)/1000000000);
	
	TempToGrid.setRowColData(tSel-1,	1	,	fm.GetNoticeNo.value									);
	TempToGrid.setRowColData(tSel-1,	3	,	fm.PayDate.value											);
	TempToGrid.setRowColData(tSel-1,	4	,	pointTwo(parseFloat(fm.OpeFee2.value)));
	TempToGrid.setRowColData(tSel-1,	5	,	fm.OpeCurrency.value									);		//����
	TempToGrid.setRowColData(tSel-1,	7	,	fm.PolicyCom.value										); 		//�������
	TempToGrid.setRowColData(tSel-1,	9,	fm.AgentGroup1.value									);		//�����˱���
	TempToGrid.setRowColData(tSel-1,	10,	fm.AgentCode1.value										);		//�����˱���
	TempToGrid.setRowColData(tSel-1,	11,	fm.InputNo3.value											); 		//ҵ�����
	TempToGrid.setRowColData(tSel-1,	12,	'2' 																	); 		//ҵ��������� 
	TempToGrid.setRowColData(tSel-1,	18,	fm.TempFeeType.value									);    //	
	TempToGrid.setRowColData(tSel-1,	19,	fm.OpeCurrencyName.value							);
	TempToGrid.setRowColData(tSel-1	,	20,	fm.TempFeeNo1.value										);
	TempToGrid.setRowColData(tSel-1,	2	,	FinFeeGrid.getRowColData(0,1));	
	
	return true;
}
//����Ԥ��
function modConBefPolicy(){
	if(!contFlag){
		alert("û�в鵽������¼");
		return false ;
	}
	if(parseFloat(fm.OpeFee3.value)<=0){
		alert("���ý���С�ڵ���0");
		return false;
	}
	var tSel = TempToGrid.getSelNo();
	tempFee = 1000000000 * parseFloat(fm.OpeFee3.value); 
	//ȡ�û�����Ϣ 
	var count=TempToGrid.mulLineCount; //�õ�MulLine������ 
	for(var i=0;i<count;i++){ 
		if(i!=(tSel-1)){
			tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
		}
	}
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		//InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("ҵ����ϼƴ��ڲ����շ��ܺͣ�������¼��ҵ�������Ϣ��");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((tempFee-sumTempFee)/1000000000);
	////////
	TempToGrid.setRowColData(tSel-1,	1	,	fm.InputNo11.value										); 		//����֪ͨ��
	TempToGrid.setRowColData(tSel-1,	3	,	fm.PayDate.value											);		//
	TempToGrid.setRowColData(tSel-1,	4	,	pointTwo(parseFloat(fm.OpeFee3.value)));		//
	TempToGrid.setRowColData(tSel-1,	5	,	fm.OpeCurrency.value									);		//
	TempToGrid.setRowColData(tSel-1,	7	,	fm.PolicyCom.value										); 		//�������
	TempToGrid.setRowColData(tSel-1,	9,	fm.AgentGroup1.value									);		//�����˱���
	TempToGrid.setRowColData(tSel-1,	10,	fm.AgentCode1.value										);		//�����˱���
	TempToGrid.setRowColData(tSel-1,	11,	fm.InputNo5.value											); 		//ҵ�����
	TempToGrid.setRowColData(tSel-1,	12,	'2' 																	); 		//ҵ���������
	TempToGrid.setRowColData(tSel-1,	18,	fm.TempFeeType.value									);    //	
	TempToGrid.setRowColData(tSel-1,	19	,	fm.OpeCurrencyName.value						);
	TempToGrid.setRowColData(tSel-1	,	20,	fm.TempFeeNo1.value										);
	TempToGrid.setRowColData(tSel-1,	2	,	FinFeeGrid.getRowColData(0,1));	
	
	return true;
}

//��ȫ
function modEdorPolicy(){
	
	if(!edorFlag){
		alert("û�в鵽Ӧ�ռ�¼");
		return false ;
	}
	if(parseFloat(fm.OpeFee4.value)<=0){
		alert("���ý���С�ڵ���0");
		return false;
	}
	var tSel = TempToGrid.getSelNo();
	tempFee = 1000000000 * parseFloat(fm.OpeFee4.value); 
	//ȡ�û�����Ϣ 
	var count=TempToGrid.mulLineCount; //�õ�MulLine������ 
	for(var i=0;i<count;i++){ 
		if(i!=(tSel-1)){
			tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
		}
	}
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		//InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("ҵ����ϼƴ��ڲ����շ��ܺͣ�������¼��ҵ�������Ϣ��");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((tempFee-sumTempFee)/1000000000);
	////////
	TempToGrid.setRowColData(tSel-1,	1	,	fm.InputNo8.value											);
	TempToGrid.setRowColData(tSel-1,	3	,	fm.PayDate.value											);
	TempToGrid.setRowColData(tSel-1,	4	,	pointTwo(parseFloat(fm.OpeFee4.value)));
	TempToGrid.setRowColData(tSel-1,	5	,	fm.OpeCurrency.value									);
	TempToGrid.setRowColData(tSel-1,	7	,	fm.PolicyCom.value										); 		//�������
	TempToGrid.setRowColData(tSel-1,	9 ,	fm.AgentGroup1.value									);		//�����˱���
	TempToGrid.setRowColData(tSel-1,	10,	fm.AgentCode1.value										);		//�����˱���
	TempToGrid.setRowColData(tSel-1,	11,	fm.InputNo7.value											); 	  //ҵ�����
	TempToGrid.setRowColData(tSel-1,	12,	'10' 																	); 		//ҵ���������
	TempToGrid.setRowColData(tSel-1,	18,	fm.TempFeeType.value									);    //	
	TempToGrid.setRowColData(tSel-1,	19	,	fm.OpeCurrencyName.value						);
	TempToGrid.setRowColData(tSel-1	,	20,	fm.TempFeeNo1.value										);
	TempToGrid.setRowColData(tSel-1,	2	,	FinFeeGrid.getRowColData(0,1));		
	
	return true;
}

//����
function modClaimPolicy(){
	if(!clmFlag){
		alert("û�в鵽Ӧ�ռ�¼");
		return false ;
	}
	if(parseFloat(fm.OpeFee5.value)<=0){
		alert("���ý���С�ڵ���0");
		return false;
	}
	var tSel = TempToGrid.getSelNo();
	tempFee = 1000000000 * parseFloat(fm.OpeFee5.value); 
	//ȡ�û�����Ϣ 
	var count=TempToGrid.mulLineCount; //�õ�MulLine������ 
	for(var i=0;i<count;i++){ 
		if(i!=(tSel-1)){
			tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
		}
	}
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		//InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("ҵ����ϼƴ��ڲ����շ��ܺͣ�������¼��ҵ�������Ϣ��");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((tempFee-sumTempFee)/1000000000);
	
	TempToGrid.setRowColData(tSel-1,	1	,	fm.InputNo11.value										);		
	TempToGrid.setRowColData(tSel-1,	3	,	fm.PayDate.value											);		
	TempToGrid.setRowColData(tSel-1,	4	,	pointTwo(parseFloat(fm.OpeFee5.value)));		
	TempToGrid.setRowColData(tSel-1,	5	,	fm.OpeCurrency.value									);		
	TempToGrid.setRowColData(tSel-1,	7	,	fm.PolicyCom.value										); 		//�������
	TempToGrid.setRowColData(tSel-1,	9 ,	fm.AgentGroup1.value									);		//�����˱���
	TempToGrid.setRowColData(tSel-1,	10,	fm.AgentCode1.value										);		//�����˱���
	TempToGrid.setRowColData(tSel-1,	11,	fm.InputNo12.value										);		//ҵ�����
	TempToGrid.setRowColData(tSel-1,	12,	'9' 																	); 		//ҵ��������� ���⡮9��
	TempToGrid.setRowColData(tSel-1,	18,	fm.TempFeeType.value									);    //	
	TempToGrid.setRowColData(tSel-1,	19,	fm.OpeCurrencyName.value							);
	TempToGrid.setRowColData(tSel-1,	20,	fm.TempFeeNo1.value										);
	TempToGrid.setRowColData(tSel-1,	2	,	FinFeeGrid.getRowColData(0,1));			
	
	return true;
}

//�����ڽɷ�
function modUrgePayPolicy(){
	if(!urgeFlag){
		alert("û�в鵽������¼");
		return false ;
	}
	if(parseFloat(fm.OpeFee6.value)<=0){
		alert("���ý���С�ڵ���0");
		return false;
	}
	var tSel = TempToGrid.getSelNo();
	tempFee = 1000000000 * parseFloat(fm.OpeFee6.value); 
	//ȡ�û�����Ϣ 
	var count=TempToGrid.mulLineCount; //�õ�MulLine������ 
	for(var i=0;i<count;i++){ 
		if(i!=(tSel-1)){
			tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
		}
	}
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		//InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("ҵ����ϼƴ��ڲ����շ��ܺͣ�������¼��ҵ�������Ϣ��");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((tempFee-sumTempFee)/1000000000);
	
	TempToGrid.setRowColData(tSel-1,	1	,	fm.InputNo11.value										);
	TempToGrid.setRowColData(tSel-1,	3	,	fm.PayDate.value											);
	TempToGrid.setRowColData(tSel-1,	4	,	pointTwo(parseFloat(fm.OpeFee6.value)));
	TempToGrid.setRowColData(tSel-1,	5	,	fm.OpeCurrency.value									);
	TempToGrid.setRowColData(tSel-1,	7	,	fm.PolicyCom.value										); 		//�������
	TempToGrid.setRowColData(tSel-1,	9 ,	fm.AgentGroup1.value									);		//�����˱���
	TempToGrid.setRowColData(tSel-1,	10,	fm.AgentCode1.value										);		//�����˱���
	TempToGrid.setRowColData(tSel-1,	11,	fm.InputNo22.value										); 		//ҵ�����
	TempToGrid.setRowColData(tSel-1,	12,	'2' 																	); 		//ҵ���������
	TempToGrid.setRowColData(tSel-1,	18,	fm.TempFeeType.value									);    //
	TempToGrid.setRowColData(tSel-1,	19	,	fm.OpeCurrencyName.value						);
	TempToGrid.setRowColData(tSel-1	,	20,	fm.TempFeeNo1.value										);
	TempToGrid.setRowColData(tSel-1,	2	,	FinFeeGrid.getRowColData(0,1));		
	return true;
}

function showInform(){
	
	var tSel = TempToGrid.getSelNo();
	
	showTempFeeTypeInput(TempToGrid.getRowColData(tSel-1,18));
	//�µ�
	if(TempToGrid.getRowColData(tSel-1,18)=="1"){
		fm.PayDate.value					= TempToGrid.getRowColData(tSel-1,	3	 );
		fm.OpeFee1.value					= TempToGrid.getRowColData(tSel-1,	4	 );
		fm.OpeCurrency.value			= TempToGrid.getRowColData(tSel-1,	5	 );
		fm.PolicyCom.value				= TempToGrid.getRowColData(tSel-1,	7	 );
		fm.AgentGroup.value				= TempToGrid.getRowColData(tSel-1,	9  );
		fm.AgentCode.value				= TempToGrid.getRowColData(tSel-1,	10 );
		fm.InputNob.value					= TempToGrid.getRowColData(tSel-1,	16 );
		fm.InputNo1.value         = TempToGrid.getRowColData(tSel-1,	11 );
		fm.TempFeeType.value      = TempToGrid.getRowColData(tSel-1,	18 );
		fm.OpeCurrencyName.value  = TempToGrid.getRowColData(tSel-1,	19 );
		fm.TempFeeNo1.value				= TempToGrid.getRowColData(tSel-1,	20 );
		fm.TempFeeTypeName.value  = "�µ�����" ;
	}else if(TempToGrid.getRowColData(tSel-1,18)=="2"){                
		                                                                 
		fm.GetNoticeNo.value 			= TempToGrid.getRowColData(tSel-1,	1	 );
		fm.PayDate.value		 			= TempToGrid.getRowColData(tSel-1,	3	 );
		fm.OpeFee2.value		 			= TempToGrid.getRowColData(tSel-1,	4	 );
		fm.OpeCurrency.value 			= TempToGrid.getRowColData(tSel-1,	5	 );		//����
		fm.PolicyCom.value	 			= TempToGrid.getRowColData(tSel-1,	7	 ); 		//�������
		fm.AgentGroup1.value 			= TempToGrid.getRowColData(tSel-1,	9  );		//�����˱���
		fm.AgentCode1.value	 			= TempToGrid.getRowColData(tSel-1,	10 );		//�����˱���
		fm.InputNo3.value		 			= TempToGrid.getRowColData(tSel-1,	11 ); 		//ҵ�����
		fm.TempFeeType.value 			= TempToGrid.getRowColData(tSel-1,	18 );
		fm.OpeCurrencyName.value  = TempToGrid.getRowColData(tSel-1,	19 );
		fm.TempFeeNo1.value				= TempToGrid.getRowColData(tSel-1,	20 );    
		fm.TempFeeTypeName.value  = "���ڴ��ս���" ; 
		                                                                 
	}else if(TempToGrid.getRowColData(tSel-1,18)=="3"){                
		                                                                 
		fm.InputNo11.value				=	TempToGrid.getRowColData(tSel-1,	1	 ); 		//����֪ͨ��
		fm.PayDate.value					=	TempToGrid.getRowColData(tSel-1,	3	 );		//
		fm.OpeFee3.value					=	TempToGrid.getRowColData(tSel-1,	4	 );		//
		fm.OpeCurrency.value			=	TempToGrid.getRowColData(tSel-1,	5	 );		//
		fm.PolicyCom.value				=	TempToGrid.getRowColData(tSel-1,	7	 ); 		//�������
		fm.AgentGroup1.value			=	TempToGrid.getRowColData(tSel-1,	9	 );		//�����˱���
		fm.AgentCode1.value				=	TempToGrid.getRowColData(tSel-1,	10 );		//�����˱���
		fm.InputNo5.value					=	TempToGrid.getRowColData(tSel-1,	11 ); 		//ҵ�����
		fm.TempFeeType.value			=	TempToGrid.getRowColData(tSel-1,	18 );    //
		fm.OpeCurrencyName.value  = TempToGrid.getRowColData(tSel-1,	19 );
		fm.TempFeeNo1.value				= TempToGrid.getRowColData(tSel-1,	20 );
		fm.TempFeeTypeName.value  = "���ڽ���" ; 
		                                                                 
	}else if(TempToGrid.getRowColData(tSel-1,18)=="4"){                
		                                                                 
		fm.InputNo8.value					= TempToGrid.getRowColData(tSel-1,	1	 );
		fm.PayDate.value					= TempToGrid.getRowColData(tSel-1,	3	 );
		fm.OpeFee4.value					= TempToGrid.getRowColData(tSel-1,	4	 );
		fm.OpeCurrency.value			= TempToGrid.getRowColData(tSel-1,	5	 );
		fm.PolicyCom.value				= TempToGrid.getRowColData(tSel-1,	7	 ); 		//�������
		fm.AgentGroup1.value			= TempToGrid.getRowColData(tSel-1,	9  );		//�����˱���
		fm.AgentCode1.value				= TempToGrid.getRowColData(tSel-1,	10 );		//�����˱���
		fm.InputNo7.value					= TempToGrid.getRowColData(tSel-1,	11 ); 	  //ҵ�����
		fm.TempFeeType.value			= TempToGrid.getRowColData(tSel-1,	18 );    //
		fm.OpeCurrencyName.value  = TempToGrid.getRowColData(tSel-1,	19 );
		fm.TempFeeNo1.value				= TempToGrid.getRowColData(tSel-1,	20 );
		fm.TempFeeTypeName.value  = "��ȫ����" ; 
		                                                                 
	}else if(TempToGrid.getRowColData(tSel-1,18)=="5"){                
		fm.InputNo11.value				= TempToGrid.getRowColData(tSel-1,	1	 );		
		fm.PayDate.value					= TempToGrid.getRowColData(tSel-1,	3	 );		
		fm.OpeFee5.value					= TempToGrid.getRowColData(tSel-1,	4	 );		
		fm.OpeCurrency.value			= TempToGrid.getRowColData(tSel-1,	5	 );		
		fm.PolicyCom.value				= TempToGrid.getRowColData(tSel-1,	7	 ); 		//�������
		fm.AgentGroup1.value			= TempToGrid.getRowColData(tSel-1,	9  );		//�����˱���
		fm.AgentCode1.value				= TempToGrid.getRowColData(tSel-1,	10 );		//�����˱���
		fm.InputNo12.value				= TempToGrid.getRowColData(tSel-1,	11 );		//ҵ�����
		fm.TempFeeType.value			= TempToGrid.getRowColData(tSel-1,	18 );    //
		fm.OpeCurrencyName.value  = TempToGrid.getRowColData(tSel-1,	19 );
		fm.TempFeeNo1.value				= TempToGrid.getRowColData(tSel-1,	20 );
		fm.TempFeeTypeName.value  = "�����շ�" ; 
		
	}else if(TempToGrid.getRowColData(tSel-1,18)=="6"){
		
		fm.InputNo11.value	 			= TempToGrid.getRowColData(tSel-1,	1	 );
		fm.PayDate.value		 			= TempToGrid.getRowColData(tSel-1,	3	 );
		fm.OpeFee6.value		 			= TempToGrid.getRowColData(tSel-1,	4	 );
		fm.OpeCurrency.value 			= TempToGrid.getRowColData(tSel-1,	5	 );
		fm.PolicyCom.value	 			= TempToGrid.getRowColData(tSel-1,	7	 ); 		//�������
		fm.AgentGroup1.value 			= TempToGrid.getRowColData(tSel-1,	9  );		//�����˱���
		fm.AgentCode1.value	 			= TempToGrid.getRowColData(tSel-1,	10 );		//�����˱���
		fm.InputNo22.value	 			= TempToGrid.getRowColData(tSel-1,	11 ); 		//ҵ�����
		fm.TempFeeType.value 			= TempToGrid.getRowColData(tSel-1,	18 );    //
		fm.OpeCurrencyName.value  = TempToGrid.getRowColData(tSel-1,	19 );
		fm.TempFeeNo1.value				= TempToGrid.getRowColData(tSel-1,	20 );
		fm.TempFeeTypeName.value  = "�����ڽ���" ; 
		
	}
	
	return true;
	
}

function modifyData(){
	
  if(!verifyInput7()){
		return false;
	}	
	var tSel = TempToGrid.getSelNo();
	
	if(tSel == "" || tSel == null ||tSel=="null")  //�ж��Ƿ�ѡ��
	{
	    alert("����ѡ��Ҫ�޸ĵļ�¼");
	    return false;
	}
	
	showTempFeeTypeInput(TempToGrid.getRowColData(tSel-1,18));
	//�µ�
	if(TempToGrid.getRowColData(tSel-1,18)=="1"){
		modNewPolicy();
	}else if(TempToGrid.getRowColData(tSel-1,18)=="2"){
		modConPolicy();
	}else if(TempToGrid.getRowColData(tSel-1,18)=="3"){
		modConBefPolicy();
	}else if(TempToGrid.getRowColData(tSel-1,18)=="4"){
		modEdorPolicy();
	}else if(TempToGrid.getRowColData(tSel-1,18)=="5"){
		modClaimPolicy();
	}else if(TempToGrid.getRowColData(tSel-1,18)=="6"){
		modUrgePayPolicy();
	}
	
	return true;
}


function sumMoney(){
	tempFee = 0;
	//ȡ�û�����Ϣ 
	var count=TempToGrid.mulLineCount; //�õ�MulLine������ 
	for(var i=0;i<count;i++){ 
		tempFee = tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((tempFee-sumTempFee)/1000000000);
	
	return true;
}

function verifyInput7(){
	if(FinFeeGrid. mulLineCount==0){
		alert("����ȷ�ϲ��񽻷���Ϣ��");
		return false;
	}
	if(fm.TempFeeType.value==''||fm.TempFeeType.value==''){
		alert("����ѡ�񽻷�����");
		return false;
	}
	if(fm.OpeCurrency.value==''||fm.OpeCurrency.value==null){
		alert("ҵ����ֲ���Ϊ��");
		return false;
	}
  if(!_CheckCurrency())
	{
		alert("ҵ�������ѡ���ֲ�һ��");
		return false;
	}
	if(fm.OpeCurrency.value!=FinFeeGrid.getRowColData(0,4)){
		alert("������շѱ��ֲ�һ�£�");
		return false;
	}
	if(fm.TempFeeType.value=='1'){
		if(fm.AgentCode.value==''||fm.AgentCode.value==null){
			alert("�����˺��벻��Ϊ��");
			return false;
		}
		if(fm.InputNo1.value==''||fm.InputNo1.value==null){
			alert("Ͷ�����Ų���Ϊ��");
			return false;
		}
		if(fm.OpeFee1.value==''||fm.OpeFee1.value==null){
			alert("���ý���Ϊ��");
			return false;
		}
		if(!verifyNumber("���ý��",fm.OpeFee1.value)){
			alert("���ѽ�����Ч����");
			return false;
		}
		var strSql = "";
    mySql.setSqlId("TempFeeInputInputSql_15");
    mySql.addPara('PolNo' , fm.InputNo1.value);
    // select agentcode from lccont where contno='?PolNo?' ;
    var strArray=easyExecSql(mySql.getString());
	  if(strArray != null && strArray != ""){
	  	var agentCode = strArray[0][0];
	  	if(agentCode != fm.AgentCode.value){
	  		alert("������������¼������˲��� ");
	  		return false;
	  	}
		}
	}
	if(fm.TempFeeType.value=='2'){
		if(fm.InputNo3.value==''||fm.InputNo3.value==null){
			alert("�����Ų���Ϊ��");
			return false;
		}
		if(fm.OpeFee2.value==''||fm.OpeFee2.value==null){
			alert("���ý���Ϊ��");
			return false;
		}
		if(!verifyNumber("���ý��",fm.OpeFee2.value)){
			alert("���ѽ�����Ч����");
			return false;
		}
	}
	
	if(fm.TempFeeType.value=='3'){
		if(fm.InputNo5.value==''||fm.InputNo5.value==null){
			alert("�����Ų���Ϊ��");
			return false;
		}
		if(fm.OpeFee3.value==''||fm.OpeFee3.value==null){
			alert("���ý���Ϊ��");
			return false;
		}
		if(!verifyNumber("���ý��",fm.OpeFee3.value)){
			alert("���ѽ�����Ч����");
			return false;
		}
	}
	
	if(fm.TempFeeType.value=='4'){
		if(fm.InputNo7.value==''||fm.InputNo7.value==null){
			alert("��ȫ����Ų���Ϊ��");
			return false;
		}
		if(fm.OpeFee4.value==''||fm.OpeFee4.value==null){
			alert("���ý���Ϊ��");
			return false;
		}
		if(!verifyNumber("���ý��",fm.OpeFee4.value)){
			alert("���ѽ�����Ч����");
			return false;
		}
	}
	
	if(fm.TempFeeType.value=='5'){
		if(fm.InputNo12.value==''||fm.InputNo12.value==null){
			alert("�ⰸ�Ų���Ϊ��");
			return false;
		}
		if(fm.OpeFee5.value==''||fm.OpeFee5.value==null){
			alert("���ý���Ϊ��");
			return false;
		}
		if(!verifyNumber("���ý��",fm.OpeFee5.value)){
			alert("���ѽ�����Ч����");
			return false;
		}
	}
	
	if(fm.TempFeeType.value=='6'){
		if(fm.InputNo22.value==''||fm.InputNo22.value==null){
			alert("�����Ų���Ϊ��");
			return false;
		}
		if(fm.OpeFee6.value==''||fm.OpeFee6.value==null){
			alert("���ý���Ϊ��");
			return false;
		}
		if(!verifyNumber("���ý��",fm.OpeFee6.value)){
			alert("���ѽ�����Ч����");
			return false;
		}
	}
	
	var NTempNo = fm.TempFeeNo1.value;
	if(fm.TempFeeNo1.value == '' || fm.TempFeeNo1.value == null)
	{
	}
	else
	{
		if( NTempNo.substr(0,2) == "TS")
		{
		}
		else
		{
			if(!_CheckTempNo())
			{
				alert("�����վ��Ѿ����գ������ظ�ʹ�ã�");
				return false;
			}
		
			if(!_CheckDoc())
		  {  
			  alert("û�н������վݷ��ŵ���ѡ������ˣ�");
			  return false;
		  }
		}
		
		if( NTempNo.substr(0,2) == "TS")
		{
		}
		else
		{
		  if(!_CheckMaxDate())
		  {
			  if(!confirm('�����վ��ѳ�����Ч�ڣ��Ƿ�ȷ��ʹ�ã�')){      
          return;      
        } 
		  }
		}

		
	}
	
	if(fm.ManageCom.value.substring(0,4)!=fm.PolicyCom.value.substring(0,4)){
		if(!confirm("ҵ�����Ϊ��ؽɷѣ�Ҫȷ����")){
			return false;
		}
	}
	return true;
}

function getAppntName(){
	if(fm.InputNo1.value==null||fm.InputNo1.value==""){
		return true;
	}
	fm.InputNob.value = "";
	var strSql = "" ;
	mySql.setSqlId("TempFeeInputInputSql_17");
  mySql.addPara('PolNo' , fm.InputNo1.value);
	
	var TarrResult = easyQueryVer3( mySql.getString(), 1, 1, 1);
	if(TarrResult!=null&&TarrResult!=""){
	 	addFlag = true;
	 	var ArrData = decodeEasyQueryResult(TarrResult);
	 	if(ArrData[0][0]!=""&&ArrData[0][0]!=null){
	 		fm.InputNob.value = ArrData[0][0] ;
	 	}
	}
	return true;
}

function getAgentCode()
{
	if((fm.all('InputNo3').value!=null||fm.all('InputNo3').value!="")&&(fm.all('AgentCode').value==""||fm.all('AgentCode').value==null))
	{
		var strSql = "";
    mySql.setSqlId("TempFeeInputInputSql_20");
    mySql.addPara('InputNo3' , fm.InputNo3.value);
    // select AgentCode,managecom,agentgroup from ljspay where otherno='?InputNo3?' ;
	  var TarrResult = easyQueryVer3( mySql.getString(), 1, 1, 1);
	  if(TarrResult!=null&&TarrResult!=""){
	  	addFlag = true;
	  	var ArrData = decodeEasyQueryResult(TarrResult);
	  	if(ArrData[0][0]==""||ArrData[0][2]==""){
	  	  alert("δ�鵽��������Ϣ"); 
	  	}
	  	if(ArrData[0][1]==""){
	  	  alert("δ�鵽�����������");
	  	}
	    fm.AgentCode1.value		= ArrData[0][0];
	    fm.PolicyCom.value  	=	ArrData[0][1];
	    fm.AgentGroup1.value	=	ArrData[0][2];
	    fm.AgentName1.value		=	ArrData[0][3];
	    fm.GetNoticeNo.value	=	ArrData[0][4];
	  }
	  else{
	  	alert("�޴��ս������ݣ���������ڴ߽ɳ鵵");	 
	  	addFlag = false;
	  	fm.AgentCode1.value		= "";
	    fm.PolicyCom.value  	=	"";
	    fm.AgentGroup1.value	=	"";
	    fm.AgentName1.value		=	"";
	    return;
	  }
  }
}

/*********************************************************************
 *  ��ش��ա���Ԥ�����ڱ��Ѳ�ѯ�����������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getPolicyCom()
{
	if (fm.InputNo5.value!=null&&fm.InputNo5.value!="")
	{
	  var flag = "";
	  var strSql2 = "";
    mySql.setSqlId("TempFeeInputInputSql_29");
    mySql.addPara('InputNo5',fm.InputNo5.value);
	  var arrResult2 = easyExecSql( mySql.getString());
	  if(arrResult2 == null)
	  {
	    var strSql = "";
      mySql.setSqlId("TempFeeInputInputSql_30"); //select managecom from LCCont where contno='?InputNo5?'
      mySql.addPara('InputNo5',fm.InputNo5.value);
      var arrResult = easyExecSql(mySql.getString());
      if(arrResult==null||arrResult=="NULL"||arrResult=="")
      {
        alert("�޴˱�����Ϣ�����������ѯʧ��");	
        fm.PolicyCom.value="";
        fm.AgentCode1.value="";
      	fm.AgentName1.value="";
      	fm.AgentGroup1.value="";
        return false;
      }
      else
  	  {
        fm.PolicyCom.value=arrResult;	  	  
        var ASql = "";
        mySql.setSqlId("TempFeeInputInputSql_31"); //select agentcode from LCCont where contno='?InputNo5?'
        mySql.addPara('InputNo5',fm.InputNo5.value);
        var brrResult = easyExecSql( mySql.getString());
        
        if(brrResult==null||brrResult=="NULL"||brrResult=="")
        {
        	fm.AgentCode1.value="";
      		fm.AgentName1.value="";
      		fm.AgentGroup1.value="";
          alert("�޴˱�����Ϣ�������˲�ѯʧ��");	
          fm.AgentCode.value="";
          return false;
        }
        else
      	{
      		fm.AgentCode1.value=brrResult[0][0];
      		fm.AgentName1.value=brrResult[0][1];
      		fm.AgentGroup1.value=brrResult[0][2];
      	}
      }	
     }
     else
     {
      var strSql = "";
      mySql.setSqlId("TempFeeInputInputSql_32");
      mySql.addPara('InputNo5',fm.InputNo5.value);
      var arrResult = easyExecSql(mySql.getString());
      if(arrResult==null||arrResult=="NULL"||arrResult=="")
      {
        alert("�޴˱�����Ϣ�����������ѯʧ��");	
        fm.PolicyCom.value="";
        return false;
      }
      else
  	  {
        fm.PolicyCom.value=arrResult;	  	  
        var ASql = "";
        mySql.setSqlId("TempFeeInputInputSql_33");
        mySql.addPara('InputNo5',fm.InputNo5.value);
        var brrResult = easyExecSql(mySql.getString());
        if(brrResult==null||brrResult=="NULL"||brrResult=="")
        {
          alert("�޴˱�����Ϣ�������˲�ѯʧ��");	
          fm.AgentCode1.value="";
      		fm.AgentName1.value="";
      		fm.AgentGroup1.value="";
          return false;
        }
        else
       	{
       		fm.AgentCode.value=brrResult;
       	}
      } 
    }
    contFlag = true;
	}
	return true;
}

function getEdorCode()
{
  if(fm.all('PayDate').value == null && fm.all('PayDate').value == "")
  {
  	alert("��¼�뽻������");
  	return false;
  }
  
  if((fm.all('InputNo7').value!=null&&fm.all('InputNo7').value!="")&&(fm.all('InputNo8').value==null||fm.all('InputNo8').value==""))
  {
  	edorFlag=true;
   	var strSql = "";
   	mySql.setSqlId("TempFeeInputInputSql_21");
   	mySql.addPara('InputNo7',fm.InputNo7.value);
   	mySql.addPara('PayDate',fm.all('PayDate').value);
   	var arrResult = easyExecSql( mySql.getString());
   	
   	if(arrResult == null ||arrResult == ""||arrResult=='null')
   	{
   	 alert("û�б�ȫӦ�ռ�¼���Ѿ�������;����ȷ��!");
   	 return false;	
   	}
  	else
  	{
  	  fm.InputNo8.value=arrResult;	//�����վݺ���
  	}
  }	
  
  if((fm.all('InputNo8').value!=null&&fm.all('InputNo8').value!="")&&(fm.all('InputNo7').value==null||fm.all('InputNo7').value==""))
  {
  	edorFlag=true;
   	var strSql = "";
  	mySql.setSqlId("TempFeeInputInputSql_22");
  	mySql.addPara('InputNo8',fm.InputNo8.value);
  	var arrResult = easyExecSql( mySql.getString());
  	if(arrResult==null||arrResult=="")
  	{
  	 alert("��ȫ����Ų����ڣ����������ڲ����շѣ���ȷ��!");	
  	 fm.InputNo7.value="";
  	 fm.InputNo8.value="";
  	}
  	else
  	{
  	 fm.InputNo7.value=arrResult;	//��ȫ�����
  	}
  }
  
  if(fm.all('InputNo8').value!=null&&fm.all('InputNo8').value!=""&&fm.all('InputNo7').value!=null&&fm.all('InputNo7').value!=""){
  	//InputNo7 �����
  	//InputNo8 �վݺ�
  	edorFlag=true;
   	var strSql = "";
  	mySql.setSqlId("TempFeeInputInputSql_22");
  	mySql.addPara('InputNo8',fm.InputNo8.value);
  	var arrResult = easyExecSql( mySql.getString());
  	if(arrResult==null||arrResult==""){
  	 alert("��ȫ����Ų����ڣ����������ڲ����շѣ���ȷ��!");	
  	 fm.InputNo7.value="";
  	 fm.InputNo8.value="";
  	}
  	else{
  	 fm.InputNo7.value=arrResult;	//��ȫ�����
  	}
  }
  if (fm.all('InputNo7').value!=null&&fm.all('InputNo7').value!="")
  {
    var strSql = "";
    mySql.setSqlId("TempFeeInputInputSql_23");
    mySql.addPara('InputNo8',fm.InputNo8.value);
    mySql.addPara('PayDate',fm.all('PayDate').value);
    var arrResult = easyExecSql(mySql.getString());
    
    if(arrResult == null ||arrResult == ""||arrResult=='null'){
      alert("��ȫ����Ų����ڣ����������ڲ����շѣ���ȷ��!");
      return false;
    }else{
      fm.PolicyCom.value=arrResult;
    }         
  }
}

//����
function getLJSPayPolicyCom()
{
  if (fm.InputNo11.value!=null&&fm.InputNo11.value!=""&&fm.InputNo11.value!='null')	
  {
	  var strSql = "";             
    mySql.setSqlId("TempFeeInputInputSql_27");
    mySql.addPara('InputNo11',fm.InputNo11.value);
    var arrResult = easyExecSql( mySql.getString());
    if(arrResult!=null&&arrResult!=""&&arrResult!="null"){
    	clmFlag=true;
    	fm.PolicyCom.value=arrResult[0][0]; 
    	fm.InputNo12.value=arrResult[0][1]; //�վݺ�
    }else{
    	alert("û�в�ѯ������Ӧ�ռ�¼");
    }
  }
  else if (fm.InputNo12.value!=null&&fm.InputNo12.value!=""&&fm.InputNo12.value!='null')
  {
	  var strSql = "";
    mySql.setSqlId("TempFeeInputInputSql_28");
    mySql.addPara('InputNo12',fm.InputNo12.value);
    var arrResult = easyExecSql(mySql.getString());
    if(arrResult!=null&&arrResult!=""&&arrResult!="null")
    {
    	clmFlag=true;
    	fm.PolicyCom.value=arrResult[0][0];
    	fm.InputNo11.value=arrResult[0][1]; //�����
  	}else{
  		alert("û�в�ѯ������Ӧ�ռ�¼");
  	}
  } 
}

//�����ڽ�
function getUrgePolicyCom()
{
	if (fm.InputNo22.value!=null&&fm.InputNo22.value!="")
	{
	  var flag = "";
	  var strSql2 = "";
    mySql.setSqlId("TempFeeInputInputSql_29"); //�ŵ���ѯ
    mySql.addPara('InputNo5',fm.InputNo22.value);
	  var arrResult2 = easyExecSql( mySql.getString());
	  if(arrResult2 == null)
	  {
	    var strSql = "";
      mySql.setSqlId("TempFeeInputInputSql_30"); //select managecom from LCCont where contno='?InputNo5?'
      mySql.addPara('InputNo22',fm.InputNo22.value);
      var arrResult = easyExecSql(mySql.getString());
      if(arrResult==null||arrResult=="NULL"||arrResult=="")
      {
        alert("�޴˱�����Ϣ�����������ѯʧ��");	
        fm.AgentCode1.value = "";
      	fm.AgentName1.value = "";
      	fm.AgentGroup1.value= "";
        fm.PolicyCom.value  = "";
        return false;
      }
      else
  	  {
        fm.PolicyCom.value=arrResult;	  	  
        var ASql = "";
        mySql.setSqlId("TempFeeInputInputSql_31"); //select agentcode from LCCont where contno='?InputNo22?'
        mySql.addPara('InputNo22',fm.InputNo22.value);
        var brrResult = easyExecSql( mySql.getString());
        if(brrResult==null||brrResult=="NULL"||brrResult=="")
        {
          alert("�޴˱�����Ϣ�������˲�ѯʧ��");	
          fm.AgentCode.value="";
          return false;
        }
        else
      	{
      		fm.AgentCode1.value=brrResult[0][0];
      		fm.AgentName1.value=brrResult[0][1];
      		fm.AgentGroup1.value=brrResult[0][2];
      	}
      }	
     }else
     	{ //������ѯ
      var strSql = "";
      mySql.setSqlId("TempFeeInputInputSql_32");
      mySql.addPara('InputNo22',fm.InputNo22.value);
      var arrResult = easyExecSql(mySql.getString());
      if(arrResult==null||arrResult=="NULL"||arrResult=="")
      {
        alert("�޴˱�����Ϣ�����������ѯʧ��");	
        fm.PolicyCom.value="";
        return false;
      }
      else
  	  {
        fm.PolicyCom.value=arrResult;	  	  
        var ASql = "";
        mySql.setSqlId("TempFeeInputInputSql_33");
        mySql.addPara('InputNo22',fm.InputNo22.value);
        var brrResult = easyExecSql(mySql.getString());
        if(brrResult==null||brrResult=="NULL"||brrResult=="")
        {
          alert("�޴˱�����Ϣ�������˲�ѯʧ��");	
          fm.AgentCode.value="";
          return false;
        }
        else
       	{
       		fm.AgentCode.value=brrResult;
       	}
      } 
    }
    urgeFlag=true;
	}	
}

function getImportPath(){
  var strSQL = "";
  strSQL = "select sysvarvalue from ldsysvar where sysvar='LJXmlPath'";
  
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("δ�ҵ��ϴ�·��");
    return;
  }
  //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	
  ImportPath = turnPage.arrDataCacheSet[0][0];
}

function importData(){
	try{
		if(!confirm("ȷ��Ҫ����ѡ�е��ļ�������")){
			return false;
		}
		
		if(fmImport.fileName.value==""||fmImport.fileName.value==null){
			alert("¼�뵼���ļ�·����");
			return false;
		}
		
		if((fmImport.fileName.value).substr((fmImport.fileName.value.length-18),fmImport.fileName.value.length)=='LJFinFeeImport.xls'){
			alert("�뽫�����ļ�����");
			return false;
		}
		var i = 0;
  	getImportPath();
  	
  	ImportFile = fmImport.all('FileName').value; 
  	
  	var showStr="�����������ݡ���"; 
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ; 
  	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  	fmImport.action = "./TempFinFeeImportSave.jsp?ImportPath="+ImportPath ; 
  	fmImport.submit(); //�ύ 
  } catch(ex){
  	showInfo.close( );
  	alert(ex);
  }
}

function queryLJSPay(){
   if(fm.all('InputNo3').value!=""){
   }
   else{
    alert("�����뱣����");
   }
}

function clearFormData(){
	clearOpeTypeInfo();
	fm.TempFeeType.value="";
	fm.TempFeeTypeName.value="";
	TempToGrid.clearData();
	fm.OperateSum.value="";
	fm.OperateSub.value="";
	fm.addButton.style.display='';
		    //����2010-2-26
    initTTempToGrid();  
    initTTempClassToGrid(); 
	return true;
}

function alink(){
	window.location.href="../temp/finfee/import/LJFinFeeImport.xls";
}


/**
 * ɾ��һ��
 */
function _DeleteOne(strPageName, spanID, cObjInstance)
{
	var tStr;
    var t_StrPageName = strPageName || this.instanceName; //ʵ������
    var tObjInstance; //����ָ��
    if (cObjInstance == null)
    {
    	tObjInstance = this;
    }
    else
    {
    	tObjInstance = cObjInstance;
      var spanName = spanID;
      spanID = eval(tObjInstance.formName + ".all('" + spanName + "')");
    }
    tObjInstance.errorString = "";
    var spanObj = eval(tObjInstance.formName + ".all('span" + t_StrPageName + "')");
    try
    {
      spanID.innerHTML = "";
      tObjInstance.errorString = "";
      tObjInstance.mulLineCount = tObjInstance.mulLineCount - 1;
      tStr = "<SPAN id=" + spanID.id + "></SPAN>";
      spanObj.innerHTML = replace(spanObj.innerHTML, tStr, "");
      _ModifyCount(tObjInstance.formName, t_StrPageName, tObjInstance.mulLineCount, tObjInstance);
      
      sumMoney();
    }
    catch (ex)
    {
        _DisplayError("�� MulLine.js --> _DeleteOne �����з����쳣��" + ex, tObjInstance);
    }
}

//��ѯ���վ��Ƿ����·�����ҵ��Ա
function _CheckDoc()
{
	// ƴSQL��䣬��ҳ��ɼ���Ϣ
  var strSql = "";
  var agentCode = "";
  mySql.setSqlId("TempFeeInputInputSql_Temp");
  mySql.addPara('TempNo',fm.all('TempFeeNo1').value);
  agentCode = fm.AgentCode.value;

  if(agentCode == null || agentCode == "")
  {
  	agentCode = "D"+fm.AgentCode1.value;
  }
  else
  {
  	agentCode = "D"+fm.AgentCode.value;
  }
  
  mySql.addPara('AgentCode',agentCode);
  strSql = mySql.getSQL();
  
  //��ѯ���ѻ���sql,���ؽ��
  var strArray = easyExecSql(strSql);
  if(strArray==null){
  	return false;
  }
  return true;
}

function _CheckCurrency()
{
	//��һ���ж�ͬһ��ҵ����루Ͷ�����š������š���ȫ����ţ���TTempToGrid�ı����Ƿ���ͬ
	var flag="";
	var strSql = "";
	for(var i=0;i<TTempToGrid.mulLineCount;i++)
	{ 
		//�µ�
		if(fm.TempFeeType.value=='1')
		{
			//�ж�ҵ������Ƿ���ͬ
		  if(fm.InputNo1.value == TTempToGrid.getRowColData(i,10))
		  {
			  //�жϱ����Ƿ���ͬ
			  if(fm.OpeCurrency.value	 != TTempToGrid.getRowColData(i,18))
			  {
				  flag = "0";
			  }
		  }
		}
		
		//����
		if(fm.TempFeeType.value=='3')
		{
			//�ж�ҵ������Ƿ���ͬ
		  if(fm.InputNo5.value == TTempToGrid.getRowColData(i,10))
		  {
			  //�жϱ����Ƿ���ͬ
			  if(fm.OpeCurrency.value	 != TTempToGrid.getRowColData(i,18))
			  {
				  flag = "0";
			  }
		  }
		}
		
		//��ȫ
		if(fm.TempFeeType.value=='4')
		{
			//�ж�ҵ������Ƿ���ͬ
		  if(fm.InputNo7.value == TTempToGrid.getRowColData(i,10))
		  {
			  //�жϱ����Ƿ���ͬ
			  if(fm.OpeCurrency.value	 != TTempToGrid.getRowColData(i,18))
			  {
				  flag = "0";
			  }
		  }
		}

	}
//	alert("flag:"+flag);
	//�ڶ����ж���ljtempfee�ı����Ƿ���ͬ
	if (flag == null || flag=="" || flag=="null")
	{
		var strSql = "";
		var OtherNo = "";
		if(fm.TempFeeType.value=='1')
		{
			OtherNo = fm.InputNo1.value;
		}
		if(fm.TempFeeType.value=='3')
		{
			OtherNo = fm.InputNo1.value;
		}
		if(fm.TempFeeType.value=='4')
		{
			OtherNo = fm.InputNo1.value;
		}
	  mySql.setSqlId("TempFeeInputInputSql_LJCurrency");
    mySql.addPara('OtherNo',OtherNo);
    strSql = mySql.getSQL();
  
    //��ѯ���ѻ���sql,���ؽ��
    var strArray = easyExecSql(strSql);
    if(strArray != null){
  	  if(fm.OpeCurrency.value != strArray[0][0])
  	  {
  	  	flag = "0";
  	  }
    }
	}

	//�������ж���lccont�еı����Ƿ���ͬ
	if (flag == null || flag=="" || flag=="null")
	{
		 var strSql = "";
		 var OtherNo = "";
		 if(fm.TempFeeType.value=='1')
		 {
			 OtherNo = fm.InputNo1.value;
		 }
		 if(fm.TempFeeType.value=='3')
		 {
			 OtherNo = fm.InputNo1.value;
		 }
		 if(fm.TempFeeType.value=='4')
		 {
			 OtherNo = fm.InputNo1.value;
		 }
		 
	   mySql.setSqlId("TempFeeInputInputSql_LCCurrency");
     mySql.addPara('OtherNo',OtherNo);
     strSql = mySql.getSQL();
     //��ѯ���ѻ���sql,���ؽ��
     var strArray = easyExecSql(strSql);
     if(strArray != null){
  	 if(fm.OpeCurrency.value != strArray[0][0])
  	 {
  	    flag = "0";
  	 }
    }
	}
	
	//��������жϺ�flag==null����ʾΪ��һ�ν����ո��ѣ�û�б�������
	if (flag == null || flag=="" || flag=="null")
	{
		flag == "1";
	}
  
  if(flag == "0")
  {
  	return false;
  }
  else
  {
  	return true;
  }
}

//��ѯ���վ��Ƿ��ѽ������ձ�
function _CheckTempNo()
{
	// ƴSQL��䣬��ҳ��ɼ���Ϣ
  var strSql = "";
  var agentCode = "";
  mySql.setSqlId("TempFeeInputInputSql_TempNo");
  mySql.addPara('TempNo',fm.all('TempFeeNo1').value);
  strSql = mySql.getSQL();
  
  //��ѯ���ѻ���sql,���ؽ��
  var strArray = easyExecSql(strSql);

  if(strArray==null){
  	return true;
  }
  return false;
}

//��ѯ���վ��Ƿ��ѹ��������
function _CheckMaxDate()
{
	// ƴSQL��䣬��ҳ��ɼ���Ϣ
  var strSql = "";
  var agentCode = "";
  mySql.setSqlId("TempFeeInputInputSql_MaxDate");
  mySql.addPara('TempNo',fm.all('TempFeeNo1').value);
  mySql.addPara('Date',fm.all('PayDate').value);
  strSql = mySql.getSQL();
  
  //��ѯ���ѻ���sql,���ؽ��
  var strArray = easyExecSql(strSql);
  if(strArray==null){
  	return false;
  }
  return true;
}

//����2010-2-26
//���һ�ʼ�¼
function addRecord()
{
	  
	//�ֽ���ӵ�������
	var EnterAccDate=""; //��������
//	if(FinFeeGrid.getRowColData(0,1)=="1"){
		EnterAccDate=fm.all('PayDate').value;
//	}
  var TempToGridCount=TempToGrid.mulLineCount; //TempToGrid������ 
	//У�����վݺ����Ƿ�Ψһ
	var ccount=TTempToGrid.mulLineCount;
	for(c=0;c<TempToGridCount;c++){
		for(c1=0;c1<ccount;c1++){
			if(TempToGrid.getRowColData(c,20)!= TTempToGrid.getRowColData(c1,1)){
						alert(" ���ڶ�����վݺ��룬��ֿ�������");
						return false;
			}
		}
	} 
	
	//���У�飬У��ҵ��ϼƽ���Ƿ�������շ��ܽ����ȣ�gaoln
	var tempFee =0; 
	for(var i=0;i<TempToGridCount;i++)
	{ 
		tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
	}
	if(pointTwo(tempFee)!=pointTwo(sumTempFee))
	{
		alert("ҵ����ϼƱ�����ڲ����շ��ܺͣ���������");
		return false;
	}
	 
  //�ݽ��ѷ�����Ϣ(TempToGrid)���������ύ���ݵ�(TTempClassToGrid)
 	for(i=0;i<TempToGridCount;i++)
 	{	
 		 var tcount=TTempClassToGrid.mulLineCount; //�õ�MulLine������ 

 		 TTempClassToGrid.addOne("TTempClassToGrid");  
     TTempClassToGrid.setRowColData(tcount,1,TempToGrid.getRowColData(i,20));  
     TTempClassToGrid.setRowColData(tcount,2,FinFeeGrid.getRowColData(0,1));
     TTempClassToGrid.setRowColData(tcount,3,fm.all('PayDate').value);
     TTempClassToGrid.setRowColData(tcount,4,TempToGrid.getRowColData(i,4));
     TTempClassToGrid.setRowColData(tcount,5,TempToGrid.getRowColData(i,5));
     TTempClassToGrid.setRowColData(tcount,6,TempToGrid.getRowColData(i,19));
     TTempClassToGrid.setRowColData(tcount,7,EnterAccDate); 
     TTempClassToGrid.setRowColData(tcount,8,fm.all('ManageCom').value);   
     TTempClassToGrid.setRowColData(tcount,9,FinFeeGrid.getRowColData(0,5));  
     TTempClassToGrid.setRowColData(tcount,10,FinFeeGrid.getRowColData(0,6));   
     TTempClassToGrid.setRowColData(tcount,11,FinFeeGrid.getRowColData(0,7));    
     TTempClassToGrid.setRowColData(tcount,12,FinFeeGrid.getRowColData(0,9));    
     TTempClassToGrid.setRowColData(tcount,13,FinFeeGrid.getRowColData(0,10));    
     TTempClassToGrid.setRowColData(tcount,14,FinFeeGrid.getRowColData(0,11));
     TTempClassToGrid.setRowColData(tcount,15,FinFeeGrid.getRowColData(0,12));
     TTempClassToGrid.setRowColData(tcount,16,FinFeeGrid.getRowColData(0,13));
     TTempClassToGrid.setRowColData(tcount,17,TempToGrid.getRowColData(i,13));
     TTempClassToGrid.setRowColData(tcount,18,TempToGrid.getRowColData(i,14));
     TTempClassToGrid.setRowColData(tcount,19,FinFeeGrid.getRowColData(0,7));
     TTempClassToGrid.setRowColData(tcount,20,FinFeeGrid.getRowColData(0,14));
     TTempClassToGrid.setRowColData(tcount,21,FinFeeGrid.getRowColData(0,15));
		 TTempClassToGrid.setRowColData(tcount,22,TempToGrid.getRowColData(i,11));
		 TTempClassToGrid.setRowColData(tcount,23,TempToGrid.getRowColData(i,12));  
		 TTempClassToGrid.setRowColData(tcount,24,FinFeeGrid.getRowColData(0,19));   
     //������������
     var scount=TTempToGrid.mulLineCount;
     if(scount<=0){
	 	 				TTempToGrid.addOne("TTempToGrid");                                                       
	 	 				TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	1,TempToGrid.getRowColData(i,20));      
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	2,TempToGrid.getRowColData(0,18));  
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	3,fm.all('PayDate').value);        
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	4,TempToGrid.getRowColData(i,4));  
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	5,EnterAccDate); 		 
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	6,fm.all('ManageCom').value);		   
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	7,TempToGrid.getRowColData(i,8));		
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	8,TempToGrid.getRowColData(i,9)); 	
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	9,TempToGrid.getRowColData(i,10)); 
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	10,TempToGrid.getRowColData(i,11));
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	11,TempToGrid.getRowColData(i,12));
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	12,TempToGrid.getRowColData(i,13));
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	13,TempToGrid.getRowColData(i,14));
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	14,TempToGrid.getRowColData(i,15));
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	15,TempToGrid.getRowColData(i,16));
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	16,TempToGrid.getRowColData(i,17));
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	17,TempToGrid.getRowColData(i,1));
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	18,TempToGrid.getRowColData(i,5));						
     	
     }else{
     			 var findFlag=0;
					 for(j=0;j<scount;j++)
				 	 {	
				 	 		//������׷��,�������ۼ�,�ݲ�֧��ɾ��
//				 	 		alert(TTempToGrid.getRowColData(j,1)+"&&"+TempToGrid.getRowColData(i,20)+"||"+TTempToGrid.getRowColData(j,2)+"&&"+TempToGrid.getRowColData(i,2)+"||"+TTempToGrid.getRowColData(j,7)+"&&"+TempToGrid.getRowColData(i,8)+"||"+TTempToGrid.getRowColData(j,10)+"&&"+ TempToGrid.getRowColData(i,11)+"||"+TTempToGrid.getRowColData(j,11)+"&&"+TempToGrid.getRowColData(i,12));
				 	 		//alert(TTempToGrid.getRowColData(j,10)+"&&"+ TempToGrid.getRowColData(i,11)+"||"+TTempToGrid.getRowColData(j,11)+"&&"+TempToGrid.getRowColData(i,12));
//				 	 		if(TTempToGrid.getRowColData(j,1) == TempToGrid.getRowColData(i,20) &&
//				 	 			 TTempToGrid.getRowColData(j,2) == TempToGrid.getRowColData(i,21) &&
//				 	 			 TTempToGrid.getRowColData(j,7) == TempToGrid.getRowColData(i,8) &&
//				 	 			 TTempToGrid.getRowColData(j,10) == TempToGrid.getRowColData(i,11) &&
//				 	 			 TTempToGrid.getRowColData(j,11) == TempToGrid.getRowColData(i,12) 
//				 	 		){
				 	 		if(				 	 						 	 			 
				 	 			 TTempToGrid.getRowColData(j,10) == TempToGrid.getRowColData(i,11) &&
				 	 			 TTempToGrid.getRowColData(j,11) == TempToGrid.getRowColData(i,12) &&
				 	 			 TTempToGrid.getRowColData(j,2) == TempToGrid.getRowColData(i,18)  &&
				 	 			 TTempToGrid.getRowColData(j,1) == TempToGrid.getRowColData(i,20)
				 	 		){
//				 	 				alert(TTempToGrid.getRowColData(j,4)+"&&"+TempToGrid.getRowColData(i,4));
//				 	 				alert(pointTwo(parseFloat(TTempToGrid.getRowColData(j,4))+parseFloat(TempToGrid.getRowColData(i,4))));
									TTempToGrid.setRowColData(j,	4,pointTwo(parseFloat(TTempToGrid.getRowColData(j,4))+parseFloat(TempToGrid.getRowColData(i,4))));		 	 			
				 	 				findFlag=1;
				 	 		}				 	 		
					 } 
					 if(findFlag!=1){
					 				TTempToGrid.addOne("TTempToGrid"); 
				 	 				TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	1,TempToGrid.getRowColData(i,20));
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	2,TempToGrid.getRowColData(0,18));
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	3,fm.all('PayDate').value);
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	4,TempToGrid.getRowColData(i,4));
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	5,EnterAccDate); 		
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	6,fm.all('ManageCom').value);		
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	7,TempToGrid.getRowColData(i,8));		
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	8,TempToGrid.getRowColData(i,9)); 		
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	9,TempToGrid.getRowColData(i,10)); 		
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	10,TempToGrid.getRowColData(i,11));    
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	11,TempToGrid.getRowColData(i,12));
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	12,TempToGrid.getRowColData(i,13));
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	13,TempToGrid.getRowColData(i,14)); 		
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	14,TempToGrid.getRowColData(i,15));    
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	15,TempToGrid.getRowColData(i,16));
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	16,TempToGrid.getRowColData(i,17));
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	17,TempToGrid.getRowColData(i,1));
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	18,TempToGrid.getRowColData(i,5));
					 }
		 }            
	}
	initFinFeeGrid();
	initTempToGrid();
	fm.TempFeeType.value="";
	fm.TempFeeTypeName.value="";
	fm.OperateSum.value="";
}
function submitForm1(){	
	if(TTempToGrid.mulLineCount==0 || TTempClassToGrid.mulLineCount==0){
		alert(" ��¼������շ���Ϣ��");
		return false;
	}
	try {
		  	var i = 0; 		  	
		  	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"; 
		  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		  	var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
    showInfo.focus(); 
				fmSave.action = "./TempFinFeeSave.jsp"; 
		  	fmSave.submit(); //�ύ
  } catch(ex){
  	showInfo.close( );
  	alert(ex);
  }
}
//����2010-2-26

//����У��
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