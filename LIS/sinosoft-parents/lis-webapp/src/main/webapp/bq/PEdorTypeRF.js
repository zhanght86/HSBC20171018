//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage1 = new turnPageClass();  
//Ӧ�û�����
var tSSumLoanMoney=0;
//ʵ��Ƿ����
var tASumLoanMoney=0;
var sqlresourcename = "bq.PEdorTypeRFInputSql";

function verify()
{

	  var tFlag=false;
		var tLength=LoanGrid.mulLineCount;
	  var tEdorNo=fm.EdorNo.value;
	  for(var i=0;i<tLength;i++)
	  {
	  	if(LoanGrid.getChkNo(i))
	  	{
	  	 tFlag=true;
	  	 //alert(LoanGrid.getRowColData(i,7));
	  	 tASumLoanMoney+=(parseFloat(LoanGrid.getRowColData(i,7)));
       if (LoanGrid.getRowColData(i, 7) == "")
       {
           alert("��" + (i+1) + "�л������Ϊ��");
           return false;
       }
       else
       {
         if (parseFloat(LoanGrid.getRowColData(i, 7)) > (parseFloat(LoanGrid.getRowColData(i, 4))+parseFloat(LoanGrid.getRowColData(i, 5)).toFixed(2)))
         {       	 
           alert("��" + (i+1) + "�л�����ڽ���ȷ�Ͻ�");
           return false;
         }
        if (parseFloat(LoanGrid.getRowColData(i, 7)) < parseFloat(LoanGrid.getRowColData(i, 5)))
         {
           alert("��" + (i+1) + "�л���С�ڱ��λ�����Ϣ����ȷ�Ͻ�");
           return false;
         }
         if (parseFloat(LoanGrid.getRowColData(i, 7)) < 500)
         {
           if(!confirm("��" + (i+1) + "�л���С��500���������С��500���Ƿ������"))
           {
           	   return;
           	}
         }
       }	 
	  	} 
	  	
	  	if(i==(tLength-1)&&!tFlag)
	  	{
	  		alert("������ѡ��һ��Ҫ���Ļ����¼��Ҫô�뷵��");
        return false;
	  	}
	  }	
	  	CountSumLoan();	
	  	
	  	//alert(tASumLoanMoney-tSSumLoanMoney);
	  	//alert(tSSumLoanMoney);
  	  	//�ۼƽ����ڻ���������Ϊ���塣
	  	if((tASumLoanMoney-tSSumLoanMoney)==0)
	  	{
	  		fm.PayOffFlag.value='1';	 
	  		tSSumLoanMoney=0; 	
	  		tASumLoanMoney=0;	
	  	}else{
	  		fm.PayOffFlag.value='0';
	  		tSSumLoanMoney=0; 	
	  		tASumLoanMoney=0;		 	  				
	  	}

  return true;
}

function edorTypeRFSave()
{
  
  if (!verify()) 
  {
    return; 
  }
  
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
var iWidth=550;      //�������ڵĿ��; 
var iHeight=250;     //�������ڵĸ߶�; 
var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

showInfo.focus();
 
  document.all('fmtransact').value = "INSERT||MAIN";
  fm.action="./PEdorTypeRFSubmit.jsp";
  fm.submit();
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content)
{ 
  try { showInfo.close(); } catch (e) {}
  
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
var iWidth=550;      //�������ڵĿ��; 
var iHeight=250;     //�������ڵĸ߶�; 
var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

showInfo.focus();
  queryBackFee();
}

function returnParent()
{
    top.opener.initEdorItemGrid();
    top.opener.getEdorItemGrid();
    top.close();
    top.opener.focus();
}

function showNotePad()
{
	var MissionID = top.opener.document.all("MissionID").value;
	var SubMissionID = top.opener.document.all("SubMissionID").value;
	var ActivityID = "0000000003";
	var OtherNo = top.opener.document.all("OtherNo").value;

	var OtherNoType = "1";
	if(MissionID == null || MissionID == "")
	{
		alert("MissionIDΪ�գ�");
		return;
	}		
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");	
}

