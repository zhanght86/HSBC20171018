var showInfo;
var mDebug="1";
 var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

//����ѡ�������̱����ѯ���ݿ����Ƿ����ж���Ĺ����������ʾ�ڽ����ϣ����û�У�����ʾ�Ը������û���Ѷ���ĸ��˳�����
function queryClick() 
{
//	 var strsql="select bpoid,ManageCom,rate,maxmum from BPOCheckCalMode where 1=1 "
//	       + " and bussnotype='OF' "
//	       + getWherePart( 'BPOID','BussNo' )
//	       + getWherePart( 'rate','checkRate' )
//	       + getWherePart( 'maxmum','checkMax' )
//	       + getWherePart( 'ManageCom','ManageCom' ) ;
	 //prompt("",strsql);
	    var  BussNo = window.document.getElementsByName(trim("BussNo"))[0].value;
	    var  checkRate = window.document.getElementsByName(trim("checkRate"))[0].value;
	    var  checkMax = window.document.getElementsByName(trim("checkMax"))[0].value;
	    var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value;
  	    var  sqlid1="ProposalSpotCheckInputSql0";
  	    var  mySql1=new SqlClass();
  	    mySql1.setResourceName("finfee.ProposalSpotCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
  	    mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
  	    mySql1.addSubPara(BussNo);//ָ������Ĳ���
  	    mySql1.addSubPara(checkRate);//ָ������Ĳ���
  	    mySql1.addSubPara(checkMax);//ָ������Ĳ���
  	    mySql1.addSubPara(ManageCom);//ָ������Ĳ���
  	    var strsql=mySql1.getString();
   	 
   	 turnPage.queryModal(strsql, CheckGrid);

}

//�ύ
function save( OperateType)
{
  document.all("OperateType").value = OperateType;
  
  //var arrReturn = new Array();
	//var tSel = CheckGrid.getSelNo();
	
	
	
	document.all("BussNoa").value = document.all("BussNo").value;
	document.all("ManageComa").value = document.all("ManageCom").value;
	document.all("checkRatea").value = document.all("checkRate").value;	
	document.all("checkMaxa").value = document.all("checkMax").value;	
		
  if((document.all('BussNoa').value=="")||(document.all('BussNoa').value==null))
  {
    alert("��ѡ�������˾��");
    return false;
  }	
  		
  if(OperateType==1 &&(vertifyInput() == false))
  {
	 return false;
  }
    
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
  
  if((document.all('checkRatea').value=="")||(document.all('checkRatea').value==null))
  {
    	alert("��¼���������");
    	return false;
  }
  else
  {
        //alert("222 "+isInteger(document.all('checkRate').value));
  		if(isInteger(document.all('checkRatea').value)==false||isOneToHundred(document.all('checkRatea').value)==0)
  		{
  		      //alert(isOneToHundred(document.all('checkRate').value));
  		      //alert(isInteger(document.all('checkRate').value));
  			  alert("����������0-100֮���������������¼�룡");
  			  return false;
  		}
  }
  
  if((document.all('checkMaxa').value=="")||(document.all('checkMaxa').value==null))
  {
    	alert("��¼�������ޣ�");
    	return false;
  }
  else
  {
  		if(isInteger(document.all('checkMaxa').value)==false)
  		{
  		      //alert(isOneToHundred(document.all('checkRate').value));
  		      //alert(isInteger(document.all('checkRate').value));
  			  alert("������޲���������������¼�룡");
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


//����ѡ�������̱����ѯ���ݿ����Ƿ����ж���Ĺ����������ʾ�ڽ����ϣ����û�У�����ʾ�Ը������û���Ѷ���ĸ��˳�����
function afterCodeSelect( cCodeName, Field ) 
{
	if(cCodeName=='queryBPOID')
	{
	// var strsql="select riskcode,rate,maxmum,remark,managecom from BPOCheckCalMode where BPOID='"+document.all('BussNo').value+"'";
	    var  sqlid2="ProposalSpotCheckInputSql1";
	    var  mySql2=new SqlClass();
	    mySql2.setResourceName("finfee.ProposalSpotCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
	    mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	    mySql2.addSubPara(document.all('BussNo').value);//ָ������Ĳ���
	    var strsql=mySql2.getString();
	 //prompt("",strsql);
	 var strResult= decodeEasyQueryResult(easyQueryVer3(strsql, 1, 1, 1));  
   
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