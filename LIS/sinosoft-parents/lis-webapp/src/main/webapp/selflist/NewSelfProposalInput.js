//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var mOperate = 0;
var mAction = "";
var arrResult = new Array();
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var showInfo;
var queryflag=0;////��ѯ��־�������ڵ�����ذ�ťǰ��������ѯ��ť���鿴һ�����صļ�¼:0��ʾδ�����1��ʾ����˼�¼�����Ҳ�ѯ���˼�¼


//����Ͷ����ְҵ��ѡ��������е���ӦԪ�ظ�ֵ
function afterAppntLOccupationSelect()
{
	var vRow = AppntCodeGrid.getSelNo();
	
	if( vRow == null || vRow == 0 ) 
	{
		return;
	}
	
	vRow = vRow - 1;
	
	//����ѡ�еļ�¼�������ְҵ��Ϣ��ֵ
	fm.AppntOccupationType.value 		= AppntCodeGrid.getRowColData(vRow, 1);
	fm.AppntOccupationName.value 		= AppntCodeGrid.getRowColData(vRow, 2);
	fm.AppntWorkName.value 				= AppntCodeGrid.getRowColData(vRow, 3);
	fm.AppntOccupationCode.value 		= AppntCodeGrid.getRowColData(vRow, 4);
	
	//alert(fm.AppntOccupationType.value);
	//alert(fm.AppntOccupationCode.value);
	
}



//��ѯͶ����ְҵ���룬ְҵ���ְҵ������Ϣ
function queryAppntOccupation()
{

  	if((document.all( 'AppntOccupationName' ).value==null||document.all( 'AppntOccupationName' ).value=="")&&(document.all( 'AppntWorkName' ).value==null||document.all( 'AppntWorkName' ).value==""))
	{
		 alert("������ְҵ���ƣ���ҵ����������һ��������������ģ����ѯ!");
		 return false;
	}
	
	var str="";
	
	//����ģ��������ѯְҵ��Ϣ
//	var strSQL="select OccupationType,OccupationName,WorkName,Occupationcode from LDOccupation where occupationver='002'";
	
	
	
	
	//ģ�����������û��������������ģ����ѯ
	if(!(document.all( 'AppntOccupationName' ).value==null||document.all( 'AppntOccupationName' ).value==""))
	{
		strSQL=strSQL+" and OccupationName like  '%25" + document.all( 'AppntOccupationName' ).value + "%25'" ;
	}
	
	if(!(document.all( 'AppntWorkName' ).value==null||document.all( 'AppntWorkName' ).value==""))
	{
		strSQL=strSQL+" and Workname like  '%25" + document.all( 'AppntWorkName' ).value + "%25'" ;
	}

	
	var sqlid1="NewSelfProposalInputSql1";
 	var mySql1=new SqlClass();
 	mySql1.setResourceName("selflist.NewSelfProposalInputSql");
 	mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
 	mySql1.addSubPara(document.all( 'AppntOccupationName' ).value);//����ָ������
 	mySql1.addSubPara(document.all( 'AppntWorkName' ).value);
 	
 	
 	var strSQL = mySql1.getString();
	

    //prompt("",strSQL);
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1); 
  
   //�ж��Ƿ��ѯ�ɹ�
   if (!turnPage.strQueryResult) 
   {
  		queryflag=0;
    	alert("û����Ҫ��ѯ��ְҵ��Ϣ��");
    	return false;
   }
    
   queryflag=1;
   //��ѯ�ɹ������ַ��������ض�ά����
   turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
   //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
   turnPage.pageDisplayGrid = AppntCodeGrid;    
          
   //����SQL���
   turnPage.strQuerySql     = strSQL; 
  
   //���ò�ѯ��ʼλ��
   turnPage.pageIndex       = 0;  
  
   //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
   arrDataSet= turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
   //tArr=chooseArray(arrDataSet,[0]) 
   //����MULTILINE������ʾ��ѯ���
  
   displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
   //displayMultiline(tArr, turnPage.pageDisplayGrid);
}

