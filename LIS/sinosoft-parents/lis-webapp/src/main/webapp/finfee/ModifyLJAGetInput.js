//ModifyLJAGetInput.js���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

var showInfo;
var qFlag=false;

//�ύ�����水ť��Ӧ����
function submitForm() {
  if (verifyInput() == false) return false;  
  if(checkPayMode()==false) return false;
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
  initInput();
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content ) {
  try { showInfo.close(); } catch(e) {}
  
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	easyQueryClick();
 // try { showInfo.close(); } catch(e) {}
}   

// ��ѯ��ť
function easyQueryClick() {
	if ((fm.ActuGetNo.value==null||fm.ActuGetNo.value=="")&&(fm.OtherNo.value==null||fm.OtherNo.value==""))
	{
		alert("�޲�ѯ���ݣ�����������һ����ѯ������");
		fm.ActuGetNo.focus();
		return;
		}
		
	// ��дSQL���			     
	//var strSql = "select a.actugetno, a.otherno, (select name from ldperson where customerno = a.appntno), a.paymode, a.sumgetmoney, a.bankcode, a.bankaccno, a.accname,a.drawertype,a.drawerid "
	//           + " from ljaget a where  EnterAccDate is null and managecom like '"+document.all('FManageCom').value+"%' "
  //        	 + getWherePart("ActuGetNo", "ActuGetNo")
  //        	 + getWherePart("OtherNo", "OtherNo");

		var strSql=wrapSql1('LJAGet1',[document.all('FManageCom').value,fm.ActuGetNo.value,fm.OtherNo.value]);

	turnPage.queryModal(strSql, BankGrid);
  
  if (!turnPage.strQueryResult) {
    alert("�Բ���,δ�鵽��Ӧ���ݡ�");
    return ;
  }
  else
  {
		fm.ActuGetNo2.value = fm.ActuGetNo.value;
		qFlag=true;
  }
}

function initInput(){
   try{
      fm.ChequeNo2.value ='';
      fm.BankCode2.value ='';
      fm.BankCode2Name.value ='';
      fm.ChequeNo3.value ='';
      fm.BankCode3.value ='';
      fm.BankCode3Name.value ='';
      fm.BankCode4.value ='';
      fm.BankCode4Name.value ='';
      fm.BankAccNo4.value ='';
      fm.AccName4.value ='';
      fm.IDType.value ='';
      fm.IDTypeName.value ='';
      fm.IDNo.value ='';
    }catch(ex){}
}

function afterCodeSelect(cCodeName, Field) {
  if (cCodeName == "PayMode") {
  	/*
    if (Field.value == "1") {
      fm.BankCode.value = "";
      fm.AccNo.value = "";
      fm.AccName.value = "";
      
      fm.BankCode.verify = "";
      fm.AccNo.verify = "";
      fm.AccName.verify = "";
    }
    else if (Field.value == "4") {
      fm.BankCode.verify = "���д���|notnull&code:bank";
      fm.AccNo.verify = "�����˺�|notnull";
      fm.AccName.verify = "�˻�����|notnull";
    }*/
    initInput();
  for(i=1;i<=4;i++)
	{		
		if(i==Field.value)
		{
			document.all("divPayMode"+i).style.display='';
		}
		else
		{
		  document.all("divPayMode"+i).style.display='none';
		}
	}
  }
  	if(cCodeName == "bank" ){
				checkBank(document.all('BankCode4'),document.all('BankAccNo4'));
	  }
}


function showOne(parm1, parm2) {	
  //�жϸ����Ƿ�ȷʵ��ѡ��
	if(document.all(parm1).all('InpBankGridSel').value == '1' ) {
	  var index = (document.all(parm1).all('BankGridNo').value - 1) % (turnPage.blockPageNum * turnPage.pageLineNum);
	  fm.ActuGetNo2.value = turnPage.arrDataCacheSet[index][0];
	  fm.PayMode.value = turnPage.arrDataCacheSet[index][3];
	  if(fm.PayMode.value=='2')
	  {
	  	document.all("divPayMode2").style.display='';
		  fm.BankCode2.value = turnPage.arrDataCacheSet[index][5];
		  fm.ChequeNo2.value = turnPage.arrDataCacheSet[index][6];
	  }
	  if(fm.PayMode.value=='3')
	  {
	  	document.all("divPayMode3").style.display='';
		  fm.BankCode3.value = turnPage.arrDataCacheSet[index][5];
		  fm.ChequeNo3.value = turnPage.arrDataCacheSet[index][6];
	  }
	  if(fm.PayMode.value=='4')
	  {
	  	document.all("divPayMode4").style.display='';
		  fm.BankCode4.value = turnPage.arrDataCacheSet[index][5];
		  fm.BankAccNo4.value = turnPage.arrDataCacheSet[index][6];
		  fm.AccName4.value = turnPage.arrDataCacheSet[index][7];
		  fm.IDType.value = turnPage.arrDataCacheSet[index][8];
		  fm.IDNo.value = turnPage.arrDataCacheSet[index][9];
	  }
  }
}

