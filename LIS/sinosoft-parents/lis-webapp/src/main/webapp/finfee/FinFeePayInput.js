  //               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var showInfo;
var mDebug="0";
var tResourceName="finfee.FinFeePayInputSql";

//�ύ�����水ť��Ӧ����
function submitForm()
{
	//У����ظ��ѻ���
		//	if (!CheckeinputCertify())
		//	{
		//		return false;
		//	}
	//���Ƹ��ѹ������������������λ		
	//alert (document.all('ManageCom').value);
	//alert ((document.all('ManageCom').value).length);    		
		//	if((document.all('PolicyCom').value).length<6) 
     // {
     // 	alert("ҵ��Ա���ڵ�λ���Ǹ��ѻ��������ѻ���������������λ");
     //   return false;	
    //  }
      	
  //���ȼ���¼���
  //alert("���ѷ�ʽ========="+fmSave.PayMode').value);
  if(!verifyInput()) return false;
  if(!checkValue()) return false; //���Ӽ���

//  fmSave.ActuGetNo.value=document.all('ActuGetNo').value;
  
  var AccDate = fmSave.EnterAccDate.value;
	var now = getCurrentDate('-');
  if(fmSave.ActuGetNo.value!="")
  {

  //	fmSave.btnSave').disabled=true;
//zy 2009-04-09 16:44 ���ӶԵ������ڵ���ʾ
	  if(fmSave.FManageCom.value.length<8)
	  {
	  	if (window.confirm("���ѻ�����Ϊ�ļ���������ȷ������������?"))
	  	{
	  		if(compareDate(AccDate,now)!=0)
	  		{
	  			if (window.confirm("���������ڲ�Ϊ���գ���ȷ������������?"))
	  			{
			  		fmSave.btnSave.disabled=true;
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
					  document.getElementById("fmSave").submit(); //�ύ
					 	initInpBox();
				  }
			  }
			  else
			  {
			  		fmSave.btnSave.disabled=true;
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
					  document.getElementById("fmSave").submit(); //�ύ
					 	initInpBox();
			  }
	  	}
	  }
	  else
	  {
	  		if(compareDate(AccDate,now)!=0)
	  		{
	  			if (window.confirm("���������ڲ�Ϊ���գ���ȷ������������?"))
	  			{
			  		fmSave.btnSave.disabled=true;
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
					  document.getElementById("fmSave").submit(); //�ύ
					 	initInpBox();
				  }
			  }
			  else
			  {
			  		fmSave.btnSave.disabled=true;
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
					  document.getElementById("fmSave").submit(); //�ύ
					 	initInpBox();
					 	initInput(); //���֮ǰ¼�������
			  }
		}
  }
  else
   alert("���Ȳ�ѯ��");
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
//  alert(FlagStr);

  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
    //ִ����һ������
  }
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterQuery( FlagStr, content )
{
  alert(FlagStr);
  showInfo.close();
	 
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
    //ִ����һ������
  }
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
//    showDiv(operateButton,"true"); 
//    showDiv(inputButton,"false"); 
	  initForm();
  }
  catch(re)
  {
  	alert("��LLReport.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//ȡ����ť��Ӧ����
function cancelForm()
{
//  window.location="../common/html/Blank.html";
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}
 
//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���
	
}           


//��ʾfrmSubmit��ܣ���������
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


//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
  //����������Ӧ�Ĵ���
  showDiv(operateButton,"false"); 
  showDiv(inputButton,"true"); 
}           

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
  //����������Ӧ�Ĵ���
  alert("û�иù��ܣ�");
}           

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
  window.open("./FinFeePayQueryLJFIGet.html");
}           

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  //����������Ӧ�Ĵ���
  alert("û�иù��ܣ�");
}           

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
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

