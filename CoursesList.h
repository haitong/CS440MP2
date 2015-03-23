#include <map>
#include <algorithm>
#include <cassert>

class Course;
using namespace std;

class CoursesList
{
  private:
    map<int, Course*> _courses;

  public:
    void addCourse( int course_id, Course* course)
    {
      // The course must not be already present
      auto it = _courses.find(course_id);
      assert( it == _courses.end() );

      _courses[course_id] = course;
    }

    Course* getCourse( int course_id)
    {
      // The course must be present
      auto it = _courses.find(course_id);
      assert( it != _courses.end() );

      return _courses[course_id];
    }

};
