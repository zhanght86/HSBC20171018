 //               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";

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
//
var arrCardRisk;

var n=0;

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
      document.getElementById("fmSave").submit();
  }
  else alert("����������");
  document.all('distill').disabled=false;

}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
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
  
  clearFormData();
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

}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("��SinCardTempFeeInput.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//ȡ����ť��Ӧ����
function cancelForm()
{
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
	window.open("../finfee/TempFeeQuery.html");
}




//��֤�ֶε�ֵ
function confirm()
{
  //���ȼ���¼���
//   if (!verifyInput()) return false; 
    

   if((trim(document.all('ManageCom').value)).length!=8) 
   {
	  	alert("������������ļ���������8λ�������룡");
	    return false;	
    }
   
   		//��¼�뵥֤���ͺ����Զ����������ˣ������У���Ƿ�¼���˵�֤����
   		if(document.all('CertifyCode').value=="" || document.all('CertifyCode').value==null)
   		{
   			alert("�����뵥֤���ͣ�");
   			return ;
   		}
   		
//   		var sql="SELECT agentstate FROM laagent where agentcode='"+trim(document.all('AgentCode').value)+"'";
   		
   		var sqlid1="SinCardTempFeeInputSql1";
   	 	var mySql1=new SqlClass();
   	 	mySql1.setResourceName("selflist.SinCardTempFeeInputSql");
   	 	mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
   	 	mySql1.addSubPara(trim(document.all('AgentCode').value));//ָ���������
   	 	var sql = mySql1.getString();
   		
	 	var state = decodeEasyQueryResult(easyQueryVer3(sql));
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
	//����Ϊ��������������
		//2010-4-30 �ſ���չ����
		
//		var sql="SELECT BranchType FROM laagent where agentcode='"+trim(document.all('AgentCode').value)+"'";
		
		var sqlid2="SinCardTempFeeInputSql2";
   	 	var mySql2=new SqlClass();
   	 	mySql2.setResourceName("selflist.SinCardTempFeeInputSql");
   	 	mySql2.setSqlId(sqlid2); //ָ��ʹ��SQL��id
   	 	mySql2.addSubPara(trim(document.all('AgentCode').value));//ָ���������
   	 	var sql = mySql2.getString();
		
		var branchtype = decodeEasyQueryResult(easyQueryVer3(sql));
		if(branchtype==null){
			alert("��ѯ������"+document.all('AgentCode').value+"��Ϣʧ�ܣ�");
			return false;
		}
		if(branchtype[0][0] != "1" && branchtype[0][0] !="4")
		{
			alert("�ô����˲��Ǹ������������ˣ����ʵ!");
			return false;
		}
		
    //2010-1-14 10:36 ����������רԱ��У��
   
//		var sql="select agentcode,upagentcode from laxagent where managecom='"+trim(document.all('PolicyCom').value)+"'";
		
		var sqlid3="SinCardTempFeeInputSql3";
   	 	var mySql3=new SqlClass();
   	 	mySql3.setResourceName("selflist.SinCardTempFeeInputSql");
   	 	mySql3.setSqlId(sqlid3); //ָ��ʹ��SQL��id
   	 	mySql3.addSubPara(trim(document.all('PolicyCom').value));//ָ���������
   	 	var sql = mySql3.getString();
		
    var aAgent= decodeEasyQueryResult(easyQueryVer3(sql));
    if(aAgent == null || aAgent == "")
		{
			alert("�ô����˲����������רԱ�����ʵ!");
			return false;
		}
		else
		{
			
//			var sql="select agentcode from laagent where agentcode='"+trim(aAgent[0][1])+"' and agentstate<'03' ";
			
			var sqlid4="SinCardTempFeeInputSql4";
	   	 	var mySql4=new SqlClass();
	   	 	mySql4.setResourceName("selflist.SinCardTempFeeInputSql");
	   	 	mySql4.setSqlId(sqlid4); //ָ��ʹ��SQL��id
	   	 	mySql4.addSubPara(trim(aAgent[0][1]));//ָ���������
	   	 	var sql = mySql4.getString();

			var cAgent= decodeEasyQueryResult(easyQueryVer3(sql));
	    if(cAgent == null || cAgent == "")
			{
				alert("�ô����˶�Ӧ��רԱ"+aAgent[0][1]+"�����ڻ�������ְ�����ʵ!");
				return false;
			}
	  }
		
    initTempGrid();
    initTempClassGrid();
    TempGrid.clearData("TempGrid");
    TempClassGrid.clearData("TempClassGrid"); 

    //У��¼��
    if (verifyElement("������ʼ����|NOTNULL&len=20", document.all('StartNo').value) != true)  return false;

    if (verifyElement("������ֹ����|NOTNULL&len=20", document.all('EndNo').value) != true)    return false;

//zy 2010-1-15 15:32 ��������µ�֤����������շ�
    if(document.all('StartNo').value.substr(2,1)!="7" || document.all('EndNo').value.substr(2,1)!="7" )
    {
      alert("������ĵ�֤�������µ�֤�������ʵ");
      return false;
    }
	  var cardNumSql = "select '"+document.all('EndNo').value+"'-'"+document.all('StartNo').value+"'+1 from dual";
    var cardNumResult = easyExecSql(cardNumSql);
         //alert(arrResult);
	  if(cardNumResult>5000)
	  {
		    alert("������Ŀ�����������5000���������һ�ο����շѵ�������");
         return false;
	  }
//    if(document.all('CertifyCode').value=="" || document.all('CertifyCode').value==null)
//    {
//    	alert("�����뵥֤���룡");
//    	return ;
//    }
    //�����������Ŀ�����ֹ��
    fmSave.fmStartNo.value=document.all('StartNo').value;
    fmSave.fmEndNo.value=document.all('EndNo').value;

    //У�鵥֤���ţ�ֻ��У���Ƿ񷢷ŵ����н��ѵ��ļ�����

  // 	var receivecom = decodeEasyQueryResult(easyQueryVer3("SELECT receivecom FROM lzcard where certifycode='"+trim(document.all('CertifyCode').value)+"' and  startno='"+trim(document.all('StartNo').value)+"' and endno='"+trim(document.all('EndNo').value)+"' "));
  // if(receivecom == null || receivecom == "")
  // {
	 //  	alert("������ĸ��������ڵ�֤ϵͳ��û�м�¼����˶Կ�����ֹ�ţ�");
	//   	return false;
  // 	}

    //2008-08-27 zz Ϊ�������������ڲ��Դ�����Ӷ����ѵ�֤�������������У��ȴ����У��
   	//if(receivecom[0][0]!=("A"+document.all('ManageCom').value))
   	//{
   		//alert("����¼��ĸ�������û�з��͵��ù�������£���˶ԣ�");
   		//return false;
   	//}
   	//�����ĵ�֤У�飬���Է��ŵ��ļ����������ǲ��Ż����Ǵ���������
//   	var receivecom = decodeEasyQueryResult(easyQueryVer3("select count(*) from lzcard where certifycode='"+trim(document.all('CertifyCode').value)+"' and  startno>='"+trim(document.all('StartNo').value)+"' and startno<='"+trim(document.all('EndNo').value)+"' and stateflag = '3' and (substr(receivecom, 1, 1)='B' or	substr(receivecom, 1, 1)='D' or (substr(receivecom, 0, 1)='A' and length(trim(receivecom))='9'))"));
//   	var cardcount = parseInt(document.all('EndNo').value.substr(12,19),10)-parseInt(document.all('StartNo').value.substr(12,19),10)+1;   	                                                         
//   	var csql="select count(*) from lzcard where certifycode='"+trim(document.all('CertifyCode').value)+"' and  startno>='"+trim(document.all('StartNo').value)+"' and startno<='"+trim(document.all('EndNo').value)+"' and stateflag = '3' and (substr(receivecom, 1, 1)='B' or	substr(receivecom, 1, 1)='D' or (substr(receivecom, 0, 1)='A' and length(trim(receivecom))='9'))";
//    if(receivecom == null || receivecom == "")
//    {
//	   	alert("������ĸ��������ڵ�֤ϵͳ��û�м�¼����˶Կ�����ֹ�ţ�");
//	   	return false;
//    }
//    if(receivecom!=cardcount)
//    {
//    	alert("������ĸ�����������ĳЩ��֤û�а��շ��Ź�����з��ţ����ʵ��");
//    	return  false;
//    }
    
    //�жϸÿ����Ƿ񷢸���ҵ��Ա
    
//    var sql="select count(*) from lzcard where certifycode='"+trim(document.all('CertifyCode').value)+"' and  startno>='"+trim(document.all('StartNo').value)+"' and endno<='"+trim(document.all('EndNo').value)+"' and stateflag = '3' and receivecom='D"+fm.AgentCode.value+"'";
    
        var sqlid5="SinCardTempFeeInputSql5";
	 	var mySql5=new SqlClass();
	 	mySql5.setResourceName("selflist.SinCardTempFeeInputSql");
	 	mySql5.setSqlId(sqlid5); //ָ��ʹ��SQL��id
	 	mySql5.addSubPara(trim(document.all('CertifyCode').value));//ָ���������
	 	mySql5.addSubPara(trim(document.all('StartNo').value));
	 	mySql5.addSubPara(trim(document.all('EndNo').value));
	 	mySql5.addSubPara("D"+fm.AgentCode.value);
	 	var sql = mySql5.getString();
    
     var receivecom = decodeEasyQueryResult(easyQueryVer3(sql));
     var dif= decodeEasyQueryResult(easyQueryVer3("select ("+trim(document.all('EndNo').value)+"-"+trim(document.all('StartNo').value)+")+1 from dual"));

     if(receivecom[0][0] < dif[0][0])
     {
    	 alert("��������δ���Ÿ���ҵ��Ա��");
    	 return false;
     }
    
   	if(!(document.all('PayDate').value==null || trim(document.all('PayDate').value)==""))
    {
      if(!isDate(document.all('PayDate').value))
      {
      	return false;
      }
      if(compareDate(document.all('PayDate').value,getCurrentDate())==1)
      {
      	alert("�������ڲ��ܳ������죡");
      	return false;
      }
     }
   	
   
    TempGrid.unLock();   	   	
    TempGrid.addOne("TempGrid");
  //  TempClassGrid.unLock();   	   	
  //  TempClassGrid.addOne("TempClassGrid");
    divTempFeeClassInput.style.display="";
    confirmFlag=true;
    document.all('distill').disabled=true;  //ֻ�ܵ��һ��ȷ������ֹ���¼��
   
}

  /**
   * ��С��������λ��������
   * <p><b>Example: </b><p>
   * <p>mathRound(123.456) returns 123.46<p>
   * @param intValue ������ֵ
   * @return ��С��������λ����������������ֵ
   */
function mathRound( x )
{
  var v = Math.round( x * 100 ) ;
  v = v/100;
  return v;
}

function addRecord()
{   
   if(confirmFlag==false){alert("�뵥��ȷ��");return;}//���û�е���ȷ������ʾ

   if(!checkPubValue())  return;
   if(!checkTempRecord()) return;
   if(!checkTempClassRecord()) return;

  //�ж��ݽ�����Ϣ�еĽ��ѽ���Ƿ���ݽ��ѷ�����Ϣ�еĽ��ѽ�����
   tempClassFee=tempClassFee/10000;
   tempFee=tempFee/10000;

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
   }

  //	 addAction = addAction+1;
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
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,2,TempClassGrid.getRowColData(i,1));
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,3,document.all('PayDate').value.trim());
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,4,TempClassGrid.getRowColData(i,3));
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,5,TempClassGrid.getRowColData(i,6));
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,6,document.all('PolicyCom').value.trim());
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,7,TempClassGrid.getRowColData(i,4)); 
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,8,TempClassGrid.getRowColData(i,5));     
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,9,TempClassGrid.getRowColData(i,7));     
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,10,TempClassGrid.getRowColData(i,8));     
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,11,TempClassGrid.getRowColData(i,9));     
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,12,TempClassGrid.getRowColData(i,10));     
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,13,TempClassGrid.getRowColData(i,11));     
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,14,TempClassGrid.getRowColData(i,12));
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,18,TempClassGrid.getRowColData(i,16));
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,19,TempClassGrid.getRowColData(i,17));
              
     if(trim(TempClassGrid.getRowColData(i,6))=="") //����н�����ĵ�������Ϊ�գ���ô��־��0
        EnterAccDateFlag=0;
     
     if(compareDate(EnterAccDate,TempClassGrid.getRowColData(i,6))==2)
       EnterAccDate=TempClassGrid.getRowColData(i,6);

    }

  }

   
  if(EnterAccDateFlag==0)
    EnterAccDate="";
   

     
  //�ݽ�����Ϣ���������ύ���ݵ�Grid
  for(i=0;i<TempRecordCount;i++)
  {
    if(TempGrid.getRowColData(i,1)!='')  //������ֱ���Ϊ�գ�������
     {
       TempToGrid.addOne("TempToGrid");//��ǰ�ļ��еĶ��󣬲��ü�ǰ׺
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,3,document.all('PayDate').value.trim());          
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,4,TempGrid.getRowColData(i,3));
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,5,EnterAccDate);          
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,6,document.all('PolicyCom').value.trim());       
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,7,TempGrid.getRowColData(i,1));          
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,8,document.all('AgentGroup').value.trim());
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,9,document.all('AgentCode').value.trim());               
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,10,TempGrid.getRowColData(0,4));                  
     	 TempToGrid.setRowColData(TempToGrid.mulLineCount-1,12,TempGrid.getRowColData(i,5));     
     	 TempToGrid.setRowColData(TempToGrid.mulLineCount-1,13,TempGrid.getRowColData(i,6));   
     }
  }
  fm.PayMode.value = '';
  fm.PayModeName.value = '';
  initTempGrid();
  initTempClassGrid();
  divTempFeeClassInput.style.display="none";
  fm.butAdd.disabled =false;
  initDivPayMode();//��ʼ��divPayMode
  confirmFlag=false; //��λ�������µ���ȷ��
}



