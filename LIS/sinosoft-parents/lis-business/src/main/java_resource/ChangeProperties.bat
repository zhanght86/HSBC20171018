(dir %1 /aa /b /s | findstr /e /c:"properties") >tmp.txt
for /f %%i in (tmp.txt) do "%JAVA_HOME%\bin\native2ascii.exe" -encoding gbk %%i %%i.tmp & del %%i /f & move %%i.tmp %%i