.include "m16def.inc"
.def a=r16
.def b=r17
.def c=r18
.def d=r19
.def count=r20

init: 
	ldi a, 1			; Запись первого числа
	ldi b, 2			; Запись второго числа
	ldi r21, $FF		; Константа для вывода в случае переполнения
	ldi r22, $55		; Константа для мигания в случае удачи
	ldi r23, $AA		; Константа для мигания в случае удачи
	ldi r24, $00		; Константа для обнуления
	jmp main

mult:
	ldi count, 7		; Множитель/количество итераций сложения
mult_for:
	adc d, c
	brcs fail			; Если произошел перенос в процессе умножения	
	dec count
	brne mult_for		; Цикл for на count итераций
	jmp success			; Когда цикл закончился перейти к помигиванию
	
fail:					; Вывод $FF если переполнение
	ldi count, 32		; Цикл на 32 помигивания
	out PORTB, r21		; Вывод на порт B $FF
fail_for:
	nop
	dec count
	brne fail_for
	out PORTB, r24		; Обнулить вывод в порт B
	ret
	
success:				; Помигивание если переполнения не было
	ldi count, 32 		; Цикл на 32 помигивания
success_for:
	out PORTD, r22		; Вывод на порт D $55
	nop	
	out PORTD, r23		; Вывод на порт D $AA
	nop
	dec count
	brne success_for	; Цикл for на count итераций
	out PORTD, r24		; Обнулить вывод в порт D
	ret

main:
	mov c, a
	adc c, b
	brcs fail			; Если произошел перенос при первичном сложении
	ldi d, 0
	call mult
	
