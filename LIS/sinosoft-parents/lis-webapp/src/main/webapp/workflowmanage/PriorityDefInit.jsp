<%
/*******************************************************************************
 * <p>Title       : �������</p>
 * <p>Description : ������ϵͳ���Ѿ������������������Ա��</p>
 * <p>Copyright   : Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company     : �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite     : http://www.sinosoft.com.cn</p>
 * @author        :
 * @version       : 1.00
 * @date          : 2007-12-25
 ******************************************************************************/
%>
<script language="JavaScript">

function initWorkFlowGridP()
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
      iArray[1][0]="����������";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="80px";         			//�п�
      iArray[1][2]=10;          			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][4]= "priority";
      iArray[1][5]="1|6";     //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
	  iArray[1][6]="0|1";    //��������з������ô����еڼ�λֵ

      iArray[2]=new Array();
      iArray[2][0]="�ID";    	            //����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      
      iArray[3]=new Array();
      iArray[3][0]="�����";    	        //����
      iArray[3][1]="150px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=2;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      iArray[3][7]="getProcessName";  


      iArray[4]=new Array();
      iArray[4][0]="��ӦSQL";    	                //����
      iArray[4][1]="400px";            		        //�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=1;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      
      iArray[5]=new Array();
      iArray[5][0]="ProcessID";    	                //����
      iArray[5][1]="50px";            		        //�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=3;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      
      iArray[6]=new Array();
      iArray[6][0]="���ȼ�ID";    	                //����
      iArray[6][1]="0px";            		        //�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      
      iArray[7]=new Array();
      iArray[7][0]="���̰汾";    	                //����
      iArray[7][1]="100px";            		        //�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=1;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      
      
      WorkFlowGridP = new MulLineEnter( "fm" , "WorkFlowGridP" );

      //��Щ���Ա�����loadMulLineǰ
      WorkFlowGridP.mulLineCount = 5;
      WorkFlowGridP.displayTitle = 1;
      WorkFlowGridP.canChk =0;
      WorkFlowGridP.canSel =1;
      WorkFlowGridP.locked =0;            //�Ƿ�������1Ϊ���� 0Ϊ������
      WorkFlowGridP.hiddenPlus=0;        //�Ƿ�����"+"���һ�б�־��1Ϊ���أ�0Ϊ������
      WorkFlowGridP.hiddenSubtraction=1; //�Ƿ�����"-"���һ�б�־��1Ϊ���أ�0Ϊ������
      WorkFlowGridP.recordNo=0;         //���������ʼ����Ϊ10�����Ҫ��ҳ��ʾ��������
      WorkFlowGridP.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert(ex);
      }
  }
function initWorkGridPa()
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
      iArray[1][0]="����������";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="80px";         			//�п�
      iArray[1][2]=10;          			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

	  iArray[2]=new Array();
      iArray[2][0]="��ɫ���� 	";                  //����
      iArray[2][1]="150px";            		        //�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=2;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      iArray[2][4]= "prioritycolor";
      iArray[2][5]="2|3";     //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
	  iArray[2][6]="0|1";    //��������з������ô����еڼ�λֵ
      
      iArray[3]=new Array();
      iArray[3][0]="COLORID";    	                //����
      iArray[3][1]="0px";            		        //�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�


      iArray[4]=new Array();
      iArray[4][0]="ʱЧ��Χ";    	                //����
      iArray[4][1]="150px";            		        //�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=1;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      
      
      iArray[5]=new Array();
      iArray[5][0]="���ȼ�ID";    	                //����
      iArray[5][1]="0px";            		        //�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      
      
      WorkGridPa = new MulLineEnter( "fmb" , "WorkGridPa" );

      //��Щ���Ա�����loadMulLineǰ
      WorkGridPa.mulLineCount = 5;
      WorkGridPa.displayTitle = 1;
      WorkGridPa.canChk =0;
      WorkGridPa.canSel =1;
      WorkGridPa.locked =0;            //�Ƿ�������1Ϊ���� 0Ϊ������
      WorkGridPa.hiddenPlus=0;        //�Ƿ�����"+"���һ�б�־��1Ϊ���أ�0Ϊ������
      WorkGridPa.hiddenSubtraction=1; //�Ƿ�����"-"���һ�б�־��1Ϊ���أ�0Ϊ������
      WorkGridPa.recordNo=0;         //���������ʼ����Ϊ10�����Ҫ��ҳ��ʾ��������
      WorkGridPa.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert(ex);
      }
  }

</script>