function queryLJAGet()
{
   if(document.getElementById('ActuGetNo').value!="")
   {
   	document.all('aquery').disabled=true;
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.getElementById("fm").submit();
   }
//  else if(document.all('OtherNo').value!="")
//   {
//    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
//    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//    var iWidth=550;      //�������ڵĿ��; 
    //var iHeight=250;     //�������ڵĸ߶�; 
//    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
//    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
//    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
//    showInfo.focus();      
//    document.getElementById("fm").submit();  		
//   }
   else
   {
    //alert("ʵ�����벻��Ϊ�գ�"); return ;	
    window.open("./FinFeePayQueryLJAGetMain.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
   }	
}

function checkValue()
{
  //������ѷ�ʽ��֧Ʊ��
  if(fmSave.PayMode.value=='2'||fmSave.PayMode.value=='3')
  {
    if(fmSave.BankCode2.value==''||fmSave.ChequeNo.value=='') 	
      {
      	alert("���ѷ�ʽ��֧Ʊ:�������к�Ʊ�ݺ��벻��Ϊ�գ�");
      	return false;
      }
  }
  // zy 2009-05-25 11:36 ������������
  if(fmSave.PayMode.value=='9')
  {
    if(fmSave.BankCode9.value==''||fmSave.BankAccNo9.value=='' ||fmSave.BankAccName9.value=='') 	
      {
      	alert("���ѷ�ʽ����������:�������С������˺š����л�������Ϊ�գ�");
      	return false;
      }
  }
  //������ѷ�ʽ������ת��
//if(fmSave.PayMode').value=='4')
//  {
//    if(fmSave.BankCode').value==''||fmSave.BankAccNo').value==''||fmSave.BankAccName').value=='') 	
//      {
//      	alert("���ѷ�ʽ������ת��:���б��롢�����˺š����л�������Ϊ�գ�");
//      	return false;
//      }      
//  }
//����ת�˲������ڸ��ѹ��ܽ���ȷ�ϲ��� zy 2009-04-02 19:35
  if(!(fmSave.PayMode.value=='1' || fmSave.PayMode.value=='2' || fmSave.PayMode.value=='3' || fmSave.PayMode.value=='9'))
  {
  	  alert("Ŀǰֻ֧���ֽ�֧Ʊ�������������ָ��ѷ�ʽ�ĸ���ȷ�ϣ����ʵ!");	
  	  return false;
  }
  return true;
	
} 
  
//������ѷ�ʽѡ��ת��֧Ʊ��������ʾ�����˺�
function showBankAccNo()
{ 
  if(fmSave.PayMode.value =="2"||fmSave.PayMode.value =="3")
  {
    divChequeNo.style.display="";	
    divBankAccNo.style.display="none";
    divNetBankAccNo.style.display="none";
  }
 // else if (fmSave.PayMode').value=="4")
 // {
 //   divChequeNo.style.display="none";  	
 // 	divBankAccNo.style.display="";	
//  }
// zy 2009-05-25 11:20 �������������ĸ��ѷ�ʽ�����Ϊ��������������ʾ���б���������˺�
else if(fmSave.PayMode.value=="9")
	{
		divNetBankAccNo.style.display="";
		divBankAccNo.style.display="none";	
    divChequeNo.style.display="none";
	}
  else  
  {
    divBankAccNo.style.display="none";	
    divChequeNo.style.display="none";
    divNetBankAccNo.style.display="none";
  }	

}
//��ӡ����ƾ֤
function Print()
{
  //if(!verifyInput()) return false;
  //if(!checkValue()) return false; //���Ӽ���
  if(document.all('ActuGetNo').value==null)
  {
  	alert("������ʵ�����룡");
  	return false;
  	}
  fmprint.all('ActuGetNo1').value=document.all('ActuGetNo').value;
  if(fmprint.all('ActuGetNo1').value==document.all('ActuGetNo').value)
  {
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
  fmprint.target="f1print";   
  document.getElementById("fmprint").submit(); //�ύ
  showInfo.close();
 	initInpBox();
  }
  else
   alert("����¼��ʵ�պ��룡");  	

}

function afterCodeSelect(cCodeName, Field){
  showBankAccNo();	
  if(cCodeName=="Mode"){
     initInput();
  	}
  	if(cCodeName == "FINAbank" ){
			checkBank(fmSave.BankCode9,fmSave.BankAccNo9);
 		}
}
function initInput(){
   try{
  	  fmSave.BankCode.value ='';
  	  fmSave.BankAccName.value ='';
  	  fmSave.BankName.value ='';
  	  fmSave.BankName2.value ='';
  	  fmSave.BankAccNo.value ='';
  	  fmSave.BankCode2.value ='';
  	  fmSave.ChequeNo.value ='';
  	  fmSave.BankCode9.value ='';
  	  fmSave.BankName9.value ='';
  	  fmSave.BankAccNo9.value ='';
  	  fmSave.BankAccName9.value ='';
  	  }catch(ex){}
}

function easyQueryClick() {
	
	if((document.all('ActuGetNo').value==null||document.all('ActuGetNo').value=="")&&(document.all('OtherNo').value==null||document.all('OtherNo').value==""))
  {
  	alert("��¼��ʵ������/��ȫ�����/�ⰸ��/Ͷ����ӡˢ�ţ���֮��֮һ��");
    return false;
  }
 //if((document.all('PolicyCom').value).length<6) 
 //     {
  //    	alert("ҵ��Ա���ڵ�λ���Ǹ��ѻ��������ѻ���������������λ");
  //      return false;	
   //   }
  //��ѯ���ѻ���
  //var ManageComSql = "select ManageCom from LJAGet where 1=1 and ConfDate is null and EnterAccDate is null and (bankonthewayflag='0' or bankonthewayflag is null) and paymode<>'4' "
	//           + getWherePart( 'ActuGetNo' )
	//           + getWherePart( 'OtherNo' )
	var ManageComSql =wrapSql(tResourceName,"LJAGet1",[document.all('ActuGetNo').value,document.all('OtherNo').value]);
	           			 
	var ManageComResult = easyExecSql(ManageComSql);
	document.all('ManageCom').value = ManageComResult;
	if(ManageComResult!=null&&ManageComResult!="")
	{
		if(trim(comCode).length >trim(document.all('ManageCom').value).length )
	 {
		alert("����¼������Ȩ�޲��������Խ��ѻ��������ϼ�������¼");
		return false;
	}

     if(document.all('ManageCom').value.substring(0,trim(comCode).length)!= trim(comCode))
    {
    	alert("����¼������Ȩ�޲����������µ�¼");
    	return false;
    	}
		
	}

    	
    	
  //var ModeSql = "select PayMode,bankonthewayflag from ljaget where 1=1 and ConfDate is null and EnterAccDate is null and rownum=1 "
	//           + getWherePart( 'ActuGetNo' )
	//           + getWherePart( 'OtherNo' )        
	
	var ModeSql =warpSql('LJAGet2',[document.all('ActuGetNo').value,document.all('OtherNo').value]);
	
	var TarrResult = easyQueryVer3(ModeSql, 1, 1, 1);
	var ArrData = decodeEasyQueryResult(TarrResult);
	if (TarrResult!=null&&TarrResult!="")
 	  {
   	    if (ArrData[0][0]=='4'&&ArrData[0][1]=='1')
   	    {
   	      alert("�ñʸ��ѵĸ��ѷ�ʽΪ���л�������ڴ˲��������Ѿ���������;��");	
   	      return false;
   	    }
   	    else if (ArrData[0][0]=='4')
   	    {
   	      alert("�ñʸ��ѵĸ��ѷ�ʽΪ���л�������ڴ˲���");	
   	      return false;   	    	
   	    }  	
  	}
	
  // ƴSQL��䣬��ҳ��ɼ���Ϣ
  //var strSql = "select ActuGetNo, OtherNo, OtherNoType,PayMode,currency,SumGetMoney,EnterAccDate,Drawer,DrawerID from LJAGet where 1=1 and ConfDate is null and EnterAccDate is null and (bankonthewayflag='0' or bankonthewayflag is null) and paymode<>'4' "
	//           + getWherePart( 'ActuGetNo' )
	//           + getWherePart( 'OtherNo' )

  var strSql =warpSql('LJAGet3',[document.all('ActuGetNo').value,document.all('OtherNo').value]);
  //��ѯ���ѻ���sql,���ؽ��
                   	                
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
                  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {  
    //���MULTILINE��ʹ�÷�����MULTILINEʹ��˵�� 
    QueryLJAGetGrid.clearData('QueryLJAGetGrid');  
    alert("û�в�ѯ�����ݣ�");
    return false;
  }
  turnPage.pageLineNum = 5;
  //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
	turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = QueryLJAGetGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSql; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  //�����Ƿ���ʾ��ҳ��ť
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }

    	
	//У����ظ��ѻ���
	//if (!CheckeinputCertify())
//	{
	//	return false;
//	}

  
}
function GetRecord()
{
  var arrReturn = new Array();
	var tSel = QueryLJAGetGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	}
		

	else 
	{
		//alert("11111111111111111");
    try
		{
     document.all('ActuGetNo').value=QueryLJAGetGrid.getRowColData(tSel-1,1);           
     fmSave.PolNo.value=QueryLJAGetGrid.getRowColData(tSel-1,2);           
     fmSave.PayMode.value=QueryLJAGetGrid.getRowColData(tSel-1,4);
fmSave.Currency.value=QueryLJAGetGrid.getRowColData(tSel-1,5);         
     fmSave.GetMoney.value=QueryLJAGetGrid.getRowColData(tSel-1,6);         
     fmSave.SDrawer.value=QueryLJAGetGrid.getRowColData(tSel-1,8);         
     fmSave.SDrawerID.value=QueryLJAGetGrid.getRowColData(tSel-1,9);
      
	  }
	  	catch(ex)
		{
				alert(ex);
		}
	}  
	   
	   
}

