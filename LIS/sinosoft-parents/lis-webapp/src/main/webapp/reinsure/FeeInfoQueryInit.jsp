<%@include file="/i18n/language.jsp"%>
<%
//�������ƣ�ClientConjoinQueryInit.jsp
//�����ܣ�
//�������ڣ�2002-08-19
//������  ��Kevin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script type="text/javascript">
function initInpBox()
{ 
  try
  {                                   
    fm.all('QureyFeeCode').value = '';
    fm.all('QureyBatchNo').value = '';

  }
  catch(ex)
  {
    myAlert("��FeeInfoQueryInit.jsp-->"+"��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
    //initFeeGrid();
  }
  catch(re)
  {
    myAlert("FeeInfoQueryInit.jsp-->"+"��ʼ���������!");
  }
}
function initFeeGrid()
{
	try
  {
  	var feeno=fm.QureyFeeCode.value;
	  var batchno=fm.QureyBatchNo.value;  
    var iArray = new Array();
    var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.FeeInfoQueryInitSql"); //ָ��ʹ�õ�properties�ļ���
		mySql100.setSqlId("FeeInfoQueryInitSql100");//ָ��ʹ�õ�Sql��id
		mySql100.addSubPara(feeno);//ָ������Ĳ���
	 var strSQL=mySql100.getString();
    //var strSQL = "select * from RIFeeRateTableClumnDef where feetableno='"+feeno+"' order by feeclumnno";
	  var  result = new Array();
	  
	  result=easyExecSql(strSQL, 1, 0, 1);
	  
	  var i=0;
	  iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";            		//�п�
    iArray[0][2]=10;            			//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  var n=0;    
	  for(i=0;i<result.length;i++){
	  	colname=result[i][3];
	  	n=i+1;
	  	iArray[n]=new Array();
      iArray[n][0]=colname;         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[n][1]="50px";            		//�п�
      iArray[n][2]=10;            			//�����ֵ
      iArray[n][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������		
	  }
	  n=n+1;
	  iArray[n]=new Array();
    iArray[n][0]="������ֵ";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[n][1]="50px";            		//�п�
    iArray[n][2]=10;            			//�����ֵ
    iArray[n][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������	
    
    FeeGrid = new MulLineEnter( "fm" , "FeeGrid" ); //��Щ���Ա�����loadMulLineǰ
    FeeGrid.mulLineCount = 0;
    FeeGrid.displayTitle = 1;
    FeeGrid.hiddenPlus = 1;
    FeeGrid.hiddenSubtraction = 1;
    FeeGrid.canSel=1;
    FeeGrid.loadMulLine(iArray);
    
	}catch(ex)
  {
    myAlert(ex);
  }
}

</script>