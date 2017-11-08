package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import java.util.*;
import java.lang.reflect.Method;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;

/**
 * <p>Title: P表和C数据交换，使保全数据生效</p>
 * <p>Description: 在保全确认时调用，只需在传入数组中指定要操作的表名即可</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author QiuYang
 * @version 1.0
 */

public class ValidateEdorData2
{
private static Logger logger = Logger.getLogger(ValidateEdorData2.class);

    /** 错误处理类 */
    public CErrors mErrors = new CErrors();

    private GlobalInput mGlobalInput = null;

    private MMap mMap = new MMap();

    /** C表表名列表 */
    private List mLCTables = new ArrayList();

    /** P表表名列表 */
    private List mLPTables = new ArrayList();

    /** B表表名列表 */
    private List mLBTables = new ArrayList();

    /** 保全号 */
    private String mEdorNo = null;

    /** 保全类型 */
    private String mEdorType = null;

    private String mContNo = null;

    /** 合同类型数据类型,"ContNo"是个单,"GrpContNo"是团单 */
    private String mContNoType = null;

    private String mCurrentDate = PubFun.getCurrentDate();

    private String mCurrentTime = PubFun.getCurrentTime();

    /**
     * 构造函数
     * @param tables String[] 要更新数据的表名
     * @param edorNo String
     * @param edorType String
     */
    public ValidateEdorData2(GlobalInput gi, String edorNo, String edorType,
            String contNo, String contNoType)
    {
        this.mEdorNo = edorNo;
        this.mEdorType = edorType;
        this.mContNo = contNo;
        this.mContNoType = contNoType;
        this.mGlobalInput = gi;
    }

    /**
     * 新增数据
     * @return boolean
     */
    public boolean addData(String[] tables)
    {
        initTables(tables);
        for (int i = 0; i < tables.length; i++)
        {
            String lcTableName = (String) mLCTables.get(i);
            String lpTableName = (String) mLPTables.get(i);
            SchemaSet tSchemaSet = queryLPTable(lpTableName);
            for (int j = 1; j <= tSchemaSet.size(); j++)
            {
                Schema tSchema = transferLCSchema((Schema) tSchemaSet.getObj(j),
                        lcTableName);
                mMap.put(tSchema, "DELETE&INSERT");
            }
        }
        return true;
    }

    /**
     * 保全回退数据处理，直接将P表数据付给C表
     * @return boolean
     */
    public boolean backConfirmData(String[] tables)
    {
        initTables(tables);
        for (int i = 0; i < tables.length; i++)
        {
            String lcTableName = (String) mLCTables.get(i);
            String lpTableName = (String) mLPTables.get(i);
            SchemaSet tSchemaSet = queryLPTable(lpTableName);
            for (int j = 1; j <= tSchemaSet.size(); j++)
            {
                Schema tSchema = transferLCSchema((Schema) tSchemaSet.getObj(j),
                        lcTableName);
                mMap.put(tSchema, "DELETE&INSERT");
            }
        }
        return true;
    }

    
    /**
     * 把数据备份到B表
     * @return boolean
     */
    public boolean bakData(String[] tables, String contNo)
    {
        LCContSchema tLCContSchema = new LCContSchema();
        tLCContSchema.setContNo(contNo);
        LCContSet tLCContSet = new LCContSet();
        tLCContSet.add(tLCContSchema);
        bakData(tables, tLCContSet);
        return true;
    }

    /**
     * 把数据备份到B表
     * @return boolean
     */
    public boolean bakData(String[] tables, LCContSet aLCContSet)
    {
        initTables(tables);
        for (int i = 1; i <= aLCContSet.size(); i++)
        {
            String contNo = aLCContSet.get(i).getContNo();
            for (int j = 0; j < tables.length; j++)
            {
                String lcTableName = (String) mLCTables.get(j);
                String lbTableName = (String) mLBTables.get(j);
                SchemaSet tSchemaSet = queryLCTable(lcTableName, contNo);
                for (int k = 1; k <= tSchemaSet.size(); k++)
                {
                    Schema tSchema = transferLBSchema((Schema) tSchemaSet.
                            getObj(k),
                            lbTableName);
                    mMap.put(tSchema, "DELETE&INSERT");
                }
                deleteLCTable(lcTableName, contNo);
            }
        }
        return true;
    }

    /**
     * 交换数据
     * @param tables String[]
     * @return boolean
     */
    public boolean changeData(String[] tables)
    {
        initTables(tables);
        for (int i = 0; i < tables.length; i++)
        {
            String lcTableName = (String) mLCTables.get(i);
            String lpTableName = (String) mLPTables.get(i);
            SchemaSet lpSchemaSet = queryLPTable(lpTableName);
            for (int j = 1; j <= lpSchemaSet.size(); j++)
            {
                Schema lpSchema = (Schema) lpSchemaSet.getObj(j);
                Schema newLcSchema = transferLCSchema(lpSchema, lcTableName);
                mMap.put(newLcSchema, "DELETE&INSERT");
                Schema lcSchema = queryLCTable(lpSchema, lcTableName);
                if (lcSchema != null) {
					Schema newLpSchema = transferLPSchema(lcSchema, lpTableName);
					mMap.put(newLpSchema, "DELETE&INSERT");
				}
            }
        }
        return true;
    }

