 //               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();   
var showInfo;
var mDebug="0";
var tResourceName="finfee.TempFeeInputSql";

//�����Ӷ���ִ�еĴ���
var addAction = 0;
//�ݽ����ܽ��
//var sumTempFee = 0.0;
//�ݽ�����Ϣ�н��ѽ���ۼ�
var tempFee = "";
//�ݽ��ѷ�����Ϣ�н��ѽ���ۼ�
var tempClassFee = "";
//����ȷ���󣬸ñ�����Ϊ�棬�������һ��ʱ�������ֵ�Ƿ�Ϊ�棬Ϊ�������Ȼ���ٽ��ñ����ü�
var confirmFlag=false;
// 
var arrCardRisk;

var todayDate = new Date();
var sdate = todayDate.getDate();
var smonth= todayDate.getMonth() +1;
var syear= todayDate.getYear();
//var sToDate = syear + "-" +smonth+"-"+sdate

//�ύ�����水ť��Ӧ����
function submitForm()
{

 if(addAction>0)//����ύ���ݻ����
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
	  document.getElementById("fmSave").submit(); //�ύ
	  initForm();
	  initInput();
 }
  else alert("����������");

}

//��ӡƱ��
function printInvoice()
{
  window.open("./TempFeeInputPrintMain.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
	TempClassToGrid.clearData(); 
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
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
//���ε�
/*    if (fm.TempFeeType.value==5)
    {
      var tSql = "select tempfeeno from ljtempfee a where a.otherno='"+TempToGrid.getRowColData(0,10)
                 +"' and a.tempfeetype='5' and managecom='"+TempToGrid.getRowColData(0,6)
                 +"' and a.makedate=to_char(sysdate, 'YYYY-MM-DD')"
                 +"  and a.maketime = (select max(maketime) from ljtempfee b where b.otherno='"+TempToGrid.getRowColData(0,10)
                 +"' and makedate=to_char(sysdate, 'YYYY-MM-DD'))";	

      var arrResult = easyExecSql(tSql);
      tSql = "select enteraccdate from ljtempfee where tempfeeno='"+arrResult+"'";   
      var TarrResult = easyExecSql(tSql);
      //alert("1======================="+TarrResult);
      if (TarrResult != "")
      {
      
      tSql = "select max(actugetno) from ljagettempfee where tempfeeno='"+arrResult+"'";	
      arrResult = easyExecSql(tSql);
      
      alert("��Ԥ��ʵ�����룺"+"��"+arrResult+"��");
      }
      else
      	{
      alert("��Ԥ��δ���ˣ�����ʱ����Ԥ���");	
      }
    }*/
   // PrintData();
  }  
  clearFormData();
  initForm();
}

function PrintData()
{
   //tSql = "select enteraccdate,tempfeetype,otherno from ljtempfee where tempfeeno='"+document.all('TempFeeNo').value+"'";   
   //var mysql=new SqlClass();
	 //mysql.setResourceName("finfee.TempFeeInputSql");
	 //mysql.setSqlId("LJTempFee1");
	 //mysql.addSubPara(document.all('TempFeeNo').value);
   
   
   
   var TarrResult = easyQueryVer3(wrapSql(tResourceName,'LJTempFee1',[document.all('TempFeeNo').value]), 1, 1, 1);;
   
   var ArrData = decodeEasyQueryResult(TarrResult);

   if (TarrResult!=null&&TarrResult!="")
   {
      if (ArrData[0][0]!= ""&&(ArrData[0][1]=="2"||ArrData[0][1]=="4"||ArrData[0][1]=="6"))
   				{
	    				if(confirm("ȷ��Ҫ��ӡ��Ʊ��"))
	      						{
     	     								if (ArrData[0][1]=="2")
     	         									window.open("../operfee/ExtendInvoice.jsp?TempFeeNo=" + document.all('TempFeeNo').value,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
           								else if (ArrData[0][1]=="4")
               									window.open("../bq/BqCheckPrintInput.jsp?OtherNo=" + ArrData[0][2],"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes"); 
               					  else if (ArrData[0][1]=="6")
               					  	     window.open("../claim/ClaimCheckPrintSave.jsp?ClmNo=" + ArrData[0][2],"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes"); 
        						}
      				else
        						{
  	        							return;
        						}
   				}
  }
      	
}

function afterSubmit2( FlagStr, content )
{
  showInfo.close();
	TempClassToGrid.clearData(); 
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
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    
  }
  
  //clearFormData();
  //initForm();
}

function showFinance(cashValue1,chequeValue1)
{
	
	showInfo.close();
	var urlStr="./FinanceInfo.jsp?cashValue="+cashValue1+"&chequeValue="+chequeValue1 ;
	//window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:1000px;dialogHeight:400px");  
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=1000;      //�������ڵĿ��; 
	var iHeight=400;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterQuery( FlagStr, content )
{
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
    //var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
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
  alert("update click");
}           

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
  //����������Ӧ�Ĵ���
  	    window.open("./TempFeeQuery.html");
}           

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  //����������Ӧ�Ĵ���
  alert("delete click");
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

