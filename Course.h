#include <vector>
using namespace std;

class Course
{
  private:
    int _id;
    int _credit;
    int _spring_cost;
    int _fall_cost;
    vector<int> prerequisites;
    bool _is_interesting;

  public:
    Course( int id)
    {
      _id = id;
    }

    Course( int id, int fall_cost, int spring_cost, int credit)
    {
      _id = id;
      _fall_cost = fall_cost;
      _spring_cost = spring_cost;
      _credit = credit;
    }

    int getSpringCost() { return _spring_cost; }
    void setSpringCost( int cost)
    {
      _spring_cost = cost;
    }

    int getFallCost() { return _fall_cost; }
    void setFallCost( int cost)
    {
      _fall_cost = cost;
    }

    int getCredit() { return _credit; }
    void setCredit( int credit)
    {
      _credit = credit;
    }

    bool isInteresting() { return _is_interesting; }
    void setInteresting() { _is_interesting = true; }

    vector<int> getPrerequisites()
    {
      return prerequisites;
    }

    void addPrerequisites( int course_id )
    {
      prerequisites.push_back(course_id);
    }
};
