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
var currname = "";
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

var tResourceName = "customer.CustomerYFSql";
var tResourceSQL1="CustomerYFSql1";
var tResourceSQL2="CustomerYFSql2";
var tResourceSQL3="CustomerYFSql3";
var tResourceSQL4="CustomerYFSql4";
var tResourceSQL5="CustomerYFSql5";
var tResourceSQL6="CustomerYFSql6";
var tResourceSQL7="CustomerYFSql7";
var tResourceSQL8="CustomerYFSql8";
var tResourceSQL9="CustomerYFSql9";
var tResourceSQL10="CustomerYFSql10";
var tResourceSQL11="CustomerYFSql11";
var tResourceSQL12="CustomerYFSql12";
var tResourceSQL13="CustomerYFSql13";
var tResourceSQL14="CustomerYFSql14";
var tResourceSQL15="CustomerYFSql15";
var tResourceSQL16="CustomerYFSql16";
var tResourceSQL17="CustomerYFSql17";
var tResourceSQL18="CustomerYFSql18";
var tResourceSQL19="CustomerYFSql19";
var tResourceSQL20="CustomerYFSql20";
var tResourceSQL21="CustomerYFSql21";
var tResourceSQL22="CustomerYFSql22";




var mySql=new SqlClass();
//mySql.setJspName("../../customer/CustomerYFInputSql.jsp");
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
	fm.OperateType.value="INSERT";
	try {
		if( verifyInput() == true) //&& FinFeeGrid.checkValue("FinFeeGrid")&&MatchInfoGrid.checkValue("MatchInfoGrid")
		{
			//if (verifyInput3()==true){
		  	var i = 0; 
		  	
		  	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"; 
		  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		  	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
				fm.action = "./CustomerYFSave.jsp"; 
				
				document.all("signbutton").disabled = true;
		  	document.getElementById("fm").submit(); //�ύ
	  	//}
	  	//else{
	  	//}
	  }
  } catch(ex){
  	showInfo.close( );
  	alert(ex);
  }
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content)
{
	showInfo.close();
	document.all("signbutton").disabled=false;
	try {
		if (FlagStr == "Fail" ) {             
	    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	  } else { 
		  var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content;
		  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
		  clearFormData();
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
	clearFormData();
	FinFeeGrid.clearData();
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
	if(fm.PayMode.value=='5'){
	currname = QueryLJAGetGrid.getRowColData(0,4);
		payTransAccout();
	}
	
	if(fm.PayMode.value=='6'){
		payPos();
	}
	
	sumTempFee = parseFloat(pointTwo(FinFeeGrid.getRowColData(0,3)));
	//clearFinInfo();
	QueryLJAGetGrid.clearData('QueryLJAGetGrid');  
	
}

function clearFinInfo(){
	
	
	fm.PayMode.value					=	'';
	fm.PayModeName.value			=	'';
	fm.Currency.value					=	'';
	fm.CurrencyName.value			=	'';
	fm.PayFee1.value					=	'';
	fm.PayFee2.value					=	'';
	fm.InBankCode2.value			=	'';
	fm.InBankCode2Name.value	=	'';
	fm.InBankAccNo2.value			=	'';
	
	fm.PayFee3.value					=	'';
	fm.ChequeNo3.value				=	'';
	fm.ChequeDate3.value			=	'';
	fm.BankCode3.value				=	'';
	fm.BankAccNo3.value				=	'';
	fm.AccName3.value					=	'';
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
	                           	
	fm.BankCode3Name.value		=	''; //ת��֧Ʊ ����������
	fm.InBankCode6Name.value	=	''; //POS�տ� �տ�������
	                           	
	fm.TempFeeNo1.value				=	'';
	fm.PayName.value					=	''

	return true;
}

//�����շ���ϢУ��
function verifyInput6(){

	if(TempToGrid. mulLineCount!=0){
		alert("���ȳ���ҵ�������Ϣ�ٽ���ȷ�ϣ�");
		return false;
	}
	
	if(fm.PayMode.value==''||fm.PayMode.value==null){
		alert("��ѡ�񽻷����ͣ�");
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
	if(fm.PayMode.value=='2'){
		if(fm.PayFee2.value==''||fm.PayFee2.value==null){
			alert("��¼�뽻�ѽ�");
			return false;
		}
		if(fm.ChequeNo2.value==''||fm.ChequeNo2.value==null){
			alert("��¼��Ʊ�ݺ��룡");
			return false;
		}
		if(!verifyNumber("���ѽ��",fm.PayFee2.value)){
			alert("���ѽ�����Ч���֣�");
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
			alert("��¼��֧Ʊ���ڣ�");
			return false;
		}
		if(fm.BankCode3.value==''||fm.BankCode3.value==null){
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
		//if(fm.InBankCode3.value==''||fm.InBankCode3.value==null){
		//	alert("��¼���տ����У�");
		//	return false;
		//}
		//if(fm.InBankAccNo3.value==''||fm.InBankAccNo3.value==null){
		//	alert("��¼���տ������˺ţ�");
		//	return false;
		//}
	}
	if(fm.PayMode.value=='5'){
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
	FinFeeGrid.setRowColData(0,18,fm.TempFeeNo1.value);
	FinFeeGrid.setRowColData(0,19,fm.PayName.value);
	
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
	FinFeeGrid.setRowColData(0,7,fm.InBankCode2.value);
	FinFeeGrid.setRowColData(0,8,fm.InBankCode2Name.value);
	FinFeeGrid.setRowColData(0,9,fm.InBankAccNo2.value);
	FinFeeGrid.setRowColData(0,18,fm.TempFeeNo1.value);
	FinFeeGrid.setRowColData(0,19,fm.PayName.value);
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
	FinFeeGrid.setRowColData(0,7,fm.BankCode3.value);	
	FinFeeGrid.setRowColData(0,9,fm.BankAccNo3.value);	
	FinFeeGrid.setRowColData(0,10,fm.AccName3.value);	
	FinFeeGrid.setRowColData(0,18,fm.TempFeeNo1.value);
	FinFeeGrid.setRowColData(0,19,fm.PayName.value);
}
//�ڲ�ת��
function payTransAccout(){
	temp=pointTwo(fm.PayFee5.value);
	fm.PayFee5.value=temp;
	FinFeeGrid.setRowColData(0,1,fm.PayMode.value);
	FinFeeGrid.setRowColData(0,2,fm.PayModeName.value);
	FinFeeGrid.setRowColData(0,3,temp);
	FinFeeGrid.setRowColData(0,4,fm.Currency.value);	
	FinFeeGrid.setRowColData(0,5,currname);	
	//FinFeeGrid.setRowColData(0,17,fm.ActuGetNo5.value);	
	//FinFeeGrid.setRowColData(0,18,fm.Drawer5.value);	
	//FinFeeGrid.setRowColData(0,19,fm.DrawerID.value);	
	//FinFeeGrid.setRowColData(0,18,fm.TempFeeNo1.value);
	//FinFeeGrid.setRowColData(0,19,fm.PayName.value);
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
	FinFeeGrid.setRowColData(0,18,fm.TempFeeNo1.value);
	FinFeeGrid.setRowColData(0,19,fm.PayName.value);
}

/*********************************************************************
 *  �շѷ�ʽѡ���ڲ�ת��ʱ����ѯʵ������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryLJAGet()
{
  
      EasyQueryPay();
   
}

function EasyQueryPay ()
{

  // ƴSQL��䣬��ҳ��ɼ���Ϣ
  var strSQL = "";
  var strSQL2 = "";

  //mySql.setSqlId("CustomerYFInputSql_34");
  //strSql = mySql.getSQL();

  if (document.all('ActuGetNo5').value!="")
  {

  	//mySql.setSqlId("CustomerYFInputSql_35");
  	//mySql.addPara('ActuGetNo5',document.all('ActuGetNo5').value);
  	//strSql += mySql.getSQL();	
  	strSQL2 = strSQL2 + " and actugetno='"+ document.all('ActuGetNo5').value+"'";  

  }
  if(document.all('OtherNo5').value!="")
	{
		//mySql.setSqlId("CustomerYFInputSql_36");
  	//mySql.addPara('OtherNo5',document.all('OtherNo5').value);
  	//strSql += mySql.getSQL();
  	strSQL2 = strSQL2 + " and otherno='"+ document.all('OtherNo5').value+"'";  

	}
  
  //��ѯ���ѻ���sql,���ؽ��
  QueryLJAGetGrid.clearData('QueryLJAGetGrid'); 
 

  strSQL = wrapSql(tResourceName,tResourceSQL1,[strSQL2]); 
 
  var strArray = easyExecSql(strSQL );
  if(strArray==null){
  	alert("û�в�ѯ�����ݣ�");
  	return false;
  }
  turnPage.queryModal(strSQL, QueryLJAGetGrid);
} 


function GetRecord(){
  var tSel = QueryLJAGetGrid.getSelNo();
	fm.ActuGetNo5.value	= QueryLJAGetGrid.getRowColData(tSel-1,1);
	fm.OtherNo5.value		=	QueryLJAGetGrid.getRowColData(tSel-1,2);
	fm.PayFee5.value		=	pointTwo(QueryLJAGetGrid.getRowColData(tSel-1,3));
	fm.CurrencyName.value		=	QueryLJAGetGrid.getRowColData(tSel-1,4);
	fm.Currency.value		=	QueryLJAGetGrid.getRowColData(tSel-1,5);
	
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
			alert("���ѷ�ʽΪת��֧Ʊ��¼��֧Ʊ����!");
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
	  }else if(Field.value=="4"||Field.value=="5"||Field.value=="6"){
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
			document.all("divPayMode"+i).style.display='';
		}
		else
		{
		  document.all("divPayMode"+i).style.display='none';
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
  if (document.all("PayMode").value == '0')
  {
  	document.all("PayFee0").value = SumPrem;
  	}
  if (document.all("PayMode").value == '1')
  {
  	document.all("PayFee1").value = SumPrem;
  	}
  if (document.all("PayMode").value == '2')
  {
  	document.all("PayFee2").value = SumPrem;
    }
  if (document.all("PayMode").value == '3')
  {
  	document.all("PayFee3").value = SumPrem;
    }
  if (document.all("PayMode").value == '4')
  {
  	document.all("PayFee4").value = SumPrem;
  }
  //if (document.all("PayMode").value == '5')
  //{
  //	document.all("PayFee5").value = SumPrem;
  //}
  if (document.all("PayMode").value == '6')
  {
  	document.all("PayFee6").value = SumPrem;
  }
  if (document.all("PayMode").value == '7')
  {
  	document.all("PayFee7").value = SumPrem;
  }
  if (document.all("PayMode").value == '8')
  {
  	document.all("PayFee8").value = SumPrem;
  }
  if (document.all("PayMode").value == '9')
  {
  	document.all("PayFee9").value = SumPrem;
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
      document.all("TempFeeType" + (i+1)).style.display = '';
    }
    else {
      document.all("TempFeeType" + (i+1)).style.display = 'none';
    }
  }
}

function queryAgent()
{
	if(document.all('ManageCom').value==""){
		alert("����¼���շѻ�����Ϣ��"); 
		return;
	}
	if(fm.AgentCode.value != ""&&fm.AgentCode.value != null){
		var cAgentCode = fm.AgentCode.value;
		var strSql = "";
  	//mySql.setSqlId("CustomerYFInputSql_13");
  	//mySql.addPara('cAgentCode',cAgentCode);
  	
    strSql = wrapSql(tResourceName,tResourceSQL2,[cAgentCode]); 
  	var arrResult = easyExecSql( strSql);
  	if (arrResult != null) {
  	  alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
  	} 
	}else{
		var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
}

function GetManageCom()
{
  if(fm.AgentCode.value!=null&&fm.AgentCode.value!="")
  {
  	var strSql = "";
   	//mySql.setSqlId("CustomerYFInputSql_24");
   	//mySql.addPara('AgentCode',fm.AgentCode.value);
   	strSql = wrapSql(tResourceName,tResourceSQL3,[fm.AgentCode.value]); 
   	
   	var arrResult = easyExecSql(strSql);
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
    //mySql.setSqlId("CustomerYFInputSql_25");
    //mySql.addPara('AgentCode',fm.AgentCode.value);
    
    aSql = wrapSql(tResourceName,tResourceSQL4,[fm.AgentCode.value]); 
    
  	arrResult = easyExecSql(aSql);
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
      //mySql.setSqlId("CustomerYFInputSql_26");
      //mySql.addPara('AgentCode',fm.AgentCode.value);
      
      nSql = wrapSql(tResourceName,tResourceSQL5,[fm.AgentCode.value]); 
      arrResult = easyExecSql(nSql);
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
  //mySql.setSqlId("CustomerYFInputSql_14");
  //mySql.addPara('AgentCode',fm.AgentCode.value);
  
  aSql = wrapSql(tResourceName,tResourceSQL6,[fm.AgentCode.value]); 
  
  arrResult = easyExecSql(aSql);
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
	if(fm.TempFeeType.value=='1'){//�µ�����
		newPolicy();
	}else if(fm.TempFeeType.value=='2'){//���ڴ߽�
		conPolicy();
	}else if(fm.TempFeeType.value=='3'){//����Ԥ��
		conBefPolicy();
	}else if(fm.TempFeeType.value=='4'){//��ȫ����
		edorPolicy();
	}else if(fm.TempFeeType.value=='6'){//���⽻��
		claimPolicy();
	/*}else if(fm.TempFeeType.value=='6'){//�����ڽ���
		urgePayPolicy ();
	}*/
	clearOpeTypeInfo();
}
}
//�µ�
function newPolicy(){
	tempFee = 1000000000 * parseFloat(pointTwo(fm.OpeFee1.value));
	
	//ȡ�û�����Ϣ
	var count=TempToGrid.mulLineCount; //�õ�MulLine������
	for(var i=0;i<count;i++){
		tempFee=tempFee + 1000000000 * parseFloat(pointTwo(TempToGrid.getRowColData(i,4)));
	}
	
	//alert("sumTempFee: "+pointTwo(sumTempFee));
	//alert("tempFee: "+pointTwo(tempFee));
	tempClassFee=tempClassFee/1000000000;
  tempFee=tempFee/1000000000;
	
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("ҵ����ϼƴ��ڲ����շ��ܺͣ�������¼��ҵ�������Ϣ��");
		return false;
	}
	fm.OperateSum.value=pointTwo(tempFee);
	fm.OperateSub.value=Math.round((parseFloat(pointTwo(tempFee))-parseFloat(pointTwo(sumTempFee)))*100)/100;
	
	if(!InputFlag){
		fm.addButton.style.display='none';
	}
	
	TempToGrid.addOne("TempToGrid");
	TempToGrid.setRowColData(count,	3	,	fm.PayDate.value			);
	TempToGrid.setRowColData(count,	4	,	pointTwo(parseFloat(fm.OpeFee1.value)));
	TempToGrid.setRowColData(count,	5	,	fm.OpeCurrency.value	);		//ҵ�������Ϣ
	TempToGrid.setRowColData(count,	7	,	fm.PolicyCom.value		); 		//���������managecom���շѻ���
	TempToGrid.setRowColData(count,	9,	fm.AgentGroup.value		);		//�����˱���
	TempToGrid.setRowColData(count,	10,	fm.AgentCode.value		);		//�����˱���
	TempToGrid.setRowColData(count,	16,	fm.InputNob.value			);		//Ͷ����
	TempToGrid.setRowColData(count,	11,	fm.InputNo1.value	); 				//ҵ�����
	TempToGrid.setRowColData(count,	12,	'6' ); 											//ҵ���������
	TempToGrid.setRowColData(count,	18	,	fm.TempFeeType.value	);
	
}
//���ڴ���
function conPolicy(){
	if(!addFlag){
		alert("û�в鵽Ӧ�ռ�¼");
		return false ;
	}
	tempFee = 1000000000 * parseFloat(pointTwo(fm.OpeFee2.value));
	
	//ȡ�û�����Ϣ
	var count=TempToGrid.mulLineCount; //�õ�MulLine������
	for(var i=0;i<count;i++){
		tempFee=tempFee+1000000000 * parseFloat(pointTwo(TempToGrid.getRowColData(i,4)));
	}
	
	tempClassFee=tempClassFee/1000000000;
  tempFee=tempFee/1000000000;
	
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("ҵ����ϼƴ��ڲ����շ��ܺͣ�������¼��ҵ�������Ϣ��");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee);
	fm.OperateSub.value=Math.round((parseFloat(pointTwo(tempFee))-parseFloat(pointTwo(sumTempFee)))*100)/100;
	
	if(!InputFlag){
		fm.addButton.style.display='none'; 
	}
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
	TempToGrid.setRowColData(count,	18,	fm.TempFeeType.value									);    
	
}
//����Ԥ��
function conBefPolicy(){
	if(!contFlag){
		alert("û�в鵽������¼");
		return false ;
	}
	
	tempFee = 1000000000 * parseFloat(pointTwo(fm.OpeFee3.value));
	
	//ȡ�û�����Ϣ
	var count=TempToGrid.mulLineCount; //�õ�MulLine������
	for(var i=0;i<count;i++){
		tempFee=tempFee+1000000000 * parseFloat(pointTwo(TempToGrid.getRowColData(i,4)));
	}
	
	tempClassFee=tempClassFee/1000000000;
  tempFee=tempFee/1000000000;
	
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("ҵ����ϼƴ��ڲ����շ��ܺͣ�������¼��ҵ�������Ϣ��");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee);
	fm.OperateSub.value=Math.round((parseFloat(pointTwo(tempFee))-parseFloat(pointTwo(sumTempFee)))*100)/100;
	
	if(!InputFlag){
		fm.addButton.style.display='none'; 
	}
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
}

