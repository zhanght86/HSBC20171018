<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWQuerySubReportInit.jsp
//�����ܣ��¼��˱�Ա���������ѯ
//�������ڣ�2002-09-24 11:10:36
//������  ��Minim
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
           
<script language="JavaScript">
function initContent() {
    document.all('Content').value = '';
}

function initForm() {
  try {
	initQuestGrid();
  
	queryClick();
  }
  catch(re) {
    alert("UWSubInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б��ĳ�ʼ��
function initQuestGrid() {                              
  var iArray = new Array();
    
  try  {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          			//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="������";    	//����
    iArray[1][1]="100px";            		//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="�˱�Ա";         			//����
    iArray[2][1]="40px";            		//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="�˱�Ա����";         			//����
    iArray[3][1]="50px";            		//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="�˱�����";         			//����
    iArray[4][1]="50px";            		//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
                         
    iArray[5]=new Array();
    iArray[5][0]="��������";         			//����
    iArray[5][1]="60px";            		//�п�
    iArray[5][2]=40;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
    
    iArray[6]=new Array();
    iArray[6][0]="¼������";         			//����
    iArray[6][1]="60px";            		//�п�
    iArray[6][2]=50;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
    
    iArray[7]=new Array();
    iArray[7][0]="¼��ʱ��";         			//����
    iArray[7][1]="50px";            		//�п�
    iArray[7][2]=50;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
    
    QuestGrid = new MulLineEnter( "fm" , "QuestGrid" ); 
    //��Щ���Ա�����loadMulLineǰ                            
    QuestGrid.mulLineCount = 0;
    QuestGrid.displayTitle = 1;
    QuestGrid.canSel = 1;
    QuestGrid.hiddenPlus = 1;
    QuestGrid.hiddenSubtraction = 1;
    QuestGrid.loadMulLine(iArray); 
    
    QuestGrid.selBoxEventFuncName = "showOne";   
  }
  catch(ex) {
    alert(ex);
  }
}

</script>

