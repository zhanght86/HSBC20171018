<%
//�������ƣ�EsDocManageInit.jsp
//�����ܣ�
//�������ڣ�2004-06-02
//������  ��LiuQiang
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {
  }
  catch(ex)
  {
    alert("��AcceptIssueDocInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
//    initSubType();
    initCodeGrid();
  }
  catch(re)
  {
    alert("AcceptIssueDocInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


/************************************************************
 *               
 *���룺          û��
 *�����          û��
 *���ܣ�          ��ʼ��CodeGrid
 ************************************************************
 */
var CodeGrid;          //����Ϊȫ�ֱ������ṩ��displayMultilineʹ��
function initCodeGrid()
{                               
    var iArray = new Array();
    
    try
    {
    	tArray = new Array();
    	tArray.push("���");
    	tArray.push("30px");
    	tArray.push(100);
    	tArray.push(0);
    	iArray.push(tArray);
    	
    	tArray = new Array();
    	tArray.push("ҵ�����");
    	tArray.push("80px");
    	tArray.push(80);
    	tArray.push(0);
    	iArray.push(tArray);
    	
    	tArray = new Array();
    	tArray.push("ҵ������");
    	tArray.push("50px");
    	tArray.push(50);
    	tArray.push(0);
    	iArray.push(tArray);
       	
    	tArray = new Array();
    	tArray.push("ҳ��");
    	tArray.push("30px");
    	tArray.push(30);
    	tArray.push(0);
    	iArray.push(tArray);
    	
    	tArray = new Array();
    	tArray.push("�������");
    	tArray.push("60px");
    	tArray.push(80);
    	tArray.push(0);
    	iArray.push(tArray);
    	
    	tArray = new Array();
    	tArray.push("��������");
    	tArray.push("50px");
    	tArray.push(30);
    	tArray.push(0);
    	iArray.push(tArray);
    	
    	tArray = new Array();
    	tArray.push("������");
    	tArray.push("50px");
    	tArray.push(30);
    	tArray.push(0);
    	iArray.push(tArray);
    	

      CodeGrid = new MulLineEnter( "fm" , "CodeGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      CodeGrid.mulLineCount = 5;   
      CodeGrid.displayTitle = 1;
      CodeGrid.hiddenSubtraction=1;
      CodeGrid.hiddenPlus=1;
      CodeGrid.canChk=1;
      CodeGrid.loadMulLine(iArray);  
    }
    catch(ex)
    {
      alert("��ʼ��CodeGridʱ����"+ ex);
    }
}
    
    
</script>
