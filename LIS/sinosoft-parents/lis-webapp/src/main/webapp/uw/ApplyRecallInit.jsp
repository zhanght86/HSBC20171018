<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ApplyRecallInit.jsp
//�����ܣ�
//�������ڣ�
//������  :
//���¼�¼��  ������    ��������     ����ԭ��/����
%>                      

<script language="JavaScript">


// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
try
  {    
	// ������ѯ����
    document.all('PrtNoSearch').value = '';
    document.all('ContNo').value = '';
    document.all('ManageCom').value = '';
    document.all('AgentCode').value = '';
    document.all('AgentGroup').value = '';
                                     
    document.all('PrtNo').value = '';
    document.all('Content').value = '¼�볷��˵��';

  }
  catch(ex)
  {
    alert("ApplyRecallInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }   
}                                      

function initForm(tPrtNo,tFlag)
{
  try
  {
	initInpBox();	
	initPolGrid();
  }
  catch(re)
  {
    alert("ApplyRecallInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
      iArray[1][0]="Ͷ������";         		//����
      iArray[1][1]="150px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    
      iArray[2]=new Array();
      iArray[2][0]="ӡˢ��";         		//����
      iArray[2][1]="160px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[3]=new Array();
      iArray[3][0]="Ͷ����";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[4]=new Array();
      iArray[4][0]="¼����Ա";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="¼������";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="������Ա";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="��������";         		//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="�˱���Ա";         		//����
      iArray[8][1]="60px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="�˱�����";         		//����
      iArray[9][1]="100px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="�˱�����";         		//����
      iArray[10][1]="100px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������   
      iArray[10][4]="UWState";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[10][5]="13";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[10][9]="�˱�����|code:UWState&NOTNULL";
      iArray[10][18]=250;
      iArray[10][19]= 0 ;
            
      iArray[11]=new Array();
      iArray[11][0]="ɨ��ʱ��";         		//����
      iArray[11][1]="100px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������        
      
      iArray[12]=new Array();
      iArray[12][0]="����";         		//����
      iArray[12][1]="100px";            		//�п�
      iArray[12][2]=100;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������        
      
      iArray[13]=new Array();
      iArray[13][0]="����־";         		//����
      iArray[13][1]="100px";            		//�п�
      iArray[13][2]=100;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������        
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 5;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>


