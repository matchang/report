#include<iostream>
#include<stack>  // 스택 함수를 사용하기 위해서 사용함
using namespace std;


// 각각 값의 가중치를 매겨서 그 값을 반환해주는 함수
int getWeight(char ch) {
	switch (ch) {
	case '/':
	case '*': return 2;  // 곱하기 나누기는 높은 2
	case '+':
	case '-': return 1;  //더하기 빼기는 그것 보다 낮은 1
	default: return 0;  // 그리고 연산자가 아닌 것들은 0으로 측정해서 반환해준다
	}
}

// infix값을 받아서 postfix(출력하는)값에다 넣어주는 함수, infix의 사이즈 값도 받는다
void convert(char infix[], char postfix[], int size) {
	stack<char> s;  // char형 스택 s를 선언해 준다
	int weight;  // 각각 infix값의 가중치를 받을 변수
	int i = 0;  // infix의 사이즈 값만큼 계속 카운트 하도록 하는 변수
	int k = 0;  // 값을 출력하는 걸 카운트하는 변수
	char ch;  // 입력받은 infix값 하나하나를 입력 받을 변수

	while (i < size) {  // size만큼 반복한다
		ch = infix[i];  // 각각 값을 ch에 대입한다
		if (ch == '(') {  // 만약 값 중에 '('가 나오면 무조건 스택에 집어넣어준다
			s.push(ch);
			i++;  // 그리고 다음 값을 받기 위해서 증가시켜준다
			continue;   // 그리고 다음 값으로 넘어간다
		}
		if (ch == ')') {  // 그러다 '('이 나오면 
			while (!s.empty() && s.top() != '(') {
				// 만약 스택이 비어있지 않고, 또 top값이 '('이 아닐 때 까지 반복한다
				postfix[k++] = s.top();
				// 남은(top)값들을 출력하는 postfix에 넣고나 서 다음 값으로 넘어가기 위해 k로 증가시켜 준다
				s.pop();  // 그러고 나서 맨 위의 값을 삭제해준다
			}
			if (!s.empty()) { // 그러고 나면 스택에 남아있는 괄호 값도 삭제해준다
				s.pop();
			}
			i++;  // 그러고 나서 다음 값을 받기 위해 증가시켜준다
			continue;
		}
		weight = getWeight(ch);  // 가중치 값을 받는 변수에 ch의 가중치 값을 받는다
		if (weight == 0) {      // 만약 가중치가 0이면 연산자가 아니니까
			postfix[k++] = ch;  // 그냥 출력 값인 postfix값에 넣어줌, 
					// 그러고 나서 k값을 증가시켜서 다음 값을 받을 수 있게 한다
		}
		else {  // 가중치가 0이 아니면, 즉 값이 연산자이면
			if (s.empty()) {  // 만약 스택이 비었으면
				s.push(ch);  // 일단 무조건 스택에 넣는다.
			}
			else {  // 스택이 비어있지 않으면
				while (!s.empty() && s.top() != '(' && weight <= getWeight(s.top())) {
					// 스택 값이 비어있지 않고, 스택 top값이 '('이 아니고, 가중치 값이 top값 보다 작을 때 동안 실행 반복.
					// 즉 스택은 당연히 비어있으면 안되고, top값이 '('이면 그 이상은 빼내면 안 되니까, 그리고
					// 다음 입력한 infix값의 가중치가 전에 스택 안에 넣은 값의 가중치와 같거나 더 작으면 못 넣으니까
					// 스택에서 빼서 postfix(출력값)에 넣어준다.
					postfix[k++] = s.top();  // 값을 넣고 나서 k값을 증가시켜 카운트한다.
					s.pop();  // 넣은 다음에 스택 안에 있으면 안 되니까 삭제한다.
				}
				s.push(ch);  // 위의 사항에 해당 안 될 때는 그냥 스택 안에 넣는다.
			}
		}
		i++;  // 그렇게 연산자 혹은 식 하나를 스택이든 postfix든 어디든 넣어야 한 사이클이 끝난다.
			 // 한 사이클이 끝났으니 i를 증가시켜 다음 값을 받는다
	}
	// 위의 과정을 다 거치면, 스택에 아직까지 남아있는 연산자가 있을 수 있다. 
	// 그래서 연산자들도 빼 줄 필요가 있다.
	while (!s.empty()) {  //  스택이 빌 때 까지
		postfix[k++] = s.top();  // 스택안의 값을 postfix 출력 값에 넣어준다 
		s.pop();  // 그리고 스택안의 값은 필요 없으니까 삭제 시킨다.
	}

	postfix[k] = 0;
	// 마지막으로 만약에 postfix 배열에 안 쓰이는 곳에 남아있는 쓰래기값들을 출력이 안 되게 초기화 시켜준다
}


int main() {
	char infix[30]; // 수식을 입력받을 배열을 선언 해준다 (크기제한 30)
	cout << "infix 수식을 입력하세요(30자제한): ";
	cin >> infix;  // infix에 수식을 받는다

	int size = strlen(infix);  // 사용자가 입력한 수식의 크기를 size에 넣어준다
	if (size > 30) {  // 만약 사용자가 입력한 값이 30이상이면, 오버플로우로 표시해주고 프로그램을 나간다
		cout << "오버플로우!!" << endl;
		return 0;
	}

	char postfix[30];  // 출력 값을 받을 배열을 선언 해준다. (크기 제한은 똑같이 30)
	convert(infix, postfix, size);
	// 입력값 infix와 출력 값을 받을 postfix, 그리고 size값을 변환하는 함수에 넣어준다.
	cout << "\n사용자가 입력한 infix 수식을 Postfix로 변환한 결과 입니다: " << postfix << endl;
	// 마지막으로 변환한 값을 postfix배열로 출력해 준다.
	return 0;
}

