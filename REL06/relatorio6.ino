int A = 0;
int B = 0;
char S = '0';
const int logical_0 = 0000;
const int logical_1 = 1111;
int led10 = 10; //azul
int led11 = 11; //verde
int led12 = 12; //amarelo
int led13 = 13; //vermelho
int saida;

void setup()
{
	Serial.begin(9600);
	pinMode(led10, OUTPUT);
	pinMode(led11, OUTPUT);
	pinMode(led12, OUTPUT);
	pinMode(led13, OUTPUT);
}

void loop()
{

	if (Serial.available() > 0)
	{

		A = Serial.parseInt();
		B = Serial.parseInt();
		S = Serial.parseInt();

		A = hexToDec(A);
		B = hexToDec(B);

		if (Serial.read() == '\n')
		{

			if (s == '0')
			{
				saida = logical_0;
			}
			else if (s == '1')
			{
				saida = logical_1;
			}
			else if (s == '2')
			{
				saida = NOT(A);
			}
			else if (s == '3')
			{
				saida = NOT(B);
			}
			else if (s == '4')
			{
				saida = OR(A, B);
			}
			else if (s == '5')
			{
				saida = AND(A, B);
			}
			else if (s == '6')
			{
				saida = XOR(A, B);
			}
			else if (s == '7')
			{
				saida = NAND(A, B);
			}
			else if (s == '8')
			{
				saida = NOR(A, B);
			}
			else if (s == '9')
			{
				saida = XNOR(A, B);
			}
			else if (s == 'A')
			{
				saida = OR(NOT(A), B);
			}
			else if (s == 'B')
			{
				saida = OR(A, NOT(B));
			}
			else if (s == 'C')
			{
				saida = AND(NOT(A), B);
			}
			else if (s == 'D')
			{
				saida = AND(A, NOT(B));
			}
			else if (s == 'E')
			{
				saida = OR(NOT(A), NOT(B));
			}
			else if (s == 'F')
			{
				saida = AND(NOT(A), NOT(B));
			}

			Serial.println(saida);
			display(saida);
			delay(500);
		}
	}
} //end ula
//FUNCOES
int hexToDec(char entrda)
{
	int saida;
	if (isAlpha(entrda))
	{
		saida = (int)entrda - 55;
	}
	else
	{
		saida = (int)entrda - 48;
	}
	return saida;
}

int NOT(int entrda)
{
	return (~entrda);
}
int AND(int entrda1, int entrda2)
{
	return (entrda1 & entrda2);
}
int OR(int entrda1, int entrda2)
{
	return (entrda1 | entrda2);
}
int NAND(int entrda1, int entrda2)
{
	return ~(entrda1 & entrda2);
}
int NOR(int entrda1, int entrda2)
{
	return ~(entrda1 | entrda2);
}
int XOR(int entrda1, int entrda2)
{
	return (entrda1 ^ entrda2);
}
int XNOR(int entrda1, int entrda2)
{
	return ~(entrda1 ^ entrda2);
} //END OF FUNCOES

//display
void display(int entrda)
{
	int temp = entrda;
	if (entrda < 0)
	{
		temp = entrda + 16;
	}
	(temp >= 8) && ((temp -= 8) >= 0) ? digitalWrite(led10, HIGH) : digitalWrite(led10, LOW);
	(temp >= 4) && ((temp -= 4) >= 0) ? digitalWrite(led11, HIGH) : digitalWrite(led11, LOW);
	(temp >= 2) && ((temp -= 2) >= 0) ? digitalWrite(led12, HIGH) : digitalWrite(led12, LOW);
	(temp >= 1) && ((temp -= 1) >= 0) ? digitalWrite(led13, HIGH) : digitalWrite(led13, LOW);
}
//fim display