function queryTempfee()
{
	window.open("./TempFeeQueryMain.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//��֤�ֶε�ֵ
function confirm1()
{
	//2008-01-03 zy ȥ������Ŀո�
  //���ȼ���¼���
  if((document.all('ManageCom').value).length<8||(document.all('PolicyCom').value).length<8) 
  {
  	alert("�շѻ����͹���������������8λ");
    return false;	
  }

  var rowNum=TempToGrid.mulLineCount;
  if(rowNum>=1)
  {
   alert("�����ύ��������");
   return false;
  }
  
  
  if (!verifyInput()) return false; 
  if(!trimNo()) return false;

  
  var typeFlag=false;	
  
  if (document.all('TempFeeType').value=="1" || document.all('TempFeeType').value=="2"||document.all('TempFeeType').value=="7")
  {
    if (document.all('AgentCode').value=='')	
    {
    	alert("�����˲���Ϊ��!");
    	return false;
    }
  }
     
  //�µ����ѣ�¼���ݽ��Ѻ� 
  if (document.all('TempFeeType').value=="1") 
  {
  	
  	var mysql=new SqlClass();
  	
		mysql.setResourceName("finfee.TempFeeInputSql");
		mysql.setSqlId("LAAgent1");
		mysql.addSubPara(trim(document.all('AgentCode').value));
  	
	  //������ְ�Ĵ����˲���¼���µ�
	 	//var state = decodeEasyQueryResult(easyQueryVer3("SELECT agentstate FROM laagent where agentcode='"+trim(document.all('AgentCode').value)+"'"));
	 	
	 	var state = decodeEasyQueryResult(easyQueryVer3(mysql.getString()));
	 	
	 	if(state == null || state == "")
		{
			alert("�ô����˲����ڻ�״̬δ֪!");
			return false;
		}
		if(parseInt(state[0][0])>=3)
		{
			alert("�ô������Ѿ���ְ������¼���µ��ݽ���!");
			return false;
		}
		/*if(document.all('CertifyFlag').value == "3")
		{
			initTempGridReadOnly6();
		}
		else
		{
      initTempGrid();
    }*/
    initTempGrid();
    initTempClassGrid();
    TempGrid.clearData("TempGrid");
    TempClassGrid.clearData("TempClassGrid"); 
    if(document.all('CertifyFlag').value == "1")
    {
    	    //У��¼��
		   if (!verifyElement("Ͷ����ӡˢ����|NOTNULL&len=14", document.all('InputNo1').value)) {
		      return false;
		   } 
		   if(verifyAgentCode(document.all('InputNo1').value)==false)
		   {
		    	return false;
		   }
      if (verifyElement("���շ��վݺ�|NOTNULL&len=14", document.all('InputNo2').value) != true) {
       return false;
       } 
           //У�鵥֤����
           //2008-12-23 17:50 ��ʱ���ε���֤��У��
    //  if (!verifyTempfeeNo(document.all('InputNo2').value)) 
    //   return false;  
    //2009-04-28 18:27 ���е�֤У�� 2009-04-29 17:44 ��ʱ���ε�֤У��
    //if(!(verifyTempfeeNoNew("3201",document.all('InputNo2').value,document.all('AgentCode').value)))
    //	return false; 
      //add ln 2009
      //if (!verifyTempNo(document.all('InputNo2').value)) return false; 
       
      document.all('TempFeeNo').value = document.all('InputNo2').value;     
    }
    else if(document.all('CertifyFlag').value == "2")
   	{
   		 if (verifyElement("Ͷ����ӡˢ����|NOTNULL&len=14", document.all('InputNoB1').value) != true) {
		      return false;
		   } 
		   if(verifyAgentCode(document.all('InputNoB1').value)==false)
		   {
		    	return false;
		   }
      if (verifyElement("���л���Э�����|NOTNULL", document.all('InputNoB').value) != true) {
       return false;
       }
       if(document.all('InputNoB').value.trim()== document.all('InputNoB1').value.trim()) 
       {
       	 alert("���л���Э�������Ͷ����ӡˢ��һ�£�������¼�����л���Э����ţ�");
       	 return false;
       	}       
       //add ln 2009
      if (!verifyTempNo(document.all('InputNoB').value)) return false; 
             
       document.all('TempFeeNo').value = document.all('InputNoB').value; 
         
   	}
    else if(document.all('CertifyFlag').value == "3")
   	{
   		var Cardno=document.all('InputNoC').value.trim();
		  var CardCertifyCode=document.all('CardCode').value.trim();
		  
       if (verifyElement("��֤����|NOTNULL&code:CardCode", CardCertifyCode) != true) {
       return false;
       }    
       if (verifyElement("��֤����|NOTNULL", Cardno) != true) {
       return false;
       } 
       document.all('TempFeeNo').value = Cardno;   
       //2008-12-23 17:51 ��ʱ���ε�
       //2009-04-18 14:27 �ſ����ε�У��
    /*   if(needVerifyCertifyCode(CardCertifyCode)==true)
       {   	
		    //У�鵥֤����
		    //ȥ��֤״̬�����ѯ�ú����Ƿ���Ч,�ݽ����վݺ�
		    var strSql = "select CertifyCode from LZCardTrack where Startno='"+Cardno+"' and Endno='"+Cardno+"'  and StateFlag='0' and CertifyCode='" + CardCertifyCode + "' "    
		               + " and length(trim(Startno))=length('"+Cardno+"') and length(trim(Endno))=length('"+Cardno+"')";
		    
		    var strResult=easyQueryVer3(strSql, 1, 1, 1);
		    
		    if(!strResult)
		    {
		      alert("�õ�֤����֤����Ϊ��"+Cardno+" ��û�з��Ÿ��ô����ˣ�"+document.all('AgentCode').value+"��!");
		      return false;
		    }
       }*/
       //2009-04-28 18:47 ���е�֤У�� ��ʱ���ε�֤У��
      if(!(verifyTempfeeNoNew(CardCertifyCode,Cardno,document.all('AgentCode').value)))
    	return false; 
    
    //2009-04-29 15:13 ��Ҫ��ȡ���Զ���Ĳ�ѯ��ʾ���ɲ�����Ա�Լ�¼��
	    //��ʾ�ö����������Ϣ
	   /* var strSql = "select CertifyClass from LMCertifyDes where CertifyCode = '" + CardCertifyCode + "'";
	    var strResult = easyExecSql(strSql);
	    
	    if (strResult==null || strResult!="D") {
	      alert("�õ�֤���벻�Ƕ��");
	      return false;
	    }
	    
	    strSql = "select a.RiskCode, b.RiskName, a.Prem, a.PremProp, a.Premlot from LMCardRisk a, LMRisk b where a.riskcode=b.riskcode and CertifyCode = '" + CardCertifyCode + "'";
	    arrCardRisk = easyExecSql(strSql);
	
	    document.all('TempFeeNo').value = Cardno;           	
	    typeFlag = true;
	    
	    if (arrCardRisk==null) 
	    {
	    	alert("û����ض��������Ϣ��");
	    	return;
	    }
	    
	    if (arrCardRisk[0][3] == "1") {
	      initTempGridReadOnly6();
	    }
	    else {
	      initTempGrid();
	    }
	
	    for (k=0; k<arrCardRisk.length; k++) {
	      TempGrid.addOne("TempGrid");	      
	      TempGrid.setRowColData(k,1,arrCardRisk[k][0]);
	      TempGrid.setRowColData(k,2,arrCardRisk[k][1]);
	      TempGrid.setRowColData(k,3,arrCardRisk[k][2]);
	      TempGrid.setRowColData(k,4,CardCertifyCode);
	    } */	
   	}                
   else
   	{
   		alert("��ѡ����ȷ��֤����");
   		return false;
   	}
    //repair:����1���µ������������ӡˢ�ţ�����֤��ʽ
    //��֤��ʽ������Ҫ������Ӣģʽ�޸�
    //У�鵥֤����
   // if (!verifyTempfeeNo(document.all('InputNo2').value)) return false;ֻ��������վݵ��µ�
  //  if (!verifyPrtNo(document.all('InputNo1').value)) return false;������5.3һ�µ�У�����
        	
    typeFlag=true;
    fm.butConfirm.disabled = false;

    //TempGrid.setRowColData(0,7,fm.InputNob.value);
    if(document.all('CertifyFlag').value == "1")
    {
    	TempGrid.unLock();
      TempGrid.addOne("TempGrid");
      TempGrid.setRowColData(0,5,fm.InputNo1.value);
    }
    else if(document.all('CertifyFlag').value == "2")
    {
      TempGrid.unLock();
      TempGrid.addOne("TempGrid");
      TempGrid.setRowColData(0,5,fm.InputNoB1.value);
    }
    else if(document.all('CertifyFlag').value == "3")
    {
      TempGrid.unLock();
      TempGrid.addOne("TempGrid");
      TempGrid.setRowColData(0,5,document.all('CardCode').value);
    }
    else
    {
    }
    TempClassGrid.unLock();
    //���Ϊ���л���Э������ֻ��ʾ�������͵Ľ��ѷ�ʽ
    if(document.all('CertifyFlag').value == "2")
    {  
    	divTempFeeClassInput.style.display="";
    	document.all('PayMode').value='4';
    	divPayMode4.style.display="";
    }
    else
    {   	   
    	divTempFeeClassInput.style.display="";
  	}
  }
   
  //���ڴ��ս��ѣ�¼���ݽ��Ѻ� 
  if(document.all('TempFeeType').value=="2")
  {    	   	               
    //У��¼��
    
    if (verifyElement("������ͬ����|NOTNULL", document.all('InputNo3').value) != true) {
      return false;
    } 
    if (verifyElement("����֪ͨ�����|NOTNULL", document.all('InputNo4').value) != true) {
      return false;
    } 
    //У��¼��������Ƿ�ͱ���������һ��
    if(verifyAgent(document.all('InputNo3').value)==false)
    {
    	return false;
    }
    //У��¼���ͬ�ź��շѻ����Ƿ�һ��
    if(verifyContNo(document.all('InputNo3').value)==false)
    {
    	return false;
    } 
    //У���ͬ��״̬
    if(verifyContState(document.all('InputNo3').value)==false)
    {
    	return false;
    }     
    

    
    //���¼����3209�������շ��վݺţ���У�鵥֤�Ƿ��Ѿ����ŵ����������У������û�з��ŵ����������У���׼���������Ϣ��
//2008-12-23 17:52��ʱ����  
 /*   if(!((document.all('XQCardNo').value==null)||(document.all('XQCardNo').value=="")))
    {

    	 //У�鵥֤����

	    var strSql = "select CertifyCode from LZCardTrack where Startno<='"+document.all('XQCardNo').value+"' and Endno>='"+document.all('XQCardNo').value+"' and Receivecom like 'D%%' and StateFlag='0' and CertifyCode='3209'";        
      var strResult=easyQueryVer3(strSql, 1, 1, 1);
      if(!strResult) 
      {
         alert("�õ�֤����֤����Ϊ��"+document.all('XQCardNo').value+" ����û�з��Ÿ��ô�����!");
        	return false;
      }
    }*/
    // 2009-04-28 18:47 ���е�֤У�� ��ʱ���ε�֤У��
 
   //�������շ��վ�¼�����У�飬֧���µ�֤���ͱ���531001��¼��
    if(!((document.all('XQCardNo').value==null)||(document.all('XQCardNo').value=="")))
    {
    	if(!(verifyTempfeeNoNew("531001",document.all('XQCardNo').value,document.all('AgentCode').value)))
    	{
	     if(!(verifyTempfeeNoNew("3209",document.all('XQCardNo').value,document.all('AgentCode').value)))
	    	{
	    		return false; 
	    	}
	        fmSave.all('CERTIFY_XQTempFee').value ="3209";
	    }
    	
    	else 
    	{
    			fmSave.all('CERTIFY_XQTempFee').value ="531001";
    	}
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
	initTempGridReadOnly();
    initTempClassGrid();       
    document.getElementById("fm").submit();//��̨���Ը����ݽ��������ж�
    typeFlag=true;         	
    divTempFeeClassInput.style.display="";       	
  }
   
  //���ڷǴ��ս��ѣ������ݽ��Ѻ� 
  if(document.all('TempFeeType').value=="8")
  {	     
    //У��¼��
    //if (verifyElement("��������|NOTNULL&len=15", document.all('InputNo5').value) != true) {
    if (verifyElement("������ͬ����|NOTNULL", document.all('InputNo5').value) != true) { 
      return false;
    } 
    initTempGrid();
    
      var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
      var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
      var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fmTypeQuery.all('QueryNo').value = document.all('InputNo5').value;
      //var strSql = "select * from lcgrpcont where grpcontno = '" + document.all('InputNo5').value + "'";
      var strSql=wrapSql(tResourceName,'LCGrpCont1',[document.all('InputNo5').value]);
      var arrResult = easyExecSql(strSql);
      if(arrResult != null)                //�����ͬ��
      {
          fmTypeQuery.all('QueryType').value = "1";
          fmSave.all('NewQueryType').value = "1";
      }
      else
      {
    	  fmTypeQuery.all('QueryType').value = "2";
    	  fmSave.all('NewQueryType').value = "2";
      }
      document.getElementById("fmTypeQuery").submit();//ȥ��̨��ѯ�ú����Ƿ�ϸ�       	
        typeFlag=true;         	
      divTempFeeClassInput.style.display="";    
   
  } 
   
  //��ȫ���ѣ�¼�뽻���վݺ�
  if(document.all('TempFeeType').value=="4")
  {  	
    //initTempGrid();
    //initTempClassGrid();
    //initTempGridReadOnlyDo();
    initTempClassGrid();
    //У��¼��
    if (verifyElement("��ȫ�����|NOTNULL", document.all('InputNo7').value) != true) {
      return false;
    } 
    if (verifyElement("�����վݺ���|NOTNULL", document.all('InputNo8').value) != true) {
      return false;
    }

    //����4����ȫ���ѣ�¼�뽻���վݺţ�31����֤��ʽ   
    //��֤��ʽ���ύ��ѯʱ
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	//initTempGridReadOnly();
    //��ȫ���Ѳ���ʾ���ֱ��뼰��������
    initTempGridReadOnlyCont();
    //initTempClassGrid();          
    document.getElementById("fm").submit();//��̨���Ը����ݽ��������ж� 
    divTempFeeClassInput.style.display="";
    typeFlag=true;   		
  }
   
   //Ԥ�汣�� 
   if(document.all('TempFeeType').value=="5")
   {
   	document.all('PolicyCom').value=document.all('ManageCom').value;        	        
    initTempGridReadOnlyDo();
    initTempClassGrid(); 
    //У��¼��
    if (verifyElement("�ͻ�����|NOTNULL", document.all('InputNo9').value) != true) {
      return false;
    } 
		//var strSql = "select GrpName from LDGrp where CustomerNo='"+fm.InputNo9.value+"'";
	  var strSql=wrapSql(tResourceName,'LDGrp1',[fm.InputNo9.value]);
	  var arrResult = easyExecSql(strSql);
	  if (arrResult==null)
		{
			alert("�޴˵�λ�ͻ�����");
			return false;
		}
  else
  	{
    TempGrid.setRowColData(0,1,"000000");
    TempGrid.setRowColData(0,2,"0000000000");
    TempGrid.setRowColData(0,3,"");
    TempGrid.setRowColData(0,4,"");
    TempGrid.setRowColData(0,5,document.all('InputNo9').value);
    TempGrid.setRowColData(0,6,arrResult[0][0]);
    divTempFeeClassInput.style.display="";
    typeFlag=true;         	
    }
   }
  if(document.all('TempFeeType').value=="6")
  {  	
    //У��¼��
    if(document.all('ClaimFeeType').value=="1")
    {
    	if (verifyElement("�ⰸ��|NOTNULL", document.all('InputNo12').value) != true) {
	      return false;
	    } 
	    if (verifyElement("�����վݺ���|NOTNULL", document.all('InputNo11').value) != true) {
	      return false;
	    }
  	}
    //У��¼��
    if(document.all('ClaimFeeType').value=="2")
    {
    	if (verifyElement("�ⰸ��|NOTNULL", document.all('InputNoH12').value) != true) {
	      return false;
	    } 
	    if (verifyElement("�����վݺ���|NOTNULL", document.all('InputNoH11').value) != true) {
	      return false;
	    }
  	}

    //����4����ȫ���ѣ�¼�뽻���վݺţ�31����֤��ʽ   
    //��֤��ʽ���ύ��ѯʱ
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	if(document.all('ClaimFeeType').value=="2")
    {
    	initTempGridReadOnlyCont();
  	}
    if(document.all('ClaimFeeType').value=="1")
    {
    	initTempGridReadOnly();
  	}
    //initTempClassGrid();          
    document.getElementById("fm").submit();//��̨���Ը����ݽ��������ж� 
    divTempFeeClassInput.style.display="";
    typeFlag=true;
  }   
   //��������
//   if(document.all('TempFeeType').value=="9")
//   {     	        
//    //У��¼��
//    if (verifyElement("�����|NOTNULL", document.all('InputNo99').value) != true) 
//    {
//      return false;
//    }
//    
//    var calsql="select * from ljtempfee where otherno='"+document.all('InputNo99').value+"' and tempfeetype='9'";
//    var casResult=easyExecSql(calsql);
//    if(casResult!=null && casResult.length>0)
//		{
//			alert("�Ѿ��ɷѲ����ٴν���");
//			return ;
//		} 
//     
//   	var strSql = "select SumPrem,managecom from LXbalance where BalanceNo='"+document.all('InputNo99').value+"'"; 	
//   	var TarrResult = easyQueryVer3(strSql, 1, 1, 1);
//   	if(TarrResult == null ||TarrResult == ""||TarrResult=='null')
//   	{
//    		alert("�˽�����벻����");
//    		return false;	
//   	}	
//    else
//    {
//    	var ArrData = decodeEasyQueryResult(TarrResult);
//	  	if(ArrData[0][0]=="")
//	  	{
//	  	  alert("δ�鵽��������Ϣ");	
//	  	}
//	  	if(ArrData[0][1]=="")
//	  	{
//	  	  alert("δ�鵽�����������");	
//	  	}	 	
//    }	
//   	var M = arrResult;
//    initTempClassGrid();
//    initBalanceGrid(); 
//		TempGrid.addOne("TempGrid");    
//    
//    TempGrid.setRowColData(0,1,"000000");           
//    TempGrid.setRowColData(0,2,"��ѡ������");
//    TempGrid.setRowColData(0,3,pointTwo(ArrData[0][0]));
//    TempGrid.setRowColData(0,4,document.all('InputNo99').value);
//		fm.PolicyCom.value = ArrData[0][1];
//		fm.TempFeeNo.value = document.all('InputNo19').value;
//    divTempFeeClassInput.style.display="";
//    typeFlag=true;         	
//
//   }
   
  if (document.all('TempFeeType').value=="3")
  {
    if (document.all('InputNo13').value=='')	
    {
    	alert("������ͬ�Ų���Ϊ��!");
    	return false;
    }
    if (document.all('InputNo14').value=='')	
    {
    	alert("Ԥ���վݺŲ���Ϊ��!");
    	return false;
    }
    //var state=decodeEasyQueryResult(easyQueryVer3("SELECT count(*) FROM lcpol where contno='"+trim(document.all('InputNo13').value)+"' and mainpolno=polno and grppolno='00000000000000000000' and payintv>0 and exists (select riskcode from lmriskapp where riskcode=lcpol.riskcode and RiskPeriod='L')"));
		var state=decodeEasyQueryResult(easyQueryVer3(wrapSql(tResourceName,'LCPol1',[document.all('InputNo13').value])));
		


	  if(parseInt(state[0][0])==0)
	  {
	  	//var gstate=decodeEasyQueryResult(easyQueryVer3("SELECT count(*) FROM lcgrppol where grpcontno='"+trim(document.all('InputNo13').value)+"' and payintv>0 and exists (select riskcode from lmriskapp where riskcode=lcgrppol.riskcode and RiskPeriod='L' and subriskflag='M')"));
	  	var gstate=decodeEasyQueryResult(easyQueryVer3(wrapSql(tResourceName,'LCGrpPol1',[document.all('InputNo13').value])));
	  	
	  	
	  	if(parseInt(gstate[0][0])==0)
	  	{
				alert("ֻ���ڽ����ղ�����Ԥ�ղ���!");
				return false;
		  }
	  }
	
	  //var strSql="select RiskCode,(select riskname from lmrisk where riskcode=lcpol.riskcode),'0.0',contno,GrpPolNo from lcpol where grppolno='00000000000000000000' and appflag='1' and mainpolno=polno and contno='"+document.all('InputNo13').value+"' and managecom='"+document.all('ManageCom').value+"' and agentcode='"+document.all('AgentCode').value+"'";
		var strSql=wrapSql(tResourceName,'LMRisk1',[document.all('InputNo13').value,document.all('ManageCom').value,document.all('AgentCode').value])
   	var strResult = easyExecSql(strSql);
   	if (strResult==null)
   	{
   		//var pstrSql="select RiskCode,(select riskname from lmrisk where riskcode=lcgrppol.riskcode),'0.0',GrpContNo,'' from lcgrppol where  appflag='1'  and GrpContNo='"+document.all('InputNo13').value+"' and managecom='"+document.all('ManageCom').value+"' and agentcode='"+document.all('AgentCode').value+"'";
   		strResult = easyExecSql(wrapSql(tResourceName,'LCGrpPol2',[document.all('InputNo13').value,document.all('ManageCom').value,document.all('AgentCode').value]));
   		if(strResult==null)
   		{
	   		alert("δ��ѯ���ñ���!");
	   		return;
   	  }
   	}
   	
	     //�������շ��վ�¼�����У�飬֧���µ�֤���ͱ���531001��¼��
    if(!((document.all('InputNo14').value==null)||(document.all('InputNo14').value=="")))
    {
    	if(!(verifyTempfeeNoNew("531001",document.all('InputNo14').value,document.all('AgentCode').value)))
    	{
	     if(!(verifyTempfeeNoNew("3209",document.all('InputNo14').value,document.all('AgentCode').value)))
	    	{
	    		return false; 
	    	}
	        fmSave.all('CERTIFY_XQTempFee').value ="3209";
	    }
    	
    	else 
    	{
    			fmSave.all('CERTIFY_XQTempFee').value ="531001";
    	}
    }
	  
   	initTempGridYS();
    for (k=0; k<strResult.length; k++) 
    {
	      TempGrid.addOne("TempGrid");	      
	      TempGrid.setRowColData(k,1,strResult[k][0]);
	      TempGrid.setRowColData(k,2,strResult[k][1]);
	      TempGrid.setRowColData(k,4,strResult[k][2]);
	      TempGrid.setRowColData(k,5,strResult[k][3]);
    	}
	  document.all('TempFeeNo').value=document.all('InputNo14').value;
    typeFlag=true;
    divTempFeeClassInput.style.display="";
 }
     
  
   if(typeFlag==true) 
     confirmFlag=true;
   else
     confirmFlag=false;
}


//���һ�ʼ�¼

function addRecord()
{
	//alert(TempClassGrid.getRowColData(i,6));
   if(document.all('TempFeeType').value=="1" && (TempGrid.getRowColData(0,1)=="141814" || TempGrid.getRowColData(0,1)=="141815")){
  	alert("������Ϊ����ҵ�����֣����ڿ��������շ���¼��");
 		return false;
   }

   if(confirmFlag==false){alert("�뵥��ȷ��");return;}//���û�е���ȷ������ʾ

   if(!checkPubValue())  return;

   if(!checkGridValue()) return;

   if(!checkTempRecord()) return;

   if(!checkTempClassRecord()) return;
   

  //�ж��ݽ�����Ϣ�еĽ��ѽ���Ƿ���ݽ��ѷ�����Ϣ�еĽ��ѽ�����
 //  tempClassFee=tempClassFee/1000000000;
 //  tempFee=tempFee/1000000000;   
  var sqlClasscheck = wrapSql(tResourceName,"querysqldes1",[tempClassFee]);  
  var arrResultClass = easyExecSql(sqlClasscheck);  
  
  var sqlcheck = wrapSql(tResourceName,"querysqldes1",[tempFee]);
  var arrResult = easyExecSql(sqlcheck);
  
  var returnFlag=true;
  //alert(arrResult.length);
  if(arrResultClass!=null && arrResult!=null && arrResultClass.length==arrResult.length)
  {
  	for(i=0;i<arrResult.length;i++)
  	{
  		if(arrResult[i][0]==arrResultClass[i][0] && pointTwo(arrResult[i][1])==pointTwo(arrResultClass[i][1]) && arrResult[i][1]!='')
  		{
  			if(arrResult[i][0]!='0')
  			{
  			addAction = addAction+1;
		      //sumTempFee = sumTempFee+arrResult[i][1];
		      document.all('TempFeeCount').value = addAction;
		      //document.all('SumTempFee').value = pointTwo(sumTempFee);
		      SumTempGrid.addOne("SumTempGrid");
     		  SumTempGrid.setRowColData(SumTempGrid.mulLineCount-1,1,arrResult[i][0]);
     		  SumTempGrid.setRowColData(SumTempGrid.mulLineCount-1,2,arrResult[i][1]);
		      tempClassFee="";
		      tempFee="";
		      
		      returnFlag=true;
		    }
  		}
  		else
  		{
  			returnFlag=false;
  			break;
  		}
  	}  	
  }
  else
  	returnFlag=false;
  	
  if(returnFlag==false)
  {
  	tempClassFee="";
	tempFee="";
	SumTempGrid.clearData();
	 alert("���Ȼ�ȱ�����ݣ�");		
	 return ;
  }
  
   /*
   if(pointTwo(tempClassFee)==pointTwo(tempFee) && tempFee!='')   
   {
      addAction = addAction+1;
      sumTempFee = sumTempFee+tempFee;
      document.all('TempFeeCount').value = addAction;
      document.all('SumTempFee').value = pointTwo(sumTempFee);
      tempClassFee=0.0;
      tempFee=0.0;

   }
   else 
   {
      tempClassFee=0.0;
      tempFee=0.0;
      alert("���Ȼ�ȱ�����ݣ�");		
      return ;
   }*/
  
  divTempFeeSave.style.display="";      
  var i = 0;	
  var TempRecordCount = 0;
  var TempClassRecordCount = 0;
  var EnterAccDate=""; //��������
  var EnterAccDateFlag=1; //�Ƿ�¼�뵽�����ڵı��(����������ж��������Ҷ���д�˵������ڣ���ô��Ч)
  TempRecordCount = TempGrid.mulLineCount; //TempGrid������ 
  TempClassRecordCount = TempClassGrid.mulLineCount; //ҳ���ϼ�¼������ 
  //alert("��jiaofei���в������==="+TempRecordCount);


 //�ݽ��ѷ�����Ϣ���������ύ���ݵ�Grid 
 for(i=0;i<TempClassRecordCount;i++)
 {
    
    if(TempClassGrid.getRowColData(i,1)!='')  //������ѷ�ʽΪ�գ�������
    {
     if(i==0) 
       EnterAccDate=TempClassGrid.getRowColData(i,6);//���������ڸ���һ�еĵ�������
     
     TempClassToGrid.addOne("TempClassToGrid");
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,1,document.all('TempFeeNo').value.trim());
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,2,TempClassGrid.getRowColData(i,1));
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,3,document.all('PayDate').value.trim());
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,4,TempClassGrid.getRowColData(i,3));
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,5,TempClassGrid.getRowColData(i,4));
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,6,TempClassGrid.getRowColData(i,7));
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,7,document.all('PolicyCom').value.trim());
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,8,TempClassGrid.getRowColData(i,5)); 
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,9,TempClassGrid.getRowColData(i,6));     
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,10,TempClassGrid.getRowColData(i,8));     
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,11,TempClassGrid.getRowColData(i,9));     
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,12,TempClassGrid.getRowColData(i,10));     
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,13,TempClassGrid.getRowColData(i,11));     
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,14,TempClassGrid.getRowColData(i,12));     
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,15,TempClassGrid.getRowColData(i,13));
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,19,TempClassGrid.getRowColData(i,17));
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,20,TempClassGrid.getRowColData(i,18));
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,21,TempClassGrid.getRowColData(i,19));
              
     if(trim(TempClassGrid.getRowColData(i,7))=="") //����н�����ĵ�������Ϊ�գ���ô��־��0
        EnterAccDateFlag=0;
     
     if(compareDate(EnterAccDate,TempClassGrid.getRowColData(i,7))==2)
       EnterAccDate=TempClassGrid.getRowColData(i,7);

    }

  }
   fmSave.all('CertifyFlagHidden').value=document.all('CertifyFlag').value;
   fmSave.all('ClaimFeeTypeHidden').value=document.all('ClaimFeeType').value;
   
   
  if(EnterAccDateFlag==0)
    EnterAccDate="";
   

     
  //�ݽ�����Ϣ���������ύ���ݵ�Grid
  for(i=0;i<TempRecordCount;i++)
  {
    if(TempGrid.getRowColData(i,1)!='')  //������ֱ���Ϊ�գ�������
     {
       TempToGrid.addOne("TempToGrid");//��ǰ�ļ��еĶ��󣬲��ü�ǰ׺
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,1,document.all('TempFeeNo').value.trim());
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,2,document.all('TempFeeType').value.trim());
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,3,document.all('PayDate').value.trim());          
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,4,TempGrid.getRowColData(i,3));
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,5,TempGrid.getRowColData(i,4));
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,6,EnterAccDate);          
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,7,document.all('PolicyCom').value.trim());       
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,8,TempGrid.getRowColData(i,1));          
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,9,document.all('AgentGroup').value.trim());
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,10,document.all('AgentCode').value.trim());               
       if (document.all('TempFeeType').value=="1") {
       	 TempToGrid.setRowColData(TempToGrid.mulLineCount-1,11,TempGrid.getRowColData(0,5));                  
     		 TempToGrid.setRowColData(TempToGrid.mulLineCount-1,13,TempGrid.getRowColData(i,6));     
     		 TempToGrid.setRowColData(TempToGrid.mulLineCount-1,14,TempGrid.getRowColData(i,7));   
       	 //��Ʒ��ϵ�У���MS������
  	     	        	 
       }
       else if (document.all('TempFeeType').value=="2"){
         TempToGrid.setRowColData(TempToGrid.mulLineCount-1,11,TempGrid.getRowColData(i,5)); 
         var ReturnNoType=getCodeType(TempGrid.getRowColData(i,5));
         //OtherNoType�Ĵ�����ں�̨����
         	TempToGrid.setRowColData(TempToGrid.mulLineCount-1,15, document.all('XQCardNo').value.trim());	//���ڴ��ս���¼�����շ��վݺ���
         } 
         //Ԥ�ձ���
        else if (document.all('TempFeeType').value=="3")
       	{
       		TempToGrid.setRowColData(TempToGrid.mulLineCount-1,12,"4");     
       		TempToGrid.setRowColData(TempToGrid.mulLineCount-1,11,document.all('InputNo13').value.trim()); 
       	}     
       else if (document.all('TempFeeType').value=="5")
       	{ 
       	 	 TempToGrid.setRowColData(TempToGrid.mulLineCount-1,11,TempGrid.getRowColData(i,5));
       	 	 TempToGrid.setRowColData(TempToGrid.mulLineCount-1,16,TempGrid.getRowColData(i,7));
       	 	 TempToGrid.setRowColData(TempToGrid.mulLineCount-1,17,TempGrid.getRowColData(i,8));
       	 	 TempToGrid.setRowColData(TempToGrid.mulLineCount-1,12,"5");
       	}
       else if (document.all('TempFeeType').value=="6")
       	{
       		TempToGrid.setRowColData(TempToGrid.mulLineCount-1,12,"5");
       		TempToGrid.setRowColData(TempToGrid.mulLineCount-1,11,TempGrid.getRowColData(i,5));
       	}
    //   else if (document.all('TempFeeType').value=="9")
    //   	{
    //   		TempToGrid.setRowColData(TempToGrid.mulLineCount-1,11,"04");     
    //   		TempToGrid.setRowColData(TempToGrid.mulLineCount-1,10,TempGrid.getRowColData(i,4));                          		      	               
    //   	}
       else{
       	 TempToGrid.setRowColData(TempToGrid.mulLineCount-1,11,TempGrid.getRowColData(i,5));
       }
     }
  }
  fm.PayMode.value = '';
  fm.PayModeName.value = '';
  initTempGrid();
  initTempClassGrid();
  divTempFeeClassInput.style.display="none";
  fm.butAdd.disabled =false;
  initDivPayMode();//��ʼ��divPayMode
  initInput();  //�ύ�����֮ǰ¼�������
  confirmFlag=false; //��λ�������µ���ȷ��
}

