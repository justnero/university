clear
clc

% ������ �� �������
E = 17;
J = 9;
R = [2 8];

% �������
A = [1     1
     R(1) -R(2)];
B = [J
    -E];
I = A\B;

% �������� ����� ������ ���������
Ppr = I(1)^2*R(1)+I(2)^2*R(2);
Pist = J*I(1)*R(1)+I(2)*E;

% �����
fprintf('\t\t����\n');
fprintf('���������� �������\n');
fprintf('E  = %3d �\n',E);
fprintf('���� ���� �������\n');
fprintf('J  = %3d �\n',J);
fprintf('�������������\n');
for i = 1:2
    fprintf('R%d = %3d ��\n',i,R(i));
end
fprintf('\n');

fprintf('\t\t�������\n');
fprintf('����� ���� �� ��������� ��������:\n');
fprintf('{ I1 + I2 =  J\t{    I1 +    I2 =  J\t{    I1 +    I2 =  %2d\n',J);
fprintf('{ U1 - U2 = -E\t{ R1*I1 - R2*I2 = -E\t{ %2d*I1 - %2d*I2 = -%2d\n',R(1),R(2),E);
fprintf('�������:\n');
for i = 1:2
    fprintf('{ I%d = %10.6f �\n',i,I(i));
end
fprintf('\n');

fprintf('\t\t��������\n');
fprintf('��������� �������� ���������\n');
fprintf('Pist = R1*I1*J +  E*I2   = %10.6f ��\n',Pist);
fprintf('��������� �������� ��������\n');
fprintf('Ppr  = R1*I1^2 + R2*I2^2 = %10.6f ��\n',Ppr);
fprintf('�����, ��� Pist = Ppr ������������� ������ ������ ���������\n');