//���ݱ�������ְҵ��ѡ��������е���ӦԪ�ظ�ֵ
function afterLOccupationSelect()
{
	var vRow = LCInsuredCodeGrid.getSelNo();
	
	if( vRow == null || vRow == 0 ) 
	{
		return;
	}
	
	vRow = vRow - 1;
	
	//����ѡ�еļ�¼�������ְҵ��Ϣ��ֵ
	fm.LCInsuredOccupationType.value 		= LCInsuredCodeGrid.getRowColData(vRow, 1);
	fm.LCInsuredOccupationName.value 		= LCInsuredCodeGrid.getRowColData(vRow, 2);
	fm.LCInsuredWorkName.value 				= LCInsuredCodeGrid.getRowColData(vRow, 3);
	fm.LCInsuredOccupationCode.value 		= LCInsuredCodeGrid.getRowColData(vRow, 4);
	
	//alert(fm.LCInsuredOccupationCode.value);
	
}

function afterInsuredSelect()
{
	var vRow = LCInsuredGrid.getSelNo();
	
	if( vRow == null || vRow == 0 ) 
	{
		return;
	}
	
	vRow = vRow - 1;
	fm.LCInsuredName.value 		= LCInsuredGrid.getRowColData(vRow, 1);
	fm.LCInsuredSex.value 		= LCInsuredGrid.getRowColData(vRow, 2);
	fm.LCInsuredBirthday.value 				= LCInsuredGrid.getRowColData(vRow, 3);
	fm.LCInsuredIDType.value 		= LCInsuredGrid.getRowColData(vRow, 4);
	fm.LCInsuredIDNo.value 		= LCInsuredGrid.getRowColData(vRow, 5);
	fm.LCInsuredOccupationType.value 		= LCInsuredGrid.getRowColData(vRow, 6);
	fm.LCInsuredOccupationCode.value 				= LCInsuredGrid.getRowColData(vRow, 7);
	fm.LCInsuredPostalAddress.value 		= LCInsuredGrid.getRowColData(vRow, 8);
	fm.LCInsuredZipCode.value 		= LCInsuredGrid.getRowColData(vRow, 9);
	fm.LCInsuredPhone.value 		= LCInsuredGrid.getRowColData(vRow, 10);
	fm.LCInsuredEMail.value 				= LCInsuredGrid.getRowColData(vRow, 11);
	fm.RelationToAppnt.value 				= LCInsuredGrid.getRowColData(vRow, 12);
}

//��ѯ��������ְҵ���룬ְҵ���ְҵ������Ϣ
function queryLcInsuredOccupation()
{

  	if((document.all( 'LCInsuredOccupationName' ).value==null||document.all( 'LCInsuredOccupationName' ).value=="")&&(document.all( 'LCInsuredWorkName' ).value==null||document.all( 'LCInsuredWorkName' ).value==""))
	{
		 alert("������ְҵ���ƣ���ҵ����������һ��������������ģ����ѯ!");
		 return false;
	}
	
	var str="";
	
	//����ģ��������ѯְҵ��Ϣ
//	var strSQL="select OccupationType,OccupationName,WorkName,Occupationcode from LDOccupation where occupationver='002'";
	
	
	//ģ�����������û��������������ģ����ѯ
	if(!(document.all( 'LCInsuredOccupationName' ).value==null||document.all( 'LCInsuredOccupationName' ).value==""))
	{
		strSQL=strSQL+" and OccupationName like  '%25" + document.all( 'LCInsuredOccupationName' ).value + "%25'" ;
	}
	
	if(!(document.all( 'LCInsuredWorkName' ).value==null||document.all( 'LCInsuredWorkName' ).value==""))
	{
		strSQL=strSQL+" and Workname like  '%25" + document.all( 'LCInsuredWorkName' ).value + "%25'" ;
	}

	
	var sqlid2="NewSelfProposalInputSql2";
 	var mySql2=new SqlClass();
 	mySql2.setResourceName("selflist.NewSelfProposalInputSql");
 	mySql2.setSqlId(sqlid2); //ָ��ʹ��SQL��id
 	mySql2.addSubPara(document.all( 'LCInsuredOccupationName' ).value);//����ָ������
 	mySql2.addSubPara(document.all( 'LCInsuredWorkName' ).value);
 	var strSQL = mySql2.getString();
	
	

    //prompt("",strSQL);
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1); 
  
   //�ж��Ƿ��ѯ�ɹ�
   if (!turnPage.strQueryResult) 
   {
  		queryflag=0;
    	alert("û����Ҫ��ѯ��ְҵ��Ϣ��");
    	return false;
   }
    
   queryflag=1;
   //��ѯ�ɹ������ַ��������ض�ά����
   turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
   //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
   turnPage.pageDisplayGrid = LCInsuredCodeGrid;    
          
   //����SQL���
   turnPage.strQuerySql     = strSQL; 
  
   //���ò�ѯ��ʼλ��
   turnPage.pageIndex       = 0;  
  
   //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
   arrDataSet= turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
   //tArr=chooseArray(arrDataSet,[0]) 
   //����MULTILINE������ʾ��ѯ���
  
   displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
   //displayMultiline(tArr, turnPage.pageDisplayGrid);
}


