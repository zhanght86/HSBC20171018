package com.sinosoft.lis.pubfun;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LDTaxTableColumnConfigSchema;
import com.sinosoft.lis.tb.CachedRiskInfo;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.SchemaSet;
import com.sinosoft.utility.SysConst;

/**
 * 税额计算器
 * 
 * @author
 * 
 */
public class TaxCalculator {
	private static Logger logger = Logger.getLogger(TaxCalculator.class);

	// 净费用
	private static final String NET_AMOUNT = "NetAmount";
	// 税额
	private static final String TAX_AMOUNT = "TaxAmount";
	// 税率
	private static final String TAX = "Tax";

	// 使用险种缓存的单例对象，缓存LDTaxTableColumnConfig表
	private static CachedRiskInfo mCRI = CachedRiskInfo.getInstance();

	
	/**
	 * 根据主子表的关系，先对子表进行价税分离，之后再汇总后，设置主表的数据。
	 * @param mainSet
	 * @param subSet
	 */
	public static void calTaxAndCheckSum(SchemaSet mainSet,SchemaSet subSet){
		
		//先将子表进行价税分离
		calBySchemaSet(subSet);
		
		
		//对子表汇总，并设置价税分离数据
		for(int i=1;i<=mainSet.size();i++){
			Schema mainSchema = (Schema)mainSet.getObj(i);
			String mainTableName = getTableNameBySchema(mainSchema);
			LDTaxTableColumnConfigSchema mainConfigSchema  = mCRI
					.findLDTaxTableColumnConfigSchemaByTableCode(mainTableName);
			//因为有多币种问题，按照币种汇总
			if(mainConfigSchema!=null){
				double mainSumAmount = Double.parseDouble(mainSchema.getV(mainConfigSchema.getSumAmountName()));   //获得总表的金额
				double mainTaxAmount = 0;   //总表的税费
				double mainNetAmount = 0;    //总表的净额
				double sumSubTaxAmount = 0;
				double sumSubNetAmount = 0;
				double tax = 0;
				String currency =  mainSchema.getV(mainConfigSchema.getCurrencyName());   //当前的币种
				logger.debug("当前币种:"+currency);
				for(int n=1;n<=subSet.size();n++){
					Schema subSchema = (Schema)subSet.getObj(n);
					String subTableName = getTableNameBySchema(subSchema);
					LDTaxTableColumnConfigSchema subConfigSchema  = mCRI
							.findLDTaxTableColumnConfigSchemaByTableCode(subTableName);
					String subCurrency = subSchema.getV(subConfigSchema.getCurrencyName());
					if(currency.equals(subCurrency)){    
						//币种一样才做累计
						sumSubTaxAmount = sumSubTaxAmount + Double.parseDouble(subSchema.getV(TAX_AMOUNT));
						sumSubNetAmount = sumSubNetAmount + Double.parseDouble(subSchema.getV(NET_AMOUNT));
						tax = Double.parseDouble(subSchema.getV(TAX));
					}	
				}
				logger.debug("累计税:"+sumSubTaxAmount+"  累计净费用:"+sumSubNetAmount);
				mainTaxAmount = sumSubTaxAmount;
				mainNetAmount = PubFun.round(mainSumAmount-mainTaxAmount, 2);
				setVATValue(mainSchema, tax, mainTaxAmount, mainNetAmount);
				
				
			}
		}
	}
	
	
	/**
	 * 获取Schema对应的表名
	 * @param schema
	 * @return
	 */
	private static String getTableNameBySchema(Schema schema){
		String schemaName = schema.getClass().getSimpleName();
		String tableName = schemaName.substring(0,
				schemaName.lastIndexOf("Schema"));
		return tableName;
	}
	/**
	 * 对Set进行价税分离计算
	 * 
	 * @param set
	 */
	public static void calBySchemaSet(SchemaSet set) {
		for (int i = 1; i <= set.size(); i++) {
			calBySchema((Schema) set.getObj(i));
		}
	}

