var showInfo;

var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var sqlresourcename = "bq.PEdorTypeLGInputSql";


function getCustomerGrid()
{
	//alert("a");
    var tContNo=document.all("ContNo").value;
    if(tContNo!=null&&tContNo!="")
    {
/*        var strSQL = " Select a.appntno, 'Ͷ����', a.appntname, "
                    +" (select trim(n.code)||'-'||trim(n.CodeName) from LDCode n where trim(n.codetype) = 'sex' and trim(n.code) = trim(appntsex)),"
                    +" a.appntbirthday, "
                    +" (select trim(m.code)||'-'||trim(m.CodeName) from LDCode m where trim(m.codetype) = 'idtype' and trim(m.code) = trim(idtype)), "
                    +" a.idno "
                    +" From lcappnt a Where contno='" + tContNo+"'"
                    +" Union"
                    +" Select i.insuredno, '������', i.name, "
                    +" (select trim(u.code)||'-'||trim(u.CodeName) from LDCode u where trim(u.codetype) = 'sex' and trim(u.code) = trim(sex)),"
                    +" i.Birthday,"
                    +" (select trim(y.code)||'-'||trim(y.CodeName) from LDCode y where trim(y.codetype) = 'idtype' and trim(y.code) = trim(idtype)), "
                    +" i.IDNo "
                    +" From lcinsured i Where contno='" + tContNo+"'";
        //prompt("",strSQL);
*/        
    var strSQL = "";
	var sqlid1="PEdorTypeLGInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tContNo);//ָ������Ĳ���
	mySql1.addSubPara(tContNo);
	strSQL=mySql1.getString();
        
        arrResult = easyExecSql(strSQL);
        
        if (arrResult)
        {
            displayMultiline(arrResult,CustomerGrid);
        }
    }
}

function getCustomerBnfGrid()
{
    var tContNo=document.all("ContNo").value;
    if(tContNo!=null&&tContNo!="")
    {
/*        var strSQL = "select a.name,(select trim(n.code)||'-'||trim(n.CodeName) from LDCode n where trim(n.codetype) = 'sex' and trim(n.code) = trim(sex)),"
        	+"  a.birthday,(select trim(m.code)||'-'||trim(m.CodeName) from LDCode m where trim(m.codetype) = 'idtype' and trim(m.code) = trim(idtype)) "
        	+"  ,a.idno,(select trim(m.code)||'-'||trim(m.CodeName) from LDCode m where trim(m.codetype) = 'bnftype' and trim(m.code) = trim(bnftype)), "
        	+"  (select trim(m.code)||'-'||trim(m.CodeName) from LDCode m where trim(m.codetype) = 'relation' and trim(m.code) = trim(a.relationtoinsured)),"
        	+"  (select trim(m.code)||'-'||trim(m.CodeName) from LDCode m where trim(m.codetype) = 'bnforder' and trim(m.code) = trim(a.bnfgrade)),a.bnflot,a.bankcode,a.BankAccNo,a.AccName from lcbnf "
        	+"a where contno='"+tContNo+"' and BnfType='0' order by bnfgrade,a.bnfno,bnflot";
*/        
    var strSQL = "";
	var sqlid2="PEdorTypeLGInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(tContNo);//ָ������Ĳ���
	strSQL=mySql2.getString();
        
        arrResult = easyExecSql(strSQL);
        if (arrResult)
        {
            displayMultiline(arrResult,CustomerBnfGrid);
        }
    }
}