//��ȫ
function edorPolicy(){
	
	if(!edorFlag){
		alert("û�в鵽Ӧ�ռ�¼");
		return false ;
	}
	tempFee = 1000000000 * parseFloat(pointTwo(fm.OpeFee4.value));
	
	//ȡ�û�����Ϣ
	var count=TempToGrid.mulLineCount; //�õ�MulLine������
	for(var i=0;i<count;i++){
		tempFee=tempFee+1000000000 * parseFloat(pointTwo(TempToGrid.getRowColData(i,4)));
	}
	tempClassFee=tempClassFee/1000000000;
  tempFee=tempFee/1000000000;
	
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("ҵ����ϼƴ��ڲ����շ��ܺͣ�������¼��ҵ�������Ϣ��");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee);
	fm.OperateSub.value=Math.round((parseFloat(pointTwo(tempFee))-parseFloat(pointTwo(sumTempFee)))*100)/100;
	
	if(!InputFlag){
		fm.addButton.style.display='none'; 
	}
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
	TempToGrid.setRowColData(count,	18,	fm.TempFeeType.value									);    //
	
}

//����
function claimPolicy(){
	if(!clmFlag){
		alert("û�в鵽Ӧ�ռ�¼");
		return false ;
	}
	tempFee = 1000000000 * parseFloat(pointTwo(fm.OpeFee5.value));
	
	//ȡ�û�����Ϣ
	var count=TempToGrid.mulLineCount; //�õ�MulLine������
	for(var i=0;i<count;i++){
		tempFee=tempFee+1000000000 * parseFloat(pointTwo(TempToGrid.getRowColData(i,4)));
	}
	tempClassFee=tempClassFee/1000000000;
  tempFee=tempFee/1000000000;
	
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("ҵ����ϼƴ��ڲ����շ��ܺͣ�������¼��ҵ�������Ϣ��");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee);
	fm.OperateSub.value=Math.round((parseFloat(pointTwo(tempFee))-parseFloat(pointTwo(sumTempFee)))*100)/100;
	
	if(!InputFlag){
		fm.addButton.style.display='none'; 
	}
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
	TempToGrid.setRowColData(count,	18,	fm.TempFeeType.value									);    //
}