function initInput(){
   try{
   		fm.Currency1.value ='';
       fm.PayFee1.value ='';
       fm.Currency2.value ='';
       fm.PayFee2.value ='';
       fm.ChequeNo2.value ='';
       fm.ChequeDate2.value='';
       fm.BankCode2.value ='';
       fm.BankCode2Name.value ='';
       fm.BankAccNo2.value ='';
       fm.AccName2.value ='';
       fm.Currency3.value ='';
       fm.PayFee3.value ='';
       fm.ChequeNo3.value ='';
       fm.ChequeDate3.value ='';
       fm.BankCode3.value ='';
       fm.BankCode3Name.value ='';
       fm.BankAccNo3.value ='';
       fm.AccName3.value ='';
       fm.Currency4.value ='';
       fm.PayFee4.value ='';
       fm.BankCode4.value ='';
       fm.BankCode4Name.value ='';
       fm.BankAccNo4.value ='';
       fm.AccName4.value ='';
       fm.IDType.value ='';
       fm.IDNo.value ='';
       fm.ChequeNo5.value ='';
       fm.OtherNo5.value ='';
       fm.Currency5.value ='';
       fm.PayFee5.value ='';
       fm.Drawer5.value ='';
       fm.Currency6.value ='';
       fm.PayFee6.value ='';
       fm.BankCode6.value ='';
       fm.BankCode6Name.value ='';
       fm.BankAccNo6.value ='';
       fm.AccName6.value ='';
       fm.IDTypeName.value ='';
       fm.CashType.value='';
       fm.CashTypeName.value='';
     }catch(ex){}
}


