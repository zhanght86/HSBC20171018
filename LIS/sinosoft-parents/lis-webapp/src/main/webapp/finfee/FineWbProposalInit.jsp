<%
//�������ƣ�FineProposalInput.jsp
//�����ܣ�
//�������ڣ�2008-06-26 
//������  ��ln
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox() { 
  try {  
                                   
	  // �շ���Ϣ����
    document.all('TempFeeNo').value = '';
    document.all('OtherNo').value = '';
    document.all('ManageCom').value = '';
    document.all('PayDate').value = '';
    document.all('EnterAccDate').value = '';
    document.all('AgentCode').value = '';

    
    document.all('SumTempFee').value = 0.0 ;   
   
    
  } catch(ex) {
    //alert("��ProposalInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  } 
  
}

// ���շ���Ϣ�б�ĳ�ʼ��
function initTempGrid()
  {                               
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="40px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���ֱ���";          		//����
      iArray[1][1]="50px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=2;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      iArray[1][4]="RiskCode";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[1][9]="���ֱ���|code:RiskCode&NOTNULL";
      iArray[1][18]=300;
      //iArray[1][19]=1;

      iArray[2]=new Array();
      iArray[2][0]="���ѽ��";      	   		//����
      iArray[2][1]="60px";            			//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][9]="���ѽ��|NUM&NOTNULL";

      iArray[3]=new Array();
      iArray[3][0]="��������";      	   		//����
      iArray[3][1]="80px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�ɷѷ�ʽ";      	   		//����
      iArray[4][1]="60px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][10]="PayIntv";
      iArray[4][11]="0|^12|���|^0|����|^6|�����^3|����^1|�½�^-1|�����ڽ�";
      
      iArray[5]=new Array();
      iArray[5][0]="�ɷ�����";      	   		//����
      iArray[5][1]="60px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      TempGrid = new MulLineEnter( "fm" , "TempGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TempGrid.mulLineCount = 0;   
      TempGrid.displayTitle = 1;      
      TempGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ���շѷ����б�ĳ�ʼ��
function initTempClassGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="40px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���ѷ�ʽ";          		//����
      iArray[1][1]="50px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=2;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      iArray[1][4]="paymode";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[1][9]="���ѷ�ʽ|code:paymode&NOTNULL";

      iArray[2]=new Array();
      iArray[2][0]="���ѽ��";      	   		//����
      iArray[2][1]="90px";            			//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][9]="���ѽ��|NUM&NOTNULL";
      
      iArray[3]=new Array();
      iArray[3][0]="Ʊ�ݺ���";      	   		//����
      iArray[3][1]="140px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
//      iArray[4][9]="Ʊ�ݺ���|NULL|LEN>10";              //���Ի�����

      iArray[4]=new Array();
      iArray[4][0]="��������";      	   		//����
      iArray[4][1]="100px";            			//�п�
      iArray[4][2]=10;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

  
      iArray[5]=new Array();
      iArray[5][0]="��������";      	   		//����
      iArray[5][1]="100px";            			//�п�
      iArray[5][2]=40;            			//�����ֵ
      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][4]="bank";              	 //�Ƿ����ô���:null||""Ϊ������
      iArray[5][9]="��������|code:bank";
      iArray[5][18]=250;
      
      iArray[6]=new Array();
      iArray[6][0]="�����˺�";      	   		//����
      iArray[6][1]="140px";            		//�п�
      iArray[6][2]=40;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="����";      	   		//����
      iArray[7][1]="100px";            			//�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="֤������";      	   		//����
      iArray[8][1]="100px";            			//�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
           

      TempClassGrid = new MulLineEnter( "fm" , "TempClassGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TempClassGrid.mulLineCount = 0;   
      TempClassGrid.displayTitle = 1;     
      TempClassGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
  }

function emptyForm() {
	//emptyFormElements();     //���ҳ�������������COMMON��JS��ʵ��
	initInpBox();
	initTempGrid();
	initTempClassGrid();
	
}

function initForm() {
	try	{   
		
		if (loadFlag == "3"||loadFlag == "4") {	//����Ͷ������ϸ��ѯ
			var tTempFeeNo = top.opener.parent.VD.gVSwitch.getVar( "TempFeeNo" );
			//alert("top.opener.parent"+top.opener.parent.location);
			
			//alert("tTempFeeNo:"+tTempFeeNo);
			queryPolDetail( tTempFeeNo );
		} 
	} catch(ex) {
	}			
}

 

</script>
