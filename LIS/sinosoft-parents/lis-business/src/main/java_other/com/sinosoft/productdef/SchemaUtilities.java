

/**
 * <p>Title: PDAlgoTempLib</p>
 * <p>Description: 算法模板库</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-17
 */

package com.sinosoft.productdef;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.sinosoft.utility.*;

public class SchemaUtilities {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	public SchemaUtilities() {
	}

	/***
	 * 将fromSchema中同名字段值赋予toSchema
	 */ 
	public void convertData (Schema fromSchema, Schema toSchema) throws Exception
	{
		int fieldCount = fromSchema.getFieldCount();
		for(int fieldIndex = 0; fieldIndex < fieldCount; fieldIndex++)
		{
			String fieldName = fromSchema.getFieldName(fieldIndex);
			convertData(fromSchema, toSchema, fieldName, fieldName);
		}
	}

	/***
	 * 将fromSchema中的fromFieldCode的值传递给toSchema的toFieldCode。
	 * @param fromSchema
	 * @param toSchema
	 * @param fromFieldCode
	 * @param toFieldCode
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void convertData(Schema fromSchema, Schema toSchema, String fromFieldCode, String toFieldCode) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Method m;

		Class[] c = new Class[1];
		c[0] = String.class;
		m = fromSchema.getClass().getMethod("getV", c);
		
		Object[] o = new Object[1];
		o[0] = fromFieldCode;
		String fieldValue = (String)m.invoke(fromSchema, o);

		Class[] coreClass = new Class[2];
		coreClass[0] = String.class;
		coreClass[1] = String.class;
		m = toSchema.getClass().getMethod("setV", coreClass);

		Object[] coreObject = new Object[2];
		coreObject[0] = toFieldCode;
		coreObject[1] = fieldValue;
		m.invoke(toSchema, coreObject);
	}

	/***
	 * 如果a和b中相同字段对应的值不同，返回false，否则返回true。（字段：Operator、MakeDate、MakeTime、ModifyTime、ModifyDate除外）
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isContentSame(Schema a, Schema b)
	{
		if(a == null && b != null
				|| a != null && b == null)
		{
			return false;
		}
		
		int aFieldCount = a.getFieldCount();
		int bFieldCount = b.getFieldCount();
		for (int i = 0; i < aFieldCount; i++) {
			String fieldName = a.getFieldName(i);
			String fieldValue = a.getV(i);

			if(!fieldName.equals("Operator") && !fieldName.equals("MakeDate") && !fieldName.equals("MakeTime") && 
					!fieldName.equals("ModifyTime") && !fieldName.equals("ModifyDate"))
			for (int j = 0; j < bFieldCount; j++) {
				if (b.getFieldName(j).equals(fieldName)) {
					if (!(b.getV(j).equals(fieldValue))) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			
//			TransferData t = new TransferData();
//			t.setNameAndValue("RiskCode", "4056");
//			t.setNameAndValue("ReleasePlatform", "devlis");
//			
////			deployDataCV("4056", "devlis");
//			
//			PDDeployBL tPDDeployBL = new PDDeployBL();
//			tPDDeployBL.deployDataCV();
			
		}
		catch (Exception ex) {
 			System.out.println(ex.getMessage());
		}
	}
}