function getCustomerActBnfGrid()
{
    var tContNo=document.all("ContNo").value;
    if(tContNo!=null&&tContNo!="")
    {
//    	var sql="select name,sex,birthday,idtype,idno,relationtoinsured,bnflot,Remark,bankcode,BankAccNo,AccName from lpbnf where edorno in (select edorno from lpedoritem where edoracceptno='"+document.all('EdorAcceptNo').value+"')";
    	
    var sql = "";
	var sqlid3="PEdorTypeLGInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(document.all('EdorAcceptNo').value);//ָ������Ĳ���
	sql=mySql3.getString();
    	
    	arrResult = easyExecSql(sql);
    	if(arrResult)
    	{
    		displayMultiline(arrResult,CustomerActBnfGrid);
    	}
    	else
    	{
/*	        var strSQL = "select a.name,sex,"
	        	+"  a.birthday,idtype"
	        	+"  ,a.idno,"
	        	//+"(select trim(m.code)||'-'||trim(m.CodeName) from LDCode m where trim(m.codetype) = 'bnftype' and trim(m.code) = trim(bnftype)), "
	        	+"  relationtoinsured,"
	        	//+"  (select trim(m.code)||'-'||trim(m.CodeName) from LDCode m where trim(m.codetype) = 'bnforder' and trim(m.code) = trim(a.bnfgrade)),"
	        	+"a.bnflot,'' ,a.bankcode,a.BankAccNo,a.AccName from lcbnf "
	        	+"a where contno='"+tContNo+"' and BnfType='0' and bnfgrade='1' order by bnfgrade,a.bnfno,bnflot";
*/	        
	var strSQL = "";
	var sqlid4="PEdorTypeLGInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(tContNo);//ָ������Ĳ���
	strSQL=mySql4.getString();
	        
	        arrResult = easyExecSql(strSQL);
	        if (arrResult)
	        {
	            displayMultiline(arrResult,CustomerActBnfGrid);
	        }
    	}
    }
}



function sedorTypeDBSave()
{
  if (PolGrid.mulLineCount <= 0) {
    alert("û�з��������ı���!");
    return false;
  }
  
 if(!verify())
 {
    return false;
 }
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
  document.all("fmtransact").value = "EdorSave";
  fm.action="./PEdorTypeLGSubmit.jsp";
  fm.submit();
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{

    showInfo.close();
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    if (FlagStr == "Success")
    {
      queryBackFee();
    }
}


function returnParent()
{
    top.close();
    top.opener.focus();
    top.opener.getEdorItemGrid();
}

function showNotePad()
{
    var MissionID = top.opener.document.all("MissionID").value;
    var SubMissionID = top.opener.document.all("SubMissionID").value;;
    var ActivityID = "0000000003";
    var OtherNo = top.opener.document.all("OtherNo").value;;

    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionIDΪ�գ�");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");
}


function Edorquery()
{
    var ButtonFlag = top.opener.document.all('ButtonFlag').value;
    if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
    }
    else
    {
        divEdorquery.style.display = "";
    }
}



function afterCount(tFlagStr,tContent,tMulArray)
{
        //��ʼ��MulLine
        if (tFlagStr == "Success")
        {
           displayMultiline(tMulArray,PolGrid,turnPage);
        }else
        	{
        		
          var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + tContent ;
            //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
									
        }

}


