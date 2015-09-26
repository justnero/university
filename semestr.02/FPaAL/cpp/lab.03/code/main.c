#include <math.h>
#include <stdio.h>

#define max(a,b) (((a)>(b))?(a):(b))
#define min(a,b) (((a)<(b))?(a):(b))
#define SIZE 100

void sort(float *num,int ifrom, int ito) {
	int temp;
	for(int i=ifrom;i<ito;i++) {
	    for(int j=ito;j>i;j--) {
			if(num[j-1] > num[j]) {
				temp = num[j-1];
				num[j-1] = num[j];
				num[j] = temp;
			}
	    }
	}
}

int main(int argc, char** argv) {
	int n,p,q;
	printf("Введите n: ");
	scanf("%d",&n);
	printf("Введите p: ");
	scanf("%d",&p);
	printf("Введите q: ");
	scanf("%d",&q);

	float a[SIZE];
	printf("Введите %d элементов:\n",n);
	float rmax = -1.0,rmin = 1.0,sq = 0.0;
	for(int i=1;i<=n;i++) {
		scanf("%f",&a[i]);
		if(i <= q) {
			rmax = max(rmax,cos(a[i]));
		}
		if(i >= p) {
			rmin = min(rmin,sin(a[i]));
		}
		if((p <= i) && (i<= q) && (((i-p)%2 == 0) || (i == q))) {
			sq += pow(a[i],2);
		}
	}
	printf("Максимальный cos: %.2f\n",rmax);
	printf("Минимальный  sin: %.2f\n",rmin);
	printf("Корень квадратов: %.2f\n",sqrt(sq));

	sort(a,p,q);
	printf("Отсортированный массив:\n");
	for(int i=1;i<=n;i++) {
		printf("%.2f ",a[i]);
	}
	printf("\n");
	return 0;
}