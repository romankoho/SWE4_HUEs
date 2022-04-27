#pragma once

#include <queue>

#include "Event.h"

struct Compare : public std::binary_function<Event*, Event*, bool> {
	bool operator()(const Event* e1, const Event* e2) const {
		return e1->Get_Time_Stamp() > e2->Get_Time_Stamp();
	}
};

class EventQueue {
private:
	std::priority_queue < Event*, std::vector<Event*>, Compare> eq;

public:

	EventQueue() {
		//nothing to do
	}

	void Add_Event(Event *e) {
		this->eq.push(e);
	}

	void Step() {
		if (!this->eq.empty()) {
			Event* temp = this->eq.top();
			temp->Execute_Event();
			this->eq.pop();
		}
	}

	void Run() {
		while (!this->eq.empty()) {
			this->Step();
		}
	}

	int Get_Queue_Size() {
		return this->eq.size();
	}


};