//���MulLine����Ϣ������ݸ�ʽ�Ƿ�ϸ�
function checkTempRecord()
{
  tempFee=0.0;
  var TempRecordCount = 0;
  TempRecordCount=TempGrid.mulLineCount; //ҳ���ϼ�¼������ 

  var i=0;
  var j=0;
  //�ϸ�����ֻ��¼��һ�����Ѽ�¼
  if(TempRecordCount>1)
  {
  	alert("һ������ֻ��¼��һ��������Ϣ");
  	return false;
  }
  
  var arrCardMoney = new Array();
  var allMoney = 0;
  var prem = 0;
  
   //parseInt�ڶ�������ָ��Ϊ10,����ʮ����������ֵ,����:�����һ�������� "0x" ��ͷ��parseInt() ��ѵ�һ�����������ಿ�ֽ���Ϊʮ�����Ƶ������������һ�������� 0 ��ͷ����ô ECMAScript v3 ���� parseInt() ��һ��ʵ�ְ������ַ�����Ϊ�˽��ƻ�ʮ�����Ƶ����֡���� ��һ������ �� 1 ~ 9 �����ֿ�ͷ��parseInt() ����������Ϊʮ���Ƶ�����
   var j = parseInt(document.all('EndNo').value.substr(12,19),10)-parseInt(document.all('StartNo').value.substr(12,19),10)+1;
   //alert("document.all('EndNo').value.substr(12,19):"+document.all('EndNo').value.substr(12,19));
   //alert("parseInt(document.all('EndNo').value.substr(12,19)):"+parseInt(document.all('EndNo').value.substr(12,19)));


  //����¼�����뱣�ѵ�У��
  if(TempGrid.checkValue("TempGrid"))
  { 
     for(var n=0;n<TempGrid.mulLineCount;n++)
     {
     	   if(TempGrid.getRowColData(n,3)<=0)
    	  {
	    		alert("¼��Ľ��ѽ��Ӧ��Ϊ����0�����ݣ�������¼��");
	    	 	return false;
    	  }           	   
		     allMoney = allMoney+ parseInt(TempGrid.getRowColData(n,3)); 
		     //alert("��ҳ��multiline�õ��ı���:"+allMoney);
		    //��ѯ�ÿ����ı�����Ϣ
		     
//		     var sql="select prem from LMCardRisk where certifycode ='"+trim(document.all('CertifyCode').value)+"'  and riskcode='"+TempGrid.getRowColData(n,1)+"' and portfolioflag='1'";
		     
		        var sqlid6="SinCardTempFeeInputSql6";
			 	var mySql6=new SqlClass();
			 	mySql6.setResourceName("selflist.SinCardTempFeeInputSql");
			 	mySql6.setSqlId(sqlid6); //ָ��ʹ��SQL��id
			 	mySql6.addSubPara(trim(document.all('CertifyCode').value));//ָ���������
			 	mySql6.addSubPara(TempGrid.getRowColData(n,1));
			 	var sql = mySql6.getString();
		     
		     prem=decodeEasyQueryResult(easyQueryVer3(sql));
		     // portfolioflag�ֶ�Ϊ1ʱ��risocde��ŵ��ǲ�Ʒ����
		     // var sprem = "select prem from LMCardRisk where certifycode ='"+trim(document.all('CertifyCode').value)+"'  and riskcode='"+TempGrid.getRowColData(n,1)+"' ";
		     // prompt("sprem",sprem);
		     if(prem == null || prem == "")
		     {
		     	alert("��֤û�жԸ��������ı��ѽ���������");
		     	return false;
		     	}     	      
     } //end of for


     //alert("��������������ܱ���"+parseInt(prem[0][0])*j);
     
		 if(parseInt(prem[0][0])*j!=allMoney)
		 {
		    alert("¼��Ľ��ѽ���ܶ�������������Ѳ�����������¼�룡");
		   	return false;
		 } 
     
     for(i=0;i<TempRecordCount;i++)  
     {
     	 if(TempGrid.getRowColData(i,1)!='')  //�����Ʒ����Ϊ�գ�������
       {   	     	      	   	
         tempFee = tempFee+10000*parseFloat(TempGrid.getRowColData(i,3));//���ѽ���ۼ� 
       }
       if(TempGrid.getRowColData(i,5)== '' ||TempGrid.getRowColData(i,6)== '')
       {
        alert("�µ��ݽ���¼�룬��¼��ɷѷ�ʽ�ͽɷ�������Ϣ��");
      	return false;
       }      
     }//end of for        	
   } //end of if	    
  return true; 
}



