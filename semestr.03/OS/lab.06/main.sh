#!/bin/bash
BASE_DIR="Kurkchi.Arif.IS21"
SUB_DIR="K.A.E."
NAME_1="Arif"
NAME_2="firA"

if [ -z "$1" ]; then
	echo "Введите имя третьего файла:"
	read NAME_3
else 
	NAME_3=${1}
fi
echo "Имя третьего файла: " ${NAME_3}
echo "Имя третьего файла: " ${NAME_3} > main.log

echo "Создание базового каталога"
echo "Создание базового каталога" >> main.log
mkdir ${BASE_DIR} 
cd ${BASE_DIR}

echo "Создание подкаталогов"
echo "Создание подкаталогов" >> ../main.log
mkdir ${SUB_DIR}1 && mkdir ${SUB_DIR}2 && mkdir ${SUB_DIR}3

echo "Создание первого файла"
echo "Создание первого файла" >> ../main.log
echo "Введите содержимое файла (Ctrl-D что бы завершить):"
echo "Введите содержимое файла (Ctrl-D что бы завершить):" >> ../main.log
cp /dev/pty0 ${SUB_DIR}1/${NAME_1}.txt
echo "Файл сохранён"
echo "Файл сохранён" >> ../main.log

echo "Копирование первого файла во второй"
echo "Копирование первого файла во второй" >> ../main.log
cp ${SUB_DIR}1/${NAME_1}.txt ${SUB_DIR}2/

echo "Переименование второго файла"
echo "Переименование второго файла" >> ../main.log
mv ${SUB_DIR}2/${NAME_1}.txt ${SUB_DIR}2/${NAME_2}.txt

echo "Объединение двух файлов в третьем"
echo "Объединение двух файлов в третьем" >> ../main.log
cat ${SUB_DIR}1/${NAME_1}.txt ${SUB_DIR}2/${NAME_2}.txt > ${SUB_DIR}3/${NAME_3}

echo "Перемещение третьего файла в базовый каталог"
echo "Перемещение третьего файла в базовый каталог" >> ../main.log
mv ${SUB_DIR}3/${NAME_3} ./

echo "Вывод содержимого третьего файла:"
echo "Вывод содержимого третьего файла:" >> ../main.log
cat ${NAME_3}
cat ${NAME_3} >> ../main.log

echo "Нажмите Ctrl-D что бы удалить результаты"
echo "Нажмите Ctrl-D что бы удалить результаты" >> ../main.log
cat /dev/pty0 >> /dev/null

echo "Удаление результатов"
echo "Удаление результатов" >> ../main.log
cd .. && rm -rf ${BASE_DIR}