function checkPubValue()
{

   //���Խ������ڵĿ������䵽�����շ�������
   if(document.all('PayDate').value==null || trim(document.all('PayDate').value)=="")
    {
    	alert("�������ڲ���Ϊ��!");
    	return false;
    }

    if((isDate(document.all('PayDate').value)==false) ||document.all('PayDate').value.length!=10)
    {
    	alert("�������ڸ�ʽ����ȷ�������'YYYY-MM-DD'��ʽ!");
    	return false;
    }
    return true;
}
//���MulLine����Ϣ������ݸ�ʽ�Ƿ�ϸ�
function checkGridValue()
{

  var arrCardMoney = new Array();
  var allMoney = 0;
  if(TempGrid.checkValue("TempGrid")&&TempClassGrid.checkValue("TempClassGrid"))
  { 
    for(var n=0;n<TempGrid.mulLineCount;n++)
     {//MS֧�ֶ����գ������շ�֧�ֶ������շ�     
       for(var m=n+1;m<TempGrid.mulLineCount;m++)
       {
         if(TempGrid.getRowColData(n,1)==TempGrid.getRowColData(m,1) && TempGrid.getRowColData(n,3)==TempGrid.getRowColData(m,3))
          {
             alert("����¼���ظ������ֱ���");	
             return false;
          }
       }
       //�����У�� 2009-04-29 15:57 ȡ���Զ����У��
     /*  if (document.all('TempFeeType').value=="1" && document.all('CertifyFlag').value=="3") {  
         //У������
         var isCardRisk = false;
         for (var k=0; k<arrCardRisk.length; k++) {
          if (TempGrid.getRowColData(n,1) == arrCardRisk[k][0]) {
            isCardRisk = true;
          }
         }        
         if (!isCardRisk) {
          alert("������" + TempGrid.getRowColData(n,1) + "���Ǹ��൥֤���������֣�");
          return false;
         }
         
         //�������ֺͽ��
         arrCardMoney[n] = new Array(TempGrid.getRowColData(n,1), TempGrid.getRowColData(n,3));
         allMoney = allMoney + parseInt(TempGrid.getRowColData(n,3));     
       }*/   
     }  
     
   /*  if (document.all('TempFeeType').value=="1"&& document.all('CertifyFlag').value=="3") {     
       //У�������
       if (arrCardRisk[0][3] != "1") {
         for (var i=0; i<arrCardRisk.length; i++) {

           for (var j=0; j<arrCardMoney.length; j++) {
             //�ҵ���ͬ����
             if (arrCardRisk[i][0] == arrCardMoney[j][0]) {
               //У�鱶��
               if (arrCardRisk[0][3]=="2" && (arrCardMoney[j][1]%arrCardRisk[i][2] != 0)) {
                 alert("�����֣�" + arrCardMoney[j][0] + "���Ľ��ѽ��������趨��" + arrCardRisk[i][2] + "���ı�����");
                 return false;
               }
               if (arrCardRisk[0][3]=="3" && (arrCardMoney[j][1]/allMoney != arrCardRisk[i][4])) {
                 alert("�����֣�" + arrCardMoney[j][0] + "���Ľ��ѽ�������" + arrCardRisk[i][4] + "������");
                 return false;
               }
             }
           }
         }
       } 
     } */    
     return true;      	
   }
  return false; 
}

//���һ�ʼ�¼ǰ�������ӵ��ݽ�����Ϣ�����Ƿ��Ѿ������ڱ������ݵ�Grid�У������㽻�ѽ���ۼ�
function checkTempRecord()
{
  //alert("check3");
  var TempRecordCount = 0;
  var TempClassGridCount = 0;
  tempFee="";
  var i = 0;
  TempRecordCount=TempGrid.mulLineCount; //TempGrid������ 
  TempToGridCount=TempToGrid.mulLineCount;//TempToGrid������
    
	for(i=0;i<TempRecordCount;i++)
	{
		  if(TempGrid.getRowColData(i,4)<=0)
    	{
    		alert("¼��Ľ��ѽ��Ӧ��Ϊ����0�����ݣ�������¼��");
    		return false;
    	} 
	    if (document.all('TempFeeType').value=="1")
	    {
		    if(TempGrid.getRowColData(i,6)==''||TempGrid.getRowColData(i,6)==null)
		     {
	          alert("��¼�뽻�Ѽ��");
	          return false;	
		     }
		    	
		    if((TempGrid.getRowColData(i,7)==''||TempGrid.getRowColData(i,7)==null))
		     {
		        alert("��¼�뽻���ڼ�");
		        return false;	
		     }
		     	    //zy 2009-04-17 16:43 ���Ӷ�¼�뽻���ڼ�ͽ��Ѽ��У��
			  if(TempGrid.getRowColData(i,6)!='' && (!isInteger(TempGrid.getRowColData(i,6))))
				{
				        alert("���Ѽ��һ������¼�����֣�");
				        return false;	
				}
			  if(TempGrid.getRowColData(i,7)!='' && (!isInteger(TempGrid.getRowColData(i,7))))
				{
				        alert("�����ڼ�һ������¼�����֣�");
				        return false;	
				}	    
	    }

	}

  for(i=0;i<TempRecordCount;i++)
  {
    if(TempGrid.getRowColData(i,1)!='')  //������ֱ���Ϊ�գ�������
     {
     	 for(var n=0;n<TempToGridCount;n++)
       {         //����Ƿ�����ͬ���ݽ��Ѻš�0��ʾ��̨�Զ�����
	        if(TempToGrid.getRowColData(n,1)==document.all('TempFeeNo').value)
	        {
	          alert("�����ظ�����:�ݽ��Ѻ���ͬ");
	          return false;
	        }      
       }   	     	      
       //tempFee = tempFee+1000000000*parseFloat(TempGrid.getRowColData(i,3));//���ѽ���ۼ�   
       //tempFee = Arithmetic(tempFee,'+',TempGrid.getRowColData(i,4),2);
       tempFee = tempFee + "select '"+TempGrid.getRowColData(i,3)+"' code,'"+TempGrid.getRowColData(i,4)+"' money from dual union all ";
     }   	
  }
     return true;  	
}

//���һ�ʼ�¼ǰ,������ӵ��ݽ��ѷ�����Ϣ���ݽ��ѽ���ۼ�
function checkTempClassRecord()
{

  var TempClassRecordCount = 0;
  tempClassFee="";
  var i = 0;
  TempClassRecordCount=TempClassGrid.mulLineCount; //ҳ���ϼ�¼������ 
  var paramTemp=''; 
  for(i=0;i<TempClassRecordCount;i++)
  {
    if(TempClassGrid.getRowColData(i,1)!='')  //������ѷ�ʽΪ�գ�������
    {
    	if(TempClassGrid.getRowColData(i,4)<=0)
    	{
    		alert("¼��Ľ��ѽ��Ӧ��Ϊ����0�����ݣ�������¼��");
    		return false;
    	}
      //tempClassFee = tempClassFee+1000000000*parseFloat(TempClassGrid.getRowColData(i,3));//���ѽ���ۼ�
      //tempClassFee = Arithmetic(tempClassFee,'+',TempClassGrid.getRowColData(i,4),2);
      tempClassFee = tempClassFee + "select '"+TempClassGrid.getRowColData(i,3)+"' code,'"+TempClassGrid.getRowColData(i,4)+"' money from dual union all ";
      if(trim(TempClassGrid.getRowColData(i,7))!='')//����������ڲ�Ϊ�գ���ô�ж��Ƿ���ڵ��ڵ�ǰ����
      {
	      	if(isDate(trim(TempClassGrid.getRowColData(i,7))))//������ڸ�ʽ��ȷ
	      	{
	      		if(compareDate(trim(TempClassGrid.getRowColData(i,7)),getCurrentDate())==1)//������ڵ�ǰ����
	      		{
	      		  alert("�������ڲ��ܳ������죡");
	      		  return false;
	      		}
	      	}
	      	else
	      	{
	      	  alert("�������ڸ�ʽ���ԣ�");
	      	  return false;
	      	}
	     }
	
	      if(TempClassGrid.getRowColData(i,1)=='5')  //������ѷ�ʽΪ5-�ڲ�ת�ˣ���Ʊ�ݺ��벻��Ϊ��
	      {
	        if(trim(TempClassGrid.getRowColData(i,5))==''||trim(TempClassGrid.getRowColData(i,7))=='')
	          {
		           alert("�ڲ�ת��ʱ��Ʊ�ݺ���͵������ڲ���Ϊ��");
		           return false;
	          }
	      }
	
	      if(TempClassGrid.getRowColData(i,1)=='4')  //������ѷ�ʽΪ4-����ת�ˣ�������д ��������,�����˺�,�������ҵ������ڱ���Ϊ��
	      {
	        if(trim(TempClassGrid.getRowColData(i,8))==''||trim(TempClassGrid.getRowColData(i,9))==''||trim(TempClassGrid.getRowColData(i,10))=='')
	          {
		           alert("����ת��ʱ��������д ��������,�����˺�,����");
		           return false;
	          }
	          
	        if (trim(TempClassGrid.getRowColData(i,8))=="0101") {
	          if (trim(TempClassGrid.getRowColData(i,9)).length!=19 || !isInteger(trim(TempClassGrid.getRowColData(i,9)))) {
	            alert("�������е��˺ű�����19λ�����֣����һ���Ǻţ�*����Ҫ��");
	            return false;
	            }
           }
	        TempClassGrid.setRowColData(i,7,'');//  �����������ÿ�
	        
	        if (document.all('TempFeeType').value=="1" && document.all('CertifyFlag').value=="1") 
	        {
              alert("�����ݽ������ͣ�" + document.all('TempFeeTypeName').value + "��������ʹ������ת�˵Ľ��ѷ�ʽ����ѡ�����л���Э����ĵ�֤���ͣ�");
             return false;
	        }
	      }
	//���Ϊ�ֽ�֧Ʊ�򲻼ӿ���
	      if(TempClassGrid.getRowColData(i,1)=='3')  //������ѷ�ʽΪ2||3-֧Ʊ�࣬��Ʊ�ݺ�������в���Ϊ��
	      {
	        if(trim(TempClassGrid.getRowColData(i,5))==''||trim(TempClassGrid.getRowColData(i,8))=='')
	          {
	           alert("���ѷ�ʽΪ֧Ʊʱ��Ʊ�ݺ�������в���Ϊ��");
	           return false;
	          }
	      }
	      
	      if(TempClassGrid.getRowColData(i,1)=='6')
	      {
	      		if(trim(TempClassGrid.getRowColData(i,8))=='')
	          {
	           alert("���ѷ�ʽΪ��������ʱ�����б��벻��Ϊ��");
	           return false;
	          }
	       }
	      
	       
	     if (document.all('TempFeeType').value=="3"&&TempClassGrid.getRowColData(i,1)!='1') 
	     {   
     		  alert("�ݽ�������ΪԤ�ս���ʱ,���ѷ�ʽֻ��Ϊ�ֽ�!");  
     			return false;   
     	 } 
     	
     	if (document.all('TempFeeType').value=="2" || document.all('TempFeeType').value=="4") 
	    {
	     	 if(TempClassGrid.getRowColData(i,1)=='4')
	     	 {   
	     			alert("�����ݽ������Ͳ�����ʹ������ת�˵Ľ��ѷ�ʽ,���ʵ��");  
	     			return false;   
     		 }
     	}
    }
  }
     return true;
}

//���ȫ���ύ����ȫ�����ʱ--������ݲ���ʼ��
function clearFormData()
{  
  initGlobalData();
  initTempToGrid();
  initTempClassToGrid();
  initTempGrid();
  initTempClassGrid();
  initSumTempGrid();
  initFee();
  initDivPayMode();
  document.all('TempFeeNo').valur="";  
  document.all('TempFeeCount').value=0;
  //document.all('SumTempFee').value=0.0 ;
  divTempFeeSave.style.display="none";
  divTempFeeInput.style.display="";
  TempGrid.lock();
}