//���һ�ʼ�¼ǰ,������ӵ��ݽ��ѷ�����Ϣ���ݽ��ѽ���ۼ�
function checkTempClassRecord()
{			
  var TempClassRecordCount = 0;
  tempClassFee=0.0;
  var i = 0;
  TempClassRecordCount=TempClassGrid.mulLineCount; //ҳ���ϼ�¼������ 
  if(TempClassRecordCount>1)
  {
  	alert("һ������ֻ��¼��һ��������Ϣ");
  	return false;
  }
     
      
  for(i=0;i<TempClassRecordCount;i++)
  {
   if(TempClassGrid.getRowColData(i,1)!='')  //������ѷ�ʽΪ�գ�������
    {
    	if(trim(TempClassGrid.getRowColData(i,1))!='1' && trim(TempClassGrid.getRowColData(i,1))!='2' && trim(TempClassGrid.getRowColData(i,1))!='3' && trim(TempClassGrid.getRowColData(i,1))!='4')
    	{
	    	alert("�����շ�Ŀǰ��֧�ָ��ֽ��ѷ�ʽ��");
	    	return false;
      }
    	tempClassFee = tempClassFee+10000*parseFloat(TempClassGrid.getRowColData(i,3));//���ѽ���ۼ� 	    	
      if(trim(TempClassGrid.getRowColData(i,6))!='')//����������ڲ�Ϊ�գ���ô�ж��Ƿ���ڵ��ڵ�ǰ����
      {
      	if(isDate(trim(TempClassGrid.getRowColData(i,6))))//������ڸ�ʽ��ȷ
      	{
      		if(compareDate(trim(TempClassGrid.getRowColData(i,6)),getCurrentDate())==1)//������ڵ�ǰ����
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
      
      
      if(TempClassGrid.getRowColData(i,1)=='4')  //������ѷ�ʽΪ4-����ת�ˣ�������д ��������,�����˺�,�������ҵ������ڱ���Ϊ��
      {
        if(trim(TempClassGrid.getRowColData(i,7))==''||trim(TempClassGrid.getRowColData(i,8))==''||trim(TempClassGrid.getRowColData(i,9))=='')
          {
           alert("����ת��ʱ��������д ��������,�����˺�,����");
           return false;
          }
        TempClassGrid.setRowColData(i,6,'');//  �����������ÿ�
        
        if (trim(TempClassGrid.getRowColData(i,7))=="0101") {
          if (trim(TempClassGrid.getRowColData(i,8)).length!=19 || !isInteger(trim(TempClassGrid.getRowColData(i,8)))) {
            alert("�������е��˺ű�����19λ�����֣����һ���Ǻţ�*����Ҫ��");
            return false;
          }
        }
      }
      
      if(TempClassGrid.getRowColData(i,1)=='2'||TempClassGrid.getRowColData(i,1)=='3')  //������ѷ�ʽΪ2||3-֧Ʊ�࣬��Ʊ�ݺ�������в���Ϊ��
      {
        if(trim(TempClassGrid.getRowColData(i,4))==''||trim(TempClassGrid.getRowColData(i,7))=='')
          {
           alert("���ѷ�ʽΪ֧Ʊʱ��Ʊ�ݺ���Ϳ������в���Ϊ��");
           return false;
          }
          TempClassGrid.setRowColData(i,6,'');//  �����������ÿ�
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
  document.all('StartNo').value="";
  document.all('EndNo').value="";
  document.all('CertifyCode').value="";
  document.all('ManageCom').value="";
  document.all('AgentCode').value="";
  document.all('TempFeeCount').value=0;
  document.all('SumTempFee').value=0.0 ;
  divTempFeeSave.style.display="none";
  divTempFeeInput.style.display="";  
  document.all('distill').disabled=false;             
  TempGrid.lock();
}


//��ʼ��ȫ�ֱ���
function initGlobalData()
{
//�����Ӷ���ִ�еĴ���
 addAction = 0;
//�ݽ����ܽ��
 sumTempFee = 0.0;
//�ݽ�����Ϣ�н��ѽ���ۼ�
 tempFee = 0.0;
//�ݽ��ѷ�����Ϣ�н��ѽ���ۼ�
 tempClassFee = 0.0;
 confirmFlag=false; //��λ�������µ���ȷ�� 
}



//У��¼��ĵ�֤�������Ƿ������Զ������ı�ǣ������Ҫ����true,���򷵻�false
function needVerifyCertifyCode(CertifyCode)
{
	try{
//		var tSql = "select JugAgtFlag from lmcertifydes where certifycode = '" + CertifyCode + "'";
		
		    var sqlid7="SinCardTempFeeInputSql7";
		 	var mySql7=new SqlClass();
		 	mySql7.setResourceName("selflist.SinCardTempFeeInputSql");
		 	mySql7.setSqlId(sqlid7); //ָ��ʹ��SQL��id
		 	mySql7.addSubPara(CertifyCode);//ָ���������
		 	var tSql = mySql7.getString();
		
		var tResult = easyExecSql(tSql, 1, 1, 1); 
		var JugAgtFlag = tResult[0][0];
		//JugAgtFlagΪ0��null-����\���ն���У�飬1-������У��,���ղ���У�飬2-���Ų���У��,������У�飬3-����/���ն�����У��
		if(JugAgtFlag == "2" || JugAgtFlag == "3")
		{
				return false;
		}
		return true;
	}catch(E){}
	return false;
/*
  try 
  {
     var tSql = "select Sysvarvalue from LDSysVar where Sysvar='NotVerifyCertifyCode'";          
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
	catch(e){}
 	return true; 
 	*/
}





function queryAgent()
{
	if(document.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��"); 
		 return;
	}
    if(document.all('AgentCode').value == "")	{  
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
	  }
	if(document.all('AgentCode').value != "")	 {
	var cAgentCode = fm.AgentCode.value;  //��������	
//	var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";
	
	var sqlid8="SinCardTempFeeInputSql8";
 	var mySql8=new SqlClass();
 	mySql8.setResourceName("selflist.SinCardTempFeeInputSql");
 	mySql8.setSqlId(sqlid8); //ָ��ʹ��SQL��id
 	mySql8.addSubPara(cAgentCode);//ָ���������
 	var strSql = mySql8.getString();
	
    var arrResult = easyExecSql(strSql);
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
  }
//  var aSql = "select agentgroup from LAAgent where agentcode='"+fm.AgentCode.value+"'";  
  
    var sqlid9="SinCardTempFeeInputSql9";
	var mySql9=new SqlClass();
	mySql9.setResourceName("selflist.SinCardTempFeeInputSql");
	mySql9.setSqlId(sqlid9); //ָ��ʹ��SQL��id
	mySql9.addSubPara(fm.AgentCode.value);//ָ���������
	var aSql = mySql9.getString();
  
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

function GetManageCom()
{
  if(fm.AgentCode.value!=null&&fm.AgentCode.value!="")
  {
   //var strSql = "select a.managecom from LAAgent a,LATree b,LABranchGroup c where 1=1 "
	 //        + "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and (a.AgentState is null or a.AgentState < '03') "
	 //        + "and a.agentcode ='"+fm.AgentCode.value+"'";
//	 var strSql = "select managecom from LAAgent where  agentcode='"+trim(fm.AgentCode.value)+"' ";
	 
	    var sqlid10="SinCardTempFeeInputSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("selflist.SinCardTempFeeInputSql");
		mySql10.setSqlId(sqlid10); //ָ��ʹ��SQL��id
		mySql10.addSubPara(trim(fm.AgentCode.value));//ָ���������
		var strSql = mySql10.getString();
	 
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
//  var aSql = "select agentgroup from LAAgent where agentcode='"+trim(fm.AgentCode.value)+"'";  
  
    var sqlid11="SinCardTempFeeInputSql11";
	var mySql11=new SqlClass();
	mySql11.setResourceName("selflist.SinCardTempFeeInputSql");
	mySql11.setSqlId(sqlid11); //ָ��ʹ��SQL��id
	mySql11.addSubPara(trim(fm.AgentCode.value));//ָ���������
	var aSql = mySql11.getString();
  
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
 //      var nSql = "select name from LAAgent where agentcode='"+trim(fm.AgentCode.value)+"'";
       
       var sqlid12="SinCardTempFeeInputSql12";
   	   var mySql12=new SqlClass();
   	   mySql12.setResourceName("selflist.SinCardTempFeeInputSql");
   	   mySql12.setSqlId(sqlid12); //ָ��ʹ��SQL��id
   	   mySql12.addSubPara(trim(fm.AgentCode.value));//ָ���������
   	   var nSql = mySql12.getString();
       
       arrResult = easyExecSql(nSql);
       fm.AgentName.value=arrResult;
    }  
  }	
	
}

///��ȡҵ��Ա��Ϣ
function GetAgentInfo()
{
	var receivecom="";
	if(_DBT==_DBO){
		
//		var sql="select substr(receivecom, 2, 10) from lzcard where certifycode='"+trim(document.all('CertifyCode').value)+"' and startno='"+trim(document.all('StartNo').value)+"'and stateflag = '3' And substr(receivecom, 1, 1)='D' and rownum='1'";
		
		   var sqlid13="SinCardTempFeeInputSql13";
	   	   var mySql13=new SqlClass();
	   	   mySql13.setResourceName("selflist.SinCardTempFeeInputSql");
	   	   mySql13.setSqlId(sqlid13); //ָ��ʹ��SQL��id
	   	   mySql13.addSubPara(trim(document.all('CertifyCode').value));//ָ���������
	   	   mySql13.addSubPara(trim(document.all('StartNo').value));
	   	   var sql = mySql13.getString();
		
		receivecom = decodeEasyQueryResult(easyQueryVer3(sql));
	}else if(_DBT==_DBM){
		
//		   var sql="select substr(receivecom, 2, 10) from lzcard where certifycode='"+trim(document.all('CertifyCode').value)+"' and startno='"+trim(document.all('StartNo').value)+"'and stateflag = '3' And substr(receivecom, 1, 1)='D' limit 1";
		
		   var sqlid14="SinCardTempFeeInputSql14";
	   	   var mySql14=new SqlClass();
	   	   mySql14.setResourceName("selflist.SinCardTempFeeInputSql");
	   	   mySql14.setSqlId(sqlid14); //ָ��ʹ��SQL��id
	   	   mySql14.addSubPara(trim(document.all('CertifyCode').value));//ָ���������
	   	   mySql14.addSubPara(trim(document.all('StartNo').value));
	   	   var sql = mySql14.getString();
		
		receivecom = decodeEasyQueryResult(easyQueryVer3(sql));
	}
    if(receivecom == null || receivecom == "")
    {
	   	alert("ͨ��������ʼ����δ��ѯ��ҵ��Ա��");
	   	return false;
    }
    else
    {
    	fm.AgentCode.value=receivecom[0][0];
    	GetManageCom();
    }
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
	var payFlag = "";
  if(PayMode=="")
  {
  	alert("����¼�뽻�ѷ�ʽ����Ӧ��Ϣ��");
  	return false;
  }
	 for(i=0;i<mulLineCount;i++)
	  {
	  	payFlag = TempClassGrid.getRowColData(i,1);
	   	if(payFlag==PayMode)
		   {
	 	       if (TempClassGrid.getRowColData(i,4)==document.all('ChequeNo5').value)
	 	       {
			         alert("�˼�¼�Ѿ����");
			         return false;
	 	       }
	 	     
	 	   }
	}

  	if(!CheckPayMode())
  	{
  	  return false;	
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
		TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee1.value);
		TempClassGrid.setRowColData(mulLineCount,6,document.all('PayDate').value);
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
		TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee2.value);
		TempClassGrid.setRowColData(mulLineCount,4,fm.ChequeNo2.value);
		TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeDate2.value);
		TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode2.value);
		TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo2.value);
		TempClassGrid.setRowColData(mulLineCount,9,fm.AccName2.value);
		TempClassGrid.setRowColData(mulLineCount,6,document.all('PayDate').value);
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
  
		TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee3.value);
		TempClassGrid.setRowColData(mulLineCount,4,fm.ChequeNo3.value);
		TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeDate3.value);
		TempClassGrid.setRowColData(mulLineCount,6,'');
		TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode3.value);
		TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo3.value);
		TempClassGrid.setRowColData(mulLineCount,9,fm.AccName3.value);
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
		 alert("����������ڲ�ת�˷�ʽ�����շ�");
		 return false;
	}	

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

		TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee4.value);
		TempClassGrid.setRowColData(mulLineCount,6,'');
		TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode4.value);
		TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo4.value);
		TempClassGrid.setRowColData(mulLineCount,9,fm.AccName4.value);
		TempClassGrid.setRowColData(mulLineCount,16,fm.IDType.value);
		TempClassGrid.setRowColData(mulLineCount,17,fm.IDNo.value);
		//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName7.value);		
	}	
	if(PayMode==8)
	{
		TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee8.value);
		TempClassGrid.setRowColData(mulLineCount,6,document.all('PayDate').value);
	}	
  if(PayMode==6)
  { //alert("BBBBBBBBBBBBBBBB");
   if (verifyElement("��������|NOTNULL", document.all('BankCode6').value) != true) {
      return false;
    }
    
    
    TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee6.value);
		TempClassGrid.setRowColData(mulLineCount,6,'');
		//TempClassGrid.setRowColData(mulLineCount,6,'');
		TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode6.value);
		TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo6.value);
		TempClassGrid.setRowColData(mulLineCount,9,fm.AccName6.value);
		//TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode9.value);
		//TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo9.value);
		//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName9.value);
  }
	if(PayMode=='0')
	{
		TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee0.value);
		TempClassGrid.setRowColData(mulLineCount,6,'');
	}  
  document.all('spanTempClassGrid'+mulLineCount).all('TempClassGridSel').checked=true;

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
	var rowNum=TempClassGrid.mulLineCount;
	if (rowNum<=0)
	{
	alert("δ����Ҫ�޸ĵ����ݣ����������ӡ���");
	return false;	
	}
	var mulLineCount = TempClassGrid.getSelNo()-1;
	//var mulLineCount = TempClassGrid.getSelNo();
	var PayMode = TempClassGrid.getRowColData(mulLineCount,1);
	TempClassGrid.setRowColData(mulLineCount,1,fm.PayMode.value);
	TempClassGrid.setRowColData(mulLineCount,2,fm.PayModeName.value);
	if(PayMode==1)
	{
		TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee1.value);
		TempClassGrid.setRowColData(mulLineCount,6,document.all('PayDate').value);
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
		TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee2.value);
		TempClassGrid.setRowColData(mulLineCount,4,fm.ChequeNo2.value);
		TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeDate2.value);
		TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode2.value);
		TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo2.value);
		TempClassGrid.setRowColData(mulLineCount,9,fm.AccName2.value);
		TempClassGrid.setRowColData(mulLineCount,6,document.all('PayDate').value);
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
  
		TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee3.value);
		TempClassGrid.setRowColData(mulLineCount,4,fm.ChequeNo3.value);
		TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeDate3.value);
		TempClassGrid.setRowColData(mulLineCount,6,'');
		TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode3.value);
		TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo3.value);
		TempClassGrid.setRowColData(mulLineCount,9,fm.AccName3.value);
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
		 alert("����������ڲ�ת�˷�ʽ�����շ�");
		 return false;
	}	

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

		TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee4.value);
		TempClassGrid.setRowColData(mulLineCount,6,'');
		TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode4.value);
		TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo4.value);
		TempClassGrid.setRowColData(mulLineCount,9,fm.AccName4.value);
		TempClassGrid.setRowColData(mulLineCount,16,fm.IDType.value);
		TempClassGrid.setRowColData(mulLineCount,17,fm.IDNo.value);
		//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName7.value);		
	}	
  document.all('spanTempClassGrid'+mulLineCount).all('TempClassGridSel').checked=true;

}