	/**
	 * 对schema行价税分离计算
	 * 
	 * @param schema
	 */
	public static void calBySchema(Schema schema) {
		// 获取费用值
		// double netAmount
		// logger.debug(schema.getClass().getSimpleName());
		String schemaName = schema.getClass().getSimpleName();
		String tableName = "";
		if(!schemaName.contains("Schema")){
			for(Class<?> class1 = schema.getClass(); class1!=Object.class; class1 = class1.getSuperclass()){
				try{
					if(class1.getSimpleName().contains("Schema")){
						tableName = class1.getSimpleName().substring(0,class1.getSimpleName().lastIndexOf("Schema"));
						break;
					}
				}catch(Exception e){
					logger.debug("获取表名字的时候出错了！请查看TaxCalculatorClass");
				}
			}
		}else{
			tableName = schemaName.substring(0,schemaName.lastIndexOf("Schema"));
		}
		logger.debug("tableName:" + tableName);
		// if (schemaColumnMap.containsKey(tableName)) {

		LDTaxTableColumnConfigSchema ldTaxTableColumnConfigSchema = mCRI
				.findLDTaxTableColumnConfigSchemaByTableCode(tableName);
		if (ldTaxTableColumnConfigSchema != null) {
			// ldTaxTableColumnConfigSchema = schemaColumnMap.get(tableName);
			String sumAmountFieldName = ldTaxTableColumnConfigSchema
					.getSumAmountName();
			// double
			String sumAmountValueStr = schema.getV(sumAmountFieldName);  //获得schema的总费用

			// 直接转换成double
			double sumAmountValue = 0;
			if (!("").equals(sumAmountValueStr)) {
				sumAmountValue = Double.parseDouble(sumAmountValueStr);
			}
			//add  zhangyingfeng  2016-07-18
			//其他退费应付表(LJSGetOther)  不存在riskCode字段，设置默认空，税率设置为"与险种无关"
//			String riskCode = schema.getV(ldTaxTableColumnConfigSchema
//					.getRiskCodeName());
			String riskCode="";
			try {
			riskCode = schema.getV(ldTaxTableColumnConfigSchema
					.getRiskCodeName());
			} catch (Exception e) {
				 riskCode="";
			}
			//end   zhangyingfeng  2016-07-18
			String feeCode = schema.getV(ldTaxTableColumnConfigSchema
					.getFeeTypeName());
			String manageCom = schema.getV(ldTaxTableColumnConfigSchema
					.getManageComName());
			String currency = schema.getV(ldTaxTableColumnConfigSchema
					.getCurrencyName());
			double tax = getTax(feeCode, riskCode, manageCom, currency);
			//add zhangyingfeng 2016-07-18
			//tax=-1 属于免税项 ,税额 取0.0 净额 取 总额
			//tax！=-1 应税项 正常计算
			if(tax==-1){
				setVATValue(schema, tax, 0.0, sumAmountValue);
			}else{
			// 计算税额
			double taxAmount = calTaxAmount(sumAmountValue, (double)6/100);
			// 计算净值 = 总费用-税额
			double netAmount = sumAmountValue - taxAmount;

			setVATValue(schema, tax, taxAmount, netAmount);
			}
			//end   zhangyingfeng  2016-07-18
		}
	}


	/**
	 * 设置税，税额和净值
	 * @param schema
	 * @param tax
	 * @param taxAmount
	 * @param netAmount
	 */
	private static void setVATValue(Schema schema, double tax,
			double taxAmount, double netAmount) {
		// 反向设置schema
		// 税
//		setBean(schema, TAX, tax);
		// 税额
//		setBean(schema, TAX_AMOUNT, taxAmount);
		// 净值
//		setBean(schema, NET_AMOUNT, netAmount);

		// 或者不反射，可以直接调用setV方法
		//由于系统schema 命名规范与java当前命名机制冲突，调用setBean(Object source, String beanName, Object beanValue) 异常抛出，直接调用schema本类set
		  schema.setV(TAX, String.valueOf(tax)); 
		  schema.setV(TAX_AMOUNT,String.valueOf(taxAmount)); 
		  schema.setV(NET_AMOUNT,String.valueOf(netAmount));
		 
	}

	/**
	 * 暂时不用算法描述，直接java计算 税额=实际保费/（1+税率）*税率，纯保费=实际保费-税额。
	 * 
	 * @param sumAmount
	 * @param tax
	 * @return
	 */
	private static double calTaxAmount(double sumAmount, double tax) {
		double taxAmount = 0;
	    taxAmount = PubFun.round(sumAmount*tax/(1+tax), 2);
		return taxAmount;
	}

