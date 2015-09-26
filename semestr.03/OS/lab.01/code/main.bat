@echo off
cls

set wdir=%~dp0\tmp

echo ^> Creating directories
echo ^> Creating directories > main.log
md %wdir%\kurkchi.is21
md %wdir%\kurkchi.is21\KAEu1
md %wdir%\kurkchi.is21\KAEu2
md %wdir%\kurkchi.is21\KAEu3
echo.

echo ^> Creating first file
echo ^> Creating first file >> main.log
echo Please, input file (Ctrl+Z for stop):
copy con %wdir%\kurkchi.is21\KAEu1\arif.txt
echo.

if not exist %wdir%\kurkchi.is21\KAEu1\arif.txt goto error_create
echo ^> Coppyng first file in second
echo ^> Coppyng first file in second >> main.log
copy %wdir%\kurkchi.is21\KAEu1\arif.txt %wdir%\kurkchi.is21\KAEu2\arif.txt
echo.

if not exist %wdir%\kurkchi.is21\KAEu2\arif.txt goto error_copy
echo ^> Renaming second file to reversed name
echo ^> Renaming second file to reversed name >> main.log
ren %wdir%\kurkchi.is21\KAEu2\arif.txt fira.txt
echo.

if not -%1==- set fname=%~1
if defined EXT set fext=%EXT%
if not defined fext set fext=doc
if not defined fname goto fname_choice

:combine
echo ^> Combining two files in third
echo ^> Combining two files in third >> main.log
copy %wdir%\kurkchi.is21\KAEu1\arif.txt+%wdir%\kurkchi.is21\KAEu2\fira.txt %wdir%\kurkchi.is21\KAEu3\%fname%.%fext%
echo.

echo ^> Moving file to root dir
echo ^> Moving file to root dir >> main.log
move %wdir%\kurkchi.is21\KAEu3\%fname%.%fext% %wdir%\kurkchi.is21\%fname%.%fext%
echo.

echo ^> Typing final file content
echo ^> Typing final file content >> main.log
type %wdir%\kurkchi.is21\%fname%.%fext%
type %wdir%\kurkchi.is21\%fname%.%fext% >> main.log
echo.

echo ^> Removing all files
echo ^> Removing all files >> main.log
rd /s /q %wdir%\kurkchi.is21
echo.

goto end

:fname_choice
set fname=arif
echo Please, choose name of the file
choice /c 12 /m "1 - arif; 2 - fira"
if errorlevel 2 set fname=fira
echo.
goto combine

:error_create
echo Error: could not create file
echo Error: could not create file >> main.log
echo.

goto end

:error_copy
echo Error: second file was not copyed
echo Error: second file was not copyed >> main.log
echo.

goto end

:end
pause