function readBankInfo() {
	if(!qFlag)
	{
		alert("���Ƚ��в�ѯ�����������ʵ��ѯ���������ڣ�");
		return;
	}
  //var strSql = "select OtherNo, OtherNoType from ljaget where ActuGetNo='" + fm.ActuGetNo2.value + "'";
	  var strSql =wrapSql1('LJAGet2',[fm.ActuGetNo2.value]);
  var arrResult = easyExecSql(strSql, 1, 0, 1, 0, 1);
  

  if (arrResult[0][1] != "10") {
    alert("���Ǳ�ȫ���ݣ�����ֻ֧�ֱ�ȫ�������ݵ��˻���Ϣ��ѯ��");
    return;
  }
  
  //strSql = "select bankcode, bankaccno, accname from lccont where contno in (select contno from lpedoritem where edoracceptno='" + arrResult[0][0] + "') "
  //       + "union select bankcode, bankaccno, accname from lccont where contno in (select contno from lpedoritem where edorno='" + arrResult[0][0] + "') ";
	
	var strSql=wrapSql1('LCCont1',arrResult[0][0]);
  var arrResult2 = easyExecSql(strSql, 1, 0, 1, 0, 1);
  
//  if (arrResult2 == null) {
 //   strSql = "select bankcode, bankaccno, accname from lccont where contno in (select contno from lpedoritem where edorno='" + arrResult[0][0] + "')";
 //   arrResult2 = easyExecSql(strSql, 1, 0, 1, 0, 1);
  //}
  
  if (arrResult2 == null) {
    alert("û�в�ѯ���������ݣ�");
    return;
  }  
  
  if (arrResult2[0][0] != "") {
  	document.all("divPayMode4").style.display='';
    fm.PayMode.value = "4";
    fm.BankCode4.value = arrResult2[0][0];
    fm.BankAccNo4.value = arrResult2[0][1];
    fm.AccName4.value = arrResult2[0][2];
  }
}

function checkPayMode()
{
   if(document.all('PayMode').value=='4')  //������ѷ�ʽΪ4-����ת�ˣ�������д ��������,�����˺�,�������ҵ������ڱ���Ϊ��
    {
      if(trim(document.all('BankCode4').value)==''||trim(document.all('BankAccNo4').value)==''||trim(document.all('AccName4').value)=='')
        {
           alert("����ת��ʱ��������д ��������,�����˺�,����");
           return false;
        }
        
      if (trim(document.all('BankCode4').value)=="0101") 
      {
        if (trim(document.all('BankAccNo4').value).length!=19 || !isInteger(trim(document.all('BankAccNo4').value))) 
        {
          alert("�������е��˺ű�����19λ�����֣����һ���Ǻţ�*����Ҫ��");
          return false;
        }
      }
      
    }
//���Ϊ�ֽ�֧Ʊ�򲻼ӿ���
    if(document.all('PayMode').value=='3')  //������ѷ�ʽΪ2||3-֧Ʊ�࣬��Ʊ�ݺ�������в���Ϊ��
    {
      if(trim(document.all('ChequeNo3').value)==''||trim(document.all('BankCode3').value)=='')
        {
         alert("���ѷ�ʽΪ֧Ʊʱ��Ʊ�ݺ�������в���Ϊ��");
         return false;
        }
    }
    
    if(document.all('PayMode').value=='2')  //������ѷ�ʽΪ2||3-֧Ʊ�࣬��Ʊ�ݺ�������в���Ϊ��
    {
      if(trim(document.all('ChequeNo2').value)==''||trim(document.all('BankCode2').value)=='')
        {
         alert("���ѷ�ʽΪ֧Ʊʱ��Ʊ�ݺ�������в���Ϊ��");
         return false;
        }
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


/**
	mysql���������ݴ����������Sql�ַ���
	
	sqlId:ҳ����ĳ��sql��Ψһ��ʶ
	param:��������,sql��where��������Ĳ���
**/
function wrapSql1(sqlId,param){
	
	var mysql=new SqlClass();
	
	mysql.setResourceName("finfee.ModifyLAGetInputSql");
	mysql.setSqlId(sqlId);
	
	for(i=0;i<param.length;i++){
		 mysql.addSubPara(param[i]);
	}
	
	return mysql.getString();
	
}