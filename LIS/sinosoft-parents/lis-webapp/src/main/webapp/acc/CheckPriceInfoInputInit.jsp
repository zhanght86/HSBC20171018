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
    document.all('QureyRiskCode').value = '';
    document.all('QueryState').value = '';
   document.all('QueryRiskCodeName').value = '';
    document.all('QueryStateName').value = '';
    document.all('QueryStartDate').value = '';
      document.all('QureyInsuAccNo').value = '';
    document.all('QueryName').value = '';
    
    document.all('RiskCode').value = '';
    document.all('RiskCodeName').value = '';
    document.all('InsuAccNo').value = '';
    document.all('InsuAccNoName').value = '';
    
    document.all('StartDate').value = '';
    document.all('EndDate').value = '';
    document.all('InvestFlag').value = '';
     document.all('InvestFlagName').value = '';
    document.all('SRateDate').value = '';

    document.all('ARateDate').value = '';
    document.all('UnitPriceBuy').value = '';
    document.all('UnitPriceSell').value = '';
    document.all('RedeemRate').value = '';
    document.all('State').value = '';
    document.all('StateName').value = '';
    document.all('RedeemMoney').value = '';
    document.all('ComChgUnitCount').value = '';
    document.all('CompanyUnitCount').value = '';
    document.all('CustomersUnitCount').value = '';
document.all('ManageFee').value = '';

     document.all('Transact').value = '';
      
   document.all('DealDate').value = '';
     document.all('DoBatch').value = '';
     document.all('SKFlag').value = '';
  }
  catch(ex)
  {
    alert("��toulianInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  //try
  //{
    initInpBox();
    initPiceDate();
    initCollectivityGrid();
  //}
  //catch(re)
  //{
  //  alert("toulianInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  //}
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

      //iArray[1]=new Array();
      //iArray[1][0]="���ֱ���";         		//����
      //iArray[1][1]="100px";            		//�п�
      //iArray[1][2]=100;            			//�����ֵ
      //iArray[1][3]=2;             			//�Ƿ���������,1��ʾ����0��ʾ������
      //iArray[1][4]="riskind";
      //iArray[1][15]="risktype3";
      //iArray[1][16]="#3#";
        
      iArray[1]=new Array();
      iArray[1][0]="�����ʻ�����";         		//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][4]="findinsuacc";
      iArray[1][15]="riskcode";
      iArray[1][17]="1";

      iArray[2]=new Array();
      iArray[2][0]="�Ƽ�����";         		//����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="����۸�";         		//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�����۸�";         		//����
      iArray[4][1]="120px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="״̬";         		//����
      iArray[5][1]="50px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=2;              //�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][10]="State";
      iArray[5][11]="0|^1|¼��^2|������ȷ^3|���˴���^4|ȷ����ȷ^0|��Ч";
      
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
