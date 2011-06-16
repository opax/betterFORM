<xsl:stylesheet version="2.0"
                xmlns="http://www.w3.org/1999/xhtml"
                xmlns:xhtml="http://www.w3.org/1999/xhtml"
                xmlns:xf="http://www.w3.org/2002/xforms"
                xmlns:ev="http://www.w3.org/2001/xml-events"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                exclude-result-prefixes="xf ev xsd xhtml" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes"  omit-xml-declaration="yes"/>
    
    <!-- author: Lars Windauer -->
    <xsl:template match="/">
        <xsl:element name="script" xmlns="">
            <xsl:attribute name="type">text/javascript</xsl:attribute>
             var types = {
                    label: "name",
                    identifier: "typeName",
                    items: [
                        <xsl:for-each select="//xsd:simpleType[string-length(@name) != 0]">
                            <xsl:choose>
                                <xsl:when test="@name='versionList' or @name='versionNumber' or
                                                @name='XPathExpression' or @name='QNameList' or
                                                @name='QNameButNotNCNAME' or @name='appearanceType'"></xsl:when>
                                <xsl:otherwise><xsl:variable name="type" select="current()/@name"/>{typeName:<xsl:value-of select="$type"/>, name:"<xsl:value-of select="$type"/>"}<xsl:if test="position()!=last()">,</xsl:if><xsl:text>
                        </xsl:text>
                                    <xsl:message>Type: <xsl:value-of select="$type"/></xsl:message></xsl:otherwise>
                            </xsl:choose>
                        </xsl:for-each><xsl:text>
                </xsl:text>]<xsl:text>
          };</xsl:text><xsl:text>
</xsl:text></xsl:element>        
    </xsl:template>
</xsl:stylesheet>
