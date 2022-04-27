#include <iostream>
#include <time.h>
#include <string>
#include <chrono>
#include <vector>
#include <set>
#include "utils.h"
#include "skip_set.h"

using namespace std;
using namespace std::chrono;

vector<int> get_data(int n) {
  vector<int> numbers;
  for (int i = 0; i < n; i++) {
    numbers.push_back(get_random_number());
  }

  return numbers;
}

void basic_skip_set_tests() {
  int n = 15;
  int non_existing_number = -24;
  skip_set<int> set;
  vector<int> v = get_data(n);

  cout << ">> inserting " << n << " elements..." << endl;
  for (int i = 0; i < n; i++) {
    set.insert(v[i]);
  }

  cout << ">> printing set..." << endl;
  set.print_all(cout);

  cout << ">> searching elements..." << endl;
  for (int i = 0; i < n; i++) {
    cout << "> found element " << v[i] << ": " << set.find(v[i]) << endl;
  }

  cout << ">> deleting elements..." << endl;
  for (int i = 0; i < n; i += 2) {
    cout << "deleted element " << v[i] << ": " << set.erase(v[i]) << endl;
  }

  cout << ">> printing set..." << endl;
  set.print_all(cout);

  cout << "> found element " << non_existing_number << ": " << set.find(non_existing_number) << endl;
  cout << "> delete element " << non_existing_number << ": " << set.erase(non_existing_number) << endl;
}

long long get_set_execution_time(int n) {
  vector<int> v = get_data(n);
  set<int> set;

  auto start = high_resolution_clock::now();
  for (int i = 0; i < n; i++) {

    set.insert(v[i]);
  }
  auto end = high_resolution_clock::now();

  duration<double> diff = end - start;
  return duration_cast<milliseconds>(diff).count();
}

long long get_skip_set_execution_time(int n) {
  vector<int> v = get_data(n);
  skip_set<int> set;

  auto start = high_resolution_clock::now();
  for (int i = 0; i < n; i++) {

    set.insert(v[i]);
  }
  auto end = high_resolution_clock::now();

  duration<double> diff = end - start;
  return duration_cast<milliseconds>(diff).count();
}

void test_avg_set_runtime(int n, int retries) {
  long long avg_execution_time = 0;
  long long execution_time = 0;

  for (int i = 0; i < retries; i++) {
    execution_time += get_set_execution_time(n);
  }

  avg_execution_time = execution_time / retries;
  cout << "avg set execution time: " << avg_execution_time << "ms" << endl;
}

void test_avg_skip_set_runtime(int n, int retries) {
  long long avg_execution_time = 0;
  long long execution_time = 0;

  for (int i = 0; i < retries; i++) {
    execution_time += get_skip_set_execution_time(n);
  }

  avg_execution_time = execution_time / retries;
  cout << "avg skip set execution time: " << avg_execution_time << "ms" << endl;
}

int main(int argc, char* argv[]) {
  if (argc != 2) {
    cout << "Usage: " << argv[0] << " <n>" << endl;
    return -1;
  }
  
  srand(time(NULL));
  cout << boolalpha;

  basic_skip_set_tests();

  int n = std::stoi(argv[1]);
  int retries = 15;
  cout << "N: " << n << ", RETRIES: " << retries << endl;

  test_avg_set_runtime(n, retries);
  test_avg_skip_set_runtime(n, retries);

  return 0;
}