function ShowCustomerInfo()
{
  try
  {
    var tContNo=document.all("ContNo").value;
    //alert(tContNo);
    if(tContNo != null && tContNo != "")
    {
/*        var strSQL =" Select a.appntno,'Ͷ����',a.appntname,a.idtype||'-'||x.codename,a.idno,a.appntsex||'-'||sex.codename,a.appntbirthday From lcappnt a "
										+" Left Join (Select code,codename From ldcode Where codetype='idtype') x On x.code = a.idtype "
										+" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = a.appntsex  Where contno='"+tContNo+"'"
										+" Union"
										+" Select i.insuredno,'������',i.name,i.IDType||'-'||xm.codename,i.IDNo,i.Sex||'-'||sex.codename,i.Birthday From lcinsured i "
										+" Left Join (Select code,codename From ldcode Where codetype='idtype') xm On xm.code = i.idtype "
										+" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = i.sex Where contno='"+tContNo+"'";
//alert(strSQL);
*/        
    var strSQL = "";
	var sqlid1="PEdorTypeRFInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tContNo);//ָ������Ĳ���
	mySql1.addSubPara(tContNo);
	strSQL=mySql1.getString();
        
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
        
        //�ж��Ƿ��ѯ�ɹ�
        if (!turnPage.strQueryResult) 
        {
            return false;
        }
        
       turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
				//��ѯ�ɹ������ַ��������ض�ά����
				turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
				//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
				turnPage.pageDisplayGrid = CustomerGrid;
				//����SQL���
				turnPage.strQuerySql = strSQL;
				//���ò�ѯ��ʼλ��
				turnPage.pageIndex = 0;
				//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
				arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
				//����MULTILINE������ʾ��ѯ���
				displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  		}
  }
  catch(ex)
  {
      alert("��ѯ�ͻ�������Ϣʧ�ܣ�");
  }
} 

//�Խ����Ϣ���в�ѯ
function getLoanInfo()
{ 
	  var tLength=LoanGrid.mulLineCount;
	  
	  var tEdorNo=fm.EdorNo.value;
	  for(var i=0;i<tLength;i++)
	  {
	  	 var tPreEdorNo=LoanGrid.getRowColData(i,1);
	  	 var tCurrency = LoanGrid.getRowColData(i,9);
//	  	 var strSQL="select nvl(ReturnMoney+ReturnInterest,'') from LPReturnLoan where EdorNo='"+tEdorNo+"' and LoanNo='"+tPreEdorNo+"'";
	  	 
	var strSQL = "";
	var sqlid2="PEdorTypeRFInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql2.addSubPara(tPreEdorNo);
	mySql2.addSubPara(tCurrency);
	strSQL=mySql2.getString();
	  	 
	  	 var brr = easyExecSql(strSQL); 
	  	 if(brr)
	  	 {
	  	 	var dMoney = brr[0][0] ;
	  	 	if(dMoney != null && dMoney != "null" && dMoney != ""){
	  	 	   LoanGrid.setRowColData(i,7,brr[0][0]);
	  	 	}
	  	 }else
	  	 		{
	  	 		//alert(LoanGrid.getRowColData(i,4));
	  	 		//alert(LoanGrid.getRowColData(i,5));
	  	 	  var tSumLoan=((parseFloat(LoanGrid.getRowColData(i,4))+ parseFloat(LoanGrid.getRowColData(i,5)))).toFixed(2); 
          LoanGrid.setRowColData(i,7,tSumLoan);	  	 		
	  	 		}	  	   
	  	}
	}
	
	function CountSumLoan()
	{
		
	  var tLength=LoanGrid.mulLineCount;
	  for(var i=0;i<tLength;i++)
	  {
	  	 tSSumLoanMoney+=(parseFloat(LoanGrid.getRowColData(i,4))+ parseFloat(LoanGrid.getRowColData(i,5)));			  
	  	}
	  tSSumLoanMoney=tSSumLoanMoney.toFixed(2)							
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
