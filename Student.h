#include <cassert>
#include <vector>
#include <algorithm>

using namespace std;

class Semester;

class Student
{
  private:
    int _cmin;
    int _cmax;
    int _num_interesting;
    vector<int> _interesting;
    vector<int> _finished;
    vector<int> _left;
    vector<int> _prerequisites;
    vector<Semester*> _semesters;
    int _budget;
  public:
    int getCmin() { return _cmin; }
    int getCmax() { return _cmax; }
    int getNumInteresting() { return _num_interesting; }
    vector<Semester*> getSemesters() { return _semesters; }
    int getNumSemesters() { return (int)_semesters.size(); }
    vector<int> getInteresting() { return _interesting; }
    vector<int> getFinished() { return _finished; }
    vector<int> getLeft() { return _left; }
    vector<int> getPrerequisites() { return _prerequisites; }
    int getBudget() { return _budget; }

    void setCmin( int cmin) { _cmin = cmin; }
    void setCmax( int cmax) { _cmax = cmax; }
    void setNumInteresting( int m) { _num_interesting = m; }
    void setBudget( int budget) { _budget = budget; }

    void addSemester( Semester* s)
    {
      _semesters.push_back(s);
    }
    void addInterestingCourse( int course_id )
    {
      if( std::find(_interesting.begin(), _interesting.end(), course_id) != _interesting.end() )
        return;
      _interesting.push_back(course_id);
    }
    void addPrerequisiteCourse( int course_id )
    {
      if( std::find(_prerequisites.begin(), _prerequisites.end(), course_id) != _prerequisites.end() )
        return;
      _prerequisites.push_back(course_id);
    }
    void addFinishedCourse( int course_id )
    {
      // This course can't be in the left vector
      assert( std::find( _left.begin(), _left.end(), course_id) == _left.end() );
      _finished.push_back(course_id);
      checkInvariant();
    }
    void addLeftCourse( int course_id )
    {
      if( std::find(_left.begin(), _left.end(), course_id) != _left.end() )
        return;

      // This course can't be in the finished vector
      assert( std::find( _finished.begin(), _finished.end(), course_id) == _finished.end() );
      _left.push_back(course_id);
      checkInvariant();
    }
    void removeLeftCourse( int course_id)
    {
      // Course must be present in the left courses
      assert( std::find( _left.begin(), _left.end(), course_id) != _left.end() );
      _left.erase( std::remove( _left.begin(), _left.end(), course_id), _left.end() );
      checkInvariant();
    }
    void removeFinisedCourse( int course_id)
    {
      // Course must be present in the finished courses
      assert( std::find( _finished.begin(), _finished.end(), course_id) != _finished.end() );
      _finished.erase( std::remove( _finished.begin(), _finished.end(), course_id), _finished.end() );
      checkInvariant();
    }

    void checkInvariant()
    {
      vector<int> a = _left;
      vector<int> b = _finished;
      vector<int> combined;
      combined.reserve( a.size() + b.size() ); // preallocate memory
      combined.insert( combined.end(), a.begin(), a.end() );
      combined.insert( combined.end(), b.begin(), b.end() );

      vector<int> c = _interesting;

      vector<int> v3;
      std::set_difference(combined.begin(), combined.end(), c.begin(), c.end(), std::back_inserter(v3));
      assert( v3.size() == 0);
    }
};
