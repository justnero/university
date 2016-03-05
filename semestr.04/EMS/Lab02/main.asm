.include "m16def.inc"
.def a=r16
.def b=r17
.def c=r18
.def d=r19
.def count=r20

init: 
	ldi count, 7
	ldi a, 10
	ldi b, 30
	ldi r21, $FF
	ldi r22, $55
	ldi r23, $AA
	jmp main

for1:
	add d, c
	brbs SREG_V, blink
	dec count
	brne for1
	out PORTB, r21		; Вывод на порт B $FF
	ret
blink:
	ldi count, 32 		; Цикл на 32 помигивания
blinkfor:
	out PORTD, r22		; Вывод на порт D $55
	nop
	out PORTD, r23		; Вывод на порт D $AA
	nop
	dec count
	brne blinkfor
	ret

main:
	mov c, a
	add c, b
	ldi d, 0
	call for1
	
