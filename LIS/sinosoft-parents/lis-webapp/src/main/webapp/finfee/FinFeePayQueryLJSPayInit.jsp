<%
//�������ƣ�FinFeePayQueryLJSPayInit.jsp
//�����ܣ�
//�������ڣ�2008-11-06 9:50
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //����ҳ��ؼ��ĳ�ʼ����
%>                            

<%
	String tGetNoticeNo = "";
	try 
	{
		tGetNoticeNo = request.getParameter("Getnoticeno");
	}
	catch( Exception e )
	{
		tGetNoticeNo = null;
	}
%>
<script>
	var mGetNoticeNo = "<%=tGetNoticeNo%>";   
	if(mGetNoticeNo=="null" || mGetNoticeNo==null)
	{
   	mGetNoticeNo="";
  }
</script>
<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   

	  document.all('GetNoticeNo').value= mGetNoticeNo;
    document.all('InsuredName').value = '';
    document.all('OtherNo').value = '';
    document.all('RiskCode').value = '';
    document.all('AppntName').value = '';
  }
  catch(ex)
  {
    alert("��FinFeeQueryLJAGetInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();  
    initFinFeeQueryGrid();
  }
  catch(re)
  {
    alert("��FinFeeQueryLJAGetInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

 var QueryLJAGetGrid ;
 
// ������Ϣ�б��ĳ�ʼ��
function initFinFeeQueryGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            		//�����ֵ
      iArray[0][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�����վݺ���";   		//����
      iArray[1][1]="160px";            		//�п�
      iArray[1][2]=100;            		//�����ֵ
      iArray[1][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";		//����
      iArray[2][1]="160px";            		//�п�
      iArray[2][2]=60;            		//�����ֵ
      iArray[2][3]=0;              		//�Ƿ���������,1��ʾ������0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=200;            	        //�����ֵ
      iArray[3][3]=0;                   	//�Ƿ���������,1��ʾ������0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="����";      	   		//����
      iArray[4][1]="80px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�շѽ��";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=200;            	        //�����ֵ
      iArray[5][3]=7;                   	//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[5][22]="col4";
		iArray[5][23]="0";

      iArray[6]=new Array();
      iArray[6][0]="�����˱���";         		//����
      iArray[6][1]="120px";            		//�п�
      iArray[6][2]=200;            	        //�����ֵ
      iArray[6][3]=0;                   	//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="���ֱ���";         		//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=200;            	        //�����ֵ
      iArray[7][3]=0;                   	//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="Ӧ������";         		//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=200;            	        //�����ֵ
      iArray[8][3]=0;                   	//�Ƿ���������,1��ʾ������0��ʾ������


      QueryLJAGetGrid = new MulLineEnter( "fm" , "QueryLJAGetGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      QueryLJAGetGrid.mulLineCount = 10;   
      QueryLJAGetGrid.displayTitle = 1;
      QueryLJAGetGrid.hiddenPlus = 1;
      QueryLJAGetGrid.hiddenSubtraction = 1;
      QueryLJAGetGrid.canSel = 1;
      QueryLJAGetGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>