	/**
	 * 按照费用类型和险种编码获得税率 使用public方法，可供外部程序直接调用
	 * 
	 * @param feeCode
	 * @param riskCode
	 * @param manageCom
	 * @param currency
	 * @return
	 */
	public static double getTax(String feeCode, String riskCode,
			String manageCom, String currency) {
		ArrayList<String> list = new ArrayList<String>();
		StringBuffer managetComStr = new StringBuffer();
		if(manageCom!=null && !manageCom.equals("") && manageCom.length()>2){
			for(int i=0;i<manageCom.length();i=i+2){
				list.add(manageCom.substring(0, manageCom.length()-i));
			}
		}else{
			list.add("86");
		}
		ExeSQL exesql = new ExeSQL();
		StringBuffer sbSQL = new StringBuffer();
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			sbSQL.append("SELECT taxrate FROM LDVATConfig a,LDVATTaxConfig b ");
			sbSQL.append(" WHERE a.id = b.ConfigID AND a.IsTaxable = 'Y' ");
			sbSQL.append(" AND NOW() BETWEEN b.StartDate AND b.EndDate AND ");
			sbSQL.append(" (CASE a.IsReleToRiskType WHEN 'Y' THEN b.RiskGrpCode else '1' end ) = (CASE a.IsReleToRiskType WHEN 'Y' THEN (SELECT RiskGrp FROM LDVATGrp WHERE RiskCode ='"+"?riskCode?"+"') else '1' end )");
			sbSQL.append(" AND a.FeeTypeCode = '"+"?feeCode?"+"' ");
			sbSQL.append(" AND b.ManageCom IN ("+"?ManageCom?"+") order by b.ManageCom DESC limit 1");
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			sbSQL.append("select taxrate from (SELECT rownum,taxrate FROM LDVATConfig a,LDVATTaxConfig b ");
			sbSQL.append(" WHERE a.id = b.ConfigID AND a.IsTaxable = 'Y' ");
			sbSQL.append(" AND NOW() BETWEEN b.StartDate AND b.EndDate ");
			sbSQL.append(" and (CASE a.IsReleToRiskType WHEN 'Y' THEN b.RiskGrpCode else '1' end ) = (CASE a.IsReleToRiskType WHEN 'Y' THEN (SELECT RiskGrp FROM LDVATGrp WHERE RiskCode ='"+"?riskCode?"+"') else '1' end )");
			sbSQL.append(" AND a.FeeTypeCode = '"+"?feeCode?"+"' ");
			sbSQL.append(" AND b.ManageCom IN ("+"?ManageCom?"+") order by b.ManageCom DESC ) where rownum = 1");
		}
		SQLwithBindVariables sbv = new SQLwithBindVariables();
		sbv.sql(sbSQL.toString());
		sbv.put("riskCode",riskCode);
		sbv.put("feeCode",feeCode);
		sbv.put("ManageCom",list);
//		String trate=exesql.getOneValue(sbv);
//		double taxRate=0;
//		if(trate!=null && !trate.equals("")){
//			taxRate = Double.parseDouble(trate);
//		}
		//add zhangyingfeng 2016-07-18
		//如果非应税业务，不存在税率   税率返回 -1
		SSRS  ssrs=exesql.execSQL(sbv);
		double taxRate=-1;
		if(ssrs.MaxRow>0){
			taxRate = Double.parseDouble(ssrs.GetText(1, 1));
		}
		//end zhangyingfeng 2016-07-18
		return taxRate;

	}

	/**
	 * set beanValue to source's field which name is beanName
	 * 
	 * @param source
	 * @param beanName
	 * @param beanValue
	 */
	private static void setBean(Object source, String beanName, Object beanValue) {
		try {
			if (!PropertyUtils.isWriteable(source, beanName))
				throw new IllegalAccessException(beanName + " of " + source
						+ " is not writable!");
			if (beanValue == null) {
				PropertyUtils.setProperty(source, beanName, beanValue);
				return;
			}
			Class<?> propertyType = PropertyUtils.getPropertyType(source,
					beanName);
			Class<?> valueType = beanValue.getClass();
			if (propertyType.isAssignableFrom(valueType)) {
				PropertyUtils.setProperty(source, beanName, beanValue);
			} else {
				if (propertyType.equals(int.class)) {
					if (beanValue instanceof String) {
						if (((String) beanValue).length() != 0) {
							int value = Integer.parseInt((String) beanValue);
							PropertyUtils.setProperty(source, beanName, value);
						}
					} else if (beanValue instanceof Integer) {
						PropertyUtils.setProperty(source, beanName, beanValue);
					} else if (beanValue instanceof Double) {

						int value = Integer.parseInt(new DecimalFormat("0")
								.format(beanValue));
						PropertyUtils.setProperty(source, beanName, value);
					}
				}
				if (propertyType.equals(double.class)) {
					if (beanValue instanceof String) {
						if (((String) beanValue).length() != 0) {
							double value = Double
									.parseDouble((String) beanValue);
							PropertyUtils.setProperty(source, beanName, value);
						}
					} else if (beanValue instanceof Double) {
						PropertyUtils.setProperty(source, beanName, beanValue);
					}
				}
				if (propertyType.isAssignableFrom(Date.class)) {
					if (beanValue instanceof String) {
						Date date = new FDate().getDate((String) beanValue);
						PropertyUtils.setProperty(source, beanName, date);
					}
				}
				if (propertyType.isAssignableFrom(String.class)) {
					if (beanValue instanceof Date) {
						String date = new FDate().getString((Date) beanValue);
						PropertyUtils.setProperty(source, beanName, date);
					}
					if (beanValue instanceof Integer) {
						String integer = String.valueOf(beanValue);
						PropertyUtils.setProperty(source, beanName, integer);
					}
					if (beanValue instanceof Boolean) {
						if ((Boolean) beanValue)
							PropertyUtils.setProperty(source, beanName, "1");
						else
							PropertyUtils.setProperty(source, beanName, null);
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	// private static
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TaxCalculator.getTax("PT","111301","86110001","001");
//		LJAPayPersonSet set = new LJAPayPersonSet();
//		set.add(ljaPayPersonSchema);
//		TaxCalculator.calBySchemaSet(set);
 
// StringBuffer sbManageCom = new StringBuffer();
// String manageCom = "1234567890";
//	if(manageCom!=null && !manageCom.equals("") && manageCom.length()>2){
//		for(int i=0;i<manageCom.length();i=i+2){
//			sbManageCom.append("'"+manageCom.substring(0, manageCom.length()-i)+"'");
//			if(i+2<manageCom.length()){
//				sbManageCom.append(",");
//			}else{
//			}
//		}
//	}else{
//		sbManageCom.append("86");
//	}
//	logger.debug(sbManageCom.toString());

	}

}
