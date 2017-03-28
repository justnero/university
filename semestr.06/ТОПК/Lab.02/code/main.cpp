# include <iostream>
# include <fstream>

using namespace std;
ofstream fout;
string my;
int i = 0;
int trans[18][15] = {
        {10,  1,   1,   804, 804, 400, 1,   1,   1,   1,   1,   1,   1,   1,   1},
        {7,   1,   1,   804, 804, 400, 1,   1,   1,   1,   11,  1,   1,   1,   1},
        {1,   1,   1,   804, 804, 400, 1,   1,   1,   1,   1,   12,  1,   1,   1},
        {1,   1,   1,   804, 804, 400, 1,   1,   1,   1,   1,   1,   13,  1,   1},
        {1,   1,   1,   804, 804, 400, 1,   8,   1,   1,   1,   1,   1,   14,  1},
        {1,   1,   1,   804, 804, 400, 1,   1,   9,   1,   1,   1,   1,   1,   1},
        {6,   6,   1,   804, 804, 400, 6,   6,   6,   6,   6,   6,   6,   6,   6},
        {1,   1,   1,   4,   804, 400, 1,   1,   1,   1,   1,   1,   1,   1,   1},
        {1,   1,   1,   804, 804, 400, 1,   1,   1,   1,   1,   1,   1,   1,   1},
        {802, 2,   2,   804, 5,   5,   2,   2,   2,   2,   2,   2,   2,   2,   2},
        {3,   2,   2,   804, 5,   5,   2,   2,   2,   2,   2,   2,   2,   2,   2},
        {802, 2,   2,   804, 804, 400, 2,   2,   2,   2,   2,   2,   2,   2,   2},
        {802, 2,   2,   804, 804, 400, 2,   2,   2,   2,   2,   2,   2,   2,   2},
        {500, 803, 803, 804, 804, 400, 300, 801, 801, 200, 801, 801, 801, 801, 100},
        {501, 803, 803, 804, 804, 400, 300, 801, 801, 200, 801, 801, 801, 801, 100},
        {502, 803, 803, 804, 804, 400, 300, 801, 801, 200, 801, 801, 801, 801, 100},
        {503, 803, 803, 804, 804, 400, 300, 801, 801, 200, 801, 801, 801, 801, 100},
        {800, 803, 803, 804, 804, 400, 300, 801, 801, 200, 801, 801, 801, 801, 100}
};

string pass(string text, int *code) {
    if (((*code > 99) & (*code < 500))) {
        char c = text[text.length() - 1];
        text.erase(text.length() - 1);
        cout << text << " - Допущено с кодом = " << *code << endl;
        text.clear();
        if (c != ' ')
            i--;
        fout << *code << " ";
    } else if (*code < 800) {
        if (text != " ") {
            cout << text << " - Допущено с кодом = " << *code << endl;
            fout << *code << " ";
        }
        text.clear();
    } else {
        if (text != " ") {
            cout << text << " - Не допущено с кодом = " << *code << endl;
            fout << *code << " ";
        }
        text.clear();
    }
    *code = 0;
    return text;
}

int main() {
    setlocale(LC_ALL, "Russian");
    ifstream fin("input.txt");
    fout.open("codes.txt");
    char c;
    string word = "";
    getline(fin, my);
    if (my[my.length() - 1] != ' ')
        my.push_back(' ');
    int cond = 0, row;
    while (i < my.length()) {
        c = my[i];
        i++;
        word.push_back(c);
        if (isalpha(c)) {
            switch (toupper(c)) {
                case 'B':
                    row = 0;
                    break;
                case 'E':
                    row = 1;
                    break;
                case 'G':
                    row = 2;
                    break;
                case 'I':
                    row = 3;
                    break;
                case 'N':
                    row = 4;
                    break;
                case 'D':
                    row = 5;
                    break;
                case 'S':
                    row = 6;
                    break;
                case 'O':
                    row = 7;
                    break;
                default:
                    row = 8;
            }
        } else {
            if (isdigit(c)) {
                switch (c) {
                    case '0':
                        row = 10;
                        break;
                    case '8':
                        row = 11;
                        break;
                    case '9':
                        row = 12;
                        break;
                    default:
                        row = 9;
                }
            } else {
                switch (c) {
                    case ';':
                        row = 13;
                        break;
                    case '=':
                        row = 14;
                        break;
                    case '*':
                        row = 15;
                        break;
                    case '/':
                        row = 16;
                        break;
                    default:
                        row = 17;
                }
            }
        }
        cond = trans[row][cond];
        if (cond > 99 || my.length() == i) {
            word = pass(word, &cond);
        }
    }

    fout << 999;
    fout.close();
    fin.close();
    return 0;
}