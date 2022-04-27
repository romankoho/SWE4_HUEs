#include <iostream>
#include <vector>

#include "Event.h"
#include "EventQueue.h"

using std::cout;
using std::endl;

std::queue<int> buffer;
const int BUFFER_CAPACITY = 10;
const int MAX_CONSUMED = 100;
int amount_produced = 0;

int Generate_Random_Number() {
  int random_number = rand() % 500;
  return random_number;
}

void Produce() {
	int value = Generate_Random_Number();
	buffer.push(value);
	cout << "Inserted value: " << value << endl;
	amount_produced++;
}

void Consume() {
	cout << "Get value: " << buffer.front() << endl;
	buffer.pop();
}

int main() {
  EventQueue q;
  int c_time = Generate_Random_Number();
  int p_time = Generate_Random_Number();
  int counter_consumed = 0;
  srand(time(0)); 
  
  while (counter_consumed < MAX_CONSUMED) {
    while (buffer.size() < BUFFER_CAPACITY) {
      Event* e = new Event(p_time, &Produce);
      q.Add_Event(e);
      e->Execute_Event();
      p_time = Generate_Random_Number();
    }
    while (!buffer.empty()) {
      Event* e = new Event(c_time, &Consume);
      q.Add_Event(e);
      e->Execute_Event();
      counter_consumed++;
    }
  }
  cout << "_____________________________________________________" << endl;
  cout << "MAXIMUM CONSUME of " << counter_consumed << " reached!" << endl;
}
