<%
//�������ƣ�PEdorTypeTRInit.jsp
//�����ܣ�
//�������ڣ�2003-01-08 
//������  ��Minim
//���¼�¼��  ������    ��������     ����ԭ��/����
//						Lihs			2005-4-27 01:50����  ������
//           Nicholas
%>                          

<script language="JavaScript">  
	
function initInpBox()
{ 
  try
  { 
    Edorquery();   
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value; 
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value; 
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value; 
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;  
    document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
    showOneCodeName('EdorCode','EdorType','EdorTypeName');
  }
  catch(ex)
  {
    alert("��ʼ���������!");
  }      
}

function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
  	//alert('1');
    initInpBox();
    //	alert('2');
    initSelBox();
    //	alert('3');
    initQueryPolInfo();  
    //	alert('4');
    QueryCustomerInfo();
    //	alert('5');
    initLoanGrid();
    //	alert('6');
  }
  catch(re)
  {
    alert("��ʼ���������!");
  }
}

function initQueryPolInfo()
{
  //��ѯ���ֻ�����Ϣ
	var tContNo=document.all('ContNo').value;
	var strSQL=""
	if(tContNo!=null && tContNo !='')
	{
// 	  strSQL = "SELECT RiskCode,"
//                  + " (select RiskName from LMRisk where RiskCode=a.RiskCode),"
//        					 + " PayToDate,Mult,Amnt,Prem,PolNo"
// 					+ " FROM LCPol a"
// 					+ " WHERE a.Contno='" + tContNo + "' and polno=mainpolno";
	  var sqlid1="PEdorTypeTRInputSql2";
	  var mySql1=new SqlClass();
	  mySql1.setResourceName("bq.PEdorTypeTRInputSql"); //ָ��ʹ�õ�properties�ļ���
	  mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	  mySql1.addSubPara(tContNo);//ָ������Ĳ���
	  strSQL=mySql1.getString();
	}
	else
	{
		alert('��������ֺŴ���');
	}
	var arrSelected = new Array();		
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
  if(!turnPage.strQueryResult){
		alert("��ѯ���ֻ�����Ϣʧ�ܣ�");
	}
	else
	{
	  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	
	  try {document.all('RiskCode').value = arrSelected[0][0];} catch(ex) { }; //���ִ���
	  try {document.all('RiskName').value = arrSelected[0][1];} catch(ex) { }; //��������
	  try {document.all('PaytoDate').value = arrSelected[0][2];}catch(ex){}; //��������
	  try {document.all('Mult').value = arrSelected[0][3];} catch(ex) { }; //����
	  try {document.all('Amnt').value = arrSelected[0][4];} catch(ex) { }; //����
	  try {document.all('Prem').value = arrSelected[0][5];} catch(ex) { }; //����
	  try {document.all('PolNo').value  = arrSelected[0][6];} catch(ex) { }; //����
	  
  }
}

