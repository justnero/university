#include <iostream>

const int regex[3][13] = {
        {-1, 8, 3, 4, -1, 4,  -1, 7,  -1, 8, 11, 7,  7},
        {1,  2,  3, 2, 5,  -1, -1, -1, 9, -1, -1, 5,  -1},
        {-1, 12, 3, 6, -1, 6,  6,  -1, -1, 10, 5,  -1, 6}
};

int validate(const char *filename, int &cnt) {
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
    return (i == -1 || i == 6 || i == 7 || i == 8 || i == 10 || i == 11 || i == 12) ? i : -2;
}

int main(int argc, char **argv) {
    int cnt;
    int res = validate("input.txt", cnt);
    printf("%s on length %d", res == -2 ? "Not full" : res == -1 ? "Invalid" : "Valid", cnt);

    return 0;
}
