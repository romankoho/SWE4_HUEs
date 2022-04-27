#pragma once

class Event {
private: 
	int timestamp;
	void (*memberfunc)();

public:
	Event(int t, void(*f)())
		: timestamp(t), memberfunc(f) {
		//nothing else to do
	}

	int Get_Time_Stamp() const {
		return timestamp;
	}

	void Execute_Event() {
		memberfunc();
	}

};