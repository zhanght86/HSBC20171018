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
  	
 
	  //��ѯ�������ֵ���Ϣ
	  esayQueryClick();
	  
  	
  }
  catch(re) {
    alert("NotProposalQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
          iArray[1][0]="�������ֺ�";         		//����
          iArray[1][1]="180px";            		//�п�
          iArray[1][2]=100;            			//�����ֵ
          iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[2]=new Array();
          iArray[2][0]="���ֱ���";         		//����
          iArray[2][1]="120px";            		//�п�
          iArray[2][2]=100;            			//�����ֵ
          iArray[2][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
           
          iArray[3]=new Array();
          iArray[3][0]="Ͷ������";         		//����
          iArray[3][1]="120px";            		//�п�
          iArray[3][2]=100;            			//�����ֵ
          iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[4]=new Array();
          iArray[4][0]="����";         		//����
          iArray[4][1]="120px";            		//�п�
          iArray[4][2]=200;            			//�����ֵ
          iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[5]=new Array();
          iArray[5][0]="����";         		//����
          iArray[5][1]="120px";            		//�п�
          iArray[5][2]=200;            			//�����ֵ
          iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[6]=new Array();
          iArray[6][0]="��������";         		//����
          iArray[6][1]="100px";            		//�п�
          iArray[6][2]=100;            			//�����ֵ
          iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
        
          
          ContGrid = new MulLineEnter( "fm" , "ContGrid" ); 

          //��Щ���Ա�����loadMulLineǰ
          ContGrid.mulLineCount = 5;   
          ContGrid.displayTitle = 1;
          ContGrid.locked = 1;
          ContGrid.canSel = 0;
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
                       

