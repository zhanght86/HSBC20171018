//TempFeeWithdrawInput.js���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var tFees;
var sqlresourcename = "app.TempFeeWithdrawInputSql";
//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var checkFlag = 0;
  //alert("��============="+FeeGrid.mulLineCount);
  //���������ȡֵ����У��
  if( verifyInput2() == false ) return false;
  
  for (i=0; i<FeeGrid.mulLineCount; i++) {
    if (FeeGrid.getChkNo(i)) { 
      checkFlag = checkFlag + 1;
    }
  }
  //alert("xuanzhong============="+checkFlag);
  if (checkFlag>=1) { 
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
   //lockScreen('lkscreen'); 

	//document.getElementById('TFConfirm').disabled = true; 
    document.getElementById("fm").submit(); //�ύ
  }
  else {
    alert("����ѡ��һ���ݽ�����Ϣ��"); 
  }
}

function PrintInform(){
var tempfeeno = fm.TempFeeNo.value;

  if (tempfeeno != "") {
  /*
    var strSql = "select ActuGetNo/*, OtherNo, OtherNoType, PayMode, SumGetMoney, StartGetDate  from ljaget where otherno ='"+tempfeeno+"' "
               //+ "(select tempfeeno from ljtempfee where otherno in "
               //+ "(select proposalno from lcpol where prtno='" + prtNo + "') union "
               //+ "select tempfeeno from ljtempfee where otherno in "
               //+ "(select '" + prtNo + "' from ldsysvar where sysvar='onerow' )"
               + " and ConfDate is null";
  //prompt("",strSql);
 */
var sqlid1="TempFeeWithdrawInputSql1";
var mySql1=new SqlClass();
mySql1.setResourceName(sqlresourcename);
mySql1.setSqlId(sqlid1);
mySql1.addSubPara(tempfeeno); 
 
             
  turnPage.strQueryResult  = easyQueryVer3(mySql1.getString(),1,0,1);  
  
  //add by jiaqiangli 2007-12-27
  if (!turnPage.strQueryResult ){
     alert("δ��ѯ��������ݻ���û�н����˷�ȷ��");
     return false;
     }else{
       fm3.PrtData1.value =easyExecSql(mySql1.getString());
       //alert(fm3.PrtData1.value);
     }
//  else
//     alert("��ѯ���ݳɹ���");

  }
  else {
    alert("�������շѺ���!"); 
    return false;
  }
     //var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    //var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
    //document.getElementById("PrintFee").disabled=true;
    document.getElementById("fm3").submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 

    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
    //ִ����һ������
  }
}

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
  //����������Ӧ�Ĵ���
  //window.showModalDialog("./ProposalQuery.jsp",window,"status:0;help:0;edge:sunken;dialogHide:0;dialogWidth=15cm;dialogHeight=12cm");
  //��ѯ���������һ��ģ̬�Ի��򣬲��ύ�������������ǲ�ͬ��
  //��ˣ����еĻ����Ҳ���Բ��ø�ֵ��
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

var queryBug = 0;
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
//�ݽ��Ѻ����ѯ��ť
function tempFeeNoQuery(afterQuery) {
	if(document.all("TempFeeNo").value == "" && document.all("PrtNo").value == "")
	{
		alert("��ѯ�������㣡���������վ��վݺŻ���ӡˢ��/�����ţ�");
		return;
		}
  var tempFeeNo = fm.TempFeeNo.value;
  var prtNo = fm.PrtNo.value;
  
  //ֻ��ʾ�����ݽ��Ѻ����δ�˷ѵ�����
  //�Ѻ������˷ѣ�ConfFlag���Ĳ���ʾ����
  //zy 2007-12-29
  //����һ���վ�ֻ��ʾһ����¼��ԭ��������շ���Ϣ�Ĳ�ѯ
  if (prtNo == "") { 
  /*
    var strSql = "select '', TempFeeNo, TempFeeType, APPntName, '', sum(PayMoney), PayDate, EnterAccDate, sum(PayMoney),'' from LJTempFee where 1=1 "
               + " and ConfFlag = '0' and enteraccdate is not null and managecom like '"+document.all('mComcode').value+"%' "
               //and not exists (select tempfeeno from ljagettempfee where tempfeeno=LJTempFee.tempfeeno)"
              // + " and exists (select 1 from ljtempfeeclass where enteraccdate is not null and enteraccdate<>'3000-1-1' and tempfeeno=ljtempfee.tempfeeno)"
               + getWherePart('TempFeeNo')
               + getWherePart('PayMoney')
               + getWherePart('PayDate')
               + getWherePart('EnterAccDate')
               + getWherePart('ManageCom')
               + getWherePart('Operator')
               + " group by TempFeeNo,TempFeeType,APPntName,PayDate,EnterAccDate";
              // + " group by TempFeeNo,TempFeeType,APPntName,OtherNo,PayDate,EnterAccDate,othernotype";
  */
var sqlid2="TempFeeWithdrawInputSql2";
var mySql2=new SqlClass();
mySql2.setResourceName(sqlresourcename);
mySql2.setSqlId(sqlid2);
mySql2.addSubPara(document.all('mComcode').value);
mySql2.addSubPara(fm.TempFeeNo.value); 
mySql2.addSubPara(fm.PayMoney.value); 
mySql2.addSubPara(fm.PayDate.value); 
mySql2.addSubPara(fm.EnterAccDate.value); 
mySql2.addSubPara(fm.ManageCom.value);  
mySql2.addSubPara(fm.Operator.value); 
  var strSql = mySql2.getString();
  
  }
  else {
  /*
    var strSql = "select '', TempFeeNo, TempFeeType, APPntName, '', sum(PayMoney), PayDate, EnterAccDate,sum(PayMoney),'' from LJTempFee where 1=1 "
               + " and ConfFlag = '0' and enteraccdate is not null and managecom like '"+document.all('mComcode').value+"%' "
               // and not exists (select tempfeeno from ljagettempfee where tempfeeno=LJTempFee.tempfeeno)"
              // + " and exists (select 1 from ljtempfeeclass where enteraccdate is not null and enteraccdate<>'3000-1-1' and tempfeeno=ljtempfee.tempfeeno)"
               + getWherePart('TempFeeNo')
               + getWherePart('PayMoney')
               + getWherePart('PayDate')
               + getWherePart('EnterAccDate')
               + getWherePart('ManageCom')
               + getWherePart('Operator')
               + " and OtherNo='" + prtNo + "'"
               + " group by TempFeeNo,TempFeeType,APPntName,PayDate,EnterAccDate";
               //+ " group by TempFeeNo,TempFeeType,APPntName,OtherNo,PayDate,EnterAccDate,othernotype";               
*/
var sqlid3="TempFeeWithdrawInputSql3";
var mySql3=new SqlClass();
mySql3.setResourceName(sqlresourcename);
mySql3.setSqlId(sqlid3);
mySql3.addSubPara(document.all('mComcode').value);
mySql3.addSubPara(fm.TempFeeNo.value); 
mySql3.addSubPara(fm.PayMoney.value); 
mySql3.addSubPara(fm.PayDate.value); 
mySql3.addSubPara(fm.EnterAccDate.value); 
mySql3.addSubPara(fm.ManageCom.value);  
mySql3.addSubPara(fm.Operator.value);  
mySql3.addSubPara(prtNo); 
  
  var strSql = mySql3.getString();
  }
  //alert(strSql);
             
  turnPage.queryModal(strSql, FeeGrid);
  
  if (FeeGrid.mulLineCount <= 0 )
     alert("δ��ѯ��������ݣ�");
  else
     alert("��ѯ���ݳɹ���");
}
