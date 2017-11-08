package com.sinosoft.lis.pubfun.diskimport;

import java.lang.reflect.Method;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.SchemaSet;
import com.sinosoft.utility.StrTool;

/**
 * <p>Title: 默认的磁盘导入类</p>
 * <p>Description: 只从一个Sheet中导入，而且导入的字段名和数据库绑定</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author QiuYang
 * @version 1.0
 */

public class DefaultDiskImporter extends MultiDiskImporter {

    /** 保全磁盘导入临时表的schema */
    private String schemaClassName = null;

    /** 保全磁盘导入临时表的set */
    private String schemaSetClassName = null;

    /** 存放导入结果的set */
    private SchemaSet schemaSet = null;

    /**
     * 设置schema
     * @param schemaClassName String
     */
    public void setSchemaClassName(String schemaClassName) {
        this.schemaClassName = schemaClassName;
    }

    /**
     * 设置schemaSet
     * @param schemaSetClassName String
     */
    public void setSchemaSetClassName(String schemaSetClassName) {
        this.schemaSetClassName = schemaSetClassName;
    }

    /**
     * 构造函数
     * @param fileName String
     * @param configFileName String
     * @param sheetName String
     */
    public DefaultDiskImporter(String fileName, String configFileName,
                               String sheetName) {
        super(fileName, configFileName, sheetName);
    }

    /**
     * 处理一个sheet
     * @param sheet Sheet
     * @return boolean
     */
    public boolean dealOneSheet(Sheet sheet) {
        if (!initSchemaSet()) {
            return false;
        }
        int size = sheet.getRowSize();
        for (int row = 0; row < size; row++) {
            Schema schema = createSchema(sheet, row);
            if (schema == null) {
                return false;
            }
            schemaSet.add(schema);
        }
        return true;
    }

    /**
     * 得到返回结果
     * @return SchemaSet
     */
    public SchemaSet getSchemaSet() {
        return schemaSet;
    }

    /**
     * 初始化SchemaSet
     * @return boolean
     */
    private boolean initSchemaSet() {
        try {
            if (schemaClassName == null) {
                mErrors.addOneError("没有得到schemaSetClassName！");
                return false;
            }
            schemaSet = (SchemaSet) Class.forName(schemaSetClassName).
                        newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            mErrors.addOneError("找不到" + schemaSetClassName + "！");
            return false;
        }
        return true;
    }

    /**
     * 创建schema
     * @param sheet Sheet
     * @param row int
     * @return Schema
     */
    private Schema createSchema(Sheet sheet, int row) {
        try {
            if (schemaClassName == null) {
                mErrors.addOneError("没有得到schemaClassName！");
                return null;
            }
            Class schemaClass = Class.forName(schemaClassName);
            Schema schema = (Schema) schemaClass.newInstance();
            for (int col = 0; col < sheet.getColSize(); col++) {
                String head = sheet.getHead(col);
                if ((head == null) || (head.equals(""))) {
                    continue;
                }
                String value = sheet.getText(row, col);
                String methodName = getSetMethodName(schemaClass, head);
                if (methodName == null) {
                    mErrors.addOneError("磁盘导入配置文件中" + head + "错误！");
                    return null;
                }
                //解决模版中不可见的空格问题
//                if (StrTool.cTrim(sheet.getText(row, 0)).equals("")
//                    || StrTool.cTrim(sheet.getText(row, 1)).equals("")
//                    || StrTool.cTrim(sheet.getText(row, 2)).equals("")
//                    || StrTool.cTrim(sheet.getText(row, 3)).equals("")
//                    || StrTool.cTrim(sheet.getText(row, 4)).equals("")
//                    || StrTool.cTrim(sheet.getText(row, 5)).equals("")
//                    || StrTool.cTrim(sheet.getText(row, 6)).equals("")
//                    || StrTool.cTrim(sheet.getText(row, 7)).equals("")
//                    || StrTool.cTrim(sheet.getText(row, 8)).equals("")
//                    || StrTool.cTrim(sheet.getText(row, 11)).equals("")) {
//                    int trow = row + 2;
//                    int tcol = col + 1;
//                    mErrors.addOneError("磁盘导入文件中第" + trow + "行,第" + tcol +
//                                        "列有空格，请填写相应内容！");
//                    return null;
//                }
                Class[] paramType = new Class[1];
                paramType[0] = Class.forName("java.lang.String");
                Method method = schemaClass.getMethod(methodName, paramType);

                Object[] args = new Object[1];
                args[0] = StrTool.cTrim(value);
                System.out.println("col" + col);
                System.out.println("value" + value);
                System.out.println("row" + row);
                method.invoke(schema, args);
            }
            return schema;
        } catch (Exception e) {
            e.printStackTrace();
            mErrors.addOneError("生成" + schemaClassName + "对象失败！");
            return null;
        }
    }

    /**
     * 得到shema中的所有和sheet的head相匹配的set方法
     * @param schemaClass Class
     * @param head String
     * @return String
     */
    private String getSetMethodName(Class schemaClass, String head) {
        Method[] methods = schemaClass.getMethods();
        for (int i = 0; i < methods.length; i++) {
            String methodName = methods[i].getName();
            if ((methodName.length() > 3) &&
                (methodName.substring(0, 3).equals("set"))) {
                String subName = methodName.substring(3);
                if (subName.equalsIgnoreCase(head)) {
                    return methodName;
                }
            }
        }
        return null;
    }

    /**
     * 主函数，调试用
     * @param args String[]
     */
    public static void main(String[] args) {
        String fileName = "D:\\workspace\\UI\\temp\\12000006717.xls";
        String configFileName = "D:\\workspace\\UI\\temp\\GrpDiskImport.xml";
        String sheetName = "Sheet1";
        DefaultDiskImporter importer =
                new DefaultDiskImporter(fileName, configFileName, sheetName);
        importer.setSchemaClassName(
                "com.sinosoft.lis.schema.LCInsuredListSchema");
        importer.setSchemaSetClassName(
                "com.sinosoft.lis.vschema.LCInsuredListSet");
        importer.doImport();
//        LPDiskImportSet set = (LPDiskImportSet) importer.getSchemaSet();
//        System.out.println(set.toString());
    }

    private void jbInit() throws Exception {
    }
}