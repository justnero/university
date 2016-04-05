main:
    in 35
    call cnt
    mov e, a
    in 36
    call cnt
    mov d, a
    call comp
    out 37
    call slp
    mvi a, 00
    out 37
    hlt

cnt:
    mvi b, 00
    mvi c, 08
cnt_for:
    rlc
    jnc cnt_no
    inr b
cnt_no:
    jnz a
    mov a, b
    ret
    
slp:
    mvi c, FF
slp_for:
    mvi b, FF
slp_for_inner:
    nop
    dcr b
    jnz slp_for_inner
    dcr c
    jnz slp_for
    ret
   
comp:
    mov a, e
    cmp d
    jnz comp_each
    mvi a, C0
    ret
comp_each:
    cmp d
    сс comp_sec
    mvi a, 80
    ret
comp_sec:
    mvi a, 40
    ret
