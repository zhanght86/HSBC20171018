package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

/**
 * Copyright (c) 2008 sinosoft Co. Ltd. All right reserved.
 */


import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Vector;

import com.sinosoft.lis.schema.LRBOMItemSchema;
import com.sinosoft.lis.schema.LRBOMSchema;
import com.sinosoft.lis.vschema.LRBOMItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;

/**
 * <p>
 * ClassName:
 * </p>
 * <p>
 * Description: 生成JAVA文件
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author:
 * @date: 2008-08-26
 */

public class BOMMaker {
private static Logger logger = Logger.getLogger(BOMMaker.class);

	private String FileName;// 生成的文件名字
	private String TableName;// 数据库中对应的BOM英文名
	private LRBOMItemSet tLRBOMItemSet; // 根据BOM查找出的词条
	private String Path = "";
	public CErrors mErrors = new CErrors();
	
	// @Constructor
	// @Method
	public String makeTable(LRBOMSchema tLRBOMSchema, String path) {
		this.Path = path;
		Vector f = new Vector();
		BOMFieldInfo tBOMFieldInfo = new BOMFieldInfo();
		TableName = tBOMFieldInfo.getFileName(tLRBOMSchema);
		tLRBOMItemSet = tBOMFieldInfo.getItem(tLRBOMSchema);
		for (int i = 0; i < tLRBOMItemSet.size(); i++) {
			f.add(tLRBOMItemSet.get(i + 1));
		}
		return create(f);
	}

