.include "m16def.inc"
.def dinp=r16
.def dout=r17
.def ired=r18
.def iyel=r19
.def iboth=r20
.def ored=r21
.def oyel=r22
.def oboth=r23
.def count=r24

init: 
	ldi count, $80 
	out SPL, count
	
	ldi ired, $80
	ldi iyel, $40
	ldi iboth, $C0
	ldi ored, $F0
	ldi oyel, $0F
	ldi oboth, $FF
	jmp main

slp:
	ldi count, $05
slp_for:
	dec count
	brne slp_for
	ret
	
btn_both:
	out PORTD, oboth
	call slp
	jmp main

btn_red:
	call btn_red_sub
	jmp main
btn_red_sub:
	out PORTD, ored
	call slp
	ret

btn_yel:
	call btn_yel_sub
	jmp main
btn_yel_sub:
	out PORTD, oyel
	call slp
	ret

btn_none:
	call btn_red_sub
	call btn_yel_sub
	jmp main

main:
	in dinp, PORTA
	cp dinp, iboth
	breq btn_both
	cp dinp, ired
	breq btn_red
	cp dinp, iyel
	breq btn_yel
	jmp btn_none
	