// ��ظ��Ѳ������л�����
function inputCertify(objCheck)
{
	if (document.all('PolicyCom').value==document.all('ManageCom').value.substring(0, document.all('PolicyCom').value.length))
	{
		if(objCheck.checked == true)
  	{	
  		alert("�˴β�����������ظ��ѣ�");
  		return;	
  	}
	}
	if(objCheck.checked == true) {
		
		showDiv(PolicyComInPut,"true");
		
	} else {
		
		showDiv(PolicyComInPut,"false");
	}
}    

//��ظ���У��
function CheckeinputCertify()
{
  //if (document.all('PolicyCom').value != document.all('ManageCom').value)
  
  //alert("document.all('PolicyCom').value==========="+document.all('PolicyCom').value);
  //alert("document.all('PolicyCom').value.length==========="+document.all('PolicyCom').value.length);
  //alert("document.all('ManageCom').value==========="+document.all('ManageCom').value);
  //alert("document.all('ManageCom').value.substring(0, document.all('PolicyCom').value.length)==========="+document.all('PolicyCom').value.substring(0, document.all('PolicyCom').value.length));
  
  if (document.all('ManageCom').value.substring(0,6)!=document.all('PolicyCom').value.substring(0,6))
  {
  	if(fm.chkPrtNo.checked == true)
  	{
  		if((document.all('PolicyCom').value==null||document.all('PolicyCom').value==""))
		  {
		  	alert("�����븶�ѻ���");
		    return false;
		  }
  		return true;
  	}else{
  		alert("�˴θ���������ظ��ѣ���ѡ����ظ��Ѻ��ٲ���");
  		return false;
  	}
  }else{
  	if(fm.chkPrtNo.checked == true)
  	{
  		alert("�˴β�����������ظ���");
  		showDiv(PolicyComInPut,"false");
  		fm.chkPrtNo.checked == none;
  	}
  	return true;
  }
}    

function checkBank(tBankCode,tBankAccNo){
	if(tBankCode.value.length>0 && tBankAccNo.value.length>0){
		if(!checkBankAccNo(tBankCode.value,tBankAccNo.value)){
			
			tBankAccNo.value = "";
			return false;
		}
	}
}