function CheckPayMode()
{

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

function afterCodeSelect(cCodeName, Field) 
{
		if(cCodeName == "FINAbank" ){
			if(divPayMode2.style.display == ""){
				checkBank(document.all('BankCode2'),document.all('BankAccNo2'));
			}
			if(divPayMode3.style.display == ""){
				checkBank(document.all('BankCode3'),document.all('BankAccNo3'));
			}
			if(divPayMode6.style.display == ""){
				checkBank(document.all('BankCode6'),document.all('BankAccNo6'));
			}
  	}
  	if(cCodeName == "bank" ){
			if(divPayMode4.style.display == ""){
				checkBank(document.all('BankCode4'),document.all('BankAccNo4'));
			}
		}
	if(cCodeName == "chargepaymode")
	{
		showTempClassInput(Field.value);
		PayModePrem();
	}
	
	//ѡ��֤���ͺ��Զ�����ҵ��Ա��Ϣ
	if(cCodeName == "CardCode")
	{
		GetAgentInfo();
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

function PayModePrem()
{
  var SumPrem =0.0;
	var TempCount = TempGrid.mulLineCount;
  for(i=0;i<TempCount;i++)
  {
  	SumPrem = SumPrem+parseFloat(TempGrid.getRowColData(i,3));//���ѽ���ۼ�   
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
  
function checkBank(tBankCode,tBankAccNo){
	if(tBankCode.value.length>0 && tBankAccNo.value.length>0){
		if(!checkBankAccNo(tBankCode.value,tBankAccNo.value)){
			tBankAccNo.value = "";
			return false;
		}
	}
}