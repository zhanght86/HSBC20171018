<%
/*******************************************************************************
 * <p>Title       : ��ֵ˰˰��</p>
 * <p>Description : ˰������</p>
 * <p>Copyright   : Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company     : �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite     : http://www.sinosoft.com.cn</p>
 * @author        :
 * @version       : 1.00
 * @date          : 2007-12-25
 ******************************************************************************/
%>
<script language="JavaScript">
var turnPage = new turnPageClass(); 

function initForm()
{
  try
  {
    initRiskGrpGrid(); 
  }
  catch(re)
  {
    alert("��ʼ���������!-initInpBox");
  }
  try {
		query(); 
		}catch(e) {
			alert("TaxRateGrid error");
		}
 
}


function initRiskGrpGrid()
{

    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;

      iArray[1]=new Array();
      iArray[1][0]="���ֱ���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="50px";         			//�п�
      iArray[1][2]=10;          			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="������";    	                //����
      iArray[2][1]="80px";            		        //�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      
      iArray[3]=new Array();
      iArray[3][0]="��¼ID";    	                //����
      iArray[3][1]="60px";            		        //�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=3;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
            
      
      RiskGrpGrid = new MulLineEnter( "fm" , "RiskGrpGrid" );

      //��Щ���Ա�����loadMulLineǰ
      RiskGrpGrid.mulLineCount = 5;
      RiskGrpGrid.displayTitle = 1;
      RiskGrpGrid.canChk =0;
      RiskGrpGrid.canSel =1;
      RiskGrpGrid.selBoxEventFuncName ="showRiskGrpDetail";
      RiskGrpGrid.locked =1;            //�Ƿ�������1Ϊ���� 0Ϊ������
      RiskGrpGrid.hiddenPlus=1;        //�Ƿ�����"+"���һ�б�־��1Ϊ���أ�0Ϊ������ 
      RiskGrpGrid.hiddenSubtraction=1; //�Ƿ�����"-"���һ�б�־��1Ϊ���أ�0Ϊ������  
      RiskGrpGrid.recordNo=0;         //���������ʼ����Ϊ10�����Ҫ��ҳ��ʾ��������
      RiskGrpGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert(ex);
      }
  }



</script>