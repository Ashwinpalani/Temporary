setlocal
set PATH=E:\CustomReporting\CustomReporting\jdk1.8.0_231\jdk1.8.0_231\bin
java -classpath E:\CustomReporting\CustomReporting\mssql-jdbc-8.4.1.jre8.jar;E:\CustomReporting\CustomReporting\postgresql-42.2.18.jar;.;E:\CustomReporting\CustomReporting; MissingDataInsertion >> E:\CustomReporting\CustomReporting\logs\MissingDataInsertion_%date:~10%%date:~4,2%%date:~7,2%.log 2>&1