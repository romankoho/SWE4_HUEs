#pragma once
#include <iostream>

template <typename T, const int MAXLEVEL = 32>
class skip_set {
private:
  class skip_node {
  public:
    T value;
    skip_node** forward;

    skip_node(T value, int level)
      : value(value),
        forward(new skip_node*[level+1]()){
    }

    ~skip_node() {
      delete[] forward;
    }
  };

private:
  const float PROBABILITY{ 0.5f };

  int level;
  int size;
  skip_node* head;

  inline int get_random_level() {
    int lvl = 1;

    while (((float)std::rand() / RAND_MAX) < PROBABILITY && lvl < MAXLEVEL) {
      lvl++;
    }

    return lvl;
  }

public:
  skip_set() : level(0), size(0), head(nullptr) {
    head = new skip_node(-1, MAXLEVEL);
  }

  ~skip_set() {
    skip_node* x = head->forward[0];

    while (x != nullptr) {
      skip_node* tmp = x->forward[0];

      delete x;
      x = tmp;
    }

    delete head;
  }

  inline int get_size() const {
    return size;
  }

  inline bool find(T value) {
    skip_node* x = head;

    for (int i = level; i >= 0; i--) {
      while (x->forward[i] != nullptr && x->forward[i]->value < value) {
        x = x->forward[i];
      }
    }
    x = x->forward[0];

    return x != nullptr && x->value == value;
  }

  inline void insert(T value) {
    skip_node* x = head;
    skip_node* update[MAXLEVEL + 1] = { nullptr };

    for (int i = level; i >= 0; i--) {
      while (x->forward[i] != nullptr && x->forward[i]->value < value) {
        x = x->forward[i];
      }
      update[i] = x;
    }

    x = x->forward[0];
    if (x == nullptr || x->value != value) {
      int new_level = get_random_level();

      if (new_level > level) {
        for (int i = level + 1; i <= new_level + 1; i++) {
          update[i] = head;
        }
        level = new_level;
      }

      x = new skip_node(value, new_level);
      size++;

      for (int i = 0; i < new_level; i++) {
        x->forward[i] = update[i]->forward[i];
        update[i]->forward[i] = x;
      }
    }
  }

  inline bool erase(T value) {
    skip_node* x = head;
    skip_node* update[MAXLEVEL + 1];

    for (int i = level; i >= 0; i--) {
      while (x->forward[i] != nullptr && x->forward[i]->value < value) {
        x = x->forward[i];
      }
      update[i] = x;
    }

    x = x->forward[0];
    if (x != nullptr && x->value == value) {
      for (int i = 0; i <= level; i++) {
        if (update[i]->forward[i] != x) {
          break;
        }
        update[i]->forward[i] = x->forward[i];
      }

      delete x;
      size--;

      while (level > 0 && head->forward[level] == nullptr) {
        level--;
      }
      return true;
    }

    return false;
  }

  inline void print_level(std::ostream& os, int lvl) const {
    skip_node* x = head->forward[lvl];
    os << "[LVL " << lvl << "]: ";

    while (x != nullptr) {
      os << x->value << " ";
      x = x->forward[lvl];
    }
    os << std::endl;
  }

  inline void print(std::ostream& os) const {
    print_level(os, 0);
  }

  inline void print_all(std::ostream& os) const {
    os << "[SIZE]: " << size << std::endl;
    for (int i = 0; i <= level; i++) {
      print_level(os, i);
    }
  }

  inline friend std::ostream& operator<<(std::ostream& os, const skip_set& s) {
    return os << s.print(os);
  }
};
