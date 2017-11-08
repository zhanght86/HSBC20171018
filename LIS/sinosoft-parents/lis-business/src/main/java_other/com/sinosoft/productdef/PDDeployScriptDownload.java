

package com.sinosoft.productdef;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDDeployScriptInfoSchema;
import com.sinosoft.lis.vdb.LDDeployScriptInfoDBSet;
import com.sinosoft.lis.vschema.LDDeployScriptInfoSet;
import com.sinosoft.utility.ExeSQL;

public class PDDeployScriptDownload {
public  static boolean recored(LDDeployScriptInfoSet aLDDeployScriptInfoSet){
	LDDeployScriptInfoSet tLDDeployScriptInfoSet=new LDDeployScriptInfoSet();
	LDDeployScriptInfoSchema schema=null;
	for (int i = 1; i <= aLDDeployScriptInfoSet.size(); i++) {
		schema =aLDDeployScriptInfoSet.get(i);
		if(schema.getVersion()==null||"".equals(schema.getVersion()))schema.setVersion(getVersion(schema));
		schema.setSeriNo(getSeri());
		schema.setMakeDate(PubFun.getCurrentDate());
		schema.setModifyDate(PubFun.getCurrentDate());
		schema.setMakeTime(PubFun.getCurrentTime());
		schema.setModifyTime(PubFun.getCurrentTime());	
		tLDDeployScriptInfoSet.add(schema);
	}
	LDDeployScriptInfoDBSet tLDDeployScriptInfoDBSet=new LDDeployScriptInfoDBSet();
	tLDDeployScriptInfoDBSet.add(tLDDeployScriptInfoSet);
	tLDDeployScriptInfoDBSet.insert();	
	return true;
}
public static boolean recored(LDDeployScriptInfoSchema aLDDeployScriptInfoSchema){
	LDDeployScriptInfoSet tLDDeployScriptInfoSet=new LDDeployScriptInfoSet();
	tLDDeployScriptInfoSet.add(aLDDeployScriptInfoSchema);
	return recored(tLDDeployScriptInfoSet);	
}
public static String getVersion(LDDeployScriptInfoSchema aLDDeployScriptInfoSchema){
	String sql="select nvl(max(to_number(version))+1,1) from lddeployscriptinfo where riskcode='"+aLDDeployScriptInfoSchema.getRiskCode()+"' and type='"+aLDDeployScriptInfoSchema.getType()+"' and name='"+aLDDeployScriptInfoSchema.getName()+"' and environment='"+aLDDeployScriptInfoSchema.getEnvironment()+"'";
	if(!"000000".equals(aLDDeployScriptInfoSchema.getRiskCode()))sql="select nvl(max(to_number(version))+1,1) from lddeployscriptinfo where riskcode='"+aLDDeployScriptInfoSchema.getRiskCode()+"' and type='"+aLDDeployScriptInfoSchema.getType()+"'"+" and environment='"+aLDDeployScriptInfoSchema.getEnvironment()+"'";
	ExeSQL tExeSQL=new ExeSQL();
	String version=tExeSQL.getOneValue(sql);
	if(version==null||"".equals(version))version="1";
	return version;
}
public static String getSeri(){
	SimpleDateFormat sd=new SimpleDateFormat("yyMMddHHmm");
	String seri=sd.format(new Date());
	return seri;
}
public static void main(String[] args) {
/*	System.out.println(getSeri());
	LDDeployScriptInfoSchema schema=new LDDeployScriptInfoSchema();
	schema.setEnvironment("01");
	schema.setType("1");
	schema.setRiskCode("000000");
	schema.setName("download.txt");
	schema.setVersion("3");
	schema.setSeriNo(getSeri());
	schema.setNote("NOTE");
	schema.setPath("ui/download/");
	schema.setOperator("001");
	LDDeployScriptInfoSet tLDDeployScriptInfoSet=new LDDeployScriptInfoSet();
	tLDDeployScriptInfoSet.add(schema);
	//recored(tLDDeployScriptInfoSet);
	//System.out.println(getVersion(schema));
*/	
	ArrayList<String> list=new ArrayList<String>();
	list.add("11111");
	list.add("22222");
	PubFun.ProductWriteToFile("Test.txt",list );
		
}
}
