

/*
 * <p>Title: PDDeploySQLMaker</p>
 * <p>Description: 产品定义数据导出SQL</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2011-11-03
 */
package com.sinosoft.productdef;

import java.util.ArrayList;

import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.Schema;

/**
 * 
 * @author Administrator
 */
public class PDDeploySQLMaker {
    private static int MAX_LEN = 512;
    private static int SQL_LEN = 1040;
    
    private static String getTableName(Schema schema) {
        String name = schema.getClass().getName();
        name = name.substring(name.lastIndexOf(".") + 1);
        if (name.endsWith("Schema")) {
            return name.substring(0, name.length() - 6).toUpperCase();
        }
        else if (name.endsWith("DB")) {
            return name.substring(0, name.length() - 2).toUpperCase();
        }
        throw new RuntimeException("error schema : " + name);
    }

    public static String makeInsertSQL(Schema schema) {
        String table = PDDeploySQLMaker.getTableName(schema);
        String sql = "select column_name, data_type from user_tab_columns where table_name='"
                + table + "' order by column_id";
        ExeSQL exeSql = new ExeSQL();
        SSRS columns = exeSql.execSQL(sql);
        StringBuffer ret = new StringBuffer();
        ret.append("INSERT INTO ").append(table);
        StringBuffer colPart = new StringBuffer().append("(");
        StringBuffer valPart = new StringBuffer().append(" values(");
        ArrayList clobList = new ArrayList();
        StringBuffer tempCol = new StringBuffer().append(colPart);
        StringBuffer tempVal = new StringBuffer().append(valPart);
        for (int i = 1; i <= columns.getMaxRow(); i++) {
            StringBuffer colAppend = new StringBuffer().append(columns.GetText(i, 1)).append(", ");
            colPart.append(colAppend);
            tempCol.append(colAppend);
            if (tempCol.length() > SQL_LEN) {
                colPart.delete(colPart.length() - colAppend.length(), colPart.length());
                colPart.append("\n").append(colAppend);
                tempCol = new StringBuffer();
                tempCol.append("\n").append(colAppend);
            }
            String colName = columns.GetText(i, 1);
            String dataType = columns.GetText(i, 2);
            String value = schema.getV(colName);
            StringBuffer valAppend = new StringBuffer();
            if (value == null || value.equals("null")) {
                valAppend.append("null, ");
            }
            else if ("DATE".equals(dataType)) {
                valAppend.append("to_date('").append(value).append("', 'yyyy-MM-dd'), ");
            }
            else if ("CLOB".equals(dataType) || "LONG".equals(dataType) || dataType.matches(".*CHAR.*")) {
                if (value.length() > MAX_LEN) {
                    valAppend.append("'")
                            .append(value.substring(0, MAX_LEN).replaceAll("'", "''")
                                    .replaceAll("&", "'||chr(38)||'").replaceAll("\n", " ")
                                    .replaceAll("\r", " ")).append("', ");
                    clobList.add(colName + "|" + value.substring(MAX_LEN));
                }
                else {
                    valAppend.append("'")
                            .append(value.replaceAll("'", "''").replaceAll("&", "'||chr(38)||'")
                                    .replaceAll("\n", " ").replaceAll("\r", " ")).append("', ");
                }
            }
            else if ("BLOB".equals(dataType)) {
                valAppend.append("empty_blob(), ");
            }
            else {
                valAppend.append("'").append(value).append("'").append(", ");
            }
            valPart.append(valAppend);
            tempVal.append(valAppend);
            if (tempVal.length() > SQL_LEN) {
                valPart.delete(valPart.length() - valAppend.length(), valPart.length());
                valPart.append("\n").append(valAppend);
                tempVal = new StringBuffer();
                tempVal.append("\n").append(valAppend);
            }
        }
        colPart.delete(colPart.length() - 2, colPart.length());
        valPart.delete(valPart.length() - 2, valPart.length());
        colPart.append(") ");
        valPart.append(");");
        ret.append(colPart).append("\n").append(valPart);

        String pkWhere = PDDeploySQLMaker.makePKWhere(schema);
        while (true) {
            if (clobList.size() == 0) {
                break;
            }
            
            StringBuffer update = new StringBuffer().append("UPDATE ").append(table)
                    .append(" SET ");
            ArrayList clobList2 = new ArrayList();
            for (int i = 0; i < clobList.size(); i++) {
                String colVal = (String) clobList.get(i);
                int idx = colVal.indexOf("|");
                String colName = colVal.substring(0, idx);
                update.append(colName).append("=").append(colName).append("||");
                String colValue = colVal.substring(idx + 1);
                if (colValue.length() > MAX_LEN) {
                    String value = colValue.substring(0, MAX_LEN);
                    update.append("'")
                            .append(value.replaceAll("'", "''").replaceAll("&", "'||chr(38)||'").replaceAll("\n", " ").replaceAll("\r", " "))
                            .append("', ");
                    clobList2.add(colName + "|" + colValue.substring(MAX_LEN));
                }
                else {
                    update.append("'")
                            .append(colValue.replaceAll("'", "''").replaceAll("&", "'||chr(38)||'").replaceAll("\n", " ").replaceAll("\r", " "))
                            .append("', ");
                }
                update.append("\n");
            }
            update.delete(update.length() - 3, update.length());
            update.append(pkWhere);
            ret.append("\n").append(update);
            clobList = clobList2;
        }
        ret.append("\n");
        return ret.toString();
    }

    private static String makePKWhere(Schema schema) {
        String[] pks = schema.getPK();
        StringBuffer ret = new StringBuffer();
        ret.append(" WHERE 1=1 ");
        for (int i = 0; i < pks.length; i++) {
            String pk = pks[i];
            String pkv = schema.getV(pk);
            ret.append("and ").append(pk).append("='").append(pkv).append("' ");
        }
        ret.append(";");
        return ret.toString();
    }

    public static String makeDeleteSQL(Schema schema) {
        String table = PDDeploySQLMaker.getTableName(schema);
        StringBuffer ret = new StringBuffer();
        ret.append("DELETE FROM ").append(table).append(PDDeploySQLMaker.makePKWhere(schema));
        ret.append("\n");
        return ret.toString();
    }
}
