# jenkins_acn

a.	Code file: XformWithXSL.java
b.	Location of XMLs: reports/<file>.xml
c.	Location of XSL: Root directory
d.	Location of output: reports/<file>_transformed.html
e.	Methods:
  i.  	Main: Searches for all XML files, and passes them one by one to a method called xsl
  ii. 	Xsl: Uses java's xml libraries and transforms the xml files. In the end, calls checkResults()
  iii.  checkResults: Unit test method. Code to generate a CSV with data about which files were converted successfully and which were not.

By,
Apoorva Patrikar 
