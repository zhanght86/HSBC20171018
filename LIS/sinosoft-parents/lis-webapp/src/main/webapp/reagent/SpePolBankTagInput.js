 //               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var saveClick=false;
var arrDataSet;
var turnPage = new turnPageClass();
var conftype= "";


window.onfocus=myonfocus;

//�Ƿ�Ϊ����
function isdigit(c){
  return(c>='0'&&c<='9');
}
//�ַ�ת������
function atoi(s){
  var t=0;
  for(var i=0;i<s.length;i++){
    var c=s.charAt(i);
	if(!isdigit(c))return t;
	else t=t*10+(c-'0');
  } 
  return t;  
}

//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();  
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}


//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  //showSubmitFrame(mDebug);
  document.getElementById("fm").submit(); //�ύ
 
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content ,confirmflag )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
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

    //ִ����һ������ 
    if (fm.action == './SpePolBankTagSave.jsp') 
    {     
    	//alert("confirmflag"+confirmflag);      
       PolConfirm();
       saveClick=true;
    }  
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

//������������
function PolConfirm()
{
	var tStartDate = document.all('StartDate').value;
	var tEndDate = document.all('EndDate').value;
	
  if( ((document.all('MainPolNo').value=="")||(document.all('MainPolNo').value==null)) 
    &&((document.all('ContNo').value=="")||(document.all('ContNo').value==null)) 
    &&((document.all('ManageCom').value=="")||(document.all('ManageCom').value==null)) )
   {
    alert("�������ֺ�,�����ż������������ͬʱΪ��!!");	
    document.all('MainPolNo').focus();
    return ;
   }
  var sub_str=" ";
  if(document.all('ContNo').value!=""&& document.all('ContNo').value!=null) 
  {
  	sub_str = sub_str + " and a.ContNo ='"+document.all('ContNo').value+"'";
  }

  if(document.all('ManageCom').value!=""&& document.all('ManageCom').value!=null) 
  {
  	sub_str = sub_str + " and a.managecom  like '"+document.all('ManageCom').value+"%%'";
  }

  if(document.all('AgentCode').value!=""&& document.all('AgentCode').value!=null) 
  {
  	sub_str = sub_str + " and a.agentcode ='"+document.all('AgentCode').value+"'";
  }

  if(document.all('StartDate').value!=""&& document.all('StartDate').value!=null) 
  {
  	sub_str = sub_str + " and b.startpaydate>='"+document.all('StartDate').value+"'";
  }

  if(document.all('EndDate').value!=""&& document.all('EndDate').value!=null) 
  {
  	sub_str = sub_str + " and b.startpaydate<='"+document.all('EndDate').value+"'";
  }
  var strSQL = "";  
  var tReturn = parseManageComLimitlike();
  //�����Ų�Ϊ��ʱ�Ա�����Ϊ׼������ContNo,managecom����������
  if((document.all('MainPolNo').value!="")&&(document.all('MainPolNo').value!=null)) 
  {
  	//strSQL=" select (select shortname from ldcom where comcode=a.managecom),a.mainpolno,a.ContNo,a.cvalidate, "
    //  +"  a.appntname,a.prem,(select codename from ldcode where code=c.bankcode and codetype='bank'),c.bankaccno from lcpol a ,ljspay b ,lccont c"
    //  +" where a.appflag=1 and exists (select 1 from lccontstate where contno=a.contno and polno=a.polno and statetype='Available' and state='0' and enddate is null ) "
    //  +" and (b.paytypeflag is null or (b.paytypeflag = '1' and exists(select 1 from lyrenewbankinfo where getnoticeno = b.getnoticeno and paytodate = b.startpaydate and state = '0') ))"
    //  +" and a.polno=a.mainpolno and a.contno=b.otherno and a.contno=c.contno and b.bankonthewayflag='0' and b.sumduepaymoney>0 and (b.payform='0' or b.payform is null)"
    //  +" and c.bankcode is not null and not exists( select 1 from ljtempfee where tempfeeno=b.getnoticeno) and a.mainpolno='"+document.all('MainPolNo').value+"' "
    //  ;
    strSQL = wrapSql('SpePolBankTagInputSql1',[document.all('MainPolNo').value]);

  }
  else
  {
  	//strSQL=" select (select shortname from ldcom where comcode=a.managecom),a.mainpolno,a.ContNo,a.cvalidate, "
    //  +"  a.appntname,a.prem,(select codename from ldcode where code=c.bankcode and codetype='bank'),c.bankaccno from lcpol a ,ljspay b ,lccont c"
    //  +" where a.appflag=1  and exists (select 1 from lccontstate where contno=a.contno and polno=a.polno and statetype='Available' and state='0' and enddate is null ) "
    //  +" and (b.paytypeflag is null or (b.paytypeflag = '1' and exists(select 1 from lyrenewbankinfo where getnoticeno = b.getnoticeno and paytodate = b.startpaydate and state = '0') ))"
    //  +" and a.polno=a.mainpolno and a.contno=b.otherno and a.contno=c.contno and b.bankonthewayflag='0' and b.sumduepaymoney>0 and (b.payform='0' or b.payform is null) "
    //  +" and c.bankcode is not null and not exists( select 1 from ljtempfee where tempfeeno=b.getnoticeno) "
    //  + sub_str +" order by a.polno";
    
    strSQL = wrapSql('SpePolBankTagInputSql2',[sub_str]);   
  	
  }   
  //prompt("",strSQL);
  var strQueryResult = easyQueryVer3(strSQL, 1, 1, 1);
  if (! strQueryResult) 
  {
     alert("��ѯ����ʧ�ܣ�ԭ���������:1,������ʧЧ����δ����;2,���뱣���ŷ����ձ�����;3,ת�ʷ�ʽ������ת��;4,�����Ѿ��е����ݽ��Ѽ�¼;5,����������;��¼;6,����δȷ�ϡ�");
     document.all("MainPolNo").value="";
     document.all("ContNo").value="";
     document.all("ManageCom").value="";
     document.all("AgentCode").value="";
     document.all("StartDate").value="";
     document.all("EndDate").value="";
     
     clearMulLine();
     return ;
  } 

  if(!query(strSQL))
  {
  	return false;
  }		 
}   


function submitSave()
{   
  
  var tempObj = document.all('AscriptionGridNo'); //�����ڱ�fm��
  if (tempObj == null)
  {
     alert('û��ѡ�еı�����');
     return ;
  }
  var lineCount = AscriptionGrid.mulLineCount;
  var str='';
   
  fm.action="./SpePolBankTagSave.jsp";
  submitForm();
  return ;
}

function clearMulLine()
{  
   AscriptionGrid.clearData("AscriptionGrid");
   saveClick=false;
}

function query( strSQL )
{
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  //alert(strSQL);
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) 
  {
    	alert("�Ҳ�������������Ӧ�ռ�¼��")
    AscriptionGrid.clearData("AscriptionGrid");
    return ;
  }
  
//��ѯ�ɹ������ַ��������ض�ά����
  arrDataSet = decodeEasyQueryResult(turnPage.strQueryResult);
  turnPage.arrDataCacheSet = chooseArray(arrDataSet,[0,1,2,3,4,5,6,7]);

  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = AscriptionGrid;              
  //����SQL���
  turnPage.strQuerySql     = strSQL;   
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;    
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  //arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  var tArr = new Array();
  tArr = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //����MULTILINE������ʾ��ѯ���  
  displayMultiline(tArr, turnPage.pageDisplayGrid);
	
}