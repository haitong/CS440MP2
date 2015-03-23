#include <iostream>
#include <vector>

#include "Course.h"
#include "CoursesList.h"
#include "Student.h"
#include "Semester.h"

#define DEBUG 1
using namespace std;

void getValidCoursePlan(Student* student, CoursesList* courses_list)
{

  #ifdef DEBUG
  cout << "The courses interesting for the student are - ";
  for(auto i : student->getInteresting() )
    cout << i << " ";
  cout << endl;
  #endif

  while( (student->getLeft()).size())
  {
    //Semester* s = new Semester();
    // Start picking the prerequisites courses from the last added
    //int course_id = student->getPrerequisites

  }

}

void addPrerequisitesToInterestingCourses(Student* student, CoursesList* courses_list)
{
  vector<int> interesting = student->getInteresting();

  #ifdef DEBUG
  cout << "The courses interesting for the student are - ";
  for(auto i : interesting )
    cout << i << " ";
  cout << endl;
  cout << "Adding prerequisites as interesting as well. " << endl;
  #endif

  for( auto i : interesting)
  {
      // Get the prerequisites for this course
      Course* course = courses_list->getCourse( i);
      vector<int> prerequisites = course->getPrerequisites();
      for( auto p : prerequisites)
      {
          student->addInterestingCourse(p);
          student->addLeftCourse(p);
          student->addPrerequisiteCourse(p);
      }
  }
}

void ProcessInput(Student* student, CoursesList* courses_list)
{
  int n, cmin, cmax;
  cin >> n >> cmin >> cmax;
  student->setCmin(cmin);
  student->setCmax(cmax);

  // Initialize the details for all the courses
  int course_id = 0;
  int fall_cost, spring_cost, credit;
  for( int i = 1 ; i <= n ; ++i)
  {
    course_id = i;
    cin >> fall_cost >> spring_cost >> credit;
    Course* course = new Course( course_id, fall_cost, spring_cost, credit);
    courses_list->addCourse(course_id, course);
  }

  // Initialize the values for prerequisites
  int k;
  for( int i = 1 ; i <= n ; ++i)
  {
    cin >> k;
    if ( k <= 0)
    {
      continue;
    }
    Course* course = courses_list->getCourse( i);
    for( int j = 1 ; j <= k ; ++j)
    {
      int prereq_id;
      cin >> prereq_id;
      course->addPrerequisites(prereq_id);
    }
  }

  // Read the interesting courses for the student
  int m, interesting_id;
  cin >> m;
  student->setNumInteresting(m);
  for( int i = 0 ; i < m ; ++i )
  {
    cin >> interesting_id;
    student->addInterestingCourse(interesting_id);
    student->addLeftCourse(interesting_id);
  }

  int budget;
  cin >> budget;
  student->setBudget(budget);
}

int main()
{
    // Step 0 : Initialize
    CoursesList* courses_list = new CoursesList();
    Student* student = new Student();

    // Step 1: Read all the inputs
    ProcessInput( student, courses_list);

    // All prerequisites are also interesting courses now
    addPrerequisitesToInterestingCourses(student, courses_list);

    // Task A : Provide a valid course plan (just the first valid assignment your
    // backtracking search encounters), disregarding the prices of the courses.
    getValidCoursePlan(student, courses_list);

    // Task B : provide a valid course plan with an overall cost that is no larger
    // than a given budget B (MC is a frugal guy sometimes).

    // Task C : provide a valid course plan with the minimum overall cost.
}
