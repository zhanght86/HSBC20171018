<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�NotProposalQueryInit.jsp
//�����ܣ�δ�б���ѯ
//�������ڣ�2005-06-01 11:10:36
//������  ��HL
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
     //���ҳ��ؼ��ĳ�ʼ����
%>     
<script language="JavaScript">

function initForm()
{
  try 
  {
  	//��ʼ���ѳб�����multiline
  	initContGrid();
  	
  	//��ʼ������������Ϣ
  	initPolGrid();
  	
  	//��ѯ�ͻ���Ϣ
  	queryPersonInfo();
	  
	  //��ѯ�ѳб�������Ϣ
	  queryCont();
	  
  	
  }
  catch(re) {
    alert("NotProposalQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initPolGrid(){
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
          iArray[1][1]="100px";            		//�п�
          iArray[1][2]=100;            			//�����ֵ
          iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[2]=new Array();
          iArray[2][0]="���ֱ�����";         		//����
          iArray[2][1]="0px";            		//�п�
          iArray[2][2]=100;            			//�����ֵ
          iArray[2][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

          iArray[3]=new Array();
          iArray[3][0]="�б�����";         		//����
          iArray[3][1]="120px";            		//�п�
          iArray[3][2]=100;            			//�����ֵ
          iArray[3][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[4]=new Array();
          iArray[4][0]="���ִ���";         		//����
          iArray[4][1]="80px";            		//�п�
          iArray[4][2]=200;            			//�����ֵ
          iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[5]=new Array();
          iArray[5][0]="��������";         		//����
          iArray[5][1]="180px";            		//�п�
          iArray[5][2]=200;            			//�����ֵ
          iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[6]=new Array();
          iArray[6][0]="����״̬";         		//����
          iArray[6][1]="60px";            		//�п�
          iArray[6][2]=100;            			//�����ֵ
          iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[7]=new Array();
          iArray[7][0]="����";         		//����
          iArray[7][1]="100px";            		//�п�
          iArray[7][2]=100;            			//�����ֵ
          iArray[7][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
          iArray[7][23]="0";

          iArray[8]=new Array();
          iArray[8][0]="����";         		//����
          iArray[8][1]="40px";            		//�п�
          iArray[8][2]=100;            			//�����ֵ
          iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

          iArray[9]=new Array();
          iArray[9][0]="�˱�����";         		//����
          iArray[9][1]="60px";            		//�п�
          iArray[9][2]=100;            			//�����ֵ
          iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

          iArray[10]=new Array();
          iArray[10][0]="�ӷ�����";         		//����
          iArray[10][1]="100px";            		//�п�
          iArray[10][2]=100;            			//�����ֵ
          iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[11]=new Array();
					iArray[11][0]="����";
					iArray[11][1]="60px";
					iArray[11][2]=100;
					iArray[11][3]=2;
					iArray[11][4]="currency";
          
          PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 

          //��Щ���Ա�����loadMulLineǰ
          PolGrid.mulLineCount = 5;   
          PolGrid.displayTitle = 1;
          PolGrid.locked = 1;
          PolGrid.canSel = 1;
          PolGrid.hiddenPlus = 1;
          PolGrid.hiddenSubtraction = 1;
          PolGrid.selBoxEventFuncName = "";
          PolGrid.loadMulLine(iArray);  
          
          //��Щ����������loadMulLine����
          //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
	
}


function initContGrid(){
    var iArray = new Array();
      
      try
      {
           iArray[0]=new Array();
          iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
          iArray[0][1]="30px";            		//�п�
          iArray[0][2]=10;            			//�����ֵ
          iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[1]=new Array();
          iArray[1][0]="������";         		//����
          iArray[1][1]="0px";            		//�п�
          iArray[1][2]=100;            			//�����ֵ
          iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[2]=new Array();
          iArray[2][0]="Ͷ������";         		//����
          iArray[2][1]="120px";            		//�п�
          iArray[2][2]=100;            			//�����ֵ
          iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[3]=new Array();
          iArray[3][0]="Ͷ���˿ͻ���";         		//����
          iArray[3][1]="120px";            		//�п�
          iArray[3][2]=100;            			//�����ֵ
          iArray[3][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
           
          iArray[4]=new Array();
          iArray[4][0]="Ͷ��������";         		//����
          iArray[4][1]="120px";            		//�п�
          iArray[4][2]=100;            			//�����ֵ
          iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[5]=new Array();
          iArray[5][0]="����������";         		//����
          iArray[5][1]="120px";            		//�п�
          iArray[5][2]=100;            			//�����ֵ
          iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
          //tongmeng 2007-12-03 add 
          iArray[6]=new Array();
          iArray[6][0]="�˱�����";         		//����
          iArray[6][1]="120px";            		//�п�
          iArray[6][2]=200;            			//�����ֵ
          iArray[6][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������


          iArray[7]=new Array();
          iArray[7][0]="������Ч����";         		//����
          iArray[7][1]="120px";            		//�п�
          iArray[7][2]=200;            			//�����ֵ
          iArray[7][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[8]=new Array();
          iArray[8][0]="����״̬";         		//����
          iArray[8][1]="80px";            		//�п�
          iArray[8][2]=200;            			//�����ֵ
          iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[9]=new Array();
          iArray[9][0]="�˱�֪ͨ��";         		//����
          iArray[9][1]="100px";            		//�п�
          iArray[9][2]=100;            			//�����ֵ
          iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[10]=new Array();
          iArray[10][0]="����";         		//����
          iArray[10][1]="60px";            		//�п�
          iArray[10][2]=100;            			//�����ֵ
          iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

          iArray[11]=new Array();
          iArray[11][0]="������";         		//����
          iArray[11][1]="60px";            		//�п�
          iArray[11][2]=100;            			//�����ֵ
          iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

          iArray[12]=new Array();
          iArray[12][0]="������֪";         		//����
          iArray[12][1]="60px";            		//�п�
          iArray[12][2]=100;            			//�����ֵ
          iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

          iArray[13]=new Array();
          iArray[13][0]="ӡˢ��";         		//����
          iArray[13][1]="0px";            		//�п�
          iArray[13][2]=100;            			//�����ֵ
          iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

          iArray[14]=new Array();
          iArray[14][0]="����״̬";         		//����
          iArray[14][1]="0px";            		//�п�
          iArray[14][2]=100;            			//�����ֵ
          iArray[14][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[15]=new Array();
          iArray[15][0]="��������";         		//����
          iArray[15][1]="80px";            		//�п�
          iArray[15][2]=100;            			//�����ֵ
          iArray[15][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
          
          iArray[16]=new Array();
          iArray[16][0]="�������ͱ���";         		//����
          iArray[16][1]="0px";            		//�п�
          iArray[16][2]=100;            			//�����ֵ
          iArray[16][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

          iArray[17]=new Array();
          iArray[17][0]="�˱�����";         		//����
          iArray[17][1]="100px";            		//�п�
          iArray[17][2]=100;            			//�����ֵ
          iArray[17][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          ContGrid = new MulLineEnter( "fm" , "ContGrid" ); 

          //��Щ���Ա�����loadMulLineǰ
          ContGrid.mulLineCount = 5;   
          ContGrid.displayTitle = 1;
          ContGrid.locked = 1;
          ContGrid.canSel = 1;
          ContGrid.hiddenPlus = 1;
          ContGrid.hiddenSubtraction = 1;
          ContGrid.selBoxEventFuncName = "contInfoClick";
          ContGrid.loadMulLine(iArray);  
          
          //��Щ����������loadMulLine����
          //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
	
}



// �����ĳ�ʼ��
function initInpBox()
{
}

// ������ĳ�ʼ��
function initSelBox(){  
}                                        


</script>
                       

