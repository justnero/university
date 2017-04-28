#include <iostream>
#include <fstream>

#define T_BEGIN 100
#define T_END 200
#define T_IDEN 300
#define T_DATA 400
#define T_COLON 500
#define T_EQUAL 501
#define T_MULT 502
#define T_DIV 503

using namespace std;

int buf_index = 0;
int buf_size = 2;
int *buf;

int N_fragment();

int N_operations();

int N_operation();

bool N_expression();

bool N_operator();

bool N_sign();

int *imem(const int size) {
    return (int *) malloc(size * sizeof(int));
}

void read() {
    buf = imem(buf_size);
    int i = 0, *tmp;
    ifstream inp("input.txt");
    while (!inp.eof()) {
        inp >> buf[i++];
        if (i == buf_size) {
            buf_size *= 2;
            tmp = buf;
            buf = imem(buf_size);
            memcpy(buf, tmp, buf_size / 2 * sizeof(int));
            free(tmp);
        }
    }
    inp.close();
    tmp = buf;
    buf = imem(i);
    memcpy(buf, tmp, i * sizeof(int));
    free(tmp);
    buf_size = i;
}

int N_fragment() {
    if (buf[buf_index] != T_BEGIN) {
        return 1;
    }
    buf_index++;
    if (buf[buf_index++] != T_COLON) {
        cout << "Illegal fragment symbol. ; expected" << endl;
        return -1;
    }
    switch (N_operations()) {
        case 1:
            return -1;
        case -1:
            cout << "Illegal fragment operation." << endl;
            return -1;
        default:
            break;
    }
    if (buf[buf_index++] != T_END) {
        cout << "Illegal fragment symbol. END expected" << endl;
        return -1;
    }
    if (buf[buf_index++] != T_COLON) {
        cout << "Illegal fragment symbol. ; expected" << endl;
        return -1;
    }
    return 0;
}

int N_operations() {
    int r = -1, c;
    while ((c = N_operation()) == 0) {
        r = c;
    }
    if (c == 1 && r == -1) {
        cout << "Illegal operations start. Iden expected" << endl;
        return -1;
    }
    return c == -1 ? 1 : r;
}

int N_operation() {
    if (buf[buf_index] != T_IDEN) {
        return 1;
    }
    buf_index++;
    if (buf[buf_index++] != T_EQUAL) {
        cout << "Illegal operation symbol. = expected" << endl;
        return -1;
    }
    if (!N_expression()) {
        return -1;
    }
    if (buf[buf_index++] != T_COLON) {
        cout << "Illegal operation end. ; expected" << endl;
        return -1;
    }
    return 0;
}

bool N_expression() {
    if (!N_operator()) {
        cout << "Illegal expression start. Operator expected" << endl;
        return false;
    }
    while (N_sign()) {
        if (!N_operator()) {
            cout << "Illegal expression part. Operator expected" << endl;
            return false;
        }
    }
    return true;
}

bool N_operator() {
    int code = buf[buf_index++];
    return code == T_IDEN || code == T_DATA;
}

bool N_sign() {
    if (buf[buf_index] == T_MULT || buf[buf_index] == T_DIV) {
        buf_index++;
        return true;
    }
    return false;
}

void parse() {
    int r = -1, c;
    while ((c = N_fragment()) == 0) {
        r = c;
    }
    if (c == 1) {
        if (r == -1) {
            cout << "Illegal fragment start. BEGIN expected" << endl;
        } else if (r == 0) {
            cout << "OK" << endl;
        }
    }
}

int main() {
    read();
    parse();
    return 0;
}