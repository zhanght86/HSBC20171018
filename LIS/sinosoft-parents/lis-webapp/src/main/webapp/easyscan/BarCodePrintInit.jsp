<script language="text/javascript">
function initForm(){
alert("test");
}
  function initSubtype(){
	String sql="select subtype,subtypename from es_doc_def where busstype like '"+busstype+"' order by subtype";

	var sqlid0="BarCodePrintInitSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("easyscan.BarCodePrintInitSql"); 
	mySql0.setSqlId(sqlid0);
	mySql0.addSubPara(busstype);
	sql=mySql0.getString();

	turnPage.strQueryResult = easyQueryVer3(sql,1,1,1);
	arrDataSet = turnPage.strQueryResult;
	fm.SubType.value="";
	fm.SubType.CodeData = arrDataSet;
	
	}
</script>
