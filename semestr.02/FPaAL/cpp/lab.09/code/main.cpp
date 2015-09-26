#include <graphics.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define max(a,b) (((a)>(b))?(a):(b))
#define min(a,b) (((a)<(b))?(a):(b))

#define KEY_ESC	  0x1b
#define KEY_PLUS  -0x55
#define KEY_MINUS -0x53

int maxX,maxY;
int vx,vy;
int cx,cy;
int offset_x,offset_y;
double scale;

double f(double x) {
	return 6*pow(x,2) + 3*x;
}
char *doubletos(double j) {
	char *buffer = new char[10];
	sprintf(buffer,"%.1lf",j);
	return buffer;
}

void draw_header() {
	cleardevice();

	setcolor(WHITE);
	setviewport(0,0,maxX,maxY,1);

	settextjustify(CENTER_TEXT,TOP_TEXT);
	setlinestyle(SOLID_LINE,0,NORM_WIDTH);
	setfillstyle(EMPTY_FILL,0);

	int height = textheight("H");
	char *msg = new char[128];
	sprintf(msg,"Function: 6*x^2 + 3*x | Scale: %.1lf (press +/- to zoom in/out, Home for reset) | Press ESC for exit",scale);

	outtextxy(maxX/2,2,msg);
	rectangle(0,height+4,maxX-1,maxY-1);

	setviewport(1,height+5,maxX-2,maxY-2,1);
	vx = maxX-3;
	vy = maxY-height-7;
}

void draw_axis() {
	setcolor(BLUE);

	line(0,cy,vx,cy); // Ox
	line(cx,0,cx,vy); // Oy

	line(vx-10,cy-5,vx,cy);
	line(vx-10,cy+5,vx,cy);

	line(cx,0,cx-5,10);
	line(cx,0,cx+5,10);

	outtextxy(vx-10,cy+20,"X");
	outtextxy(cx+10,10,"Y");
}

void draw_legend() {
	double i,j;
	char *s;
	setcolor(BLUE);
	int height = textheight("H");

	j=0;
	do {
		i = cx-j*scale;
		line(floor(i),cy-5,floor(i),cy+5);
		if(j != 0) {
			s = doubletos(-j);
			outtextxy(floor(i),cy+10,s);
		}
		i = cx+j*scale;
		line(floor(i),cy-5,floor(i),cy+5);
		if(j != 0) {
			s = doubletos(j);
			outtextxy(floor(i),cy+10,s);
		}
		j += 0.5;
	} while(floor(i) < vx);

	j=0;
	do {
		i = cy-j*scale;
		line(cx+3,floor(i),cx-3,floor(i));
		if(j != 0 && (floor(i) >= 0)) {
			s = doubletos(j);
			outtextxy(cx+20,floor(i)-height/2,s);
		}
		i = cy+j*scale;
		line(cx+3,floor(i),cx-3,floor(i));
		if(j != 0 && (floor(i)+height/2 <= vy)) {
			s = doubletos(-j);
			outtextxy(cx+20,floor(i)-height/2,s);
		}
		j += 0.5;
	} while(floor(i) < vy);
}

void draw_function() {
	int x0,y0,x1,y1;
	double x;
	setcolor(RED);
	for(x0=0;x0<=vx;x0++) {
		x = (x0-cx)/scale;
		y0 = floor(cy-scale*f(x));
		if(x0>0 && fabs(y1) <= vy) {
			line(x0,y0,x1,y1);
		}
		x1 = x0;
		y1 = y0;
	}
}

bool act(int c) {
	switch(c) {
		case KEY_HOME:
			scale = 100;
			return true;
		case KEY_PLUS:
		case '+':
			scale = min(scale+1,500);
			return true;
		case KEY_MINUS:
		case '-':
			scale = max(scale-1,50);
			return true;
		default:
			return false;
	}
}

int main(int argc,char **argv) {
	char c;
	int Driver = DETECT,Mode;
	initwindow(1280,720);
	//initgraph(&Driver,&Mode,"");
	
	int errorcode;
	if((errorcode = graphresult()) != grOk) {
		printf("Graphics error: %s\n",grapherrormsg(errorcode));
		printf("Press any key to exit:");
		getch();
		getch();
		exit(1);
	}

	scale = 100.0;
	maxX = getmaxx();
	maxY = getmaxy();
	cx = maxX/2;
	cy = maxY/2;
	bool draw = true;
	do {
		if(draw) {
			draw_header();
			draw_axis();
			draw_legend();
			draw_function();
			draw = false;
		}
		c = getch();
		draw = act(c);
	} while(c != KEY_ESC);
	
	closegraph();
	return 0;
}