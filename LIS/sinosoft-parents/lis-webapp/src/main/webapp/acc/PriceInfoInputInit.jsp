<%
//�������ƣ�ClientConjoinQueryInit.jsp
//�����ܣ�
//�������ڣ�2002-08-19
//������  ��Kevin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {                                   
    document.all('QueryState').value = '';
    document.all('QueryStateName').value = '';
    document.all('QueryStartDate').value = '';
      document.all('QureyInsuAccNo').value = '';
    document.all('QueryName').value = '';
    document.all('InsuAccNo').value = '';
    document.all('InsuAccNoName').value = '';
    
    document.all('StartDate').value = '';
    document.all('SKFlag').value = '';
    document.all('SKFlagName').value = '';
    //document.all('InvestFlagName').value = '';
    //document.all('SRateDate').value = '';

    //document.all('ARateDate').value = '';
    document.all('InsuTotalMoney').value = '';
    document.all('Liabilities').value = '';
    //document.all('RedeemRate').value = '';
    document.all('State').value = '1';
    document.all('StateName').value = '1-¼��';
    //document.all('RedeemMoney').value = '';
	document.all('CompanyUnitCount').value = '';
	document.all('ComChgUnitCount').value = '0';
	document.all('CustomersUnitCount').value = '';
	myCheckDate = '';
	myCheckInsuAccNo = '';

    document.all('Transact').value = '';

  }
  catch(ex)
  {
    alert("��toulianInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
    initCollectivityGrid();
  }
  catch(re)
  {
    alert("toulianInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
function initCollectivityGrid()
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
      iArray[1][0]="Ͷ���ʻ�����";         		//����
      iArray[1][1]="80px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][4]="findinsuacc";
      iArray[1][15]="riskcode";
      iArray[1][17]="1";

      iArray[2]=new Array();
      iArray[2][0]="�ʲ���������";         		//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
     
      iArray[3]=new Array();
      iArray[3][0]="����۸�";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
     
      iArray[4]=new Array();
      iArray[4][0]="�����۸�";         		//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="�˻����ʲ�";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="��ծ";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="δʵ������Ӫҵ˰";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="�˻��ʲ������";         		//����
      iArray[8][1]="90px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="��˾Ͷ�ʵ�λ��";         		//����
      iArray[9][1]="90px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="��˾�䶯��λ��";         		//����
      iArray[10][1]="90px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="״̬";         		//����
      iArray[11][1]="30px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=2;              //�Ƿ���������,1��ʾ����0��ʾ������
      iArray[11][10]="State";
      iArray[11][11]="0|^1|¼��^2|������ȷ^3|���˴���^4|ȷ����ȷ^5|ȷ�ϴ���^0|��Ч";
      
      CollectivityGrid = new MulLineEnter( "fm" , "CollectivityGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      
      CollectivityGrid.mulLineCount = 5;
      CollectivityGrid.displayTitle = 1;
      CollectivityGrid.hiddenPlus = 1;
    //  CollectivityGrid.locked = 0;
      CollectivityGrid.hiddenSubtraction = 1;
      CollectivityGrid.canSel=1;
      CollectivityGrid.selBoxEventFuncName = "ShowPlan";
      CollectivityGrid.loadMulLine(iArray);
      
   //    CollectivityGrid.mulLineCount = 10;   
  //    CollectivityGrid.displayTitle = 1;
    //  CollectivityGrid.locked = 1;
   ///   CollectivityGrid.hiddenPlus=1;
    //  CollectivityGrid.hiddenSubtraction=1;      
    //  CollectivityGrid.canSel = 1;
    //  CollectivityGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}


</script>