function QueryCustomerInfo()
{
  //��ѯ�ͻ�������Ϣ
	var tContNo=document.all('ContNo').value;
	var strSQL="" 
	if(tContNo!=null && tContNo !='')
	{
// 	  strSQL = "SELECT distinct a.AppntName,"
//        										+ " (select CodeName from LDCode where CodeType='idtype' and Code=a.IDType),"
//        										+ " a.IDNo,"
//        										+ " b.Name,"
//        										+ " (select CodeName from LDCode where CodeType='idtype' and Code=b.IDType),"
//        										+ " b.IDNo"
//        		+ " FROM LCAppnt a,LCInsured b,LCPol c"
//        		+ " WHERE c.ContNo='" + tContNo + "' and a.ContNo=c.ContNo and b.ContNo=c.ContNo and b.InsuredNo=c.InsuredNo and c.polno=c.mainpolno";
	  var sqlid1="PEdorTypeTRInputSql3";
	  var mySql1=new SqlClass();
	  mySql1.setResourceName("bq.PEdorTypeTRInputSql"); //ָ��ʹ�õ�properties�ļ���
	  mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	  mySql1.addSubPara(tContNo);//ָ������Ĳ���
	  strSQL=mySql1.getString();
	}
	else
	{
		alert('����ı����Ŵ���');
		return;
	}
	var arrSelected = new Array();	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);	
  if(!turnPage.strQueryResult){
		alert("��ѯ�ͻ�������Ϣʧ�ܣ�");
		return;
	}
	else
	{
	  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	  try {document.all('AppntName').value = arrSelected[0][0];} catch(ex) { }; //Ͷ��������
	  try {document.all('AppntIDType').value = arrSelected[0][1];} catch(ex) { }; //Ͷ����֤������
	  try {document.all('AppntIDNo').value = arrSelected[0][2];} catch(ex) { }; //Ͷ����֤������ 
	  try {document.all('InsuredName').value = arrSelected[0][3];} catch(ex) { }; //����������
	  try {document.all('InsuredIDType').value = arrSelected[0][4];} catch(ex) { }; //������֤������
	  try {document.all('InsuredIDNo').value = arrSelected[0][5];} catch(ex) { }; //������֤������ 
	  
	  //showOneCodeName('idtype','AppntIDType','AppntIDTypeName');
	  //showOneCodeName('idtype','InsuredIDType','InsuredIDTypeName');
  }
}
//var LoanGrid;
// ��Ϣ�б�ĳ�ʼ��
function initLoanGrid()
{
  var iArray = new Array();
    
  try
  {

    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          			  //�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
  
    iArray[1]=new Array();
    iArray[1][0]="�潻����";    	    //����1
    iArray[1][1]="150px";            	//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
  
    iArray[2]=new Array();
    iArray[2][0]="�潻����";         	//����2
    iArray[2][1]="120px";            	//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
  	iArray[2][23]="0";
  
    iArray[3]=new Array();
    iArray[3][0]="�潻��Ϣ";         	//����5
    iArray[3][1]="120px";            	//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
    iArray[3][23]="0";
  
  
    iArray[4]=new Array();
    iArray[4][0]="ʵ�ʳ����潻����";         	//����2
    iArray[4][1]="120px";            	//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
  	iArray[4][23]="0";
  
    iArray[5]=new Array();
    iArray[5][0]="ʵ�ʳ����潻��Ϣ";         	//����5
    iArray[5][1]="120px";            	//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
    iArray[5][23]="0";
    
    iArray[6]=new Array();
    iArray[6][0]="ԭ������";					//��¼ԭ���������ţ���LOLoan���EdorNo��								
    iArray[6][1]="0px";								//�浽LPReturnLoan�����LoanNo
    iArray[6][2]=20;
    iArray[6][3]=3;
    
    iArray[7]=new Array();
    iArray[7][0]="�������ֺ���";					//��¼ԭ���������ţ���LOLoan���EdorNo��	iArray[7][1]="120px";								//�浽LPReturnLoan�����LoanNo
    iArray[7][1]="0px";	
    iArray[7][2]=20;
    iArray[7][3]=0;
    
    iArray[8]=new Array();
		iArray[8][0]="����";
		iArray[8][1]="60px";
		iArray[8][2]=100;
		iArray[8][3]=2;
		iArray[8][4]="currency";
  
    LoanGrid = new MulLineEnter( "fm" , "LoanGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    //LoanGrid.mulLineCount = 6;   
    //alert('1111');
    LoanGrid.displayTitle = 1;
    
    LoanGrid.hiddenPlus = 1;
    LoanGrid.hiddenSubtraction = 1;
    LoanGrid.canChk=1;    
    LoanGrid.loadMulLine(iArray);  
    // alert('1111222');
    
    //��̨��ѯ����ʼ������MulLine������
		fm.action="./PEdorTypeTRCount.jsp";
		fm.submit();
  }
  catch(ex)
  {
    alert(ex);
  }
}

//��ѯ���ʼ��MulLine
function afterCount(tFlagStr,tMulArray,tContent)
{
		//��ʼ��MulLine
		var brr = new Array()
		//alert('afterCount');
		if (tFlagStr == "Success")
		{
    	displayMultiline(tMulArray,LoanGrid,turnPage);
    	//document.all('AllShouldPay').value = tWholeMoney; 
    	//document.all('AllShouldPayIntrest').value = tWholeMoneyIntrest; 

      //for (i=0; i<LoanGrid.mulLineCount; i++)
      //{  
      //var tStr="select ReturnMoney,returninterest from lpreturnloan where edorno='"+fm.EdorNo.value+"' and edortype='TR' and LoanNo='"+LoanGrid.getRowColData(i, 6)+"' order by loandate";    
      //brr = easyExecSql(tStr);   
      //if(brr!=null && brr!='')
      //{

        // LoanGrid.setRowColData(i,4,brr[0][0]);
         //LoanGrid.setRowColData(i,5,brr[0][1]);
      //}	
  
      //}  
      
    }
    else
    {
    	alert(tContent);
    }
    
    //add by jiaqiangli 2009-04-03 ����DivLockScreen���ֲ�Ĵ��� ����
	unlockScreen('DivLockScreen');
	//add by jiaqiangli 2009-04-03 ����DivLockScreen���ֲ�Ĵ��� ����
}

</script>