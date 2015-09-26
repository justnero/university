#include <iostream>
#include <chrono>
#include <stdlib.h>
#include <time.h>
#include <algorithm>
#include <random>
#include <string.h>

#define ITERATIONS_COUNT 100000
#define ARRAY_SIZE_MIN 10
#define ARRAY_SIZE_MAX 500
#define ARRAY_SIZE_STEP 10

using namespace std;

struct data_t {
	int id;
	char name[10];
	float price;

	void operator=(int v) {
		id = v;
		strcpy(name,"Item name");
		price = 1.1f;
	}

	bool operator>(const data_t &v) const {
		return id > v.id;
	}
};

auto seed = default_random_engine(chrono::system_clock::now().time_since_epoch().count());

void fill_array(data_t *a,int n) {
	for(int i=0;i<n;i++) {
		a[i] = rand();
	}
}

void shuffle_array(data_t *a,int n) {
	shuffle(a,a+n,seed);
}

void empty(data_t *a,int n) {}

void select_sort(data_t *a, int n) {
	int i,j,imax;
	for(i=n;i>1;i--) {
		imax = 1;
		for(j=1;j<=i;j++)
			if(a[j] > a[imax])
				imax = j;
		swap(a[i],a[imax]);
	}
}

void bubble_sort(data_t *a,int n) {
	int i,j;
	for(i=n;i>1;i--) 
		for(j=1;j<i;j++)
			if(a[j] > a[j+1])
				swap(a[j],a[j+1]);
}

void shell_sort(data_t *a,int n) {
	int i,j,temp,step = n/2;
	while(step > 0) {
		for(i=0;i<(n-step);i++) {
			j = i;
			while(j >= 0 && a[j] > a[j+step]) {
				swap(a[j],a[j+step]);
				j--; 
			}
		}
        step = step / 2;
	}    
}

long long pass(data_t *a,int n,void (*f)(data_t *,int)) {
	auto start_time = chrono::high_resolution_clock::now();
	for(int i=0;i<ITERATIONS_COUNT;i++) {
		shuffle_array(a,n);
		f(a,n);
	}
	auto end_time = chrono::high_resolution_clock::now();
	return chrono::duration_cast<chrono::milliseconds>(end_time-start_time).count();
}

int main(int argc,char **argv) {
	srand(time(0));
	data_t *a = new data_t[ARRAY_SIZE_MAX];
	for(int n=ARRAY_SIZE_MIN;n<=ARRAY_SIZE_MAX;n+=ARRAY_SIZE_STEP) {
		fill_array(a,n);
		cout << n << " ";
		cout << pass(a,n,empty) << " ";
		cout << pass(a,n,select_sort) << " ";
		cout << pass(a,n,bubble_sort) << " ";
		cout << pass(a,n,shell_sort) << " ";
		cout << endl;
	}
	delete a;
}