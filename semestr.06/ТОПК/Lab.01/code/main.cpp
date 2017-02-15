#include <iostream>

const int regex[3][15] = {
        {-1, 10, 3, 4, -1, 4,  -1, -1, 7,  9,  -1, 10, 13, 9,  9},
        {1,  2,  3, 2, 5,  -1, -1, 8,  -1, -1, 11, -1, -1, 5,  -1},
        {-1, 14, 3, 6, -1, 6,  6,  -1, 9,  -1, -1, 12, 5,  -1, 6}
};

bool validate(const char *filename, int &cnt) {
    FILE *file = fopen(filename, "r");
    int c, i = 0;
    cnt = 0;
    printf("\"");
    while ((c = getc(file)) != EOF && i != -1) {
        switch (c) {
            case 'a':
                i = regex[0][i];
                break;
            case 'b':
                i = regex[1][i];
                break;
            case 'c':
                i = regex[2][i];
                break;
            default:
                i = -1;
                break;
        }
        if (i != -1) {
            printf("%c", c);
            cnt++;
        }
    }
    printf("\"\nState = %d\n", i);
    fclose(file);
    // Конечные состояния – являются допустимым концом
    return (i == 6 || i == 9 || i == 13 || i == 14);
}

int main(int argc, char **argv) {
    int cnt;
    printf("%s on length %d", validate("input.txt", cnt) ? "Valid" : "Invalid", cnt);

    return 0;
}