//��ʼ��ȫ�ֱ���
function initGlobalData()
{
//�����Ӷ���ִ�еĴ���
 addAction = 0;
//�ݽ����ܽ��
 //sumTempFee = 0.0;
//�ݽ�����Ϣ�н��ѽ���ۼ�
 tempFee = "";
//�ݽ��ѷ�����Ϣ�н��ѽ���ۼ�
 tempClassFee = "";
 confirmFlag=false; //��λ�������µ���ȷ�� 
}


/*********************************************************************
 *  ѡ���ݽ������ͺ�Ķ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterCodeSelect(cCodeName, Field) 
{
	if(cCodeName == "FINAbank" ){
		if(divPayMode2.style.display == ""){
				checkBank(document.all('BankCode2'),document.all('BankAccNo2'));
		}
		if(divPayMode3.style.display == ""){
				checkBank(document.all('BankCode3'),document.all('BankAccNo3'));
		}
		if(divPayMode4.style.display == ""){
				checkBank(document.all('BankCode4'),document.all('BankAccNo4'));
		}
		if(divPayMode6.style.display == ""){
				checkBank(document.all('BankCode6'),document.all('BankAccNo6'));
		}
  }
	if(cCodeName == "TempFeeType") 
	{  
	  showTempFeeTypeInput(Field.value);
	}
	if(cCodeName == "chargepaymode")
	{
		showTempClassInput(Field.value);
		PayModePrem();
	}

	if(cCodeName=="certifyflag"){
  	afterSelectCode(Field.value);
  	}

	if(cCodeName=="ClaimFeeType"){
  	afterSelectCode(Field.value);
  	}
  	
  if(fm.CertifyFlag9.value=="2")
  {
  	 fm.InputNo19.value = fm.InputNo99.value;
  	 fm.InputNo19.disabled = true;
  }
	else
  {
  	 fm.InputNo19.value = "";
  	 fm.InputNo19.disabled = false;
  }
  if(cCodeName =="chargepaymode"){
    //��ʼ��
    initInput();
  }
  //���ñ��ֽ�����ʾ��ʽ
    if(cCodeName=="currency"){
    	for(i=0;i<=6;i++)
    	{
    		var scurrency=document.all('Currency'+i).value;
    		if(scurrency!=null && scurrency!="")
    		{
    			document.all('PayFee'+i).moneytype=scurrency;
    		}
    	}    	
    }

}

/*********************************************************************
 *  ����ѡ��ͬ�Ľ��ѷ�ʽ����ʼ��ҳ�� gaoht
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showTempClassInput(type)
{
	for(i=0;i<=6;i++)
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


/*********************************************************************
 *  ����ѡ��ͬ���ݽ�������  ����ʼ��ҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showTempFeeTypeInput(type) 
{         
    document.all('divCertifyFlag1').style.display='none';
    document.all('divCertifyFlag2').style.display='none';
    document.all('divCertifyFlag3').style.display='none';
    initFee();
    for (i=0; i<9; i++) {
    if ((i+1) == type) {
      document.all("TempFeeType" + (i+1)).style.display = '';
    }
    else {
      document.all("TempFeeType" + (i+1)).style.display = 'none';
    }
  }
}


function queryLJSPay()
{
   if(document.all('InputNo4').value!="")
   {
    	window.open("./FinFeeGetQueryLJSPayMain.jsp?Getnoticeno="+document.all('InputNo4').value,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
   }
   else
   {
      window.open("./FinFeeGetQueryLJSPayMain.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
   }	
}

function queryLJSPayEdor()
{
   if(document.all('InputNo7').value="")
   {
   alert("��ȫ����Ų���Ϊ��");
   return false;
   }
   else
   {
    window.open("./FinFeeGetQueryLJSPayEdorMain.jsp?PayDate=" + document.all('PayDate').value,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");			
   }	
}
function queryLJSPayClaim ()
{
   if(document.all('InputNo11').value!="")
   {
    	  //var strSql = "select otherno from ljspay where getnoticeno='"+fm.InputNo11.value+"'";
        var strSql =wrapSql(tResourceName,'LJSPay1',[fm.InputNo11.value,'']);
        var arrResult = easyExecSql(strSql);     	
        fm.InputNo12.value=arrResult;
   }
   else if (document.all('InputNo12').value!="")
   {
	     //var strSql = "select getnoticeno from ljspay where otherno='"+fm.InputNo12.value+"'";
	     var strSql =wrapSql(tResourceName,'LJSPay1',['',fm.InputNo12.value]);
       var arrResult = easyExecSql(strSql);     	
       fm.InputNo11.value=arrResult;   	
   }
   else
   {
    window.open("./FinFeeGetQueryLJSPayClaim.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");			
   }	
}



//У��¼��ĵ�֤�Ƿ���ҪУ���Ƿ񷢷Ÿ�ҵ��Ա�������Ҫ����true,���򷵻�false
function needVerifyCertifyCode(CertifyCode)
{
  try {
       //var tSql = "select Sysvarvalue from LDSysVar where Sysvar='NotVerifyCertifyCode'";          
       var tSql=wrapSql(tResourceName,'LDSysVar',[""]);
       var tResult = easyExecSql(tSql, 1, 1, 1);       
       var strRiskcode = tResult[0][0];
       var strValue=strRiskcode.split("/");
       var i=0;
	   while(i<strValue.length)
	   {
	   	if(CertifyCode==strValue[i])
	   	{
           return false;
	   	}
	   	i++;
	   }   	   
  	 }
  	catch(e)
  	 {}
  	return true;
}

function queryAgent()
{
	if(document.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��"); 
		 return;
	}
    if(document.all('AgentCode').value == "")	{  
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  }
	if(document.all('AgentCode').value != "")	 {
	//var cAgentCode = fm.AgentCode.value;  //��������	
	//var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + trim(fm.AgentCode.value) +"'";
    var strSql = wrapSql(tResourceName,'LAAgent2',[fm.AgentCode.value]);
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
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
  	fm.AgentName.value = arrResult[0][3];
  }
  //var aSql = "select agentgroup from LAAgent where agentcode='"+fm.AgentCode.value+"'";  
  var aSql=wrapSql(tResourceName,'LAAgent3',[fm.AgentCode.value]);
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

//У��¼������˺ͱ����������Ƿ�һ��
function verifyAgent(PolNo)
{
	var strSql="";

	   //strSql = "select agentcode from lccont where contno='" + PolNo +"'";
		 strSql=wrapSql(tResourceName,'LCCont1',[PolNo]);	
			
    var arrResult = easyExecSql(strSql);
    if (arrResult != null) 
    {
      //alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"]");
      if(arrResult[0][0]!=trim(document.all('AgentCode').value))
      {
      	alert("¼������˺ͱ��������˲�һ��!");
      	return false;
      }      
    }
    else
    {
    	//strSql = "select agentcode from LCGrpCont where GrpContNo='" + PolNo +"'";
    	strSql=wrapSql(tResourceName,'LCGrpCont2',[PolNo]);
    	arrResult = easyExecSql(strSql);
      if (arrResult != null) 
       {
      //alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"]");
         if(arrResult[0][0]!=trim(document.all('AgentCode').value))
           {
      	      alert("¼������˺ͱ��������˲�һ��!");
      	      return false;
           }      
       }
      else
       {
          alert("δ�鵽�ñ�����Ϣ��");
          return false;	
       }
    }  
	
	return true;
}

function verifyAgentCode(PrtNo)//�µ�¼��ҵ��Ա�Ƿ�һ��
{
	//var strSql="select agentcode from lccont where prtno='"+trim(PrtNo)+"'";
  var strSql=wrapSql(tResourceName,'LCCont2',[trim(PrtNo)]);
  var arrResult = easyExecSql(strSql);
  if (arrResult != null) 
   {
      if(arrResult[0][0] != document.all('AgentCode').value)
      {
      	alert("¼��ҵ��Ա�ͱ��������˲�һ��!");
      	return false;
      }      
   }	
	return true;
}



/*********************************************************************
 *  ����һ��mul
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function addMul()
{
  
	var mulLineCount = TempClassGrid.mulLineCount;
	var PayMode = fm.PayMode.value;
	var Currency =  document.all('Currency' + PayMode).value;
	var payFlag = "";
	var curFlag = "";

 // if(PayMode!='3')
 // {
	 for(i=0;i<mulLineCount;i++)
	  {//У���Ϊͬ�ֽ��ѷ�ʽ�¿��Դ��ڶ������
	  	payFlag = TempClassGrid.getRowColData(i,1);
	  	curFlag = TempClassGrid.getRowColData(i,3);	  	
	   	if(payFlag==PayMode && curFlag==Currency)
		   {
			   if(payFlag!='5')
			   {
			    alert("�Ѵ��ڸ��ֽ��ѷ�ʽ,�����ɾ�����޸ģ�");
			    fm.PayMode.focus();
			    return false;
	 	     }
	 	     else
	 	     {
	 	       if (TempClassGrid.getRowColData(i,5)==document.all('ChequeNo5').value)
	 	       {
			         alert("�˼�¼�Ѿ����");
			         return false;
	 	       }
	 	     } 
	 	   }
	}
  //}
//  if(PayMode=='5')
 // {
	//   if(document.all('ManageCom').value.substring(0, 4)!=document.all('PolicyCom').value.substring(0, 4))
//	     {
 //       alert("����Լ������ؽɷ�");
//        return false;
//	      }
//  }
  
 // if (document.all('TempFeeType').value=="1")
 // {
  	if(!CheckPayMode())
  	{
  	  return false;	
  	}	
//  }
	if(document.all('Currency'+PayMode).value=="")
	{
		alert("��¼�����!");
		return;
	}
	if(document.all('PayFee'+PayMode).value=="")
	{
		alert("��¼�뽻�ѽ��!");
		return;
	}
  
	TempClassGrid.addOne("TempClassGrid");

	TempClassGrid.setRowColData(mulLineCount, 1, fm.PayMode.value);

	TempClassGrid.setRowColData(mulLineCount,2,fm.PayModeName.value);
	if(PayMode==1)
	{
		TempClassGrid.setRowColData(mulLineCount,3,fm.Currency1.value);
		TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee1.value);
		var CashType ;
		if(fm.CashType.value=='2'){
			CashType = "P12";
		}else{
			CashType = "P11";
		}
		TempClassGrid.setRowColData(mulLineCount,19,CashType);
		TempClassGrid.setRowColData(mulLineCount,7,document.all('PayDate').value);
	}
	if(PayMode==2)
	{
	//	alert("��������======");
		if (verifyElement("��������|NOTNULL", document.all('BankCode2').value) != true) {
      return false;
    }
		if (verifyElement("Ʊ�ݺ���|NOTNULL", document.all('ChequeNo2').value) != true) {
      return false;
    }	
    	TempClassGrid.setRowColData(mulLineCount,3,fm.Currency2.value);	
		TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee2.value);
		TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeNo2.value);
		TempClassGrid.setRowColData(mulLineCount,6,fm.ChequeDate2.value);
		TempClassGrid.setRowColData(mulLineCount,8,fm.BankCode2.value);
		TempClassGrid.setRowColData(mulLineCount,9,fm.BankAccNo2.value);
		TempClassGrid.setRowColData(mulLineCount,10,fm.AccName2.value);
		TempClassGrid.setRowColData(mulLineCount,7,document.all('PayDate').value);
		//TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode2.value);
		//TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo2.value);
		//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName2.value);		
	}	
	if(PayMode==3)
	{
		if (verifyElement("Ʊ�ݺ�|NOTNULL", document.all('ChequeNo3').value) != true) {
      return false;
    }	
	//	if (verifyElement("֧Ʊ����|NOTNULL", document.all('ChequeDate3').value) != true) {
   //   return false;
   // }
		if (verifyElement("��������|NOTNULL", document.all('BankCode3').value) != true) {
      return false;
    }
	//	if (verifyElement("�������л���|NOTNULL", document.all('AccName3').value) != true) {
   //   return false;
   // } 
	//	if (verifyElement("�շ�����|NOTNULL", document.all('InBankCode3').value) != true) {
   //   return false;
  //  }
	//	if (verifyElement("�շ������˺�|NOTNULL", document.all('InBankAccNo3').value) != true) {
  //    return false;
    //}
  		TempClassGrid.setRowColData(mulLineCount,3,fm.Currency3.value);
		TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee3.value);
		TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeNo3.value);
		TempClassGrid.setRowColData(mulLineCount,6,fm.ChequeDate3.value);
		TempClassGrid.setRowColData(mulLineCount,7,'');
		TempClassGrid.setRowColData(mulLineCount,8,fm.BankCode3.value);
		TempClassGrid.setRowColData(mulLineCount,9,fm.BankAccNo3.value);
		TempClassGrid.setRowColData(mulLineCount,10,fm.AccName3.value);
	//	TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode3.value);
	//	TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo3.value);
		//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName3.value);		
	}	
	/*if(PayMode==4)
	{
		
		if (verifyElement("��������|NOTNULL", document.all('BankCode4').value) != true) {
      return false;
    }
		if (verifyElement("���������˺�|NOTNULL", document.all('BankAccNo4').value) != true) {
      return false;
    }
		if (verifyElement("�������л���|NOTNULL", document.all('AccName4').value) != true) {
      return false;
    } 
		if (verifyElement("�շ�����|NOTNULL", document.all('InBankCode4').value) != true) {
      return false;
    }    
		if (verifyElement("�շ������˺�|NOTNULL", document.all('InBankAccNo4').value) != true) {
      return false;
    }		
		
		
		TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee4.value);
		//TempClassGrid.setRowColData(mulLineCount,4,fm.ChequeNo4.value);
		//TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeDate4.value);
		TempClassGrid.setRowColData(mulLineCount,6,'');
		TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode4.value);
		TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo4.value);
		TempClassGrid.setRowColData(mulLineCount,9,fm.AccName4.value);
		TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode4.value);
		TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo4.value);
		//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName4.value);		
	}	*/
	if(PayMode==5)
	{
		TempClassGrid.setRowColData(mulLineCount,3,fm.Currency5.value);
		TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee5.value);
		TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeNo5.value);
		TempClassGrid.setRowColData(mulLineCount,7,document.all('PayDate').value);
		//TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeDate5.value);
		//TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode5.value);
		//TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo5.value);
		//TempClassGrid.setRowColData(mulLineCount,9,fm.AccName5.value);
		//TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode5.value);
		//TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo5.value);
		//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName5.value);		
	}	