	public String createAbstract(String jPath) {
		PrintWriter out = null;
		//String  sqlJava = "select sysvarvalue from ldsysvar where sysvar='ibrmsCreatJava'";
		//String jPath = new ExeSQL().getOneValue(sqlJava);
		Path = jPath; // 生成java类的路径
		String ClassName = "AbstractBOM" ; // 抽象类类名
		FileName = ClassName + ".java"; // 抽象类文件名

		try {
			out = new PrintWriter(new FileWriter(new File(Path + FileName)),
					true);

			// 文件头信息
			out.println("/**");
			out.println(" * Copyright (c) 2008 sinosoft  Co. Ltd.");
			out.println(" * All right reserved.");
			out.println(" */");
			out.println("");
			// @Package
			out.println("package com.sinosoft.ibrms.bom;");
			out.println("  ");
			out.println("public class AbstractBOM {");
			out.println("  ");
			out.println("  public String getV(String code){");
			out.println("      return null;");
			out.println("    }");
			out.println("");
			out.println("  public boolean setV(String code,String value){");
			out.println("      return true;");
			out.println("    }");
			out.println("");
			out.println("  public AbstractBOM getFatherBOM(){");
			out.println("      return null;");
			out.println("    }");
			out.println("");
			out.println("}");
		} catch (Exception e) {
			// e.printStackTrace();
			logger.debug("Create failde!!");
			logger.debug(e.toString());
			CError.buildErr(this, "创建java文件AbstractBOM.java出错，原因为"+e.toString());   
		//	System.exit(0);
		}finally{
			if(out!=null)
	    	   out.close();
		}
		return FileName;
	}

	
	public String create(Vector fields) {
		PrintWriter out = null;
		// 从sysvarvalue表中取出路径
		String  sql = "select sysvarvalue from ldsysvar where sysvar='ibrmsCreatJava'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		Path = new ExeSQL().getOneValue(sqlbv);// 生成java类的路径
		//Path = "F:\\ibms\\ui\\ibrms\\src\\com\\sinosoft\\ibrms\\bom22\\"; // 生成java类的路径
		String ClassName = TableName; // BOM类名
		FileName = ClassName + ".java"; // BOM文件名

		try {
			logger.debug("---------Creating file ......");
			logger.debug("---------开始执行表" + TableName);

			out = new PrintWriter(new FileWriter(new File(Path +"\\"+ FileName)),
					true);

			// 文件头信息
			out.println("/**");
			out.println(" * Copyright (c) 2008 sinosoft  Co. Ltd.");
			out.println(" * All right reserved.");
			out.println(" */");
			out.println("");
			// @Package
			out.println("package com.sinosoft.ibrms.bom;");

			out.println("");
			// @Import
			out.println("import java.util.*;");
			out.println("import java.text.SimpleDateFormat;");
			out.println("import com.sinosoft.utility.*;");
			out.println("import com.sinosoft.lis.pubfun.*;");
			out.println("import com.sinosoft.ibrms.BOMPreCheck;");
			out.println("import org.apache.log4j.Logger;");
			//out.println("import com.sinosoft.ibrms.bom.AbstractBOM;");
			out.println("");
			
			// 类信息
			out.println("/**");
			out.println(" * <p>ClassName: " + ClassName + " </p>");
			out.println(" * <p>Description: BOM 类文件 </p>");
			out.println(" * <p>Copyright: Copyright (c) 2008</p>");
			out.println(" * <p>Company: sinosoft </p>");
			out.println(" * @CreateDate：" + getDate());
			out.println(" */");
			out.println("");
			// @Begin
			out.println("public class " + ClassName+" extends AbstractBOM");
			out.println("{");
			out.println("");
			
			// 生成数据成员定义
			out.println("	// @Field");
			out.println("	/** 错误处理类 */");
			out.println("	public CErrors mErrors = new CErrors();");
			out.println("");
			out.println("	private FDate fDate = new FDate();		// 处理日期");
			out.println("");
			out.println("	private String value = \"\";");
			out.println("");
			String bomsql = "select fbom from lrbom where name='?ClassName?'";
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(bomsql);
			sqlbv3.put("ClassName", ClassName);
			String fbom = new ExeSQL().getOneValue(sqlbv3);
			if(fbom!=null&&fbom.trim().length()>0){
				out.println("	private "+fbom+" t"+fbom+";");
			}
			out.println("");
			
			int n = fields.size();
			for (int i = 0; i < n; i++) {
				LRBOMItemSchema f = (LRBOMItemSchema) fields.get(i);
				String FieldCode = "";
				String FieldType = "";
				// 如果获取的是Number类型
				if (f.getCommandType().equalsIgnoreCase("number")) {
					FieldCode = f.getName();
					FieldType = "Double";
				} else {
					FieldCode = f.getName();
					FieldType = f.getCommandType();
				}
				out.println("	/** " + f.getCNName() + " */");
				out.println("	private " + FieldType + " " + FieldCode + ";");
				out.println("");
			}
			out.println("");
			out.println("");

			// 生成构建器
			out.println("	// @Constructor");
			out.println("	public " + ClassName + "()");
			out.println("	{  }");
			out.println("");

			// 生成 set、get 字段的方法
			if(fbom!=null&&fbom.trim().length()>0){
			out.println("	public void setFatherBOM("+fbom+" m"+fbom+")");
			out.println("	{");
			out.println("		this.t"+fbom+" = m"+fbom+";");
			out.println("	}");
			out.println(" ");
			out.println("	public AbstractBOM getFatherBOM()");
			out.println("	{");
			out.println("		return t"+fbom+";");
			out.println("	}");
			out.println("");
			}
			
			
			for (int i = 0; i < n; i++) {
				LRBOMItemSchema f = (LRBOMItemSchema) fields.get(i);
				String FieldCode = "";
				String FieldType = "";
				FieldCode = f.getName();
				FieldType = f.getCommandType();
				if (FieldType.equalsIgnoreCase("number")) {
					out.println("	public void set" + FieldCode + "( Double "
							+ FieldCode + " )");
					out.println("	{");
					String ldcode1sql ="select codealias from ldcode1 where code1=(select precheck from lrbomitem where name='?FieldCode?' and bomname='?bomname?') and codetype like '%ibrms%'";
					SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
					sqlbv1.sql(ldcode1sql);
					sqlbv1.put("FieldCode", FieldCode);
					sqlbv1.put("bomname", f.getBOMName());
					String codeAlias = new ExeSQL().getOneValue(sqlbv1);					
					if(codeAlias.trim().length()==0){
						out.println("	  this." + FieldCode + " = " + FieldCode+";");
					}else{
					out.println("	   if(!(new BOMPreCheck()."+codeAlias+"(String.valueOf("+FieldCode+")))){");					
					out.println("	  	  return;");
					out.println("	    }else{");
					out.println("	    	this." + FieldCode + " = " + FieldCode+";");
					out.println("	   }");
					}
					out.println(" 	}");
					out.println("");
					// 生成GET
					out.println("	public Double get" + FieldCode + "()");
					out.println("	{");
					out.println("	  return " + FieldCode + ";");
					out.println("	}");
					out.println("");
				}else {
					//除Number以外的类型
					out.println("	public void set" + FieldCode + "("+FieldType+"  "
							+ FieldCode + " )");
					out.println("	{");
					String ldcode1sql ="select codealias from ldcode1 where code1=(select precheck from lrbomitem where name='?FieldCode?' and bomname='?bomname?') and codetype like '%ibrms%'";
					SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
					sqlbv2.sql(ldcode1sql);
					sqlbv2.put("FieldCode", FieldCode);
					sqlbv2.put("bomname", f.getBOMName());
					String codeAlias = new ExeSQL().getOneValue(sqlbv2);
					
					if(codeAlias.trim().length()==0){
					out.println("	  this." + FieldCode + " = " + FieldCode+";");
					}else{
					out.println("	  if(!(new BOMPreCheck()."+codeAlias+"(String.valueOf("+FieldCode+")))){");
					out.println("	    	return;");
					out.println("  	   }else{");
					out.println("	    	this." + FieldCode + " = " + FieldCode+";");
					out.println("	  }");
					}
					out.println(" 	}");
					out.println("");
					// 生成GET
					out.println("	public " +FieldType+" get" + FieldCode + "()");
					out.println("	{");
					out.println("	  return " + FieldCode + ";");
					out.println("	}");
					out.println("");
				}
			}
			out.println("");
		
			
			//生成setV(String)
			out.println("	/**");
			out.println("	* 设置对应传入参数的String形式的字段值");
			out.println("	* @param: FCode String 需要赋值的对象");
			out.println("	* @param: FValue String 要赋的值");
			out.println("	* @return: boolean");
			out.println("	**/");
			out.println("	public boolean setV(String FCode ,String FValue)");
			out.println("	{");
			out.println("");
			out.println("	  if( StrTool.cTrim( FCode ).equals( \"\" ))");
			out.println("	      return false;");
			out.println("");
			for (int i = 0; i < n; i++) {
				LRBOMItemSchema f = (LRBOMItemSchema) fields.get(i);
				String FieldCode = "";
				String FieldType = "";
				FieldCode = f.getName();
				FieldType = f.getCommandType();
				//out.println("		if( FValue != null && !FValue.equals(\"\")&&FCode.equals(String.valueOf(\""+FieldCode+"\")))");
				out.println("	if(\""+FieldCode+"\".equals(FCode))");
				out.println("		{");
				if(FieldType.equalsIgnoreCase("number")){
					out.println("			set"+FieldCode+"(Double.valueOf(FValue));");					
				}else if(FieldType.equalsIgnoreCase("date"))
				{					
					out.println("		Date d = fDate.getDate(FValue);");
					out.println("			set"+FieldCode+"(d);");
				}else {
					out.println("		    set"+FieldCode+"(FValue);");
				}
				out.println("		}");
				out.println("");
			}
				out.println("	    return true;");
				out.println("	}");
				out.println("");
				
			//生成getV
				out.println("	/**");
				out.println("	* 取得对应传入参数的String形式的字段值");
				out.println("	* @param: FCode String 希望取得的字段名");
				out.println("	* @return: String");
				out.println("	* 如果没有对应的字段，返回\"\"");
				out.println("	* 如果字段值为空，返回\"null\"");
				out.println("	**/");
				out.println("	public String getV(String FCode)");
				out.println("	{");
				out.println("");
				out.println("	  String strReturn = \"\";");
				for (int i = 0; i < n; i++) {
					LRBOMItemSchema f = (LRBOMItemSchema) fields.get(i);
					String FieldCode = "";
					String FieldType = "";
					FieldCode = f.getName();
					FieldType = f.getCommandType();
					out.println("	  if (FCode.equalsIgnoreCase(\""+FieldCode+"\"))");
					out.println("	  {");
					if(f.getCommandType().equalsIgnoreCase("date")){
						out.println("SimpleDateFormat sdf = new SimpleDateFormat(\"yyyy-MM-dd hh:mm:ss\");");	
						out.println("	     strReturn = StrTool.GBKToUnicode(String.valueOf"+"(sdf.format(get"+FieldCode+"())));");
					}else{
					    out.println("	     strReturn = StrTool.GBKToUnicode(String.valueOf"+"(get"+FieldCode+"()));");
					}    
					out.println("	  }");
				}
				out.println("	  if (strReturn.equals(\"\"))");
				out.println("	  {");
				out.println("	     strReturn = \"null\";");
				out.println("	  }");
				out.println("	  return strReturn;");
				out.println("	}");
				out.println("");	
			
			// 获取错误信息
			out.println("	// @CErrors");
			out.println("	public CErrors getErrors(){");
			out.println("		return new BOMPreCheck().mErrors;");
			out.println("	}");

			out.println("}");
		} catch (Exception e) {
			// e.printStackTrace();
			logger.debug("生成源文件失败!!");
			logger.debug(e.toString());
			CError.buildErr(this, "创建java文件"+FileName+"出错，原因为"+e.toString());   
			//System.exit(0);
		}finally{
			if(out!=null)
		       out.close();
		}
		return FileName;
	}

	
	// 得到当前日期
	private static String getDate() {
		String strReturn = "";
		int intYear = Calendar.getInstance().get(Calendar.YEAR);
		int intMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
		int intDate = Calendar.getInstance().get(Calendar.DATE);
		strReturn = "" + intYear;

		if (intMonth < 10) {
			strReturn += "-" + "0" + intMonth;
		} else {
			strReturn += "-" + intMonth;
		}

		if (intDate < 10) {
			strReturn += "-" + "0" + intDate;
		} else {
			strReturn += "-" + intDate;
		}
		return strReturn;
	}
}
