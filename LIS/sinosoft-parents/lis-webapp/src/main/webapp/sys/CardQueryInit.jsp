<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�CardQueryInit.jsp
//�����ܣ����������ͬ���໥��ѯ��ʼ��
//�������ڣ�2006-1-10 11:10:36
//������  ��renhj
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>                        
<script language="JavaScript">

function initForm()
{
  try
  {			
    initmyGrpGrid();
  }
  catch(re)
  {
    alert("ProposalQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initmyGrpGrid()
  {     
   			                      
    var iArray = new Array();
      
     
     try
      {
      
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            	//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
      //iArray[1]=new Array();
      //iArray[1][0]="�������";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      //iArray[1][1]="50px";            		//�п�
      //iArray[1][2]=10;            			//�����ֵ
      //iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="������";         		//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��ͬ��";         		//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      myGrpGrid = new MulLineEnter( "fm" , "myGrpGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      myGrpGrid.mulLineCount =1;   
      myGrpGrid.displayTitle = 1;
      myGrpGrid.locked = 1;
      myGrpGrid.canSel = 1;
      myGrpGrid.canChk = 0;
      myGrpGrid.hiddenPlus = 1;
      myGrpGrid.hiddenSubtraction = 1;        
      myGrpGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
