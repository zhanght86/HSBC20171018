<%
//�������ƣ�PEdorInputInit.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��Tjj
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
                      

<script language="JavaScript">  
function initInpBox()
{ 

  try
  {        
  
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('GrpContNo').value = top.opener.document.all('OtherNo').value;    
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('ContType').value = top.opener.document.all('ContType').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    document.all('CValiDate').value = top.opener.document.all('CValiDate').value;

    //easyQueryClick();

  }
  catch(ex)
  {
    alert("��GEdorTypeWTInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initSelBox()
{  
  try                 
  {

  }
  catch(ex)
  {
    alert("��PEdorTypeWTInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    easyQueryClick();    
    //initLPPolGrid();
    initWTFeeDetailGrid();
    //alert("ok");
    getWTFeeDetailGrid();

    //initQuery();
    //ctrlGetEndorse();
  }
  catch(re)
  {
    alert("PEdorTypeWTInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initLPPolGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="������";    	//����
      iArray[1][1]="200px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�ͻ���";         			//����
      iArray[2][1]="200px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�ͻ�����";         			//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="������";         		//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

      iArray[5]=new Array();
      iArray[5][0]="����";         		//����
      iArray[5][1]="150px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

		iArray[6]=new Array();
		iArray[6][0]="����";         		
		iArray[6][1]="50px";            		 
		iArray[6][2]=60;            			
		iArray[6][3]=2;              		
		iArray[6][4]="Currency";              	  
		iArray[6][9]="����|code:Currency";

      LPPolGrid = new MulLineEnter( "fm" , "LPPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LPPolGrid.mulLineCount = 10;   
      LPPolGrid.displayTitle = 1;
      LPPolGrid.canChk=1;
      LPPolGrid.detailInfo="������ʾ��ϸ��Ϣ";
      LPPolGrid.loadMulLine(iArray);  
      
      
      }
      catch(ex)
      {
        alert(ex);
      }
}
// ��ѯ��ť
function easyQueryClick()
{
	var tEdorNo;
	var tGrpContNo;
	var tEdorType;
	
	tEdorNo = top.opener.document.all('EdorNo').value;
  tGrpContNo  = top.opener.document.all('ContNoApp').value;
  tEdorType = top.opener.document.all('EdorType').value;  
	
	document.all('GetMoney').value = '';
	var strSQL = "";	

// 	strSQL = "select sum(prem)-10 from lcpol where grpcontno='"+ tGrpContNo +"' and appflag = '1'";
	var sqlid1="GEdorTypeWTInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.GEdorTypeWTInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tGrpContNo);//ָ������Ĳ���
	strSQL=mySql1.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	if (!turnPage.strQueryResult) 
  {
  		    
  }else
	 {
	 	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		document.all('GetMoney').value = turnPage.arrDataCacheSet[0][0];
	 }

}

function initQuery()
{	
	
	 var i = 0;
	 var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	 var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	 showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		
		document.all('fmtransact').value = "QUERY||MAIN";
		//alert("----begin---");
		//showSubmitFrame(mDebug);
		fm.submit();	  	 	 
}

function initWTFeeDetailGrid()
{                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";            		//�п�
        iArray[0][2]=10;            			//�����ֵ
        iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[1]=new Array();
        iArray[1][0]="���ֱ�����";
        iArray[1][1]="60px";
        iArray[1][2]=100;
        iArray[1][3]=0;
        
         iArray[2]=new Array();
        iArray[2][0]="���ִ���";
        iArray[2][1]="40px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="��������";
        iArray[3][1]="120px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="��Ч����";
        iArray[4][1]="50px";
        iArray[4][2]=100;
        iArray[4][3]=0;   
        
        iArray[5]=new Array();
        iArray[5][0]="�ɷѽ��";
        iArray[5][1]="50px";
        iArray[5][2]=100;
        iArray[5][3]=0;   
        
		iArray[6]=new Array();
		iArray[6][0]="����";         		
		iArray[6][1]="50px";            		 
		iArray[6][2]=60;            			
		iArray[6][3]=2;              		
		iArray[6][4]="Currency";              	  
		iArray[6][9]="����|code:Currency";
        
      WTFeeDetailGrid = new MulLineEnter( "fm" , "WTFeeDetailGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      WTFeeDetailGrid.mulLineCount = 0;   
      WTFeeDetailGrid.displayTitle = 1;
      WTFeeDetailGrid.canChk=0;
      WTFeeDetailGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      WTFeeDetailGrid.hiddenSubtraction=1;
      WTFeeDetailGrid.loadMulLine(iArray);
      //��Щ����������loadMulLine����

      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
