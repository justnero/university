### Установка и настройка MPI на Windows и CLion

1. Скачать установщик Cygwin:

	* Для x32 (x86) – [Скачать](https://cygwin.com/setup-x86.exe)
	* Для x64          – [Скачать](https://cygwin.com/setup-x86_64.exe)

2. Скачать установщик CLion с [официального сайта](https://www.jetbrains.com/clion/download/download-thanks.html) и установить его, но пока не запускать

3. Запустить установщик Cygwin из первого шага и нажимать "Next" до появления списка зеркал

	![Список зеркал](https://github.com/justnero-ru/university/blob/master/semestr.05/%D0%A2%D0%A0%D0%A1%D0%B8%D0%9F%D0%92/%D0%98%D0%BD%D1%81%D1%82%D1%80%D1%83%D0%BA%D1%86%D0%B8%D1%8F/1.png)
	Ввести в поле "User URL" `ftp://mirror.yandex.ru/mirrors/ftp.cygwin.com/` и нажать "Add" и "Next" до появления большого окна со списком

4. В этом списке нужно выбрать следующие компоненты, нажав на надпись "Skip" напротив его названия:

	Раздел Devel: `gcc-core, gcc-g++, gdb, automake, cmake, make`

	Раздел Libs: `libopenmpi-devel, libopenmpi12, libopenmpicxx1, openmpi`

	Советую использовать строку поиска

5. Нажимать "Next" до упора для установки выбранных компонентов.

6. После установки запускаем впервые CLion, выбирая настройки среды на свой вкус, и создаём пустой проект в **стандартной** папке

7. Открываем настройки File -> Settings и переходим в раздел Tools -> External tools

8. Добавляем 2 новые конфигурации, нажав на плюсик для добавления каждой

	![Окно добавления конфигурации](https://github.com/justnero-ru/university/blob/master/semestr.05/%D0%A2%D0%A0%D0%A1%D0%B8%D0%9F%D0%92/%D0%98%D0%BD%D1%81%D1%82%D1%80%D1%83%D0%BA%D1%86%D0%B8%D1%8F/2.png)

	**Конфигурация компилятора:**
	* Name: `MPI C++`
	* Group: `MPI`
	* Program: `C:\cygwin64\bin\sh.exe`
	* Parameters: `-l -c "cd /cygdrive/c/Users/user/CLionProjects/$FileDirName$; mpic++ $FileName$ -o $FileNameWithoutExtension$.exe"`
	* Working directory: `$ProjectFileDir$`


	**Конфигурация запускатора:**
	* Name: `MPI Run`
	* Group: `MPI`
	* Program: `C:\cygwin64\bin\sh.exe`
 	* Parameters: `-l -c "cd /cygdrive/c/Users/user/CLionProjects/$FileDirName$; mpirun --mca btl ^tcp -np $Prompt$ $FileNameWithoutExtension$"`
	* Working directory: `$ProjectFileDir$`
	

	**Внимание**

	`с/Users/user/CLionProjects` – в пункте "Parameters" исправьте на ваш путь к папке `CLionProjects`, обычно достаточно заменить `user` на имя вашего пользователя

9. В проекте нажимаем правой клавишей мыши по файлу с исходниками и в разделе MPI находим MPI C++ и MPI Run. Теперь когда необходимо скомпилировать проект выбирайте MPI C++, а для запуска MPI Run.


	![Раздел MPI](https://github.com/justnero-ru/university/blob/master/semestr.05/%D0%A2%D0%A0%D0%A1%D0%B8%D0%9F%D0%92/%D0%98%D0%BD%D1%81%D1%82%D1%80%D1%83%D0%BA%D1%86%D0%B8%D1%8F/3.png)

	При запуске через MPI Run необходимо ввести, сколько процессов запускать
	![Выбор количества процессов](https://github.com/justnero-ru/university/blob/master/semestr.05/%D0%A2%D0%A0%D0%A1%D0%B8%D0%9F%D0%92/%D0%98%D0%BD%D1%81%D1%82%D1%80%D1%83%D0%BA%D1%86%D0%B8%D1%8F/4.png)

Если возникли проблемы – пишите в [ВК](http://vk.com/justnero_ru))

Инструкция может обновляться

**UPD 1:**
Если у вас очень долго запускается программа (40+ секунд) измените конфигурацию запускатора в соответствии с обновлённой (--mca btl ^tcp)
