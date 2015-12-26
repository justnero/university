clear
clc

% ����
E = 12;
w = 250;
X = [6 11];
R = [2 1];

% �������
eps = 0.0001;
z1 = (R(1)+X(1))*X(2)/(R(1)+X(1)+X(2));
z = sqrt(z1^2+R(2)^2);
I = E/z;
UR = I*R;
UX = I*X;
P = UR*I;
Q = UX*I;
U1 = (UR(1)+UX(1))*UX(2)/(UR(1)+UX(1)+UX(2));
U = sqrt(U1^2+UR(2)^2);
S1 = (P(1)+Q(1))*Q(2)/(P(1)+Q(1)+Q(2));
S = sqrt(S1^2+P(2)^2);
Pt = I*E;

% �����
fprintf('\t\t����\n');
fprintf('���������� �������\n');
fprintf('E  = %2d �\n',E);
fprintf('�������� �������\n');
fprintf('w  = %4d ���/�\n',w);
fprintf('������������� �������������\n');
fprintf('Xl = %2d ��\n',X(1));
fprintf('������������� �������\n');
fprintf('Xc = %2d ��\n',X(2));
fprintf('������������� ����������\n');
for i = 1:2
    fprintf('R%d = %2d ��\n',i,R(i));
end
fprintf('\n');

fprintf('\t\t�������\n');
fprintf('��������� ������������� ������� �������\n');
fprintf('z1 = (R1+Xl)*Xc/(R1+Xl+Xc) = %10.6f ��\n',z1);
fprintf('��������� ������ �������������\n');
fprintf('z  = sqrt(z1^2+R2^2) = %10.6f ��\n',z);
fprintf('��������� ���� ���� �� ������ ���\n');
fprintf('I = E/z = %10.6f �\n',I);
fprintf('��������� ���������� �� �������������\n');
fprintf('Ul  = I*Xl = %10.6f �\n',UX(1));
fprintf('��������� ���������� �� �������\n');
fprintf('Uc  = I*Xc = %10.6f �\n',UX(2));
fprintf('��������� ���������� �� ����������\n');
for i = 1:2
    fprintf('Ur%d = I*R%d = %10.6f �\n',i,i,UR(i));
end
fprintf('��������� �������� �� �������������\n');
fprintf('Ql  = I*Ul  = %10.6f ��\n',Q(1));
fprintf('��������� �������� �� �������\n');
fprintf('Qc  = I*Uc  = %10.6f ��\n',Q(2));
fprintf('��������� �������� �� ����������\n');
for i = 1:2
    fprintf('Pr%d = I*Ur%d = %10.6f ��\n',i,i,P(i));
end
fprintf('\n');

fprintf('\t\t��������\n');
fprintf('��������� ��������� ���������� ������� �������\n');
fprintf('U1 = (Ur1+Ul)*Uc/(Ur1+Ul+Uc) = %10.6f �\n',U1);
fprintf('��������� ������ ����������\n');
fprintf('U  = sqrt(U1^2+Ur2^2) = %10.6f �\n',U);
if abs(U-E) <= eps
    fprintf('\tU  =  E - �������� �� �� ��������\n');
else
    fprintf('\tU !=  E - �������� �� �� �� ��������\n');
end
fprintf('\n');
fprintf('��������� �������� ������� �������\n');
fprintf('S1 = (Pr1+Ql)*Qc/(Pr1+Ql+Qc) = %10.6f �\n',S1);
fprintf('��������� ������ ��������\n');
fprintf('S = sqrt(S1^2+(Pr2)^2) = %10.6f ��\n',S);
fprintf('��������� ������������� ��������\n');
fprintf('Pt = I*E = %10.6f ��\n',Pt);
if abs(S-Pt) <= eps
    fprintf('\tS  =  Pt - �������� �� �� ��������\n');
else
    fprintf('\tS !=  Pt - �������� �� �� �� ��������\n');
end