//�����ڽɷ�
function urgePayPolicy(){
	
	if(!urgeFlag){
		alert("û�в鵽������¼");
		return false ;
	}
	tempFee = 1000000000 * parseFloat(pointTwo(fm.OpeFee6.value));
	
	//ȡ�û�����Ϣ
	var count=TempToGrid.mulLineCount; //�õ�MulLine������
	for(var i=0;i<count;i++){
		tempFee=tempFee+1000000000 * parseFloat(pointTwo(TempToGrid.getRowColData(i,4)));
	}
	tempClassFee=tempClassFee/1000000000;
  tempFee=tempFee/1000000000;
	
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("ҵ����ϼƴ��ڲ����շ��ܺͣ�������¼��ҵ�������Ϣ��");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee);
	fm.OperateSub.value=Math.round((parseFloat(pointTwo(tempFee))-parseFloat(pointTwo(sumTempFee)))*100)/100;
	
	if(!InputFlag){
		fm.addButton.style.display='none'; 
	}
	TempToGrid.addOne("TempToGrid");
	TempToGrid.setRowColData(count,	1	,	fm.InputNo11.value										);
	TempToGrid.setRowColData(count,	3	,	fm.PayDate.value											);
	TempToGrid.setRowColData(count,	4	,	pointTwo(parseFloat(fm.OpeFee6.value)));
	TempToGrid.setRowColData(count,	5	,	fm.OpeCurrency.value									);
	TempToGrid.setRowColData(count,	7	,	fm.PolicyCom.value										); 		//�������
	TempToGrid.setRowColData(count,	9 ,	fm.AgentGroup1.value									);		//�����˱���
	TempToGrid.setRowColData(count,	10,	fm.AgentCode1.value										);		//�����˱���
	TempToGrid.setRowColData(count,	11,	fm.InputNo22.value											); 		//ҵ�����
	TempToGrid.setRowColData(count,	12,	'2' 																	); 		//ҵ���������
	TempToGrid.setRowColData(count,	18,	fm.TempFeeType.value									);    //
	
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
			alert("�ⰸ�Ų���Ϊ��");
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
	
	if(fm.ManageCom.value.substring(0,4)!=fm.PolicyCom.value.substring(0,4)){
		if(!confirm("ҵ�����Ϊ��ؽɷѣ�Ҫȷ����")){
			return false;
		}
	}
	return true;
}

