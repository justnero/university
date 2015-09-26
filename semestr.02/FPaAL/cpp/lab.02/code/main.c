#include <stdio.h>
#include <math.h>

#define abs(n) (((n)<0)?-(n):(n)) // Макрос функции модуля

int main(int argc, char** argv) {
    float a,b,xstart,xfinish,dx,x,z;
    printf("Введите a:           ");
    scanf("%f",&a);
    printf("Введите b:           ");
    scanf("%f",&b);
    printf("Введите x начальное: ");
    scanf("%f",&xstart);
    printf("Введите x конечное:  ");
    scanf("%f",&xfinish);
    printf("Введите шаг dx:      ");
    scanf("%f",&dx);
    printf("╔═════════╦═════════╗\n");
    printf("║    x    ║    z    ║\n");
    for(x=xstart;x<=xfinish;x+=dx) { 
    	printf("╠═════════╬═════════╣\n");
    	printf("║%9.3f║",x);
	    if(x <= a) { // Случай x <= a
	        if(x <= 0) { // Проверка ОДЗ
    			printf("   ОДЗ   ║\n");
	            continue;
	        }
	        // Расчёт значения функции
	        z = pow(exp((x-1)/atan(x)),1/3.)+pow(tan(x),2)+log(x)+6;
	    } else if((a < x) && (x < b)) { // Случай a < x < b
	        if((x < 0) || (x > 1)) { // Проверка ОДЗ
                printf("   ОДЗ   ║\n");
	            continue;
	        }
	        // Расчёт значения функции
	        z = pow((asin(x)+sinh(x)),(cos(x)+pow(x,2)+exp(1)));
	    } else { // Случай b <= x
	        if(x <= 0) { // Проверка ОДЗ
                printf("   ОДЗ   ║\n");
	            continue;
	        }
	        // Расчёт значения функции
	        z = pow(abs(pow(x,(1.3/x))-log10(1+x)),(1.3+pow(x,2.0))) + pow(x,5) + x;
	    }
        printf("%9.3f║\n",z);
	}
	printf("╚═════════╩═════════╝\n");
    return 0;
}