/*	if(PayMode==6)
	{
		if (verifyElement("�շ�����|NOTNULL", document.all('InBankCode6').value) != true) {
      return false;
    }    
		if (verifyElement("�շ������˺�|NOTNULL", document.all('InBankAccNo6').value) != true) {
      return false;
    }	
		
		TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee6.value);
		//TempClassGrid.setRowColData(mulLineCount,4,fm.ChequeNo6.value);
		//TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeDate6.value);
		//TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode6.value);
		//TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo6.value);
		//TempClassGrid.setRowColData(mulLineCount,9,fm.AccName6.value);
    TempClassGrid.setRowColData(mulLineCount,6,document.all('PayDate').value);
		TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode6.value);
		TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo6.value);
		//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName6.value);		
	}	*/
	if(PayMode==4)
	{

		if (verifyElement("��������|NOTNULL", document.all('BankCode4').value) != true) {
      return false;
    }
		if (verifyElement("�����˺�|NOTNULL", document.all('BankAccNo4').value) != true) {
      return false;
    }
		if (verifyElement("���л���|NOTNULL", document.all('AccName4').value) != true) {
      return false;
    }

		TempClassGrid.setRowColData(mulLineCount,3,fm.Currency4.value);
		TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee4.value);
		TempClassGrid.setRowColData(mulLineCount,7,'');
		TempClassGrid.setRowColData(mulLineCount,8,fm.BankCode4.value);
		TempClassGrid.setRowColData(mulLineCount,9,fm.BankAccNo4.value);
		TempClassGrid.setRowColData(mulLineCount,10,fm.AccName4.value);
		TempClassGrid.setRowColData(mulLineCount,17,fm.IDType.value);
		TempClassGrid.setRowColData(mulLineCount,18,fm.IDNo.value);
		//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName7.value);		
	}	
	if(PayMode==8)
	{
		TempClassGrid.setRowColData(mulLineCount,3,fm.Currency8.value);
		TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee8.value);
		TempClassGrid.setRowColData(mulLineCount,7,document.all('PayDate').value);
	}	
  if(PayMode==6)
  { //alert("BBBBBBBBBBBBBBBB");
   if (verifyElement("��������|NOTNULL", document.all('BankCode6').value) != true) {
      return false;
    }
    
    TempClassGrid.setRowColData(mulLineCount,3,fm.Currency6.value);
    TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee6.value);
		TempClassGrid.setRowColData(mulLineCount,7,'');
		//TempClassGrid.setRowColData(mulLineCount,6,'');
		TempClassGrid.setRowColData(mulLineCount,8,fm.BankCode6.value);
		TempClassGrid.setRowColData(mulLineCount,9,fm.BankAccNo6.value);
		TempClassGrid.setRowColData(mulLineCount,10,fm.AccName6.value);
		//TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode9.value);
		//TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo9.value);
		//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName9.value);
  }
	if(PayMode=='0')
	{
		TempClassGrid.setRowColData(mulLineCount,3,fm.Currency0.value);
		TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee0.value);
		TempClassGrid.setRowColData(mulLineCount,7,'');
	}  
//  document.all('spanTempClassGrid'+mulLineCount).all('TempClassGridSel').checked=true;
  if(PayMode=='5')
  {
  	document.all('ChequeNo5').value="";
  	document.all('OtherNo5').value="";
  	document.all('Currency5').value="";
  	document.all('PayFee5').value="";
  	document.all('Drawer5').value="";
  }
}

/*********************************************************************
 *  �޸�ѡ�е�mul
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function ModMul()
{
	//var MulData=TempClassGrid.getRowColData(0,3);
	var rowNum=TempClassGrid.getSelNo();
	//alert(rowNum);
	if (rowNum==0)
	{
	alert("δ����Ҫ�޸ĵ����ݣ����������ӡ���");
	return false;	
	}
	var mulLineCount = TempClassGrid.getSelNo()-1;
	//var mulLineCount = TempClassGrid.getSelNo();
	if(mulLineCount>=0)
	{
		
		TempClassGrid.setRowColData(mulLineCount,1,fm.PayMode.value);
		TempClassGrid.setRowColData(mulLineCount,2,fm.PayModeName.value);
		var PayMode = TempClassGrid.getRowColData(mulLineCount,1);
		if(PayMode==1)
		{
			TempClassGrid.setRowColData(mulLineCount,3,fm.Currency1.value);
			TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee1.value);
			TempClassGrid.setRowColData(mulLineCount,7,document.all('PayDate').value);
			TempClassGrid.setRowColData(mulLineCount,5,'');
			TempClassGrid.setRowColData(mulLineCount,6,'');
			TempClassGrid.setRowColData(mulLineCount,9,'');
			TempClassGrid.setRowColData(mulLineCount,10,'');
			TempClassGrid.setRowColData(mulLineCount,8,'');
			TempClassGrid.setRowColData(mulLineCount,17,'');
			TempClassGrid.setRowColData(mulLineCount,18,'');
			var CashType ;
			if(fm.CashType.value=='2'){
				CashType = "P12";
			}else{
				CashType = "P11";
			}
			TempClassGrid.setRowColData(mulLineCount,19,CashType);
		}
		if(PayMode==2)
		{
		//	alert("��������======");
			if (verifyElement("��������|NOTNULL", document.all('BankCode2').value) != true) {
	      return false;
	    }
			if (verifyElement("Ʊ�ݺ���|NOTNULL", document.all('ChequeNo2').value) != true) {
	      return false;
	    }		
	    	TempClassGrid.setRowColData(mulLineCount,3,fm.Currency2.value);
			TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee2.value);
			TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeNo2.value);
			TempClassGrid.setRowColData(mulLineCount,6,fm.ChequeDate2.value);
			TempClassGrid.setRowColData(mulLineCount,8,fm.BankCode2.value);
			TempClassGrid.setRowColData(mulLineCount,9,fm.BankAccNo2.value);
			TempClassGrid.setRowColData(mulLineCount,10,fm.AccName2.value);
			TempClassGrid.setRowColData(mulLineCount,7,document.all('PayDate').value);
			TempClassGrid.setRowColData(mulLineCount,17,'');
			TempClassGrid.setRowColData(mulLineCount,18,'');
			//TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode2.value);
			//TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo2.value);
			//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName2.value);		
		}	
		if(PayMode==3)
		{
			if (verifyElement("Ʊ�ݺ�|NOTNULL", document.all('ChequeNo3').value) != true) {
	      return false;
	    }	
		//	if (verifyElement("֧Ʊ����|NOTNULL", document.all('ChequeDate3').value) != true) {
	   //   return false;
	   // }
			if (verifyElement("��������|NOTNULL", document.all('BankCode3').value) != true) {
	      return false;
	    }
		//	if (verifyElement("�������л���|NOTNULL", document.all('AccName3').value) != true) {
	   //   return false;
	   // } 
		//	if (verifyElement("�շ�����|NOTNULL", document.all('InBankCode3').value) != true) {
	   //   return false;
	  //  }
		//	if (verifyElement("�շ������˺�|NOTNULL", document.all('InBankAccNo3').value) != true) {
	  //    return false;
	    //}
	  
	  		TempClassGrid.setRowColData(mulLineCount,3,fm.Currency3.value);
			TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee3.value);
			TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeNo3.value);
			TempClassGrid.setRowColData(mulLineCount,6,fm.ChequeDate3.value);
			TempClassGrid.setRowColData(mulLineCount,7,'');
			TempClassGrid.setRowColData(mulLineCount,8,fm.BankCode3.value);
			TempClassGrid.setRowColData(mulLineCount,9,fm.BankAccNo3.value);
			TempClassGrid.setRowColData(mulLineCount,10,fm.AccName3.value);
			TempClassGrid.setRowColData(mulLineCount,17,'');
			TempClassGrid.setRowColData(mulLineCount,18,'');
		//	TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode3.value);
		//	TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo3.value);
			//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName3.value);		
		}	
		/*if(PayMode==4)
		{
			
			if (verifyElement("��������|NOTNULL", document.all('BankCode4').value) != true) {
	      return false;
	    }
			if (verifyElement("���������˺�|NOTNULL", document.all('BankAccNo4').value) != true) {
	      return false;
	    }
			if (verifyElement("�������л���|NOTNULL", document.all('AccName4').value) != true) {
	      return false;
	    } 
			if (verifyElement("�շ�����|NOTNULL", document.all('InBankCode4').value) != true) {
	      return false;
	    }    
			if (verifyElement("�շ������˺�|NOTNULL", document.all('InBankAccNo4').value) != true) {
	      return false;
	    }		
			
			
			TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee4.value);
			//TempClassGrid.setRowColData(mulLineCount,4,fm.ChequeNo4.value);
			//TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeDate4.value);
			TempClassGrid.setRowColData(mulLineCount,6,'');
			TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode4.value);
			TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo4.value);
			TempClassGrid.setRowColData(mulLineCount,9,fm.AccName4.value);
			TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode4.value);
			TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo4.value);
			//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName4.value);		
		}	*/
		if(PayMode==5)
		{
			TempClassGrid.setRowColData(mulLineCount,3,fm.Currency5.value);
			TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee5.value);
			TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeNo5.value);
			TempClassGrid.setRowColData(mulLineCount,7,document.all('PayDate').value);
			TempClassGrid.setRowColData(mulLineCount,6,'');
			TempClassGrid.setRowColData(mulLineCount,8,'');
			TempClassGrid.setRowColData(mulLineCount,10,'');
			TempClassGrid.setRowColData(mulLineCount,8,'');
			TempClassGrid.setRowColData(mulLineCount,17,'');
			TempClassGrid.setRowColData(mulLineCount,18,'');
			//TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeDate5.value);
			//TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode5.value);
			//TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo5.value);
			//TempClassGrid.setRowColData(mulLineCount,9,fm.AccName5.value);
			//TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode5.value);
			//TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo5.value);
			//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName5.value);		
		}	
	/*	if(PayMode==6)
		{
			if (verifyElement("�շ�����|NOTNULL", document.all('InBankCode6').value) != true) {
	      return false;
	    }    
			if (verifyElement("�շ������˺�|NOTNULL", document.all('InBankAccNo6').value) != true) {
	      return false;
	    }	
			
			TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee6.value);
			//TempClassGrid.setRowColData(mulLineCount,4,fm.ChequeNo6.value);
			//TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeDate6.value);
			//TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode6.value);
			//TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo6.value);
			//TempClassGrid.setRowColData(mulLineCount,9,fm.AccName6.value);
	    TempClassGrid.setRowColData(mulLineCount,6,document.all('PayDate').value);
			TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode6.value);
			TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo6.value);
			//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName6.value);		
		}	*/
		if(PayMode==4)
		{
	
			if (verifyElement("��������|NOTNULL", document.all('BankCode4').value) != true) {
	      return false;
	    }
			if (verifyElement("�����˺�|NOTNULL", document.all('BankAccNo4').value) != true) {
	      return false;
	    }
			if (verifyElement("���л���|NOTNULL", document.all('AccName4').value) != true) {
	      return false;
	    }
	
			TempClassGrid.setRowColData(mulLineCount,3,fm.Currency4.value);
			TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee4.value);
			TempClassGrid.setRowColData(mulLineCount,7,'');
			TempClassGrid.setRowColData(mulLineCount,8,fm.BankCode4.value);
			TempClassGrid.setRowColData(mulLineCount,9,fm.BankAccNo4.value);
			TempClassGrid.setRowColData(mulLineCount,10,fm.AccName4.value);
			TempClassGrid.setRowColData(mulLineCount,17,fm.IDType.value);
			TempClassGrid.setRowColData(mulLineCount,18,fm.IDNo.value);
			TempClassGrid.setRowColData(mulLineCount,5,'');
			//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName7.value);		
		}	
		//if(PayMode==8)
		//{
		//	TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee8.value);
		//	TempClassGrid.setRowColData(mulLineCount,6,document.all('PayDate').value);
		//}	
	  if(PayMode==6)
	  { //alert("BBBBBBBBBBBBBBBB");
	   if (verifyElement("��������|NOTNULL", document.all('BankCode6').value) != true) {
	      return false;
	    }
	    
	    TempClassGrid.setRowColData(mulLineCount,3,fm.Currency6.value);
	    TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee6.value);
			TempClassGrid.setRowColData(mulLineCount,7,'');
			//TempClassGrid.setRowColData(mulLineCount,6,'');
			TempClassGrid.setRowColData(mulLineCount,8,fm.BankCode6.value);
			TempClassGrid.setRowColData(mulLineCount,9,fm.BankAccNo6.value);
			TempClassGrid.setRowColData(mulLineCount,10,fm.AccName6.value);
			//TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode9.value);
			//TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo9.value);
			//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName9.value);
			TempClassGrid.setRowColData(mulLineCount,5,'');
			TempClassGrid.setRowColData(mulLineCount,6,'');
			TempClassGrid.setRowColData(mulLineCount,17,'');
			TempClassGrid.setRowColData(mulLineCount,18,'');
	  }
		if(PayMode=='0')
		{
			TempClassGrid.setRowColData(mulLineCount,3,fm.Currency0.value);
			TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee0.value);
			TempClassGrid.setRowColData(mulLineCount,7,'');
		}  
	 // document.all('spanTempClassGrid'+mulLineCount).all('TempClassGridSel').checked=true;
 }

}

