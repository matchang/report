#include <iostream>
#include <algorithm>
#include <ctime>

using namespace std;

#define MAX 30000  // �ִ� �迭�� ũ�⸦ 30000���� �̸� ��������
int s[MAX];  // �����迭�� ��������

//���Ϸδ� ���� �˰���� �ڵ���
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

	for (int x = 0; x < MAX; x++) {  // �����迭�� ����
		int y = rand() % MAX;
		s[x] = y;
	}

	int s1[MAX];	 // �Ȱ��� �����迭�� �������� ���� �˰����� ����غ����ϱ� ������,
	copy(s, s + MAX, s1);    //�� ���� �˰����� ����� ������ �Ȱ��� ���� �迭�� �����Ͽ� �����
	clock_t start1 = clock();  //�׸��� �ð��� ��, �󸶳� �ɸ����� ����
	Bubble_Sort(s1); 	  // ���� �˰����� ����, ���⼭�� ��������
	cout << "���� ������ �ɸ��ð�: " << (float)(clock() - start1) / CLOCKS_PER_SEC << endl;
	// �󸶳� �ɸ��� �� �����

	int s2[MAX];
	copy(s, s + MAX, s2);
	clock_t start2 = clock();
	sort(s2, s2 + MAX);
	cout << "C++ ���� ����(��)�� �ɸ��ð�: " << (float)(clock() - start2) / CLOCKS_PER_SEC << endl;

	int s3[MAX];
	copy(s, s + MAX, s3);
	clock_t start3 = clock();
	SelectionSort(s3);
	cout << "���� ������ �ɸ��ð�: " << (float)(clock() - start3) / CLOCKS_PER_SEC << endl;

	int s4[MAX];
	copy(s, s + MAX, s4);
	clock_t start4 = clock();
	insertionSort(s4,MAX);
	cout << "���� ������ �ɸ��ð�: " << (float)(clock() - start4) / CLOCKS_PER_SEC << endl;

	int s5[MAX];
	copy(s, s + MAX, s5);
	clock_t start5 = clock();
	heap_sort(s5, MAX);
	cout << "�� ������ �ɸ��ð�: " << (float)(clock() - start5) / CLOCKS_PER_SEC << endl;

	int s6[MAX];
	copy(s, s + MAX, s6);
	clock_t start6 = clock();
	Shellsort(s6);
	cout << "�� ������ �ɸ��ð�: " << (float)(clock() - start6) / CLOCKS_PER_SEC << endl;

	int s7[MAX];
	copy(s, s + MAX, s7);
	clock_t start7 = clock();
	RadixSort(s7);
	cout << "��� ������ �ɸ��ð�: " << (float)(clock() - start7) / CLOCKS_PER_SEC << endl;
}