    /**
     * 返回待提交的数据
     * @return MMap
     */
    public MMap getMap()
    {
        return mMap;
    }

    /**
     * 初始化交换数据要用到的表名
     * @param tables String[]
     */
    private void initTables(String[] tables)
    {
        mLCTables.clear();
        mLPTables.clear();
        mLBTables.clear();
        for (int i = 0; i < tables.length; i++)
        {
            String tableName = tables[i];
            String subTableName = tableName.substring(2);
            mLCTables.add(tableName);
            mLPTables.add("LP" + subTableName);
            mLBTables.add("LB" + subTableName);
        }
    }

    /**
     * 从P表查询
     * @return Schema
     */
    private SchemaSet queryLPTable(String lpTableName)
    {
//        String sql = "select * from " + lpTableName + " " +
//                "where EdorNo = '" + mEdorNo + "' " +
//                "and EdorType = '" + mEdorType + "' " +
//                "and " + mContNoType + " = '" + mContNo + "' ";
        String sql = "select * from " + lpTableName + " " +
                "where EdorNo = '?mEdorNo?' " +
                "and EdorType = '?mEdorType?' " +
                "and " + mContNoType + " = '?mContNo?' ";
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(sql);
        sqlbv.put("mEdorNo", mEdorNo);
        sqlbv.put("mEdorType", mEdorType);
        sqlbv.put("mContNo", mContNo);
   
        String dbClassName = "com.sinosoft.lis.db." + lpTableName + "DB";
        logger.debug(sql);
        try
        {
            Class dbClass = Class.forName(dbClassName);
            Object dbObject = (Object) dbClass.newInstance();
            Class[] paramType = new Class[1];
            paramType[0] = Class.forName("com.sinosoft.utility.SQLwithBindVariables");
            Method queryMethod = dbClass.getMethod("executeQuery", paramType);
            Object[] args = new Object[1];
            args[0] = sqlbv;
            return (SchemaSet) queryMethod.invoke(dbObject, args);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 从C表查询
     * @return Schema
     */
    private SchemaSet queryLCTable(String lcTableName, String contNo)
    {
//        String sql = "select * from " + lcTableName + " " +
//                "where ContNo = '" + contNo + "' ";
        
        String sql = "select * from " + lcTableName + " " +
                "where ContNo = '?contNo?' ";
        SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
        sqlbv1.sql(sql);
        sqlbv1.put("contNo", contNo);
        String dbClassName = "com.sinosoft.lis.db." + lcTableName + "DB";
        logger.debug(sql);
        try
        {
            Class dbClass = Class.forName(dbClassName);
            Object dbObject = (Object) dbClass.newInstance();
            Class[] paramType = new Class[1];
            paramType[0] = Class.forName("java.lang.String");
            Method queryMethod = dbClass.getMethod("executeQuery", paramType);
            Object[] args = new Object[1];
            args[0] = sqlbv1;
            return (SchemaSet) queryMethod.invoke(dbObject, args);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 根据P表查询出相应的C表数据
     * @param lpSchema Schema
     * @param lcTableName String
     * @return Schema
     */
    private Schema queryLCTable(Schema lpSchema, String lcTableName)
    {
        String dbClassName = "com.sinosoft.lis.db." + lcTableName + "DB";
        try
        {
            Class dbClass = Class.forName(dbClassName);
            Schema dbObject = (Schema) dbClass.newInstance();
            Class[] paramType = new Class[1];
            paramType[0] = Class.forName("java.lang.String");
            String[] pk = dbObject.getPK();
            for (int i = 0; i < pk.length; i++)
            {
                Method getMethod = lpSchema.getClass().getMethod("get" + pk[i], null);
                String value = (String) getMethod.invoke(lpSchema, null);
                Method setMethod = dbClass.getMethod("set" + pk[i], paramType);
                Object[] args = new Object[1];
                args[0] = value;
                setMethod.invoke(dbObject, args);
            }
            Method queryMethod = dbClass.getMethod("getInfo", null);
            
            //getInfo
            //c表数据有可能查询失败，如lcprem的加费处理
            if (((Boolean)queryMethod.invoke(dbObject, null)).booleanValue() == false) {
				return null;
			}
			else {
				Method getSchemaMethod = dbClass.getMethod("getSchema", null);
				getSchemaMethod.invoke(dbObject, null);

				return (Schema) getSchemaMethod.invoke(dbObject, null);
			}
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 把lpSchema转为lcSchema
     * @param lcSchema Schema
     * @return Schema
     */
    private Schema transferLCSchema(Schema lpSchema, String lcTableName)
    {
        String schemaClassName = "com.sinosoft.lis.schema." + lcTableName +
                "Schema";
        String dbClassName = "com.sinosoft.lis.db." + lcTableName + "DB";
        try
        {
            Class schemaClass = Class.forName(schemaClassName);
            Schema schemaObject = (Schema) schemaClass.newInstance();
            Reflections ref = new Reflections();
            ref.transFields(schemaObject, lpSchema);

            Class dbClass = Class.forName(dbClassName);
            Class[] paramType = new Class[1];
            paramType[0] = Class.forName("java.lang.String");
            Method setModifyDateMethod = dbClass.getMethod("setModifyDate",
                    paramType);
            Object[] args = new Object[1];
            args[0] = mCurrentDate;
            setModifyDateMethod.invoke(schemaObject, args);
            Method setModifyTimeMethod = dbClass.getMethod("setModifyTime",
                    paramType);
            args[0] = mCurrentTime;
            setModifyTimeMethod.invoke(schemaObject, args);
            Method setOperatorMethod = dbClass.getMethod("setOperator",
                    paramType);
            args[0] = mGlobalInput.Operator;
            setOperatorMethod.invoke(schemaObject, args);
            return schemaObject;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 把lcSchema转为lbSchema
     * @param lcSchema Schema
     * @return Schema
     */
    private Schema transferLBSchema(Schema lcSchema, String lbTableName)
    {
        String schemaClassName = "com.sinosoft.lis.schema." + lbTableName +
                "Schema";
        String dbClassName = "com.sinosoft.lis.db." + lbTableName + "DB";
        try
        {
            Class schemaClass = Class.forName(schemaClassName);
            Schema schemaObject = (Schema) schemaClass.newInstance();
            Reflections ref = new Reflections();
            ref.transFields(schemaObject, lcSchema);

            Class dbClass = Class.forName(dbClassName);
            Class[] paramType = new Class[1];
            paramType[0] = Class.forName("java.lang.String");
            Object[] args = new Object[1];
            Method setEdorNoMethod = dbClass.getMethod("setEdorNo",
                    paramType);
            args[0] = mEdorNo;
            setEdorNoMethod.invoke(schemaObject, args);
            Method setModifyDateMethod = dbClass.getMethod("setModifyDate",
                    paramType);
            args[0] = mCurrentDate;
            setModifyDateMethod.invoke(schemaObject, args);
            Method setModifyTimeMethod = dbClass.getMethod("setModifyTime",
                    paramType);
            args[0] = mCurrentTime;
            setModifyTimeMethod.invoke(schemaObject, args);
            Method setOperatorMethod = dbClass.getMethod("setOperator",
                    paramType);
            args[0] = mGlobalInput.Operator;
            setOperatorMethod.invoke(schemaObject, args);
            return schemaObject;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 把lcSchema转为lpSchema
     * @param lcSchema Schema
     * @return Schema
     */
    private Schema transferLPSchema(Schema lcSchema, String lpTableName)
    {
        String schemaClassName = "com.sinosoft.lis.schema." + lpTableName +
                "Schema";
        String dbClassName = "com.sinosoft.lis.db." + lpTableName + "DB";
        try
        {
            Class schemaClass = Class.forName(schemaClassName);
            Schema schemaObject = (Schema) schemaClass.newInstance();
            Reflections ref = new Reflections();
            ref.transFields(schemaObject, lcSchema);

            Class dbClass = Class.forName(dbClassName);
            Class[] paramType = new Class[1];
            paramType[0] = Class.forName("java.lang.String");
            Object[] args = new Object[1];
            Method setEdorNoMethod = dbClass.getMethod("setEdorNo",
                    paramType);
            args[0] = mEdorNo;
            setEdorNoMethod.invoke(schemaObject, args);
            Method setEdorTypeMethod = dbClass.getMethod("setEdorType",
                    paramType);
            args[0] = mEdorType;
            setEdorTypeMethod.invoke(schemaObject, args);
            Method setModifyDateMethod = dbClass.getMethod("setModifyDate",
                    paramType);
            args[0] = mCurrentDate;
            setModifyDateMethod.invoke(schemaObject, args);
            Method setModifyTimeMethod = dbClass.getMethod("setModifyTime",
                    paramType);
            args[0] = mCurrentTime;
            setModifyTimeMethod.invoke(schemaObject, args);
            Method setOperatorMethod = dbClass.getMethod("setOperator",
                    paramType);
            args[0] = mGlobalInput.Operator;
            setOperatorMethod.invoke(schemaObject, args);
            return schemaObject;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }


    private void deleteLCTable(String lcTableName, String contNo)
    {
        String sql = "delete from "+lcTableName+" where ContNo = '?contNo?' ";
        SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
        sqlbv2.sql(sql);
        sqlbv2.put("lcTableName", lcTableName);
        sqlbv2.put("contNo", contNo);
        
        mMap.put(sqlbv2, "DELETE");
    }

    static public void main(String args[])
    {
    }
}