function verify()
{
	if(!PolGrid.checkValue2())
	{
		return false;
	}
	var money=0;
  for (i=0; i<PolGrid.mulLineCount; i++)
  {
	  if(PolGrid.getRowColData(i, 10)!=null&&PolGrid.getRowColData(i, 10)!="")
	  {
	      if ((parseFloat(PolGrid.getRowColData(i, 10)) > PolGrid.getRowColData(i, 6)))
	      {
	        alert("��" + (i+1) + "��ʵ����ڿ������ȷ�Ͻ�");
	        return false;
	      }
	      
	      if ( (parseFloat(PolGrid.getRowColData(i, 10))<=0))
      		{
        		alert("��" + (i+1) + "��ʵ������¼�����0����ֵ��");
        		return false;
      		}
      		
	      money = money+parseFloat(PolGrid.getRowColData(i, 10));
	  }
  }
  if(money==0)
  {
  	    alert("ʵ����ȡ���Ϊ0����¼�������!���������ֵΪ0����ʾû�п���ȡ��������볷�����α�ȫ��");
        return false;
  }
  //alert(money);
  CustomerActBnfGrid.delBlankLine();
  if(!CustomerActBnfGrid.checkValue2())
  {
	  return false;
  }
  
  if(CustomerActBnfGrid.mulLineCount<=0)
  {
	  alert("δ¼��ʵ����ȡ�ˣ�");
	  return false;
  }
  var bnflot=0;
  //alert("CustomerActBnfGrid.mulLineCount:::::"+CustomerActBnfGrid.mulLineCount); 
  var getformflag;
  for (i=0;i<CustomerActBnfGrid.mulLineCount;i++)
  {
	  //CustomerActBnfGrid.getRowColData(i,1)
		var tIDType=CustomerActBnfGrid.getRowColData(i,4);
		var tIDNo=CustomerActBnfGrid.getRowColData(i,5);
		if(tIDType=="0")
		{
		   tBirthday=getBirthdatByIdNo(tIDNo);
		   tSex=getSexByIDNo(tIDNo);
		   if(tBirthday=="��������֤��λ������"||tSex=="��������֤��λ������")
		   {
		   	  str="��"+(i+1)+"�����֤��λ���������!!!" ;
		   	  alert(str);
		   	  return false;
		   }
		   else
		   {  
			   CustomerActBnfGrid.setRowColData(i,3,tBirthday) ;
			   CustomerActBnfGrid.setRowColData(i,2,tSex) ;
		   }
	    }
		if(CustomerActBnfGrid.getRowColData(i,8)!="1"&&CustomerActBnfGrid.getRowColData(i,8)!="4"&&CustomerActBnfGrid.getRowColData(i,8)!="9")
		{
			alert("ʵ����ȡ���б��е�"+(i+1)+"����ȡ��ʽ����ȷ��������ѡ��");
			return false;
		}
		
		
		if(i==0)
		{
			getformflag=CustomerActBnfGrid.getRowColData(i,8);
		}
		else
		{
			if(getformflag!=CustomerActBnfGrid.getRowColData(i,8))
			{
				alert("��ȡ��ʽ����ʵ����ȡ�˵���ȡ��ʽ����һ�£�");
				return false;
			}
		}
		
		
		if(CustomerActBnfGrid.getRowColData(i,8)=="4"||CustomerActBnfGrid.getRowColData(i,8)=="9")
		{
			if(CustomerActBnfGrid.getRowColData(i,9)==null||CustomerActBnfGrid.getRowColData(i,9)==""||
					CustomerActBnfGrid.getRowColData(i,10)==null||CustomerActBnfGrid.getRowColData(i,10)==""||
					CustomerActBnfGrid.getRowColData(i,11)==null||CustomerActBnfGrid.getRowColData(i,11)=="")
			{
				alert("ʵ����ȡ���б��е�"+(i+1)+"����ȡ��ʽΪ������ת�ˡ����ߡ����������������Ա�����д�����б��롿���������˺š��͡������ʻ�����");
				return false;
			}
			//����ת�ʻ����ϼ��������ʺŹ���У��
			if(!checkBank(CustomerActBnfGrid.getRowColData(i,9),CustomerActBnfGrid.getRowColData(i,10)))
			{
			  //CustomerActBnfGrid.setRowColData(i,8,"");
			  CustomerActBnfGrid.setRowColData(i,10,"");
			  return false;
			}
			
			if(CustomerActBnfGrid.getRowColData(i,11)!=CustomerActBnfGrid.getRowColData(i,1))
			{
				alert("ʵ����ȡ���б��е�"+(i+1)+"����ȡ�������������ʻ�����һ��");
				return false;
			}

		}
		bnflot += parseFloat(CustomerActBnfGrid.getRowColData(i,7))*100;
  }
  if(bnflot!=100)
  {
	  alert("ʵ����ȡ����ȡ�����ϼƲ���100%��������¼����ȡ������");
	  return false;
  }
  
  return true;
}


function confirmSecondInput1(aObject,aEvent)
{
 {
  if(theFirstValue!="")
  {
   clipboardData.setData('text','');
   theSecondValue = aObject.value;
   if(theSecondValue=="")
   {
    alert("���ٴ�¼�룡");
    aObject.value="";
    aObject.focus();
    return;
   }
   if(theSecondValue==theFirstValue)
   {
    aObject.value = theSecondValue;
    theSecondValue="";
    theFirstValue="";
    return;
   }
   else
   {
    alert("����¼����������������¼�룡");
    theFirstValue="";
    theSecondValue="";
    aObject.value="";
    aObject.focus();
    return;
   }
  }
  else
  {
   theFirstValue = aObject.value;
   theSecondValue="";
   if(theFirstValue=="")
   {
    return;
   }
   aObject.value="";
   aObject.focus();
   clipboardData.setData('text','');
   return;
  }
 }
}

/**
 * �����ʺŹ���
 */
function checkBank(tBankCode,tBankAccNo)
{
	if(tBankCode.length>0 && tBankAccNo.length>0)
	{
		if(!checkBankAccNo(tBankCode,tBankAccNo))
		{	
			return false;
		}
	}
	return true;
}

  