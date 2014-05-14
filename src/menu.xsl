<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" 
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method='html' version='1.0' encoding='utf-8' indent='yes'/>

	<xsl:template match="/role">
			
				<input type="hidden"><xsl:attribute name="value"><xsl:value-of select="@id"/></xsl:attribute></input>
				<input type="hidden"><xsl:attribute name="value"><xsl:value-of select="name"/></xsl:attribute></input>
				<xsl:call-template name="functionTemp" />
	
	</xsl:template>

	
	<xsl:template match="role"  name="functionTemp">
		<xsl:for-each select="function">
			<dl>
				<dt> <xsl:value-of select="id"/>
					<input type="hidden"><xsl:attribute name="value"><xsl:value-of select="visible"/></xsl:attribute></input>
					<input type="hidden"><xsl:attribute name="value"><xsl:value-of select="url"/></xsl:attribute></input>
				</dt>	
				     <xsl:call-template name="subMenuTemp" />
			</dl>
		</xsl:for-each>
	</xsl:template>
	
	<xsl:template match="role/function" name="subMenuTemp"> 
		<xsl:for-each select="subMenu">
		
			<dd><xsl:value-of select="id" />
					<input type="hidden"><xsl:attribute name="value"><xsl:value-of select="visible"/></xsl:attribute></input>
					<input type="hidden"><xsl:attribute name="value"><xsl:value-of select="url"/></xsl:attribute></input>
			</dd>
		</xsl:for-each>
	</xsl:template>
	
</xsl:stylesheet>