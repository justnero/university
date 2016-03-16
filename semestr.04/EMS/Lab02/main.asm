.include "m16def.inc"
.def a=r16
.def b=r17
.def c=r18
.def d=r19
.def count=r20

init: 
	ldi a, 1			; ������ ������� �����
	ldi b, 2			; ������ ������� �����
	ldi r21, $FF		; ��������� ��� ������ � ������ ������������
	ldi r22, $55		; ��������� ��� ������� � ������ �����
	ldi r23, $AA		; ��������� ��� ������� � ������ �����
	ldi r24, $00		; ��������� ��� ���������
	jmp main

mult:
	ldi count, 7		; ���������/���������� �������� ��������
mult_for:
	adc d, c
	brcs fail			; ���� ��������� ������� � �������� ���������	
	dec count
	brne mult_for		; ���� for �� count ��������
	jmp success			; ����� ���� ���������� ������� � �����������
	
fail:					; ����� $FF ���� ������������
	ldi count, 32		; ���� �� 32 �����������
	out PORTB, r21		; ����� �� ���� B $FF
fail_for:
	nop
	dec count
	brne fail_for
	out PORTB, r24		; �������� ����� � ���� B
	ret
	
success:				; ����������� ���� ������������ �� ����
	ldi count, 32 		; ���� �� 32 �����������
success_for:
	out PORTD, r22		; ����� �� ���� D $55
	nop	
	out PORTD, r23		; ����� �� ���� D $AA
	nop
	dec count
	brne success_for	; ���� for �� count ��������
	out PORTD, r24		; �������� ����� � ���� D
	ret

main:
	mov c, a
	adc c, b
	brcs fail			; ���� ��������� ������� ��� ��������� ��������
	ldi d, 0
	call mult
	
