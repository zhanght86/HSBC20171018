<%
//�������ƣ�ProposalApproveInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   
	// ������ѯ����
    //document.all('ProposalNo').value = '';
    //document.all('ManageCom').value = '';
    //document.all('AgentCode').value = '';
    //document.all('AgentGroup').value = '';
  }
  catch(ex)
  {
    alert("��ProposalApproveInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("��ProposalApproveInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
	initInpBox();  
	initSelBox();       
	initPolGrid();
	initSelfPolGrid();	
	easyQueryClickSelf();
  }
  catch(re)
  {
    alert("ProposalApproveInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initPolGrid()
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
      iArray[1][0]="ӡˢ��";         		//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���ֱ���";         		//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=200;            			//�����ֵ
      iArray[2][3]=0; 
      iArray[2][4]="RiskCode";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[2][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[2][9]="���ֱ���|code:RiskCode&NOTNULL";
      iArray[2][18]=250;
      iArray[2][19]= 0 ;

      iArray[3]=new Array();
      iArray[3][0]="�������";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[4]=new Array();
      iArray[4][0]="����ԭ��";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=80;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][4]="BPODealType";              	        //�Ƿ����ô���:null||""Ϊ������

      iArray[5]=new Array();
      iArray[5][0]="ɨ������";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�����������";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[7]=new Array();
      iArray[7][0]="�������������";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[8]=new Array();
      iArray[8][0]="�������Id";         		//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[9]=new Array();
      iArray[9][0]="ɨ��ʱ��";         		//����
      iArray[9][1]="80px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="ҵ���������";         		//����
      iArray[10][1]="0px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 5;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ���˱�����Ϣ�б�ĳ�ʼ��
function initSelfPolGrid()
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
      iArray[1][0]="ӡˢ��";         		//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���ֱ���";         		//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=200;            			//�����ֵ
      iArray[2][3]=0; 
      iArray[2][4]="RiskCode";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[2][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[2][9]="���ֱ���|code:RiskCode&NOTNULL";
      iArray[2][18]=250;
      iArray[2][19]= 0 ;

      iArray[3]=new Array();
      iArray[3][0]="�������";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="����ԭ��";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=80;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][4]="BPODealType";              	        //�Ƿ����ô���:null||""Ϊ������
      
      iArray[5]=new Array();
      iArray[5][0]="ɨ������";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�����������";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[7]=new Array();
      iArray[7][0]="�������������";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[8]=new Array();
      iArray[8][0]="�������Id";         		//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[9]=new Array();
      iArray[9][0]="ɨ��ʱ��";         		//����
      iArray[9][1]="80px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="ҵ���������";         		//����
      iArray[10][1]="0px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      SelfPolGrid = new MulLineEnter( "fm" , "SelfPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      SelfPolGrid.mulLineCount = 5;   
      SelfPolGrid.displayTitle = 1;
      SelfPolGrid.locked = 1;
      SelfPolGrid.canSel = 1;
      SelfPolGrid.hiddenPlus = 1;
      SelfPolGrid.hiddenSubtraction = 1;
      SelfPolGrid.selBoxEventFuncName = "InitshowApproveDetail";    
      SelfPolGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}


</script>
