<%
//�������ƣ�ShowTraceQueryInit.jsp
//�����ܣ���������켣��ѯ
//�������ڣ�2005-11-24 11:23
//������  ����Ρ
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
   document.all('ContNo').value= nullToEmpty("<%= tContNo %>");	
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
		initTraceGrid();
		//alert("initTraceGrid");
		initParam();
		//alert("initParam");
		LLTraceGrid();
		//alert("LLTraceGrid");
  }
  catch(re)
  {
    alert("ShowTraceQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initTraceGrid()
  {                               
    var iArray = new Array();

      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";                 //����
        iArray[0][1]="30px";                 //����
        iArray[0][2]=100;                    //����
        iArray[0][3]=0;                      //����

        iArray[1]=new Array();
        iArray[1][0]="������ʼ����";         //����
        iArray[1][1]="40px";                 //���
        iArray[1][2]=100;                    //��󳤶�
        iArray[1][3]=0;                      //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[2]=new Array();
        iArray[2][0]="������ֹ����";         //����
        iArray[2][1]="40px";                 //���
        iArray[2][2]=100;                    //��󳤶�
        iArray[2][3]=0;        		           //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[3]=new Array();
        iArray[3][0]="������Ա����";         //����
        iArray[3][1]="40px";                 //���
        iArray[3][2]=100;                    //��󳤶�
        iArray[3][3]=0;                      //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[4]=new Array();
        iArray[4][0]="������Ա����";         //����
        iArray[4][1]="40px";                 //���
        iArray[4][2]=100;                    //��󳤶�
        iArray[4][3]=0;                      //�Ƿ�����¼�룬0--���ܣ�1--����
  
        TraceGrid = new MulLineEnter( "fm" , "TraceGrid" ); 

        //��Щ���Ա�����loadMulLineǰ
        TraceGrid.mulLineCount = 0;   
        TraceGrid.displayTitle = 1;
        TraceGrid.canSel=0;
        TraceGrid.locked=1;
	      TraceGrid.hiddenPlus=1;
	      TraceGrid.hiddenSubtraction=1;
        TraceGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert("��ʼ��TraceGridʱ����"+ ex);
      }
    }
</script>
