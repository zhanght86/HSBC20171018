<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ProposalQueryInit.jsp
//�����ܣ��б���ѯ
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
    alert("ProposalQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
          iArray[1][0]="������";         		//����
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
          iArray[3][1]="100px";            		//�п�
          iArray[3][2]=100;            			//�����ֵ
          iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[4]=new Array();
          iArray[4][0]="���ִ���";         		//����
          iArray[4][1]="80px";            		//�п�
          iArray[4][2]=200;            			//�����ֵ
          iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          //iArray[5]=new Array();
          //iArray[5][0]="��������";         		//����
          //iArray[5][1]="180px";            		//�п�
          //iArray[5][2]=200;            			//�����ֵ
          //iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          //
          //iArray[6]=new Array();
          //iArray[6][0]="����״̬";         		//����
          //iArray[6][1]="60px";            		//�п�
          //iArray[6][2]=100;            			//�����ֵ
          //iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[5]=new Array();
          iArray[5][0]="����";         		//����
          iArray[5][1]="100px";            		//�п�
          iArray[5][2]=100;            			//�����ֵ
          iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

          iArray[6]=new Array();
          iArray[6][0]="����";         		//����
          iArray[6][1]="40px";            		//�п�
          iArray[6][2]=100;            			//�����ֵ
          iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

          iArray[7]=new Array();
          iArray[7][0]="�˱�����";         		//����
          iArray[7][1]="60px";            		//�п�
          iArray[7][2]=100;            			//�����ֵ
          iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

          //iArray[10]=new Array();
          //iArray[10][0]="�ӷ�����";         		//����
          //iArray[10][1]="60px";            		//�п�
          //iArray[10][2]=100;            			//�����ֵ
          //iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          
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
          iArray[1][1]="180px";            		//�п�
          iArray[1][2]=100;            			//�����ֵ
          iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[2]=new Array();
          iArray[2][0]="Ͷ���˿ͻ���";         		//����
          iArray[2][1]="120px";            		//�п�
          iArray[2][2]=100;            			//�����ֵ
          iArray[2][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
           
          iArray[3]=new Array();
          iArray[3][0]="Ͷ��������";         		//����
          iArray[3][1]="120px";            		//�п�
          iArray[3][2]=100;            			//�����ֵ
          iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[4]=new Array();
          iArray[4][0]="������Ч����";         		//����
          iArray[4][1]="120px";            		//�п�
          iArray[4][2]=200;            			//�����ֵ
          iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[5]=new Array();
          iArray[5][0]="Ͷ������";         		//����
          iArray[5][1]="180px";            		//�п�
          iArray[5][2]=200;            			//�����ֵ
          iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          //iArray[6]=new Array();
          //iArray[6][0]="�����";         		//����
          //iArray[6][1]="60px";            		//�п�
          //iArray[6][2]=100;            			//�����ֵ
          //iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          //
          //iArray[7]=new Array();
          //iArray[7][0]="����";         		//����
          //iArray[7][1]="60px";            		//�п�
          //iArray[7][2]=100;            			//�����ֵ
          //iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          //
          //iArray[8]=new Array();
          //iArray[8][0]="������֪";         		//����
          //iArray[8][1]="60px";            		//�п�
          //iArray[8][2]=100;            			//�����ֵ
          //iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          //
          //iArray[9]=new Array();
          //iArray[9][0]="Ͷ������";         		//����
          //iArray[9][1]="0px";            		//�п�
          //iArray[9][2]=100;            			//�����ֵ
          //iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
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
                       