/*********************************************************************
 *  ��Mulѡ��ֵ������fm��¼���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showMul()
{

		var tSelNo = TempClassGrid.getSelNo()-1;
		if(tSelNo>=0)
		{
			var PayMode = TempClassGrid.getRowColData(tSelNo,1);
			fm.PayMode.value=TempClassGrid.getRowColData(tSelNo,1);
			fm.PayModeName.value=TempClassGrid.getRowColData(tSelNo,2);
			showTempClassInput(PayMode);
			if(PayMode==1)
			{
					fm.Currency1.value=TempClassGrid.getRowColData(tSelNo,3);
					fm.PayFee1.value=TempClassGrid.getRowColData(tSelNo,4);
			}
			if(PayMode==2)
			{
					fm.Currency2.value=TempClassGrid.getRowColData(tSelNo,3);
					fm.PayFee2.value = TempClassGrid.getRowColData(tSelNo,4);
					fm.ChequeNo2.value = TempClassGrid.getRowColData(tSelNo,5);
					fm.ChequeDate2.value = TempClassGrid.getRowColData(tSelNo,6);
					fm.BankCode2.value = TempClassGrid.getRowColData(tSelNo,8);
					fm.BankAccNo2.value = TempClassGrid.getRowColData(tSelNo,9);
					fm.AccName2.value = TempClassGrid.getRowColData(tSelNo,10);
			}
			if(PayMode==3)
			{
					fm.Currency3.value=TempClassGrid.getRowColData(tSelNo,3);
					fm.PayFee3.value = TempClassGrid.getRowColData(tSelNo,4);
					fm.ChequeNo3.value = TempClassGrid.getRowColData(tSelNo,5);
					fm.ChequeDate3.value = TempClassGrid.getRowColData(tSelNo,6);
					fm.BankCode3.value = TempClassGrid.getRowColData(tSelNo,8);
					fm.BankAccNo3.value = TempClassGrid.getRowColData(tSelNo,9);
					fm.AccName3.value = TempClassGrid.getRowColData(tSelNo,10);
			}
			if(PayMode==4)
			{
					fm.Currency4.value=TempClassGrid.getRowColData(tSelNo,3);
					fm.PayFee4.value = TempClassGrid.getRowColData(tSelNo,4);
					fm.BankCode4.value = TempClassGrid.getRowColData(tSelNo,8);
					fm.BankAccNo4.value = TempClassGrid.getRowColData(tSelNo,9);
					fm.AccName4.value = TempClassGrid.getRowColData(tSelNo,10);
					fm.IDType.value = TempClassGrid.getRowColData(tSelNo,17);
					fm.IDNo.value = TempClassGrid.getRowColData(tSelNo,18);						 			
			}		
			if(PayMode==5)
			{
					//fm.ChequeDate5.value = TempClassGrid.getRowColData(tSelNo,5);       
					//fm.BankCode5.value = TempClassGrid.getRowColData(tSelNo,7);         
					//fm.BankAccNo5.value = TempClassGrid.getRowColData(tSelNo,8);        
					//fm.AccName5.value = TempClassGrid.getRowColData(tSelNo,9);          
					//fm.InBankCode5.value = TempClassGrid.getRowColData(tSelNo,10);      
					//fm.InBankAccNo5.value = TempClassGrid.getRowColData(tSelNo,11);     
					//fm.InAccName5.value = TempClassGrid.getRowColData(tSelNo,12);		 			
			}		
			if(PayMode==6)
			{
					fm.Currency6.value=TempClassGrid.getRowColData(tSelNo,3);
					fm.PayFee6.value = TempClassGrid.getRowColData(tSelNo,4);            
					//fm.ChequeNo6.value = TempClassGrid.getRowColData(tSelNo,4);      
					fm.BankCode6.value = TempClassGrid.getRowColData(tSelNo,8);         
					fm.BankAccNo6.value = TempClassGrid.getRowColData(tSelNo,9);        
					fm.AccName6.value = TempClassGrid.getRowColData(tSelNo,10);          			
			}
	}
}

function getAgentCode()
{
	if((document.all('InputNo3').value!=null||document.all('InputNo3').value!="")&&(document.all('AgentCode').value==""||document.all('AgentCode').value==null))
	{
		//var strSql = "select AgentCode,managecom,agentgroup from ljspay where otherno='"+fm.InputNo3.value+"'";
	  var strSql=wrapSql(tResourceName,'LJSPay2',[fm.InputNo3.value]);
	  var TarrResult = easyQueryVer3(strSql, 1, 1, 1);
	  //var TarrResult = easyExecSql(strSql);
	  //alert("0000000000000000000");
	  if(TarrResult!=null)
	  {
	  	var ArrData = decodeEasyQueryResult(TarrResult);
	  	//alert("1111111111111111");
	  	if(ArrData!=null)
	  	{
		  	if(ArrData[0][0]==""||ArrData[0][2]=="")
		  	{
		  	  alert("δ�鵽��������Ϣ");	
		  	}
		  	//alert("2222222222222");
		  	if(ArrData[0][1]=="")
		  	{
		  	  alert("δ�鵽�����������");	
		  	}	  	
		    fm.AgentCode.value=ArrData[0][0];  
		    fm.PolicyCom.value=ArrData[0][1];
		    fm.AgentGroup.value=ArrData[0][2];	
	    }
	  }
	  
	  else
	  {
	     alert("�޴��ս������ݣ���������ڴ߽ɳ鵵");	 
	     return;
	  }
	  
  }
//  if(TarrResult==null||TarrResult=="")
//  //if((document.all('PolicyCom').value==null||document.all('PolicyCom').value=="")&&(document.all('AgentCode').value==""||document.all('AgentCode').value==null)&&(document.all('AgentGroup').value==""||document.all('AgentGroup').value==null))
//	  {
//	     alert("�˱�����û�д��ս��ѣ�������鵵!"); 
//	  }
//  else
//  {
//     	
//  }
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

   //var strSql = "select GetNoticeNo from LJSPay where othernotype='10' "
   //           + getWherePart('OtherNo','InputNo7')
   //           + getWherePart('PayDate','PayDate','>=');
              //+ getWherePart('PayDate','PayDate','<=');  
   
    var strSql=wrapSql(tResourceName,'LJSPay3',[document.all('InputNo7').value,document.all('PayDate').value]);            	
   var arrResult = easyExecSql(strSql);
   if(arrResult == null ||arrResult == ""||arrResult=='null')
   {
    alert("���ڲ����շ�!!!");
    return false;	
   }
  else
  	{
    fm.InputNo8.value=arrResult;	
    }
  }	
  if((document.all('InputNo8').value!=null&&document.all('InputNo8').value!="")&&(document.all('InputNo7').value==null||document.all('InputNo7').value==""))
  {
   //var strSql = "select otherno from LJSPay where GetNoticeNo='"+fm.InputNo8.value+"' and othernotype='10' and PayDate>='"+document.all('PayDate').value+"'"; 	
   var strSql=wrapSql(tResourceName,'LJSPay4',[fm.InputNo8.value,document.all('PayDate').value]);
   var arrResult = easyExecSql(strSql);
   if(arrResult==null||arrResult=="")
   {
    alert("�ñ�ȫ����Ų����ڣ����Ѿ�����");	
   }
  else
  	{
     fm.InputNo7.value=arrResult;	
    }
  }  

   if (document.all('InputNo7').value!=null&&document.all('InputNo7').value!="")
   {
     //var strSql = "select managecom from LJSPay where GetNoticeNo='"+fm.InputNo8.value+"' and othernotype='10' and PayDate >='" +document.all('PayDate').value+ "' "; 	
			 var strSql=wrapSql(tResourceName,'LJSPay5',[fm.InputNo8.value,document.all('PayDate').value]);
     var arrResult = easyExecSql(strSql);
     if(arrResult == null ||arrResult == ""||arrResult=='null')
      {
        alert("���ڲ����շ�!!!");
        return false;	
      }
     else
  	  {
        fm.PolicyCom.value=arrResult;	
       }         
   }

}

function GetManageCom()
{
  if(fm.AgentCode.value!=null&&fm.AgentCode.value!="")
  {
   //var strSql = "select a.managecom from LAAgent a,LATree b,LABranchGroup c where 1=1 "
	 //        + "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and (a.AgentState is null or a.AgentState < '03') "
	 //        + "and a.agentcode ='"+fm.AgentCode.value+"'";
	 //var strSql = "select managecom from LAAgent where  agentcode='"+trim(fm.AgentCode.value)+"' ";
   var strSql=wrapSql(tResourceName,'LAAgent4',[trim(fm.AgentCode.value)]);
   var arrResult = easyExecSql(strSql);
   if(arrResult==null||arrResult=="NULL"||arrResult=="")
   {
     alert("ҵ��Ա��Ŵ����޽��ѻ�����Ϣ");	
     fm.AgentCode.value="";
     return false;
   }
  else
  	{
     fm.PolicyCom.value=arrResult;	  	  
    }
  //var aSql = "select agentgroup from LAAgent where agentcode='"+trim(fm.AgentCode.value)+"'";  
  var aSql=wrapSql(tResourceName,'LAAgent5',[trim(fm.AgentCode.value)]);
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
       //var nSql = "select name from LAAgent where agentcode='"+trim(fm.AgentCode.value)+"'";
       var nSql=wrapSql(tResourceName,'LAAgent6',[trim(fm.AgentCode.value)]);
       arrResult = easyExecSql(nSql);
       fm.AgentName.value=arrResult;
    }  
  }	
	
}
/*********************************************************************
 *  �շѷ�ʽѡ���ڲ�ת��ʱ����ѯʵ������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryLJAGet()
{
   if(document.all('ChequeNo5').value!=""||document.all('OtherNo5').value!="")
   {
      EasyQueryPay();
   }
   else
   {
    alert("ʵ��������������벻��ͬʱΪ��"); 
    return false;	
   }	
}

function getLJSPayPolicyCom()
{
  if (fm.InputNo11.value!=null&&fm.InputNo11.value!="")	
  {
	  //var strSql = "select distinct managecom from ljspay where getnoticeno='"+fm.InputNo11.value+"'";
    var strSql=wrapSql(tResourceName,'LJSPay6',[fm.InputNo11.value]);
    
    var arrResult = easyExecSql(strSql);    	
    fm.PolicyCom.value=arrResult;
  }
  else if (fm.InputNo12.value!=null&&fm.InputNo12.value!="")
  {
	  //var strSql = "select managecom from ljspay where otherno='"+fm.InputNo12.value+"'";
    var strSql = wrapSql(tResourceName,'LJSPay7',fm.InputNo12.value);
    var arrResult = easyExecSql(strSql);     	
    fm.PolicyCom.value=arrResult;
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
	  //var strSql2 = "select * from lcgrpcont where grpcontno = '" + fm.InputNo5.value +"'";
	  var strSql2 =wrapSql(tResourceName,'LCGrpCont1',[fm.InputNo5.value]);
	  var arrResult2 = easyExecSql(strSql2);
	  if(arrResult2 == null)
	  {
	     //var strSql = "select managecom from LCCont where contno='"+fm.InputNo5.value+"'";
       	 var strSql = wrapSql(tResourceName,'LCCont3',[fm.InputNo5.value]);
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
           //var ASql = "select agentcode from LCCont where contno='"+fm.InputNo5.value+"'";
           var ASql=wrapSql(tResourceName,'LCCont1',[fm.InputNo5.value]);
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
     else
    {
      //var strSql = "select managecom from LCGrpCont where grpcontno='"+fm.InputNo5.value+"'";
      var strSql = wrapSql(tResourceName,'LCGrpCont3',[fm.InputNo5.value]);
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
        //var ASql = "select agentcode from LCGrpCont where grpcontno='"+fm.InputNo5.value+"'";
        var ASql=wrapSql(tResourceName,'LCGrpCont2',[fm.InputNo5.value]);
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




function afterSelectCode()
{
	if(document.all('TempFeeType').value=='1' )
	{
		if(document.all('CertifyFlag').value=='1')
		{
			 divCertifyFlag1.style.display="";	
       divCertifyFlag2.style.display="none";
       divCertifyFlag3.style.display="none";
		}
		else if(document.all('CertifyFlag').value=='2')
		{
			 divCertifyFlag2.style.display="";	
       divCertifyFlag3.style.display="none";
       divCertifyFlag1.style.display="none";
		}
		else if (document.all('CertifyFlag').value=='3')
		{
			 divCertifyFlag3.style.display="";	
       divCertifyFlag2.style.display="none";
       divCertifyFlag1.style.display="none";
       document.all.InputNo1.display = "none";
		}
		else
			{
			}
	}
	if(document.all('TempFeeType').value=='6' )
	{
		if(document.all('ClaimFeeType').value=='1')
		{
			 divClaimFeeType1.style.display="";	
       divClaimFeeType2.style.display="none";
		}
		else if(document.all('ClaimFeeType').value=='2')
		{
			 divClaimFeeType2.style.display="";	
       divClaimFeeType1.style.display="none";
       divCertifyFlag1.style.display="none";
		}
		else
			{
			}
	}
}

function PayModePrem()
{
  var SumPrem =0.0;
	var TempCount = TempGrid.mulLineCount;
  for(i=0;i<TempCount;i++)
  {
  		var tempfeegridfee =TempGrid.getRowColData(i,4);
	    if(TempGrid.getRowColData(i,4)=="")
	    tempfeegridfee=0.00;
  //	SumPrem = SumPrem+parseFloat(tempfeegridfee);//���ѽ���ۼ� 
  SumPrem=Arithmetic(SumPrem,'+',tempfeegridfee,2);  
  }
 // SumPrem = pointTwo(SumPrem);
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
 // if (document.all("PayMode").value == '7')
 // {
 // 	document.all("PayFee7").value = SumPrem;
//  }
 // if (document.all("PayMode").value == '8')
//  {
//  	document.all("PayFee8").value = SumPrem;
//  }
//  if (document.all("PayMode").value == '9')
//  {
//  	document.all("PayFee9").value = SumPrem;
//  }
  }
  
function EasyQueryPay ()
{
  // ƴSQL��䣬��ҳ��ɼ���Ϣ
  //var strSql = "select ActuGetNo, OtherNo,SumGetMoney,Drawer,DrawerID from LJAGet where 1=1 and ConfDate is null and EnterAccDate is null and (bankonthewayflag='0' or bankonthewayflag is null) and paymode<>'4' ";
  
  
  //if (document.all('ChequeNo5').value!="")
  //{
  //  	strSql = strSql + " and actugetno='"+document.all('ChequeNo5').value+"'";
  //}
  //if(document.all('OtherNo5').value!="")
	//{
	//    strSql = strSql + " and otherno='"+document.all('OtherNo5').value+"'";	
	//}
  
  var strSql = wrapSql(tResourceName,'LJAGet1',[document.all('ChequeNo5').value,document.all('OtherNo5').value]);
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
    try
		{
     document.all('ChequeNo5').value=QueryLJAGetGrid.getRowColData(tSel-1,1);           
     document.all('OtherNo5').value=QueryLJAGetGrid.getRowColData(tSel-1,2);
     document.all('Currency5').value=QueryLJAGetGrid.getRowColData(tSel-1,3);              
     document.all('PayFee5').value=QueryLJAGetGrid.getRowColData(tSel-1,4);         
     document.all('Drawer5').value=QueryLJAGetGrid.getRowColData(tSel-1,5);            
	  }
	  	catch(ex)
		{
				alert(ex);
		}
	} 	
}

function CheckPayMode()
{
	if(document.all('CertifyFlag').value=="2"&&document.all('PayMode').value!="4")
	{
	    alert("���л���Э����(����)ֻ��ѡ�񽻷ѷ�ʽ������ת��")	;
	    return false;
	}
 //������ѷ�ʽΪ2||3-֧Ʊ�࣬��Ʊ�ݺ�������в���Ϊ��
   if(document.all('PayMode').value=="2")
  {
  		if (verifyElement("��������|NOTNULL", document.all('BankCode2').value) != true) {
      return false;
      }
		  if (verifyElement("Ʊ�ݺ���|NOTNULL", document.all('ChequeNo2').value) !=true)  {
			return false;
  		}
  }
  
  if(document.all('PayMode').value=="3")
  {
  	  if (verifyElement("��������|NOTNULL", document.all('BankCode3').value) != true) {
      return false;
      }
		  if (verifyElement("Ʊ�ݺ���|NOTNULL", document.all('ChequeNo3').value) !=true)  {
			return false;
  		}
  	}
  
  if(document.all('PayMode').value=="4" )
  {
  			if (verifyElement("��������|NOTNULL", document.all('BankCode4').value) != true) {
      return false;
    }
		if (verifyElement("�����˺�|NOTNULL", document.all('BankAccNo4').value) != true) {
      return false;
    }
		if (verifyElement("���л���|NOTNULL", document.all('AccName4').value) != true) {
      return false;
    }
  	
  	if(document.all('BankCode4').value=="0101")
  	{
  	if(document.all('BankAccNo4').value.length!=19 || !isInteger(trim(document.all('BankAccNo4').value)))
  	{
  		alert("�������е��˺ű�����19λ�����֣����һ���Ǻţ�*����Ҫ��");
  		return false;
  		}
  	}
  	
  	}
  	
   if(document.all('PayMode').value=="6")
  {
  		if (verifyElement("��������|NOTNULL", document.all('BankCode6').value) != true) {
      return false;
      }
  }
   
  
      return true;	
}
/* add ln 2008-07-14*/
// ��ѯ��ť
var queryBug = 1;
function verifyTempNo(tempfeeno) 
{
	var tQueryPrtNo =tempfeeno;		                                           
	
  	  //�ж������������Ѿ���Ϊ����ʽ���������������Ҫ�����ݵ��뵽����ҵ��ϵͳ����������ʾ����¼��
  	  //strSql= "select 1 from LJTempFee where TempFeeNo='"+tQueryPrtNo+"'"
      //            +" union "
      //            +"select 1 from LJTempFeeClass where TempFeeNo='"+tQueryPrtNo+"'"
      //            +" union "
  	  //            + "select 1 from BPOMissionState where BussNo='"+tQueryPrtNo+"' and  BussNoType='OF' and State <>'2'";   //��������ɾ�����쳣��
  
  		strSql=wrapSql(tResourceName,'LJTempFee2',[tQueryPrtNo,tQueryPrtNo,tQueryPrtNo]);
  
      var arrResult = easyExecSql(strSql);
	   if (arrResult == null) 
	   {  
          return true;
       }
      else
      {
      		alert("�����շ��վݺ�/���л���Э����Ŵ���Ĳ����շѵ�֤�����Ѿ��������������Ѿ����뵽����ҵ��ϵͳ�У�����Ҫ����¼�룡");
      		return false;
      } 
}