//ѡ��ָ����Ч���ڸ�ѡ��ʱ�����Ĳ���
function callCValiDate(ttype)
{
    //alert(ttype);
	switch (ttype)
	{  
	        case "01" : 
	            document.all("CValiDate").readOnly = false;
	            document.all("CValiDate").value = "";
	        default :
	            return;
	}	
}


/*********************************************************************
 *  ѡ���뱻���˹�ϵ��Ķ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterCodeSelect(cCodeName, Field) 
{
	if(cCodeName == "RelationToLCInsured") 
	{  
	    //alert(Field.value);
	    //���"�����˹�ϵѡ��Ϊ���׻�ĸ��,����ʾ�����˹�ϵ"
	    if(Field.value=="00")
	    {
			divLCInsure1.style.display="none";  	
	    }
	    else
	    {
	        divLCInsure1.style.display="";   
	    }

	}
}


//Ͷ���˿ͻ��Ų�ѯ��Ť�¼�
function queryAppntNo() 
{
  if (document.all("AppntCustomerNo").value == "")
   {
      //�򿪿ͻ���Ϣ��ѯ����
      showAppnt1();
  } 
  else 
  {
//    var sql="select CustomerNo,name,sex,birthday,idtype,idno,PostalAddress,ZipCode,Mobile,Email,OccupationType,OccupationCode from LDPerson where CustomerNo = '" + document.all("AppntCustomerNo").value + "'";
    
    var sqlid3="NewSelfProposalInputSql3";
 	var mySql3=new SqlClass();
 	mySql3.setResourceName("selflist.NewSelfProposalInputSql");
 	mySql3.setSqlId(sqlid3); //ָ��ʹ��SQL��id
 	mySql3.addSubPara(document.all("AppntCustomerNo").value);//ָ���������
 	var sql = mySql3.getString();
    
    prompt("����Ͷ���˿ͻ��Ų�ѯ�ͻ���Ϣ",sql);
    arrResult = easyExecSql(sql, 1, 0);
    
    if (arrResult == null) 
    {
      alert("δ�鵽Ͷ������Ϣ");
      displayAppnt(new Array());
    } 
    else 
    {
      displayAppnt(arrResult[0]);
    }
  }
}


//�򿪿ͻ���Ϣ��ѯ����,���пͻ���Ϣ��ѯ*********************************************************************
function showAppnt1()
{
	if( mOperate == 0 )
	{
		mOperate = 1;
		showInfo = window.open( "../sys/LDPersonQuery.html" );
	}
}



/*********************************************************************
 *  �������е�������ʾ��Ͷ���˲���
 *  ����  ��  ���˿ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayAppnt(cArr) 
{
	// ��LDPerson��ȡ����
	try
	{
	  	document.all( 'AppntCustomerNo' ).value          = cArr[0];
  		document.all( 'AppntName' ).value                = cArr[1];
  		document.all( 'AppntSex' ).value                 = cArr[2];
  		document.all( 'AppntBirthday' ).value            = cArr[3];
  		document.all( 'AppntIDType' ).value              = cArr[4];
  		document.all( 'AppntIDNo' ).value                = cArr[5];
  		document.all( 'AppntPostalAddress' ).value       = cArr[6];
  		document.all( 'AppntZipCode' ).value             = cArr[7];
  		document.all( 'AppntPhone' ).value              = cArr[8];
  		document.all( 'AppntEMail' ).value               = cArr[9];
  		document.all( 'AppntOccupationType' ).value      = cArr[10];
  		document.all( 'AppntOccupationCode' ).value      = cArr[11];
	}
	catch(ex)
	{
	   alert("����Ͷ������Ϣ����");
	}
}




/*********************************************************************
 *  ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
 *  ����  ��  ��ѯ���صĶ�ά����
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterQuery( arrQueryResult ) 
{
	if( arrQueryResult != null )
	 {
		arrResult = arrQueryResult;
		
		if( mOperate == 1 ) 
		{		// Ͷ������Ϣ	  
			
//			var sql="select CustomerNo,name,sex,birthday,idtype,idno,PostalAddress,ZipCode,Mobile,Email,OccupationType,OccupationCode from LDPerson where CustomerNo = '" + arrQueryResult[0][0] + "'";
			
			var sqlid4="NewSelfProposalInputSql4";
		 	var mySql4=new SqlClass();
		 	mySql4.setResourceName("selflist.NewSelfProposalInputSql");
		 	mySql4.setSqlId(sqlid4); //ָ��ʹ��SQL��id
		 	mySql4.addSubPara(arrQueryResult[0][0]);//ָ���������
		 	var sql = mySql4.getString();
			
			arrResult = easyExecSql(sql, 1, 0);
			if (arrResult == null)
			 {
			  alert("δ�鵽Ͷ������Ϣ");
			} 
			else 
			{
			   displayAppnt(arrResult[0]);
			}

	   }
	   
		if( mOperate == 2 )	
		{		// ����������Ϣ
//			var sql="select CustomerNo,name,sex,birthday,idtype,idno,PostalAddress,ZipCode,Mobile,Email,OccupationType,OccupationCode from LDPerson where CustomerNo = '" + arrQueryResult[0][0] + "'"
			
			var sqlid5="NewSelfProposalInputSql5";
		 	var mySql5=new SqlClass();
		 	mySql5.setResourceName("selflist.NewSelfProposalInputSql");
		 	mySql5.setSqlId(sqlid5); //ָ��ʹ��SQL��id
		 	mySql5.addSubPara(arrQueryResult[0][0]);//ָ���������
		 	var sql = mySql5.getString();
			
			arrResult = easyExecSql(sql, 1, 0);
			if (arrResult == null) 
			{
			  alert("δ�鵽����������Ϣ");
			} 
			else
			{
			   displayInsured(arrResult[0]);
			}

	    }

	}
	mOperate = 0;		// �ָ���̬

}






//*************************************************************
//�����˿ͻ��Ų�ѯ��Ť�¼�
function queryInsuredNo()
 {
 
   if (document.all("LCInsuredCustomerNo").value == "") 
   {
      showInsured1();
   }  
   else 
   {
//     var sql="select CustomerNo,name,sex,birthday,idtype,idno,PostalAddress,ZipCode,Mobile,Email,OccupationType,OccupationCode from LDPerson where CustomerNo = '" + document.all("LCInsuredCustomerNo").value + "'";
    
        var sqlid6="NewSelfProposalInputSql6";
	 	var mySql6=new SqlClass();
	 	mySql6.setResourceName("selflist.NewSelfProposalInputSql");
	 	mySql6.setSqlId(sqlid6); //ָ��ʹ��SQL��id
	 	mySql6.addSubPara(document.all("LCInsuredCustomerNo").value);//ָ���������
	 	var sql = mySql6.getString();
     
     //prompt("����Ͷ���˿ͻ��Ų�ѯ�ͻ���Ϣ",sql);
     arrResult = easyExecSql(sql, 1, 0);
    
     if (arrResult == null) 
     {
        alert("δ�鵽��������Ϣ");
     } 
     else 
     {
       displayInsured(arrResult[0]);
     }
  }
}


//*********************************************************************
function showInsured1()
{
	if( mOperate == 0 )
	{
		mOperate = 2;
		showInfo = window.open( "../sys/LDPersonQuery.html" );
	}
} 


/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ�������ʾ�������˲���
 *  ����  ��  �ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayInsured(cArr) 
{
	// ��LDPerson��ȡ����
	try
	{
	  	document.all( 'LCInsuredCustomerNo' ).value          = cArr[0];
  		document.all( 'LCInsuredName' ).value                = cArr[1];
  		document.all( 'LCInsuredSex' ).value                 = cArr[2];
  		document.all( 'LCInsuredBirthday' ).value            = cArr[3];
  		document.all( 'LCInsuredIDType' ).value              = cArr[4];
  		document.all( 'LCInsuredIDNo' ).value                = cArr[5];
  		document.all( 'LCInsuredPostalAddress' ).value       = cArr[6];
  		document.all( 'LCInsuredZipCode' ).value             = cArr[7];
  		document.all( 'LCInsuredPhone' ).value              = cArr[8];
  		document.all( 'LCInsuredEMail' ).value               = cArr[9];
  		document.all( 'LCInsuredOccupationType' ).value      = cArr[10];
  		document.all( 'LCInsuredOccupationCode' ).value      = cArr[11];
	}
	catch(ex)
	{
	   alert("���ñ�������Ϣ����");
	}
}


/*********************************************************************
 *  У����������ݷǿ�,�������Ͷ�����뱻���˵Ĺ�ϵ�Ǹ�ĸ,������,�Ա�,�������ڲ���ȫ����ͬ,�����϶���ͬһ��
 *  ����  ��  �ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function checkAppnt()
{
    //�����ѡ��ָ����Ч����,�����ѡ����Ч����
    //alert(document.all('CValiDateType').checked);
    
    //ָ����Ч���ڱ�־����д��Ч���ڱ���ͬʱ����,Ҫ��д��Ҫ��д
    if(document.all('CValiDateType').checked)
    {
        if(document.all( 'CValiDate' ).value==null||document.all( 'CValiDate' ).value=="")
        {
        	alert("��ѡ����Ч����!");
	    	return false;
        }
    }
    
    if(!(document.all( 'CValiDate' ).value==null||document.all( 'CValiDate' ).value==""))
    {
    	if(!document.all('CValiDateType').checked)
    	{
        	alert("�빴ѡָ����Ч���ڱ�־!");
	    	return false;
    	}
    }
    
	 //Ͷ������ϢУ��
		if(document.all( 'AppntName' ).value==null||document.all( 'AppntName' ).value=="")
		{
		    alert("������Ͷ��������!");
		    return false;
		}
	if(document.all( 'AppntSex' ).value==null||document.all( 'AppntSex' ).value=="")
		{
		    alert("��ѡ��Ͷ�����Ա�!");
		    return false;
		}
	if(document.all( 'AppntSex' ).value=='2')
		{
		    alert("Ͷ�����Ա���Ϊ2-����!");
		    return false;
		}
	if(document.all( 'AppntBirthday' ).value==null||document.all( 'AppntBirthday' ).value=="")
		{
		    alert("��ѡ��Ͷ���˵ĳ�������!");
		    return false;
		}
	if(document.all( 'AppntIDType' ).value==null||document.all( 'AppntIDType' ).value=="")
		{
		    alert("��ѡ��Ͷ���˵�֤������!");
		    return false;
		}
	//��֤��ʱ����¼��֤����
		if(document.all( 'AppntIDType' ).value!=9)
		{
			if(document.all( 'AppntIDNo' ).value==null||document.all( 'AppntIDNo' ).value=="")
			{
		    	alert("������Ͷ���˵�֤������!");
		    	return false;
			}
		}
		else
		{
			alert("Ͷ����֤�����Ͳ���Ϊ9-��֤��!");
		    return false;
		}
	if(document.all( 'AppntIDType' ).value=="0")
		{
		    if(document.all( 'AppntIDNo' ).value.length!=15&&document.all( 'AppntIDNo' ).value.length!=18)
		    {
		    	alert("���֤�ű�����15��18λ��!");
		    	return false;
		    }
		}
	if(document.all( 'AppntPhone' ).value==null||document.all( 'AppntPhone' ).value=="")
		{
		    alert("������Ͷ���˵���ϵ�绰!");
		    return false;
		}
		if(document.all( 'AppntZipCode' ).value==null||document.all( 'AppntZipCode' ).value=="")
		{
		    alert("������Ͷ������ϵ��ַ���ڵص��ʱ�!");
		    return false;
		}

		
		if(document.all( 'AppntZipCode' ).value.length!=6)
		{
	    	alert("�ʱ೤�ȱ�����6λ!");
	    	return false;
		}

		if(!doCheckNumber(document.all('AppntZipCode' ).value))
		{
			alert("�ʱ����������");
			return false;
		}
    return true;
}
function checkGrid(){
	var vRow = LCInsuredGrid.mulLineCount;
	
	if( vRow == null || vRow == 0 ) 
	{
		alert("��¼�뱻������Ϣ");
		return;
	}
	if(vRow!=fm.Peoples.value){
		alert("¼�뱻������������Ӧ¼��"+fm.Peoples.value+"�������ˣ�ʵ¼"+vRow+"��������");
		return;
	}
	vRow = vRow -1 ;
	for(var i = 0;i<=vRow;i++){
		var tLCInsuredName 		= LCInsuredGrid.getRowColData(i, 1);
		var tLCInsuredSex 		= LCInsuredGrid.getRowColData(i, 2);
		var tLCInsuredBirthday 				= LCInsuredGrid.getRowColData(i, 3);
		var tLCInsuredIDType 		= LCInsuredGrid.getRowColData(i, 4);
		var tLCInsuredIDNo 		= LCInsuredGrid.getRowColData(i, 5);
		var tLCInsuredOccupationType 		= LCInsuredGrid.getRowColData(i, 6);
		var tLCInsuredOccupationCode 				= LCInsuredGrid.getRowColData(i, 7);
		var tLCInsuredPostalAddress 		= LCInsuredGrid.getRowColData(i, 8);
		var tLCInsuredZipCode 		= LCInsuredGrid.getRowColData(i, 9);
		var tLCInsuredPhone 		= LCInsuredGrid.getRowColData(i, 10);
		var tLCInsuredEMail 				= LCInsuredGrid.getRowColData(i, 11);
		var tRelationToAppnt 				= LCInsuredGrid.getRowColData(i, 12);
	
			 	//��������ϢУ��
	  if(tLCInsuredName==null||tLCInsuredName=="")
		{
	    	alert("�����뱻��������!");
	    	return false;
		}
	
		if(tRelationToAppnt==null||tRelationToAppnt=="")
		{
	    	alert("��ѡ����Ͷ���˹�ϵ!");
	    	return false;
		}
		if(tLCInsuredSex==null||tLCInsuredSex=="")
		{
	    	alert("��ѡ�񱻱����Ա�!");
	    	return false;
		}
		else if(tLCInsuredSex=='2')
		{
			alert("�������Ա���Ϊ2-����!");
	    	return false;
		}
		if(tLCInsuredBirthday==null||tLCInsuredBirthday=="")
		{
		    alert("��ѡ�񱻱��˵ĳ�������!");
		    return false;
		}
		
		if(tLCInsuredIDType==null||tLCInsuredIDType=="")
		{
		    alert("��ѡ�񱻱��˵�֤������!");
		    return false;
		}
	
		if(tLCInsuredIDType!=9)
		{
		  if(tLCInsuredIDNo==null||tLCInsuredIDNo=="")
			{
		    	alert("�����뱻���˵�֤������!");
		    	return false;
			}
		}
		else
		{
			alert("������֤�����Ͳ���Ϊ9-��֤��!");
		    return false;
		}
		
		//���֤������ѡ��Ϊ���֤,��֤�����ͱ���Ҫ��15-18λ��
		if(tLCInsuredIDType=="0")
		{
	    	if(tLCInsuredIDNo.length!=15&&tLCInsuredIDNo.length!=18)
	    	{
	    		alert("���֤�ű�����15��18λ��!");
	    		return false;
	    	}
		}
	
		if(tLCInsuredPostalAddress==null||tLCInsuredPostalAddress=="")
		{
		    alert("�����뱻���˵���ϵ��ַ!");
		    return false;
		}
		
		if(tLCInsuredZipCode==null||tLCInsuredZipCode=="")
		{
		    alert("�����뱻���˵���ϵ��ַ���ڵص��ʱ�!");
		    return false;
		}

		
		if(tLCInsuredZipCode.length!=6)
		{
	    	alert("�ʱ೤�ȱ�����6λ!");
	    	return false;
		}

		if(!doCheckNumber(tLCInsuredZipCode))
		{
			alert("�ʱ����������");
			return false;
		}
		
		if(tLCInsuredPhone==null||tLCInsuredPhone=="")
		{
		    alert("�����뱻���˵���ϵ�绰!");
		    return false;
		}
		
		
	}//ѭ������
	return true;
}
function checkInsured(){

		var tLCInsuredName 		= fm.LCInsuredName.value;
		var tLCInsuredSex 		= fm.LCInsuredSex.value;
		var tLCInsuredBirthday 				= fm.LCInsuredBirthday.value;
		var tLCInsuredIDType 		= fm.LCInsuredIDType.value;
		var tLCInsuredIDNo 		= fm.LCInsuredIDNo.value;
		var tLCInsuredOccupationType 		= fm.LCInsuredOccupationType.value;
		var tLCInsuredOccupationCode 				= fm.LCInsuredOccupationCode.value;
		var tLCInsuredPostalAddress 		= fm.LCInsuredPostalAddress.value;
		var tLCInsuredZipCode 		= fm.LCInsuredZipCode.value;
		var tLCInsuredPhone 		= fm.LCInsuredPhone.value;
		var tLCInsuredEMail 				= fm.LCInsuredEMail.value;
		var tRelationToAppnt 				= fm.RelationToAppnt.value;
	
			 	//��������ϢУ��
	  if(tLCInsuredName==null||tLCInsuredName=="")
		{
	    	alert("�����뱻��������!");
	    	return false;
		}
	
		if(tRelationToAppnt==null||tRelationToAppnt=="")
		{
	    	alert("��ѡ����Ͷ���˹�ϵ!");
	    	return false;
		}
		if(tLCInsuredSex==null||tLCInsuredSex=="")
		{
	    	alert("��ѡ�񱻱����Ա�!");
	    	return false;
		}
		else if(tLCInsuredSex=='2')
		{
			alert("�������Ա���Ϊ2-����!");
	    	return false;
		}
		if(tLCInsuredBirthday==null||tLCInsuredBirthday=="")
		{
		    alert("��ѡ�񱻱��˵ĳ�������!");
		    return false;
		}
		
		if(tLCInsuredIDType==null||tLCInsuredIDType=="")
		{
		    alert("��ѡ�񱻱��˵�֤������!");
		    return false;
		}
	
		if(tLCInsuredIDType!=9)
		{
		  if(tLCInsuredIDNo==null||tLCInsuredIDNo=="")
			{
		    	alert("�����뱻���˵�֤������!");
		    	return false;
			}
		}
		else
		{
			alert("������֤�����Ͳ���Ϊ9-��֤��!");
		    return false;
		}
		
		//���֤������ѡ��Ϊ���֤,��֤�����ͱ���Ҫ��15-18λ��
		if(tLCInsuredIDType=="0")
		{
	    	if(tLCInsuredIDNo.length!=15&&tLCInsuredIDNo.length!=18)
	    	{
	    		alert("���֤�ű�����15��18λ��!");
	    		return false;
	    	}
		}
	
		if(tLCInsuredPostalAddress==null||tLCInsuredPostalAddress=="")
		{
		    alert("�����뱻���˵���ϵ��ַ!");
		    return false;
		}
		
		if(tLCInsuredZipCode==null||tLCInsuredZipCode=="")
		{
		    alert("�����뱻���˵���ϵ��ַ���ڵص��ʱ�!");
		    return false;
		}

		
		if(tLCInsuredZipCode.length!=6)
		{
	    	alert("�ʱ೤�ȱ�����6λ!");
	    	return false;
		}

		if(!doCheckNumber(tLCInsuredZipCode))
		{
			alert("�ʱ����������");
			return false;
		}
		
		if(tLCInsuredPhone==null||tLCInsuredPhone=="")
		{
		    alert("�����뱻���˵���ϵ�绰!");
		    return false;
		}
		
	return true;
}
function doCheckNumber(str)
{ 
    //alert(str);
	for(var i=0; i<str.length; i++)
	{
		if(str.charAt(i)<'0' || str.charAt(i)>'9')
		{
			return false;
		}
	}
	
	return true;
}

function AddInsured()
{
	if(!checkInsured()) return;
	LCInsuredGrid.addOne();
	var maxLine = LCInsuredGrid.mulLineCount;

	LCInsuredGrid.setRowColData(maxLine-1,1,fm.LCInsuredName.value);
	LCInsuredGrid.setRowColData(maxLine-1,2,fm.LCInsuredSex.value);
	LCInsuredGrid.setRowColData(maxLine-1,3,fm.LCInsuredBirthday.value);
	LCInsuredGrid.setRowColData(maxLine-1,4,fm.LCInsuredIDType.value);
	LCInsuredGrid.setRowColData(maxLine-1,5,fm.LCInsuredIDNo.value);
	LCInsuredGrid.setRowColData(maxLine-1,6,fm.LCInsuredOccupationType.value);
	LCInsuredGrid.setRowColData(maxLine-1,7,fm.LCInsuredOccupationCode.value);
	LCInsuredGrid.setRowColData(maxLine-1,8,fm.LCInsuredPostalAddress.value);
	LCInsuredGrid.setRowColData(maxLine-1,9,fm.LCInsuredZipCode.value);
	LCInsuredGrid.setRowColData(maxLine-1,10,fm.LCInsuredPhone.value);
	LCInsuredGrid.setRowColData(maxLine-1,11,fm.LCInsuredEMail.value);
	LCInsuredGrid.setRowColData(maxLine-1,12,fm.RelationToAppnt.value);
	
	fm.EditPeoples.value=maxLine;
}

function RemoveInsured()
{
	var vRow = LCInsuredGrid.getSelNo();
	
	if( vRow == null || vRow == 0 ) 
	{
		return;
	}
	vRow = vRow -1 ;

	LCInsuredGrid.delRadioTrueLine();
	
	fm.EditPeoples.value=LCInsuredGrid.mulLineCount;
}

function UpdateInsured()
{
	if(!checkInsured()) return;
	var vRow = LCInsuredGrid.getSelNo();
	
	if( vRow == null || vRow == 0 ) 
	{
		return;
	}
	vRow = vRow -1 ;
	LCInsuredGrid.setRowColData(vRow,1,fm.LCInsuredName.value);
	LCInsuredGrid.setRowColData(vRow,2,fm.LCInsuredSex.value);
	LCInsuredGrid.setRowColData(vRow,3,fm.LCInsuredBirthday.value);
	LCInsuredGrid.setRowColData(vRow,4,fm.LCInsuredIDType.value);
	LCInsuredGrid.setRowColData(vRow,5,fm.LCInsuredIDNo.value);
	LCInsuredGrid.setRowColData(vRow,6,fm.LCInsuredOccupationType.value);
	LCInsuredGrid.setRowColData(vRow,7,fm.LCInsuredOccupationCode.value);
	LCInsuredGrid.setRowColData(vRow,8,fm.LCInsuredPostalAddress.value);
	LCInsuredGrid.setRowColData(vRow,9,fm.LCInsuredZipCode.value);
	LCInsuredGrid.setRowColData(vRow,10,fm.LCInsuredPhone.value);
	LCInsuredGrid.setRowColData(vRow,11,fm.LCInsuredEMail.value);
	LCInsuredGrid.setRowColData(vRow,12,fm.RelationToAppnt.value);

}





/*********************************************************************
 *  �ύ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function submitForm() 
{

	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  

  
    
	// У������뱻���˹�ϵѡ���Ǹ�ĸ,��Ͷ��������Ϣ������ͬ
    try 
    { 
    	if (!checkAppnt()) return false; 
      if (!checkGrid()) return false;
      mAction = "Confirm";
			document.all( 'fmAction' ).value = mAction;	
			fm.action="../selflist/NewSelfProposalSave.jsp"	
			//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			fm.submit(); //�ύ
        }
    catch(e) 
    {
        alert("������������У�����");
        return false;
    }
}

/*********************************************************************
 *  ���������������ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	showInfo.close(); 
    mAction = ""; 
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
		  var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
		  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");  		
		  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		  //ͨ��У����ת��ͻ�������Ϣ¼�����
          window.location.href="./ActivateInput.jsp";
	}	
}













