var showInfo;
var mDebug="1";
 var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var sqlresourcename = "app.ProposalSpotCheckInputSql";

//function afterCodeSelect( cName, Filed)
//{
  //if(cName=='CertifyClassListNew')
  //{
   // displayMulLine();
  
	//} 
	//else if( cName == 'CertifyHaveNumber' ) 
  //{
		//displayNumberInfo();		
  //}
//}

//function displayMulLine()
//{
  //var displayType = fm.CertifyClass.value;

  //document.all('divShow').style.display="";
  //document.all('divCardRisk').style.display="";

//}

//����ѡ�������̱����ѯ���ݿ����Ƿ����ж���Ĺ����������ʾ�ڽ����ϣ����û�У�����ʾ�Ը������û���Ѷ���ĸ��˳�����
function afterCodeSelect( cCodeName, Field ) 
{
  if(cCodeName == 'queryBPOID')
  {
//	 var strsql="select riskcode,rate,maxmum,remark,ManageCom from BPOCheckCalMode where BPOID='"+document.all('BussNo').value+"'";
	 
var sqlid1="ProposalSpotCheckInputSql1";
var mySql1=new SqlClass();
mySql1.setResourceName(sqlresourcename);
mySql1.setSqlId(sqlid1);
mySql1.addSubPara(document.all('BussNo').value);
	 
	 //prompt("",strsql);
	 var strResult= decodeEasyQueryResult(easyQueryVer3(mySql1.getString(), 1, 1, 1));  
   
     //�ж��Ƿ��ѯ�ɹ�
     if (!strResult) 
     {  
   	  	alert("�����"+document.all('BussNo').value+"��ϵͳ��û���Ѷ���ĸ��˳�����");
      	return false;
     }
     
   	 
   	 //�������Ԫ�ظ�ֵ  
   	 if(!((strResult[0][0]==null)||(strResult[0][0]=="")))
   	 {
   	    document.all('RiskCode').value=strResult[0][0];
   	 }
   	 
   	 if(!((strResult[0][1]==null)||(strResult[0][1]=="")))
   	 {
   	    document.all('checkRate').value=strResult[0][1]*100;
   	 }
   	 
   	 if(!((strResult[0][2]==null)||(strResult[0][2]=="")))
   	 {
   	    document.all('checkMax').value=strResult[0][2];
   	 }
   	 
   	 if(!((strResult[0][3]==null)||(strResult[0][3]=="")))
   	 {
   	    document.all('Remark').value=strResult[0][3];
   	 }

   	 if(!((strResult[0][4]==null)||(strResult[0][4]=="")))
   	 {
   	    document.all('ManageCom').value=strResult[0][4];
   	 }
  	}
}

//�ύ
function submitForm()
{

  if(vertifyInput() == false)
  {
	 return false;
  }
    
  fm.OperateType.value = "INSERT";
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
  //fm.action = './CertifyFeeSave.jsp';
  document.getElementById("fm").submit(); //
}


//����
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
    initForm();
  }
}


//�����ݵ����ݽ��и�ʽ��¼��Ҫ���У��
function vertifyInput()
{
  //alert("11111");
   
  if((document.all('BussNo').value=="")||(document.all('BussNo').value==null))
  {
    alert("��ѡ�����������");
    return false;
  }

  
  if((document.all('checkRate').value=="")||(document.all('checkRate').value==null))
  {
    	alert("��¼�������");
    	return false;
  }
  else
  {
        //alert("222 "+isInteger(document.all('checkRate').value));
  		if(isInteger(document.all('checkRate').value)==false||isOneToHundred(document.all('checkRate').value)==0)
  		{
  		      //alert(isOneToHundred(document.all('checkRate').value));
  		      //alert(isInteger(document.all('checkRate').value));
  			  alert("�����ʲ���0-100֮���������������¼��");
  			  return false;
  		}
  }
  
  if((document.all('checkMax').value=="")||(document.all('checkMax').value==null))
  {
    	alert("��¼��������");
    	return false;
  }
  else
  {
  		if(isInteger(document.all('checkMax').value)==false)
  		{
  		      //alert(isOneToHundred(document.all('checkRate').value));
  		      //alert(isInteger(document.all('checkRate').value));
  			  alert("������޲���������������¼��");
  			  return false;
  		}
  }
  

  
  return true;
}


//У������������Ƿ���0-100֮��
function isOneToHundred(strvalue)
{
      //alert("3= "+parseInt(strvalue));
	  if(parseInt(strvalue)>100||parseInt(strvalue)<0)
	  {
  	      return 0;
	  }
  	
  	return 1;
}


