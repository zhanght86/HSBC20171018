<%
//�������ƣ�NotepadQueryInit.jsp
//�����ܣ����±���ѯ
//�������ڣ�2005-09-29 16:10:36
//������  ��wuhao2
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
                       

 <SCRIPT src="../common/javascript/Common.js"></SCRIPT>

 <script language="JavaScript">
 	
//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
   document.all('PrtNo').value= nullToEmpty("<%= tPrtNo %>");	
}	
 	
//��null���ַ���תΪ��
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
} 	
 
//�����ʼ��	
function initForm()
{
  try
  {
	
		initQuestGrid();
		initParam();
		LLQuestGrid();

	
  }
  catch(re)
  {
    alert("NotepadQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
} 	
 	
 	
function initQuestGrid() 
{                              
  var iArray = new Array();
    
  try  {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          			//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="ӡˢ��";    	//����
    iArray[1][1]="90px";            		//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="���±�����";         			//����
    iArray[2][1]="280px";            		//�п�
    iArray[2][2]=400;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="¼��λ��";         			//����
    iArray[3][1]="120px";            		//�п�
    iArray[3][2]=40;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
                             
    iArray[4]=new Array();
    iArray[4][0]="¼��Ա";         			//����
    iArray[4][1]="50px";            		//�п�
    iArray[4][2]=40;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[5]=new Array();
    iArray[5][0]="¼������";         			//����
    iArray[5][1]="80px";            		//�п�
    iArray[5][2]=50;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[6]=new Array();
    iArray[6][0]="¼��ʱ��";         			//����
    iArray[6][1]="80px";            		//�п�
    iArray[6][2]=50;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    QuestGrid = new MulLineEnter( "fm" , "QuestGrid" ); 
    //��Щ���Ա�����loadMulLineǰ                         
    QuestGrid.mulLineCount = 0;
    QuestGrid.displayTitle = 1;
    QuestGrid.canSel = 1;
    QuestGrid.hiddenPlus = 1;
    QuestGrid.hiddenSubtraction = 1;
    QuestGrid.selBoxEventFuncName = "QuestGridClick"; 
    QuestGrid.loadMulLine(iArray); 
 
 
  }
  catch(ex) {
    alert(ex);
  }
}
</script> 	
 	
 	
 	
 	
 	
 	
 	
 	