function getAgentCode()
{
	if((document.all('InputNo3').value!=null||document.all('InputNo3').value!="")&&(document.all('AgentCode').value==""||document.all('AgentCode').value==null))
	{
		var strSql = "";
    //mySql.setSqlId("CustomerYFInputSql_20");
    //mySql.addPara('InputNo3' , fm.InputNo3.value);
    
    strSql = wrapSql(tResourceName,tResourceSQL7,[fm.InputNo3.value]); 
    
    // select AgentCode,managecom,agentgroup from ljspay where otherno='?InputNo3?' ;
	  var TarrResult = easyQueryVer3( strSql, 1, 1, 1);
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
    //mySql.setSqlId("CustomerYFInputSql_29");
    //mySql.addPara('InputNo5',fm.InputNo5.value);
    strSql2 = wrapSql(tResourceName,tResourceSQL8,[fm.InputNo5.value]); 
    
	  var arrResult2 = easyExecSql(strSql2);
	  if(arrResult2 == null)
	  {
	  	contFlag=true;
	    var strSql = "";
      //mySql.setSqlId("CustomerYFInputSql_30"); //select managecom from LCCont where contno='?InputNo5?'
     // mySql.addPara('InputNo5',fm.InputNo5.value);
     
     strSql = wrapSql(tResourceName,tResourceSQL9,[fm.InputNo5.value]); 
     
      var arrResult = easyExecSql(strSql);
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
        //mySql.setSqlId("CustomerYFInputSql_31"); //select agentcode from LCCont where contno='?InputNo5?'
        //mySql.addPara('InputNo5',fm.InputNo5.value);
        
        ASql = wrapSql(tResourceName,tResourceSQL10,[fm.InputNo5.value]); 
        
        var brrResult = easyExecSql(ASql);
        
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
      //mySql.setSqlId("CustomerYFInputSql_32");
      //mySql.addPara('InputNo5',fm.InputNo5.value);
      
      
      strSql = wrapSql(tResourceName,tResourceSQL11,[fm.InputNo5.value]); 
      
      var arrResult = easyExecSql(strSql);
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
        //mySql.setSqlId("CustomerYFInputSql_33");
        //mySql.addPara('InputNo5',fm.InputNo5.value);
        ASql = wrapSql(tResourceName,tResourceSQL12,[fm.InputNo5.value]); 
        
        var brrResult = easyExecSql(ASql);
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
	}
}

function getEdorCode()
{
  if(document.all('PayDate').value == null && document.all('PayDate').value == "")
  {
  	alert("��¼�뽻������");
  	return false;
  }
  
  if((document.all('InputNo7').value!=null&&document.all('InputNo7').value!="")&&(document.all('InputNo8').value==null||document.all('InputNo8').value==""))
  {
  	edorFlag=true;
   	var strSql = "";
   	//mySql.setSqlId("CustomerYFInputSql_21");
   	//mySql.addPara('InputNo7',fm.InputNo7.value);
   	//mySql.addPara('PayDate',document.all('PayDate').value);
   	
   	strSql = wrapSql(tResourceName,tResourceSQL13,[fm.InputNo7.value,document.all('PayDate').value,document.all('PayDate').value]); 
   	
   	var arrResult = easyExecSql(strSql);
   	
   	if(arrResult == null ||arrResult == ""||arrResult=='null')
   	{
   	 alert("û�в�ѯ����ȫӦ�ռ�¼!!!");
   	 return false;	
   	}
  	else
  	{
  	  fm.InputNo8.value=arrResult;	//�����վݺ���
  	}
  }	
  
  if((document.all('InputNo8').value!=null&&document.all('InputNo8').value!="")&&(document.all('InputNo7').value==null||document.all('InputNo7').value==""))
  {
  	edorFlag=true;
   	var strSql = "";
  	//mySql.setSqlId("CustomerYFInputSql_22");
  	//mySql.addPara('InputNo8',fm.InputNo8.value);
  	
  	
  	strSql = wrapSql(tResourceName,tResourceSQL14,[fm.InputNo8.value]); 
  	
  	var arrResult = easyExecSql(strSql);
  	if(arrResult==null||arrResult=="")
  	{
  	 alert("�ñ�ȫ����Ų����ڣ����Ѿ�����");	
  	 fm.InputNo7.value="";
  	 fm.InputNo8.value="";
  	}
  	else
  	{
  	 fm.InputNo7.value=arrResult;	//��ȫ�����
  	}
  }
  
  if(document.all('InputNo8').value!=null&&document.all('InputNo8').value!=""&&document.all('InputNo7').value!=null&&document.all('InputNo7').value!=""){
  	//InputNo7 �����
  	//InputNo8 �վݺ�
  	edorFlag=true;
   	var strSql = "";
  	//mySql.setSqlId("CustomerYFInputSql_22");
  	//mySql.addPara('InputNo8',fm.InputNo8.value);
  	
  	
  	strSql = wrapSql(tResourceName,tResourceSQL14,[fm.InputNo8.value]); 
  	
  	var arrResult = easyExecSql( strSql);
  	if(arrResult==null||arrResult==""){
  	 alert("�ñ�ȫ����Ų����ڣ����Ѿ�����");	
  	 fm.InputNo7.value="";
  	 fm.InputNo8.value="";
  	}
  	else{
  	 fm.InputNo7.value=arrResult;	//��ȫ�����
  	}
  }
  if (document.all('InputNo7').value!=null&&document.all('InputNo7').value!="")
  {
    var strSql = "";
   // mySql.setSqlId("CustomerYFInputSql_23");
    //mySql.addPara('InputNo8',fm.InputNo8.value);
    //mySql.addPara('PayDate',document.all('PayDate').value);
    
    
    
    strSql = wrapSql(tResourceName,tResourceSQL15,[fm.InputNo8.value,document.all('PayDate').value,document.all('PayDate').value]); 
    
    var arrResult = easyExecSql(strSql);
    
    if(arrResult == null ||arrResult == ""||arrResult=='null'){
      alert("�ñ�ȫ����Ų����ڣ����Ѿ�����");
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
    //mySql.setSqlId("CustomerYFInputSql_27");
    //mySql.addPara('InputNo11',fm.InputNo11.value);
    strSql = wrapSql(tResourceName,tResourceSQL16,[fm.InputNo11.value]); 
    
    var arrResult = easyExecSql(strSql);
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
    //mySql.setSqlId("CustomerYFInputSql_28");
    //mySql.addPara('InputNo12',fm.InputNo12.value);
    
     strSql = wrapSql(tResourceName,tResourceSQL17,[fm.InputNo12.value]); 
    var arrResult = easyExecSql(strSql);
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
    //mySql.setSqlId("CustomerYFInputSql_29"); //�ŵ���ѯ
    //mySql.addPara('InputNo5',fm.InputNo22.value);
    
     strSql2 = wrapSql(tResourceName,tResourceSQL18,[fm.InputNo22.value]); 
    
	  var arrResult2 = easyExecSql( strSql2);
	  if(arrResult2 == null)
	  {
	  	urgeFlag=true;
	    var strSql = "";
      //mySql.setSqlId("CustomerYFInputSql_30"); //select managecom from LCCont where contno='?InputNo5?'
      //mySql.addPara('InputNo22',fm.InputNo22.value);
      
      strSql = wrapSql(tResourceName,tResourceSQL19,[fm.InputNo22.value]); 
      
      var arrResult = easyExecSql(strSql);
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
        //mySql.setSqlId("CustomerYFInputSql_31"); //select agentcode from LCCont where contno='?InputNo22?'
        //mySql.addPara('InputNo22',fm.InputNo22.value);
        
        ASql = wrapSql(tResourceName,tResourceSQL20,[fm.InputNo22.value]);  
        
        var brrResult = easyExecSql(ASql);
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
      //mySql.setSqlId("CustomerYFInputSql_32");
      //mySql.addPara('InputNo22',fm.InputNo22.value);
      
      strSql = wrapSql(tResourceName,tResourceSQL21,[fm.InputNo22.value]);  
      
      var arrResult = easyExecSql(strSql);
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
        //mySql.setSqlId("CustomerYFInputSql_33");
        //mySql.addPara('InputNo22',fm.InputNo22.value);
        
        ASql = wrapSql(tResourceName,tResourceSQL22,[fm.InputNo22.value]);  
        var brrResult = easyExecSql(ASql);
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
	}	
}



function queryLJSPay(){
   if(document.all('InputNo3').value!=""){
   }
   else{
    alert("�����뱣����");
   }
}

function clearFormData(){
	//clearOpeTypeInfo();
	fm.TempFeeType.value="";
	fm.TempFeeTypeName.value="";
	TempToGrid.clearData();
	fm.OperateSum.value="";
	fm.OperateSub.value="";
	fm.addButton.style.display='';
	return true;
}

