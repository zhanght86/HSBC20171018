<%
/*******************************************************************************
 * <p>Title       : ��ֵ˰��������</p>
 * <p>Description : ��ֵ˰����������ɾ�Ĳ�</p>
 * <p>Copyright   : Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company     : �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite     : http://www.sinosoft.com.cn</p>
 * @author        :
 * @version       : 1.00
 * @date          : 2007-12-25
 ******************************************************************************/
%>
<script language="JavaScript">
function initInpBox()
{ 
  try
  {               
      document.all('FeeTypeCode').value= '';  
      
  }
  catch(ex)
  {
    alert("��FindVATRateInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
function initForm()
{
  try
  {
    initInpBox();
    initWorkFlowGrid();
  }
  catch(re)
  {
    alert("��ʼ���������!");
  }
}
function initWorkFlowGrid()
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
      iArray[1][0]="��������";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="90px";         			//�п�
      iArray[1][2]=10;          			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="������������";    	                //����
      iArray[2][1]="90px";            		        //�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      
      iArray[3]=new Array();
      iArray[3][0]="�Ƿ�Ӧ˰";    	                //����
      iArray[3][1]="90px";            		        //�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[4]=new Array();
      iArray[4][0]="�Ƿ��������";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[4][1]="90px";         			//�п�
      iArray[4][2]=10;          			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��¼ID";    	                //����
      iArray[5][1]="10px";            		        //�п�
      iArray[5][2]=105;            			//�����ֵ      
      iArray[5][3]=3;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�      
      
      WorkFlowGrid = new MulLineEnter( "fm" , "WorkFlowGrid" );

      //��Щ���Ա�����loadMulLineǰ
      WorkFlowGrid.mulLineCount = 5;
      WorkFlowGrid.displayTitle = 1;
      WorkFlowGrid.canChk =0;
      WorkFlowGrid.canSel =1;
      WorkFlowGrid.selBoxEventFuncName ="showVATDetail";
      WorkFlowGrid.locked =1;            //�Ƿ�������1Ϊ���� 0Ϊ������
      WorkFlowGrid.hiddenPlus=1;        //�Ƿ�����"+"���һ�б�־��1Ϊ���أ�0Ϊ������ 
      WorkFlowGrid.hiddenSubtraction=1; //�Ƿ�����"-"���һ�б�־��1Ϊ���أ�0Ϊ������  
      WorkFlowGrid.recordNo=0;         //���������ʼ����Ϊ10�����Ҫ��ҳ��ʾ��������
      WorkFlowGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert(ex);
      }
  }
  
</script>
