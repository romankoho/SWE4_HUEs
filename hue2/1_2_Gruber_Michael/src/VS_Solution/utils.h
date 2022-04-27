#pragma once
#include <stdlib.h>

#define MIN_RANDOM_NUMBER 0
#define MAX_RANDOM_NUMBER 2000000

/// <summary>
/// Gets a random integer number between a given limit.
/// </summary>
inline int get_random_number(int min, int max) {
  return (rand() % (max - min + 1) + min);
}

/// <summary>
/// Gets a random integer number between RAND_MIN and RAND_MAX.
/// </summary>
inline int get_random_number() {
  return get_random_number(MIN_RANDOM_NUMBER, MAX_RANDOM_NUMBER);
}
