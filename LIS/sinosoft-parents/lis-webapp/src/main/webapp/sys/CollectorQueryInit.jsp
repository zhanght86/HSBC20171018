<%
//�������ƣ�CollectorQueryInit.jsp
//�����ܣ��շ�Ա������Ϣ��ѯ
//�������ڣ�2005-09-30 11:10:36
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
   document.all('AgentCode').value= nullToEmpty("<%= tAgentCode %>");	
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

function initForm()
{
  try
  {
	
		initCollectorGrid();
		initParam();
		LLCollectorGrid();
	
  }
  catch(re)
  {
    alert("CollectorQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initCollectorGrid()
  {                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";         //����
        iArray[0][1]="30px";         //����
        iArray[0][2]=100;         //����
        iArray[0][3]=0;         //����

        iArray[1]=new Array();
        iArray[1][0]="ҵ��Ա����";         //����
        iArray[1][1]="80px";         //���
        iArray[1][2]=100;         //��󳤶�
        iArray[1][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[2]=new Array();
        iArray[2][0]="���ۻ���";         //����
        iArray[2][1]="80px";         //���
        iArray[2][2]=100;         //��󳤶�
        iArray[2][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[3]=new Array();
        iArray[3][0]="�������";         //����
        iArray[3][1]="80px";         //���
        iArray[3][2]=100;         //��󳤶�
        iArray[3][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[4]=new Array();
        iArray[4][0]="����";         //����
        iArray[4][1]="60px";         //���
        iArray[4][2]=100;         //��󳤶�
        iArray[4][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
        
        iArray[5]=new Array();
        iArray[5][0]="�Ա�";         //����
        iArray[5][1]="40px";         //���
        iArray[5][2]=40;         //��󳤶�
        iArray[5][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[6]=new Array();
        iArray[6][0]="���֤��";         //����
        iArray[6][1]="140px";         //���
        iArray[6][2]=100;         //��󳤶�
        iArray[6][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
        
        iArray[7]=new Array();
        iArray[7][0]="״̬";         //����
        iArray[7][1]="40px";         //���
        iArray[7][2]=100;         //��󳤶�
        iArray[7][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
        
        iArray[8]=new Array();
        iArray[8][0]="�绰";         //����
        iArray[8][1]="80px";         //���
        iArray[8][2]=100;         //��󳤶�
        iArray[8][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
        
        iArray[9]=new Array();
        iArray[9][0]="�ֻ�";         //����
        iArray[9][1]="80px";         //���
        iArray[9][2]=100;         //��󳤶�
        iArray[9][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

  
        CollectorGrid = new MulLineEnter( "fm" , "CollectorGrid" ); 

        //��Щ���Ա�����loadMulLineǰ
        CollectorGrid.mulLineCount = 0;   
        CollectorGrid.displayTitle = 1;
        CollectorGrid.canSel=0;
        CollectorGrid.locked=1;
	      CollectorGrid.hiddenPlus=1;
	      CollectorGrid.hiddenSubtraction=1;
        CollectorGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert("��ʼ��CollectorGridʱ����"+ ex);
      }
    }
</script>