//У��¼��ĵ�֤�������Ƿ������Զ������ı�ǣ������Ҫ����true,���򷵻�false
function needVerifyCertifyCode(CertifyCode)
{
	try{
			//var tSql = "select JugAgtFlag from lmcertifydes where certifycode = '" + CertifyCode + "'";
			var tSql=wrapSql(tResourceName,'LMCertifyDes1',[CertifyCode]);
			var tResult = easyExecSql(tSql, 1, 1, 1); 
			var JugAgtFlag = tResult[0][0];
			//JugAgtFlagΪ0��null-����\���ն���У�飬1-������У��,���ղ���У�飬2-���Ų���У��,������У�飬3-����/���ն�����У��
			if(JugAgtFlag == "2" || JugAgtFlag == "3")
			{
					return false;
			}
		return true;
	}catch(ex){}
	return false;
}


//У��¼�뱣���ź͹�������Ƿ�һ��
function verifyContNo(ContNo)
{
		var strSql="";
		
		//strSql = "select managecom from ljspay where othernotype in ('1','2') and otherno = '" + ContNo +"'";
		strSql = wrapSql(tResourceName,'LJSPay8',[ContNo]);
		var arrResult = easyExecSql(strSql)        ;
		if (arrResult != null) 
		{
				if(arrResult[0][0] != trim(document.all('ManageCom').value))
				{
						alert("��½���շѻ����ͱ�����Ӧ�շѹ��������һ�£����ʵ!");
						return false;
				}      
		}
		else
		{
				alert("δ�鵽�ñ�����Ϣ��");
				return false;	
		}  
		
		return true;
}




//У�鱣����״̬��������ڽ��ѱ�����״̬ΪʧЧ����������
function verifyContState(ContNo)
{
  var strSql="";
  var CurrentDate=getCurrentDate();
  //strSql = "select 1 from lccontstate where contno='"+ContNo+"' and statetype='Available' " 
  //       + "and state='1' and startdate<='"+CurrentDate+"' and (enddate >='"+CurrentDate+"' or enddate is null) ";

  strSql=wrapSql(tResourceName,'LCContState1',[ContNo,'Available',CurrentDate,CurrentDate]);

  if(easyExecSql(strSql)!=null)
  {
  	alert("�ñ���״̬Ϊ��ʧЧ״̬���������ڽ���!\n��������ȫ��Ч��");
  	return false;
  }
  
  //var strSql1 = "select 1 from lccontstate where contno='"+ContNo+"' and statetype='PayPrem' " 
  //            + "and state='1' and startdate<='"+CurrentDate+"' and (enddate >='"+CurrentDate+"' or enddate is null) ";

   strSql1=wrapSql(tResourceName,'LCContState1',[ContNo,'PayPrem',CurrentDate,CurrentDate]);
              
  if(easyExecSql(strSql1)!=null)
  {
  	alert("�ñ���״̬Ϊ���潻״̬���������ڽ���!\n��������ȫ���棡");
  	return false;
  }            	
 return true;
}


//ȥ�����еĿؼ��еĿ��ַ�
function trimNo()
{
    document.all('InputNo1').value = document.all('InputNo1').value.trim()    ;
    document.all('InputNo2').value = document.all('InputNo2').value.trim()    ;
    document.all('InputNoB').value = document.all('InputNoB').value.trim()    ;
    document.all('InputNoB1').value= document.all('InputNoB1').value.trim()   ;
    document.all('InputNoC').value = document.all('InputNoC').value.trim()    ;
    document.all('InputNo3').value = document.all('InputNo3').value.trim()    ;
    document.all('XQCardNo').value = document.all('XQCardNo').value.trim()    ;
    document.all('InputNo4').value = document.all('InputNo4').value.trim()    ;
    document.all('InputNo5').value = document.all('InputNo5').value.trim()    ;
    document.all('InputNo7').value = document.all('InputNo7').value.trim()    ;
    document.all('InputNo8').value = document.all('InputNo8').value.trim()    ;
    document.all('InputNo11').value= document.all('InputNo11').value.trim()   ;
    document.all('InputNo12').value= document.all('InputNo12').value.trim()   ;
    document.all('InputNo13').value= document.all('InputNo13').value.trim()   ;
    document.all('InputNo14').value= document.all('InputNo14').value.trim()   ;
    document.all('InputNo99').value= document.all('InputNo99').value.trim()   ;
    document.all('InputNo19').value= document.all('InputNo19').value.trim()   ;          	
 return true;
}
function checkBank(tBankCode,tBankAccNo){
	if(tBankCode.value.length>0 && tBankAccNo.value.length>0){
		if(!checkBankAccNo(tBankCode.value,tBankAccNo.value)){
			tBankAccNo.value = "";
			return false;
		}
	}
}
