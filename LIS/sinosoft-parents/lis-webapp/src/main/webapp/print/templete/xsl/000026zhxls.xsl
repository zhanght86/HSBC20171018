<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:java="http://xml.apache.org/xslt/java"
                exclude-result-prefixes="java"
                version="1.0">

<xsl:output method="text" encoding="UTF-8"/>

<xsl:template match="/">

<xsl:for-each select="DATASET/InsuredInfo/Insured">
  <xsl:value-of select="Name"/>
  <xsl:value-of select="Sex"/>
  <xsl:value-of select="Phone"/>
  
  <!-- 回车换行 -->
  <xsl:text>&#13;&#10;</xsl:text>
</xsl:for-each>

</xsl:template>

</xsl:stylesheet>