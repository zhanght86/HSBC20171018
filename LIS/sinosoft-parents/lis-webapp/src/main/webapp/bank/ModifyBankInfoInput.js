//ModifyBankInfoInput.js���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

var showInfo;

//�ύ�����水ť��Ӧ����
function submitForm() {
  if (verifyInput() == false) return false;  
  if (fm.BankCode.value == "0101") {
    if (trim(fm.AccNo.value).length!=19 || !isInteger(trim(fm.AccNo.value))) {
      alert("�������е��˺ű�����19λ�����֣����һ���Ǻţ�*����Ҫ��");
      return false;
    }
  }
 /* if(document.all('FeeType').value=="2")
  {
  	if(document.all('PayMode').value!="")
  	{
  		alert("�������շѲ������޸Ľ��ѷ�ʽ");
  		return;
  	}
  }

  if(document.all('FeeType').value=="1"&& document.all('PayMode').value=="")
  {
  	alert("��ѡ�񽻷ѷ�ʽ");
  	return false;
  }*/
  
  //if(document.all('FeeType').value=="2" || (document.all('FeeType').value=="1" &&  document.all('PayMode').value=="4"))
 // {
  	if(document.all('BankCode').value=="" || document.all('AccNo').value=="" || document.all('AccName').value=="")
  	{
  		alert("���д��롢�����˺š��˻����Ʋ���Ϊ�գ����ʵ��");
  		return false;
  	}
 // }
   
   fm.butSave.disabled = true;  
  var showStr="�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ; 
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();  
  document.getElementById("fm").submit(); //�ύ
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content ) {
  try { 
  	initForm();
  	showInfo.close(); 
  } catch(e) {}
  
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}   

// ��ѯ��ť
function easyQueryClick() {
	//if(document.all('FeeType').value=="2")
	//{
	  if (fm.TempfeeNo.value == "") {
	    alert("���������վݺ�");
	    return; 
	  }
	  initBankGrid();
		// ��дSQL���			
		 fm.butQuery.disabled = true;       
		
	/*	var strSql = "SELECT tempfeeno,'"+document.all('PrtNo').value+"',paymode, paymoney, bankcode, accname, bankaccno FROM ljtempfeeclass WHERE 1 = 1"
	               + " AND tempfeeno IN (SELECT tempfeeno FROM ljtempfee WHERE "
	               + " enteraccdate IS NULL AND otherno IN (SELECT contno FROM lccont WHERE prtno = '" + trim(document.all('PrtNo').value) + "'))"
	               + "union all select tempfeeno,'"+document.all('PrtNo').value+"',paymode, paymoney, bankcode, accname, bankaccno FROM ljtempfeeclass WHERE 1 = 1"
	               + " AND tempfeeno IN (SELECT tempfeeno FROM ljtempfee WHERE "
	               + " enteraccdate IS NULL AND otherno IN (SELECT familycontno FROM lccont WHERE prtno = '" + trim(document.all('PrtNo').value) + "')) "
	               + " union all select a.tempfeeno,'"+document.all('PrtNo').value+"',a.paymode, a.paymoney, bankcode, accname, bankaccno from ljtempfeeclass a, ljtempfee b where a.tempfeeno=  b.tempfeeno"
	               + " and b.enteraccdate IS NULL and b.otherno= '" + trim(document.all('PrtNo').value) + "'";*/
		//var strSql = "select tempfeeno,'',paymode, paymoney,bankcode,bankaccno,accname from ljtempfeeclass where tempfeeno='" + trim(document.all('TempfeeNo').value) + "' and enteraccdate is null "
		//					 + " and paymode='4'  and managecom like '"+document.all('FManageCom').value+"%'";
		
			var strSql = wrapSql1("LJTempFeeClass1",[trim(document.all('TempfeeNo').value),document.all('FManageCom').value]);
		turnPage.queryModal(strSql, BankGrid);
		if (BankGrid.mulLineCount<=0)
		{
			alert("δ�鵽����������ݣ�");
			return false;
		}
		//fm.PrtNo2.value = fm.PrtNo.value;
		document.all('TempfeeNo1').value = document.all('TempfeeNo').value;
		
  //}
 /* if(document.all('FeeType').value=="1")
  {
  	if(fm.GetNoticeNo.value=="")
  	{
  		alert("�����뽻��֪ͨ���");
  		return false;
  	}
  	initForm();
  	fm.butQuery.disabled = true;     
  	var strSql = "select '',GetNoticeNo,(select payform from LPEdorApp where GetNoticeNo=a.GetNoticeNo and rownum=1),bankcode, accname, bankaccno "
  	           + " from ljspay a where GetNoticeNo='"+trim(document.all('GetNoticeNo').value)+"' and (bankonthewayflag='0' or bankonthewayflag is null) and othernotype='10' ";
         
  	turnPage.queryModal(strSql, BankGrid);
  	if (BankGrid.mulLineCount<=0)
		{
			alert("δ�鵽���Ӧ�����ݣ�");
			return false;
		}
		fm.GetNoticeNo2.value = fm.GetNoticeNo.value;
  }*/

}

/*function afterCodeSelect(cCodeName, Field) 
{
  if(document.all('FeeType').value=="1")
  {
  	divFeeType1.style.display='';
  	divFeeType2.style.display='none';
  }
    if(document.all('FeeType').value=="2")
  {
  	divFeeType1.style.display='none';
  	divFeeType2.style.display='';
  }
}*/

function checkBank(tBankCode,tBankAccNo){
	if(tBankCode.value.length>0 && tBankAccNo.value.length>0){
		if(!checkBankAccNo(tBankCode.value,tBankAccNo.value)){
			tBankAccNo.value = "";
			return false;
		}
	}
}

function afterCodeSelect(cCodeName, Field) 
{
	if(cCodeName == "bank" ){
			checkBank(document.all('BankCode'),document.all('AccNo'));
  }
	

}


/**
	mysql���������ݴ����������Sql�ַ���
	
	sqlId:ҳ����ĳ��sql��Ψһ��ʶ
	param:��������,sql��where��������Ĳ���
**/
function wrapSql1(sqlId,param){
	
	var mysql=new SqlClass();
	
	mysql.setResourceName("bank.ModifyBankInfoInputSql");
	mysql.setSqlId(sqlId);
	
	for(i=0;i<param.length;i++){
		 mysql.addSubPara(param[i]);
	}
	
	return mysql.getString();
	
}