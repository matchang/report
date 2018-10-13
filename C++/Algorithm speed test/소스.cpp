#include <iostream>
#include <algorithm>
#include <ctime>

using namespace std;

#define MAX 30000  // 최대 배열의 크기를 30000으로 미리 정의해줌
int s[MAX];  // 랜덤배열을 선언해줌

//이하로는 정렬 알고리즘들 코드임
void Bubble_Sort(int s[])
{
	for (int i = 0; i < MAX; i++) {
		for (int j = 0; j < MAX - i - 1; j++)

			if (s[j] > s[j + 1])
			{

				int t = s[j];
				s[j] = s[j + 1];
				s[j + 1] = t;

			}
	}
}

void SelectionSort(int s[]) {
	int i, j;
	int min, temp;
	for (i = 0; i < MAX - 1; i++) {
		min = i;
		for (j = i + 1; j < MAX; j++) {
			if (s[j] < s[min]) min = j;
		}
		temp = s[i];
		s[i] = s[min];
		s[min] = temp;
	}

}

void insertionSort(int a[], int n) {

	int i, j, t;
	for (i = 1; i < n; i++) {
		t = a[i];
		j = i;
		while (j > 0 && a[j - 1] > t) {
			a[j] = a[j - 1];
			j--;
		}
		a[j] = t;
	}
}

void max_heapify(int arr[], int start, int end) {

	int dad = start;
	int son = dad * 2 + 1;
	while (son <= end) {
		if (son + 1 <= end && arr[son] < arr[son + 1])
			son++;
		if (arr[dad] > arr[son])
			return;
		else {
			swap(arr[dad], arr[son]);
			dad = son;
			son = dad * 2 + 1;
		}
	}
}

void heap_sort(int arr[], int len) {

	for (int i = len / 2 - 1; i >= 0; i--)
		max_heapify(arr, i, len - 1);

	for (int i = len - 1; i > 0; i--) {
		swap(arr[0], arr[i]);
		max_heapify(arr, 0, i - 1);
	}
}

void Shellsort(int A[]) {
	int i, j, Increment;

	int Tmp;
	for (Increment = MAX / 2; Increment > 0; Increment /= 2)

		for (i = Increment; i < MAX; i++)

		{
			Tmp = A[i];

			for (j = i; j >= Increment; j -= Increment)

				if (Tmp < A[j - Increment])
					A[j] = A[j - Increment];
				else
					break;

			A[j] = Tmp;

		}
}

void RadixSort(int *a)
{
	int i, b[MAX], m = 0, exp = 1;

	for (i = 0; i < MAX; i++)
	{
		if (a[i] > m)
			m = a[i];
	}

	while (m / exp > 0)
	{
		int bucket[10] = { 0 };

		for (i = 0; i < MAX; i++)
			bucket[a[i] / exp % 10]++;

		for (i = 1; i < 10; i++)
			bucket[i] += bucket[i - 1];

		for (i = MAX - 1; i >= 0; i--)
			b[--bucket[a[i] / exp % 10]] = a[i];

		for (i = 0; i < MAX; i++)
			a[i] = b[i];

		exp *= 10;
	}
}

int main() {

	for (int x = 0; x < MAX; x++) {  // 랜덤배열을 생성
		int y = rand() % MAX;
		s[x] = y;
	}

	int s1[MAX];	 // 똑같은 랜덤배열을 기준으로 정렬 알고리즘을 사용해봐야하기 때문에,
	copy(s, s + MAX, s1);    //각 정렬 알고리즘을 사용할 때마다 똑같이 랜덤 배열을 복사하여 사용함
	clock_t start1 = clock();  //그리고 시간을 재어서, 얼마나 걸리는지 구함
	Bubble_Sort(s1); 	  // 정렬 알고리즘을 돌림, 여기서는 버블정렬
	cout << "버블 정렬의 걸린시간: " << (float)(clock() - start1) / CLOCKS_PER_SEC << endl;
	// 얼마나 걸리는 지 출력함

	int s2[MAX];
	copy(s, s + MAX, s2);
	clock_t start2 = clock();
	sort(s2, s2 + MAX);
	cout << "C++ 내장 정렬(퀵)의 걸린시간: " << (float)(clock() - start2) / CLOCKS_PER_SEC << endl;

	int s3[MAX];
	copy(s, s + MAX, s3);
	clock_t start3 = clock();
	SelectionSort(s3);
	cout << "선택 정렬의 걸린시간: " << (float)(clock() - start3) / CLOCKS_PER_SEC << endl;

	int s4[MAX];
	copy(s, s + MAX, s4);
	clock_t start4 = clock();
	insertionSort(s4,MAX);
	cout << "삽입 정렬의 걸린시간: " << (float)(clock() - start4) / CLOCKS_PER_SEC << endl;

	int s5[MAX];
	copy(s, s + MAX, s5);
	clock_t start5 = clock();
	heap_sort(s5, MAX);
	cout << "힙 정렬의 걸린시간: " << (float)(clock() - start5) / CLOCKS_PER_SEC << endl;

	int s6[MAX];
	copy(s, s + MAX, s6);
	clock_t start6 = clock();
	Shellsort(s6);
	cout << "쉘 정렬의 걸린시간: " << (float)(clock() - start6) / CLOCKS_PER_SEC << endl;

	int s7[MAX];
	copy(s, s + MAX, s7);
	clock_t start7 = clock();
	RadixSort(s7);
	cout << "기수 정렬의 걸린시간: " << (float)(clock() - start7) / CLOCKS_PER_SEC << endl;
}